/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EDataTypeEList;

import com.tibco.be.baas.security.authn.saml.model.protocol.AssertionIDRequestType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Assertion ID Request Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.AssertionIDRequestTypeImpl#getAssertionIDRef <em>Assertion ID Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AssertionIDRequestTypeImpl extends RequestAbstractTypeImpl implements AssertionIDRequestType {
	/**
	 * The cached value of the '{@link #getAssertionIDRef() <em>Assertion ID Ref</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssertionIDRef()
	 * @generated
	 * @ordered
	 */
	protected EList<String> assertionIDRef;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AssertionIDRequestTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProtocolPackage.Literals.ASSERTION_ID_REQUEST_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getAssertionIDRef() {
		if (assertionIDRef == null) {
			assertionIDRef = new EDataTypeEList<String>(String.class, this, ProtocolPackage.ASSERTION_ID_REQUEST_TYPE__ASSERTION_ID_REF);
		}
		return assertionIDRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ProtocolPackage.ASSERTION_ID_REQUEST_TYPE__ASSERTION_ID_REF:
				return getAssertionIDRef();
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
			case ProtocolPackage.ASSERTION_ID_REQUEST_TYPE__ASSERTION_ID_REF:
				getAssertionIDRef().clear();
				getAssertionIDRef().addAll((Collection<? extends String>)newValue);
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
			case ProtocolPackage.ASSERTION_ID_REQUEST_TYPE__ASSERTION_ID_REF:
				getAssertionIDRef().clear();
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
			case ProtocolPackage.ASSERTION_ID_REQUEST_TYPE__ASSERTION_ID_REF:
				return assertionIDRef != null && !assertionIDRef.isEmpty();
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
		result.append(" (assertionIDRef: ");
		result.append(assertionIDRef);
		result.append(')');
		return result.toString();
	}

} //AssertionIDRequestTypeImpl
