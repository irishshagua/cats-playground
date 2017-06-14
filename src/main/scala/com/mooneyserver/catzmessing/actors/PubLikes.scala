package com.mooneyserver.catzmessing.actors

import akka.actor.{Actor, ActorLogging, Props}
import com.mooneyserver.catzmessing.actors.PubLikes.{GetLikes, Like, Likeability, ResetLikes}
import cats.data.State

object PubLikes {

  case object Like
  case object GetLikes
  case object ResetLikes

  case class Likeability(numLikes: Int = 0)

  def props: Props = Props(new PubLikes)

  def like: State[Likeability, Unit] = State[Likeability, Unit](xs => (xs.copy(numLikes = xs.numLikes + 1), ()))
}

class PubLikes extends Actor with ActorLogging {

  var numLikes: State[Int, Likeability] = State.pure(Likeability())

  def receive: Receive = {
    case Like =>
      numLikes = numLikes.map(l => (l.copy(l.numLikes + 1) -> 1))
      log.info("Liked")
    case GetLikes =>
      log.info(s"Sending num likes: ${numLikes.run(0).value}")
    case ResetLikes =>
      numLikes = numLikes.map(_ => Likeability())
      log.info("Resetting Likes to 0")
  }
}
