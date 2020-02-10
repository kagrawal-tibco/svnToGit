
package com.tibco.cep.driver.http.server.utils;

import java.io.IOException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;

import org.apache.http.conn.ssl.SSLSocketFactory;

import com.tibco.cep.driver.http.server.utils.SSLUtils.SSLProtocol;

/**
 * Custom SSL factory to override the protocol support when sockets are created 
 * during the HTTP Communication.
 * 
 * @author vpatil
 */
public class CustomSSLSocketFactory extends SSLSocketFactory {
	private String restrictedSSLProtocols; 

	public CustomSSLSocketFactory(SSLContext sslContext) {
		super(sslContext);
	}
	
	@Override
	protected void prepareSocket(SSLSocket sslSocket) throws IOException {
		super.prepareSocket(sslSocket);
		if (restrictedSSLProtocols != null) {
			sslSocket.setEnabledProtocols(new String[] {restrictedSSLProtocols});
		}
	}

	/**
	 * Add restricted protocol.
	 * Used for cases where the communication needs to be restricted to specific protocols, i.e. like SSLv3
	 * 
	 * @param sslProtocol
	 */
	public void setRestrictedSSLProtocol(String sslProtocol) {
		restrictedSSLProtocols = SSLProtocol.getProtocolByValue(sslProtocol);
	}
}
