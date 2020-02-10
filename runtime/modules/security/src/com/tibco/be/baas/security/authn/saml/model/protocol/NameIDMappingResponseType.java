/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol;

import com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Name ID Mapping Response Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingResponseType#getNameID <em>Name ID</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getNameIDMappingResponseType()
 * @model extendedMetaData="name='NameIDMappingResponseType' kind='elementOnly'"
 * @generated
 */
public interface NameIDMappingResponseType extends StatusResponseType {
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
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getNameIDMappingResponseType_NameID()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='NameID' namespace='urn:oasis:names:tc:SAML:2.0:assertion'"
	 * @generated
	 */
	NameIDType getNameID();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingResponseType#getNameID <em>Name ID</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name ID</em>' containment reference.
	 * @see #getNameID()
	 * @generated
	 */
	void setNameID(NameIDType value);

} // NameIDMappingResponseType
