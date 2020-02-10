package com.tibco.cep._retired_;

import com.tangosol.net.NamedCache;
import com.tibco.cep.runtime.service.om.coherence.cluster.CacheCluster;
import com.tibco.cep.runtime.service.om.coherence.cluster.EntityCacheName;
import com.tibco.cep.runtime.session.locks.LockManager;
import com.tibco.cep.runtime.session.locks.LockRecorder;
import com.tibco.cep.runtime.session.ServiceLocator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by IntelliJ IDEA. User: apuneet Date: Mar 5, 2008 Time: 8:32:44 AM To change this
 * template use File | Settings | File Templates.
 */
@Deprecated
public class CoherenceLockManagerImpl /*implements LockManager*/ {

    NamedCache lockCache;

    protected static final ThreadLocal currentClusterLocks = new ThreadLocal();

    public CoherenceLockManagerImpl() {
    }

    protected CoherenceLockManagerImpl(NamedCache lockCache) {
        this.lockCache = lockCache;
    }

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
        if (lockCache.lock(key, timeout)) {
            associate(key);
            return true;
        }
        else {
            return false;
        }
    }

    public void unlock(Object key) {
        lockCache.unlock(key);
        remove(key);
    }

    private void associate(Object key) {
        ArrayList locks = (ArrayList) currentClusterLocks.get();
        if (locks == null) {
            locks = new ArrayList(100);
            currentClusterLocks.set(locks);
        }
        locks.add(key);
    }

    private void remove(Object key) {
        ArrayList locks = (ArrayList) currentClusterLocks.get();
        if (locks != null) {
            locks.remove(key);
        }
    }

    //-------------

    public void init(ServiceLocator serviceLocator) throws Exception {
        CacheCluster cacheCluster = serviceLocator.locateCacheCluster();

        String clusterName = cacheCluster.getClusterName();

        EntityCacheName entityCacheName = EntityCacheName
                .getCacheName(EntityCacheName.DISTRIBUTED_CACHE_UNLIMITED_NOBACKINGSTORE,
                        clusterName, "", "Locks");

        this.lockCache = cacheCluster.getCache(entityCacheName);
    }

    public void discard() {
    }

    public void unlock(Object key, Collection<Long> affectedIds) {
        unlock(key);
    }

    public void unlock(Collection<Object> keys) {
        if (keys != null) {
            for (Object key : keys) {
                unlock(key);
            }
        }
    }

    public void unlock(Collection<Object> keys, Collection<Long> affectedIds) {
        unlock(keys);
    }

    public void unlockAllLocksHeldByThread() {
        ArrayList locks = (ArrayList) currentClusterLocks.get();
        if (locks != null) {
//            for (int i=0; i < locks.size(); i++) {
//                unlock(locks.get(i));
//            }
            locks.clear();
        }
    }

    public void unlockAllLocksHeldByThread(Collection<Long> affectedIds) {
        unlockAllLocksHeldByThread();
    }

    public Collection<Object> getLockDataStuckToThread() {
        Collection<Object> locks = (Collection<Object>) currentClusterLocks.get();

        if (locks != null) {
            return locks;
        }

        return null;
    }

    public Collection<Object> takeLockDataStuckToThread() {
        Collection<Object> locks = (Collection<Object>) currentClusterLocks.get();

        if (locks != null) {
            currentClusterLocks.remove();

            return locks;
        }

        return null;
    }

    public long[] takeIdsReceivedByThreadAfterLock() {
        return null;
    }
}
