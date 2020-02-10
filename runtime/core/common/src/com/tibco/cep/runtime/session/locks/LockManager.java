package com.tibco.cep.runtime.session.locks;

import java.util.Collection;
import java.util.concurrent.ConcurrentMap;

import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.session.impl.locks.LockContextResolver;

/**
 * Created by apuneet Date: Jan 31, 2008 Time: 10:58:17 AM
 */
public interface LockManager<K, V> {
    /**
     * Implementations must have an empty constructor.
     *
     * @param cluster
     * @throws Exception
     */
    void init(Cluster cluster) throws Exception;

    void discard();

    /**
     * @return Can be <code>null</code>.
     */
    ConcurrentMap<K, LockRecorder.DoubleLevelLockRecord> getLockRecords();

    //-----------

    /**
     * @param key
     * @param timeoutMillis <b>0 or less</b> to indicate wait and try <b>forever</b>.
     * @param level
     * @return
     */
    boolean lock(K key, long timeoutMillis, LockLevel level);

    void unlock(K key);

    /**
     * @param key
     * @param affectedIds Can be <code>null</code>.
     */
    void unlock(K key, Collection<Long> affectedIds);

    void unlock(Collection<LockData<K, V>> keys);

    /**
     * @param keys
     * @param affectedIds Can be <code>null</code>.
     */
    void unlock(Collection<LockData<K, V>> keys, Collection<Long> affectedIds);

    void unlockAllLocksHeldByThread();

    /**
     * @param affectedIds Can be <code>null</code>.
     */
    void unlockAllLocksHeldByThread(Collection<Long> affectedIds);

    /**
     * Used by the UnLock function for BE users.
     * Only release the lock when explicitUnlock has 
     * been called as many times as lock has been previously called for the same key.
     * All the other unlock methods release the lock
     * with a single call no matter how many times lock has been called with the same key.   
     */
    void explicitUnlock(K key, boolean localOnly);
    
    //---------

    /**
     * @return Might be <code>null</code>.
     */
    Collection<LockData<K, V>> getLockDataStuckToThread();

    /**
     * @return Might be <code>null</code>. If not-<code>null</code>, then the collection will be removed from the
     *         current owner and returned to the caller. Subsequent invocations will return <code>null</code> unless a
     *         new collection has been associated with the Thread.
     */
    Collection<LockData<K, V>> takeLockDataStuckToThread();

    void setContextResolver(LockContextResolver res);
    String getLockContextName(Object context);
    StackTraceElement[] getLockContextStackTrace(Object context);
    Thread.State getLockContextThreadState(Object context);

    //--------------

    public abstract boolean hasLockDataStuckToThread();

	public static enum LockLevel {
        LEVEL1, LEVEL2
    }

    public static interface Level1Lock {
        /**
         * Level 1 lock has the details of the actual lock level that was requested.
         *
         * @return
         */
        LockLevel getRequestedLockLevel();

        Collection<Thread> getQueuedThreads();
    }

    public static interface LockData<K, V> {
        K getKey();

        /**
         * @return Can be <code>null</code>.
         */
        V getValue();
    }
}
