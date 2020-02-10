/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PropertyMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Destination</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.Destination#getEventURI <em>Event URI</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.Destination#getSerializerDeserializerClass <em>Serializer Deserializer Class</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.Destination#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.Destination#getDriverConfig <em>Driver Config</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.Destination#isInputEnabled <em>Input Enabled</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.Destination#isOutputEnabled <em>Output Enabled</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDestination()
 * @model
 * @generated
 */
public interface Destination extends Entity {
	/**
	 * Returns the value of the '<em><b>Event URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event URI</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Event URI</em>' attribute.
	 * @see #setEventURI(String)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDestination_EventURI()
	 * @model
	 * @generated
	 */
	String getEventURI();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.Destination#getEventURI <em>Event URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Event URI</em>' attribute.
	 * @see #getEventURI()
	 * @generated
	 */
	void setEventURI(String value);

	/**
	 * Returns the value of the '<em><b>Serializer Deserializer Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Serializer Deserializer Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Serializer Deserializer Class</em>' attribute.
	 * @see #setSerializerDeserializerClass(String)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDestination_SerializerDeserializerClass()
	 * @model
	 * @generated
	 */
	String getSerializerDeserializerClass();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.Destination#getSerializerDeserializerClass <em>Serializer Deserializer Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Serializer Deserializer Class</em>' attribute.
	 * @see #getSerializerDeserializerClass()
	 * @generated
	 */
	void setSerializerDeserializerClass(String value);

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Properties</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference.
	 * @see #setProperties(PropertyMap)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDestination_Properties()
	 * @model containment="true"
	 * @generated
	 */
	PropertyMap getProperties();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.Destination#getProperties <em>Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Properties</em>' containment reference.
	 * @see #getProperties()
	 * @generated
	 */
	void setProperties(PropertyMap value);

	/**
	 * Returns the value of the '<em><b>Driver Config</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Driver Config</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Driver Config</em>' reference.
	 * @see #setDriverConfig(DriverConfig)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDestination_DriverConfig()
	 * @model
	 * @generated
	 */
	DriverConfig getDriverConfig();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.Destination#getDriverConfig <em>Driver Config</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Driver Config</em>' reference.
	 * @see #getDriverConfig()
	 * @generated
	 */
	void setDriverConfig(DriverConfig value);

	/**
	 * Returns the value of the '<em><b>Input Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Input Enabled</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Input Enabled</em>' attribute.
	 * @see #setInputEnabled(boolean)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDestination_InputEnabled()
	 * @model
	 * @generated
	 */
	boolean isInputEnabled();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.Destination#isInputEnabled <em>Input Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Input Enabled</em>' attribute.
	 * @see #isInputEnabled()
	 * @generated
	 */
	void setInputEnabled(boolean value);

	/**
	 * Returns the value of the '<em><b>Output Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Output Enabled</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output Enabled</em>' attribute.
	 * @see #setOutputEnabled(boolean)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDestination_OutputEnabled()
	 * @model
	 * @generated
	 */
	boolean isOutputEnabled();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.Destination#isOutputEnabled <em>Output Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Output Enabled</em>' attribute.
	 * @see #isOutputEnabled()
	 * @generated
	 */
	void setOutputEnabled(boolean value);

} // Destination
