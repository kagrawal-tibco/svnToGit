/**
 */
package com.tibco.be.util.config.cdd.impl;

import com.tibco.be.util.config.cdd.AgentClassesConfig;
import com.tibco.be.util.config.cdd.AgentConfig;
import com.tibco.be.util.config.cdd.AgentsConfig;
import com.tibco.be.util.config.cdd.AlertConfigConfig;
import com.tibco.be.util.config.cdd.AlertConfigSetConfig;
import com.tibco.be.util.config.cdd.BackingStoreConfig;
import com.tibco.be.util.config.cdd.BusinessworksConfig;
import com.tibco.be.util.config.cdd.CacheAgentClassConfig;
import com.tibco.be.util.config.cdd.CacheManagerConfig;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.CddRoot;
import com.tibco.be.util.config.cdd.ChildClusterMemberConfig;
import com.tibco.be.util.config.cdd.CiphersConfig;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.ClusterMemberConfig;
import com.tibco.be.util.config.cdd.CompositeIndexConfig;
import com.tibco.be.util.config.cdd.CompositeIndexesConfig;
import com.tibco.be.util.config.cdd.ConditionConfig;
import com.tibco.be.util.config.cdd.ConnectionConfig;
import com.tibco.be.util.config.cdd.DashboardAgentClassConfig;
import com.tibco.be.util.config.cdd.DbConceptsConfig;
import com.tibco.be.util.config.cdd.DestinationConfig;
import com.tibco.be.util.config.cdd.DestinationGroupsConfig;
import com.tibco.be.util.config.cdd.DestinationsConfig;
import com.tibco.be.util.config.cdd.DomainObjectConfig;
import com.tibco.be.util.config.cdd.DomainObjectModeConfig;
import com.tibco.be.util.config.cdd.DomainObjectsConfig;
import com.tibco.be.util.config.cdd.EntityConfig;
import com.tibco.be.util.config.cdd.EntitySetConfig;
import com.tibco.be.util.config.cdd.EvictionConfig;
import com.tibco.be.util.config.cdd.FieldEncryptionConfig;
import com.tibco.be.util.config.cdd.FilesConfig;
import com.tibco.be.util.config.cdd.FunctionGroupsConfig;
import com.tibco.be.util.config.cdd.FunctionsConfig;
import com.tibco.be.util.config.cdd.GetPropertyConfig;
import com.tibco.be.util.config.cdd.HttpConfig;
import com.tibco.be.util.config.cdd.IndexConfig;
import com.tibco.be.util.config.cdd.IndexesConfig;
import com.tibco.be.util.config.cdd.InferenceAgentClassConfig;
import com.tibco.be.util.config.cdd.InferenceEngineConfig;
import com.tibco.be.util.config.cdd.JobManagerConfig;
import com.tibco.be.util.config.cdd.LDMConnectionConfig;
import com.tibco.be.util.config.cdd.LineLayoutConfig;
import com.tibco.be.util.config.cdd.LiveViewAgentClassConfig;
import com.tibco.be.util.config.cdd.LoadBalancerAdhocConfigConfig;
import com.tibco.be.util.config.cdd.LoadBalancerAdhocConfigsConfig;
import com.tibco.be.util.config.cdd.LoadBalancerConfigsConfig;
import com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig;
import com.tibco.be.util.config.cdd.LoadBalancerPairConfigsConfig;
import com.tibco.be.util.config.cdd.LoadConfig;
import com.tibco.be.util.config.cdd.LocalCacheConfig;
import com.tibco.be.util.config.cdd.LogConfigConfig;
import com.tibco.be.util.config.cdd.LogConfigsConfig;
import com.tibco.be.util.config.cdd.MemoryManagerConfig;
import com.tibco.be.util.config.cdd.MmActionConfig;
import com.tibco.be.util.config.cdd.MmActionConfigConfig;
import com.tibco.be.util.config.cdd.MmActionConfigSetConfig;
import com.tibco.be.util.config.cdd.MmAgentClassConfig;
import com.tibco.be.util.config.cdd.MmAlertConfig;
import com.tibco.be.util.config.cdd.MmExecuteCommandConfig;
import com.tibco.be.util.config.cdd.MmHealthLevelConfig;
import com.tibco.be.util.config.cdd.MmSendEmailConfig;
import com.tibco.be.util.config.cdd.MmTriggerConditionConfig;
import com.tibco.be.util.config.cdd.NotificationConfig;
import com.tibco.be.util.config.cdd.ObjectManagementConfig;
import com.tibco.be.util.config.cdd.ObjectTableConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.be.util.config.cdd.ProcessAgentClassConfig;
import com.tibco.be.util.config.cdd.ProcessConfig;
import com.tibco.be.util.config.cdd.ProcessEngineConfig;
import com.tibco.be.util.config.cdd.ProcessGroupsConfig;
import com.tibco.be.util.config.cdd.ProcessesConfig;
import com.tibco.be.util.config.cdd.ProcessingUnitConfig;
import com.tibco.be.util.config.cdd.ProcessingUnitsConfig;
import com.tibco.be.util.config.cdd.ProjectionConfig;
import com.tibco.be.util.config.cdd.PropertyConfig;
import com.tibco.be.util.config.cdd.PropertyGroupConfig;
import com.tibco.be.util.config.cdd.ProtocolsConfig;
import com.tibco.be.util.config.cdd.ProviderConfig;
import com.tibco.be.util.config.cdd.PublisherConfig;
import com.tibco.be.util.config.cdd.QueryAgentClassConfig;
import com.tibco.be.util.config.cdd.RevisionConfig;
import com.tibco.be.util.config.cdd.RuleConfig;
import com.tibco.be.util.config.cdd.RuleConfigConfig;
import com.tibco.be.util.config.cdd.RulesConfig;
import com.tibco.be.util.config.cdd.RulesetsConfig;
import com.tibco.be.util.config.cdd.SecurityConfig;
import com.tibco.be.util.config.cdd.SecurityController;
import com.tibco.be.util.config.cdd.SecurityOverrideConfig;
import com.tibco.be.util.config.cdd.SecurityRequester;
import com.tibco.be.util.config.cdd.SetPropertyConfig;
import com.tibco.be.util.config.cdd.SharedQueueConfig;
import com.tibco.be.util.config.cdd.SslConfig;
import com.tibco.be.util.config.cdd.TerminalConfig;
import com.tibco.be.util.config.cdd.ThreadingModelConfig;
import com.tibco.be.util.config.cdd.UrisConfig;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getAcceptCount <em>Accept Count</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getAddress <em>Address</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getAdhocConfig <em>Adhoc Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getAdhocConfigs <em>Adhoc Configs</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getAgent <em>Agent</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getAgentClasses <em>Agent Classes</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getAgents <em>Agents</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getAlertConfig <em>Alert Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getAlertConfigSet <em>Alert Config Set</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getAppend <em>Append</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getArg <em>Arg</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getAuthor <em>Author</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getBackingStore <em>Backing Store</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getBackupCopies <em>Backup Copies</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getBusinessworks <em>Businessworks</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getCache <em>Cache</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getCacheAgentClass <em>Cache Agent Class</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getCacheAgentQuorum <em>Cache Agent Quorum</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getCacheAside <em>Cache Aside</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getCacheLimited <em>Cache Limited</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getCacheLoaderClass <em>Cache Loader Class</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getCacheManager <em>Cache Manager</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getCacheMode <em>Cache Mode</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getCacheStorageEnabled <em>Cache Storage Enabled</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getCertificateKeyFile <em>Certificate Key File</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getChannel <em>Channel</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getCheckForDuplicates <em>Check For Duplicates</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getCheckForVersion <em>Check For Version</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getCheckInterval <em>Check Interval</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getCheckpointInterval <em>Checkpoint Interval</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getCheckpointOpsLimit <em>Checkpoint Ops Limit</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getChildClusterMember <em>Child Cluster Member</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getCipher <em>Cipher</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getCiphers <em>Ciphers</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getClass_ <em>Class</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getCluster <em>Cluster</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getClusterMember <em>Cluster Member</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getComment <em>Comment</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getConcurrentRtc <em>Concurrent Rtc</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getCondition <em>Condition</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getConnectionLinger <em>Connection Linger</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getConnectionTimeout <em>Connection Timeout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getConstant <em>Constant</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getController <em>Controller</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getDaemon <em>Daemon</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getDashboardAgentClass <em>Dashboard Agent Class</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getDataStorePath <em>Data Store Path</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getDate <em>Date</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getDbConcepts <em>Db Concepts</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getDbDir <em>Db Dir</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getDbUris <em>Db Uris</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getDefaultMode <em>Default Mode</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getDeleteRetracted <em>Delete Retracted</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getDestination <em>Destination</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getDestinationGroups <em>Destination Groups</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getDestinations <em>Destinations</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getDir <em>Dir</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getDiscoveryUrl <em>Discovery Url</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getDocumentPage <em>Document Page</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getDocumentRoot <em>Document Root</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getDomainName <em>Domain Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getDomainObject <em>Domain Object</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getDomainObjects <em>Domain Objects</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getEnabled <em>Enabled</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getEnableTracking <em>Enable Tracking</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getEncoding <em>Encoding</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getEncryption <em>Encryption</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getEnforcePools <em>Enforce Pools</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getEntityCacheSize <em>Entity Cache Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getEviction <em>Eviction</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getEvictOnUpdate <em>Evict On Update</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getExplicitTuple <em>Explicit Tuple</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getFiles <em>Files</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getFunctionGroups <em>Function Groups</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getFunctions <em>Functions</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getGetProperty <em>Get Property</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getHotDeploy <em>Hot Deploy</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getHttp <em>Http</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getIdentityPassword <em>Identity Password</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getInactivityTimeout <em>Inactivity Timeout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getIndex <em>Index</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getIndexes <em>Indexes</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getInferenceAgentClass <em>Inference Agent Class</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getInferenceEngine <em>Inference Engine</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getInitialSize <em>Initial Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getJobManager <em>Job Manager</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getJobPoolQueueSize <em>Job Pool Queue Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getJobPoolThreadCount <em>Job Pool Thread Count</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getKey <em>Key</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getKeyManagerAlgorithm <em>Key Manager Algorithm</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getLineLayout <em>Line Layout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getListenUrl <em>Listen Url</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getLoad <em>Load</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getLoadBalancerConfigs <em>Load Balancer Configs</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getLocalCache <em>Local Cache</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getLockTimeout <em>Lock Timeout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getLogConfig <em>Log Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getLogConfigs <em>Log Configs</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getMaxActive <em>Max Active</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getMaxNumber <em>Max Number</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getMaxProcessors <em>Max Processors</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getMaxSize <em>Max Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getMaxTime <em>Max Time</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getMemoryManager <em>Memory Manager</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getMessageEncoding <em>Message Encoding</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getMinSize <em>Min Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getMmAction <em>Mm Action</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getMmActionConfig <em>Mm Action Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getMmActionConfigSet <em>Mm Action Config Set</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getMmAgentClass <em>Mm Agent Class</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getMmAlert <em>Mm Alert</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getMmExecuteCommand <em>Mm Execute Command</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getMmHealthLevel <em>Mm Health Level</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getMmSendEmail <em>Mm Send Email</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getMmTriggerCondition <em>Mm Trigger Condition</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getMode <em>Mode</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getNotification <em>Notification</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getObjectManagement <em>Object Management</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getObjectTable <em>Object Table</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getPairConfig <em>Pair Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getPairConfigs <em>Pair Configs</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getParallelRecovery <em>Parallel Recovery</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getPersistenceOption <em>Persistence Option</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getPersistencePolicy <em>Persistence Policy</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getPolicyFile <em>Policy File</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getPort <em>Port</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getPreLoadCaches <em>Pre Load Caches</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getPreLoadEnabled <em>Pre Load Enabled</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getPreLoadFetchSize <em>Pre Load Fetch Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getPreLoadHandles <em>Pre Load Handles</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getPreProcessor <em>Pre Processor</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getPrimaryConnection <em>Primary Connection</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getPriority <em>Priority</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getProcess <em>Process</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getProcessAgentClass <em>Process Agent Class</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getProcessEngine <em>Process Engine</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getProcesses <em>Processes</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getProcessGroups <em>Process Groups</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getProcessingUnit <em>Processing Unit</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getProcessingUnits <em>Processing Units</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getProjection <em>Projection</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getProperty <em>Property</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getPropertyCacheSize <em>Property Cache Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getPropertyCheckInterval <em>Property Check Interval</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getPropertyGroup <em>Property Group</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getProtocol <em>Protocol</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getProtocols <em>Protocols</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getProtocolTimeout <em>Protocol Timeout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getProvider <em>Provider</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getQueryAgentClass <em>Query Agent Class</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getQueueSize <em>Queue Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getReadTimeout <em>Read Timeout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getReceiver <em>Receiver</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getRemoteListenUrl <em>Remote Listen Url</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getRequester <em>Requester</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getRetryCount <em>Retry Count</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getReverseReferences <em>Reverse References</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getRevision <em>Revision</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getRole <em>Role</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getRoles <em>Roles</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getRouter <em>Router</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getRule <em>Rule</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getRuleConfig <em>Rule Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getRules <em>Rules</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getRulesets <em>Rulesets</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getSecondaryConnection <em>Secondary Connection</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getSecurityConfig <em>Security Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getService <em>Service</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getSetProperty <em>Set Property</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getSharedAll <em>Shared All</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getSharedQueue <em>Shared Queue</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getShoutdownWait <em>Shoutdown Wait</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getShutdown <em>Shutdown</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getSize <em>Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getSkipRecovery <em>Skip Recovery</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getSocketOutputBufferSize <em>Socket Output Buffer Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getSsl <em>Ssl</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getStaleConnectionCheck <em>Stale Connection Check</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getStartup <em>Startup</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getStrategy <em>Strategy</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getSubject <em>Subject</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getSubscribe <em>Subscribe</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getSysErrRedirect <em>Sys Err Redirect</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getSysOutRedirect <em>Sys Out Redirect</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getTableName <em>Table Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getTcpNoDelay <em>Tcp No Delay</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getTerminal <em>Terminal</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getThreadAffinityRuleFunction <em>Thread Affinity Rule Function</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getThreadCount <em>Thread Count</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getThreadingModel <em>Threading Model</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getTokenFile <em>Token File</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getTrustManagerAlgorithm <em>Trust Manager Algorithm</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getTtl <em>Ttl</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getUri <em>Uri</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getUserName <em>User Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getUserPassword <em>User Password</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getWaitTimeout <em>Wait Timeout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getWorkers <em>Workers</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getWorkerthreadsCount <em>Workerthreads Count</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getWriteTimeout <em>Write Timeout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getEntity <em>Entity</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getFilter <em>Filter</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getEntitySet <em>Entity Set</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getEnableTableTrimming <em>Enable Table Trimming</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getTrimmingField <em>Trimming Field</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getTrimmingRule <em>Trimming Rule</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getGenerateLVFiles <em>Generate LV Files</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getOutputPath <em>Output Path</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getLdmConnection <em>Ldm Connection</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getLdmUrl <em>Ldm Url</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getPublisher <em>Publisher</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getPublisherQueueSize <em>Publisher Queue Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getPublisherThreadCount <em>Publisher Thread Count</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getLiveviewAgentClass <em>Liveview Agent Class</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getCompositeIndexes <em>Composite Indexes</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CddRootImpl#getCompositeIndex <em>Composite Index</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CddRootImpl extends EObjectImpl implements CddRoot {
	/**
	 * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMixed()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap mixed;

	/**
	 * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXMLNSPrefixMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xMLNSPrefixMap;

	/**
	 * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXSISchemaLocation()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xSISchemaLocation;

	/**
	 * The default value of the '{@link #getCacheMode() <em>Cache Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCacheMode()
	 * @generated
	 * @ordered
	 */
	protected static final DomainObjectModeConfig CACHE_MODE_EDEFAULT = DomainObjectModeConfig.CACHE;

	/**
	 * The default value of the '{@link #getDefaultMode() <em>Default Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultMode()
	 * @generated
	 * @ordered
	 */
	protected static final DomainObjectModeConfig DEFAULT_MODE_EDEFAULT = DomainObjectModeConfig.CACHE;

	/**
	 * The default value of the '{@link #getMessageEncoding() <em>Message Encoding</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessageEncoding()
	 * @generated
	 * @ordered
	 */
	protected static final String MESSAGE_ENCODING_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getMode() <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMode()
	 * @generated
	 * @ordered
	 */
	protected static final DomainObjectModeConfig MODE_EDEFAULT = DomainObjectModeConfig.CACHE;

	/**
	 * The default value of the '{@link #getPreProcessor() <em>Pre Processor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreProcessor()
	 * @generated
	 * @ordered
	 */
	protected static final String PRE_PROCESSOR_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getThreadAffinityRuleFunction() <em>Thread Affinity Rule Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThreadAffinityRuleFunction()
	 * @generated
	 * @ordered
	 */
	protected static final String THREAD_AFFINITY_RULE_FUNCTION_EDEFAULT = "";

	/**
	 * The default value of the '{@link #getThreadingModel() <em>Threading Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThreadingModel()
	 * @generated
	 * @ordered
	 */
	protected static final ThreadingModelConfig THREADING_MODEL_EDEFAULT = ThreadingModelConfig.SHARED_QUEUE;

	/**
	 * The default value of the '{@link #getUri() <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUri()
	 * @generated
	 * @ordered
	 */
	protected static final String URI_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CddRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getCddRoot();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, CddPackage.CDD_ROOT__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getXMLNSPrefixMap() {
		if (xMLNSPrefixMap == null) {
			xMLNSPrefixMap = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, CddPackage.CDD_ROOT__XMLNS_PREFIX_MAP);
		}
		return xMLNSPrefixMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getXSISchemaLocation() {
		if (xSISchemaLocation == null) {
			xSISchemaLocation = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, CddPackage.CDD_ROOT__XSI_SCHEMA_LOCATION);
		}
		return xSISchemaLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getAcceptCount() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_AcceptCount(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAcceptCount(OverrideConfig newAcceptCount, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_AcceptCount(), newAcceptCount, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAcceptCount(OverrideConfig newAcceptCount) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_AcceptCount(), newAcceptCount);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getAddress() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Address(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAddress(OverrideConfig newAddress, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Address(), newAddress, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAddress(OverrideConfig newAddress) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Address(), newAddress);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LoadBalancerAdhocConfigConfig getAdhocConfig() {
		return (LoadBalancerAdhocConfigConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_AdhocConfig(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAdhocConfig(LoadBalancerAdhocConfigConfig newAdhocConfig, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_AdhocConfig(), newAdhocConfig, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAdhocConfig(LoadBalancerAdhocConfigConfig newAdhocConfig) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_AdhocConfig(), newAdhocConfig);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LoadBalancerAdhocConfigsConfig getAdhocConfigs() {
		return (LoadBalancerAdhocConfigsConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_AdhocConfigs(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAdhocConfigs(LoadBalancerAdhocConfigsConfig newAdhocConfigs, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_AdhocConfigs(), newAdhocConfigs, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAdhocConfigs(LoadBalancerAdhocConfigsConfig newAdhocConfigs) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_AdhocConfigs(), newAdhocConfigs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AgentConfig getAgent() {
		return (AgentConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Agent(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAgent(AgentConfig newAgent, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Agent(), newAgent, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAgent(AgentConfig newAgent) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Agent(), newAgent);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AgentClassesConfig getAgentClasses() {
		return (AgentClassesConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_AgentClasses(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAgentClasses(AgentClassesConfig newAgentClasses, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_AgentClasses(), newAgentClasses, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAgentClasses(AgentClassesConfig newAgentClasses) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_AgentClasses(), newAgentClasses);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AgentsConfig getAgents() {
		return (AgentsConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Agents(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAgents(AgentsConfig newAgents, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Agents(), newAgents, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAgents(AgentsConfig newAgents) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Agents(), newAgents);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlertConfigConfig getAlertConfig() {
		return (AlertConfigConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_AlertConfig(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAlertConfig(AlertConfigConfig newAlertConfig, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_AlertConfig(), newAlertConfig, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAlertConfig(AlertConfigConfig newAlertConfig) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_AlertConfig(), newAlertConfig);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlertConfigSetConfig getAlertConfigSet() {
		return (AlertConfigSetConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_AlertConfigSet(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAlertConfigSet(AlertConfigSetConfig newAlertConfigSet, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_AlertConfigSet(), newAlertConfigSet, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAlertConfigSet(AlertConfigSetConfig newAlertConfigSet) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_AlertConfigSet(), newAlertConfigSet);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getAppend() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Append(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAppend(OverrideConfig newAppend, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Append(), newAppend, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAppend(OverrideConfig newAppend) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Append(), newAppend);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getArg() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Arg(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetArg(OverrideConfig newArg, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Arg(), newArg, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArg(OverrideConfig newArg) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Arg(), newArg);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getAuthor() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Author(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAuthor(OverrideConfig newAuthor, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Author(), newAuthor, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthor(OverrideConfig newAuthor) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Author(), newAuthor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BackingStoreConfig getBackingStore() {
		return (BackingStoreConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_BackingStore(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBackingStore(BackingStoreConfig newBackingStore, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_BackingStore(), newBackingStore, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBackingStore(BackingStoreConfig newBackingStore) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_BackingStore(), newBackingStore);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getBackupCopies() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_BackupCopies(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBackupCopies(OverrideConfig newBackupCopies, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_BackupCopies(), newBackupCopies, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBackupCopies(OverrideConfig newBackupCopies) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_BackupCopies(), newBackupCopies);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BusinessworksConfig getBusinessworks() {
		return (BusinessworksConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Businessworks(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBusinessworks(BusinessworksConfig newBusinessworks, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Businessworks(), newBusinessworks, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBusinessworks(BusinessworksConfig newBusinessworks) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Businessworks(), newBusinessworks);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CacheAgentClassConfig getCache() {
		return (CacheAgentClassConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Cache(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCache(CacheAgentClassConfig newCache, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Cache(), newCache, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCache(CacheAgentClassConfig newCache) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Cache(), newCache);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CacheAgentClassConfig getCacheAgentClass() {
		return (CacheAgentClassConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_CacheAgentClass(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCacheAgentClass(CacheAgentClassConfig newCacheAgentClass, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_CacheAgentClass(), newCacheAgentClass, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCacheAgentClass(CacheAgentClassConfig newCacheAgentClass) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_CacheAgentClass(), newCacheAgentClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getCacheAgentQuorum() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_CacheAgentQuorum(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCacheAgentQuorum(OverrideConfig newCacheAgentQuorum, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_CacheAgentQuorum(), newCacheAgentQuorum, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCacheAgentQuorum(OverrideConfig newCacheAgentQuorum) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_CacheAgentQuorum(), newCacheAgentQuorum);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getCacheAside() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_CacheAside(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCacheAside(OverrideConfig newCacheAside, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_CacheAside(), newCacheAside, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCacheAside(OverrideConfig newCacheAside) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_CacheAside(), newCacheAside);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getCacheLimited() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_CacheLimited(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCacheLimited(OverrideConfig newCacheLimited, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_CacheLimited(), newCacheLimited, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCacheLimited(OverrideConfig newCacheLimited) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_CacheLimited(), newCacheLimited);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getCacheLoaderClass() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_CacheLoaderClass(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCacheLoaderClass(OverrideConfig newCacheLoaderClass, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_CacheLoaderClass(), newCacheLoaderClass, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCacheLoaderClass(OverrideConfig newCacheLoaderClass) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_CacheLoaderClass(), newCacheLoaderClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CacheManagerConfig getCacheManager() {
		return (CacheManagerConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_CacheManager(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCacheManager(CacheManagerConfig newCacheManager, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_CacheManager(), newCacheManager, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCacheManager(CacheManagerConfig newCacheManager) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_CacheManager(), newCacheManager);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainObjectModeConfig getCacheMode() {
		return (DomainObjectModeConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_CacheMode(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCacheMode(DomainObjectModeConfig newCacheMode) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_CacheMode(), newCacheMode);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getCacheStorageEnabled() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_CacheStorageEnabled(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCacheStorageEnabled(OverrideConfig newCacheStorageEnabled, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_CacheStorageEnabled(), newCacheStorageEnabled, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCacheStorageEnabled(OverrideConfig newCacheStorageEnabled) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_CacheStorageEnabled(), newCacheStorageEnabled);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityOverrideConfig getCertificateKeyFile() {
		return (SecurityOverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_CertificateKeyFile(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCertificateKeyFile(SecurityOverrideConfig newCertificateKeyFile, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_CertificateKeyFile(), newCertificateKeyFile, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCertificateKeyFile(SecurityOverrideConfig newCertificateKeyFile) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_CertificateKeyFile(), newCertificateKeyFile);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getChannel() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Channel(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetChannel(OverrideConfig newChannel, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Channel(), newChannel, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChannel(OverrideConfig newChannel) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Channel(), newChannel);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getCheckForDuplicates() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_CheckForDuplicates(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCheckForDuplicates(OverrideConfig newCheckForDuplicates, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_CheckForDuplicates(), newCheckForDuplicates, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCheckForDuplicates(OverrideConfig newCheckForDuplicates) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_CheckForDuplicates(), newCheckForDuplicates);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getCheckForVersion() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_CheckForVersion(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCheckForVersion(OverrideConfig newCheckForVersion, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_CheckForVersion(), newCheckForVersion, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCheckForVersion(OverrideConfig newCheckForVersion) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_CheckForVersion(), newCheckForVersion);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getCheckInterval() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_CheckInterval(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCheckInterval(OverrideConfig newCheckInterval, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_CheckInterval(), newCheckInterval, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCheckInterval(OverrideConfig newCheckInterval) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_CheckInterval(), newCheckInterval);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getCheckpointInterval() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_CheckpointInterval(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCheckpointInterval(OverrideConfig newCheckpointInterval, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_CheckpointInterval(), newCheckpointInterval, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCheckpointInterval(OverrideConfig newCheckpointInterval) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_CheckpointInterval(), newCheckpointInterval);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getCheckpointOpsLimit() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_CheckpointOpsLimit(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCheckpointOpsLimit(OverrideConfig newCheckpointOpsLimit, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_CheckpointOpsLimit(), newCheckpointOpsLimit, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCheckpointOpsLimit(OverrideConfig newCheckpointOpsLimit) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_CheckpointOpsLimit(), newCheckpointOpsLimit);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChildClusterMemberConfig getChildClusterMember() {
		return (ChildClusterMemberConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_ChildClusterMember(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetChildClusterMember(ChildClusterMemberConfig newChildClusterMember, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_ChildClusterMember(), newChildClusterMember, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChildClusterMember(ChildClusterMemberConfig newChildClusterMember) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_ChildClusterMember(), newChildClusterMember);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getCipher() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Cipher(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCipher(OverrideConfig newCipher, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Cipher(), newCipher, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCipher(OverrideConfig newCipher) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Cipher(), newCipher);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CiphersConfig getCiphers() {
		return (CiphersConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Ciphers(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCiphers(CiphersConfig newCiphers, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Ciphers(), newCiphers, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCiphers(CiphersConfig newCiphers) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Ciphers(), newCiphers);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getClass_() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Class(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetClass(OverrideConfig newClass, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Class(), newClass, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClass(OverrideConfig newClass) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Class(), newClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClusterConfig getCluster() {
		return (ClusterConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Cluster(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCluster(ClusterConfig newCluster, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Cluster(), newCluster, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCluster(ClusterConfig newCluster) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Cluster(), newCluster);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClusterMemberConfig getClusterMember() {
		return (ClusterMemberConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_ClusterMember(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetClusterMember(ClusterMemberConfig newClusterMember, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_ClusterMember(), newClusterMember, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClusterMember(ClusterMemberConfig newClusterMember) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_ClusterMember(), newClusterMember);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getComment() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Comment(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetComment(OverrideConfig newComment, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Comment(), newComment, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComment(OverrideConfig newComment) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Comment(), newComment);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getConcurrentRtc() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_ConcurrentRtc(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConcurrentRtc(OverrideConfig newConcurrentRtc, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_ConcurrentRtc(), newConcurrentRtc, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConcurrentRtc(OverrideConfig newConcurrentRtc) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_ConcurrentRtc(), newConcurrentRtc);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConditionConfig getCondition() {
		return (ConditionConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Condition(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCondition(ConditionConfig newCondition, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Condition(), newCondition, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCondition(ConditionConfig newCondition) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Condition(), newCondition);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getConnectionLinger() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_ConnectionLinger(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConnectionLinger(OverrideConfig newConnectionLinger, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_ConnectionLinger(), newConnectionLinger, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConnectionLinger(OverrideConfig newConnectionLinger) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_ConnectionLinger(), newConnectionLinger);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getConnectionTimeout() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_ConnectionTimeout(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConnectionTimeout(OverrideConfig newConnectionTimeout, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_ConnectionTimeout(), newConnectionTimeout, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConnectionTimeout(OverrideConfig newConnectionTimeout) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_ConnectionTimeout(), newConnectionTimeout);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getConstant() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Constant(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConstant(OverrideConfig newConstant, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Constant(), newConstant, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConstant(OverrideConfig newConstant) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Constant(), newConstant);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityController getController() {
		return (SecurityController)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Controller(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetController(SecurityController newController, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Controller(), newController, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setController(SecurityController newController) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Controller(), newController);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getDaemon() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Daemon(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDaemon(OverrideConfig newDaemon, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Daemon(), newDaemon, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDaemon(OverrideConfig newDaemon) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Daemon(), newDaemon);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DashboardAgentClassConfig getDashboardAgentClass() {
		return (DashboardAgentClassConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_DashboardAgentClass(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDashboardAgentClass(DashboardAgentClassConfig newDashboardAgentClass, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_DashboardAgentClass(), newDashboardAgentClass, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDashboardAgentClass(DashboardAgentClassConfig newDashboardAgentClass) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_DashboardAgentClass(), newDashboardAgentClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getDataStorePath() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_DataStorePath(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDataStorePath(OverrideConfig newDataStorePath, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_DataStorePath(), newDataStorePath, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDataStorePath(OverrideConfig newDataStorePath) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_DataStorePath(), newDataStorePath);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getDate() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Date(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDate(OverrideConfig newDate, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Date(), newDate, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDate(OverrideConfig newDate) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Date(), newDate);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DbConceptsConfig getDbConcepts() {
		return (DbConceptsConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_DbConcepts(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDbConcepts(DbConceptsConfig newDbConcepts, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_DbConcepts(), newDbConcepts, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDbConcepts(DbConceptsConfig newDbConcepts) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_DbConcepts(), newDbConcepts);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getDbDir() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_DbDir(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDbDir(OverrideConfig newDbDir, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_DbDir(), newDbDir, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDbDir(OverrideConfig newDbDir) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_DbDir(), newDbDir);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UrisConfig getDbUris() {
		return (UrisConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_DbUris(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDbUris(UrisConfig newDbUris, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_DbUris(), newDbUris, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDbUris(UrisConfig newDbUris) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_DbUris(), newDbUris);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainObjectModeConfig getDefaultMode() {
		return (DomainObjectModeConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_DefaultMode(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefaultMode(DomainObjectModeConfig newDefaultMode) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_DefaultMode(), newDefaultMode);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getDeleteRetracted() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_DeleteRetracted(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeleteRetracted(OverrideConfig newDeleteRetracted, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_DeleteRetracted(), newDeleteRetracted, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeleteRetracted(OverrideConfig newDeleteRetracted) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_DeleteRetracted(), newDeleteRetracted);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DestinationConfig getDestination() {
		return (DestinationConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Destination(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDestination(DestinationConfig newDestination, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Destination(), newDestination, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDestination(DestinationConfig newDestination) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Destination(), newDestination);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DestinationGroupsConfig getDestinationGroups() {
		return (DestinationGroupsConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_DestinationGroups(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDestinationGroups(DestinationGroupsConfig newDestinationGroups, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_DestinationGroups(), newDestinationGroups, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDestinationGroups(DestinationGroupsConfig newDestinationGroups) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_DestinationGroups(), newDestinationGroups);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DestinationsConfig getDestinations() {
		return (DestinationsConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Destinations(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDestinations(DestinationsConfig newDestinations, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Destinations(), newDestinations, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDestinations(DestinationsConfig newDestinations) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Destinations(), newDestinations);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getDir() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Dir(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDir(OverrideConfig newDir, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Dir(), newDir, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDir(OverrideConfig newDir) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Dir(), newDir);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getDiscoveryUrl() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_DiscoveryUrl(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDiscoveryUrl(OverrideConfig newDiscoveryUrl, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_DiscoveryUrl(), newDiscoveryUrl, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDiscoveryUrl(OverrideConfig newDiscoveryUrl) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_DiscoveryUrl(), newDiscoveryUrl);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getDocumentPage() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_DocumentPage(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDocumentPage(OverrideConfig newDocumentPage, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_DocumentPage(), newDocumentPage, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDocumentPage(OverrideConfig newDocumentPage) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_DocumentPage(), newDocumentPage);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getDocumentRoot() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_DocumentRoot(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDocumentRoot(OverrideConfig newDocumentRoot, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_DocumentRoot(), newDocumentRoot, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDocumentRoot(OverrideConfig newDocumentRoot) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_DocumentRoot(), newDocumentRoot);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityOverrideConfig getDomainName() {
		return (SecurityOverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_DomainName(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDomainName(SecurityOverrideConfig newDomainName, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_DomainName(), newDomainName, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDomainName(SecurityOverrideConfig newDomainName) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_DomainName(), newDomainName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainObjectConfig getDomainObject() {
		return (DomainObjectConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_DomainObject(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDomainObject(DomainObjectConfig newDomainObject, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_DomainObject(), newDomainObject, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDomainObject(DomainObjectConfig newDomainObject) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_DomainObject(), newDomainObject);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainObjectsConfig getDomainObjects() {
		return (DomainObjectsConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_DomainObjects(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDomainObjects(DomainObjectsConfig newDomainObjects, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_DomainObjects(), newDomainObjects, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDomainObjects(DomainObjectsConfig newDomainObjects) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_DomainObjects(), newDomainObjects);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getEnabled() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Enabled(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEnabled(OverrideConfig newEnabled, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Enabled(), newEnabled, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnabled(OverrideConfig newEnabled) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Enabled(), newEnabled);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getEnableTracking() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_EnableTracking(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEnableTracking(OverrideConfig newEnableTracking, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_EnableTracking(), newEnableTracking, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnableTracking(OverrideConfig newEnableTracking) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_EnableTracking(), newEnableTracking);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getEncoding() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Encoding(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEncoding(OverrideConfig newEncoding, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Encoding(), newEncoding, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEncoding(OverrideConfig newEncoding) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Encoding(), newEncoding);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FieldEncryptionConfig getEncryption() {
		return (FieldEncryptionConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Encryption(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEncryption(FieldEncryptionConfig newEncryption, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Encryption(), newEncryption, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEncryption(FieldEncryptionConfig newEncryption) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Encryption(), newEncryption);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getEnforcePools() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_EnforcePools(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEnforcePools(OverrideConfig newEnforcePools, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_EnforcePools(), newEnforcePools, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnforcePools(OverrideConfig newEnforcePools) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_EnforcePools(), newEnforcePools);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getEntityCacheSize() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_EntityCacheSize(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEntityCacheSize(OverrideConfig newEntityCacheSize, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_EntityCacheSize(), newEntityCacheSize, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntityCacheSize(OverrideConfig newEntityCacheSize) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_EntityCacheSize(), newEntityCacheSize);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EvictionConfig getEviction() {
		return (EvictionConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Eviction(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEviction(EvictionConfig newEviction, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Eviction(), newEviction, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEviction(EvictionConfig newEviction) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Eviction(), newEviction);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getEvictOnUpdate() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_EvictOnUpdate(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEvictOnUpdate(OverrideConfig newEvictOnUpdate, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_EvictOnUpdate(), newEvictOnUpdate, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEvictOnUpdate(OverrideConfig newEvictOnUpdate) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_EvictOnUpdate(), newEvictOnUpdate);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getExplicitTuple() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_ExplicitTuple(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExplicitTuple(OverrideConfig newExplicitTuple, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_ExplicitTuple(), newExplicitTuple, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExplicitTuple(OverrideConfig newExplicitTuple) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_ExplicitTuple(), newExplicitTuple);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FilesConfig getFiles() {
		return (FilesConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Files(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFiles(FilesConfig newFiles, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Files(), newFiles, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFiles(FilesConfig newFiles) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Files(), newFiles);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionGroupsConfig getFunctionGroups() {
		return (FunctionGroupsConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_FunctionGroups(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFunctionGroups(FunctionGroupsConfig newFunctionGroups, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_FunctionGroups(), newFunctionGroups, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFunctionGroups(FunctionGroupsConfig newFunctionGroups) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_FunctionGroups(), newFunctionGroups);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionsConfig getFunctions() {
		return (FunctionsConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Functions(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFunctions(FunctionsConfig newFunctions, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Functions(), newFunctions, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFunctions(FunctionsConfig newFunctions) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Functions(), newFunctions);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GetPropertyConfig getGetProperty() {
		return (GetPropertyConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_GetProperty(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGetProperty(GetPropertyConfig newGetProperty, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_GetProperty(), newGetProperty, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGetProperty(GetPropertyConfig newGetProperty) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_GetProperty(), newGetProperty);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getHotDeploy() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_HotDeploy(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHotDeploy(OverrideConfig newHotDeploy, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_HotDeploy(), newHotDeploy, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHotDeploy(OverrideConfig newHotDeploy) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_HotDeploy(), newHotDeploy);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HttpConfig getHttp() {
		return (HttpConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Http(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHttp(HttpConfig newHttp, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Http(), newHttp, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHttp(HttpConfig newHttp) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Http(), newHttp);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityOverrideConfig getIdentityPassword() {
		return (SecurityOverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_IdentityPassword(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIdentityPassword(SecurityOverrideConfig newIdentityPassword, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_IdentityPassword(), newIdentityPassword, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIdentityPassword(SecurityOverrideConfig newIdentityPassword) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_IdentityPassword(), newIdentityPassword);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getInactivityTimeout() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_InactivityTimeout(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInactivityTimeout(OverrideConfig newInactivityTimeout, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_InactivityTimeout(), newInactivityTimeout, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInactivityTimeout(OverrideConfig newInactivityTimeout) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_InactivityTimeout(), newInactivityTimeout);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IndexConfig getIndex() {
		return (IndexConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Index(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIndex(IndexConfig newIndex, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Index(), newIndex, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIndex(IndexConfig newIndex) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Index(), newIndex);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IndexesConfig getIndexes() {
		return (IndexesConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Indexes(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIndexes(IndexesConfig newIndexes, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Indexes(), newIndexes, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIndexes(IndexesConfig newIndexes) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Indexes(), newIndexes);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InferenceAgentClassConfig getInferenceAgentClass() {
		return (InferenceAgentClassConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_InferenceAgentClass(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInferenceAgentClass(InferenceAgentClassConfig newInferenceAgentClass, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_InferenceAgentClass(), newInferenceAgentClass, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInferenceAgentClass(InferenceAgentClassConfig newInferenceAgentClass) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_InferenceAgentClass(), newInferenceAgentClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InferenceEngineConfig getInferenceEngine() {
		return (InferenceEngineConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_InferenceEngine(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInferenceEngine(InferenceEngineConfig newInferenceEngine, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_InferenceEngine(), newInferenceEngine, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInferenceEngine(InferenceEngineConfig newInferenceEngine) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_InferenceEngine(), newInferenceEngine);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getInitialSize() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_InitialSize(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInitialSize(OverrideConfig newInitialSize, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_InitialSize(), newInitialSize, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitialSize(OverrideConfig newInitialSize) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_InitialSize(), newInitialSize);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JobManagerConfig getJobManager() {
		return (JobManagerConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_JobManager(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJobManager(JobManagerConfig newJobManager, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_JobManager(), newJobManager, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJobManager(JobManagerConfig newJobManager) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_JobManager(), newJobManager);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getJobPoolQueueSize() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_JobPoolQueueSize(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJobPoolQueueSize(OverrideConfig newJobPoolQueueSize, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_JobPoolQueueSize(), newJobPoolQueueSize, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJobPoolQueueSize(OverrideConfig newJobPoolQueueSize) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_JobPoolQueueSize(), newJobPoolQueueSize);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getJobPoolThreadCount() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_JobPoolThreadCount(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJobPoolThreadCount(OverrideConfig newJobPoolThreadCount, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_JobPoolThreadCount(), newJobPoolThreadCount, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJobPoolThreadCount(OverrideConfig newJobPoolThreadCount) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_JobPoolThreadCount(), newJobPoolThreadCount);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getKey() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Key(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetKey(OverrideConfig newKey, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Key(), newKey, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKey(OverrideConfig newKey) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Key(), newKey);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getKeyManagerAlgorithm() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_KeyManagerAlgorithm(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetKeyManagerAlgorithm(OverrideConfig newKeyManagerAlgorithm, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_KeyManagerAlgorithm(), newKeyManagerAlgorithm, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKeyManagerAlgorithm(OverrideConfig newKeyManagerAlgorithm) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_KeyManagerAlgorithm(), newKeyManagerAlgorithm);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LineLayoutConfig getLineLayout() {
		return (LineLayoutConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_LineLayout(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLineLayout(LineLayoutConfig newLineLayout, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_LineLayout(), newLineLayout, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLineLayout(LineLayoutConfig newLineLayout) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_LineLayout(), newLineLayout);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getListenUrl() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_ListenUrl(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetListenUrl(OverrideConfig newListenUrl, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_ListenUrl(), newListenUrl, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setListenUrl(OverrideConfig newListenUrl) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_ListenUrl(), newListenUrl);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LoadConfig getLoad() {
		return (LoadConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Load(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLoad(LoadConfig newLoad, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Load(), newLoad, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLoad(LoadConfig newLoad) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Load(), newLoad);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LoadBalancerConfigsConfig getLoadBalancerConfigs() {
		return (LoadBalancerConfigsConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_LoadBalancerConfigs(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLoadBalancerConfigs(LoadBalancerConfigsConfig newLoadBalancerConfigs, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_LoadBalancerConfigs(), newLoadBalancerConfigs, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLoadBalancerConfigs(LoadBalancerConfigsConfig newLoadBalancerConfigs) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_LoadBalancerConfigs(), newLoadBalancerConfigs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalCacheConfig getLocalCache() {
		return (LocalCacheConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_LocalCache(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLocalCache(LocalCacheConfig newLocalCache, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_LocalCache(), newLocalCache, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocalCache(LocalCacheConfig newLocalCache) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_LocalCache(), newLocalCache);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getLockTimeout() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_LockTimeout(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLockTimeout(OverrideConfig newLockTimeout, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_LockTimeout(), newLockTimeout, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLockTimeout(OverrideConfig newLockTimeout) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_LockTimeout(), newLockTimeout);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LogConfigConfig getLogConfig() {
		return (LogConfigConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_LogConfig(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLogConfig(LogConfigConfig newLogConfig, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_LogConfig(), newLogConfig, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLogConfig(LogConfigConfig newLogConfig) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_LogConfig(), newLogConfig);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LogConfigsConfig getLogConfigs() {
		return (LogConfigsConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_LogConfigs(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLogConfigs(LogConfigsConfig newLogConfigs, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_LogConfigs(), newLogConfigs, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLogConfigs(LogConfigsConfig newLogConfigs) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_LogConfigs(), newLogConfigs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getMaxActive() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_MaxActive(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMaxActive(OverrideConfig newMaxActive, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_MaxActive(), newMaxActive, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxActive(OverrideConfig newMaxActive) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_MaxActive(), newMaxActive);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getMaxNumber() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_MaxNumber(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMaxNumber(OverrideConfig newMaxNumber, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_MaxNumber(), newMaxNumber, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxNumber(OverrideConfig newMaxNumber) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_MaxNumber(), newMaxNumber);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getMaxProcessors() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_MaxProcessors(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMaxProcessors(OverrideConfig newMaxProcessors, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_MaxProcessors(), newMaxProcessors, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxProcessors(OverrideConfig newMaxProcessors) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_MaxProcessors(), newMaxProcessors);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getMaxSize() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_MaxSize(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMaxSize(OverrideConfig newMaxSize, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_MaxSize(), newMaxSize, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxSize(OverrideConfig newMaxSize) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_MaxSize(), newMaxSize);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getMaxTime() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_MaxTime(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMaxTime(OverrideConfig newMaxTime, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_MaxTime(), newMaxTime, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxTime(OverrideConfig newMaxTime) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_MaxTime(), newMaxTime);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MemoryManagerConfig getMemoryManager() {
		return (MemoryManagerConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_MemoryManager(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMemoryManager(MemoryManagerConfig newMemoryManager, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_MemoryManager(), newMemoryManager, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMemoryManager(MemoryManagerConfig newMemoryManager) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_MemoryManager(), newMemoryManager);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMessageEncoding() {
		return (String)getMixed().get(CddPackage.eINSTANCE.getCddRoot_MessageEncoding(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMessageEncoding(String newMessageEncoding) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_MessageEncoding(), newMessageEncoding);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getMinSize() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_MinSize(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMinSize(OverrideConfig newMinSize, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_MinSize(), newMinSize, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinSize(OverrideConfig newMinSize) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_MinSize(), newMinSize);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MmActionConfig getMmAction() {
		return (MmActionConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_MmAction(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMmAction(MmActionConfig newMmAction, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_MmAction(), newMmAction, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMmAction(MmActionConfig newMmAction) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_MmAction(), newMmAction);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MmActionConfigConfig getMmActionConfig() {
		return (MmActionConfigConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_MmActionConfig(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMmActionConfig(MmActionConfigConfig newMmActionConfig, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_MmActionConfig(), newMmActionConfig, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMmActionConfig(MmActionConfigConfig newMmActionConfig) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_MmActionConfig(), newMmActionConfig);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MmActionConfigSetConfig getMmActionConfigSet() {
		return (MmActionConfigSetConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_MmActionConfigSet(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMmActionConfigSet(MmActionConfigSetConfig newMmActionConfigSet, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_MmActionConfigSet(), newMmActionConfigSet, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMmActionConfigSet(MmActionConfigSetConfig newMmActionConfigSet) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_MmActionConfigSet(), newMmActionConfigSet);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MmAgentClassConfig getMmAgentClass() {
		return (MmAgentClassConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_MmAgentClass(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMmAgentClass(MmAgentClassConfig newMmAgentClass, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_MmAgentClass(), newMmAgentClass, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMmAgentClass(MmAgentClassConfig newMmAgentClass) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_MmAgentClass(), newMmAgentClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MmAlertConfig getMmAlert() {
		return (MmAlertConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_MmAlert(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMmAlert(MmAlertConfig newMmAlert, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_MmAlert(), newMmAlert, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMmAlert(MmAlertConfig newMmAlert) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_MmAlert(), newMmAlert);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MmExecuteCommandConfig getMmExecuteCommand() {
		return (MmExecuteCommandConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_MmExecuteCommand(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMmExecuteCommand(MmExecuteCommandConfig newMmExecuteCommand, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_MmExecuteCommand(), newMmExecuteCommand, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMmExecuteCommand(MmExecuteCommandConfig newMmExecuteCommand) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_MmExecuteCommand(), newMmExecuteCommand);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MmHealthLevelConfig getMmHealthLevel() {
		return (MmHealthLevelConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_MmHealthLevel(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMmHealthLevel(MmHealthLevelConfig newMmHealthLevel, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_MmHealthLevel(), newMmHealthLevel, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMmHealthLevel(MmHealthLevelConfig newMmHealthLevel) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_MmHealthLevel(), newMmHealthLevel);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MmSendEmailConfig getMmSendEmail() {
		return (MmSendEmailConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_MmSendEmail(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMmSendEmail(MmSendEmailConfig newMmSendEmail, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_MmSendEmail(), newMmSendEmail, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMmSendEmail(MmSendEmailConfig newMmSendEmail) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_MmSendEmail(), newMmSendEmail);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MmTriggerConditionConfig getMmTriggerCondition() {
		return (MmTriggerConditionConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_MmTriggerCondition(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMmTriggerCondition(MmTriggerConditionConfig newMmTriggerCondition, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_MmTriggerCondition(), newMmTriggerCondition, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMmTriggerCondition(MmTriggerConditionConfig newMmTriggerCondition) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_MmTriggerCondition(), newMmTriggerCondition);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainObjectModeConfig getMode() {
		return (DomainObjectModeConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Mode(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMode(DomainObjectModeConfig newMode) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Mode(), newMode);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getName() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Name(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetName(OverrideConfig newName, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Name(), newName, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(OverrideConfig newName) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Name(), newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationConfig getNotification() {
		return (NotificationConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Notification(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNotification(NotificationConfig newNotification, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Notification(), newNotification, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNotification(NotificationConfig newNotification) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Notification(), newNotification);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ObjectManagementConfig getObjectManagement() {
		return (ObjectManagementConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_ObjectManagement(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetObjectManagement(ObjectManagementConfig newObjectManagement, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_ObjectManagement(), newObjectManagement, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setObjectManagement(ObjectManagementConfig newObjectManagement) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_ObjectManagement(), newObjectManagement);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ObjectTableConfig getObjectTable() {
		return (ObjectTableConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_ObjectTable(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetObjectTable(ObjectTableConfig newObjectTable, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_ObjectTable(), newObjectTable, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setObjectTable(ObjectTableConfig newObjectTable) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_ObjectTable(), newObjectTable);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LoadBalancerPairConfigConfig getPairConfig() {
		return (LoadBalancerPairConfigConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_PairConfig(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPairConfig(LoadBalancerPairConfigConfig newPairConfig, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_PairConfig(), newPairConfig, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPairConfig(LoadBalancerPairConfigConfig newPairConfig) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_PairConfig(), newPairConfig);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LoadBalancerPairConfigsConfig getPairConfigs() {
		return (LoadBalancerPairConfigsConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_PairConfigs(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPairConfigs(LoadBalancerPairConfigsConfig newPairConfigs, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_PairConfigs(), newPairConfigs, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPairConfigs(LoadBalancerPairConfigsConfig newPairConfigs) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_PairConfigs(), newPairConfigs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getParallelRecovery() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_ParallelRecovery(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParallelRecovery(OverrideConfig newParallelRecovery, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_ParallelRecovery(), newParallelRecovery, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParallelRecovery(OverrideConfig newParallelRecovery) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_ParallelRecovery(), newParallelRecovery);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getPersistenceOption() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_PersistenceOption(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPersistenceOption(OverrideConfig newPersistenceOption, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_PersistenceOption(), newPersistenceOption, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPersistenceOption(OverrideConfig newPersistenceOption) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_PersistenceOption(), newPersistenceOption);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getPersistencePolicy() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_PersistencePolicy(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPersistencePolicy(OverrideConfig newPersistencePolicy, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_PersistencePolicy(), newPersistencePolicy, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPersistencePolicy(OverrideConfig newPersistencePolicy) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_PersistencePolicy(), newPersistencePolicy);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityOverrideConfig getPolicyFile() {
		return (SecurityOverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_PolicyFile(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPolicyFile(SecurityOverrideConfig newPolicyFile, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_PolicyFile(), newPolicyFile, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPolicyFile(SecurityOverrideConfig newPolicyFile) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_PolicyFile(), newPolicyFile);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getPort() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Port(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPort(OverrideConfig newPort, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Port(), newPort, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPort(OverrideConfig newPort) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Port(), newPort);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getPreLoadCaches() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_PreLoadCaches(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPreLoadCaches(OverrideConfig newPreLoadCaches, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_PreLoadCaches(), newPreLoadCaches, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreLoadCaches(OverrideConfig newPreLoadCaches) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_PreLoadCaches(), newPreLoadCaches);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getPreLoadEnabled() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_PreLoadEnabled(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPreLoadEnabled(OverrideConfig newPreLoadEnabled, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_PreLoadEnabled(), newPreLoadEnabled, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreLoadEnabled(OverrideConfig newPreLoadEnabled) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_PreLoadEnabled(), newPreLoadEnabled);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getPreLoadFetchSize() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_PreLoadFetchSize(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPreLoadFetchSize(OverrideConfig newPreLoadFetchSize, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_PreLoadFetchSize(), newPreLoadFetchSize, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreLoadFetchSize(OverrideConfig newPreLoadFetchSize) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_PreLoadFetchSize(), newPreLoadFetchSize);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getPreLoadHandles() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_PreLoadHandles(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPreLoadHandles(OverrideConfig newPreLoadHandles, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_PreLoadHandles(), newPreLoadHandles, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreLoadHandles(OverrideConfig newPreLoadHandles) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_PreLoadHandles(), newPreLoadHandles);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPreProcessor() {
		return (String)getMixed().get(CddPackage.eINSTANCE.getCddRoot_PreProcessor(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreProcessor(String newPreProcessor) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_PreProcessor(), newPreProcessor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectionConfig getPrimaryConnection() {
		return (ConnectionConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_PrimaryConnection(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPrimaryConnection(ConnectionConfig newPrimaryConnection, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_PrimaryConnection(), newPrimaryConnection, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPrimaryConnection(ConnectionConfig newPrimaryConnection) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_PrimaryConnection(), newPrimaryConnection);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getPriority() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Priority(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPriority(OverrideConfig newPriority, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Priority(), newPriority, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPriority(OverrideConfig newPriority) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Priority(), newPriority);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessConfig getProcess() {
		return (ProcessConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Process(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProcess(ProcessConfig newProcess, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Process(), newProcess, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProcess(ProcessConfig newProcess) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Process(), newProcess);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessAgentClassConfig getProcessAgentClass() {
		return (ProcessAgentClassConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_ProcessAgentClass(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProcessAgentClass(ProcessAgentClassConfig newProcessAgentClass, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_ProcessAgentClass(), newProcessAgentClass, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProcessAgentClass(ProcessAgentClassConfig newProcessAgentClass) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_ProcessAgentClass(), newProcessAgentClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessEngineConfig getProcessEngine() {
		return (ProcessEngineConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_ProcessEngine(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProcessEngine(ProcessEngineConfig newProcessEngine, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_ProcessEngine(), newProcessEngine, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProcessEngine(ProcessEngineConfig newProcessEngine) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_ProcessEngine(), newProcessEngine);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessesConfig getProcesses() {
		return (ProcessesConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Processes(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProcesses(ProcessesConfig newProcesses, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Processes(), newProcesses, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProcesses(ProcessesConfig newProcesses) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Processes(), newProcesses);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessGroupsConfig getProcessGroups() {
		return (ProcessGroupsConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_ProcessGroups(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProcessGroups(ProcessGroupsConfig newProcessGroups, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_ProcessGroups(), newProcessGroups, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProcessGroups(ProcessGroupsConfig newProcessGroups) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_ProcessGroups(), newProcessGroups);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessingUnitConfig getProcessingUnit() {
		return (ProcessingUnitConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_ProcessingUnit(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProcessingUnit(ProcessingUnitConfig newProcessingUnit, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_ProcessingUnit(), newProcessingUnit, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProcessingUnit(ProcessingUnitConfig newProcessingUnit) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_ProcessingUnit(), newProcessingUnit);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessingUnitsConfig getProcessingUnits() {
		return (ProcessingUnitsConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_ProcessingUnits(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProcessingUnits(ProcessingUnitsConfig newProcessingUnits, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_ProcessingUnits(), newProcessingUnits, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProcessingUnits(ProcessingUnitsConfig newProcessingUnits) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_ProcessingUnits(), newProcessingUnits);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectionConfig getProjection() {
		return (ProjectionConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Projection(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProjection(ProjectionConfig newProjection, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Projection(), newProjection, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProjection(ProjectionConfig newProjection) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Projection(), newProjection);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyConfig getProperty() {
		return (PropertyConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Property(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProperty(PropertyConfig newProperty, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Property(), newProperty, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProperty(PropertyConfig newProperty) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Property(), newProperty);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getPropertyCacheSize() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_PropertyCacheSize(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPropertyCacheSize(OverrideConfig newPropertyCacheSize, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_PropertyCacheSize(), newPropertyCacheSize, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropertyCacheSize(OverrideConfig newPropertyCacheSize) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_PropertyCacheSize(), newPropertyCacheSize);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getPropertyCheckInterval() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_PropertyCheckInterval(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPropertyCheckInterval(OverrideConfig newPropertyCheckInterval, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_PropertyCheckInterval(), newPropertyCheckInterval, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropertyCheckInterval(OverrideConfig newPropertyCheckInterval) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_PropertyCheckInterval(), newPropertyCheckInterval);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyGroupConfig getPropertyGroup() {
		return (PropertyGroupConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_PropertyGroup(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPropertyGroup(PropertyGroupConfig newPropertyGroup, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_PropertyGroup(), newPropertyGroup, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropertyGroup(PropertyGroupConfig newPropertyGroup) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_PropertyGroup(), newPropertyGroup);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getProtocol() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Protocol(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProtocol(OverrideConfig newProtocol, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Protocol(), newProtocol, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProtocol(OverrideConfig newProtocol) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Protocol(), newProtocol);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProtocolsConfig getProtocols() {
		return (ProtocolsConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Protocols(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProtocols(ProtocolsConfig newProtocols, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Protocols(), newProtocols, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProtocols(ProtocolsConfig newProtocols) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Protocols(), newProtocols);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getProtocolTimeout() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_ProtocolTimeout(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProtocolTimeout(OverrideConfig newProtocolTimeout, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_ProtocolTimeout(), newProtocolTimeout, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProtocolTimeout(OverrideConfig newProtocolTimeout) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_ProtocolTimeout(), newProtocolTimeout);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProviderConfig getProvider() {
		return (ProviderConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Provider(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProvider(ProviderConfig newProvider, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Provider(), newProvider, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProvider(ProviderConfig newProvider) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Provider(), newProvider);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public QueryAgentClassConfig getQueryAgentClass() {
		return (QueryAgentClassConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_QueryAgentClass(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetQueryAgentClass(QueryAgentClassConfig newQueryAgentClass, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_QueryAgentClass(), newQueryAgentClass, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQueryAgentClass(QueryAgentClassConfig newQueryAgentClass) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_QueryAgentClass(), newQueryAgentClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getQueueSize() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_QueueSize(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetQueueSize(OverrideConfig newQueueSize, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_QueueSize(), newQueueSize, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQueueSize(OverrideConfig newQueueSize) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_QueueSize(), newQueueSize);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getReadTimeout() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_ReadTimeout(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReadTimeout(OverrideConfig newReadTimeout, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_ReadTimeout(), newReadTimeout, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReadTimeout(OverrideConfig newReadTimeout) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_ReadTimeout(), newReadTimeout);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getReceiver() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Receiver(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReceiver(OverrideConfig newReceiver, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Receiver(), newReceiver, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReceiver(OverrideConfig newReceiver) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Receiver(), newReceiver);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getRemoteListenUrl() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_RemoteListenUrl(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRemoteListenUrl(OverrideConfig newRemoteListenUrl, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_RemoteListenUrl(), newRemoteListenUrl, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRemoteListenUrl(OverrideConfig newRemoteListenUrl) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_RemoteListenUrl(), newRemoteListenUrl);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityRequester getRequester() {
		return (SecurityRequester)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Requester(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRequester(SecurityRequester newRequester, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Requester(), newRequester, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRequester(SecurityRequester newRequester) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Requester(), newRequester);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getRetryCount() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_RetryCount(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRetryCount(OverrideConfig newRetryCount, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_RetryCount(), newRetryCount, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRetryCount(OverrideConfig newRetryCount) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_RetryCount(), newRetryCount);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getReverseReferences() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_ReverseReferences(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReverseReferences(OverrideConfig newReverseReferences, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_ReverseReferences(), newReverseReferences, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReverseReferences(OverrideConfig newReverseReferences) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_ReverseReferences(), newReverseReferences);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RevisionConfig getRevision() {
		return (RevisionConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Revision(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRevision(RevisionConfig newRevision, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Revision(), newRevision, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRevision(RevisionConfig newRevision) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Revision(), newRevision);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getRole() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Role(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRole(OverrideConfig newRole, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Role(), newRole, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRole(OverrideConfig newRole) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Role(), newRole);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getRoles() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Roles(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRoles(OverrideConfig newRoles, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Roles(), newRoles, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRoles(OverrideConfig newRoles) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Roles(), newRoles);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getRouter() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Router(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRouter(OverrideConfig newRouter, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Router(), newRouter, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRouter(OverrideConfig newRouter) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Router(), newRouter);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleConfig getRule() {
		return (RuleConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Rule(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRule(RuleConfig newRule, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Rule(), newRule, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRule(RuleConfig newRule) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Rule(), newRule);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleConfigConfig getRuleConfig() {
		return (RuleConfigConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_RuleConfig(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRuleConfig(RuleConfigConfig newRuleConfig, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_RuleConfig(), newRuleConfig, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRuleConfig(RuleConfigConfig newRuleConfig) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_RuleConfig(), newRuleConfig);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RulesConfig getRules() {
		return (RulesConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Rules(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRules(RulesConfig newRules, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Rules(), newRules, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRules(RulesConfig newRules) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Rules(), newRules);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RulesetsConfig getRulesets() {
		return (RulesetsConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Rulesets(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRulesets(RulesetsConfig newRulesets, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Rulesets(), newRulesets, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRulesets(RulesetsConfig newRulesets) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Rulesets(), newRulesets);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectionConfig getSecondaryConnection() {
		return (ConnectionConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_SecondaryConnection(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSecondaryConnection(ConnectionConfig newSecondaryConnection, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_SecondaryConnection(), newSecondaryConnection, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSecondaryConnection(ConnectionConfig newSecondaryConnection) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_SecondaryConnection(), newSecondaryConnection);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityConfig getSecurityConfig() {
		return (SecurityConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_SecurityConfig(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSecurityConfig(SecurityConfig newSecurityConfig, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_SecurityConfig(), newSecurityConfig, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSecurityConfig(SecurityConfig newSecurityConfig) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_SecurityConfig(), newSecurityConfig);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getService() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Service(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetService(OverrideConfig newService, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Service(), newService, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setService(OverrideConfig newService) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Service(), newService);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SetPropertyConfig getSetProperty() {
		return (SetPropertyConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_SetProperty(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSetProperty(SetPropertyConfig newSetProperty, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_SetProperty(), newSetProperty, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSetProperty(SetPropertyConfig newSetProperty) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_SetProperty(), newSetProperty);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getSharedAll() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_SharedAll(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSharedAll(OverrideConfig newSharedAll, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_SharedAll(), newSharedAll, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSharedAll(OverrideConfig newSharedAll) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_SharedAll(), newSharedAll);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SharedQueueConfig getSharedQueue() {
		return (SharedQueueConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_SharedQueue(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSharedQueue(SharedQueueConfig newSharedQueue, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_SharedQueue(), newSharedQueue, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSharedQueue(SharedQueueConfig newSharedQueue) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_SharedQueue(), newSharedQueue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getShoutdownWait() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_ShoutdownWait(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetShoutdownWait(OverrideConfig newShoutdownWait, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_ShoutdownWait(), newShoutdownWait, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShoutdownWait(OverrideConfig newShoutdownWait) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_ShoutdownWait(), newShoutdownWait);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionsConfig getShutdown() {
		return (FunctionsConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Shutdown(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetShutdown(FunctionsConfig newShutdown, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Shutdown(), newShutdown, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShutdown(FunctionsConfig newShutdown) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Shutdown(), newShutdown);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getSize() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Size(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSize(OverrideConfig newSize, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Size(), newSize, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSize(OverrideConfig newSize) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Size(), newSize);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getSkipRecovery() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_SkipRecovery(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSkipRecovery(OverrideConfig newSkipRecovery, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_SkipRecovery(), newSkipRecovery, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSkipRecovery(OverrideConfig newSkipRecovery) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_SkipRecovery(), newSkipRecovery);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getSocketOutputBufferSize() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_SocketOutputBufferSize(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSocketOutputBufferSize(OverrideConfig newSocketOutputBufferSize, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_SocketOutputBufferSize(), newSocketOutputBufferSize, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSocketOutputBufferSize(OverrideConfig newSocketOutputBufferSize) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_SocketOutputBufferSize(), newSocketOutputBufferSize);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SslConfig getSsl() {
		return (SslConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Ssl(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSsl(SslConfig newSsl, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Ssl(), newSsl, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSsl(SslConfig newSsl) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Ssl(), newSsl);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getStaleConnectionCheck() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_StaleConnectionCheck(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStaleConnectionCheck(OverrideConfig newStaleConnectionCheck, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_StaleConnectionCheck(), newStaleConnectionCheck, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStaleConnectionCheck(OverrideConfig newStaleConnectionCheck) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_StaleConnectionCheck(), newStaleConnectionCheck);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionsConfig getStartup() {
		return (FunctionsConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Startup(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStartup(FunctionsConfig newStartup, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Startup(), newStartup, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartup(FunctionsConfig newStartup) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Startup(), newStartup);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getStrategy() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Strategy(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStrategy(OverrideConfig newStrategy, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Strategy(), newStrategy, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStrategy(OverrideConfig newStrategy) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Strategy(), newStrategy);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getSubject() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Subject(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSubject(OverrideConfig newSubject, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Subject(), newSubject, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubject(OverrideConfig newSubject) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Subject(), newSubject);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getSubscribe() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Subscribe(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSubscribe(OverrideConfig newSubscribe, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Subscribe(), newSubscribe, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubscribe(OverrideConfig newSubscribe) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Subscribe(), newSubscribe);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getSysErrRedirect() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_SysErrRedirect(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSysErrRedirect(OverrideConfig newSysErrRedirect, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_SysErrRedirect(), newSysErrRedirect, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSysErrRedirect(OverrideConfig newSysErrRedirect) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_SysErrRedirect(), newSysErrRedirect);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getSysOutRedirect() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_SysOutRedirect(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSysOutRedirect(OverrideConfig newSysOutRedirect, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_SysOutRedirect(), newSysOutRedirect, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSysOutRedirect(OverrideConfig newSysOutRedirect) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_SysOutRedirect(), newSysOutRedirect);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getTableName() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_TableName(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTableName(OverrideConfig newTableName, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_TableName(), newTableName, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTableName(OverrideConfig newTableName) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_TableName(), newTableName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getTcpNoDelay() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_TcpNoDelay(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTcpNoDelay(OverrideConfig newTcpNoDelay, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_TcpNoDelay(), newTcpNoDelay, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTcpNoDelay(OverrideConfig newTcpNoDelay) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_TcpNoDelay(), newTcpNoDelay);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TerminalConfig getTerminal() {
		return (TerminalConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Terminal(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTerminal(TerminalConfig newTerminal, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Terminal(), newTerminal, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTerminal(TerminalConfig newTerminal) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Terminal(), newTerminal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getThreadAffinityRuleFunction() {
		return (String)getMixed().get(CddPackage.eINSTANCE.getCddRoot_ThreadAffinityRuleFunction(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setThreadAffinityRuleFunction(String newThreadAffinityRuleFunction) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_ThreadAffinityRuleFunction(), newThreadAffinityRuleFunction);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getThreadCount() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_ThreadCount(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetThreadCount(OverrideConfig newThreadCount, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_ThreadCount(), newThreadCount, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setThreadCount(OverrideConfig newThreadCount) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_ThreadCount(), newThreadCount);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ThreadingModelConfig getThreadingModel() {
		return (ThreadingModelConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_ThreadingModel(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setThreadingModel(ThreadingModelConfig newThreadingModel) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_ThreadingModel(), newThreadingModel);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityOverrideConfig getTokenFile() {
		return (SecurityOverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_TokenFile(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTokenFile(SecurityOverrideConfig newTokenFile, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_TokenFile(), newTokenFile, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTokenFile(SecurityOverrideConfig newTokenFile) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_TokenFile(), newTokenFile);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getTrustManagerAlgorithm() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_TrustManagerAlgorithm(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTrustManagerAlgorithm(OverrideConfig newTrustManagerAlgorithm, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_TrustManagerAlgorithm(), newTrustManagerAlgorithm, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTrustManagerAlgorithm(OverrideConfig newTrustManagerAlgorithm) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_TrustManagerAlgorithm(), newTrustManagerAlgorithm);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getTtl() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Ttl(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTtl(OverrideConfig newTtl, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Ttl(), newTtl, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTtl(OverrideConfig newTtl) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Ttl(), newTtl);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getType() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Type(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetType(OverrideConfig newType, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Type(), newType, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(OverrideConfig newType) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Type(), newType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUri() {
		return (String)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Uri(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUri(String newUri) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Uri(), newUri);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityOverrideConfig getUserName() {
		return (SecurityOverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_UserName(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUserName(SecurityOverrideConfig newUserName, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_UserName(), newUserName, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUserName(SecurityOverrideConfig newUserName) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_UserName(), newUserName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityOverrideConfig getUserPassword() {
		return (SecurityOverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_UserPassword(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUserPassword(SecurityOverrideConfig newUserPassword, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_UserPassword(), newUserPassword, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUserPassword(SecurityOverrideConfig newUserPassword) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_UserPassword(), newUserPassword);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersion() {
		return (String)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Version(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(String newVersion) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Version(), newVersion);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getWaitTimeout() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_WaitTimeout(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetWaitTimeout(OverrideConfig newWaitTimeout, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_WaitTimeout(), newWaitTimeout, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWaitTimeout(OverrideConfig newWaitTimeout) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_WaitTimeout(), newWaitTimeout);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getWorkers() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Workers(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetWorkers(OverrideConfig newWorkers, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Workers(), newWorkers, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWorkers(OverrideConfig newWorkers) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Workers(), newWorkers);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getWorkerthreadsCount() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_WorkerthreadsCount(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetWorkerthreadsCount(OverrideConfig newWorkerthreadsCount, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_WorkerthreadsCount(), newWorkerthreadsCount, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWorkerthreadsCount(OverrideConfig newWorkerthreadsCount) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_WorkerthreadsCount(), newWorkerthreadsCount);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getWriteTimeout() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_WriteTimeout(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetWriteTimeout(OverrideConfig newWriteTimeout, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_WriteTimeout(), newWriteTimeout, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWriteTimeout(OverrideConfig newWriteTimeout) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_WriteTimeout(), newWriteTimeout);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntityConfig getEntity() {
		return (EntityConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Entity(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEntity(EntityConfig newEntity, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Entity(), newEntity, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntity(EntityConfig newEntity) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Entity(), newEntity);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getFilter() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Filter(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFilter(OverrideConfig newFilter, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Filter(), newFilter, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFilter(OverrideConfig newFilter) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Filter(), newFilter);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntitySetConfig getEntitySet() {
		return (EntitySetConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_EntitySet(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEntitySet(EntitySetConfig newEntitySet, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_EntitySet(), newEntitySet, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntitySet(EntitySetConfig newEntitySet) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_EntitySet(), newEntitySet);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getEnableTableTrimming() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_EnableTableTrimming(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEnableTableTrimming(OverrideConfig newEnableTableTrimming, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_EnableTableTrimming(), newEnableTableTrimming, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnableTableTrimming(OverrideConfig newEnableTableTrimming) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_EnableTableTrimming(), newEnableTableTrimming);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getTrimmingField() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_TrimmingField(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTrimmingField(OverrideConfig newTrimmingField, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_TrimmingField(), newTrimmingField, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTrimmingField(OverrideConfig newTrimmingField) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_TrimmingField(), newTrimmingField);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getTrimmingRule() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_TrimmingRule(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTrimmingRule(OverrideConfig newTrimmingRule, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_TrimmingRule(), newTrimmingRule, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTrimmingRule(OverrideConfig newTrimmingRule) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_TrimmingRule(), newTrimmingRule);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getGenerateLVFiles() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_GenerateLVFiles(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGenerateLVFiles(OverrideConfig newGenerateLVFiles, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_GenerateLVFiles(), newGenerateLVFiles, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenerateLVFiles(OverrideConfig newGenerateLVFiles) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_GenerateLVFiles(), newGenerateLVFiles);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getOutputPath() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_OutputPath(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOutputPath(OverrideConfig newOutputPath, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_OutputPath(), newOutputPath, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutputPath(OverrideConfig newOutputPath) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_OutputPath(), newOutputPath);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LDMConnectionConfig getLdmConnection() {
		return (LDMConnectionConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_LdmConnection(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLdmConnection(LDMConnectionConfig newLdmConnection, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_LdmConnection(), newLdmConnection, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLdmConnection(LDMConnectionConfig newLdmConnection) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_LdmConnection(), newLdmConnection);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getLdmUrl() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_LdmUrl(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLdmUrl(OverrideConfig newLdmUrl, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_LdmUrl(), newLdmUrl, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLdmUrl(OverrideConfig newLdmUrl) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_LdmUrl(), newLdmUrl);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PublisherConfig getPublisher() {
		return (PublisherConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_Publisher(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPublisher(PublisherConfig newPublisher, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_Publisher(), newPublisher, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPublisher(PublisherConfig newPublisher) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_Publisher(), newPublisher);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getPublisherQueueSize() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_PublisherQueueSize(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPublisherQueueSize(OverrideConfig newPublisherQueueSize, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_PublisherQueueSize(), newPublisherQueueSize, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPublisherQueueSize(OverrideConfig newPublisherQueueSize) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_PublisherQueueSize(), newPublisherQueueSize);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getPublisherThreadCount() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_PublisherThreadCount(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPublisherThreadCount(OverrideConfig newPublisherThreadCount, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_PublisherThreadCount(), newPublisherThreadCount, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPublisherThreadCount(OverrideConfig newPublisherThreadCount) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_PublisherThreadCount(), newPublisherThreadCount);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LiveViewAgentClassConfig getLiveviewAgentClass() {
		return (LiveViewAgentClassConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_LiveviewAgentClass(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLiveviewAgentClass(LiveViewAgentClassConfig newLiveviewAgentClass, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_LiveviewAgentClass(), newLiveviewAgentClass, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLiveviewAgentClass(LiveViewAgentClassConfig newLiveviewAgentClass) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_LiveviewAgentClass(), newLiveviewAgentClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompositeIndexesConfig getCompositeIndexes() {
		return (CompositeIndexesConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_CompositeIndexes(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCompositeIndexes(CompositeIndexesConfig newCompositeIndexes, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_CompositeIndexes(), newCompositeIndexes, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompositeIndexes(CompositeIndexesConfig newCompositeIndexes) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_CompositeIndexes(), newCompositeIndexes);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompositeIndexConfig getCompositeIndex() {
		return (CompositeIndexConfig)getMixed().get(CddPackage.eINSTANCE.getCddRoot_CompositeIndex(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCompositeIndex(CompositeIndexConfig newCompositeIndex, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getCddRoot_CompositeIndex(), newCompositeIndex, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompositeIndex(CompositeIndexConfig newCompositeIndex) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getCddRoot_CompositeIndex(), newCompositeIndex);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.CDD_ROOT__MIXED:
				return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
			case CddPackage.CDD_ROOT__XMLNS_PREFIX_MAP:
				return ((InternalEList<?>)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
			case CddPackage.CDD_ROOT__XSI_SCHEMA_LOCATION:
				return ((InternalEList<?>)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
			case CddPackage.CDD_ROOT__ACCEPT_COUNT:
				return basicSetAcceptCount(null, msgs);
			case CddPackage.CDD_ROOT__ADDRESS:
				return basicSetAddress(null, msgs);
			case CddPackage.CDD_ROOT__ADHOC_CONFIG:
				return basicSetAdhocConfig(null, msgs);
			case CddPackage.CDD_ROOT__ADHOC_CONFIGS:
				return basicSetAdhocConfigs(null, msgs);
			case CddPackage.CDD_ROOT__AGENT:
				return basicSetAgent(null, msgs);
			case CddPackage.CDD_ROOT__AGENT_CLASSES:
				return basicSetAgentClasses(null, msgs);
			case CddPackage.CDD_ROOT__AGENTS:
				return basicSetAgents(null, msgs);
			case CddPackage.CDD_ROOT__ALERT_CONFIG:
				return basicSetAlertConfig(null, msgs);
			case CddPackage.CDD_ROOT__ALERT_CONFIG_SET:
				return basicSetAlertConfigSet(null, msgs);
			case CddPackage.CDD_ROOT__APPEND:
				return basicSetAppend(null, msgs);
			case CddPackage.CDD_ROOT__ARG:
				return basicSetArg(null, msgs);
			case CddPackage.CDD_ROOT__AUTHOR:
				return basicSetAuthor(null, msgs);
			case CddPackage.CDD_ROOT__BACKING_STORE:
				return basicSetBackingStore(null, msgs);
			case CddPackage.CDD_ROOT__BACKUP_COPIES:
				return basicSetBackupCopies(null, msgs);
			case CddPackage.CDD_ROOT__BUSINESSWORKS:
				return basicSetBusinessworks(null, msgs);
			case CddPackage.CDD_ROOT__CACHE:
				return basicSetCache(null, msgs);
			case CddPackage.CDD_ROOT__CACHE_AGENT_CLASS:
				return basicSetCacheAgentClass(null, msgs);
			case CddPackage.CDD_ROOT__CACHE_AGENT_QUORUM:
				return basicSetCacheAgentQuorum(null, msgs);
			case CddPackage.CDD_ROOT__CACHE_ASIDE:
				return basicSetCacheAside(null, msgs);
			case CddPackage.CDD_ROOT__CACHE_LIMITED:
				return basicSetCacheLimited(null, msgs);
			case CddPackage.CDD_ROOT__CACHE_LOADER_CLASS:
				return basicSetCacheLoaderClass(null, msgs);
			case CddPackage.CDD_ROOT__CACHE_MANAGER:
				return basicSetCacheManager(null, msgs);
			case CddPackage.CDD_ROOT__CACHE_STORAGE_ENABLED:
				return basicSetCacheStorageEnabled(null, msgs);
			case CddPackage.CDD_ROOT__CERTIFICATE_KEY_FILE:
				return basicSetCertificateKeyFile(null, msgs);
			case CddPackage.CDD_ROOT__CHANNEL:
				return basicSetChannel(null, msgs);
			case CddPackage.CDD_ROOT__CHECK_FOR_DUPLICATES:
				return basicSetCheckForDuplicates(null, msgs);
			case CddPackage.CDD_ROOT__CHECK_FOR_VERSION:
				return basicSetCheckForVersion(null, msgs);
			case CddPackage.CDD_ROOT__CHECK_INTERVAL:
				return basicSetCheckInterval(null, msgs);
			case CddPackage.CDD_ROOT__CHECKPOINT_INTERVAL:
				return basicSetCheckpointInterval(null, msgs);
			case CddPackage.CDD_ROOT__CHECKPOINT_OPS_LIMIT:
				return basicSetCheckpointOpsLimit(null, msgs);
			case CddPackage.CDD_ROOT__CHILD_CLUSTER_MEMBER:
				return basicSetChildClusterMember(null, msgs);
			case CddPackage.CDD_ROOT__CIPHER:
				return basicSetCipher(null, msgs);
			case CddPackage.CDD_ROOT__CIPHERS:
				return basicSetCiphers(null, msgs);
			case CddPackage.CDD_ROOT__CLASS:
				return basicSetClass(null, msgs);
			case CddPackage.CDD_ROOT__CLUSTER:
				return basicSetCluster(null, msgs);
			case CddPackage.CDD_ROOT__CLUSTER_MEMBER:
				return basicSetClusterMember(null, msgs);
			case CddPackage.CDD_ROOT__COMMENT:
				return basicSetComment(null, msgs);
			case CddPackage.CDD_ROOT__CONCURRENT_RTC:
				return basicSetConcurrentRtc(null, msgs);
			case CddPackage.CDD_ROOT__CONDITION:
				return basicSetCondition(null, msgs);
			case CddPackage.CDD_ROOT__CONNECTION_LINGER:
				return basicSetConnectionLinger(null, msgs);
			case CddPackage.CDD_ROOT__CONNECTION_TIMEOUT:
				return basicSetConnectionTimeout(null, msgs);
			case CddPackage.CDD_ROOT__CONSTANT:
				return basicSetConstant(null, msgs);
			case CddPackage.CDD_ROOT__CONTROLLER:
				return basicSetController(null, msgs);
			case CddPackage.CDD_ROOT__DAEMON:
				return basicSetDaemon(null, msgs);
			case CddPackage.CDD_ROOT__DASHBOARD_AGENT_CLASS:
				return basicSetDashboardAgentClass(null, msgs);
			case CddPackage.CDD_ROOT__DATA_STORE_PATH:
				return basicSetDataStorePath(null, msgs);
			case CddPackage.CDD_ROOT__DATE:
				return basicSetDate(null, msgs);
			case CddPackage.CDD_ROOT__DB_CONCEPTS:
				return basicSetDbConcepts(null, msgs);
			case CddPackage.CDD_ROOT__DB_DIR:
				return basicSetDbDir(null, msgs);
			case CddPackage.CDD_ROOT__DB_URIS:
				return basicSetDbUris(null, msgs);
			case CddPackage.CDD_ROOT__DELETE_RETRACTED:
				return basicSetDeleteRetracted(null, msgs);
			case CddPackage.CDD_ROOT__DESTINATION:
				return basicSetDestination(null, msgs);
			case CddPackage.CDD_ROOT__DESTINATION_GROUPS:
				return basicSetDestinationGroups(null, msgs);
			case CddPackage.CDD_ROOT__DESTINATIONS:
				return basicSetDestinations(null, msgs);
			case CddPackage.CDD_ROOT__DIR:
				return basicSetDir(null, msgs);
			case CddPackage.CDD_ROOT__DISCOVERY_URL:
				return basicSetDiscoveryUrl(null, msgs);
			case CddPackage.CDD_ROOT__DOCUMENT_PAGE:
				return basicSetDocumentPage(null, msgs);
			case CddPackage.CDD_ROOT__DOCUMENT_ROOT:
				return basicSetDocumentRoot(null, msgs);
			case CddPackage.CDD_ROOT__DOMAIN_NAME:
				return basicSetDomainName(null, msgs);
			case CddPackage.CDD_ROOT__DOMAIN_OBJECT:
				return basicSetDomainObject(null, msgs);
			case CddPackage.CDD_ROOT__DOMAIN_OBJECTS:
				return basicSetDomainObjects(null, msgs);
			case CddPackage.CDD_ROOT__ENABLED:
				return basicSetEnabled(null, msgs);
			case CddPackage.CDD_ROOT__ENABLE_TRACKING:
				return basicSetEnableTracking(null, msgs);
			case CddPackage.CDD_ROOT__ENCODING:
				return basicSetEncoding(null, msgs);
			case CddPackage.CDD_ROOT__ENCRYPTION:
				return basicSetEncryption(null, msgs);
			case CddPackage.CDD_ROOT__ENFORCE_POOLS:
				return basicSetEnforcePools(null, msgs);
			case CddPackage.CDD_ROOT__ENTITY_CACHE_SIZE:
				return basicSetEntityCacheSize(null, msgs);
			case CddPackage.CDD_ROOT__EVICTION:
				return basicSetEviction(null, msgs);
			case CddPackage.CDD_ROOT__EVICT_ON_UPDATE:
				return basicSetEvictOnUpdate(null, msgs);
			case CddPackage.CDD_ROOT__EXPLICIT_TUPLE:
				return basicSetExplicitTuple(null, msgs);
			case CddPackage.CDD_ROOT__FILES:
				return basicSetFiles(null, msgs);
			case CddPackage.CDD_ROOT__FUNCTION_GROUPS:
				return basicSetFunctionGroups(null, msgs);
			case CddPackage.CDD_ROOT__FUNCTIONS:
				return basicSetFunctions(null, msgs);
			case CddPackage.CDD_ROOT__GET_PROPERTY:
				return basicSetGetProperty(null, msgs);
			case CddPackage.CDD_ROOT__HOT_DEPLOY:
				return basicSetHotDeploy(null, msgs);
			case CddPackage.CDD_ROOT__HTTP:
				return basicSetHttp(null, msgs);
			case CddPackage.CDD_ROOT__IDENTITY_PASSWORD:
				return basicSetIdentityPassword(null, msgs);
			case CddPackage.CDD_ROOT__INACTIVITY_TIMEOUT:
				return basicSetInactivityTimeout(null, msgs);
			case CddPackage.CDD_ROOT__INDEX:
				return basicSetIndex(null, msgs);
			case CddPackage.CDD_ROOT__INDEXES:
				return basicSetIndexes(null, msgs);
			case CddPackage.CDD_ROOT__INFERENCE_AGENT_CLASS:
				return basicSetInferenceAgentClass(null, msgs);
			case CddPackage.CDD_ROOT__INFERENCE_ENGINE:
				return basicSetInferenceEngine(null, msgs);
			case CddPackage.CDD_ROOT__INITIAL_SIZE:
				return basicSetInitialSize(null, msgs);
			case CddPackage.CDD_ROOT__JOB_MANAGER:
				return basicSetJobManager(null, msgs);
			case CddPackage.CDD_ROOT__JOB_POOL_QUEUE_SIZE:
				return basicSetJobPoolQueueSize(null, msgs);
			case CddPackage.CDD_ROOT__JOB_POOL_THREAD_COUNT:
				return basicSetJobPoolThreadCount(null, msgs);
			case CddPackage.CDD_ROOT__KEY:
				return basicSetKey(null, msgs);
			case CddPackage.CDD_ROOT__KEY_MANAGER_ALGORITHM:
				return basicSetKeyManagerAlgorithm(null, msgs);
			case CddPackage.CDD_ROOT__LINE_LAYOUT:
				return basicSetLineLayout(null, msgs);
			case CddPackage.CDD_ROOT__LISTEN_URL:
				return basicSetListenUrl(null, msgs);
			case CddPackage.CDD_ROOT__LOAD:
				return basicSetLoad(null, msgs);
			case CddPackage.CDD_ROOT__LOAD_BALANCER_CONFIGS:
				return basicSetLoadBalancerConfigs(null, msgs);
			case CddPackage.CDD_ROOT__LOCAL_CACHE:
				return basicSetLocalCache(null, msgs);
			case CddPackage.CDD_ROOT__LOCK_TIMEOUT:
				return basicSetLockTimeout(null, msgs);
			case CddPackage.CDD_ROOT__LOG_CONFIG:
				return basicSetLogConfig(null, msgs);
			case CddPackage.CDD_ROOT__LOG_CONFIGS:
				return basicSetLogConfigs(null, msgs);
			case CddPackage.CDD_ROOT__MAX_ACTIVE:
				return basicSetMaxActive(null, msgs);
			case CddPackage.CDD_ROOT__MAX_NUMBER:
				return basicSetMaxNumber(null, msgs);
			case CddPackage.CDD_ROOT__MAX_PROCESSORS:
				return basicSetMaxProcessors(null, msgs);
			case CddPackage.CDD_ROOT__MAX_SIZE:
				return basicSetMaxSize(null, msgs);
			case CddPackage.CDD_ROOT__MAX_TIME:
				return basicSetMaxTime(null, msgs);
			case CddPackage.CDD_ROOT__MEMORY_MANAGER:
				return basicSetMemoryManager(null, msgs);
			case CddPackage.CDD_ROOT__MIN_SIZE:
				return basicSetMinSize(null, msgs);
			case CddPackage.CDD_ROOT__MM_ACTION:
				return basicSetMmAction(null, msgs);
			case CddPackage.CDD_ROOT__MM_ACTION_CONFIG:
				return basicSetMmActionConfig(null, msgs);
			case CddPackage.CDD_ROOT__MM_ACTION_CONFIG_SET:
				return basicSetMmActionConfigSet(null, msgs);
			case CddPackage.CDD_ROOT__MM_AGENT_CLASS:
				return basicSetMmAgentClass(null, msgs);
			case CddPackage.CDD_ROOT__MM_ALERT:
				return basicSetMmAlert(null, msgs);
			case CddPackage.CDD_ROOT__MM_EXECUTE_COMMAND:
				return basicSetMmExecuteCommand(null, msgs);
			case CddPackage.CDD_ROOT__MM_HEALTH_LEVEL:
				return basicSetMmHealthLevel(null, msgs);
			case CddPackage.CDD_ROOT__MM_SEND_EMAIL:
				return basicSetMmSendEmail(null, msgs);
			case CddPackage.CDD_ROOT__MM_TRIGGER_CONDITION:
				return basicSetMmTriggerCondition(null, msgs);
			case CddPackage.CDD_ROOT__NAME:
				return basicSetName(null, msgs);
			case CddPackage.CDD_ROOT__NOTIFICATION:
				return basicSetNotification(null, msgs);
			case CddPackage.CDD_ROOT__OBJECT_MANAGEMENT:
				return basicSetObjectManagement(null, msgs);
			case CddPackage.CDD_ROOT__OBJECT_TABLE:
				return basicSetObjectTable(null, msgs);
			case CddPackage.CDD_ROOT__PAIR_CONFIG:
				return basicSetPairConfig(null, msgs);
			case CddPackage.CDD_ROOT__PAIR_CONFIGS:
				return basicSetPairConfigs(null, msgs);
			case CddPackage.CDD_ROOT__PARALLEL_RECOVERY:
				return basicSetParallelRecovery(null, msgs);
			case CddPackage.CDD_ROOT__PERSISTENCE_OPTION:
				return basicSetPersistenceOption(null, msgs);
			case CddPackage.CDD_ROOT__PERSISTENCE_POLICY:
				return basicSetPersistencePolicy(null, msgs);
			case CddPackage.CDD_ROOT__POLICY_FILE:
				return basicSetPolicyFile(null, msgs);
			case CddPackage.CDD_ROOT__PORT:
				return basicSetPort(null, msgs);
			case CddPackage.CDD_ROOT__PRE_LOAD_CACHES:
				return basicSetPreLoadCaches(null, msgs);
			case CddPackage.CDD_ROOT__PRE_LOAD_ENABLED:
				return basicSetPreLoadEnabled(null, msgs);
			case CddPackage.CDD_ROOT__PRE_LOAD_FETCH_SIZE:
				return basicSetPreLoadFetchSize(null, msgs);
			case CddPackage.CDD_ROOT__PRE_LOAD_HANDLES:
				return basicSetPreLoadHandles(null, msgs);
			case CddPackage.CDD_ROOT__PRIMARY_CONNECTION:
				return basicSetPrimaryConnection(null, msgs);
			case CddPackage.CDD_ROOT__PRIORITY:
				return basicSetPriority(null, msgs);
			case CddPackage.CDD_ROOT__PROCESS:
				return basicSetProcess(null, msgs);
			case CddPackage.CDD_ROOT__PROCESS_AGENT_CLASS:
				return basicSetProcessAgentClass(null, msgs);
			case CddPackage.CDD_ROOT__PROCESS_ENGINE:
				return basicSetProcessEngine(null, msgs);
			case CddPackage.CDD_ROOT__PROCESSES:
				return basicSetProcesses(null, msgs);
			case CddPackage.CDD_ROOT__PROCESS_GROUPS:
				return basicSetProcessGroups(null, msgs);
			case CddPackage.CDD_ROOT__PROCESSING_UNIT:
				return basicSetProcessingUnit(null, msgs);
			case CddPackage.CDD_ROOT__PROCESSING_UNITS:
				return basicSetProcessingUnits(null, msgs);
			case CddPackage.CDD_ROOT__PROJECTION:
				return basicSetProjection(null, msgs);
			case CddPackage.CDD_ROOT__PROPERTY:
				return basicSetProperty(null, msgs);
			case CddPackage.CDD_ROOT__PROPERTY_CACHE_SIZE:
				return basicSetPropertyCacheSize(null, msgs);
			case CddPackage.CDD_ROOT__PROPERTY_CHECK_INTERVAL:
				return basicSetPropertyCheckInterval(null, msgs);
			case CddPackage.CDD_ROOT__PROPERTY_GROUP:
				return basicSetPropertyGroup(null, msgs);
			case CddPackage.CDD_ROOT__PROTOCOL:
				return basicSetProtocol(null, msgs);
			case CddPackage.CDD_ROOT__PROTOCOLS:
				return basicSetProtocols(null, msgs);
			case CddPackage.CDD_ROOT__PROTOCOL_TIMEOUT:
				return basicSetProtocolTimeout(null, msgs);
			case CddPackage.CDD_ROOT__PROVIDER:
				return basicSetProvider(null, msgs);
			case CddPackage.CDD_ROOT__QUERY_AGENT_CLASS:
				return basicSetQueryAgentClass(null, msgs);
			case CddPackage.CDD_ROOT__QUEUE_SIZE:
				return basicSetQueueSize(null, msgs);
			case CddPackage.CDD_ROOT__READ_TIMEOUT:
				return basicSetReadTimeout(null, msgs);
			case CddPackage.CDD_ROOT__RECEIVER:
				return basicSetReceiver(null, msgs);
			case CddPackage.CDD_ROOT__REMOTE_LISTEN_URL:
				return basicSetRemoteListenUrl(null, msgs);
			case CddPackage.CDD_ROOT__REQUESTER:
				return basicSetRequester(null, msgs);
			case CddPackage.CDD_ROOT__RETRY_COUNT:
				return basicSetRetryCount(null, msgs);
			case CddPackage.CDD_ROOT__REVERSE_REFERENCES:
				return basicSetReverseReferences(null, msgs);
			case CddPackage.CDD_ROOT__REVISION:
				return basicSetRevision(null, msgs);
			case CddPackage.CDD_ROOT__ROLE:
				return basicSetRole(null, msgs);
			case CddPackage.CDD_ROOT__ROLES:
				return basicSetRoles(null, msgs);
			case CddPackage.CDD_ROOT__ROUTER:
				return basicSetRouter(null, msgs);
			case CddPackage.CDD_ROOT__RULE:
				return basicSetRule(null, msgs);
			case CddPackage.CDD_ROOT__RULE_CONFIG:
				return basicSetRuleConfig(null, msgs);
			case CddPackage.CDD_ROOT__RULES:
				return basicSetRules(null, msgs);
			case CddPackage.CDD_ROOT__RULESETS:
				return basicSetRulesets(null, msgs);
			case CddPackage.CDD_ROOT__SECONDARY_CONNECTION:
				return basicSetSecondaryConnection(null, msgs);
			case CddPackage.CDD_ROOT__SECURITY_CONFIG:
				return basicSetSecurityConfig(null, msgs);
			case CddPackage.CDD_ROOT__SERVICE:
				return basicSetService(null, msgs);
			case CddPackage.CDD_ROOT__SET_PROPERTY:
				return basicSetSetProperty(null, msgs);
			case CddPackage.CDD_ROOT__SHARED_ALL:
				return basicSetSharedAll(null, msgs);
			case CddPackage.CDD_ROOT__SHARED_QUEUE:
				return basicSetSharedQueue(null, msgs);
			case CddPackage.CDD_ROOT__SHOUTDOWN_WAIT:
				return basicSetShoutdownWait(null, msgs);
			case CddPackage.CDD_ROOT__SHUTDOWN:
				return basicSetShutdown(null, msgs);
			case CddPackage.CDD_ROOT__SIZE:
				return basicSetSize(null, msgs);
			case CddPackage.CDD_ROOT__SKIP_RECOVERY:
				return basicSetSkipRecovery(null, msgs);
			case CddPackage.CDD_ROOT__SOCKET_OUTPUT_BUFFER_SIZE:
				return basicSetSocketOutputBufferSize(null, msgs);
			case CddPackage.CDD_ROOT__SSL:
				return basicSetSsl(null, msgs);
			case CddPackage.CDD_ROOT__STALE_CONNECTION_CHECK:
				return basicSetStaleConnectionCheck(null, msgs);
			case CddPackage.CDD_ROOT__STARTUP:
				return basicSetStartup(null, msgs);
			case CddPackage.CDD_ROOT__STRATEGY:
				return basicSetStrategy(null, msgs);
			case CddPackage.CDD_ROOT__SUBJECT:
				return basicSetSubject(null, msgs);
			case CddPackage.CDD_ROOT__SUBSCRIBE:
				return basicSetSubscribe(null, msgs);
			case CddPackage.CDD_ROOT__SYS_ERR_REDIRECT:
				return basicSetSysErrRedirect(null, msgs);
			case CddPackage.CDD_ROOT__SYS_OUT_REDIRECT:
				return basicSetSysOutRedirect(null, msgs);
			case CddPackage.CDD_ROOT__TABLE_NAME:
				return basicSetTableName(null, msgs);
			case CddPackage.CDD_ROOT__TCP_NO_DELAY:
				return basicSetTcpNoDelay(null, msgs);
			case CddPackage.CDD_ROOT__TERMINAL:
				return basicSetTerminal(null, msgs);
			case CddPackage.CDD_ROOT__THREAD_COUNT:
				return basicSetThreadCount(null, msgs);
			case CddPackage.CDD_ROOT__TOKEN_FILE:
				return basicSetTokenFile(null, msgs);
			case CddPackage.CDD_ROOT__TRUST_MANAGER_ALGORITHM:
				return basicSetTrustManagerAlgorithm(null, msgs);
			case CddPackage.CDD_ROOT__TTL:
				return basicSetTtl(null, msgs);
			case CddPackage.CDD_ROOT__TYPE:
				return basicSetType(null, msgs);
			case CddPackage.CDD_ROOT__USER_NAME:
				return basicSetUserName(null, msgs);
			case CddPackage.CDD_ROOT__USER_PASSWORD:
				return basicSetUserPassword(null, msgs);
			case CddPackage.CDD_ROOT__WAIT_TIMEOUT:
				return basicSetWaitTimeout(null, msgs);
			case CddPackage.CDD_ROOT__WORKERS:
				return basicSetWorkers(null, msgs);
			case CddPackage.CDD_ROOT__WORKERTHREADS_COUNT:
				return basicSetWorkerthreadsCount(null, msgs);
			case CddPackage.CDD_ROOT__WRITE_TIMEOUT:
				return basicSetWriteTimeout(null, msgs);
			case CddPackage.CDD_ROOT__ENTITY:
				return basicSetEntity(null, msgs);
			case CddPackage.CDD_ROOT__FILTER:
				return basicSetFilter(null, msgs);
			case CddPackage.CDD_ROOT__ENTITY_SET:
				return basicSetEntitySet(null, msgs);
			case CddPackage.CDD_ROOT__ENABLE_TABLE_TRIMMING:
				return basicSetEnableTableTrimming(null, msgs);
			case CddPackage.CDD_ROOT__TRIMMING_FIELD:
				return basicSetTrimmingField(null, msgs);
			case CddPackage.CDD_ROOT__TRIMMING_RULE:
				return basicSetTrimmingRule(null, msgs);
			case CddPackage.CDD_ROOT__GENERATE_LV_FILES:
				return basicSetGenerateLVFiles(null, msgs);
			case CddPackage.CDD_ROOT__OUTPUT_PATH:
				return basicSetOutputPath(null, msgs);
			case CddPackage.CDD_ROOT__LDM_CONNECTION:
				return basicSetLdmConnection(null, msgs);
			case CddPackage.CDD_ROOT__LDM_URL:
				return basicSetLdmUrl(null, msgs);
			case CddPackage.CDD_ROOT__PUBLISHER:
				return basicSetPublisher(null, msgs);
			case CddPackage.CDD_ROOT__PUBLISHER_QUEUE_SIZE:
				return basicSetPublisherQueueSize(null, msgs);
			case CddPackage.CDD_ROOT__PUBLISHER_THREAD_COUNT:
				return basicSetPublisherThreadCount(null, msgs);
			case CddPackage.CDD_ROOT__LIVEVIEW_AGENT_CLASS:
				return basicSetLiveviewAgentClass(null, msgs);
			case CddPackage.CDD_ROOT__COMPOSITE_INDEXES:
				return basicSetCompositeIndexes(null, msgs);
			case CddPackage.CDD_ROOT__COMPOSITE_INDEX:
				return basicSetCompositeIndex(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CddPackage.CDD_ROOT__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case CddPackage.CDD_ROOT__XMLNS_PREFIX_MAP:
				if (coreType) return getXMLNSPrefixMap();
				else return getXMLNSPrefixMap().map();
			case CddPackage.CDD_ROOT__XSI_SCHEMA_LOCATION:
				if (coreType) return getXSISchemaLocation();
				else return getXSISchemaLocation().map();
			case CddPackage.CDD_ROOT__ACCEPT_COUNT:
				return getAcceptCount();
			case CddPackage.CDD_ROOT__ADDRESS:
				return getAddress();
			case CddPackage.CDD_ROOT__ADHOC_CONFIG:
				return getAdhocConfig();
			case CddPackage.CDD_ROOT__ADHOC_CONFIGS:
				return getAdhocConfigs();
			case CddPackage.CDD_ROOT__AGENT:
				return getAgent();
			case CddPackage.CDD_ROOT__AGENT_CLASSES:
				return getAgentClasses();
			case CddPackage.CDD_ROOT__AGENTS:
				return getAgents();
			case CddPackage.CDD_ROOT__ALERT_CONFIG:
				return getAlertConfig();
			case CddPackage.CDD_ROOT__ALERT_CONFIG_SET:
				return getAlertConfigSet();
			case CddPackage.CDD_ROOT__APPEND:
				return getAppend();
			case CddPackage.CDD_ROOT__ARG:
				return getArg();
			case CddPackage.CDD_ROOT__AUTHOR:
				return getAuthor();
			case CddPackage.CDD_ROOT__BACKING_STORE:
				return getBackingStore();
			case CddPackage.CDD_ROOT__BACKUP_COPIES:
				return getBackupCopies();
			case CddPackage.CDD_ROOT__BUSINESSWORKS:
				return getBusinessworks();
			case CddPackage.CDD_ROOT__CACHE:
				return getCache();
			case CddPackage.CDD_ROOT__CACHE_AGENT_CLASS:
				return getCacheAgentClass();
			case CddPackage.CDD_ROOT__CACHE_AGENT_QUORUM:
				return getCacheAgentQuorum();
			case CddPackage.CDD_ROOT__CACHE_ASIDE:
				return getCacheAside();
			case CddPackage.CDD_ROOT__CACHE_LIMITED:
				return getCacheLimited();
			case CddPackage.CDD_ROOT__CACHE_LOADER_CLASS:
				return getCacheLoaderClass();
			case CddPackage.CDD_ROOT__CACHE_MANAGER:
				return getCacheManager();
			case CddPackage.CDD_ROOT__CACHE_MODE:
				return getCacheMode();
			case CddPackage.CDD_ROOT__CACHE_STORAGE_ENABLED:
				return getCacheStorageEnabled();
			case CddPackage.CDD_ROOT__CERTIFICATE_KEY_FILE:
				return getCertificateKeyFile();
			case CddPackage.CDD_ROOT__CHANNEL:
				return getChannel();
			case CddPackage.CDD_ROOT__CHECK_FOR_DUPLICATES:
				return getCheckForDuplicates();
			case CddPackage.CDD_ROOT__CHECK_FOR_VERSION:
				return getCheckForVersion();
			case CddPackage.CDD_ROOT__CHECK_INTERVAL:
				return getCheckInterval();
			case CddPackage.CDD_ROOT__CHECKPOINT_INTERVAL:
				return getCheckpointInterval();
			case CddPackage.CDD_ROOT__CHECKPOINT_OPS_LIMIT:
				return getCheckpointOpsLimit();
			case CddPackage.CDD_ROOT__CHILD_CLUSTER_MEMBER:
				return getChildClusterMember();
			case CddPackage.CDD_ROOT__CIPHER:
				return getCipher();
			case CddPackage.CDD_ROOT__CIPHERS:
				return getCiphers();
			case CddPackage.CDD_ROOT__CLASS:
				return getClass_();
			case CddPackage.CDD_ROOT__CLUSTER:
				return getCluster();
			case CddPackage.CDD_ROOT__CLUSTER_MEMBER:
				return getClusterMember();
			case CddPackage.CDD_ROOT__COMMENT:
				return getComment();
			case CddPackage.CDD_ROOT__CONCURRENT_RTC:
				return getConcurrentRtc();
			case CddPackage.CDD_ROOT__CONDITION:
				return getCondition();
			case CddPackage.CDD_ROOT__CONNECTION_LINGER:
				return getConnectionLinger();
			case CddPackage.CDD_ROOT__CONNECTION_TIMEOUT:
				return getConnectionTimeout();
			case CddPackage.CDD_ROOT__CONSTANT:
				return getConstant();
			case CddPackage.CDD_ROOT__CONTROLLER:
				return getController();
			case CddPackage.CDD_ROOT__DAEMON:
				return getDaemon();
			case CddPackage.CDD_ROOT__DASHBOARD_AGENT_CLASS:
				return getDashboardAgentClass();
			case CddPackage.CDD_ROOT__DATA_STORE_PATH:
				return getDataStorePath();
			case CddPackage.CDD_ROOT__DATE:
				return getDate();
			case CddPackage.CDD_ROOT__DB_CONCEPTS:
				return getDbConcepts();
			case CddPackage.CDD_ROOT__DB_DIR:
				return getDbDir();
			case CddPackage.CDD_ROOT__DB_URIS:
				return getDbUris();
			case CddPackage.CDD_ROOT__DEFAULT_MODE:
				return getDefaultMode();
			case CddPackage.CDD_ROOT__DELETE_RETRACTED:
				return getDeleteRetracted();
			case CddPackage.CDD_ROOT__DESTINATION:
				return getDestination();
			case CddPackage.CDD_ROOT__DESTINATION_GROUPS:
				return getDestinationGroups();
			case CddPackage.CDD_ROOT__DESTINATIONS:
				return getDestinations();
			case CddPackage.CDD_ROOT__DIR:
				return getDir();
			case CddPackage.CDD_ROOT__DISCOVERY_URL:
				return getDiscoveryUrl();
			case CddPackage.CDD_ROOT__DOCUMENT_PAGE:
				return getDocumentPage();
			case CddPackage.CDD_ROOT__DOCUMENT_ROOT:
				return getDocumentRoot();
			case CddPackage.CDD_ROOT__DOMAIN_NAME:
				return getDomainName();
			case CddPackage.CDD_ROOT__DOMAIN_OBJECT:
				return getDomainObject();
			case CddPackage.CDD_ROOT__DOMAIN_OBJECTS:
				return getDomainObjects();
			case CddPackage.CDD_ROOT__ENABLED:
				return getEnabled();
			case CddPackage.CDD_ROOT__ENABLE_TRACKING:
				return getEnableTracking();
			case CddPackage.CDD_ROOT__ENCODING:
				return getEncoding();
			case CddPackage.CDD_ROOT__ENCRYPTION:
				return getEncryption();
			case CddPackage.CDD_ROOT__ENFORCE_POOLS:
				return getEnforcePools();
			case CddPackage.CDD_ROOT__ENTITY_CACHE_SIZE:
				return getEntityCacheSize();
			case CddPackage.CDD_ROOT__EVICTION:
				return getEviction();
			case CddPackage.CDD_ROOT__EVICT_ON_UPDATE:
				return getEvictOnUpdate();
			case CddPackage.CDD_ROOT__EXPLICIT_TUPLE:
				return getExplicitTuple();
			case CddPackage.CDD_ROOT__FILES:
				return getFiles();
			case CddPackage.CDD_ROOT__FUNCTION_GROUPS:
				return getFunctionGroups();
			case CddPackage.CDD_ROOT__FUNCTIONS:
				return getFunctions();
			case CddPackage.CDD_ROOT__GET_PROPERTY:
				return getGetProperty();
			case CddPackage.CDD_ROOT__HOT_DEPLOY:
				return getHotDeploy();
			case CddPackage.CDD_ROOT__HTTP:
				return getHttp();
			case CddPackage.CDD_ROOT__IDENTITY_PASSWORD:
				return getIdentityPassword();
			case CddPackage.CDD_ROOT__INACTIVITY_TIMEOUT:
				return getInactivityTimeout();
			case CddPackage.CDD_ROOT__INDEX:
				return getIndex();
			case CddPackage.CDD_ROOT__INDEXES:
				return getIndexes();
			case CddPackage.CDD_ROOT__INFERENCE_AGENT_CLASS:
				return getInferenceAgentClass();
			case CddPackage.CDD_ROOT__INFERENCE_ENGINE:
				return getInferenceEngine();
			case CddPackage.CDD_ROOT__INITIAL_SIZE:
				return getInitialSize();
			case CddPackage.CDD_ROOT__JOB_MANAGER:
				return getJobManager();
			case CddPackage.CDD_ROOT__JOB_POOL_QUEUE_SIZE:
				return getJobPoolQueueSize();
			case CddPackage.CDD_ROOT__JOB_POOL_THREAD_COUNT:
				return getJobPoolThreadCount();
			case CddPackage.CDD_ROOT__KEY:
				return getKey();
			case CddPackage.CDD_ROOT__KEY_MANAGER_ALGORITHM:
				return getKeyManagerAlgorithm();
			case CddPackage.CDD_ROOT__LINE_LAYOUT:
				return getLineLayout();
			case CddPackage.CDD_ROOT__LISTEN_URL:
				return getListenUrl();
			case CddPackage.CDD_ROOT__LOAD:
				return getLoad();
			case CddPackage.CDD_ROOT__LOAD_BALANCER_CONFIGS:
				return getLoadBalancerConfigs();
			case CddPackage.CDD_ROOT__LOCAL_CACHE:
				return getLocalCache();
			case CddPackage.CDD_ROOT__LOCK_TIMEOUT:
				return getLockTimeout();
			case CddPackage.CDD_ROOT__LOG_CONFIG:
				return getLogConfig();
			case CddPackage.CDD_ROOT__LOG_CONFIGS:
				return getLogConfigs();
			case CddPackage.CDD_ROOT__MAX_ACTIVE:
				return getMaxActive();
			case CddPackage.CDD_ROOT__MAX_NUMBER:
				return getMaxNumber();
			case CddPackage.CDD_ROOT__MAX_PROCESSORS:
				return getMaxProcessors();
			case CddPackage.CDD_ROOT__MAX_SIZE:
				return getMaxSize();
			case CddPackage.CDD_ROOT__MAX_TIME:
				return getMaxTime();
			case CddPackage.CDD_ROOT__MEMORY_MANAGER:
				return getMemoryManager();
			case CddPackage.CDD_ROOT__MESSAGE_ENCODING:
				return getMessageEncoding();
			case CddPackage.CDD_ROOT__MIN_SIZE:
				return getMinSize();
			case CddPackage.CDD_ROOT__MM_ACTION:
				return getMmAction();
			case CddPackage.CDD_ROOT__MM_ACTION_CONFIG:
				return getMmActionConfig();
			case CddPackage.CDD_ROOT__MM_ACTION_CONFIG_SET:
				return getMmActionConfigSet();
			case CddPackage.CDD_ROOT__MM_AGENT_CLASS:
				return getMmAgentClass();
			case CddPackage.CDD_ROOT__MM_ALERT:
				return getMmAlert();
			case CddPackage.CDD_ROOT__MM_EXECUTE_COMMAND:
				return getMmExecuteCommand();
			case CddPackage.CDD_ROOT__MM_HEALTH_LEVEL:
				return getMmHealthLevel();
			case CddPackage.CDD_ROOT__MM_SEND_EMAIL:
				return getMmSendEmail();
			case CddPackage.CDD_ROOT__MM_TRIGGER_CONDITION:
				return getMmTriggerCondition();
			case CddPackage.CDD_ROOT__MODE:
				return getMode();
			case CddPackage.CDD_ROOT__NAME:
				return getName();
			case CddPackage.CDD_ROOT__NOTIFICATION:
				return getNotification();
			case CddPackage.CDD_ROOT__OBJECT_MANAGEMENT:
				return getObjectManagement();
			case CddPackage.CDD_ROOT__OBJECT_TABLE:
				return getObjectTable();
			case CddPackage.CDD_ROOT__PAIR_CONFIG:
				return getPairConfig();
			case CddPackage.CDD_ROOT__PAIR_CONFIGS:
				return getPairConfigs();
			case CddPackage.CDD_ROOT__PARALLEL_RECOVERY:
				return getParallelRecovery();
			case CddPackage.CDD_ROOT__PERSISTENCE_OPTION:
				return getPersistenceOption();
			case CddPackage.CDD_ROOT__PERSISTENCE_POLICY:
				return getPersistencePolicy();
			case CddPackage.CDD_ROOT__POLICY_FILE:
				return getPolicyFile();
			case CddPackage.CDD_ROOT__PORT:
				return getPort();
			case CddPackage.CDD_ROOT__PRE_LOAD_CACHES:
				return getPreLoadCaches();
			case CddPackage.CDD_ROOT__PRE_LOAD_ENABLED:
				return getPreLoadEnabled();
			case CddPackage.CDD_ROOT__PRE_LOAD_FETCH_SIZE:
				return getPreLoadFetchSize();
			case CddPackage.CDD_ROOT__PRE_LOAD_HANDLES:
				return getPreLoadHandles();
			case CddPackage.CDD_ROOT__PRE_PROCESSOR:
				return getPreProcessor();
			case CddPackage.CDD_ROOT__PRIMARY_CONNECTION:
				return getPrimaryConnection();
			case CddPackage.CDD_ROOT__PRIORITY:
				return getPriority();
			case CddPackage.CDD_ROOT__PROCESS:
				return getProcess();
			case CddPackage.CDD_ROOT__PROCESS_AGENT_CLASS:
				return getProcessAgentClass();
			case CddPackage.CDD_ROOT__PROCESS_ENGINE:
				return getProcessEngine();
			case CddPackage.CDD_ROOT__PROCESSES:
				return getProcesses();
			case CddPackage.CDD_ROOT__PROCESS_GROUPS:
				return getProcessGroups();
			case CddPackage.CDD_ROOT__PROCESSING_UNIT:
				return getProcessingUnit();
			case CddPackage.CDD_ROOT__PROCESSING_UNITS:
				return getProcessingUnits();
			case CddPackage.CDD_ROOT__PROJECTION:
				return getProjection();
			case CddPackage.CDD_ROOT__PROPERTY:
				return getProperty();
			case CddPackage.CDD_ROOT__PROPERTY_CACHE_SIZE:
				return getPropertyCacheSize();
			case CddPackage.CDD_ROOT__PROPERTY_CHECK_INTERVAL:
				return getPropertyCheckInterval();
			case CddPackage.CDD_ROOT__PROPERTY_GROUP:
				return getPropertyGroup();
			case CddPackage.CDD_ROOT__PROTOCOL:
				return getProtocol();
			case CddPackage.CDD_ROOT__PROTOCOLS:
				return getProtocols();
			case CddPackage.CDD_ROOT__PROTOCOL_TIMEOUT:
				return getProtocolTimeout();
			case CddPackage.CDD_ROOT__PROVIDER:
				return getProvider();
			case CddPackage.CDD_ROOT__QUERY_AGENT_CLASS:
				return getQueryAgentClass();
			case CddPackage.CDD_ROOT__QUEUE_SIZE:
				return getQueueSize();
			case CddPackage.CDD_ROOT__READ_TIMEOUT:
				return getReadTimeout();
			case CddPackage.CDD_ROOT__RECEIVER:
				return getReceiver();
			case CddPackage.CDD_ROOT__REMOTE_LISTEN_URL:
				return getRemoteListenUrl();
			case CddPackage.CDD_ROOT__REQUESTER:
				return getRequester();
			case CddPackage.CDD_ROOT__RETRY_COUNT:
				return getRetryCount();
			case CddPackage.CDD_ROOT__REVERSE_REFERENCES:
				return getReverseReferences();
			case CddPackage.CDD_ROOT__REVISION:
				return getRevision();
			case CddPackage.CDD_ROOT__ROLE:
				return getRole();
			case CddPackage.CDD_ROOT__ROLES:
				return getRoles();
			case CddPackage.CDD_ROOT__ROUTER:
				return getRouter();
			case CddPackage.CDD_ROOT__RULE:
				return getRule();
			case CddPackage.CDD_ROOT__RULE_CONFIG:
				return getRuleConfig();
			case CddPackage.CDD_ROOT__RULES:
				return getRules();
			case CddPackage.CDD_ROOT__RULESETS:
				return getRulesets();
			case CddPackage.CDD_ROOT__SECONDARY_CONNECTION:
				return getSecondaryConnection();
			case CddPackage.CDD_ROOT__SECURITY_CONFIG:
				return getSecurityConfig();
			case CddPackage.CDD_ROOT__SERVICE:
				return getService();
			case CddPackage.CDD_ROOT__SET_PROPERTY:
				return getSetProperty();
			case CddPackage.CDD_ROOT__SHARED_ALL:
				return getSharedAll();
			case CddPackage.CDD_ROOT__SHARED_QUEUE:
				return getSharedQueue();
			case CddPackage.CDD_ROOT__SHOUTDOWN_WAIT:
				return getShoutdownWait();
			case CddPackage.CDD_ROOT__SHUTDOWN:
				return getShutdown();
			case CddPackage.CDD_ROOT__SIZE:
				return getSize();
			case CddPackage.CDD_ROOT__SKIP_RECOVERY:
				return getSkipRecovery();
			case CddPackage.CDD_ROOT__SOCKET_OUTPUT_BUFFER_SIZE:
				return getSocketOutputBufferSize();
			case CddPackage.CDD_ROOT__SSL:
				return getSsl();
			case CddPackage.CDD_ROOT__STALE_CONNECTION_CHECK:
				return getStaleConnectionCheck();
			case CddPackage.CDD_ROOT__STARTUP:
				return getStartup();
			case CddPackage.CDD_ROOT__STRATEGY:
				return getStrategy();
			case CddPackage.CDD_ROOT__SUBJECT:
				return getSubject();
			case CddPackage.CDD_ROOT__SUBSCRIBE:
				return getSubscribe();
			case CddPackage.CDD_ROOT__SYS_ERR_REDIRECT:
				return getSysErrRedirect();
			case CddPackage.CDD_ROOT__SYS_OUT_REDIRECT:
				return getSysOutRedirect();
			case CddPackage.CDD_ROOT__TABLE_NAME:
				return getTableName();
			case CddPackage.CDD_ROOT__TCP_NO_DELAY:
				return getTcpNoDelay();
			case CddPackage.CDD_ROOT__TERMINAL:
				return getTerminal();
			case CddPackage.CDD_ROOT__THREAD_AFFINITY_RULE_FUNCTION:
				return getThreadAffinityRuleFunction();
			case CddPackage.CDD_ROOT__THREAD_COUNT:
				return getThreadCount();
			case CddPackage.CDD_ROOT__THREADING_MODEL:
				return getThreadingModel();
			case CddPackage.CDD_ROOT__TOKEN_FILE:
				return getTokenFile();
			case CddPackage.CDD_ROOT__TRUST_MANAGER_ALGORITHM:
				return getTrustManagerAlgorithm();
			case CddPackage.CDD_ROOT__TTL:
				return getTtl();
			case CddPackage.CDD_ROOT__TYPE:
				return getType();
			case CddPackage.CDD_ROOT__URI:
				return getUri();
			case CddPackage.CDD_ROOT__USER_NAME:
				return getUserName();
			case CddPackage.CDD_ROOT__USER_PASSWORD:
				return getUserPassword();
			case CddPackage.CDD_ROOT__VERSION:
				return getVersion();
			case CddPackage.CDD_ROOT__WAIT_TIMEOUT:
				return getWaitTimeout();
			case CddPackage.CDD_ROOT__WORKERS:
				return getWorkers();
			case CddPackage.CDD_ROOT__WORKERTHREADS_COUNT:
				return getWorkerthreadsCount();
			case CddPackage.CDD_ROOT__WRITE_TIMEOUT:
				return getWriteTimeout();
			case CddPackage.CDD_ROOT__ENTITY:
				return getEntity();
			case CddPackage.CDD_ROOT__FILTER:
				return getFilter();
			case CddPackage.CDD_ROOT__ENTITY_SET:
				return getEntitySet();
			case CddPackage.CDD_ROOT__ENABLE_TABLE_TRIMMING:
				return getEnableTableTrimming();
			case CddPackage.CDD_ROOT__TRIMMING_FIELD:
				return getTrimmingField();
			case CddPackage.CDD_ROOT__TRIMMING_RULE:
				return getTrimmingRule();
			case CddPackage.CDD_ROOT__GENERATE_LV_FILES:
				return getGenerateLVFiles();
			case CddPackage.CDD_ROOT__OUTPUT_PATH:
				return getOutputPath();
			case CddPackage.CDD_ROOT__LDM_CONNECTION:
				return getLdmConnection();
			case CddPackage.CDD_ROOT__LDM_URL:
				return getLdmUrl();
			case CddPackage.CDD_ROOT__PUBLISHER:
				return getPublisher();
			case CddPackage.CDD_ROOT__PUBLISHER_QUEUE_SIZE:
				return getPublisherQueueSize();
			case CddPackage.CDD_ROOT__PUBLISHER_THREAD_COUNT:
				return getPublisherThreadCount();
			case CddPackage.CDD_ROOT__LIVEVIEW_AGENT_CLASS:
				return getLiveviewAgentClass();
			case CddPackage.CDD_ROOT__COMPOSITE_INDEXES:
				return getCompositeIndexes();
			case CddPackage.CDD_ROOT__COMPOSITE_INDEX:
				return getCompositeIndex();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CddPackage.CDD_ROOT__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case CddPackage.CDD_ROOT__XMLNS_PREFIX_MAP:
				((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
				return;
			case CddPackage.CDD_ROOT__XSI_SCHEMA_LOCATION:
				((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
				return;
			case CddPackage.CDD_ROOT__ACCEPT_COUNT:
				setAcceptCount((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__ADDRESS:
				setAddress((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__ADHOC_CONFIG:
				setAdhocConfig((LoadBalancerAdhocConfigConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__ADHOC_CONFIGS:
				setAdhocConfigs((LoadBalancerAdhocConfigsConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__AGENT:
				setAgent((AgentConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__AGENT_CLASSES:
				setAgentClasses((AgentClassesConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__AGENTS:
				setAgents((AgentsConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__ALERT_CONFIG:
				setAlertConfig((AlertConfigConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__ALERT_CONFIG_SET:
				setAlertConfigSet((AlertConfigSetConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__APPEND:
				setAppend((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__ARG:
				setArg((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__AUTHOR:
				setAuthor((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__BACKING_STORE:
				setBackingStore((BackingStoreConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__BACKUP_COPIES:
				setBackupCopies((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__BUSINESSWORKS:
				setBusinessworks((BusinessworksConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CACHE:
				setCache((CacheAgentClassConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CACHE_AGENT_CLASS:
				setCacheAgentClass((CacheAgentClassConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CACHE_AGENT_QUORUM:
				setCacheAgentQuorum((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CACHE_ASIDE:
				setCacheAside((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CACHE_LIMITED:
				setCacheLimited((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CACHE_LOADER_CLASS:
				setCacheLoaderClass((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CACHE_MANAGER:
				setCacheManager((CacheManagerConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CACHE_MODE:
				setCacheMode((DomainObjectModeConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CACHE_STORAGE_ENABLED:
				setCacheStorageEnabled((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CERTIFICATE_KEY_FILE:
				setCertificateKeyFile((SecurityOverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CHANNEL:
				setChannel((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CHECK_FOR_DUPLICATES:
				setCheckForDuplicates((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CHECK_FOR_VERSION:
				setCheckForVersion((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CHECK_INTERVAL:
				setCheckInterval((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CHECKPOINT_INTERVAL:
				setCheckpointInterval((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CHECKPOINT_OPS_LIMIT:
				setCheckpointOpsLimit((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CHILD_CLUSTER_MEMBER:
				setChildClusterMember((ChildClusterMemberConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CIPHER:
				setCipher((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CIPHERS:
				setCiphers((CiphersConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CLASS:
				setClass((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CLUSTER:
				setCluster((ClusterConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CLUSTER_MEMBER:
				setClusterMember((ClusterMemberConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__COMMENT:
				setComment((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CONCURRENT_RTC:
				setConcurrentRtc((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CONDITION:
				setCondition((ConditionConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CONNECTION_LINGER:
				setConnectionLinger((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CONNECTION_TIMEOUT:
				setConnectionTimeout((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CONSTANT:
				setConstant((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__CONTROLLER:
				setController((SecurityController)newValue);
				return;
			case CddPackage.CDD_ROOT__DAEMON:
				setDaemon((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__DASHBOARD_AGENT_CLASS:
				setDashboardAgentClass((DashboardAgentClassConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__DATA_STORE_PATH:
				setDataStorePath((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__DATE:
				setDate((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__DB_CONCEPTS:
				setDbConcepts((DbConceptsConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__DB_DIR:
				setDbDir((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__DB_URIS:
				setDbUris((UrisConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__DEFAULT_MODE:
				setDefaultMode((DomainObjectModeConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__DELETE_RETRACTED:
				setDeleteRetracted((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__DESTINATION:
				setDestination((DestinationConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__DESTINATION_GROUPS:
				setDestinationGroups((DestinationGroupsConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__DESTINATIONS:
				setDestinations((DestinationsConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__DIR:
				setDir((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__DISCOVERY_URL:
				setDiscoveryUrl((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__DOCUMENT_PAGE:
				setDocumentPage((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__DOCUMENT_ROOT:
				setDocumentRoot((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__DOMAIN_NAME:
				setDomainName((SecurityOverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__DOMAIN_OBJECT:
				setDomainObject((DomainObjectConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__DOMAIN_OBJECTS:
				setDomainObjects((DomainObjectsConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__ENABLED:
				setEnabled((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__ENABLE_TRACKING:
				setEnableTracking((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__ENCODING:
				setEncoding((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__ENCRYPTION:
				setEncryption((FieldEncryptionConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__ENFORCE_POOLS:
				setEnforcePools((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__ENTITY_CACHE_SIZE:
				setEntityCacheSize((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__EVICTION:
				setEviction((EvictionConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__EVICT_ON_UPDATE:
				setEvictOnUpdate((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__EXPLICIT_TUPLE:
				setExplicitTuple((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__FILES:
				setFiles((FilesConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__FUNCTION_GROUPS:
				setFunctionGroups((FunctionGroupsConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__FUNCTIONS:
				setFunctions((FunctionsConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__GET_PROPERTY:
				setGetProperty((GetPropertyConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__HOT_DEPLOY:
				setHotDeploy((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__HTTP:
				setHttp((HttpConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__IDENTITY_PASSWORD:
				setIdentityPassword((SecurityOverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__INACTIVITY_TIMEOUT:
				setInactivityTimeout((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__INDEX:
				setIndex((IndexConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__INDEXES:
				setIndexes((IndexesConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__INFERENCE_AGENT_CLASS:
				setInferenceAgentClass((InferenceAgentClassConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__INFERENCE_ENGINE:
				setInferenceEngine((InferenceEngineConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__INITIAL_SIZE:
				setInitialSize((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__JOB_MANAGER:
				setJobManager((JobManagerConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__JOB_POOL_QUEUE_SIZE:
				setJobPoolQueueSize((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__JOB_POOL_THREAD_COUNT:
				setJobPoolThreadCount((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__KEY:
				setKey((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__KEY_MANAGER_ALGORITHM:
				setKeyManagerAlgorithm((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__LINE_LAYOUT:
				setLineLayout((LineLayoutConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__LISTEN_URL:
				setListenUrl((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__LOAD:
				setLoad((LoadConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__LOAD_BALANCER_CONFIGS:
				setLoadBalancerConfigs((LoadBalancerConfigsConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__LOCAL_CACHE:
				setLocalCache((LocalCacheConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__LOCK_TIMEOUT:
				setLockTimeout((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__LOG_CONFIG:
				setLogConfig((LogConfigConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__LOG_CONFIGS:
				setLogConfigs((LogConfigsConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__MAX_ACTIVE:
				setMaxActive((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__MAX_NUMBER:
				setMaxNumber((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__MAX_PROCESSORS:
				setMaxProcessors((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__MAX_SIZE:
				setMaxSize((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__MAX_TIME:
				setMaxTime((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__MEMORY_MANAGER:
				setMemoryManager((MemoryManagerConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__MESSAGE_ENCODING:
				setMessageEncoding((String)newValue);
				return;
			case CddPackage.CDD_ROOT__MIN_SIZE:
				setMinSize((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__MM_ACTION:
				setMmAction((MmActionConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__MM_ACTION_CONFIG:
				setMmActionConfig((MmActionConfigConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__MM_ACTION_CONFIG_SET:
				setMmActionConfigSet((MmActionConfigSetConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__MM_AGENT_CLASS:
				setMmAgentClass((MmAgentClassConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__MM_ALERT:
				setMmAlert((MmAlertConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__MM_EXECUTE_COMMAND:
				setMmExecuteCommand((MmExecuteCommandConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__MM_HEALTH_LEVEL:
				setMmHealthLevel((MmHealthLevelConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__MM_SEND_EMAIL:
				setMmSendEmail((MmSendEmailConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__MM_TRIGGER_CONDITION:
				setMmTriggerCondition((MmTriggerConditionConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__MODE:
				setMode((DomainObjectModeConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__NAME:
				setName((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__NOTIFICATION:
				setNotification((NotificationConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__OBJECT_MANAGEMENT:
				setObjectManagement((ObjectManagementConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__OBJECT_TABLE:
				setObjectTable((ObjectTableConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PAIR_CONFIG:
				setPairConfig((LoadBalancerPairConfigConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PAIR_CONFIGS:
				setPairConfigs((LoadBalancerPairConfigsConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PARALLEL_RECOVERY:
				setParallelRecovery((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PERSISTENCE_OPTION:
				setPersistenceOption((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PERSISTENCE_POLICY:
				setPersistencePolicy((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__POLICY_FILE:
				setPolicyFile((SecurityOverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PORT:
				setPort((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PRE_LOAD_CACHES:
				setPreLoadCaches((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PRE_LOAD_ENABLED:
				setPreLoadEnabled((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PRE_LOAD_FETCH_SIZE:
				setPreLoadFetchSize((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PRE_LOAD_HANDLES:
				setPreLoadHandles((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PRE_PROCESSOR:
				setPreProcessor((String)newValue);
				return;
			case CddPackage.CDD_ROOT__PRIMARY_CONNECTION:
				setPrimaryConnection((ConnectionConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PRIORITY:
				setPriority((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PROCESS:
				setProcess((ProcessConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PROCESS_AGENT_CLASS:
				setProcessAgentClass((ProcessAgentClassConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PROCESS_ENGINE:
				setProcessEngine((ProcessEngineConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PROCESSES:
				setProcesses((ProcessesConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PROCESS_GROUPS:
				setProcessGroups((ProcessGroupsConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PROCESSING_UNIT:
				setProcessingUnit((ProcessingUnitConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PROCESSING_UNITS:
				setProcessingUnits((ProcessingUnitsConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PROJECTION:
				setProjection((ProjectionConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PROPERTY:
				setProperty((PropertyConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PROPERTY_CACHE_SIZE:
				setPropertyCacheSize((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PROPERTY_CHECK_INTERVAL:
				setPropertyCheckInterval((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PROPERTY_GROUP:
				setPropertyGroup((PropertyGroupConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PROTOCOL:
				setProtocol((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PROTOCOLS:
				setProtocols((ProtocolsConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PROTOCOL_TIMEOUT:
				setProtocolTimeout((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PROVIDER:
				setProvider((ProviderConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__QUERY_AGENT_CLASS:
				setQueryAgentClass((QueryAgentClassConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__QUEUE_SIZE:
				setQueueSize((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__READ_TIMEOUT:
				setReadTimeout((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__RECEIVER:
				setReceiver((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__REMOTE_LISTEN_URL:
				setRemoteListenUrl((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__REQUESTER:
				setRequester((SecurityRequester)newValue);
				return;
			case CddPackage.CDD_ROOT__RETRY_COUNT:
				setRetryCount((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__REVERSE_REFERENCES:
				setReverseReferences((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__REVISION:
				setRevision((RevisionConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__ROLE:
				setRole((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__ROLES:
				setRoles((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__ROUTER:
				setRouter((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__RULE:
				setRule((RuleConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__RULE_CONFIG:
				setRuleConfig((RuleConfigConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__RULES:
				setRules((RulesConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__RULESETS:
				setRulesets((RulesetsConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__SECONDARY_CONNECTION:
				setSecondaryConnection((ConnectionConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__SECURITY_CONFIG:
				setSecurityConfig((SecurityConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__SERVICE:
				setService((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__SET_PROPERTY:
				setSetProperty((SetPropertyConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__SHARED_ALL:
				setSharedAll((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__SHARED_QUEUE:
				setSharedQueue((SharedQueueConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__SHOUTDOWN_WAIT:
				setShoutdownWait((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__SHUTDOWN:
				setShutdown((FunctionsConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__SIZE:
				setSize((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__SKIP_RECOVERY:
				setSkipRecovery((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__SOCKET_OUTPUT_BUFFER_SIZE:
				setSocketOutputBufferSize((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__SSL:
				setSsl((SslConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__STALE_CONNECTION_CHECK:
				setStaleConnectionCheck((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__STARTUP:
				setStartup((FunctionsConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__STRATEGY:
				setStrategy((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__SUBJECT:
				setSubject((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__SUBSCRIBE:
				setSubscribe((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__SYS_ERR_REDIRECT:
				setSysErrRedirect((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__SYS_OUT_REDIRECT:
				setSysOutRedirect((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__TABLE_NAME:
				setTableName((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__TCP_NO_DELAY:
				setTcpNoDelay((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__TERMINAL:
				setTerminal((TerminalConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__THREAD_AFFINITY_RULE_FUNCTION:
				setThreadAffinityRuleFunction((String)newValue);
				return;
			case CddPackage.CDD_ROOT__THREAD_COUNT:
				setThreadCount((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__THREADING_MODEL:
				setThreadingModel((ThreadingModelConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__TOKEN_FILE:
				setTokenFile((SecurityOverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__TRUST_MANAGER_ALGORITHM:
				setTrustManagerAlgorithm((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__TTL:
				setTtl((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__TYPE:
				setType((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__URI:
				setUri((String)newValue);
				return;
			case CddPackage.CDD_ROOT__USER_NAME:
				setUserName((SecurityOverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__USER_PASSWORD:
				setUserPassword((SecurityOverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__VERSION:
				setVersion((String)newValue);
				return;
			case CddPackage.CDD_ROOT__WAIT_TIMEOUT:
				setWaitTimeout((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__WORKERS:
				setWorkers((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__WORKERTHREADS_COUNT:
				setWorkerthreadsCount((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__WRITE_TIMEOUT:
				setWriteTimeout((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__ENTITY:
				setEntity((EntityConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__FILTER:
				setFilter((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__ENTITY_SET:
				setEntitySet((EntitySetConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__ENABLE_TABLE_TRIMMING:
				setEnableTableTrimming((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__TRIMMING_FIELD:
				setTrimmingField((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__TRIMMING_RULE:
				setTrimmingRule((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__GENERATE_LV_FILES:
				setGenerateLVFiles((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__OUTPUT_PATH:
				setOutputPath((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__LDM_CONNECTION:
				setLdmConnection((LDMConnectionConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__LDM_URL:
				setLdmUrl((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PUBLISHER:
				setPublisher((PublisherConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PUBLISHER_QUEUE_SIZE:
				setPublisherQueueSize((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__PUBLISHER_THREAD_COUNT:
				setPublisherThreadCount((OverrideConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__LIVEVIEW_AGENT_CLASS:
				setLiveviewAgentClass((LiveViewAgentClassConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__COMPOSITE_INDEXES:
				setCompositeIndexes((CompositeIndexesConfig)newValue);
				return;
			case CddPackage.CDD_ROOT__COMPOSITE_INDEX:
				setCompositeIndex((CompositeIndexConfig)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case CddPackage.CDD_ROOT__MIXED:
				getMixed().clear();
				return;
			case CddPackage.CDD_ROOT__XMLNS_PREFIX_MAP:
				getXMLNSPrefixMap().clear();
				return;
			case CddPackage.CDD_ROOT__XSI_SCHEMA_LOCATION:
				getXSISchemaLocation().clear();
				return;
			case CddPackage.CDD_ROOT__ACCEPT_COUNT:
				setAcceptCount((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__ADDRESS:
				setAddress((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__ADHOC_CONFIG:
				setAdhocConfig((LoadBalancerAdhocConfigConfig)null);
				return;
			case CddPackage.CDD_ROOT__ADHOC_CONFIGS:
				setAdhocConfigs((LoadBalancerAdhocConfigsConfig)null);
				return;
			case CddPackage.CDD_ROOT__AGENT:
				setAgent((AgentConfig)null);
				return;
			case CddPackage.CDD_ROOT__AGENT_CLASSES:
				setAgentClasses((AgentClassesConfig)null);
				return;
			case CddPackage.CDD_ROOT__AGENTS:
				setAgents((AgentsConfig)null);
				return;
			case CddPackage.CDD_ROOT__ALERT_CONFIG:
				setAlertConfig((AlertConfigConfig)null);
				return;
			case CddPackage.CDD_ROOT__ALERT_CONFIG_SET:
				setAlertConfigSet((AlertConfigSetConfig)null);
				return;
			case CddPackage.CDD_ROOT__APPEND:
				setAppend((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__ARG:
				setArg((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__AUTHOR:
				setAuthor((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__BACKING_STORE:
				setBackingStore((BackingStoreConfig)null);
				return;
			case CddPackage.CDD_ROOT__BACKUP_COPIES:
				setBackupCopies((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__BUSINESSWORKS:
				setBusinessworks((BusinessworksConfig)null);
				return;
			case CddPackage.CDD_ROOT__CACHE:
				setCache((CacheAgentClassConfig)null);
				return;
			case CddPackage.CDD_ROOT__CACHE_AGENT_CLASS:
				setCacheAgentClass((CacheAgentClassConfig)null);
				return;
			case CddPackage.CDD_ROOT__CACHE_AGENT_QUORUM:
				setCacheAgentQuorum((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__CACHE_ASIDE:
				setCacheAside((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__CACHE_LIMITED:
				setCacheLimited((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__CACHE_LOADER_CLASS:
				setCacheLoaderClass((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__CACHE_MANAGER:
				setCacheManager((CacheManagerConfig)null);
				return;
			case CddPackage.CDD_ROOT__CACHE_MODE:
				setCacheMode(CACHE_MODE_EDEFAULT);
				return;
			case CddPackage.CDD_ROOT__CACHE_STORAGE_ENABLED:
				setCacheStorageEnabled((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__CERTIFICATE_KEY_FILE:
				setCertificateKeyFile((SecurityOverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__CHANNEL:
				setChannel((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__CHECK_FOR_DUPLICATES:
				setCheckForDuplicates((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__CHECK_FOR_VERSION:
				setCheckForVersion((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__CHECK_INTERVAL:
				setCheckInterval((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__CHECKPOINT_INTERVAL:
				setCheckpointInterval((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__CHECKPOINT_OPS_LIMIT:
				setCheckpointOpsLimit((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__CHILD_CLUSTER_MEMBER:
				setChildClusterMember((ChildClusterMemberConfig)null);
				return;
			case CddPackage.CDD_ROOT__CIPHER:
				setCipher((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__CIPHERS:
				setCiphers((CiphersConfig)null);
				return;
			case CddPackage.CDD_ROOT__CLASS:
				setClass((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__CLUSTER:
				setCluster((ClusterConfig)null);
				return;
			case CddPackage.CDD_ROOT__CLUSTER_MEMBER:
				setClusterMember((ClusterMemberConfig)null);
				return;
			case CddPackage.CDD_ROOT__COMMENT:
				setComment((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__CONCURRENT_RTC:
				setConcurrentRtc((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__CONDITION:
				setCondition((ConditionConfig)null);
				return;
			case CddPackage.CDD_ROOT__CONNECTION_LINGER:
				setConnectionLinger((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__CONNECTION_TIMEOUT:
				setConnectionTimeout((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__CONSTANT:
				setConstant((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__CONTROLLER:
				setController((SecurityController)null);
				return;
			case CddPackage.CDD_ROOT__DAEMON:
				setDaemon((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__DASHBOARD_AGENT_CLASS:
				setDashboardAgentClass((DashboardAgentClassConfig)null);
				return;
			case CddPackage.CDD_ROOT__DATA_STORE_PATH:
				setDataStorePath((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__DATE:
				setDate((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__DB_CONCEPTS:
				setDbConcepts((DbConceptsConfig)null);
				return;
			case CddPackage.CDD_ROOT__DB_DIR:
				setDbDir((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__DB_URIS:
				setDbUris((UrisConfig)null);
				return;
			case CddPackage.CDD_ROOT__DEFAULT_MODE:
				setDefaultMode(DEFAULT_MODE_EDEFAULT);
				return;
			case CddPackage.CDD_ROOT__DELETE_RETRACTED:
				setDeleteRetracted((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__DESTINATION:
				setDestination((DestinationConfig)null);
				return;
			case CddPackage.CDD_ROOT__DESTINATION_GROUPS:
				setDestinationGroups((DestinationGroupsConfig)null);
				return;
			case CddPackage.CDD_ROOT__DESTINATIONS:
				setDestinations((DestinationsConfig)null);
				return;
			case CddPackage.CDD_ROOT__DIR:
				setDir((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__DISCOVERY_URL:
				setDiscoveryUrl((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__DOCUMENT_PAGE:
				setDocumentPage((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__DOCUMENT_ROOT:
				setDocumentRoot((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__DOMAIN_NAME:
				setDomainName((SecurityOverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__DOMAIN_OBJECT:
				setDomainObject((DomainObjectConfig)null);
				return;
			case CddPackage.CDD_ROOT__DOMAIN_OBJECTS:
				setDomainObjects((DomainObjectsConfig)null);
				return;
			case CddPackage.CDD_ROOT__ENABLED:
				setEnabled((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__ENABLE_TRACKING:
				setEnableTracking((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__ENCODING:
				setEncoding((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__ENCRYPTION:
				setEncryption((FieldEncryptionConfig)null);
				return;
			case CddPackage.CDD_ROOT__ENFORCE_POOLS:
				setEnforcePools((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__ENTITY_CACHE_SIZE:
				setEntityCacheSize((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__EVICTION:
				setEviction((EvictionConfig)null);
				return;
			case CddPackage.CDD_ROOT__EVICT_ON_UPDATE:
				setEvictOnUpdate((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__EXPLICIT_TUPLE:
				setExplicitTuple((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__FILES:
				setFiles((FilesConfig)null);
				return;
			case CddPackage.CDD_ROOT__FUNCTION_GROUPS:
				setFunctionGroups((FunctionGroupsConfig)null);
				return;
			case CddPackage.CDD_ROOT__FUNCTIONS:
				setFunctions((FunctionsConfig)null);
				return;
			case CddPackage.CDD_ROOT__GET_PROPERTY:
				setGetProperty((GetPropertyConfig)null);
				return;
			case CddPackage.CDD_ROOT__HOT_DEPLOY:
				setHotDeploy((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__HTTP:
				setHttp((HttpConfig)null);
				return;
			case CddPackage.CDD_ROOT__IDENTITY_PASSWORD:
				setIdentityPassword((SecurityOverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__INACTIVITY_TIMEOUT:
				setInactivityTimeout((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__INDEX:
				setIndex((IndexConfig)null);
				return;
			case CddPackage.CDD_ROOT__INDEXES:
				setIndexes((IndexesConfig)null);
				return;
			case CddPackage.CDD_ROOT__INFERENCE_AGENT_CLASS:
				setInferenceAgentClass((InferenceAgentClassConfig)null);
				return;
			case CddPackage.CDD_ROOT__INFERENCE_ENGINE:
				setInferenceEngine((InferenceEngineConfig)null);
				return;
			case CddPackage.CDD_ROOT__INITIAL_SIZE:
				setInitialSize((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__JOB_MANAGER:
				setJobManager((JobManagerConfig)null);
				return;
			case CddPackage.CDD_ROOT__JOB_POOL_QUEUE_SIZE:
				setJobPoolQueueSize((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__JOB_POOL_THREAD_COUNT:
				setJobPoolThreadCount((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__KEY:
				setKey((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__KEY_MANAGER_ALGORITHM:
				setKeyManagerAlgorithm((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__LINE_LAYOUT:
				setLineLayout((LineLayoutConfig)null);
				return;
			case CddPackage.CDD_ROOT__LISTEN_URL:
				setListenUrl((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__LOAD:
				setLoad((LoadConfig)null);
				return;
			case CddPackage.CDD_ROOT__LOAD_BALANCER_CONFIGS:
				setLoadBalancerConfigs((LoadBalancerConfigsConfig)null);
				return;
			case CddPackage.CDD_ROOT__LOCAL_CACHE:
				setLocalCache((LocalCacheConfig)null);
				return;
			case CddPackage.CDD_ROOT__LOCK_TIMEOUT:
				setLockTimeout((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__LOG_CONFIG:
				setLogConfig((LogConfigConfig)null);
				return;
			case CddPackage.CDD_ROOT__LOG_CONFIGS:
				setLogConfigs((LogConfigsConfig)null);
				return;
			case CddPackage.CDD_ROOT__MAX_ACTIVE:
				setMaxActive((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__MAX_NUMBER:
				setMaxNumber((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__MAX_PROCESSORS:
				setMaxProcessors((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__MAX_SIZE:
				setMaxSize((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__MAX_TIME:
				setMaxTime((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__MEMORY_MANAGER:
				setMemoryManager((MemoryManagerConfig)null);
				return;
			case CddPackage.CDD_ROOT__MESSAGE_ENCODING:
				setMessageEncoding(MESSAGE_ENCODING_EDEFAULT);
				return;
			case CddPackage.CDD_ROOT__MIN_SIZE:
				setMinSize((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__MM_ACTION:
				setMmAction((MmActionConfig)null);
				return;
			case CddPackage.CDD_ROOT__MM_ACTION_CONFIG:
				setMmActionConfig((MmActionConfigConfig)null);
				return;
			case CddPackage.CDD_ROOT__MM_ACTION_CONFIG_SET:
				setMmActionConfigSet((MmActionConfigSetConfig)null);
				return;
			case CddPackage.CDD_ROOT__MM_AGENT_CLASS:
				setMmAgentClass((MmAgentClassConfig)null);
				return;
			case CddPackage.CDD_ROOT__MM_ALERT:
				setMmAlert((MmAlertConfig)null);
				return;
			case CddPackage.CDD_ROOT__MM_EXECUTE_COMMAND:
				setMmExecuteCommand((MmExecuteCommandConfig)null);
				return;
			case CddPackage.CDD_ROOT__MM_HEALTH_LEVEL:
				setMmHealthLevel((MmHealthLevelConfig)null);
				return;
			case CddPackage.CDD_ROOT__MM_SEND_EMAIL:
				setMmSendEmail((MmSendEmailConfig)null);
				return;
			case CddPackage.CDD_ROOT__MM_TRIGGER_CONDITION:
				setMmTriggerCondition((MmTriggerConditionConfig)null);
				return;
			case CddPackage.CDD_ROOT__MODE:
				setMode(MODE_EDEFAULT);
				return;
			case CddPackage.CDD_ROOT__NAME:
				setName((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__NOTIFICATION:
				setNotification((NotificationConfig)null);
				return;
			case CddPackage.CDD_ROOT__OBJECT_MANAGEMENT:
				setObjectManagement((ObjectManagementConfig)null);
				return;
			case CddPackage.CDD_ROOT__OBJECT_TABLE:
				setObjectTable((ObjectTableConfig)null);
				return;
			case CddPackage.CDD_ROOT__PAIR_CONFIG:
				setPairConfig((LoadBalancerPairConfigConfig)null);
				return;
			case CddPackage.CDD_ROOT__PAIR_CONFIGS:
				setPairConfigs((LoadBalancerPairConfigsConfig)null);
				return;
			case CddPackage.CDD_ROOT__PARALLEL_RECOVERY:
				setParallelRecovery((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__PERSISTENCE_OPTION:
				setPersistenceOption((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__PERSISTENCE_POLICY:
				setPersistencePolicy((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__POLICY_FILE:
				setPolicyFile((SecurityOverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__PORT:
				setPort((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__PRE_LOAD_CACHES:
				setPreLoadCaches((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__PRE_LOAD_ENABLED:
				setPreLoadEnabled((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__PRE_LOAD_FETCH_SIZE:
				setPreLoadFetchSize((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__PRE_LOAD_HANDLES:
				setPreLoadHandles((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__PRE_PROCESSOR:
				setPreProcessor(PRE_PROCESSOR_EDEFAULT);
				return;
			case CddPackage.CDD_ROOT__PRIMARY_CONNECTION:
				setPrimaryConnection((ConnectionConfig)null);
				return;
			case CddPackage.CDD_ROOT__PRIORITY:
				setPriority((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__PROCESS:
				setProcess((ProcessConfig)null);
				return;
			case CddPackage.CDD_ROOT__PROCESS_AGENT_CLASS:
				setProcessAgentClass((ProcessAgentClassConfig)null);
				return;
			case CddPackage.CDD_ROOT__PROCESS_ENGINE:
				setProcessEngine((ProcessEngineConfig)null);
				return;
			case CddPackage.CDD_ROOT__PROCESSES:
				setProcesses((ProcessesConfig)null);
				return;
			case CddPackage.CDD_ROOT__PROCESS_GROUPS:
				setProcessGroups((ProcessGroupsConfig)null);
				return;
			case CddPackage.CDD_ROOT__PROCESSING_UNIT:
				setProcessingUnit((ProcessingUnitConfig)null);
				return;
			case CddPackage.CDD_ROOT__PROCESSING_UNITS:
				setProcessingUnits((ProcessingUnitsConfig)null);
				return;
			case CddPackage.CDD_ROOT__PROJECTION:
				setProjection((ProjectionConfig)null);
				return;
			case CddPackage.CDD_ROOT__PROPERTY:
				setProperty((PropertyConfig)null);
				return;
			case CddPackage.CDD_ROOT__PROPERTY_CACHE_SIZE:
				setPropertyCacheSize((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__PROPERTY_CHECK_INTERVAL:
				setPropertyCheckInterval((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__PROPERTY_GROUP:
				setPropertyGroup((PropertyGroupConfig)null);
				return;
			case CddPackage.CDD_ROOT__PROTOCOL:
				setProtocol((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__PROTOCOLS:
				setProtocols((ProtocolsConfig)null);
				return;
			case CddPackage.CDD_ROOT__PROTOCOL_TIMEOUT:
				setProtocolTimeout((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__PROVIDER:
				setProvider((ProviderConfig)null);
				return;
			case CddPackage.CDD_ROOT__QUERY_AGENT_CLASS:
				setQueryAgentClass((QueryAgentClassConfig)null);
				return;
			case CddPackage.CDD_ROOT__QUEUE_SIZE:
				setQueueSize((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__READ_TIMEOUT:
				setReadTimeout((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__RECEIVER:
				setReceiver((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__REMOTE_LISTEN_URL:
				setRemoteListenUrl((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__REQUESTER:
				setRequester((SecurityRequester)null);
				return;
			case CddPackage.CDD_ROOT__RETRY_COUNT:
				setRetryCount((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__REVERSE_REFERENCES:
				setReverseReferences((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__REVISION:
				setRevision((RevisionConfig)null);
				return;
			case CddPackage.CDD_ROOT__ROLE:
				setRole((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__ROLES:
				setRoles((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__ROUTER:
				setRouter((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__RULE:
				setRule((RuleConfig)null);
				return;
			case CddPackage.CDD_ROOT__RULE_CONFIG:
				setRuleConfig((RuleConfigConfig)null);
				return;
			case CddPackage.CDD_ROOT__RULES:
				setRules((RulesConfig)null);
				return;
			case CddPackage.CDD_ROOT__RULESETS:
				setRulesets((RulesetsConfig)null);
				return;
			case CddPackage.CDD_ROOT__SECONDARY_CONNECTION:
				setSecondaryConnection((ConnectionConfig)null);
				return;
			case CddPackage.CDD_ROOT__SECURITY_CONFIG:
				setSecurityConfig((SecurityConfig)null);
				return;
			case CddPackage.CDD_ROOT__SERVICE:
				setService((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__SET_PROPERTY:
				setSetProperty((SetPropertyConfig)null);
				return;
			case CddPackage.CDD_ROOT__SHARED_ALL:
				setSharedAll((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__SHARED_QUEUE:
				setSharedQueue((SharedQueueConfig)null);
				return;
			case CddPackage.CDD_ROOT__SHOUTDOWN_WAIT:
				setShoutdownWait((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__SHUTDOWN:
				setShutdown((FunctionsConfig)null);
				return;
			case CddPackage.CDD_ROOT__SIZE:
				setSize((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__SKIP_RECOVERY:
				setSkipRecovery((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__SOCKET_OUTPUT_BUFFER_SIZE:
				setSocketOutputBufferSize((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__SSL:
				setSsl((SslConfig)null);
				return;
			case CddPackage.CDD_ROOT__STALE_CONNECTION_CHECK:
				setStaleConnectionCheck((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__STARTUP:
				setStartup((FunctionsConfig)null);
				return;
			case CddPackage.CDD_ROOT__STRATEGY:
				setStrategy((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__SUBJECT:
				setSubject((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__SUBSCRIBE:
				setSubscribe((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__SYS_ERR_REDIRECT:
				setSysErrRedirect((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__SYS_OUT_REDIRECT:
				setSysOutRedirect((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__TABLE_NAME:
				setTableName((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__TCP_NO_DELAY:
				setTcpNoDelay((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__TERMINAL:
				setTerminal((TerminalConfig)null);
				return;
			case CddPackage.CDD_ROOT__THREAD_AFFINITY_RULE_FUNCTION:
				setThreadAffinityRuleFunction(THREAD_AFFINITY_RULE_FUNCTION_EDEFAULT);
				return;
			case CddPackage.CDD_ROOT__THREAD_COUNT:
				setThreadCount((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__THREADING_MODEL:
				setThreadingModel(THREADING_MODEL_EDEFAULT);
				return;
			case CddPackage.CDD_ROOT__TOKEN_FILE:
				setTokenFile((SecurityOverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__TRUST_MANAGER_ALGORITHM:
				setTrustManagerAlgorithm((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__TTL:
				setTtl((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__TYPE:
				setType((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__URI:
				setUri(URI_EDEFAULT);
				return;
			case CddPackage.CDD_ROOT__USER_NAME:
				setUserName((SecurityOverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__USER_PASSWORD:
				setUserPassword((SecurityOverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case CddPackage.CDD_ROOT__WAIT_TIMEOUT:
				setWaitTimeout((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__WORKERS:
				setWorkers((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__WORKERTHREADS_COUNT:
				setWorkerthreadsCount((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__WRITE_TIMEOUT:
				setWriteTimeout((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__ENTITY:
				setEntity((EntityConfig)null);
				return;
			case CddPackage.CDD_ROOT__FILTER:
				setFilter((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__ENTITY_SET:
				setEntitySet((EntitySetConfig)null);
				return;
			case CddPackage.CDD_ROOT__ENABLE_TABLE_TRIMMING:
				setEnableTableTrimming((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__TRIMMING_FIELD:
				setTrimmingField((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__TRIMMING_RULE:
				setTrimmingRule((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__GENERATE_LV_FILES:
				setGenerateLVFiles((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__OUTPUT_PATH:
				setOutputPath((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__LDM_CONNECTION:
				setLdmConnection((LDMConnectionConfig)null);
				return;
			case CddPackage.CDD_ROOT__LDM_URL:
				setLdmUrl((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__PUBLISHER:
				setPublisher((PublisherConfig)null);
				return;
			case CddPackage.CDD_ROOT__PUBLISHER_QUEUE_SIZE:
				setPublisherQueueSize((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__PUBLISHER_THREAD_COUNT:
				setPublisherThreadCount((OverrideConfig)null);
				return;
			case CddPackage.CDD_ROOT__LIVEVIEW_AGENT_CLASS:
				setLiveviewAgentClass((LiveViewAgentClassConfig)null);
				return;
			case CddPackage.CDD_ROOT__COMPOSITE_INDEXES:
				setCompositeIndexes((CompositeIndexesConfig)null);
				return;
			case CddPackage.CDD_ROOT__COMPOSITE_INDEX:
				setCompositeIndex((CompositeIndexConfig)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CddPackage.CDD_ROOT__MIXED:
				return mixed != null && !mixed.isEmpty();
			case CddPackage.CDD_ROOT__XMLNS_PREFIX_MAP:
				return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
			case CddPackage.CDD_ROOT__XSI_SCHEMA_LOCATION:
				return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
			case CddPackage.CDD_ROOT__ACCEPT_COUNT:
				return getAcceptCount() != null;
			case CddPackage.CDD_ROOT__ADDRESS:
				return getAddress() != null;
			case CddPackage.CDD_ROOT__ADHOC_CONFIG:
				return getAdhocConfig() != null;
			case CddPackage.CDD_ROOT__ADHOC_CONFIGS:
				return getAdhocConfigs() != null;
			case CddPackage.CDD_ROOT__AGENT:
				return getAgent() != null;
			case CddPackage.CDD_ROOT__AGENT_CLASSES:
				return getAgentClasses() != null;
			case CddPackage.CDD_ROOT__AGENTS:
				return getAgents() != null;
			case CddPackage.CDD_ROOT__ALERT_CONFIG:
				return getAlertConfig() != null;
			case CddPackage.CDD_ROOT__ALERT_CONFIG_SET:
				return getAlertConfigSet() != null;
			case CddPackage.CDD_ROOT__APPEND:
				return getAppend() != null;
			case CddPackage.CDD_ROOT__ARG:
				return getArg() != null;
			case CddPackage.CDD_ROOT__AUTHOR:
				return getAuthor() != null;
			case CddPackage.CDD_ROOT__BACKING_STORE:
				return getBackingStore() != null;
			case CddPackage.CDD_ROOT__BACKUP_COPIES:
				return getBackupCopies() != null;
			case CddPackage.CDD_ROOT__BUSINESSWORKS:
				return getBusinessworks() != null;
			case CddPackage.CDD_ROOT__CACHE:
				return getCache() != null;
			case CddPackage.CDD_ROOT__CACHE_AGENT_CLASS:
				return getCacheAgentClass() != null;
			case CddPackage.CDD_ROOT__CACHE_AGENT_QUORUM:
				return getCacheAgentQuorum() != null;
			case CddPackage.CDD_ROOT__CACHE_ASIDE:
				return getCacheAside() != null;
			case CddPackage.CDD_ROOT__CACHE_LIMITED:
				return getCacheLimited() != null;
			case CddPackage.CDD_ROOT__CACHE_LOADER_CLASS:
				return getCacheLoaderClass() != null;
			case CddPackage.CDD_ROOT__CACHE_MANAGER:
				return getCacheManager() != null;
			case CddPackage.CDD_ROOT__CACHE_MODE:
				return getCacheMode() != CACHE_MODE_EDEFAULT;
			case CddPackage.CDD_ROOT__CACHE_STORAGE_ENABLED:
				return getCacheStorageEnabled() != null;
			case CddPackage.CDD_ROOT__CERTIFICATE_KEY_FILE:
				return getCertificateKeyFile() != null;
			case CddPackage.CDD_ROOT__CHANNEL:
				return getChannel() != null;
			case CddPackage.CDD_ROOT__CHECK_FOR_DUPLICATES:
				return getCheckForDuplicates() != null;
			case CddPackage.CDD_ROOT__CHECK_FOR_VERSION:
				return getCheckForVersion() != null;
			case CddPackage.CDD_ROOT__CHECK_INTERVAL:
				return getCheckInterval() != null;
			case CddPackage.CDD_ROOT__CHECKPOINT_INTERVAL:
				return getCheckpointInterval() != null;
			case CddPackage.CDD_ROOT__CHECKPOINT_OPS_LIMIT:
				return getCheckpointOpsLimit() != null;
			case CddPackage.CDD_ROOT__CHILD_CLUSTER_MEMBER:
				return getChildClusterMember() != null;
			case CddPackage.CDD_ROOT__CIPHER:
				return getCipher() != null;
			case CddPackage.CDD_ROOT__CIPHERS:
				return getCiphers() != null;
			case CddPackage.CDD_ROOT__CLASS:
				return getClass_() != null;
			case CddPackage.CDD_ROOT__CLUSTER:
				return getCluster() != null;
			case CddPackage.CDD_ROOT__CLUSTER_MEMBER:
				return getClusterMember() != null;
			case CddPackage.CDD_ROOT__COMMENT:
				return getComment() != null;
			case CddPackage.CDD_ROOT__CONCURRENT_RTC:
				return getConcurrentRtc() != null;
			case CddPackage.CDD_ROOT__CONDITION:
				return getCondition() != null;
			case CddPackage.CDD_ROOT__CONNECTION_LINGER:
				return getConnectionLinger() != null;
			case CddPackage.CDD_ROOT__CONNECTION_TIMEOUT:
				return getConnectionTimeout() != null;
			case CddPackage.CDD_ROOT__CONSTANT:
				return getConstant() != null;
			case CddPackage.CDD_ROOT__CONTROLLER:
				return getController() != null;
			case CddPackage.CDD_ROOT__DAEMON:
				return getDaemon() != null;
			case CddPackage.CDD_ROOT__DASHBOARD_AGENT_CLASS:
				return getDashboardAgentClass() != null;
			case CddPackage.CDD_ROOT__DATA_STORE_PATH:
				return getDataStorePath() != null;
			case CddPackage.CDD_ROOT__DATE:
				return getDate() != null;
			case CddPackage.CDD_ROOT__DB_CONCEPTS:
				return getDbConcepts() != null;
			case CddPackage.CDD_ROOT__DB_DIR:
				return getDbDir() != null;
			case CddPackage.CDD_ROOT__DB_URIS:
				return getDbUris() != null;
			case CddPackage.CDD_ROOT__DEFAULT_MODE:
				return getDefaultMode() != DEFAULT_MODE_EDEFAULT;
			case CddPackage.CDD_ROOT__DELETE_RETRACTED:
				return getDeleteRetracted() != null;
			case CddPackage.CDD_ROOT__DESTINATION:
				return getDestination() != null;
			case CddPackage.CDD_ROOT__DESTINATION_GROUPS:
				return getDestinationGroups() != null;
			case CddPackage.CDD_ROOT__DESTINATIONS:
				return getDestinations() != null;
			case CddPackage.CDD_ROOT__DIR:
				return getDir() != null;
			case CddPackage.CDD_ROOT__DISCOVERY_URL:
				return getDiscoveryUrl() != null;
			case CddPackage.CDD_ROOT__DOCUMENT_PAGE:
				return getDocumentPage() != null;
			case CddPackage.CDD_ROOT__DOCUMENT_ROOT:
				return getDocumentRoot() != null;
			case CddPackage.CDD_ROOT__DOMAIN_NAME:
				return getDomainName() != null;
			case CddPackage.CDD_ROOT__DOMAIN_OBJECT:
				return getDomainObject() != null;
			case CddPackage.CDD_ROOT__DOMAIN_OBJECTS:
				return getDomainObjects() != null;
			case CddPackage.CDD_ROOT__ENABLED:
				return getEnabled() != null;
			case CddPackage.CDD_ROOT__ENABLE_TRACKING:
				return getEnableTracking() != null;
			case CddPackage.CDD_ROOT__ENCODING:
				return getEncoding() != null;
			case CddPackage.CDD_ROOT__ENCRYPTION:
				return getEncryption() != null;
			case CddPackage.CDD_ROOT__ENFORCE_POOLS:
				return getEnforcePools() != null;
			case CddPackage.CDD_ROOT__ENTITY_CACHE_SIZE:
				return getEntityCacheSize() != null;
			case CddPackage.CDD_ROOT__EVICTION:
				return getEviction() != null;
			case CddPackage.CDD_ROOT__EVICT_ON_UPDATE:
				return getEvictOnUpdate() != null;
			case CddPackage.CDD_ROOT__EXPLICIT_TUPLE:
				return getExplicitTuple() != null;
			case CddPackage.CDD_ROOT__FILES:
				return getFiles() != null;
			case CddPackage.CDD_ROOT__FUNCTION_GROUPS:
				return getFunctionGroups() != null;
			case CddPackage.CDD_ROOT__FUNCTIONS:
				return getFunctions() != null;
			case CddPackage.CDD_ROOT__GET_PROPERTY:
				return getGetProperty() != null;
			case CddPackage.CDD_ROOT__HOT_DEPLOY:
				return getHotDeploy() != null;
			case CddPackage.CDD_ROOT__HTTP:
				return getHttp() != null;
			case CddPackage.CDD_ROOT__IDENTITY_PASSWORD:
				return getIdentityPassword() != null;
			case CddPackage.CDD_ROOT__INACTIVITY_TIMEOUT:
				return getInactivityTimeout() != null;
			case CddPackage.CDD_ROOT__INDEX:
				return getIndex() != null;
			case CddPackage.CDD_ROOT__INDEXES:
				return getIndexes() != null;
			case CddPackage.CDD_ROOT__INFERENCE_AGENT_CLASS:
				return getInferenceAgentClass() != null;
			case CddPackage.CDD_ROOT__INFERENCE_ENGINE:
				return getInferenceEngine() != null;
			case CddPackage.CDD_ROOT__INITIAL_SIZE:
				return getInitialSize() != null;
			case CddPackage.CDD_ROOT__JOB_MANAGER:
				return getJobManager() != null;
			case CddPackage.CDD_ROOT__JOB_POOL_QUEUE_SIZE:
				return getJobPoolQueueSize() != null;
			case CddPackage.CDD_ROOT__JOB_POOL_THREAD_COUNT:
				return getJobPoolThreadCount() != null;
			case CddPackage.CDD_ROOT__KEY:
				return getKey() != null;
			case CddPackage.CDD_ROOT__KEY_MANAGER_ALGORITHM:
				return getKeyManagerAlgorithm() != null;
			case CddPackage.CDD_ROOT__LINE_LAYOUT:
				return getLineLayout() != null;
			case CddPackage.CDD_ROOT__LISTEN_URL:
				return getListenUrl() != null;
			case CddPackage.CDD_ROOT__LOAD:
				return getLoad() != null;
			case CddPackage.CDD_ROOT__LOAD_BALANCER_CONFIGS:
				return getLoadBalancerConfigs() != null;
			case CddPackage.CDD_ROOT__LOCAL_CACHE:
				return getLocalCache() != null;
			case CddPackage.CDD_ROOT__LOCK_TIMEOUT:
				return getLockTimeout() != null;
			case CddPackage.CDD_ROOT__LOG_CONFIG:
				return getLogConfig() != null;
			case CddPackage.CDD_ROOT__LOG_CONFIGS:
				return getLogConfigs() != null;
			case CddPackage.CDD_ROOT__MAX_ACTIVE:
				return getMaxActive() != null;
			case CddPackage.CDD_ROOT__MAX_NUMBER:
				return getMaxNumber() != null;
			case CddPackage.CDD_ROOT__MAX_PROCESSORS:
				return getMaxProcessors() != null;
			case CddPackage.CDD_ROOT__MAX_SIZE:
				return getMaxSize() != null;
			case CddPackage.CDD_ROOT__MAX_TIME:
				return getMaxTime() != null;
			case CddPackage.CDD_ROOT__MEMORY_MANAGER:
				return getMemoryManager() != null;
			case CddPackage.CDD_ROOT__MESSAGE_ENCODING:
				return MESSAGE_ENCODING_EDEFAULT == null ? getMessageEncoding() != null : !MESSAGE_ENCODING_EDEFAULT.equals(getMessageEncoding());
			case CddPackage.CDD_ROOT__MIN_SIZE:
				return getMinSize() != null;
			case CddPackage.CDD_ROOT__MM_ACTION:
				return getMmAction() != null;
			case CddPackage.CDD_ROOT__MM_ACTION_CONFIG:
				return getMmActionConfig() != null;
			case CddPackage.CDD_ROOT__MM_ACTION_CONFIG_SET:
				return getMmActionConfigSet() != null;
			case CddPackage.CDD_ROOT__MM_AGENT_CLASS:
				return getMmAgentClass() != null;
			case CddPackage.CDD_ROOT__MM_ALERT:
				return getMmAlert() != null;
			case CddPackage.CDD_ROOT__MM_EXECUTE_COMMAND:
				return getMmExecuteCommand() != null;
			case CddPackage.CDD_ROOT__MM_HEALTH_LEVEL:
				return getMmHealthLevel() != null;
			case CddPackage.CDD_ROOT__MM_SEND_EMAIL:
				return getMmSendEmail() != null;
			case CddPackage.CDD_ROOT__MM_TRIGGER_CONDITION:
				return getMmTriggerCondition() != null;
			case CddPackage.CDD_ROOT__MODE:
				return getMode() != MODE_EDEFAULT;
			case CddPackage.CDD_ROOT__NAME:
				return getName() != null;
			case CddPackage.CDD_ROOT__NOTIFICATION:
				return getNotification() != null;
			case CddPackage.CDD_ROOT__OBJECT_MANAGEMENT:
				return getObjectManagement() != null;
			case CddPackage.CDD_ROOT__OBJECT_TABLE:
				return getObjectTable() != null;
			case CddPackage.CDD_ROOT__PAIR_CONFIG:
				return getPairConfig() != null;
			case CddPackage.CDD_ROOT__PAIR_CONFIGS:
				return getPairConfigs() != null;
			case CddPackage.CDD_ROOT__PARALLEL_RECOVERY:
				return getParallelRecovery() != null;
			case CddPackage.CDD_ROOT__PERSISTENCE_OPTION:
				return getPersistenceOption() != null;
			case CddPackage.CDD_ROOT__PERSISTENCE_POLICY:
				return getPersistencePolicy() != null;
			case CddPackage.CDD_ROOT__POLICY_FILE:
				return getPolicyFile() != null;
			case CddPackage.CDD_ROOT__PORT:
				return getPort() != null;
			case CddPackage.CDD_ROOT__PRE_LOAD_CACHES:
				return getPreLoadCaches() != null;
			case CddPackage.CDD_ROOT__PRE_LOAD_ENABLED:
				return getPreLoadEnabled() != null;
			case CddPackage.CDD_ROOT__PRE_LOAD_FETCH_SIZE:
				return getPreLoadFetchSize() != null;
			case CddPackage.CDD_ROOT__PRE_LOAD_HANDLES:
				return getPreLoadHandles() != null;
			case CddPackage.CDD_ROOT__PRE_PROCESSOR:
				return PRE_PROCESSOR_EDEFAULT == null ? getPreProcessor() != null : !PRE_PROCESSOR_EDEFAULT.equals(getPreProcessor());
			case CddPackage.CDD_ROOT__PRIMARY_CONNECTION:
				return getPrimaryConnection() != null;
			case CddPackage.CDD_ROOT__PRIORITY:
				return getPriority() != null;
			case CddPackage.CDD_ROOT__PROCESS:
				return getProcess() != null;
			case CddPackage.CDD_ROOT__PROCESS_AGENT_CLASS:
				return getProcessAgentClass() != null;
			case CddPackage.CDD_ROOT__PROCESS_ENGINE:
				return getProcessEngine() != null;
			case CddPackage.CDD_ROOT__PROCESSES:
				return getProcesses() != null;
			case CddPackage.CDD_ROOT__PROCESS_GROUPS:
				return getProcessGroups() != null;
			case CddPackage.CDD_ROOT__PROCESSING_UNIT:
				return getProcessingUnit() != null;
			case CddPackage.CDD_ROOT__PROCESSING_UNITS:
				return getProcessingUnits() != null;
			case CddPackage.CDD_ROOT__PROJECTION:
				return getProjection() != null;
			case CddPackage.CDD_ROOT__PROPERTY:
				return getProperty() != null;
			case CddPackage.CDD_ROOT__PROPERTY_CACHE_SIZE:
				return getPropertyCacheSize() != null;
			case CddPackage.CDD_ROOT__PROPERTY_CHECK_INTERVAL:
				return getPropertyCheckInterval() != null;
			case CddPackage.CDD_ROOT__PROPERTY_GROUP:
				return getPropertyGroup() != null;
			case CddPackage.CDD_ROOT__PROTOCOL:
				return getProtocol() != null;
			case CddPackage.CDD_ROOT__PROTOCOLS:
				return getProtocols() != null;
			case CddPackage.CDD_ROOT__PROTOCOL_TIMEOUT:
				return getProtocolTimeout() != null;
			case CddPackage.CDD_ROOT__PROVIDER:
				return getProvider() != null;
			case CddPackage.CDD_ROOT__QUERY_AGENT_CLASS:
				return getQueryAgentClass() != null;
			case CddPackage.CDD_ROOT__QUEUE_SIZE:
				return getQueueSize() != null;
			case CddPackage.CDD_ROOT__READ_TIMEOUT:
				return getReadTimeout() != null;
			case CddPackage.CDD_ROOT__RECEIVER:
				return getReceiver() != null;
			case CddPackage.CDD_ROOT__REMOTE_LISTEN_URL:
				return getRemoteListenUrl() != null;
			case CddPackage.CDD_ROOT__REQUESTER:
				return getRequester() != null;
			case CddPackage.CDD_ROOT__RETRY_COUNT:
				return getRetryCount() != null;
			case CddPackage.CDD_ROOT__REVERSE_REFERENCES:
				return getReverseReferences() != null;
			case CddPackage.CDD_ROOT__REVISION:
				return getRevision() != null;
			case CddPackage.CDD_ROOT__ROLE:
				return getRole() != null;
			case CddPackage.CDD_ROOT__ROLES:
				return getRoles() != null;
			case CddPackage.CDD_ROOT__ROUTER:
				return getRouter() != null;
			case CddPackage.CDD_ROOT__RULE:
				return getRule() != null;
			case CddPackage.CDD_ROOT__RULE_CONFIG:
				return getRuleConfig() != null;
			case CddPackage.CDD_ROOT__RULES:
				return getRules() != null;
			case CddPackage.CDD_ROOT__RULESETS:
				return getRulesets() != null;
			case CddPackage.CDD_ROOT__SECONDARY_CONNECTION:
				return getSecondaryConnection() != null;
			case CddPackage.CDD_ROOT__SECURITY_CONFIG:
				return getSecurityConfig() != null;
			case CddPackage.CDD_ROOT__SERVICE:
				return getService() != null;
			case CddPackage.CDD_ROOT__SET_PROPERTY:
				return getSetProperty() != null;
			case CddPackage.CDD_ROOT__SHARED_ALL:
				return getSharedAll() != null;
			case CddPackage.CDD_ROOT__SHARED_QUEUE:
				return getSharedQueue() != null;
			case CddPackage.CDD_ROOT__SHOUTDOWN_WAIT:
				return getShoutdownWait() != null;
			case CddPackage.CDD_ROOT__SHUTDOWN:
				return getShutdown() != null;
			case CddPackage.CDD_ROOT__SIZE:
				return getSize() != null;
			case CddPackage.CDD_ROOT__SKIP_RECOVERY:
				return getSkipRecovery() != null;
			case CddPackage.CDD_ROOT__SOCKET_OUTPUT_BUFFER_SIZE:
				return getSocketOutputBufferSize() != null;
			case CddPackage.CDD_ROOT__SSL:
				return getSsl() != null;
			case CddPackage.CDD_ROOT__STALE_CONNECTION_CHECK:
				return getStaleConnectionCheck() != null;
			case CddPackage.CDD_ROOT__STARTUP:
				return getStartup() != null;
			case CddPackage.CDD_ROOT__STRATEGY:
				return getStrategy() != null;
			case CddPackage.CDD_ROOT__SUBJECT:
				return getSubject() != null;
			case CddPackage.CDD_ROOT__SUBSCRIBE:
				return getSubscribe() != null;
			case CddPackage.CDD_ROOT__SYS_ERR_REDIRECT:
				return getSysErrRedirect() != null;
			case CddPackage.CDD_ROOT__SYS_OUT_REDIRECT:
				return getSysOutRedirect() != null;
			case CddPackage.CDD_ROOT__TABLE_NAME:
				return getTableName() != null;
			case CddPackage.CDD_ROOT__TCP_NO_DELAY:
				return getTcpNoDelay() != null;
			case CddPackage.CDD_ROOT__TERMINAL:
				return getTerminal() != null;
			case CddPackage.CDD_ROOT__THREAD_AFFINITY_RULE_FUNCTION:
				return THREAD_AFFINITY_RULE_FUNCTION_EDEFAULT == null ? getThreadAffinityRuleFunction() != null : !THREAD_AFFINITY_RULE_FUNCTION_EDEFAULT.equals(getThreadAffinityRuleFunction());
			case CddPackage.CDD_ROOT__THREAD_COUNT:
				return getThreadCount() != null;
			case CddPackage.CDD_ROOT__THREADING_MODEL:
				return getThreadingModel() != THREADING_MODEL_EDEFAULT;
			case CddPackage.CDD_ROOT__TOKEN_FILE:
				return getTokenFile() != null;
			case CddPackage.CDD_ROOT__TRUST_MANAGER_ALGORITHM:
				return getTrustManagerAlgorithm() != null;
			case CddPackage.CDD_ROOT__TTL:
				return getTtl() != null;
			case CddPackage.CDD_ROOT__TYPE:
				return getType() != null;
			case CddPackage.CDD_ROOT__URI:
				return URI_EDEFAULT == null ? getUri() != null : !URI_EDEFAULT.equals(getUri());
			case CddPackage.CDD_ROOT__USER_NAME:
				return getUserName() != null;
			case CddPackage.CDD_ROOT__USER_PASSWORD:
				return getUserPassword() != null;
			case CddPackage.CDD_ROOT__VERSION:
				return VERSION_EDEFAULT == null ? getVersion() != null : !VERSION_EDEFAULT.equals(getVersion());
			case CddPackage.CDD_ROOT__WAIT_TIMEOUT:
				return getWaitTimeout() != null;
			case CddPackage.CDD_ROOT__WORKERS:
				return getWorkers() != null;
			case CddPackage.CDD_ROOT__WORKERTHREADS_COUNT:
				return getWorkerthreadsCount() != null;
			case CddPackage.CDD_ROOT__WRITE_TIMEOUT:
				return getWriteTimeout() != null;
			case CddPackage.CDD_ROOT__ENTITY:
				return getEntity() != null;
			case CddPackage.CDD_ROOT__FILTER:
				return getFilter() != null;
			case CddPackage.CDD_ROOT__ENTITY_SET:
				return getEntitySet() != null;
			case CddPackage.CDD_ROOT__ENABLE_TABLE_TRIMMING:
				return getEnableTableTrimming() != null;
			case CddPackage.CDD_ROOT__TRIMMING_FIELD:
				return getTrimmingField() != null;
			case CddPackage.CDD_ROOT__TRIMMING_RULE:
				return getTrimmingRule() != null;
			case CddPackage.CDD_ROOT__GENERATE_LV_FILES:
				return getGenerateLVFiles() != null;
			case CddPackage.CDD_ROOT__OUTPUT_PATH:
				return getOutputPath() != null;
			case CddPackage.CDD_ROOT__LDM_CONNECTION:
				return getLdmConnection() != null;
			case CddPackage.CDD_ROOT__LDM_URL:
				return getLdmUrl() != null;
			case CddPackage.CDD_ROOT__PUBLISHER:
				return getPublisher() != null;
			case CddPackage.CDD_ROOT__PUBLISHER_QUEUE_SIZE:
				return getPublisherQueueSize() != null;
			case CddPackage.CDD_ROOT__PUBLISHER_THREAD_COUNT:
				return getPublisherThreadCount() != null;
			case CddPackage.CDD_ROOT__LIVEVIEW_AGENT_CLASS:
				return getLiveviewAgentClass() != null;
			case CddPackage.CDD_ROOT__COMPOSITE_INDEXES:
				return getCompositeIndexes() != null;
			case CddPackage.CDD_ROOT__COMPOSITE_INDEX:
				return getCompositeIndex() != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (mixed: ");
		result.append(mixed);
		result.append(')');
		return result.toString();
	}

} //CddRootImpl
