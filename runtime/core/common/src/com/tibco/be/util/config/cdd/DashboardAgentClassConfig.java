/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dashboard Agent Class Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.DashboardAgentClassConfig#getRules <em>Rules</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DashboardAgentClassConfig#getDestinations <em>Destinations</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DashboardAgentClassConfig#getStartup <em>Startup</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DashboardAgentClassConfig#getShutdown <em>Shutdown</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DashboardAgentClassConfig#getPropertyGroup <em>Property Group</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getDashboardAgentClassConfig()
 * @model extendedMetaData="name='dashboard-agent-class-type' kind='elementOnly'"
 * @generated
 */
public interface DashboardAgentClassConfig extends AgentClassConfig {
	/**
	 * Returns the value of the '<em><b>Rules</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rules</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rules</em>' containment reference.
	 * @see #setRules(RulesConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDashboardAgentClassConfig_Rules()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='rules' namespace='##targetNamespace'"
	 * @generated
	 */
	RulesConfig getRules();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DashboardAgentClassConfig#getRules <em>Rules</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rules</em>' containment reference.
	 * @see #getRules()
	 * @generated
	 */
	void setRules(RulesConfig value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDashboardAgentClassConfig_Destinations()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='destinations' namespace='##targetNamespace'"
	 * @generated
	 */
	DestinationsConfig getDestinations();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DashboardAgentClassConfig#getDestinations <em>Destinations</em>}' containment reference.
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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDashboardAgentClassConfig_Startup()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='startup' namespace='##targetNamespace'"
	 * @generated
	 */
	FunctionsConfig getStartup();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DashboardAgentClassConfig#getStartup <em>Startup</em>}' containment reference.
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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDashboardAgentClassConfig_Shutdown()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='shutdown' namespace='##targetNamespace'"
	 * @generated
	 */
	FunctionsConfig getShutdown();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DashboardAgentClassConfig#getShutdown <em>Shutdown</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shutdown</em>' containment reference.
	 * @see #getShutdown()
	 * @generated
	 */
	void setShutdown(FunctionsConfig value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDashboardAgentClassConfig_PropertyGroup()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='property-group' namespace='##targetNamespace'"
	 * @generated
	 */
	PropertyGroupConfig getPropertyGroup();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DashboardAgentClassConfig#getPropertyGroup <em>Property Group</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Group</em>' containment reference.
	 * @see #getPropertyGroup()
	 * @generated
	 */
	void setPropertyGroup(PropertyGroupConfig value);

} // DashboardAgentClassConfig
