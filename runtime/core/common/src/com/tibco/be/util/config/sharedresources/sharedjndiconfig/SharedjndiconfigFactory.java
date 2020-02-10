/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedjndiconfig;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigPackage
 * @generated
 */
public interface SharedjndiconfigFactory extends EFactory {
	/**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	SharedjndiconfigFactory eINSTANCE = com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.SharedjndiconfigFactoryImpl.init();

	/**
     * Returns a new object of class '<em>Bw Shared Resource</em>'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return a new object of class '<em>Bw Shared Resource</em>'.
     * @generated
     */
	BwSharedResource createBwSharedResource();

	/**
     * Returns a new object of class '<em>Config</em>'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return a new object of class '<em>Config</em>'.
     * @generated
     */
	Config createConfig();

	/**
     * Returns a new object of class '<em>Optional Jndi Properties</em>'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return a new object of class '<em>Optional Jndi Properties</em>'.
     * @generated
     */
	OptionalJndiProperties createOptionalJndiProperties();

	/**
     * Returns a new object of class '<em>Row</em>'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return a new object of class '<em>Row</em>'.
     * @generated
     */
	Row createRow();

	/**
     * Returns a new object of class '<em>Shared Jndi Config Root</em>'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return a new object of class '<em>Shared Jndi Config Root</em>'.
     * @generated
     */
	SharedJndiConfigRoot createSharedJndiConfigRoot();

	/**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
	SharedjndiconfigPackage getSharedjndiconfigPackage();

} //SharedjndiconfigFactory
