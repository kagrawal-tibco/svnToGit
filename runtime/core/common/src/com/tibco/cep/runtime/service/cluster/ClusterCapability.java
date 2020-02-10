/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 12/7/2010
 */

package com.tibco.cep.runtime.service.cluster;

import java.util.EnumMap;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.util.SystemProperty;

public enum ClusterCapability {

    NAME,
    HASBACKINGSTORE, /* boolean*/
    BACKING_STORE_TYPE, /*String */
    DATABASETYPE, /* String */
    USEPRIMARYDATASOURCE, /* boolean*/
    AUTOFAIL, /* boolean*/
    AUTOFAILINTERVAL, /* int*/
    MINCACHESERVERS, /* int*/
    MINSEEDERS, /* int*/
    CONFIGXML, /* String*/
    ISCLASSLOADER, /* boolean*/
    EXTERNALCLASSESDIR, /* String*/
    RTIDEPLOYERDIR, /* String */
    SERIALIZATIONOPTIMIZED, /* boolean*/
    CACHETYPE, /* String*/
    CLUSTERQUEUESIZE, /* int*/
    ISCACHELIMITED, /* boolean*/
    SITEID, /* long*/
    OBJECTTABLESCHEME, /* String*/
    ENTITYMEDIATORTYPE, /* String*/
    KEEPDELETED, /* boolean*/
    AUTOSTARTUP, /* boolean*/
    ISCACHEASIDE, /* boolean*/
    USE2XMODE, /* boolean*/
    USEDBBATCHING, /* boolean*/
    USEOBJECTTABLE, /* boolean*/
    USEINVOKEFORDELETES, /* boolean*/
    EXTENDEDCAPABILITIES,
    ISSEEDER,
    ISMULTIENGINEMODE, 
	EXTERNAL_CLASSES_PACKAGE_EXCLUSIONS, /* any object*/
    ISMULTISITE,
    CLUSTER_VERSION;

    public static EnumMap<ClusterCapability, Object> loadCapabilities(String name, RuleServiceProvider rsp) {
        EnumMap<ClusterCapability, Object> capabilities = new EnumMap<ClusterCapability, Object>(ClusterCapability.class);

        BEProperties props = (BEProperties) rsp.getProperties();

        capabilities.put(ClusterCapability.NAME, name);        
        capabilities.put(ClusterCapability.AUTOSTARTUP, props.getBoolean(SystemProperty.CLUSTER_AUTO_STARTUP.getPropertyName(), true));

        boolean hasBackingStore = props.getBoolean(SystemProperty.CLUSTER_HAS_BACKING_STORE.getPropertyName(), false);
        String cacheProviderType = props.getString(SystemProperty.VM_DAOPROVIDER_TYPE.getPropertyName(), "tibco");

        capabilities.put(ClusterCapability.HASBACKINGSTORE, hasBackingStore);
        capabilities.put(ClusterCapability.BACKING_STORE_TYPE,props.getString(SystemProperty.BACKING_STORE_TYPE.getPropertyName(),null));
        capabilities.put(ClusterCapability.DATABASETYPE, props.getString(SystemProperty.BACKING_STORE_DB_TYPE.getPropertyName(),null) );
        capabilities.put(ClusterCapability.ISCACHELIMITED, props.getBoolean(SystemProperty.CLUSTER_IS_CACHE_LIMITED.getPropertyName(), hasBackingStore));
        capabilities.put(ClusterCapability.ISCACHEASIDE, props.getBoolean(SystemProperty.CLUSTER_IS_CACHE_ASIDE.getPropertyName(), hasBackingStore));

        capabilities.put(ClusterCapability.USEDBBATCHING,props.getBoolean(SystemProperty.CLUSTER_USE_DB_BATCHING.getPropertyName(), false));
        capabilities.put(ClusterCapability.KEEPDELETED, props.getBoolean(SystemProperty.CLUSTER_KEEP_DELETED.getPropertyName(), false));
        capabilities.put(ClusterCapability.USEPRIMARYDATASOURCE, props.getBoolean(SystemProperty.CLUSTER_USE_PRIMARY_DATA_SOURCE.getPropertyName(), true));
        capabilities.put(ClusterCapability.AUTOFAIL,props.getBoolean(SystemProperty.CLUSTER_AUTO_FAILOVER.getPropertyName(), false));
        capabilities.put(ClusterCapability.AUTOFAILINTERVAL, props.getInt(SystemProperty.CLUSTER_FAILOVER_INTERVAL.getPropertyName(), 300));

		String minSeeders = props.getProperty(SystemProperty.CLUSTER_MIN_SPACE_SEEDERS.getPropertyName(), "0");
		minSeeders = rsp.getGlobalVariables().substituteVariables(minSeeders).toString();
		capabilities.put(ClusterCapability.MINSEEDERS, Integer.parseInt(minSeeders));

        String minCacheServer = props.getProperty(SystemProperty.CLUSTER_MIN_CACHE_SERVERS.getPropertyName(),"1");
        minCacheServer = rsp.getGlobalVariables().substituteVariables(minCacheServer).toString();
        capabilities.put(ClusterCapability.MINCACHESERVERS, Integer.parseInt(minCacheServer));
        
        capabilities.put(ClusterCapability.ISCLASSLOADER,props.getBoolean(SystemProperty.CLUSTER_EXTERNAL_CLASSES_CLASSLOADER.getPropertyName(), true));
        
        String externalClassesDir = props.getString(SystemProperty.CLUSTER_EXTERNAL_CLASSES_PATH.getPropertyName());
        if (externalClassesDir != null) externalClassesDir = rsp.getGlobalVariables().substituteVariables(externalClassesDir).toString();
        capabilities.put(ClusterCapability.EXTERNALCLASSESDIR, externalClassesDir);
        
        String rtiDeployerDir = props.getString(SystemProperty.CLUSTER_RULETEMPLATE_INSTANCE_DEPLOYER_DIR.getPropertyName());
        if (rtiDeployerDir != null) rtiDeployerDir = rsp.getGlobalVariables().substituteVariables(rtiDeployerDir).toString();
        capabilities.put(ClusterCapability.RTIDEPLOYERDIR, rtiDeployerDir);
        
        capabilities.put(ClusterCapability.SERIALIZATIONOPTIMIZED, props.getBoolean(SystemProperty.CLUSTER_OPTIMIZE_SERIALIZATION.getPropertyName(), false));
        capabilities.put(ClusterCapability.CACHETYPE, props.getString(SystemProperty.CLUSTER_CACHE_TYPE.getPropertyName(), "DISTRIBUTED"));
        capabilities.put(ClusterCapability.CLUSTERQUEUESIZE, props.getInt(SystemProperty.CLUSTER_QUEUE_SIZE.getPropertyName(), 1024));

        capabilities.put(ClusterCapability.SITEID, props.getInt(SystemProperty.CLUSTER_SITE_ID.getPropertyName(), 0));
        capabilities.put(ClusterCapability.ISMULTISITE, props.getBoolean(SystemProperty.CLUSTER_IS_MULTISITE.getPropertyName(), false));
        //capabilities.put(ClusterCapability. props.getBranch(SystemProperty.CLUSTER_EXTEND.getPropertyName()).size() > 0;

        boolean bMultiEngineMode = props.getBoolean(SystemProperty.CLUSTER_MULTI_ENGINE_ON.getPropertyName(), true);
        capabilities.put(ClusterCapability.ISMULTIENGINEMODE, bMultiEngineMode);
        capabilities.put(ClusterCapability.USE2XMODE, !bMultiEngineMode);

        capabilities.put(ClusterCapability.ENTITYMEDIATORTYPE, props.getString(SystemProperty.CLUSTER_ENTITY_MEDIATOR.getPropertyName(), "cluster"));
        capabilities.put(ClusterCapability.USEOBJECTTABLE, props.getBoolean(SystemProperty.CLUSTER_USEOBJECTTABLE.getPropertyName(), true));
        capabilities.put(ClusterCapability.ISSEEDER, props.getBoolean(SystemProperty.CLUSTER_NODE_ISSEEDER.getPropertyName(), false));
        capabilities.put(ClusterCapability.EXTERNAL_CLASSES_PACKAGE_EXCLUSIONS, props.getString(SystemProperty.CLUSTER_EXTERNAL_CLASSES_PACKAGE_EXCLUSIONS.getPropertyName()));

        capabilities.put(ClusterCapability.CLUSTER_VERSION, props.getString(SystemProperty.CLUSTER_VERSION_ID.getPropertyName()));
        return capabilities;
    }

    public static <K> K getValue(EnumMap<ClusterCapability, Object> capabilities, ClusterCapability capability, Class<K> returnType) {
        Object value = capabilities.get(capability);
        return returnType.cast(value);
    }
}
