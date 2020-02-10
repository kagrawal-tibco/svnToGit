/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.metadata;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAdditionalMetadataLocation <em>Additional Metadata Location</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAffiliateMember <em>Affiliate Member</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAffiliationDescriptor <em>Affiliation Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getArtifactResolutionService <em>Artifact Resolution Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAssertionConsumerService <em>Assertion Consumer Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAssertionIDRequestService <em>Assertion ID Request Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAttributeAuthorityDescriptor <em>Attribute Authority Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAttributeConsumingService <em>Attribute Consuming Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAttributeProfile <em>Attribute Profile</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAttributeService <em>Attribute Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAuthnAuthorityDescriptor <em>Authn Authority Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAuthnQueryService <em>Authn Query Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAuthzService <em>Authz Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getCompany <em>Company</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getContactPerson <em>Contact Person</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getEmailAddress <em>Email Address</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getEntitiesDescriptor <em>Entities Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getEntityDescriptor <em>Entity Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getGivenName <em>Given Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getIDPSSODescriptor <em>IDPSSO Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getManageNameIDService <em>Manage Name ID Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getNameIDFormat <em>Name ID Format</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getNameIDMappingService <em>Name ID Mapping Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getOrganization <em>Organization</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getOrganizationDisplayName <em>Organization Display Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getOrganizationName <em>Organization Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getOrganizationURL <em>Organization URL</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getPDPDescriptor <em>PDP Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getRequestedAttribute <em>Requested Attribute</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getRoleDescriptor <em>Role Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getServiceDescription <em>Service Description</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getServiceName <em>Service Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getSingleLogoutService <em>Single Logout Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getSingleSignOnService <em>Single Sign On Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getSPSSODescriptor <em>SPSSO Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getSurName <em>Sur Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getTelephoneNumber <em>Telephone Number</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface DocumentRoot extends EObject {
	/**
	 * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mixed</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mixed</em>' attribute list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_Mixed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' name=':mixed'"
	 * @generated
	 */
	FeatureMap getMixed();

	/**
	 * Returns the value of the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XMLNS Prefix Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XMLNS Prefix Map</em>' map.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_XMLNSPrefixMap()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
	 *        extendedMetaData="kind='attribute' name='xmlns:prefix'"
	 * @generated
	 */
	EMap<String, String> getXMLNSPrefixMap();

	/**
	 * Returns the value of the '<em><b>XSI Schema Location</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XSI Schema Location</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XSI Schema Location</em>' map.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_XSISchemaLocation()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
	 *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
	 * @generated
	 */
	EMap<String, String> getXSISchemaLocation();

	/**
	 * Returns the value of the '<em><b>Additional Metadata Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Additional Metadata Location</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Additional Metadata Location</em>' containment reference.
	 * @see #setAdditionalMetadataLocation(AdditionalMetadataLocationType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_AdditionalMetadataLocation()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AdditionalMetadataLocation' namespace='##targetNamespace'"
	 * @generated
	 */
	AdditionalMetadataLocationType getAdditionalMetadataLocation();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAdditionalMetadataLocation <em>Additional Metadata Location</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Additional Metadata Location</em>' containment reference.
	 * @see #getAdditionalMetadataLocation()
	 * @generated
	 */
	void setAdditionalMetadataLocation(AdditionalMetadataLocationType value);

	/**
	 * Returns the value of the '<em><b>Affiliate Member</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Affiliate Member</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Affiliate Member</em>' attribute.
	 * @see #setAffiliateMember(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_AffiliateMember()
	 * @model unique="false" dataType="com.tibco.be.baas.security.authn.saml.model.metadata.EntityIDType" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AffiliateMember' namespace='##targetNamespace'"
	 * @generated
	 */
	String getAffiliateMember();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAffiliateMember <em>Affiliate Member</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Affiliate Member</em>' attribute.
	 * @see #getAffiliateMember()
	 * @generated
	 */
	void setAffiliateMember(String value);

	/**
	 * Returns the value of the '<em><b>Affiliation Descriptor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Affiliation Descriptor</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Affiliation Descriptor</em>' containment reference.
	 * @see #setAffiliationDescriptor(AffiliationDescriptorType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_AffiliationDescriptor()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AffiliationDescriptor' namespace='##targetNamespace'"
	 * @generated
	 */
	AffiliationDescriptorType getAffiliationDescriptor();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAffiliationDescriptor <em>Affiliation Descriptor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Affiliation Descriptor</em>' containment reference.
	 * @see #getAffiliationDescriptor()
	 * @generated
	 */
	void setAffiliationDescriptor(AffiliationDescriptorType value);

	/**
	 * Returns the value of the '<em><b>Artifact Resolution Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Artifact Resolution Service</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Artifact Resolution Service</em>' containment reference.
	 * @see #setArtifactResolutionService(IndexedEndpointType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_ArtifactResolutionService()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ArtifactResolutionService' namespace='##targetNamespace'"
	 * @generated
	 */
	IndexedEndpointType getArtifactResolutionService();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getArtifactResolutionService <em>Artifact Resolution Service</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Artifact Resolution Service</em>' containment reference.
	 * @see #getArtifactResolutionService()
	 * @generated
	 */
	void setArtifactResolutionService(IndexedEndpointType value);

	/**
	 * Returns the value of the '<em><b>Assertion Consumer Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assertion Consumer Service</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assertion Consumer Service</em>' containment reference.
	 * @see #setAssertionConsumerService(IndexedEndpointType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_AssertionConsumerService()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AssertionConsumerService' namespace='##targetNamespace'"
	 * @generated
	 */
	IndexedEndpointType getAssertionConsumerService();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAssertionConsumerService <em>Assertion Consumer Service</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Assertion Consumer Service</em>' containment reference.
	 * @see #getAssertionConsumerService()
	 * @generated
	 */
	void setAssertionConsumerService(IndexedEndpointType value);

	/**
	 * Returns the value of the '<em><b>Assertion ID Request Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assertion ID Request Service</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assertion ID Request Service</em>' containment reference.
	 * @see #setAssertionIDRequestService(EndpointType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_AssertionIDRequestService()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AssertionIDRequestService' namespace='##targetNamespace'"
	 * @generated
	 */
	EndpointType getAssertionIDRequestService();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAssertionIDRequestService <em>Assertion ID Request Service</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Assertion ID Request Service</em>' containment reference.
	 * @see #getAssertionIDRequestService()
	 * @generated
	 */
	void setAssertionIDRequestService(EndpointType value);

	/**
	 * Returns the value of the '<em><b>Attribute Authority Descriptor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Authority Descriptor</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Authority Descriptor</em>' containment reference.
	 * @see #setAttributeAuthorityDescriptor(AttributeAuthorityDescriptorType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_AttributeAuthorityDescriptor()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AttributeAuthorityDescriptor' namespace='##targetNamespace'"
	 * @generated
	 */
	AttributeAuthorityDescriptorType getAttributeAuthorityDescriptor();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAttributeAuthorityDescriptor <em>Attribute Authority Descriptor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Authority Descriptor</em>' containment reference.
	 * @see #getAttributeAuthorityDescriptor()
	 * @generated
	 */
	void setAttributeAuthorityDescriptor(AttributeAuthorityDescriptorType value);

	/**
	 * Returns the value of the '<em><b>Attribute Consuming Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Consuming Service</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Consuming Service</em>' containment reference.
	 * @see #setAttributeConsumingService(AttributeConsumingServiceType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_AttributeConsumingService()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AttributeConsumingService' namespace='##targetNamespace'"
	 * @generated
	 */
	AttributeConsumingServiceType getAttributeConsumingService();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAttributeConsumingService <em>Attribute Consuming Service</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Consuming Service</em>' containment reference.
	 * @see #getAttributeConsumingService()
	 * @generated
	 */
	void setAttributeConsumingService(AttributeConsumingServiceType value);

	/**
	 * Returns the value of the '<em><b>Attribute Profile</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Profile</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Profile</em>' attribute.
	 * @see #setAttributeProfile(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_AttributeProfile()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AttributeProfile' namespace='##targetNamespace'"
	 * @generated
	 */
	String getAttributeProfile();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAttributeProfile <em>Attribute Profile</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Profile</em>' attribute.
	 * @see #getAttributeProfile()
	 * @generated
	 */
	void setAttributeProfile(String value);

	/**
	 * Returns the value of the '<em><b>Attribute Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Service</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Service</em>' containment reference.
	 * @see #setAttributeService(EndpointType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_AttributeService()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AttributeService' namespace='##targetNamespace'"
	 * @generated
	 */
	EndpointType getAttributeService();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAttributeService <em>Attribute Service</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Service</em>' containment reference.
	 * @see #getAttributeService()
	 * @generated
	 */
	void setAttributeService(EndpointType value);

	/**
	 * Returns the value of the '<em><b>Authn Authority Descriptor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authn Authority Descriptor</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authn Authority Descriptor</em>' containment reference.
	 * @see #setAuthnAuthorityDescriptor(AuthnAuthorityDescriptorType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_AuthnAuthorityDescriptor()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AuthnAuthorityDescriptor' namespace='##targetNamespace'"
	 * @generated
	 */
	AuthnAuthorityDescriptorType getAuthnAuthorityDescriptor();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAuthnAuthorityDescriptor <em>Authn Authority Descriptor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Authn Authority Descriptor</em>' containment reference.
	 * @see #getAuthnAuthorityDescriptor()
	 * @generated
	 */
	void setAuthnAuthorityDescriptor(AuthnAuthorityDescriptorType value);

	/**
	 * Returns the value of the '<em><b>Authn Query Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authn Query Service</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authn Query Service</em>' containment reference.
	 * @see #setAuthnQueryService(EndpointType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_AuthnQueryService()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AuthnQueryService' namespace='##targetNamespace'"
	 * @generated
	 */
	EndpointType getAuthnQueryService();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAuthnQueryService <em>Authn Query Service</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Authn Query Service</em>' containment reference.
	 * @see #getAuthnQueryService()
	 * @generated
	 */
	void setAuthnQueryService(EndpointType value);

	/**
	 * Returns the value of the '<em><b>Authz Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authz Service</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authz Service</em>' containment reference.
	 * @see #setAuthzService(EndpointType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_AuthzService()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AuthzService' namespace='##targetNamespace'"
	 * @generated
	 */
	EndpointType getAuthzService();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAuthzService <em>Authz Service</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Authz Service</em>' containment reference.
	 * @see #getAuthzService()
	 * @generated
	 */
	void setAuthzService(EndpointType value);

	/**
	 * Returns the value of the '<em><b>Company</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Company</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Company</em>' attribute.
	 * @see #setCompany(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_Company()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Company' namespace='##targetNamespace'"
	 * @generated
	 */
	String getCompany();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getCompany <em>Company</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Company</em>' attribute.
	 * @see #getCompany()
	 * @generated
	 */
	void setCompany(String value);

	/**
	 * Returns the value of the '<em><b>Contact Person</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contact Person</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contact Person</em>' containment reference.
	 * @see #setContactPerson(ContactType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_ContactPerson()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ContactPerson' namespace='##targetNamespace'"
	 * @generated
	 */
	ContactType getContactPerson();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getContactPerson <em>Contact Person</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contact Person</em>' containment reference.
	 * @see #getContactPerson()
	 * @generated
	 */
	void setContactPerson(ContactType value);

	/**
	 * Returns the value of the '<em><b>Email Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Email Address</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Email Address</em>' attribute.
	 * @see #setEmailAddress(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_EmailAddress()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='EmailAddress' namespace='##targetNamespace'"
	 * @generated
	 */
	String getEmailAddress();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getEmailAddress <em>Email Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Email Address</em>' attribute.
	 * @see #getEmailAddress()
	 * @generated
	 */
	void setEmailAddress(String value);

	/**
	 * Returns the value of the '<em><b>Entities Descriptor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entities Descriptor</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entities Descriptor</em>' containment reference.
	 * @see #setEntitiesDescriptor(EntitiesDescriptorType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_EntitiesDescriptor()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='EntitiesDescriptor' namespace='##targetNamespace'"
	 * @generated
	 */
	EntitiesDescriptorType getEntitiesDescriptor();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getEntitiesDescriptor <em>Entities Descriptor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entities Descriptor</em>' containment reference.
	 * @see #getEntitiesDescriptor()
	 * @generated
	 */
	void setEntitiesDescriptor(EntitiesDescriptorType value);

	/**
	 * Returns the value of the '<em><b>Entity Descriptor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity Descriptor</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity Descriptor</em>' containment reference.
	 * @see #setEntityDescriptor(EntityDescriptorType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_EntityDescriptor()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='EntityDescriptor' namespace='##targetNamespace'"
	 * @generated
	 */
	EntityDescriptorType getEntityDescriptor();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getEntityDescriptor <em>Entity Descriptor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entity Descriptor</em>' containment reference.
	 * @see #getEntityDescriptor()
	 * @generated
	 */
	void setEntityDescriptor(EntityDescriptorType value);

	/**
	 * Returns the value of the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extensions</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extensions</em>' containment reference.
	 * @see #setExtensions(ExtensionsType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_Extensions()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Extensions' namespace='##targetNamespace'"
	 * @generated
	 */
	ExtensionsType getExtensions();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getExtensions <em>Extensions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extensions</em>' containment reference.
	 * @see #getExtensions()
	 * @generated
	 */
	void setExtensions(ExtensionsType value);

	/**
	 * Returns the value of the '<em><b>Given Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Given Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Given Name</em>' attribute.
	 * @see #setGivenName(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_GivenName()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='GivenName' namespace='##targetNamespace'"
	 * @generated
	 */
	String getGivenName();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getGivenName <em>Given Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Given Name</em>' attribute.
	 * @see #getGivenName()
	 * @generated
	 */
	void setGivenName(String value);

	/**
	 * Returns the value of the '<em><b>IDPSSO Descriptor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>IDPSSO Descriptor</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>IDPSSO Descriptor</em>' containment reference.
	 * @see #setIDPSSODescriptor(IDPSSODescriptorType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_IDPSSODescriptor()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='IDPSSODescriptor' namespace='##targetNamespace'"
	 * @generated
	 */
	IDPSSODescriptorType getIDPSSODescriptor();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getIDPSSODescriptor <em>IDPSSO Descriptor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>IDPSSO Descriptor</em>' containment reference.
	 * @see #getIDPSSODescriptor()
	 * @generated
	 */
	void setIDPSSODescriptor(IDPSSODescriptorType value);

	/**
	 * Returns the value of the '<em><b>Manage Name ID Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Manage Name ID Service</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Manage Name ID Service</em>' containment reference.
	 * @see #setManageNameIDService(EndpointType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_ManageNameIDService()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ManageNameIDService' namespace='##targetNamespace'"
	 * @generated
	 */
	EndpointType getManageNameIDService();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getManageNameIDService <em>Manage Name ID Service</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Manage Name ID Service</em>' containment reference.
	 * @see #getManageNameIDService()
	 * @generated
	 */
	void setManageNameIDService(EndpointType value);

	/**
	 * Returns the value of the '<em><b>Name ID Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name ID Format</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name ID Format</em>' attribute.
	 * @see #setNameIDFormat(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_NameIDFormat()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='NameIDFormat' namespace='##targetNamespace'"
	 * @generated
	 */
	String getNameIDFormat();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getNameIDFormat <em>Name ID Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name ID Format</em>' attribute.
	 * @see #getNameIDFormat()
	 * @generated
	 */
	void setNameIDFormat(String value);

	/**
	 * Returns the value of the '<em><b>Name ID Mapping Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name ID Mapping Service</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name ID Mapping Service</em>' containment reference.
	 * @see #setNameIDMappingService(EndpointType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_NameIDMappingService()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='NameIDMappingService' namespace='##targetNamespace'"
	 * @generated
	 */
	EndpointType getNameIDMappingService();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getNameIDMappingService <em>Name ID Mapping Service</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name ID Mapping Service</em>' containment reference.
	 * @see #getNameIDMappingService()
	 * @generated
	 */
	void setNameIDMappingService(EndpointType value);

	/**
	 * Returns the value of the '<em><b>Organization</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Organization</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Organization</em>' containment reference.
	 * @see #setOrganization(OrganizationType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_Organization()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Organization' namespace='##targetNamespace'"
	 * @generated
	 */
	OrganizationType getOrganization();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getOrganization <em>Organization</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Organization</em>' containment reference.
	 * @see #getOrganization()
	 * @generated
	 */
	void setOrganization(OrganizationType value);

	/**
	 * Returns the value of the '<em><b>Organization Display Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Organization Display Name</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Organization Display Name</em>' containment reference.
	 * @see #setOrganizationDisplayName(LocalizedNameType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_OrganizationDisplayName()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='OrganizationDisplayName' namespace='##targetNamespace'"
	 * @generated
	 */
	LocalizedNameType getOrganizationDisplayName();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getOrganizationDisplayName <em>Organization Display Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Organization Display Name</em>' containment reference.
	 * @see #getOrganizationDisplayName()
	 * @generated
	 */
	void setOrganizationDisplayName(LocalizedNameType value);

	/**
	 * Returns the value of the '<em><b>Organization Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Organization Name</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Organization Name</em>' containment reference.
	 * @see #setOrganizationName(LocalizedNameType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_OrganizationName()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='OrganizationName' namespace='##targetNamespace'"
	 * @generated
	 */
	LocalizedNameType getOrganizationName();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getOrganizationName <em>Organization Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Organization Name</em>' containment reference.
	 * @see #getOrganizationName()
	 * @generated
	 */
	void setOrganizationName(LocalizedNameType value);

	/**
	 * Returns the value of the '<em><b>Organization URL</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Organization URL</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Organization URL</em>' containment reference.
	 * @see #setOrganizationURL(LocalizedURIType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_OrganizationURL()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='OrganizationURL' namespace='##targetNamespace'"
	 * @generated
	 */
	LocalizedURIType getOrganizationURL();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getOrganizationURL <em>Organization URL</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Organization URL</em>' containment reference.
	 * @see #getOrganizationURL()
	 * @generated
	 */
	void setOrganizationURL(LocalizedURIType value);

	/**
	 * Returns the value of the '<em><b>PDP Descriptor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>PDP Descriptor</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>PDP Descriptor</em>' containment reference.
	 * @see #setPDPDescriptor(PDPDescriptorType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_PDPDescriptor()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='PDPDescriptor' namespace='##targetNamespace'"
	 * @generated
	 */
	PDPDescriptorType getPDPDescriptor();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getPDPDescriptor <em>PDP Descriptor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>PDP Descriptor</em>' containment reference.
	 * @see #getPDPDescriptor()
	 * @generated
	 */
	void setPDPDescriptor(PDPDescriptorType value);

	/**
	 * Returns the value of the '<em><b>Requested Attribute</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Requested Attribute</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Requested Attribute</em>' containment reference.
	 * @see #setRequestedAttribute(RequestedAttributeType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_RequestedAttribute()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='RequestedAttribute' namespace='##targetNamespace'"
	 * @generated
	 */
	RequestedAttributeType getRequestedAttribute();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getRequestedAttribute <em>Requested Attribute</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Requested Attribute</em>' containment reference.
	 * @see #getRequestedAttribute()
	 * @generated
	 */
	void setRequestedAttribute(RequestedAttributeType value);

	/**
	 * Returns the value of the '<em><b>Role Descriptor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Role Descriptor</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Role Descriptor</em>' containment reference.
	 * @see #setRoleDescriptor(RoleDescriptorType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_RoleDescriptor()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='RoleDescriptor' namespace='##targetNamespace'"
	 * @generated
	 */
	RoleDescriptorType getRoleDescriptor();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getRoleDescriptor <em>Role Descriptor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Role Descriptor</em>' containment reference.
	 * @see #getRoleDescriptor()
	 * @generated
	 */
	void setRoleDescriptor(RoleDescriptorType value);

	/**
	 * Returns the value of the '<em><b>Service Description</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Service Description</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Service Description</em>' containment reference.
	 * @see #setServiceDescription(LocalizedNameType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_ServiceDescription()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ServiceDescription' namespace='##targetNamespace'"
	 * @generated
	 */
	LocalizedNameType getServiceDescription();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getServiceDescription <em>Service Description</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Service Description</em>' containment reference.
	 * @see #getServiceDescription()
	 * @generated
	 */
	void setServiceDescription(LocalizedNameType value);

	/**
	 * Returns the value of the '<em><b>Service Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Service Name</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Service Name</em>' containment reference.
	 * @see #setServiceName(LocalizedNameType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_ServiceName()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ServiceName' namespace='##targetNamespace'"
	 * @generated
	 */
	LocalizedNameType getServiceName();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getServiceName <em>Service Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Service Name</em>' containment reference.
	 * @see #getServiceName()
	 * @generated
	 */
	void setServiceName(LocalizedNameType value);

	/**
	 * Returns the value of the '<em><b>Single Logout Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Single Logout Service</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Single Logout Service</em>' containment reference.
	 * @see #setSingleLogoutService(EndpointType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_SingleLogoutService()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='SingleLogoutService' namespace='##targetNamespace'"
	 * @generated
	 */
	EndpointType getSingleLogoutService();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getSingleLogoutService <em>Single Logout Service</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Single Logout Service</em>' containment reference.
	 * @see #getSingleLogoutService()
	 * @generated
	 */
	void setSingleLogoutService(EndpointType value);

	/**
	 * Returns the value of the '<em><b>Single Sign On Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Single Sign On Service</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Single Sign On Service</em>' containment reference.
	 * @see #setSingleSignOnService(EndpointType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_SingleSignOnService()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='SingleSignOnService' namespace='##targetNamespace'"
	 * @generated
	 */
	EndpointType getSingleSignOnService();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getSingleSignOnService <em>Single Sign On Service</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Single Sign On Service</em>' containment reference.
	 * @see #getSingleSignOnService()
	 * @generated
	 */
	void setSingleSignOnService(EndpointType value);

	/**
	 * Returns the value of the '<em><b>SPSSO Descriptor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>SPSSO Descriptor</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>SPSSO Descriptor</em>' containment reference.
	 * @see #setSPSSODescriptor(SPSSODescriptorType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_SPSSODescriptor()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='SPSSODescriptor' namespace='##targetNamespace'"
	 * @generated
	 */
	SPSSODescriptorType getSPSSODescriptor();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getSPSSODescriptor <em>SPSSO Descriptor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>SPSSO Descriptor</em>' containment reference.
	 * @see #getSPSSODescriptor()
	 * @generated
	 */
	void setSPSSODescriptor(SPSSODescriptorType value);

	/**
	 * Returns the value of the '<em><b>Sur Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sur Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sur Name</em>' attribute.
	 * @see #setSurName(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_SurName()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='SurName' namespace='##targetNamespace'"
	 * @generated
	 */
	String getSurName();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getSurName <em>Sur Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sur Name</em>' attribute.
	 * @see #getSurName()
	 * @generated
	 */
	void setSurName(String value);

	/**
	 * Returns the value of the '<em><b>Telephone Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Telephone Number</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Telephone Number</em>' attribute.
	 * @see #setTelephoneNumber(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getDocumentRoot_TelephoneNumber()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='TelephoneNumber' namespace='##targetNamespace'"
	 * @generated
	 */
	String getTelephoneNumber();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getTelephoneNumber <em>Telephone Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Telephone Number</em>' attribute.
	 * @see #getTelephoneNumber()
	 * @generated
	 */
	void setTelephoneNumber(String value);

} // DocumentRoot
