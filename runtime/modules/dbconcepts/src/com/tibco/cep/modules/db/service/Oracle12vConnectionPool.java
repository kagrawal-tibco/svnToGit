/**
 * 
 */
package com.tibco.cep.modules.db.service;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.ucp.admin.UniversalConnectionPoolManager;
import oracle.ucp.admin.UniversalConnectionPoolManagerImpl;
import oracle.ucp.jdbc.PoolDataSourceImpl;

/**
 * Oracle 12c onwards complaint connection pool
 * 
 * @author vpatil
 */
public class Oracle12vConnectionPool implements OracleConnectionPool {

	/* Key that identifies the cache */
	private String key = null;
	
	/* Data Source connection info */
	private OracleConnectionInfo oracleConnectionInfo;
	
	/* Pool Data Source */
	private PoolDataSourceImpl pds;
	
	/* Universal Connection Pool Manager */
	private UniversalConnectionPoolManager ucpMgr;
	
	public Oracle12vConnectionPool(String key, OracleConnectionInfo oracleConnectionInfo)
			throws Exception {
		this.key = key;
		this.oracleConnectionInfo = oracleConnectionInfo;
		
		init();
	}
	
	private void init() throws Exception {
		PoolDataSourceImpl pds = new PoolDataSourceImpl();
		pds.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
		pds.setConnectionPoolName(key);
		pds.setURL(this.oracleConnectionInfo.getJdbcUrl());
		pds.setUser(this.oracleConnectionInfo.getUsername());
		pds.setPassword(this.oracleConnectionInfo.getPassword());
		if (oracleConnectionInfo.getSslConnectionInfo() != null) {
			pds.setConnectionProperties(oracleConnectionInfo.getSslConnectionInfo().getProperties());
        	oracleConnectionInfo.getSslConnectionInfo().loadSecurityProvider();
        }
		
		this.pds = pds;
	}

	@Override
	public synchronized void activate() throws Exception {
		setConnectionConfig();
		
		this.ucpMgr = UniversalConnectionPoolManagerImpl.getUniversalConnectionPoolManager();
		ucpMgr.createConnectionPool(this.pds);
		ucpMgr.startConnectionPool(this.key);
	}

	@Override
	public synchronized Connection getConnection() throws SQLException {
		Connection conn = this.pds.getConnection();
		if (conn != null) {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
		}
		return conn;
	}

	@Override
	public synchronized void refresh() throws SQLException {
		try {
			ucpMgr.recycleConnectionPool(this.key);
		} catch(Exception exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public synchronized void close() {
		try {
			this.ucpMgr.stopConnectionPool(this.key);
			this.ucpMgr.destroyConnectionPool(this.key);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public String getName() {
		return key;
	}

	@Override
	public int getCacheSize() {
		return (getNumberOfActiveConnections() + getNumberOfAvailableConnections());
	}

	@Override
	public int getNumberOfActiveConnections() {
		return pds.getBorrowedConnectionsCount();
	}

	@Override
	public int getNumberOfAvailableConnections() {
		return pds.getAvailableConnectionsCount();
	}
	
	private void setConnectionConfig() throws Exception {
		this.pds.setInitialPoolSize(this.oracleConnectionInfo.getInitialConnections());
		this.pds.setMaxPoolSize(this.oracleConnectionInfo.getMaxConnections());
		this.pds.setMinPoolSize(this.oracleConnectionInfo.getMinConnections());
		this.pds.setInactiveConnectionTimeout(this.oracleConnectionInfo.getInactivityTimeout());
		this.pds.setTimeToLiveConnectionTimeout(this.oracleConnectionInfo.getTimeToLiveTimeout());
		this.pds.setAbandonedConnectionTimeout(this.oracleConnectionInfo.getAbandonedConnectionTimeout());
		this.pds.setPropertyCycle(this.oracleConnectionInfo.getPropertyCheckInterval());
		this.pds.setConnectionWaitTimeout(this.oracleConnectionInfo.getConnectionWaitTimeout());
		this.pds.setValidateConnectionOnBorrow(this.oracleConnectionInfo.isValidateConnection());
	}
}
