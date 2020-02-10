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
 * A representation of the model object '<em><b>Name ID Policy Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDPolicyType#isAllowCreate <em>Allow Create</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDPolicyType#getFormat <em>Format</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDPolicyType#getSPNameQualifier <em>SP Name Qualifier</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getNameIDPolicyType()
 * @model extendedMetaData="name='NameIDPolicyType' kind='empty'"
 * @generated
 */
public interface NameIDPolicyType extends EObject {
	/**
	 * Returns the value of the '<em><b>Allow Create</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Allow Create</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Allow Create</em>' attribute.
	 * @see #isSetAllowCreate()
	 * @see #unsetAllowCreate()
	 * @see #setAllowCreate(boolean)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getNameIDPolicyType_AllowCreate()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='AllowCreate'"
	 * @generated
	 */
	boolean isAllowCreate();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDPolicyType#isAllowCreate <em>Allow Create</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Allow Create</em>' attribute.
	 * @see #isSetAllowCreate()
	 * @see #unsetAllowCreate()
	 * @see #isAllowCreate()
	 * @generated
	 */
	void setAllowCreate(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDPolicyType#isAllowCreate <em>Allow Create</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAllowCreate()
	 * @see #isAllowCreate()
	 * @see #setAllowCreate(boolean)
	 * @generated
	 */
	void unsetAllowCreate();

	/**
	 * Returns whether the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDPolicyType#isAllowCreate <em>Allow Create</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Allow Create</em>' attribute is set.
	 * @see #unsetAllowCreate()
	 * @see #isAllowCreate()
	 * @see #setAllowCreate(boolean)
	 * @generated
	 */
	boolean isSetAllowCreate();

	/**
	 * Returns the value of the '<em><b>Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Format</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Format</em>' attribute.
	 * @see #setFormat(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getNameIDPolicyType_Format()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='attribute' name='Format'"
	 * @generated
	 */
	String getFormat();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDPolicyType#getFormat <em>Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Format</em>' attribute.
	 * @see #getFormat()
	 * @generated
	 */
	void setFormat(String value);

	/**
	 * Returns the value of the '<em><b>SP Name Qualifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>SP Name Qualifier</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>SP Name Qualifier</em>' attribute.
	 * @see #setSPNameQualifier(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getNameIDPolicyType_SPNameQualifier()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='SPNameQualifier'"
	 * @generated
	 */
	String getSPNameQualifier();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.NameIDPolicyType#getSPNameQualifier <em>SP Name Qualifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>SP Name Qualifier</em>' attribute.
	 * @see #getSPNameQualifier()
	 * @generated
	 */
	void setSPNameQualifier(String value);

} // NameIDPolicyType
