package com.tibco.store.persistence.model;


/**
 * Clients need to implement this
 */
public interface TableFactory {

    public MemTable<?> createTable();
       
}
