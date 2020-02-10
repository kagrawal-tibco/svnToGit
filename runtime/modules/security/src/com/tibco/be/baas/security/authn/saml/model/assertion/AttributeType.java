/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

import com.tibco.be.baas.security.authn.saml.common.ISAMLObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType#getAttributeValue <em>Attribute Value</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType#getFriendlyName <em>Friendly Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType#getNameFormat <em>Name Format</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAttributeType()
 * @model extendedMetaData="name='AttributeType' kind='elementOnly'"
 * @generated NOT
 */
public interface AttributeType extends EObject, ISAMLObject {
	/**
	 * Returns the value of the '<em><b>Attribute Value</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Value</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Value</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAttributeType_AttributeValue()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='AttributeValue' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<EObject> getAttributeValue();

	/**
	 * Returns the value of the '<em><b>Friendly Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Friendly Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Friendly Name</em>' attribute.
	 * @see #setFriendlyName(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAttributeType_FriendlyName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='FriendlyName'"
	 * @generated
	 */
	String getFriendlyName();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType#getFriendlyName <em>Friendly Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Friendly Name</em>' attribute.
	 * @see #getFriendlyName()
	 * @generated
	 */
	void setFriendlyName(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAttributeType_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='Name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Name Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name Format</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name Format</em>' attribute.
	 * @see #setNameFormat(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAttributeType_NameFormat()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='attribute' name='NameFormat'"
	 * @generated
	 */
	String getNameFormat();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType#getNameFormat <em>Name Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name Format</em>' attribute.
	 * @see #getNameFormat()
	 * @generated
	 */
	void setNameFormat(String value);

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
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAttributeType_AnyAttribute()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':4' processing='lax'"
	 * @generated
	 */
	FeatureMap getAnyAttribute();

} // AttributeType
