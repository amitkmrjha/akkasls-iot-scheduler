package com.lightbend.iot.scheduling

import com.akkaserverless.scalasdk.action.{Action, ActionCreationContext}
import com.google.protobuf.empty.Empty
import org.slf4j.LoggerFactory
import scala.concurrent.duration._

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

/** An action. */
class SchedulingTicksTopicAction(creationContext: ActionCreationContext) extends AbstractSchedulingTicksTopicAction {



  val logger = LoggerFactory.getLogger(classOf[SchedulingTicksTopicAction])
  /** Handler for "Publish". */
  override def publish(event: SchedulerTicked): Action.Effect[SchedulerTicked] = {
    logger.info(s"Publishing tick $event")
    effects.reply(event)
  }
  /** Handler for "Subscribe". */
  override def subscribe(event: SchedulerTicked): Action.Effect[Empty] = {

    creationContext.materializer().scheduleOnce(5.seconds, () => {
      logger.info(s"Scheduling next tick $event")
      creationContext.getGrpcClient(classOf[SchedulingService], "scheduler").schedule(ScheduleNext.of(event.id))
    })

    effects.reply(Empty.defaultInstance)
  }
}
