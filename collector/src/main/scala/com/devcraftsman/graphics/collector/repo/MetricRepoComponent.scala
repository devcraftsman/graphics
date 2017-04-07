package com.devcraftsman.graphics.collector.repo

import com.devcraftsman.graphics.collector.metrics.Metric

/**
  * Created by devcraftsman on 4/7/17.
  * ----------------------------------------------------
  * This software is licensed under the Apache 2 license
  * see: [http://www.apache.org/licenses/LICENSE-2.0]
  **/
trait MetricRepoComponent {

  val repo: MetricRepo

  trait MetricRepo {

    def init(): Unit = {} // make NOP optional

    def close(): Unit = {} // make NOP optional

    def append(metric: Metric)
  }

}
