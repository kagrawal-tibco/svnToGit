/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.metadata;

import org.eclipse.emf.common.util.EList;

import com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IDPSSO Descriptor Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType#getSingleSignOnService <em>Single Sign On Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType#getNameIDMappingService <em>Name ID Mapping Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType#getAssertionIDRequestService <em>Assertion ID Request Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType#getAttributeProfile <em>Attribute Profile</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType#isWantAuthnRequestsSigned <em>Want Authn Requests Signed</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getIDPSSODescriptorType()
 * @model extendedMetaData="name='IDPSSODescriptorType' kind='elementOnly'"
 * @generated
 */
public interface IDPSSODescriptorType extends SSODescriptorType {
	/**
	 * Returns the value of the '<em><b>Single Sign On Service</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Single Sign On Service</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Single Sign On Service</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getIDPSSODescriptorType_SingleSignOnService()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='SingleSignOnService' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<EndpointType> getSingleSignOnService();

	/**
	 * Returns the value of the '<em><b>Name ID Mapping Service</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name ID Mapping Service</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name ID Mapping Service</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getIDPSSODescriptorType_NameIDMappingService()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='NameIDMappingService' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<EndpointType> getNameIDMappingService();

	/**
	 * Returns the value of the '<em><b>Assertion ID Request Service</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assertion ID Request Service</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assertion ID Request Service</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getIDPSSODescriptorType_AssertionIDRequestService()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='AssertionIDRequestService' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<EndpointType> getAssertionIDRequestService();

	/**
	 * Returns the value of the '<em><b>Attribute Profile</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Profile</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Profile</em>' attribute list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getIDPSSODescriptorType_AttributeProfile()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='element' name='AttributeProfile' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<String> getAttributeProfile();

	/**
	 * Returns the value of the '<em><b>Attribute</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getIDPSSODescriptorType_Attribute()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Attribute' namespace='urn:oasis:names:tc:SAML:2.0:assertion'"
	 * @generated
	 */
	EList<AttributeType> getAttribute();

	/**
	 * Returns the value of the '<em><b>Want Authn Requests Signed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Want Authn Requests Signed</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Want Authn Requests Signed</em>' attribute.
	 * @see #isSetWantAuthnRequestsSigned()
	 * @see #unsetWantAuthnRequestsSigned()
	 * @see #setWantAuthnRequestsSigned(boolean)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getIDPSSODescriptorType_WantAuthnRequestsSigned()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='WantAuthnRequestsSigned'"
	 * @generated
	 */
	boolean isWantAuthnRequestsSigned();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType#isWantAuthnRequestsSigned <em>Want Authn Requests Signed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Want Authn Requests Signed</em>' attribute.
	 * @see #isSetWantAuthnRequestsSigned()
	 * @see #unsetWantAuthnRequestsSigned()
	 * @see #isWantAuthnRequestsSigned()
	 * @generated
	 */
	void setWantAuthnRequestsSigned(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType#isWantAuthnRequestsSigned <em>Want Authn Requests Signed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetWantAuthnRequestsSigned()
	 * @see #isWantAuthnRequestsSigned()
	 * @see #setWantAuthnRequestsSigned(boolean)
	 * @generated
	 */
	void unsetWantAuthnRequestsSigned();

	/**
	 * Returns whether the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType#isWantAuthnRequestsSigned <em>Want Authn Requests Signed</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Want Authn Requests Signed</em>' attribute is set.
	 * @see #unsetWantAuthnRequestsSigned()
	 * @see #isWantAuthnRequestsSigned()
	 * @see #setWantAuthnRequestsSigned(boolean)
	 * @generated
	 */
	boolean isSetWantAuthnRequestsSigned();

} // IDPSSODescriptorType
