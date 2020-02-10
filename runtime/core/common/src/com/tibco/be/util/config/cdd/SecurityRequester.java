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
 * A representation of the model object '<em><b>Security Requester</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.SecurityRequester#getTokenFile <em>Token File</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.SecurityRequester#getIdentityPassword <em>Identity Password</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.SecurityRequester#getCertificateKeyFile <em>Certificate Key File</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.SecurityRequester#getDomainName <em>Domain Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.SecurityRequester#getUserName <em>User Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.SecurityRequester#getUserPassword <em>User Password</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getSecurityRequester()
 * @model extendedMetaData="name='security-requester-type' kind='elementOnly'"
 * @generated
 */
public interface SecurityRequester extends EObject {
	/**
	 * Returns the value of the '<em><b>Token File</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Token File</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Token File</em>' containment reference.
	 * @see #setTokenFile(SecurityOverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getSecurityRequester_TokenFile()
	 * @model containment="true"
	 *        extendedMetaData="name='token-file' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	SecurityOverrideConfig getTokenFile();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.SecurityRequester#getTokenFile <em>Token File</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Token File</em>' containment reference.
	 * @see #getTokenFile()
	 * @generated
	 */
	void setTokenFile(SecurityOverrideConfig value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getSecurityRequester_IdentityPassword()
	 * @model containment="true"
	 *        extendedMetaData="name='identity-password' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	SecurityOverrideConfig getIdentityPassword();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.SecurityRequester#getIdentityPassword <em>Identity Password</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Identity Password</em>' containment reference.
	 * @see #getIdentityPassword()
	 * @generated
	 */
	void setIdentityPassword(SecurityOverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Certificate Key File</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Certificate Key File</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Certificate Key File</em>' containment reference.
	 * @see #setCertificateKeyFile(SecurityOverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getSecurityRequester_CertificateKeyFile()
	 * @model containment="true"
	 *        extendedMetaData="name='certificate-key-file' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	SecurityOverrideConfig getCertificateKeyFile();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.SecurityRequester#getCertificateKeyFile <em>Certificate Key File</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Certificate Key File</em>' containment reference.
	 * @see #getCertificateKeyFile()
	 * @generated
	 */
	void setCertificateKeyFile(SecurityOverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Domain Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain Name</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain Name</em>' containment reference.
	 * @see #setDomainName(SecurityOverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getSecurityRequester_DomainName()
	 * @model containment="true"
	 *        extendedMetaData="name='domain-name' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	SecurityOverrideConfig getDomainName();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.SecurityRequester#getDomainName <em>Domain Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain Name</em>' containment reference.
	 * @see #getDomainName()
	 * @generated
	 */
	void setDomainName(SecurityOverrideConfig value);

	/**
	 * Returns the value of the '<em><b>User Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User Name</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Name</em>' containment reference.
	 * @see #setUserName(SecurityOverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getSecurityRequester_UserName()
	 * @model containment="true"
	 *        extendedMetaData="name='user-name' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	SecurityOverrideConfig getUserName();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.SecurityRequester#getUserName <em>User Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User Name</em>' containment reference.
	 * @see #getUserName()
	 * @generated
	 */
	void setUserName(SecurityOverrideConfig value);

	/**
	 * Returns the value of the '<em><b>User Password</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User Password</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Password</em>' containment reference.
	 * @see #setUserPassword(SecurityOverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getSecurityRequester_UserPassword()
	 * @model containment="true"
	 *        extendedMetaData="name='user-password' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	SecurityOverrideConfig getUserPassword();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.SecurityRequester#getUserPassword <em>User Password</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User Password</em>' containment reference.
	 * @see #getUserPassword()
	 * @generated
	 */
	void setUserPassword(SecurityOverrideConfig value);

} // SecurityRequester
