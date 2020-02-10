/**
 */
package com.tibco.be.util.config.cdd;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Domain Objects Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getDefaultMode <em>Default Mode</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getCheckForVersion <em>Check For Version</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getConstant <em>Constant</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getEnableTracking <em>Enable Tracking</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getEvictOnUpdate <em>Evict On Update</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getCacheLimited <em>Cache Limited</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getSubscribe <em>Subscribe</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getPreLoadEnabled <em>Pre Load Enabled</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getPreLoadFetchSize <em>Pre Load Fetch Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getPreLoadHandles <em>Pre Load Handles</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getDomainObject <em>Domain Object</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getConceptTTL <em>Concept TTL</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectsConfig()
 * @model extendedMetaData="name='domain-objects-type' kind='elementOnly'"
 * @generated
 */
public interface DomainObjectsConfig extends ArtifactConfig {
	/**
	 * Returns the value of the '<em><b>Default Mode</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.be.util.config.cdd.DomainObjectModeConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Mode</em>' attribute.
	 * @see com.tibco.be.util.config.cdd.DomainObjectModeConfig
	 * @see #isSetDefaultMode()
	 * @see #unsetDefaultMode()
	 * @see #setDefaultMode(DomainObjectModeConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectsConfig_DefaultMode()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='element' name='default-mode' namespace='##targetNamespace'"
	 * @generated
	 */
	DomainObjectModeConfig getDefaultMode();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getDefaultMode <em>Default Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Mode</em>' attribute.
	 * @see com.tibco.be.util.config.cdd.DomainObjectModeConfig
	 * @see #isSetDefaultMode()
	 * @see #unsetDefaultMode()
	 * @see #getDefaultMode()
	 * @generated
	 */
	void setDefaultMode(DomainObjectModeConfig value);

	/**
	 * Unsets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getDefaultMode <em>Default Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDefaultMode()
	 * @see #getDefaultMode()
	 * @see #setDefaultMode(DomainObjectModeConfig)
	 * @generated
	 */
	void unsetDefaultMode();

	/**
	 * Returns whether the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getDefaultMode <em>Default Mode</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Default Mode</em>' attribute is set.
	 * @see #unsetDefaultMode()
	 * @see #getDefaultMode()
	 * @see #setDefaultMode(DomainObjectModeConfig)
	 * @generated
	 */
	boolean isSetDefaultMode();

	/**
	 * Returns the value of the '<em><b>Check For Version</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Check For Version</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Check For Version</em>' containment reference.
	 * @see #setCheckForVersion(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectsConfig_CheckForVersion()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='check-for-version' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getCheckForVersion();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getCheckForVersion <em>Check For Version</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Check For Version</em>' containment reference.
	 * @see #getCheckForVersion()
	 * @generated
	 */
	void setCheckForVersion(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Constant</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constant</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constant</em>' containment reference.
	 * @see #setConstant(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectsConfig_Constant()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='constant' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getConstant();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getConstant <em>Constant</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Constant</em>' containment reference.
	 * @see #getConstant()
	 * @generated
	 */
	void setConstant(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Enable Tracking</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enable Tracking</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enable Tracking</em>' containment reference.
	 * @see #setEnableTracking(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectsConfig_EnableTracking()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='enable-tracking' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getEnableTracking();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getEnableTracking <em>Enable Tracking</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enable Tracking</em>' containment reference.
	 * @see #getEnableTracking()
	 * @generated
	 */
	void setEnableTracking(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Evict On Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Evict On Update</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Evict On Update</em>' containment reference.
	 * @see #setEvictOnUpdate(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectsConfig_EvictOnUpdate()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='evict-on-update' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getEvictOnUpdate();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getEvictOnUpdate <em>Evict On Update</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Evict On Update</em>' containment reference.
	 * @see #getEvictOnUpdate()
	 * @generated
	 */
	void setEvictOnUpdate(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Cache Limited</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache Limited</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cache Limited</em>' containment reference.
	 * @see #setCacheLimited(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectsConfig_CacheLimited()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='cache-limited' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getCacheLimited();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getCacheLimited <em>Cache Limited</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache Limited</em>' containment reference.
	 * @see #getCacheLimited()
	 * @generated
	 */
	void setCacheLimited(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Subscribe</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subscribe</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subscribe</em>' containment reference.
	 * @see #setSubscribe(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectsConfig_Subscribe()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='subscribe' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getSubscribe();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getSubscribe <em>Subscribe</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subscribe</em>' containment reference.
	 * @see #getSubscribe()
	 * @generated
	 */
	void setSubscribe(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Pre Load Enabled</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pre Load Enabled</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre Load Enabled</em>' containment reference.
	 * @see #setPreLoadEnabled(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectsConfig_PreLoadEnabled()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='pre-load-enabled' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getPreLoadEnabled();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getPreLoadEnabled <em>Pre Load Enabled</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Load Enabled</em>' containment reference.
	 * @see #getPreLoadEnabled()
	 * @generated
	 */
	void setPreLoadEnabled(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Pre Load Fetch Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pre Load Fetch Size</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre Load Fetch Size</em>' containment reference.
	 * @see #setPreLoadFetchSize(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectsConfig_PreLoadFetchSize()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='pre-load-fetch-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getPreLoadFetchSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getPreLoadFetchSize <em>Pre Load Fetch Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Load Fetch Size</em>' containment reference.
	 * @see #getPreLoadFetchSize()
	 * @generated
	 */
	void setPreLoadFetchSize(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Pre Load Handles</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pre Load Handles</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre Load Handles</em>' containment reference.
	 * @see #setPreLoadHandles(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectsConfig_PreLoadHandles()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='pre-load-handles' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getPreLoadHandles();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getPreLoadHandles <em>Pre Load Handles</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Load Handles</em>' containment reference.
	 * @see #getPreLoadHandles()
	 * @generated
	 */
	void setPreLoadHandles(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Domain Object</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.DomainObjectConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain Object</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain Object</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectsConfig_DomainObject()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='domain-object' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<DomainObjectConfig> getDomainObject();

	/**
	 * Returns the value of the '<em><b>Concept TTL</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Concept TTL</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Concept TTL</em>' containment reference.
	 * @see #setConceptTTL(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectsConfig_ConceptTTL()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='concept-ttl' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getConceptTTL();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectsConfig#getConceptTTL <em>Concept TTL</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Concept TTL</em>' containment reference.
	 * @see #getConceptTTL()
	 * @generated
	 */
	void setConceptTTL(OverrideConfig value);

/**
	 * @generated NOT
	 */
    public Boolean getCacheLimited(String uri);
    

	/**
	 * @generated NOT
	 */
    public Boolean getCheckForVersion(String uri);

	/**
	 * @generated NOT
	 */
    public Boolean getConstant(String uri);


	/**
	 * @generated NOT
	 */
    public Boolean getEnableTracking(String uri);

	/**
	 * @generated NOT
	 */
    public Boolean getEvictOnUpdate(String uri);


	/**
	 * @generated NOT
	 */
    public String getMode(String uri);

    
	/**
	 * @generated NOT
	 */
    public Boolean getPreLoadEnabled(String uri);


	/**
	 * @generated NOT
	 */
    public Long getPreLoadFetchSize(String uri);
    

    /**
	 * @generated NOT
	 */
    public Boolean getPreLoadHandles(String uri);
    

	/**
	 * @generated NOT
	 */
    public String getPreprocessor(String uri);


	/**
	 * @generated NOT
	 */
    public Boolean getSubscribe(String uri);

} // DomainObjectsConfig
