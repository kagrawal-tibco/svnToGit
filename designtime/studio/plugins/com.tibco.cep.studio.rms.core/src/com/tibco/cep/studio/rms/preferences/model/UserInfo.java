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
 * A representation of the model object '<em><b>User Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.rms.preferences.model.UserInfo#getUsername <em>Username</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.preferences.model.UserInfo#getPassword <em>Password</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.rms.preferences.model.PreferencesPackage#getUserInfo()
 * @model
 * @generated
 */
public interface UserInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Username</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Username</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Username</em>' attribute.
	 * @see #setUsername(String)
	 * @see com.tibco.cep.studio.rms.preferences.model.PreferencesPackage#getUserInfo_Username()
	 * @model
	 * @generated
	 */
	String getUsername();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.rms.preferences.model.UserInfo#getUsername <em>Username</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Username</em>' attribute.
	 * @see #getUsername()
	 * @generated
	 */
	void setUsername(String value);

	/**
	 * Returns the value of the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Password</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Password</em>' attribute.
	 * @see #setPassword(byte[])
	 * @see com.tibco.cep.studio.rms.preferences.model.PreferencesPackage#getUserInfo_Password()
	 * @model
	 * @generated
	 */
	byte[] getPassword();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.rms.preferences.model.UserInfo#getPassword <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Password</em>' attribute.
	 * @see #getPassword()
	 * @generated
	 */
	void setPassword(byte[] value);

} // UserInfo
