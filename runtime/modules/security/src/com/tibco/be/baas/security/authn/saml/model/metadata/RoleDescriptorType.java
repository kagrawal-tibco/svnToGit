/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.metadata;

import java.util.List;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Role Descriptor Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getOrganization <em>Organization</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getContactPerson <em>Contact Person</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getCacheDuration <em>Cache Duration</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getErrorURL <em>Error URL</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getID <em>ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getProtocolSupportEnumeration <em>Protocol Support Enumeration</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getValidUntil <em>Valid Until</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getRoleDescriptorType()
 * @model abstract="true"
 *        extendedMetaData="name='RoleDescriptorType' kind='elementOnly'"
 * @generated
 */
public interface RoleDescriptorType extends EObject {
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
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getRoleDescriptorType_Extensions()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Extensions' namespace='##targetNamespace'"
	 * @generated
	 */
	ExtensionsType getExtensions();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getExtensions <em>Extensions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extensions</em>' containment reference.
	 * @see #getExtensions()
	 * @generated
	 */
	void setExtensions(ExtensionsType value);

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
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getRoleDescriptorType_Organization()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Organization' namespace='##targetNamespace'"
	 * @generated
	 */
	OrganizationType getOrganization();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getOrganization <em>Organization</em>}' containment reference.
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
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getRoleDescriptorType_ContactPerson()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='ContactPerson' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<ContactType> getContactPerson();

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
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getRoleDescriptorType_CacheDuration()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Duration"
	 *        extendedMetaData="kind='attribute' name='cacheDuration'"
	 * @generated
	 */
	Duration getCacheDuration();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getCacheDuration <em>Cache Duration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache Duration</em>' attribute.
	 * @see #getCacheDuration()
	 * @generated
	 */
	void setCacheDuration(Duration value);

	/**
	 * Returns the value of the '<em><b>Error URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Error URL</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Error URL</em>' attribute.
	 * @see #setErrorURL(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getRoleDescriptorType_ErrorURL()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='attribute' name='errorURL'"
	 * @generated
	 */
	String getErrorURL();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getErrorURL <em>Error URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Error URL</em>' attribute.
	 * @see #getErrorURL()
	 * @generated
	 */
	void setErrorURL(String value);

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
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getRoleDescriptorType_ID()
	 * @model id="true" dataType="org.eclipse.emf.ecore.xml.type.ID"
	 *        extendedMetaData="kind='attribute' name='ID'"
	 * @generated
	 */
	String getID();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getID <em>ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>ID</em>' attribute.
	 * @see #getID()
	 * @generated
	 */
	void setID(String value);

	/**
	 * Returns the value of the '<em><b>Protocol Support Enumeration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Protocol Support Enumeration</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Protocol Support Enumeration</em>' attribute.
	 * @see #setProtocolSupportEnumeration(List)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getRoleDescriptorType_ProtocolSupportEnumeration()
	 * @model dataType="com.tibco.be.baas.security.authn.saml.model.metadata.AnyURIListType" required="true" many="false"
	 *        extendedMetaData="kind='attribute' name='protocolSupportEnumeration'"
	 * @generated
	 */
	List<String> getProtocolSupportEnumeration();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getProtocolSupportEnumeration <em>Protocol Support Enumeration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Protocol Support Enumeration</em>' attribute.
	 * @see #getProtocolSupportEnumeration()
	 * @generated
	 */
	void setProtocolSupportEnumeration(List<String> value);

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
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getRoleDescriptorType_ValidUntil()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
	 *        extendedMetaData="kind='attribute' name='validUntil'"
	 * @generated
	 */
	XMLGregorianCalendar getValidUntil();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType#getValidUntil <em>Valid Until</em>}' attribute.
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
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getRoleDescriptorType_AnyAttribute()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':8' processing='lax'"
	 * @generated
	 */
	FeatureMap getAnyAttribute();

} // RoleDescriptorType
