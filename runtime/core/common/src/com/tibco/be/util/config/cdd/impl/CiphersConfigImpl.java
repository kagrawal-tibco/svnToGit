/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.CiphersConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ciphers Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CiphersConfigImpl#getCipher <em>Cipher</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CiphersConfigImpl extends EObjectImpl implements CiphersConfig {
	/**
	 * The cached value of the '{@link #getCipher() <em>Cipher</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCipher()
	 * @generated
	 * @ordered
	 */
	protected EList<OverrideConfig> cipher;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CiphersConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getCiphersConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<OverrideConfig> getCipher() {
		if (cipher == null) {
			cipher = new EObjectContainmentEList<OverrideConfig>(OverrideConfig.class, this, CddPackage.CIPHERS_CONFIG__CIPHER);
		}
		return cipher;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.CIPHERS_CONFIG__CIPHER:
				return ((InternalEList<?>)getCipher()).basicRemove(otherEnd, msgs);
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
			case CddPackage.CIPHERS_CONFIG__CIPHER:
				return getCipher();
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
			case CddPackage.CIPHERS_CONFIG__CIPHER:
				getCipher().clear();
				getCipher().addAll((Collection<? extends OverrideConfig>)newValue);
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
			case CddPackage.CIPHERS_CONFIG__CIPHER:
				getCipher().clear();
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
			case CddPackage.CIPHERS_CONFIG__CIPHER:
				return cipher != null && !cipher.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //CiphersConfigImpl
