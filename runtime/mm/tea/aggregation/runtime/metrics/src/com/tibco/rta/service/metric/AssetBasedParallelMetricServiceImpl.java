package com.tibco.rta.service.metric;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.google.common.util.concurrent.AtomicDouble;
import com.tibco.rta.Fact;
import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.service.FactMessageContext;
import com.tibco.rta.common.service.RtaTransaction;
import com.tibco.rta.common.service.Transaction;
import com.tibco.rta.common.service.TransactionEvent;
import com.tibco.rta.common.service.TransactionEvent.Status;
import com.tibco.rta.common.service.WorkItem;
import com.tibco.rta.impl.FactKeyImpl;
import com.tibco.rta.impl.RecoveredFactImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.TimeDimension;
import com.tibco.rta.model.impl.DimensionHierarchyImpl;



abstract public class AssetBasedParallelMetricServiceImpl extends AbstractMetricServiceImpl implements MetricService, AssetBasedParallelMetricServiceImplMBean {
	
//	 StickyThreadPool stickyThreadPool2;
	
	
	protected AtomicLong totalFacts = new AtomicLong(0);
	
	protected AtomicLong totalTimeForFactTrip = new AtomicLong(0);
    
	protected AtomicLong totalTimeForFactSaving = new AtomicLong(0);
	
	protected AtomicLong firstFactTime = new AtomicLong(0);
	
//	Bala. Facts related metrics
	protected AtomicLong   totalFacts2          = new AtomicLong(0);
	protected AtomicLong   totalFactsPrev2          = new AtomicLong(0);
	protected AtomicLong   totalMsgs           = new AtomicLong();
	protected AtomicLong   totalMsgsPrev           = new AtomicLong();
	protected AtomicDouble avgFactBatchSaveTime = new AtomicDouble();
	protected AtomicDouble avgFactBatchSize     = new AtomicDouble();
	
	
//	Bala: Metric Jobs related metrics
	protected AtomicLong   totalAggregators = new AtomicLong();
	protected AtomicLong   totalBatchJobs   = new AtomicLong();
	protected AtomicDouble averageBatchSize = new AtomicDouble(); //metric batch size
	
	protected AtomicLong   totalTxnListSize = new AtomicLong();
	protected AtomicLong   totalTxnSaveTime = new AtomicLong();

	protected AtomicDouble avgTxnSaveTime   = new AtomicDouble();
	protected AtomicDouble avgTxnSize       = new AtomicDouble();
	
	protected AtomicLong totalWorkerExecTime      = new AtomicLong();
	protected AtomicDouble avgWorkerExecTime      = new AtomicDouble();
	
	protected AtomicLong totalMetricThreadExecTime = new AtomicLong();
	protected AtomicDouble avgMetricThreadExecTime = new AtomicDouble();
	
	protected AtomicDouble msgTps = new AtomicDouble();
	protected AtomicDouble factTps = new AtomicDouble();

	
	Timer flushJobTimer = new Timer("Flush-Job", true);
	
	private boolean rootNodeSplit = false;

    public AssetBasedParallelMetricServiceImpl() {
    	
    }

    @Override
    public void init(Properties configuration) throws Exception {
    	if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Initializing metric service..");
        }
        super.init(configuration);
        rootNodeSplit= Boolean.parseBoolean((String)ConfigProperty.RTA_PARALLEL_ROOT_NODE_COMPUTE.getValue(configuration));
        registerMBean(configuration);
//        stickyThreadPool2 = new StickyThreadPoolImpl("metric-service");
//        stickyThreadPool2.init(configuration);
    	if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Initializing metric service Complete.");
        }
    }

    @Override
    public void start() throws Exception {
    	if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Starting metric service..");
        }
        super.start();
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Starting metric service Complete.");
        }
    }

    @Override
    public void stop() throws Exception {
    	if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Stopping metric service..");
        }
        if (flushJobTimer != null) {
        	flushJobTimer.cancel();
        }
        super.stop();

    	if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Stopping metric service Complete.");
        }
    }

    @Override
    public <C extends FactMessageContext> TransactionEvent assertFact(Fact fact) throws Exception {
        return assertFact(null, fact);
    }
    
//    @Override
//    public <C extends FactMessageContext> TransactionEvent assertFact(Fact fact, boolean isRecovery) throws Exception {
//        return assertFact(null, fact, isRecovery);
//    }
//    
//    @Override
//    public <C extends FactMessageContext> TransactionEvent assertFact(C messageContext, Fact fact) throws Exception {
//    	return assertFact(messageContext, fact, false);
//    }

    @Override
    public <C extends FactMessageContext> TransactionEvent assertFact(C messageContext, Fact fact) throws Exception 
    {
        TransactionEvent transactionEvent = new TransactionEvent(((FactKeyImpl)fact.getKey()).getUid());
        if (isStarted()) {
        	FactWorkItem<C> factWorkItem = null;
        	if (fact instanceof RecoveredFactImpl) {
        		factWorkItem = new RecoveryWorkItem<C>(fact, messageContext, transactionEvent);
        	} else {
        		factWorkItem = new FactWorkItem<C>(fact, messageContext, transactionEvent);
        	}
            workItemService.addWorkItem(factWorkItem);
            //this ensures that the fact.save and submit for processing to other Qs is complete.
            return transactionEvent;
        } else {
            if (LOGGER.isEnabledFor(Level.WARN)) {
                LOGGER.log(Level.WARN, "Service %s is not yet started", serviceName);
            }
            transactionEvent.setStatus(Status.FAILURE);
            return transactionEvent;
        }
    }

    private class RecoveryWorkItem<C extends FactMessageContext> extends FactWorkItem {

		RecoveryWorkItem(Fact fact, C messageContext,
				TransactionEvent transactionEvent) {
			super(fact, messageContext, transactionEvent);
			// TODO Auto-generated constructor stub
			LOGGER.log(Level.WARN,"Recovered fact " + fact.getKey() + " ,hc: " + fact.hashCode());
		}
		
        @Override
        public TransactionEvent call() throws Exception {
        	super.notifyAndProcessFact();
        	return transactionEvent;
        }
    	
    }
    /**
     * Workitem for fact assertion.
     */
    private class FactWorkItem<C extends FactMessageContext> implements WorkItem<TransactionEvent> {

    	protected Fact fact;

    	protected C messageContext;

    	protected TransactionEvent transactionEvent;
        
        FactWorkItem(Fact fact, C messageContext, TransactionEvent transactionEvent) {
            this.fact = fact;
            this.messageContext = messageContext;
            this.transactionEvent = transactionEvent;
        }

        @Override
        public TransactionEvent call() throws Exception {


        	boolean success = false;
            Transaction txn = null;
            
            try {

                //tranRetry<0 --> infinite retry..
                for (int i = 0; i < transactionRetryCount || transactionRetryCount < 0; i++) {
                    try {
                        if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    	    LOGGER.log(Level.DEBUG, "Fact received : %s", fact.getKey());
                        }
                    	long t1 = System.currentTimeMillis();
                    	txn = RtaTransaction.get();
                    	txn.beginTransaction();

                    	omService.save(fact);
                    	txn.commit();
                        long t2 = System.currentTimeMillis();
                        if (LOGGER.isEnabledFor(Level.DEBUG)) {
                            LOGGER.log(Level.DEBUG, "Saving Fact is complete: %s, Time %d", fact.getKey(), (t2 - t1));
                        }
                    	success = true;
                        break;
                    } catch (Exception e) {
                        txn.rollback();
                        LOGGER.log(Level.ERROR, "", e);
                        Thread.sleep(1000);
                    } finally {
                        if (txn != null) {
                            txn.clear();
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "", e);
            } finally {
            	if (txn != null) {
            		txn.remove();
            	}
            }
            
            if (success) {
            	notifyAndProcessFact();
            }

            return transactionEvent;
        }

		protected void notifyAndProcessFact() throws Exception,	InterruptedException {
			//notify other listeners for fact publish events.
			notifyFacts(messageContext, fact);
			//this one distributes the work to various sticky threads.
			processFact();
		}

 
		@SuppressWarnings("unused")
		protected void processFact() throws Exception, InterruptedException {
			
			try {
				List<Future<Transaction>> fList = new ArrayList<Future<Transaction>>();
				
				RtaSchema schema = fact.getOwnerSchema();
				for (Cube cube : schema.getCubes()) {
					for (DimensionHierarchy hierarchy : cube.getDimensionHierarchies()) {
						if (!hierarchy.isEnabled()) {
							continue;
						}

						//this check is related to the recovery logic. whether to process this DH
						if (!shouldProcessFactForHierarchy(fact, hierarchy)) {
							continue;
						}
						
						AssetBasedAggregator agg = null;
						if (!DimensionHierarchyImpl.isAssetHierarchy(hierarchy)) {
							if (isAssetFact(fact)) { //feed asset deletes to regular hrs.
								agg = new AssetBasedAggregator(fact, hierarchy);
							} else if (validateFactForHierarchy(hierarchy)) {
								agg = new AssetBasedAggregator(fact, hierarchy);
							} else {
								agg = new AssetBasedNoOpAggregator(fact, hierarchy);							
							}
						} else {
							//TODO: BALA THIS NEEDS SOME WORK.!!
//							if (isAssetFact(fact)) {
//								agg = new CopyOfAssetStatusAggregator(fact, hierarchy);
//							} else if (isImplicitAssetStatus) { // derive implicit only if set. default is not to set.
//								agg = new CopyOfAssetStatusAggregator(fact, hierarchy);
//							} else {
//								agg = new AssetBasedNoOpAggregator(fact, hierarchy);
//							}
						}
						if (agg != null) {
							agg.initAggregator();
							AssetBasedMetricComputeJob job = null;
//DONT BATCH HERE anymore. moved to BatchedParallelMetricServiceImpl
//							if (batchsize > 1) {
//								job = new BatchedMetricComputeJob(ParallelMetricServiceImpl.this, agg);
//							} else {
								job = new AssetBasedMetricComputeJob(AssetBasedParallelMetricServiceImpl.this, agg, true);
//							}
							
							String key = schema.getName() + cube.getName() + hierarchy.getName();
//							if (!hierarchy.computeRoot()) {
//								//further parallelize it by adding the value of the 0th dimension to the key, thereby making it more unique
//								//increasing its chances of executing on another thread.
//								String zeroDimVal = getZeroDimValue(fact, hierarchy);
//								key += zeroDimVal;
//							}
							
							Future<Transaction> f = stickyThreadPool.submit(key, job, stickyThreadAllocator);
						}
					}
				}
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, "Exception during fact processing", e);
			}
		}

		protected boolean validateFactForHierarchy(DimensionHierarchy hierarchy) {
			boolean isValid = true;
			StringBuilder b = new StringBuilder();
			for (Dimension dimName : hierarchy.getDimensions()) {
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
							(hierarchy.getOwnerSchema().getName() + "/" + hierarchy.getOwnerCube().getName() + "/" + hierarchy.getName()),
							b.toString());
				}
			}
			return isValid;
		}

		@Override
		public TransactionEvent get() {
			// TODO Auto-generated method stub
			return null;
		}
    }
    
	protected boolean isAssetFact(Fact fact) {
		if(fact.getAttribute(Fact.ASSET_STATUS) != null) {
			return true;
		}
		return false;
	}

	
	//more parallelization by adding all the non compute dimensions and the first compute dimension
	
	protected String getZeroDimValue(Fact fact, DimensionHierarchy hierarchy) {
		if (rootNodeSplit) {
			String zeroDimension = new String("");
			int depth = hierarchy.getDepth();

			for (int level = 0; level <= depth - 1; level++) {
				if (!hierarchy.getComputeForLevel(level)) {
					zeroDimension = getDimValue(fact, hierarchy, zeroDimension, level);
				} else {
					zeroDimension = getDimValue(fact, hierarchy, zeroDimension, level);
					break;
				}
			}
			return zeroDimension;
		} else
			return "";
	}

	private String getDimValue(Fact fact, DimensionHierarchy hierarchy, String zeroDimension, int level) {
		Dimension d = hierarchy.getDimension(level);
		String attrName = d.getAssociatedAttribute().getName();
		Object value = fact.getAttribute(attrName);
		if (d instanceof TimeDimension) {
			TimeDimension td = (TimeDimension) d;
			long timestamp = 0;
			if (value == null) {
				timestamp = System.currentTimeMillis();
			} else if (value instanceof Long) {
				timestamp = (Long) value;
			}
			value = td.getTimeUnit().getTimeDimensionValue(timestamp);
		}
		if (value != null) {
			zeroDimension += value;
		} else {
			zeroDimension += "null";
		}
		return zeroDimension;
	}

	public boolean shouldProcessFactForHierarchy(Fact fact, DimensionHierarchy hierarchy) {
		
		if (fact instanceof RecoveredFactImpl) {
			RecoveredFactImpl recoveredFact = (RecoveredFactImpl) fact;
			for (DimensionHierarchy dh : recoveredFact.getUnProcessedHierarchies()) {
				if (dh.getOwnerCube().getName().equals(hierarchy.getOwnerCube().getName()) &&
					dh.getName().equals(hierarchy.getName())) {
					return true;
				}
			}
			return false;
		}
		return true;
	}
	

	class FactWrapper {
		List<Fact> facts;
		AtomicInteger counter = new AtomicInteger(0);
		long createdTime;

		public FactWrapper(List<Fact> facts) {
			this.facts = facts;
			this.createdTime = System.currentTimeMillis();
		}

		public void incrementCount() {
			counter.incrementAndGet();
		}

		public void done() {
			if (counter.decrementAndGet() == 0) {
				totalTimeForFactTrip.addAndGet(System.currentTimeMillis() - createdTime);
				totalFacts.addAndGet(facts.size());
			}
		}
	}

	@Override
	public long getTotalFactsProcessed() {
		return totalFacts.get();
	}


	private void registerMBean(Properties configuration) throws Exception {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		String mbeanPrefix = (String) ConfigProperty.BE_TEA_AGENT_SERVICE_MBEANS_PREFIX.getValue(configuration);
		ObjectName name = new ObjectName(mbeanPrefix + ".metric:type=MetricService");
		if (!mbs.isRegistered(name)) {
			mbs.registerMBean(this, name);
		}
	}

	@Override
	public double getAvgFactProcessTimeInMilliSeconds() {
		//to avoid % by 0
		long totFacts = totalFacts.get();
		if (totFacts > 0) {
			return (double) totalTimeForFactTrip.get() / totFacts;
		} else {
			return 0;
		}
	}

	/*@Override
	public double getAvgFactSaveTimeInMilliSeconds() {
		if (totalFacts.get() != 0) {
			return (double) totalTimeForFactSaving.get() / totalFacts.get();
		}
		return 0;

	} */

	@Override
	public double getFactPerSecond() {
		//to avoid % by 0
		long t1 = firstFactTime.get();
		if (t1 > 0) {
			return (double) totalFacts.get() * 1000 / (System.currentTimeMillis() - t1);
		} else {
			return 0;
		}
	}

	@Override
	public double getAvgMetricJobsBatchSize() {
		return averageBatchSize.doubleValue();
	}

	@Override
	public double getAvgFactBatchSize() {
		return avgFactBatchSize.doubleValue();
		}

	@Override
	public double getAvgFactBatchSaveTimeInMilliSeconds() {
		//to avoid % by 0
		long totFacts = totalFacts.get();
		if (totFacts != 0) {
			return (double) totalTimeForFactSaving.get() / totFacts;
		} else {
		return 0;
		}
	}

	@Override
	public long getTotalTransactions() {
		return totalBatchJobs.longValue();
	}
	
	@Override
	public long getTotalTransactionListSize() {
		return totalTxnListSize.longValue();
	}

	@Override
	public long getTotalTxnSaveTimeInMilliSeconds() {
		return totalTxnSaveTime.longValue();
	}

	@Override
	public double getAvgTxnSaveTimeInMilliSeconds() {
		return avgTxnSaveTime.doubleValue();
	}

	@Override
	public double getAvgTxnSize() {
		return avgTxnSize.doubleValue();
	}
	

	@Override
	public double getAvgWorkerThreadExecTimeInMilliSeconds() {
		return avgWorkerExecTime.doubleValue();
	}

	@Override
	public double getAvgMetricThreadExecTimeInMilliSeconds() {
		return avgMetricThreadExecTime.doubleValue();
		}
	
	//yamini
	@Override
	public double  getTxnSavePercentTime() {
		//to avoid % by 0
		double avgExecTm = avgMetricThreadExecTime.doubleValue();
		if (avgExecTm > 0) {
			return (avgTxnSaveTime.doubleValue() * 100 / avgExecTm);
		} else {
		return 0;
		}
	}
	
	@Override
	public double getMessageTps(){
		return msgTps.doubleValue();
		
	}
	@Override
	public double getFactTps(){
		return factTps.doubleValue();
		
	}
	@Override
	public void resetMetricMetricsCumulative(){
		totalAggregators.set(0);
		totalBatchJobs.set(0);
		averageBatchSize.set(0);
		totalTxnListSize.set(0);
		totalTxnSaveTime.set(0);
		avgTxnSize.set(0);
		avgTxnSaveTime.set(0);
		avgMetricThreadExecTime.set(0);
	}
	
	public void resetMetricMetrics() {
		
		//this reset should happen with a copy of the variables to be able to have cumulative metrics available from the start of time
		totalAggregators.set(0);
		totalBatchJobs.set(0);
		averageBatchSize.set(0);
		totalTxnListSize.set(0);
		totalTxnSaveTime.set(0);
		avgTxnSize.set(0);
		avgTxnSaveTime.set(0);
		avgMetricThreadExecTime.set(0);
	}
	
	@Override
	public void resetFactsMetricsCumulative(){
		totalFacts2.set(0);         
		totalMsgs.set(0);          
		avgFactBatchSaveTime.set(0);
		avgFactBatchSize.set(0);
		avgWorkerExecTime.set(0);
	}
	
	//@Override
	public void resetFactsMetrics() {
		//this reset should happen with a copy of the variables to be able to have cumulative metrics available from the start of time
		totalFacts2.set(0);         
		totalMsgs.set(0);          
		avgFactBatchSaveTime.set(0);
		avgFactBatchSize.set(0);
		avgWorkerExecTime.set(0);
	}
}



