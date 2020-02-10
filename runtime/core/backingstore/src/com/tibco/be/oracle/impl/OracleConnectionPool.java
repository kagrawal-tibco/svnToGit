package com.tibco.be.oracle.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.Callable;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.pool.OracleConnectionCacheManager;
import oracle.jdbc.pool.OracleDataSource;
import oracle.sql.ArrayDescriptor;
import oracle.sql.StructDescriptor;
import oracle.sql.TypeDescriptor;

import com.tibco.be.common.ConnectionPool;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.util.DBConnectionsBusyException;
import com.tibco.cep.util.ThreadSafeInitializer;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Apr 27, 2008
 * Time: 7:57:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class OracleConnectionPool extends ConnectionPool {

    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(OracleConnectionPool.class);
    
    /* Maximum number of connection instances */
    private static int MAX_LIMIT = 15;

    /* Connection cache manager */
    private OracleConnectionCacheManager connMgr = null;
    private String schema=null;
    private String qualifier="";

    /* Data Source Variable */
    private OracleDataSource ods = null;
    private String key = null;
    private Properties config;
    private Map beAliases = new HashMap();
    private boolean isActivated = false;
    private boolean loadTypes = true;

    private Thread refreshThread = null;

    private final ThreadSafeInitializer<HashMap<String, TypeDescriptor>> cachedTypeDescriptors;

    public OracleConnectionPool(String key, OracleDataSource ds, Properties config) throws Exception {
        this.ods = ds;
        this.key = key;
        this.config = config;
        
        if (config.containsKey("schema"))
            this.schema=(String) config.get("schema");
        if (schema != null)
            qualifier = schema.toUpperCase() + ".";

        if (config.containsKey("LoadTypes"))
            this.loadTypes = (Boolean) config.get("LoadTypes");

        this.cachedTypeDescriptors = new ThreadSafeInitializer<HashMap<String, TypeDescriptor>>(5, logger);
    }

    public synchronized void activate() throws Exception {
        if (!isActivated) {
            initializeConnectionCache();
            loadAliases();
            //loadAllObjectTypes();
            isActivated = true;
            setStatus(CONNECTED);
        }
    }

    public String getQualifier() {
        return qualifier;
    }

    public Connection getConnection() throws SQLException {
        return getConnection(loadTypes);
    }

    public Connection getConnection(boolean typeFlag) throws SQLException {
        Connection conn = null;
        synchronized (this) {
            conn = ods.getConnection();
            //logger.log(Level.WARN, "Connection: %s Datasource FFC: %s", conn, ods.getFastConnectionFailoverEnabled());
            if (conn == null) {
                logger.log(Level.WARN,
                        "All Database Connections busy. Unable to service request. Please increase Connection Pool size.");
                throw new DBConnectionsBusyException();
            }

            ((OracleConnection)conn).setImplicitCachingEnabled(true);
            //((OracleConnection)conn).setExplicitCachingEnabled(true); // Requires 'closeWithKey' and 'getStatementWithKey' calls
            ((OracleConnection)conn).setStatementCacheSize(160);
            logger.log(Level.INFO, "Connection caching: implicit=%s explicit=%s size=%s", 
            		((OracleConnection)conn).getImplicitCachingEnabled(), ((OracleConnection)conn).getExplicitCachingEnabled(), ((OracleConnection)conn).getStatementCacheSize());
        }

        // Change default schema, if so requested
        if (schema != null) {
            changeDefaultSchema(conn);
        }

        // Check if types loaded on this connection or not.
        if (typeFlag) {
            if (!isTypeLoaded("T_CONCEPT", (OracleConnection)conn)) {
                try {
                    loadTypesToConnection((OracleConnection) conn);
                } catch (SQLException sqlex) {
                    logger.log(Level.FATAL, "Failed to load custom types: %s - " + 
                            " Make sure backingstore database is setup properly.", sqlex.getMessage());
                    throw new RuntimeException("Failed to load custom types from database: " + sqlex.getMessage());
                } catch (Exception ex) {
                    SQLException sql = new SQLException("Error occurred while loading custom types");
                    sql.initCause(ex);
                    throw sql;
                }
            }
        }

        if (conn.getAutoCommit()) {
            conn.setAutoCommit(false);
        }
        return conn;
    }

    private void loadTypesToConnection(final OracleConnection conn) throws Exception {
        Callable<HashMap<String, TypeDescriptor>> job =
                new Callable<HashMap<String, TypeDescriptor>>() {
                    public HashMap<String, TypeDescriptor> call() throws Exception {
                        return fetchTypes(conn);
                    }
                };

        HashMap<String, TypeDescriptor> map = cachedTypeDescriptors.getCachedOrCallJob(job);

        for (Entry<String, TypeDescriptor> entry : map.entrySet()) {
            String k = entry.getKey();
            TypeDescriptor v = entry.getValue();

            conn.putDescriptor(k.toUpperCase(), v);
        }
    }

    private void changeDefaultSchema(java.sql.Connection con) throws SQLException {
        // First check if current schema not same as intended
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select sys_context('USERENV','SESSION_SCHEMA') from dual");
        rs.next();
        String currSchema = rs.getString(1);
        if (schema!= null && !schema.equalsIgnoreCase(currSchema)) {
            //System.out.println(" ### Altering schema, currSchema=" + currSchema + ", schema=" + schema);
            stmt.execute("alter session set current_schema = " + schema);
        }
        else {
            //System.out.println(" ### NOT Altering schema, currSchema=" + currSchema + ", schema=" + schema);
        }
        stmt.close();
    }

    private void loadAliases() throws Exception {
        OracleConnection cnx = null;
        try {
            cnx = (OracleConnection) getConnection();
            if (cnx != null) {
                try {
                    OraclePreparedStatement stmt = (OraclePreparedStatement) cnx.prepareStatement("SELECT * FROM BEAliases");
                    OracleResultSet rs = (OracleResultSet) stmt.executeQuery();
                    while (rs.next()) {
                        String beName = rs.getString(1);
                        String alias = rs.getString(2);
                        beAliases.put(beName, alias);
                    }
                } catch (SQLException sqlex) {
                    logger.log(Level.FATAL, "Failed to query BEAliases database table: %s - " + 
                            " Make sure backingstore database is setup properly.", sqlex.getMessage());
                    throw new RuntimeException("Failed to query BEAliases table: " + sqlex.getMessage());
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            if (cnx != null) {
                cnx.close();
            }
        }
    }

    public Map getAliases() {
        return beAliases;
    }

    public synchronized void refresh(boolean remove) throws SQLException {
        logger.log(Level.INFO, "OracleConnectionPool Refresh [remove=" + remove + "]");
        boolean cleanupCheckedOutConnections = true;
        if (remove) {
            if (connMgr.existsCache(key)) {
                connMgr.purgeCache(key, cleanupCheckedOutConnections);
                connMgr.removeCache(key, 5);
            }
            connMgr.createCache(key, ods, getConnectionCacheProperties());
        } else {
            connMgr.purgeCache(key, cleanupCheckedOutConnections);
            // TODO: Enable following after further testing
            //connMgr.refreshCache(key, OracleConnectionCacheManager.REFRESH_ALL_CONNECTIONS);
            //connMgr.reinitializeCache(key, getConnectionCacheProperties());
        }
    }

    public synchronized void refreshConnections(boolean removeFlag) {
        if (refreshThread != null && refreshThread.isAlive()) {
            // Refresh already being tried, so return.
            logger.log(Level.INFO, "OracleConnectionPool Refresh thread alive, returning...");
            return;
        } else {  
            // Refresh-thread object exists, but not active.
            refreshThread = new Thread(new PoolRefreshThread(removeFlag), "DB_PoolReconnect");
            refreshThread.start();
            logger.log(Level.INFO, "OracleConnectionPool Started refresh thread...");
        }
    }

    public synchronized void close() {
        try {
            ods.close();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
        isActivated = false;
        setStatus(DISCONNECTED);
    }

    public boolean isAvailable() {
        OracleActiveConnectionPool activeConnectionPool = OracleConnectionManager.getActiveConnectionPool(key);
        return activeConnectionPool.isAvailable();
    }

    public boolean isAlive() {
        return (getStatus() == CONNECTED);
    }

    /**
     * This method returns active size of the Cache.
     *
     * @return int - Number of active connections
     * @throws Exception - Any exception while getting the active size
     */
    public int getActiveSize() throws Exception {
        try {
            return connMgr.getNumberOfActiveConnections(key);
        } catch (SQLException sqlEx) {
            //sqlEx.printStackTrace();
            throw new Exception("SQL Error while getting the no of active " +
                    " connections"
            );
        }
    }

    /**
     * This method returns the number of connections available in the cache.
     *
     * @return int - Available connections in the cache
     * @throws Exception - Exception while getting the available connections
     */
    public int getAvailableConnections() throws Exception {
        return connMgr.getNumberOfAvailableConnections(key);
    }

    /**
     * This method returns connection cache size.
     *
     * @return int - Cache size
     */
    public int getCacheSize() {
        try {
            return connMgr.getNumberOfActiveConnections(key) +
                    connMgr.getNumberOfAvailableConnections(key);
        } catch (Exception ex) {
            return 0;
        }
    }

    public int getNumberOfActiveConnections() {
        try {
            return connMgr.getNumberOfActiveConnections(key);
        } catch (Exception ex) {
            return 0;
        }
    }

    public int getNumberOfAvailableConnections() {
        try {
            return connMgr.getNumberOfAvailableConnections(key);
        } catch (Exception ex) {
            return 0;
        }
    }

     public synchronized String toString() {
         try {
             String info = "OracleConnectionPool(" + getDataSource().getURL() + "," + getDataSource().getUser() + ")" +
             ", available=" + getAvailableConnections() + ", busy=" + getNumberOfActiveConnections() +
             ", max=" + getCacheMaxLimit();
             return (info);
         } catch (Exception e) {
             return toString();
         }
     }

    /**
     * This method returns the instance of OracleDataSource
     *
     * @return OracleDataSource instance
     */
    public OracleDataSource getDataSource() {
        return ods;
    }

    /**
     * This method returns the MaxLimit of the connection cache
     *
     * @return MAX_LIMIT - Cache max limit
     */
    public int getCacheMaxLimit() {
        return MAX_LIMIT;
    }

    /**
     * This method reinitializes the cache with the new properties
     *
     * @param properties - Properties object containing connection cache properties
     * @throws SQLException - SQLException while setting the properties
     */
    public void setCacheProperties(Properties properties) throws SQLException {
    }

    /**
     * This method reinitializes the cache with the new properties
     *
     * @return Properties - Connection cache properties
     * @throws SQLException - SQLException
     */
    public Properties getCacheProperties() throws SQLException {
        return ods.getConnectionCacheProperties();
    }

    /**
     * This method closes the connection cache. This is called to close the
     * connection cache when the application closes.
     *
     * @throws SQLException - SQLException
     */
    public void closeConnCache() throws SQLException {
        if (ods != null) {
            ods.close();
        }
    }

    /**
     * This Method initializes Connection Cache by associating a data source
     * with the cache. Also the properties of the cache are set while creating
     * the connection cache.
     *
     * @throws Exception
     */
    private void initializeConnectionCache() throws Exception {
        if (ods != null) {
            try {
                this.initializeConnectionCacheDataSrc();

                /* Initialize the Connection Cache */
                connMgr = OracleConnectionCacheManager.getConnectionCacheManagerInstance();

                // Set connection-cache props
                // Amitabh: Users can specify whether to enforce pool-size or not using the tra prop be.backingstore.dburi.pool.enforce.x
                connMgr.createCache(key, ods, getConnectionCacheProperties());
            } catch (java.sql.SQLException ex) { /* Catch SQL Errors */
                throw new Exception("SQL Error while Instantiating Connection Cache : \n" + ex.toString());
            } catch (java.lang.Exception ex) { /* Catch other generic errors */
                throw new Exception("Exception : \n" + ex.toString());
            }
        }
    }

    private Properties getConnectionCacheProperties() {
        Properties props = new Properties();
        int initial, min, max, waitTimeout, inactivityTimeout;

        String initialLimit_str = config.getProperty("InitialLimit");
        if (initialLimit_str != null) {
            initial = Integer.parseInt(initialLimit_str.trim());
            if (initial > 0)
                props.put("InitialLimit", initialLimit_str);
        }

        String minLimit_str = config.getProperty("MinLimit");
        if (minLimit_str != null) {
            min = Integer.parseInt(minLimit_str.trim());
            if (min >= 0)
                props.put("MinLimit", minLimit_str);
        }

        String maxLimit_str = config.getProperty("MaxLimit");
        if (maxLimit_str != null) {
            max = Integer.parseInt(maxLimit_str.trim());
            if (max > 0)
                props.put("MaxLimit", maxLimit_str);
        }

        String waitTimeout_str = config.getProperty("WaitTimeout");
        if (waitTimeout_str != null) {
            waitTimeout = Integer.parseInt(waitTimeout_str.trim());
            if (waitTimeout > 0)
                props.put("ConnectionWaitTimeout", waitTimeout_str);
            else  //Put default value of wait-timeout as 1 Sec
                props.put("ConnectionWaitTimeout", "1");
        }

        String inactivityTimeout_str = config.getProperty("InactivityTimeout");
        if (inactivityTimeout_str != null) {
            inactivityTimeout = Integer.parseInt(inactivityTimeout_str.trim());
            if (inactivityTimeout > 0)
                props.put("InactivityTimeout", inactivityTimeout_str);
            else  //Put default value of InactivityTimeout as 900 Sec
                props.put("InactivityTimeout", "900");
        }
        // Lets keep default value for PropertyCheckInterval (900 secs i.e. 15mins)
        //props.put("PropertyCheckInterval", "10");
        return props;
    }

    /**
     * This Method initializes the variable 'ods' with value of valid Connection
     * Cache Data Source.
     *
     * @throws Exception
     */
    private void initializeConnectionCacheDataSrc()
            throws Exception {
        try {
            /* Enable caching */
            ods.setConnectionCachingEnabled(true);
            
            /* Set the cache name */
            ods.setConnectionCacheName(key);
            
            /* Enable statement caching */
            ods.setImplicitCachingEnabled(true);
            //ods.setExplicitCachingEnabled(true);
        } catch (SQLException sqlEx) { /* Catch SQL Errors */
            sqlEx.printStackTrace();
            throw new Exception("SQL Errors = " + sqlEx.toString());
        } catch (Exception ex) { /* Catch Generic Errors */
            ex.printStackTrace();
            throw new Exception("Generic Errors = " + ex.toString());
        }
    }

    private void fetchObjectType(String typeName, OracleConnection cnx,
                                HashMap<String, TypeDescriptor> typeMap) throws SQLException {
        try {
            StructDescriptor sd = StructDescriptor.createDescriptor(qualifier + typeName.toUpperCase(), cnx);
            typeMap.put(typeName, sd);
        } catch (SQLException ex) {
            logger.log(Level.ERROR, ex, "Got exception when loading " + typeName.toUpperCase());
            throw ex;
        }
    }

    private boolean isTypeLoaded(String typeName, OracleConnection cnx) {
        Object obj = cnx.getDescriptor(typeName.toUpperCase());
        if (obj == null) {
            return false;
        }
        return true;
    }

    private void fetchArrayType(String typeName, OracleConnection cnx,
                               HashMap<String, TypeDescriptor> typeMap) throws SQLException {
        try {
            ArrayDescriptor sd = ArrayDescriptor.createDescriptor(qualifier + typeName, cnx);
            typeMap.put(typeName, sd);
        } catch (Exception ex) {
            logger.log(Level.ERROR, ex, "Got exception when loading " + typeName.toUpperCase());
        }
    }

    private HashMap<String, TypeDescriptor> fetchTypes(OracleConnection cnx) throws SQLException {
        HashMap<String, TypeDescriptor> typeDescriptors = new HashMap<String, TypeDescriptor>();

        /* Load the entity types */
        fetchObjectType("T_SIMPLE_EVENT", cnx, typeDescriptors);
        fetchObjectType("T_STATEMACHINE_TIMEOUT", cnx, typeDescriptors);
        fetchObjectType("T_TIME_EVENT", cnx, typeDescriptors);
        fetchObjectType("T_EVENT", cnx, typeDescriptors);
        fetchObjectType("T_CONCEPT", cnx, typeDescriptors);
        fetchObjectType("T_ENTITY", cnx, typeDescriptors);

        fetchArrayType("T_ENTITYREF_COL_HIST", cnx, typeDescriptors);
        fetchObjectType("T_ENTITYREF_HIST", cnx, typeDescriptors);
        fetchArrayType("T_ENTITYREF_HIST_TABLE", cnx, typeDescriptors);
        fetchObjectType("T_ENTITYREF_HIST_TUPLE", cnx, typeDescriptors);

        fetchArrayType("T_ENTITY_REF_TABLE", cnx, typeDescriptors);
        fetchObjectType("T_ENTITY_REF", cnx, typeDescriptors);
        fetchObjectType("T_REVERSE_REF", cnx, typeDescriptors);
        fetchArrayType("T_REVERSE_REF_TABLE", cnx, typeDescriptors);

        /* Load The String Types */
        fetchArrayType("T_STRING_COL", cnx, typeDescriptors);
        fetchObjectType("T_STRING_HIST_TUPLE", cnx, typeDescriptors);
        fetchArrayType("T_STRING_HIST_TABLE", cnx, typeDescriptors);
        fetchObjectType("T_STRING_HIST", cnx, typeDescriptors);
        fetchArrayType("T_STRING_COL_HIST", cnx, typeDescriptors);

        //fetchArrayType("T_INT_COL", cnx, typeDescriptors);
        //fetchObjectType("T_INT_HIST_TUPLE", cnx, typeDescriptors);
        //fetchArrayType("T_INT_HIST_TABLE", cnx, typeDescriptors);
        //fetchObjectType("T_INT_HIST", cnx, typeDescriptors);
        //fetchArrayType("T_INT_COL_HIST", cnx, typeDescriptors);

        fetchArrayType("T_INT_COL", cnx, typeDescriptors);
        fetchObjectType("T_INT_HIST_TUPLE", cnx, typeDescriptors);
        fetchArrayType("T_INT_HIST_TABLE", cnx, typeDescriptors);
        fetchObjectType("T_INT_HIST", cnx, typeDescriptors);
        fetchArrayType("T_INT_COL_HIST", cnx, typeDescriptors);

        fetchArrayType("T_BOOLEAN_COL", cnx, typeDescriptors);
        fetchObjectType("T_BOOLEAN_HIST_TUPLE", cnx, typeDescriptors);
        fetchArrayType("T_BOOLEAN_HIST_TABLE", cnx, typeDescriptors);
        fetchObjectType("T_BOOLEAN_HIST", cnx, typeDescriptors);
        fetchArrayType("T_BOOLEAN_COL_HIST", cnx, typeDescriptors);

        fetchArrayType("T_NUMBER_COL", cnx, typeDescriptors);
        fetchObjectType("T_NUMBER_HIST_TUPLE", cnx, typeDescriptors);
        fetchArrayType("T_NUMBER_HIST_TABLE", cnx, typeDescriptors);
        fetchObjectType("T_NUMBER_HIST", cnx, typeDescriptors);
        fetchArrayType("T_NUMBER_COL_HIST", cnx, typeDescriptors);

        fetchArrayType("T_DATETIME_COL", cnx, typeDescriptors);
        fetchObjectType("T_DATETIME_HIST_TUPLE", cnx, typeDescriptors);
        fetchArrayType("T_DATETIME_HIST_TABLE", cnx, typeDescriptors);
        fetchObjectType("T_DATETIME_HIST", cnx, typeDescriptors);
        fetchArrayType("T_DATETIME_COL_HIST", cnx, typeDescriptors);

        List<String> custom_types = new ArrayList<String>();
        OraclePreparedStatement stmt = (OraclePreparedStatement) cnx.prepareStatement("SELECT ORACLETYPE FROM ClassToOracleType");
        OracleResultSet rs = (OracleResultSet) stmt.executeQuery();
        while (rs.next())  {
            custom_types.add(rs.getString(1));
        }
        rs.close();
        stmt.close();

        custom_types.add("T_STATEMACHINE_TIMEOUT");

        for (int i=0 ; i < custom_types.size(); i++) {
            String customType= custom_types.get(i);
            fetchObjectType(customType, cnx, typeDescriptors);
        }

        return typeDescriptors;
    }

    private class PoolRefreshThread implements Runnable {
        boolean recreateFlag = true;
        public PoolRefreshThread(boolean recreateOnRecovery) {
            recreateFlag = recreateOnRecovery; 
        }
        public void run() {
            logger.log(Level.INFO, "OracleConnectionPool Refresh thread - Starting...");
            Connection conn = null;
            while (true) {
                try {
                    refresh(recreateFlag);
                    try {
                        conn = getConnection();
                    } catch (DBConnectionsBusyException busyEx) {
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException intrEx) {
                    }
                    logger.log(Level.INFO, "PoolRefreshThread restored connections");
                    setStatus(CONNECTED);
                    reconnected();
                    break;
                } catch (SQLException sqle) {
                    try {
                        logger.log(Level.WARN, "PoolRefreshThread trying to reconnect - error=" + sqle.getMessage());
                        setStatus(DISCONNECTED);
                        Thread.sleep(5000);
                    }
                    catch (Exception e) {
                        logger.log(Level.WARN, "PoolRefreshThread reconnect thread interrupted");
                    }
                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
    }
}
