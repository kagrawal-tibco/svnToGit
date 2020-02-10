package com.tibco.be.jdbcstore.impl;

import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: agupta
 * Date: May 5, 2008
 * Time: 2:58:29 PM
 */
public interface DBConnectionPoolMBean {
    //public void switchToSecondary() throws SQLException, Exception;
    //public void switchToPrimary() throws SQLException, Exception;
    public void recycle() throws SQLException;
    public int getCacheSize();
    //public boolean getIsUsingPrimary();
    public String getPoolState();
    //public boolean isAutoFailover() ;
    //public String getFailoverInterval() ;
    public String getPrimaryURI();
    //public String getSecondaryURI();
    public void refreshConnections();
    public int getNumberOfConnectionsInUse();
    public int getNumberOfAvailableConnections();
    //public void refreshSecondary();
}
