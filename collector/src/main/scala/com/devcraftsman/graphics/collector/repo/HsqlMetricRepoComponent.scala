package com.devcraftsman.graphics.collector.repo

import com.devcraftsman.graphics.collector.Config
import com.devcraftsman.graphics.collector.metrics.Metric
import slick.jdbc.HsqldbProfile.api._

/**
  * Created by devcraftsman on 4/7/17.
  * ----------------------------------------------------
  * This software is licensed under the Apache 2 license
  * see: [http://www.apache.org/licenses/LICENSE-2.0]
  **/
trait HsqlMetricRepoComponent extends MetricRepoComponent {

  val db: Database = Database.forConfig("hsqlMem", Config.properties)
  val repo = new HsqlMetricRepo(db)

  class HsqlMetricRepo(db: => Database) extends MetricRepo {
    override def init(): Unit = ???

    override def append(metric: Metric): Unit = ???
  }


}
