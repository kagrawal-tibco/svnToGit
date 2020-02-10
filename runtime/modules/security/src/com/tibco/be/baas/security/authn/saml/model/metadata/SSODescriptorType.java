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
 * A representation of the model object '<em><b>SSO Descriptor Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.SSODescriptorType#getArtifactResolutionService <em>Artifact Resolution Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.SSODescriptorType#getSingleLogoutService <em>Single Logout Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.SSODescriptorType#getManageNameIDService <em>Manage Name ID Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.SSODescriptorType#getNameIDFormat <em>Name ID Format</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getSSODescriptorType()
 * @model abstract="true"
 *        extendedMetaData="name='SSODescriptorType' kind='elementOnly'"
 * @generated
 */
public interface SSODescriptorType extends RoleDescriptorType {
	/**
	 * Returns the value of the '<em><b>Artifact Resolution Service</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.metadata.IndexedEndpointType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Artifact Resolution Service</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Artifact Resolution Service</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getSSODescriptorType_ArtifactResolutionService()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='ArtifactResolutionService' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<IndexedEndpointType> getArtifactResolutionService();

	/**
	 * Returns the value of the '<em><b>Single Logout Service</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Single Logout Service</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Single Logout Service</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getSSODescriptorType_SingleLogoutService()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='SingleLogoutService' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<EndpointType> getSingleLogoutService();

	/**
	 * Returns the value of the '<em><b>Manage Name ID Service</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Manage Name ID Service</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Manage Name ID Service</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getSSODescriptorType_ManageNameIDService()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='ManageNameIDService' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<EndpointType> getManageNameIDService();

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
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getSSODescriptorType_NameIDFormat()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='element' name='NameIDFormat' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<String> getNameIDFormat();

} // SSODescriptorType
