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
 * A representation of the model object '<em><b>Property Attrs Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 				Basic Attributes on the property element.
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.PropertyAttrsType#getValue <em>Value</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.PropertyAttrsType#getDataType <em>Data Type</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.PropertyAttrsType#isMultiple <em>Multiple</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.PropertyAttrsType#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getPropertyAttrsType()
 * @model extendedMetaData="name='PropertyAttrsType' kind='simple'"
 * @generated
 */
public interface PropertyAttrsType extends EObject {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getPropertyAttrsType_Value()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="name=':0' kind='simple'"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.PropertyAttrsType#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Data Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.studio.tester.emf.model.DataTypeType1}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Type</em>' attribute.
	 * @see com.tibco.cep.studio.tester.emf.model.DataTypeType1
	 * @see #isSetDataType()
	 * @see #unsetDataType()
	 * @see #setDataType(DataTypeType1)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getPropertyAttrsType_DataType()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='dataType'"
	 * @generated
	 */
	DataTypeType1 getDataType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.PropertyAttrsType#getDataType <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Type</em>' attribute.
	 * @see com.tibco.cep.studio.tester.emf.model.DataTypeType1
	 * @see #isSetDataType()
	 * @see #unsetDataType()
	 * @see #getDataType()
	 * @generated
	 */
	void setDataType(DataTypeType1 value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.studio.tester.emf.model.PropertyAttrsType#getDataType <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDataType()
	 * @see #getDataType()
	 * @see #setDataType(DataTypeType1)
	 * @generated
	 */
	void unsetDataType();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.tester.emf.model.PropertyAttrsType#getDataType <em>Data Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Data Type</em>' attribute is set.
	 * @see #unsetDataType()
	 * @see #getDataType()
	 * @see #setDataType(DataTypeType1)
	 * @generated
	 */
	boolean isSetDataType();

	/**
	 * Returns the value of the '<em><b>Multiple</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multiple</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multiple</em>' attribute.
	 * @see #isSetMultiple()
	 * @see #unsetMultiple()
	 * @see #setMultiple(boolean)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getPropertyAttrsType_Multiple()
	 * @model default="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='multiple'"
	 * @generated
	 */
	boolean isMultiple();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.PropertyAttrsType#isMultiple <em>Multiple</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Multiple</em>' attribute.
	 * @see #isSetMultiple()
	 * @see #unsetMultiple()
	 * @see #isMultiple()
	 * @generated
	 */
	void setMultiple(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.studio.tester.emf.model.PropertyAttrsType#isMultiple <em>Multiple</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMultiple()
	 * @see #isMultiple()
	 * @see #setMultiple(boolean)
	 * @generated
	 */
	void unsetMultiple();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.tester.emf.model.PropertyAttrsType#isMultiple <em>Multiple</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Multiple</em>' attribute is set.
	 * @see #unsetMultiple()
	 * @see #isMultiple()
	 * @see #setMultiple(boolean)
	 * @generated
	 */
	boolean isSetMultiple();

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
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getPropertyAttrsType_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.PropertyAttrsType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // PropertyAttrsType
