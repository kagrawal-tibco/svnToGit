/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.rms.preferences.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.studio.rms.preferences.model.AuthenticationInfo;
import com.tibco.cep.studio.rms.preferences.model.AuthenticationURLInfo;
import com.tibco.cep.studio.rms.preferences.model.PreferencesPackage;
import com.tibco.cep.studio.rms.preferences.model.UserInfo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Authentication Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.rms.preferences.model.impl.AuthenticationInfoImpl#getUrlInfo <em>Url Info</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.preferences.model.impl.AuthenticationInfoImpl#getUserInfos <em>User Infos</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AuthenticationInfoImpl extends EObjectImpl implements AuthenticationInfo {
	/**
	 * The cached value of the '{@link #getUrlInfo() <em>Url Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUrlInfo()
	 * @generated
	 * @ordered
	 */
	protected AuthenticationURLInfo urlInfo;

	/**
	 * The cached value of the '{@link #getUserInfos() <em>User Infos</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUserInfos()
	 * @generated
	 * @ordered
	 */
	protected EList<UserInfo> userInfos;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AuthenticationInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PreferencesPackage.Literals.AUTHENTICATION_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthenticationURLInfo getUrlInfo() {
		return urlInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUrlInfo(AuthenticationURLInfo newUrlInfo, NotificationChain msgs) {
		AuthenticationURLInfo oldUrlInfo = urlInfo;
		urlInfo = newUrlInfo;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PreferencesPackage.AUTHENTICATION_INFO__URL_INFO, oldUrlInfo, newUrlInfo);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUrlInfo(AuthenticationURLInfo newUrlInfo) {
		if (newUrlInfo != urlInfo) {
			NotificationChain msgs = null;
			if (urlInfo != null)
				msgs = ((InternalEObject)urlInfo).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PreferencesPackage.AUTHENTICATION_INFO__URL_INFO, null, msgs);
			if (newUrlInfo != null)
				msgs = ((InternalEObject)newUrlInfo).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PreferencesPackage.AUTHENTICATION_INFO__URL_INFO, null, msgs);
			msgs = basicSetUrlInfo(newUrlInfo, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.AUTHENTICATION_INFO__URL_INFO, newUrlInfo, newUrlInfo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<UserInfo> getUserInfos() {
		if (userInfos == null) {
			userInfos = new EObjectContainmentEList<UserInfo>(UserInfo.class, this, PreferencesPackage.AUTHENTICATION_INFO__USER_INFOS);
		}
		return userInfos;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PreferencesPackage.AUTHENTICATION_INFO__URL_INFO:
				return basicSetUrlInfo(null, msgs);
			case PreferencesPackage.AUTHENTICATION_INFO__USER_INFOS:
				return ((InternalEList<?>)getUserInfos()).basicRemove(otherEnd, msgs);
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
			case PreferencesPackage.AUTHENTICATION_INFO__URL_INFO:
				return getUrlInfo();
			case PreferencesPackage.AUTHENTICATION_INFO__USER_INFOS:
				return getUserInfos();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PreferencesPackage.AUTHENTICATION_INFO__URL_INFO:
				setUrlInfo((AuthenticationURLInfo)newValue);
				return;
			case PreferencesPackage.AUTHENTICATION_INFO__USER_INFOS:
				getUserInfos().clear();
				getUserInfos().addAll((Collection<? extends UserInfo>)newValue);
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
			case PreferencesPackage.AUTHENTICATION_INFO__URL_INFO:
				setUrlInfo((AuthenticationURLInfo)null);
				return;
			case PreferencesPackage.AUTHENTICATION_INFO__USER_INFOS:
				getUserInfos().clear();
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
			case PreferencesPackage.AUTHENTICATION_INFO__URL_INFO:
				return urlInfo != null;
			case PreferencesPackage.AUTHENTICATION_INFO__USER_INFOS:
				return userInfos != null && !userInfos.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //AuthenticationInfoImpl
