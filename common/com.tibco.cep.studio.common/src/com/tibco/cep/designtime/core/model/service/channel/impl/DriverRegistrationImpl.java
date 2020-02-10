/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.service.channel.ChannelDescriptor;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.Choice;
import com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration;
import com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE;
import com.tibco.cep.designtime.core.model.service.channel.DestinationDescriptor;
import com.tibco.cep.designtime.core.model.service.channel.DriverRegistration;
import com.tibco.cep.designtime.core.model.service.channel.SerializerConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Driver Registration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverRegistrationImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverRegistrationImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverRegistrationImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverRegistrationImpl#getChannelDescriptor <em>Channel Descriptor</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverRegistrationImpl#getDestinationDescriptor <em>Destination Descriptor</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverRegistrationImpl#getResourcesAllowed <em>Resources Allowed</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverRegistrationImpl#getSerializerConfig <em>Serializer Config</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverRegistrationImpl#getChoiceConfigurations <em>Choice Configurations</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverRegistrationImpl#getExtendedConfigurations <em>Extended Configurations</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverRegistrationImpl#getDriverType <em>Driver Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverRegistrationImpl#getChoice <em>Choice</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DriverRegistrationImpl extends EObjectImpl implements DriverRegistration {
	/**
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getChannelDescriptor() <em>Channel Descriptor</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChannelDescriptor()
	 * @generated
	 * @ordered
	 */
	protected ChannelDescriptor channelDescriptor;

	/**
	 * The cached value of the '{@link #getDestinationDescriptor() <em>Destination Descriptor</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDestinationDescriptor()
	 * @generated
	 * @ordered
	 */
	protected DestinationDescriptor destinationDescriptor;

	/**
	 * The cached value of the '{@link #getResourcesAllowed() <em>Resources Allowed</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResourcesAllowed()
	 * @generated
	 * @ordered
	 */
	protected EList<String> resourcesAllowed;

	/**
	 * The cached value of the '{@link #getSerializerConfig() <em>Serializer Config</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSerializerConfig()
	 * @generated
	 * @ordered
	 */
	protected SerializerConfig serializerConfig;

	/**
	 * The cached value of the '{@link #getChoiceConfigurations() <em>Choice Configurations</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChoiceConfigurations()
	 * @generated
	 * @ordered
	 */
	protected EList<ChoiceConfiguration> choiceConfigurations;

	/**
	 * The cached value of the '{@link #getExtendedConfigurations() <em>Extended Configurations</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtendedConfigurations()
	 * @generated
	 * @ordered
	 */
	protected EList<ChoiceConfiguration> extendedConfigurations;

	/**
	 * The cached value of the '{@link #getDriverType() <em>Driver Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDriverType()
	 * @generated
	 * @ordered
	 */
	protected DRIVER_TYPE driverType;

	/**
	 * The cached value of the '{@link #getChoice() <em>Choice</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChoice()
	 * @generated
	 * @ordered
	 */
	protected EList<Choice> choice;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DriverRegistrationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ChannelPackage.Literals.DRIVER_REGISTRATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DRIVER_TYPE getDriverType() {
		if (driverType != null && driverType.eIsProxy()) {
			InternalEObject oldDriverType = (InternalEObject)driverType;
			driverType = (DRIVER_TYPE)eResolveProxy(oldDriverType);
			if (driverType != oldDriverType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ChannelPackage.DRIVER_REGISTRATION__DRIVER_TYPE, oldDriverType, driverType));
			}
		}
		return driverType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DRIVER_TYPE basicGetDriverType() {
		return driverType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDriverType(DRIVER_TYPE newDriverType) {
		DRIVER_TYPE oldDriverType = driverType;
		driverType = newDriverType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.DRIVER_REGISTRATION__DRIVER_TYPE, oldDriverType, driverType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Choice> getChoice() {
		if (choice == null) {
			choice = new EObjectContainmentEList<Choice>(Choice.class, this, ChannelPackage.DRIVER_REGISTRATION__CHOICE);
		}
		return choice;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ChannelPackage.DRIVER_REGISTRATION__CHOICE:
				return ((InternalEList<?>)getChoice()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.DRIVER_REGISTRATION__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.DRIVER_REGISTRATION__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.DRIVER_REGISTRATION__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChannelDescriptor getChannelDescriptor() {
		if (channelDescriptor != null && channelDescriptor.eIsProxy()) {
			InternalEObject oldChannelDescriptor = (InternalEObject)channelDescriptor;
			channelDescriptor = (ChannelDescriptor)eResolveProxy(oldChannelDescriptor);
			if (channelDescriptor != oldChannelDescriptor) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ChannelPackage.DRIVER_REGISTRATION__CHANNEL_DESCRIPTOR, oldChannelDescriptor, channelDescriptor));
			}
		}
		return channelDescriptor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChannelDescriptor basicGetChannelDescriptor() {
		return channelDescriptor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChannelDescriptor(ChannelDescriptor newChannelDescriptor) {
		ChannelDescriptor oldChannelDescriptor = channelDescriptor;
		channelDescriptor = newChannelDescriptor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.DRIVER_REGISTRATION__CHANNEL_DESCRIPTOR, oldChannelDescriptor, channelDescriptor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DestinationDescriptor getDestinationDescriptor() {
		if (destinationDescriptor != null && destinationDescriptor.eIsProxy()) {
			InternalEObject oldDestinationDescriptor = (InternalEObject)destinationDescriptor;
			destinationDescriptor = (DestinationDescriptor)eResolveProxy(oldDestinationDescriptor);
			if (destinationDescriptor != oldDestinationDescriptor) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ChannelPackage.DRIVER_REGISTRATION__DESTINATION_DESCRIPTOR, oldDestinationDescriptor, destinationDescriptor));
			}
		}
		return destinationDescriptor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DestinationDescriptor basicGetDestinationDescriptor() {
		return destinationDescriptor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDestinationDescriptor(DestinationDescriptor newDestinationDescriptor) {
		DestinationDescriptor oldDestinationDescriptor = destinationDescriptor;
		destinationDescriptor = newDestinationDescriptor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.DRIVER_REGISTRATION__DESTINATION_DESCRIPTOR, oldDestinationDescriptor, destinationDescriptor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getResourcesAllowed() {
		if (resourcesAllowed == null) {
			resourcesAllowed = new EDataTypeUniqueEList<String>(String.class, this, ChannelPackage.DRIVER_REGISTRATION__RESOURCES_ALLOWED);
		}
		return resourcesAllowed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SerializerConfig getSerializerConfig() {
		if (serializerConfig != null && serializerConfig.eIsProxy()) {
			InternalEObject oldSerializerConfig = (InternalEObject)serializerConfig;
			serializerConfig = (SerializerConfig)eResolveProxy(oldSerializerConfig);
			if (serializerConfig != oldSerializerConfig) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ChannelPackage.DRIVER_REGISTRATION__SERIALIZER_CONFIG, oldSerializerConfig, serializerConfig));
			}
		}
		return serializerConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SerializerConfig basicGetSerializerConfig() {
		return serializerConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSerializerConfig(SerializerConfig newSerializerConfig) {
		SerializerConfig oldSerializerConfig = serializerConfig;
		serializerConfig = newSerializerConfig;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.DRIVER_REGISTRATION__SERIALIZER_CONFIG, oldSerializerConfig, serializerConfig));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ChoiceConfiguration> getChoiceConfigurations() {
		if (choiceConfigurations == null) {
			choiceConfigurations = new EObjectResolvingEList<ChoiceConfiguration>(ChoiceConfiguration.class, this, ChannelPackage.DRIVER_REGISTRATION__CHOICE_CONFIGURATIONS);
		}
		return choiceConfigurations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ChoiceConfiguration> getExtendedConfigurations() {
		if (extendedConfigurations == null) {
			extendedConfigurations = new EObjectResolvingEList<ChoiceConfiguration>(ChoiceConfiguration.class, this, ChannelPackage.DRIVER_REGISTRATION__EXTENDED_CONFIGURATIONS);
		}
		return extendedConfigurations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ChannelPackage.DRIVER_REGISTRATION__LABEL:
				return getLabel();
			case ChannelPackage.DRIVER_REGISTRATION__VERSION:
				return getVersion();
			case ChannelPackage.DRIVER_REGISTRATION__DESCRIPTION:
				return getDescription();
			case ChannelPackage.DRIVER_REGISTRATION__CHANNEL_DESCRIPTOR:
				if (resolve) return getChannelDescriptor();
				return basicGetChannelDescriptor();
			case ChannelPackage.DRIVER_REGISTRATION__DESTINATION_DESCRIPTOR:
				if (resolve) return getDestinationDescriptor();
				return basicGetDestinationDescriptor();
			case ChannelPackage.DRIVER_REGISTRATION__RESOURCES_ALLOWED:
				return getResourcesAllowed();
			case ChannelPackage.DRIVER_REGISTRATION__SERIALIZER_CONFIG:
				if (resolve) return getSerializerConfig();
				return basicGetSerializerConfig();
			case ChannelPackage.DRIVER_REGISTRATION__CHOICE_CONFIGURATIONS:
				return getChoiceConfigurations();
			case ChannelPackage.DRIVER_REGISTRATION__EXTENDED_CONFIGURATIONS:
				return getExtendedConfigurations();
			case ChannelPackage.DRIVER_REGISTRATION__DRIVER_TYPE:
				if (resolve) return getDriverType();
				return basicGetDriverType();
			case ChannelPackage.DRIVER_REGISTRATION__CHOICE:
				return getChoice();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ChannelPackage.DRIVER_REGISTRATION__LABEL:
				setLabel((String)newValue);
				return;
			case ChannelPackage.DRIVER_REGISTRATION__VERSION:
				setVersion((String)newValue);
				return;
			case ChannelPackage.DRIVER_REGISTRATION__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case ChannelPackage.DRIVER_REGISTRATION__CHANNEL_DESCRIPTOR:
				setChannelDescriptor((ChannelDescriptor)newValue);
				return;
			case ChannelPackage.DRIVER_REGISTRATION__DESTINATION_DESCRIPTOR:
				setDestinationDescriptor((DestinationDescriptor)newValue);
				return;
			case ChannelPackage.DRIVER_REGISTRATION__RESOURCES_ALLOWED:
				getResourcesAllowed().clear();
				getResourcesAllowed().addAll((Collection<? extends String>)newValue);
				return;
			case ChannelPackage.DRIVER_REGISTRATION__SERIALIZER_CONFIG:
				setSerializerConfig((SerializerConfig)newValue);
				return;
			case ChannelPackage.DRIVER_REGISTRATION__CHOICE_CONFIGURATIONS:
				getChoiceConfigurations().clear();
				getChoiceConfigurations().addAll((Collection<? extends ChoiceConfiguration>)newValue);
				return;
			case ChannelPackage.DRIVER_REGISTRATION__EXTENDED_CONFIGURATIONS:
				getExtendedConfigurations().clear();
				getExtendedConfigurations().addAll((Collection<? extends ChoiceConfiguration>)newValue);
				return;
			case ChannelPackage.DRIVER_REGISTRATION__DRIVER_TYPE:
				setDriverType((DRIVER_TYPE)newValue);
				return;
			case ChannelPackage.DRIVER_REGISTRATION__CHOICE:
				getChoice().clear();
				getChoice().addAll((Collection<? extends Choice>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ChannelPackage.DRIVER_REGISTRATION__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
			case ChannelPackage.DRIVER_REGISTRATION__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case ChannelPackage.DRIVER_REGISTRATION__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case ChannelPackage.DRIVER_REGISTRATION__CHANNEL_DESCRIPTOR:
				setChannelDescriptor((ChannelDescriptor)null);
				return;
			case ChannelPackage.DRIVER_REGISTRATION__DESTINATION_DESCRIPTOR:
				setDestinationDescriptor((DestinationDescriptor)null);
				return;
			case ChannelPackage.DRIVER_REGISTRATION__RESOURCES_ALLOWED:
				getResourcesAllowed().clear();
				return;
			case ChannelPackage.DRIVER_REGISTRATION__SERIALIZER_CONFIG:
				setSerializerConfig((SerializerConfig)null);
				return;
			case ChannelPackage.DRIVER_REGISTRATION__CHOICE_CONFIGURATIONS:
				getChoiceConfigurations().clear();
				return;
			case ChannelPackage.DRIVER_REGISTRATION__EXTENDED_CONFIGURATIONS:
				getExtendedConfigurations().clear();
				return;
			case ChannelPackage.DRIVER_REGISTRATION__DRIVER_TYPE:
				setDriverType((DRIVER_TYPE)null);
				return;
			case ChannelPackage.DRIVER_REGISTRATION__CHOICE:
				getChoice().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ChannelPackage.DRIVER_REGISTRATION__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case ChannelPackage.DRIVER_REGISTRATION__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
			case ChannelPackage.DRIVER_REGISTRATION__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case ChannelPackage.DRIVER_REGISTRATION__CHANNEL_DESCRIPTOR:
				return channelDescriptor != null;
			case ChannelPackage.DRIVER_REGISTRATION__DESTINATION_DESCRIPTOR:
				return destinationDescriptor != null;
			case ChannelPackage.DRIVER_REGISTRATION__RESOURCES_ALLOWED:
				return resourcesAllowed != null && !resourcesAllowed.isEmpty();
			case ChannelPackage.DRIVER_REGISTRATION__SERIALIZER_CONFIG:
				return serializerConfig != null;
			case ChannelPackage.DRIVER_REGISTRATION__CHOICE_CONFIGURATIONS:
				return choiceConfigurations != null && !choiceConfigurations.isEmpty();
			case ChannelPackage.DRIVER_REGISTRATION__EXTENDED_CONFIGURATIONS:
				return extendedConfigurations != null && !extendedConfigurations.isEmpty();
			case ChannelPackage.DRIVER_REGISTRATION__DRIVER_TYPE:
				return driverType != null;
			case ChannelPackage.DRIVER_REGISTRATION__CHOICE:
				return choice != null && !choice.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (label: ");
		result.append(label);
		result.append(", version: ");
		result.append(version);
		result.append(", description: ");
		result.append(description);
		result.append(", resourcesAllowed: ");
		result.append(resourcesAllowed);
		result.append(')');
		return result.toString();
	}

} //DriverRegistrationImpl
