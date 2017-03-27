package com.devcraftsman.graphics.collector.metrics

import java.text.ParseException

import akka.http.scaladsl.unmarshalling.PredefinedFromEntityUnmarshallers

import scala.util.{Failure, Success, Try}


case class Metric(name: String, sample: (Long, Float))

object Metric {

  val pattern = "([a-zA-Z]+(?:\\.[a-zA-Z]+)*)\\s(\\d+)\\s(\\d+(?:\\.\\d+)?)".r

  def parse(s: String): Try[Metric] = s match {
    case pattern(k, t, v) => Success(new Metric(k, (t.toLong, v.toFloat)))
    case _ => Failure(new ParseException("Unable to parse request", 0))
  }

  val unMarshaller = PredefinedFromEntityUnmarshallers.stringUnmarshaller.map(parse(_))


}




