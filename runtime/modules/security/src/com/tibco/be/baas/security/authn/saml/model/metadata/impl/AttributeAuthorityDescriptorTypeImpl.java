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

import com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType;
import com.tibco.be.baas.security.authn.saml.model.metadata.AttributeAuthorityDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType;
import com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute Authority Descriptor Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AttributeAuthorityDescriptorTypeImpl#getAttributeService <em>Attribute Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AttributeAuthorityDescriptorTypeImpl#getAssertionIDRequestService <em>Assertion ID Request Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AttributeAuthorityDescriptorTypeImpl#getNameIDFormat <em>Name ID Format</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AttributeAuthorityDescriptorTypeImpl#getAttributeProfile <em>Attribute Profile</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AttributeAuthorityDescriptorTypeImpl#getAttribute <em>Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AttributeAuthorityDescriptorTypeImpl extends RoleDescriptorTypeImpl implements AttributeAuthorityDescriptorType {
	/**
	 * The cached value of the '{@link #getAttributeService() <em>Attribute Service</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeService()
	 * @generated
	 * @ordered
	 */
	protected EList<EndpointType> attributeService;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeAuthorityDescriptorTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetadataPackage.Literals.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EndpointType> getAttributeService() {
		if (attributeService == null) {
			attributeService = new EObjectContainmentEList<EndpointType>(EndpointType.class, this, MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ATTRIBUTE_SERVICE);
		}
		return attributeService;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EndpointType> getAssertionIDRequestService() {
		if (assertionIDRequestService == null) {
			assertionIDRequestService = new EObjectContainmentEList<EndpointType>(EndpointType.class, this, MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE);
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
			nameIDFormat = new EDataTypeEList<String>(String.class, this, MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__NAME_ID_FORMAT);
		}
		return nameIDFormat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getAttributeProfile() {
		if (attributeProfile == null) {
			attributeProfile = new EDataTypeEList<String>(String.class, this, MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ATTRIBUTE_PROFILE);
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
			attribute = new EObjectContainmentEList<AttributeType>(AttributeType.class, this, MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ATTRIBUTE);
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
			case MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ATTRIBUTE_SERVICE:
				return ((InternalEList<?>)getAttributeService()).basicRemove(otherEnd, msgs);
			case MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE:
				return ((InternalEList<?>)getAssertionIDRequestService()).basicRemove(otherEnd, msgs);
			case MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ATTRIBUTE:
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
			case MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ATTRIBUTE_SERVICE:
				return getAttributeService();
			case MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE:
				return getAssertionIDRequestService();
			case MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__NAME_ID_FORMAT:
				return getNameIDFormat();
			case MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ATTRIBUTE_PROFILE:
				return getAttributeProfile();
			case MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ATTRIBUTE:
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
			case MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ATTRIBUTE_SERVICE:
				getAttributeService().clear();
				getAttributeService().addAll((Collection<? extends EndpointType>)newValue);
				return;
			case MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE:
				getAssertionIDRequestService().clear();
				getAssertionIDRequestService().addAll((Collection<? extends EndpointType>)newValue);
				return;
			case MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__NAME_ID_FORMAT:
				getNameIDFormat().clear();
				getNameIDFormat().addAll((Collection<? extends String>)newValue);
				return;
			case MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ATTRIBUTE_PROFILE:
				getAttributeProfile().clear();
				getAttributeProfile().addAll((Collection<? extends String>)newValue);
				return;
			case MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ATTRIBUTE:
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
			case MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ATTRIBUTE_SERVICE:
				getAttributeService().clear();
				return;
			case MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE:
				getAssertionIDRequestService().clear();
				return;
			case MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__NAME_ID_FORMAT:
				getNameIDFormat().clear();
				return;
			case MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ATTRIBUTE_PROFILE:
				getAttributeProfile().clear();
				return;
			case MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ATTRIBUTE:
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
			case MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ATTRIBUTE_SERVICE:
				return attributeService != null && !attributeService.isEmpty();
			case MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE:
				return assertionIDRequestService != null && !assertionIDRequestService.isEmpty();
			case MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__NAME_ID_FORMAT:
				return nameIDFormat != null && !nameIDFormat.isEmpty();
			case MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ATTRIBUTE_PROFILE:
				return attributeProfile != null && !attributeProfile.isEmpty();
			case MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ATTRIBUTE:
				return attribute != null && !attribute.isEmpty();
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
		result.append(", attributeProfile: ");
		result.append(attributeProfile);
		result.append(')');
		return result.toString();
	}

} //AttributeAuthorityDescriptorTypeImpl
