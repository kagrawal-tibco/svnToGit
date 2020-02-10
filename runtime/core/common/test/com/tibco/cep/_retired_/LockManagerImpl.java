package com.tibco.cep._retired_;

import com.tibco.cep.runtime.session.locks.LockManager;
import com.tibco.cep.runtime.session.locks.LockRecorder;
import com.tibco.cep.runtime.session.ServiceLocator;

import java.util.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by IntelliJ IDEA. User: apuneet Date: Jan 31, 2008 Time: 11:01:09 AM To change this
 * template use File | Settings | File Templates.
 */
@Deprecated
public class LockManagerImpl /*implements LockManager*/ {
    Map locks = Collections.synchronizedMap(new HashMap());

    protected static final ThreadLocal currentLocks = new ThreadLocal();

    public ConcurrentMap<Object, LockRecorder.DoubleLevelLockRecord> getLockRecords() {
        return null;
    }

    /**
     * @param key
     * @param timeout
     * @param lockLevel Ignored paramter!
     * @return
     */
    public boolean lock(Object key, long timeout, LockManager.LockLevel lockLevel) {
        try {
            boolean firstTime = true;
            Object lock = null;
            while (true) {
                synchronized (locks) {
                    lock = locks.get(key);
                    if (lock == null) {
                        locks.put(key, new Object());
                        associate(key);
                        return true;
                    }
                    else {
                        if (!firstTime) {
                            return false;
                        }
                        firstTime = false;
                        locks.wait(timeout);
                    }
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /**
     * @param key
     */
    public void unlock(Object key) {
        try {
            Object lock = null;
            synchronized (locks) {
                lock = locks.remove(key);
                if (lock != null) {
                    remove(key);
                    locks.notify();
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /**
     * @param key
     */
    private void associate(Object key) {
        ArrayList locks = (ArrayList) currentLocks.get();
        if (locks == null) {
            locks = new ArrayList(10);
            currentLocks.set(locks);
        }
        locks.add(key);
    }

    private void remove(Object key) {
        ArrayList locks = (ArrayList) currentLocks.get();
        if (locks != null) {
            locks.remove(key);
        }
    }

    //------------

    public void init(ServiceLocator serviceLocator) throws Exception {
    }

    public void discard() {
    }

    public void unlock(Object key, Collection<Long> affectedIds) {
        unlock(key);
    }

    public void unlock(Collection<Object> keys) {
        for (Object key : keys) {
            unlock(key);
        }
    }

    public void unlock(Collection<Object> keys, Collection<Long> affectedIds) {
        unlock(keys);
    }

    public void unlockAllLocksHeldByThread() {
        ArrayList locks = (ArrayList) currentLocks.get();

        if (locks != null) {
            for (Object lock : locks) {
                unlock(lock);
            }

            locks.clear();
        }
    }

    public void unlockAllLocksHeldByThread(Collection<Long> affectedIds) {
        unlockAllLocksHeldByThread();
    }

    public Collection<Object> getLockDataStuckToThread() {
        return (Collection<Object>) currentLocks.get();
    }

    public Collection<Object> takeLockDataStuckToThread() {
        Collection<Object> locks =  (Collection<Object>) currentLocks.get();

        currentLocks.remove();

        return locks;
    }

    public long[] takeIdsReceivedByThreadAfterLock() {
        return null;
    }
}
