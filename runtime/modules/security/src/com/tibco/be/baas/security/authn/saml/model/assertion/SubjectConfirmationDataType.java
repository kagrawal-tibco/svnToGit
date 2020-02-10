/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Subject Confirmation Data Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getAny <em>Any</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getAddress <em>Address</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getInResponseTo <em>In Response To</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getNotBefore <em>Not Before</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getNotOnOrAfter <em>Not On Or After</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getRecipient <em>Recipient</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getSubjectConfirmationDataType()
 * @model extendedMetaData="name='SubjectConfirmationDataType' kind='mixed'"
 * @generated
 */
public interface SubjectConfirmationDataType extends EObject {
	/**
	 * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mixed</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mixed</em>' attribute list.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getSubjectConfirmationDataType_Mixed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' name=':mixed'"
	 * @generated
	 */
	FeatureMap getMixed();

	/**
	 * Returns the value of the '<em><b>Any</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Any</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Any</em>' attribute list.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getSubjectConfirmationDataType_Any()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='elementWildcard' wildcards='##any' name=':1' processing='lax'"
	 * @generated
	 */
	FeatureMap getAny();

	/**
	 * Returns the value of the '<em><b>Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Address</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Address</em>' attribute.
	 * @see #setAddress(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getSubjectConfirmationDataType_Address()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='Address'"
	 * @generated
	 */
	String getAddress();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getAddress <em>Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Address</em>' attribute.
	 * @see #getAddress()
	 * @generated
	 */
	void setAddress(String value);

	/**
	 * Returns the value of the '<em><b>In Response To</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>In Response To</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>In Response To</em>' attribute.
	 * @see #setInResponseTo(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getSubjectConfirmationDataType_InResponseTo()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.NCName"
	 *        extendedMetaData="kind='attribute' name='InResponseTo'"
	 * @generated
	 */
	String getInResponseTo();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getInResponseTo <em>In Response To</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>In Response To</em>' attribute.
	 * @see #getInResponseTo()
	 * @generated
	 */
	void setInResponseTo(String value);

	/**
	 * Returns the value of the '<em><b>Not Before</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Not Before</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Not Before</em>' attribute.
	 * @see #setNotBefore(XMLGregorianCalendar)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getSubjectConfirmationDataType_NotBefore()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
	 *        extendedMetaData="kind='attribute' name='NotBefore'"
	 * @generated
	 */
	XMLGregorianCalendar getNotBefore();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getNotBefore <em>Not Before</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Not Before</em>' attribute.
	 * @see #getNotBefore()
	 * @generated
	 */
	void setNotBefore(XMLGregorianCalendar value);

	/**
	 * Returns the value of the '<em><b>Not On Or After</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Not On Or After</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Not On Or After</em>' attribute.
	 * @see #setNotOnOrAfter(XMLGregorianCalendar)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getSubjectConfirmationDataType_NotOnOrAfter()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
	 *        extendedMetaData="kind='attribute' name='NotOnOrAfter'"
	 * @generated
	 */
	XMLGregorianCalendar getNotOnOrAfter();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getNotOnOrAfter <em>Not On Or After</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Not On Or After</em>' attribute.
	 * @see #getNotOnOrAfter()
	 * @generated
	 */
	void setNotOnOrAfter(XMLGregorianCalendar value);

	/**
	 * Returns the value of the '<em><b>Recipient</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Recipient</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Recipient</em>' attribute.
	 * @see #setRecipient(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getSubjectConfirmationDataType_Recipient()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='attribute' name='Recipient'"
	 * @generated
	 */
	String getRecipient();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType#getRecipient <em>Recipient</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Recipient</em>' attribute.
	 * @see #getRecipient()
	 * @generated
	 */
	void setRecipient(String value);

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
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getSubjectConfirmationDataType_AnyAttribute()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':7' processing='lax'"
	 * @generated
	 */
	FeatureMap getAnyAttribute();

} // SubjectConfirmationDataType
