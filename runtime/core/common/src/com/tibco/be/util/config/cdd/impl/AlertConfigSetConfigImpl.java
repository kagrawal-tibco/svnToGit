/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.util.config.cdd.AlertConfigConfig;
import com.tibco.be.util.config.cdd.AlertConfigSetConfig;
import com.tibco.be.util.config.cdd.CddPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Alert Config Set Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.AlertConfigSetConfigImpl#getAlertConfig <em>Alert Config</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AlertConfigSetConfigImpl extends EObjectImpl implements AlertConfigSetConfig {
	/**
	 * The cached value of the '{@link #getAlertConfig() <em>Alert Config</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlertConfig()
	 * @generated
	 * @ordered
	 */
	protected EList<AlertConfigConfig> alertConfig;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AlertConfigSetConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getAlertConfigSetConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AlertConfigConfig> getAlertConfig() {
		if (alertConfig == null) {
			alertConfig = new EObjectContainmentEList<AlertConfigConfig>(AlertConfigConfig.class, this, CddPackage.ALERT_CONFIG_SET_CONFIG__ALERT_CONFIG);
		}
		return alertConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.ALERT_CONFIG_SET_CONFIG__ALERT_CONFIG:
				return ((InternalEList<?>)getAlertConfig()).basicRemove(otherEnd, msgs);
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
			case CddPackage.ALERT_CONFIG_SET_CONFIG__ALERT_CONFIG:
				return getAlertConfig();
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
			case CddPackage.ALERT_CONFIG_SET_CONFIG__ALERT_CONFIG:
				getAlertConfig().clear();
				getAlertConfig().addAll((Collection<? extends AlertConfigConfig>)newValue);
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
			case CddPackage.ALERT_CONFIG_SET_CONFIG__ALERT_CONFIG:
				getAlertConfig().clear();
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
			case CddPackage.ALERT_CONFIG_SET_CONFIG__ALERT_CONFIG:
				return alertConfig != null && !alertConfig.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //AlertConfigSetConfigImpl
