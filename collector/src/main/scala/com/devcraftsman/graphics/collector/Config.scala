package com.devcraftsman.graphics.collector

import java.io.File

import com.devcraftsman.graphics.collector.repo.FileMetricRepoComponent
import com.typesafe.config.ConfigFactory

import scala.util.Properties

/**
  * Created by devcraftsman on 4/7/17.
  * ----------------------------------------------------
  * This software is licensed under the Apache 2 license
  * see: [http://www.apache.org/licenses/LICENSE-2.0]
  **/
object Config extends /*HsqlMetricRepoComponent */ FileMetricRepoComponent {

  class MyConfig(fileNameOption: Option[String] = None) {

    val cfg = fileNameOption.fold(ifEmpty = ConfigFactory.load())(file => ConfigFactory.load(file))

    def envOrElseConfig(name: String): String = {
      Properties.envOrElse(
        name.toUpperCase.replaceAll("""\.""", "_"),
        cfg.getString(name)
      )
    }

  }

  // actual config api
  private val config = new MyConfig()
  val properties = config.cfg

  override val dataFile: File = new File(getClass.getClassLoader.getResource(config.envOrElseConfig("graphics.collector.fileDataRepo")).getFile)


}