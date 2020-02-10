/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.metadata.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType;
import com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType;
import com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IDPSSO Descriptor Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.IDPSSODescriptorTypeImpl#getSingleSignOnService <em>Single Sign On Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.IDPSSODescriptorTypeImpl#getNameIDMappingService <em>Name ID Mapping Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.IDPSSODescriptorTypeImpl#getAssertionIDRequestService <em>Assertion ID Request Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.IDPSSODescriptorTypeImpl#getAttributeProfile <em>Attribute Profile</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.IDPSSODescriptorTypeImpl#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.IDPSSODescriptorTypeImpl#isWantAuthnRequestsSigned <em>Want Authn Requests Signed</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IDPSSODescriptorTypeImpl extends SSODescriptorTypeImpl implements IDPSSODescriptorType {
	/**
	 * The cached value of the '{@link #getSingleSignOnService() <em>Single Sign On Service</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSingleSignOnService()
	 * @generated
	 * @ordered
	 */
	protected EList<EndpointType> singleSignOnService;

	/**
	 * The cached value of the '{@link #getNameIDMappingService() <em>Name ID Mapping Service</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNameIDMappingService()
	 * @generated
	 * @ordered
	 */
	protected EList<EndpointType> nameIDMappingService;

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
	 * The cached value of the '{@link #getAttributeProfile() <em>Attribute Profile</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeProfile()
	 * @generated
	 * @ordered
	 */
	protected EList<String> attributeProfile;

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
	 * The default value of the '{@link #isWantAuthnRequestsSigned() <em>Want Authn Requests Signed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWantAuthnRequestsSigned()
	 * @generated
	 * @ordered
	 */
	protected static final boolean WANT_AUTHN_REQUESTS_SIGNED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isWantAuthnRequestsSigned() <em>Want Authn Requests Signed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWantAuthnRequestsSigned()
	 * @generated
	 * @ordered
	 */
	protected boolean wantAuthnRequestsSigned = WANT_AUTHN_REQUESTS_SIGNED_EDEFAULT;

	/**
	 * This is true if the Want Authn Requests Signed attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean wantAuthnRequestsSignedESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IDPSSODescriptorTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetadataPackage.Literals.IDPSSO_DESCRIPTOR_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EndpointType> getSingleSignOnService() {
		if (singleSignOnService == null) {
			singleSignOnService = new EObjectContainmentEList<EndpointType>(EndpointType.class, this, MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__SINGLE_SIGN_ON_SERVICE);
		}
		return singleSignOnService;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EndpointType> getNameIDMappingService() {
		if (nameIDMappingService == null) {
			nameIDMappingService = new EObjectContainmentEList<EndpointType>(EndpointType.class, this, MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__NAME_ID_MAPPING_SERVICE);
		}
		return nameIDMappingService;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EndpointType> getAssertionIDRequestService() {
		if (assertionIDRequestService == null) {
			assertionIDRequestService = new EObjectContainmentEList<EndpointType>(EndpointType.class, this, MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE);
		}
		return assertionIDRequestService;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getAttributeProfile() {
		if (attributeProfile == null) {
			attributeProfile = new EDataTypeEList<String>(String.class, this, MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__ATTRIBUTE_PROFILE);
		}
		return attributeProfile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AttributeType> getAttribute() {
		if (attribute == null) {
			attribute = new EObjectContainmentEList<AttributeType>(AttributeType.class, this, MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__ATTRIBUTE);
		}
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isWantAuthnRequestsSigned() {
		return wantAuthnRequestsSigned;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWantAuthnRequestsSigned(boolean newWantAuthnRequestsSigned) {
		boolean oldWantAuthnRequestsSigned = wantAuthnRequestsSigned;
		wantAuthnRequestsSigned = newWantAuthnRequestsSigned;
		boolean oldWantAuthnRequestsSignedESet = wantAuthnRequestsSignedESet;
		wantAuthnRequestsSignedESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__WANT_AUTHN_REQUESTS_SIGNED, oldWantAuthnRequestsSigned, wantAuthnRequestsSigned, !oldWantAuthnRequestsSignedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetWantAuthnRequestsSigned() {
		boolean oldWantAuthnRequestsSigned = wantAuthnRequestsSigned;
		boolean oldWantAuthnRequestsSignedESet = wantAuthnRequestsSignedESet;
		wantAuthnRequestsSigned = WANT_AUTHN_REQUESTS_SIGNED_EDEFAULT;
		wantAuthnRequestsSignedESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__WANT_AUTHN_REQUESTS_SIGNED, oldWantAuthnRequestsSigned, WANT_AUTHN_REQUESTS_SIGNED_EDEFAULT, oldWantAuthnRequestsSignedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetWantAuthnRequestsSigned() {
		return wantAuthnRequestsSignedESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__SINGLE_SIGN_ON_SERVICE:
				return ((InternalEList<?>)getSingleSignOnService()).basicRemove(otherEnd, msgs);
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__NAME_ID_MAPPING_SERVICE:
				return ((InternalEList<?>)getNameIDMappingService()).basicRemove(otherEnd, msgs);
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE:
				return ((InternalEList<?>)getAssertionIDRequestService()).basicRemove(otherEnd, msgs);
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__ATTRIBUTE:
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
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__SINGLE_SIGN_ON_SERVICE:
				return getSingleSignOnService();
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__NAME_ID_MAPPING_SERVICE:
				return getNameIDMappingService();
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE:
				return getAssertionIDRequestService();
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__ATTRIBUTE_PROFILE:
				return getAttributeProfile();
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__ATTRIBUTE:
				return getAttribute();
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__WANT_AUTHN_REQUESTS_SIGNED:
				return isWantAuthnRequestsSigned() ? Boolean.TRUE : Boolean.FALSE;
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
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__SINGLE_SIGN_ON_SERVICE:
				getSingleSignOnService().clear();
				getSingleSignOnService().addAll((Collection<? extends EndpointType>)newValue);
				return;
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__NAME_ID_MAPPING_SERVICE:
				getNameIDMappingService().clear();
				getNameIDMappingService().addAll((Collection<? extends EndpointType>)newValue);
				return;
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE:
				getAssertionIDRequestService().clear();
				getAssertionIDRequestService().addAll((Collection<? extends EndpointType>)newValue);
				return;
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__ATTRIBUTE_PROFILE:
				getAttributeProfile().clear();
				getAttributeProfile().addAll((Collection<? extends String>)newValue);
				return;
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__ATTRIBUTE:
				getAttribute().clear();
				getAttribute().addAll((Collection<? extends AttributeType>)newValue);
				return;
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__WANT_AUTHN_REQUESTS_SIGNED:
				setWantAuthnRequestsSigned(((Boolean)newValue).booleanValue());
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
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__SINGLE_SIGN_ON_SERVICE:
				getSingleSignOnService().clear();
				return;
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__NAME_ID_MAPPING_SERVICE:
				getNameIDMappingService().clear();
				return;
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE:
				getAssertionIDRequestService().clear();
				return;
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__ATTRIBUTE_PROFILE:
				getAttributeProfile().clear();
				return;
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__ATTRIBUTE:
				getAttribute().clear();
				return;
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__WANT_AUTHN_REQUESTS_SIGNED:
				unsetWantAuthnRequestsSigned();
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
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__SINGLE_SIGN_ON_SERVICE:
				return singleSignOnService != null && !singleSignOnService.isEmpty();
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__NAME_ID_MAPPING_SERVICE:
				return nameIDMappingService != null && !nameIDMappingService.isEmpty();
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE:
				return assertionIDRequestService != null && !assertionIDRequestService.isEmpty();
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__ATTRIBUTE_PROFILE:
				return attributeProfile != null && !attributeProfile.isEmpty();
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__ATTRIBUTE:
				return attribute != null && !attribute.isEmpty();
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE__WANT_AUTHN_REQUESTS_SIGNED:
				return isSetWantAuthnRequestsSigned();
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
		result.append(" (attributeProfile: ");
		result.append(attributeProfile);
		result.append(", wantAuthnRequestsSigned: ");
		if (wantAuthnRequestsSignedESet) result.append(wantAuthnRequestsSigned); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //IDPSSODescriptorTypeImpl
