/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol;

import org.eclipse.emf.ecore.EObject;

import com.tibco.be.baas.security.authn.saml.common.ISAMLObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Status Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusType#getStatusCode <em>Status Code</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusType#getStatusMessage <em>Status Message</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusType#getStatusDetail <em>Status Detail</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getStatusType()
 * @model extendedMetaData="name='StatusType' kind='elementOnly'"
 * @generated NOT
 */
public interface StatusType extends EObject, ISAMLObject {
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
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getStatusType_StatusCode()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='StatusCode' namespace='##targetNamespace'"
	 * @generated
	 */
	StatusCodeType getStatusCode();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusType#getStatusCode <em>Status Code</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Status Code</em>' containment reference.
	 * @see #getStatusCode()
	 * @generated
	 */
	void setStatusCode(StatusCodeType value);

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
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getStatusType_StatusMessage()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='StatusMessage' namespace='##targetNamespace'"
	 * @generated
	 */
	String getStatusMessage();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusType#getStatusMessage <em>Status Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Status Message</em>' attribute.
	 * @see #getStatusMessage()
	 * @generated
	 */
	void setStatusMessage(String value);

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
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getStatusType_StatusDetail()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='StatusDetail' namespace='##targetNamespace'"
	 * @generated
	 */
	StatusDetailType getStatusDetail();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.StatusType#getStatusDetail <em>Status Detail</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Status Detail</em>' containment reference.
	 * @see #getStatusDetail()
	 * @generated
	 */
	void setStatusDetail(StatusDetailType value);

} // StatusType
