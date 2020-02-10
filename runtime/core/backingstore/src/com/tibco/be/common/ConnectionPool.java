/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.common;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ddtek.jdbc.extensions.ExtEmbeddedConnection;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;

public abstract class ConnectionPool {
    
    public static int CONNECTED = 1;
    public static int DISCONNECTED = 0;
    private static final String DATADIRECT_LICENSE_CODE = "TIBADB123QQQQQQQQQQQQQQQ";
    
    private int status = DISCONNECTED;
    private ArrayList<BackingStore.ConnectionPoolListener> lsnrs = new ArrayList<BackingStore.ConnectionPoolListener> ();
    
    public abstract Connection getConnection() throws SQLException;
    public abstract boolean isAlive();
    
    public synchronized int getStatus() {
        return status;
    }

    public synchronized void setStatus(int status) {
        this.status = status;
    }

    public void addConnectionListener(BackingStore.ConnectionPoolListener lsnr) {
        if (!lsnrs.contains(lsnr)) {
            lsnrs.add(lsnr);
        }
    }

    public void disconnected() {
        for (int i=0; i < lsnrs.size(); i++) {
            lsnrs.get(i).disconnected();
        }
    }
    
    public void reconnected() {
        for (int i=0; i < lsnrs.size(); i++) {
            lsnrs.get(i).reconnected();
        }
    }
    
    /**
     * Unlocks a DataDirect connection using the default license key.
     * @param connection
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public static boolean unlockDDConnection(Connection connection) throws SQLException {
    	if (connection instanceof ExtEmbeddedConnection) {
    		return ((ExtEmbeddedConnection) connection).unlock(DATADIRECT_LICENSE_CODE);
    	}
    	return false;
    }
}
