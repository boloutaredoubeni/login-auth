package com.boloutaredoubeni.loginauth
/**
 * Created by boloutare on 1/19/16.
 */

import spray.http.MediaTypes

import scala.util.Success
import scala.util.Failure
import spray.http._
import pdi.jwt._
import spray.routing._
import MediaTypes._

trait JwtService extends HttpService {

  val mySuperSecretKey = "mY5Up3r53cr3tk3Y"
  val defaultAlgorithm = JwtAlgorithm.HS256

  val index = {
    get {
      complete {
        "Hello World"
      }
    }
  }

  val apiRoutes = {
    pathPrefix("api") {
      pathPrefix("v1") {
        get {
          path("login") {
            headerValue({
              case x @ HttpHeaders.`Authorization`(value) => Some(value)
              case default => None
            }) {
              case GenericHttpCredentials("Bearer", token, _) =>
                respondWithMediaType(`application/json`) {
                  complete {
                    // NOTE: token passed in to decode with quotes, they must be replaced
                    Jwt.decodeAll(token.replace("'", ""), mySuperSecretKey, Seq(defaultAlgorithm)) match {
                      case Success(_ /* decoded */ ) => HttpResponse(200, entity = "Logged In")
                      case Failure(ex) => HttpResponse(406, entity = ex.toString())
                    }
                    //                token
                  }
                }
              case default => complete {
                HttpResponse(406)
              }
            }
          }
        } ~
          post {
            path("auth") {
              headerValue({
                case x @ HttpHeaders.`Content-Type`(value) => Some(value)
                case default => None
              }) {
                case ContentType(`application/json`, _) =>
                  respondWithMediaType(`application/json`) {
                    entity(as[String]) { user =>
                      complete {
                        Jwt.encode(user, mySuperSecretKey, defaultAlgorithm)
                      }
                    }
                  }
                case default => complete {
                  HttpResponse(406)
                }
              }
            }
          }
      }
    }
  }
}

