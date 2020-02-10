package com.tibco.cep.modules.db.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import oracle.jdbc.pool.OracleConnectionCacheManager;
import oracle.jdbc.pool.OracleDataSource;

/**
 * 
 * @author bgokhale
 * Based on OracleConnectionPool without all the backing store dependencies.
 * 
 */
public class ORAConnectionPool {

	/* Connection cache manager */
	private OracleConnectionCacheManager connMgr = null;

	/* Data Source Variable */
	private OracleDataSource ods = null;

	/* Key that identifies the cache */
	private String key = null;

	/* Properties based configuration of data source */
	private Properties config;


	public ORAConnectionPool(String key, OracleDataSource ds, Properties config)
			throws Exception {
		this.ods = ds;
		this.key = key;
		this.config = config;
	}

	public String getName() {
		return key;
	}
	
	public synchronized void activate() throws Exception {
		if (ods != null) {
			try {
				// enable connection cacheing
				ods.setConnectionCachingEnabled(true);

				/* Set the cache name */
				ods.setConnectionCacheName(key);
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

	public synchronized Connection getConnection() throws SQLException {
		java.sql.Connection conn = ods.getConnection();
		if (conn != null) {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
		}
		return conn;
	}

	public synchronized void refresh() throws SQLException {
		//Shiv 28Sep09: Replaced remove/create by purge, since remove has bugs according to Oracle
        /*try {
            connMgr.removeCache(key, 1);
        } catch (Exception e) {
            // Ignore.....
        }
        connMgr.createCache(key, ods, config);*/
        boolean cleanupCheckedOutConnections = false;
        connMgr.purgeCache(key, cleanupCheckedOutConnections);
	}

	public synchronized void close() {
		try {
			ods.close();
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
		}
	}

	/**
	 * This method returns active size of the Cache.
	 *
	 * @return int - Number of active conncetions
	 * @throws Exception - Any exception while getting the active size
	 */
	public int getActiveSize() throws Exception {
		try {
			return connMgr.getNumberOfActiveConnections(key);
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			throw new Exception("SQL Error while getting the no of active "
					+ " connections");
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
			return connMgr.getNumberOfActiveConnections(key)
					+ connMgr.getNumberOfAvailableConnections(key);
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	public int getNumberOfActiveConnections() {
		try {
			return connMgr.getNumberOfActiveConnections(key);
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	public int getNumberOfAvailableConnections() {
		try {
			return connMgr.getNumberOfAvailableConnections(key);
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}
	
	public boolean isFatal(Exception e) {
		if (e instanceof SQLException) {
			return connMgr.isFatalConnectionError((SQLException) e);
		} else {
			return false;
		}
	}
}
