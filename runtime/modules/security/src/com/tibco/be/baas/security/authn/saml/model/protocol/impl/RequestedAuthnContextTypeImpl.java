/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;

import com.tibco.be.baas.security.authn.saml.model.protocol.AuthnContextComparisonType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage;
import com.tibco.be.baas.security.authn.saml.model.protocol.RequestedAuthnContextType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Requested Authn Context Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.RequestedAuthnContextTypeImpl#getAuthnContextClassRef <em>Authn Context Class Ref</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.RequestedAuthnContextTypeImpl#getAuthnContextDeclRef <em>Authn Context Decl Ref</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.RequestedAuthnContextTypeImpl#getComparison <em>Comparison</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RequestedAuthnContextTypeImpl extends EObjectImpl implements RequestedAuthnContextType {
	/**
	 * The cached value of the '{@link #getAuthnContextClassRef() <em>Authn Context Class Ref</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthnContextClassRef()
	 * @generated
	 * @ordered
	 */
	protected EList<String> authnContextClassRef;

	/**
	 * The cached value of the '{@link #getAuthnContextDeclRef() <em>Authn Context Decl Ref</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthnContextDeclRef()
	 * @generated
	 * @ordered
	 */
	protected EList<String> authnContextDeclRef;

	/**
	 * The default value of the '{@link #getComparison() <em>Comparison</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComparison()
	 * @generated
	 * @ordered
	 */
	protected static final AuthnContextComparisonType COMPARISON_EDEFAULT = AuthnContextComparisonType.EXACT;

	/**
	 * The cached value of the '{@link #getComparison() <em>Comparison</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComparison()
	 * @generated
	 * @ordered
	 */
	protected AuthnContextComparisonType comparison = COMPARISON_EDEFAULT;

	/**
	 * This is true if the Comparison attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean comparisonESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RequestedAuthnContextTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProtocolPackage.Literals.REQUESTED_AUTHN_CONTEXT_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getAuthnContextClassRef() {
		if (authnContextClassRef == null) {
			authnContextClassRef = new EDataTypeEList<String>(String.class, this, ProtocolPackage.REQUESTED_AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_CLASS_REF);
		}
		return authnContextClassRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getAuthnContextDeclRef() {
		if (authnContextDeclRef == null) {
			authnContextDeclRef = new EDataTypeEList<String>(String.class, this, ProtocolPackage.REQUESTED_AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL_REF);
		}
		return authnContextDeclRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthnContextComparisonType getComparison() {
		return comparison;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComparison(AuthnContextComparisonType newComparison) {
		AuthnContextComparisonType oldComparison = comparison;
		comparison = newComparison == null ? COMPARISON_EDEFAULT : newComparison;
		boolean oldComparisonESet = comparisonESet;
		comparisonESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.REQUESTED_AUTHN_CONTEXT_TYPE__COMPARISON, oldComparison, comparison, !oldComparisonESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetComparison() {
		AuthnContextComparisonType oldComparison = comparison;
		boolean oldComparisonESet = comparisonESet;
		comparison = COMPARISON_EDEFAULT;
		comparisonESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ProtocolPackage.REQUESTED_AUTHN_CONTEXT_TYPE__COMPARISON, oldComparison, COMPARISON_EDEFAULT, oldComparisonESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetComparison() {
		return comparisonESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ProtocolPackage.REQUESTED_AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_CLASS_REF:
				return getAuthnContextClassRef();
			case ProtocolPackage.REQUESTED_AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL_REF:
				return getAuthnContextDeclRef();
			case ProtocolPackage.REQUESTED_AUTHN_CONTEXT_TYPE__COMPARISON:
				return getComparison();
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
			case ProtocolPackage.REQUESTED_AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_CLASS_REF:
				getAuthnContextClassRef().clear();
				getAuthnContextClassRef().addAll((Collection<? extends String>)newValue);
				return;
			case ProtocolPackage.REQUESTED_AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL_REF:
				getAuthnContextDeclRef().clear();
				getAuthnContextDeclRef().addAll((Collection<? extends String>)newValue);
				return;
			case ProtocolPackage.REQUESTED_AUTHN_CONTEXT_TYPE__COMPARISON:
				setComparison((AuthnContextComparisonType)newValue);
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
			case ProtocolPackage.REQUESTED_AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_CLASS_REF:
				getAuthnContextClassRef().clear();
				return;
			case ProtocolPackage.REQUESTED_AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL_REF:
				getAuthnContextDeclRef().clear();
				return;
			case ProtocolPackage.REQUESTED_AUTHN_CONTEXT_TYPE__COMPARISON:
				unsetComparison();
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
			case ProtocolPackage.REQUESTED_AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_CLASS_REF:
				return authnContextClassRef != null && !authnContextClassRef.isEmpty();
			case ProtocolPackage.REQUESTED_AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL_REF:
				return authnContextDeclRef != null && !authnContextDeclRef.isEmpty();
			case ProtocolPackage.REQUESTED_AUTHN_CONTEXT_TYPE__COMPARISON:
				return isSetComparison();
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
		result.append(" (authnContextClassRef: ");
		result.append(authnContextClassRef);
		result.append(", authnContextDeclRef: ");
		result.append(authnContextDeclRef);
		result.append(", comparison: ");
		if (comparisonESet) result.append(comparison); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //RequestedAuthnContextTypeImpl
