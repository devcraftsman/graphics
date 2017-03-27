package com.devcraftsman.graphics.collector.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.devcraftsman.graphics.collector.metrics._

import scala.io.StdIn
import scala.util.{Failure, Success, Try}


object WebServer {

  implicit val metricUnmarshaller = Metric.unMarshaller


  val route = path("ping") {
    get {
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h2>pong</h2>"))
    }
  } ~ path("collect") {
    post {
      decodeRequest {
        entity(as[Try[Metric]]) {
          case Success(_) => complete("OK")
          case Failure(e) => complete(HttpResponse(BadRequest, entity = "Error: " + e.getLocalizedMessage))
        }
      }
    }
  }


  def start() {

    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher


    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}