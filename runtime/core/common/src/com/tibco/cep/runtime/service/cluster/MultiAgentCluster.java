/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster;

import com.tibco.be.util.config.ClusterProviderConfiguration;
import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.exception.RecoveryException;
import com.tibco.cep.common.log.LoggerService;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.impl.common.log.LogDelegatorService;
import com.tibco.cep.impl.common.resource.DefaultResourceProvider;
import com.tibco.cep.runtime.service.cluster.agent.AgentManager;
import com.tibco.cep.runtime.service.cluster.agent.DefaultAgentManager;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.cluster.backingstore.GenericBackingStore;
import com.tibco.cep.runtime.service.cluster.backingstore.RecoveryManager;
import com.tibco.cep.runtime.service.cluster.deploy.DefaultHotDeployer;
import com.tibco.cep.runtime.service.cluster.deploy.HotDeployer;
import com.tibco.cep.runtime.service.cluster.events.DefaultEventTableProvider;
import com.tibco.cep.runtime.service.cluster.events.EventTableProvider;
import com.tibco.cep.runtime.service.cluster.events.notification.TopicRegistry;
import com.tibco.cep.runtime.service.cluster.events.notification.impl.DefaultTopicRegistry;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMembershipService;
import com.tibco.cep.runtime.service.cluster.gmp.impl.GroupMembershipServiceImpl;
import com.tibco.cep.runtime.service.cluster.mm.CacheClusterMBean;
import com.tibco.cep.runtime.service.cluster.scheduler.SchedulerCache;
import com.tibco.cep.runtime.service.cluster.scheduler.impl.DefaultSchedulerCache;
import com.tibco.cep.runtime.service.cluster.system.*;
import com.tibco.cep.runtime.service.cluster.system.impl.ObjectTableCache;
import com.tibco.cep.runtime.service.cluster.util.DefaultCacheSequenceManager;
import com.tibco.cep.runtime.service.om.api.DaoProvider;
import com.tibco.cep.runtime.service.om.api.DaoProviderFactory;
import com.tibco.cep.runtime.service.om.api.GroupMemberMediator;
import com.tibco.cep.runtime.service.om.api.InvocationService;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.impl.locks.DefaultConcurrentLockManager;
import com.tibco.cep.runtime.session.locks.LockManager;
import com.tibco.cep.runtime.session.sequences.SequenceManager;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.service.Service;

import java.lang.reflect.Method;
import java.util.EnumMap;


public class MultiAgentCluster implements Cluster {

    private String clusterName;
    private RuleServiceProvider rsp;
    private ResourceProvider resourceProvider;
    private GroupMembershipService gmpService;
    //private DaoProvider daoProvider;
    private IMetadataCache metadataCache;
    private IExternalClassesCache externalClassCache;
    private ObjectTable objectTableCache; ///////Check
    private ClusterIdGenerator idGenerator;

    private ClusterConfiguration clusterConfig;
    private TopicRegistry topicRegistry; ///////////check
    private LockManager lockManager;
    private CacheSequenceManager sequenceManager;
    private SchedulerCache schedulerCache;
    private EventTableProvider eventTableProvider; ///////required
    private GroupMemberMediator groupMemberMediator;
    private AgentManager agentManager;
    private RecoveryManager recoveryManager;
    private HotDeployer hotDeployer;
    private CacheClusterMBean mbean; ///////////////
    
    
    
    private InvocationService invocationService;
    
    private ClusterProvider clusterProvider;
    private GenericBackingStore backingStore;
    private BECacheProvider beCacheProvider;

    public MultiAgentCluster(String name, RuleServiceProvider rsp) throws Exception {
        this.clusterName = name;
        this.rsp = rsp;
        this.resourceProvider = new DefaultResourceProvider();
        this.clusterConfig = new DefaultClusterConfiguration(name, rsp);
        
        ClusterProviderConfiguration clusterConfig = (ClusterProviderConfiguration) rsp.getProperties().get(SystemProperty.VM_CLUSTER_CONFIG.getPropertyName());
        clusterProvider = ClusterProviderFactory.getClusterProvider(this, clusterConfig);
        
        this.idGenerator = clusterProvider.getIdGenerator();
       // this.daoProvider = DaoProviderFactory.getInstance().newProvider();
        this.metadataCache = clusterProvider.getMetadataCache();
        this.externalClassCache = clusterProvider.getExternalClassCache();
        this.objectTableCache = new ObjectTableCache();
        
        this.gmpService = clusterProvider.getGmpService();
        this.topicRegistry = new DefaultTopicRegistry();
        this.lockManager = new DefaultConcurrentLockManager();
        this.sequenceManager =  new DefaultCacheSequenceManager();
        this.schedulerCache = clusterProvider.getSchedulerCache();
        this.eventTableProvider = new DefaultEventTableProvider();
        this.agentManager = clusterProvider.getAgentManager();
        this.recoveryManager = null; //createRecoveryManager(); ///// TODO - 6.0 rectify this - getRecoveryManager is not part of BackingStore
        this.invocationService = clusterProvider.getInvocationService();
        this.groupMemberMediator = clusterProvider.getGroupMediator();
        this.groupMemberMediator.addGroupMemberServiceListener(gmpService);
        this.hotDeployer = clusterProvider.getHotDeployer();
    }

    @Override
    public String getClusterName() {
        return this.clusterName;
    }

    @Override
    public void init() throws Exception {
        LogDelegatorService logDelegatorService = new LogDelegatorService();
        logDelegatorService.start();
        this.resourceProvider.registerResource(LoggerService.class, logDelegatorService);
        
        clusterProvider.init(rsp.getProperties(), this);
        
        this.invocationService.init(this);

       // this.daoProvider.init(this);
        this.groupMemberMediator.init(this);
        this.gmpService.init(this);
        this.gmpService.waitForQuorum();
        this.idGenerator.init(this);
        this.metadataCache.init(this);
        this.externalClassCache.init(this);
        this.externalClassCache.loadExternalClasses();
        this.objectTableCache.init(this);
        this.topicRegistry.init(this);
        this.lockManager.init(this);
        this.sequenceManager.init(this);
        this.recoveryManager.init(this);
        this.schedulerCache.init(this);
        this.eventTableProvider.init(this);
        this.agentManager.init(clusterName, rsp, this);
        this.hotDeployer.init(this);

        //Couldn't find a better place to register this MBean.
        registerRTDeployerMBean();
        //this.mbean = new CacheClusterMBeanImpl(this);

        //this.mbean.registerMBean();
    }

    @Override
    public ResourceProvider getResourceProvider() {
        return resourceProvider;
    }

    @Override
    public EnumMap<ClusterCapability, Object> getCapabilities() {
        return this.clusterConfig.getCapabilties();
    }

    @Override
    public GroupMembershipService getGroupMembershipService() {
        return gmpService;
    }

	
	/*@Override 
	public DaoProvider getDaoProvider() { 
		//return this.daoProvider;
		return null;
	}*/
	 

    @Override
    public RuleServiceProvider getRuleServiceProvider() {
        return this.rsp;
    }

    @Override
    public IMetadataCache getMetadataCache() {
        return this.metadataCache;
    }

    @Override
    public IExternalClassesCache getExternalClassesCache() {
        return this.externalClassCache;
    }

    @Override
    public ObjectTable getObjectTableCache() {
        return this.objectTableCache;
    }

    @Override
    public LockManager getLockManager() {
        return lockManager;
    }

    @Override
    public ClusterConfiguration getClusterConfig() {
        return this.clusterConfig;
    }

    @Override
    public TopicRegistry getTopicRegistry() {
        return this.topicRegistry;
    }

    @Override
    public SequenceManager getSequenceManager() {
        return this.sequenceManager;
    }

    @Override
    public SchedulerCache getSchedulerCache() {
        return this.schedulerCache;
    }

    @Override
    public GenericBackingStore getBackingStore() {
       // return this.daoProvider.getBackingStore();
    	return null;
    }

 /*   @Override
    public BackingStore getCacheAsideStore() {
		
        return null;
    }*/

  /*  @Override
    public BackingStore getRecoveryBackingStore() {
		
        return null;
    }

    @Override
    public RecoveryManager getRecoveryManager() {
        return this.recoveryManager;
    }

    @Override
    public EventTableProvider getEventTableProvider() {
        return this.eventTableProvider;
    }
*/
    @Override
    public void flushAll() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Id getResourceId() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Service recover(ResourceProvider resourceProvider, Object... params) throws RecoveryException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void start() throws LifecycleException {
        try {
        	clusterProvider.start();
        	//lockCache.start();
            //this.daoProvider.start();
            this.gmpService.start();
            this.idGenerator.start();
            this.metadataCache.start();
            this.objectTableCache.start();
            this.topicRegistry.start();
            this.sequenceManager.start();
            this.eventTableProvider.start();
            this.recoveryManager.start();
            this.externalClassCache.deployExternalClasses();
            this.schedulerCache.start();
            this.agentManager.start();
            this.hotDeployer.start();
        }
        catch (Exception e) {
            throw new LifecycleException(e);
        }
    }

    @Override
    public void stop() throws LifecycleException {
        lockManager.discard();
        resourceProvider.discard();
        clusterProvider.stop();
       // daoProvider.stop();
    }

    public GroupMemberMediator getGroupMemberMediator() {
        return this.groupMemberMediator;
    }

    @Override
    public AgentManager getAgentManager() {
        return this.agentManager;
    }

    @Override
    public HotDeployer getHotDeployer() {
        return hotDeployer;
    }

    @Override
    public ClusterIdGenerator getIdGenerator() {
        return idGenerator;
    }

    private void registerRTDeployerMBean() {
        try {
            Class rtiDeployerClass = Class.forName("com.tibco.cep.runtime.service.cluster.deploy.RuleTemplateDeployer");
            Object rtiDeployer = rtiDeployerClass.getConstructor(Cluster.class).newInstance(this);
            Method mbeanMethod = rtiDeployerClass.getMethod("registerMBean");
            mbeanMethod.invoke(rtiDeployer);
        } catch (Exception e) {
            //Log it?
        }
    }

//    private RecoveryManager createRecoveryManager() {
//        String strategy = System.getProperty(SystemProperty.DATAGRID_RECOVERY_DISTRIBUTED_STRATEGY.getPropertyName(), /* alternative */
//                          System.getProperty(SystemProperty.DATAGRID_RECOVERY_GENERALIZED_STRATEGY.getPropertyName(), "nobatch"));
//        if ("nobatch".equals(strategy)){
//            return new com.tibco.cep.runtime.service.cluster.backingstore.ClusterCacheRecoveryManager();
//        } else {
//            return new com.tibco.cep.runtime.service.cluster.backingstore.recovery.ClusterCacheRecoveryManager();
//        }
//    }

	@Override
	public InvocationService getInvocationService() {
		return invocationService;
	}

	@Override
	public ClusterProvider getClusterProvider() {
		return clusterProvider;
	}
	
	@Override
	public BECacheProvider getBECacheProvider() {
		return beCacheProvider;
	}

	@Override
	public EventTableProvider getEventTableProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
