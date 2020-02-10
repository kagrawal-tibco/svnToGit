/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.scope;

import com.tibco.cep.studio.core.index.model.ElementReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Root Scope Block</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.scope.RootScopeBlock#getDefinitionRef <em>Definition Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.scope.ScopePackage#getRootScopeBlock()
 * @model
 * @generated
 */
public interface RootScopeBlock extends ScopeBlock {
	/**
	 * Returns the value of the '<em><b>Definition Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Definition Ref</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Definition Ref</em>' containment reference.
	 * @see #setDefinitionRef(ElementReference)
	 * @see com.tibco.cep.studio.core.index.model.scope.ScopePackage#getRootScopeBlock_DefinitionRef()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ElementReference getDefinitionRef();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.scope.RootScopeBlock#getDefinitionRef <em>Definition Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Definition Ref</em>' containment reference.
	 * @see #getDefinitionRef()
	 * @generated
	 */
	void setDefinitionRef(ElementReference value);

} // RootScopeBlock
