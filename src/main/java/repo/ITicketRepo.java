package repo;

import domain.Client;
import domain.Ticket;

import java.time.LocalDate;
import java.util.List;

public interface ITicketRepo extends IRepository<Long, Ticket>{
    public List<Ticket> getAllTicketsByDate(LocalDate date);
}
