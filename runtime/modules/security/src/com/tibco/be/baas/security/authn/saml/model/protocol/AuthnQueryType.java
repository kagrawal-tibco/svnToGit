/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Authn Query Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnQueryType#getRequestedAuthnContext <em>Requested Authn Context</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnQueryType#getSessionIndex <em>Session Index</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getAuthnQueryType()
 * @model extendedMetaData="name='AuthnQueryType' kind='elementOnly'"
 * @generated
 */
public interface AuthnQueryType extends SubjectQueryAbstractType {
	/**
	 * Returns the value of the '<em><b>Requested Authn Context</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Requested Authn Context</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Requested Authn Context</em>' containment reference.
	 * @see #setRequestedAuthnContext(RequestedAuthnContextType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getAuthnQueryType_RequestedAuthnContext()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='RequestedAuthnContext' namespace='##targetNamespace'"
	 * @generated
	 */
	RequestedAuthnContextType getRequestedAuthnContext();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnQueryType#getRequestedAuthnContext <em>Requested Authn Context</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Requested Authn Context</em>' containment reference.
	 * @see #getRequestedAuthnContext()
	 * @generated
	 */
	void setRequestedAuthnContext(RequestedAuthnContextType value);

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
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getAuthnQueryType_SessionIndex()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='SessionIndex'"
	 * @generated
	 */
	String getSessionIndex();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnQueryType#getSessionIndex <em>Session Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Session Index</em>' attribute.
	 * @see #getSessionIndex()
	 * @generated
	 */
	void setSessionIndex(String value);

} // AuthnQueryType
