/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.aemetaservices.impl;

import com.tibco.be.util.config.sharedresources.aemetaservices.AemetaservicesPackage;
import com.tibco.be.util.config.sharedresources.aemetaservices.CertType;
import com.tibco.be.util.config.sharedresources.aemetaservices.IdentityType;
import com.tibco.be.util.config.sharedresources.aemetaservices.Ssl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ssl</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.sharedresources.aemetaservices.impl.SslImpl#getCert <em>Cert</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.aemetaservices.impl.SslImpl#getExpectedHostName <em>Expected Host Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.aemetaservices.impl.SslImpl#getDebugTrace <em>Debug Trace</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.aemetaservices.impl.SslImpl#getIdentity <em>Identity</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.aemetaservices.impl.SslImpl#getRequiresClientAuthentication <em>Requires Client Authentication</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.aemetaservices.impl.SslImpl#getStrongCipherSuitesOnly <em>Strong Cipher Suites Only</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.aemetaservices.impl.SslImpl#getTrace <em>Trace</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.aemetaservices.impl.SslImpl#getTrustStorePassword <em>Trust Store Password</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.aemetaservices.impl.SslImpl#getVerifyHostName <em>Verify Host Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SslImpl extends EObjectImpl implements Ssl {
	/**
     * The cached value of the '{@link #getCert() <em>Cert</em>}' containment reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getCert()
     * @generated
     * @ordered
     */
	protected CertType cert;

	/**
     * The default value of the '{@link #getExpectedHostName() <em>Expected Host Name</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getExpectedHostName()
     * @generated
     * @ordered
     */
	protected static final String EXPECTED_HOST_NAME_EDEFAULT = null;

	/**
     * The cached value of the '{@link #getExpectedHostName() <em>Expected Host Name</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getExpectedHostName()
     * @generated
     * @ordered
     */
	protected String expectedHostName = EXPECTED_HOST_NAME_EDEFAULT;

	/**
     * The default value of the '{@link #getDebugTrace() <em>Debug Trace</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getDebugTrace()
     * @generated
     * @ordered
     */
	protected static final Object DEBUG_TRACE_EDEFAULT = null;

	/**
     * The cached value of the '{@link #getDebugTrace() <em>Debug Trace</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getDebugTrace()
     * @generated
     * @ordered
     */
	protected Object debugTrace = DEBUG_TRACE_EDEFAULT;

	/**
     * The cached value of the '{@link #getIdentity() <em>Identity</em>}' containment reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getIdentity()
     * @generated
     * @ordered
     */
	protected IdentityType identity;

	/**
     * The default value of the '{@link #getRequiresClientAuthentication() <em>Requires Client Authentication</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getRequiresClientAuthentication()
     * @generated
     * @ordered
     */
	protected static final String REQUIRES_CLIENT_AUTHENTICATION_EDEFAULT = null;

	/**
     * The cached value of the '{@link #getRequiresClientAuthentication() <em>Requires Client Authentication</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getRequiresClientAuthentication()
     * @generated
     * @ordered
     */
	protected String requiresClientAuthentication = REQUIRES_CLIENT_AUTHENTICATION_EDEFAULT;

	/**
     * The default value of the '{@link #getStrongCipherSuitesOnly() <em>Strong Cipher Suites Only</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getStrongCipherSuitesOnly()
     * @generated
     * @ordered
     */
	protected static final Object STRONG_CIPHER_SUITES_ONLY_EDEFAULT = null;

	/**
     * The cached value of the '{@link #getStrongCipherSuitesOnly() <em>Strong Cipher Suites Only</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getStrongCipherSuitesOnly()
     * @generated
     * @ordered
     */
	protected Object strongCipherSuitesOnly = STRONG_CIPHER_SUITES_ONLY_EDEFAULT;

	/**
     * The default value of the '{@link #getTrace() <em>Trace</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getTrace()
     * @generated
     * @ordered
     */
	protected static final Object TRACE_EDEFAULT = null;

	/**
     * The cached value of the '{@link #getTrace() <em>Trace</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getTrace()
     * @generated
     * @ordered
     */
	protected Object trace = TRACE_EDEFAULT;

	/**
     * The default value of the '{@link #getTrustStorePassword() <em>Trust Store Password</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTrustStorePassword()
     * @generated
     * @ordered
     */
    protected static final String TRUST_STORE_PASSWORD_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTrustStorePassword() <em>Trust Store Password</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTrustStorePassword()
     * @generated
     * @ordered
     */
    protected String trustStorePassword = TRUST_STORE_PASSWORD_EDEFAULT;

    /**
     * The default value of the '{@link #getVerifyHostName() <em>Verify Host Name</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getVerifyHostName()
     * @generated
     * @ordered
     */
	protected static final Object VERIFY_HOST_NAME_EDEFAULT = null;

	/**
     * The cached value of the '{@link #getVerifyHostName() <em>Verify Host Name</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getVerifyHostName()
     * @generated
     * @ordered
     */
	protected Object verifyHostName = VERIFY_HOST_NAME_EDEFAULT;

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected SslImpl() {
        super();
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	protected EClass eStaticClass() {
        return AemetaservicesPackage.Literals.SSL;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public CertType getCert() {
        return cert;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public NotificationChain basicSetCert(CertType newCert, NotificationChain msgs) {
        CertType oldCert = cert;
        cert = newCert;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AemetaservicesPackage.SSL__CERT, oldCert, newCert);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setCert(CertType newCert) {
        if (newCert != cert)
        {
            NotificationChain msgs = null;
            if (cert != null)
                msgs = ((InternalEObject)cert).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AemetaservicesPackage.SSL__CERT, null, msgs);
            if (newCert != null)
                msgs = ((InternalEObject)newCert).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AemetaservicesPackage.SSL__CERT, null, msgs);
            msgs = basicSetCert(newCert, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, AemetaservicesPackage.SSL__CERT, newCert, newCert));
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public String getExpectedHostName() {
        return expectedHostName;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setExpectedHostName(String newExpectedHostName) {
        String oldExpectedHostName = expectedHostName;
        expectedHostName = newExpectedHostName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, AemetaservicesPackage.SSL__EXPECTED_HOST_NAME, oldExpectedHostName, expectedHostName));
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public Object getDebugTrace() {
        return debugTrace;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setDebugTrace(Object newDebugTrace) {
        Object oldDebugTrace = debugTrace;
        debugTrace = newDebugTrace;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, AemetaservicesPackage.SSL__DEBUG_TRACE, oldDebugTrace, debugTrace));
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public IdentityType getIdentity() {
        return identity;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public NotificationChain basicSetIdentity(IdentityType newIdentity, NotificationChain msgs) {
        IdentityType oldIdentity = identity;
        identity = newIdentity;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AemetaservicesPackage.SSL__IDENTITY, oldIdentity, newIdentity);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setIdentity(IdentityType newIdentity) {
        if (newIdentity != identity)
        {
            NotificationChain msgs = null;
            if (identity != null)
                msgs = ((InternalEObject)identity).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AemetaservicesPackage.SSL__IDENTITY, null, msgs);
            if (newIdentity != null)
                msgs = ((InternalEObject)newIdentity).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AemetaservicesPackage.SSL__IDENTITY, null, msgs);
            msgs = basicSetIdentity(newIdentity, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, AemetaservicesPackage.SSL__IDENTITY, newIdentity, newIdentity));
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public String getRequiresClientAuthentication() {
        return requiresClientAuthentication;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setRequiresClientAuthentication(String newRequiresClientAuthentication) {
        String oldRequiresClientAuthentication = requiresClientAuthentication;
        requiresClientAuthentication = newRequiresClientAuthentication;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, AemetaservicesPackage.SSL__REQUIRES_CLIENT_AUTHENTICATION, oldRequiresClientAuthentication, requiresClientAuthentication));
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public Object getStrongCipherSuitesOnly() {
        return strongCipherSuitesOnly;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setStrongCipherSuitesOnly(Object newStrongCipherSuitesOnly) {
        Object oldStrongCipherSuitesOnly = strongCipherSuitesOnly;
        strongCipherSuitesOnly = newStrongCipherSuitesOnly;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, AemetaservicesPackage.SSL__STRONG_CIPHER_SUITES_ONLY, oldStrongCipherSuitesOnly, strongCipherSuitesOnly));
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public Object getTrace() {
        return trace;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setTrace(Object newTrace) {
        Object oldTrace = trace;
        trace = newTrace;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, AemetaservicesPackage.SSL__TRACE, oldTrace, trace));
    }

	/**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getTrustStorePassword()
    {
        return trustStorePassword;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTrustStorePassword(String newTrustStorePassword)
    {
        String oldTrustStorePassword = trustStorePassword;
        trustStorePassword = newTrustStorePassword;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, AemetaservicesPackage.SSL__TRUST_STORE_PASSWORD, oldTrustStorePassword, trustStorePassword));
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public Object getVerifyHostName() {
        return verifyHostName;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setVerifyHostName(Object newVerifyHostName) {
        Object oldVerifyHostName = verifyHostName;
        verifyHostName = newVerifyHostName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, AemetaservicesPackage.SSL__VERIFY_HOST_NAME, oldVerifyHostName, verifyHostName));
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID)
        {
            case AemetaservicesPackage.SSL__CERT:
                return basicSetCert(null, msgs);
            case AemetaservicesPackage.SSL__IDENTITY:
                return basicSetIdentity(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID)
        {
            case AemetaservicesPackage.SSL__CERT:
                return getCert();
            case AemetaservicesPackage.SSL__EXPECTED_HOST_NAME:
                return getExpectedHostName();
            case AemetaservicesPackage.SSL__DEBUG_TRACE:
                return getDebugTrace();
            case AemetaservicesPackage.SSL__IDENTITY:
                return getIdentity();
            case AemetaservicesPackage.SSL__REQUIRES_CLIENT_AUTHENTICATION:
                return getRequiresClientAuthentication();
            case AemetaservicesPackage.SSL__STRONG_CIPHER_SUITES_ONLY:
                return getStrongCipherSuitesOnly();
            case AemetaservicesPackage.SSL__TRACE:
                return getTrace();
            case AemetaservicesPackage.SSL__TRUST_STORE_PASSWORD:
                return getTrustStorePassword();
            case AemetaservicesPackage.SSL__VERIFY_HOST_NAME:
                return getVerifyHostName();
        }
        return super.eGet(featureID, resolve, coreType);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public void eSet(int featureID, Object newValue) {
        switch (featureID)
        {
            case AemetaservicesPackage.SSL__CERT:
                setCert((CertType)newValue);
                return;
            case AemetaservicesPackage.SSL__EXPECTED_HOST_NAME:
                setExpectedHostName((String)newValue);
                return;
            case AemetaservicesPackage.SSL__DEBUG_TRACE:
                setDebugTrace(newValue);
                return;
            case AemetaservicesPackage.SSL__IDENTITY:
                setIdentity((IdentityType)newValue);
                return;
            case AemetaservicesPackage.SSL__REQUIRES_CLIENT_AUTHENTICATION:
                setRequiresClientAuthentication((String)newValue);
                return;
            case AemetaservicesPackage.SSL__STRONG_CIPHER_SUITES_ONLY:
                setStrongCipherSuitesOnly(newValue);
                return;
            case AemetaservicesPackage.SSL__TRACE:
                setTrace(newValue);
                return;
            case AemetaservicesPackage.SSL__TRUST_STORE_PASSWORD:
                setTrustStorePassword((String)newValue);
                return;
            case AemetaservicesPackage.SSL__VERIFY_HOST_NAME:
                setVerifyHostName(newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public void eUnset(int featureID) {
        switch (featureID)
        {
            case AemetaservicesPackage.SSL__CERT:
                setCert((CertType)null);
                return;
            case AemetaservicesPackage.SSL__EXPECTED_HOST_NAME:
                setExpectedHostName(EXPECTED_HOST_NAME_EDEFAULT);
                return;
            case AemetaservicesPackage.SSL__DEBUG_TRACE:
                setDebugTrace(DEBUG_TRACE_EDEFAULT);
                return;
            case AemetaservicesPackage.SSL__IDENTITY:
                setIdentity((IdentityType)null);
                return;
            case AemetaservicesPackage.SSL__REQUIRES_CLIENT_AUTHENTICATION:
                setRequiresClientAuthentication(REQUIRES_CLIENT_AUTHENTICATION_EDEFAULT);
                return;
            case AemetaservicesPackage.SSL__STRONG_CIPHER_SUITES_ONLY:
                setStrongCipherSuitesOnly(STRONG_CIPHER_SUITES_ONLY_EDEFAULT);
                return;
            case AemetaservicesPackage.SSL__TRACE:
                setTrace(TRACE_EDEFAULT);
                return;
            case AemetaservicesPackage.SSL__TRUST_STORE_PASSWORD:
                setTrustStorePassword(TRUST_STORE_PASSWORD_EDEFAULT);
                return;
            case AemetaservicesPackage.SSL__VERIFY_HOST_NAME:
                setVerifyHostName(VERIFY_HOST_NAME_EDEFAULT);
                return;
        }
        super.eUnset(featureID);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public boolean eIsSet(int featureID) {
        switch (featureID)
        {
            case AemetaservicesPackage.SSL__CERT:
                return cert != null;
            case AemetaservicesPackage.SSL__EXPECTED_HOST_NAME:
                return EXPECTED_HOST_NAME_EDEFAULT == null ? expectedHostName != null : !EXPECTED_HOST_NAME_EDEFAULT.equals(expectedHostName);
            case AemetaservicesPackage.SSL__DEBUG_TRACE:
                return DEBUG_TRACE_EDEFAULT == null ? debugTrace != null : !DEBUG_TRACE_EDEFAULT.equals(debugTrace);
            case AemetaservicesPackage.SSL__IDENTITY:
                return identity != null;
            case AemetaservicesPackage.SSL__REQUIRES_CLIENT_AUTHENTICATION:
                return REQUIRES_CLIENT_AUTHENTICATION_EDEFAULT == null ? requiresClientAuthentication != null : !REQUIRES_CLIENT_AUTHENTICATION_EDEFAULT.equals(requiresClientAuthentication);
            case AemetaservicesPackage.SSL__STRONG_CIPHER_SUITES_ONLY:
                return STRONG_CIPHER_SUITES_ONLY_EDEFAULT == null ? strongCipherSuitesOnly != null : !STRONG_CIPHER_SUITES_ONLY_EDEFAULT.equals(strongCipherSuitesOnly);
            case AemetaservicesPackage.SSL__TRACE:
                return TRACE_EDEFAULT == null ? trace != null : !TRACE_EDEFAULT.equals(trace);
            case AemetaservicesPackage.SSL__TRUST_STORE_PASSWORD:
                return TRUST_STORE_PASSWORD_EDEFAULT == null ? trustStorePassword != null : !TRUST_STORE_PASSWORD_EDEFAULT.equals(trustStorePassword);
            case AemetaservicesPackage.SSL__VERIFY_HOST_NAME:
                return VERIFY_HOST_NAME_EDEFAULT == null ? verifyHostName != null : !VERIFY_HOST_NAME_EDEFAULT.equals(verifyHostName);
        }
        return super.eIsSet(featureID);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (expectedHostName: ");
        result.append(expectedHostName);
        result.append(", debugTrace: ");
        result.append(debugTrace);
        result.append(", requiresClientAuthentication: ");
        result.append(requiresClientAuthentication);
        result.append(", strongCipherSuitesOnly: ");
        result.append(strongCipherSuitesOnly);
        result.append(", trace: ");
        result.append(trace);
        result.append(", trustStorePassword: ");
        result.append(trustStorePassword);
        result.append(", verifyHostName: ");
        result.append(verifyHostName);
        result.append(')');
        return result.toString();
    }

} //SslImpl
