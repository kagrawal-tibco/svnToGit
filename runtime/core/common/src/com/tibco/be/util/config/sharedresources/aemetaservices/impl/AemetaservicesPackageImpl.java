/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.aemetaservices.impl;

import com.tibco.be.util.config.sharedresources.aemetaservices.AeMetaServicesRoot;
import com.tibco.be.util.config.sharedresources.aemetaservices.AemetaservicesFactory;
import com.tibco.be.util.config.sharedresources.aemetaservices.AemetaservicesPackage;
import com.tibco.be.util.config.sharedresources.aemetaservices.CertType;
import com.tibco.be.util.config.sharedresources.aemetaservices.IdentityType;
import com.tibco.be.util.config.sharedresources.aemetaservices.Ssl;

import com.tibco.be.util.config.sharedresources.basetypes.BasetypesPackage;

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
public class AemetaservicesPackageImpl extends EPackageImpl implements AemetaservicesPackage {
	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass aeMetaServicesRootEClass = null;

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass certTypeEClass = null;

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass identityTypeEClass = null;

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass sslEClass = null;

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
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.AemetaservicesPackage#eNS_URI
     * @see #init()
     * @generated
     */
	private AemetaservicesPackageImpl() {
        super(eNS_URI, AemetaservicesFactory.eINSTANCE);
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
     * <p>This method is used to initialize {@link AemetaservicesPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
	public static AemetaservicesPackage init() {
        if (isInited) return (AemetaservicesPackage)EPackage.Registry.INSTANCE.getEPackage(AemetaservicesPackage.eNS_URI);

        // Obtain or create and register package
        AemetaservicesPackageImpl theAemetaservicesPackage = (AemetaservicesPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof AemetaservicesPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new AemetaservicesPackageImpl());

        isInited = true;

        // Initialize simple dependencies
        BasetypesPackage.eINSTANCE.eClass();
        XMLTypePackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theAemetaservicesPackage.createPackageContents();

        // Initialize created meta-data
        theAemetaservicesPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theAemetaservicesPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(AemetaservicesPackage.eNS_URI, theAemetaservicesPackage);
        return theAemetaservicesPackage;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getAeMetaServicesRoot() {
        return aeMetaServicesRootEClass;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getAeMetaServicesRoot_Mixed() {
        return (EAttribute)aeMetaServicesRootEClass.getEStructuralFeatures().get(0);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getAeMetaServicesRoot_XMLNSPrefixMap() {
        return (EReference)aeMetaServicesRootEClass.getEStructuralFeatures().get(1);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getAeMetaServicesRoot_XSISchemaLocation() {
        return (EReference)aeMetaServicesRootEClass.getEStructuralFeatures().get(2);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getAeMetaServicesRoot_Ssl() {
        return (EReference)aeMetaServicesRootEClass.getEStructuralFeatures().get(3);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getCertType() {
        return certTypeEClass;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getCertType_Mixed() {
        return (EAttribute)certTypeEClass.getEStructuralFeatures().get(0);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getCertType_IsRef() {
        return (EAttribute)certTypeEClass.getEStructuralFeatures().get(1);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getIdentityType() {
        return identityTypeEClass;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getIdentityType_Mixed() {
        return (EAttribute)identityTypeEClass.getEStructuralFeatures().get(0);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getIdentityType_IsRef() {
        return (EAttribute)identityTypeEClass.getEStructuralFeatures().get(1);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getSsl() {
        return sslEClass;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getSsl_Cert() {
        return (EReference)sslEClass.getEStructuralFeatures().get(0);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getSsl_ExpectedHostName() {
        return (EAttribute)sslEClass.getEStructuralFeatures().get(1);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getSsl_DebugTrace() {
        return (EAttribute)sslEClass.getEStructuralFeatures().get(2);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getSsl_Identity() {
        return (EReference)sslEClass.getEStructuralFeatures().get(3);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getSsl_RequiresClientAuthentication() {
        return (EAttribute)sslEClass.getEStructuralFeatures().get(4);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getSsl_StrongCipherSuitesOnly() {
        return (EAttribute)sslEClass.getEStructuralFeatures().get(5);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getSsl_Trace() {
        return (EAttribute)sslEClass.getEStructuralFeatures().get(6);
    }

	/**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSsl_TrustStorePassword()
    {
        return (EAttribute)sslEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getSsl_VerifyHostName() {
        return (EAttribute)sslEClass.getEStructuralFeatures().get(8);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public AemetaservicesFactory getAemetaservicesFactory() {
        return (AemetaservicesFactory)getEFactoryInstance();
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
        aeMetaServicesRootEClass = createEClass(AE_META_SERVICES_ROOT);
        createEAttribute(aeMetaServicesRootEClass, AE_META_SERVICES_ROOT__MIXED);
        createEReference(aeMetaServicesRootEClass, AE_META_SERVICES_ROOT__XMLNS_PREFIX_MAP);
        createEReference(aeMetaServicesRootEClass, AE_META_SERVICES_ROOT__XSI_SCHEMA_LOCATION);
        createEReference(aeMetaServicesRootEClass, AE_META_SERVICES_ROOT__SSL);

        certTypeEClass = createEClass(CERT_TYPE);
        createEAttribute(certTypeEClass, CERT_TYPE__MIXED);
        createEAttribute(certTypeEClass, CERT_TYPE__IS_REF);

        identityTypeEClass = createEClass(IDENTITY_TYPE);
        createEAttribute(identityTypeEClass, IDENTITY_TYPE__MIXED);
        createEAttribute(identityTypeEClass, IDENTITY_TYPE__IS_REF);

        sslEClass = createEClass(SSL);
        createEReference(sslEClass, SSL__CERT);
        createEAttribute(sslEClass, SSL__EXPECTED_HOST_NAME);
        createEAttribute(sslEClass, SSL__DEBUG_TRACE);
        createEReference(sslEClass, SSL__IDENTITY);
        createEAttribute(sslEClass, SSL__REQUIRES_CLIENT_AUTHENTICATION);
        createEAttribute(sslEClass, SSL__STRONG_CIPHER_SUITES_ONLY);
        createEAttribute(sslEClass, SSL__TRACE);
        createEAttribute(sslEClass, SSL__TRUST_STORE_PASSWORD);
        createEAttribute(sslEClass, SSL__VERIFY_HOST_NAME);
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
        BasetypesPackage theBasetypesPackage = (BasetypesPackage)EPackage.Registry.INSTANCE.getEPackage(BasetypesPackage.eNS_URI);
        XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes

        // Initialize classes and features; add operations and parameters
        initEClass(aeMetaServicesRootEClass, AeMetaServicesRoot.class, "AeMetaServicesRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAeMetaServicesRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAeMetaServicesRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAeMetaServicesRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAeMetaServicesRoot_Ssl(), this.getSsl(), null, "ssl", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(certTypeEClass, CertType.class, "CertType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getCertType_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, CertType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getCertType_IsRef(), theBasetypesPackage.getBooleanOrGvs(), "isRef", null, 0, 1, CertType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(identityTypeEClass, IdentityType.class, "IdentityType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getIdentityType_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, IdentityType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getIdentityType_IsRef(), theBasetypesPackage.getBooleanOrGvs(), "isRef", null, 0, 1, IdentityType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(sslEClass, Ssl.class, "Ssl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getSsl_Cert(), this.getCertType(), null, "cert", null, 0, 1, Ssl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSsl_ExpectedHostName(), theXMLTypePackage.getString(), "expectedHostName", null, 0, 1, Ssl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSsl_DebugTrace(), theBasetypesPackage.getBooleanOrGvs(), "debugTrace", null, 0, 1, Ssl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getSsl_Identity(), this.getIdentityType(), null, "identity", null, 0, 1, Ssl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSsl_RequiresClientAuthentication(), theXMLTypePackage.getString(), "requiresClientAuthentication", null, 0, 1, Ssl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSsl_StrongCipherSuitesOnly(), theBasetypesPackage.getBooleanOrGvs(), "strongCipherSuitesOnly", null, 0, 1, Ssl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSsl_Trace(), theBasetypesPackage.getBooleanOrGvs(), "trace", null, 0, 1, Ssl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSsl_TrustStorePassword(), theXMLTypePackage.getString(), "trustStorePassword", null, 0, 1, Ssl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSsl_VerifyHostName(), theBasetypesPackage.getBooleanOrGvs(), "verifyHostName", null, 0, 1, Ssl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Create resource
        createResource(eNS_URI);

        // Create annotations
        // http:///org/eclipse/emf/ecore/util/ExtendedMetaData
        createExtendedMetaDataAnnotations();
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
          (aeMetaServicesRootEClass, 
           source, 
           new String[] 
           {
             "name", "",
             "kind", "mixed"
           });		
        addAnnotation
          (getAeMetaServicesRoot_Mixed(), 
           source, 
           new String[] 
           {
             "kind", "elementWildcard",
             "name", ":mixed"
           });		
        addAnnotation
          (getAeMetaServicesRoot_XMLNSPrefixMap(), 
           source, 
           new String[] 
           {
             "kind", "attribute",
             "name", "xmlns:prefix"
           });		
        addAnnotation
          (getAeMetaServicesRoot_XSISchemaLocation(), 
           source, 
           new String[] 
           {
             "kind", "attribute",
             "name", "xsi:schemaLocation"
           });		
        addAnnotation
          (getAeMetaServicesRoot_Ssl(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "ssl",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (certTypeEClass, 
           source, 
           new String[] 
           {
             "name", "cert_._type",
             "kind", "mixed"
           });		
        addAnnotation
          (getCertType_Mixed(), 
           source, 
           new String[] 
           {
             "kind", "elementWildcard",
             "name", ":mixed"
           });		
        addAnnotation
          (getCertType_IsRef(), 
           source, 
           new String[] 
           {
             "kind", "attribute",
             "name", "isRef"
           });		
        addAnnotation
          (identityTypeEClass, 
           source, 
           new String[] 
           {
             "name", "identity_._type",
             "kind", "mixed"
           });		
        addAnnotation
          (getIdentityType_Mixed(), 
           source, 
           new String[] 
           {
             "kind", "elementWildcard",
             "name", ":mixed"
           });		
        addAnnotation
          (getIdentityType_IsRef(), 
           source, 
           new String[] 
           {
             "kind", "attribute",
             "name", "isRef"
           });		
        addAnnotation
          (sslEClass, 
           source, 
           new String[] 
           {
             "name", "sslType",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getSsl_Cert(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "cert"
           });		
        addAnnotation
          (getSsl_ExpectedHostName(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "expectedHostName"
           });		
        addAnnotation
          (getSsl_DebugTrace(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "debugTrace"
           });		
        addAnnotation
          (getSsl_Identity(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "identity"
           });		
        addAnnotation
          (getSsl_RequiresClientAuthentication(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "requiresClientAuthentication"
           });		
        addAnnotation
          (getSsl_StrongCipherSuitesOnly(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "strongCipherSuitesOnly"
           });		
        addAnnotation
          (getSsl_Trace(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "trace"
           });		
        addAnnotation
          (getSsl_TrustStorePassword(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "trustStorePassword"
           });		
        addAnnotation
          (getSsl_VerifyHostName(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "verifyHostName"
           });
    }

} //AemetaservicesPackageImpl
