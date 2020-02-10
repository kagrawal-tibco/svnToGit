/**
 */
package com.tibco.be.util.config.cdd;

import java.util.Map;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Agent Classes Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.AgentClassesConfig#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.AgentClassesConfig#getCacheAgentConfig <em>Cache Agent Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.AgentClassesConfig#getDashboardAgentConfig <em>Dashboard Agent Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.AgentClassesConfig#getInferenceAgentConfig <em>Inference Agent Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.AgentClassesConfig#getQueryAgentConfig <em>Query Agent Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.AgentClassesConfig#getMmAgentConfig <em>Mm Agent Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.AgentClassesConfig#getProcessAgentConfig <em>Process Agent Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.AgentClassesConfig#getLiveViewAgentConfig <em>Live View Agent Config</em>}</li>
 * </ul>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getAgentClassesConfig()
 * @model extendedMetaData="name='agent-classes-type' kind='elementOnly'"
 * @generated
 */
public interface AgentClassesConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Group</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Group</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group</em>' attribute list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getAgentClassesConfig_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:0'"
	 * @generated
	 */
	FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>Cache Agent Config</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.CacheAgentClassConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache Agent Config</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cache Agent Config</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getAgentClassesConfig_CacheAgentConfig()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='cache-agent-class' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<CacheAgentClassConfig> getCacheAgentConfig();

	/**
	 * Returns the value of the '<em><b>Dashboard Agent Config</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.DashboardAgentClassConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dashboard Agent Config</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dashboard Agent Config</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getAgentClassesConfig_DashboardAgentConfig()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='dashboard-agent-class' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<DashboardAgentClassConfig> getDashboardAgentConfig();

	/**
	 * Returns the value of the '<em><b>Inference Agent Config</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.InferenceAgentClassConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inference Agent Config</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inference Agent Config</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getAgentClassesConfig_InferenceAgentConfig()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='inference-agent-class' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<InferenceAgentClassConfig> getInferenceAgentConfig();

	/**
	 * Returns the value of the '<em><b>Query Agent Config</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.QueryAgentClassConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Query Agent Config</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Query Agent Config</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getAgentClassesConfig_QueryAgentConfig()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='query-agent-class' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<QueryAgentClassConfig> getQueryAgentConfig();

	/**
	 * Returns the value of the '<em><b>Mm Agent Config</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.MmAgentClassConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mm Agent Config</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mm Agent Config</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getAgentClassesConfig_MmAgentConfig()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mm-agent-class' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<MmAgentClassConfig> getMmAgentConfig();

	/**
	 * Returns the value of the '<em><b>Process Agent Config</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.ProcessAgentClassConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Process Agent Config</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Process Agent Config</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getAgentClassesConfig_ProcessAgentConfig()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='process-agent-class' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<ProcessAgentClassConfig> getProcessAgentConfig();

	/**
	 * Returns the value of the '<em><b>Live View Agent Config</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.LiveViewAgentClassConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Live View Agent Config</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Live View Agent Config</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getAgentClassesConfig_LiveViewAgentConfig()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='liveview-agent-class' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<LiveViewAgentClassConfig> getLiveViewAgentConfig();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='return new java.util.Properties();'"
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // AgentClassesConfig
