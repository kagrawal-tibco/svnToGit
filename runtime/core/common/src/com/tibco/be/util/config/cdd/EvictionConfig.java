/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Eviction Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.EvictionConfig#getMaxSize <em>Max Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.EvictionConfig#getMaxTime <em>Max Time</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getEvictionConfig()
 * @model extendedMetaData="name='eviction-type' kind='elementOnly'"
 * @generated
 */
public interface EvictionConfig extends ArtifactConfig {
	/**
	 * Returns the value of the '<em><b>Max Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Size</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Size</em>' containment reference.
	 * @see #setMaxSize(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getEvictionConfig_MaxSize()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='max-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getMaxSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.EvictionConfig#getMaxSize <em>Max Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Size</em>' containment reference.
	 * @see #getMaxSize()
	 * @generated
	 */
	void setMaxSize(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Max Time</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Time</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Time</em>' containment reference.
	 * @see #setMaxTime(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getEvictionConfig_MaxTime()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='max-time' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getMaxTime();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.EvictionConfig#getMaxTime <em>Max Time</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Time</em>' containment reference.
	 * @see #getMaxTime()
	 * @generated
	 */
	void setMaxTime(OverrideConfig value);

} // EvictionConfig
