/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model;

import com.tibco.cep.studio.core.index.model.scope.CompilableScopeEntry;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.EventElement#getExpiryActionScopeEntry <em>Expiry Action Scope Entry</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getEventElement()
 * @model
 * @generated
 */
public interface EventElement extends EntityElement {
	/**
	 * Returns the value of the '<em><b>Expiry Action Scope Entry</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expiry Action Scope Entry</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expiry Action Scope Entry</em>' containment reference.
	 * @see #setExpiryActionScopeEntry(CompilableScopeEntry)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getEventElement_ExpiryActionScopeEntry()
	 * @model containment="true"
	 * @generated
	 */
	CompilableScopeEntry getExpiryActionScopeEntry();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.EventElement#getExpiryActionScopeEntry <em>Expiry Action Scope Entry</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expiry Action Scope Entry</em>' containment reference.
	 * @see #getExpiryActionScopeEntry()
	 * @generated
	 */
	void setExpiryActionScopeEntry(CompilableScopeEntry value);

} // EventElement
