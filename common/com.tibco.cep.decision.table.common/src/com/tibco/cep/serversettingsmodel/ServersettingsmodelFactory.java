/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.serversettingsmodel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.serversettingsmodel.ServersettingsmodelPackage
 * @generated
 */
public interface ServersettingsmodelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ServersettingsmodelFactory eINSTANCE = com.tibco.cep.serversettingsmodel.impl.ServersettingsmodelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Authentication URL</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Authentication URL</em>'.
	 * @generated
	 */
	AuthenticationURL createAuthenticationURL();

	/**
	 * Returns a new object of class '<em>Checkout URL</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Checkout URL</em>'.
	 * @generated
	 */
	CheckoutURL createCheckoutURL();

	/**
	 * Returns a new object of class '<em>Authentication</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Authentication</em>'.
	 * @generated
	 */
	Authentication createAuthentication();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ServersettingsmodelPackage getServersettingsmodelPackage();

} //ServersettingsmodelFactory
