package service;

import domain.Client;
import domain.Flight;
import domain.Ticket;
import exceptions.ServiceException;
import repo.IClientRepo;
import repo.IFlightRepo;
import repo.IRepository;
import repo.ITicketRepo;
import utils.events.AeroportEvent;
import utils.events.EEventType;
import utils.observer.IObservable;
import utils.observer.IObserver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AeroportService implements IService<Integer>, IObservable<AeroportEvent> {
    private List<IObserver<AeroportEvent>> observers = new ArrayList<>();
    private final IClientRepo clientRepo;
    private final IFlightRepo flightRepo;
    private final ITicketRepo ticketRepo;

    public AeroportService(IClientRepo clientRepo, IFlightRepo flightRepo, ITicketRepo ticketRepo) {
        this.clientRepo = clientRepo;
        this.flightRepo = flightRepo;
        this.ticketRepo = ticketRepo;
    }

    @Override
    public void addObserver(IObserver<AeroportEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(IObserver<AeroportEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(AeroportEvent aeroportEvent) {
        observers.forEach(x -> x.update(aeroportEvent));
    }

    public Client logClient (String username) {
        try {
            Client client = clientRepo.findByUsername(username);
            return client;
        } catch (Exception e) {
            throw new ServiceException("User not found");
        }
    }

    public Iterable<Ticket> getTicketsByUsername(String username) {
        Iterable<Ticket> list = ticketRepo.findAll();
        List<Ticket> tickets = new ArrayList<>();
        list.forEach(tickets::add);
        List<Ticket> result = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getUsername().equals(username)) {
                result.add(ticket);
            }
        }
        return result;
    }

    public List<Ticket> getAllTicketsByDate(LocalDate date) {
        return ticketRepo.getAllTicketsByDate(date);
    }

    public List<String> getAllFromFlights() {
        return flightRepo.getAllFrom();
    }

    public List<String> getAllToFlights() {
        return flightRepo.getAllTo();
    }

    public List<Flight> getFlightsByDateAndFromTo(LocalDate date, String from, String to) {
        return flightRepo.getFlightsFromDateAndFromAndTo(date, from, to);
    }

    public Long getIds(LocalDateTime departureTime, LocalDateTime landingTime, String from, String to) {
        return flightRepo.getId(departureTime, landingTime, from, to);
    }

    public Ticket addTicket(Ticket ticket) {
        Optional<Flight> flight = flightRepo.findOne(ticket.getFlightId());
        if (flight.get().getSeats() == 0) {
            throw new ServiceException("No more seats available");
        }

        Flight foundFlight = flight.get();
        foundFlight.setSeats(foundFlight.getSeats() - 1);
        foundFlight.setId(ticket.getFlightId());
        flightRepo.update(foundFlight);

        Optional<Ticket> addedTicket = ticketRepo.save(ticket);
        notifyObservers(new AeroportEvent(EEventType.BOUGHT_TICKET, ticket));

        return addedTicket.get();
    }
}
