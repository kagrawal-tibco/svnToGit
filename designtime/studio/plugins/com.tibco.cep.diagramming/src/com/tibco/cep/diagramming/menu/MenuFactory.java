/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.diagramming.menu;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.diagramming.menu.MenuPackage
 * @generated
 */
public interface MenuFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MenuFactory eINSTANCE = com.tibco.cep.diagramming.menu.impl.MenuFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Item</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Item</em>'.
	 * @generated
	 */
	MenuItem createMenuItem();

	/**
	 * Returns a new object of class '<em>Menu</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Menu</em>'.
	 * @generated
	 */
	Menu createMenu();

	/**
	 * Returns a new object of class '<em>Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Pattern</em>'.
	 * @generated
	 */
	Pattern createPattern();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	MenuPackage getMenuPackage();

} //MenuFactory
