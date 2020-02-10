/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Process Agent Class Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.ProcessAgentClassConfig#getLoad <em>Load</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ProcessAgentClassConfig#getProcessEngine <em>Process Engine</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ProcessAgentClassConfig#getPropertyGroup <em>Property Group</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessAgentClassConfig()
 * @model extendedMetaData="name='process-agent-class-type' kind='elementOnly'"
 * @generated
 */
public interface ProcessAgentClassConfig extends AgentClassConfig {
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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessAgentClassConfig_Load()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='load' namespace='##targetNamespace'"
	 * @generated
	 */
	LoadConfig getLoad();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ProcessAgentClassConfig#getLoad <em>Load</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Load</em>' containment reference.
	 * @see #getLoad()
	 * @generated
	 */
	void setLoad(LoadConfig value);

	/**
	 * Returns the value of the '<em><b>Process Engine</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.ProcessEngineConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Process Engine</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Process Engine</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessAgentClassConfig_ProcessEngine()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='process-engine' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<ProcessEngineConfig> getProcessEngine();

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessAgentClassConfig_PropertyGroup()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='property-group' namespace='##targetNamespace'"
	 * @generated
	 */
	PropertyGroupConfig getPropertyGroup();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ProcessAgentClassConfig#getPropertyGroup <em>Property Group</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Group</em>' containment reference.
	 * @see #getPropertyGroup()
	 * @generated
	 */
	void setPropertyGroup(PropertyGroupConfig value);

} // ProcessAgentClassConfig
