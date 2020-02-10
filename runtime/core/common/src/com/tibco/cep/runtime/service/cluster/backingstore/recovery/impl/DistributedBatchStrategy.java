package com.tibco.cep.runtime.service.cluster.backingstore.recovery.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.tibco.cep.impl.common.resource.UID;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.cluster.backingstore.recovery.ClusterCacheRecoveryManager;
import com.tibco.cep.runtime.service.cluster.backingstore.recovery.EntityRecovery;
import com.tibco.cep.runtime.service.cluster.backingstore.recovery.MetadataCacheNotInitializedException;
import com.tibco.cep.runtime.service.cluster.backingstore.recovery.RecoveryStatus;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMember;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMemberServiceListener;
import com.tibco.cep.runtime.service.cluster.util.WorkManager;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.Invocable;
import com.tibco.cep.runtime.service.om.api.InvocationService;
import com.tibco.cep.runtime.util.SystemProperty;

public class DistributedBatchStrategy implements EntityRecovery {
	
    private Cluster cluster;
	private WorkManager clusterMgr;
    private Logger logger;
	private ClusterCacheRecoveryManager recoveryManager;
	
    private ControlDao<String, Object> loadDao;
    //private ControlDao<Integer, Object> totalsDao;
	
	private MemberPool memberPool;
	private InvocationService invocationService;
    private BlockingQueue<DistributedBatch> batchQueue;
    private ConcurrentMap<String, DistributedBatch> batchMap;
    private AtomicInteger totalBatches = new AtomicInteger(0);
    private AtomicInteger completedBatches = new AtomicInteger(0);
    private long batchSize;
    private int batchPerNode;
    private long maxBatchCount;
    private long minWindowSize;
    
    private boolean checkAllBatches = false;
    
    public DistributedBatchStrategy(Cluster cacheCluster,
			WorkManager clusterMgr,
			ClusterCacheRecoveryManager recoveryManager,
            ControlDao<String, Object> loadDao,
            ControlDao<Integer, Object> totalsDao) {
        this.cluster = cacheCluster;
		this.clusterMgr = clusterMgr;
		this.recoveryManager = recoveryManager;
        this.loadDao = loadDao;
        //this.totalsDao = totalsDao;
        this.logger = this.cluster.getRuleServiceProvider().getLogger(this.getClass());
        
        Properties props = cluster.getRuleServiceProvider().getProperties();
        batchSize = Long.parseLong(props.getProperty(SystemProperty.DATAGRID_RECOVERY_DISTRIBUTED_BATCHSIZE.getPropertyName(), "1000000"));
        batchPerNode = Integer.parseInt(props.getProperty(SystemProperty.DATAGRID_RECOVERY_DISTRIBUTED_BATCHPERNODE.getPropertyName(), "1"));
        maxBatchCount = Long.parseLong(props.getProperty(SystemProperty.DATAGRID_RECOVERY_DISTRIBUTED_NUMBATCHES.getPropertyName(), "0"));
        minWindowSize = Long.parseLong(props.getProperty(SystemProperty.DATAGRID_RECOVERY_DISTRIBUTED_MINBATCHSIZE.getPropertyName(), "100000"));
        if (maxBatchCount > 0) {
			logger.log(Level.INFO, 
				"DistributedBatchStrategy configured with max-batches=%s and min-windowsize=%s", maxBatchCount, minWindowSize);
        }
        if (cluster.getClusterConfig().useObjectTable() == false) {
            batchSize = batchSize * 16384;
            minWindowSize = minWindowSize * 16384;
        }
        checkAllBatches = Boolean.parseBoolean(props.getProperty(SystemProperty.DATAGRID_RECOVERY_DISTRIBUTED_CHECKALLBATCHES.getPropertyName(), "false"));
        
        invocationService = cluster.getDaoProvider().getInvocationService();
        cluster.getGroupMembershipService().addGroupMemberServiceListener(new PreloadMemberListener());
	}
	
	public void init() throws Exception {
		memberPool = new MemberPool();
		memberPool.init(this.cluster);
		batchQueue = new LinkedBlockingQueue<DistributedBatch>();
		batchMap = new ConcurrentHashMap<String, DistributedBatch>();
	}
	
	@Override
	public boolean recover() throws Exception {
        boolean scheduledRecovery = false;
        boolean scheduledPreload = false;
	    // Set recovery flags
        for (EntityDao cache : cluster.getDaoProvider().getAllEntityDao()) {
            int typeId = cluster.getMetadataCache().getTypeId(cache.getEntityClass());
            String typeName = cache.getEntityClass().getName();
            if (cache.getConfig().hasBackingStore()) {
                if (cache.getConfig().isRecoverOnStartup()) {
                    scheduledRecovery = true;
                    recoveryManager.acquireTypeForRecovery(typeId, typeName);
                }
                if (cache.getConfig().isLoadOnStartup()) {
                    scheduledPreload = true;
                    recoveryManager.acquireTypeForPreLoad(typeId, typeName);
                }
            }
        }

        // Mark recovery complete for a GENERIC type, 
        // so isRecoveryComplete() can return TRUE for all
        if (scheduledRecovery == false) {
            recoveryManager.markRecoveryStatus(ClusterCacheRecoveryManager.GENERIC_TYPE_NAME, RecoveryStatus.LOADING_DONE);
        }
        // Mark preload complete for a GENERIC type, 
        // so isLoadComplete() can return TRUE for all
        if (scheduledPreload == false) {
            recoveryManager.markPreloadStatus(ClusterCacheRecoveryManager.GENERIC_TYPE_NAME, RecoveryStatus.LOADING_DONE);
        }
        
		acquirePreloadBatchControl(null);
		return true;
	}

	@Override
	public boolean resetLoadTable(UID memberLeftId) {
		return false;
	}
	
    private boolean lockLoadTable() {
        return loadDao.lock(ClusterCacheRecoveryManager.RECOVERY_LOCK_KEY, -1);
    }
    
    private void unlockLoadTable() {
        loadDao.unlock(ClusterCacheRecoveryManager.RECOVERY_LOCK_KEY);
    }
    
	private synchronized void acquirePreloadBatchControl(UID memberLeft) {
		if (lockLoadTable()) {
			try {
				// Acquire control if current preload manager is null
                UID currPreloadMrg = (UID) loadDao.get(ClusterCacheRecoveryManager.PRELOAD_MANAGER);
                if (currPreloadMrg == null) {
                    UID localMemberUID = cluster.getGroupMembershipService().getLocalMember().getMemberId();
                    loadDao.put(ClusterCacheRecoveryManager.PRELOAD_MANAGER, localMemberUID);
					logger.log(Level.INFO, "Initial preloadmanager %s", localMemberUID);
					init();
					clusterMgr.submitJob(new BatchPreloadCacheAgent());
					for (int i = 0; i < memberPool.getPoolSize(); i++) {
	                    clusterMgr.submitJob(new BatchInvoker());
					}
				} else {
					if (currPreloadMrg.equals(memberLeft)) {
						logger.log(Level.INFO, "Previous preloadmanager %s", currPreloadMrg);
                        UID localMemberUID = cluster.getGroupMembershipService().getLocalMember().getMemberId();
                        loadDao.put(ClusterCacheRecoveryManager.PRELOAD_MANAGER, localMemberUID);
						logger.log(Level.INFO, "Current preloadmanager %s", localMemberUID);
						init();
						clusterMgr.submitJob(new BatchPreloadCacheAgent());
	                    for (int i = 0; i < memberPool.getPoolSize(); i++) {
	                        clusterMgr.submitJob(new BatchInvoker());
	                    }
					}
				}
			} catch(Exception ex) {
                logger.log(Level.WARN, ex, "Failed to acquire preload batch control ownership");
			} finally {
				unlockLoadTable();
			}
		}
	}

	protected class BatchPreloadCacheAgent implements Runnable {
	    
		@Override
		public void run() {
			try {
				// Reset data-structure before pre-loading next entity
				reset();

                EntityDao cache = recoveryManager.getNextEntity();
				if (cache == null) { // All the entities are pre-loaded
					logger.log(Level.DEBUG, 
							"#### No more entities to pre-load, markdone");
					recoveryManager.markRecoveryComplete();
					return;
				}

                int typeId = cluster.getMetadataCache().getTypeId(cache.getEntityClass());
				String typeName = cache.getEntityClass().getName();
				
                boolean loadHandles = cache.getConfig().isRecoverOnStartup();

				recoveryManager.markPreloadStatus(typeName,
						RecoveryStatus.LOADING_IN_PROGRESS);
				if (loadHandles) {
					recoveryManager.markRecoveryStatus(typeName,
							RecoveryStatus.LOADING_IN_PROGRESS);
				}
				logger.log(Level.INFO,
						"Starting pre-load for entity %s", typeName);

				createBatches(typeId, typeName, loadHandles);

				Collection<DistributedBatch> batches = batchMap.values();
				for(DistributedBatch batch : batches) {
					if(!batchQueue.offer(batch)) {
						logger.log(Level.ERROR,
						"##### Critical Error: Unable to add batch for %s", typeName);
					}
				}
				logger.log(Level.DEBUG,
						"#### Added all the batches to queue for %s", typeName);
			} catch (Exception e) {
				logger.log(Level.WARN, "Failed adding batches to the queue", e);
			}
		}

		private void reset() {
			batchMap.clear();
			completedBatches = new AtomicInteger(0);
			totalBatches = new AtomicInteger(0);
		}
		
		private void createBatches(int typeId, String typeName, boolean loadHandles) throws Exception {
			logger.log(Level.INFO, 
					"Creating batches for entity %s typeId %s", typeName, typeId);
            BackingStore recoverystore = (BackingStore) cluster.getRecoveryBackingStore();
			long minId = recoverystore.getMinId(typeId);
			long maxId = recoverystore.getMaxId(typeId);
			logger.log(Level.DEBUG, 
					"Creating batches for entity %s min=%s max=%s", typeName, String.valueOf(minId), String.valueOf(maxId));
			int numOfBatches = 0;
			if (maxBatchCount > 0) { // Limit number of batches
				if (minId == maxId) {
					numOfBatches++;
					DistributedBatch batch = new DistributedBatch(typeId, minId, maxId);
					batch.setStatus(BatchStatus.READY);
					addPreloadBatch(batch);
				} else {
					long windowSize = (maxId-minId)/maxBatchCount;
					if (windowSize < minWindowSize) {
						windowSize = minWindowSize;
					}
					long start = minId;
					long end = start + windowSize;
					while (start <= maxId) {
						numOfBatches++;
						if (end > maxId) {
							end = maxId;
						}
						DistributedBatch batch = new DistributedBatch(typeId, start, end);
						batch.setStatus(BatchStatus.READY);
						addPreloadBatch(batch);
						start = end + 1;
						end = start + windowSize;
					}
				}
			} else {
				long start = minId;
				long end = minId + batchSize;

				while (start <= maxId) {
					numOfBatches++;
					DistributedBatch batch = new DistributedBatch(typeId, start, end);
					batch.setStatus(BatchStatus.READY);
					addPreloadBatch(batch);
					start = end + 1;
					end = start + batchSize;
				}
			}

			totalBatches = new AtomicInteger(numOfBatches);
			logger.log(Level.INFO, "Created %s batches for entity %s", numOfBatches, typeName);
		}
		
		private void addPreloadBatch(DistributedBatch batch) {
    		if(!batchMap.containsKey(batch.getKey())) { 
    			batchMap.put(batch.getKey(), batch);
    		} else {
				if((!BatchStatus.SUCCESS.equals(batch.getStatus()) && !BatchStatus.FAILED.equals(batch.getStatus())) ) {
					batchMap.put(batch.getKey(), batch);
				} else {
					logger.log(Level.DEBUG, "" +
						"#### Batch already added/executed %s ####", batch.getKey());
				}
    		}
	    }
	}

	protected class BatchInvoker implements Runnable {
	    
        @Override
		public void run() {
			try {
				while(true) {
    				logger.log(Level.TRACE, 
    						"#### Getting next batch");
					DistributedBatch batch = batchQueue.poll(5000, TimeUnit.MILLISECONDS);
					if (batch == null) {
                        // TODO: Should we interrupt as in BE-4.0 instead?
                        if (recoveryManager.hasLoadAndRecoveryCompleted()) {
                            break;
                        }
	    				logger.log(Level.TRACE, 
	    						"#### Batch unavailable in 5 seconds... keep polling");
						continue;
					}
    				logger.log(Level.TRACE,
    						"#### Invoking batch %s ####", batch.getKey());
					if(!BatchStatus.SUCCESS.equals(batch.getStatus()) && !BatchStatus.FAILED.equals(batch.getStatus())) {
                        if (BatchStatus.READY.equals(batch.getStatus()) ||
                            BatchStatus.RETRY.equals(batch.getStatus())) {
                            GroupMember availableMember = memberPool.getMember();
		    				logger.log(Level.INFO,
			   						"Invoke batch=%s member=%s", batch.getKey(), availableMember.getMemberId());
							DistributedBatchJob batchJob = new DistributedBatchJob(batch);
                            Set<GroupMember> memberSet = new HashSet<GroupMember>();
							memberSet.add(availableMember);
							if(!BatchStatus.RETRY.equals(batch.getStatus())) {
								batch.setStatus(BatchStatus.ASSIGNED);
							}
                            PreloadBatchObserver observer = new PreloadBatchObserver(batch.getTypeId(), batch.getKey());
                            Map<String, Invocable.Result> result = invocationService.invokeAndObserve(batchJob, availableMember, observer);
                            logger.log(Level.DEBUG,
                                    "Invoke complete batch=%s member=%s result=%s", batch.getKey(), availableMember.getMemberId(), result);
						} else {
		    				logger.log(Level.ERROR,
		    						"##### Batch status is not ready/retry %s", batch.getKey());
						}
					} else {
	    				logger.log(Level.ERROR, 
	    						"##### Batch already completed... some problem with batch %s", batch.getKey());
					}
				}
			} catch (InterruptedException e) {
				// Do nothing.
			} catch(Exception e) {
                logger.log(Level.WARN, "Failed invoking batches", e);
			}
		}
	}
	
    protected class MemberService implements Runnable {
		private GroupMember member;
		private int state = 0;
		public int MEMBER_LEFT = 1;
        public int MEMBER_JOINED = 2;
		
        MemberService(GroupMember gm, int event) {
			this.member = gm;
			this.state = event;
        }

		@Override
		public void run() {
			try {
				logger.log(Level.DEBUG,	"Received member event %s", this.state);
				if (this.state == this.MEMBER_LEFT) {
					handleMemberLeft(this.member);
				} else if(this.state == this.MEMBER_JOINED) {
					handleMemberJoined(this.member);
				}
			} catch (Exception e) {
                logger.log(Level.WARN, "Failed handling member event %s", e, this.state);
			}
		}
		
        private void handleMemberLeft(GroupMember member) throws Exception {
			try {
				if (lockLoadTable()) {
                    UID controllerUID = (UID)loadDao.get(ClusterCacheRecoveryManager.PRELOAD_MANAGER);
                    logger.log(Level.DEBUG, "Handling member left, current preloadmanager %s", (controllerUID == null) ? "none" : controllerUID);
					if (controllerUID == null) {
                        if (cluster.getMetadataCache().hasStarted() == false) {
							logger.log(Level.ERROR,	"Critical Error: preloadmanager is null");
                            acquirePreloadBatchControl(null);
						}
					} else {
                        if (controllerUID.equals(member.getMemberId())) {//controller is down
                            logger.log(Level.INFO, "Preloadmanager is down %s", controllerUID);
                            //loadDao.put(ClusterCacheRecoveryManagerNew.PRELOAD_MANAGER, null);
                            acquirePreloadBatchControl(member.getMemberId());
						} else {
							if (memberPool != null) {
								logger.log(Level.INFO,
                                    "Member %s left cluster, removing from pool", member.getMemberId());
								// Find the batches for member and assign it to other members
								memberPool.remove(member);
							}
						}
					}
				}
			} finally {
				unlockLoadTable();
			}
		}
		
        private void handleMemberJoined(GroupMember member) throws Exception {
			if (memberPool != null) {
				logger.log(Level.INFO,
                    "Member %s joined cluster, adding to pool", member.getMemberId());
				memberPool.put(member);
			}
		}
	}
	
    protected class PreloadMemberListener implements GroupMemberServiceListener {

        @Override
        public void memberJoined(GroupMember member) {
			try {
				// Process event only if recovery in progress
                if (cluster.getRecoveryManager().isLoadAndRecoveryComplete() == false) {
                    clusterMgr.submitJob(new MemberService(member, 2));
				}
			} catch (InterruptedException e1) {
                logger.log(Level.WARN, "Failed handling member joined event", e1);
			}
		}

		@Override
        public void memberStatusChanged(GroupMember member, Status oldStatus, Status newStatus) {
			try {
				// Process event only if recovery in progress
                if (cluster.getRecoveryManager().isLoadAndRecoveryComplete() == false) {
                    clusterMgr.submitJob(new MemberService(member, 1));
				}
			} catch (InterruptedException e1) {
                logger.log(Level.WARN, "Failed handling member status-changed event", e1);
			}
		}

		@Override
        public void memberLeft(GroupMember member) {
			try {
				// Process event only if recovery in progress
                if (cluster.getRecoveryManager().isLoadAndRecoveryComplete() == false) {
                    clusterMgr.submitJob(new MemberService(member, 1));
				}
			} catch (InterruptedException e1) {
                logger.log(Level.WARN, "Failed handling member left event", e1);
			}
		}
	}

    protected class PreloadBatchObserver implements Observer /* implements InvocationObserver */ {
		private int typeId;
		private String batchKey;
		
		PreloadBatchObserver(int typeId, String batchKey) {
			this.typeId = typeId;
			this.batchKey = batchKey;
		}
		
		@Override
		public void update(Observable observable, Object memberResults) {
	        Map<String, Invocable.Result> resultMap = (LinkedHashMap<String, Invocable.Result>) memberResults;
	        for (Iterator iter = resultMap.entrySet().iterator(); iter.hasNext();) {
	            Map.Entry entry = (Entry) iter.next();
	            GroupMember member = memberPool.locate(entry.getKey().toString());
	            Invocable.Result result = (Invocable.Result) entry.getValue();
	            if ((member != null) && (result.getStatus() == Invocable.Status.SUCCESS)) {
	                Long numLoaded = (Long) result.getResult();
	                memberCompleted((GroupMember)member, numLoaded);
	            }
	        }

			DistributedBatch batch = batchMap.get(batchKey);
			if (completedBatches != null) {
				int progress = (completedBatches.intValue()*100)/totalBatches.intValue();
				logger.log(Level.INFO,
						"Done batch=%s status=%s progress=%s%%", batchKey, batch.getStatus(), progress);
			}
			
            if (BatchStatus.READY.equals(batch.getStatus()) || 
                BatchStatus.RETRY.equals(batch.getStatus()) ||
                BatchStatus.ASSIGNED.equals(batch.getStatus())) {
				return;
			}
            EntityDao cache = cluster.getMetadataCache().getEntityDao(typeId);
			String typeName = cache.getEntityClass().getName();
			int typeLoadStatus = checkPreloadDone();
			if (typeLoadStatus == 1 || typeLoadStatus == 2) {
				if (typeLoadStatus == 1) {
					recoveryManager.markPreloadStatus(typeName, RecoveryStatus.LOADING_DONE);
                    if (cache.getConfig().isRecoverOnStartup()){
						recoveryManager.markRecoveryStatus(typeName, RecoveryStatus.LOADING_DONE);
					}
				} else {
					recoveryManager.markPreloadStatus(typeName, RecoveryStatus.PARTIAL_LOADING_DONE);
                    if (cache.getConfig().isRecoverOnStartup()){
						recoveryManager.markRecoveryStatus(typeName, RecoveryStatus.PARTIAL_LOADING_DONE);
					}
				}
				long numLoaded = countTotalPreload();
                if (cache.getConfig().isLoadOnStartup()) {
                    recoveryManager.incrementTotalLoaded(numLoaded);
                }
                if (cache.getConfig().isRecoverOnStartup()){
					recoveryManager.incrementTotalRecovered(numLoaded);
				}
				logger.log(Level.INFO,
						"Pre-load complete for entity %s, instances loaded=%s", typeName, numLoaded);
				// Start the preload jobs for the next entity
				try {
					clusterMgr.submitJob(new BatchPreloadCacheAgent());
				} catch (InterruptedException e) {
	                logger.log(Level.WARN, "Failed handling pre-load updates", e);
				}
			} else {
				if (logger.isEnabledFor(Level.DEBUG)) {
					logger.log(Level.DEBUG,
						"Pre-load in-progress for type %s, instances loaded=%s", typeName, countTotalPreload());
				}
			}
		}
		
		//@Override
        public void memberCompleted(GroupMember member, Object arg1) {
			DistributedBatch batch = batchMap.get(batchKey);
			logger.log(Level.DEBUG,
					"#### Completed batch %s and result=%s", batch.getKey(), arg1);
			batch.setLoadCount((Long)arg1);
			batch.setStatus(BatchStatus.SUCCESS);
			batchMap.put(batchKey, batch);
			incrementCompletedBatchCount();
			memberPool.release(member);
		}

		//@Override
		public void memberFailed(GroupMember member, Throwable error) {
			DistributedBatch batch = batchMap.get(batchKey);
			if (error instanceof MetadataCacheNotInitializedException) {
				logger.log(Level.DEBUG,  
						"#### Retrying batch %s as metadatacache not initialized at member %s", 
						batch.getKey(), member.getMemberId());
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
				batch.setStatus(BatchStatus.READY);
				batchMap.put(batchKey, batch);
				batchQueue.offer(batch);
			} else {
				logger.log(Level.ERROR, error, 
						"#### Failed batch %s", batch.getKey());
				if (BatchStatus.ASSIGNED.equals(batch.getStatus())) {
					// Give one more retry and then set to failed 
					logger.log(Level.DEBUG,
							"#### Retrying preload batch %s", batch.getKey());
					batch.setStatus(BatchStatus.RETRY);
					batchMap.put(batchKey, batch);
					batchQueue.offer(batch);
				} else if (batch.getStatus() == BatchStatus.RETRY) {
					batch.setStatus(BatchStatus.FAILED);
					batchMap.put(batchKey, batch);
					incrementCompletedBatchCount();
				}
			}
			memberPool.release(member);
		}
		
		//@Override
        public void memberLeft(GroupMember member) {
            logger.log(Level.INFO,
                    "#### Member %s left cluster ####", member.getMemberId());
			DistributedBatch batch = batchMap.get(batchKey);
            if (batch != null &&
               (BatchStatus.ASSIGNED.equals(batch.getStatus()) ||
                BatchStatus.RETRY.equals(batch.getStatus()))) {
				logger.log(Level.DEBUG,
						"#### Adding back batch %s as owner member left", batch.getKey());
				batch.setStatus(BatchStatus.READY);
				batchMap.put(batchKey, batch);
				batchQueue.offer(batch);
			}
			memberPool.remove(member);
		}
		
		private void incrementCompletedBatchCount() {
			if (completedBatches == null) {
				completedBatches = new AtomicInteger(1);
			} else {
				completedBatches.incrementAndGet();
			}
		}
		
		/*
		 * 0 pending
		 * 1 success
		 * 2 partial success
		 * 
		 */
		private int checkPreloadDone() {
			if(!checkAllBatches) {
				if (completedBatches.intValue() == totalBatches.intValue()) {
					return 1;
				} else {
					return 0;
				}
			} else {
				Collection<DistributedBatch> params = batchMap.values();
				int ready = 0;
				int assigned = 0;
				int retry = 0;
				int failed = 0;
				int completed = 0;
				for(DistributedBatch batch : params) {
					if (BatchStatus.READY.equals(batch.getStatus())) {
						ready++;
						if(!logger.isEnabledFor(Level.DEBUG)) {
							break;
						}
					} else if (BatchStatus.ASSIGNED.equals(batch.getStatus())) {
						assigned++;
						if(!logger.isEnabledFor(Level.DEBUG)) {
							break;
						}
					} else if (BatchStatus.RETRY.equals(batch.getStatus())){
						retry++;
					} else if (BatchStatus.FAILED.equals(batch.getStatus())) {
						failed++;
					} else {
						completed++;
					}
				}
				logger.log(Level.DEBUG, "Pre-load Status: total batches=%s ready=%s assigned=%s retry=%s completed=%s failed=%s", 
				        params.size(), ready, assigned, retry, completed, failed);
				if (ready > 0 || assigned > 0) {
					return 0;
				} else if(failed > 0) {
					return 2;
				} else {
					return 1;
				}
			}
		}
		
		private Long countTotalPreload() {
			long result = 0L;
			Collection<DistributedBatch> params = batchMap.values();
			for(DistributedBatch batch : params) {
				result = result + batch.getLoadCount();
			}
			return result;
		}
	}
	
	protected class MemberPool {

        private BlockingQueue<GroupMember> membersQueue;
        private Map<String, GroupMember> membersRegistry;
		
		@SuppressWarnings("unchecked")
        public void init(Cluster cacheCluster) throws Exception {
            logger.log(Level.INFO,
                    "Initializing MemberPool for distributed batch pre-load");
            this.membersQueue = new LinkedBlockingQueue<GroupMember>();
            this.membersRegistry = new TreeMap<String, GroupMember>();
            Set<GroupMember> members = cacheCluster.getGroupMembershipService().getMembers();
            // Add each member multiple times for additional batch jobs 
            for(int i = 0; i < batchPerNode; i++) {
                for(GroupMember member : members) {
                    if (member.getMemberId().equals(cacheCluster.getGroupMembershipService().getLocalMember().getMemberId())){
                        // Batch controller will not execute batch job
                        logger.log(Level.TRACE, "Excluding LocalMember:" + member.getMemberId());
                        continue;
                    }
                    put(member);
                }
			}
			logger.log(Level.INFO,
					"MemberPool initialized with %s members and %s jobs", membersRegistry.size(), membersQueue.size());
		}
		
        public GroupMember getMember() throws Exception {
            GroupMember member = membersQueue.take();
			logger.log(Level.TRACE, "Giving out member %s", member.getMemberId());
			return member;
		}
		
        public void release(GroupMember member) {
			try {
				logger.log(Level.TRACE,
                        "Putting back member %s", member.getMemberId());
				membersQueue.put(member);
			} catch (InterruptedException e) {
                logger.log(Level.WARN, "Failed releasing member from pre-load pool", e);
			}
		}

        public boolean remove(GroupMember member) {
			logger.log(Level.TRACE,
                    "Removing member %s from pool", member.getMemberId());
			return membersQueue.remove(member);
		}
		
        public void put(GroupMember member) throws Exception {
			membersQueue.put(member);
			membersRegistry.put(member.getMemberId().toString(), member);
		}
		
        public boolean contains(GroupMember member) {
            return membersQueue.contains(member);
		}
		
        public GroupMember locate(String memberId) {
            return membersRegistry.get(memberId);
        }
        
        public int getPoolSize() {
            return membersQueue.size();
        }
        
		public void clear() {
			membersQueue.clear();
		}
	}
}
