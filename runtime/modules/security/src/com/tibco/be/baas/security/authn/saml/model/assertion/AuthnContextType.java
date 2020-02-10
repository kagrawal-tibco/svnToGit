/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.baas.security.authn.saml.common.ISAMLObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Authn Context Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType#getAuthnContextClassRef <em>Authn Context Class Ref</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType#getAuthnContextDecl <em>Authn Context Decl</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType#getAuthnContextDeclRef <em>Authn Context Decl Ref</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType#getAuthnContextDecl1 <em>Authn Context Decl1</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType#getAuthnContextDeclRef1 <em>Authn Context Decl Ref1</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType#getAuthenticatingAuthority <em>Authenticating Authority</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAuthnContextType()
 * @model extendedMetaData="name='AuthnContextType' kind='elementOnly'"
 * @generated NOT
 */
public interface AuthnContextType extends EObject, ISAMLObject {
	/**
	 * Returns the value of the '<em><b>Authn Context Class Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authn Context Class Ref</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authn Context Class Ref</em>' attribute.
	 * @see #setAuthnContextClassRef(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAuthnContextType_AuthnContextClassRef()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='element' name='AuthnContextClassRef' namespace='##targetNamespace'"
	 * @generated
	 */
	String getAuthnContextClassRef();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType#getAuthnContextClassRef <em>Authn Context Class Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Authn Context Class Ref</em>' attribute.
	 * @see #getAuthnContextClassRef()
	 * @generated
	 */
	void setAuthnContextClassRef(String value);

	/**
	 * Returns the value of the '<em><b>Authn Context Decl</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authn Context Decl</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authn Context Decl</em>' containment reference.
	 * @see #setAuthnContextDecl(EObject)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAuthnContextType_AuthnContextDecl()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='AuthnContextDecl' namespace='##targetNamespace'"
	 * @generated
	 */
	EObject getAuthnContextDecl();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType#getAuthnContextDecl <em>Authn Context Decl</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Authn Context Decl</em>' containment reference.
	 * @see #getAuthnContextDecl()
	 * @generated
	 */
	void setAuthnContextDecl(EObject value);

	/**
	 * Returns the value of the '<em><b>Authn Context Decl Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authn Context Decl Ref</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authn Context Decl Ref</em>' attribute.
	 * @see #setAuthnContextDeclRef(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAuthnContextType_AuthnContextDeclRef()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='element' name='AuthnContextDeclRef' namespace='##targetNamespace'"
	 * @generated
	 */
	String getAuthnContextDeclRef();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType#getAuthnContextDeclRef <em>Authn Context Decl Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Authn Context Decl Ref</em>' attribute.
	 * @see #getAuthnContextDeclRef()
	 * @generated
	 */
	void setAuthnContextDeclRef(String value);

	/**
	 * Returns the value of the '<em><b>Authn Context Decl1</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authn Context Decl1</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authn Context Decl1</em>' containment reference.
	 * @see #setAuthnContextDecl1(EObject)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAuthnContextType_AuthnContextDecl1()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='AuthnContextDecl' namespace='##targetNamespace'"
	 * @generated
	 */
	EObject getAuthnContextDecl1();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType#getAuthnContextDecl1 <em>Authn Context Decl1</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Authn Context Decl1</em>' containment reference.
	 * @see #getAuthnContextDecl1()
	 * @generated
	 */
	void setAuthnContextDecl1(EObject value);

	/**
	 * Returns the value of the '<em><b>Authn Context Decl Ref1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authn Context Decl Ref1</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authn Context Decl Ref1</em>' attribute.
	 * @see #setAuthnContextDeclRef1(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAuthnContextType_AuthnContextDeclRef1()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='element' name='AuthnContextDeclRef' namespace='##targetNamespace'"
	 * @generated
	 */
	String getAuthnContextDeclRef1();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType#getAuthnContextDeclRef1 <em>Authn Context Decl Ref1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Authn Context Decl Ref1</em>' attribute.
	 * @see #getAuthnContextDeclRef1()
	 * @generated
	 */
	void setAuthnContextDeclRef1(String value);

	/**
	 * Returns the value of the '<em><b>Authenticating Authority</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authenticating Authority</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authenticating Authority</em>' attribute list.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAuthnContextType_AuthenticatingAuthority()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='element' name='AuthenticatingAuthority' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<String> getAuthenticatingAuthority();

} // AuthnContextType
