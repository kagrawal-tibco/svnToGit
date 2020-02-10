package com.tibco.rta.common.service.impl;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.service.LockService;
import com.tibco.rta.common.service.ReadWriteLock;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;

public class LockServiceImpl extends AbstractStartStopServiceImpl implements LockService {
	
	
    protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_METRIC.getCategory());

	protected ConcurrentHashMap<String, ReadWriteLock> locks = new ConcurrentHashMap<String, ReadWriteLock>();
	
	protected boolean leaseGranularityIsThread;
	
	protected boolean isFairPolicy;
	
	@Override
	public void init(Properties configuration) throws Exception {
		this.configuration = configuration;
		
		String leaseGranularity = (String) ConfigProperty.RTA_LOCK_LEASE_GRANULARITY.getValue(configuration);
		leaseGranularityIsThread = "thread".equals(leaseGranularity);
		
		String fairPolicy = (String) ConfigProperty.RTA_LOCK_LEASE_FAIRNESS_POLICY.getValue(configuration);
		isFairPolicy = Boolean.parseBoolean(fairPolicy);
	}

	
	@Override
	public boolean acquireWriteLock(String key, int timeoutInMillis) {
		ReadWriteLock rwlock = getLock();
		//get and put should be atomic. putifabsent gives this behavior.
		ReadWriteLock rwlockOld = locks.putIfAbsent(key, rwlock);
		if (rwlockOld != null) {
			rwlock = rwlockOld;
		}
		boolean isWrite = true;
		try {
			if (LOGGER.isEnabledFor(Level.DEBUG)) {
				LOGGER.log(Level.DEBUG, "Trying to ACQUIRE lock [%s] [%s]", isWrite? "write" : "read" , key);
			}
			if (rwlock.acquireWriteLock(timeoutInMillis)) {
				if (LOGGER.isEnabledFor(Level.DEBUG)) {
					LOGGER.log(Level.DEBUG, "Trying to ACQUIRE lock [%s] [%s]-successful", key, isWrite? "write" : "read");
				}
				return true;
			}
			if (LOGGER.isEnabledFor(Level.DEBUG)) {
				LOGGER.log(Level.DEBUG, "Trying to ACQUIRE lock [%s] [%s] failed", isWrite? "write" : "read" , key);
			}
			return false;

		} catch (InterruptedException e) {
			e.printStackTrace();
			locks.remove(key);
			return false;
		}
	}
	
	@Override
	public boolean acquireReadLock(String key, int timeoutInMillis) {
		ReadWriteLock rwlock = getLock();
		//get and put should be atomic. putifabsent gives this behavior.
		ReadWriteLock rwlockOld = locks.putIfAbsent(key, rwlock);
		if (rwlockOld != null) {
			rwlock = rwlockOld;
		}
		
		boolean isWrite = false;
		try {
			if (LOGGER.isEnabledFor(Level.DEBUG)) {
				LOGGER.log(Level.DEBUG, "Trying to ACQUIRE lock [%s] [%s]", key, isWrite? "write" : "read");
			}
			if (rwlock.acquireReadLock(timeoutInMillis)) {
				if (LOGGER.isEnabledFor(Level.DEBUG)) {
					LOGGER.log(Level.DEBUG, "Trying to ACQUIRE lock [%s] [%s]-successful", key, isWrite? "write" : "read");
				}
				return true;
			}
			if (LOGGER.isEnabledFor(Level.DEBUG)) {
				LOGGER.log(Level.DEBUG, "Trying to ACQUIRE lock [%s] [%s] failed", isWrite? "write" : "read" , key);
			}
			return false;

		} catch (InterruptedException e) {
			e.printStackTrace();
			locks.remove(key);
			return false;
		}
	}

	@Override
	public boolean unlock(String key) {
		ReadWriteLock rwlock = locks.get(key);
		if (rwlock != null) {
			boolean isWrite = rwlock.isWriteLocked();
			try {
				if (LOGGER.isEnabledFor(Level.DEBUG)) {
					LOGGER.log(Level.DEBUG, "Trying to RELEASE lock [%s] [%s]", key, isWrite? "write" : "read");
				}
				rwlock.unlock();
				if (LOGGER.isEnabledFor(Level.DEBUG)) {
					LOGGER.log(Level.DEBUG, "Trying to RELEASE lock [%s] [%s]-released", key, isWrite? "write" : "read");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			if (LOGGER.isEnabledFor(Level.DEBUG)) {
				LOGGER.log(Level.DEBUG, "Trying to RELEASE lock [%s] is null", key);
			}
		}
		return true;
	}
	
	private ReadWriteLock getLock() {
		if (leaseGranularityIsThread) {
			return new ReadWriteLockImpl2(isFairPolicy);
		} else {
			return new ReadWriteLockImpl();
		}
	}
	public static void main (String args[]) throws Exception {
		LOGGER.setLevel(Level.DEBUG);
		Properties props = new Properties();
		props.put(ConfigProperty.RTA_LOCK_LEASE_FAIRNESS_POLICY.getPropertyName(), "true");
		props.put(ConfigProperty.RTA_LOCK_LEASE_GRANULARITY.getPropertyName(), "thread");
		LockServiceImpl ls = new LockServiceImpl();
		ls.init(props);
		for (int i=0; i<2; i++) {
			new MyThread(ls, i).start();
		}
		
	}
	static class MyThread extends Thread {
		
		int t;
		LockServiceImpl ls;
		public MyThread (LockServiceImpl ls, int t) {
			this.ls= ls;
			this.t=t;
		}
		@Override
		public void run() {
			for (;;) {
				if (t % 2 == 0) {
					try {
						Thread.sleep(2000);
					} catch (Exception e) {}
					ls.acquireWriteLock("1", -1);
					ls.acquireReadLock("1", -1);
				} else {
//					System.out.println("tt");
					ls.acquireReadLock("1", -1);
					ls.acquireReadLock("1", -1);
					ls.acquireReadLock("1", -1);
//					ls.acquireWriteLock("1", -1);
//					ls.acquireWriteLock("1", -1);

				}
				try {
					Thread.sleep(10000);
					ls.unlock("1");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
