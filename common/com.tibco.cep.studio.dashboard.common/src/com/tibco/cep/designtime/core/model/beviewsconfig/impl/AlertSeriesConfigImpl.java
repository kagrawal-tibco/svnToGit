/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateEnumeration;
import com.tibco.cep.designtime.core.model.beviewsconfig.AlertSeriesConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Alert Series Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.AlertSeriesConfigImpl#getIndicatorStateEnumeration <em>Indicator State Enumeration</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AlertSeriesConfigImpl extends ClassifierSeriesConfigImpl implements AlertSeriesConfig {
	/**
	 * The cached value of the '{@link #getIndicatorStateEnumeration() <em>Indicator State Enumeration</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndicatorStateEnumeration()
	 * @generated
	 * @ordered
	 */
	protected EList<AlertIndicatorStateEnumeration> indicatorStateEnumeration;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AlertSeriesConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getAlertSeriesConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AlertIndicatorStateEnumeration> getIndicatorStateEnumeration() {
		if (indicatorStateEnumeration == null) {
			indicatorStateEnumeration = new EObjectContainmentEList<AlertIndicatorStateEnumeration>(AlertIndicatorStateEnumeration.class, this, BEViewsConfigurationPackage.ALERT_SERIES_CONFIG__INDICATOR_STATE_ENUMERATION);
		}
		return indicatorStateEnumeration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.ALERT_SERIES_CONFIG__INDICATOR_STATE_ENUMERATION:
				return ((InternalEList<?>)getIndicatorStateEnumeration()).basicRemove(otherEnd, msgs);
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
			case BEViewsConfigurationPackage.ALERT_SERIES_CONFIG__INDICATOR_STATE_ENUMERATION:
				return getIndicatorStateEnumeration();
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
			case BEViewsConfigurationPackage.ALERT_SERIES_CONFIG__INDICATOR_STATE_ENUMERATION:
				getIndicatorStateEnumeration().clear();
				getIndicatorStateEnumeration().addAll((Collection<? extends AlertIndicatorStateEnumeration>)newValue);
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
			case BEViewsConfigurationPackage.ALERT_SERIES_CONFIG__INDICATOR_STATE_ENUMERATION:
				getIndicatorStateEnumeration().clear();
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
			case BEViewsConfigurationPackage.ALERT_SERIES_CONFIG__INDICATOR_STATE_ENUMERATION:
				return indicatorStateEnumeration != null && !indicatorStateEnumeration.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //AlertSeriesConfigImpl
