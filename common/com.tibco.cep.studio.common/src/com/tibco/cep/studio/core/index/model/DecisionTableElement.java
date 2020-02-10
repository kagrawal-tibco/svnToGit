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
 * A representation of the model object '<em><b>Decision Table Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.DecisionTableElement#getImplementation <em>Implementation</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getDecisionTableElement()
 * @model
 * @generated
 */
public interface DecisionTableElement extends TypeElement {
	/**
	 * Returns the value of the '<em><b>Implementation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implementation</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implementation</em>' reference.
	 * @see #setImplementation(EObject)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getDecisionTableElement_Implementation()
	 * @model required="true"
	 * @generated
	 */
	EObject getImplementation();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.DecisionTableElement#getImplementation <em>Implementation</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Implementation</em>' reference.
	 * @see #getImplementation()
	 * @generated
	 */
	void setImplementation(EObject value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorType="com.tibco.cep.studio.core.index.model.IStructuredElementVisitor"
	 * @generated
	 */
	void accept(IStructuredElementVisitor visitor);

} // DecisionTableElement
