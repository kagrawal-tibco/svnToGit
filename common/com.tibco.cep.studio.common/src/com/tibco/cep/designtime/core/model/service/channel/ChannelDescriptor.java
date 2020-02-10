/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Descriptor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.ChannelDescriptor#getDestinationDescriptor <em>Destination Descriptor</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getChannelDescriptor()
 * @model
 * @generated
 */
public interface ChannelDescriptor extends PropertyDescriptorMap {
	/**
	 * Returns the value of the '<em><b>Destination Descriptor</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Destination Descriptor</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Destination Descriptor</em>' reference.
	 * @see #setDestinationDescriptor(DestinationDescriptor)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getChannelDescriptor_DestinationDescriptor()
	 * @model
	 * @generated
	 */
	DestinationDescriptor getDestinationDescriptor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.ChannelDescriptor#getDestinationDescriptor <em>Destination Descriptor</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Destination Descriptor</em>' reference.
	 * @see #getDestinationDescriptor()
	 * @generated
	 */
	void setDestinationDescriptor(DestinationDescriptor value);

} // ChannelDescriptor
