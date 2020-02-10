package com.tibco.cep.bemm.common;

public interface ConnectionContext<P> {

	String getConnectionType();
	/**
	 * @return Connection object
	 */
	P getConnection();

	/**
	 * Close connection
	 */
	void closeConnection();
	
}
