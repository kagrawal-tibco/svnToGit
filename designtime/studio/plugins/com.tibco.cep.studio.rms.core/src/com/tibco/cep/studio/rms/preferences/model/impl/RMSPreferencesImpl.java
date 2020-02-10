/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.rms.preferences.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.studio.rms.preferences.model.AuthenticationInfo;
import com.tibco.cep.studio.rms.preferences.model.CheckoutRepoInfo;
import com.tibco.cep.studio.rms.preferences.model.PreferencesPackage;
import com.tibco.cep.studio.rms.preferences.model.RMSPreferences;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>RMS Preferences</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.rms.preferences.model.impl.RMSPreferencesImpl#getAuthInfo <em>Auth Info</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.preferences.model.impl.RMSPreferencesImpl#getCheckoutInfo <em>Checkout Info</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RMSPreferencesImpl extends EObjectImpl implements RMSPreferences {
	/**
	 * The cached value of the '{@link #getAuthInfo() <em>Auth Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthInfo()
	 * @generated
	 * @ordered
	 */
	protected AuthenticationInfo authInfo;

	/**
	 * The cached value of the '{@link #getCheckoutInfo() <em>Checkout Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCheckoutInfo()
	 * @generated
	 * @ordered
	 */
	protected CheckoutRepoInfo checkoutInfo;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RMSPreferencesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PreferencesPackage.Literals.RMS_PREFERENCES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthenticationInfo getAuthInfo() {
		return authInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAuthInfo(AuthenticationInfo newAuthInfo, NotificationChain msgs) {
		AuthenticationInfo oldAuthInfo = authInfo;
		authInfo = newAuthInfo;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PreferencesPackage.RMS_PREFERENCES__AUTH_INFO, oldAuthInfo, newAuthInfo);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthInfo(AuthenticationInfo newAuthInfo) {
		if (newAuthInfo != authInfo) {
			NotificationChain msgs = null;
			if (authInfo != null)
				msgs = ((InternalEObject)authInfo).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PreferencesPackage.RMS_PREFERENCES__AUTH_INFO, null, msgs);
			if (newAuthInfo != null)
				msgs = ((InternalEObject)newAuthInfo).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PreferencesPackage.RMS_PREFERENCES__AUTH_INFO, null, msgs);
			msgs = basicSetAuthInfo(newAuthInfo, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.RMS_PREFERENCES__AUTH_INFO, newAuthInfo, newAuthInfo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CheckoutRepoInfo getCheckoutInfo() {
		return checkoutInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCheckoutInfo(CheckoutRepoInfo newCheckoutInfo, NotificationChain msgs) {
		CheckoutRepoInfo oldCheckoutInfo = checkoutInfo;
		checkoutInfo = newCheckoutInfo;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PreferencesPackage.RMS_PREFERENCES__CHECKOUT_INFO, oldCheckoutInfo, newCheckoutInfo);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCheckoutInfo(CheckoutRepoInfo newCheckoutInfo) {
		if (newCheckoutInfo != checkoutInfo) {
			NotificationChain msgs = null;
			if (checkoutInfo != null)
				msgs = ((InternalEObject)checkoutInfo).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PreferencesPackage.RMS_PREFERENCES__CHECKOUT_INFO, null, msgs);
			if (newCheckoutInfo != null)
				msgs = ((InternalEObject)newCheckoutInfo).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PreferencesPackage.RMS_PREFERENCES__CHECKOUT_INFO, null, msgs);
			msgs = basicSetCheckoutInfo(newCheckoutInfo, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.RMS_PREFERENCES__CHECKOUT_INFO, newCheckoutInfo, newCheckoutInfo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PreferencesPackage.RMS_PREFERENCES__AUTH_INFO:
				return basicSetAuthInfo(null, msgs);
			case PreferencesPackage.RMS_PREFERENCES__CHECKOUT_INFO:
				return basicSetCheckoutInfo(null, msgs);
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
			case PreferencesPackage.RMS_PREFERENCES__AUTH_INFO:
				return getAuthInfo();
			case PreferencesPackage.RMS_PREFERENCES__CHECKOUT_INFO:
				return getCheckoutInfo();
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
			case PreferencesPackage.RMS_PREFERENCES__AUTH_INFO:
				setAuthInfo((AuthenticationInfo)newValue);
				return;
			case PreferencesPackage.RMS_PREFERENCES__CHECKOUT_INFO:
				setCheckoutInfo((CheckoutRepoInfo)newValue);
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
			case PreferencesPackage.RMS_PREFERENCES__AUTH_INFO:
				setAuthInfo((AuthenticationInfo)null);
				return;
			case PreferencesPackage.RMS_PREFERENCES__CHECKOUT_INFO:
				setCheckoutInfo((CheckoutRepoInfo)null);
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
			case PreferencesPackage.RMS_PREFERENCES__AUTH_INFO:
				return authInfo != null;
			case PreferencesPackage.RMS_PREFERENCES__CHECKOUT_INFO:
				return checkoutInfo != null;
		}
		return super.eIsSet(featureID);
	}

} //RMSPreferencesImpl
