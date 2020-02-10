package com.tibco.be.jdbcstore.ssl;

import java.util.Properties;

public class PostgresSSLConnectionInfo extends JdbcSSLConnectionInfo {

	protected PostgresSSLConnectionInfo(String username, String password) {
		super(username, password);
		infoProperties.put("ssl", "true");//additional properties like sslmode have to be passed in url
	}

	public void setTrustStoreProps(String trustStore, String trustStoreType, String trustStorePassword) {
		Properties systemProperties = System.getProperties();
		systemProperties.setProperty("javax.net.ssl.trustStore", trustStore);
		if (trustStorePassword != null) {
			systemProperties.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
		}
	}
	
	/*
	 * Do nothing for postgres
	 */
	public void setKeyStoreProps(String keyStore, String keyStoreType, String keyStorePassword) {
		
	}

}
