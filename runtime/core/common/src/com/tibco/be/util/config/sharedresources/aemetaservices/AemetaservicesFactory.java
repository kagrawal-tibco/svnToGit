/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.aemetaservices;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.be.util.config.sharedresources.aemetaservices.AemetaservicesPackage
 * @generated
 */
public interface AemetaservicesFactory extends EFactory {
	/**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	AemetaservicesFactory eINSTANCE = com.tibco.be.util.config.sharedresources.aemetaservices.impl.AemetaservicesFactoryImpl.init();

	/**
     * Returns a new object of class '<em>Ae Meta Services Root</em>'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return a new object of class '<em>Ae Meta Services Root</em>'.
     * @generated
     */
	AeMetaServicesRoot createAeMetaServicesRoot();

	/**
     * Returns a new object of class '<em>Cert Type</em>'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return a new object of class '<em>Cert Type</em>'.
     * @generated
     */
	CertType createCertType();

	/**
     * Returns a new object of class '<em>Identity Type</em>'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return a new object of class '<em>Identity Type</em>'.
     * @generated
     */
	IdentityType createIdentityType();

	/**
     * Returns a new object of class '<em>Ssl</em>'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return a new object of class '<em>Ssl</em>'.
     * @generated
     */
	Ssl createSsl();

	/**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
	AemetaservicesPackage getAemetaservicesPackage();

} //AemetaservicesFactory
