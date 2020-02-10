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
 * A representation of the model object '<em><b>Tester Results Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 				Root of the tester results XML sorted by operations.
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.TesterResultsOperation#getRunName <em>Run Name</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.TesterResultsOperation#getOperationObject <em>Operation Object</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.TesterResultsOperation#getProject <em>Project</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getTesterResultsOperation()
 * @model extendedMetaData="name='TesterResultsOperation' kind='elementOnly'"
 * @generated
 */
public interface TesterResultsOperation extends EObject {
	/**
	 * Returns the value of the '<em><b>Run Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Represents the name of the run for which the
	 * 						results
	 * 						are generated.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Run Name</em>' attribute.
	 * @see #setRunName(String)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getTesterResultsOperation_RunName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='RunName' namespace='##targetNamespace'"
	 * @generated
	 */
	String getRunName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.TesterResultsOperation#getRunName <em>Run Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Run Name</em>' attribute.
	 * @see #getRunName()
	 * @generated
	 */
	void setRunName(String value);

	/**
	 * Returns the value of the '<em><b>Operation Object</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.tester.emf.model.OperationObjectType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Represents a list of object that have passed through same operation.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Operation Object</em>' containment reference list.
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getTesterResultsOperation_OperationObject()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='OperationObject' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<OperationObjectType> getOperationObject();

	/**
	 * Returns the value of the '<em><b>Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project</em>' attribute.
	 * @see #setProject(String)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getTesterResultsOperation_Project()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='project' namespace='##targetNamespace'"
	 * @generated
	 */
	String getProject();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.TesterResultsOperation#getProject <em>Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project</em>' attribute.
	 * @see #getProject()
	 * @generated
	 */
	void setProject(String value);

} // TesterResultsOperation
