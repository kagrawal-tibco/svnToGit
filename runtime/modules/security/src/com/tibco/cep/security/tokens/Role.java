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
 * A representation of the model object '<em><b>Role</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.security.tokens.Role#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.security.tokens.Role#getUsers <em>Users</em>}</li>
 * </ul>
 * </p>
 *
 */
public interface Role extends Principal, Serializable {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.security.tokens.Role#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 */
	void setName(String value);

	/**
	 * Two roles are semantically equivalent based on their names
	 * @param other
	 * @return
	 */
	boolean isSemanticallyEquivalent(Role other);

} // Role
