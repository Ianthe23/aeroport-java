package repo;

import domain.Client;

public interface IClientRepo extends IRepository<Long, Client>{
    Client findByUsername(String username);
}
