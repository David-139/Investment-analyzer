package com.Model;

/**
 * Interface for persistence of data, saving and loading
 */

public interface PersistenceManager {
    void saveAll(Evidence evidence) throws PersistenceException;
    Evidence loadAll() throws PersistenceException;
    String getFileName();
}
