package com.tibco.cep.bemm.common.pool;

/**
 * An interface for implementations the intend to provide a connection pool functionality.
 * @author moshaikh
 *
 * @param <C> The user's connection type.
 */
public interface ConnectionPool<C> {

	/**
	 * Get a connection from the pool for the specified connection config.
	 * @return
	 * @throws Exception
	 */
	PooledConnection<C> getConnection(PooledConnectionConfig connectionConfig) throws Exception;
	
	/**
	 * Return the connection to the pool. The implementation should ensure that a connection that is marked unusable should be discarded.
	 * @param connection
	 * @throws Exception
	 */
	void returnConnection(PooledConnection<C> connection) throws Exception;
	
	/**
	 * Close all the connections in the pool.
	 */
	void close();
}
