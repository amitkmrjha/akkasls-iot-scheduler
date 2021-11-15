import Dependencies._

ThisBuild / scalaVersion     := "2.13.6"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.lightbend"
ThisBuild / organizationName := "Lightbend"
val AkkaVersion = "2.6.17"
lazy val root =
  (project in file(".")).settings(
    name := "gpubsub",
    libraryDependencies ++=
      Seq(
        "com.lightbend.akka" %% "akka-stream-alpakka-google-cloud-pub-sub-grpc" % "3.0.3",
        "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
        "com.typesafe.akka" %% "akka-discovery" % AkkaVersion,
        scalaTest                        % Test),
    Runtime / envVars := Map("PUBSUB_EMULATOR_HOST" -> "localhost", "PUBSUB_EMULATOR_PORT" -> "8085")
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
