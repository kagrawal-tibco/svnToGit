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
 * A representation of the model object '<em><b>Load Balancer Adhoc Configs Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.LoadBalancerAdhocConfigsConfig#getLoadBalancerAdhocConfigs <em>Load Balancer Adhoc Configs</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getLoadBalancerAdhocConfigsConfig()
 * @model extendedMetaData="name='load-balancer-adhoc-configs-type' kind='elementOnly'"
 * @generated
 */
public interface LoadBalancerAdhocConfigsConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Load Balancer Adhoc Configs</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.LoadBalancerAdhocConfigConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Load Balancer Adhoc Configs</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Load Balancer Adhoc Configs</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLoadBalancerAdhocConfigsConfig_LoadBalancerAdhocConfigs()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='adhoc-config' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<LoadBalancerAdhocConfigConfig> getLoadBalancerAdhocConfigs();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='return new java.util.Properties();'"
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // LoadBalancerAdhocConfigsConfig
