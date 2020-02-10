/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.designtimelib;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.designtimelib.DesigntimelibPackage
 * @generated
 */
public interface DesigntimelibFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DesigntimelibFactory eINSTANCE = com.tibco.cep.designtime.core.model.designtimelib.impl.DesigntimelibFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Design Time Lib</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Design Time Lib</em>'.
	 * @generated
	 */
	DesignTimeLib createDesignTimeLib();

	/**
	 * Returns a new object of class '<em>Design Time Lib Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Design Time Lib Entry</em>'.
	 * @generated
	 */
	DesignTimeLibEntry createDesignTimeLibEntry();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	DesigntimelibPackage getDesigntimelibPackage();

} //DesigntimelibFactory
