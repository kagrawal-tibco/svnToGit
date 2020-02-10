package com.tibco.cep.modules.db.service;

import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.tibco.be.common.ConnectionPool;
import com.tibco.be.jdbcstore.ssl.JdbcSSLConnectionInfo;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleServiceProvider;

public class JDBCConnectionPool implements JDBCConnectionPoolMBean {

    protected final static int CONNECTED = 1;
    protected final static int DISCONNECTED = 0;

    protected final static String TSI_SQL_SERVER_DRIVER="tibcosoftwareinc.jdbc.sqlserver.SQLServerDriver";
    protected final static String TSI_ORACLE_SERVER_DRIVER="tibcosoftwareinc.jdbc.oracle.OracleDriver";
    protected final static String TSI_SYBASE_SERVER_DRIVER="tibcosoftwareinc.jdbc.sybase.SybaseDriver";
    protected final static String TSI_DB2_SERVER_DRIVER="tibcosoftwareinc.jdbc.db2.DB2Driver";

    protected final static HashMap jdbcDriverClasses = new HashMap();
    static {
        jdbcDriverClasses.put("tibcosoftwareinc.jdbc.oracle.OracleDriver (RAC)","tibcosoftwareinc.jdbc.oracle.OracleDriver");
        jdbcDriverClasses.put("oracle.jdbc.OracleDriver (thin)","oracle.jdbc.OracleDriver");
        jdbcDriverClasses.put("oracle.jdbc.OracleDriver (oci)","oracle.jdbc.OracleDriver");
        jdbcDriverClasses.put(TSI_SQL_SERVER_DRIVER, TSI_SQL_SERVER_DRIVER);
        jdbcDriverClasses.put(TSI_ORACLE_SERVER_DRIVER, TSI_ORACLE_SERVER_DRIVER);
        jdbcDriverClasses.put(TSI_SYBASE_SERVER_DRIVER, TSI_SYBASE_SERVER_DRIVER);
        jdbcDriverClasses.put(TSI_DB2_SERVER_DRIVER, TSI_DB2_SERVER_DRIVER);
    }
    
    protected String jdbcDriver, jdbcUrl, username, password, poolName;
    protected int maxConnections, initialConnections;
    
    private boolean enableCheckAllConnections = false;
    private int checkRatio = 5; //-1-1, 1-all, 2-half, 4-quarter, 5-fifth, 6-sixth, 8-eighth, 16-sixteenth
    private boolean enableCheckConnectionLogging = false;

    //how often to scan for DB connection health.
    protected int checkConnectionsInterval = 60; //60 secs

    //how many times to retry. Default 0 -> do not retry
    protected int connRetryCount = -1;

    protected Timer reconnectTimer;
    protected int status = DISCONNECTED;
    
    protected static Logger logger;
    protected static RuleServiceProvider rsProvider;

    private Vector inUseCnx;
    private BlockingQueue availableCnx;
    private Set allConnections;

    private int connectionCount = 0;

    //Used for lock
    private Object dummySyncObj = new Object();

    //Used for validate db connection
    private Connection testConnection;
    private Map idleCnxMap = new ConcurrentHashMap();

    private int minConnections = 0;
    private long inactivityTimeout = 0;
    private long propertyChkInterval = 900;
    private Timer propertyChkTimer;
    private long waitTimeout = -1;
    protected JdbcSSLConnectionInfo sslConnectionInfo;

    public JDBCConnectionPool() {

    }

    public JDBCConnectionPool(String resourceURI, String driver, String url, String username, String password, int initialConnections, int maxConnections, int timeout, JdbcSSLConnectionInfo sslConnectionInfo) {

        // Support for Oracle-11g
        if (driver.startsWith("oracle.jdbc.driver")) {
            this.jdbcDriver = driver.replace("oracle.jdbc.driver", "oracle.jdbc");
            logger.log(Level.WARN, "JDBC connection pool is configured using deprecated package 'oracle.jdbc.driver'");
        } else {
            this.jdbcDriver = driver;
        }
        this.jdbcUrl = url;
        this.poolName = "be.dbconcepts:"+resourceURI;
        this.username = username;
        this.password = password;
        this.maxConnections = maxConnections;
        this.initialConnections = initialConnections;
        this.sslConnectionInfo = sslConnectionInfo;
        
        if (rsProvider == null) {
            rsProvider = DBConnectionFactory.getInstance().getModuleManager().getRuleServiceProvider();
            logger = rsProvider.getLogger(JDBCConnectionPool.class);
        }

        //naming similar to bs pool, for consistency
        String propVal = rsProvider.getProperties().getProperty("be.dbconcepts.pool.initial", ""+this.initialConnections).trim();
        this.initialConnections = Integer.parseInt(propVal);
        if (this.initialConnections > maxConnections) {
            this.initialConnections = maxConnections;
        }
        
        try {
			this.initialConnections = Integer.parseInt(rsProvider.getProperties().getProperty("be.dbconcepts.pool.initial", ""+initialConnections).trim());
		} catch (NumberFormatException e1) {
		}
		
        try {
        	this.maxConnections = Integer.parseInt(rsProvider.getProperties().getProperty("be.dbconcepts.pool.max", ""+maxConnections).trim());
		} catch (NumberFormatException e1) {
		}
		
        try {
        	this.minConnections = Integer.parseInt(rsProvider.getProperties().getProperty("be.dbconcepts.pool.min", "0").trim());
        } catch (Exception e) {
        }
        
        try {
            this.enableCheckAllConnections = Boolean.valueOf(rsProvider.getProperties().getProperty("be.dbconcepts.connections.checkall", "false"));
        } catch (Exception e) {
        }

        try {
            this.checkRatio = Integer.valueOf(rsProvider.getProperties().getProperty("be.dbconcepts.connections.checkratio", "5"));
            if (checkRatio <= 0) {
                checkRatio = -1;
            }
        } catch (Exception e) {
        }

        try {
            this.enableCheckConnectionLogging = Boolean.valueOf(rsProvider.getProperties().getProperty("be.dbconcepts.connections.logall", "false"));
        } catch (Exception e) {
        }
        		
        try {
        	checkConnectionsInterval = Integer.parseInt(rsProvider.getProperties().getProperty("be.dbconcepts.connection.check.interval", "60").trim());
            checkConnectionsInterval *= 1000; //convert to milliseconds
        } catch (Exception e) {
        }

        try {
        	connRetryCount = Integer.parseInt(rsProvider.getProperties().getProperty("be.dbconcepts.connection.retry.count", "-1").trim());
        } catch (Exception e) {
        }

        try {
        	inactivityTimeout = Integer.parseInt(rsProvider.getProperties().getProperty("be.dbconcepts.pool.inactivityTimeout", "0").trim());
        	inactivityTimeout *= 1000; //convert to milliseconds
        } catch (Exception e) {
        }
        
        try {
        	propertyChkInterval = Integer.parseInt(rsProvider.getProperties().getProperty("be.dbconcepts.pool.PropertyCheckInterval", "900").trim());
        	propertyChkInterval *= 1000; //convert to milliseconds
        } catch (Exception e) {
        }
        
        try {
        	waitTimeout =Integer.parseInt(rsProvider.getProperties().getProperty("be.dbconcepts.pool.waitTimeout", "-1").trim());
        	if (waitTimeout != -1) {
        		waitTimeout *= 1000; // convert to milliseconds
        	}
        } catch (Exception e) {
        }
        
        registerMBean(this.poolName);
    }

    public void init() {

        availableCnx = new ArrayBlockingQueue(maxConnections);
        inUseCnx = new Vector();
        allConnections = Collections.synchronizedSet(new HashSet());

        try {
            logger.log(Level.INFO, "Registering DataSource, rsp=%s, key=%s, uri=%s, user=%s",
                    rsProvider.getName(), this.poolName, this.jdbcUrl, this.username);
            if (!makeInitialConnections()) {
                throw new Exception("Error while creating connection " + this.jdbcUrl);
            }
            logger.log(Level.INFO, "[%s] initialized successfully", this);
        } catch (Exception e) {
            logger.log(Level.ERROR, e, "Failed to initialize pool [%s] during startup", this);
        } finally {
            if (connRetryCount != 0) {
                reconnectTimer = new Timer("connection-check-timer-" + jdbcUrl, true);
                reconnectTimer.schedule(new RefreshConnectionsThread(), checkConnectionsInterval, checkConnectionsInterval);
            }
        }

        if (propertyChkInterval != -1 && inactivityTimeout > 0) {
        	try {
        		propertyChkTimer = new Timer("property-check-timer-" + jdbcUrl, true);
        		propertyChkTimer.schedule (new PropertyCheckIntervalThread(), propertyChkInterval, propertyChkInterval);
        	} catch (Exception e) {
        		logger.log(Level.ERROR, e, "Failed to start property check timer for pool [%s]", this);
        	}
        }
    }
    
    public synchronized Connection getConnection() throws SQLException {
    	return getConnection(true);
    }

    public synchronized Connection getConnection(boolean growPool) throws SQLException {
    	if (getStatus() == DISCONNECTED) {
    		logger.log(Level.WARN, "** Connection pool status is disconnected.");
    		return null;
    	}

    	Connection conn = (Connection) this.availableCnx.poll();
    	if (conn == null && growPool) {
    		if (connectionCount < maxConnections) {
    			addNewConnection();
    			conn = (Connection)this.availableCnx.poll();
    		} else {
    			try {
    				if (waitTimeout != -1) {
    					conn = (Connection) this.availableCnx.poll(waitTimeout, TimeUnit.MILLISECONDS);
    				} else {
    					conn = (Connection) this.availableCnx.take(); //wait forever
    				}
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    	if (conn == null && growPool) {
    		throw new SQLException ("Cannot get/create connection for database " + jdbcUrl);
    	} else if (conn != null) {
    		synchronized (dummySyncObj) {
    			inUseCnx.addElement(conn);
    			idleCnxMap.remove(conn);
    		}
    	}
    	return conn;
    }
     
    public void free(Connection connection) {
        //System.out.println("My ID:"+Thread.currentThread()+ " releasing connection...");
        synchronized (dummySyncObj) {
            if (allConnections.contains(connection)) {
                inUseCnx.removeElement(connection);
                idleCnxMap.put(connection, new Long(System.currentTimeMillis()));
                availableCnx.add(connection);
                //System.out.println("My ID:"+Thread.currentThread()+ " connection released...");
            } else {
                //System.out.println("My ID:"+Thread.currentThread()+ " unknown connection closed...");
                closeConnection(connection);
            }
        }
    }

    public synchronized int getStatus() {
        return status;
    }

    protected synchronized void setStatus (int status) {
        this.status = status;
    }

    private void closeAllConnections() {
        try {
            if(testConnection != null && !testConnection.isClosed()){
                testConnection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        synchronized (dummySyncObj) {
        	closeConnections(allConnections.iterator());
			inUseCnx.clear();
			availableCnx.clear();
			idleCnxMap.clear();
			allConnections.clear();
		}
		connectionCount = 0;
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

    private void closeConnection (Connection c) {
        if (c != null) {
            try {
                if (!c.isClosed()) {
                    c.close();
                }
            } catch (Exception e) {
            }
        }
    }

    private void closeConnections(Iterator iter) {
        synchronized (dummySyncObj) {
			while (iter.hasNext()) {
				Connection connection = (Connection) iter.next();
				availableCnx.remove(connection);
				inUseCnx.remove(connection);
				idleCnxMap.remove(connection);
				closeConnection(connection);
				connectionCount--;
			}
		}
    }

    public synchronized String toString() {
        String info =
                "JDBCConnectionPool(" + jdbcUrl + "," + username + ")" +
                ", available=" + connectionCount +
                ", busy=" + inUseCnx.size() +
                ", max=" + maxConnections;
        return (info);
    }

    /**
     * Tests pool connectivity, using a reserved test connection or through one that is passed. This is to check connection pool validity.  
     * 
     * @param testConn
     * @return
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
            logger.log(Level.DEBUG, "Connection is bad, need to reconnect to database: %s", this.jdbcUrl);
            logger.log(Level.DEBUG, "Connection test failed: %s %s", testSql, e.getMessage());
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
    
    /**
     * Tests the connections in the pool based on the check ratio defined.
     * 
     * @return
     */
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
            for (int i=0; i<size; i++) {
                conn = getConnection(false);
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
        if (jdbcDriver.toLowerCase().contains("oracle")) {
            return rsProvider.getProperties().getProperty("be.dbconcepts.test.connections.sql", "select 1 from dual");
        } else if (jdbcDriver.toLowerCase().contains("sqlserver")) {
            return rsProvider.getProperties().getProperty("be.dbconcepts.test.connections.sql","select count(*) from sysobjects");
        } else if (jdbcDriver.toLowerCase().contains("db2")) {
            return rsProvider.getProperties().getProperty("be.dbconcepts.test.connections.sql", "select count(*) from sysibm.systables");
        } else if (jdbcDriver.toLowerCase().contains("sybase")) {
            return rsProvider.getProperties().getProperty("be.dbconcepts.test.connections.sql", "select getdate())");
        } else if (jdbcDriver.toLowerCase().contains("mysql")) {
            return rsProvider.getProperties().getProperty("be.dbconcepts.test.connections.sql", "select 1");
        } else {
            return rsProvider.getProperties().getProperty("be.dbconcepts.test.connections.sql");
        }
    }

    synchronized private boolean makeInitialConnections() {

        try {
            testConnection = makeNewConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.log(Level.DEBUG, "Failed to create/recover connections to database: %s", this.jdbcUrl);
            return false;
        }

        for (int i = 0; i < initialConnections; i++) {
            try {
                addNewConnection();
            } catch (Exception e2) {
                e2.printStackTrace();
                closeAllConnections();
                logger.log(Level.DEBUG, "Failed to create/recover connections to database: %s", this.jdbcUrl);
                return false;
            }
        }
        logger.log(Level.DEBUG, "Created/recovered connections to database: %s", this.jdbcUrl);
        setStatus(CONNECTED);
        return true;
    }

    private Connection makeNewConnection() throws SQLException {

        try {
            // Load database Driver if not already loaded
            String drvClass = getDriverClass(jdbcDriver);
            logger.log(Level.DEBUG, "Making connection for: JdbcUrl=%s, username=%s", this.jdbcUrl, this.username);
            Class.forName(drvClass);
            
            Connection connection;
            if (sslConnectionInfo != null) {
            	sslConnectionInfo.setUser(username);
				sslConnectionInfo.setPassword(password);
				sslConnectionInfo.loadSecurityProvider();
				connection = DriverManager.getConnection(jdbcUrl, sslConnectionInfo.getProperties());
            } else {
            	connection = DriverManager.getConnection(jdbcUrl, username, password);
            }
            ConnectionPool.unlockDDConnection(connection);
            return connection;

        } catch (ClassNotFoundException cnfe) {
            logger.log(Level.WARN, "Can't find class for driver: %s", this.jdbcDriver);
            throw new SQLException("Can't find class for Driver: " + jdbcDriver);
        } catch (InstantiationException | IllegalAccessException e) {
        	logger.log(Level.WARN, "Error loading security provider - " + e.getMessage());
            throw new SQLException("Error loading security provider.", e);
        }
    }

    /*
     * Add a new connection to pool
     */
    private void addNewConnection() throws SQLException {
        Connection connection = makeNewConnection();
        synchronized (dummySyncObj) {
            allConnections.add(connection);
            availableCnx.add(connection);
            idleCnxMap.put(connection, new Long(System.currentTimeMillis()));
            connectionCount++;
        }
    }

    public static String getDriverClass(String jdbcDriver) {
        String drvClass = (String) jdbcDriverClasses.get(jdbcDriver);
        if (drvClass == null)
            return jdbcDriver;
        return drvClass;
    }

    //periodically test the health of connections and if bad, try to reconnect
    class RefreshConnectionsThread extends java.util.TimerTask {
        int retryCount = 0;

        //TODO: Compare with 3.x
        public void run() {
        	boolean valid = true;
        	if (enableCheckAllConnections == true) {
                valid = validateConnections();
            } else {
                valid = validateConnectivity(null);
            }
            if (!valid) {
                if (connRetryCount > 0) {
                    if (retryCount == connRetryCount) {
                        cancel();   
                    }
                }
                retryCount++;
                synchronized (JDBCConnectionPool.this) {
                    setStatus(DISCONNECTED);
                    synchronized (dummySyncObj) {
                        closeAllConnections();
                    }
                    boolean success = makeInitialConnections();
                }
            } else {
                retryCount = 0;
            }
        }
    }
    
    // periodically closes unused connections
    class PropertyCheckIntervalThread extends java.util.TimerTask {

        public void run() {
        	try {
				long now = System.currentTimeMillis();
				int cnt = 0;
				synchronized (JDBCConnectionPool.this) {
					synchronized (dummySyncObj) {
						Iterator i = idleCnxMap.entrySet().iterator();
						Set cnxToClose = new HashSet();
						while (i.hasNext()) {
							Map.Entry e = (Map.Entry) i.next();
							Connection c = (Connection) e.getKey();
							Long ts = (Long) e.getValue();
							if (now - ts.longValue() >= inactivityTimeout) {
								if (cnt < idleCnxMap.size() - minConnections) {
									cnxToClose.add(c);
									cnt++;
								} else {
									break;
								}
							}
						}
						closeConnections(cnxToClose.iterator());
					}
				}
			} catch (Throwable t) {
        		
        	}
        }
    }
    
    protected void registerMBean(String key) {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            String resourceURI = key.substring(key.indexOf(":")+1);
            ObjectName name = new ObjectName("com.tibco.be:type=SharedResource,service=DBConceptConnectionPool,name=" + resourceURI);
            if(!mbs.isRegistered(name)) {
            	mbs.registerMBean(this, name);
            }
        } catch (Exception e) {
            logger.log(Level.WARN, e, e.getMessage());
        }
    }

	@Override
	public int getCacheSize() {
        return maxConnections;
	}

	//@Override
	public String getFailoverInterval() {
        return "0 Seconds";
	}

	@Override
	public boolean getIsUsingPrimary() {
        return true;
	}

	@Override
	public int getNumberOfAvailableConnections() {
		return availableCnx.size();
	}

	@Override
	public int getNumberOfConnectionsInUse() {
		return inUseCnx.size();
	}

	@Override
	public String getPoolState() {
        if (testConnection == null) {
            return "Not Initialized";
        } else if (validateConnectivity(null)) {
            return "Available (Using " + (getIsUsingPrimary() ? "Primary" : "Secondary") + ")";
        } else {
            return "Not Available";
        }
	}

	@Override
	public String getPrimaryURI() {
		return jdbcUrl;
	}

	//@Override
	public String getSecondaryURI() {
		return null;
	}

	//@Override
	public boolean isAutoFailover() {
        return false;
	}

	@Override
	public void recycle() throws SQLException {
        logger.log(Level.INFO, "DBConnectionPool recycling connections");
        synchronized (JDBCConnectionPool.this) {
            setStatus(DISCONNECTED);
            synchronized (dummySyncObj) {
                closeAllConnections();
            }
            makeInitialConnections();
        }		
	}

	@Override
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
        
        if (!valid) {
            synchronized (JDBCConnectionPool.this) {
                setStatus(DISCONNECTED);
                synchronized (dummySyncObj) {
                    closeAllConnections();
                }
                makeInitialConnections();
            }
        }
	}

	//@Override
	public void switchToPrimary() throws SQLException, Exception {
        logger.log(Level.WARN, "DBConnectionPool doesn't support switchToPrimary()");
    }

	//@Override
	public void switchToSecondary() throws SQLException, Exception {
        logger.log(Level.WARN, "DBConnectionPool doesn't support switchToSecondary()");
	}
 }