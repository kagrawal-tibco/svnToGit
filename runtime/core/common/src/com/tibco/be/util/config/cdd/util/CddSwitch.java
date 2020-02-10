/**
 */
package com.tibco.be.util.config.cdd.util;

import com.tibco.be.util.config.cdd.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.tibco.be.util.config.cdd.CddPackage
 * @generated
 */
public class CddSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static CddPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CddSwitch() {
		if (modelPackage == null) {
			modelPackage = CddPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case CddPackage.AGENT_CLASS_CONFIG: {
				AgentClassConfig agentClassConfig = (AgentClassConfig)theEObject;
				T result = caseAgentClassConfig(agentClassConfig);
				if (result == null) result = caseArtifactConfig(agentClassConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.AGENT_CLASSES_CONFIG: {
				AgentClassesConfig agentClassesConfig = (AgentClassesConfig)theEObject;
				T result = caseAgentClassesConfig(agentClassesConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.AGENT_CONFIG: {
				AgentConfig agentConfig = (AgentConfig)theEObject;
				T result = caseAgentConfig(agentConfig);
				if (result == null) result = caseArtifactConfig(agentConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.AGENTS_CONFIG: {
				AgentsConfig agentsConfig = (AgentsConfig)theEObject;
				T result = caseAgentsConfig(agentsConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.ALERT_CONFIG_CONFIG: {
				AlertConfigConfig alertConfigConfig = (AlertConfigConfig)theEObject;
				T result = caseAlertConfigConfig(alertConfigConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.ALERT_CONFIG_SET_CONFIG: {
				AlertConfigSetConfig alertConfigSetConfig = (AlertConfigSetConfig)theEObject;
				T result = caseAlertConfigSetConfig(alertConfigSetConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.ARTIFACT_CONFIG: {
				ArtifactConfig artifactConfig = (ArtifactConfig)theEObject;
				T result = caseArtifactConfig(artifactConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.BACKING_STORE_CONFIG: {
				BackingStoreConfig backingStoreConfig = (BackingStoreConfig)theEObject;
				T result = caseBackingStoreConfig(backingStoreConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG: {
				BackingStoreForDomainObjectConfig backingStoreForDomainObjectConfig = (BackingStoreForDomainObjectConfig)theEObject;
				T result = caseBackingStoreForDomainObjectConfig(backingStoreForDomainObjectConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.BACKING_STORE_FOR_PROPERTIES_CONFIG: {
				BackingStoreForPropertiesConfig backingStoreForPropertiesConfig = (BackingStoreForPropertiesConfig)theEObject;
				T result = caseBackingStoreForPropertiesConfig(backingStoreForPropertiesConfig);
				if (result == null) result = caseArtifactConfig(backingStoreForPropertiesConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG: {
				BackingStoreForPropertyConfig backingStoreForPropertyConfig = (BackingStoreForPropertyConfig)theEObject;
				T result = caseBackingStoreForPropertyConfig(backingStoreForPropertyConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.BUSINESSWORKS_CONFIG: {
				BusinessworksConfig businessworksConfig = (BusinessworksConfig)theEObject;
				T result = caseBusinessworksConfig(businessworksConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.CACHE_AGENT_CLASS_CONFIG: {
				CacheAgentClassConfig cacheAgentClassConfig = (CacheAgentClassConfig)theEObject;
				T result = caseCacheAgentClassConfig(cacheAgentClassConfig);
				if (result == null) result = caseAgentClassConfig(cacheAgentClassConfig);
				if (result == null) result = caseArtifactConfig(cacheAgentClassConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.CACHE_MANAGER_CONFIG: {
				CacheManagerConfig cacheManagerConfig = (CacheManagerConfig)theEObject;
				T result = caseCacheManagerConfig(cacheManagerConfig);
				if (result == null) result = caseObjectManagerConfig(cacheManagerConfig);
				if (result == null) result = caseArtifactConfig(cacheManagerConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.CACHE_MANAGER_SECURITY_CONFIG: {
				CacheManagerSecurityConfig cacheManagerSecurityConfig = (CacheManagerSecurityConfig)theEObject;
				T result = caseCacheManagerSecurityConfig(cacheManagerSecurityConfig);
				if (result == null) result = caseSecurityConfig(cacheManagerSecurityConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.CDD_ROOT: {
				CddRoot cddRoot = (CddRoot)theEObject;
				T result = caseCddRoot(cddRoot);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.CHILD_CLUSTER_MEMBER_CONFIG: {
				ChildClusterMemberConfig childClusterMemberConfig = (ChildClusterMemberConfig)theEObject;
				T result = caseChildClusterMemberConfig(childClusterMemberConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.CIPHERS_CONFIG: {
				CiphersConfig ciphersConfig = (CiphersConfig)theEObject;
				T result = caseCiphersConfig(ciphersConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.CLUSTER_CONFIG: {
				ClusterConfig clusterConfig = (ClusterConfig)theEObject;
				T result = caseClusterConfig(clusterConfig);
				if (result == null) result = caseArtifactConfig(clusterConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.CLUSTER_MEMBER_CONFIG: {
				ClusterMemberConfig clusterMemberConfig = (ClusterMemberConfig)theEObject;
				T result = caseClusterMemberConfig(clusterMemberConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.CONDITION_CONFIG: {
				ConditionConfig conditionConfig = (ConditionConfig)theEObject;
				T result = caseConditionConfig(conditionConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.CONNECTION_CONFIG: {
				ConnectionConfig connectionConfig = (ConnectionConfig)theEObject;
				T result = caseConnectionConfig(connectionConfig);
				if (result == null) result = caseArtifactConfig(connectionConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.DASHBOARD_AGENT_CLASS_CONFIG: {
				DashboardAgentClassConfig dashboardAgentClassConfig = (DashboardAgentClassConfig)theEObject;
				T result = caseDashboardAgentClassConfig(dashboardAgentClassConfig);
				if (result == null) result = caseAgentClassConfig(dashboardAgentClassConfig);
				if (result == null) result = caseArtifactConfig(dashboardAgentClassConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.DB_CONCEPTS_CONFIG: {
				DbConceptsConfig dbConceptsConfig = (DbConceptsConfig)theEObject;
				T result = caseDbConceptsConfig(dbConceptsConfig);
				if (result == null) result = caseArtifactConfig(dbConceptsConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.DESTINATION_CONFIG: {
				DestinationConfig destinationConfig = (DestinationConfig)theEObject;
				T result = caseDestinationConfig(destinationConfig);
				if (result == null) result = caseArtifactConfig(destinationConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.DESTINATION_GROUPS_CONFIG: {
				DestinationGroupsConfig destinationGroupsConfig = (DestinationGroupsConfig)theEObject;
				T result = caseDestinationGroupsConfig(destinationGroupsConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.DESTINATIONS_CONFIG: {
				DestinationsConfig destinationsConfig = (DestinationsConfig)theEObject;
				T result = caseDestinationsConfig(destinationsConfig);
				if (result == null) result = caseArtifactConfig(destinationsConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.DOMAIN_OBJECT_CONFIG: {
				DomainObjectConfig domainObjectConfig = (DomainObjectConfig)theEObject;
				T result = caseDomainObjectConfig(domainObjectConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.DOMAIN_OBJECTS_CONFIG: {
				DomainObjectsConfig domainObjectsConfig = (DomainObjectsConfig)theEObject;
				T result = caseDomainObjectsConfig(domainObjectsConfig);
				if (result == null) result = caseArtifactConfig(domainObjectsConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.EVICTION_CONFIG: {
				EvictionConfig evictionConfig = (EvictionConfig)theEObject;
				T result = caseEvictionConfig(evictionConfig);
				if (result == null) result = caseArtifactConfig(evictionConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.FILES_CONFIG: {
				FilesConfig filesConfig = (FilesConfig)theEObject;
				T result = caseFilesConfig(filesConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.FUNCTION_GROUPS_CONFIG: {
				FunctionGroupsConfig functionGroupsConfig = (FunctionGroupsConfig)theEObject;
				T result = caseFunctionGroupsConfig(functionGroupsConfig);
				if (result == null) result = caseArtifactConfig(functionGroupsConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.FUNCTIONS_CONFIG: {
				FunctionsConfig functionsConfig = (FunctionsConfig)theEObject;
				T result = caseFunctionsConfig(functionsConfig);
				if (result == null) result = caseUrisAndRefsConfig(functionsConfig);
				if (result == null) result = caseArtifactConfig(functionsConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.GET_PROPERTY_CONFIG: {
				GetPropertyConfig getPropertyConfig = (GetPropertyConfig)theEObject;
				T result = caseGetPropertyConfig(getPropertyConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.HTTP_CONFIG: {
				HttpConfig httpConfig = (HttpConfig)theEObject;
				T result = caseHttpConfig(httpConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.INDEX_CONFIG: {
				IndexConfig indexConfig = (IndexConfig)theEObject;
				T result = caseIndexConfig(indexConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.INDEXES_CONFIG: {
				IndexesConfig indexesConfig = (IndexesConfig)theEObject;
				T result = caseIndexesConfig(indexesConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG: {
				InferenceAgentClassConfig inferenceAgentClassConfig = (InferenceAgentClassConfig)theEObject;
				T result = caseInferenceAgentClassConfig(inferenceAgentClassConfig);
				if (result == null) result = caseAgentClassConfig(inferenceAgentClassConfig);
				if (result == null) result = caseArtifactConfig(inferenceAgentClassConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.INFERENCE_ENGINE_CONFIG: {
				InferenceEngineConfig inferenceEngineConfig = (InferenceEngineConfig)theEObject;
				T result = caseInferenceEngineConfig(inferenceEngineConfig);
				if (result == null) result = caseArtifactConfig(inferenceEngineConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.JOB_MANAGER_CONFIG: {
				JobManagerConfig jobManagerConfig = (JobManagerConfig)theEObject;
				T result = caseJobManagerConfig(jobManagerConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.LINE_LAYOUT_CONFIG: {
				LineLayoutConfig lineLayoutConfig = (LineLayoutConfig)theEObject;
				T result = caseLineLayoutConfig(lineLayoutConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.LOAD_BALANCER_ADHOC_CONFIG_CONFIG: {
				LoadBalancerAdhocConfigConfig loadBalancerAdhocConfigConfig = (LoadBalancerAdhocConfigConfig)theEObject;
				T result = caseLoadBalancerAdhocConfigConfig(loadBalancerAdhocConfigConfig);
				if (result == null) result = caseArtifactConfig(loadBalancerAdhocConfigConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.LOAD_BALANCER_ADHOC_CONFIGS_CONFIG: {
				LoadBalancerAdhocConfigsConfig loadBalancerAdhocConfigsConfig = (LoadBalancerAdhocConfigsConfig)theEObject;
				T result = caseLoadBalancerAdhocConfigsConfig(loadBalancerAdhocConfigsConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.LOAD_BALANCER_CONFIGS_CONFIG: {
				LoadBalancerConfigsConfig loadBalancerConfigsConfig = (LoadBalancerConfigsConfig)theEObject;
				T result = caseLoadBalancerConfigsConfig(loadBalancerConfigsConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG: {
				LoadBalancerPairConfigConfig loadBalancerPairConfigConfig = (LoadBalancerPairConfigConfig)theEObject;
				T result = caseLoadBalancerPairConfigConfig(loadBalancerPairConfigConfig);
				if (result == null) result = caseArtifactConfig(loadBalancerPairConfigConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.LOAD_BALANCER_PAIR_CONFIGS_CONFIG: {
				LoadBalancerPairConfigsConfig loadBalancerPairConfigsConfig = (LoadBalancerPairConfigsConfig)theEObject;
				T result = caseLoadBalancerPairConfigsConfig(loadBalancerPairConfigsConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.LOAD_CONFIG: {
				LoadConfig loadConfig = (LoadConfig)theEObject;
				T result = caseLoadConfig(loadConfig);
				if (result == null) result = caseArtifactConfig(loadConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.LOCAL_CACHE_CONFIG: {
				LocalCacheConfig localCacheConfig = (LocalCacheConfig)theEObject;
				T result = caseLocalCacheConfig(localCacheConfig);
				if (result == null) result = caseArtifactConfig(localCacheConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.LOG_CONFIG_CONFIG: {
				LogConfigConfig logConfigConfig = (LogConfigConfig)theEObject;
				T result = caseLogConfigConfig(logConfigConfig);
				if (result == null) result = caseArtifactConfig(logConfigConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.LOG_CONFIGS_CONFIG: {
				LogConfigsConfig logConfigsConfig = (LogConfigsConfig)theEObject;
				T result = caseLogConfigsConfig(logConfigsConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.MEMORY_MANAGER_CONFIG: {
				MemoryManagerConfig memoryManagerConfig = (MemoryManagerConfig)theEObject;
				T result = caseMemoryManagerConfig(memoryManagerConfig);
				if (result == null) result = caseObjectManagerConfig(memoryManagerConfig);
				if (result == null) result = caseArtifactConfig(memoryManagerConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.MM_ACTION_CONFIG: {
				MmActionConfig mmActionConfig = (MmActionConfig)theEObject;
				T result = caseMmActionConfig(mmActionConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.MM_ACTION_CONFIG_CONFIG: {
				MmActionConfigConfig mmActionConfigConfig = (MmActionConfigConfig)theEObject;
				T result = caseMmActionConfigConfig(mmActionConfigConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.MM_ACTION_CONFIG_SET_CONFIG: {
				MmActionConfigSetConfig mmActionConfigSetConfig = (MmActionConfigSetConfig)theEObject;
				T result = caseMmActionConfigSetConfig(mmActionConfigSetConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.MM_AGENT_CLASS_CONFIG: {
				MmAgentClassConfig mmAgentClassConfig = (MmAgentClassConfig)theEObject;
				T result = caseMmAgentClassConfig(mmAgentClassConfig);
				if (result == null) result = caseAgentClassConfig(mmAgentClassConfig);
				if (result == null) result = caseArtifactConfig(mmAgentClassConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.MM_ALERT_CONFIG: {
				MmAlertConfig mmAlertConfig = (MmAlertConfig)theEObject;
				T result = caseMmAlertConfig(mmAlertConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.MM_EXECUTE_COMMAND_CONFIG: {
				MmExecuteCommandConfig mmExecuteCommandConfig = (MmExecuteCommandConfig)theEObject;
				T result = caseMmExecuteCommandConfig(mmExecuteCommandConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.MM_HEALTH_LEVEL_CONFIG: {
				MmHealthLevelConfig mmHealthLevelConfig = (MmHealthLevelConfig)theEObject;
				T result = caseMmHealthLevelConfig(mmHealthLevelConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.MM_SEND_EMAIL_CONFIG: {
				MmSendEmailConfig mmSendEmailConfig = (MmSendEmailConfig)theEObject;
				T result = caseMmSendEmailConfig(mmSendEmailConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.MM_TRIGGER_CONDITION_CONFIG: {
				MmTriggerConditionConfig mmTriggerConditionConfig = (MmTriggerConditionConfig)theEObject;
				T result = caseMmTriggerConditionConfig(mmTriggerConditionConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.NOTIFICATION_CONFIG: {
				NotificationConfig notificationConfig = (NotificationConfig)theEObject;
				T result = caseNotificationConfig(notificationConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.OBJECT_MANAGEMENT_CONFIG: {
				ObjectManagementConfig objectManagementConfig = (ObjectManagementConfig)theEObject;
				T result = caseObjectManagementConfig(objectManagementConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.OBJECT_MANAGER_CONFIG: {
				ObjectManagerConfig objectManagerConfig = (ObjectManagerConfig)theEObject;
				T result = caseObjectManagerConfig(objectManagerConfig);
				if (result == null) result = caseArtifactConfig(objectManagerConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.OBJECT_TABLE_CONFIG: {
				ObjectTableConfig objectTableConfig = (ObjectTableConfig)theEObject;
				T result = caseObjectTableConfig(objectTableConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.OVERRIDE_CONFIG: {
				OverrideConfig overrideConfig = (OverrideConfig)theEObject;
				T result = caseOverrideConfig(overrideConfig);
				if (result == null) result = caseSystemPropertyConfig(overrideConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.PROCESS_AGENT_CLASS_CONFIG: {
				ProcessAgentClassConfig processAgentClassConfig = (ProcessAgentClassConfig)theEObject;
				T result = caseProcessAgentClassConfig(processAgentClassConfig);
				if (result == null) result = caseAgentClassConfig(processAgentClassConfig);
				if (result == null) result = caseArtifactConfig(processAgentClassConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.PROCESS_CONFIG: {
				ProcessConfig processConfig = (ProcessConfig)theEObject;
				T result = caseProcessConfig(processConfig);
				if (result == null) result = caseArtifactConfig(processConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.PROCESS_ENGINE_CONFIG: {
				ProcessEngineConfig processEngineConfig = (ProcessEngineConfig)theEObject;
				T result = caseProcessEngineConfig(processEngineConfig);
				if (result == null) result = caseArtifactConfig(processEngineConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.PROCESSES_CONFIG: {
				ProcessesConfig processesConfig = (ProcessesConfig)theEObject;
				T result = caseProcessesConfig(processesConfig);
				if (result == null) result = caseUrisAndRefsConfig(processesConfig);
				if (result == null) result = caseArtifactConfig(processesConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.PROCESS_GROUPS_CONFIG: {
				ProcessGroupsConfig processGroupsConfig = (ProcessGroupsConfig)theEObject;
				T result = caseProcessGroupsConfig(processGroupsConfig);
				if (result == null) result = caseArtifactConfig(processGroupsConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.PROCESSING_UNIT_CONFIG: {
				ProcessingUnitConfig processingUnitConfig = (ProcessingUnitConfig)theEObject;
				T result = caseProcessingUnitConfig(processingUnitConfig);
				if (result == null) result = caseArtifactConfig(processingUnitConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.PROCESSING_UNITS_CONFIG: {
				ProcessingUnitsConfig processingUnitsConfig = (ProcessingUnitsConfig)theEObject;
				T result = caseProcessingUnitsConfig(processingUnitsConfig);
				if (result == null) result = caseArtifactConfig(processingUnitsConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.PROCESSING_UNIT_SECURITY_CONFIG: {
				ProcessingUnitSecurityConfig processingUnitSecurityConfig = (ProcessingUnitSecurityConfig)theEObject;
				T result = caseProcessingUnitSecurityConfig(processingUnitSecurityConfig);
				if (result == null) result = caseSecurityConfig(processingUnitSecurityConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.PROJECTION_CONFIG: {
				ProjectionConfig projectionConfig = (ProjectionConfig)theEObject;
				T result = caseProjectionConfig(projectionConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.PROPERTY_CONFIG: {
				PropertyConfig propertyConfig = (PropertyConfig)theEObject;
				T result = casePropertyConfig(propertyConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.PROPERTY_GROUP_CONFIG: {
				PropertyGroupConfig propertyGroupConfig = (PropertyGroupConfig)theEObject;
				T result = casePropertyGroupConfig(propertyGroupConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.PROTOCOLS_CONFIG: {
				ProtocolsConfig protocolsConfig = (ProtocolsConfig)theEObject;
				T result = caseProtocolsConfig(protocolsConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.PROVIDER_CONFIG: {
				ProviderConfig providerConfig = (ProviderConfig)theEObject;
				T result = caseProviderConfig(providerConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.QUERY_AGENT_CLASS_CONFIG: {
				QueryAgentClassConfig queryAgentClassConfig = (QueryAgentClassConfig)theEObject;
				T result = caseQueryAgentClassConfig(queryAgentClassConfig);
				if (result == null) result = caseAgentClassConfig(queryAgentClassConfig);
				if (result == null) result = caseArtifactConfig(queryAgentClassConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.REVISION_CONFIG: {
				RevisionConfig revisionConfig = (RevisionConfig)theEObject;
				T result = caseRevisionConfig(revisionConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.RULE_CONFIG: {
				RuleConfig ruleConfig = (RuleConfig)theEObject;
				T result = caseRuleConfig(ruleConfig);
				if (result == null) result = caseArtifactConfig(ruleConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.RULE_CONFIG_CONFIG: {
				RuleConfigConfig ruleConfigConfig = (RuleConfigConfig)theEObject;
				T result = caseRuleConfigConfig(ruleConfigConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.RULES_CONFIG: {
				RulesConfig rulesConfig = (RulesConfig)theEObject;
				T result = caseRulesConfig(rulesConfig);
				if (result == null) result = caseUrisAndRefsConfig(rulesConfig);
				if (result == null) result = caseArtifactConfig(rulesConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.RULESETS_CONFIG: {
				RulesetsConfig rulesetsConfig = (RulesetsConfig)theEObject;
				T result = caseRulesetsConfig(rulesetsConfig);
				if (result == null) result = caseArtifactConfig(rulesetsConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.SECURITY_CONFIG: {
				SecurityConfig securityConfig = (SecurityConfig)theEObject;
				T result = caseSecurityConfig(securityConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.SECURITY_CONTROLLER: {
				SecurityController securityController = (SecurityController)theEObject;
				T result = caseSecurityController(securityController);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.SECURITY_OVERRIDE_CONFIG: {
				SecurityOverrideConfig securityOverrideConfig = (SecurityOverrideConfig)theEObject;
				T result = caseSecurityOverrideConfig(securityOverrideConfig);
				if (result == null) result = caseOverrideConfig(securityOverrideConfig);
				if (result == null) result = caseSystemPropertyConfig(securityOverrideConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.SECURITY_REQUESTER: {
				SecurityRequester securityRequester = (SecurityRequester)theEObject;
				T result = caseSecurityRequester(securityRequester);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.SET_PROPERTY_CONFIG: {
				SetPropertyConfig setPropertyConfig = (SetPropertyConfig)theEObject;
				T result = caseSetPropertyConfig(setPropertyConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.SHARED_QUEUE_CONFIG: {
				SharedQueueConfig sharedQueueConfig = (SharedQueueConfig)theEObject;
				T result = caseSharedQueueConfig(sharedQueueConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.SSL_CONFIG: {
				SslConfig sslConfig = (SslConfig)theEObject;
				T result = caseSslConfig(sslConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.SYSTEM_PROPERTY_CONFIG: {
				SystemPropertyConfig systemPropertyConfig = (SystemPropertyConfig)theEObject;
				T result = caseSystemPropertyConfig(systemPropertyConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.TERMINAL_CONFIG: {
				TerminalConfig terminalConfig = (TerminalConfig)theEObject;
				T result = caseTerminalConfig(terminalConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.URIS_AND_REFS_CONFIG: {
				UrisAndRefsConfig urisAndRefsConfig = (UrisAndRefsConfig)theEObject;
				T result = caseUrisAndRefsConfig(urisAndRefsConfig);
				if (result == null) result = caseArtifactConfig(urisAndRefsConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.URIS_CONFIG: {
				UrisConfig urisConfig = (UrisConfig)theEObject;
				T result = caseUrisConfig(urisConfig);
				if (result == null) result = caseArtifactConfig(urisConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.FIELD_ENCRYPTION_CONFIG: {
				FieldEncryptionConfig fieldEncryptionConfig = (FieldEncryptionConfig)theEObject;
				T result = caseFieldEncryptionConfig(fieldEncryptionConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.ENTITY_CONFIG: {
				EntityConfig entityConfig = (EntityConfig)theEObject;
				T result = caseEntityConfig(entityConfig);
				if (result == null) result = caseArtifactConfig(entityConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.ENTITY_SET_CONFIG: {
				EntitySetConfig entitySetConfig = (EntitySetConfig)theEObject;
				T result = caseEntitySetConfig(entitySetConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.LDM_CONNECTION_CONFIG: {
				LDMConnectionConfig ldmConnectionConfig = (LDMConnectionConfig)theEObject;
				T result = caseLDMConnectionConfig(ldmConnectionConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.PUBLISHER_CONFIG: {
				PublisherConfig publisherConfig = (PublisherConfig)theEObject;
				T result = casePublisherConfig(publisherConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG: {
				LiveViewAgentClassConfig liveViewAgentClassConfig = (LiveViewAgentClassConfig)theEObject;
				T result = caseLiveViewAgentClassConfig(liveViewAgentClassConfig);
				if (result == null) result = caseAgentClassConfig(liveViewAgentClassConfig);
				if (result == null) result = caseArtifactConfig(liveViewAgentClassConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.COMPOSITE_INDEX_PROPERTIES_CONFIG: {
				CompositeIndexPropertiesConfig compositeIndexPropertiesConfig = (CompositeIndexPropertiesConfig)theEObject;
				T result = caseCompositeIndexPropertiesConfig(compositeIndexPropertiesConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.COMPOSITE_INDEX_CONFIG: {
				CompositeIndexConfig compositeIndexConfig = (CompositeIndexConfig)theEObject;
				T result = caseCompositeIndexConfig(compositeIndexConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CddPackage.COMPOSITE_INDEXES_CONFIG: {
				CompositeIndexesConfig compositeIndexesConfig = (CompositeIndexesConfig)theEObject;
				T result = caseCompositeIndexesConfig(compositeIndexesConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Agent Class Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Agent Class Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAgentClassConfig(AgentClassConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Agent Classes Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Agent Classes Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAgentClassesConfig(AgentClassesConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Agent Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Agent Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAgentConfig(AgentConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Agents Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Agents Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAgentsConfig(AgentsConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Alert Config Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Alert Config Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAlertConfigConfig(AlertConfigConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Alert Config Set Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Alert Config Set Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAlertConfigSetConfig(AlertConfigSetConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Artifact Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Artifact Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArtifactConfig(ArtifactConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Backing Store Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Backing Store Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBackingStoreConfig(BackingStoreConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Backing Store For Domain Object Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Backing Store For Domain Object Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBackingStoreForDomainObjectConfig(BackingStoreForDomainObjectConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Backing Store For Properties Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Backing Store For Properties Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBackingStoreForPropertiesConfig(BackingStoreForPropertiesConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Backing Store For Property Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Backing Store For Property Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBackingStoreForPropertyConfig(BackingStoreForPropertyConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Businessworks Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Businessworks Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBusinessworksConfig(BusinessworksConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Cache Agent Class Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cache Agent Class Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCacheAgentClassConfig(CacheAgentClassConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Cache Manager Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cache Manager Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCacheManagerConfig(CacheManagerConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Cache Manager Security Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cache Manager Security Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCacheManagerSecurityConfig(CacheManagerSecurityConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Root</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Root</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCddRoot(CddRoot object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Child Cluster Member Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Child Cluster Member Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseChildClusterMemberConfig(ChildClusterMemberConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ciphers Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ciphers Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCiphersConfig(CiphersConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Cluster Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cluster Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseClusterConfig(ClusterConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Cluster Member Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cluster Member Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseClusterMemberConfig(ClusterMemberConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Condition Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Condition Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConditionConfig(ConditionConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Connection Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Connection Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConnectionConfig(ConnectionConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dashboard Agent Class Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dashboard Agent Class Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDashboardAgentClassConfig(DashboardAgentClassConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Db Concepts Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Db Concepts Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDbConceptsConfig(DbConceptsConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Destination Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Destination Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDestinationConfig(DestinationConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Destination Groups Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Destination Groups Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDestinationGroupsConfig(DestinationGroupsConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Destinations Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Destinations Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDestinationsConfig(DestinationsConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Domain Object Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Domain Object Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDomainObjectConfig(DomainObjectConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Domain Objects Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Domain Objects Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDomainObjectsConfig(DomainObjectsConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Eviction Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Eviction Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEvictionConfig(EvictionConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Files Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Files Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFilesConfig(FilesConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function Groups Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function Groups Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunctionGroupsConfig(FunctionGroupsConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Functions Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Functions Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunctionsConfig(FunctionsConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Get Property Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Get Property Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGetPropertyConfig(GetPropertyConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Http Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Http Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHttpConfig(HttpConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Index Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Index Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIndexConfig(IndexConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Indexes Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Indexes Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIndexesConfig(IndexesConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Inference Agent Class Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Inference Agent Class Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInferenceAgentClassConfig(InferenceAgentClassConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Inference Engine Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Inference Engine Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInferenceEngineConfig(InferenceEngineConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Job Manager Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Job Manager Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJobManagerConfig(JobManagerConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Line Layout Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Line Layout Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLineLayoutConfig(LineLayoutConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Load Balancer Adhoc Config Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Load Balancer Adhoc Config Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLoadBalancerAdhocConfigConfig(LoadBalancerAdhocConfigConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Load Balancer Adhoc Configs Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Load Balancer Adhoc Configs Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLoadBalancerAdhocConfigsConfig(LoadBalancerAdhocConfigsConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Load Balancer Configs Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Load Balancer Configs Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLoadBalancerConfigsConfig(LoadBalancerConfigsConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Load Balancer Pair Config Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Load Balancer Pair Config Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLoadBalancerPairConfigConfig(LoadBalancerPairConfigConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Load Balancer Pair Configs Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Load Balancer Pair Configs Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLoadBalancerPairConfigsConfig(LoadBalancerPairConfigsConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Load Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Load Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLoadConfig(LoadConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Local Cache Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Local Cache Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLocalCacheConfig(LocalCacheConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Log Config Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Log Config Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLogConfigConfig(LogConfigConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Log Configs Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Log Configs Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLogConfigsConfig(LogConfigsConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Memory Manager Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Memory Manager Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMemoryManagerConfig(MemoryManagerConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mm Action Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mm Action Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMmActionConfig(MmActionConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mm Action Config Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mm Action Config Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMmActionConfigConfig(MmActionConfigConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mm Action Config Set Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mm Action Config Set Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMmActionConfigSetConfig(MmActionConfigSetConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mm Agent Class Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mm Agent Class Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMmAgentClassConfig(MmAgentClassConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mm Alert Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mm Alert Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMmAlertConfig(MmAlertConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mm Execute Command Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mm Execute Command Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMmExecuteCommandConfig(MmExecuteCommandConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mm Health Level Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mm Health Level Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMmHealthLevelConfig(MmHealthLevelConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mm Send Email Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mm Send Email Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMmSendEmailConfig(MmSendEmailConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mm Trigger Condition Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mm Trigger Condition Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMmTriggerConditionConfig(MmTriggerConditionConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Notification Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Notification Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNotificationConfig(NotificationConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Object Management Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Object Management Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseObjectManagementConfig(ObjectManagementConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Object Manager Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Object Manager Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseObjectManagerConfig(ObjectManagerConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Object Table Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Object Table Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseObjectTableConfig(ObjectTableConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Override Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Override Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOverrideConfig(OverrideConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Process Agent Class Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Process Agent Class Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProcessAgentClassConfig(ProcessAgentClassConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Process Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Process Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProcessConfig(ProcessConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Process Engine Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Process Engine Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProcessEngineConfig(ProcessEngineConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Processes Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Processes Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProcessesConfig(ProcessesConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Process Groups Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Process Groups Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProcessGroupsConfig(ProcessGroupsConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Processing Unit Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Processing Unit Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProcessingUnitConfig(ProcessingUnitConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Processing Units Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Processing Units Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProcessingUnitsConfig(ProcessingUnitsConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Processing Unit Security Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Processing Unit Security Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProcessingUnitSecurityConfig(ProcessingUnitSecurityConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Projection Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Projection Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProjectionConfig(ProjectionConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Property Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePropertyConfig(PropertyConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Property Group Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property Group Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePropertyGroupConfig(PropertyGroupConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Protocols Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Protocols Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProtocolsConfig(ProtocolsConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Provider Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Provider Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProviderConfig(ProviderConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Query Agent Class Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Query Agent Class Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQueryAgentClassConfig(QueryAgentClassConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Revision Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Revision Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRevisionConfig(RevisionConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rule Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rule Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRuleConfig(RuleConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rule Config Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rule Config Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRuleConfigConfig(RuleConfigConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rules Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rules Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRulesConfig(RulesConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rulesets Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rulesets Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRulesetsConfig(RulesetsConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Security Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Security Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSecurityConfig(SecurityConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Security Controller</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Security Controller</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSecurityController(SecurityController object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Security Override Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Security Override Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSecurityOverrideConfig(SecurityOverrideConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Security Requester</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Security Requester</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSecurityRequester(SecurityRequester object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Set Property Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Set Property Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSetPropertyConfig(SetPropertyConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Shared Queue Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Shared Queue Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSharedQueueConfig(SharedQueueConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ssl Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ssl Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSslConfig(SslConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>System Property Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>System Property Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSystemPropertyConfig(SystemPropertyConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Terminal Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Terminal Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTerminalConfig(TerminalConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Uris And Refs Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Uris And Refs Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUrisAndRefsConfig(UrisAndRefsConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Uris Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Uris Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUrisConfig(UrisConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Field Encryption Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Field Encryption Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFieldEncryptionConfig(FieldEncryptionConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Entity Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entity Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEntityConfig(EntityConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Entity Set Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entity Set Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEntitySetConfig(EntitySetConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>LDM Connection Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>LDM Connection Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLDMConnectionConfig(LDMConnectionConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Publisher Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Publisher Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePublisherConfig(PublisherConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Live View Agent Class Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Live View Agent Class Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLiveViewAgentClassConfig(LiveViewAgentClassConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Composite Index Properties Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Composite Index Properties Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompositeIndexPropertiesConfig(CompositeIndexPropertiesConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Composite Index Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Composite Index Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompositeIndexConfig(CompositeIndexConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Composite Indexes Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Composite Indexes Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompositeIndexesConfig(CompositeIndexesConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //CddSwitch
