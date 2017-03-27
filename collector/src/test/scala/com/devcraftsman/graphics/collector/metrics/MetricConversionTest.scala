package com.devcraftsman.graphics.collector.metrics

/**
  * Created by devcraftsman on 3/27/17.
  * ----------------------------------------------------
  * This software is licensed under the Apache 2 license
  * see: [http://www.apache.org/licenses/LICENSE-2.0]
  **/

import org.scalatest._

import scala.util.Success

class MetricConversionTest extends WordSpec with Matchers {

  "Metric parse" must {

    "convert correct string to valid Metric instance" in {
      val expected = Metric("test.metric.rate", (1490622061L, 5.3f))
      val input = "test.metric.rate 1490622061 5.30"

      val parsedTry = Metric.parse(input)

      parsedTry shouldBe (Success(expected))
      parsedTry.get shouldEqual (expected)

    }

    "throw error when empty String is passed" in {

      val input = ""

      val parsedTry = Metric.parse(input)

      parsedTry.isFailure shouldEqual (true)


    }

    "throw error when invalid String is passed" in {

      val input = "dgwhpibunwpbwnbi"

      val parsedTry = Metric.parse(input)

      parsedTry.isFailure shouldEqual (true)


    }

  }
}
