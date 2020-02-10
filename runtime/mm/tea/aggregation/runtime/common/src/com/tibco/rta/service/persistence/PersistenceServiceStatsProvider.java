package com.tibco.rta.service.persistence;


import com.tibco.rta.model.stats.DBConnectionPoolStats;
import com.tibco.rta.model.stats.DBTransactionStats;

/**
 * A separate interface created to avoid polluting the main
 * persistence service interface with monitoring stuff.
 */
public interface PersistenceServiceStatsProvider {

    /**
     * Provide connection pool stats from persistence service.
     */
    public DBConnectionPoolStats getConnectionPoolStats();

    /**
     * Provide transaction stats from DB persistence service.
     */
    public DBTransactionStats getTransactionStats();
}
