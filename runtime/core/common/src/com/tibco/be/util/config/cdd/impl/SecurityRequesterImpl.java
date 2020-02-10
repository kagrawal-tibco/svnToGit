/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.SecurityOverrideConfig;
import com.tibco.be.util.config.cdd.SecurityRequester;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Security Requester</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.SecurityRequesterImpl#getTokenFile <em>Token File</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.SecurityRequesterImpl#getIdentityPassword <em>Identity Password</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.SecurityRequesterImpl#getCertificateKeyFile <em>Certificate Key File</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.SecurityRequesterImpl#getDomainName <em>Domain Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.SecurityRequesterImpl#getUserName <em>User Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.SecurityRequesterImpl#getUserPassword <em>User Password</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SecurityRequesterImpl extends EObjectImpl implements SecurityRequester {
	/**
	 * The cached value of the '{@link #getTokenFile() <em>Token File</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTokenFile()
	 * @generated
	 * @ordered
	 */
	protected SecurityOverrideConfig tokenFile;

	/**
	 * The cached value of the '{@link #getIdentityPassword() <em>Identity Password</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdentityPassword()
	 * @generated
	 * @ordered
	 */
	protected SecurityOverrideConfig identityPassword;

	/**
	 * The cached value of the '{@link #getCertificateKeyFile() <em>Certificate Key File</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCertificateKeyFile()
	 * @generated
	 * @ordered
	 */
	protected SecurityOverrideConfig certificateKeyFile;

	/**
	 * The cached value of the '{@link #getDomainName() <em>Domain Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomainName()
	 * @generated
	 * @ordered
	 */
	protected SecurityOverrideConfig domainName;

	/**
	 * The cached value of the '{@link #getUserName() <em>User Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUserName()
	 * @generated
	 * @ordered
	 */
	protected SecurityOverrideConfig userName;

	/**
	 * The cached value of the '{@link #getUserPassword() <em>User Password</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUserPassword()
	 * @generated
	 * @ordered
	 */
	protected SecurityOverrideConfig userPassword;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SecurityRequesterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getSecurityRequester();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityOverrideConfig getTokenFile() {
		return tokenFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTokenFile(SecurityOverrideConfig newTokenFile, NotificationChain msgs) {
		SecurityOverrideConfig oldTokenFile = tokenFile;
		tokenFile = newTokenFile;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.SECURITY_REQUESTER__TOKEN_FILE, oldTokenFile, newTokenFile);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTokenFile(SecurityOverrideConfig newTokenFile) {
		if (newTokenFile != tokenFile) {
			NotificationChain msgs = null;
			if (tokenFile != null)
				msgs = ((InternalEObject)tokenFile).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.SECURITY_REQUESTER__TOKEN_FILE, null, msgs);
			if (newTokenFile != null)
				msgs = ((InternalEObject)newTokenFile).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.SECURITY_REQUESTER__TOKEN_FILE, null, msgs);
			msgs = basicSetTokenFile(newTokenFile, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.SECURITY_REQUESTER__TOKEN_FILE, newTokenFile, newTokenFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityOverrideConfig getIdentityPassword() {
		return identityPassword;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIdentityPassword(SecurityOverrideConfig newIdentityPassword, NotificationChain msgs) {
		SecurityOverrideConfig oldIdentityPassword = identityPassword;
		identityPassword = newIdentityPassword;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.SECURITY_REQUESTER__IDENTITY_PASSWORD, oldIdentityPassword, newIdentityPassword);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIdentityPassword(SecurityOverrideConfig newIdentityPassword) {
		if (newIdentityPassword != identityPassword) {
			NotificationChain msgs = null;
			if (identityPassword != null)
				msgs = ((InternalEObject)identityPassword).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.SECURITY_REQUESTER__IDENTITY_PASSWORD, null, msgs);
			if (newIdentityPassword != null)
				msgs = ((InternalEObject)newIdentityPassword).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.SECURITY_REQUESTER__IDENTITY_PASSWORD, null, msgs);
			msgs = basicSetIdentityPassword(newIdentityPassword, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.SECURITY_REQUESTER__IDENTITY_PASSWORD, newIdentityPassword, newIdentityPassword));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityOverrideConfig getCertificateKeyFile() {
		return certificateKeyFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCertificateKeyFile(SecurityOverrideConfig newCertificateKeyFile, NotificationChain msgs) {
		SecurityOverrideConfig oldCertificateKeyFile = certificateKeyFile;
		certificateKeyFile = newCertificateKeyFile;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.SECURITY_REQUESTER__CERTIFICATE_KEY_FILE, oldCertificateKeyFile, newCertificateKeyFile);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCertificateKeyFile(SecurityOverrideConfig newCertificateKeyFile) {
		if (newCertificateKeyFile != certificateKeyFile) {
			NotificationChain msgs = null;
			if (certificateKeyFile != null)
				msgs = ((InternalEObject)certificateKeyFile).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.SECURITY_REQUESTER__CERTIFICATE_KEY_FILE, null, msgs);
			if (newCertificateKeyFile != null)
				msgs = ((InternalEObject)newCertificateKeyFile).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.SECURITY_REQUESTER__CERTIFICATE_KEY_FILE, null, msgs);
			msgs = basicSetCertificateKeyFile(newCertificateKeyFile, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.SECURITY_REQUESTER__CERTIFICATE_KEY_FILE, newCertificateKeyFile, newCertificateKeyFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityOverrideConfig getDomainName() {
		return domainName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDomainName(SecurityOverrideConfig newDomainName, NotificationChain msgs) {
		SecurityOverrideConfig oldDomainName = domainName;
		domainName = newDomainName;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.SECURITY_REQUESTER__DOMAIN_NAME, oldDomainName, newDomainName);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDomainName(SecurityOverrideConfig newDomainName) {
		if (newDomainName != domainName) {
			NotificationChain msgs = null;
			if (domainName != null)
				msgs = ((InternalEObject)domainName).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.SECURITY_REQUESTER__DOMAIN_NAME, null, msgs);
			if (newDomainName != null)
				msgs = ((InternalEObject)newDomainName).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.SECURITY_REQUESTER__DOMAIN_NAME, null, msgs);
			msgs = basicSetDomainName(newDomainName, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.SECURITY_REQUESTER__DOMAIN_NAME, newDomainName, newDomainName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityOverrideConfig getUserName() {
		return userName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUserName(SecurityOverrideConfig newUserName, NotificationChain msgs) {
		SecurityOverrideConfig oldUserName = userName;
		userName = newUserName;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.SECURITY_REQUESTER__USER_NAME, oldUserName, newUserName);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUserName(SecurityOverrideConfig newUserName) {
		if (newUserName != userName) {
			NotificationChain msgs = null;
			if (userName != null)
				msgs = ((InternalEObject)userName).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.SECURITY_REQUESTER__USER_NAME, null, msgs);
			if (newUserName != null)
				msgs = ((InternalEObject)newUserName).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.SECURITY_REQUESTER__USER_NAME, null, msgs);
			msgs = basicSetUserName(newUserName, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.SECURITY_REQUESTER__USER_NAME, newUserName, newUserName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityOverrideConfig getUserPassword() {
		return userPassword;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUserPassword(SecurityOverrideConfig newUserPassword, NotificationChain msgs) {
		SecurityOverrideConfig oldUserPassword = userPassword;
		userPassword = newUserPassword;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.SECURITY_REQUESTER__USER_PASSWORD, oldUserPassword, newUserPassword);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUserPassword(SecurityOverrideConfig newUserPassword) {
		if (newUserPassword != userPassword) {
			NotificationChain msgs = null;
			if (userPassword != null)
				msgs = ((InternalEObject)userPassword).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.SECURITY_REQUESTER__USER_PASSWORD, null, msgs);
			if (newUserPassword != null)
				msgs = ((InternalEObject)newUserPassword).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.SECURITY_REQUESTER__USER_PASSWORD, null, msgs);
			msgs = basicSetUserPassword(newUserPassword, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.SECURITY_REQUESTER__USER_PASSWORD, newUserPassword, newUserPassword));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.SECURITY_REQUESTER__TOKEN_FILE:
				return basicSetTokenFile(null, msgs);
			case CddPackage.SECURITY_REQUESTER__IDENTITY_PASSWORD:
				return basicSetIdentityPassword(null, msgs);
			case CddPackage.SECURITY_REQUESTER__CERTIFICATE_KEY_FILE:
				return basicSetCertificateKeyFile(null, msgs);
			case CddPackage.SECURITY_REQUESTER__DOMAIN_NAME:
				return basicSetDomainName(null, msgs);
			case CddPackage.SECURITY_REQUESTER__USER_NAME:
				return basicSetUserName(null, msgs);
			case CddPackage.SECURITY_REQUESTER__USER_PASSWORD:
				return basicSetUserPassword(null, msgs);
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
		switch (featureID) {
			case CddPackage.SECURITY_REQUESTER__TOKEN_FILE:
				return getTokenFile();
			case CddPackage.SECURITY_REQUESTER__IDENTITY_PASSWORD:
				return getIdentityPassword();
			case CddPackage.SECURITY_REQUESTER__CERTIFICATE_KEY_FILE:
				return getCertificateKeyFile();
			case CddPackage.SECURITY_REQUESTER__DOMAIN_NAME:
				return getDomainName();
			case CddPackage.SECURITY_REQUESTER__USER_NAME:
				return getUserName();
			case CddPackage.SECURITY_REQUESTER__USER_PASSWORD:
				return getUserPassword();
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
		switch (featureID) {
			case CddPackage.SECURITY_REQUESTER__TOKEN_FILE:
				setTokenFile((SecurityOverrideConfig)newValue);
				return;
			case CddPackage.SECURITY_REQUESTER__IDENTITY_PASSWORD:
				setIdentityPassword((SecurityOverrideConfig)newValue);
				return;
			case CddPackage.SECURITY_REQUESTER__CERTIFICATE_KEY_FILE:
				setCertificateKeyFile((SecurityOverrideConfig)newValue);
				return;
			case CddPackage.SECURITY_REQUESTER__DOMAIN_NAME:
				setDomainName((SecurityOverrideConfig)newValue);
				return;
			case CddPackage.SECURITY_REQUESTER__USER_NAME:
				setUserName((SecurityOverrideConfig)newValue);
				return;
			case CddPackage.SECURITY_REQUESTER__USER_PASSWORD:
				setUserPassword((SecurityOverrideConfig)newValue);
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
		switch (featureID) {
			case CddPackage.SECURITY_REQUESTER__TOKEN_FILE:
				setTokenFile((SecurityOverrideConfig)null);
				return;
			case CddPackage.SECURITY_REQUESTER__IDENTITY_PASSWORD:
				setIdentityPassword((SecurityOverrideConfig)null);
				return;
			case CddPackage.SECURITY_REQUESTER__CERTIFICATE_KEY_FILE:
				setCertificateKeyFile((SecurityOverrideConfig)null);
				return;
			case CddPackage.SECURITY_REQUESTER__DOMAIN_NAME:
				setDomainName((SecurityOverrideConfig)null);
				return;
			case CddPackage.SECURITY_REQUESTER__USER_NAME:
				setUserName((SecurityOverrideConfig)null);
				return;
			case CddPackage.SECURITY_REQUESTER__USER_PASSWORD:
				setUserPassword((SecurityOverrideConfig)null);
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
		switch (featureID) {
			case CddPackage.SECURITY_REQUESTER__TOKEN_FILE:
				return tokenFile != null;
			case CddPackage.SECURITY_REQUESTER__IDENTITY_PASSWORD:
				return identityPassword != null;
			case CddPackage.SECURITY_REQUESTER__CERTIFICATE_KEY_FILE:
				return certificateKeyFile != null;
			case CddPackage.SECURITY_REQUESTER__DOMAIN_NAME:
				return domainName != null;
			case CddPackage.SECURITY_REQUESTER__USER_NAME:
				return userName != null;
			case CddPackage.SECURITY_REQUESTER__USER_PASSWORD:
				return userPassword != null;
		}
		return super.eIsSet(featureID);
	}

} //SecurityRequesterImpl
