/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.be.baas.security.authn.saml.model.protocol.ArtifactResolveType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ArtifactResponseType;
import com.tibco.be.baas.security.authn.saml.model.protocol.AssertionIDRequestType;
import com.tibco.be.baas.security.authn.saml.model.protocol.AttributeQueryType;
import com.tibco.be.baas.security.authn.saml.model.protocol.AuthnContextComparisonType;
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
import com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolFactory;
import com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage;
import com.tibco.be.baas.security.authn.saml.model.protocol.RequestedAuthnContextType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ResponseType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ScopingType;
import com.tibco.be.baas.security.authn.saml.model.protocol.StatusCodeType;
import com.tibco.be.baas.security.authn.saml.model.protocol.StatusDetailType;
import com.tibco.be.baas.security.authn.saml.model.protocol.StatusResponseType;
import com.tibco.be.baas.security.authn.saml.model.protocol.StatusType;
import com.tibco.be.baas.security.authn.saml.model.protocol.TerminateType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ProtocolFactoryImpl extends EFactoryImpl implements ProtocolFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ProtocolFactory init() {
		try {
			ProtocolFactory theProtocolFactory = (ProtocolFactory)EPackage.Registry.INSTANCE.getEFactory("urn:oasis:names:tc:SAML:2.0:protocol"); 
			if (theProtocolFactory != null) {
				return theProtocolFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ProtocolFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProtocolFactoryImpl() {
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
			case ProtocolPackage.ARTIFACT_RESOLVE_TYPE: return createArtifactResolveType();
			case ProtocolPackage.ARTIFACT_RESPONSE_TYPE: return createArtifactResponseType();
			case ProtocolPackage.ASSERTION_ID_REQUEST_TYPE: return createAssertionIDRequestType();
			case ProtocolPackage.ATTRIBUTE_QUERY_TYPE: return createAttributeQueryType();
			case ProtocolPackage.AUTHN_QUERY_TYPE: return createAuthnQueryType();
			case ProtocolPackage.AUTHN_REQUEST_TYPE: return createAuthnRequestType();
			case ProtocolPackage.AUTHZ_DECISION_QUERY_TYPE: return createAuthzDecisionQueryType();
			case ProtocolPackage.DOCUMENT_ROOT: return createDocumentRoot();
			case ProtocolPackage.EXTENSIONS_TYPE: return createExtensionsType();
			case ProtocolPackage.IDP_ENTRY_TYPE: return createIDPEntryType();
			case ProtocolPackage.IDP_LIST_TYPE: return createIDPListType();
			case ProtocolPackage.LOGOUT_REQUEST_TYPE: return createLogoutRequestType();
			case ProtocolPackage.MANAGE_NAME_ID_REQUEST_TYPE: return createManageNameIDRequestType();
			case ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE: return createNameIDMappingRequestType();
			case ProtocolPackage.NAME_ID_MAPPING_RESPONSE_TYPE: return createNameIDMappingResponseType();
			case ProtocolPackage.NAME_ID_POLICY_TYPE: return createNameIDPolicyType();
			case ProtocolPackage.REQUESTED_AUTHN_CONTEXT_TYPE: return createRequestedAuthnContextType();
			case ProtocolPackage.RESPONSE_TYPE: return createResponseType();
			case ProtocolPackage.SCOPING_TYPE: return createScopingType();
			case ProtocolPackage.STATUS_CODE_TYPE: return createStatusCodeType();
			case ProtocolPackage.STATUS_DETAIL_TYPE: return createStatusDetailType();
			case ProtocolPackage.STATUS_RESPONSE_TYPE: return createStatusResponseType();
			case ProtocolPackage.STATUS_TYPE: return createStatusType();
			case ProtocolPackage.TERMINATE_TYPE: return createTerminateType();
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
			case ProtocolPackage.AUTHN_CONTEXT_COMPARISON_TYPE:
				return createAuthnContextComparisonTypeFromString(eDataType, initialValue);
			case ProtocolPackage.AUTHN_CONTEXT_COMPARISON_TYPE_OBJECT:
				return createAuthnContextComparisonTypeObjectFromString(eDataType, initialValue);
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
			case ProtocolPackage.AUTHN_CONTEXT_COMPARISON_TYPE:
				return convertAuthnContextComparisonTypeToString(eDataType, instanceValue);
			case ProtocolPackage.AUTHN_CONTEXT_COMPARISON_TYPE_OBJECT:
				return convertAuthnContextComparisonTypeObjectToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArtifactResolveType createArtifactResolveType() {
		ArtifactResolveTypeImpl artifactResolveType = new ArtifactResolveTypeImpl();
		return artifactResolveType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArtifactResponseType createArtifactResponseType() {
		ArtifactResponseTypeImpl artifactResponseType = new ArtifactResponseTypeImpl();
		return artifactResponseType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AssertionIDRequestType createAssertionIDRequestType() {
		AssertionIDRequestTypeImpl assertionIDRequestType = new AssertionIDRequestTypeImpl();
		return assertionIDRequestType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeQueryType createAttributeQueryType() {
		AttributeQueryTypeImpl attributeQueryType = new AttributeQueryTypeImpl();
		return attributeQueryType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthnQueryType createAuthnQueryType() {
		AuthnQueryTypeImpl authnQueryType = new AuthnQueryTypeImpl();
		return authnQueryType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthnRequestType createAuthnRequestType() {
		AuthnRequestTypeImpl authnRequestType = new AuthnRequestTypeImpl();
		return authnRequestType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthzDecisionQueryType createAuthzDecisionQueryType() {
		AuthzDecisionQueryTypeImpl authzDecisionQueryType = new AuthzDecisionQueryTypeImpl();
		return authzDecisionQueryType;
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
	public ExtensionsType createExtensionsType() {
		ExtensionsTypeImpl extensionsType = new ExtensionsTypeImpl();
		return extensionsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IDPEntryType createIDPEntryType() {
		IDPEntryTypeImpl idpEntryType = new IDPEntryTypeImpl();
		return idpEntryType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IDPListType createIDPListType() {
		IDPListTypeImpl idpListType = new IDPListTypeImpl();
		return idpListType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LogoutRequestType createLogoutRequestType() {
		LogoutRequestTypeImpl logoutRequestType = new LogoutRequestTypeImpl();
		return logoutRequestType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ManageNameIDRequestType createManageNameIDRequestType() {
		ManageNameIDRequestTypeImpl manageNameIDRequestType = new ManageNameIDRequestTypeImpl();
		return manageNameIDRequestType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NameIDMappingRequestType createNameIDMappingRequestType() {
		NameIDMappingRequestTypeImpl nameIDMappingRequestType = new NameIDMappingRequestTypeImpl();
		return nameIDMappingRequestType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NameIDMappingResponseType createNameIDMappingResponseType() {
		NameIDMappingResponseTypeImpl nameIDMappingResponseType = new NameIDMappingResponseTypeImpl();
		return nameIDMappingResponseType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NameIDPolicyType createNameIDPolicyType() {
		NameIDPolicyTypeImpl nameIDPolicyType = new NameIDPolicyTypeImpl();
		return nameIDPolicyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequestedAuthnContextType createRequestedAuthnContextType() {
		RequestedAuthnContextTypeImpl requestedAuthnContextType = new RequestedAuthnContextTypeImpl();
		return requestedAuthnContextType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResponseType createResponseType() {
		ResponseTypeImpl responseType = new ResponseTypeImpl();
		return responseType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScopingType createScopingType() {
		ScopingTypeImpl scopingType = new ScopingTypeImpl();
		return scopingType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatusCodeType createStatusCodeType() {
		StatusCodeTypeImpl statusCodeType = new StatusCodeTypeImpl();
		return statusCodeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatusDetailType createStatusDetailType() {
		StatusDetailTypeImpl statusDetailType = new StatusDetailTypeImpl();
		return statusDetailType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatusResponseType createStatusResponseType() {
		StatusResponseTypeImpl statusResponseType = new StatusResponseTypeImpl();
		return statusResponseType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatusType createStatusType() {
		StatusTypeImpl statusType = new StatusTypeImpl();
		return statusType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TerminateType createTerminateType() {
		TerminateTypeImpl terminateType = new TerminateTypeImpl();
		return terminateType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthnContextComparisonType createAuthnContextComparisonTypeFromString(EDataType eDataType, String initialValue) {
		AuthnContextComparisonType result = AuthnContextComparisonType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAuthnContextComparisonTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthnContextComparisonType createAuthnContextComparisonTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createAuthnContextComparisonTypeFromString(ProtocolPackage.Literals.AUTHN_CONTEXT_COMPARISON_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAuthnContextComparisonTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertAuthnContextComparisonTypeToString(ProtocolPackage.Literals.AUTHN_CONTEXT_COMPARISON_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProtocolPackage getProtocolPackage() {
		return (ProtocolPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ProtocolPackage getPackage() {
		return ProtocolPackage.eINSTANCE;
	}

} //ProtocolFactoryImpl
