/**
 */
package com.tibco.be.util.config.cdd.util;

import com.tibco.be.util.config.cdd.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.be.util.config.cdd.CddPackage
 * @generated
 */
public class CddAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static CddPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CddAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = CddPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CddSwitch<Adapter> modelSwitch =
		new CddSwitch<Adapter>() {
			@Override
			public Adapter caseAgentClassConfig(AgentClassConfig object) {
				return createAgentClassConfigAdapter();
			}
			@Override
			public Adapter caseAgentClassesConfig(AgentClassesConfig object) {
				return createAgentClassesConfigAdapter();
			}
			@Override
			public Adapter caseAgentConfig(AgentConfig object) {
				return createAgentConfigAdapter();
			}
			@Override
			public Adapter caseAgentsConfig(AgentsConfig object) {
				return createAgentsConfigAdapter();
			}
			@Override
			public Adapter caseAlertConfigConfig(AlertConfigConfig object) {
				return createAlertConfigConfigAdapter();
			}
			@Override
			public Adapter caseAlertConfigSetConfig(AlertConfigSetConfig object) {
				return createAlertConfigSetConfigAdapter();
			}
			@Override
			public Adapter caseArtifactConfig(ArtifactConfig object) {
				return createArtifactConfigAdapter();
			}
			@Override
			public Adapter caseBackingStoreConfig(BackingStoreConfig object) {
				return createBackingStoreConfigAdapter();
			}
			@Override
			public Adapter caseBackingStoreForDomainObjectConfig(BackingStoreForDomainObjectConfig object) {
				return createBackingStoreForDomainObjectConfigAdapter();
			}
			@Override
			public Adapter caseBackingStoreForPropertiesConfig(BackingStoreForPropertiesConfig object) {
				return createBackingStoreForPropertiesConfigAdapter();
			}
			@Override
			public Adapter caseBackingStoreForPropertyConfig(BackingStoreForPropertyConfig object) {
				return createBackingStoreForPropertyConfigAdapter();
			}
			@Override
			public Adapter caseBusinessworksConfig(BusinessworksConfig object) {
				return createBusinessworksConfigAdapter();
			}
			@Override
			public Adapter caseCacheAgentClassConfig(CacheAgentClassConfig object) {
				return createCacheAgentClassConfigAdapter();
			}
			@Override
			public Adapter caseCacheManagerConfig(CacheManagerConfig object) {
				return createCacheManagerConfigAdapter();
			}
			@Override
			public Adapter caseCacheManagerSecurityConfig(CacheManagerSecurityConfig object) {
				return createCacheManagerSecurityConfigAdapter();
			}
			@Override
			public Adapter caseCddRoot(CddRoot object) {
				return createCddRootAdapter();
			}
			@Override
			public Adapter caseChildClusterMemberConfig(ChildClusterMemberConfig object) {
				return createChildClusterMemberConfigAdapter();
			}
			@Override
			public Adapter caseCiphersConfig(CiphersConfig object) {
				return createCiphersConfigAdapter();
			}
			@Override
			public Adapter caseClusterConfig(ClusterConfig object) {
				return createClusterConfigAdapter();
			}
			@Override
			public Adapter caseClusterMemberConfig(ClusterMemberConfig object) {
				return createClusterMemberConfigAdapter();
			}
			@Override
			public Adapter caseConditionConfig(ConditionConfig object) {
				return createConditionConfigAdapter();
			}
			@Override
			public Adapter caseConnectionConfig(ConnectionConfig object) {
				return createConnectionConfigAdapter();
			}
			@Override
			public Adapter caseDashboardAgentClassConfig(DashboardAgentClassConfig object) {
				return createDashboardAgentClassConfigAdapter();
			}
			@Override
			public Adapter caseDbConceptsConfig(DbConceptsConfig object) {
				return createDbConceptsConfigAdapter();
			}
			@Override
			public Adapter caseDestinationConfig(DestinationConfig object) {
				return createDestinationConfigAdapter();
			}
			@Override
			public Adapter caseDestinationGroupsConfig(DestinationGroupsConfig object) {
				return createDestinationGroupsConfigAdapter();
			}
			@Override
			public Adapter caseDestinationsConfig(DestinationsConfig object) {
				return createDestinationsConfigAdapter();
			}
			@Override
			public Adapter caseDomainObjectConfig(DomainObjectConfig object) {
				return createDomainObjectConfigAdapter();
			}
			@Override
			public Adapter caseDomainObjectsConfig(DomainObjectsConfig object) {
				return createDomainObjectsConfigAdapter();
			}
			@Override
			public Adapter caseEvictionConfig(EvictionConfig object) {
				return createEvictionConfigAdapter();
			}
			@Override
			public Adapter caseFilesConfig(FilesConfig object) {
				return createFilesConfigAdapter();
			}
			@Override
			public Adapter caseFunctionGroupsConfig(FunctionGroupsConfig object) {
				return createFunctionGroupsConfigAdapter();
			}
			@Override
			public Adapter caseFunctionsConfig(FunctionsConfig object) {
				return createFunctionsConfigAdapter();
			}
			@Override
			public Adapter caseGetPropertyConfig(GetPropertyConfig object) {
				return createGetPropertyConfigAdapter();
			}
			@Override
			public Adapter caseHttpConfig(HttpConfig object) {
				return createHttpConfigAdapter();
			}
			@Override
			public Adapter caseIndexConfig(IndexConfig object) {
				return createIndexConfigAdapter();
			}
			@Override
			public Adapter caseIndexesConfig(IndexesConfig object) {
				return createIndexesConfigAdapter();
			}
			@Override
			public Adapter caseInferenceAgentClassConfig(InferenceAgentClassConfig object) {
				return createInferenceAgentClassConfigAdapter();
			}
			@Override
			public Adapter caseInferenceEngineConfig(InferenceEngineConfig object) {
				return createInferenceEngineConfigAdapter();
			}
			@Override
			public Adapter caseJobManagerConfig(JobManagerConfig object) {
				return createJobManagerConfigAdapter();
			}
			@Override
			public Adapter caseLineLayoutConfig(LineLayoutConfig object) {
				return createLineLayoutConfigAdapter();
			}
			@Override
			public Adapter caseLoadBalancerAdhocConfigConfig(LoadBalancerAdhocConfigConfig object) {
				return createLoadBalancerAdhocConfigConfigAdapter();
			}
			@Override
			public Adapter caseLoadBalancerAdhocConfigsConfig(LoadBalancerAdhocConfigsConfig object) {
				return createLoadBalancerAdhocConfigsConfigAdapter();
			}
			@Override
			public Adapter caseLoadBalancerConfigsConfig(LoadBalancerConfigsConfig object) {
				return createLoadBalancerConfigsConfigAdapter();
			}
			@Override
			public Adapter caseLoadBalancerPairConfigConfig(LoadBalancerPairConfigConfig object) {
				return createLoadBalancerPairConfigConfigAdapter();
			}
			@Override
			public Adapter caseLoadBalancerPairConfigsConfig(LoadBalancerPairConfigsConfig object) {
				return createLoadBalancerPairConfigsConfigAdapter();
			}
			@Override
			public Adapter caseLoadConfig(LoadConfig object) {
				return createLoadConfigAdapter();
			}
			@Override
			public Adapter caseLocalCacheConfig(LocalCacheConfig object) {
				return createLocalCacheConfigAdapter();
			}
			@Override
			public Adapter caseLogConfigConfig(LogConfigConfig object) {
				return createLogConfigConfigAdapter();
			}
			@Override
			public Adapter caseLogConfigsConfig(LogConfigsConfig object) {
				return createLogConfigsConfigAdapter();
			}
			@Override
			public Adapter caseMemoryManagerConfig(MemoryManagerConfig object) {
				return createMemoryManagerConfigAdapter();
			}
			@Override
			public Adapter caseMmActionConfig(MmActionConfig object) {
				return createMmActionConfigAdapter();
			}
			@Override
			public Adapter caseMmActionConfigConfig(MmActionConfigConfig object) {
				return createMmActionConfigConfigAdapter();
			}
			@Override
			public Adapter caseMmActionConfigSetConfig(MmActionConfigSetConfig object) {
				return createMmActionConfigSetConfigAdapter();
			}
			@Override
			public Adapter caseMmAgentClassConfig(MmAgentClassConfig object) {
				return createMmAgentClassConfigAdapter();
			}
			@Override
			public Adapter caseMmAlertConfig(MmAlertConfig object) {
				return createMmAlertConfigAdapter();
			}
			@Override
			public Adapter caseMmExecuteCommandConfig(MmExecuteCommandConfig object) {
				return createMmExecuteCommandConfigAdapter();
			}
			@Override
			public Adapter caseMmHealthLevelConfig(MmHealthLevelConfig object) {
				return createMmHealthLevelConfigAdapter();
			}
			@Override
			public Adapter caseMmSendEmailConfig(MmSendEmailConfig object) {
				return createMmSendEmailConfigAdapter();
			}
			@Override
			public Adapter caseMmTriggerConditionConfig(MmTriggerConditionConfig object) {
				return createMmTriggerConditionConfigAdapter();
			}
			@Override
			public Adapter caseNotificationConfig(NotificationConfig object) {
				return createNotificationConfigAdapter();
			}
			@Override
			public Adapter caseObjectManagementConfig(ObjectManagementConfig object) {
				return createObjectManagementConfigAdapter();
			}
			@Override
			public Adapter caseObjectManagerConfig(ObjectManagerConfig object) {
				return createObjectManagerConfigAdapter();
			}
			@Override
			public Adapter caseObjectTableConfig(ObjectTableConfig object) {
				return createObjectTableConfigAdapter();
			}
			@Override
			public Adapter caseOverrideConfig(OverrideConfig object) {
				return createOverrideConfigAdapter();
			}
			@Override
			public Adapter caseProcessAgentClassConfig(ProcessAgentClassConfig object) {
				return createProcessAgentClassConfigAdapter();
			}
			@Override
			public Adapter caseProcessConfig(ProcessConfig object) {
				return createProcessConfigAdapter();
			}
			@Override
			public Adapter caseProcessEngineConfig(ProcessEngineConfig object) {
				return createProcessEngineConfigAdapter();
			}
			@Override
			public Adapter caseProcessesConfig(ProcessesConfig object) {
				return createProcessesConfigAdapter();
			}
			@Override
			public Adapter caseProcessGroupsConfig(ProcessGroupsConfig object) {
				return createProcessGroupsConfigAdapter();
			}
			@Override
			public Adapter caseProcessingUnitConfig(ProcessingUnitConfig object) {
				return createProcessingUnitConfigAdapter();
			}
			@Override
			public Adapter caseProcessingUnitsConfig(ProcessingUnitsConfig object) {
				return createProcessingUnitsConfigAdapter();
			}
			@Override
			public Adapter caseProcessingUnitSecurityConfig(ProcessingUnitSecurityConfig object) {
				return createProcessingUnitSecurityConfigAdapter();
			}
			@Override
			public Adapter caseProjectionConfig(ProjectionConfig object) {
				return createProjectionConfigAdapter();
			}
			@Override
			public Adapter casePropertyConfig(PropertyConfig object) {
				return createPropertyConfigAdapter();
			}
			@Override
			public Adapter casePropertyGroupConfig(PropertyGroupConfig object) {
				return createPropertyGroupConfigAdapter();
			}
			@Override
			public Adapter caseProtocolsConfig(ProtocolsConfig object) {
				return createProtocolsConfigAdapter();
			}
			@Override
			public Adapter caseProviderConfig(ProviderConfig object) {
				return createProviderConfigAdapter();
			}
			@Override
			public Adapter caseQueryAgentClassConfig(QueryAgentClassConfig object) {
				return createQueryAgentClassConfigAdapter();
			}
			@Override
			public Adapter caseRevisionConfig(RevisionConfig object) {
				return createRevisionConfigAdapter();
			}
			@Override
			public Adapter caseRuleConfig(RuleConfig object) {
				return createRuleConfigAdapter();
			}
			@Override
			public Adapter caseRuleConfigConfig(RuleConfigConfig object) {
				return createRuleConfigConfigAdapter();
			}
			@Override
			public Adapter caseRulesConfig(RulesConfig object) {
				return createRulesConfigAdapter();
			}
			@Override
			public Adapter caseRulesetsConfig(RulesetsConfig object) {
				return createRulesetsConfigAdapter();
			}
			@Override
			public Adapter caseSecurityConfig(SecurityConfig object) {
				return createSecurityConfigAdapter();
			}
			@Override
			public Adapter caseSecurityController(SecurityController object) {
				return createSecurityControllerAdapter();
			}
			@Override
			public Adapter caseSecurityOverrideConfig(SecurityOverrideConfig object) {
				return createSecurityOverrideConfigAdapter();
			}
			@Override
			public Adapter caseSecurityRequester(SecurityRequester object) {
				return createSecurityRequesterAdapter();
			}
			@Override
			public Adapter caseSetPropertyConfig(SetPropertyConfig object) {
				return createSetPropertyConfigAdapter();
			}
			@Override
			public Adapter caseSharedQueueConfig(SharedQueueConfig object) {
				return createSharedQueueConfigAdapter();
			}
			@Override
			public Adapter caseSslConfig(SslConfig object) {
				return createSslConfigAdapter();
			}
			@Override
			public Adapter caseSystemPropertyConfig(SystemPropertyConfig object) {
				return createSystemPropertyConfigAdapter();
			}
			@Override
			public Adapter caseTerminalConfig(TerminalConfig object) {
				return createTerminalConfigAdapter();
			}
			@Override
			public Adapter caseUrisAndRefsConfig(UrisAndRefsConfig object) {
				return createUrisAndRefsConfigAdapter();
			}
			@Override
			public Adapter caseUrisConfig(UrisConfig object) {
				return createUrisConfigAdapter();
			}
			@Override
			public Adapter caseFieldEncryptionConfig(FieldEncryptionConfig object) {
				return createFieldEncryptionConfigAdapter();
			}
			@Override
			public Adapter caseEntityConfig(EntityConfig object) {
				return createEntityConfigAdapter();
			}
			@Override
			public Adapter caseEntitySetConfig(EntitySetConfig object) {
				return createEntitySetConfigAdapter();
			}
			@Override
			public Adapter caseLDMConnectionConfig(LDMConnectionConfig object) {
				return createLDMConnectionConfigAdapter();
			}
			@Override
			public Adapter casePublisherConfig(PublisherConfig object) {
				return createPublisherConfigAdapter();
			}
			@Override
			public Adapter caseLiveViewAgentClassConfig(LiveViewAgentClassConfig object) {
				return createLiveViewAgentClassConfigAdapter();
			}
			@Override
			public Adapter caseCompositeIndexPropertiesConfig(CompositeIndexPropertiesConfig object) {
				return createCompositeIndexPropertiesConfigAdapter();
			}
			@Override
			public Adapter caseCompositeIndexConfig(CompositeIndexConfig object) {
				return createCompositeIndexConfigAdapter();
			}
			@Override
			public Adapter caseCompositeIndexesConfig(CompositeIndexesConfig object) {
				return createCompositeIndexesConfigAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.AgentClassConfig <em>Agent Class Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.AgentClassConfig
	 * @generated
	 */
	public Adapter createAgentClassConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.AgentClassesConfig <em>Agent Classes Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.AgentClassesConfig
	 * @generated
	 */
	public Adapter createAgentClassesConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.AgentConfig <em>Agent Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.AgentConfig
	 * @generated
	 */
	public Adapter createAgentConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.AgentsConfig <em>Agents Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.AgentsConfig
	 * @generated
	 */
	public Adapter createAgentsConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.AlertConfigConfig <em>Alert Config Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.AlertConfigConfig
	 * @generated
	 */
	public Adapter createAlertConfigConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.AlertConfigSetConfig <em>Alert Config Set Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.AlertConfigSetConfig
	 * @generated
	 */
	public Adapter createAlertConfigSetConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.ArtifactConfig <em>Artifact Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.ArtifactConfig
	 * @generated
	 */
	public Adapter createArtifactConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.BackingStoreConfig <em>Backing Store Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.BackingStoreConfig
	 * @generated
	 */
	public Adapter createBackingStoreConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.BackingStoreForDomainObjectConfig <em>Backing Store For Domain Object Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.BackingStoreForDomainObjectConfig
	 * @generated
	 */
	public Adapter createBackingStoreForDomainObjectConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.BackingStoreForPropertiesConfig <em>Backing Store For Properties Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.BackingStoreForPropertiesConfig
	 * @generated
	 */
	public Adapter createBackingStoreForPropertiesConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.BackingStoreForPropertyConfig <em>Backing Store For Property Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.BackingStoreForPropertyConfig
	 * @generated
	 */
	public Adapter createBackingStoreForPropertyConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.BusinessworksConfig <em>Businessworks Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.BusinessworksConfig
	 * @generated
	 */
	public Adapter createBusinessworksConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.CacheAgentClassConfig <em>Cache Agent Class Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.CacheAgentClassConfig
	 * @generated
	 */
	public Adapter createCacheAgentClassConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.CacheManagerConfig <em>Cache Manager Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.CacheManagerConfig
	 * @generated
	 */
	public Adapter createCacheManagerConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.CacheManagerSecurityConfig <em>Cache Manager Security Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.CacheManagerSecurityConfig
	 * @generated
	 */
	public Adapter createCacheManagerSecurityConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.CddRoot <em>Root</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.CddRoot
	 * @generated
	 */
	public Adapter createCddRootAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.ChildClusterMemberConfig <em>Child Cluster Member Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.ChildClusterMemberConfig
	 * @generated
	 */
	public Adapter createChildClusterMemberConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.CiphersConfig <em>Ciphers Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.CiphersConfig
	 * @generated
	 */
	public Adapter createCiphersConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.ClusterConfig <em>Cluster Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.ClusterConfig
	 * @generated
	 */
	public Adapter createClusterConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.ClusterMemberConfig <em>Cluster Member Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.ClusterMemberConfig
	 * @generated
	 */
	public Adapter createClusterMemberConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.ConditionConfig <em>Condition Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.ConditionConfig
	 * @generated
	 */
	public Adapter createConditionConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.ConnectionConfig <em>Connection Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.ConnectionConfig
	 * @generated
	 */
	public Adapter createConnectionConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.DashboardAgentClassConfig <em>Dashboard Agent Class Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.DashboardAgentClassConfig
	 * @generated
	 */
	public Adapter createDashboardAgentClassConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.DbConceptsConfig <em>Db Concepts Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.DbConceptsConfig
	 * @generated
	 */
	public Adapter createDbConceptsConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.DestinationConfig <em>Destination Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.DestinationConfig
	 * @generated
	 */
	public Adapter createDestinationConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.DestinationGroupsConfig <em>Destination Groups Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.DestinationGroupsConfig
	 * @generated
	 */
	public Adapter createDestinationGroupsConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.DestinationsConfig <em>Destinations Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.DestinationsConfig
	 * @generated
	 */
	public Adapter createDestinationsConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.DomainObjectConfig <em>Domain Object Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.DomainObjectConfig
	 * @generated
	 */
	public Adapter createDomainObjectConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.DomainObjectsConfig <em>Domain Objects Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.DomainObjectsConfig
	 * @generated
	 */
	public Adapter createDomainObjectsConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.EvictionConfig <em>Eviction Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.EvictionConfig
	 * @generated
	 */
	public Adapter createEvictionConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.FilesConfig <em>Files Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.FilesConfig
	 * @generated
	 */
	public Adapter createFilesConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.FunctionGroupsConfig <em>Function Groups Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.FunctionGroupsConfig
	 * @generated
	 */
	public Adapter createFunctionGroupsConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.FunctionsConfig <em>Functions Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.FunctionsConfig
	 * @generated
	 */
	public Adapter createFunctionsConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.GetPropertyConfig <em>Get Property Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.GetPropertyConfig
	 * @generated
	 */
	public Adapter createGetPropertyConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.HttpConfig <em>Http Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.HttpConfig
	 * @generated
	 */
	public Adapter createHttpConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.IndexConfig <em>Index Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.IndexConfig
	 * @generated
	 */
	public Adapter createIndexConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.IndexesConfig <em>Indexes Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.IndexesConfig
	 * @generated
	 */
	public Adapter createIndexesConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.InferenceAgentClassConfig <em>Inference Agent Class Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.InferenceAgentClassConfig
	 * @generated
	 */
	public Adapter createInferenceAgentClassConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.InferenceEngineConfig <em>Inference Engine Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.InferenceEngineConfig
	 * @generated
	 */
	public Adapter createInferenceEngineConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.JobManagerConfig <em>Job Manager Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.JobManagerConfig
	 * @generated
	 */
	public Adapter createJobManagerConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.LineLayoutConfig <em>Line Layout Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.LineLayoutConfig
	 * @generated
	 */
	public Adapter createLineLayoutConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.LoadBalancerAdhocConfigConfig <em>Load Balancer Adhoc Config Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.LoadBalancerAdhocConfigConfig
	 * @generated
	 */
	public Adapter createLoadBalancerAdhocConfigConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.LoadBalancerAdhocConfigsConfig <em>Load Balancer Adhoc Configs Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.LoadBalancerAdhocConfigsConfig
	 * @generated
	 */
	public Adapter createLoadBalancerAdhocConfigsConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.LoadBalancerConfigsConfig <em>Load Balancer Configs Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.LoadBalancerConfigsConfig
	 * @generated
	 */
	public Adapter createLoadBalancerConfigsConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig <em>Load Balancer Pair Config Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig
	 * @generated
	 */
	public Adapter createLoadBalancerPairConfigConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.LoadBalancerPairConfigsConfig <em>Load Balancer Pair Configs Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.LoadBalancerPairConfigsConfig
	 * @generated
	 */
	public Adapter createLoadBalancerPairConfigsConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.LoadConfig <em>Load Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.LoadConfig
	 * @generated
	 */
	public Adapter createLoadConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.LocalCacheConfig <em>Local Cache Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.LocalCacheConfig
	 * @generated
	 */
	public Adapter createLocalCacheConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.LogConfigConfig <em>Log Config Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.LogConfigConfig
	 * @generated
	 */
	public Adapter createLogConfigConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.LogConfigsConfig <em>Log Configs Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.LogConfigsConfig
	 * @generated
	 */
	public Adapter createLogConfigsConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.MemoryManagerConfig <em>Memory Manager Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.MemoryManagerConfig
	 * @generated
	 */
	public Adapter createMemoryManagerConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.MmActionConfig <em>Mm Action Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.MmActionConfig
	 * @generated
	 */
	public Adapter createMmActionConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.MmActionConfigConfig <em>Mm Action Config Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.MmActionConfigConfig
	 * @generated
	 */
	public Adapter createMmActionConfigConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.MmActionConfigSetConfig <em>Mm Action Config Set Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.MmActionConfigSetConfig
	 * @generated
	 */
	public Adapter createMmActionConfigSetConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.MmAgentClassConfig <em>Mm Agent Class Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.MmAgentClassConfig
	 * @generated
	 */
	public Adapter createMmAgentClassConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.MmAlertConfig <em>Mm Alert Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.MmAlertConfig
	 * @generated
	 */
	public Adapter createMmAlertConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.MmExecuteCommandConfig <em>Mm Execute Command Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.MmExecuteCommandConfig
	 * @generated
	 */
	public Adapter createMmExecuteCommandConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.MmHealthLevelConfig <em>Mm Health Level Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.MmHealthLevelConfig
	 * @generated
	 */
	public Adapter createMmHealthLevelConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.MmSendEmailConfig <em>Mm Send Email Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.MmSendEmailConfig
	 * @generated
	 */
	public Adapter createMmSendEmailConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.MmTriggerConditionConfig <em>Mm Trigger Condition Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.MmTriggerConditionConfig
	 * @generated
	 */
	public Adapter createMmTriggerConditionConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.NotificationConfig <em>Notification Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.NotificationConfig
	 * @generated
	 */
	public Adapter createNotificationConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.ObjectManagementConfig <em>Object Management Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.ObjectManagementConfig
	 * @generated
	 */
	public Adapter createObjectManagementConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.ObjectManagerConfig <em>Object Manager Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.ObjectManagerConfig
	 * @generated
	 */
	public Adapter createObjectManagerConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.ObjectTableConfig <em>Object Table Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.ObjectTableConfig
	 * @generated
	 */
	public Adapter createObjectTableConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.OverrideConfig <em>Override Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.OverrideConfig
	 * @generated
	 */
	public Adapter createOverrideConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.ProcessAgentClassConfig <em>Process Agent Class Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.ProcessAgentClassConfig
	 * @generated
	 */
	public Adapter createProcessAgentClassConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.ProcessConfig <em>Process Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.ProcessConfig
	 * @generated
	 */
	public Adapter createProcessConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.ProcessEngineConfig <em>Process Engine Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.ProcessEngineConfig
	 * @generated
	 */
	public Adapter createProcessEngineConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.ProcessesConfig <em>Processes Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.ProcessesConfig
	 * @generated
	 */
	public Adapter createProcessesConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.ProcessGroupsConfig <em>Process Groups Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.ProcessGroupsConfig
	 * @generated
	 */
	public Adapter createProcessGroupsConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.ProcessingUnitConfig <em>Processing Unit Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.ProcessingUnitConfig
	 * @generated
	 */
	public Adapter createProcessingUnitConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.ProcessingUnitsConfig <em>Processing Units Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.ProcessingUnitsConfig
	 * @generated
	 */
	public Adapter createProcessingUnitsConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.ProcessingUnitSecurityConfig <em>Processing Unit Security Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.ProcessingUnitSecurityConfig
	 * @generated
	 */
	public Adapter createProcessingUnitSecurityConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.ProjectionConfig <em>Projection Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.ProjectionConfig
	 * @generated
	 */
	public Adapter createProjectionConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.PropertyConfig <em>Property Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.PropertyConfig
	 * @generated
	 */
	public Adapter createPropertyConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.PropertyGroupConfig <em>Property Group Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.PropertyGroupConfig
	 * @generated
	 */
	public Adapter createPropertyGroupConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.ProtocolsConfig <em>Protocols Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.ProtocolsConfig
	 * @generated
	 */
	public Adapter createProtocolsConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.ProviderConfig <em>Provider Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.ProviderConfig
	 * @generated
	 */
	public Adapter createProviderConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.QueryAgentClassConfig <em>Query Agent Class Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.QueryAgentClassConfig
	 * @generated
	 */
	public Adapter createQueryAgentClassConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.RevisionConfig <em>Revision Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.RevisionConfig
	 * @generated
	 */
	public Adapter createRevisionConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.RuleConfig <em>Rule Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.RuleConfig
	 * @generated
	 */
	public Adapter createRuleConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.RuleConfigConfig <em>Rule Config Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.RuleConfigConfig
	 * @generated
	 */
	public Adapter createRuleConfigConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.RulesConfig <em>Rules Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.RulesConfig
	 * @generated
	 */
	public Adapter createRulesConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.RulesetsConfig <em>Rulesets Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.RulesetsConfig
	 * @generated
	 */
	public Adapter createRulesetsConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.SecurityConfig <em>Security Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.SecurityConfig
	 * @generated
	 */
	public Adapter createSecurityConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.SecurityController <em>Security Controller</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.SecurityController
	 * @generated
	 */
	public Adapter createSecurityControllerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.SecurityOverrideConfig <em>Security Override Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.SecurityOverrideConfig
	 * @generated
	 */
	public Adapter createSecurityOverrideConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.SecurityRequester <em>Security Requester</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.SecurityRequester
	 * @generated
	 */
	public Adapter createSecurityRequesterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.SetPropertyConfig <em>Set Property Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.SetPropertyConfig
	 * @generated
	 */
	public Adapter createSetPropertyConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.SharedQueueConfig <em>Shared Queue Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.SharedQueueConfig
	 * @generated
	 */
	public Adapter createSharedQueueConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.SslConfig <em>Ssl Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.SslConfig
	 * @generated
	 */
	public Adapter createSslConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.SystemPropertyConfig <em>System Property Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.SystemPropertyConfig
	 * @generated
	 */
	public Adapter createSystemPropertyConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.TerminalConfig <em>Terminal Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.TerminalConfig
	 * @generated
	 */
	public Adapter createTerminalConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.UrisAndRefsConfig <em>Uris And Refs Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.UrisAndRefsConfig
	 * @generated
	 */
	public Adapter createUrisAndRefsConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.UrisConfig <em>Uris Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.UrisConfig
	 * @generated
	 */
	public Adapter createUrisConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.FieldEncryptionConfig <em>Field Encryption Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.FieldEncryptionConfig
	 * @generated
	 */
	public Adapter createFieldEncryptionConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.EntityConfig <em>Entity Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.EntityConfig
	 * @generated
	 */
	public Adapter createEntityConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.EntitySetConfig <em>Entity Set Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.EntitySetConfig
	 * @generated
	 */
	public Adapter createEntitySetConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.LDMConnectionConfig <em>LDM Connection Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.LDMConnectionConfig
	 * @generated
	 */
	public Adapter createLDMConnectionConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.PublisherConfig <em>Publisher Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.PublisherConfig
	 * @generated
	 */
	public Adapter createPublisherConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.LiveViewAgentClassConfig <em>Live View Agent Class Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.LiveViewAgentClassConfig
	 * @generated
	 */
	public Adapter createLiveViewAgentClassConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.CompositeIndexPropertiesConfig <em>Composite Index Properties Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.CompositeIndexPropertiesConfig
	 * @generated
	 */
	public Adapter createCompositeIndexPropertiesConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.CompositeIndexConfig <em>Composite Index Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.CompositeIndexConfig
	 * @generated
	 */
	public Adapter createCompositeIndexConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.cdd.CompositeIndexesConfig <em>Composite Indexes Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.cdd.CompositeIndexesConfig
	 * @generated
	 */
	public Adapter createCompositeIndexesConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //CddAdapterFactory
