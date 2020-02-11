package com.tibco.cep.runtime.service.cluster;

import com.tibco.cep.runtime.service.cluster.agent.AgentManager;
import com.tibco.cep.runtime.service.cluster.deploy.HotDeployer;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMembershipService;
import com.tibco.cep.runtime.service.cluster.scheduler.SchedulerCache;
import com.tibco.cep.runtime.service.cluster.system.CacheSequenceManager;
import com.tibco.cep.runtime.service.cluster.system.ClusterIdGenerator;
import com.tibco.cep.runtime.service.cluster.system.IExternalClassesCache;
import com.tibco.cep.runtime.service.cluster.system.IMetadataCache;
import com.tibco.cep.runtime.service.cluster.system.LockCache;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.service.om.api.GroupMemberMediator;
import com.tibco.cep.runtime.service.om.api.InvocationService;

public interface ClusterProvider extends LifeCycleService {
	
	GroupMembershipService getGmpService();
	IMetadataCache getMetadataCache();
	IExternalClassesCache getExternalClassCache();
	SchedulerCache getSchedulerCache();
	GroupMemberMediator getGroupMediator();
	AgentManager getAgentManager();
	HotDeployer getHotDeployer();
	ClusterIdGenerator getIdGenerator();
	CacheSequenceManager getSequenceManager();

	<K, V> ControlDao<K, V> createControlDao(Class<K> keyClass, Class<V> valueClass, ControlDaoType daoType,
			Object... additionalProps) throws Exception;

	InvocationService getInvocationService();
}
