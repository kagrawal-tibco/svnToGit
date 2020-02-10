/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.metadata;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Additional Metadata Location Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.AdditionalMetadataLocationType#getValue <em>Value</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.AdditionalMetadataLocationType#getNamespace <em>Namespace</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getAdditionalMetadataLocationType()
 * @model extendedMetaData="name='AdditionalMetadataLocationType' kind='simple'"
 * @generated
 */
public interface AdditionalMetadataLocationType extends EObject {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getAdditionalMetadataLocationType_Value()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="name=':0' kind='simple'"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AdditionalMetadataLocationType#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Namespace</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Namespace</em>' attribute.
	 * @see #setNamespace(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getAdditionalMetadataLocationType_Namespace()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI" required="true"
	 *        extendedMetaData="kind='attribute' name='namespace'"
	 * @generated
	 */
	String getNamespace();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AdditionalMetadataLocationType#getNamespace <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Namespace</em>' attribute.
	 * @see #getNamespace()
	 * @generated
	 */
	void setNamespace(String value);

} // AdditionalMetadataLocationType
