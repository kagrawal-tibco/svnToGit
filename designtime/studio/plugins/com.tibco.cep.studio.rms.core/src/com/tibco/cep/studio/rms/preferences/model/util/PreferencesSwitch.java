/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.rms.preferences.model.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.studio.rms.preferences.model.AuthenticationInfo;
import com.tibco.cep.studio.rms.preferences.model.AuthenticationURLInfo;
import com.tibco.cep.studio.rms.preferences.model.CheckoutRepoInfo;
import com.tibco.cep.studio.rms.preferences.model.CheckoutURLInfo;
import com.tibco.cep.studio.rms.preferences.model.PreferencesPackage;
import com.tibco.cep.studio.rms.preferences.model.RMSPreferences;
import com.tibco.cep.studio.rms.preferences.model.URL;
import com.tibco.cep.studio.rms.preferences.model.URLInfo;
import com.tibco.cep.studio.rms.preferences.model.UserInfo;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.rms.preferences.model.PreferencesPackage
 * @generated
 */
public class PreferencesSwitch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static PreferencesPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreferencesSwitch() {
		if (modelPackage == null) {
			modelPackage = PreferencesPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case PreferencesPackage.AUTHENTICATION_INFO: {
				AuthenticationInfo authenticationInfo = (AuthenticationInfo)theEObject;
				T result = caseAuthenticationInfo(authenticationInfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PreferencesPackage.URL_INFO: {
				URLInfo urlInfo = (URLInfo)theEObject;
				T result = caseURLInfo(urlInfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PreferencesPackage.URL: {
				URL url = (URL)theEObject;
				T result = caseURL(url);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PreferencesPackage.CHECKOUT_REPO_INFO: {
				CheckoutRepoInfo checkoutRepoInfo = (CheckoutRepoInfo)theEObject;
				T result = caseCheckoutRepoInfo(checkoutRepoInfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PreferencesPackage.RMS_PREFERENCES: {
				RMSPreferences rmsPreferences = (RMSPreferences)theEObject;
				T result = caseRMSPreferences(rmsPreferences);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PreferencesPackage.AUTHENTICATION_URL_INFO: {
				AuthenticationURLInfo authenticationURLInfo = (AuthenticationURLInfo)theEObject;
				T result = caseAuthenticationURLInfo(authenticationURLInfo);
				if (result == null) result = caseURLInfo(authenticationURLInfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PreferencesPackage.CHECKOUT_URL_INFO: {
				CheckoutURLInfo checkoutURLInfo = (CheckoutURLInfo)theEObject;
				T result = caseCheckoutURLInfo(checkoutURLInfo);
				if (result == null) result = caseURLInfo(checkoutURLInfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PreferencesPackage.USER_INFO: {
				UserInfo userInfo = (UserInfo)theEObject;
				T result = caseUserInfo(userInfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Authentication Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Authentication Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAuthenticationInfo(AuthenticationInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>URL Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>URL Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseURLInfo(URLInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>URL</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>URL</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseURL(URL object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Checkout Repo Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Checkout Repo Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCheckoutRepoInfo(CheckoutRepoInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>RMS Preferences</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>RMS Preferences</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRMSPreferences(RMSPreferences object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Authentication URL Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Authentication URL Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAuthenticationURLInfo(AuthenticationURLInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Checkout URL Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Checkout URL Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCheckoutURLInfo(CheckoutURLInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>User Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>User Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUserInfo(UserInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public T defaultCase(EObject object) {
		return null;
	}

} //PreferencesSwitch
