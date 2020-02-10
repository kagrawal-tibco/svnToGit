/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.EList;

import com.tibco.be.baas.security.authn.saml.model.assertion.BaseIDAbstractType;
import com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Logout Request Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.LogoutRequestType#getBaseID <em>Base ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.LogoutRequestType#getNameID <em>Name ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.LogoutRequestType#getSessionIndex <em>Session Index</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.LogoutRequestType#getNotOnOrAfter <em>Not On Or After</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.LogoutRequestType#getReason <em>Reason</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getLogoutRequestType()
 * @model extendedMetaData="name='LogoutRequestType' kind='elementOnly'"
 * @generated
 */
public interface LogoutRequestType extends RequestAbstractType {
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
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getLogoutRequestType_BaseID()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='BaseID' namespace='urn:oasis:names:tc:SAML:2.0:assertion'"
	 * @generated
	 */
	BaseIDAbstractType getBaseID();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.LogoutRequestType#getBaseID <em>Base ID</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base ID</em>' containment reference.
	 * @see #getBaseID()
	 * @generated
	 */
	void setBaseID(BaseIDAbstractType value);

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
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getLogoutRequestType_NameID()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='NameID' namespace='urn:oasis:names:tc:SAML:2.0:assertion'"
	 * @generated
	 */
	NameIDType getNameID();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.LogoutRequestType#getNameID <em>Name ID</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name ID</em>' containment reference.
	 * @see #getNameID()
	 * @generated
	 */
	void setNameID(NameIDType value);

	/**
	 * Returns the value of the '<em><b>Session Index</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Session Index</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Session Index</em>' attribute list.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getLogoutRequestType_SessionIndex()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='SessionIndex' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<String> getSessionIndex();

	/**
	 * Returns the value of the '<em><b>Not On Or After</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Not On Or After</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Not On Or After</em>' attribute.
	 * @see #setNotOnOrAfter(XMLGregorianCalendar)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getLogoutRequestType_NotOnOrAfter()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
	 *        extendedMetaData="kind='attribute' name='NotOnOrAfter'"
	 * @generated
	 */
	XMLGregorianCalendar getNotOnOrAfter();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.LogoutRequestType#getNotOnOrAfter <em>Not On Or After</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Not On Or After</em>' attribute.
	 * @see #getNotOnOrAfter()
	 * @generated
	 */
	void setNotOnOrAfter(XMLGregorianCalendar value);

	/**
	 * Returns the value of the '<em><b>Reason</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reason</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reason</em>' attribute.
	 * @see #setReason(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getLogoutRequestType_Reason()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='Reason'"
	 * @generated
	 */
	String getReason();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.LogoutRequestType#getReason <em>Reason</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reason</em>' attribute.
	 * @see #getReason()
	 * @generated
	 */
	void setReason(String value);

} // LogoutRequestType
