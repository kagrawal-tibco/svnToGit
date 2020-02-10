package com.tibco.cep.bemm.common.pool;

/**
 * The implementing class should ideally hold all the data required to create a new connection. 
 * @author moshaikh
 */
public interface PooledConnectionConfig {
	
	/**
	 * @return A String which identifies a connection, such that it is unique for any particular source.
	 */
	public String getConnectionIdentifierString();
	
}
