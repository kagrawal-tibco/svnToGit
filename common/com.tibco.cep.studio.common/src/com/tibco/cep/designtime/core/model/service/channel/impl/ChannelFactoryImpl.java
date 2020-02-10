/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel.impl;

import com.tibco.cep.designtime.core.model.service.channel.*;
import java.util.Map;
import java.util.Properties;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.ChannelDescriptor;
import com.tibco.cep.designtime.core.model.service.channel.ChannelFactory;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.Choice;
import com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.DestinationConcept;
import com.tibco.cep.designtime.core.model.service.channel.DestinationDescriptor;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.designtime.core.model.service.channel.DriverManager;
import com.tibco.cep.designtime.core.model.service.channel.DriverRegistration;
import com.tibco.cep.designtime.core.model.service.channel.DriverTypeInfo;
import com.tibco.cep.designtime.core.model.service.channel.ExtendedConfiguration;
import com.tibco.cep.designtime.core.model.service.channel.HttpChannelDriverConfig;
import com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor;
import com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMap;
import com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMapEntry;
import com.tibco.cep.designtime.core.model.service.channel.SerializerConfig;
import com.tibco.cep.designtime.core.model.service.channel.SerializerInfo;
import com.tibco.cep.designtime.core.model.service.channel.WebApplicationDescriptor;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ChannelFactoryImpl extends EFactoryImpl implements ChannelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ChannelFactory init() {
		try {
			ChannelFactory theChannelFactory = (ChannelFactory)EPackage.Registry.INSTANCE.getEFactory(ChannelPackage.eNS_URI);
			if (theChannelFactory != null) {
				return theChannelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ChannelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChannelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ChannelPackage.CHANNEL: return createChannel();
			case ChannelPackage.DESTINATION: return createDestination();
			case ChannelPackage.DRIVER_CONFIG: return createDriverConfig();
			case ChannelPackage.DESTINATION_CONCEPT: return createDestinationConcept();
			case ChannelPackage.DRIVER_MANAGER: return createDriverManager();
			case ChannelPackage.DRIVER_REGISTRATION: return createDriverRegistration();
			case ChannelPackage.SERIALIZER_CONFIG: return createSerializerConfig();
			case ChannelPackage.CHOICE_CONFIGURATION: return createChoiceConfiguration();
			case ChannelPackage.CHOICE: return createChoice();
			case ChannelPackage.SERIALIZER_INFO: return createSerializerInfo();
			case ChannelPackage.EXTENDED_CONFIGURATION: return createExtendedConfiguration();
			case ChannelPackage.DESTINATION_DESCRIPTOR: return createDestinationDescriptor();
			case ChannelPackage.CHANNEL_DESCRIPTOR: return createChannelDescriptor();
			case ChannelPackage.PROPERTY_DESCRIPTOR: return createPropertyDescriptor();
			case ChannelPackage.PROPERTY_DESCRIPTOR_MAP_ENTRY: return createPropertyDescriptorMapEntry();
			case ChannelPackage.PROPERTY_DESCRIPTOR_MAP: return createPropertyDescriptorMap();
			case ChannelPackage.DRIVER_TYPE_INFO: return createDriverTypeInfo();
			case ChannelPackage.HTTP_CHANNEL_DRIVER_CONFIG: return createHttpChannelDriverConfig();
			case ChannelPackage.WEB_APPLICATION_DESCRIPTOR: return createWebApplicationDescriptor();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case ChannelPackage.CONFIG_METHOD:
				return createCONFIG_METHODFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case ChannelPackage.CONFIG_METHOD:
				return convertCONFIG_METHODToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Channel createChannel() {
		ChannelImpl channel = new ChannelImpl();
		return channel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Destination createDestination() {
		DestinationImpl destination = new DestinationImpl();
		return destination;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DriverConfig createDriverConfig() {
		DriverConfigImpl driverConfig = new DriverConfigImpl();
		return driverConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DestinationConcept createDestinationConcept() {
		DestinationConceptImpl destinationConcept = new DestinationConceptImpl();
		return destinationConcept;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DriverManager createDriverManager() {
		DriverManagerImpl driverManager = new DriverManagerImpl();
		return driverManager;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DriverRegistration createDriverRegistration() {
		DriverRegistrationImpl driverRegistration = new DriverRegistrationImpl();
		return driverRegistration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SerializerConfig createSerializerConfig() {
		SerializerConfigImpl serializerConfig = new SerializerConfigImpl();
		return serializerConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChoiceConfiguration createChoiceConfiguration() {
		ChoiceConfigurationImpl choiceConfiguration = new ChoiceConfigurationImpl();
		return choiceConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Choice createChoice() {
		ChoiceImpl choice = new ChoiceImpl();
		return choice;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SerializerInfo createSerializerInfo() {
		SerializerInfoImpl serializerInfo = new SerializerInfoImpl();
		return serializerInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExtendedConfiguration createExtendedConfiguration() {
		ExtendedConfigurationImpl extendedConfiguration = new ExtendedConfigurationImpl();
		return extendedConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DestinationDescriptor createDestinationDescriptor() {
		DestinationDescriptorImpl destinationDescriptor = new DestinationDescriptorImpl();
		return destinationDescriptor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChannelDescriptor createChannelDescriptor() {
		ChannelDescriptorImpl channelDescriptor = new ChannelDescriptorImpl();
		return channelDescriptor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyDescriptor createPropertyDescriptor() {
		PropertyDescriptorImpl propertyDescriptor = new PropertyDescriptorImpl();
		return propertyDescriptor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyDescriptorMapEntry createPropertyDescriptorMapEntry() {
		PropertyDescriptorMapEntryImpl propertyDescriptorMapEntry = new PropertyDescriptorMapEntryImpl();
		return propertyDescriptorMapEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyDescriptorMap createPropertyDescriptorMap() {
		PropertyDescriptorMapImpl propertyDescriptorMap = new PropertyDescriptorMapImpl();
		return propertyDescriptorMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DriverTypeInfo createDriverTypeInfo() {
		DriverTypeInfoImpl driverTypeInfo = new DriverTypeInfoImpl();
		return driverTypeInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HttpChannelDriverConfig createHttpChannelDriverConfig() {
		HttpChannelDriverConfigImpl httpChannelDriverConfig = new HttpChannelDriverConfigImpl();
		return httpChannelDriverConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WebApplicationDescriptor createWebApplicationDescriptor() {
		WebApplicationDescriptorImpl webApplicationDescriptor = new WebApplicationDescriptorImpl();
		return webApplicationDescriptor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Properties createProperties() {
		return new Properties();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CONFIG_METHOD createCONFIG_METHODFromString(EDataType eDataType, String initialValue) {
		CONFIG_METHOD result = CONFIG_METHOD.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCONFIG_METHODToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChannelPackage getChannelPackage() {
		return (ChannelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ChannelPackage getPackage() {
		return ChannelPackage.eINSTANCE;
	}

} //ChannelFactoryImpl
