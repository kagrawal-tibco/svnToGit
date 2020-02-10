/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.namespace;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.be.baas.security.authn.saml.model.namespace.NamespacePackage
 * @generated
 */
public interface NamespaceFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	NamespaceFactory eINSTANCE = com.tibco.be.baas.security.authn.saml.model.namespace.impl.NamespaceFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>XML Namespace Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>XML Namespace Document Root</em>'.
	 * @generated
	 */
	XMLNamespaceDocumentRoot createXMLNamespaceDocumentRoot();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	NamespacePackage getNamespacePackage();

} //NamespaceFactory
