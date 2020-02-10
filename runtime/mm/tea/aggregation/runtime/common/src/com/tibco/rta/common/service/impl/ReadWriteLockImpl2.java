package com.tibco.rta.common.service.impl;

/**
 * @author bgokhale
 * 
 * Wrapper over Java's ReadWriteLock. This lock can be used where lock/unlock can be issued from the same thread.
 * 
 * 
 */
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.tibco.rta.common.service.ReadWriteLock;

public class ReadWriteLockImpl2 implements ReadWriteLock {
	
	protected boolean fairPolicyEnabled;

	public ReadWriteLockImpl2 (boolean fairPolicyEnabled) {
		this.fairPolicyEnabled = fairPolicyEnabled;
	}

	final java.util.concurrent.locks.ReentrantReadWriteLock lock = new ReentrantReadWriteLock(fairPolicyEnabled);

	public boolean acquireWriteLock(int timeInMillis) throws InterruptedException {
		
		//relinquish all the readlocks, so current thread can acquire a write lock.
		while (lock.getReadHoldCount() > 0) {
			lock.readLock().unlock();
		}
		
		
		if (timeInMillis > 0) {
			return lock.writeLock().tryLock(timeInMillis, TimeUnit.MILLISECONDS);
		} else {
			lock.writeLock().lock();
			return true;
		}
	}

	public boolean acquireReadLock(int timeInMillis) throws InterruptedException {
		if (timeInMillis > 0) {
			return lock.readLock().tryLock(timeInMillis, TimeUnit.MILLISECONDS);
		} else {
			lock.readLock().lock();
			return true;
		}
	}

	public void unlock() throws InterruptedException {
		try {
			if (lock.isWriteLockedByCurrentThread()) {
				while(lock.getWriteHoldCount() > 0) {
					lock.writeLock().unlock();
				}
			} else {	
				//relinquish all the readlocks, so current thread can acquire a write lock and other thread can acquire a write lock
				while (lock.getReadHoldCount() > 0) {
					lock.readLock().unlock();
				}
			}
		} catch (Exception e){}

	}

	public boolean isWriteLocked() {
		return lock.isWriteLocked();
	}

}