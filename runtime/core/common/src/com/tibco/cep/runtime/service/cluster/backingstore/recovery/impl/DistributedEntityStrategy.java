package com.tibco.cep.runtime.service.cluster.backingstore.recovery.impl;

import com.tibco.cep.impl.common.resource.UID;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.cluster.backingstore.recovery.ClusterCacheRecoveryManager;
import com.tibco.cep.runtime.service.cluster.backingstore.recovery.EntityRecovery;
import com.tibco.cep.runtime.service.cluster.backingstore.recovery.RecoveryStatus;
import com.tibco.cep.runtime.service.cluster.util.WorkManager;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.EntityDao;

public class DistributedEntityStrategy implements EntityRecovery {
    
    private Cluster cluster;
    private WorkManager recoveryPool;
    private ClusterCacheRecoveryManager recoveryManager;
    private Logger logger;

    //private ControlDao<String, Object> loadDao;
    //private ControlDao<Integer, Object> totalsDao;

    public DistributedEntityStrategy(Cluster cacheCluster,
            WorkManager recoveryPool,
            ClusterCacheRecoveryManager recoveryManager,
            ControlDao<String, Object> loadDao,
            ControlDao<Integer, Object> totalsDao) {
        this.cluster = cacheCluster;
        this.recoveryPool = recoveryPool;
        this.recoveryManager = recoveryManager;
        //this.loadDao = loadDao;
        //this.totalsDao = totalsDao;

        this.logger = cluster.getRuleServiceProvider().getLogger(this.getClass());
	}
	
    public boolean recover() throws Exception {
        boolean scheduled = false;
        for (EntityDao cache : cluster.getDaoProvider().getAllEntityDao()) {
            if (cache.getConfig().hasBackingStore()) {
                boolean loadThis = cache.getConfig().isLoadOnStartup();
                if (loadThis) {
                    scheduled = true;
                    schedule_preLoad(cache);
                    //this.logger.log(Level.DEBUG, "####### Scheduled Pre-load for %s , max-rows=%s", cache.getEntityClass(), cache.getLoadFetchSize());
                }
            }
        }
        return scheduled;
    }

    public boolean schedule_preLoad(EntityDao cache) throws Exception {
        if (recoveryManager.toStartPreload(cache)) {
            recoveryPool.submitJob(new PreLoadCacheAgent(cache));
            return true;
        }
        return false;
    }
    
    /*private synchronized boolean toStartPreload(ClusterEntityProvider cache) {
        String typeName=cache.getEntityClass().getName();
        try {
            lockLoadTable();
            Object isLoaded = loadDao.get(typeName);
            if (isLoaded != null && isLoaded instanceof Integer) {
                if (((Integer) isLoaded).intValue() == RecoveryStatus.SCHEDULE_FOR_LOADING.intValue()) {
                    return true;
                }
            }
            return false;
        } finally {
            unlockLoadTable();
        }
    }*/

    /*private void lockLoadTable() {
        loadDao.lock(ClusterCacheRecoveryManager.RECOVERY_LOCK_KEY, -1);
    }
    
    private void unlockLoadTable() {
        loadDao.unlock(ClusterCacheRecoveryManager.RECOVERY_LOCK_KEY);
    }*/
    
    public class PreLoadCacheAgent implements Runnable {
        EntityDao cache;

        PreLoadCacheAgent(EntityDao cache) {
            this.cache = cache;
        }

        public void run() {
            try {
                long total = load(cache);
            } catch (IllegalStateException isex) {
                // If error is 'SafeNamedCache was explicitly released' likely shutdown in progress.
                logger.log(Level.ERROR, "Exception during preload: " + isex.getMessage());
            } catch (Exception e) {
                logger.log(Level.ERROR, "Error while Pre-Loading class " +
                        cache.getEntityClass().getName());
                e.printStackTrace();
            } finally {
            	recoveryManager.markRecoveryComplete();
            }
        }
    }

    protected long load(EntityDao cache) throws Exception {
        long entityCount = 0;
        int typeId = cluster.getMetadataCache().getTypeId(cache.getEntityClass());
        String typeName = cache.getEntityClass().getName();
        long maxRows = cache.getConfig().getLoadFetchSize();
        boolean toLoad = recoveryManager.acquireTypeForPreLoad(typeId, typeName);
        if (toLoad) {
            boolean loadHandles = cache.getConfig().isRecoverOnStartup();
            boolean loadEntities = cache.getConfig().isLoadOnStartup();
            this.logger.log(Level.INFO, "####### Pre-loading %s, max-rows=%s, load-handles=%s", typeName, maxRows, loadHandles);
            entityCount = _load(typeId, maxRows, loadHandles, loadEntities);
            RecoveryStatus loadStatus = maxRows == 0 ? RecoveryStatus.LOADING_DONE : RecoveryStatus.PARTIAL_LOADING_DONE;
        	if (loadHandles){
        		int totalRecoreved = recoveryManager.incrementTotalRecovered(entityCount);
        		recoveryManager.markRecoveryStatus(typeName, loadStatus);
        		logger.log(Level.DEBUG, "Total object-table types recovered (%s), total=%s", typeName, totalRecoreved);
        	}
            if (loadEntities) {
                int totalLoaded = recoveryManager.incrementTotalLoaded(entityCount);
                recoveryManager.markPreloadStatus(typeName, loadStatus);
                logger.log(Level.DEBUG, "Total types preloaded (%s), total=%s", typeName, totalLoaded);
            }
        }
        return entityCount;
    }

    /*private synchronized boolean acquireTypeForPreLoad(int typeId, String typeName) {
        boolean toLoad = false;
        try {
            lockLoadTable();
            Object retVal = loadDao.get(typeName);
            if (retVal == null || (retVal instanceof Integer && ((Integer) retVal).intValue() == RecoveryStatus.SCHEDULE_FOR_LOADING.intValue())) {
                loadDao.put(typeName, cluster.getLocalMemberUID());
                loadDao.put(ClusterCacheRecoveryManager.PRELOAD_SMRY+typeName, cluster.getRuleServiceProvider().getName() + ": Started preload");
                toLoad = true;
            }
        } catch (Exception e) {
        	return false;
        } finally {
            unlockLoadTable();
        }
        return toLoad;
    }*/

    private long _load(int typeId, long maxRows, boolean loadHandles, boolean loadEntities) throws Exception{
        BackingStore store = cluster.getRecoveryBackingStore();
        if (store != null) {
            long loaded = store.loadObjects(typeId, maxRows, loadHandles, loadEntities);
            return loaded;
        } else {
            throw new RuntimeException("Unable to instantiate backing store for entityProvider=" +
                    cluster.getMetadataCache().getClass(typeId).getName());
        }
    }
    
    public boolean resetLoadTable(UID memberLeftId) {
    	return recoveryManager.resetLoadTableEntryOwner(memberLeftId);
        /*try {
            boolean isReset = false;
            lockLoadTable();
            Iterator<String> i = loadDao.keySet().iterator();
            while (i.hasNext()) {
                String typeName = i.next();
                Object memberId = loadDao.get(typeName);
                if (memberId != null && memberId instanceof UID) {
                    if (memberLeftId.equals(memberId)) {
                        loadDao.put(typeName, RecoveryStatus.SCHEDULE_FOR_LOADING.intValue());
                        loadDao.put(ClusterCacheRecoveryManager.PRELOAD_SMRY+typeName, cluster.getRuleServiceProvider().getName() + ": Reset for preload");
                        logger.log(Level.INFO, "## Reset preload for type %s", typeName);
                        isReset = true;
                    }
                }
            }
            return isReset;
        } finally {
            unlockLoadTable();
        }*/
    }
}
