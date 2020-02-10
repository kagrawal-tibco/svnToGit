/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.decision.tree.common.model.FlowElement;
import com.tibco.cep.decision.tree.common.model.edge.Edge;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.NodeElement#getInEdges <em>In Edges</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.NodeElement#getOutEdge <em>Out Edge</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decision.tree.common.model.node.NodePackage#getNodeElement()
 * @model
 * @generated
 */
public interface NodeElement extends FlowElement {
	/**
	 * Returns the value of the '<em><b>In Edges</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.decision.tree.common.model.edge.Edge}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>In Edges</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>In Edges</em>' reference list.
	 * @see com.tibco.cep.decision.tree.common.model.node.NodePackage#getNodeElement_InEdges()
	 * @model
	 * @generated
	 */
	EList<Edge> getInEdges();

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
	 * @see com.tibco.cep.decision.tree.common.model.node.NodePackage#getNodeElement_OutEdge()
	 * @model
	 * @generated
	 */
	Edge getOutEdge();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.tree.common.model.node.NodeElement#getOutEdge <em>Out Edge</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Out Edge</em>' reference.
	 * @see #getOutEdge()
	 * @generated
	 */
	void setOutEdge(Edge value);

} // NodeElement
