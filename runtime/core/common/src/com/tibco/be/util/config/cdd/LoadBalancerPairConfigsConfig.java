/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Load Balancer Pair Configs Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.LoadBalancerPairConfigsConfig#getLoadBalancerPairConfigs <em>Load Balancer Pair Configs</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getLoadBalancerPairConfigsConfig()
 * @model extendedMetaData="name='load-balancer-pair-configs-type' kind='elementOnly'"
 * @generated
 */
public interface LoadBalancerPairConfigsConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Load Balancer Pair Configs</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Load Balancer Pair Configs</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Load Balancer Pair Configs</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLoadBalancerPairConfigsConfig_LoadBalancerPairConfigs()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='pair-config' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<LoadBalancerPairConfigConfig> getLoadBalancerPairConfigs();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='return new java.util.Properties();'"
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // LoadBalancerPairConfigsConfig
