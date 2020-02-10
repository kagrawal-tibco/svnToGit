/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Enumerator;

import com.tibco.cep.decision.tree.common.model.FlowElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Loop</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.Loop#getLoopType <em>Loop Type</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.Loop#getLoopCondition <em>Loop Condition</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.Loop#getElements <em>Elements</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.Loop#getStartNode <em>Start Node</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.Loop#getEndNodes <em>End Nodes</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decision.tree.common.model.node.NodePackage#getLoop()
 * @model
 * @generated
 */
public interface Loop extends NodeElement {
	/**
	 * Returns the value of the '<em><b>Loop Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Loop Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Loop Type</em>' attribute.
	 * @see #setLoopType(Enumerator)
	 * @see com.tibco.cep.decision.tree.common.model.node.NodePackage#getLoop_LoopType()
	 * @model transient="true"
	 * @generated
	 */
	Enumerator getLoopType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.tree.common.model.node.Loop#getLoopType <em>Loop Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Loop Type</em>' attribute.
	 * @see #getLoopType()
	 * @generated
	 */
	void setLoopType(Enumerator value);

	/**
	 * Returns the value of the '<em><b>Loop Condition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Loop Condition</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Loop Condition</em>' attribute.
	 * @see #setLoopCondition(String)
	 * @see com.tibco.cep.decision.tree.common.model.node.NodePackage#getLoop_LoopCondition()
	 * @model
	 * @generated
	 */
	String getLoopCondition();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.tree.common.model.node.Loop#getLoopCondition <em>Loop Condition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Loop Condition</em>' attribute.
	 * @see #getLoopCondition()
	 * @generated
	 */
	void setLoopCondition(String value);

	/**
	 * Returns the value of the '<em><b>Elements</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.decision.tree.common.model.FlowElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Elements</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Elements</em>' reference list.
	 * @see com.tibco.cep.decision.tree.common.model.node.NodePackage#getLoop_Elements()
	 * @model
	 * @generated
	 */
	EList<FlowElement> getElements();

	/**
	 * Returns the value of the '<em><b>Start Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start Node</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Node</em>' reference.
	 * @see #setStartNode(NodeElement)
	 * @see com.tibco.cep.decision.tree.common.model.node.NodePackage#getLoop_StartNode()
	 * @model required="true"
	 * @generated
	 */
	NodeElement getStartNode();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.tree.common.model.node.Loop#getStartNode <em>Start Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Node</em>' reference.
	 * @see #getStartNode()
	 * @generated
	 */
	void setStartNode(NodeElement value);

	/**
	 * Returns the value of the '<em><b>End Nodes</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.decision.tree.common.model.node.NodeElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End Nodes</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End Nodes</em>' reference list.
	 * @see com.tibco.cep.decision.tree.common.model.node.NodePackage#getLoop_EndNodes()
	 * @model
	 * @generated
	 */
	EList<NodeElement> getEndNodes();

} // Loop
