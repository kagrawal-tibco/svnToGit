/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.agent;

import com.tibco.cep.runtime.service.cluster.ClusterConfiguration;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransactionProperties;

public interface AgentConfiguration {

    RtcTransactionProperties getDefaultRtcTransactionProperties();

    //HashMap getEntityConfigurations();

    //EntityDaoConfig getEntityDaoConfig(Class entityClz);

    int getThreadCount();

    int getDBThreadCount();

    int getCacheOpsQueueSize();

    int getDBOpsQueueSize();

    int getDBOpsBatchSize();

    boolean isReadOnly();

    boolean keepHandles();

    boolean listenToAgents();

    boolean recoverOnStartup();

    String getAgentKey();

    String getAgentName();

    ClusterConfiguration getClusterConfig();

    int getMaxActive();

    int getPriority();

    long getL1CacheExpiryMillis();

    int getL1CacheSize();

    int getRecoveryPageSize();

    String getCommandChannel();

    boolean isLenient();

    boolean isPublishTxn();

    boolean isConcurrent();

    boolean isDuplicateCheckOn();

    boolean isEnableParallelOps();
}

