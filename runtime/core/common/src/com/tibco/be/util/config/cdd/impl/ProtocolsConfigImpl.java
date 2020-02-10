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
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.be.util.config.cdd.ProtocolsConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Protocols Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ProtocolsConfigImpl#getProtocol <em>Protocol</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProtocolsConfigImpl extends EObjectImpl implements ProtocolsConfig {
	/**
	 * The cached value of the '{@link #getProtocol() <em>Protocol</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProtocol()
	 * @generated
	 * @ordered
	 */
	protected EList<OverrideConfig> protocol;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProtocolsConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getProtocolsConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<OverrideConfig> getProtocol() {
		if (protocol == null) {
			protocol = new EObjectContainmentEList<OverrideConfig>(OverrideConfig.class, this, CddPackage.PROTOCOLS_CONFIG__PROTOCOL);
		}
		return protocol;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.PROTOCOLS_CONFIG__PROTOCOL:
				return ((InternalEList<?>)getProtocol()).basicRemove(otherEnd, msgs);
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
			case CddPackage.PROTOCOLS_CONFIG__PROTOCOL:
				return getProtocol();
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
			case CddPackage.PROTOCOLS_CONFIG__PROTOCOL:
				getProtocol().clear();
				getProtocol().addAll((Collection<? extends OverrideConfig>)newValue);
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
			case CddPackage.PROTOCOLS_CONFIG__PROTOCOL:
				getProtocol().clear();
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
			case CddPackage.PROTOCOLS_CONFIG__PROTOCOL:
				return protocol != null && !protocol.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ProtocolsConfigImpl
