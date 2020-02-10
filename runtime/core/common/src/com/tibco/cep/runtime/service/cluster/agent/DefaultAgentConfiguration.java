/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 *  All Rights Reserved.
 *
 *  This software is confidential and proprietary information of
 *  TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.agent;

import java.util.EnumMap;

import com.tibco.cep.runtime.service.cluster.ClusterConfiguration;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransactionProperties;
import com.tibco.cep.runtime.session.RuleServiceProvider;

public class DefaultAgentConfiguration implements AgentConfiguration{

    EnumMap<AgentCapability, Object> agentCapabilities;
    
    String name;
    RuleServiceProvider rsp;
    RtcTransactionProperties rtcTransactionProperties;

    public DefaultAgentConfiguration(String name, String key, RuleServiceProvider rsp) {
        this.name = name;
        this.rsp = rsp;
        agentCapabilities = AgentCapability.loadAgentCapabilities(name, key, rsp);
        initRTCTransactionProperties();
    }
    
    private void initRTCTransactionProperties() {
    	rtcTransactionProperties = new RtcTransactionProperties();
    	rtcTransactionProperties.updateCache = AgentCapability.getValue(agentCapabilities, AgentCapability.UPDATECACHE, Boolean.class);
    }

    @Override
    public RtcTransactionProperties getDefaultRtcTransactionProperties() {
        return rtcTransactionProperties;
    }

    @Override
    public int getThreadCount() {
        return AgentCapability.getValue(agentCapabilities, AgentCapability.THREADCOUNT, Integer.class);
    }

    @Override
    public int getDBThreadCount() {
        return AgentCapability.getValue(agentCapabilities, AgentCapability.DBTHREADCOUNT, Integer.class);
    }

    @Override
    public int getCacheOpsQueueSize() {
        return AgentCapability.getValue(agentCapabilities, AgentCapability.CACHEOPSQUEUESIZE, Integer.class);
    }

    @Override
    public int getDBOpsQueueSize() {
        return AgentCapability.getValue(agentCapabilities, AgentCapability.DBOPSQUEUESIZE, Integer.class);
    }

    @Override
    public int getDBOpsBatchSize() {
        return AgentCapability.getValue(agentCapabilities, AgentCapability.DBOPSBATCHSIZE, Integer.class);
    }

    @Override
    public boolean isReadOnly() {
        return AgentCapability.getValue(agentCapabilities, AgentCapability.READONLY, Boolean.class);
    }

    @Override
    public boolean keepHandles() {
        return AgentCapability.getValue(agentCapabilities, AgentCapability.KEEPHANDLES, Boolean.class);
    }

    @Override
    public boolean listenToAgents() {
        return AgentCapability.getValue(agentCapabilities, AgentCapability.CACHELISTENER, Boolean.class);
    }

    @Override
    public boolean recoverOnStartup() {
        return AgentCapability.getValue(agentCapabilities, AgentCapability.RECOVERONSTARTUP, Boolean.class);
    }

    @Override
    public String getAgentKey() {
        return AgentCapability.getValue(agentCapabilities, AgentCapability.AGENTKEY, String.class);
    }

    @Override
    public String getAgentName() {
        return name;
    }

    @Override
    public ClusterConfiguration getClusterConfig() {
        return rsp.getCluster().getClusterConfig();
    }

    @Override
    public int getMaxActive() {
        return AgentCapability.getValue(agentCapabilities, AgentCapability.MAXACTIVE, Integer.class);
    }

    @Override
    public int getPriority() {
        return AgentCapability.getValue(agentCapabilities, AgentCapability.PRIORITY, Integer.class);
    }

    @Override
    public long getL1CacheExpiryMillis() {
        return AgentCapability.getValue(agentCapabilities, AgentCapability.L1CACHEEXPIRYMILLIS, Long.class);
    }

    @Override
    public int getL1CacheSize() {
        return AgentCapability.getValue(agentCapabilities, AgentCapability.L1CACHESIZE, Integer.class);
    }

    @Override
    public int getRecoveryPageSize() {
        return AgentCapability.getValue(agentCapabilities, AgentCapability.RECOVERYPAGESIZE, Integer.class);
    }

    @Override
    public String getCommandChannel() {
        return AgentCapability.getValue(agentCapabilities, AgentCapability.CHANNELCOMMAND, String.class);
    }

    @Override
    public boolean isLenient() {
        return AgentCapability.getValue(agentCapabilities, AgentCapability.LENIENT, Boolean.class);
    }

    @Override
    public boolean isPublishTxn() {
        return AgentCapability.getValue(agentCapabilities, AgentCapability.PUBLISHTXN, Boolean.class);
    }

    @Override
    public boolean isConcurrent() {
        return AgentCapability.getValue(agentCapabilities, AgentCapability.CONCURRENTWM, Boolean.class);
    }

    @Override
    public boolean isDuplicateCheckOn() {
        return AgentCapability.getValue(agentCapabilities, AgentCapability.CHECKDUPLICATES, Boolean.class);
    }

    @Override
    public boolean isEnableParallelOps() {
        return AgentCapability.getValue(agentCapabilities, AgentCapability.ENABLEPARALLELOPS, Boolean.class);
    }
}
