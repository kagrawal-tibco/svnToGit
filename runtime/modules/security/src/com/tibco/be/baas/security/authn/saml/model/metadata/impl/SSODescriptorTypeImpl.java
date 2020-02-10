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
import com.tibco.be.baas.security.authn.saml.model.metadata.IndexedEndpointType;
import com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage;
import com.tibco.be.baas.security.authn.saml.model.metadata.SSODescriptorType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>SSO Descriptor Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.SSODescriptorTypeImpl#getArtifactResolutionService <em>Artifact Resolution Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.SSODescriptorTypeImpl#getSingleLogoutService <em>Single Logout Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.SSODescriptorTypeImpl#getManageNameIDService <em>Manage Name ID Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.SSODescriptorTypeImpl#getNameIDFormat <em>Name ID Format</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class SSODescriptorTypeImpl extends RoleDescriptorTypeImpl implements SSODescriptorType {
	/**
	 * The cached value of the '{@link #getArtifactResolutionService() <em>Artifact Resolution Service</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArtifactResolutionService()
	 * @generated
	 * @ordered
	 */
	protected EList<IndexedEndpointType> artifactResolutionService;

	/**
	 * The cached value of the '{@link #getSingleLogoutService() <em>Single Logout Service</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSingleLogoutService()
	 * @generated
	 * @ordered
	 */
	protected EList<EndpointType> singleLogoutService;

	/**
	 * The cached value of the '{@link #getManageNameIDService() <em>Manage Name ID Service</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getManageNameIDService()
	 * @generated
	 * @ordered
	 */
	protected EList<EndpointType> manageNameIDService;

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
	protected SSODescriptorTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetadataPackage.Literals.SSO_DESCRIPTOR_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IndexedEndpointType> getArtifactResolutionService() {
		if (artifactResolutionService == null) {
			artifactResolutionService = new EObjectContainmentEList<IndexedEndpointType>(IndexedEndpointType.class, this, MetadataPackage.SSO_DESCRIPTOR_TYPE__ARTIFACT_RESOLUTION_SERVICE);
		}
		return artifactResolutionService;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EndpointType> getSingleLogoutService() {
		if (singleLogoutService == null) {
			singleLogoutService = new EObjectContainmentEList<EndpointType>(EndpointType.class, this, MetadataPackage.SSO_DESCRIPTOR_TYPE__SINGLE_LOGOUT_SERVICE);
		}
		return singleLogoutService;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EndpointType> getManageNameIDService() {
		if (manageNameIDService == null) {
			manageNameIDService = new EObjectContainmentEList<EndpointType>(EndpointType.class, this, MetadataPackage.SSO_DESCRIPTOR_TYPE__MANAGE_NAME_ID_SERVICE);
		}
		return manageNameIDService;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getNameIDFormat() {
		if (nameIDFormat == null) {
			nameIDFormat = new EDataTypeEList<String>(String.class, this, MetadataPackage.SSO_DESCRIPTOR_TYPE__NAME_ID_FORMAT);
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
			case MetadataPackage.SSO_DESCRIPTOR_TYPE__ARTIFACT_RESOLUTION_SERVICE:
				return ((InternalEList<?>)getArtifactResolutionService()).basicRemove(otherEnd, msgs);
			case MetadataPackage.SSO_DESCRIPTOR_TYPE__SINGLE_LOGOUT_SERVICE:
				return ((InternalEList<?>)getSingleLogoutService()).basicRemove(otherEnd, msgs);
			case MetadataPackage.SSO_DESCRIPTOR_TYPE__MANAGE_NAME_ID_SERVICE:
				return ((InternalEList<?>)getManageNameIDService()).basicRemove(otherEnd, msgs);
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
			case MetadataPackage.SSO_DESCRIPTOR_TYPE__ARTIFACT_RESOLUTION_SERVICE:
				return getArtifactResolutionService();
			case MetadataPackage.SSO_DESCRIPTOR_TYPE__SINGLE_LOGOUT_SERVICE:
				return getSingleLogoutService();
			case MetadataPackage.SSO_DESCRIPTOR_TYPE__MANAGE_NAME_ID_SERVICE:
				return getManageNameIDService();
			case MetadataPackage.SSO_DESCRIPTOR_TYPE__NAME_ID_FORMAT:
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
			case MetadataPackage.SSO_DESCRIPTOR_TYPE__ARTIFACT_RESOLUTION_SERVICE:
				getArtifactResolutionService().clear();
				getArtifactResolutionService().addAll((Collection<? extends IndexedEndpointType>)newValue);
				return;
			case MetadataPackage.SSO_DESCRIPTOR_TYPE__SINGLE_LOGOUT_SERVICE:
				getSingleLogoutService().clear();
				getSingleLogoutService().addAll((Collection<? extends EndpointType>)newValue);
				return;
			case MetadataPackage.SSO_DESCRIPTOR_TYPE__MANAGE_NAME_ID_SERVICE:
				getManageNameIDService().clear();
				getManageNameIDService().addAll((Collection<? extends EndpointType>)newValue);
				return;
			case MetadataPackage.SSO_DESCRIPTOR_TYPE__NAME_ID_FORMAT:
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
			case MetadataPackage.SSO_DESCRIPTOR_TYPE__ARTIFACT_RESOLUTION_SERVICE:
				getArtifactResolutionService().clear();
				return;
			case MetadataPackage.SSO_DESCRIPTOR_TYPE__SINGLE_LOGOUT_SERVICE:
				getSingleLogoutService().clear();
				return;
			case MetadataPackage.SSO_DESCRIPTOR_TYPE__MANAGE_NAME_ID_SERVICE:
				getManageNameIDService().clear();
				return;
			case MetadataPackage.SSO_DESCRIPTOR_TYPE__NAME_ID_FORMAT:
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
			case MetadataPackage.SSO_DESCRIPTOR_TYPE__ARTIFACT_RESOLUTION_SERVICE:
				return artifactResolutionService != null && !artifactResolutionService.isEmpty();
			case MetadataPackage.SSO_DESCRIPTOR_TYPE__SINGLE_LOGOUT_SERVICE:
				return singleLogoutService != null && !singleLogoutService.isEmpty();
			case MetadataPackage.SSO_DESCRIPTOR_TYPE__MANAGE_NAME_ID_SERVICE:
				return manageNameIDService != null && !manageNameIDService.isEmpty();
			case MetadataPackage.SSO_DESCRIPTOR_TYPE__NAME_ID_FORMAT:
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

} //SSODescriptorTypeImpl
