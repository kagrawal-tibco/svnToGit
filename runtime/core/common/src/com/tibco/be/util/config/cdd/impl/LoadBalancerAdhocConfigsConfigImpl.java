/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.LoadBalancerAdhocConfigConfig;
import com.tibco.be.util.config.cdd.LoadBalancerAdhocConfigsConfig;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Load Balancer Adhoc Configs Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LoadBalancerAdhocConfigsConfigImpl#getLoadBalancerAdhocConfigs <em>Load Balancer Adhoc Configs</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LoadBalancerAdhocConfigsConfigImpl extends EObjectImpl implements LoadBalancerAdhocConfigsConfig {
	/**
	 * The cached value of the '{@link #getLoadBalancerAdhocConfigs() <em>Load Balancer Adhoc Configs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLoadBalancerAdhocConfigs()
	 * @generated
	 * @ordered
	 */
	protected EList<LoadBalancerAdhocConfigConfig> loadBalancerAdhocConfigs;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LoadBalancerAdhocConfigsConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getLoadBalancerAdhocConfigsConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<LoadBalancerAdhocConfigConfig> getLoadBalancerAdhocConfigs() {
		if (loadBalancerAdhocConfigs == null) {
			loadBalancerAdhocConfigs = new EObjectContainmentEList<LoadBalancerAdhocConfigConfig>(LoadBalancerAdhocConfigConfig.class, this, CddPackage.LOAD_BALANCER_ADHOC_CONFIGS_CONFIG__LOAD_BALANCER_ADHOC_CONFIGS);
		}
		return loadBalancerAdhocConfigs;
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
			case CddPackage.LOAD_BALANCER_ADHOC_CONFIGS_CONFIG__LOAD_BALANCER_ADHOC_CONFIGS:
				return ((InternalEList<?>)getLoadBalancerAdhocConfigs()).basicRemove(otherEnd, msgs);
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
			case CddPackage.LOAD_BALANCER_ADHOC_CONFIGS_CONFIG__LOAD_BALANCER_ADHOC_CONFIGS:
				return getLoadBalancerAdhocConfigs();
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
			case CddPackage.LOAD_BALANCER_ADHOC_CONFIGS_CONFIG__LOAD_BALANCER_ADHOC_CONFIGS:
				getLoadBalancerAdhocConfigs().clear();
				getLoadBalancerAdhocConfigs().addAll((Collection<? extends LoadBalancerAdhocConfigConfig>)newValue);
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
			case CddPackage.LOAD_BALANCER_ADHOC_CONFIGS_CONFIG__LOAD_BALANCER_ADHOC_CONFIGS:
				getLoadBalancerAdhocConfigs().clear();
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
			case CddPackage.LOAD_BALANCER_ADHOC_CONFIGS_CONFIG__LOAD_BALANCER_ADHOC_CONFIGS:
				return loadBalancerAdhocConfigs != null && !loadBalancerAdhocConfigs.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //LoadBalancerAdhocConfigsConfigImpl
