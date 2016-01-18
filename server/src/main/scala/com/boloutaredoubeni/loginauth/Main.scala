package com.boloutaredoubeni.loginauth

import akka.actor.{Props, ActorSystem, Actor}
import akka.io.IO
import akka.util.Timeout
import akka.pattern.ask
import spray.can.Http
import spray.http.HttpHeaders._
import spray.http.ContentTypes._
import spray.routing.HttpService
import spray.http.HttpHeaders
import scala.concurrent.duration._

/**
  * Created by boloutare on 1/18/16.
  */
object Main extends App {
  implicit val system = ActorSystem("login-auth")
  val service = system.actorOf(Props[JwtServiceActor], "login-auth")
  implicit val timeout = Timeout(5.seconds)
  IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080)
}

class JwtServiceActor extends Actor with HttpService {
  def actorRefFactory = context

  def receive = runRoute(authRoute)

  val authRoute = {
    path("auth") {
      post {
        headerValue({
          case x@HttpHeaders.`Authorization`(value) => Some(value)
          case default => None
        }) {
          case _ => {
            respondWithHeader(`Content-Type`(`application/json`)) {
              complete {
                """{"key":"got-it"}"""
              }
            }
          }
//          case default => {
//            complete {
//              HttpResponse(406)
//            }
//          }
        }
      }
    }
  }
}
