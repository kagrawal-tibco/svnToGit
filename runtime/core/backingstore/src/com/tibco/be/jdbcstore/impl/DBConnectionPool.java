package com.tibco.be.jdbcstore.impl;

import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.Timer;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.tibco.be.common.ConnectionPool;
import com.tibco.be.jdbcstore.RDBMSType;
import com.tibco.be.jdbcstore.ssl.JdbcSSLConnectionInfo;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.util.DBNotAvailableException;

public class DBConnectionPool extends ConnectionPool implements DBConnectionPoolMBean {

    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(DBConnectionPool.class);

    private String key, jdbcDriver, jdbcUrl, username, password;
    private int maxConnections, initialConnections;
    private Vector inUseCnx;
    private BlockingQueue availableCnx;
    private Set allConnections;

    private int connectionCount = 0;

    // Used for lock
    private Object dummySyncObj = new Object();

    // Used for validate database connection
    private Connection testConnection;

    // How often to scan for database connection health (seconds)
    private int checkConnectionsInterval = 60;

    // Following two settings are for checking multiple connections at the same time
    private boolean enableCheckAllConnections = false;
    private int checkRatio = 5; //-1-1, 1-all, 2-half, 4-quarter, 5-fifth, 6-sixth, 8-eighth, 16-sixteenth
    private boolean enableCheckConnectionLogging = false;

    // How many times to retry. Default 0 -> do not retry
    private int connRetryCount = 0;

    private Timer reconnectTimer;
    private JdbcSSLConnectionInfo sslConnectionInfo;

    private static RuleServiceProvider rsProvider;

    private final static HashMap jdbcDriverClasses = new HashMap();
    private final static String TSI_SQL_SERVER_DRIVER = "tibcosoftwareinc.jdbc.sqlserver.SQLServerDriver";
    private final static String TSI_ORACLE_SERVER_DRIVER = "tibcosoftwareinc.jdbc.oracle.OracleDriver";
    private final static String TSI_SYBASE_SERVER_DRIVER = "tibcosoftwareinc.jdbc.sybase.SybaseDriver";
    private final static String TSI_DB2_SERVER_DRIVER = "tibcosoftwareinc.jdbc.db2.DB2Driver";

    static {
        jdbcDriverClasses.put("tibcosoftwareinc.jdbc.oracle.OracleDriver (RAC)", "tibcosoftwareinc.jdbc.oracle.OracleDriver");
        jdbcDriverClasses.put("oracle.jdbc.OracleDriver (thin)", "oracle.jdbc.OracleDriver");
        jdbcDriverClasses.put("oracle.jdbc.OracleDriver (oci)", "oracle.jdbc.OracleDriver");
        jdbcDriverClasses.put(TSI_SQL_SERVER_DRIVER, TSI_SQL_SERVER_DRIVER);
        jdbcDriverClasses.put(TSI_ORACLE_SERVER_DRIVER, TSI_ORACLE_SERVER_DRIVER);
        jdbcDriverClasses.put(TSI_SYBASE_SERVER_DRIVER, TSI_SYBASE_SERVER_DRIVER);
        jdbcDriverClasses.put(TSI_DB2_SERVER_DRIVER, TSI_DB2_SERVER_DRIVER);
    }

    public DBConnectionPool(String key, String driver, String url, String username, String password, int initialConnections, int maxConnections, int timeout_NotUsed, JdbcSSLConnectionInfo sslConnInfo) {

        // Initialize RSP and Logger first
        if (rsProvider == null) {
            rsProvider = RuleServiceProviderManager.getInstance().getDefaultProvider();
        }

        this.key = key;
        this.jdbcUrl = url;
        this.username = username;
        this.password = password;
        this.sslConnectionInfo = sslConnInfo;

        // Support for Oracle-11g
        if (driver.startsWith("oracle.jdbc.driver")) {
            this.jdbcDriver = driver.replace("oracle.jdbc.driver", "oracle.jdbc");
            logger.log(Level.WARN, "Database connection pool is configured using deprecated package 'oracle.jdbc.driver'");
        } else {
            this.jdbcDriver = driver;
        }

        // Validate initial connection size
        this.maxConnections = maxConnections;
        this.initialConnections = initialConnections;
        if (this.initialConnections > maxConnections) {
            this.initialConnections = maxConnections;
        }

        try {
            enableCheckAllConnections = Boolean.valueOf(rsProvider.getProperties().getProperty("be.backingstore.test.connections.checkall", "false"));
        } catch (Exception e) {
        }

        try {
            checkRatio = Integer.valueOf(rsProvider.getProperties().getProperty("be.backingstore.test.connections.checkratio", "5"));
            if (checkRatio <= 0) {
                checkRatio = -1;
            }
        } catch (Exception e) {
        }

        try {
            enableCheckConnectionLogging = Boolean.valueOf(rsProvider.getProperties().getProperty("be.backingstore.test.connections.logall", "false"));
        } catch (Exception e) {
        }

        try {
            String checkConnStr = rsProvider.getProperties().getProperty("be.backingstore.test.connections.interval", "60");
            checkConnectionsInterval = Integer.parseInt(checkConnStr);
        } catch (Exception e) {
        }

        // Convert connections interval to milliseconds
        checkConnectionsInterval = checkConnectionsInterval * 1000;

        try {
            String retryStr = rsProvider.getProperties().getProperty("be.backingstore.connection.retry.count", "-1");
            connRetryCount = Integer.parseInt(retryStr);
        } catch (Exception e) {
        }
        registerMBean();
    }

    public void init() throws Exception {
    	logger.log(Level.DEBUG, "Checking all jdbc connections is " + (enableCheckAllConnections ? "enabled" : "disabled"));
        availableCnx = new ArrayBlockingQueue(maxConnections);
        inUseCnx = new Vector();
        allConnections = Collections.synchronizedSet(new HashSet());

        try {
            if (makeInitialConnections() == false) {
                throw new Exception("Error while creating database connection");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (connRetryCount != 0) {
                reconnectTimer = new Timer("DB_PoolRefresh", true);
                reconnectTimer.schedule(new RefreshConnectionsThread(), checkConnectionsInterval, checkConnectionsInterval);
            }
            if (enableCheckAllConnections == true) {
                int total = allConnections.size();
                int size = 1;
                if (checkRatio > 0) {
                    size = total / checkRatio;
	                if (size == 0) {
	                    size = 1;
	                }
                }
                logger.log(Level.DEBUG, "Checking all jdbc connection ratio : " + checkRatio + " with initialize check size = " + size);
            }
        }
    }

    public Connection getConnection() throws SQLException {
        return getConnection(true, true);
    }

    public synchronized Connection getConnection(boolean typeFlag, boolean growPool) throws SQLException {
        if (getStatus() == DISCONNECTED) {
            throw new DBNotAvailableException("Connection pool status is disconnected");
        }

        Connection conn = (Connection) this.availableCnx.poll();
        if (conn == null && growPool) {
            if (connectionCount < maxConnections) {
                addNewConnection();
                conn = (Connection) this.availableCnx.poll();
            } else {
                try {
                    conn = (Connection) this.availableCnx.take();
                } catch (InterruptedException e) {
                }
            }
        }
        if (growPool && conn == null) {
            throw new SQLException("Cannot get/create connection for database " + jdbcUrl);
        } else if (conn != null) {
            inUseCnx.addElement(conn);
        }
        if (conn == null) {
            logger.log(Level.WARN, "Failed: Getting connection from pool Total=%s Used=%s Available=%s",
                    this.allConnections.size(), this.inUseCnx.size(), this.availableCnx.size());
        } else {
            logger.log(Level.TRACE, "Success: Getting connection from pool Total=%s Used=%s Available=%s [conn=%s]",
                    this.allConnections.size(), this.inUseCnx.size(), this.availableCnx.size(), conn);
        }
        
        return conn;
    }

    public synchronized Connection getConnection(int timeout) throws SQLException {
        if (getStatus() == DISCONNECTED) {
            logger.log(Level.WARN, "** Connection pool status is disconnected.");
            return null;
        }

        Connection conn = null;
        try {
            conn = (Connection) this.availableCnx.poll(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e1) {
        }
        if (conn == null) {
            try {
                if (connectionCount < maxConnections) {
                    addNewConnection();
                    conn = (Connection) this.availableCnx.poll(timeout, TimeUnit.MILLISECONDS);
                } else {
                    conn = (Connection) this.availableCnx.poll(timeout, TimeUnit.MILLISECONDS);
                }
            } catch (InterruptedException e) {
                logger.log(Level.WARN, e, e.getMessage());
            }
        }
        if (conn == null) {
            throw new SQLException("Cannot get/create connection for database " + jdbcUrl);
        } else {
            inUseCnx.addElement(conn);
        }
        return conn;
    }

    public void free(Connection connection) {
    	logger.log(Level.DEBUG, "Pool freeing connection: " + connection);
        synchronized (dummySyncObj) {
            if (allConnections.contains(connection)) {
                // Only add it back to the availableCnx if it's in the inUseCnx.
                if (inUseCnx.removeElement(connection)) {
                    availableCnx.add(connection);
                }
                logger.log(Level.DEBUG, "Pool connection freed: " + connection);
            } else {
            	logger.log(Level.WARN, "Unknown connection closed: " + connection);
                closeConnection(connection);
            }
        }
    }

    public boolean isAlive() {
        return (getStatus() == CONNECTED);
    }

    public synchronized void close() {
        try {
            closeAllConnections();
            if (reconnectTimer != null) {
                reconnectTimer.cancel();
            }
        } catch (Exception e) {
            logger.log(Level.ERROR, e, "Error while closing connection pool");
        }
    }

    private void closeAllConnections() {
        try {
            if (testConnection != null && !testConnection.isClosed()) {
                testConnection.close();
            }
        } catch (SQLException e) {
            logger.log(Level.WARN, e, e.getMessage());
        }
        closeConnections(allConnections.iterator());
        logger.log(Level.DEBUG, "Successfully finished closing connection pool [ size = %s ]", allConnections.size());
        inUseCnx.clear();
        availableCnx.clear();
        allConnections.clear();
        connectionCount = 0;
    }

    private void closeConnections(Iterator iter) {
        while (iter.hasNext()) {
            Connection connection = (Connection) iter.next();
            iter.remove();
            closeConnection(connection);
        }
    }

    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                synchronized (dummySyncObj) {
                    inUseCnx.remove(connection);
                    availableCnx.remove(connection);
                    allConnections.remove(connection);
                    connectionCount--;
                }
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception e) {
            }
        }
    }

    public synchronized String toString() {
        String info =
                "DBConnectionPool(" + jdbcUrl + "," + username + ")" +
                ", available=" + connectionCount +
                ", busy=" + inUseCnx.size() +
                ", max=" + maxConnections;
        return (info);
    }

    /**
     * Tests pool connectivity, using a reserved test connection. Under some circumstances
     * such as IDLE-TIMEOUT; other connections in the pool may disconnect, while the special
     * test connection is still valid. In those cases current thread connection is
     * passed to confirm validity of those other connections in the pool.  
     */    
    private boolean validateConnectivity(Connection testConn) {
        String testSql = null;
        Statement stmt = null;
        if (testConn == null) {
            testConn = testConnection;
        }
        try {
            if (testConn == null) {
                return false;
            }
            testSql = getTestSql(jdbcDriver);
            stmt = testConn.createStatement();
            stmt.execute(testSql);
            return true;
        } catch (Exception e) {
            logger.log(Level.WARN, "Connection is bad, need to reconnect to database: %s", this.jdbcUrl);
            logger.log(Level.WARN, "Connection test failed: %s %s", testSql, e.getMessage());
            return false;
        } finally {
            if (testConn != null) {
                try {
                    testConn.rollback();
                } catch (Exception e) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private boolean validateConnections() {
        String testSql = null;
        Statement stmt = null;
        Connection conn = null;
        try {
            int total = allConnections.size();
            int size = 1;
            if (checkRatio > 0) {
                size = total / checkRatio;
	            if (size == 0) {
	                size = 1;
	            }
            }
            // Show how many connections are being validated
            /*
            if (enableCheckConnectionLogging == true) {
            	logger.log(Level.DEBUG, "Validate " + size + " connection(s)");
            }
            */
            testSql = getTestSql(jdbcDriver);
            for (int i=0; i<size; i++) {
                conn = getConnection(true, false);
                // If the pool is already busy, don't validate
                if (conn == null) {
                	logger.log(Level.DEBUG, "No available connection - skip validation");
                    return true;
                }
                // Show the connection being validated
                if (enableCheckConnectionLogging == true) {
                	logger.log(Level.DEBUG, "Validate connection : " + conn);
                }
                stmt = conn.createStatement();
                stmt.execute(testSql);
                conn.rollback();
                stmt.close();
                stmt = null;
                free(conn);
                conn = null;
            }
            return true;
        } catch (Exception e) {
        	logger.log(Level.DEBUG, "Connection(" + conn + ") validation failed, need to reconnect to database : " + jdbcUrl);
            logger.log(Level.DEBUG, "Connection test failed: " + testSql + " " + e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (Exception e) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            }
            if (conn != null) {
                free(conn);
            }
        }
    }

	private static String getTestSql(String jdbcDriver) {
		if (jdbcDriver.toLowerCase().contains(RDBMSType.RDBMS_TYPE_NAME_DB2.toLowerCase())) {
			return rsProvider.getProperties().getProperty("be.backingstore.test.connections.sql",
					"select 10 from sysibm.sysdummy1");
		} else {
			if (jdbcDriver.toLowerCase().contains(RDBMSType.RDBMS_TYPE_NAME_ORACLE.toLowerCase())
					|| jdbcDriver.toLowerCase().contains(RDBMSType.RDBMS_TYPE_NAME_POSTGRES.toLowerCase())
					|| jdbcDriver.toLowerCase().contains(RDBMSType.RDBMS_TYPE_NAME_MYSQL.toLowerCase())
					|| jdbcDriver.toLowerCase().contains(RDBMSType.RDBMS_TYPE_NAME_SQLSERVER.toLowerCase())) {
				return rsProvider.getProperties().getProperty("be.backingstore.test.connections.sql",
						"select 1 from BEALIASES where 1=2");
			} else {
				return rsProvider.getProperties().getProperty("be.backingstore.test.connections.sql",
						"select 1 from BEALIASES where 1=2");
			}
		}
	}

    synchronized private boolean makeInitialConnections() {
        logger.log(Level.DEBUG, "Creating/recovering connections to database: %s", this.jdbcUrl);
        try {
            testConnection = makeNewConnection();
        } catch (SQLException e1) {
            logger.log(Level.WARN, "Failed to create/recover connections to database: %s", this.jdbcUrl);
            logger.log(Level.WARN, "Error - %s", e1.getMessage());
            return false;
        }

        for (int i = 0; i < initialConnections; i++) {
            try {
                addNewConnection();
            } catch (Exception e2) {
                closeAllConnections();
                logger.log(Level.DEBUG, "Failed to create/recover connections to database: %s", this.jdbcUrl);
                logger.log(Level.DEBUG, "Error: %s", e2.getMessage());
                return false;
            }
        }
        logger.log(Level.DEBUG, "Created/recovered connections to database: %s", this.jdbcUrl);
        setStatus(CONNECTED);
        return true;
    }

    private Connection makeNewConnection() throws SQLException {
        int attempts = 0;
        int maxattempts = 5;
        while (attempts < maxattempts) {
            attempts++;
            try {
                // Load database Driver if not already loaded
                String drvClass = getDriverClass(jdbcDriver);
                logger.log(Level.DEBUG, "Making connection for: JdbcUrl=%s, driver=%s, username=%s",
                        this.jdbcUrl, this.jdbcDriver, this.username);
                
                Class.forName(drvClass);
                Connection connection = null;
                String connprops = rsProvider.getProperties().getProperty("be.backingstore.connection.properties", null);
                
                Properties props = new Properties();
                if (sslConnectionInfo != null) {
                	props.putAll(sslConnectionInfo.getProperties());
                	sslConnectionInfo.loadSecurityProvider();
                } else {
                    if (this.jdbcDriver.toLowerCase().contains("mysql")) {
                        props.put("useSSL", "false");
                    }
                }
                
                if (connprops == null || connprops.isEmpty()) {
                	// Usual connection with username and password
                	props.put("user", username);
                	props.put("password", password);
                } else {
                    // Extended connection with additional properties 
                    // E.g. be.backingstore.connection.properties="defaultRowPrefetch=300,.."
                	props.put("user", username);
                	props.put("password", password);
                	String[] nvpairs = connprops.split(",");
                	for (int i = 0; i < nvpairs.length; i++) {
                		String[] pairs = nvpairs[i].split("=", 2);
                		if (pairs.length == 2) {
                			props.put(pairs[0], pairs[1]);
                		}
                	}
                }
                connection = DriverManager.getConnection(jdbcUrl, props);
                if (connection == null) {
                    logger.log(Level.WARN, "Making connection failed (attempt=%s)", attempts);
                    continue;
                }
                
                ConnectionPool.unlockDDConnection(connection);
                connection.setAutoCommit(false);
            
                logger.log(Level.DEBUG, "New connection %s has autocommit: %s", connection.getClass(),
                        (connection.getAutoCommit() ? "on" : "off"));
                return connection;
            } catch (ClassNotFoundException cnfe) {
            	throw new SQLException("Can't find class : " + cnfe.getMessage(), cnfe);
            } catch (SQLException sqle) {
                // Re-try helps in cases like 'ORA-12170: TNS:Connect timeout occurred'
                if (attempts < maxattempts) {
                    logger.log(Level.WARN, "Making connection failed (attempt=%s): %s", attempts, sqle.getMessage());
                    try { Thread.sleep(1000); } catch (Exception e) { }
                } else {
                    logger.log(Level.WARN, "Making connection failed (attempt=%s): %s", sqle, attempts, sqle.getMessage());
                    throw sqle;
                }
            } catch (InstantiationException e) {
            	throw new SQLException("JDBC connection failed. \n\n " + e.getMessage(),e);
 			} catch (IllegalAccessException e) {
 				throw new SQLException("JDBC connection failed. \n\n " + e.getMessage(),e);
			}
        }
        return null;
    }

    /*
     * Add a new connection to pool
     */
    private void addNewConnection() throws SQLException {
        Connection connection = makeNewConnection();
        synchronized (dummySyncObj) {
            if (connection != null) {
                allConnections.add(connection);
                availableCnx.add(connection);
                connectionCount++;
//                try {
//                    if (connection.getClass().getName().startsWith("oracle.jdbc.driver")) {
//                        logger.log(Level.DEBUG, "Oracle connection %s ", ((oracle.jdbc.driver.OracleConnection) connection).getProperties());
//                    }
//                } catch (Throwable e) {
//                    // Ignore strange NPE thrown-by PhysicalConnection.getProperties()
//                    /**
//                    java.lang.NullPointerException 
//                    at oracle.jdbc.driver.PhysicalConnection.getProperties(PhysicalConnection.java:2975)
//                    at oracle.jdbc.driver.T2CConnection.getProperties(T2CConnection.java:47)
//                    */
//                }
            }
        }
    }

    public static String getDriverClass(String jdbcDriver) {
        String drvClass = (String) jdbcDriverClasses.get(jdbcDriver);
        if (drvClass == null) {
            return jdbcDriver;
        }
        return drvClass;
    }

    /**
     * Periodically test the health of connections and if bad, try to reconnect.
     * Scheduled as a timer thread.
     */
    private class RefreshConnectionsThread extends java.util.TimerTask {
        int retryCount = 0;
        boolean connectedState = true;

        public void run() {
            boolean valid = true;
            if (enableCheckAllConnections == true) {
                valid = validateConnections();
            } else {
                valid = validateConnectivity(null);
            }
            if (!valid) {
                synchronized (DBConnectionPool.this) {
                    setStatus(DISCONNECTED);
                    synchronized (dummySyncObj) {
                        closeAllConnections();
                    }
                    makeInitialConnections();
                    if (isAlive()) {
                        logger.log(Level.INFO, "RefreshConnectionsThread restored connections");
                        reconnected();
                        connectedState = true;
                    } else if (connectedState) {
                        logger.log(Level.WARN, "RefreshConnectionsThread detected disconnection");
                        disconnected();
                        connectedState = false;
                    }
                }
            }
            if (connRetryCount > 0) {
                retryCount++;
                if (retryCount == connRetryCount) {
                    cancel();
                }
            }
        }
    }

    private void registerMBean() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("com.tibco.be:type=SharedResource,service=DBConnectionPool,name=" + key);
            mbs.registerMBean(this, name);
        } catch (Exception e) {
            logger.log(Level.WARN, e, e.getMessage());
        }
    }

    public int getCacheSize() {
        return maxConnections;
    }

    public String getFailoverInterval() {
        return "0 Seconds";
    }

    public boolean getIsUsingPrimary() {
        return true;
    }

    public int getNumberOfAvailableConnections() {
        //return (maxConnections-inUseCnx.size());
        return connectionCount;
    }

    public int getNumberOfConnectionsInUse() {
        return inUseCnx.size();
    }

    public String getPoolState() {
        if (testConnection == null) {
            return "Not Initialized";
        } else if (validateConnectivity(null)) {
            return "Available (Using " + (getIsUsingPrimary() ? "Primary" : "Secondary") + ")";
        } else {
            return "Not Available";
        }
    }

    public String getPrimaryURI() {
        return jdbcUrl;
    }

    public String getSecondaryURI() {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isAutoFailover() {
        return false;
    }

    public void recycle() throws SQLException {
        logger.log(Level.INFO, "DBConnectionPool recycling connections");
        synchronized (DBConnectionPool.this) {
            setStatus(DISCONNECTED);
            synchronized (dummySyncObj) {
                closeAllConnections();
            }
            makeInitialConnections();
        }
    }

    public void refreshConnections() {
        logger.log(Level.INFO, "DBConnectionPool refreshing connections");
        boolean valid = false;
        Connection conn = null;
        try {
            conn = getConnection();
            valid = validateConnectivity(conn);
        } catch (SQLException e) {
        } finally {
            free(conn);
        }
        if (valid) {
            // Return the connection to the pool
        } else {
            synchronized (DBConnectionPool.this) {
                setStatus(DISCONNECTED);
                synchronized (dummySyncObj) {
                    closeAllConnections();
                }
                makeInitialConnections();
            }
        }
    }

    public void switchToPrimary() throws SQLException, Exception {
        logger.log(Level.WARN, "DBConnectionPool doesn't support switchToPrimary()");
    }

    public void switchToSecondary() throws SQLException, Exception {
        logger.log(Level.WARN, "DBConnectionPool doesn't support switchToSecondary()");
    }
}
