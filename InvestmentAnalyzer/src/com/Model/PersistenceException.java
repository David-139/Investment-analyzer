package com.Model;

/**
 * throws error while saving/loading data
 */
public class PersistenceException extends Exception {
    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
