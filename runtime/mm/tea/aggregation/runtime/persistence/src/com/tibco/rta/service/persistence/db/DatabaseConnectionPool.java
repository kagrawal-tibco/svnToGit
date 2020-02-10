package com.tibco.rta.service.persistence.db;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.Timer;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.sql.DataSource;

import oracle.jdbc.pool.OracleDataSource;

import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.exception.PersistenceStoreNotAvailableException;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.DataType;
import com.tibco.rta.util.PasswordObfuscation;

public class DatabaseConnectionPool implements DatabaseConnectionPoolMBean {

    public static int CONNECTED = 1;

    public static int DISCONNECTED = 0;


    protected static final String DATADIRECT_LICENSE_CODE = "TIBADB123QQQQQQQQQQQQQQQ";

    protected static final Logger LOGGER = LogManagerFactory.getLogManager()
            .getLogger(LoggerCategory.RTA_SERVICES_PERSISTENCE_CONNECTION.getCategory());

    protected static final ThreadLocal<Connection> CONNECTION_THREADLOCAL = new ThreadLocal<Connection>();

    protected int status = DISCONNECTED;

    protected boolean _rollbackAfterRelease = true;

    /**
     * Typically locking objects should be made final.
     */
    protected final Object reconnectLock = "dummy lock";

    // Used for lock
//    protected Object dummySyncObj = new Object();

    protected ArrayList<ConnectionPoolListener> lsnrs = new ArrayList<ConnectionPoolListener>();

    protected DataSource ds;


    protected String key, jdbcDriver, jdbcUrl, username, password;
    protected int maxConnections;
    protected int initialConnections;
    protected Vector<Connection> inUseCnx;
    protected BlockingQueue<Connection> availableCnx;
    protected Set<Connection> allConnections;

    protected int connectionCount = 0;

    // Used for validate database connection
    protected Connection testConnection;

    // How often to scan for database connection health (seconds)
    protected int checkConnectionsInterval = 60;

    // Following two settings are for checking multiple connections at the same
    // time
    protected boolean enableCheckAllConnections = false;
    protected int checkRatio = 5; // -1-1, 1-all, 2-half, 4-quarter, 5-fifth,
    // 6-sixth, 8-eighth, 16-sixteenth
    protected boolean enableCheckConnectionLogging = false;

    // How many times to retry. Default 0 -> do not retry
    protected int connRetryCount = 0;

    protected Timer reconnectTimer;

    protected Properties config;
    
    private boolean useSnapshotIsolationForSqlServer = false; 

    private final ReentrantLock cLock = new ReentrantLock();


    public DatabaseConnectionPool(Properties configuration) {
        this.config = configuration;
        init1();
    }


    public void init() throws Exception {
        LOGGER.log(Level.DEBUG, "Checking all jdbc connections is "
                + (enableCheckAllConnections ? "enabled" : "disabled"));
        availableCnx = new ArrayBlockingQueue<Connection>(maxConnections);
        inUseCnx = new Vector<Connection>();
        allConnections = Collections.synchronizedSet(new HashSet<Connection>());

        try {
            if (!makeInitialConnections()) {
                throw new Exception("An error occurred while creating database connection.");
            }
        } finally {
            if (connRetryCount != 0) {
                reconnectTimer = new Timer("DB_PoolRefresh", true);
                reconnectTimer.schedule(new RefreshConnectionsThread(),
                        checkConnectionsInterval, checkConnectionsInterval);
            }
            if (enableCheckAllConnections) {
                int total = allConnections.size();
                int size = 1;
                if (checkRatio > 0) {
                    size = total / checkRatio;
                    if (size == 0) {
                        size = 1;
                    }
                }
                LOGGER.log(Level.DEBUG, "Checking all jdbc connection ratio : "
                        + checkRatio + " with initialize check size = " + size);
            }
        }

        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG,
                    "Database connection pool initialized successfully");
            LOGGER.log(Level.DEBUG, "\tInitial   %s", initialConnections);
            LOGGER.log(Level.DEBUG, "\tMax       %s", maxConnections);
            LOGGER.log(Level.DEBUG, "\tCheckAll  %s", enableCheckAllConnections);
            LOGGER.log(Level.DEBUG, "\tCheckRatio %s", checkRatio);
            LOGGER.log(Level.DEBUG, "\tEnableCheckConnectionLogging %s", enableCheckConnectionLogging);
            LOGGER.log(Level.DEBUG, "\tCheckConnectionsInterval %s", checkConnectionsInterval);
            LOGGER.log(Level.DEBUG, "\tConnRetryCount %s", connRetryCount);

        }

    }

    public Connection getSqlConnection() throws SQLException {
        if (CONNECTION_THREADLOCAL.get() == null) {

            CONNECTION_THREADLOCAL.set(getConnection());
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Getting a new connection for this thread [%s]", CONNECTION_THREADLOCAL.get());
            }
        }
        Connection connection = CONNECTION_THREADLOCAL.get();
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Getting an existing connection for this thread [%s]", CONNECTION_THREADLOCAL.get());
        }

        return connection;
    }

    public Connection getCurrentConnection() {
        return CONNECTION_THREADLOCAL.get();
    }

    public boolean isStarted() {
        return (getStatus() == CONNECTED);
    }


    public boolean check(Exception sqlEx, Connection conn) {
        boolean success = false;
        try {
            if (conn == null) {
                try {
                    conn = getSqlConnection();
                } catch (DBConnectionsBusyException busyEx) {
                    // No need to refresh connections
                    success = true;
                }
            }
            if (conn != null) {
                Statement stmt = null;
                try {
                    stmt = conn.createStatement();
                    stmt.executeQuery(getTestSql(jdbcDriver));
                    success = true;
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                } finally {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (Exception e) {

                        }
                    }
                    if (conn != null) {
                        try {
                            releaseConnection(conn);
                        } catch (Exception e1) {
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.DEBUG, "Exception while cheking connection:", ex);
        }
        if (!success) {
            LOGGER.log(Level.DEBUG, "In Check, calling refresh conns & throwing DBNotAvailableException");
            releaseCurrentConnection();
            try {
                Thread.sleep(5000);
            } catch (Exception e) {

            }
            LOGGER.log(Level.DEBUG, "Trying to refresh connections...");

            refreshConnections();

            LOGGER.log(Level.DEBUG, "Trying to refresh connections complete...");
        }
        return success;
    }

    public boolean waitForReconnect(long timeout, long maxTries)
            throws InterruptedException {
        int numTries = 0;
        while (!isAlive()) {
            synchronized (reconnectLock) {
                reconnectLock.wait(timeout);
            }
            ++numTries;
            if (maxTries > 0) {
                if (numTries >= maxTries) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Remove connection from thread local without freeing it or releasing to
     * pool.
     */
    public void removeCurrentConnectionFromThreadLocal() {
        CONNECTION_THREADLOCAL.remove();
    }

    public void releaseCurrentConnection() {
        Connection cnx = CONNECTION_THREADLOCAL.get();
        if (cnx != null) {
            try {
                // Rollback any uncommitted txn, for safety
                try {
                    if (_rollbackAfterRelease) {
                        cnx.rollback();
                    }
                } catch (Exception e) {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG,
                                "DBAdapter release current jdbc connection (rollback) failed: "
                                        + e.getMessage(), e
                        );
                    } else {
                        LOGGER.log(Level.WARN,
                                "DBAdapter release current jdbc connection (rollback) failed: "
                                        + e.getMessage()
                        );
                    }
                }

                try {
                    free(cnx);
                } catch (Exception e) {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG,
                                "DBAdapter release current jdbc connection (free) failed: "
                                        + e.getMessage(), e
                        );
                    } else {
                        LOGGER.log(Level.WARN,
                                "DBAdapter release current jdbc connection (free) failed: "
                                        + e.getMessage()
                        );
                    }
                }
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Releasing an existing connection for this thread" + cnx);
                }
            } catch (Exception ex) {
                LOGGER.log(
                        Level.WARN,
                        "DBAdapter release current connection (close) failed: %s",
                        ex.getMessage());
            } finally {
                CONNECTION_THREADLOCAL.set(null);
            }
        }
    }


    /**
     * Will be used by query interfaces to release a specific connection not bound to the thread.
     *
     * @param cnx
     */
    public void releaseConnection(Connection cnx) {
        if (cnx != null) {
            try {
                try {
                    cnx.rollback();
                } catch (Exception e) {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(
                                Level.DEBUG,
                                "DBAdapter release jdbc connection (rollback) failed: ",
                                e);
                    } else {
                        LOGGER.log(
                                Level.WARN,
                                "DBAdapter release jdbc connection (rollback) failed: ",
                                e);
                    }
                }
                try {
                    free(cnx);
                } catch (Exception e) {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(
                                Level.DEBUG,
                                "DBAdapter release jdbc connection (free) failed: ",
                                e);
                    } else {
                        LOGGER.log(
                                Level.WARN,
                                "DBAdapter release jdbc connection (free) failed: ",
                                e);
                    }
                }
            } catch (Exception ex) {
                LOGGER.log(Level.WARN,
                        "DBAdapter release connection (close) failed: %s", ex,
                        ex.getMessage());
            }
        }
    }

    public void refreshConnections() {
        LOGGER.log(Level.DEBUG, "DBConnectionPool refreshing connections..");
        boolean valid = false;
        try {
            valid = validateConnectivity(getConnection());
        } catch (SQLException e) {
            LOGGER.log(Level.DEBUG, "DBConnectionPool refreshing connections failed.");
        }
        if (!valid) {
            recreateAllConnections(System.currentTimeMillis());
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
            LOGGER.log(Level.WARN, "Error getting connection", e);
        }
    }


    public synchronized String toString() {
        String info = "DBConnectionPool(%s, %s), available=%d, busy=%d, max=%d";
        return String.format(info, jdbcUrl, username, connectionCount, inUseCnx.size(), maxConnections);
    }


    public static String getDriverClass(String jdbcDriver) {
        return jdbcDriver;
    }


    public int getConnectionPoolSize() {
        return maxConnections;
    }


    public boolean getIsUsingPrimary() {
        return true;
    }

    public int getAvailableConnections() {
        return connectionCount;
        // return (maxConnections-inUseCnx.size());
    }

    public int getConnectionsInUse() {
        return inUseCnx.size();
    }

    public String getPoolState() {
        if (testConnection == null) {
            return "Not Initialized";
        } else if (validateConnectivity(null)) {
            return "Available (Using "
                    + (getIsUsingPrimary() ? "Primary" : "Secondary") + ")";
        } else {
            return "Not Available";
        }
    }

    public void switchToPrimary() throws SQLException, Exception {
        LOGGER.log(Level.WARN,
                "DBConnectionPool doesn't support switchToPrimary()");
    }

    public void switchToSecondary() throws SQLException, Exception {
        LOGGER.log(Level.WARN,
                "DBConnectionPool doesn't support switchToSecondary()");
    }


    protected String getDBDataType(DataType dataType) {
        String dt = null;
        boolean isOracle = jdbcDriver.contains("Oracle") || jdbcDriver.contains("oracle");
        boolean isPostgres = jdbcDriver.contains("postgresql") || jdbcDriver.contains("Postgresql");
        switch (dataType) {
            case BOOLEAN:
                dt = "CHAR(1)";
                break;
            case DOUBLE:
                if (isOracle) {
                    dt = "NUMBER(16)";
                } else if (isPostgres) {
                    dt = "FLOAT8";
                } else {
                    dt = "DOUBLE";
                }
                break;
            case INTEGER:
                dt = isOracle ? "NUMBER(12)" : "INT";
                break;
            case LONG:
                dt = isOracle ? "NUMBER(14)" : "BIGINT";
                break;
            case STRING:
                dt = "VARCHAR(256)";
                break;
        }
        return dt;
    }

    protected String getBinaryDataType() {
        boolean isPostgres = jdbcDriver.contains("postgresql") || jdbcDriver.contains("Postgresql");
        if (isPostgres) {
            return "BYTEA";
        }
        return "BLOB";
    }

    protected String getDatabaseType() {
        if (jdbcDriver.contains("Oracle") || jdbcDriver.contains("oracle")) {
            return "Oracle";
        } else if (jdbcDriver.contains("Postgresql") || jdbcDriver.contains("postgresql")) {
            return "postgreSql";
        } else if (jdbcDriver.contains("H2") || jdbcDriver.contains("h2")) {
            return "H2";
        } else if (jdbcDriver.contains("mysql") || jdbcDriver.contains("MYSQL")) {
            return "mysql";
        } else if (jdbcDriver.contains("sqlserver") || jdbcDriver.contains("SQLSERVER")) {
            return "sqlServer";
        } else if (jdbcDriver.contains("db2") || jdbcDriver.contains("DB2")) {
            return "DB2";
        }
        return "Other";
    }


    private void init1() {

        this.key = (String) ConfigProperty.RTA_JDBC_KEY.getValue(config);
        this.jdbcUrl = (String) ConfigProperty.RTA_JDBC_URL.getValue(config);
        if (jdbcUrl == null || jdbcUrl.isEmpty()) {
            throw new RuntimeException(ConfigProperty.RTA_JDBC_URL.getPropertyName() + " cannot be null or empty.");
        }
        LOGGER.log(Level.INFO, "Configuration: Database JDBC URL [%s]", jdbcUrl);

        this.username = (String) ConfigProperty.RTA_JDBC_USER.getValue(config);
        LOGGER.log(Level.INFO, "Configuration: Database username [%s]", username);

        this.password = (String) ConfigProperty.RTA_JDBC_PASSWORD.getValue(config);
        try {
            this.password = PasswordObfuscation.decrypt(this.password);
        } catch (Exception e3) {
            LOGGER.log(Level.WARN, "Cannot decrypt password, will assume plain-text and continue..", e3);
        }

        this.jdbcDriver = (String) ConfigProperty.RTA_JDBC_DRIVER.getValue(config);
        if (jdbcDriver == null || jdbcDriver.isEmpty()) {
            throw new RuntimeException(ConfigProperty.RTA_JDBC_DRIVER.getPropertyName() + " cannot be null or empty.");
        }
        LOGGER.log(Level.INFO, "Configuration: Database driver [%s]", jdbcDriver);
        
        if (jdbcDriver.toLowerCase().contains("sqlserver") && !DatabasePersistenceService.usePK) {
        	// To disable MS SQL server Read committed snapshot isolation set -DSQLSERVER_SNAPSHOT_ISOLATION=false
        	useSnapshotIsolationForSqlServer = "true".equalsIgnoreCase(System.getProperty("SQLSERVER_SNAPSHOT_ISOLATION", "true"));
        	if (LOGGER.isEnabledFor(Level.DEBUG)) {
    			LOGGER.log(Level.DEBUG, "MS Sql Server READ_COMMITTED_SNAPSHOT ISOLATION is %s", useSnapshotIsolationForSqlServer ? "ENABLED" : "DISABLED");
    		}
        }		

        // Validate initial connection size
        try {
            this.initialConnections = Integer.valueOf((String) ConfigProperty.RTA_JDBC_INIT_CONNECTION.getValue(config));
            LOGGER.log(Level.INFO, "Configuration: Database initial connections driver [%d]", initialConnections);
        } catch (NumberFormatException e1) {
            LOGGER.log(Level.WARN, "Bad Configuration: Using default. Database initial connections[%d]", initialConnections);
        }

        try {
            this.maxConnections = Integer.valueOf((String) ConfigProperty.RTA_JDBC_MAX_CONNECTION.getValue(config));
            LOGGER.log(Level.INFO, "Configuration: Database maximum connections [%d]", maxConnections);

        } catch (NumberFormatException e2) {
            LOGGER.log(Level.WARN, "Bad Configuration: Using default. Database maximum connections [%d]", maxConnections);
        }
        if (this.initialConnections > maxConnections) {
            this.initialConnections = maxConnections;
        }

        try {
            enableCheckAllConnections = Boolean.valueOf(config.getProperty(
                    "spm.backingstore.test.connections.checkall", "false"));
        } catch (Exception e) {
        }

        try {
            checkRatio = Integer.valueOf(config.getProperty("rta.backingstore.test.connections.checkratio", "5"));
            if (checkRatio <= 0) {
                checkRatio = -1;
            }
        } catch (Exception e) {
        }

        try {
            enableCheckConnectionLogging = Boolean.valueOf(config.getProperty(
                    "rta.backingstore.test.connections.logall", "false"));
        } catch (Exception e) {
        }

        try {
            String checkConnStr = config.getProperty("rta.backingstore.test.connections.interval", "60");
            checkConnectionsInterval = Integer.parseInt(checkConnStr);
        } catch (Exception e) {
        }

        // Convert connections interval to milliseconds
        checkConnectionsInterval = checkConnectionsInterval * 1000;

        try {
            String retryStr = config.getProperty("rta.backingstore.connection.retry.count", "-1");
            connRetryCount = Integer.parseInt(retryStr);
        } catch (Exception e) {
        }
        registerMBean(config);
    }


    synchronized private int getStatus() {
        return status;
    }

    synchronized private void setStatus(int status) {
        this.status = status;
    }

    private void free(Connection connection) {

        try {
            while (true) {
                if (cLock.tryLock(1, TimeUnit.SECONDS)) {

                    try {
                        if (allConnections.contains(connection)) {
                            // Only add it back to the availableCnx if it's in
                            // the
                            // inUseCnx.
                            if (inUseCnx.removeElement(connection)) {
                                availableCnx.add(connection);
                            }

                        } else {
                            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                                LOGGER.log(Level.DEBUG,
                                        "Freeing connection >>> [%s]",
                                        connection);
                            }
                            closeConnection(connection);
                        }
                    } finally {
                        cLock.unlock();
                    }

                    break;
                }
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    private Connection getConnection() throws SQLException {
        return getConnection(true, true);
    }

    private Connection getConnection(boolean typeFlag, boolean growPool)
            throws SQLException {

        if (getStatus() == DISCONNECTED) {
            throw new PersistenceStoreNotAvailableException(
                    "Connection pool status is disconnected");
        }

        while (true) {
            try {
                if (cLock.tryLock(1, TimeUnit.SECONDS)) {

                    try {
                        Connection conn = availableCnx.poll();
                        if (conn == null && growPool) {
                            if (connectionCount < maxConnections) {
                                addNewConnection();
                                conn = availableCnx.poll();
                            } else {
                                try {
                                    conn = availableCnx.take();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if (growPool && conn == null) {
                            throw new SQLException(
                                    "Cannot get/create connection for database "
                                            + jdbcUrl
                            );
                        } else if (conn != null) {
                            inUseCnx.addElement(conn);
                        }

                        return conn;

                    } finally {
                        cLock.unlock();
                    }
                }
            } catch (InterruptedException e) {
                // // TODO Auto-generated catch block
                // e.printStackTrace();
            }
        }

    }


    private void closeAllConnections() {
        try {
            if (testConnection != null && !testConnection.isClosed()) {
                testConnection.close();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, "Error getting connection", e);
        }
        closeConnections(allConnections.iterator());
        inUseCnx.clear();
        availableCnx.clear();
        allConnections.clear();
        connectionCount = 0;
    }

    private void closeConnection(Connection connection) {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Closing connection >>> [%s]", connection);
        }
        if (connection != null) {
        	 try {
                 if (!connection.isClosed()) {
                     connection.rollback();
                 }
             } catch (Exception e) {
             }
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception e) {
            }
        }
    }

    private void closeConnections(Iterator iter) {
        while (iter.hasNext()) {
            Connection connection = (Connection) iter.next();
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Attempting close connection >>> [%s]", connection);
            }
            closeConnection(connection);
        }
    }

    /**
     * Tests pool connectivity, using a reserved test connection. Under some
     * circumstances such as IDLE-TIMEOUT; other connections in the pool may
     * disconnect, while the special test connection is still valid. In those
     * cases current thread connection is passed to confirm validity of those
     * other connections in the pool.
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
            LOGGER.log(Level.ERROR,
                    "Connection is bad, need to reconnect to database: %s",
                    this.jdbcUrl);
            LOGGER.log(Level.ERROR, "Connection test failed: %s %s", testSql,
                    e.getMessage());
            return false;
        } finally {
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

            testSql = getTestSql(jdbcDriver);
            for (int i = 0; i < size; i++) {
                conn = getConnection(true, false);
                // If the pool is already busy, don't validate
                if (conn == null) {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG,
                                "No available connection - skip validation");
                    }
                    return true;
                }
                // Show the connection being validated
                if (enableCheckConnectionLogging) {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "Validate connection : " + conn);
                    }
                }
                stmt = conn.createStatement();
                stmt.execute(testSql);
                stmt.close();
                stmt = null;
                free(conn);
                conn = null;
            }
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Connection() validation failed, need to reconnect to database : %s %s", conn, jdbcUrl);
            LOGGER.log(Level.ERROR, "Connection test failed: %s", testSql, e);
            return false;
        } finally {
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

    private String getTestSql(String jdbcDriver) {
        if (jdbcDriver.toLowerCase().contains("oracle")) {
            return config.getProperty("spm.backingstore.test.connections.sql",
                    "select 1 from dual");
        } else if (jdbcDriver.toLowerCase().contains("sqlserver")) {
            return config.getProperty("spm.backingstore.test.connections.sql",
                    "select 1");
        } else if (jdbcDriver.toLowerCase().contains("org.h2.driver")) {
            return config.getProperty("spm.backingstore.test.connections.sql",
                    "select 1");
        } else if (jdbcDriver.toLowerCase().contains("postgresql")) {
            return config.getProperty("spm.backingstore.test.connections.sql",
                    "select 1");
        } else if (jdbcDriver.toLowerCase().contains("mysql")) {
            return config.getProperty("spm.backingstore.test.connections.sql",
                    "select 1");
        } else if (jdbcDriver.toLowerCase().contains("sqllite")) {
            return config.getProperty("spm.backingstore.test.connections.sql",
                    "select 1");
        } else if (jdbcDriver.toLowerCase().contains("db2")) {
            return config.getProperty("spm.backingstore.test.connections.sql",
                    "select 10 from sysibm.sysdummy1");
        } else {
            return config.getProperty("spm.backingstore.test.connections.sql");
        }
    }

    private boolean makeInitialConnections() {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG,
                    "Creating/recovering connections to database: %s", jdbcUrl);
        }
        try {
            testConnection = makeNewConnection();
        } catch (SQLException e1) {
            LOGGER.log(Level.ERROR,
                    "Failed to create/recover connections to database: %s", e1, jdbcUrl);
            return false;
        }

        for (int i = 0; i < initialConnections; i++) {
            try {
                addNewConnection();
            } catch (Exception e2) {
                closeAllConnections();
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG,
                            "Failed to create/recover connections to database: %s",
                            this.jdbcUrl);
                    LOGGER.log(Level.DEBUG, "Error: %s", e2);
                }
                return false;
            }
        }
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG,
                    "Created/recovered connections to database: %s", this.jdbcUrl);
        }
        setStatus(CONNECTED);
        return true;
    }

    private Connection makeNewConnection() throws SQLException {
        try {

            Connection connection;
            if (jdbcDriver.toLowerCase().contains("oracle")) {
                connection = createConnectionOracle();
            } else {
                connection = createConnectionOther();
                // connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);                
                
                if (useSnapshotIsolationForSqlServer) {
                	// For MS SQL enable SQLServerConnection.TRANSACTION_SNAPSHOT Isolation i.e. 4096
                    // To enable READ_COMMITTED_SNAPSHOT for SPM database run below SQLs as Database System Administrator (sa):
                    // ALTER DATABASE <spmDB name> SET READ_COMMITTED_SNAPSHOT ON;
                    // ALTER DATABASE <spmDB name> SET ALLOW_SNAPSHOT_ISOLATION ON;
                	connection.setTransactionIsolation(4096);
                }                
            }
            if (connection != null) {
                connection.setAutoCommit(false);
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "New connection %s has autocommit: %s",
                            connection.getClass(), (connection.getAutoCommit() ? "on"
                                    : "off")
                    );
                }
            }
            return connection;
        } catch (ClassNotFoundException cnfe) {
            throw new SQLException("Can't find class for driver: " + jdbcDriver);
        } catch (SQLException sqle) {
            LOGGER.log(Level.WARN,
                    "Making connection failed : " + sqle.getMessage());
            throw sqle;
        }
    }


    private Connection createConnectionOther()
            throws ClassNotFoundException, SQLException {
        // Load database Driver if not already loaded
        String drvClass = getDriverClass(jdbcDriver);
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(
                    Level.DEBUG,
                    "Making connection for: JdbcUrl=%s, driver=%s, username=%s",
                    this.jdbcUrl, this.jdbcDriver, this.username);
        }
        Class.forName(drvClass);
        Connection connection = null;
        String connprops = config.getProperty(
                "spm.backingstore.connection.properties", null);
        if (connprops == null || connprops.isEmpty()) {
            connection = DriverManager.getConnection(jdbcUrl, username,
                    password);
            unlockDD(connection);
        } else {
            Properties props = new Properties();
            props.put("user", username);
            props.put("password", password);
            String[] nvpairs = connprops.split(",");
            for (int i = 0; i < nvpairs.length; i++) {
                String[] pairs = nvpairs[i].split("=", 2);
                if (pairs.length == 2) {
                    props.put(pairs[0], pairs[1]);
                }
            }
            connection = DriverManager.getConnection(jdbcUrl, props);
        }
        return connection;
    }


    private Connection createConnectionOracle()
            throws ClassNotFoundException, SQLException {

        Connection connection = null;

        String connprops = config.getProperty(ConfigProperty.RTA_JDBC_CONNECTION_PROPERTY.getPropertyName(), null);
        if (connprops == null || connprops.isEmpty()) {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            unlockDD(connection);
        } else {
            Properties props = new Properties();
            props.put("user", username);
            props.put("password", password);


            String[] nvpairs = connprops.split(",");
            for (int i = 0; i < nvpairs.length; i++) {
                String[] pairs = nvpairs[i].split("=", 2);
                if (pairs.length == 2) {
                    props.put(pairs[0], pairs[1]);
                }
            }

            //for oracle, set a default value of read time out of 10 minutes. This will prevent the system from perpetually hanging in case of network
            //disconnects. Other data bases may handle this in other ways.. have to treat it on a case by case basis.
            if (props.getProperty("oracle.jdbc.ReadTimeout") == null) {
                props.put("oracle.jdbc.ReadTimeout", "600000");
            }


            if (ds == null) {
                ds = new OracleDataSource();
            }

            OracleDataSource ods = (OracleDataSource) ds;
            ods.setURL(jdbcUrl);
            ods.setConnectionProperties(props);
            connection = ods.getConnection();
        }
        return connection;
    }


    private void unlockDD(Connection connection) throws SQLException {
        String clsNm = "com.ddtek.jdbc.extensions.ExtEmbeddedConnection";
        try {
            Class<?> clz = Class.forName(clsNm);

            if (clz.isAssignableFrom(connection.getClass())) {
                Method m = clz.getMethod("unlock", String.class);
                if (m != null) {
                    Object unlockedObj = m.invoke(connection, DATADIRECT_LICENSE_CODE);
                    if (unlockedObj instanceof Boolean) {
                        boolean unlocked = (Boolean) unlockedObj;
                        if (unlocked) {
                            LOGGER.log(Level.DEBUG, "DataDirect driver unlock successful.");
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            //ignore it. It means its not a data direct driver in use..
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "DataDirect driver unlock failed.", e);
        }
    }

    /*
     * Add a new connection to pool
     */
    private void addNewConnection() throws SQLException {
        Connection connection = makeNewConnection();
        while (true) {
            try {
                if (cLock.tryLock(1, TimeUnit.SECONDS)) {

                    try {
                        allConnections.add(connection);
                        availableCnx.add(connection);
                        connectionCount++;
                    } finally {
                        cLock.unlock();
                    }

                    break;
                }
            } catch (InterruptedException e) {

            }
        }
    }

    /**
     * Periodically test the health of connections and if bad, try to reconnect.
     * Scheduled as a timer thread.
     */
    private class RefreshConnectionsThread extends java.util.TimerTask {
        int retryCount = 0;

        public void run() {
            boolean valid;
            if (enableCheckAllConnections) {
                valid = validateConnections();
            } else {
                valid = validateConnectivity(null);
            }
            if (!valid) {
                recreateAllConnections(System.currentTimeMillis());
            }
            if (connRetryCount > 0) {
                retryCount++;
                if (retryCount == connRetryCount) {
                    cancel();
                }
            }
        }
    }

    long lastReconnectTime = 0;

    private void recreateAllConnections(long timestamp) {
        while (true) {
            try {
                if (cLock.tryLock(1, TimeUnit.SECONDS)) {

                    try {

                        if (timestamp < lastReconnectTime) {
                            LOGGER.log(Level.DEBUG, "Already reconnected..");
                            return;
                        }

                        setStatus(DISCONNECTED);

                        closeAllConnections();

                        while (!makeInitialConnections()) {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                            }
                        }
                        if (timestamp > lastReconnectTime) {
                            lastReconnectTime = timestamp;
                        }
                        LOGGER.log(Level.DEBUG, "Database reconnected successfully.");
                    } catch (Exception e) {

                    } finally {
                        cLock.unlock();
                    }
                    break;
                }
            } catch (InterruptedException e) {

            }
        }
    }

    private void registerMBean(Properties configuration) {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            String mbeanPrefix = (String) ConfigProperty.BE_TEA_AGENT_SERVICE_MBEANS_PREFIX.getValue(configuration);
            ObjectName name = new ObjectName(
            		mbeanPrefix + ".persistence:type=DatabaseConnectionPool");
            mbs.registerMBean(this, name);
        } catch (Exception e) {
            LOGGER.log(Level.WARN, "Error getting connection", e);
        }
    }
}

