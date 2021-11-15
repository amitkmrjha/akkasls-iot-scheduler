import Dependencies._

ThisBuild / scalaVersion     := "2.13.6"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.lightbend"
ThisBuild / organizationName := "Lightbend"

lazy val root =
  (project in file(".")).settings(
    name := "Api Helper Project",
    libraryDependencies ++=
      Seq(
        "com.typesafe"                   % "config"     % "1.4.1",
        "com.softwaremill.sttp.client3" %% "core"       % "3.3.16",
        "io.spray"                      %% "spray-json" % "1.3.6",
        scalaTest                        % Test))

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
