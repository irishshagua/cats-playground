package com.mooneyserver.catzmessing

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.mooneyserver.catzmessing.http.LikeSvc

object Main extends App with LikeSvc {

  Http().bindAndHandle(route, "localhost", 8080)
}
