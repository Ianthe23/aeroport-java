package repo;

import domain.Flight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IFlightRepo extends IRepository<Long, Flight>{
    public List<String> getAllFrom();
    public List<String> getAllTo();
    public List<Flight> getFlightsFromDateAndFromAndTo(LocalDate date, String from, String to);
    public Long getId(LocalDateTime departureTime, LocalDateTime landingTime, String from, String to);
}
