/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 27/8/2010
 */

package com.tibco.cep.runtime.service.cluster.mm;

import com.tibco.cep.runtime.service.management.HotDeployMethods;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Apr 28, 2008
 * Time: 1:18:55 AM
 * To change this template use File | Settings | File Templates.
 */
public interface CacheClusterMBean extends HotDeployMethods {

    public String getCacheType();

    public String getClusterName();

    public void suspendNode();

    public void resumeNode();

    public String[] getLoadInfo();

    public String[] getRecoveryInfo();

    public String[] getPreloadInfo();

    public boolean isBackingStoreEnabled();

    public boolean isCacheAside();

    public boolean isAutoStartup();

    boolean isSerializationOptimized();

    boolean isStorageEnabled(); // per-node value
     
    String getNodeId(); // per-node value

    int getMemberCount();

    public void SetLogLevel(String level) throws Exception;
    
    public String getPersistenceOption();

// Bala: Some of these are already available in Coherence mbeans, some dont have AS equiv and
// we can implement if there is a real need.
//    public void startCluster() throws Exception;
//
//    String getSiteName();
//
//    String getRackName();
//
//    String getMachineName();
//
//    String getProcessName();
//
//    void flushAll() throws Exception;//
//
//    void flushCache();
//
//    public String getClusterState();
//
//    public long getSiteId();
}
