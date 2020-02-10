/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.archive.config;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.archive.config.ConfigPackage
 * @generated
 */
public interface ConfigFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ConfigFactory eINSTANCE = com.tibco.cep.designtime.core.model.archive.config.impl.ConfigFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Engine Config</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Engine Config</em>'.
	 * @generated
	 */
	EngineConfig createEngineConfig();

	/**
	 * Returns a new object of class '<em>Archives</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Archives</em>'.
	 * @generated
	 */
	Archives createArchives();

	/**
	 * Returns a new object of class '<em>Entity Resources</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Entity Resources</em>'.
	 * @generated
	 */
	EntityResources createEntityResources();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ConfigPackage getConfigPackage();

} //ConfigFactory
