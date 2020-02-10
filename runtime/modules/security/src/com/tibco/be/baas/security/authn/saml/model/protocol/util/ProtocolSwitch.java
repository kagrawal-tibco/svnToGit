/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.baas.security.authn.saml.model.protocol.ArtifactResolveType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ArtifactResponseType;
import com.tibco.be.baas.security.authn.saml.model.protocol.AssertionIDRequestType;
import com.tibco.be.baas.security.authn.saml.model.protocol.AttributeQueryType;
import com.tibco.be.baas.security.authn.saml.model.protocol.AuthnQueryType;
import com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType;
import com.tibco.be.baas.security.authn.saml.model.protocol.AuthzDecisionQueryType;
import com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot;
import com.tibco.be.baas.security.authn.saml.model.protocol.ExtensionsType;
import com.tibco.be.baas.security.authn.saml.model.protocol.IDPEntryType;
import com.tibco.be.baas.security.authn.saml.model.protocol.IDPListType;
import com.tibco.be.baas.security.authn.saml.model.protocol.LogoutRequestType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ManageNameIDRequestType;
import com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingRequestType;
import com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingResponseType;
import com.tibco.be.baas.security.authn.saml.model.protocol.NameIDPolicyType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage;
import com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType;
import com.tibco.be.baas.security.authn.saml.model.protocol.RequestedAuthnContextType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ResponseType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ScopingType;
import com.tibco.be.baas.security.authn.saml.model.protocol.StatusCodeType;
import com.tibco.be.baas.security.authn.saml.model.protocol.StatusDetailType;
import com.tibco.be.baas.security.authn.saml.model.protocol.StatusResponseType;
import com.tibco.be.baas.security.authn.saml.model.protocol.StatusType;
import com.tibco.be.baas.security.authn.saml.model.protocol.SubjectQueryAbstractType;
import com.tibco.be.baas.security.authn.saml.model.protocol.TerminateType;

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
 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage
 * @generated
 */
public class ProtocolSwitch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ProtocolPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProtocolSwitch() {
		if (modelPackage == null) {
			modelPackage = ProtocolPackage.eINSTANCE;
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
			case ProtocolPackage.ARTIFACT_RESOLVE_TYPE: {
				ArtifactResolveType artifactResolveType = (ArtifactResolveType)theEObject;
				T result = caseArtifactResolveType(artifactResolveType);
				if (result == null) result = caseRequestAbstractType(artifactResolveType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProtocolPackage.ARTIFACT_RESPONSE_TYPE: {
				ArtifactResponseType artifactResponseType = (ArtifactResponseType)theEObject;
				T result = caseArtifactResponseType(artifactResponseType);
				if (result == null) result = caseStatusResponseType(artifactResponseType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProtocolPackage.ASSERTION_ID_REQUEST_TYPE: {
				AssertionIDRequestType assertionIDRequestType = (AssertionIDRequestType)theEObject;
				T result = caseAssertionIDRequestType(assertionIDRequestType);
				if (result == null) result = caseRequestAbstractType(assertionIDRequestType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProtocolPackage.ATTRIBUTE_QUERY_TYPE: {
				AttributeQueryType attributeQueryType = (AttributeQueryType)theEObject;
				T result = caseAttributeQueryType(attributeQueryType);
				if (result == null) result = caseSubjectQueryAbstractType(attributeQueryType);
				if (result == null) result = caseRequestAbstractType(attributeQueryType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProtocolPackage.AUTHN_QUERY_TYPE: {
				AuthnQueryType authnQueryType = (AuthnQueryType)theEObject;
				T result = caseAuthnQueryType(authnQueryType);
				if (result == null) result = caseSubjectQueryAbstractType(authnQueryType);
				if (result == null) result = caseRequestAbstractType(authnQueryType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProtocolPackage.AUTHN_REQUEST_TYPE: {
				AuthnRequestType authnRequestType = (AuthnRequestType)theEObject;
				T result = caseAuthnRequestType(authnRequestType);
				if (result == null) result = caseRequestAbstractType(authnRequestType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProtocolPackage.AUTHZ_DECISION_QUERY_TYPE: {
				AuthzDecisionQueryType authzDecisionQueryType = (AuthzDecisionQueryType)theEObject;
				T result = caseAuthzDecisionQueryType(authzDecisionQueryType);
				if (result == null) result = caseSubjectQueryAbstractType(authzDecisionQueryType);
				if (result == null) result = caseRequestAbstractType(authzDecisionQueryType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProtocolPackage.DOCUMENT_ROOT: {
				DocumentRoot documentRoot = (DocumentRoot)theEObject;
				T result = caseDocumentRoot(documentRoot);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProtocolPackage.EXTENSIONS_TYPE: {
				ExtensionsType extensionsType = (ExtensionsType)theEObject;
				T result = caseExtensionsType(extensionsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProtocolPackage.IDP_ENTRY_TYPE: {
				IDPEntryType idpEntryType = (IDPEntryType)theEObject;
				T result = caseIDPEntryType(idpEntryType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProtocolPackage.IDP_LIST_TYPE: {
				IDPListType idpListType = (IDPListType)theEObject;
				T result = caseIDPListType(idpListType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProtocolPackage.LOGOUT_REQUEST_TYPE: {
				LogoutRequestType logoutRequestType = (LogoutRequestType)theEObject;
				T result = caseLogoutRequestType(logoutRequestType);
				if (result == null) result = caseRequestAbstractType(logoutRequestType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProtocolPackage.MANAGE_NAME_ID_REQUEST_TYPE: {
				ManageNameIDRequestType manageNameIDRequestType = (ManageNameIDRequestType)theEObject;
				T result = caseManageNameIDRequestType(manageNameIDRequestType);
				if (result == null) result = caseRequestAbstractType(manageNameIDRequestType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE: {
				NameIDMappingRequestType nameIDMappingRequestType = (NameIDMappingRequestType)theEObject;
				T result = caseNameIDMappingRequestType(nameIDMappingRequestType);
				if (result == null) result = caseRequestAbstractType(nameIDMappingRequestType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProtocolPackage.NAME_ID_MAPPING_RESPONSE_TYPE: {
				NameIDMappingResponseType nameIDMappingResponseType = (NameIDMappingResponseType)theEObject;
				T result = caseNameIDMappingResponseType(nameIDMappingResponseType);
				if (result == null) result = caseStatusResponseType(nameIDMappingResponseType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProtocolPackage.NAME_ID_POLICY_TYPE: {
				NameIDPolicyType nameIDPolicyType = (NameIDPolicyType)theEObject;
				T result = caseNameIDPolicyType(nameIDPolicyType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE: {
				RequestAbstractType requestAbstractType = (RequestAbstractType)theEObject;
				T result = caseRequestAbstractType(requestAbstractType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProtocolPackage.REQUESTED_AUTHN_CONTEXT_TYPE: {
				RequestedAuthnContextType requestedAuthnContextType = (RequestedAuthnContextType)theEObject;
				T result = caseRequestedAuthnContextType(requestedAuthnContextType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProtocolPackage.RESPONSE_TYPE: {
				ResponseType responseType = (ResponseType)theEObject;
				T result = caseResponseType(responseType);
				if (result == null) result = caseStatusResponseType(responseType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProtocolPackage.SCOPING_TYPE: {
				ScopingType scopingType = (ScopingType)theEObject;
				T result = caseScopingType(scopingType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProtocolPackage.STATUS_CODE_TYPE: {
				StatusCodeType statusCodeType = (StatusCodeType)theEObject;
				T result = caseStatusCodeType(statusCodeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProtocolPackage.STATUS_DETAIL_TYPE: {
				StatusDetailType statusDetailType = (StatusDetailType)theEObject;
				T result = caseStatusDetailType(statusDetailType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProtocolPackage.STATUS_RESPONSE_TYPE: {
				StatusResponseType statusResponseType = (StatusResponseType)theEObject;
				T result = caseStatusResponseType(statusResponseType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProtocolPackage.STATUS_TYPE: {
				StatusType statusType = (StatusType)theEObject;
				T result = caseStatusType(statusType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProtocolPackage.SUBJECT_QUERY_ABSTRACT_TYPE: {
				SubjectQueryAbstractType subjectQueryAbstractType = (SubjectQueryAbstractType)theEObject;
				T result = caseSubjectQueryAbstractType(subjectQueryAbstractType);
				if (result == null) result = caseRequestAbstractType(subjectQueryAbstractType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProtocolPackage.TERMINATE_TYPE: {
				TerminateType terminateType = (TerminateType)theEObject;
				T result = caseTerminateType(terminateType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Artifact Resolve Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Artifact Resolve Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArtifactResolveType(ArtifactResolveType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Artifact Response Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Artifact Response Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArtifactResponseType(ArtifactResponseType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Assertion ID Request Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Assertion ID Request Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAssertionIDRequestType(AssertionIDRequestType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute Query Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute Query Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributeQueryType(AttributeQueryType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Authn Query Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Authn Query Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAuthnQueryType(AuthnQueryType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Authn Request Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Authn Request Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAuthnRequestType(AuthnRequestType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Authz Decision Query Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Authz Decision Query Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAuthzDecisionQueryType(AuthzDecisionQueryType object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>Extensions Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Extensions Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExtensionsType(ExtensionsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IDP Entry Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IDP Entry Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIDPEntryType(IDPEntryType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IDP List Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IDP List Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIDPListType(IDPListType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Logout Request Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Logout Request Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLogoutRequestType(LogoutRequestType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Manage Name ID Request Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Manage Name ID Request Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseManageNameIDRequestType(ManageNameIDRequestType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Name ID Mapping Request Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Name ID Mapping Request Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNameIDMappingRequestType(NameIDMappingRequestType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Name ID Mapping Response Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Name ID Mapping Response Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNameIDMappingResponseType(NameIDMappingResponseType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Name ID Policy Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Name ID Policy Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNameIDPolicyType(NameIDPolicyType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Request Abstract Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Request Abstract Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRequestAbstractType(RequestAbstractType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Requested Authn Context Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Requested Authn Context Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRequestedAuthnContextType(RequestedAuthnContextType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Response Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Response Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseResponseType(ResponseType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Scoping Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Scoping Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseScopingType(ScopingType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Status Code Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Status Code Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStatusCodeType(StatusCodeType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Status Detail Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Status Detail Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStatusDetailType(StatusDetailType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Status Response Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Status Response Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStatusResponseType(StatusResponseType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Status Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Status Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStatusType(StatusType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Subject Query Abstract Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Subject Query Abstract Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubjectQueryAbstractType(SubjectQueryAbstractType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Terminate Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Terminate Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTerminateType(TerminateType object) {
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

} //ProtocolSwitch
