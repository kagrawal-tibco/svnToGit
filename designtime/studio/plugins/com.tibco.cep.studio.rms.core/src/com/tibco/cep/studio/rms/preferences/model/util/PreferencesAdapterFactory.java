/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.rms.preferences.model.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
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
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.rms.preferences.model.PreferencesPackage
 * @generated
 */
public class PreferencesAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static PreferencesPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreferencesAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = PreferencesPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PreferencesSwitch<Adapter> modelSwitch =
		new PreferencesSwitch<Adapter>() {
			@Override
			public Adapter caseAuthenticationInfo(AuthenticationInfo object) {
				return createAuthenticationInfoAdapter();
			}
			@Override
			public Adapter caseURLInfo(URLInfo object) {
				return createURLInfoAdapter();
			}
			@Override
			public Adapter caseURL(URL object) {
				return createURLAdapter();
			}
			@Override
			public Adapter caseCheckoutRepoInfo(CheckoutRepoInfo object) {
				return createCheckoutRepoInfoAdapter();
			}
			@Override
			public Adapter caseRMSPreferences(RMSPreferences object) {
				return createRMSPreferencesAdapter();
			}
			@Override
			public Adapter caseAuthenticationURLInfo(AuthenticationURLInfo object) {
				return createAuthenticationURLInfoAdapter();
			}
			@Override
			public Adapter caseCheckoutURLInfo(CheckoutURLInfo object) {
				return createCheckoutURLInfoAdapter();
			}
			@Override
			public Adapter caseUserInfo(UserInfo object) {
				return createUserInfoAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.rms.preferences.model.AuthenticationInfo <em>Authentication Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.rms.preferences.model.AuthenticationInfo
	 * @generated
	 */
	public Adapter createAuthenticationInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.rms.preferences.model.URLInfo <em>URL Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.rms.preferences.model.URLInfo
	 * @generated
	 */
	public Adapter createURLInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.rms.preferences.model.URL <em>URL</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.rms.preferences.model.URL
	 * @generated
	 */
	public Adapter createURLAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.rms.preferences.model.CheckoutRepoInfo <em>Checkout Repo Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.rms.preferences.model.CheckoutRepoInfo
	 * @generated
	 */
	public Adapter createCheckoutRepoInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.rms.preferences.model.RMSPreferences <em>RMS Preferences</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.rms.preferences.model.RMSPreferences
	 * @generated
	 */
	public Adapter createRMSPreferencesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.rms.preferences.model.AuthenticationURLInfo <em>Authentication URL Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.rms.preferences.model.AuthenticationURLInfo
	 * @generated
	 */
	public Adapter createAuthenticationURLInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.rms.preferences.model.CheckoutURLInfo <em>Checkout URL Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.rms.preferences.model.CheckoutURLInfo
	 * @generated
	 */
	public Adapter createCheckoutURLInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.rms.preferences.model.UserInfo <em>User Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.rms.preferences.model.UserInfo
	 * @generated
	 */
	public Adapter createUserInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //PreferencesAdapterFactory
