package com.tibco.cep.runtime.service.cluster.backingstore.recovery.impl;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

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
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.util.SystemProperty;

public class DistributedEntityParallelStrategy implements EntityRecovery {
    private Cluster cluster;
    private WorkManager recoveryPool;
    private ClusterCacheRecoveryManager recoveryManager;
    private Logger logger;

    private ControlDao<String, Object> loadDao;
    //private ControlDao<Integer, Object> totalsDao;

    int preloadThreads = 4;
    int preloadRetryCount = -1;
    private ExecutorService preloadExecutor;

    public DistributedEntityParallelStrategy(Cluster cacheCluster,
            WorkManager recoveryPool,
            ClusterCacheRecoveryManager recoveryManager,
            ControlDao<String, Object> loadDao,
            ControlDao<Integer, Object> totalsDao) {
        this.cluster = cacheCluster;
        this.recoveryPool = recoveryPool;
        this.recoveryManager = recoveryManager;
        this.loadDao = loadDao;
        //this.totalsDao = totalsDao;

        RuleServiceProvider rsp = cluster.getRuleServiceProvider();
        this.logger = rsp.getLogger(this.getClass());

        this.preloadThreads = Integer.parseInt(rsp.getProperties().getProperty(SystemProperty.DATAGRID_RECOVERY_THREADS.getPropertyName(), "4"));
        this.preloadExecutor = Executors.newFixedThreadPool(preloadThreads*preloadThreads, new PreloadThreadFactory(this.cluster));
    }

    public boolean recover() throws Exception {
        boolean scheduled = false;
        for (EntityDao cache : cluster.getDaoProvider().getAllEntityDao()) {
            if (cache.getConfig().hasBackingStore()) {
                boolean loadThis = cache.getConfig().isLoadOnStartup();
                if (loadThis) {
                    scheduled = true;
                    schedule_preLoad(cache);
                    this.logger.log(Level.DEBUG, "#### Scheduled Pre-load for %s , max-rows=%s", cache.getEntityClass(), cache.getConfig().getLoadFetchSize());
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
        try {
            loadLock();
            Object isLoaded = loadDao.get(cache.getEntityClass().getName());
            if (isLoaded != null && isLoaded instanceof Integer) {
                if (((Integer) isLoaded).intValue() == RecoveryStatus.SCHEDULE_FOR_LOADING.intValue()) {
                    return true;
                }
            }
            return false;
        } finally {
            loadUnlock();
        }
    }*/

    private void loadUnlock() {
        loadDao.unlock(ClusterCacheRecoveryManager.RECOVERY_LOCK_KEY);
    }

    private void loadLock() {
        loadDao.lock(ClusterCacheRecoveryManager.RECOVERY_LOCK_KEY, -1);
    }

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
        boolean toLoad = recoveryManager.acquireTypeForPreLoad(cluster.getMetadataCache().getTypeId(cache.getEntityClass()), cache.getEntityClass().getName());
        if (toLoad) {
            entityCount = _load(cache);
            int total = recoveryManager.incrementTotalLoaded(entityCount);
            long maxRows = cache.getConfig().getLoadFetchSize();
            String typeName = cache.getEntityClass().getName();
            if (maxRows == 0) {
                recoveryManager.markPreloadStatus(typeName, RecoveryStatus.LOADING_DONE);
            } else {
                recoveryManager.markPreloadStatus(typeName, RecoveryStatus.PARTIAL_LOADING_DONE);
            }
            logger.log(Level.DEBUG, "Total types preloaded (" + typeName + "), total=" + total);
        }
        return entityCount;
    }

    /*synchronized private boolean acquireTypeForPreLoad(int typeId, String typeName) {
        boolean toLoad = false;
        try {
            loadLock();
            Object retVal = loadDao.get(typeName);
            if (retVal == null || (retVal instanceof Integer && ((Integer) retVal).intValue() == RecoveryStatus.SCHEDULE_FOR_LOADING.intValue())) {
                loadDao.put(typeName, cluster.getLocalMemberUID());
                loadDao.put(ClusterCacheRecoveryManager.PRELOAD_SMRY+typeName, cluster.getRuleServiceProvider().getName() + ": Started preload");
                toLoad = true;
            }
        } finally {
            loadUnlock();
        }
        return toLoad;
    }*/

    private long _load(EntityDao cache) throws Exception{
        int typeId = cluster.getMetadataCache().getTypeId(cache.getEntityClass());
        String typeName = cache.getEntityClass().getName();
        long maxRows = cache.getConfig().getLoadFetchSize();
        long startTime = System.currentTimeMillis();

        List<Future<Long>> loadList = new ArrayList<Future<Long>>();
        BackingStore recoverystore = cluster.getRecoveryBackingStore();
        long numEntities = recoverystore.getNumEntities(typeId, maxRows);
        if (numEntities == 0) {
            logger.log(Level.INFO, "#### No entries to pre-load for type=%s", typeName);
            return 0L;
        }

        boolean loadHandles = cache.getConfig().isRecoverOnStartup();
        boolean loadEntities = cache.getConfig().isLoadOnStartup();
        long maxId = recoverystore.getMaxId(typeId);
        long minId = recoverystore.getMinId(typeId);
        long batchSize = (maxId-minId)/preloadThreads;
        logger.log(Level.INFO, "####### Pre-loading type=%s max-rows=%s batchsize=%s", typeName, maxRows, batchSize);
        long start = minId;
        long end = minId + batchSize;
        while (start < maxId) {
            Future<Long> future = preloadExecutor.submit(new BatchPreload(typeId, typeName, maxRows, start, end, loadHandles, loadEntities));
            loadList.add(future);
            start = end + 1;
            end = start + batchSize;
            logger.log(Level.DEBUG, "** Submitted BatchPreload entity=%s start=%s end=%s", typeName, start, end);
        }

        long totalCount = 0L;
        //System.out.println(String.format("** Submitted %s jobs, now waiting..", batchNo));
        for (int i = 0; i < loadList.size(); i++) {
            Future<Long> future = (Future<Long>) loadList.get(i);
            try {
                Long result = future.get();
                totalCount = totalCount + result.longValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        long dbTime = System.currentTimeMillis() - startTime;
        String totalStr = (dbTime/3600000)+"h"+((dbTime/60000)%60)+"m"+((dbTime/1000)%60)+"s ";
        Class<?> entityClz = cluster.getMetadataCache().getClass(typeId);
        logger.log(Level.DEBUG,
                "######## Preload Complete for "+entityClz.getName()+" instances="+NumberFormat.getInstance().format(totalCount)+" time="+totalStr+"]");

        return totalCount;
    }

    public synchronized boolean resetLoadTable(UID memberLeftId) {
        try {
            boolean isReset = false;
            loadLock();
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
            loadUnlock();
        }
    }

    protected class BatchPreload implements Callable<Long> {
        private int typeId;
        private String typeName;
        private long maxRows;
        private long start;
        private long end;
        private boolean loadHandles;
        private boolean loadEntities;

        BatchPreload(int typeId, String typeName, long maxRows, boolean loadHandles, boolean loadEntities) {
            this(typeId, typeName, maxRows, -1, -1, loadHandles, loadEntities);
        }

        BatchPreload(int typeId, String typeName, long maxRows, long start, long end, boolean loadHandles, boolean loadEntities) {
            this.typeId = typeId;
            this.typeName = typeName;
            this.maxRows = maxRows;
            this.start = start;
            this.end = end;
            this.loadHandles = loadHandles;
            this.loadEntities = loadEntities;
        }

        public Long call() throws Exception {
            int retryCount = 0;
            while(true) {
                try {
                    BackingStore store = (BackingStore)cluster.getRecoveryBackingStore();
                    if (store != null) {
                        long loaded = store.loadObjects(typeId, maxRows, start, end, loadHandles, loadEntities);
                        return Long.valueOf(loaded);
                    } else {
                        throw new RuntimeException("Unable to instantiate backing store for entityProvider=" +
                                typeName);
                    }
                } catch (Exception e) {
                    retryCount++;
                    if(preloadRetryCount > 0 && retryCount >= preloadRetryCount) {
                        String msg = MessageFormat.format(
                                "Exception \"{0}\" while pre-loading class {1}",
                                e.getMessage(),
                                typeName);
                        throw new RuntimeException(msg);
                    } else {
                        logger.log(Level.INFO,
                                "Exception while preload. Retrying after 5 sec.", e);
                        Thread.sleep(5000);
                    }
                }
            }
        }
    }

    /*
     * A static thread factory for preload executor service
     */
    static class PreloadThreadFactory implements ThreadFactory {

        static int counter = 1;
        Cluster cluster;

        PreloadThreadFactory(Cluster cluster) {
            this.cluster = cluster;
        }

        public Thread newThread(Runnable r) {
            return new Thread(r, this.cluster.getClusterName()+".PreLoadThread-"+PreloadThreadFactory.counter++);
        }
    }
}
