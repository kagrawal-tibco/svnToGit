package com.tibco.be.jdbcstore.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

public class DBConnectionPoolManager {

    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(DBConnectionPoolManager.class);
    private static HashMap jdbcCnxPools = new HashMap();

    static public void init() throws Exception {
        Collection jdbcpools = jdbcCnxPools.values();
        for (Iterator it = jdbcpools.iterator(); it.hasNext();) {
            com.tibco.be.jdbcstore.impl.DBConnectionPool pool = (com.tibco.be.jdbcstore.impl.DBConnectionPool) it.next();
            pool.init();
            logger.log(Level.INFO, "[%s] initialized successfully", pool);
        }
    }

    static public void start(int mode) throws Exception {

    }

    static public void addConnectionPool(String poolUri, DBConnectionPool pool) {
        synchronized (jdbcCnxPools) {
            jdbcCnxPools.put(poolUri, pool);
        }
    }

    static public void stop() {
        Collection jdbcpools = jdbcCnxPools.values();
        for (Iterator it = jdbcpools.iterator(); it.hasNext();) {
            com.tibco.be.jdbcstore.impl.DBConnectionPool pool = (com.tibco.be.jdbcstore.impl.DBConnectionPool) it.next();
            pool.close();
        }
    }

    static public Connection getDBConnection(String jdbcResourceName) throws SQLException {
        synchronized (jdbcCnxPools) {
            DBConnectionPool cnxPool = (com.tibco.be.jdbcstore.impl.DBConnectionPool) jdbcCnxPools.get(jdbcResourceName);
            if (cnxPool == null) {
                throw new RuntimeException("jdbcResource " + jdbcResourceName + " not found");
            }
            return cnxPool.getConnection();
        }
    }

    static public void relaseConnection(String jdbcResourceName, Connection cnx) {
        synchronized (jdbcCnxPools) {
            DBConnectionPool cnxPool = (com.tibco.be.jdbcstore.impl.DBConnectionPool) jdbcCnxPools.get(jdbcResourceName);
            if (cnxPool != null) {
                cnxPool.free(cnx);
            }
        }
    }

    static public int getConnectionStatus(String jdbcResourceName) {
        synchronized (jdbcCnxPools) {
            DBConnectionPool cnxPool = (com.tibco.be.jdbcstore.impl.DBConnectionPool) jdbcCnxPools.get(jdbcResourceName);
            if (cnxPool == null) {
                throw new RuntimeException("jdbcResource " + jdbcResourceName + " not found");
            }
            return cnxPool.getStatus();
        }
    }
}
