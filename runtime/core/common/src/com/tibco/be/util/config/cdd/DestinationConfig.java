/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Destination Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.DestinationConfig#getUri <em>Uri</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DestinationConfig#getPreProcessor <em>Pre Processor</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DestinationConfig#getThreadingModel <em>Threading Model</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DestinationConfig#getThreadCount <em>Thread Count</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DestinationConfig#getQueueSize <em>Queue Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DestinationConfig#getThreadAffinityRuleFunction <em>Thread Affinity Rule Function</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getDestinationConfig()
 * @model extendedMetaData="name='destination-type' kind='elementOnly'"
 * @generated
 */
public interface DestinationConfig extends ArtifactConfig {
	/**
	 * Returns the value of the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uri</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uri</em>' attribute.
	 * @see #setUri(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDestinationConfig_Uri()
	 * @model dataType="com.tibco.be.util.config.cdd.OntologyUriConfig" required="true"
	 *        extendedMetaData="kind='element' name='uri' namespace='##targetNamespace'"
	 * @generated
	 */
	String getUri();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DestinationConfig#getUri <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uri</em>' attribute.
	 * @see #getUri()
	 * @generated
	 */
	void setUri(String value);

	/**
	 * Returns the value of the '<em><b>Pre Processor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pre Processor</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre Processor</em>' attribute.
	 * @see #setPreProcessor(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDestinationConfig_PreProcessor()
	 * @model dataType="com.tibco.be.util.config.cdd.OntologyUriConfig"
	 *        extendedMetaData="kind='element' name='pre-processor' namespace='##targetNamespace'"
	 * @generated
	 */
	String getPreProcessor();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DestinationConfig#getPreProcessor <em>Pre Processor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Processor</em>' attribute.
	 * @see #getPreProcessor()
	 * @generated
	 */
	void setPreProcessor(String value);

	/**
	 * Returns the value of the '<em><b>Threading Model</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.be.util.config.cdd.ThreadingModelConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Threading Model</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Threading Model</em>' attribute.
	 * @see com.tibco.be.util.config.cdd.ThreadingModelConfig
	 * @see #isSetThreadingModel()
	 * @see #unsetThreadingModel()
	 * @see #setThreadingModel(ThreadingModelConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDestinationConfig_ThreadingModel()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='element' name='threading-model' namespace='##targetNamespace'"
	 * @generated
	 */
	ThreadingModelConfig getThreadingModel();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DestinationConfig#getThreadingModel <em>Threading Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Threading Model</em>' attribute.
	 * @see com.tibco.be.util.config.cdd.ThreadingModelConfig
	 * @see #isSetThreadingModel()
	 * @see #unsetThreadingModel()
	 * @see #getThreadingModel()
	 * @generated
	 */
	void setThreadingModel(ThreadingModelConfig value);

	/**
	 * Unsets the value of the '{@link com.tibco.be.util.config.cdd.DestinationConfig#getThreadingModel <em>Threading Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetThreadingModel()
	 * @see #getThreadingModel()
	 * @see #setThreadingModel(ThreadingModelConfig)
	 * @generated
	 */
	void unsetThreadingModel();

	/**
	 * Returns whether the value of the '{@link com.tibco.be.util.config.cdd.DestinationConfig#getThreadingModel <em>Threading Model</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Threading Model</em>' attribute is set.
	 * @see #unsetThreadingModel()
	 * @see #getThreadingModel()
	 * @see #setThreadingModel(ThreadingModelConfig)
	 * @generated
	 */
	boolean isSetThreadingModel();

	/**
	 * Returns the value of the '<em><b>Thread Count</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Thread Count</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Thread Count</em>' containment reference.
	 * @see #setThreadCount(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDestinationConfig_ThreadCount()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='thread-count' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getThreadCount();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DestinationConfig#getThreadCount <em>Thread Count</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Thread Count</em>' containment reference.
	 * @see #getThreadCount()
	 * @generated
	 */
	void setThreadCount(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Queue Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Queue Size</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Queue Size</em>' containment reference.
	 * @see #setQueueSize(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDestinationConfig_QueueSize()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='queue-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getQueueSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DestinationConfig#getQueueSize <em>Queue Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Queue Size</em>' containment reference.
	 * @see #getQueueSize()
	 * @generated
	 */
	void setQueueSize(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Thread Affinity Rule Function</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Thread Affinity Rule Function</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Thread Affinity Rule Function</em>' attribute.
	 * @see #setThreadAffinityRuleFunction(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDestinationConfig_ThreadAffinityRuleFunction()
	 * @model default="" dataType="com.tibco.be.util.config.cdd.OntologyUriConfig"
	 *        extendedMetaData="kind='element' name='thread-affinity-rule-function' namespace='##targetNamespace'"
	 * @generated
	 */
	String getThreadAffinityRuleFunction();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DestinationConfig#getThreadAffinityRuleFunction <em>Thread Affinity Rule Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Thread Affinity Rule Function</em>' attribute.
	 * @see #getThreadAffinityRuleFunction()
	 * @generated
	 */
	void setThreadAffinityRuleFunction(String value);

} // DestinationConfig
