/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.serversettingsmodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import com.tibco.cep.serversettingsmodel.Authentication;
import com.tibco.cep.serversettingsmodel.AuthenticationURL;
import com.tibco.cep.serversettingsmodel.ServersettingsmodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Authentication URL</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.serversettingsmodel.impl.AuthenticationURLImpl#getURL <em>URL</em>}</li>
 *   <li>{@link com.tibco.cep.serversettingsmodel.impl.AuthenticationURLImpl#getAuthentication <em>Authentication</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AuthenticationURLImpl extends EObjectImpl implements AuthenticationURL {
	/**
	 * The cached value of the '{@link #getURL() <em>URL</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getURL()
	 * @generated
	 * @ordered
	 */
	protected EList<String> url;

	/**
	 * The cached value of the '{@link #getAuthentication() <em>Authentication</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthentication()
	 * @generated
	 * @ordered
	 */
	protected Authentication authentication;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AuthenticationURLImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ServersettingsmodelPackage.Literals.AUTHENTICATION_URL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getURL() {
		if (url == null) {
			url = new EDataTypeUniqueEList<String>(String.class, this, ServersettingsmodelPackage.AUTHENTICATION_URL__URL);
		}
		return url;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Authentication getAuthentication() {
		if (authentication != null && authentication.eIsProxy()) {
			InternalEObject oldAuthentication = (InternalEObject)authentication;
			authentication = (Authentication)eResolveProxy(oldAuthentication);
			if (authentication != oldAuthentication) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ServersettingsmodelPackage.AUTHENTICATION_URL__AUTHENTICATION, oldAuthentication, authentication));
			}
		}
		return authentication;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Authentication basicGetAuthentication() {
		return authentication;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthentication(Authentication newAuthentication) {
		Authentication oldAuthentication = authentication;
		authentication = newAuthentication;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ServersettingsmodelPackage.AUTHENTICATION_URL__AUTHENTICATION, oldAuthentication, authentication));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ServersettingsmodelPackage.AUTHENTICATION_URL__URL:
				return getURL();
			case ServersettingsmodelPackage.AUTHENTICATION_URL__AUTHENTICATION:
				if (resolve) return getAuthentication();
				return basicGetAuthentication();
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
			case ServersettingsmodelPackage.AUTHENTICATION_URL__URL:
				getURL().clear();
				getURL().addAll((Collection<? extends String>)newValue);
				return;
			case ServersettingsmodelPackage.AUTHENTICATION_URL__AUTHENTICATION:
				setAuthentication((Authentication)newValue);
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
			case ServersettingsmodelPackage.AUTHENTICATION_URL__URL:
				getURL().clear();
				return;
			case ServersettingsmodelPackage.AUTHENTICATION_URL__AUTHENTICATION:
				setAuthentication((Authentication)null);
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
			case ServersettingsmodelPackage.AUTHENTICATION_URL__URL:
				return url != null && !url.isEmpty();
			case ServersettingsmodelPackage.AUTHENTICATION_URL__AUTHENTICATION:
				return authentication != null;
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
		result.append(" (URL: ");
		result.append(url);
		result.append(')');
		return result.toString();
	}

} //AuthenticationURLImpl
