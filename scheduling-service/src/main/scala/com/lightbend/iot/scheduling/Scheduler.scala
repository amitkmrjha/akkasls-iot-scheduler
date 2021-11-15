
package com.lightbend.iot.scheduling

import com.akkaserverless.scalasdk.eventsourcedentity.{EventSourcedEntity, EventSourcedEntityContext}
import com.google.protobuf.empty.Empty
import com.google.protobuf.timestamp.Timestamp
import org.slf4j.LoggerFactory

import java.time.Instant

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

/** A value entity. */
class Scheduler(context: EventSourcedEntityContext) extends AbstractScheduler {
  val logger = LoggerFactory.getLogger(classOf[Scheduler])

  override def emptyState: SchedulerState = SchedulerState.of(None)

  override def schedule(currentState: SchedulerState, start: ScheduleNext): EventSourcedEntity.Effect[Empty] = {
    effects
      .emitEvent(SchedulerTicked.of(context.entityId, Some(Timestamp.of(Instant.now().getEpochSecond, 0))))
      .thenReply(_ => Empty.defaultInstance)
  }

  override def schedulerTicked(currentState: SchedulerState, schedulerStarted: SchedulerTicked): SchedulerState = SchedulerState.of(schedulerStarted.at)
}
