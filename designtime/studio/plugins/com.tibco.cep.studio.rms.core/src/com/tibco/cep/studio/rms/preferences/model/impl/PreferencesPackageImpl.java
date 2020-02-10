/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.rms.preferences.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.cep.studio.rms.preferences.model.AuthenticationInfo;
import com.tibco.cep.studio.rms.preferences.model.AuthenticationURLInfo;
import com.tibco.cep.studio.rms.preferences.model.CheckoutRepoInfo;
import com.tibco.cep.studio.rms.preferences.model.CheckoutURLInfo;
import com.tibco.cep.studio.rms.preferences.model.PreferencesFactory;
import com.tibco.cep.studio.rms.preferences.model.PreferencesPackage;
import com.tibco.cep.studio.rms.preferences.model.RMSPreferences;
import com.tibco.cep.studio.rms.preferences.model.URLInfo;
import com.tibco.cep.studio.rms.preferences.model.UserInfo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PreferencesPackageImpl extends EPackageImpl implements PreferencesPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass authenticationInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass urlInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass urlEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass checkoutRepoInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rmsPreferencesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass authenticationURLInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass checkoutURLInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass userInfoEClass = null;

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
	 * @see com.tibco.cep.studio.rms.preferences.model.PreferencesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private PreferencesPackageImpl() {
		super(eNS_URI, PreferencesFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this
	 * model, and for any others upon which it depends.  Simple
	 * dependencies are satisfied by calling this method on all
	 * dependent packages before doing anything else.  This method drives
	 * initialization for interdependent packages directly, in parallel
	 * with this package, itself.
	 * <p>Of this package and its interdependencies, all packages which
	 * have not yet been registered by their URI values are first created
	 * and registered.  The packages are then initialized in two steps:
	 * meta-model objects for all of the packages are created before any
	 * are initialized, since one package's meta-model objects may refer to
	 * those of another.
	 * <p>Invocation of this method will not affect any packages that have
	 * already been initialized.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static PreferencesPackage init() {
		if (isInited) return (PreferencesPackage)EPackage.Registry.INSTANCE.getEPackage(PreferencesPackage.eNS_URI);

		// Obtain or create and register package
		PreferencesPackageImpl thePreferencesPackage = (PreferencesPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof PreferencesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new PreferencesPackageImpl());

		isInited = true;

		// Create package meta-data objects
		thePreferencesPackage.createPackageContents();

		// Initialize created meta-data
		thePreferencesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		thePreferencesPackage.freeze();

		return thePreferencesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAuthenticationInfo() {
		return authenticationInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAuthenticationInfo_UrlInfo() {
		return (EReference)authenticationInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAuthenticationInfo_UserInfos() {
		return (EReference)authenticationInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getURLInfo() {
		return urlInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getURLInfo_Urls() {
		return (EReference)urlInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getURL() {
		return urlEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getURL_UrlString() {
		return (EAttribute)urlEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCheckoutRepoInfo() {
		return checkoutRepoInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCheckoutRepoInfo_UrlInfo() {
		return (EReference)checkoutRepoInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRMSPreferences() {
		return rmsPreferencesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRMSPreferences_AuthInfo() {
		return (EReference)rmsPreferencesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRMSPreferences_CheckoutInfo() {
		return (EReference)rmsPreferencesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAuthenticationURLInfo() {
		return authenticationURLInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCheckoutURLInfo() {
		return checkoutURLInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUserInfo() {
		return userInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUserInfo_Username() {
		return (EAttribute)userInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUserInfo_Password() {
		return (EAttribute)userInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreferencesFactory getPreferencesFactory() {
		return (PreferencesFactory)getEFactoryInstance();
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
		authenticationInfoEClass = createEClass(AUTHENTICATION_INFO);
		createEReference(authenticationInfoEClass, AUTHENTICATION_INFO__URL_INFO);
		createEReference(authenticationInfoEClass, AUTHENTICATION_INFO__USER_INFOS);

		urlInfoEClass = createEClass(URL_INFO);
		createEReference(urlInfoEClass, URL_INFO__URLS);

		urlEClass = createEClass(URL);
		createEAttribute(urlEClass, URL__URL_STRING);

		checkoutRepoInfoEClass = createEClass(CHECKOUT_REPO_INFO);
		createEReference(checkoutRepoInfoEClass, CHECKOUT_REPO_INFO__URL_INFO);

		rmsPreferencesEClass = createEClass(RMS_PREFERENCES);
		createEReference(rmsPreferencesEClass, RMS_PREFERENCES__AUTH_INFO);
		createEReference(rmsPreferencesEClass, RMS_PREFERENCES__CHECKOUT_INFO);

		authenticationURLInfoEClass = createEClass(AUTHENTICATION_URL_INFO);

		checkoutURLInfoEClass = createEClass(CHECKOUT_URL_INFO);

		userInfoEClass = createEClass(USER_INFO);
		createEAttribute(userInfoEClass, USER_INFO__USERNAME);
		createEAttribute(userInfoEClass, USER_INFO__PASSWORD);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		authenticationURLInfoEClass.getESuperTypes().add(this.getURLInfo());
		checkoutURLInfoEClass.getESuperTypes().add(this.getURLInfo());

		// Initialize classes and features; add operations and parameters
		initEClass(authenticationInfoEClass, AuthenticationInfo.class, "AuthenticationInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAuthenticationInfo_UrlInfo(), this.getAuthenticationURLInfo(), null, "urlInfo", null, 0, 1, AuthenticationInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAuthenticationInfo_UserInfos(), this.getUserInfo(), null, "userInfos", null, 0, -1, AuthenticationInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(urlInfoEClass, URLInfo.class, "URLInfo", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getURLInfo_Urls(), this.getURL(), null, "urls", null, 0, -1, URLInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(urlEClass, com.tibco.cep.studio.rms.preferences.model.URL.class, "URL", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getURL_UrlString(), ecorePackage.getEString(), "urlString", null, 1, 1, com.tibco.cep.studio.rms.preferences.model.URL.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(checkoutRepoInfoEClass, CheckoutRepoInfo.class, "CheckoutRepoInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCheckoutRepoInfo_UrlInfo(), this.getCheckoutURLInfo(), null, "urlInfo", null, 0, 1, CheckoutRepoInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(rmsPreferencesEClass, RMSPreferences.class, "RMSPreferences", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRMSPreferences_AuthInfo(), this.getAuthenticationInfo(), null, "authInfo", null, 0, 1, RMSPreferences.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRMSPreferences_CheckoutInfo(), this.getCheckoutRepoInfo(), null, "checkoutInfo", null, 0, 1, RMSPreferences.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(authenticationURLInfoEClass, AuthenticationURLInfo.class, "AuthenticationURLInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(checkoutURLInfoEClass, CheckoutURLInfo.class, "CheckoutURLInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(userInfoEClass, UserInfo.class, "UserInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUserInfo_Username(), ecorePackage.getEString(), "username", null, 0, 1, UserInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUserInfo_Password(), ecorePackage.getEByteArray(), "password", null, 0, 1, UserInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //PreferencesPackageImpl
