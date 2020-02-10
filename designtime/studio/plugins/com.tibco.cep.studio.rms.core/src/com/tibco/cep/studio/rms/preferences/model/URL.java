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
 * A representation of the model object '<em><b>URL</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.rms.preferences.model.URL#getUrlString <em>Url String</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.rms.preferences.model.PreferencesPackage#getURL()
 * @model
 * @generated
 */
public interface URL extends EObject {
	/**
	 * Returns the value of the '<em><b>Url String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Url String</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Url String</em>' attribute.
	 * @see #setUrlString(String)
	 * @see com.tibco.cep.studio.rms.preferences.model.PreferencesPackage#getURL_UrlString()
	 * @model required="true"
	 * @generated
	 */
	String getUrlString();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.rms.preferences.model.URL#getUrlString <em>Url String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Url String</em>' attribute.
	 * @see #getUrlString()
	 * @generated
	 */
	void setUrlString(String value);

} // URL
