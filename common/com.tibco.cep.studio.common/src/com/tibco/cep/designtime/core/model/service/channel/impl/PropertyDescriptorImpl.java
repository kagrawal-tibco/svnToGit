/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property Descriptor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.PropertyDescriptorImpl#getDefaultValue <em>Default Value</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.PropertyDescriptorImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.PropertyDescriptorImpl#getPattern <em>Pattern</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.PropertyDescriptorImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.PropertyDescriptorImpl#isMandatory <em>Mandatory</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.PropertyDescriptorImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.PropertyDescriptorImpl#isGvToggle <em>Gv Toggle</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PropertyDescriptorImpl extends EObjectImpl implements PropertyDescriptor {
	/**
	 * The default value of the '{@link #getDefaultValue() <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultValue()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultValue() <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultValue()
	 * @generated
	 * @ordered
	 */
	protected String defaultValue = DEFAULT_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getPattern() <em>Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPattern()
	 * @generated
	 * @ordered
	 */
	protected static final String PATTERN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPattern() <em>Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPattern()
	 * @generated
	 * @ordered
	 */
	protected String pattern = PATTERN_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #isMandatory() <em>Mandatory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMandatory()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MANDATORY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMandatory() <em>Mandatory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMandatory()
	 * @generated
	 * @ordered
	 */
	protected boolean mandatory = MANDATORY_EDEFAULT;
	
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
	 * The default value of the '{@link #isGvToggle() <em>Gv Toggle</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGvToggle()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GV_TOGGLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isGvToggle() <em>Gv Toggle</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGvToggle()
	 * @generated
	 * @ordered
	 */
	protected boolean gvToggle = GV_TOGGLE_EDEFAULT;

	/**
	 * @generated NOT
	 */
	protected boolean mask = MANDATORY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyDescriptorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ChannelPackage.Literals.PROPERTY_DESCRIPTOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefaultValue(String newDefaultValue) {
		String oldDefaultValue = defaultValue;
		defaultValue = newDefaultValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.PROPERTY_DESCRIPTOR__DEFAULT_VALUE, oldDefaultValue, defaultValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.PROPERTY_DESCRIPTOR__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPattern() {
		return pattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPattern(String newPattern) {
		String oldPattern = pattern;
		pattern = newPattern;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.PROPERTY_DESCRIPTOR__PATTERN, oldPattern, pattern));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(String newType) {
		String oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.PROPERTY_DESCRIPTOR__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMandatory() {
		return mandatory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMandatory(boolean newMandatory) {
		boolean oldMandatory = mandatory;
		mandatory = newMandatory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.PROPERTY_DESCRIPTOR__MANDATORY, oldMandatory, mandatory));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.PROPERTY_DESCRIPTOR__DISPLAY_NAME, oldDisplayName, displayName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGvToggle() {
		return gvToggle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGvToggle(boolean newGvToggle) {
		boolean oldGvToggle = gvToggle;
		gvToggle = newGvToggle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.PROPERTY_DESCRIPTOR__GV_TOGGLE, oldGvToggle, gvToggle));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ChannelPackage.PROPERTY_DESCRIPTOR__DEFAULT_VALUE:
				return getDefaultValue();
			case ChannelPackage.PROPERTY_DESCRIPTOR__NAME:
				return getName();
			case ChannelPackage.PROPERTY_DESCRIPTOR__PATTERN:
				return getPattern();
			case ChannelPackage.PROPERTY_DESCRIPTOR__TYPE:
				return getType();
			case ChannelPackage.PROPERTY_DESCRIPTOR__MANDATORY:
				return isMandatory();
			case ChannelPackage.PROPERTY_DESCRIPTOR__DISPLAY_NAME:
				return getDisplayName();
			case ChannelPackage.PROPERTY_DESCRIPTOR__GV_TOGGLE:
				return isGvToggle();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ChannelPackage.PROPERTY_DESCRIPTOR__DEFAULT_VALUE:
				setDefaultValue((String)newValue);
				return;
			case ChannelPackage.PROPERTY_DESCRIPTOR__NAME:
				setName((String)newValue);
				return;
			case ChannelPackage.PROPERTY_DESCRIPTOR__PATTERN:
				setPattern((String)newValue);
				return;
			case ChannelPackage.PROPERTY_DESCRIPTOR__TYPE:
				setType((String)newValue);
				return;
			case ChannelPackage.PROPERTY_DESCRIPTOR__MANDATORY:
				setMandatory((Boolean)newValue);
				return;
			case ChannelPackage.PROPERTY_DESCRIPTOR__DISPLAY_NAME:
				setDisplayName((String)newValue);
				return;
			case ChannelPackage.PROPERTY_DESCRIPTOR__GV_TOGGLE:
				setGvToggle((Boolean)newValue);
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
			case ChannelPackage.PROPERTY_DESCRIPTOR__DEFAULT_VALUE:
				setDefaultValue(DEFAULT_VALUE_EDEFAULT);
				return;
			case ChannelPackage.PROPERTY_DESCRIPTOR__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ChannelPackage.PROPERTY_DESCRIPTOR__PATTERN:
				setPattern(PATTERN_EDEFAULT);
				return;
			case ChannelPackage.PROPERTY_DESCRIPTOR__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case ChannelPackage.PROPERTY_DESCRIPTOR__MANDATORY:
				setMandatory(MANDATORY_EDEFAULT);
				return;
			case ChannelPackage.PROPERTY_DESCRIPTOR__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
				return;
			case ChannelPackage.PROPERTY_DESCRIPTOR__GV_TOGGLE:
				setGvToggle(GV_TOGGLE_EDEFAULT);
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
			case ChannelPackage.PROPERTY_DESCRIPTOR__DEFAULT_VALUE:
				return DEFAULT_VALUE_EDEFAULT == null ? defaultValue != null : !DEFAULT_VALUE_EDEFAULT.equals(defaultValue);
			case ChannelPackage.PROPERTY_DESCRIPTOR__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ChannelPackage.PROPERTY_DESCRIPTOR__PATTERN:
				return PATTERN_EDEFAULT == null ? pattern != null : !PATTERN_EDEFAULT.equals(pattern);
			case ChannelPackage.PROPERTY_DESCRIPTOR__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case ChannelPackage.PROPERTY_DESCRIPTOR__MANDATORY:
				return mandatory != MANDATORY_EDEFAULT;
			case ChannelPackage.PROPERTY_DESCRIPTOR__DISPLAY_NAME:
				return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
			case ChannelPackage.PROPERTY_DESCRIPTOR__GV_TOGGLE:
				return gvToggle != GV_TOGGLE_EDEFAULT;
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
		result.append(" (defaultValue: ");
		result.append(defaultValue);
		result.append(", name: ");
		result.append(name);
		result.append(", pattern: ");
		result.append(pattern);
		result.append(", type: ");
		result.append(type);
		result.append(", mandatory: ");
		result.append(mandatory);
		result.append(", displayName: ");
		result.append(displayName);
		result.append(", gvToggle: ");
		result.append(gvToggle);
		result.append(')');
		return result.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public boolean isMask() {
		return mask;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void setMask(boolean value) {
		mask = value;
	}

} //PropertyDescriptorImpl
