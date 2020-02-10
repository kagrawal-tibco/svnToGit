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

package com.tibco.cep.runtime.service.cluster.agent.mm;

import java.io.IOException;

import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgentMBean;

public class InferenceAgentMBeanImpl implements InferenceAgentMBean {

    InferenceAgent ia;

    public InferenceAgentMBeanImpl(InferenceAgent ia) {
        this.ia = ia;
    }

    @Override
    public String getAgentName() {
//        if (!objectStore.isCacheServer()) {
//            return (String) agentConfig.getAgentName();
//        } else {
//            return (String) agentConfig.getAgentName() + CACHESERVER_SUFFIX;
//        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isSuspended() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getPriority() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getMaxActive() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getAgentId() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getCurrentState() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getNumJobs() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double getJobRate() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getL1CacheMaxSize() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getL1CacheSize() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getCacheQueueRemainingCapacity() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getDBOpsQueueRemainingCapacity() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double getHitRatio() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isReadOnly() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getTxnCommitCount() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double getAvgTxnCommitTime() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getTxnReceiveCount() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double getAvgReceiveTime() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String[] getRuleUris() {
        return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getNumEventThreads() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean suspend() throws Exception {
        return true;    //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean resume() throws Exception {
        return true;    //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void purgeDeletedObjects() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void printMemoryInfo(String filePath) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void printL1CacheContents(String filePathAndName) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void printLockManagerDetails() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getTotalLocksHeldCount() {
    	return 0;
    }

    @Override
    public long getLocalLocksHeldCount() {
    	return 0;
    }

    @Override
    public long getClusterWideLocksHeldCount() {
    	return 0;
    }

    @Override
    public void printAsynchronousWorkerDetails() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
