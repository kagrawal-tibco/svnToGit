/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateMap;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Alert Indicator State Map</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.AlertIndicatorStateMapImpl#getFieldValue <em>Field Value</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.AlertIndicatorStateMapImpl#getIndicatorState <em>Indicator State</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AlertIndicatorStateMapImpl extends BEViewsElementImpl implements AlertIndicatorStateMap {
	/**
	 * The default value of the '{@link #getFieldValue() <em>Field Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFieldValue()
	 * @generated
	 * @ordered
	 */
	protected static final String FIELD_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFieldValue() <em>Field Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFieldValue()
	 * @generated
	 * @ordered
	 */
	protected String fieldValue = FIELD_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getIndicatorState() <em>Indicator State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndicatorState()
	 * @generated
	 * @ordered
	 */
	protected static final String INDICATOR_STATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIndicatorState() <em>Indicator State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndicatorState()
	 * @generated
	 * @ordered
	 */
	protected String indicatorState = INDICATOR_STATE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AlertIndicatorStateMapImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getAlertIndicatorStateMap();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFieldValue() {
		return fieldValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFieldValue(String newFieldValue) {
		String oldFieldValue = fieldValue;
		fieldValue = newFieldValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.ALERT_INDICATOR_STATE_MAP__FIELD_VALUE, oldFieldValue, fieldValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIndicatorState() {
		return indicatorState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIndicatorState(String newIndicatorState) {
		String oldIndicatorState = indicatorState;
		indicatorState = newIndicatorState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.ALERT_INDICATOR_STATE_MAP__INDICATOR_STATE, oldIndicatorState, indicatorState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.ALERT_INDICATOR_STATE_MAP__FIELD_VALUE:
				return getFieldValue();
			case BEViewsConfigurationPackage.ALERT_INDICATOR_STATE_MAP__INDICATOR_STATE:
				return getIndicatorState();
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
			case BEViewsConfigurationPackage.ALERT_INDICATOR_STATE_MAP__FIELD_VALUE:
				setFieldValue((String)newValue);
				return;
			case BEViewsConfigurationPackage.ALERT_INDICATOR_STATE_MAP__INDICATOR_STATE:
				setIndicatorState((String)newValue);
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
			case BEViewsConfigurationPackage.ALERT_INDICATOR_STATE_MAP__FIELD_VALUE:
				setFieldValue(FIELD_VALUE_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.ALERT_INDICATOR_STATE_MAP__INDICATOR_STATE:
				setIndicatorState(INDICATOR_STATE_EDEFAULT);
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
			case BEViewsConfigurationPackage.ALERT_INDICATOR_STATE_MAP__FIELD_VALUE:
				return FIELD_VALUE_EDEFAULT == null ? fieldValue != null : !FIELD_VALUE_EDEFAULT.equals(fieldValue);
			case BEViewsConfigurationPackage.ALERT_INDICATOR_STATE_MAP__INDICATOR_STATE:
				return INDICATOR_STATE_EDEFAULT == null ? indicatorState != null : !INDICATOR_STATE_EDEFAULT.equals(indicatorState);
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
		result.append(" (fieldValue: ");
		result.append(fieldValue);
		result.append(", indicatorState: ");
		result.append(indicatorState);
		result.append(')');
		return result.toString();
	}

} //AlertIndicatorStateMapImpl
