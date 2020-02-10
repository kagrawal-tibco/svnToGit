/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Authn Statement Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType#getSubjectLocality <em>Subject Locality</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType#getAuthnContext <em>Authn Context</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType#getAuthnInstant <em>Authn Instant</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType#getSessionIndex <em>Session Index</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType#getSessionNotOnOrAfter <em>Session Not On Or After</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAuthnStatementType()
 * @model extendedMetaData="name='AuthnStatementType' kind='elementOnly'"
 * @generated
 */
public interface AuthnStatementType extends StatementAbstractType {
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
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAuthnStatementType_SubjectLocality()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='SubjectLocality' namespace='##targetNamespace'"
	 * @generated
	 */
	SubjectLocalityType getSubjectLocality();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType#getSubjectLocality <em>Subject Locality</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subject Locality</em>' containment reference.
	 * @see #getSubjectLocality()
	 * @generated
	 */
	void setSubjectLocality(SubjectLocalityType value);

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
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAuthnStatementType_AuthnContext()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='AuthnContext' namespace='##targetNamespace'"
	 * @generated
	 */
	AuthnContextType getAuthnContext();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType#getAuthnContext <em>Authn Context</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Authn Context</em>' containment reference.
	 * @see #getAuthnContext()
	 * @generated
	 */
	void setAuthnContext(AuthnContextType value);

	/**
	 * Returns the value of the '<em><b>Authn Instant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authn Instant</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authn Instant</em>' attribute.
	 * @see #setAuthnInstant(XMLGregorianCalendar)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAuthnStatementType_AuthnInstant()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime" required="true"
	 *        extendedMetaData="kind='attribute' name='AuthnInstant'"
	 * @generated
	 */
	XMLGregorianCalendar getAuthnInstant();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType#getAuthnInstant <em>Authn Instant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Authn Instant</em>' attribute.
	 * @see #getAuthnInstant()
	 * @generated
	 */
	void setAuthnInstant(XMLGregorianCalendar value);

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
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAuthnStatementType_SessionIndex()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='SessionIndex'"
	 * @generated
	 */
	String getSessionIndex();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType#getSessionIndex <em>Session Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Session Index</em>' attribute.
	 * @see #getSessionIndex()
	 * @generated
	 */
	void setSessionIndex(String value);

	/**
	 * Returns the value of the '<em><b>Session Not On Or After</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Session Not On Or After</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Session Not On Or After</em>' attribute.
	 * @see #setSessionNotOnOrAfter(XMLGregorianCalendar)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAuthnStatementType_SessionNotOnOrAfter()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
	 *        extendedMetaData="kind='attribute' name='SessionNotOnOrAfter'"
	 * @generated
	 */
	XMLGregorianCalendar getSessionNotOnOrAfter();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType#getSessionNotOnOrAfter <em>Session Not On Or After</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Session Not On Or After</em>' attribute.
	 * @see #getSessionNotOnOrAfter()
	 * @generated
	 */
	void setSessionNotOnOrAfter(XMLGregorianCalendar value);

} // AuthnStatementType
