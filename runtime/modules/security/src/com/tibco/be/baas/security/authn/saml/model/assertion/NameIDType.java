/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion;

import org.eclipse.emf.ecore.EObject;

import com.tibco.be.baas.security.authn.saml.common.ISAMLObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Name ID Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType#getValue <em>Value</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType#getFormat <em>Format</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType#getNameQualifier <em>Name Qualifier</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType#getSPNameQualifier <em>SP Name Qualifier</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType#getSPProvidedID <em>SP Provided ID</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getNameIDType()
 * @model extendedMetaData="name='NameIDType' kind='simple'"
 * @generated NOT
 */
public interface NameIDType extends EObject, ISAMLObject {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getNameIDType_Value()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="name=':0' kind='simple'"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Format</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Format</em>' attribute.
	 * @see #setFormat(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getNameIDType_Format()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='attribute' name='Format'"
	 * @generated
	 */
	String getFormat();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType#getFormat <em>Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Format</em>' attribute.
	 * @see #getFormat()
	 * @generated
	 */
	void setFormat(String value);

	/**
	 * Returns the value of the '<em><b>Name Qualifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name Qualifier</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name Qualifier</em>' attribute.
	 * @see #setNameQualifier(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getNameIDType_NameQualifier()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='NameQualifier'"
	 * @generated
	 */
	String getNameQualifier();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType#getNameQualifier <em>Name Qualifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name Qualifier</em>' attribute.
	 * @see #getNameQualifier()
	 * @generated
	 */
	void setNameQualifier(String value);

	/**
	 * Returns the value of the '<em><b>SP Name Qualifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>SP Name Qualifier</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>SP Name Qualifier</em>' attribute.
	 * @see #setSPNameQualifier(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getNameIDType_SPNameQualifier()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='SPNameQualifier'"
	 * @generated
	 */
	String getSPNameQualifier();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType#getSPNameQualifier <em>SP Name Qualifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>SP Name Qualifier</em>' attribute.
	 * @see #getSPNameQualifier()
	 * @generated
	 */
	void setSPNameQualifier(String value);

	/**
	 * Returns the value of the '<em><b>SP Provided ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>SP Provided ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>SP Provided ID</em>' attribute.
	 * @see #setSPProvidedID(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getNameIDType_SPProvidedID()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='SPProvidedID'"
	 * @generated
	 */
	String getSPProvidedID();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType#getSPProvidedID <em>SP Provided ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>SP Provided ID</em>' attribute.
	 * @see #getSPProvidedID()
	 * @generated
	 */
	void setSPProvidedID(String value);

} // NameIDType
