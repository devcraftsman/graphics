
import sbt._

object Dependencies {
  lazy val httpVersion = "10.0.5"
  lazy val scalaTestVersion = "3.0.1"


  val akkaHttp = "com.typesafe.akka" %% "akka-http" % httpVersion
  val akkaHttpTest = "com.typesafe.akka" % "akka-http-testkit_2.12" % httpVersion % "test"
  val scalactic = "org.scalactic" %% "scalactic" % scalaTestVersion
  val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
}