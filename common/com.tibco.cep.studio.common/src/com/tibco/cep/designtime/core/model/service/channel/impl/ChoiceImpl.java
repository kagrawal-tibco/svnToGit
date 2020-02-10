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

import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.Choice;
import com.tibco.cep.designtime.core.model.service.channel.ExtendedConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Choice</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.ChoiceImpl#getDisplayedValue <em>Displayed Value</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.ChoiceImpl#getValue <em>Value</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.ChoiceImpl#getExtendedConfiguration <em>Extended Configuration</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ChoiceImpl extends EObjectImpl implements Choice {
	/**
	 * The default value of the '{@link #getDisplayedValue() <em>Displayed Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayedValue()
	 * @generated
	 * @ordered
	 */
	protected static final String DISPLAYED_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDisplayedValue() <em>Displayed Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayedValue()
	 * @generated
	 * @ordered
	 */
	protected String displayedValue = DISPLAYED_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getExtendedConfiguration() <em>Extended Configuration</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtendedConfiguration()
	 * @generated
	 * @ordered
	 */
	protected EList<ExtendedConfiguration> extendedConfiguration;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChoiceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ChannelPackage.Literals.CHOICE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDisplayedValue() {
		return displayedValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDisplayedValue(String newDisplayedValue) {
		String oldDisplayedValue = displayedValue;
		displayedValue = newDisplayedValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.CHOICE__DISPLAYED_VALUE, oldDisplayedValue, displayedValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(String newValue) {
		String oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.CHOICE__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ExtendedConfiguration> getExtendedConfiguration() {
		if (extendedConfiguration == null) {
			extendedConfiguration = new EObjectContainmentEList<ExtendedConfiguration>(ExtendedConfiguration.class, this, ChannelPackage.CHOICE__EXTENDED_CONFIGURATION);
		}
		return extendedConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ChannelPackage.CHOICE__EXTENDED_CONFIGURATION:
				return ((InternalEList<?>)getExtendedConfiguration()).basicRemove(otherEnd, msgs);
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
			case ChannelPackage.CHOICE__DISPLAYED_VALUE:
				return getDisplayedValue();
			case ChannelPackage.CHOICE__VALUE:
				return getValue();
			case ChannelPackage.CHOICE__EXTENDED_CONFIGURATION:
				return getExtendedConfiguration();
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
			case ChannelPackage.CHOICE__DISPLAYED_VALUE:
				setDisplayedValue((String)newValue);
				return;
			case ChannelPackage.CHOICE__VALUE:
				setValue((String)newValue);
				return;
			case ChannelPackage.CHOICE__EXTENDED_CONFIGURATION:
				getExtendedConfiguration().clear();
				getExtendedConfiguration().addAll((Collection<? extends ExtendedConfiguration>)newValue);
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
			case ChannelPackage.CHOICE__DISPLAYED_VALUE:
				setDisplayedValue(DISPLAYED_VALUE_EDEFAULT);
				return;
			case ChannelPackage.CHOICE__VALUE:
				setValue(VALUE_EDEFAULT);
				return;
			case ChannelPackage.CHOICE__EXTENDED_CONFIGURATION:
				getExtendedConfiguration().clear();
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
			case ChannelPackage.CHOICE__DISPLAYED_VALUE:
				return DISPLAYED_VALUE_EDEFAULT == null ? displayedValue != null : !DISPLAYED_VALUE_EDEFAULT.equals(displayedValue);
			case ChannelPackage.CHOICE__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
			case ChannelPackage.CHOICE__EXTENDED_CONFIGURATION:
				return extendedConfiguration != null && !extendedConfiguration.isEmpty();
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
		result.append(" (displayedValue: ");
		result.append(displayedValue);
		result.append(", value: ");
		result.append(value);
		result.append(')');
		return result.toString();
	}

} //ChoiceImpl
