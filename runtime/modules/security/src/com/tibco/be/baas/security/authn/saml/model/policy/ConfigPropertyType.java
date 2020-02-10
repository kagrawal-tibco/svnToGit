/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.policy;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Config Property Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyType#getDataType <em>Data Type</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyType#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyType#getPropertyValue <em>Property Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyPackage#getConfigPropertyType()
 * @model extendedMetaData="name='ConfigPropertyType' kind='elementOnly'"
 *        annotation="http:///org/eclipse/emf/mapping/xsd2ecore/XSD2Ecore targetNamespace='http://www.tibco.com/be/baas/authn/PolicyTemplateSchema' name='configProperty'"
 * @generated
 */
public interface ConfigPropertyType extends EObject {
	/**
	 * Returns the value of the '<em><b>Data Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.be.baas.security.authn.saml.model.policy.DataTypeType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Type</em>' attribute.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.DataTypeType
	 * @see #isSetDataType()
	 * @see #unsetDataType()
	 * @see #setDataType(DataTypeType)
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyPackage#getConfigPropertyType_DataType()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='attribute' name='dataType'"
	 * @generated
	 */
	DataTypeType getDataType();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyType#getDataType <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Type</em>' attribute.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.DataTypeType
	 * @see #isSetDataType()
	 * @see #unsetDataType()
	 * @see #getDataType()
	 * @generated
	 */
	void setDataType(DataTypeType value);

	/**
	 * Unsets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyType#getDataType <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDataType()
	 * @see #getDataType()
	 * @see #setDataType(DataTypeType)
	 * @generated
	 */
	void unsetDataType();

	/**
	 * Returns whether the value of the '{@link com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyType#getDataType <em>Data Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Data Type</em>' attribute is set.
	 * @see #unsetDataType()
	 * @see #getDataType()
	 * @see #setDataType(DataTypeType)
	 * @generated
	 */
	boolean isSetDataType();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 *     				Specify unique name inside a policyconfig
	 *     			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyPackage#getConfigPropertyType_Name()
	 * @model dataType="com.tibco.be.baas.security.authn.saml.model.policy.NameType1" required="true"
	 *        extendedMetaData="kind='attribute' name='name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Property Value</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyValue}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Value</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Value</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyPackage#getConfigPropertyType_PropertyValue()
	 * @model containment="true"
	 * @generated
	 */
	EList<ConfigPropertyValue> getPropertyValue();

} // ConfigPropertyType
