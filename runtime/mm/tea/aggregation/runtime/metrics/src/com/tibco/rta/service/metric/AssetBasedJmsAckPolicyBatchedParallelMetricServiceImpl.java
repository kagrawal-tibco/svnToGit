package com.tibco.rta.service.metric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.google.common.util.concurrent.AtomicDouble;
import com.tibco.rta.Fact;
import com.tibco.rta.common.GMPActivationListener;
import com.tibco.rta.common.service.FactMessageContext;
import com.tibco.rta.common.service.RtaTransaction;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.StickyThreadPool;
import com.tibco.rta.common.service.Transaction;
import com.tibco.rta.common.service.TransactionEvent;
import com.tibco.rta.common.service.TransactionEvent.Status;
import com.tibco.rta.common.service.WorkItem;
import com.tibco.rta.impl.FactImpl;
import com.tibco.rta.impl.FactKeyImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.impl.DimensionHierarchyImpl;
import com.tibco.rta.service.cluster.GroupMember;
import com.tibco.rta.service.cluster.GroupMembershipListener;
import com.tibco.rta.service.metric.directive.MetricProcessingDirective;
import com.tibco.rta.service.metric.directive.MetricProcessingDirectiveFactory;


public class AssetBasedJmsAckPolicyBatchedParallelMetricServiceImpl extends AssetBasedParallelMetricServiceImpl implements GroupMembershipListener, GMPActivationListener {


    public AssetBasedJmsAckPolicyBatchedParallelMetricServiceImpl() {

    }

    @Override
    public void init(Properties configuration) throws Exception {
        super.init(configuration);

        ServiceProviderManager.getInstance().getGroupMembershipService().addMembershipListener(this);
        ServiceProviderManager.getInstance().getGroupMembershipService().addActivationListener(this);

    }

    @Override
    public <C extends FactMessageContext> TransactionEvent assertFact(Fact fact) throws Exception {
        return assertFact(null, fact);
    }

    @Override
    public <C extends FactMessageContext> TransactionEvent assertFact(C messageContext, List<Fact> facts) throws Exception {
        TransactionEvent transactionEvent = new TransactionEvent(((FactKeyImpl) facts.get(0).getKey()).getUid());
//        for (int i=0; i<facts.size(); i++) {
//        	Fact fact = facts.get(i);
//        	te = new TransactionEvent(((FactKeyImpl)facts.get(0).getKey()).getUid());
//        	transactionEvents.add(te);
//        }
        if (isStarted()) {
        	
        	//Bala: since this executes on the work item thread already, no need to further dispatch it to the pool.
        	//just do a synchronous "call" here instead.
            //workItemService.addWorkItem(new FactWorkItem<C>(facts, messageContext, transactionEvent));
        	
        	FactWorkItem factWorkItem = new FactWorkItem<C>(facts, messageContext, transactionEvent);
        	factWorkItem.call();
        	

            //this ensures that the fact.save and submit for processing to other queues is complete.
            return transactionEvent;
            
        } else {
            if (LOGGER.isEnabledFor(Level.WARN)) {
                LOGGER.log(Level.WARN, "Service %s is not yet started", serviceName);
            }
            transactionEvent.setStatus(Status.FAILURE);
            return transactionEvent;
        }

    }

    @Override
	public <G extends GroupMember> void memberJoined(G member) {
	    //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public <G extends GroupMember> void memberLeft(G member) {
	    //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public <G extends GroupMember> void onPrimary(G member) {
	    if (LOGGER.isEnabledFor(Level.INFO)) {
	        LOGGER.log(Level.INFO, "Engine Activated to Primary. Will start Batch Flush Job Timer.");
	    }
	    try {
	        firstFactTime.set(System.currentTimeMillis());
	        createFlushJobTimer();
	    } catch (Exception e) {
	        LOGGER.log(Level.ERROR, "Error while creating server batch flush job timer", e);
	    }
	}

	@Override
	public <G extends GroupMember> void onSecondary(G member) {
	    //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void quorumComplete(GroupMember... groupMembers) {
	    //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public <G extends GroupMember> void networkFailed() {
	    //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public <G extends GroupMember> void networkEstablished() {
	    //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public <G extends GroupMember> void onFenced(G member) {
	    //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public <G extends GroupMember> void onUnfenced(G member) {
	    //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public <G extends GroupMember> void onConflict(G member) {
	    //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void onActivate() {
	    if (LOGGER.isEnabledFor(Level.INFO)) {
	        LOGGER.log(Level.INFO, "Engine Activated to Primary. Will start Batch Flush Job Timer.");
	    }
	    try {
	        firstFactTime.set(System.currentTimeMillis());
	        createFlushJobTimer();
	    } catch (Exception e) {
	        LOGGER.log(Level.ERROR, "Error while creating server batch flush job timer", e);
	    }
	}

	@Override
	public void onDeactivate() {
		// TODO Auto-generated method stub
		
	}

	protected void handleAcknowlegements(List<AssetBasedMetricComputeJob> metricJobs) {
	    //now get the contexts that have been processed by various jobs
	    for (AssetBasedMetricComputeJob job : metricJobs) {
			if (job instanceof AssetBasedBatchedMetricComputeJob) {
				AssetBasedBatchedMetricComputeJob newBatchedMetricComputeJob = (AssetBasedBatchedMetricComputeJob) job;
				for (MessageContextWrapper mc : newBatchedMetricComputeJob
						.getProcessedContexts()) {
					mc.tryToAck();
				}
			}
	    }
	}

	protected Map<String, List<AssetBasedAggregator>> groupByKey(List<AssetBasedAggregator> aggList) throws Exception {
		
		Map<String, List<AssetBasedAggregator>> grpByKeyMap = new HashMap<String, List<AssetBasedAggregator>>();
		for (AssetBasedAggregator agg : aggList) {
			if (agg != null) {
	
				agg.initAggregator();
				
				Fact fact = agg.getFact();
				DimensionHierarchy hierarchy = agg.getHierarchy();
				RtaSchema schema = hierarchy.getOwnerSchema();
				Cube cube = hierarchy.getOwnerCube();
	
				String key = schema.getName() + cube.getName()
						+ hierarchy.getName();
	
				if (!hierarchy.computeRoot() && !isAssetFact(fact)) {
					// further parallelize it by adding the value of the 0th
					// dimension to the key, thereby making it more unique
					// increasing its chances of executing on another
					// thread.
					String zeroDimVal = getZeroDimValue(fact, hierarchy);
					key += zeroDimVal;
				}
	
				List<AssetBasedAggregator> innerList = grpByKeyMap.get(key);
				if (innerList == null) {
					innerList = new ArrayList<AssetBasedAggregator>();
					grpByKeyMap.put(key, innerList);
				}
				innerList.add(agg);
			}
		}
		
		return grpByKeyMap;
	}

	private void createFlushJobTimer() {
        if (batchsize > 1) {
            TimerTask task = new FlushJobPoster(this);
            flushJobTimer.schedule(task, 0, batchFlushPeriodInMillis);
        }
    }

    private void distributeJobs(MessageContextWrapper messageContextWrapper, List<AssetBasedMetricComputeJob> totalMetricComputeJobList, 
			List<AssetBasedAggregator> totalAggregatorList, boolean flushBatch) throws Exception, InterruptedException, ExecutionException {
		//one MetricComputeJob per distinctKey will be added to the totalMetricComputeJobList.
		List<Future<Transaction>> futures = batchAndDistributeAggregatorsToStickyThreads(messageContextWrapper, totalMetricComputeJobList, totalAggregatorList, flushBatch);
		
		//Wait for them to complete. Some of them will complete immediately, as they only add to thread local and exit.
		waitForFutures(futures);
		
		//Iterate over all the MetricComputeJobLists and try to acknowlege the message.
		handleAcknowlegements(totalMetricComputeJobList);
	}

	private List<Future<Transaction>> batchAndDistributeAggregatorsToStickyThreads(MessageContextWrapper messageContextWrapper, List<AssetBasedMetricComputeJob> totalMetricComputeJobList,
			List<AssetBasedAggregator> metricJobList, boolean flushBatch) 
			throws Exception, InterruptedException, ExecutionException {
		List<Future<Transaction>> futures = new ArrayList<Future<Transaction>>();
		
		Map<String, List<AssetBasedAggregator>> grpByKeyMap = groupByKey(metricJobList);
		
		for (Map.Entry<String, List<AssetBasedAggregator>> entry : grpByKeyMap.entrySet()) {
		    String key = entry.getKey();
		    List<AssetBasedAggregator> aggs = entry.getValue();
		    messageContextWrapper.incrementCount();
		    AssetBasedBatchedMetricComputeJob job = new AssetBasedBatchedMetricComputeJob(AssetBasedJmsAckPolicyBatchedParallelMetricServiceImpl.this, aggs, messageContextWrapper, null, flushBatch, false);
		    totalMetricComputeJobList.add(job);
		    Future<Transaction> future = stickyThreadPool.submit(key, job, stickyThreadAllocator);
		    futures.add(future);
		}
		return futures;
		
	}

	private void waitForFutures(List<Future<Transaction>> futures) {
		for (Future<Transaction> future : futures) {
		    //this is the key difference. Wait for all threads to complete, so that the message can be acknowledged in 1 place.
			try {
				future.get();
			} catch (Exception e) {
				//TODO: Bala: Handle Exception
			}
		}
	}

	private boolean validateFactForHierarchy(Fact fact, DimensionHierarchy hierarchy2) {
	    boolean isValid = true;
	    StringBuilder b = new StringBuilder();
	    for (Dimension dimName : hierarchy2.getDimensions()) {
	        String attrName = dimName.getAssociatedAttribute().getName();
	        if (fact.getAttribute(attrName) == null) {
	            isValid = false;
	            b.append(attrName).append(", ");
	        }
	    }
	    if (!isValid) {
	        if (LOGGER.isEnabledFor(Level.DEBUG)) {
	            LOGGER.log(Level.DEBUG,
	                    "Fact [%s] rejected by hierarchy [%s] because it does not contain the following mandatory attributes [%s]",
	                    ((FactKeyImpl) fact.getKey()).getUid(),
	                    (hierarchy2.getOwnerSchema().getName() + "/" + hierarchy2.getOwnerCube().getName() + "/" + hierarchy2.getName()),
	                    b.toString());
	        }
	    }
	    return isValid;
	}

	/**
     * Workitem for fact assertion.
     */
    private class FactWorkItem<C extends FactMessageContext> implements WorkItem<TransactionEvent> {

        private List<Fact> facts;

        private C messageContext;

        private MessageContextWrapper messageContextWrapper;

        private TransactionEvent transactionEvent;

        FactWorkItem(List<Fact> fact, C messageContext, TransactionEvent transactionEvent) {
            this.facts = fact;
            this.messageContext = messageContext;
            this.transactionEvent = transactionEvent;
            this.messageContextWrapper = new MessageContextWrapper(messageContext, fact.size(), totalTimeForFactTrip, totalFacts);
        }

        @Override
        public TransactionEvent call() throws Exception {
        	long t1 =0, t2=0, t3=0, t4=0;
            boolean success = false;
            
            Transaction txn = null;
            if (LOGGER_DTLS.isEnabledFor(Level.DEBUG)) {
            	LOGGER_DTLS.log(Level.DEBUG, "Received message [%s]", messageContext.getId());
            }
            //only honor this setting in this implementation. i.e; if "ems" is used for "zero" dataloss.
            t3 = System.currentTimeMillis();
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Facts batch [%d] received", facts.size());
            }

            if (isShouldStoreFacts()) {
                try {

                	int i = 0;
                    for (i = 0; i < transactionRetryCount || transactionRetryCount < 0; i++) {
                    	if (i > 0) {
                            Thread.sleep(5000);
                    	}
                        txn = RtaTransaction.get();
                        txn.beginTransaction();
                        if (LOGGER.isEnabledFor(Level.DEBUG)) {
                            LOGGER.log(Level.DEBUG, "Facts transaction [%d] of batch [%d] Started at time: [%d]", txn.hashCode(), facts.size(), t1);
                        }
                        try {
                            for (Fact fact : facts) {
                                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                                    LOGGER.log(Level.DEBUG, "Saving a fact [%s]", fact.getKey());
                                }
                                omService.save(fact);
                            }
                            t1 = System.currentTimeMillis();
                            txn.commit();
                            t2 = System.currentTimeMillis();
                            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                                LOGGER.log(Level.DEBUG, "Facts transaction [%d] of batch [%d] Completed at time: [%d]", txn.hashCode(), facts.size(), t2);
                            }
                            success = true;
                            break;
                        } catch (Exception e) {
                            LOGGER.log(Level.ERROR, "Facts transaction [%d] Error. Retry attempt [%d] failed. Will retry again in [5] seconds.", e, txn.hashCode(), i+1);
                            try {
								txn.rollback();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
	                            LOGGER.log(Level.ERROR, "Rollback excetFacts transaction [%d] Error. Retry attempt [%d] failed. Will retry again in [5] seconds.", e, txn.hashCode(), i+1);
							}

                        } finally {
                            if (txn != null) {
                                txn.clear();
                            }
                        }
                    }
                    if (i == transactionRetryCount) {
                    	
                    	LOGGER.log(Level.ERROR, "Facts transaction [%s] Error. Maximum retry attempts [%d] reached. Giving up.",txn.hashCode(), i);
                    	
//in the case of multiple failures, code reaches here. now simply acknowledge the messages. 
                    	messageContext.setBatchSize(1);
                    	messageContext.acknowledge();
                    	
//TODO and put them to an error queue
                    	ServiceProviderManager.getInstance().getTransportService().handleError(messageContext);
                    }
                } finally {
                    if (txn != null) {
                        txn.remove();
                    }
                }
            } else {
                success = true;
            }

            long totBatches1 = totalMsgs.incrementAndGet();

            if (success) {
            	
                long totTime = totalTimeForFactSaving.addAndGet(t2-t1);
                long totFacts = totalFacts2.addAndGet(facts.size());
                double avgBatchSaveTime = totTime / totBatches1;
                avgFactBatchSaveTime.set(avgBatchSaveTime);
                avgFactBatchSize.set(totFacts/totBatches1);
                

                //this one distributes the work to various sticky threads.
                processFacts();

            }
            t4 = System.currentTimeMillis();
            long totWorkerExecTime = totalWorkerExecTime.addAndGet(t4-t3);
            double avgWorkerExecTime1 = totWorkerExecTime/totBatches1;
            avgWorkerExecTime.set(avgWorkerExecTime1);
            
            

            return transactionEvent;
        }


        @Override
        public TransactionEvent get() {
            // TODO Auto-generated method stub
            return null;
        }

		private void processFacts() throws Exception, InterruptedException {
		
            List<AssetBasedAggregator> totalAggregatorList = new ArrayList<AssetBasedAggregator>();

			List<AssetBasedMetricComputeJob> totalMetricComputeJobList = new ArrayList<AssetBasedMetricComputeJob>();


            try {

                for (Fact fact : facts) {
                	
                    if (((FactImpl) fact).isDuplicate()) { //it gets marked as duplicate when a constraint violation exception is thrown in the database.
                        if (isProcessDuplicateFacts()) {
                            LOGGER.log(Level.WARN, "Processing a possible duplicate fact: [%s]", fact.getKey());
                        } else {
                            LOGGER.log(Level.WARN, "Ignoring a possible duplicate fact: [%s]", fact.getKey());
                            continue;
                        }
                    }
                    
                    RtaSchema schema = fact.getOwnerSchema();
                	     	

                    for (Cube cube : schema.getCubes()) {
                        for (DimensionHierarchy hierarchy : cube.getDimensionHierarchies()) {
                        	AssetBasedAggregator agg = null;
                        	MetricProcessingDirective directive = MetricProcessingDirectiveFactory.INSTANCE.getMetricProcessingDirectives(hierarchy.getOwnerSchema().getName());
                        	boolean allowNulls = directive.allowNullDimensionValues(hierarchy, fact);
                        	LOGGER.log(Level.DEBUG, "Hierachy Name: %s", hierarchy.getName() + "::" + hierarchy.isEnabled());
                        	if (!hierarchy.isEnabled()) {
                        		agg = new AssetBasedNoOpAggregator(fact, hierarchy);
                        	} else if (!DimensionHierarchyImpl.isAssetHierarchy(hierarchy)) {
                        		boolean processNode = directive.processNode(hierarchy, fact);
                        		if (processNode) {
                        			if (allowNulls){
	                                    agg = new AssetBasedAggregator(fact, hierarchy, false, null);
	                                } else if (validateFactForHierarchy(fact, hierarchy)) {
	                                    agg = new AssetBasedAggregator(fact, hierarchy);
	                                } else  {
	                                	agg = new AssetBasedNoOpAggregator(fact, hierarchy);
	                                }
                        		} else {
                        			agg = new AssetBasedNoOpAggregator(fact, hierarchy);
                        		}

                            } else {
                            	String assetName = (String) fact.getAttribute(Fact.ASSET_NAME);
                            	if (assetName != null && assetName.equals(DimensionHierarchyImpl.getAssetName(hierarchy))) {
                            		if (allowNulls){
                                        agg = new AssetBasedAggregator(fact, hierarchy, false, null);
                                    } else if (validateFactForHierarchy(fact, hierarchy)) {
                                        agg = new AssetBasedAggregator(fact, hierarchy);
                                    } else {
                                    	agg = new AssetBasedNoOpAggregator(fact, hierarchy);
                                    }
                            	} else {
                                	agg = new AssetBasedNoOpAggregator(fact, hierarchy);
                            	}
                            }
                        	
                        	totalAggregatorList.add(agg);
                        }
                    }
                }
                
                distributeJobs(messageContextWrapper, totalMetricComputeJobList, totalAggregatorList, false);


            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "Exception while creating and distriburing compute jobs to sticky pool.", e);
            }
        }
    }
}

class FlushJobPoster extends TimerTask {
	
    protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_METRIC.getCategory());
	AssetBasedJmsAckPolicyBatchedParallelMetricServiceImpl metricService;
	StickyThreadPool stickyThreadPool;

	public FlushJobPoster(AssetBasedJmsAckPolicyBatchedParallelMetricServiceImpl metricService) {
		this.metricService = metricService;
		this.stickyThreadPool = metricService.getStickyPool();
	}
    
	@Override
    public void run() {
		// message per sec code here
		long messageCount = 0;

        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Posting a batch flush job to all compute threads.");
        }

        List<Future<Transaction>> futures = new ArrayList<Future<Transaction>>();
        List<AssetBasedMetricComputeJob> metricJobs = new ArrayList<AssetBasedMetricComputeJob>();

    	messageCount = ((AssetBasedParallelMetricServiceImpl)metricService).totalMsgs.get(); //get previous message counts
    	long msgsInBatch = messageCount - ((AssetBasedParallelMetricServiceImpl)metricService).totalMsgsPrev.get();
    	((AssetBasedParallelMetricServiceImpl)metricService).msgTps.set(msgsInBatch*1000/metricService.getBatchFlushPeriod());
    	
    	long factCount = ((AssetBasedParallelMetricServiceImpl)metricService).totalFacts2.get(); //get previous facts counts
    	long factsInBatch = factCount - ((AssetBasedParallelMetricServiceImpl)metricService).totalFactsPrev2.get();
    	((AssetBasedParallelMetricServiceImpl)metricService).factTps.set(factsInBatch*1000/metricService.getBatchFlushPeriod());

        for (Map.Entry<String, ExecutorService> executor : stickyThreadPool.getAllExecutors().entrySet()) {
            ExecutorService es = executor.getValue();
            try {
                AssetBasedBatchedMetricComputeJob flushJob = getFlushBatchJob();
                metricJobs.add(flushJob);
                Future<Transaction> future = es.submit(flushJob);
                futures.add(future);
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "", e);
            }
        }

        ((AssetBasedParallelMetricServiceImpl)metricService).totalMsgsPrev.set(messageCount);  // previous becomes current
        ((AssetBasedParallelMetricServiceImpl)metricService).totalFactsPrev2.set(factCount);  // previous becomes current

        for (Future<Transaction> future : futures) {
            //this is the key difference. Wait for all threads to complete, so that the message can be acknowledged in 1 place.
            try {
                future.get();
            } catch (Exception e) {
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Error while waiting for a sticky jobs to complete", e);
            }
        }
        }

        metricService.handleAcknowlegements(metricJobs);

    }
	
    private AssetBasedBatchedMetricComputeJob getFlushBatchJob() throws Exception {
        AssetBasedBatchedMetricComputeJob flushJob = new AssetBasedBatchedMetricComputeJob(metricService, new ArrayList<AssetBasedAggregator>(), null, null, true, false);
        return flushJob;
    }
    

}
