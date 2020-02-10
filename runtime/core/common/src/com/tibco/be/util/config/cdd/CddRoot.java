/**
 */
package com.tibco.be.util.config.cdd;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getAcceptCount <em>Accept Count</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getAddress <em>Address</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getAdhocConfig <em>Adhoc Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getAdhocConfigs <em>Adhoc Configs</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getAgent <em>Agent</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getAgentClasses <em>Agent Classes</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getAgents <em>Agents</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getAlertConfig <em>Alert Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getAlertConfigSet <em>Alert Config Set</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getAppend <em>Append</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getArg <em>Arg</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getAuthor <em>Author</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getBackingStore <em>Backing Store</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getBackupCopies <em>Backup Copies</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getBusinessworks <em>Businessworks</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getCache <em>Cache</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getCacheAgentClass <em>Cache Agent Class</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getCacheAgentQuorum <em>Cache Agent Quorum</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getCacheAside <em>Cache Aside</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getCacheLimited <em>Cache Limited</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getCacheLoaderClass <em>Cache Loader Class</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getCacheManager <em>Cache Manager</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getCacheMode <em>Cache Mode</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getCacheStorageEnabled <em>Cache Storage Enabled</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getCertificateKeyFile <em>Certificate Key File</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getChannel <em>Channel</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getCheckForDuplicates <em>Check For Duplicates</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getCheckForVersion <em>Check For Version</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getCheckInterval <em>Check Interval</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getCheckpointInterval <em>Checkpoint Interval</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getCheckpointOpsLimit <em>Checkpoint Ops Limit</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getChildClusterMember <em>Child Cluster Member</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getCipher <em>Cipher</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getCiphers <em>Ciphers</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getClass_ <em>Class</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getCluster <em>Cluster</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getClusterMember <em>Cluster Member</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getComment <em>Comment</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getConcurrentRtc <em>Concurrent Rtc</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getCondition <em>Condition</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getConnectionLinger <em>Connection Linger</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getConnectionTimeout <em>Connection Timeout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getConstant <em>Constant</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getController <em>Controller</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getDaemon <em>Daemon</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getDashboardAgentClass <em>Dashboard Agent Class</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getDataStorePath <em>Data Store Path</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getDate <em>Date</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getDbConcepts <em>Db Concepts</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getDbDir <em>Db Dir</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getDbUris <em>Db Uris</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getDefaultMode <em>Default Mode</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getDeleteRetracted <em>Delete Retracted</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getDestination <em>Destination</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getDestinationGroups <em>Destination Groups</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getDestinations <em>Destinations</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getDir <em>Dir</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getDiscoveryUrl <em>Discovery Url</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getDocumentPage <em>Document Page</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getDocumentRoot <em>Document Root</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getDomainName <em>Domain Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getDomainObject <em>Domain Object</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getDomainObjects <em>Domain Objects</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getEnabled <em>Enabled</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getEnableTracking <em>Enable Tracking</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getEncoding <em>Encoding</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getEncryption <em>Encryption</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getEnforcePools <em>Enforce Pools</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getEntityCacheSize <em>Entity Cache Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getEviction <em>Eviction</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getEvictOnUpdate <em>Evict On Update</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getExplicitTuple <em>Explicit Tuple</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getFiles <em>Files</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getFunctionGroups <em>Function Groups</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getFunctions <em>Functions</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getGetProperty <em>Get Property</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getHotDeploy <em>Hot Deploy</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getHttp <em>Http</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getIdentityPassword <em>Identity Password</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getInactivityTimeout <em>Inactivity Timeout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getIndex <em>Index</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getIndexes <em>Indexes</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getInferenceAgentClass <em>Inference Agent Class</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getInferenceEngine <em>Inference Engine</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getInitialSize <em>Initial Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getJobManager <em>Job Manager</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getJobPoolQueueSize <em>Job Pool Queue Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getJobPoolThreadCount <em>Job Pool Thread Count</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getKey <em>Key</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getKeyManagerAlgorithm <em>Key Manager Algorithm</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getLineLayout <em>Line Layout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getListenUrl <em>Listen Url</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getLoad <em>Load</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getLoadBalancerConfigs <em>Load Balancer Configs</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getLocalCache <em>Local Cache</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getLockTimeout <em>Lock Timeout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getLogConfig <em>Log Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getLogConfigs <em>Log Configs</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getMaxActive <em>Max Active</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getMaxNumber <em>Max Number</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getMaxProcessors <em>Max Processors</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getMaxSize <em>Max Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getMaxTime <em>Max Time</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getMemoryManager <em>Memory Manager</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getMessageEncoding <em>Message Encoding</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getMinSize <em>Min Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getMmAction <em>Mm Action</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getMmActionConfig <em>Mm Action Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getMmActionConfigSet <em>Mm Action Config Set</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getMmAgentClass <em>Mm Agent Class</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getMmAlert <em>Mm Alert</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getMmExecuteCommand <em>Mm Execute Command</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getMmHealthLevel <em>Mm Health Level</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getMmSendEmail <em>Mm Send Email</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getMmTriggerCondition <em>Mm Trigger Condition</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getMode <em>Mode</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getNotification <em>Notification</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getObjectManagement <em>Object Management</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getObjectTable <em>Object Table</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getPairConfig <em>Pair Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getPairConfigs <em>Pair Configs</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getParallelRecovery <em>Parallel Recovery</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getPersistenceOption <em>Persistence Option</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getPersistencePolicy <em>Persistence Policy</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getPolicyFile <em>Policy File</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getPort <em>Port</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getPreLoadCaches <em>Pre Load Caches</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getPreLoadEnabled <em>Pre Load Enabled</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getPreLoadFetchSize <em>Pre Load Fetch Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getPreLoadHandles <em>Pre Load Handles</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getPreProcessor <em>Pre Processor</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getPrimaryConnection <em>Primary Connection</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getPriority <em>Priority</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getProcess <em>Process</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getProcessAgentClass <em>Process Agent Class</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getProcessEngine <em>Process Engine</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getProcesses <em>Processes</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getProcessGroups <em>Process Groups</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getProcessingUnit <em>Processing Unit</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getProcessingUnits <em>Processing Units</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getProjection <em>Projection</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getProperty <em>Property</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getPropertyCacheSize <em>Property Cache Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getPropertyCheckInterval <em>Property Check Interval</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getPropertyGroup <em>Property Group</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getProtocol <em>Protocol</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getProtocols <em>Protocols</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getProtocolTimeout <em>Protocol Timeout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getProvider <em>Provider</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getQueryAgentClass <em>Query Agent Class</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getQueueSize <em>Queue Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getReadTimeout <em>Read Timeout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getReceiver <em>Receiver</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getRemoteListenUrl <em>Remote Listen Url</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getRequester <em>Requester</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getRetryCount <em>Retry Count</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getReverseReferences <em>Reverse References</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getRevision <em>Revision</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getRole <em>Role</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getRoles <em>Roles</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getRouter <em>Router</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getRule <em>Rule</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getRuleConfig <em>Rule Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getRules <em>Rules</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getRulesets <em>Rulesets</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getSecondaryConnection <em>Secondary Connection</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getSecurityConfig <em>Security Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getService <em>Service</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getSetProperty <em>Set Property</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getSharedAll <em>Shared All</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getSharedQueue <em>Shared Queue</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getShoutdownWait <em>Shoutdown Wait</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getShutdown <em>Shutdown</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getSize <em>Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getSkipRecovery <em>Skip Recovery</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getSocketOutputBufferSize <em>Socket Output Buffer Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getSsl <em>Ssl</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getStaleConnectionCheck <em>Stale Connection Check</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getStartup <em>Startup</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getStrategy <em>Strategy</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getSubject <em>Subject</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getSubscribe <em>Subscribe</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getSysErrRedirect <em>Sys Err Redirect</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getSysOutRedirect <em>Sys Out Redirect</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getTableName <em>Table Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getTcpNoDelay <em>Tcp No Delay</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getTerminal <em>Terminal</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getThreadAffinityRuleFunction <em>Thread Affinity Rule Function</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getThreadCount <em>Thread Count</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getThreadingModel <em>Threading Model</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getTokenFile <em>Token File</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getTrustManagerAlgorithm <em>Trust Manager Algorithm</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getTtl <em>Ttl</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getUri <em>Uri</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getUserName <em>User Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getUserPassword <em>User Password</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getVersion <em>Version</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getWaitTimeout <em>Wait Timeout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getWorkers <em>Workers</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getWorkerthreadsCount <em>Workerthreads Count</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getWriteTimeout <em>Write Timeout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getEntity <em>Entity</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getFilter <em>Filter</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getEntitySet <em>Entity Set</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getEnableTableTrimming <em>Enable Table Trimming</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getTrimmingField <em>Trimming Field</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getTrimmingRule <em>Trimming Rule</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getGenerateLVFiles <em>Generate LV Files</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getOutputPath <em>Output Path</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getLdmConnection <em>Ldm Connection</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getLdmUrl <em>Ldm Url</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getPublisher <em>Publisher</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getPublisherQueueSize <em>Publisher Queue Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getPublisherThreadCount <em>Publisher Thread Count</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getLiveviewAgentClass <em>Liveview Agent Class</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getCompositeIndexes <em>Composite Indexes</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CddRoot#getCompositeIndex <em>Composite Index</em>}</li>
 * </ul>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface CddRoot extends EObject {
	/**
	 * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mixed</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mixed</em>' attribute list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Mixed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' name=':mixed'"
	 * @generated
	 */
	FeatureMap getMixed();

	/**
	 * Returns the value of the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XMLNS Prefix Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XMLNS Prefix Map</em>' map.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_XMLNSPrefixMap()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
	 *        extendedMetaData="kind='attribute' name='xmlns:prefix'"
	 * @generated
	 */
	EMap<String, String> getXMLNSPrefixMap();

	/**
	 * Returns the value of the '<em><b>XSI Schema Location</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XSI Schema Location</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XSI Schema Location</em>' map.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_XSISchemaLocation()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
	 *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
	 * @generated
	 */
	EMap<String, String> getXSISchemaLocation();

	/**
	 * Returns the value of the '<em><b>Accept Count</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Accept Count</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Accept Count</em>' containment reference.
	 * @see #setAcceptCount(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_AcceptCount()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='accept-count' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getAcceptCount();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getAcceptCount <em>Accept Count</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Accept Count</em>' containment reference.
	 * @see #getAcceptCount()
	 * @generated
	 */
	void setAcceptCount(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Address</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Address</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Address</em>' containment reference.
	 * @see #setAddress(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Address()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='address' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getAddress();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getAddress <em>Address</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Address</em>' containment reference.
	 * @see #getAddress()
	 * @generated
	 */
	void setAddress(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Adhoc Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Adhoc Config</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Adhoc Config</em>' containment reference.
	 * @see #setAdhocConfig(LoadBalancerAdhocConfigConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_AdhocConfig()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='adhoc-config' namespace='##targetNamespace'"
	 * @generated
	 */
	LoadBalancerAdhocConfigConfig getAdhocConfig();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getAdhocConfig <em>Adhoc Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Adhoc Config</em>' containment reference.
	 * @see #getAdhocConfig()
	 * @generated
	 */
	void setAdhocConfig(LoadBalancerAdhocConfigConfig value);

	/**
	 * Returns the value of the '<em><b>Adhoc Configs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Adhoc Configs</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Adhoc Configs</em>' containment reference.
	 * @see #setAdhocConfigs(LoadBalancerAdhocConfigsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_AdhocConfigs()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='adhoc-configs' namespace='##targetNamespace'"
	 * @generated
	 */
	LoadBalancerAdhocConfigsConfig getAdhocConfigs();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getAdhocConfigs <em>Adhoc Configs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Adhoc Configs</em>' containment reference.
	 * @see #getAdhocConfigs()
	 * @generated
	 */
	void setAdhocConfigs(LoadBalancerAdhocConfigsConfig value);

	/**
	 * Returns the value of the '<em><b>Agent</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Agent</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Agent</em>' containment reference.
	 * @see #setAgent(AgentConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Agent()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='agent' namespace='##targetNamespace'"
	 * @generated
	 */
	AgentConfig getAgent();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getAgent <em>Agent</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Agent</em>' containment reference.
	 * @see #getAgent()
	 * @generated
	 */
	void setAgent(AgentConfig value);

	/**
	 * Returns the value of the '<em><b>Agent Classes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Agent Classes</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Agent Classes</em>' containment reference.
	 * @see #setAgentClasses(AgentClassesConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_AgentClasses()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='agent-classes' namespace='##targetNamespace'"
	 * @generated
	 */
	AgentClassesConfig getAgentClasses();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getAgentClasses <em>Agent Classes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Agent Classes</em>' containment reference.
	 * @see #getAgentClasses()
	 * @generated
	 */
	void setAgentClasses(AgentClassesConfig value);

	/**
	 * Returns the value of the '<em><b>Agents</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Agents</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Agents</em>' containment reference.
	 * @see #setAgents(AgentsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Agents()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='agents' namespace='##targetNamespace'"
	 * @generated
	 */
	AgentsConfig getAgents();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getAgents <em>Agents</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Agents</em>' containment reference.
	 * @see #getAgents()
	 * @generated
	 */
	void setAgents(AgentsConfig value);

	/**
	 * Returns the value of the '<em><b>Alert Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Alert Config</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alert Config</em>' containment reference.
	 * @see #setAlertConfig(AlertConfigConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_AlertConfig()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='alert-config' namespace='##targetNamespace'"
	 * @generated
	 */
	AlertConfigConfig getAlertConfig();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getAlertConfig <em>Alert Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Alert Config</em>' containment reference.
	 * @see #getAlertConfig()
	 * @generated
	 */
	void setAlertConfig(AlertConfigConfig value);

	/**
	 * Returns the value of the '<em><b>Alert Config Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Alert Config Set</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alert Config Set</em>' containment reference.
	 * @see #setAlertConfigSet(AlertConfigSetConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_AlertConfigSet()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='alert-config-set' namespace='##targetNamespace'"
	 * @generated
	 */
	AlertConfigSetConfig getAlertConfigSet();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getAlertConfigSet <em>Alert Config Set</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Alert Config Set</em>' containment reference.
	 * @see #getAlertConfigSet()
	 * @generated
	 */
	void setAlertConfigSet(AlertConfigSetConfig value);

	/**
	 * Returns the value of the '<em><b>Append</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Append</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Append</em>' containment reference.
	 * @see #setAppend(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Append()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='append' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getAppend();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getAppend <em>Append</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Append</em>' containment reference.
	 * @see #getAppend()
	 * @generated
	 */
	void setAppend(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Arg</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Arg</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Arg</em>' containment reference.
	 * @see #setArg(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Arg()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='arg' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getArg();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getArg <em>Arg</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Arg</em>' containment reference.
	 * @see #getArg()
	 * @generated
	 */
	void setArg(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Author</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Author</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Author</em>' containment reference.
	 * @see #setAuthor(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Author()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='author' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getAuthor();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getAuthor <em>Author</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Author</em>' containment reference.
	 * @see #getAuthor()
	 * @generated
	 */
	void setAuthor(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Backing Store</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Backing Store</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Backing Store</em>' containment reference.
	 * @see #setBackingStore(BackingStoreConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_BackingStore()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='backing-store' namespace='##targetNamespace'"
	 * @generated
	 */
	BackingStoreConfig getBackingStore();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getBackingStore <em>Backing Store</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Backing Store</em>' containment reference.
	 * @see #getBackingStore()
	 * @generated
	 */
	void setBackingStore(BackingStoreConfig value);

	/**
	 * Returns the value of the '<em><b>Backup Copies</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Backup Copies</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Backup Copies</em>' containment reference.
	 * @see #setBackupCopies(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_BackupCopies()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='backup-copies' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getBackupCopies();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getBackupCopies <em>Backup Copies</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Backup Copies</em>' containment reference.
	 * @see #getBackupCopies()
	 * @generated
	 */
	void setBackupCopies(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Businessworks</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Businessworks</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Businessworks</em>' containment reference.
	 * @see #setBusinessworks(BusinessworksConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Businessworks()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='businessworks' namespace='##targetNamespace'"
	 * @generated
	 */
	BusinessworksConfig getBusinessworks();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getBusinessworks <em>Businessworks</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Businessworks</em>' containment reference.
	 * @see #getBusinessworks()
	 * @generated
	 */
	void setBusinessworks(BusinessworksConfig value);

	/**
	 * Returns the value of the '<em><b>Cache</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cache</em>' containment reference.
	 * @see #setCache(CacheAgentClassConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Cache()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='cache' namespace='##targetNamespace'"
	 * @generated
	 */
	CacheAgentClassConfig getCache();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getCache <em>Cache</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache</em>' containment reference.
	 * @see #getCache()
	 * @generated
	 */
	void setCache(CacheAgentClassConfig value);

	/**
	 * Returns the value of the '<em><b>Cache Agent Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache Agent Class</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cache Agent Class</em>' containment reference.
	 * @see #setCacheAgentClass(CacheAgentClassConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_CacheAgentClass()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='cache-agent-class' namespace='##targetNamespace'"
	 * @generated
	 */
	CacheAgentClassConfig getCacheAgentClass();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getCacheAgentClass <em>Cache Agent Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache Agent Class</em>' containment reference.
	 * @see #getCacheAgentClass()
	 * @generated
	 */
	void setCacheAgentClass(CacheAgentClassConfig value);

	/**
	 * Returns the value of the '<em><b>Cache Agent Quorum</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache Agent Quorum</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cache Agent Quorum</em>' containment reference.
	 * @see #setCacheAgentQuorum(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_CacheAgentQuorum()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='cache-agent-quorum' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getCacheAgentQuorum();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getCacheAgentQuorum <em>Cache Agent Quorum</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache Agent Quorum</em>' containment reference.
	 * @see #getCacheAgentQuorum()
	 * @generated
	 */
	void setCacheAgentQuorum(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Cache Aside</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache Aside</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cache Aside</em>' containment reference.
	 * @see #setCacheAside(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_CacheAside()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='cache-aside' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getCacheAside();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getCacheAside <em>Cache Aside</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache Aside</em>' containment reference.
	 * @see #getCacheAside()
	 * @generated
	 */
	void setCacheAside(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Cache Limited</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache Limited</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cache Limited</em>' containment reference.
	 * @see #setCacheLimited(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_CacheLimited()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='cache-limited' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getCacheLimited();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getCacheLimited <em>Cache Limited</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache Limited</em>' containment reference.
	 * @see #getCacheLimited()
	 * @generated
	 */
	void setCacheLimited(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Cache Loader Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache Loader Class</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cache Loader Class</em>' containment reference.
	 * @see #setCacheLoaderClass(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_CacheLoaderClass()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='cache-loader-class' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getCacheLoaderClass();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getCacheLoaderClass <em>Cache Loader Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache Loader Class</em>' containment reference.
	 * @see #getCacheLoaderClass()
	 * @generated
	 */
	void setCacheLoaderClass(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Cache Manager</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache Manager</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cache Manager</em>' containment reference.
	 * @see #setCacheManager(CacheManagerConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_CacheManager()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='cache-manager' namespace='##targetNamespace'"
	 * @generated
	 */
	CacheManagerConfig getCacheManager();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getCacheManager <em>Cache Manager</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache Manager</em>' containment reference.
	 * @see #getCacheManager()
	 * @generated
	 */
	void setCacheManager(CacheManagerConfig value);

	/**
	 * Returns the value of the '<em><b>Cache Mode</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.be.util.config.cdd.DomainObjectModeConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cache Mode</em>' attribute.
	 * @see com.tibco.be.util.config.cdd.DomainObjectModeConfig
	 * @see #setCacheMode(DomainObjectModeConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_CacheMode()
	 * @model unique="false" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='cache-mode' namespace='##targetNamespace'"
	 * @generated
	 */
	DomainObjectModeConfig getCacheMode();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getCacheMode <em>Cache Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache Mode</em>' attribute.
	 * @see com.tibco.be.util.config.cdd.DomainObjectModeConfig
	 * @see #getCacheMode()
	 * @generated
	 */
	void setCacheMode(DomainObjectModeConfig value);

	/**
	 * Returns the value of the '<em><b>Cache Storage Enabled</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache Storage Enabled</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cache Storage Enabled</em>' containment reference.
	 * @see #setCacheStorageEnabled(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_CacheStorageEnabled()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='cache-storage-enabled' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getCacheStorageEnabled();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getCacheStorageEnabled <em>Cache Storage Enabled</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache Storage Enabled</em>' containment reference.
	 * @see #getCacheStorageEnabled()
	 * @generated
	 */
	void setCacheStorageEnabled(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Certificate Key File</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Certificate Key File</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Certificate Key File</em>' containment reference.
	 * @see #setCertificateKeyFile(SecurityOverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_CertificateKeyFile()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="name='certificate-key-file' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	SecurityOverrideConfig getCertificateKeyFile();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getCertificateKeyFile <em>Certificate Key File</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Certificate Key File</em>' containment reference.
	 * @see #getCertificateKeyFile()
	 * @generated
	 */
	void setCertificateKeyFile(SecurityOverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Channel</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Channel</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Channel</em>' containment reference.
	 * @see #setChannel(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Channel()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='channel' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getChannel();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getChannel <em>Channel</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Channel</em>' containment reference.
	 * @see #getChannel()
	 * @generated
	 */
	void setChannel(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Check For Duplicates</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Check For Duplicates</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Check For Duplicates</em>' containment reference.
	 * @see #setCheckForDuplicates(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_CheckForDuplicates()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='check-for-duplicates' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getCheckForDuplicates();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getCheckForDuplicates <em>Check For Duplicates</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Check For Duplicates</em>' containment reference.
	 * @see #getCheckForDuplicates()
	 * @generated
	 */
	void setCheckForDuplicates(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Check For Version</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Check For Version</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Check For Version</em>' containment reference.
	 * @see #setCheckForVersion(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_CheckForVersion()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='check-for-version' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getCheckForVersion();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getCheckForVersion <em>Check For Version</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Check For Version</em>' containment reference.
	 * @see #getCheckForVersion()
	 * @generated
	 */
	void setCheckForVersion(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Check Interval</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Check Interval</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Check Interval</em>' containment reference.
	 * @see #setCheckInterval(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_CheckInterval()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='check-interval' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getCheckInterval();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getCheckInterval <em>Check Interval</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Check Interval</em>' containment reference.
	 * @see #getCheckInterval()
	 * @generated
	 */
	void setCheckInterval(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Checkpoint Interval</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Checkpoint Interval</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Checkpoint Interval</em>' containment reference.
	 * @see #setCheckpointInterval(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_CheckpointInterval()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='checkpoint-interval' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getCheckpointInterval();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getCheckpointInterval <em>Checkpoint Interval</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Checkpoint Interval</em>' containment reference.
	 * @see #getCheckpointInterval()
	 * @generated
	 */
	void setCheckpointInterval(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Checkpoint Ops Limit</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Checkpoint Ops Limit</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Checkpoint Ops Limit</em>' containment reference.
	 * @see #setCheckpointOpsLimit(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_CheckpointOpsLimit()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='checkpoint-ops-limit' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getCheckpointOpsLimit();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getCheckpointOpsLimit <em>Checkpoint Ops Limit</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Checkpoint Ops Limit</em>' containment reference.
	 * @see #getCheckpointOpsLimit()
	 * @generated
	 */
	void setCheckpointOpsLimit(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Child Cluster Member</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Child Cluster Member</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Child Cluster Member</em>' containment reference.
	 * @see #setChildClusterMember(ChildClusterMemberConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_ChildClusterMember()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='child-cluster-member' namespace='##targetNamespace'"
	 * @generated
	 */
	ChildClusterMemberConfig getChildClusterMember();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getChildClusterMember <em>Child Cluster Member</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Child Cluster Member</em>' containment reference.
	 * @see #getChildClusterMember()
	 * @generated
	 */
	void setChildClusterMember(ChildClusterMemberConfig value);

	/**
	 * Returns the value of the '<em><b>Cipher</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cipher</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cipher</em>' containment reference.
	 * @see #setCipher(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Cipher()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='cipher' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getCipher();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getCipher <em>Cipher</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cipher</em>' containment reference.
	 * @see #getCipher()
	 * @generated
	 */
	void setCipher(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Ciphers</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ciphers</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ciphers</em>' containment reference.
	 * @see #setCiphers(CiphersConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Ciphers()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ciphers' namespace='##targetNamespace'"
	 * @generated
	 */
	CiphersConfig getCiphers();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getCiphers <em>Ciphers</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ciphers</em>' containment reference.
	 * @see #getCiphers()
	 * @generated
	 */
	void setCiphers(CiphersConfig value);

	/**
	 * Returns the value of the '<em><b>Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class</em>' containment reference.
	 * @see #setClass(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Class()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='class' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getClass_();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getClass_ <em>Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Class</em>' containment reference.
	 * @see #getClass_()
	 * @generated
	 */
	void setClass(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Cluster</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cluster</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cluster</em>' containment reference.
	 * @see #setCluster(ClusterConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Cluster()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='cluster' namespace='##targetNamespace'"
	 * @generated
	 */
	ClusterConfig getCluster();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getCluster <em>Cluster</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cluster</em>' containment reference.
	 * @see #getCluster()
	 * @generated
	 */
	void setCluster(ClusterConfig value);

	/**
	 * Returns the value of the '<em><b>Cluster Member</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cluster Member</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cluster Member</em>' containment reference.
	 * @see #setClusterMember(ClusterMemberConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_ClusterMember()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='cluster-member' namespace='##targetNamespace'"
	 * @generated
	 */
	ClusterMemberConfig getClusterMember();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getClusterMember <em>Cluster Member</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cluster Member</em>' containment reference.
	 * @see #getClusterMember()
	 * @generated
	 */
	void setClusterMember(ClusterMemberConfig value);

	/**
	 * Returns the value of the '<em><b>Comment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Comment</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comment</em>' containment reference.
	 * @see #setComment(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Comment()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='comment' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getComment();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getComment <em>Comment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comment</em>' containment reference.
	 * @see #getComment()
	 * @generated
	 */
	void setComment(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Concurrent Rtc</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Concurrent Rtc</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Concurrent Rtc</em>' containment reference.
	 * @see #setConcurrentRtc(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_ConcurrentRtc()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='concurrent-rtc' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getConcurrentRtc();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getConcurrentRtc <em>Concurrent Rtc</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Concurrent Rtc</em>' containment reference.
	 * @see #getConcurrentRtc()
	 * @generated
	 */
	void setConcurrentRtc(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition</em>' containment reference.
	 * @see #setCondition(ConditionConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Condition()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='condition' namespace='##targetNamespace'"
	 * @generated
	 */
	ConditionConfig getCondition();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getCondition <em>Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition</em>' containment reference.
	 * @see #getCondition()
	 * @generated
	 */
	void setCondition(ConditionConfig value);

	/**
	 * Returns the value of the '<em><b>Connection Linger</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Connection Linger</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Connection Linger</em>' containment reference.
	 * @see #setConnectionLinger(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_ConnectionLinger()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='connection-linger' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getConnectionLinger();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getConnectionLinger <em>Connection Linger</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Connection Linger</em>' containment reference.
	 * @see #getConnectionLinger()
	 * @generated
	 */
	void setConnectionLinger(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Connection Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Connection Timeout</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Connection Timeout</em>' containment reference.
	 * @see #setConnectionTimeout(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_ConnectionTimeout()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='connection-timeout' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getConnectionTimeout();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getConnectionTimeout <em>Connection Timeout</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Connection Timeout</em>' containment reference.
	 * @see #getConnectionTimeout()
	 * @generated
	 */
	void setConnectionTimeout(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Constant</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constant</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constant</em>' containment reference.
	 * @see #setConstant(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Constant()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='constant' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getConstant();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getConstant <em>Constant</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Constant</em>' containment reference.
	 * @see #getConstant()
	 * @generated
	 */
	void setConstant(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Controller</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Controller</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Controller</em>' containment reference.
	 * @see #setController(SecurityController)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Controller()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="name='controller' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	SecurityController getController();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getController <em>Controller</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Controller</em>' containment reference.
	 * @see #getController()
	 * @generated
	 */
	void setController(SecurityController value);

	/**
	 * Returns the value of the '<em><b>Daemon</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Daemon</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Daemon</em>' containment reference.
	 * @see #setDaemon(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Daemon()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='daemon' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getDaemon();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getDaemon <em>Daemon</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Daemon</em>' containment reference.
	 * @see #getDaemon()
	 * @generated
	 */
	void setDaemon(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Dashboard Agent Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dashboard Agent Class</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dashboard Agent Class</em>' containment reference.
	 * @see #setDashboardAgentClass(DashboardAgentClassConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_DashboardAgentClass()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='dashboard-agent-class' namespace='##targetNamespace'"
	 * @generated
	 */
	DashboardAgentClassConfig getDashboardAgentClass();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getDashboardAgentClass <em>Dashboard Agent Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dashboard Agent Class</em>' containment reference.
	 * @see #getDashboardAgentClass()
	 * @generated
	 */
	void setDashboardAgentClass(DashboardAgentClassConfig value);

	/**
	 * Returns the value of the '<em><b>Data Store Path</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Store Path</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Store Path</em>' containment reference.
	 * @see #setDataStorePath(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_DataStorePath()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='data-store-path' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getDataStorePath();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getDataStorePath <em>Data Store Path</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Store Path</em>' containment reference.
	 * @see #getDataStorePath()
	 * @generated
	 */
	void setDataStorePath(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Date</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Date</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Date</em>' containment reference.
	 * @see #setDate(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Date()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='date' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getDate();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getDate <em>Date</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Date</em>' containment reference.
	 * @see #getDate()
	 * @generated
	 */
	void setDate(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Db Concepts</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Db Concepts</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Db Concepts</em>' containment reference.
	 * @see #setDbConcepts(DbConceptsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_DbConcepts()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='db-concepts' namespace='##targetNamespace'"
	 * @generated
	 */
	DbConceptsConfig getDbConcepts();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getDbConcepts <em>Db Concepts</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Db Concepts</em>' containment reference.
	 * @see #getDbConcepts()
	 * @generated
	 */
	void setDbConcepts(DbConceptsConfig value);

	/**
	 * Returns the value of the '<em><b>Db Dir</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Db Dir</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Db Dir</em>' containment reference.
	 * @see #setDbDir(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_DbDir()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='db-dir' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getDbDir();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getDbDir <em>Db Dir</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Db Dir</em>' containment reference.
	 * @see #getDbDir()
	 * @generated
	 */
	void setDbDir(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Db Uris</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Db Uris</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Db Uris</em>' containment reference.
	 * @see #setDbUris(UrisConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_DbUris()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='db-uris' namespace='##targetNamespace'"
	 * @generated
	 */
	UrisConfig getDbUris();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getDbUris <em>Db Uris</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Db Uris</em>' containment reference.
	 * @see #getDbUris()
	 * @generated
	 */
	void setDbUris(UrisConfig value);

	/**
	 * Returns the value of the '<em><b>Default Mode</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.be.util.config.cdd.DomainObjectModeConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Mode</em>' attribute.
	 * @see com.tibco.be.util.config.cdd.DomainObjectModeConfig
	 * @see #setDefaultMode(DomainObjectModeConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_DefaultMode()
	 * @model unique="false" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='default-mode' namespace='##targetNamespace'"
	 * @generated
	 */
	DomainObjectModeConfig getDefaultMode();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getDefaultMode <em>Default Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Mode</em>' attribute.
	 * @see com.tibco.be.util.config.cdd.DomainObjectModeConfig
	 * @see #getDefaultMode()
	 * @generated
	 */
	void setDefaultMode(DomainObjectModeConfig value);

	/**
	 * Returns the value of the '<em><b>Delete Retracted</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delete Retracted</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delete Retracted</em>' containment reference.
	 * @see #setDeleteRetracted(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_DeleteRetracted()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='delete-retracted' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getDeleteRetracted();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getDeleteRetracted <em>Delete Retracted</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delete Retracted</em>' containment reference.
	 * @see #getDeleteRetracted()
	 * @generated
	 */
	void setDeleteRetracted(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Destination</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Destination</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Destination</em>' containment reference.
	 * @see #setDestination(DestinationConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Destination()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='destination' namespace='##targetNamespace'"
	 * @generated
	 */
	DestinationConfig getDestination();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getDestination <em>Destination</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Destination</em>' containment reference.
	 * @see #getDestination()
	 * @generated
	 */
	void setDestination(DestinationConfig value);

	/**
	 * Returns the value of the '<em><b>Destination Groups</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Destination Groups</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Destination Groups</em>' containment reference.
	 * @see #setDestinationGroups(DestinationGroupsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_DestinationGroups()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='destination-groups' namespace='##targetNamespace'"
	 * @generated
	 */
	DestinationGroupsConfig getDestinationGroups();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getDestinationGroups <em>Destination Groups</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Destination Groups</em>' containment reference.
	 * @see #getDestinationGroups()
	 * @generated
	 */
	void setDestinationGroups(DestinationGroupsConfig value);

	/**
	 * Returns the value of the '<em><b>Destinations</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Destinations</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Destinations</em>' containment reference.
	 * @see #setDestinations(DestinationsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Destinations()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='destinations' namespace='##targetNamespace'"
	 * @generated
	 */
	DestinationsConfig getDestinations();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getDestinations <em>Destinations</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Destinations</em>' containment reference.
	 * @see #getDestinations()
	 * @generated
	 */
	void setDestinations(DestinationsConfig value);

	/**
	 * Returns the value of the '<em><b>Dir</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dir</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dir</em>' containment reference.
	 * @see #setDir(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Dir()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='dir' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getDir();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getDir <em>Dir</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dir</em>' containment reference.
	 * @see #getDir()
	 * @generated
	 */
	void setDir(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Discovery Url</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Discovery Url</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Discovery Url</em>' containment reference.
	 * @see #setDiscoveryUrl(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_DiscoveryUrl()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='discovery-url' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getDiscoveryUrl();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getDiscoveryUrl <em>Discovery Url</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Discovery Url</em>' containment reference.
	 * @see #getDiscoveryUrl()
	 * @generated
	 */
	void setDiscoveryUrl(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Document Page</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Document Page</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Document Page</em>' containment reference.
	 * @see #setDocumentPage(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_DocumentPage()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='document-page' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getDocumentPage();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getDocumentPage <em>Document Page</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Document Page</em>' containment reference.
	 * @see #getDocumentPage()
	 * @generated
	 */
	void setDocumentPage(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Document Root</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Document Root</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Document Root</em>' containment reference.
	 * @see #setDocumentRoot(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_DocumentRoot()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='document-root' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getDocumentRoot();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getDocumentRoot <em>Document Root</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Document Root</em>' containment reference.
	 * @see #getDocumentRoot()
	 * @generated
	 */
	void setDocumentRoot(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Domain Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain Name</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain Name</em>' containment reference.
	 * @see #setDomainName(SecurityOverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_DomainName()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="name='domain-name' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	SecurityOverrideConfig getDomainName();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getDomainName <em>Domain Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain Name</em>' containment reference.
	 * @see #getDomainName()
	 * @generated
	 */
	void setDomainName(SecurityOverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Domain Object</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain Object</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain Object</em>' containment reference.
	 * @see #setDomainObject(DomainObjectConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_DomainObject()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='domain-object' namespace='##targetNamespace'"
	 * @generated
	 */
	DomainObjectConfig getDomainObject();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getDomainObject <em>Domain Object</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain Object</em>' containment reference.
	 * @see #getDomainObject()
	 * @generated
	 */
	void setDomainObject(DomainObjectConfig value);

	/**
	 * Returns the value of the '<em><b>Domain Objects</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain Objects</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain Objects</em>' containment reference.
	 * @see #setDomainObjects(DomainObjectsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_DomainObjects()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='domain-objects' namespace='##targetNamespace'"
	 * @generated
	 */
	DomainObjectsConfig getDomainObjects();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getDomainObjects <em>Domain Objects</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain Objects</em>' containment reference.
	 * @see #getDomainObjects()
	 * @generated
	 */
	void setDomainObjects(DomainObjectsConfig value);

	/**
	 * Returns the value of the '<em><b>Enabled</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enabled</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enabled</em>' containment reference.
	 * @see #setEnabled(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Enabled()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='enabled' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getEnabled();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getEnabled <em>Enabled</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enabled</em>' containment reference.
	 * @see #getEnabled()
	 * @generated
	 */
	void setEnabled(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Enable Tracking</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enable Tracking</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enable Tracking</em>' containment reference.
	 * @see #setEnableTracking(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_EnableTracking()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='enable-tracking' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getEnableTracking();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getEnableTracking <em>Enable Tracking</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enable Tracking</em>' containment reference.
	 * @see #getEnableTracking()
	 * @generated
	 */
	void setEnableTracking(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Encoding</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Encoding</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Encoding</em>' containment reference.
	 * @see #setEncoding(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Encoding()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='encoding' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getEncoding();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getEncoding <em>Encoding</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Encoding</em>' containment reference.
	 * @see #getEncoding()
	 * @generated
	 */
	void setEncoding(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Encryption</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Encryption</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Encryption</em>' containment reference.
	 * @see #setEncryption(FieldEncryptionConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Encryption()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='encryption' namespace='##targetNamespace'"
	 * @generated
	 */
	FieldEncryptionConfig getEncryption();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getEncryption <em>Encryption</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Encryption</em>' containment reference.
	 * @see #getEncryption()
	 * @generated
	 */
	void setEncryption(FieldEncryptionConfig value);

	/**
	 * Returns the value of the '<em><b>Enforce Pools</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enforce Pools</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enforce Pools</em>' containment reference.
	 * @see #setEnforcePools(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_EnforcePools()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='enforce-pools' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getEnforcePools();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getEnforcePools <em>Enforce Pools</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enforce Pools</em>' containment reference.
	 * @see #getEnforcePools()
	 * @generated
	 */
	void setEnforcePools(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Entity Cache Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity Cache Size</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity Cache Size</em>' containment reference.
	 * @see #setEntityCacheSize(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_EntityCacheSize()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='entity-cache-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getEntityCacheSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getEntityCacheSize <em>Entity Cache Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entity Cache Size</em>' containment reference.
	 * @see #getEntityCacheSize()
	 * @generated
	 */
	void setEntityCacheSize(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Eviction</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Eviction</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Eviction</em>' containment reference.
	 * @see #setEviction(EvictionConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Eviction()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='eviction' namespace='##targetNamespace'"
	 * @generated
	 */
	EvictionConfig getEviction();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getEviction <em>Eviction</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Eviction</em>' containment reference.
	 * @see #getEviction()
	 * @generated
	 */
	void setEviction(EvictionConfig value);

	/**
	 * Returns the value of the '<em><b>Evict On Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Evict On Update</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Evict On Update</em>' containment reference.
	 * @see #setEvictOnUpdate(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_EvictOnUpdate()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='evict-on-update' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getEvictOnUpdate();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getEvictOnUpdate <em>Evict On Update</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Evict On Update</em>' containment reference.
	 * @see #getEvictOnUpdate()
	 * @generated
	 */
	void setEvictOnUpdate(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Explicit Tuple</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Explicit Tuple</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Explicit Tuple</em>' containment reference.
	 * @see #setExplicitTuple(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_ExplicitTuple()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='explicit-tuple' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getExplicitTuple();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getExplicitTuple <em>Explicit Tuple</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Explicit Tuple</em>' containment reference.
	 * @see #getExplicitTuple()
	 * @generated
	 */
	void setExplicitTuple(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Files</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Files</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Files</em>' containment reference.
	 * @see #setFiles(FilesConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Files()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='files' namespace='##targetNamespace'"
	 * @generated
	 */
	FilesConfig getFiles();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getFiles <em>Files</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Files</em>' containment reference.
	 * @see #getFiles()
	 * @generated
	 */
	void setFiles(FilesConfig value);

	/**
	 * Returns the value of the '<em><b>Function Groups</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Function Groups</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function Groups</em>' containment reference.
	 * @see #setFunctionGroups(FunctionGroupsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_FunctionGroups()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='function-groups' namespace='##targetNamespace'"
	 * @generated
	 */
	FunctionGroupsConfig getFunctionGroups();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getFunctionGroups <em>Function Groups</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function Groups</em>' containment reference.
	 * @see #getFunctionGroups()
	 * @generated
	 */
	void setFunctionGroups(FunctionGroupsConfig value);

	/**
	 * Returns the value of the '<em><b>Functions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Functions</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Functions</em>' containment reference.
	 * @see #setFunctions(FunctionsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Functions()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='functions' namespace='##targetNamespace'"
	 * @generated
	 */
	FunctionsConfig getFunctions();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getFunctions <em>Functions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Functions</em>' containment reference.
	 * @see #getFunctions()
	 * @generated
	 */
	void setFunctions(FunctionsConfig value);

	/**
	 * Returns the value of the '<em><b>Get Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Get Property</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Get Property</em>' containment reference.
	 * @see #setGetProperty(GetPropertyConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_GetProperty()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='get-property' namespace='##targetNamespace'"
	 * @generated
	 */
	GetPropertyConfig getGetProperty();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getGetProperty <em>Get Property</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Get Property</em>' containment reference.
	 * @see #getGetProperty()
	 * @generated
	 */
	void setGetProperty(GetPropertyConfig value);

	/**
	 * Returns the value of the '<em><b>Hot Deploy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hot Deploy</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hot Deploy</em>' containment reference.
	 * @see #setHotDeploy(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_HotDeploy()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='hot-deploy' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getHotDeploy();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getHotDeploy <em>Hot Deploy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hot Deploy</em>' containment reference.
	 * @see #getHotDeploy()
	 * @generated
	 */
	void setHotDeploy(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Http</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Http</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Http</em>' containment reference.
	 * @see #setHttp(HttpConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Http()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='http' namespace='##targetNamespace'"
	 * @generated
	 */
	HttpConfig getHttp();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getHttp <em>Http</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Http</em>' containment reference.
	 * @see #getHttp()
	 * @generated
	 */
	void setHttp(HttpConfig value);

	/**
	 * Returns the value of the '<em><b>Identity Password</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Identity Password</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Identity Password</em>' containment reference.
	 * @see #setIdentityPassword(SecurityOverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_IdentityPassword()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="name='identity-password' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	SecurityOverrideConfig getIdentityPassword();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getIdentityPassword <em>Identity Password</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Identity Password</em>' containment reference.
	 * @see #getIdentityPassword()
	 * @generated
	 */
	void setIdentityPassword(SecurityOverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Inactivity Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inactivity Timeout</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inactivity Timeout</em>' containment reference.
	 * @see #setInactivityTimeout(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_InactivityTimeout()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='inactivity-timeout' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getInactivityTimeout();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getInactivityTimeout <em>Inactivity Timeout</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inactivity Timeout</em>' containment reference.
	 * @see #getInactivityTimeout()
	 * @generated
	 */
	void setInactivityTimeout(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Index</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Index</em>' containment reference.
	 * @see #setIndex(IndexConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Index()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='index' namespace='##targetNamespace'"
	 * @generated
	 */
	IndexConfig getIndex();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getIndex <em>Index</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Index</em>' containment reference.
	 * @see #getIndex()
	 * @generated
	 */
	void setIndex(IndexConfig value);

	/**
	 * Returns the value of the '<em><b>Indexes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Indexes</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Indexes</em>' containment reference.
	 * @see #setIndexes(IndexesConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Indexes()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='indexes' namespace='##targetNamespace'"
	 * @generated
	 */
	IndexesConfig getIndexes();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getIndexes <em>Indexes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Indexes</em>' containment reference.
	 * @see #getIndexes()
	 * @generated
	 */
	void setIndexes(IndexesConfig value);

	/**
	 * Returns the value of the '<em><b>Inference Agent Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inference Agent Class</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inference Agent Class</em>' containment reference.
	 * @see #setInferenceAgentClass(InferenceAgentClassConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_InferenceAgentClass()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='inference-agent-class' namespace='##targetNamespace'"
	 * @generated
	 */
	InferenceAgentClassConfig getInferenceAgentClass();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getInferenceAgentClass <em>Inference Agent Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inference Agent Class</em>' containment reference.
	 * @see #getInferenceAgentClass()
	 * @generated
	 */
	void setInferenceAgentClass(InferenceAgentClassConfig value);

	/**
	 * Returns the value of the '<em><b>Inference Engine</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inference Engine</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inference Engine</em>' containment reference.
	 * @see #setInferenceEngine(InferenceEngineConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_InferenceEngine()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='inference-engine' namespace='##targetNamespace'"
	 * @generated
	 */
	InferenceEngineConfig getInferenceEngine();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getInferenceEngine <em>Inference Engine</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inference Engine</em>' containment reference.
	 * @see #getInferenceEngine()
	 * @generated
	 */
	void setInferenceEngine(InferenceEngineConfig value);

	/**
	 * Returns the value of the '<em><b>Initial Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Initial Size</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initial Size</em>' containment reference.
	 * @see #setInitialSize(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_InitialSize()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='initial-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getInitialSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getInitialSize <em>Initial Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initial Size</em>' containment reference.
	 * @see #getInitialSize()
	 * @generated
	 */
	void setInitialSize(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Job Manager</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Job Manager</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Job Manager</em>' containment reference.
	 * @see #setJobManager(JobManagerConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_JobManager()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='job-manager' namespace='##targetNamespace'"
	 * @generated
	 */
	JobManagerConfig getJobManager();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getJobManager <em>Job Manager</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Job Manager</em>' containment reference.
	 * @see #getJobManager()
	 * @generated
	 */
	void setJobManager(JobManagerConfig value);

	/**
	 * Returns the value of the '<em><b>Job Pool Queue Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Job Pool Queue Size</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Job Pool Queue Size</em>' containment reference.
	 * @see #setJobPoolQueueSize(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_JobPoolQueueSize()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='job-pool-queue-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getJobPoolQueueSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getJobPoolQueueSize <em>Job Pool Queue Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Job Pool Queue Size</em>' containment reference.
	 * @see #getJobPoolQueueSize()
	 * @generated
	 */
	void setJobPoolQueueSize(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Job Pool Thread Count</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Job Pool Thread Count</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Job Pool Thread Count</em>' containment reference.
	 * @see #setJobPoolThreadCount(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_JobPoolThreadCount()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='job-pool-thread-count' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getJobPoolThreadCount();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getJobPoolThreadCount <em>Job Pool Thread Count</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Job Pool Thread Count</em>' containment reference.
	 * @see #getJobPoolThreadCount()
	 * @generated
	 */
	void setJobPoolThreadCount(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Key</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Key</em>' containment reference.
	 * @see #setKey(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Key()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='key' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getKey();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getKey <em>Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key</em>' containment reference.
	 * @see #getKey()
	 * @generated
	 */
	void setKey(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Key Manager Algorithm</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Key Manager Algorithm</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Key Manager Algorithm</em>' containment reference.
	 * @see #setKeyManagerAlgorithm(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_KeyManagerAlgorithm()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='key-manager-algorithm' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getKeyManagerAlgorithm();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getKeyManagerAlgorithm <em>Key Manager Algorithm</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key Manager Algorithm</em>' containment reference.
	 * @see #getKeyManagerAlgorithm()
	 * @generated
	 */
	void setKeyManagerAlgorithm(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Line Layout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Line Layout</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Line Layout</em>' containment reference.
	 * @see #setLineLayout(LineLayoutConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_LineLayout()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='line-layout' namespace='##targetNamespace'"
	 * @generated
	 */
	LineLayoutConfig getLineLayout();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getLineLayout <em>Line Layout</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Line Layout</em>' containment reference.
	 * @see #getLineLayout()
	 * @generated
	 */
	void setLineLayout(LineLayoutConfig value);

	/**
	 * Returns the value of the '<em><b>Listen Url</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Listen Url</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Listen Url</em>' containment reference.
	 * @see #setListenUrl(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_ListenUrl()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='listen-url' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getListenUrl();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getListenUrl <em>Listen Url</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Listen Url</em>' containment reference.
	 * @see #getListenUrl()
	 * @generated
	 */
	void setListenUrl(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Load</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Load</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Load</em>' containment reference.
	 * @see #setLoad(LoadConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Load()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='load' namespace='##targetNamespace'"
	 * @generated
	 */
	LoadConfig getLoad();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getLoad <em>Load</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Load</em>' containment reference.
	 * @see #getLoad()
	 * @generated
	 */
	void setLoad(LoadConfig value);

	/**
	 * Returns the value of the '<em><b>Load Balancer Configs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Load Balancer Configs</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Load Balancer Configs</em>' containment reference.
	 * @see #setLoadBalancerConfigs(LoadBalancerConfigsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_LoadBalancerConfigs()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='load-balancer-configs' namespace='##targetNamespace'"
	 * @generated
	 */
	LoadBalancerConfigsConfig getLoadBalancerConfigs();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getLoadBalancerConfigs <em>Load Balancer Configs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Load Balancer Configs</em>' containment reference.
	 * @see #getLoadBalancerConfigs()
	 * @generated
	 */
	void setLoadBalancerConfigs(LoadBalancerConfigsConfig value);

	/**
	 * Returns the value of the '<em><b>Local Cache</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Local Cache</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Local Cache</em>' containment reference.
	 * @see #setLocalCache(LocalCacheConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_LocalCache()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='local-cache' namespace='##targetNamespace'"
	 * @generated
	 */
	LocalCacheConfig getLocalCache();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getLocalCache <em>Local Cache</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Local Cache</em>' containment reference.
	 * @see #getLocalCache()
	 * @generated
	 */
	void setLocalCache(LocalCacheConfig value);

	/**
	 * Returns the value of the '<em><b>Lock Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lock Timeout</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lock Timeout</em>' containment reference.
	 * @see #setLockTimeout(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_LockTimeout()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='lock-timeout' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getLockTimeout();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getLockTimeout <em>Lock Timeout</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lock Timeout</em>' containment reference.
	 * @see #getLockTimeout()
	 * @generated
	 */
	void setLockTimeout(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Log Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Log Config</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Log Config</em>' containment reference.
	 * @see #setLogConfig(LogConfigConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_LogConfig()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='log-config' namespace='##targetNamespace'"
	 * @generated
	 */
	LogConfigConfig getLogConfig();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getLogConfig <em>Log Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Log Config</em>' containment reference.
	 * @see #getLogConfig()
	 * @generated
	 */
	void setLogConfig(LogConfigConfig value);

	/**
	 * Returns the value of the '<em><b>Log Configs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Log Configs</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Log Configs</em>' containment reference.
	 * @see #setLogConfigs(LogConfigsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_LogConfigs()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='log-configs' namespace='##targetNamespace'"
	 * @generated
	 */
	LogConfigsConfig getLogConfigs();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getLogConfigs <em>Log Configs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Log Configs</em>' containment reference.
	 * @see #getLogConfigs()
	 * @generated
	 */
	void setLogConfigs(LogConfigsConfig value);

	/**
	 * Returns the value of the '<em><b>Max Active</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Active</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Active</em>' containment reference.
	 * @see #setMaxActive(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_MaxActive()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='max-active' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getMaxActive();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getMaxActive <em>Max Active</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Active</em>' containment reference.
	 * @see #getMaxActive()
	 * @generated
	 */
	void setMaxActive(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Max Number</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Number</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Number</em>' containment reference.
	 * @see #setMaxNumber(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_MaxNumber()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='max-number' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getMaxNumber();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getMaxNumber <em>Max Number</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Number</em>' containment reference.
	 * @see #getMaxNumber()
	 * @generated
	 */
	void setMaxNumber(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Max Processors</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Processors</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Processors</em>' containment reference.
	 * @see #setMaxProcessors(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_MaxProcessors()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='max-processors' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getMaxProcessors();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getMaxProcessors <em>Max Processors</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Processors</em>' containment reference.
	 * @see #getMaxProcessors()
	 * @generated
	 */
	void setMaxProcessors(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Max Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Size</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Size</em>' containment reference.
	 * @see #setMaxSize(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_MaxSize()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='max-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getMaxSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getMaxSize <em>Max Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Size</em>' containment reference.
	 * @see #getMaxSize()
	 * @generated
	 */
	void setMaxSize(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Max Time</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Time</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Time</em>' containment reference.
	 * @see #setMaxTime(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_MaxTime()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='max-time' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getMaxTime();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getMaxTime <em>Max Time</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Time</em>' containment reference.
	 * @see #getMaxTime()
	 * @generated
	 */
	void setMaxTime(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Memory Manager</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Memory Manager</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Memory Manager</em>' containment reference.
	 * @see #setMemoryManager(MemoryManagerConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_MemoryManager()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='memory-manager' namespace='##targetNamespace'"
	 * @generated
	 */
	MemoryManagerConfig getMemoryManager();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getMemoryManager <em>Memory Manager</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Memory Manager</em>' containment reference.
	 * @see #getMemoryManager()
	 * @generated
	 */
	void setMemoryManager(MemoryManagerConfig value);

	/**
	 * Returns the value of the '<em><b>Message Encoding</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Message Encoding</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Message Encoding</em>' attribute.
	 * @see #setMessageEncoding(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_MessageEncoding()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='message-encoding' namespace='##targetNamespace'"
	 * @generated
	 */
	String getMessageEncoding();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getMessageEncoding <em>Message Encoding</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Message Encoding</em>' attribute.
	 * @see #getMessageEncoding()
	 * @generated
	 */
	void setMessageEncoding(String value);

	/**
	 * Returns the value of the '<em><b>Min Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min Size</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min Size</em>' containment reference.
	 * @see #setMinSize(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_MinSize()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='min-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getMinSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getMinSize <em>Min Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Size</em>' containment reference.
	 * @see #getMinSize()
	 * @generated
	 */
	void setMinSize(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Mm Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mm Action</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mm Action</em>' containment reference.
	 * @see #setMmAction(MmActionConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_MmAction()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mm-action' namespace='##targetNamespace'"
	 * @generated
	 */
	MmActionConfig getMmAction();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getMmAction <em>Mm Action</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mm Action</em>' containment reference.
	 * @see #getMmAction()
	 * @generated
	 */
	void setMmAction(MmActionConfig value);

	/**
	 * Returns the value of the '<em><b>Mm Action Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mm Action Config</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mm Action Config</em>' containment reference.
	 * @see #setMmActionConfig(MmActionConfigConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_MmActionConfig()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mm-action-config' namespace='##targetNamespace'"
	 * @generated
	 */
	MmActionConfigConfig getMmActionConfig();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getMmActionConfig <em>Mm Action Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mm Action Config</em>' containment reference.
	 * @see #getMmActionConfig()
	 * @generated
	 */
	void setMmActionConfig(MmActionConfigConfig value);

	/**
	 * Returns the value of the '<em><b>Mm Action Config Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mm Action Config Set</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mm Action Config Set</em>' containment reference.
	 * @see #setMmActionConfigSet(MmActionConfigSetConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_MmActionConfigSet()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mm-action-config-set' namespace='##targetNamespace'"
	 * @generated
	 */
	MmActionConfigSetConfig getMmActionConfigSet();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getMmActionConfigSet <em>Mm Action Config Set</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mm Action Config Set</em>' containment reference.
	 * @see #getMmActionConfigSet()
	 * @generated
	 */
	void setMmActionConfigSet(MmActionConfigSetConfig value);

	/**
	 * Returns the value of the '<em><b>Mm Agent Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mm Agent Class</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mm Agent Class</em>' containment reference.
	 * @see #setMmAgentClass(MmAgentClassConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_MmAgentClass()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mm-agent-class' namespace='##targetNamespace'"
	 * @generated
	 */
	MmAgentClassConfig getMmAgentClass();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getMmAgentClass <em>Mm Agent Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mm Agent Class</em>' containment reference.
	 * @see #getMmAgentClass()
	 * @generated
	 */
	void setMmAgentClass(MmAgentClassConfig value);

	/**
	 * Returns the value of the '<em><b>Mm Alert</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mm Alert</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mm Alert</em>' containment reference.
	 * @see #setMmAlert(MmAlertConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_MmAlert()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mm-alert' namespace='##targetNamespace'"
	 * @generated
	 */
	MmAlertConfig getMmAlert();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getMmAlert <em>Mm Alert</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mm Alert</em>' containment reference.
	 * @see #getMmAlert()
	 * @generated
	 */
	void setMmAlert(MmAlertConfig value);

	/**
	 * Returns the value of the '<em><b>Mm Execute Command</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mm Execute Command</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mm Execute Command</em>' containment reference.
	 * @see #setMmExecuteCommand(MmExecuteCommandConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_MmExecuteCommand()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mm-execute-command' namespace='##targetNamespace'"
	 * @generated
	 */
	MmExecuteCommandConfig getMmExecuteCommand();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getMmExecuteCommand <em>Mm Execute Command</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mm Execute Command</em>' containment reference.
	 * @see #getMmExecuteCommand()
	 * @generated
	 */
	void setMmExecuteCommand(MmExecuteCommandConfig value);

	/**
	 * Returns the value of the '<em><b>Mm Health Level</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mm Health Level</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mm Health Level</em>' containment reference.
	 * @see #setMmHealthLevel(MmHealthLevelConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_MmHealthLevel()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mm-health-level' namespace='##targetNamespace'"
	 * @generated
	 */
	MmHealthLevelConfig getMmHealthLevel();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getMmHealthLevel <em>Mm Health Level</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mm Health Level</em>' containment reference.
	 * @see #getMmHealthLevel()
	 * @generated
	 */
	void setMmHealthLevel(MmHealthLevelConfig value);

	/**
	 * Returns the value of the '<em><b>Mm Send Email</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mm Send Email</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mm Send Email</em>' containment reference.
	 * @see #setMmSendEmail(MmSendEmailConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_MmSendEmail()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mm-send-email' namespace='##targetNamespace'"
	 * @generated
	 */
	MmSendEmailConfig getMmSendEmail();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getMmSendEmail <em>Mm Send Email</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mm Send Email</em>' containment reference.
	 * @see #getMmSendEmail()
	 * @generated
	 */
	void setMmSendEmail(MmSendEmailConfig value);

	/**
	 * Returns the value of the '<em><b>Mm Trigger Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mm Trigger Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mm Trigger Condition</em>' containment reference.
	 * @see #setMmTriggerCondition(MmTriggerConditionConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_MmTriggerCondition()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mm-trigger-condition' namespace='##targetNamespace'"
	 * @generated
	 */
	MmTriggerConditionConfig getMmTriggerCondition();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getMmTriggerCondition <em>Mm Trigger Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mm Trigger Condition</em>' containment reference.
	 * @see #getMmTriggerCondition()
	 * @generated
	 */
	void setMmTriggerCondition(MmTriggerConditionConfig value);

	/**
	 * Returns the value of the '<em><b>Mode</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.be.util.config.cdd.DomainObjectModeConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mode</em>' attribute.
	 * @see com.tibco.be.util.config.cdd.DomainObjectModeConfig
	 * @see #setMode(DomainObjectModeConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Mode()
	 * @model unique="false" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mode' namespace='##targetNamespace'"
	 * @generated
	 */
	DomainObjectModeConfig getMode();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getMode <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mode</em>' attribute.
	 * @see com.tibco.be.util.config.cdd.DomainObjectModeConfig
	 * @see #getMode()
	 * @generated
	 */
	void setMode(DomainObjectModeConfig value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' containment reference.
	 * @see #setName(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Name()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getName();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getName <em>Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' containment reference.
	 * @see #getName()
	 * @generated
	 */
	void setName(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Notification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Notification</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Notification</em>' containment reference.
	 * @see #setNotification(NotificationConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Notification()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='notification' namespace='##targetNamespace'"
	 * @generated
	 */
	NotificationConfig getNotification();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getNotification <em>Notification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Notification</em>' containment reference.
	 * @see #getNotification()
	 * @generated
	 */
	void setNotification(NotificationConfig value);

	/**
	 * Returns the value of the '<em><b>Object Management</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Object Management</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object Management</em>' containment reference.
	 * @see #setObjectManagement(ObjectManagementConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_ObjectManagement()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='object-management' namespace='##targetNamespace'"
	 * @generated
	 */
	ObjectManagementConfig getObjectManagement();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getObjectManagement <em>Object Management</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object Management</em>' containment reference.
	 * @see #getObjectManagement()
	 * @generated
	 */
	void setObjectManagement(ObjectManagementConfig value);

	/**
	 * Returns the value of the '<em><b>Object Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Object Table</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object Table</em>' containment reference.
	 * @see #setObjectTable(ObjectTableConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_ObjectTable()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='object-table' namespace='##targetNamespace'"
	 * @generated
	 */
	ObjectTableConfig getObjectTable();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getObjectTable <em>Object Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object Table</em>' containment reference.
	 * @see #getObjectTable()
	 * @generated
	 */
	void setObjectTable(ObjectTableConfig value);

	/**
	 * Returns the value of the '<em><b>Pair Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pair Config</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pair Config</em>' containment reference.
	 * @see #setPairConfig(LoadBalancerPairConfigConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_PairConfig()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='pair-config' namespace='##targetNamespace'"
	 * @generated
	 */
	LoadBalancerPairConfigConfig getPairConfig();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getPairConfig <em>Pair Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pair Config</em>' containment reference.
	 * @see #getPairConfig()
	 * @generated
	 */
	void setPairConfig(LoadBalancerPairConfigConfig value);

	/**
	 * Returns the value of the '<em><b>Pair Configs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pair Configs</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pair Configs</em>' containment reference.
	 * @see #setPairConfigs(LoadBalancerPairConfigsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_PairConfigs()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='pair-configs' namespace='##targetNamespace'"
	 * @generated
	 */
	LoadBalancerPairConfigsConfig getPairConfigs();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getPairConfigs <em>Pair Configs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pair Configs</em>' containment reference.
	 * @see #getPairConfigs()
	 * @generated
	 */
	void setPairConfigs(LoadBalancerPairConfigsConfig value);

	/**
	 * Returns the value of the '<em><b>Parallel Recovery</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parallel Recovery</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parallel Recovery</em>' containment reference.
	 * @see #setParallelRecovery(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_ParallelRecovery()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='parallel-recovery' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getParallelRecovery();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getParallelRecovery <em>Parallel Recovery</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parallel Recovery</em>' containment reference.
	 * @see #getParallelRecovery()
	 * @generated
	 */
	void setParallelRecovery(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Persistence Option</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Persistence Option</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Persistence Option</em>' containment reference.
	 * @see #setPersistenceOption(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_PersistenceOption()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='persistence-option' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getPersistenceOption();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getPersistenceOption <em>Persistence Option</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Persistence Option</em>' containment reference.
	 * @see #getPersistenceOption()
	 * @generated
	 */
	void setPersistenceOption(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Persistence Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Persistence Policy</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Persistence Policy</em>' containment reference.
	 * @see #setPersistencePolicy(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_PersistencePolicy()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='persistence-policy' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getPersistencePolicy();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getPersistencePolicy <em>Persistence Policy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Persistence Policy</em>' containment reference.
	 * @see #getPersistencePolicy()
	 * @generated
	 */
	void setPersistencePolicy(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Policy File</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Policy File</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Policy File</em>' containment reference.
	 * @see #setPolicyFile(SecurityOverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_PolicyFile()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="name='policy-file' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	SecurityOverrideConfig getPolicyFile();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getPolicyFile <em>Policy File</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Policy File</em>' containment reference.
	 * @see #getPolicyFile()
	 * @generated
	 */
	void setPolicyFile(SecurityOverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Port</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Port</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Port</em>' containment reference.
	 * @see #setPort(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Port()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='port' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getPort();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getPort <em>Port</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Port</em>' containment reference.
	 * @see #getPort()
	 * @generated
	 */
	void setPort(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Pre Load Caches</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pre Load Caches</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre Load Caches</em>' containment reference.
	 * @see #setPreLoadCaches(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_PreLoadCaches()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='pre-load-caches' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getPreLoadCaches();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getPreLoadCaches <em>Pre Load Caches</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Load Caches</em>' containment reference.
	 * @see #getPreLoadCaches()
	 * @generated
	 */
	void setPreLoadCaches(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Pre Load Enabled</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pre Load Enabled</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre Load Enabled</em>' containment reference.
	 * @see #setPreLoadEnabled(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_PreLoadEnabled()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='pre-load-enabled' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getPreLoadEnabled();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getPreLoadEnabled <em>Pre Load Enabled</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Load Enabled</em>' containment reference.
	 * @see #getPreLoadEnabled()
	 * @generated
	 */
	void setPreLoadEnabled(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Pre Load Fetch Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pre Load Fetch Size</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre Load Fetch Size</em>' containment reference.
	 * @see #setPreLoadFetchSize(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_PreLoadFetchSize()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='pre-load-fetch-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getPreLoadFetchSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getPreLoadFetchSize <em>Pre Load Fetch Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Load Fetch Size</em>' containment reference.
	 * @see #getPreLoadFetchSize()
	 * @generated
	 */
	void setPreLoadFetchSize(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Pre Load Handles</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pre Load Handles</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre Load Handles</em>' containment reference.
	 * @see #setPreLoadHandles(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_PreLoadHandles()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='pre-load-handles' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getPreLoadHandles();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getPreLoadHandles <em>Pre Load Handles</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Load Handles</em>' containment reference.
	 * @see #getPreLoadHandles()
	 * @generated
	 */
	void setPreLoadHandles(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Pre Processor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pre Processor</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre Processor</em>' attribute.
	 * @see #setPreProcessor(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_PreProcessor()
	 * @model unique="false" dataType="com.tibco.be.util.config.cdd.OntologyUriConfig" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='pre-processor' namespace='##targetNamespace'"
	 * @generated
	 */
	String getPreProcessor();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getPreProcessor <em>Pre Processor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Processor</em>' attribute.
	 * @see #getPreProcessor()
	 * @generated
	 */
	void setPreProcessor(String value);

	/**
	 * Returns the value of the '<em><b>Primary Connection</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primary Connection</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primary Connection</em>' containment reference.
	 * @see #setPrimaryConnection(ConnectionConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_PrimaryConnection()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='primary-connection' namespace='##targetNamespace'"
	 * @generated
	 */
	ConnectionConfig getPrimaryConnection();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getPrimaryConnection <em>Primary Connection</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primary Connection</em>' containment reference.
	 * @see #getPrimaryConnection()
	 * @generated
	 */
	void setPrimaryConnection(ConnectionConfig value);

	/**
	 * Returns the value of the '<em><b>Priority</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Priority</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Priority</em>' containment reference.
	 * @see #setPriority(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Priority()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='priority' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getPriority();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getPriority <em>Priority</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Priority</em>' containment reference.
	 * @see #getPriority()
	 * @generated
	 */
	void setPriority(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Process</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Process</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Process</em>' containment reference.
	 * @see #setProcess(ProcessConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Process()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='process' namespace='##targetNamespace'"
	 * @generated
	 */
	ProcessConfig getProcess();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getProcess <em>Process</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Process</em>' containment reference.
	 * @see #getProcess()
	 * @generated
	 */
	void setProcess(ProcessConfig value);

	/**
	 * Returns the value of the '<em><b>Process Agent Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Process Agent Class</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Process Agent Class</em>' containment reference.
	 * @see #setProcessAgentClass(ProcessAgentClassConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_ProcessAgentClass()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='process-agent-class' namespace='##targetNamespace'"
	 * @generated
	 */
	ProcessAgentClassConfig getProcessAgentClass();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getProcessAgentClass <em>Process Agent Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Process Agent Class</em>' containment reference.
	 * @see #getProcessAgentClass()
	 * @generated
	 */
	void setProcessAgentClass(ProcessAgentClassConfig value);

	/**
	 * Returns the value of the '<em><b>Process Engine</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Process Engine</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Process Engine</em>' containment reference.
	 * @see #setProcessEngine(ProcessEngineConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_ProcessEngine()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='process-engine' namespace='##targetNamespace'"
	 * @generated
	 */
	ProcessEngineConfig getProcessEngine();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getProcessEngine <em>Process Engine</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Process Engine</em>' containment reference.
	 * @see #getProcessEngine()
	 * @generated
	 */
	void setProcessEngine(ProcessEngineConfig value);

	/**
	 * Returns the value of the '<em><b>Processes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Processes</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Processes</em>' containment reference.
	 * @see #setProcesses(ProcessesConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Processes()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='processes' namespace='##targetNamespace'"
	 * @generated
	 */
	ProcessesConfig getProcesses();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getProcesses <em>Processes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Processes</em>' containment reference.
	 * @see #getProcesses()
	 * @generated
	 */
	void setProcesses(ProcessesConfig value);

	/**
	 * Returns the value of the '<em><b>Process Groups</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Process Groups</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Process Groups</em>' containment reference.
	 * @see #setProcessGroups(ProcessGroupsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_ProcessGroups()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='process-groups' namespace='##targetNamespace'"
	 * @generated
	 */
	ProcessGroupsConfig getProcessGroups();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getProcessGroups <em>Process Groups</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Process Groups</em>' containment reference.
	 * @see #getProcessGroups()
	 * @generated
	 */
	void setProcessGroups(ProcessGroupsConfig value);

	/**
	 * Returns the value of the '<em><b>Processing Unit</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Processing Unit</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Processing Unit</em>' containment reference.
	 * @see #setProcessingUnit(ProcessingUnitConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_ProcessingUnit()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='processing-unit' namespace='##targetNamespace'"
	 * @generated
	 */
	ProcessingUnitConfig getProcessingUnit();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getProcessingUnit <em>Processing Unit</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Processing Unit</em>' containment reference.
	 * @see #getProcessingUnit()
	 * @generated
	 */
	void setProcessingUnit(ProcessingUnitConfig value);

	/**
	 * Returns the value of the '<em><b>Processing Units</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Processing Units</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Processing Units</em>' containment reference.
	 * @see #setProcessingUnits(ProcessingUnitsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_ProcessingUnits()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='processing-units' namespace='##targetNamespace'"
	 * @generated
	 */
	ProcessingUnitsConfig getProcessingUnits();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getProcessingUnits <em>Processing Units</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Processing Units</em>' containment reference.
	 * @see #getProcessingUnits()
	 * @generated
	 */
	void setProcessingUnits(ProcessingUnitsConfig value);

	/**
	 * Returns the value of the '<em><b>Projection</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Projection</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Projection</em>' containment reference.
	 * @see #setProjection(ProjectionConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Projection()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='projection' namespace='##targetNamespace'"
	 * @generated
	 */
	ProjectionConfig getProjection();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getProjection <em>Projection</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Projection</em>' containment reference.
	 * @see #getProjection()
	 * @generated
	 */
	void setProjection(ProjectionConfig value);

	/**
	 * Returns the value of the '<em><b>Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property</em>' containment reference.
	 * @see #setProperty(PropertyConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Property()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='property' namespace='##targetNamespace'"
	 * @generated
	 */
	PropertyConfig getProperty();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getProperty <em>Property</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property</em>' containment reference.
	 * @see #getProperty()
	 * @generated
	 */
	void setProperty(PropertyConfig value);

	/**
	 * Returns the value of the '<em><b>Property Cache Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Cache Size</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Cache Size</em>' containment reference.
	 * @see #setPropertyCacheSize(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_PropertyCacheSize()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='property-cache-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getPropertyCacheSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getPropertyCacheSize <em>Property Cache Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Cache Size</em>' containment reference.
	 * @see #getPropertyCacheSize()
	 * @generated
	 */
	void setPropertyCacheSize(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Property Check Interval</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Check Interval</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Check Interval</em>' containment reference.
	 * @see #setPropertyCheckInterval(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_PropertyCheckInterval()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='property-check-interval' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getPropertyCheckInterval();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getPropertyCheckInterval <em>Property Check Interval</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Check Interval</em>' containment reference.
	 * @see #getPropertyCheckInterval()
	 * @generated
	 */
	void setPropertyCheckInterval(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Property Group</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Group</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Group</em>' containment reference.
	 * @see #setPropertyGroup(PropertyGroupConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_PropertyGroup()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='property-group' namespace='##targetNamespace'"
	 * @generated
	 */
	PropertyGroupConfig getPropertyGroup();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getPropertyGroup <em>Property Group</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Group</em>' containment reference.
	 * @see #getPropertyGroup()
	 * @generated
	 */
	void setPropertyGroup(PropertyGroupConfig value);

	/**
	 * Returns the value of the '<em><b>Protocol</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Protocol</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Protocol</em>' containment reference.
	 * @see #setProtocol(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Protocol()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='protocol' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getProtocol();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getProtocol <em>Protocol</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Protocol</em>' containment reference.
	 * @see #getProtocol()
	 * @generated
	 */
	void setProtocol(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Protocols</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Protocols</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Protocols</em>' containment reference.
	 * @see #setProtocols(ProtocolsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Protocols()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='protocols' namespace='##targetNamespace'"
	 * @generated
	 */
	ProtocolsConfig getProtocols();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getProtocols <em>Protocols</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Protocols</em>' containment reference.
	 * @see #getProtocols()
	 * @generated
	 */
	void setProtocols(ProtocolsConfig value);

	/**
	 * Returns the value of the '<em><b>Protocol Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Protocol Timeout</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Protocol Timeout</em>' containment reference.
	 * @see #setProtocolTimeout(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_ProtocolTimeout()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='protocol-timeout' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getProtocolTimeout();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getProtocolTimeout <em>Protocol Timeout</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Protocol Timeout</em>' containment reference.
	 * @see #getProtocolTimeout()
	 * @generated
	 */
	void setProtocolTimeout(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Provider</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provider</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provider</em>' containment reference.
	 * @see #setProvider(ProviderConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Provider()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='provider' namespace='##targetNamespace'"
	 * @generated
	 */
	ProviderConfig getProvider();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getProvider <em>Provider</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provider</em>' containment reference.
	 * @see #getProvider()
	 * @generated
	 */
	void setProvider(ProviderConfig value);

	/**
	 * Returns the value of the '<em><b>Query Agent Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Query Agent Class</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Query Agent Class</em>' containment reference.
	 * @see #setQueryAgentClass(QueryAgentClassConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_QueryAgentClass()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='query-agent-class' namespace='##targetNamespace'"
	 * @generated
	 */
	QueryAgentClassConfig getQueryAgentClass();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getQueryAgentClass <em>Query Agent Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Query Agent Class</em>' containment reference.
	 * @see #getQueryAgentClass()
	 * @generated
	 */
	void setQueryAgentClass(QueryAgentClassConfig value);

	/**
	 * Returns the value of the '<em><b>Queue Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Queue Size</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Queue Size</em>' containment reference.
	 * @see #setQueueSize(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_QueueSize()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='queue-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getQueueSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getQueueSize <em>Queue Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Queue Size</em>' containment reference.
	 * @see #getQueueSize()
	 * @generated
	 */
	void setQueueSize(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Read Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Read Timeout</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Read Timeout</em>' containment reference.
	 * @see #setReadTimeout(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_ReadTimeout()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='read-timeout' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getReadTimeout();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getReadTimeout <em>Read Timeout</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Read Timeout</em>' containment reference.
	 * @see #getReadTimeout()
	 * @generated
	 */
	void setReadTimeout(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Receiver</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Receiver</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Receiver</em>' containment reference.
	 * @see #setReceiver(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Receiver()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='receiver' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getReceiver();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getReceiver <em>Receiver</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Receiver</em>' containment reference.
	 * @see #getReceiver()
	 * @generated
	 */
	void setReceiver(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Remote Listen Url</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Remote Listen Url</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Remote Listen Url</em>' containment reference.
	 * @see #setRemoteListenUrl(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_RemoteListenUrl()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='remote-listen-url' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getRemoteListenUrl();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getRemoteListenUrl <em>Remote Listen Url</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remote Listen Url</em>' containment reference.
	 * @see #getRemoteListenUrl()
	 * @generated
	 */
	void setRemoteListenUrl(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Requester</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Requester</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Requester</em>' containment reference.
	 * @see #setRequester(SecurityRequester)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Requester()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="name='requester' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	SecurityRequester getRequester();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getRequester <em>Requester</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Requester</em>' containment reference.
	 * @see #getRequester()
	 * @generated
	 */
	void setRequester(SecurityRequester value);

	/**
	 * Returns the value of the '<em><b>Retry Count</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Retry Count</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Retry Count</em>' containment reference.
	 * @see #setRetryCount(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_RetryCount()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='retry-count' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getRetryCount();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getRetryCount <em>Retry Count</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Retry Count</em>' containment reference.
	 * @see #getRetryCount()
	 * @generated
	 */
	void setRetryCount(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Reverse References</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reverse References</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reverse References</em>' containment reference.
	 * @see #setReverseReferences(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_ReverseReferences()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='reverse-references' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getReverseReferences();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getReverseReferences <em>Reverse References</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reverse References</em>' containment reference.
	 * @see #getReverseReferences()
	 * @generated
	 */
	void setReverseReferences(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Revision</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Revision</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Revision</em>' containment reference.
	 * @see #setRevision(RevisionConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Revision()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='revision' namespace='##targetNamespace'"
	 * @generated
	 */
	RevisionConfig getRevision();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getRevision <em>Revision</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Revision</em>' containment reference.
	 * @see #getRevision()
	 * @generated
	 */
	void setRevision(RevisionConfig value);

	/**
	 * Returns the value of the '<em><b>Role</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Role</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Role</em>' containment reference.
	 * @see #setRole(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Role()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='role' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getRole();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getRole <em>Role</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Role</em>' containment reference.
	 * @see #getRole()
	 * @generated
	 */
	void setRole(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Roles</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Roles</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Roles</em>' containment reference.
	 * @see #setRoles(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Roles()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='roles' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getRoles();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getRoles <em>Roles</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Roles</em>' containment reference.
	 * @see #getRoles()
	 * @generated
	 */
	void setRoles(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Router</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Router</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Router</em>' containment reference.
	 * @see #setRouter(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Router()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='router' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getRouter();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getRouter <em>Router</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Router</em>' containment reference.
	 * @see #getRouter()
	 * @generated
	 */
	void setRouter(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule</em>' containment reference.
	 * @see #setRule(RuleConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Rule()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='rule' namespace='##targetNamespace'"
	 * @generated
	 */
	RuleConfig getRule();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getRule <em>Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule</em>' containment reference.
	 * @see #getRule()
	 * @generated
	 */
	void setRule(RuleConfig value);

	/**
	 * Returns the value of the '<em><b>Rule Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Config</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Config</em>' containment reference.
	 * @see #setRuleConfig(RuleConfigConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_RuleConfig()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='rule-config' namespace='##targetNamespace'"
	 * @generated
	 */
	RuleConfigConfig getRuleConfig();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getRuleConfig <em>Rule Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Config</em>' containment reference.
	 * @see #getRuleConfig()
	 * @generated
	 */
	void setRuleConfig(RuleConfigConfig value);

	/**
	 * Returns the value of the '<em><b>Rules</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rules</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rules</em>' containment reference.
	 * @see #setRules(RulesConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Rules()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='rules' namespace='##targetNamespace'"
	 * @generated
	 */
	RulesConfig getRules();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getRules <em>Rules</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rules</em>' containment reference.
	 * @see #getRules()
	 * @generated
	 */
	void setRules(RulesConfig value);

	/**
	 * Returns the value of the '<em><b>Rulesets</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rulesets</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rulesets</em>' containment reference.
	 * @see #setRulesets(RulesetsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Rulesets()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='rulesets' namespace='##targetNamespace'"
	 * @generated
	 */
	RulesetsConfig getRulesets();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getRulesets <em>Rulesets</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rulesets</em>' containment reference.
	 * @see #getRulesets()
	 * @generated
	 */
	void setRulesets(RulesetsConfig value);

	/**
	 * Returns the value of the '<em><b>Secondary Connection</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Secondary Connection</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Secondary Connection</em>' containment reference.
	 * @see #setSecondaryConnection(ConnectionConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_SecondaryConnection()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='secondary-connection' namespace='##targetNamespace'"
	 * @generated
	 */
	ConnectionConfig getSecondaryConnection();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getSecondaryConnection <em>Secondary Connection</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Secondary Connection</em>' containment reference.
	 * @see #getSecondaryConnection()
	 * @generated
	 */
	void setSecondaryConnection(ConnectionConfig value);

	/**
	 * Returns the value of the '<em><b>Security Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Security Config</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Security Config</em>' containment reference.
	 * @see #setSecurityConfig(SecurityConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_SecurityConfig()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="name='security-config' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	SecurityConfig getSecurityConfig();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getSecurityConfig <em>Security Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Security Config</em>' containment reference.
	 * @see #getSecurityConfig()
	 * @generated
	 */
	void setSecurityConfig(SecurityConfig value);

	/**
	 * Returns the value of the '<em><b>Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Service</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Service</em>' containment reference.
	 * @see #setService(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Service()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='service' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getService();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getService <em>Service</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Service</em>' containment reference.
	 * @see #getService()
	 * @generated
	 */
	void setService(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Set Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Set Property</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Set Property</em>' containment reference.
	 * @see #setSetProperty(SetPropertyConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_SetProperty()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='set-property' namespace='##targetNamespace'"
	 * @generated
	 */
	SetPropertyConfig getSetProperty();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getSetProperty <em>Set Property</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Set Property</em>' containment reference.
	 * @see #getSetProperty()
	 * @generated
	 */
	void setSetProperty(SetPropertyConfig value);

	/**
	 * Returns the value of the '<em><b>Shared All</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shared All</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shared All</em>' containment reference.
	 * @see #setSharedAll(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_SharedAll()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='shared-all' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getSharedAll();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getSharedAll <em>Shared All</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shared All</em>' containment reference.
	 * @see #getSharedAll()
	 * @generated
	 */
	void setSharedAll(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Shared Queue</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shared Queue</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shared Queue</em>' containment reference.
	 * @see #setSharedQueue(SharedQueueConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_SharedQueue()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='shared-queue' namespace='##targetNamespace'"
	 * @generated
	 */
	SharedQueueConfig getSharedQueue();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getSharedQueue <em>Shared Queue</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shared Queue</em>' containment reference.
	 * @see #getSharedQueue()
	 * @generated
	 */
	void setSharedQueue(SharedQueueConfig value);

	/**
	 * Returns the value of the '<em><b>Shoutdown Wait</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shoutdown Wait</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shoutdown Wait</em>' containment reference.
	 * @see #setShoutdownWait(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_ShoutdownWait()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='shoutdown-wait' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getShoutdownWait();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getShoutdownWait <em>Shoutdown Wait</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shoutdown Wait</em>' containment reference.
	 * @see #getShoutdownWait()
	 * @generated
	 */
	void setShoutdownWait(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Shutdown</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shutdown</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shutdown</em>' containment reference.
	 * @see #setShutdown(FunctionsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Shutdown()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='shutdown' namespace='##targetNamespace'"
	 * @generated
	 */
	FunctionsConfig getShutdown();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getShutdown <em>Shutdown</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shutdown</em>' containment reference.
	 * @see #getShutdown()
	 * @generated
	 */
	void setShutdown(FunctionsConfig value);

	/**
	 * Returns the value of the '<em><b>Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Size</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Size</em>' containment reference.
	 * @see #setSize(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Size()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getSize <em>Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Size</em>' containment reference.
	 * @see #getSize()
	 * @generated
	 */
	void setSize(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Skip Recovery</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Skip Recovery</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Skip Recovery</em>' containment reference.
	 * @see #setSkipRecovery(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_SkipRecovery()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='skip-recovery' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getSkipRecovery();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getSkipRecovery <em>Skip Recovery</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Skip Recovery</em>' containment reference.
	 * @see #getSkipRecovery()
	 * @generated
	 */
	void setSkipRecovery(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Socket Output Buffer Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Socket Output Buffer Size</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Socket Output Buffer Size</em>' containment reference.
	 * @see #setSocketOutputBufferSize(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_SocketOutputBufferSize()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='socket-output-buffer-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getSocketOutputBufferSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getSocketOutputBufferSize <em>Socket Output Buffer Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Socket Output Buffer Size</em>' containment reference.
	 * @see #getSocketOutputBufferSize()
	 * @generated
	 */
	void setSocketOutputBufferSize(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Ssl</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ssl</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ssl</em>' containment reference.
	 * @see #setSsl(SslConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Ssl()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ssl' namespace='##targetNamespace'"
	 * @generated
	 */
	SslConfig getSsl();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getSsl <em>Ssl</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ssl</em>' containment reference.
	 * @see #getSsl()
	 * @generated
	 */
	void setSsl(SslConfig value);

	/**
	 * Returns the value of the '<em><b>Stale Connection Check</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Stale Connection Check</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stale Connection Check</em>' containment reference.
	 * @see #setStaleConnectionCheck(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_StaleConnectionCheck()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='stale-connection-check' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getStaleConnectionCheck();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getStaleConnectionCheck <em>Stale Connection Check</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Stale Connection Check</em>' containment reference.
	 * @see #getStaleConnectionCheck()
	 * @generated
	 */
	void setStaleConnectionCheck(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Startup</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Startup</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Startup</em>' containment reference.
	 * @see #setStartup(FunctionsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Startup()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='startup' namespace='##targetNamespace'"
	 * @generated
	 */
	FunctionsConfig getStartup();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getStartup <em>Startup</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Startup</em>' containment reference.
	 * @see #getStartup()
	 * @generated
	 */
	void setStartup(FunctionsConfig value);

	/**
	 * Returns the value of the '<em><b>Strategy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Strategy</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Strategy</em>' containment reference.
	 * @see #setStrategy(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Strategy()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='strategy' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getStrategy();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getStrategy <em>Strategy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Strategy</em>' containment reference.
	 * @see #getStrategy()
	 * @generated
	 */
	void setStrategy(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Subject</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subject</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subject</em>' containment reference.
	 * @see #setSubject(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Subject()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='subject' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getSubject();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getSubject <em>Subject</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subject</em>' containment reference.
	 * @see #getSubject()
	 * @generated
	 */
	void setSubject(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Subscribe</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subscribe</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subscribe</em>' containment reference.
	 * @see #setSubscribe(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Subscribe()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='subscribe' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getSubscribe();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getSubscribe <em>Subscribe</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subscribe</em>' containment reference.
	 * @see #getSubscribe()
	 * @generated
	 */
	void setSubscribe(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Sys Err Redirect</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sys Err Redirect</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sys Err Redirect</em>' containment reference.
	 * @see #setSysErrRedirect(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_SysErrRedirect()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='sys-err-redirect' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getSysErrRedirect();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getSysErrRedirect <em>Sys Err Redirect</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sys Err Redirect</em>' containment reference.
	 * @see #getSysErrRedirect()
	 * @generated
	 */
	void setSysErrRedirect(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Sys Out Redirect</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sys Out Redirect</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sys Out Redirect</em>' containment reference.
	 * @see #setSysOutRedirect(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_SysOutRedirect()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='sys-out-redirect' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getSysOutRedirect();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getSysOutRedirect <em>Sys Out Redirect</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sys Out Redirect</em>' containment reference.
	 * @see #getSysOutRedirect()
	 * @generated
	 */
	void setSysOutRedirect(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Table Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table Name</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table Name</em>' containment reference.
	 * @see #setTableName(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_TableName()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='table-name' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getTableName();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getTableName <em>Table Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Table Name</em>' containment reference.
	 * @see #getTableName()
	 * @generated
	 */
	void setTableName(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Tcp No Delay</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tcp No Delay</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tcp No Delay</em>' containment reference.
	 * @see #setTcpNoDelay(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_TcpNoDelay()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='tcp-no-delay' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getTcpNoDelay();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getTcpNoDelay <em>Tcp No Delay</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tcp No Delay</em>' containment reference.
	 * @see #getTcpNoDelay()
	 * @generated
	 */
	void setTcpNoDelay(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Terminal</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Terminal</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Terminal</em>' containment reference.
	 * @see #setTerminal(TerminalConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Terminal()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='terminal' namespace='##targetNamespace'"
	 * @generated
	 */
	TerminalConfig getTerminal();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getTerminal <em>Terminal</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Terminal</em>' containment reference.
	 * @see #getTerminal()
	 * @generated
	 */
	void setTerminal(TerminalConfig value);

	/**
	 * Returns the value of the '<em><b>Thread Affinity Rule Function</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Thread Affinity Rule Function</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Thread Affinity Rule Function</em>' attribute.
	 * @see #setThreadAffinityRuleFunction(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_ThreadAffinityRuleFunction()
	 * @model default="" unique="false" dataType="com.tibco.be.util.config.cdd.OntologyUriConfig" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='thread-affinity-rule-function' namespace='##targetNamespace'"
	 * @generated
	 */
	String getThreadAffinityRuleFunction();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getThreadAffinityRuleFunction <em>Thread Affinity Rule Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Thread Affinity Rule Function</em>' attribute.
	 * @see #getThreadAffinityRuleFunction()
	 * @generated
	 */
	void setThreadAffinityRuleFunction(String value);

	/**
	 * Returns the value of the '<em><b>Thread Count</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Thread Count</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Thread Count</em>' containment reference.
	 * @see #setThreadCount(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_ThreadCount()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='thread-count' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getThreadCount();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getThreadCount <em>Thread Count</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Thread Count</em>' containment reference.
	 * @see #getThreadCount()
	 * @generated
	 */
	void setThreadCount(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Threading Model</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.be.util.config.cdd.ThreadingModelConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Threading Model</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Threading Model</em>' attribute.
	 * @see com.tibco.be.util.config.cdd.ThreadingModelConfig
	 * @see #setThreadingModel(ThreadingModelConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_ThreadingModel()
	 * @model unique="false" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='threading-model' namespace='##targetNamespace'"
	 * @generated
	 */
	ThreadingModelConfig getThreadingModel();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getThreadingModel <em>Threading Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Threading Model</em>' attribute.
	 * @see com.tibco.be.util.config.cdd.ThreadingModelConfig
	 * @see #getThreadingModel()
	 * @generated
	 */
	void setThreadingModel(ThreadingModelConfig value);

	/**
	 * Returns the value of the '<em><b>Token File</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Token File</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Token File</em>' containment reference.
	 * @see #setTokenFile(SecurityOverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_TokenFile()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="name='token-file' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	SecurityOverrideConfig getTokenFile();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getTokenFile <em>Token File</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Token File</em>' containment reference.
	 * @see #getTokenFile()
	 * @generated
	 */
	void setTokenFile(SecurityOverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Trust Manager Algorithm</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Trust Manager Algorithm</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trust Manager Algorithm</em>' containment reference.
	 * @see #setTrustManagerAlgorithm(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_TrustManagerAlgorithm()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='trust-manager-algorithm' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getTrustManagerAlgorithm();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getTrustManagerAlgorithm <em>Trust Manager Algorithm</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Trust Manager Algorithm</em>' containment reference.
	 * @see #getTrustManagerAlgorithm()
	 * @generated
	 */
	void setTrustManagerAlgorithm(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Ttl</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ttl</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ttl</em>' containment reference.
	 * @see #setTtl(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Ttl()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ttl' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getTtl();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getTtl <em>Ttl</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ttl</em>' containment reference.
	 * @see #getTtl()
	 * @generated
	 */
	void setTtl(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' containment reference.
	 * @see #setType(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Type()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='type' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getType();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getType <em>Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' containment reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uri</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uri</em>' attribute.
	 * @see #setUri(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Uri()
	 * @model unique="false" dataType="com.tibco.be.util.config.cdd.OntologyUriConfig" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='uri' namespace='##targetNamespace'"
	 * @generated
	 */
	String getUri();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getUri <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uri</em>' attribute.
	 * @see #getUri()
	 * @generated
	 */
	void setUri(String value);

	/**
	 * Returns the value of the '<em><b>User Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User Name</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Name</em>' containment reference.
	 * @see #setUserName(SecurityOverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_UserName()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="name='user-name' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	SecurityOverrideConfig getUserName();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getUserName <em>User Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User Name</em>' containment reference.
	 * @see #getUserName()
	 * @generated
	 */
	void setUserName(SecurityOverrideConfig value);

	/**
	 * Returns the value of the '<em><b>User Password</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User Password</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Password</em>' containment reference.
	 * @see #setUserPassword(SecurityOverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_UserPassword()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="name='user-password' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	SecurityOverrideConfig getUserPassword();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getUserPassword <em>User Password</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User Password</em>' containment reference.
	 * @see #getUserPassword()
	 * @generated
	 */
	void setUserPassword(SecurityOverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Version()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='version' namespace='##targetNamespace'"
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

	/**
	 * Returns the value of the '<em><b>Wait Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Wait Timeout</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Wait Timeout</em>' containment reference.
	 * @see #setWaitTimeout(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_WaitTimeout()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='wait-timeout' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getWaitTimeout();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getWaitTimeout <em>Wait Timeout</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Wait Timeout</em>' containment reference.
	 * @see #getWaitTimeout()
	 * @generated
	 */
	void setWaitTimeout(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Workers</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Workers</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Workers</em>' containment reference.
	 * @see #setWorkers(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Workers()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='workers' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getWorkers();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getWorkers <em>Workers</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Workers</em>' containment reference.
	 * @see #getWorkers()
	 * @generated
	 */
	void setWorkers(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Workerthreads Count</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Workerthreads Count</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Workerthreads Count</em>' containment reference.
	 * @see #setWorkerthreadsCount(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_WorkerthreadsCount()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='workerthreads-count' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getWorkerthreadsCount();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getWorkerthreadsCount <em>Workerthreads Count</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Workerthreads Count</em>' containment reference.
	 * @see #getWorkerthreadsCount()
	 * @generated
	 */
	void setWorkerthreadsCount(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Write Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Write Timeout</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Write Timeout</em>' containment reference.
	 * @see #setWriteTimeout(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_WriteTimeout()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='write-timeout' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getWriteTimeout();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getWriteTimeout <em>Write Timeout</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Write Timeout</em>' containment reference.
	 * @see #getWriteTimeout()
	 * @generated
	 */
	void setWriteTimeout(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Entity</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity</em>' containment reference.
	 * @see #setEntity(EntityConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Entity()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='entity' namespace='##targetNamespace'"
	 * @generated
	 */
	EntityConfig getEntity();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getEntity <em>Entity</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entity</em>' containment reference.
	 * @see #getEntity()
	 * @generated
	 */
	void setEntity(EntityConfig value);

	/**
	 * Returns the value of the '<em><b>Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Filter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Filter</em>' containment reference.
	 * @see #setFilter(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Filter()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='filter' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getFilter();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getFilter <em>Filter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Filter</em>' containment reference.
	 * @see #getFilter()
	 * @generated
	 */
	void setFilter(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Entity Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity Set</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity Set</em>' containment reference.
	 * @see #setEntitySet(EntitySetConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_EntitySet()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='entity-set' namespace='##targetNamespace'"
	 * @generated
	 */
	EntitySetConfig getEntitySet();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getEntitySet <em>Entity Set</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entity Set</em>' containment reference.
	 * @see #getEntitySet()
	 * @generated
	 */
	void setEntitySet(EntitySetConfig value);

	/**
	 * Returns the value of the '<em><b>Enable Table Trimming</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enable Table Trimming</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enable Table Trimming</em>' containment reference.
	 * @see #setEnableTableTrimming(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_EnableTableTrimming()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='enable-table-trimming' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getEnableTableTrimming();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getEnableTableTrimming <em>Enable Table Trimming</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enable Table Trimming</em>' containment reference.
	 * @see #getEnableTableTrimming()
	 * @generated
	 */
	void setEnableTableTrimming(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Trimming Field</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Trimming Field</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trimming Field</em>' containment reference.
	 * @see #setTrimmingField(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_TrimmingField()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='trimming-field' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getTrimmingField();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getTrimmingField <em>Trimming Field</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Trimming Field</em>' containment reference.
	 * @see #getTrimmingField()
	 * @generated
	 */
	void setTrimmingField(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Trimming Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Trimming Rule</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trimming Rule</em>' containment reference.
	 * @see #setTrimmingRule(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_TrimmingRule()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='trimming-rule' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getTrimmingRule();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getTrimmingRule <em>Trimming Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Trimming Rule</em>' containment reference.
	 * @see #getTrimmingRule()
	 * @generated
	 */
	void setTrimmingRule(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Generate LV Files</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generate LV Files</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generate LV Files</em>' containment reference.
	 * @see #setGenerateLVFiles(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_GenerateLVFiles()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='generate-lv-files' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getGenerateLVFiles();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getGenerateLVFiles <em>Generate LV Files</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generate LV Files</em>' containment reference.
	 * @see #getGenerateLVFiles()
	 * @generated
	 */
	void setGenerateLVFiles(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Output Path</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Output Path</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output Path</em>' containment reference.
	 * @see #setOutputPath(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_OutputPath()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='output-path' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getOutputPath();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getOutputPath <em>Output Path</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Output Path</em>' containment reference.
	 * @see #getOutputPath()
	 * @generated
	 */
	void setOutputPath(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Ldm Connection</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ldm Connection</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ldm Connection</em>' containment reference.
	 * @see #setLdmConnection(LDMConnectionConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_LdmConnection()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ldm-connection' namespace='##targetNamespace'"
	 * @generated
	 */
	LDMConnectionConfig getLdmConnection();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getLdmConnection <em>Ldm Connection</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ldm Connection</em>' containment reference.
	 * @see #getLdmConnection()
	 * @generated
	 */
	void setLdmConnection(LDMConnectionConfig value);

	/**
	 * Returns the value of the '<em><b>Ldm Url</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ldm Url</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ldm Url</em>' containment reference.
	 * @see #setLdmUrl(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_LdmUrl()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ldm-url' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getLdmUrl();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getLdmUrl <em>Ldm Url</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ldm Url</em>' containment reference.
	 * @see #getLdmUrl()
	 * @generated
	 */
	void setLdmUrl(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Publisher</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Publisher</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Publisher</em>' containment reference.
	 * @see #setPublisher(PublisherConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_Publisher()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='publisher' namespace='##targetNamespace'"
	 * @generated
	 */
	PublisherConfig getPublisher();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getPublisher <em>Publisher</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Publisher</em>' containment reference.
	 * @see #getPublisher()
	 * @generated
	 */
	void setPublisher(PublisherConfig value);

	/**
	 * Returns the value of the '<em><b>Publisher Queue Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Publisher Queue Size</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Publisher Queue Size</em>' containment reference.
	 * @see #setPublisherQueueSize(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_PublisherQueueSize()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='publisher-queue-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getPublisherQueueSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getPublisherQueueSize <em>Publisher Queue Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Publisher Queue Size</em>' containment reference.
	 * @see #getPublisherQueueSize()
	 * @generated
	 */
	void setPublisherQueueSize(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Publisher Thread Count</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Publisher Thread Count</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Publisher Thread Count</em>' containment reference.
	 * @see #setPublisherThreadCount(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_PublisherThreadCount()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='publisher-thread-count' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getPublisherThreadCount();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getPublisherThreadCount <em>Publisher Thread Count</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Publisher Thread Count</em>' containment reference.
	 * @see #getPublisherThreadCount()
	 * @generated
	 */
	void setPublisherThreadCount(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Liveview Agent Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Liveview Agent Class</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Liveview Agent Class</em>' containment reference.
	 * @see #setLiveviewAgentClass(LiveViewAgentClassConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_LiveviewAgentClass()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='liveview-agent-class' namespace='##targetNamespace'"
	 * @generated
	 */
	LiveViewAgentClassConfig getLiveviewAgentClass();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getLiveviewAgentClass <em>Liveview Agent Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Liveview Agent Class</em>' containment reference.
	 * @see #getLiveviewAgentClass()
	 * @generated
	 */
	void setLiveviewAgentClass(LiveViewAgentClassConfig value);

	/**
	 * Returns the value of the '<em><b>Composite Indexes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Composite Indexes</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Composite Indexes</em>' containment reference.
	 * @see #setCompositeIndexes(CompositeIndexesConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_CompositeIndexes()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='composite-indexes' namespace='##targetNamespace'"
	 * @generated
	 */
	CompositeIndexesConfig getCompositeIndexes();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getCompositeIndexes <em>Composite Indexes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Composite Indexes</em>' containment reference.
	 * @see #getCompositeIndexes()
	 * @generated
	 */
	void setCompositeIndexes(CompositeIndexesConfig value);

	/**
	 * Returns the value of the '<em><b>Composite Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Composite Index</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Composite Index</em>' containment reference.
	 * @see #setCompositeIndex(CompositeIndexConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCddRoot_CompositeIndex()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='composite-index' namespace='##targetNamespace'"
	 * @generated
	 */
	CompositeIndexConfig getCompositeIndex();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CddRoot#getCompositeIndex <em>Composite Index</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Composite Index</em>' containment reference.
	 * @see #getCompositeIndex()
	 * @generated
	 */
	void setCompositeIndex(CompositeIndexConfig value);

} // CddRoot
