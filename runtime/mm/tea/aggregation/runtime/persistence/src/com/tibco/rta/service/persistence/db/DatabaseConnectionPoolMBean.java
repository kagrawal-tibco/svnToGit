package com.tibco.rta.service.persistence.db;

public interface DatabaseConnectionPoolMBean {
    public int getConnectionPoolSize();
    public String getPoolState();
    public void refreshConnections();
    public int getConnectionsInUse();
    public int getAvailableConnections();
}