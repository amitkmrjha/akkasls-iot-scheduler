package com.lightbend.iot.city.subscribe

import com.akkaserverless.scalasdk.action.Action
import com.akkaserverless.scalasdk.action.ActionCreationContext
import com.google.protobuf.empty.Empty
import com.lightbend.iot.city.api.{AddHashTagActivity, AddLocationInfo, AddWeatherActivity}

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

/** An action. */
class CityTopicSubscriptionAction(creationContext: ActionCreationContext) extends AbstractCityTopicSubscriptionAction {
  private val addLocationCallRef =
    creationContext.serviceCallFactory.lookup(
      "com.lightbend.iot.city.api.CityService",
      "AddLocation",
      classOf[AddLocationInfo])

  private val addWeatherCallRef =
    creationContext.serviceCallFactory.lookup(
      "com.lightbend.iot.city.api.CityService",
      "AddWeather",
      classOf[AddWeatherActivity])

  private val addHashTagCallRef =
    creationContext.serviceCallFactory.lookup(
      "com.lightbend.iot.city.api.CityService",
      "AddHashTag",
      classOf[AddHashTagActivity])


  /** Handler for "AddLocationViaTopic". */
  override def addLocationViaTopic(addLocationEvent: AddLocationEvent): Action.Effect[Empty] = {
    //throw new RuntimeException("The command handler for `AddLocationViaTopic` is not implemented, yet")
    val addLocationInfo = AddLocationInfo(
      cityId = addLocationEvent.cityId,
      longitude = addLocationEvent.longitude,
      latitude = addLocationEvent.latitude,
      placeId = addLocationEvent.placeId
    )
    effects.forward(addLocationCallRef.createCall(addLocationInfo))

  }
  /** Handler for "AddWeatherViaTopic". */
  override def addWeatherViaTopic(addWeatherEvent: AddWeatherEvent): Action.Effect[Empty] = {
    //throw new RuntimeException("The command handler for `AddWeatherViaTopic` is not implemented, yet")
    val addWeather = AddWeatherActivity(
      cityId = addWeatherEvent.cityId,
      weather = addWeatherEvent.weather
    )
    effects.forward(addWeatherCallRef.createCall(addWeather))
  }
  /** Handler for "AddHashTagViaTopic". */
  override def addHashTagViaTopic(addHashTagEvent: AddHashTagEvent): Action.Effect[Empty] = {
    //throw new RuntimeException("The command handler for `AddHashTagViaTopic` is not implemented, yet")
    val addHashTag = AddHashTagActivity(
      cityId = addHashTagEvent.cityId,
      hashTag = addHashTagEvent.hashTag
    )
    effects.forward(addHashTagCallRef.createCall(addHashTag))
  }
}
