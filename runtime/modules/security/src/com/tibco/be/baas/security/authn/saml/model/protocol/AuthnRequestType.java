/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol;

import com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType;
import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Authn Request Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getSubject <em>Subject</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getNameIDPolicy <em>Name ID Policy</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getConditions <em>Conditions</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getRequestedAuthnContext <em>Requested Authn Context</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getScoping <em>Scoping</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getAssertionConsumerServiceIndex <em>Assertion Consumer Service Index</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getAssertionConsumerServiceURL <em>Assertion Consumer Service URL</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getAttributeConsumingServiceIndex <em>Attribute Consuming Service Index</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#isForceAuthn <em>Force Authn</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#isIsPassive <em>Is Passive</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getProtocolBinding <em>Protocol Binding</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getProviderName <em>Provider Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getAuthnRequestType()
 * @model extendedMetaData="name='AuthnRequestType' kind='elementOnly'"
 * @generated
 */
public interface AuthnRequestType extends RequestAbstractType {
	/**
	 * Returns the value of the '<em><b>Subject</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subject</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subject</em>' containment reference.
	 * @see #setSubject(SubjectType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getAuthnRequestType_Subject()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Subject' namespace='urn:oasis:names:tc:SAML:2.0:assertion'"
	 * @generated
	 */
	SubjectType getSubject();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getSubject <em>Subject</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subject</em>' containment reference.
	 * @see #getSubject()
	 * @generated
	 */
	void setSubject(SubjectType value);

	/**
	 * Returns the value of the '<em><b>Name ID Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name ID Policy</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name ID Policy</em>' containment reference.
	 * @see #setNameIDPolicy(NameIDPolicyType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getAuthnRequestType_NameIDPolicy()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='NameIDPolicy' namespace='##targetNamespace'"
	 * @generated
	 */
	NameIDPolicyType getNameIDPolicy();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getNameIDPolicy <em>Name ID Policy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name ID Policy</em>' containment reference.
	 * @see #getNameIDPolicy()
	 * @generated
	 */
	void setNameIDPolicy(NameIDPolicyType value);

	/**
	 * Returns the value of the '<em><b>Conditions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Conditions</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Conditions</em>' containment reference.
	 * @see #setConditions(ConditionsType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getAuthnRequestType_Conditions()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Conditions' namespace='urn:oasis:names:tc:SAML:2.0:assertion'"
	 * @generated
	 */
	ConditionsType getConditions();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getConditions <em>Conditions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Conditions</em>' containment reference.
	 * @see #getConditions()
	 * @generated
	 */
	void setConditions(ConditionsType value);

	/**
	 * Returns the value of the '<em><b>Requested Authn Context</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Requested Authn Context</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Requested Authn Context</em>' containment reference.
	 * @see #setRequestedAuthnContext(RequestedAuthnContextType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getAuthnRequestType_RequestedAuthnContext()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='RequestedAuthnContext' namespace='##targetNamespace'"
	 * @generated
	 */
	RequestedAuthnContextType getRequestedAuthnContext();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getRequestedAuthnContext <em>Requested Authn Context</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Requested Authn Context</em>' containment reference.
	 * @see #getRequestedAuthnContext()
	 * @generated
	 */
	void setRequestedAuthnContext(RequestedAuthnContextType value);

	/**
	 * Returns the value of the '<em><b>Scoping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Scoping</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scoping</em>' containment reference.
	 * @see #setScoping(ScopingType)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getAuthnRequestType_Scoping()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Scoping' namespace='##targetNamespace'"
	 * @generated
	 */
	ScopingType getScoping();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getScoping <em>Scoping</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scoping</em>' containment reference.
	 * @see #getScoping()
	 * @generated
	 */
	void setScoping(ScopingType value);

	/**
	 * Returns the value of the '<em><b>Assertion Consumer Service Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assertion Consumer Service Index</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assertion Consumer Service Index</em>' attribute.
	 * @see #isSetAssertionConsumerServiceIndex()
	 * @see #unsetAssertionConsumerServiceIndex()
	 * @see #setAssertionConsumerServiceIndex(int)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getAuthnRequestType_AssertionConsumerServiceIndex()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.UnsignedShort"
	 *        extendedMetaData="kind='attribute' name='AssertionConsumerServiceIndex'"
	 * @generated
	 */
	int getAssertionConsumerServiceIndex();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getAssertionConsumerServiceIndex <em>Assertion Consumer Service Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Assertion Consumer Service Index</em>' attribute.
	 * @see #isSetAssertionConsumerServiceIndex()
	 * @see #unsetAssertionConsumerServiceIndex()
	 * @see #getAssertionConsumerServiceIndex()
	 * @generated
	 */
	void setAssertionConsumerServiceIndex(int value);

	/**
	 * Unsets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getAssertionConsumerServiceIndex <em>Assertion Consumer Service Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAssertionConsumerServiceIndex()
	 * @see #getAssertionConsumerServiceIndex()
	 * @see #setAssertionConsumerServiceIndex(int)
	 * @generated
	 */
	void unsetAssertionConsumerServiceIndex();

	/**
	 * Returns whether the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getAssertionConsumerServiceIndex <em>Assertion Consumer Service Index</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Assertion Consumer Service Index</em>' attribute is set.
	 * @see #unsetAssertionConsumerServiceIndex()
	 * @see #getAssertionConsumerServiceIndex()
	 * @see #setAssertionConsumerServiceIndex(int)
	 * @generated
	 */
	boolean isSetAssertionConsumerServiceIndex();

	/**
	 * Returns the value of the '<em><b>Assertion Consumer Service URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assertion Consumer Service URL</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assertion Consumer Service URL</em>' attribute.
	 * @see #setAssertionConsumerServiceURL(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getAuthnRequestType_AssertionConsumerServiceURL()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='attribute' name='AssertionConsumerServiceURL'"
	 * @generated
	 */
	String getAssertionConsumerServiceURL();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getAssertionConsumerServiceURL <em>Assertion Consumer Service URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Assertion Consumer Service URL</em>' attribute.
	 * @see #getAssertionConsumerServiceURL()
	 * @generated
	 */
	void setAssertionConsumerServiceURL(String value);

	/**
	 * Returns the value of the '<em><b>Attribute Consuming Service Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Consuming Service Index</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Consuming Service Index</em>' attribute.
	 * @see #isSetAttributeConsumingServiceIndex()
	 * @see #unsetAttributeConsumingServiceIndex()
	 * @see #setAttributeConsumingServiceIndex(int)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getAuthnRequestType_AttributeConsumingServiceIndex()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.UnsignedShort"
	 *        extendedMetaData="kind='attribute' name='AttributeConsumingServiceIndex'"
	 * @generated
	 */
	int getAttributeConsumingServiceIndex();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getAttributeConsumingServiceIndex <em>Attribute Consuming Service Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Consuming Service Index</em>' attribute.
	 * @see #isSetAttributeConsumingServiceIndex()
	 * @see #unsetAttributeConsumingServiceIndex()
	 * @see #getAttributeConsumingServiceIndex()
	 * @generated
	 */
	void setAttributeConsumingServiceIndex(int value);

	/**
	 * Unsets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getAttributeConsumingServiceIndex <em>Attribute Consuming Service Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAttributeConsumingServiceIndex()
	 * @see #getAttributeConsumingServiceIndex()
	 * @see #setAttributeConsumingServiceIndex(int)
	 * @generated
	 */
	void unsetAttributeConsumingServiceIndex();

	/**
	 * Returns whether the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getAttributeConsumingServiceIndex <em>Attribute Consuming Service Index</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Attribute Consuming Service Index</em>' attribute is set.
	 * @see #unsetAttributeConsumingServiceIndex()
	 * @see #getAttributeConsumingServiceIndex()
	 * @see #setAttributeConsumingServiceIndex(int)
	 * @generated
	 */
	boolean isSetAttributeConsumingServiceIndex();

	/**
	 * Returns the value of the '<em><b>Force Authn</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Force Authn</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Force Authn</em>' attribute.
	 * @see #isSetForceAuthn()
	 * @see #unsetForceAuthn()
	 * @see #setForceAuthn(boolean)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getAuthnRequestType_ForceAuthn()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='ForceAuthn'"
	 * @generated
	 */
	boolean isForceAuthn();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#isForceAuthn <em>Force Authn</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Force Authn</em>' attribute.
	 * @see #isSetForceAuthn()
	 * @see #unsetForceAuthn()
	 * @see #isForceAuthn()
	 * @generated
	 */
	void setForceAuthn(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#isForceAuthn <em>Force Authn</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetForceAuthn()
	 * @see #isForceAuthn()
	 * @see #setForceAuthn(boolean)
	 * @generated
	 */
	void unsetForceAuthn();

	/**
	 * Returns whether the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#isForceAuthn <em>Force Authn</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Force Authn</em>' attribute is set.
	 * @see #unsetForceAuthn()
	 * @see #isForceAuthn()
	 * @see #setForceAuthn(boolean)
	 * @generated
	 */
	boolean isSetForceAuthn();

	/**
	 * Returns the value of the '<em><b>Is Passive</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Passive</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Passive</em>' attribute.
	 * @see #isSetIsPassive()
	 * @see #unsetIsPassive()
	 * @see #setIsPassive(boolean)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getAuthnRequestType_IsPassive()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='IsPassive'"
	 * @generated
	 */
	boolean isIsPassive();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#isIsPassive <em>Is Passive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Passive</em>' attribute.
	 * @see #isSetIsPassive()
	 * @see #unsetIsPassive()
	 * @see #isIsPassive()
	 * @generated
	 */
	void setIsPassive(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#isIsPassive <em>Is Passive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetIsPassive()
	 * @see #isIsPassive()
	 * @see #setIsPassive(boolean)
	 * @generated
	 */
	void unsetIsPassive();

	/**
	 * Returns whether the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#isIsPassive <em>Is Passive</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Is Passive</em>' attribute is set.
	 * @see #unsetIsPassive()
	 * @see #isIsPassive()
	 * @see #setIsPassive(boolean)
	 * @generated
	 */
	boolean isSetIsPassive();

	/**
	 * Returns the value of the '<em><b>Protocol Binding</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Protocol Binding</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Protocol Binding</em>' attribute.
	 * @see #setProtocolBinding(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getAuthnRequestType_ProtocolBinding()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='attribute' name='ProtocolBinding'"
	 * @generated
	 */
	String getProtocolBinding();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getProtocolBinding <em>Protocol Binding</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Protocol Binding</em>' attribute.
	 * @see #getProtocolBinding()
	 * @generated
	 */
	void setProtocolBinding(String value);

	/**
	 * Returns the value of the '<em><b>Provider Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provider Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provider Name</em>' attribute.
	 * @see #setProviderName(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getAuthnRequestType_ProviderName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='ProviderName'"
	 * @generated
	 */
	String getProviderName();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType#getProviderName <em>Provider Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provider Name</em>' attribute.
	 * @see #getProviderName()
	 * @generated
	 */
	void setProviderName(String value);

} // AuthnRequestType
