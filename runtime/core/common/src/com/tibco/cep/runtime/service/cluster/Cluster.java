/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster;

import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.runtime.service.cluster.agent.AgentManager;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.cluster.backingstore.GenericBackingStore;
import com.tibco.cep.runtime.service.cluster.backingstore.RecoveryManager;
import com.tibco.cep.runtime.service.cluster.deploy.HotDeployer;
import com.tibco.cep.runtime.service.cluster.events.EventTableProvider;
import com.tibco.cep.runtime.service.cluster.events.notification.TopicRegistry;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMembershipService;
import com.tibco.cep.runtime.service.cluster.scheduler.SchedulerCache;
import com.tibco.cep.runtime.service.cluster.system.ClusterIdGenerator;
import com.tibco.cep.runtime.service.cluster.system.ExternalClassesCache;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable;
import com.tibco.cep.runtime.service.om.api.DaoProvider;
import com.tibco.cep.runtime.service.om.api.GroupMemberMediator;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.locks.LockManager;
import com.tibco.cep.runtime.session.sequences.SequenceManager;
import com.tibco.cep.service.Service;

import java.util.EnumMap;

public interface Cluster extends Service {

    void init() throws Exception;

    String getClusterName();

    EnumMap<ClusterCapability, Object> getCapabilities();

    GroupMembershipService getGroupMembershipService();

    DaoProvider getDaoProvider();

    RuleServiceProvider getRuleServiceProvider();

    ResourceProvider getResourceProvider();

    MetadataCache getMetadataCache();

    ExternalClassesCache getExternalClassesCache();

    ObjectTable getObjectTableCache();

    LockManager getLockManager();

    ClusterConfiguration getClusterConfig();

    TopicRegistry getTopicRegistry();

    SequenceManager getSequenceManager();

    SchedulerCache getSchedulerCache();

    GenericBackingStore getBackingStore();

    BackingStore getCacheAsideStore();

    BackingStore getRecoveryBackingStore();

    RecoveryManager getRecoveryManager();

    EventTableProvider getEventTableProvider();

    GroupMemberMediator getGroupMemberMediator();

    AgentManager getAgentManager();
    
    void flushAll();
    
    HotDeployer getHotDeployer();

    ClusterIdGenerator getIdGenerator();
}
