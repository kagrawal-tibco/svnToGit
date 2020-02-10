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
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage;
import com.tibco.be.baas.security.authn.saml.model.assertion.AttributeStatementType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute Statement Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AttributeStatementTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AttributeStatementTypeImpl#getAttribute <em>Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AttributeStatementTypeImpl extends StatementAbstractTypeImpl implements AttributeStatementType {
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
	protected AttributeStatementTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AssertionPackage.Literals.ATTRIBUTE_STATEMENT_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, AssertionPackage.ATTRIBUTE_STATEMENT_TYPE__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AttributeType> getAttribute() {
		return getGroup().list(AssertionPackage.Literals.ATTRIBUTE_STATEMENT_TYPE__ATTRIBUTE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AssertionPackage.ATTRIBUTE_STATEMENT_TYPE__GROUP:
				return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
			case AssertionPackage.ATTRIBUTE_STATEMENT_TYPE__ATTRIBUTE:
				return ((InternalEList<?>)getAttribute()).basicRemove(otherEnd, msgs);
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
			case AssertionPackage.ATTRIBUTE_STATEMENT_TYPE__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case AssertionPackage.ATTRIBUTE_STATEMENT_TYPE__ATTRIBUTE:
				return getAttribute();
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
			case AssertionPackage.ATTRIBUTE_STATEMENT_TYPE__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case AssertionPackage.ATTRIBUTE_STATEMENT_TYPE__ATTRIBUTE:
				getAttribute().clear();
				getAttribute().addAll((Collection<? extends AttributeType>)newValue);
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
			case AssertionPackage.ATTRIBUTE_STATEMENT_TYPE__GROUP:
				getGroup().clear();
				return;
			case AssertionPackage.ATTRIBUTE_STATEMENT_TYPE__ATTRIBUTE:
				getAttribute().clear();
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
			case AssertionPackage.ATTRIBUTE_STATEMENT_TYPE__GROUP:
				return group != null && !group.isEmpty();
			case AssertionPackage.ATTRIBUTE_STATEMENT_TYPE__ATTRIBUTE:
				return !getAttribute().isEmpty();
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

} //AttributeStatementTypeImpl
