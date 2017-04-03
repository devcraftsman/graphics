package com.devcraftsman.graphics.collector.metrics

import java.io._


/**
  * Created by devcraftsman on 3/31/17.
  * ----------------------------------------------------
  * This software is licensed under the Apache 2 license
  * see: [http://www.apache.org/licenses/LICENSE-2.0]
  **/
trait MetricRepo[A] {

  def append(m: Metric)(implicit a: A): Unit;

}

object MetricRepo {

  class FileMetricRepo extends MetricRepo[File] {
    override def append(m: Metric)(implicit fileRepo: File) = {
      val writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileRepo, true), "UTF-8"))
      writer.append(Metric.serialize(m))
      writer.flush()
      writer.close()
    }
  }

  val fileRepo: MetricRepo[File] = new FileMetricRepo()
}

