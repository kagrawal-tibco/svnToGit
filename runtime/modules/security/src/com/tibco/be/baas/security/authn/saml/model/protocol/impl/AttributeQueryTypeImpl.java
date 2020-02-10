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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType;
import com.tibco.be.baas.security.authn.saml.model.protocol.AttributeQueryType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute Query Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.AttributeQueryTypeImpl#getAttribute <em>Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AttributeQueryTypeImpl extends SubjectQueryAbstractTypeImpl implements AttributeQueryType {
	/**
	 * The cached value of the '{@link #getAttribute() <em>Attribute</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttribute()
	 * @generated
	 * @ordered
	 */
	protected EList<AttributeType> attribute;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeQueryTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProtocolPackage.Literals.ATTRIBUTE_QUERY_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AttributeType> getAttribute() {
		if (attribute == null) {
			attribute = new EObjectContainmentEList<AttributeType>(AttributeType.class, this, ProtocolPackage.ATTRIBUTE_QUERY_TYPE__ATTRIBUTE);
		}
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProtocolPackage.ATTRIBUTE_QUERY_TYPE__ATTRIBUTE:
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
			case ProtocolPackage.ATTRIBUTE_QUERY_TYPE__ATTRIBUTE:
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
			case ProtocolPackage.ATTRIBUTE_QUERY_TYPE__ATTRIBUTE:
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
			case ProtocolPackage.ATTRIBUTE_QUERY_TYPE__ATTRIBUTE:
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
			case ProtocolPackage.ATTRIBUTE_QUERY_TYPE__ATTRIBUTE:
				return attribute != null && !attribute.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //AttributeQueryTypeImpl
