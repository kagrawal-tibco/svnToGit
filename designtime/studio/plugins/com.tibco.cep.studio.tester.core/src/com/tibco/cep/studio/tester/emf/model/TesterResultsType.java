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
 * A representation of the model object '<em><b>Tester Results Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 				Root of the tester results XML.
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.TesterResultsType#getRunName <em>Run Name</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.TesterResultsType#getExecutionObject <em>Execution Object</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.TesterResultsType#getProject <em>Project</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getTesterResultsType()
 * @model extendedMetaData="name='TesterResultsType' kind='elementOnly'"
 * @generated
 */
public interface TesterResultsType extends EObject {
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
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getTesterResultsType_RunName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='RunName' namespace='##targetNamespace'"
	 * @generated
	 */
	String getRunName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.TesterResultsType#getRunName <em>Run Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Run Name</em>' attribute.
	 * @see #getRunName()
	 * @generated
	 */
	void setRunName(String value);

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
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getTesterResultsType_ExecutionObject()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='ExecutionObject' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<ExecutionObjectType> getExecutionObject();

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
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getTesterResultsType_Project()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='project' namespace='##targetNamespace'"
	 * @generated
	 */
	String getProject();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.TesterResultsType#getProject <em>Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project</em>' attribute.
	 * @see #getProject()
	 * @generated
	 */
	void setProject(String value);

} // TesterResultsType
