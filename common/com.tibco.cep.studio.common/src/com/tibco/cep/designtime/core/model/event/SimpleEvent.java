/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.event;

import com.tibco.cep.designtime.core.model.service.channel.Destination;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simple Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.SimpleEvent#getChannelURI <em>Channel URI</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.SimpleEvent#getDestinationName <em>Destination Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getSimpleEvent()
 * @model
 * @generated
 */
public interface SimpleEvent extends Event {

	/**
	 * Returns the value of the '<em><b>Channel URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Channel URI</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Channel URI</em>' attribute.
	 * @see #setChannelURI(String)
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getSimpleEvent_ChannelURI()
	 * @model
	 * @generated
	 */
	String getChannelURI();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.event.SimpleEvent#getChannelURI <em>Channel URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Channel URI</em>' attribute.
	 * @see #getChannelURI()
	 * @generated
	 */
	void setChannelURI(String value);

	/**
	 * Returns the value of the '<em><b>Destination Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Destination Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Destination Name</em>' attribute.
	 * @see #setDestinationName(String)
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getSimpleEvent_DestinationName()
	 * @model
	 * @generated
	 */
	String getDestinationName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.event.SimpleEvent#getDestinationName <em>Destination Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Destination Name</em>' attribute.
	 * @see #getDestinationName()
	 * @generated
	 */
	void setDestinationName(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	Destination getDestination();
	
//	/**
//     * Replaces existing user properties.
//     *
//     * @param propertyName
//     * @param type
//     * @generated NOT
//     */
//    public void addUserProperty(String propertyName, PROPERTY_TYPES type) throws ModelException;
//
//    /**
//     * @generated NOT
//     * @param propertyName
//     */
//    public void deleteUserProperty(String propertyName);
//
//    /**
//     * @generated NOT
//     */
//    public void clearUserProperties();
} // SimpleEvent
