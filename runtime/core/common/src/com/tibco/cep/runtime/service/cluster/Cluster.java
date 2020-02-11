/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster;

import java.util.EnumMap;

import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.runtime.service.cluster.agent.AgentManager;
import com.tibco.cep.runtime.service.cluster.backingstore.GenericBackingStore;
//import com.tibco.cep.runtime.service.cluster.backingstore.CacheAsideBackingStore;
//import com.tibco.cep.runtime.service.cluster.backingstore.RecoveryManager;
import com.tibco.cep.runtime.service.cluster.deploy.HotDeployer;
import com.tibco.cep.runtime.service.cluster.events.EventTableProvider;
import com.tibco.cep.runtime.service.cluster.events.notification.TopicRegistry;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMembershipService;
import com.tibco.cep.runtime.service.cluster.scheduler.SchedulerCache;
import com.tibco.cep.runtime.service.cluster.system.ClusterIdGenerator;
import com.tibco.cep.runtime.service.cluster.system.IExternalClassesCache;
import com.tibco.cep.runtime.service.cluster.system.IMetadataCache;
import com.tibco.cep.runtime.service.cluster.system.LockCache;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable;
import com.tibco.cep.runtime.service.om.api.GroupMemberMediator;
import com.tibco.cep.runtime.service.om.api.InvocationService;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.locks.LockManager;
import com.tibco.cep.runtime.session.sequences.SequenceManager;
import com.tibco.cep.service.Service;

public interface Cluster extends Service {

    void init() throws Exception;

    String getClusterName();

    EnumMap<ClusterCapability, Object> getCapabilities();

    GroupMembershipService getGroupMembershipService();

//    DaoProvider getDaoProvider();

    RuleServiceProvider getRuleServiceProvider();

    ResourceProvider getResourceProvider();

    IMetadataCache getMetadataCache();

    IExternalClassesCache getExternalClassesCache();

    ObjectTable getObjectTableCache();

    LockManager getLockManager();

    ClusterConfiguration getClusterConfig();

    TopicRegistry getTopicRegistry();
//TODO:6 later on revive seq mgr..
    SequenceManager getSequenceManager();

    SchedulerCache getSchedulerCache();

//    CacheAsideBackingStore getBackingStore();

//    BackingStore getCacheAsideStore();

//    BackingStore getRecoveryBackingStore();

//    RecoveryManager getRecoveryManager();

    EventTableProvider getEventTableProvider();

    GroupMemberMediator getGroupMemberMediator();

    AgentManager getAgentManager();
    
    void flushAll();
    
    HotDeployer getHotDeployer();

    ClusterIdGenerator getIdGenerator();
    
//    ZKClusterService getZKClusterService();
    
    InvocationService getInvocationService();

//	TxnCacheService getTxnCacheService();

	ClusterProvider getClusterProvider();
	BECacheProvider getBECacheProvider();

//	CacheProvider getCacheProvider();
	
	GenericBackingStore getBackingStore();

	//BEStore getBackingStoreForType(int typeId);
	
	//BEStore registerBEStoreForType(int typeId, String storeUri) throws Exception;

}
