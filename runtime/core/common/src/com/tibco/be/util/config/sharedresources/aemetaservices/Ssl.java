/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.aemetaservices;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ssl</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getCert <em>Cert</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getExpectedHostName <em>Expected Host Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getDebugTrace <em>Debug Trace</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getIdentity <em>Identity</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getRequiresClientAuthentication <em>Requires Client Authentication</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getStrongCipherSuitesOnly <em>Strong Cipher Suites Only</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getTrace <em>Trace</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getTrustStorePassword <em>Trust Store Password</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getVerifyHostName <em>Verify Host Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.sharedresources.aemetaservices.AemetaservicesPackage#getSsl()
 * @model extendedMetaData="name='sslType' kind='elementOnly'"
 * @generated
 */
public interface Ssl extends EObject {
	/**
     * Returns the value of the '<em><b>Cert</b></em>' containment reference.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cert</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>Cert</em>' containment reference.
     * @see #setCert(CertType)
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.AemetaservicesPackage#getSsl_Cert()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='cert'"
     * @generated
     */
	CertType getCert();

	/**
     * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getCert <em>Cert</em>}' containment reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Cert</em>' containment reference.
     * @see #getCert()
     * @generated
     */
	void setCert(CertType value);

	/**
     * Returns the value of the '<em><b>Expected Host Name</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expected Host Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>Expected Host Name</em>' attribute.
     * @see #setExpectedHostName(String)
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.AemetaservicesPackage#getSsl_ExpectedHostName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='expectedHostName'"
     * @generated
     */
	String getExpectedHostName();

	/**
     * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getExpectedHostName <em>Expected Host Name</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Expected Host Name</em>' attribute.
     * @see #getExpectedHostName()
     * @generated
     */
	void setExpectedHostName(String value);

	/**
     * Returns the value of the '<em><b>Debug Trace</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Debug Trace</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>Debug Trace</em>' attribute.
     * @see #setDebugTrace(Object)
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.AemetaservicesPackage#getSsl_DebugTrace()
     * @model dataType="com.tibco.be.util.config.sharedresources.basetypes.BooleanOrGvs"
     *        extendedMetaData="kind='element' name='debugTrace'"
     * @generated
     */
	Object getDebugTrace();

	/**
     * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getDebugTrace <em>Debug Trace</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Debug Trace</em>' attribute.
     * @see #getDebugTrace()
     * @generated
     */
	void setDebugTrace(Object value);

	/**
     * Returns the value of the '<em><b>Identity</b></em>' containment reference.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Identity</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>Identity</em>' containment reference.
     * @see #setIdentity(IdentityType)
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.AemetaservicesPackage#getSsl_Identity()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='identity'"
     * @generated
     */
	IdentityType getIdentity();

	/**
     * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getIdentity <em>Identity</em>}' containment reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Identity</em>' containment reference.
     * @see #getIdentity()
     * @generated
     */
	void setIdentity(IdentityType value);

	/**
     * Returns the value of the '<em><b>Requires Client Authentication</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Requires Client Authentication</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>Requires Client Authentication</em>' attribute.
     * @see #setRequiresClientAuthentication(String)
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.AemetaservicesPackage#getSsl_RequiresClientAuthentication()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='requiresClientAuthentication'"
     * @generated
     */
	String getRequiresClientAuthentication();

	/**
     * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getRequiresClientAuthentication <em>Requires Client Authentication</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Requires Client Authentication</em>' attribute.
     * @see #getRequiresClientAuthentication()
     * @generated
     */
	void setRequiresClientAuthentication(String value);

	/**
     * Returns the value of the '<em><b>Strong Cipher Suites Only</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Strong Cipher Suites Only</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>Strong Cipher Suites Only</em>' attribute.
     * @see #setStrongCipherSuitesOnly(Object)
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.AemetaservicesPackage#getSsl_StrongCipherSuitesOnly()
     * @model dataType="com.tibco.be.util.config.sharedresources.basetypes.BooleanOrGvs"
     *        extendedMetaData="kind='element' name='strongCipherSuitesOnly'"
     * @generated
     */
	Object getStrongCipherSuitesOnly();

	/**
     * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getStrongCipherSuitesOnly <em>Strong Cipher Suites Only</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Strong Cipher Suites Only</em>' attribute.
     * @see #getStrongCipherSuitesOnly()
     * @generated
     */
	void setStrongCipherSuitesOnly(Object value);

	/**
     * Returns the value of the '<em><b>Trace</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Trace</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>Trace</em>' attribute.
     * @see #setTrace(Object)
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.AemetaservicesPackage#getSsl_Trace()
     * @model dataType="com.tibco.be.util.config.sharedresources.basetypes.BooleanOrGvs"
     *        extendedMetaData="kind='element' name='trace'"
     * @generated
     */
	Object getTrace();

	/**
     * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getTrace <em>Trace</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Trace</em>' attribute.
     * @see #getTrace()
     * @generated
     */
	void setTrace(Object value);

	/**
     * Returns the value of the '<em><b>Trust Store Password</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Trust Store Password</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Trust Store Password</em>' attribute.
     * @see #setTrustStorePassword(String)
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.AemetaservicesPackage#getSsl_TrustStorePassword()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='trustStorePassword'"
     * @generated
     */
    String getTrustStorePassword();

    /**
     * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getTrustStorePassword <em>Trust Store Password</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Trust Store Password</em>' attribute.
     * @see #getTrustStorePassword()
     * @generated
     */
    void setTrustStorePassword(String value);

    /**
     * Returns the value of the '<em><b>Verify Host Name</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Verify Host Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>Verify Host Name</em>' attribute.
     * @see #setVerifyHostName(Object)
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.AemetaservicesPackage#getSsl_VerifyHostName()
     * @model dataType="com.tibco.be.util.config.sharedresources.basetypes.BooleanOrGvs"
     *        extendedMetaData="kind='element' name='verifyHostName'"
     * @generated
     */
	Object getVerifyHostName();

	/**
     * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl#getVerifyHostName <em>Verify Host Name</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Verify Host Name</em>' attribute.
     * @see #getVerifyHostName()
     * @generated
     */
	void setVerifyHostName(Object value);

} // Ssl
