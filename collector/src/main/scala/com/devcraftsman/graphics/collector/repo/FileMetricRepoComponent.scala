package com.devcraftsman.graphics.collector.repo

import java.io.{BufferedWriter, File, FileOutputStream, OutputStreamWriter}

import com.devcraftsman.graphics.collector.metrics.Metric

/**
  * Created by devcraftsman on 4/7/17.
  * ----------------------------------------------------
  * This software is licensed under the Apache 2 license
  * see: [http://www.apache.org/licenses/LICENSE-2.0]
  **/
trait FileMetricRepoComponent extends MetricRepoComponent {

  val dataFile: File
  val repo = new FileMetricRepo(dataFile)
  var writer: BufferedWriter = null

  class FileMetricRepo(dataFile: => File) extends MetricRepo {
    override def append(m: Metric) = {
      writer.append(Metric.serialize(m))
      writer.flush()
    }

    override def init(): Unit = {
      writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dataFile, true), "UTF-8"))
    }

    override def close(): Unit = {
      if (writer != null) {
        writer.close()
      }
    }
  }

}
