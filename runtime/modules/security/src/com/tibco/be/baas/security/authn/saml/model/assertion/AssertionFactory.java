/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage
 * @generated
 */
public interface AssertionFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AssertionFactory eINSTANCE = com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Action Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Action Type</em>'.
	 * @generated
	 */
	ActionType createActionType();

	/**
	 * Returns a new object of class '<em>Advice Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Advice Type</em>'.
	 * @generated
	 */
	AdviceType createAdviceType();

	/**
	 * Returns a new object of class '<em>Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Type</em>'.
	 * @generated
	 */
	AssertionType createAssertionType();

	/**
	 * Returns a new object of class '<em>Attribute Statement Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute Statement Type</em>'.
	 * @generated
	 */
	AttributeStatementType createAttributeStatementType();

	/**
	 * Returns a new object of class '<em>Attribute Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute Type</em>'.
	 * @generated
	 */
	AttributeType createAttributeType();

	/**
	 * Returns a new object of class '<em>Audience Restriction Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Audience Restriction Type</em>'.
	 * @generated
	 */
	AudienceRestrictionType createAudienceRestrictionType();

	/**
	 * Returns a new object of class '<em>Authn Context Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Authn Context Type</em>'.
	 * @generated
	 */
	AuthnContextType createAuthnContextType();

	/**
	 * Returns a new object of class '<em>Authn Statement Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Authn Statement Type</em>'.
	 * @generated
	 */
	AuthnStatementType createAuthnStatementType();

	/**
	 * Returns a new object of class '<em>Authz Decision Statement Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Authz Decision Statement Type</em>'.
	 * @generated
	 */
	AuthzDecisionStatementType createAuthzDecisionStatementType();

	/**
	 * Returns a new object of class '<em>Conditions Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Conditions Type</em>'.
	 * @generated
	 */
	ConditionsType createConditionsType();

	/**
	 * Returns a new object of class '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Document Root</em>'.
	 * @generated
	 */
	DocumentRoot createDocumentRoot();

	/**
	 * Returns a new object of class '<em>Evidence Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Evidence Type</em>'.
	 * @generated
	 */
	EvidenceType createEvidenceType();

	/**
	 * Returns a new object of class '<em>Name ID Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Name ID Type</em>'.
	 * @generated
	 */
	NameIDType createNameIDType();

	/**
	 * Returns a new object of class '<em>One Time Use Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>One Time Use Type</em>'.
	 * @generated
	 */
	OneTimeUseType createOneTimeUseType();

	/**
	 * Returns a new object of class '<em>Proxy Restriction Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Proxy Restriction Type</em>'.
	 * @generated
	 */
	ProxyRestrictionType createProxyRestrictionType();

	/**
	 * Returns a new object of class '<em>Subject Confirmation Data Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Subject Confirmation Data Type</em>'.
	 * @generated
	 */
	SubjectConfirmationDataType createSubjectConfirmationDataType();

	/**
	 * Returns a new object of class '<em>Subject Confirmation Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Subject Confirmation Type</em>'.
	 * @generated
	 */
	SubjectConfirmationType createSubjectConfirmationType();

	/**
	 * Returns a new object of class '<em>Subject Locality Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Subject Locality Type</em>'.
	 * @generated
	 */
	SubjectLocalityType createSubjectLocalityType();

	/**
	 * Returns a new object of class '<em>Subject Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Subject Type</em>'.
	 * @generated
	 */
	SubjectType createSubjectType();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	AssertionPackage getAssertionPackage();

} //AssertionFactory
