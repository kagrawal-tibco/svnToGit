/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.metadata.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.baas.security.authn.saml.model.metadata.AdditionalMetadataLocationType;
import com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.AttributeAuthorityDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType;
import com.tibco.be.baas.security.authn.saml.model.metadata.AuthnAuthorityDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.ContactType;
import com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot;
import com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType;
import com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.ExtensionsType;
import com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.IndexedEndpointType;
import com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedNameType;
import com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedURIType;
import com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage;
import com.tibco.be.baas.security.authn.saml.model.metadata.OrganizationType;
import com.tibco.be.baas.security.authn.saml.model.metadata.PDPDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.RequestedAttributeType;
import com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getAdditionalMetadataLocation <em>Additional Metadata Location</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getAffiliateMember <em>Affiliate Member</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getAffiliationDescriptor <em>Affiliation Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getArtifactResolutionService <em>Artifact Resolution Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getAssertionConsumerService <em>Assertion Consumer Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getAssertionIDRequestService <em>Assertion ID Request Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getAttributeAuthorityDescriptor <em>Attribute Authority Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getAttributeConsumingService <em>Attribute Consuming Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getAttributeProfile <em>Attribute Profile</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getAttributeService <em>Attribute Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getAuthnAuthorityDescriptor <em>Authn Authority Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getAuthnQueryService <em>Authn Query Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getAuthzService <em>Authz Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getCompany <em>Company</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getContactPerson <em>Contact Person</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getEmailAddress <em>Email Address</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getEntitiesDescriptor <em>Entities Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getEntityDescriptor <em>Entity Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getGivenName <em>Given Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getIDPSSODescriptor <em>IDPSSO Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getManageNameIDService <em>Manage Name ID Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getNameIDFormat <em>Name ID Format</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getNameIDMappingService <em>Name ID Mapping Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getOrganization <em>Organization</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getOrganizationDisplayName <em>Organization Display Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getOrganizationName <em>Organization Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getOrganizationURL <em>Organization URL</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getPDPDescriptor <em>PDP Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getRequestedAttribute <em>Requested Attribute</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getRoleDescriptor <em>Role Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getServiceDescription <em>Service Description</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getServiceName <em>Service Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getSingleLogoutService <em>Single Logout Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getSingleSignOnService <em>Single Sign On Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getSPSSODescriptor <em>SPSSO Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getSurName <em>Sur Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl#getTelephoneNumber <em>Telephone Number</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DocumentRootImpl extends EObjectImpl implements DocumentRoot {
	/**
	 * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMixed()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap mixed;

	/**
	 * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXMLNSPrefixMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xMLNSPrefixMap;

	/**
	 * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXSISchemaLocation()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xSISchemaLocation;

	/**
	 * The default value of the '{@link #getAffiliateMember() <em>Affiliate Member</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAffiliateMember()
	 * @generated
	 * @ordered
	 */
	protected static final String AFFILIATE_MEMBER_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getAttributeProfile() <em>Attribute Profile</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeProfile()
	 * @generated
	 * @ordered
	 */
	protected static final String ATTRIBUTE_PROFILE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getCompany() <em>Company</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompany()
	 * @generated
	 * @ordered
	 */
	protected static final String COMPANY_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getEmailAddress() <em>Email Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmailAddress()
	 * @generated
	 * @ordered
	 */
	protected static final String EMAIL_ADDRESS_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getGivenName() <em>Given Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGivenName()
	 * @generated
	 * @ordered
	 */
	protected static final String GIVEN_NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getNameIDFormat() <em>Name ID Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNameIDFormat()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_ID_FORMAT_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSurName() <em>Sur Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSurName()
	 * @generated
	 * @ordered
	 */
	protected static final String SUR_NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getTelephoneNumber() <em>Telephone Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTelephoneNumber()
	 * @generated
	 * @ordered
	 */
	protected static final String TELEPHONE_NUMBER_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DocumentRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetadataPackage.Literals.DOCUMENT_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, MetadataPackage.DOCUMENT_ROOT__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getXMLNSPrefixMap() {
		if (xMLNSPrefixMap == null) {
			xMLNSPrefixMap = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, MetadataPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		}
		return xMLNSPrefixMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getXSISchemaLocation() {
		if (xSISchemaLocation == null) {
			xSISchemaLocation = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, MetadataPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		}
		return xSISchemaLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdditionalMetadataLocationType getAdditionalMetadataLocation() {
		return (AdditionalMetadataLocationType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__ADDITIONAL_METADATA_LOCATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAdditionalMetadataLocation(AdditionalMetadataLocationType newAdditionalMetadataLocation, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__ADDITIONAL_METADATA_LOCATION, newAdditionalMetadataLocation, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAdditionalMetadataLocation(AdditionalMetadataLocationType newAdditionalMetadataLocation) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__ADDITIONAL_METADATA_LOCATION, newAdditionalMetadataLocation);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAffiliateMember() {
		return (String)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__AFFILIATE_MEMBER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAffiliateMember(String newAffiliateMember) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__AFFILIATE_MEMBER, newAffiliateMember);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AffiliationDescriptorType getAffiliationDescriptor() {
		return (AffiliationDescriptorType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__AFFILIATION_DESCRIPTOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAffiliationDescriptor(AffiliationDescriptorType newAffiliationDescriptor, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__AFFILIATION_DESCRIPTOR, newAffiliationDescriptor, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAffiliationDescriptor(AffiliationDescriptorType newAffiliationDescriptor) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__AFFILIATION_DESCRIPTOR, newAffiliationDescriptor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IndexedEndpointType getArtifactResolutionService() {
		return (IndexedEndpointType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__ARTIFACT_RESOLUTION_SERVICE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetArtifactResolutionService(IndexedEndpointType newArtifactResolutionService, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__ARTIFACT_RESOLUTION_SERVICE, newArtifactResolutionService, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArtifactResolutionService(IndexedEndpointType newArtifactResolutionService) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__ARTIFACT_RESOLUTION_SERVICE, newArtifactResolutionService);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IndexedEndpointType getAssertionConsumerService() {
		return (IndexedEndpointType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__ASSERTION_CONSUMER_SERVICE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAssertionConsumerService(IndexedEndpointType newAssertionConsumerService, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__ASSERTION_CONSUMER_SERVICE, newAssertionConsumerService, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAssertionConsumerService(IndexedEndpointType newAssertionConsumerService) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__ASSERTION_CONSUMER_SERVICE, newAssertionConsumerService);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EndpointType getAssertionIDRequestService() {
		return (EndpointType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__ASSERTION_ID_REQUEST_SERVICE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAssertionIDRequestService(EndpointType newAssertionIDRequestService, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__ASSERTION_ID_REQUEST_SERVICE, newAssertionIDRequestService, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAssertionIDRequestService(EndpointType newAssertionIDRequestService) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__ASSERTION_ID_REQUEST_SERVICE, newAssertionIDRequestService);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeAuthorityDescriptorType getAttributeAuthorityDescriptor() {
		return (AttributeAuthorityDescriptorType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__ATTRIBUTE_AUTHORITY_DESCRIPTOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttributeAuthorityDescriptor(AttributeAuthorityDescriptorType newAttributeAuthorityDescriptor, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__ATTRIBUTE_AUTHORITY_DESCRIPTOR, newAttributeAuthorityDescriptor, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttributeAuthorityDescriptor(AttributeAuthorityDescriptorType newAttributeAuthorityDescriptor) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__ATTRIBUTE_AUTHORITY_DESCRIPTOR, newAttributeAuthorityDescriptor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeConsumingServiceType getAttributeConsumingService() {
		return (AttributeConsumingServiceType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__ATTRIBUTE_CONSUMING_SERVICE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttributeConsumingService(AttributeConsumingServiceType newAttributeConsumingService, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__ATTRIBUTE_CONSUMING_SERVICE, newAttributeConsumingService, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttributeConsumingService(AttributeConsumingServiceType newAttributeConsumingService) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__ATTRIBUTE_CONSUMING_SERVICE, newAttributeConsumingService);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAttributeProfile() {
		return (String)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__ATTRIBUTE_PROFILE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttributeProfile(String newAttributeProfile) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__ATTRIBUTE_PROFILE, newAttributeProfile);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EndpointType getAttributeService() {
		return (EndpointType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__ATTRIBUTE_SERVICE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttributeService(EndpointType newAttributeService, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__ATTRIBUTE_SERVICE, newAttributeService, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttributeService(EndpointType newAttributeService) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__ATTRIBUTE_SERVICE, newAttributeService);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthnAuthorityDescriptorType getAuthnAuthorityDescriptor() {
		return (AuthnAuthorityDescriptorType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__AUTHN_AUTHORITY_DESCRIPTOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAuthnAuthorityDescriptor(AuthnAuthorityDescriptorType newAuthnAuthorityDescriptor, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__AUTHN_AUTHORITY_DESCRIPTOR, newAuthnAuthorityDescriptor, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthnAuthorityDescriptor(AuthnAuthorityDescriptorType newAuthnAuthorityDescriptor) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__AUTHN_AUTHORITY_DESCRIPTOR, newAuthnAuthorityDescriptor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EndpointType getAuthnQueryService() {
		return (EndpointType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__AUTHN_QUERY_SERVICE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAuthnQueryService(EndpointType newAuthnQueryService, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__AUTHN_QUERY_SERVICE, newAuthnQueryService, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthnQueryService(EndpointType newAuthnQueryService) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__AUTHN_QUERY_SERVICE, newAuthnQueryService);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EndpointType getAuthzService() {
		return (EndpointType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__AUTHZ_SERVICE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAuthzService(EndpointType newAuthzService, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__AUTHZ_SERVICE, newAuthzService, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthzService(EndpointType newAuthzService) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__AUTHZ_SERVICE, newAuthzService);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCompany() {
		return (String)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__COMPANY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompany(String newCompany) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__COMPANY, newCompany);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContactType getContactPerson() {
		return (ContactType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__CONTACT_PERSON, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContactPerson(ContactType newContactPerson, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__CONTACT_PERSON, newContactPerson, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContactPerson(ContactType newContactPerson) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__CONTACT_PERSON, newContactPerson);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEmailAddress() {
		return (String)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__EMAIL_ADDRESS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEmailAddress(String newEmailAddress) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__EMAIL_ADDRESS, newEmailAddress);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntitiesDescriptorType getEntitiesDescriptor() {
		return (EntitiesDescriptorType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__ENTITIES_DESCRIPTOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEntitiesDescriptor(EntitiesDescriptorType newEntitiesDescriptor, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__ENTITIES_DESCRIPTOR, newEntitiesDescriptor, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntitiesDescriptor(EntitiesDescriptorType newEntitiesDescriptor) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__ENTITIES_DESCRIPTOR, newEntitiesDescriptor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntityDescriptorType getEntityDescriptor() {
		return (EntityDescriptorType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__ENTITY_DESCRIPTOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEntityDescriptor(EntityDescriptorType newEntityDescriptor, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__ENTITY_DESCRIPTOR, newEntityDescriptor, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntityDescriptor(EntityDescriptorType newEntityDescriptor) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__ENTITY_DESCRIPTOR, newEntityDescriptor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExtensionsType getExtensions() {
		return (ExtensionsType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__EXTENSIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExtensions(ExtensionsType newExtensions, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__EXTENSIONS, newExtensions, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtensions(ExtensionsType newExtensions) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__EXTENSIONS, newExtensions);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getGivenName() {
		return (String)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__GIVEN_NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGivenName(String newGivenName) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__GIVEN_NAME, newGivenName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IDPSSODescriptorType getIDPSSODescriptor() {
		return (IDPSSODescriptorType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__IDPSSO_DESCRIPTOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIDPSSODescriptor(IDPSSODescriptorType newIDPSSODescriptor, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__IDPSSO_DESCRIPTOR, newIDPSSODescriptor, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIDPSSODescriptor(IDPSSODescriptorType newIDPSSODescriptor) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__IDPSSO_DESCRIPTOR, newIDPSSODescriptor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EndpointType getManageNameIDService() {
		return (EndpointType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__MANAGE_NAME_ID_SERVICE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetManageNameIDService(EndpointType newManageNameIDService, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__MANAGE_NAME_ID_SERVICE, newManageNameIDService, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setManageNameIDService(EndpointType newManageNameIDService) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__MANAGE_NAME_ID_SERVICE, newManageNameIDService);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNameIDFormat() {
		return (String)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__NAME_ID_FORMAT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNameIDFormat(String newNameIDFormat) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__NAME_ID_FORMAT, newNameIDFormat);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EndpointType getNameIDMappingService() {
		return (EndpointType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__NAME_ID_MAPPING_SERVICE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNameIDMappingService(EndpointType newNameIDMappingService, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__NAME_ID_MAPPING_SERVICE, newNameIDMappingService, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNameIDMappingService(EndpointType newNameIDMappingService) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__NAME_ID_MAPPING_SERVICE, newNameIDMappingService);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OrganizationType getOrganization() {
		return (OrganizationType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__ORGANIZATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOrganization(OrganizationType newOrganization, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__ORGANIZATION, newOrganization, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOrganization(OrganizationType newOrganization) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__ORGANIZATION, newOrganization);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalizedNameType getOrganizationDisplayName() {
		return (LocalizedNameType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__ORGANIZATION_DISPLAY_NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOrganizationDisplayName(LocalizedNameType newOrganizationDisplayName, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__ORGANIZATION_DISPLAY_NAME, newOrganizationDisplayName, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOrganizationDisplayName(LocalizedNameType newOrganizationDisplayName) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__ORGANIZATION_DISPLAY_NAME, newOrganizationDisplayName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalizedNameType getOrganizationName() {
		return (LocalizedNameType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__ORGANIZATION_NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOrganizationName(LocalizedNameType newOrganizationName, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__ORGANIZATION_NAME, newOrganizationName, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOrganizationName(LocalizedNameType newOrganizationName) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__ORGANIZATION_NAME, newOrganizationName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalizedURIType getOrganizationURL() {
		return (LocalizedURIType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__ORGANIZATION_URL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOrganizationURL(LocalizedURIType newOrganizationURL, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__ORGANIZATION_URL, newOrganizationURL, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOrganizationURL(LocalizedURIType newOrganizationURL) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__ORGANIZATION_URL, newOrganizationURL);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PDPDescriptorType getPDPDescriptor() {
		return (PDPDescriptorType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__PDP_DESCRIPTOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPDPDescriptor(PDPDescriptorType newPDPDescriptor, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__PDP_DESCRIPTOR, newPDPDescriptor, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPDPDescriptor(PDPDescriptorType newPDPDescriptor) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__PDP_DESCRIPTOR, newPDPDescriptor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequestedAttributeType getRequestedAttribute() {
		return (RequestedAttributeType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__REQUESTED_ATTRIBUTE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRequestedAttribute(RequestedAttributeType newRequestedAttribute, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__REQUESTED_ATTRIBUTE, newRequestedAttribute, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRequestedAttribute(RequestedAttributeType newRequestedAttribute) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__REQUESTED_ATTRIBUTE, newRequestedAttribute);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RoleDescriptorType getRoleDescriptor() {
		return (RoleDescriptorType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__ROLE_DESCRIPTOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRoleDescriptor(RoleDescriptorType newRoleDescriptor, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__ROLE_DESCRIPTOR, newRoleDescriptor, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRoleDescriptor(RoleDescriptorType newRoleDescriptor) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__ROLE_DESCRIPTOR, newRoleDescriptor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalizedNameType getServiceDescription() {
		return (LocalizedNameType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__SERVICE_DESCRIPTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetServiceDescription(LocalizedNameType newServiceDescription, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__SERVICE_DESCRIPTION, newServiceDescription, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setServiceDescription(LocalizedNameType newServiceDescription) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__SERVICE_DESCRIPTION, newServiceDescription);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalizedNameType getServiceName() {
		return (LocalizedNameType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__SERVICE_NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetServiceName(LocalizedNameType newServiceName, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__SERVICE_NAME, newServiceName, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setServiceName(LocalizedNameType newServiceName) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__SERVICE_NAME, newServiceName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EndpointType getSingleLogoutService() {
		return (EndpointType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__SINGLE_LOGOUT_SERVICE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSingleLogoutService(EndpointType newSingleLogoutService, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__SINGLE_LOGOUT_SERVICE, newSingleLogoutService, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSingleLogoutService(EndpointType newSingleLogoutService) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__SINGLE_LOGOUT_SERVICE, newSingleLogoutService);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EndpointType getSingleSignOnService() {
		return (EndpointType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__SINGLE_SIGN_ON_SERVICE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSingleSignOnService(EndpointType newSingleSignOnService, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__SINGLE_SIGN_ON_SERVICE, newSingleSignOnService, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSingleSignOnService(EndpointType newSingleSignOnService) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__SINGLE_SIGN_ON_SERVICE, newSingleSignOnService);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SPSSODescriptorType getSPSSODescriptor() {
		return (SPSSODescriptorType)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__SPSSO_DESCRIPTOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSPSSODescriptor(SPSSODescriptorType newSPSSODescriptor, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(MetadataPackage.Literals.DOCUMENT_ROOT__SPSSO_DESCRIPTOR, newSPSSODescriptor, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSPSSODescriptor(SPSSODescriptorType newSPSSODescriptor) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__SPSSO_DESCRIPTOR, newSPSSODescriptor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSurName() {
		return (String)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__SUR_NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSurName(String newSurName) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__SUR_NAME, newSurName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTelephoneNumber() {
		return (String)getMixed().get(MetadataPackage.Literals.DOCUMENT_ROOT__TELEPHONE_NUMBER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTelephoneNumber(String newTelephoneNumber) {
		((FeatureMap.Internal)getMixed()).set(MetadataPackage.Literals.DOCUMENT_ROOT__TELEPHONE_NUMBER, newTelephoneNumber);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MetadataPackage.DOCUMENT_ROOT__MIXED:
				return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
			case MetadataPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return ((InternalEList<?>)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
			case MetadataPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return ((InternalEList<?>)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
			case MetadataPackage.DOCUMENT_ROOT__ADDITIONAL_METADATA_LOCATION:
				return basicSetAdditionalMetadataLocation(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__AFFILIATION_DESCRIPTOR:
				return basicSetAffiliationDescriptor(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__ARTIFACT_RESOLUTION_SERVICE:
				return basicSetArtifactResolutionService(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__ASSERTION_CONSUMER_SERVICE:
				return basicSetAssertionConsumerService(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__ASSERTION_ID_REQUEST_SERVICE:
				return basicSetAssertionIDRequestService(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__ATTRIBUTE_AUTHORITY_DESCRIPTOR:
				return basicSetAttributeAuthorityDescriptor(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__ATTRIBUTE_CONSUMING_SERVICE:
				return basicSetAttributeConsumingService(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__ATTRIBUTE_SERVICE:
				return basicSetAttributeService(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__AUTHN_AUTHORITY_DESCRIPTOR:
				return basicSetAuthnAuthorityDescriptor(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__AUTHN_QUERY_SERVICE:
				return basicSetAuthnQueryService(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__AUTHZ_SERVICE:
				return basicSetAuthzService(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__CONTACT_PERSON:
				return basicSetContactPerson(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__ENTITIES_DESCRIPTOR:
				return basicSetEntitiesDescriptor(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__ENTITY_DESCRIPTOR:
				return basicSetEntityDescriptor(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__EXTENSIONS:
				return basicSetExtensions(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__IDPSSO_DESCRIPTOR:
				return basicSetIDPSSODescriptor(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__MANAGE_NAME_ID_SERVICE:
				return basicSetManageNameIDService(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__NAME_ID_MAPPING_SERVICE:
				return basicSetNameIDMappingService(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__ORGANIZATION:
				return basicSetOrganization(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__ORGANIZATION_DISPLAY_NAME:
				return basicSetOrganizationDisplayName(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__ORGANIZATION_NAME:
				return basicSetOrganizationName(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__ORGANIZATION_URL:
				return basicSetOrganizationURL(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__PDP_DESCRIPTOR:
				return basicSetPDPDescriptor(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__REQUESTED_ATTRIBUTE:
				return basicSetRequestedAttribute(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__ROLE_DESCRIPTOR:
				return basicSetRoleDescriptor(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__SERVICE_DESCRIPTION:
				return basicSetServiceDescription(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__SERVICE_NAME:
				return basicSetServiceName(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__SINGLE_LOGOUT_SERVICE:
				return basicSetSingleLogoutService(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__SINGLE_SIGN_ON_SERVICE:
				return basicSetSingleSignOnService(null, msgs);
			case MetadataPackage.DOCUMENT_ROOT__SPSSO_DESCRIPTOR:
				return basicSetSPSSODescriptor(null, msgs);
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
			case MetadataPackage.DOCUMENT_ROOT__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case MetadataPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				if (coreType) return getXMLNSPrefixMap();
				else return getXMLNSPrefixMap().map();
			case MetadataPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				if (coreType) return getXSISchemaLocation();
				else return getXSISchemaLocation().map();
			case MetadataPackage.DOCUMENT_ROOT__ADDITIONAL_METADATA_LOCATION:
				return getAdditionalMetadataLocation();
			case MetadataPackage.DOCUMENT_ROOT__AFFILIATE_MEMBER:
				return getAffiliateMember();
			case MetadataPackage.DOCUMENT_ROOT__AFFILIATION_DESCRIPTOR:
				return getAffiliationDescriptor();
			case MetadataPackage.DOCUMENT_ROOT__ARTIFACT_RESOLUTION_SERVICE:
				return getArtifactResolutionService();
			case MetadataPackage.DOCUMENT_ROOT__ASSERTION_CONSUMER_SERVICE:
				return getAssertionConsumerService();
			case MetadataPackage.DOCUMENT_ROOT__ASSERTION_ID_REQUEST_SERVICE:
				return getAssertionIDRequestService();
			case MetadataPackage.DOCUMENT_ROOT__ATTRIBUTE_AUTHORITY_DESCRIPTOR:
				return getAttributeAuthorityDescriptor();
			case MetadataPackage.DOCUMENT_ROOT__ATTRIBUTE_CONSUMING_SERVICE:
				return getAttributeConsumingService();
			case MetadataPackage.DOCUMENT_ROOT__ATTRIBUTE_PROFILE:
				return getAttributeProfile();
			case MetadataPackage.DOCUMENT_ROOT__ATTRIBUTE_SERVICE:
				return getAttributeService();
			case MetadataPackage.DOCUMENT_ROOT__AUTHN_AUTHORITY_DESCRIPTOR:
				return getAuthnAuthorityDescriptor();
			case MetadataPackage.DOCUMENT_ROOT__AUTHN_QUERY_SERVICE:
				return getAuthnQueryService();
			case MetadataPackage.DOCUMENT_ROOT__AUTHZ_SERVICE:
				return getAuthzService();
			case MetadataPackage.DOCUMENT_ROOT__COMPANY:
				return getCompany();
			case MetadataPackage.DOCUMENT_ROOT__CONTACT_PERSON:
				return getContactPerson();
			case MetadataPackage.DOCUMENT_ROOT__EMAIL_ADDRESS:
				return getEmailAddress();
			case MetadataPackage.DOCUMENT_ROOT__ENTITIES_DESCRIPTOR:
				return getEntitiesDescriptor();
			case MetadataPackage.DOCUMENT_ROOT__ENTITY_DESCRIPTOR:
				return getEntityDescriptor();
			case MetadataPackage.DOCUMENT_ROOT__EXTENSIONS:
				return getExtensions();
			case MetadataPackage.DOCUMENT_ROOT__GIVEN_NAME:
				return getGivenName();
			case MetadataPackage.DOCUMENT_ROOT__IDPSSO_DESCRIPTOR:
				return getIDPSSODescriptor();
			case MetadataPackage.DOCUMENT_ROOT__MANAGE_NAME_ID_SERVICE:
				return getManageNameIDService();
			case MetadataPackage.DOCUMENT_ROOT__NAME_ID_FORMAT:
				return getNameIDFormat();
			case MetadataPackage.DOCUMENT_ROOT__NAME_ID_MAPPING_SERVICE:
				return getNameIDMappingService();
			case MetadataPackage.DOCUMENT_ROOT__ORGANIZATION:
				return getOrganization();
			case MetadataPackage.DOCUMENT_ROOT__ORGANIZATION_DISPLAY_NAME:
				return getOrganizationDisplayName();
			case MetadataPackage.DOCUMENT_ROOT__ORGANIZATION_NAME:
				return getOrganizationName();
			case MetadataPackage.DOCUMENT_ROOT__ORGANIZATION_URL:
				return getOrganizationURL();
			case MetadataPackage.DOCUMENT_ROOT__PDP_DESCRIPTOR:
				return getPDPDescriptor();
			case MetadataPackage.DOCUMENT_ROOT__REQUESTED_ATTRIBUTE:
				return getRequestedAttribute();
			case MetadataPackage.DOCUMENT_ROOT__ROLE_DESCRIPTOR:
				return getRoleDescriptor();
			case MetadataPackage.DOCUMENT_ROOT__SERVICE_DESCRIPTION:
				return getServiceDescription();
			case MetadataPackage.DOCUMENT_ROOT__SERVICE_NAME:
				return getServiceName();
			case MetadataPackage.DOCUMENT_ROOT__SINGLE_LOGOUT_SERVICE:
				return getSingleLogoutService();
			case MetadataPackage.DOCUMENT_ROOT__SINGLE_SIGN_ON_SERVICE:
				return getSingleSignOnService();
			case MetadataPackage.DOCUMENT_ROOT__SPSSO_DESCRIPTOR:
				return getSPSSODescriptor();
			case MetadataPackage.DOCUMENT_ROOT__SUR_NAME:
				return getSurName();
			case MetadataPackage.DOCUMENT_ROOT__TELEPHONE_NUMBER:
				return getTelephoneNumber();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MetadataPackage.DOCUMENT_ROOT__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ADDITIONAL_METADATA_LOCATION:
				setAdditionalMetadataLocation((AdditionalMetadataLocationType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__AFFILIATE_MEMBER:
				setAffiliateMember((String)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__AFFILIATION_DESCRIPTOR:
				setAffiliationDescriptor((AffiliationDescriptorType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ARTIFACT_RESOLUTION_SERVICE:
				setArtifactResolutionService((IndexedEndpointType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ASSERTION_CONSUMER_SERVICE:
				setAssertionConsumerService((IndexedEndpointType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ASSERTION_ID_REQUEST_SERVICE:
				setAssertionIDRequestService((EndpointType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ATTRIBUTE_AUTHORITY_DESCRIPTOR:
				setAttributeAuthorityDescriptor((AttributeAuthorityDescriptorType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ATTRIBUTE_CONSUMING_SERVICE:
				setAttributeConsumingService((AttributeConsumingServiceType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ATTRIBUTE_PROFILE:
				setAttributeProfile((String)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ATTRIBUTE_SERVICE:
				setAttributeService((EndpointType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__AUTHN_AUTHORITY_DESCRIPTOR:
				setAuthnAuthorityDescriptor((AuthnAuthorityDescriptorType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__AUTHN_QUERY_SERVICE:
				setAuthnQueryService((EndpointType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__AUTHZ_SERVICE:
				setAuthzService((EndpointType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__COMPANY:
				setCompany((String)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__CONTACT_PERSON:
				setContactPerson((ContactType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__EMAIL_ADDRESS:
				setEmailAddress((String)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ENTITIES_DESCRIPTOR:
				setEntitiesDescriptor((EntitiesDescriptorType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ENTITY_DESCRIPTOR:
				setEntityDescriptor((EntityDescriptorType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__EXTENSIONS:
				setExtensions((ExtensionsType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__GIVEN_NAME:
				setGivenName((String)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__IDPSSO_DESCRIPTOR:
				setIDPSSODescriptor((IDPSSODescriptorType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__MANAGE_NAME_ID_SERVICE:
				setManageNameIDService((EndpointType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__NAME_ID_FORMAT:
				setNameIDFormat((String)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__NAME_ID_MAPPING_SERVICE:
				setNameIDMappingService((EndpointType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ORGANIZATION:
				setOrganization((OrganizationType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ORGANIZATION_DISPLAY_NAME:
				setOrganizationDisplayName((LocalizedNameType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ORGANIZATION_NAME:
				setOrganizationName((LocalizedNameType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ORGANIZATION_URL:
				setOrganizationURL((LocalizedURIType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__PDP_DESCRIPTOR:
				setPDPDescriptor((PDPDescriptorType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__REQUESTED_ATTRIBUTE:
				setRequestedAttribute((RequestedAttributeType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ROLE_DESCRIPTOR:
				setRoleDescriptor((RoleDescriptorType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__SERVICE_DESCRIPTION:
				setServiceDescription((LocalizedNameType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__SERVICE_NAME:
				setServiceName((LocalizedNameType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__SINGLE_LOGOUT_SERVICE:
				setSingleLogoutService((EndpointType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__SINGLE_SIGN_ON_SERVICE:
				setSingleSignOnService((EndpointType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__SPSSO_DESCRIPTOR:
				setSPSSODescriptor((SPSSODescriptorType)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__SUR_NAME:
				setSurName((String)newValue);
				return;
			case MetadataPackage.DOCUMENT_ROOT__TELEPHONE_NUMBER:
				setTelephoneNumber((String)newValue);
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
			case MetadataPackage.DOCUMENT_ROOT__MIXED:
				getMixed().clear();
				return;
			case MetadataPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				getXMLNSPrefixMap().clear();
				return;
			case MetadataPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				getXSISchemaLocation().clear();
				return;
			case MetadataPackage.DOCUMENT_ROOT__ADDITIONAL_METADATA_LOCATION:
				setAdditionalMetadataLocation((AdditionalMetadataLocationType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__AFFILIATE_MEMBER:
				setAffiliateMember(AFFILIATE_MEMBER_EDEFAULT);
				return;
			case MetadataPackage.DOCUMENT_ROOT__AFFILIATION_DESCRIPTOR:
				setAffiliationDescriptor((AffiliationDescriptorType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ARTIFACT_RESOLUTION_SERVICE:
				setArtifactResolutionService((IndexedEndpointType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ASSERTION_CONSUMER_SERVICE:
				setAssertionConsumerService((IndexedEndpointType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ASSERTION_ID_REQUEST_SERVICE:
				setAssertionIDRequestService((EndpointType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ATTRIBUTE_AUTHORITY_DESCRIPTOR:
				setAttributeAuthorityDescriptor((AttributeAuthorityDescriptorType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ATTRIBUTE_CONSUMING_SERVICE:
				setAttributeConsumingService((AttributeConsumingServiceType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ATTRIBUTE_PROFILE:
				setAttributeProfile(ATTRIBUTE_PROFILE_EDEFAULT);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ATTRIBUTE_SERVICE:
				setAttributeService((EndpointType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__AUTHN_AUTHORITY_DESCRIPTOR:
				setAuthnAuthorityDescriptor((AuthnAuthorityDescriptorType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__AUTHN_QUERY_SERVICE:
				setAuthnQueryService((EndpointType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__AUTHZ_SERVICE:
				setAuthzService((EndpointType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__COMPANY:
				setCompany(COMPANY_EDEFAULT);
				return;
			case MetadataPackage.DOCUMENT_ROOT__CONTACT_PERSON:
				setContactPerson((ContactType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__EMAIL_ADDRESS:
				setEmailAddress(EMAIL_ADDRESS_EDEFAULT);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ENTITIES_DESCRIPTOR:
				setEntitiesDescriptor((EntitiesDescriptorType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ENTITY_DESCRIPTOR:
				setEntityDescriptor((EntityDescriptorType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__EXTENSIONS:
				setExtensions((ExtensionsType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__GIVEN_NAME:
				setGivenName(GIVEN_NAME_EDEFAULT);
				return;
			case MetadataPackage.DOCUMENT_ROOT__IDPSSO_DESCRIPTOR:
				setIDPSSODescriptor((IDPSSODescriptorType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__MANAGE_NAME_ID_SERVICE:
				setManageNameIDService((EndpointType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__NAME_ID_FORMAT:
				setNameIDFormat(NAME_ID_FORMAT_EDEFAULT);
				return;
			case MetadataPackage.DOCUMENT_ROOT__NAME_ID_MAPPING_SERVICE:
				setNameIDMappingService((EndpointType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ORGANIZATION:
				setOrganization((OrganizationType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ORGANIZATION_DISPLAY_NAME:
				setOrganizationDisplayName((LocalizedNameType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ORGANIZATION_NAME:
				setOrganizationName((LocalizedNameType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ORGANIZATION_URL:
				setOrganizationURL((LocalizedURIType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__PDP_DESCRIPTOR:
				setPDPDescriptor((PDPDescriptorType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__REQUESTED_ATTRIBUTE:
				setRequestedAttribute((RequestedAttributeType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__ROLE_DESCRIPTOR:
				setRoleDescriptor((RoleDescriptorType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__SERVICE_DESCRIPTION:
				setServiceDescription((LocalizedNameType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__SERVICE_NAME:
				setServiceName((LocalizedNameType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__SINGLE_LOGOUT_SERVICE:
				setSingleLogoutService((EndpointType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__SINGLE_SIGN_ON_SERVICE:
				setSingleSignOnService((EndpointType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__SPSSO_DESCRIPTOR:
				setSPSSODescriptor((SPSSODescriptorType)null);
				return;
			case MetadataPackage.DOCUMENT_ROOT__SUR_NAME:
				setSurName(SUR_NAME_EDEFAULT);
				return;
			case MetadataPackage.DOCUMENT_ROOT__TELEPHONE_NUMBER:
				setTelephoneNumber(TELEPHONE_NUMBER_EDEFAULT);
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
			case MetadataPackage.DOCUMENT_ROOT__MIXED:
				return mixed != null && !mixed.isEmpty();
			case MetadataPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
			case MetadataPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
			case MetadataPackage.DOCUMENT_ROOT__ADDITIONAL_METADATA_LOCATION:
				return getAdditionalMetadataLocation() != null;
			case MetadataPackage.DOCUMENT_ROOT__AFFILIATE_MEMBER:
				return AFFILIATE_MEMBER_EDEFAULT == null ? getAffiliateMember() != null : !AFFILIATE_MEMBER_EDEFAULT.equals(getAffiliateMember());
			case MetadataPackage.DOCUMENT_ROOT__AFFILIATION_DESCRIPTOR:
				return getAffiliationDescriptor() != null;
			case MetadataPackage.DOCUMENT_ROOT__ARTIFACT_RESOLUTION_SERVICE:
				return getArtifactResolutionService() != null;
			case MetadataPackage.DOCUMENT_ROOT__ASSERTION_CONSUMER_SERVICE:
				return getAssertionConsumerService() != null;
			case MetadataPackage.DOCUMENT_ROOT__ASSERTION_ID_REQUEST_SERVICE:
				return getAssertionIDRequestService() != null;
			case MetadataPackage.DOCUMENT_ROOT__ATTRIBUTE_AUTHORITY_DESCRIPTOR:
				return getAttributeAuthorityDescriptor() != null;
			case MetadataPackage.DOCUMENT_ROOT__ATTRIBUTE_CONSUMING_SERVICE:
				return getAttributeConsumingService() != null;
			case MetadataPackage.DOCUMENT_ROOT__ATTRIBUTE_PROFILE:
				return ATTRIBUTE_PROFILE_EDEFAULT == null ? getAttributeProfile() != null : !ATTRIBUTE_PROFILE_EDEFAULT.equals(getAttributeProfile());
			case MetadataPackage.DOCUMENT_ROOT__ATTRIBUTE_SERVICE:
				return getAttributeService() != null;
			case MetadataPackage.DOCUMENT_ROOT__AUTHN_AUTHORITY_DESCRIPTOR:
				return getAuthnAuthorityDescriptor() != null;
			case MetadataPackage.DOCUMENT_ROOT__AUTHN_QUERY_SERVICE:
				return getAuthnQueryService() != null;
			case MetadataPackage.DOCUMENT_ROOT__AUTHZ_SERVICE:
				return getAuthzService() != null;
			case MetadataPackage.DOCUMENT_ROOT__COMPANY:
				return COMPANY_EDEFAULT == null ? getCompany() != null : !COMPANY_EDEFAULT.equals(getCompany());
			case MetadataPackage.DOCUMENT_ROOT__CONTACT_PERSON:
				return getContactPerson() != null;
			case MetadataPackage.DOCUMENT_ROOT__EMAIL_ADDRESS:
				return EMAIL_ADDRESS_EDEFAULT == null ? getEmailAddress() != null : !EMAIL_ADDRESS_EDEFAULT.equals(getEmailAddress());
			case MetadataPackage.DOCUMENT_ROOT__ENTITIES_DESCRIPTOR:
				return getEntitiesDescriptor() != null;
			case MetadataPackage.DOCUMENT_ROOT__ENTITY_DESCRIPTOR:
				return getEntityDescriptor() != null;
			case MetadataPackage.DOCUMENT_ROOT__EXTENSIONS:
				return getExtensions() != null;
			case MetadataPackage.DOCUMENT_ROOT__GIVEN_NAME:
				return GIVEN_NAME_EDEFAULT == null ? getGivenName() != null : !GIVEN_NAME_EDEFAULT.equals(getGivenName());
			case MetadataPackage.DOCUMENT_ROOT__IDPSSO_DESCRIPTOR:
				return getIDPSSODescriptor() != null;
			case MetadataPackage.DOCUMENT_ROOT__MANAGE_NAME_ID_SERVICE:
				return getManageNameIDService() != null;
			case MetadataPackage.DOCUMENT_ROOT__NAME_ID_FORMAT:
				return NAME_ID_FORMAT_EDEFAULT == null ? getNameIDFormat() != null : !NAME_ID_FORMAT_EDEFAULT.equals(getNameIDFormat());
			case MetadataPackage.DOCUMENT_ROOT__NAME_ID_MAPPING_SERVICE:
				return getNameIDMappingService() != null;
			case MetadataPackage.DOCUMENT_ROOT__ORGANIZATION:
				return getOrganization() != null;
			case MetadataPackage.DOCUMENT_ROOT__ORGANIZATION_DISPLAY_NAME:
				return getOrganizationDisplayName() != null;
			case MetadataPackage.DOCUMENT_ROOT__ORGANIZATION_NAME:
				return getOrganizationName() != null;
			case MetadataPackage.DOCUMENT_ROOT__ORGANIZATION_URL:
				return getOrganizationURL() != null;
			case MetadataPackage.DOCUMENT_ROOT__PDP_DESCRIPTOR:
				return getPDPDescriptor() != null;
			case MetadataPackage.DOCUMENT_ROOT__REQUESTED_ATTRIBUTE:
				return getRequestedAttribute() != null;
			case MetadataPackage.DOCUMENT_ROOT__ROLE_DESCRIPTOR:
				return getRoleDescriptor() != null;
			case MetadataPackage.DOCUMENT_ROOT__SERVICE_DESCRIPTION:
				return getServiceDescription() != null;
			case MetadataPackage.DOCUMENT_ROOT__SERVICE_NAME:
				return getServiceName() != null;
			case MetadataPackage.DOCUMENT_ROOT__SINGLE_LOGOUT_SERVICE:
				return getSingleLogoutService() != null;
			case MetadataPackage.DOCUMENT_ROOT__SINGLE_SIGN_ON_SERVICE:
				return getSingleSignOnService() != null;
			case MetadataPackage.DOCUMENT_ROOT__SPSSO_DESCRIPTOR:
				return getSPSSODescriptor() != null;
			case MetadataPackage.DOCUMENT_ROOT__SUR_NAME:
				return SUR_NAME_EDEFAULT == null ? getSurName() != null : !SUR_NAME_EDEFAULT.equals(getSurName());
			case MetadataPackage.DOCUMENT_ROOT__TELEPHONE_NUMBER:
				return TELEPHONE_NUMBER_EDEFAULT == null ? getTelephoneNumber() != null : !TELEPHONE_NUMBER_EDEFAULT.equals(getTelephoneNumber());
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
		result.append(" (mixed: ");
		result.append(mixed);
		result.append(')');
		return result.toString();
	}

} //DocumentRootImpl
