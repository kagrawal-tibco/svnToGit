/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.metadata;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Organization Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.OrganizationType#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.OrganizationType#getOrganizationName <em>Organization Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.OrganizationType#getOrganizationDisplayName <em>Organization Display Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.OrganizationType#getOrganizationURL <em>Organization URL</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.OrganizationType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getOrganizationType()
 * @model extendedMetaData="name='OrganizationType' kind='elementOnly'"
 * @generated
 */
public interface OrganizationType extends EObject {
	/**
	 * Returns the value of the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extensions</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extensions</em>' containment reference.
	 * @see #setExtensions(ExtensionsType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getOrganizationType_Extensions()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Extensions' namespace='##targetNamespace'"
	 * @generated
	 */
	ExtensionsType getExtensions();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.OrganizationType#getExtensions <em>Extensions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extensions</em>' containment reference.
	 * @see #getExtensions()
	 * @generated
	 */
	void setExtensions(ExtensionsType value);

	/**
	 * Returns the value of the '<em><b>Organization Name</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedNameType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Organization Name</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Organization Name</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getOrganizationType_OrganizationName()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='OrganizationName' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<LocalizedNameType> getOrganizationName();

	/**
	 * Returns the value of the '<em><b>Organization Display Name</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedNameType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Organization Display Name</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Organization Display Name</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getOrganizationType_OrganizationDisplayName()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='OrganizationDisplayName' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<LocalizedNameType> getOrganizationDisplayName();

	/**
	 * Returns the value of the '<em><b>Organization URL</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedURIType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Organization URL</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Organization URL</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getOrganizationType_OrganizationURL()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='OrganizationURL' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<LocalizedURIType> getOrganizationURL();

	/**
	 * Returns the value of the '<em><b>Any Attribute</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Any Attribute</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Any Attribute</em>' attribute list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getOrganizationType_AnyAttribute()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':4' processing='lax'"
	 * @generated
	 */
	FeatureMap getAnyAttribute();

} // OrganizationType
