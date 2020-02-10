package com.tibco.cep.bemm.common.jmx;

import java.io.IOException;

import javax.management.MBeanServerConnection;

/**
 * Interface to manage Jmx connections.
 * 
 * @author bala
 *
 */

public interface JmxConnectionPool {

	MBeanServerConnection getConnection(String uri, String[] credentials, int timeout) throws IOException;
	
	void returnConnection (MBeanServerConnection conn) throws Exception;
	
	void returnConnection (MBeanServerConnection conn, boolean isBad) throws Exception;
	
	void closeConnection (String uri);
	
	void close();
	
}
