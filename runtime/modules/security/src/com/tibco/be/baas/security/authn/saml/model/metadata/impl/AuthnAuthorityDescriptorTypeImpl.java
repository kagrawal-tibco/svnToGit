/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.metadata.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.baas.security.authn.saml.model.metadata.AuthnAuthorityDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType;
import com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Authn Authority Descriptor Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AuthnAuthorityDescriptorTypeImpl#getAuthnQueryService <em>Authn Query Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AuthnAuthorityDescriptorTypeImpl#getAssertionIDRequestService <em>Assertion ID Request Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AuthnAuthorityDescriptorTypeImpl#getNameIDFormat <em>Name ID Format</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AuthnAuthorityDescriptorTypeImpl extends RoleDescriptorTypeImpl implements AuthnAuthorityDescriptorType {
	/**
	 * The cached value of the '{@link #getAuthnQueryService() <em>Authn Query Service</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthnQueryService()
	 * @generated
	 * @ordered
	 */
	protected EList<EndpointType> authnQueryService;

	/**
	 * The cached value of the '{@link #getAssertionIDRequestService() <em>Assertion ID Request Service</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssertionIDRequestService()
	 * @generated
	 * @ordered
	 */
	protected EList<EndpointType> assertionIDRequestService;

	/**
	 * The cached value of the '{@link #getNameIDFormat() <em>Name ID Format</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNameIDFormat()
	 * @generated
	 * @ordered
	 */
	protected EList<String> nameIDFormat;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AuthnAuthorityDescriptorTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetadataPackage.Literals.AUTHN_AUTHORITY_DESCRIPTOR_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EndpointType> getAuthnQueryService() {
		if (authnQueryService == null) {
			authnQueryService = new EObjectContainmentEList<EndpointType>(EndpointType.class, this, MetadataPackage.AUTHN_AUTHORITY_DESCRIPTOR_TYPE__AUTHN_QUERY_SERVICE);
		}
		return authnQueryService;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EndpointType> getAssertionIDRequestService() {
		if (assertionIDRequestService == null) {
			assertionIDRequestService = new EObjectContainmentEList<EndpointType>(EndpointType.class, this, MetadataPackage.AUTHN_AUTHORITY_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE);
		}
		return assertionIDRequestService;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getNameIDFormat() {
		if (nameIDFormat == null) {
			nameIDFormat = new EDataTypeEList<String>(String.class, this, MetadataPackage.AUTHN_AUTHORITY_DESCRIPTOR_TYPE__NAME_ID_FORMAT);
		}
		return nameIDFormat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MetadataPackage.AUTHN_AUTHORITY_DESCRIPTOR_TYPE__AUTHN_QUERY_SERVICE:
				return ((InternalEList<?>)getAuthnQueryService()).basicRemove(otherEnd, msgs);
			case MetadataPackage.AUTHN_AUTHORITY_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE:
				return ((InternalEList<?>)getAssertionIDRequestService()).basicRemove(otherEnd, msgs);
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
			case MetadataPackage.AUTHN_AUTHORITY_DESCRIPTOR_TYPE__AUTHN_QUERY_SERVICE:
				return getAuthnQueryService();
			case MetadataPackage.AUTHN_AUTHORITY_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE:
				return getAssertionIDRequestService();
			case MetadataPackage.AUTHN_AUTHORITY_DESCRIPTOR_TYPE__NAME_ID_FORMAT:
				return getNameIDFormat();
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
			case MetadataPackage.AUTHN_AUTHORITY_DESCRIPTOR_TYPE__AUTHN_QUERY_SERVICE:
				getAuthnQueryService().clear();
				getAuthnQueryService().addAll((Collection<? extends EndpointType>)newValue);
				return;
			case MetadataPackage.AUTHN_AUTHORITY_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE:
				getAssertionIDRequestService().clear();
				getAssertionIDRequestService().addAll((Collection<? extends EndpointType>)newValue);
				return;
			case MetadataPackage.AUTHN_AUTHORITY_DESCRIPTOR_TYPE__NAME_ID_FORMAT:
				getNameIDFormat().clear();
				getNameIDFormat().addAll((Collection<? extends String>)newValue);
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
			case MetadataPackage.AUTHN_AUTHORITY_DESCRIPTOR_TYPE__AUTHN_QUERY_SERVICE:
				getAuthnQueryService().clear();
				return;
			case MetadataPackage.AUTHN_AUTHORITY_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE:
				getAssertionIDRequestService().clear();
				return;
			case MetadataPackage.AUTHN_AUTHORITY_DESCRIPTOR_TYPE__NAME_ID_FORMAT:
				getNameIDFormat().clear();
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
			case MetadataPackage.AUTHN_AUTHORITY_DESCRIPTOR_TYPE__AUTHN_QUERY_SERVICE:
				return authnQueryService != null && !authnQueryService.isEmpty();
			case MetadataPackage.AUTHN_AUTHORITY_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE:
				return assertionIDRequestService != null && !assertionIDRequestService.isEmpty();
			case MetadataPackage.AUTHN_AUTHORITY_DESCRIPTOR_TYPE__NAME_ID_FORMAT:
				return nameIDFormat != null && !nameIDFormat.isEmpty();
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
		result.append(" (nameIDFormat: ");
		result.append(nameIDFormat);
		result.append(')');
		return result.toString();
	}

} //AuthnAuthorityDescriptorTypeImpl
