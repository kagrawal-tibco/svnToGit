package com.tibco.cep.bemm.common.pool;

/**
 * A wrapper class for a implementation defined connection type.
 * @author moshaikh
 *
 * @param <C> The type of the connection object to wrap.
 */
public class PooledConnection<C> {
	
	private C underlyingConnection;
	private boolean markedUnusable;
	private final String connectionIdentifier;
	
	/**
	 * 
	 * @param underlyingConnection The actual connection
	 * @param connectionIdentifier A String key to identify each connection uniquely.
	 */
	public PooledConnection(C underlyingConnection, String connectionIdentifier) {
		this.underlyingConnection = underlyingConnection;
		this.connectionIdentifier = connectionIdentifier;
	}
	
	public boolean isMarkedUnusable() {
		return markedUnusable;
	}
	
	public void setMarkedUnusable(boolean unusable) {
		this.markedUnusable = unusable;
	}
	
	public C getUnderlyingConnection() {
		return this.underlyingConnection;
	}
	
	public String getConnectionIdentifier() {
		return this.connectionIdentifier;
	}
}
