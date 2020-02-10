/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cache Manager Security Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.CacheManagerSecurityConfig#getEnabled <em>Enabled</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CacheManagerSecurityConfig#getController <em>Controller</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CacheManagerSecurityConfig#getRequester <em>Requester</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getCacheManagerSecurityConfig()
 * @model extendedMetaData="name='cache-manager-security-config-type' kind='elementOnly'"
 * @generated
 */
public interface CacheManagerSecurityConfig extends SecurityConfig {
	/**
	 * Returns the value of the '<em><b>Enabled</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enabled</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enabled</em>' containment reference.
	 * @see #setEnabled(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCacheManagerSecurityConfig_Enabled()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='enabled' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getEnabled();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CacheManagerSecurityConfig#getEnabled <em>Enabled</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enabled</em>' containment reference.
	 * @see #getEnabled()
	 * @generated
	 */
	void setEnabled(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Controller</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Controller</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Controller</em>' containment reference.
	 * @see #setController(SecurityController)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCacheManagerSecurityConfig_Controller()
	 * @model containment="true"
	 *        extendedMetaData="name='controller' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	SecurityController getController();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CacheManagerSecurityConfig#getController <em>Controller</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Controller</em>' containment reference.
	 * @see #getController()
	 * @generated
	 */
	void setController(SecurityController value);

	/**
	 * Returns the value of the '<em><b>Requester</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Requester</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Requester</em>' containment reference.
	 * @see #setRequester(SecurityRequester)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCacheManagerSecurityConfig_Requester()
	 * @model containment="true"
	 *        extendedMetaData="name='requester' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	SecurityRequester getRequester();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CacheManagerSecurityConfig#getRequester <em>Requester</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Requester</em>' containment reference.
	 * @see #getRequester()
	 * @generated
	 */
	void setRequester(SecurityRequester value);

} // CacheManagerSecurityConfig
