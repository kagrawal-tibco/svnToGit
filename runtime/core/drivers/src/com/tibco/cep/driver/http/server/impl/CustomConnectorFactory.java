/**
 * 
 */
package com.tibco.cep.driver.http.server.impl;

import java.net.InetAddress;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11AprProtocol;
import org.apache.coyote.http11.Http11NioProtocol;

/**
 * Factory to create Http Connector objects. Conditional based on Channel
 * configuration NIO/BIO type connectors will be created
 * Additional support for APR type of channel as well
 * 
 * @author vpatil
 */
public class CustomConnectorFactory {
	// connector type
    private static final String BIO_CONNECTOR_PROTOCOL = "BIO";
    private static final String NIO_CONNECTOR_PROTOCOL = "NIO";
    public static final String APR_CONNECTOR_PROTOCOL = "APR";
	
    /**
     * 
     * @param connectorType
     * @param inetAddress
     * @param port
     * @param isSecure
     * @param maxExecutorThreads
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <C extends Connector> C createConnector(String connectorType,
    		InetAddress inetAddress,
    		int port,
    		boolean isSecure,
    		int maxExecutorThreads) throws Exception {
		Class connectorProtocol = getConnectorProtocol(connectorType);
    	Connector httpConnector = new CustomHttpConnector(connectorProtocol, inetAddress, port, isSecure, maxExecutorThreads);
    	
    	return (C) httpConnector;
    }

    /**
     * 
     * @param connectorType
     * @return
     * @throws Exception 
     */
	@SuppressWarnings("rawtypes")
	private static Class getConnectorProtocol(final String connectorType) throws Exception {
		if (connectorType != null &&  !connectorType.isEmpty())
		{
			String connTypeStr = connectorType.trim();
			if (connTypeStr.equals(BIO_CONNECTOR_PROTOCOL)) 
					throw new Exception("Invalid Connector Protocol");
			
			return connTypeStr.equals(NIO_CONNECTOR_PROTOCOL)? Http11NioProtocol.class : Http11AprProtocol.class;
		}
		else {
			throw new Exception("Invalid Connector Protocol");
		}
	}
}
