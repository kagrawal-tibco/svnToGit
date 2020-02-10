/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variable Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.VariableDefinition#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.VariableDefinition#isArray <em>Array</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getVariableDefinition()
 * @model
 * @generated
 */
public interface VariableDefinition extends MemberElement {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getVariableDefinition_Type()
	 * @model
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.VariableDefinition#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Array</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Array</em>' attribute.
	 * @see #setArray(boolean)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getVariableDefinition_Array()
	 * @model
	 * @generated
	 */
	boolean isArray();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.VariableDefinition#isArray <em>Array</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Array</em>' attribute.
	 * @see #isArray()
	 * @generated
	 */
	void setArray(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorType="com.tibco.cep.studio.core.index.model.IStructuredElementVisitor"
	 * @generated
	 */
	void accept(IStructuredElementVisitor visitor);

} // VariableDefinition
