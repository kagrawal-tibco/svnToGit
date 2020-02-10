/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decision.tree.common.model.edge.Association;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Flow Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.FlowElement#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.FlowElement#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.FlowElement#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.FlowElement#getData <em>Data</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.FlowElement#getAssocEdge <em>Assoc Edge</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decision.tree.common.model.ModelPackage#getFlowElement()
 * @model
 * @generated
 */
public interface FlowElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(long)
	 * @see com.tibco.cep.decision.tree.common.model.ModelPackage#getFlowElement_Id()
	 * @model id="true" required="true"
	 * @generated
	 */
	long getId();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.tree.common.model.FlowElement#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(long value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.tibco.cep.decision.tree.common.model.ModelPackage#getFlowElement_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.tree.common.model.FlowElement#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see com.tibco.cep.decision.tree.common.model.ModelPackage#getFlowElement_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.tree.common.model.FlowElement#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data</em>' attribute.
	 * @see #setData(Object)
	 * @see com.tibco.cep.decision.tree.common.model.ModelPackage#getFlowElement_Data()
	 * @model
	 * @generated
	 */
	Object getData();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.tree.common.model.FlowElement#getData <em>Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data</em>' attribute.
	 * @see #getData()
	 * @generated
	 */
	void setData(Object value);

	/**
	 * Returns the value of the '<em><b>Assoc Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assoc Edge</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assoc Edge</em>' reference.
	 * @see #setAssocEdge(Association)
	 * @see com.tibco.cep.decision.tree.common.model.ModelPackage#getFlowElement_AssocEdge()
	 * @model
	 * @generated
	 */
	Association getAssocEdge();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.tree.common.model.FlowElement#getAssocEdge <em>Assoc Edge</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Assoc Edge</em>' reference.
	 * @see #getAssocEdge()
	 * @generated
	 */
	void setAssocEdge(Association value);

} // FlowElement
