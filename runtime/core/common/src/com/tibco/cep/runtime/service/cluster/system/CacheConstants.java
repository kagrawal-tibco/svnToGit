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

package com.tibco.cep.runtime.service.cluster.system;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Mar 17, 2008
 * Time: 8:52:39 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 17, 2007
 * Time: 10:57:48 AM
 * To change this template use File | Settings | File Templates.
 */
public interface CacheConstants {

    public final static String CLUSTERNAME = "ClusterName";
    public final static String CONFIGFILE = "ConfigFile";
    public final static String MASTERCACHENAME = "MasterCacheName";
    public final static String MULTICASTENABLED = "MulticastEnabled";
    public final static String CLUSTERADDRESS = "ClusterAddress";

    public final static String CLUSTERPORT = "ClusterPort";
    public final static String WKA = "WKA";
    public final static String RECOVERYCUSTOMFACTORY = "RecoveryCustomFactory";
    public final static String TOPOLOGY = "Topology";
    public final static String LOCALADDRESS = "LocalAddress";
    public final static String LOCALPORT = "LocalPort";


    public final static String MINCACHESERVERS = "MinCacheServers";
    public final static String SERVICETHREADS = "ServiceThreadCount";
    public final static String USEAUTOMATEDRECOVERY = "UseAutomatedRecovery";
    public final static String RECOVERYTHREADPOOL = "RecoveryThreadPool";
    public final static String DEPLOY = "Deploy";
    public final static String LOAD_ALL_OBJECTS = "LoadObjectsOnStartup";
    public final static String LOAD_BATCHSIZE = "LoadBatchSize";

    public final static String CONCEPTS = "Concepts";
    public final static String PROPERTIES = "Properties";
    public final static String STATEMACHINES = "StateMachines";
    public final static String CHILDSTATES = "Child-States";
    public final static String EVENTS = "Events";
    public final static String INTERNAL = "Internal";
    public final static String RULESETS = "RuleSets";
    public final static String CHANNELS = "Channels";
    public final static String DESTINATIONS = "Destinations";


    public final static String CACHETYPE = "CacheType";
    public final static String CACHEMODE = "CacheMode";
    public final static String RECOVERYMODE = "RecoveryMode";
    public final static String INDEX = "Index";

    public final static String AGENTNAME = "AgentName";
    public final static String AGENTCLUSTER = "Cluster";


    // Start of Recovery Modes
    public final static int RECOVERY_MODE_NOPRELOAD = 0;
    public final static int RECOVERY_MODE_PRELOAD_SYNC = 1;
    public final static int RECOVERY_MODE_PRELOAD_ASYNC = 2;

    public final static String[] ENUM_RECOVERYMODES_S = {
            "Dont Pre Load",
            "Pre Load Objects Synchronously",
            "Pre Load Objects Asynchronously"};

    public final static Integer[] ENUM_RECOVERYMODES_I = {
            RECOVERY_MODE_NOPRELOAD,
            RECOVERY_MODE_PRELOAD_SYNC,
            RECOVERY_MODE_PRELOAD_ASYNC};
    // End of Recovery Modes

    // Start of Cache Modes
    public final static int CACHEMODE_WMANDCACHE = 0;
    public final static int CACHEMODE_CACHEONLY = 1;
    public final static int CACHEMODE_WMONLY = 2;

    public final static String[] ENUM_CACHEMODES_S = {
            "Cache and Working Memory",
            "Cache Only",
            "Working Memory Only"};


    public final static Integer[] ENUM_CACHEMODES_I = {
            CACHEMODE_WMANDCACHE,
            CACHEMODE_CACHEONLY,
            CACHEMODE_WMONLY};
    // End of Cache Modes

    // Start of Cache Types
    public final static int CACHETYPE_LOCAL = 0;
    public final static int CACHETYPE_DISTRIBUTED = 1;
    public final static int CACHETYPE_REPLICATED = 2;

    public final static String[] ENUM_CACHETYPES_S = {
            "Local Cache",
            "Distributed Cache",
            "Replicated Cache"};

    public final static Integer[] ENUM_CACHETYPES_I = {
            CACHETYPE_LOCAL,
            CACHETYPE_DISTRIBUTED,
            CACHETYPE_REPLICATED};
    // End of Cache Types

    public final static String NEAR_LIMIT = "Near Limit";
    public final static String BACKING_STORE = "Backing Store";
    public final static String WRITE_DELAY = "Write Delay";
    public final static String WRITE_REQUEUE = "Write Requeue Threshold";


    /* Extended Property Types */
    public final static int TYPE_STRING = 0;
    public final static int TYPE_INTEGER = 1;
    public final static int TYPE_LONG = 2;
    public final static int TYPE_DOUBLE = 3;
    public final static int TYPE_DATETIME = 4;
    public final static int TYPE_BOOLEAN = 5;
    public final static int TYPE_OBJECT = 6;

    public final static int TYPE_STRINGARRAY = 7;
    public final static int TYPE_INTEGERARRAY = 8;
    public final static int TYPE_LONGARRAY = 9;
    public final static int TYPE_DOUBLEARRAY = 10;

    public final static Integer MASTER_LOCK = new Integer(-1);
    public final static Integer AGENTNEXTID = new Integer(-2);
    public final static Integer CLUSTERSTATE = new Integer(-3);
    public final static Integer ENTITYID = new Integer(-4);
    public final static Integer COMMAND = new Integer(-5);

    public final static int AGENTTYPE_INFERENCE = 0;
    public final static int AGENTTYPE_CACHESERVER = 1;
    public final static int AGENTTYPE_CHANNELS = 2;
    public final static int AGENTTYPE_MASTER = 3;


    public final static String[] AGENTTYPES =
            {"Inference Agent", "Cache Agent", "Channel Agent", "Master Agent"};


    public final static String AGENT_MINACTIVE = "MinAgentActive";
    public final static String AGENT_MININACTIVE = "MinAgentInActive";
    public final static String AGENTTYPE_INFERENCE_PARTITION = "Partition";
    public final static String AGENTTYPE_INFERENCE_PARTITIONFILTER = "Partition Filter";
    public final static String AGENT_DESTINATIONENABLE = "EnableDestination";
    public final static String AGENT_DESTINATIONPREPROCESSOR = "Preprocessor";
    public final static String AGENT_DESTINATIONPWEIGHT = "Weight";
    public final static String AGENT_RULESETDEPLOY = "EnableRuleSet";
    public final static String AGENT_PRIORITY = "Priority";
}
