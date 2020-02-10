package com.tibco.rta.common.service;

public interface ReadWriteLock {

	boolean acquireWriteLock(int timeInMillis) throws InterruptedException;

	boolean acquireReadLock(int timeInMillis) throws InterruptedException;
	
	void unlock() throws InterruptedException;

	boolean isWriteLocked();

}
