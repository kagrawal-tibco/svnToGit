/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

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
 * An implementation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getArtifact <em>Artifact</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getArtifactResolve <em>Artifact Resolve</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getArtifactResponse <em>Artifact Response</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getAssertionIDRequest <em>Assertion ID Request</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getAttributeQuery <em>Attribute Query</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getAuthnQuery <em>Authn Query</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getAuthnRequest <em>Authn Request</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getAuthzDecisionQuery <em>Authz Decision Query</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getGetComplete <em>Get Complete</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getIDPEntry <em>IDP Entry</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getIDPList <em>IDP List</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getLogoutRequest <em>Logout Request</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getLogoutResponse <em>Logout Response</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getManageNameIDRequest <em>Manage Name ID Request</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getManageNameIDResponse <em>Manage Name ID Response</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getNameIDMappingRequest <em>Name ID Mapping Request</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getNameIDMappingResponse <em>Name ID Mapping Response</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getNameIDPolicy <em>Name ID Policy</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getNewID <em>New ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getRequestedAuthnContext <em>Requested Authn Context</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getRequesterID <em>Requester ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getResponse <em>Response</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getScoping <em>Scoping</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getSessionIndex <em>Session Index</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getStatus <em>Status</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getStatusCode <em>Status Code</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getStatusDetail <em>Status Detail</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getStatusMessage <em>Status Message</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getSubjectQuery <em>Subject Query</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.DocumentRootImpl#getTerminate <em>Terminate</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DocumentRootImpl extends EObjectImpl implements DocumentRoot {
	/**
	 * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMixed()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap mixed;

	/**
	 * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXMLNSPrefixMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xMLNSPrefixMap;

	/**
	 * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXSISchemaLocation()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xSISchemaLocation;

	/**
	 * The default value of the '{@link #getArtifact() <em>Artifact</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArtifact()
	 * @generated
	 * @ordered
	 */
	protected static final String ARTIFACT_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getGetComplete() <em>Get Complete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGetComplete()
	 * @generated
	 * @ordered
	 */
	protected static final String GET_COMPLETE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getNewID() <em>New ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNewID()
	 * @generated
	 * @ordered
	 */
	protected static final String NEW_ID_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getRequesterID() <em>Requester ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequesterID()
	 * @generated
	 * @ordered
	 */
	protected static final String REQUESTER_ID_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSessionIndex() <em>Session Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSessionIndex()
	 * @generated
	 * @ordered
	 */
	protected static final String SESSION_INDEX_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getStatusMessage() <em>Status Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatusMessage()
	 * @generated
	 * @ordered
	 */
	protected static final String STATUS_MESSAGE_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DocumentRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProtocolPackage.Literals.DOCUMENT_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, ProtocolPackage.DOCUMENT_ROOT__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getXMLNSPrefixMap() {
		if (xMLNSPrefixMap == null) {
			xMLNSPrefixMap = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, ProtocolPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		}
		return xMLNSPrefixMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getXSISchemaLocation() {
		if (xSISchemaLocation == null) {
			xSISchemaLocation = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, ProtocolPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		}
		return xSISchemaLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getArtifact() {
		return (String)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__ARTIFACT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArtifact(String newArtifact) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__ARTIFACT, newArtifact);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArtifactResolveType getArtifactResolve() {
		return (ArtifactResolveType)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__ARTIFACT_RESOLVE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetArtifactResolve(ArtifactResolveType newArtifactResolve, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProtocolPackage.Literals.DOCUMENT_ROOT__ARTIFACT_RESOLVE, newArtifactResolve, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArtifactResolve(ArtifactResolveType newArtifactResolve) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__ARTIFACT_RESOLVE, newArtifactResolve);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArtifactResponseType getArtifactResponse() {
		return (ArtifactResponseType)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__ARTIFACT_RESPONSE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetArtifactResponse(ArtifactResponseType newArtifactResponse, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProtocolPackage.Literals.DOCUMENT_ROOT__ARTIFACT_RESPONSE, newArtifactResponse, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArtifactResponse(ArtifactResponseType newArtifactResponse) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__ARTIFACT_RESPONSE, newArtifactResponse);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AssertionIDRequestType getAssertionIDRequest() {
		return (AssertionIDRequestType)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__ASSERTION_ID_REQUEST, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAssertionIDRequest(AssertionIDRequestType newAssertionIDRequest, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProtocolPackage.Literals.DOCUMENT_ROOT__ASSERTION_ID_REQUEST, newAssertionIDRequest, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAssertionIDRequest(AssertionIDRequestType newAssertionIDRequest) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__ASSERTION_ID_REQUEST, newAssertionIDRequest);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeQueryType getAttributeQuery() {
		return (AttributeQueryType)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__ATTRIBUTE_QUERY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttributeQuery(AttributeQueryType newAttributeQuery, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProtocolPackage.Literals.DOCUMENT_ROOT__ATTRIBUTE_QUERY, newAttributeQuery, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttributeQuery(AttributeQueryType newAttributeQuery) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__ATTRIBUTE_QUERY, newAttributeQuery);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthnQueryType getAuthnQuery() {
		return (AuthnQueryType)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__AUTHN_QUERY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAuthnQuery(AuthnQueryType newAuthnQuery, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProtocolPackage.Literals.DOCUMENT_ROOT__AUTHN_QUERY, newAuthnQuery, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthnQuery(AuthnQueryType newAuthnQuery) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__AUTHN_QUERY, newAuthnQuery);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthnRequestType getAuthnRequest() {
		return (AuthnRequestType)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__AUTHN_REQUEST, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAuthnRequest(AuthnRequestType newAuthnRequest, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProtocolPackage.Literals.DOCUMENT_ROOT__AUTHN_REQUEST, newAuthnRequest, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthnRequest(AuthnRequestType newAuthnRequest) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__AUTHN_REQUEST, newAuthnRequest);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthzDecisionQueryType getAuthzDecisionQuery() {
		return (AuthzDecisionQueryType)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__AUTHZ_DECISION_QUERY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAuthzDecisionQuery(AuthzDecisionQueryType newAuthzDecisionQuery, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProtocolPackage.Literals.DOCUMENT_ROOT__AUTHZ_DECISION_QUERY, newAuthzDecisionQuery, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthzDecisionQuery(AuthzDecisionQueryType newAuthzDecisionQuery) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__AUTHZ_DECISION_QUERY, newAuthzDecisionQuery);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExtensionsType getExtensions() {
		return (ExtensionsType)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__EXTENSIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExtensions(ExtensionsType newExtensions, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProtocolPackage.Literals.DOCUMENT_ROOT__EXTENSIONS, newExtensions, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtensions(ExtensionsType newExtensions) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__EXTENSIONS, newExtensions);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getGetComplete() {
		return (String)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__GET_COMPLETE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGetComplete(String newGetComplete) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__GET_COMPLETE, newGetComplete);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IDPEntryType getIDPEntry() {
		return (IDPEntryType)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__IDP_ENTRY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIDPEntry(IDPEntryType newIDPEntry, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProtocolPackage.Literals.DOCUMENT_ROOT__IDP_ENTRY, newIDPEntry, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIDPEntry(IDPEntryType newIDPEntry) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__IDP_ENTRY, newIDPEntry);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IDPListType getIDPList() {
		return (IDPListType)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__IDP_LIST, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIDPList(IDPListType newIDPList, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProtocolPackage.Literals.DOCUMENT_ROOT__IDP_LIST, newIDPList, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIDPList(IDPListType newIDPList) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__IDP_LIST, newIDPList);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LogoutRequestType getLogoutRequest() {
		return (LogoutRequestType)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__LOGOUT_REQUEST, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLogoutRequest(LogoutRequestType newLogoutRequest, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProtocolPackage.Literals.DOCUMENT_ROOT__LOGOUT_REQUEST, newLogoutRequest, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLogoutRequest(LogoutRequestType newLogoutRequest) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__LOGOUT_REQUEST, newLogoutRequest);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatusResponseType getLogoutResponse() {
		return (StatusResponseType)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__LOGOUT_RESPONSE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLogoutResponse(StatusResponseType newLogoutResponse, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProtocolPackage.Literals.DOCUMENT_ROOT__LOGOUT_RESPONSE, newLogoutResponse, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLogoutResponse(StatusResponseType newLogoutResponse) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__LOGOUT_RESPONSE, newLogoutResponse);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ManageNameIDRequestType getManageNameIDRequest() {
		return (ManageNameIDRequestType)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__MANAGE_NAME_ID_REQUEST, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetManageNameIDRequest(ManageNameIDRequestType newManageNameIDRequest, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProtocolPackage.Literals.DOCUMENT_ROOT__MANAGE_NAME_ID_REQUEST, newManageNameIDRequest, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setManageNameIDRequest(ManageNameIDRequestType newManageNameIDRequest) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__MANAGE_NAME_ID_REQUEST, newManageNameIDRequest);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatusResponseType getManageNameIDResponse() {
		return (StatusResponseType)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__MANAGE_NAME_ID_RESPONSE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetManageNameIDResponse(StatusResponseType newManageNameIDResponse, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProtocolPackage.Literals.DOCUMENT_ROOT__MANAGE_NAME_ID_RESPONSE, newManageNameIDResponse, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setManageNameIDResponse(StatusResponseType newManageNameIDResponse) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__MANAGE_NAME_ID_RESPONSE, newManageNameIDResponse);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NameIDMappingRequestType getNameIDMappingRequest() {
		return (NameIDMappingRequestType)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__NAME_ID_MAPPING_REQUEST, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNameIDMappingRequest(NameIDMappingRequestType newNameIDMappingRequest, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProtocolPackage.Literals.DOCUMENT_ROOT__NAME_ID_MAPPING_REQUEST, newNameIDMappingRequest, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNameIDMappingRequest(NameIDMappingRequestType newNameIDMappingRequest) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__NAME_ID_MAPPING_REQUEST, newNameIDMappingRequest);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NameIDMappingResponseType getNameIDMappingResponse() {
		return (NameIDMappingResponseType)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__NAME_ID_MAPPING_RESPONSE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNameIDMappingResponse(NameIDMappingResponseType newNameIDMappingResponse, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProtocolPackage.Literals.DOCUMENT_ROOT__NAME_ID_MAPPING_RESPONSE, newNameIDMappingResponse, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNameIDMappingResponse(NameIDMappingResponseType newNameIDMappingResponse) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__NAME_ID_MAPPING_RESPONSE, newNameIDMappingResponse);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NameIDPolicyType getNameIDPolicy() {
		return (NameIDPolicyType)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__NAME_ID_POLICY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNameIDPolicy(NameIDPolicyType newNameIDPolicy, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProtocolPackage.Literals.DOCUMENT_ROOT__NAME_ID_POLICY, newNameIDPolicy, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNameIDPolicy(NameIDPolicyType newNameIDPolicy) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__NAME_ID_POLICY, newNameIDPolicy);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNewID() {
		return (String)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__NEW_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNewID(String newNewID) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__NEW_ID, newNewID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequestedAuthnContextType getRequestedAuthnContext() {
		return (RequestedAuthnContextType)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__REQUESTED_AUTHN_CONTEXT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRequestedAuthnContext(RequestedAuthnContextType newRequestedAuthnContext, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProtocolPackage.Literals.DOCUMENT_ROOT__REQUESTED_AUTHN_CONTEXT, newRequestedAuthnContext, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRequestedAuthnContext(RequestedAuthnContextType newRequestedAuthnContext) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__REQUESTED_AUTHN_CONTEXT, newRequestedAuthnContext);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRequesterID() {
		return (String)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__REQUESTER_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRequesterID(String newRequesterID) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__REQUESTER_ID, newRequesterID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResponseType getResponse() {
		return (ResponseType)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__RESPONSE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetResponse(ResponseType newResponse, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProtocolPackage.Literals.DOCUMENT_ROOT__RESPONSE, newResponse, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResponse(ResponseType newResponse) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__RESPONSE, newResponse);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScopingType getScoping() {
		return (ScopingType)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__SCOPING, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetScoping(ScopingType newScoping, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProtocolPackage.Literals.DOCUMENT_ROOT__SCOPING, newScoping, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScoping(ScopingType newScoping) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__SCOPING, newScoping);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSessionIndex() {
		return (String)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__SESSION_INDEX, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSessionIndex(String newSessionIndex) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__SESSION_INDEX, newSessionIndex);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatusType getStatus() {
		return (StatusType)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__STATUS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStatus(StatusType newStatus, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProtocolPackage.Literals.DOCUMENT_ROOT__STATUS, newStatus, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatus(StatusType newStatus) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__STATUS, newStatus);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatusCodeType getStatusCode() {
		return (StatusCodeType)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__STATUS_CODE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStatusCode(StatusCodeType newStatusCode, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProtocolPackage.Literals.DOCUMENT_ROOT__STATUS_CODE, newStatusCode, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatusCode(StatusCodeType newStatusCode) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__STATUS_CODE, newStatusCode);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatusDetailType getStatusDetail() {
		return (StatusDetailType)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__STATUS_DETAIL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStatusDetail(StatusDetailType newStatusDetail, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProtocolPackage.Literals.DOCUMENT_ROOT__STATUS_DETAIL, newStatusDetail, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatusDetail(StatusDetailType newStatusDetail) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__STATUS_DETAIL, newStatusDetail);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getStatusMessage() {
		return (String)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__STATUS_MESSAGE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatusMessage(String newStatusMessage) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__STATUS_MESSAGE, newStatusMessage);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SubjectQueryAbstractType getSubjectQuery() {
		return (SubjectQueryAbstractType)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__SUBJECT_QUERY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSubjectQuery(SubjectQueryAbstractType newSubjectQuery, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProtocolPackage.Literals.DOCUMENT_ROOT__SUBJECT_QUERY, newSubjectQuery, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubjectQuery(SubjectQueryAbstractType newSubjectQuery) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__SUBJECT_QUERY, newSubjectQuery);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TerminateType getTerminate() {
		return (TerminateType)getMixed().get(ProtocolPackage.Literals.DOCUMENT_ROOT__TERMINATE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTerminate(TerminateType newTerminate, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ProtocolPackage.Literals.DOCUMENT_ROOT__TERMINATE, newTerminate, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTerminate(TerminateType newTerminate) {
		((FeatureMap.Internal)getMixed()).set(ProtocolPackage.Literals.DOCUMENT_ROOT__TERMINATE, newTerminate);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProtocolPackage.DOCUMENT_ROOT__MIXED:
				return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
			case ProtocolPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return ((InternalEList<?>)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
			case ProtocolPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return ((InternalEList<?>)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
			case ProtocolPackage.DOCUMENT_ROOT__ARTIFACT_RESOLVE:
				return basicSetArtifactResolve(null, msgs);
			case ProtocolPackage.DOCUMENT_ROOT__ARTIFACT_RESPONSE:
				return basicSetArtifactResponse(null, msgs);
			case ProtocolPackage.DOCUMENT_ROOT__ASSERTION_ID_REQUEST:
				return basicSetAssertionIDRequest(null, msgs);
			case ProtocolPackage.DOCUMENT_ROOT__ATTRIBUTE_QUERY:
				return basicSetAttributeQuery(null, msgs);
			case ProtocolPackage.DOCUMENT_ROOT__AUTHN_QUERY:
				return basicSetAuthnQuery(null, msgs);
			case ProtocolPackage.DOCUMENT_ROOT__AUTHN_REQUEST:
				return basicSetAuthnRequest(null, msgs);
			case ProtocolPackage.DOCUMENT_ROOT__AUTHZ_DECISION_QUERY:
				return basicSetAuthzDecisionQuery(null, msgs);
			case ProtocolPackage.DOCUMENT_ROOT__EXTENSIONS:
				return basicSetExtensions(null, msgs);
			case ProtocolPackage.DOCUMENT_ROOT__IDP_ENTRY:
				return basicSetIDPEntry(null, msgs);
			case ProtocolPackage.DOCUMENT_ROOT__IDP_LIST:
				return basicSetIDPList(null, msgs);
			case ProtocolPackage.DOCUMENT_ROOT__LOGOUT_REQUEST:
				return basicSetLogoutRequest(null, msgs);
			case ProtocolPackage.DOCUMENT_ROOT__LOGOUT_RESPONSE:
				return basicSetLogoutResponse(null, msgs);
			case ProtocolPackage.DOCUMENT_ROOT__MANAGE_NAME_ID_REQUEST:
				return basicSetManageNameIDRequest(null, msgs);
			case ProtocolPackage.DOCUMENT_ROOT__MANAGE_NAME_ID_RESPONSE:
				return basicSetManageNameIDResponse(null, msgs);
			case ProtocolPackage.DOCUMENT_ROOT__NAME_ID_MAPPING_REQUEST:
				return basicSetNameIDMappingRequest(null, msgs);
			case ProtocolPackage.DOCUMENT_ROOT__NAME_ID_MAPPING_RESPONSE:
				return basicSetNameIDMappingResponse(null, msgs);
			case ProtocolPackage.DOCUMENT_ROOT__NAME_ID_POLICY:
				return basicSetNameIDPolicy(null, msgs);
			case ProtocolPackage.DOCUMENT_ROOT__REQUESTED_AUTHN_CONTEXT:
				return basicSetRequestedAuthnContext(null, msgs);
			case ProtocolPackage.DOCUMENT_ROOT__RESPONSE:
				return basicSetResponse(null, msgs);
			case ProtocolPackage.DOCUMENT_ROOT__SCOPING:
				return basicSetScoping(null, msgs);
			case ProtocolPackage.DOCUMENT_ROOT__STATUS:
				return basicSetStatus(null, msgs);
			case ProtocolPackage.DOCUMENT_ROOT__STATUS_CODE:
				return basicSetStatusCode(null, msgs);
			case ProtocolPackage.DOCUMENT_ROOT__STATUS_DETAIL:
				return basicSetStatusDetail(null, msgs);
			case ProtocolPackage.DOCUMENT_ROOT__SUBJECT_QUERY:
				return basicSetSubjectQuery(null, msgs);
			case ProtocolPackage.DOCUMENT_ROOT__TERMINATE:
				return basicSetTerminate(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ProtocolPackage.DOCUMENT_ROOT__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case ProtocolPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				if (coreType) return getXMLNSPrefixMap();
				else return getXMLNSPrefixMap().map();
			case ProtocolPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				if (coreType) return getXSISchemaLocation();
				else return getXSISchemaLocation().map();
			case ProtocolPackage.DOCUMENT_ROOT__ARTIFACT:
				return getArtifact();
			case ProtocolPackage.DOCUMENT_ROOT__ARTIFACT_RESOLVE:
				return getArtifactResolve();
			case ProtocolPackage.DOCUMENT_ROOT__ARTIFACT_RESPONSE:
				return getArtifactResponse();
			case ProtocolPackage.DOCUMENT_ROOT__ASSERTION_ID_REQUEST:
				return getAssertionIDRequest();
			case ProtocolPackage.DOCUMENT_ROOT__ATTRIBUTE_QUERY:
				return getAttributeQuery();
			case ProtocolPackage.DOCUMENT_ROOT__AUTHN_QUERY:
				return getAuthnQuery();
			case ProtocolPackage.DOCUMENT_ROOT__AUTHN_REQUEST:
				return getAuthnRequest();
			case ProtocolPackage.DOCUMENT_ROOT__AUTHZ_DECISION_QUERY:
				return getAuthzDecisionQuery();
			case ProtocolPackage.DOCUMENT_ROOT__EXTENSIONS:
				return getExtensions();
			case ProtocolPackage.DOCUMENT_ROOT__GET_COMPLETE:
				return getGetComplete();
			case ProtocolPackage.DOCUMENT_ROOT__IDP_ENTRY:
				return getIDPEntry();
			case ProtocolPackage.DOCUMENT_ROOT__IDP_LIST:
				return getIDPList();
			case ProtocolPackage.DOCUMENT_ROOT__LOGOUT_REQUEST:
				return getLogoutRequest();
			case ProtocolPackage.DOCUMENT_ROOT__LOGOUT_RESPONSE:
				return getLogoutResponse();
			case ProtocolPackage.DOCUMENT_ROOT__MANAGE_NAME_ID_REQUEST:
				return getManageNameIDRequest();
			case ProtocolPackage.DOCUMENT_ROOT__MANAGE_NAME_ID_RESPONSE:
				return getManageNameIDResponse();
			case ProtocolPackage.DOCUMENT_ROOT__NAME_ID_MAPPING_REQUEST:
				return getNameIDMappingRequest();
			case ProtocolPackage.DOCUMENT_ROOT__NAME_ID_MAPPING_RESPONSE:
				return getNameIDMappingResponse();
			case ProtocolPackage.DOCUMENT_ROOT__NAME_ID_POLICY:
				return getNameIDPolicy();
			case ProtocolPackage.DOCUMENT_ROOT__NEW_ID:
				return getNewID();
			case ProtocolPackage.DOCUMENT_ROOT__REQUESTED_AUTHN_CONTEXT:
				return getRequestedAuthnContext();
			case ProtocolPackage.DOCUMENT_ROOT__REQUESTER_ID:
				return getRequesterID();
			case ProtocolPackage.DOCUMENT_ROOT__RESPONSE:
				return getResponse();
			case ProtocolPackage.DOCUMENT_ROOT__SCOPING:
				return getScoping();
			case ProtocolPackage.DOCUMENT_ROOT__SESSION_INDEX:
				return getSessionIndex();
			case ProtocolPackage.DOCUMENT_ROOT__STATUS:
				return getStatus();
			case ProtocolPackage.DOCUMENT_ROOT__STATUS_CODE:
				return getStatusCode();
			case ProtocolPackage.DOCUMENT_ROOT__STATUS_DETAIL:
				return getStatusDetail();
			case ProtocolPackage.DOCUMENT_ROOT__STATUS_MESSAGE:
				return getStatusMessage();
			case ProtocolPackage.DOCUMENT_ROOT__SUBJECT_QUERY:
				return getSubjectQuery();
			case ProtocolPackage.DOCUMENT_ROOT__TERMINATE:
				return getTerminate();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ProtocolPackage.DOCUMENT_ROOT__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__ARTIFACT:
				setArtifact((String)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__ARTIFACT_RESOLVE:
				setArtifactResolve((ArtifactResolveType)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__ARTIFACT_RESPONSE:
				setArtifactResponse((ArtifactResponseType)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__ASSERTION_ID_REQUEST:
				setAssertionIDRequest((AssertionIDRequestType)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__ATTRIBUTE_QUERY:
				setAttributeQuery((AttributeQueryType)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__AUTHN_QUERY:
				setAuthnQuery((AuthnQueryType)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__AUTHN_REQUEST:
				setAuthnRequest((AuthnRequestType)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__AUTHZ_DECISION_QUERY:
				setAuthzDecisionQuery((AuthzDecisionQueryType)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__EXTENSIONS:
				setExtensions((ExtensionsType)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__GET_COMPLETE:
				setGetComplete((String)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__IDP_ENTRY:
				setIDPEntry((IDPEntryType)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__IDP_LIST:
				setIDPList((IDPListType)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__LOGOUT_REQUEST:
				setLogoutRequest((LogoutRequestType)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__LOGOUT_RESPONSE:
				setLogoutResponse((StatusResponseType)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__MANAGE_NAME_ID_REQUEST:
				setManageNameIDRequest((ManageNameIDRequestType)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__MANAGE_NAME_ID_RESPONSE:
				setManageNameIDResponse((StatusResponseType)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__NAME_ID_MAPPING_REQUEST:
				setNameIDMappingRequest((NameIDMappingRequestType)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__NAME_ID_MAPPING_RESPONSE:
				setNameIDMappingResponse((NameIDMappingResponseType)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__NAME_ID_POLICY:
				setNameIDPolicy((NameIDPolicyType)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__NEW_ID:
				setNewID((String)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__REQUESTED_AUTHN_CONTEXT:
				setRequestedAuthnContext((RequestedAuthnContextType)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__REQUESTER_ID:
				setRequesterID((String)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__RESPONSE:
				setResponse((ResponseType)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__SCOPING:
				setScoping((ScopingType)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__SESSION_INDEX:
				setSessionIndex((String)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__STATUS:
				setStatus((StatusType)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__STATUS_CODE:
				setStatusCode((StatusCodeType)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__STATUS_DETAIL:
				setStatusDetail((StatusDetailType)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__STATUS_MESSAGE:
				setStatusMessage((String)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__SUBJECT_QUERY:
				setSubjectQuery((SubjectQueryAbstractType)newValue);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__TERMINATE:
				setTerminate((TerminateType)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ProtocolPackage.DOCUMENT_ROOT__MIXED:
				getMixed().clear();
				return;
			case ProtocolPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				getXMLNSPrefixMap().clear();
				return;
			case ProtocolPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				getXSISchemaLocation().clear();
				return;
			case ProtocolPackage.DOCUMENT_ROOT__ARTIFACT:
				setArtifact(ARTIFACT_EDEFAULT);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__ARTIFACT_RESOLVE:
				setArtifactResolve((ArtifactResolveType)null);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__ARTIFACT_RESPONSE:
				setArtifactResponse((ArtifactResponseType)null);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__ASSERTION_ID_REQUEST:
				setAssertionIDRequest((AssertionIDRequestType)null);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__ATTRIBUTE_QUERY:
				setAttributeQuery((AttributeQueryType)null);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__AUTHN_QUERY:
				setAuthnQuery((AuthnQueryType)null);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__AUTHN_REQUEST:
				setAuthnRequest((AuthnRequestType)null);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__AUTHZ_DECISION_QUERY:
				setAuthzDecisionQuery((AuthzDecisionQueryType)null);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__EXTENSIONS:
				setExtensions((ExtensionsType)null);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__GET_COMPLETE:
				setGetComplete(GET_COMPLETE_EDEFAULT);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__IDP_ENTRY:
				setIDPEntry((IDPEntryType)null);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__IDP_LIST:
				setIDPList((IDPListType)null);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__LOGOUT_REQUEST:
				setLogoutRequest((LogoutRequestType)null);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__LOGOUT_RESPONSE:
				setLogoutResponse((StatusResponseType)null);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__MANAGE_NAME_ID_REQUEST:
				setManageNameIDRequest((ManageNameIDRequestType)null);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__MANAGE_NAME_ID_RESPONSE:
				setManageNameIDResponse((StatusResponseType)null);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__NAME_ID_MAPPING_REQUEST:
				setNameIDMappingRequest((NameIDMappingRequestType)null);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__NAME_ID_MAPPING_RESPONSE:
				setNameIDMappingResponse((NameIDMappingResponseType)null);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__NAME_ID_POLICY:
				setNameIDPolicy((NameIDPolicyType)null);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__NEW_ID:
				setNewID(NEW_ID_EDEFAULT);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__REQUESTED_AUTHN_CONTEXT:
				setRequestedAuthnContext((RequestedAuthnContextType)null);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__REQUESTER_ID:
				setRequesterID(REQUESTER_ID_EDEFAULT);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__RESPONSE:
				setResponse((ResponseType)null);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__SCOPING:
				setScoping((ScopingType)null);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__SESSION_INDEX:
				setSessionIndex(SESSION_INDEX_EDEFAULT);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__STATUS:
				setStatus((StatusType)null);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__STATUS_CODE:
				setStatusCode((StatusCodeType)null);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__STATUS_DETAIL:
				setStatusDetail((StatusDetailType)null);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__STATUS_MESSAGE:
				setStatusMessage(STATUS_MESSAGE_EDEFAULT);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__SUBJECT_QUERY:
				setSubjectQuery((SubjectQueryAbstractType)null);
				return;
			case ProtocolPackage.DOCUMENT_ROOT__TERMINATE:
				setTerminate((TerminateType)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ProtocolPackage.DOCUMENT_ROOT__MIXED:
				return mixed != null && !mixed.isEmpty();
			case ProtocolPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
			case ProtocolPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
			case ProtocolPackage.DOCUMENT_ROOT__ARTIFACT:
				return ARTIFACT_EDEFAULT == null ? getArtifact() != null : !ARTIFACT_EDEFAULT.equals(getArtifact());
			case ProtocolPackage.DOCUMENT_ROOT__ARTIFACT_RESOLVE:
				return getArtifactResolve() != null;
			case ProtocolPackage.DOCUMENT_ROOT__ARTIFACT_RESPONSE:
				return getArtifactResponse() != null;
			case ProtocolPackage.DOCUMENT_ROOT__ASSERTION_ID_REQUEST:
				return getAssertionIDRequest() != null;
			case ProtocolPackage.DOCUMENT_ROOT__ATTRIBUTE_QUERY:
				return getAttributeQuery() != null;
			case ProtocolPackage.DOCUMENT_ROOT__AUTHN_QUERY:
				return getAuthnQuery() != null;
			case ProtocolPackage.DOCUMENT_ROOT__AUTHN_REQUEST:
				return getAuthnRequest() != null;
			case ProtocolPackage.DOCUMENT_ROOT__AUTHZ_DECISION_QUERY:
				return getAuthzDecisionQuery() != null;
			case ProtocolPackage.DOCUMENT_ROOT__EXTENSIONS:
				return getExtensions() != null;
			case ProtocolPackage.DOCUMENT_ROOT__GET_COMPLETE:
				return GET_COMPLETE_EDEFAULT == null ? getGetComplete() != null : !GET_COMPLETE_EDEFAULT.equals(getGetComplete());
			case ProtocolPackage.DOCUMENT_ROOT__IDP_ENTRY:
				return getIDPEntry() != null;
			case ProtocolPackage.DOCUMENT_ROOT__IDP_LIST:
				return getIDPList() != null;
			case ProtocolPackage.DOCUMENT_ROOT__LOGOUT_REQUEST:
				return getLogoutRequest() != null;
			case ProtocolPackage.DOCUMENT_ROOT__LOGOUT_RESPONSE:
				return getLogoutResponse() != null;
			case ProtocolPackage.DOCUMENT_ROOT__MANAGE_NAME_ID_REQUEST:
				return getManageNameIDRequest() != null;
			case ProtocolPackage.DOCUMENT_ROOT__MANAGE_NAME_ID_RESPONSE:
				return getManageNameIDResponse() != null;
			case ProtocolPackage.DOCUMENT_ROOT__NAME_ID_MAPPING_REQUEST:
				return getNameIDMappingRequest() != null;
			case ProtocolPackage.DOCUMENT_ROOT__NAME_ID_MAPPING_RESPONSE:
				return getNameIDMappingResponse() != null;
			case ProtocolPackage.DOCUMENT_ROOT__NAME_ID_POLICY:
				return getNameIDPolicy() != null;
			case ProtocolPackage.DOCUMENT_ROOT__NEW_ID:
				return NEW_ID_EDEFAULT == null ? getNewID() != null : !NEW_ID_EDEFAULT.equals(getNewID());
			case ProtocolPackage.DOCUMENT_ROOT__REQUESTED_AUTHN_CONTEXT:
				return getRequestedAuthnContext() != null;
			case ProtocolPackage.DOCUMENT_ROOT__REQUESTER_ID:
				return REQUESTER_ID_EDEFAULT == null ? getRequesterID() != null : !REQUESTER_ID_EDEFAULT.equals(getRequesterID());
			case ProtocolPackage.DOCUMENT_ROOT__RESPONSE:
				return getResponse() != null;
			case ProtocolPackage.DOCUMENT_ROOT__SCOPING:
				return getScoping() != null;
			case ProtocolPackage.DOCUMENT_ROOT__SESSION_INDEX:
				return SESSION_INDEX_EDEFAULT == null ? getSessionIndex() != null : !SESSION_INDEX_EDEFAULT.equals(getSessionIndex());
			case ProtocolPackage.DOCUMENT_ROOT__STATUS:
				return getStatus() != null;
			case ProtocolPackage.DOCUMENT_ROOT__STATUS_CODE:
				return getStatusCode() != null;
			case ProtocolPackage.DOCUMENT_ROOT__STATUS_DETAIL:
				return getStatusDetail() != null;
			case ProtocolPackage.DOCUMENT_ROOT__STATUS_MESSAGE:
				return STATUS_MESSAGE_EDEFAULT == null ? getStatusMessage() != null : !STATUS_MESSAGE_EDEFAULT.equals(getStatusMessage());
			case ProtocolPackage.DOCUMENT_ROOT__SUBJECT_QUERY:
				return getSubjectQuery() != null;
			case ProtocolPackage.DOCUMENT_ROOT__TERMINATE:
				return getTerminate() != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (mixed: ");
		result.append(mixed);
		result.append(')');
		return result.toString();
	}

} //DocumentRootImpl
