/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.policy.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyType;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyConfigType;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Config Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyConfigTypeImpl#getConfigProperty <em>Config Property</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PolicyConfigTypeImpl extends EObjectImpl implements PolicyConfigType {
	/**
	 * The cached value of the '{@link #getConfigProperty() <em>Config Property</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfigProperty()
	 * @generated
	 * @ordered
	 */
	protected EList<ConfigPropertyType> configProperty;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PolicyConfigTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PolicyPackage.Literals.POLICY_CONFIG_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ConfigPropertyType> getConfigProperty() {
		if (configProperty == null) {
			configProperty = new EObjectContainmentEList<ConfigPropertyType>(ConfigPropertyType.class, this, PolicyPackage.POLICY_CONFIG_TYPE__CONFIG_PROPERTY);
		}
		return configProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PolicyPackage.POLICY_CONFIG_TYPE__CONFIG_PROPERTY:
				return ((InternalEList<?>)getConfigProperty()).basicRemove(otherEnd, msgs);
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
			case PolicyPackage.POLICY_CONFIG_TYPE__CONFIG_PROPERTY:
				return getConfigProperty();
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
			case PolicyPackage.POLICY_CONFIG_TYPE__CONFIG_PROPERTY:
				getConfigProperty().clear();
				getConfigProperty().addAll((Collection<? extends ConfigPropertyType>)newValue);
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
			case PolicyPackage.POLICY_CONFIG_TYPE__CONFIG_PROPERTY:
				getConfigProperty().clear();
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
			case PolicyPackage.POLICY_CONFIG_TYPE__CONFIG_PROPERTY:
				return configProperty != null && !configProperty.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //PolicyConfigTypeImpl
