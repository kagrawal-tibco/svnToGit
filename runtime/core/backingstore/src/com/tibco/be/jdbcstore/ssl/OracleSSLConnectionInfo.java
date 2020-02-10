package com.tibco.be.jdbcstore.ssl;

import java.security.Provider;
import java.security.Security;
import java.util.Properties;

/**
 * 
 * @author moshaikh
 */
public class OracleSSLConnectionInfo extends JdbcSSLConnectionInfo {

	private boolean requiresOraclePKIProvider;
	
	OracleSSLConnectionInfo(String user, String password) {
		super(user, password);
	}
	
	@Override
	public void setTrustStoreProps(String trustStore, String trustStoreType, String trustStorePassword) {
		super.setTrustStoreProps(trustStore, trustStoreType, trustStorePassword);
		if ("PKCS12".equalsIgnoreCase(trustStoreType) || "SSO".equalsIgnoreCase(trustStoreType)) {
			requiresOraclePKIProvider = true;
		}
	}
	
	@Override
	public void setKeyStoreProps(String keyStore, String keyStoreType, String keyStorePassword) {
		super.setKeyStoreProps(keyStore, keyStoreType, keyStorePassword);
		if ("PKCS12".equalsIgnoreCase(keyStoreType) || "SSO".equalsIgnoreCase(keyStoreType)) {
			requiresOraclePKIProvider = true;
		}
	}
	
	@Override
	public void setVerifyHostname(String hostname) {
		//TODO
	}
	
	@Override
	public void loadSecurityProvider() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		//Check if oracle thin AND Oracle PKI provider not yet installed.
		if (requiresOraclePKIProvider
				&& databaseUrl != null && databaseUrl.startsWith("jdbc:oracle:thin")
				&& Security.getProvider("OraclePKI") == null) {
			String providerProp= System.getProperty("oracle.ssl.security.provider", "oracle.security.pki.OraclePKIProvider");
			String providerOrder= System.getProperty("oracle.ssl.security.provider.order", "3");
			Security.insertProviderAt((Provider) Class.forName(providerProp).newInstance(), Integer.parseInt(providerOrder));
		}
	}
	
	@Override
	public boolean initializeConfigs(Properties initializationValues) {
		boolean initialized = super.initializeConfigs(initializationValues);
		setVerifyHostname((String)initializationValues.get("oracle.net.ssl_server_dn_match"));
		return initialized;
	}
}
