/**
 * 
 */
package com.tibco.cep.modules.db.service;

import java.util.Properties;

import com.tibco.be.jdbcstore.ssl.JdbcSSLConnectionInfo;

/**
 * @author vpatil
 *
 */
public class OracleConnectionInfo {
	
	private String jdbcUrl;
	private String username;
	private String password;
	private int initialConnections;
	private int maxConnections;
	private int minConnections;
	private int readTimeout;
	private int inactivityTimeout;
	private int timeToLiveTimeout;
	private int abandonedConnectionTimeout;
	private int propertyCheckInterval;
	private int connectionWaitTimeout;
	private boolean validateConnection;
	
	private JdbcSSLConnectionInfo sslConnectionInfo;
	
	private boolean oracle12Driver;
	
	public OracleConnectionInfo(String jdbcUrl, String username, String password, int defaultInitConnection,
			int defaultMaxConnections, JdbcSSLConnectionInfo sslConnectionInfo, Properties properties) {
		this.jdbcUrl = jdbcUrl;
		this.username = username;
		this.password = password;
		this.sslConnectionInfo = sslConnectionInfo;
		
		this.initialConnections = Integer.parseInt(properties.getProperty("be.dbconcepts.pool.initial", ("" + defaultInitConnection)).trim());
        this.maxConnections = Integer.parseInt(properties.getProperty("be.dbconcepts.pool.max", ("" + defaultMaxConnections)).trim());
        this.minConnections = Integer.parseInt(properties.getProperty("be.dbconcepts.pool.min", "0").trim());
        this.inactivityTimeout = Integer.parseInt(properties.getProperty("be.dbconcepts.pool.inactivityTimeout", "0").trim());
        this.timeToLiveTimeout = Integer.parseInt(properties.getProperty("be.dbconcepts.pool.TimeToLiveTimeout", "0").trim());  
        this.abandonedConnectionTimeout = Integer.parseInt(properties.getProperty("be.dbconcepts.pool.AbandonedConnectionTimeout", "0").trim());
        this.propertyCheckInterval = Integer.parseInt(properties.getProperty("be.dbconcepts.pool.PropertyCheckInterval", "900").trim());
        this.connectionWaitTimeout = Integer.parseInt(properties.getProperty("be.dbconcepts.pool.waitTimeout", "1").trim());
        this.validateConnection = Boolean.parseBoolean(properties.getProperty("be.dbconcepts.pool.ValidateConnection", "false").trim());
        
        this.readTimeout = Integer.parseInt(properties.getProperty("be.oracle.jdbc.readtimeout", "0").trim());
        this.oracle12Driver = Boolean.parseBoolean(properties.getProperty("be.dbconcepts.oracle.pool.v12", "false").trim());
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public int getInitialConnections() {
		return initialConnections;
	}

	public int getMaxConnections() {
		return maxConnections;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public int getMinConnections() {
		return minConnections;
	}

	public int getInactivityTimeout() {
		return inactivityTimeout;
	}

	public int getTimeToLiveTimeout() {
		return timeToLiveTimeout;
	}

	public int getAbandonedConnectionTimeout() {
		return abandonedConnectionTimeout;
	}

	public int getPropertyCheckInterval() {
		return propertyCheckInterval;
	}

	public int getConnectionWaitTimeout() {
		return connectionWaitTimeout;
	}

	public boolean isValidateConnection() {
		return validateConnection;
	}

	public boolean isOracle12Driver() {
		return oracle12Driver;
	}

	public JdbcSSLConnectionInfo getSslConnectionInfo() {
		return sslConnectionInfo;
	}
}
