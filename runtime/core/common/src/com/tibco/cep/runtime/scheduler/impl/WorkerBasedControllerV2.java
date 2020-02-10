/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.scheduler.impl;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.tibco.cep.kernel.core.rete.BeTransaction;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.Channel.Destination;
import com.tibco.cep.runtime.channel.PayloadValidationHelper;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.channel.impl.DefaultSerializationContext;
import com.tibco.cep.runtime.management.impl.metrics.Constants;
import com.tibco.cep.runtime.model.event.EventContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;
import com.tibco.cep.runtime.scheduler.DispatcherThreadPool;
import com.tibco.cep.runtime.scheduler.TaskController;
import com.tibco.cep.runtime.scheduler.impl.HashedDispatcherThreadPoolImpl.HashProvider;
import com.tibco.cep.runtime.service.basic.AsyncWorkerServiceWatcher;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.om.DistributedCacheBasedStore;
import com.tibco.cep.runtime.service.cluster.om.ObjectManagerInfo;
import com.tibco.cep.runtime.service.cluster.om.ServiceInfo;
import com.tibco.cep.runtime.service.cluster.om.ServiceMemberInfo;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionConfig;
import com.tibco.cep.runtime.session.impl.ProcessingContextImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.runtime.util.FQName;
import com.tibco.cep.runtime.util.ProcessInfo;
import com.tibco.cep.runtime.util.SystemProperty;

/*
* Author: Ashwin Jayaprakash Date: Mar 12, 2009 Time: 6:51:13 PM
*/
public class WorkerBasedControllerV2 extends DefaultTaskController implements TaskController {
    /**
     * {@value}
     */
    public static final String PROPERTY_NUM_THREADS =
            WorkerBasedController.PROPERTY_NUM_THREADS;

    /**
     * {@value}
     */
    public static final String PROPERTY_QUEUE_SIZE = WorkerBasedController.PROPERTY_QUEUE_SIZE;

    /**
     * {@value}
     */
    public static final String DEFAULT_POOL_NAME = WorkerBasedController.DEFAULT_POOL_NAME;
    
    public static final String DEFAULT_HASHED_POOL_NAME = "$default.be.affinity.mt$";

    protected DispatcherThreadPool defaultExecutor;
    
    protected DispatcherThreadPool defaultHashedExecutor;

    protected Logger logger;

    protected FQName fqn;

    protected HashMap<String, DispatcherThreadPool> destinationAndExecutors;

    protected HashSet<DispatcherThreadPool> allExecutors;

    protected AtomicLong lastNumJobsTimeMillis;

    protected AtomicLong lastNumJobs;

    protected AtomicLong lastJobRatePerSecAsDouble;

    public WorkerBasedControllerV2(RuleSession session) {
        super(session);

        RuleServiceProvider rsp = session.getRuleServiceProvider();

        this.logger = rsp.getLogger(WorkerBasedControllerV2.class);
        this.destinationAndExecutors = new HashMap<String, DispatcherThreadPool>();
        this.allExecutors = new HashSet<DispatcherThreadPool>();

        this.lastNumJobsTimeMillis = new AtomicLong();
        this.lastNumJobs = new AtomicLong();
        this.lastJobRatePerSecAsDouble = new AtomicLong();

        //------------------

        Properties properties = this.ruleSession.getRuleServiceProvider().getProperties();

        String s = properties.getProperty(PROPERTY_NUM_THREADS, "10").trim();
        int numWorkers = Integer.parseInt(s);

        s = properties.getProperty(PROPERTY_QUEUE_SIZE, "1024");
        int queueSize = Integer.parseInt(s);

        //------------------

        this.defaultExecutor =
                DispatcherThreadPoolImpl.create(DEFAULT_POOL_NAME, numWorkers, queueSize, rsp);
        this.allExecutors.add(this.defaultExecutor);

        this.logger.log(Level.INFO,"Initialized thread pool [" + DEFAULT_POOL_NAME +
                "] with max [" + numWorkers + "] threads and max [" + queueSize +
                "] job queue size.");

        //------------------

        RuleSessionConfig.InputDestinationConfig[] dests =
                this.ruleSession.getConfig().getInputDestinations();

        for (int i = 0; i < dests.length; i++) {
			int numWorker = dests[i].getNumWorker();
			RuleSessionConfig.ThreadingModel threadingModel = dests[i]
					.getThreadingModel();
			RuleFunction affinityRuleFunction = dests[i].getThreadAffinityRuleFunction();
            switch (threadingModel) {
                case CALLER:
                    break;
                case SHARED_QUEUE:
                    if(affinityRuleFunction == null) {
	                	this.destinationAndExecutors.put(dests[i].getURI(),
	                            this.defaultExecutor);
                    } else {
                    	if(this.defaultHashedExecutor == null) {
                    		this.defaultHashedExecutor = 
                            		new HashedDispatcherThreadPoolImpl(DEFAULT_HASHED_POOL_NAME, numWorkers, queueSize, rsp, new WorkerTaskHashProvider());
                            this.allExecutors.add(this.defaultHashedExecutor);
                            
                            this.logger.log(Level.INFO,"Initialized thread pool [" + DEFAULT_HASHED_POOL_NAME +
                                    "] with max [" + numWorkers + "] threads and max [" + queueSize +
                                    "] job queue size.");
                    	}
                    	
                    	this.destinationAndExecutors.put(dests[i].getURI(),
                    			this.defaultHashedExecutor);
                    }
                    break;                    
                case WORKERS:
				if (numWorker == 0) {
					if(affinityRuleFunction == null) {
	                	this.destinationAndExecutors.put(dests[i].getURI(),
	                            this.defaultExecutor);
                    } else {
                    	if(this.defaultHashedExecutor == null) {
                    		this.defaultHashedExecutor = 
                            		new HashedDispatcherThreadPoolImpl(DEFAULT_HASHED_POOL_NAME, numWorkers, queueSize, rsp, new WorkerTaskHashProvider());
                            this.allExecutors.add(this.defaultHashedExecutor);
                            
                            this.logger.log(Level.INFO,"Initialized thread pool [" + DEFAULT_HASHED_POOL_NAME +
                                    "] with max [" + numWorkers + "] threads and max [" + queueSize +
                                    "] job queue size.");
                    	}
                    	
                    	this.destinationAndExecutors.put(dests[i].getURI(),
                    			this.defaultHashedExecutor);
                    }
				} else if (numWorker > 0) {
					String name = dests[i].getURI();
					queueSize = (dests[i].getQueueSize() <= 0) ? queueSize : dests[i].getQueueSize();

					DispatcherThreadPool executor = null;
					if (affinityRuleFunction == null) {
						executor = DispatcherThreadPoolImpl.create(name,
								numWorker, queueSize, rsp);
					} else {
						executor = new HashedDispatcherThreadPoolImpl(name,
								numWorker, queueSize, rsp, new WorkerTaskHashProvider());
					}

					this.destinationAndExecutors.put(name, executor);
					this.allExecutors.add(executor);

					this.logger.log(Level.INFO, "Initialized thread pool ["
							+ name + "] with max [" + numWorker
							+ "] threads and max [" + queueSize
							+ "] job queue size.");
				}
				// No dispatcher, use the client's thread.
				else {
				}
			} 
				// in case of ThreadingModel.CALLER No dispatcher, use the client's thread.
		}

        // add default dispatcher to the map for bebw callback rulefunction
        this.destinationAndExecutors.put(DEFAULT_POOL_NAME, defaultExecutor);
    }

    protected void registerExecutorMBeans(String agentId) {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        for (DispatcherThreadPool executor : allExecutors) {
            String objectName =
                    "com.tibco.be:type=Agent,agentId=" + agentId +
                            ",subType=ThreadPool,threadPoolId=" +
                            executor.getDestinationName();

            try {
                ObjectName name = new ObjectName(objectName);
                if(!mbs.isRegistered(name))
                    mbs.registerMBean(executor, name);
            }
            catch (Exception e) {
                logger.log(Level.WARN,"Error occurred while registering MBean for [" + objectName + "]",
                        e);
            }
        }
    }

    protected FQName getFQName() {
        if (fqn != null) {
            return fqn;
        }

        if (ruleSession.getObjectManager() instanceof DistributedCacheBasedStore) {
            CacheAgent ia =
                    ((DistributedCacheBasedStore) ruleSession.getObjectManager()).getCacheAgent();

            fqn = new FQName(ia.getCluster().getClusterName(),
                    ProcessInfo.getProcessIdentifier(),
                    ia.getAgentName(),
                    ia.getAgentId() + "",
                    getClass().getSimpleName());
        }
        else {
            fqn = new FQName(ProcessInfo.getProcessIdentifier(),
                    ruleSession.getName(),
                    getClass().getSimpleName());
        }

        return fqn;
    }

    protected String getName() {
        return ruleSession.getName();
    }

    public double getJobRate() {
        long prevTSMillis = lastNumJobsTimeMillis.get();
        long prevNumJobs = lastNumJobs.get();
        long prevJobRateAsLongBits = lastJobRatePerSecAsDouble.get();

        long currTimeMillis = System.currentTimeMillis();
        long diffTSMillis = (currTimeMillis - prevTSMillis);
        if (diffTSMillis < 1000) {
            return Double.longBitsToDouble(prevJobRateAsLongBits);
        }

        long currNumJobs = getNumJobsProcessed();
        long diffNumJobs = (currNumJobs - prevNumJobs);
        double result = diffNumJobs / (diffTSMillis / 1000.0);

        //Commit values.
        lastNumJobs.weakCompareAndSet(prevNumJobs, currNumJobs);
        lastNumJobsTimeMillis.weakCompareAndSet(prevTSMillis, currTimeMillis);
        lastJobRatePerSecAsDouble
                .weakCompareAndSet(prevJobRateAsLongBits, Double.doubleToLongBits(result));

        return result;
    }

    public long getNumJobsProcessed() {
        long total = 0;

        for (DispatcherThreadPool executor : allExecutors) {
            total = total + executor.getCompletedTaskCount();
        }

        return total;
    }

    public void start() {
        String clusterName = Constants.KEY_TEMPLATE_CLUSTER_NAME;
        String pid = ProcessInfo.getProcessIdentifier();
        String agentName = "session-" + ruleSession.getName();
        String agentId = System.identityHashCode(ruleSession) + "";

        ObjectManager objectManager = ruleSession.getObjectManager();
        if (objectManager instanceof ObjectManagerInfo) {
            ObjectManagerInfo managerInfo = (ObjectManagerInfo) objectManager;
            ServiceInfo serviceInfo = managerInfo.getServiceInfo();
            ServiceMemberInfo memberInfo = serviceInfo.getLocalMemberInfo();

            clusterName = serviceInfo.getName();
            agentName = memberInfo.getName();
            agentId = memberInfo.getUniqueId() + "";
        }

        for (DispatcherThreadPool executor : allExecutors) {
            FQName executorFQN = new FQName(clusterName, pid, agentName, agentId,
                    AsyncWorkerServiceWatcher.AsyncWorkerService.class.getSimpleName(),
                    executor.getDestinationName());

            executor.start(executorFQN);
        }

        registerExecutorMBeans(agentId);
    }

    public int threadCount() {
        int total = 0;

        for (DispatcherThreadPool executor : allExecutors) {
            total = total + executor.getPoolSize();
        }

        return total;
    }

    synchronized public void shutdown() {
        for (DispatcherThreadPool executor : allExecutors) {
            executor.shutdown();
        }

        for (DispatcherThreadPool executor : allExecutors) {
            try {
                executor.awaitTermination(Integer.getInteger(SystemProperty.THREADPOOL_SHUTDOWN_TIMEOUT.getPropertyName(), 5), TimeUnit.SECONDS);
            }
            catch (InterruptedException e) {
            }
        }
    }

    synchronized public boolean isRunning() {
        for (DispatcherThreadPool executor : allExecutors) {
            if (executor.isTerminated() == false && executor.isSuspended() == false) {
                return true;
            }
        }

        return false;
    }

    public void resume() {
        for (DispatcherThreadPool executor : allExecutors) {
            executor.resume();
        }
    }

    public void suspend() {
        for (DispatcherThreadPool executor : allExecutors) {
            executor.suspend();
        }
    }

    void superProcessEvent(final Channel.Destination dest, final SimpleEvent event, final EventContext ctx)
            throws Exception {
        super.processEvent(dest, event, ctx);
    }

    public void processEvent(final Channel.Destination dest, final SimpleEvent event, final EventContext ctx)
            throws Exception {
        DispatcherThreadPool executor = destinationAndExecutors.get(dest.getURI());
        if (executor == null) {
            new BeTransaction("RTC-" + generateTxnName(dest, event)) {
                @Override
                protected void doTxnWork()  {
                    cleanup(ruleSession);

                    try {
                        superProcessEvent(dest, event, ctx);
                    }
                    catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }.execute();

            return;
        }

        SerializationContext sci = new DefaultSerializationContext(ruleSession, dest);

        executor.execute(new WorkerTask(dest, event, ctx, sci));
    }

    protected static String generateTxnName(Destination dest, SimpleEvent event) {
        return (dest == null ? "" : dest.getURI()) + "-" + (event == null ? "" : event.getId() + "");
    }

    public void processTask(String dispatcherName, Runnable task) throws Exception {
        DispatcherThreadPool executor = destinationAndExecutors.get(dispatcherName);
        if (executor == null) {
            super.processTask(dispatcherName, task);

            return;
        }

        executor.execute(task);
    }

    protected void executeTask_Orig(SimpleEvent event, EventContext context, Channel.Destination dest,
                               SerializationContext sci) {
        try {
            if (event == null) {
                //try deserializing the event
                if (dest.getEventSerializer() != null) {
                    event = dest.getEventSerializer().deserialize(context.getMessage(), sci);
                    if (event != null) {
                        event.setContext(context);
                        ((SimpleEventImpl) event).setDestinationURI(dest.getURI());
                        if (PayloadValidationHelper.ENABLED) {
                            PayloadValidationHelper.validate(event, sci);
                        }
                    }
                }
            }

            if (event != null) {
                RuleFunction preprocessor = sci.getDeployedDestinationConfig().getPreprocessor();
                if (preprocessor != null) {
                    ((RuleSessionImpl) ruleSession).preprocessPassthru(preprocessor, event);
                }
                else {
                    ruleSession.assertObject(event, true);
                }
            }
        }
        catch (Exception ex) {
            logger.log(Level.ERROR, ex, ex.getMessage());
        }
    }

    protected void executeTask(SimpleEvent event, EventContext context, Channel.Destination dest,
                               SerializationContext sci) {
        try {
            final long start = System.currentTimeMillis();

            if (event == null) {
                //try deserializing the event
                if (dest.getEventSerializer() != null) {
                    event = dest.getEventSerializer().deserialize(context.getMessage(), sci);
                    if (event != null) {
                        event.setContext(context);
                        ((SimpleEventImpl) event).setDestinationURI(dest.getURI());
                        if (PayloadValidationHelper.ENABLED) {
                            PayloadValidationHelper.validate(event, sci);
                        }
                    }
                }
            }

            if (event != null) {
                final RuleFunction preprocessor = sci.getDeployedDestinationConfig().getPreprocessor();
                final SimpleEvent triggerEvent = event;
                final Destination sourceDestination = dest;

                new BeTransaction("RTC-" + generateTxnName(dest, event)) {
                    @Override
                    protected void doTxnWork()  {
                    	try {
	                        ruleSession.getProcessingContextProvider().setProcessingContext(
	                                new ProcessingContextImpl(ruleSession, sourceDestination, start, triggerEvent));
	
	                        cleanup(ruleSession);
	
	                        if (preprocessor != null) {
	                            ((RuleSessionImpl) ruleSession).preprocessPassthru(preprocessor, triggerEvent);
	                        }
	                        else {
	                            try {
	                                ruleSession.assertObject(triggerEvent, true);
	                            }
	                            catch (DuplicateExtIdException e) {
	                                throw new RuntimeException(e);
	                            }
	                        }
                    	} finally {
                    		ruleSession.getProcessingContextProvider().setProcessingContext(null);
                    	}
                    }
                }.execute();
            }
        }
        catch (Exception ex) {
            logger.log(Level.ERROR, ex, ex.getMessage());
        }
    }

    //-----------

    protected class WorkerTask implements Runnable {
        protected SimpleEvent m_event;

        protected EventContext m_ctx;

        protected Channel.Destination m_dest;

        protected SerializationContext m_sci;

        protected WorkerTask(Channel.Destination dest, SimpleEvent event, EventContext ctx,
                             SerializationContext sci) {
            this.m_dest = dest;
            this.m_event = event;
            this.m_ctx = ctx;
            this.m_sci = sci;
        }

        public void run() {
            WorkerBasedControllerV2.this.executeTask(m_event, m_ctx, m_dest, m_sci);
        }
    }
    
	protected class WorkerTaskHashProvider implements HashProvider {

		@Override
		public int getHash(Object o) {
			WorkerTask workerTask = (WorkerTask) o;
			RuleFunction threadAffinityRuleFunction = workerTask.m_sci
					.getDeployedDestinationConfig()
					.getThreadAffinityRuleFunction();
			try {
				if (workerTask.m_event == null) {
					if (workerTask.m_dest.getEventSerializer() != null) {
						workerTask.m_event = workerTask.m_dest
								.getEventSerializer().deserialize(
										workerTask.m_ctx.getMessage(),
										workerTask.m_sci);

						if (workerTask.m_event != null) {
							workerTask.m_event.setContext(workerTask.m_ctx);
							((SimpleEventImpl) workerTask.m_event)
									.setDestinationURI(workerTask.m_dest
											.getURI());
							if (PayloadValidationHelper.ENABLED) {
								PayloadValidationHelper.validate(
										workerTask.m_event, workerTask.m_sci);

							}
						}
					}
				}
				Object returnValue = threadAffinityRuleFunction
						.invoke(new Object[] { workerTask.m_event });
				return returnValue.toString().hashCode();
			} catch (Exception ex) {
				logger.log(Level.ERROR, ex, ex.getMessage());
				throw new RuntimeException(
						"hash calculation using thread affinity function '"
								+ threadAffinityRuleFunction
								+ "' failed. cannot process this event "
								+ workerTask.m_ctx.getMessage(), ex);
			}
		}

	}
}
