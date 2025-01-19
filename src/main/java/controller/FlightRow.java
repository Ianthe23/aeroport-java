package controller;

import domain.Flight;

public class FlightRow {
    private final Long flightId;
    private final Flight flight;

    public FlightRow(Long flightId, Flight flight) {
        this.flightId = flightId;
        this.flight = flight;
    }

    public Long getFlightId() {
        return flightId;
    }

    public Flight getFlight() {
        return flight;
    }
}
