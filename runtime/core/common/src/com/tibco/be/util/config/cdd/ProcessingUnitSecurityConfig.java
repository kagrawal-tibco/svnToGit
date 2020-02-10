/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Processing Unit Security Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.ProcessingUnitSecurityConfig#getRole <em>Role</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ProcessingUnitSecurityConfig#getController <em>Controller</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ProcessingUnitSecurityConfig#getRequester <em>Requester</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessingUnitSecurityConfig()
 * @model extendedMetaData="name='processing-unit-security-config-type' kind='elementOnly'"
 * @generated
 */
public interface ProcessingUnitSecurityConfig extends SecurityConfig {
	/**
	 * Returns the value of the '<em><b>Role</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Role</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Role</em>' containment reference.
	 * @see #setRole(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessingUnitSecurityConfig_Role()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='role' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getRole();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ProcessingUnitSecurityConfig#getRole <em>Role</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Role</em>' containment reference.
	 * @see #getRole()
	 * @generated
	 */
	void setRole(OverrideConfig value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessingUnitSecurityConfig_Controller()
	 * @model containment="true"
	 *        extendedMetaData="name='controller' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	SecurityController getController();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ProcessingUnitSecurityConfig#getController <em>Controller</em>}' containment reference.
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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessingUnitSecurityConfig_Requester()
	 * @model containment="true"
	 *        extendedMetaData="name='requester' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	SecurityRequester getRequester();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ProcessingUnitSecurityConfig#getRequester <em>Requester</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Requester</em>' containment reference.
	 * @see #getRequester()
	 * @generated
	 */
	void setRequester(SecurityRequester value);

} // ProcessingUnitSecurityConfig
