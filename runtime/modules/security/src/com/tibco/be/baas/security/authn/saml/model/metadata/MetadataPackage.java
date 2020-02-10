/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.metadata;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * 
 * 			Document identifier: saml-schema-metadata-2.0 Location:
 * 			http://docs.oasis-open.org/security/saml/v2.0/ Revision history: V2.0
 * 			(March, 2005): Schema for SAML metadata, first published in SAML 2.0.
 * 		
 * 
 * 			Document identifier: saml-schema-assertion-2.0 Location:
 * 			http://docs.oasis-open.org/security/saml/v2.0/ Revision history: V1.0
 * 			(November, 2002): Initial Standard Schema. V1.1 (September, 2003):
 * 			Updates within the same V1.0 namespace. V2.0 (March, 2005): New
 * 			assertion schema for SAML V2.0 namespace.
 * 		
 * <!-- end-model-doc -->
 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataFactory
 * @model kind="package"
 *        annotation="http://java.sun.com/xml/ns/jaxb version='2.0'"
 * @generated
 */
public interface MetadataPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "metadata";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "urn:oasis:names:tc:SAML:2.0:metadata";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "metadata";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MetadataPackage eINSTANCE = com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AdditionalMetadataLocationTypeImpl <em>Additional Metadata Location Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.AdditionalMetadataLocationTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getAdditionalMetadataLocationType()
	 * @generated
	 */
	int ADDITIONAL_METADATA_LOCATION_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIONAL_METADATA_LOCATION_TYPE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIONAL_METADATA_LOCATION_TYPE__NAMESPACE = 1;

	/**
	 * The number of structural features of the '<em>Additional Metadata Location Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIONAL_METADATA_LOCATION_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AffiliationDescriptorTypeImpl <em>Affiliation Descriptor Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.AffiliationDescriptorTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getAffiliationDescriptorType()
	 * @generated
	 */
	int AFFILIATION_DESCRIPTOR_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AFFILIATION_DESCRIPTOR_TYPE__EXTENSIONS = 0;

	/**
	 * The feature id for the '<em><b>Affiliate Member</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AFFILIATION_DESCRIPTOR_TYPE__AFFILIATE_MEMBER = 1;

	/**
	 * The feature id for the '<em><b>Affiliation Owner ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AFFILIATION_DESCRIPTOR_TYPE__AFFILIATION_OWNER_ID = 2;

	/**
	 * The feature id for the '<em><b>Cache Duration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AFFILIATION_DESCRIPTOR_TYPE__CACHE_DURATION = 3;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AFFILIATION_DESCRIPTOR_TYPE__ID = 4;

	/**
	 * The feature id for the '<em><b>Valid Until</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AFFILIATION_DESCRIPTOR_TYPE__VALID_UNTIL = 5;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AFFILIATION_DESCRIPTOR_TYPE__ANY_ATTRIBUTE = 6;

	/**
	 * The number of structural features of the '<em>Affiliation Descriptor Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AFFILIATION_DESCRIPTOR_TYPE_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.RoleDescriptorTypeImpl <em>Role Descriptor Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.RoleDescriptorTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getRoleDescriptorType()
	 * @generated
	 */
	int ROLE_DESCRIPTOR_TYPE = 18;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_DESCRIPTOR_TYPE__EXTENSIONS = 0;

	/**
	 * The feature id for the '<em><b>Organization</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_DESCRIPTOR_TYPE__ORGANIZATION = 1;

	/**
	 * The feature id for the '<em><b>Contact Person</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_DESCRIPTOR_TYPE__CONTACT_PERSON = 2;

	/**
	 * The feature id for the '<em><b>Cache Duration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_DESCRIPTOR_TYPE__CACHE_DURATION = 3;

	/**
	 * The feature id for the '<em><b>Error URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_DESCRIPTOR_TYPE__ERROR_URL = 4;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_DESCRIPTOR_TYPE__ID = 5;

	/**
	 * The feature id for the '<em><b>Protocol Support Enumeration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_DESCRIPTOR_TYPE__PROTOCOL_SUPPORT_ENUMERATION = 6;

	/**
	 * The feature id for the '<em><b>Valid Until</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_DESCRIPTOR_TYPE__VALID_UNTIL = 7;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_DESCRIPTOR_TYPE__ANY_ATTRIBUTE = 8;

	/**
	 * The number of structural features of the '<em>Role Descriptor Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_DESCRIPTOR_TYPE_FEATURE_COUNT = 9;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AttributeAuthorityDescriptorTypeImpl <em>Attribute Authority Descriptor Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.AttributeAuthorityDescriptorTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getAttributeAuthorityDescriptorType()
	 * @generated
	 */
	int ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__EXTENSIONS = ROLE_DESCRIPTOR_TYPE__EXTENSIONS;

	/**
	 * The feature id for the '<em><b>Organization</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ORGANIZATION = ROLE_DESCRIPTOR_TYPE__ORGANIZATION;

	/**
	 * The feature id for the '<em><b>Contact Person</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__CONTACT_PERSON = ROLE_DESCRIPTOR_TYPE__CONTACT_PERSON;

	/**
	 * The feature id for the '<em><b>Cache Duration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__CACHE_DURATION = ROLE_DESCRIPTOR_TYPE__CACHE_DURATION;

	/**
	 * The feature id for the '<em><b>Error URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ERROR_URL = ROLE_DESCRIPTOR_TYPE__ERROR_URL;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ID = ROLE_DESCRIPTOR_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Protocol Support Enumeration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__PROTOCOL_SUPPORT_ENUMERATION = ROLE_DESCRIPTOR_TYPE__PROTOCOL_SUPPORT_ENUMERATION;

	/**
	 * The feature id for the '<em><b>Valid Until</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__VALID_UNTIL = ROLE_DESCRIPTOR_TYPE__VALID_UNTIL;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ANY_ATTRIBUTE = ROLE_DESCRIPTOR_TYPE__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Attribute Service</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ATTRIBUTE_SERVICE = ROLE_DESCRIPTOR_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Assertion ID Request Service</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE = ROLE_DESCRIPTOR_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name ID Format</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__NAME_ID_FORMAT = ROLE_DESCRIPTOR_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Attribute Profile</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ATTRIBUTE_PROFILE = ROLE_DESCRIPTOR_TYPE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ATTRIBUTE = ROLE_DESCRIPTOR_TYPE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Attribute Authority Descriptor Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE_FEATURE_COUNT = ROLE_DESCRIPTOR_TYPE_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AttributeConsumingServiceTypeImpl <em>Attribute Consuming Service Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.AttributeConsumingServiceTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getAttributeConsumingServiceType()
	 * @generated
	 */
	int ATTRIBUTE_CONSUMING_SERVICE_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Service Name</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSUMING_SERVICE_TYPE__SERVICE_NAME = 0;

	/**
	 * The feature id for the '<em><b>Service Description</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSUMING_SERVICE_TYPE__SERVICE_DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>Requested Attribute</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSUMING_SERVICE_TYPE__REQUESTED_ATTRIBUTE = 2;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSUMING_SERVICE_TYPE__INDEX = 3;

	/**
	 * The feature id for the '<em><b>Is Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSUMING_SERVICE_TYPE__IS_DEFAULT = 4;

	/**
	 * The number of structural features of the '<em>Attribute Consuming Service Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSUMING_SERVICE_TYPE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AuthnAuthorityDescriptorTypeImpl <em>Authn Authority Descriptor Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.AuthnAuthorityDescriptorTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getAuthnAuthorityDescriptorType()
	 * @generated
	 */
	int AUTHN_AUTHORITY_DESCRIPTOR_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_AUTHORITY_DESCRIPTOR_TYPE__EXTENSIONS = ROLE_DESCRIPTOR_TYPE__EXTENSIONS;

	/**
	 * The feature id for the '<em><b>Organization</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_AUTHORITY_DESCRIPTOR_TYPE__ORGANIZATION = ROLE_DESCRIPTOR_TYPE__ORGANIZATION;

	/**
	 * The feature id for the '<em><b>Contact Person</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_AUTHORITY_DESCRIPTOR_TYPE__CONTACT_PERSON = ROLE_DESCRIPTOR_TYPE__CONTACT_PERSON;

	/**
	 * The feature id for the '<em><b>Cache Duration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_AUTHORITY_DESCRIPTOR_TYPE__CACHE_DURATION = ROLE_DESCRIPTOR_TYPE__CACHE_DURATION;

	/**
	 * The feature id for the '<em><b>Error URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_AUTHORITY_DESCRIPTOR_TYPE__ERROR_URL = ROLE_DESCRIPTOR_TYPE__ERROR_URL;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_AUTHORITY_DESCRIPTOR_TYPE__ID = ROLE_DESCRIPTOR_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Protocol Support Enumeration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_AUTHORITY_DESCRIPTOR_TYPE__PROTOCOL_SUPPORT_ENUMERATION = ROLE_DESCRIPTOR_TYPE__PROTOCOL_SUPPORT_ENUMERATION;

	/**
	 * The feature id for the '<em><b>Valid Until</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_AUTHORITY_DESCRIPTOR_TYPE__VALID_UNTIL = ROLE_DESCRIPTOR_TYPE__VALID_UNTIL;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_AUTHORITY_DESCRIPTOR_TYPE__ANY_ATTRIBUTE = ROLE_DESCRIPTOR_TYPE__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Authn Query Service</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_AUTHORITY_DESCRIPTOR_TYPE__AUTHN_QUERY_SERVICE = ROLE_DESCRIPTOR_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Assertion ID Request Service</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_AUTHORITY_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE = ROLE_DESCRIPTOR_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name ID Format</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_AUTHORITY_DESCRIPTOR_TYPE__NAME_ID_FORMAT = ROLE_DESCRIPTOR_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Authn Authority Descriptor Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_AUTHORITY_DESCRIPTOR_TYPE_FEATURE_COUNT = ROLE_DESCRIPTOR_TYPE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.ContactTypeImpl <em>Contact Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.ContactTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getContactType()
	 * @generated
	 */
	int CONTACT_TYPE = 5;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_TYPE__EXTENSIONS = 0;

	/**
	 * The feature id for the '<em><b>Company</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_TYPE__COMPANY = 1;

	/**
	 * The feature id for the '<em><b>Given Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_TYPE__GIVEN_NAME = 2;

	/**
	 * The feature id for the '<em><b>Sur Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_TYPE__SUR_NAME = 3;

	/**
	 * The feature id for the '<em><b>Email Address</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_TYPE__EMAIL_ADDRESS = 4;

	/**
	 * The feature id for the '<em><b>Telephone Number</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_TYPE__TELEPHONE_NUMBER = 5;

	/**
	 * The feature id for the '<em><b>Contact Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_TYPE__CONTACT_TYPE = 6;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_TYPE__ANY_ATTRIBUTE = 7;

	/**
	 * The number of structural features of the '<em>Contact Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_TYPE_FEATURE_COUNT = 8;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 6;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MIXED = 0;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Additional Metadata Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ADDITIONAL_METADATA_LOCATION = 3;

	/**
	 * The feature id for the '<em><b>Affiliate Member</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__AFFILIATE_MEMBER = 4;

	/**
	 * The feature id for the '<em><b>Affiliation Descriptor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__AFFILIATION_DESCRIPTOR = 5;

	/**
	 * The feature id for the '<em><b>Artifact Resolution Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ARTIFACT_RESOLUTION_SERVICE = 6;

	/**
	 * The feature id for the '<em><b>Assertion Consumer Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ASSERTION_CONSUMER_SERVICE = 7;

	/**
	 * The feature id for the '<em><b>Assertion ID Request Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ASSERTION_ID_REQUEST_SERVICE = 8;

	/**
	 * The feature id for the '<em><b>Attribute Authority Descriptor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ATTRIBUTE_AUTHORITY_DESCRIPTOR = 9;

	/**
	 * The feature id for the '<em><b>Attribute Consuming Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ATTRIBUTE_CONSUMING_SERVICE = 10;

	/**
	 * The feature id for the '<em><b>Attribute Profile</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ATTRIBUTE_PROFILE = 11;

	/**
	 * The feature id for the '<em><b>Attribute Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ATTRIBUTE_SERVICE = 12;

	/**
	 * The feature id for the '<em><b>Authn Authority Descriptor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__AUTHN_AUTHORITY_DESCRIPTOR = 13;

	/**
	 * The feature id for the '<em><b>Authn Query Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__AUTHN_QUERY_SERVICE = 14;

	/**
	 * The feature id for the '<em><b>Authz Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__AUTHZ_SERVICE = 15;

	/**
	 * The feature id for the '<em><b>Company</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__COMPANY = 16;

	/**
	 * The feature id for the '<em><b>Contact Person</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CONTACT_PERSON = 17;

	/**
	 * The feature id for the '<em><b>Email Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EMAIL_ADDRESS = 18;

	/**
	 * The feature id for the '<em><b>Entities Descriptor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ENTITIES_DESCRIPTOR = 19;

	/**
	 * The feature id for the '<em><b>Entity Descriptor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ENTITY_DESCRIPTOR = 20;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EXTENSIONS = 21;

	/**
	 * The feature id for the '<em><b>Given Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__GIVEN_NAME = 22;

	/**
	 * The feature id for the '<em><b>IDPSSO Descriptor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__IDPSSO_DESCRIPTOR = 23;

	/**
	 * The feature id for the '<em><b>Manage Name ID Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MANAGE_NAME_ID_SERVICE = 24;

	/**
	 * The feature id for the '<em><b>Name ID Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__NAME_ID_FORMAT = 25;

	/**
	 * The feature id for the '<em><b>Name ID Mapping Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__NAME_ID_MAPPING_SERVICE = 26;

	/**
	 * The feature id for the '<em><b>Organization</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ORGANIZATION = 27;

	/**
	 * The feature id for the '<em><b>Organization Display Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ORGANIZATION_DISPLAY_NAME = 28;

	/**
	 * The feature id for the '<em><b>Organization Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ORGANIZATION_NAME = 29;

	/**
	 * The feature id for the '<em><b>Organization URL</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ORGANIZATION_URL = 30;

	/**
	 * The feature id for the '<em><b>PDP Descriptor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PDP_DESCRIPTOR = 31;

	/**
	 * The feature id for the '<em><b>Requested Attribute</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__REQUESTED_ATTRIBUTE = 32;

	/**
	 * The feature id for the '<em><b>Role Descriptor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ROLE_DESCRIPTOR = 33;

	/**
	 * The feature id for the '<em><b>Service Description</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SERVICE_DESCRIPTION = 34;

	/**
	 * The feature id for the '<em><b>Service Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SERVICE_NAME = 35;

	/**
	 * The feature id for the '<em><b>Single Logout Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SINGLE_LOGOUT_SERVICE = 36;

	/**
	 * The feature id for the '<em><b>Single Sign On Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SINGLE_SIGN_ON_SERVICE = 37;

	/**
	 * The feature id for the '<em><b>SPSSO Descriptor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SPSSO_DESCRIPTOR = 38;

	/**
	 * The feature id for the '<em><b>Sur Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SUR_NAME = 39;

	/**
	 * The feature id for the '<em><b>Telephone Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TELEPHONE_NUMBER = 40;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 41;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EndpointTypeImpl <em>Endpoint Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.EndpointTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getEndpointType()
	 * @generated
	 */
	int ENDPOINT_TYPE = 7;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENDPOINT_TYPE__ANY = 0;

	/**
	 * The feature id for the '<em><b>Binding</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENDPOINT_TYPE__BINDING = 1;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENDPOINT_TYPE__LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Response Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENDPOINT_TYPE__RESPONSE_LOCATION = 3;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENDPOINT_TYPE__ANY_ATTRIBUTE = 4;

	/**
	 * The number of structural features of the '<em>Endpoint Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENDPOINT_TYPE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntitiesDescriptorTypeImpl <em>Entities Descriptor Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntitiesDescriptorTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getEntitiesDescriptorType()
	 * @generated
	 */
	int ENTITIES_DESCRIPTOR_TYPE = 8;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITIES_DESCRIPTOR_TYPE__EXTENSIONS = 0;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITIES_DESCRIPTOR_TYPE__GROUP = 1;

	/**
	 * The feature id for the '<em><b>Entity Descriptor</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITIES_DESCRIPTOR_TYPE__ENTITY_DESCRIPTOR = 2;

	/**
	 * The feature id for the '<em><b>Entities Descriptor</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITIES_DESCRIPTOR_TYPE__ENTITIES_DESCRIPTOR = 3;

	/**
	 * The feature id for the '<em><b>Cache Duration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITIES_DESCRIPTOR_TYPE__CACHE_DURATION = 4;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITIES_DESCRIPTOR_TYPE__ID = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITIES_DESCRIPTOR_TYPE__NAME = 6;

	/**
	 * The feature id for the '<em><b>Valid Until</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITIES_DESCRIPTOR_TYPE__VALID_UNTIL = 7;

	/**
	 * The number of structural features of the '<em>Entities Descriptor Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITIES_DESCRIPTOR_TYPE_FEATURE_COUNT = 8;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntityDescriptorTypeImpl <em>Entity Descriptor Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntityDescriptorTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getEntityDescriptorType()
	 * @generated
	 */
	int ENTITY_DESCRIPTOR_TYPE = 9;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_DESCRIPTOR_TYPE__EXTENSIONS = 0;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_DESCRIPTOR_TYPE__GROUP = 1;

	/**
	 * The feature id for the '<em><b>Role Descriptor</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_DESCRIPTOR_TYPE__ROLE_DESCRIPTOR = 2;

	/**
	 * The feature id for the '<em><b>IDPSSO Descriptor</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_DESCRIPTOR_TYPE__IDPSSO_DESCRIPTOR = 3;

	/**
	 * The feature id for the '<em><b>SPSSO Descriptor</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_DESCRIPTOR_TYPE__SPSSO_DESCRIPTOR = 4;

	/**
	 * The feature id for the '<em><b>Authn Authority Descriptor</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_DESCRIPTOR_TYPE__AUTHN_AUTHORITY_DESCRIPTOR = 5;

	/**
	 * The feature id for the '<em><b>Attribute Authority Descriptor</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_DESCRIPTOR_TYPE__ATTRIBUTE_AUTHORITY_DESCRIPTOR = 6;

	/**
	 * The feature id for the '<em><b>PDP Descriptor</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_DESCRIPTOR_TYPE__PDP_DESCRIPTOR = 7;

	/**
	 * The feature id for the '<em><b>Affiliation Descriptor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_DESCRIPTOR_TYPE__AFFILIATION_DESCRIPTOR = 8;

	/**
	 * The feature id for the '<em><b>Organization</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_DESCRIPTOR_TYPE__ORGANIZATION = 9;

	/**
	 * The feature id for the '<em><b>Contact Person</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_DESCRIPTOR_TYPE__CONTACT_PERSON = 10;

	/**
	 * The feature id for the '<em><b>Additional Metadata Location</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_DESCRIPTOR_TYPE__ADDITIONAL_METADATA_LOCATION = 11;

	/**
	 * The feature id for the '<em><b>Cache Duration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_DESCRIPTOR_TYPE__CACHE_DURATION = 12;

	/**
	 * The feature id for the '<em><b>Entity ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_DESCRIPTOR_TYPE__ENTITY_ID = 13;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_DESCRIPTOR_TYPE__ID = 14;

	/**
	 * The feature id for the '<em><b>Valid Until</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_DESCRIPTOR_TYPE__VALID_UNTIL = 15;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_DESCRIPTOR_TYPE__ANY_ATTRIBUTE = 16;

	/**
	 * The number of structural features of the '<em>Entity Descriptor Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_DESCRIPTOR_TYPE_FEATURE_COUNT = 17;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.ExtensionsTypeImpl <em>Extensions Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.ExtensionsTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getExtensionsType()
	 * @generated
	 */
	int EXTENSIONS_TYPE = 10;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSIONS_TYPE__ANY = 0;

	/**
	 * The number of structural features of the '<em>Extensions Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSIONS_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.SSODescriptorTypeImpl <em>SSO Descriptor Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.SSODescriptorTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getSSODescriptorType()
	 * @generated
	 */
	int SSO_DESCRIPTOR_TYPE = 20;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SSO_DESCRIPTOR_TYPE__EXTENSIONS = ROLE_DESCRIPTOR_TYPE__EXTENSIONS;

	/**
	 * The feature id for the '<em><b>Organization</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SSO_DESCRIPTOR_TYPE__ORGANIZATION = ROLE_DESCRIPTOR_TYPE__ORGANIZATION;

	/**
	 * The feature id for the '<em><b>Contact Person</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SSO_DESCRIPTOR_TYPE__CONTACT_PERSON = ROLE_DESCRIPTOR_TYPE__CONTACT_PERSON;

	/**
	 * The feature id for the '<em><b>Cache Duration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SSO_DESCRIPTOR_TYPE__CACHE_DURATION = ROLE_DESCRIPTOR_TYPE__CACHE_DURATION;

	/**
	 * The feature id for the '<em><b>Error URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SSO_DESCRIPTOR_TYPE__ERROR_URL = ROLE_DESCRIPTOR_TYPE__ERROR_URL;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SSO_DESCRIPTOR_TYPE__ID = ROLE_DESCRIPTOR_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Protocol Support Enumeration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SSO_DESCRIPTOR_TYPE__PROTOCOL_SUPPORT_ENUMERATION = ROLE_DESCRIPTOR_TYPE__PROTOCOL_SUPPORT_ENUMERATION;

	/**
	 * The feature id for the '<em><b>Valid Until</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SSO_DESCRIPTOR_TYPE__VALID_UNTIL = ROLE_DESCRIPTOR_TYPE__VALID_UNTIL;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SSO_DESCRIPTOR_TYPE__ANY_ATTRIBUTE = ROLE_DESCRIPTOR_TYPE__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Artifact Resolution Service</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SSO_DESCRIPTOR_TYPE__ARTIFACT_RESOLUTION_SERVICE = ROLE_DESCRIPTOR_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Single Logout Service</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SSO_DESCRIPTOR_TYPE__SINGLE_LOGOUT_SERVICE = ROLE_DESCRIPTOR_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Manage Name ID Service</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SSO_DESCRIPTOR_TYPE__MANAGE_NAME_ID_SERVICE = ROLE_DESCRIPTOR_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Name ID Format</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SSO_DESCRIPTOR_TYPE__NAME_ID_FORMAT = ROLE_DESCRIPTOR_TYPE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>SSO Descriptor Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SSO_DESCRIPTOR_TYPE_FEATURE_COUNT = ROLE_DESCRIPTOR_TYPE_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.IDPSSODescriptorTypeImpl <em>IDPSSO Descriptor Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.IDPSSODescriptorTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getIDPSSODescriptorType()
	 * @generated
	 */
	int IDPSSO_DESCRIPTOR_TYPE = 11;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDPSSO_DESCRIPTOR_TYPE__EXTENSIONS = SSO_DESCRIPTOR_TYPE__EXTENSIONS;

	/**
	 * The feature id for the '<em><b>Organization</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDPSSO_DESCRIPTOR_TYPE__ORGANIZATION = SSO_DESCRIPTOR_TYPE__ORGANIZATION;

	/**
	 * The feature id for the '<em><b>Contact Person</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDPSSO_DESCRIPTOR_TYPE__CONTACT_PERSON = SSO_DESCRIPTOR_TYPE__CONTACT_PERSON;

	/**
	 * The feature id for the '<em><b>Cache Duration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDPSSO_DESCRIPTOR_TYPE__CACHE_DURATION = SSO_DESCRIPTOR_TYPE__CACHE_DURATION;

	/**
	 * The feature id for the '<em><b>Error URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDPSSO_DESCRIPTOR_TYPE__ERROR_URL = SSO_DESCRIPTOR_TYPE__ERROR_URL;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDPSSO_DESCRIPTOR_TYPE__ID = SSO_DESCRIPTOR_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Protocol Support Enumeration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDPSSO_DESCRIPTOR_TYPE__PROTOCOL_SUPPORT_ENUMERATION = SSO_DESCRIPTOR_TYPE__PROTOCOL_SUPPORT_ENUMERATION;

	/**
	 * The feature id for the '<em><b>Valid Until</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDPSSO_DESCRIPTOR_TYPE__VALID_UNTIL = SSO_DESCRIPTOR_TYPE__VALID_UNTIL;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDPSSO_DESCRIPTOR_TYPE__ANY_ATTRIBUTE = SSO_DESCRIPTOR_TYPE__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Artifact Resolution Service</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDPSSO_DESCRIPTOR_TYPE__ARTIFACT_RESOLUTION_SERVICE = SSO_DESCRIPTOR_TYPE__ARTIFACT_RESOLUTION_SERVICE;

	/**
	 * The feature id for the '<em><b>Single Logout Service</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDPSSO_DESCRIPTOR_TYPE__SINGLE_LOGOUT_SERVICE = SSO_DESCRIPTOR_TYPE__SINGLE_LOGOUT_SERVICE;

	/**
	 * The feature id for the '<em><b>Manage Name ID Service</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDPSSO_DESCRIPTOR_TYPE__MANAGE_NAME_ID_SERVICE = SSO_DESCRIPTOR_TYPE__MANAGE_NAME_ID_SERVICE;

	/**
	 * The feature id for the '<em><b>Name ID Format</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDPSSO_DESCRIPTOR_TYPE__NAME_ID_FORMAT = SSO_DESCRIPTOR_TYPE__NAME_ID_FORMAT;

	/**
	 * The feature id for the '<em><b>Single Sign On Service</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDPSSO_DESCRIPTOR_TYPE__SINGLE_SIGN_ON_SERVICE = SSO_DESCRIPTOR_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name ID Mapping Service</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDPSSO_DESCRIPTOR_TYPE__NAME_ID_MAPPING_SERVICE = SSO_DESCRIPTOR_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Assertion ID Request Service</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDPSSO_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE = SSO_DESCRIPTOR_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Attribute Profile</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDPSSO_DESCRIPTOR_TYPE__ATTRIBUTE_PROFILE = SSO_DESCRIPTOR_TYPE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDPSSO_DESCRIPTOR_TYPE__ATTRIBUTE = SSO_DESCRIPTOR_TYPE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Want Authn Requests Signed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDPSSO_DESCRIPTOR_TYPE__WANT_AUTHN_REQUESTS_SIGNED = SSO_DESCRIPTOR_TYPE_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>IDPSSO Descriptor Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDPSSO_DESCRIPTOR_TYPE_FEATURE_COUNT = SSO_DESCRIPTOR_TYPE_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.IndexedEndpointTypeImpl <em>Indexed Endpoint Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.IndexedEndpointTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getIndexedEndpointType()
	 * @generated
	 */
	int INDEXED_ENDPOINT_TYPE = 12;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEXED_ENDPOINT_TYPE__ANY = ENDPOINT_TYPE__ANY;

	/**
	 * The feature id for the '<em><b>Binding</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEXED_ENDPOINT_TYPE__BINDING = ENDPOINT_TYPE__BINDING;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEXED_ENDPOINT_TYPE__LOCATION = ENDPOINT_TYPE__LOCATION;

	/**
	 * The feature id for the '<em><b>Response Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEXED_ENDPOINT_TYPE__RESPONSE_LOCATION = ENDPOINT_TYPE__RESPONSE_LOCATION;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEXED_ENDPOINT_TYPE__ANY_ATTRIBUTE = ENDPOINT_TYPE__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEXED_ENDPOINT_TYPE__INDEX = ENDPOINT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Is Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEXED_ENDPOINT_TYPE__IS_DEFAULT = ENDPOINT_TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Indexed Endpoint Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEXED_ENDPOINT_TYPE_FEATURE_COUNT = ENDPOINT_TYPE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.LocalizedNameTypeImpl <em>Localized Name Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.LocalizedNameTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getLocalizedNameType()
	 * @generated
	 */
	int LOCALIZED_NAME_TYPE = 13;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCALIZED_NAME_TYPE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Lang</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCALIZED_NAME_TYPE__LANG = 1;

	/**
	 * The number of structural features of the '<em>Localized Name Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCALIZED_NAME_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.LocalizedURITypeImpl <em>Localized URI Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.LocalizedURITypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getLocalizedURIType()
	 * @generated
	 */
	int LOCALIZED_URI_TYPE = 14;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCALIZED_URI_TYPE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Lang</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCALIZED_URI_TYPE__LANG = 1;

	/**
	 * The number of structural features of the '<em>Localized URI Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCALIZED_URI_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.OrganizationTypeImpl <em>Organization Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.OrganizationTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getOrganizationType()
	 * @generated
	 */
	int ORGANIZATION_TYPE = 15;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORGANIZATION_TYPE__EXTENSIONS = 0;

	/**
	 * The feature id for the '<em><b>Organization Name</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORGANIZATION_TYPE__ORGANIZATION_NAME = 1;

	/**
	 * The feature id for the '<em><b>Organization Display Name</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORGANIZATION_TYPE__ORGANIZATION_DISPLAY_NAME = 2;

	/**
	 * The feature id for the '<em><b>Organization URL</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORGANIZATION_TYPE__ORGANIZATION_URL = 3;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORGANIZATION_TYPE__ANY_ATTRIBUTE = 4;

	/**
	 * The number of structural features of the '<em>Organization Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORGANIZATION_TYPE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.PDPDescriptorTypeImpl <em>PDP Descriptor Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.PDPDescriptorTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getPDPDescriptorType()
	 * @generated
	 */
	int PDP_DESCRIPTOR_TYPE = 16;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PDP_DESCRIPTOR_TYPE__EXTENSIONS = ROLE_DESCRIPTOR_TYPE__EXTENSIONS;

	/**
	 * The feature id for the '<em><b>Organization</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PDP_DESCRIPTOR_TYPE__ORGANIZATION = ROLE_DESCRIPTOR_TYPE__ORGANIZATION;

	/**
	 * The feature id for the '<em><b>Contact Person</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PDP_DESCRIPTOR_TYPE__CONTACT_PERSON = ROLE_DESCRIPTOR_TYPE__CONTACT_PERSON;

	/**
	 * The feature id for the '<em><b>Cache Duration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PDP_DESCRIPTOR_TYPE__CACHE_DURATION = ROLE_DESCRIPTOR_TYPE__CACHE_DURATION;

	/**
	 * The feature id for the '<em><b>Error URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PDP_DESCRIPTOR_TYPE__ERROR_URL = ROLE_DESCRIPTOR_TYPE__ERROR_URL;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PDP_DESCRIPTOR_TYPE__ID = ROLE_DESCRIPTOR_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Protocol Support Enumeration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PDP_DESCRIPTOR_TYPE__PROTOCOL_SUPPORT_ENUMERATION = ROLE_DESCRIPTOR_TYPE__PROTOCOL_SUPPORT_ENUMERATION;

	/**
	 * The feature id for the '<em><b>Valid Until</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PDP_DESCRIPTOR_TYPE__VALID_UNTIL = ROLE_DESCRIPTOR_TYPE__VALID_UNTIL;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PDP_DESCRIPTOR_TYPE__ANY_ATTRIBUTE = ROLE_DESCRIPTOR_TYPE__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Authz Service</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PDP_DESCRIPTOR_TYPE__AUTHZ_SERVICE = ROLE_DESCRIPTOR_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Assertion ID Request Service</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PDP_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE = ROLE_DESCRIPTOR_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name ID Format</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PDP_DESCRIPTOR_TYPE__NAME_ID_FORMAT = ROLE_DESCRIPTOR_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>PDP Descriptor Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PDP_DESCRIPTOR_TYPE_FEATURE_COUNT = ROLE_DESCRIPTOR_TYPE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.RequestedAttributeTypeImpl <em>Requested Attribute Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.RequestedAttributeTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getRequestedAttributeType()
	 * @generated
	 */
	int REQUESTED_ATTRIBUTE_TYPE = 17;

	/**
	 * The feature id for the '<em><b>Attribute Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUESTED_ATTRIBUTE_TYPE__ATTRIBUTE_VALUE = AssertionPackage.ATTRIBUTE_TYPE__ATTRIBUTE_VALUE;

	/**
	 * The feature id for the '<em><b>Friendly Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUESTED_ATTRIBUTE_TYPE__FRIENDLY_NAME = AssertionPackage.ATTRIBUTE_TYPE__FRIENDLY_NAME;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUESTED_ATTRIBUTE_TYPE__NAME = AssertionPackage.ATTRIBUTE_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Name Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUESTED_ATTRIBUTE_TYPE__NAME_FORMAT = AssertionPackage.ATTRIBUTE_TYPE__NAME_FORMAT;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUESTED_ATTRIBUTE_TYPE__ANY_ATTRIBUTE = AssertionPackage.ATTRIBUTE_TYPE__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Is Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUESTED_ATTRIBUTE_TYPE__IS_REQUIRED = AssertionPackage.ATTRIBUTE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Requested Attribute Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUESTED_ATTRIBUTE_TYPE_FEATURE_COUNT = AssertionPackage.ATTRIBUTE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.SPSSODescriptorTypeImpl <em>SPSSO Descriptor Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.SPSSODescriptorTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getSPSSODescriptorType()
	 * @generated
	 */
	int SPSSO_DESCRIPTOR_TYPE = 19;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPSSO_DESCRIPTOR_TYPE__EXTENSIONS = SSO_DESCRIPTOR_TYPE__EXTENSIONS;

	/**
	 * The feature id for the '<em><b>Organization</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPSSO_DESCRIPTOR_TYPE__ORGANIZATION = SSO_DESCRIPTOR_TYPE__ORGANIZATION;

	/**
	 * The feature id for the '<em><b>Contact Person</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPSSO_DESCRIPTOR_TYPE__CONTACT_PERSON = SSO_DESCRIPTOR_TYPE__CONTACT_PERSON;

	/**
	 * The feature id for the '<em><b>Cache Duration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPSSO_DESCRIPTOR_TYPE__CACHE_DURATION = SSO_DESCRIPTOR_TYPE__CACHE_DURATION;

	/**
	 * The feature id for the '<em><b>Error URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPSSO_DESCRIPTOR_TYPE__ERROR_URL = SSO_DESCRIPTOR_TYPE__ERROR_URL;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPSSO_DESCRIPTOR_TYPE__ID = SSO_DESCRIPTOR_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Protocol Support Enumeration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPSSO_DESCRIPTOR_TYPE__PROTOCOL_SUPPORT_ENUMERATION = SSO_DESCRIPTOR_TYPE__PROTOCOL_SUPPORT_ENUMERATION;

	/**
	 * The feature id for the '<em><b>Valid Until</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPSSO_DESCRIPTOR_TYPE__VALID_UNTIL = SSO_DESCRIPTOR_TYPE__VALID_UNTIL;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPSSO_DESCRIPTOR_TYPE__ANY_ATTRIBUTE = SSO_DESCRIPTOR_TYPE__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Artifact Resolution Service</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPSSO_DESCRIPTOR_TYPE__ARTIFACT_RESOLUTION_SERVICE = SSO_DESCRIPTOR_TYPE__ARTIFACT_RESOLUTION_SERVICE;

	/**
	 * The feature id for the '<em><b>Single Logout Service</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPSSO_DESCRIPTOR_TYPE__SINGLE_LOGOUT_SERVICE = SSO_DESCRIPTOR_TYPE__SINGLE_LOGOUT_SERVICE;

	/**
	 * The feature id for the '<em><b>Manage Name ID Service</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPSSO_DESCRIPTOR_TYPE__MANAGE_NAME_ID_SERVICE = SSO_DESCRIPTOR_TYPE__MANAGE_NAME_ID_SERVICE;

	/**
	 * The feature id for the '<em><b>Name ID Format</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPSSO_DESCRIPTOR_TYPE__NAME_ID_FORMAT = SSO_DESCRIPTOR_TYPE__NAME_ID_FORMAT;

	/**
	 * The feature id for the '<em><b>Assertion Consumer Service</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPSSO_DESCRIPTOR_TYPE__ASSERTION_CONSUMER_SERVICE = SSO_DESCRIPTOR_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Attribute Consuming Service</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPSSO_DESCRIPTOR_TYPE__ATTRIBUTE_CONSUMING_SERVICE = SSO_DESCRIPTOR_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Authn Policy</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPSSO_DESCRIPTOR_TYPE__AUTHN_POLICY = SSO_DESCRIPTOR_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Authn Requests Signed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPSSO_DESCRIPTOR_TYPE__AUTHN_REQUESTS_SIGNED = SSO_DESCRIPTOR_TYPE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Want Assertions Signed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPSSO_DESCRIPTOR_TYPE__WANT_ASSERTIONS_SIGNED = SSO_DESCRIPTOR_TYPE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>SPSSO Descriptor Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPSSO_DESCRIPTOR_TYPE_FEATURE_COUNT = SSO_DESCRIPTOR_TYPE_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactTypeType <em>Contact Type Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.ContactTypeType
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getContactTypeType()
	 * @generated
	 */
	int CONTACT_TYPE_TYPE = 21;

	/**
	 * The meta object id for the '<em>Any URI List Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.util.List
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getAnyURIListType()
	 * @generated
	 */
	int ANY_URI_LIST_TYPE = 22;

	/**
	 * The meta object id for the '<em>Contact Type Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.ContactTypeType
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getContactTypeTypeObject()
	 * @generated
	 */
	int CONTACT_TYPE_TYPE_OBJECT = 23;

	/**
	 * The meta object id for the '<em>Entity ID Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getEntityIDType()
	 * @generated
	 */
	int ENTITY_ID_TYPE = 24;


	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AdditionalMetadataLocationType <em>Additional Metadata Location Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Additional Metadata Location Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AdditionalMetadataLocationType
	 * @generated
	 */
	EClass getAdditionalMetadataLocationType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AdditionalMetadataLocationType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AdditionalMetadataLocationType#getValue()
	 * @see #getAdditionalMetadataLocationType()
	 * @generated
	 */
	EAttribute getAdditionalMetadataLocationType_Value();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AdditionalMetadataLocationType#getNamespace <em>Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Namespace</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AdditionalMetadataLocationType#getNamespace()
	 * @see #getAdditionalMetadataLocationType()
	 * @generated
	 */
	EAttribute getAdditionalMetadataLocationType_Namespace();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType <em>Affiliation Descriptor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Affiliation Descriptor Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType
	 * @generated
	 */
	EClass getAffiliationDescriptorType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType#getExtensions <em>Extensions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Extensions</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType#getExtensions()
	 * @see #getAffiliationDescriptorType()
	 * @generated
	 */
	EReference getAffiliationDescriptorType_Extensions();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType#getAffiliateMember <em>Affiliate Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Affiliate Member</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType#getAffiliateMember()
	 * @see #getAffiliationDescriptorType()
	 * @generated
	 */
	EAttribute getAffiliationDescriptorType_AffiliateMember();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType#getAffiliationOwnerID <em>Affiliation Owner ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Affiliation Owner ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType#getAffiliationOwnerID()
	 * @see #getAffiliationDescriptorType()
	 * @generated
	 */
	EAttribute getAffiliationDescriptorType_AffiliationOwnerID();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType#getCacheDuration <em>Cache Duration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cache Duration</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType#getCacheDuration()
	 * @see #getAffiliationDescriptorType()
	 * @generated
	 */
	EAttribute getAffiliationDescriptorType_CacheDuration();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType#getID <em>ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType#getID()
	 * @see #getAffiliationDescriptorType()
	 * @generated
	 */
	EAttribute getAffiliationDescriptorType_ID();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType#getValidUntil <em>Valid Until</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Valid Until</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType#getValidUntil()
	 * @see #getAffiliationDescriptorType()
	 * @generated
	 */
	EAttribute getAffiliationDescriptorType_ValidUntil();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType#getAnyAttribute()
	 * @see #getAffiliationDescriptorType()
	 * @generated
	 */
	EAttribute getAffiliationDescriptorType_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeAuthorityDescriptorType <em>Attribute Authority Descriptor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Authority Descriptor Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AttributeAuthorityDescriptorType
	 * @generated
	 */
	EClass getAttributeAuthorityDescriptorType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeAuthorityDescriptorType#getAttributeService <em>Attribute Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute Service</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AttributeAuthorityDescriptorType#getAttributeService()
	 * @see #getAttributeAuthorityDescriptorType()
	 * @generated
	 */
	EReference getAttributeAuthorityDescriptorType_AttributeService();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeAuthorityDescriptorType#getAssertionIDRequestService <em>Assertion ID Request Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Assertion ID Request Service</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AttributeAuthorityDescriptorType#getAssertionIDRequestService()
	 * @see #getAttributeAuthorityDescriptorType()
	 * @generated
	 */
	EReference getAttributeAuthorityDescriptorType_AssertionIDRequestService();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeAuthorityDescriptorType#getNameIDFormat <em>Name ID Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Name ID Format</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AttributeAuthorityDescriptorType#getNameIDFormat()
	 * @see #getAttributeAuthorityDescriptorType()
	 * @generated
	 */
	EAttribute getAttributeAuthorityDescriptorType_NameIDFormat();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeAuthorityDescriptorType#getAttributeProfile <em>Attribute Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Attribute Profile</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AttributeAuthorityDescriptorType#getAttributeProfile()
	 * @see #getAttributeAuthorityDescriptorType()
	 * @generated
	 */
	EAttribute getAttributeAuthorityDescriptorType_AttributeProfile();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeAuthorityDescriptorType#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AttributeAuthorityDescriptorType#getAttribute()
	 * @see #getAttributeAuthorityDescriptorType()
	 * @generated
	 */
	EReference getAttributeAuthorityDescriptorType_Attribute();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType <em>Attribute Consuming Service Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Consuming Service Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType
	 * @generated
	 */
	EClass getAttributeConsumingServiceType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType#getServiceName <em>Service Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Service Name</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType#getServiceName()
	 * @see #getAttributeConsumingServiceType()
	 * @generated
	 */
	EReference getAttributeConsumingServiceType_ServiceName();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType#getServiceDescription <em>Service Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Service Description</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType#getServiceDescription()
	 * @see #getAttributeConsumingServiceType()
	 * @generated
	 */
	EReference getAttributeConsumingServiceType_ServiceDescription();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType#getRequestedAttribute <em>Requested Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Requested Attribute</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType#getRequestedAttribute()
	 * @see #getAttributeConsumingServiceType()
	 * @generated
	 */
	EReference getAttributeConsumingServiceType_RequestedAttribute();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Index</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType#getIndex()
	 * @see #getAttributeConsumingServiceType()
	 * @generated
	 */
	EAttribute getAttributeConsumingServiceType_Index();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType#isIsDefault <em>Is Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Default</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType#isIsDefault()
	 * @see #getAttributeConsumingServiceType()
	 * @generated
	 */
	EAttribute getAttributeConsumingServiceType_IsDefault();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AuthnAuthorityDescriptorType <em>Authn Authority Descriptor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Authn Authority Descriptor Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AuthnAuthorityDescriptorType
	 * @generated
	 */
	EClass getAuthnAuthorityDescriptorType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AuthnAuthorityDescriptorType#getAuthnQueryService <em>Authn Query Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Authn Query Service</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AuthnAuthorityDescriptorType#getAuthnQueryService()
	 * @see #getAuthnAuthorityDescriptorType()
	 * @generated
	 */
	EReference getAuthnAuthorityDescriptorType_AuthnQueryService();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AuthnAuthorityDescriptorType#getAssertionIDRequestService <em>Assertion ID Request Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Assertion ID Request Service</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AuthnAuthorityDescriptorType#getAssertionIDRequestService()
	 * @see #getAuthnAuthorityDescriptorType()
	 * @generated
	 */
	EReference getAuthnAuthorityDescriptorType_AssertionIDRequestService();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AuthnAuthorityDescriptorType#getNameIDFormat <em>Name ID Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Name ID Format</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AuthnAuthorityDescriptorType#getNameIDFormat()
	 * @see #getAuthnAuthorityDescriptorType()
	 * @generated
	 */
	EAttribute getAuthnAuthorityDescriptorType_NameIDFormat();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactType <em>Contact Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Contact Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.ContactType
	 * @generated
	 */
	EClass getContactType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getExtensions <em>Extensions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Extensions</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getExtensions()
	 * @see #getContactType()
	 * @generated
	 */
	EReference getContactType_Extensions();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getCompany <em>Company</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Company</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getCompany()
	 * @see #getContactType()
	 * @generated
	 */
	EAttribute getContactType_Company();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getGivenName <em>Given Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Given Name</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getGivenName()
	 * @see #getContactType()
	 * @generated
	 */
	EAttribute getContactType_GivenName();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getSurName <em>Sur Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sur Name</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getSurName()
	 * @see #getContactType()
	 * @generated
	 */
	EAttribute getContactType_SurName();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getEmailAddress <em>Email Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Email Address</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getEmailAddress()
	 * @see #getContactType()
	 * @generated
	 */
	EAttribute getContactType_EmailAddress();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getTelephoneNumber <em>Telephone Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Telephone Number</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getTelephoneNumber()
	 * @see #getContactType()
	 * @generated
	 */
	EAttribute getContactType_TelephoneNumber();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getContactType <em>Contact Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Contact Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getContactType()
	 * @see #getContactType()
	 * @generated
	 */
	EAttribute getContactType_ContactType();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getAnyAttribute()
	 * @see #getContactType()
	 * @generated
	 */
	EAttribute getContactType_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAdditionalMetadataLocation <em>Additional Metadata Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Additional Metadata Location</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAdditionalMetadataLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_AdditionalMetadataLocation();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAffiliateMember <em>Affiliate Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Affiliate Member</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAffiliateMember()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_AffiliateMember();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAffiliationDescriptor <em>Affiliation Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Affiliation Descriptor</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAffiliationDescriptor()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_AffiliationDescriptor();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getArtifactResolutionService <em>Artifact Resolution Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Artifact Resolution Service</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getArtifactResolutionService()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ArtifactResolutionService();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAssertionConsumerService <em>Assertion Consumer Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Assertion Consumer Service</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAssertionConsumerService()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_AssertionConsumerService();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAssertionIDRequestService <em>Assertion ID Request Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Assertion ID Request Service</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAssertionIDRequestService()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_AssertionIDRequestService();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAttributeAuthorityDescriptor <em>Attribute Authority Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Attribute Authority Descriptor</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAttributeAuthorityDescriptor()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_AttributeAuthorityDescriptor();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAttributeConsumingService <em>Attribute Consuming Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Attribute Consuming Service</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAttributeConsumingService()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_AttributeConsumingService();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAttributeProfile <em>Attribute Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attribute Profile</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAttributeProfile()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_AttributeProfile();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAttributeService <em>Attribute Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Attribute Service</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAttributeService()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_AttributeService();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAuthnAuthorityDescriptor <em>Authn Authority Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Authn Authority Descriptor</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAuthnAuthorityDescriptor()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_AuthnAuthorityDescriptor();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAuthnQueryService <em>Authn Query Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Authn Query Service</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAuthnQueryService()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_AuthnQueryService();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAuthzService <em>Authz Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Authz Service</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getAuthzService()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_AuthzService();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getCompany <em>Company</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Company</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getCompany()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Company();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getContactPerson <em>Contact Person</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Contact Person</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getContactPerson()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ContactPerson();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getEmailAddress <em>Email Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Email Address</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getEmailAddress()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_EmailAddress();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getEntitiesDescriptor <em>Entities Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Entities Descriptor</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getEntitiesDescriptor()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_EntitiesDescriptor();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getEntityDescriptor <em>Entity Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Entity Descriptor</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getEntityDescriptor()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_EntityDescriptor();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getExtensions <em>Extensions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Extensions</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getExtensions()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Extensions();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getGivenName <em>Given Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Given Name</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getGivenName()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_GivenName();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getIDPSSODescriptor <em>IDPSSO Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>IDPSSO Descriptor</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getIDPSSODescriptor()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_IDPSSODescriptor();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getManageNameIDService <em>Manage Name ID Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Manage Name ID Service</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getManageNameIDService()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ManageNameIDService();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getNameIDFormat <em>Name ID Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name ID Format</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getNameIDFormat()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_NameIDFormat();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getNameIDMappingService <em>Name ID Mapping Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Name ID Mapping Service</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getNameIDMappingService()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_NameIDMappingService();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getOrganization <em>Organization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Organization</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getOrganization()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Organization();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getOrganizationDisplayName <em>Organization Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Organization Display Name</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getOrganizationDisplayName()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_OrganizationDisplayName();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getOrganizationName <em>Organization Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Organization Name</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getOrganizationName()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_OrganizationName();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getOrganizationURL <em>Organization URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Organization URL</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getOrganizationURL()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_OrganizationURL();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getPDPDescriptor <em>PDP Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>PDP Descriptor</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getPDPDescriptor()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_PDPDescriptor();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getRequestedAttribute <em>Requested Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Requested Attribute</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getRequestedAttribute()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_RequestedAttribute();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getRoleDescriptor <em>Role Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Role Descriptor</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getRoleDescriptor()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_RoleDescriptor();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getServiceDescription <em>Service Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Service Description</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getServiceDescription()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ServiceDescription();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getServiceName <em>Service Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Service Name</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getServiceName()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ServiceName();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getSingleLogoutService <em>Single Logout Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Single Logout Service</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getSingleLogoutService()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_SingleLogoutService();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getSingleSignOnService <em>Single Sign On Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Single Sign On Service</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getSingleSignOnService()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_SingleSignOnService();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getSPSSODescriptor <em>SPSSO Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>SPSSO Descriptor</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getSPSSODescriptor()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_SPSSODescriptor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getSurName <em>Sur Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sur Name</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getSurName()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_SurName();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getTelephoneNumber <em>Telephone Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Telephone Number</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot#getTelephoneNumber()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_TelephoneNumber();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType <em>Endpoint Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Endpoint Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType
	 * @generated
	 */
	EClass getEndpointType();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType#getAny()
	 * @see #getEndpointType()
	 * @generated
	 */
	EAttribute getEndpointType_Any();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType#getBinding <em>Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Binding</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType#getBinding()
	 * @see #getEndpointType()
	 * @generated
	 */
	EAttribute getEndpointType_Binding();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Location</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType#getLocation()
	 * @see #getEndpointType()
	 * @generated
	 */
	EAttribute getEndpointType_Location();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType#getResponseLocation <em>Response Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Response Location</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType#getResponseLocation()
	 * @see #getEndpointType()
	 * @generated
	 */
	EAttribute getEndpointType_ResponseLocation();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType#getAnyAttribute()
	 * @see #getEndpointType()
	 * @generated
	 */
	EAttribute getEndpointType_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType <em>Entities Descriptor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entities Descriptor Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType
	 * @generated
	 */
	EClass getEntitiesDescriptorType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getExtensions <em>Extensions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Extensions</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getExtensions()
	 * @see #getEntitiesDescriptorType()
	 * @generated
	 */
	EReference getEntitiesDescriptorType_Extensions();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getGroup()
	 * @see #getEntitiesDescriptorType()
	 * @generated
	 */
	EAttribute getEntitiesDescriptorType_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getEntityDescriptor <em>Entity Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entity Descriptor</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getEntityDescriptor()
	 * @see #getEntitiesDescriptorType()
	 * @generated
	 */
	EReference getEntitiesDescriptorType_EntityDescriptor();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getEntitiesDescriptor <em>Entities Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entities Descriptor</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getEntitiesDescriptor()
	 * @see #getEntitiesDescriptorType()
	 * @generated
	 */
	EReference getEntitiesDescriptorType_EntitiesDescriptor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getCacheDuration <em>Cache Duration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cache Duration</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getCacheDuration()
	 * @see #getEntitiesDescriptorType()
	 * @generated
	 */
	EAttribute getEntitiesDescriptorType_CacheDuration();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getID <em>ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getID()
	 * @see #getEntitiesDescriptorType()
	 * @generated
	 */
	EAttribute getEntitiesDescriptorType_ID();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getName()
	 * @see #getEntitiesDescriptorType()
	 * @generated
	 */
	EAttribute getEntitiesDescriptorType_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getValidUntil <em>Valid Until</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Valid Until</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getValidUntil()
	 * @see #getEntitiesDescriptorType()
	 * @generated
	 */
	EAttribute getEntitiesDescriptorType_ValidUntil();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType <em>Entity Descriptor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity Descriptor Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType
	 * @generated
	 */
	EClass getEntityDescriptorType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getExtensions <em>Extensions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Extensions</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getExtensions()
	 * @see #getEntityDescriptorType()
	 * @generated
	 */
	EReference getEntityDescriptorType_Extensions();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getGroup()
	 * @see #getEntityDescriptorType()
	 * @generated
	 */
	EAttribute getEntityDescriptorType_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getRoleDescriptor <em>Role Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Role Descriptor</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getRoleDescriptor()
	 * @see #getEntityDescriptorType()
	 * @generated
	 */
	EReference getEntityDescriptorType_RoleDescriptor();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getIDPSSODescriptor <em>IDPSSO Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>IDPSSO Descriptor</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getIDPSSODescriptor()
	 * @see #getEntityDescriptorType()
	 * @generated
	 */
	EReference getEntityDescriptorType_IDPSSODescriptor();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getSPSSODescriptor <em>SPSSO Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>SPSSO Descriptor</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getSPSSODescriptor()
	 * @see #getEntityDescriptorType()
	 * @generated
	 */
	EReference getEntityDescriptorType_SPSSODescriptor();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getAuthnAuthorityDescriptor <em>Authn Authority Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Authn Authority Descriptor</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getAuthnAuthorityDescriptor()
	 * @see #getEntityDescriptorType()
	 * @generated
	 */
	EReference getEntityDescriptorType_AuthnAuthorityDescriptor();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getAttributeAuthorityDescriptor <em>Attribute Authority Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute Authority Descriptor</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getAttributeAuthorityDescriptor()
	 * @see #getEntityDescriptorType()
	 * @generated
	 */
	EReference getEntityDescriptorType_AttributeAuthorityDescriptor();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getPDPDescriptor <em>PDP Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>PDP Descriptor</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getPDPDescriptor()
	 * @see #getEntityDescriptorType()
	 * @generated
	 */
	EReference getEntityDescriptorType_PDPDescriptor();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getAffiliationDescriptor <em>Affiliation Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Affiliation Descriptor</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getAffiliationDescriptor()
	 * @see #getEntityDescriptorType()
	 * @generated
	 */
	EReference getEntityDescriptorType_AffiliationDescriptor();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getOrganization <em>Organization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Organization</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getOrganization()
	 * @see #getEntityDescriptorType()
	 * @generated
	 */
	EReference getEntityDescriptorType_Organization();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getContactPerson <em>Contact Person</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contact Person</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getContactPerson()
	 * @see #getEntityDescriptorType()
	 * @generated
	 */
	EReference getEntityDescriptorType_ContactPerson();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getAdditionalMetadataLocation <em>Additional Metadata Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Additional Metadata Location</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getAdditionalMetadataLocation()
	 * @see #getEntityDescriptorType()
	 * @generated
	 */
	EReference getEntityDescriptorType_AdditionalMetadataLocation();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getCacheDuration <em>Cache Duration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cache Duration</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getCacheDuration()
	 * @see #getEntityDescriptorType()
	 * @generated
	 */
	EAttribute getEntityDescriptorType_CacheDuration();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getEntityID <em>Entity ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Entity ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getEntityID()
	 * @see #getEntityDescriptorType()
	 * @generated
	 */
	EAttribute getEntityDescriptorType_EntityID();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getID <em>ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getID()
	 * @see #getEntityDescriptorType()
	 * @generated
	 */
	EAttribute getEntityDescriptorType_ID();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getValidUntil <em>Valid Until</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Valid Until</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getValidUntil()
	 * @see #getEntityDescriptorType()
	 * @generated
	 */
	EAttribute getEntityDescriptorType_ValidUntil();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getAnyAttribute()
	 * @see #getEntityDescriptorType()
	 * @generated
	 */
	EAttribute getEntityDescriptorType_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.ExtensionsType <em>Extensions Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Extensions Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.ExtensionsType
	 * @generated
	 */
	EClass getExtensionsType();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.ExtensionsType#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.ExtensionsType#getAny()
	 * @see #getExtensionsType()
	 * @generated
	 */
	EAttribute getExtensionsType_Any();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType <em>IDPSSO Descriptor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IDPSSO Descriptor Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType
	 * @generated
	 */
	EClass getIDPSSODescriptorType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType#getSingleSignOnService <em>Single Sign On Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Single Sign On Service</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType#getSingleSignOnService()
	 * @see #getIDPSSODescriptorType()
	 * @generated
	 */
	EReference getIDPSSODescriptorType_SingleSignOnService();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType#getNameIDMappingService <em>Name ID Mapping Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Name ID Mapping Service</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType#getNameIDMappingService()
	 * @see #getIDPSSODescriptorType()
	 * @generated
	 */
	EReference getIDPSSODescriptorType_NameIDMappingService();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType#getAssertionIDRequestService <em>Assertion ID Request Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Assertion ID Request Service</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType#getAssertionIDRequestService()
	 * @see #getIDPSSODescriptorType()
	 * @generated
	 */
	EReference getIDPSSODescriptorType_AssertionIDRequestService();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType#getAttributeProfile <em>Attribute Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Attribute Profile</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType#getAttributeProfile()
	 * @see #getIDPSSODescriptorType()
	 * @generated
	 */
	EAttribute getIDPSSODescriptorType_AttributeProfile();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType#getAttribute()
	 * @see #getIDPSSODescriptorType()
	 * @generated
	 */
	EReference getIDPSSODescriptorType_Attribute();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType#isWantAuthnRequestsSigned <em>Want Authn Requests Signed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Want Authn Requests Signed</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType#isWantAuthnRequestsSigned()
	 * @see #getIDPSSODescriptorType()
	 * @generated
	 */
	EAttribute getIDPSSODescriptorType_WantAuthnRequestsSigned();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.IndexedEndpointType <em>Indexed Endpoint Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Indexed Endpoint Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.IndexedEndpointType
	 * @generated
	 */
	EClass getIndexedEndpointType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.IndexedEndpointType#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Index</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.IndexedEndpointType#getIndex()
	 * @see #getIndexedEndpointType()
	 * @generated
	 */
	EAttribute getIndexedEndpointType_Index();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.IndexedEndpointType#isIsDefault <em>Is Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Default</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.IndexedEndpointType#isIsDefault()
	 * @see #getIndexedEndpointType()
	 * @generated
	 */
	EAttribute getIndexedEndpointType_IsDefault();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedNameType <em>Localized Name Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Localized Name Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedNameType
	 * @generated
	 */
	EClass getLocalizedNameType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedNameType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedNameType#getValue()
	 * @see #getLocalizedNameType()
	 * @generated
	 */
	EAttribute getLocalizedNameType_Value();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedNameType#getLang <em>Lang</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lang</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedNameType#getLang()
	 * @see #getLocalizedNameType()
	 * @generated
	 */
	EAttribute getLocalizedNameType_Lang();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedURIType <em>Localized URI Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Localized URI Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedURIType
	 * @generated
	 */
	EClass getLocalizedURIType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedURIType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedURIType#getValue()
	 * @see #getLocalizedURIType()
	 * @generated
	 */
	EAttribute getLocalizedURIType_Value();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedURIType#getLang <em>Lang</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lang</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedURIType#getLang()
	 * @see #getLocalizedURIType()
	 * @generated
	 */
	EAttribute getLocalizedURIType_Lang();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.OrganizationType <em>Organization Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Organization Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.OrganizationType
	 * @generated
	 */
	EClass getOrganizationType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.OrganizationType#getExtensions <em>Extensions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Extensions</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.OrganizationType#getExtensions()
	 * @see #getOrganizationType()
	 * @generated
	 */
	EReference getOrganizationType_Extensions();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.OrganizationType#getOrganizationName <em>Organization Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Organization Name</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.OrganizationType#getOrganizationName()
	 * @see #getOrganizationType()
	 * @generated
	 */
	EReference getOrganizationType_OrganizationName();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.OrganizationType#getOrganizationDisplayName <em>Organization Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Organization Display Name</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.OrganizationType#getOrganizationDisplayName()
	 * @see #getOrganizationType()
	 * @generated
	 */
	EReference getOrganizationType_OrganizationDisplayName();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.OrganizationType#getOrganizationURL <em>Organization URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Organization URL</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.OrganizationType#getOrganizationURL()
	 * @see #getOrganizationType()
	 * @generated
	 */
	EReference getOrganizationType_OrganizationURL();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.OrganizationType#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.OrganizationType#getAnyAttribute()
	 * @see #getOrganizationType()
	 * @generated
	 */
	EAttribute getOrganizationType_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.PDPDescriptorType <em>PDP Descriptor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>PDP Descriptor Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.PDPDescriptorType
	 * @generated
	 */
	EClass getPDPDescriptorType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.PDPDescriptorType#getAuthzService <em>Authz Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Authz Service</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.PDPDescriptorType#getAuthzService()
	 * @see #getPDPDescriptorType()
	 * @generated
	 */
	EReference getPDPDescriptorType_AuthzService();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.PDPDescriptorType#getAssertionIDRequestService <em>Assertion ID Request Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Assertion ID Request Service</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.PDPDescriptorType#getAssertionIDRequestService()
	 * @see #getPDPDescriptorType()
	 * @generated
	 */
	EReference getPDPDescriptorType_AssertionIDRequestService();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.PDPDescriptorType#getNameIDFormat <em>Name ID Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Name ID Format</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.PDPDescriptorType#getNameIDFormat()
	 * @see #getPDPDescriptorType()
	 * @generated
	 */
	EAttribute getPDPDescriptorType_NameIDFormat();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.RequestedAttributeType <em>Requested Attribute Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Requested Attribute Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.RequestedAttributeType
	 * @generated
	 */
	EClass getRequestedAttributeType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.RequestedAttributeType#isIsRequired <em>Is Required</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Required</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.RequestedAttributeType#isIsRequired()
	 * @see #getRequestedAttributeType()
	 * @generated
	 */
	EAttribute getRequestedAttributeType_IsRequired();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType <em>Role Descriptor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Role Descriptor Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType
	 * @generated
	 */
	EClass getRoleDescriptorType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getExtensions <em>Extensions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Extensions</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getExtensions()
	 * @see #getRoleDescriptorType()
	 * @generated
	 */
	EReference getRoleDescriptorType_Extensions();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getOrganization <em>Organization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Organization</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getOrganization()
	 * @see #getRoleDescriptorType()
	 * @generated
	 */
	EReference getRoleDescriptorType_Organization();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getContactPerson <em>Contact Person</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contact Person</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getContactPerson()
	 * @see #getRoleDescriptorType()
	 * @generated
	 */
	EReference getRoleDescriptorType_ContactPerson();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getCacheDuration <em>Cache Duration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cache Duration</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getCacheDuration()
	 * @see #getRoleDescriptorType()
	 * @generated
	 */
	EAttribute getRoleDescriptorType_CacheDuration();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getErrorURL <em>Error URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Error URL</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getErrorURL()
	 * @see #getRoleDescriptorType()
	 * @generated
	 */
	EAttribute getRoleDescriptorType_ErrorURL();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getID <em>ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getID()
	 * @see #getRoleDescriptorType()
	 * @generated
	 */
	EAttribute getRoleDescriptorType_ID();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getProtocolSupportEnumeration <em>Protocol Support Enumeration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Protocol Support Enumeration</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getProtocolSupportEnumeration()
	 * @see #getRoleDescriptorType()
	 * @generated
	 */
	EAttribute getRoleDescriptorType_ProtocolSupportEnumeration();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getValidUntil <em>Valid Until</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Valid Until</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getValidUntil()
	 * @see #getRoleDescriptorType()
	 * @generated
	 */
	EAttribute getRoleDescriptorType_ValidUntil();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getAnyAttribute()
	 * @see #getRoleDescriptorType()
	 * @generated
	 */
	EAttribute getRoleDescriptorType_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType <em>SPSSO Descriptor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>SPSSO Descriptor Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType
	 * @generated
	 */
	EClass getSPSSODescriptorType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType#getAssertionConsumerService <em>Assertion Consumer Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Assertion Consumer Service</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType#getAssertionConsumerService()
	 * @see #getSPSSODescriptorType()
	 * @generated
	 */
	EReference getSPSSODescriptorType_AssertionConsumerService();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType#getAttributeConsumingService <em>Attribute Consuming Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute Consuming Service</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType#getAttributeConsumingService()
	 * @see #getSPSSODescriptorType()
	 * @generated
	 */
	EReference getSPSSODescriptorType_AttributeConsumingService();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType#getAuthnPolicy <em>Authn Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Authn Policy</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType#getAuthnPolicy()
	 * @see #getSPSSODescriptorType()
	 * @generated
	 */
	EReference getSPSSODescriptorType_AuthnPolicy();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType#isAuthnRequestsSigned <em>Authn Requests Signed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Authn Requests Signed</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType#isAuthnRequestsSigned()
	 * @see #getSPSSODescriptorType()
	 * @generated
	 */
	EAttribute getSPSSODescriptorType_AuthnRequestsSigned();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType#isWantAssertionsSigned <em>Want Assertions Signed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Want Assertions Signed</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType#isWantAssertionsSigned()
	 * @see #getSPSSODescriptorType()
	 * @generated
	 */
	EAttribute getSPSSODescriptorType_WantAssertionsSigned();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.SSODescriptorType <em>SSO Descriptor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>SSO Descriptor Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.SSODescriptorType
	 * @generated
	 */
	EClass getSSODescriptorType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.SSODescriptorType#getArtifactResolutionService <em>Artifact Resolution Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Artifact Resolution Service</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.SSODescriptorType#getArtifactResolutionService()
	 * @see #getSSODescriptorType()
	 * @generated
	 */
	EReference getSSODescriptorType_ArtifactResolutionService();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.SSODescriptorType#getSingleLogoutService <em>Single Logout Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Single Logout Service</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.SSODescriptorType#getSingleLogoutService()
	 * @see #getSSODescriptorType()
	 * @generated
	 */
	EReference getSSODescriptorType_SingleLogoutService();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.SSODescriptorType#getManageNameIDService <em>Manage Name ID Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Manage Name ID Service</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.SSODescriptorType#getManageNameIDService()
	 * @see #getSSODescriptorType()
	 * @generated
	 */
	EReference getSSODescriptorType_ManageNameIDService();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.metadata.SSODescriptorType#getNameIDFormat <em>Name ID Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Name ID Format</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.SSODescriptorType#getNameIDFormat()
	 * @see #getSSODescriptorType()
	 * @generated
	 */
	EAttribute getSSODescriptorType_NameIDFormat();

	/**
	 * Returns the meta object for enum '{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactTypeType <em>Contact Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Contact Type Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.ContactTypeType
	 * @generated
	 */
	EEnum getContactTypeType();

	/**
	 * Returns the meta object for data type '{@link java.util.List <em>Any URI List Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Any URI List Type</em>'.
	 * @see java.util.List
	 * @model instanceClass="java.util.List"
	 *        extendedMetaData="name='anyURIListType' itemType='http://www.eclipse.org/emf/2003/XMLType#anyURI'"
	 * @generated
	 */
	EDataType getAnyURIListType();

	/**
	 * Returns the meta object for data type '{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactTypeType <em>Contact Type Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Contact Type Type Object</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.ContactTypeType
	 * @model instanceClass="com.tibco.be.baas.security.authn.saml.model.metadata.ContactTypeType"
	 *        extendedMetaData="name='ContactTypeType:Object' baseType='ContactTypeType'"
	 * @generated
	 */
	EDataType getContactTypeTypeObject();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Entity ID Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Entity ID Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='entityIDType' baseType='http://www.eclipse.org/emf/2003/XMLType#anyURI' maxLength='1024'"
	 * @generated
	 */
	EDataType getEntityIDType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MetadataFactory getMetadataFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AdditionalMetadataLocationTypeImpl <em>Additional Metadata Location Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.AdditionalMetadataLocationTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getAdditionalMetadataLocationType()
		 * @generated
		 */
		EClass ADDITIONAL_METADATA_LOCATION_TYPE = eINSTANCE.getAdditionalMetadataLocationType();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDITIONAL_METADATA_LOCATION_TYPE__VALUE = eINSTANCE.getAdditionalMetadataLocationType_Value();

		/**
		 * The meta object literal for the '<em><b>Namespace</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDITIONAL_METADATA_LOCATION_TYPE__NAMESPACE = eINSTANCE.getAdditionalMetadataLocationType_Namespace();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AffiliationDescriptorTypeImpl <em>Affiliation Descriptor Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.AffiliationDescriptorTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getAffiliationDescriptorType()
		 * @generated
		 */
		EClass AFFILIATION_DESCRIPTOR_TYPE = eINSTANCE.getAffiliationDescriptorType();

		/**
		 * The meta object literal for the '<em><b>Extensions</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AFFILIATION_DESCRIPTOR_TYPE__EXTENSIONS = eINSTANCE.getAffiliationDescriptorType_Extensions();

		/**
		 * The meta object literal for the '<em><b>Affiliate Member</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AFFILIATION_DESCRIPTOR_TYPE__AFFILIATE_MEMBER = eINSTANCE.getAffiliationDescriptorType_AffiliateMember();

		/**
		 * The meta object literal for the '<em><b>Affiliation Owner ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AFFILIATION_DESCRIPTOR_TYPE__AFFILIATION_OWNER_ID = eINSTANCE.getAffiliationDescriptorType_AffiliationOwnerID();

		/**
		 * The meta object literal for the '<em><b>Cache Duration</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AFFILIATION_DESCRIPTOR_TYPE__CACHE_DURATION = eINSTANCE.getAffiliationDescriptorType_CacheDuration();

		/**
		 * The meta object literal for the '<em><b>ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AFFILIATION_DESCRIPTOR_TYPE__ID = eINSTANCE.getAffiliationDescriptorType_ID();

		/**
		 * The meta object literal for the '<em><b>Valid Until</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AFFILIATION_DESCRIPTOR_TYPE__VALID_UNTIL = eINSTANCE.getAffiliationDescriptorType_ValidUntil();

		/**
		 * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AFFILIATION_DESCRIPTOR_TYPE__ANY_ATTRIBUTE = eINSTANCE.getAffiliationDescriptorType_AnyAttribute();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AttributeAuthorityDescriptorTypeImpl <em>Attribute Authority Descriptor Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.AttributeAuthorityDescriptorTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getAttributeAuthorityDescriptorType()
		 * @generated
		 */
		EClass ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE = eINSTANCE.getAttributeAuthorityDescriptorType();

		/**
		 * The meta object literal for the '<em><b>Attribute Service</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ATTRIBUTE_SERVICE = eINSTANCE.getAttributeAuthorityDescriptorType_AttributeService();

		/**
		 * The meta object literal for the '<em><b>Assertion ID Request Service</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE = eINSTANCE.getAttributeAuthorityDescriptorType_AssertionIDRequestService();

		/**
		 * The meta object literal for the '<em><b>Name ID Format</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__NAME_ID_FORMAT = eINSTANCE.getAttributeAuthorityDescriptorType_NameIDFormat();

		/**
		 * The meta object literal for the '<em><b>Attribute Profile</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ATTRIBUTE_PROFILE = eINSTANCE.getAttributeAuthorityDescriptorType_AttributeProfile();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ATTRIBUTE = eINSTANCE.getAttributeAuthorityDescriptorType_Attribute();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AttributeConsumingServiceTypeImpl <em>Attribute Consuming Service Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.AttributeConsumingServiceTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getAttributeConsumingServiceType()
		 * @generated
		 */
		EClass ATTRIBUTE_CONSUMING_SERVICE_TYPE = eINSTANCE.getAttributeConsumingServiceType();

		/**
		 * The meta object literal for the '<em><b>Service Name</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_CONSUMING_SERVICE_TYPE__SERVICE_NAME = eINSTANCE.getAttributeConsumingServiceType_ServiceName();

		/**
		 * The meta object literal for the '<em><b>Service Description</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_CONSUMING_SERVICE_TYPE__SERVICE_DESCRIPTION = eINSTANCE.getAttributeConsumingServiceType_ServiceDescription();

		/**
		 * The meta object literal for the '<em><b>Requested Attribute</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_CONSUMING_SERVICE_TYPE__REQUESTED_ATTRIBUTE = eINSTANCE.getAttributeConsumingServiceType_RequestedAttribute();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_CONSUMING_SERVICE_TYPE__INDEX = eINSTANCE.getAttributeConsumingServiceType_Index();

		/**
		 * The meta object literal for the '<em><b>Is Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_CONSUMING_SERVICE_TYPE__IS_DEFAULT = eINSTANCE.getAttributeConsumingServiceType_IsDefault();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AuthnAuthorityDescriptorTypeImpl <em>Authn Authority Descriptor Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.AuthnAuthorityDescriptorTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getAuthnAuthorityDescriptorType()
		 * @generated
		 */
		EClass AUTHN_AUTHORITY_DESCRIPTOR_TYPE = eINSTANCE.getAuthnAuthorityDescriptorType();

		/**
		 * The meta object literal for the '<em><b>Authn Query Service</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTHN_AUTHORITY_DESCRIPTOR_TYPE__AUTHN_QUERY_SERVICE = eINSTANCE.getAuthnAuthorityDescriptorType_AuthnQueryService();

		/**
		 * The meta object literal for the '<em><b>Assertion ID Request Service</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTHN_AUTHORITY_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE = eINSTANCE.getAuthnAuthorityDescriptorType_AssertionIDRequestService();

		/**
		 * The meta object literal for the '<em><b>Name ID Format</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHN_AUTHORITY_DESCRIPTOR_TYPE__NAME_ID_FORMAT = eINSTANCE.getAuthnAuthorityDescriptorType_NameIDFormat();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.ContactTypeImpl <em>Contact Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.ContactTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getContactType()
		 * @generated
		 */
		EClass CONTACT_TYPE = eINSTANCE.getContactType();

		/**
		 * The meta object literal for the '<em><b>Extensions</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTACT_TYPE__EXTENSIONS = eINSTANCE.getContactType_Extensions();

		/**
		 * The meta object literal for the '<em><b>Company</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTACT_TYPE__COMPANY = eINSTANCE.getContactType_Company();

		/**
		 * The meta object literal for the '<em><b>Given Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTACT_TYPE__GIVEN_NAME = eINSTANCE.getContactType_GivenName();

		/**
		 * The meta object literal for the '<em><b>Sur Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTACT_TYPE__SUR_NAME = eINSTANCE.getContactType_SurName();

		/**
		 * The meta object literal for the '<em><b>Email Address</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTACT_TYPE__EMAIL_ADDRESS = eINSTANCE.getContactType_EmailAddress();

		/**
		 * The meta object literal for the '<em><b>Telephone Number</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTACT_TYPE__TELEPHONE_NUMBER = eINSTANCE.getContactType_TelephoneNumber();

		/**
		 * The meta object literal for the '<em><b>Contact Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTACT_TYPE__CONTACT_TYPE = eINSTANCE.getContactType_ContactType();

		/**
		 * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTACT_TYPE__ANY_ATTRIBUTE = eINSTANCE.getContactType_AnyAttribute();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.DocumentRootImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getDocumentRoot()
		 * @generated
		 */
		EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

		/**
		 * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

		/**
		 * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getDocumentRoot_XSISchemaLocation();

		/**
		 * The meta object literal for the '<em><b>Additional Metadata Location</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ADDITIONAL_METADATA_LOCATION = eINSTANCE.getDocumentRoot_AdditionalMetadataLocation();

		/**
		 * The meta object literal for the '<em><b>Affiliate Member</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__AFFILIATE_MEMBER = eINSTANCE.getDocumentRoot_AffiliateMember();

		/**
		 * The meta object literal for the '<em><b>Affiliation Descriptor</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__AFFILIATION_DESCRIPTOR = eINSTANCE.getDocumentRoot_AffiliationDescriptor();

		/**
		 * The meta object literal for the '<em><b>Artifact Resolution Service</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ARTIFACT_RESOLUTION_SERVICE = eINSTANCE.getDocumentRoot_ArtifactResolutionService();

		/**
		 * The meta object literal for the '<em><b>Assertion Consumer Service</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ASSERTION_CONSUMER_SERVICE = eINSTANCE.getDocumentRoot_AssertionConsumerService();

		/**
		 * The meta object literal for the '<em><b>Assertion ID Request Service</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ASSERTION_ID_REQUEST_SERVICE = eINSTANCE.getDocumentRoot_AssertionIDRequestService();

		/**
		 * The meta object literal for the '<em><b>Attribute Authority Descriptor</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ATTRIBUTE_AUTHORITY_DESCRIPTOR = eINSTANCE.getDocumentRoot_AttributeAuthorityDescriptor();

		/**
		 * The meta object literal for the '<em><b>Attribute Consuming Service</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ATTRIBUTE_CONSUMING_SERVICE = eINSTANCE.getDocumentRoot_AttributeConsumingService();

		/**
		 * The meta object literal for the '<em><b>Attribute Profile</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__ATTRIBUTE_PROFILE = eINSTANCE.getDocumentRoot_AttributeProfile();

		/**
		 * The meta object literal for the '<em><b>Attribute Service</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ATTRIBUTE_SERVICE = eINSTANCE.getDocumentRoot_AttributeService();

		/**
		 * The meta object literal for the '<em><b>Authn Authority Descriptor</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__AUTHN_AUTHORITY_DESCRIPTOR = eINSTANCE.getDocumentRoot_AuthnAuthorityDescriptor();

		/**
		 * The meta object literal for the '<em><b>Authn Query Service</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__AUTHN_QUERY_SERVICE = eINSTANCE.getDocumentRoot_AuthnQueryService();

		/**
		 * The meta object literal for the '<em><b>Authz Service</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__AUTHZ_SERVICE = eINSTANCE.getDocumentRoot_AuthzService();

		/**
		 * The meta object literal for the '<em><b>Company</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__COMPANY = eINSTANCE.getDocumentRoot_Company();

		/**
		 * The meta object literal for the '<em><b>Contact Person</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__CONTACT_PERSON = eINSTANCE.getDocumentRoot_ContactPerson();

		/**
		 * The meta object literal for the '<em><b>Email Address</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__EMAIL_ADDRESS = eINSTANCE.getDocumentRoot_EmailAddress();

		/**
		 * The meta object literal for the '<em><b>Entities Descriptor</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ENTITIES_DESCRIPTOR = eINSTANCE.getDocumentRoot_EntitiesDescriptor();

		/**
		 * The meta object literal for the '<em><b>Entity Descriptor</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ENTITY_DESCRIPTOR = eINSTANCE.getDocumentRoot_EntityDescriptor();

		/**
		 * The meta object literal for the '<em><b>Extensions</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__EXTENSIONS = eINSTANCE.getDocumentRoot_Extensions();

		/**
		 * The meta object literal for the '<em><b>Given Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__GIVEN_NAME = eINSTANCE.getDocumentRoot_GivenName();

		/**
		 * The meta object literal for the '<em><b>IDPSSO Descriptor</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__IDPSSO_DESCRIPTOR = eINSTANCE.getDocumentRoot_IDPSSODescriptor();

		/**
		 * The meta object literal for the '<em><b>Manage Name ID Service</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MANAGE_NAME_ID_SERVICE = eINSTANCE.getDocumentRoot_ManageNameIDService();

		/**
		 * The meta object literal for the '<em><b>Name ID Format</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__NAME_ID_FORMAT = eINSTANCE.getDocumentRoot_NameIDFormat();

		/**
		 * The meta object literal for the '<em><b>Name ID Mapping Service</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__NAME_ID_MAPPING_SERVICE = eINSTANCE.getDocumentRoot_NameIDMappingService();

		/**
		 * The meta object literal for the '<em><b>Organization</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ORGANIZATION = eINSTANCE.getDocumentRoot_Organization();

		/**
		 * The meta object literal for the '<em><b>Organization Display Name</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ORGANIZATION_DISPLAY_NAME = eINSTANCE.getDocumentRoot_OrganizationDisplayName();

		/**
		 * The meta object literal for the '<em><b>Organization Name</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ORGANIZATION_NAME = eINSTANCE.getDocumentRoot_OrganizationName();

		/**
		 * The meta object literal for the '<em><b>Organization URL</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ORGANIZATION_URL = eINSTANCE.getDocumentRoot_OrganizationURL();

		/**
		 * The meta object literal for the '<em><b>PDP Descriptor</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__PDP_DESCRIPTOR = eINSTANCE.getDocumentRoot_PDPDescriptor();

		/**
		 * The meta object literal for the '<em><b>Requested Attribute</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__REQUESTED_ATTRIBUTE = eINSTANCE.getDocumentRoot_RequestedAttribute();

		/**
		 * The meta object literal for the '<em><b>Role Descriptor</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ROLE_DESCRIPTOR = eINSTANCE.getDocumentRoot_RoleDescriptor();

		/**
		 * The meta object literal for the '<em><b>Service Description</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__SERVICE_DESCRIPTION = eINSTANCE.getDocumentRoot_ServiceDescription();

		/**
		 * The meta object literal for the '<em><b>Service Name</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__SERVICE_NAME = eINSTANCE.getDocumentRoot_ServiceName();

		/**
		 * The meta object literal for the '<em><b>Single Logout Service</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__SINGLE_LOGOUT_SERVICE = eINSTANCE.getDocumentRoot_SingleLogoutService();

		/**
		 * The meta object literal for the '<em><b>Single Sign On Service</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__SINGLE_SIGN_ON_SERVICE = eINSTANCE.getDocumentRoot_SingleSignOnService();

		/**
		 * The meta object literal for the '<em><b>SPSSO Descriptor</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__SPSSO_DESCRIPTOR = eINSTANCE.getDocumentRoot_SPSSODescriptor();

		/**
		 * The meta object literal for the '<em><b>Sur Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__SUR_NAME = eINSTANCE.getDocumentRoot_SurName();

		/**
		 * The meta object literal for the '<em><b>Telephone Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__TELEPHONE_NUMBER = eINSTANCE.getDocumentRoot_TelephoneNumber();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EndpointTypeImpl <em>Endpoint Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.EndpointTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getEndpointType()
		 * @generated
		 */
		EClass ENDPOINT_TYPE = eINSTANCE.getEndpointType();

		/**
		 * The meta object literal for the '<em><b>Any</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENDPOINT_TYPE__ANY = eINSTANCE.getEndpointType_Any();

		/**
		 * The meta object literal for the '<em><b>Binding</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENDPOINT_TYPE__BINDING = eINSTANCE.getEndpointType_Binding();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENDPOINT_TYPE__LOCATION = eINSTANCE.getEndpointType_Location();

		/**
		 * The meta object literal for the '<em><b>Response Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENDPOINT_TYPE__RESPONSE_LOCATION = eINSTANCE.getEndpointType_ResponseLocation();

		/**
		 * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENDPOINT_TYPE__ANY_ATTRIBUTE = eINSTANCE.getEndpointType_AnyAttribute();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntitiesDescriptorTypeImpl <em>Entities Descriptor Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntitiesDescriptorTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getEntitiesDescriptorType()
		 * @generated
		 */
		EClass ENTITIES_DESCRIPTOR_TYPE = eINSTANCE.getEntitiesDescriptorType();

		/**
		 * The meta object literal for the '<em><b>Extensions</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITIES_DESCRIPTOR_TYPE__EXTENSIONS = eINSTANCE.getEntitiesDescriptorType_Extensions();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITIES_DESCRIPTOR_TYPE__GROUP = eINSTANCE.getEntitiesDescriptorType_Group();

		/**
		 * The meta object literal for the '<em><b>Entity Descriptor</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITIES_DESCRIPTOR_TYPE__ENTITY_DESCRIPTOR = eINSTANCE.getEntitiesDescriptorType_EntityDescriptor();

		/**
		 * The meta object literal for the '<em><b>Entities Descriptor</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITIES_DESCRIPTOR_TYPE__ENTITIES_DESCRIPTOR = eINSTANCE.getEntitiesDescriptorType_EntitiesDescriptor();

		/**
		 * The meta object literal for the '<em><b>Cache Duration</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITIES_DESCRIPTOR_TYPE__CACHE_DURATION = eINSTANCE.getEntitiesDescriptorType_CacheDuration();

		/**
		 * The meta object literal for the '<em><b>ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITIES_DESCRIPTOR_TYPE__ID = eINSTANCE.getEntitiesDescriptorType_ID();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITIES_DESCRIPTOR_TYPE__NAME = eINSTANCE.getEntitiesDescriptorType_Name();

		/**
		 * The meta object literal for the '<em><b>Valid Until</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITIES_DESCRIPTOR_TYPE__VALID_UNTIL = eINSTANCE.getEntitiesDescriptorType_ValidUntil();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntityDescriptorTypeImpl <em>Entity Descriptor Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntityDescriptorTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getEntityDescriptorType()
		 * @generated
		 */
		EClass ENTITY_DESCRIPTOR_TYPE = eINSTANCE.getEntityDescriptorType();

		/**
		 * The meta object literal for the '<em><b>Extensions</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY_DESCRIPTOR_TYPE__EXTENSIONS = eINSTANCE.getEntityDescriptorType_Extensions();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITY_DESCRIPTOR_TYPE__GROUP = eINSTANCE.getEntityDescriptorType_Group();

		/**
		 * The meta object literal for the '<em><b>Role Descriptor</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY_DESCRIPTOR_TYPE__ROLE_DESCRIPTOR = eINSTANCE.getEntityDescriptorType_RoleDescriptor();

		/**
		 * The meta object literal for the '<em><b>IDPSSO Descriptor</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY_DESCRIPTOR_TYPE__IDPSSO_DESCRIPTOR = eINSTANCE.getEntityDescriptorType_IDPSSODescriptor();

		/**
		 * The meta object literal for the '<em><b>SPSSO Descriptor</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY_DESCRIPTOR_TYPE__SPSSO_DESCRIPTOR = eINSTANCE.getEntityDescriptorType_SPSSODescriptor();

		/**
		 * The meta object literal for the '<em><b>Authn Authority Descriptor</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY_DESCRIPTOR_TYPE__AUTHN_AUTHORITY_DESCRIPTOR = eINSTANCE.getEntityDescriptorType_AuthnAuthorityDescriptor();

		/**
		 * The meta object literal for the '<em><b>Attribute Authority Descriptor</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY_DESCRIPTOR_TYPE__ATTRIBUTE_AUTHORITY_DESCRIPTOR = eINSTANCE.getEntityDescriptorType_AttributeAuthorityDescriptor();

		/**
		 * The meta object literal for the '<em><b>PDP Descriptor</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY_DESCRIPTOR_TYPE__PDP_DESCRIPTOR = eINSTANCE.getEntityDescriptorType_PDPDescriptor();

		/**
		 * The meta object literal for the '<em><b>Affiliation Descriptor</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY_DESCRIPTOR_TYPE__AFFILIATION_DESCRIPTOR = eINSTANCE.getEntityDescriptorType_AffiliationDescriptor();

		/**
		 * The meta object literal for the '<em><b>Organization</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY_DESCRIPTOR_TYPE__ORGANIZATION = eINSTANCE.getEntityDescriptorType_Organization();

		/**
		 * The meta object literal for the '<em><b>Contact Person</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY_DESCRIPTOR_TYPE__CONTACT_PERSON = eINSTANCE.getEntityDescriptorType_ContactPerson();

		/**
		 * The meta object literal for the '<em><b>Additional Metadata Location</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY_DESCRIPTOR_TYPE__ADDITIONAL_METADATA_LOCATION = eINSTANCE.getEntityDescriptorType_AdditionalMetadataLocation();

		/**
		 * The meta object literal for the '<em><b>Cache Duration</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITY_DESCRIPTOR_TYPE__CACHE_DURATION = eINSTANCE.getEntityDescriptorType_CacheDuration();

		/**
		 * The meta object literal for the '<em><b>Entity ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITY_DESCRIPTOR_TYPE__ENTITY_ID = eINSTANCE.getEntityDescriptorType_EntityID();

		/**
		 * The meta object literal for the '<em><b>ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITY_DESCRIPTOR_TYPE__ID = eINSTANCE.getEntityDescriptorType_ID();

		/**
		 * The meta object literal for the '<em><b>Valid Until</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITY_DESCRIPTOR_TYPE__VALID_UNTIL = eINSTANCE.getEntityDescriptorType_ValidUntil();

		/**
		 * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITY_DESCRIPTOR_TYPE__ANY_ATTRIBUTE = eINSTANCE.getEntityDescriptorType_AnyAttribute();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.ExtensionsTypeImpl <em>Extensions Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.ExtensionsTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getExtensionsType()
		 * @generated
		 */
		EClass EXTENSIONS_TYPE = eINSTANCE.getExtensionsType();

		/**
		 * The meta object literal for the '<em><b>Any</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTENSIONS_TYPE__ANY = eINSTANCE.getExtensionsType_Any();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.IDPSSODescriptorTypeImpl <em>IDPSSO Descriptor Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.IDPSSODescriptorTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getIDPSSODescriptorType()
		 * @generated
		 */
		EClass IDPSSO_DESCRIPTOR_TYPE = eINSTANCE.getIDPSSODescriptorType();

		/**
		 * The meta object literal for the '<em><b>Single Sign On Service</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IDPSSO_DESCRIPTOR_TYPE__SINGLE_SIGN_ON_SERVICE = eINSTANCE.getIDPSSODescriptorType_SingleSignOnService();

		/**
		 * The meta object literal for the '<em><b>Name ID Mapping Service</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IDPSSO_DESCRIPTOR_TYPE__NAME_ID_MAPPING_SERVICE = eINSTANCE.getIDPSSODescriptorType_NameIDMappingService();

		/**
		 * The meta object literal for the '<em><b>Assertion ID Request Service</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IDPSSO_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE = eINSTANCE.getIDPSSODescriptorType_AssertionIDRequestService();

		/**
		 * The meta object literal for the '<em><b>Attribute Profile</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDPSSO_DESCRIPTOR_TYPE__ATTRIBUTE_PROFILE = eINSTANCE.getIDPSSODescriptorType_AttributeProfile();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IDPSSO_DESCRIPTOR_TYPE__ATTRIBUTE = eINSTANCE.getIDPSSODescriptorType_Attribute();

		/**
		 * The meta object literal for the '<em><b>Want Authn Requests Signed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDPSSO_DESCRIPTOR_TYPE__WANT_AUTHN_REQUESTS_SIGNED = eINSTANCE.getIDPSSODescriptorType_WantAuthnRequestsSigned();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.IndexedEndpointTypeImpl <em>Indexed Endpoint Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.IndexedEndpointTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getIndexedEndpointType()
		 * @generated
		 */
		EClass INDEXED_ENDPOINT_TYPE = eINSTANCE.getIndexedEndpointType();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INDEXED_ENDPOINT_TYPE__INDEX = eINSTANCE.getIndexedEndpointType_Index();

		/**
		 * The meta object literal for the '<em><b>Is Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INDEXED_ENDPOINT_TYPE__IS_DEFAULT = eINSTANCE.getIndexedEndpointType_IsDefault();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.LocalizedNameTypeImpl <em>Localized Name Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.LocalizedNameTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getLocalizedNameType()
		 * @generated
		 */
		EClass LOCALIZED_NAME_TYPE = eINSTANCE.getLocalizedNameType();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCALIZED_NAME_TYPE__VALUE = eINSTANCE.getLocalizedNameType_Value();

		/**
		 * The meta object literal for the '<em><b>Lang</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCALIZED_NAME_TYPE__LANG = eINSTANCE.getLocalizedNameType_Lang();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.LocalizedURITypeImpl <em>Localized URI Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.LocalizedURITypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getLocalizedURIType()
		 * @generated
		 */
		EClass LOCALIZED_URI_TYPE = eINSTANCE.getLocalizedURIType();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCALIZED_URI_TYPE__VALUE = eINSTANCE.getLocalizedURIType_Value();

		/**
		 * The meta object literal for the '<em><b>Lang</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCALIZED_URI_TYPE__LANG = eINSTANCE.getLocalizedURIType_Lang();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.OrganizationTypeImpl <em>Organization Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.OrganizationTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getOrganizationType()
		 * @generated
		 */
		EClass ORGANIZATION_TYPE = eINSTANCE.getOrganizationType();

		/**
		 * The meta object literal for the '<em><b>Extensions</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ORGANIZATION_TYPE__EXTENSIONS = eINSTANCE.getOrganizationType_Extensions();

		/**
		 * The meta object literal for the '<em><b>Organization Name</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ORGANIZATION_TYPE__ORGANIZATION_NAME = eINSTANCE.getOrganizationType_OrganizationName();

		/**
		 * The meta object literal for the '<em><b>Organization Display Name</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ORGANIZATION_TYPE__ORGANIZATION_DISPLAY_NAME = eINSTANCE.getOrganizationType_OrganizationDisplayName();

		/**
		 * The meta object literal for the '<em><b>Organization URL</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ORGANIZATION_TYPE__ORGANIZATION_URL = eINSTANCE.getOrganizationType_OrganizationURL();

		/**
		 * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ORGANIZATION_TYPE__ANY_ATTRIBUTE = eINSTANCE.getOrganizationType_AnyAttribute();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.PDPDescriptorTypeImpl <em>PDP Descriptor Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.PDPDescriptorTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getPDPDescriptorType()
		 * @generated
		 */
		EClass PDP_DESCRIPTOR_TYPE = eINSTANCE.getPDPDescriptorType();

		/**
		 * The meta object literal for the '<em><b>Authz Service</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PDP_DESCRIPTOR_TYPE__AUTHZ_SERVICE = eINSTANCE.getPDPDescriptorType_AuthzService();

		/**
		 * The meta object literal for the '<em><b>Assertion ID Request Service</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PDP_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE = eINSTANCE.getPDPDescriptorType_AssertionIDRequestService();

		/**
		 * The meta object literal for the '<em><b>Name ID Format</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PDP_DESCRIPTOR_TYPE__NAME_ID_FORMAT = eINSTANCE.getPDPDescriptorType_NameIDFormat();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.RequestedAttributeTypeImpl <em>Requested Attribute Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.RequestedAttributeTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getRequestedAttributeType()
		 * @generated
		 */
		EClass REQUESTED_ATTRIBUTE_TYPE = eINSTANCE.getRequestedAttributeType();

		/**
		 * The meta object literal for the '<em><b>Is Required</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUESTED_ATTRIBUTE_TYPE__IS_REQUIRED = eINSTANCE.getRequestedAttributeType_IsRequired();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.RoleDescriptorTypeImpl <em>Role Descriptor Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.RoleDescriptorTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getRoleDescriptorType()
		 * @generated
		 */
		EClass ROLE_DESCRIPTOR_TYPE = eINSTANCE.getRoleDescriptorType();

		/**
		 * The meta object literal for the '<em><b>Extensions</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROLE_DESCRIPTOR_TYPE__EXTENSIONS = eINSTANCE.getRoleDescriptorType_Extensions();

		/**
		 * The meta object literal for the '<em><b>Organization</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROLE_DESCRIPTOR_TYPE__ORGANIZATION = eINSTANCE.getRoleDescriptorType_Organization();

		/**
		 * The meta object literal for the '<em><b>Contact Person</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROLE_DESCRIPTOR_TYPE__CONTACT_PERSON = eINSTANCE.getRoleDescriptorType_ContactPerson();

		/**
		 * The meta object literal for the '<em><b>Cache Duration</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROLE_DESCRIPTOR_TYPE__CACHE_DURATION = eINSTANCE.getRoleDescriptorType_CacheDuration();

		/**
		 * The meta object literal for the '<em><b>Error URL</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROLE_DESCRIPTOR_TYPE__ERROR_URL = eINSTANCE.getRoleDescriptorType_ErrorURL();

		/**
		 * The meta object literal for the '<em><b>ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROLE_DESCRIPTOR_TYPE__ID = eINSTANCE.getRoleDescriptorType_ID();

		/**
		 * The meta object literal for the '<em><b>Protocol Support Enumeration</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROLE_DESCRIPTOR_TYPE__PROTOCOL_SUPPORT_ENUMERATION = eINSTANCE.getRoleDescriptorType_ProtocolSupportEnumeration();

		/**
		 * The meta object literal for the '<em><b>Valid Until</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROLE_DESCRIPTOR_TYPE__VALID_UNTIL = eINSTANCE.getRoleDescriptorType_ValidUntil();

		/**
		 * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROLE_DESCRIPTOR_TYPE__ANY_ATTRIBUTE = eINSTANCE.getRoleDescriptorType_AnyAttribute();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.SPSSODescriptorTypeImpl <em>SPSSO Descriptor Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.SPSSODescriptorTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getSPSSODescriptorType()
		 * @generated
		 */
		EClass SPSSO_DESCRIPTOR_TYPE = eINSTANCE.getSPSSODescriptorType();

		/**
		 * The meta object literal for the '<em><b>Assertion Consumer Service</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SPSSO_DESCRIPTOR_TYPE__ASSERTION_CONSUMER_SERVICE = eINSTANCE.getSPSSODescriptorType_AssertionConsumerService();

		/**
		 * The meta object literal for the '<em><b>Attribute Consuming Service</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SPSSO_DESCRIPTOR_TYPE__ATTRIBUTE_CONSUMING_SERVICE = eINSTANCE.getSPSSODescriptorType_AttributeConsumingService();

		/**
		 * The meta object literal for the '<em><b>Authn Policy</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SPSSO_DESCRIPTOR_TYPE__AUTHN_POLICY = eINSTANCE.getSPSSODescriptorType_AuthnPolicy();

		/**
		 * The meta object literal for the '<em><b>Authn Requests Signed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SPSSO_DESCRIPTOR_TYPE__AUTHN_REQUESTS_SIGNED = eINSTANCE.getSPSSODescriptorType_AuthnRequestsSigned();

		/**
		 * The meta object literal for the '<em><b>Want Assertions Signed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SPSSO_DESCRIPTOR_TYPE__WANT_ASSERTIONS_SIGNED = eINSTANCE.getSPSSODescriptorType_WantAssertionsSigned();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.SSODescriptorTypeImpl <em>SSO Descriptor Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.SSODescriptorTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getSSODescriptorType()
		 * @generated
		 */
		EClass SSO_DESCRIPTOR_TYPE = eINSTANCE.getSSODescriptorType();

		/**
		 * The meta object literal for the '<em><b>Artifact Resolution Service</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SSO_DESCRIPTOR_TYPE__ARTIFACT_RESOLUTION_SERVICE = eINSTANCE.getSSODescriptorType_ArtifactResolutionService();

		/**
		 * The meta object literal for the '<em><b>Single Logout Service</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SSO_DESCRIPTOR_TYPE__SINGLE_LOGOUT_SERVICE = eINSTANCE.getSSODescriptorType_SingleLogoutService();

		/**
		 * The meta object literal for the '<em><b>Manage Name ID Service</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SSO_DESCRIPTOR_TYPE__MANAGE_NAME_ID_SERVICE = eINSTANCE.getSSODescriptorType_ManageNameIDService();

		/**
		 * The meta object literal for the '<em><b>Name ID Format</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SSO_DESCRIPTOR_TYPE__NAME_ID_FORMAT = eINSTANCE.getSSODescriptorType_NameIDFormat();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactTypeType <em>Contact Type Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.ContactTypeType
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getContactTypeType()
		 * @generated
		 */
		EEnum CONTACT_TYPE_TYPE = eINSTANCE.getContactTypeType();

		/**
		 * The meta object literal for the '<em>Any URI List Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.util.List
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getAnyURIListType()
		 * @generated
		 */
		EDataType ANY_URI_LIST_TYPE = eINSTANCE.getAnyURIListType();

		/**
		 * The meta object literal for the '<em>Contact Type Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.ContactTypeType
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getContactTypeTypeObject()
		 * @generated
		 */
		EDataType CONTACT_TYPE_TYPE_OBJECT = eINSTANCE.getContactTypeTypeObject();

		/**
		 * The meta object literal for the '<em>Entity ID Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl#getEntityIDType()
		 * @generated
		 */
		EDataType ENTITY_ID_TYPE = eINSTANCE.getEntityIDType();

	}

} //MetadataPackage
