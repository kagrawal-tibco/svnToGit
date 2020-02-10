/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.security.tokens;

import java.io.Serializable;
import java.util.List;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Authz</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.security.tokens.Authz#getRoles <em>Roles</em>}</li>
 * </ul>
 * </p>
 *
 */
public interface Authz extends Serializable {
	/**
	 * Returns the value of the '<em><b>Roles</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.security.tokens.Role}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Roles</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Roles</em>' containment reference list.
	 */
	List<Role> getRoles();

} // Authz
