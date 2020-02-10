/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import java.util.Map;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Load Balancer Pair Config Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig#getChannel <em>Channel</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig#getKey <em>Key</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig#getRouter <em>Router</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig#getReceiver <em>Receiver</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig#getPropertyGroup <em>Property Group</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getLoadBalancerPairConfigConfig()
 * @model extendedMetaData="name='load-balancer-pair-config-type' kind='elementOnly'"
 * @generated
 */
public interface LoadBalancerPairConfigConfig extends ArtifactConfig {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' containment reference.
	 * @see #setName(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLoadBalancerPairConfigConfig_Name()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getName();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig#getName <em>Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' containment reference.
	 * @see #getName()
	 * @generated
	 */
	void setName(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Channel</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Channel</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Channel</em>' containment reference.
	 * @see #setChannel(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLoadBalancerPairConfigConfig_Channel()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='channel' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getChannel();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig#getChannel <em>Channel</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Channel</em>' containment reference.
	 * @see #getChannel()
	 * @generated
	 */
	void setChannel(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Key</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Key</em>' containment reference.
	 * @see #setKey(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLoadBalancerPairConfigConfig_Key()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='key' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getKey();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig#getKey <em>Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key</em>' containment reference.
	 * @see #getKey()
	 * @generated
	 */
	void setKey(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Router</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Router</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Router</em>' containment reference.
	 * @see #setRouter(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLoadBalancerPairConfigConfig_Router()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='router' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getRouter();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig#getRouter <em>Router</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Router</em>' containment reference.
	 * @see #getRouter()
	 * @generated
	 */
	void setRouter(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Receiver</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Receiver</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Receiver</em>' containment reference.
	 * @see #setReceiver(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLoadBalancerPairConfigConfig_Receiver()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='receiver' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getReceiver();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig#getReceiver <em>Receiver</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Receiver</em>' containment reference.
	 * @see #getReceiver()
	 * @generated
	 */
	void setReceiver(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Property Group</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Group</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Group</em>' containment reference.
	 * @see #setPropertyGroup(PropertyGroupConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLoadBalancerPairConfigConfig_PropertyGroup()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='property-group' namespace='##targetNamespace'"
	 * @generated
	 */
	PropertyGroupConfig getPropertyGroup();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig#getPropertyGroup <em>Property Group</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Group</em>' containment reference.
	 * @see #getPropertyGroup()
	 * @generated
	 */
	void setPropertyGroup(PropertyGroupConfig value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // LoadBalancerPairConfigConfig
