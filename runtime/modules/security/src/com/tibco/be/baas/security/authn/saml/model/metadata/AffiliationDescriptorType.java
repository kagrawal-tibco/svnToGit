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
 * A representation of the model object '<em><b>Affiliation Descriptor Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType#getAffiliateMember <em>Affiliate Member</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType#getAffiliationOwnerID <em>Affiliation Owner ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType#getCacheDuration <em>Cache Duration</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType#getID <em>ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType#getValidUntil <em>Valid Until</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getAffiliationDescriptorType()
 * @model extendedMetaData="name='AffiliationDescriptorType' kind='elementOnly'"
 * @generated
 */
public interface AffiliationDescriptorType extends EObject {
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
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getAffiliationDescriptorType_Extensions()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Extensions' namespace='##targetNamespace'"
	 * @generated
	 */
	ExtensionsType getExtensions();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType#getExtensions <em>Extensions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extensions</em>' containment reference.
	 * @see #getExtensions()
	 * @generated
	 */
	void setExtensions(ExtensionsType value);

	/**
	 * Returns the value of the '<em><b>Affiliate Member</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Affiliate Member</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Affiliate Member</em>' attribute list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getAffiliationDescriptorType_AffiliateMember()
	 * @model unique="false" dataType="com.tibco.be.baas.security.authn.saml.model.metadata.EntityIDType" required="true"
	 *        extendedMetaData="kind='element' name='AffiliateMember' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<String> getAffiliateMember();

	/**
	 * Returns the value of the '<em><b>Affiliation Owner ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Affiliation Owner ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Affiliation Owner ID</em>' attribute.
	 * @see #setAffiliationOwnerID(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getAffiliationDescriptorType_AffiliationOwnerID()
	 * @model dataType="com.tibco.be.baas.security.authn.saml.model.metadata.EntityIDType" required="true"
	 *        extendedMetaData="kind='attribute' name='affiliationOwnerID'"
	 * @generated
	 */
	String getAffiliationOwnerID();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType#getAffiliationOwnerID <em>Affiliation Owner ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Affiliation Owner ID</em>' attribute.
	 * @see #getAffiliationOwnerID()
	 * @generated
	 */
	void setAffiliationOwnerID(String value);

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
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getAffiliationDescriptorType_CacheDuration()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Duration"
	 *        extendedMetaData="kind='attribute' name='cacheDuration'"
	 * @generated
	 */
	Duration getCacheDuration();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType#getCacheDuration <em>Cache Duration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache Duration</em>' attribute.
	 * @see #getCacheDuration()
	 * @generated
	 */
	void setCacheDuration(Duration value);

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
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getAffiliationDescriptorType_ID()
	 * @model id="true" dataType="org.eclipse.emf.ecore.xml.type.ID"
	 *        extendedMetaData="kind='attribute' name='ID'"
	 * @generated
	 */
	String getID();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType#getID <em>ID</em>}' attribute.
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
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getAffiliationDescriptorType_ValidUntil()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
	 *        extendedMetaData="kind='attribute' name='validUntil'"
	 * @generated
	 */
	XMLGregorianCalendar getValidUntil();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType#getValidUntil <em>Valid Until</em>}' attribute.
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
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getAffiliationDescriptorType_AnyAttribute()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':6' processing='lax'"
	 * @generated
	 */
	FeatureMap getAnyAttribute();

} // AffiliationDescriptorType
