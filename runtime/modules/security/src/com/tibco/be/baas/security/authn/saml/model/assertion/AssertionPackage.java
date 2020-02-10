/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion;

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
 * 			Document identifier: saml-schema-assertion-2.0 Location:
 * 			http://docs.oasis-open.org/security/saml/v2.0/ Revision history: V1.0
 * 			(November, 2002): Initial Standard Schema. V1.1 (September, 2003):
 * 			Updates within the same V1.0 namespace. V2.0 (March, 2005): New
 * 			assertion schema for SAML V2.0 namespace.
 * 		
 * <!-- end-model-doc -->
 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionFactory
 * @model kind="package"
 *        annotation="http://java.sun.com/xml/ns/jaxb version='2.0'"
 * @generated
 */
public interface AssertionPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "assertion";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "urn:oasis:names:tc:SAML:2.0:assertion";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	String eNS_PREFIX = "saml";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AssertionPackage eINSTANCE = com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.ActionTypeImpl <em>Action Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.ActionTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getActionType()
	 * @generated
	 */
	int ACTION_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_TYPE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_TYPE__NAMESPACE = 1;

	/**
	 * The number of structural features of the '<em>Action Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AdviceTypeImpl <em>Advice Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AdviceTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getAdviceType()
	 * @generated
	 */
	int ADVICE_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_TYPE__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Assertion ID Ref</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_TYPE__ASSERTION_ID_REF = 1;

	/**
	 * The feature id for the '<em><b>Assertion URI Ref</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_TYPE__ASSERTION_URI_REF = 2;

	/**
	 * The feature id for the '<em><b>Assertion</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_TYPE__ASSERTION = 3;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_TYPE__ANY = 4;

	/**
	 * The number of structural features of the '<em>Advice Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_TYPE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionTypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getAssertionType()
	 * @generated
	 */
	int ASSERTION_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Issuer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSERTION_TYPE__ISSUER = 0;

	/**
	 * The feature id for the '<em><b>Subject</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSERTION_TYPE__SUBJECT = 1;

	/**
	 * The feature id for the '<em><b>Conditions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSERTION_TYPE__CONDITIONS = 2;

	/**
	 * The feature id for the '<em><b>Advice</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSERTION_TYPE__ADVICE = 3;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSERTION_TYPE__GROUP = 4;

	/**
	 * The feature id for the '<em><b>Statement</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSERTION_TYPE__STATEMENT = 5;

	/**
	 * The feature id for the '<em><b>Authn Statement</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSERTION_TYPE__AUTHN_STATEMENT = 6;

	/**
	 * The feature id for the '<em><b>Authz Decision Statement</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSERTION_TYPE__AUTHZ_DECISION_STATEMENT = 7;

	/**
	 * The feature id for the '<em><b>Attribute Statement</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSERTION_TYPE__ATTRIBUTE_STATEMENT = 8;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSERTION_TYPE__ID = 9;

	/**
	 * The feature id for the '<em><b>Issue Instant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSERTION_TYPE__ISSUE_INSTANT = 10;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSERTION_TYPE__VERSION = 11;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSERTION_TYPE_FEATURE_COUNT = 12;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.StatementAbstractTypeImpl <em>Statement Abstract Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.StatementAbstractTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getStatementAbstractType()
	 * @generated
	 */
	int STATEMENT_ABSTRACT_TYPE = 17;

	/**
	 * The number of structural features of the '<em>Statement Abstract Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATEMENT_ABSTRACT_TYPE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AttributeStatementTypeImpl <em>Attribute Statement Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AttributeStatementTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getAttributeStatementType()
	 * @generated
	 */
	int ATTRIBUTE_STATEMENT_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_STATEMENT_TYPE__GROUP = STATEMENT_ABSTRACT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_STATEMENT_TYPE__ATTRIBUTE = STATEMENT_ABSTRACT_TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Attribute Statement Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_STATEMENT_TYPE_FEATURE_COUNT = STATEMENT_ABSTRACT_TYPE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AttributeTypeImpl <em>Attribute Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AttributeTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getAttributeType()
	 * @generated
	 */
	int ATTRIBUTE_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Attribute Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_TYPE__ATTRIBUTE_VALUE = 0;

	/**
	 * The feature id for the '<em><b>Friendly Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_TYPE__FRIENDLY_NAME = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_TYPE__NAME = 2;

	/**
	 * The feature id for the '<em><b>Name Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_TYPE__NAME_FORMAT = 3;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_TYPE__ANY_ATTRIBUTE = 4;

	/**
	 * The number of structural features of the '<em>Attribute Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_TYPE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.ConditionAbstractTypeImpl <em>Condition Abstract Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.ConditionAbstractTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getConditionAbstractType()
	 * @generated
	 */
	int CONDITION_ABSTRACT_TYPE = 10;

	/**
	 * The number of structural features of the '<em>Condition Abstract Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION_ABSTRACT_TYPE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AudienceRestrictionTypeImpl <em>Audience Restriction Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AudienceRestrictionTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getAudienceRestrictionType()
	 * @generated
	 */
	int AUDIENCE_RESTRICTION_TYPE = 5;

	/**
	 * The feature id for the '<em><b>Audience</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUDIENCE_RESTRICTION_TYPE__AUDIENCE = CONDITION_ABSTRACT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Audience Restriction Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUDIENCE_RESTRICTION_TYPE_FEATURE_COUNT = CONDITION_ABSTRACT_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AuthnContextTypeImpl <em>Authn Context Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AuthnContextTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getAuthnContextType()
	 * @generated
	 */
	int AUTHN_CONTEXT_TYPE = 6;

	/**
	 * The feature id for the '<em><b>Authn Context Class Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_CLASS_REF = 0;

	/**
	 * The feature id for the '<em><b>Authn Context Decl</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL = 1;

	/**
	 * The feature id for the '<em><b>Authn Context Decl Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL_REF = 2;

	/**
	 * The feature id for the '<em><b>Authn Context Decl1</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL1 = 3;

	/**
	 * The feature id for the '<em><b>Authn Context Decl Ref1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL_REF1 = 4;

	/**
	 * The feature id for the '<em><b>Authenticating Authority</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_CONTEXT_TYPE__AUTHENTICATING_AUTHORITY = 5;

	/**
	 * The number of structural features of the '<em>Authn Context Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_CONTEXT_TYPE_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AuthnStatementTypeImpl <em>Authn Statement Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AuthnStatementTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getAuthnStatementType()
	 * @generated
	 */
	int AUTHN_STATEMENT_TYPE = 7;

	/**
	 * The feature id for the '<em><b>Subject Locality</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_STATEMENT_TYPE__SUBJECT_LOCALITY = STATEMENT_ABSTRACT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Authn Context</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_STATEMENT_TYPE__AUTHN_CONTEXT = STATEMENT_ABSTRACT_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Authn Instant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_STATEMENT_TYPE__AUTHN_INSTANT = STATEMENT_ABSTRACT_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Session Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_STATEMENT_TYPE__SESSION_INDEX = STATEMENT_ABSTRACT_TYPE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Session Not On Or After</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_STATEMENT_TYPE__SESSION_NOT_ON_OR_AFTER = STATEMENT_ABSTRACT_TYPE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Authn Statement Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_STATEMENT_TYPE_FEATURE_COUNT = STATEMENT_ABSTRACT_TYPE_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AuthzDecisionStatementTypeImpl <em>Authz Decision Statement Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AuthzDecisionStatementTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getAuthzDecisionStatementType()
	 * @generated
	 */
	int AUTHZ_DECISION_STATEMENT_TYPE = 8;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHZ_DECISION_STATEMENT_TYPE__ACTION = STATEMENT_ABSTRACT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Evidence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHZ_DECISION_STATEMENT_TYPE__EVIDENCE = STATEMENT_ABSTRACT_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Decision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHZ_DECISION_STATEMENT_TYPE__DECISION = STATEMENT_ABSTRACT_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHZ_DECISION_STATEMENT_TYPE__RESOURCE = STATEMENT_ABSTRACT_TYPE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Authz Decision Statement Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHZ_DECISION_STATEMENT_TYPE_FEATURE_COUNT = STATEMENT_ABSTRACT_TYPE_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.BaseIDAbstractTypeImpl <em>Base ID Abstract Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.BaseIDAbstractTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getBaseIDAbstractType()
	 * @generated
	 */
	int BASE_ID_ABSTRACT_TYPE = 9;

	/**
	 * The feature id for the '<em><b>Name Qualifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_ID_ABSTRACT_TYPE__NAME_QUALIFIER = 0;

	/**
	 * The feature id for the '<em><b>SP Name Qualifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_ID_ABSTRACT_TYPE__SP_NAME_QUALIFIER = 1;

	/**
	 * The number of structural features of the '<em>Base ID Abstract Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_ID_ABSTRACT_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.ConditionsTypeImpl <em>Conditions Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.ConditionsTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getConditionsType()
	 * @generated
	 */
	int CONDITIONS_TYPE = 11;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONS_TYPE__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONS_TYPE__CONDITION = 1;

	/**
	 * The feature id for the '<em><b>Audience Restriction</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONS_TYPE__AUDIENCE_RESTRICTION = 2;

	/**
	 * The feature id for the '<em><b>One Time Use</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONS_TYPE__ONE_TIME_USE = 3;

	/**
	 * The feature id for the '<em><b>Proxy Restriction</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONS_TYPE__PROXY_RESTRICTION = 4;

	/**
	 * The feature id for the '<em><b>Not Before</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONS_TYPE__NOT_BEFORE = 5;

	/**
	 * The feature id for the '<em><b>Not On Or After</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONS_TYPE__NOT_ON_OR_AFTER = 6;

	/**
	 * The number of structural features of the '<em>Conditions Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONS_TYPE_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 12;

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
	 * The feature id for the '<em><b>Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ACTION = 3;

	/**
	 * The feature id for the '<em><b>Advice</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ADVICE = 4;

	/**
	 * The feature id for the '<em><b>Assertion</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ASSERTION = 5;

	/**
	 * The feature id for the '<em><b>Assertion ID Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ASSERTION_ID_REF = 6;

	/**
	 * The feature id for the '<em><b>Assertion URI Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ASSERTION_URI_REF = 7;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ATTRIBUTE = 8;

	/**
	 * The feature id for the '<em><b>Attribute Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ATTRIBUTE_STATEMENT = 9;

	/**
	 * The feature id for the '<em><b>Attribute Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ATTRIBUTE_VALUE = 10;

	/**
	 * The feature id for the '<em><b>Audience</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__AUDIENCE = 11;

	/**
	 * The feature id for the '<em><b>Audience Restriction</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__AUDIENCE_RESTRICTION = 12;

	/**
	 * The feature id for the '<em><b>Authenticating Authority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__AUTHENTICATING_AUTHORITY = 13;

	/**
	 * The feature id for the '<em><b>Authn Context</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__AUTHN_CONTEXT = 14;

	/**
	 * The feature id for the '<em><b>Authn Context Class Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__AUTHN_CONTEXT_CLASS_REF = 15;

	/**
	 * The feature id for the '<em><b>Authn Context Decl</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__AUTHN_CONTEXT_DECL = 16;

	/**
	 * The feature id for the '<em><b>Authn Context Decl Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__AUTHN_CONTEXT_DECL_REF = 17;

	/**
	 * The feature id for the '<em><b>Authn Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__AUTHN_STATEMENT = 18;

	/**
	 * The feature id for the '<em><b>Authz Decision Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__AUTHZ_DECISION_STATEMENT = 19;

	/**
	 * The feature id for the '<em><b>Base ID</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BASE_ID = 20;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CONDITION = 21;

	/**
	 * The feature id for the '<em><b>Conditions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CONDITIONS = 22;

	/**
	 * The feature id for the '<em><b>Evidence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EVIDENCE = 23;

	/**
	 * The feature id for the '<em><b>Issuer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ISSUER = 24;

	/**
	 * The feature id for the '<em><b>Name ID</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__NAME_ID = 25;

	/**
	 * The feature id for the '<em><b>One Time Use</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ONE_TIME_USE = 26;

	/**
	 * The feature id for the '<em><b>Proxy Restriction</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PROXY_RESTRICTION = 27;

	/**
	 * The feature id for the '<em><b>Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__STATEMENT = 28;

	/**
	 * The feature id for the '<em><b>Subject</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SUBJECT = 29;

	/**
	 * The feature id for the '<em><b>Subject Confirmation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SUBJECT_CONFIRMATION = 30;

	/**
	 * The feature id for the '<em><b>Subject Confirmation Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SUBJECT_CONFIRMATION_DATA = 31;

	/**
	 * The feature id for the '<em><b>Subject Locality</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SUBJECT_LOCALITY = 32;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 33;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.EvidenceTypeImpl <em>Evidence Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.EvidenceTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getEvidenceType()
	 * @generated
	 */
	int EVIDENCE_TYPE = 13;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVIDENCE_TYPE__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Assertion ID Ref</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVIDENCE_TYPE__ASSERTION_ID_REF = 1;

	/**
	 * The feature id for the '<em><b>Assertion URI Ref</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVIDENCE_TYPE__ASSERTION_URI_REF = 2;

	/**
	 * The feature id for the '<em><b>Assertion</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVIDENCE_TYPE__ASSERTION = 3;

	/**
	 * The number of structural features of the '<em>Evidence Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVIDENCE_TYPE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.NameIDTypeImpl <em>Name ID Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.NameIDTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getNameIDType()
	 * @generated
	 */
	int NAME_ID_TYPE = 14;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_TYPE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_TYPE__FORMAT = 1;

	/**
	 * The feature id for the '<em><b>Name Qualifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_TYPE__NAME_QUALIFIER = 2;

	/**
	 * The feature id for the '<em><b>SP Name Qualifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_TYPE__SP_NAME_QUALIFIER = 3;

	/**
	 * The feature id for the '<em><b>SP Provided ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_TYPE__SP_PROVIDED_ID = 4;

	/**
	 * The number of structural features of the '<em>Name ID Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_ID_TYPE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.OneTimeUseTypeImpl <em>One Time Use Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.OneTimeUseTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getOneTimeUseType()
	 * @generated
	 */
	int ONE_TIME_USE_TYPE = 15;

	/**
	 * The number of structural features of the '<em>One Time Use Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_TIME_USE_TYPE_FEATURE_COUNT = CONDITION_ABSTRACT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.ProxyRestrictionTypeImpl <em>Proxy Restriction Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.ProxyRestrictionTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getProxyRestrictionType()
	 * @generated
	 */
	int PROXY_RESTRICTION_TYPE = 16;

	/**
	 * The feature id for the '<em><b>Audience</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROXY_RESTRICTION_TYPE__AUDIENCE = CONDITION_ABSTRACT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROXY_RESTRICTION_TYPE__COUNT = CONDITION_ABSTRACT_TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Proxy Restriction Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROXY_RESTRICTION_TYPE_FEATURE_COUNT = CONDITION_ABSTRACT_TYPE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectConfirmationDataTypeImpl <em>Subject Confirmation Data Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectConfirmationDataTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getSubjectConfirmationDataType()
	 * @generated
	 */
	int SUBJECT_CONFIRMATION_DATA_TYPE = 18;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_CONFIRMATION_DATA_TYPE__MIXED = 0;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_CONFIRMATION_DATA_TYPE__ANY = 1;

	/**
	 * The feature id for the '<em><b>Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_CONFIRMATION_DATA_TYPE__ADDRESS = 2;

	/**
	 * The feature id for the '<em><b>In Response To</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_CONFIRMATION_DATA_TYPE__IN_RESPONSE_TO = 3;

	/**
	 * The feature id for the '<em><b>Not Before</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_CONFIRMATION_DATA_TYPE__NOT_BEFORE = 4;

	/**
	 * The feature id for the '<em><b>Not On Or After</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_CONFIRMATION_DATA_TYPE__NOT_ON_OR_AFTER = 5;

	/**
	 * The feature id for the '<em><b>Recipient</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_CONFIRMATION_DATA_TYPE__RECIPIENT = 6;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_CONFIRMATION_DATA_TYPE__ANY_ATTRIBUTE = 7;

	/**
	 * The number of structural features of the '<em>Subject Confirmation Data Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_CONFIRMATION_DATA_TYPE_FEATURE_COUNT = 8;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectConfirmationTypeImpl <em>Subject Confirmation Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectConfirmationTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getSubjectConfirmationType()
	 * @generated
	 */
	int SUBJECT_CONFIRMATION_TYPE = 19;

	/**
	 * The feature id for the '<em><b>Base ID</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_CONFIRMATION_TYPE__BASE_ID = 0;

	/**
	 * The feature id for the '<em><b>Name ID</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_CONFIRMATION_TYPE__NAME_ID = 1;

	/**
	 * The feature id for the '<em><b>Subject Confirmation Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_CONFIRMATION_TYPE__SUBJECT_CONFIRMATION_DATA = 2;

	/**
	 * The feature id for the '<em><b>Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_CONFIRMATION_TYPE__METHOD = 3;

	/**
	 * The number of structural features of the '<em>Subject Confirmation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_CONFIRMATION_TYPE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectLocalityTypeImpl <em>Subject Locality Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectLocalityTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getSubjectLocalityType()
	 * @generated
	 */
	int SUBJECT_LOCALITY_TYPE = 20;

	/**
	 * The feature id for the '<em><b>Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_LOCALITY_TYPE__ADDRESS = 0;

	/**
	 * The feature id for the '<em><b>DNS Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_LOCALITY_TYPE__DNS_NAME = 1;

	/**
	 * The number of structural features of the '<em>Subject Locality Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_LOCALITY_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectTypeImpl <em>Subject Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getSubjectType()
	 * @generated
	 */
	int SUBJECT_TYPE = 21;

	/**
	 * The feature id for the '<em><b>Base ID</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_TYPE__BASE_ID = 0;

	/**
	 * The feature id for the '<em><b>Name ID</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_TYPE__NAME_ID = 1;

	/**
	 * The feature id for the '<em><b>Subject Confirmation</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_TYPE__SUBJECT_CONFIRMATION = 2;

	/**
	 * The feature id for the '<em><b>Subject Confirmation1</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_TYPE__SUBJECT_CONFIRMATION1 = 3;

	/**
	 * The number of structural features of the '<em>Subject Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBJECT_TYPE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DecisionType <em>Decision Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DecisionType
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getDecisionType()
	 * @generated
	 */
	int DECISION_TYPE = 22;

	/**
	 * The meta object id for the '<em>Decision Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DecisionType
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getDecisionTypeObject()
	 * @generated
	 */
	int DECISION_TYPE_OBJECT = 23;


	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.ActionType <em>Action Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Action Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.ActionType
	 * @generated
	 */
	EClass getActionType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.ActionType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.ActionType#getValue()
	 * @see #getActionType()
	 * @generated
	 */
	EAttribute getActionType_Value();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.ActionType#getNamespace <em>Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Namespace</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.ActionType#getNamespace()
	 * @see #getActionType()
	 * @generated
	 */
	EAttribute getActionType_Namespace();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AdviceType <em>Advice Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Advice Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AdviceType
	 * @generated
	 */
	EClass getAdviceType();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AdviceType#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AdviceType#getGroup()
	 * @see #getAdviceType()
	 * @generated
	 */
	EAttribute getAdviceType_Group();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AdviceType#getAssertionIDRef <em>Assertion ID Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Assertion ID Ref</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AdviceType#getAssertionIDRef()
	 * @see #getAdviceType()
	 * @generated
	 */
	EAttribute getAdviceType_AssertionIDRef();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AdviceType#getAssertionURIRef <em>Assertion URI Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Assertion URI Ref</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AdviceType#getAssertionURIRef()
	 * @see #getAdviceType()
	 * @generated
	 */
	EAttribute getAdviceType_AssertionURIRef();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AdviceType#getAssertion <em>Assertion</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Assertion</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AdviceType#getAssertion()
	 * @see #getAdviceType()
	 * @generated
	 */
	EReference getAdviceType_Assertion();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AdviceType#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AdviceType#getAny()
	 * @see #getAdviceType()
	 * @generated
	 */
	EAttribute getAdviceType_Any();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType
	 * @generated
	 */
	EClass getAssertionType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getIssuer <em>Issuer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Issuer</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getIssuer()
	 * @see #getAssertionType()
	 * @generated
	 */
	EReference getAssertionType_Issuer();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getSubject <em>Subject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Subject</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getSubject()
	 * @see #getAssertionType()
	 * @generated
	 */
	EReference getAssertionType_Subject();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getConditions <em>Conditions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Conditions</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getConditions()
	 * @see #getAssertionType()
	 * @generated
	 */
	EReference getAssertionType_Conditions();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getAdvice <em>Advice</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Advice</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getAdvice()
	 * @see #getAssertionType()
	 * @generated
	 */
	EReference getAssertionType_Advice();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getGroup()
	 * @see #getAssertionType()
	 * @generated
	 */
	EAttribute getAssertionType_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getStatement <em>Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Statement</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getStatement()
	 * @see #getAssertionType()
	 * @generated
	 */
	EReference getAssertionType_Statement();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getAuthnStatement <em>Authn Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Authn Statement</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getAuthnStatement()
	 * @see #getAssertionType()
	 * @generated
	 */
	EReference getAssertionType_AuthnStatement();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getAuthzDecisionStatement <em>Authz Decision Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Authz Decision Statement</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getAuthzDecisionStatement()
	 * @see #getAssertionType()
	 * @generated
	 */
	EReference getAssertionType_AuthzDecisionStatement();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getAttributeStatement <em>Attribute Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute Statement</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getAttributeStatement()
	 * @see #getAssertionType()
	 * @generated
	 */
	EReference getAssertionType_AttributeStatement();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getID <em>ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getID()
	 * @see #getAssertionType()
	 * @generated
	 */
	EAttribute getAssertionType_ID();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getIssueInstant <em>Issue Instant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Issue Instant</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getIssueInstant()
	 * @see #getAssertionType()
	 * @generated
	 */
	EAttribute getAssertionType_IssueInstant();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getVersion()
	 * @see #getAssertionType()
	 * @generated
	 */
	EAttribute getAssertionType_Version();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AttributeStatementType <em>Attribute Statement Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Statement Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AttributeStatementType
	 * @generated
	 */
	EClass getAttributeStatementType();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AttributeStatementType#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AttributeStatementType#getGroup()
	 * @see #getAttributeStatementType()
	 * @generated
	 */
	EAttribute getAttributeStatementType_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AttributeStatementType#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AttributeStatementType#getAttribute()
	 * @see #getAttributeStatementType()
	 * @generated
	 */
	EReference getAttributeStatementType_Attribute();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType <em>Attribute Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType
	 * @generated
	 */
	EClass getAttributeType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType#getAttributeValue <em>Attribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute Value</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType#getAttributeValue()
	 * @see #getAttributeType()
	 * @generated
	 */
	EReference getAttributeType_AttributeValue();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType#getFriendlyName <em>Friendly Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Friendly Name</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType#getFriendlyName()
	 * @see #getAttributeType()
	 * @generated
	 */
	EAttribute getAttributeType_FriendlyName();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType#getName()
	 * @see #getAttributeType()
	 * @generated
	 */
	EAttribute getAttributeType_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType#getNameFormat <em>Name Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name Format</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType#getNameFormat()
	 * @see #getAttributeType()
	 * @generated
	 */
	EAttribute getAttributeType_NameFormat();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType#getAnyAttribute()
	 * @see #getAttributeType()
	 * @generated
	 */
	EAttribute getAttributeType_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AudienceRestrictionType <em>Audience Restriction Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Audience Restriction Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AudienceRestrictionType
	 * @generated
	 */
	EClass getAudienceRestrictionType();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AudienceRestrictionType#getAudience <em>Audience</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Audience</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AudienceRestrictionType#getAudience()
	 * @see #getAudienceRestrictionType()
	 * @generated
	 */
	EAttribute getAudienceRestrictionType_Audience();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType <em>Authn Context Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Authn Context Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType
	 * @generated
	 */
	EClass getAuthnContextType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType#getAuthnContextClassRef <em>Authn Context Class Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Authn Context Class Ref</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType#getAuthnContextClassRef()
	 * @see #getAuthnContextType()
	 * @generated
	 */
	EAttribute getAuthnContextType_AuthnContextClassRef();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType#getAuthnContextDecl <em>Authn Context Decl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Authn Context Decl</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType#getAuthnContextDecl()
	 * @see #getAuthnContextType()
	 * @generated
	 */
	EReference getAuthnContextType_AuthnContextDecl();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType#getAuthnContextDeclRef <em>Authn Context Decl Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Authn Context Decl Ref</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType#getAuthnContextDeclRef()
	 * @see #getAuthnContextType()
	 * @generated
	 */
	EAttribute getAuthnContextType_AuthnContextDeclRef();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType#getAuthnContextDecl1 <em>Authn Context Decl1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Authn Context Decl1</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType#getAuthnContextDecl1()
	 * @see #getAuthnContextType()
	 * @generated
	 */
	EReference getAuthnContextType_AuthnContextDecl1();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType#getAuthnContextDeclRef1 <em>Authn Context Decl Ref1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Authn Context Decl Ref1</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType#getAuthnContextDeclRef1()
	 * @see #getAuthnContextType()
	 * @generated
	 */
	EAttribute getAuthnContextType_AuthnContextDeclRef1();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType#getAuthenticatingAuthority <em>Authenticating Authority</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Authenticating Authority</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType#getAuthenticatingAuthority()
	 * @see #getAuthnContextType()
	 * @generated
	 */
	EAttribute getAuthnContextType_AuthenticatingAuthority();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType <em>Authn Statement Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Authn Statement Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType
	 * @generated
	 */
	EClass getAuthnStatementType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType#getSubjectLocality <em>Subject Locality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Subject Locality</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType#getSubjectLocality()
	 * @see #getAuthnStatementType()
	 * @generated
	 */
	EReference getAuthnStatementType_SubjectLocality();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType#getAuthnContext <em>Authn Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Authn Context</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType#getAuthnContext()
	 * @see #getAuthnStatementType()
	 * @generated
	 */
	EReference getAuthnStatementType_AuthnContext();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType#getAuthnInstant <em>Authn Instant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Authn Instant</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType#getAuthnInstant()
	 * @see #getAuthnStatementType()
	 * @generated
	 */
	EAttribute getAuthnStatementType_AuthnInstant();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType#getSessionIndex <em>Session Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Session Index</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType#getSessionIndex()
	 * @see #getAuthnStatementType()
	 * @generated
	 */
	EAttribute getAuthnStatementType_SessionIndex();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType#getSessionNotOnOrAfter <em>Session Not On Or After</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Session Not On Or After</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType#getSessionNotOnOrAfter()
	 * @see #getAuthnStatementType()
	 * @generated
	 */
	EAttribute getAuthnStatementType_SessionNotOnOrAfter();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthzDecisionStatementType <em>Authz Decision Statement Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Authz Decision Statement Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AuthzDecisionStatementType
	 * @generated
	 */
	EClass getAuthzDecisionStatementType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthzDecisionStatementType#getAction <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Action</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AuthzDecisionStatementType#getAction()
	 * @see #getAuthzDecisionStatementType()
	 * @generated
	 */
	EReference getAuthzDecisionStatementType_Action();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthzDecisionStatementType#getEvidence <em>Evidence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Evidence</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AuthzDecisionStatementType#getEvidence()
	 * @see #getAuthzDecisionStatementType()
	 * @generated
	 */
	EReference getAuthzDecisionStatementType_Evidence();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthzDecisionStatementType#getDecision <em>Decision</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Decision</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AuthzDecisionStatementType#getDecision()
	 * @see #getAuthzDecisionStatementType()
	 * @generated
	 */
	EAttribute getAuthzDecisionStatementType_Decision();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthzDecisionStatementType#getResource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Resource</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AuthzDecisionStatementType#getResource()
	 * @see #getAuthzDecisionStatementType()
	 * @generated
	 */
	EAttribute getAuthzDecisionStatementType_Resource();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.BaseIDAbstractType <em>Base ID Abstract Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Base ID Abstract Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.BaseIDAbstractType
	 * @generated
	 */
	EClass getBaseIDAbstractType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.BaseIDAbstractType#getNameQualifier <em>Name Qualifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name Qualifier</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.BaseIDAbstractType#getNameQualifier()
	 * @see #getBaseIDAbstractType()
	 * @generated
	 */
	EAttribute getBaseIDAbstractType_NameQualifier();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.BaseIDAbstractType#getSPNameQualifier <em>SP Name Qualifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>SP Name Qualifier</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.BaseIDAbstractType#getSPNameQualifier()
	 * @see #getBaseIDAbstractType()
	 * @generated
	 */
	EAttribute getBaseIDAbstractType_SPNameQualifier();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.ConditionAbstractType <em>Condition Abstract Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Condition Abstract Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.ConditionAbstractType
	 * @generated
	 */
	EClass getConditionAbstractType();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType <em>Conditions Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Conditions Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType
	 * @generated
	 */
	EClass getConditionsType();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType#getGroup()
	 * @see #getConditionsType()
	 * @generated
	 */
	EAttribute getConditionsType_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Condition</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType#getCondition()
	 * @see #getConditionsType()
	 * @generated
	 */
	EReference getConditionsType_Condition();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType#getAudienceRestriction <em>Audience Restriction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Audience Restriction</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType#getAudienceRestriction()
	 * @see #getConditionsType()
	 * @generated
	 */
	EReference getConditionsType_AudienceRestriction();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType#getOneTimeUse <em>One Time Use</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>One Time Use</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType#getOneTimeUse()
	 * @see #getConditionsType()
	 * @generated
	 */
	EReference getConditionsType_OneTimeUse();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType#getProxyRestriction <em>Proxy Restriction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Proxy Restriction</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType#getProxyRestriction()
	 * @see #getConditionsType()
	 * @generated
	 */
	EReference getConditionsType_ProxyRestriction();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType#getNotBefore <em>Not Before</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Not Before</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType#getNotBefore()
	 * @see #getConditionsType()
	 * @generated
	 */
	EAttribute getConditionsType_NotBefore();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType#getNotOnOrAfter <em>Not On Or After</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Not On Or After</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType#getNotOnOrAfter()
	 * @see #getConditionsType()
	 * @generated
	 */
	EAttribute getConditionsType_NotOnOrAfter();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAction <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Action</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAction()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Action();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAdvice <em>Advice</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Advice</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAdvice()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Advice();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAssertion <em>Assertion</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Assertion</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAssertion()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Assertion();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAssertionIDRef <em>Assertion ID Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Assertion ID Ref</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAssertionIDRef()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_AssertionIDRef();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAssertionURIRef <em>Assertion URI Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Assertion URI Ref</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAssertionURIRef()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_AssertionURIRef();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Attribute</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAttribute()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Attribute();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAttributeStatement <em>Attribute Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Attribute Statement</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAttributeStatement()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_AttributeStatement();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAttributeValue <em>Attribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Attribute Value</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAttributeValue()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_AttributeValue();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAudience <em>Audience</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Audience</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAudience()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Audience();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAudienceRestriction <em>Audience Restriction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Audience Restriction</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAudienceRestriction()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_AudienceRestriction();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthenticatingAuthority <em>Authenticating Authority</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Authenticating Authority</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthenticatingAuthority()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_AuthenticatingAuthority();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthnContext <em>Authn Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Authn Context</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthnContext()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_AuthnContext();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthnContextClassRef <em>Authn Context Class Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Authn Context Class Ref</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthnContextClassRef()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_AuthnContextClassRef();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthnContextDecl <em>Authn Context Decl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Authn Context Decl</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthnContextDecl()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_AuthnContextDecl();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthnContextDeclRef <em>Authn Context Decl Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Authn Context Decl Ref</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthnContextDeclRef()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_AuthnContextDeclRef();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthnStatement <em>Authn Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Authn Statement</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthnStatement()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_AuthnStatement();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthzDecisionStatement <em>Authz Decision Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Authz Decision Statement</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthzDecisionStatement()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_AuthzDecisionStatement();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getBaseID <em>Base ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Base ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getBaseID()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_BaseID();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getCondition()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Condition();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getConditions <em>Conditions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Conditions</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getConditions()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Conditions();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getEvidence <em>Evidence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Evidence</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getEvidence()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Evidence();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getIssuer <em>Issuer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Issuer</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getIssuer()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Issuer();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getNameID <em>Name ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Name ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getNameID()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_NameID();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getOneTimeUse <em>One Time Use</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>One Time Use</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getOneTimeUse()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_OneTimeUse();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getProxyRestriction <em>Proxy Restriction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Proxy Restriction</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getProxyRestriction()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ProxyRestriction();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getStatement <em>Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Statement</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getStatement()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Statement();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getSubject <em>Subject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Subject</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getSubject()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Subject();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getSubjectConfirmation <em>Subject Confirmation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Subject Confirmation</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getSubjectConfirmation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_SubjectConfirmation();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getSubjectConfirmationData <em>Subject Confirmation Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Subject Confirmation Data</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getSubjectConfirmationData()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_SubjectConfirmationData();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getSubjectLocality <em>Subject Locality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Subject Locality</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getSubjectLocality()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_SubjectLocality();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.EvidenceType <em>Evidence Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Evidence Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.EvidenceType
	 * @generated
	 */
	EClass getEvidenceType();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.EvidenceType#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.EvidenceType#getGroup()
	 * @see #getEvidenceType()
	 * @generated
	 */
	EAttribute getEvidenceType_Group();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.EvidenceType#getAssertionIDRef <em>Assertion ID Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Assertion ID Ref</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.EvidenceType#getAssertionIDRef()
	 * @see #getEvidenceType()
	 * @generated
	 */
	EAttribute getEvidenceType_AssertionIDRef();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.EvidenceType#getAssertionURIRef <em>Assertion URI Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Assertion URI Ref</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.EvidenceType#getAssertionURIRef()
	 * @see #getEvidenceType()
	 * @generated
	 */
	EAttribute getEvidenceType_AssertionURIRef();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.EvidenceType#getAssertion <em>Assertion</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Assertion</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.EvidenceType#getAssertion()
	 * @see #getEvidenceType()
	 * @generated
	 */
	EReference getEvidenceType_Assertion();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType <em>Name ID Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Name ID Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType
	 * @generated
	 */
	EClass getNameIDType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType#getValue()
	 * @see #getNameIDType()
	 * @generated
	 */
	EAttribute getNameIDType_Value();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType#getFormat <em>Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Format</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType#getFormat()
	 * @see #getNameIDType()
	 * @generated
	 */
	EAttribute getNameIDType_Format();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType#getNameQualifier <em>Name Qualifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name Qualifier</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType#getNameQualifier()
	 * @see #getNameIDType()
	 * @generated
	 */
	EAttribute getNameIDType_NameQualifier();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType#getSPNameQualifier <em>SP Name Qualifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>SP Name Qualifier</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType#getSPNameQualifier()
	 * @see #getNameIDType()
	 * @generated
	 */
	EAttribute getNameIDType_SPNameQualifier();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType#getSPProvidedID <em>SP Provided ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>SP Provided ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType#getSPProvidedID()
	 * @see #getNameIDType()
	 * @generated
	 */
	EAttribute getNameIDType_SPProvidedID();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.OneTimeUseType <em>One Time Use Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>One Time Use Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.OneTimeUseType
	 * @generated
	 */
	EClass getOneTimeUseType();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.ProxyRestrictionType <em>Proxy Restriction Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Proxy Restriction Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.ProxyRestrictionType
	 * @generated
	 */
	EClass getProxyRestrictionType();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.ProxyRestrictionType#getAudience <em>Audience</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Audience</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.ProxyRestrictionType#getAudience()
	 * @see #getProxyRestrictionType()
	 * @generated
	 */
	EAttribute getProxyRestrictionType_Audience();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.ProxyRestrictionType#getCount <em>Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Count</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.ProxyRestrictionType#getCount()
	 * @see #getProxyRestrictionType()
	 * @generated
	 */
	EAttribute getProxyRestrictionType_Count();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.StatementAbstractType <em>Statement Abstract Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Statement Abstract Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.StatementAbstractType
	 * @generated
	 */
	EClass getStatementAbstractType();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType <em>Subject Confirmation Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Subject Confirmation Data Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType
	 * @generated
	 */
	EClass getSubjectConfirmationDataType();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getMixed()
	 * @see #getSubjectConfirmationDataType()
	 * @generated
	 */
	EAttribute getSubjectConfirmationDataType_Mixed();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getAny()
	 * @see #getSubjectConfirmationDataType()
	 * @generated
	 */
	EAttribute getSubjectConfirmationDataType_Any();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getAddress <em>Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Address</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getAddress()
	 * @see #getSubjectConfirmationDataType()
	 * @generated
	 */
	EAttribute getSubjectConfirmationDataType_Address();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getInResponseTo <em>In Response To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>In Response To</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getInResponseTo()
	 * @see #getSubjectConfirmationDataType()
	 * @generated
	 */
	EAttribute getSubjectConfirmationDataType_InResponseTo();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getNotBefore <em>Not Before</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Not Before</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getNotBefore()
	 * @see #getSubjectConfirmationDataType()
	 * @generated
	 */
	EAttribute getSubjectConfirmationDataType_NotBefore();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getNotOnOrAfter <em>Not On Or After</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Not On Or After</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getNotOnOrAfter()
	 * @see #getSubjectConfirmationDataType()
	 * @generated
	 */
	EAttribute getSubjectConfirmationDataType_NotOnOrAfter();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getRecipient <em>Recipient</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Recipient</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getRecipient()
	 * @see #getSubjectConfirmationDataType()
	 * @generated
	 */
	EAttribute getSubjectConfirmationDataType_Recipient();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getAnyAttribute()
	 * @see #getSubjectConfirmationDataType()
	 * @generated
	 */
	EAttribute getSubjectConfirmationDataType_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType <em>Subject Confirmation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Subject Confirmation Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType
	 * @generated
	 */
	EClass getSubjectConfirmationType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType#getBaseID <em>Base ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Base ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType#getBaseID()
	 * @see #getSubjectConfirmationType()
	 * @generated
	 */
	EReference getSubjectConfirmationType_BaseID();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType#getNameID <em>Name ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Name ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType#getNameID()
	 * @see #getSubjectConfirmationType()
	 * @generated
	 */
	EReference getSubjectConfirmationType_NameID();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType#getSubjectConfirmationData <em>Subject Confirmation Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Subject Confirmation Data</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType#getSubjectConfirmationData()
	 * @see #getSubjectConfirmationType()
	 * @generated
	 */
	EReference getSubjectConfirmationType_SubjectConfirmationData();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType#getMethod <em>Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Method</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType#getMethod()
	 * @see #getSubjectConfirmationType()
	 * @generated
	 */
	EAttribute getSubjectConfirmationType_Method();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectLocalityType <em>Subject Locality Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Subject Locality Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.SubjectLocalityType
	 * @generated
	 */
	EClass getSubjectLocalityType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectLocalityType#getAddress <em>Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Address</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.SubjectLocalityType#getAddress()
	 * @see #getSubjectLocalityType()
	 * @generated
	 */
	EAttribute getSubjectLocalityType_Address();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectLocalityType#getDNSName <em>DNS Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>DNS Name</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.SubjectLocalityType#getDNSName()
	 * @see #getSubjectLocalityType()
	 * @generated
	 */
	EAttribute getSubjectLocalityType_DNSName();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType <em>Subject Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Subject Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType
	 * @generated
	 */
	EClass getSubjectType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType#getBaseID <em>Base ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Base ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType#getBaseID()
	 * @see #getSubjectType()
	 * @generated
	 */
	EReference getSubjectType_BaseID();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType#getNameID <em>Name ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Name ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType#getNameID()
	 * @see #getSubjectType()
	 * @generated
	 */
	EReference getSubjectType_NameID();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType#getSubjectConfirmation <em>Subject Confirmation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Subject Confirmation</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType#getSubjectConfirmation()
	 * @see #getSubjectType()
	 * @generated
	 */
	EReference getSubjectType_SubjectConfirmation();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType#getSubjectConfirmation1 <em>Subject Confirmation1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Subject Confirmation1</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType#getSubjectConfirmation1()
	 * @see #getSubjectType()
	 * @generated
	 */
	EReference getSubjectType_SubjectConfirmation1();

	/**
	 * Returns the meta object for enum '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DecisionType <em>Decision Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Decision Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DecisionType
	 * @generated
	 */
	EEnum getDecisionType();

	/**
	 * Returns the meta object for data type '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DecisionType <em>Decision Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Decision Type Object</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DecisionType
	 * @model instanceClass="com.tibco.be.baas.security.authn.saml.model.assertion.DecisionType"
	 *        extendedMetaData="name='DecisionType:Object' baseType='DecisionType'"
	 * @generated
	 */
	EDataType getDecisionTypeObject();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	AssertionFactory getAssertionFactory();

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
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.ActionTypeImpl <em>Action Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.ActionTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getActionType()
		 * @generated
		 */
		EClass ACTION_TYPE = eINSTANCE.getActionType();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION_TYPE__VALUE = eINSTANCE.getActionType_Value();

		/**
		 * The meta object literal for the '<em><b>Namespace</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION_TYPE__NAMESPACE = eINSTANCE.getActionType_Namespace();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AdviceTypeImpl <em>Advice Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AdviceTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getAdviceType()
		 * @generated
		 */
		EClass ADVICE_TYPE = eINSTANCE.getAdviceType();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADVICE_TYPE__GROUP = eINSTANCE.getAdviceType_Group();

		/**
		 * The meta object literal for the '<em><b>Assertion ID Ref</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADVICE_TYPE__ASSERTION_ID_REF = eINSTANCE.getAdviceType_AssertionIDRef();

		/**
		 * The meta object literal for the '<em><b>Assertion URI Ref</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADVICE_TYPE__ASSERTION_URI_REF = eINSTANCE.getAdviceType_AssertionURIRef();

		/**
		 * The meta object literal for the '<em><b>Assertion</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADVICE_TYPE__ASSERTION = eINSTANCE.getAdviceType_Assertion();

		/**
		 * The meta object literal for the '<em><b>Any</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADVICE_TYPE__ANY = eINSTANCE.getAdviceType_Any();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionTypeImpl <em>Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getAssertionType()
		 * @generated
		 */
		EClass ASSERTION_TYPE = eINSTANCE.getAssertionType();

		/**
		 * The meta object literal for the '<em><b>Issuer</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSERTION_TYPE__ISSUER = eINSTANCE.getAssertionType_Issuer();

		/**
		 * The meta object literal for the '<em><b>Subject</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSERTION_TYPE__SUBJECT = eINSTANCE.getAssertionType_Subject();

		/**
		 * The meta object literal for the '<em><b>Conditions</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSERTION_TYPE__CONDITIONS = eINSTANCE.getAssertionType_Conditions();

		/**
		 * The meta object literal for the '<em><b>Advice</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSERTION_TYPE__ADVICE = eINSTANCE.getAssertionType_Advice();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASSERTION_TYPE__GROUP = eINSTANCE.getAssertionType_Group();

		/**
		 * The meta object literal for the '<em><b>Statement</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSERTION_TYPE__STATEMENT = eINSTANCE.getAssertionType_Statement();

		/**
		 * The meta object literal for the '<em><b>Authn Statement</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSERTION_TYPE__AUTHN_STATEMENT = eINSTANCE.getAssertionType_AuthnStatement();

		/**
		 * The meta object literal for the '<em><b>Authz Decision Statement</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSERTION_TYPE__AUTHZ_DECISION_STATEMENT = eINSTANCE.getAssertionType_AuthzDecisionStatement();

		/**
		 * The meta object literal for the '<em><b>Attribute Statement</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSERTION_TYPE__ATTRIBUTE_STATEMENT = eINSTANCE.getAssertionType_AttributeStatement();

		/**
		 * The meta object literal for the '<em><b>ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASSERTION_TYPE__ID = eINSTANCE.getAssertionType_ID();

		/**
		 * The meta object literal for the '<em><b>Issue Instant</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASSERTION_TYPE__ISSUE_INSTANT = eINSTANCE.getAssertionType_IssueInstant();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASSERTION_TYPE__VERSION = eINSTANCE.getAssertionType_Version();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AttributeStatementTypeImpl <em>Attribute Statement Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AttributeStatementTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getAttributeStatementType()
		 * @generated
		 */
		EClass ATTRIBUTE_STATEMENT_TYPE = eINSTANCE.getAttributeStatementType();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_STATEMENT_TYPE__GROUP = eINSTANCE.getAttributeStatementType_Group();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_STATEMENT_TYPE__ATTRIBUTE = eINSTANCE.getAttributeStatementType_Attribute();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AttributeTypeImpl <em>Attribute Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AttributeTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getAttributeType()
		 * @generated
		 */
		EClass ATTRIBUTE_TYPE = eINSTANCE.getAttributeType();

		/**
		 * The meta object literal for the '<em><b>Attribute Value</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_TYPE__ATTRIBUTE_VALUE = eINSTANCE.getAttributeType_AttributeValue();

		/**
		 * The meta object literal for the '<em><b>Friendly Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_TYPE__FRIENDLY_NAME = eINSTANCE.getAttributeType_FriendlyName();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_TYPE__NAME = eINSTANCE.getAttributeType_Name();

		/**
		 * The meta object literal for the '<em><b>Name Format</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_TYPE__NAME_FORMAT = eINSTANCE.getAttributeType_NameFormat();

		/**
		 * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_TYPE__ANY_ATTRIBUTE = eINSTANCE.getAttributeType_AnyAttribute();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AudienceRestrictionTypeImpl <em>Audience Restriction Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AudienceRestrictionTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getAudienceRestrictionType()
		 * @generated
		 */
		EClass AUDIENCE_RESTRICTION_TYPE = eINSTANCE.getAudienceRestrictionType();

		/**
		 * The meta object literal for the '<em><b>Audience</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUDIENCE_RESTRICTION_TYPE__AUDIENCE = eINSTANCE.getAudienceRestrictionType_Audience();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AuthnContextTypeImpl <em>Authn Context Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AuthnContextTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getAuthnContextType()
		 * @generated
		 */
		EClass AUTHN_CONTEXT_TYPE = eINSTANCE.getAuthnContextType();

		/**
		 * The meta object literal for the '<em><b>Authn Context Class Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_CLASS_REF = eINSTANCE.getAuthnContextType_AuthnContextClassRef();

		/**
		 * The meta object literal for the '<em><b>Authn Context Decl</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL = eINSTANCE.getAuthnContextType_AuthnContextDecl();

		/**
		 * The meta object literal for the '<em><b>Authn Context Decl Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL_REF = eINSTANCE.getAuthnContextType_AuthnContextDeclRef();

		/**
		 * The meta object literal for the '<em><b>Authn Context Decl1</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL1 = eINSTANCE.getAuthnContextType_AuthnContextDecl1();

		/**
		 * The meta object literal for the '<em><b>Authn Context Decl Ref1</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL_REF1 = eINSTANCE.getAuthnContextType_AuthnContextDeclRef1();

		/**
		 * The meta object literal for the '<em><b>Authenticating Authority</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHN_CONTEXT_TYPE__AUTHENTICATING_AUTHORITY = eINSTANCE.getAuthnContextType_AuthenticatingAuthority();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AuthnStatementTypeImpl <em>Authn Statement Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AuthnStatementTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getAuthnStatementType()
		 * @generated
		 */
		EClass AUTHN_STATEMENT_TYPE = eINSTANCE.getAuthnStatementType();

		/**
		 * The meta object literal for the '<em><b>Subject Locality</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTHN_STATEMENT_TYPE__SUBJECT_LOCALITY = eINSTANCE.getAuthnStatementType_SubjectLocality();

		/**
		 * The meta object literal for the '<em><b>Authn Context</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTHN_STATEMENT_TYPE__AUTHN_CONTEXT = eINSTANCE.getAuthnStatementType_AuthnContext();

		/**
		 * The meta object literal for the '<em><b>Authn Instant</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHN_STATEMENT_TYPE__AUTHN_INSTANT = eINSTANCE.getAuthnStatementType_AuthnInstant();

		/**
		 * The meta object literal for the '<em><b>Session Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHN_STATEMENT_TYPE__SESSION_INDEX = eINSTANCE.getAuthnStatementType_SessionIndex();

		/**
		 * The meta object literal for the '<em><b>Session Not On Or After</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHN_STATEMENT_TYPE__SESSION_NOT_ON_OR_AFTER = eINSTANCE.getAuthnStatementType_SessionNotOnOrAfter();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AuthzDecisionStatementTypeImpl <em>Authz Decision Statement Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AuthzDecisionStatementTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getAuthzDecisionStatementType()
		 * @generated
		 */
		EClass AUTHZ_DECISION_STATEMENT_TYPE = eINSTANCE.getAuthzDecisionStatementType();

		/**
		 * The meta object literal for the '<em><b>Action</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTHZ_DECISION_STATEMENT_TYPE__ACTION = eINSTANCE.getAuthzDecisionStatementType_Action();

		/**
		 * The meta object literal for the '<em><b>Evidence</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTHZ_DECISION_STATEMENT_TYPE__EVIDENCE = eINSTANCE.getAuthzDecisionStatementType_Evidence();

		/**
		 * The meta object literal for the '<em><b>Decision</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHZ_DECISION_STATEMENT_TYPE__DECISION = eINSTANCE.getAuthzDecisionStatementType_Decision();

		/**
		 * The meta object literal for the '<em><b>Resource</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHZ_DECISION_STATEMENT_TYPE__RESOURCE = eINSTANCE.getAuthzDecisionStatementType_Resource();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.BaseIDAbstractTypeImpl <em>Base ID Abstract Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.BaseIDAbstractTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getBaseIDAbstractType()
		 * @generated
		 */
		EClass BASE_ID_ABSTRACT_TYPE = eINSTANCE.getBaseIDAbstractType();

		/**
		 * The meta object literal for the '<em><b>Name Qualifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BASE_ID_ABSTRACT_TYPE__NAME_QUALIFIER = eINSTANCE.getBaseIDAbstractType_NameQualifier();

		/**
		 * The meta object literal for the '<em><b>SP Name Qualifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BASE_ID_ABSTRACT_TYPE__SP_NAME_QUALIFIER = eINSTANCE.getBaseIDAbstractType_SPNameQualifier();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.ConditionAbstractTypeImpl <em>Condition Abstract Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.ConditionAbstractTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getConditionAbstractType()
		 * @generated
		 */
		EClass CONDITION_ABSTRACT_TYPE = eINSTANCE.getConditionAbstractType();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.ConditionsTypeImpl <em>Conditions Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.ConditionsTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getConditionsType()
		 * @generated
		 */
		EClass CONDITIONS_TYPE = eINSTANCE.getConditionsType();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONDITIONS_TYPE__GROUP = eINSTANCE.getConditionsType_Group();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITIONS_TYPE__CONDITION = eINSTANCE.getConditionsType_Condition();

		/**
		 * The meta object literal for the '<em><b>Audience Restriction</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITIONS_TYPE__AUDIENCE_RESTRICTION = eINSTANCE.getConditionsType_AudienceRestriction();

		/**
		 * The meta object literal for the '<em><b>One Time Use</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITIONS_TYPE__ONE_TIME_USE = eINSTANCE.getConditionsType_OneTimeUse();

		/**
		 * The meta object literal for the '<em><b>Proxy Restriction</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITIONS_TYPE__PROXY_RESTRICTION = eINSTANCE.getConditionsType_ProxyRestriction();

		/**
		 * The meta object literal for the '<em><b>Not Before</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONDITIONS_TYPE__NOT_BEFORE = eINSTANCE.getConditionsType_NotBefore();

		/**
		 * The meta object literal for the '<em><b>Not On Or After</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONDITIONS_TYPE__NOT_ON_OR_AFTER = eINSTANCE.getConditionsType_NotOnOrAfter();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getDocumentRoot()
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
		 * The meta object literal for the '<em><b>Action</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ACTION = eINSTANCE.getDocumentRoot_Action();

		/**
		 * The meta object literal for the '<em><b>Advice</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ADVICE = eINSTANCE.getDocumentRoot_Advice();

		/**
		 * The meta object literal for the '<em><b>Assertion</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ASSERTION = eINSTANCE.getDocumentRoot_Assertion();

		/**
		 * The meta object literal for the '<em><b>Assertion ID Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__ASSERTION_ID_REF = eINSTANCE.getDocumentRoot_AssertionIDRef();

		/**
		 * The meta object literal for the '<em><b>Assertion URI Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__ASSERTION_URI_REF = eINSTANCE.getDocumentRoot_AssertionURIRef();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ATTRIBUTE = eINSTANCE.getDocumentRoot_Attribute();

		/**
		 * The meta object literal for the '<em><b>Attribute Statement</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ATTRIBUTE_STATEMENT = eINSTANCE.getDocumentRoot_AttributeStatement();

		/**
		 * The meta object literal for the '<em><b>Attribute Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ATTRIBUTE_VALUE = eINSTANCE.getDocumentRoot_AttributeValue();

		/**
		 * The meta object literal for the '<em><b>Audience</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__AUDIENCE = eINSTANCE.getDocumentRoot_Audience();

		/**
		 * The meta object literal for the '<em><b>Audience Restriction</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__AUDIENCE_RESTRICTION = eINSTANCE.getDocumentRoot_AudienceRestriction();

		/**
		 * The meta object literal for the '<em><b>Authenticating Authority</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__AUTHENTICATING_AUTHORITY = eINSTANCE.getDocumentRoot_AuthenticatingAuthority();

		/**
		 * The meta object literal for the '<em><b>Authn Context</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__AUTHN_CONTEXT = eINSTANCE.getDocumentRoot_AuthnContext();

		/**
		 * The meta object literal for the '<em><b>Authn Context Class Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__AUTHN_CONTEXT_CLASS_REF = eINSTANCE.getDocumentRoot_AuthnContextClassRef();

		/**
		 * The meta object literal for the '<em><b>Authn Context Decl</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__AUTHN_CONTEXT_DECL = eINSTANCE.getDocumentRoot_AuthnContextDecl();

		/**
		 * The meta object literal for the '<em><b>Authn Context Decl Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__AUTHN_CONTEXT_DECL_REF = eINSTANCE.getDocumentRoot_AuthnContextDeclRef();

		/**
		 * The meta object literal for the '<em><b>Authn Statement</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__AUTHN_STATEMENT = eINSTANCE.getDocumentRoot_AuthnStatement();

		/**
		 * The meta object literal for the '<em><b>Authz Decision Statement</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__AUTHZ_DECISION_STATEMENT = eINSTANCE.getDocumentRoot_AuthzDecisionStatement();

		/**
		 * The meta object literal for the '<em><b>Base ID</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__BASE_ID = eINSTANCE.getDocumentRoot_BaseID();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__CONDITION = eINSTANCE.getDocumentRoot_Condition();

		/**
		 * The meta object literal for the '<em><b>Conditions</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__CONDITIONS = eINSTANCE.getDocumentRoot_Conditions();

		/**
		 * The meta object literal for the '<em><b>Evidence</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__EVIDENCE = eINSTANCE.getDocumentRoot_Evidence();

		/**
		 * The meta object literal for the '<em><b>Issuer</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ISSUER = eINSTANCE.getDocumentRoot_Issuer();

		/**
		 * The meta object literal for the '<em><b>Name ID</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__NAME_ID = eINSTANCE.getDocumentRoot_NameID();

		/**
		 * The meta object literal for the '<em><b>One Time Use</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ONE_TIME_USE = eINSTANCE.getDocumentRoot_OneTimeUse();

		/**
		 * The meta object literal for the '<em><b>Proxy Restriction</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__PROXY_RESTRICTION = eINSTANCE.getDocumentRoot_ProxyRestriction();

		/**
		 * The meta object literal for the '<em><b>Statement</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__STATEMENT = eINSTANCE.getDocumentRoot_Statement();

		/**
		 * The meta object literal for the '<em><b>Subject</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__SUBJECT = eINSTANCE.getDocumentRoot_Subject();

		/**
		 * The meta object literal for the '<em><b>Subject Confirmation</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__SUBJECT_CONFIRMATION = eINSTANCE.getDocumentRoot_SubjectConfirmation();

		/**
		 * The meta object literal for the '<em><b>Subject Confirmation Data</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__SUBJECT_CONFIRMATION_DATA = eINSTANCE.getDocumentRoot_SubjectConfirmationData();

		/**
		 * The meta object literal for the '<em><b>Subject Locality</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__SUBJECT_LOCALITY = eINSTANCE.getDocumentRoot_SubjectLocality();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.EvidenceTypeImpl <em>Evidence Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.EvidenceTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getEvidenceType()
		 * @generated
		 */
		EClass EVIDENCE_TYPE = eINSTANCE.getEvidenceType();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVIDENCE_TYPE__GROUP = eINSTANCE.getEvidenceType_Group();

		/**
		 * The meta object literal for the '<em><b>Assertion ID Ref</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVIDENCE_TYPE__ASSERTION_ID_REF = eINSTANCE.getEvidenceType_AssertionIDRef();

		/**
		 * The meta object literal for the '<em><b>Assertion URI Ref</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVIDENCE_TYPE__ASSERTION_URI_REF = eINSTANCE.getEvidenceType_AssertionURIRef();

		/**
		 * The meta object literal for the '<em><b>Assertion</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVIDENCE_TYPE__ASSERTION = eINSTANCE.getEvidenceType_Assertion();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.NameIDTypeImpl <em>Name ID Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.NameIDTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getNameIDType()
		 * @generated
		 */
		EClass NAME_ID_TYPE = eINSTANCE.getNameIDType();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAME_ID_TYPE__VALUE = eINSTANCE.getNameIDType_Value();

		/**
		 * The meta object literal for the '<em><b>Format</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAME_ID_TYPE__FORMAT = eINSTANCE.getNameIDType_Format();

		/**
		 * The meta object literal for the '<em><b>Name Qualifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAME_ID_TYPE__NAME_QUALIFIER = eINSTANCE.getNameIDType_NameQualifier();

		/**
		 * The meta object literal for the '<em><b>SP Name Qualifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAME_ID_TYPE__SP_NAME_QUALIFIER = eINSTANCE.getNameIDType_SPNameQualifier();

		/**
		 * The meta object literal for the '<em><b>SP Provided ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAME_ID_TYPE__SP_PROVIDED_ID = eINSTANCE.getNameIDType_SPProvidedID();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.OneTimeUseTypeImpl <em>One Time Use Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.OneTimeUseTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getOneTimeUseType()
		 * @generated
		 */
		EClass ONE_TIME_USE_TYPE = eINSTANCE.getOneTimeUseType();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.ProxyRestrictionTypeImpl <em>Proxy Restriction Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.ProxyRestrictionTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getProxyRestrictionType()
		 * @generated
		 */
		EClass PROXY_RESTRICTION_TYPE = eINSTANCE.getProxyRestrictionType();

		/**
		 * The meta object literal for the '<em><b>Audience</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROXY_RESTRICTION_TYPE__AUDIENCE = eINSTANCE.getProxyRestrictionType_Audience();

		/**
		 * The meta object literal for the '<em><b>Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROXY_RESTRICTION_TYPE__COUNT = eINSTANCE.getProxyRestrictionType_Count();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.StatementAbstractTypeImpl <em>Statement Abstract Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.StatementAbstractTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getStatementAbstractType()
		 * @generated
		 */
		EClass STATEMENT_ABSTRACT_TYPE = eINSTANCE.getStatementAbstractType();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectConfirmationDataTypeImpl <em>Subject Confirmation Data Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectConfirmationDataTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getSubjectConfirmationDataType()
		 * @generated
		 */
		EClass SUBJECT_CONFIRMATION_DATA_TYPE = eINSTANCE.getSubjectConfirmationDataType();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUBJECT_CONFIRMATION_DATA_TYPE__MIXED = eINSTANCE.getSubjectConfirmationDataType_Mixed();

		/**
		 * The meta object literal for the '<em><b>Any</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUBJECT_CONFIRMATION_DATA_TYPE__ANY = eINSTANCE.getSubjectConfirmationDataType_Any();

		/**
		 * The meta object literal for the '<em><b>Address</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUBJECT_CONFIRMATION_DATA_TYPE__ADDRESS = eINSTANCE.getSubjectConfirmationDataType_Address();

		/**
		 * The meta object literal for the '<em><b>In Response To</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUBJECT_CONFIRMATION_DATA_TYPE__IN_RESPONSE_TO = eINSTANCE.getSubjectConfirmationDataType_InResponseTo();

		/**
		 * The meta object literal for the '<em><b>Not Before</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUBJECT_CONFIRMATION_DATA_TYPE__NOT_BEFORE = eINSTANCE.getSubjectConfirmationDataType_NotBefore();

		/**
		 * The meta object literal for the '<em><b>Not On Or After</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUBJECT_CONFIRMATION_DATA_TYPE__NOT_ON_OR_AFTER = eINSTANCE.getSubjectConfirmationDataType_NotOnOrAfter();

		/**
		 * The meta object literal for the '<em><b>Recipient</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUBJECT_CONFIRMATION_DATA_TYPE__RECIPIENT = eINSTANCE.getSubjectConfirmationDataType_Recipient();

		/**
		 * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUBJECT_CONFIRMATION_DATA_TYPE__ANY_ATTRIBUTE = eINSTANCE.getSubjectConfirmationDataType_AnyAttribute();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectConfirmationTypeImpl <em>Subject Confirmation Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectConfirmationTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getSubjectConfirmationType()
		 * @generated
		 */
		EClass SUBJECT_CONFIRMATION_TYPE = eINSTANCE.getSubjectConfirmationType();

		/**
		 * The meta object literal for the '<em><b>Base ID</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBJECT_CONFIRMATION_TYPE__BASE_ID = eINSTANCE.getSubjectConfirmationType_BaseID();

		/**
		 * The meta object literal for the '<em><b>Name ID</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBJECT_CONFIRMATION_TYPE__NAME_ID = eINSTANCE.getSubjectConfirmationType_NameID();

		/**
		 * The meta object literal for the '<em><b>Subject Confirmation Data</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBJECT_CONFIRMATION_TYPE__SUBJECT_CONFIRMATION_DATA = eINSTANCE.getSubjectConfirmationType_SubjectConfirmationData();

		/**
		 * The meta object literal for the '<em><b>Method</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUBJECT_CONFIRMATION_TYPE__METHOD = eINSTANCE.getSubjectConfirmationType_Method();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectLocalityTypeImpl <em>Subject Locality Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectLocalityTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getSubjectLocalityType()
		 * @generated
		 */
		EClass SUBJECT_LOCALITY_TYPE = eINSTANCE.getSubjectLocalityType();

		/**
		 * The meta object literal for the '<em><b>Address</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUBJECT_LOCALITY_TYPE__ADDRESS = eINSTANCE.getSubjectLocalityType_Address();

		/**
		 * The meta object literal for the '<em><b>DNS Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUBJECT_LOCALITY_TYPE__DNS_NAME = eINSTANCE.getSubjectLocalityType_DNSName();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectTypeImpl <em>Subject Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getSubjectType()
		 * @generated
		 */
		EClass SUBJECT_TYPE = eINSTANCE.getSubjectType();

		/**
		 * The meta object literal for the '<em><b>Base ID</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBJECT_TYPE__BASE_ID = eINSTANCE.getSubjectType_BaseID();

		/**
		 * The meta object literal for the '<em><b>Name ID</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBJECT_TYPE__NAME_ID = eINSTANCE.getSubjectType_NameID();

		/**
		 * The meta object literal for the '<em><b>Subject Confirmation</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBJECT_TYPE__SUBJECT_CONFIRMATION = eINSTANCE.getSubjectType_SubjectConfirmation();

		/**
		 * The meta object literal for the '<em><b>Subject Confirmation1</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBJECT_TYPE__SUBJECT_CONFIRMATION1 = eINSTANCE.getSubjectType_SubjectConfirmation1();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DecisionType <em>Decision Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DecisionType
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getDecisionType()
		 * @generated
		 */
		EEnum DECISION_TYPE = eINSTANCE.getDecisionType();

		/**
		 * The meta object literal for the '<em>Decision Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DecisionType
		 * @see com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl#getDecisionTypeObject()
		 * @generated
		 */
		EDataType DECISION_TYPE_OBJECT = eINSTANCE.getDecisionTypeObject();

	}

} //AssertionPackage
