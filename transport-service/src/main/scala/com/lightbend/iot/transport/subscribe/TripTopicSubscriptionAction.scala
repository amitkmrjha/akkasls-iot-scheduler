package com.lightbend.iot.transport.subscribe

import com.akkaserverless.scalasdk.action.Action
import com.akkaserverless.scalasdk.action.ActionCreationContext
import com.google.protobuf.empty.Empty
import com.lightbend.iot.transport.api.{AddTripLeg, CompleteTripLeg}

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

/** An action. */
class TripTopicSubscriptionAction(creationContext: ActionCreationContext) extends AbstractTripTopicSubscriptionAction {
  private val addTripCallRef =
    creationContext.serviceCallFactory.lookup(
      "com.lightbend.iot.transport.api.TripService",
      "AddTrip",
      classOf[AddTripLeg])

  private val completeTripCallRef =
    creationContext.serviceCallFactory.lookup(
      "com.lightbend.iot.transport.api.TripService",
      "CompleteTrip",
      classOf[CompleteTripLeg])
  /** Handler for "AddTripViaTopic". */
  override def addTripViaTopic(addTripEvent: AddTripEvent): Action.Effect[Empty] = {
    //throw new RuntimeException("The command handler for `AddTripViaTopic` is not implemented, yet")
     val addTripLeg = AddTripLeg(
      tripId = addTripEvent.tripId,
      longitude = addTripEvent.longitude,
      latitude = addTripEvent.latitude,
      placeId = addTripEvent.placeId,
      destinationCityId = addTripEvent.destinationCityId,
      originCityId = addTripEvent.originCityId,
      transportName = addTripEvent.transportName,
      transportType = addTripEvent.transportType
    )
    effects.forward(addTripCallRef.createCall(addTripLeg))
  }
  /** Handler for "CompleteTripViaTopic". */
  override def completeTripViaTopic(completeTripEvent: CompleteTripEvent): Action.Effect[Empty] = {
    //throw new RuntimeException("The command handler for `CompleteTripViaTopic` is not implemented, yet")
    val completeTripLeg = CompleteTripLeg(tripId = completeTripEvent.tripId)
    effects.forward(completeTripCallRef.createCall(completeTripLeg))
  }
}
