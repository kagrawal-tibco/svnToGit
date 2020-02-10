/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node.action;

import com.tibco.cep.decision.tree.common.model.node.NodeElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Action</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.action.Action#getText <em>Text</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decision.tree.common.model.node.action.ActionPackage#getAction()
 * @model
 * @generated
 */
public interface Action extends NodeElement {
	/**
	 * Returns the value of the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Text</em>' attribute.
	 * @see #setText(String)
	 * @see com.tibco.cep.decision.tree.common.model.node.action.ActionPackage#getAction_Text()
	 * @model required="true"
	 * @generated
	 */
	String getText();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.tree.common.model.node.action.Action#getText <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Text</em>' attribute.
	 * @see #getText()
	 * @generated
	 */
	void setText(String value);

} // Action
