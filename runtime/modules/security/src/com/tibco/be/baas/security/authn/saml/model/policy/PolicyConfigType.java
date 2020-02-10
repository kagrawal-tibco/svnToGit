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
 * A representation of the model object '<em><b>Config Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *     			Add all configuration for this policy in form of
 *     			attributes/properties to it.
 *     		
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.policy.PolicyConfigType#getConfigProperty <em>Config Property</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyPackage#getPolicyConfigType()
 * @model extendedMetaData="name='PolicyConfigType' kind='elementOnly'"
 *        annotation="http:///org/eclipse/emf/mapping/xsd2ecore/XSD2Ecore targetNamespace='http://www.tibco.com/be/baas/authn/PolicyTemplateSchema' name='policyConfig'"
 * @generated
 */
public interface PolicyConfigType extends EObject {
	/**
	 * Returns the value of the '<em><b>Config Property</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Config Property</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Config Property</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyPackage#getPolicyConfigType_ConfigProperty()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='configProperty' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<ConfigPropertyType> getConfigProperty();

} // PolicyConfigType
