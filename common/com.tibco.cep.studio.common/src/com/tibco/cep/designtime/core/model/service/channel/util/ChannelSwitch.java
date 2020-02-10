/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.RuleParticipant;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.service.channel.*;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.ChannelDescriptor;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.Choice;
import com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration;
import com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE;
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
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage
 * @generated
 */
public class ChannelSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ChannelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChannelSwitch() {
		if (modelPackage == null) {
			modelPackage = ChannelPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case ChannelPackage.CHANNEL: {
				Channel channel = (Channel)theEObject;
				T result = caseChannel(channel);
				if (result == null) result = caseEntity(channel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChannelPackage.DESTINATION: {
				Destination destination = (Destination)theEObject;
				T result = caseDestination(destination);
				if (result == null) result = caseEntity(destination);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChannelPackage.DRIVER_CONFIG: {
				DriverConfig driverConfig = (DriverConfig)theEObject;
				T result = caseDriverConfig(driverConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChannelPackage.DESTINATION_CONCEPT: {
				DestinationConcept destinationConcept = (DestinationConcept)theEObject;
				T result = caseDestinationConcept(destinationConcept);
				if (result == null) result = caseConcept(destinationConcept);
				if (result == null) result = caseRuleParticipant(destinationConcept);
				if (result == null) result = caseEntity(destinationConcept);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChannelPackage.DRIVER_MANAGER: {
				DriverManager driverManager = (DriverManager)theEObject;
				T result = caseDriverManager(driverManager);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChannelPackage.DRIVER_REGISTRATION: {
				DriverRegistration driverRegistration = (DriverRegistration)theEObject;
				T result = caseDriverRegistration(driverRegistration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChannelPackage.SERIALIZER_CONFIG: {
				SerializerConfig serializerConfig = (SerializerConfig)theEObject;
				T result = caseSerializerConfig(serializerConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChannelPackage.CHOICE_CONFIGURATION: {
				ChoiceConfiguration choiceConfiguration = (ChoiceConfiguration)theEObject;
				T result = caseChoiceConfiguration(choiceConfiguration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChannelPackage.CHOICE: {
				Choice choice = (Choice)theEObject;
				T result = caseChoice(choice);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChannelPackage.SERIALIZER_INFO: {
				SerializerInfo serializerInfo = (SerializerInfo)theEObject;
				T result = caseSerializerInfo(serializerInfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChannelPackage.EXTENDED_CONFIGURATION: {
				ExtendedConfiguration extendedConfiguration = (ExtendedConfiguration)theEObject;
				T result = caseExtendedConfiguration(extendedConfiguration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChannelPackage.DESTINATION_DESCRIPTOR: {
				DestinationDescriptor destinationDescriptor = (DestinationDescriptor)theEObject;
				T result = caseDestinationDescriptor(destinationDescriptor);
				if (result == null) result = casePropertyDescriptorMap(destinationDescriptor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChannelPackage.CHANNEL_DESCRIPTOR: {
				ChannelDescriptor channelDescriptor = (ChannelDescriptor)theEObject;
				T result = caseChannelDescriptor(channelDescriptor);
				if (result == null) result = casePropertyDescriptorMap(channelDescriptor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChannelPackage.PROPERTY_DESCRIPTOR: {
				PropertyDescriptor propertyDescriptor = (PropertyDescriptor)theEObject;
				T result = casePropertyDescriptor(propertyDescriptor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChannelPackage.PROPERTY_DESCRIPTOR_MAP_ENTRY: {
				PropertyDescriptorMapEntry propertyDescriptorMapEntry = (PropertyDescriptorMapEntry)theEObject;
				T result = casePropertyDescriptorMapEntry(propertyDescriptorMapEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChannelPackage.PROPERTY_DESCRIPTOR_MAP: {
				PropertyDescriptorMap propertyDescriptorMap = (PropertyDescriptorMap)theEObject;
				T result = casePropertyDescriptorMap(propertyDescriptorMap);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChannelPackage.DRIVER_TYPE: {
				DRIVER_TYPE driveR_TYPE = (DRIVER_TYPE)theEObject;
				T result = caseDRIVER_TYPE(driveR_TYPE);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChannelPackage.DRIVER_TYPE_INFO: {
				DriverTypeInfo driverTypeInfo = (DriverTypeInfo)theEObject;
				T result = caseDriverTypeInfo(driverTypeInfo);
				if (result == null) result = caseDRIVER_TYPE(driverTypeInfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChannelPackage.HTTP_CHANNEL_DRIVER_CONFIG: {
				HttpChannelDriverConfig httpChannelDriverConfig = (HttpChannelDriverConfig)theEObject;
				T result = caseHttpChannelDriverConfig(httpChannelDriverConfig);
				if (result == null) result = caseDriverConfig(httpChannelDriverConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChannelPackage.WEB_APPLICATION_DESCRIPTOR: {
				WebApplicationDescriptor webApplicationDescriptor = (WebApplicationDescriptor)theEObject;
				T result = caseWebApplicationDescriptor(webApplicationDescriptor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Channel</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Channel</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseChannel(Channel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Destination</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Destination</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDestination(Destination object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Driver Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Driver Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDriverConfig(DriverConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Destination Concept</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Destination Concept</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDestinationConcept(DestinationConcept object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Driver Manager</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Driver Manager</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDriverManager(DriverManager object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Driver Registration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Driver Registration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDriverRegistration(DriverRegistration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Serializer Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Serializer Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSerializerConfig(SerializerConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Choice Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Choice Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseChoiceConfiguration(ChoiceConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Choice</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Choice</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseChoice(Choice object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Serializer Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Serializer Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSerializerInfo(SerializerInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Extended Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Extended Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExtendedConfiguration(ExtendedConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Destination Descriptor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Destination Descriptor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDestinationDescriptor(DestinationDescriptor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Descriptor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Descriptor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseChannelDescriptor(ChannelDescriptor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Property Descriptor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property Descriptor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePropertyDescriptor(PropertyDescriptor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Property Descriptor Map Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property Descriptor Map Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePropertyDescriptorMapEntry(PropertyDescriptorMapEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Property Descriptor Map</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property Descriptor Map</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePropertyDescriptorMap(PropertyDescriptorMap object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DRIVER TYPE</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DRIVER TYPE</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDRIVER_TYPE(DRIVER_TYPE object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Driver Type Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Driver Type Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDriverTypeInfo(DriverTypeInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Http Channel Driver Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Http Channel Driver Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHttpChannelDriverConfig(HttpChannelDriverConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Web Application Descriptor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Web Application Descriptor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWebApplicationDescriptor(WebApplicationDescriptor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Entity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEntity(Entity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rule Participant</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rule Participant</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRuleParticipant(RuleParticipant object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Concept</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Concept</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConcept(Concept object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //ChannelSwitch
