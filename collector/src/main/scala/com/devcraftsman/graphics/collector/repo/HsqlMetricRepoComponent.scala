package com.devcraftsman.graphics.collector.repo

import com.devcraftsman.graphics.collector.metrics.{DbMapping, Metric}
import com.typesafe.scalalogging.StrictLogging
import slick.jdbc.HsqldbProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

/**
  * Created by devcraftsman on 4/7/17.
  * ----------------------------------------------------
  * This software is licensed under the Apache 2 license
  * see: [http://www.apache.org/licenses/LICENSE-2.0]
  **/
trait HsqlMetricRepoComponent extends MetricRepoComponent {

  val db: Database;
  val repo = new HsqlMetricRepo(db)

  class HsqlMetricRepo(db: => Database) extends MetricRepo with DbMapping with StrictLogging {
    override def init(): Unit = {
      logger.info("Init In memory Database Schema")
      db.run(metrics.schema.create).onComplete(_ match {
        case Success(_) => logger.debug(s"Database schema created")
        case Failure(e) => logger.error(s"Unable to create Db schema. Error is $e")
      });
    }

    override def append(metric: Metric): Unit = {
      db.run(metrics += metric).onComplete(_ match {
        case Success(_) => logger.debug(s"Sample of metric ${metric.name} saved. ")
        case Failure(e) => logger.error(s"unable to save metric ${metric.name}. Error is $e")
      });
    }

    override def close(): Unit = {
      logger.info("Closing In memory Database")
      db.close();
      logger.info("In memory Database Closed")
    }
  }


}
