/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node.action;

import com.tibco.cep.decision.tree.common.model.DecisionTree;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Call Decision Tree Action</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.action.CallDecisionTreeAction#getCallTree <em>Call Tree</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decision.tree.common.model.node.action.ActionPackage#getCallDecisionTreeAction()
 * @model
 * @generated
 */
public interface CallDecisionTreeAction extends Action {
	/**
	 * Returns the value of the '<em><b>Call Tree</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Call Tree</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Call Tree</em>' reference.
	 * @see #setCallTree(DecisionTree)
	 * @see com.tibco.cep.decision.tree.common.model.node.action.ActionPackage#getCallDecisionTreeAction_CallTree()
	 * @model required="true"
	 * @generated
	 */
	DecisionTree getCallTree();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.tree.common.model.node.action.CallDecisionTreeAction#getCallTree <em>Call Tree</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Call Tree</em>' reference.
	 * @see #getCallTree()
	 * @generated
	 */
	void setCallTree(DecisionTree value);

} // CallDecisionTreeAction
