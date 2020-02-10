/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.edge;

import com.tibco.cep.decision.tree.common.model.FlowElement;
import com.tibco.cep.decision.tree.common.model.node.NodeElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Edge</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.edge.Edge#getSrc <em>Src</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.edge.Edge#getTgt <em>Tgt</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decision.tree.common.model.edge.EdgePackage#getEdge()
 * @model
 * @generated
 */
public interface Edge extends FlowElement {
	/**
	 * Returns the value of the '<em><b>Src</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Src</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Src</em>' reference.
	 * @see #setSrc(NodeElement)
	 * @see com.tibco.cep.decision.tree.common.model.edge.EdgePackage#getEdge_Src()
	 * @model required="true"
	 * @generated
	 */
	NodeElement getSrc();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.tree.common.model.edge.Edge#getSrc <em>Src</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Src</em>' reference.
	 * @see #getSrc()
	 * @generated
	 */
	void setSrc(NodeElement value);

	/**
	 * Returns the value of the '<em><b>Tgt</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tgt</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tgt</em>' reference.
	 * @see #setTgt(NodeElement)
	 * @see com.tibco.cep.decision.tree.common.model.edge.EdgePackage#getEdge_Tgt()
	 * @model required="true"
	 * @generated
	 */
	NodeElement getTgt();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.tree.common.model.edge.Edge#getTgt <em>Tgt</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tgt</em>' reference.
	 * @see #getTgt()
	 * @generated
	 */
	void setTgt(NodeElement value);

} // Edge
