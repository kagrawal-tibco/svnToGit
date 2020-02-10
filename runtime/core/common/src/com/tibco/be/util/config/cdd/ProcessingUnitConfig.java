/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Processing Unit Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.ProcessingUnitConfig#getAgents <em>Agents</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ProcessingUnitConfig#getCacheStorageEnabled <em>Cache Storage Enabled</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ProcessingUnitConfig#getDbConcepts <em>Db Concepts</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ProcessingUnitConfig#getLogs <em>Logs</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ProcessingUnitConfig#getHotDeploy <em>Hot Deploy</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ProcessingUnitConfig#getHttp <em>Http</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ProcessingUnitConfig#getPropertyGroup <em>Property Group</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ProcessingUnitConfig#getSecurityConfig <em>Security Config</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessingUnitConfig()
 * @model extendedMetaData="name='processing-unit-type' kind='elementOnly'"
 * @generated
 */
public interface ProcessingUnitConfig extends ArtifactConfig {
	/**
	 * Returns the value of the '<em><b>Agents</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Agents</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Agents</em>' containment reference.
	 * @see #setAgents(AgentsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessingUnitConfig_Agents()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='agents' namespace='##targetNamespace'"
	 * @generated
	 */
	AgentsConfig getAgents();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ProcessingUnitConfig#getAgents <em>Agents</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Agents</em>' containment reference.
	 * @see #getAgents()
	 * @generated
	 */
	void setAgents(AgentsConfig value);

	/**
	 * Returns the value of the '<em><b>Cache Storage Enabled</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache Storage Enabled</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cache Storage Enabled</em>' containment reference.
	 * @see #setCacheStorageEnabled(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessingUnitConfig_CacheStorageEnabled()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='cache-storage-enabled' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getCacheStorageEnabled();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ProcessingUnitConfig#getCacheStorageEnabled <em>Cache Storage Enabled</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache Storage Enabled</em>' containment reference.
	 * @see #getCacheStorageEnabled()
	 * @generated
	 */
	void setCacheStorageEnabled(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Db Concepts</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Db Concepts</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Db Concepts</em>' containment reference.
	 * @see #setDbConcepts(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessingUnitConfig_DbConcepts()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='db-concepts' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getDbConcepts();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ProcessingUnitConfig#getDbConcepts <em>Db Concepts</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Db Concepts</em>' containment reference.
	 * @see #getDbConcepts()
	 * @generated
	 */
	void setDbConcepts(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Logs</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Logs</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Logs</em>' reference.
	 * @see #setLogs(LogConfigConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessingUnitConfig_Logs()
	 * @model resolveProxies="false"
	 *        extendedMetaData="kind='element' name='logs' namespace='##targetNamespace'"
	 * @generated
	 */
	LogConfigConfig getLogs();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ProcessingUnitConfig#getLogs <em>Logs</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Logs</em>' reference.
	 * @see #getLogs()
	 * @generated
	 */
	void setLogs(LogConfigConfig value);

	/**
	 * Returns the value of the '<em><b>Hot Deploy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hot Deploy</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hot Deploy</em>' containment reference.
	 * @see #setHotDeploy(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessingUnitConfig_HotDeploy()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='hot-deploy' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getHotDeploy();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ProcessingUnitConfig#getHotDeploy <em>Hot Deploy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hot Deploy</em>' containment reference.
	 * @see #getHotDeploy()
	 * @generated
	 */
	void setHotDeploy(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Http</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Http</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Http</em>' containment reference.
	 * @see #setHttp(HttpConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessingUnitConfig_Http()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='http' namespace='##targetNamespace'"
	 * @generated
	 */
	HttpConfig getHttp();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ProcessingUnitConfig#getHttp <em>Http</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Http</em>' containment reference.
	 * @see #getHttp()
	 * @generated
	 */
	void setHttp(HttpConfig value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessingUnitConfig_PropertyGroup()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='property-group' namespace='##targetNamespace'"
	 * @generated
	 */
	PropertyGroupConfig getPropertyGroup();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ProcessingUnitConfig#getPropertyGroup <em>Property Group</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Group</em>' containment reference.
	 * @see #getPropertyGroup()
	 * @generated
	 */
	void setPropertyGroup(PropertyGroupConfig value);

	/**
	 * Returns the value of the '<em><b>Security Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Security Config</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Security Config</em>' containment reference.
	 * @see #setSecurityConfig(ProcessingUnitSecurityConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessingUnitConfig_SecurityConfig()
	 * @model containment="true"
	 *        extendedMetaData="name='security-config' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	ProcessingUnitSecurityConfig getSecurityConfig();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ProcessingUnitConfig#getSecurityConfig <em>Security Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Security Config</em>' containment reference.
	 * @see #getSecurityConfig()
	 * @generated
	 */
	void setSecurityConfig(ProcessingUnitSecurityConfig value);

} // ProcessingUnitConfig
