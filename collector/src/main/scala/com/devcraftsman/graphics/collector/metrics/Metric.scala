package com.devcraftsman.graphics.collector.metrics

import java.text.ParseException

import akka.http.scaladsl.unmarshalling.PredefinedFromEntityUnmarshallers

import scala.util.{Failure, Success, Try}


case class Metric(name: String, sample: (Float, Long))

object Metric {

  val pattern = "([a-zA-Z]+(?:\\.[a-zA-Z]+)*)\\s(\\d+(?:\\.\\d+)?)\\s(\\d+)\\n".r

  def parse(s: String): Try[Metric] = s match {
    case pattern(k, v, t) => Success(new Metric(k, (v.toFloat, t.toLong)))
    case _ => Failure(new ParseException("Unable to parse request", 0))
  }

  val unMarshaller = PredefinedFromEntityUnmarshallers.stringUnmarshaller.map(parse(_))


}




