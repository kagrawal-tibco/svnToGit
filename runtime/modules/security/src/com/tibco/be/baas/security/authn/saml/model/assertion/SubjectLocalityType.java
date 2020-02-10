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
 * A representation of the model object '<em><b>Subject Locality Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectLocalityType#getAddress <em>Address</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectLocalityType#getDNSName <em>DNS Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getSubjectLocalityType()
 * @model extendedMetaData="name='SubjectLocalityType' kind='empty'"
 * @generated
 */
public interface SubjectLocalityType extends EObject {
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
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getSubjectLocalityType_Address()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='Address'"
	 * @generated
	 */
	String getAddress();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectLocalityType#getAddress <em>Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Address</em>' attribute.
	 * @see #getAddress()
	 * @generated
	 */
	void setAddress(String value);

	/**
	 * Returns the value of the '<em><b>DNS Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>DNS Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>DNS Name</em>' attribute.
	 * @see #setDNSName(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getSubjectLocalityType_DNSName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='DNSName'"
	 * @generated
	 */
	String getDNSName();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectLocalityType#getDNSName <em>DNS Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>DNS Name</em>' attribute.
	 * @see #getDNSName()
	 * @generated
	 */
	void setDNSName(String value);

} // SubjectLocalityType
