
import sbt._

object Dependencies {
  lazy val httpVersion = "10.0.5"
  lazy val scalaTestVersion = "3.0.1"
  lazy val hsqldbVersion = "2.3.4"
  lazy val slickVersion = "3.2.0"
  lazy val slf4jVersion = "1.6.4"

  // db
  val hsqlDb = "org.hsqldb" % "hsqldb" % hsqldbVersion
  val slick = "com.typesafe.slick" %% "slick" % slickVersion
  val slf4j = "org.slf4j" % "slf4j-nop" % slf4jVersion
  val hikariCP = "com.typesafe.slick" %% "slick-hikaricp" % slickVersion


  val akkaHttp = "com.typesafe.akka" %% "akka-http" % httpVersion
  // test
  val akkaHttpTest = "com.typesafe.akka" % "akka-http-testkit_2.12" % httpVersion % "test"
  val scalactic = "org.scalactic" %% "scalactic" % scalaTestVersion % "test"
  val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
}