/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol;

import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Subject Query Abstract Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.SubjectQueryAbstractType#getSubject <em>Subject</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getSubjectQueryAbstractType()
 * @model abstract="true"
 *        extendedMetaData="name='SubjectQueryAbstractType' kind='elementOnly'"
 * @generated
 */
public interface SubjectQueryAbstractType extends RequestAbstractType {
	/**
	 * Returns the value of the '<em><b>Subject</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subject</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subject</em>' containment reference.
	 * @see #setSubject(SubjectType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getSubjectQueryAbstractType_Subject()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='Subject' namespace='urn:oasis:names:tc:SAML:2.0:assertion'"
	 * @generated
	 */
	SubjectType getSubject();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.SubjectQueryAbstractType#getSubject <em>Subject</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subject</em>' containment reference.
	 * @see #getSubject()
	 * @generated
	 */
	void setSubject(SubjectType value);

} // SubjectQueryAbstractType
