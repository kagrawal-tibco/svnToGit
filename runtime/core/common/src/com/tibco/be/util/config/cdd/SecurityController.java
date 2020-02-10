/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Security Controller</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.SecurityController#getPolicyFile <em>Policy File</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.SecurityController#getIdentityPassword <em>Identity Password</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getSecurityController()
 * @model extendedMetaData="name='security-controller-type' kind='elementOnly'"
 * @generated
 */
public interface SecurityController extends EObject {
	/**
	 * Returns the value of the '<em><b>Policy File</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Policy File</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Policy File</em>' containment reference.
	 * @see #setPolicyFile(SecurityOverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getSecurityController_PolicyFile()
	 * @model containment="true"
	 *        extendedMetaData="name='policy-file' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	SecurityOverrideConfig getPolicyFile();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.SecurityController#getPolicyFile <em>Policy File</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Policy File</em>' containment reference.
	 * @see #getPolicyFile()
	 * @generated
	 */
	void setPolicyFile(SecurityOverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Identity Password</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Identity Password</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Identity Password</em>' containment reference.
	 * @see #setIdentityPassword(SecurityOverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getSecurityController_IdentityPassword()
	 * @model containment="true"
	 *        extendedMetaData="name='identity-password' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	SecurityOverrideConfig getIdentityPassword();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.SecurityController#getIdentityPassword <em>Identity Password</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Identity Password</em>' containment reference.
	 * @see #getIdentityPassword()
	 * @generated
	 */
	void setIdentityPassword(SecurityOverrideConfig value);

} // SecurityController
