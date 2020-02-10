/**
 * 
 */
package com.tibco.cep.modules.db.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import oracle.jdbc.OracleDriver;
import oracle.jdbc.pool.OracleConnectionCacheManager;
import oracle.jdbc.pool.OracleDataSource;

/**
 * @author vpatil
 *
 */
public class OraclePre12vConnectionPool implements OracleConnectionPool {

	/* Connection cache manager */
	private OracleConnectionCacheManager connMgr = null;

	/* Data Source Variable */
	private OracleDataSource ods = null;

	/* Key that identifies the cache */
	private String key = null;

	/* Data Source connection info */
	private OracleConnectionInfo oracleConnectionInfo;

	public OraclePre12vConnectionPool(String key, OracleConnectionInfo oracleConnectionInfo)
			throws Exception {
		this.key = key;
		this.oracleConnectionInfo = oracleConnectionInfo;
		
		initDataSource();
	}
	
	private void initDataSource() throws Exception {
		DriverManager.registerDriver(new OracleDriver());
		
		OracleDataSource ds = new OracleDataSource();
		ds.setURL(oracleConnectionInfo.getJdbcUrl());
		ds.setUser(oracleConnectionInfo.getUsername());
		ds.setPassword(oracleConnectionInfo.getPassword());
		ds.setDataSourceName(key);
		
		// TODO - check if this property too can go along with others
		Properties props = new Properties();
		props.put("oracle.jdbc.ReadTimeout", oracleConnectionInfo.getReadTimeout());
		if (oracleConnectionInfo.getSslConnectionInfo() != null) {
        	props.putAll(oracleConnectionInfo.getSslConnectionInfo().getProperties());
        	oracleConnectionInfo.getSslConnectionInfo().loadSecurityProvider();
        }
		ds.setConnectionProperties(props);
		
		this.ods = ds;
	}

	public String getName() {
		return key;
	}
	
	@Override
	public synchronized void activate() throws Exception {
		if (ods != null) {
			try {
				// enable connection cacheing
				ods.setConnectionCachingEnabled(true);

				/* Set the cache name */
				ods.setConnectionCacheName(key);
				
				Properties config = getConnectionConfig();
				ods.setConnectionCacheProperties(config);

				/* Initialize the Connection Cache */
				connMgr = OracleConnectionCacheManager
						.getConnectionCacheManagerInstance();
				connMgr.createCache(key, ods, config);
			} catch (java.sql.SQLException ex) { /* Catch SQL Errors */
				throw new Exception(
						"SQL Error while Instantiating Connection Cache : \n"
								+ ex.toString());
			} catch (java.lang.Exception ex) { /* Catch other generic errors */
				throw new Exception("Exception : \n" + ex.toString());
			}
		}
	}
	
	/**
	 * Load all the pool properties
	 * Naming similar to bs pool, for consistency
	 * @return
	 */
	private Properties getConnectionConfig() {
		Properties config = new Properties();

		config.setProperty("InitialLimit", ""+oracleConnectionInfo.getInitialConnections());
		config.setProperty("MaxLimit", ""+oracleConnectionInfo.getMaxConnections());
		config.setProperty("MinLimit", ""+oracleConnectionInfo.getMinConnections());
		config.setProperty("InactivityTimeout", ""+oracleConnectionInfo.getInactivityTimeout());
		config.setProperty("TimeToLiveTimeout", ""+oracleConnectionInfo.getTimeToLiveTimeout());
		config.setProperty("AbandonedConnectionTimeout", ""+oracleConnectionInfo.getAbandonedConnectionTimeout());
		config.setProperty("PropertyCheckInterval", ""+oracleConnectionInfo.getPropertyCheckInterval());
		config.setProperty("ConnectionWaitTimeout", ""+oracleConnectionInfo.getConnectionWaitTimeout());
		config.setProperty("ValidateConnection", ""+oracleConnectionInfo.isValidateConnection());

		return config;
	}

	@Override
	public synchronized Connection getConnection() throws SQLException {
		java.sql.Connection conn = ods.getConnection();
		if (conn != null) {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
		}
		return conn;
	}

	@Override
	public synchronized void refresh() throws SQLException {
		boolean cleanupCheckedOutConnections = false;
        connMgr.purgeCache(key, cleanupCheckedOutConnections);
	}

	@Override
	public synchronized void close() {
		try {
			ods.close();
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
		}
	}
	
	@Override
	public int getCacheSize() {
		try {
			return getNumberOfActiveConnections()
					+ getNumberOfAvailableConnections();
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public int getNumberOfActiveConnections() {
		try {
			return connMgr.getNumberOfActiveConnections(key);
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public int getNumberOfAvailableConnections() {
		try {
			return connMgr.getNumberOfAvailableConnections(key);
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}
}
