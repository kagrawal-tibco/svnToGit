/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 9/7/2010
 */

package com.tibco.cep.runtime.service.cluster.agent;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Apr 28, 2008
 * Time: 9:48:35 AM
 * To change this template use File | Settings | File Templates.
 */
public interface InferenceAgentMBean {

    public String getAgentName();

    public String getType();

    public boolean isSuspended();

    public int getPriority();

    public int getMaxActive();

    public int getAgentId();

    public String getCurrentState();

    public long getNumJobs();

    public double getJobRate();

    public long getL1CacheMaxSize();

    public long getL1CacheSize();

    public long getCacheQueueRemainingCapacity();

    public long getDBOpsQueueRemainingCapacity();

    public double getHitRatio();

    public boolean isReadOnly();

    public long getTxnCommitCount();

    public double getAvgTxnCommitTime();

    public long getTxnReceiveCount();

    public double getAvgReceiveTime();

    public String[] getRuleUris();

    public int getNumEventThreads();

    public boolean suspend() throws Exception;

    public boolean resume() throws Exception;

    public void purgeDeletedObjects();

    public void printMemoryInfo(String filePath) throws IOException;

    public void printL1CacheContents(String filePathAndName) throws IOException;

    public void printLockManagerDetails();
    public long getTotalLocksHeldCount();
    public long getLocalLocksHeldCount();
    public long getClusterWideLocksHeldCount();

    public void printAsynchronousWorkerDetails();
}
