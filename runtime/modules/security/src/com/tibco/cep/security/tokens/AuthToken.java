/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.security.tokens;

import java.io.Serializable;
import java.security.Principal;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Auth Token</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.security.tokens.AuthToken#getAuthen <em>Authen</em>}</li>
 *   <li>{@link com.tibco.cep.security.tokens.AuthToken#getAuthz <em>Authz</em>}</li>
 * </ul>
 * </p>
 *
 */
public interface AuthToken extends Principal, Serializable {
	/**
	 * Returns the value of the '<em><b>Authen</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authen</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authen</em>' containment reference.
	 * @see #setAuthen(Authen)
	 */
	Authen getAuthen();

	/**
	 * Sets the value of the '{@link com.tibco.cep.security.tokens.AuthToken#getAuthen <em>Authen</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Authen</em>' containment reference.
	 * @see #getAuthen()
	 */
	void setAuthen(Authen value);

	/**
	 * Returns the value of the '<em><b>Authz</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authz</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authz</em>' containment reference.
	 * @see #setAuthz(Authz)
	 */
	Authz getAuthz();

	/**
	 * Sets the value of the '{@link com.tibco.cep.security.tokens.AuthToken#getAuthz <em>Authz</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Authz</em>' containment reference.
	 * @see #getAuthz()
	 */
	void setAuthz(Authz value);

} // AuthToken
