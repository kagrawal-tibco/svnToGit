/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model;

import java.util.Date;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decision.tree.common.model.edge.Edge;
import com.tibco.cep.decision.tree.common.model.node.NodeElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Decision Tree</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.DecisionTree#getFolder <em>Folder</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.DecisionTree#getOwnerProject <em>Owner Project</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.DecisionTree#getCreationDate <em>Creation Date</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.DecisionTree#getLastModified <em>Last Modified</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.DecisionTree#getNodes <em>Nodes</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.DecisionTree#getEdges <em>Edges</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.DecisionTree#getStartNode <em>Start Node</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decision.tree.common.model.ModelPackage#getDecisionTree()
 * @model
 * @generated
 */
public interface DecisionTree extends EObject {
	/**
	 * Returns the value of the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Folder</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Folder</em>' attribute.
	 * @see #setFolder(String)
	 * @see com.tibco.cep.decision.tree.common.model.ModelPackage#getDecisionTree_Folder()
	 * @model
	 * @generated
	 */
	String getFolder();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.tree.common.model.DecisionTree#getFolder <em>Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Folder</em>' attribute.
	 * @see #getFolder()
	 * @generated
	 */
	void setFolder(String value);

	/**
	 * Returns the value of the '<em><b>Owner Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner Project</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner Project</em>' attribute.
	 * @see #setOwnerProject(String)
	 * @see com.tibco.cep.decision.tree.common.model.ModelPackage#getDecisionTree_OwnerProject()
	 * @model
	 * @generated
	 */
	String getOwnerProject();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.tree.common.model.DecisionTree#getOwnerProject <em>Owner Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner Project</em>' attribute.
	 * @see #getOwnerProject()
	 * @generated
	 */
	void setOwnerProject(String value);

	/**
	 * Returns the value of the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Creation Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Creation Date</em>' attribute.
	 * @see #setCreationDate(Date)
	 * @see com.tibco.cep.decision.tree.common.model.ModelPackage#getDecisionTree_CreationDate()
	 * @model
	 * @generated
	 */
	Date getCreationDate();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.tree.common.model.DecisionTree#getCreationDate <em>Creation Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Creation Date</em>' attribute.
	 * @see #getCreationDate()
	 * @generated
	 */
	void setCreationDate(Date value);

	/**
	 * Returns the value of the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Last Modified</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Last Modified</em>' attribute.
	 * @see #setLastModified(Date)
	 * @see com.tibco.cep.decision.tree.common.model.ModelPackage#getDecisionTree_LastModified()
	 * @model
	 * @generated
	 */
	Date getLastModified();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.tree.common.model.DecisionTree#getLastModified <em>Last Modified</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Last Modified</em>' attribute.
	 * @see #getLastModified()
	 * @generated
	 */
	void setLastModified(Date value);

	/**
	 * Returns the value of the '<em><b>Nodes</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.decision.tree.common.model.node.NodeElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nodes</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nodes</em>' reference list.
	 * @see com.tibco.cep.decision.tree.common.model.ModelPackage#getDecisionTree_Nodes()
	 * @model
	 * @generated
	 */
	EList<NodeElement> getNodes();

	/**
	 * Returns the value of the '<em><b>Edges</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.decision.tree.common.model.edge.Edge}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edges</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edges</em>' reference list.
	 * @see com.tibco.cep.decision.tree.common.model.ModelPackage#getDecisionTree_Edges()
	 * @model
	 * @generated
	 */
	EList<Edge> getEdges();

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
	 * @see com.tibco.cep.decision.tree.common.model.ModelPackage#getDecisionTree_StartNode()
	 * @model
	 * @generated
	 */
	NodeElement getStartNode();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.tree.common.model.DecisionTree#getStartNode <em>Start Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Node</em>' reference.
	 * @see #getStartNode()
	 * @generated
	 */
	void setStartNode(NodeElement value);

} // DecisionTree
