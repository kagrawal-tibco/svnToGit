/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.baas.security.authn.saml.model.assertion.ActionType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AdviceType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage;
import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AttributeStatementType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AudienceRestrictionType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AuthzDecisionStatementType;
import com.tibco.be.baas.security.authn.saml.model.assertion.BaseIDAbstractType;
import com.tibco.be.baas.security.authn.saml.model.assertion.ConditionAbstractType;
import com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType;
import com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot;
import com.tibco.be.baas.security.authn.saml.model.assertion.EvidenceType;
import com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType;
import com.tibco.be.baas.security.authn.saml.model.assertion.OneTimeUseType;
import com.tibco.be.baas.security.authn.saml.model.assertion.ProxyRestrictionType;
import com.tibco.be.baas.security.authn.saml.model.assertion.StatementAbstractType;
import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType;
import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType;
import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectLocalityType;
import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage
 * @generated
 */
public class AssertionSwitch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static AssertionPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AssertionSwitch() {
		if (modelPackage == null) {
			modelPackage = AssertionPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case AssertionPackage.ACTION_TYPE: {
				ActionType actionType = (ActionType)theEObject;
				T result = caseActionType(actionType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AssertionPackage.ADVICE_TYPE: {
				AdviceType adviceType = (AdviceType)theEObject;
				T result = caseAdviceType(adviceType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AssertionPackage.ASSERTION_TYPE: {
				AssertionType assertionType = (AssertionType)theEObject;
				T result = caseAssertionType(assertionType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AssertionPackage.ATTRIBUTE_STATEMENT_TYPE: {
				AttributeStatementType attributeStatementType = (AttributeStatementType)theEObject;
				T result = caseAttributeStatementType(attributeStatementType);
				if (result == null) result = caseStatementAbstractType(attributeStatementType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AssertionPackage.ATTRIBUTE_TYPE: {
				AttributeType attributeType = (AttributeType)theEObject;
				T result = caseAttributeType(attributeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AssertionPackage.AUDIENCE_RESTRICTION_TYPE: {
				AudienceRestrictionType audienceRestrictionType = (AudienceRestrictionType)theEObject;
				T result = caseAudienceRestrictionType(audienceRestrictionType);
				if (result == null) result = caseConditionAbstractType(audienceRestrictionType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AssertionPackage.AUTHN_CONTEXT_TYPE: {
				AuthnContextType authnContextType = (AuthnContextType)theEObject;
				T result = caseAuthnContextType(authnContextType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AssertionPackage.AUTHN_STATEMENT_TYPE: {
				AuthnStatementType authnStatementType = (AuthnStatementType)theEObject;
				T result = caseAuthnStatementType(authnStatementType);
				if (result == null) result = caseStatementAbstractType(authnStatementType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE: {
				AuthzDecisionStatementType authzDecisionStatementType = (AuthzDecisionStatementType)theEObject;
				T result = caseAuthzDecisionStatementType(authzDecisionStatementType);
				if (result == null) result = caseStatementAbstractType(authzDecisionStatementType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AssertionPackage.BASE_ID_ABSTRACT_TYPE: {
				BaseIDAbstractType baseIDAbstractType = (BaseIDAbstractType)theEObject;
				T result = caseBaseIDAbstractType(baseIDAbstractType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AssertionPackage.CONDITION_ABSTRACT_TYPE: {
				ConditionAbstractType conditionAbstractType = (ConditionAbstractType)theEObject;
				T result = caseConditionAbstractType(conditionAbstractType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AssertionPackage.CONDITIONS_TYPE: {
				ConditionsType conditionsType = (ConditionsType)theEObject;
				T result = caseConditionsType(conditionsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AssertionPackage.DOCUMENT_ROOT: {
				DocumentRoot documentRoot = (DocumentRoot)theEObject;
				T result = caseDocumentRoot(documentRoot);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AssertionPackage.EVIDENCE_TYPE: {
				EvidenceType evidenceType = (EvidenceType)theEObject;
				T result = caseEvidenceType(evidenceType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AssertionPackage.NAME_ID_TYPE: {
				NameIDType nameIDType = (NameIDType)theEObject;
				T result = caseNameIDType(nameIDType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AssertionPackage.ONE_TIME_USE_TYPE: {
				OneTimeUseType oneTimeUseType = (OneTimeUseType)theEObject;
				T result = caseOneTimeUseType(oneTimeUseType);
				if (result == null) result = caseConditionAbstractType(oneTimeUseType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AssertionPackage.PROXY_RESTRICTION_TYPE: {
				ProxyRestrictionType proxyRestrictionType = (ProxyRestrictionType)theEObject;
				T result = caseProxyRestrictionType(proxyRestrictionType);
				if (result == null) result = caseConditionAbstractType(proxyRestrictionType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AssertionPackage.STATEMENT_ABSTRACT_TYPE: {
				StatementAbstractType statementAbstractType = (StatementAbstractType)theEObject;
				T result = caseStatementAbstractType(statementAbstractType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE: {
				SubjectConfirmationDataType subjectConfirmationDataType = (SubjectConfirmationDataType)theEObject;
				T result = caseSubjectConfirmationDataType(subjectConfirmationDataType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AssertionPackage.SUBJECT_CONFIRMATION_TYPE: {
				SubjectConfirmationType subjectConfirmationType = (SubjectConfirmationType)theEObject;
				T result = caseSubjectConfirmationType(subjectConfirmationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AssertionPackage.SUBJECT_LOCALITY_TYPE: {
				SubjectLocalityType subjectLocalityType = (SubjectLocalityType)theEObject;
				T result = caseSubjectLocalityType(subjectLocalityType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AssertionPackage.SUBJECT_TYPE: {
				SubjectType subjectType = (SubjectType)theEObject;
				T result = caseSubjectType(subjectType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Action Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Action Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseActionType(ActionType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Advice Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Advice Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAdviceType(AdviceType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAssertionType(AssertionType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute Statement Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute Statement Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributeStatementType(AttributeStatementType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributeType(AttributeType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Audience Restriction Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Audience Restriction Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAudienceRestrictionType(AudienceRestrictionType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Authn Context Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Authn Context Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAuthnContextType(AuthnContextType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Authn Statement Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Authn Statement Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAuthnStatementType(AuthnStatementType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Authz Decision Statement Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Authz Decision Statement Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAuthzDecisionStatementType(AuthzDecisionStatementType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Base ID Abstract Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Base ID Abstract Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBaseIDAbstractType(BaseIDAbstractType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Condition Abstract Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Condition Abstract Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConditionAbstractType(ConditionAbstractType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Conditions Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Conditions Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConditionsType(ConditionsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Document Root</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDocumentRoot(DocumentRoot object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Evidence Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Evidence Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEvidenceType(EvidenceType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Name ID Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Name ID Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNameIDType(NameIDType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>One Time Use Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>One Time Use Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOneTimeUseType(OneTimeUseType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Proxy Restriction Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Proxy Restriction Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProxyRestrictionType(ProxyRestrictionType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Statement Abstract Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Statement Abstract Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStatementAbstractType(StatementAbstractType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Subject Confirmation Data Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Subject Confirmation Data Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubjectConfirmationDataType(SubjectConfirmationDataType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Subject Confirmation Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Subject Confirmation Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubjectConfirmationType(SubjectConfirmationType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Subject Locality Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Subject Locality Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubjectLocalityType(SubjectLocalityType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Subject Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Subject Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubjectType(SubjectType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public T defaultCase(EObject object) {
		return null;
	}

} //AssertionSwitch
