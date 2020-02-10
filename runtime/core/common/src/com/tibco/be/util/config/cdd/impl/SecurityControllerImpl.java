/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.SecurityController;
import com.tibco.be.util.config.cdd.SecurityOverrideConfig;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Security Controller</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.SecurityControllerImpl#getPolicyFile <em>Policy File</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.SecurityControllerImpl#getIdentityPassword <em>Identity Password</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SecurityControllerImpl extends EObjectImpl implements SecurityController {
	/**
	 * The cached value of the '{@link #getPolicyFile() <em>Policy File</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPolicyFile()
	 * @generated
	 * @ordered
	 */
	protected SecurityOverrideConfig policyFile;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SecurityControllerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getSecurityController();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityOverrideConfig getPolicyFile() {
		return policyFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPolicyFile(SecurityOverrideConfig newPolicyFile, NotificationChain msgs) {
		SecurityOverrideConfig oldPolicyFile = policyFile;
		policyFile = newPolicyFile;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.SECURITY_CONTROLLER__POLICY_FILE, oldPolicyFile, newPolicyFile);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPolicyFile(SecurityOverrideConfig newPolicyFile) {
		if (newPolicyFile != policyFile) {
			NotificationChain msgs = null;
			if (policyFile != null)
				msgs = ((InternalEObject)policyFile).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.SECURITY_CONTROLLER__POLICY_FILE, null, msgs);
			if (newPolicyFile != null)
				msgs = ((InternalEObject)newPolicyFile).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.SECURITY_CONTROLLER__POLICY_FILE, null, msgs);
			msgs = basicSetPolicyFile(newPolicyFile, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.SECURITY_CONTROLLER__POLICY_FILE, newPolicyFile, newPolicyFile));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.SECURITY_CONTROLLER__IDENTITY_PASSWORD, oldIdentityPassword, newIdentityPassword);
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
				msgs = ((InternalEObject)identityPassword).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.SECURITY_CONTROLLER__IDENTITY_PASSWORD, null, msgs);
			if (newIdentityPassword != null)
				msgs = ((InternalEObject)newIdentityPassword).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.SECURITY_CONTROLLER__IDENTITY_PASSWORD, null, msgs);
			msgs = basicSetIdentityPassword(newIdentityPassword, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.SECURITY_CONTROLLER__IDENTITY_PASSWORD, newIdentityPassword, newIdentityPassword));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.SECURITY_CONTROLLER__POLICY_FILE:
				return basicSetPolicyFile(null, msgs);
			case CddPackage.SECURITY_CONTROLLER__IDENTITY_PASSWORD:
				return basicSetIdentityPassword(null, msgs);
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
			case CddPackage.SECURITY_CONTROLLER__POLICY_FILE:
				return getPolicyFile();
			case CddPackage.SECURITY_CONTROLLER__IDENTITY_PASSWORD:
				return getIdentityPassword();
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
			case CddPackage.SECURITY_CONTROLLER__POLICY_FILE:
				setPolicyFile((SecurityOverrideConfig)newValue);
				return;
			case CddPackage.SECURITY_CONTROLLER__IDENTITY_PASSWORD:
				setIdentityPassword((SecurityOverrideConfig)newValue);
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
			case CddPackage.SECURITY_CONTROLLER__POLICY_FILE:
				setPolicyFile((SecurityOverrideConfig)null);
				return;
			case CddPackage.SECURITY_CONTROLLER__IDENTITY_PASSWORD:
				setIdentityPassword((SecurityOverrideConfig)null);
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
			case CddPackage.SECURITY_CONTROLLER__POLICY_FILE:
				return policyFile != null;
			case CddPackage.SECURITY_CONTROLLER__IDENTITY_PASSWORD:
				return identityPassword != null;
		}
		return super.eIsSet(featureID);
	}

} //SecurityControllerImpl
