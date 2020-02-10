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
 * A representation of the model object '<em><b>Property Modification Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 				Representation of a modified property.
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.PropertyModificationType#getOldValue <em>Old Value</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.PropertyModificationType#getNewValue <em>New Value</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.PropertyModificationType#getDataType <em>Data Type</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.PropertyModificationType#isMultiple <em>Multiple</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.PropertyModificationType#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getPropertyModificationType()
 * @model extendedMetaData="name='PropertyModificationType' kind='elementOnly'"
 * @generated
 */
public interface PropertyModificationType extends EObject {
	/**
	 * Returns the value of the '<em><b>Old Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Old Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Old Value</em>' containment reference.
	 * @see #setOldValue(ValueType)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getPropertyModificationType_OldValue()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='oldValue' namespace='##targetNamespace'"
	 * @generated
	 */
	ValueType getOldValue();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.PropertyModificationType#getOldValue <em>Old Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Old Value</em>' containment reference.
	 * @see #getOldValue()
	 * @generated
	 */
	void setOldValue(ValueType value);

	/**
	 * Returns the value of the '<em><b>New Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>New Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>New Value</em>' containment reference.
	 * @see #setNewValue(ValueType)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getPropertyModificationType_NewValue()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='newValue' namespace='##targetNamespace'"
	 * @generated
	 */
	ValueType getNewValue();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.PropertyModificationType#getNewValue <em>New Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>New Value</em>' containment reference.
	 * @see #getNewValue()
	 * @generated
	 */
	void setNewValue(ValueType value);

	/**
	 * Returns the value of the '<em><b>Data Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.studio.tester.emf.model.DataTypeType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Type</em>' attribute.
	 * @see com.tibco.cep.studio.tester.emf.model.DataTypeType
	 * @see #isSetDataType()
	 * @see #unsetDataType()
	 * @see #setDataType(DataTypeType)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getPropertyModificationType_DataType()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='dataType'"
	 * @generated
	 */
	DataTypeType getDataType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.PropertyModificationType#getDataType <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Type</em>' attribute.
	 * @see com.tibco.cep.studio.tester.emf.model.DataTypeType
	 * @see #isSetDataType()
	 * @see #unsetDataType()
	 * @see #getDataType()
	 * @generated
	 */
	void setDataType(DataTypeType value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.studio.tester.emf.model.PropertyModificationType#getDataType <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDataType()
	 * @see #getDataType()
	 * @see #setDataType(DataTypeType)
	 * @generated
	 */
	void unsetDataType();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.tester.emf.model.PropertyModificationType#getDataType <em>Data Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Data Type</em>' attribute is set.
	 * @see #unsetDataType()
	 * @see #getDataType()
	 * @see #setDataType(DataTypeType)
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
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getPropertyModificationType_Multiple()
	 * @model default="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='multiple'"
	 * @generated
	 */
	boolean isMultiple();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.PropertyModificationType#isMultiple <em>Multiple</em>}' attribute.
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
	 * Unsets the value of the '{@link com.tibco.cep.studio.tester.emf.model.PropertyModificationType#isMultiple <em>Multiple</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMultiple()
	 * @see #isMultiple()
	 * @see #setMultiple(boolean)
	 * @generated
	 */
	void unsetMultiple();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.tester.emf.model.PropertyModificationType#isMultiple <em>Multiple</em>}' attribute is set.
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
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getPropertyModificationType_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.PropertyModificationType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // PropertyModificationType
