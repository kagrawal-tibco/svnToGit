/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Local Cache Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.LocalCacheConfig#getEviction <em>Eviction</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getLocalCacheConfig()
 * @model extendedMetaData="name='local-cache-type' kind='elementOnly'"
 * @generated
 */
public interface LocalCacheConfig extends ArtifactConfig {
	/**
	 * Returns the value of the '<em><b>Eviction</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Eviction</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Eviction</em>' containment reference.
	 * @see #setEviction(EvictionConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLocalCacheConfig_Eviction()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='eviction' namespace='##targetNamespace'"
	 * @generated
	 */
	EvictionConfig getEviction();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LocalCacheConfig#getEviction <em>Eviction</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Eviction</em>' containment reference.
	 * @see #getEviction()
	 * @generated
	 */
	void setEviction(EvictionConfig value);

} // LocalCacheConfig
