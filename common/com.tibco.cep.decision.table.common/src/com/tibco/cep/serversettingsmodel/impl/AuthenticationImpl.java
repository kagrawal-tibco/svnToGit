/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.serversettingsmodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.serversettingsmodel.Authentication;
import com.tibco.cep.serversettingsmodel.ServersettingsmodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Authentication</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.serversettingsmodel.impl.AuthenticationImpl#getUsername <em>Username</em>}</li>
 *   <li>{@link com.tibco.cep.serversettingsmodel.impl.AuthenticationImpl#getPassword <em>Password</em>}</li>
 *   <li>{@link com.tibco.cep.serversettingsmodel.impl.AuthenticationImpl#isSavePassword <em>Save Password</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AuthenticationImpl extends EObjectImpl implements Authentication {
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
	protected static final byte[] PASSWORD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPassword() <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPassword()
	 * @generated
	 * @ordered
	 */
	protected byte[] password = PASSWORD_EDEFAULT;

	/**
	 * The default value of the '{@link #isSavePassword() <em>Save Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSavePassword()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SAVE_PASSWORD_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSavePassword() <em>Save Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSavePassword()
	 * @generated
	 * @ordered
	 */
	protected boolean savePassword = SAVE_PASSWORD_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AuthenticationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ServersettingsmodelPackage.Literals.AUTHENTICATION;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ServersettingsmodelPackage.AUTHENTICATION__USERNAME, oldUsername, username));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public byte[] getPassword() {
		return password;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPassword(byte[] newPassword) {
		byte[] oldPassword = password;
		password = newPassword;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ServersettingsmodelPackage.AUTHENTICATION__PASSWORD, oldPassword, password));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSavePassword() {
		return savePassword;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSavePassword(boolean newSavePassword) {
		boolean oldSavePassword = savePassword;
		savePassword = newSavePassword;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ServersettingsmodelPackage.AUTHENTICATION__SAVE_PASSWORD, oldSavePassword, savePassword));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ServersettingsmodelPackage.AUTHENTICATION__USERNAME:
				return getUsername();
			case ServersettingsmodelPackage.AUTHENTICATION__PASSWORD:
				return getPassword();
			case ServersettingsmodelPackage.AUTHENTICATION__SAVE_PASSWORD:
				return isSavePassword() ? Boolean.TRUE : Boolean.FALSE;
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
			case ServersettingsmodelPackage.AUTHENTICATION__USERNAME:
				setUsername((String)newValue);
				return;
			case ServersettingsmodelPackage.AUTHENTICATION__PASSWORD:
				setPassword((byte[])newValue);
				return;
			case ServersettingsmodelPackage.AUTHENTICATION__SAVE_PASSWORD:
				setSavePassword(((Boolean)newValue).booleanValue());
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
			case ServersettingsmodelPackage.AUTHENTICATION__USERNAME:
				setUsername(USERNAME_EDEFAULT);
				return;
			case ServersettingsmodelPackage.AUTHENTICATION__PASSWORD:
				setPassword(PASSWORD_EDEFAULT);
				return;
			case ServersettingsmodelPackage.AUTHENTICATION__SAVE_PASSWORD:
				setSavePassword(SAVE_PASSWORD_EDEFAULT);
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
			case ServersettingsmodelPackage.AUTHENTICATION__USERNAME:
				return USERNAME_EDEFAULT == null ? username != null : !USERNAME_EDEFAULT.equals(username);
			case ServersettingsmodelPackage.AUTHENTICATION__PASSWORD:
				return PASSWORD_EDEFAULT == null ? password != null : !PASSWORD_EDEFAULT.equals(password);
			case ServersettingsmodelPackage.AUTHENTICATION__SAVE_PASSWORD:
				return savePassword != SAVE_PASSWORD_EDEFAULT;
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
		result.append(", savePassword: ");
		result.append(savePassword);
		result.append(')');
		return result.toString();
	}

} //AuthenticationImpl
