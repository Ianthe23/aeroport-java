package repo.database.utils;

import domain.Entity;
import domain.validator.IValidator;
import repo.IRepository;

public abstract class AbstractDataBaseRepo <ID, E extends Entity<ID>> implements IRepository<ID, E> {
    protected DataBaseAcces data;
    protected String table;

    /**
     * Constructor
     * @param data - the database access
     * @param table - the table name
     */
    public AbstractDataBaseRepo(DataBaseAcces data, String table) {
        this.data = data;
        this.table = table;
    }

    /**
     * Constructor
     */
    public AbstractDataBaseRepo() {
        super();
    }
}
