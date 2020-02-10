/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IDP Entry Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.IDPEntryType#getLoc <em>Loc</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.IDPEntryType#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.IDPEntryType#getProviderID <em>Provider ID</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getIDPEntryType()
 * @model extendedMetaData="name='IDPEntryType' kind='empty'"
 * @generated
 */
public interface IDPEntryType extends EObject {
	/**
	 * Returns the value of the '<em><b>Loc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Loc</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Loc</em>' attribute.
	 * @see #setLoc(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getIDPEntryType_Loc()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='attribute' name='Loc'"
	 * @generated
	 */
	String getLoc();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.IDPEntryType#getLoc <em>Loc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Loc</em>' attribute.
	 * @see #getLoc()
	 * @generated
	 */
	void setLoc(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getIDPEntryType_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='Name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.IDPEntryType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Provider ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provider ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provider ID</em>' attribute.
	 * @see #setProviderID(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getIDPEntryType_ProviderID()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI" required="true"
	 *        extendedMetaData="kind='attribute' name='ProviderID'"
	 * @generated
	 */
	String getProviderID();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.IDPEntryType#getProviderID <em>Provider ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provider ID</em>' attribute.
	 * @see #getProviderID()
	 * @generated
	 */
	void setProviderID(String value);

} // IDPEntryType
