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
 * A representation of the model object '<em><b>Property Descriptor Map Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMapEntry#getKey <em>Key</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMapEntry#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getPropertyDescriptorMapEntry()
 * @model
 * @generated
 */
public interface PropertyDescriptorMapEntry extends EObject {
	/**
	 * Returns the value of the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Key</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Key</em>' attribute.
	 * @see #setKey(String)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getPropertyDescriptorMapEntry_Key()
	 * @model required="true"
	 * @generated
	 */
	String getKey();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMapEntry#getKey <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key</em>' attribute.
	 * @see #getKey()
	 * @generated
	 */
	void setKey(String value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' containment reference.
	 * @see #setValue(PropertyDescriptor)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getPropertyDescriptorMapEntry_Value()
	 * @model containment="true" required="true"
	 * @generated
	 */
	PropertyDescriptor getValue();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMapEntry#getValue <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(PropertyDescriptor value);

} // PropertyDescriptorMapEntry
