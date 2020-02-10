/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage;
import com.tibco.be.baas.security.authn.saml.model.protocol.ResponseType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Response Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.ResponseTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.ResponseTypeImpl#getAssertion <em>Assertion</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ResponseTypeImpl extends StatusResponseTypeImpl implements ResponseType {
	/**
	 * The cached value of the '{@link #getGroup() <em>Group</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroup()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap group;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ResponseTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProtocolPackage.Literals.RESPONSE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, ProtocolPackage.RESPONSE_TYPE__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AssertionType> getAssertion() {
		return getGroup().list(ProtocolPackage.Literals.RESPONSE_TYPE__ASSERTION);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProtocolPackage.RESPONSE_TYPE__GROUP:
				return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
			case ProtocolPackage.RESPONSE_TYPE__ASSERTION:
				return ((InternalEList<?>)getAssertion()).basicRemove(otherEnd, msgs);
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
			case ProtocolPackage.RESPONSE_TYPE__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case ProtocolPackage.RESPONSE_TYPE__ASSERTION:
				return getAssertion();
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
			case ProtocolPackage.RESPONSE_TYPE__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case ProtocolPackage.RESPONSE_TYPE__ASSERTION:
				getAssertion().clear();
				getAssertion().addAll((Collection<? extends AssertionType>)newValue);
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
			case ProtocolPackage.RESPONSE_TYPE__GROUP:
				getGroup().clear();
				return;
			case ProtocolPackage.RESPONSE_TYPE__ASSERTION:
				getAssertion().clear();
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
			case ProtocolPackage.RESPONSE_TYPE__GROUP:
				return group != null && !group.isEmpty();
			case ProtocolPackage.RESPONSE_TYPE__ASSERTION:
				return !getAssertion().isEmpty();
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
		result.append(" (group: ");
		result.append(group);
		result.append(')');
		return result.toString();
	}

} //ResponseTypeImpl
