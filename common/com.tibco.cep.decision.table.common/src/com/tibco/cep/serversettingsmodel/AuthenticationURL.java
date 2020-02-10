/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.serversettingsmodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Authentication URL</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.serversettingsmodel.AuthenticationURL#getURL <em>URL</em>}</li>
 *   <li>{@link com.tibco.cep.serversettingsmodel.AuthenticationURL#getAuthentication <em>Authentication</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.serversettingsmodel.ServersettingsmodelPackage#getAuthenticationURL()
 * @model
 * @generated
 */
public interface AuthenticationURL extends EObject {
	/**
	 * Returns the value of the '<em><b>URL</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>URL</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>URL</em>' attribute list.
	 * @see com.tibco.cep.serversettingsmodel.ServersettingsmodelPackage#getAuthenticationURL_URL()
	 * @model
	 * @generated
	 */
	EList<String> getURL();

	/**
	 * Returns the value of the '<em><b>Authentication</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authentication</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authentication</em>' reference.
	 * @see #setAuthentication(Authentication)
	 * @see com.tibco.cep.serversettingsmodel.ServersettingsmodelPackage#getAuthenticationURL_Authentication()
	 * @model
	 * @generated
	 */
	Authentication getAuthentication();

	/**
	 * Sets the value of the '{@link com.tibco.cep.serversettingsmodel.AuthenticationURL#getAuthentication <em>Authentication</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Authentication</em>' reference.
	 * @see #getAuthentication()
	 * @generated
	 */
	void setAuthentication(Authentication value);

} // AuthenticationURL
