/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.backingstore.recovery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.impl.common.resource.UID;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.ClusterConfiguration;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.cluster.backingstore.RecoveryManager;
import com.tibco.cep.runtime.service.cluster.backingstore.SetWriteMode;
import com.tibco.cep.runtime.service.cluster.backingstore.recovery.impl.DistributedBatchStrategy;
import com.tibco.cep.runtime.service.cluster.backingstore.recovery.impl.DistributedEntityParallelStrategy;
import com.tibco.cep.runtime.service.cluster.backingstore.recovery.impl.DistributedEntityStrategy;
import com.tibco.cep.runtime.service.cluster.backingstore.recovery.impl.DistributedOTStrategy;
import com.tibco.cep.runtime.service.cluster.system.EntityIdMask;
import com.tibco.cep.runtime.service.cluster.util.WorkManagerFactory;
import com.tibco.cep.runtime.service.cluster.util.WorkManagerImpl;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.EntityDaoConfig;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * Created by IntelliJ IDEA.
 * User: fildiz
 * Date: Nov 17, 2008
 * Time: 2:26:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClusterCacheRecoveryManager implements RecoveryManager {
	
	boolean isInitialized = false;
	boolean isCompleted = false;
	
	Cluster cluster;
    ControlDao<String, Object> recoveryDao;
    ControlDao<String, Object> loadDao;
    ControlDao<Integer, Object> totalsDao;

    WorkManagerImpl recoveryPool;

    public final static String PRELOAD_SMRY         = "PRELOAD_SMRY:"; 
    public final static String RECOVERY_SMRY        = "RECOVERY_SMRY:";
    public final static String PRELOAD_MANAGER 	    = new String("PRELOAD_MANAGER");

    private final static Integer TOTALS_RECOVERY_KEY  = new Integer(6);
    private final static Integer ENTITIES_RECOVERY_KEY= new Integer(7);
    private final static Integer TOTALS_PRELOAD_KEY   = new Integer(8);
    private final static Integer ENTITIES_PRELOAD_KEY = new Integer(9);

    public static final String GENERIC_TYPE_NAME     = "GENERIC_TYPE_CLASS";

    public static final String  RECOVERY_LOCK_KEY    = new String("-1");
    public static final String  PRE_RECOVERY_LOCK_KEY= new String("-2");
    public static final Integer TOTALS_LOCK_KEY      = new Integer(-1);

    private boolean hasLoadAndRecoveryCompleted = false;
    private long timeLoadAndRecoveryStarted = 0L;

    private Logger logger;
    
    public boolean preRecovered = false;
    public boolean allRecovered = false;

    EntityRecovery entityRecovery;
    ObjectTableRecovery otRecovery;

    public ClusterCacheRecoveryManager() {

    }

    public void init(Cluster cacheCluster) throws Exception {
        this.cluster = cacheCluster;
        this.logger = cluster.getRuleServiceProvider().getLogger(ClusterCacheRecoveryManager.class);

        /*
         * Handle mis-configured Recovery, when not needed like with SharedNothing
         */
        String databaseType = System.getProperty(SystemProperty.BACKING_STORE_DB_TYPE.getPropertyName(), "");
        String sharedNothingStoreClassName = "com.tibco.cep.runtime.service.dao.impl.tibas.backingstore.BEDataGridSharedNothingStore";
        Class sharedNothingStoreClass = Class.forName(sharedNothingStoreClassName);

        if (databaseType.equalsIgnoreCase("Berkeley DB")) {
            this.logger.log(Level.INFO, "Backing Store [Berkeley DB] is configured for the cluster [ %s ] ", this.cluster.getClusterName());
            return;
        }

        if (!this.cluster.getClusterConfig().isHasBackingStore() && cluster.getBackingStore() == null) {
            this.logger.log(Level.INFO, "Backing Store is not configured for the cluster [ %s ]", this.cluster.getClusterName());
            return;
        }

        if (!this.cluster.getClusterConfig().isHasBackingStore() && sharedNothingStoreClass.isAssignableFrom(cluster.getBackingStore().getClass())) {
            this.logger.log(Level.INFO, "Backing Store [Shared Nothing] is configured for the cluster [ %s ]", this.cluster.getClusterName());
            return;
        }

        if (this.cluster.getRecoveryBackingStore() == null) {
            this.logger.log(Level.WARN, "Backing Store is not initialized for the cluster  [ %s ]", this.cluster.getClusterName());
            return;
        }
        
        /*
         * Ready for recovery process, and create spaces
         */
        recoveryDao = cluster.getDaoProvider().createControlDao(String.class, Object.class, ControlDaoType.RecoveryTable);

        loadDao = cluster.getDaoProvider().createControlDao(String.class, Object.class, ControlDaoType.LoadTable);

        totalsDao = cluster.getDaoProvider().createControlDao(Integer.class, Object.class, ControlDaoType.TotalsTable);

        /*
         * Check if recovery is already done, nothing more to do then
         */
        if (isLoadAndRecoveryComplete() == true) {
            this.logger.log(Level.INFO, "Backing Store recovery has already completed for the cluster [ %s ]", this.cluster.getClusterName());
            SetWriteMode.setWriteMode(true);
            return;
        }

        Properties props = cluster.getRuleServiceProvider().getProperties();
        String strategy = props.getProperty(SystemProperty.DATAGRID_RECOVERY_DISTRIBUTED_STRATEGY.getPropertyName(), "batch");
        int numThreads = Integer.parseInt(props.getProperty(SystemProperty.DATAGRID_RECOVERY_THREADS.getPropertyName(), "4"));
        if ("batch".equals(strategy) || "partitioned".equals(strategy)) {
        	if (numThreads < 4) {
        		logger.log(Level.DEBUG, "Recovery strategy batched|partitioned needs atleast 4 threads to succeed");
        		numThreads = 4;
        	}
        }
        recoveryPool = (WorkManagerImpl) WorkManagerFactory.create(cluster.getClusterName(), null, null, getClass().getSimpleName(),
                numThreads, numThreads * 8, 1024, cluster.getRuleServiceProvider());
        recoveryPool.allowCoreThreadTimeOut(true);
        recoveryPool.start();
		
        /*
         * Decide on which distributed strategy to use
         */
		if ("partitioned".equals(strategy)) {
			//TODO: Not-implemented - entityRecovery = new PartitionedEntityStrategy(cluster, recoveryPool, this, loadDao, totalsDao);
            entityRecovery = new DistributedBatchStrategy(cluster, recoveryPool, this, loadDao, totalsDao); 
		} else if ("parallel".equals(strategy)) {
			entityRecovery = new DistributedEntityParallelStrategy(cluster, recoveryPool, this, loadDao, totalsDao);
        } else if ("entity".equals(strategy)) {
			entityRecovery = new DistributedEntityStrategy(cluster, recoveryPool, this, loadDao, totalsDao);
		} else { // Default is batch
		    entityRecovery = new DistributedBatchStrategy(cluster, recoveryPool, this, loadDao, totalsDao); 
		}
		otRecovery = new DistributedOTStrategy(cluster, recoveryPool, this, recoveryDao, totalsDao);
        timeLoadAndRecoveryStarted = System.currentTimeMillis();
        isInitialized = true;
        logger.log(Level.INFO, "Cluster Cache Recovery Manager initialized with strategy=%s threads=%s", strategy, numThreads);
    }

    public void start() throws Exception {
        if (isInitialized == false) {
            // Not configured for Recovery - Ignore!
            return;
        }

        boolean scheduled = false;
        boolean otRecovered = otRecovery.recover();
        boolean entityRecovered = entityRecovery.recover();
        scheduled = otRecovered || entityRecovered;
        
        // Perform some one time tasks
        preRecovery();
        
        if (!isCompleted) {
            if (!scheduled /* or cluster.getClusterConfig().asyncRecovery() */) {
                // Let the cluster proceed...
                this.logger.log(Level.INFO, "Cluster Recovered");
                //CacheFlush.flushCache(cluster);
                SetWriteMode.setWriteMode(true);
                //cluster.getMasterCache().transitionTo(Cluster.CLUSTER_RECOVERED);
                isCompleted = true;
            } else if (scheduled) {
                if (markRecoveryComplete()) {
                    isCompleted = true;
                }
            }
        }

        // TODO: Memberships
        /**
        if (this.cluster.getClusterConfig().isStorageEnabled() == false) {
            ControlDao.ChangeListener cacheChangeListener = new CacheChangeListener();
            recoveryDao.registerListener(cacheChangeListener);
            loadDao.registerListener(cacheChangeListener);
            GroupMemberServiceListener memberListener = new MemberServiceListener();
            cluster.getGroupMembershipService().addGroupMemberServiceListener(memberListener);
        }
         */

        logger.log(Level.INFO, "## Waiting for %s cluster recovery to complete", this.cluster.getClusterName());
        waitForRecovery();
        logger.log(Level.INFO, "## %s cluster recovery has completed", this.cluster.getClusterName());
    }
    
    // Only to be performed by one node
    private void preRecovery() throws Exception {
        boolean acquired = false;
        try {
            this.logger.log(Level.DEBUG, "### Waiting for pre-recovery to complete..");
            acquired = recoveryDao.lock(PRE_RECOVERY_LOCK_KEY, -1);
            Boolean b = (Boolean) recoveryDao.get(PRE_RECOVERY_LOCK_KEY);
            preRecovered = (b != null && b.booleanValue());
            
            if (preRecovered) {
                return;
            }

	        this.logger.log(Level.DEBUG, "### Pre-recovery started..");
	        BackingStore recoveryStore = cluster.getRecoveryBackingStore();
	        
	        boolean truncateOnRestart = ((BEProperties) cluster.getRuleServiceProvider().getProperties()).getBoolean(SystemProperty.CLUSTER_TRUNCATE_ON_RESTART.getPropertyName(), false);
	        if (truncateOnRestart) {
	            this.logger.log(Level.WARN, "Cluster is configured to truncate on restart, all data will be lost...");
	            recoveryStore.clear();
	        }
	
	        boolean migrate = ((BEProperties) cluster.getRuleServiceProvider().getProperties()).getBoolean(SystemProperty.CLUSTER_MIGRATE_OBJECTTABLE.getPropertyName(), false);
	        if (migrate) {
            this.logger.log(Level.INFO, "Pre-recovery Phase 1: Migrating ObjectTable...");
	            for (EntityDao entity : cluster.getDaoProvider().getAllEntityDao()) {
	                int typeId = cluster.getMetadataCache().getTypeId(entity.getEntityClass());
	                EntityDaoConfig entityConfig = this.cluster.getMetadataCache().getEntityDaoConfig(entity.getEntityClass());
	                if (entityConfig.hasBackingStore()) {
                        this.logger.log(Level.DEBUG, "Migrating ObjectTable for %s [ %s ]", entity.getEntityClass().getName(), typeId);
	                    recoveryStore.migrate(typeId);
	                }
	            }
	            this.logger.log(Level.INFO, "Pre-recovery Phase 1: Migration Done.");
	        }
	        else {
	            this.logger.log(Level.INFO, "Pre-recovery Phase 1: Property %s is false. Migration Skipped.", SystemProperty.CLUSTER_MIGRATE_OBJECTTABLE.getPropertyName());
	        }
	
	        boolean isCacheAside = ((BEProperties) cluster.getRuleServiceProvider().getProperties()).getBoolean(SystemProperty.CLUSTER_IS_CACHE_ASIDE.getPropertyName(), false);
	        boolean keepDeleted = ((BEProperties) cluster.getRuleServiceProvider().getProperties()).getBoolean(SystemProperty.CLUSTER_KEEP_DELETED.getPropertyName(), true);
	        boolean performCleanup = ((BEProperties) cluster.getRuleServiceProvider().getProperties()).getBoolean(SystemProperty.CLUSTER_CLEANUP.getPropertyName(), !keepDeleted);
	        if (!isCacheAside && performCleanup) {
	            this.logger.log(Level.INFO, "Pre-recovery Phase 2: Cleanup...");
	            for (EntityDao entity : cluster.getDaoProvider().getAllEntityDao()) {
	                int typeId = cluster.getMetadataCache().getTypeId(entity.getEntityClass());
	                EntityDaoConfig entityConfig = this.cluster.getMetadataCache().getEntityDaoConfig(entity.getEntityClass());
	                if (entityConfig.hasBackingStore()) {
	                    recoveryStore.cleanup(typeId);
	                }
	            }
	            recoveryStore.cleanup();
	            this.logger.log(Level.INFO, "Pre-recovery Phase 2: Cleanup Done.");
	        }
	        else {
	            this.logger.log(Level.INFO, "Pre-recovery Phase 2: Property %s is false. Cleanup Skipped.", SystemProperty.CLUSTER_CLEANUP.getPropertyName());
	        }
	
	        // Get the minimum-entity id for entity generator
	        this.logger.log(Level.INFO, "Pre-recovery Phase 3: Querying minEntityId...");
	        long minEntityId = recoveryStore.getMaxEntityId();
	        ClusterConfiguration clusterConfig = cluster.getClusterConfig(); 
	        if (!clusterConfig.isMultiSite()) {
	            // If "multi-site" inferred from existing data in the database, exit.
	            long siteId = EntityIdMask.getMaskedId(minEntityId);
	            if (siteId > 0) {
	                throw new Exception ("Multi-site configuration inconsistency detected: Database data is multi-site enabled (siteId=" + siteId + ") but this instance is started with multi-site disabled");
	            }
	        } else {
	            //strip off the site-id
	            minEntityId = EntityIdMask.getEntityId(minEntityId);
	        }
	        this.cluster.getRuleServiceProvider().getIdGenerator().setMinEntityId(minEntityId);
	        this.logger.log(Level.INFO, "Pre-recovery Phase 3: Setting minEntityId = " + minEntityId);
	
	        // Disable writes until recovery is complete
	        SetWriteMode.setWriteMode(false);
            preRecovered = true;
        } catch (Exception e) {
            this.logger.log(Level.WARN, "## %s Pre-recovery process failed.", e, this.cluster.getClusterName());
        } finally {
            if (acquired) {
                recoveryDao.put(PRE_RECOVERY_LOCK_KEY, true);
                recoveryDao.unlock(PRE_RECOVERY_LOCK_KEY);
            }
        }
    }

    @SuppressWarnings("unchecked")
	public java.util.Iterator getObjects(int typeId) throws Exception {
        BackingStore store = cluster.getRecoveryBackingStore();
        return store.getObjects(typeId);
    }

    public void shutdown() {
        recoveryPool.shutdown();
        //TODO: Decide if these needs to be discharged
        //loadDao.discard();
        //recoveryDao.discard();
        //totalsDao.discard();
    }

    public synchronized boolean markRecoveryComplete() {
        try {
            if (!hasLoadAndRecoveryCompleted && isLoadComplete() && isRecoveryComplete()) {
                // Sleep 10 seconds to avoid confusing log entries 
                // (no table recovery happens literally in milliseconds, logs get out of order) 
                try { Thread.sleep(10000); } catch (Exception e) { }
                
                if (logger.isEnabledFor(Level.DEBUG)) {
                    printPreloadStatus();
                	printRecoveryStatus();
                }
                logger.log(Level.INFO,
                        "Total Preloaded types=" + ((totalsDao.get(TOTALS_PRELOAD_KEY) == null) ? 0:totalsDao.get(TOTALS_PRELOAD_KEY)) +
                        " instances=" + ((totalsDao.get(ENTITIES_PRELOAD_KEY) == null) ? 0:totalsDao.get(ENTITIES_PRELOAD_KEY)));
                logger.log(Level.INFO,
                        "Total Recovered types=" + ((totalsDao.get(TOTALS_RECOVERY_KEY) == null) ? 0:totalsDao.get(TOTALS_RECOVERY_KEY)) +
                        " instances=" + ((totalsDao.get(ENTITIES_RECOVERY_KEY) == null) ? 0: totalsDao.get(ENTITIES_RECOVERY_KEY)));

                // Enable writes (recovery complete)
                //CacheFlush.flushCache(cluster);
                SetWriteMode.setWriteMode(true);
                //cluster.getMasterCache().transitionTo(CacheCluster.CLUSTER_RECOVERED);
                //cluster.getMasterCache().setClusterRecoveryState(true);
                //TODO: If needed - cluster.getGroupMembershipService().transitionTo(GroupMembershipServiceImpl.State.RECOVERED);
                hasLoadAndRecoveryCompleted = true;
                long total = (System.currentTimeMillis() - timeLoadAndRecoveryStarted);
                String totalStr = (total/3600000)+"h"+((total/60000)%60)+"m"+((total/1000)%60)+"s ";
                logger.log(Level.INFO, "####### Cluster Preload/Recovery completed in %s", totalStr);
                shutdown();
                return true;
            }
            return false;
        } catch (IllegalStateException isex) {
            // If error is 'SafeNamedCache was explicitly released' likely shutdown in progress.
            logger.log(Level.ERROR, "Exception during preload/recovery: " + isex.getMessage());
            return false;
        } catch (Exception e) {
            logger.log(Level.ERROR, "Error while marking recovery completed", e);
            e.printStackTrace();
            return false;
        }
    }
    
    public synchronized boolean toStartRecovery(String typeName) {
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
    }
    
    public synchronized boolean acquireTypeForRecovery(int typeId,
			String typeName) {
		boolean toLoad = false;
		try {
			recoveryLock();
			Object retVal = recoveryDao.get(typeName);
			if (retVal == null || retVal instanceof Integer &&
			        ((Integer) retVal).intValue() == RecoveryStatus.SCHEDULE_FOR_LOADING.intValue()) {
				recoveryDao.put(typeName, cluster.getGroupMembershipService().getLocalMember().getMemberId());
				recoveryDao.put(ClusterCacheRecoveryManager.RECOVERY_SMRY+typeName, 
				        cluster.getRuleServiceProvider().getName() + ": Started recovery");
				toLoad = true;
			}
		} finally {
			recoveryUnlock();
		}
		return toLoad;
	}
    
    public synchronized boolean resetLoadTableEntryOwner(UID memberLeftId) {
        try {
            boolean isReset = false;
            lockLoadTable();
            Iterator<String> itr = loadDao.keySet().iterator();
            while (itr.hasNext()) {
                String typeName = itr.next();
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
        }
    }

    public boolean resetLoadTable(UID memberLeftId) {
    	if (entityRecovery != null) {
        	return entityRecovery.resetLoadTable(memberLeftId);
    	}
    	return true;
    }
    
    private void lockLoadTable() {
        loadDao.lock(ClusterCacheRecoveryManager.RECOVERY_LOCK_KEY, -1);
    }
    
    private void unlockLoadTable() {
        loadDao.unlock(ClusterCacheRecoveryManager.RECOVERY_LOCK_KEY);
    }
    
    public void registerTypeForPreload(String typeName) {
    	markPreloadStatus(typeName, RecoveryStatus.SCHEDULE_FOR_LOADING);
    }

    public synchronized int incrementTotalLoaded(long entities) {
        try {
            totalsDao.lock(TOTALS_LOCK_KEY, -1);
            Integer total = (Integer) totalsDao.get(TOTALS_PRELOAD_KEY);
            Long entityTotal = (Long) totalsDao.get(ENTITIES_PRELOAD_KEY);
            if (total == null) {
                total = new Integer(1);
                entityTotal = new Long(entities);
            } else {
                total = new Integer(total.intValue() + 1);
                entityTotal = new Long(entityTotal.longValue() + entities);
            }
            totalsDao.put(TOTALS_PRELOAD_KEY, total);
            totalsDao.put(ENTITIES_PRELOAD_KEY, entityTotal);
            return total.intValue();
        } catch (Exception e) {
            return 0;
        } finally {
        	totalsDao.unlock(TOTALS_LOCK_KEY);
        }
    }

    @SuppressWarnings("unchecked")
    public String[] getPreloadStatus() {
        try {
        	lockLoadTable();
            ArrayList result = new ArrayList();
            Iterator itr = loadDao.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry entry = (Entry) itr.next();
                String key = (String) entry.getKey();
                Object retVal = entry.getValue();
                if (key.startsWith(PRELOAD_SMRY)) {
                	logger.log(Level.DEBUG, "JMX Preload Type=" + key + ", preload status=" + retVal);
                    if (retVal == null) {
                        result.add(key.substring(PRELOAD_SMRY.length()) + " = Not-loaded");
                    } else {
                        result.add(key.substring(PRELOAD_SMRY.length()) + " = " + retVal);
                    }
                }
            }
            Collections.sort(result);
            return (String[]) result.toArray(new String[result.size()]);
        } finally {
        	unlockLoadTable();
        }
    }
    
    private boolean printPreloadStatus() {
        try {
        	lockLoadTable();
            logger.log(Level.DEBUG, "## Preload summary ##");
            Iterator<Map.Entry<String, Object>> itr = loadDao.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry<String, Object> entry = itr.next();
                String key = entry.getKey();
                Object retVal = entry.getValue();
                if (key.startsWith(PRELOAD_SMRY)  || key.startsWith(PRELOAD_MANAGER)) {
                    continue;
                }
                logger.log(Level.DEBUG, "## Preload Type=%s, preload status=%s", key, retVal);
                if (retVal == null) {
                    return false;
                } else if (retVal instanceof UID) {
                    return false;
                } else if (retVal instanceof Integer) {
                	int loadStatus = ((Integer)retVal).intValue();
                	if (loadStatus == RecoveryStatus.SCHEDULE_FOR_LOADING.intValue() 
                		|| loadStatus == RecoveryStatus.LOADING_IN_PROGRESS.intValue()) {
                		return false;
                	}
                }
            }
            return true;
        } finally {
            unlockLoadTable();
        }
    }
    
    public EntityDao getNextEntity() throws Exception {
		logger.log(Level.DEBUG, "Get next type to pre-load");
    	Collection<? extends EntityDao> allCaches = cluster.getDaoProvider().getAllEntityDao();
    	for (EntityDao cache : allCaches) {
            if (cache.getConfig().hasBackingStore()) {
            	boolean loadThis = (cache.getConfig().isLoadOnStartup() || cache.getConfig().isRecoverOnStartup());
                if (loadThis) {
                    int typeId = cluster.getMetadataCache().getTypeId(cache.getEntityClass());
                	if(!isLoaded(typeId)) {
                		return cache;
                	}
                }
            }
    	}
    	return null;
    }
    
    public boolean isLoaded(int typeId) {
        String typeName = cluster.getMetadataCache().getClass(typeId).getName();
        try {
        	lockLoadTable();
        	Object isLoaded = loadDao.get(typeName);
            if (isLoaded != null && isLoaded instanceof Integer) {
                int loadStatus = ((Integer) isLoaded).intValue();
                if (loadStatus == RecoveryStatus.LOADING_DONE.intValue() || loadStatus == RecoveryStatus.PARTIAL_LOADING_DONE.intValue()) {
    				logger.log(Level.TRACE, "" +
    						"#### Type %s already loaded ####", typeName);
                    return true;
                } else if(loadStatus == RecoveryStatus.SCHEDULE_FOR_LOADING.intValue()){
    				logger.log(Level.TRACE, "" +
    						"#### Type %s ready for loading ####", typeName);
                    return false;
                } else if(loadStatus == RecoveryStatus.LOADING_IN_PROGRESS.intValue()){
    				logger.log(Level.TRACE, "" +
    						"#### Type %s already in progress ####", typeName);
                    return false;	
                } else {
                	return false;
                }
            } else {
				logger.log(Level.TRACE, "" +
						"#### Type %s is never loaded ####", typeName);
                return false;
            }
        } finally {
        	unlockLoadTable();
        }
    }

    public synchronized boolean markPreloadStatus(String typeName, RecoveryStatus preloadStatus) {
    	try {
    		lockLoadTable();
   			loadDao.put(typeName, preloadStatus.intValue());
   			loadDao.put(ClusterCacheRecoveryManager.PRELOAD_SMRY+typeName, cluster.getRuleServiceProvider().getName() + " : " + preloadStatus.summary());
    	} finally {
    		unlockLoadTable();
    	}
    	return true;
    }
    
    public synchronized boolean toStartPreload(EntityDao cache) {
        String typeName = cache.getEntityClass().getName();
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
    }
    
    public synchronized boolean acquireTypeForPreLoad(int typeId, String typeName) {
        boolean toLoad = false;
        try {
            lockLoadTable();
            Object retVal = loadDao.get(typeName);
            if (retVal == null || (retVal instanceof Integer && ((Integer) retVal).intValue() == RecoveryStatus.SCHEDULE_FOR_LOADING.intValue())) {
                loadDao.put(typeName, cluster.getGroupMembershipService().getLocalMember().getMemberId());
                loadDao.put(ClusterCacheRecoveryManager.PRELOAD_SMRY+typeName, cluster.getRuleServiceProvider().getName() + ": Started preload");
                toLoad = true;
            }
        } finally {
            unlockLoadTable();
        }
        return toLoad;
    }
    
    public boolean resetRecoveryTable(UID memberLeftId) {
    	if (otRecovery != null) {
    		return otRecovery.resetRecoveryTable(memberLeftId);
    	}
    	return true;
    }
    
    public synchronized boolean resetRecoveryTableEntryOwner(UID memberLeftId) {
        try {
            boolean isReset = false;
            recoveryLock();
            Iterator<String> itr = recoveryDao.keySet().iterator();
            while (itr.hasNext()) {
                String typeName = itr.next();
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
        }
    }
    
	private void recoveryLock() {
		recoveryDao.lock(ClusterCacheRecoveryManager.RECOVERY_LOCK_KEY, -1);
	}
	
    private void recoveryUnlock() {
        recoveryDao.unlock(ClusterCacheRecoveryManager.RECOVERY_LOCK_KEY);
    }

    public void registerTypeForRecovery(String typeName) {
    	markRecoveryStatus(typeName, RecoveryStatus.SCHEDULE_FOR_LOADING);
    }
	
    private void waitForRecovery() {
        while (!hasLoadAndRecoveryCompleted) {
            if (isLoadAndRecoveryComplete()) {
                if (logger.isEnabledFor(Level.DEBUG)) {
                    printRecoveryStatus();
                    printPreloadStatus();
                }
                markRecoveryComplete();
                return;
            }
            try {
                Thread.sleep(2000);
            }
            catch (InterruptedException ie) {
            }
        }
        logger.log(Level.INFO, "## %s cluster recovery is complete", this.cluster.getClusterName());
    }
    
    private synchronized boolean isLoadComplete() {
        try {
        	lockLoadTable();
            boolean anyLoaded = false;
            Iterator<Map.Entry<String, Object>> itr = loadDao.entrySet().iterator();
            while (itr.hasNext()) {
            	Map.Entry<String, Object> entry = itr.next();
                String key = entry.getKey();
                Object retVal = entry.getValue();
                if (key.startsWith(PRELOAD_SMRY)) {
                    continue;
                }
                if (key.startsWith(PRELOAD_MANAGER)) {
                    continue;
                }
                if (retVal == null) {
                    return false;
                } else if (retVal instanceof UID) {
                    return false;
                } else if (retVal instanceof Integer) {
                	int loadStatus = ((Integer)retVal).intValue();
                	if (loadStatus == RecoveryStatus.SCHEDULE_FOR_LOADING.intValue() ||
                		loadStatus == RecoveryStatus.LOADING_IN_PROGRESS.intValue()) {
                		return false;
                	}
                }
                anyLoaded = true;
            }
            return anyLoaded;
        } finally {
        	unlockLoadTable();
        }
    }
	
    private synchronized boolean isRecoveryComplete() {
        try {
            recoveryLock();
            boolean anyRecovered = false;
            Iterator<Map.Entry<String, Object>> itr = recoveryDao.entrySet().iterator();
            while (itr.hasNext()) {
            	Map.Entry<String, Object> entry = itr.next();
                String key = (String) entry.getKey();
                Object retVal = entry.getValue();
                if (key.startsWith(RECOVERY_SMRY)) {
                    continue;
                }
				if (key.equals(RECOVERY_LOCK_KEY)) {
                    continue; // Ignore the recovery LOCK key as well
                }
                if (retVal == null) {
                    return false;
                } else if (retVal instanceof UID) {
                    return false;
                } else if (retVal instanceof Integer) {
                	int loadStatus = ((Integer)retVal).intValue();
                	if (loadStatus == RecoveryStatus.SCHEDULE_FOR_LOADING.intValue() ||
                	    loadStatus == RecoveryStatus.LOADING_IN_PROGRESS.intValue()) {
                		return false;
                	}
                }
                anyRecovered = true;
            }
            return anyRecovered;
        } finally {
            recoveryUnlock();
        }
    }
    
    public boolean isLoadAndRecoveryComplete() {
        return isRecoveryComplete() && isLoadComplete();
    }

    public boolean hasLoadAndRecoveryCompleted() {
        return hasLoadAndRecoveryCompleted;
    }

    public synchronized int incrementTotalRecovered(long entities) {
        try {
            totalsDao.lock(TOTALS_LOCK_KEY, -1);
            Integer total = (Integer) totalsDao.get(TOTALS_RECOVERY_KEY);
            Long entityTotal = (Long) totalsDao.get(ENTITIES_RECOVERY_KEY);
            if (total == null) {
                total = new Integer(1);
                entityTotal = new Long(entities);
            } else {
                total = new Integer(total.intValue() + 1);
                entityTotal = new Long(entityTotal.longValue() + entities);
            }
            totalsDao.put(TOTALS_RECOVERY_KEY, total);
            totalsDao.put(ENTITIES_RECOVERY_KEY, entityTotal);
            return total.intValue();
        } catch (Exception e) {
            return 0;
        } finally {
            totalsDao.unlock(TOTALS_LOCK_KEY);
        }
    }
    
    @SuppressWarnings("unchecked")
    public String[] getRecoveryStatus() {
        try {
            recoveryLock();
            ArrayList result = new ArrayList();
            Iterator itr = recoveryDao.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry entry = (Entry) itr.next();
                String key = (String) entry.getKey();
                Object retVal = entry.getValue();
                if (key.startsWith(RECOVERY_SMRY)) {
                	logger.log(Level.DEBUG, "JMX Recovery Type=" + key + ", recovery status=" + retVal);
                    if (retVal == null) {
                        result.add(key.substring(RECOVERY_SMRY.length()) + " = Not-recovered");
                    } else {
                        result.add(key.substring(RECOVERY_SMRY.length()) + " = " + retVal);
                    }
                }
            }
            Collections.sort(result);
            return (String[]) result.toArray(new String[result.size()]);
        } finally {
            recoveryUnlock();
        }
    }
    
    private boolean printRecoveryStatus() {
        try {
            recoveryLock();
            logger.log(Level.DEBUG, "## Recovery summary ##");
            Iterator<Map.Entry<String, Object>> itr = recoveryDao.entrySet().iterator();
            while (itr.hasNext()) {
            	Map.Entry<String, Object> entry = itr.next();
                String key = entry.getKey();
                Object retVal = entry.getValue();
                if (key.startsWith(RECOVERY_SMRY)) {
                    continue;
                }
                logger.log(Level.DEBUG, "## Recovery Type=%s, recovery status=%s", key, retVal);
                if (retVal == null) {
                    return false;
                } else if (retVal instanceof UID) {
                    return false;
                } else if (retVal instanceof Integer) {
                	int loadStatus = ((Integer)retVal).intValue();
                	if (loadStatus == RecoveryStatus.SCHEDULE_FOR_LOADING.intValue() || 
						loadStatus == RecoveryStatus.LOADING_IN_PROGRESS.intValue()) {
                		return false;
                	}
                }
            }
            return true;
        } finally {
            recoveryUnlock();
        }
    }
    
    public synchronized void markRecoveryStatus(String typeName, RecoveryStatus recoveryStatus) {
        try {
            recoveryLock();
            recoveryDao.put(typeName, recoveryStatus.intValue());
            recoveryDao.put(ClusterCacheRecoveryManager.RECOVERY_SMRY+typeName, cluster.getRuleServiceProvider().getName() + ": " + recoveryStatus.summary());
        } finally {
            recoveryUnlock();
        }
    }
}
