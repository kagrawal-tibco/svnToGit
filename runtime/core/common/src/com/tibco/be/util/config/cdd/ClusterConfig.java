/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;



/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cluster Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.ClusterConfig#getAgentClasses <em>Agent Classes</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ClusterConfig#getDestinationGroups <em>Destination Groups</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ClusterConfig#getFunctionGroups <em>Function Groups</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ClusterConfig#getLoadBalancerConfigs <em>Load Balancer Configs</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ClusterConfig#getLogConfigs <em>Log Configs</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ClusterConfig#getMessageEncoding <em>Message Encoding</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ClusterConfig#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ClusterConfig#getObjectManagement <em>Object Management</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ClusterConfig#getProcessGroups <em>Process Groups</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ClusterConfig#getProcessingUnits <em>Processing Units</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ClusterConfig#getPropertyGroup <em>Property Group</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ClusterConfig#getRevision <em>Revision</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ClusterConfig#getRulesets <em>Rulesets</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getClusterConfig()
 * @model extendedMetaData="name='cluster-type' kind='elementOnly'"
 * @generated
 */
public interface ClusterConfig extends ArtifactConfig {
	/**
	 * Returns the value of the '<em><b>Agent Classes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Agent Classes</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Agent Classes</em>' containment reference.
	 * @see #setAgentClasses(AgentClassesConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getClusterConfig_AgentClasses()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='agent-classes' namespace='##targetNamespace'"
	 * @generated
	 */
	AgentClassesConfig getAgentClasses();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ClusterConfig#getAgentClasses <em>Agent Classes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Agent Classes</em>' containment reference.
	 * @see #getAgentClasses()
	 * @generated
	 */
	void setAgentClasses(AgentClassesConfig value);

	/**
	 * Returns the value of the '<em><b>Destination Groups</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Destination Groups</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Destination Groups</em>' containment reference.
	 * @see #setDestinationGroups(DestinationGroupsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getClusterConfig_DestinationGroups()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='destination-groups' namespace='##targetNamespace'"
	 * @generated
	 */
	DestinationGroupsConfig getDestinationGroups();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ClusterConfig#getDestinationGroups <em>Destination Groups</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Destination Groups</em>' containment reference.
	 * @see #getDestinationGroups()
	 * @generated
	 */
	void setDestinationGroups(DestinationGroupsConfig value);

	/**
	 * Returns the value of the '<em><b>Function Groups</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Function Groups</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function Groups</em>' containment reference.
	 * @see #setFunctionGroups(FunctionGroupsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getClusterConfig_FunctionGroups()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='function-groups' namespace='##targetNamespace'"
	 * @generated
	 */
	FunctionGroupsConfig getFunctionGroups();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ClusterConfig#getFunctionGroups <em>Function Groups</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function Groups</em>' containment reference.
	 * @see #getFunctionGroups()
	 * @generated
	 */
	void setFunctionGroups(FunctionGroupsConfig value);

	/**
	 * Returns the value of the '<em><b>Log Configs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Log Configs</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Log Configs</em>' containment reference.
	 * @see #setLogConfigs(LogConfigsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getClusterConfig_LogConfigs()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='log-configs' namespace='##targetNamespace'"
	 * @generated
	 */
	LogConfigsConfig getLogConfigs();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ClusterConfig#getLogConfigs <em>Log Configs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Log Configs</em>' containment reference.
	 * @see #getLogConfigs()
	 * @generated
	 */
	void setLogConfigs(LogConfigsConfig value);

	/**
	 * Returns the value of the '<em><b>Message Encoding</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Message Encoding</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Message Encoding</em>' attribute.
	 * @see #setMessageEncoding(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getClusterConfig_MessageEncoding()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='message-encoding' namespace='##targetNamespace'"
	 * @generated
	 */
	String getMessageEncoding();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ClusterConfig#getMessageEncoding <em>Message Encoding</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Message Encoding</em>' attribute.
	 * @see #getMessageEncoding()
	 * @generated
	 */
	void setMessageEncoding(String value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getClusterConfig_Name()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getName();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ClusterConfig#getName <em>Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' containment reference.
	 * @see #getName()
	 * @generated
	 */
	void setName(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Object Management</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Object Management</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object Management</em>' containment reference.
	 * @see #setObjectManagement(ObjectManagementConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getClusterConfig_ObjectManagement()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='object-management' namespace='##targetNamespace'"
	 * @generated
	 */
	ObjectManagementConfig getObjectManagement();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ClusterConfig#getObjectManagement <em>Object Management</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object Management</em>' containment reference.
	 * @see #getObjectManagement()
	 * @generated
	 */
	void setObjectManagement(ObjectManagementConfig value);

	/**
	 * Returns the value of the '<em><b>Process Groups</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Process Groups</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Process Groups</em>' containment reference.
	 * @see #setProcessGroups(ProcessGroupsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getClusterConfig_ProcessGroups()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='process-groups' namespace='##targetNamespace'"
	 * @generated
	 */
	ProcessGroupsConfig getProcessGroups();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ClusterConfig#getProcessGroups <em>Process Groups</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Process Groups</em>' containment reference.
	 * @see #getProcessGroups()
	 * @generated
	 */
	void setProcessGroups(ProcessGroupsConfig value);

	/**
	 * Returns the value of the '<em><b>Processing Units</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Processing Units</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Processing Units</em>' containment reference.
	 * @see #setProcessingUnits(ProcessingUnitsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getClusterConfig_ProcessingUnits()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='processing-units' namespace='##targetNamespace'"
	 * @generated
	 */
	ProcessingUnitsConfig getProcessingUnits();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ClusterConfig#getProcessingUnits <em>Processing Units</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Processing Units</em>' containment reference.
	 * @see #getProcessingUnits()
	 * @generated
	 */
	void setProcessingUnits(ProcessingUnitsConfig value);

	/**
	 * Returns the value of the '<em><b>Load Balancer Configs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Load Balancer Configs</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Load Balancer Configs</em>' containment reference.
	 * @see #setLoadBalancerConfigs(LoadBalancerConfigsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getClusterConfig_LoadBalancerConfigs()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='load-balancer-configs' namespace='##targetNamespace'"
	 * @generated
	 */
	LoadBalancerConfigsConfig getLoadBalancerConfigs();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ClusterConfig#getLoadBalancerConfigs <em>Load Balancer Configs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Load Balancer Configs</em>' containment reference.
	 * @see #getLoadBalancerConfigs()
	 * @generated
	 */
	void setLoadBalancerConfigs(LoadBalancerConfigsConfig value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getClusterConfig_PropertyGroup()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='property-group' namespace='##targetNamespace'"
	 * @generated
	 */
	PropertyGroupConfig getPropertyGroup();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ClusterConfig#getPropertyGroup <em>Property Group</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Group</em>' containment reference.
	 * @see #getPropertyGroup()
	 * @generated
	 */
	void setPropertyGroup(PropertyGroupConfig value);

	/**
	 * Returns the value of the '<em><b>Revision</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Revision</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Revision</em>' containment reference.
	 * @see #setRevision(RevisionConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getClusterConfig_Revision()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='revision' namespace='##targetNamespace'"
	 * @generated
	 */
	RevisionConfig getRevision();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ClusterConfig#getRevision <em>Revision</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Revision</em>' containment reference.
	 * @see #getRevision()
	 * @generated
	 */
	void setRevision(RevisionConfig value);

	/**
	 * Returns the value of the '<em><b>Rulesets</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rulesets</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rulesets</em>' containment reference.
	 * @see #setRulesets(RulesetsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getClusterConfig_Rulesets()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='rulesets' namespace='##targetNamespace'"
	 * @generated
	 */
	RulesetsConfig getRulesets();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ClusterConfig#getRulesets <em>Rulesets</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rulesets</em>' containment reference.
	 * @see #getRulesets()
	 * @generated
	 */
	void setRulesets(RulesetsConfig value);

} // ClusterConfig
