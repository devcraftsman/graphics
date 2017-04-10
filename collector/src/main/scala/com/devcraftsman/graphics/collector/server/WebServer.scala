package com.devcraftsman.graphics.collector.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives.{entity, _}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import com.devcraftsman.graphics.collector.Config
import com.devcraftsman.graphics.collector.metrics._
import com.typesafe.scalalogging.StrictLogging

import scala.io.StdIn
import scala.util.{Failure, Success, Try}


class WebServer extends StrictLogging {

  implicit val metricUnmarshaller = Metric.unMarshaller
  implicit val system = ActorSystem("collector-system")
  implicit val materializer = ActorMaterializer()

  val repo = Config.repo


  val route = path("ping") {
    get {
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h2>pong</h2>"))
    }
  } ~ path("collect") {
    post {
      decodeRequest {
        entity(as[Try[Metric]]) {
          case Success(m) => {
            val saving = Source.single(m).map(repo.append(_)).runWith(Sink.ignore)

            onComplete(saving) { result =>
              result.toEither match {
                case Right(_)
                => {
                  logger.info(s" metric ${m.name} for time ${m.sample._2} processed")
                  complete("OK - FLOW")
                }
                case Left(e) => {
                  complete({
                    logger.error(s"Unable to process metric. Error is $e")
                    HttpResponse(InternalServerError, entity = "Error: " + e.getLocalizedMessage)
                  })
                }
              }
            }
          }
          case Failure(e) => complete({
            logger.error(s"Unable to process metric. Error is $e")
            HttpResponse(BadRequest, entity = "Error: " + e.getLocalizedMessage)
          })
        }
      }
    }
  }


  def start() {
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher
    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => {
      Config.repo.close();
      system.terminate()
    }) // and shutdown when done
  }
}