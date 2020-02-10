/**
 */
package com.tibco.be.util.config.cdd;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Live View Agent Class Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.LiveViewAgentClassConfig#getLdmConnection <em>Ldm Connection</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.LiveViewAgentClassConfig#getPublisher <em>Publisher</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.LiveViewAgentClassConfig#getEntitySet <em>Entity Set</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.LiveViewAgentClassConfig#getLoad <em>Load</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.LiveViewAgentClassConfig#getPropertyGroup <em>Property Group</em>}</li>
 * </ul>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getLiveViewAgentClassConfig()
 * @model extendedMetaData="name='liveview-agent-class-type' kind='elementOnly'"
 * @generated
 */
public interface LiveViewAgentClassConfig extends AgentClassConfig {
	/**
	 * Returns the value of the '<em><b>Ldm Connection</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ldm Connection</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ldm Connection</em>' containment reference.
	 * @see #setLdmConnection(LDMConnectionConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLiveViewAgentClassConfig_LdmConnection()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='ldm-connection' namespace='##targetNamespace'"
	 * @generated
	 */
	LDMConnectionConfig getLdmConnection();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LiveViewAgentClassConfig#getLdmConnection <em>Ldm Connection</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ldm Connection</em>' containment reference.
	 * @see #getLdmConnection()
	 * @generated
	 */
	void setLdmConnection(LDMConnectionConfig value);

	/**
	 * Returns the value of the '<em><b>Publisher</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Publisher</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Publisher</em>' containment reference.
	 * @see #setPublisher(PublisherConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLiveViewAgentClassConfig_Publisher()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='publisher' namespace='##targetNamespace'"
	 * @generated
	 */
	PublisherConfig getPublisher();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LiveViewAgentClassConfig#getPublisher <em>Publisher</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Publisher</em>' containment reference.
	 * @see #getPublisher()
	 * @generated
	 */
	void setPublisher(PublisherConfig value);

	/**
	 * Returns the value of the '<em><b>Entity Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity Set</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity Set</em>' containment reference.
	 * @see #setEntitySet(EntitySetConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLiveViewAgentClassConfig_EntitySet()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='entity-set' namespace='##targetNamespace'"
	 * @generated
	 */
	EntitySetConfig getEntitySet();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LiveViewAgentClassConfig#getEntitySet <em>Entity Set</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entity Set</em>' containment reference.
	 * @see #getEntitySet()
	 * @generated
	 */
	void setEntitySet(EntitySetConfig value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLiveViewAgentClassConfig_Load()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='load' namespace='##targetNamespace'"
	 * @generated
	 */
	LoadConfig getLoad();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LiveViewAgentClassConfig#getLoad <em>Load</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Load</em>' containment reference.
	 * @see #getLoad()
	 * @generated
	 */
	void setLoad(LoadConfig value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLiveViewAgentClassConfig_PropertyGroup()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='property-group' namespace='##targetNamespace'"
	 * @generated
	 */
	PropertyGroupConfig getPropertyGroup();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LiveViewAgentClassConfig#getPropertyGroup <em>Property Group</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Group</em>' containment reference.
	 * @see #getPropertyGroup()
	 * @generated
	 */
	void setPropertyGroup(PropertyGroupConfig value);

} // LiveViewAgentClassConfig
