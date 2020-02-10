package com.tibco.cep.runtime.service.cluster.backingstore.recovery.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.tibco.cep.impl.common.resource.UID;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.cluster.backingstore.recovery.ClusterCacheRecoveryManager;
import com.tibco.cep.runtime.service.cluster.backingstore.recovery.EntityRecovery;
import com.tibco.cep.runtime.service.cluster.backingstore.recovery.RecoveryStatus;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMember;
import com.tibco.cep.runtime.service.cluster.util.WorkManager;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.InvocationService;

public class PartitionedEntityStrategy implements EntityRecovery {
	
    Cluster cluster;
    Logger logger;
    WorkManager clusterMgr;
    ClusterCacheRecoveryManager recoveryManager;

    private ControlDao<String, Object> loadDao;
    //private ControlDao<Integer, Object> totalsDao;
    private ControlDao<Integer, Object> preloadBatchesDao;
	
	private InvocationService invocationService;
	
	BlockingQueue<PartitionedEntity> batchQueue;
	ConcurrentSkipListSet<UID> workingMemberSet;
	
	private final static Integer PRELOAD_MANAGER = -1;
	
    public PartitionedEntityStrategy(Cluster cacheCluster,
            WorkManager clusterMgr,
            ClusterCacheRecoveryManager recoveryManager,
            ControlDao<String, Object> loadDao,
            ControlDao<Integer, Object> totalsDao) throws Exception {
        this.cluster = cacheCluster;
        this.clusterMgr = clusterMgr;
        this.recoveryManager = recoveryManager;
        this.loadDao = loadDao;
        //this.totalsDao = totalsDao;
        this.logger = this.cluster.getRuleServiceProvider().getLogger(this.getClass());

        preloadBatchesDao = this.cluster.getDaoProvider().getControlDao("$preloadBatchesDao");

        try {
            invocationService = cluster.getDaoProvider().getInvocationService();
            batchQueue = new LinkedBlockingQueue<PartitionedEntity>();
            workingMemberSet = new ConcurrentSkipListSet<UID>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@Override
	public boolean recover() throws Exception {
		synchronized (this) {
			//clusterMgr.submitJob(new AcquirePreloadControl());
			acquirePartitionedIdBatchControl();
		}
		return true;
	}

	@Override
	public boolean resetLoadTable(UID memberLeftId) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void init() throws Exception {
		
	}
	
    private boolean lockLoadTable() {
        return loadDao.lock(ClusterCacheRecoveryManager.RECOVERY_LOCK_KEY, -1);
    }

    private void unlockLoadTable() {
        loadDao.unlock(ClusterCacheRecoveryManager.RECOVERY_LOCK_KEY);
    }
    
    private void acquirePartitionedIdBatchControl() {
        if (lockLoadTable()) {
            try {
                UID memberID = (UID) loadDao.get(PRELOAD_MANAGER);
                if (memberID == null) {
                    logger.log(Level.DEBUG, "#### This node is preloadmanager ####");
                    loadDao.put(ClusterCacheRecoveryManager.PRELOAD_MANAGER, cluster.getGroupMemberMediator().getLocalMember().getMemberId());
					init();
					clusterMgr.submitJob(new BatchPreloadCacheAgent());
					clusterMgr.submitJob(new BatchInvoker());
				}
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				unlockLoadTable();
			}
		}
	}
	
	protected class BatchInvoker implements Runnable {

		@Override
		public void run() {
			try {
				while(true) {
    				logger.log(Level.TRACE,  
    						"#### getting next batch");
    				PartitionedEntity batch = batchQueue.poll(5000, TimeUnit.MILLISECONDS);
					if(batch == null) {
	    				logger.log(Level.DEBUG,  
						"#### batch unavailable in 5 seconds... continue");
						continue;
					}

					if (batch.getStatus() != BatchStatus.SUCCESS && batch.getStatus() != BatchStatus.FAILED) {
	    				logger.log(Level.TRACE, 
	    						"#### invoking batch "+ batch.getKey());		
                        ControlDao /** or EntityDao */ namedCache = cluster.getDaoProvider().getControlDao(batch.getCacheName());
						/**
						DistributedCacheService service =
							(DistributedCacheService) namedCache.getCacheService();
						
                        GroupMember ownerMember = service.getPartitionOwner(batch.getPartition());
                        if(!workingMemberSet.contains(ownerMember.getMemberId())) {
                            logger.log(Level.DEBUG,
                                    "#### invoking batch %s on member %s ####", batch.getKey(), ownerMember.getMemberId());
                            Set<GroupMember> memberSet = new HashSet<GroupMember>();
                            memberSet.add(ownerMember);
                            batch.setStatus(BatchStatus.ASSIGNED);
                            preloadBatchesDao.put(batch.getPartition(), batch);
                            PartitionedEntityJob partjob = new PartitionedEntityJob(batch);
                            PartitionedIdBatchObserver observer = new PartitionedIdBatchObserver(batch.getTypeName(), batch.getKey());
                            invocationService.invoke(partjob, memberSet); //TODO: invocationService.invoke(partjob, memberSet, observer);
                            workingMemberSet.add(ownerMember.getMemberId());
                        } else {
                            logger.log(Level.TRACE,
                                    "#### member busy, adding batch " + batch + " back to queue");
                            batchQueue.offer(batch);
                            continue;
                        }
						*/
					} else {
	    				logger.log(Level.DEBUG,  
						"#### batchinvoker: batch already completed... some problem here with batch " + batch.getKey());
					}
				}
			} catch (InterruptedException e) {
				//do nothing
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
    
	protected class BatchPreloadCacheAgent implements Runnable {

		@Override
		public void run() {
			try {
				logger.log(Level.DEBUG, "#### Resume Pre-load ####");
				
                preloadBatchesDao.clear();
                EntityDao cache = recoveryManager.getNextEntity();
                if (cache == null)  { // nothing to load
                    logger.log(Level.DEBUG,
                    "#### no more entities to pre-load, markdone");
                    recoveryManager.markRecoveryComplete();
                    return;
                }

				logger.log(Level.INFO, 
						"#### start pre-loading entity " + cache.getEntityClass().getName());

				String typeName = cache.getEntityClass().getName();

                recoveryManager.markPreloadStatus(typeName, RecoveryStatus.LOADING_IN_PROGRESS);

                createBatches(cache);

                Collection<Object> batches = preloadBatchesDao.values();
				Iterator<Object> batchIter = batches.iterator();
				while(batchIter.hasNext()) {
					Object obj = batchIter.next();
					if(!(obj instanceof PartitionedEntity)) {
						continue;
					}
					PartitionedEntity batch = (PartitionedEntity) obj;
					batchQueue.offer(batch);
				}
				
				logger.log(Level.DEBUG, 
						"#### added all the batched to queue");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
        private void createBatches(EntityDao entity) throws Exception {
			
            int typeId       = cluster.getMetadataCache().getTypeId(entity.getEntityClass());
            String typeName  = entity.getEntityClass().getName();
            long fetchSize   = entity.getConfig().getLoadFetchSize();
            String cacheName = entity.getName();

            // Load all the ids
            BackingStore recoverystore = (BackingStore)cluster.getRecoveryBackingStore();
            //long numKeysLoaded = recoverystore.loadObjectKeys(typeId, fetchSize);

            ControlDao /** or EntityDao */ namedCache = cluster.getDaoProvider().getControlDao(cacheName);
            /**
            DistributedCacheService service = (DistributedCacheService) cache.getCacheService();
            int cPartitions = service.getPartitionCount();
            logger.log(Level.DEBUG, "type %s has %s partitions", typeName, cPartitions);
            for (int iPartition = 0; iPartition < cPartitions; iPartition++){
                PartitionedEntity batch = new PartitionedEntity(typeId, typeName, iPartition, cPartitions);
                batch.setCacheName(entity.getName());
                batch.setStatus(BatchStatus.READY);
                batch.setPartition(iPartition);
                batch.setRetryCount(3);
                addBatch(batch);
            }

            int batchcount = preloadBatchesDao.size();
            if (logger.isEnabledFor(Level.DEBUG)) {
                logger.log(Level.DEBUG,
                    "#### Created "+batchcount+" batches for entity " + typeName);
            }
            */
        }

        private void addBatch(PartitionedEntity batchEnt) {
            Integer batchKey = batchEnt.getPartition();
            Object obj = preloadBatchesDao.get(batchEnt.getPartition());
            if (obj == null) {
                preloadBatchesDao.put(batchKey, batchEnt);
            } else {
                if (obj instanceof PartitionedEntity) {
                    PartitionedEntity batch = (PartitionedEntity) obj;
                    if((batch.getStatus() != BatchStatus.SUCCESS && batch.getStatus() != BatchStatus.FAILED) ) {
                        preloadBatchesDao.put(batchKey, batch);
                    } else {
                        logger.log(Level.DEBUG, "" +
                                "#### Batch already added/executed %s ####", batch.getKey());
                    }
                }
            }
        }
    }
	
    protected class PartitionedIdBatchObserver /* implements InvocationObserver */ {
        private String typeName;
        private Integer batchKey;

        PartitionedIdBatchObserver(String typeName, Integer batchKey) {
            this.typeName = typeName;
            this.batchKey = batchKey;
        }

		//@Override
		public void invocationCompleted() {
			logger.log(Level.DEBUG, 
			"#### Invocation complete for batch " + batchKey);
			int typeLoadStatus = checkPreloadDone();
			if (typeLoadStatus == 1 || typeLoadStatus == 2) {
				if (typeLoadStatus == 1) {
					recoveryManager.markPreloadStatus(typeName, RecoveryStatus.LOADING_DONE);
				} else {
					recoveryManager.markPreloadStatus(typeName, RecoveryStatus.PARTIAL_LOADING_DONE);
				}
				logger.log(Level.DEBUG, 
						"#### Preload complete for entity " + typeName + " loaded="+countTotalPreload());
				try {
					clusterMgr.submitJob(new BatchPreloadCacheAgent());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				logger.log(Level.DEBUG, 
						"#### Preloading is still in progress for entity " + typeName);
			}
		}

		//@Override
        public void memberCompleted(GroupMember member, Object arg1) {
            PartitionedEntity batch = (PartitionedEntity) preloadBatchesDao.get(batchKey);
            if (logger.isEnabledFor(Level.DEBUG)) {
                logger.log(Level.DEBUG,
					"#### Completed batch " +	batch + " and result=" + arg1);
            }
            batch.setLoadCount((Long)arg1);
            batch.setStatus(BatchStatus.SUCCESS);
            preloadBatchesDao.put(batchKey, batch);
            workingMemberSet.remove(member.getMemberId());
        }

		//@Override
        public void memberFailed(GroupMember member, Throwable t) {
            PartitionedEntity batch = (PartitionedEntity) preloadBatchesDao.get(batchKey);
			logger.log(Level.ERROR, "#### Failed batch "+ batch.getKey(), t);
			if(batch.getStatus().equals(BatchStatus.ASSIGNED)) {
				batch.setStatus(BatchStatus.RETRY);			
                preloadBatchesDao.put(batchKey, batch);
                logger.log(Level.DEBUG,
                    "#### Retrying preload batch " + batch);
                workingMemberSet.remove(member.getMemberId());
                batchQueue.offer(batch);
            } else {
                batch.setStatus(BatchStatus.FAILED);
                preloadBatchesDao.put(batchKey, batch);
                workingMemberSet.remove(member.getMemberId());
                logger.log(Level.ERROR, "#### Batch failed after retry "+ batch, t);
            }
        }

		//@Override
        public void memberLeft(GroupMember member) {
            logger.log(Level.ERROR, "#### Member " + member.getMemberId() + " left cluster");
            PartitionedEntity batch = (PartitionedEntity) preloadBatchesDao.get(batchKey);
            logger.log(Level.DEBUG, "#### Reassigning batch to new partition owner" + batch);
            batchQueue.offer(batch);
            workingMemberSet.remove(member.getMemberId());
        }

		/*
		 * 0 pending
		 * 1 success
		 * 2 partial success
		 * 
		 */
        private int checkPreloadDone() {
            Collection<Object> params = preloadBatchesDao.values();
            Iterator<Object> paramIter = params.iterator();
            int inprogress = 0;
            int failed = 0;
            int completed = 0;
            while(paramIter.hasNext()) {
                Object obj = paramIter.next();
                if(!(obj instanceof PartitionedEntity)) {
                    continue;
                }
                PartitionedEntity batch = (PartitionedEntity) obj;
                BatchStatus batchStatus = batch.getStatus();
                if (batchStatus == BatchStatus.READY
                        || batchStatus == BatchStatus.ASSIGNED
                        || batchStatus == BatchStatus.RETRY) {
                    inprogress++;
                    if (logger.isEnabledFor(Level.DEBUG)) {
                        //logger.log(Level.DEBUG, "Pre-load batch " + batch.getKey() + " in progress");
                    }
                } else if(batchStatus == BatchStatus.FAILED) {
                    failed++;
                } else {
                    completed++;
                }
            }
            if (logger.isEnabledFor(Level.DEBUG)) {
                logger.log(Level.DEBUG, "Pre-load Batch Status: total batches checked="+params.size()
                        + " inprogress=" + inprogress
                        + " completed=" + completed
                        + " failed=" + failed);
            }
            if (inprogress > 0) {
                return 0;
            } else if(failed > 0) {
                return 2;
            } else {
                return 1;
            }
        }

        private Long countTotalPreload() {
            long result = 0L;
            Collection<Object> params = preloadBatchesDao.values();
            Iterator<Object> paramIter = params.iterator();
            while(paramIter.hasNext()) {
                Object obj = paramIter.next();;
                if(!(obj instanceof PartitionedEntity)) {
                    continue;
                }
                PartitionedEntity batch = (PartitionedEntity) obj;
                result = result + batch.getLoadCount();
            }
            return result;
        }
    }
}
