/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedhttp;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.be.util.config.sharedresources.sharedhttp.SharedhttpPackage
 * @generated
 */
public interface SharedhttpFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SharedhttpFactory eINSTANCE = com.tibco.be.util.config.sharedresources.sharedhttp.impl.SharedhttpFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Config</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Config</em>'.
	 * @generated
	 */
	Config createConfig();

	/**
	 * Returns a new object of class '<em>Http Shared Resource</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Http Shared Resource</em>'.
	 * @generated
	 */
	HttpSharedResource createHttpSharedResource();

	/**
	 * Returns a new object of class '<em>Shared Http Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Shared Http Root</em>'.
	 * @generated
	 */
	SharedHttpRoot createSharedHttpRoot();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SharedhttpPackage getSharedhttpPackage();

} //SharedhttpFactory
