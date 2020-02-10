/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.scope;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Compilable Scope Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.scope.CompilableScopeEntry#getRuleName <em>Rule Name</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.scope.CompilableScopeEntry#getScope <em>Scope</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.scope.ScopePackage#getCompilableScopeEntry()
 * @model
 * @generated
 */
public interface CompilableScopeEntry extends EObject {
	/**
	 * Returns the value of the '<em><b>Rule Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Name</em>' attribute.
	 * @see #setRuleName(String)
	 * @see com.tibco.cep.studio.core.index.model.scope.ScopePackage#getCompilableScopeEntry_RuleName()
	 * @model required="true"
	 * @generated
	 */
	String getRuleName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.scope.CompilableScopeEntry#getRuleName <em>Rule Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Name</em>' attribute.
	 * @see #getRuleName()
	 * @generated
	 */
	void setRuleName(String value);

	/**
	 * Returns the value of the '<em><b>Scope</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Scope</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scope</em>' containment reference.
	 * @see #setScope(CompilableScope)
	 * @see com.tibco.cep.studio.core.index.model.scope.ScopePackage#getCompilableScopeEntry_Scope()
	 * @model containment="true" required="true"
	 * @generated
	 */
	CompilableScope getScope();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.scope.CompilableScopeEntry#getScope <em>Scope</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scope</em>' containment reference.
	 * @see #getScope()
	 * @generated
	 */
	void setScope(CompilableScope value);

} // CompilableScopeEntry
