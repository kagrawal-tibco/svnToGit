/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.metadata;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entity Descriptor Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getRoleDescriptor <em>Role Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getIDPSSODescriptor <em>IDPSSO Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getSPSSODescriptor <em>SPSSO Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getAuthnAuthorityDescriptor <em>Authn Authority Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getAttributeAuthorityDescriptor <em>Attribute Authority Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getPDPDescriptor <em>PDP Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getAffiliationDescriptor <em>Affiliation Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getOrganization <em>Organization</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getContactPerson <em>Contact Person</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getAdditionalMetadataLocation <em>Additional Metadata Location</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getCacheDuration <em>Cache Duration</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getEntityID <em>Entity ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getID <em>ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getValidUntil <em>Valid Until</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEntityDescriptorType()
 * @model extendedMetaData="name='EntityDescriptorType' kind='elementOnly'"
 * @generated
 */
public interface EntityDescriptorType extends EObject {
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
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEntityDescriptorType_Extensions()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Extensions' namespace='##targetNamespace'"
	 * @generated
	 */
	ExtensionsType getExtensions();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getExtensions <em>Extensions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extensions</em>' containment reference.
	 * @see #getExtensions()
	 * @generated
	 */
	void setExtensions(ExtensionsType value);

	/**
	 * Returns the value of the '<em><b>Group</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Group</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group</em>' attribute list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEntityDescriptorType_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:1'"
	 * @generated
	 */
	FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>Role Descriptor</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Role Descriptor</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Role Descriptor</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEntityDescriptorType_RoleDescriptor()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='RoleDescriptor' namespace='##targetNamespace' group='#group:1'"
	 * @generated
	 */
	EList<RoleDescriptorType> getRoleDescriptor();

	/**
	 * Returns the value of the '<em><b>IDPSSO Descriptor</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>IDPSSO Descriptor</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>IDPSSO Descriptor</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEntityDescriptorType_IDPSSODescriptor()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='IDPSSODescriptor' namespace='##targetNamespace' group='#group:1'"
	 * @generated
	 */
	EList<IDPSSODescriptorType> getIDPSSODescriptor();

	/**
	 * Returns the value of the '<em><b>SPSSO Descriptor</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>SPSSO Descriptor</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>SPSSO Descriptor</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEntityDescriptorType_SPSSODescriptor()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='SPSSODescriptor' namespace='##targetNamespace' group='#group:1'"
	 * @generated
	 */
	EList<SPSSODescriptorType> getSPSSODescriptor();

	/**
	 * Returns the value of the '<em><b>Authn Authority Descriptor</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.metadata.AuthnAuthorityDescriptorType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authn Authority Descriptor</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authn Authority Descriptor</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEntityDescriptorType_AuthnAuthorityDescriptor()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AuthnAuthorityDescriptor' namespace='##targetNamespace' group='#group:1'"
	 * @generated
	 */
	EList<AuthnAuthorityDescriptorType> getAuthnAuthorityDescriptor();

	/**
	 * Returns the value of the '<em><b>Attribute Authority Descriptor</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeAuthorityDescriptorType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Authority Descriptor</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Authority Descriptor</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEntityDescriptorType_AttributeAuthorityDescriptor()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AttributeAuthorityDescriptor' namespace='##targetNamespace' group='#group:1'"
	 * @generated
	 */
	EList<AttributeAuthorityDescriptorType> getAttributeAuthorityDescriptor();

	/**
	 * Returns the value of the '<em><b>PDP Descriptor</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.metadata.PDPDescriptorType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>PDP Descriptor</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>PDP Descriptor</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEntityDescriptorType_PDPDescriptor()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='PDPDescriptor' namespace='##targetNamespace' group='#group:1'"
	 * @generated
	 */
	EList<PDPDescriptorType> getPDPDescriptor();

	/**
	 * Returns the value of the '<em><b>Affiliation Descriptor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Affiliation Descriptor</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Affiliation Descriptor</em>' containment reference.
	 * @see #setAffiliationDescriptor(AffiliationDescriptorType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEntityDescriptorType_AffiliationDescriptor()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='AffiliationDescriptor' namespace='##targetNamespace'"
	 * @generated
	 */
	AffiliationDescriptorType getAffiliationDescriptor();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getAffiliationDescriptor <em>Affiliation Descriptor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Affiliation Descriptor</em>' containment reference.
	 * @see #getAffiliationDescriptor()
	 * @generated
	 */
	void setAffiliationDescriptor(AffiliationDescriptorType value);

	/**
	 * Returns the value of the '<em><b>Organization</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Organization</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Organization</em>' containment reference.
	 * @see #setOrganization(OrganizationType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEntityDescriptorType_Organization()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Organization' namespace='##targetNamespace'"
	 * @generated
	 */
	OrganizationType getOrganization();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getOrganization <em>Organization</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Organization</em>' containment reference.
	 * @see #getOrganization()
	 * @generated
	 */
	void setOrganization(OrganizationType value);

	/**
	 * Returns the value of the '<em><b>Contact Person</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contact Person</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contact Person</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEntityDescriptorType_ContactPerson()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='ContactPerson' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<ContactType> getContactPerson();

	/**
	 * Returns the value of the '<em><b>Additional Metadata Location</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.metadata.AdditionalMetadataLocationType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Additional Metadata Location</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Additional Metadata Location</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEntityDescriptorType_AdditionalMetadataLocation()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='AdditionalMetadataLocation' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<AdditionalMetadataLocationType> getAdditionalMetadataLocation();

	/**
	 * Returns the value of the '<em><b>Cache Duration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache Duration</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cache Duration</em>' attribute.
	 * @see #setCacheDuration(Duration)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEntityDescriptorType_CacheDuration()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Duration"
	 *        extendedMetaData="kind='attribute' name='cacheDuration'"
	 * @generated
	 */
	Duration getCacheDuration();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getCacheDuration <em>Cache Duration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache Duration</em>' attribute.
	 * @see #getCacheDuration()
	 * @generated
	 */
	void setCacheDuration(Duration value);

	/**
	 * Returns the value of the '<em><b>Entity ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity ID</em>' attribute.
	 * @see #setEntityID(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEntityDescriptorType_EntityID()
	 * @model dataType="com.tibco.be.baas.security.authn.saml.model.metadata.EntityIDType" required="true"
	 *        extendedMetaData="kind='attribute' name='entityID'"
	 * @generated
	 */
	String getEntityID();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getEntityID <em>Entity ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entity ID</em>' attribute.
	 * @see #getEntityID()
	 * @generated
	 */
	void setEntityID(String value);

	/**
	 * Returns the value of the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>ID</em>' attribute.
	 * @see #setID(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEntityDescriptorType_ID()
	 * @model id="true" dataType="org.eclipse.emf.ecore.xml.type.ID"
	 *        extendedMetaData="kind='attribute' name='ID'"
	 * @generated
	 */
	String getID();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getID <em>ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>ID</em>' attribute.
	 * @see #getID()
	 * @generated
	 */
	void setID(String value);

	/**
	 * Returns the value of the '<em><b>Valid Until</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Valid Until</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Valid Until</em>' attribute.
	 * @see #setValidUntil(XMLGregorianCalendar)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEntityDescriptorType_ValidUntil()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
	 *        extendedMetaData="kind='attribute' name='validUntil'"
	 * @generated
	 */
	XMLGregorianCalendar getValidUntil();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType#getValidUntil <em>Valid Until</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Valid Until</em>' attribute.
	 * @see #getValidUntil()
	 * @generated
	 */
	void setValidUntil(XMLGregorianCalendar value);

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
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEntityDescriptorType_AnyAttribute()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':16' processing='lax'"
	 * @generated
	 */
	FeatureMap getAnyAttribute();

} // EntityDescriptorType
