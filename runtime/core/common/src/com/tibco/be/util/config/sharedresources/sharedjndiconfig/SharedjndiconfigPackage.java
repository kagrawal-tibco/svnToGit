/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedjndiconfig;

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
 * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigFactory
 * @model kind="package"
 *        annotation="http://www.w3.org/XML/1998/namespace lang='en'"
 *        extendedMetaData="qualified='false'"
 * @generated
 */
public interface SharedjndiconfigPackage extends EPackage {
	/**
     * The package name.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	String eNAME = "sharedjndiconfig";

	/**
     * The package namespace URI.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	String eNS_URI = "platform:/resource/BE/runtime/core/common/src/sharedjndiconfig.xsd";

	/**
     * The package namespace name.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	String eNS_PREFIX = "sharedjndiconfig";

	/**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	SharedjndiconfigPackage eINSTANCE = com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.SharedjndiconfigPackageImpl.init();

	/**
     * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.BwSharedResourceImpl <em>Bw Shared Resource</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.BwSharedResourceImpl
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.SharedjndiconfigPackageImpl#getBwSharedResource()
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
     * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.ConfigImpl <em>Config</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.ConfigImpl
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.SharedjndiconfigPackageImpl#getConfig()
     * @generated
     */
	int CONFIG = 1;

	/**
     * The feature id for the '<em><b>Resource Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONFIG__RESOURCE_TYPE = 0;

    /**
     * The feature id for the '<em><b>Resource Type1</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONFIG__RESOURCE_TYPE1 = 1;

    /**
     * The feature id for the '<em><b>Provider Url</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CONFIG__PROVIDER_URL = 2;

    /**
     * The feature id for the '<em><b>Validate Jndi Security Context</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CONFIG__VALIDATE_JNDI_SECURITY_CONTEXT = 3;

    /**
     * The feature id for the '<em><b>Context Factory</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CONFIG__CONTEXT_FACTORY = 4;

    /**
     * The feature id for the '<em><b>Provider Url1</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONFIG__PROVIDER_URL1 = 5;

	/**
     * The feature id for the '<em><b>Security Principal</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CONFIG__SECURITY_PRINCIPAL = 6;

	/**
     * The feature id for the '<em><b>Security Credentials</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CONFIG__SECURITY_CREDENTIALS = 7;

	/**
     * The feature id for the '<em><b>Optional Jndi Properties</b></em>' containment reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CONFIG__OPTIONAL_JNDI_PROPERTIES = 8;

	/**
     * The number of structural features of the '<em>Config</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CONFIG_FEATURE_COUNT = 9;

	/**
     * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.OptionalJndiPropertiesImpl <em>Optional Jndi Properties</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.OptionalJndiPropertiesImpl
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.SharedjndiconfigPackageImpl#getOptionalJndiProperties()
     * @generated
     */
	int OPTIONAL_JNDI_PROPERTIES = 2;

	/**
     * The feature id for the '<em><b>Row</b></em>' containment reference list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int OPTIONAL_JNDI_PROPERTIES__ROW = 0;

	/**
     * The number of structural features of the '<em>Optional Jndi Properties</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int OPTIONAL_JNDI_PROPERTIES_FEATURE_COUNT = 1;

	/**
     * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.RowImpl <em>Row</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.RowImpl
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.SharedjndiconfigPackageImpl#getRow()
     * @generated
     */
	int ROW = 3;

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
     * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.SharedJndiConfigRootImpl <em>Shared Jndi Config Root</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.SharedJndiConfigRootImpl
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.SharedjndiconfigPackageImpl#getSharedJndiConfigRoot()
     * @generated
     */
	int SHARED_JNDI_CONFIG_ROOT = 4;

	/**
     * The feature id for the '<em><b>Mixed</b></em>' attribute list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int SHARED_JNDI_CONFIG_ROOT__MIXED = 0;

	/**
     * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int SHARED_JNDI_CONFIG_ROOT__XMLNS_PREFIX_MAP = 1;

	/**
     * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int SHARED_JNDI_CONFIG_ROOT__XSI_SCHEMA_LOCATION = 2;

	/**
     * The feature id for the '<em><b>BW Shared Resource</b></em>' containment reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int SHARED_JNDI_CONFIG_ROOT__BW_SHARED_RESOURCE = 3;

	/**
     * The number of structural features of the '<em>Shared Jndi Config Root</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int SHARED_JNDI_CONFIG_ROOT_FEATURE_COUNT = 4;

	/**
     * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.ResourceType <em>Resource Type</em>}' enum.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.ResourceType
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.SharedjndiconfigPackageImpl#getResourceType()
     * @generated
     */
	int RESOURCE_TYPE = 5;

	/**
     * The meta object id for the '<em>Resource Type Object</em>' data type.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.ResourceType
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.SharedjndiconfigPackageImpl#getResourceTypeObject()
     * @generated
     */
	int RESOURCE_TYPE_OBJECT = 6;


	/**
     * Returns the meta object for class '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.BwSharedResource <em>Bw Shared Resource</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Bw Shared Resource</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.BwSharedResource
     * @generated
     */
	EClass getBwSharedResource();

	/**
     * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.BwSharedResource#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.BwSharedResource#getName()
     * @see #getBwSharedResource()
     * @generated
     */
	EAttribute getBwSharedResource_Name();

	/**
     * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.BwSharedResource#getResourceType <em>Resource Type</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Resource Type</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.BwSharedResource#getResourceType()
     * @see #getBwSharedResource()
     * @generated
     */
	EAttribute getBwSharedResource_ResourceType();

	/**
     * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.BwSharedResource#getDescription <em>Description</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Description</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.BwSharedResource#getDescription()
     * @see #getBwSharedResource()
     * @generated
     */
	EAttribute getBwSharedResource_Description();

	/**
     * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.BwSharedResource#getConfig <em>Config</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Config</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.BwSharedResource#getConfig()
     * @see #getBwSharedResource()
     * @generated
     */
	EReference getBwSharedResource_Config();

	/**
     * Returns the meta object for class '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config <em>Config</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Config</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config
     * @generated
     */
	EClass getConfig();

	/**
     * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getResourceType <em>Resource Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Resource Type</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getResourceType()
     * @see #getConfig()
     * @generated
     */
    EAttribute getConfig_ResourceType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getResourceType1 <em>Resource Type1</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Resource Type1</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getResourceType1()
     * @see #getConfig()
     * @generated
     */
    EAttribute getConfig_ResourceType1();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getValidateJndiSecurityContext <em>Validate Jndi Security Context</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Validate Jndi Security Context</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getValidateJndiSecurityContext()
     * @see #getConfig()
     * @generated
     */
	EAttribute getConfig_ValidateJndiSecurityContext();

	/**
     * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getContextFactory <em>Context Factory</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Context Factory</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getContextFactory()
     * @see #getConfig()
     * @generated
     */
	EAttribute getConfig_ContextFactory();

	/**
     * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getProviderUrl1 <em>Provider Url1</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Provider Url1</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getProviderUrl1()
     * @see #getConfig()
     * @generated
     */
    EAttribute getConfig_ProviderUrl1();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getProviderUrl <em>Provider Url</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Provider Url</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getProviderUrl()
     * @see #getConfig()
     * @generated
     */
	EAttribute getConfig_ProviderUrl();

	/**
     * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getSecurityPrincipal <em>Security Principal</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Security Principal</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getSecurityPrincipal()
     * @see #getConfig()
     * @generated
     */
	EAttribute getConfig_SecurityPrincipal();

	/**
     * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getSecurityCredentials <em>Security Credentials</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Security Credentials</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getSecurityCredentials()
     * @see #getConfig()
     * @generated
     */
	EAttribute getConfig_SecurityCredentials();

	/**
     * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getOptionalJndiProperties <em>Optional Jndi Properties</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Optional Jndi Properties</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getOptionalJndiProperties()
     * @see #getConfig()
     * @generated
     */
    EReference getConfig_OptionalJndiProperties();

    /**
     * Returns the meta object for class '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.OptionalJndiProperties <em>Optional Jndi Properties</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Optional Jndi Properties</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.OptionalJndiProperties
     * @generated
     */
	EClass getOptionalJndiProperties();

	/**
     * Returns the meta object for the containment reference list '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.OptionalJndiProperties#getRow <em>Row</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Row</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.OptionalJndiProperties#getRow()
     * @see #getOptionalJndiProperties()
     * @generated
     */
	EReference getOptionalJndiProperties_Row();

	/**
     * Returns the meta object for class '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Row <em>Row</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Row</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.Row
     * @generated
     */
	EClass getRow();

	/**
     * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Row#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.Row#getName()
     * @see #getRow()
     * @generated
     */
	EAttribute getRow_Name();

	/**
     * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Row#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.Row#getType()
     * @see #getRow()
     * @generated
     */
	EAttribute getRow_Type();

	/**
     * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Row#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.Row#getValue()
     * @see #getRow()
     * @generated
     */
	EAttribute getRow_Value();

	/**
     * Returns the meta object for class '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedJndiConfigRoot <em>Shared Jndi Config Root</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Shared Jndi Config Root</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedJndiConfigRoot
     * @generated
     */
	EClass getSharedJndiConfigRoot();

	/**
     * Returns the meta object for the attribute list '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedJndiConfigRoot#getMixed <em>Mixed</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Mixed</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedJndiConfigRoot#getMixed()
     * @see #getSharedJndiConfigRoot()
     * @generated
     */
	EAttribute getSharedJndiConfigRoot_Mixed();

	/**
     * Returns the meta object for the map '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedJndiConfigRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedJndiConfigRoot#getXMLNSPrefixMap()
     * @see #getSharedJndiConfigRoot()
     * @generated
     */
	EReference getSharedJndiConfigRoot_XMLNSPrefixMap();

	/**
     * Returns the meta object for the map '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedJndiConfigRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XSI Schema Location</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedJndiConfigRoot#getXSISchemaLocation()
     * @see #getSharedJndiConfigRoot()
     * @generated
     */
	EReference getSharedJndiConfigRoot_XSISchemaLocation();

	/**
     * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedJndiConfigRoot#getBWSharedResource <em>BW Shared Resource</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>BW Shared Resource</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedJndiConfigRoot#getBWSharedResource()
     * @see #getSharedJndiConfigRoot()
     * @generated
     */
	EReference getSharedJndiConfigRoot_BWSharedResource();

	/**
     * Returns the meta object for enum '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.ResourceType <em>Resource Type</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Resource Type</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.ResourceType
     * @generated
     */
	EEnum getResourceType();

	/**
     * Returns the meta object for data type '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.ResourceType <em>Resource Type Object</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Resource Type Object</em>'.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.ResourceType
     * @model instanceClass="com.tibco.be.util.config.sharedresources.sharedjndiconfig.ResourceType"
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
	SharedjndiconfigFactory getSharedjndiconfigFactory();

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
         * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.BwSharedResourceImpl <em>Bw Shared Resource</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.BwSharedResourceImpl
         * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.SharedjndiconfigPackageImpl#getBwSharedResource()
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
         * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.ConfigImpl <em>Config</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.ConfigImpl
         * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.SharedjndiconfigPackageImpl#getConfig()
         * @generated
         */
		EClass CONFIG = eINSTANCE.getConfig();

		/**
         * The meta object literal for the '<em><b>Resource Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONFIG__RESOURCE_TYPE = eINSTANCE.getConfig_ResourceType();

        /**
         * The meta object literal for the '<em><b>Resource Type1</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONFIG__RESOURCE_TYPE1 = eINSTANCE.getConfig_ResourceType1();

        /**
         * The meta object literal for the '<em><b>Validate Jndi Security Context</b></em>' attribute feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute CONFIG__VALIDATE_JNDI_SECURITY_CONTEXT = eINSTANCE.getConfig_ValidateJndiSecurityContext();

		/**
         * The meta object literal for the '<em><b>Context Factory</b></em>' attribute feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute CONFIG__CONTEXT_FACTORY = eINSTANCE.getConfig_ContextFactory();

		/**
         * The meta object literal for the '<em><b>Provider Url1</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONFIG__PROVIDER_URL1 = eINSTANCE.getConfig_ProviderUrl1();

        /**
         * The meta object literal for the '<em><b>Provider Url</b></em>' attribute feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute CONFIG__PROVIDER_URL = eINSTANCE.getConfig_ProviderUrl();

		/**
         * The meta object literal for the '<em><b>Security Principal</b></em>' attribute feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute CONFIG__SECURITY_PRINCIPAL = eINSTANCE.getConfig_SecurityPrincipal();

		/**
         * The meta object literal for the '<em><b>Security Credentials</b></em>' attribute feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute CONFIG__SECURITY_CREDENTIALS = eINSTANCE.getConfig_SecurityCredentials();

		/**
         * The meta object literal for the '<em><b>Optional Jndi Properties</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EReference CONFIG__OPTIONAL_JNDI_PROPERTIES = eINSTANCE.getConfig_OptionalJndiProperties();

		/**
         * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.OptionalJndiPropertiesImpl <em>Optional Jndi Properties</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.OptionalJndiPropertiesImpl
         * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.SharedjndiconfigPackageImpl#getOptionalJndiProperties()
         * @generated
         */
		EClass OPTIONAL_JNDI_PROPERTIES = eINSTANCE.getOptionalJndiProperties();

		/**
         * The meta object literal for the '<em><b>Row</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EReference OPTIONAL_JNDI_PROPERTIES__ROW = eINSTANCE.getOptionalJndiProperties_Row();

		/**
         * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.RowImpl <em>Row</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.RowImpl
         * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.SharedjndiconfigPackageImpl#getRow()
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
         * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.SharedJndiConfigRootImpl <em>Shared Jndi Config Root</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.SharedJndiConfigRootImpl
         * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.SharedjndiconfigPackageImpl#getSharedJndiConfigRoot()
         * @generated
         */
		EClass SHARED_JNDI_CONFIG_ROOT = eINSTANCE.getSharedJndiConfigRoot();

		/**
         * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute SHARED_JNDI_CONFIG_ROOT__MIXED = eINSTANCE.getSharedJndiConfigRoot_Mixed();

		/**
         * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EReference SHARED_JNDI_CONFIG_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getSharedJndiConfigRoot_XMLNSPrefixMap();

		/**
         * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EReference SHARED_JNDI_CONFIG_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getSharedJndiConfigRoot_XSISchemaLocation();

		/**
         * The meta object literal for the '<em><b>BW Shared Resource</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EReference SHARED_JNDI_CONFIG_ROOT__BW_SHARED_RESOURCE = eINSTANCE.getSharedJndiConfigRoot_BWSharedResource();

		/**
         * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.ResourceType <em>Resource Type</em>}' enum.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.ResourceType
         * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.SharedjndiconfigPackageImpl#getResourceType()
         * @generated
         */
		EEnum RESOURCE_TYPE = eINSTANCE.getResourceType();

		/**
         * The meta object literal for the '<em>Resource Type Object</em>' data type.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.ResourceType
         * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.SharedjndiconfigPackageImpl#getResourceTypeObject()
         * @generated
         */
		EDataType RESOURCE_TYPE_OBJECT = eINSTANCE.getResourceTypeObject();

	}

} //SharedjndiconfigPackage
