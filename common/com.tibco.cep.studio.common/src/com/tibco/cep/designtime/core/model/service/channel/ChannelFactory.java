/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage
 * @generated
 */
public interface ChannelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ChannelFactory eINSTANCE = com.tibco.cep.designtime.core.model.service.channel.impl.ChannelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Channel</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Channel</em>'.
	 * @generated
	 */
	Channel createChannel();

	/**
	 * Returns a new object of class '<em>Destination</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Destination</em>'.
	 * @generated
	 */
	Destination createDestination();

	/**
	 * Returns a new object of class '<em>Driver Config</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Driver Config</em>'.
	 * @generated
	 */
	DriverConfig createDriverConfig();

	/**
	 * Returns a new object of class '<em>Destination Concept</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Destination Concept</em>'.
	 * @generated
	 */
	DestinationConcept createDestinationConcept();

	/**
	 * Returns a new object of class '<em>Driver Manager</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Driver Manager</em>'.
	 * @generated
	 */
	DriverManager createDriverManager();

	/**
	 * Returns a new object of class '<em>Driver Registration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Driver Registration</em>'.
	 * @generated
	 */
	DriverRegistration createDriverRegistration();

	/**
	 * Returns a new object of class '<em>Serializer Config</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Serializer Config</em>'.
	 * @generated
	 */
	SerializerConfig createSerializerConfig();

	/**
	 * Returns a new object of class '<em>Choice Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Choice Configuration</em>'.
	 * @generated
	 */
	ChoiceConfiguration createChoiceConfiguration();

	/**
	 * Returns a new object of class '<em>Choice</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Choice</em>'.
	 * @generated
	 */
	Choice createChoice();

	/**
	 * Returns a new object of class '<em>Serializer Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Serializer Info</em>'.
	 * @generated
	 */
	SerializerInfo createSerializerInfo();

	/**
	 * Returns a new object of class '<em>Extended Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Extended Configuration</em>'.
	 * @generated
	 */
	ExtendedConfiguration createExtendedConfiguration();

	/**
	 * Returns a new object of class '<em>Destination Descriptor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Destination Descriptor</em>'.
	 * @generated
	 */
	DestinationDescriptor createDestinationDescriptor();

	/**
	 * Returns a new object of class '<em>Descriptor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Descriptor</em>'.
	 * @generated
	 */
	ChannelDescriptor createChannelDescriptor();

	/**
	 * Returns a new object of class '<em>Property Descriptor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property Descriptor</em>'.
	 * @generated
	 */
	PropertyDescriptor createPropertyDescriptor();

	/**
	 * Returns a new object of class '<em>Property Descriptor Map Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property Descriptor Map Entry</em>'.
	 * @generated
	 */
	PropertyDescriptorMapEntry createPropertyDescriptorMapEntry();

	/**
	 * Returns a new object of class '<em>Property Descriptor Map</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property Descriptor Map</em>'.
	 * @generated
	 */
	PropertyDescriptorMap createPropertyDescriptorMap();

	/**
	 * Returns a new object of class '<em>Driver Type Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Driver Type Info</em>'.
	 * @generated
	 */
	DriverTypeInfo createDriverTypeInfo();

	/**
	 * Returns a new object of class '<em>Http Channel Driver Config</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Http Channel Driver Config</em>'.
	 * @generated
	 */
	HttpChannelDriverConfig createHttpChannelDriverConfig();

	/**
	 * Returns a new object of class '<em>Web Application Descriptor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Web Application Descriptor</em>'.
	 * @generated
	 */
	WebApplicationDescriptor createWebApplicationDescriptor();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ChannelPackage getChannelPackage();

} //ChannelFactory
