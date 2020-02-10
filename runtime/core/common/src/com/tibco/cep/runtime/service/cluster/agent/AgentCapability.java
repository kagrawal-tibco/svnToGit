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

package com.tibco.cep.runtime.service.cluster.agent;

import java.util.EnumMap;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.util.SystemProperty;


public enum AgentCapability {
    NAME(".name"),   // Name of the Agent
    MAXACTIVE(".maxActive", 0),
    PRIORITY(".priority", 10),
    L1CACHESIZE(".l1CacheSize", 1024),
    L1CACHEEXPIRYMILLIS(".l1CacheExpiryMillis", 30 * 60 * 1000L /*30 mins.*/),
    AGENTKEY(".agentKey"),
    THREADCOUNT(".threadcount", 2),
    DBTHREADCOUNT(".dbthreadcount", 2),
    CACHEOPSQUEUESIZE(".cacheOpsQueueSize", 8),
    DBOPSQUEUESIZE(".dbOpsQueueSize", 8),
    DBOPSBATCHSIZE(".dbOpsBatchSize", 10),
    RECOVERYPAGESIZE(".recoveryPageSize", 0),
    KEEPHANDLES(".keepHandles", false),
    CACHELISTENER(".cacheListener", true),
    RECOVERONSTARTUP(".recoverOnStartup", true),
    LENIENT(".lenient", true),
    PUBLISHTXN(".publishTxn", true),
    PROPERTIES(".properties"),
    CHANNELCOMMAND(".channelCommand"),
    ENABLEPARALLELOPS(".enableParallelOps", false),
    READONLY(".readOnly", false),
    CHECKDUPLICATES(".checkDuplicates", false),
    CONCURRENTWM(".concurrentwm", false),
    UPDATECACHE(".cacheTxn.updateCache", true);

    protected String propSuffix;

    protected Object defaultValue;

    AgentCapability(String propSuffix) {
        this.propSuffix = propSuffix;
    }

    AgentCapability(String propSuffix, Object defaultValue) {
        this.propSuffix = propSuffix;
        this.defaultValue = defaultValue;
    }

    public String getPropSuffix() {
        return propSuffix;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public static EnumMap<AgentCapability, Object> loadAgentCapabilities(String agentName, String key,
                                                                         RuleServiceProvider rsp) {
        BEProperties props = (BEProperties) rsp.getProperties();
        EnumMap<AgentCapability, Object> capabilities = new EnumMap<AgentCapability, Object>(AgentCapability.class);

        capabilities.put(NAME, agentName);

        //TODO Suresh: null should be replaced by the ID implementor
        capabilities.put(AGENTKEY, props.getString("Agent." + agentName + AGENTKEY.propSuffix, key));

        capabilities.put(MAXACTIVE, props.getInt("Agent." + agentName + MAXACTIVE.propSuffix,
                (Integer) MAXACTIVE.defaultValue));

        capabilities.put(PRIORITY, props.getInt("Agent." + agentName + PRIORITY.propSuffix,
                (Integer) PRIORITY.defaultValue));

        capabilities.put(L1CACHESIZE, props.getInt("Agent." + agentName + L1CACHESIZE.propSuffix,
                (Integer) L1CACHESIZE.defaultValue));

        capabilities.put(L1CACHEEXPIRYMILLIS, props.getLong("Agent." + agentName + L1CACHEEXPIRYMILLIS.propSuffix,
                (Long) L1CACHEEXPIRYMILLIS.defaultValue));

        capabilities.put(THREADCOUNT, props.getInt("Agent." + agentName + THREADCOUNT.propSuffix,
                (Integer) THREADCOUNT.defaultValue));

        capabilities.put(DBTHREADCOUNT, props.getInt("Agent." + agentName + DBTHREADCOUNT.propSuffix,
                (Integer) DBTHREADCOUNT.defaultValue));

        capabilities.put(CACHEOPSQUEUESIZE, props.getInt("Agent." + agentName + CACHEOPSQUEUESIZE.propSuffix,
                (Integer) CACHEOPSQUEUESIZE.defaultValue));

        capabilities.put(DBOPSQUEUESIZE, props.getInt("Agent." + agentName + DBOPSQUEUESIZE.propSuffix,
                (Integer) DBOPSQUEUESIZE.defaultValue));

        capabilities.put(DBOPSBATCHSIZE, props.getInt("Agent." + agentName + DBOPSBATCHSIZE.propSuffix,
                (Integer) DBOPSBATCHSIZE.defaultValue));

        capabilities.put(RECOVERYPAGESIZE, props.getInt("Agent." + agentName + RECOVERYPAGESIZE.propSuffix,
                (Integer) RECOVERYPAGESIZE.defaultValue));

        capabilities.put(READONLY, props.getBoolean("Agent." + agentName + READONLY.propSuffix,
                (Boolean) READONLY.defaultValue));

        capabilities.put(KEEPHANDLES, props.getBoolean("Agent." + agentName + KEEPHANDLES.propSuffix,
                (Boolean) KEEPHANDLES.defaultValue));

        capabilities.put(CACHELISTENER, props.getBoolean("Agent." + agentName + CACHELISTENER.propSuffix,
                (Boolean) CACHELISTENER.defaultValue));

        capabilities.put(RECOVERONSTARTUP, props.getBoolean("Agent." + agentName + RECOVERONSTARTUP.propSuffix,
                (Boolean) RECOVERONSTARTUP.defaultValue));

        capabilities.put(LENIENT, props.getBoolean("Agent." + agentName + LENIENT.propSuffix,
                (Boolean) LENIENT.defaultValue));

        boolean sharedNothing = props.getString(SystemProperty.BACKING_STORE_TYPE.getPropertyName(), "None").matches("Shared.*Nothing");
        capabilities.put(CHECKDUPLICATES, props.getBoolean("Agent." + agentName + CHECKDUPLICATES.propSuffix,
        		(Boolean) CHECKDUPLICATES.defaultValue) && (sharedNothing == false));

        capabilities.put(PUBLISHTXN, props.getBoolean("Agent." + agentName + PUBLISHTXN.propSuffix,
                (Boolean) PUBLISHTXN.defaultValue));

		boolean concurrentWM = props.getBoolean("Agent." + agentName + CONCURRENTWM.propSuffix, (Boolean)CONCURRENTWM.defaultValue);
        capabilities.put(CONCURRENTWM, concurrentWM);

        capabilities.put(CHANNELCOMMAND, props.getString("Agent." + agentName + CHANNELCOMMAND.propSuffix,
                (String) CHANNELCOMMAND.defaultValue));
       
        boolean cacheAside = props.getBoolean(SystemProperty.CLUSTER_IS_CACHE_ASIDE.getPropertyName(), false);
        capabilities.put(ENABLEPARALLELOPS, props.getBoolean("Agent." + agentName + ENABLEPARALLELOPS.propSuffix,
                cacheAside && concurrentWM || (Boolean) ENABLEPARALLELOPS.defaultValue));

        // prerequisite, cluster should be set with backing store and cache aside persistence
        boolean updateCache = cacheAside ? props.getBoolean("Agent." + agentName + UPDATECACHE.propSuffix, (Boolean) UPDATECACHE.defaultValue) : (Boolean) UPDATECACHE.defaultValue;
        capabilities.put(UPDATECACHE, updateCache);

        return capabilities;
    }

    /**
     * Return String form of all the capabilities
     *
     * @param agentCapabilities
     * @return
     */
    public static String toString(EnumMap<AgentCapability, Object> agentCapabilities, boolean includeCacheAside) {
        StringBuilder builder = new StringBuilder();
        
        builder.append("\n--MaxActive=" + agentCapabilities.get(MAXACTIVE));
        builder.append("\n--Priority=" + agentCapabilities.get(PRIORITY));
        builder.append("\n--ConcurrentRETE Enabled=" + agentCapabilities.get(CONCURRENTWM));
        builder.append("\n--ParallelWrites Enabled=" + agentCapabilities.get(ENABLEPARALLELOPS));
        builder.append("\n--DuplicateCheck Enabled=" + agentCapabilities.get(CHECKDUPLICATES));
        builder.append("\n--L1CacheSize=" + agentCapabilities.get(L1CACHESIZE));
        builder.append("\n--Cache ThreadPool=" + agentCapabilities.get(THREADCOUNT));
        builder.append("\n--Cache QueueSize=" + agentCapabilities.get(CACHEOPSQUEUESIZE));

        if (includeCacheAside) {
            builder.append("\n--DB ThreadPool=" + agentCapabilities.get(DBTHREADCOUNT));
            builder.append("\n--DB QueueSize=" + agentCapabilities.get(DBOPSQUEUESIZE));
        }
        return builder.toString();
    }

    public static <K> K getValue(EnumMap<AgentCapability, Object> agentCapabilities, AgentCapability key,
                                 Class<K> returnType) {
        Object value = agentCapabilities.get(key);
        // System.err.println("Agent : " + key + " = " + value); 
        // This call is made excessively like KEEPHANDLES=10x per txn 
        return returnType.cast(value);
    }
}
