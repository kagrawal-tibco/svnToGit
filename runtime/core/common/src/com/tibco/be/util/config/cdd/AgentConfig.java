/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Agent Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.AgentConfig#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.AgentConfig#getRef <em>Ref</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.AgentConfig#getKey <em>Key</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.AgentConfig#getPriority <em>Priority</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getAgentConfig()
 * @model extendedMetaData="name='agent-type' kind='mixed'"
 * @generated
 */
public interface AgentConfig extends ArtifactConfig {
	/**
	 * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mixed</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mixed</em>' attribute list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getAgentConfig_Mixed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' name=':mixed'"
	 * @generated
	 */
	FeatureMap getMixed();

	/**
	 * Returns the value of the '<em><b>Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ref</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ref</em>' reference.
	 * @see #setRef(AgentClassConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getAgentConfig_Ref()
	 * @model resolveProxies="false" required="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ref' namespace='##targetNamespace'"
	 * @generated
	 */
	AgentClassConfig getRef();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.AgentConfig#getRef <em>Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ref</em>' reference.
	 * @see #getRef()
	 * @generated
	 */
	void setRef(AgentClassConfig value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getAgentConfig_Key()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='key' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getKey();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.AgentConfig#getKey <em>Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key</em>' containment reference.
	 * @see #getKey()
	 * @generated
	 */
	void setKey(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Priority</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Priority</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Priority</em>' containment reference.
	 * @see #setPriority(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getAgentConfig_Priority()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='priority' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getPriority();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.AgentConfig#getPriority <em>Priority</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Priority</em>' containment reference.
	 * @see #getPriority()
	 * @generated
	 */
	void setPriority(OverrideConfig value);

} // AgentConfig
