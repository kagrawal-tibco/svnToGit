package com.tibco.rta.service.metric;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.tibco.rta.Fact;
import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.common.service.FactMessageContext;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.StickyThreadAllocator;
import com.tibco.rta.common.service.StickyThreadPool;
import com.tibco.rta.common.service.Transaction;
import com.tibco.rta.common.service.TransactionEvent;
import com.tibco.rta.common.service.TransactionEventListener;
import com.tibco.rta.common.service.WorkItem;
import com.tibco.rta.common.service.WorkItemService;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.rta.common.service.impl.StickyThreadPoolImpl;
import com.tibco.rta.impl.MetricKeyImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.query.MetricEventType;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.MetricNodeEvent;
import com.tibco.rta.runtime.model.impl.MetricNodeEventImpl;
import com.tibco.rta.service.om.ObjectManager;
import com.tibco.rta.service.persistence.PersistenceService;
import com.tibco.rta.util.ServiceConstants;


public abstract class AbstractMetricServiceImpl extends AbstractStartStopServiceImpl implements MetricService {

    protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_METRIC.getCategory());
    protected static final Logger LOGGER_DTLS = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_METRIC_DETAILS.getCategory());


    protected ObjectManager omService;

    protected PersistenceService pServ;

    protected int transactionRetryCount = 1;

    protected List<MetricEventListener> metricEventListeners = new ArrayList<MetricEventListener>();

    protected List<TransactionEventListener> transactionEventListeners = new ArrayList<TransactionEventListener>();

    protected List<FactListener<?>> factListeners = new ArrayList<FactListener<?>>();

    protected boolean useParallel;

    protected WorkItemService workItemService;

    protected StickyThreadPool publishTxnPool;
    
    protected StickyThreadPool snapshotRuleEvalPool;

    protected StickyThreadPool stickyThreadPool;

    protected WorkItemService scatterThreadPool;

    protected int batchsize = 1;

    protected long batchFlushPeriodInMillis = 30000;

    protected int clientBatchsize = 1;

    protected boolean isImplicitAssetStatus;

    protected String storeProcessedFacts;

    protected boolean shouldStoreFacts;

    protected boolean processDuplicateFacts;

    protected StickyThreadAllocator stickyThreadAllocator = new HierarchyAllocator();
    
    protected boolean useAssetLocking = false;
	
    protected boolean useSingleLock = false;

    public AbstractMetricServiceImpl() {

    }

    @Override
    public void init(Properties configuration) throws Exception {
        super.init(configuration);
        omService = ServiceProviderManager.getInstance().getObjectManager();
        pServ = ServiceProviderManager.getInstance().getPersistenceService();

        workItemService = ServiceProviderManager.getInstance().getWorkItemService();

        stickyThreadPool = new StickyThreadPoolImpl("metric-compute-thread");
        stickyThreadPool.init(configuration);

        try {
            transactionRetryCount = Integer.parseInt((String) ConfigProperty.RTA_TRANSACTION_RETRY_COUNT.getValue(configuration));
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Configuration: Transaction Retry Count [%d]", transactionRetryCount);
            }
        } catch (Exception e) {
            if (LOGGER.isEnabledFor(Level.WARN)) {
                LOGGER.log(Level.WARN, "Bad Configuration: Using default setting. Transaction Retry Count [%d]", transactionRetryCount);
            }
        }

        try {
            useParallel = Boolean.parseBoolean((String) ConfigProperty.RTA_ASYNC_RULES_EVAL.getValue(configuration));
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Configuration: Use parallel threads for rule evaluations [%s]", useParallel);
            }
        } catch (Exception e) {
            if (LOGGER.isEnabledFor(Level.WARN)) {
                LOGGER.log(Level.WARN, "Bad Configuration: Using default setting. Use parallel threads for rule evaluations [%s]", useParallel);
            }
        }

        try {
            batchsize = Integer.parseInt((String) ConfigProperty.RTA_TRANSACTION_BATCH_SIZE.getValue(configuration));
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Configuration: Server batch size [%d]", batchsize);
            }
        } catch (Exception e) {
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.WARN, "Bad Configuration: Using default setting. Server batch size [%d]", batchsize);
            }
        }

        try {
            clientBatchsize = Integer.parseInt((String) ConfigProperty.RTA_TRANSACTION_CLIENT_BATCH_SIZE.getValue(configuration));
        } catch (Exception e) {
            if (LOGGER.isEnabledFor(Level.WARN)) {
                LOGGER.log(Level.WARN, "Bad Configuration: Using default setting. Client batch size [%d]", clientBatchsize);
            }
        }

        try {
            batchFlushPeriodInMillis = Long.parseLong((String) ConfigProperty.RTA_TRANSACTION_BATCH_FLUSH_PERIOD.getValue(configuration));
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Configuration: Server batch flush period [%d] milliseconds", batchFlushPeriodInMillis);
            }
        } catch (Exception e) {
            if (LOGGER.isEnabledFor(Level.WARN)) {
                LOGGER.log(Level.WARN, "Bad Configuration: Using default. Server batch flush period [%d] milliseconds", batchFlushPeriodInMillis);
            }
        }

        try {
            isImplicitAssetStatus = Boolean.parseBoolean((String) ConfigProperty.RTA_IMPLICIT_ASSET_STATUS.getValue(configuration));
        } catch (Exception e) {
        }

        try {
            storeProcessedFacts = (String) ConfigProperty.RTA_STORE_PROCESSED_FACTS.getValue(configuration);
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Configuration: Store processed facts [%s]", storeProcessedFacts);
            }
        } catch (Exception e) {
            if (LOGGER.isEnabledFor(Level.WARN)) {
                LOGGER.log(Level.WARN, "Bad Configuration: Using default. Store processed facts [%s]", storeProcessedFacts);
            }
        }

        try {
            processDuplicateFacts = Boolean.parseBoolean((String) ConfigProperty.RTA_PROCESS_DUPLICATE_FACTS.getValue(configuration));
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Configuration: Process duplicate facts [%s]", processDuplicateFacts);
            }
        } catch (Exception e) {
            if (LOGGER.isEnabledFor(Level.WARN)) {
                LOGGER.log(Level.WARN, "Bad Configuration: Using default. Process duplicate facts [%s]", processDuplicateFacts);
            }
        }

        try {
            shouldStoreFacts = Boolean.parseBoolean((String) ConfigProperty.RTA_STORE_FACTS.getValue(configuration));
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Configuration: Should store facts [%s]", shouldStoreFacts);
            }
        } catch (Exception e) {
            if (LOGGER.isEnabledFor(Level.WARN)) {
                LOGGER.log(Level.WARN, "Bad Configuration: Using default. Should store facts [%s]", shouldStoreFacts);
            }
        }
        
        try {
            useAssetLocking = Boolean.parseBoolean((String) ConfigProperty.RTA_USE_ASSET_LOCKING.getValue(configuration));
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Configuration: Use asset Locking feature [%s]", useAssetLocking);
            }
        } catch (Exception e) {
            if (LOGGER.isEnabledFor(Level.WARN)) {
                LOGGER.log(Level.WARN, "Bad Configuration: Using default. Use asset Locking feature [%s]", useAssetLocking);
            }
        }
        
        try {
            useSingleLock = Boolean.parseBoolean((String) ConfigProperty.RTA_USE_SINGLE_LOCK.getValue(configuration));
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Configuration: Use single lock feature [%s]", useSingleLock);
            }
        } catch (Exception e) {
            if (LOGGER.isEnabledFor(Level.WARN)) {
                LOGGER.log(Level.WARN, "Bad Configuration: Using default. Use single lock feature [%s]", useSingleLock);
            }
        }

        @SuppressWarnings("unchecked")
//        Map<String, String> config = (Map<String, String>) ServiceProviderManager.getInstance().getConfiguration();

        Properties props = new Properties();ServiceProviderManager.getInstance().getConfiguration();
//        props.putAll(ServiceProviderManager.getInstance().getConfiguration());

        scatterThreadPool = ServiceProviderManager.getInstance().newWorkItemService("scatter-thread");
        scatterThreadPool.init(props);

        publishTxnPool = new StickyThreadPoolImpl("rule-eval-thread");
        publishTxnPool.init(props);
        
        snapshotRuleEvalPool=new StickyThreadPoolImpl("snapshot-rule-eval-thread");
        snapshotRuleEvalPool.init(props);
        
        registerMBean(configuration);

    }

    private void registerMBean(Properties configuration) throws Exception {
    	  MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
    	  String mbeanPrefix = (String) ConfigProperty.BE_TEA_AGENT_SERVICE_MBEANS_PREFIX.getValue(configuration);
          ObjectName name = new ObjectName(mbeanPrefix + ".threadpool:type=" + "metric-thread-pool");
          if (!mbs.isRegistered(name)) {
              mbs.registerMBean(stickyThreadPool, name);
          }
          
          ObjectName ruleObjectName = new ObjectName(mbeanPrefix + ".threadpool:type=" + "rule-eval-thread-pool");
          if (!mbs.isRegistered(ruleObjectName)) {
              mbs.registerMBean(publishTxnPool, ruleObjectName);
          }	
	}

	@Override
    public void start() throws Exception {
        super.start();

    }

    @Override
    public void stop() throws Exception {
    	
    	try {
    		if (scatterThreadPool != null) {
    			scatterThreadPool.stop();
    		}
    	} catch (Exception e) {
    		
    	}
    	
    	try {
    		if (stickyThreadPool != null) {
    			stickyThreadPool.stop();
    		}
    	} catch (Exception e) {
    		
    	}
    	
    	
    	try {
    		if (publishTxnPool != null) {
    			publishTxnPool.stop();
    		}
    	} catch (Exception e) {
    		
    	}
    	
    	try {
    		if (workItemService != null) {
    			workItemService.stop();
    		}
    	} catch (Exception e) {
    		
    	}
    	
    	if (metricEventListeners != null) {
    		metricEventListeners.clear();
    	}
    	
    	if (factListeners != null) {
    		factListeners.clear();
    	}
        super.stop();
        

    }

    @Override
    public void addMetricValueChangeListener(MetricEventListener listener) {
        metricEventListeners.add(listener);
    }

    @Override
    public void removeMetricValueChangeListener(MetricEventListener listener) {
        metricEventListeners.remove(listener);
    }

    @Override
    public void addTransactionContextListener(TransactionEventListener listener) {
        transactionEventListeners.add(listener);
    }

    @Override
    public void removeTransactionContextListener(TransactionEventListener listener) {
        transactionEventListeners.remove(listener);
    }

    @Override
    public void addFactListener(FactListener<?> factListener) throws Exception {
        this.factListeners.add(factListener);
    }

    @Override
    public void removeFactListener(FactListener factListener) {
        factListeners.remove(factListener);
    }

    @Override
    public StickyThreadPool getStickyPool() {
        return stickyThreadPool;
    }

    @Override
    public int getTxnRetryCount() {
        return transactionRetryCount;
    }

    public void publishTxn(Transaction txn) {

        ArrayList<MetricNodeEvent> metricChangeList = new ArrayList<MetricNodeEvent>(txn.getMetricNodeChanges());

        PublishTxnJob job;
        for (MetricNodeEvent evt : metricChangeList) {
            MetricEventType evtType = evt.getEventType();
            MetricNode node = evt.getMetricNode();
            if (useParallel) {
                MetricNode cloned = node.deepCopy();
                evt = new MetricNodeEventImpl(cloned, evtType);
            }
            job = new PublishTxnJob(evt);

            if (useParallel) {
                try {
                    String key = evt.getMetricNode().getKey().toString();
                    publishTxnPool.submit(key, job);
                } catch (Exception e) {
                    LOGGER.log(Level.ERROR, "Error while adding a workitem to publish transaction queue", e);
                }
            } else {
                //on the same thread.
                job.call();
            }
        }

    }
    
    @Override
    public void publishSnapshotRuleJob(MetricNodeEvent evt) {

    	PublishSnapshotRuleJob job;
    	MetricEventType evtType = evt.getEventType();
    	MetricNode node = evt.getMetricNode();
    	if (useParallel) {
    		MetricNode cloned = node.deepCopy();
    		evt = new MetricNodeEventImpl(cloned, evtType);
    	}
    	job = new PublishSnapshotRuleJob(evt);

    	if (useParallel) {
    		try {
    			String key = evt.getMetricNode().getKey().toString();
    			snapshotRuleEvalPool.submit(key, job);
    		} catch (Exception e) {
    			LOGGER.log(Level.ERROR, "Error while adding a workitem to publish transaction to Snapshot rule eval queue", e);
    		}
    	} else {
    		//on the same thread.
    		job.call();
    	}
    }

    @SuppressWarnings("unchecked")
    protected <C extends FactMessageContext> void notifyFacts(C messageContext, Fact fact) {
        for (FactListener factListener : factListeners) {
            try {
                factListener.onFactAsserted(messageContext, fact);
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "Error while notifying fact listener", e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected <C extends FactMessageContext> void notifyFacts(C messageContext, List<Fact> facts) {
        for (FactListener factListener : factListeners) {
            for (Fact fact : facts) {
                try {
                    factListener.onFactAsserted(messageContext, fact);
                } catch (Exception e) {
                    LOGGER.log(Level.ERROR, "Error while notifying fact listener", e);
                }
            }
        }
    }

    public WorkItemService getScatterThreadPool() {
        return scatterThreadPool;
    }


    public int getTxnBatchSize() {
        return batchsize;
    }


    public int getClientTxnBatchSize() {
        return clientBatchsize;
    }

    public long getBatchFlushPeriod() {
        return batchFlushPeriodInMillis;
    }

    public String getStoreProcessedFacts() {
        return storeProcessedFacts;
    }

    public boolean isShouldStoreFacts() {
        return shouldStoreFacts;
    }

    public boolean isProcessDuplicateFacts() {
        return processDuplicateFacts;
    }
    
    @Override
    public boolean useAssetLocking() {
        return useAssetLocking;
    }

    @Override
    public boolean useSingleLock() {
        return useSingleLock;
    }

    @Override
    public <C extends FactMessageContext> TransactionEvent assertFact(C messageContext, List<Fact> facts) throws Exception {
        //no-op
        return null;
    }


    class PublishTxnJob implements WorkItem {

        MetricNodeEvent metricNodeEvent;

        public PublishTxnJob(MetricNodeEvent metricNodeEvent) {
            this.metricNodeEvent = metricNodeEvent;
        }

        @Override
        public Object call() {
            try {
                for (MetricEventListener lsnr : metricEventListeners) {
                    try {
                    	if(metricNodeEvent.getMetricNode().getKey() instanceof MetricKeyImpl){
                    		MetricKeyImpl key=(MetricKeyImpl) metricNodeEvent.getMetricNode().getKey();
                    		RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(key.getSchemaName());
                    		//Note : Do nothing for nodes from bucketed hierarchies , the SnapshotRuleService will take care of it 
                    		if(snapShotRuleEval(schema.getCube(key.getCubeName()).getDimensionHierarchy(key.getDimensionHierarchyName())))
                    			return null;
                    	}
                        lsnr.onValueChange(metricNodeEvent);
                    } catch (Exception e) {
                        LOGGER.log(Level.ERROR, "Error invoking MetricNode change listener", e);
                    }
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "Error while processing publish transaction", e);
            }
            return null;
        }

        @Override
        public Object get() {
            return null;
        }

    }
    
    class PublishSnapshotRuleJob implements WorkItem {

    	MetricNodeEvent metricNodeEvent;

    	public PublishSnapshotRuleJob(MetricNodeEvent metricNodeEvent) {
    		this.metricNodeEvent = metricNodeEvent;
    	}

    	@Override
    	public Object call() {
    		try {
    			ServiceProviderManager.getInstance().getRuleService().getRuleRegistry().processSnapshotRuleJob(metricNodeEvent);
    		} catch (Exception e) {
    			LOGGER.log(Level.ERROR, "Error invoking Snapshot rule job", e);
    		}
    		return null;
    	}
    	@Override
    	public Object get() {
    		return null;
    	}

    }
    	
    

    class HierarchyAllocator implements StickyThreadAllocator {

        protected Map<String, Integer> keyToThread = new HashMap<String, Integer>();
        int currentThreadNo = 0;

        @Override
        synchronized public int allocateThread(int totalThreads, String key) {
            Integer cachedThreadNo = keyToThread.get(key);
            if (cachedThreadNo == null) {
                currentThreadNo = (currentThreadNo + 1) % totalThreads;
                keyToThread.put(key, currentThreadNo);
                return currentThreadNo;
            }
            return cachedThreadNo;
        }
    }
    private boolean snapShotRuleEval(DimensionHierarchy hierarchy) {
    	if(hierarchy!=null)
    		if(hierarchy.getProperty(ServiceConstants.HIERARCHY_SNAPSHOT_RULE_EVAL)!=null 
    		&& !hierarchy.getProperty(ServiceConstants.HIERARCHY_SNAPSHOT_RULE_EVAL).isEmpty()
    		&&hierarchy.getProperty(ServiceConstants.HIERARCHY_SNAPSHOT_RULE_EVAL).equals("true"))
    			return true;
    	return false;
    }
}