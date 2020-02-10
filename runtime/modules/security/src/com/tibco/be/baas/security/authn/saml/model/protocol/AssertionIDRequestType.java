/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Assertion ID Request Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.AssertionIDRequestType#getAssertionIDRef <em>Assertion ID Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getAssertionIDRequestType()
 * @model extendedMetaData="name='AssertionIDRequestType' kind='elementOnly'"
 * @generated
 */
public interface AssertionIDRequestType extends RequestAbstractType {
	/**
	 * Returns the value of the '<em><b>Assertion ID Ref</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assertion ID Ref</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assertion ID Ref</em>' attribute list.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getAssertionIDRequestType_AssertionIDRef()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.NCName" required="true"
	 *        extendedMetaData="kind='element' name='AssertionIDRef' namespace='urn:oasis:names:tc:SAML:2.0:assertion'"
	 * @generated
	 */
	EList<String> getAssertionIDRef();

} // AssertionIDRequestType
