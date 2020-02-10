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
 * A representation of the model object '<em><b>Serializer Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.SerializerInfo#getSerializerType <em>Serializer Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.SerializerInfo#getSerializerClass <em>Serializer Class</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.SerializerInfo#isDefault <em>Default</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getSerializerInfo()
 * @model
 * @generated
 */
public interface SerializerInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Serializer Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Serializer Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Serializer Type</em>' attribute.
	 * @see #setSerializerType(String)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getSerializerInfo_SerializerType()
	 * @model
	 * @generated
	 */
	String getSerializerType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.SerializerInfo#getSerializerType <em>Serializer Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Serializer Type</em>' attribute.
	 * @see #getSerializerType()
	 * @generated
	 */
	void setSerializerType(String value);

	/**
	 * Returns the value of the '<em><b>Serializer Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Serializer Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Serializer Class</em>' attribute.
	 * @see #setSerializerClass(String)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getSerializerInfo_SerializerClass()
	 * @model
	 * @generated
	 */
	String getSerializerClass();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.SerializerInfo#getSerializerClass <em>Serializer Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Serializer Class</em>' attribute.
	 * @see #getSerializerClass()
	 * @generated
	 */
	void setSerializerClass(String value);

	/**
	 * Returns the value of the '<em><b>Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default</em>' attribute.
	 * @see #setDefault(boolean)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getSerializerInfo_Default()
	 * @model
	 * @generated
	 */
	boolean isDefault();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.SerializerInfo#isDefault <em>Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default</em>' attribute.
	 * @see #isDefault()
	 * @generated
	 */
	void setDefault(boolean value);

} // SerializerInfo
