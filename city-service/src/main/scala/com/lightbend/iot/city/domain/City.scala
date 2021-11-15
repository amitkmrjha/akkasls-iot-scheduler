
package com.lightbend.iot.city.domain

import com.akkaserverless.scalasdk.valueentity.ValueEntity
import com.akkaserverless.scalasdk.valueentity.ValueEntityContext
import com.google.protobuf.empty.Empty
import com.lightbend.iot.city.api

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

/** A value entity. */
class City(context: ValueEntityContext) extends AbstractCity {
  override def emptyState: CityState = CityState()

  override def addLocation(currentState: CityState, addLocationInfo: api.AddLocationInfo): ValueEntity.Effect[Empty] = {
   //effects.error("The command handler for `AddLocation` is not implemented, yet")
   val updatedState = currentState.copy(
     cityId = addLocationInfo.cityId,
     longitude = addLocationInfo.longitude,
     latitude = addLocationInfo.latitude,
     placeId = addLocationInfo.placeId
   )
    effects.updateState(updatedState).thenReply(Empty())
  }

  override def addWeather(currentState: CityState, addWeatherActivity: api.AddWeatherActivity): ValueEntity.Effect[Empty] = {
    //effects.error("The command handler for `AddWeather` is not implemented, yet")
    val updatedState = currentState.copy(
      weather = addWeatherActivity.weather
    )
    effects.updateState(updatedState).thenReply(Empty())
  }

  override def addHashTag(currentState: CityState, addHashTagActivity: api.AddHashTagActivity): ValueEntity.Effect[Empty] = {
    //effects.error("The command handler for `AddHashTag` is not implemented, yet")
    val updatedState = currentState.copy(
      hashTag = addHashTagActivity.hashTag
    )
    effects.updateState(updatedState).thenReply(Empty())
  }

  override def get(currentState: CityState, getCity: api.GetCity): ValueEntity.Effect[api.CurrentCity] = {
  //effects.error("The command handler for `Get` is not implemented, yet")
    effects.reply(convertToApi(currentState))
  }



  private def convertToApi(state: CityState) =
    api.CurrentCity(
      cityId = state.cityId,
      longitude = state.longitude,
      latitude = state.latitude,
      placeId = state.placeId,
      weather = state.weather,
      hashTag = state.hashTag
    )
}
