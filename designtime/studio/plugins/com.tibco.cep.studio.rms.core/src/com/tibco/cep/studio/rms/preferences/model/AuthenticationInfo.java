/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.rms.preferences.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Authentication Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.rms.preferences.model.AuthenticationInfo#getUrlInfo <em>Url Info</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.preferences.model.AuthenticationInfo#getUserInfos <em>User Infos</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.rms.preferences.model.PreferencesPackage#getAuthenticationInfo()
 * @model
 * @generated
 */
public interface AuthenticationInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Url Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Url Info</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Url Info</em>' containment reference.
	 * @see #setUrlInfo(AuthenticationURLInfo)
	 * @see com.tibco.cep.studio.rms.preferences.model.PreferencesPackage#getAuthenticationInfo_UrlInfo()
	 * @model containment="true"
	 * @generated
	 */
	AuthenticationURLInfo getUrlInfo();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.rms.preferences.model.AuthenticationInfo#getUrlInfo <em>Url Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Url Info</em>' containment reference.
	 * @see #getUrlInfo()
	 * @generated
	 */
	void setUrlInfo(AuthenticationURLInfo value);

	/**
	 * Returns the value of the '<em><b>User Infos</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.rms.preferences.model.UserInfo}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User Infos</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Infos</em>' containment reference list.
	 * @see com.tibco.cep.studio.rms.preferences.model.PreferencesPackage#getAuthenticationInfo_UserInfos()
	 * @model containment="true"
	 * @generated
	 */
	EList<UserInfo> getUserInfos();

} // AuthenticationInfo
