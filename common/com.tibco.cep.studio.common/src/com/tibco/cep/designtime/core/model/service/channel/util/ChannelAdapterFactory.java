/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

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
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage
 * @generated
 */
public class ChannelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ChannelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChannelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ChannelPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChannelSwitch<Adapter> modelSwitch =
		new ChannelSwitch<Adapter>() {
			@Override
			public Adapter caseChannel(Channel object) {
				return createChannelAdapter();
			}
			@Override
			public Adapter caseDestination(Destination object) {
				return createDestinationAdapter();
			}
			@Override
			public Adapter caseDriverConfig(DriverConfig object) {
				return createDriverConfigAdapter();
			}
			@Override
			public Adapter caseDestinationConcept(DestinationConcept object) {
				return createDestinationConceptAdapter();
			}
			@Override
			public Adapter caseDriverManager(DriverManager object) {
				return createDriverManagerAdapter();
			}
			@Override
			public Adapter caseDriverRegistration(DriverRegistration object) {
				return createDriverRegistrationAdapter();
			}
			@Override
			public Adapter caseSerializerConfig(SerializerConfig object) {
				return createSerializerConfigAdapter();
			}
			@Override
			public Adapter caseChoiceConfiguration(ChoiceConfiguration object) {
				return createChoiceConfigurationAdapter();
			}
			@Override
			public Adapter caseChoice(Choice object) {
				return createChoiceAdapter();
			}
			@Override
			public Adapter caseSerializerInfo(SerializerInfo object) {
				return createSerializerInfoAdapter();
			}
			@Override
			public Adapter caseExtendedConfiguration(ExtendedConfiguration object) {
				return createExtendedConfigurationAdapter();
			}
			@Override
			public Adapter caseDestinationDescriptor(DestinationDescriptor object) {
				return createDestinationDescriptorAdapter();
			}
			@Override
			public Adapter caseChannelDescriptor(ChannelDescriptor object) {
				return createChannelDescriptorAdapter();
			}
			@Override
			public Adapter casePropertyDescriptor(PropertyDescriptor object) {
				return createPropertyDescriptorAdapter();
			}
			@Override
			public Adapter casePropertyDescriptorMapEntry(PropertyDescriptorMapEntry object) {
				return createPropertyDescriptorMapEntryAdapter();
			}
			@Override
			public Adapter casePropertyDescriptorMap(PropertyDescriptorMap object) {
				return createPropertyDescriptorMapAdapter();
			}
			@Override
			public Adapter caseDRIVER_TYPE(DRIVER_TYPE object) {
				return createDRIVER_TYPEAdapter();
			}
			@Override
			public Adapter caseDriverTypeInfo(DriverTypeInfo object) {
				return createDriverTypeInfoAdapter();
			}
			@Override
			public Adapter caseHttpChannelDriverConfig(HttpChannelDriverConfig object) {
				return createHttpChannelDriverConfigAdapter();
			}
			@Override
			public Adapter caseWebApplicationDescriptor(WebApplicationDescriptor object) {
				return createWebApplicationDescriptorAdapter();
			}
			@Override
			public Adapter caseEntity(Entity object) {
				return createEntityAdapter();
			}
			@Override
			public Adapter caseRuleParticipant(RuleParticipant object) {
				return createRuleParticipantAdapter();
			}
			@Override
			public Adapter caseConcept(Concept object) {
				return createConceptAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.service.channel.Channel <em>Channel</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.service.channel.Channel
	 * @generated
	 */
	public Adapter createChannelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.service.channel.Destination <em>Destination</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.service.channel.Destination
	 * @generated
	 */
	public Adapter createDestinationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig <em>Driver Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverConfig
	 * @generated
	 */
	public Adapter createDriverConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.service.channel.DestinationConcept <em>Destination Concept</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DestinationConcept
	 * @generated
	 */
	public Adapter createDestinationConceptAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.service.channel.DriverManager <em>Driver Manager</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverManager
	 * @generated
	 */
	public Adapter createDriverManagerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration <em>Driver Registration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverRegistration
	 * @generated
	 */
	public Adapter createDriverRegistrationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.service.channel.SerializerConfig <em>Serializer Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.service.channel.SerializerConfig
	 * @generated
	 */
	public Adapter createSerializerConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration <em>Choice Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration
	 * @generated
	 */
	public Adapter createChoiceConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.service.channel.Choice <em>Choice</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.service.channel.Choice
	 * @generated
	 */
	public Adapter createChoiceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.service.channel.SerializerInfo <em>Serializer Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.service.channel.SerializerInfo
	 * @generated
	 */
	public Adapter createSerializerInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.service.channel.ExtendedConfiguration <em>Extended Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.service.channel.ExtendedConfiguration
	 * @generated
	 */
	public Adapter createExtendedConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.service.channel.DestinationDescriptor <em>Destination Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DestinationDescriptor
	 * @generated
	 */
	public Adapter createDestinationDescriptorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.service.channel.ChannelDescriptor <em>Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelDescriptor
	 * @generated
	 */
	public Adapter createChannelDescriptorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor <em>Property Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor
	 * @generated
	 */
	public Adapter createPropertyDescriptorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMapEntry <em>Property Descriptor Map Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMapEntry
	 * @generated
	 */
	public Adapter createPropertyDescriptorMapEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMap <em>Property Descriptor Map</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMap
	 * @generated
	 */
	public Adapter createPropertyDescriptorMapAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE <em>DRIVER TYPE</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE
	 * @generated
	 */
	public Adapter createDRIVER_TYPEAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.service.channel.DriverTypeInfo <em>Driver Type Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverTypeInfo
	 * @generated
	 */
	public Adapter createDriverTypeInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.service.channel.HttpChannelDriverConfig <em>Http Channel Driver Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.service.channel.HttpChannelDriverConfig
	 * @generated
	 */
	public Adapter createHttpChannelDriverConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.service.channel.WebApplicationDescriptor <em>Web Application Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.service.channel.WebApplicationDescriptor
	 * @generated
	 */
	public Adapter createWebApplicationDescriptorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.Entity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.Entity
	 * @generated
	 */
	public Adapter createEntityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.RuleParticipant <em>Rule Participant</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.RuleParticipant
	 * @generated
	 */
	public Adapter createRuleParticipantAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.element.Concept <em>Concept</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.element.Concept
	 * @generated
	 */
	public Adapter createConceptAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //ChannelAdapterFactory
