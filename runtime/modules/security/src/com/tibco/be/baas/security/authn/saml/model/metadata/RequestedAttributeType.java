/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.metadata;

import com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Requested Attribute Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.RequestedAttributeType#isIsRequired <em>Is Required</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getRequestedAttributeType()
 * @model extendedMetaData="name='RequestedAttributeType' kind='elementOnly'"
 * @generated
 */
public interface RequestedAttributeType extends AttributeType {
	/**
	 * Returns the value of the '<em><b>Is Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Required</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Required</em>' attribute.
	 * @see #isSetIsRequired()
	 * @see #unsetIsRequired()
	 * @see #setIsRequired(boolean)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getRequestedAttributeType_IsRequired()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='isRequired'"
	 * @generated
	 */
	boolean isIsRequired();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.RequestedAttributeType#isIsRequired <em>Is Required</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Required</em>' attribute.
	 * @see #isSetIsRequired()
	 * @see #unsetIsRequired()
	 * @see #isIsRequired()
	 * @generated
	 */
	void setIsRequired(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.RequestedAttributeType#isIsRequired <em>Is Required</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetIsRequired()
	 * @see #isIsRequired()
	 * @see #setIsRequired(boolean)
	 * @generated
	 */
	void unsetIsRequired();

	/**
	 * Returns whether the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.RequestedAttributeType#isIsRequired <em>Is Required</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Is Required</em>' attribute is set.
	 * @see #unsetIsRequired()
	 * @see #isIsRequired()
	 * @see #setIsRequired(boolean)
	 * @generated
	 */
	boolean isSetIsRequired();

} // RequestedAttributeType
