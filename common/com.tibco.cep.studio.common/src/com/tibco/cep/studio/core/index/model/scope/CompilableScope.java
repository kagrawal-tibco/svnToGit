/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.scope;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.studio.core.index.model.GlobalVariableDef;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Compilable Scope</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.scope.CompilableScope#getGlobalVariables <em>Global Variables</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.scope.CompilableScope#getConditionScope <em>Condition Scope</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.scope.CompilableScope#getActionScope <em>Action Scope</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.scope.ScopePackage#getCompilableScope()
 * @model
 * @generated
 */
public interface CompilableScope extends EObject {
	/**
	 * Returns the value of the '<em><b>Global Variables</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.core.index.model.GlobalVariableDef}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Global Variables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Global Variables</em>' containment reference list.
	 * @see com.tibco.cep.studio.core.index.model.scope.ScopePackage#getCompilableScope_GlobalVariables()
	 * @model containment="true"
	 * @generated
	 */
	EList<GlobalVariableDef> getGlobalVariables();

	/**
	 * Returns the value of the '<em><b>Condition Scope</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition Scope</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition Scope</em>' containment reference.
	 * @see #setConditionScope(ScopeBlock)
	 * @see com.tibco.cep.studio.core.index.model.scope.ScopePackage#getCompilableScope_ConditionScope()
	 * @model containment="true"
	 * @generated
	 */
	ScopeBlock getConditionScope();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.scope.CompilableScope#getConditionScope <em>Condition Scope</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition Scope</em>' containment reference.
	 * @see #getConditionScope()
	 * @generated
	 */
	void setConditionScope(ScopeBlock value);

	/**
	 * Returns the value of the '<em><b>Action Scope</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action Scope</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action Scope</em>' containment reference.
	 * @see #setActionScope(ScopeBlock)
	 * @see com.tibco.cep.studio.core.index.model.scope.ScopePackage#getCompilableScope_ActionScope()
	 * @model containment="true"
	 * @generated
	 */
	ScopeBlock getActionScope();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.scope.CompilableScope#getActionScope <em>Action Scope</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Action Scope</em>' containment reference.
	 * @see #getActionScope()
	 * @generated
	 */
	void setActionScope(ScopeBlock value);

} // CompilableScope
