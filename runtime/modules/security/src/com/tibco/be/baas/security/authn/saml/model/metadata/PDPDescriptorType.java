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
 * A representation of the model object '<em><b>PDP Descriptor Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.PDPDescriptorType#getAuthzService <em>Authz Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.PDPDescriptorType#getAssertionIDRequestService <em>Assertion ID Request Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.PDPDescriptorType#getNameIDFormat <em>Name ID Format</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getPDPDescriptorType()
 * @model extendedMetaData="name='PDPDescriptorType' kind='elementOnly'"
 * @generated
 */
public interface PDPDescriptorType extends RoleDescriptorType {
	/**
	 * Returns the value of the '<em><b>Authz Service</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authz Service</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authz Service</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getPDPDescriptorType_AuthzService()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='AuthzService' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<EndpointType> getAuthzService();

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
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getPDPDescriptorType_AssertionIDRequestService()
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
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getPDPDescriptorType_NameIDFormat()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='element' name='NameIDFormat' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<String> getNameIDFormat();

} // PDPDescriptorType
