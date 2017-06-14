package com.mooneyserver.catzmessing.http

import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.mooneyserver.catzmessing.actors.PubLikes
import com.mooneyserver.catzmessing.actors.PubLikes.{GetLikes, Like, ResetLikes}
import akka.util.Timeout

import scala.concurrent.duration._

trait LikeSvc {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val timeout: Timeout = 5 seconds

  val likeableFellow = system.actorOf(PubLikes.props)

  val route: Route =
    path("likes") {
      post {
        likeableFellow ! Like
        complete(StatusCodes.OK)
      } ~ get {
        likeableFellow ! GetLikes
        complete(StatusCodes.OK)
      } ~ delete {
        likeableFellow ! ResetLikes
        complete(StatusCodes.OK)
      }
    }
}
