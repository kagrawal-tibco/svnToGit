/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.ecore.EObject;

import com.tibco.be.baas.security.authn.saml.common.ISAMLObject;
import com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Request Abstract Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getIssuer <em>Issuer</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getConsent <em>Consent</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getDestination <em>Destination</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getID <em>ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getIssueInstant <em>Issue Instant</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getRequestAbstractType()
 * @model abstract="true"
 *        extendedMetaData="name='RequestAbstractType' kind='elementOnly'"
 * @generated NOT
 */
public interface RequestAbstractType extends EObject, ISAMLObject {
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
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getRequestAbstractType_Issuer()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Issuer' namespace='urn:oasis:names:tc:SAML:2.0:assertion'"
	 * @generated
	 */
	NameIDType getIssuer();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getIssuer <em>Issuer</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Issuer</em>' containment reference.
	 * @see #getIssuer()
	 * @generated
	 */
	void setIssuer(NameIDType value);

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
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getRequestAbstractType_Extensions()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Extensions' namespace='##targetNamespace'"
	 * @generated
	 */
	ExtensionsType getExtensions();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getExtensions <em>Extensions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extensions</em>' containment reference.
	 * @see #getExtensions()
	 * @generated
	 */
	void setExtensions(ExtensionsType value);

	/**
	 * Returns the value of the '<em><b>Consent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Consent</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Consent</em>' attribute.
	 * @see #setConsent(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getRequestAbstractType_Consent()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='attribute' name='Consent'"
	 * @generated
	 */
	String getConsent();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getConsent <em>Consent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Consent</em>' attribute.
	 * @see #getConsent()
	 * @generated
	 */
	void setConsent(String value);

	/**
	 * Returns the value of the '<em><b>Destination</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Destination</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Destination</em>' attribute.
	 * @see #setDestination(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getRequestAbstractType_Destination()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='attribute' name='Destination'"
	 * @generated
	 */
	String getDestination();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getDestination <em>Destination</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Destination</em>' attribute.
	 * @see #getDestination()
	 * @generated
	 */
	void setDestination(String value);

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
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getRequestAbstractType_ID()
	 * @model id="true" dataType="org.eclipse.emf.ecore.xml.type.ID" required="true"
	 *        extendedMetaData="kind='attribute' name='ID'"
	 * @generated
	 */
	String getID();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getID <em>ID</em>}' attribute.
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
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getRequestAbstractType_IssueInstant()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime" required="true"
	 *        extendedMetaData="kind='attribute' name='IssueInstant'"
	 * @generated
	 */
	XMLGregorianCalendar getIssueInstant();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getIssueInstant <em>Issue Instant</em>}' attribute.
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
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getRequestAbstractType_Version()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='Version'"
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

} // RequestAbstractType
