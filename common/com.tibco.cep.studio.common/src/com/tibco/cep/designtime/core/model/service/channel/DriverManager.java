/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel;

import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Driver Manager</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDriverManager()
 * @model
 * @generated
 */
public interface DriverManager extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void registerDrivers(Map<String, Map<DRIVER_TYPE, DriverRegistration>> resourceDriverMap);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	ChannelDescriptor getChannelDescriptor(DRIVER_TYPE driverType);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" many="false"
	 * @generated
	 */
	EList<DRIVER_TYPE> getDriverTypes();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	DestinationDescriptor getDestinationDescriptor(DRIVER_TYPE driverType);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	String getVersion(DRIVER_TYPE driverType);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	String getDescription(DRIVER_TYPE driverType);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	String getLabel(DRIVER_TYPE driverType);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	EList<String> getAllowedResourceTypes(DRIVER_TYPE driverType);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	EList<SerializerInfo> getDestinationsSerializerClasses(DRIVER_TYPE driverType);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	SerializerInfo getDefaultSerializer(DRIVER_TYPE driverType);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	EList<ChoiceConfiguration> getPropertyConfigurations(DRIVER_TYPE driverType);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	EList<ChoiceConfiguration> getExtendedConfigurations(DRIVER_TYPE driverType);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	EList<Choice> getExtendedConfigurationForChoice(DRIVER_TYPE DRIVER_TYPE);


} // DriverManager
