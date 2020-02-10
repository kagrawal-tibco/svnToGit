/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property Descriptor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#getDefaultValue <em>Default Value</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#getPattern <em>Pattern</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#isMandatory <em>Mandatory</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#isGvToggle <em>Gv Toggle</em>}</li>
 * </ul>
 *
 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getPropertyDescriptor()
 * @model
 * @generated
 */
public interface PropertyDescriptor extends EObject {
	/**
	 * Returns the value of the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Value</em>' attribute.
	 * @see #setDefaultValue(String)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getPropertyDescriptor_DefaultValue()
	 * @model
	 * @generated
	 */
	String getDefaultValue();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#getDefaultValue <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Value</em>' attribute.
	 * @see #getDefaultValue()
	 * @generated
	 */
	void setDefaultValue(String value);

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
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getPropertyDescriptor_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pattern</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pattern</em>' attribute.
	 * @see #setPattern(String)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getPropertyDescriptor_Pattern()
	 * @model
	 * @generated
	 */
	String getPattern();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#getPattern <em>Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pattern</em>' attribute.
	 * @see #getPattern()
	 * @generated
	 */
	void setPattern(String value);

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
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getPropertyDescriptor_Type()
	 * @model
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Mandatory</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mandatory</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mandatory</em>' attribute.
	 * @see #setMandatory(boolean)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getPropertyDescriptor_Mandatory()
	 * @model default="false"
	 * @generated
	 */
	boolean isMandatory();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#isMandatory <em>Mandatory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mandatory</em>' attribute.
	 * @see #isMandatory()
	 * @generated
	 */
	void setMandatory(boolean value);
	
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
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getPropertyDescriptor_DisplayName()
	 * @model
	 * @generated
	 */
	String getDisplayName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
	void setDisplayName(String value);

	/**
	 * Returns the value of the '<em><b>Gv Toggle</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Gv Toggle</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gv Toggle</em>' attribute.
	 * @see #setGvToggle(boolean)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getPropertyDescriptor_GvToggle()
	 * @model default="false"
	 * @generated
	 */
	boolean isGvToggle();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#isGvToggle <em>Gv Toggle</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Gv Toggle</em>' attribute.
	 * @see #isGvToggle()
	 * @generated
	 */
	void setGvToggle(boolean value);

	/**
	 * @generated NOT
	 */
	boolean isMask();

	/**
	 * @generated NOT
	 */
	void setMask(boolean value);

} // PropertyDescriptor
