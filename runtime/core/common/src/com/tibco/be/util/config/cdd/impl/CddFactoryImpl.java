/**
 */
package com.tibco.be.util.config.cdd.impl;

import com.tibco.be.util.config.cdd.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CddFactoryImpl extends EFactoryImpl implements CddFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CddFactory init() {
		try {
			CddFactory theCddFactory = (CddFactory)EPackage.Registry.INSTANCE.getEFactory(CddPackage.eNS_URI);
			if (theCddFactory != null) {
				return theCddFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CddFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CddFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case CddPackage.AGENT_CLASSES_CONFIG: return createAgentClassesConfig();
			case CddPackage.AGENT_CONFIG: return createAgentConfig();
			case CddPackage.AGENTS_CONFIG: return createAgentsConfig();
			case CddPackage.ALERT_CONFIG_CONFIG: return createAlertConfigConfig();
			case CddPackage.ALERT_CONFIG_SET_CONFIG: return createAlertConfigSetConfig();
			case CddPackage.BACKING_STORE_CONFIG: return createBackingStoreConfig();
			case CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG: return createBackingStoreForDomainObjectConfig();
			case CddPackage.BACKING_STORE_FOR_PROPERTIES_CONFIG: return createBackingStoreForPropertiesConfig();
			case CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG: return createBackingStoreForPropertyConfig();
			case CddPackage.BUSINESSWORKS_CONFIG: return createBusinessworksConfig();
			case CddPackage.CACHE_AGENT_CLASS_CONFIG: return createCacheAgentClassConfig();
			case CddPackage.CACHE_MANAGER_CONFIG: return createCacheManagerConfig();
			case CddPackage.CACHE_MANAGER_SECURITY_CONFIG: return createCacheManagerSecurityConfig();
			case CddPackage.CDD_ROOT: return createCddRoot();
			case CddPackage.CHILD_CLUSTER_MEMBER_CONFIG: return createChildClusterMemberConfig();
			case CddPackage.CIPHERS_CONFIG: return createCiphersConfig();
			case CddPackage.CLUSTER_CONFIG: return createClusterConfig();
			case CddPackage.CLUSTER_MEMBER_CONFIG: return createClusterMemberConfig();
			case CddPackage.CONDITION_CONFIG: return createConditionConfig();
			case CddPackage.CONNECTION_CONFIG: return createConnectionConfig();
			case CddPackage.DASHBOARD_AGENT_CLASS_CONFIG: return createDashboardAgentClassConfig();
			case CddPackage.DB_CONCEPTS_CONFIG: return createDbConceptsConfig();
			case CddPackage.DESTINATION_CONFIG: return createDestinationConfig();
			case CddPackage.DESTINATION_GROUPS_CONFIG: return createDestinationGroupsConfig();
			case CddPackage.DESTINATIONS_CONFIG: return createDestinationsConfig();
			case CddPackage.DOMAIN_OBJECT_CONFIG: return createDomainObjectConfig();
			case CddPackage.DOMAIN_OBJECTS_CONFIG: return createDomainObjectsConfig();
			case CddPackage.EVICTION_CONFIG: return createEvictionConfig();
			case CddPackage.FILES_CONFIG: return createFilesConfig();
			case CddPackage.FUNCTION_GROUPS_CONFIG: return createFunctionGroupsConfig();
			case CddPackage.FUNCTIONS_CONFIG: return createFunctionsConfig();
			case CddPackage.GET_PROPERTY_CONFIG: return createGetPropertyConfig();
			case CddPackage.HTTP_CONFIG: return createHttpConfig();
			case CddPackage.INDEX_CONFIG: return createIndexConfig();
			case CddPackage.INDEXES_CONFIG: return createIndexesConfig();
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG: return createInferenceAgentClassConfig();
			case CddPackage.INFERENCE_ENGINE_CONFIG: return createInferenceEngineConfig();
			case CddPackage.JOB_MANAGER_CONFIG: return createJobManagerConfig();
			case CddPackage.LINE_LAYOUT_CONFIG: return createLineLayoutConfig();
			case CddPackage.LOAD_BALANCER_ADHOC_CONFIG_CONFIG: return createLoadBalancerAdhocConfigConfig();
			case CddPackage.LOAD_BALANCER_ADHOC_CONFIGS_CONFIG: return createLoadBalancerAdhocConfigsConfig();
			case CddPackage.LOAD_BALANCER_CONFIGS_CONFIG: return createLoadBalancerConfigsConfig();
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG: return createLoadBalancerPairConfigConfig();
			case CddPackage.LOAD_BALANCER_PAIR_CONFIGS_CONFIG: return createLoadBalancerPairConfigsConfig();
			case CddPackage.LOAD_CONFIG: return createLoadConfig();
			case CddPackage.LOCAL_CACHE_CONFIG: return createLocalCacheConfig();
			case CddPackage.LOG_CONFIG_CONFIG: return createLogConfigConfig();
			case CddPackage.LOG_CONFIGS_CONFIG: return createLogConfigsConfig();
			case CddPackage.MEMORY_MANAGER_CONFIG: return createMemoryManagerConfig();
			case CddPackage.MM_ACTION_CONFIG: return createMmActionConfig();
			case CddPackage.MM_ACTION_CONFIG_CONFIG: return createMmActionConfigConfig();
			case CddPackage.MM_ACTION_CONFIG_SET_CONFIG: return createMmActionConfigSetConfig();
			case CddPackage.MM_AGENT_CLASS_CONFIG: return createMmAgentClassConfig();
			case CddPackage.MM_ALERT_CONFIG: return createMmAlertConfig();
			case CddPackage.MM_EXECUTE_COMMAND_CONFIG: return createMmExecuteCommandConfig();
			case CddPackage.MM_HEALTH_LEVEL_CONFIG: return createMmHealthLevelConfig();
			case CddPackage.MM_SEND_EMAIL_CONFIG: return createMmSendEmailConfig();
			case CddPackage.MM_TRIGGER_CONDITION_CONFIG: return createMmTriggerConditionConfig();
			case CddPackage.NOTIFICATION_CONFIG: return createNotificationConfig();
			case CddPackage.OBJECT_MANAGEMENT_CONFIG: return createObjectManagementConfig();
			case CddPackage.OBJECT_TABLE_CONFIG: return createObjectTableConfig();
			case CddPackage.OVERRIDE_CONFIG: return createOverrideConfig();
			case CddPackage.PROCESS_AGENT_CLASS_CONFIG: return createProcessAgentClassConfig();
			case CddPackage.PROCESS_CONFIG: return createProcessConfig();
			case CddPackage.PROCESS_ENGINE_CONFIG: return createProcessEngineConfig();
			case CddPackage.PROCESSES_CONFIG: return createProcessesConfig();
			case CddPackage.PROCESS_GROUPS_CONFIG: return createProcessGroupsConfig();
			case CddPackage.PROCESSING_UNIT_CONFIG: return createProcessingUnitConfig();
			case CddPackage.PROCESSING_UNITS_CONFIG: return createProcessingUnitsConfig();
			case CddPackage.PROCESSING_UNIT_SECURITY_CONFIG: return createProcessingUnitSecurityConfig();
			case CddPackage.PROJECTION_CONFIG: return createProjectionConfig();
			case CddPackage.PROPERTY_CONFIG: return createPropertyConfig();
			case CddPackage.PROPERTY_GROUP_CONFIG: return createPropertyGroupConfig();
			case CddPackage.PROTOCOLS_CONFIG: return createProtocolsConfig();
			case CddPackage.PROVIDER_CONFIG: return createProviderConfig();
			case CddPackage.QUERY_AGENT_CLASS_CONFIG: return createQueryAgentClassConfig();
			case CddPackage.REVISION_CONFIG: return createRevisionConfig();
			case CddPackage.RULE_CONFIG: return createRuleConfig();
			case CddPackage.RULE_CONFIG_CONFIG: return createRuleConfigConfig();
			case CddPackage.RULES_CONFIG: return createRulesConfig();
			case CddPackage.RULESETS_CONFIG: return createRulesetsConfig();
			case CddPackage.SECURITY_CONTROLLER: return createSecurityController();
			case CddPackage.SECURITY_OVERRIDE_CONFIG: return createSecurityOverrideConfig();
			case CddPackage.SECURITY_REQUESTER: return createSecurityRequester();
			case CddPackage.SET_PROPERTY_CONFIG: return createSetPropertyConfig();
			case CddPackage.SHARED_QUEUE_CONFIG: return createSharedQueueConfig();
			case CddPackage.SSL_CONFIG: return createSslConfig();
			case CddPackage.SYSTEM_PROPERTY_CONFIG: return createSystemPropertyConfig();
			case CddPackage.TERMINAL_CONFIG: return createTerminalConfig();
			case CddPackage.URIS_CONFIG: return createUrisConfig();
			case CddPackage.FIELD_ENCRYPTION_CONFIG: return createFieldEncryptionConfig();
			case CddPackage.ENTITY_CONFIG: return createEntityConfig();
			case CddPackage.ENTITY_SET_CONFIG: return createEntitySetConfig();
			case CddPackage.LDM_CONNECTION_CONFIG: return createLDMConnectionConfig();
			case CddPackage.PUBLISHER_CONFIG: return createPublisherConfig();
			case CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG: return createLiveViewAgentClassConfig();
			case CddPackage.COMPOSITE_INDEX_PROPERTIES_CONFIG: return createCompositeIndexPropertiesConfig();
			case CddPackage.COMPOSITE_INDEX_CONFIG: return createCompositeIndexConfig();
			case CddPackage.COMPOSITE_INDEXES_CONFIG: return createCompositeIndexesConfig();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case CddPackage.DOMAIN_OBJECT_MODE_CONFIG:
				return createDomainObjectModeConfigFromString(eDataType, initialValue);
			case CddPackage.THREADING_MODEL_CONFIG:
				return createThreadingModelConfigFromString(eDataType, initialValue);
			case CddPackage.DOMAIN_OBJECT_MODE_CONFIG_OBJECT:
				return createDomainObjectModeConfigObjectFromString(eDataType, initialValue);
			case CddPackage.ONTOLOGY_URI_CONFIG:
				return createOntologyUriConfigFromString(eDataType, initialValue);
			case CddPackage.THREADING_MODEL_CONFIG_OBJECT:
				return createThreadingModelConfigObjectFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case CddPackage.DOMAIN_OBJECT_MODE_CONFIG:
				return convertDomainObjectModeConfigToString(eDataType, instanceValue);
			case CddPackage.THREADING_MODEL_CONFIG:
				return convertThreadingModelConfigToString(eDataType, instanceValue);
			case CddPackage.DOMAIN_OBJECT_MODE_CONFIG_OBJECT:
				return convertDomainObjectModeConfigObjectToString(eDataType, instanceValue);
			case CddPackage.ONTOLOGY_URI_CONFIG:
				return convertOntologyUriConfigToString(eDataType, instanceValue);
			case CddPackage.THREADING_MODEL_CONFIG_OBJECT:
				return convertThreadingModelConfigObjectToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AgentClassesConfig createAgentClassesConfig() {
		AgentClassesConfigImpl agentClassesConfig = new AgentClassesConfigImpl();
		return agentClassesConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AgentConfig createAgentConfig() {
		AgentConfigImpl agentConfig = new AgentConfigImpl();
		return agentConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AgentsConfig createAgentsConfig() {
		AgentsConfigImpl agentsConfig = new AgentsConfigImpl();
		return agentsConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlertConfigConfig createAlertConfigConfig() {
		AlertConfigConfigImpl alertConfigConfig = new AlertConfigConfigImpl();
		return alertConfigConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlertConfigSetConfig createAlertConfigSetConfig() {
		AlertConfigSetConfigImpl alertConfigSetConfig = new AlertConfigSetConfigImpl();
		return alertConfigSetConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BackingStoreConfig createBackingStoreConfig() {
		BackingStoreConfigImpl backingStoreConfig = new BackingStoreConfigImpl();
		return backingStoreConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BackingStoreForDomainObjectConfig createBackingStoreForDomainObjectConfig() {
		BackingStoreForDomainObjectConfigImpl backingStoreForDomainObjectConfig = new BackingStoreForDomainObjectConfigImpl();
		return backingStoreForDomainObjectConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BackingStoreForPropertiesConfig createBackingStoreForPropertiesConfig() {
		BackingStoreForPropertiesConfigImpl backingStoreForPropertiesConfig = new BackingStoreForPropertiesConfigImpl();
		return backingStoreForPropertiesConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BackingStoreForPropertyConfig createBackingStoreForPropertyConfig() {
		BackingStoreForPropertyConfigImpl backingStoreForPropertyConfig = new BackingStoreForPropertyConfigImpl();
		return backingStoreForPropertyConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BusinessworksConfig createBusinessworksConfig() {
		BusinessworksConfigImpl businessworksConfig = new BusinessworksConfigImpl();
		return businessworksConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CacheAgentClassConfig createCacheAgentClassConfig() {
		CacheAgentClassConfigImpl cacheAgentClassConfig = new CacheAgentClassConfigImpl();
		return cacheAgentClassConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CacheManagerConfig createCacheManagerConfig() {
		CacheManagerConfigImpl cacheManagerConfig = new CacheManagerConfigImpl();
		return cacheManagerConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CacheManagerSecurityConfig createCacheManagerSecurityConfig() {
		CacheManagerSecurityConfigImpl cacheManagerSecurityConfig = new CacheManagerSecurityConfigImpl();
		return cacheManagerSecurityConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CddRoot createCddRoot() {
		CddRootImpl cddRoot = new CddRootImpl();
		return cddRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChildClusterMemberConfig createChildClusterMemberConfig() {
		ChildClusterMemberConfigImpl childClusterMemberConfig = new ChildClusterMemberConfigImpl();
		return childClusterMemberConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CiphersConfig createCiphersConfig() {
		CiphersConfigImpl ciphersConfig = new CiphersConfigImpl();
		return ciphersConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClusterConfig createClusterConfig() {
		ClusterConfigImpl clusterConfig = new ClusterConfigImpl();
		return clusterConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClusterMemberConfig createClusterMemberConfig() {
		ClusterMemberConfigImpl clusterMemberConfig = new ClusterMemberConfigImpl();
		return clusterMemberConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConditionConfig createConditionConfig() {
		ConditionConfigImpl conditionConfig = new ConditionConfigImpl();
		return conditionConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectionConfig createConnectionConfig() {
		ConnectionConfigImpl connectionConfig = new ConnectionConfigImpl();
		return connectionConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DashboardAgentClassConfig createDashboardAgentClassConfig() {
		DashboardAgentClassConfigImpl dashboardAgentClassConfig = new DashboardAgentClassConfigImpl();
		return dashboardAgentClassConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DbConceptsConfig createDbConceptsConfig() {
		DbConceptsConfigImpl dbConceptsConfig = new DbConceptsConfigImpl();
		return dbConceptsConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DestinationConfig createDestinationConfig() {
		DestinationConfigImpl destinationConfig = new DestinationConfigImpl();
		return destinationConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DestinationGroupsConfig createDestinationGroupsConfig() {
		DestinationGroupsConfigImpl destinationGroupsConfig = new DestinationGroupsConfigImpl();
		return destinationGroupsConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DestinationsConfig createDestinationsConfig() {
		DestinationsConfigImpl destinationsConfig = new DestinationsConfigImpl();
		return destinationsConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainObjectConfig createDomainObjectConfig() {
		DomainObjectConfigImpl domainObjectConfig = new DomainObjectConfigImpl();
		return domainObjectConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainObjectsConfig createDomainObjectsConfig() {
		DomainObjectsConfigImpl domainObjectsConfig = new DomainObjectsConfigImpl();
		return domainObjectsConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EvictionConfig createEvictionConfig() {
		EvictionConfigImpl evictionConfig = new EvictionConfigImpl();
		return evictionConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FilesConfig createFilesConfig() {
		FilesConfigImpl filesConfig = new FilesConfigImpl();
		return filesConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionGroupsConfig createFunctionGroupsConfig() {
		FunctionGroupsConfigImpl functionGroupsConfig = new FunctionGroupsConfigImpl();
		return functionGroupsConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionsConfig createFunctionsConfig() {
		FunctionsConfigImpl functionsConfig = new FunctionsConfigImpl();
		return functionsConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GetPropertyConfig createGetPropertyConfig() {
		GetPropertyConfigImpl getPropertyConfig = new GetPropertyConfigImpl();
		return getPropertyConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HttpConfig createHttpConfig() {
		HttpConfigImpl httpConfig = new HttpConfigImpl();
		return httpConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IndexConfig createIndexConfig() {
		IndexConfigImpl indexConfig = new IndexConfigImpl();
		return indexConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IndexesConfig createIndexesConfig() {
		IndexesConfigImpl indexesConfig = new IndexesConfigImpl();
		return indexesConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InferenceAgentClassConfig createInferenceAgentClassConfig() {
		InferenceAgentClassConfigImpl inferenceAgentClassConfig = new InferenceAgentClassConfigImpl();
		return inferenceAgentClassConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InferenceEngineConfig createInferenceEngineConfig() {
		InferenceEngineConfigImpl inferenceEngineConfig = new InferenceEngineConfigImpl();
		return inferenceEngineConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JobManagerConfig createJobManagerConfig() {
		JobManagerConfigImpl jobManagerConfig = new JobManagerConfigImpl();
		return jobManagerConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LineLayoutConfig createLineLayoutConfig() {
		LineLayoutConfigImpl lineLayoutConfig = new LineLayoutConfigImpl();
		return lineLayoutConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LoadBalancerAdhocConfigConfig createLoadBalancerAdhocConfigConfig() {
		LoadBalancerAdhocConfigConfigImpl loadBalancerAdhocConfigConfig = new LoadBalancerAdhocConfigConfigImpl();
		return loadBalancerAdhocConfigConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LoadBalancerAdhocConfigsConfig createLoadBalancerAdhocConfigsConfig() {
		LoadBalancerAdhocConfigsConfigImpl loadBalancerAdhocConfigsConfig = new LoadBalancerAdhocConfigsConfigImpl();
		return loadBalancerAdhocConfigsConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LoadBalancerConfigsConfig createLoadBalancerConfigsConfig() {
		LoadBalancerConfigsConfigImpl loadBalancerConfigsConfig = new LoadBalancerConfigsConfigImpl();
		return loadBalancerConfigsConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LoadBalancerPairConfigConfig createLoadBalancerPairConfigConfig() {
		LoadBalancerPairConfigConfigImpl loadBalancerPairConfigConfig = new LoadBalancerPairConfigConfigImpl();
		return loadBalancerPairConfigConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LoadBalancerPairConfigsConfig createLoadBalancerPairConfigsConfig() {
		LoadBalancerPairConfigsConfigImpl loadBalancerPairConfigsConfig = new LoadBalancerPairConfigsConfigImpl();
		return loadBalancerPairConfigsConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LoadConfig createLoadConfig() {
		LoadConfigImpl loadConfig = new LoadConfigImpl();
		return loadConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalCacheConfig createLocalCacheConfig() {
		LocalCacheConfigImpl localCacheConfig = new LocalCacheConfigImpl();
		return localCacheConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LogConfigConfig createLogConfigConfig() {
		LogConfigConfigImpl logConfigConfig = new LogConfigConfigImpl();
		return logConfigConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LogConfigsConfig createLogConfigsConfig() {
		LogConfigsConfigImpl logConfigsConfig = new LogConfigsConfigImpl();
		return logConfigsConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MemoryManagerConfig createMemoryManagerConfig() {
		MemoryManagerConfigImpl memoryManagerConfig = new MemoryManagerConfigImpl();
		return memoryManagerConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MmActionConfig createMmActionConfig() {
		MmActionConfigImpl mmActionConfig = new MmActionConfigImpl();
		return mmActionConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MmActionConfigConfig createMmActionConfigConfig() {
		MmActionConfigConfigImpl mmActionConfigConfig = new MmActionConfigConfigImpl();
		return mmActionConfigConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MmActionConfigSetConfig createMmActionConfigSetConfig() {
		MmActionConfigSetConfigImpl mmActionConfigSetConfig = new MmActionConfigSetConfigImpl();
		return mmActionConfigSetConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MmAgentClassConfig createMmAgentClassConfig() {
		MmAgentClassConfigImpl mmAgentClassConfig = new MmAgentClassConfigImpl();
		return mmAgentClassConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MmAlertConfig createMmAlertConfig() {
		MmAlertConfigImpl mmAlertConfig = new MmAlertConfigImpl();
		return mmAlertConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MmExecuteCommandConfig createMmExecuteCommandConfig() {
		MmExecuteCommandConfigImpl mmExecuteCommandConfig = new MmExecuteCommandConfigImpl();
		return mmExecuteCommandConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MmHealthLevelConfig createMmHealthLevelConfig() {
		MmHealthLevelConfigImpl mmHealthLevelConfig = new MmHealthLevelConfigImpl();
		return mmHealthLevelConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MmSendEmailConfig createMmSendEmailConfig() {
		MmSendEmailConfigImpl mmSendEmailConfig = new MmSendEmailConfigImpl();
		return mmSendEmailConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MmTriggerConditionConfig createMmTriggerConditionConfig() {
		MmTriggerConditionConfigImpl mmTriggerConditionConfig = new MmTriggerConditionConfigImpl();
		return mmTriggerConditionConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationConfig createNotificationConfig() {
		NotificationConfigImpl notificationConfig = new NotificationConfigImpl();
		return notificationConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ObjectManagementConfig createObjectManagementConfig() {
		ObjectManagementConfigImpl objectManagementConfig = new ObjectManagementConfigImpl();
		return objectManagementConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ObjectTableConfig createObjectTableConfig() {
		ObjectTableConfigImpl objectTableConfig = new ObjectTableConfigImpl();
		return objectTableConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig createOverrideConfig() {
		OverrideConfigImpl overrideConfig = new OverrideConfigImpl();
		return overrideConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessAgentClassConfig createProcessAgentClassConfig() {
		ProcessAgentClassConfigImpl processAgentClassConfig = new ProcessAgentClassConfigImpl();
		return processAgentClassConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessConfig createProcessConfig() {
		ProcessConfigImpl processConfig = new ProcessConfigImpl();
		return processConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessEngineConfig createProcessEngineConfig() {
		ProcessEngineConfigImpl processEngineConfig = new ProcessEngineConfigImpl();
		return processEngineConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessesConfig createProcessesConfig() {
		ProcessesConfigImpl processesConfig = new ProcessesConfigImpl();
		return processesConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessGroupsConfig createProcessGroupsConfig() {
		ProcessGroupsConfigImpl processGroupsConfig = new ProcessGroupsConfigImpl();
		return processGroupsConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessingUnitConfig createProcessingUnitConfig() {
		ProcessingUnitConfigImpl processingUnitConfig = new ProcessingUnitConfigImpl();
		return processingUnitConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessingUnitsConfig createProcessingUnitsConfig() {
		ProcessingUnitsConfigImpl processingUnitsConfig = new ProcessingUnitsConfigImpl();
		return processingUnitsConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessingUnitSecurityConfig createProcessingUnitSecurityConfig() {
		ProcessingUnitSecurityConfigImpl processingUnitSecurityConfig = new ProcessingUnitSecurityConfigImpl();
		return processingUnitSecurityConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectionConfig createProjectionConfig() {
		ProjectionConfigImpl projectionConfig = new ProjectionConfigImpl();
		return projectionConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyConfig createPropertyConfig() {
		PropertyConfigImpl propertyConfig = new PropertyConfigImpl();
		return propertyConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyGroupConfig createPropertyGroupConfig() {
		PropertyGroupConfigImpl propertyGroupConfig = new PropertyGroupConfigImpl();
		return propertyGroupConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProtocolsConfig createProtocolsConfig() {
		ProtocolsConfigImpl protocolsConfig = new ProtocolsConfigImpl();
		return protocolsConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProviderConfig createProviderConfig() {
		ProviderConfigImpl providerConfig = new ProviderConfigImpl();
		return providerConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public QueryAgentClassConfig createQueryAgentClassConfig() {
		QueryAgentClassConfigImpl queryAgentClassConfig = new QueryAgentClassConfigImpl();
		return queryAgentClassConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RevisionConfig createRevisionConfig() {
		RevisionConfigImpl revisionConfig = new RevisionConfigImpl();
		return revisionConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleConfig createRuleConfig() {
		RuleConfigImpl ruleConfig = new RuleConfigImpl();
		return ruleConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleConfigConfig createRuleConfigConfig() {
		RuleConfigConfigImpl ruleConfigConfig = new RuleConfigConfigImpl();
		return ruleConfigConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RulesConfig createRulesConfig() {
		RulesConfigImpl rulesConfig = new RulesConfigImpl();
		return rulesConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RulesetsConfig createRulesetsConfig() {
		RulesetsConfigImpl rulesetsConfig = new RulesetsConfigImpl();
		return rulesetsConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityController createSecurityController() {
		SecurityControllerImpl securityController = new SecurityControllerImpl();
		return securityController;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityOverrideConfig createSecurityOverrideConfig() {
		SecurityOverrideConfigImpl securityOverrideConfig = new SecurityOverrideConfigImpl();
		return securityOverrideConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityRequester createSecurityRequester() {
		SecurityRequesterImpl securityRequester = new SecurityRequesterImpl();
		return securityRequester;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SetPropertyConfig createSetPropertyConfig() {
		SetPropertyConfigImpl setPropertyConfig = new SetPropertyConfigImpl();
		return setPropertyConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SharedQueueConfig createSharedQueueConfig() {
		SharedQueueConfigImpl sharedQueueConfig = new SharedQueueConfigImpl();
		return sharedQueueConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SslConfig createSslConfig() {
		SslConfigImpl sslConfig = new SslConfigImpl();
		return sslConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SystemPropertyConfig createSystemPropertyConfig() {
		SystemPropertyConfigImpl systemPropertyConfig = new SystemPropertyConfigImpl();
		return systemPropertyConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TerminalConfig createTerminalConfig() {
		TerminalConfigImpl terminalConfig = new TerminalConfigImpl();
		return terminalConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UrisConfig createUrisConfig() {
		UrisConfigImpl urisConfig = new UrisConfigImpl();
		return urisConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FieldEncryptionConfig createFieldEncryptionConfig() {
		FieldEncryptionConfigImpl fieldEncryptionConfig = new FieldEncryptionConfigImpl();
		return fieldEncryptionConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntityConfig createEntityConfig() {
		EntityConfigImpl entityConfig = new EntityConfigImpl();
		return entityConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntitySetConfig createEntitySetConfig() {
		EntitySetConfigImpl entitySetConfig = new EntitySetConfigImpl();
		return entitySetConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LDMConnectionConfig createLDMConnectionConfig() {
		LDMConnectionConfigImpl ldmConnectionConfig = new LDMConnectionConfigImpl();
		return ldmConnectionConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PublisherConfig createPublisherConfig() {
		PublisherConfigImpl publisherConfig = new PublisherConfigImpl();
		return publisherConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LiveViewAgentClassConfig createLiveViewAgentClassConfig() {
		LiveViewAgentClassConfigImpl liveViewAgentClassConfig = new LiveViewAgentClassConfigImpl();
		return liveViewAgentClassConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompositeIndexPropertiesConfig createCompositeIndexPropertiesConfig() {
		CompositeIndexPropertiesConfigImpl compositeIndexPropertiesConfig = new CompositeIndexPropertiesConfigImpl();
		return compositeIndexPropertiesConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompositeIndexConfig createCompositeIndexConfig() {
		CompositeIndexConfigImpl compositeIndexConfig = new CompositeIndexConfigImpl();
		return compositeIndexConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompositeIndexesConfig createCompositeIndexesConfig() {
		CompositeIndexesConfigImpl compositeIndexesConfig = new CompositeIndexesConfigImpl();
		return compositeIndexesConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainObjectModeConfig createDomainObjectModeConfigFromString(EDataType eDataType, String initialValue) {
		DomainObjectModeConfig result = DomainObjectModeConfig.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDomainObjectModeConfigToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ThreadingModelConfig createThreadingModelConfigFromString(EDataType eDataType, String initialValue) {
		ThreadingModelConfig result = ThreadingModelConfig.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertThreadingModelConfigToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainObjectModeConfig createDomainObjectModeConfigObjectFromString(EDataType eDataType, String initialValue) {
		return createDomainObjectModeConfigFromString(CddPackage.eINSTANCE.getDomainObjectModeConfig(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDomainObjectModeConfigObjectToString(EDataType eDataType, Object instanceValue) {
		return convertDomainObjectModeConfigToString(CddPackage.eINSTANCE.getDomainObjectModeConfig(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createOntologyUriConfigFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertOntologyUriConfigToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ThreadingModelConfig createThreadingModelConfigObjectFromString(EDataType eDataType, String initialValue) {
		return createThreadingModelConfigFromString(CddPackage.eINSTANCE.getThreadingModelConfig(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertThreadingModelConfigObjectToString(EDataType eDataType, Object instanceValue) {
		return convertThreadingModelConfigToString(CddPackage.eINSTANCE.getThreadingModelConfig(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CddPackage getCddPackage() {
		return (CddPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CddPackage getPackage() {
		return CddPackage.eINSTANCE;
	}

} //CddFactoryImpl
