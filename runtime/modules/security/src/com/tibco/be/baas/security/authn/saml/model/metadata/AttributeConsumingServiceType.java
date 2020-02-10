/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.metadata;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute Consuming Service Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType#getServiceName <em>Service Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType#getServiceDescription <em>Service Description</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType#getRequestedAttribute <em>Requested Attribute</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType#getIndex <em>Index</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType#isIsDefault <em>Is Default</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getAttributeConsumingServiceType()
 * @model extendedMetaData="name='AttributeConsumingServiceType' kind='elementOnly'"
 * @generated
 */
public interface AttributeConsumingServiceType extends EObject {
	/**
	 * Returns the value of the '<em><b>Service Name</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedNameType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Service Name</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Service Name</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getAttributeConsumingServiceType_ServiceName()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='ServiceName' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<LocalizedNameType> getServiceName();

	/**
	 * Returns the value of the '<em><b>Service Description</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedNameType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Service Description</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Service Description</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getAttributeConsumingServiceType_ServiceDescription()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='ServiceDescription' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<LocalizedNameType> getServiceDescription();

	/**
	 * Returns the value of the '<em><b>Requested Attribute</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.metadata.RequestedAttributeType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Requested Attribute</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Requested Attribute</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getAttributeConsumingServiceType_RequestedAttribute()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='RequestedAttribute' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<RequestedAttributeType> getRequestedAttribute();

	/**
	 * Returns the value of the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Index</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Index</em>' attribute.
	 * @see #isSetIndex()
	 * @see #unsetIndex()
	 * @see #setIndex(int)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getAttributeConsumingServiceType_Index()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.UnsignedShort" required="true"
	 *        extendedMetaData="kind='attribute' name='index'"
	 * @generated
	 */
	int getIndex();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType#getIndex <em>Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Index</em>' attribute.
	 * @see #isSetIndex()
	 * @see #unsetIndex()
	 * @see #getIndex()
	 * @generated
	 */
	void setIndex(int value);

	/**
	 * Unsets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType#getIndex <em>Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetIndex()
	 * @see #getIndex()
	 * @see #setIndex(int)
	 * @generated
	 */
	void unsetIndex();

	/**
	 * Returns whether the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType#getIndex <em>Index</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Index</em>' attribute is set.
	 * @see #unsetIndex()
	 * @see #getIndex()
	 * @see #setIndex(int)
	 * @generated
	 */
	boolean isSetIndex();

	/**
	 * Returns the value of the '<em><b>Is Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Default</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Default</em>' attribute.
	 * @see #isSetIsDefault()
	 * @see #unsetIsDefault()
	 * @see #setIsDefault(boolean)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getAttributeConsumingServiceType_IsDefault()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='isDefault'"
	 * @generated
	 */
	boolean isIsDefault();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType#isIsDefault <em>Is Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Default</em>' attribute.
	 * @see #isSetIsDefault()
	 * @see #unsetIsDefault()
	 * @see #isIsDefault()
	 * @generated
	 */
	void setIsDefault(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType#isIsDefault <em>Is Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetIsDefault()
	 * @see #isIsDefault()
	 * @see #setIsDefault(boolean)
	 * @generated
	 */
	void unsetIsDefault();

	/**
	 * Returns whether the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType#isIsDefault <em>Is Default</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Is Default</em>' attribute is set.
	 * @see #unsetIsDefault()
	 * @see #isIsDefault()
	 * @see #setIsDefault(boolean)
	 * @generated
	 */
	boolean isSetIsDefault();

} // AttributeConsumingServiceType
