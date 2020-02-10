/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node.condition;

import com.tibco.cep.decision.tree.common.model.edge.Edge;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bool Cond</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.condition.BoolCond#getFalseEdge <em>False Edge</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decision.tree.common.model.node.condition.ConditionPackage#getBoolCond()
 * @model
 * @generated
 */
public interface BoolCond extends Cond {
	/**
	 * Returns the value of the '<em><b>False Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>False Edge</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>False Edge</em>' reference.
	 * @see #setFalseEdge(Edge)
	 * @see com.tibco.cep.decision.tree.common.model.node.condition.ConditionPackage#getBoolCond_FalseEdge()
	 * @model
	 * @generated
	 */
	Edge getFalseEdge();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.tree.common.model.node.condition.BoolCond#getFalseEdge <em>False Edge</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>False Edge</em>' reference.
	 * @see #getFalseEdge()
	 * @generated
	 */
	void setFalseEdge(Edge value);

} // BoolCond
