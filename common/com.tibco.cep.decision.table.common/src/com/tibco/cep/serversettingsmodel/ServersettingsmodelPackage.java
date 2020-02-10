/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.serversettingsmodel;

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
 * @see com.tibco.cep.serversettingsmodel.ServersettingsmodelFactory
 * @model kind="package"
 * @generated
 */
public interface ServersettingsmodelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "serversettingsmodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/bui/setting/model/serversettings.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "serversettings";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ServersettingsmodelPackage eINSTANCE = com.tibco.cep.serversettingsmodel.impl.ServersettingsmodelPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.serversettingsmodel.impl.AuthenticationURLImpl <em>Authentication URL</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.serversettingsmodel.impl.AuthenticationURLImpl
	 * @see com.tibco.cep.serversettingsmodel.impl.ServersettingsmodelPackageImpl#getAuthenticationURL()
	 * @generated
	 */
	int AUTHENTICATION_URL = 0;

	/**
	 * The feature id for the '<em><b>URL</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHENTICATION_URL__URL = 0;

	/**
	 * The feature id for the '<em><b>Authentication</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHENTICATION_URL__AUTHENTICATION = 1;

	/**
	 * The number of structural features of the '<em>Authentication URL</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHENTICATION_URL_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.serversettingsmodel.impl.CheckoutURLImpl <em>Checkout URL</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.serversettingsmodel.impl.CheckoutURLImpl
	 * @see com.tibco.cep.serversettingsmodel.impl.ServersettingsmodelPackageImpl#getCheckoutURL()
	 * @generated
	 */
	int CHECKOUT_URL = 1;

	/**
	 * The feature id for the '<em><b>URL</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHECKOUT_URL__URL = 0;

	/**
	 * The number of structural features of the '<em>Checkout URL</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHECKOUT_URL_FEATURE_COUNT = 1;


	/**
	 * The meta object id for the '{@link com.tibco.cep.serversettingsmodel.impl.AuthenticationImpl <em>Authentication</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.serversettingsmodel.impl.AuthenticationImpl
	 * @see com.tibco.cep.serversettingsmodel.impl.ServersettingsmodelPackageImpl#getAuthentication()
	 * @generated
	 */
	int AUTHENTICATION = 2;

	/**
	 * The feature id for the '<em><b>Username</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHENTICATION__USERNAME = 0;

	/**
	 * The feature id for the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHENTICATION__PASSWORD = 1;

	/**
	 * The feature id for the '<em><b>Save Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHENTICATION__SAVE_PASSWORD = 2;

	/**
	 * The number of structural features of the '<em>Authentication</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHENTICATION_FEATURE_COUNT = 3;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.serversettingsmodel.AuthenticationURL <em>Authentication URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Authentication URL</em>'.
	 * @see com.tibco.cep.serversettingsmodel.AuthenticationURL
	 * @generated
	 */
	EClass getAuthenticationURL();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.serversettingsmodel.AuthenticationURL#getURL <em>URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>URL</em>'.
	 * @see com.tibco.cep.serversettingsmodel.AuthenticationURL#getURL()
	 * @see #getAuthenticationURL()
	 * @generated
	 */
	EAttribute getAuthenticationURL_URL();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.serversettingsmodel.AuthenticationURL#getAuthentication <em>Authentication</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Authentication</em>'.
	 * @see com.tibco.cep.serversettingsmodel.AuthenticationURL#getAuthentication()
	 * @see #getAuthenticationURL()
	 * @generated
	 */
	EReference getAuthenticationURL_Authentication();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.serversettingsmodel.CheckoutURL <em>Checkout URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Checkout URL</em>'.
	 * @see com.tibco.cep.serversettingsmodel.CheckoutURL
	 * @generated
	 */
	EClass getCheckoutURL();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.serversettingsmodel.CheckoutURL#getURL <em>URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>URL</em>'.
	 * @see com.tibco.cep.serversettingsmodel.CheckoutURL#getURL()
	 * @see #getCheckoutURL()
	 * @generated
	 */
	EAttribute getCheckoutURL_URL();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.serversettingsmodel.Authentication <em>Authentication</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Authentication</em>'.
	 * @see com.tibco.cep.serversettingsmodel.Authentication
	 * @generated
	 */
	EClass getAuthentication();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.serversettingsmodel.Authentication#getUsername <em>Username</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Username</em>'.
	 * @see com.tibco.cep.serversettingsmodel.Authentication#getUsername()
	 * @see #getAuthentication()
	 * @generated
	 */
	EAttribute getAuthentication_Username();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.serversettingsmodel.Authentication#getPassword <em>Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Password</em>'.
	 * @see com.tibco.cep.serversettingsmodel.Authentication#getPassword()
	 * @see #getAuthentication()
	 * @generated
	 */
	EAttribute getAuthentication_Password();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.serversettingsmodel.Authentication#isSavePassword <em>Save Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Save Password</em>'.
	 * @see com.tibco.cep.serversettingsmodel.Authentication#isSavePassword()
	 * @see #getAuthentication()
	 * @generated
	 */
	EAttribute getAuthentication_SavePassword();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ServersettingsmodelFactory getServersettingsmodelFactory();

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
		 * The meta object literal for the '{@link com.tibco.cep.serversettingsmodel.impl.AuthenticationURLImpl <em>Authentication URL</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.serversettingsmodel.impl.AuthenticationURLImpl
		 * @see com.tibco.cep.serversettingsmodel.impl.ServersettingsmodelPackageImpl#getAuthenticationURL()
		 * @generated
		 */
		EClass AUTHENTICATION_URL = eINSTANCE.getAuthenticationURL();

		/**
		 * The meta object literal for the '<em><b>URL</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHENTICATION_URL__URL = eINSTANCE.getAuthenticationURL_URL();

		/**
		 * The meta object literal for the '<em><b>Authentication</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTHENTICATION_URL__AUTHENTICATION = eINSTANCE.getAuthenticationURL_Authentication();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.serversettingsmodel.impl.CheckoutURLImpl <em>Checkout URL</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.serversettingsmodel.impl.CheckoutURLImpl
		 * @see com.tibco.cep.serversettingsmodel.impl.ServersettingsmodelPackageImpl#getCheckoutURL()
		 * @generated
		 */
		EClass CHECKOUT_URL = eINSTANCE.getCheckoutURL();

		/**
		 * The meta object literal for the '<em><b>URL</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHECKOUT_URL__URL = eINSTANCE.getCheckoutURL_URL();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.serversettingsmodel.impl.AuthenticationImpl <em>Authentication</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.serversettingsmodel.impl.AuthenticationImpl
		 * @see com.tibco.cep.serversettingsmodel.impl.ServersettingsmodelPackageImpl#getAuthentication()
		 * @generated
		 */
		EClass AUTHENTICATION = eINSTANCE.getAuthentication();

		/**
		 * The meta object literal for the '<em><b>Username</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHENTICATION__USERNAME = eINSTANCE.getAuthentication_Username();

		/**
		 * The meta object literal for the '<em><b>Password</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHENTICATION__PASSWORD = eINSTANCE.getAuthentication_Password();

		/**
		 * The meta object literal for the '<em><b>Save Password</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHENTICATION__SAVE_PASSWORD = eINSTANCE.getAuthentication_SavePassword();

	}

} //ServersettingsmodelPackage
