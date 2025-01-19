package repo.database.factory;

import domain.validator.IValidator;
import exceptions.RepoException;
import repo.database.ClientDataBaseRepo;
import repo.database.FlightDataBaseRepo;
import repo.database.TicketDataBaseRepo;
import repo.database.utils.AbstractDataBaseRepo;
import repo.database.utils.DataBaseAcces;

public class DataBaseRepoFactory implements IDataBaseFactory {
    private final DataBaseAcces data;

    /**
     * Constructor
     * @param data - the database access
     */
    public DataBaseRepoFactory(DataBaseAcces data ) {
        this.data = data;
    }

    /**
     * Method to create a repository
     * @param strategy - the strategy
     * @return AbstractDataBaseRepo
     */
    @Override
    public AbstractDataBaseRepo createRepo(EDataBaseStrategy strategy) {
        switch (strategy) {
            case Client -> {
                return new ClientDataBaseRepo(data, strategy.toString());
            }
            case Flight -> {
                return new FlightDataBaseRepo(data, strategy.toString());
            }
            case Ticket -> {
                return new TicketDataBaseRepo(data, strategy.toString());
            }
            default -> {
                throw new RepoException("Invalid strategy");
            }
        }
    }
}

