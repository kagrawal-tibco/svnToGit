/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simple Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.SimpleProperty#getValue <em>Value</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.SimpleProperty#isMandatory <em>Mandatory</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.SimpleProperty#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.SimpleProperty#getDisplayName <em>Display Name</em>}</li>
 * </ul>
 *
 * @see com.tibco.cep.designtime.core.model.ModelPackage#getSimpleProperty()
 * @model
 * @generated
 */
public interface SimpleProperty extends Entity {
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
	 * @see com.tibco.cep.designtime.core.model.ModelPackage#getSimpleProperty_Value()
	 * @model required="true"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.SimpleProperty#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Mandatory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mandatory</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mandatory</em>' attribute.
	 * @see #setMandatory(boolean)
	 * @see com.tibco.cep.designtime.core.model.ModelPackage#getSimpleProperty_Mandatory()
	 * @model
	 * @generated
	 */
	boolean isMandatory();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.SimpleProperty#isMandatory <em>Mandatory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mandatory</em>' attribute.
	 * @see #isMandatory()
	 * @generated
	 */
	void setMandatory(boolean value);

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
	 * @see com.tibco.cep.designtime.core.model.ModelPackage#getSimpleProperty_Type()
	 * @model
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.SimpleProperty#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Display Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Display Name</em>' attribute.
	 * @see #setDisplayName(String)
	 * @see com.tibco.cep.designtime.core.model.ModelPackage#getSimpleProperty_DisplayName()
	 * @model
	 * @generated
	 */
	String getDisplayName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.SimpleProperty#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
	void setDisplayName(String value);

} // SimpleProperty
