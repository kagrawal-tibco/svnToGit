/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.rms.preferences.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.studio.rms.preferences.model.PreferencesPackage;
import com.tibco.cep.studio.rms.preferences.model.URL;
import com.tibco.cep.studio.rms.preferences.model.URLInfo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>URL Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.rms.preferences.model.impl.URLInfoImpl#getUrls <em>Urls</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class URLInfoImpl extends EObjectImpl implements URLInfo {
	/**
	 * The cached value of the '{@link #getUrls() <em>Urls</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUrls()
	 * @generated
	 * @ordered
	 */
	protected EList<URL> urls;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected URLInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PreferencesPackage.Literals.URL_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<URL> getUrls() {
		if (urls == null) {
			urls = new EObjectContainmentEList<URL>(URL.class, this, PreferencesPackage.URL_INFO__URLS);
		}
		return urls;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PreferencesPackage.URL_INFO__URLS:
				return ((InternalEList<?>)getUrls()).basicRemove(otherEnd, msgs);
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
			case PreferencesPackage.URL_INFO__URLS:
				return getUrls();
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
			case PreferencesPackage.URL_INFO__URLS:
				getUrls().clear();
				getUrls().addAll((Collection<? extends URL>)newValue);
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
			case PreferencesPackage.URL_INFO__URLS:
				getUrls().clear();
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
			case PreferencesPackage.URL_INFO__URLS:
				return urls != null && !urls.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //URLInfoImpl
