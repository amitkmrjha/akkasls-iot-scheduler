package com.lightbend.iot.transport.helper

object Test extends App {
  val flights: Seq[FlightDetails] =
    FlightDetailsHelper.getAllFlights("LHR", "JFK").getOrElse(Seq.empty[FlightDetails])

  flights
    .map { fd =>
      (fd.departure.iataCode, fd.arrival.iataCode, fd.geography.longitude, fd.geography.latitude)
    }
    .foreach { case (dep, arr, long, lat) =>
      println(s"Flight from $dep to $arr is at $long, $lat")
    }
}
