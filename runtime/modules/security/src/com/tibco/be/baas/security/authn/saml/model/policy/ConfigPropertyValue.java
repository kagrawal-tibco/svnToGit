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
 * A representation of the model object '<em><b>Config Property Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyValue#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyPackage#getConfigPropertyValue()
 * @model annotation="http:///org/eclipse/emf/mapping/xsd2ecore/XSD2Ecore targetNamespace='http://www.tibco.com/be/baas/authn/PolicyTemplateSchema' name='propertyValue'"
 * @generated
 */
public interface ConfigPropertyValue extends EObject {
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
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyPackage#getConfigPropertyValue_Value()
	 * @model
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyValue#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

} // ConfigPropertyValue
