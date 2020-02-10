/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Base ID Abstract Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.BaseIDAbstractType#getNameQualifier <em>Name Qualifier</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.BaseIDAbstractType#getSPNameQualifier <em>SP Name Qualifier</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getBaseIDAbstractType()
 * @model abstract="true"
 *        extendedMetaData="name='BaseIDAbstractType' kind='empty'"
 * @generated
 */
public interface BaseIDAbstractType extends EObject {
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
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getBaseIDAbstractType_NameQualifier()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='NameQualifier'"
	 * @generated
	 */
	String getNameQualifier();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.BaseIDAbstractType#getNameQualifier <em>Name Qualifier</em>}' attribute.
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
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getBaseIDAbstractType_SPNameQualifier()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='SPNameQualifier'"
	 * @generated
	 */
	String getSPNameQualifier();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.BaseIDAbstractType#getSPNameQualifier <em>SP Name Qualifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>SP Name Qualifier</em>' attribute.
	 * @see #getSPNameQualifier()
	 * @generated
	 */
	void setSPNameQualifier(String value);

} // BaseIDAbstractType
