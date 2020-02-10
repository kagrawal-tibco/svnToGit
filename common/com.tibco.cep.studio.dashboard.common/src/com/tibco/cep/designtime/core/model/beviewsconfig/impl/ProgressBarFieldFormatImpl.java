/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.ProgressBarFieldFormat;
import com.tibco.cep.designtime.core.model.beviewsconfig.ValueOption;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Progress Bar Field Format</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ProgressBarFieldFormatImpl#getMinValue <em>Min Value</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ProgressBarFieldFormatImpl#getMaxValue <em>Max Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProgressBarFieldFormatImpl extends IndicatorFieldFormatImpl implements ProgressBarFieldFormat {
	/**
	 * The cached value of the '{@link #getMinValue() <em>Min Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinValue()
	 * @generated
	 * @ordered
	 */
	protected ValueOption minValue;

	/**
	 * The cached value of the '{@link #getMaxValue() <em>Max Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxValue()
	 * @generated
	 * @ordered
	 */
	protected ValueOption maxValue;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProgressBarFieldFormatImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getProgressBarFieldFormat();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueOption getMaxValue() {
		return maxValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMaxValue(ValueOption newMaxValue, NotificationChain msgs) {
		ValueOption oldMaxValue = maxValue;
		maxValue = newMaxValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.PROGRESS_BAR_FIELD_FORMAT__MAX_VALUE, oldMaxValue, newMaxValue);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxValue(ValueOption newMaxValue) {
		if (newMaxValue != maxValue) {
			NotificationChain msgs = null;
			if (maxValue != null)
				msgs = ((InternalEObject)maxValue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.PROGRESS_BAR_FIELD_FORMAT__MAX_VALUE, null, msgs);
			if (newMaxValue != null)
				msgs = ((InternalEObject)newMaxValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.PROGRESS_BAR_FIELD_FORMAT__MAX_VALUE, null, msgs);
			msgs = basicSetMaxValue(newMaxValue, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.PROGRESS_BAR_FIELD_FORMAT__MAX_VALUE, newMaxValue, newMaxValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.PROGRESS_BAR_FIELD_FORMAT__MIN_VALUE:
				return basicSetMinValue(null, msgs);
			case BEViewsConfigurationPackage.PROGRESS_BAR_FIELD_FORMAT__MAX_VALUE:
				return basicSetMaxValue(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueOption getMinValue() {
		return minValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMinValue(ValueOption newMinValue, NotificationChain msgs) {
		ValueOption oldMinValue = minValue;
		minValue = newMinValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.PROGRESS_BAR_FIELD_FORMAT__MIN_VALUE, oldMinValue, newMinValue);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinValue(ValueOption newMinValue) {
		if (newMinValue != minValue) {
			NotificationChain msgs = null;
			if (minValue != null)
				msgs = ((InternalEObject)minValue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.PROGRESS_BAR_FIELD_FORMAT__MIN_VALUE, null, msgs);
			if (newMinValue != null)
				msgs = ((InternalEObject)newMinValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.PROGRESS_BAR_FIELD_FORMAT__MIN_VALUE, null, msgs);
			msgs = basicSetMinValue(newMinValue, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.PROGRESS_BAR_FIELD_FORMAT__MIN_VALUE, newMinValue, newMinValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.PROGRESS_BAR_FIELD_FORMAT__MIN_VALUE:
				return getMinValue();
			case BEViewsConfigurationPackage.PROGRESS_BAR_FIELD_FORMAT__MAX_VALUE:
				return getMaxValue();
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
			case BEViewsConfigurationPackage.PROGRESS_BAR_FIELD_FORMAT__MIN_VALUE:
				setMinValue((ValueOption)newValue);
				return;
			case BEViewsConfigurationPackage.PROGRESS_BAR_FIELD_FORMAT__MAX_VALUE:
				setMaxValue((ValueOption)newValue);
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
			case BEViewsConfigurationPackage.PROGRESS_BAR_FIELD_FORMAT__MIN_VALUE:
				setMinValue((ValueOption)null);
				return;
			case BEViewsConfigurationPackage.PROGRESS_BAR_FIELD_FORMAT__MAX_VALUE:
				setMaxValue((ValueOption)null);
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
			case BEViewsConfigurationPackage.PROGRESS_BAR_FIELD_FORMAT__MIN_VALUE:
				return minValue != null;
			case BEViewsConfigurationPackage.PROGRESS_BAR_FIELD_FORMAT__MAX_VALUE:
				return maxValue != null;
		}
		return super.eIsSet(featureID);
	}

} //ProgressBarFieldFormatImpl
