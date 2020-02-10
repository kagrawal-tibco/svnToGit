/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.backingstore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.impl.common.resource.UID;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.ClusterConfiguration;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMember;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMemberServiceListener;
import com.tibco.cep.runtime.service.cluster.system.EntityIdMask;
import com.tibco.cep.runtime.service.cluster.util.WorkManager;
import com.tibco.cep.runtime.service.cluster.util.WorkManagerFactory;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.EntityDaoConfig;
import com.tibco.cep.runtime.session.RuleServiceProvider;
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

    Cluster cluster;
    ControlDao<String, Object> recoveryDao;
    ControlDao<String, Object> loadDao;
    ControlDao<Integer, Object> totalsDao;
    RuleServiceProvider rsp;
    WorkManager recoveryPool;

    private final static String  RECOVERY_SMRY        = "RECOVERY_SMRY:"; 
    private final static String  PRELOAD_SMRY         = "PRELOAD_SMRY:"; 
    private final static Integer RECOVERY_DONE        = new Integer(1);
    private final static Integer RECOVERY_ERROR       = new Integer(2);
    private final static Integer SCHEDULE_FOR_RECOVERY= new Integer(3);

    private final static Integer LOADING_DONE         = new Integer(1);
    private final static Integer PARTIAL_LOADING_DONE = new Integer(2);
    private final static Integer LOADING_ERROR        = new Integer(3);
    private final static Integer SCHEDULE_FOR_LOADING = new Integer(4);

    private final static Integer TOTALS_RECOVERY_KEY  = new Integer(6);
    private final static Integer ENTITIES_RECOVERY_KEY= new Integer(7);
    private final static Integer TOTALS_PRELOAD_KEY   = new Integer(8);
    private final static Integer ENTITIES_PRELOAD_KEY = new Integer(9);

    private final static String  GENERIC_TYPE_NAME    = "GENERIC_TYPE_CLASS";

    private static final String  RECOVERY_LOCK_KEY    = new String("-1");
    private static final String  PRE_RECOVERY_LOCK_KEY= new String("-2");
    private static final Integer TOTALS_LOCK_KEY      = new Integer(-1);

    private volatile boolean hasLoadAndRecoveryCompleted = false;
    private volatile long timeLoadAndRecoveryStarted = 0L;

    private Logger logger;

    public boolean preRecovered = false;
    public boolean allRecovered = false;

    private BackingStore recoveryStore = null;

    public ClusterCacheRecoveryManager() {

    }

    public void init(Cluster cacheCluster) throws Exception {
        this.cluster = cacheCluster;
        this.rsp = cluster.getRuleServiceProvider();
        this.logger = rsp.getLogger(ClusterCacheRecoveryManager.class);
        
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

        this.recoveryStore = this.cluster.getRecoveryBackingStore();
        
        if (this.recoveryStore == null) {
            this.logger.log(Level.WARN, "Backing Store is not initialized for the cluster [ %s ]", this.cluster.getClusterName());
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

        int numThreads = Integer.parseInt(rsp.getProperties().getProperty(SystemProperty.DATAGRID_RECOVERY_THREADS.getPropertyName(), "4"));
        recoveryPool = WorkManagerFactory.create(cluster.getClusterName(), null, null,
                getClass().getSimpleName(), numThreads, 4 * numThreads, cluster.getRuleServiceProvider());
        recoveryPool.start();
        timeLoadAndRecoveryStarted = System.currentTimeMillis();

        isInitialized = true;
        logger.log(Level.INFO, "Cluster Cache Recovery Manager initialized with mode=distributed threads=%s", numThreads);
    }

    public void start() throws Exception {
		/*
		// Disable recovery in Inference engines
        if (!this.cluster.getClusterConfig().isStorageEnabled()) {
            return;
        }
		*/
        
        if (isInitialized == false) {
            // Not configured for Recovery - Ignore!
            return;
        }

        GenericBackingStore gbs = this.cluster.getBackingStore();
        String sharedNothingStoreClassName = "com.tibco.cep.runtime.service.dao.impl.tibas.backingstore.BEDataGridSharedNothingStore";
        Class sharedNothingStoreClass = Class.forName(sharedNothingStoreClassName);

        if (!this.cluster.getClusterConfig().isHasBackingStore() && gbs == null ) {
            this.logger.log(Level.TRACE, "Backing Store is not configured for the cluster " + this.cluster.getClusterName());
            //masterCache.transitionTo(CLUSTER_RECOVERED);
            return;
        } else if (!this.cluster.getClusterConfig().isHasBackingStore() && sharedNothingStoreClass.isAssignableFrom(gbs.getClass())) {
            this.logger.log(Level.TRACE, "Backing Store [Shared Nothing] is configured for the cluster [" + this.cluster.getClusterName()+"]");
            return;
        }

        if (this.cluster.getBackingStore() == null) {
            this.logger.log(Level.DEBUG, "Backing Store is not initialized for the cluster " + this.cluster.getClusterName());
            //masterCache.transitionTo(CLUSTER_RECOVERED);
            return;
        }
        
        // Start control caches
        recoveryDao.start();
        loadDao.start();
        totalsDao.start();

        // Perform some one time tasks
        preRecovery();
        
        // preload/recovering to be performed by CS only
        if (this.cluster.getClusterConfig().isStorageEnabled()) {
			ControlDao.ChangeListener cacheChangeListener = new CacheChangeListener();

			recoveryDao.registerListener(cacheChangeListener);
			loadDao.registerListener(cacheChangeListener);

			GroupMemberServiceListener memberListener = new MemberServiceListener();
			cluster.getGroupMembershipService().addGroupMemberServiceListener(memberListener);
			// Execute recovery
			recover();
		}
        
       	logger.log(Level.INFO, "## Waiting for %s cluster recovery to complete", this.cluster.getClusterName());
    	waitForRecovery();
    	logger.log(Level.INFO, "## %s cluster recovery has completed", this.cluster.getClusterName());
    	// Enable writes (recovery already done)...
		// Note: This is just a local call, although there
		// is no harm in making it a global-invocation
		//this.backingStore.setWriteMode(true);
    	waitForWriteMode();
    }
    
    private void waitForWriteMode() {
    	BackingStore backingStore = null;
    	GenericBackingStore genericBackingStore = this.cluster.getBackingStore();
    	if ((genericBackingStore != null) && (genericBackingStore instanceof BackingStore)) {
    		backingStore = ((BackingStore)genericBackingStore);
    	}
    	if (backingStore != null) {
    		logger.log(Level.INFO, "## Waiting for WRITE MODE to be turned on");
    		while (!backingStore.getWriteMode()) {
    			try {
    				Thread.sleep(5000);
    			}
    			catch (InterruptedException ie) {
    			}
    		}
    		logger.log(Level.INFO, "## WRITE MODE turned on");
    	}
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

            boolean truncateOnRestart = ((BEProperties) rsp.getProperties()).getBoolean(SystemProperty.CLUSTER_TRUNCATE_ON_RESTART.getPropertyName(), false);
            if (truncateOnRestart) {
                this.logger.log(Level.WARN, "Cluster is configured to truncate on restart, all data will be lost...");
                recoveryStore.clear();
            }

            boolean migrate = ((BEProperties) rsp.getProperties()).getBoolean(SystemProperty.CLUSTER_MIGRATE_OBJECTTABLE.getPropertyName(), false);
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

            boolean isCacheAside = ((BEProperties) rsp.getProperties()).getBoolean(SystemProperty.CLUSTER_IS_CACHE_ASIDE.getPropertyName(), false);
            boolean keepDeleted = ((BEProperties) rsp.getProperties()).getBoolean(SystemProperty.CLUSTER_KEEP_DELETED.getPropertyName(), true);
            boolean performCleanup = ((BEProperties) rsp.getProperties()).getBoolean(SystemProperty.CLUSTER_CLEANUP.getPropertyName(), !keepDeleted);
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

            registerTypesForPreloading();
            registerTypesForRecovery();

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
    
    protected void recover() throws Exception {
        boolean scheduledRecovery = false;
        boolean scheduledPreload = false;

        final String loadAll = ((BEProperties) rsp.getProperties()).getString(SystemProperty.PRELOAD_HANDLES.getPropertyName(), "");

        if (!loadAll.equalsIgnoreCase("none")) {
            for (EntityDao entity : cluster.getDaoProvider().getAllEntityDao()) {
                String typeName = entity.getEntityClass().getName();
                int typeId = cluster.getMetadataCache().getTypeId(entity.getEntityClass());
                EntityDaoConfig entityConfig = this.cluster.getMetadataCache().getEntityDaoConfig(entity.getEntityClass());
                if (entityConfig.hasBackingStore()) {
                    boolean loadThis = loadAll.equalsIgnoreCase("all") || entityConfig.isRecoverOnStartup() ||
                        ((BEProperties) rsp.getProperties()).getBoolean("be.engine.cluster." + typeName + ".preload.handles", false);
                    if (loadThis) {
                        this.schedule_recovery(typeId, entityConfig);
                        scheduledRecovery = true; 
                    }
                }
            }
        }

        // Mark recovery complete for a GENERIC type, 
        // so isRecoveryComplete() can return TRUE for all
        if (scheduledRecovery == false) {
            markRecoveryCompleteForType(GENERIC_TYPE_NAME, true);
        }
        
        final String preloadAll = ((BEProperties) rsp.getProperties()).getString(SystemProperty.PRELOAD.getPropertyName(), "");

        if (!preloadAll.equalsIgnoreCase("none")) {
            for (EntityDao entity : cluster.getDaoProvider().getAllEntityDao()) {
                String typeName = entity.getEntityClass().getName();
                int typeId = cluster.getMetadataCache().getTypeId(entity.getEntityClass());
                EntityDaoConfig entityConfig = this.cluster.getMetadataCache().getEntityDaoConfig(entity.getEntityClass());
                if (entityConfig.hasBackingStore()) {
                    boolean loadThis = preloadAll.equalsIgnoreCase("all") || entityConfig.isLoadOnStartup() ||
                        ((BEProperties) rsp.getProperties()).getBoolean("be.engine.cluster." + typeName + ".preload", false);
                    if (loadThis) {
                        this.schedule_preLoad(typeId, entityConfig);
                        scheduledPreload = true; 
                    }
                }
            }
        }
        
        // Mark preload complete for a GENERIC type, 
        // so isLoadComplete() can return TRUE for all
        if (scheduledPreload == false) {
            markPreloadCompleteForType(0, GENERIC_TYPE_NAME, true);
        }
        
        if (!allRecovered) {
            if (!scheduledRecovery && !scheduledPreload) { // || this.cluster.getClusterConfig().asyncRecovery())
                // Let the cluster proceed...
                this.logger.log(Level.INFO, "Cluster Recovered");
                
                // Enable writes (no recovery scheduled)...
                //CacheFlush.flushCache(cluster);
                SetWriteMode.setWriteMode(true);
                //masterCache.transitionTo(CLUSTER_RECOVERED);
                
                allRecovered = true;
            } else {
                if (this.markRecoveryComplete()) {
                    allRecovered = true;
                }
            }
        }
    }

    private void registerTypesForPreloading() throws Exception {
        String preloadAll = ((BEProperties) rsp.getProperties()).getString(SystemProperty.PRELOAD.getPropertyName(), "");
        if (!preloadAll.equalsIgnoreCase("none")) {
            for (EntityDao entity : cluster.getDaoProvider().getAllEntityDao()) {
                String typeName = entity.getEntityClass().getName();
                int typeId = cluster.getMetadataCache().getTypeId(entity.getEntityClass());
                EntityDaoConfig entityConfig = this.cluster.getMetadataCache().getEntityDaoConfig(entity.getEntityClass());
                if (entityConfig.hasBackingStore()) {
                    boolean loadThis = preloadAll.equalsIgnoreCase("all") || entityConfig.isLoadOnStartup() ||
                        ((BEProperties) rsp.getProperties()).getBoolean("be.engine.cluster." + typeName + ".preload", false);
                    if (loadThis) {
                        this.registerTypeForPreload(typeName);
                        logger.log(Level.INFO, "Pre-loading (" + entity.getEntityClass() + ") flag=" + entityConfig.isLoadOnStartup()
                                + " [" + preloadAll + " / " + ((BEProperties) rsp.getProperties()).getBoolean("be.engine.cluster." + typeName + ".preload", false) + "]");                        
                    } else {
                        logger.log(Level.WARN, "Skipping preload (" + entity.getEntityClass() + ") flag=" + entityConfig.isLoadOnStartup()
                                + " [" + preloadAll + " / " + ((BEProperties) rsp.getProperties()).getBoolean("be.engine.cluster." + typeName + ".preload", false) + "]");                        
                    }
                }
            }
        }
    }

    private void registerTypesForRecovery() throws Exception {
        String loadAll = ((BEProperties) rsp.getProperties()).getString(SystemProperty.PRELOAD_HANDLES.getPropertyName(), "");
        if (!loadAll.equalsIgnoreCase("none")) {
            for (EntityDao entity : cluster.getDaoProvider().getAllEntityDao()) {
                String typeName = entity.getEntityClass().getName();
                int typeId = cluster.getMetadataCache().getTypeId(entity.getEntityClass());
                EntityDaoConfig entityConfig = this.cluster.getMetadataCache().getEntityDaoConfig(entity.getEntityClass());
                if (entityConfig.hasBackingStore()) {
                    boolean loadThis = loadAll.equalsIgnoreCase("all") || entityConfig.isRecoverOnStartup() ||
                        ((BEProperties) rsp.getProperties()).getBoolean("be.engine.cluster." + typeName + ".preload.handles", false);
                    if (loadThis) {
                        this.registerTypeForRecovery(typeName);
                        logger.log(Level.INFO, "Recovering (" + entity.getEntityClass() + ") flag=" + entityConfig.isRecoverOnStartup()
                                + " [" + loadAll + " / " + ((BEProperties) rsp.getProperties()).getBoolean("be.engine.cluster." + typeName + ".preload.handles", false) + "]");                        
                    } else {
                        logger.log(Level.WARN, "Skipping recovery (" + entity.getEntityClass() + ") flag=" + entityConfig.isRecoverOnStartup()
                                + " [" + loadAll + " / " + ((BEProperties) rsp.getProperties()).getBoolean("be.engine.cluster." + typeName + ".preload.handles", false) + "]");                        
                    }
                }
            }
        }
    }

    /**
     *
     * @param typeId
     * @throws Exception
     */
    public long recover(int typeId, long maxRows) throws Exception {
        long entityCount = 0;
        String typeName = getTypeName(typeId);
        boolean toLoad = true; //acquireTypeForRecovery(typeName); //Bala: schedule_recovery already acquired it
        if (toLoad) {
            boolean success = false;
            entityCount = _recover(typeId, maxRows);
            success = true;
            int total = incrementTotalRecovered(entityCount);
            markRecoveryCompleteForType(typeName, success);
            logger.log(Level.DEBUG, "Total ObjectTable types recovered (" + typeName + "), total="+ total);
        }
        return entityCount;
    }

    synchronized private void markRecoveryCompleteForType(String typeName, boolean success) {
        try {
            recoveryLock();
            if (success) {
                recoveryDao.put(typeName, RECOVERY_DONE);
                recoveryDao.put(RECOVERY_SMRY+typeName, cluster.getRuleServiceProvider().getName() + ": Completed recovery");
            } else {
                // Don't mark as error, so another instance can take over
                //recoveryDao.put(typeName, RECOVERY_ERROR);
            }
        } finally {
            recoveryUnlock();
        }
    }

    @Deprecated
    synchronized private boolean acquireTypeForRecovery(String typeName) {
        boolean toLoad = false;
        try {
            recoveryLock();
            //logger.log(Level.DEBUG, "Acquired recovery lock: " + typeId);
            Object retVal = recoveryDao.get(typeName);
            if (retVal == null || retVal instanceof Integer &&
					((Integer) retVal).intValue() == SCHEDULE_FOR_RECOVERY.intValue()) {
                recoveryDao.put(typeName, cluster.getGroupMemberMediator().getLocalMember().getMemberId());
                recoveryDao.put(RECOVERY_SMRY+typeName, cluster.getRuleServiceProvider().getName() + ": Started recovery");
                toLoad = true;
            }
        } finally {
            //logger.log(Level.DEBUG, "Released recovery lock: " + typeId);
            recoveryUnlock();
        }
        return toLoad;
    }

    private void recoveryUnlock() {
        //recoveryDao.unlock(ConcurrentMap.LOCK_ALL); //LOCK_ALL does not work
        recoveryDao.unlock(RECOVERY_LOCK_KEY);
    }

    private void recoveryLock() {
        //recoveryDao.lock(ConcurrentMap.LOCK_ALL, -1);
        recoveryDao.lock(RECOVERY_LOCK_KEY, -1);
    }

    public java.util.Iterator getObjects(int typeId) throws Exception {
        return recoveryStore.getObjects(typeId);
    }

    private long _recover(int typeId, long maxRows) throws Exception {
        this.logger.log(Level.INFO, "Recovering class %s", getTypeName(typeId));
        if (recoveryStore != null) {
            long l = recoveryStore.recoverObjectTable(typeId, maxRows);
            return l;
        } else {
            throw new RuntimeException("Unable to instantiate backing store for entityProvider=" + 
                    getTypeName(typeId));
        }
    }

    private boolean schedule_preLoad(int typeId, EntityDaoConfig entityConfig) throws Exception {
        long loadFetchSize = ((BEProperties) rsp.getProperties()).getLong(SystemProperty.PRELOAD_FETCH_SIZE.getPropertyName(), entityConfig.getLoadFetchSize());
        boolean scheduledPreload = this.schedule_preLoad(typeId, loadFetchSize, false);
        if (scheduledPreload) {
             this.logger.log(Level.INFO, "####### Scheduled %s for pre-loading max-rows=%s", getTypeName(typeId), loadFetchSize);
        }
        return scheduledPreload;
    }
    
    private boolean schedule_preLoad(int typeId, long maxRows, boolean loadHandles) throws Exception {
        if (toStartPreload(typeId)) {
            Thread.sleep(500); // introduce a delay so that one node does not hog all entries
            recoveryPool.submitJob(new PreLoadCacheAgent(typeId, maxRows, logger, loadHandles));
            return true;
        }
        return false;
    }

    private boolean schedule_recovery(int typeId, EntityDaoConfig entityConfig)
            throws Exception {
        long loadFetchSize = ((BEProperties) rsp.getProperties()).getLong(SystemProperty.PRELOAD_FETCH_SIZE.getPropertyName(), entityConfig.getLoadFetchSize());
        boolean scheduledRecovery = schedule_recovery(typeId, loadFetchSize);
        if (scheduledRecovery) {
            this.logger.log(Level.INFO, "####### Scheduled %s for recovery fetch-size=%d", getTypeName(typeId), loadFetchSize);
        }
        return scheduledRecovery;
    }
    
    private boolean schedule_recovery(int typeId, long maxRows) throws Exception {
        if (toStartRecovery(typeId)) {
            Thread.sleep(500); //introduce a delay so that one node does not hog all entries
            recoveryPool.submitJob(new ObjectTableRecoverAgent(typeId, maxRows, logger));
            return true;
        }
        return false;
    }

    private synchronized boolean toStartRecovery(int typeId) {
        try {
            recoveryLock();
        	String typeName = getTypeName(typeId);
            Object isRecovered = recoveryDao.get(typeName);
            if (isRecovered != null && isRecovered instanceof Integer) {
                if (((Integer) isRecovered).intValue() == SCHEDULE_FOR_RECOVERY.intValue()) {
                    UID self = cluster.getGroupMemberMediator().getLocalMember().getMemberId();
                    recoveryDao.put(typeName, self);
                    return true;
                }
            }
            return false;
        } finally {
            recoveryUnlock();
        }
    }

    private synchronized boolean toStartPreload(int typeId) {
        try {
            loadLock();
	        String typeName = getTypeName(typeId);
            Object isRecovered = loadDao.get(typeName);
            if (isRecovered != null && isRecovered instanceof Integer) {
                if (((Integer) isRecovered).intValue() == SCHEDULE_FOR_LOADING.intValue()) {
                    UID self = cluster.getGroupMemberMediator().getLocalMember().getMemberId();
                    loadDao.put(typeName, self);
                    return true;
                }
            }
            return false;
        } finally {
            loadUnlock();
        }
    }

    private void loadUnlock() {
        loadDao.unlock(RECOVERY_LOCK_KEY);
    }

    private void loadLock() {
        loadDao.lock(RECOVERY_LOCK_KEY, -1);
    }

    public void shutdown() {
        recoveryPool.shutdown();
        loadDao.discard();
        recoveryDao.discard();
        totalsDao.discard();
    }

    public synchronized boolean isRecovered(int typeId) throws Exception {
        String typeName = getTypeName(typeId);
        try {
            recoveryLock();
            Object isRecovered = recoveryDao.get(typeName);

            if (isRecovered != null && isRecovered instanceof Integer) {
                int recoveryStatus = ((Integer) isRecovered).intValue();
                if (recoveryStatus == RECOVERY_DONE.intValue()) {
                    return true;
                } else {
                    logger.log(Level.ERROR, "Class=" + typeName + " not recovered");
                    return false;
                }
            } else {
                return false;
            }
        } finally {
            recoveryUnlock();
        }
    }

    public synchronized boolean isLoaded(int typeId) throws Exception {
        String typeName = getTypeName(typeId);
        try {
            loadLock();
            Object isLoaded = loadDao.get(typeName);

            if (isLoaded != null && isLoaded instanceof Integer) {
                int loadStatus = ((Integer) isLoaded).intValue();
                if (loadStatus == LOADING_DONE.intValue() || loadStatus == PARTIAL_LOADING_DONE.intValue()) { // Should partial loading return TRUE?
                    return true;
                } else {
                    logger.log(Level.ERROR, "Class=" + typeName + " not loaded");
                    return false;
                }
            } else {
                return false;
            }
        } finally {
            loadUnlock();
        }
    }

    public long load(int typeId, long maxRows, boolean loadHandles) throws Exception {
        long entityCount = 0;
        String typeName = getTypeName(typeId);
        boolean toLoad = true; //acquireTypeForPreLoad(typeName); //Bala: schedule_preload already acquired it
        if (toLoad) {
            boolean success = false;
            entityCount = _load(typeId, maxRows, loadHandles);
            success = true;
            int total = incrementTotalLoaded(entityCount);
            markPreloadCompleteForType(maxRows, typeName, success);
            logger.log(Level.DEBUG, "Total types preloaded (" + typeName + "), total=" + total);
        }
        return entityCount;
    }

    synchronized private void markPreloadCompleteForType(long maxRows, String typeName, 
            boolean success) {
        try {
            loadLock();
            if (success) {
                if (maxRows == 0) {
                    loadDao.put(typeName, LOADING_DONE);
                    loadDao.put(PRELOAD_SMRY+typeName, cluster.getRuleServiceProvider().getName() + ": Completed preload");
                } else {
                    loadDao.put(typeName, PARTIAL_LOADING_DONE);
                    loadDao.put(PRELOAD_SMRY+typeName, cluster.getRuleServiceProvider().getName() + ": Completed partial preload");
                }
            } else {
                // Don't mark as error, so another instance can take over
                //loadDao.put(typeName, LOADING_ERROR);
            }
        } finally {
            loadUnlock();
        }
    }

    @Deprecated
    synchronized private boolean acquireTypeForPreLoad(String typeName) {
        boolean toLoad = false;
        try {
            loadLock();
            //logger.log(Level.DEBUG, "Acquired load lock: " + typeId);
            Object retVal = loadDao.get(typeName);
            if (retVal == null || (retVal instanceof Integer &&
					((Integer) retVal).intValue() == SCHEDULE_FOR_LOADING.intValue())) {
                loadDao.put(typeName, cluster.getGroupMemberMediator().getLocalMember().getMemberId());
                loadDao.put(PRELOAD_SMRY+typeName, cluster.getRuleServiceProvider().getName() + ": Started preload");
                toLoad = true;
            }
        } finally {
            //logger.log(Level.DEBUG, "Released load lock: " + typeId);
            loadUnlock();
        }
        return toLoad;
    }

    private long _load(int typeId, long maxRows, boolean loadHandles) throws Exception {
        this.logger.log(Level.INFO, "Pre-loading class %s", getTypeName(typeId));
        if (recoveryStore != null) {
        	// Ignoring loadHandles for now as it needs quite a few other adjustments
            long loaded = recoveryStore.loadObjects(typeId, maxRows, false, true);
            return loaded;
        } else {
            throw new RuntimeException("Unable to instantiate backing store for entityProvider=" +
                    getTypeName(typeId));
        }
    }

    public class PreLoadCacheAgent implements Runnable {
        int typeId;
        long maxRows;
        Logger logger;
        boolean loadHandles;

        PreLoadCacheAgent(int typeId, long maxRows, Logger logger, boolean loadHandles) {
            this.typeId = typeId;
            this.maxRows = maxRows;
            this.logger = logger;
            this.loadHandles = loadHandles;
        }

        public void run() {
            try {
                long total = load(typeId, maxRows, loadHandles);
            } catch (IllegalStateException isex) {
                // If error is 'SafeNamedCache was explicitly released' likely shutdown in progress.
                this.logger.log(Level.ERROR, "Exception during preload: " + isex.getMessage());
            } catch (Exception e) {
                this.logger.log(Level.ERROR, "Error while Pre-loading class " + 
                        getTypeName(typeId), e);
            } finally {
                markRecoveryComplete();
            }
        }
    }

    private synchronized int incrementTotalLoaded(long entities) {
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

    private synchronized int incrementTotalRecovered(long entities) {
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
                        getTypeName(typeId));
                e.printStackTrace();
            } finally {
                markRecoveryComplete();
            }
        }
    }

    protected synchronized boolean markRecoveryComplete() {
        try {
            if (!hasLoadAndRecoveryCompleted && isLoadAndRecoveryComplete()) {
                // Sleep 10 seconds to avoid confusing log entries 
                // (no table recovery happens literally in milliseconds, logs get out of order) 
                try { Thread.sleep(10000); } catch (Exception e) { }
                
                if (logger.isEnabledFor(Level.DEBUG)) {
                    printRecoveryStatus(Level.DEBUG);
                    printPreloadStatus(Level.DEBUG);
                }
                logger.log(Level.INFO,
                        "Total preloaded types=" + ((totalsDao.get(TOTALS_PRELOAD_KEY) == null) ? 0:totalsDao.get(TOTALS_PRELOAD_KEY)) +
                        " instances=" + ((totalsDao.get(ENTITIES_PRELOAD_KEY) == null) ? 0:totalsDao.get(ENTITIES_PRELOAD_KEY)));
                logger.log(Level.INFO,
                        "Total recovered types=" + ((totalsDao.get(TOTALS_RECOVERY_KEY) == null) ? 0:totalsDao.get(TOTALS_RECOVERY_KEY)) +
                        " instances=" + ((totalsDao.get(ENTITIES_RECOVERY_KEY) == null) ? 0: totalsDao.get(ENTITIES_RECOVERY_KEY)));

                // Enable writes (recovery complete)
                //CacheFlush.flushCache(cluster);
                SetWriteMode.setWriteMode(true);
                //cluster.getMasterCache().transitionTo(CacheCluster.CLUSTER_RECOVERED);
                //cluster.getMasterCache().setClusterRecoveryState(true);
                //TODO: cluster.getGroupMembershipService().transitionTo(GroupMembershipServiceImpl.State.RECOVERED);
                hasLoadAndRecoveryCompleted = true;
                long total = (System.currentTimeMillis() - timeLoadAndRecoveryStarted);
                String totalStr = (total/3600000)+"h"+((total/60000)%60)+"m"+((total/1000)%60)+"s ";
                logger.log(Level.INFO, "####### Cluster Preload/Recovery completed in %s", totalStr);
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

    public void registerTypeForPreload(String typeName) {
        if (loadDao.lock(typeName, -1)) {
            try {
                loadDao.put(typeName, SCHEDULE_FOR_LOADING);
                loadDao.put(PRELOAD_SMRY+typeName, cluster.getRuleServiceProvider().getName() + ": Registered for preload");
            } catch (Throwable ex) {
            } finally {
                loadDao.unlock(typeName);
            }
        }
    }

    public void registerTypeForRecovery(String typeName) {
        if (recoveryDao.lock(typeName, -1)) {
            try {
                recoveryDao.put(typeName, SCHEDULE_FOR_RECOVERY);
                recoveryDao.put(RECOVERY_SMRY+typeName, cluster.getRuleServiceProvider().getName() + ": Registered for recovery");
            } catch (Throwable ex) {
            } finally {
                recoveryDao.unlock(typeName);
            }
        }
    }
    
    public synchronized boolean resetRecoveryTable(UID memberLeftId) {
        try {
            boolean isReset = false;
            recoveryLock();
            Iterator itr = recoveryDao.keySet().iterator();
            while (itr.hasNext()) {
                String typeName = (String) itr.next();
                Object memberId = recoveryDao.get(typeName);
                if (memberId != null && memberId instanceof UID) {
                    if (memberLeftId.equals(memberId)) {
                        recoveryDao.put(typeName, SCHEDULE_FOR_RECOVERY);
                        recoveryDao.put(RECOVERY_SMRY+typeName, cluster.getRuleServiceProvider().getName() + ": Reset for recovery");
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

    public synchronized boolean resetLoadTable(UID memberLeftId) {
        try {
            boolean isReset = false;
            loadLock();
            Iterator itr = loadDao.keySet().iterator();
            while (itr.hasNext()) {
                String typeName = (String) itr.next();
                Object memberId = loadDao.get(typeName);
                if (memberId != null && memberId instanceof UID) {
                    if (memberLeftId.equals(memberId)) {
                        loadDao.put(typeName, SCHEDULE_FOR_LOADING);
                        loadDao.put(PRELOAD_SMRY+typeName, cluster.getRuleServiceProvider().getName() + ": Reset for preload");
                        logger.log(Level.INFO, "## Reset preload for type %s", typeName);
                        isReset = true;
                    }
                }
            }
            return isReset;
        } finally {
            loadUnlock();
        }
    }

    private synchronized boolean isLoadComplete() {
        try {
            loadLock();
            boolean anyLoaded = false;
            Iterator itr = loadDao.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry entry = (Entry) itr.next();
                Object key = entry.getKey();
                Object retVal = entry.getValue();
                if (key.equals(RECOVERY_LOCK_KEY)) {                    
                    continue; // Ignore LOCK key - FM loadLock();
                }
				if (key.equals(PRE_RECOVERY_LOCK_KEY)) {
                    continue; // Ignore the pre-recovery LOCK key as well
                }
                if (retVal == null) {
                    return false;
                } else if (retVal instanceof UID) {
                    return false;
                } else if (retVal instanceof Integer) {
                	int loadStatus = ((Integer)retVal).intValue();
                	if (loadStatus == SCHEDULE_FOR_LOADING.intValue() ||
                		loadStatus == LOADING_ERROR.intValue()) {
                	    return false;
                	}
                }
                anyLoaded = true;
            }
            return anyLoaded;
        } finally {
            loadUnlock();
        }
    }
	
    private synchronized boolean isRecoveryComplete() {
        try {
            recoveryLock();
            boolean anyRecovered = false;
            Iterator itr = recoveryDao.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry entry = (Entry) itr.next();
                Object key = entry.getKey();
                Object retVal = entry.getValue();
                if (key.equals(RECOVERY_LOCK_KEY)) {                    
                    continue; // Ignore LOCK key - FM recoveryLock();
                }
				if (key.equals(PRE_RECOVERY_LOCK_KEY)) {
                    continue; // Ignore the pre-recovery LOCK key as well
                }
                if (retVal == null) {
                    return false;
                } else if (retVal instanceof UID) {
                    return false;
                } else if (retVal instanceof Integer) {
                	int loadStatus = ((Integer)retVal).intValue();
                	if (loadStatus == SCHEDULE_FOR_RECOVERY.intValue() ||
                	    loadStatus == RECOVERY_ERROR.intValue()) {
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

    synchronized public boolean isLoadAndRecoveryComplete() {
        return isRecoveryComplete() && isLoadComplete();
    }

    private String getTypeName(int typeId) {
        if (typeId < 0) {
            return GENERIC_TYPE_NAME;
        }
        String typeName = cluster.getMetadataCache().getClass(typeId).getName();
        return typeName;
    }
    
    @SuppressWarnings("unchecked")
    public synchronized String[] getRecoveryStatus() {
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

    @SuppressWarnings("unchecked")
    public synchronized String[] getPreloadStatus() {
        try {
            loadLock();
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
            loadUnlock();
        }
    }

    synchronized private void printRecoveryStatus(Level level) {
        try {
            recoveryLock();
            logger.log(level, "## Recovery summary ## ");
            Iterator itr = recoveryDao.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry entry = (Entry) itr.next();
                Object key = entry.getKey();
                Object retVal = entry.getValue();
                if (key.equals(PRE_RECOVERY_LOCK_KEY) || key.equals(RECOVERY_LOCK_KEY)) {
                    continue;
                }
                logger.log(level, "## Recovery Type=%s, recovery status=%s", key, retVal);
            }
        } finally {
            recoveryUnlock();
        }
    }

    synchronized private void printPreloadStatus(Level level) {
        try {
            loadLock();
            logger.log(level, "## Preload summary ##");
            Iterator itr = loadDao.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry entry = (Entry) itr.next();
                Object key = entry.getKey();
                Object retVal = entry.getValue();
                if (key.equals(PRE_RECOVERY_LOCK_KEY) || key.equals(RECOVERY_LOCK_KEY)) {
                    continue;
                }
                logger.log(level, "## Preload Type=%s, preload status=%s", key, retVal);
            }
        } finally {
            loadUnlock();
        }
    }
    
    private void waitForRecovery() {
        while (!hasLoadAndRecoveryCompleted) {
            if (isLoadAndRecoveryComplete()) {
                if (logger.isEnabledFor(Level.DEBUG)) {
                    printRecoveryStatus(Level.DEBUG);
                    printPreloadStatus(Level.DEBUG);
                }
                hasLoadAndRecoveryCompleted = true;
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
    
    /**
     * Act if any member left while preloading on recovering object table
     */
    class MemberServiceListener implements GroupMemberServiceListener {

        public void memberJoined(GroupMember member) {
            // TODO Auto-generated method stub
        }

        public void memberLeft(GroupMember member) {
            resetLoadTable(member.getMemberId());
            resetRecoveryTable(member.getMemberId());
        }

        public void memberStatusChanged(GroupMember member, Status oldStatus,
                Status newStatus) {
            // TODO Auto-generated method stub
        }
    }
    
    /**
     * Listen for any change recover/load cache 
     */
    class CacheChangeListener implements ControlDao.ChangeListener<String, Object> {

        public void onPut(String key, Object value) {
            // TODO Auto-generated method stub
        }

        public void onRemove(String key, Object value) {
            // TODO Auto-generated method stub
        }

        public void onUpdate(String key, Object oldValue, Object newValue) {
            try {
                if (newValue.equals(SCHEDULE_FOR_LOADING)) {
                    int typeId = cluster.getMetadataCache().getTypeId(key);
                    Class entityClass = cluster.getMetadataCache().getClass(typeId);
                    EntityDaoConfig entityConfig = cluster.getMetadataCache().getEntityDaoConfig(entityClass);
                    schedule_preLoad(typeId, entityConfig);
                } else if(newValue.equals(SCHEDULE_FOR_RECOVERY)) {
                    int typeId = cluster.getMetadataCache().getTypeId(key);
                    Class entityClass = cluster.getMetadataCache().getClass(typeId);
                    EntityDaoConfig entityConfig = cluster.getMetadataCache().getEntityDaoConfig(entityClass);
                    schedule_recovery(typeId, entityConfig);
                }   
            } catch (Exception e) {
                logger.log(Level.ERROR, "####### Error occured while rescheduling preload/recovery", e);
            }
        }
    }
}
