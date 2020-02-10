/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.aemetaservices;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see com.tibco.be.util.config.sharedresources.aemetaservices.AemetaservicesFactory
 * @model kind="package"
 * @generated
 */
public interface AemetaservicesPackage extends EPackage {
	/**
     * The package name.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	String eNAME = "aemetaservices";

	/**
     * The package namespace URI.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	String eNS_URI = "http://www.tibco.com/xmlns/aemeta/services/2002";

	/**
     * The package namespace name.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	String eNS_PREFIX = "aemetaservices";

	/**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	AemetaservicesPackage eINSTANCE = com.tibco.be.util.config.sharedresources.aemetaservices.impl.AemetaservicesPackageImpl.init();

	/**
     * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.aemetaservices.impl.AeMetaServicesRootImpl <em>Ae Meta Services Root</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.impl.AeMetaServicesRootImpl
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.impl.AemetaservicesPackageImpl#getAeMetaServicesRoot()
     * @generated
     */
	int AE_META_SERVICES_ROOT = 0;

	/**
     * The feature id for the '<em><b>Mixed</b></em>' attribute list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int AE_META_SERVICES_ROOT__MIXED = 0;

	/**
     * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int AE_META_SERVICES_ROOT__XMLNS_PREFIX_MAP = 1;

	/**
     * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int AE_META_SERVICES_ROOT__XSI_SCHEMA_LOCATION = 2;

	/**
     * The feature id for the '<em><b>Ssl</b></em>' containment reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int AE_META_SERVICES_ROOT__SSL = 3;

	/**
     * The number of structural features of the '<em>Ae Meta Services Root</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int AE_META_SERVICES_ROOT_FEATURE_COUNT = 4;

	/**
     * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.aemetaservices.impl.CertTypeImpl <em>Cert Type</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.impl.CertTypeImpl
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.impl.AemetaservicesPackageImpl#getCertType()
     * @generated
     */
	int CERT_TYPE = 1;

	/**
     * The feature id for the '<em><b>Mixed</b></em>' attribute list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CERT_TYPE__MIXED = 0;

	/**
     * The feature id for the '<em><b>Is Ref</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CERT_TYPE__IS_REF = 1;

	/**
     * The number of structural features of the '<em>Cert Type</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CERT_TYPE_FEATURE_COUNT = 2;

	/**
     * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.aemetaservices.impl.IdentityTypeImpl <em>Identity Type</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.impl.IdentityTypeImpl
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.impl.AemetaservicesPackageImpl#getIdentityType()
     * @generated
     */
	int IDENTITY_TYPE = 2;

	/**
     * The feature id for the '<em><b>Mixed</b></em>' attribute list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int IDENTITY_TYPE__MIXED = 0;

	/**
     * The feature id for the '<em><b>Is Ref</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int IDENTITY_TYPE__IS_REF = 1;

	/**
     * The number of structural features of the '<em>Identity Type</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int IDENTITY_TYPE_FEATURE_COUNT = 2;

	/**
     * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.aemetaservices.impl.SslImpl <em>Ssl</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.impl.SslImpl
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.impl.AemetaservicesPackageImpl#getSsl()
     * @generated
     */
	int SSL = 3;

	/**
     * The feature id for the '<em><b>Cert</b></em>' containment reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int SSL__CERT = 0;

	/**
     * The feature id for the '<em><b>Expected Host Name</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int SSL__EXPECTED_HOST_NAME = 1;

	/**
     * The feature id for the '<em><b>Debug Trace</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int SSL__DEBUG_TRACE = 2;

	/**
     * The feature id for the '<em><b>Identity</b></em>' containment reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int SSL__IDENTITY = 3;

	/**
     * The feature id for the '<em><b>Requires Client Authentication</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int SSL__REQUIRES_CLIENT_AUTHENTICATION = 4;

	/**
     * The feature id for the '<em><b>Strong Cipher Suites Only</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int SSL__STRONG_CIPHER_SUITES_ONLY = 5;

	/**
     * The feature id for the '<em><b>Trace</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int SSL__TRACE = 6;

	/**
     * The feature id for the '<em><b>Trust Store Password</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SSL__TRUST_STORE_PASSWORD = 7;

    /**
     * The feature id for the '<em><b>Verify Host Name</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int SSL__VERIFY_HOST_NAME = 8;

	/**
     * The number of structural features of the '<em>Ssl</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int SSL_FEATURE_COUNT = 9;


	/**
     * Returns the meta object for class '{@link com.tibco.be.util.config.sharedresources.aemetaservices.AeMetaServicesRoot <em>Ae Meta Services Root</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Ae Meta Services Root</em>'.
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.AeMetaServicesRoot
     * @generated
     */
	EClass getAeMetaServicesRoot();

	/**
     * Returns the meta object for the attribute list '{@link com.tibco.be.util.config.sharedresources.aemetaservices.AeMetaServicesRoot#getMixed <em>Mixed</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Mixed</em>'.
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.AeMetaServicesRoot#getMixed()
     * @see #getAeMetaServicesRoot()
     * @generated
     */
	EAttribute getAeMetaServicesRoot_Mixed();

	/**
     * Returns the meta object for the map '{@link com.tibco.be.util.config.sharedresources.aemetaservices.AeMetaServicesRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.AeMetaServicesRoot#getXMLNSPrefixMap()
     * @see #getAeMetaServicesRoot()
     * @generated
     */
	EReference getAeMetaServicesRoot_XMLNSPrefixMap();

	/**
     * Returns the meta object for the map '{@link com.tibco.be.util.config.sharedresources.aemetaservices.AeMetaServicesRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XSI Schema Location</em>'.
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.AeMetaServicesRoot#getXSISchemaLocation()
     * @see #getAeMetaServicesRoot()
     * @generated
     */
	EReference getAeMetaServicesRoot_XSISchemaLocation();

	/**
     * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.sharedresources.aemetaservices.AeMetaServicesRoot#getSsl <em>Ssl</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Ssl</em>'.
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.AeMetaServicesRoot#getSsl()
     * @see #getAeMetaServicesRoot()
     * @generated
     */
	EReference getAeMetaServicesRoot_Ssl();

	/**
     * Returns the meta object for class '{@link com.tibco.be.util.config.sharedresources.aemetaservices.CertType <em>Cert Type</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Cert Type</em>'.
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.CertType
     * @generated
     */
	EClass getCertType();

	/**
     * Returns the meta object for the attribute list '{@link com.tibco.be.util.config.sharedresources.aemetaservices.CertType#getMixed <em>Mixed</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Mixed</em>'.
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.CertType#getMixed()
     * @see #getCertType()
     * @generated
     */
	EAttribute getCertType_Mixed();

	/**
     * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.aemetaservices.CertType#getIsRef <em>Is Ref</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Is Ref</em>'.
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.CertType#getIsRef()
     * @see #getCertType()
     * @generated
     */
	EAttribute getCertType_IsRef();

	/**
     * Returns the meta object for class '{@link com.tibco.be.util.config.sharedresources.aemetaservices.IdentityType <em>Identity Type</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Identity Type</em>'.
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.IdentityType
     * @generated
     */
	EClass getIdentityType();

	/**
     * Returns the meta object for the attribute list '{@link com.tibco.be.util.config.sharedresources.aemetaservices.IdentityType#getMixed <em>Mixed</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Mixed</em>'.
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.IdentityType#getMixed()
     * @see #getIdentityType()
     * @generated
     */
	EAttribute getIdentityType_Mixed();

	/**
     * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.aemetaservices.IdentityType#getIsRef <em>Is Ref</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Is Ref</em>'.
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.IdentityType#getIsRef()
     * @see #getIdentityType()
     * @generated
     */
	EAttribute getIdentityType_IsRef();

	/**
     * Returns the meta object for class '{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl <em>Ssl</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Ssl</em>'.
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.Ssl
     * @generated
     */
	EClass getSsl();

	/**
     * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getCert <em>Cert</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Cert</em>'.
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getCert()
     * @see #getSsl()
     * @generated
     */
	EReference getSsl_Cert();

	/**
     * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getExpectedHostName <em>Expected Host Name</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Expected Host Name</em>'.
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getExpectedHostName()
     * @see #getSsl()
     * @generated
     */
	EAttribute getSsl_ExpectedHostName();

	/**
     * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getDebugTrace <em>Debug Trace</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Debug Trace</em>'.
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getDebugTrace()
     * @see #getSsl()
     * @generated
     */
	EAttribute getSsl_DebugTrace();

	/**
     * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getIdentity <em>Identity</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Identity</em>'.
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getIdentity()
     * @see #getSsl()
     * @generated
     */
	EReference getSsl_Identity();

	/**
     * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getRequiresClientAuthentication <em>Requires Client Authentication</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Requires Client Authentication</em>'.
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getRequiresClientAuthentication()
     * @see #getSsl()
     * @generated
     */
	EAttribute getSsl_RequiresClientAuthentication();

	/**
     * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getStrongCipherSuitesOnly <em>Strong Cipher Suites Only</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Strong Cipher Suites Only</em>'.
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getStrongCipherSuitesOnly()
     * @see #getSsl()
     * @generated
     */
	EAttribute getSsl_StrongCipherSuitesOnly();

	/**
     * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getTrace <em>Trace</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Trace</em>'.
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getTrace()
     * @see #getSsl()
     * @generated
     */
	EAttribute getSsl_Trace();

	/**
     * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getTrustStorePassword <em>Trust Store Password</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Trust Store Password</em>'.
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getTrustStorePassword()
     * @see #getSsl()
     * @generated
     */
    EAttribute getSsl_TrustStorePassword();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getVerifyHostName <em>Verify Host Name</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Verify Host Name</em>'.
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getVerifyHostName()
     * @see #getSsl()
     * @generated
     */
	EAttribute getSsl_VerifyHostName();

	/**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
	AemetaservicesFactory getAemetaservicesFactory();

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
         * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.aemetaservices.impl.AeMetaServicesRootImpl <em>Ae Meta Services Root</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see com.tibco.be.util.config.sharedresources.aemetaservices.impl.AeMetaServicesRootImpl
         * @see com.tibco.be.util.config.sharedresources.aemetaservices.impl.AemetaservicesPackageImpl#getAeMetaServicesRoot()
         * @generated
         */
		EClass AE_META_SERVICES_ROOT = eINSTANCE.getAeMetaServicesRoot();

		/**
         * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute AE_META_SERVICES_ROOT__MIXED = eINSTANCE.getAeMetaServicesRoot_Mixed();

		/**
         * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EReference AE_META_SERVICES_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getAeMetaServicesRoot_XMLNSPrefixMap();

		/**
         * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EReference AE_META_SERVICES_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getAeMetaServicesRoot_XSISchemaLocation();

		/**
         * The meta object literal for the '<em><b>Ssl</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EReference AE_META_SERVICES_ROOT__SSL = eINSTANCE.getAeMetaServicesRoot_Ssl();

		/**
         * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.aemetaservices.impl.CertTypeImpl <em>Cert Type</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see com.tibco.be.util.config.sharedresources.aemetaservices.impl.CertTypeImpl
         * @see com.tibco.be.util.config.sharedresources.aemetaservices.impl.AemetaservicesPackageImpl#getCertType()
         * @generated
         */
		EClass CERT_TYPE = eINSTANCE.getCertType();

		/**
         * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute CERT_TYPE__MIXED = eINSTANCE.getCertType_Mixed();

		/**
         * The meta object literal for the '<em><b>Is Ref</b></em>' attribute feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute CERT_TYPE__IS_REF = eINSTANCE.getCertType_IsRef();

		/**
         * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.aemetaservices.impl.IdentityTypeImpl <em>Identity Type</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see com.tibco.be.util.config.sharedresources.aemetaservices.impl.IdentityTypeImpl
         * @see com.tibco.be.util.config.sharedresources.aemetaservices.impl.AemetaservicesPackageImpl#getIdentityType()
         * @generated
         */
		EClass IDENTITY_TYPE = eINSTANCE.getIdentityType();

		/**
         * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute IDENTITY_TYPE__MIXED = eINSTANCE.getIdentityType_Mixed();

		/**
         * The meta object literal for the '<em><b>Is Ref</b></em>' attribute feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute IDENTITY_TYPE__IS_REF = eINSTANCE.getIdentityType_IsRef();

		/**
         * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.aemetaservices.impl.SslImpl <em>Ssl</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see com.tibco.be.util.config.sharedresources.aemetaservices.impl.SslImpl
         * @see com.tibco.be.util.config.sharedresources.aemetaservices.impl.AemetaservicesPackageImpl#getSsl()
         * @generated
         */
		EClass SSL = eINSTANCE.getSsl();

		/**
         * The meta object literal for the '<em><b>Cert</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EReference SSL__CERT = eINSTANCE.getSsl_Cert();

		/**
         * The meta object literal for the '<em><b>Expected Host Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute SSL__EXPECTED_HOST_NAME = eINSTANCE.getSsl_ExpectedHostName();

		/**
         * The meta object literal for the '<em><b>Debug Trace</b></em>' attribute feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute SSL__DEBUG_TRACE = eINSTANCE.getSsl_DebugTrace();

		/**
         * The meta object literal for the '<em><b>Identity</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EReference SSL__IDENTITY = eINSTANCE.getSsl_Identity();

		/**
         * The meta object literal for the '<em><b>Requires Client Authentication</b></em>' attribute feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute SSL__REQUIRES_CLIENT_AUTHENTICATION = eINSTANCE.getSsl_RequiresClientAuthentication();

		/**
         * The meta object literal for the '<em><b>Strong Cipher Suites Only</b></em>' attribute feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute SSL__STRONG_CIPHER_SUITES_ONLY = eINSTANCE.getSsl_StrongCipherSuitesOnly();

		/**
         * The meta object literal for the '<em><b>Trace</b></em>' attribute feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute SSL__TRACE = eINSTANCE.getSsl_Trace();

		/**
         * The meta object literal for the '<em><b>Trust Store Password</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SSL__TRUST_STORE_PASSWORD = eINSTANCE.getSsl_TrustStorePassword();

        /**
         * The meta object literal for the '<em><b>Verify Host Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute SSL__VERIFY_HOST_NAME = eINSTANCE.getSsl_VerifyHostName();

	}

} //AemetaservicesPackage
