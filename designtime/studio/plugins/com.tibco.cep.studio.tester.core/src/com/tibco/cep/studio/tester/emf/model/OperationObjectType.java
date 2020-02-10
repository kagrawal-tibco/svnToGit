/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.tester.emf.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Operation Object Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.OperationObjectType#getExecutionObject <em>Execution Object</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.OperationObjectType#getOperation <em>Operation</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getOperationObjectType()
 * @model extendedMetaData="name='OperationObjectType' kind='elementOnly'"
 * @generated
 */
public interface OperationObjectType extends EObject {
	/**
	 * Returns the value of the '<em><b>Execution Object</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.tester.emf.model.ExecutionObjectType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Represents a result object based upon input
	 * 						data.
	 * 						This will typically contain
	 * 						asserted/modified/retracted entity.
	 * 						This will
	 * 						also contain references to invocation object and
	 * 						causal
	 * 						objects.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Execution Object</em>' containment reference list.
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getOperationObjectType_ExecutionObject()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='ExecutionObject' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<ExecutionObjectType> getExecutionObject();

	/**
	 * Returns the value of the '<em><b>Operation</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.studio.tester.emf.model.OperationType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operation</em>' attribute.
	 * @see com.tibco.cep.studio.tester.emf.model.OperationType
	 * @see #isSetOperation()
	 * @see #unsetOperation()
	 * @see #setOperation(OperationType)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getOperationObjectType_Operation()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='operation'"
	 * @generated
	 */
	OperationType getOperation();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.OperationObjectType#getOperation <em>Operation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operation</em>' attribute.
	 * @see com.tibco.cep.studio.tester.emf.model.OperationType
	 * @see #isSetOperation()
	 * @see #unsetOperation()
	 * @see #getOperation()
	 * @generated
	 */
	void setOperation(OperationType value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.studio.tester.emf.model.OperationObjectType#getOperation <em>Operation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetOperation()
	 * @see #getOperation()
	 * @see #setOperation(OperationType)
	 * @generated
	 */
	void unsetOperation();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.tester.emf.model.OperationObjectType#getOperation <em>Operation</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Operation</em>' attribute is set.
	 * @see #unsetOperation()
	 * @see #getOperation()
	 * @see #setOperation(OperationType)
	 * @generated
	 */
	boolean isSetOperation();

} // OperationObjectType
