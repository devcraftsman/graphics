import Dependencies._

name := "graphics"

lazy val commonSettings = Seq(
  organization := "com.devcraftsman",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.12.1"
)


lazy val root = (project in file("."))
  .aggregate(collector, render)

lazy val collector = (project in file("collector"))
  .settings(
    name := "graphics-collector",
    commonSettings,
    libraryDependencies ++= Seq(logback, slf4j, scalaLogging),
    libraryDependencies ++= Seq(hsqlDb, hikariCP, slick),
    libraryDependencies ++= Seq(akkaHttp, akkaHttpTest),
    libraryDependencies ++= Seq(scalactic, scalaTest),
    mainClass in(Compile, run) := Some("com.devcraftsman.graphics.collector.Main")

  )

lazy val render = (project in file("render"))
  .settings(
    name := "graphics-render",
    commonSettings
  )

    