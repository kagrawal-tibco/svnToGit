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

import com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType;
import com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage;
import com.tibco.be.baas.security.authn.saml.model.metadata.PDPDescriptorType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>PDP Descriptor Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.PDPDescriptorTypeImpl#getAuthzService <em>Authz Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.PDPDescriptorTypeImpl#getAssertionIDRequestService <em>Assertion ID Request Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.PDPDescriptorTypeImpl#getNameIDFormat <em>Name ID Format</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PDPDescriptorTypeImpl extends RoleDescriptorTypeImpl implements PDPDescriptorType {
	/**
	 * The cached value of the '{@link #getAuthzService() <em>Authz Service</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthzService()
	 * @generated
	 * @ordered
	 */
	protected EList<EndpointType> authzService;

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
	protected PDPDescriptorTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetadataPackage.Literals.PDP_DESCRIPTOR_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EndpointType> getAuthzService() {
		if (authzService == null) {
			authzService = new EObjectContainmentEList<EndpointType>(EndpointType.class, this, MetadataPackage.PDP_DESCRIPTOR_TYPE__AUTHZ_SERVICE);
		}
		return authzService;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EndpointType> getAssertionIDRequestService() {
		if (assertionIDRequestService == null) {
			assertionIDRequestService = new EObjectContainmentEList<EndpointType>(EndpointType.class, this, MetadataPackage.PDP_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE);
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
			nameIDFormat = new EDataTypeEList<String>(String.class, this, MetadataPackage.PDP_DESCRIPTOR_TYPE__NAME_ID_FORMAT);
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
			case MetadataPackage.PDP_DESCRIPTOR_TYPE__AUTHZ_SERVICE:
				return ((InternalEList<?>)getAuthzService()).basicRemove(otherEnd, msgs);
			case MetadataPackage.PDP_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE:
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
			case MetadataPackage.PDP_DESCRIPTOR_TYPE__AUTHZ_SERVICE:
				return getAuthzService();
			case MetadataPackage.PDP_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE:
				return getAssertionIDRequestService();
			case MetadataPackage.PDP_DESCRIPTOR_TYPE__NAME_ID_FORMAT:
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
			case MetadataPackage.PDP_DESCRIPTOR_TYPE__AUTHZ_SERVICE:
				getAuthzService().clear();
				getAuthzService().addAll((Collection<? extends EndpointType>)newValue);
				return;
			case MetadataPackage.PDP_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE:
				getAssertionIDRequestService().clear();
				getAssertionIDRequestService().addAll((Collection<? extends EndpointType>)newValue);
				return;
			case MetadataPackage.PDP_DESCRIPTOR_TYPE__NAME_ID_FORMAT:
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
			case MetadataPackage.PDP_DESCRIPTOR_TYPE__AUTHZ_SERVICE:
				getAuthzService().clear();
				return;
			case MetadataPackage.PDP_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE:
				getAssertionIDRequestService().clear();
				return;
			case MetadataPackage.PDP_DESCRIPTOR_TYPE__NAME_ID_FORMAT:
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
			case MetadataPackage.PDP_DESCRIPTOR_TYPE__AUTHZ_SERVICE:
				return authzService != null && !authzService.isEmpty();
			case MetadataPackage.PDP_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE:
				return assertionIDRequestService != null && !assertionIDRequestService.isEmpty();
			case MetadataPackage.PDP_DESCRIPTOR_TYPE__NAME_ID_FORMAT:
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

} //PDPDescriptorTypeImpl
