/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedjmscon.impl;

import com.tibco.be.util.config.sharedresources.sharedjmscon.ConnectionAttributes;
import com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Connection Attributes</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.ConnectionAttributesImpl#getUsername <em>Username</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.ConnectionAttributesImpl#getPassword <em>Password</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.ConnectionAttributesImpl#getClientId <em>Client Id</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.ConnectionAttributesImpl#getAutoGenClientID <em>Auto Gen Client ID</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConnectionAttributesImpl extends EObjectImpl implements ConnectionAttributes {
	/**
	 * The default value of the '{@link #getUsername() <em>Username</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUsername()
	 * @generated
	 * @ordered
	 */
	protected static final String USERNAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUsername() <em>Username</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUsername()
	 * @generated
	 * @ordered
	 */
	protected String username = USERNAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getPassword() <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPassword()
	 * @generated
	 * @ordered
	 */
	protected static final String PASSWORD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPassword() <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPassword()
	 * @generated
	 * @ordered
	 */
	protected String password = PASSWORD_EDEFAULT;

	/**
	 * The default value of the '{@link #getClientId() <em>Client Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClientId()
	 * @generated
	 * @ordered
	 */
	protected static final String CLIENT_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getClientId() <em>Client Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClientId()
	 * @generated
	 * @ordered
	 */
	protected String clientId = CLIENT_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getAutoGenClientID() <em>Auto Gen Client ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAutoGenClientID()
	 * @generated
	 * @ordered
	 */
	protected static final Object AUTO_GEN_CLIENT_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAutoGenClientID() <em>Auto Gen Client ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAutoGenClientID()
	 * @generated
	 * @ordered
	 */
	protected Object autoGenClientID = AUTO_GEN_CLIENT_ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConnectionAttributesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SharedjmsconPackage.Literals.CONNECTION_ATTRIBUTES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUsername(String newUsername) {
		String oldUsername = username;
		username = newUsername;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.CONNECTION_ATTRIBUTES__USERNAME, oldUsername, username));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPassword(String newPassword) {
		String oldPassword = password;
		password = newPassword;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.CONNECTION_ATTRIBUTES__PASSWORD, oldPassword, password));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClientId(String newClientId) {
		String oldClientId = clientId;
		clientId = newClientId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.CONNECTION_ATTRIBUTES__CLIENT_ID, oldClientId, clientId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getAutoGenClientID() {
		return autoGenClientID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAutoGenClientID(Object newAutoGenClientID) {
		Object oldAutoGenClientID = autoGenClientID;
		autoGenClientID = newAutoGenClientID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.CONNECTION_ATTRIBUTES__AUTO_GEN_CLIENT_ID, oldAutoGenClientID, autoGenClientID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SharedjmsconPackage.CONNECTION_ATTRIBUTES__USERNAME:
				return getUsername();
			case SharedjmsconPackage.CONNECTION_ATTRIBUTES__PASSWORD:
				return getPassword();
			case SharedjmsconPackage.CONNECTION_ATTRIBUTES__CLIENT_ID:
				return getClientId();
			case SharedjmsconPackage.CONNECTION_ATTRIBUTES__AUTO_GEN_CLIENT_ID:
				return getAutoGenClientID();
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
			case SharedjmsconPackage.CONNECTION_ATTRIBUTES__USERNAME:
				setUsername((String)newValue);
				return;
			case SharedjmsconPackage.CONNECTION_ATTRIBUTES__PASSWORD:
				setPassword((String)newValue);
				return;
			case SharedjmsconPackage.CONNECTION_ATTRIBUTES__CLIENT_ID:
				setClientId((String)newValue);
				return;
			case SharedjmsconPackage.CONNECTION_ATTRIBUTES__AUTO_GEN_CLIENT_ID:
				setAutoGenClientID(newValue);
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
			case SharedjmsconPackage.CONNECTION_ATTRIBUTES__USERNAME:
				setUsername(USERNAME_EDEFAULT);
				return;
			case SharedjmsconPackage.CONNECTION_ATTRIBUTES__PASSWORD:
				setPassword(PASSWORD_EDEFAULT);
				return;
			case SharedjmsconPackage.CONNECTION_ATTRIBUTES__CLIENT_ID:
				setClientId(CLIENT_ID_EDEFAULT);
				return;
			case SharedjmsconPackage.CONNECTION_ATTRIBUTES__AUTO_GEN_CLIENT_ID:
				setAutoGenClientID(AUTO_GEN_CLIENT_ID_EDEFAULT);
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
			case SharedjmsconPackage.CONNECTION_ATTRIBUTES__USERNAME:
				return USERNAME_EDEFAULT == null ? username != null : !USERNAME_EDEFAULT.equals(username);
			case SharedjmsconPackage.CONNECTION_ATTRIBUTES__PASSWORD:
				return PASSWORD_EDEFAULT == null ? password != null : !PASSWORD_EDEFAULT.equals(password);
			case SharedjmsconPackage.CONNECTION_ATTRIBUTES__CLIENT_ID:
				return CLIENT_ID_EDEFAULT == null ? clientId != null : !CLIENT_ID_EDEFAULT.equals(clientId);
			case SharedjmsconPackage.CONNECTION_ATTRIBUTES__AUTO_GEN_CLIENT_ID:
				return AUTO_GEN_CLIENT_ID_EDEFAULT == null ? autoGenClientID != null : !AUTO_GEN_CLIENT_ID_EDEFAULT.equals(autoGenClientID);
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
		result.append(" (username: ");
		result.append(username);
		result.append(", password: ");
		result.append(password);
		result.append(", clientId: ");
		result.append(clientId);
		result.append(", autoGenClientID: ");
		result.append(autoGenClientID);
		result.append(')');
		return result.toString();
	}

} //ConnectionAttributesImpl
