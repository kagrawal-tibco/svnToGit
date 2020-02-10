/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.rms.preferences.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.studio.rms.preferences.model.AuthenticationInfo;
import com.tibco.cep.studio.rms.preferences.model.AuthenticationURLInfo;
import com.tibco.cep.studio.rms.preferences.model.CheckoutRepoInfo;
import com.tibco.cep.studio.rms.preferences.model.CheckoutURLInfo;
import com.tibco.cep.studio.rms.preferences.model.PreferencesFactory;
import com.tibco.cep.studio.rms.preferences.model.PreferencesPackage;
import com.tibco.cep.studio.rms.preferences.model.RMSPreferences;
import com.tibco.cep.studio.rms.preferences.model.URL;
import com.tibco.cep.studio.rms.preferences.model.UserInfo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PreferencesFactoryImpl extends EFactoryImpl implements PreferencesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PreferencesFactory init() {
		try {
			PreferencesFactory thePreferencesFactory = (PreferencesFactory)EPackage.Registry.INSTANCE.getEFactory("http:///com/tibco/cep/studio/rms/preferences/RMSPrefs.ecore"); 
			if (thePreferencesFactory != null) {
				return thePreferencesFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PreferencesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreferencesFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case PreferencesPackage.AUTHENTICATION_INFO: return createAuthenticationInfo();
			case PreferencesPackage.URL: return createURL();
			case PreferencesPackage.CHECKOUT_REPO_INFO: return createCheckoutRepoInfo();
			case PreferencesPackage.RMS_PREFERENCES: return createRMSPreferences();
			case PreferencesPackage.AUTHENTICATION_URL_INFO: return createAuthenticationURLInfo();
			case PreferencesPackage.CHECKOUT_URL_INFO: return createCheckoutURLInfo();
			case PreferencesPackage.USER_INFO: return createUserInfo();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthenticationInfo createAuthenticationInfo() {
		AuthenticationInfoImpl authenticationInfo = new AuthenticationInfoImpl();
		return authenticationInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public URL createURL() {
		URLImpl url = new URLImpl();
		return url;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CheckoutRepoInfo createCheckoutRepoInfo() {
		CheckoutRepoInfoImpl checkoutRepoInfo = new CheckoutRepoInfoImpl();
		return checkoutRepoInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RMSPreferences createRMSPreferences() {
		RMSPreferencesImpl rmsPreferences = new RMSPreferencesImpl();
		return rmsPreferences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthenticationURLInfo createAuthenticationURLInfo() {
		AuthenticationURLInfoImpl authenticationURLInfo = new AuthenticationURLInfoImpl();
		return authenticationURLInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CheckoutURLInfo createCheckoutURLInfo() {
		CheckoutURLInfoImpl checkoutURLInfo = new CheckoutURLInfoImpl();
		return checkoutURLInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserInfo createUserInfo() {
		UserInfoImpl userInfo = new UserInfoImpl();
		return userInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreferencesPackage getPreferencesPackage() {
		return (PreferencesPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static PreferencesPackage getPackage() {
		return PreferencesPackage.eINSTANCE;
	}

} //PreferencesFactoryImpl
