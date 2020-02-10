/**
 */
package com.tibco.be.util.config.cdd;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.tibco.be.util.config.cdd.CddFactory
 * @model kind="package"
 *        annotation="http://www.w3.org/XML/1998/namespace lang='en'"
 * @generated
 */
public interface CddPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "cdd";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tibco.com/businessevents/configuration/5.6";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "cdd";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CddPackage eINSTANCE = com.tibco.be.util.config.cdd.impl.CddPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.ArtifactConfigImpl <em>Artifact Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.ArtifactConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getArtifactConfig()
	 * @generated
	 */
	int ARTIFACT_CONFIG = 6;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_CONFIG__ID = 0;

	/**
	 * The number of structural features of the '<em>Artifact Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_CONFIG_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.AgentClassConfigImpl <em>Agent Class Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.AgentClassConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getAgentClassConfig()
	 * @generated
	 */
	int AGENT_CLASS_CONFIG = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_CLASS_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The number of structural features of the '<em>Agent Class Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_CLASS_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.AgentClassesConfigImpl <em>Agent Classes Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.AgentClassesConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getAgentClassesConfig()
	 * @generated
	 */
	int AGENT_CLASSES_CONFIG = 1;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_CLASSES_CONFIG__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Cache Agent Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_CLASSES_CONFIG__CACHE_AGENT_CONFIG = 1;

	/**
	 * The feature id for the '<em><b>Dashboard Agent Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_CLASSES_CONFIG__DASHBOARD_AGENT_CONFIG = 2;

	/**
	 * The feature id for the '<em><b>Inference Agent Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_CLASSES_CONFIG__INFERENCE_AGENT_CONFIG = 3;

	/**
	 * The feature id for the '<em><b>Query Agent Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_CLASSES_CONFIG__QUERY_AGENT_CONFIG = 4;

	/**
	 * The feature id for the '<em><b>Mm Agent Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_CLASSES_CONFIG__MM_AGENT_CONFIG = 5;

	/**
	 * The feature id for the '<em><b>Process Agent Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_CLASSES_CONFIG__PROCESS_AGENT_CONFIG = 6;

	/**
	 * The feature id for the '<em><b>Live View Agent Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_CLASSES_CONFIG__LIVE_VIEW_AGENT_CONFIG = 7;

	/**
	 * The number of structural features of the '<em>Agent Classes Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_CLASSES_CONFIG_FEATURE_COUNT = 8;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.AgentConfigImpl <em>Agent Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.AgentConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getAgentConfig()
	 * @generated
	 */
	int AGENT_CONFIG = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_CONFIG__MIXED = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_CONFIG__REF = ARTIFACT_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_CONFIG__KEY = ARTIFACT_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_CONFIG__PRIORITY = ARTIFACT_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Agent Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.AgentsConfigImpl <em>Agents Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.AgentsConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getAgentsConfig()
	 * @generated
	 */
	int AGENTS_CONFIG = 3;

	/**
	 * The feature id for the '<em><b>Agent</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENTS_CONFIG__AGENT = 0;

	/**
	 * The number of structural features of the '<em>Agents Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENTS_CONFIG_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.AlertConfigConfigImpl <em>Alert Config Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.AlertConfigConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getAlertConfigConfig()
	 * @generated
	 */
	int ALERT_CONFIG_CONFIG = 4;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_CONFIG_CONFIG__CONDITION = 0;

	/**
	 * The feature id for the '<em><b>Projection</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_CONFIG_CONFIG__PROJECTION = 1;

	/**
	 * The feature id for the '<em><b>Alert Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_CONFIG_CONFIG__ALERT_NAME = 2;

	/**
	 * The number of structural features of the '<em>Alert Config Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_CONFIG_CONFIG_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.AlertConfigSetConfigImpl <em>Alert Config Set Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.AlertConfigSetConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getAlertConfigSetConfig()
	 * @generated
	 */
	int ALERT_CONFIG_SET_CONFIG = 5;

	/**
	 * The feature id for the '<em><b>Alert Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_CONFIG_SET_CONFIG__ALERT_CONFIG = 0;

	/**
	 * The number of structural features of the '<em>Alert Config Set Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_CONFIG_SET_CONFIG_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.BackingStoreConfigImpl <em>Backing Store Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.BackingStoreConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getBackingStoreConfig()
	 * @generated
	 */
	int BACKING_STORE_CONFIG = 7;

	/**
	 * The feature id for the '<em><b>Cache Aside</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKING_STORE_CONFIG__CACHE_ASIDE = 0;

	/**
	 * The feature id for the '<em><b>Cache Loader Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKING_STORE_CONFIG__CACHE_LOADER_CLASS = 1;

	/**
	 * The feature id for the '<em><b>Data Store Path</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKING_STORE_CONFIG__DATA_STORE_PATH = 2;

	/**
	 * The feature id for the '<em><b>Enforce Pools</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKING_STORE_CONFIG__ENFORCE_POOLS = 3;

	/**
	 * The feature id for the '<em><b>Persistence Option</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKING_STORE_CONFIG__PERSISTENCE_OPTION = 4;

	/**
	 * The feature id for the '<em><b>Persistence Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKING_STORE_CONFIG__PERSISTENCE_POLICY = 5;

	/**
	 * The feature id for the '<em><b>Parallel Recovery</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKING_STORE_CONFIG__PARALLEL_RECOVERY = 6;

	/**
	 * The feature id for the '<em><b>Primary Connection</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKING_STORE_CONFIG__PRIMARY_CONNECTION = 7;

	/**
	 * The feature id for the '<em><b>Secondary Connection</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKING_STORE_CONFIG__SECONDARY_CONNECTION = 8;

	/**
	 * The feature id for the '<em><b>Strategy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKING_STORE_CONFIG__STRATEGY = 9;

	/**
	 * The feature id for the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKING_STORE_CONFIG__TYPE = 10;

	/**
	 * The number of structural features of the '<em>Backing Store Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKING_STORE_CONFIG_FEATURE_COUNT = 11;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.BackingStoreForDomainObjectConfigImpl <em>Backing Store For Domain Object Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.BackingStoreForDomainObjectConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getBackingStoreForDomainObjectConfig()
	 * @generated
	 */
	int BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG = 8;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__PROPERTIES = 0;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__ENABLED = 1;

	/**
	 * The feature id for the '<em><b>Table Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__TABLE_NAME = 2;

	/**
	 * The number of structural features of the '<em>Backing Store For Domain Object Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.BackingStoreForPropertiesConfigImpl <em>Backing Store For Properties Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.BackingStoreForPropertiesConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getBackingStoreForPropertiesConfig()
	 * @generated
	 */
	int BACKING_STORE_FOR_PROPERTIES_CONFIG = 9;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKING_STORE_FOR_PROPERTIES_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKING_STORE_FOR_PROPERTIES_CONFIG__GROUP = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKING_STORE_FOR_PROPERTIES_CONFIG__PROPERTY = ARTIFACT_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Backing Store For Properties Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKING_STORE_FOR_PROPERTIES_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.BackingStoreForPropertyConfigImpl <em>Backing Store For Property Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.BackingStoreForPropertyConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getBackingStoreForPropertyConfig()
	 * @generated
	 */
	int BACKING_STORE_FOR_PROPERTY_CONFIG = 10;

	/**
	 * The feature id for the '<em><b>Reverse References</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKING_STORE_FOR_PROPERTY_CONFIG__REVERSE_REFERENCES = 0;

	/**
	 * The feature id for the '<em><b>Max Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKING_STORE_FOR_PROPERTY_CONFIG__MAX_SIZE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKING_STORE_FOR_PROPERTY_CONFIG__NAME = 2;

	/**
	 * The number of structural features of the '<em>Backing Store For Property Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKING_STORE_FOR_PROPERTY_CONFIG_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.BusinessworksConfigImpl <em>Businessworks Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.BusinessworksConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getBusinessworksConfig()
	 * @generated
	 */
	int BUSINESSWORKS_CONFIG = 11;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESSWORKS_CONFIG__URI = 0;

	/**
	 * The number of structural features of the '<em>Businessworks Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESSWORKS_CONFIG_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.CacheAgentClassConfigImpl <em>Cache Agent Class Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.CacheAgentClassConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getCacheAgentClassConfig()
	 * @generated
	 */
	int CACHE_AGENT_CLASS_CONFIG = 12;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE_AGENT_CLASS_CONFIG__ID = AGENT_CLASS_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Property Group</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE_AGENT_CLASS_CONFIG__PROPERTY_GROUP = AGENT_CLASS_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Cache Agent Class Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE_AGENT_CLASS_CONFIG_FEATURE_COUNT = AGENT_CLASS_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.ObjectManagerConfigImpl <em>Object Manager Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.ObjectManagerConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getObjectManagerConfig()
	 * @generated
	 */
	int OBJECT_MANAGER_CONFIG = 62;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_MANAGER_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The number of structural features of the '<em>Object Manager Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_MANAGER_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.CacheManagerConfigImpl <em>Cache Manager Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.CacheManagerConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getCacheManagerConfig()
	 * @generated
	 */
	int CACHE_MANAGER_CONFIG = 13;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE_MANAGER_CONFIG__ID = OBJECT_MANAGER_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Cache Agent Quorum</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE_MANAGER_CONFIG__CACHE_AGENT_QUORUM = OBJECT_MANAGER_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Backing Store</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE_MANAGER_CONFIG__BACKING_STORE = OBJECT_MANAGER_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Domain Objects</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE_MANAGER_CONFIG__DOMAIN_OBJECTS = OBJECT_MANAGER_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Provider</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE_MANAGER_CONFIG__PROVIDER = OBJECT_MANAGER_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Backup Copies</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE_MANAGER_CONFIG__BACKUP_COPIES = OBJECT_MANAGER_CONFIG_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Entity Cache Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE_MANAGER_CONFIG__ENTITY_CACHE_SIZE = OBJECT_MANAGER_CONFIG_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Object Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE_MANAGER_CONFIG__OBJECT_TABLE = OBJECT_MANAGER_CONFIG_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Discovery URL</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE_MANAGER_CONFIG__DISCOVERY_URL = OBJECT_MANAGER_CONFIG_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Listen URL</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE_MANAGER_CONFIG__LISTEN_URL = OBJECT_MANAGER_CONFIG_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Remote Listen URL</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE_MANAGER_CONFIG__REMOTE_LISTEN_URL = OBJECT_MANAGER_CONFIG_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Protocol Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE_MANAGER_CONFIG__PROTOCOL_TIMEOUT = OBJECT_MANAGER_CONFIG_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Read Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE_MANAGER_CONFIG__READ_TIMEOUT = OBJECT_MANAGER_CONFIG_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Write Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE_MANAGER_CONFIG__WRITE_TIMEOUT = OBJECT_MANAGER_CONFIG_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Lock Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE_MANAGER_CONFIG__LOCK_TIMEOUT = OBJECT_MANAGER_CONFIG_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Shoutdown Wait</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE_MANAGER_CONFIG__SHOUTDOWN_WAIT = OBJECT_MANAGER_CONFIG_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Workerthreads Count</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE_MANAGER_CONFIG__WORKERTHREADS_COUNT = OBJECT_MANAGER_CONFIG_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Explicit Tuple</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE_MANAGER_CONFIG__EXPLICIT_TUPLE = OBJECT_MANAGER_CONFIG_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Security Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE_MANAGER_CONFIG__SECURITY_CONFIG = OBJECT_MANAGER_CONFIG_FEATURE_COUNT + 17;

	/**
	 * The number of structural features of the '<em>Cache Manager Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE_MANAGER_CONFIG_FEATURE_COUNT = OBJECT_MANAGER_CONFIG_FEATURE_COUNT + 18;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.SecurityConfigImpl <em>Security Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.SecurityConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getSecurityConfig()
	 * @generated
	 */
	int SECURITY_CONFIG = 84;

	/**
	 * The number of structural features of the '<em>Security Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_CONFIG_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.CacheManagerSecurityConfigImpl <em>Cache Manager Security Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.CacheManagerSecurityConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getCacheManagerSecurityConfig()
	 * @generated
	 */
	int CACHE_MANAGER_SECURITY_CONFIG = 14;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE_MANAGER_SECURITY_CONFIG__ENABLED = SECURITY_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Controller</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE_MANAGER_SECURITY_CONFIG__CONTROLLER = SECURITY_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Requester</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE_MANAGER_SECURITY_CONFIG__REQUESTER = SECURITY_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Cache Manager Security Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE_MANAGER_SECURITY_CONFIG_FEATURE_COUNT = SECURITY_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.CddRootImpl <em>Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.CddRootImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getCddRoot()
	 * @generated
	 */
	int CDD_ROOT = 15;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__MIXED = 0;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__XMLNS_PREFIX_MAP = 1;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__XSI_SCHEMA_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Accept Count</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__ACCEPT_COUNT = 3;

	/**
	 * The feature id for the '<em><b>Address</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__ADDRESS = 4;

	/**
	 * The feature id for the '<em><b>Adhoc Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__ADHOC_CONFIG = 5;

	/**
	 * The feature id for the '<em><b>Adhoc Configs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__ADHOC_CONFIGS = 6;

	/**
	 * The feature id for the '<em><b>Agent</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__AGENT = 7;

	/**
	 * The feature id for the '<em><b>Agent Classes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__AGENT_CLASSES = 8;

	/**
	 * The feature id for the '<em><b>Agents</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__AGENTS = 9;

	/**
	 * The feature id for the '<em><b>Alert Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__ALERT_CONFIG = 10;

	/**
	 * The feature id for the '<em><b>Alert Config Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__ALERT_CONFIG_SET = 11;

	/**
	 * The feature id for the '<em><b>Append</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__APPEND = 12;

	/**
	 * The feature id for the '<em><b>Arg</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__ARG = 13;

	/**
	 * The feature id for the '<em><b>Author</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__AUTHOR = 14;

	/**
	 * The feature id for the '<em><b>Backing Store</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__BACKING_STORE = 15;

	/**
	 * The feature id for the '<em><b>Backup Copies</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__BACKUP_COPIES = 16;

	/**
	 * The feature id for the '<em><b>Businessworks</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__BUSINESSWORKS = 17;

	/**
	 * The feature id for the '<em><b>Cache</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CACHE = 18;

	/**
	 * The feature id for the '<em><b>Cache Agent Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CACHE_AGENT_CLASS = 19;

	/**
	 * The feature id for the '<em><b>Cache Agent Quorum</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CACHE_AGENT_QUORUM = 20;

	/**
	 * The feature id for the '<em><b>Cache Aside</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CACHE_ASIDE = 21;

	/**
	 * The feature id for the '<em><b>Cache Limited</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CACHE_LIMITED = 22;

	/**
	 * The feature id for the '<em><b>Cache Loader Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CACHE_LOADER_CLASS = 23;

	/**
	 * The feature id for the '<em><b>Cache Manager</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CACHE_MANAGER = 24;

	/**
	 * The feature id for the '<em><b>Cache Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CACHE_MODE = 25;

	/**
	 * The feature id for the '<em><b>Cache Storage Enabled</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CACHE_STORAGE_ENABLED = 26;

	/**
	 * The feature id for the '<em><b>Certificate Key File</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CERTIFICATE_KEY_FILE = 27;

	/**
	 * The feature id for the '<em><b>Channel</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CHANNEL = 28;

	/**
	 * The feature id for the '<em><b>Check For Duplicates</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CHECK_FOR_DUPLICATES = 29;

	/**
	 * The feature id for the '<em><b>Check For Version</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CHECK_FOR_VERSION = 30;

	/**
	 * The feature id for the '<em><b>Check Interval</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CHECK_INTERVAL = 31;

	/**
	 * The feature id for the '<em><b>Checkpoint Interval</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CHECKPOINT_INTERVAL = 32;

	/**
	 * The feature id for the '<em><b>Checkpoint Ops Limit</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CHECKPOINT_OPS_LIMIT = 33;

	/**
	 * The feature id for the '<em><b>Child Cluster Member</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CHILD_CLUSTER_MEMBER = 34;

	/**
	 * The feature id for the '<em><b>Cipher</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CIPHER = 35;

	/**
	 * The feature id for the '<em><b>Ciphers</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CIPHERS = 36;

	/**
	 * The feature id for the '<em><b>Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CLASS = 37;

	/**
	 * The feature id for the '<em><b>Cluster</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CLUSTER = 38;

	/**
	 * The feature id for the '<em><b>Cluster Member</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CLUSTER_MEMBER = 39;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__COMMENT = 40;

	/**
	 * The feature id for the '<em><b>Concurrent Rtc</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CONCURRENT_RTC = 41;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CONDITION = 42;

	/**
	 * The feature id for the '<em><b>Connection Linger</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CONNECTION_LINGER = 43;

	/**
	 * The feature id for the '<em><b>Connection Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CONNECTION_TIMEOUT = 44;

	/**
	 * The feature id for the '<em><b>Constant</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CONSTANT = 45;

	/**
	 * The feature id for the '<em><b>Controller</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__CONTROLLER = 46;

	/**
	 * The feature id for the '<em><b>Daemon</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__DAEMON = 47;

	/**
	 * The feature id for the '<em><b>Dashboard Agent Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__DASHBOARD_AGENT_CLASS = 48;

	/**
	 * The feature id for the '<em><b>Data Store Path</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__DATA_STORE_PATH = 49;

	/**
	 * The feature id for the '<em><b>Date</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__DATE = 50;

	/**
	 * The feature id for the '<em><b>Db Concepts</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__DB_CONCEPTS = 51;

	/**
	 * The feature id for the '<em><b>Db Dir</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__DB_DIR = 52;

	/**
	 * The feature id for the '<em><b>Db Uris</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__DB_URIS = 53;

	/**
	 * The feature id for the '<em><b>Default Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__DEFAULT_MODE = 54;

	/**
	 * The feature id for the '<em><b>Delete Retracted</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__DELETE_RETRACTED = 55;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__DESTINATION = 56;

	/**
	 * The feature id for the '<em><b>Destination Groups</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__DESTINATION_GROUPS = 57;

	/**
	 * The feature id for the '<em><b>Destinations</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__DESTINATIONS = 58;

	/**
	 * The feature id for the '<em><b>Dir</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__DIR = 59;

	/**
	 * The feature id for the '<em><b>Discovery Url</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__DISCOVERY_URL = 60;

	/**
	 * The feature id for the '<em><b>Document Page</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__DOCUMENT_PAGE = 61;

	/**
	 * The feature id for the '<em><b>Document Root</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__DOCUMENT_ROOT = 62;

	/**
	 * The feature id for the '<em><b>Domain Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__DOMAIN_NAME = 63;

	/**
	 * The feature id for the '<em><b>Domain Object</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__DOMAIN_OBJECT = 64;

	/**
	 * The feature id for the '<em><b>Domain Objects</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__DOMAIN_OBJECTS = 65;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__ENABLED = 66;

	/**
	 * The feature id for the '<em><b>Enable Tracking</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__ENABLE_TRACKING = 67;

	/**
	 * The feature id for the '<em><b>Encoding</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__ENCODING = 68;

	/**
	 * The feature id for the '<em><b>Encryption</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__ENCRYPTION = 69;

	/**
	 * The feature id for the '<em><b>Enforce Pools</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__ENFORCE_POOLS = 70;

	/**
	 * The feature id for the '<em><b>Entity Cache Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__ENTITY_CACHE_SIZE = 71;

	/**
	 * The feature id for the '<em><b>Eviction</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__EVICTION = 72;

	/**
	 * The feature id for the '<em><b>Evict On Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__EVICT_ON_UPDATE = 73;

	/**
	 * The feature id for the '<em><b>Explicit Tuple</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__EXPLICIT_TUPLE = 74;

	/**
	 * The feature id for the '<em><b>Files</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__FILES = 75;

	/**
	 * The feature id for the '<em><b>Function Groups</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__FUNCTION_GROUPS = 76;

	/**
	 * The feature id for the '<em><b>Functions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__FUNCTIONS = 77;

	/**
	 * The feature id for the '<em><b>Get Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__GET_PROPERTY = 78;

	/**
	 * The feature id for the '<em><b>Hot Deploy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__HOT_DEPLOY = 79;

	/**
	 * The feature id for the '<em><b>Http</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__HTTP = 80;

	/**
	 * The feature id for the '<em><b>Identity Password</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__IDENTITY_PASSWORD = 81;

	/**
	 * The feature id for the '<em><b>Inactivity Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__INACTIVITY_TIMEOUT = 82;

	/**
	 * The feature id for the '<em><b>Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__INDEX = 83;

	/**
	 * The feature id for the '<em><b>Indexes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__INDEXES = 84;

	/**
	 * The feature id for the '<em><b>Inference Agent Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__INFERENCE_AGENT_CLASS = 85;

	/**
	 * The feature id for the '<em><b>Inference Engine</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__INFERENCE_ENGINE = 86;

	/**
	 * The feature id for the '<em><b>Initial Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__INITIAL_SIZE = 87;

	/**
	 * The feature id for the '<em><b>Job Manager</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__JOB_MANAGER = 88;

	/**
	 * The feature id for the '<em><b>Job Pool Queue Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__JOB_POOL_QUEUE_SIZE = 89;

	/**
	 * The feature id for the '<em><b>Job Pool Thread Count</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__JOB_POOL_THREAD_COUNT = 90;

	/**
	 * The feature id for the '<em><b>Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__KEY = 91;

	/**
	 * The feature id for the '<em><b>Key Manager Algorithm</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__KEY_MANAGER_ALGORITHM = 92;

	/**
	 * The feature id for the '<em><b>Line Layout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__LINE_LAYOUT = 93;

	/**
	 * The feature id for the '<em><b>Listen Url</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__LISTEN_URL = 94;

	/**
	 * The feature id for the '<em><b>Load</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__LOAD = 95;

	/**
	 * The feature id for the '<em><b>Load Balancer Configs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__LOAD_BALANCER_CONFIGS = 96;

	/**
	 * The feature id for the '<em><b>Local Cache</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__LOCAL_CACHE = 97;

	/**
	 * The feature id for the '<em><b>Lock Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__LOCK_TIMEOUT = 98;

	/**
	 * The feature id for the '<em><b>Log Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__LOG_CONFIG = 99;

	/**
	 * The feature id for the '<em><b>Log Configs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__LOG_CONFIGS = 100;

	/**
	 * The feature id for the '<em><b>Max Active</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__MAX_ACTIVE = 101;

	/**
	 * The feature id for the '<em><b>Max Number</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__MAX_NUMBER = 102;

	/**
	 * The feature id for the '<em><b>Max Processors</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__MAX_PROCESSORS = 103;

	/**
	 * The feature id for the '<em><b>Max Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__MAX_SIZE = 104;

	/**
	 * The feature id for the '<em><b>Max Time</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__MAX_TIME = 105;

	/**
	 * The feature id for the '<em><b>Memory Manager</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__MEMORY_MANAGER = 106;

	/**
	 * The feature id for the '<em><b>Message Encoding</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__MESSAGE_ENCODING = 107;

	/**
	 * The feature id for the '<em><b>Min Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__MIN_SIZE = 108;

	/**
	 * The feature id for the '<em><b>Mm Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__MM_ACTION = 109;

	/**
	 * The feature id for the '<em><b>Mm Action Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__MM_ACTION_CONFIG = 110;

	/**
	 * The feature id for the '<em><b>Mm Action Config Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__MM_ACTION_CONFIG_SET = 111;

	/**
	 * The feature id for the '<em><b>Mm Agent Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__MM_AGENT_CLASS = 112;

	/**
	 * The feature id for the '<em><b>Mm Alert</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__MM_ALERT = 113;

	/**
	 * The feature id for the '<em><b>Mm Execute Command</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__MM_EXECUTE_COMMAND = 114;

	/**
	 * The feature id for the '<em><b>Mm Health Level</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__MM_HEALTH_LEVEL = 115;

	/**
	 * The feature id for the '<em><b>Mm Send Email</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__MM_SEND_EMAIL = 116;

	/**
	 * The feature id for the '<em><b>Mm Trigger Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__MM_TRIGGER_CONDITION = 117;

	/**
	 * The feature id for the '<em><b>Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__MODE = 118;

	/**
	 * The feature id for the '<em><b>Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__NAME = 119;

	/**
	 * The feature id for the '<em><b>Notification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__NOTIFICATION = 120;

	/**
	 * The feature id for the '<em><b>Object Management</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__OBJECT_MANAGEMENT = 121;

	/**
	 * The feature id for the '<em><b>Object Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__OBJECT_TABLE = 122;

	/**
	 * The feature id for the '<em><b>Pair Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PAIR_CONFIG = 123;

	/**
	 * The feature id for the '<em><b>Pair Configs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PAIR_CONFIGS = 124;

	/**
	 * The feature id for the '<em><b>Parallel Recovery</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PARALLEL_RECOVERY = 125;

	/**
	 * The feature id for the '<em><b>Persistence Option</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PERSISTENCE_OPTION = 126;

	/**
	 * The feature id for the '<em><b>Persistence Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PERSISTENCE_POLICY = 127;

	/**
	 * The feature id for the '<em><b>Policy File</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__POLICY_FILE = 128;

	/**
	 * The feature id for the '<em><b>Port</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PORT = 129;

	/**
	 * The feature id for the '<em><b>Pre Load Caches</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PRE_LOAD_CACHES = 130;

	/**
	 * The feature id for the '<em><b>Pre Load Enabled</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PRE_LOAD_ENABLED = 131;

	/**
	 * The feature id for the '<em><b>Pre Load Fetch Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PRE_LOAD_FETCH_SIZE = 132;

	/**
	 * The feature id for the '<em><b>Pre Load Handles</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PRE_LOAD_HANDLES = 133;

	/**
	 * The feature id for the '<em><b>Pre Processor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PRE_PROCESSOR = 134;

	/**
	 * The feature id for the '<em><b>Primary Connection</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PRIMARY_CONNECTION = 135;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PRIORITY = 136;

	/**
	 * The feature id for the '<em><b>Process</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PROCESS = 137;

	/**
	 * The feature id for the '<em><b>Process Agent Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PROCESS_AGENT_CLASS = 138;

	/**
	 * The feature id for the '<em><b>Process Engine</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PROCESS_ENGINE = 139;

	/**
	 * The feature id for the '<em><b>Processes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PROCESSES = 140;

	/**
	 * The feature id for the '<em><b>Process Groups</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PROCESS_GROUPS = 141;

	/**
	 * The feature id for the '<em><b>Processing Unit</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PROCESSING_UNIT = 142;

	/**
	 * The feature id for the '<em><b>Processing Units</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PROCESSING_UNITS = 143;

	/**
	 * The feature id for the '<em><b>Projection</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PROJECTION = 144;

	/**
	 * The feature id for the '<em><b>Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PROPERTY = 145;

	/**
	 * The feature id for the '<em><b>Property Cache Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PROPERTY_CACHE_SIZE = 146;

	/**
	 * The feature id for the '<em><b>Property Check Interval</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PROPERTY_CHECK_INTERVAL = 147;

	/**
	 * The feature id for the '<em><b>Property Group</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PROPERTY_GROUP = 148;

	/**
	 * The feature id for the '<em><b>Protocol</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PROTOCOL = 149;

	/**
	 * The feature id for the '<em><b>Protocols</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PROTOCOLS = 150;

	/**
	 * The feature id for the '<em><b>Protocol Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PROTOCOL_TIMEOUT = 151;

	/**
	 * The feature id for the '<em><b>Provider</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PROVIDER = 152;

	/**
	 * The feature id for the '<em><b>Query Agent Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__QUERY_AGENT_CLASS = 153;

	/**
	 * The feature id for the '<em><b>Queue Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__QUEUE_SIZE = 154;

	/**
	 * The feature id for the '<em><b>Read Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__READ_TIMEOUT = 155;

	/**
	 * The feature id for the '<em><b>Receiver</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__RECEIVER = 156;

	/**
	 * The feature id for the '<em><b>Remote Listen Url</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__REMOTE_LISTEN_URL = 157;

	/**
	 * The feature id for the '<em><b>Requester</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__REQUESTER = 158;

	/**
	 * The feature id for the '<em><b>Retry Count</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__RETRY_COUNT = 159;

	/**
	 * The feature id for the '<em><b>Reverse References</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__REVERSE_REFERENCES = 160;

	/**
	 * The feature id for the '<em><b>Revision</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__REVISION = 161;

	/**
	 * The feature id for the '<em><b>Role</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__ROLE = 162;

	/**
	 * The feature id for the '<em><b>Roles</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__ROLES = 163;

	/**
	 * The feature id for the '<em><b>Router</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__ROUTER = 164;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__RULE = 165;

	/**
	 * The feature id for the '<em><b>Rule Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__RULE_CONFIG = 166;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__RULES = 167;

	/**
	 * The feature id for the '<em><b>Rulesets</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__RULESETS = 168;

	/**
	 * The feature id for the '<em><b>Secondary Connection</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__SECONDARY_CONNECTION = 169;

	/**
	 * The feature id for the '<em><b>Security Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__SECURITY_CONFIG = 170;

	/**
	 * The feature id for the '<em><b>Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__SERVICE = 171;

	/**
	 * The feature id for the '<em><b>Set Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__SET_PROPERTY = 172;

	/**
	 * The feature id for the '<em><b>Shared All</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__SHARED_ALL = 173;

	/**
	 * The feature id for the '<em><b>Shared Queue</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__SHARED_QUEUE = 174;

	/**
	 * The feature id for the '<em><b>Shoutdown Wait</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__SHOUTDOWN_WAIT = 175;

	/**
	 * The feature id for the '<em><b>Shutdown</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__SHUTDOWN = 176;

	/**
	 * The feature id for the '<em><b>Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__SIZE = 177;

	/**
	 * The feature id for the '<em><b>Skip Recovery</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__SKIP_RECOVERY = 178;

	/**
	 * The feature id for the '<em><b>Socket Output Buffer Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__SOCKET_OUTPUT_BUFFER_SIZE = 179;

	/**
	 * The feature id for the '<em><b>Ssl</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__SSL = 180;

	/**
	 * The feature id for the '<em><b>Stale Connection Check</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__STALE_CONNECTION_CHECK = 181;

	/**
	 * The feature id for the '<em><b>Startup</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__STARTUP = 182;

	/**
	 * The feature id for the '<em><b>Strategy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__STRATEGY = 183;

	/**
	 * The feature id for the '<em><b>Subject</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__SUBJECT = 184;

	/**
	 * The feature id for the '<em><b>Subscribe</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__SUBSCRIBE = 185;

	/**
	 * The feature id for the '<em><b>Sys Err Redirect</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__SYS_ERR_REDIRECT = 186;

	/**
	 * The feature id for the '<em><b>Sys Out Redirect</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__SYS_OUT_REDIRECT = 187;

	/**
	 * The feature id for the '<em><b>Table Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__TABLE_NAME = 188;

	/**
	 * The feature id for the '<em><b>Tcp No Delay</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__TCP_NO_DELAY = 189;

	/**
	 * The feature id for the '<em><b>Terminal</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__TERMINAL = 190;

	/**
	 * The feature id for the '<em><b>Thread Affinity Rule Function</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__THREAD_AFFINITY_RULE_FUNCTION = 191;

	/**
	 * The feature id for the '<em><b>Thread Count</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__THREAD_COUNT = 192;

	/**
	 * The feature id for the '<em><b>Threading Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__THREADING_MODEL = 193;

	/**
	 * The feature id for the '<em><b>Token File</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__TOKEN_FILE = 194;

	/**
	 * The feature id for the '<em><b>Trust Manager Algorithm</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__TRUST_MANAGER_ALGORITHM = 195;

	/**
	 * The feature id for the '<em><b>Ttl</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__TTL = 196;

	/**
	 * The feature id for the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__TYPE = 197;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__URI = 198;

	/**
	 * The feature id for the '<em><b>User Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__USER_NAME = 199;

	/**
	 * The feature id for the '<em><b>User Password</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__USER_PASSWORD = 200;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__VERSION = 201;

	/**
	 * The feature id for the '<em><b>Wait Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__WAIT_TIMEOUT = 202;

	/**
	 * The feature id for the '<em><b>Workers</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__WORKERS = 203;

	/**
	 * The feature id for the '<em><b>Workerthreads Count</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__WORKERTHREADS_COUNT = 204;

	/**
	 * The feature id for the '<em><b>Write Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__WRITE_TIMEOUT = 205;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__ENTITY = 206;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__FILTER = 207;

	/**
	 * The feature id for the '<em><b>Entity Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__ENTITY_SET = 208;

	/**
	 * The feature id for the '<em><b>Enable Table Trimming</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__ENABLE_TABLE_TRIMMING = 209;

	/**
	 * The feature id for the '<em><b>Trimming Field</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__TRIMMING_FIELD = 210;

	/**
	 * The feature id for the '<em><b>Trimming Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__TRIMMING_RULE = 211;

	/**
	 * The feature id for the '<em><b>Generate LV Files</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__GENERATE_LV_FILES = 212;

	/**
	 * The feature id for the '<em><b>Output Path</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__OUTPUT_PATH = 213;

	/**
	 * The feature id for the '<em><b>Ldm Connection</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__LDM_CONNECTION = 214;

	/**
	 * The feature id for the '<em><b>Ldm Url</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__LDM_URL = 215;

	/**
	 * The feature id for the '<em><b>Publisher</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PUBLISHER = 216;

	/**
	 * The feature id for the '<em><b>Publisher Queue Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PUBLISHER_QUEUE_SIZE = 217;

	/**
	 * The feature id for the '<em><b>Publisher Thread Count</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__PUBLISHER_THREAD_COUNT = 218;

	/**
	 * The feature id for the '<em><b>Liveview Agent Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__LIVEVIEW_AGENT_CLASS = 219;

	/**
	 * The feature id for the '<em><b>Composite Indexes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__COMPOSITE_INDEXES = 220;

	/**
	 * The feature id for the '<em><b>Composite Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT__COMPOSITE_INDEX = 221;

	/**
	 * The number of structural features of the '<em>Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CDD_ROOT_FEATURE_COUNT = 222;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.ChildClusterMemberConfigImpl <em>Child Cluster Member Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.ChildClusterMemberConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getChildClusterMemberConfig()
	 * @generated
	 */
	int CHILD_CLUSTER_MEMBER_CONFIG = 16;

	/**
	 * The feature id for the '<em><b>Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_CLUSTER_MEMBER_CONFIG__PROPERTY = 0;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_CLUSTER_MEMBER_CONFIG__PATH = 1;

	/**
	 * The feature id for the '<em><b>Tolerance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_CLUSTER_MEMBER_CONFIG__TOLERANCE = 2;

	/**
	 * The number of structural features of the '<em>Child Cluster Member Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_CLUSTER_MEMBER_CONFIG_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.CiphersConfigImpl <em>Ciphers Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.CiphersConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getCiphersConfig()
	 * @generated
	 */
	int CIPHERS_CONFIG = 17;

	/**
	 * The feature id for the '<em><b>Cipher</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CIPHERS_CONFIG__CIPHER = 0;

	/**
	 * The number of structural features of the '<em>Ciphers Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CIPHERS_CONFIG_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.ClusterConfigImpl <em>Cluster Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.ClusterConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getClusterConfig()
	 * @generated
	 */
	int CLUSTER_CONFIG = 18;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTER_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Agent Classes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTER_CONFIG__AGENT_CLASSES = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Destination Groups</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTER_CONFIG__DESTINATION_GROUPS = ARTIFACT_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Function Groups</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTER_CONFIG__FUNCTION_GROUPS = ARTIFACT_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Load Balancer Configs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTER_CONFIG__LOAD_BALANCER_CONFIGS = ARTIFACT_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Log Configs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTER_CONFIG__LOG_CONFIGS = ARTIFACT_CONFIG_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Message Encoding</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTER_CONFIG__MESSAGE_ENCODING = ARTIFACT_CONFIG_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTER_CONFIG__NAME = ARTIFACT_CONFIG_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Object Management</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTER_CONFIG__OBJECT_MANAGEMENT = ARTIFACT_CONFIG_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Process Groups</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTER_CONFIG__PROCESS_GROUPS = ARTIFACT_CONFIG_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Processing Units</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTER_CONFIG__PROCESSING_UNITS = ARTIFACT_CONFIG_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Property Group</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTER_CONFIG__PROPERTY_GROUP = ARTIFACT_CONFIG_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Revision</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTER_CONFIG__REVISION = ARTIFACT_CONFIG_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Rulesets</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTER_CONFIG__RULESETS = ARTIFACT_CONFIG_FEATURE_COUNT + 12;

	/**
	 * The number of structural features of the '<em>Cluster Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTER_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 13;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.ClusterMemberConfigImpl <em>Cluster Member Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.ClusterMemberConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getClusterMemberConfig()
	 * @generated
	 */
	int CLUSTER_MEMBER_CONFIG = 19;

	/**
	 * The feature id for the '<em><b>Set Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTER_MEMBER_CONFIG__SET_PROPERTY = 0;

	/**
	 * The feature id for the '<em><b>Member Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTER_MEMBER_CONFIG__MEMBER_NAME = 1;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTER_MEMBER_CONFIG__PATH = 2;

	/**
	 * The number of structural features of the '<em>Cluster Member Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTER_MEMBER_CONFIG_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.ConditionConfigImpl <em>Condition Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.ConditionConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getConditionConfig()
	 * @generated
	 */
	int CONDITION_CONFIG = 20;

	/**
	 * The feature id for the '<em><b>Get Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION_CONFIG__GET_PROPERTY = 0;

	/**
	 * The number of structural features of the '<em>Condition Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION_CONFIG_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.ConnectionConfigImpl <em>Connection Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.ConnectionConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getConnectionConfig()
	 * @generated
	 */
	int CONNECTION_CONFIG = 21;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Initial Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_CONFIG__INITIAL_SIZE = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Min Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_CONFIG__MIN_SIZE = ARTIFACT_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Max Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_CONFIG__MAX_SIZE = ARTIFACT_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_CONFIG__URI = ARTIFACT_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Connection Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.DashboardAgentClassConfigImpl <em>Dashboard Agent Class Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.DashboardAgentClassConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getDashboardAgentClassConfig()
	 * @generated
	 */
	int DASHBOARD_AGENT_CLASS_CONFIG = 22;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DASHBOARD_AGENT_CLASS_CONFIG__ID = AGENT_CLASS_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DASHBOARD_AGENT_CLASS_CONFIG__RULES = AGENT_CLASS_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Destinations</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DASHBOARD_AGENT_CLASS_CONFIG__DESTINATIONS = AGENT_CLASS_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Startup</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DASHBOARD_AGENT_CLASS_CONFIG__STARTUP = AGENT_CLASS_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Shutdown</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DASHBOARD_AGENT_CLASS_CONFIG__SHUTDOWN = AGENT_CLASS_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Property Group</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DASHBOARD_AGENT_CLASS_CONFIG__PROPERTY_GROUP = AGENT_CLASS_CONFIG_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Dashboard Agent Class Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DASHBOARD_AGENT_CLASS_CONFIG_FEATURE_COUNT = AGENT_CLASS_CONFIG_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.DbConceptsConfigImpl <em>Db Concepts Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.DbConceptsConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getDbConceptsConfig()
	 * @generated
	 */
	int DB_CONCEPTS_CONFIG = 23;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DB_CONCEPTS_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Check Interval</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DB_CONCEPTS_CONFIG__CHECK_INTERVAL = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Db Uris</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DB_CONCEPTS_CONFIG__DB_URIS = ARTIFACT_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Inactivity Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DB_CONCEPTS_CONFIG__INACTIVITY_TIMEOUT = ARTIFACT_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Initial Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DB_CONCEPTS_CONFIG__INITIAL_SIZE = ARTIFACT_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Max Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DB_CONCEPTS_CONFIG__MAX_SIZE = ARTIFACT_CONFIG_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Min Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DB_CONCEPTS_CONFIG__MIN_SIZE = ARTIFACT_CONFIG_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Property Check Interval</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DB_CONCEPTS_CONFIG__PROPERTY_CHECK_INTERVAL = ARTIFACT_CONFIG_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Retry Count</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DB_CONCEPTS_CONFIG__RETRY_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Wait Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DB_CONCEPTS_CONFIG__WAIT_TIMEOUT = ARTIFACT_CONFIG_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>Db Concepts Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DB_CONCEPTS_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 9;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.DestinationConfigImpl <em>Destination Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.DestinationConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getDestinationConfig()
	 * @generated
	 */
	int DESTINATION_CONFIG = 24;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONFIG__URI = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Pre Processor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONFIG__PRE_PROCESSOR = ARTIFACT_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Threading Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONFIG__THREADING_MODEL = ARTIFACT_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Thread Count</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONFIG__THREAD_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Queue Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONFIG__QUEUE_SIZE = ARTIFACT_CONFIG_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Thread Affinity Rule Function</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONFIG__THREAD_AFFINITY_RULE_FUNCTION = ARTIFACT_CONFIG_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Destination Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.DestinationGroupsConfigImpl <em>Destination Groups Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.DestinationGroupsConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getDestinationGroupsConfig()
	 * @generated
	 */
	int DESTINATION_GROUPS_CONFIG = 25;

	/**
	 * The feature id for the '<em><b>Destinations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_GROUPS_CONFIG__DESTINATIONS = 0;

	/**
	 * The number of structural features of the '<em>Destination Groups Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_GROUPS_CONFIG_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.DestinationsConfigImpl <em>Destinations Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.DestinationsConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getDestinationsConfig()
	 * @generated
	 */
	int DESTINATIONS_CONFIG = 26;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATIONS_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATIONS_CONFIG__GROUP = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Ref</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATIONS_CONFIG__REF = ARTIFACT_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATIONS_CONFIG__DESTINATION = ARTIFACT_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Destinations Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATIONS_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.DomainObjectConfigImpl <em>Domain Object Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.DomainObjectConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getDomainObjectConfig()
	 * @generated
	 */
	int DOMAIN_OBJECT_CONFIG = 27;

	/**
	 * The feature id for the '<em><b>Backing Store</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECT_CONFIG__BACKING_STORE = 0;

	/**
	 * The feature id for the '<em><b>Cache Limited</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECT_CONFIG__CACHE_LIMITED = 1;

	/**
	 * The feature id for the '<em><b>Check For Version</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECT_CONFIG__CHECK_FOR_VERSION = 2;

	/**
	 * The feature id for the '<em><b>Constant</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECT_CONFIG__CONSTANT = 3;

	/**
	 * The feature id for the '<em><b>Enable Tracking</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECT_CONFIG__ENABLE_TRACKING = 4;

	/**
	 * The feature id for the '<em><b>Encryption</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECT_CONFIG__ENCRYPTION = 5;

	/**
	 * The feature id for the '<em><b>Evict On Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECT_CONFIG__EVICT_ON_UPDATE = 6;

	/**
	 * The feature id for the '<em><b>Indexes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECT_CONFIG__INDEXES = 7;

	/**
	 * The feature id for the '<em><b>Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECT_CONFIG__MODE = 8;

	/**
	 * The feature id for the '<em><b>Pre Load Enabled</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECT_CONFIG__PRE_LOAD_ENABLED = 9;

	/**
	 * The feature id for the '<em><b>Pre Load Fetch Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECT_CONFIG__PRE_LOAD_FETCH_SIZE = 10;

	/**
	 * The feature id for the '<em><b>Pre Load Handles</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECT_CONFIG__PRE_LOAD_HANDLES = 11;

	/**
	 * The feature id for the '<em><b>Pre Processor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECT_CONFIG__PRE_PROCESSOR = 12;

	/**
	 * The feature id for the '<em><b>Subscribe</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECT_CONFIG__SUBSCRIBE = 13;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECT_CONFIG__URI = 14;

	/**
	 * The feature id for the '<em><b>Concept TTL</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECT_CONFIG__CONCEPT_TTL = 15;

	/**
	 * The feature id for the '<em><b>Composite Indexes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECT_CONFIG__COMPOSITE_INDEXES = 16;

	/**
	 * The number of structural features of the '<em>Domain Object Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECT_CONFIG_FEATURE_COUNT = 17;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.DomainObjectsConfigImpl <em>Domain Objects Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.DomainObjectsConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getDomainObjectsConfig()
	 * @generated
	 */
	int DOMAIN_OBJECTS_CONFIG = 28;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECTS_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Default Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECTS_CONFIG__DEFAULT_MODE = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Check For Version</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECTS_CONFIG__CHECK_FOR_VERSION = ARTIFACT_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Constant</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECTS_CONFIG__CONSTANT = ARTIFACT_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Enable Tracking</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECTS_CONFIG__ENABLE_TRACKING = ARTIFACT_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Evict On Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECTS_CONFIG__EVICT_ON_UPDATE = ARTIFACT_CONFIG_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Cache Limited</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECTS_CONFIG__CACHE_LIMITED = ARTIFACT_CONFIG_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Subscribe</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECTS_CONFIG__SUBSCRIBE = ARTIFACT_CONFIG_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Pre Load Enabled</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECTS_CONFIG__PRE_LOAD_ENABLED = ARTIFACT_CONFIG_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Pre Load Fetch Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECTS_CONFIG__PRE_LOAD_FETCH_SIZE = ARTIFACT_CONFIG_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Pre Load Handles</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECTS_CONFIG__PRE_LOAD_HANDLES = ARTIFACT_CONFIG_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Domain Object</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECTS_CONFIG__DOMAIN_OBJECT = ARTIFACT_CONFIG_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Concept TTL</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECTS_CONFIG__CONCEPT_TTL = ARTIFACT_CONFIG_FEATURE_COUNT + 11;

	/**
	 * The number of structural features of the '<em>Domain Objects Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OBJECTS_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 12;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.EvictionConfigImpl <em>Eviction Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.EvictionConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getEvictionConfig()
	 * @generated
	 */
	int EVICTION_CONFIG = 29;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVICTION_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Max Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVICTION_CONFIG__MAX_SIZE = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Max Time</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVICTION_CONFIG__MAX_TIME = ARTIFACT_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Eviction Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVICTION_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.FilesConfigImpl <em>Files Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.FilesConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getFilesConfig()
	 * @generated
	 */
	int FILES_CONFIG = 30;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILES_CONFIG__ENABLED = 0;

	/**
	 * The feature id for the '<em><b>Dir</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILES_CONFIG__DIR = 1;

	/**
	 * The feature id for the '<em><b>Max Number</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILES_CONFIG__MAX_NUMBER = 2;

	/**
	 * The feature id for the '<em><b>Max Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILES_CONFIG__MAX_SIZE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILES_CONFIG__NAME = 4;

	/**
	 * The feature id for the '<em><b>Append</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILES_CONFIG__APPEND = 5;

	/**
	 * The number of structural features of the '<em>Files Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILES_CONFIG_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.FunctionGroupsConfigImpl <em>Function Groups Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.FunctionGroupsConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getFunctionGroupsConfig()
	 * @generated
	 */
	int FUNCTION_GROUPS_CONFIG = 31;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_GROUPS_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Functions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_GROUPS_CONFIG__FUNCTIONS = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Function Groups Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_GROUPS_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.UrisAndRefsConfigImpl <em>Uris And Refs Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.UrisAndRefsConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getUrisAndRefsConfig()
	 * @generated
	 */
	int URIS_AND_REFS_CONFIG = 93;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URIS_AND_REFS_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The number of structural features of the '<em>Uris And Refs Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URIS_AND_REFS_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.FunctionsConfigImpl <em>Functions Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.FunctionsConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getFunctionsConfig()
	 * @generated
	 */
	int FUNCTIONS_CONFIG = 32;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTIONS_CONFIG__ID = URIS_AND_REFS_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTIONS_CONFIG__GROUP = URIS_AND_REFS_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Ref</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTIONS_CONFIG__REF = URIS_AND_REFS_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTIONS_CONFIG__URI = URIS_AND_REFS_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Functions Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTIONS_CONFIG_FEATURE_COUNT = URIS_AND_REFS_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.GetPropertyConfigImpl <em>Get Property Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.GetPropertyConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getGetPropertyConfig()
	 * @generated
	 */
	int GET_PROPERTY_CONFIG = 33;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GET_PROPERTY_CONFIG__NAME = 0;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GET_PROPERTY_CONFIG__PATH = 1;

	/**
	 * The feature id for the '<em><b>Reference</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GET_PROPERTY_CONFIG__REFERENCE = 2;

	/**
	 * The feature id for the '<em><b>Threshold</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GET_PROPERTY_CONFIG__THRESHOLD = 3;

	/**
	 * The number of structural features of the '<em>Get Property Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GET_PROPERTY_CONFIG_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.HttpConfigImpl <em>Http Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.HttpConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getHttpConfig()
	 * @generated
	 */
	int HTTP_CONFIG = 34;

	/**
	 * The feature id for the '<em><b>Accept Count</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_CONFIG__ACCEPT_COUNT = 0;

	/**
	 * The feature id for the '<em><b>Connection Linger</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_CONFIG__CONNECTION_LINGER = 1;

	/**
	 * The feature id for the '<em><b>Connection Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_CONFIG__CONNECTION_TIMEOUT = 2;

	/**
	 * The feature id for the '<em><b>Document Page</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_CONFIG__DOCUMENT_PAGE = 3;

	/**
	 * The feature id for the '<em><b>Document Root</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_CONFIG__DOCUMENT_ROOT = 4;

	/**
	 * The feature id for the '<em><b>Max Processors</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_CONFIG__MAX_PROCESSORS = 5;

	/**
	 * The feature id for the '<em><b>Socket Output Buffer Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_CONFIG__SOCKET_OUTPUT_BUFFER_SIZE = 6;

	/**
	 * The feature id for the '<em><b>Ssl</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_CONFIG__SSL = 7;

	/**
	 * The feature id for the '<em><b>Stale Connection Check</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_CONFIG__STALE_CONNECTION_CHECK = 8;

	/**
	 * The feature id for the '<em><b>Tcp No Delay</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_CONFIG__TCP_NO_DELAY = 9;

	/**
	 * The number of structural features of the '<em>Http Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_CONFIG_FEATURE_COUNT = 10;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.IndexConfigImpl <em>Index Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.IndexConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getIndexConfig()
	 * @generated
	 */
	int INDEX_CONFIG = 35;

	/**
	 * The feature id for the '<em><b>Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEX_CONFIG__PROPERTY = 0;

	/**
	 * The number of structural features of the '<em>Index Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEX_CONFIG_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.IndexesConfigImpl <em>Indexes Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.IndexesConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getIndexesConfig()
	 * @generated
	 */
	int INDEXES_CONFIG = 36;

	/**
	 * The feature id for the '<em><b>Index</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEXES_CONFIG__INDEX = 0;

	/**
	 * The number of structural features of the '<em>Indexes Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEXES_CONFIG_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.InferenceAgentClassConfigImpl <em>Inference Agent Class Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.InferenceAgentClassConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getInferenceAgentClassConfig()
	 * @generated
	 */
	int INFERENCE_AGENT_CLASS_CONFIG = 37;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFERENCE_AGENT_CLASS_CONFIG__ID = AGENT_CLASS_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Businessworks</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFERENCE_AGENT_CLASS_CONFIG__BUSINESSWORKS = AGENT_CLASS_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Check For Duplicates</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFERENCE_AGENT_CLASS_CONFIG__CHECK_FOR_DUPLICATES = AGENT_CLASS_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Concurrent Rtc</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFERENCE_AGENT_CLASS_CONFIG__CONCURRENT_RTC = AGENT_CLASS_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Destinations</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFERENCE_AGENT_CLASS_CONFIG__DESTINATIONS = AGENT_CLASS_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Load</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFERENCE_AGENT_CLASS_CONFIG__LOAD = AGENT_CLASS_CONFIG_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Local Cache</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFERENCE_AGENT_CLASS_CONFIG__LOCAL_CACHE = AGENT_CLASS_CONFIG_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Property Group</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFERENCE_AGENT_CLASS_CONFIG__PROPERTY_GROUP = AGENT_CLASS_CONFIG_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFERENCE_AGENT_CLASS_CONFIG__RULES = AGENT_CLASS_CONFIG_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Shared Queue</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFERENCE_AGENT_CLASS_CONFIG__SHARED_QUEUE = AGENT_CLASS_CONFIG_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Shutdown</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFERENCE_AGENT_CLASS_CONFIG__SHUTDOWN = AGENT_CLASS_CONFIG_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Startup</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFERENCE_AGENT_CLASS_CONFIG__STARTUP = AGENT_CLASS_CONFIG_FEATURE_COUNT + 10;

	/**
	 * The number of structural features of the '<em>Inference Agent Class Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFERENCE_AGENT_CLASS_CONFIG_FEATURE_COUNT = AGENT_CLASS_CONFIG_FEATURE_COUNT + 11;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.InferenceEngineConfigImpl <em>Inference Engine Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.InferenceEngineConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getInferenceEngineConfig()
	 * @generated
	 */
	int INFERENCE_ENGINE_CONFIG = 38;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFERENCE_ENGINE_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Check For Duplicates</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFERENCE_ENGINE_CONFIG__CHECK_FOR_DUPLICATES = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Concurrent Rtc</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFERENCE_ENGINE_CONFIG__CONCURRENT_RTC = ARTIFACT_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Local Cache</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFERENCE_ENGINE_CONFIG__LOCAL_CACHE = ARTIFACT_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Property Group</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFERENCE_ENGINE_CONFIG__PROPERTY_GROUP = ARTIFACT_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Inference Engine Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFERENCE_ENGINE_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.JobManagerConfigImpl <em>Job Manager Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.JobManagerConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getJobManagerConfig()
	 * @generated
	 */
	int JOB_MANAGER_CONFIG = 39;

	/**
	 * The feature id for the '<em><b>Job Pool Queue Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JOB_MANAGER_CONFIG__JOB_POOL_QUEUE_SIZE = 0;

	/**
	 * The feature id for the '<em><b>Job Pool Thread Count</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JOB_MANAGER_CONFIG__JOB_POOL_THREAD_COUNT = 1;

	/**
	 * The number of structural features of the '<em>Job Manager Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JOB_MANAGER_CONFIG_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.LineLayoutConfigImpl <em>Line Layout Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.LineLayoutConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getLineLayoutConfig()
	 * @generated
	 */
	int LINE_LAYOUT_CONFIG = 40;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_LAYOUT_CONFIG__ENABLED = 0;

	/**
	 * The feature id for the '<em><b>Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_LAYOUT_CONFIG__CLASS = 1;

	/**
	 * The feature id for the '<em><b>Arg</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_LAYOUT_CONFIG__ARG = 2;

	/**
	 * The number of structural features of the '<em>Line Layout Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_LAYOUT_CONFIG_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.LoadBalancerAdhocConfigConfigImpl <em>Load Balancer Adhoc Config Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.LoadBalancerAdhocConfigConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getLoadBalancerAdhocConfigConfig()
	 * @generated
	 */
	int LOAD_BALANCER_ADHOC_CONFIG_CONFIG = 41;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOAD_BALANCER_ADHOC_CONFIG_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOAD_BALANCER_ADHOC_CONFIG_CONFIG__NAME = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Channel</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOAD_BALANCER_ADHOC_CONFIG_CONFIG__CHANNEL = ARTIFACT_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Property Group</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOAD_BALANCER_ADHOC_CONFIG_CONFIG__PROPERTY_GROUP = ARTIFACT_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Load Balancer Adhoc Config Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOAD_BALANCER_ADHOC_CONFIG_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.LoadBalancerAdhocConfigsConfigImpl <em>Load Balancer Adhoc Configs Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.LoadBalancerAdhocConfigsConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getLoadBalancerAdhocConfigsConfig()
	 * @generated
	 */
	int LOAD_BALANCER_ADHOC_CONFIGS_CONFIG = 42;

	/**
	 * The feature id for the '<em><b>Load Balancer Adhoc Configs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOAD_BALANCER_ADHOC_CONFIGS_CONFIG__LOAD_BALANCER_ADHOC_CONFIGS = 0;

	/**
	 * The number of structural features of the '<em>Load Balancer Adhoc Configs Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOAD_BALANCER_ADHOC_CONFIGS_CONFIG_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.LoadBalancerConfigsConfigImpl <em>Load Balancer Configs Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.LoadBalancerConfigsConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getLoadBalancerConfigsConfig()
	 * @generated
	 */
	int LOAD_BALANCER_CONFIGS_CONFIG = 43;

	/**
	 * The feature id for the '<em><b>Load Balancer Pair Configs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOAD_BALANCER_CONFIGS_CONFIG__LOAD_BALANCER_PAIR_CONFIGS = 0;

	/**
	 * The feature id for the '<em><b>Load Balancer Adhoc Configs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOAD_BALANCER_CONFIGS_CONFIG__LOAD_BALANCER_ADHOC_CONFIGS = 1;

	/**
	 * The number of structural features of the '<em>Load Balancer Configs Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOAD_BALANCER_CONFIGS_CONFIG_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.LoadBalancerPairConfigConfigImpl <em>Load Balancer Pair Config Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.LoadBalancerPairConfigConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getLoadBalancerPairConfigConfig()
	 * @generated
	 */
	int LOAD_BALANCER_PAIR_CONFIG_CONFIG = 44;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOAD_BALANCER_PAIR_CONFIG_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOAD_BALANCER_PAIR_CONFIG_CONFIG__NAME = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Channel</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOAD_BALANCER_PAIR_CONFIG_CONFIG__CHANNEL = ARTIFACT_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOAD_BALANCER_PAIR_CONFIG_CONFIG__KEY = ARTIFACT_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Router</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOAD_BALANCER_PAIR_CONFIG_CONFIG__ROUTER = ARTIFACT_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Receiver</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOAD_BALANCER_PAIR_CONFIG_CONFIG__RECEIVER = ARTIFACT_CONFIG_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Property Group</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOAD_BALANCER_PAIR_CONFIG_CONFIG__PROPERTY_GROUP = ARTIFACT_CONFIG_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Load Balancer Pair Config Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOAD_BALANCER_PAIR_CONFIG_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.LoadBalancerPairConfigsConfigImpl <em>Load Balancer Pair Configs Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.LoadBalancerPairConfigsConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getLoadBalancerPairConfigsConfig()
	 * @generated
	 */
	int LOAD_BALANCER_PAIR_CONFIGS_CONFIG = 45;

	/**
	 * The feature id for the '<em><b>Load Balancer Pair Configs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOAD_BALANCER_PAIR_CONFIGS_CONFIG__LOAD_BALANCER_PAIR_CONFIGS = 0;

	/**
	 * The number of structural features of the '<em>Load Balancer Pair Configs Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOAD_BALANCER_PAIR_CONFIGS_CONFIG_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.LoadConfigImpl <em>Load Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.LoadConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getLoadConfig()
	 * @generated
	 */
	int LOAD_CONFIG = 46;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOAD_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Max Active</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOAD_CONFIG__MAX_ACTIVE = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Load Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOAD_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.LocalCacheConfigImpl <em>Local Cache Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.LocalCacheConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getLocalCacheConfig()
	 * @generated
	 */
	int LOCAL_CACHE_CONFIG = 47;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_CACHE_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Eviction</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_CACHE_CONFIG__EVICTION = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Local Cache Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_CACHE_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.LogConfigConfigImpl <em>Log Config Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.LogConfigConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getLogConfigConfig()
	 * @generated
	 */
	int LOG_CONFIG_CONFIG = 48;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOG_CONFIG_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOG_CONFIG_CONFIG__ENABLED = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Roles</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOG_CONFIG_CONFIG__ROLES = ARTIFACT_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Line Layout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOG_CONFIG_CONFIG__LINE_LAYOUT = ARTIFACT_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Terminal</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOG_CONFIG_CONFIG__TERMINAL = ARTIFACT_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Files</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOG_CONFIG_CONFIG__FILES = ARTIFACT_CONFIG_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Log Config Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOG_CONFIG_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.LogConfigsConfigImpl <em>Log Configs Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.LogConfigsConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getLogConfigsConfig()
	 * @generated
	 */
	int LOG_CONFIGS_CONFIG = 49;

	/**
	 * The feature id for the '<em><b>Log Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOG_CONFIGS_CONFIG__LOG_CONFIG = 0;

	/**
	 * The number of structural features of the '<em>Log Configs Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOG_CONFIGS_CONFIG_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.MemoryManagerConfigImpl <em>Memory Manager Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.MemoryManagerConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getMemoryManagerConfig()
	 * @generated
	 */
	int MEMORY_MANAGER_CONFIG = 50;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMORY_MANAGER_CONFIG__ID = OBJECT_MANAGER_CONFIG__ID;

	/**
	 * The number of structural features of the '<em>Memory Manager Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMORY_MANAGER_CONFIG_FEATURE_COUNT = OBJECT_MANAGER_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.MmActionConfigImpl <em>Mm Action Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.MmActionConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getMmActionConfig()
	 * @generated
	 */
	int MM_ACTION_CONFIG = 51;

	/**
	 * The feature id for the '<em><b>Mm Execute Command</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_ACTION_CONFIG__MM_EXECUTE_COMMAND = 0;

	/**
	 * The feature id for the '<em><b>Mm Send Email</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_ACTION_CONFIG__MM_SEND_EMAIL = 1;

	/**
	 * The number of structural features of the '<em>Mm Action Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_ACTION_CONFIG_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.MmActionConfigConfigImpl <em>Mm Action Config Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.MmActionConfigConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getMmActionConfigConfig()
	 * @generated
	 */
	int MM_ACTION_CONFIG_CONFIG = 52;

	/**
	 * The feature id for the '<em><b>Mm Trigger Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_ACTION_CONFIG_CONFIG__MM_TRIGGER_CONDITION = 0;

	/**
	 * The feature id for the '<em><b>Mm Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_ACTION_CONFIG_CONFIG__MM_ACTION = 1;

	/**
	 * The feature id for the '<em><b>Action Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_ACTION_CONFIG_CONFIG__ACTION_NAME = 2;

	/**
	 * The number of structural features of the '<em>Mm Action Config Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_ACTION_CONFIG_CONFIG_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.MmActionConfigSetConfigImpl <em>Mm Action Config Set Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.MmActionConfigSetConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getMmActionConfigSetConfig()
	 * @generated
	 */
	int MM_ACTION_CONFIG_SET_CONFIG = 53;

	/**
	 * The feature id for the '<em><b>Mm Action Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_ACTION_CONFIG_SET_CONFIG__MM_ACTION_CONFIG = 0;

	/**
	 * The number of structural features of the '<em>Mm Action Config Set Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_ACTION_CONFIG_SET_CONFIG_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.MmAgentClassConfigImpl <em>Mm Agent Class Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.MmAgentClassConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getMmAgentClassConfig()
	 * @generated
	 */
	int MM_AGENT_CLASS_CONFIG = 54;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_AGENT_CLASS_CONFIG__ID = AGENT_CLASS_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Mm Inference Agent Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_AGENT_CLASS_CONFIG__MM_INFERENCE_AGENT_CLASS = AGENT_CLASS_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Mm Query Agent Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_AGENT_CLASS_CONFIG__MM_QUERY_AGENT_CLASS = AGENT_CLASS_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Alert Config Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_AGENT_CLASS_CONFIG__ALERT_CONFIG_SET = AGENT_CLASS_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Rule Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_AGENT_CLASS_CONFIG__RULE_CONFIG = AGENT_CLASS_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Mm Action Config Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_AGENT_CLASS_CONFIG__MM_ACTION_CONFIG_SET = AGENT_CLASS_CONFIG_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Property Group</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_AGENT_CLASS_CONFIG__PROPERTY_GROUP = AGENT_CLASS_CONFIG_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Mm Agent Class Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_AGENT_CLASS_CONFIG_FEATURE_COUNT = AGENT_CLASS_CONFIG_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.MmAlertConfigImpl <em>Mm Alert Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.MmAlertConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getMmAlertConfig()
	 * @generated
	 */
	int MM_ALERT_CONFIG = 55;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_ALERT_CONFIG__PATH = 0;

	/**
	 * The feature id for the '<em><b>Severity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_ALERT_CONFIG__SEVERITY = 1;

	/**
	 * The number of structural features of the '<em>Mm Alert Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_ALERT_CONFIG_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.MmExecuteCommandConfigImpl <em>Mm Execute Command Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.MmExecuteCommandConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getMmExecuteCommandConfig()
	 * @generated
	 */
	int MM_EXECUTE_COMMAND_CONFIG = 56;

	/**
	 * The feature id for the '<em><b>Command</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_EXECUTE_COMMAND_CONFIG__COMMAND = 0;

	/**
	 * The number of structural features of the '<em>Mm Execute Command Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_EXECUTE_COMMAND_CONFIG_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.MmHealthLevelConfigImpl <em>Mm Health Level Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.MmHealthLevelConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getMmHealthLevelConfig()
	 * @generated
	 */
	int MM_HEALTH_LEVEL_CONFIG = 57;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_HEALTH_LEVEL_CONFIG__PATH = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_HEALTH_LEVEL_CONFIG__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Mm Health Level Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_HEALTH_LEVEL_CONFIG_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.MmSendEmailConfigImpl <em>Mm Send Email Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.MmSendEmailConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getMmSendEmailConfig()
	 * @generated
	 */
	int MM_SEND_EMAIL_CONFIG = 58;

	/**
	 * The feature id for the '<em><b>Cc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_SEND_EMAIL_CONFIG__CC = 0;

	/**
	 * The feature id for the '<em><b>Msg</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_SEND_EMAIL_CONFIG__MSG = 1;

	/**
	 * The feature id for the '<em><b>Subject</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_SEND_EMAIL_CONFIG__SUBJECT = 2;

	/**
	 * The feature id for the '<em><b>To</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_SEND_EMAIL_CONFIG__TO = 3;

	/**
	 * The number of structural features of the '<em>Mm Send Email Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_SEND_EMAIL_CONFIG_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.MmTriggerConditionConfigImpl <em>Mm Trigger Condition Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.MmTriggerConditionConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getMmTriggerConditionConfig()
	 * @generated
	 */
	int MM_TRIGGER_CONDITION_CONFIG = 59;

	/**
	 * The feature id for the '<em><b>Mm Health Level</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_TRIGGER_CONDITION_CONFIG__MM_HEALTH_LEVEL = 0;

	/**
	 * The feature id for the '<em><b>Mm Alert</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_TRIGGER_CONDITION_CONFIG__MM_ALERT = 1;

	/**
	 * The number of structural features of the '<em>Mm Trigger Condition Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MM_TRIGGER_CONDITION_CONFIG_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.NotificationConfigImpl <em>Notification Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.NotificationConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getNotificationConfig()
	 * @generated
	 */
	int NOTIFICATION_CONFIG = 60;

	/**
	 * The feature id for the '<em><b>Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION_CONFIG__PROPERTY = 0;

	/**
	 * The feature id for the '<em><b>Range</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION_CONFIG__RANGE = 1;

	/**
	 * The feature id for the '<em><b>Tolerance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION_CONFIG__TOLERANCE = 2;

	/**
	 * The number of structural features of the '<em>Notification Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION_CONFIG_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.ObjectManagementConfigImpl <em>Object Management Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.ObjectManagementConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getObjectManagementConfig()
	 * @generated
	 */
	int OBJECT_MANAGEMENT_CONFIG = 61;

	/**
	 * The feature id for the '<em><b>Memory Manager</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_MANAGEMENT_CONFIG__MEMORY_MANAGER = 0;

	/**
	 * The feature id for the '<em><b>Cache Manager</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_MANAGEMENT_CONFIG__CACHE_MANAGER = 1;

	/**
	 * The feature id for the '<em><b>Db Concepts</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_MANAGEMENT_CONFIG__DB_CONCEPTS = 2;

	/**
	 * The number of structural features of the '<em>Object Management Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_MANAGEMENT_CONFIG_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.ObjectTableConfigImpl <em>Object Table Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.ObjectTableConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getObjectTableConfig()
	 * @generated
	 */
	int OBJECT_TABLE_CONFIG = 63;

	/**
	 * The feature id for the '<em><b>Max Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_TABLE_CONFIG__MAX_SIZE = 0;

	/**
	 * The number of structural features of the '<em>Object Table Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_TABLE_CONFIG_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.SystemPropertyConfigImpl <em>System Property Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.SystemPropertyConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getSystemPropertyConfig()
	 * @generated
	 */
	int SYSTEM_PROPERTY_CONFIG = 91;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_PROPERTY_CONFIG__MIXED = 0;

	/**
	 * The feature id for the '<em><b>System Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_PROPERTY_CONFIG__SYSTEM_PROPERTY = 1;

	/**
	 * The number of structural features of the '<em>System Property Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_PROPERTY_CONFIG_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.OverrideConfigImpl <em>Override Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.OverrideConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getOverrideConfig()
	 * @generated
	 */
	int OVERRIDE_CONFIG = 64;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OVERRIDE_CONFIG__MIXED = SYSTEM_PROPERTY_CONFIG__MIXED;

	/**
	 * The feature id for the '<em><b>System Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OVERRIDE_CONFIG__SYSTEM_PROPERTY = SYSTEM_PROPERTY_CONFIG__SYSTEM_PROPERTY;

	/**
	 * The number of structural features of the '<em>Override Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OVERRIDE_CONFIG_FEATURE_COUNT = SYSTEM_PROPERTY_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.ProcessAgentClassConfigImpl <em>Process Agent Class Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.ProcessAgentClassConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getProcessAgentClassConfig()
	 * @generated
	 */
	int PROCESS_AGENT_CLASS_CONFIG = 65;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_AGENT_CLASS_CONFIG__ID = AGENT_CLASS_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Load</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_AGENT_CLASS_CONFIG__LOAD = AGENT_CLASS_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Process Engine</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_AGENT_CLASS_CONFIG__PROCESS_ENGINE = AGENT_CLASS_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Property Group</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_AGENT_CLASS_CONFIG__PROPERTY_GROUP = AGENT_CLASS_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Process Agent Class Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_AGENT_CLASS_CONFIG_FEATURE_COUNT = AGENT_CLASS_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.ProcessConfigImpl <em>Process Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.ProcessConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getProcessConfig()
	 * @generated
	 */
	int PROCESS_CONFIG = 66;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_CONFIG__GROUP = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Ref</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_CONFIG__REF = ARTIFACT_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_CONFIG__URI = ARTIFACT_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Process Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.ProcessEngineConfigImpl <em>Process Engine Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.ProcessEngineConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getProcessEngineConfig()
	 * @generated
	 */
	int PROCESS_ENGINE_CONFIG = 67;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ENGINE_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Process</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ENGINE_CONFIG__PROCESS = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Startup</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ENGINE_CONFIG__STARTUP = ARTIFACT_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Shutdown</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ENGINE_CONFIG__SHUTDOWN = ARTIFACT_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ENGINE_CONFIG__RULES = ARTIFACT_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Destinations</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ENGINE_CONFIG__DESTINATIONS = ARTIFACT_CONFIG_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Businessworks</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ENGINE_CONFIG__BUSINESSWORKS = ARTIFACT_CONFIG_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Property Group</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ENGINE_CONFIG__PROPERTY_GROUP = ARTIFACT_CONFIG_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Job Manager</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ENGINE_CONFIG__JOB_MANAGER = ARTIFACT_CONFIG_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Inference Engine</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ENGINE_CONFIG__INFERENCE_ENGINE = ARTIFACT_CONFIG_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>Process Engine Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ENGINE_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 9;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.ProcessesConfigImpl <em>Processes Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.ProcessesConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getProcessesConfig()
	 * @generated
	 */
	int PROCESSES_CONFIG = 68;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSES_CONFIG__ID = URIS_AND_REFS_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSES_CONFIG__GROUP = URIS_AND_REFS_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Ref</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSES_CONFIG__REF = URIS_AND_REFS_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSES_CONFIG__URI = URIS_AND_REFS_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Processes Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSES_CONFIG_FEATURE_COUNT = URIS_AND_REFS_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.ProcessGroupsConfigImpl <em>Process Groups Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.ProcessGroupsConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getProcessGroupsConfig()
	 * @generated
	 */
	int PROCESS_GROUPS_CONFIG = 69;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_GROUPS_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Processes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_GROUPS_CONFIG__PROCESSES = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Process Groups Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_GROUPS_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.ProcessingUnitConfigImpl <em>Processing Unit Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.ProcessingUnitConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getProcessingUnitConfig()
	 * @generated
	 */
	int PROCESSING_UNIT_CONFIG = 70;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSING_UNIT_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Agents</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSING_UNIT_CONFIG__AGENTS = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Cache Storage Enabled</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSING_UNIT_CONFIG__CACHE_STORAGE_ENABLED = ARTIFACT_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Db Concepts</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSING_UNIT_CONFIG__DB_CONCEPTS = ARTIFACT_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Logs</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSING_UNIT_CONFIG__LOGS = ARTIFACT_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Hot Deploy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSING_UNIT_CONFIG__HOT_DEPLOY = ARTIFACT_CONFIG_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Http</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSING_UNIT_CONFIG__HTTP = ARTIFACT_CONFIG_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Property Group</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSING_UNIT_CONFIG__PROPERTY_GROUP = ARTIFACT_CONFIG_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Security Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSING_UNIT_CONFIG__SECURITY_CONFIG = ARTIFACT_CONFIG_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Processing Unit Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSING_UNIT_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 8;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.ProcessingUnitsConfigImpl <em>Processing Units Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.ProcessingUnitsConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getProcessingUnitsConfig()
	 * @generated
	 */
	int PROCESSING_UNITS_CONFIG = 71;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSING_UNITS_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSING_UNITS_CONFIG__GROUP = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Processing Unit</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSING_UNITS_CONFIG__PROCESSING_UNIT = ARTIFACT_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Processing Units Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSING_UNITS_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.ProcessingUnitSecurityConfigImpl <em>Processing Unit Security Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.ProcessingUnitSecurityConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getProcessingUnitSecurityConfig()
	 * @generated
	 */
	int PROCESSING_UNIT_SECURITY_CONFIG = 72;

	/**
	 * The feature id for the '<em><b>Role</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSING_UNIT_SECURITY_CONFIG__ROLE = SECURITY_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Controller</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSING_UNIT_SECURITY_CONFIG__CONTROLLER = SECURITY_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Requester</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSING_UNIT_SECURITY_CONFIG__REQUESTER = SECURITY_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Processing Unit Security Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSING_UNIT_SECURITY_CONFIG_FEATURE_COUNT = SECURITY_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.ProjectionConfigImpl <em>Projection Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.ProjectionConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getProjectionConfig()
	 * @generated
	 */
	int PROJECTION_CONFIG = 73;

	/**
	 * The feature id for the '<em><b>Set Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECTION_CONFIG__SET_PROPERTY = 0;

	/**
	 * The number of structural features of the '<em>Projection Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECTION_CONFIG_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.PropertyConfigImpl <em>Property Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.PropertyConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getPropertyConfig()
	 * @generated
	 */
	int PROPERTY_CONFIG = 74;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_CONFIG__NAME = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_CONFIG__TYPE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_CONFIG__VALUE = 2;

	/**
	 * The number of structural features of the '<em>Property Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_CONFIG_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.PropertyGroupConfigImpl <em>Property Group Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.PropertyGroupConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getPropertyGroupConfig()
	 * @generated
	 */
	int PROPERTY_GROUP_CONFIG = 75;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GROUP_CONFIG__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Property Group</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GROUP_CONFIG__PROPERTY_GROUP = 1;

	/**
	 * The feature id for the '<em><b>Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GROUP_CONFIG__PROPERTY = 2;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GROUP_CONFIG__COMMENT = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GROUP_CONFIG__NAME = 4;

	/**
	 * The number of structural features of the '<em>Property Group Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GROUP_CONFIG_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.ProtocolsConfigImpl <em>Protocols Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.ProtocolsConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getProtocolsConfig()
	 * @generated
	 */
	int PROTOCOLS_CONFIG = 76;

	/**
	 * The feature id for the '<em><b>Protocol</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOLS_CONFIG__PROTOCOL = 0;

	/**
	 * The number of structural features of the '<em>Protocols Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOLS_CONFIG_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.ProviderConfigImpl <em>Provider Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.ProviderConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getProviderConfig()
	 * @generated
	 */
	int PROVIDER_CONFIG = 77;

	/**
	 * The feature id for the '<em><b>Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDER_CONFIG__NAME = 0;

	/**
	 * The number of structural features of the '<em>Provider Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDER_CONFIG_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.QueryAgentClassConfigImpl <em>Query Agent Class Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.QueryAgentClassConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getQueryAgentClassConfig()
	 * @generated
	 */
	int QUERY_AGENT_CLASS_CONFIG = 78;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_AGENT_CLASS_CONFIG__ID = AGENT_CLASS_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Destinations</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_AGENT_CLASS_CONFIG__DESTINATIONS = AGENT_CLASS_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Startup</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_AGENT_CLASS_CONFIG__STARTUP = AGENT_CLASS_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Shutdown</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_AGENT_CLASS_CONFIG__SHUTDOWN = AGENT_CLASS_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Load</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_AGENT_CLASS_CONFIG__LOAD = AGENT_CLASS_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Local Cache</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_AGENT_CLASS_CONFIG__LOCAL_CACHE = AGENT_CLASS_CONFIG_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Property Group</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_AGENT_CLASS_CONFIG__PROPERTY_GROUP = AGENT_CLASS_CONFIG_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Shared Queue</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_AGENT_CLASS_CONFIG__SHARED_QUEUE = AGENT_CLASS_CONFIG_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Query Agent Class Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_AGENT_CLASS_CONFIG_FEATURE_COUNT = AGENT_CLASS_CONFIG_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.RevisionConfigImpl <em>Revision Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.RevisionConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getRevisionConfig()
	 * @generated
	 */
	int REVISION_CONFIG = 79;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISION_CONFIG__VERSION = 0;

	/**
	 * The feature id for the '<em><b>Author</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISION_CONFIG__AUTHOR = 1;

	/**
	 * The feature id for the '<em><b>Date</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISION_CONFIG__DATE = 2;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISION_CONFIG__COMMENT = 3;

	/**
	 * The number of structural features of the '<em>Revision Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISION_CONFIG_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.RuleConfigImpl <em>Rule Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.RuleConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getRuleConfig()
	 * @generated
	 */
	int RULE_CONFIG = 80;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_CONFIG__ENABLED = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_CONFIG__URI = ARTIFACT_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Rule Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.RuleConfigConfigImpl <em>Rule Config Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.RuleConfigConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getRuleConfigConfig()
	 * @generated
	 */
	int RULE_CONFIG_CONFIG = 81;

	/**
	 * The feature id for the '<em><b>Cluster Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_CONFIG_CONFIG__CLUSTER_MEMBER = 0;

	/**
	 * The number of structural features of the '<em>Rule Config Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_CONFIG_CONFIG_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.RulesConfigImpl <em>Rules Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.RulesConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getRulesConfig()
	 * @generated
	 */
	int RULES_CONFIG = 82;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULES_CONFIG__ID = URIS_AND_REFS_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULES_CONFIG__GROUP = URIS_AND_REFS_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Ref</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULES_CONFIG__REF = URIS_AND_REFS_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULES_CONFIG__URI = URIS_AND_REFS_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Rules Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULES_CONFIG_FEATURE_COUNT = URIS_AND_REFS_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.RulesetsConfigImpl <em>Rulesets Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.RulesetsConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getRulesetsConfig()
	 * @generated
	 */
	int RULESETS_CONFIG = 83;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULESETS_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULESETS_CONFIG__RULES = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Rulesets Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULESETS_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.SecurityControllerImpl <em>Security Controller</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.SecurityControllerImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getSecurityController()
	 * @generated
	 */
	int SECURITY_CONTROLLER = 85;

	/**
	 * The feature id for the '<em><b>Policy File</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_CONTROLLER__POLICY_FILE = 0;

	/**
	 * The feature id for the '<em><b>Identity Password</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_CONTROLLER__IDENTITY_PASSWORD = 1;

	/**
	 * The number of structural features of the '<em>Security Controller</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_CONTROLLER_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.SecurityOverrideConfigImpl <em>Security Override Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.SecurityOverrideConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getSecurityOverrideConfig()
	 * @generated
	 */
	int SECURITY_OVERRIDE_CONFIG = 86;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_OVERRIDE_CONFIG__MIXED = OVERRIDE_CONFIG__MIXED;

	/**
	 * The feature id for the '<em><b>System Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_OVERRIDE_CONFIG__SYSTEM_PROPERTY = OVERRIDE_CONFIG__SYSTEM_PROPERTY;

	/**
	 * The feature id for the '<em><b>Override</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_OVERRIDE_CONFIG__OVERRIDE = OVERRIDE_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Security Override Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_OVERRIDE_CONFIG_FEATURE_COUNT = OVERRIDE_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.SecurityRequesterImpl <em>Security Requester</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.SecurityRequesterImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getSecurityRequester()
	 * @generated
	 */
	int SECURITY_REQUESTER = 87;

	/**
	 * The feature id for the '<em><b>Token File</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_REQUESTER__TOKEN_FILE = 0;

	/**
	 * The feature id for the '<em><b>Identity Password</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_REQUESTER__IDENTITY_PASSWORD = 1;

	/**
	 * The feature id for the '<em><b>Certificate Key File</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_REQUESTER__CERTIFICATE_KEY_FILE = 2;

	/**
	 * The feature id for the '<em><b>Domain Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_REQUESTER__DOMAIN_NAME = 3;

	/**
	 * The feature id for the '<em><b>User Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_REQUESTER__USER_NAME = 4;

	/**
	 * The feature id for the '<em><b>User Password</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_REQUESTER__USER_PASSWORD = 5;

	/**
	 * The number of structural features of the '<em>Security Requester</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_REQUESTER_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.SetPropertyConfigImpl <em>Set Property Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.SetPropertyConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getSetPropertyConfig()
	 * @generated
	 */
	int SET_PROPERTY_CONFIG = 88;

	/**
	 * The feature id for the '<em><b>Child Cluster Member</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SET_PROPERTY_CONFIG__CHILD_CLUSTER_MEMBER = 0;

	/**
	 * The feature id for the '<em><b>Notification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SET_PROPERTY_CONFIG__NOTIFICATION = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SET_PROPERTY_CONFIG__NAME = 2;

	/**
	 * The feature id for the '<em><b>Set Property Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SET_PROPERTY_CONFIG__SET_PROPERTY_NAME = 3;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SET_PROPERTY_CONFIG__VALUE = 4;

	/**
	 * The number of structural features of the '<em>Set Property Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SET_PROPERTY_CONFIG_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.SharedQueueConfigImpl <em>Shared Queue Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.SharedQueueConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getSharedQueueConfig()
	 * @generated
	 */
	int SHARED_QUEUE_CONFIG = 89;

	/**
	 * The feature id for the '<em><b>Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_QUEUE_CONFIG__SIZE = 0;

	/**
	 * The feature id for the '<em><b>Workers</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_QUEUE_CONFIG__WORKERS = 1;

	/**
	 * The number of structural features of the '<em>Shared Queue Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_QUEUE_CONFIG_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.SslConfigImpl <em>Ssl Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.SslConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getSslConfig()
	 * @generated
	 */
	int SSL_CONFIG = 90;

	/**
	 * The feature id for the '<em><b>Protocols</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SSL_CONFIG__PROTOCOLS = 0;

	/**
	 * The feature id for the '<em><b>Ciphers</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SSL_CONFIG__CIPHERS = 1;

	/**
	 * The feature id for the '<em><b>Key Manager Algorithm</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SSL_CONFIG__KEY_MANAGER_ALGORITHM = 2;

	/**
	 * The feature id for the '<em><b>Trust Manager Algorithm</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SSL_CONFIG__TRUST_MANAGER_ALGORITHM = 3;

	/**
	 * The number of structural features of the '<em>Ssl Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SSL_CONFIG_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.TerminalConfigImpl <em>Terminal Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.TerminalConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getTerminalConfig()
	 * @generated
	 */
	int TERMINAL_CONFIG = 92;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_CONFIG__ENABLED = 0;

	/**
	 * The feature id for the '<em><b>Sys Err Redirect</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_CONFIG__SYS_ERR_REDIRECT = 1;

	/**
	 * The feature id for the '<em><b>Sys Out Redirect</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_CONFIG__SYS_OUT_REDIRECT = 2;

	/**
	 * The feature id for the '<em><b>Encoding</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_CONFIG__ENCODING = 3;

	/**
	 * The number of structural features of the '<em>Terminal Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_CONFIG_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.UrisConfigImpl <em>Uris Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.UrisConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getUrisConfig()
	 * @generated
	 */
	int URIS_CONFIG = 94;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URIS_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URIS_CONFIG__GROUP = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URIS_CONFIG__URI = ARTIFACT_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Uris Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URIS_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.FieldEncryptionConfigImpl <em>Field Encryption Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.FieldEncryptionConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getFieldEncryptionConfig()
	 * @generated
	 */
	int FIELD_ENCRYPTION_CONFIG = 95;

	/**
	 * The feature id for the '<em><b>Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ENCRYPTION_CONFIG__PROPERTY = 0;

	/**
	 * The number of structural features of the '<em>Field Encryption Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ENCRYPTION_CONFIG_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.EntityConfigImpl <em>Entity Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.EntityConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getEntityConfig()
	 * @generated
	 */
	int ENTITY_CONFIG = 96;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_CONFIG__ID = ARTIFACT_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_CONFIG__URI = ARTIFACT_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_CONFIG__FILTER = ARTIFACT_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Enable Table Trimming</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_CONFIG__ENABLE_TABLE_TRIMMING = ARTIFACT_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Trimming Field</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_CONFIG__TRIMMING_FIELD = ARTIFACT_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Trimming Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_CONFIG__TRIMMING_RULE = ARTIFACT_CONFIG_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Entity Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_CONFIG_FEATURE_COUNT = ARTIFACT_CONFIG_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.EntitySetConfigImpl <em>Entity Set Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.EntitySetConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getEntitySetConfig()
	 * @generated
	 */
	int ENTITY_SET_CONFIG = 97;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_SET_CONFIG__ENTITY = 0;

	/**
	 * The feature id for the '<em><b>Generate LV Files</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_SET_CONFIG__GENERATE_LV_FILES = 1;

	/**
	 * The feature id for the '<em><b>Output Path</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_SET_CONFIG__OUTPUT_PATH = 2;

	/**
	 * The number of structural features of the '<em>Entity Set Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_SET_CONFIG_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.LDMConnectionConfigImpl <em>LDM Connection Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.LDMConnectionConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getLDMConnectionConfig()
	 * @generated
	 */
	int LDM_CONNECTION_CONFIG = 98;

	/**
	 * The feature id for the '<em><b>Ldm Url</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LDM_CONNECTION_CONFIG__LDM_URL = 0;

	/**
	 * The feature id for the '<em><b>User Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LDM_CONNECTION_CONFIG__USER_NAME = 1;

	/**
	 * The feature id for the '<em><b>User Password</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LDM_CONNECTION_CONFIG__USER_PASSWORD = 2;

	/**
	 * The feature id for the '<em><b>Initial Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LDM_CONNECTION_CONFIG__INITIAL_SIZE = 3;

	/**
	 * The feature id for the '<em><b>Min Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LDM_CONNECTION_CONFIG__MIN_SIZE = 4;

	/**
	 * The feature id for the '<em><b>Max Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LDM_CONNECTION_CONFIG__MAX_SIZE = 5;

	/**
	 * The number of structural features of the '<em>LDM Connection Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LDM_CONNECTION_CONFIG_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.PublisherConfigImpl <em>Publisher Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.PublisherConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getPublisherConfig()
	 * @generated
	 */
	int PUBLISHER_CONFIG = 99;

	/**
	 * The feature id for the '<em><b>Publisher Queue Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUBLISHER_CONFIG__PUBLISHER_QUEUE_SIZE = 0;

	/**
	 * The feature id for the '<em><b>Publisher Thread Count</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUBLISHER_CONFIG__PUBLISHER_THREAD_COUNT = 1;

	/**
	 * The number of structural features of the '<em>Publisher Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUBLISHER_CONFIG_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.LiveViewAgentClassConfigImpl <em>Live View Agent Class Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.LiveViewAgentClassConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getLiveViewAgentClassConfig()
	 * @generated
	 */
	int LIVE_VIEW_AGENT_CLASS_CONFIG = 100;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIVE_VIEW_AGENT_CLASS_CONFIG__ID = AGENT_CLASS_CONFIG__ID;

	/**
	 * The feature id for the '<em><b>Ldm Connection</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIVE_VIEW_AGENT_CLASS_CONFIG__LDM_CONNECTION = AGENT_CLASS_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Publisher</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIVE_VIEW_AGENT_CLASS_CONFIG__PUBLISHER = AGENT_CLASS_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Entity Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIVE_VIEW_AGENT_CLASS_CONFIG__ENTITY_SET = AGENT_CLASS_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Load</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIVE_VIEW_AGENT_CLASS_CONFIG__LOAD = AGENT_CLASS_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Property Group</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIVE_VIEW_AGENT_CLASS_CONFIG__PROPERTY_GROUP = AGENT_CLASS_CONFIG_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Live View Agent Class Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIVE_VIEW_AGENT_CLASS_CONFIG_FEATURE_COUNT = AGENT_CLASS_CONFIG_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.CompositeIndexPropertiesConfigImpl <em>Composite Index Properties Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.CompositeIndexPropertiesConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getCompositeIndexPropertiesConfig()
	 * @generated
	 */
	int COMPOSITE_INDEX_PROPERTIES_CONFIG = 101;

	/**
	 * The feature id for the '<em><b>Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_INDEX_PROPERTIES_CONFIG__PROPERTY = 0;

	/**
	 * The number of structural features of the '<em>Composite Index Properties Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_INDEX_PROPERTIES_CONFIG_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.CompositeIndexConfigImpl <em>Composite Index Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.CompositeIndexConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getCompositeIndexConfig()
	 * @generated
	 */
	int COMPOSITE_INDEX_CONFIG = 102;

	/**
	 * The feature id for the '<em><b>Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_INDEX_CONFIG__NAME = 0;

	/**
	 * The feature id for the '<em><b>Composite Index Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_INDEX_CONFIG__COMPOSITE_INDEX_PROPERTIES = 1;

	/**
	 * The number of structural features of the '<em>Composite Index Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_INDEX_CONFIG_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.impl.CompositeIndexesConfigImpl <em>Composite Indexes Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.impl.CompositeIndexesConfigImpl
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getCompositeIndexesConfig()
	 * @generated
	 */
	int COMPOSITE_INDEXES_CONFIG = 103;

	/**
	 * The feature id for the '<em><b>Composite Index</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_INDEXES_CONFIG__COMPOSITE_INDEX = 0;

	/**
	 * The number of structural features of the '<em>Composite Indexes Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_INDEXES_CONFIG_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.DomainObjectModeConfig <em>Domain Object Mode Config</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.DomainObjectModeConfig
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getDomainObjectModeConfig()
	 * @generated
	 */
	int DOMAIN_OBJECT_MODE_CONFIG = 104;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.cdd.ThreadingModelConfig <em>Threading Model Config</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.ThreadingModelConfig
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getThreadingModelConfig()
	 * @generated
	 */
	int THREADING_MODEL_CONFIG = 105;

	/**
	 * The meta object id for the '<em>Domain Object Mode Config Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.DomainObjectModeConfig
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getDomainObjectModeConfigObject()
	 * @generated
	 */
	int DOMAIN_OBJECT_MODE_CONFIG_OBJECT = 106;

	/**
	 * The meta object id for the '<em>Ontology Uri Config</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getOntologyUriConfig()
	 * @generated
	 */
	int ONTOLOGY_URI_CONFIG = 107;

	/**
	 * The meta object id for the '<em>Threading Model Config Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.cdd.ThreadingModelConfig
	 * @see com.tibco.be.util.config.cdd.impl.CddPackageImpl#getThreadingModelConfigObject()
	 * @generated
	 */
	int THREADING_MODEL_CONFIG_OBJECT = 108;


	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.AgentClassConfig <em>Agent Class Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Agent Class Config</em>'.
	 * @see com.tibco.be.util.config.cdd.AgentClassConfig
	 * @generated
	 */
	EClass getAgentClassConfig();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.AgentClassesConfig <em>Agent Classes Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Agent Classes Config</em>'.
	 * @see com.tibco.be.util.config.cdd.AgentClassesConfig
	 * @generated
	 */
	EClass getAgentClassesConfig();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.util.config.cdd.AgentClassesConfig#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.tibco.be.util.config.cdd.AgentClassesConfig#getGroup()
	 * @see #getAgentClassesConfig()
	 * @generated
	 */
	EAttribute getAgentClassesConfig_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.AgentClassesConfig#getCacheAgentConfig <em>Cache Agent Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Cache Agent Config</em>'.
	 * @see com.tibco.be.util.config.cdd.AgentClassesConfig#getCacheAgentConfig()
	 * @see #getAgentClassesConfig()
	 * @generated
	 */
	EReference getAgentClassesConfig_CacheAgentConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.AgentClassesConfig#getDashboardAgentConfig <em>Dashboard Agent Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Dashboard Agent Config</em>'.
	 * @see com.tibco.be.util.config.cdd.AgentClassesConfig#getDashboardAgentConfig()
	 * @see #getAgentClassesConfig()
	 * @generated
	 */
	EReference getAgentClassesConfig_DashboardAgentConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.AgentClassesConfig#getInferenceAgentConfig <em>Inference Agent Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Inference Agent Config</em>'.
	 * @see com.tibco.be.util.config.cdd.AgentClassesConfig#getInferenceAgentConfig()
	 * @see #getAgentClassesConfig()
	 * @generated
	 */
	EReference getAgentClassesConfig_InferenceAgentConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.AgentClassesConfig#getQueryAgentConfig <em>Query Agent Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Query Agent Config</em>'.
	 * @see com.tibco.be.util.config.cdd.AgentClassesConfig#getQueryAgentConfig()
	 * @see #getAgentClassesConfig()
	 * @generated
	 */
	EReference getAgentClassesConfig_QueryAgentConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.AgentClassesConfig#getMmAgentConfig <em>Mm Agent Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mm Agent Config</em>'.
	 * @see com.tibco.be.util.config.cdd.AgentClassesConfig#getMmAgentConfig()
	 * @see #getAgentClassesConfig()
	 * @generated
	 */
	EReference getAgentClassesConfig_MmAgentConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.AgentClassesConfig#getProcessAgentConfig <em>Process Agent Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Process Agent Config</em>'.
	 * @see com.tibco.be.util.config.cdd.AgentClassesConfig#getProcessAgentConfig()
	 * @see #getAgentClassesConfig()
	 * @generated
	 */
	EReference getAgentClassesConfig_ProcessAgentConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.AgentClassesConfig#getLiveViewAgentConfig <em>Live View Agent Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Live View Agent Config</em>'.
	 * @see com.tibco.be.util.config.cdd.AgentClassesConfig#getLiveViewAgentConfig()
	 * @see #getAgentClassesConfig()
	 * @generated
	 */
	EReference getAgentClassesConfig_LiveViewAgentConfig();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.AgentConfig <em>Agent Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Agent Config</em>'.
	 * @see com.tibco.be.util.config.cdd.AgentConfig
	 * @generated
	 */
	EClass getAgentConfig();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.util.config.cdd.AgentConfig#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.tibco.be.util.config.cdd.AgentConfig#getMixed()
	 * @see #getAgentConfig()
	 * @generated
	 */
	EAttribute getAgentConfig_Mixed();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.be.util.config.cdd.AgentConfig#getRef <em>Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Ref</em>'.
	 * @see com.tibco.be.util.config.cdd.AgentConfig#getRef()
	 * @see #getAgentConfig()
	 * @generated
	 */
	EReference getAgentConfig_Ref();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.AgentConfig#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Key</em>'.
	 * @see com.tibco.be.util.config.cdd.AgentConfig#getKey()
	 * @see #getAgentConfig()
	 * @generated
	 */
	EReference getAgentConfig_Key();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.AgentConfig#getPriority <em>Priority</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Priority</em>'.
	 * @see com.tibco.be.util.config.cdd.AgentConfig#getPriority()
	 * @see #getAgentConfig()
	 * @generated
	 */
	EReference getAgentConfig_Priority();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.AgentsConfig <em>Agents Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Agents Config</em>'.
	 * @see com.tibco.be.util.config.cdd.AgentsConfig
	 * @generated
	 */
	EClass getAgentsConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.AgentsConfig#getAgent <em>Agent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Agent</em>'.
	 * @see com.tibco.be.util.config.cdd.AgentsConfig#getAgent()
	 * @see #getAgentsConfig()
	 * @generated
	 */
	EReference getAgentsConfig_Agent();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.AlertConfigConfig <em>Alert Config Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alert Config Config</em>'.
	 * @see com.tibco.be.util.config.cdd.AlertConfigConfig
	 * @generated
	 */
	EClass getAlertConfigConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.AlertConfigConfig#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see com.tibco.be.util.config.cdd.AlertConfigConfig#getCondition()
	 * @see #getAlertConfigConfig()
	 * @generated
	 */
	EReference getAlertConfigConfig_Condition();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.AlertConfigConfig#getProjection <em>Projection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Projection</em>'.
	 * @see com.tibco.be.util.config.cdd.AlertConfigConfig#getProjection()
	 * @see #getAlertConfigConfig()
	 * @generated
	 */
	EReference getAlertConfigConfig_Projection();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.AlertConfigConfig#getAlertName <em>Alert Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Alert Name</em>'.
	 * @see com.tibco.be.util.config.cdd.AlertConfigConfig#getAlertName()
	 * @see #getAlertConfigConfig()
	 * @generated
	 */
	EAttribute getAlertConfigConfig_AlertName();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.AlertConfigSetConfig <em>Alert Config Set Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alert Config Set Config</em>'.
	 * @see com.tibco.be.util.config.cdd.AlertConfigSetConfig
	 * @generated
	 */
	EClass getAlertConfigSetConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.AlertConfigSetConfig#getAlertConfig <em>Alert Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Alert Config</em>'.
	 * @see com.tibco.be.util.config.cdd.AlertConfigSetConfig#getAlertConfig()
	 * @see #getAlertConfigSetConfig()
	 * @generated
	 */
	EReference getAlertConfigSetConfig_AlertConfig();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.ArtifactConfig <em>Artifact Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Artifact Config</em>'.
	 * @see com.tibco.be.util.config.cdd.ArtifactConfig
	 * @generated
	 */
	EClass getArtifactConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.ArtifactConfig#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.tibco.be.util.config.cdd.ArtifactConfig#getId()
	 * @see #getArtifactConfig()
	 * @generated
	 */
	EAttribute getArtifactConfig_Id();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.BackingStoreConfig <em>Backing Store Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Backing Store Config</em>'.
	 * @see com.tibco.be.util.config.cdd.BackingStoreConfig
	 * @generated
	 */
	EClass getBackingStoreConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getCacheAside <em>Cache Aside</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cache Aside</em>'.
	 * @see com.tibco.be.util.config.cdd.BackingStoreConfig#getCacheAside()
	 * @see #getBackingStoreConfig()
	 * @generated
	 */
	EReference getBackingStoreConfig_CacheAside();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getCacheLoaderClass <em>Cache Loader Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cache Loader Class</em>'.
	 * @see com.tibco.be.util.config.cdd.BackingStoreConfig#getCacheLoaderClass()
	 * @see #getBackingStoreConfig()
	 * @generated
	 */
	EReference getBackingStoreConfig_CacheLoaderClass();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getDataStorePath <em>Data Store Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Data Store Path</em>'.
	 * @see com.tibco.be.util.config.cdd.BackingStoreConfig#getDataStorePath()
	 * @see #getBackingStoreConfig()
	 * @generated
	 */
	EReference getBackingStoreConfig_DataStorePath();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getEnforcePools <em>Enforce Pools</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Enforce Pools</em>'.
	 * @see com.tibco.be.util.config.cdd.BackingStoreConfig#getEnforcePools()
	 * @see #getBackingStoreConfig()
	 * @generated
	 */
	EReference getBackingStoreConfig_EnforcePools();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getPersistenceOption <em>Persistence Option</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Persistence Option</em>'.
	 * @see com.tibco.be.util.config.cdd.BackingStoreConfig#getPersistenceOption()
	 * @see #getBackingStoreConfig()
	 * @generated
	 */
	EReference getBackingStoreConfig_PersistenceOption();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getPersistencePolicy <em>Persistence Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Persistence Policy</em>'.
	 * @see com.tibco.be.util.config.cdd.BackingStoreConfig#getPersistencePolicy()
	 * @see #getBackingStoreConfig()
	 * @generated
	 */
	EReference getBackingStoreConfig_PersistencePolicy();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getParallelRecovery <em>Parallel Recovery</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Parallel Recovery</em>'.
	 * @see com.tibco.be.util.config.cdd.BackingStoreConfig#getParallelRecovery()
	 * @see #getBackingStoreConfig()
	 * @generated
	 */
	EReference getBackingStoreConfig_ParallelRecovery();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getPrimaryConnection <em>Primary Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Primary Connection</em>'.
	 * @see com.tibco.be.util.config.cdd.BackingStoreConfig#getPrimaryConnection()
	 * @see #getBackingStoreConfig()
	 * @generated
	 */
	EReference getBackingStoreConfig_PrimaryConnection();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getSecondaryConnection <em>Secondary Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Secondary Connection</em>'.
	 * @see com.tibco.be.util.config.cdd.BackingStoreConfig#getSecondaryConnection()
	 * @see #getBackingStoreConfig()
	 * @generated
	 */
	EReference getBackingStoreConfig_SecondaryConnection();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getStrategy <em>Strategy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Strategy</em>'.
	 * @see com.tibco.be.util.config.cdd.BackingStoreConfig#getStrategy()
	 * @see #getBackingStoreConfig()
	 * @generated
	 */
	EReference getBackingStoreConfig_Strategy();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Type</em>'.
	 * @see com.tibco.be.util.config.cdd.BackingStoreConfig#getType()
	 * @see #getBackingStoreConfig()
	 * @generated
	 */
	EReference getBackingStoreConfig_Type();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.BackingStoreForDomainObjectConfig <em>Backing Store For Domain Object Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Backing Store For Domain Object Config</em>'.
	 * @see com.tibco.be.util.config.cdd.BackingStoreForDomainObjectConfig
	 * @generated
	 */
	EClass getBackingStoreForDomainObjectConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.BackingStoreForDomainObjectConfig#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Properties</em>'.
	 * @see com.tibco.be.util.config.cdd.BackingStoreForDomainObjectConfig#getProperties()
	 * @see #getBackingStoreForDomainObjectConfig()
	 * @generated
	 */
	EReference getBackingStoreForDomainObjectConfig_Properties();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.BackingStoreForDomainObjectConfig#getEnabled <em>Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Enabled</em>'.
	 * @see com.tibco.be.util.config.cdd.BackingStoreForDomainObjectConfig#getEnabled()
	 * @see #getBackingStoreForDomainObjectConfig()
	 * @generated
	 */
	EReference getBackingStoreForDomainObjectConfig_Enabled();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.BackingStoreForDomainObjectConfig#getTableName <em>Table Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Table Name</em>'.
	 * @see com.tibco.be.util.config.cdd.BackingStoreForDomainObjectConfig#getTableName()
	 * @see #getBackingStoreForDomainObjectConfig()
	 * @generated
	 */
	EReference getBackingStoreForDomainObjectConfig_TableName();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.BackingStoreForPropertiesConfig <em>Backing Store For Properties Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Backing Store For Properties Config</em>'.
	 * @see com.tibco.be.util.config.cdd.BackingStoreForPropertiesConfig
	 * @generated
	 */
	EClass getBackingStoreForPropertiesConfig();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.util.config.cdd.BackingStoreForPropertiesConfig#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.tibco.be.util.config.cdd.BackingStoreForPropertiesConfig#getGroup()
	 * @see #getBackingStoreForPropertiesConfig()
	 * @generated
	 */
	EAttribute getBackingStoreForPropertiesConfig_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.BackingStoreForPropertiesConfig#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Property</em>'.
	 * @see com.tibco.be.util.config.cdd.BackingStoreForPropertiesConfig#getProperty()
	 * @see #getBackingStoreForPropertiesConfig()
	 * @generated
	 */
	EReference getBackingStoreForPropertiesConfig_Property();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.BackingStoreForPropertyConfig <em>Backing Store For Property Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Backing Store For Property Config</em>'.
	 * @see com.tibco.be.util.config.cdd.BackingStoreForPropertyConfig
	 * @generated
	 */
	EClass getBackingStoreForPropertyConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.BackingStoreForPropertyConfig#getReverseReferences <em>Reverse References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Reverse References</em>'.
	 * @see com.tibco.be.util.config.cdd.BackingStoreForPropertyConfig#getReverseReferences()
	 * @see #getBackingStoreForPropertyConfig()
	 * @generated
	 */
	EReference getBackingStoreForPropertyConfig_ReverseReferences();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.BackingStoreForPropertyConfig#getMaxSize <em>Max Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Max Size</em>'.
	 * @see com.tibco.be.util.config.cdd.BackingStoreForPropertyConfig#getMaxSize()
	 * @see #getBackingStoreForPropertyConfig()
	 * @generated
	 */
	EReference getBackingStoreForPropertyConfig_MaxSize();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.BackingStoreForPropertyConfig#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Name</em>'.
	 * @see com.tibco.be.util.config.cdd.BackingStoreForPropertyConfig#getName()
	 * @see #getBackingStoreForPropertyConfig()
	 * @generated
	 */
	EReference getBackingStoreForPropertyConfig_Name();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.BusinessworksConfig <em>Businessworks Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Businessworks Config</em>'.
	 * @see com.tibco.be.util.config.cdd.BusinessworksConfig
	 * @generated
	 */
	EClass getBusinessworksConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.BusinessworksConfig#getUri <em>Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Uri</em>'.
	 * @see com.tibco.be.util.config.cdd.BusinessworksConfig#getUri()
	 * @see #getBusinessworksConfig()
	 * @generated
	 */
	EAttribute getBusinessworksConfig_Uri();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.CacheAgentClassConfig <em>Cache Agent Class Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cache Agent Class Config</em>'.
	 * @see com.tibco.be.util.config.cdd.CacheAgentClassConfig
	 * @generated
	 */
	EClass getCacheAgentClassConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CacheAgentClassConfig#getPropertyGroup <em>Property Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Property Group</em>'.
	 * @see com.tibco.be.util.config.cdd.CacheAgentClassConfig#getPropertyGroup()
	 * @see #getCacheAgentClassConfig()
	 * @generated
	 */
	EReference getCacheAgentClassConfig_PropertyGroup();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.CacheManagerConfig <em>Cache Manager Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cache Manager Config</em>'.
	 * @see com.tibco.be.util.config.cdd.CacheManagerConfig
	 * @generated
	 */
	EClass getCacheManagerConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getCacheAgentQuorum <em>Cache Agent Quorum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cache Agent Quorum</em>'.
	 * @see com.tibco.be.util.config.cdd.CacheManagerConfig#getCacheAgentQuorum()
	 * @see #getCacheManagerConfig()
	 * @generated
	 */
	EReference getCacheManagerConfig_CacheAgentQuorum();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getBackingStore <em>Backing Store</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Backing Store</em>'.
	 * @see com.tibco.be.util.config.cdd.CacheManagerConfig#getBackingStore()
	 * @see #getCacheManagerConfig()
	 * @generated
	 */
	EReference getCacheManagerConfig_BackingStore();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getDomainObjects <em>Domain Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Domain Objects</em>'.
	 * @see com.tibco.be.util.config.cdd.CacheManagerConfig#getDomainObjects()
	 * @see #getCacheManagerConfig()
	 * @generated
	 */
	EReference getCacheManagerConfig_DomainObjects();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getProvider <em>Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Provider</em>'.
	 * @see com.tibco.be.util.config.cdd.CacheManagerConfig#getProvider()
	 * @see #getCacheManagerConfig()
	 * @generated
	 */
	EReference getCacheManagerConfig_Provider();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getBackupCopies <em>Backup Copies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Backup Copies</em>'.
	 * @see com.tibco.be.util.config.cdd.CacheManagerConfig#getBackupCopies()
	 * @see #getCacheManagerConfig()
	 * @generated
	 */
	EReference getCacheManagerConfig_BackupCopies();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getEntityCacheSize <em>Entity Cache Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Entity Cache Size</em>'.
	 * @see com.tibco.be.util.config.cdd.CacheManagerConfig#getEntityCacheSize()
	 * @see #getCacheManagerConfig()
	 * @generated
	 */
	EReference getCacheManagerConfig_EntityCacheSize();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getObjectTable <em>Object Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Object Table</em>'.
	 * @see com.tibco.be.util.config.cdd.CacheManagerConfig#getObjectTable()
	 * @see #getCacheManagerConfig()
	 * @generated
	 */
	EReference getCacheManagerConfig_ObjectTable();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getDiscoveryURL <em>Discovery URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Discovery URL</em>'.
	 * @see com.tibco.be.util.config.cdd.CacheManagerConfig#getDiscoveryURL()
	 * @see #getCacheManagerConfig()
	 * @generated
	 */
	EReference getCacheManagerConfig_DiscoveryURL();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getListenURL <em>Listen URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Listen URL</em>'.
	 * @see com.tibco.be.util.config.cdd.CacheManagerConfig#getListenURL()
	 * @see #getCacheManagerConfig()
	 * @generated
	 */
	EReference getCacheManagerConfig_ListenURL();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getRemoteListenURL <em>Remote Listen URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Remote Listen URL</em>'.
	 * @see com.tibco.be.util.config.cdd.CacheManagerConfig#getRemoteListenURL()
	 * @see #getCacheManagerConfig()
	 * @generated
	 */
	EReference getCacheManagerConfig_RemoteListenURL();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getProtocolTimeout <em>Protocol Timeout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Protocol Timeout</em>'.
	 * @see com.tibco.be.util.config.cdd.CacheManagerConfig#getProtocolTimeout()
	 * @see #getCacheManagerConfig()
	 * @generated
	 */
	EReference getCacheManagerConfig_ProtocolTimeout();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getReadTimeout <em>Read Timeout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Read Timeout</em>'.
	 * @see com.tibco.be.util.config.cdd.CacheManagerConfig#getReadTimeout()
	 * @see #getCacheManagerConfig()
	 * @generated
	 */
	EReference getCacheManagerConfig_ReadTimeout();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getWriteTimeout <em>Write Timeout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Write Timeout</em>'.
	 * @see com.tibco.be.util.config.cdd.CacheManagerConfig#getWriteTimeout()
	 * @see #getCacheManagerConfig()
	 * @generated
	 */
	EReference getCacheManagerConfig_WriteTimeout();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getLockTimeout <em>Lock Timeout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Lock Timeout</em>'.
	 * @see com.tibco.be.util.config.cdd.CacheManagerConfig#getLockTimeout()
	 * @see #getCacheManagerConfig()
	 * @generated
	 */
	EReference getCacheManagerConfig_LockTimeout();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getShoutdownWait <em>Shoutdown Wait</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Shoutdown Wait</em>'.
	 * @see com.tibco.be.util.config.cdd.CacheManagerConfig#getShoutdownWait()
	 * @see #getCacheManagerConfig()
	 * @generated
	 */
	EReference getCacheManagerConfig_ShoutdownWait();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getWorkerthreadsCount <em>Workerthreads Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Workerthreads Count</em>'.
	 * @see com.tibco.be.util.config.cdd.CacheManagerConfig#getWorkerthreadsCount()
	 * @see #getCacheManagerConfig()
	 * @generated
	 */
	EReference getCacheManagerConfig_WorkerthreadsCount();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getExplicitTuple <em>Explicit Tuple</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Explicit Tuple</em>'.
	 * @see com.tibco.be.util.config.cdd.CacheManagerConfig#getExplicitTuple()
	 * @see #getCacheManagerConfig()
	 * @generated
	 */
	EReference getCacheManagerConfig_ExplicitTuple();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getSecurityConfig <em>Security Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Security Config</em>'.
	 * @see com.tibco.be.util.config.cdd.CacheManagerConfig#getSecurityConfig()
	 * @see #getCacheManagerConfig()
	 * @generated
	 */
	EReference getCacheManagerConfig_SecurityConfig();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.CacheManagerSecurityConfig <em>Cache Manager Security Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cache Manager Security Config</em>'.
	 * @see com.tibco.be.util.config.cdd.CacheManagerSecurityConfig
	 * @generated
	 */
	EClass getCacheManagerSecurityConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CacheManagerSecurityConfig#getEnabled <em>Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Enabled</em>'.
	 * @see com.tibco.be.util.config.cdd.CacheManagerSecurityConfig#getEnabled()
	 * @see #getCacheManagerSecurityConfig()
	 * @generated
	 */
	EReference getCacheManagerSecurityConfig_Enabled();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CacheManagerSecurityConfig#getController <em>Controller</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Controller</em>'.
	 * @see com.tibco.be.util.config.cdd.CacheManagerSecurityConfig#getController()
	 * @see #getCacheManagerSecurityConfig()
	 * @generated
	 */
	EReference getCacheManagerSecurityConfig_Controller();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CacheManagerSecurityConfig#getRequester <em>Requester</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Requester</em>'.
	 * @see com.tibco.be.util.config.cdd.CacheManagerSecurityConfig#getRequester()
	 * @see #getCacheManagerSecurityConfig()
	 * @generated
	 */
	EReference getCacheManagerSecurityConfig_Requester();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.CddRoot <em>Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Root</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot
	 * @generated
	 */
	EClass getCddRoot();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.util.config.cdd.CddRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getMixed()
	 * @see #getCddRoot()
	 * @generated
	 */
	EAttribute getCddRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link com.tibco.be.util.config.cdd.CddRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getXMLNSPrefixMap()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link com.tibco.be.util.config.cdd.CddRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getXSISchemaLocation()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getAcceptCount <em>Accept Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Accept Count</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getAcceptCount()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_AcceptCount();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getAddress <em>Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Address</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getAddress()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Address();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getAdhocConfig <em>Adhoc Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Adhoc Config</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getAdhocConfig()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_AdhocConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getAdhocConfigs <em>Adhoc Configs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Adhoc Configs</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getAdhocConfigs()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_AdhocConfigs();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getAgent <em>Agent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Agent</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getAgent()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Agent();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getAgentClasses <em>Agent Classes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Agent Classes</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getAgentClasses()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_AgentClasses();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getAgents <em>Agents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Agents</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getAgents()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Agents();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getAlertConfig <em>Alert Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Alert Config</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getAlertConfig()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_AlertConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getAlertConfigSet <em>Alert Config Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Alert Config Set</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getAlertConfigSet()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_AlertConfigSet();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getAppend <em>Append</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Append</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getAppend()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Append();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getArg <em>Arg</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Arg</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getArg()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Arg();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getAuthor <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Author</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getAuthor()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Author();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getBackingStore <em>Backing Store</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Backing Store</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getBackingStore()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_BackingStore();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getBackupCopies <em>Backup Copies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Backup Copies</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getBackupCopies()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_BackupCopies();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getBusinessworks <em>Businessworks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Businessworks</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getBusinessworks()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Businessworks();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getCache <em>Cache</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cache</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getCache()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Cache();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getCacheAgentClass <em>Cache Agent Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cache Agent Class</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getCacheAgentClass()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_CacheAgentClass();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getCacheAgentQuorum <em>Cache Agent Quorum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cache Agent Quorum</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getCacheAgentQuorum()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_CacheAgentQuorum();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getCacheAside <em>Cache Aside</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cache Aside</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getCacheAside()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_CacheAside();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getCacheLimited <em>Cache Limited</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cache Limited</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getCacheLimited()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_CacheLimited();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getCacheLoaderClass <em>Cache Loader Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cache Loader Class</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getCacheLoaderClass()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_CacheLoaderClass();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getCacheManager <em>Cache Manager</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cache Manager</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getCacheManager()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_CacheManager();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.CddRoot#getCacheMode <em>Cache Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cache Mode</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getCacheMode()
	 * @see #getCddRoot()
	 * @generated
	 */
	EAttribute getCddRoot_CacheMode();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getCacheStorageEnabled <em>Cache Storage Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cache Storage Enabled</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getCacheStorageEnabled()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_CacheStorageEnabled();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getCertificateKeyFile <em>Certificate Key File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Certificate Key File</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getCertificateKeyFile()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_CertificateKeyFile();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getChannel <em>Channel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Channel</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getChannel()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Channel();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getCheckForDuplicates <em>Check For Duplicates</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Check For Duplicates</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getCheckForDuplicates()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_CheckForDuplicates();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getCheckForVersion <em>Check For Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Check For Version</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getCheckForVersion()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_CheckForVersion();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getCheckInterval <em>Check Interval</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Check Interval</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getCheckInterval()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_CheckInterval();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getCheckpointInterval <em>Checkpoint Interval</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Checkpoint Interval</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getCheckpointInterval()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_CheckpointInterval();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getCheckpointOpsLimit <em>Checkpoint Ops Limit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Checkpoint Ops Limit</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getCheckpointOpsLimit()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_CheckpointOpsLimit();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getChildClusterMember <em>Child Cluster Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Child Cluster Member</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getChildClusterMember()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_ChildClusterMember();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getCipher <em>Cipher</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cipher</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getCipher()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Cipher();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getCiphers <em>Ciphers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Ciphers</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getCiphers()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Ciphers();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getClass_ <em>Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Class</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getClass_()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Class();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getCluster <em>Cluster</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cluster</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getCluster()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Cluster();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getClusterMember <em>Cluster Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cluster Member</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getClusterMember()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_ClusterMember();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Comment</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getComment()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Comment();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getConcurrentRtc <em>Concurrent Rtc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Concurrent Rtc</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getConcurrentRtc()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_ConcurrentRtc();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getCondition()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Condition();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getConnectionLinger <em>Connection Linger</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Connection Linger</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getConnectionLinger()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_ConnectionLinger();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getConnectionTimeout <em>Connection Timeout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Connection Timeout</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getConnectionTimeout()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_ConnectionTimeout();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getConstant <em>Constant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Constant</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getConstant()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Constant();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getController <em>Controller</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Controller</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getController()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Controller();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getDaemon <em>Daemon</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Daemon</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getDaemon()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Daemon();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getDashboardAgentClass <em>Dashboard Agent Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Dashboard Agent Class</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getDashboardAgentClass()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_DashboardAgentClass();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getDataStorePath <em>Data Store Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Data Store Path</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getDataStorePath()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_DataStorePath();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getDate <em>Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Date</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getDate()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Date();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getDbConcepts <em>Db Concepts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Db Concepts</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getDbConcepts()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_DbConcepts();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getDbDir <em>Db Dir</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Db Dir</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getDbDir()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_DbDir();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getDbUris <em>Db Uris</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Db Uris</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getDbUris()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_DbUris();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.CddRoot#getDefaultMode <em>Default Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Mode</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getDefaultMode()
	 * @see #getCddRoot()
	 * @generated
	 */
	EAttribute getCddRoot_DefaultMode();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getDeleteRetracted <em>Delete Retracted</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Delete Retracted</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getDeleteRetracted()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_DeleteRetracted();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getDestination <em>Destination</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Destination</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getDestination()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Destination();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getDestinationGroups <em>Destination Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Destination Groups</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getDestinationGroups()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_DestinationGroups();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getDestinations <em>Destinations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Destinations</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getDestinations()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Destinations();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getDir <em>Dir</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Dir</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getDir()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Dir();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getDiscoveryUrl <em>Discovery Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Discovery Url</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getDiscoveryUrl()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_DiscoveryUrl();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getDocumentPage <em>Document Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Document Page</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getDocumentPage()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_DocumentPage();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getDocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Document Root</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getDocumentRoot()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_DocumentRoot();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getDomainName <em>Domain Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Domain Name</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getDomainName()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_DomainName();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getDomainObject <em>Domain Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Domain Object</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getDomainObject()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_DomainObject();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getDomainObjects <em>Domain Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Domain Objects</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getDomainObjects()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_DomainObjects();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getEnabled <em>Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Enabled</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getEnabled()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Enabled();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getEnableTracking <em>Enable Tracking</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Enable Tracking</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getEnableTracking()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_EnableTracking();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getEncoding <em>Encoding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Encoding</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getEncoding()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Encoding();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getEncryption <em>Encryption</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Encryption</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getEncryption()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Encryption();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getEnforcePools <em>Enforce Pools</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Enforce Pools</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getEnforcePools()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_EnforcePools();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getEntityCacheSize <em>Entity Cache Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Entity Cache Size</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getEntityCacheSize()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_EntityCacheSize();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getEviction <em>Eviction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Eviction</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getEviction()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Eviction();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getEvictOnUpdate <em>Evict On Update</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Evict On Update</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getEvictOnUpdate()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_EvictOnUpdate();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getExplicitTuple <em>Explicit Tuple</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Explicit Tuple</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getExplicitTuple()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_ExplicitTuple();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getFiles <em>Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Files</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getFiles()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Files();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getFunctionGroups <em>Function Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Function Groups</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getFunctionGroups()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_FunctionGroups();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getFunctions <em>Functions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Functions</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getFunctions()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Functions();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getGetProperty <em>Get Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Get Property</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getGetProperty()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_GetProperty();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getHotDeploy <em>Hot Deploy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Hot Deploy</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getHotDeploy()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_HotDeploy();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getHttp <em>Http</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Http</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getHttp()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Http();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getIdentityPassword <em>Identity Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Identity Password</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getIdentityPassword()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_IdentityPassword();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getInactivityTimeout <em>Inactivity Timeout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Inactivity Timeout</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getInactivityTimeout()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_InactivityTimeout();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Index</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getIndex()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Index();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getIndexes <em>Indexes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Indexes</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getIndexes()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Indexes();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getInferenceAgentClass <em>Inference Agent Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Inference Agent Class</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getInferenceAgentClass()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_InferenceAgentClass();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getInferenceEngine <em>Inference Engine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Inference Engine</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getInferenceEngine()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_InferenceEngine();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getInitialSize <em>Initial Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Initial Size</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getInitialSize()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_InitialSize();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getJobManager <em>Job Manager</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Job Manager</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getJobManager()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_JobManager();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getJobPoolQueueSize <em>Job Pool Queue Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Job Pool Queue Size</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getJobPoolQueueSize()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_JobPoolQueueSize();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getJobPoolThreadCount <em>Job Pool Thread Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Job Pool Thread Count</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getJobPoolThreadCount()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_JobPoolThreadCount();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Key</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getKey()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Key();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getKeyManagerAlgorithm <em>Key Manager Algorithm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Key Manager Algorithm</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getKeyManagerAlgorithm()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_KeyManagerAlgorithm();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getLineLayout <em>Line Layout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Line Layout</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getLineLayout()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_LineLayout();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getListenUrl <em>Listen Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Listen Url</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getListenUrl()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_ListenUrl();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getLoad <em>Load</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Load</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getLoad()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Load();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getLoadBalancerConfigs <em>Load Balancer Configs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Load Balancer Configs</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getLoadBalancerConfigs()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_LoadBalancerConfigs();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getLocalCache <em>Local Cache</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Local Cache</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getLocalCache()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_LocalCache();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getLockTimeout <em>Lock Timeout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Lock Timeout</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getLockTimeout()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_LockTimeout();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getLogConfig <em>Log Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Log Config</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getLogConfig()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_LogConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getLogConfigs <em>Log Configs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Log Configs</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getLogConfigs()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_LogConfigs();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getMaxActive <em>Max Active</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Max Active</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getMaxActive()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_MaxActive();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getMaxNumber <em>Max Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Max Number</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getMaxNumber()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_MaxNumber();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getMaxProcessors <em>Max Processors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Max Processors</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getMaxProcessors()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_MaxProcessors();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getMaxSize <em>Max Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Max Size</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getMaxSize()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_MaxSize();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getMaxTime <em>Max Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Max Time</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getMaxTime()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_MaxTime();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getMemoryManager <em>Memory Manager</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Memory Manager</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getMemoryManager()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_MemoryManager();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.CddRoot#getMessageEncoding <em>Message Encoding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message Encoding</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getMessageEncoding()
	 * @see #getCddRoot()
	 * @generated
	 */
	EAttribute getCddRoot_MessageEncoding();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getMinSize <em>Min Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Min Size</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getMinSize()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_MinSize();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getMmAction <em>Mm Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mm Action</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getMmAction()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_MmAction();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getMmActionConfig <em>Mm Action Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mm Action Config</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getMmActionConfig()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_MmActionConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getMmActionConfigSet <em>Mm Action Config Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mm Action Config Set</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getMmActionConfigSet()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_MmActionConfigSet();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getMmAgentClass <em>Mm Agent Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mm Agent Class</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getMmAgentClass()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_MmAgentClass();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getMmAlert <em>Mm Alert</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mm Alert</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getMmAlert()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_MmAlert();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getMmExecuteCommand <em>Mm Execute Command</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mm Execute Command</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getMmExecuteCommand()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_MmExecuteCommand();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getMmHealthLevel <em>Mm Health Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mm Health Level</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getMmHealthLevel()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_MmHealthLevel();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getMmSendEmail <em>Mm Send Email</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mm Send Email</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getMmSendEmail()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_MmSendEmail();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getMmTriggerCondition <em>Mm Trigger Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mm Trigger Condition</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getMmTriggerCondition()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_MmTriggerCondition();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.CddRoot#getMode <em>Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mode</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getMode()
	 * @see #getCddRoot()
	 * @generated
	 */
	EAttribute getCddRoot_Mode();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Name</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getName()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Name();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getNotification <em>Notification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Notification</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getNotification()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Notification();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getObjectManagement <em>Object Management</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Object Management</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getObjectManagement()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_ObjectManagement();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getObjectTable <em>Object Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Object Table</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getObjectTable()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_ObjectTable();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getPairConfig <em>Pair Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pair Config</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getPairConfig()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_PairConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getPairConfigs <em>Pair Configs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pair Configs</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getPairConfigs()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_PairConfigs();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getParallelRecovery <em>Parallel Recovery</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Parallel Recovery</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getParallelRecovery()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_ParallelRecovery();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getPersistenceOption <em>Persistence Option</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Persistence Option</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getPersistenceOption()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_PersistenceOption();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getPersistencePolicy <em>Persistence Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Persistence Policy</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getPersistencePolicy()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_PersistencePolicy();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getPolicyFile <em>Policy File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Policy File</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getPolicyFile()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_PolicyFile();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getPort <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Port</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getPort()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Port();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getPreLoadCaches <em>Pre Load Caches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Load Caches</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getPreLoadCaches()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_PreLoadCaches();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getPreLoadEnabled <em>Pre Load Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Load Enabled</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getPreLoadEnabled()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_PreLoadEnabled();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getPreLoadFetchSize <em>Pre Load Fetch Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Load Fetch Size</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getPreLoadFetchSize()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_PreLoadFetchSize();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getPreLoadHandles <em>Pre Load Handles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Load Handles</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getPreLoadHandles()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_PreLoadHandles();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.CddRoot#getPreProcessor <em>Pre Processor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pre Processor</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getPreProcessor()
	 * @see #getCddRoot()
	 * @generated
	 */
	EAttribute getCddRoot_PreProcessor();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getPrimaryConnection <em>Primary Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Primary Connection</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getPrimaryConnection()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_PrimaryConnection();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getPriority <em>Priority</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Priority</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getPriority()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Priority();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getProcess <em>Process</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Process</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getProcess()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Process();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getProcessAgentClass <em>Process Agent Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Process Agent Class</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getProcessAgentClass()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_ProcessAgentClass();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getProcessEngine <em>Process Engine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Process Engine</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getProcessEngine()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_ProcessEngine();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getProcesses <em>Processes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Processes</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getProcesses()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Processes();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getProcessGroups <em>Process Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Process Groups</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getProcessGroups()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_ProcessGroups();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getProcessingUnit <em>Processing Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Processing Unit</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getProcessingUnit()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_ProcessingUnit();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getProcessingUnits <em>Processing Units</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Processing Units</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getProcessingUnits()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_ProcessingUnits();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getProjection <em>Projection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Projection</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getProjection()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Projection();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Property</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getProperty()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Property();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getPropertyCacheSize <em>Property Cache Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Property Cache Size</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getPropertyCacheSize()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_PropertyCacheSize();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getPropertyCheckInterval <em>Property Check Interval</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Property Check Interval</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getPropertyCheckInterval()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_PropertyCheckInterval();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getPropertyGroup <em>Property Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Property Group</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getPropertyGroup()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_PropertyGroup();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getProtocol <em>Protocol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Protocol</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getProtocol()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Protocol();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getProtocols <em>Protocols</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Protocols</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getProtocols()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Protocols();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getProtocolTimeout <em>Protocol Timeout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Protocol Timeout</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getProtocolTimeout()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_ProtocolTimeout();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getProvider <em>Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Provider</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getProvider()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Provider();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getQueryAgentClass <em>Query Agent Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Query Agent Class</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getQueryAgentClass()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_QueryAgentClass();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getQueueSize <em>Queue Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Queue Size</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getQueueSize()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_QueueSize();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getReadTimeout <em>Read Timeout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Read Timeout</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getReadTimeout()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_ReadTimeout();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getReceiver <em>Receiver</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Receiver</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getReceiver()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Receiver();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getRemoteListenUrl <em>Remote Listen Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Remote Listen Url</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getRemoteListenUrl()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_RemoteListenUrl();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getRequester <em>Requester</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Requester</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getRequester()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Requester();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getRetryCount <em>Retry Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Retry Count</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getRetryCount()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_RetryCount();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getReverseReferences <em>Reverse References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Reverse References</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getReverseReferences()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_ReverseReferences();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getRevision <em>Revision</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Revision</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getRevision()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Revision();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getRole <em>Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Role</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getRole()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Role();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getRoles <em>Roles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Roles</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getRoles()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Roles();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getRouter <em>Router</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Router</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getRouter()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Router();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getRule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rule</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getRule()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Rule();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getRuleConfig <em>Rule Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rule Config</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getRuleConfig()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_RuleConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rules</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getRules()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Rules();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getRulesets <em>Rulesets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rulesets</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getRulesets()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Rulesets();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getSecondaryConnection <em>Secondary Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Secondary Connection</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getSecondaryConnection()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_SecondaryConnection();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getSecurityConfig <em>Security Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Security Config</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getSecurityConfig()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_SecurityConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getService <em>Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Service</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getService()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Service();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getSetProperty <em>Set Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Set Property</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getSetProperty()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_SetProperty();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getSharedAll <em>Shared All</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Shared All</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getSharedAll()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_SharedAll();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getSharedQueue <em>Shared Queue</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Shared Queue</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getSharedQueue()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_SharedQueue();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getShoutdownWait <em>Shoutdown Wait</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Shoutdown Wait</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getShoutdownWait()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_ShoutdownWait();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getShutdown <em>Shutdown</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Shutdown</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getShutdown()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Shutdown();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getSize <em>Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Size</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getSize()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Size();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getSkipRecovery <em>Skip Recovery</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Skip Recovery</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getSkipRecovery()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_SkipRecovery();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getSocketOutputBufferSize <em>Socket Output Buffer Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Socket Output Buffer Size</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getSocketOutputBufferSize()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_SocketOutputBufferSize();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getSsl <em>Ssl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Ssl</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getSsl()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Ssl();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getStaleConnectionCheck <em>Stale Connection Check</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Stale Connection Check</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getStaleConnectionCheck()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_StaleConnectionCheck();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getStartup <em>Startup</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Startup</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getStartup()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Startup();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getStrategy <em>Strategy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Strategy</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getStrategy()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Strategy();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getSubject <em>Subject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Subject</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getSubject()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Subject();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getSubscribe <em>Subscribe</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Subscribe</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getSubscribe()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Subscribe();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getSysErrRedirect <em>Sys Err Redirect</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Sys Err Redirect</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getSysErrRedirect()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_SysErrRedirect();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getSysOutRedirect <em>Sys Out Redirect</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Sys Out Redirect</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getSysOutRedirect()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_SysOutRedirect();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getTableName <em>Table Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Table Name</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getTableName()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_TableName();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getTcpNoDelay <em>Tcp No Delay</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Tcp No Delay</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getTcpNoDelay()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_TcpNoDelay();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getTerminal <em>Terminal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Terminal</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getTerminal()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Terminal();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.CddRoot#getThreadAffinityRuleFunction <em>Thread Affinity Rule Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Thread Affinity Rule Function</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getThreadAffinityRuleFunction()
	 * @see #getCddRoot()
	 * @generated
	 */
	EAttribute getCddRoot_ThreadAffinityRuleFunction();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getThreadCount <em>Thread Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Thread Count</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getThreadCount()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_ThreadCount();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.CddRoot#getThreadingModel <em>Threading Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Threading Model</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getThreadingModel()
	 * @see #getCddRoot()
	 * @generated
	 */
	EAttribute getCddRoot_ThreadingModel();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getTokenFile <em>Token File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Token File</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getTokenFile()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_TokenFile();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getTrustManagerAlgorithm <em>Trust Manager Algorithm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Trust Manager Algorithm</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getTrustManagerAlgorithm()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_TrustManagerAlgorithm();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getTtl <em>Ttl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Ttl</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getTtl()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Ttl();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Type</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getType()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Type();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.CddRoot#getUri <em>Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Uri</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getUri()
	 * @see #getCddRoot()
	 * @generated
	 */
	EAttribute getCddRoot_Uri();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getUserName <em>User Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>User Name</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getUserName()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_UserName();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getUserPassword <em>User Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>User Password</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getUserPassword()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_UserPassword();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.CddRoot#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getVersion()
	 * @see #getCddRoot()
	 * @generated
	 */
	EAttribute getCddRoot_Version();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getWaitTimeout <em>Wait Timeout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Wait Timeout</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getWaitTimeout()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_WaitTimeout();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getWorkers <em>Workers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Workers</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getWorkers()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Workers();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getWorkerthreadsCount <em>Workerthreads Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Workerthreads Count</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getWorkerthreadsCount()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_WorkerthreadsCount();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getWriteTimeout <em>Write Timeout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Write Timeout</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getWriteTimeout()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_WriteTimeout();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getEntity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Entity</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getEntity()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Entity();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getFilter <em>Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Filter</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getFilter()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Filter();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getEntitySet <em>Entity Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Entity Set</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getEntitySet()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_EntitySet();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getEnableTableTrimming <em>Enable Table Trimming</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Enable Table Trimming</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getEnableTableTrimming()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_EnableTableTrimming();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getTrimmingField <em>Trimming Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Trimming Field</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getTrimmingField()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_TrimmingField();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getTrimmingRule <em>Trimming Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Trimming Rule</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getTrimmingRule()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_TrimmingRule();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getGenerateLVFiles <em>Generate LV Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Generate LV Files</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getGenerateLVFiles()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_GenerateLVFiles();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getOutputPath <em>Output Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Output Path</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getOutputPath()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_OutputPath();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getLdmConnection <em>Ldm Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Ldm Connection</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getLdmConnection()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_LdmConnection();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getLdmUrl <em>Ldm Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Ldm Url</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getLdmUrl()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_LdmUrl();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getPublisher <em>Publisher</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Publisher</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getPublisher()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_Publisher();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getPublisherQueueSize <em>Publisher Queue Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Publisher Queue Size</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getPublisherQueueSize()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_PublisherQueueSize();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getPublisherThreadCount <em>Publisher Thread Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Publisher Thread Count</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getPublisherThreadCount()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_PublisherThreadCount();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getLiveviewAgentClass <em>Liveview Agent Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Liveview Agent Class</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getLiveviewAgentClass()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_LiveviewAgentClass();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getCompositeIndexes <em>Composite Indexes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Composite Indexes</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getCompositeIndexes()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_CompositeIndexes();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CddRoot#getCompositeIndex <em>Composite Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Composite Index</em>'.
	 * @see com.tibco.be.util.config.cdd.CddRoot#getCompositeIndex()
	 * @see #getCddRoot()
	 * @generated
	 */
	EReference getCddRoot_CompositeIndex();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.ChildClusterMemberConfig <em>Child Cluster Member Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Child Cluster Member Config</em>'.
	 * @see com.tibco.be.util.config.cdd.ChildClusterMemberConfig
	 * @generated
	 */
	EClass getChildClusterMemberConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.ChildClusterMemberConfig#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Property</em>'.
	 * @see com.tibco.be.util.config.cdd.ChildClusterMemberConfig#getProperty()
	 * @see #getChildClusterMemberConfig()
	 * @generated
	 */
	EReference getChildClusterMemberConfig_Property();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.ChildClusterMemberConfig#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see com.tibco.be.util.config.cdd.ChildClusterMemberConfig#getPath()
	 * @see #getChildClusterMemberConfig()
	 * @generated
	 */
	EAttribute getChildClusterMemberConfig_Path();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.ChildClusterMemberConfig#getTolerance <em>Tolerance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tolerance</em>'.
	 * @see com.tibco.be.util.config.cdd.ChildClusterMemberConfig#getTolerance()
	 * @see #getChildClusterMemberConfig()
	 * @generated
	 */
	EAttribute getChildClusterMemberConfig_Tolerance();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.CiphersConfig <em>Ciphers Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ciphers Config</em>'.
	 * @see com.tibco.be.util.config.cdd.CiphersConfig
	 * @generated
	 */
	EClass getCiphersConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.CiphersConfig#getCipher <em>Cipher</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Cipher</em>'.
	 * @see com.tibco.be.util.config.cdd.CiphersConfig#getCipher()
	 * @see #getCiphersConfig()
	 * @generated
	 */
	EReference getCiphersConfig_Cipher();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.ClusterConfig <em>Cluster Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cluster Config</em>'.
	 * @see com.tibco.be.util.config.cdd.ClusterConfig
	 * @generated
	 */
	EClass getClusterConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ClusterConfig#getAgentClasses <em>Agent Classes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Agent Classes</em>'.
	 * @see com.tibco.be.util.config.cdd.ClusterConfig#getAgentClasses()
	 * @see #getClusterConfig()
	 * @generated
	 */
	EReference getClusterConfig_AgentClasses();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ClusterConfig#getDestinationGroups <em>Destination Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Destination Groups</em>'.
	 * @see com.tibco.be.util.config.cdd.ClusterConfig#getDestinationGroups()
	 * @see #getClusterConfig()
	 * @generated
	 */
	EReference getClusterConfig_DestinationGroups();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ClusterConfig#getFunctionGroups <em>Function Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Function Groups</em>'.
	 * @see com.tibco.be.util.config.cdd.ClusterConfig#getFunctionGroups()
	 * @see #getClusterConfig()
	 * @generated
	 */
	EReference getClusterConfig_FunctionGroups();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ClusterConfig#getLoadBalancerConfigs <em>Load Balancer Configs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Load Balancer Configs</em>'.
	 * @see com.tibco.be.util.config.cdd.ClusterConfig#getLoadBalancerConfigs()
	 * @see #getClusterConfig()
	 * @generated
	 */
	EReference getClusterConfig_LoadBalancerConfigs();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ClusterConfig#getLogConfigs <em>Log Configs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Log Configs</em>'.
	 * @see com.tibco.be.util.config.cdd.ClusterConfig#getLogConfigs()
	 * @see #getClusterConfig()
	 * @generated
	 */
	EReference getClusterConfig_LogConfigs();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.ClusterConfig#getMessageEncoding <em>Message Encoding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message Encoding</em>'.
	 * @see com.tibco.be.util.config.cdd.ClusterConfig#getMessageEncoding()
	 * @see #getClusterConfig()
	 * @generated
	 */
	EAttribute getClusterConfig_MessageEncoding();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ClusterConfig#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Name</em>'.
	 * @see com.tibco.be.util.config.cdd.ClusterConfig#getName()
	 * @see #getClusterConfig()
	 * @generated
	 */
	EReference getClusterConfig_Name();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ClusterConfig#getObjectManagement <em>Object Management</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Object Management</em>'.
	 * @see com.tibco.be.util.config.cdd.ClusterConfig#getObjectManagement()
	 * @see #getClusterConfig()
	 * @generated
	 */
	EReference getClusterConfig_ObjectManagement();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ClusterConfig#getProcessGroups <em>Process Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Process Groups</em>'.
	 * @see com.tibco.be.util.config.cdd.ClusterConfig#getProcessGroups()
	 * @see #getClusterConfig()
	 * @generated
	 */
	EReference getClusterConfig_ProcessGroups();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ClusterConfig#getProcessingUnits <em>Processing Units</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Processing Units</em>'.
	 * @see com.tibco.be.util.config.cdd.ClusterConfig#getProcessingUnits()
	 * @see #getClusterConfig()
	 * @generated
	 */
	EReference getClusterConfig_ProcessingUnits();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ClusterConfig#getPropertyGroup <em>Property Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Property Group</em>'.
	 * @see com.tibco.be.util.config.cdd.ClusterConfig#getPropertyGroup()
	 * @see #getClusterConfig()
	 * @generated
	 */
	EReference getClusterConfig_PropertyGroup();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ClusterConfig#getRevision <em>Revision</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Revision</em>'.
	 * @see com.tibco.be.util.config.cdd.ClusterConfig#getRevision()
	 * @see #getClusterConfig()
	 * @generated
	 */
	EReference getClusterConfig_Revision();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ClusterConfig#getRulesets <em>Rulesets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rulesets</em>'.
	 * @see com.tibco.be.util.config.cdd.ClusterConfig#getRulesets()
	 * @see #getClusterConfig()
	 * @generated
	 */
	EReference getClusterConfig_Rulesets();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.ClusterMemberConfig <em>Cluster Member Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cluster Member Config</em>'.
	 * @see com.tibco.be.util.config.cdd.ClusterMemberConfig
	 * @generated
	 */
	EClass getClusterMemberConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.ClusterMemberConfig#getSetProperty <em>Set Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Set Property</em>'.
	 * @see com.tibco.be.util.config.cdd.ClusterMemberConfig#getSetProperty()
	 * @see #getClusterMemberConfig()
	 * @generated
	 */
	EReference getClusterMemberConfig_SetProperty();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.ClusterMemberConfig#getMemberName <em>Member Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Member Name</em>'.
	 * @see com.tibco.be.util.config.cdd.ClusterMemberConfig#getMemberName()
	 * @see #getClusterMemberConfig()
	 * @generated
	 */
	EAttribute getClusterMemberConfig_MemberName();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.ClusterMemberConfig#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see com.tibco.be.util.config.cdd.ClusterMemberConfig#getPath()
	 * @see #getClusterMemberConfig()
	 * @generated
	 */
	EAttribute getClusterMemberConfig_Path();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.ConditionConfig <em>Condition Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Condition Config</em>'.
	 * @see com.tibco.be.util.config.cdd.ConditionConfig
	 * @generated
	 */
	EClass getConditionConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ConditionConfig#getGetProperty <em>Get Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Get Property</em>'.
	 * @see com.tibco.be.util.config.cdd.ConditionConfig#getGetProperty()
	 * @see #getConditionConfig()
	 * @generated
	 */
	EReference getConditionConfig_GetProperty();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.ConnectionConfig <em>Connection Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connection Config</em>'.
	 * @see com.tibco.be.util.config.cdd.ConnectionConfig
	 * @generated
	 */
	EClass getConnectionConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ConnectionConfig#getInitialSize <em>Initial Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Initial Size</em>'.
	 * @see com.tibco.be.util.config.cdd.ConnectionConfig#getInitialSize()
	 * @see #getConnectionConfig()
	 * @generated
	 */
	EReference getConnectionConfig_InitialSize();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ConnectionConfig#getMinSize <em>Min Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Min Size</em>'.
	 * @see com.tibco.be.util.config.cdd.ConnectionConfig#getMinSize()
	 * @see #getConnectionConfig()
	 * @generated
	 */
	EReference getConnectionConfig_MinSize();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ConnectionConfig#getMaxSize <em>Max Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Max Size</em>'.
	 * @see com.tibco.be.util.config.cdd.ConnectionConfig#getMaxSize()
	 * @see #getConnectionConfig()
	 * @generated
	 */
	EReference getConnectionConfig_MaxSize();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.ConnectionConfig#getUri <em>Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Uri</em>'.
	 * @see com.tibco.be.util.config.cdd.ConnectionConfig#getUri()
	 * @see #getConnectionConfig()
	 * @generated
	 */
	EAttribute getConnectionConfig_Uri();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.DashboardAgentClassConfig <em>Dashboard Agent Class Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dashboard Agent Class Config</em>'.
	 * @see com.tibco.be.util.config.cdd.DashboardAgentClassConfig
	 * @generated
	 */
	EClass getDashboardAgentClassConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DashboardAgentClassConfig#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rules</em>'.
	 * @see com.tibco.be.util.config.cdd.DashboardAgentClassConfig#getRules()
	 * @see #getDashboardAgentClassConfig()
	 * @generated
	 */
	EReference getDashboardAgentClassConfig_Rules();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DashboardAgentClassConfig#getDestinations <em>Destinations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Destinations</em>'.
	 * @see com.tibco.be.util.config.cdd.DashboardAgentClassConfig#getDestinations()
	 * @see #getDashboardAgentClassConfig()
	 * @generated
	 */
	EReference getDashboardAgentClassConfig_Destinations();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DashboardAgentClassConfig#getStartup <em>Startup</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Startup</em>'.
	 * @see com.tibco.be.util.config.cdd.DashboardAgentClassConfig#getStartup()
	 * @see #getDashboardAgentClassConfig()
	 * @generated
	 */
	EReference getDashboardAgentClassConfig_Startup();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DashboardAgentClassConfig#getShutdown <em>Shutdown</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Shutdown</em>'.
	 * @see com.tibco.be.util.config.cdd.DashboardAgentClassConfig#getShutdown()
	 * @see #getDashboardAgentClassConfig()
	 * @generated
	 */
	EReference getDashboardAgentClassConfig_Shutdown();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DashboardAgentClassConfig#getPropertyGroup <em>Property Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Property Group</em>'.
	 * @see com.tibco.be.util.config.cdd.DashboardAgentClassConfig#getPropertyGroup()
	 * @see #getDashboardAgentClassConfig()
	 * @generated
	 */
	EReference getDashboardAgentClassConfig_PropertyGroup();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.DbConceptsConfig <em>Db Concepts Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Db Concepts Config</em>'.
	 * @see com.tibco.be.util.config.cdd.DbConceptsConfig
	 * @generated
	 */
	EClass getDbConceptsConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DbConceptsConfig#getCheckInterval <em>Check Interval</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Check Interval</em>'.
	 * @see com.tibco.be.util.config.cdd.DbConceptsConfig#getCheckInterval()
	 * @see #getDbConceptsConfig()
	 * @generated
	 */
	EReference getDbConceptsConfig_CheckInterval();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DbConceptsConfig#getDbUris <em>Db Uris</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Db Uris</em>'.
	 * @see com.tibco.be.util.config.cdd.DbConceptsConfig#getDbUris()
	 * @see #getDbConceptsConfig()
	 * @generated
	 */
	EReference getDbConceptsConfig_DbUris();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DbConceptsConfig#getInactivityTimeout <em>Inactivity Timeout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Inactivity Timeout</em>'.
	 * @see com.tibco.be.util.config.cdd.DbConceptsConfig#getInactivityTimeout()
	 * @see #getDbConceptsConfig()
	 * @generated
	 */
	EReference getDbConceptsConfig_InactivityTimeout();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DbConceptsConfig#getInitialSize <em>Initial Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Initial Size</em>'.
	 * @see com.tibco.be.util.config.cdd.DbConceptsConfig#getInitialSize()
	 * @see #getDbConceptsConfig()
	 * @generated
	 */
	EReference getDbConceptsConfig_InitialSize();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DbConceptsConfig#getMaxSize <em>Max Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Max Size</em>'.
	 * @see com.tibco.be.util.config.cdd.DbConceptsConfig#getMaxSize()
	 * @see #getDbConceptsConfig()
	 * @generated
	 */
	EReference getDbConceptsConfig_MaxSize();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DbConceptsConfig#getMinSize <em>Min Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Min Size</em>'.
	 * @see com.tibco.be.util.config.cdd.DbConceptsConfig#getMinSize()
	 * @see #getDbConceptsConfig()
	 * @generated
	 */
	EReference getDbConceptsConfig_MinSize();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DbConceptsConfig#getPropertyCheckInterval <em>Property Check Interval</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Property Check Interval</em>'.
	 * @see com.tibco.be.util.config.cdd.DbConceptsConfig#getPropertyCheckInterval()
	 * @see #getDbConceptsConfig()
	 * @generated
	 */
	EReference getDbConceptsConfig_PropertyCheckInterval();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DbConceptsConfig#getRetryCount <em>Retry Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Retry Count</em>'.
	 * @see com.tibco.be.util.config.cdd.DbConceptsConfig#getRetryCount()
	 * @see #getDbConceptsConfig()
	 * @generated
	 */
	EReference getDbConceptsConfig_RetryCount();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DbConceptsConfig#getWaitTimeout <em>Wait Timeout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Wait Timeout</em>'.
	 * @see com.tibco.be.util.config.cdd.DbConceptsConfig#getWaitTimeout()
	 * @see #getDbConceptsConfig()
	 * @generated
	 */
	EReference getDbConceptsConfig_WaitTimeout();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.DestinationConfig <em>Destination Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Destination Config</em>'.
	 * @see com.tibco.be.util.config.cdd.DestinationConfig
	 * @generated
	 */
	EClass getDestinationConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.DestinationConfig#getUri <em>Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Uri</em>'.
	 * @see com.tibco.be.util.config.cdd.DestinationConfig#getUri()
	 * @see #getDestinationConfig()
	 * @generated
	 */
	EAttribute getDestinationConfig_Uri();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.DestinationConfig#getPreProcessor <em>Pre Processor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pre Processor</em>'.
	 * @see com.tibco.be.util.config.cdd.DestinationConfig#getPreProcessor()
	 * @see #getDestinationConfig()
	 * @generated
	 */
	EAttribute getDestinationConfig_PreProcessor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.DestinationConfig#getThreadingModel <em>Threading Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Threading Model</em>'.
	 * @see com.tibco.be.util.config.cdd.DestinationConfig#getThreadingModel()
	 * @see #getDestinationConfig()
	 * @generated
	 */
	EAttribute getDestinationConfig_ThreadingModel();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DestinationConfig#getThreadCount <em>Thread Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Thread Count</em>'.
	 * @see com.tibco.be.util.config.cdd.DestinationConfig#getThreadCount()
	 * @see #getDestinationConfig()
	 * @generated
	 */
	EReference getDestinationConfig_ThreadCount();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DestinationConfig#getQueueSize <em>Queue Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Queue Size</em>'.
	 * @see com.tibco.be.util.config.cdd.DestinationConfig#getQueueSize()
	 * @see #getDestinationConfig()
	 * @generated
	 */
	EReference getDestinationConfig_QueueSize();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.DestinationConfig#getThreadAffinityRuleFunction <em>Thread Affinity Rule Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Thread Affinity Rule Function</em>'.
	 * @see com.tibco.be.util.config.cdd.DestinationConfig#getThreadAffinityRuleFunction()
	 * @see #getDestinationConfig()
	 * @generated
	 */
	EAttribute getDestinationConfig_ThreadAffinityRuleFunction();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.DestinationGroupsConfig <em>Destination Groups Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Destination Groups Config</em>'.
	 * @see com.tibco.be.util.config.cdd.DestinationGroupsConfig
	 * @generated
	 */
	EClass getDestinationGroupsConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.DestinationGroupsConfig#getDestinations <em>Destinations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Destinations</em>'.
	 * @see com.tibco.be.util.config.cdd.DestinationGroupsConfig#getDestinations()
	 * @see #getDestinationGroupsConfig()
	 * @generated
	 */
	EReference getDestinationGroupsConfig_Destinations();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.DestinationsConfig <em>Destinations Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Destinations Config</em>'.
	 * @see com.tibco.be.util.config.cdd.DestinationsConfig
	 * @generated
	 */
	EClass getDestinationsConfig();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.util.config.cdd.DestinationsConfig#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.tibco.be.util.config.cdd.DestinationsConfig#getGroup()
	 * @see #getDestinationsConfig()
	 * @generated
	 */
	EAttribute getDestinationsConfig_Group();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.be.util.config.cdd.DestinationsConfig#getRef <em>Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ref</em>'.
	 * @see com.tibco.be.util.config.cdd.DestinationsConfig#getRef()
	 * @see #getDestinationsConfig()
	 * @generated
	 */
	EReference getDestinationsConfig_Ref();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.DestinationsConfig#getDestination <em>Destination</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Destination</em>'.
	 * @see com.tibco.be.util.config.cdd.DestinationsConfig#getDestination()
	 * @see #getDestinationsConfig()
	 * @generated
	 */
	EReference getDestinationsConfig_Destination();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.DomainObjectConfig <em>Domain Object Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Domain Object Config</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectConfig
	 * @generated
	 */
	EClass getDomainObjectConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getBackingStore <em>Backing Store</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Backing Store</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectConfig#getBackingStore()
	 * @see #getDomainObjectConfig()
	 * @generated
	 */
	EReference getDomainObjectConfig_BackingStore();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getCacheLimited <em>Cache Limited</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cache Limited</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectConfig#getCacheLimited()
	 * @see #getDomainObjectConfig()
	 * @generated
	 */
	EReference getDomainObjectConfig_CacheLimited();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getCheckForVersion <em>Check For Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Check For Version</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectConfig#getCheckForVersion()
	 * @see #getDomainObjectConfig()
	 * @generated
	 */
	EReference getDomainObjectConfig_CheckForVersion();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getConstant <em>Constant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Constant</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectConfig#getConstant()
	 * @see #getDomainObjectConfig()
	 * @generated
	 */
	EReference getDomainObjectConfig_Constant();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getEnableTracking <em>Enable Tracking</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Enable Tracking</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectConfig#getEnableTracking()
	 * @see #getDomainObjectConfig()
	 * @generated
	 */
	EReference getDomainObjectConfig_EnableTracking();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getEncryption <em>Encryption</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Encryption</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectConfig#getEncryption()
	 * @see #getDomainObjectConfig()
	 * @generated
	 */
	EReference getDomainObjectConfig_Encryption();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getEvictOnUpdate <em>Evict On Update</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Evict On Update</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectConfig#getEvictOnUpdate()
	 * @see #getDomainObjectConfig()
	 * @generated
	 */
	EReference getDomainObjectConfig_EvictOnUpdate();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getIndexes <em>Indexes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Indexes</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectConfig#getIndexes()
	 * @see #getDomainObjectConfig()
	 * @generated
	 */
	EReference getDomainObjectConfig_Indexes();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getMode <em>Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mode</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectConfig#getMode()
	 * @see #getDomainObjectConfig()
	 * @generated
	 */
	EAttribute getDomainObjectConfig_Mode();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getPreLoadEnabled <em>Pre Load Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Load Enabled</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectConfig#getPreLoadEnabled()
	 * @see #getDomainObjectConfig()
	 * @generated
	 */
	EReference getDomainObjectConfig_PreLoadEnabled();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getPreLoadFetchSize <em>Pre Load Fetch Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Load Fetch Size</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectConfig#getPreLoadFetchSize()
	 * @see #getDomainObjectConfig()
	 * @generated
	 */
	EReference getDomainObjectConfig_PreLoadFetchSize();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getPreLoadHandles <em>Pre Load Handles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Load Handles</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectConfig#getPreLoadHandles()
	 * @see #getDomainObjectConfig()
	 * @generated
	 */
	EReference getDomainObjectConfig_PreLoadHandles();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getPreProcessor <em>Pre Processor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pre Processor</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectConfig#getPreProcessor()
	 * @see #getDomainObjectConfig()
	 * @generated
	 */
	EAttribute getDomainObjectConfig_PreProcessor();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getSubscribe <em>Subscribe</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Subscribe</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectConfig#getSubscribe()
	 * @see #getDomainObjectConfig()
	 * @generated
	 */
	EReference getDomainObjectConfig_Subscribe();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getUri <em>Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Uri</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectConfig#getUri()
	 * @see #getDomainObjectConfig()
	 * @generated
	 */
	EAttribute getDomainObjectConfig_Uri();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getConceptTTL <em>Concept TTL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Concept TTL</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectConfig#getConceptTTL()
	 * @see #getDomainObjectConfig()
	 * @generated
	 */
	EReference getDomainObjectConfig_ConceptTTL();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getCompositeIndexes <em>Composite Indexes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Composite Indexes</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectConfig#getCompositeIndexes()
	 * @see #getDomainObjectConfig()
	 * @generated
	 */
	EReference getDomainObjectConfig_CompositeIndexes();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.DomainObjectsConfig <em>Domain Objects Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Domain Objects Config</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectsConfig
	 * @generated
	 */
	EClass getDomainObjectsConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getDefaultMode <em>Default Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Mode</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectsConfig#getDefaultMode()
	 * @see #getDomainObjectsConfig()
	 * @generated
	 */
	EAttribute getDomainObjectsConfig_DefaultMode();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getCheckForVersion <em>Check For Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Check For Version</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectsConfig#getCheckForVersion()
	 * @see #getDomainObjectsConfig()
	 * @generated
	 */
	EReference getDomainObjectsConfig_CheckForVersion();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getConstant <em>Constant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Constant</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectsConfig#getConstant()
	 * @see #getDomainObjectsConfig()
	 * @generated
	 */
	EReference getDomainObjectsConfig_Constant();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getEnableTracking <em>Enable Tracking</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Enable Tracking</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectsConfig#getEnableTracking()
	 * @see #getDomainObjectsConfig()
	 * @generated
	 */
	EReference getDomainObjectsConfig_EnableTracking();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getEvictOnUpdate <em>Evict On Update</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Evict On Update</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectsConfig#getEvictOnUpdate()
	 * @see #getDomainObjectsConfig()
	 * @generated
	 */
	EReference getDomainObjectsConfig_EvictOnUpdate();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getCacheLimited <em>Cache Limited</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cache Limited</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectsConfig#getCacheLimited()
	 * @see #getDomainObjectsConfig()
	 * @generated
	 */
	EReference getDomainObjectsConfig_CacheLimited();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getSubscribe <em>Subscribe</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Subscribe</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectsConfig#getSubscribe()
	 * @see #getDomainObjectsConfig()
	 * @generated
	 */
	EReference getDomainObjectsConfig_Subscribe();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getPreLoadEnabled <em>Pre Load Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Load Enabled</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectsConfig#getPreLoadEnabled()
	 * @see #getDomainObjectsConfig()
	 * @generated
	 */
	EReference getDomainObjectsConfig_PreLoadEnabled();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getPreLoadFetchSize <em>Pre Load Fetch Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Load Fetch Size</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectsConfig#getPreLoadFetchSize()
	 * @see #getDomainObjectsConfig()
	 * @generated
	 */
	EReference getDomainObjectsConfig_PreLoadFetchSize();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getPreLoadHandles <em>Pre Load Handles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Load Handles</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectsConfig#getPreLoadHandles()
	 * @see #getDomainObjectsConfig()
	 * @generated
	 */
	EReference getDomainObjectsConfig_PreLoadHandles();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getDomainObject <em>Domain Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Domain Object</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectsConfig#getDomainObject()
	 * @see #getDomainObjectsConfig()
	 * @generated
	 */
	EReference getDomainObjectsConfig_DomainObject();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getConceptTTL <em>Concept TTL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Concept TTL</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectsConfig#getConceptTTL()
	 * @see #getDomainObjectsConfig()
	 * @generated
	 */
	EReference getDomainObjectsConfig_ConceptTTL();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.EvictionConfig <em>Eviction Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Eviction Config</em>'.
	 * @see com.tibco.be.util.config.cdd.EvictionConfig
	 * @generated
	 */
	EClass getEvictionConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.EvictionConfig#getMaxSize <em>Max Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Max Size</em>'.
	 * @see com.tibco.be.util.config.cdd.EvictionConfig#getMaxSize()
	 * @see #getEvictionConfig()
	 * @generated
	 */
	EReference getEvictionConfig_MaxSize();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.EvictionConfig#getMaxTime <em>Max Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Max Time</em>'.
	 * @see com.tibco.be.util.config.cdd.EvictionConfig#getMaxTime()
	 * @see #getEvictionConfig()
	 * @generated
	 */
	EReference getEvictionConfig_MaxTime();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.FilesConfig <em>Files Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Files Config</em>'.
	 * @see com.tibco.be.util.config.cdd.FilesConfig
	 * @generated
	 */
	EClass getFilesConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.FilesConfig#getEnabled <em>Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Enabled</em>'.
	 * @see com.tibco.be.util.config.cdd.FilesConfig#getEnabled()
	 * @see #getFilesConfig()
	 * @generated
	 */
	EReference getFilesConfig_Enabled();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.FilesConfig#getDir <em>Dir</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Dir</em>'.
	 * @see com.tibco.be.util.config.cdd.FilesConfig#getDir()
	 * @see #getFilesConfig()
	 * @generated
	 */
	EReference getFilesConfig_Dir();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.FilesConfig#getMaxNumber <em>Max Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Max Number</em>'.
	 * @see com.tibco.be.util.config.cdd.FilesConfig#getMaxNumber()
	 * @see #getFilesConfig()
	 * @generated
	 */
	EReference getFilesConfig_MaxNumber();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.FilesConfig#getMaxSize <em>Max Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Max Size</em>'.
	 * @see com.tibco.be.util.config.cdd.FilesConfig#getMaxSize()
	 * @see #getFilesConfig()
	 * @generated
	 */
	EReference getFilesConfig_MaxSize();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.FilesConfig#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Name</em>'.
	 * @see com.tibco.be.util.config.cdd.FilesConfig#getName()
	 * @see #getFilesConfig()
	 * @generated
	 */
	EReference getFilesConfig_Name();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.FilesConfig#getAppend <em>Append</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Append</em>'.
	 * @see com.tibco.be.util.config.cdd.FilesConfig#getAppend()
	 * @see #getFilesConfig()
	 * @generated
	 */
	EReference getFilesConfig_Append();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.FunctionGroupsConfig <em>Function Groups Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Groups Config</em>'.
	 * @see com.tibco.be.util.config.cdd.FunctionGroupsConfig
	 * @generated
	 */
	EClass getFunctionGroupsConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.FunctionGroupsConfig#getFunctions <em>Functions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Functions</em>'.
	 * @see com.tibco.be.util.config.cdd.FunctionGroupsConfig#getFunctions()
	 * @see #getFunctionGroupsConfig()
	 * @generated
	 */
	EReference getFunctionGroupsConfig_Functions();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.FunctionsConfig <em>Functions Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Functions Config</em>'.
	 * @see com.tibco.be.util.config.cdd.FunctionsConfig
	 * @generated
	 */
	EClass getFunctionsConfig();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.util.config.cdd.FunctionsConfig#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.tibco.be.util.config.cdd.FunctionsConfig#getGroup()
	 * @see #getFunctionsConfig()
	 * @generated
	 */
	EAttribute getFunctionsConfig_Group();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.be.util.config.cdd.FunctionsConfig#getRef <em>Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ref</em>'.
	 * @see com.tibco.be.util.config.cdd.FunctionsConfig#getRef()
	 * @see #getFunctionsConfig()
	 * @generated
	 */
	EReference getFunctionsConfig_Ref();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.util.config.cdd.FunctionsConfig#getUri <em>Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Uri</em>'.
	 * @see com.tibco.be.util.config.cdd.FunctionsConfig#getUri()
	 * @see #getFunctionsConfig()
	 * @generated
	 */
	EAttribute getFunctionsConfig_Uri();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.GetPropertyConfig <em>Get Property Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Get Property Config</em>'.
	 * @see com.tibco.be.util.config.cdd.GetPropertyConfig
	 * @generated
	 */
	EClass getGetPropertyConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.GetPropertyConfig#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.be.util.config.cdd.GetPropertyConfig#getName()
	 * @see #getGetPropertyConfig()
	 * @generated
	 */
	EAttribute getGetPropertyConfig_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.GetPropertyConfig#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see com.tibco.be.util.config.cdd.GetPropertyConfig#getPath()
	 * @see #getGetPropertyConfig()
	 * @generated
	 */
	EAttribute getGetPropertyConfig_Path();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.GetPropertyConfig#getReference <em>Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Reference</em>'.
	 * @see com.tibco.be.util.config.cdd.GetPropertyConfig#getReference()
	 * @see #getGetPropertyConfig()
	 * @generated
	 */
	EAttribute getGetPropertyConfig_Reference();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.GetPropertyConfig#getThreshold <em>Threshold</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Threshold</em>'.
	 * @see com.tibco.be.util.config.cdd.GetPropertyConfig#getThreshold()
	 * @see #getGetPropertyConfig()
	 * @generated
	 */
	EAttribute getGetPropertyConfig_Threshold();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.HttpConfig <em>Http Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Http Config</em>'.
	 * @see com.tibco.be.util.config.cdd.HttpConfig
	 * @generated
	 */
	EClass getHttpConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.HttpConfig#getAcceptCount <em>Accept Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Accept Count</em>'.
	 * @see com.tibco.be.util.config.cdd.HttpConfig#getAcceptCount()
	 * @see #getHttpConfig()
	 * @generated
	 */
	EReference getHttpConfig_AcceptCount();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.HttpConfig#getConnectionLinger <em>Connection Linger</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Connection Linger</em>'.
	 * @see com.tibco.be.util.config.cdd.HttpConfig#getConnectionLinger()
	 * @see #getHttpConfig()
	 * @generated
	 */
	EReference getHttpConfig_ConnectionLinger();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.HttpConfig#getConnectionTimeout <em>Connection Timeout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Connection Timeout</em>'.
	 * @see com.tibco.be.util.config.cdd.HttpConfig#getConnectionTimeout()
	 * @see #getHttpConfig()
	 * @generated
	 */
	EReference getHttpConfig_ConnectionTimeout();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.HttpConfig#getDocumentPage <em>Document Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Document Page</em>'.
	 * @see com.tibco.be.util.config.cdd.HttpConfig#getDocumentPage()
	 * @see #getHttpConfig()
	 * @generated
	 */
	EReference getHttpConfig_DocumentPage();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.HttpConfig#getDocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Document Root</em>'.
	 * @see com.tibco.be.util.config.cdd.HttpConfig#getDocumentRoot()
	 * @see #getHttpConfig()
	 * @generated
	 */
	EReference getHttpConfig_DocumentRoot();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.HttpConfig#getMaxProcessors <em>Max Processors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Max Processors</em>'.
	 * @see com.tibco.be.util.config.cdd.HttpConfig#getMaxProcessors()
	 * @see #getHttpConfig()
	 * @generated
	 */
	EReference getHttpConfig_MaxProcessors();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.HttpConfig#getSocketOutputBufferSize <em>Socket Output Buffer Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Socket Output Buffer Size</em>'.
	 * @see com.tibco.be.util.config.cdd.HttpConfig#getSocketOutputBufferSize()
	 * @see #getHttpConfig()
	 * @generated
	 */
	EReference getHttpConfig_SocketOutputBufferSize();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.HttpConfig#getSsl <em>Ssl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Ssl</em>'.
	 * @see com.tibco.be.util.config.cdd.HttpConfig#getSsl()
	 * @see #getHttpConfig()
	 * @generated
	 */
	EReference getHttpConfig_Ssl();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.HttpConfig#getStaleConnectionCheck <em>Stale Connection Check</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Stale Connection Check</em>'.
	 * @see com.tibco.be.util.config.cdd.HttpConfig#getStaleConnectionCheck()
	 * @see #getHttpConfig()
	 * @generated
	 */
	EReference getHttpConfig_StaleConnectionCheck();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.HttpConfig#getTcpNoDelay <em>Tcp No Delay</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Tcp No Delay</em>'.
	 * @see com.tibco.be.util.config.cdd.HttpConfig#getTcpNoDelay()
	 * @see #getHttpConfig()
	 * @generated
	 */
	EReference getHttpConfig_TcpNoDelay();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.IndexConfig <em>Index Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Index Config</em>'.
	 * @see com.tibco.be.util.config.cdd.IndexConfig
	 * @generated
	 */
	EClass getIndexConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.IndexConfig#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Property</em>'.
	 * @see com.tibco.be.util.config.cdd.IndexConfig#getProperty()
	 * @see #getIndexConfig()
	 * @generated
	 */
	EReference getIndexConfig_Property();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.IndexesConfig <em>Indexes Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Indexes Config</em>'.
	 * @see com.tibco.be.util.config.cdd.IndexesConfig
	 * @generated
	 */
	EClass getIndexesConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.IndexesConfig#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Index</em>'.
	 * @see com.tibco.be.util.config.cdd.IndexesConfig#getIndex()
	 * @see #getIndexesConfig()
	 * @generated
	 */
	EReference getIndexesConfig_Index();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.InferenceAgentClassConfig <em>Inference Agent Class Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inference Agent Class Config</em>'.
	 * @see com.tibco.be.util.config.cdd.InferenceAgentClassConfig
	 * @generated
	 */
	EClass getInferenceAgentClassConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.InferenceAgentClassConfig#getBusinessworks <em>Businessworks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Businessworks</em>'.
	 * @see com.tibco.be.util.config.cdd.InferenceAgentClassConfig#getBusinessworks()
	 * @see #getInferenceAgentClassConfig()
	 * @generated
	 */
	EReference getInferenceAgentClassConfig_Businessworks();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.InferenceAgentClassConfig#getCheckForDuplicates <em>Check For Duplicates</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Check For Duplicates</em>'.
	 * @see com.tibco.be.util.config.cdd.InferenceAgentClassConfig#getCheckForDuplicates()
	 * @see #getInferenceAgentClassConfig()
	 * @generated
	 */
	EReference getInferenceAgentClassConfig_CheckForDuplicates();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.InferenceAgentClassConfig#getConcurrentRtc <em>Concurrent Rtc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Concurrent Rtc</em>'.
	 * @see com.tibco.be.util.config.cdd.InferenceAgentClassConfig#getConcurrentRtc()
	 * @see #getInferenceAgentClassConfig()
	 * @generated
	 */
	EReference getInferenceAgentClassConfig_ConcurrentRtc();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.InferenceAgentClassConfig#getDestinations <em>Destinations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Destinations</em>'.
	 * @see com.tibco.be.util.config.cdd.InferenceAgentClassConfig#getDestinations()
	 * @see #getInferenceAgentClassConfig()
	 * @generated
	 */
	EReference getInferenceAgentClassConfig_Destinations();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.InferenceAgentClassConfig#getLoad <em>Load</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Load</em>'.
	 * @see com.tibco.be.util.config.cdd.InferenceAgentClassConfig#getLoad()
	 * @see #getInferenceAgentClassConfig()
	 * @generated
	 */
	EReference getInferenceAgentClassConfig_Load();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.InferenceAgentClassConfig#getLocalCache <em>Local Cache</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Local Cache</em>'.
	 * @see com.tibco.be.util.config.cdd.InferenceAgentClassConfig#getLocalCache()
	 * @see #getInferenceAgentClassConfig()
	 * @generated
	 */
	EReference getInferenceAgentClassConfig_LocalCache();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.InferenceAgentClassConfig#getPropertyGroup <em>Property Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Property Group</em>'.
	 * @see com.tibco.be.util.config.cdd.InferenceAgentClassConfig#getPropertyGroup()
	 * @see #getInferenceAgentClassConfig()
	 * @generated
	 */
	EReference getInferenceAgentClassConfig_PropertyGroup();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.InferenceAgentClassConfig#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rules</em>'.
	 * @see com.tibco.be.util.config.cdd.InferenceAgentClassConfig#getRules()
	 * @see #getInferenceAgentClassConfig()
	 * @generated
	 */
	EReference getInferenceAgentClassConfig_Rules();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.InferenceAgentClassConfig#getSharedQueue <em>Shared Queue</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Shared Queue</em>'.
	 * @see com.tibco.be.util.config.cdd.InferenceAgentClassConfig#getSharedQueue()
	 * @see #getInferenceAgentClassConfig()
	 * @generated
	 */
	EReference getInferenceAgentClassConfig_SharedQueue();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.InferenceAgentClassConfig#getShutdown <em>Shutdown</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Shutdown</em>'.
	 * @see com.tibco.be.util.config.cdd.InferenceAgentClassConfig#getShutdown()
	 * @see #getInferenceAgentClassConfig()
	 * @generated
	 */
	EReference getInferenceAgentClassConfig_Shutdown();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.InferenceAgentClassConfig#getStartup <em>Startup</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Startup</em>'.
	 * @see com.tibco.be.util.config.cdd.InferenceAgentClassConfig#getStartup()
	 * @see #getInferenceAgentClassConfig()
	 * @generated
	 */
	EReference getInferenceAgentClassConfig_Startup();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.InferenceEngineConfig <em>Inference Engine Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inference Engine Config</em>'.
	 * @see com.tibco.be.util.config.cdd.InferenceEngineConfig
	 * @generated
	 */
	EClass getInferenceEngineConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.InferenceEngineConfig#getCheckForDuplicates <em>Check For Duplicates</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Check For Duplicates</em>'.
	 * @see com.tibco.be.util.config.cdd.InferenceEngineConfig#getCheckForDuplicates()
	 * @see #getInferenceEngineConfig()
	 * @generated
	 */
	EReference getInferenceEngineConfig_CheckForDuplicates();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.InferenceEngineConfig#getConcurrentRtc <em>Concurrent Rtc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Concurrent Rtc</em>'.
	 * @see com.tibco.be.util.config.cdd.InferenceEngineConfig#getConcurrentRtc()
	 * @see #getInferenceEngineConfig()
	 * @generated
	 */
	EReference getInferenceEngineConfig_ConcurrentRtc();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.InferenceEngineConfig#getLocalCache <em>Local Cache</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Local Cache</em>'.
	 * @see com.tibco.be.util.config.cdd.InferenceEngineConfig#getLocalCache()
	 * @see #getInferenceEngineConfig()
	 * @generated
	 */
	EReference getInferenceEngineConfig_LocalCache();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.InferenceEngineConfig#getPropertyGroup <em>Property Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Property Group</em>'.
	 * @see com.tibco.be.util.config.cdd.InferenceEngineConfig#getPropertyGroup()
	 * @see #getInferenceEngineConfig()
	 * @generated
	 */
	EReference getInferenceEngineConfig_PropertyGroup();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.JobManagerConfig <em>Job Manager Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Job Manager Config</em>'.
	 * @see com.tibco.be.util.config.cdd.JobManagerConfig
	 * @generated
	 */
	EClass getJobManagerConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.JobManagerConfig#getJobPoolQueueSize <em>Job Pool Queue Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Job Pool Queue Size</em>'.
	 * @see com.tibco.be.util.config.cdd.JobManagerConfig#getJobPoolQueueSize()
	 * @see #getJobManagerConfig()
	 * @generated
	 */
	EReference getJobManagerConfig_JobPoolQueueSize();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.JobManagerConfig#getJobPoolThreadCount <em>Job Pool Thread Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Job Pool Thread Count</em>'.
	 * @see com.tibco.be.util.config.cdd.JobManagerConfig#getJobPoolThreadCount()
	 * @see #getJobManagerConfig()
	 * @generated
	 */
	EReference getJobManagerConfig_JobPoolThreadCount();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.LineLayoutConfig <em>Line Layout Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Line Layout Config</em>'.
	 * @see com.tibco.be.util.config.cdd.LineLayoutConfig
	 * @generated
	 */
	EClass getLineLayoutConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LineLayoutConfig#getEnabled <em>Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Enabled</em>'.
	 * @see com.tibco.be.util.config.cdd.LineLayoutConfig#getEnabled()
	 * @see #getLineLayoutConfig()
	 * @generated
	 */
	EReference getLineLayoutConfig_Enabled();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LineLayoutConfig#getClass_ <em>Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Class</em>'.
	 * @see com.tibco.be.util.config.cdd.LineLayoutConfig#getClass_()
	 * @see #getLineLayoutConfig()
	 * @generated
	 */
	EReference getLineLayoutConfig_Class();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.LineLayoutConfig#getArg <em>Arg</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Arg</em>'.
	 * @see com.tibco.be.util.config.cdd.LineLayoutConfig#getArg()
	 * @see #getLineLayoutConfig()
	 * @generated
	 */
	EReference getLineLayoutConfig_Arg();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.LoadBalancerAdhocConfigConfig <em>Load Balancer Adhoc Config Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Load Balancer Adhoc Config Config</em>'.
	 * @see com.tibco.be.util.config.cdd.LoadBalancerAdhocConfigConfig
	 * @generated
	 */
	EClass getLoadBalancerAdhocConfigConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LoadBalancerAdhocConfigConfig#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Name</em>'.
	 * @see com.tibco.be.util.config.cdd.LoadBalancerAdhocConfigConfig#getName()
	 * @see #getLoadBalancerAdhocConfigConfig()
	 * @generated
	 */
	EReference getLoadBalancerAdhocConfigConfig_Name();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LoadBalancerAdhocConfigConfig#getChannel <em>Channel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Channel</em>'.
	 * @see com.tibco.be.util.config.cdd.LoadBalancerAdhocConfigConfig#getChannel()
	 * @see #getLoadBalancerAdhocConfigConfig()
	 * @generated
	 */
	EReference getLoadBalancerAdhocConfigConfig_Channel();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LoadBalancerAdhocConfigConfig#getPropertyGroup <em>Property Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Property Group</em>'.
	 * @see com.tibco.be.util.config.cdd.LoadBalancerAdhocConfigConfig#getPropertyGroup()
	 * @see #getLoadBalancerAdhocConfigConfig()
	 * @generated
	 */
	EReference getLoadBalancerAdhocConfigConfig_PropertyGroup();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.LoadBalancerAdhocConfigsConfig <em>Load Balancer Adhoc Configs Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Load Balancer Adhoc Configs Config</em>'.
	 * @see com.tibco.be.util.config.cdd.LoadBalancerAdhocConfigsConfig
	 * @generated
	 */
	EClass getLoadBalancerAdhocConfigsConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.LoadBalancerAdhocConfigsConfig#getLoadBalancerAdhocConfigs <em>Load Balancer Adhoc Configs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Load Balancer Adhoc Configs</em>'.
	 * @see com.tibco.be.util.config.cdd.LoadBalancerAdhocConfigsConfig#getLoadBalancerAdhocConfigs()
	 * @see #getLoadBalancerAdhocConfigsConfig()
	 * @generated
	 */
	EReference getLoadBalancerAdhocConfigsConfig_LoadBalancerAdhocConfigs();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.LoadBalancerConfigsConfig <em>Load Balancer Configs Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Load Balancer Configs Config</em>'.
	 * @see com.tibco.be.util.config.cdd.LoadBalancerConfigsConfig
	 * @generated
	 */
	EClass getLoadBalancerConfigsConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LoadBalancerConfigsConfig#getLoadBalancerPairConfigs <em>Load Balancer Pair Configs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Load Balancer Pair Configs</em>'.
	 * @see com.tibco.be.util.config.cdd.LoadBalancerConfigsConfig#getLoadBalancerPairConfigs()
	 * @see #getLoadBalancerConfigsConfig()
	 * @generated
	 */
	EReference getLoadBalancerConfigsConfig_LoadBalancerPairConfigs();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LoadBalancerConfigsConfig#getLoadBalancerAdhocConfigs <em>Load Balancer Adhoc Configs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Load Balancer Adhoc Configs</em>'.
	 * @see com.tibco.be.util.config.cdd.LoadBalancerConfigsConfig#getLoadBalancerAdhocConfigs()
	 * @see #getLoadBalancerConfigsConfig()
	 * @generated
	 */
	EReference getLoadBalancerConfigsConfig_LoadBalancerAdhocConfigs();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig <em>Load Balancer Pair Config Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Load Balancer Pair Config Config</em>'.
	 * @see com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig
	 * @generated
	 */
	EClass getLoadBalancerPairConfigConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Name</em>'.
	 * @see com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig#getName()
	 * @see #getLoadBalancerPairConfigConfig()
	 * @generated
	 */
	EReference getLoadBalancerPairConfigConfig_Name();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig#getChannel <em>Channel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Channel</em>'.
	 * @see com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig#getChannel()
	 * @see #getLoadBalancerPairConfigConfig()
	 * @generated
	 */
	EReference getLoadBalancerPairConfigConfig_Channel();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Key</em>'.
	 * @see com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig#getKey()
	 * @see #getLoadBalancerPairConfigConfig()
	 * @generated
	 */
	EReference getLoadBalancerPairConfigConfig_Key();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig#getRouter <em>Router</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Router</em>'.
	 * @see com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig#getRouter()
	 * @see #getLoadBalancerPairConfigConfig()
	 * @generated
	 */
	EReference getLoadBalancerPairConfigConfig_Router();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig#getReceiver <em>Receiver</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Receiver</em>'.
	 * @see com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig#getReceiver()
	 * @see #getLoadBalancerPairConfigConfig()
	 * @generated
	 */
	EReference getLoadBalancerPairConfigConfig_Receiver();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig#getPropertyGroup <em>Property Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Property Group</em>'.
	 * @see com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig#getPropertyGroup()
	 * @see #getLoadBalancerPairConfigConfig()
	 * @generated
	 */
	EReference getLoadBalancerPairConfigConfig_PropertyGroup();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.LoadBalancerPairConfigsConfig <em>Load Balancer Pair Configs Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Load Balancer Pair Configs Config</em>'.
	 * @see com.tibco.be.util.config.cdd.LoadBalancerPairConfigsConfig
	 * @generated
	 */
	EClass getLoadBalancerPairConfigsConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.LoadBalancerPairConfigsConfig#getLoadBalancerPairConfigs <em>Load Balancer Pair Configs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Load Balancer Pair Configs</em>'.
	 * @see com.tibco.be.util.config.cdd.LoadBalancerPairConfigsConfig#getLoadBalancerPairConfigs()
	 * @see #getLoadBalancerPairConfigsConfig()
	 * @generated
	 */
	EReference getLoadBalancerPairConfigsConfig_LoadBalancerPairConfigs();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.LoadConfig <em>Load Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Load Config</em>'.
	 * @see com.tibco.be.util.config.cdd.LoadConfig
	 * @generated
	 */
	EClass getLoadConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LoadConfig#getMaxActive <em>Max Active</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Max Active</em>'.
	 * @see com.tibco.be.util.config.cdd.LoadConfig#getMaxActive()
	 * @see #getLoadConfig()
	 * @generated
	 */
	EReference getLoadConfig_MaxActive();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.LocalCacheConfig <em>Local Cache Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Local Cache Config</em>'.
	 * @see com.tibco.be.util.config.cdd.LocalCacheConfig
	 * @generated
	 */
	EClass getLocalCacheConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LocalCacheConfig#getEviction <em>Eviction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Eviction</em>'.
	 * @see com.tibco.be.util.config.cdd.LocalCacheConfig#getEviction()
	 * @see #getLocalCacheConfig()
	 * @generated
	 */
	EReference getLocalCacheConfig_Eviction();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.LogConfigConfig <em>Log Config Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Log Config Config</em>'.
	 * @see com.tibco.be.util.config.cdd.LogConfigConfig
	 * @generated
	 */
	EClass getLogConfigConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LogConfigConfig#getEnabled <em>Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Enabled</em>'.
	 * @see com.tibco.be.util.config.cdd.LogConfigConfig#getEnabled()
	 * @see #getLogConfigConfig()
	 * @generated
	 */
	EReference getLogConfigConfig_Enabled();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LogConfigConfig#getRoles <em>Roles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Roles</em>'.
	 * @see com.tibco.be.util.config.cdd.LogConfigConfig#getRoles()
	 * @see #getLogConfigConfig()
	 * @generated
	 */
	EReference getLogConfigConfig_Roles();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LogConfigConfig#getLineLayout <em>Line Layout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Line Layout</em>'.
	 * @see com.tibco.be.util.config.cdd.LogConfigConfig#getLineLayout()
	 * @see #getLogConfigConfig()
	 * @generated
	 */
	EReference getLogConfigConfig_LineLayout();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LogConfigConfig#getTerminal <em>Terminal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Terminal</em>'.
	 * @see com.tibco.be.util.config.cdd.LogConfigConfig#getTerminal()
	 * @see #getLogConfigConfig()
	 * @generated
	 */
	EReference getLogConfigConfig_Terminal();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LogConfigConfig#getFiles <em>Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Files</em>'.
	 * @see com.tibco.be.util.config.cdd.LogConfigConfig#getFiles()
	 * @see #getLogConfigConfig()
	 * @generated
	 */
	EReference getLogConfigConfig_Files();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.LogConfigsConfig <em>Log Configs Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Log Configs Config</em>'.
	 * @see com.tibco.be.util.config.cdd.LogConfigsConfig
	 * @generated
	 */
	EClass getLogConfigsConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.LogConfigsConfig#getLogConfig <em>Log Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Log Config</em>'.
	 * @see com.tibco.be.util.config.cdd.LogConfigsConfig#getLogConfig()
	 * @see #getLogConfigsConfig()
	 * @generated
	 */
	EReference getLogConfigsConfig_LogConfig();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.MemoryManagerConfig <em>Memory Manager Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Memory Manager Config</em>'.
	 * @see com.tibco.be.util.config.cdd.MemoryManagerConfig
	 * @generated
	 */
	EClass getMemoryManagerConfig();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.MmActionConfig <em>Mm Action Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mm Action Config</em>'.
	 * @see com.tibco.be.util.config.cdd.MmActionConfig
	 * @generated
	 */
	EClass getMmActionConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.MmActionConfig#getMmExecuteCommand <em>Mm Execute Command</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mm Execute Command</em>'.
	 * @see com.tibco.be.util.config.cdd.MmActionConfig#getMmExecuteCommand()
	 * @see #getMmActionConfig()
	 * @generated
	 */
	EReference getMmActionConfig_MmExecuteCommand();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.MmActionConfig#getMmSendEmail <em>Mm Send Email</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mm Send Email</em>'.
	 * @see com.tibco.be.util.config.cdd.MmActionConfig#getMmSendEmail()
	 * @see #getMmActionConfig()
	 * @generated
	 */
	EReference getMmActionConfig_MmSendEmail();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.MmActionConfigConfig <em>Mm Action Config Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mm Action Config Config</em>'.
	 * @see com.tibco.be.util.config.cdd.MmActionConfigConfig
	 * @generated
	 */
	EClass getMmActionConfigConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.MmActionConfigConfig#getMmTriggerCondition <em>Mm Trigger Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mm Trigger Condition</em>'.
	 * @see com.tibco.be.util.config.cdd.MmActionConfigConfig#getMmTriggerCondition()
	 * @see #getMmActionConfigConfig()
	 * @generated
	 */
	EReference getMmActionConfigConfig_MmTriggerCondition();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.MmActionConfigConfig#getMmAction <em>Mm Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mm Action</em>'.
	 * @see com.tibco.be.util.config.cdd.MmActionConfigConfig#getMmAction()
	 * @see #getMmActionConfigConfig()
	 * @generated
	 */
	EReference getMmActionConfigConfig_MmAction();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.MmActionConfigConfig#getActionName <em>Action Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Action Name</em>'.
	 * @see com.tibco.be.util.config.cdd.MmActionConfigConfig#getActionName()
	 * @see #getMmActionConfigConfig()
	 * @generated
	 */
	EAttribute getMmActionConfigConfig_ActionName();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.MmActionConfigSetConfig <em>Mm Action Config Set Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mm Action Config Set Config</em>'.
	 * @see com.tibco.be.util.config.cdd.MmActionConfigSetConfig
	 * @generated
	 */
	EClass getMmActionConfigSetConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.MmActionConfigSetConfig#getMmActionConfig <em>Mm Action Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mm Action Config</em>'.
	 * @see com.tibco.be.util.config.cdd.MmActionConfigSetConfig#getMmActionConfig()
	 * @see #getMmActionConfigSetConfig()
	 * @generated
	 */
	EReference getMmActionConfigSetConfig_MmActionConfig();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.MmAgentClassConfig <em>Mm Agent Class Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mm Agent Class Config</em>'.
	 * @see com.tibco.be.util.config.cdd.MmAgentClassConfig
	 * @generated
	 */
	EClass getMmAgentClassConfig();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.be.util.config.cdd.MmAgentClassConfig#getMmInferenceAgentClass <em>Mm Inference Agent Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Mm Inference Agent Class</em>'.
	 * @see com.tibco.be.util.config.cdd.MmAgentClassConfig#getMmInferenceAgentClass()
	 * @see #getMmAgentClassConfig()
	 * @generated
	 */
	EReference getMmAgentClassConfig_MmInferenceAgentClass();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.be.util.config.cdd.MmAgentClassConfig#getMmQueryAgentClass <em>Mm Query Agent Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Mm Query Agent Class</em>'.
	 * @see com.tibco.be.util.config.cdd.MmAgentClassConfig#getMmQueryAgentClass()
	 * @see #getMmAgentClassConfig()
	 * @generated
	 */
	EReference getMmAgentClassConfig_MmQueryAgentClass();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.MmAgentClassConfig#getAlertConfigSet <em>Alert Config Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Alert Config Set</em>'.
	 * @see com.tibco.be.util.config.cdd.MmAgentClassConfig#getAlertConfigSet()
	 * @see #getMmAgentClassConfig()
	 * @generated
	 */
	EReference getMmAgentClassConfig_AlertConfigSet();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.MmAgentClassConfig#getRuleConfig <em>Rule Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rule Config</em>'.
	 * @see com.tibco.be.util.config.cdd.MmAgentClassConfig#getRuleConfig()
	 * @see #getMmAgentClassConfig()
	 * @generated
	 */
	EReference getMmAgentClassConfig_RuleConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.MmAgentClassConfig#getMmActionConfigSet <em>Mm Action Config Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mm Action Config Set</em>'.
	 * @see com.tibco.be.util.config.cdd.MmAgentClassConfig#getMmActionConfigSet()
	 * @see #getMmAgentClassConfig()
	 * @generated
	 */
	EReference getMmAgentClassConfig_MmActionConfigSet();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.MmAgentClassConfig#getPropertyGroup <em>Property Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Property Group</em>'.
	 * @see com.tibco.be.util.config.cdd.MmAgentClassConfig#getPropertyGroup()
	 * @see #getMmAgentClassConfig()
	 * @generated
	 */
	EReference getMmAgentClassConfig_PropertyGroup();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.MmAlertConfig <em>Mm Alert Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mm Alert Config</em>'.
	 * @see com.tibco.be.util.config.cdd.MmAlertConfig
	 * @generated
	 */
	EClass getMmAlertConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.MmAlertConfig#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see com.tibco.be.util.config.cdd.MmAlertConfig#getPath()
	 * @see #getMmAlertConfig()
	 * @generated
	 */
	EAttribute getMmAlertConfig_Path();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.MmAlertConfig#getSeverity <em>Severity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Severity</em>'.
	 * @see com.tibco.be.util.config.cdd.MmAlertConfig#getSeverity()
	 * @see #getMmAlertConfig()
	 * @generated
	 */
	EAttribute getMmAlertConfig_Severity();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.MmExecuteCommandConfig <em>Mm Execute Command Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mm Execute Command Config</em>'.
	 * @see com.tibco.be.util.config.cdd.MmExecuteCommandConfig
	 * @generated
	 */
	EClass getMmExecuteCommandConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.MmExecuteCommandConfig#getCommand <em>Command</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Command</em>'.
	 * @see com.tibco.be.util.config.cdd.MmExecuteCommandConfig#getCommand()
	 * @see #getMmExecuteCommandConfig()
	 * @generated
	 */
	EAttribute getMmExecuteCommandConfig_Command();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.MmHealthLevelConfig <em>Mm Health Level Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mm Health Level Config</em>'.
	 * @see com.tibco.be.util.config.cdd.MmHealthLevelConfig
	 * @generated
	 */
	EClass getMmHealthLevelConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.MmHealthLevelConfig#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see com.tibco.be.util.config.cdd.MmHealthLevelConfig#getPath()
	 * @see #getMmHealthLevelConfig()
	 * @generated
	 */
	EAttribute getMmHealthLevelConfig_Path();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.MmHealthLevelConfig#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.tibco.be.util.config.cdd.MmHealthLevelConfig#getValue()
	 * @see #getMmHealthLevelConfig()
	 * @generated
	 */
	EAttribute getMmHealthLevelConfig_Value();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.MmSendEmailConfig <em>Mm Send Email Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mm Send Email Config</em>'.
	 * @see com.tibco.be.util.config.cdd.MmSendEmailConfig
	 * @generated
	 */
	EClass getMmSendEmailConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.MmSendEmailConfig#getCc <em>Cc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cc</em>'.
	 * @see com.tibco.be.util.config.cdd.MmSendEmailConfig#getCc()
	 * @see #getMmSendEmailConfig()
	 * @generated
	 */
	EAttribute getMmSendEmailConfig_Cc();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.MmSendEmailConfig#getMsg <em>Msg</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Msg</em>'.
	 * @see com.tibco.be.util.config.cdd.MmSendEmailConfig#getMsg()
	 * @see #getMmSendEmailConfig()
	 * @generated
	 */
	EAttribute getMmSendEmailConfig_Msg();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.MmSendEmailConfig#getSubject <em>Subject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Subject</em>'.
	 * @see com.tibco.be.util.config.cdd.MmSendEmailConfig#getSubject()
	 * @see #getMmSendEmailConfig()
	 * @generated
	 */
	EAttribute getMmSendEmailConfig_Subject();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.MmSendEmailConfig#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>To</em>'.
	 * @see com.tibco.be.util.config.cdd.MmSendEmailConfig#getTo()
	 * @see #getMmSendEmailConfig()
	 * @generated
	 */
	EAttribute getMmSendEmailConfig_To();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.MmTriggerConditionConfig <em>Mm Trigger Condition Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mm Trigger Condition Config</em>'.
	 * @see com.tibco.be.util.config.cdd.MmTriggerConditionConfig
	 * @generated
	 */
	EClass getMmTriggerConditionConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.MmTriggerConditionConfig#getMmHealthLevel <em>Mm Health Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mm Health Level</em>'.
	 * @see com.tibco.be.util.config.cdd.MmTriggerConditionConfig#getMmHealthLevel()
	 * @see #getMmTriggerConditionConfig()
	 * @generated
	 */
	EReference getMmTriggerConditionConfig_MmHealthLevel();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.MmTriggerConditionConfig#getMmAlert <em>Mm Alert</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mm Alert</em>'.
	 * @see com.tibco.be.util.config.cdd.MmTriggerConditionConfig#getMmAlert()
	 * @see #getMmTriggerConditionConfig()
	 * @generated
	 */
	EReference getMmTriggerConditionConfig_MmAlert();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.NotificationConfig <em>Notification Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Notification Config</em>'.
	 * @see com.tibco.be.util.config.cdd.NotificationConfig
	 * @generated
	 */
	EClass getNotificationConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.NotificationConfig#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Property</em>'.
	 * @see com.tibco.be.util.config.cdd.NotificationConfig#getProperty()
	 * @see #getNotificationConfig()
	 * @generated
	 */
	EReference getNotificationConfig_Property();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.NotificationConfig#getRange <em>Range</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Range</em>'.
	 * @see com.tibco.be.util.config.cdd.NotificationConfig#getRange()
	 * @see #getNotificationConfig()
	 * @generated
	 */
	EAttribute getNotificationConfig_Range();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.NotificationConfig#getTolerance <em>Tolerance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tolerance</em>'.
	 * @see com.tibco.be.util.config.cdd.NotificationConfig#getTolerance()
	 * @see #getNotificationConfig()
	 * @generated
	 */
	EAttribute getNotificationConfig_Tolerance();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.ObjectManagementConfig <em>Object Management Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Object Management Config</em>'.
	 * @see com.tibco.be.util.config.cdd.ObjectManagementConfig
	 * @generated
	 */
	EClass getObjectManagementConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ObjectManagementConfig#getMemoryManager <em>Memory Manager</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Memory Manager</em>'.
	 * @see com.tibco.be.util.config.cdd.ObjectManagementConfig#getMemoryManager()
	 * @see #getObjectManagementConfig()
	 * @generated
	 */
	EReference getObjectManagementConfig_MemoryManager();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ObjectManagementConfig#getCacheManager <em>Cache Manager</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cache Manager</em>'.
	 * @see com.tibco.be.util.config.cdd.ObjectManagementConfig#getCacheManager()
	 * @see #getObjectManagementConfig()
	 * @generated
	 */
	EReference getObjectManagementConfig_CacheManager();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ObjectManagementConfig#getDbConcepts <em>Db Concepts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Db Concepts</em>'.
	 * @see com.tibco.be.util.config.cdd.ObjectManagementConfig#getDbConcepts()
	 * @see #getObjectManagementConfig()
	 * @generated
	 */
	EReference getObjectManagementConfig_DbConcepts();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.ObjectManagerConfig <em>Object Manager Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Object Manager Config</em>'.
	 * @see com.tibco.be.util.config.cdd.ObjectManagerConfig
	 * @generated
	 */
	EClass getObjectManagerConfig();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.ObjectTableConfig <em>Object Table Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Object Table Config</em>'.
	 * @see com.tibco.be.util.config.cdd.ObjectTableConfig
	 * @generated
	 */
	EClass getObjectTableConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ObjectTableConfig#getMaxSize <em>Max Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Max Size</em>'.
	 * @see com.tibco.be.util.config.cdd.ObjectTableConfig#getMaxSize()
	 * @see #getObjectTableConfig()
	 * @generated
	 */
	EReference getObjectTableConfig_MaxSize();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.OverrideConfig <em>Override Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Override Config</em>'.
	 * @see com.tibco.be.util.config.cdd.OverrideConfig
	 * @generated
	 */
	EClass getOverrideConfig();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.ProcessAgentClassConfig <em>Process Agent Class Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Process Agent Class Config</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessAgentClassConfig
	 * @generated
	 */
	EClass getProcessAgentClassConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ProcessAgentClassConfig#getLoad <em>Load</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Load</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessAgentClassConfig#getLoad()
	 * @see #getProcessAgentClassConfig()
	 * @generated
	 */
	EReference getProcessAgentClassConfig_Load();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.ProcessAgentClassConfig#getProcessEngine <em>Process Engine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Process Engine</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessAgentClassConfig#getProcessEngine()
	 * @see #getProcessAgentClassConfig()
	 * @generated
	 */
	EReference getProcessAgentClassConfig_ProcessEngine();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ProcessAgentClassConfig#getPropertyGroup <em>Property Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Property Group</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessAgentClassConfig#getPropertyGroup()
	 * @see #getProcessAgentClassConfig()
	 * @generated
	 */
	EReference getProcessAgentClassConfig_PropertyGroup();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.ProcessConfig <em>Process Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Process Config</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessConfig
	 * @generated
	 */
	EClass getProcessConfig();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.util.config.cdd.ProcessConfig#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessConfig#getGroup()
	 * @see #getProcessConfig()
	 * @generated
	 */
	EAttribute getProcessConfig_Group();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.be.util.config.cdd.ProcessConfig#getRef <em>Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ref</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessConfig#getRef()
	 * @see #getProcessConfig()
	 * @generated
	 */
	EReference getProcessConfig_Ref();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.util.config.cdd.ProcessConfig#getUri <em>Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Uri</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessConfig#getUri()
	 * @see #getProcessConfig()
	 * @generated
	 */
	EAttribute getProcessConfig_Uri();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.ProcessEngineConfig <em>Process Engine Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Process Engine Config</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessEngineConfig
	 * @generated
	 */
	EClass getProcessEngineConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ProcessEngineConfig#getProcess <em>Process</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Process</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessEngineConfig#getProcess()
	 * @see #getProcessEngineConfig()
	 * @generated
	 */
	EReference getProcessEngineConfig_Process();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ProcessEngineConfig#getStartup <em>Startup</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Startup</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessEngineConfig#getStartup()
	 * @see #getProcessEngineConfig()
	 * @generated
	 */
	EReference getProcessEngineConfig_Startup();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ProcessEngineConfig#getShutdown <em>Shutdown</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Shutdown</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessEngineConfig#getShutdown()
	 * @see #getProcessEngineConfig()
	 * @generated
	 */
	EReference getProcessEngineConfig_Shutdown();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ProcessEngineConfig#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rules</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessEngineConfig#getRules()
	 * @see #getProcessEngineConfig()
	 * @generated
	 */
	EReference getProcessEngineConfig_Rules();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ProcessEngineConfig#getDestinations <em>Destinations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Destinations</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessEngineConfig#getDestinations()
	 * @see #getProcessEngineConfig()
	 * @generated
	 */
	EReference getProcessEngineConfig_Destinations();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ProcessEngineConfig#getBusinessworks <em>Businessworks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Businessworks</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessEngineConfig#getBusinessworks()
	 * @see #getProcessEngineConfig()
	 * @generated
	 */
	EReference getProcessEngineConfig_Businessworks();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ProcessEngineConfig#getPropertyGroup <em>Property Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Property Group</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessEngineConfig#getPropertyGroup()
	 * @see #getProcessEngineConfig()
	 * @generated
	 */
	EReference getProcessEngineConfig_PropertyGroup();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ProcessEngineConfig#getJobManager <em>Job Manager</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Job Manager</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessEngineConfig#getJobManager()
	 * @see #getProcessEngineConfig()
	 * @generated
	 */
	EReference getProcessEngineConfig_JobManager();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ProcessEngineConfig#getInferenceEngine <em>Inference Engine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Inference Engine</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessEngineConfig#getInferenceEngine()
	 * @see #getProcessEngineConfig()
	 * @generated
	 */
	EReference getProcessEngineConfig_InferenceEngine();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.ProcessesConfig <em>Processes Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Processes Config</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessesConfig
	 * @generated
	 */
	EClass getProcessesConfig();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.util.config.cdd.ProcessesConfig#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessesConfig#getGroup()
	 * @see #getProcessesConfig()
	 * @generated
	 */
	EAttribute getProcessesConfig_Group();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.be.util.config.cdd.ProcessesConfig#getRef <em>Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ref</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessesConfig#getRef()
	 * @see #getProcessesConfig()
	 * @generated
	 */
	EReference getProcessesConfig_Ref();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.util.config.cdd.ProcessesConfig#getUri <em>Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Uri</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessesConfig#getUri()
	 * @see #getProcessesConfig()
	 * @generated
	 */
	EAttribute getProcessesConfig_Uri();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.ProcessGroupsConfig <em>Process Groups Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Process Groups Config</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessGroupsConfig
	 * @generated
	 */
	EClass getProcessGroupsConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.ProcessGroupsConfig#getProcesses <em>Processes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Processes</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessGroupsConfig#getProcesses()
	 * @see #getProcessGroupsConfig()
	 * @generated
	 */
	EReference getProcessGroupsConfig_Processes();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.ProcessingUnitConfig <em>Processing Unit Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Processing Unit Config</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessingUnitConfig
	 * @generated
	 */
	EClass getProcessingUnitConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ProcessingUnitConfig#getAgents <em>Agents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Agents</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessingUnitConfig#getAgents()
	 * @see #getProcessingUnitConfig()
	 * @generated
	 */
	EReference getProcessingUnitConfig_Agents();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ProcessingUnitConfig#getCacheStorageEnabled <em>Cache Storage Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cache Storage Enabled</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessingUnitConfig#getCacheStorageEnabled()
	 * @see #getProcessingUnitConfig()
	 * @generated
	 */
	EReference getProcessingUnitConfig_CacheStorageEnabled();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ProcessingUnitConfig#getDbConcepts <em>Db Concepts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Db Concepts</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessingUnitConfig#getDbConcepts()
	 * @see #getProcessingUnitConfig()
	 * @generated
	 */
	EReference getProcessingUnitConfig_DbConcepts();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.be.util.config.cdd.ProcessingUnitConfig#getLogs <em>Logs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Logs</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessingUnitConfig#getLogs()
	 * @see #getProcessingUnitConfig()
	 * @generated
	 */
	EReference getProcessingUnitConfig_Logs();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ProcessingUnitConfig#getHotDeploy <em>Hot Deploy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Hot Deploy</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessingUnitConfig#getHotDeploy()
	 * @see #getProcessingUnitConfig()
	 * @generated
	 */
	EReference getProcessingUnitConfig_HotDeploy();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ProcessingUnitConfig#getHttp <em>Http</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Http</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessingUnitConfig#getHttp()
	 * @see #getProcessingUnitConfig()
	 * @generated
	 */
	EReference getProcessingUnitConfig_Http();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ProcessingUnitConfig#getPropertyGroup <em>Property Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Property Group</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessingUnitConfig#getPropertyGroup()
	 * @see #getProcessingUnitConfig()
	 * @generated
	 */
	EReference getProcessingUnitConfig_PropertyGroup();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ProcessingUnitConfig#getSecurityConfig <em>Security Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Security Config</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessingUnitConfig#getSecurityConfig()
	 * @see #getProcessingUnitConfig()
	 * @generated
	 */
	EReference getProcessingUnitConfig_SecurityConfig();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.ProcessingUnitsConfig <em>Processing Units Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Processing Units Config</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessingUnitsConfig
	 * @generated
	 */
	EClass getProcessingUnitsConfig();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.util.config.cdd.ProcessingUnitsConfig#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessingUnitsConfig#getGroup()
	 * @see #getProcessingUnitsConfig()
	 * @generated
	 */
	EAttribute getProcessingUnitsConfig_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.ProcessingUnitsConfig#getProcessingUnit <em>Processing Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Processing Unit</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessingUnitsConfig#getProcessingUnit()
	 * @see #getProcessingUnitsConfig()
	 * @generated
	 */
	EReference getProcessingUnitsConfig_ProcessingUnit();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.ProcessingUnitSecurityConfig <em>Processing Unit Security Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Processing Unit Security Config</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessingUnitSecurityConfig
	 * @generated
	 */
	EClass getProcessingUnitSecurityConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ProcessingUnitSecurityConfig#getRole <em>Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Role</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessingUnitSecurityConfig#getRole()
	 * @see #getProcessingUnitSecurityConfig()
	 * @generated
	 */
	EReference getProcessingUnitSecurityConfig_Role();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ProcessingUnitSecurityConfig#getController <em>Controller</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Controller</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessingUnitSecurityConfig#getController()
	 * @see #getProcessingUnitSecurityConfig()
	 * @generated
	 */
	EReference getProcessingUnitSecurityConfig_Controller();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ProcessingUnitSecurityConfig#getRequester <em>Requester</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Requester</em>'.
	 * @see com.tibco.be.util.config.cdd.ProcessingUnitSecurityConfig#getRequester()
	 * @see #getProcessingUnitSecurityConfig()
	 * @generated
	 */
	EReference getProcessingUnitSecurityConfig_Requester();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.ProjectionConfig <em>Projection Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Projection Config</em>'.
	 * @see com.tibco.be.util.config.cdd.ProjectionConfig
	 * @generated
	 */
	EClass getProjectionConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.ProjectionConfig#getSetProperty <em>Set Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Set Property</em>'.
	 * @see com.tibco.be.util.config.cdd.ProjectionConfig#getSetProperty()
	 * @see #getProjectionConfig()
	 * @generated
	 */
	EReference getProjectionConfig_SetProperty();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.PropertyConfig <em>Property Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Config</em>'.
	 * @see com.tibco.be.util.config.cdd.PropertyConfig
	 * @generated
	 */
	EClass getPropertyConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.PropertyConfig#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.be.util.config.cdd.PropertyConfig#getName()
	 * @see #getPropertyConfig()
	 * @generated
	 */
	EAttribute getPropertyConfig_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.PropertyConfig#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.tibco.be.util.config.cdd.PropertyConfig#getType()
	 * @see #getPropertyConfig()
	 * @generated
	 */
	EAttribute getPropertyConfig_Type();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.PropertyConfig#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.tibco.be.util.config.cdd.PropertyConfig#getValue()
	 * @see #getPropertyConfig()
	 * @generated
	 */
	EAttribute getPropertyConfig_Value();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.PropertyGroupConfig <em>Property Group Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Group Config</em>'.
	 * @see com.tibco.be.util.config.cdd.PropertyGroupConfig
	 * @generated
	 */
	EClass getPropertyGroupConfig();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.util.config.cdd.PropertyGroupConfig#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.tibco.be.util.config.cdd.PropertyGroupConfig#getGroup()
	 * @see #getPropertyGroupConfig()
	 * @generated
	 */
	EAttribute getPropertyGroupConfig_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.PropertyGroupConfig#getPropertyGroup <em>Property Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Property Group</em>'.
	 * @see com.tibco.be.util.config.cdd.PropertyGroupConfig#getPropertyGroup()
	 * @see #getPropertyGroupConfig()
	 * @generated
	 */
	EReference getPropertyGroupConfig_PropertyGroup();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.PropertyGroupConfig#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Property</em>'.
	 * @see com.tibco.be.util.config.cdd.PropertyGroupConfig#getProperty()
	 * @see #getPropertyGroupConfig()
	 * @generated
	 */
	EReference getPropertyGroupConfig_Property();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.PropertyGroupConfig#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see com.tibco.be.util.config.cdd.PropertyGroupConfig#getComment()
	 * @see #getPropertyGroupConfig()
	 * @generated
	 */
	EAttribute getPropertyGroupConfig_Comment();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.PropertyGroupConfig#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.be.util.config.cdd.PropertyGroupConfig#getName()
	 * @see #getPropertyGroupConfig()
	 * @generated
	 */
	EAttribute getPropertyGroupConfig_Name();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.ProtocolsConfig <em>Protocols Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Protocols Config</em>'.
	 * @see com.tibco.be.util.config.cdd.ProtocolsConfig
	 * @generated
	 */
	EClass getProtocolsConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.ProtocolsConfig#getProtocol <em>Protocol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Protocol</em>'.
	 * @see com.tibco.be.util.config.cdd.ProtocolsConfig#getProtocol()
	 * @see #getProtocolsConfig()
	 * @generated
	 */
	EReference getProtocolsConfig_Protocol();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.ProviderConfig <em>Provider Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Provider Config</em>'.
	 * @see com.tibco.be.util.config.cdd.ProviderConfig
	 * @generated
	 */
	EClass getProviderConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.ProviderConfig#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Name</em>'.
	 * @see com.tibco.be.util.config.cdd.ProviderConfig#getName()
	 * @see #getProviderConfig()
	 * @generated
	 */
	EReference getProviderConfig_Name();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.QueryAgentClassConfig <em>Query Agent Class Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Query Agent Class Config</em>'.
	 * @see com.tibco.be.util.config.cdd.QueryAgentClassConfig
	 * @generated
	 */
	EClass getQueryAgentClassConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.QueryAgentClassConfig#getDestinations <em>Destinations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Destinations</em>'.
	 * @see com.tibco.be.util.config.cdd.QueryAgentClassConfig#getDestinations()
	 * @see #getQueryAgentClassConfig()
	 * @generated
	 */
	EReference getQueryAgentClassConfig_Destinations();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.QueryAgentClassConfig#getStartup <em>Startup</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Startup</em>'.
	 * @see com.tibco.be.util.config.cdd.QueryAgentClassConfig#getStartup()
	 * @see #getQueryAgentClassConfig()
	 * @generated
	 */
	EReference getQueryAgentClassConfig_Startup();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.QueryAgentClassConfig#getShutdown <em>Shutdown</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Shutdown</em>'.
	 * @see com.tibco.be.util.config.cdd.QueryAgentClassConfig#getShutdown()
	 * @see #getQueryAgentClassConfig()
	 * @generated
	 */
	EReference getQueryAgentClassConfig_Shutdown();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.QueryAgentClassConfig#getLoad <em>Load</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Load</em>'.
	 * @see com.tibco.be.util.config.cdd.QueryAgentClassConfig#getLoad()
	 * @see #getQueryAgentClassConfig()
	 * @generated
	 */
	EReference getQueryAgentClassConfig_Load();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.QueryAgentClassConfig#getLocalCache <em>Local Cache</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Local Cache</em>'.
	 * @see com.tibco.be.util.config.cdd.QueryAgentClassConfig#getLocalCache()
	 * @see #getQueryAgentClassConfig()
	 * @generated
	 */
	EReference getQueryAgentClassConfig_LocalCache();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.QueryAgentClassConfig#getPropertyGroup <em>Property Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Property Group</em>'.
	 * @see com.tibco.be.util.config.cdd.QueryAgentClassConfig#getPropertyGroup()
	 * @see #getQueryAgentClassConfig()
	 * @generated
	 */
	EReference getQueryAgentClassConfig_PropertyGroup();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.QueryAgentClassConfig#getSharedQueue <em>Shared Queue</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Shared Queue</em>'.
	 * @see com.tibco.be.util.config.cdd.QueryAgentClassConfig#getSharedQueue()
	 * @see #getQueryAgentClassConfig()
	 * @generated
	 */
	EReference getQueryAgentClassConfig_SharedQueue();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.RevisionConfig <em>Revision Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Revision Config</em>'.
	 * @see com.tibco.be.util.config.cdd.RevisionConfig
	 * @generated
	 */
	EClass getRevisionConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.RevisionConfig#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see com.tibco.be.util.config.cdd.RevisionConfig#getVersion()
	 * @see #getRevisionConfig()
	 * @generated
	 */
	EAttribute getRevisionConfig_Version();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.RevisionConfig#getAuthor <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Author</em>'.
	 * @see com.tibco.be.util.config.cdd.RevisionConfig#getAuthor()
	 * @see #getRevisionConfig()
	 * @generated
	 */
	EReference getRevisionConfig_Author();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.RevisionConfig#getDate <em>Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Date</em>'.
	 * @see com.tibco.be.util.config.cdd.RevisionConfig#getDate()
	 * @see #getRevisionConfig()
	 * @generated
	 */
	EReference getRevisionConfig_Date();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.RevisionConfig#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Comment</em>'.
	 * @see com.tibco.be.util.config.cdd.RevisionConfig#getComment()
	 * @see #getRevisionConfig()
	 * @generated
	 */
	EReference getRevisionConfig_Comment();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.RuleConfig <em>Rule Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Config</em>'.
	 * @see com.tibco.be.util.config.cdd.RuleConfig
	 * @generated
	 */
	EClass getRuleConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.RuleConfig#isEnabled <em>Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enabled</em>'.
	 * @see com.tibco.be.util.config.cdd.RuleConfig#isEnabled()
	 * @see #getRuleConfig()
	 * @generated
	 */
	EAttribute getRuleConfig_Enabled();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.RuleConfig#getUri <em>Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Uri</em>'.
	 * @see com.tibco.be.util.config.cdd.RuleConfig#getUri()
	 * @see #getRuleConfig()
	 * @generated
	 */
	EAttribute getRuleConfig_Uri();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.RuleConfigConfig <em>Rule Config Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Config Config</em>'.
	 * @see com.tibco.be.util.config.cdd.RuleConfigConfig
	 * @generated
	 */
	EClass getRuleConfigConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.RuleConfigConfig#getClusterMember <em>Cluster Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Cluster Member</em>'.
	 * @see com.tibco.be.util.config.cdd.RuleConfigConfig#getClusterMember()
	 * @see #getRuleConfigConfig()
	 * @generated
	 */
	EReference getRuleConfigConfig_ClusterMember();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.RulesConfig <em>Rules Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rules Config</em>'.
	 * @see com.tibco.be.util.config.cdd.RulesConfig
	 * @generated
	 */
	EClass getRulesConfig();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.util.config.cdd.RulesConfig#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.tibco.be.util.config.cdd.RulesConfig#getGroup()
	 * @see #getRulesConfig()
	 * @generated
	 */
	EAttribute getRulesConfig_Group();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.be.util.config.cdd.RulesConfig#getRef <em>Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ref</em>'.
	 * @see com.tibco.be.util.config.cdd.RulesConfig#getRef()
	 * @see #getRulesConfig()
	 * @generated
	 */
	EReference getRulesConfig_Ref();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.util.config.cdd.RulesConfig#getUri <em>Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Uri</em>'.
	 * @see com.tibco.be.util.config.cdd.RulesConfig#getUri()
	 * @see #getRulesConfig()
	 * @generated
	 */
	EAttribute getRulesConfig_Uri();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.RulesetsConfig <em>Rulesets Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rulesets Config</em>'.
	 * @see com.tibco.be.util.config.cdd.RulesetsConfig
	 * @generated
	 */
	EClass getRulesetsConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.RulesetsConfig#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rules</em>'.
	 * @see com.tibco.be.util.config.cdd.RulesetsConfig#getRules()
	 * @see #getRulesetsConfig()
	 * @generated
	 */
	EReference getRulesetsConfig_Rules();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.SecurityConfig <em>Security Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Security Config</em>'.
	 * @see com.tibco.be.util.config.cdd.SecurityConfig
	 * @generated
	 */
	EClass getSecurityConfig();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.SecurityController <em>Security Controller</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Security Controller</em>'.
	 * @see com.tibco.be.util.config.cdd.SecurityController
	 * @generated
	 */
	EClass getSecurityController();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.SecurityController#getPolicyFile <em>Policy File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Policy File</em>'.
	 * @see com.tibco.be.util.config.cdd.SecurityController#getPolicyFile()
	 * @see #getSecurityController()
	 * @generated
	 */
	EReference getSecurityController_PolicyFile();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.SecurityController#getIdentityPassword <em>Identity Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Identity Password</em>'.
	 * @see com.tibco.be.util.config.cdd.SecurityController#getIdentityPassword()
	 * @see #getSecurityController()
	 * @generated
	 */
	EReference getSecurityController_IdentityPassword();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.SecurityOverrideConfig <em>Security Override Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Security Override Config</em>'.
	 * @see com.tibco.be.util.config.cdd.SecurityOverrideConfig
	 * @generated
	 */
	EClass getSecurityOverrideConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.SecurityOverrideConfig#isOverride <em>Override</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Override</em>'.
	 * @see com.tibco.be.util.config.cdd.SecurityOverrideConfig#isOverride()
	 * @see #getSecurityOverrideConfig()
	 * @generated
	 */
	EAttribute getSecurityOverrideConfig_Override();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.SecurityRequester <em>Security Requester</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Security Requester</em>'.
	 * @see com.tibco.be.util.config.cdd.SecurityRequester
	 * @generated
	 */
	EClass getSecurityRequester();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.SecurityRequester#getTokenFile <em>Token File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Token File</em>'.
	 * @see com.tibco.be.util.config.cdd.SecurityRequester#getTokenFile()
	 * @see #getSecurityRequester()
	 * @generated
	 */
	EReference getSecurityRequester_TokenFile();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.SecurityRequester#getIdentityPassword <em>Identity Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Identity Password</em>'.
	 * @see com.tibco.be.util.config.cdd.SecurityRequester#getIdentityPassword()
	 * @see #getSecurityRequester()
	 * @generated
	 */
	EReference getSecurityRequester_IdentityPassword();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.SecurityRequester#getCertificateKeyFile <em>Certificate Key File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Certificate Key File</em>'.
	 * @see com.tibco.be.util.config.cdd.SecurityRequester#getCertificateKeyFile()
	 * @see #getSecurityRequester()
	 * @generated
	 */
	EReference getSecurityRequester_CertificateKeyFile();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.SecurityRequester#getDomainName <em>Domain Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Domain Name</em>'.
	 * @see com.tibco.be.util.config.cdd.SecurityRequester#getDomainName()
	 * @see #getSecurityRequester()
	 * @generated
	 */
	EReference getSecurityRequester_DomainName();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.SecurityRequester#getUserName <em>User Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>User Name</em>'.
	 * @see com.tibco.be.util.config.cdd.SecurityRequester#getUserName()
	 * @see #getSecurityRequester()
	 * @generated
	 */
	EReference getSecurityRequester_UserName();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.SecurityRequester#getUserPassword <em>User Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>User Password</em>'.
	 * @see com.tibco.be.util.config.cdd.SecurityRequester#getUserPassword()
	 * @see #getSecurityRequester()
	 * @generated
	 */
	EReference getSecurityRequester_UserPassword();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.SetPropertyConfig <em>Set Property Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Set Property Config</em>'.
	 * @see com.tibco.be.util.config.cdd.SetPropertyConfig
	 * @generated
	 */
	EClass getSetPropertyConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.SetPropertyConfig#getChildClusterMember <em>Child Cluster Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Child Cluster Member</em>'.
	 * @see com.tibco.be.util.config.cdd.SetPropertyConfig#getChildClusterMember()
	 * @see #getSetPropertyConfig()
	 * @generated
	 */
	EReference getSetPropertyConfig_ChildClusterMember();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.SetPropertyConfig#getNotification <em>Notification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Notification</em>'.
	 * @see com.tibco.be.util.config.cdd.SetPropertyConfig#getNotification()
	 * @see #getSetPropertyConfig()
	 * @generated
	 */
	EReference getSetPropertyConfig_Notification();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.SetPropertyConfig#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.be.util.config.cdd.SetPropertyConfig#getName()
	 * @see #getSetPropertyConfig()
	 * @generated
	 */
	EAttribute getSetPropertyConfig_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.SetPropertyConfig#getSetPropertyName <em>Set Property Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Set Property Name</em>'.
	 * @see com.tibco.be.util.config.cdd.SetPropertyConfig#getSetPropertyName()
	 * @see #getSetPropertyConfig()
	 * @generated
	 */
	EAttribute getSetPropertyConfig_SetPropertyName();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.SetPropertyConfig#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.tibco.be.util.config.cdd.SetPropertyConfig#getValue()
	 * @see #getSetPropertyConfig()
	 * @generated
	 */
	EAttribute getSetPropertyConfig_Value();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.SharedQueueConfig <em>Shared Queue Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Shared Queue Config</em>'.
	 * @see com.tibco.be.util.config.cdd.SharedQueueConfig
	 * @generated
	 */
	EClass getSharedQueueConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.SharedQueueConfig#getSize <em>Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Size</em>'.
	 * @see com.tibco.be.util.config.cdd.SharedQueueConfig#getSize()
	 * @see #getSharedQueueConfig()
	 * @generated
	 */
	EReference getSharedQueueConfig_Size();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.SharedQueueConfig#getWorkers <em>Workers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Workers</em>'.
	 * @see com.tibco.be.util.config.cdd.SharedQueueConfig#getWorkers()
	 * @see #getSharedQueueConfig()
	 * @generated
	 */
	EReference getSharedQueueConfig_Workers();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.SslConfig <em>Ssl Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ssl Config</em>'.
	 * @see com.tibco.be.util.config.cdd.SslConfig
	 * @generated
	 */
	EClass getSslConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.SslConfig#getProtocols <em>Protocols</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Protocols</em>'.
	 * @see com.tibco.be.util.config.cdd.SslConfig#getProtocols()
	 * @see #getSslConfig()
	 * @generated
	 */
	EReference getSslConfig_Protocols();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.SslConfig#getCiphers <em>Ciphers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Ciphers</em>'.
	 * @see com.tibco.be.util.config.cdd.SslConfig#getCiphers()
	 * @see #getSslConfig()
	 * @generated
	 */
	EReference getSslConfig_Ciphers();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.SslConfig#getKeyManagerAlgorithm <em>Key Manager Algorithm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Key Manager Algorithm</em>'.
	 * @see com.tibco.be.util.config.cdd.SslConfig#getKeyManagerAlgorithm()
	 * @see #getSslConfig()
	 * @generated
	 */
	EReference getSslConfig_KeyManagerAlgorithm();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.SslConfig#getTrustManagerAlgorithm <em>Trust Manager Algorithm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Trust Manager Algorithm</em>'.
	 * @see com.tibco.be.util.config.cdd.SslConfig#getTrustManagerAlgorithm()
	 * @see #getSslConfig()
	 * @generated
	 */
	EReference getSslConfig_TrustManagerAlgorithm();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.SystemPropertyConfig <em>System Property Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>System Property Config</em>'.
	 * @see com.tibco.be.util.config.cdd.SystemPropertyConfig
	 * @generated
	 */
	EClass getSystemPropertyConfig();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.util.config.cdd.SystemPropertyConfig#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.tibco.be.util.config.cdd.SystemPropertyConfig#getMixed()
	 * @see #getSystemPropertyConfig()
	 * @generated
	 */
	EAttribute getSystemPropertyConfig_Mixed();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.SystemPropertyConfig#getSystemProperty <em>System Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>System Property</em>'.
	 * @see com.tibco.be.util.config.cdd.SystemPropertyConfig#getSystemProperty()
	 * @see #getSystemPropertyConfig()
	 * @generated
	 */
	EAttribute getSystemPropertyConfig_SystemProperty();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.TerminalConfig <em>Terminal Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Terminal Config</em>'.
	 * @see com.tibco.be.util.config.cdd.TerminalConfig
	 * @generated
	 */
	EClass getTerminalConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.TerminalConfig#getEnabled <em>Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Enabled</em>'.
	 * @see com.tibco.be.util.config.cdd.TerminalConfig#getEnabled()
	 * @see #getTerminalConfig()
	 * @generated
	 */
	EReference getTerminalConfig_Enabled();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.TerminalConfig#getSysErrRedirect <em>Sys Err Redirect</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Sys Err Redirect</em>'.
	 * @see com.tibco.be.util.config.cdd.TerminalConfig#getSysErrRedirect()
	 * @see #getTerminalConfig()
	 * @generated
	 */
	EReference getTerminalConfig_SysErrRedirect();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.TerminalConfig#getSysOutRedirect <em>Sys Out Redirect</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Sys Out Redirect</em>'.
	 * @see com.tibco.be.util.config.cdd.TerminalConfig#getSysOutRedirect()
	 * @see #getTerminalConfig()
	 * @generated
	 */
	EReference getTerminalConfig_SysOutRedirect();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.TerminalConfig#getEncoding <em>Encoding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Encoding</em>'.
	 * @see com.tibco.be.util.config.cdd.TerminalConfig#getEncoding()
	 * @see #getTerminalConfig()
	 * @generated
	 */
	EReference getTerminalConfig_Encoding();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.UrisAndRefsConfig <em>Uris And Refs Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Uris And Refs Config</em>'.
	 * @see com.tibco.be.util.config.cdd.UrisAndRefsConfig
	 * @generated
	 */
	EClass getUrisAndRefsConfig();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.UrisConfig <em>Uris Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Uris Config</em>'.
	 * @see com.tibco.be.util.config.cdd.UrisConfig
	 * @generated
	 */
	EClass getUrisConfig();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.util.config.cdd.UrisConfig#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.tibco.be.util.config.cdd.UrisConfig#getGroup()
	 * @see #getUrisConfig()
	 * @generated
	 */
	EAttribute getUrisConfig_Group();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.util.config.cdd.UrisConfig#getUri <em>Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Uri</em>'.
	 * @see com.tibco.be.util.config.cdd.UrisConfig#getUri()
	 * @see #getUrisConfig()
	 * @generated
	 */
	EAttribute getUrisConfig_Uri();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.FieldEncryptionConfig <em>Field Encryption Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Field Encryption Config</em>'.
	 * @see com.tibco.be.util.config.cdd.FieldEncryptionConfig
	 * @generated
	 */
	EClass getFieldEncryptionConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.FieldEncryptionConfig#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Property</em>'.
	 * @see com.tibco.be.util.config.cdd.FieldEncryptionConfig#getProperty()
	 * @see #getFieldEncryptionConfig()
	 * @generated
	 */
	EReference getFieldEncryptionConfig_Property();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.EntityConfig <em>Entity Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity Config</em>'.
	 * @see com.tibco.be.util.config.cdd.EntityConfig
	 * @generated
	 */
	EClass getEntityConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.cdd.EntityConfig#getUri <em>Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Uri</em>'.
	 * @see com.tibco.be.util.config.cdd.EntityConfig#getUri()
	 * @see #getEntityConfig()
	 * @generated
	 */
	EAttribute getEntityConfig_Uri();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.EntityConfig#getFilter <em>Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Filter</em>'.
	 * @see com.tibco.be.util.config.cdd.EntityConfig#getFilter()
	 * @see #getEntityConfig()
	 * @generated
	 */
	EReference getEntityConfig_Filter();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.EntityConfig#getEnableTableTrimming <em>Enable Table Trimming</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Enable Table Trimming</em>'.
	 * @see com.tibco.be.util.config.cdd.EntityConfig#getEnableTableTrimming()
	 * @see #getEntityConfig()
	 * @generated
	 */
	EReference getEntityConfig_EnableTableTrimming();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.EntityConfig#getTrimmingField <em>Trimming Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Trimming Field</em>'.
	 * @see com.tibco.be.util.config.cdd.EntityConfig#getTrimmingField()
	 * @see #getEntityConfig()
	 * @generated
	 */
	EReference getEntityConfig_TrimmingField();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.EntityConfig#getTrimmingRule <em>Trimming Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Trimming Rule</em>'.
	 * @see com.tibco.be.util.config.cdd.EntityConfig#getTrimmingRule()
	 * @see #getEntityConfig()
	 * @generated
	 */
	EReference getEntityConfig_TrimmingRule();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.EntitySetConfig <em>Entity Set Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity Set Config</em>'.
	 * @see com.tibco.be.util.config.cdd.EntitySetConfig
	 * @generated
	 */
	EClass getEntitySetConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.EntitySetConfig#getEntity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entity</em>'.
	 * @see com.tibco.be.util.config.cdd.EntitySetConfig#getEntity()
	 * @see #getEntitySetConfig()
	 * @generated
	 */
	EReference getEntitySetConfig_Entity();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.EntitySetConfig#getGenerateLVFiles <em>Generate LV Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Generate LV Files</em>'.
	 * @see com.tibco.be.util.config.cdd.EntitySetConfig#getGenerateLVFiles()
	 * @see #getEntitySetConfig()
	 * @generated
	 */
	EReference getEntitySetConfig_GenerateLVFiles();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.EntitySetConfig#getOutputPath <em>Output Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Output Path</em>'.
	 * @see com.tibco.be.util.config.cdd.EntitySetConfig#getOutputPath()
	 * @see #getEntitySetConfig()
	 * @generated
	 */
	EReference getEntitySetConfig_OutputPath();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.LDMConnectionConfig <em>LDM Connection Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>LDM Connection Config</em>'.
	 * @see com.tibco.be.util.config.cdd.LDMConnectionConfig
	 * @generated
	 */
	EClass getLDMConnectionConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LDMConnectionConfig#getLdmUrl <em>Ldm Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Ldm Url</em>'.
	 * @see com.tibco.be.util.config.cdd.LDMConnectionConfig#getLdmUrl()
	 * @see #getLDMConnectionConfig()
	 * @generated
	 */
	EReference getLDMConnectionConfig_LdmUrl();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LDMConnectionConfig#getUserName <em>User Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>User Name</em>'.
	 * @see com.tibco.be.util.config.cdd.LDMConnectionConfig#getUserName()
	 * @see #getLDMConnectionConfig()
	 * @generated
	 */
	EReference getLDMConnectionConfig_UserName();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LDMConnectionConfig#getUserPassword <em>User Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>User Password</em>'.
	 * @see com.tibco.be.util.config.cdd.LDMConnectionConfig#getUserPassword()
	 * @see #getLDMConnectionConfig()
	 * @generated
	 */
	EReference getLDMConnectionConfig_UserPassword();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LDMConnectionConfig#getInitialSize <em>Initial Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Initial Size</em>'.
	 * @see com.tibco.be.util.config.cdd.LDMConnectionConfig#getInitialSize()
	 * @see #getLDMConnectionConfig()
	 * @generated
	 */
	EReference getLDMConnectionConfig_InitialSize();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LDMConnectionConfig#getMinSize <em>Min Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Min Size</em>'.
	 * @see com.tibco.be.util.config.cdd.LDMConnectionConfig#getMinSize()
	 * @see #getLDMConnectionConfig()
	 * @generated
	 */
	EReference getLDMConnectionConfig_MinSize();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LDMConnectionConfig#getMaxSize <em>Max Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Max Size</em>'.
	 * @see com.tibco.be.util.config.cdd.LDMConnectionConfig#getMaxSize()
	 * @see #getLDMConnectionConfig()
	 * @generated
	 */
	EReference getLDMConnectionConfig_MaxSize();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.PublisherConfig <em>Publisher Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Publisher Config</em>'.
	 * @see com.tibco.be.util.config.cdd.PublisherConfig
	 * @generated
	 */
	EClass getPublisherConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.PublisherConfig#getPublisherQueueSize <em>Publisher Queue Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Publisher Queue Size</em>'.
	 * @see com.tibco.be.util.config.cdd.PublisherConfig#getPublisherQueueSize()
	 * @see #getPublisherConfig()
	 * @generated
	 */
	EReference getPublisherConfig_PublisherQueueSize();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.PublisherConfig#getPublisherThreadCount <em>Publisher Thread Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Publisher Thread Count</em>'.
	 * @see com.tibco.be.util.config.cdd.PublisherConfig#getPublisherThreadCount()
	 * @see #getPublisherConfig()
	 * @generated
	 */
	EReference getPublisherConfig_PublisherThreadCount();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.LiveViewAgentClassConfig <em>Live View Agent Class Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Live View Agent Class Config</em>'.
	 * @see com.tibco.be.util.config.cdd.LiveViewAgentClassConfig
	 * @generated
	 */
	EClass getLiveViewAgentClassConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LiveViewAgentClassConfig#getLdmConnection <em>Ldm Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Ldm Connection</em>'.
	 * @see com.tibco.be.util.config.cdd.LiveViewAgentClassConfig#getLdmConnection()
	 * @see #getLiveViewAgentClassConfig()
	 * @generated
	 */
	EReference getLiveViewAgentClassConfig_LdmConnection();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LiveViewAgentClassConfig#getPublisher <em>Publisher</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Publisher</em>'.
	 * @see com.tibco.be.util.config.cdd.LiveViewAgentClassConfig#getPublisher()
	 * @see #getLiveViewAgentClassConfig()
	 * @generated
	 */
	EReference getLiveViewAgentClassConfig_Publisher();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LiveViewAgentClassConfig#getEntitySet <em>Entity Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Entity Set</em>'.
	 * @see com.tibco.be.util.config.cdd.LiveViewAgentClassConfig#getEntitySet()
	 * @see #getLiveViewAgentClassConfig()
	 * @generated
	 */
	EReference getLiveViewAgentClassConfig_EntitySet();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LiveViewAgentClassConfig#getLoad <em>Load</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Load</em>'.
	 * @see com.tibco.be.util.config.cdd.LiveViewAgentClassConfig#getLoad()
	 * @see #getLiveViewAgentClassConfig()
	 * @generated
	 */
	EReference getLiveViewAgentClassConfig_Load();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.LiveViewAgentClassConfig#getPropertyGroup <em>Property Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Property Group</em>'.
	 * @see com.tibco.be.util.config.cdd.LiveViewAgentClassConfig#getPropertyGroup()
	 * @see #getLiveViewAgentClassConfig()
	 * @generated
	 */
	EReference getLiveViewAgentClassConfig_PropertyGroup();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.CompositeIndexPropertiesConfig <em>Composite Index Properties Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Composite Index Properties Config</em>'.
	 * @see com.tibco.be.util.config.cdd.CompositeIndexPropertiesConfig
	 * @generated
	 */
	EClass getCompositeIndexPropertiesConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.CompositeIndexPropertiesConfig#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Property</em>'.
	 * @see com.tibco.be.util.config.cdd.CompositeIndexPropertiesConfig#getProperty()
	 * @see #getCompositeIndexPropertiesConfig()
	 * @generated
	 */
	EReference getCompositeIndexPropertiesConfig_Property();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.CompositeIndexConfig <em>Composite Index Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Composite Index Config</em>'.
	 * @see com.tibco.be.util.config.cdd.CompositeIndexConfig
	 * @generated
	 */
	EClass getCompositeIndexConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CompositeIndexConfig#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Name</em>'.
	 * @see com.tibco.be.util.config.cdd.CompositeIndexConfig#getName()
	 * @see #getCompositeIndexConfig()
	 * @generated
	 */
	EReference getCompositeIndexConfig_Name();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.cdd.CompositeIndexConfig#getCompositeIndexProperties <em>Composite Index Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Composite Index Properties</em>'.
	 * @see com.tibco.be.util.config.cdd.CompositeIndexConfig#getCompositeIndexProperties()
	 * @see #getCompositeIndexConfig()
	 * @generated
	 */
	EReference getCompositeIndexConfig_CompositeIndexProperties();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.cdd.CompositeIndexesConfig <em>Composite Indexes Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Composite Indexes Config</em>'.
	 * @see com.tibco.be.util.config.cdd.CompositeIndexesConfig
	 * @generated
	 */
	EClass getCompositeIndexesConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.cdd.CompositeIndexesConfig#getCompositeIndex <em>Composite Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Composite Index</em>'.
	 * @see com.tibco.be.util.config.cdd.CompositeIndexesConfig#getCompositeIndex()
	 * @see #getCompositeIndexesConfig()
	 * @generated
	 */
	EReference getCompositeIndexesConfig_CompositeIndex();

	/**
	 * Returns the meta object for enum '{@link com.tibco.be.util.config.cdd.DomainObjectModeConfig <em>Domain Object Mode Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Domain Object Mode Config</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectModeConfig
	 * @generated
	 */
	EEnum getDomainObjectModeConfig();

	/**
	 * Returns the meta object for enum '{@link com.tibco.be.util.config.cdd.ThreadingModelConfig <em>Threading Model Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Threading Model Config</em>'.
	 * @see com.tibco.be.util.config.cdd.ThreadingModelConfig
	 * @generated
	 */
	EEnum getThreadingModelConfig();

	/**
	 * Returns the meta object for data type '{@link com.tibco.be.util.config.cdd.DomainObjectModeConfig <em>Domain Object Mode Config Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Domain Object Mode Config Object</em>'.
	 * @see com.tibco.be.util.config.cdd.DomainObjectModeConfig
	 * @model instanceClass="com.tibco.be.util.config.cdd.DomainObjectModeConfig"
	 *        extendedMetaData="name='domain-object-mode-type:Object' baseType='domain-object-mode-type'"
	 * @generated
	 */
	EDataType getDomainObjectModeConfigObject();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Ontology Uri Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Ontology Uri Config</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='ontology-uri-type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'"
	 * @generated
	 */
	EDataType getOntologyUriConfig();

	/**
	 * Returns the meta object for data type '{@link com.tibco.be.util.config.cdd.ThreadingModelConfig <em>Threading Model Config Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Threading Model Config Object</em>'.
	 * @see com.tibco.be.util.config.cdd.ThreadingModelConfig
	 * @model instanceClass="com.tibco.be.util.config.cdd.ThreadingModelConfig"
	 *        extendedMetaData="name='threading-model-type:Object' baseType='threading-model-type'"
	 * @generated
	 */
	EDataType getThreadingModelConfigObject();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CddFactory getCddFactory();

} //CddPackage
