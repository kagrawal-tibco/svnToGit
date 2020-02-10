package com.tibco.be.jdbcstore.ssl;

import java.util.Properties;

import com.tibco.be.jdbcstore.RDBMSType;

/**
 * A container for all necessary configurations for a JDBC secure connection.
 * 
 * @author moshaikh
 */
public class JdbcSSLConnectionInfo {
	
	public static final String KEY_JDBC_SSL_CONNECTION_INFO = "jdbc.ssl.connection.info";
	
	protected String databaseUrl;
	protected String jdbcDriver;
	protected Properties infoProperties;
	
	protected JdbcSSLConnectionInfo(String username, String password) {
		this.infoProperties = new Properties();
		if (username != null) {
			this.infoProperties.put("user", username);
        }
        if (password != null) {
        	this.infoProperties.put("password", password);
        }
	}
	//can not use constants from RDBMSTYPE here because of build dependency
	public static JdbcSSLConnectionInfo createConnectionInfo(String username, String password, String databaseUrl, String jdbcdriver) {
		JdbcSSLConnectionInfo connectionInfo;
		if (jdbcdriver.toLowerCase().contains(RDBMSType.RDBMS_TYPE_NAME_SQLSERVER.toLowerCase())) {
			connectionInfo = new SQLServerSSLConnectionInfo(username, password);
		}
		else if (jdbcdriver.toLowerCase().contains(RDBMSType.RDBMS_TYPE_NAME_ORACLE.toLowerCase())) {
			connectionInfo = new OracleSSLConnectionInfo(username, password);
		}
		else if (jdbcdriver.toLowerCase().contains(RDBMSType.RDBMS_TYPE_NAME_MYSQL.toLowerCase())) {
			connectionInfo = new MySQLSSLConnectionInfo(username, password);
		}
		else if (jdbcdriver.toLowerCase().contains(RDBMSType.RDBMS_TYPE_NAME_POSTGRES.toLowerCase())) {
			connectionInfo = new PostgresSSLConnectionInfo(username, password);
		}
		else {
			connectionInfo = new JdbcSSLConnectionInfo(username, password);
		}
		connectionInfo.databaseUrl = databaseUrl;
		connectionInfo.jdbcDriver = jdbcdriver;
		return connectionInfo;
	}
	
	public void setTrustStoreProps(String trustStore, String trustStoreType, String trustStorePassword) {
		infoProperties.setProperty("javax.net.ssl.trustStore", trustStore);
		if (trustStoreType != null) {
			infoProperties.setProperty("javax.net.ssl.trustStoreType", trustStoreType);
		}
		if (trustStorePassword != null) {
			infoProperties.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
		}
	}
	
	public void setKeyStoreProps(String keyStore, String keyStoreType, String keyStorePassword) {
		infoProperties.setProperty("javax.net.ssl.keyStore", keyStore);
		if (keyStoreType != null) {
			infoProperties.setProperty("javax.net.ssl.keyStoreType", keyStoreType);
		}
		if (keyStorePassword != null) {
			infoProperties.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);
		}
	}
	
	public void setVerifyHostname(String hostname) {
		//Nothing to do by default
	}
	
	public String getUser() {
		return this.infoProperties.getProperty("user");
	}
	public void setUser(String user) {
		if (user != null) {
			this.infoProperties.put("user", user);
        }
	}
	
	public String getPassword() {
		return this.infoProperties.getProperty("password");
	}
	public void setPassword(String password) {
        if (password != null) {
        	this.infoProperties.put("password", password);
        }
	}
	
	public Properties getProperties() {
		return this.infoProperties;
	}
	
	public Object put(Object key, Object value) {
		return this.infoProperties.put(key, value);
	}
	
	public String getDatabaseUrl() {
		return this.databaseUrl;
	}
	
	public String getJdbcDriver() {
		return this.jdbcDriver;
	}
	
	/**
     * Installs any needed security provider for <code>this</code> SSL connection info at the required position if not already installed.
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
	public void loadSecurityProvider() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		//Nothing to load by default
	}
	
	/**
	 * Reads ssl configuration values from passed properties.
	 * @param initializationValues
	 * @return
	 */
	public boolean initializeConfigs(Properties initializationValues) {
		boolean initialized = false;
		if (initializationValues.get("javax.net.ssl.trustStore") != null) {
			setTrustStoreProps(
					(String)initializationValues.get("javax.net.ssl.trustStore"),
					(String)initializationValues.get("javax.net.ssl.trustStoreType"),
					(String)initializationValues.get("javax.net.ssl.trustStorePassword"));
			initialized = true;
		}
		if (initializationValues.get("javax.net.ssl.keyStore") != null) {
			setKeyStoreProps(
					(String)initializationValues.get("javax.net.ssl.keyStore"),
					(String)initializationValues.get("javax.net.ssl.keyStoreType"),
					(String)initializationValues.get("javax.net.ssl.keyStorePassword"));
			initialized = true;
		}
		return initialized;
	}
}
