package com.tibco.be.oracle.impl;

import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Apr 28, 2008
 * Time: 1:46:38 AM
 * To change this template use File | Settings | File Templates.
 */
public interface OracleConnectionPoolMBean {

    public void switchToSecondary() throws SQLException, Exception;
    public void switchToPrimary() throws SQLException, Exception;
    public void recycle() throws SQLException;

    public int getCacheSize();
}
