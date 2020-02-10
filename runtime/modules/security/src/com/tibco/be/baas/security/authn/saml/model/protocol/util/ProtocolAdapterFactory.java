/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
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
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage
 * @generated
 */
public class ProtocolAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ProtocolPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProtocolAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ProtocolPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProtocolSwitch<Adapter> modelSwitch =
		new ProtocolSwitch<Adapter>() {
			@Override
			public Adapter caseArtifactResolveType(ArtifactResolveType object) {
				return createArtifactResolveTypeAdapter();
			}
			@Override
			public Adapter caseArtifactResponseType(ArtifactResponseType object) {
				return createArtifactResponseTypeAdapter();
			}
			@Override
			public Adapter caseAssertionIDRequestType(AssertionIDRequestType object) {
				return createAssertionIDRequestTypeAdapter();
			}
			@Override
			public Adapter caseAttributeQueryType(AttributeQueryType object) {
				return createAttributeQueryTypeAdapter();
			}
			@Override
			public Adapter caseAuthnQueryType(AuthnQueryType object) {
				return createAuthnQueryTypeAdapter();
			}
			@Override
			public Adapter caseAuthnRequestType(AuthnRequestType object) {
				return createAuthnRequestTypeAdapter();
			}
			@Override
			public Adapter caseAuthzDecisionQueryType(AuthzDecisionQueryType object) {
				return createAuthzDecisionQueryTypeAdapter();
			}
			@Override
			public Adapter caseDocumentRoot(DocumentRoot object) {
				return createDocumentRootAdapter();
			}
			@Override
			public Adapter caseExtensionsType(ExtensionsType object) {
				return createExtensionsTypeAdapter();
			}
			@Override
			public Adapter caseIDPEntryType(IDPEntryType object) {
				return createIDPEntryTypeAdapter();
			}
			@Override
			public Adapter caseIDPListType(IDPListType object) {
				return createIDPListTypeAdapter();
			}
			@Override
			public Adapter caseLogoutRequestType(LogoutRequestType object) {
				return createLogoutRequestTypeAdapter();
			}
			@Override
			public Adapter caseManageNameIDRequestType(ManageNameIDRequestType object) {
				return createManageNameIDRequestTypeAdapter();
			}
			@Override
			public Adapter caseNameIDMappingRequestType(NameIDMappingRequestType object) {
				return createNameIDMappingRequestTypeAdapter();
			}
			@Override
			public Adapter caseNameIDMappingResponseType(NameIDMappingResponseType object) {
				return createNameIDMappingResponseTypeAdapter();
			}
			@Override
			public Adapter caseNameIDPolicyType(NameIDPolicyType object) {
				return createNameIDPolicyTypeAdapter();
			}
			@Override
			public Adapter caseRequestAbstractType(RequestAbstractType object) {
				return createRequestAbstractTypeAdapter();
			}
			@Override
			public Adapter caseRequestedAuthnContextType(RequestedAuthnContextType object) {
				return createRequestedAuthnContextTypeAdapter();
			}
			@Override
			public Adapter caseResponseType(ResponseType object) {
				return createResponseTypeAdapter();
			}
			@Override
			public Adapter caseScopingType(ScopingType object) {
				return createScopingTypeAdapter();
			}
			@Override
			public Adapter caseStatusCodeType(StatusCodeType object) {
				return createStatusCodeTypeAdapter();
			}
			@Override
			public Adapter caseStatusDetailType(StatusDetailType object) {
				return createStatusDetailTypeAdapter();
			}
			@Override
			public Adapter caseStatusResponseType(StatusResponseType object) {
				return createStatusResponseTypeAdapter();
			}
			@Override
			public Adapter caseStatusType(StatusType object) {
				return createStatusTypeAdapter();
			}
			@Override
			public Adapter caseSubjectQueryAbstractType(SubjectQueryAbstractType object) {
				return createSubjectQueryAbstractTypeAdapter();
			}
			@Override
			public Adapter caseTerminateType(TerminateType object) {
				return createTerminateTypeAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ArtifactResolveType <em>Artifact Resolve Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ArtifactResolveType
	 * @generated
	 */
	public Adapter createArtifactResolveTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ArtifactResponseType <em>Artifact Response Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ArtifactResponseType
	 * @generated
	 */
	public Adapter createArtifactResponseTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AssertionIDRequestType <em>Assertion ID Request Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AssertionIDRequestType
	 * @generated
	 */
	public Adapter createAssertionIDRequestTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AttributeQueryType <em>Attribute Query Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AttributeQueryType
	 * @generated
	 */
	public Adapter createAttributeQueryTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnQueryType <em>Authn Query Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthnQueryType
	 * @generated
	 */
	public Adapter createAuthnQueryTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType <em>Authn Request Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType
	 * @generated
	 */
	public Adapter createAuthnRequestTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthzDecisionQueryType <em>Authz Decision Query Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthzDecisionQueryType
	 * @generated
	 */
	public Adapter createAuthzDecisionQueryTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot
	 * @generated
	 */
	public Adapter createDocumentRootAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ExtensionsType <em>Extensions Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ExtensionsType
	 * @generated
	 */
	public Adapter createExtensionsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.IDPEntryType <em>IDP Entry Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.IDPEntryType
	 * @generated
	 */
	public Adapter createIDPEntryTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.IDPListType <em>IDP List Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.IDPListType
	 * @generated
	 */
	public Adapter createIDPListTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.LogoutRequestType <em>Logout Request Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.LogoutRequestType
	 * @generated
	 */
	public Adapter createLogoutRequestTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ManageNameIDRequestType <em>Manage Name ID Request Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ManageNameIDRequestType
	 * @generated
	 */
	public Adapter createManageNameIDRequestTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingRequestType <em>Name ID Mapping Request Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingRequestType
	 * @generated
	 */
	public Adapter createNameIDMappingRequestTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingResponseType <em>Name ID Mapping Response Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingResponseType
	 * @generated
	 */
	public Adapter createNameIDMappingResponseTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDPolicyType <em>Name ID Policy Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.NameIDPolicyType
	 * @generated
	 */
	public Adapter createNameIDPolicyTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType <em>Request Abstract Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType
	 * @generated
	 */
	public Adapter createRequestAbstractTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestedAuthnContextType <em>Requested Authn Context Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.RequestedAuthnContextType
	 * @generated
	 */
	public Adapter createRequestedAuthnContextTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ResponseType <em>Response Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ResponseType
	 * @generated
	 */
	public Adapter createResponseTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ScopingType <em>Scoping Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ScopingType
	 * @generated
	 */
	public Adapter createScopingTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusCodeType <em>Status Code Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.StatusCodeType
	 * @generated
	 */
	public Adapter createStatusCodeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusDetailType <em>Status Detail Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.StatusDetailType
	 * @generated
	 */
	public Adapter createStatusDetailTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusResponseType <em>Status Response Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.StatusResponseType
	 * @generated
	 */
	public Adapter createStatusResponseTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusType <em>Status Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.StatusType
	 * @generated
	 */
	public Adapter createStatusTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.SubjectQueryAbstractType <em>Subject Query Abstract Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.SubjectQueryAbstractType
	 * @generated
	 */
	public Adapter createSubjectQueryAbstractTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.protocol.TerminateType <em>Terminate Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.TerminateType
	 * @generated
	 */
	public Adapter createTerminateTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //ProtocolAdapterFactory
