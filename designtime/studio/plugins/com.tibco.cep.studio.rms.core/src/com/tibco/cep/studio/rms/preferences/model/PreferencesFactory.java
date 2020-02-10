/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.rms.preferences.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.rms.preferences.model.PreferencesPackage
 * @generated
 */
public interface PreferencesFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PreferencesFactory eINSTANCE = com.tibco.cep.studio.rms.preferences.model.impl.PreferencesFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Authentication Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Authentication Info</em>'.
	 * @generated
	 */
	AuthenticationInfo createAuthenticationInfo();

	/**
	 * Returns a new object of class '<em>URL</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>URL</em>'.
	 * @generated
	 */
	URL createURL();

	/**
	 * Returns a new object of class '<em>Checkout Repo Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Checkout Repo Info</em>'.
	 * @generated
	 */
	CheckoutRepoInfo createCheckoutRepoInfo();

	/**
	 * Returns a new object of class '<em>RMS Preferences</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>RMS Preferences</em>'.
	 * @generated
	 */
	RMSPreferences createRMSPreferences();

	/**
	 * Returns a new object of class '<em>Authentication URL Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Authentication URL Info</em>'.
	 * @generated
	 */
	AuthenticationURLInfo createAuthenticationURLInfo();

	/**
	 * Returns a new object of class '<em>Checkout URL Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Checkout URL Info</em>'.
	 * @generated
	 */
	CheckoutURLInfo createCheckoutURLInfo();

	/**
	 * Returns a new object of class '<em>User Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>User Info</em>'.
	 * @generated
	 */
	UserInfo createUserInfo();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	PreferencesPackage getPreferencesPackage();

} //PreferencesFactory
