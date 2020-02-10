/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.rms.preferences.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.studio.rms.preferences.model.PreferencesPackage;
import com.tibco.cep.studio.rms.preferences.model.URL;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>URL</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.rms.preferences.model.impl.URLImpl#getUrlString <em>Url String</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class URLImpl extends EObjectImpl implements URL {
	/**
	 * The default value of the '{@link #getUrlString() <em>Url String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUrlString()
	 * @generated
	 * @ordered
	 */
	protected static final String URL_STRING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUrlString() <em>Url String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUrlString()
	 * @generated
	 * @ordered
	 */
	protected String urlString = URL_STRING_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected URLImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PreferencesPackage.Literals.URL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUrlString() {
		return urlString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUrlString(String newUrlString) {
		String oldUrlString = urlString;
		urlString = newUrlString;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.URL__URL_STRING, oldUrlString, urlString));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PreferencesPackage.URL__URL_STRING:
				return getUrlString();
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
			case PreferencesPackage.URL__URL_STRING:
				setUrlString((String)newValue);
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
			case PreferencesPackage.URL__URL_STRING:
				setUrlString(URL_STRING_EDEFAULT);
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
			case PreferencesPackage.URL__URL_STRING:
				return URL_STRING_EDEFAULT == null ? urlString != null : !URL_STRING_EDEFAULT.equals(urlString);
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
		result.append(" (urlString: ");
		result.append(urlString);
		result.append(')');
		return result.toString();
	}

} //URLImpl
