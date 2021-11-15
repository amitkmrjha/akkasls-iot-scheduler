package com.lightbend.pubsub

import akka.actor.{ActorSystem, Cancellable}
import akka.stream.DelayOverflowStrategy
import akka.stream.alpakka.googlecloud.pubsub.grpc.scaladsl.GooglePubSub
import akka.stream.scaladsl.{Sink, Source}
import com.google.protobuf.ByteString
import com.google.pubsub.v1.pubsub.{PublishRequest, PubsubMessage, StreamingPullRequest}
import com.typesafe.config.ConfigFactory

import java.util.logging.{Level, Logger}
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.{Failure, Random, Success}

object TestPubSub {
  //env GOOGLE_APPLICATION_CREDENTIALS=/Users/amitkumar/Downloads/lbamitpubsub-6a074a97fe42.json sbt "runMain com.lightbend.pubsub.TestPubSub publish-stream test trip-events"
  Logger.getLogger("io.grpc.netty").setLevel(Level.ALL)

  def main(args: Array[String]): Unit = {

    val config = ConfigFactory.parseString("""
                                             |akka.loglevel = DEBUG
                                             |PUBSUB_EMULATOR_HOST=localhost
                                             |PUBSUB_EMULATOR_POR=8085
      """.stripMargin)

    implicit val sys = ActorSystem("TestPubSub", config)

    import sys.dispatcher

    val result = args.toList match {
      case "publish-single" :: rest => publishSingle(rest)
      case "publish-stream" :: rest => publishStream(rest)
      case "subscribe" :: rest => subscribeStream(rest)
      case other => Future.failed(new Error(s"unknown arguments: $other"))
    }

    result.onComplete { res =>
      res match {
        case Success(c: Cancellable) =>
          println("### Press ENTER to stop the application.")
          scala.io.StdIn.readLine()
          c.cancel()
        case Success(s) =>
          println(s)
        case Failure(ex) =>
          ex.printStackTrace()
      }
      sys.terminate()
    }
  }

  private def publishSingle(args: List[String])(implicit system: ActorSystem) = {
    val projectId :: topic :: Nil = args

    Source
      .single(publish(projectId, topic)("Hello!"))
      .via(GooglePubSub.publish(parallelism = 1))
      .runWith(Sink.head)
  }

  private def publishStream(args: List[String])(implicit system: ActorSystem) = {
    val projectId :: topic :: Nil = args

    Source
      .tick(0.seconds, 50.millisecond, ())
      .map(_ => {
        println(s"starting to publish ")
        getRandomTrip
      })
      .delay(1.second, DelayOverflowStrategy.backpressure)
      .map(publish(projectId, topic)(_))
      .via(GooglePubSub.publish(parallelism = 1))
      .to(Sink.foreach(e => println(s"published ${e}")))
      .mapMaterializedValue(Future.successful(_))
      .run()
  }

  private def subscribeStream(args: List[String])(implicit system: ActorSystem) = {
    val projectId :: sub :: Nil = args

    GooglePubSub
      .subscribe(subscribe(projectId, sub), 1.second)
      .to(Sink.foreach(println))
      .run()
  }

  private def publish(projectId: String, topic: String)(msg: String) = {
    PublishRequest(topicFqrs(projectId, topic), Seq(PubsubMessage(ByteString.copyFromUtf8(msg))))
  }

  private def subscribe(projectId: String, sub: String) =
    StreamingPullRequest(subFqrs(projectId, sub)).withStreamAckDeadlineSeconds(10)

  private def topicFqrs(projectId: String, topic: String) =
    s"projects/$projectId/topics/$topic"

  private def subFqrs(projectId: String, sub: String) =
    s"projects/$projectId/subscriptions/$sub"

  private def getRandomTrip = {
     s"""
       |{
       |     "trip_id": "${Random.nextInt(Integer.MAX_VALUE)}",
       |    "longitude":2 ,
       |    "latitude": 3 ,
       |    "place_id":"XXXXASDFFSFSFSFSFSFS",
       |    "destination_city_id":"london",
       |    "origin_city_id": "paris",
       |    "transport_name":"SG342",
       |    "transport_type": "flight"
       |}
       |""".stripMargin

  }
}
