package com.tibco.rta.common.service;

/**
 * 
 * A service that allows multiple threads to acquire read and write locks.
 * This will be used for synchronizing metrics processing and associated asset lifecycle events.
 *
 */
public interface LockService extends StartStopService {
	/**
	 * Acquire a read lock for the given key.
	 * 
	 * @param key Key to acquire a read lock on.
	 * 
	 * @param timeout Time in milliseconds to try for a lock.
	 * @return true if a lock is acquired successfully.
	 */
	boolean acquireReadLock(String key, int timeout);
	
	/**
	 * Acquire a write lock for the given key.
	 * 
	 * @param key Key to acquire a write lock on.
	 * 
	 * @param timeout Time in milliseconds to try for a lock.
	 * @return true if a lock is acquired successfully.
	 */
	boolean acquireWriteLock(String key, int timeout);
	
	/**
	 * Unlock it from any other thread.
	 * 
	 * @param key
	 * @return
	 */
	boolean unlock(String key);

}
