/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node;

import com.tibco.cep.decision.tree.common.model.FlowElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Description Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.DescriptionElement#getAssocElement <em>Assoc Element</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decision.tree.common.model.node.NodePackage#getDescriptionElement()
 * @model
 * @generated
 */
public interface DescriptionElement extends NodeElement {
	/**
	 * Returns the value of the '<em><b>Assoc Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assoc Element</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assoc Element</em>' reference.
	 * @see #setAssocElement(FlowElement)
	 * @see com.tibco.cep.decision.tree.common.model.node.NodePackage#getDescriptionElement_AssocElement()
	 * @model required="true"
	 * @generated
	 */
	FlowElement getAssocElement();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.tree.common.model.node.DescriptionElement#getAssocElement <em>Assoc Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Assoc Element</em>' reference.
	 * @see #getAssocElement()
	 * @generated
	 */
	void setAssocElement(FlowElement value);

} // DescriptionElement
