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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.Choice;
import com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.designtime.core.model.service.channel.ExtendedConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Driver Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverConfigImpl#getConfigMethod <em>Config Method</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverConfigImpl#getReference <em>Reference</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverConfigImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverConfigImpl#getChannel <em>Channel</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverConfigImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverConfigImpl#getDestinations <em>Destinations</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverConfigImpl#getExtendedConfiguration <em>Extended Configuration</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverConfigImpl#getDriverType <em>Driver Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverConfigImpl#getChoice <em>Choice</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DriverConfigImpl extends EObjectImpl implements DriverConfig {
	/**
	 * The default value of the '{@link #getConfigMethod() <em>Config Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfigMethod()
	 * @generated
	 * @ordered
	 */
	protected static final CONFIG_METHOD CONFIG_METHOD_EDEFAULT = CONFIG_METHOD.PROPERTIES;

	/**
	 * The cached value of the '{@link #getConfigMethod() <em>Config Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfigMethod()
	 * @generated
	 * @ordered
	 */
	protected CONFIG_METHOD configMethod = CONFIG_METHOD_EDEFAULT;

	/**
	 * The default value of the '{@link #getReference() <em>Reference</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReference()
	 * @generated
	 * @ordered
	 */
	protected static final String REFERENCE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReference() <em>Reference</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReference()
	 * @generated
	 * @ordered
	 */
	protected String reference = REFERENCE_EDEFAULT;

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
	 * The cached value of the '{@link #getChannel() <em>Channel</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChannel()
	 * @generated
	 * @ordered
	 */
	protected Channel channel;

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected PropertyMap properties;

	/**
	 * The cached value of the '{@link #getDestinations() <em>Destinations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDestinations()
	 * @generated
	 * @ordered
	 */
	protected EList<Destination> destinations;

	/**
	 * The cached value of the '{@link #getExtendedConfiguration() <em>Extended Configuration</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtendedConfiguration()
	 * @generated
	 * @ordered
	 */
	protected ExtendedConfiguration extendedConfiguration;

	/**
	 * The cached value of the '{@link #getDriverType() <em>Driver Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDriverType()
	 * @generated
	 * @ordered
	 */
	protected DRIVER_TYPE driverType;

	/**
	 * The cached value of the '{@link #getChoice() <em>Choice</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChoice()
	 * @generated
	 * @ordered
	 */
	protected Choice choice;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DriverConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ChannelPackage.Literals.DRIVER_CONFIG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DRIVER_TYPE getDriverType() {
		return driverType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDriverType(DRIVER_TYPE newDriverType, NotificationChain msgs) {
		DRIVER_TYPE oldDriverType = driverType;
		driverType = newDriverType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ChannelPackage.DRIVER_CONFIG__DRIVER_TYPE, oldDriverType, newDriverType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDriverType(DRIVER_TYPE newDriverType) {
		if (newDriverType != driverType) {
			NotificationChain msgs = null;
			if (driverType != null)
				msgs = ((InternalEObject)driverType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ChannelPackage.DRIVER_CONFIG__DRIVER_TYPE, null, msgs);
			if (newDriverType != null)
				msgs = ((InternalEObject)newDriverType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ChannelPackage.DRIVER_CONFIG__DRIVER_TYPE, null, msgs);
			msgs = basicSetDriverType(newDriverType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.DRIVER_CONFIG__DRIVER_TYPE, newDriverType, newDriverType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Choice getChoice() {
		return choice;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetChoice(Choice newChoice, NotificationChain msgs) {
		Choice oldChoice = choice;
		choice = newChoice;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ChannelPackage.DRIVER_CONFIG__CHOICE, oldChoice, newChoice);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChoice(Choice newChoice) {
		if (newChoice != choice) {
			NotificationChain msgs = null;
			if (choice != null)
				msgs = ((InternalEObject)choice).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ChannelPackage.DRIVER_CONFIG__CHOICE, null, msgs);
			if (newChoice != null)
				msgs = ((InternalEObject)newChoice).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ChannelPackage.DRIVER_CONFIG__CHOICE, null, msgs);
			msgs = basicSetChoice(newChoice, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.DRIVER_CONFIG__CHOICE, newChoice, newChoice));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CONFIG_METHOD getConfigMethod() {
		return configMethod;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConfigMethod(CONFIG_METHOD newConfigMethod) {
		CONFIG_METHOD oldConfigMethod = configMethod;
		configMethod = newConfigMethod == null ? CONFIG_METHOD_EDEFAULT : newConfigMethod;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.DRIVER_CONFIG__CONFIG_METHOD, oldConfigMethod, configMethod));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReference(String newReference) {
		String oldReference = reference;
		reference = newReference;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.DRIVER_CONFIG__REFERENCE, oldReference, reference));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.DRIVER_CONFIG__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Channel getChannel() {
		if (channel != null && channel.eIsProxy()) {
			InternalEObject oldChannel = (InternalEObject)channel;
			channel = (Channel)eResolveProxy(oldChannel);
			if (channel != oldChannel) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ChannelPackage.DRIVER_CONFIG__CHANNEL, oldChannel, channel));
			}
		}
		return channel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Channel basicGetChannel() {
		return channel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChannel(Channel newChannel) {
		Channel oldChannel = channel;
		channel = newChannel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.DRIVER_CONFIG__CHANNEL, oldChannel, channel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyMap getProperties() {
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProperties(PropertyMap newProperties, NotificationChain msgs) {
		PropertyMap oldProperties = properties;
		properties = newProperties;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ChannelPackage.DRIVER_CONFIG__PROPERTIES, oldProperties, newProperties);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProperties(PropertyMap newProperties) {
		if (newProperties != properties) {
			NotificationChain msgs = null;
			if (properties != null)
				msgs = ((InternalEObject)properties).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ChannelPackage.DRIVER_CONFIG__PROPERTIES, null, msgs);
			if (newProperties != null)
				msgs = ((InternalEObject)newProperties).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ChannelPackage.DRIVER_CONFIG__PROPERTIES, null, msgs);
			msgs = basicSetProperties(newProperties, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.DRIVER_CONFIG__PROPERTIES, newProperties, newProperties));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Destination> getDestinations() {
		if (destinations == null) {
			destinations = new EObjectContainmentEList<Destination>(Destination.class, this, ChannelPackage.DRIVER_CONFIG__DESTINATIONS);
		}
		return destinations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExtendedConfiguration getExtendedConfiguration() {
		return extendedConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExtendedConfiguration(ExtendedConfiguration newExtendedConfiguration, NotificationChain msgs) {
		ExtendedConfiguration oldExtendedConfiguration = extendedConfiguration;
		extendedConfiguration = newExtendedConfiguration;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ChannelPackage.DRIVER_CONFIG__EXTENDED_CONFIGURATION, oldExtendedConfiguration, newExtendedConfiguration);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtendedConfiguration(ExtendedConfiguration newExtendedConfiguration) {
		if (newExtendedConfiguration != extendedConfiguration) {
			NotificationChain msgs = null;
			if (extendedConfiguration != null)
				msgs = ((InternalEObject)extendedConfiguration).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ChannelPackage.DRIVER_CONFIG__EXTENDED_CONFIGURATION, null, msgs);
			if (newExtendedConfiguration != null)
				msgs = ((InternalEObject)newExtendedConfiguration).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ChannelPackage.DRIVER_CONFIG__EXTENDED_CONFIGURATION, null, msgs);
			msgs = basicSetExtendedConfiguration(newExtendedConfiguration, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.DRIVER_CONFIG__EXTENDED_CONFIGURATION, newExtendedConfiguration, newExtendedConfiguration));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ChannelPackage.DRIVER_CONFIG__PROPERTIES:
				return basicSetProperties(null, msgs);
			case ChannelPackage.DRIVER_CONFIG__DESTINATIONS:
				return ((InternalEList<?>)getDestinations()).basicRemove(otherEnd, msgs);
			case ChannelPackage.DRIVER_CONFIG__EXTENDED_CONFIGURATION:
				return basicSetExtendedConfiguration(null, msgs);
			case ChannelPackage.DRIVER_CONFIG__DRIVER_TYPE:
				return basicSetDriverType(null, msgs);
			case ChannelPackage.DRIVER_CONFIG__CHOICE:
				return basicSetChoice(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ChannelPackage.DRIVER_CONFIG__CONFIG_METHOD:
				return getConfigMethod();
			case ChannelPackage.DRIVER_CONFIG__REFERENCE:
				return getReference();
			case ChannelPackage.DRIVER_CONFIG__LABEL:
				return getLabel();
			case ChannelPackage.DRIVER_CONFIG__CHANNEL:
				if (resolve) return getChannel();
				return basicGetChannel();
			case ChannelPackage.DRIVER_CONFIG__PROPERTIES:
				return getProperties();
			case ChannelPackage.DRIVER_CONFIG__DESTINATIONS:
				return getDestinations();
			case ChannelPackage.DRIVER_CONFIG__EXTENDED_CONFIGURATION:
				return getExtendedConfiguration();
			case ChannelPackage.DRIVER_CONFIG__DRIVER_TYPE:
				return getDriverType();
			case ChannelPackage.DRIVER_CONFIG__CHOICE:
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
			case ChannelPackage.DRIVER_CONFIG__CONFIG_METHOD:
				setConfigMethod((CONFIG_METHOD)newValue);
				return;
			case ChannelPackage.DRIVER_CONFIG__REFERENCE:
				setReference((String)newValue);
				return;
			case ChannelPackage.DRIVER_CONFIG__LABEL:
				setLabel((String)newValue);
				return;
			case ChannelPackage.DRIVER_CONFIG__CHANNEL:
				setChannel((Channel)newValue);
				return;
			case ChannelPackage.DRIVER_CONFIG__PROPERTIES:
				setProperties((PropertyMap)newValue);
				return;
			case ChannelPackage.DRIVER_CONFIG__DESTINATIONS:
				getDestinations().clear();
				getDestinations().addAll((Collection<? extends Destination>)newValue);
				return;
			case ChannelPackage.DRIVER_CONFIG__EXTENDED_CONFIGURATION:
				setExtendedConfiguration((ExtendedConfiguration)newValue);
				return;
			case ChannelPackage.DRIVER_CONFIG__DRIVER_TYPE:
				setDriverType((DRIVER_TYPE)newValue);
				return;
			case ChannelPackage.DRIVER_CONFIG__CHOICE:
				setChoice((Choice)newValue);
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
			case ChannelPackage.DRIVER_CONFIG__CONFIG_METHOD:
				setConfigMethod(CONFIG_METHOD_EDEFAULT);
				return;
			case ChannelPackage.DRIVER_CONFIG__REFERENCE:
				setReference(REFERENCE_EDEFAULT);
				return;
			case ChannelPackage.DRIVER_CONFIG__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
			case ChannelPackage.DRIVER_CONFIG__CHANNEL:
				setChannel((Channel)null);
				return;
			case ChannelPackage.DRIVER_CONFIG__PROPERTIES:
				setProperties((PropertyMap)null);
				return;
			case ChannelPackage.DRIVER_CONFIG__DESTINATIONS:
				getDestinations().clear();
				return;
			case ChannelPackage.DRIVER_CONFIG__EXTENDED_CONFIGURATION:
				setExtendedConfiguration((ExtendedConfiguration)null);
				return;
			case ChannelPackage.DRIVER_CONFIG__DRIVER_TYPE:
				setDriverType((DRIVER_TYPE)null);
				return;
			case ChannelPackage.DRIVER_CONFIG__CHOICE:
				setChoice((Choice)null);
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
			case ChannelPackage.DRIVER_CONFIG__CONFIG_METHOD:
				return configMethod != CONFIG_METHOD_EDEFAULT;
			case ChannelPackage.DRIVER_CONFIG__REFERENCE:
				return REFERENCE_EDEFAULT == null ? reference != null : !REFERENCE_EDEFAULT.equals(reference);
			case ChannelPackage.DRIVER_CONFIG__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case ChannelPackage.DRIVER_CONFIG__CHANNEL:
				return channel != null;
			case ChannelPackage.DRIVER_CONFIG__PROPERTIES:
				return properties != null;
			case ChannelPackage.DRIVER_CONFIG__DESTINATIONS:
				return destinations != null && !destinations.isEmpty();
			case ChannelPackage.DRIVER_CONFIG__EXTENDED_CONFIGURATION:
				return extendedConfiguration != null;
			case ChannelPackage.DRIVER_CONFIG__DRIVER_TYPE:
				return driverType != null;
			case ChannelPackage.DRIVER_CONFIG__CHOICE:
				return choice != null;
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
		result.append(" (configMethod: ");
		result.append(configMethod);
		result.append(", reference: ");
		result.append(reference);
		result.append(", label: ");
		result.append(label);
		result.append(')');
		return result.toString();
	}

} //DriverConfigImpl
