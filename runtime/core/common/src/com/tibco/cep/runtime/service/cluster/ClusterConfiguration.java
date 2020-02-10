/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster;

import java.io.Serializable;
import java.util.EnumMap;


public interface ClusterConfiguration extends Serializable {
    String getVersion();

    String getClusterName();

    boolean isAutoStartup();

    boolean use2xMode();

    boolean isHasBackingStore();

    boolean isUsePrimaryDatasource();

    boolean isAutoFailover();

    boolean isCacheLimited();

    String getCacheType();

    boolean isClassLoader();

    String getExternalClassesDir();

    int getMinSeeders();
    
    int getMinCacheServers();

    int getClusterQueueSize();

    void printClusterInfo();

    void matches(ClusterConfiguration otherConfig) throws Exception;

    String getObjectTableScheme();

    String getEntityMediatorType();

    boolean isKeepDeleted();

    boolean isCacheAside();

    boolean isDBBatching();

    boolean useObjectTable();

    int getSiteId();

    boolean isMultiSite();
    
    int getAutoFailoverInterval();

    boolean isStorageEnabled();

    boolean isNewSerializationEnabled();

    EnumMap<ClusterCapability, Object> getCapabilties();

    String getExternalClassPackageExclusions();
    
    String getRTIDeployerDir();
    
    String getPersistenceOption();
    
}
