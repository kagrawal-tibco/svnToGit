/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.LogConfigConfig;
import com.tibco.be.util.config.cdd.LogConfigsConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Log Configs Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LogConfigsConfigImpl#getLogConfig <em>Log Config</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LogConfigsConfigImpl extends EObjectImpl implements LogConfigsConfig {
	/**
	 * The cached value of the '{@link #getLogConfig() <em>Log Config</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLogConfig()
	 * @generated
	 * @ordered
	 */
	protected EList<LogConfigConfig> logConfig;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LogConfigsConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getLogConfigsConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<LogConfigConfig> getLogConfig() {
		if (logConfig == null) {
			logConfig = new EObjectContainmentEList<LogConfigConfig>(LogConfigConfig.class, this, CddPackage.LOG_CONFIGS_CONFIG__LOG_CONFIG);
		}
		return logConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map<Object, Object> toProperties() {
		return new java.util.Properties();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.LOG_CONFIGS_CONFIG__LOG_CONFIG:
				return ((InternalEList<?>)getLogConfig()).basicRemove(otherEnd, msgs);
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
			case CddPackage.LOG_CONFIGS_CONFIG__LOG_CONFIG:
				return getLogConfig();
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
			case CddPackage.LOG_CONFIGS_CONFIG__LOG_CONFIG:
				getLogConfig().clear();
				getLogConfig().addAll((Collection<? extends LogConfigConfig>)newValue);
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
			case CddPackage.LOG_CONFIGS_CONFIG__LOG_CONFIG:
				getLogConfig().clear();
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
			case CddPackage.LOG_CONFIGS_CONFIG__LOG_CONFIG:
				return logConfig != null && !logConfig.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //LogConfigsConfigImpl
