/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Driver Registration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getLabel <em>Label</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getVersion <em>Version</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getChannelDescriptor <em>Channel Descriptor</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getDestinationDescriptor <em>Destination Descriptor</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getResourcesAllowed <em>Resources Allowed</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getSerializerConfig <em>Serializer Config</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getChoiceConfigurations <em>Choice Configurations</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getExtendedConfigurations <em>Extended Configurations</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getDriverType <em>Driver Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getChoice <em>Choice</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDriverRegistration()
 * @model
 * @generated
 */
public interface DriverRegistration extends EObject {
	/**
	 * Returns the value of the '<em><b>Driver Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Driver Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Driver Type</em>' reference.
	 * @see #setDriverType(DRIVER_TYPE)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDriverRegistration_DriverType()
	 * @model
	 * @generated
	 */
	DRIVER_TYPE getDriverType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getDriverType <em>Driver Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Driver Type</em>' reference.
	 * @see #getDriverType()
	 * @generated
	 */
	void setDriverType(DRIVER_TYPE value);

	/**
	 * Returns the value of the '<em><b>Choice</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.service.channel.Choice}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Choice</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Choice</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDriverRegistration_Choice()
	 * @model containment="true"
	 * @generated
	 */
	EList<Choice> getChoice();

	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDriverRegistration_Label()
	 * @model
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDriverRegistration_Version()
	 * @model
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDriverRegistration_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Channel Descriptor</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Channel Descriptor</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Channel Descriptor</em>' reference.
	 * @see #setChannelDescriptor(ChannelDescriptor)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDriverRegistration_ChannelDescriptor()
	 * @model
	 * @generated
	 */
	ChannelDescriptor getChannelDescriptor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getChannelDescriptor <em>Channel Descriptor</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Channel Descriptor</em>' reference.
	 * @see #getChannelDescriptor()
	 * @generated
	 */
	void setChannelDescriptor(ChannelDescriptor value);

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
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDriverRegistration_DestinationDescriptor()
	 * @model
	 * @generated
	 */
	DestinationDescriptor getDestinationDescriptor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getDestinationDescriptor <em>Destination Descriptor</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Destination Descriptor</em>' reference.
	 * @see #getDestinationDescriptor()
	 * @generated
	 */
	void setDestinationDescriptor(DestinationDescriptor value);

	/**
	 * Returns the value of the '<em><b>Resources Allowed</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resources Allowed</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resources Allowed</em>' attribute list.
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDriverRegistration_ResourcesAllowed()
	 * @model
	 * @generated
	 */
	EList<String> getResourcesAllowed();

	/**
	 * Returns the value of the '<em><b>Serializer Config</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Serializer Config</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Serializer Config</em>' reference.
	 * @see #setSerializerConfig(SerializerConfig)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDriverRegistration_SerializerConfig()
	 * @model
	 * @generated
	 */
	SerializerConfig getSerializerConfig();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getSerializerConfig <em>Serializer Config</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Serializer Config</em>' reference.
	 * @see #getSerializerConfig()
	 * @generated
	 */
	void setSerializerConfig(SerializerConfig value);

	/**
	 * Returns the value of the '<em><b>Choice Configurations</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Choice Configurations</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Choice Configurations</em>' reference list.
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDriverRegistration_ChoiceConfigurations()
	 * @model
	 * @generated
	 */
	EList<ChoiceConfiguration> getChoiceConfigurations();

	/**
	 * Returns the value of the '<em><b>Extended Configurations</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extended Configurations</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extended Configurations</em>' reference list.
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDriverRegistration_ExtendedConfigurations()
	 * @model
	 * @generated
	 */
	EList<ChoiceConfiguration> getExtendedConfigurations();

} // DriverRegistration
