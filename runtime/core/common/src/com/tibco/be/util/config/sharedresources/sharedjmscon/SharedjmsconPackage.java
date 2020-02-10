/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedjmscon;

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
 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconFactory
 * @model kind="package"
 *        annotation="http://www.w3.org/XML/1998/namespace lang='en'"
 *        extendedMetaData="qualified='false'"
 * @generated
 */
public interface SharedjmsconPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "sharedjmscon";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/resource/BE/runtime/core/common/src/sharedjmscon.xsd";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "sharedjmscon";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SharedjmsconPackage eINSTANCE = com.tibco.be.util.config.sharedresources.sharedjmscon.impl.SharedjmsconPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.BwSharedResourceImpl <em>Bw Shared Resource</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.BwSharedResourceImpl
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.SharedjmsconPackageImpl#getBwSharedResource()
	 * @generated
	 */
	int BW_SHARED_RESOURCE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BW_SHARED_RESOURCE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Resource Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BW_SHARED_RESOURCE__RESOURCE_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BW_SHARED_RESOURCE__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BW_SHARED_RESOURCE__CONFIG = 3;

	/**
	 * The number of structural features of the '<em>Bw Shared Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BW_SHARED_RESOURCE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.ConfigImpl <em>Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.ConfigImpl
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.SharedjmsconPackageImpl#getConfig()
	 * @generated
	 */
	int CONFIG = 1;

	/**
	 * The feature id for the '<em><b>Naming Environment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG__NAMING_ENVIRONMENT = 0;

	/**
	 * The feature id for the '<em><b>Connection Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG__CONNECTION_ATTRIBUTES = 1;

	/**
	 * The feature id for the '<em><b>Use Xacf</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG__USE_XACF = 2;

	/**
	 * The feature id for the '<em><b>Use Ssl</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG__USE_SSL = 3;

	/**
	 * The feature id for the '<em><b>Use Shared Jndi Config</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG__USE_SHARED_JNDI_CONFIG = 4;

	/**
	 * The feature id for the '<em><b>Adm Factory Ssl Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG__ADM_FACTORY_SSL_PASSWORD = 5;

	/**
	 * The feature id for the '<em><b>Ssl</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG__SSL = 6;

	/**
	 * The feature id for the '<em><b>Jndi Shared Configuration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG__JNDI_SHARED_CONFIGURATION = 7;

	/**
	 * The feature id for the '<em><b>Jndi Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG__JNDI_PROPERTIES = 8;

	/**
	 * The number of structural features of the '<em>Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_FEATURE_COUNT = 9;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.ConnectionAttributesImpl <em>Connection Attributes</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.ConnectionAttributesImpl
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.SharedjmsconPackageImpl#getConnectionAttributes()
	 * @generated
	 */
	int CONNECTION_ATTRIBUTES = 2;

	/**
	 * The feature id for the '<em><b>Username</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ATTRIBUTES__USERNAME = 0;

	/**
	 * The feature id for the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ATTRIBUTES__PASSWORD = 1;

	/**
	 * The feature id for the '<em><b>Client Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ATTRIBUTES__CLIENT_ID = 2;

	/**
	 * The feature id for the '<em><b>Auto Gen Client ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ATTRIBUTES__AUTO_GEN_CLIENT_ID = 3;

	/**
	 * The number of structural features of the '<em>Connection Attributes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ATTRIBUTES_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.JndiPropertiesImpl <em>Jndi Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.JndiPropertiesImpl
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.SharedjmsconPackageImpl#getJndiProperties()
	 * @generated
	 */
	int JNDI_PROPERTIES = 3;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNDI_PROPERTIES__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Row</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNDI_PROPERTIES__ROW = 1;

	/**
	 * The number of structural features of the '<em>Jndi Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JNDI_PROPERTIES_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.NamingEnvironmentImpl <em>Naming Environment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.NamingEnvironmentImpl
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.SharedjmsconPackageImpl#getNamingEnvironment()
	 * @generated
	 */
	int NAMING_ENVIRONMENT = 4;

	/**
	 * The feature id for the '<em><b>Use Jndi</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMING_ENVIRONMENT__USE_JNDI = 0;

	/**
	 * The feature id for the '<em><b>Provider Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMING_ENVIRONMENT__PROVIDER_URL = 1;

	/**
	 * The feature id for the '<em><b>Naming Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMING_ENVIRONMENT__NAMING_URL = 2;

	/**
	 * The feature id for the '<em><b>Naming Initial Context Factory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMING_ENVIRONMENT__NAMING_INITIAL_CONTEXT_FACTORY = 3;

	/**
	 * The feature id for the '<em><b>Topic Factory Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMING_ENVIRONMENT__TOPIC_FACTORY_NAME = 4;

	/**
	 * The feature id for the '<em><b>Queue Factory Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMING_ENVIRONMENT__QUEUE_FACTORY_NAME = 5;

	/**
	 * The feature id for the '<em><b>Naming Principal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMING_ENVIRONMENT__NAMING_PRINCIPAL = 6;

	/**
	 * The feature id for the '<em><b>Naming Credential</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMING_ENVIRONMENT__NAMING_CREDENTIAL = 7;

	/**
	 * The number of structural features of the '<em>Naming Environment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMING_ENVIRONMENT_FEATURE_COUNT = 8;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.RowImpl <em>Row</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.RowImpl
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.SharedjmsconPackageImpl#getRow()
	 * @generated
	 */
	int ROW = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROW__NAME = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROW__TYPE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROW__VALUE = 2;

	/**
	 * The number of structural features of the '<em>Row</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROW_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.SharedJmsConRootImpl <em>Shared Jms Con Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.SharedJmsConRootImpl
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.SharedjmsconPackageImpl#getSharedJmsConRoot()
	 * @generated
	 */
	int SHARED_JMS_CON_ROOT = 6;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JMS_CON_ROOT__MIXED = 0;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JMS_CON_ROOT__XMLNS_PREFIX_MAP = 1;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JMS_CON_ROOT__XSI_SCHEMA_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>BW Shared Resource</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JMS_CON_ROOT__BW_SHARED_RESOURCE = 3;

	/**
	 * The number of structural features of the '<em>Shared Jms Con Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JMS_CON_ROOT_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.ResourceType <em>Resource Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.ResourceType
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.SharedjmsconPackageImpl#getResourceType()
	 * @generated
	 */
	int RESOURCE_TYPE = 7;

	/**
	 * The meta object id for the '<em>Resource Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.ResourceType
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.SharedjmsconPackageImpl#getResourceTypeObject()
	 * @generated
	 */
	int RESOURCE_TYPE_OBJECT = 8;


	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.BwSharedResource <em>Bw Shared Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bw Shared Resource</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.BwSharedResource
	 * @generated
	 */
	EClass getBwSharedResource();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.BwSharedResource#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.BwSharedResource#getName()
	 * @see #getBwSharedResource()
	 * @generated
	 */
	EAttribute getBwSharedResource_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.BwSharedResource#getResourceType <em>Resource Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Resource Type</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.BwSharedResource#getResourceType()
	 * @see #getBwSharedResource()
	 * @generated
	 */
	EAttribute getBwSharedResource_ResourceType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.BwSharedResource#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.BwSharedResource#getDescription()
	 * @see #getBwSharedResource()
	 * @generated
	 */
	EAttribute getBwSharedResource_Description();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.BwSharedResource#getConfig <em>Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Config</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.BwSharedResource#getConfig()
	 * @see #getBwSharedResource()
	 * @generated
	 */
	EReference getBwSharedResource_Config();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config <em>Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Config</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.Config
	 * @generated
	 */
	EClass getConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getNamingEnvironment <em>Naming Environment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Naming Environment</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getNamingEnvironment()
	 * @see #getConfig()
	 * @generated
	 */
	EReference getConfig_NamingEnvironment();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getConnectionAttributes <em>Connection Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Connection Attributes</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getConnectionAttributes()
	 * @see #getConfig()
	 * @generated
	 */
	EReference getConfig_ConnectionAttributes();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getUseXacf <em>Use Xacf</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Use Xacf</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getUseXacf()
	 * @see #getConfig()
	 * @generated
	 */
	EAttribute getConfig_UseXacf();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getUseSsl <em>Use Ssl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Use Ssl</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getUseSsl()
	 * @see #getConfig()
	 * @generated
	 */
	EAttribute getConfig_UseSsl();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getUseSharedJndiConfig <em>Use Shared Jndi Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Use Shared Jndi Config</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getUseSharedJndiConfig()
	 * @see #getConfig()
	 * @generated
	 */
	EAttribute getConfig_UseSharedJndiConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getAdmFactorySslPassword <em>Adm Factory Ssl Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Adm Factory Ssl Password</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getAdmFactorySslPassword()
	 * @see #getConfig()
	 * @generated
	 */
	EAttribute getConfig_AdmFactorySslPassword();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getSsl <em>Ssl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Ssl</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getSsl()
	 * @see #getConfig()
	 * @generated
	 */
	EReference getConfig_Ssl();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getJndiSharedConfiguration <em>Jndi Shared Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Jndi Shared Configuration</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getJndiSharedConfiguration()
	 * @see #getConfig()
	 * @generated
	 */
	EAttribute getConfig_JndiSharedConfiguration();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getJndiProperties <em>Jndi Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Jndi Properties</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getJndiProperties()
	 * @see #getConfig()
	 * @generated
	 */
	EReference getConfig_JndiProperties();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.ConnectionAttributes <em>Connection Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connection Attributes</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.ConnectionAttributes
	 * @generated
	 */
	EClass getConnectionAttributes();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.ConnectionAttributes#getUsername <em>Username</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Username</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.ConnectionAttributes#getUsername()
	 * @see #getConnectionAttributes()
	 * @generated
	 */
	EAttribute getConnectionAttributes_Username();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.ConnectionAttributes#getPassword <em>Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Password</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.ConnectionAttributes#getPassword()
	 * @see #getConnectionAttributes()
	 * @generated
	 */
	EAttribute getConnectionAttributes_Password();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.ConnectionAttributes#getClientId <em>Client Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Client Id</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.ConnectionAttributes#getClientId()
	 * @see #getConnectionAttributes()
	 * @generated
	 */
	EAttribute getConnectionAttributes_ClientId();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.ConnectionAttributes#getAutoGenClientID <em>Auto Gen Client ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Auto Gen Client ID</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.ConnectionAttributes#getAutoGenClientID()
	 * @see #getConnectionAttributes()
	 * @generated
	 */
	EAttribute getConnectionAttributes_AutoGenClientID();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.JndiProperties <em>Jndi Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Jndi Properties</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.JndiProperties
	 * @generated
	 */
	EClass getJndiProperties();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.JndiProperties#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.JndiProperties#getGroup()
	 * @see #getJndiProperties()
	 * @generated
	 */
	EAttribute getJndiProperties_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.JndiProperties#getRow <em>Row</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Row</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.JndiProperties#getRow()
	 * @see #getJndiProperties()
	 * @generated
	 */
	EReference getJndiProperties_Row();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment <em>Naming Environment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Naming Environment</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment
	 * @generated
	 */
	EClass getNamingEnvironment();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getUseJndi <em>Use Jndi</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Use Jndi</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getUseJndi()
	 * @see #getNamingEnvironment()
	 * @generated
	 */
	EAttribute getNamingEnvironment_UseJndi();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getProviderUrl <em>Provider Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Provider Url</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getProviderUrl()
	 * @see #getNamingEnvironment()
	 * @generated
	 */
	EAttribute getNamingEnvironment_ProviderUrl();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getNamingUrl <em>Naming Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Naming Url</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getNamingUrl()
	 * @see #getNamingEnvironment()
	 * @generated
	 */
	EAttribute getNamingEnvironment_NamingUrl();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getNamingInitialContextFactory <em>Naming Initial Context Factory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Naming Initial Context Factory</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getNamingInitialContextFactory()
	 * @see #getNamingEnvironment()
	 * @generated
	 */
	EAttribute getNamingEnvironment_NamingInitialContextFactory();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getTopicFactoryName <em>Topic Factory Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Topic Factory Name</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getTopicFactoryName()
	 * @see #getNamingEnvironment()
	 * @generated
	 */
	EAttribute getNamingEnvironment_TopicFactoryName();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getQueueFactoryName <em>Queue Factory Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Queue Factory Name</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getQueueFactoryName()
	 * @see #getNamingEnvironment()
	 * @generated
	 */
	EAttribute getNamingEnvironment_QueueFactoryName();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getNamingPrincipal <em>Naming Principal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Naming Principal</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getNamingPrincipal()
	 * @see #getNamingEnvironment()
	 * @generated
	 */
	EAttribute getNamingEnvironment_NamingPrincipal();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getNamingCredential <em>Naming Credential</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Naming Credential</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getNamingCredential()
	 * @see #getNamingEnvironment()
	 * @generated
	 */
	EAttribute getNamingEnvironment_NamingCredential();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Row <em>Row</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Row</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.Row
	 * @generated
	 */
	EClass getRow();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Row#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.Row#getName()
	 * @see #getRow()
	 * @generated
	 */
	EAttribute getRow_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Row#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.Row#getType()
	 * @see #getRow()
	 * @generated
	 */
	EAttribute getRow_Type();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Row#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.Row#getValue()
	 * @see #getRow()
	 * @generated
	 */
	EAttribute getRow_Value();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.SharedJmsConRoot <em>Shared Jms Con Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Shared Jms Con Root</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedJmsConRoot
	 * @generated
	 */
	EClass getSharedJmsConRoot();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.SharedJmsConRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedJmsConRoot#getMixed()
	 * @see #getSharedJmsConRoot()
	 * @generated
	 */
	EAttribute getSharedJmsConRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.SharedJmsConRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedJmsConRoot#getXMLNSPrefixMap()
	 * @see #getSharedJmsConRoot()
	 * @generated
	 */
	EReference getSharedJmsConRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.SharedJmsConRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedJmsConRoot#getXSISchemaLocation()
	 * @see #getSharedJmsConRoot()
	 * @generated
	 */
	EReference getSharedJmsConRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.SharedJmsConRoot#getBWSharedResource <em>BW Shared Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>BW Shared Resource</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedJmsConRoot#getBWSharedResource()
	 * @see #getSharedJmsConRoot()
	 * @generated
	 */
	EReference getSharedJmsConRoot_BWSharedResource();

	/**
	 * Returns the meta object for enum '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.ResourceType <em>Resource Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Resource Type</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.ResourceType
	 * @generated
	 */
	EEnum getResourceType();

	/**
	 * Returns the meta object for data type '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.ResourceType <em>Resource Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Resource Type Object</em>'.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.ResourceType
	 * @model instanceClass="com.tibco.be.util.config.sharedresources.sharedjmscon.ResourceType"
	 *        extendedMetaData="name='resourceType_._type:Object' baseType='resourceType_._type'"
	 * @generated
	 */
	EDataType getResourceTypeObject();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SharedjmsconFactory getSharedjmsconFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.BwSharedResourceImpl <em>Bw Shared Resource</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.BwSharedResourceImpl
		 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.SharedjmsconPackageImpl#getBwSharedResource()
		 * @generated
		 */
		EClass BW_SHARED_RESOURCE = eINSTANCE.getBwSharedResource();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BW_SHARED_RESOURCE__NAME = eINSTANCE.getBwSharedResource_Name();

		/**
		 * The meta object literal for the '<em><b>Resource Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BW_SHARED_RESOURCE__RESOURCE_TYPE = eINSTANCE.getBwSharedResource_ResourceType();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BW_SHARED_RESOURCE__DESCRIPTION = eINSTANCE.getBwSharedResource_Description();

		/**
		 * The meta object literal for the '<em><b>Config</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BW_SHARED_RESOURCE__CONFIG = eINSTANCE.getBwSharedResource_Config();

		/**
		 * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.ConfigImpl <em>Config</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.ConfigImpl
		 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.SharedjmsconPackageImpl#getConfig()
		 * @generated
		 */
		EClass CONFIG = eINSTANCE.getConfig();

		/**
		 * The meta object literal for the '<em><b>Naming Environment</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIG__NAMING_ENVIRONMENT = eINSTANCE.getConfig_NamingEnvironment();

		/**
		 * The meta object literal for the '<em><b>Connection Attributes</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIG__CONNECTION_ATTRIBUTES = eINSTANCE.getConfig_ConnectionAttributes();

		/**
		 * The meta object literal for the '<em><b>Use Xacf</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIG__USE_XACF = eINSTANCE.getConfig_UseXacf();

		/**
		 * The meta object literal for the '<em><b>Use Ssl</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIG__USE_SSL = eINSTANCE.getConfig_UseSsl();

		/**
		 * The meta object literal for the '<em><b>Use Shared Jndi Config</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIG__USE_SHARED_JNDI_CONFIG = eINSTANCE.getConfig_UseSharedJndiConfig();

		/**
		 * The meta object literal for the '<em><b>Adm Factory Ssl Password</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIG__ADM_FACTORY_SSL_PASSWORD = eINSTANCE.getConfig_AdmFactorySslPassword();

		/**
		 * The meta object literal for the '<em><b>Ssl</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIG__SSL = eINSTANCE.getConfig_Ssl();

		/**
		 * The meta object literal for the '<em><b>Jndi Shared Configuration</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIG__JNDI_SHARED_CONFIGURATION = eINSTANCE.getConfig_JndiSharedConfiguration();

		/**
		 * The meta object literal for the '<em><b>Jndi Properties</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIG__JNDI_PROPERTIES = eINSTANCE.getConfig_JndiProperties();

		/**
		 * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.ConnectionAttributesImpl <em>Connection Attributes</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.ConnectionAttributesImpl
		 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.SharedjmsconPackageImpl#getConnectionAttributes()
		 * @generated
		 */
		EClass CONNECTION_ATTRIBUTES = eINSTANCE.getConnectionAttributes();

		/**
		 * The meta object literal for the '<em><b>Username</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTION_ATTRIBUTES__USERNAME = eINSTANCE.getConnectionAttributes_Username();

		/**
		 * The meta object literal for the '<em><b>Password</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTION_ATTRIBUTES__PASSWORD = eINSTANCE.getConnectionAttributes_Password();

		/**
		 * The meta object literal for the '<em><b>Client Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTION_ATTRIBUTES__CLIENT_ID = eINSTANCE.getConnectionAttributes_ClientId();

		/**
		 * The meta object literal for the '<em><b>Auto Gen Client ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTION_ATTRIBUTES__AUTO_GEN_CLIENT_ID = eINSTANCE.getConnectionAttributes_AutoGenClientID();

		/**
		 * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.JndiPropertiesImpl <em>Jndi Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.JndiPropertiesImpl
		 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.SharedjmsconPackageImpl#getJndiProperties()
		 * @generated
		 */
		EClass JNDI_PROPERTIES = eINSTANCE.getJndiProperties();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JNDI_PROPERTIES__GROUP = eINSTANCE.getJndiProperties_Group();

		/**
		 * The meta object literal for the '<em><b>Row</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JNDI_PROPERTIES__ROW = eINSTANCE.getJndiProperties_Row();

		/**
		 * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.NamingEnvironmentImpl <em>Naming Environment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.NamingEnvironmentImpl
		 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.SharedjmsconPackageImpl#getNamingEnvironment()
		 * @generated
		 */
		EClass NAMING_ENVIRONMENT = eINSTANCE.getNamingEnvironment();

		/**
		 * The meta object literal for the '<em><b>Use Jndi</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMING_ENVIRONMENT__USE_JNDI = eINSTANCE.getNamingEnvironment_UseJndi();

		/**
		 * The meta object literal for the '<em><b>Provider Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMING_ENVIRONMENT__PROVIDER_URL = eINSTANCE.getNamingEnvironment_ProviderUrl();

		/**
		 * The meta object literal for the '<em><b>Naming Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMING_ENVIRONMENT__NAMING_URL = eINSTANCE.getNamingEnvironment_NamingUrl();

		/**
		 * The meta object literal for the '<em><b>Naming Initial Context Factory</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMING_ENVIRONMENT__NAMING_INITIAL_CONTEXT_FACTORY = eINSTANCE.getNamingEnvironment_NamingInitialContextFactory();

		/**
		 * The meta object literal for the '<em><b>Topic Factory Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMING_ENVIRONMENT__TOPIC_FACTORY_NAME = eINSTANCE.getNamingEnvironment_TopicFactoryName();

		/**
		 * The meta object literal for the '<em><b>Queue Factory Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMING_ENVIRONMENT__QUEUE_FACTORY_NAME = eINSTANCE.getNamingEnvironment_QueueFactoryName();

		/**
		 * The meta object literal for the '<em><b>Naming Principal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMING_ENVIRONMENT__NAMING_PRINCIPAL = eINSTANCE.getNamingEnvironment_NamingPrincipal();

		/**
		 * The meta object literal for the '<em><b>Naming Credential</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMING_ENVIRONMENT__NAMING_CREDENTIAL = eINSTANCE.getNamingEnvironment_NamingCredential();

		/**
		 * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.RowImpl <em>Row</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.RowImpl
		 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.SharedjmsconPackageImpl#getRow()
		 * @generated
		 */
		EClass ROW = eINSTANCE.getRow();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROW__NAME = eINSTANCE.getRow_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROW__TYPE = eINSTANCE.getRow_Type();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROW__VALUE = eINSTANCE.getRow_Value();

		/**
		 * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.SharedJmsConRootImpl <em>Shared Jms Con Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.SharedJmsConRootImpl
		 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.SharedjmsconPackageImpl#getSharedJmsConRoot()
		 * @generated
		 */
		EClass SHARED_JMS_CON_ROOT = eINSTANCE.getSharedJmsConRoot();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHARED_JMS_CON_ROOT__MIXED = eINSTANCE.getSharedJmsConRoot_Mixed();

		/**
		 * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SHARED_JMS_CON_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getSharedJmsConRoot_XMLNSPrefixMap();

		/**
		 * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SHARED_JMS_CON_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getSharedJmsConRoot_XSISchemaLocation();

		/**
		 * The meta object literal for the '<em><b>BW Shared Resource</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SHARED_JMS_CON_ROOT__BW_SHARED_RESOURCE = eINSTANCE.getSharedJmsConRoot_BWSharedResource();

		/**
		 * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.ResourceType <em>Resource Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.ResourceType
		 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.SharedjmsconPackageImpl#getResourceType()
		 * @generated
		 */
		EEnum RESOURCE_TYPE = eINSTANCE.getResourceType();

		/**
		 * The meta object literal for the '<em>Resource Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.ResourceType
		 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.impl.SharedjmsconPackageImpl#getResourceTypeObject()
		 * @generated
		 */
		EDataType RESOURCE_TYPE_OBJECT = eINSTANCE.getResourceTypeObject();

	}

} //SharedjmsconPackage
