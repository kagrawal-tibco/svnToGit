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
 * A representation of the model object '<em><b>Manage Name ID Request Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.ManageNameIDRequestType#getNameID <em>Name ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.ManageNameIDRequestType#getNewID <em>New ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.ManageNameIDRequestType#getTerminate <em>Terminate</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getManageNameIDRequestType()
 * @model extendedMetaData="name='ManageNameIDRequestType' kind='elementOnly'"
 * @generated
 */
public interface ManageNameIDRequestType extends RequestAbstractType {
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
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getManageNameIDRequestType_NameID()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='NameID' namespace='urn:oasis:names:tc:SAML:2.0:assertion'"
	 * @generated
	 */
	NameIDType getNameID();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ManageNameIDRequestType#getNameID <em>Name ID</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name ID</em>' containment reference.
	 * @see #getNameID()
	 * @generated
	 */
	void setNameID(NameIDType value);

	/**
	 * Returns the value of the '<em><b>New ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>New ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>New ID</em>' attribute.
	 * @see #setNewID(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getManageNameIDRequestType_NewID()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='NewID' namespace='##targetNamespace'"
	 * @generated
	 */
	String getNewID();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ManageNameIDRequestType#getNewID <em>New ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>New ID</em>' attribute.
	 * @see #getNewID()
	 * @generated
	 */
	void setNewID(String value);

	/**
	 * Returns the value of the '<em><b>Terminate</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Terminate</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Terminate</em>' containment reference.
	 * @see #setTerminate(TerminateType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getManageNameIDRequestType_Terminate()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Terminate' namespace='##targetNamespace'"
	 * @generated
	 */
	TerminateType getTerminate();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ManageNameIDRequestType#getTerminate <em>Terminate</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Terminate</em>' containment reference.
	 * @see #getTerminate()
	 * @generated
	 */
	void setTerminate(TerminateType value);

} // ManageNameIDRequestType
