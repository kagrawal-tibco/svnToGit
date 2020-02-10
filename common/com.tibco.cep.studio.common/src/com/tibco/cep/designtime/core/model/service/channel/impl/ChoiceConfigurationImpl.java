/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.Choice;
import com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Choice Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.ChoiceConfigurationImpl#getPropertyName <em>Property Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.ChoiceConfigurationImpl#getPropertyParent <em>Property Parent</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.ChoiceConfigurationImpl#getConfigType <em>Config Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.ChoiceConfigurationImpl#getChoices <em>Choices</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.ChoiceConfigurationImpl#getDefaultValue <em>Default Value</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.ChoiceConfigurationImpl#getDisplayName <em>Display Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ChoiceConfigurationImpl extends EObjectImpl implements ChoiceConfiguration {
	/**
	 * The default value of the '{@link #getPropertyName() <em>Property Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyName()
	 * @generated
	 * @ordered
	 */
	protected static final String PROPERTY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPropertyName() <em>Property Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyName()
	 * @generated
	 * @ordered
	 */
	protected String propertyName = PROPERTY_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getPropertyParent() <em>Property Parent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyParent()
	 * @generated
	 * @ordered
	 */
	protected static final String PROPERTY_PARENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPropertyParent() <em>Property Parent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyParent()
	 * @generated
	 * @ordered
	 */
	protected String propertyParent = PROPERTY_PARENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getConfigType() <em>Config Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfigType()
	 * @generated
	 * @ordered
	 */
	protected static final String CONFIG_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getConfigType() <em>Config Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfigType()
	 * @generated
	 * @ordered
	 */
	protected String configType = CONFIG_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getChoices() <em>Choices</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChoices()
	 * @generated
	 * @ordered
	 */
	protected EList<Choice> choices;

	/**
	 * The default value of the '{@link #getDefaultValue() <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultValue()
	 * @generated
	 * @ordered
	 */
	protected static final Object DEFAULT_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultValue() <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultValue()
	 * @generated
	 * @ordered
	 */
	protected Object defaultValue = DEFAULT_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected static final String DISPLAY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected String displayName = DISPLAY_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChoiceConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ChannelPackage.Literals.CHOICE_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropertyName(String newPropertyName) {
		String oldPropertyName = propertyName;
		propertyName = newPropertyName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.CHOICE_CONFIGURATION__PROPERTY_NAME, oldPropertyName, propertyName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPropertyParent() {
		return propertyParent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropertyParent(String newPropertyParent) {
		String oldPropertyParent = propertyParent;
		propertyParent = newPropertyParent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.CHOICE_CONFIGURATION__PROPERTY_PARENT, oldPropertyParent, propertyParent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getConfigType() {
		return configType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConfigType(String newConfigType) {
		String oldConfigType = configType;
		configType = newConfigType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.CHOICE_CONFIGURATION__CONFIG_TYPE, oldConfigType, configType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Choice> getChoices() {
		if (choices == null) {
			choices = new EObjectResolvingEList<Choice>(Choice.class, this, ChannelPackage.CHOICE_CONFIGURATION__CHOICES);
		}
		return choices;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getDefaultValue() {
		return defaultValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefaultValue(Object newDefaultValue) {
		Object oldDefaultValue = defaultValue;
		defaultValue = newDefaultValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.CHOICE_CONFIGURATION__DEFAULT_VALUE, oldDefaultValue, defaultValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDisplayName(String newDisplayName) {
		String oldDisplayName = displayName;
		displayName = newDisplayName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.CHOICE_CONFIGURATION__DISPLAY_NAME, oldDisplayName, displayName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ChannelPackage.CHOICE_CONFIGURATION__PROPERTY_NAME:
				return getPropertyName();
			case ChannelPackage.CHOICE_CONFIGURATION__PROPERTY_PARENT:
				return getPropertyParent();
			case ChannelPackage.CHOICE_CONFIGURATION__CONFIG_TYPE:
				return getConfigType();
			case ChannelPackage.CHOICE_CONFIGURATION__CHOICES:
				return getChoices();
			case ChannelPackage.CHOICE_CONFIGURATION__DEFAULT_VALUE:
				return getDefaultValue();
			case ChannelPackage.CHOICE_CONFIGURATION__DISPLAY_NAME:
				return getDisplayName();
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
			case ChannelPackage.CHOICE_CONFIGURATION__PROPERTY_NAME:
				setPropertyName((String)newValue);
				return;
			case ChannelPackage.CHOICE_CONFIGURATION__PROPERTY_PARENT:
				setPropertyParent((String)newValue);
				return;
			case ChannelPackage.CHOICE_CONFIGURATION__CONFIG_TYPE:
				setConfigType((String)newValue);
				return;
			case ChannelPackage.CHOICE_CONFIGURATION__CHOICES:
				getChoices().clear();
				getChoices().addAll((Collection<? extends Choice>)newValue);
				return;
			case ChannelPackage.CHOICE_CONFIGURATION__DEFAULT_VALUE:
				setDefaultValue(newValue);
				return;
			case ChannelPackage.CHOICE_CONFIGURATION__DISPLAY_NAME:
				setDisplayName((String)newValue);
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
			case ChannelPackage.CHOICE_CONFIGURATION__PROPERTY_NAME:
				setPropertyName(PROPERTY_NAME_EDEFAULT);
				return;
			case ChannelPackage.CHOICE_CONFIGURATION__PROPERTY_PARENT:
				setPropertyParent(PROPERTY_PARENT_EDEFAULT);
				return;
			case ChannelPackage.CHOICE_CONFIGURATION__CONFIG_TYPE:
				setConfigType(CONFIG_TYPE_EDEFAULT);
				return;
			case ChannelPackage.CHOICE_CONFIGURATION__CHOICES:
				getChoices().clear();
				return;
			case ChannelPackage.CHOICE_CONFIGURATION__DEFAULT_VALUE:
				setDefaultValue(DEFAULT_VALUE_EDEFAULT);
				return;
			case ChannelPackage.CHOICE_CONFIGURATION__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
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
			case ChannelPackage.CHOICE_CONFIGURATION__PROPERTY_NAME:
				return PROPERTY_NAME_EDEFAULT == null ? propertyName != null : !PROPERTY_NAME_EDEFAULT.equals(propertyName);
			case ChannelPackage.CHOICE_CONFIGURATION__PROPERTY_PARENT:
				return PROPERTY_PARENT_EDEFAULT == null ? propertyParent != null : !PROPERTY_PARENT_EDEFAULT.equals(propertyParent);
			case ChannelPackage.CHOICE_CONFIGURATION__CONFIG_TYPE:
				return CONFIG_TYPE_EDEFAULT == null ? configType != null : !CONFIG_TYPE_EDEFAULT.equals(configType);
			case ChannelPackage.CHOICE_CONFIGURATION__CHOICES:
				return choices != null && !choices.isEmpty();
			case ChannelPackage.CHOICE_CONFIGURATION__DEFAULT_VALUE:
				return DEFAULT_VALUE_EDEFAULT == null ? defaultValue != null : !DEFAULT_VALUE_EDEFAULT.equals(defaultValue);
			case ChannelPackage.CHOICE_CONFIGURATION__DISPLAY_NAME:
				return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
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
		result.append(" (propertyName: ");
		result.append(propertyName);
		result.append(", propertyParent: ");
		result.append(propertyParent);
		result.append(", configType: ");
		result.append(configType);
		result.append(", defaultValue: ");
		result.append(defaultValue);
		result.append(", displayName: ");
		result.append(displayName);
		result.append(')');
		return result.toString();
	}

} //ChoiceConfigurationImpl
