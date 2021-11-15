package com.lightbend.iot.transport.helper
import spray.json._
import DefaultJsonProtocol._
import com.typesafe.config.ConfigFactory
import sttp.client3._

case class Aircraft(iataCode: String, icao24: String, icaoCode: String, regNumber: String)

case class Airline(iataCode: String, icaoCode: String)

case class Flight(iataNumber: String, icaoNumber: String, number: String)

case class Geography(altitude: Double, direction: Double, latitude: Double, longitude: Double)

case class Speed(horizontal: Double, isGround: Int, vspeed: Double)

case class System(squawk: Option[String], updated: Option[Int])

case class FlightDetails(
    aircraft: Aircraft,
    airline: Airline,
    arrival: Airline,
    departure: Airline,
    flight: Flight,
    geography: Geography,
    speed: Speed,
    status: String,
    system: System)

object FlightDetailsHelper {
  implicit val aircraftFormat       = jsonFormat4(Aircraft)
  implicit val airlineFormat        = jsonFormat2(Airline)
  implicit val flightFormat         = jsonFormat3(Flight)
  implicit val geographyFormat      = jsonFormat4(Geography)
  implicit val speedFormat          = jsonFormat3(Speed)
  implicit val systemFormat         = jsonFormat2(System)
  implicit val flightsDetailsFormat = jsonFormat9(FlightDetails)

  val key = ConfigFactory.load().getString("aviation.key")

  val backend = HttpURLConnectionBackend()

  def getAllFlights(departureIota: String, arrivalIota: String): Option[Seq[FlightDetails]] = {

    val request = basicRequest.get(
      uri"http://aviation-edge.com/v2/public/flights?key=$key&depIata=$departureIota&arrIata=$arrivalIota&status=en-route")

    val result = backend.send(request)
    result.body.toOption.map(body => body.parseJson.convertTo[List[FlightDetails]])
  }
}
