/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.session.impl.locks;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.time.HeartbeatService;
import com.tibco.cep.runtime.session.locks.LockKeeper;
import com.tibco.cep.runtime.session.locks.LockManager;
import com.tibco.cep.runtime.session.locks.LockRecorder;
import com.tibco.cep.runtime.session.locks.LockRecorder.DoubleLevelLockRecord;
import com.tibco.cep.runtime.session.locks.LockRecorder.Level2LockRecord;
import com.tibco.cep.util.annotation.DependsOn;

/*
* Author: Ashwin Jayaprakash Date: Jul 7, 2008 Time: 12:57:56 PM
*/

public abstract class AbstractConcurrentLockManager<K, V> implements LockManager<K, V> {
    /**
     * {@value}.
     */
    public static final String PROPERTY_ENABLE_LOCK_RECORDING =
            "be.engine.lockManager.enableRecording";

    private static final long ONE_MILLISEC_IN_NANOS = 1000 * 1000;

    protected ConcurrentHashMap<Object, InvalidatableLock> localLockMap;

    protected ConcurrentHashMap<Object, TxnDataHeldByThread<K, V>> dataPerThread;

    protected ConcurrentHashMap<K, LockRecorder.DoubleLevelLockRecord> lockRecords;

    protected HeartbeatService heartbeatService;

    /**
     * See {@link #PROPERTY_ENABLE_LOCK_RECORDING}.
     */
    protected boolean lockRecordingEnabled;

    protected LockKeeper<K, V> lockKeeper;

    protected LockContextResolver contextResolver = new ThreadLockContextResolver();

    /**
     * Invoke {@link #init(Cluster)} before using.
     */
    protected AbstractConcurrentLockManager() {}

    /**
     * If this constructor is used, then no need to invoke {@link #init(Cluster)}. Calls {@link
     * #AbstractConcurrentLockManager(com.tibco.cep.runtime.session.locks.LockKeeper)} with <code>false</code>.
     *
     * @param lockKeeper
     */
    protected AbstractConcurrentLockManager(LockKeeper<K, V> lockKeeper) {
        this(false, lockKeeper);
    }

    /**
     * If this constructor is used, then no need to invoke {@link #init(Cluster)}.
     *
     * @param enableLockRecording
     * @param lockKeeper
     */
    protected AbstractConcurrentLockManager(boolean enableLockRecording,
                                            LockKeeper<K, V> lockKeeper) {
        doInit(enableLockRecording, lockKeeper);
    }

    public void setContextResolver(LockContextResolver resolver) {
    	contextResolver = resolver;    	
    }
    
    /**
     * @param enableLockRecording Creates and uses {@link #lockRecords} if <code>true</code>.
     * @param lockKeeper
     */
    private void doInit(boolean enableLockRecording, LockKeeper<K, V> lockKeeper) {
        this.lockKeeper = lockKeeper;
        lockKeeper.init();

        this.localLockMap = new ConcurrentHashMap<Object, InvalidatableLock>(1024 * 12, 0.75f, 32);

        this.dataPerThread = new ConcurrentHashMap<Object, TxnDataHeldByThread<K, V>>();

        this.lockRecords = new ConcurrentHashMap<K, LockRecorder.DoubleLevelLockRecord>();

        ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
        this.heartbeatService = registry.getService(HeartbeatService.class);

        if (enableLockRecording) {
            this.lockRecordingEnabled = enableLockRecording;
        }
    }

    protected LockKeeper<K, V> createLockForInit(Cluster cluster) throws Exception {
        return new DummyLockKeeper<K, V>();
    }

    /**
     * Calls {@link #createLockForInit(Cluster)}
     *
     * @param cluster
     * @throws Exception
     */
    @DependsOn({HeartbeatService.class})
    public void init(Cluster cluster) throws Exception {
        LockKeeper<K, V> level2Lock = createLockForInit(cluster);

        boolean enableLockRecording = false;
        BEProperties beProperties = (BEProperties) cluster.getRuleServiceProvider().getProperties();
        if (beProperties != null) {
            enableLockRecording = beProperties.getBoolean(PROPERTY_ENABLE_LOCK_RECORDING, false);

            if (enableLockRecording) {
                Logger logger = LogManagerFactory.getLogManager().getLogger(getClass());
                String msg = "[" + getClass().getSimpleName() + "] Lock recording is on.";
                if (logger != null) {
                    logger.log(Level.WARN, msg);
                }
                else {
                    System.err.println(msg);
                }
            }
        }

        doInit(enableLockRecording, level2Lock);
    }

    /**
     * @return Can be <code>null</code>.
     */
    public ConcurrentMap<K, LockRecorder.DoubleLevelLockRecord> getLockRecords() {
        return lockRecords;
    }
    
    public void discard() {
/*
        if (localLockMap == null) {
            return;
        }

        localLockMap.clear();
        localLockMap = null;

        dataPerThread.clear();
        dataPerThread = null;

        if (lockRecords != null) {
            lockRecords.clear();
            lockRecords = null;
        }

        lockKeeper.discard();
        lockKeeper = null;
*/
    }

    //-----------------

    /**
     * @param key
     * @param waitTimeNanos 0 or less means wait and try forever.
     * @return <code>null</code> if the lock did not succeed.
     */
    private InvalidatableLock tryLocalLock(K key, long waitTimeNanos) {
        long givenTimeToSpendNanos = waitTimeNanos;
        if (givenTimeToSpendNanos <= 0) {
            givenTimeToSpendNanos = Long.MAX_VALUE;
        }

        final long startTimeNanos = System.nanoTime();
        long tryLockTimeNanos = givenTimeToSpendNanos;

        for (InvalidatableLock localLock = localLockMap.get(key); ;) {
            //If this is our lock, then don't lock again.
            boolean localLockSucceeded = false;

            if (localLock == null) {
                while (localLockSucceeded == false) {
                    localLock = new InvalidatableLock();

                    //Lock first and then place it in the map.
                    try {
                        localLock.acquire();
                        localLockSucceeded = true;
                    }
                    catch (InterruptedException e) {
                        //Ignore.
                    }
                }

                InvalidatableLock existingLocalLock = localLockMap.putIfAbsent(key, localLock);

                //Somebody has already put a lock in. So, use theirs and release our lock.
                if (existingLocalLock != null) {
                    localLock.release();
                    localLockSucceeded = false;

                    localLock = existingLocalLock;
                }
            }

            //---------

            while (localLockSucceeded == false && localLock.isValid()) {
                try {
                    localLockSucceeded =
                            localLock.tryAcquire(tryLockTimeNanos, TimeUnit.NANOSECONDS);
                }
                catch (InterruptedException e) {
                    //Ignore.
                }

                if (localLockSucceeded) {
                    break;
                }

                //-------------

                //Might have woken up sooner because of spurious wake ups.
                long actualTimeSpentTryingNanos = System.nanoTime() - startTimeNanos;
                tryLockTimeNanos = givenTimeToSpendNanos - actualTimeSpentTryingNanos;
                if (tryLockTimeNanos <= 0) {
                    //Could not lock in time.
                    return null;
                }
            }

            //---------

            if (localLockSucceeded) {
                if (localLock.isValid()) {
                    recordLevel1Lock(key, localLock);

                    return localLock;
                }
                else {
                    //The lock we've just acquired is not in use anymore. Unlock and discard it.
                    localLock.release();
                }
            }

            //---------

            //Get the latest lock that is in the map. Could also be null.
            localLock = localLockMap.get(key);

            if (tryLockTimeNanos > 0) {
                //Start all over with the current lock in the map.
                continue;
            }

            //Could not lock in time.
            return null;
        }
    }

    private void unlockLocal(K key, InvalidatableLock lock) {
        if (lock.hasQueuedThreads() == false) {
            /*
            It appears that nobody is waiting on this lock. Remove it and let it get GC'ed.
            A waiter could have accumulated just in between these 2 lines. But it is taken care
            of in the local-lock method.
            */
            localLockMap.remove(key);

            lock.invalidate();
        }

        lock.clearLockLevel();

        lockRecords.remove(key);

        lock.release();
    }

    /**
     * @param key
     * @param waitTimeNanos 0 or less means wait and try forever.
     * @return
     */
    private boolean tryGlobalLock(K key, long waitTimeNanos) {
        boolean lockStatus = lockKeeper.lock(key, waitTimeNanos);

        if (lockStatus) {
            recordLevel2Lock(key);
        }

        return lockStatus;
    }

    private void recordLevel1Lock(K key, InvalidatableLock localLock) {
        Object context = contextResolver.getContext();
        long timestamp = heartbeatService.getLatestHeartbeat().getTimestamp();

        Exception trace = null;
        if (lockRecordingEnabled) {
            String threadName = getLockContextName(context);
            trace = new Exception("Lock acquired by thread [" + threadName + "]");
        }

        LockRecorder.Level1LockRecord level1LockRecord =
                new LockRecorder.Level1LockRecord(context, timestamp, localLock, trace);

        LockRecorder.DoubleLevelLockRecord dll =
                new LockRecorder.DoubleLevelLockRecord(level1LockRecord);

        lockRecords.put(key, dll);
    }

    private void recordLevel2Lock(K key) {
        LockRecorder.DoubleLevelLockRecord dll = lockRecords.get(key);

        if (dll != null) {
        	Object context = contextResolver.getContext();
            long timestamp = heartbeatService.getLatestHeartbeat().getTimestamp();

            LockRecorder.Level2LockRecord level2LockRecord =
                    new LockRecorder.Level2LockRecord(context, timestamp);

            dll.setLevel2(level2LockRecord);
        }
    }

    private void unlockGlobal(K key) {
        lockKeeper.unlock(key);
    }

    //---------------

    void onLockSuccess(K key, InvalidatableLock lock) {
        Object currentThread = contextResolver.getContext();;

        TxnDataHeldByThread<K, V> dataHeldByThread = dataPerThread.get(currentThread);
        if (dataHeldByThread == null) {
            dataHeldByThread = new TxnDataHeldByThread<K, V>();

            dataPerThread.put(currentThread, dataHeldByThread);
        }

        //-------

        V value = lockKeeper.remove(lock.getRequestedLockLevel(), key);

        dataHeldByThread.addLockData(key, value);
    }

    private TxnDataHeldByThread<K, V> getTxnDataHeldByThread() {
        Object currentThread = contextResolver.getContext();;

        return dataPerThread.get(currentThread);
    }

    /**
     * @param key
     * @return
     * @throws LockManagerException
     */
    protected InvalidatableLock handleAbsentLock(K key) {
        throw new LockManagerException(
                "Unable to unlock key [" + key + "]. Lock was never acquired.");
    }

    /**
     * @param key
     * @param txn Can be <code>null</code>.
     */
    void onUnlockSuccess(K key, TxnDataHeldByThread<K, V> txn) {
        if (txn != null) {
            int remaining = txn.removeLockedData(key);

            if (remaining == 0) {
            	//System.out.println("UNLOCK:Removed "+key+" Thread:"+Thread.currentThread().getName()+" Context:"+contextResolver.getContext());
            	String threadLockingContext = System.getProperty("be.engine.locking.context.thread","false");
            	boolean isThreadLockingContext = Boolean.getBoolean(threadLockingContext.trim());
            	Object currentThread = contextResolver.getContext();
            	if (isThreadLockingContext) {
            		currentThread = Thread.currentThread();
            	} else {
            		if (currentThread == null) {
            			currentThread = Thread.currentThread();
            		}
            	}
                dataPerThread.remove(currentThread);

                txn.discard();
            }
        }
    }

    //---------------

    public boolean lock(K key, long timeToSpendMillis, LockLevel level) {
    	final long givenTimeToSpendNanos = timeToSpendMillis <=0 ? Long.MAX_VALUE : timeToSpendMillis * ONE_MILLISEC_IN_NANOS;

        //-------------
        
    	long tempNanos = System.nanoTime();
    	InvalidatableLock localLock = null;
    	boolean alreadyLocalLocked = false;
    	DoubleLevelLockRecord lockRec = null;
    	
    	{
    	    TxnDataHeldByThread txn = getTxnDataHeldByThread();
    	    if(txn != null && txn.getLockData(key) != null) {
	            alreadyLocalLocked = true;
	            lockRec = lockRecords.get(key);
	            lockRec.getLevel1().incrLock();
    	    }
    	}

    	if(alreadyLocalLocked && level == LockLevel.LEVEL1) {
    	    return true;
    	} else if(alreadyLocalLocked && level == LockLevel.LEVEL2) {
            //the record doesn't belong to this thread unless alreadyLocalLocked is true
            Level2LockRecord l2rec = lockRec.getLevel2();
            if(l2rec != null) {
                //already global locked;
                l2rec.incrLock();
                return true;
            }
            
            localLock = localLockMap.get(key);
        } else {
            localLock = tryLocalLock(key, givenTimeToSpendNanos);
        
            if (localLock == null) {
                return false;
            }
        
            localLock.setRequestedLockLevel(level);
            
            //Just first level. Nothing more to do.
            if (level == LockLevel.LEVEL1) {
                onLockSuccess(key, localLock);
                return true;
            }
    	}

        boolean globalLockSuccess = false;
        try {
        	//timeout - elapsed
            tempNanos = givenTimeToSpendNanos - (System.nanoTime() - tempNanos);
            //even if time has run out, try to get the global lock if it is uncontested
            if(tempNanos <= 0) tempNanos = 1;
            
            globalLockSuccess = tryGlobalLock(key, tempNanos);

            if (globalLockSuccess) {
                if(alreadyLocalLocked) localLock.setRequestedLockLevel(LockLevel.LEVEL2);
                else onLockSuccess(key, localLock);
            }
        }
        finally {
            //Cleanup local work.
            if (!globalLockSuccess) {
                if(alreadyLocalLocked) {
                    lockRec.getLevel1().decrLock();
                } else {
                    unlockLocal(key, localLock);
                }
            }
        }

        return globalLockSuccess;
    }
    
    /**
     * @param lockData
     * @param txn         Can be <code>null</code>.
     * @param affectedIds Can be <code>null</code>.
     */
    private void handleUnlock(LockData<K, V> lockData, TxnDataHeldByThread<K, V> txn,
            Collection<Long> affectedIds)
    {
        handleUnlock(lockData, txn, affectedIds, false, false);
    }
    private void handleUnlock(LockData<K, V> lockData, TxnDataHeldByThread<K, V> txn,
                              Collection<Long> affectedIds, boolean explicitUnlock, boolean localOnly)
    {
        K key = lockData.getKey();
        InvalidatableLock lock = localLockMap.get(key);
        if (lock == null) {
            handleAbsentLock(key);
            return;
        }

        boolean isGlobal = lock.getRequestedLockLevel() == LockLevel.LEVEL2;
        //defaults for explicitUnlock=false
        boolean unlockLocal = true;
        boolean unlockGlobal = isGlobal;
        DoubleLevelLockRecord dll = null;
               
        if(explicitUnlock) { 
            dll = lockRecords.get(key);
            unlockLocal = dll.getLevel1().decrLock() == 0;

            if(isGlobal) {
                unlockGlobal = !localOnly && dll.getLevel2().decrLock() == 0;
                
                if(unlockLocal && !unlockGlobal) {
                    //not allowed to release the local lock and keep the global lock
                    //the argument to Cluster.DataGrid.lock is "localOnly" implying
                    //the alternative is local + global
                   throw new LockManagerException(
                         "Cannot release local lock for key [" + key + "] without releasing cluster-wide lock");
                }
            } else {
                if(!localOnly) { 
                        throw new LockManagerException(
                                "UnLock called with localOnly=false for local-only lock for key [" + key + "] ");
                }
            }
        }
        
        //a lock can never be unlocked locally and still be locked globally
        //so onUnlockSuccess is only necessary for unlockLocal
        if(unlockLocal || unlockGlobal) {
            lockKeeper.put(lock.getRequestedLockLevel(), lockData, affectedIds);
            if(unlockGlobal) {
                unlockGlobal(key);
                //for explicit unlock releasing global lock and keeping local lock
                if(!unlockLocal) {
                    dll.setLevel2(null);
                    lock.setRequestedLockLevel(LockLevel.LEVEL1);
                }
            }
            if(unlockLocal) {
                unlockLocal(key, lock);
                onUnlockSuccess(key, txn);
            }
        }
    }

    public void unlock(K key) {
        unlock(key, null);
    }
    public void unlock(K key, Collection<Long> affectedIds) {
        _unlock(key, affectedIds, false, false);
    }
    protected void _unlock(K key, Collection<Long> affectedIds, boolean explicit, boolean localOnly) {
        if (key == null) {
            return;
        }

        TxnDataHeldByThread<K, V> txn = getTxnDataHeldByThread();
        LockData<K, V> lockData = getLockDataForKey(key, txn);

        lockKeeper.batchStart();
        handleUnlock(lockData, txn, affectedIds, explicit, localOnly);
        lockKeeper.batchEnd();
    }
    
    public void explicitUnlock(K key, boolean localOnly) {
        _unlock(key, null, true, localOnly);
    }

    /**
     * @param key
     * @param txn
     * @return
     * @see #handleAbsentLockData(Object, com.tibco.cep.runtime.session.impl.locks.AbstractConcurrentLockManager.TxnDataHeldByThread)
     *      if the txn is null or if lockData was not found.
     */
    private LockData<K, V> getLockDataForKey(K key, TxnDataHeldByThread<K, V> txn) {
        if (txn != null) {
            LockData<K, V> lockData = txn.getLockData(key);

            if (lockData != null) {
                return lockData;
            }
        }

        return handleAbsentLockData(key, txn);
    }

    /**
     * @param key
     * @param txn Can be <code>null</code>.
     * @return
     * @throws LockManagerException
     */
    protected LockData<K, V> handleAbsentLockData(K key, TxnDataHeldByThread<K, V> txn) {
        throw new LockManagerException(
                "Unable to unlock key [" + key + "]. Lock is not held.");
    }

    public void unlock(Collection<LockData<K, V>> allLockData) {
        if (allLockData == null) {
            return;
        }

        unlock(allLockData, null);
    }

    public void unlock(Collection<LockData<K, V>> allLockData, Collection<Long> affectedIds) {
        if (allLockData == null) {
            return;
        }

        //Will be null most(all) of the time.
        TxnDataHeldByThread<K, V> txn = getTxnDataHeldByThread();

        lockKeeper.batchStart();
        try {
            for (LockData<K, V> lockData : allLockData) {
                handleUnlock(lockData, txn, affectedIds);
            }
        }
        finally {
            lockKeeper.batchEnd();
        }
    }

    public void unlockAllLocksHeldByThread() {
        unlockAllLocksHeldByThread(null);
    }

    public void unlockAllLocksHeldByThread(Collection<Long> affectedIds) {
        Collection<LockData<K, V>> allLockData = takeLockDataStuckToThread();
        if (allLockData == null) {
            return;
        }

        lockKeeper.batchStart();
        try {
            for (LockData<K, V> lockData : allLockData) {
                handleUnlock(lockData, null, affectedIds);
            }
        }
        finally {
            lockKeeper.batchEnd();
        }
    }

    //---------------

    public Collection<LockData<K, V>> getLockDataStuckToThread() {
        TxnDataHeldByThread<K, V> txn = getTxnDataHeldByThread();
        if (txn == null) {
            return null;
        }

        return txn.getAllLockData();
    }
    
    @Override
	public boolean hasLockDataStuckToThread() {
    	Object currentThread = contextResolver.getContext();;

        return dataPerThread.contains(currentThread);
    }

    public Collection<LockData<K, V>> takeLockDataStuckToThread() {
        Object currentThread = contextResolver.getContext();

        //Remove because we will be discarding this now.
        TxnDataHeldByThread<K, V> txn = dataPerThread.remove(currentThread);
        if (txn == null) {
            return null;
        }

        Collection<LockData<K, V>> allTheLockData = txn.takeAllLockData();

        txn.discard();

        return allTheLockData;
    }

    //---------------

    /**
     * Uses just the {@link #getKey()} in {@link #hashCode()} and {@link #equals(Object)}.
     */
    protected static class LockDataImpl<K, V> implements LockData<K, V> {
        protected K key;

        protected V value;

        /**
         * @param key
         * @param value Can be <code>null</code>.
         */
        public LockDataImpl(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof LockDataImpl)) {
                return false;
            }

            LockDataImpl lockData = (LockDataImpl) o;

            return key.equals(lockData.key);
        }

        public int hashCode() {
            return key.hashCode();
        }
    }

    protected static class TxnDataHeldByThread<K, V> {
        protected static final int MIN_LOCKED_KEYS_CAPACITY = 4;

        /**
         * Key is {@link com.tibco.cep.runtime.session.locks.LockManager.LockData#getKey()}.
         */
        protected LinkedHashMap<K, LockData<K, V>> allLockData;

        public TxnDataHeldByThread() {
            this.allLockData = new LinkedHashMap<K, LockData<K, V>>(MIN_LOCKED_KEYS_CAPACITY);
        }

        //------------

        protected void addLockData(K key, V value) {
            allLockData.put(key, new LockDataImpl<K, V>(key, value));
        }

        /**
         * @param key
         * @return Number of locks remaining.
         */
        protected int removeLockedData(K key) {
            allLockData.remove(key);

            return allLockData.size();
        }

        protected LockData<K, V> getLockData(K key) {
            return allLockData.get(key);
        }

        protected LockData<K, V> removeLockData(K key) {
            return allLockData.remove(key);
        }

        protected Collection<LockData<K, V>> getAllLockData() {
            Collection<LockData<K,V>> values = allLockData.values();

            ArrayList<LockData<K,V>> list = new ArrayList<LockData<K,V>>(values);
            Collections.reverse(list);

            return list;
        }

        protected Collection<LockData<K, V>> takeAllLockData() {
            Collection<LockData<K, V>> collection = getAllLockData();

            allLockData = null;

            return collection;
        }

        protected boolean locksRemaining() {
            return !(allLockData == null || allLockData.isEmpty());
        }

        //------------

        protected void discard() {
            if (allLockData != null) {
                allLockData.clear();

                allLockData = null;
            }
        }
    }

    protected static class InvalidatableLock extends Semaphore implements Level1Lock {
        protected volatile boolean valid;

        protected LockLevel requestedLockLevel;

        /**
         * @see #InvalidatableLock(com.tibco.cep.runtime.session.locks.LockManager.LockLevel) with null.
         */
        public InvalidatableLock() {
            this(null);
        }

        /**
         * Fair lock! See {@link java.util.concurrent.locks.ReentrantLock#isFair()}. Fairness was enabled because on
         * multicore Linux, the timeouts for a lot of Threads reduced greatly without any change in the throughput.
         *
         * @param lockLevel
         */
        public InvalidatableLock(LockLevel lockLevel) {
            super(1, true);

            this.valid = true;
            this.requestedLockLevel = lockLevel;
        }

        public boolean isValid() {
            return valid;
        }

        /**
         * Should called by lock owner only!
         */
        protected void invalidate() {
            valid = false;
        }

        /**
         * Should called by lock owner only!
         */
        protected void clearLockLevel() {
            requestedLockLevel = null;
        }

        public LockLevel getRequestedLockLevel() {
            return requestedLockLevel;
        }

        /**
         * Should called by lock owner only!
         *
         * @param requestedLockLevel
         */
        protected void setRequestedLockLevel(LockLevel requestedLockLevel) {
            this.requestedLockLevel = requestedLockLevel;
        }

        @Override
        public Collection<Thread> getQueuedThreads() {
            return super.getQueuedThreads();
        }
    }

    public static class DummyLockKeeper<K, V> implements LockKeeper<K, V> {
        public void init() {
        }

        public void batchStart() {
        }

        public boolean lock(K key, long timeoutNanos) {
            return true;
        }

        public void put(LockLevel lockLevel, LockData<K, V> lockData,
                        Collection<Long> affectedIds) {
        }

        public V remove(LockLevel lockLevel, K key) {
            return null;
        }

        public void unlock(K key) {
        }

        public void batchEnd() {
        }

        public void discard() {
        }
    }

    public static class LockManagerException extends RuntimeException {
        public LockManagerException(String message) {
            super(message);
        }

        public LockManagerException(String message, Throwable cause) {
            super(message, cause);
        }

        public LockManagerException(Throwable cause) {
            super(cause);
        }
    }

    public String getLockContextName(Object context) {
    	return contextResolver.getName(context);
    }
    
    public StackTraceElement[] getLockContextStackTrace(Object context) {
    	return contextResolver.getStackTrace(context);
    }
    
    public Thread.State getLockContextThreadState(Object context) {
    	return contextResolver.getThreadState(context);
    }
}
