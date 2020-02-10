/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage;
import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType;
import com.tibco.be.baas.security.authn.saml.model.assertion.EvidenceType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Evidence Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.EvidenceTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.EvidenceTypeImpl#getAssertionIDRef <em>Assertion ID Ref</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.EvidenceTypeImpl#getAssertionURIRef <em>Assertion URI Ref</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.EvidenceTypeImpl#getAssertion <em>Assertion</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EvidenceTypeImpl extends EObjectImpl implements EvidenceType {
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
	protected EvidenceTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AssertionPackage.Literals.EVIDENCE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, AssertionPackage.EVIDENCE_TYPE__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getAssertionIDRef() {
		return getGroup().list(AssertionPackage.Literals.EVIDENCE_TYPE__ASSERTION_ID_REF);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getAssertionURIRef() {
		return getGroup().list(AssertionPackage.Literals.EVIDENCE_TYPE__ASSERTION_URI_REF);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AssertionType> getAssertion() {
		return getGroup().list(AssertionPackage.Literals.EVIDENCE_TYPE__ASSERTION);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AssertionPackage.EVIDENCE_TYPE__GROUP:
				return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
			case AssertionPackage.EVIDENCE_TYPE__ASSERTION:
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
			case AssertionPackage.EVIDENCE_TYPE__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case AssertionPackage.EVIDENCE_TYPE__ASSERTION_ID_REF:
				return getAssertionIDRef();
			case AssertionPackage.EVIDENCE_TYPE__ASSERTION_URI_REF:
				return getAssertionURIRef();
			case AssertionPackage.EVIDENCE_TYPE__ASSERTION:
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
			case AssertionPackage.EVIDENCE_TYPE__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case AssertionPackage.EVIDENCE_TYPE__ASSERTION_ID_REF:
				getAssertionIDRef().clear();
				getAssertionIDRef().addAll((Collection<? extends String>)newValue);
				return;
			case AssertionPackage.EVIDENCE_TYPE__ASSERTION_URI_REF:
				getAssertionURIRef().clear();
				getAssertionURIRef().addAll((Collection<? extends String>)newValue);
				return;
			case AssertionPackage.EVIDENCE_TYPE__ASSERTION:
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
			case AssertionPackage.EVIDENCE_TYPE__GROUP:
				getGroup().clear();
				return;
			case AssertionPackage.EVIDENCE_TYPE__ASSERTION_ID_REF:
				getAssertionIDRef().clear();
				return;
			case AssertionPackage.EVIDENCE_TYPE__ASSERTION_URI_REF:
				getAssertionURIRef().clear();
				return;
			case AssertionPackage.EVIDENCE_TYPE__ASSERTION:
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
			case AssertionPackage.EVIDENCE_TYPE__GROUP:
				return group != null && !group.isEmpty();
			case AssertionPackage.EVIDENCE_TYPE__ASSERTION_ID_REF:
				return !getAssertionIDRef().isEmpty();
			case AssertionPackage.EVIDENCE_TYPE__ASSERTION_URI_REF:
				return !getAssertionURIRef().isEmpty();
			case AssertionPackage.EVIDENCE_TYPE__ASSERTION:
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

} //EvidenceTypeImpl
