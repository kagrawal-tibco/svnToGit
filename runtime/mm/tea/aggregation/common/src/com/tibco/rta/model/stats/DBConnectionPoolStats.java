package com.tibco.rta.model.stats;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 12/9/14
 * Time: 2:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class DBConnectionPoolStats {

    private int poolSize;

    private String poolState;

    private int connectionsInUse;

    private int availableConnections;

    public DBConnectionPoolStats(int poolSize, String poolState, int connectionsInUse, int availableConnections) {
        this.poolSize = poolSize;
        this.poolState = poolState;
        this.connectionsInUse = connectionsInUse;
        this.availableConnections = availableConnections;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public String getPoolState() {
        return poolState;
    }

    public int getConnectionsInUse() {
        return connectionsInUse;
    }

    public int getAvailableConnections() {
        return availableConnections;
    }
}
