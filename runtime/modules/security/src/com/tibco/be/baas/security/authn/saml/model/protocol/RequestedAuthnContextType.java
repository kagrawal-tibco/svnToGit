/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Requested Authn Context Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestedAuthnContextType#getAuthnContextClassRef <em>Authn Context Class Ref</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestedAuthnContextType#getAuthnContextDeclRef <em>Authn Context Decl Ref</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestedAuthnContextType#getComparison <em>Comparison</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getRequestedAuthnContextType()
 * @model extendedMetaData="name='RequestedAuthnContextType' kind='elementOnly'"
 * @generated
 */
public interface RequestedAuthnContextType extends EObject {
	/**
	 * Returns the value of the '<em><b>Authn Context Class Ref</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authn Context Class Ref</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authn Context Class Ref</em>' attribute list.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getRequestedAuthnContextType_AuthnContextClassRef()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='element' name='AuthnContextClassRef' namespace='urn:oasis:names:tc:SAML:2.0:assertion'"
	 * @generated
	 */
	EList<String> getAuthnContextClassRef();

	/**
	 * Returns the value of the '<em><b>Authn Context Decl Ref</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authn Context Decl Ref</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authn Context Decl Ref</em>' attribute list.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getRequestedAuthnContextType_AuthnContextDeclRef()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='element' name='AuthnContextDeclRef' namespace='urn:oasis:names:tc:SAML:2.0:assertion'"
	 * @generated
	 */
	EList<String> getAuthnContextDeclRef();

	/**
	 * Returns the value of the '<em><b>Comparison</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnContextComparisonType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Comparison</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comparison</em>' attribute.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthnContextComparisonType
	 * @see #isSetComparison()
	 * @see #unsetComparison()
	 * @see #setComparison(AuthnContextComparisonType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getRequestedAuthnContextType_Comparison()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='Comparison'"
	 * @generated
	 */
	AuthnContextComparisonType getComparison();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestedAuthnContextType#getComparison <em>Comparison</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comparison</em>' attribute.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.AuthnContextComparisonType
	 * @see #isSetComparison()
	 * @see #unsetComparison()
	 * @see #getComparison()
	 * @generated
	 */
	void setComparison(AuthnContextComparisonType value);

	/**
	 * Unsets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestedAuthnContextType#getComparison <em>Comparison</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetComparison()
	 * @see #getComparison()
	 * @see #setComparison(AuthnContextComparisonType)
	 * @generated
	 */
	void unsetComparison();

	/**
	 * Returns whether the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.RequestedAuthnContextType#getComparison <em>Comparison</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Comparison</em>' attribute is set.
	 * @see #unsetComparison()
	 * @see #getComparison()
	 * @see #setComparison(AuthnContextComparisonType)
	 * @generated
	 */
	boolean isSetComparison();

} // RequestedAuthnContextType
