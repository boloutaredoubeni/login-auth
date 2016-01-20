package com.boloutaredoubeni.loginauth

import akka.actor._
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import spray.can.Http

import scala.concurrent.duration._

object Main extends App {
  implicit val system = ActorSystem("login-auth")
  val service = system.actorOf(Props[JwtServiceActor], "login-auth")
  implicit val timeout = Timeout(60.seconds)
  IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080)
}

