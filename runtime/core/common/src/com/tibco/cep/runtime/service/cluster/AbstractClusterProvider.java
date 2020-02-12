package com.tibco.cep.runtime.service.cluster;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import com.tibco.be.util.config.ClusterProviderConfig;
import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.agent.AgentManager;
import com.tibco.cep.runtime.service.cluster.deploy.HotDeployer;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMembershipService;
import com.tibco.cep.runtime.service.cluster.scheduler.SchedulerCache;
import com.tibco.cep.runtime.service.cluster.system.CacheSequenceManager;
import com.tibco.cep.runtime.service.cluster.system.ClusterIdGenerator;
import com.tibco.cep.runtime.service.cluster.system.IExternalClassesCache;
import com.tibco.cep.runtime.service.cluster.system.IMetadataCache;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.service.om.api.GroupMemberMediator;
import com.tibco.cep.runtime.service.om.api.InvocationService;

abstract public class AbstractClusterProvider implements ClusterProvider {

	protected Cluster cluster;
	protected Map<String, ControlDao> controlDaos = new LinkedHashMap<String, ControlDao>();
	protected ClusterProviderConfig beClusterConfig;
	
	protected ControlDao<String, Integer> clusterLockMgr;
	protected GroupMemberMediator groupMemberMediator;
	protected GroupMembershipService gmpService;
	protected IMetadataCache metadataCache;
	protected IExternalClassesCache externalClasses;
	protected SchedulerCache schedulerCache;
	protected AgentManager agentManager;
	protected HotDeployer hotDeployer;
	protected CacheSequenceManager cacheSeqMgr;
	protected ClusterIdGenerator clusterIdGenerator;
	protected InvocationService invocationService;
	protected Logger logger;

	public AbstractClusterProvider(ClusterProviderConfig beClusterConfig) {
		this.beClusterConfig = beClusterConfig;
		gmpService = new GroupMembershipServiceImpl();
		metadataCache = new MetadataCache();
		externalClasses = new ExternalClassesCache();
		schedulerCache = new DefaultSchedulerCache();
		agentManager = new DefaultAgentManager();
		hotDeployer = new DefaultHotDeployer();
		cacheSeqMgr = new DefaultCacheSequenceManager();
		clusterIdGenerator = new DefaultClusterIdGenerator3();
		invocationService = new DefaultInvocationService();
	}

	
	@Override
	abstract public <K, V> ControlDao<K, V> createControlDao(Class<K> keyClass, Class<V> valueClass,
			ControlDaoType daoType, Object... additionalProps) throws Exception;

	@Override
	public void init(Properties props, Object... objects) throws Exception {
		this.cluster = (Cluster) objects[0];
	}

	@Override
	public void start() throws LifecycleException {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() throws LifecycleException {
		if (logger != null) {
			logger.log(Level.DEBUG, "Stopping Cluster provider");
		}
		
		for (Entry<String, ControlDao> daoEntry : controlDaos.entrySet()) {
			daoEntry.getValue().discard();
		}

	}

	@Override
	public GroupMembershipService getGmpService() {
		return gmpService;
	}

	@Override
	public IMetadataCache getMetadataCache() {
		return metadataCache;
	}

	@Override
	public IExternalClassesCache getExternalClassCache() {
		return externalClasses;
	}

	@Override
	public CacheSequenceManager getSequenceManager() {
		return cacheSeqMgr;
	}

	@Override
	public SchedulerCache getSchedulerCache() {
		return schedulerCache;
	}

	@Override
	public GroupMemberMediator getGroupMediator() {
		return groupMemberMediator;
	}

	@Override
	public AgentManager getAgentManager() {
		return agentManager;
	}

	@Override
	public HotDeployer getHotDeployer() {
		return hotDeployer;
	}

	@Override
	public ClusterIdGenerator getIdGenerator() {
		return clusterIdGenerator;
	}

	@Override
	public InvocationService getInvocationService() {
		return invocationService;
	}

	
	protected String getDaoName(ControlDaoType daoType, Object[] additionalProps) {
		String daoName = null;
		String clusterName = cluster.getClusterName();
		String suffix = null;
		switch (daoType) {
		case WorkList$SchedulerId:
			if (null != additionalProps && additionalProps.length>1 && additionalProps[1]!=null){
				suffix = (String) additionalProps[1];
				daoName = clusterName + "$" + (suffix == null ? "" : suffix) + "$" + daoType.getAlias();
			}else{
				daoName = daoType.getAlias();
			}
			break;

		default:
			daoName = daoType.getAlias();
			break;
		}
		return daoName;
	}
}
