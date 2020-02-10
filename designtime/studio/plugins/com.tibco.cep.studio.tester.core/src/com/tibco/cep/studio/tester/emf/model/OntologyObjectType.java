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
 * A representation of the model object '<em><b>Ontology Object Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.OntologyObjectType#getOperationObject <em>Operation Object</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.OntologyObjectType#getDataType <em>Data Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getOntologyObjectType()
 * @model extendedMetaData="name='OntologyObjectType' kind='elementOnly'"
 * @generated
 */
public interface OntologyObjectType extends EObject {
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
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getOntologyObjectType_OperationObject()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='OperationObject' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<OperationObjectType> getOperationObject();

	/**
	 * Returns the value of the '<em><b>Data Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Type</em>' attribute.
	 * @see #setDataType(String)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getOntologyObjectType_DataType()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='dataType'"
	 * @generated
	 */
	String getDataType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.OntologyObjectType#getDataType <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Type</em>' attribute.
	 * @see #getDataType()
	 * @generated
	 */
	void setDataType(String value);

} // OntologyObjectType
