package repo.database;

import domain.Ticket;
import repo.ITicketRepo;
import repo.database.utils.AbstractDataBaseRepo;
import repo.database.utils.DataBaseAcces;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketDataBaseRepo extends AbstractDataBaseRepo<Long, Ticket> implements ITicketRepo {
    private final String GET_ALL_TICKETS = "SELECT username, flight_id, purchaseTime FROM Ticket";
    private final String GET_ALL_TICKETS_BY_DATE = "SELECT username, flight_id, purchaseTime FROM Ticket WHERE (purchaseTime >= ? AND purchaseTime < ?)";
    private final String INSERT_TICKET = "INSERT INTO Ticket (username, flight_id, purchaseTime) VALUES (?, ?, ?)";

    private static Ticket getTicket(ResultSet resultSet) throws SQLException {
        String username = resultSet.getString("username");
        Long flightId = resultSet.getLong("flight_id");
        Timestamp purchaseTimeTs = resultSet.getTimestamp("purchaseTime");
        LocalDateTime purchaseTime = purchaseTimeTs != null ? purchaseTimeTs.toLocalDateTime() : null;
        return new Ticket(username, flightId, purchaseTime);
    }

    private List<Ticket> getList(String sql) {
        List<Ticket> tickets = new ArrayList<>();
        try (PreparedStatement statement = data.createStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                tickets.add(getTicket(resultSet));
            }
            return tickets;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public TicketDataBaseRepo(DataBaseAcces data, String table) {
        super(data, table);
    }

    @Override
    public Optional<Ticket> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Iterable<Ticket> findAll() {
        return getList(GET_ALL_TICKETS);
    }

    @Override
    public Optional<Ticket> save(Ticket entity) {
        try (PreparedStatement statement = data.createStatement(INSERT_TICKET)) {
            statement.setString(1, entity.getUsername());
            statement.setLong(2, entity.getFlightId());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getPurchaseTime()));
            statement.execute();
            return Optional.of(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Ticket> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Ticket> update(Ticket entity) {
        return Optional.empty();
    }

    @Override
    public List<Ticket> getAllTicketsByDate(LocalDate date) {
        List<Ticket> tickets = new ArrayList<>();
        try (PreparedStatement statement = data.createStatement(GET_ALL_TICKETS_BY_DATE)) {
            statement.setTimestamp(1, Timestamp.valueOf(date.atStartOfDay()));
            statement.setTimestamp(2, Timestamp.valueOf(date.plusDays(1).atStartOfDay()));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tickets.add(getTicket(resultSet));
            }
            return tickets;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
