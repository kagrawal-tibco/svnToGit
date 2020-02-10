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

import com.tibco.cep.studio.rms.preferences.model.CheckoutRepoInfo;
import com.tibco.cep.studio.rms.preferences.model.CheckoutURLInfo;
import com.tibco.cep.studio.rms.preferences.model.PreferencesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Checkout Repo Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.rms.preferences.model.impl.CheckoutRepoInfoImpl#getUrlInfo <em>Url Info</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CheckoutRepoInfoImpl extends EObjectImpl implements CheckoutRepoInfo {
	/**
	 * The cached value of the '{@link #getUrlInfo() <em>Url Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUrlInfo()
	 * @generated
	 * @ordered
	 */
	protected CheckoutURLInfo urlInfo;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CheckoutRepoInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PreferencesPackage.Literals.CHECKOUT_REPO_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CheckoutURLInfo getUrlInfo() {
		return urlInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUrlInfo(CheckoutURLInfo newUrlInfo, NotificationChain msgs) {
		CheckoutURLInfo oldUrlInfo = urlInfo;
		urlInfo = newUrlInfo;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PreferencesPackage.CHECKOUT_REPO_INFO__URL_INFO, oldUrlInfo, newUrlInfo);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUrlInfo(CheckoutURLInfo newUrlInfo) {
		if (newUrlInfo != urlInfo) {
			NotificationChain msgs = null;
			if (urlInfo != null)
				msgs = ((InternalEObject)urlInfo).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PreferencesPackage.CHECKOUT_REPO_INFO__URL_INFO, null, msgs);
			if (newUrlInfo != null)
				msgs = ((InternalEObject)newUrlInfo).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PreferencesPackage.CHECKOUT_REPO_INFO__URL_INFO, null, msgs);
			msgs = basicSetUrlInfo(newUrlInfo, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.CHECKOUT_REPO_INFO__URL_INFO, newUrlInfo, newUrlInfo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PreferencesPackage.CHECKOUT_REPO_INFO__URL_INFO:
				return basicSetUrlInfo(null, msgs);
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
			case PreferencesPackage.CHECKOUT_REPO_INFO__URL_INFO:
				return getUrlInfo();
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
			case PreferencesPackage.CHECKOUT_REPO_INFO__URL_INFO:
				setUrlInfo((CheckoutURLInfo)newValue);
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
			case PreferencesPackage.CHECKOUT_REPO_INFO__URL_INFO:
				setUrlInfo((CheckoutURLInfo)null);
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
			case PreferencesPackage.CHECKOUT_REPO_INFO__URL_INFO:
				return urlInfo != null;
		}
		return super.eIsSet(featureID);
	}

} //CheckoutRepoInfoImpl
