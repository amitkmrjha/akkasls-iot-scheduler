
package com.lightbend.iot.transport.domain

import com.akkaserverless.scalasdk.valueentity.ValueEntity
import com.akkaserverless.scalasdk.valueentity.ValueEntityContext
import com.google.protobuf.empty.Empty
import com.lightbend.iot.transport.api

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

/** A value entity. */
class Trip(context: ValueEntityContext) extends AbstractTrip {
  override def emptyState: TripState = TripState()

  override def addTrip(currentState: TripState, addTripLeg: api.AddTripLeg): ValueEntity.Effect[Empty] = {
    //effects.error("The command handler for `AddTrip` is not implemented, yet")
    val state = convertToDomain(addTripLeg)
    effects.updateState(state).thenReply(Empty())

  }

  override def completeTrip(currentState: TripState, completeTripLeg: api.CompleteTripLeg): ValueEntity.Effect[Empty] = {
    //effects.error("The command handler for `CompleteTrip` is not implemented, yet")
    val updatedState = currentState.copy(isActive = false)
    effects.updateState(updatedState).thenReply(Empty())
  }

  override def getTrip(currentState: TripState, getTripLeg: api.GetTripLeg): ValueEntity.Effect[api.Trip] = {
    //effects.error("The command handler for `GetTrip` is not implemented, yet")
    effects.reply(convertToApi(currentState))
  }

  private def convertToDomain(tripInfo: api.AddTripLeg) =
    TripState(tripId = tripInfo.tripId,
      longitude = tripInfo.longitude,
      latitude = tripInfo.latitude,
      placeId = tripInfo.placeId,
      destinationCityId = tripInfo.destinationCityId,
      originCityId = tripInfo.originCityId,
      transportName = tripInfo.transportName,
      transportType = tripInfo.transportType,
      isActive = true

    )

  private def convertToApi(state: TripState) =
    api.Trip(tripId = state.tripId,
      longitude = state.longitude,
      latitude = state.latitude,
      placeId = state.placeId,
      destinationCityId = state.destinationCityId,
      originCityId = state.originCityId,
      transportName = state.transportName,
      transportType = state.transportType,
      isActive = state.isActive)

}
