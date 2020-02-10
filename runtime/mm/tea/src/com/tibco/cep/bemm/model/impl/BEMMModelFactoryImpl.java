package com.tibco.cep.bemm.model.impl;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.AgentConfig;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.BEMMModelFactory;
import com.tibco.cep.bemm.model.BERuntimeVariable;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.LocalCache;
import com.tibco.cep.bemm.model.ProcessingUnit;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.model.SharedQueue;
import com.tibco.cep.bemm.model.Summary;

/**
 * Implementation of functionality defined in BETeaAgentModelFactory
 * 
 * @author dijadhav
 *
 */
public class BEMMModelFactoryImpl implements BEMMModelFactory {
	private static final String APPLICATION = "com.tibco.cep.bemm.model.impl.ApplicationImpl";
	private static final String HOST = "com.tibco.cep.bemm.model.impl.HostImpl";
	private static final String SERVICE_INSTANCE = "com.tibco.cep.bemm.model.impl.ServiceInstanceImpl";
	private static final String PROCESSING_UNIT = "com.tibco.cep.bemm.model.impl.ProcessingUnitImpl";
	private static final String AGENT_CONFIG = "com.tibco.cep.bemm.model.impl.AgentConfigImpl";
	private static final String BE_RUNTTIME_VARIABLE = "com.tibco.cep.bemm.model.impl.BERuntimeVariableImpl";
	private static final String AGENT = "com.tibco.cep.bemm.model.impl.AgentImpl";
	private static final String SUMMARY = "com.tibco.cep.bemm.model.impl.SummaryImpl";
	private static final String LOCAL_CACHEE = "com.tibco.cep.bemm.model.impl.LocalCacheImpl";
	private static final String SHARED_QUEUE = "com.tibco.cep.bemm.model.impl.SharedQueueImpl";
	/**
	 * Instance of type BETeaAgentModelFactory
	 */
	private static BEMMModelFactory INSTANCE;

	/**
	 * Private constructor
	 */
	private BEMMModelFactoryImpl() {

	}

	/**
	 * Initialize and return instance of type BETeaAgentModelFactory
	 * 
	 * @return Instance of type BETeaAgentModelFactory
	 */
	public synchronized static BEMMModelFactory getInstance() {
		if (null == INSTANCE) {
			synchronized (BEMMModelFactoryImpl.class) {
				INSTANCE = new BEMMModelFactoryImpl();
			}
		}
		return INSTANCE;
	}

	@Override
	public Agent getAgent() throws ObjectCreationException {
		return (Agent) ManagementUtil.getInstance(AGENT);
	}

	@Override
	public Application getApplication() throws ObjectCreationException {
		return (Application) ManagementUtil.getInstance(APPLICATION);
	}

	@Override
	public Host getHost() throws ObjectCreationException {
		return (Host) ManagementUtil.getInstance(HOST);
	}

	@Override
	public ServiceInstance getServiceInstance() throws ObjectCreationException {
		return (ServiceInstance) ManagementUtil.getInstance(SERVICE_INSTANCE);
	}

	@Override
	public BERuntimeVariable getBERuntimeVariable() throws ObjectCreationException {
		return (BERuntimeVariable) ManagementUtil.getInstance(BE_RUNTTIME_VARIABLE);
	}

	@Override
	public ProcessingUnit getProcessingUnit() throws ObjectCreationException {
		return (ProcessingUnit) ManagementUtil.getInstance(PROCESSING_UNIT);
	}

	@Override
	public AgentConfig getAgentConfig() throws ObjectCreationException {
		return (AgentConfig) ManagementUtil.getInstance(AGENT_CONFIG);
	}

	@Override
	public Summary getSummary() throws ObjectCreationException {
		return (Summary) ManagementUtil.getInstance(SUMMARY);
	}

	@Override
	public LocalCache getLocalCache() throws ObjectCreationException {
		return (LocalCache) ManagementUtil.getInstance(LOCAL_CACHEE);
	}

	@Override
	public SharedQueue getSharedQueue() throws ObjectCreationException {
		return (SharedQueue) ManagementUtil.getInstance(SHARED_QUEUE);
	}
}
