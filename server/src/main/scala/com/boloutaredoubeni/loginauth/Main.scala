package com.boloutaredoubeni.loginauth

import akka.actor._
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import spray.can.Http
import spray.http._
import spray.routing._
import MediaTypes._
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

class JwtServiceActor extends Actor with HttpService with ActorLogging {
  def actorRefFactory = context

  def receive = runRoute(loginRoute ~ authRoute)

  val loginRoute = {
    path("login") {
      get {
        headerValue({
          case x@HttpHeaders.`Authorization`(value) => Some(value)
          case default => None
        }) {
          header => header match {
            case GenericHttpCredentials("Bearer", token, params) => {
              respondWithMediaType(`application/json`) {
                complete {
                  //                  """{"key":"got-it"}"""
                  Array("Bearer", token, params.toString).mkString(" ")
                }
              }
            }
            case default => {
              complete {
                HttpResponse(406)
              }
            }
          }
        }
      }
    }
  }

  val authRoute = {
    path("auth") {
      post {
        headerValue({
          case x@HttpHeaders.`Content-Type`(value) => Some(value)
          case default => None
        }) {
          header => header match {
            case ContentType(`application/json`, _) => {
              respondWithMediaType(`application/json`) {
//                val token = ???
                complete {
//                  """{"key":"got-it"}"""
                  "Ur authorized"
                }
              }
            }
            case default => {
              complete {
                HttpResponse(406)
              }
            }
          }
        }
      }
    }
  }
}
