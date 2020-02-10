/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol;

import java.math.BigInteger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Scoping Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.ScopingType#getIDPList <em>IDP List</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.ScopingType#getRequesterID <em>Requester ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.ScopingType#getProxyCount <em>Proxy Count</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getScopingType()
 * @model extendedMetaData="name='ScopingType' kind='elementOnly'"
 * @generated
 */
public interface ScopingType extends EObject {
	/**
	 * Returns the value of the '<em><b>IDP List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>IDP List</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>IDP List</em>' containment reference.
	 * @see #setIDPList(IDPListType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getScopingType_IDPList()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='IDPList' namespace='##targetNamespace'"
	 * @generated
	 */
	IDPListType getIDPList();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ScopingType#getIDPList <em>IDP List</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>IDP List</em>' containment reference.
	 * @see #getIDPList()
	 * @generated
	 */
	void setIDPList(IDPListType value);

	/**
	 * Returns the value of the '<em><b>Requester ID</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Requester ID</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Requester ID</em>' attribute list.
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getScopingType_RequesterID()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='element' name='RequesterID' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<String> getRequesterID();

	/**
	 * Returns the value of the '<em><b>Proxy Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Proxy Count</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Proxy Count</em>' attribute.
	 * @see #setProxyCount(BigInteger)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getScopingType_ProxyCount()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.NonNegativeInteger"
	 *        extendedMetaData="kind='attribute' name='ProxyCount'"
	 * @generated
	 */
	BigInteger getProxyCount();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ScopingType#getProxyCount <em>Proxy Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Proxy Count</em>' attribute.
	 * @see #getProxyCount()
	 * @generated
	 */
	void setProxyCount(BigInteger value);

} // ScopingType
