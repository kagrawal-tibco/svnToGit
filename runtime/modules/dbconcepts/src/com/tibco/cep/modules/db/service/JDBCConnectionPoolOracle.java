package com.tibco.cep.modules.db.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;

import com.tibco.be.jdbcstore.ssl.JdbcSSLConnectionInfo;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author bgokhale
 * This class is a parallel implementation of JDBCConnectionPool specifically for Oracle.
 * It is instantiated by DBConnectionFactory, if the driver is Oracle.
 * 
 * It is required because the framework uses the generic JDBCConnectionPool, which, when Oracle is the database, is incompatible with the
 * backing store (oracle) pool. So, if the dbconcepts is a Oracle schema, use a pool implementation that closely
 * follows the backing store schema.
 * 
 * Since JDBCConnectionPool is not an interface (as it  should have been) for now, just extend it and override all its public methods
 *  
 */
public class JDBCConnectionPoolOracle extends JDBCConnectionPool {

    private OracleConnectionPool oracleConnectionPool;

    public JDBCConnectionPoolOracle(String resourceURI, String driver, String url, String username,
            String password, int initialConnections, int maxConnections,
            int timeout, JdbcSSLConnectionInfo sslConnectionInfo) throws Exception {

        this.rsProvider = DBConnectionFactory.getInstance().getModuleManager().getRuleServiceProvider();
        this.logger = rsProvider.getLogger(JDBCConnectionPoolOracle.class);

        this.jdbcUrl = url;
        this.poolName = "be.dbconcepts:"+resourceURI;
        this.username = username;
        this.password = password;
        this.maxConnections = maxConnections;
        this.initialConnections = initialConnections;
        this.sslConnectionInfo = sslConnectionInfo;

        try {
            String checkConnStr = rsProvider.getProperties().getProperty("be.dbconcepts.connection.check.interval", "60").trim();
            this.checkConnectionsInterval = Integer.parseInt(checkConnStr);
            this.checkConnectionsInterval *= 1000;
        } catch (Exception e) {
        }
       
        try {
            String retryStr = rsProvider.getProperties().getProperty("be.dbconcepts.connection.retry.count", "-1").trim();
            connRetryCount = Integer.parseInt(retryStr);
        } catch (Exception e) {
        }
        
        registerMBean(this.poolName);
    }

    public void init() {
    	try {
    		this.logger.log(Level.INFO, "Registering DataSource, rsp=%s, key=%s, uri=%s, user=%s",
                this.rsProvider.getName(), this.poolName, this.jdbcUrl, this.username);
 		
    		OracleConnectionInfo oracleConnectionInfo = new OracleConnectionInfo(jdbcUrl, username, password, initialConnections, maxConnections, sslConnectionInfo, rsProvider.getProperties());
    		this.oracleConnectionPool = OracleConnectionPoolFactory.getConnectionPool(poolName, oracleConnectionInfo);
    		this.oracleConnectionPool.activate();
    		
    		setStatus(CONNECTED);
    		
            logger.log(Level.INFO, "[%s] initialized successfully", this);
    	} catch(Exception e) {
    		logger.log(Level.ERROR, e, "Failed to initialize pool [%s] during startup", this);
    	} finally {
    		if (connRetryCount != 0) {
    			reconnectTimer = new Timer("DBConcept Connection Check Timer", true);
    			reconnectTimer.schedule(new RefreshConnectionsThread(rsProvider.getLogger(RefreshConnectionsThread.class)), checkConnectionsInterval, checkConnectionsInterval);
    		}
    	}
    }

    public synchronized Connection getConnection() throws SQLException {
        if (getStatus() == DISCONNECTED) {
            this.logger.log(Level.ERROR, "DBConcept pool is already in disconnected state: %s", this.poolName);
            return null;
        }

        Connection c = null;
        try {
            c = oracleConnectionPool.getConnection();
        } catch (Exception e) {
            setStatus(DISCONNECTED);
            this.logger.log(Level.ERROR, e, "DBConcept pool has disconnected: %s, error encountered: %s", this.poolName, e.getMessage());
        }
        return c;
    }

    public void free(Connection connection) {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void close() {
        this.logger.log(Level.DEBUG, "Closing DBConcept pool: %s", this.poolName);
        oracleConnectionPool.close();
        reconnectTimer.cancel();
    }

    public synchronized String toString() {
        return "JDBCConnectionPoolOracle(" + jdbcUrl + "," + username + ")";
    }

    /**
     * 
     * @param jdbcDriver Driver name
     * @return driver class corresponding to the driver name
     * @caution Not really used
     */
    public static String getDriverClass(String jdbcDriver) {
        return "oracle.jdbc.OracleDriver";
    }
    
    //periodically test the health of connections and if bad, try to reconnect
    class RefreshConnectionsThread extends java.util.TimerTask {
    	Logger logger;
        int retryCount = 0;
        
        public RefreshConnectionsThread(Logger logger) {
			this.logger = logger;
		}
    
        public void run() {
            Connection conn = null;
            try {
                conn = checkConnection();
                //if it reaches here, its success, else an exception would be thrown
                setStatus(CONNECTED);
                retryCount = 0;
            } catch (Exception e) {
            	this.logger.log(Level.ERROR, e, "Connection check failed, error encountered: %s", e.getMessage());
                if (connRetryCount > 0) {
                    if (retryCount == connRetryCount) {
                        cancel();   
                    }
                }
                retryCount++;
                // else, try to refresh the connections
                try {
                    if (status == CONNECTED) {
                        logger.log(Level.DEBUG, "DBConcept database disconnect detected: %s", poolName);
                    }
                    
                    setStatus(DISCONNECTED);
                    
                    int retryCnt = 0;
                    for (retryCnt=0; retryCnt<3; retryCnt++) {// try connection pool refresh -> this removes and creates 
                        try {
                            oracleConnectionPool.refresh();
                            if (logger.isEnabledFor(Level.DEBUG)) {
                            	 logger.log(Level.DEBUG, "OracleConnectionPool.refresh() is successful.");
                            }
                            break;
                        } catch (Exception ex) {
                        	this.logger.log(Level.ERROR, ex, "Connection refresh failed, error encountered: %s", ex.getMessage());
                            try {
                                Thread.sleep(1000);
                            } catch (Exception excp) {
                                
                            }
                        }
                    }

                    // check connection again
                    conn = checkConnection();

                    // ok so far, so set status to connected
                    setStatus(CONNECTED);
                    retryCount = 0;
                    logger.log(Level.DEBUG, "DBConcept database connection re-established: %s", poolName);

                } catch (Exception ex) {
                    // if can't refresh or still can't get connection, stay
                    // disconnected. status is already set above
                	this.logger.log(Level.ERROR, e, "Connection refresh failed, error encountered: %s", e.getMessage());
                }
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (Exception e) {
                }
            }
        }
    }
    
    private Connection checkConnection() throws SQLException {
    	Connection conn = oracleConnectionPool.getConnection();
        if (conn != null) {
            // Check if the connection is useful
            Statement stmt = conn.createStatement();
            stmt.executeQuery("select 1 from dual");
            stmt.close();
        } else {
        }
        return conn;
    }
    
	@Override
	public int getCacheSize() {
		return oracleConnectionPool.getCacheSize();
	}

	@Override
	public String getFailoverInterval() {
        return "0 Seconds";
	}

	@Override
	public boolean getIsUsingPrimary() {
        return true;
	}
	
	@Override
	public String getPoolState() {
		String poolStatus = "Not Available";
		if (getStatus() == CONNECTED) {
			Connection conn = null;
			try {
				conn = checkConnection();
				if (conn != null) {
					poolStatus = "Available (Using " + (getIsUsingPrimary() ? "Primary" : "Secondary") + ")";
				}
			} catch (SQLException sqlException) {
				logger.log(Level.ERROR, "Error while checking connection status");
			} finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						logger.log(Level.ERROR, "Error while closing connection");
					}
				}
			}
		}
		return poolStatus;
	}

	@Override
	public int getNumberOfAvailableConnections() {
		return oracleConnectionPool.getNumberOfAvailableConnections();
	}

	@Override
	public int getNumberOfConnectionsInUse() {
		return oracleConnectionPool.getNumberOfActiveConnections();
	}

	@Override
	public void recycle() throws SQLException {
        logger.log(Level.INFO, "DBConnectionPoolOracle recycling connections");
        oracleConnectionPool.refresh();
	}

	@Override
	public void refreshConnections() {
        logger.log(Level.INFO, "DBConnectionPoolOracle refreshing connections");
        try {
			oracleConnectionPool.refresh();
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Error while refreshing DBConnectionPoolOracle");
		}
	}
}
