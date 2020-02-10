/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.tester.emf.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Execution Object Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.ExecutionObjectType#getReteObject <em>Rete Object</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.ExecutionObjectType#getInvocationObject <em>Invocation Object</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.ExecutionObjectType#getCasualObjects <em>Casual Objects</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getExecutionObjectType()
 * @model extendedMetaData="name='ExecutionObjectType' kind='elementOnly'"
 * @generated
 */
public interface ExecutionObjectType extends EObject {
	/**
	 * Returns the value of the '<em><b>Rete Object</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rete Object</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rete Object</em>' containment reference.
	 * @see #setReteObject(ReteObjectType)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getExecutionObjectType_ReteObject()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='ReteObject' namespace='##targetNamespace'"
	 * @generated
	 */
	ReteObjectType getReteObject();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.ExecutionObjectType#getReteObject <em>Rete Object</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rete Object</em>' containment reference.
	 * @see #getReteObject()
	 * @generated
	 */
	void setReteObject(ReteObjectType value);

	/**
	 * Returns the value of the '<em><b>Invocation Object</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Invocation Object</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Invocation Object</em>' containment reference.
	 * @see #setInvocationObject(InvocationObjectType)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getExecutionObjectType_InvocationObject()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='InvocationObject' namespace='##targetNamespace'"
	 * @generated
	 */
	InvocationObjectType getInvocationObject();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.ExecutionObjectType#getInvocationObject <em>Invocation Object</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Invocation Object</em>' containment reference.
	 * @see #getInvocationObject()
	 * @generated
	 */
	void setInvocationObject(InvocationObjectType value);

	/**
	 * Returns the value of the '<em><b>Casual Objects</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Casual Objects</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Casual Objects</em>' containment reference.
	 * @see #setCasualObjects(CasualObjectsType)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getExecutionObjectType_CasualObjects()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='CausalObjects' namespace='##targetNamespace'"
	 * @generated
	 */
	CasualObjectsType getCasualObjects();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.ExecutionObjectType#getCasualObjects <em>Casual Objects</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Casual Objects</em>' containment reference.
	 * @see #getCasualObjects()
	 * @generated
	 */
	void setCasualObjects(CasualObjectsType value);

} // ExecutionObjectType
