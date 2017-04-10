package com.devcraftsman.graphics.collector

import com.devcraftsman.graphics.collector.repo.HsqlMetricRepoComponent
import com.typesafe.config.ConfigFactory
import slick.jdbc.HsqldbProfile.api._

import scala.util.Properties

/**
  * Created by devcraftsman on 4/7/17.
  * ----------------------------------------------------
  * This software is licensed under the Apache 2 license
  * see: [http://www.apache.org/licenses/LICENSE-2.0]
  **/
object Config extends HsqlMetricRepoComponent {

  class MyConfig(fileNameOption: Option[String] = None) {

    val cfg = fileNameOption.fold(ifEmpty = ConfigFactory.load())(file => ConfigFactory.load(file))

    def envOrElseConfig(name: String): String = {
      Properties.envOrElse(
        name.toUpperCase.replaceAll("""\.""", "_"),
        cfg.getString(name)
      )
    }

  }

  val db: Database = Database.forConfig("graphics.collector.hsqlMem")
  val config = new MyConfig()



}