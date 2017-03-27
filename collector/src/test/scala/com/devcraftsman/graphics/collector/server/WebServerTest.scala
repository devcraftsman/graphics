package com.devcraftsman.graphics.collector.server

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}

/**
  * Created by devcraftsman on 3/27/17.
  * ----------------------------------------------------
  * This software is licensed under the Apache 2 license
  * see: [http://www.apache.org/licenses/LICENSE-2.0]
  **/
class WebServerTest extends WordSpec with Matchers with ScalatestRouteTest {

  val route = WebServer.route;

  "The service" should {

    "return a greeting for GET requests to the ping path" in {
      // tests:
      Get("/ping") ~> route ~> check {
        responseAs[String] shouldEqual "<h2>pong</h2>"
      }
    }

    "return 'OK' for a valid metric record" in {
      Post("/collect", "test.metric.rate 1490622061 5.30") ~> route ~> check {
        responseAs[String] shouldEqual "OK"
      }
    }

    "return BadRequest for a empty metric record" in {
      Post("/collect") ~> route ~> check {
        response.status shouldEqual (BadRequest)
      }
    }
  }

}
