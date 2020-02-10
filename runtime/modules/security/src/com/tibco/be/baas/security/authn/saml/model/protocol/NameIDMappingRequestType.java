/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol;

import com.tibco.be.baas.security.authn.saml.model.assertion.BaseIDAbstractType;
import com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Name ID Mapping Request Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingRequestType#getBaseID <em>Base ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingRequestType#getNameID <em>Name ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingRequestType#getNameIDPolicy <em>Name ID Policy</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getNameIDMappingRequestType()
 * @model extendedMetaData="name='NameIDMappingRequestType' kind='elementOnly'"
 * @generated
 */
public interface NameIDMappingRequestType extends RequestAbstractType {
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
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getNameIDMappingRequestType_BaseID()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='BaseID' namespace='urn:oasis:names:tc:SAML:2.0:assertion'"
	 * @generated
	 */
	BaseIDAbstractType getBaseID();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingRequestType#getBaseID <em>Base ID</em>}' containment reference.
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
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getNameIDMappingRequestType_NameID()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='NameID' namespace='urn:oasis:names:tc:SAML:2.0:assertion'"
	 * @generated
	 */
	NameIDType getNameID();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingRequestType#getNameID <em>Name ID</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name ID</em>' containment reference.
	 * @see #getNameID()
	 * @generated
	 */
	void setNameID(NameIDType value);

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
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getNameIDMappingRequestType_NameIDPolicy()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='NameIDPolicy' namespace='##targetNamespace'"
	 * @generated
	 */
	NameIDPolicyType getNameIDPolicy();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingRequestType#getNameIDPolicy <em>Name ID Policy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name ID Policy</em>' containment reference.
	 * @see #getNameIDPolicy()
	 * @generated
	 */
	void setNameIDPolicy(NameIDPolicyType value);

} // NameIDMappingRequestType
