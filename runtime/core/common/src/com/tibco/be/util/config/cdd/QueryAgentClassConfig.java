/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Query Agent Class Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.QueryAgentClassConfig#getDestinations <em>Destinations</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.QueryAgentClassConfig#getStartup <em>Startup</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.QueryAgentClassConfig#getShutdown <em>Shutdown</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.QueryAgentClassConfig#getLoad <em>Load</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.QueryAgentClassConfig#getLocalCache <em>Local Cache</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.QueryAgentClassConfig#getPropertyGroup <em>Property Group</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.QueryAgentClassConfig#getSharedQueue <em>Shared Queue</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getQueryAgentClassConfig()
 * @model extendedMetaData="name='query-agent-class-type' kind='elementOnly'"
 * @generated
 */
public interface QueryAgentClassConfig extends AgentClassConfig {
	/**
	 * Returns the value of the '<em><b>Destinations</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Destinations</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Destinations</em>' containment reference.
	 * @see #setDestinations(DestinationsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getQueryAgentClassConfig_Destinations()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='destinations' namespace='##targetNamespace'"
	 * @generated
	 */
	DestinationsConfig getDestinations();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.QueryAgentClassConfig#getDestinations <em>Destinations</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Destinations</em>' containment reference.
	 * @see #getDestinations()
	 * @generated
	 */
	void setDestinations(DestinationsConfig value);

	/**
	 * Returns the value of the '<em><b>Startup</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Startup</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Startup</em>' containment reference.
	 * @see #setStartup(FunctionsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getQueryAgentClassConfig_Startup()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='startup' namespace='##targetNamespace'"
	 * @generated
	 */
	FunctionsConfig getStartup();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.QueryAgentClassConfig#getStartup <em>Startup</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Startup</em>' containment reference.
	 * @see #getStartup()
	 * @generated
	 */
	void setStartup(FunctionsConfig value);

	/**
	 * Returns the value of the '<em><b>Shutdown</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shutdown</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shutdown</em>' containment reference.
	 * @see #setShutdown(FunctionsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getQueryAgentClassConfig_Shutdown()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='shutdown' namespace='##targetNamespace'"
	 * @generated
	 */
	FunctionsConfig getShutdown();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.QueryAgentClassConfig#getShutdown <em>Shutdown</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shutdown</em>' containment reference.
	 * @see #getShutdown()
	 * @generated
	 */
	void setShutdown(FunctionsConfig value);

	/**
	 * Returns the value of the '<em><b>Load</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Load</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Load</em>' containment reference.
	 * @see #setLoad(LoadConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getQueryAgentClassConfig_Load()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='load' namespace='##targetNamespace'"
	 * @generated
	 */
	LoadConfig getLoad();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.QueryAgentClassConfig#getLoad <em>Load</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Load</em>' containment reference.
	 * @see #getLoad()
	 * @generated
	 */
	void setLoad(LoadConfig value);

	/**
	 * Returns the value of the '<em><b>Local Cache</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Local Cache</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Local Cache</em>' containment reference.
	 * @see #setLocalCache(LocalCacheConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getQueryAgentClassConfig_LocalCache()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='local-cache' namespace='##targetNamespace'"
	 * @generated
	 */
	LocalCacheConfig getLocalCache();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.QueryAgentClassConfig#getLocalCache <em>Local Cache</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Local Cache</em>' containment reference.
	 * @see #getLocalCache()
	 * @generated
	 */
	void setLocalCache(LocalCacheConfig value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getQueryAgentClassConfig_PropertyGroup()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='property-group' namespace='##targetNamespace'"
	 * @generated
	 */
	PropertyGroupConfig getPropertyGroup();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.QueryAgentClassConfig#getPropertyGroup <em>Property Group</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Group</em>' containment reference.
	 * @see #getPropertyGroup()
	 * @generated
	 */
	void setPropertyGroup(PropertyGroupConfig value);

	/**
	 * Returns the value of the '<em><b>Shared Queue</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shared Queue</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shared Queue</em>' containment reference.
	 * @see #setSharedQueue(SharedQueueConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getQueryAgentClassConfig_SharedQueue()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='shared-queue' namespace='##targetNamespace'"
	 * @generated
	 */
	SharedQueueConfig getSharedQueue();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.QueryAgentClassConfig#getSharedQueue <em>Shared Queue</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shared Queue</em>' containment reference.
	 * @see #getSharedQueue()
	 * @generated
	 */
	void setSharedQueue(SharedQueueConfig value);

} // QueryAgentClassConfig
