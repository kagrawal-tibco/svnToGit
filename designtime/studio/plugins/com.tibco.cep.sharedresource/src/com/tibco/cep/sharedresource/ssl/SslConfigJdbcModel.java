package com.tibco.cep.sharedresource.ssl;

/**
 * Model class for SSL configurations of a JDBC shared resource.
 * 
 * @author moshaikh
 */
public class SslConfigJdbcModel extends SslConfigModel {

	public static final String ID_CLIENT_AUTH = "requiresClientAuthentication";
	public static final String ID_VERIFY_HOST_NAME = "verifyHostName";
	public static final String ID_EXPECTED_HOSTNAME = "expectedHostName";

	private String expectedHostName, verifyHostName, clientAuth;

	public SslConfigJdbcModel() {
		super();
		expectedHostName = "";
		verifyHostName = "true";
		clientAuth = "false";
	}

	/**
	 * Returns the specified expected hostname.
	 * @return
	 */
	public String getExpectedHostName() {
		return expectedHostName;
	}

	/**
	 * Updates the expected hostname into the model.
	 * @param expectedHostName
	 */
	public void setExpectedHostName(String expectedHostName) {
		this.expectedHostName = expectedHostName;
	}

	/**
	 * Returns whether need to verify hostname.
	 * @return
	 */
	public String getVerifyHostName() {
		return verifyHostName;
	}

	/**
	 * Returns whether need to verify hostname as a boolean value.
	 * @return
	 */
	public boolean isVerifyHostName() {
		return Boolean.parseBoolean(verifyHostName);
	}

	/**
	 * Sets whether need to verify hostname.
	 * @param verifyHostName
	 */
	public void setVerifyHostName(String verifyHostName) {
		this.verifyHostName = verifyHostName;
	}

	/**
	 * Returns whether client authentication is required.
	 * @return
	 */
	public String getClientAuth() {
		return clientAuth;
	}
	
	/**
	 * Returns whether client authentication is required as a boolean value.
	 * @return
	 */
	public boolean isClientAuth() {
		return Boolean.parseBoolean(clientAuth);
	}

	/**
	 * Sets whether client authentication is required.
	 * @param clientAuth
	 */
	public void setClientAuth(String clientAuth) {
		this.clientAuth = clientAuth;
	}
}
