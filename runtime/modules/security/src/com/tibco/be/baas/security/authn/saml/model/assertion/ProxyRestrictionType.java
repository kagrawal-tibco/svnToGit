/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion;

import java.math.BigInteger;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Proxy Restriction Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.ProxyRestrictionType#getAudience <em>Audience</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.ProxyRestrictionType#getCount <em>Count</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getProxyRestrictionType()
 * @model extendedMetaData="name='ProxyRestrictionType' kind='elementOnly'"
 * @generated
 */
public interface ProxyRestrictionType extends ConditionAbstractType {
	/**
	 * Returns the value of the '<em><b>Audience</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Audience</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Audience</em>' attribute list.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getProxyRestrictionType_Audience()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='element' name='Audience' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<String> getAudience();

	/**
	 * Returns the value of the '<em><b>Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Count</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Count</em>' attribute.
	 * @see #setCount(BigInteger)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getProxyRestrictionType_Count()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.NonNegativeInteger"
	 *        extendedMetaData="kind='attribute' name='Count'"
	 * @generated
	 */
	BigInteger getCount();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.ProxyRestrictionType#getCount <em>Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Count</em>' attribute.
	 * @see #getCount()
	 * @generated
	 */
	void setCount(BigInteger value);

} // ProxyRestrictionType
