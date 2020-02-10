/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Inference Engine Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.InferenceEngineConfig#getCheckForDuplicates <em>Check For Duplicates</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.InferenceEngineConfig#getConcurrentRtc <em>Concurrent Rtc</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.InferenceEngineConfig#getLocalCache <em>Local Cache</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.InferenceEngineConfig#getPropertyGroup <em>Property Group</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getInferenceEngineConfig()
 * @model extendedMetaData="name='inference-engine-type' kind='elementOnly'"
 * @generated
 */
public interface InferenceEngineConfig extends ArtifactConfig {
	/**
	 * Returns the value of the '<em><b>Check For Duplicates</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Check For Duplicates</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Check For Duplicates</em>' containment reference.
	 * @see #setCheckForDuplicates(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getInferenceEngineConfig_CheckForDuplicates()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='check-for-duplicates' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getCheckForDuplicates();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.InferenceEngineConfig#getCheckForDuplicates <em>Check For Duplicates</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Check For Duplicates</em>' containment reference.
	 * @see #getCheckForDuplicates()
	 * @generated
	 */
	void setCheckForDuplicates(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Concurrent Rtc</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Concurrent Rtc</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Concurrent Rtc</em>' containment reference.
	 * @see #setConcurrentRtc(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getInferenceEngineConfig_ConcurrentRtc()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='concurrent-rtc' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getConcurrentRtc();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.InferenceEngineConfig#getConcurrentRtc <em>Concurrent Rtc</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Concurrent Rtc</em>' containment reference.
	 * @see #getConcurrentRtc()
	 * @generated
	 */
	void setConcurrentRtc(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Local Cache</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Local Cache</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Local Cache</em>' containment reference.
	 * @see #setLocalCache(LocalCacheConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getInferenceEngineConfig_LocalCache()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='local-cache' namespace='##targetNamespace'"
	 * @generated
	 */
	LocalCacheConfig getLocalCache();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.InferenceEngineConfig#getLocalCache <em>Local Cache</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Local Cache</em>' containment reference.
	 * @see #getLocalCache()
	 * @generated
	 */
	void setLocalCache(LocalCacheConfig value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getInferenceEngineConfig_PropertyGroup()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='property-group' namespace='##targetNamespace'"
	 * @generated
	 */
	PropertyGroupConfig getPropertyGroup();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.InferenceEngineConfig#getPropertyGroup <em>Property Group</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Group</em>' containment reference.
	 * @see #getPropertyGroup()
	 * @generated
	 */
	void setPropertyGroup(PropertyGroupConfig value);

} // InferenceEngineConfig
