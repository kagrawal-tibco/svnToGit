/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Process Engine Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.ProcessEngineConfig#getProcess <em>Process</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ProcessEngineConfig#getStartup <em>Startup</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ProcessEngineConfig#getShutdown <em>Shutdown</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ProcessEngineConfig#getRules <em>Rules</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ProcessEngineConfig#getDestinations <em>Destinations</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ProcessEngineConfig#getBusinessworks <em>Businessworks</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ProcessEngineConfig#getPropertyGroup <em>Property Group</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ProcessEngineConfig#getJobManager <em>Job Manager</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ProcessEngineConfig#getInferenceEngine <em>Inference Engine</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessEngineConfig()
 * @model extendedMetaData="name='process-engine-type' kind='elementOnly'"
 * @generated
 */
public interface ProcessEngineConfig extends ArtifactConfig {
	/**
	 * Returns the value of the '<em><b>Process</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Process</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Process</em>' containment reference.
	 * @see #setProcess(ProcessConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessEngineConfig_Process()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='process' namespace='##targetNamespace'"
	 * @generated
	 */
	ProcessConfig getProcess();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ProcessEngineConfig#getProcess <em>Process</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Process</em>' containment reference.
	 * @see #getProcess()
	 * @generated
	 */
	void setProcess(ProcessConfig value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessEngineConfig_Startup()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='startup' namespace='##targetNamespace'"
	 * @generated
	 */
	FunctionsConfig getStartup();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ProcessEngineConfig#getStartup <em>Startup</em>}' containment reference.
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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessEngineConfig_Shutdown()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='shutdown' namespace='##targetNamespace'"
	 * @generated
	 */
	FunctionsConfig getShutdown();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ProcessEngineConfig#getShutdown <em>Shutdown</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shutdown</em>' containment reference.
	 * @see #getShutdown()
	 * @generated
	 */
	void setShutdown(FunctionsConfig value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessEngineConfig_Rules()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='rules' namespace='##targetNamespace'"
	 * @generated
	 */
	RulesConfig getRules();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ProcessEngineConfig#getRules <em>Rules</em>}' containment reference.
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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessEngineConfig_Destinations()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='destinations' namespace='##targetNamespace'"
	 * @generated
	 */
	DestinationsConfig getDestinations();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ProcessEngineConfig#getDestinations <em>Destinations</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Destinations</em>' containment reference.
	 * @see #getDestinations()
	 * @generated
	 */
	void setDestinations(DestinationsConfig value);

	/**
	 * Returns the value of the '<em><b>Businessworks</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Businessworks</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Businessworks</em>' containment reference.
	 * @see #setBusinessworks(BusinessworksConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessEngineConfig_Businessworks()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='businessworks' namespace='##targetNamespace'"
	 * @generated
	 */
	BusinessworksConfig getBusinessworks();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ProcessEngineConfig#getBusinessworks <em>Businessworks</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Businessworks</em>' containment reference.
	 * @see #getBusinessworks()
	 * @generated
	 */
	void setBusinessworks(BusinessworksConfig value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessEngineConfig_PropertyGroup()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='property-group' namespace='##targetNamespace'"
	 * @generated
	 */
	PropertyGroupConfig getPropertyGroup();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ProcessEngineConfig#getPropertyGroup <em>Property Group</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Group</em>' containment reference.
	 * @see #getPropertyGroup()
	 * @generated
	 */
	void setPropertyGroup(PropertyGroupConfig value);

	/**
	 * Returns the value of the '<em><b>Job Manager</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Job Manager</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Job Manager</em>' containment reference.
	 * @see #setJobManager(JobManagerConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessEngineConfig_JobManager()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='job-manager' namespace='##targetNamespace'"
	 * @generated
	 */
	JobManagerConfig getJobManager();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ProcessEngineConfig#getJobManager <em>Job Manager</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Job Manager</em>' containment reference.
	 * @see #getJobManager()
	 * @generated
	 */
	void setJobManager(JobManagerConfig value);

	/**
	 * Returns the value of the '<em><b>Inference Engine</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inference Engine</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inference Engine</em>' containment reference.
	 * @see #setInferenceEngine(InferenceEngineConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessEngineConfig_InferenceEngine()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='inference-engine' namespace='##targetNamespace'"
	 * @generated
	 */
	InferenceEngineConfig getInferenceEngine();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ProcessEngineConfig#getInferenceEngine <em>Inference Engine</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inference Engine</em>' containment reference.
	 * @see #getInferenceEngine()
	 * @generated
	 */
	void setInferenceEngine(InferenceEngineConfig value);

} // ProcessEngineConfig
