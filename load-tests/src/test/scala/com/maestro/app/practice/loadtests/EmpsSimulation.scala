package com.maestro.app.practice.loadtests

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.language.postfixOps

class EmpsSimulation extends Simulation {
  val rampUpTimeSecs = 5
  val testTimeSecs   = 20
  val noOfUsers      = 1000
  val minWaitMs      = 300 milliseconds
  val maxWaitMs      = 3000 milliseconds
  val baseName       = "loadtests"

  val httpProtocol = http
    .baseUrl("http://localhost:8802")
    .inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.(t|o)tf""", """.*\.png"""), WhiteList())

  val uri1 = "localhost"

  val http_headers = Map(
    "Accept-Encoding" -> "gzip,deflate",
    "Content-Type" -> "text/json;charset=UTF-8",
    "Keep-Alive" -> "115")
  val scn = scenario(baseName + "-emps-scenario")
    .during(testTimeSecs) {
      exec(
        http(baseName + "-list-request")
          .get("/emp")
          .headers(http_headers)
          .check(status.is(200))
      )
        .pause(minWaitMs, maxWaitMs)

        .exec(
          http(baseName + "-rec-request")
            .get("/emp/1")
            .headers(http_headers)
            .check(status.is(200))
            .check(jsonPath("$.id").exists)
        )
        .pause(minWaitMs, maxWaitMs)
    }
  setUp(scn.inject(rampUsers(noOfUsers) during (rampUpTimeSecs seconds))).protocols(httpProtocol)
}
