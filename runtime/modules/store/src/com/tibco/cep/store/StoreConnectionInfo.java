/**
 * 
 */
package com.tibco.cep.store;

/**
 * @author vpatil
 *
 */
public class StoreConnectionInfo {
	
	private StoreType storeType;
	private String url;
	private String name;
	
	private double connectionTimeout;
	private int poolSize;
	
	public StoreConnectionInfo(StoreType storeType, String url) {
		this.storeType = storeType;
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public String getName() {
		return name;
	}

	public StoreType getStoreType() {
		return storeType;
	}

	public double getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(double connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getPoolSize() {
		return poolSize;
	}

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}
}
