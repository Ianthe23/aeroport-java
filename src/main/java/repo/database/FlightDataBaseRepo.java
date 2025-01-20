package repo.database;

import domain.Flight;
import repo.IFlightRepo;
import repo.database.utils.AbstractDataBaseRepo;
import repo.database.utils.DataBaseAcces;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class FlightDataBaseRepo extends AbstractDataBaseRepo<Long, Flight> implements IFlightRepo {
    private final String GET_ALL_FROM = "SELECT from_city from flight GROUP BY from_city";
    private final String GET_ALL_TO = "SELECT to_city from flight GROUP BY to_city";
    private final String GET_FLIGHTS_FROM_DATE_AND_FROM_AND_TO = "SELECT * from flight WHERE (departureTime >= ? AND landingTime < ?) AND from_city = ? AND to_city = ?";
    private final String GET_ID_FROM_FLIGHT_FROM_TO_DATE = "SELECT id from flight WHERE from_city = ? AND to_city = ? AND departureTime = ? and landingTime = ?";
    private final String GET_FLIGHT_BY_ID = "SELECT * from flight WHERE id = ?";
    private final String UPDATE_SEATS_FROM_FLIGHT_BY_ID = "UPDATE flight SET seats = ? WHERE id = ?";
    private final String GET_ALL_FLIGHTS = "SELECT * from flight";


    public FlightDataBaseRepo(DataBaseAcces data, String table) {
        super(data, table);
    }

    @Override
    public Optional<Flight> findOne(Long aLong) {
        try (PreparedStatement statement = data.createStatement(GET_FLIGHT_BY_ID)) {
            statement.setLong(1, aLong);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Flight(
                        resultSet.getString("from_city"),
                        resultSet.getString("to_city"),
                        resultSet.getTimestamp("departureTime").toLocalDateTime(),
                        resultSet.getTimestamp("landingTime").toLocalDateTime(),
                        resultSet.getInt("seats")
                ));
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Flight> findAll() {
        List<Flight> flights = new ArrayList<>();
        try (PreparedStatement statement = data.createStatement(GET_ALL_FLIGHTS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Flight flight = new Flight(
                        resultSet.getString("from_city"),
                        resultSet.getString("to_city"),
                        resultSet.getTimestamp("departureTime").toLocalDateTime(),
                        resultSet.getTimestamp("landingTime").toLocalDateTime(),
                        resultSet.getInt("seats")
                );
                flights.add(flight);
            }
            return flights;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Flight> save(Flight entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Flight> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Flight> update(Flight entity) {
        try (PreparedStatement statement = data.createStatement(UPDATE_SEATS_FROM_FLIGHT_BY_ID)) {
            statement.setInt(1, entity.getSeats());
            statement.setLong(2, entity.getId());
            statement.executeUpdate();
            return Optional.of(entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getAllFrom() {
        List<String> froms = new ArrayList<>();
        try (PreparedStatement statement = data.createStatement(GET_ALL_FROM)) {
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                froms.add(resultSet.getString("from_city"));
            }
            return froms;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getAllTo() {
        List<String> tos = new ArrayList<>();
        try (PreparedStatement statement = data.createStatement(GET_ALL_TO)) {
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tos.add(resultSet.getString("to_city"));
            }
            return tos;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Flight> getFlightsFromDateAndFromAndTo(LocalDate date, String from, String to) {
        List<Flight> flights = new ArrayList<>();
        try (PreparedStatement statement = data.createStatement(GET_FLIGHTS_FROM_DATE_AND_FROM_AND_TO)) {
            statement.setTimestamp(1, Timestamp.valueOf(date.atStartOfDay()));
            statement.setTimestamp(2, Timestamp.valueOf(date.plusDays(1).atStartOfDay()));
            statement.setString(3, from);
            statement.setString(4, to);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Flight flight = new Flight(
                        resultSet.getString("from_city"),
                        resultSet.getString("to_city"),
                        resultSet.getTimestamp("departureTime").toLocalDateTime(),
                        resultSet.getTimestamp("landingTime").toLocalDateTime(),
                        resultSet.getInt("seats")
                );
                flights.add(flight);
            }
            return flights;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long getId(LocalDateTime departureTime, LocalDateTime landingTime, String from, String to) {
        List<Long> ids = new ArrayList<>();
        try (PreparedStatement statement = data.createStatement(GET_ID_FROM_FLIGHT_FROM_TO_DATE)) {
            statement.setString(1, from);
            statement.setString(2, to);
            statement.setTimestamp(3, Timestamp.valueOf(departureTime));
            statement.setTimestamp(4, Timestamp.valueOf(landingTime));
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getLong("id");
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
