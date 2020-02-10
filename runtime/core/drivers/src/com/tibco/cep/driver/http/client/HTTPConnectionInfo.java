/**
 * 
 */
package com.tibco.cep.driver.http.client;

/**
 * HTTP Connection Info to configure various connection attributes/parameters before actually making the HTTP call
 * 
 * @author vpatil
 */
public class HTTPConnectionInfo {
	
	private boolean isSecure;
	private boolean expectContinueHeaderDisabled;
	
	private String proxyHost;
	private int proxyPort;
	
	private String sslProtocol;
	private Object clientIdKeystore;
	private String clientIdPassword;
	private Object trustedCertsKeystore;
	private String trustedCertsPassword;
	private boolean verifyHostName;
	private boolean cookiesDisabled;
	private String httpMethod;
	
	public HTTPConnectionInfo(boolean isSecure) {
		this.isSecure = isSecure;
		// defaults
		this.expectContinueHeaderDisabled = false;
		this.cookiesDisabled = false;
	}
	
	public boolean isSecure() {
		return isSecure;
	}
	
	public void setSecureInfo(String sslProtocol, Object clientIdKeyStore, String clientIdPassword, Object trustedCertsKeystore, String trustedCertsPassword, boolean verifyHostName) {
		if (isSecure) {
			this.sslProtocol = sslProtocol;
			this.clientIdKeystore = clientIdKeyStore;
			this.clientIdPassword = clientIdPassword;
			this.trustedCertsKeystore = trustedCertsKeystore;
			this.trustedCertsPassword = trustedCertsPassword;
			this.verifyHostName = verifyHostName;
		} else {
			throw new RuntimeException("Not a secure ConnectionInfo Object.");
		}
	}
	
	public boolean isExpectContinueHeaderDisabled() {
		return expectContinueHeaderDisabled;
	}

	public void setExpectContinueHeaderDisabled(boolean expectContinueHeaderDisabled) {
		this.expectContinueHeaderDisabled = expectContinueHeaderDisabled;
	}

	public String getProxyHost() {
		return proxyHost;
	}
	
	public void setProxy(String proxyHost, int proxyPort) {
		this.proxyHost = proxyHost;
		this.proxyPort = proxyPort;
	}
	
	public int getProxyPort() {
		return proxyPort;
	}
	
	public String getSSLProtocol() {
		return sslProtocol;
	}
	
	public void setSSLProtocol(String sslProtocol) {
		this.sslProtocol = sslProtocol;
	}

	public Object getClientIdKeystore() {
		return clientIdKeystore;
	}

	public String getClientIdPassword() {
		return clientIdPassword;
	}

	public Object getTrustedCertsKeystore() {
		return trustedCertsKeystore;
	}

	public String getTrustedCertsPassword() {
		return trustedCertsPassword;
	}

	public boolean isVerifyHostName() {
		return verifyHostName;
	}

	public boolean isCookiesDisabled() {
		return cookiesDisabled;
	}

	public void setCookiesDisabled(boolean cookiesDisabled) {
		this.cookiesDisabled = cookiesDisabled;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}
	
}
