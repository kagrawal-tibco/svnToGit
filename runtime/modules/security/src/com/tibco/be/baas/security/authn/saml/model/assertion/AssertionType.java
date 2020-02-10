/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

import com.tibco.be.baas.security.authn.saml.common.ISAMLObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getIssuer <em>Issuer</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getSubject <em>Subject</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getConditions <em>Conditions</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getAdvice <em>Advice</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getStatement <em>Statement</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getAuthnStatement <em>Authn Statement</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getAuthzDecisionStatement <em>Authz Decision Statement</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getAttributeStatement <em>Attribute Statement</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getID <em>ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getIssueInstant <em>Issue Instant</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAssertionType()
 * @model extendedMetaData="name='AssertionType' kind='elementOnly'"
 * @generated NOT
 */
public interface AssertionType extends EObject, ISAMLObject {
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
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAssertionType_Issuer()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='Issuer' namespace='##targetNamespace'"
	 * @generated
	 */
	NameIDType getIssuer();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getIssuer <em>Issuer</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Issuer</em>' containment reference.
	 * @see #getIssuer()
	 * @generated
	 */
	void setIssuer(NameIDType value);

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
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAssertionType_Subject()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Subject' namespace='##targetNamespace'"
	 * @generated
	 */
	SubjectType getSubject();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getSubject <em>Subject</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subject</em>' containment reference.
	 * @see #getSubject()
	 * @generated
	 */
	void setSubject(SubjectType value);

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
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAssertionType_Conditions()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Conditions' namespace='##targetNamespace'"
	 * @generated
	 */
	ConditionsType getConditions();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getConditions <em>Conditions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Conditions</em>' containment reference.
	 * @see #getConditions()
	 * @generated
	 */
	void setConditions(ConditionsType value);

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
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAssertionType_Advice()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Advice' namespace='##targetNamespace'"
	 * @generated
	 */
	AdviceType getAdvice();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getAdvice <em>Advice</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Advice</em>' containment reference.
	 * @see #getAdvice()
	 * @generated
	 */
	void setAdvice(AdviceType value);

	/**
	 * Returns the value of the '<em><b>Group</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Group</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group</em>' attribute list.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAssertionType_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:4'"
	 * @generated
	 */
	FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>Statement</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.assertion.StatementAbstractType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Statement</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Statement</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAssertionType_Statement()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Statement' namespace='##targetNamespace' group='#group:4'"
	 * @generated
	 */
	EList<StatementAbstractType> getStatement();

	/**
	 * Returns the value of the '<em><b>Authn Statement</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authn Statement</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authn Statement</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAssertionType_AuthnStatement()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AuthnStatement' namespace='##targetNamespace' group='#group:4'"
	 * @generated
	 */
	EList<AuthnStatementType> getAuthnStatement();

	/**
	 * Returns the value of the '<em><b>Authz Decision Statement</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthzDecisionStatementType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authz Decision Statement</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authz Decision Statement</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAssertionType_AuthzDecisionStatement()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AuthzDecisionStatement' namespace='##targetNamespace' group='#group:4'"
	 * @generated
	 */
	EList<AuthzDecisionStatementType> getAuthzDecisionStatement();

	/**
	 * Returns the value of the '<em><b>Attribute Statement</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.assertion.AttributeStatementType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Statement</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Statement</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAssertionType_AttributeStatement()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AttributeStatement' namespace='##targetNamespace' group='#group:4'"
	 * @generated
	 */
	EList<AttributeStatementType> getAttributeStatement();

	/**
	 * Returns the value of the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>ID</em>' attribute.
	 * @see #setID(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAssertionType_ID()
	 * @model id="true" dataType="org.eclipse.emf.ecore.xml.type.ID" required="true"
	 *        extendedMetaData="kind='attribute' name='ID'"
	 * @generated
	 */
	String getID();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getID <em>ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>ID</em>' attribute.
	 * @see #getID()
	 * @generated
	 */
	void setID(String value);

	/**
	 * Returns the value of the '<em><b>Issue Instant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Issue Instant</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Issue Instant</em>' attribute.
	 * @see #setIssueInstant(XMLGregorianCalendar)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAssertionType_IssueInstant()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime" required="true"
	 *        extendedMetaData="kind='attribute' name='IssueInstant'"
	 * @generated
	 */
	XMLGregorianCalendar getIssueInstant();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getIssueInstant <em>Issue Instant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Issue Instant</em>' attribute.
	 * @see #getIssueInstant()
	 * @generated
	 */
	void setIssueInstant(XMLGregorianCalendar value);

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAssertionType_Version()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='Version'"
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

} // AssertionType
