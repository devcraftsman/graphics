package com.devcraftsman.graphics.collector.metrics

import slick.jdbc.HsqldbProfile.api._

trait DbMapping {

  class Metrics(tag: Tag) extends Table[Metric](tag, "metrics") {
    def name = column[String]("name")

    def value = column[Float]("value")

    def timestamp = column[Long]("timeMillis")

    def * = (name, value, timestamp).shaped <> ( {
      case (name, value, timestamp) =>
        new Metric(name, (value, timestamp))
    }, { m: Metric =>
      Some((m.name, m.sample._1, m.sample._2))
    })

    def pk = primaryKey("metric_pk", (name, timestamp))

    def nameIdx = index("name_idx", (name), unique = false)

  }

  val metrics = TableQuery[Metrics]

}

