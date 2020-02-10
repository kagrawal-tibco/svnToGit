/*
* Copyright (c) 2010.  TIBCO Software Inc.
*  All Rights Reserved.
*
*  This software is confidential and proprietary information of
*  TIBCO Software Inc.
*/

/*
 * Author : Suresh Subramani
 */

package com.tibco.cep.runtime.service.cluster;

import java.util.EnumMap;

import com.tibco.cep.runtime.session.RuleServiceProvider;

public class DefaultClusterConfiguration implements ClusterConfiguration {

    EnumMap<ClusterCapability, Object> capabilities;

    public DefaultClusterConfiguration() {
    }

    public DefaultClusterConfiguration(String clusterName, RuleServiceProvider rsp) {
        capabilities = ClusterCapability.loadCapabilities(clusterName, rsp);
    }

    @Override
    public String getVersion() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getClusterName() {
        return ClusterCapability.getValue(capabilities, ClusterCapability.NAME, String.class);
    }

    @Override
    public boolean isAutoStartup() {
        return ClusterCapability.getValue(capabilities, ClusterCapability.AUTOSTARTUP, Boolean.class);
    }

    @Override
    public boolean use2xMode() {
        return ClusterCapability.getValue(capabilities, ClusterCapability.USE2XMODE, Boolean.class);

    }

    @Override
    public boolean isHasBackingStore() {
        return ClusterCapability.getValue(capabilities, ClusterCapability.HASBACKINGSTORE, Boolean.class);
    }

    @Override
    public boolean isUsePrimaryDatasource() {
        return ClusterCapability.getValue(capabilities, ClusterCapability.USEPRIMARYDATASOURCE, Boolean.class);
    }

    @Override
    public boolean isAutoFailover() {
        return ClusterCapability.getValue(capabilities, ClusterCapability.AUTOFAIL, Boolean.class);
    }

    @Override
    public boolean isCacheLimited() {
        return ClusterCapability.getValue(capabilities, ClusterCapability.ISCACHELIMITED, Boolean.class);
    }

    @Override
    public String getCacheType() {
        return ClusterCapability.getValue(capabilities, ClusterCapability.CACHETYPE, String.class);
    }

    @Override
    public boolean isClassLoader() {
        return ClusterCapability.getValue(capabilities, ClusterCapability.ISCLASSLOADER, Boolean.class);
    }

    @Override
    public String getExternalClassesDir() {
        return ClusterCapability.getValue(capabilities, ClusterCapability.EXTERNALCLASSESDIR, String.class);
    }

    @Override
    public int getMinSeeders() {
        int x = ClusterCapability.getValue(capabilities, ClusterCapability.MINSEEDERS, Integer.class);
        int y = Math.max(ClusterCapability.getValue(capabilities, ClusterCapability.MINCACHESERVERS, Integer.class), 1);

        return (x < 1) ? y : x;
    }

    @Override
    public int getMinCacheServers() {
        int x = ClusterCapability.getValue(capabilities, ClusterCapability.MINCACHESERVERS, Integer.class);

        return (x <= 1) ? 1 : x;
    }

    @Override
    public int getClusterQueueSize() {
        return ClusterCapability.getValue(capabilities, ClusterCapability.CLUSTERQUEUESIZE, Integer.class);
    }

    @Override
    public void printClusterInfo() {

    }

    @Override
    public void matches(ClusterConfiguration otherConfig) throws Exception {

    }

    @Override
    public String getObjectTableScheme() {
        return ClusterCapability.getValue(capabilities, ClusterCapability.OBJECTTABLESCHEME, String.class);
    }

    @Override
    public String getEntityMediatorType() {
        return ClusterCapability.getValue(capabilities, ClusterCapability.ENTITYMEDIATORTYPE, String.class);
    }

    @Override
    public boolean isKeepDeleted() {
        return ClusterCapability.getValue(capabilities, ClusterCapability.KEEPDELETED, Boolean.class);
    }

    @Override
    public boolean isCacheAside() {
        return ClusterCapability.getValue(capabilities, ClusterCapability.ISCACHEASIDE, Boolean.class);
    }

    @Override
    public boolean isDBBatching() {
        return ClusterCapability.getValue(capabilities, ClusterCapability.USEDBBATCHING, Boolean.class);
    }

    @Override
    public boolean useObjectTable() {
        return ClusterCapability.getValue(capabilities, ClusterCapability.USEOBJECTTABLE, Boolean.class);
    }

    @Override
    public int getSiteId() {
        return ClusterCapability.getValue(capabilities, ClusterCapability.SITEID, Integer.class);
    }

    @Override
    public int getAutoFailoverInterval() {
        return ClusterCapability.getValue(capabilities, ClusterCapability.AUTOFAILINTERVAL, Integer.class);
    }

    @Override
    public boolean isStorageEnabled() {
        return ClusterCapability.getValue(capabilities, ClusterCapability.ISSEEDER, Boolean.class);
    }

    @Override
    public boolean isNewSerializationEnabled() {
        return ClusterCapability.getValue(capabilities, ClusterCapability.SERIALIZATIONOPTIMIZED, Boolean.class);
    }

    @Override
    public EnumMap<ClusterCapability, Object> getCapabilties() {
        return capabilities;
    }

    @Override
    public String getExternalClassPackageExclusions() {
        return ClusterCapability
                .getValue(capabilities, ClusterCapability.EXTERNAL_CLASSES_PACKAGE_EXCLUSIONS, String.class);
    }

	@Override
	public boolean isMultiSite() {
		return ClusterCapability.getValue(capabilities, ClusterCapability.ISMULTISITE, Boolean.class);
	}
	
	@Override
	public String getRTIDeployerDir() {
		return ClusterCapability
                .getValue(capabilities, ClusterCapability.RTIDEPLOYERDIR, String.class);
	}

	@Override
	public String getPersistenceOption() {
		return ClusterCapability
                .getValue(capabilities, ClusterCapability.BACKING_STORE_TYPE, String.class);
	}
}
