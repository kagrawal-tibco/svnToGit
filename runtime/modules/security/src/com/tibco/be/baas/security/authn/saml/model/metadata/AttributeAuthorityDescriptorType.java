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
 * A representation of the model object '<em><b>Attribute Authority Descriptor Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeAuthorityDescriptorType#getAttributeService <em>Attribute Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeAuthorityDescriptorType#getAssertionIDRequestService <em>Assertion ID Request Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeAuthorityDescriptorType#getNameIDFormat <em>Name ID Format</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeAuthorityDescriptorType#getAttributeProfile <em>Attribute Profile</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeAuthorityDescriptorType#getAttribute <em>Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getAttributeAuthorityDescriptorType()
 * @model extendedMetaData="name='AttributeAuthorityDescriptorType' kind='elementOnly'"
 * @generated
 */
public interface AttributeAuthorityDescriptorType extends RoleDescriptorType {
	/**
	 * Returns the value of the '<em><b>Attribute Service</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Service</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Service</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getAttributeAuthorityDescriptorType_AttributeService()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='AttributeService' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<EndpointType> getAttributeService();

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
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getAttributeAuthorityDescriptorType_AssertionIDRequestService()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='AssertionIDRequestService' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<EndpointType> getAssertionIDRequestService();

	/**
	 * Returns the value of the '<em><b>Name ID Format</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name ID Format</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name ID Format</em>' attribute list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getAttributeAuthorityDescriptorType_NameIDFormat()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='element' name='NameIDFormat' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<String> getNameIDFormat();

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
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getAttributeAuthorityDescriptorType_AttributeProfile()
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
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getAttributeAuthorityDescriptorType_Attribute()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Attribute' namespace='urn:oasis:names:tc:SAML:2.0:assertion'"
	 * @generated
	 */
	EList<AttributeType> getAttribute();

} // AttributeAuthorityDescriptorType
