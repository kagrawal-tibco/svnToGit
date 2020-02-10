/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Status Code Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusCodeType#getStatusCode <em>Status Code</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusCodeType#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getStatusCodeType()
 * @model extendedMetaData="name='StatusCodeType' kind='elementOnly'"
 * @generated
 */
public interface StatusCodeType extends EObject {
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
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getStatusCodeType_StatusCode()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='StatusCode' namespace='##targetNamespace'"
	 * @generated
	 */
	StatusCodeType getStatusCode();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusCodeType#getStatusCode <em>Status Code</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Status Code</em>' containment reference.
	 * @see #getStatusCode()
	 * @generated
	 */
	void setStatusCode(StatusCodeType value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getStatusCodeType_Value()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI" required="true"
	 *        extendedMetaData="kind='attribute' name='Value'"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusCodeType#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

} // StatusCodeType
