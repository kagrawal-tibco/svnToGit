/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 30/6/2010
 */

package com.tibco.cep.runtime.util;

/*
 * Author: Ashwin Jayaprakash Date: Apr 10, 2009 Time: 4:04:43 PM
 */
public enum SystemProperty {

	AS_DISCOVER_URL("be.engine.cluster.as.discover.url"),
	
	AS_LISTEN_URL("be.engine.cluster.as.listen.url"),
	
	AS_LOCK_TTL("be.engine.cluster.as.lock.ttl"),

    AS_LOCK_SCOPE("be.engine.cluster.as.lock.scope"),
    
    AS_SECURITY_ENABLE("be.engine.cluster.as.security.enable", false, true),

    // Default AS security mode is Requester
    // At least one node in the cluster must take on the Controller mode.
    AS_SECURITY_MODE_ROLE("be.engine.cluster.as.security.mode.role","Requester"),

    AS_SECURITY_FILE("be.engine.cluster.as.security.file"),
    
    AS_SECURITY_FILE_IDENTITY("be.engine.cluster.as.security.file.identity.password"),
	
	AS_LOG_DIR("be.engine.cluster.as.log.dir"),
	
	AS_LOG_FILE_NAME("be.engine.cluster.as.log.filename"),

	AS_LOG_LEVEL("be.engine.cluster.as.log.level"),
	
	AS_PROTOCOL_TIMEOUT("as.protocol-timeout"),

    AS_CHANNEL_POOLSIZE("be.engine.channel.as.poolsize", 100),
    
    AS_CHANNEL_QUERYLIMIT("be.engine.channel.as.querylimit", -1),
    
    AS_CONNECTION_TIMEOUT("be.engine.cluster.as.connection.timeout", 10000),

	AS_READ_TIMEOUT("be.engine.cluster.as.read.timeout"),
	
    AS_WRITE_TIMEOUT("be.engine.cluster.as.write.timeout"),
    
    AS_FILE_SYNC_INTERVAL("be.engine.cluster.as.file.sync.interval", 10000),

    // The member_timeout parameter specifies how many milliseconds ActiveSpaces 
    // waits for a member to reconnect if it loses connection to the metaspace. 
    // The default value is 30 seconds
    AS_MEMBER_TIMEOUT("be.engine.cluster.as.member.timeout"),
    
    // Specifies how many milliseconds ActiveSpaces waits for a remote member to reconnect before giving up on it.
    // AS's default value is 600000, BE default value is 120000
    AS_REMOTE_MEMBER_TIMEOUT("be.engine.cluster.as.remote.member.timeout"),
    
    AS_REMOTE_MEMBER_ALLOW_RECONNECT("be.engine.cluster.as.remote.member.allow.connections.reset", false, true),
    
    // The cluster_suspend_threshold parameter specifies the number of host connections 
    // that can be lost before the cluster goes into a suspended state. When suspended, 
    // members cannot leave or join the cluster. If connectivity is lost for a seeder 
    // member of a space, doing a read/write for that space might cause a protocol timeout.
    // The default value is -1 (never suspend membership operations).
    AS_CLUSTER_SUSPEND_THRESHOLD("be.engine.cluster.as.suspend.threshold"),

    AS_REMOTE_LISTEN_URL("be.engine.cluster.as.remote.listen.url"),
	
	AS_SHUTDOWN_WAIT_MILLIS("be.engine.cluster.as.shutdown.wait.millis"),
	
	AS_TUPLE_EXPLICIT_STORE_TIMEZONES("be.engine.cluster.as.tuple.explicit.store_timezones"),
    AS_TUPLE_EXPLICIT_STORE_HISTORY("be.engine.cluster.as.tuple.explicit.store_history"),
	
	AS_WORKERTHREADS_COUNT("be.engine.cluster.as.workerthreads.count"),
	
	AS_EXTID_INDEX("be.engine.cluster.as.extid.index"),
	
    /*
     * Enabler for the rest-based query service. Disabled by default
     */
    QUERY_RESTSERVICE_ENABLED("be.engine.cluster.query.restservice.enabled",Boolean.FALSE, Boolean.TRUE),

	/* Cluster GMP Lock wait (a.k.a. lock-timeout) */
    CLUSTER_LOCK_LOG_LEVEL("be.engine.cluster.lock.log.level", "info"),
	CLUSTER_LOCK_WAIT("be.engine.cluster.lock.wait", "10000"),

    BACKING_STORE_CACHE_LOADER_CLASS("be.backingstore.cacheLoaderClass"),
    
    BACKING_STORE_DB_TYPE("be.backingstore.database.type"),

    /* Shared All , Shared Nothing or None  */
    BACKING_STORE_TYPE("be.backingstore.type"),

    BACKING_STORE_DB_ENFORCE_POOLS_PRIMARY("be.backingstore.dburi.pool.enforce.0"),
    BACKING_STORE_DB_ENFORCE_POOLS_SECONDARY("be.backingstore.dburi.pool.enforce.1"),
    BACKING_STORE_PERSISTER_DISTRIBUTED("be.backingstore.persister.distributed"),

    BE_HOME("tibco.env.BE_HOME"),

    BW_REPO_URL("tibco.bwrepourl"),

    /**
     * AJ: public
     */
    CACHE_SERVER("be.engine.cacheServer"),

    /**
     * Default Cluster Mode (cache, memory, or cacheAndMemory)
     */
    CLUSTER_MODE("be.engine.cluster.mode"),

    CLUSTER_AUTO_FAILOVER("be.engine.cluster.autoFailover"),
    CLUSTER_AUTO_STARTUP("be.engine.cluster.autoStartup"),
    CLUSTER_SHUTDOWN_STRATEGY("be.engine.cluster.shutdown.strategy"),

    CLUSTER_CACHE_CONFIG_FILE("be.engine.cluster.cacheConfigFile"),

    /**
     * PD: Set the cluster cache type to either replicated or distributed, default is replicated.
     */
    CLUSTER_CACHE_TYPE("be.engine.cluster.cacheType"),

    CLUSTER_CONFIG("com.tibco.be.config"),
    CLUSTER_CONFIG_PATH("com.tibco.be.config.path"),

    /**
     * AJ: internal
     */
    CLUSTER_ENTITY_MEDIATOR("be.engine.cluster.entityMediator"),

    /**
     * AJ: internal
     */
    CLUSTER_EXTEND("be.engine.extend"),

    /**
     * AJ: internal
     */
    CLUSTER_EXTERNAL_CLASSES_CLASSLOADER("be.engine.cluster.externalClasses.classLoader"),

    /**
     * AJ: internal
     */
    CLUSTER_EXTERNAL_CLASSES_PATH("be.engine.cluster.externalClasses.path"),

    /**
     * Hot deploy directory for Rule template instances.
     */
    CLUSTER_RULETEMPLATE_INSTANCE_DEPLOYER_DIR("be.cluster.ruletemplateinstances.deploy.dir"),

    /**
     * Property for specifying which packages to be excluded 
     */
    CLUSTER_EXTERNAL_CLASSES_PACKAGE_EXCLUSIONS("be.engine.cluster.externalClasses.packageExclusions"),
    
    CLUSTER_FAILOVER_INTERVAL("be.engine.cluster.failoverInterval"),

    /**
     * PD: Sets the Cache OM to use backing store enabled caching schemes.
     * AJ: public
     */
    CLUSTER_HAS_BACKING_STORE("be.engine.cluster.hasBackingStore", "false"),

    /**
     * FI: Eliminates database lookups, following cache-misses.
     */
    CLUSTER_CACHE_FULLY_LOADED("be.engine.cluster.isObjectCacheFullyLoaded", "false"),

    /**
     * PD: Sets the Cache OM to use cache aside serialization,
     * where the RtcTxn objects are written to the store directly
     * and the cache is mainly used for retrieval purposes.
     * AJ: public
     */
    CLUSTER_IS_CACHE_ASIDE("be.engine.cluster.isCacheAside", "false"),

    /**
     * PD: Sets whether the entity caches are limited.
     * This uses the limited caching scheme and the size limit needs to be set.
     * AJ: public
     */
    CLUSTER_IS_CACHE_LIMITED("be.engine.cluster.isCacheLimited"),

    /**
     * FI: Sets whether the deleted entities should be removed from
     * object-table (keepDeleted=false [default]), or simply 
     * marked as deleted (keepDeleted=true).
     */
    CLUSTER_KEEP_DELETED("be.engine.cluster.keepDeleted"),
    CLUSTER_CLEANUP("be.engine.cluster.cleanup"),
    CLUSTER_TRUNCATE_ON_RESTART("be.engine.cluster.truncateOnRestart"),
    CLUSTER_MIGRATE_OBJECTTABLE("be.engine.cluster.migrateObjectTable"),

    /**
     * FI: Use descriptive cache names like 
     *   dist-unlimited-bs-TestBQL--be_gen_Concepts_Agreement (default)
     * or non-descriptive cache names like
     *   TestBQL--be_gen_Concepts_Agreement
     */
    CLUSTER_HAS_DESCRIPTIVE_NAMES("be.engine.cluster.cacheNaming.isDescriptive", "true"),

    /**
     * PD: Sets the number of objects to hold for a given cache scheme.
     * AJ: public
     */
    CLUSTER_LIMITED_SIZE("be.engine.limited.cache.back.size.limit"),

    /**
     * PD: If delay is 0 then objects written to the cache
     * are serialized to the store synchronously, else async.
     * AJ: Internal
     */
    CLUSTER_LIMITED_TIME("be.engine.limited.cache.expiry.delay"),

    /**
     * AJ: public
     */
    CLUSTER_BACKUP_COUNT("be.engine.cluster.cache.backup.count"),

    /**
     * FI: public
     */
    CLUSTER_MIN_SPACE_SEEDERS("be.engine.cluster.as.minSeeders"),
    
    /**
     * AJ: public (a.k.a. Quorum)
     */
    CLUSTER_MIN_CACHE_SERVERS("be.engine.cluster.minCacheServers"),

    /**
     * AJ: limited
     */
    CLUSTER_MIN_CACHE_SERVERS_STRICT("be.engine.cluster.minCacheServers.strict"),

    /**
     * AJ: limited
     */
    CLUSTER_MIN_CACHE_SERVERS_STRICT_SELF_REPAIR("be.engine.cluster.minCacheServers.strict.selfRepair"),

    /**
     * AJ: limited
     */
    CLUSTER_CACHE_SERVER_QUORUM_CHECK_LENIENT("be.engine.cluster.quorumCheck.setLenient"),

    /**
     * @public
     */
    CLUSTER_NODE_ISSEEDER("be.engine.cluster.isSeeder", false),
    CLUSTER_NODE_ISFORCED("be.engine.cluster.forceSeeder", false),

    /**
     * PD: Changes the behavior of the OM when multiple engines
     * work simultaneously and respond to each others changes.
     * AJ: public
     */
    CLUSTER_MULTI_ENGINE_ON("be.engine.cluster.multiEngineOn"),
    
    CLUSTER_NAME("cluster.name", "standalone-cluster"),
    CLUSTER_OBJECT_TABLE_SCHEME("be.engine.cluster.objectTableScheme"),

    CLUSTER_USEOBJECTTABLE("be.backingstore.useobjecttable"),
    
    /**
     * PD: Not used any more.
     * AJ: internal
     */
    CLUSTER_OPTIMIZE_SERIALIZATION("be.engine.cluster.serialization.optimize"),
    
    CLUSTER_QUEUE_SIZE("be.engine.cluster.queueSize"),

    /**
     * PD: This is mostly used for identification purposes according
     * to tangosol docs as the backup count of objects are distributed
     * across nodes, it could be that the master and backup both belong
     * to same site but different nodes, it is wrong assumption that
     * specifying the site will distribute across different site nodes.
     * AJ: internal
     */
    CLUSTER_VERSION_ID("be.engine.cluster.version.id"),
    CLUSTER_SITE_ID("be.engine.cluster.siteId"),
    CLUSTER_IS_MULTISITE("be.engine.cluster.multisite"),
    
    CLUSTER_USE_DB_BATCHING("be.engine.cluster.useDBBatching"),
    CLUSTER_USE_PRIMARY_DATA_SOURCE("be.engine.cluster.usePrimaryDatasource"),

    CLUSTER_LOCKLESS_PROVIDER("be.engine.cluster.lockless.provider"),

    PROP_TUPLE_EXPLICIT("be.engine.cluster.as.tuple.explicit", false),
    PROP_TUPLE_USEBROWSER("be.engine.cluster.as.tuple.browser", true),
    PROP_TUPLE_USECLEAR("be.engine.cluster.as.tuple.clear", true),

    PROP_AS_SET_FORGET("be.engine.cluster.as.set.forget", true),

    //Shared-All: Berkeley DB Retry interval and num_retries
    CLUSTER_AS_BDB_RETRY_INTERVAL("be.engine.cluster.as.bdb.retry_interval", 300),
    CLUSTER_AS_BDB_NUM_RETRIES("be.engine.cluster.as.bdb.num_retries", 10),

    /**
     * The data store path for SN /Shared-All BDB
     * This is the override property
     */
    CLUSTER_AS_DATA_STORE_PATH("be.engine.cluster.as.storePath"),

    /**
     * Shared-all persistence for AS via BDB
     */
    CLUSTER_AS_USE_SHARE_ALL("be.engine.cluster.as.useSharedAll"),

    /**
     * Property to deactivate channels preventing it from binding.
     * GG: not exposed in BE, for ASG purposes at this time.
     */
    CHANNEL_DEACTIVATE("be.channel.deactivate"),

    DB_CONCEPTS_CONNECTION_CHECK_INTERVAL("be.dbconcepts.connection.check.interval"),
    DB_CONCEPTS_CONNECTION_RETRY_COUNT("be.dbconcepts.connection.retry.count"),
    DB_CONCEPTS_DBURI("be.dbconcepts.dburi"),
    DB_CONCEPTS_ENABLED("be.dbconcepts.enabled"),
    DB_CONCEPTS_POOL_INITIAL("be.dbconcepts.pool.initial"),
    DB_CONCEPTS_POOL_MAX("be.dbconcepts.pool.max"),
    DB_CONCEPTS_POOL_MIN("be.dbconcepts.pool.min"),
    DB_CONCEPTS_POOL_INACTIVITY_TIMEOUT("be.dbconcepts.pool.inactivityTimeout"),
    DB_CONCEPTS_POOL_WAIT_TIME("be.dbconcepts.pool.waitTimeout"),
    DB_CONCEPTS_PROPERTY_CHECK_INTERVAL("be.dbconcepts.pool.PropertyCheckInterval"),

    DEBUGGER_SERVICE_ENABLED("com.tibco.cep.debugger.service.enabled", Boolean.FALSE, Boolean.TRUE),

    DESTINATION_QUEUE_CACHE_SIZE("be.destination.queue.cache.size"),
    DESTINATION_TOPIC_CACHE_SIZE("be.destination.topic.cache.size"),

    //Public: To be used when the engine's host has multiple network cards. The value of this property must be set
    //to the IP of the desired NIC. It is necessary in order for the domain to be registered with the correct IP, and not
    //with the default IP. Default value is localhost
    ENGINE_HOST_ADDRESS("be.engine.hostaddress","localhost"),
    ENGINE_NAME("be.engine.name"),
    ENGINE_IS_MM_TOOLS("be.engine.is.mm.tools"),

    FUNCTION_CATALOG_URLS("be.function.catalog.urls"),
    /**
     * For RV message encoding CR:1-AK8TMV
     */
    MESSAGE_ENCODING("MessageEncoding", "ISO8859-1", "UTF-8"),

    // To enable monitoring only scorecards and no other ontology types (e.g. concepts, events, ...)
    MONITOR_SCORECARDS_ONLY("be.engine.monitor.scorecards.only"),

    RULE_ADMINISTRATOR("com.tibco.cep.runtime.session.RuleAdministrator"),
    HAWK_ENABLED("HawkEnabled"),
    HAWK_SERVICE("TIBHawkService"),
    HAWK_DAEMON("TIBHawkDaemon"),
    HAWK_NETWORK("TIBHawkNetwork"),

    HOT_DEPLOY_ENABLED("be.engine.hotDeploy.enabled"),

    INJECT_EXTERNALIZABLE_LITE("be.engine.inject.externalizableLite", Boolean.FALSE, Boolean.TRUE),

    JMX_ENABLED("com.sun.management.jmxremote"),
    JMX_PORT("com.sun.management.jmxremote.port"),
    JMX_SSL_ENABLED("com.sun.management.jmxremote.ssl"),

    /**
     * Enable/disable the new metrics publishing
     */
    METRIC_PUBLISH_ENABLE("com.tibco.be.metric.publish.enable",Boolean.FALSE, Boolean.TRUE),

    METRIC_PUBLISH_INTERVAL("com.tibco.be.metric.publish.interval"),

    METRIC_RESOLUTION("com.tibco.be.metric.resolution"),
    METRIC_SLAB_COUNT("be.engine.slab.process.count"),

    OBJECT_TABLE_BACK_SIZE_LIMIT("objectTable.back.size.limit"),
    OBJECT_TABLE_EVICTION_BATCH_SIZE("objectTable.eviction.batch.size"),
    OBJECT_TABLE_EXPIRY_BATCH_SIZE("objectTable.expiry.batch.size"),
    OBJECT_TABLE_EVICTION_DELAY_INTERVAL("objectTable.eviction.delay.interval"),

    OM_CLASS("omClass"),

    /**
     * AJ: public
     */
    PRELOAD("be.engine.cluster.preload"),

    //PRELOAD_CACHES("be.engine.cluster.preload.caches"),

    /**
     * AJ: public
     */
    PRELOAD_FETCH_SIZE("be.engine.cluster.preload.fetchSize"),
    
    PRELOAD_HANDLES("be.engine.cluster.preload.handles"),

    PROCESSING_UNIT_ID("com.tibco.be.config.unit.id"),
    PROCESSING_UNIT_ID_DEFAULT("com.tibco.be.config.unit.id.default"),

    RV_DOMAIN("Domain"),
    RV_DAEMON("RvDaemon"),
    RV_SERVICE("RvService"),
    RV_REMOTE_DAEMON("RemoteRvDaemon"),
    RV_NETWORK("RvNetwork"),

    SHARED_QUEUE_SIZE("com.tibco.cep.runtime.scheduler.queueSize"),
    SHARED_QUEUE_WORKERS("com.tibco.cep.runtime.scheduler.default.numThreads"),

    THREADPOOL_SHUTDOWN_TIMEOUT("com.tibco.cep.runtime.threadpool.shutdown.timeout.seconds"),
    
    TRACE_ENABLED("be.trace.enable"),
    TRACE_FILE_APPEND("be.trace.log.append"),
    TRACE_FILE_DIR("be.trace.log.dir"),
    TRACE_FILE_ENABLED("be.trace.log.enable"),
    TRACE_FILE_MAX_SIZE("be.trace.log.maxsize"),
    TRACE_FILE_MAX_NUM("be.trace.log.maxnum"),
    TRACE_FILE_NAME("be.trace.log.fileName"),
    TRACE_LAYOUT_ENABLED("be.trace.layout.enabled"),
    TRACE_LAYOUT_CLASS_NAME("be.trace.layout.class.name"),
    TRACE_LAYOUT_CLASS_ARG("be.trace.layout.class.arg"),
    TRACE_LOGGER_CLASS_NAME("com.tibco.cep.logger.factory"),
    TRACE_NAME("be.trace.name"),
    TRACE_ROLE("be.trace.role"),
    TRACE_ROLES("be.trace.roles"),
    TRACE_TERM_ENABLED("be.trace.term.enable"),
    TRACE_TERM_ENCODING("be.trace.term.encoding"),
    TRACE_TERM_SYSERR_REDIRECT("be.trace.term.syserr.redirect"),
    TRACE_TERM_SYSOUT_REDIRECT("be.trace.term.sysout.redirect"),
    TRACE_TRACE("be.trace.trace"),
    TRACE_DATE_FORMAT("be.trace.date.format"),

    /**
     * AJ: internal
     */
    VM_DAOPROVIDER_CLASSNAME("be.dao.provider.className","com.tibco.cep.runtime.service.dao.impl.tibas.ASDaoProvider","com.tibco.cep.runtime.service.cluster.om.impl.CoherenceDaoProvider"),
    VM_CLUSTER_CONFIG("be.cluster.config"),

    /**
     * Need a reference to this
     */
    VM_DAOPROVIDER_TYPE("be.dao.provider.type", "coherence", "tibco"),

    /**
     * AJ: internal
     */
    VM_LOCALCACHE_CLASSNAME("be.om.localCache.className"),

    /**
     * AJ: public
     */
    VM_LOCALCACHE_SIZE("be.om.localCache.size"),

    /**
     * AJ: public
     */
    VM_LOCALCACHE_EXPIRY_MILLIS("be.om.localCache.expiryMillis"),

    /**
     * AJ: public
     */
    VM_NETWORK_MODE_STANDALONE("be.network.mode.standalone", Boolean.FALSE, Boolean.TRUE),

    /**
     * AJ: public
     * For remote Query filters. 
     */
    VM_SPECIAL_CACHE_OM("be.agent.cache.specialom", Boolean.FALSE, Boolean.TRUE),

    /**
     * AJ: public
     * For remote Query filters. 
     */
    VM_SPECIAL_CACHE_OM_MAX_THREADS("be.agent.cache.specialom.maxthreads"),

    /**
     * AJ: internal
     */
    VM_NETWORK_CLUSTER_NAME("be.network.cluster.name"),

    /**
     * AJ: internal
     */
    @Deprecated
    VM_TXNMGR_CLASSNAME("be.om.txnMgr.className"),

    TIBCO_SECURITY_VENDOR("TIBCO_SECURITY_VENDOR"),    

    USE_CODEGEN_ANNOTATION("be.codegen.annotation", Boolean.FALSE, Boolean.TRUE),
    //-------------

    //Non-standard properties.

    /*
    Originally added for Barclays POC where complex graph processing was being performed in the
    Pre-Proc without getting into complex RTC cycles.

    Default is true. Setting to false allows Concepts to be modified in the Pre-Proc and those
    changes will not be recorded.
    */
    PERFORM_CONCEPT_SESSION_CHECK("be.ontology.concept.session.check", Boolean.FALSE, Boolean.TRUE),
	
    ADD_REMOVE_ONLY_CONTAINED_CONCEPT_PROPERTY_VALUE("be.backingstore.containedconceptarray.addremoveonly", Boolean.FALSE, Boolean.TRUE),
    
    // This actually affects all property types in DBAdapter causing it not to write out unmodified properties of any type
	DONT_PERSIST_UNMODIFIED_CONTAINED_CONCEPT_PROPERTY_VALUE("be.backingstore.unmodified.skip", Boolean.FALSE, Boolean.TRUE),
	
    DATAGRID_RECOVERY_THREADS("be.engine.cluster.recovery.threads", 4),
    DATAGRID_RECOVERY_BATCHSIZE("be.engine.cluster.recovery.batchsize", 10000),
    DATAGRID_RECOVERY_DISTRIBUTED("be.engine.cluster.recovery.distributed", Boolean.FALSE, Boolean.TRUE),
    DATAGRID_RECOVERY_GENERALIZED_STRATEGY("be.engine.cluster.recovery.strategy", "no_data_loss"),
    DATAGRID_RECOVERY_DISTRIBUTED_STRATEGY("be.engine.cluster.recovery.distributed.strategy", "no_data_loss"),
    DATAGRID_RECOVERY_DISTRIBUTED_BATCHPERNODE("be.engine.cluster.recovery.distributed.batchpernode", 1),
    DATAGRID_RECOVERY_DISTRIBUTED_BATCHSIZE("be.engine.cluster.recovery.distributed.batchsize", 1000000),
    DATAGRID_RECOVERY_DISTRIBUTED_MINBATCHSIZE("be.engine.cluster.recovery.distributed.minbatchsize", 100000),
    DATAGRID_RECOVERY_DISTRIBUTED_NUMBATCHES("be.engine.cluster.recovery.distributed.numBatches", 8),
    DATAGRID_RECOVERY_DISTRIBUTED_CHECKALLBATCHES("be.engine.cluster.recovery.distributed.checkallbatches", Boolean.FALSE, Boolean.TRUE),
    
    ENGINE_STARTUP("be.engine.startup.parallel", Boolean.FALSE, Boolean.TRUE),

    AS_LOGFILE_COUNT("be.engine.cluster.as.logfile.count"),

    AS_LOGFILE_SIZE("be.engine.cluster.as.logfile.size", 10000000),

    AS_LOGFILE_APPEND("be.engine.cluster.as.logfile.append", Boolean.TRUE, Boolean.FALSE),

    AS_HOST_AWARE_REPLICATION_ENABLE("be.engine.cluster.as.hostaware.enable", Boolean.TRUE, Boolean.FALSE),

	AS_HOST_AWARE_HOSTNAME("be.engine.cluster.as.hostaware.hostname"),
	
	AS_CONNECTION_RETRY_COUNT("be.engine.cluster.as.connection.retry.count"),

	//Controller node to be configured with a security policy file.
    AS_SECURITY_POLICY_FILE("be.engine.cluster.as.security.controller.policy.file"),

	//Identity of the Controller (plain-text or obfuscated)
	AS_SECURITY_CONTROLLER_IDENTITY("be.engine.cluster.as.security.controller.identity.password"),

	//Requester's token policy file path
    AS_SECURITY_TOKEN_FILE("be.engine.cluster.as.security.token.file"),
    
	//Requester's token identity
	AS_SECURITY_TOKEN_IDENTITY("be.engine.cluster.as.security.token.identity.password"),

	//Requester's X509 certificate file
	AS_SECURITY_REQUESTER_CERTKEYFILE("be.engine.cluster.as.security.requester.identity.keyfile"),

	//Requester's authentication domain name 
	AS_SECURITY_REQUESTER_DOMAINNAME("be.engine.cluster.as.security.domain"),

	//Requester's authentication username
	AS_SECURITY_REQUESTER_USERNAME("be.engine.cluster.as.security.username"),
  
	//Requester's authentication password (plain-text or obfuscated)
	AS_SECURITY_REQUESTER_PASSWORD("be.engine.cluster.as.security.password"),
	
	//Error handler function for transaction errors
	RTC_TXN_ERROR_FN ("be.engine.txn.error.function"),
	
	//Number of times to retry the transaction actions and sleep between retries.
	RTC_TXN_ERROR_ACTION_RETRY_COUNT ("be.engine.txn.action.retrycount"),
	RTC_TXN_ERROR_ACTION_RETRY_SLEEP ("be.engine.txn.action.sleeptime"),

	//Number of times to retry the database transactions and sleep between retries.
	RTC_TXN_ERROR_DATABASE_RETRY_COUNT ("be.engine.txn.database.retrycount"),
	RTC_TXN_ERROR_DATABASE_RETRY_SLEEP ("be.engine.txn.database.sleeptime"),
	
	//Is transaction function recursive?
	RTC_TXN_ERROR_FN_RECURSIVE_RTC ("be.engine.txn.error.function.recursive.rtc"),
	
	//Don't throw EventPropertyModificationException if an event is modified during an RTC
	ALLOW_MODIFY_EVENT_IN_RTC ("be.engine.event.modification.allowed.during.rtc"),
	
	//Enables it for one case, not a universal setting
	ENABLE_ONE_JMS_SESSION_PER_MESSAGE("be.engine.channel.session_per_message.enable"),
	
	//This property would allow you to enable/disable starting of JMS channel in cache mode but for in-memory mode it would always start.
	//defaults to true
	ENABLE_JMS_CHANEL_START_ON_REQUEST_ENABLE("be.engine.channel.jms.start_on_request_enable", Boolean.TRUE, Boolean.FALSE),
	
	//This property allows setting delivery delay on a JMS destination, the placeholder ${destination_uri} should be replaced by destination uri
	JMS_DESTINATION_DELIVERY_DELAY("be.engine.channel.${destination_uri}.deliverydelay"),
	
	// Block size for the Ids for an entity
	CLUSTER_BLOCK_ID_SIZE ("be.engine.cluster.blockidsize"),
	
	HTTP_JSON_IGNORE_ROOT_ELEMENT("be.http.json.rootElement.ignore"),
	
	HTTP_ENABLE_HOSTNAME_VALIDATION("be.engine.channel.http.hostname.validation.enable", Boolean.TRUE, Boolean.FALSE),
	
	MQTT_CLIENT_PERSISTENCE_PATH("be.engine.channel.mqtt.client_persistence_path"),
	
	
	//Number of times to retry the cluster datagrid reads and sleep between retries.
	CLUSTER_DATAGRID_DB_RETRY_COUNT ("be.engine.cluster.datagrid.db.retrycount", Integer.MAX_VALUE),
	CLUSTER_DATAGRID_DB_RETRY_SLEEP ("be.engine.cluster.datagrid.db.sleeptime", 5000),
	
	// This property allow to override scheduler cache persistence policy in case of
	// shared nothing persistence
	CLUSTER_DATAGRID_SCHEDULER_PERSISTENCE_POLICY("be.engine.cluster.datagrid.scheduler.persistence.policy");
  
    //-------------

    private final String propertyName;

    private final Object[] validValues;

    SystemProperty(String propertyName, Object... validValues) {
        this.propertyName = propertyName;
        this.validValues = validValues;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Object[] getValidValues() {
        return validValues;
    }
}
