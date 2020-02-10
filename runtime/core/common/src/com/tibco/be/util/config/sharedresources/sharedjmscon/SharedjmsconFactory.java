/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedjmscon;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage
 * @generated
 */
public interface SharedjmsconFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SharedjmsconFactory eINSTANCE = com.tibco.be.util.config.sharedresources.sharedjmscon.impl.SharedjmsconFactoryImpl.init();

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
	 * Returns a new object of class '<em>Connection Attributes</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Connection Attributes</em>'.
	 * @generated
	 */
	ConnectionAttributes createConnectionAttributes();

	/**
	 * Returns a new object of class '<em>Jndi Properties</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Jndi Properties</em>'.
	 * @generated
	 */
	JndiProperties createJndiProperties();

	/**
	 * Returns a new object of class '<em>Naming Environment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Naming Environment</em>'.
	 * @generated
	 */
	NamingEnvironment createNamingEnvironment();

	/**
	 * Returns a new object of class '<em>Row</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Row</em>'.
	 * @generated
	 */
	Row createRow();

	/**
	 * Returns a new object of class '<em>Shared Jms Con Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Shared Jms Con Root</em>'.
	 * @generated
	 */
	SharedJmsConRoot createSharedJmsConRoot();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SharedjmsconPackage getSharedjmsconPackage();

} //SharedjmsconFactory
