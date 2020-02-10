/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.metadata;

import org.eclipse.emf.common.util.EList;

import com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>SPSSO Descriptor Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType#getAssertionConsumerService <em>Assertion Consumer Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType#getAttributeConsumingService <em>Attribute Consuming Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType#getAuthnPolicy <em>Authn Policy</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType#isAuthnRequestsSigned <em>Authn Requests Signed</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType#isWantAssertionsSigned <em>Want Assertions Signed</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getSPSSODescriptorType()
 * @model extendedMetaData="name='SPSSODescriptorType' kind='elementOnly'"
 * @generated
 */
public interface SPSSODescriptorType extends SSODescriptorType {
	/**
	 * Returns the value of the '<em><b>Assertion Consumer Service</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.metadata.IndexedEndpointType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assertion Consumer Service</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assertion Consumer Service</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getSPSSODescriptorType_AssertionConsumerService()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='AssertionConsumerService' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<IndexedEndpointType> getAssertionConsumerService();

	/**
	 * Returns the value of the '<em><b>Attribute Consuming Service</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Consuming Service</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Consuming Service</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getSPSSODescriptorType_AttributeConsumingService()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='AttributeConsumingService' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<AttributeConsumingServiceType> getAttributeConsumingService();

	/**
	 * Returns the value of the '<em><b>Authn Policy</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authn Policy</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authn Policy</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getSPSSODescriptorType_AuthnPolicy()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='AuthnPolicy' namespace='http://www.tibco.com/be/baas/authn/PolicyTemplateSchema'"
	 * @generated
	 */
	EList<PolicyTemplateType> getAuthnPolicy();

	/**
	 * Returns the value of the '<em><b>Authn Requests Signed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authn Requests Signed</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authn Requests Signed</em>' attribute.
	 * @see #isSetAuthnRequestsSigned()
	 * @see #unsetAuthnRequestsSigned()
	 * @see #setAuthnRequestsSigned(boolean)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getSPSSODescriptorType_AuthnRequestsSigned()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='AuthnRequestsSigned'"
	 * @generated
	 */
	boolean isAuthnRequestsSigned();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType#isAuthnRequestsSigned <em>Authn Requests Signed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Authn Requests Signed</em>' attribute.
	 * @see #isSetAuthnRequestsSigned()
	 * @see #unsetAuthnRequestsSigned()
	 * @see #isAuthnRequestsSigned()
	 * @generated
	 */
	void setAuthnRequestsSigned(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType#isAuthnRequestsSigned <em>Authn Requests Signed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAuthnRequestsSigned()
	 * @see #isAuthnRequestsSigned()
	 * @see #setAuthnRequestsSigned(boolean)
	 * @generated
	 */
	void unsetAuthnRequestsSigned();

	/**
	 * Returns whether the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType#isAuthnRequestsSigned <em>Authn Requests Signed</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Authn Requests Signed</em>' attribute is set.
	 * @see #unsetAuthnRequestsSigned()
	 * @see #isAuthnRequestsSigned()
	 * @see #setAuthnRequestsSigned(boolean)
	 * @generated
	 */
	boolean isSetAuthnRequestsSigned();

	/**
	 * Returns the value of the '<em><b>Want Assertions Signed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Want Assertions Signed</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Want Assertions Signed</em>' attribute.
	 * @see #isSetWantAssertionsSigned()
	 * @see #unsetWantAssertionsSigned()
	 * @see #setWantAssertionsSigned(boolean)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getSPSSODescriptorType_WantAssertionsSigned()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='WantAssertionsSigned'"
	 * @generated
	 */
	boolean isWantAssertionsSigned();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType#isWantAssertionsSigned <em>Want Assertions Signed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Want Assertions Signed</em>' attribute.
	 * @see #isSetWantAssertionsSigned()
	 * @see #unsetWantAssertionsSigned()
	 * @see #isWantAssertionsSigned()
	 * @generated
	 */
	void setWantAssertionsSigned(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType#isWantAssertionsSigned <em>Want Assertions Signed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetWantAssertionsSigned()
	 * @see #isWantAssertionsSigned()
	 * @see #setWantAssertionsSigned(boolean)
	 * @generated
	 */
	void unsetWantAssertionsSigned();

	/**
	 * Returns whether the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType#isWantAssertionsSigned <em>Want Assertions Signed</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Want Assertions Signed</em>' attribute is set.
	 * @see #unsetWantAssertionsSigned()
	 * @see #isWantAssertionsSigned()
	 * @see #setWantAssertionsSigned(boolean)
	 * @generated
	 */
	boolean isSetWantAssertionsSigned();

} // SPSSODescriptorType
