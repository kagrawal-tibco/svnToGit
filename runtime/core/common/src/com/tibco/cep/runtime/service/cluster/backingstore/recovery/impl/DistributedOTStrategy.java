package com.tibco.cep.runtime.service.cluster.backingstore.recovery.impl;

import com.tibco.cep.impl.common.resource.UID;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.cluster.backingstore.recovery.ClusterCacheRecoveryManager;
import com.tibco.cep.runtime.service.cluster.backingstore.recovery.ObjectTableRecovery;
import com.tibco.cep.runtime.service.cluster.backingstore.recovery.RecoveryStatus;
import com.tibco.cep.runtime.service.cluster.util.WorkManager;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.EntityDao;

public class DistributedOTStrategy implements ObjectTableRecovery {

    private Cluster cluster;
    private WorkManager recoveryPool;
    private ClusterCacheRecoveryManager recoveryManager;
    private Logger logger;
    //private ControlDao<String, Object> recoveryDao;
    //private ControlDao<Integer, Object> totalsDao;

    public DistributedOTStrategy(Cluster cacheCluster,
            WorkManager recoveryPool,
            ClusterCacheRecoveryManager recoveryManager,
            ControlDao<String, Object> recoveryDao,
            ControlDao<Integer, Object> totalsDao) {
        this.cluster = cacheCluster;
        this.recoveryPool = recoveryPool;
        this.recoveryManager = recoveryManager;
        //this.recoveryDao = recoveryDao;
        //this.totalsDao = totalsDao;

        this.logger = cacheCluster.getRuleServiceProvider().getLogger(this.getClass());
    }

    public boolean recover() throws Exception {
        boolean scheduled = false;
        for (EntityDao cache : cluster.getDaoProvider().getAllEntityDao()) {
            if (cache.getConfig().hasBackingStore()) {
                boolean loadThis = cache.getConfig().isRecoverOnStartup();
                if (loadThis) {
                    scheduled = true;
                    if(!cache.getConfig().isLoadOnStartup()) {
                        scheduled = true;
                        schedule_recover(cluster.getMetadataCache().getTypeId(cache.getEntityClass()), cache.getEntityClass().getName(), cache.getConfig().getLoadFetchSize());
                    } // else handles will be loaded with the entity
                }
            }
        }
        return scheduled;
	}
	
    private boolean schedule_recover(int typeId, String typeName, long maxRows) throws Exception {
        if (recoveryManager.toStartRecovery(typeName)) {
            logger.log(Level.DEBUG, "####### Schedule recovery for type %s with fetch-size=%d", typeName, maxRows);
            recoveryPool.submitJob(new ObjectTableRecoverAgent(typeId, maxRows, logger));
            return true;
        }
        return false;
    }
	
    /*private synchronized boolean toStartRecovery(String typeName) {
        try {
            recoveryLock();
            Object isRecovered = recoveryDao.get(typeName);
            if (isRecovered != null && isRecovered instanceof Integer) {
                if (((Integer) isRecovered).intValue() == RecoveryStatus.SCHEDULE_FOR_LOADING.intValue()) {
                    return true;
                }
            }
            return false;
        } finally {
            recoveryUnlock();
        }
    }*/

	public class ObjectTableRecoverAgent implements Runnable {
        int typeId;
        long maxRows;
        Logger logger;

        ObjectTableRecoverAgent(int typeId, long maxRows, Logger logger) {
            this.typeId = typeId;
            this.maxRows = maxRows;
            this.logger = logger;
        }

        public void run() {
            try {
                long total = recover(typeId, maxRows);
            } catch (IllegalStateException isex) {
                // If error is 'SafeNamedCache was explicitly released' likely shutdown in progress.
                logger.log(Level.ERROR, "Exception during recovery: " + isex.getMessage());
            } catch (Exception e) {
                logger.log(Level.ERROR, "Error while object table recovery " +
                        cluster.getMetadataCache().getClass(typeId).getName());
                e.printStackTrace();
            } finally {
            	recoveryManager.markRecoveryComplete();
            }
        }
    }
    
    protected long recover(int typeId, long maxRows) throws Exception {
        long entityCount = 0;
        String typeName = cluster.getMetadataCache().getClass(typeId).getName();
        boolean toLoad = recoveryManager.acquireTypeForRecovery(typeId, typeName);
        if (toLoad) {
        	logger.log(Level.DEBUG, "##### Recovering ObjectTable type %s", typeName);
            entityCount = _recover(typeId, maxRows);
            int total = recoveryManager.incrementTotalRecovered(entityCount);
            recoveryManager.markRecoveryStatus(typeName, RecoveryStatus.LOADING_DONE);
            logger.log(Level.DEBUG, "Total ObjectTable tuples recovered (" + typeName + "), total="+ total);
        }
        return entityCount;
    }
    
    /*public synchronized boolean acquireTypeForRecovery(int typeId,
            String typeName) {
        boolean toLoad = false;
        try {
            recoveryLock();
            // logger.log(Level.DEBUG, "Acquired recovery lock: " + typeId);
            Object retVal = recoveryDao.get(typeName);
            if (retVal == null
                    || retVal instanceof Integer
                    && ((Integer) retVal).intValue() == RecoveryStatus.SCHEDULE_FOR_LOADING
                            .intValue()) {
                recoveryDao.put(typeName, cluster.getLocalMemberUID());
                recoveryDao.put(ClusterCacheRecoveryManager.RECOVERY_SMRY + typeName, cluster.getRuleServiceProvider()
                        .getName()
                        + ": Started recovery");
                toLoad = true;
            }
        } catch(Exception e) {
            return false;
        } finally {
            // logger.log(Level.DEBUG, "Released recovery lock: " + typeId);
            recoveryUnlock();
        }
        return toLoad;
    }*/

    /*private void recoveryUnlock() {
        recoveryDao.unlock(ClusterCacheRecoveryManager.RECOVERY_LOCK_KEY);
    }

    private void recoveryLock() {
        recoveryDao.lock(ClusterCacheRecoveryManager.RECOVERY_LOCK_KEY, -1);
    }*/

    private long _recover(int typeId, long maxRows) throws Exception {
        BackingStore store = (BackingStore) cluster.getRecoveryBackingStore();
        if (store != null) {
            long l = store.recoverObjectTable(typeId, maxRows);
            return l;
        } else {
            throw new RuntimeException("Unable to instantiate backing store for entityProvider=" + 
                    cluster.getMetadataCache().getClass(typeId).getName());
        }
    }

    public boolean resetRecoveryTable(UID memberLeftId) {
    	return recoveryManager.resetRecoveryTableEntryOwner(memberLeftId);
        /*try {
            boolean isReset = false;
            recoveryLock();
            Iterator<String> i = recoveryDao.keySet().iterator();
            while (i.hasNext()) {
                String typeName = i.next();
                Object memberId = recoveryDao.get(typeName);
                if (memberId != null && memberId instanceof UID) {
                    if (memberLeftId.equals(memberId)) {
                        recoveryDao.put(typeName, RecoveryStatus.SCHEDULE_FOR_LOADING.intValue());
                        recoveryDao.put(ClusterCacheRecoveryManager.RECOVERY_SMRY+typeName, cluster.getRuleServiceProvider().getName() + ": Reset for recovery");
                        logger.log(Level.INFO, "## Reset recovery for type %s", typeName);
                        isReset = true;
                    }
                }
            }
            return isReset;
        } finally {
            recoveryUnlock();
        }*/
    }
}
