package repo.database;

import domain.Client;
import domain.validator.IValidator;
import exceptions.RepoException;
import repo.IClientRepo;
import repo.database.utils.AbstractDataBaseRepo;
import repo.database.utils.DataBaseAcces;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ClientDataBaseRepo extends AbstractDataBaseRepo<Long, Client> implements IClientRepo {
    private static final String GET_ONE_BY_USERNAME = "SELECT * FROM Client WHERE username = ?";
    private static final String GET_ONE_BY_ID = "SELECT * FROM Client WHERE id = ?";

    public ClientDataBaseRepo(DataBaseAcces data, String table) {
        super(data, table);
    }

    private static Client getClient(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String username = resultSet.getString("username");
        String name = resultSet.getString("name");

        return new Client(username, name);
    }

    @Override
    public Client findByUsername(String username) {
        try {
            PreparedStatement statement = data.createStatement(GET_ONE_BY_USERNAME);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getClient(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Client> findOne(Long aLong) {
        try {
            PreparedStatement statement = data.createStatement(GET_ONE_BY_ID);
            statement.setLong(1, aLong);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getClient(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Client> findAll() {
        return null;
    }

    @Override
    public Optional<Client> save(Client entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Client> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Client> update(Client entity) {
        return Optional.empty();
    }
}
