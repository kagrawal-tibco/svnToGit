/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model;

import org.eclipse.emf.ecore.EObject;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Shared Decision Table Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.SharedDecisionTableElement#getSharedImplementation <em>Shared Implementation</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getSharedDecisionTableElement()
 * @model
 * @generated
 */
public interface SharedDecisionTableElement extends SharedElement, DecisionTableElement {

	/**
	 * Returns the value of the '<em><b>Shared Implementation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shared Implementation</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shared Implementation</em>' containment reference.
	 * @see #setSharedImplementation(EObject)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getSharedDecisionTableElement_SharedImplementation()
	 * @model containment="true" required="true" transient="true"
	 * @generated
	 */
	EObject getSharedImplementation();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.SharedDecisionTableElement#getSharedImplementation <em>Shared Implementation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shared Implementation</em>' containment reference.
	 * @see #getSharedImplementation()
	 * @generated
	 */
	void setSharedImplementation(EObject value);
} // SharedDecisionTableElement
