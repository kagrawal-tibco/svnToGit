/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.oracle.impl;

import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.tibco.cep.runtime.service.cluster.backingstore.GenericBackingStore;
import oracle.jdbc.OracleConnection;

import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.util.DBConnectionsBusyException;

/**
 * Created by IntelliJ IDEA.
 * User: agupta
 * Date: May 05, 2008
 * Time: 1:57:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class OracleActiveConnectionPool implements OracleActiveConnectionPoolMBean {

    private String key = null;

    private Map defaultEntityPropsMap;
    private Map<OracleConnection, Map> entityPropertiesMapByConn = new HashMap<OracleConnection, Map>();
    private Map defaultOracleTypesMap;
    private Map<OracleConnection, Map> oracleTypes2DescriptionByConn = new HashMap<OracleConnection, Map>();

    // Retry interval is in seconds
    private int retryInterval;
    private boolean recreateOnRecovery = false;

    // Failure-duration after which the pool automatically switches over to secondary.
    private int failoverInterval;
    private boolean autoFailover = false;

    private OracleConnectionPool primaryPool = null;
    private OracleConnectionPool ftPool = null;
    // Need to store FT URI too, since FT pool may not have got created until later.
    private String ftKey = null;
    private boolean usingPrimary = true;

    // Whichever pool is being used currently, primary or secondary, is it usable or there are connection issues
    private long disconnectTime = -1;
    private transient int poolState = NOT_INITIALIZED;
    private static final int NOT_INITIALIZED = 0;
    private static final int AVAILABLE = 1;
    private static final int UNAVAILABLE = 2;
    private static final int RECONNECT_BEING_TRIED = 3;

    private Thread refreshThread = null;
    private Object refreshLock = new Object();

    private ArrayList<BackingStore.ConnectionPoolListener> lsnrs = new ArrayList<BackingStore.ConnectionPoolListener>();

    public OracleActiveConnectionPool(String key, OracleConnectionPool primary, OracleConnectionPool ft, Properties properties) throws Exception {
        this.primaryPool = primary;
        this.ftPool = ft;
        this.key = key;
        try {
            if (properties.containsKey("RetryInterval"))
                this.retryInterval = Integer.parseInt((String) properties.get("RetryInterval"));
            if (properties.containsKey("FailoverInterval"))
                this.failoverInterval = (Integer) properties.get("FailoverInterval");
            if (properties.containsKey("AutoFailover"))
                this.autoFailover = (Boolean) properties.get("AutoFailover");
            if (properties.containsKey("RecreateOnRecovery"))
                this.recreateOnRecovery = (Boolean) properties.get("RecreateOnRecovery");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        registerMBean();
    }

    public void setUsePrimary(boolean usePrimary) {
        this.usingPrimary = usePrimary;
    }

    public void setSecondaryPool(OracleConnectionPool ft) {
        this.ftPool = ft;
    }

    public synchronized void setFailbackKey(String key) {
        ftKey = key;
    }

    public OracleConnectionPool getPoolInUse() {
        if (usingPrimary)
            return primaryPool;

        if (ftPool == null) {
            if (ftKey != null) {
                ftPool = OracleConnectionManager.getDataSource(ftKey);
            }
        }
        return ftPool;
    }

    public boolean isAvailable() {
        return (poolState == AVAILABLE);
    }

    public boolean isAutoFailover() {
        return autoFailover;
    }

    public String getPoolState() {
        switch (poolState) {
            case AVAILABLE:
                return "Available (Using " + (getIsUsingPrimary() ? "Primary" : "Secondary") + ")";
            case NOT_INITIALIZED:
                return "Not Initialized";
            case UNAVAILABLE:
                return "Not Available";
            case RECONNECT_BEING_TRIED:
                return "Trying to reconnect";
        }

        return "Unknown"; //Should not happen
    }

    public String getPrimaryURI() {
        return key;
    }

    public String getSecondaryURI() {
        return ftKey;
    }

    public boolean getIsUsingPrimary() {
        return usingPrimary;
    }

    public boolean getIsFailoverAutomatic() {
        return autoFailover;
    }

    public String getFailoverInterval() {
        return "" + failoverInterval + " Seconds";
    }

    public synchronized void activate() throws Exception {
        getPoolInUse().activate();
        poolState = AVAILABLE;
    }

    public Map getEntityPropsMap(OracleConnection conn) {
        if (conn == null) {
            return defaultEntityPropsMap;
        }
        OracleConnection key = conn.unwrap();
        return entityPropertiesMapByConn.get(key);
    }

    public Map getOracleTypesMap(OracleConnection conn) {
        if (conn == null) {
            return defaultOracleTypesMap;
        }
        OracleConnection key = conn.unwrap();
        return oracleTypes2DescriptionByConn.get(key);
    }

    public boolean areMapsInitialized(OracleConnection conn) {
        if (conn == null) {
            // Un-specific (any) connection
            return (defaultEntityPropsMap != null);
        } else {
            // Specific connection
            OracleConnection key = conn.unwrap();
            return entityPropertiesMapByConn.containsKey(key);
        }
    }

    public synchronized void setMapsInitialized(Map entityProps, Map types2Desc, OracleConnection conn) {
        OracleConnection key = conn.unwrap();
        entityPropertiesMapByConn.put(key, entityProps);
        oracleTypes2DescriptionByConn.put(key, types2Desc);
        if (defaultEntityPropsMap == null) {
            defaultEntityPropsMap = entityProps;
            defaultOracleTypesMap = types2Desc;
        }
    }

    public void recycle() throws SQLException {
        getPoolInUse().refresh(recreateOnRecovery);
    }

    /**
     * Switches the data-source to secondary for this active connection on all cache-servers
     *
     * @throws SQLException
     * @throws Exception
     */
    public void switchToSecondary() throws Exception {
        ManageConnection.switchDBAcrossCluster(this.getPrimaryURI(), true);
        boolean usePrimary = false;

        GenericBackingStore backingStoreHandle = CacheClusterProvider.getInstance().getCacheCluster().getBackingStore();
        if ((backingStoreHandle != null) && (backingStoreHandle instanceof BackingStore)) {
            ((BackingStore)backingStoreHandle).setClusterDatasource(usePrimary);
        }
    }

    /**
     * Switches the data-source to primary for this active connection on all cache-servers
     *
     * @throws SQLException
     * @throws Exception
     */
    public void switchToPrimary() throws Exception {
        ManageConnection.switchDBAcrossCluster(this.getPrimaryURI(), false);
        boolean usePrimary = true;

        GenericBackingStore backingStoreHandle = CacheClusterProvider.getInstance().getCacheCluster().getBackingStore();
        if ((backingStoreHandle != null) && (backingStoreHandle instanceof BackingStore)) {
            ((BackingStore)backingStoreHandle).setClusterDatasource(usePrimary);
        }
    }

    private void notifySwitch(boolean toSecondary) {
        for (int i = 0; i < lsnrs.size(); i++) {
            lsnrs.get(i).switched(toSecondary);
        }
    }

    private void reconnected() {
        poolState = AVAILABLE;
        for (int i = 0; i < lsnrs.size(); i++) {
            lsnrs.get(i).reconnected();
        }
    }

    /**
     * Switches the data-source to secondary for this active connection
     *
     * @throws SQLException
     * @throws Exception
     */
    protected synchronized void switchLocalToSecondary() throws SQLException, Exception {
        System.out.println("Switching local DB connection to Secondary....");
        if (usingPrimary) {
            if (ftPool == null) {
                if (ftKey != null)
                    ftPool = OracleConnectionManager.getDataSource(ftKey);
            }
            //ftPool might still be null if no FT defined for this pool
            if (ftPool != null) {
                try {
                    ftPool.activate();
                    notifySwitch(true);
                } catch (Exception ex) {
                    System.out.println("Error switching to Secondary:" + ex.getMessage());
                    // Log, and may be send JMS message
                    throw ex;
                }
                poolState = AVAILABLE;
                disconnectTime = -1;
                usingPrimary = false;
                GenericBackingStore backingStoreHandle = CacheClusterProvider.getInstance().getCacheCluster().getBackingStore();
                if ((backingStoreHandle != null) && (backingStoreHandle instanceof BackingStore)) {
                    ((BackingStore)backingStoreHandle).setUsePrimaryDatasource(usingPrimary);
                }
                resetTypeMaps();
                primaryPool.close();
                System.out.println("Successfully Switched local DB connection to Secondary.");
            } else {
                // If no FT pool defined
                // Log, and may be send JMS message
                System.out.println("No secondary DB configured. Switching local DB connection to Secondary failed.");
            }
        } else {
            // If currently not using Primary, Log and may be send JMS message
            System.out.println("Local already using Secondary DB connection.");
        }
    }

    /**
     * Switches the data-source to primary for this active connection
     *
     * @throws SQLException
     * @throws Exception
     */
    protected synchronized void switchLocalToPrimary() throws SQLException, Exception {
        System.out.println("Switching local DB connection to Primary....");
        if (!usingPrimary) {
            try {
                primaryPool.activate();
                notifySwitch(false);
            } catch (Exception ex) {
                // Log, and may be send JMS message
                throw ex;
            }

            poolState = AVAILABLE;
            usingPrimary = true;
            //Update local config too
            GenericBackingStore backingStoreHandle = CacheClusterProvider.getInstance().getCacheCluster().getBackingStore();
            if ((backingStoreHandle != null) && (backingStoreHandle instanceof BackingStore)) {
                ((BackingStore)backingStoreHandle).setUsePrimaryDatasource(usingPrimary);
            }
            disconnectTime = -1;
            resetTypeMaps();
            if (ftPool != null)
                ftPool.close();

            System.out.println("Successfully Switched local DB connection to Primary.");
        } else {
            // If currently not using Secondary, Log and may be send JMS message
            System.out.println("Local already using Primary DB connection.");
        }
    }

    private synchronized void resetTypeMaps() {
        entityPropertiesMapByConn.clear();
        oracleTypes2DescriptionByConn.clear();
    }

    public synchronized java.sql.Connection getConnection() throws SQLException {
        return getPoolInUse().getConnection();
    }

    /**
     * @return
     * @throws SQLException
     */
    public String getQualifier() throws SQLException {
        return getPoolInUse().getQualifier();
    }

    /**
     * @return
     */
    public Map getAliases() {
        return getPoolInUse().getAliases();
    }

    /**
     * @param lsnr
     */
    public void addConnectionListener(BackingStore.ConnectionPoolListener lsnr) {
        if (!lsnrs.contains(lsnr)) {
            lsnrs.add(lsnr);
        }
    }

    /**
     * This method returns connection cache size.
     *
     * @return int - Cache size
     */
    public int getCacheSize() {
        return getPoolInUse().getCacheSize();
    }

    public int getNumberOfConnectionsInUse() {
        return getPoolInUse().getNumberOfActiveConnections();
    }

    public int getNumberOfAvailableConnections() {
        return getPoolInUse().getNumberOfAvailableConnections();
    }

    public synchronized String toString() {
        try {
            String info = "OracleConnectionPool(" + getPoolInUse().getDataSource().getURL() + "," + getPoolInUse().getDataSource().getUser() + ")" +
                    ", available=" + getPoolInUse().getAvailableConnections() + ", busy=" + getPoolInUse().getNumberOfActiveConnections() +
                    ", max=" + getPoolInUse().getCacheMaxLimit();
            return (info);
        } catch (Exception e) {
            return getPoolInUse().toString();
        }
    }

    /**
     * This method returns active size of the Cache.
     *
     * @return int - Number of active connections
     * @throws Exception - Any exception while getting the active size
     */
    @Deprecated
    public int getActiveSize() throws Exception {
        return getPoolInUse().getActiveSize();
    }

    /**
     * This method returns the number of connections available in the cache.
     *
     * @return int - Available connections in the cache
     * @throws Exception - Exception while getting the available connections
     */
    public int getAvailableConnections() throws Exception {
        return getPoolInUse().getAvailableConnections();
    }

    /**
     * This method closes the connection cache. This is called to close the
     * connection cache when the application closes.
     *
     * @throws SQLException - SQLException
     */
    public void closeConnCache() throws SQLException {
        getPoolInUse().closeConnCache();
    }

    private void registerMBean() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("com.tibco.be:type=SharedResource,service=OracleActiveConnectionPool,name=" + key);
            mbs.registerMBean(this, name);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void refreshConnections() {
        synchronized (refreshLock) {
            //System.err.println("In refreshConnections ...disconnectTime=" + disconnectTime + ", refreshThread=" + refreshThread);
            if (refreshThread != null && refreshThread.isAlive()) {
                // Refresh already being tried, so return.
                System.err.println("OracleActiveConnectionPool Refresh thread alive, returning...");
                return;
            } else {  
                // Refresh-thread object exists, but not active.
                disconnectTime = System.currentTimeMillis();
                poolState = RECONNECT_BEING_TRIED;
                refreshThread = new Thread(new RefreshThread(), "DB_Reconnect_Thread");
                refreshThread.start();
                System.err.println("OracleActiveConnectionPool Started refresh thread...");
            }
        }
    }

    private class RefreshThread implements Runnable {
        public void run() {
            System.err.println("Refresh thread - Starting...");
            //System.err.println("Started refresh thread, autoFailover=" + autoFailover + ", failoverInterval=" + failoverInterval);
            while (poolState == RECONNECT_BEING_TRIED) {
                try {
                    //Auto failover, if allowed, happens only from Primary to secondary
                    if (autoFailover && getIsUsingPrimary()) {
                        long currTime = System.currentTimeMillis();
                        if (currTime - disconnectTime > (failoverInterval * 1000)) {
                            //Switch to secondary
                            try {
                                System.err.println("  Refresh thread - Switching To Secondary Datasource");
                                switchToSecondary();
                                try {
                                    Thread.sleep(10000);   //To allow for system to stabilize.
                                } catch (Exception e) {
                                }
                                //poolState = AVAILABLE;
                                reconnected();
                                break; //break on success only
                            } catch (Exception ex) {
                                System.err.println("  Refresh thread - Switching To Secondary Datasource failed:  " + ex.getMessage());
                            }
                        }
                    }

                    getPoolInUse().refresh(recreateOnRecovery);
                    //Try getting a connection
                    Connection conn = null;
                    try {
                        conn = getConnection();
                    } catch (DBConnectionsBusyException busyEx) {
                        //No op. DB available but all connections busy.
                    } finally {
                        if (conn != null) {
                            try {
                                conn.close();
                            } catch (Exception e) {
                            }
                        }
                    }
                    try {
                        Thread.sleep(5000);   //To allow for system to stabilize.
                    } catch (Exception e) {
                    }
                    reconnected();
                    System.err.println("Refresh thread - Reconnected with Database successfully");
                    break;
                } catch (SQLException sqle) {
                    try {
                        System.err.println("Refresh thread - Trying to reconnect... Exc=" + sqle.getMessage());
                        Thread.sleep(retryInterval * 1000);
                    } catch (Exception e) {
                        System.err.println("Refresh thread - Exception: DB-Reconnect thread interrupted");
                    }
                } catch (Exception ex) {
                    try {
                        System.err.println("Refresh thread - Trying to reconnect... Exc=" + ex.getMessage());
                        Thread.sleep(retryInterval * 1000);
                    } catch (Exception e) {
                        System.err.println("Refresh thread - Exception: DB-Reconnect thread interrupted");
                    }
                }
            }
        }
    }
}
