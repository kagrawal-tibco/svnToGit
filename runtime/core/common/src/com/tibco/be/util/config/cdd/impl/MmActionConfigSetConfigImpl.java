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
import com.tibco.be.util.config.cdd.MmActionConfigConfig;
import com.tibco.be.util.config.cdd.MmActionConfigSetConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mm Action Config Set Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.MmActionConfigSetConfigImpl#getMmActionConfig <em>Mm Action Config</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MmActionConfigSetConfigImpl extends EObjectImpl implements MmActionConfigSetConfig {
	/**
	 * The cached value of the '{@link #getMmActionConfig() <em>Mm Action Config</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMmActionConfig()
	 * @generated
	 * @ordered
	 */
	protected EList<MmActionConfigConfig> mmActionConfig;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MmActionConfigSetConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getMmActionConfigSetConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MmActionConfigConfig> getMmActionConfig() {
		if (mmActionConfig == null) {
			mmActionConfig = new EObjectContainmentEList<MmActionConfigConfig>(MmActionConfigConfig.class, this, CddPackage.MM_ACTION_CONFIG_SET_CONFIG__MM_ACTION_CONFIG);
		}
		return mmActionConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.MM_ACTION_CONFIG_SET_CONFIG__MM_ACTION_CONFIG:
				return ((InternalEList<?>)getMmActionConfig()).basicRemove(otherEnd, msgs);
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
			case CddPackage.MM_ACTION_CONFIG_SET_CONFIG__MM_ACTION_CONFIG:
				return getMmActionConfig();
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
			case CddPackage.MM_ACTION_CONFIG_SET_CONFIG__MM_ACTION_CONFIG:
				getMmActionConfig().clear();
				getMmActionConfig().addAll((Collection<? extends MmActionConfigConfig>)newValue);
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
			case CddPackage.MM_ACTION_CONFIG_SET_CONFIG__MM_ACTION_CONFIG:
				getMmActionConfig().clear();
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
			case CddPackage.MM_ACTION_CONFIG_SET_CONFIG__MM_ACTION_CONFIG:
				return mmActionConfig != null && !mmActionConfig.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //MmActionConfigSetConfigImpl
