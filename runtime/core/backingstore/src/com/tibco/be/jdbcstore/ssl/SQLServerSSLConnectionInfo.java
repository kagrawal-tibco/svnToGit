package com.tibco.be.jdbcstore.ssl;

import java.util.Properties;

/**
 * 
 * @author moshaikh
 */
public class SQLServerSSLConnectionInfo extends JdbcSSLConnectionInfo {
	
	SQLServerSSLConnectionInfo(String user, String password) {
		super(user, password);
//		infoProperties.setProperty("encrypt", "true");
//		infoProperties.setProperty("trustServerCertificate", "false");//Don't trust them verify them!
	}

	@Override
	public void setTrustStoreProps(String trustStore, String trustStoreType, String trustStorePassword) {
		infoProperties.setProperty("trustStore", trustStore);
		if (trustStoreType != null) {
			infoProperties.setProperty("trustStoreType", trustStoreType);
		}
		if (trustStorePassword != null) {
			infoProperties.setProperty("trustStorePassword", trustStorePassword);
		}
	}
	
	@Override
	public void setKeyStoreProps(String keyStore, String keyStoreType, String keyStorePassword) {
		//TODO
	}
	
	@Override
	public void setVerifyHostname(String hostname) {
		if (hostname != null) {
			infoProperties.setProperty("hostNameInCertificate", hostname);
		}
	}
	
	@Override
	public boolean initializeConfigs(Properties initializationValues) {
		boolean initialized = false;
		if (initializationValues.get("trustStore") != null) {
			setTrustStoreProps(
					(String)initializationValues.get("trustStore"),
					(String)initializationValues.get("trustStoreType"),
					(String)initializationValues.get("trustStorePassword"));
			initialized = true;
		}
		//TODO: keystore
		setVerifyHostname((String)initializationValues.get("hostNameInCertificate"));
		return initialized;
	}
}
