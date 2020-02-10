package com.tibco.rta.service.metric;

import java.util.ArrayList;
import java.util.List;

import com.tibco.rta.common.FatalException;
import com.tibco.rta.common.RecoverableException;
import com.tibco.rta.common.service.RtaTransaction;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.Transaction;
import com.tibco.rta.log.Level;
import com.tibco.rta.service.metric.AssetBasedParallelMetricServiceImpl.FactWrapper;

public class AssetBasedBatchedMetricComputeJob extends AssetBasedMetricComputeJob {
	
	static final ThreadLocal<List<AssetBasedAggregator>> JOBS = new ThreadLocal<List<AssetBasedAggregator>>() {
		public List<AssetBasedAggregator> initialValue() {
			return new ArrayList<AssetBasedAggregator>();
		}
	};
	
	static final ThreadLocal<List<MessageContextWrapper>> MESSAGECONTEXTS = new ThreadLocal<List<MessageContextWrapper>>() {
		public List<MessageContextWrapper> initialValue() {
			return new ArrayList<MessageContextWrapper>();
		}
	};
	
	static final ThreadLocal<List<FactWrapper>> FACTWRAPPERS = new ThreadLocal<List<FactWrapper>>() {
		public List<FactWrapper> initialValue() {
			return new ArrayList<FactWrapper>();
		}
	};
	
	protected List<AssetBasedAggregator> jobs = new ArrayList<AssetBasedAggregator>();

	protected boolean flushBatch;
	
//	protected boolean markProcessedFacts;
	
	protected List<MessageContextWrapper> processedContexts = new ArrayList<MessageContextWrapper>();
	
	protected MessageContextWrapper contextWrapper;
	
	protected FactWrapper factWrapper;
	
	
	public AssetBasedBatchedMetricComputeJob(MetricService metricService, List<AssetBasedAggregator> jobs, MessageContextWrapper contextWrapper, FactWrapper factWrapper, 
			boolean flushBatch,	boolean updateFactMetric) throws Exception {
		super(metricService, null, updateFactMetric);
		this.jobs = jobs;
		this.flushBatch = flushBatch;
		this.contextWrapper = contextWrapper;
		this.factWrapper = factWrapper;
	}
	
//	public NewBatchedMetricComputeJob(MetricService metricService, List<AbstractAggregator> jobs, , boolean updateFactMetric) throws Exception {
//		super(metricService, null, updateFactMetric);
//		this.jobs = jobs;
//		this.flushBatch = flushBatch;
//	}
	
	@Override
	public Transaction call() throws Exception {
		
		List<AssetBasedAggregator> threadLocalList = JOBS.get();
		
		List<MessageContextWrapper> contextList = MESSAGECONTEXTS.get();
		
		List<FactWrapper> factWrapperList = FACTWRAPPERS.get();
		
		if (!flushBatch) {
			// add all the batch jobs to this list
			threadLocalList.addAll(jobs);

			// add it to the cached contexts.
			if (contextWrapper != null) {
				contextList.add(contextWrapper);
			}

			if (factWrapper != null) {
				factWrapperList.add(factWrapper);
			}
		}
		
		if (threadLocalList.size() < metricService.getTxnBatchSize() && !flushBatch) {
			if (LOGGER.isEnabledFor(Level.DEBUG)) {
				LOGGER.log(Level.DEBUG, "Jobs added to a server batch. [%d]", jobs.size());
			}
			return new RtaTransaction();
		}
		
		
		if (threadLocalList.size() > 0) {
			if (LOGGER.isEnabledFor(Level.DEBUG)) {
				LOGGER.log(Level.DEBUG, "Started processing a server batch of size [%d]", threadLocalList.size());
			}
		} else {
			return new RtaTransaction();
		}
		
		
		Transaction txn = null;
		boolean isSuccess = false;
		Exception ex = null;
		int i = 0;
		
		int transactionRetryCount = metricService.getTxnRetryCount();
		int batchSize = 0;
		long t1=0, t2=0, t3=0, t4=0;
		t3=System.currentTimeMillis();
		long totJobs = 0;
		try {
			for (i = 0; i < transactionRetryCount || transactionRetryCount < 0; i++) {
				txn = RtaTransaction.get();
				try {			
					
					if (LOGGER.isEnabledFor(Level.DEBUG)) {
						LOGGER.log(Level.DEBUG, "Metrics Transaction [%d] Started at time: [%d]",	txn.hashCode(), System.currentTimeMillis());
					}
					int retryCnt = 0;
					while (true) {
						retryCnt++;
						try {
							txn.beginTransaction();
							txn.setClientBatchSize(metricService.getClientTxnBatchSize());
							for (AssetBasedAggregator job : threadLocalList) {
								if (job.preProcess()) {
									try {
										job.computeMetric();
									} catch (Exception e) {
										job.postProcess();
										throw e;
									}
									
									if (updateFactMetric) {
										omService.save(job.getFact(),
												job.getHierarchy());
									}
								}
							}
							break;
						} catch (RecoverableException e) {
							if (LOGGER.isEnabledFor(Level.ERROR)) {
								LOGGER.log(Level.ERROR, "Metrics Transaction [%d] Recoverable Error. Retry attempt [%d]. Will retry in [%d] seconds.", txn.hashCode(), retryCnt+1, 5);
							}
							Thread.sleep(5000);
							try {
								txn.rollback();
							} catch (Exception e1) {
								
							}
							txn.clear();
						}
					}
					t1 = System.currentTimeMillis();
					txn.commit();
					t2 = System.currentTimeMillis();
					//unlock
					postProcess(threadLocalList);
					
					isSuccess = true;
					if (LOGGER.isEnabledFor(Level.DEBUG)) {
						LOGGER.log(Level.DEBUG, "Metrics Transaction [%d] Completed at time: [%d]", txn.hashCode(), System.currentTimeMillis());
					}

				
					break;

				} catch (FatalException e) {
					postProcess(threadLocalList);
					
					LOGGER.log(Level.ERROR,	"A fatal error occured while processing Metrics Transaction [%d]. Will stop the server.", e, txn.hashCode(), i + 1, 5);
					try {
						txn.rollback();
					} catch (Exception e1) {
					}
					txn.remove();
					
					//initiate a shutdown
					ServiceProviderManager.getInstance().getShutdownService().shutdown();
					
					throw e;
				} catch (Exception e) {
					//unlock
					postProcess(threadLocalList);
					
					LOGGER.log(Level.ERROR,	"Metrics Transaction [%d] Error. Retry attempt [%d]. Will retry in [%d] seconds.", e, txn.hashCode(), i + 1, 5);
					try {
						txn.rollback();
					} catch (Exception e1) {
					}
					txn.clear();
					ex = e;
					Thread.sleep(5000);

				}
			}
			batchSize = threadLocalList.size();
			totJobs = ((AssetBasedParallelMetricServiceImpl)metricService).totalBatchJobs.incrementAndGet();

			if (isSuccess) {
				
//				Bala: Need to expose them as methods on the metric service so can avoid casting
				long totAggs = ((AssetBasedParallelMetricServiceImpl)metricService).totalAggregators.addAndGet(batchSize);
				double avgBatchSize = totAggs/totJobs;
				((AssetBasedParallelMetricServiceImpl)metricService).averageBatchSize.set(avgBatchSize);
				
//				long saveTime = t2 - t1;
				long totTxnListSize = ((AssetBasedParallelMetricServiceImpl)metricService).totalTxnListSize.addAndGet(txn.getTxnList().size());
				long totTxnSaveTime = ((AssetBasedParallelMetricServiceImpl)metricService).totalTxnSaveTime.addAndGet(t2-t1);
				double avgTxnSaveTime = totTxnSaveTime/totJobs;
				((AssetBasedParallelMetricServiceImpl)metricService).avgTxnSaveTime.set(avgTxnSaveTime);
				
				double avgTxnSize = totTxnListSize/totJobs;
				((AssetBasedParallelMetricServiceImpl)metricService).avgTxnSize.set(avgTxnSize);

				metricService.publishTxn(txn);
			} else if (ex != null) {
				LOGGER.log(Level.ERROR, "Metrics Transaction [%s] Error. Maximum retry attempts [%d] reached. Giving up.", ex, txn.hashCode(), transactionRetryCount);
			}
			
		} catch (FatalException e) {
			//This should never get called..
			LOGGER.log(Level.ERROR, "Fatal error occured. Aborting the transaction and stopping Metrics Server.", e, txn.hashCode(), i + 1);
		} catch (Throwable e) {
			//This should never get called..
			LOGGER.log(Level.ERROR, "Metrics Transaction [%s] Error. Giving up after [%d] retries.", e, txn.hashCode(), i + 1);
		} finally {
			if (txn != null) {
				txn.remove();
			}
			
			for(FactWrapper factWrapper : factWrapperList){
				factWrapper.done();
			}
			JOBS.remove();
			MESSAGECONTEXTS.remove();
			
			processedContexts = contextList;
			
			FACTWRAPPERS.remove();
			t4 = System.currentTimeMillis();
			long totMetricThreadExecTime1 = ((AssetBasedParallelMetricServiceImpl)metricService).totalMetricThreadExecTime.addAndGet(t4-t3);
			((AssetBasedParallelMetricServiceImpl)metricService).avgMetricThreadExecTime.set(totMetricThreadExecTime1/totJobs);
		}
		// txn is cleared, so returning it is a no-op
		return txn;
	}

	//synchronized, since there could be multiple threads trying to access this
	synchronized public List<MessageContextWrapper> getProcessedContexts() {
		List<MessageContextWrapper> l = processedContexts;
		//ensure it only returns values only once. subsequent invocations will return empty list.
		processedContexts = new ArrayList<MessageContextWrapper>();
		return l;
	}

	private void postProcess(List<AssetBasedAggregator> threadLocalList) {
		for (AssetBasedAggregator job : threadLocalList) {
			job.postProcess();
		}
	}
}
