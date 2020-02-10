package com.tibco.cep._retired_;

import com.tangosol.net.NamedCache;
import com.tibco.cep.runtime.session.locks.LockManager;
import com.tibco.cep.runtime.session.locks.LockRecorder;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.ServiceLocator;
import com.tibco.cep.runtime.service.om.coherence.cluster.CacheCluster;
import com.tibco.cep.runtime.service.om.coherence.cluster.EntityCacheName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Mar 5, 2008
 * Time: 8:32:44 AM
 * To change this template use File | Settings | File Templates.
 */
@Deprecated
public class ClusterLockManager /*implements LockManager*/ {
    CacheCluster cluster;
    NamedCache lockCache;
    protected static final ThreadLocal currentClusterLocks = new ThreadLocal();
    Map local_locks= new HashMap(10000);
//    ReentrantLock LOCK_LOCK= new ReentrantLock();
    protected ThreadLocal loadFromCache = new ThreadLocal();

    protected ClusterLockManager(CacheCluster cluster) throws Exception {
        this.cluster = cluster;
        this.lockCache= cluster.getCache(EntityCacheName.getCacheName(EntityCacheName.DISTRIBUTED_CACHE_UNLIMITED_NOBACKINGSTORE,
                                    cluster.getClusterName(), "", "Locks"));
    }

    public ClusterLockManager(NamedCache cache) {
        this.lockCache = cache;
    }

    public ClusterLockManager() {
    }

    public ConcurrentMap<Object, LockRecorder.DoubleLevelLockRecord> getLockRecords() {
        return null;
    }

    /**
     * @param key
     * @param timeout
     * @param lockLevel Ignored parameter!
     * @return
     */
    public boolean lock(Object key, long timeout, LockManager.LockLevel lockLevel) {
        // Get the local lock first
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        if (lock_local(key, timeout)) {
            if (!lock_global(key, timeout)) {
                unlock_local(key);
            } else {
                associate(key);
                return true;
            }
        }
        return false;
    }

    public boolean lock_global(Object key, long timeout) {
        loadFromCache.set(null);
        boolean isLocked=lockCache.lock(key, timeout);
        if (isLocked) {
            long[] IDS= (long[]) lockCache.remove(key);
            if (IDS != null) {
                loadFromCache.set(IDS);
            }
        }
        return isLocked;
    }

    private boolean lock_local(Object key, long timeout) {
        try {
            Object lock= null;
            boolean first_time=true;
            while (true) {
                synchronized(local_locks) {
                    lock = local_locks.get(key);
                    if (lock == null) {
                        local_locks.put(key, new Object());
                        // associate_local(key);
//                        System.out.println("### LOCAL LOCK " + key + " ACQUIRED");
                        return true;
                    }
                }
                if (lock != null) {
                    if (!first_time) {
                        if (timeout > 0) {
                            return false;
                        }
                    } else {
                        first_time=false;
                    }
                    synchronized(lock) {
                        if (timeout >= 0)
                            lock.wait(timeout);
                        else {
//                            System.out.println("### WAITING FOR LOCAL LOCK " + key);
                            lock.wait();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private void unlock_local(Object key)  {
        try {
            Object lock= null;
            synchronized(local_locks) {
                lock = local_locks.remove(key);
            }
            if (lock != null) {
                // remove_local(key);
                synchronized(lock) {
                    lock.notifyAll();
                }
            }
        } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
   }

    private void unlock_cluster(Object key, long[] IDS) {
        if(IDS != null){
            lockCache.put(key, IDS);
        }

        lockCache.unlock(key);
        // remove_cluster(key);
    }

    /**
     *
     * @param key
     */
    public void unlock(Object key) {
//        lockCache.unlock(key);
//        remove(key);
        unlock_cluster(key, null);
        unlock_local(key);
        ArrayList locks= (ArrayList) currentClusterLocks.get();
        if (locks != null) {
            locks.remove(key);
        }
    }

    private void associate(Object key) {
        ArrayList locks= (ArrayList) currentClusterLocks.get();
        if (locks == null) {
            locks = new ArrayList();
            currentClusterLocks.set(locks);
        }
        locks.add(key);
    }

    //--------------

    public void init(ServiceLocator serviceLocator) throws Exception {
        this.cluster = serviceLocator.locateCacheCluster();
        this.lockCache= cluster.getCache(
                        EntityCacheName.getCacheName(EntityCacheName.DISTRIBUTED_CACHE_UNLIMITED_NOBACKINGSTORE,
                        cluster.getClusterName(), "", "Locks"));
    }

    public void discard() {
    }

    private static long[] convert(Collection<Long> affectedIds){
        if(affectedIds == null || affectedIds.isEmpty()){
            return null;
        }

        long[] array = new long[affectedIds.size()];

        int i = 0;
        for (Long affectedId : affectedIds) {
            array[i] = affectedId;
            i++;
        }

        return array;
    }

    private void handleUnlock(Object key, long[] IDS){
//        lockCache.unlock(key);
//        remove(key);
        unlock_cluster(key, IDS);
        unlock_local(key);

        ArrayList locks= (ArrayList) currentClusterLocks.get();
        if (locks != null) {
            locks.remove(key);
        }
    }

    public void unlock(Object key, Collection<Long> affectedIds) {
        long[] IDS = convert(affectedIds);

        handleUnlock(key, IDS);
    }

    public void unlock(Collection<Object> keys) {
         unlock(keys, null);
    }

    public void unlock(Collection<Object> keys, Collection<Long> affectedIds) {
        long[] IDS = convert(affectedIds);

        for (Object key : keys) {
            handleUnlock(key, IDS);
        }
    }

    public void unlockAllLocksHeldByThread() {
        unlockAllLocksHeldByThread(null);
    }

    public void unlockAllLocksHeldByThread(Collection<Long> affectedIds) {
        Collection<Object> locks= (Collection<Object>) currentClusterLocks.get();
        if (locks != null) {
            unlock(locks, affectedIds);
            locks.clear();
        }
    }

    public Collection<Object> getLockDataStuckToThread() {
        return (Collection<Object>) currentClusterLocks.get();
    }

    public Collection<Object> takeLockDataStuckToThread() {
        Collection<Object> keys = (Collection<Object>) currentClusterLocks.get();

        currentClusterLocks.remove();

        return keys;
    }

    public long[] takeIdsReceivedByThreadAfterLock() {
        long[] array = (long[]) loadFromCache.get();
        
        loadFromCache.remove();

        return array;
    }
}
