/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mm Agent Class Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.MmAgentClassConfig#getMmInferenceAgentClass <em>Mm Inference Agent Class</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.MmAgentClassConfig#getMmQueryAgentClass <em>Mm Query Agent Class</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.MmAgentClassConfig#getAlertConfigSet <em>Alert Config Set</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.MmAgentClassConfig#getRuleConfig <em>Rule Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.MmAgentClassConfig#getMmActionConfigSet <em>Mm Action Config Set</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.MmAgentClassConfig#getPropertyGroup <em>Property Group</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getMmAgentClassConfig()
 * @model extendedMetaData="name='mm-agent-class-type' kind='elementOnly'"
 * @generated
 */
public interface MmAgentClassConfig extends AgentClassConfig {
	/**
	 * Returns the value of the '<em><b>Mm Inference Agent Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mm Inference Agent Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mm Inference Agent Class</em>' reference.
	 * @see #setMmInferenceAgentClass(InferenceAgentClassConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getMmAgentClassConfig_MmInferenceAgentClass()
	 * @model resolveProxies="false" required="true"
	 *        extendedMetaData="kind='element' name='mm-inference-agent-class' namespace='##targetNamespace'"
	 * @generated
	 */
	InferenceAgentClassConfig getMmInferenceAgentClass();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.MmAgentClassConfig#getMmInferenceAgentClass <em>Mm Inference Agent Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mm Inference Agent Class</em>' reference.
	 * @see #getMmInferenceAgentClass()
	 * @generated
	 */
	void setMmInferenceAgentClass(InferenceAgentClassConfig value);

	/**
	 * Returns the value of the '<em><b>Mm Query Agent Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mm Query Agent Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mm Query Agent Class</em>' reference.
	 * @see #setMmQueryAgentClass(QueryAgentClassConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getMmAgentClassConfig_MmQueryAgentClass()
	 * @model resolveProxies="false" required="true"
	 *        extendedMetaData="kind='element' name='mm-query-agent-class' namespace='##targetNamespace'"
	 * @generated
	 */
	QueryAgentClassConfig getMmQueryAgentClass();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.MmAgentClassConfig#getMmQueryAgentClass <em>Mm Query Agent Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mm Query Agent Class</em>' reference.
	 * @see #getMmQueryAgentClass()
	 * @generated
	 */
	void setMmQueryAgentClass(QueryAgentClassConfig value);

	/**
	 * Returns the value of the '<em><b>Alert Config Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Alert Config Set</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alert Config Set</em>' containment reference.
	 * @see #setAlertConfigSet(AlertConfigSetConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getMmAgentClassConfig_AlertConfigSet()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='alert-config-set' namespace='##targetNamespace'"
	 * @generated
	 */
	AlertConfigSetConfig getAlertConfigSet();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.MmAgentClassConfig#getAlertConfigSet <em>Alert Config Set</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Alert Config Set</em>' containment reference.
	 * @see #getAlertConfigSet()
	 * @generated
	 */
	void setAlertConfigSet(AlertConfigSetConfig value);

	/**
	 * Returns the value of the '<em><b>Rule Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Config</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Config</em>' containment reference.
	 * @see #setRuleConfig(RuleConfigConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getMmAgentClassConfig_RuleConfig()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='rule-config' namespace='##targetNamespace'"
	 * @generated
	 */
	RuleConfigConfig getRuleConfig();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.MmAgentClassConfig#getRuleConfig <em>Rule Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Config</em>' containment reference.
	 * @see #getRuleConfig()
	 * @generated
	 */
	void setRuleConfig(RuleConfigConfig value);

	/**
	 * Returns the value of the '<em><b>Mm Action Config Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mm Action Config Set</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mm Action Config Set</em>' containment reference.
	 * @see #setMmActionConfigSet(MmActionConfigSetConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getMmAgentClassConfig_MmActionConfigSet()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='mm-action-config-set' namespace='##targetNamespace'"
	 * @generated
	 */
	MmActionConfigSetConfig getMmActionConfigSet();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.MmAgentClassConfig#getMmActionConfigSet <em>Mm Action Config Set</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mm Action Config Set</em>' containment reference.
	 * @see #getMmActionConfigSet()
	 * @generated
	 */
	void setMmActionConfigSet(MmActionConfigSetConfig value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getMmAgentClassConfig_PropertyGroup()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='property-group' namespace='##targetNamespace'"
	 * @generated
	 */
	PropertyGroupConfig getPropertyGroup();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.MmAgentClassConfig#getPropertyGroup <em>Property Group</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Group</em>' containment reference.
	 * @see #getPropertyGroup()
	 * @generated
	 */
	void setPropertyGroup(PropertyGroupConfig value);

} // MmAgentClassConfig
