/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.rms.preferences.model;

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
 * @see com.tibco.cep.studio.rms.preferences.model.PreferencesFactory
 * @model kind="package"
 * @generated
 */
public interface PreferencesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "model";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/studio/rms/preferences/RMSPrefs.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "rmsPrefs";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PreferencesPackage eINSTANCE = com.tibco.cep.studio.rms.preferences.model.impl.PreferencesPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.rms.preferences.model.impl.AuthenticationInfoImpl <em>Authentication Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.rms.preferences.model.impl.AuthenticationInfoImpl
	 * @see com.tibco.cep.studio.rms.preferences.model.impl.PreferencesPackageImpl#getAuthenticationInfo()
	 * @generated
	 */
	int AUTHENTICATION_INFO = 0;

	/**
	 * The feature id for the '<em><b>Url Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHENTICATION_INFO__URL_INFO = 0;

	/**
	 * The feature id for the '<em><b>User Infos</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHENTICATION_INFO__USER_INFOS = 1;

	/**
	 * The number of structural features of the '<em>Authentication Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHENTICATION_INFO_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.rms.preferences.model.impl.URLInfoImpl <em>URL Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.rms.preferences.model.impl.URLInfoImpl
	 * @see com.tibco.cep.studio.rms.preferences.model.impl.PreferencesPackageImpl#getURLInfo()
	 * @generated
	 */
	int URL_INFO = 1;

	/**
	 * The feature id for the '<em><b>Urls</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_INFO__URLS = 0;

	/**
	 * The number of structural features of the '<em>URL Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_INFO_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.rms.preferences.model.impl.URLImpl <em>URL</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.rms.preferences.model.impl.URLImpl
	 * @see com.tibco.cep.studio.rms.preferences.model.impl.PreferencesPackageImpl#getURL()
	 * @generated
	 */
	int URL = 2;

	/**
	 * The feature id for the '<em><b>Url String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL__URL_STRING = 0;

	/**
	 * The number of structural features of the '<em>URL</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URL_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.rms.preferences.model.impl.CheckoutRepoInfoImpl <em>Checkout Repo Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.rms.preferences.model.impl.CheckoutRepoInfoImpl
	 * @see com.tibco.cep.studio.rms.preferences.model.impl.PreferencesPackageImpl#getCheckoutRepoInfo()
	 * @generated
	 */
	int CHECKOUT_REPO_INFO = 3;

	/**
	 * The feature id for the '<em><b>Url Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHECKOUT_REPO_INFO__URL_INFO = 0;

	/**
	 * The number of structural features of the '<em>Checkout Repo Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHECKOUT_REPO_INFO_FEATURE_COUNT = 1;


	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.rms.preferences.model.impl.RMSPreferencesImpl <em>RMS Preferences</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.rms.preferences.model.impl.RMSPreferencesImpl
	 * @see com.tibco.cep.studio.rms.preferences.model.impl.PreferencesPackageImpl#getRMSPreferences()
	 * @generated
	 */
	int RMS_PREFERENCES = 4;

	/**
	 * The feature id for the '<em><b>Auth Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RMS_PREFERENCES__AUTH_INFO = 0;

	/**
	 * The feature id for the '<em><b>Checkout Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RMS_PREFERENCES__CHECKOUT_INFO = 1;

	/**
	 * The number of structural features of the '<em>RMS Preferences</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RMS_PREFERENCES_FEATURE_COUNT = 2;


	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.rms.preferences.model.impl.AuthenticationURLInfoImpl <em>Authentication URL Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.rms.preferences.model.impl.AuthenticationURLInfoImpl
	 * @see com.tibco.cep.studio.rms.preferences.model.impl.PreferencesPackageImpl#getAuthenticationURLInfo()
	 * @generated
	 */
	int AUTHENTICATION_URL_INFO = 5;

	/**
	 * The feature id for the '<em><b>Urls</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHENTICATION_URL_INFO__URLS = URL_INFO__URLS;

	/**
	 * The number of structural features of the '<em>Authentication URL Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHENTICATION_URL_INFO_FEATURE_COUNT = URL_INFO_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.rms.preferences.model.impl.CheckoutURLInfoImpl <em>Checkout URL Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.rms.preferences.model.impl.CheckoutURLInfoImpl
	 * @see com.tibco.cep.studio.rms.preferences.model.impl.PreferencesPackageImpl#getCheckoutURLInfo()
	 * @generated
	 */
	int CHECKOUT_URL_INFO = 6;

	/**
	 * The feature id for the '<em><b>Urls</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHECKOUT_URL_INFO__URLS = URL_INFO__URLS;

	/**
	 * The number of structural features of the '<em>Checkout URL Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHECKOUT_URL_INFO_FEATURE_COUNT = URL_INFO_FEATURE_COUNT + 0;


	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.rms.preferences.model.impl.UserInfoImpl <em>User Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.rms.preferences.model.impl.UserInfoImpl
	 * @see com.tibco.cep.studio.rms.preferences.model.impl.PreferencesPackageImpl#getUserInfo()
	 * @generated
	 */
	int USER_INFO = 7;

	/**
	 * The feature id for the '<em><b>Username</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_INFO__USERNAME = 0;

	/**
	 * The feature id for the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_INFO__PASSWORD = 1;

	/**
	 * The number of structural features of the '<em>User Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_INFO_FEATURE_COUNT = 2;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.rms.preferences.model.AuthenticationInfo <em>Authentication Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Authentication Info</em>'.
	 * @see com.tibco.cep.studio.rms.preferences.model.AuthenticationInfo
	 * @generated
	 */
	EClass getAuthenticationInfo();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.rms.preferences.model.AuthenticationInfo#getUrlInfo <em>Url Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Url Info</em>'.
	 * @see com.tibco.cep.studio.rms.preferences.model.AuthenticationInfo#getUrlInfo()
	 * @see #getAuthenticationInfo()
	 * @generated
	 */
	EReference getAuthenticationInfo_UrlInfo();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.rms.preferences.model.AuthenticationInfo#getUserInfos <em>User Infos</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>User Infos</em>'.
	 * @see com.tibco.cep.studio.rms.preferences.model.AuthenticationInfo#getUserInfos()
	 * @see #getAuthenticationInfo()
	 * @generated
	 */
	EReference getAuthenticationInfo_UserInfos();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.rms.preferences.model.URLInfo <em>URL Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>URL Info</em>'.
	 * @see com.tibco.cep.studio.rms.preferences.model.URLInfo
	 * @generated
	 */
	EClass getURLInfo();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.rms.preferences.model.URLInfo#getUrls <em>Urls</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Urls</em>'.
	 * @see com.tibco.cep.studio.rms.preferences.model.URLInfo#getUrls()
	 * @see #getURLInfo()
	 * @generated
	 */
	EReference getURLInfo_Urls();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.rms.preferences.model.URL <em>URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>URL</em>'.
	 * @see com.tibco.cep.studio.rms.preferences.model.URL
	 * @generated
	 */
	EClass getURL();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.rms.preferences.model.URL#getUrlString <em>Url String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Url String</em>'.
	 * @see com.tibco.cep.studio.rms.preferences.model.URL#getUrlString()
	 * @see #getURL()
	 * @generated
	 */
	EAttribute getURL_UrlString();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.rms.preferences.model.CheckoutRepoInfo <em>Checkout Repo Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Checkout Repo Info</em>'.
	 * @see com.tibco.cep.studio.rms.preferences.model.CheckoutRepoInfo
	 * @generated
	 */
	EClass getCheckoutRepoInfo();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.rms.preferences.model.CheckoutRepoInfo#getUrlInfo <em>Url Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Url Info</em>'.
	 * @see com.tibco.cep.studio.rms.preferences.model.CheckoutRepoInfo#getUrlInfo()
	 * @see #getCheckoutRepoInfo()
	 * @generated
	 */
	EReference getCheckoutRepoInfo_UrlInfo();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.rms.preferences.model.RMSPreferences <em>RMS Preferences</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RMS Preferences</em>'.
	 * @see com.tibco.cep.studio.rms.preferences.model.RMSPreferences
	 * @generated
	 */
	EClass getRMSPreferences();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.rms.preferences.model.RMSPreferences#getAuthInfo <em>Auth Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Auth Info</em>'.
	 * @see com.tibco.cep.studio.rms.preferences.model.RMSPreferences#getAuthInfo()
	 * @see #getRMSPreferences()
	 * @generated
	 */
	EReference getRMSPreferences_AuthInfo();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.rms.preferences.model.RMSPreferences#getCheckoutInfo <em>Checkout Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Checkout Info</em>'.
	 * @see com.tibco.cep.studio.rms.preferences.model.RMSPreferences#getCheckoutInfo()
	 * @see #getRMSPreferences()
	 * @generated
	 */
	EReference getRMSPreferences_CheckoutInfo();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.rms.preferences.model.AuthenticationURLInfo <em>Authentication URL Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Authentication URL Info</em>'.
	 * @see com.tibco.cep.studio.rms.preferences.model.AuthenticationURLInfo
	 * @generated
	 */
	EClass getAuthenticationURLInfo();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.rms.preferences.model.CheckoutURLInfo <em>Checkout URL Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Checkout URL Info</em>'.
	 * @see com.tibco.cep.studio.rms.preferences.model.CheckoutURLInfo
	 * @generated
	 */
	EClass getCheckoutURLInfo();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.rms.preferences.model.UserInfo <em>User Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>User Info</em>'.
	 * @see com.tibco.cep.studio.rms.preferences.model.UserInfo
	 * @generated
	 */
	EClass getUserInfo();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.rms.preferences.model.UserInfo#getUsername <em>Username</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Username</em>'.
	 * @see com.tibco.cep.studio.rms.preferences.model.UserInfo#getUsername()
	 * @see #getUserInfo()
	 * @generated
	 */
	EAttribute getUserInfo_Username();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.rms.preferences.model.UserInfo#getPassword <em>Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Password</em>'.
	 * @see com.tibco.cep.studio.rms.preferences.model.UserInfo#getPassword()
	 * @see #getUserInfo()
	 * @generated
	 */
	EAttribute getUserInfo_Password();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PreferencesFactory getPreferencesFactory();

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
		 * The meta object literal for the '{@link com.tibco.cep.studio.rms.preferences.model.impl.AuthenticationInfoImpl <em>Authentication Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.rms.preferences.model.impl.AuthenticationInfoImpl
		 * @see com.tibco.cep.studio.rms.preferences.model.impl.PreferencesPackageImpl#getAuthenticationInfo()
		 * @generated
		 */
		EClass AUTHENTICATION_INFO = eINSTANCE.getAuthenticationInfo();

		/**
		 * The meta object literal for the '<em><b>Url Info</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTHENTICATION_INFO__URL_INFO = eINSTANCE.getAuthenticationInfo_UrlInfo();

		/**
		 * The meta object literal for the '<em><b>User Infos</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTHENTICATION_INFO__USER_INFOS = eINSTANCE.getAuthenticationInfo_UserInfos();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.rms.preferences.model.impl.URLInfoImpl <em>URL Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.rms.preferences.model.impl.URLInfoImpl
		 * @see com.tibco.cep.studio.rms.preferences.model.impl.PreferencesPackageImpl#getURLInfo()
		 * @generated
		 */
		EClass URL_INFO = eINSTANCE.getURLInfo();

		/**
		 * The meta object literal for the '<em><b>Urls</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference URL_INFO__URLS = eINSTANCE.getURLInfo_Urls();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.rms.preferences.model.impl.URLImpl <em>URL</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.rms.preferences.model.impl.URLImpl
		 * @see com.tibco.cep.studio.rms.preferences.model.impl.PreferencesPackageImpl#getURL()
		 * @generated
		 */
		EClass URL = eINSTANCE.getURL();

		/**
		 * The meta object literal for the '<em><b>Url String</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute URL__URL_STRING = eINSTANCE.getURL_UrlString();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.rms.preferences.model.impl.CheckoutRepoInfoImpl <em>Checkout Repo Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.rms.preferences.model.impl.CheckoutRepoInfoImpl
		 * @see com.tibco.cep.studio.rms.preferences.model.impl.PreferencesPackageImpl#getCheckoutRepoInfo()
		 * @generated
		 */
		EClass CHECKOUT_REPO_INFO = eINSTANCE.getCheckoutRepoInfo();

		/**
		 * The meta object literal for the '<em><b>Url Info</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHECKOUT_REPO_INFO__URL_INFO = eINSTANCE.getCheckoutRepoInfo_UrlInfo();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.rms.preferences.model.impl.RMSPreferencesImpl <em>RMS Preferences</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.rms.preferences.model.impl.RMSPreferencesImpl
		 * @see com.tibco.cep.studio.rms.preferences.model.impl.PreferencesPackageImpl#getRMSPreferences()
		 * @generated
		 */
		EClass RMS_PREFERENCES = eINSTANCE.getRMSPreferences();

		/**
		 * The meta object literal for the '<em><b>Auth Info</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RMS_PREFERENCES__AUTH_INFO = eINSTANCE.getRMSPreferences_AuthInfo();

		/**
		 * The meta object literal for the '<em><b>Checkout Info</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RMS_PREFERENCES__CHECKOUT_INFO = eINSTANCE.getRMSPreferences_CheckoutInfo();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.rms.preferences.model.impl.AuthenticationURLInfoImpl <em>Authentication URL Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.rms.preferences.model.impl.AuthenticationURLInfoImpl
		 * @see com.tibco.cep.studio.rms.preferences.model.impl.PreferencesPackageImpl#getAuthenticationURLInfo()
		 * @generated
		 */
		EClass AUTHENTICATION_URL_INFO = eINSTANCE.getAuthenticationURLInfo();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.rms.preferences.model.impl.CheckoutURLInfoImpl <em>Checkout URL Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.rms.preferences.model.impl.CheckoutURLInfoImpl
		 * @see com.tibco.cep.studio.rms.preferences.model.impl.PreferencesPackageImpl#getCheckoutURLInfo()
		 * @generated
		 */
		EClass CHECKOUT_URL_INFO = eINSTANCE.getCheckoutURLInfo();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.rms.preferences.model.impl.UserInfoImpl <em>User Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.rms.preferences.model.impl.UserInfoImpl
		 * @see com.tibco.cep.studio.rms.preferences.model.impl.PreferencesPackageImpl#getUserInfo()
		 * @generated
		 */
		EClass USER_INFO = eINSTANCE.getUserInfo();

		/**
		 * The meta object literal for the '<em><b>Username</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER_INFO__USERNAME = eINSTANCE.getUserInfo_Username();

		/**
		 * The meta object literal for the '<em><b>Password</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER_INFO__PASSWORD = eINSTANCE.getUserInfo_Password();

	}

} //PreferencesPackage
