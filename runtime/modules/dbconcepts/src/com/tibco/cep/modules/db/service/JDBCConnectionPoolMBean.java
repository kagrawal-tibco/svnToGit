package com.tibco.cep.modules.db.service;

import java.sql.SQLException;

public interface JDBCConnectionPoolMBean {
    //public void switchToSecondary() throws SQLException, Exception;
    //public void switchToPrimary() throws SQLException, Exception;
    public void recycle() throws SQLException;
    public int getCacheSize();
    public boolean getIsUsingPrimary();
    public String getPoolState();
    //public boolean isAutoFailover() ;
    //public String getFailoverInterval() ;
    public String getPrimaryURI();
    //public String getSecondaryURI();
    public void refreshConnections();
    public int getNumberOfConnectionsInUse();
    public int getNumberOfAvailableConnections();
}
