package com.tibco.be.oracle.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Dec 10, 2006
 * Time: 12:17:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class OracleConnectionCache {
    
    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(OracleConnectionCache.class);  
    static Map m_dataSources = Collections.synchronizedMap(new LinkedHashMap());
    //public static int POOL_SIZE=10;
    //public static int POOL_SIZE=-1;
    static Map m_connectionPool = Collections.synchronizedMap(new LinkedHashMap());
    static boolean m_expandPool = Boolean.valueOf(System.getProperty("be.engine.oracle.expandPoolOnRequest", "false")).booleanValue();

    /**
     *
     * @param key
     * @param oracleDataSource
     */
    public static void registerConnection(String key, OracleDataSource oracleDataSource, int poolSize) throws SQLException,RuntimeException   {
        synchronized(m_dataSources) {
            if (!m_dataSources.containsKey(key)) {
                m_dataSources.put(key, oracleDataSource);
                //m_connectionPool.put(key, createConnections(oracleDataSource,poolSize));
                ConnectionPool cnxPool=new ConnectionPool(oracleDataSource,poolSize);
                try {
                    cnxPool.connect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                m_connectionPool.put(key, cnxPool);
            }
        }
    }

    /**
     *
     * @param key
     * @return
     */
    public static boolean isRegistered(String key) {
        synchronized(m_dataSources) {
            return m_dataSources.containsKey(key);
        }
    }

    /**
     *
     * @param key
     * @return
     * @throws SQLException
     */
    //timeout -1 means infinite timeout
    public static PooledConnection getConnection(String key, long timeout) throws SQLException{
        ConnectionPool pool= (ConnectionPool) m_connectionPool.get(key);
        return pool.getConnection(timeout);
    }

    public static class ConnectionPool {
        ArrayList connections;
        boolean isConnected = false;
        OracleDataSource oracleDataSource;
        int poolSize;
        int openCount;

        ConnectionPool(OracleDataSource oracleDataSource, int poolSize ) {
            this.oracleDataSource = oracleDataSource;
            this.poolSize = poolSize;
            connections = new ArrayList(poolSize);
        }

        protected void close() {

            for (int i=0; i < connections.size(); i++) {
                PooledConnection cnxn = (PooledConnection) connections.get(i);
                if (cnxn != null) {
                    try {
                        cnxn.connection.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            connections.clear();
            openCount=0;
        }

        //timeout -1 is for infinite timeout
        public  PooledConnection getConnection(long timeout) throws SQLException{
            long timeToWait = timeout;
            while (true) {
                synchronized(connections) {
                    for (int i=0; i < connections.size(); i++) {
                        PooledConnection pc = (PooledConnection)connections.get(i);
                        if (pc.canUse()) {
                            return pc;
                        }
                    }
                    if (m_expandPool) {
                        //logger.log(Level.DEBUG, "Number of connections=" + m_connectionPool.size() + " : " + connections.size() + " , adding a connection");
                        OracleConnection sqlConnection = addConnection();
                        PooledConnection pc = new PooledConnection(sqlConnection, connections,oracleDataSource);
                        pc.getConnection();
                        connections.add(pc);
                        ++openCount;
                        return pc;
                    } else if (timeout == -1 || timeToWait > 0) {
                        logger.log(Level.DEBUG, "%s: Waiting for connection ...", Thread.currentThread());
                        if (timeout == -1) {
                            try {
                                connections.wait();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            long start = System.currentTimeMillis();
                            try {
                                connections.wait(timeToWait);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            long elapsed = System.currentTimeMillis() - start;
                            timeToWait -= elapsed;
                            // Check elapsed < 0 in case clock has been moved backwards
                            if (elapsed < 0 || timeToWait < 0) {
                                timeToWait = 0;
                            }
                        }
                        logger.log(Level.DEBUG, "Retrying, checking for free connection ...");
                    } else {
                        return  null;
                    }
                }
            }
        }

        protected OracleConnection addConnection() throws SQLException {
            OracleConnection cnx = (OracleConnection) oracleDataSource.getConnection();
            cnx.setStatementCacheSize(100);
            cnx.setAutoCommit(false);
            return cnx;
        }

        protected boolean connect() throws Exception{
            for (int i=0; i < poolSize; i++) {
                System.out.println("CONNECTING " + oracleDataSource.getURL() + "," + oracleDataSource.getUser());
                try {
                    OracleConnection sqlConnection = addConnection();
                    connections.add(new PooledConnection(sqlConnection, connections,oracleDataSource));
                    ++openCount;
                } catch (SQLException sqex) {
                    sqex.printStackTrace();
                    break;
                }
            }
            if (openCount > 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static class PooledConnection {
        OracleConnection connection;
        boolean isAvailable = true;
        Object lock;
        OracleDataSource ds;

        PooledConnection(OracleConnection connection, Object lock, OracleDataSource ds) {
            this.connection = connection;
            this.lock = lock;
            this.ds = ds;
        }

        public OracleConnection getConnection () {
            if (connection == null) {
                boolean isReconnected = reconnect();
                if (!isReconnected) {
                    throw new RuntimeException("Unable to get the Oracle Connection");
                }
            }
            isAvailable = false;
            return connection;
        }

        public void releaseConnection() {
            synchronized(this) {
                isAvailable = true;
            }
            synchronized(lock) {
                lock.notify();
            }
            logger.log(Level.DEBUG, "Connection released ...");
        }

        synchronized boolean canUse() {
            if (isAvailable) {
                isAvailable = false;
                return true;
            }
            else {
                return false;
            }
        }

        synchronized public boolean isConnectionClosed() {
            try {
                if (connection == null) {
                    return true;
                }
                if (connection.isClosed()) {
                    connection = null;
                    return true;
                }
                PreparedStatement pstmt = connection.prepareStatement("SELECT sysdate from DUAL");
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                }
                rs.close();
                pstmt.close();
                return false;
            } catch (Exception ex) {
                connection=null;
                ex.printStackTrace();
                return true;
            }
        }

        private boolean reconnect() {
            try {
                logger.log(Level.DEBUG, "%s: Reconnecting ...", Thread.currentThread());
                if (connection != null)
                    connection.close();
                connection = (OracleConnection) ds.getConnection();
                connection.setStatementCacheSize(100);
                connection.setAutoCommit(false);
                return true;
            } catch (Exception ex) {
                connection = null;
                return false;
            }
        }
    }
}
