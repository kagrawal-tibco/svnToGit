/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

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
 * An implementation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getAction <em>Action</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getAdvice <em>Advice</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getAssertion <em>Assertion</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getAssertionIDRef <em>Assertion ID Ref</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getAssertionURIRef <em>Assertion URI Ref</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getAttributeStatement <em>Attribute Statement</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getAttributeValue <em>Attribute Value</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getAudience <em>Audience</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getAudienceRestriction <em>Audience Restriction</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getAuthenticatingAuthority <em>Authenticating Authority</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getAuthnContext <em>Authn Context</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getAuthnContextClassRef <em>Authn Context Class Ref</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getAuthnContextDecl <em>Authn Context Decl</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getAuthnContextDeclRef <em>Authn Context Decl Ref</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getAuthnStatement <em>Authn Statement</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getAuthzDecisionStatement <em>Authz Decision Statement</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getBaseID <em>Base ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getCondition <em>Condition</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getConditions <em>Conditions</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getEvidence <em>Evidence</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getIssuer <em>Issuer</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getNameID <em>Name ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getOneTimeUse <em>One Time Use</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getProxyRestriction <em>Proxy Restriction</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getStatement <em>Statement</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getSubject <em>Subject</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getSubjectConfirmation <em>Subject Confirmation</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getSubjectConfirmationData <em>Subject Confirmation Data</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.DocumentRootImpl#getSubjectLocality <em>Subject Locality</em>}</li>
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
	 * The default value of the '{@link #getAssertionIDRef() <em>Assertion ID Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssertionIDRef()
	 * @generated
	 * @ordered
	 */
	protected static final String ASSERTION_ID_REF_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getAssertionURIRef() <em>Assertion URI Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssertionURIRef()
	 * @generated
	 * @ordered
	 */
	protected static final String ASSERTION_URI_REF_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getAudience() <em>Audience</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAudience()
	 * @generated
	 * @ordered
	 */
	protected static final String AUDIENCE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getAuthenticatingAuthority() <em>Authenticating Authority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthenticatingAuthority()
	 * @generated
	 * @ordered
	 */
	protected static final String AUTHENTICATING_AUTHORITY_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getAuthnContextClassRef() <em>Authn Context Class Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthnContextClassRef()
	 * @generated
	 * @ordered
	 */
	protected static final String AUTHN_CONTEXT_CLASS_REF_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getAuthnContextDeclRef() <em>Authn Context Decl Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthnContextDeclRef()
	 * @generated
	 * @ordered
	 */
	protected static final String AUTHN_CONTEXT_DECL_REF_EDEFAULT = null;

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
		return AssertionPackage.Literals.DOCUMENT_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, AssertionPackage.DOCUMENT_ROOT__MIXED);
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
			xMLNSPrefixMap = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, AssertionPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
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
			xSISchemaLocation = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, AssertionPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		}
		return xSISchemaLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActionType getAction() {
		return (ActionType)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__ACTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAction(ActionType newAction, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(AssertionPackage.Literals.DOCUMENT_ROOT__ACTION, newAction, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAction(ActionType newAction) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__ACTION, newAction);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdviceType getAdvice() {
		return (AdviceType)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__ADVICE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAdvice(AdviceType newAdvice, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(AssertionPackage.Literals.DOCUMENT_ROOT__ADVICE, newAdvice, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAdvice(AdviceType newAdvice) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__ADVICE, newAdvice);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AssertionType getAssertion() {
		return (AssertionType)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__ASSERTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAssertion(AssertionType newAssertion, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(AssertionPackage.Literals.DOCUMENT_ROOT__ASSERTION, newAssertion, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAssertion(AssertionType newAssertion) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__ASSERTION, newAssertion);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAssertionIDRef() {
		return (String)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__ASSERTION_ID_REF, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAssertionIDRef(String newAssertionIDRef) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__ASSERTION_ID_REF, newAssertionIDRef);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAssertionURIRef() {
		return (String)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__ASSERTION_URI_REF, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAssertionURIRef(String newAssertionURIRef) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__ASSERTION_URI_REF, newAssertionURIRef);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeType getAttribute() {
		return (AttributeType)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__ATTRIBUTE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttribute(AttributeType newAttribute, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(AssertionPackage.Literals.DOCUMENT_ROOT__ATTRIBUTE, newAttribute, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttribute(AttributeType newAttribute) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__ATTRIBUTE, newAttribute);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeStatementType getAttributeStatement() {
		return (AttributeStatementType)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__ATTRIBUTE_STATEMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttributeStatement(AttributeStatementType newAttributeStatement, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(AssertionPackage.Literals.DOCUMENT_ROOT__ATTRIBUTE_STATEMENT, newAttributeStatement, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttributeStatement(AttributeStatementType newAttributeStatement) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__ATTRIBUTE_STATEMENT, newAttributeStatement);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getAttributeValue() {
		return (EObject)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__ATTRIBUTE_VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttributeValue(EObject newAttributeValue, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(AssertionPackage.Literals.DOCUMENT_ROOT__ATTRIBUTE_VALUE, newAttributeValue, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttributeValue(EObject newAttributeValue) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__ATTRIBUTE_VALUE, newAttributeValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAudience() {
		return (String)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__AUDIENCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAudience(String newAudience) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__AUDIENCE, newAudience);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AudienceRestrictionType getAudienceRestriction() {
		return (AudienceRestrictionType)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__AUDIENCE_RESTRICTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAudienceRestriction(AudienceRestrictionType newAudienceRestriction, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(AssertionPackage.Literals.DOCUMENT_ROOT__AUDIENCE_RESTRICTION, newAudienceRestriction, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAudienceRestriction(AudienceRestrictionType newAudienceRestriction) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__AUDIENCE_RESTRICTION, newAudienceRestriction);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAuthenticatingAuthority() {
		return (String)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__AUTHENTICATING_AUTHORITY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthenticatingAuthority(String newAuthenticatingAuthority) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__AUTHENTICATING_AUTHORITY, newAuthenticatingAuthority);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthnContextType getAuthnContext() {
		return (AuthnContextType)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__AUTHN_CONTEXT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAuthnContext(AuthnContextType newAuthnContext, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(AssertionPackage.Literals.DOCUMENT_ROOT__AUTHN_CONTEXT, newAuthnContext, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthnContext(AuthnContextType newAuthnContext) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__AUTHN_CONTEXT, newAuthnContext);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAuthnContextClassRef() {
		return (String)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__AUTHN_CONTEXT_CLASS_REF, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthnContextClassRef(String newAuthnContextClassRef) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__AUTHN_CONTEXT_CLASS_REF, newAuthnContextClassRef);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getAuthnContextDecl() {
		return (EObject)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__AUTHN_CONTEXT_DECL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAuthnContextDecl(EObject newAuthnContextDecl, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(AssertionPackage.Literals.DOCUMENT_ROOT__AUTHN_CONTEXT_DECL, newAuthnContextDecl, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthnContextDecl(EObject newAuthnContextDecl) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__AUTHN_CONTEXT_DECL, newAuthnContextDecl);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAuthnContextDeclRef() {
		return (String)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__AUTHN_CONTEXT_DECL_REF, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthnContextDeclRef(String newAuthnContextDeclRef) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__AUTHN_CONTEXT_DECL_REF, newAuthnContextDeclRef);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthnStatementType getAuthnStatement() {
		return (AuthnStatementType)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__AUTHN_STATEMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAuthnStatement(AuthnStatementType newAuthnStatement, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(AssertionPackage.Literals.DOCUMENT_ROOT__AUTHN_STATEMENT, newAuthnStatement, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthnStatement(AuthnStatementType newAuthnStatement) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__AUTHN_STATEMENT, newAuthnStatement);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthzDecisionStatementType getAuthzDecisionStatement() {
		return (AuthzDecisionStatementType)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__AUTHZ_DECISION_STATEMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAuthzDecisionStatement(AuthzDecisionStatementType newAuthzDecisionStatement, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(AssertionPackage.Literals.DOCUMENT_ROOT__AUTHZ_DECISION_STATEMENT, newAuthzDecisionStatement, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthzDecisionStatement(AuthzDecisionStatementType newAuthzDecisionStatement) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__AUTHZ_DECISION_STATEMENT, newAuthzDecisionStatement);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BaseIDAbstractType getBaseID() {
		return (BaseIDAbstractType)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__BASE_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBaseID(BaseIDAbstractType newBaseID, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(AssertionPackage.Literals.DOCUMENT_ROOT__BASE_ID, newBaseID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBaseID(BaseIDAbstractType newBaseID) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__BASE_ID, newBaseID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConditionAbstractType getCondition() {
		return (ConditionAbstractType)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__CONDITION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCondition(ConditionAbstractType newCondition, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(AssertionPackage.Literals.DOCUMENT_ROOT__CONDITION, newCondition, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCondition(ConditionAbstractType newCondition) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__CONDITION, newCondition);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConditionsType getConditions() {
		return (ConditionsType)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__CONDITIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConditions(ConditionsType newConditions, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(AssertionPackage.Literals.DOCUMENT_ROOT__CONDITIONS, newConditions, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConditions(ConditionsType newConditions) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__CONDITIONS, newConditions);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EvidenceType getEvidence() {
		return (EvidenceType)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__EVIDENCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEvidence(EvidenceType newEvidence, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(AssertionPackage.Literals.DOCUMENT_ROOT__EVIDENCE, newEvidence, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEvidence(EvidenceType newEvidence) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__EVIDENCE, newEvidence);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NameIDType getIssuer() {
		return (NameIDType)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__ISSUER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIssuer(NameIDType newIssuer, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(AssertionPackage.Literals.DOCUMENT_ROOT__ISSUER, newIssuer, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIssuer(NameIDType newIssuer) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__ISSUER, newIssuer);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NameIDType getNameID() {
		return (NameIDType)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__NAME_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNameID(NameIDType newNameID, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(AssertionPackage.Literals.DOCUMENT_ROOT__NAME_ID, newNameID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNameID(NameIDType newNameID) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__NAME_ID, newNameID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OneTimeUseType getOneTimeUse() {
		return (OneTimeUseType)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__ONE_TIME_USE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOneTimeUse(OneTimeUseType newOneTimeUse, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(AssertionPackage.Literals.DOCUMENT_ROOT__ONE_TIME_USE, newOneTimeUse, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOneTimeUse(OneTimeUseType newOneTimeUse) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__ONE_TIME_USE, newOneTimeUse);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProxyRestrictionType getProxyRestriction() {
		return (ProxyRestrictionType)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__PROXY_RESTRICTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProxyRestriction(ProxyRestrictionType newProxyRestriction, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(AssertionPackage.Literals.DOCUMENT_ROOT__PROXY_RESTRICTION, newProxyRestriction, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProxyRestriction(ProxyRestrictionType newProxyRestriction) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__PROXY_RESTRICTION, newProxyRestriction);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatementAbstractType getStatement() {
		return (StatementAbstractType)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__STATEMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStatement(StatementAbstractType newStatement, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(AssertionPackage.Literals.DOCUMENT_ROOT__STATEMENT, newStatement, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatement(StatementAbstractType newStatement) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__STATEMENT, newStatement);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SubjectType getSubject() {
		return (SubjectType)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__SUBJECT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSubject(SubjectType newSubject, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(AssertionPackage.Literals.DOCUMENT_ROOT__SUBJECT, newSubject, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubject(SubjectType newSubject) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__SUBJECT, newSubject);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SubjectConfirmationType getSubjectConfirmation() {
		return (SubjectConfirmationType)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__SUBJECT_CONFIRMATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSubjectConfirmation(SubjectConfirmationType newSubjectConfirmation, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(AssertionPackage.Literals.DOCUMENT_ROOT__SUBJECT_CONFIRMATION, newSubjectConfirmation, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubjectConfirmation(SubjectConfirmationType newSubjectConfirmation) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__SUBJECT_CONFIRMATION, newSubjectConfirmation);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SubjectConfirmationDataType getSubjectConfirmationData() {
		return (SubjectConfirmationDataType)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__SUBJECT_CONFIRMATION_DATA, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSubjectConfirmationData(SubjectConfirmationDataType newSubjectConfirmationData, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(AssertionPackage.Literals.DOCUMENT_ROOT__SUBJECT_CONFIRMATION_DATA, newSubjectConfirmationData, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubjectConfirmationData(SubjectConfirmationDataType newSubjectConfirmationData) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__SUBJECT_CONFIRMATION_DATA, newSubjectConfirmationData);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SubjectLocalityType getSubjectLocality() {
		return (SubjectLocalityType)getMixed().get(AssertionPackage.Literals.DOCUMENT_ROOT__SUBJECT_LOCALITY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSubjectLocality(SubjectLocalityType newSubjectLocality, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(AssertionPackage.Literals.DOCUMENT_ROOT__SUBJECT_LOCALITY, newSubjectLocality, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubjectLocality(SubjectLocalityType newSubjectLocality) {
		((FeatureMap.Internal)getMixed()).set(AssertionPackage.Literals.DOCUMENT_ROOT__SUBJECT_LOCALITY, newSubjectLocality);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AssertionPackage.DOCUMENT_ROOT__MIXED:
				return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
			case AssertionPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return ((InternalEList<?>)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
			case AssertionPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return ((InternalEList<?>)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
			case AssertionPackage.DOCUMENT_ROOT__ACTION:
				return basicSetAction(null, msgs);
			case AssertionPackage.DOCUMENT_ROOT__ADVICE:
				return basicSetAdvice(null, msgs);
			case AssertionPackage.DOCUMENT_ROOT__ASSERTION:
				return basicSetAssertion(null, msgs);
			case AssertionPackage.DOCUMENT_ROOT__ATTRIBUTE:
				return basicSetAttribute(null, msgs);
			case AssertionPackage.DOCUMENT_ROOT__ATTRIBUTE_STATEMENT:
				return basicSetAttributeStatement(null, msgs);
			case AssertionPackage.DOCUMENT_ROOT__ATTRIBUTE_VALUE:
				return basicSetAttributeValue(null, msgs);
			case AssertionPackage.DOCUMENT_ROOT__AUDIENCE_RESTRICTION:
				return basicSetAudienceRestriction(null, msgs);
			case AssertionPackage.DOCUMENT_ROOT__AUTHN_CONTEXT:
				return basicSetAuthnContext(null, msgs);
			case AssertionPackage.DOCUMENT_ROOT__AUTHN_CONTEXT_DECL:
				return basicSetAuthnContextDecl(null, msgs);
			case AssertionPackage.DOCUMENT_ROOT__AUTHN_STATEMENT:
				return basicSetAuthnStatement(null, msgs);
			case AssertionPackage.DOCUMENT_ROOT__AUTHZ_DECISION_STATEMENT:
				return basicSetAuthzDecisionStatement(null, msgs);
			case AssertionPackage.DOCUMENT_ROOT__BASE_ID:
				return basicSetBaseID(null, msgs);
			case AssertionPackage.DOCUMENT_ROOT__CONDITION:
				return basicSetCondition(null, msgs);
			case AssertionPackage.DOCUMENT_ROOT__CONDITIONS:
				return basicSetConditions(null, msgs);
			case AssertionPackage.DOCUMENT_ROOT__EVIDENCE:
				return basicSetEvidence(null, msgs);
			case AssertionPackage.DOCUMENT_ROOT__ISSUER:
				return basicSetIssuer(null, msgs);
			case AssertionPackage.DOCUMENT_ROOT__NAME_ID:
				return basicSetNameID(null, msgs);
			case AssertionPackage.DOCUMENT_ROOT__ONE_TIME_USE:
				return basicSetOneTimeUse(null, msgs);
			case AssertionPackage.DOCUMENT_ROOT__PROXY_RESTRICTION:
				return basicSetProxyRestriction(null, msgs);
			case AssertionPackage.DOCUMENT_ROOT__STATEMENT:
				return basicSetStatement(null, msgs);
			case AssertionPackage.DOCUMENT_ROOT__SUBJECT:
				return basicSetSubject(null, msgs);
			case AssertionPackage.DOCUMENT_ROOT__SUBJECT_CONFIRMATION:
				return basicSetSubjectConfirmation(null, msgs);
			case AssertionPackage.DOCUMENT_ROOT__SUBJECT_CONFIRMATION_DATA:
				return basicSetSubjectConfirmationData(null, msgs);
			case AssertionPackage.DOCUMENT_ROOT__SUBJECT_LOCALITY:
				return basicSetSubjectLocality(null, msgs);
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
			case AssertionPackage.DOCUMENT_ROOT__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case AssertionPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				if (coreType) return getXMLNSPrefixMap();
				else return getXMLNSPrefixMap().map();
			case AssertionPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				if (coreType) return getXSISchemaLocation();
				else return getXSISchemaLocation().map();
			case AssertionPackage.DOCUMENT_ROOT__ACTION:
				return getAction();
			case AssertionPackage.DOCUMENT_ROOT__ADVICE:
				return getAdvice();
			case AssertionPackage.DOCUMENT_ROOT__ASSERTION:
				return getAssertion();
			case AssertionPackage.DOCUMENT_ROOT__ASSERTION_ID_REF:
				return getAssertionIDRef();
			case AssertionPackage.DOCUMENT_ROOT__ASSERTION_URI_REF:
				return getAssertionURIRef();
			case AssertionPackage.DOCUMENT_ROOT__ATTRIBUTE:
				return getAttribute();
			case AssertionPackage.DOCUMENT_ROOT__ATTRIBUTE_STATEMENT:
				return getAttributeStatement();
			case AssertionPackage.DOCUMENT_ROOT__ATTRIBUTE_VALUE:
				return getAttributeValue();
			case AssertionPackage.DOCUMENT_ROOT__AUDIENCE:
				return getAudience();
			case AssertionPackage.DOCUMENT_ROOT__AUDIENCE_RESTRICTION:
				return getAudienceRestriction();
			case AssertionPackage.DOCUMENT_ROOT__AUTHENTICATING_AUTHORITY:
				return getAuthenticatingAuthority();
			case AssertionPackage.DOCUMENT_ROOT__AUTHN_CONTEXT:
				return getAuthnContext();
			case AssertionPackage.DOCUMENT_ROOT__AUTHN_CONTEXT_CLASS_REF:
				return getAuthnContextClassRef();
			case AssertionPackage.DOCUMENT_ROOT__AUTHN_CONTEXT_DECL:
				return getAuthnContextDecl();
			case AssertionPackage.DOCUMENT_ROOT__AUTHN_CONTEXT_DECL_REF:
				return getAuthnContextDeclRef();
			case AssertionPackage.DOCUMENT_ROOT__AUTHN_STATEMENT:
				return getAuthnStatement();
			case AssertionPackage.DOCUMENT_ROOT__AUTHZ_DECISION_STATEMENT:
				return getAuthzDecisionStatement();
			case AssertionPackage.DOCUMENT_ROOT__BASE_ID:
				return getBaseID();
			case AssertionPackage.DOCUMENT_ROOT__CONDITION:
				return getCondition();
			case AssertionPackage.DOCUMENT_ROOT__CONDITIONS:
				return getConditions();
			case AssertionPackage.DOCUMENT_ROOT__EVIDENCE:
				return getEvidence();
			case AssertionPackage.DOCUMENT_ROOT__ISSUER:
				return getIssuer();
			case AssertionPackage.DOCUMENT_ROOT__NAME_ID:
				return getNameID();
			case AssertionPackage.DOCUMENT_ROOT__ONE_TIME_USE:
				return getOneTimeUse();
			case AssertionPackage.DOCUMENT_ROOT__PROXY_RESTRICTION:
				return getProxyRestriction();
			case AssertionPackage.DOCUMENT_ROOT__STATEMENT:
				return getStatement();
			case AssertionPackage.DOCUMENT_ROOT__SUBJECT:
				return getSubject();
			case AssertionPackage.DOCUMENT_ROOT__SUBJECT_CONFIRMATION:
				return getSubjectConfirmation();
			case AssertionPackage.DOCUMENT_ROOT__SUBJECT_CONFIRMATION_DATA:
				return getSubjectConfirmationData();
			case AssertionPackage.DOCUMENT_ROOT__SUBJECT_LOCALITY:
				return getSubjectLocality();
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
			case AssertionPackage.DOCUMENT_ROOT__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__ACTION:
				setAction((ActionType)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__ADVICE:
				setAdvice((AdviceType)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__ASSERTION:
				setAssertion((AssertionType)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__ASSERTION_ID_REF:
				setAssertionIDRef((String)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__ASSERTION_URI_REF:
				setAssertionURIRef((String)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__ATTRIBUTE:
				setAttribute((AttributeType)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__ATTRIBUTE_STATEMENT:
				setAttributeStatement((AttributeStatementType)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__ATTRIBUTE_VALUE:
				setAttributeValue((EObject)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__AUDIENCE:
				setAudience((String)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__AUDIENCE_RESTRICTION:
				setAudienceRestriction((AudienceRestrictionType)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__AUTHENTICATING_AUTHORITY:
				setAuthenticatingAuthority((String)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__AUTHN_CONTEXT:
				setAuthnContext((AuthnContextType)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__AUTHN_CONTEXT_CLASS_REF:
				setAuthnContextClassRef((String)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__AUTHN_CONTEXT_DECL:
				setAuthnContextDecl((EObject)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__AUTHN_CONTEXT_DECL_REF:
				setAuthnContextDeclRef((String)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__AUTHN_STATEMENT:
				setAuthnStatement((AuthnStatementType)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__AUTHZ_DECISION_STATEMENT:
				setAuthzDecisionStatement((AuthzDecisionStatementType)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__BASE_ID:
				setBaseID((BaseIDAbstractType)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__CONDITION:
				setCondition((ConditionAbstractType)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__CONDITIONS:
				setConditions((ConditionsType)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__EVIDENCE:
				setEvidence((EvidenceType)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__ISSUER:
				setIssuer((NameIDType)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__NAME_ID:
				setNameID((NameIDType)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__ONE_TIME_USE:
				setOneTimeUse((OneTimeUseType)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__PROXY_RESTRICTION:
				setProxyRestriction((ProxyRestrictionType)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__STATEMENT:
				setStatement((StatementAbstractType)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__SUBJECT:
				setSubject((SubjectType)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__SUBJECT_CONFIRMATION:
				setSubjectConfirmation((SubjectConfirmationType)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__SUBJECT_CONFIRMATION_DATA:
				setSubjectConfirmationData((SubjectConfirmationDataType)newValue);
				return;
			case AssertionPackage.DOCUMENT_ROOT__SUBJECT_LOCALITY:
				setSubjectLocality((SubjectLocalityType)newValue);
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
			case AssertionPackage.DOCUMENT_ROOT__MIXED:
				getMixed().clear();
				return;
			case AssertionPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				getXMLNSPrefixMap().clear();
				return;
			case AssertionPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				getXSISchemaLocation().clear();
				return;
			case AssertionPackage.DOCUMENT_ROOT__ACTION:
				setAction((ActionType)null);
				return;
			case AssertionPackage.DOCUMENT_ROOT__ADVICE:
				setAdvice((AdviceType)null);
				return;
			case AssertionPackage.DOCUMENT_ROOT__ASSERTION:
				setAssertion((AssertionType)null);
				return;
			case AssertionPackage.DOCUMENT_ROOT__ASSERTION_ID_REF:
				setAssertionIDRef(ASSERTION_ID_REF_EDEFAULT);
				return;
			case AssertionPackage.DOCUMENT_ROOT__ASSERTION_URI_REF:
				setAssertionURIRef(ASSERTION_URI_REF_EDEFAULT);
				return;
			case AssertionPackage.DOCUMENT_ROOT__ATTRIBUTE:
				setAttribute((AttributeType)null);
				return;
			case AssertionPackage.DOCUMENT_ROOT__ATTRIBUTE_STATEMENT:
				setAttributeStatement((AttributeStatementType)null);
				return;
			case AssertionPackage.DOCUMENT_ROOT__ATTRIBUTE_VALUE:
				setAttributeValue((EObject)null);
				return;
			case AssertionPackage.DOCUMENT_ROOT__AUDIENCE:
				setAudience(AUDIENCE_EDEFAULT);
				return;
			case AssertionPackage.DOCUMENT_ROOT__AUDIENCE_RESTRICTION:
				setAudienceRestriction((AudienceRestrictionType)null);
				return;
			case AssertionPackage.DOCUMENT_ROOT__AUTHENTICATING_AUTHORITY:
				setAuthenticatingAuthority(AUTHENTICATING_AUTHORITY_EDEFAULT);
				return;
			case AssertionPackage.DOCUMENT_ROOT__AUTHN_CONTEXT:
				setAuthnContext((AuthnContextType)null);
				return;
			case AssertionPackage.DOCUMENT_ROOT__AUTHN_CONTEXT_CLASS_REF:
				setAuthnContextClassRef(AUTHN_CONTEXT_CLASS_REF_EDEFAULT);
				return;
			case AssertionPackage.DOCUMENT_ROOT__AUTHN_CONTEXT_DECL:
				setAuthnContextDecl((EObject)null);
				return;
			case AssertionPackage.DOCUMENT_ROOT__AUTHN_CONTEXT_DECL_REF:
				setAuthnContextDeclRef(AUTHN_CONTEXT_DECL_REF_EDEFAULT);
				return;
			case AssertionPackage.DOCUMENT_ROOT__AUTHN_STATEMENT:
				setAuthnStatement((AuthnStatementType)null);
				return;
			case AssertionPackage.DOCUMENT_ROOT__AUTHZ_DECISION_STATEMENT:
				setAuthzDecisionStatement((AuthzDecisionStatementType)null);
				return;
			case AssertionPackage.DOCUMENT_ROOT__BASE_ID:
				setBaseID((BaseIDAbstractType)null);
				return;
			case AssertionPackage.DOCUMENT_ROOT__CONDITION:
				setCondition((ConditionAbstractType)null);
				return;
			case AssertionPackage.DOCUMENT_ROOT__CONDITIONS:
				setConditions((ConditionsType)null);
				return;
			case AssertionPackage.DOCUMENT_ROOT__EVIDENCE:
				setEvidence((EvidenceType)null);
				return;
			case AssertionPackage.DOCUMENT_ROOT__ISSUER:
				setIssuer((NameIDType)null);
				return;
			case AssertionPackage.DOCUMENT_ROOT__NAME_ID:
				setNameID((NameIDType)null);
				return;
			case AssertionPackage.DOCUMENT_ROOT__ONE_TIME_USE:
				setOneTimeUse((OneTimeUseType)null);
				return;
			case AssertionPackage.DOCUMENT_ROOT__PROXY_RESTRICTION:
				setProxyRestriction((ProxyRestrictionType)null);
				return;
			case AssertionPackage.DOCUMENT_ROOT__STATEMENT:
				setStatement((StatementAbstractType)null);
				return;
			case AssertionPackage.DOCUMENT_ROOT__SUBJECT:
				setSubject((SubjectType)null);
				return;
			case AssertionPackage.DOCUMENT_ROOT__SUBJECT_CONFIRMATION:
				setSubjectConfirmation((SubjectConfirmationType)null);
				return;
			case AssertionPackage.DOCUMENT_ROOT__SUBJECT_CONFIRMATION_DATA:
				setSubjectConfirmationData((SubjectConfirmationDataType)null);
				return;
			case AssertionPackage.DOCUMENT_ROOT__SUBJECT_LOCALITY:
				setSubjectLocality((SubjectLocalityType)null);
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
			case AssertionPackage.DOCUMENT_ROOT__MIXED:
				return mixed != null && !mixed.isEmpty();
			case AssertionPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
			case AssertionPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
			case AssertionPackage.DOCUMENT_ROOT__ACTION:
				return getAction() != null;
			case AssertionPackage.DOCUMENT_ROOT__ADVICE:
				return getAdvice() != null;
			case AssertionPackage.DOCUMENT_ROOT__ASSERTION:
				return getAssertion() != null;
			case AssertionPackage.DOCUMENT_ROOT__ASSERTION_ID_REF:
				return ASSERTION_ID_REF_EDEFAULT == null ? getAssertionIDRef() != null : !ASSERTION_ID_REF_EDEFAULT.equals(getAssertionIDRef());
			case AssertionPackage.DOCUMENT_ROOT__ASSERTION_URI_REF:
				return ASSERTION_URI_REF_EDEFAULT == null ? getAssertionURIRef() != null : !ASSERTION_URI_REF_EDEFAULT.equals(getAssertionURIRef());
			case AssertionPackage.DOCUMENT_ROOT__ATTRIBUTE:
				return getAttribute() != null;
			case AssertionPackage.DOCUMENT_ROOT__ATTRIBUTE_STATEMENT:
				return getAttributeStatement() != null;
			case AssertionPackage.DOCUMENT_ROOT__ATTRIBUTE_VALUE:
				return getAttributeValue() != null;
			case AssertionPackage.DOCUMENT_ROOT__AUDIENCE:
				return AUDIENCE_EDEFAULT == null ? getAudience() != null : !AUDIENCE_EDEFAULT.equals(getAudience());
			case AssertionPackage.DOCUMENT_ROOT__AUDIENCE_RESTRICTION:
				return getAudienceRestriction() != null;
			case AssertionPackage.DOCUMENT_ROOT__AUTHENTICATING_AUTHORITY:
				return AUTHENTICATING_AUTHORITY_EDEFAULT == null ? getAuthenticatingAuthority() != null : !AUTHENTICATING_AUTHORITY_EDEFAULT.equals(getAuthenticatingAuthority());
			case AssertionPackage.DOCUMENT_ROOT__AUTHN_CONTEXT:
				return getAuthnContext() != null;
			case AssertionPackage.DOCUMENT_ROOT__AUTHN_CONTEXT_CLASS_REF:
				return AUTHN_CONTEXT_CLASS_REF_EDEFAULT == null ? getAuthnContextClassRef() != null : !AUTHN_CONTEXT_CLASS_REF_EDEFAULT.equals(getAuthnContextClassRef());
			case AssertionPackage.DOCUMENT_ROOT__AUTHN_CONTEXT_DECL:
				return getAuthnContextDecl() != null;
			case AssertionPackage.DOCUMENT_ROOT__AUTHN_CONTEXT_DECL_REF:
				return AUTHN_CONTEXT_DECL_REF_EDEFAULT == null ? getAuthnContextDeclRef() != null : !AUTHN_CONTEXT_DECL_REF_EDEFAULT.equals(getAuthnContextDeclRef());
			case AssertionPackage.DOCUMENT_ROOT__AUTHN_STATEMENT:
				return getAuthnStatement() != null;
			case AssertionPackage.DOCUMENT_ROOT__AUTHZ_DECISION_STATEMENT:
				return getAuthzDecisionStatement() != null;
			case AssertionPackage.DOCUMENT_ROOT__BASE_ID:
				return getBaseID() != null;
			case AssertionPackage.DOCUMENT_ROOT__CONDITION:
				return getCondition() != null;
			case AssertionPackage.DOCUMENT_ROOT__CONDITIONS:
				return getConditions() != null;
			case AssertionPackage.DOCUMENT_ROOT__EVIDENCE:
				return getEvidence() != null;
			case AssertionPackage.DOCUMENT_ROOT__ISSUER:
				return getIssuer() != null;
			case AssertionPackage.DOCUMENT_ROOT__NAME_ID:
				return getNameID() != null;
			case AssertionPackage.DOCUMENT_ROOT__ONE_TIME_USE:
				return getOneTimeUse() != null;
			case AssertionPackage.DOCUMENT_ROOT__PROXY_RESTRICTION:
				return getProxyRestriction() != null;
			case AssertionPackage.DOCUMENT_ROOT__STATEMENT:
				return getStatement() != null;
			case AssertionPackage.DOCUMENT_ROOT__SUBJECT:
				return getSubject() != null;
			case AssertionPackage.DOCUMENT_ROOT__SUBJECT_CONFIRMATION:
				return getSubjectConfirmation() != null;
			case AssertionPackage.DOCUMENT_ROOT__SUBJECT_CONFIRMATION_DATA:
				return getSubjectConfirmationData() != null;
			case AssertionPackage.DOCUMENT_ROOT__SUBJECT_LOCALITY:
				return getSubjectLocality() != null;
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
