/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.LoadBalancerAdhocConfigsConfig;
import com.tibco.be.util.config.cdd.LoadBalancerConfigsConfig;
import com.tibco.be.util.config.cdd.LoadBalancerPairConfigsConfig;

import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Load Balancer Configs Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LoadBalancerConfigsConfigImpl#getLoadBalancerPairConfigs <em>Load Balancer Pair Configs</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LoadBalancerConfigsConfigImpl#getLoadBalancerAdhocConfigs <em>Load Balancer Adhoc Configs</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LoadBalancerConfigsConfigImpl extends EObjectImpl implements LoadBalancerConfigsConfig {
	/**
	 * The cached value of the '{@link #getLoadBalancerPairConfigs() <em>Load Balancer Pair Configs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLoadBalancerPairConfigs()
	 * @generated
	 * @ordered
	 */
	protected LoadBalancerPairConfigsConfig loadBalancerPairConfigs;

	/**
	 * The cached value of the '{@link #getLoadBalancerAdhocConfigs() <em>Load Balancer Adhoc Configs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLoadBalancerAdhocConfigs()
	 * @generated
	 * @ordered
	 */
	protected LoadBalancerAdhocConfigsConfig loadBalancerAdhocConfigs;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LoadBalancerConfigsConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getLoadBalancerConfigsConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LoadBalancerPairConfigsConfig getLoadBalancerPairConfigs() {
		return loadBalancerPairConfigs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLoadBalancerPairConfigs(LoadBalancerPairConfigsConfig newLoadBalancerPairConfigs, NotificationChain msgs) {
		LoadBalancerPairConfigsConfig oldLoadBalancerPairConfigs = loadBalancerPairConfigs;
		loadBalancerPairConfigs = newLoadBalancerPairConfigs;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LOAD_BALANCER_CONFIGS_CONFIG__LOAD_BALANCER_PAIR_CONFIGS, oldLoadBalancerPairConfigs, newLoadBalancerPairConfigs);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLoadBalancerPairConfigs(LoadBalancerPairConfigsConfig newLoadBalancerPairConfigs) {
		if (newLoadBalancerPairConfigs != loadBalancerPairConfigs) {
			NotificationChain msgs = null;
			if (loadBalancerPairConfigs != null)
				msgs = ((InternalEObject)loadBalancerPairConfigs).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOAD_BALANCER_CONFIGS_CONFIG__LOAD_BALANCER_PAIR_CONFIGS, null, msgs);
			if (newLoadBalancerPairConfigs != null)
				msgs = ((InternalEObject)newLoadBalancerPairConfigs).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOAD_BALANCER_CONFIGS_CONFIG__LOAD_BALANCER_PAIR_CONFIGS, null, msgs);
			msgs = basicSetLoadBalancerPairConfigs(newLoadBalancerPairConfigs, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LOAD_BALANCER_CONFIGS_CONFIG__LOAD_BALANCER_PAIR_CONFIGS, newLoadBalancerPairConfigs, newLoadBalancerPairConfigs));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LoadBalancerAdhocConfigsConfig getLoadBalancerAdhocConfigs() {
		return loadBalancerAdhocConfigs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLoadBalancerAdhocConfigs(LoadBalancerAdhocConfigsConfig newLoadBalancerAdhocConfigs, NotificationChain msgs) {
		LoadBalancerAdhocConfigsConfig oldLoadBalancerAdhocConfigs = loadBalancerAdhocConfigs;
		loadBalancerAdhocConfigs = newLoadBalancerAdhocConfigs;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LOAD_BALANCER_CONFIGS_CONFIG__LOAD_BALANCER_ADHOC_CONFIGS, oldLoadBalancerAdhocConfigs, newLoadBalancerAdhocConfigs);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLoadBalancerAdhocConfigs(LoadBalancerAdhocConfigsConfig newLoadBalancerAdhocConfigs) {
		if (newLoadBalancerAdhocConfigs != loadBalancerAdhocConfigs) {
			NotificationChain msgs = null;
			if (loadBalancerAdhocConfigs != null)
				msgs = ((InternalEObject)loadBalancerAdhocConfigs).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOAD_BALANCER_CONFIGS_CONFIG__LOAD_BALANCER_ADHOC_CONFIGS, null, msgs);
			if (newLoadBalancerAdhocConfigs != null)
				msgs = ((InternalEObject)newLoadBalancerAdhocConfigs).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOAD_BALANCER_CONFIGS_CONFIG__LOAD_BALANCER_ADHOC_CONFIGS, null, msgs);
			msgs = basicSetLoadBalancerAdhocConfigs(newLoadBalancerAdhocConfigs, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LOAD_BALANCER_CONFIGS_CONFIG__LOAD_BALANCER_ADHOC_CONFIGS, newLoadBalancerAdhocConfigs, newLoadBalancerAdhocConfigs));
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
			case CddPackage.LOAD_BALANCER_CONFIGS_CONFIG__LOAD_BALANCER_PAIR_CONFIGS:
				return basicSetLoadBalancerPairConfigs(null, msgs);
			case CddPackage.LOAD_BALANCER_CONFIGS_CONFIG__LOAD_BALANCER_ADHOC_CONFIGS:
				return basicSetLoadBalancerAdhocConfigs(null, msgs);
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
			case CddPackage.LOAD_BALANCER_CONFIGS_CONFIG__LOAD_BALANCER_PAIR_CONFIGS:
				return getLoadBalancerPairConfigs();
			case CddPackage.LOAD_BALANCER_CONFIGS_CONFIG__LOAD_BALANCER_ADHOC_CONFIGS:
				return getLoadBalancerAdhocConfigs();
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
			case CddPackage.LOAD_BALANCER_CONFIGS_CONFIG__LOAD_BALANCER_PAIR_CONFIGS:
				setLoadBalancerPairConfigs((LoadBalancerPairConfigsConfig)newValue);
				return;
			case CddPackage.LOAD_BALANCER_CONFIGS_CONFIG__LOAD_BALANCER_ADHOC_CONFIGS:
				setLoadBalancerAdhocConfigs((LoadBalancerAdhocConfigsConfig)newValue);
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
			case CddPackage.LOAD_BALANCER_CONFIGS_CONFIG__LOAD_BALANCER_PAIR_CONFIGS:
				setLoadBalancerPairConfigs((LoadBalancerPairConfigsConfig)null);
				return;
			case CddPackage.LOAD_BALANCER_CONFIGS_CONFIG__LOAD_BALANCER_ADHOC_CONFIGS:
				setLoadBalancerAdhocConfigs((LoadBalancerAdhocConfigsConfig)null);
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
			case CddPackage.LOAD_BALANCER_CONFIGS_CONFIG__LOAD_BALANCER_PAIR_CONFIGS:
				return loadBalancerPairConfigs != null;
			case CddPackage.LOAD_BALANCER_CONFIGS_CONFIG__LOAD_BALANCER_ADHOC_CONFIGS:
				return loadBalancerAdhocConfigs != null;
		}
		return super.eIsSet(featureID);
	}

} //LoadBalancerConfigsConfigImpl
