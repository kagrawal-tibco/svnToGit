/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol;

import org.eclipse.emf.common.util.EList;

import com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute Query Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.AttributeQueryType#getAttribute <em>Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getAttributeQueryType()
 * @model extendedMetaData="name='AttributeQueryType' kind='elementOnly'"
 * @generated
 */
public interface AttributeQueryType extends SubjectQueryAbstractType {
	/**
	 * Returns the value of the '<em><b>Attribute</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getAttributeQueryType_Attribute()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Attribute' namespace='urn:oasis:names:tc:SAML:2.0:assertion'"
	 * @generated
	 */
	EList<AttributeType> getAttribute();

} // AttributeQueryType
