package com.tibco.rta.service.persistence.db;

public interface DatabasePersistenceServiceMBean {

	/**
	 * return total number of insertion in database
	 * @return total number of inserts in database
	 */
	public long getInsertCount();
	
	/**
	 * return total number of updates in database
	 * @return total number of updates in database
	 */
	public long getUpdateCount();
	
	/**
	 * return total number of deletes in database
	 * @return total number of deletes in database
	 */
	public long getDeleteCount();
	
	/**
	 * return total number of transactions in database
	 * @return total number of transactions in database
	 */
	public long getTransactionCount();
	
	
}
