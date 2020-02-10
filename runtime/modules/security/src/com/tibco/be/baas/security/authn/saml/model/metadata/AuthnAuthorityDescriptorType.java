/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.metadata;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Authn Authority Descriptor Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.AuthnAuthorityDescriptorType#getAuthnQueryService <em>Authn Query Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.AuthnAuthorityDescriptorType#getAssertionIDRequestService <em>Assertion ID Request Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.AuthnAuthorityDescriptorType#getNameIDFormat <em>Name ID Format</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getAuthnAuthorityDescriptorType()
 * @model extendedMetaData="name='AuthnAuthorityDescriptorType' kind='elementOnly'"
 * @generated
 */
public interface AuthnAuthorityDescriptorType extends RoleDescriptorType {
	/**
	 * Returns the value of the '<em><b>Authn Query Service</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authn Query Service</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authn Query Service</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getAuthnAuthorityDescriptorType_AuthnQueryService()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='AuthnQueryService' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<EndpointType> getAuthnQueryService();

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
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getAuthnAuthorityDescriptorType_AssertionIDRequestService()
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
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getAuthnAuthorityDescriptorType_NameIDFormat()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='element' name='NameIDFormat' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<String> getNameIDFormat();

} // AuthnAuthorityDescriptorType
