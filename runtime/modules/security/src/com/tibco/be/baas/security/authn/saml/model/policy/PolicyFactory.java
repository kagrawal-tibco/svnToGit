/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.policy;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyPackage
 * @generated
 */
public interface PolicyFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PolicyFactory eINSTANCE = com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Config Property Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Config Property Type</em>'.
	 * @generated
	 */
	ConfigPropertyType createConfigPropertyType();

	/**
	 * Returns a new object of class '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Document Root</em>'.
	 * @generated
	 */
	DocumentRoot createDocumentRoot();

	/**
	 * Returns a new object of class '<em>Config Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Config Type</em>'.
	 * @generated
	 */
	PolicyConfigType createPolicyConfigType();

	/**
	 * Returns a new object of class '<em>Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Type</em>'.
	 * @generated
	 */
	PolicyType createPolicyType();

	/**
	 * Returns a new object of class '<em>Config Property Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Config Property Value</em>'.
	 * @generated
	 */
	ConfigPropertyValue createConfigPropertyValue();

	/**
	 * Returns a new object of class '<em>Authn Policy</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Authn Policy</em>'.
	 * @generated
	 */
	AuthnPolicy createAuthnPolicy();

	/**
	 * Returns a new object of class '<em>Authn Policy Template</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Authn Policy Template</em>'.
	 * @generated
	 */
	AuthnPolicyTemplate createAuthnPolicyTemplate();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	PolicyPackage getPolicyPackage();

} //PolicyFactory
