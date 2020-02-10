/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.studio.core.index.model.scope.CompilableScopeEntry;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State Machine Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.StateMachineElement#getCompilableScopes <em>Compilable Scopes</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.StateMachineElement#getIndexName <em>Index Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getStateMachineElement()
 * @model
 * @generated
 */
public interface StateMachineElement extends EntityElement {
	/**
	 * Returns the value of the '<em><b>Compilable Scopes</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.core.index.model.scope.CompilableScopeEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Compilable Scopes</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compilable Scopes</em>' containment reference list.
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getStateMachineElement_CompilableScopes()
	 * @model containment="true"
	 * @generated
	 */
	EList<CompilableScopeEntry> getCompilableScopes();

	/**
	 * Returns the value of the '<em><b>Index Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Index Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Index Name</em>' attribute.
	 * @see #setIndexName(String)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getStateMachineElement_IndexName()
	 * @model required="true"
	 * @generated
	 */
	String getIndexName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.StateMachineElement#getIndexName <em>Index Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Index Name</em>' attribute.
	 * @see #getIndexName()
	 * @generated
	 */
	void setIndexName(String value);

} // StateMachineElement
