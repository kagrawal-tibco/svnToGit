package com.tibco.rta.common.service.impl;

/**
 * @author bgokhale
 * 
 * A rudimentary implementation of a ReadWriteLock that enables unlock from another thread.
 * Java's ReentrantReadWriteLock has a limitation of unlocking only from the thread acquiring the lock.
 * This implementation attempts to get around that limitation.
 * 
 * Multiple threads should be able to acquire read locks, but only one thread should acquire a write lock.
 * Once write lock is exclusive. Once it is taken, read locks also have to wait.
 * 
 * This could be required for correctly handling deletes while online processing.
 * 
 */
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.tibco.rta.common.service.ReadWriteLock;

public class ReadWriteLockImpl implements ReadWriteLock {
	final Lock lock = new ReentrantLock();
	final Condition conditionVar = lock.newCondition();

	boolean writeAcquired = false;
	int readers = 0;

	public boolean acquireWriteLock(int timeInMillis) throws InterruptedException {
		lock.lock();
		try {
			while (writeAcquired || readers > 0) {
				if (timeInMillis < 0) {
					conditionVar.await();
				} else {
					boolean success = conditionVar.await(timeInMillis, TimeUnit.MILLISECONDS);
					if (!success) {
						return false;
					}
				}
			}
			writeAcquired = true;
		} finally {
			lock.unlock();
		}
		return true;
	}

	public boolean acquireReadLock(int timeInMillis) throws InterruptedException {
		lock.lock();
		try {
			while (writeAcquired) {
				if (timeInMillis < 0) {
					conditionVar.await();
				} else {
					boolean success = conditionVar.await(timeInMillis, TimeUnit.MILLISECONDS);
					if (!success) {
						return false;
					}
				}
			}
			readers++;
		} finally {
			lock.unlock();
		}
		return true;
	}

	public void unlock() throws InterruptedException {
		lock.lock();
		try {
			if (writeAcquired) {
				writeAcquired = false;
			} else if (readers > 0) {
				readers--;
			}
			if (!writeAcquired || readers == 0) {
				conditionVar.signalAll();
			}

		} finally {
			lock.unlock();
		}

	}

	@Override
	public boolean isWriteLocked() {
		return writeAcquired;
	}

}