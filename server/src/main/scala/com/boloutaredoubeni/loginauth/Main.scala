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
import scala.util.Success
import scala.util.Failure
import pdi.jwt._

/**
  * Created by boloutare on 1/18/16.
  */
object Main extends App {
  implicit val system = ActorSystem("login-auth")
  val service = system.actorOf(Props[JwtServiceActor], "login-auth")
  implicit val timeout = Timeout(60.seconds)
  IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080)
}

class JwtServiceActor extends Actor with HttpService with ActorLogging {
  val mySuperSecretKey = "mY5Up3r53cr3tk3Y"
  val defaultAlgorithm = JwtAlgorithm.HS256

  def actorRefFactory = context

  def receive = runRoute(loginRoute ~ authRoute)

  val loginRoute = {
    get {
      path("login") {
        headerValue({
          case x@HttpHeaders.`Authorization`(value) => Some(value)
          case default => None
        }) {
          case GenericHttpCredentials("Bearer", token, _) =>
            respondWithMediaType(`application/json`) {
              complete {
                // NOTE: token passed in to decode with quotes, they must be replaced
                Jwt.decodeAll(token.replace("'", ""), mySuperSecretKey, Seq(defaultAlgorithm)) match {
                  case Success(_ /* decoded */) => HttpResponse(200, entity = "Logged In")
                  case Failure(ex) => HttpResponse(406, entity = ex.toString())
                }
//                token
              }
            }
          case default => complete { HttpResponse(406) }
        }
      }
    }
  }

  val authRoute = {
    post {
      path("auth") {
        headerValue({
          case x@HttpHeaders.`Content-Type`(value) => Some(value)
          case default => None
        }) {
          case ContentType(`application/json`, _) =>
            respondWithMediaType(`application/json`) {
              entity(as[String]) { user =>
                complete { Jwt.encode(user , mySuperSecretKey, defaultAlgorithm) }
              }
            }
          case default => complete { HttpResponse(406) }
        }
      }
    }
  }
}

