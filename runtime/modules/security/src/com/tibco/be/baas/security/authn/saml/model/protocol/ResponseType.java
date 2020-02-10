/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.FeatureMap;

import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Response Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.ResponseType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.ResponseType#getAssertion <em>Assertion</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getResponseType()
 * @model extendedMetaData="name='ResponseType' kind='elementOnly'"
 * @generated
 */
public interface ResponseType extends StatusResponseType {
	/**
	 * Returns the value of the '<em><b>Group</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Group</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group</em>' attribute list.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getResponseType_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:9'"
	 * @generated
	 */
	FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>Assertion</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assertion</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assertion</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getResponseType_Assertion()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Assertion' namespace='urn:oasis:names:tc:SAML:2.0:assertion' group='#group:9'"
	 * @generated
	 */
	EList<AssertionType> getAssertion();

} // ResponseType
