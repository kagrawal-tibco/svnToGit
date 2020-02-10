/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.serversettingsmodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import com.tibco.cep.serversettingsmodel.CheckoutURL;
import com.tibco.cep.serversettingsmodel.ServersettingsmodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Checkout URL</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.serversettingsmodel.impl.CheckoutURLImpl#getURL <em>URL</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CheckoutURLImpl extends EObjectImpl implements CheckoutURL {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CheckoutURLImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ServersettingsmodelPackage.Literals.CHECKOUT_URL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getURL() {
		if (url == null) {
			url = new EDataTypeUniqueEList<String>(String.class, this, ServersettingsmodelPackage.CHECKOUT_URL__URL);
		}
		return url;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ServersettingsmodelPackage.CHECKOUT_URL__URL:
				return getURL();
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
			case ServersettingsmodelPackage.CHECKOUT_URL__URL:
				getURL().clear();
				getURL().addAll((Collection<? extends String>)newValue);
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
			case ServersettingsmodelPackage.CHECKOUT_URL__URL:
				getURL().clear();
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
			case ServersettingsmodelPackage.CHECKOUT_URL__URL:
				return url != null && !url.isEmpty();
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

} //CheckoutURLImpl
