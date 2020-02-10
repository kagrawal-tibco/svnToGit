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
 * A representation of the model object '<em><b>Checkout Repo Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.rms.preferences.model.CheckoutRepoInfo#getUrlInfo <em>Url Info</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.rms.preferences.model.PreferencesPackage#getCheckoutRepoInfo()
 * @model
 * @generated
 */
public interface CheckoutRepoInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Url Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Url Info</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Url Info</em>' containment reference.
	 * @see #setUrlInfo(CheckoutURLInfo)
	 * @see com.tibco.cep.studio.rms.preferences.model.PreferencesPackage#getCheckoutRepoInfo_UrlInfo()
	 * @model containment="true"
	 * @generated
	 */
	CheckoutURLInfo getUrlInfo();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.rms.preferences.model.CheckoutRepoInfo#getUrlInfo <em>Url Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Url Info</em>' containment reference.
	 * @see #getUrlInfo()
	 * @generated
	 */
	void setUrlInfo(CheckoutURLInfo value);

} // CheckoutRepoInfo
