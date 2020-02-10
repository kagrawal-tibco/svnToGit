/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion;

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
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAction <em>Action</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAdvice <em>Advice</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAssertion <em>Assertion</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAssertionIDRef <em>Assertion ID Ref</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAssertionURIRef <em>Assertion URI Ref</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAttributeStatement <em>Attribute Statement</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAttributeValue <em>Attribute Value</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAudience <em>Audience</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAudienceRestriction <em>Audience Restriction</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthenticatingAuthority <em>Authenticating Authority</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthnContext <em>Authn Context</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthnContextClassRef <em>Authn Context Class Ref</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthnContextDecl <em>Authn Context Decl</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthnContextDeclRef <em>Authn Context Decl Ref</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthnStatement <em>Authn Statement</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthzDecisionStatement <em>Authz Decision Statement</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getBaseID <em>Base ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getCondition <em>Condition</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getConditions <em>Conditions</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getEvidence <em>Evidence</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getIssuer <em>Issuer</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getNameID <em>Name ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getOneTimeUse <em>One Time Use</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getProxyRestriction <em>Proxy Restriction</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getStatement <em>Statement</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getSubject <em>Subject</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getSubjectConfirmation <em>Subject Confirmation</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getSubjectConfirmationData <em>Subject Confirmation Data</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getSubjectLocality <em>Subject Locality</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot()
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
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_Mixed()
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
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_XMLNSPrefixMap()
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
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_XSISchemaLocation()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
	 *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
	 * @generated
	 */
	EMap<String, String> getXSISchemaLocation();

	/**
	 * Returns the value of the '<em><b>Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action</em>' containment reference.
	 * @see #setAction(ActionType)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_Action()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Action' namespace='##targetNamespace'"
	 * @generated
	 */
	ActionType getAction();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAction <em>Action</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Action</em>' containment reference.
	 * @see #getAction()
	 * @generated
	 */
	void setAction(ActionType value);

	/**
	 * Returns the value of the '<em><b>Advice</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Advice</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Advice</em>' containment reference.
	 * @see #setAdvice(AdviceType)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_Advice()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Advice' namespace='##targetNamespace'"
	 * @generated
	 */
	AdviceType getAdvice();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAdvice <em>Advice</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Advice</em>' containment reference.
	 * @see #getAdvice()
	 * @generated
	 */
	void setAdvice(AdviceType value);

	/**
	 * Returns the value of the '<em><b>Assertion</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assertion</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assertion</em>' containment reference.
	 * @see #setAssertion(AssertionType)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_Assertion()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Assertion' namespace='##targetNamespace'"
	 * @generated
	 */
	AssertionType getAssertion();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAssertion <em>Assertion</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Assertion</em>' containment reference.
	 * @see #getAssertion()
	 * @generated
	 */
	void setAssertion(AssertionType value);

	/**
	 * Returns the value of the '<em><b>Assertion ID Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assertion ID Ref</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assertion ID Ref</em>' attribute.
	 * @see #setAssertionIDRef(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_AssertionIDRef()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.NCName" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AssertionIDRef' namespace='##targetNamespace'"
	 * @generated
	 */
	String getAssertionIDRef();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAssertionIDRef <em>Assertion ID Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Assertion ID Ref</em>' attribute.
	 * @see #getAssertionIDRef()
	 * @generated
	 */
	void setAssertionIDRef(String value);

	/**
	 * Returns the value of the '<em><b>Assertion URI Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assertion URI Ref</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assertion URI Ref</em>' attribute.
	 * @see #setAssertionURIRef(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_AssertionURIRef()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AssertionURIRef' namespace='##targetNamespace'"
	 * @generated
	 */
	String getAssertionURIRef();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAssertionURIRef <em>Assertion URI Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Assertion URI Ref</em>' attribute.
	 * @see #getAssertionURIRef()
	 * @generated
	 */
	void setAssertionURIRef(String value);

	/**
	 * Returns the value of the '<em><b>Attribute</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute</em>' containment reference.
	 * @see #setAttribute(AttributeType)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_Attribute()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Attribute' namespace='##targetNamespace'"
	 * @generated
	 */
	AttributeType getAttribute();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAttribute <em>Attribute</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute</em>' containment reference.
	 * @see #getAttribute()
	 * @generated
	 */
	void setAttribute(AttributeType value);

	/**
	 * Returns the value of the '<em><b>Attribute Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Statement</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Statement</em>' containment reference.
	 * @see #setAttributeStatement(AttributeStatementType)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_AttributeStatement()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AttributeStatement' namespace='##targetNamespace'"
	 * @generated
	 */
	AttributeStatementType getAttributeStatement();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAttributeStatement <em>Attribute Statement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Statement</em>' containment reference.
	 * @see #getAttributeStatement()
	 * @generated
	 */
	void setAttributeStatement(AttributeStatementType value);

	/**
	 * Returns the value of the '<em><b>Attribute Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Value</em>' containment reference.
	 * @see #setAttributeValue(EObject)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_AttributeValue()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AttributeValue' namespace='##targetNamespace'"
	 * @generated
	 */
	EObject getAttributeValue();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAttributeValue <em>Attribute Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Value</em>' containment reference.
	 * @see #getAttributeValue()
	 * @generated
	 */
	void setAttributeValue(EObject value);

	/**
	 * Returns the value of the '<em><b>Audience</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Audience</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Audience</em>' attribute.
	 * @see #setAudience(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_Audience()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Audience' namespace='##targetNamespace'"
	 * @generated
	 */
	String getAudience();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAudience <em>Audience</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Audience</em>' attribute.
	 * @see #getAudience()
	 * @generated
	 */
	void setAudience(String value);

	/**
	 * Returns the value of the '<em><b>Audience Restriction</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Audience Restriction</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Audience Restriction</em>' containment reference.
	 * @see #setAudienceRestriction(AudienceRestrictionType)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_AudienceRestriction()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AudienceRestriction' namespace='##targetNamespace'"
	 * @generated
	 */
	AudienceRestrictionType getAudienceRestriction();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAudienceRestriction <em>Audience Restriction</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Audience Restriction</em>' containment reference.
	 * @see #getAudienceRestriction()
	 * @generated
	 */
	void setAudienceRestriction(AudienceRestrictionType value);

	/**
	 * Returns the value of the '<em><b>Authenticating Authority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authenticating Authority</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authenticating Authority</em>' attribute.
	 * @see #setAuthenticatingAuthority(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_AuthenticatingAuthority()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AuthenticatingAuthority' namespace='##targetNamespace'"
	 * @generated
	 */
	String getAuthenticatingAuthority();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthenticatingAuthority <em>Authenticating Authority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Authenticating Authority</em>' attribute.
	 * @see #getAuthenticatingAuthority()
	 * @generated
	 */
	void setAuthenticatingAuthority(String value);

	/**
	 * Returns the value of the '<em><b>Authn Context</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authn Context</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authn Context</em>' containment reference.
	 * @see #setAuthnContext(AuthnContextType)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_AuthnContext()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AuthnContext' namespace='##targetNamespace'"
	 * @generated
	 */
	AuthnContextType getAuthnContext();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthnContext <em>Authn Context</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Authn Context</em>' containment reference.
	 * @see #getAuthnContext()
	 * @generated
	 */
	void setAuthnContext(AuthnContextType value);

	/**
	 * Returns the value of the '<em><b>Authn Context Class Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authn Context Class Ref</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authn Context Class Ref</em>' attribute.
	 * @see #setAuthnContextClassRef(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_AuthnContextClassRef()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AuthnContextClassRef' namespace='##targetNamespace'"
	 * @generated
	 */
	String getAuthnContextClassRef();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthnContextClassRef <em>Authn Context Class Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Authn Context Class Ref</em>' attribute.
	 * @see #getAuthnContextClassRef()
	 * @generated
	 */
	void setAuthnContextClassRef(String value);

	/**
	 * Returns the value of the '<em><b>Authn Context Decl</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authn Context Decl</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authn Context Decl</em>' containment reference.
	 * @see #setAuthnContextDecl(EObject)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_AuthnContextDecl()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AuthnContextDecl' namespace='##targetNamespace'"
	 * @generated
	 */
	EObject getAuthnContextDecl();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthnContextDecl <em>Authn Context Decl</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Authn Context Decl</em>' containment reference.
	 * @see #getAuthnContextDecl()
	 * @generated
	 */
	void setAuthnContextDecl(EObject value);

	/**
	 * Returns the value of the '<em><b>Authn Context Decl Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authn Context Decl Ref</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authn Context Decl Ref</em>' attribute.
	 * @see #setAuthnContextDeclRef(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_AuthnContextDeclRef()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AuthnContextDeclRef' namespace='##targetNamespace'"
	 * @generated
	 */
	String getAuthnContextDeclRef();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthnContextDeclRef <em>Authn Context Decl Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Authn Context Decl Ref</em>' attribute.
	 * @see #getAuthnContextDeclRef()
	 * @generated
	 */
	void setAuthnContextDeclRef(String value);

	/**
	 * Returns the value of the '<em><b>Authn Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authn Statement</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authn Statement</em>' containment reference.
	 * @see #setAuthnStatement(AuthnStatementType)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_AuthnStatement()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AuthnStatement' namespace='##targetNamespace'"
	 * @generated
	 */
	AuthnStatementType getAuthnStatement();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthnStatement <em>Authn Statement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Authn Statement</em>' containment reference.
	 * @see #getAuthnStatement()
	 * @generated
	 */
	void setAuthnStatement(AuthnStatementType value);

	/**
	 * Returns the value of the '<em><b>Authz Decision Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authz Decision Statement</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authz Decision Statement</em>' containment reference.
	 * @see #setAuthzDecisionStatement(AuthzDecisionStatementType)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_AuthzDecisionStatement()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AuthzDecisionStatement' namespace='##targetNamespace'"
	 * @generated
	 */
	AuthzDecisionStatementType getAuthzDecisionStatement();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getAuthzDecisionStatement <em>Authz Decision Statement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Authz Decision Statement</em>' containment reference.
	 * @see #getAuthzDecisionStatement()
	 * @generated
	 */
	void setAuthzDecisionStatement(AuthzDecisionStatementType value);

	/**
	 * Returns the value of the '<em><b>Base ID</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base ID</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base ID</em>' containment reference.
	 * @see #setBaseID(BaseIDAbstractType)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_BaseID()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='BaseID' namespace='##targetNamespace'"
	 * @generated
	 */
	BaseIDAbstractType getBaseID();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getBaseID <em>Base ID</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base ID</em>' containment reference.
	 * @see #getBaseID()
	 * @generated
	 */
	void setBaseID(BaseIDAbstractType value);

	/**
	 * Returns the value of the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition</em>' containment reference.
	 * @see #setCondition(ConditionAbstractType)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_Condition()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Condition' namespace='##targetNamespace'"
	 * @generated
	 */
	ConditionAbstractType getCondition();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getCondition <em>Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition</em>' containment reference.
	 * @see #getCondition()
	 * @generated
	 */
	void setCondition(ConditionAbstractType value);

	/**
	 * Returns the value of the '<em><b>Conditions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Conditions</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Conditions</em>' containment reference.
	 * @see #setConditions(ConditionsType)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_Conditions()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Conditions' namespace='##targetNamespace'"
	 * @generated
	 */
	ConditionsType getConditions();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getConditions <em>Conditions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Conditions</em>' containment reference.
	 * @see #getConditions()
	 * @generated
	 */
	void setConditions(ConditionsType value);

	/**
	 * Returns the value of the '<em><b>Evidence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Evidence</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Evidence</em>' containment reference.
	 * @see #setEvidence(EvidenceType)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_Evidence()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Evidence' namespace='##targetNamespace'"
	 * @generated
	 */
	EvidenceType getEvidence();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getEvidence <em>Evidence</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Evidence</em>' containment reference.
	 * @see #getEvidence()
	 * @generated
	 */
	void setEvidence(EvidenceType value);

	/**
	 * Returns the value of the '<em><b>Issuer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Issuer</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Issuer</em>' containment reference.
	 * @see #setIssuer(NameIDType)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_Issuer()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Issuer' namespace='##targetNamespace'"
	 * @generated
	 */
	NameIDType getIssuer();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getIssuer <em>Issuer</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Issuer</em>' containment reference.
	 * @see #getIssuer()
	 * @generated
	 */
	void setIssuer(NameIDType value);

	/**
	 * Returns the value of the '<em><b>Name ID</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name ID</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name ID</em>' containment reference.
	 * @see #setNameID(NameIDType)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_NameID()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='NameID' namespace='##targetNamespace'"
	 * @generated
	 */
	NameIDType getNameID();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getNameID <em>Name ID</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name ID</em>' containment reference.
	 * @see #getNameID()
	 * @generated
	 */
	void setNameID(NameIDType value);

	/**
	 * Returns the value of the '<em><b>One Time Use</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>One Time Use</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>One Time Use</em>' containment reference.
	 * @see #setOneTimeUse(OneTimeUseType)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_OneTimeUse()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='OneTimeUse' namespace='##targetNamespace'"
	 * @generated
	 */
	OneTimeUseType getOneTimeUse();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getOneTimeUse <em>One Time Use</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>One Time Use</em>' containment reference.
	 * @see #getOneTimeUse()
	 * @generated
	 */
	void setOneTimeUse(OneTimeUseType value);

	/**
	 * Returns the value of the '<em><b>Proxy Restriction</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Proxy Restriction</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Proxy Restriction</em>' containment reference.
	 * @see #setProxyRestriction(ProxyRestrictionType)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_ProxyRestriction()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ProxyRestriction' namespace='##targetNamespace'"
	 * @generated
	 */
	ProxyRestrictionType getProxyRestriction();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getProxyRestriction <em>Proxy Restriction</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Proxy Restriction</em>' containment reference.
	 * @see #getProxyRestriction()
	 * @generated
	 */
	void setProxyRestriction(ProxyRestrictionType value);

	/**
	 * Returns the value of the '<em><b>Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Statement</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Statement</em>' containment reference.
	 * @see #setStatement(StatementAbstractType)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_Statement()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Statement' namespace='##targetNamespace'"
	 * @generated
	 */
	StatementAbstractType getStatement();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getStatement <em>Statement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Statement</em>' containment reference.
	 * @see #getStatement()
	 * @generated
	 */
	void setStatement(StatementAbstractType value);

	/**
	 * Returns the value of the '<em><b>Subject</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subject</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subject</em>' containment reference.
	 * @see #setSubject(SubjectType)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_Subject()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Subject' namespace='##targetNamespace'"
	 * @generated
	 */
	SubjectType getSubject();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getSubject <em>Subject</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subject</em>' containment reference.
	 * @see #getSubject()
	 * @generated
	 */
	void setSubject(SubjectType value);

	/**
	 * Returns the value of the '<em><b>Subject Confirmation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subject Confirmation</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subject Confirmation</em>' containment reference.
	 * @see #setSubjectConfirmation(SubjectConfirmationType)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_SubjectConfirmation()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='SubjectConfirmation' namespace='##targetNamespace'"
	 * @generated
	 */
	SubjectConfirmationType getSubjectConfirmation();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getSubjectConfirmation <em>Subject Confirmation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subject Confirmation</em>' containment reference.
	 * @see #getSubjectConfirmation()
	 * @generated
	 */
	void setSubjectConfirmation(SubjectConfirmationType value);

	/**
	 * Returns the value of the '<em><b>Subject Confirmation Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subject Confirmation Data</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subject Confirmation Data</em>' containment reference.
	 * @see #setSubjectConfirmationData(SubjectConfirmationDataType)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_SubjectConfirmationData()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='SubjectConfirmationData' namespace='##targetNamespace'"
	 * @generated
	 */
	SubjectConfirmationDataType getSubjectConfirmationData();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getSubjectConfirmationData <em>Subject Confirmation Data</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subject Confirmation Data</em>' containment reference.
	 * @see #getSubjectConfirmationData()
	 * @generated
	 */
	void setSubjectConfirmationData(SubjectConfirmationDataType value);

	/**
	 * Returns the value of the '<em><b>Subject Locality</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subject Locality</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subject Locality</em>' containment reference.
	 * @see #setSubjectLocality(SubjectLocalityType)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDocumentRoot_SubjectLocality()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='SubjectLocality' namespace='##targetNamespace'"
	 * @generated
	 */
	SubjectLocalityType getSubjectLocality();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot#getSubjectLocality <em>Subject Locality</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subject Locality</em>' containment reference.
	 * @see #getSubjectLocality()
	 * @generated
	 */
	void setSubjectLocality(SubjectLocalityType value);

} // DocumentRoot
