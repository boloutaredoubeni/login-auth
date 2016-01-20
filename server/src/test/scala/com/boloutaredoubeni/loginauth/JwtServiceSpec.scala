package com.boloutaredoubeni.loginauth

import org.specs2.mutable.Specification
import spray.testkit.Specs2RouteTest
import spray.http._
import StatusCodes._

class JwtServiceSpec extends Specification with Specs2RouteTest with JwtService {
  def actorRefFactory = system

  "JwtService" should {
    "should have a root path" in {
      Get() ~> index ~> check {
        status === OK
      }
    }
  }
}
