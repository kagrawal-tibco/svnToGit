/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.webstudio.model.rule.instance;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Clause</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.webstudio.model.rule.instance.Clause#getSubClause <em>Sub Clause</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public interface Clause extends Filter {
	
	public final int SUB_CLAUSE_FEATURE_ID = 0;

	/**
	 * Returns the value of the '<em><b>Sub Clause</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub Clause</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Clause</em>' containment reference.
	 * @see #setSubClause(BuilderSubClause)
	 * @generated
	 */
	BuilderSubClause getSubClause();

	/**
	 * Sets the value of the '{@link com.tibco.cep.webstudio.model.rule.instance.Clause#getSubClause <em>Sub Clause</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sub Clause</em>' containment reference.
	 * @see #getSubClause()
	 * @generated
	 */
	void setSubClause(BuilderSubClause value);

} // Clause
