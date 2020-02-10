/**
 */
package com.tibco.be.util.config.cdd;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Domain Object Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getBackingStore <em>Backing Store</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getCacheLimited <em>Cache Limited</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getCheckForVersion <em>Check For Version</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getConstant <em>Constant</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getEnableTracking <em>Enable Tracking</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getEncryption <em>Encryption</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getEvictOnUpdate <em>Evict On Update</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getIndexes <em>Indexes</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getMode <em>Mode</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getPreLoadEnabled <em>Pre Load Enabled</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getPreLoadFetchSize <em>Pre Load Fetch Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getPreLoadHandles <em>Pre Load Handles</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getPreProcessor <em>Pre Processor</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getSubscribe <em>Subscribe</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getUri <em>Uri</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getConceptTTL <em>Concept TTL</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getCompositeIndexes <em>Composite Indexes</em>}</li>
 * </ul>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectConfig()
 * @model extendedMetaData="name='domain-object-type' kind='elementOnly'"
 * @generated
 */
public interface DomainObjectConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Backing Store</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Backing Store</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Backing Store</em>' containment reference.
	 * @see #setBackingStore(BackingStoreForDomainObjectConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectConfig_BackingStore()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='backing-store' namespace='##targetNamespace'"
	 * @generated
	 */
	BackingStoreForDomainObjectConfig getBackingStore();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getBackingStore <em>Backing Store</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Backing Store</em>' containment reference.
	 * @see #getBackingStore()
	 * @generated
	 */
	void setBackingStore(BackingStoreForDomainObjectConfig value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectConfig_CacheLimited()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='cache-limited' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getCacheLimited();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getCacheLimited <em>Cache Limited</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache Limited</em>' containment reference.
	 * @see #getCacheLimited()
	 * @generated
	 */
	void setCacheLimited(OverrideConfig value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectConfig_CheckForVersion()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='check-for-version' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getCheckForVersion();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getCheckForVersion <em>Check For Version</em>}' containment reference.
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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectConfig_Constant()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='constant' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getConstant();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getConstant <em>Constant</em>}' containment reference.
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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectConfig_EnableTracking()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='enable-tracking' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getEnableTracking();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getEnableTracking <em>Enable Tracking</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enable Tracking</em>' containment reference.
	 * @see #getEnableTracking()
	 * @generated
	 */
	void setEnableTracking(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Encryption</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Encryption</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Encryption</em>' containment reference.
	 * @see #setEncryption(FieldEncryptionConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectConfig_Encryption()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='encryption' namespace='##targetNamespace'"
	 * @generated
	 */
	FieldEncryptionConfig getEncryption();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getEncryption <em>Encryption</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Encryption</em>' containment reference.
	 * @see #getEncryption()
	 * @generated
	 */
	void setEncryption(FieldEncryptionConfig value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectConfig_EvictOnUpdate()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='evict-on-update' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getEvictOnUpdate();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getEvictOnUpdate <em>Evict On Update</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Evict On Update</em>' containment reference.
	 * @see #getEvictOnUpdate()
	 * @generated
	 */
	void setEvictOnUpdate(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Indexes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Indexes</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Indexes</em>' containment reference.
	 * @see #setIndexes(IndexesConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectConfig_Indexes()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='indexes' namespace='##targetNamespace'"
	 * @generated
	 */
	IndexesConfig getIndexes();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getIndexes <em>Indexes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Indexes</em>' containment reference.
	 * @see #getIndexes()
	 * @generated
	 */
	void setIndexes(IndexesConfig value);

	/**
	 * Returns the value of the '<em><b>Mode</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.be.util.config.cdd.DomainObjectModeConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mode</em>' attribute.
	 * @see com.tibco.be.util.config.cdd.DomainObjectModeConfig
	 * @see #isSetMode()
	 * @see #unsetMode()
	 * @see #setMode(DomainObjectModeConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectConfig_Mode()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='element' name='mode' namespace='##targetNamespace'"
	 * @generated
	 */
	DomainObjectModeConfig getMode();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getMode <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mode</em>' attribute.
	 * @see com.tibco.be.util.config.cdd.DomainObjectModeConfig
	 * @see #isSetMode()
	 * @see #unsetMode()
	 * @see #getMode()
	 * @generated
	 */
	void setMode(DomainObjectModeConfig value);

	/**
	 * Unsets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getMode <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMode()
	 * @see #getMode()
	 * @see #setMode(DomainObjectModeConfig)
	 * @generated
	 */
	void unsetMode();

	/**
	 * Returns whether the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getMode <em>Mode</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Mode</em>' attribute is set.
	 * @see #unsetMode()
	 * @see #getMode()
	 * @see #setMode(DomainObjectModeConfig)
	 * @generated
	 */
	boolean isSetMode();

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectConfig_PreLoadEnabled()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='pre-load-enabled' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getPreLoadEnabled();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getPreLoadEnabled <em>Pre Load Enabled</em>}' containment reference.
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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectConfig_PreLoadFetchSize()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='pre-load-fetch-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getPreLoadFetchSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getPreLoadFetchSize <em>Pre Load Fetch Size</em>}' containment reference.
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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectConfig_PreLoadHandles()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='pre-load-handles' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getPreLoadHandles();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getPreLoadHandles <em>Pre Load Handles</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Load Handles</em>' containment reference.
	 * @see #getPreLoadHandles()
	 * @generated
	 */
	void setPreLoadHandles(OverrideConfig value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectConfig_PreProcessor()
	 * @model dataType="com.tibco.be.util.config.cdd.OntologyUriConfig"
	 *        extendedMetaData="kind='element' name='pre-processor' namespace='##targetNamespace'"
	 * @generated
	 */
	String getPreProcessor();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getPreProcessor <em>Pre Processor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Processor</em>' attribute.
	 * @see #getPreProcessor()
	 * @generated
	 */
	void setPreProcessor(String value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectConfig_Subscribe()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='subscribe' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getSubscribe();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getSubscribe <em>Subscribe</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subscribe</em>' containment reference.
	 * @see #getSubscribe()
	 * @generated
	 */
	void setSubscribe(OverrideConfig value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectConfig_Uri()
	 * @model dataType="com.tibco.be.util.config.cdd.OntologyUriConfig" required="true"
	 *        extendedMetaData="kind='element' name='uri' namespace='##targetNamespace'"
	 * @generated
	 */
	String getUri();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getUri <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uri</em>' attribute.
	 * @see #getUri()
	 * @generated
	 */
	void setUri(String value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectConfig_ConceptTTL()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='concept-ttl' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getConceptTTL();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getConceptTTL <em>Concept TTL</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Concept TTL</em>' containment reference.
	 * @see #getConceptTTL()
	 * @generated
	 */
	void setConceptTTL(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Composite Indexes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Composite Indexes</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Composite Indexes</em>' containment reference.
	 * @see #setCompositeIndexes(CompositeIndexesConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDomainObjectConfig_CompositeIndexes()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='composite-indexes' namespace='##targetNamespace'"
	 * @generated
	 */
	CompositeIndexesConfig getCompositeIndexes();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DomainObjectConfig#getCompositeIndexes <em>Composite Indexes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Composite Indexes</em>' containment reference.
	 * @see #getCompositeIndexes()
	 * @generated
	 */
	void setCompositeIndexes(CompositeIndexesConfig value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='return new java.util.Properties();'"
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // DomainObjectConfig
