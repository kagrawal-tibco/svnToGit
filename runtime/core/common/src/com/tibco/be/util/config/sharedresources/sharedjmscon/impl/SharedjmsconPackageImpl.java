/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedjmscon.impl;

import com.tibco.be.util.config.sharedresources.aemetaservices.AemetaservicesPackage;

import com.tibco.be.util.config.sharedresources.basetypes.BasetypesPackage;

import com.tibco.be.util.config.sharedresources.sharedjmscon.BwSharedResource;
import com.tibco.be.util.config.sharedresources.sharedjmscon.Config;
import com.tibco.be.util.config.sharedresources.sharedjmscon.ConnectionAttributes;
import com.tibco.be.util.config.sharedresources.sharedjmscon.JndiProperties;
import com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment;
import com.tibco.be.util.config.sharedresources.sharedjmscon.ResourceType;
import com.tibco.be.util.config.sharedresources.sharedjmscon.Row;
import com.tibco.be.util.config.sharedresources.sharedjmscon.SharedJmsConRoot;
import com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconFactory;
import com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SharedjmsconPackageImpl extends EPackageImpl implements SharedjmsconPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bwSharedResourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass configEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass connectionAttributesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jndiPropertiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass namingEnvironmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rowEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sharedJmsConRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum resourceTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType resourceTypeObjectEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SharedjmsconPackageImpl() {
		super(eNS_URI, SharedjmsconFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link SharedjmsconPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SharedjmsconPackage init() {
		if (isInited) return (SharedjmsconPackage)EPackage.Registry.INSTANCE.getEPackage(SharedjmsconPackage.eNS_URI);

		// Obtain or create and register package
		SharedjmsconPackageImpl theSharedjmsconPackage = (SharedjmsconPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof SharedjmsconPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new SharedjmsconPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		AemetaservicesPackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theSharedjmsconPackage.createPackageContents();

		// Initialize created meta-data
		theSharedjmsconPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSharedjmsconPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SharedjmsconPackage.eNS_URI, theSharedjmsconPackage);
		return theSharedjmsconPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBwSharedResource() {
		return bwSharedResourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBwSharedResource_Name() {
		return (EAttribute)bwSharedResourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBwSharedResource_ResourceType() {
		return (EAttribute)bwSharedResourceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBwSharedResource_Description() {
		return (EAttribute)bwSharedResourceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBwSharedResource_Config() {
		return (EReference)bwSharedResourceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConfig() {
		return configEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConfig_NamingEnvironment() {
		return (EReference)configEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConfig_ConnectionAttributes() {
		return (EReference)configEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConfig_UseXacf() {
		return (EAttribute)configEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConfig_UseSsl() {
		return (EAttribute)configEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConfig_UseSharedJndiConfig() {
		return (EAttribute)configEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConfig_AdmFactorySslPassword() {
		return (EAttribute)configEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConfig_Ssl() {
		return (EReference)configEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConfig_JndiSharedConfiguration() {
		return (EAttribute)configEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConfig_JndiProperties() {
		return (EReference)configEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConnectionAttributes() {
		return connectionAttributesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConnectionAttributes_Username() {
		return (EAttribute)connectionAttributesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConnectionAttributes_Password() {
		return (EAttribute)connectionAttributesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConnectionAttributes_ClientId() {
		return (EAttribute)connectionAttributesEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConnectionAttributes_AutoGenClientID() {
		return (EAttribute)connectionAttributesEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJndiProperties() {
		return jndiPropertiesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJndiProperties_Group() {
		return (EAttribute)jndiPropertiesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getJndiProperties_Row() {
		return (EReference)jndiPropertiesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNamingEnvironment() {
		return namingEnvironmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamingEnvironment_UseJndi() {
		return (EAttribute)namingEnvironmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamingEnvironment_ProviderUrl() {
		return (EAttribute)namingEnvironmentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamingEnvironment_NamingUrl() {
		return (EAttribute)namingEnvironmentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamingEnvironment_NamingInitialContextFactory() {
		return (EAttribute)namingEnvironmentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamingEnvironment_TopicFactoryName() {
		return (EAttribute)namingEnvironmentEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamingEnvironment_QueueFactoryName() {
		return (EAttribute)namingEnvironmentEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamingEnvironment_NamingPrincipal() {
		return (EAttribute)namingEnvironmentEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamingEnvironment_NamingCredential() {
		return (EAttribute)namingEnvironmentEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRow() {
		return rowEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRow_Name() {
		return (EAttribute)rowEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRow_Type() {
		return (EAttribute)rowEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRow_Value() {
		return (EAttribute)rowEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSharedJmsConRoot() {
		return sharedJmsConRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSharedJmsConRoot_Mixed() {
		return (EAttribute)sharedJmsConRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSharedJmsConRoot_XMLNSPrefixMap() {
		return (EReference)sharedJmsConRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSharedJmsConRoot_XSISchemaLocation() {
		return (EReference)sharedJmsConRootEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSharedJmsConRoot_BWSharedResource() {
		return (EReference)sharedJmsConRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getResourceType() {
		return resourceTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getResourceTypeObject() {
		return resourceTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SharedjmsconFactory getSharedjmsconFactory() {
		return (SharedjmsconFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		bwSharedResourceEClass = createEClass(BW_SHARED_RESOURCE);
		createEAttribute(bwSharedResourceEClass, BW_SHARED_RESOURCE__NAME);
		createEAttribute(bwSharedResourceEClass, BW_SHARED_RESOURCE__RESOURCE_TYPE);
		createEAttribute(bwSharedResourceEClass, BW_SHARED_RESOURCE__DESCRIPTION);
		createEReference(bwSharedResourceEClass, BW_SHARED_RESOURCE__CONFIG);

		configEClass = createEClass(CONFIG);
		createEReference(configEClass, CONFIG__NAMING_ENVIRONMENT);
		createEReference(configEClass, CONFIG__CONNECTION_ATTRIBUTES);
		createEAttribute(configEClass, CONFIG__USE_XACF);
		createEAttribute(configEClass, CONFIG__USE_SSL);
		createEAttribute(configEClass, CONFIG__USE_SHARED_JNDI_CONFIG);
		createEAttribute(configEClass, CONFIG__ADM_FACTORY_SSL_PASSWORD);
		createEReference(configEClass, CONFIG__SSL);
		createEAttribute(configEClass, CONFIG__JNDI_SHARED_CONFIGURATION);
		createEReference(configEClass, CONFIG__JNDI_PROPERTIES);

		connectionAttributesEClass = createEClass(CONNECTION_ATTRIBUTES);
		createEAttribute(connectionAttributesEClass, CONNECTION_ATTRIBUTES__USERNAME);
		createEAttribute(connectionAttributesEClass, CONNECTION_ATTRIBUTES__PASSWORD);
		createEAttribute(connectionAttributesEClass, CONNECTION_ATTRIBUTES__CLIENT_ID);
		createEAttribute(connectionAttributesEClass, CONNECTION_ATTRIBUTES__AUTO_GEN_CLIENT_ID);

		jndiPropertiesEClass = createEClass(JNDI_PROPERTIES);
		createEAttribute(jndiPropertiesEClass, JNDI_PROPERTIES__GROUP);
		createEReference(jndiPropertiesEClass, JNDI_PROPERTIES__ROW);

		namingEnvironmentEClass = createEClass(NAMING_ENVIRONMENT);
		createEAttribute(namingEnvironmentEClass, NAMING_ENVIRONMENT__USE_JNDI);
		createEAttribute(namingEnvironmentEClass, NAMING_ENVIRONMENT__PROVIDER_URL);
		createEAttribute(namingEnvironmentEClass, NAMING_ENVIRONMENT__NAMING_URL);
		createEAttribute(namingEnvironmentEClass, NAMING_ENVIRONMENT__NAMING_INITIAL_CONTEXT_FACTORY);
		createEAttribute(namingEnvironmentEClass, NAMING_ENVIRONMENT__TOPIC_FACTORY_NAME);
		createEAttribute(namingEnvironmentEClass, NAMING_ENVIRONMENT__QUEUE_FACTORY_NAME);
		createEAttribute(namingEnvironmentEClass, NAMING_ENVIRONMENT__NAMING_PRINCIPAL);
		createEAttribute(namingEnvironmentEClass, NAMING_ENVIRONMENT__NAMING_CREDENTIAL);

		rowEClass = createEClass(ROW);
		createEAttribute(rowEClass, ROW__NAME);
		createEAttribute(rowEClass, ROW__TYPE);
		createEAttribute(rowEClass, ROW__VALUE);

		sharedJmsConRootEClass = createEClass(SHARED_JMS_CON_ROOT);
		createEAttribute(sharedJmsConRootEClass, SHARED_JMS_CON_ROOT__MIXED);
		createEReference(sharedJmsConRootEClass, SHARED_JMS_CON_ROOT__XMLNS_PREFIX_MAP);
		createEReference(sharedJmsConRootEClass, SHARED_JMS_CON_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(sharedJmsConRootEClass, SHARED_JMS_CON_ROOT__BW_SHARED_RESOURCE);

		// Create enums
		resourceTypeEEnum = createEEnum(RESOURCE_TYPE);

		// Create data types
		resourceTypeObjectEDataType = createEDataType(RESOURCE_TYPE_OBJECT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);
		BasetypesPackage theBasetypesPackage = (BasetypesPackage)EPackage.Registry.INSTANCE.getEPackage(BasetypesPackage.eNS_URI);
		AemetaservicesPackage theAemetaservicesPackage = (AemetaservicesPackage)EPackage.Registry.INSTANCE.getEPackage(AemetaservicesPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(bwSharedResourceEClass, BwSharedResource.class, "BwSharedResource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBwSharedResource_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, BwSharedResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBwSharedResource_ResourceType(), this.getResourceType(), "resourceType", null, 1, 1, BwSharedResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBwSharedResource_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, BwSharedResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBwSharedResource_Config(), this.getConfig(), null, "config", null, 1, 1, BwSharedResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(configEClass, Config.class, "Config", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConfig_NamingEnvironment(), this.getNamingEnvironment(), null, "namingEnvironment", null, 1, 1, Config.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConfig_ConnectionAttributes(), this.getConnectionAttributes(), null, "connectionAttributes", null, 1, 1, Config.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConfig_UseXacf(), theBasetypesPackage.getBooleanOrGvs(), "useXacf", null, 1, 1, Config.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConfig_UseSsl(), theBasetypesPackage.getBooleanOrGvs(), "useSsl", null, 0, 1, Config.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConfig_UseSharedJndiConfig(), theBasetypesPackage.getBooleanOrGvs(), "useSharedJndiConfig", null, 0, 1, Config.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConfig_AdmFactorySslPassword(), theXMLTypePackage.getString(), "admFactorySslPassword", null, 0, 1, Config.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConfig_Ssl(), theAemetaservicesPackage.getSsl(), null, "ssl", null, 0, 1, Config.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConfig_JndiSharedConfiguration(), theXMLTypePackage.getString(), "jndiSharedConfiguration", null, 0, 1, Config.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConfig_JndiProperties(), this.getJndiProperties(), null, "jndiProperties", null, 0, 1, Config.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(connectionAttributesEClass, ConnectionAttributes.class, "ConnectionAttributes", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConnectionAttributes_Username(), theXMLTypePackage.getString(), "username", null, 1, 1, ConnectionAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConnectionAttributes_Password(), theXMLTypePackage.getString(), "password", null, 1, 1, ConnectionAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConnectionAttributes_ClientId(), theXMLTypePackage.getString(), "clientId", null, 1, 1, ConnectionAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConnectionAttributes_AutoGenClientID(), theBasetypesPackage.getBooleanOrGvs(), "autoGenClientID", null, 1, 1, ConnectionAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(jndiPropertiesEClass, JndiProperties.class, "JndiProperties", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJndiProperties_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, JndiProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getJndiProperties_Row(), this.getRow(), null, "row", null, 0, -1, JndiProperties.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(namingEnvironmentEClass, NamingEnvironment.class, "NamingEnvironment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNamingEnvironment_UseJndi(), theBasetypesPackage.getBooleanOrGvs(), "useJndi", null, 1, 1, NamingEnvironment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNamingEnvironment_ProviderUrl(), theXMLTypePackage.getString(), "providerUrl", null, 1, 1, NamingEnvironment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNamingEnvironment_NamingUrl(), theXMLTypePackage.getString(), "namingUrl", null, 1, 1, NamingEnvironment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNamingEnvironment_NamingInitialContextFactory(), theXMLTypePackage.getString(), "namingInitialContextFactory", null, 1, 1, NamingEnvironment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNamingEnvironment_TopicFactoryName(), theXMLTypePackage.getString(), "topicFactoryName", null, 1, 1, NamingEnvironment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNamingEnvironment_QueueFactoryName(), theXMLTypePackage.getString(), "queueFactoryName", null, 1, 1, NamingEnvironment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNamingEnvironment_NamingPrincipal(), theXMLTypePackage.getString(), "namingPrincipal", null, 1, 1, NamingEnvironment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNamingEnvironment_NamingCredential(), theXMLTypePackage.getString(), "namingCredential", null, 1, 1, NamingEnvironment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(rowEClass, Row.class, "Row", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRow_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, Row.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRow_Type(), theBasetypesPackage.getJndiPropertyTypeOrGvs(), "type", null, 1, 1, Row.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRow_Value(), theXMLTypePackage.getString(), "value", null, 1, 1, Row.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sharedJmsConRootEClass, SharedJmsConRoot.class, "SharedJmsConRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSharedJmsConRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSharedJmsConRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSharedJmsConRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSharedJmsConRoot_BWSharedResource(), this.getBwSharedResource(), null, "bWSharedResource", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(resourceTypeEEnum, ResourceType.class, "ResourceType");
		addEEnumLiteral(resourceTypeEEnum, ResourceType.AE_SHARED_JMS_CONNECTION_KEY);

		// Initialize data types
		initEDataType(resourceTypeObjectEDataType, ResourceType.class, "ResourceTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.w3.org/XML/1998/namespace
		createNamespaceAnnotations();
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.w3.org/XML/1998/namespace</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createNamespaceAnnotations() {
		String source = "http://www.w3.org/XML/1998/namespace";		
		addAnnotation
		  (this, 
		   source, 
		   new String[] {
			 "lang", "en"
		   });																																												
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";			
		addAnnotation
		  (this, 
		   source, 
		   new String[] {
			 "qualified", "false"
		   });		
		addAnnotation
		  (bwSharedResourceEClass, 
		   source, 
		   new String[] {
			 "name", "BWSharedResource-type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getBwSharedResource_Name(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "name",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getBwSharedResource_ResourceType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "resourceType",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getBwSharedResource_Description(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "description",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getBwSharedResource_Config(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "config",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (configEClass, 
		   source, 
		   new String[] {
			 "name", "Config-type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getConfig_NamingEnvironment(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NamingEnvironment",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getConfig_ConnectionAttributes(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ConnectionAttributes",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getConfig_UseXacf(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "UseXACF",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getConfig_UseSsl(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "useSsl",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getConfig_UseSharedJndiConfig(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "UseSharedJndiConfig",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getConfig_AdmFactorySslPassword(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AdmFactorySslPassword",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getConfig_Ssl(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ssl",
			 "namespace", "http://www.tibco.com/xmlns/aemeta/services/2002"
		   });		
		addAnnotation
		  (getConfig_JndiSharedConfiguration(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "JndiSharedConfiguration",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getConfig_JndiProperties(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "JNDIProperties",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (connectionAttributesEClass, 
		   source, 
		   new String[] {
			 "name", "ConnectionAttributes-type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getConnectionAttributes_Username(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "username",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getConnectionAttributes_Password(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "password",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getConnectionAttributes_ClientId(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "clientID",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getConnectionAttributes_AutoGenClientID(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "autoGenClientID",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (jndiPropertiesEClass, 
		   source, 
		   new String[] {
			 "name", "JNDIProperties-type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getJndiProperties_Group(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "group:0"
		   });		
		addAnnotation
		  (getJndiProperties_Row(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "row",
			 "namespace", "##targetNamespace",
			 "group", "group:0"
		   });		
		addAnnotation
		  (namingEnvironmentEClass, 
		   source, 
		   new String[] {
			 "name", "NamingEnvironment-type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getNamingEnvironment_UseJndi(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "UseJNDI",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getNamingEnvironment_ProviderUrl(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ProviderURL",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getNamingEnvironment_NamingUrl(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NamingURL",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getNamingEnvironment_NamingInitialContextFactory(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NamingInitialContextFactory",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getNamingEnvironment_TopicFactoryName(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "TopicFactoryName",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getNamingEnvironment_QueueFactoryName(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "QueueFactoryName",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getNamingEnvironment_NamingPrincipal(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NamingPrincipal",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getNamingEnvironment_NamingCredential(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NamingCredential",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (resourceTypeEEnum, 
		   source, 
		   new String[] {
			 "name", "resourceType_._type"
		   });		
		addAnnotation
		  (resourceTypeObjectEDataType, 
		   source, 
		   new String[] {
			 "name", "resourceType_._type:Object",
			 "baseType", "resourceType_._type"
		   });		
		addAnnotation
		  (rowEClass, 
		   source, 
		   new String[] {
			 "name", "Row-type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getRow_Name(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Name",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getRow_Type(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Type",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getRow_Value(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Value",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (sharedJmsConRootEClass, 
		   source, 
		   new String[] {
			 "name", "",
			 "kind", "mixed"
		   });		
		addAnnotation
		  (getSharedJmsConRoot_Mixed(), 
		   source, 
		   new String[] {
			 "kind", "elementWildcard",
			 "name", ":mixed"
		   });		
		addAnnotation
		  (getSharedJmsConRoot_XMLNSPrefixMap(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xmlns:prefix"
		   });		
		addAnnotation
		  (getSharedJmsConRoot_XSISchemaLocation(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xsi:schemaLocation"
		   });		
		addAnnotation
		  (getSharedJmsConRoot_BWSharedResource(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "BWSharedResource",
			 "namespace", "##targetNamespace"
		   });
	}

} //SharedjmsconPackageImpl
