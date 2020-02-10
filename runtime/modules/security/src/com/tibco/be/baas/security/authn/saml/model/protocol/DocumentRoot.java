/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getArtifact <em>Artifact</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getArtifactResolve <em>Artifact Resolve</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getArtifactResponse <em>Artifact Response</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getAssertionIDRequest <em>Assertion ID Request</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getAttributeQuery <em>Attribute Query</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getAuthnQuery <em>Authn Query</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getAuthnRequest <em>Authn Request</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getAuthzDecisionQuery <em>Authz Decision Query</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getGetComplete <em>Get Complete</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getIDPEntry <em>IDP Entry</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getIDPList <em>IDP List</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getLogoutRequest <em>Logout Request</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getLogoutResponse <em>Logout Response</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getManageNameIDRequest <em>Manage Name ID Request</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getManageNameIDResponse <em>Manage Name ID Response</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getNameIDMappingRequest <em>Name ID Mapping Request</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getNameIDMappingResponse <em>Name ID Mapping Response</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getNameIDPolicy <em>Name ID Policy</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getNewID <em>New ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getRequestedAuthnContext <em>Requested Authn Context</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getRequesterID <em>Requester ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getResponse <em>Response</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getScoping <em>Scoping</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getSessionIndex <em>Session Index</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getStatus <em>Status</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getStatusCode <em>Status Code</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getStatusDetail <em>Status Detail</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getStatusMessage <em>Status Message</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getSubjectQuery <em>Subject Query</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getTerminate <em>Terminate</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface DocumentRoot extends EObject {
	/**
	 * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mixed</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mixed</em>' attribute list.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_Mixed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' name=':mixed'"
	 * @generated
	 */
	FeatureMap getMixed();

	/**
	 * Returns the value of the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XMLNS Prefix Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XMLNS Prefix Map</em>' map.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_XMLNSPrefixMap()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
	 *        extendedMetaData="kind='attribute' name='xmlns:prefix'"
	 * @generated
	 */
	EMap<String, String> getXMLNSPrefixMap();

	/**
	 * Returns the value of the '<em><b>XSI Schema Location</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XSI Schema Location</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XSI Schema Location</em>' map.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_XSISchemaLocation()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
	 *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
	 * @generated
	 */
	EMap<String, String> getXSISchemaLocation();

	/**
	 * Returns the value of the '<em><b>Artifact</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Artifact</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Artifact</em>' attribute.
	 * @see #setArtifact(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_Artifact()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Artifact' namespace='##targetNamespace'"
	 * @generated
	 */
	String getArtifact();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getArtifact <em>Artifact</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Artifact</em>' attribute.
	 * @see #getArtifact()
	 * @generated
	 */
	void setArtifact(String value);

	/**
	 * Returns the value of the '<em><b>Artifact Resolve</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Artifact Resolve</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Artifact Resolve</em>' containment reference.
	 * @see #setArtifactResolve(ArtifactResolveType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_ArtifactResolve()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ArtifactResolve' namespace='##targetNamespace'"
	 * @generated
	 */
	ArtifactResolveType getArtifactResolve();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getArtifactResolve <em>Artifact Resolve</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Artifact Resolve</em>' containment reference.
	 * @see #getArtifactResolve()
	 * @generated
	 */
	void setArtifactResolve(ArtifactResolveType value);

	/**
	 * Returns the value of the '<em><b>Artifact Response</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Artifact Response</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Artifact Response</em>' containment reference.
	 * @see #setArtifactResponse(ArtifactResponseType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_ArtifactResponse()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ArtifactResponse' namespace='##targetNamespace'"
	 * @generated
	 */
	ArtifactResponseType getArtifactResponse();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getArtifactResponse <em>Artifact Response</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Artifact Response</em>' containment reference.
	 * @see #getArtifactResponse()
	 * @generated
	 */
	void setArtifactResponse(ArtifactResponseType value);

	/**
	 * Returns the value of the '<em><b>Assertion ID Request</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assertion ID Request</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assertion ID Request</em>' containment reference.
	 * @see #setAssertionIDRequest(AssertionIDRequestType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_AssertionIDRequest()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AssertionIDRequest' namespace='##targetNamespace'"
	 * @generated
	 */
	AssertionIDRequestType getAssertionIDRequest();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getAssertionIDRequest <em>Assertion ID Request</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Assertion ID Request</em>' containment reference.
	 * @see #getAssertionIDRequest()
	 * @generated
	 */
	void setAssertionIDRequest(AssertionIDRequestType value);

	/**
	 * Returns the value of the '<em><b>Attribute Query</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Query</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Query</em>' containment reference.
	 * @see #setAttributeQuery(AttributeQueryType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_AttributeQuery()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AttributeQuery' namespace='##targetNamespace'"
	 * @generated
	 */
	AttributeQueryType getAttributeQuery();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getAttributeQuery <em>Attribute Query</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Query</em>' containment reference.
	 * @see #getAttributeQuery()
	 * @generated
	 */
	void setAttributeQuery(AttributeQueryType value);

	/**
	 * Returns the value of the '<em><b>Authn Query</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authn Query</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authn Query</em>' containment reference.
	 * @see #setAuthnQuery(AuthnQueryType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_AuthnQuery()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AuthnQuery' namespace='##targetNamespace'"
	 * @generated
	 */
	AuthnQueryType getAuthnQuery();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getAuthnQuery <em>Authn Query</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Authn Query</em>' containment reference.
	 * @see #getAuthnQuery()
	 * @generated
	 */
	void setAuthnQuery(AuthnQueryType value);

	/**
	 * Returns the value of the '<em><b>Authn Request</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authn Request</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authn Request</em>' containment reference.
	 * @see #setAuthnRequest(AuthnRequestType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_AuthnRequest()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AuthnRequest' namespace='##targetNamespace'"
	 * @generated
	 */
	AuthnRequestType getAuthnRequest();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getAuthnRequest <em>Authn Request</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Authn Request</em>' containment reference.
	 * @see #getAuthnRequest()
	 * @generated
	 */
	void setAuthnRequest(AuthnRequestType value);

	/**
	 * Returns the value of the '<em><b>Authz Decision Query</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authz Decision Query</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authz Decision Query</em>' containment reference.
	 * @see #setAuthzDecisionQuery(AuthzDecisionQueryType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_AuthzDecisionQuery()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AuthzDecisionQuery' namespace='##targetNamespace'"
	 * @generated
	 */
	AuthzDecisionQueryType getAuthzDecisionQuery();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getAuthzDecisionQuery <em>Authz Decision Query</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Authz Decision Query</em>' containment reference.
	 * @see #getAuthzDecisionQuery()
	 * @generated
	 */
	void setAuthzDecisionQuery(AuthzDecisionQueryType value);

	/**
	 * Returns the value of the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extensions</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extensions</em>' containment reference.
	 * @see #setExtensions(ExtensionsType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_Extensions()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Extensions' namespace='##targetNamespace'"
	 * @generated
	 */
	ExtensionsType getExtensions();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getExtensions <em>Extensions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extensions</em>' containment reference.
	 * @see #getExtensions()
	 * @generated
	 */
	void setExtensions(ExtensionsType value);

	/**
	 * Returns the value of the '<em><b>Get Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Get Complete</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Get Complete</em>' attribute.
	 * @see #setGetComplete(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_GetComplete()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='GetComplete' namespace='##targetNamespace'"
	 * @generated
	 */
	String getGetComplete();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getGetComplete <em>Get Complete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Get Complete</em>' attribute.
	 * @see #getGetComplete()
	 * @generated
	 */
	void setGetComplete(String value);

	/**
	 * Returns the value of the '<em><b>IDP Entry</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>IDP Entry</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>IDP Entry</em>' containment reference.
	 * @see #setIDPEntry(IDPEntryType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_IDPEntry()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='IDPEntry' namespace='##targetNamespace'"
	 * @generated
	 */
	IDPEntryType getIDPEntry();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getIDPEntry <em>IDP Entry</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>IDP Entry</em>' containment reference.
	 * @see #getIDPEntry()
	 * @generated
	 */
	void setIDPEntry(IDPEntryType value);

	/**
	 * Returns the value of the '<em><b>IDP List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>IDP List</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>IDP List</em>' containment reference.
	 * @see #setIDPList(IDPListType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_IDPList()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='IDPList' namespace='##targetNamespace'"
	 * @generated
	 */
	IDPListType getIDPList();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getIDPList <em>IDP List</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>IDP List</em>' containment reference.
	 * @see #getIDPList()
	 * @generated
	 */
	void setIDPList(IDPListType value);

	/**
	 * Returns the value of the '<em><b>Logout Request</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Logout Request</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Logout Request</em>' containment reference.
	 * @see #setLogoutRequest(LogoutRequestType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_LogoutRequest()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='LogoutRequest' namespace='##targetNamespace'"
	 * @generated
	 */
	LogoutRequestType getLogoutRequest();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getLogoutRequest <em>Logout Request</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Logout Request</em>' containment reference.
	 * @see #getLogoutRequest()
	 * @generated
	 */
	void setLogoutRequest(LogoutRequestType value);

	/**
	 * Returns the value of the '<em><b>Logout Response</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Logout Response</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Logout Response</em>' containment reference.
	 * @see #setLogoutResponse(StatusResponseType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_LogoutResponse()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='LogoutResponse' namespace='##targetNamespace'"
	 * @generated
	 */
	StatusResponseType getLogoutResponse();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getLogoutResponse <em>Logout Response</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Logout Response</em>' containment reference.
	 * @see #getLogoutResponse()
	 * @generated
	 */
	void setLogoutResponse(StatusResponseType value);

	/**
	 * Returns the value of the '<em><b>Manage Name ID Request</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Manage Name ID Request</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Manage Name ID Request</em>' containment reference.
	 * @see #setManageNameIDRequest(ManageNameIDRequestType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_ManageNameIDRequest()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ManageNameIDRequest' namespace='##targetNamespace'"
	 * @generated
	 */
	ManageNameIDRequestType getManageNameIDRequest();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getManageNameIDRequest <em>Manage Name ID Request</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Manage Name ID Request</em>' containment reference.
	 * @see #getManageNameIDRequest()
	 * @generated
	 */
	void setManageNameIDRequest(ManageNameIDRequestType value);

	/**
	 * Returns the value of the '<em><b>Manage Name ID Response</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Manage Name ID Response</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Manage Name ID Response</em>' containment reference.
	 * @see #setManageNameIDResponse(StatusResponseType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_ManageNameIDResponse()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ManageNameIDResponse' namespace='##targetNamespace'"
	 * @generated
	 */
	StatusResponseType getManageNameIDResponse();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getManageNameIDResponse <em>Manage Name ID Response</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Manage Name ID Response</em>' containment reference.
	 * @see #getManageNameIDResponse()
	 * @generated
	 */
	void setManageNameIDResponse(StatusResponseType value);

	/**
	 * Returns the value of the '<em><b>Name ID Mapping Request</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name ID Mapping Request</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name ID Mapping Request</em>' containment reference.
	 * @see #setNameIDMappingRequest(NameIDMappingRequestType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_NameIDMappingRequest()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='NameIDMappingRequest' namespace='##targetNamespace'"
	 * @generated
	 */
	NameIDMappingRequestType getNameIDMappingRequest();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getNameIDMappingRequest <em>Name ID Mapping Request</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name ID Mapping Request</em>' containment reference.
	 * @see #getNameIDMappingRequest()
	 * @generated
	 */
	void setNameIDMappingRequest(NameIDMappingRequestType value);

	/**
	 * Returns the value of the '<em><b>Name ID Mapping Response</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name ID Mapping Response</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name ID Mapping Response</em>' containment reference.
	 * @see #setNameIDMappingResponse(NameIDMappingResponseType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_NameIDMappingResponse()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='NameIDMappingResponse' namespace='##targetNamespace'"
	 * @generated
	 */
	NameIDMappingResponseType getNameIDMappingResponse();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getNameIDMappingResponse <em>Name ID Mapping Response</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name ID Mapping Response</em>' containment reference.
	 * @see #getNameIDMappingResponse()
	 * @generated
	 */
	void setNameIDMappingResponse(NameIDMappingResponseType value);

	/**
	 * Returns the value of the '<em><b>Name ID Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name ID Policy</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name ID Policy</em>' containment reference.
	 * @see #setNameIDPolicy(NameIDPolicyType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_NameIDPolicy()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='NameIDPolicy' namespace='##targetNamespace'"
	 * @generated
	 */
	NameIDPolicyType getNameIDPolicy();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getNameIDPolicy <em>Name ID Policy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name ID Policy</em>' containment reference.
	 * @see #getNameIDPolicy()
	 * @generated
	 */
	void setNameIDPolicy(NameIDPolicyType value);

	/**
	 * Returns the value of the '<em><b>New ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>New ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>New ID</em>' attribute.
	 * @see #setNewID(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_NewID()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='NewID' namespace='##targetNamespace'"
	 * @generated
	 */
	String getNewID();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getNewID <em>New ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>New ID</em>' attribute.
	 * @see #getNewID()
	 * @generated
	 */
	void setNewID(String value);

	/**
	 * Returns the value of the '<em><b>Requested Authn Context</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Requested Authn Context</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Requested Authn Context</em>' containment reference.
	 * @see #setRequestedAuthnContext(RequestedAuthnContextType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_RequestedAuthnContext()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='RequestedAuthnContext' namespace='##targetNamespace'"
	 * @generated
	 */
	RequestedAuthnContextType getRequestedAuthnContext();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getRequestedAuthnContext <em>Requested Authn Context</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Requested Authn Context</em>' containment reference.
	 * @see #getRequestedAuthnContext()
	 * @generated
	 */
	void setRequestedAuthnContext(RequestedAuthnContextType value);

	/**
	 * Returns the value of the '<em><b>Requester ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Requester ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Requester ID</em>' attribute.
	 * @see #setRequesterID(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_RequesterID()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='RequesterID' namespace='##targetNamespace'"
	 * @generated
	 */
	String getRequesterID();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getRequesterID <em>Requester ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Requester ID</em>' attribute.
	 * @see #getRequesterID()
	 * @generated
	 */
	void setRequesterID(String value);

	/**
	 * Returns the value of the '<em><b>Response</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Response</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Response</em>' containment reference.
	 * @see #setResponse(ResponseType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_Response()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Response' namespace='##targetNamespace'"
	 * @generated
	 */
	ResponseType getResponse();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getResponse <em>Response</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Response</em>' containment reference.
	 * @see #getResponse()
	 * @generated
	 */
	void setResponse(ResponseType value);

	/**
	 * Returns the value of the '<em><b>Scoping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Scoping</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scoping</em>' containment reference.
	 * @see #setScoping(ScopingType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_Scoping()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Scoping' namespace='##targetNamespace'"
	 * @generated
	 */
	ScopingType getScoping();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getScoping <em>Scoping</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scoping</em>' containment reference.
	 * @see #getScoping()
	 * @generated
	 */
	void setScoping(ScopingType value);

	/**
	 * Returns the value of the '<em><b>Session Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Session Index</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Session Index</em>' attribute.
	 * @see #setSessionIndex(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_SessionIndex()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='SessionIndex' namespace='##targetNamespace'"
	 * @generated
	 */
	String getSessionIndex();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getSessionIndex <em>Session Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Session Index</em>' attribute.
	 * @see #getSessionIndex()
	 * @generated
	 */
	void setSessionIndex(String value);

	/**
	 * Returns the value of the '<em><b>Status</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Status</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Status</em>' containment reference.
	 * @see #setStatus(StatusType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_Status()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Status' namespace='##targetNamespace'"
	 * @generated
	 */
	StatusType getStatus();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getStatus <em>Status</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Status</em>' containment reference.
	 * @see #getStatus()
	 * @generated
	 */
	void setStatus(StatusType value);

	/**
	 * Returns the value of the '<em><b>Status Code</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Status Code</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Status Code</em>' containment reference.
	 * @see #setStatusCode(StatusCodeType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_StatusCode()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='StatusCode' namespace='##targetNamespace'"
	 * @generated
	 */
	StatusCodeType getStatusCode();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getStatusCode <em>Status Code</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Status Code</em>' containment reference.
	 * @see #getStatusCode()
	 * @generated
	 */
	void setStatusCode(StatusCodeType value);

	/**
	 * Returns the value of the '<em><b>Status Detail</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Status Detail</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Status Detail</em>' containment reference.
	 * @see #setStatusDetail(StatusDetailType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_StatusDetail()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='StatusDetail' namespace='##targetNamespace'"
	 * @generated
	 */
	StatusDetailType getStatusDetail();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getStatusDetail <em>Status Detail</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Status Detail</em>' containment reference.
	 * @see #getStatusDetail()
	 * @generated
	 */
	void setStatusDetail(StatusDetailType value);

	/**
	 * Returns the value of the '<em><b>Status Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Status Message</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Status Message</em>' attribute.
	 * @see #setStatusMessage(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_StatusMessage()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='StatusMessage' namespace='##targetNamespace'"
	 * @generated
	 */
	String getStatusMessage();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getStatusMessage <em>Status Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Status Message</em>' attribute.
	 * @see #getStatusMessage()
	 * @generated
	 */
	void setStatusMessage(String value);

	/**
	 * Returns the value of the '<em><b>Subject Query</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subject Query</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subject Query</em>' containment reference.
	 * @see #setSubjectQuery(SubjectQueryAbstractType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_SubjectQuery()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='SubjectQuery' namespace='##targetNamespace'"
	 * @generated
	 */
	SubjectQueryAbstractType getSubjectQuery();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getSubjectQuery <em>Subject Query</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subject Query</em>' containment reference.
	 * @see #getSubjectQuery()
	 * @generated
	 */
	void setSubjectQuery(SubjectQueryAbstractType value);

	/**
	 * Returns the value of the '<em><b>Terminate</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Terminate</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Terminate</em>' containment reference.
	 * @see #setTerminate(TerminateType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getDocumentRoot_Terminate()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Terminate' namespace='##targetNamespace'"
	 * @generated
	 */
	TerminateType getTerminate();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot#getTerminate <em>Terminate</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Terminate</em>' containment reference.
	 * @see #getTerminate()
	 * @generated
	 */
	void setTerminate(TerminateType value);

} // DocumentRoot
