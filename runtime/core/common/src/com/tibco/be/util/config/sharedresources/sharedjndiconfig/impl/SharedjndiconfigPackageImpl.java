/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl;

import com.tibco.be.util.config.sharedresources.basetypes.BasetypesPackage;

import com.tibco.be.util.config.sharedresources.sharedjndiconfig.BwSharedResource;
import com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config;
import com.tibco.be.util.config.sharedresources.sharedjndiconfig.OptionalJndiProperties;
import com.tibco.be.util.config.sharedresources.sharedjndiconfig.ResourceType;
import com.tibco.be.util.config.sharedresources.sharedjndiconfig.Row;
import com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedJndiConfigRoot;
import com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigFactory;
import com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigPackage;

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
public class SharedjndiconfigPackageImpl extends EPackageImpl implements SharedjndiconfigPackage {
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
	private EClass optionalJndiPropertiesEClass = null;

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
	private EClass sharedJndiConfigRootEClass = null;

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
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigPackage#eNS_URI
     * @see #init()
     * @generated
     */
	private SharedjndiconfigPackageImpl() {
        super(eNS_URI, SharedjndiconfigFactory.eINSTANCE);
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
     * <p>This method is used to initialize {@link SharedjndiconfigPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
	public static SharedjndiconfigPackage init() {
        if (isInited) return (SharedjndiconfigPackage)EPackage.Registry.INSTANCE.getEPackage(SharedjndiconfigPackage.eNS_URI);

        // Obtain or create and register package
        SharedjndiconfigPackageImpl theSharedjndiconfigPackage = (SharedjndiconfigPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof SharedjndiconfigPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new SharedjndiconfigPackageImpl());

        isInited = true;

        // Initialize simple dependencies
        BasetypesPackage.eINSTANCE.eClass();
        XMLTypePackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theSharedjndiconfigPackage.createPackageContents();

        // Initialize created meta-data
        theSharedjndiconfigPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theSharedjndiconfigPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(SharedjndiconfigPackage.eNS_URI, theSharedjndiconfigPackage);
        return theSharedjndiconfigPackage;
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
    public EAttribute getConfig_ResourceType()
    {
        return (EAttribute)configEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getConfig_ResourceType1()
    {
        return (EAttribute)configEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getConfig_ValidateJndiSecurityContext() {
        return (EAttribute)configEClass.getEStructuralFeatures().get(3);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getConfig_ContextFactory() {
        return (EAttribute)configEClass.getEStructuralFeatures().get(4);
    }

	/**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getConfig_ProviderUrl1()
    {
        return (EAttribute)configEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getConfig_ProviderUrl() {
        return (EAttribute)configEClass.getEStructuralFeatures().get(2);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getConfig_SecurityPrincipal() {
        return (EAttribute)configEClass.getEStructuralFeatures().get(6);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getConfig_SecurityCredentials() {
        return (EAttribute)configEClass.getEStructuralFeatures().get(7);
    }

	/**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getConfig_OptionalJndiProperties()
    {
        return (EReference)configEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getOptionalJndiProperties() {
        return optionalJndiPropertiesEClass;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getOptionalJndiProperties_Row() {
        return (EReference)optionalJndiPropertiesEClass.getEStructuralFeatures().get(0);
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
	public EClass getSharedJndiConfigRoot() {
        return sharedJndiConfigRootEClass;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getSharedJndiConfigRoot_Mixed() {
        return (EAttribute)sharedJndiConfigRootEClass.getEStructuralFeatures().get(0);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getSharedJndiConfigRoot_XMLNSPrefixMap() {
        return (EReference)sharedJndiConfigRootEClass.getEStructuralFeatures().get(1);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getSharedJndiConfigRoot_XSISchemaLocation() {
        return (EReference)sharedJndiConfigRootEClass.getEStructuralFeatures().get(2);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getSharedJndiConfigRoot_BWSharedResource() {
        return (EReference)sharedJndiConfigRootEClass.getEStructuralFeatures().get(3);
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
	public SharedjndiconfigFactory getSharedjndiconfigFactory() {
        return (SharedjndiconfigFactory)getEFactoryInstance();
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
        createEAttribute(configEClass, CONFIG__RESOURCE_TYPE);
        createEAttribute(configEClass, CONFIG__RESOURCE_TYPE1);
        createEAttribute(configEClass, CONFIG__PROVIDER_URL);
        createEAttribute(configEClass, CONFIG__VALIDATE_JNDI_SECURITY_CONTEXT);
        createEAttribute(configEClass, CONFIG__CONTEXT_FACTORY);
        createEAttribute(configEClass, CONFIG__PROVIDER_URL1);
        createEAttribute(configEClass, CONFIG__SECURITY_PRINCIPAL);
        createEAttribute(configEClass, CONFIG__SECURITY_CREDENTIALS);
        createEReference(configEClass, CONFIG__OPTIONAL_JNDI_PROPERTIES);

        optionalJndiPropertiesEClass = createEClass(OPTIONAL_JNDI_PROPERTIES);
        createEReference(optionalJndiPropertiesEClass, OPTIONAL_JNDI_PROPERTIES__ROW);

        rowEClass = createEClass(ROW);
        createEAttribute(rowEClass, ROW__NAME);
        createEAttribute(rowEClass, ROW__TYPE);
        createEAttribute(rowEClass, ROW__VALUE);

        sharedJndiConfigRootEClass = createEClass(SHARED_JNDI_CONFIG_ROOT);
        createEAttribute(sharedJndiConfigRootEClass, SHARED_JNDI_CONFIG_ROOT__MIXED);
        createEReference(sharedJndiConfigRootEClass, SHARED_JNDI_CONFIG_ROOT__XMLNS_PREFIX_MAP);
        createEReference(sharedJndiConfigRootEClass, SHARED_JNDI_CONFIG_ROOT__XSI_SCHEMA_LOCATION);
        createEReference(sharedJndiConfigRootEClass, SHARED_JNDI_CONFIG_ROOT__BW_SHARED_RESOURCE);

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
        initEAttribute(getConfig_ResourceType(), theXMLTypePackage.getString(), "resourceType", null, 1, 1, Config.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getConfig_ResourceType1(), theXMLTypePackage.getString(), "resourceType1", null, 1, 1, Config.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getConfig_ProviderUrl(), theXMLTypePackage.getString(), "providerUrl", null, 1, 1, Config.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getConfig_ValidateJndiSecurityContext(), theBasetypesPackage.getBooleanOrGvs(), "validateJndiSecurityContext", null, 1, 1, Config.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getConfig_ContextFactory(), theXMLTypePackage.getString(), "contextFactory", null, 1, 1, Config.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getConfig_ProviderUrl1(), theXMLTypePackage.getString(), "providerUrl1", null, 1, 1, Config.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getConfig_SecurityPrincipal(), theXMLTypePackage.getString(), "securityPrincipal", null, 1, 1, Config.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getConfig_SecurityCredentials(), theXMLTypePackage.getString(), "securityCredentials", null, 1, 1, Config.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getConfig_OptionalJndiProperties(), this.getOptionalJndiProperties(), null, "optionalJndiProperties", null, 1, 1, Config.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(optionalJndiPropertiesEClass, OptionalJndiProperties.class, "OptionalJndiProperties", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getOptionalJndiProperties_Row(), this.getRow(), null, "row", null, 0, -1, OptionalJndiProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(rowEClass, Row.class, "Row", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getRow_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, Row.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getRow_Type(), theBasetypesPackage.getJndiPropertyTypeOrGvs(), "type", null, 1, 1, Row.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getRow_Value(), theXMLTypePackage.getString(), "value", null, 1, 1, Row.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(sharedJndiConfigRootEClass, SharedJndiConfigRoot.class, "SharedJndiConfigRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getSharedJndiConfigRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getSharedJndiConfigRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getSharedJndiConfigRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getSharedJndiConfigRoot_BWSharedResource(), this.getBwSharedResource(), null, "bWSharedResource", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        // Initialize enums and add enum literals
        initEEnum(resourceTypeEEnum, ResourceType.class, "ResourceType");
        addEEnumLiteral(resourceTypeEEnum, ResourceType.AE_SHARED_JNDI_SHARED_CONFIGURATION);

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
           new String[] 
           {
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
           new String[] 
           {
             "qualified", "false"
           });		
        addAnnotation
          (bwSharedResourceEClass, 
           source, 
           new String[] 
           {
             "name", "BWSharedResource-type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getBwSharedResource_Name(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "name",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getBwSharedResource_ResourceType(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "resourceType",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getBwSharedResource_Description(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "description",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getBwSharedResource_Config(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "config",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (configEClass, 
           source, 
           new String[] 
           {
             "name", "Config-type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getConfig_ResourceType(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "resourceType",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getConfig_ResourceType1(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "description",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getConfig_ProviderUrl(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "ProviderUrl",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getConfig_ValidateJndiSecurityContext(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "ValidateJndiSecurityContext",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getConfig_ContextFactory(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "ContextFactory",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getConfig_ProviderUrl1(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "ProviderUrl",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getConfig_SecurityPrincipal(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "SecurityPrincipal",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getConfig_SecurityCredentials(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "SecurityCredentials",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getConfig_OptionalJndiProperties(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "OptionalJNDIProperties",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (optionalJndiPropertiesEClass, 
           source, 
           new String[] 
           {
             "name", "OptionalJndiProperties-type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getOptionalJndiProperties_Row(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "row",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (resourceTypeEEnum, 
           source, 
           new String[] 
           {
             "name", "resourceType_._type"
           });		
        addAnnotation
          (resourceTypeObjectEDataType, 
           source, 
           new String[] 
           {
             "name", "resourceType_._type:Object",
             "baseType", "resourceType_._type"
           });		
        addAnnotation
          (rowEClass, 
           source, 
           new String[] 
           {
             "name", "row_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getRow_Name(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "JNDIPropNameCol",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getRow_Type(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "JNDIPropTypeCol",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getRow_Value(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "JNDIPropValueCol",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (sharedJndiConfigRootEClass, 
           source, 
           new String[] 
           {
             "name", "",
             "kind", "mixed"
           });		
        addAnnotation
          (getSharedJndiConfigRoot_Mixed(), 
           source, 
           new String[] 
           {
             "kind", "elementWildcard",
             "name", ":mixed"
           });		
        addAnnotation
          (getSharedJndiConfigRoot_XMLNSPrefixMap(), 
           source, 
           new String[] 
           {
             "kind", "attribute",
             "name", "xmlns:prefix"
           });		
        addAnnotation
          (getSharedJndiConfigRoot_XSISchemaLocation(), 
           source, 
           new String[] 
           {
             "kind", "attribute",
             "name", "xsi:schemaLocation"
           });		
        addAnnotation
          (getSharedJndiConfigRoot_BWSharedResource(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "BWSharedResource",
             "namespace", "##targetNamespace"
           });
    }

} //SharedjndiconfigPackageImpl
