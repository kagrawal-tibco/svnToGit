/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.be.baas.security.authn.saml.model.assertion.ActionType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AdviceType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionFactory;
import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage;
import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AttributeStatementType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AudienceRestrictionType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AuthzDecisionStatementType;
import com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType;
import com.tibco.be.baas.security.authn.saml.model.assertion.DecisionType;
import com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot;
import com.tibco.be.baas.security.authn.saml.model.assertion.EvidenceType;
import com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType;
import com.tibco.be.baas.security.authn.saml.model.assertion.OneTimeUseType;
import com.tibco.be.baas.security.authn.saml.model.assertion.ProxyRestrictionType;
import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType;
import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType;
import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectLocalityType;
import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AssertionFactoryImpl extends EFactoryImpl implements AssertionFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static AssertionFactory init() {
		try {
			AssertionFactory theAssertionFactory = (AssertionFactory)EPackage.Registry.INSTANCE.getEFactory("urn:oasis:names:tc:SAML:2.0:assertion"); 
			if (theAssertionFactory != null) {
				return theAssertionFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new AssertionFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AssertionFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case AssertionPackage.ACTION_TYPE: return createActionType();
			case AssertionPackage.ADVICE_TYPE: return createAdviceType();
			case AssertionPackage.ASSERTION_TYPE: return createAssertionType();
			case AssertionPackage.ATTRIBUTE_STATEMENT_TYPE: return createAttributeStatementType();
			case AssertionPackage.ATTRIBUTE_TYPE: return createAttributeType();
			case AssertionPackage.AUDIENCE_RESTRICTION_TYPE: return createAudienceRestrictionType();
			case AssertionPackage.AUTHN_CONTEXT_TYPE: return createAuthnContextType();
			case AssertionPackage.AUTHN_STATEMENT_TYPE: return createAuthnStatementType();
			case AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE: return createAuthzDecisionStatementType();
			case AssertionPackage.CONDITIONS_TYPE: return createConditionsType();
			case AssertionPackage.DOCUMENT_ROOT: return createDocumentRoot();
			case AssertionPackage.EVIDENCE_TYPE: return createEvidenceType();
			case AssertionPackage.NAME_ID_TYPE: return createNameIDType();
			case AssertionPackage.ONE_TIME_USE_TYPE: return createOneTimeUseType();
			case AssertionPackage.PROXY_RESTRICTION_TYPE: return createProxyRestrictionType();
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE: return createSubjectConfirmationDataType();
			case AssertionPackage.SUBJECT_CONFIRMATION_TYPE: return createSubjectConfirmationType();
			case AssertionPackage.SUBJECT_LOCALITY_TYPE: return createSubjectLocalityType();
			case AssertionPackage.SUBJECT_TYPE: return createSubjectType();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case AssertionPackage.DECISION_TYPE:
				return createDecisionTypeFromString(eDataType, initialValue);
			case AssertionPackage.DECISION_TYPE_OBJECT:
				return createDecisionTypeObjectFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case AssertionPackage.DECISION_TYPE:
				return convertDecisionTypeToString(eDataType, instanceValue);
			case AssertionPackage.DECISION_TYPE_OBJECT:
				return convertDecisionTypeObjectToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActionType createActionType() {
		ActionTypeImpl actionType = new ActionTypeImpl();
		return actionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdviceType createAdviceType() {
		AdviceTypeImpl adviceType = new AdviceTypeImpl();
		return adviceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AssertionType createAssertionType() {
		AssertionTypeImpl assertionType = new AssertionTypeImpl();
		return assertionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeStatementType createAttributeStatementType() {
		AttributeStatementTypeImpl attributeStatementType = new AttributeStatementTypeImpl();
		return attributeStatementType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeType createAttributeType() {
		AttributeTypeImpl attributeType = new AttributeTypeImpl();
		return attributeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AudienceRestrictionType createAudienceRestrictionType() {
		AudienceRestrictionTypeImpl audienceRestrictionType = new AudienceRestrictionTypeImpl();
		return audienceRestrictionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthnContextType createAuthnContextType() {
		AuthnContextTypeImpl authnContextType = new AuthnContextTypeImpl();
		return authnContextType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthnStatementType createAuthnStatementType() {
		AuthnStatementTypeImpl authnStatementType = new AuthnStatementTypeImpl();
		return authnStatementType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthzDecisionStatementType createAuthzDecisionStatementType() {
		AuthzDecisionStatementTypeImpl authzDecisionStatementType = new AuthzDecisionStatementTypeImpl();
		return authzDecisionStatementType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConditionsType createConditionsType() {
		ConditionsTypeImpl conditionsType = new ConditionsTypeImpl();
		return conditionsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentRoot createDocumentRoot() {
		DocumentRootImpl documentRoot = new DocumentRootImpl();
		return documentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EvidenceType createEvidenceType() {
		EvidenceTypeImpl evidenceType = new EvidenceTypeImpl();
		return evidenceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NameIDType createNameIDType() {
		NameIDTypeImpl nameIDType = new NameIDTypeImpl();
		return nameIDType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OneTimeUseType createOneTimeUseType() {
		OneTimeUseTypeImpl oneTimeUseType = new OneTimeUseTypeImpl();
		return oneTimeUseType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProxyRestrictionType createProxyRestrictionType() {
		ProxyRestrictionTypeImpl proxyRestrictionType = new ProxyRestrictionTypeImpl();
		return proxyRestrictionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SubjectConfirmationDataType createSubjectConfirmationDataType() {
		SubjectConfirmationDataTypeImpl subjectConfirmationDataType = new SubjectConfirmationDataTypeImpl();
		return subjectConfirmationDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SubjectConfirmationType createSubjectConfirmationType() {
		SubjectConfirmationTypeImpl subjectConfirmationType = new SubjectConfirmationTypeImpl();
		return subjectConfirmationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SubjectLocalityType createSubjectLocalityType() {
		SubjectLocalityTypeImpl subjectLocalityType = new SubjectLocalityTypeImpl();
		return subjectLocalityType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SubjectType createSubjectType() {
		SubjectTypeImpl subjectType = new SubjectTypeImpl();
		return subjectType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DecisionType createDecisionTypeFromString(EDataType eDataType, String initialValue) {
		DecisionType result = DecisionType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDecisionTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DecisionType createDecisionTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createDecisionTypeFromString(AssertionPackage.Literals.DECISION_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDecisionTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertDecisionTypeToString(AssertionPackage.Literals.DECISION_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AssertionPackage getAssertionPackage() {
		return (AssertionPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static AssertionPackage getPackage() {
		return AssertionPackage.eINSTANCE;
	}

} //AssertionFactoryImpl
