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
 * A representation of the model object '<em><b>Contact Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getCompany <em>Company</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getGivenName <em>Given Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getSurName <em>Sur Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getEmailAddress <em>Email Address</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getTelephoneNumber <em>Telephone Number</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getContactType <em>Contact Type</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getContactType()
 * @model extendedMetaData="name='ContactType' kind='elementOnly'"
 * @generated
 */
public interface ContactType extends EObject {
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
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getContactType_Extensions()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Extensions' namespace='##targetNamespace'"
	 * @generated
	 */
	ExtensionsType getExtensions();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getExtensions <em>Extensions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extensions</em>' containment reference.
	 * @see #getExtensions()
	 * @generated
	 */
	void setExtensions(ExtensionsType value);

	/**
	 * Returns the value of the '<em><b>Company</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Company</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Company</em>' attribute.
	 * @see #setCompany(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getContactType_Company()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='Company' namespace='##targetNamespace'"
	 * @generated
	 */
	String getCompany();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getCompany <em>Company</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Company</em>' attribute.
	 * @see #getCompany()
	 * @generated
	 */
	void setCompany(String value);

	/**
	 * Returns the value of the '<em><b>Given Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Given Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Given Name</em>' attribute.
	 * @see #setGivenName(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getContactType_GivenName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='GivenName' namespace='##targetNamespace'"
	 * @generated
	 */
	String getGivenName();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getGivenName <em>Given Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Given Name</em>' attribute.
	 * @see #getGivenName()
	 * @generated
	 */
	void setGivenName(String value);

	/**
	 * Returns the value of the '<em><b>Sur Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sur Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sur Name</em>' attribute.
	 * @see #setSurName(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getContactType_SurName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='SurName' namespace='##targetNamespace'"
	 * @generated
	 */
	String getSurName();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getSurName <em>Sur Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sur Name</em>' attribute.
	 * @see #getSurName()
	 * @generated
	 */
	void setSurName(String value);

	/**
	 * Returns the value of the '<em><b>Email Address</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Email Address</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Email Address</em>' attribute list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getContactType_EmailAddress()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='element' name='EmailAddress' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<String> getEmailAddress();

	/**
	 * Returns the value of the '<em><b>Telephone Number</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Telephone Number</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Telephone Number</em>' attribute list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getContactType_TelephoneNumber()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='TelephoneNumber' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<String> getTelephoneNumber();

	/**
	 * Returns the value of the '<em><b>Contact Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactTypeType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contact Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contact Type</em>' attribute.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.ContactTypeType
	 * @see #isSetContactType()
	 * @see #unsetContactType()
	 * @see #setContactType(ContactTypeType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getContactType_ContactType()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='attribute' name='contactType'"
	 * @generated
	 */
	ContactTypeType getContactType();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getContactType <em>Contact Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contact Type</em>' attribute.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.ContactTypeType
	 * @see #isSetContactType()
	 * @see #unsetContactType()
	 * @see #getContactType()
	 * @generated
	 */
	void setContactType(ContactTypeType value);

	/**
	 * Unsets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getContactType <em>Contact Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetContactType()
	 * @see #getContactType()
	 * @see #setContactType(ContactTypeType)
	 * @generated
	 */
	void unsetContactType();

	/**
	 * Returns whether the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactType#getContactType <em>Contact Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Contact Type</em>' attribute is set.
	 * @see #unsetContactType()
	 * @see #getContactType()
	 * @see #setContactType(ContactTypeType)
	 * @generated
	 */
	boolean isSetContactType();

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
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getContactType_AnyAttribute()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':7' processing='lax'"
	 * @generated
	 */
	FeatureMap getAnyAttribute();

} // ContactType
