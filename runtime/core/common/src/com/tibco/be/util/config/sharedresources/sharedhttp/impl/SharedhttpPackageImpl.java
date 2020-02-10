/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedhttp.impl;

import com.tibco.be.util.config.sharedresources.aemetaservices.AemetaservicesPackage;

import com.tibco.be.util.config.sharedresources.basetypes.BasetypesPackage;

import com.tibco.be.util.config.sharedresources.sharedhttp.Config;
import com.tibco.be.util.config.sharedresources.sharedhttp.HttpSharedResource;
import com.tibco.be.util.config.sharedresources.sharedhttp.SharedHttpRoot;
import com.tibco.be.util.config.sharedresources.sharedhttp.SharedhttpFactory;
import com.tibco.be.util.config.sharedresources.sharedhttp.SharedhttpPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
public class SharedhttpPackageImpl extends EPackageImpl implements SharedhttpPackage {
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
	private EClass httpSharedResourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sharedHttpRootEClass = null;

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
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.SharedhttpPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SharedhttpPackageImpl() {
		super(eNS_URI, SharedhttpFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link SharedhttpPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SharedhttpPackage init() {
		if (isInited) return (SharedhttpPackage)EPackage.Registry.INSTANCE.getEPackage(SharedhttpPackage.eNS_URI);

		// Obtain or create and register package
		SharedhttpPackageImpl theSharedhttpPackage = (SharedhttpPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof SharedhttpPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new SharedhttpPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		AemetaservicesPackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theSharedhttpPackage.createPackageContents();

		// Initialize created meta-data
		theSharedhttpPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSharedhttpPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SharedhttpPackage.eNS_URI, theSharedhttpPackage);
		return theSharedhttpPackage;
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
	public EAttribute getConfig_Host() {
		return (EAttribute)configEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConfig_Port() {
		return (EAttribute)configEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConfig_EnableLookups() {
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
	public EReference getConfig_Ssl() {
		return (EReference)configEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConfig_Description() {
		return (EAttribute)configEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHttpSharedResource() {
		return httpSharedResourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHttpSharedResource_Config() {
		return (EReference)httpSharedResourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSharedHttpRoot() {
		return sharedHttpRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSharedHttpRoot_Mixed() {
		return (EAttribute)sharedHttpRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSharedHttpRoot_XMLNSPrefixMap() {
		return (EReference)sharedHttpRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSharedHttpRoot_XSISchemaLocation() {
		return (EReference)sharedHttpRootEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSharedHttpRoot_HttpSharedResource() {
		return (EReference)sharedHttpRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SharedhttpFactory getSharedhttpFactory() {
		return (SharedhttpFactory)getEFactoryInstance();
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
		configEClass = createEClass(CONFIG);
		createEAttribute(configEClass, CONFIG__HOST);
		createEAttribute(configEClass, CONFIG__PORT);
		createEAttribute(configEClass, CONFIG__ENABLE_LOOKUPS);
		createEAttribute(configEClass, CONFIG__USE_SSL);
		createEReference(configEClass, CONFIG__SSL);
		createEAttribute(configEClass, CONFIG__DESCRIPTION);

		httpSharedResourceEClass = createEClass(HTTP_SHARED_RESOURCE);
		createEReference(httpSharedResourceEClass, HTTP_SHARED_RESOURCE__CONFIG);

		sharedHttpRootEClass = createEClass(SHARED_HTTP_ROOT);
		createEAttribute(sharedHttpRootEClass, SHARED_HTTP_ROOT__MIXED);
		createEReference(sharedHttpRootEClass, SHARED_HTTP_ROOT__XMLNS_PREFIX_MAP);
		createEReference(sharedHttpRootEClass, SHARED_HTTP_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(sharedHttpRootEClass, SHARED_HTTP_ROOT__HTTP_SHARED_RESOURCE);
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
		initEClass(configEClass, Config.class, "Config", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConfig_Host(), theXMLTypePackage.getString(), "host", null, 1, 1, Config.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConfig_Port(), theBasetypesPackage.getIntOrGVs(), "port", null, 1, 1, Config.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConfig_EnableLookups(), theBasetypesPackage.getBooleanOrGvs(), "enableLookups", null, 1, 1, Config.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConfig_UseSsl(), theBasetypesPackage.getBooleanOrGvs(), "useSsl", null, 0, 1, Config.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConfig_Ssl(), theAemetaservicesPackage.getSsl(), null, "ssl", null, 0, 1, Config.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConfig_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, Config.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(httpSharedResourceEClass, HttpSharedResource.class, "HttpSharedResource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getHttpSharedResource_Config(), this.getConfig(), null, "config", null, 1, 1, HttpSharedResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sharedHttpRootEClass, SharedHttpRoot.class, "SharedHttpRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSharedHttpRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSharedHttpRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSharedHttpRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSharedHttpRoot_HttpSharedResource(), this.getHttpSharedResource(), null, "httpSharedResource", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

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
		  (configEClass, 
		   source, 
		   new String[] {
			 "name", "configType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getConfig_Host(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Host"
		   });		
		addAnnotation
		  (getConfig_Port(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Port"
		   });		
		addAnnotation
		  (getConfig_EnableLookups(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "enableLookups"
		   });		
		addAnnotation
		  (getConfig_UseSsl(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "useSsl"
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
		  (getConfig_Description(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "description"
		   });		
		addAnnotation
		  (httpSharedResourceEClass, 
		   source, 
		   new String[] {
			 "name", "httpSharedResourceType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getHttpSharedResource_Config(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "config"
		   });		
		addAnnotation
		  (sharedHttpRootEClass, 
		   source, 
		   new String[] {
			 "name", "",
			 "kind", "mixed"
		   });		
		addAnnotation
		  (getSharedHttpRoot_Mixed(), 
		   source, 
		   new String[] {
			 "kind", "elementWildcard",
			 "name", ":mixed"
		   });		
		addAnnotation
		  (getSharedHttpRoot_XMLNSPrefixMap(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xmlns:prefix"
		   });		
		addAnnotation
		  (getSharedHttpRoot_XSISchemaLocation(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xsi:schemaLocation"
		   });		
		addAnnotation
		  (getSharedHttpRoot_HttpSharedResource(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "httpSharedResource",
			 "namespace", "##targetNamespace"
		   });
	}

} //SharedhttpPackageImpl
