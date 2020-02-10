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

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.RangeAlert;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Range Alert</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.RangeAlertImpl#getLowerValue <em>Lower Value</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.RangeAlertImpl#getUpperValue <em>Upper Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RangeAlertImpl extends AlertImpl implements RangeAlert {
	/**
	 * The default value of the '{@link #getLowerValue() <em>Lower Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLowerValue()
	 * @generated
	 * @ordered
	 */
	protected static final double LOWER_VALUE_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getLowerValue() <em>Lower Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLowerValue()
	 * @generated
	 * @ordered
	 */
	protected double lowerValue = LOWER_VALUE_EDEFAULT;

	/**
	 * This is true if the Lower Value attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean lowerValueESet;

	/**
	 * The default value of the '{@link #getUpperValue() <em>Upper Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpperValue()
	 * @generated
	 * @ordered
	 */
	protected static final double UPPER_VALUE_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getUpperValue() <em>Upper Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpperValue()
	 * @generated
	 * @ordered
	 */
	protected double upperValue = UPPER_VALUE_EDEFAULT;

	/**
	 * This is true if the Upper Value attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean upperValueESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RangeAlertImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getRangeAlert();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getLowerValue() {
		return lowerValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLowerValue(double newLowerValue) {
		double oldLowerValue = lowerValue;
		lowerValue = newLowerValue;
		boolean oldLowerValueESet = lowerValueESet;
		lowerValueESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.RANGE_ALERT__LOWER_VALUE, oldLowerValue, lowerValue, !oldLowerValueESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetLowerValue() {
		double oldLowerValue = lowerValue;
		boolean oldLowerValueESet = lowerValueESet;
		lowerValue = LOWER_VALUE_EDEFAULT;
		lowerValueESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.RANGE_ALERT__LOWER_VALUE, oldLowerValue, LOWER_VALUE_EDEFAULT, oldLowerValueESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetLowerValue() {
		return lowerValueESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getUpperValue() {
		return upperValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUpperValue(double newUpperValue) {
		double oldUpperValue = upperValue;
		upperValue = newUpperValue;
		boolean oldUpperValueESet = upperValueESet;
		upperValueESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.RANGE_ALERT__UPPER_VALUE, oldUpperValue, upperValue, !oldUpperValueESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetUpperValue() {
		double oldUpperValue = upperValue;
		boolean oldUpperValueESet = upperValueESet;
		upperValue = UPPER_VALUE_EDEFAULT;
		upperValueESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.RANGE_ALERT__UPPER_VALUE, oldUpperValue, UPPER_VALUE_EDEFAULT, oldUpperValueESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetUpperValue() {
		return upperValueESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.RANGE_ALERT__LOWER_VALUE:
				return getLowerValue();
			case BEViewsConfigurationPackage.RANGE_ALERT__UPPER_VALUE:
				return getUpperValue();
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
			case BEViewsConfigurationPackage.RANGE_ALERT__LOWER_VALUE:
				setLowerValue((Double)newValue);
				return;
			case BEViewsConfigurationPackage.RANGE_ALERT__UPPER_VALUE:
				setUpperValue((Double)newValue);
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
			case BEViewsConfigurationPackage.RANGE_ALERT__LOWER_VALUE:
				unsetLowerValue();
				return;
			case BEViewsConfigurationPackage.RANGE_ALERT__UPPER_VALUE:
				unsetUpperValue();
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
			case BEViewsConfigurationPackage.RANGE_ALERT__LOWER_VALUE:
				return isSetLowerValue();
			case BEViewsConfigurationPackage.RANGE_ALERT__UPPER_VALUE:
				return isSetUpperValue();
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
		result.append(" (lowerValue: ");
		if (lowerValueESet) result.append(lowerValue); else result.append("<unset>");
		result.append(", upperValue: ");
		if (upperValueESet) result.append(upperValue); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //RangeAlertImpl
