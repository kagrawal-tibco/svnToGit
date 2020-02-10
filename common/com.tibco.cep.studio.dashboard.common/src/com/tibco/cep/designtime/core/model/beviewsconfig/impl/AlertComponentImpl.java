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

import com.tibco.cep.designtime.core.model.beviewsconfig.AlertComponent;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Alert Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.AlertComponentImpl#getThreshold <em>Threshold</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AlertComponentImpl extends ClassifierComponentImpl implements AlertComponent {
	/**
	 * The default value of the '{@link #getThreshold() <em>Threshold</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThreshold()
	 * @generated
	 * @ordered
	 */
	protected static final int THRESHOLD_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getThreshold() <em>Threshold</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThreshold()
	 * @generated
	 * @ordered
	 */
	protected int threshold = THRESHOLD_EDEFAULT;

	/**
	 * This is true if the Threshold attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean thresholdESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AlertComponentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getAlertComponent();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getThreshold() {
		return threshold;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setThreshold(int newThreshold) {
		int oldThreshold = threshold;
		threshold = newThreshold;
		boolean oldThresholdESet = thresholdESet;
		thresholdESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.ALERT_COMPONENT__THRESHOLD, oldThreshold, threshold, !oldThresholdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetThreshold() {
		int oldThreshold = threshold;
		boolean oldThresholdESet = thresholdESet;
		threshold = THRESHOLD_EDEFAULT;
		thresholdESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.ALERT_COMPONENT__THRESHOLD, oldThreshold, THRESHOLD_EDEFAULT, oldThresholdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetThreshold() {
		return thresholdESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.ALERT_COMPONENT__THRESHOLD:
				return getThreshold();
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
			case BEViewsConfigurationPackage.ALERT_COMPONENT__THRESHOLD:
				setThreshold((Integer)newValue);
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
			case BEViewsConfigurationPackage.ALERT_COMPONENT__THRESHOLD:
				unsetThreshold();
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
			case BEViewsConfigurationPackage.ALERT_COMPONENT__THRESHOLD:
				return isSetThreshold();
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
		result.append(" (threshold: ");
		if (thresholdESet) result.append(threshold); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //AlertComponentImpl
