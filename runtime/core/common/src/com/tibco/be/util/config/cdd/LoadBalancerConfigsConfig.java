/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Load Balancer Configs Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.LoadBalancerConfigsConfig#getLoadBalancerPairConfigs <em>Load Balancer Pair Configs</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.LoadBalancerConfigsConfig#getLoadBalancerAdhocConfigs <em>Load Balancer Adhoc Configs</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getLoadBalancerConfigsConfig()
 * @model extendedMetaData="name='load-balancer-configs-type' kind='elementOnly'"
 * @generated
 */
public interface LoadBalancerConfigsConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Load Balancer Pair Configs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Load Balancer Pair Configs</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Load Balancer Pair Configs</em>' containment reference.
	 * @see #setLoadBalancerPairConfigs(LoadBalancerPairConfigsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLoadBalancerConfigsConfig_LoadBalancerPairConfigs()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='pair-configs' namespace='##targetNamespace'"
	 * @generated
	 */
	LoadBalancerPairConfigsConfig getLoadBalancerPairConfigs();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LoadBalancerConfigsConfig#getLoadBalancerPairConfigs <em>Load Balancer Pair Configs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Load Balancer Pair Configs</em>' containment reference.
	 * @see #getLoadBalancerPairConfigs()
	 * @generated
	 */
	void setLoadBalancerPairConfigs(LoadBalancerPairConfigsConfig value);

	/**
	 * Returns the value of the '<em><b>Load Balancer Adhoc Configs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Load Balancer Adhoc Configs</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Load Balancer Adhoc Configs</em>' containment reference.
	 * @see #setLoadBalancerAdhocConfigs(LoadBalancerAdhocConfigsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLoadBalancerConfigsConfig_LoadBalancerAdhocConfigs()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='adhoc-configs' namespace='##targetNamespace'"
	 * @generated
	 */
	LoadBalancerAdhocConfigsConfig getLoadBalancerAdhocConfigs();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LoadBalancerConfigsConfig#getLoadBalancerAdhocConfigs <em>Load Balancer Adhoc Configs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Load Balancer Adhoc Configs</em>' containment reference.
	 * @see #getLoadBalancerAdhocConfigs()
	 * @generated
	 */
	void setLoadBalancerAdhocConfigs(LoadBalancerAdhocConfigsConfig value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='return new java.util.Properties();'"
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // LoadBalancerConfigsConfig
