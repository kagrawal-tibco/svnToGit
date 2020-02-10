/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.policy;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Template Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * All authentication policy templates should adhere to this schema.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType#getPolicyConfig <em>Policy Config</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType#getID <em>ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyPackage#getPolicyTemplateType()
 * @model abstract="true"
 *        extendedMetaData="name='PolicyTemplateType' kind='elementOnly'"
 *        extendedMetaData="name='AuthnPolicy' kind='elementOnly'"
 *        extendedMetaData="name='AuthnPolicyTemplate' kind='elementOnly'"
 *        annotation="http:///org/eclipse/emf/mapping/xsd2ecore/XSD2Ecore targetNamespace='http://www.tibco.com/be/baas/authn/PolicyTemplateSchema' name='AuthnPolicy'"
 *        annotation="http:///org/eclipse/emf/mapping/xsd2ecore/XSD2Ecore targetNamespace='http://www.tibco.com/be/baas/authn/PolicyTemplateSchema' name='AuthnPolicyTemplate'"
 * @generated
 */
public interface PolicyTemplateType extends EObject {
	/**
	 * Returns the value of the '<em><b>Policy Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Policy Config</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Policy Config</em>' containment reference.
	 * @see #setPolicyConfig(PolicyConfigType)
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyPackage#getPolicyTemplateType_PolicyConfig()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='policyConfig' namespace='##targetNamespace'"
	 * @generated
	 */
	PolicyConfigType getPolicyConfig();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType#getPolicyConfig <em>Policy Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Policy Config</em>' containment reference.
	 * @see #getPolicyConfig()
	 * @generated
	 */
	void setPolicyConfig(PolicyConfigType value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyPackage#getPolicyTemplateType_Description()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='description'"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 *     				Unique ID to identify the Template.
	 *     			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>ID</em>' attribute.
	 * @see #setID(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyPackage#getPolicyTemplateType_ID()
	 * @model id="true" dataType="org.eclipse.emf.ecore.xml.type.ID" required="true"
	 *        extendedMetaData="kind='attribute' name='ID'"
	 * @generated
	 */
	String getID();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType#getID <em>ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>ID</em>' attribute.
	 * @see #getID()
	 * @generated
	 */
	void setID(String value);

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
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyPackage#getPolicyTemplateType_Name()
	 * @model dataType="com.tibco.be.baas.security.authn.saml.model.policy.NameType" required="true"
	 *        extendedMetaData="kind='attribute' name='name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // PolicyTemplateType
