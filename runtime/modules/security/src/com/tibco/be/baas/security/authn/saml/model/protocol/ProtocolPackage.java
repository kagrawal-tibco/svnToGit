/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * 			Document identifier: saml-schema-protocol-2.0 Location:
 * 			http://docs.oasis-open.org/security/saml/v2.0/ Revision history: V1.0
 * 			(November, 2002): Initial Standard Schema. V1.1 (September, 2003):
 * 			Updates within the same V1.0 namespace. V2.0 (March, 2005): New
 * 			protocol schema based in a SAML V2.0 namespace.
 * 		
 * 
 * 			Document identifier: saml-schema-assertion-2.0 Location:
 * 			http://docs.oasis-open.org/security/saml/v2.0/ Revision history: V1.0
 * 			(November, 2002): Initial Standard Schema. V1.1 (September, 2003):
 * 			Updates within the same V1.0 namespace. V2.0 (March, 2005): New
 * 			assertion schema for SAML V2.0 namespace.
 * 		
 * <!-- end-model-doc -->
 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolFactory
 * @model kind="package"
 *        annotation="http://java.sun.com/xml/ns/jaxb version='2.0'"
 * @generated
 */
public interface ProtocolPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "protocol";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "urn:oasis:names:tc:SAML:2.0:protocol";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	String eNS_PREFIX = "samlp";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ProtocolPackage eINSTANCE = com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.RequestAbstractTypeImpl <em>Request Abstract Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.RequestAbstractTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getRequestAbstractType()
	 * @generated
	 */
	int REQUEST_ABSTRACT_TYPE = 16;

	/**
	 * The feature id for the '<em><b>Issuer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUEST_ABSTRACT_TYPE__ISSUER = 0;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUEST_ABSTRACT_TYPE__EXTENSIONS = 1;

	/**
	 * The feature id for the '<em><b>Consent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUEST_ABSTRACT_TYPE__CONSENT = 2;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUEST_ABSTRACT_TYPE__DESTINATION = 3;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUEST_ABSTRACT_TYPE__ID = 4;

	/**
	 * The feature id for the '<em><b>Issue Instant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUEST_ABSTRACT_TYPE__ISSUE_INSTANT = 5;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUEST_ABSTRACT_TYPE__VERSION = 6;

	/**
	 * The number of structural features of the '<em>Request Abstract Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUEST_ABSTRACT_TYPE_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.ArtifactResolveTypeImpl <em>Artifact Resolve Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ArtifactResolveTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getArtifactResolveType()
	 * @generated
	 */
	int ARTIFACT_RESOLVE_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Issuer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_RESOLVE_TYPE__ISSUER = REQUEST_ABSTRACT_TYPE__ISSUER;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_RESOLVE_TYPE__EXTENSIONS = REQUEST_ABSTRACT_TYPE__EXTENSIONS;

	/**
	 * The feature id for the '<em><b>Consent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_RESOLVE_TYPE__CONSENT = REQUEST_ABSTRACT_TYPE__CONSENT;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_RESOLVE_TYPE__DESTINATION = REQUEST_ABSTRACT_TYPE__DESTINATION;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_RESOLVE_TYPE__ID = REQUEST_ABSTRACT_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Issue Instant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_RESOLVE_TYPE__ISSUE_INSTANT = REQUEST_ABSTRACT_TYPE__ISSUE_INSTANT;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_RESOLVE_TYPE__VERSION = REQUEST_ABSTRACT_TYPE__VERSION;

	/**
	 * The feature id for the '<em><b>Artifact</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_RESOLVE_TYPE__ARTIFACT = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Artifact Resolve Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_RESOLVE_TYPE_FEATURE_COUNT = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.StatusResponseTypeImpl <em>Status Response Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.StatusResponseTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getStatusResponseType()
	 * @generated
	 */
	int STATUS_RESPONSE_TYPE = 22;

	/**
	 * The feature id for the '<em><b>Issuer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_RESPONSE_TYPE__ISSUER = 0;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_RESPONSE_TYPE__EXTENSIONS = 1;

	/**
	 * The feature id for the '<em><b>Status</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_RESPONSE_TYPE__STATUS = 2;

	/**
	 * The feature id for the '<em><b>Consent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_RESPONSE_TYPE__CONSENT = 3;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_RESPONSE_TYPE__DESTINATION = 4;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_RESPONSE_TYPE__ID = 5;

	/**
	 * The feature id for the '<em><b>In Response To</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_RESPONSE_TYPE__IN_RESPONSE_TO = 6;

	/**
	 * The feature id for the '<em><b>Issue Instant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_RESPONSE_TYPE__ISSUE_INSTANT = 7;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_RESPONSE_TYPE__VERSION = 8;

	/**
	 * The number of structural features of the '<em>Status Response Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_RESPONSE_TYPE_FEATURE_COUNT = 9;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.ArtifactResponseTypeImpl <em>Artifact Response Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ArtifactResponseTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getArtifactResponseType()
	 * @generated
	 */
	int ARTIFACT_RESPONSE_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Issuer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_RESPONSE_TYPE__ISSUER = STATUS_RESPONSE_TYPE__ISSUER;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_RESPONSE_TYPE__EXTENSIONS = STATUS_RESPONSE_TYPE__EXTENSIONS;

	/**
	 * The feature id for the '<em><b>Status</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_RESPONSE_TYPE__STATUS = STATUS_RESPONSE_TYPE__STATUS;

	/**
	 * The feature id for the '<em><b>Consent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_RESPONSE_TYPE__CONSENT = STATUS_RESPONSE_TYPE__CONSENT;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_RESPONSE_TYPE__DESTINATION = STATUS_RESPONSE_TYPE__DESTINATION;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_RESPONSE_TYPE__ID = STATUS_RESPONSE_TYPE__ID;

	/**
	 * The feature id for the '<em><b>In Response To</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_RESPONSE_TYPE__IN_RESPONSE_TO = STATUS_RESPONSE_TYPE__IN_RESPONSE_TO;

	/**
	 * The feature id for the '<em><b>Issue Instant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_RESPONSE_TYPE__ISSUE_INSTANT = STATUS_RESPONSE_TYPE__ISSUE_INSTANT;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_RESPONSE_TYPE__VERSION = STATUS_RESPONSE_TYPE__VERSION;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_RESPONSE_TYPE__ANY = STATUS_RESPONSE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Artifact Response Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_RESPONSE_TYPE_FEATURE_COUNT = STATUS_RESPONSE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.AssertionIDRequestTypeImpl <em>Assertion ID Request Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.AssertionIDRequestTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getAssertionIDRequestType()
	 * @generated
	 */
	int ASSERTION_ID_REQUEST_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Issuer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSERTION_ID_REQUEST_TYPE__ISSUER = REQUEST_ABSTRACT_TYPE__ISSUER;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSERTION_ID_REQUEST_TYPE__EXTENSIONS = REQUEST_ABSTRACT_TYPE__EXTENSIONS;

	/**
	 * The feature id for the '<em><b>Consent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSERTION_ID_REQUEST_TYPE__CONSENT = REQUEST_ABSTRACT_TYPE__CONSENT;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSERTION_ID_REQUEST_TYPE__DESTINATION = REQUEST_ABSTRACT_TYPE__DESTINATION;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSERTION_ID_REQUEST_TYPE__ID = REQUEST_ABSTRACT_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Issue Instant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSERTION_ID_REQUEST_TYPE__ISSUE_INSTANT = REQUEST_ABSTRACT_TYPE__ISSUE_INSTANT;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSERTION_ID_REQUEST_TYPE__VERSION = REQUEST_ABSTRACT_TYPE__VERSION;

	/**
	 * The feature id for the '<em><b>Assertion ID Ref</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSERTION_ID_REQUEST_TYPE__ASSERTION_ID_REF = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Assertion ID Request Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSERTION_ID_REQUEST_TYPE_FEATURE_COUNT = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.SubjectQueryAbstractTypeImpl <em>Subject Query Abstract Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.SubjectQueryAbstractTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getSubjectQueryAbstractType()
	 * @generated
	 */
	int SUBJECT_QUERY_ABSTRACT_TYPE = 24;

	/**
	 * The feature id for the '<em><b>Issuer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_QUERY_ABSTRACT_TYPE__ISSUER = REQUEST_ABSTRACT_TYPE__ISSUER;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_QUERY_ABSTRACT_TYPE__EXTENSIONS = REQUEST_ABSTRACT_TYPE__EXTENSIONS;

	/**
	 * The feature id for the '<em><b>Consent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_QUERY_ABSTRACT_TYPE__CONSENT = REQUEST_ABSTRACT_TYPE__CONSENT;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_QUERY_ABSTRACT_TYPE__DESTINATION = REQUEST_ABSTRACT_TYPE__DESTINATION;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_QUERY_ABSTRACT_TYPE__ID = REQUEST_ABSTRACT_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Issue Instant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_QUERY_ABSTRACT_TYPE__ISSUE_INSTANT = REQUEST_ABSTRACT_TYPE__ISSUE_INSTANT;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_QUERY_ABSTRACT_TYPE__VERSION = REQUEST_ABSTRACT_TYPE__VERSION;

	/**
	 * The feature id for the '<em><b>Subject</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_QUERY_ABSTRACT_TYPE__SUBJECT = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Subject Query Abstract Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_QUERY_ABSTRACT_TYPE_FEATURE_COUNT = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.AttributeQueryTypeImpl <em>Attribute Query Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.AttributeQueryTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getAttributeQueryType()
	 * @generated
	 */
	int ATTRIBUTE_QUERY_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Issuer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_QUERY_TYPE__ISSUER = SUBJECT_QUERY_ABSTRACT_TYPE__ISSUER;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_QUERY_TYPE__EXTENSIONS = SUBJECT_QUERY_ABSTRACT_TYPE__EXTENSIONS;

	/**
	 * The feature id for the '<em><b>Consent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_QUERY_TYPE__CONSENT = SUBJECT_QUERY_ABSTRACT_TYPE__CONSENT;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_QUERY_TYPE__DESTINATION = SUBJECT_QUERY_ABSTRACT_TYPE__DESTINATION;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_QUERY_TYPE__ID = SUBJECT_QUERY_ABSTRACT_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Issue Instant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_QUERY_TYPE__ISSUE_INSTANT = SUBJECT_QUERY_ABSTRACT_TYPE__ISSUE_INSTANT;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_QUERY_TYPE__VERSION = SUBJECT_QUERY_ABSTRACT_TYPE__VERSION;

	/**
	 * The feature id for the '<em><b>Subject</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_QUERY_TYPE__SUBJECT = SUBJECT_QUERY_ABSTRACT_TYPE__SUBJECT;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_QUERY_TYPE__ATTRIBUTE = SUBJECT_QUERY_ABSTRACT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Attribute Query Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_QUERY_TYPE_FEATURE_COUNT = SUBJECT_QUERY_ABSTRACT_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.AuthnQueryTypeImpl <em>Authn Query Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.AuthnQueryTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getAuthnQueryType()
	 * @generated
	 */
	int AUTHN_QUERY_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Issuer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_QUERY_TYPE__ISSUER = SUBJECT_QUERY_ABSTRACT_TYPE__ISSUER;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_QUERY_TYPE__EXTENSIONS = SUBJECT_QUERY_ABSTRACT_TYPE__EXTENSIONS;

	/**
	 * The feature id for the '<em><b>Consent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_QUERY_TYPE__CONSENT = SUBJECT_QUERY_ABSTRACT_TYPE__CONSENT;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_QUERY_TYPE__DESTINATION = SUBJECT_QUERY_ABSTRACT_TYPE__DESTINATION;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_QUERY_TYPE__ID = SUBJECT_QUERY_ABSTRACT_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Issue Instant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_QUERY_TYPE__ISSUE_INSTANT = SUBJECT_QUERY_ABSTRACT_TYPE__ISSUE_INSTANT;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_QUERY_TYPE__VERSION = SUBJECT_QUERY_ABSTRACT_TYPE__VERSION;

	/**
	 * The feature id for the '<em><b>Subject</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_QUERY_TYPE__SUBJECT = SUBJECT_QUERY_ABSTRACT_TYPE__SUBJECT;

	/**
	 * The feature id for the '<em><b>Requested Authn Context</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_QUERY_TYPE__REQUESTED_AUTHN_CONTEXT = SUBJECT_QUERY_ABSTRACT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Session Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_QUERY_TYPE__SESSION_INDEX = SUBJECT_QUERY_ABSTRACT_TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Authn Query Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_QUERY_TYPE_FEATURE_COUNT = SUBJECT_QUERY_ABSTRACT_TYPE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.AuthnRequestTypeImpl <em>Authn Request Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.AuthnRequestTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getAuthnRequestType()
	 * @generated
	 */
	int AUTHN_REQUEST_TYPE = 5;

	/**
	 * The feature id for the '<em><b>Issuer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_REQUEST_TYPE__ISSUER = REQUEST_ABSTRACT_TYPE__ISSUER;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_REQUEST_TYPE__EXTENSIONS = REQUEST_ABSTRACT_TYPE__EXTENSIONS;

	/**
	 * The feature id for the '<em><b>Consent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_REQUEST_TYPE__CONSENT = REQUEST_ABSTRACT_TYPE__CONSENT;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_REQUEST_TYPE__DESTINATION = REQUEST_ABSTRACT_TYPE__DESTINATION;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_REQUEST_TYPE__ID = REQUEST_ABSTRACT_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Issue Instant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_REQUEST_TYPE__ISSUE_INSTANT = REQUEST_ABSTRACT_TYPE__ISSUE_INSTANT;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_REQUEST_TYPE__VERSION = REQUEST_ABSTRACT_TYPE__VERSION;

	/**
	 * The feature id for the '<em><b>Subject</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_REQUEST_TYPE__SUBJECT = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name ID Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_REQUEST_TYPE__NAME_ID_POLICY = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Conditions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_REQUEST_TYPE__CONDITIONS = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Requested Authn Context</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_REQUEST_TYPE__REQUESTED_AUTHN_CONTEXT = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Scoping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_REQUEST_TYPE__SCOPING = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Assertion Consumer Service Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_REQUEST_TYPE__ASSERTION_CONSUMER_SERVICE_INDEX = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Assertion Consumer Service URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_REQUEST_TYPE__ASSERTION_CONSUMER_SERVICE_URL = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Attribute Consuming Service Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_REQUEST_TYPE__ATTRIBUTE_CONSUMING_SERVICE_INDEX = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Force Authn</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_REQUEST_TYPE__FORCE_AUTHN = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Is Passive</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_REQUEST_TYPE__IS_PASSIVE = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Protocol Binding</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_REQUEST_TYPE__PROTOCOL_BINDING = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Provider Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_REQUEST_TYPE__PROVIDER_NAME = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 11;

	/**
	 * The number of structural features of the '<em>Authn Request Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_REQUEST_TYPE_FEATURE_COUNT = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 12;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.AuthzDecisionQueryTypeImpl <em>Authz Decision Query Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.AuthzDecisionQueryTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getAuthzDecisionQueryType()
	 * @generated
	 */
	int AUTHZ_DECISION_QUERY_TYPE = 6;

	/**
	 * The feature id for the '<em><b>Issuer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHZ_DECISION_QUERY_TYPE__ISSUER = SUBJECT_QUERY_ABSTRACT_TYPE__ISSUER;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHZ_DECISION_QUERY_TYPE__EXTENSIONS = SUBJECT_QUERY_ABSTRACT_TYPE__EXTENSIONS;

	/**
	 * The feature id for the '<em><b>Consent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHZ_DECISION_QUERY_TYPE__CONSENT = SUBJECT_QUERY_ABSTRACT_TYPE__CONSENT;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHZ_DECISION_QUERY_TYPE__DESTINATION = SUBJECT_QUERY_ABSTRACT_TYPE__DESTINATION;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHZ_DECISION_QUERY_TYPE__ID = SUBJECT_QUERY_ABSTRACT_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Issue Instant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHZ_DECISION_QUERY_TYPE__ISSUE_INSTANT = SUBJECT_QUERY_ABSTRACT_TYPE__ISSUE_INSTANT;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHZ_DECISION_QUERY_TYPE__VERSION = SUBJECT_QUERY_ABSTRACT_TYPE__VERSION;

	/**
	 * The feature id for the '<em><b>Subject</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHZ_DECISION_QUERY_TYPE__SUBJECT = SUBJECT_QUERY_ABSTRACT_TYPE__SUBJECT;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHZ_DECISION_QUERY_TYPE__ACTION = SUBJECT_QUERY_ABSTRACT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Evidence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHZ_DECISION_QUERY_TYPE__EVIDENCE = SUBJECT_QUERY_ABSTRACT_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHZ_DECISION_QUERY_TYPE__RESOURCE = SUBJECT_QUERY_ABSTRACT_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Authz Decision Query Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHZ_DECISION_QUERY_TYPE_FEATURE_COUNT = SUBJECT_QUERY_ABSTRACT_TYPE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 7;

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
	 * The feature id for the '<em><b>Artifact</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ARTIFACT = 3;

	/**
	 * The feature id for the '<em><b>Artifact Resolve</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ARTIFACT_RESOLVE = 4;

	/**
	 * The feature id for the '<em><b>Artifact Response</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ARTIFACT_RESPONSE = 5;

	/**
	 * The feature id for the '<em><b>Assertion ID Request</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ASSERTION_ID_REQUEST = 6;

	/**
	 * The feature id for the '<em><b>Attribute Query</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ATTRIBUTE_QUERY = 7;

	/**
	 * The feature id for the '<em><b>Authn Query</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__AUTHN_QUERY = 8;

	/**
	 * The feature id for the '<em><b>Authn Request</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__AUTHN_REQUEST = 9;

	/**
	 * The feature id for the '<em><b>Authz Decision Query</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__AUTHZ_DECISION_QUERY = 10;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EXTENSIONS = 11;

	/**
	 * The feature id for the '<em><b>Get Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__GET_COMPLETE = 12;

	/**
	 * The feature id for the '<em><b>IDP Entry</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__IDP_ENTRY = 13;

	/**
	 * The feature id for the '<em><b>IDP List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__IDP_LIST = 14;

	/**
	 * The feature id for the '<em><b>Logout Request</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__LOGOUT_REQUEST = 15;

	/**
	 * The feature id for the '<em><b>Logout Response</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__LOGOUT_RESPONSE = 16;

	/**
	 * The feature id for the '<em><b>Manage Name ID Request</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MANAGE_NAME_ID_REQUEST = 17;

	/**
	 * The feature id for the '<em><b>Manage Name ID Response</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MANAGE_NAME_ID_RESPONSE = 18;

	/**
	 * The feature id for the '<em><b>Name ID Mapping Request</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__NAME_ID_MAPPING_REQUEST = 19;

	/**
	 * The feature id for the '<em><b>Name ID Mapping Response</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__NAME_ID_MAPPING_RESPONSE = 20;

	/**
	 * The feature id for the '<em><b>Name ID Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__NAME_ID_POLICY = 21;

	/**
	 * The feature id for the '<em><b>New ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__NEW_ID = 22;

	/**
	 * The feature id for the '<em><b>Requested Authn Context</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__REQUESTED_AUTHN_CONTEXT = 23;

	/**
	 * The feature id for the '<em><b>Requester ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__REQUESTER_ID = 24;

	/**
	 * The feature id for the '<em><b>Response</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__RESPONSE = 25;

	/**
	 * The feature id for the '<em><b>Scoping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SCOPING = 26;

	/**
	 * The feature id for the '<em><b>Session Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SESSION_INDEX = 27;

	/**
	 * The feature id for the '<em><b>Status</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__STATUS = 28;

	/**
	 * The feature id for the '<em><b>Status Code</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__STATUS_CODE = 29;

	/**
	 * The feature id for the '<em><b>Status Detail</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__STATUS_DETAIL = 30;

	/**
	 * The feature id for the '<em><b>Status Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__STATUS_MESSAGE = 31;

	/**
	 * The feature id for the '<em><b>Subject Query</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SUBJECT_QUERY = 32;

	/**
	 * The feature id for the '<em><b>Terminate</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TERMINATE = 33;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 34;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.ExtensionsTypeImpl <em>Extensions Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ExtensionsTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getExtensionsType()
	 * @generated
	 */
	int EXTENSIONS_TYPE = 8;

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
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.IDPEntryTypeImpl <em>IDP Entry Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.IDPEntryTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getIDPEntryType()
	 * @generated
	 */
	int IDP_ENTRY_TYPE = 9;

	/**
	 * The feature id for the '<em><b>Loc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDP_ENTRY_TYPE__LOC = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDP_ENTRY_TYPE__NAME = 1;

	/**
	 * The feature id for the '<em><b>Provider ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDP_ENTRY_TYPE__PROVIDER_ID = 2;

	/**
	 * The number of structural features of the '<em>IDP Entry Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDP_ENTRY_TYPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.IDPListTypeImpl <em>IDP List Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.IDPListTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getIDPListType()
	 * @generated
	 */
	int IDP_LIST_TYPE = 10;

	/**
	 * The feature id for the '<em><b>IDP Entry</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDP_LIST_TYPE__IDP_ENTRY = 0;

	/**
	 * The feature id for the '<em><b>Get Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDP_LIST_TYPE__GET_COMPLETE = 1;

	/**
	 * The number of structural features of the '<em>IDP List Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDP_LIST_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.LogoutRequestTypeImpl <em>Logout Request Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.LogoutRequestTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getLogoutRequestType()
	 * @generated
	 */
	int LOGOUT_REQUEST_TYPE = 11;

	/**
	 * The feature id for the '<em><b>Issuer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGOUT_REQUEST_TYPE__ISSUER = REQUEST_ABSTRACT_TYPE__ISSUER;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGOUT_REQUEST_TYPE__EXTENSIONS = REQUEST_ABSTRACT_TYPE__EXTENSIONS;

	/**
	 * The feature id for the '<em><b>Consent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGOUT_REQUEST_TYPE__CONSENT = REQUEST_ABSTRACT_TYPE__CONSENT;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGOUT_REQUEST_TYPE__DESTINATION = REQUEST_ABSTRACT_TYPE__DESTINATION;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGOUT_REQUEST_TYPE__ID = REQUEST_ABSTRACT_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Issue Instant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGOUT_REQUEST_TYPE__ISSUE_INSTANT = REQUEST_ABSTRACT_TYPE__ISSUE_INSTANT;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGOUT_REQUEST_TYPE__VERSION = REQUEST_ABSTRACT_TYPE__VERSION;

	/**
	 * The feature id for the '<em><b>Base ID</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGOUT_REQUEST_TYPE__BASE_ID = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name ID</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGOUT_REQUEST_TYPE__NAME_ID = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Session Index</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGOUT_REQUEST_TYPE__SESSION_INDEX = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Not On Or After</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGOUT_REQUEST_TYPE__NOT_ON_OR_AFTER = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Reason</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGOUT_REQUEST_TYPE__REASON = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Logout Request Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGOUT_REQUEST_TYPE_FEATURE_COUNT = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.ManageNameIDRequestTypeImpl <em>Manage Name ID Request Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ManageNameIDRequestTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getManageNameIDRequestType()
	 * @generated
	 */
	int MANAGE_NAME_ID_REQUEST_TYPE = 12;

	/**
	 * The feature id for the '<em><b>Issuer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGE_NAME_ID_REQUEST_TYPE__ISSUER = REQUEST_ABSTRACT_TYPE__ISSUER;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGE_NAME_ID_REQUEST_TYPE__EXTENSIONS = REQUEST_ABSTRACT_TYPE__EXTENSIONS;

	/**
	 * The feature id for the '<em><b>Consent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGE_NAME_ID_REQUEST_TYPE__CONSENT = REQUEST_ABSTRACT_TYPE__CONSENT;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGE_NAME_ID_REQUEST_TYPE__DESTINATION = REQUEST_ABSTRACT_TYPE__DESTINATION;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGE_NAME_ID_REQUEST_TYPE__ID = REQUEST_ABSTRACT_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Issue Instant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGE_NAME_ID_REQUEST_TYPE__ISSUE_INSTANT = REQUEST_ABSTRACT_TYPE__ISSUE_INSTANT;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGE_NAME_ID_REQUEST_TYPE__VERSION = REQUEST_ABSTRACT_TYPE__VERSION;

	/**
	 * The feature id for the '<em><b>Name ID</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGE_NAME_ID_REQUEST_TYPE__NAME_ID = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>New ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGE_NAME_ID_REQUEST_TYPE__NEW_ID = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Terminate</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGE_NAME_ID_REQUEST_TYPE__TERMINATE = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Manage Name ID Request Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGE_NAME_ID_REQUEST_TYPE_FEATURE_COUNT = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.NameIDMappingRequestTypeImpl <em>Name ID Mapping Request Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.NameIDMappingRequestTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getNameIDMappingRequestType()
	 * @generated
	 */
	int NAME_ID_MAPPING_REQUEST_TYPE = 13;

	/**
	 * The feature id for the '<em><b>Issuer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_MAPPING_REQUEST_TYPE__ISSUER = REQUEST_ABSTRACT_TYPE__ISSUER;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_MAPPING_REQUEST_TYPE__EXTENSIONS = REQUEST_ABSTRACT_TYPE__EXTENSIONS;

	/**
	 * The feature id for the '<em><b>Consent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_MAPPING_REQUEST_TYPE__CONSENT = REQUEST_ABSTRACT_TYPE__CONSENT;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_MAPPING_REQUEST_TYPE__DESTINATION = REQUEST_ABSTRACT_TYPE__DESTINATION;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_MAPPING_REQUEST_TYPE__ID = REQUEST_ABSTRACT_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Issue Instant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_MAPPING_REQUEST_TYPE__ISSUE_INSTANT = REQUEST_ABSTRACT_TYPE__ISSUE_INSTANT;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_MAPPING_REQUEST_TYPE__VERSION = REQUEST_ABSTRACT_TYPE__VERSION;

	/**
	 * The feature id for the '<em><b>Base ID</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_MAPPING_REQUEST_TYPE__BASE_ID = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name ID</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_MAPPING_REQUEST_TYPE__NAME_ID = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name ID Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_MAPPING_REQUEST_TYPE__NAME_ID_POLICY = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Name ID Mapping Request Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_MAPPING_REQUEST_TYPE_FEATURE_COUNT = REQUEST_ABSTRACT_TYPE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.NameIDMappingResponseTypeImpl <em>Name ID Mapping Response Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.NameIDMappingResponseTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getNameIDMappingResponseType()
	 * @generated
	 */
	int NAME_ID_MAPPING_RESPONSE_TYPE = 14;

	/**
	 * The feature id for the '<em><b>Issuer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_MAPPING_RESPONSE_TYPE__ISSUER = STATUS_RESPONSE_TYPE__ISSUER;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_MAPPING_RESPONSE_TYPE__EXTENSIONS = STATUS_RESPONSE_TYPE__EXTENSIONS;

	/**
	 * The feature id for the '<em><b>Status</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_MAPPING_RESPONSE_TYPE__STATUS = STATUS_RESPONSE_TYPE__STATUS;

	/**
	 * The feature id for the '<em><b>Consent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_MAPPING_RESPONSE_TYPE__CONSENT = STATUS_RESPONSE_TYPE__CONSENT;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_MAPPING_RESPONSE_TYPE__DESTINATION = STATUS_RESPONSE_TYPE__DESTINATION;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_MAPPING_RESPONSE_TYPE__ID = STATUS_RESPONSE_TYPE__ID;

	/**
	 * The feature id for the '<em><b>In Response To</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_MAPPING_RESPONSE_TYPE__IN_RESPONSE_TO = STATUS_RESPONSE_TYPE__IN_RESPONSE_TO;

	/**
	 * The feature id for the '<em><b>Issue Instant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_MAPPING_RESPONSE_TYPE__ISSUE_INSTANT = STATUS_RESPONSE_TYPE__ISSUE_INSTANT;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_MAPPING_RESPONSE_TYPE__VERSION = STATUS_RESPONSE_TYPE__VERSION;

	/**
	 * The feature id for the '<em><b>Name ID</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_MAPPING_RESPONSE_TYPE__NAME_ID = STATUS_RESPONSE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Name ID Mapping Response Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_MAPPING_RESPONSE_TYPE_FEATURE_COUNT = STATUS_RESPONSE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.NameIDPolicyTypeImpl <em>Name ID Policy Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.NameIDPolicyTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getNameIDPolicyType()
	 * @generated
	 */
	int NAME_ID_POLICY_TYPE = 15;

	/**
	 * The feature id for the '<em><b>Allow Create</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_POLICY_TYPE__ALLOW_CREATE = 0;

	/**
	 * The feature id for the '<em><b>Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_POLICY_TYPE__FORMAT = 1;

	/**
	 * The feature id for the '<em><b>SP Name Qualifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_POLICY_TYPE__SP_NAME_QUALIFIER = 2;

	/**
	 * The number of structural features of the '<em>Name ID Policy Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_POLICY_TYPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.RequestedAuthnContextTypeImpl <em>Requested Authn Context Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.RequestedAuthnContextTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getRequestedAuthnContextType()
	 * @generated
	 */
	int REQUESTED_AUTHN_CONTEXT_TYPE = 17;

	/**
	 * The feature id for the '<em><b>Authn Context Class Ref</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUESTED_AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_CLASS_REF = 0;

	/**
	 * The feature id for the '<em><b>Authn Context Decl Ref</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUESTED_AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL_REF = 1;

	/**
	 * The feature id for the '<em><b>Comparison</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUESTED_AUTHN_CONTEXT_TYPE__COMPARISON = 2;

	/**
	 * The number of structural features of the '<em>Requested Authn Context Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUESTED_AUTHN_CONTEXT_TYPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.ResponseTypeImpl <em>Response Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ResponseTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getResponseType()
	 * @generated
	 */
	int RESPONSE_TYPE = 18;

	/**
	 * The feature id for the '<em><b>Issuer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE_TYPE__ISSUER = STATUS_RESPONSE_TYPE__ISSUER;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE_TYPE__EXTENSIONS = STATUS_RESPONSE_TYPE__EXTENSIONS;

	/**
	 * The feature id for the '<em><b>Status</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE_TYPE__STATUS = STATUS_RESPONSE_TYPE__STATUS;

	/**
	 * The feature id for the '<em><b>Consent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE_TYPE__CONSENT = STATUS_RESPONSE_TYPE__CONSENT;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE_TYPE__DESTINATION = STATUS_RESPONSE_TYPE__DESTINATION;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE_TYPE__ID = STATUS_RESPONSE_TYPE__ID;

	/**
	 * The feature id for the '<em><b>In Response To</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE_TYPE__IN_RESPONSE_TO = STATUS_RESPONSE_TYPE__IN_RESPONSE_TO;

	/**
	 * The feature id for the '<em><b>Issue Instant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE_TYPE__ISSUE_INSTANT = STATUS_RESPONSE_TYPE__ISSUE_INSTANT;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE_TYPE__VERSION = STATUS_RESPONSE_TYPE__VERSION;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE_TYPE__GROUP = STATUS_RESPONSE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Assertion</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE_TYPE__ASSERTION = STATUS_RESPONSE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Response Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE_TYPE_FEATURE_COUNT = STATUS_RESPONSE_TYPE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.ScopingTypeImpl <em>Scoping Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ScopingTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getScopingType()
	 * @generated
	 */
	int SCOPING_TYPE = 19;

	/**
	 * The feature id for the '<em><b>IDP List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCOPING_TYPE__IDP_LIST = 0;

	/**
	 * The feature id for the '<em><b>Requester ID</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCOPING_TYPE__REQUESTER_ID = 1;

	/**
	 * The feature id for the '<em><b>Proxy Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCOPING_TYPE__PROXY_COUNT = 2;

	/**
	 * The number of structural features of the '<em>Scoping Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCOPING_TYPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.StatusCodeTypeImpl <em>Status Code Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.StatusCodeTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getStatusCodeType()
	 * @generated
	 */
	int STATUS_CODE_TYPE = 20;

	/**
	 * The feature id for the '<em><b>Status Code</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_CODE_TYPE__STATUS_CODE = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_CODE_TYPE__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Status Code Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_CODE_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.StatusDetailTypeImpl <em>Status Detail Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.StatusDetailTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getStatusDetailType()
	 * @generated
	 */
	int STATUS_DETAIL_TYPE = 21;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_DETAIL_TYPE__ANY = 0;

	/**
	 * The number of structural features of the '<em>Status Detail Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_DETAIL_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.StatusTypeImpl <em>Status Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.StatusTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getStatusType()
	 * @generated
	 */
	int STATUS_TYPE = 23;

	/**
	 * The feature id for the '<em><b>Status Code</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_TYPE__STATUS_CODE = 0;

	/**
	 * The feature id for the '<em><b>Status Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_TYPE__STATUS_MESSAGE = 1;

	/**
	 * The feature id for the '<em><b>Status Detail</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_TYPE__STATUS_DETAIL = 2;

	/**
	 * The number of structural features of the '<em>Status Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATUS_TYPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.TerminateTypeImpl <em>Terminate Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.TerminateTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getTerminateType()
	 * @generated
	 */
	int TERMINATE_TYPE = 25;

	/**
	 * The number of structural features of the '<em>Terminate Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINATE_TYPE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnContextComparisonType <em>Authn Context Comparison Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthnContextComparisonType
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getAuthnContextComparisonType()
	 * @generated
	 */
	int AUTHN_CONTEXT_COMPARISON_TYPE = 26;

	/**
	 * The meta object id for the '<em>Authn Context Comparison Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthnContextComparisonType
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getAuthnContextComparisonTypeObject()
	 * @generated
	 */
	int AUTHN_CONTEXT_COMPARISON_TYPE_OBJECT = 27;


	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ArtifactResolveType <em>Artifact Resolve Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Artifact Resolve Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ArtifactResolveType
	 * @generated
	 */
	EClass getArtifactResolveType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ArtifactResolveType#getArtifact <em>Artifact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Artifact</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ArtifactResolveType#getArtifact()
	 * @see #getArtifactResolveType()
	 * @generated
	 */
	EAttribute getArtifactResolveType_Artifact();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ArtifactResponseType <em>Artifact Response Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Artifact Response Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ArtifactResponseType
	 * @generated
	 */
	EClass getArtifactResponseType();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ArtifactResponseType#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ArtifactResponseType#getAny()
	 * @see #getArtifactResponseType()
	 * @generated
	 */
	EAttribute getArtifactResponseType_Any();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AssertionIDRequestType <em>Assertion ID Request Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Assertion ID Request Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AssertionIDRequestType
	 * @generated
	 */
	EClass getAssertionIDRequestType();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AssertionIDRequestType#getAssertionIDRef <em>Assertion ID Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Assertion ID Ref</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AssertionIDRequestType#getAssertionIDRef()
	 * @see #getAssertionIDRequestType()
	 * @generated
	 */
	EAttribute getAssertionIDRequestType_AssertionIDRef();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AttributeQueryType <em>Attribute Query Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Query Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AttributeQueryType
	 * @generated
	 */
	EClass getAttributeQueryType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AttributeQueryType#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AttributeQueryType#getAttribute()
	 * @see #getAttributeQueryType()
	 * @generated
	 */
	EReference getAttributeQueryType_Attribute();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnQueryType <em>Authn Query Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Authn Query Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthnQueryType
	 * @generated
	 */
	EClass getAuthnQueryType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnQueryType#getRequestedAuthnContext <em>Requested Authn Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Requested Authn Context</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthnQueryType#getRequestedAuthnContext()
	 * @see #getAuthnQueryType()
	 * @generated
	 */
	EReference getAuthnQueryType_RequestedAuthnContext();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnQueryType#getSessionIndex <em>Session Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Session Index</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthnQueryType#getSessionIndex()
	 * @see #getAuthnQueryType()
	 * @generated
	 */
	EAttribute getAuthnQueryType_SessionIndex();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType <em>Authn Request Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Authn Request Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType
	 * @generated
	 */
	EClass getAuthnRequestType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getSubject <em>Subject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Subject</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getSubject()
	 * @see #getAuthnRequestType()
	 * @generated
	 */
	EReference getAuthnRequestType_Subject();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getNameIDPolicy <em>Name ID Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Name ID Policy</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getNameIDPolicy()
	 * @see #getAuthnRequestType()
	 * @generated
	 */
	EReference getAuthnRequestType_NameIDPolicy();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getConditions <em>Conditions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Conditions</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getConditions()
	 * @see #getAuthnRequestType()
	 * @generated
	 */
	EReference getAuthnRequestType_Conditions();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getRequestedAuthnContext <em>Requested Authn Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Requested Authn Context</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getRequestedAuthnContext()
	 * @see #getAuthnRequestType()
	 * @generated
	 */
	EReference getAuthnRequestType_RequestedAuthnContext();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getScoping <em>Scoping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Scoping</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getScoping()
	 * @see #getAuthnRequestType()
	 * @generated
	 */
	EReference getAuthnRequestType_Scoping();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getAssertionConsumerServiceIndex <em>Assertion Consumer Service Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Assertion Consumer Service Index</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getAssertionConsumerServiceIndex()
	 * @see #getAuthnRequestType()
	 * @generated
	 */
	EAttribute getAuthnRequestType_AssertionConsumerServiceIndex();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getAssertionConsumerServiceURL <em>Assertion Consumer Service URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Assertion Consumer Service URL</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getAssertionConsumerServiceURL()
	 * @see #getAuthnRequestType()
	 * @generated
	 */
	EAttribute getAuthnRequestType_AssertionConsumerServiceURL();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getAttributeConsumingServiceIndex <em>Attribute Consuming Service Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attribute Consuming Service Index</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getAttributeConsumingServiceIndex()
	 * @see #getAuthnRequestType()
	 * @generated
	 */
	EAttribute getAuthnRequestType_AttributeConsumingServiceIndex();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#isForceAuthn <em>Force Authn</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Force Authn</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#isForceAuthn()
	 * @see #getAuthnRequestType()
	 * @generated
	 */
	EAttribute getAuthnRequestType_ForceAuthn();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#isIsPassive <em>Is Passive</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Passive</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#isIsPassive()
	 * @see #getAuthnRequestType()
	 * @generated
	 */
	EAttribute getAuthnRequestType_IsPassive();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getProtocolBinding <em>Protocol Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Protocol Binding</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getProtocolBinding()
	 * @see #getAuthnRequestType()
	 * @generated
	 */
	EAttribute getAuthnRequestType_ProtocolBinding();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getProviderName <em>Provider Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Provider Name</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getProviderName()
	 * @see #getAuthnRequestType()
	 * @generated
	 */
	EAttribute getAuthnRequestType_ProviderName();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthzDecisionQueryType <em>Authz Decision Query Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Authz Decision Query Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthzDecisionQueryType
	 * @generated
	 */
	EClass getAuthzDecisionQueryType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthzDecisionQueryType#getAction <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Action</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthzDecisionQueryType#getAction()
	 * @see #getAuthzDecisionQueryType()
	 * @generated
	 */
	EReference getAuthzDecisionQueryType_Action();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthzDecisionQueryType#getEvidence <em>Evidence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Evidence</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthzDecisionQueryType#getEvidence()
	 * @see #getAuthzDecisionQueryType()
	 * @generated
	 */
	EReference getAuthzDecisionQueryType_Evidence();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthzDecisionQueryType#getResource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Resource</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthzDecisionQueryType#getResource()
	 * @see #getAuthzDecisionQueryType()
	 * @generated
	 */
	EAttribute getAuthzDecisionQueryType_Resource();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getArtifact <em>Artifact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Artifact</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getArtifact()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Artifact();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getArtifactResolve <em>Artifact Resolve</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Artifact Resolve</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getArtifactResolve()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ArtifactResolve();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getArtifactResponse <em>Artifact Response</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Artifact Response</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getArtifactResponse()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ArtifactResponse();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getAssertionIDRequest <em>Assertion ID Request</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Assertion ID Request</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getAssertionIDRequest()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_AssertionIDRequest();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getAttributeQuery <em>Attribute Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Attribute Query</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getAttributeQuery()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_AttributeQuery();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getAuthnQuery <em>Authn Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Authn Query</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getAuthnQuery()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_AuthnQuery();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getAuthnRequest <em>Authn Request</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Authn Request</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getAuthnRequest()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_AuthnRequest();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getAuthzDecisionQuery <em>Authz Decision Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Authz Decision Query</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getAuthzDecisionQuery()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_AuthzDecisionQuery();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getExtensions <em>Extensions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Extensions</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getExtensions()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Extensions();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getGetComplete <em>Get Complete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Get Complete</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getGetComplete()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_GetComplete();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getIDPEntry <em>IDP Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>IDP Entry</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getIDPEntry()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_IDPEntry();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getIDPList <em>IDP List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>IDP List</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getIDPList()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_IDPList();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getLogoutRequest <em>Logout Request</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Logout Request</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getLogoutRequest()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_LogoutRequest();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getLogoutResponse <em>Logout Response</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Logout Response</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getLogoutResponse()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_LogoutResponse();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getManageNameIDRequest <em>Manage Name ID Request</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Manage Name ID Request</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getManageNameIDRequest()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ManageNameIDRequest();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getManageNameIDResponse <em>Manage Name ID Response</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Manage Name ID Response</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getManageNameIDResponse()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ManageNameIDResponse();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getNameIDMappingRequest <em>Name ID Mapping Request</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Name ID Mapping Request</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getNameIDMappingRequest()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_NameIDMappingRequest();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getNameIDMappingResponse <em>Name ID Mapping Response</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Name ID Mapping Response</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getNameIDMappingResponse()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_NameIDMappingResponse();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getNameIDPolicy <em>Name ID Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Name ID Policy</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getNameIDPolicy()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_NameIDPolicy();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getNewID <em>New ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>New ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getNewID()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_NewID();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getRequestedAuthnContext <em>Requested Authn Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Requested Authn Context</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getRequestedAuthnContext()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_RequestedAuthnContext();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getRequesterID <em>Requester ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Requester ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getRequesterID()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_RequesterID();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getResponse <em>Response</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Response</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getResponse()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Response();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getScoping <em>Scoping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Scoping</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getScoping()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Scoping();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getSessionIndex <em>Session Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Session Index</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getSessionIndex()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_SessionIndex();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getStatus <em>Status</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Status</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getStatus()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Status();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getStatusCode <em>Status Code</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Status Code</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getStatusCode()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_StatusCode();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getStatusDetail <em>Status Detail</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Status Detail</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getStatusDetail()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_StatusDetail();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getStatusMessage <em>Status Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Status Message</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getStatusMessage()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_StatusMessage();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getSubjectQuery <em>Subject Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Subject Query</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getSubjectQuery()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_SubjectQuery();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getTerminate <em>Terminate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Terminate</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getTerminate()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Terminate();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ExtensionsType <em>Extensions Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Extensions Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ExtensionsType
	 * @generated
	 */
	EClass getExtensionsType();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ExtensionsType#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ExtensionsType#getAny()
	 * @see #getExtensionsType()
	 * @generated
	 */
	EAttribute getExtensionsType_Any();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.IDPEntryType <em>IDP Entry Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IDP Entry Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.IDPEntryType
	 * @generated
	 */
	EClass getIDPEntryType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.IDPEntryType#getLoc <em>Loc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Loc</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.IDPEntryType#getLoc()
	 * @see #getIDPEntryType()
	 * @generated
	 */
	EAttribute getIDPEntryType_Loc();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.IDPEntryType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.IDPEntryType#getName()
	 * @see #getIDPEntryType()
	 * @generated
	 */
	EAttribute getIDPEntryType_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.IDPEntryType#getProviderID <em>Provider ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Provider ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.IDPEntryType#getProviderID()
	 * @see #getIDPEntryType()
	 * @generated
	 */
	EAttribute getIDPEntryType_ProviderID();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.IDPListType <em>IDP List Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IDP List Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.IDPListType
	 * @generated
	 */
	EClass getIDPListType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.protocol.IDPListType#getIDPEntry <em>IDP Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>IDP Entry</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.IDPListType#getIDPEntry()
	 * @see #getIDPListType()
	 * @generated
	 */
	EReference getIDPListType_IDPEntry();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.IDPListType#getGetComplete <em>Get Complete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Get Complete</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.IDPListType#getGetComplete()
	 * @see #getIDPListType()
	 * @generated
	 */
	EAttribute getIDPListType_GetComplete();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.LogoutRequestType <em>Logout Request Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Logout Request Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.LogoutRequestType
	 * @generated
	 */
	EClass getLogoutRequestType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.LogoutRequestType#getBaseID <em>Base ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Base ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.LogoutRequestType#getBaseID()
	 * @see #getLogoutRequestType()
	 * @generated
	 */
	EReference getLogoutRequestType_BaseID();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.LogoutRequestType#getNameID <em>Name ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Name ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.LogoutRequestType#getNameID()
	 * @see #getLogoutRequestType()
	 * @generated
	 */
	EReference getLogoutRequestType_NameID();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.protocol.LogoutRequestType#getSessionIndex <em>Session Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Session Index</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.LogoutRequestType#getSessionIndex()
	 * @see #getLogoutRequestType()
	 * @generated
	 */
	EAttribute getLogoutRequestType_SessionIndex();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.LogoutRequestType#getNotOnOrAfter <em>Not On Or After</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Not On Or After</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.LogoutRequestType#getNotOnOrAfter()
	 * @see #getLogoutRequestType()
	 * @generated
	 */
	EAttribute getLogoutRequestType_NotOnOrAfter();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.LogoutRequestType#getReason <em>Reason</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Reason</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.LogoutRequestType#getReason()
	 * @see #getLogoutRequestType()
	 * @generated
	 */
	EAttribute getLogoutRequestType_Reason();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ManageNameIDRequestType <em>Manage Name ID Request Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Manage Name ID Request Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ManageNameIDRequestType
	 * @generated
	 */
	EClass getManageNameIDRequestType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ManageNameIDRequestType#getNameID <em>Name ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Name ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ManageNameIDRequestType#getNameID()
	 * @see #getManageNameIDRequestType()
	 * @generated
	 */
	EReference getManageNameIDRequestType_NameID();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ManageNameIDRequestType#getNewID <em>New ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>New ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ManageNameIDRequestType#getNewID()
	 * @see #getManageNameIDRequestType()
	 * @generated
	 */
	EAttribute getManageNameIDRequestType_NewID();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ManageNameIDRequestType#getTerminate <em>Terminate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Terminate</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ManageNameIDRequestType#getTerminate()
	 * @see #getManageNameIDRequestType()
	 * @generated
	 */
	EReference getManageNameIDRequestType_Terminate();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingRequestType <em>Name ID Mapping Request Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Name ID Mapping Request Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingRequestType
	 * @generated
	 */
	EClass getNameIDMappingRequestType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingRequestType#getBaseID <em>Base ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Base ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingRequestType#getBaseID()
	 * @see #getNameIDMappingRequestType()
	 * @generated
	 */
	EReference getNameIDMappingRequestType_BaseID();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingRequestType#getNameID <em>Name ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Name ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingRequestType#getNameID()
	 * @see #getNameIDMappingRequestType()
	 * @generated
	 */
	EReference getNameIDMappingRequestType_NameID();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingRequestType#getNameIDPolicy <em>Name ID Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Name ID Policy</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingRequestType#getNameIDPolicy()
	 * @see #getNameIDMappingRequestType()
	 * @generated
	 */
	EReference getNameIDMappingRequestType_NameIDPolicy();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingResponseType <em>Name ID Mapping Response Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Name ID Mapping Response Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingResponseType
	 * @generated
	 */
	EClass getNameIDMappingResponseType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingResponseType#getNameID <em>Name ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Name ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingResponseType#getNameID()
	 * @see #getNameIDMappingResponseType()
	 * @generated
	 */
	EReference getNameIDMappingResponseType_NameID();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDPolicyType <em>Name ID Policy Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Name ID Policy Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.NameIDPolicyType
	 * @generated
	 */
	EClass getNameIDPolicyType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDPolicyType#isAllowCreate <em>Allow Create</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Allow Create</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.NameIDPolicyType#isAllowCreate()
	 * @see #getNameIDPolicyType()
	 * @generated
	 */
	EAttribute getNameIDPolicyType_AllowCreate();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDPolicyType#getFormat <em>Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Format</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.NameIDPolicyType#getFormat()
	 * @see #getNameIDPolicyType()
	 * @generated
	 */
	EAttribute getNameIDPolicyType_Format();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDPolicyType#getSPNameQualifier <em>SP Name Qualifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>SP Name Qualifier</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.NameIDPolicyType#getSPNameQualifier()
	 * @see #getNameIDPolicyType()
	 * @generated
	 */
	EAttribute getNameIDPolicyType_SPNameQualifier();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType <em>Request Abstract Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Request Abstract Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType
	 * @generated
	 */
	EClass getRequestAbstractType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getIssuer <em>Issuer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Issuer</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getIssuer()
	 * @see #getRequestAbstractType()
	 * @generated
	 */
	EReference getRequestAbstractType_Issuer();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getExtensions <em>Extensions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Extensions</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getExtensions()
	 * @see #getRequestAbstractType()
	 * @generated
	 */
	EReference getRequestAbstractType_Extensions();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getConsent <em>Consent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Consent</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getConsent()
	 * @see #getRequestAbstractType()
	 * @generated
	 */
	EAttribute getRequestAbstractType_Consent();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getDestination <em>Destination</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Destination</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getDestination()
	 * @see #getRequestAbstractType()
	 * @generated
	 */
	EAttribute getRequestAbstractType_Destination();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getID <em>ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getID()
	 * @see #getRequestAbstractType()
	 * @generated
	 */
	EAttribute getRequestAbstractType_ID();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getIssueInstant <em>Issue Instant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Issue Instant</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getIssueInstant()
	 * @see #getRequestAbstractType()
	 * @generated
	 */
	EAttribute getRequestAbstractType_IssueInstant();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getVersion()
	 * @see #getRequestAbstractType()
	 * @generated
	 */
	EAttribute getRequestAbstractType_Version();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestedAuthnContextType <em>Requested Authn Context Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Requested Authn Context Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.RequestedAuthnContextType
	 * @generated
	 */
	EClass getRequestedAuthnContextType();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestedAuthnContextType#getAuthnContextClassRef <em>Authn Context Class Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Authn Context Class Ref</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.RequestedAuthnContextType#getAuthnContextClassRef()
	 * @see #getRequestedAuthnContextType()
	 * @generated
	 */
	EAttribute getRequestedAuthnContextType_AuthnContextClassRef();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestedAuthnContextType#getAuthnContextDeclRef <em>Authn Context Decl Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Authn Context Decl Ref</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.RequestedAuthnContextType#getAuthnContextDeclRef()
	 * @see #getRequestedAuthnContextType()
	 * @generated
	 */
	EAttribute getRequestedAuthnContextType_AuthnContextDeclRef();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestedAuthnContextType#getComparison <em>Comparison</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comparison</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.RequestedAuthnContextType#getComparison()
	 * @see #getRequestedAuthnContextType()
	 * @generated
	 */
	EAttribute getRequestedAuthnContextType_Comparison();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ResponseType <em>Response Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Response Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ResponseType
	 * @generated
	 */
	EClass getResponseType();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ResponseType#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ResponseType#getGroup()
	 * @see #getResponseType()
	 * @generated
	 */
	EAttribute getResponseType_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ResponseType#getAssertion <em>Assertion</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Assertion</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ResponseType#getAssertion()
	 * @see #getResponseType()
	 * @generated
	 */
	EReference getResponseType_Assertion();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ScopingType <em>Scoping Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Scoping Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ScopingType
	 * @generated
	 */
	EClass getScopingType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ScopingType#getIDPList <em>IDP List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>IDP List</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ScopingType#getIDPList()
	 * @see #getScopingType()
	 * @generated
	 */
	EReference getScopingType_IDPList();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ScopingType#getRequesterID <em>Requester ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Requester ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ScopingType#getRequesterID()
	 * @see #getScopingType()
	 * @generated
	 */
	EAttribute getScopingType_RequesterID();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ScopingType#getProxyCount <em>Proxy Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Proxy Count</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ScopingType#getProxyCount()
	 * @see #getScopingType()
	 * @generated
	 */
	EAttribute getScopingType_ProxyCount();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusCodeType <em>Status Code Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Status Code Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.StatusCodeType
	 * @generated
	 */
	EClass getStatusCodeType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusCodeType#getStatusCode <em>Status Code</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Status Code</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.StatusCodeType#getStatusCode()
	 * @see #getStatusCodeType()
	 * @generated
	 */
	EReference getStatusCodeType_StatusCode();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusCodeType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.StatusCodeType#getValue()
	 * @see #getStatusCodeType()
	 * @generated
	 */
	EAttribute getStatusCodeType_Value();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusDetailType <em>Status Detail Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Status Detail Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.StatusDetailType
	 * @generated
	 */
	EClass getStatusDetailType();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusDetailType#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.StatusDetailType#getAny()
	 * @see #getStatusDetailType()
	 * @generated
	 */
	EAttribute getStatusDetailType_Any();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusResponseType <em>Status Response Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Status Response Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.StatusResponseType
	 * @generated
	 */
	EClass getStatusResponseType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusResponseType#getIssuer <em>Issuer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Issuer</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.StatusResponseType#getIssuer()
	 * @see #getStatusResponseType()
	 * @generated
	 */
	EReference getStatusResponseType_Issuer();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusResponseType#getExtensions <em>Extensions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Extensions</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.StatusResponseType#getExtensions()
	 * @see #getStatusResponseType()
	 * @generated
	 */
	EReference getStatusResponseType_Extensions();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusResponseType#getStatus <em>Status</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Status</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.StatusResponseType#getStatus()
	 * @see #getStatusResponseType()
	 * @generated
	 */
	EReference getStatusResponseType_Status();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusResponseType#getConsent <em>Consent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Consent</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.StatusResponseType#getConsent()
	 * @see #getStatusResponseType()
	 * @generated
	 */
	EAttribute getStatusResponseType_Consent();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusResponseType#getDestination <em>Destination</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Destination</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.StatusResponseType#getDestination()
	 * @see #getStatusResponseType()
	 * @generated
	 */
	EAttribute getStatusResponseType_Destination();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusResponseType#getID <em>ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.StatusResponseType#getID()
	 * @see #getStatusResponseType()
	 * @generated
	 */
	EAttribute getStatusResponseType_ID();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusResponseType#getInResponseTo <em>In Response To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>In Response To</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.StatusResponseType#getInResponseTo()
	 * @see #getStatusResponseType()
	 * @generated
	 */
	EAttribute getStatusResponseType_InResponseTo();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusResponseType#getIssueInstant <em>Issue Instant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Issue Instant</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.StatusResponseType#getIssueInstant()
	 * @see #getStatusResponseType()
	 * @generated
	 */
	EAttribute getStatusResponseType_IssueInstant();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusResponseType#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.StatusResponseType#getVersion()
	 * @see #getStatusResponseType()
	 * @generated
	 */
	EAttribute getStatusResponseType_Version();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusType <em>Status Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Status Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.StatusType
	 * @generated
	 */
	EClass getStatusType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusType#getStatusCode <em>Status Code</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Status Code</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.StatusType#getStatusCode()
	 * @see #getStatusType()
	 * @generated
	 */
	EReference getStatusType_StatusCode();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusType#getStatusMessage <em>Status Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Status Message</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.StatusType#getStatusMessage()
	 * @see #getStatusType()
	 * @generated
	 */
	EAttribute getStatusType_StatusMessage();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusType#getStatusDetail <em>Status Detail</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Status Detail</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.StatusType#getStatusDetail()
	 * @see #getStatusType()
	 * @generated
	 */
	EReference getStatusType_StatusDetail();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.SubjectQueryAbstractType <em>Subject Query Abstract Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Subject Query Abstract Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.SubjectQueryAbstractType
	 * @generated
	 */
	EClass getSubjectQueryAbstractType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.protocol.SubjectQueryAbstractType#getSubject <em>Subject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Subject</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.SubjectQueryAbstractType#getSubject()
	 * @see #getSubjectQueryAbstractType()
	 * @generated
	 */
	EReference getSubjectQueryAbstractType_Subject();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.TerminateType <em>Terminate Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Terminate Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.TerminateType
	 * @generated
	 */
	EClass getTerminateType();

	/**
	 * Returns the meta object for enum '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnContextComparisonType <em>Authn Context Comparison Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Authn Context Comparison Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthnContextComparisonType
	 * @generated
	 */
	EEnum getAuthnContextComparisonType();

	/**
	 * Returns the meta object for data type '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnContextComparisonType <em>Authn Context Comparison Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Authn Context Comparison Type Object</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthnContextComparisonType
	 * @model instanceClass="com.tibco.be.baas.security.authn.saml.model.protocol.AuthnContextComparisonType"
	 *        extendedMetaData="name='AuthnContextComparisonType:Object' baseType='AuthnContextComparisonType'"
	 * @generated
	 */
	EDataType getAuthnContextComparisonTypeObject();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ProtocolFactory getProtocolFactory();

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
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.ArtifactResolveTypeImpl <em>Artifact Resolve Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ArtifactResolveTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getArtifactResolveType()
		 * @generated
		 */
		EClass ARTIFACT_RESOLVE_TYPE = eINSTANCE.getArtifactResolveType();

		/**
		 * The meta object literal for the '<em><b>Artifact</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARTIFACT_RESOLVE_TYPE__ARTIFACT = eINSTANCE.getArtifactResolveType_Artifact();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.ArtifactResponseTypeImpl <em>Artifact Response Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ArtifactResponseTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getArtifactResponseType()
		 * @generated
		 */
		EClass ARTIFACT_RESPONSE_TYPE = eINSTANCE.getArtifactResponseType();

		/**
		 * The meta object literal for the '<em><b>Any</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARTIFACT_RESPONSE_TYPE__ANY = eINSTANCE.getArtifactResponseType_Any();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.AssertionIDRequestTypeImpl <em>Assertion ID Request Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.AssertionIDRequestTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getAssertionIDRequestType()
		 * @generated
		 */
		EClass ASSERTION_ID_REQUEST_TYPE = eINSTANCE.getAssertionIDRequestType();

		/**
		 * The meta object literal for the '<em><b>Assertion ID Ref</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASSERTION_ID_REQUEST_TYPE__ASSERTION_ID_REF = eINSTANCE.getAssertionIDRequestType_AssertionIDRef();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.AttributeQueryTypeImpl <em>Attribute Query Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.AttributeQueryTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getAttributeQueryType()
		 * @generated
		 */
		EClass ATTRIBUTE_QUERY_TYPE = eINSTANCE.getAttributeQueryType();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_QUERY_TYPE__ATTRIBUTE = eINSTANCE.getAttributeQueryType_Attribute();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.AuthnQueryTypeImpl <em>Authn Query Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.AuthnQueryTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getAuthnQueryType()
		 * @generated
		 */
		EClass AUTHN_QUERY_TYPE = eINSTANCE.getAuthnQueryType();

		/**
		 * The meta object literal for the '<em><b>Requested Authn Context</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTHN_QUERY_TYPE__REQUESTED_AUTHN_CONTEXT = eINSTANCE.getAuthnQueryType_RequestedAuthnContext();

		/**
		 * The meta object literal for the '<em><b>Session Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHN_QUERY_TYPE__SESSION_INDEX = eINSTANCE.getAuthnQueryType_SessionIndex();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.AuthnRequestTypeImpl <em>Authn Request Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.AuthnRequestTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getAuthnRequestType()
		 * @generated
		 */
		EClass AUTHN_REQUEST_TYPE = eINSTANCE.getAuthnRequestType();

		/**
		 * The meta object literal for the '<em><b>Subject</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTHN_REQUEST_TYPE__SUBJECT = eINSTANCE.getAuthnRequestType_Subject();

		/**
		 * The meta object literal for the '<em><b>Name ID Policy</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTHN_REQUEST_TYPE__NAME_ID_POLICY = eINSTANCE.getAuthnRequestType_NameIDPolicy();

		/**
		 * The meta object literal for the '<em><b>Conditions</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTHN_REQUEST_TYPE__CONDITIONS = eINSTANCE.getAuthnRequestType_Conditions();

		/**
		 * The meta object literal for the '<em><b>Requested Authn Context</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTHN_REQUEST_TYPE__REQUESTED_AUTHN_CONTEXT = eINSTANCE.getAuthnRequestType_RequestedAuthnContext();

		/**
		 * The meta object literal for the '<em><b>Scoping</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTHN_REQUEST_TYPE__SCOPING = eINSTANCE.getAuthnRequestType_Scoping();

		/**
		 * The meta object literal for the '<em><b>Assertion Consumer Service Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHN_REQUEST_TYPE__ASSERTION_CONSUMER_SERVICE_INDEX = eINSTANCE.getAuthnRequestType_AssertionConsumerServiceIndex();

		/**
		 * The meta object literal for the '<em><b>Assertion Consumer Service URL</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHN_REQUEST_TYPE__ASSERTION_CONSUMER_SERVICE_URL = eINSTANCE.getAuthnRequestType_AssertionConsumerServiceURL();

		/**
		 * The meta object literal for the '<em><b>Attribute Consuming Service Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHN_REQUEST_TYPE__ATTRIBUTE_CONSUMING_SERVICE_INDEX = eINSTANCE.getAuthnRequestType_AttributeConsumingServiceIndex();

		/**
		 * The meta object literal for the '<em><b>Force Authn</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHN_REQUEST_TYPE__FORCE_AUTHN = eINSTANCE.getAuthnRequestType_ForceAuthn();

		/**
		 * The meta object literal for the '<em><b>Is Passive</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHN_REQUEST_TYPE__IS_PASSIVE = eINSTANCE.getAuthnRequestType_IsPassive();

		/**
		 * The meta object literal for the '<em><b>Protocol Binding</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHN_REQUEST_TYPE__PROTOCOL_BINDING = eINSTANCE.getAuthnRequestType_ProtocolBinding();

		/**
		 * The meta object literal for the '<em><b>Provider Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHN_REQUEST_TYPE__PROVIDER_NAME = eINSTANCE.getAuthnRequestType_ProviderName();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.AuthzDecisionQueryTypeImpl <em>Authz Decision Query Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.AuthzDecisionQueryTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getAuthzDecisionQueryType()
		 * @generated
		 */
		EClass AUTHZ_DECISION_QUERY_TYPE = eINSTANCE.getAuthzDecisionQueryType();

		/**
		 * The meta object literal for the '<em><b>Action</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTHZ_DECISION_QUERY_TYPE__ACTION = eINSTANCE.getAuthzDecisionQueryType_Action();

		/**
		 * The meta object literal for the '<em><b>Evidence</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTHZ_DECISION_QUERY_TYPE__EVIDENCE = eINSTANCE.getAuthzDecisionQueryType_Evidence();

		/**
		 * The meta object literal for the '<em><b>Resource</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHZ_DECISION_QUERY_TYPE__RESOURCE = eINSTANCE.getAuthzDecisionQueryType_Resource();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getDocumentRoot()
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
		 * The meta object literal for the '<em><b>Artifact</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__ARTIFACT = eINSTANCE.getDocumentRoot_Artifact();

		/**
		 * The meta object literal for the '<em><b>Artifact Resolve</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ARTIFACT_RESOLVE = eINSTANCE.getDocumentRoot_ArtifactResolve();

		/**
		 * The meta object literal for the '<em><b>Artifact Response</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ARTIFACT_RESPONSE = eINSTANCE.getDocumentRoot_ArtifactResponse();

		/**
		 * The meta object literal for the '<em><b>Assertion ID Request</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ASSERTION_ID_REQUEST = eINSTANCE.getDocumentRoot_AssertionIDRequest();

		/**
		 * The meta object literal for the '<em><b>Attribute Query</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ATTRIBUTE_QUERY = eINSTANCE.getDocumentRoot_AttributeQuery();

		/**
		 * The meta object literal for the '<em><b>Authn Query</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__AUTHN_QUERY = eINSTANCE.getDocumentRoot_AuthnQuery();

		/**
		 * The meta object literal for the '<em><b>Authn Request</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__AUTHN_REQUEST = eINSTANCE.getDocumentRoot_AuthnRequest();

		/**
		 * The meta object literal for the '<em><b>Authz Decision Query</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__AUTHZ_DECISION_QUERY = eINSTANCE.getDocumentRoot_AuthzDecisionQuery();

		/**
		 * The meta object literal for the '<em><b>Extensions</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__EXTENSIONS = eINSTANCE.getDocumentRoot_Extensions();

		/**
		 * The meta object literal for the '<em><b>Get Complete</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__GET_COMPLETE = eINSTANCE.getDocumentRoot_GetComplete();

		/**
		 * The meta object literal for the '<em><b>IDP Entry</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__IDP_ENTRY = eINSTANCE.getDocumentRoot_IDPEntry();

		/**
		 * The meta object literal for the '<em><b>IDP List</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__IDP_LIST = eINSTANCE.getDocumentRoot_IDPList();

		/**
		 * The meta object literal for the '<em><b>Logout Request</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__LOGOUT_REQUEST = eINSTANCE.getDocumentRoot_LogoutRequest();

		/**
		 * The meta object literal for the '<em><b>Logout Response</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__LOGOUT_RESPONSE = eINSTANCE.getDocumentRoot_LogoutResponse();

		/**
		 * The meta object literal for the '<em><b>Manage Name ID Request</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MANAGE_NAME_ID_REQUEST = eINSTANCE.getDocumentRoot_ManageNameIDRequest();

		/**
		 * The meta object literal for the '<em><b>Manage Name ID Response</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MANAGE_NAME_ID_RESPONSE = eINSTANCE.getDocumentRoot_ManageNameIDResponse();

		/**
		 * The meta object literal for the '<em><b>Name ID Mapping Request</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__NAME_ID_MAPPING_REQUEST = eINSTANCE.getDocumentRoot_NameIDMappingRequest();

		/**
		 * The meta object literal for the '<em><b>Name ID Mapping Response</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__NAME_ID_MAPPING_RESPONSE = eINSTANCE.getDocumentRoot_NameIDMappingResponse();

		/**
		 * The meta object literal for the '<em><b>Name ID Policy</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__NAME_ID_POLICY = eINSTANCE.getDocumentRoot_NameIDPolicy();

		/**
		 * The meta object literal for the '<em><b>New ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__NEW_ID = eINSTANCE.getDocumentRoot_NewID();

		/**
		 * The meta object literal for the '<em><b>Requested Authn Context</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__REQUESTED_AUTHN_CONTEXT = eINSTANCE.getDocumentRoot_RequestedAuthnContext();

		/**
		 * The meta object literal for the '<em><b>Requester ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__REQUESTER_ID = eINSTANCE.getDocumentRoot_RequesterID();

		/**
		 * The meta object literal for the '<em><b>Response</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__RESPONSE = eINSTANCE.getDocumentRoot_Response();

		/**
		 * The meta object literal for the '<em><b>Scoping</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__SCOPING = eINSTANCE.getDocumentRoot_Scoping();

		/**
		 * The meta object literal for the '<em><b>Session Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__SESSION_INDEX = eINSTANCE.getDocumentRoot_SessionIndex();

		/**
		 * The meta object literal for the '<em><b>Status</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__STATUS = eINSTANCE.getDocumentRoot_Status();

		/**
		 * The meta object literal for the '<em><b>Status Code</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__STATUS_CODE = eINSTANCE.getDocumentRoot_StatusCode();

		/**
		 * The meta object literal for the '<em><b>Status Detail</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__STATUS_DETAIL = eINSTANCE.getDocumentRoot_StatusDetail();

		/**
		 * The meta object literal for the '<em><b>Status Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__STATUS_MESSAGE = eINSTANCE.getDocumentRoot_StatusMessage();

		/**
		 * The meta object literal for the '<em><b>Subject Query</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__SUBJECT_QUERY = eINSTANCE.getDocumentRoot_SubjectQuery();

		/**
		 * The meta object literal for the '<em><b>Terminate</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__TERMINATE = eINSTANCE.getDocumentRoot_Terminate();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.ExtensionsTypeImpl <em>Extensions Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ExtensionsTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getExtensionsType()
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
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.IDPEntryTypeImpl <em>IDP Entry Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.IDPEntryTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getIDPEntryType()
		 * @generated
		 */
		EClass IDP_ENTRY_TYPE = eINSTANCE.getIDPEntryType();

		/**
		 * The meta object literal for the '<em><b>Loc</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDP_ENTRY_TYPE__LOC = eINSTANCE.getIDPEntryType_Loc();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDP_ENTRY_TYPE__NAME = eINSTANCE.getIDPEntryType_Name();

		/**
		 * The meta object literal for the '<em><b>Provider ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDP_ENTRY_TYPE__PROVIDER_ID = eINSTANCE.getIDPEntryType_ProviderID();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.IDPListTypeImpl <em>IDP List Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.IDPListTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getIDPListType()
		 * @generated
		 */
		EClass IDP_LIST_TYPE = eINSTANCE.getIDPListType();

		/**
		 * The meta object literal for the '<em><b>IDP Entry</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IDP_LIST_TYPE__IDP_ENTRY = eINSTANCE.getIDPListType_IDPEntry();

		/**
		 * The meta object literal for the '<em><b>Get Complete</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDP_LIST_TYPE__GET_COMPLETE = eINSTANCE.getIDPListType_GetComplete();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.LogoutRequestTypeImpl <em>Logout Request Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.LogoutRequestTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getLogoutRequestType()
		 * @generated
		 */
		EClass LOGOUT_REQUEST_TYPE = eINSTANCE.getLogoutRequestType();

		/**
		 * The meta object literal for the '<em><b>Base ID</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOGOUT_REQUEST_TYPE__BASE_ID = eINSTANCE.getLogoutRequestType_BaseID();

		/**
		 * The meta object literal for the '<em><b>Name ID</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOGOUT_REQUEST_TYPE__NAME_ID = eINSTANCE.getLogoutRequestType_NameID();

		/**
		 * The meta object literal for the '<em><b>Session Index</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOGOUT_REQUEST_TYPE__SESSION_INDEX = eINSTANCE.getLogoutRequestType_SessionIndex();

		/**
		 * The meta object literal for the '<em><b>Not On Or After</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOGOUT_REQUEST_TYPE__NOT_ON_OR_AFTER = eINSTANCE.getLogoutRequestType_NotOnOrAfter();

		/**
		 * The meta object literal for the '<em><b>Reason</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOGOUT_REQUEST_TYPE__REASON = eINSTANCE.getLogoutRequestType_Reason();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.ManageNameIDRequestTypeImpl <em>Manage Name ID Request Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ManageNameIDRequestTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getManageNameIDRequestType()
		 * @generated
		 */
		EClass MANAGE_NAME_ID_REQUEST_TYPE = eINSTANCE.getManageNameIDRequestType();

		/**
		 * The meta object literal for the '<em><b>Name ID</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MANAGE_NAME_ID_REQUEST_TYPE__NAME_ID = eINSTANCE.getManageNameIDRequestType_NameID();

		/**
		 * The meta object literal for the '<em><b>New ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MANAGE_NAME_ID_REQUEST_TYPE__NEW_ID = eINSTANCE.getManageNameIDRequestType_NewID();

		/**
		 * The meta object literal for the '<em><b>Terminate</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MANAGE_NAME_ID_REQUEST_TYPE__TERMINATE = eINSTANCE.getManageNameIDRequestType_Terminate();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.NameIDMappingRequestTypeImpl <em>Name ID Mapping Request Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.NameIDMappingRequestTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getNameIDMappingRequestType()
		 * @generated
		 */
		EClass NAME_ID_MAPPING_REQUEST_TYPE = eINSTANCE.getNameIDMappingRequestType();

		/**
		 * The meta object literal for the '<em><b>Base ID</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NAME_ID_MAPPING_REQUEST_TYPE__BASE_ID = eINSTANCE.getNameIDMappingRequestType_BaseID();

		/**
		 * The meta object literal for the '<em><b>Name ID</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NAME_ID_MAPPING_REQUEST_TYPE__NAME_ID = eINSTANCE.getNameIDMappingRequestType_NameID();

		/**
		 * The meta object literal for the '<em><b>Name ID Policy</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NAME_ID_MAPPING_REQUEST_TYPE__NAME_ID_POLICY = eINSTANCE.getNameIDMappingRequestType_NameIDPolicy();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.NameIDMappingResponseTypeImpl <em>Name ID Mapping Response Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.NameIDMappingResponseTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getNameIDMappingResponseType()
		 * @generated
		 */
		EClass NAME_ID_MAPPING_RESPONSE_TYPE = eINSTANCE.getNameIDMappingResponseType();

		/**
		 * The meta object literal for the '<em><b>Name ID</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NAME_ID_MAPPING_RESPONSE_TYPE__NAME_ID = eINSTANCE.getNameIDMappingResponseType_NameID();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.NameIDPolicyTypeImpl <em>Name ID Policy Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.NameIDPolicyTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getNameIDPolicyType()
		 * @generated
		 */
		EClass NAME_ID_POLICY_TYPE = eINSTANCE.getNameIDPolicyType();

		/**
		 * The meta object literal for the '<em><b>Allow Create</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAME_ID_POLICY_TYPE__ALLOW_CREATE = eINSTANCE.getNameIDPolicyType_AllowCreate();

		/**
		 * The meta object literal for the '<em><b>Format</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAME_ID_POLICY_TYPE__FORMAT = eINSTANCE.getNameIDPolicyType_Format();

		/**
		 * The meta object literal for the '<em><b>SP Name Qualifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAME_ID_POLICY_TYPE__SP_NAME_QUALIFIER = eINSTANCE.getNameIDPolicyType_SPNameQualifier();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.RequestAbstractTypeImpl <em>Request Abstract Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.RequestAbstractTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getRequestAbstractType()
		 * @generated
		 */
		EClass REQUEST_ABSTRACT_TYPE = eINSTANCE.getRequestAbstractType();

		/**
		 * The meta object literal for the '<em><b>Issuer</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REQUEST_ABSTRACT_TYPE__ISSUER = eINSTANCE.getRequestAbstractType_Issuer();

		/**
		 * The meta object literal for the '<em><b>Extensions</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REQUEST_ABSTRACT_TYPE__EXTENSIONS = eINSTANCE.getRequestAbstractType_Extensions();

		/**
		 * The meta object literal for the '<em><b>Consent</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUEST_ABSTRACT_TYPE__CONSENT = eINSTANCE.getRequestAbstractType_Consent();

		/**
		 * The meta object literal for the '<em><b>Destination</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUEST_ABSTRACT_TYPE__DESTINATION = eINSTANCE.getRequestAbstractType_Destination();

		/**
		 * The meta object literal for the '<em><b>ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUEST_ABSTRACT_TYPE__ID = eINSTANCE.getRequestAbstractType_ID();

		/**
		 * The meta object literal for the '<em><b>Issue Instant</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUEST_ABSTRACT_TYPE__ISSUE_INSTANT = eINSTANCE.getRequestAbstractType_IssueInstant();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUEST_ABSTRACT_TYPE__VERSION = eINSTANCE.getRequestAbstractType_Version();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.RequestedAuthnContextTypeImpl <em>Requested Authn Context Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.RequestedAuthnContextTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getRequestedAuthnContextType()
		 * @generated
		 */
		EClass REQUESTED_AUTHN_CONTEXT_TYPE = eINSTANCE.getRequestedAuthnContextType();

		/**
		 * The meta object literal for the '<em><b>Authn Context Class Ref</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUESTED_AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_CLASS_REF = eINSTANCE.getRequestedAuthnContextType_AuthnContextClassRef();

		/**
		 * The meta object literal for the '<em><b>Authn Context Decl Ref</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUESTED_AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL_REF = eINSTANCE.getRequestedAuthnContextType_AuthnContextDeclRef();

		/**
		 * The meta object literal for the '<em><b>Comparison</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUESTED_AUTHN_CONTEXT_TYPE__COMPARISON = eINSTANCE.getRequestedAuthnContextType_Comparison();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.ResponseTypeImpl <em>Response Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ResponseTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getResponseType()
		 * @generated
		 */
		EClass RESPONSE_TYPE = eINSTANCE.getResponseType();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESPONSE_TYPE__GROUP = eINSTANCE.getResponseType_Group();

		/**
		 * The meta object literal for the '<em><b>Assertion</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESPONSE_TYPE__ASSERTION = eINSTANCE.getResponseType_Assertion();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.ScopingTypeImpl <em>Scoping Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ScopingTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getScopingType()
		 * @generated
		 */
		EClass SCOPING_TYPE = eINSTANCE.getScopingType();

		/**
		 * The meta object literal for the '<em><b>IDP List</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCOPING_TYPE__IDP_LIST = eINSTANCE.getScopingType_IDPList();

		/**
		 * The meta object literal for the '<em><b>Requester ID</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCOPING_TYPE__REQUESTER_ID = eINSTANCE.getScopingType_RequesterID();

		/**
		 * The meta object literal for the '<em><b>Proxy Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCOPING_TYPE__PROXY_COUNT = eINSTANCE.getScopingType_ProxyCount();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.StatusCodeTypeImpl <em>Status Code Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.StatusCodeTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getStatusCodeType()
		 * @generated
		 */
		EClass STATUS_CODE_TYPE = eINSTANCE.getStatusCodeType();

		/**
		 * The meta object literal for the '<em><b>Status Code</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATUS_CODE_TYPE__STATUS_CODE = eINSTANCE.getStatusCodeType_StatusCode();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATUS_CODE_TYPE__VALUE = eINSTANCE.getStatusCodeType_Value();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.StatusDetailTypeImpl <em>Status Detail Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.StatusDetailTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getStatusDetailType()
		 * @generated
		 */
		EClass STATUS_DETAIL_TYPE = eINSTANCE.getStatusDetailType();

		/**
		 * The meta object literal for the '<em><b>Any</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATUS_DETAIL_TYPE__ANY = eINSTANCE.getStatusDetailType_Any();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.StatusResponseTypeImpl <em>Status Response Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.StatusResponseTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getStatusResponseType()
		 * @generated
		 */
		EClass STATUS_RESPONSE_TYPE = eINSTANCE.getStatusResponseType();

		/**
		 * The meta object literal for the '<em><b>Issuer</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATUS_RESPONSE_TYPE__ISSUER = eINSTANCE.getStatusResponseType_Issuer();

		/**
		 * The meta object literal for the '<em><b>Extensions</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATUS_RESPONSE_TYPE__EXTENSIONS = eINSTANCE.getStatusResponseType_Extensions();

		/**
		 * The meta object literal for the '<em><b>Status</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATUS_RESPONSE_TYPE__STATUS = eINSTANCE.getStatusResponseType_Status();

		/**
		 * The meta object literal for the '<em><b>Consent</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATUS_RESPONSE_TYPE__CONSENT = eINSTANCE.getStatusResponseType_Consent();

		/**
		 * The meta object literal for the '<em><b>Destination</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATUS_RESPONSE_TYPE__DESTINATION = eINSTANCE.getStatusResponseType_Destination();

		/**
		 * The meta object literal for the '<em><b>ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATUS_RESPONSE_TYPE__ID = eINSTANCE.getStatusResponseType_ID();

		/**
		 * The meta object literal for the '<em><b>In Response To</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATUS_RESPONSE_TYPE__IN_RESPONSE_TO = eINSTANCE.getStatusResponseType_InResponseTo();

		/**
		 * The meta object literal for the '<em><b>Issue Instant</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATUS_RESPONSE_TYPE__ISSUE_INSTANT = eINSTANCE.getStatusResponseType_IssueInstant();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATUS_RESPONSE_TYPE__VERSION = eINSTANCE.getStatusResponseType_Version();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.StatusTypeImpl <em>Status Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.StatusTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getStatusType()
		 * @generated
		 */
		EClass STATUS_TYPE = eINSTANCE.getStatusType();

		/**
		 * The meta object literal for the '<em><b>Status Code</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATUS_TYPE__STATUS_CODE = eINSTANCE.getStatusType_StatusCode();

		/**
		 * The meta object literal for the '<em><b>Status Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATUS_TYPE__STATUS_MESSAGE = eINSTANCE.getStatusType_StatusMessage();

		/**
		 * The meta object literal for the '<em><b>Status Detail</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATUS_TYPE__STATUS_DETAIL = eINSTANCE.getStatusType_StatusDetail();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.SubjectQueryAbstractTypeImpl <em>Subject Query Abstract Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.SubjectQueryAbstractTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getSubjectQueryAbstractType()
		 * @generated
		 */
		EClass SUBJECT_QUERY_ABSTRACT_TYPE = eINSTANCE.getSubjectQueryAbstractType();

		/**
		 * The meta object literal for the '<em><b>Subject</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBJECT_QUERY_ABSTRACT_TYPE__SUBJECT = eINSTANCE.getSubjectQueryAbstractType_Subject();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.TerminateTypeImpl <em>Terminate Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.TerminateTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getTerminateType()
		 * @generated
		 */
		EClass TERMINATE_TYPE = eINSTANCE.getTerminateType();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnContextComparisonType <em>Authn Context Comparison Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthnContextComparisonType
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getAuthnContextComparisonType()
		 * @generated
		 */
		EEnum AUTHN_CONTEXT_COMPARISON_TYPE = eINSTANCE.getAuthnContextComparisonType();

		/**
		 * The meta object literal for the '<em>Authn Context Comparison Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthnContextComparisonType
		 * @see com.tibco.be.baas.security.authn.saml.model.protocol.impl.ProtocolPackageImpl#getAuthnContextComparisonTypeObject()
		 * @generated
		 */
		EDataType AUTHN_CONTEXT_COMPARISON_TYPE_OBJECT = eINSTANCE.getAuthnContextComparisonTypeObject();

	}

} //ProtocolPackage
