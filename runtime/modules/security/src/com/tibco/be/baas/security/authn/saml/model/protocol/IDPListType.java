/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IDP List Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.IDPListType#getIDPEntry <em>IDP Entry</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.IDPListType#getGetComplete <em>Get Complete</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getIDPListType()
 * @model extendedMetaData="name='IDPListType' kind='elementOnly'"
 * @generated
 */
public interface IDPListType extends EObject {
	/**
	 * Returns the value of the '<em><b>IDP Entry</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.protocol.IDPEntryType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>IDP Entry</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>IDP Entry</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getIDPListType_IDPEntry()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='IDPEntry' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<IDPEntryType> getIDPEntry();

	/**
	 * Returns the value of the '<em><b>Get Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Get Complete</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Get Complete</em>' attribute.
	 * @see #setGetComplete(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getIDPListType_GetComplete()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='element' name='GetComplete' namespace='##targetNamespace'"
	 * @generated
	 */
	String getGetComplete();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.IDPListType#getGetComplete <em>Get Complete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Get Complete</em>' attribute.
	 * @see #getGetComplete()
	 * @generated
	 */
	void setGetComplete(String value);

} // IDPListType
