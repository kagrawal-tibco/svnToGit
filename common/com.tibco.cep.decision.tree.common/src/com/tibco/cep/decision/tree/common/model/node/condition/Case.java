/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node.condition;

import com.tibco.cep.decision.tree.common.model.FlowElement;
import com.tibco.cep.decision.tree.common.model.edge.Edge;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Case</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.condition.Case#getOutEdge <em>Out Edge</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decision.tree.common.model.node.condition.ConditionPackage#getCase()
 * @model
 * @generated
 */
public interface Case extends FlowElement {
	/**
	 * Returns the value of the '<em><b>Out Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Out Edge</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Out Edge</em>' reference.
	 * @see #setOutEdge(Edge)
	 * @see com.tibco.cep.decision.tree.common.model.node.condition.ConditionPackage#getCase_OutEdge()
	 * @model
	 * @generated
	 */
	Edge getOutEdge();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.tree.common.model.node.condition.Case#getOutEdge <em>Out Edge</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Out Edge</em>' reference.
	 * @see #getOutEdge()
	 * @generated
	 */
	void setOutEdge(Edge value);

} // Case
