/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.rms.preferences.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>RMS Preferences</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.rms.preferences.model.RMSPreferences#getAuthInfo <em>Auth Info</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.preferences.model.RMSPreferences#getCheckoutInfo <em>Checkout Info</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.rms.preferences.model.PreferencesPackage#getRMSPreferences()
 * @model
 * @generated
 */
public interface RMSPreferences extends EObject {
	/**
	 * Returns the value of the '<em><b>Auth Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Auth Info</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Auth Info</em>' containment reference.
	 * @see #setAuthInfo(AuthenticationInfo)
	 * @see com.tibco.cep.studio.rms.preferences.model.PreferencesPackage#getRMSPreferences_AuthInfo()
	 * @model containment="true"
	 * @generated
	 */
	AuthenticationInfo getAuthInfo();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.rms.preferences.model.RMSPreferences#getAuthInfo <em>Auth Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Auth Info</em>' containment reference.
	 * @see #getAuthInfo()
	 * @generated
	 */
	void setAuthInfo(AuthenticationInfo value);

	/**
	 * Returns the value of the '<em><b>Checkout Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Checkout Info</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Checkout Info</em>' containment reference.
	 * @see #setCheckoutInfo(CheckoutRepoInfo)
	 * @see com.tibco.cep.studio.rms.preferences.model.PreferencesPackage#getRMSPreferences_CheckoutInfo()
	 * @model containment="true"
	 * @generated
	 */
	CheckoutRepoInfo getCheckoutInfo();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.rms.preferences.model.RMSPreferences#getCheckoutInfo <em>Checkout Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Checkout Info</em>' containment reference.
	 * @see #getCheckoutInfo()
	 * @generated
	 */
	void setCheckoutInfo(CheckoutRepoInfo value);

} // RMSPreferences
