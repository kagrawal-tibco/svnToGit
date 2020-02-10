/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig;
import com.tibco.be.util.config.cdd.LoadBalancerPairConfigsConfig;

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
 * An implementation of the model object '<em><b>Load Balancer Pair Configs Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LoadBalancerPairConfigsConfigImpl#getLoadBalancerPairConfigs <em>Load Balancer Pair Configs</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LoadBalancerPairConfigsConfigImpl extends EObjectImpl implements LoadBalancerPairConfigsConfig {
	/**
	 * The cached value of the '{@link #getLoadBalancerPairConfigs() <em>Load Balancer Pair Configs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLoadBalancerPairConfigs()
	 * @generated
	 * @ordered
	 */
	protected EList<LoadBalancerPairConfigConfig> loadBalancerPairConfigs;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LoadBalancerPairConfigsConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getLoadBalancerPairConfigsConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<LoadBalancerPairConfigConfig> getLoadBalancerPairConfigs() {
		if (loadBalancerPairConfigs == null) {
			loadBalancerPairConfigs = new EObjectContainmentEList<LoadBalancerPairConfigConfig>(LoadBalancerPairConfigConfig.class, this, CddPackage.LOAD_BALANCER_PAIR_CONFIGS_CONFIG__LOAD_BALANCER_PAIR_CONFIGS);
		}
		return loadBalancerPairConfigs;
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
			case CddPackage.LOAD_BALANCER_PAIR_CONFIGS_CONFIG__LOAD_BALANCER_PAIR_CONFIGS:
				return ((InternalEList<?>)getLoadBalancerPairConfigs()).basicRemove(otherEnd, msgs);
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
			case CddPackage.LOAD_BALANCER_PAIR_CONFIGS_CONFIG__LOAD_BALANCER_PAIR_CONFIGS:
				return getLoadBalancerPairConfigs();
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
			case CddPackage.LOAD_BALANCER_PAIR_CONFIGS_CONFIG__LOAD_BALANCER_PAIR_CONFIGS:
				getLoadBalancerPairConfigs().clear();
				getLoadBalancerPairConfigs().addAll((Collection<? extends LoadBalancerPairConfigConfig>)newValue);
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
			case CddPackage.LOAD_BALANCER_PAIR_CONFIGS_CONFIG__LOAD_BALANCER_PAIR_CONFIGS:
				getLoadBalancerPairConfigs().clear();
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
			case CddPackage.LOAD_BALANCER_PAIR_CONFIGS_CONFIG__LOAD_BALANCER_PAIR_CONFIGS:
				return loadBalancerPairConfigs != null && !loadBalancerPairConfigs.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //LoadBalancerPairConfigsConfigImpl
