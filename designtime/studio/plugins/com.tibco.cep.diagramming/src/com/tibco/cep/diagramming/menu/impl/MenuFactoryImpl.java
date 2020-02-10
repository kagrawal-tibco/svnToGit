/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.diagramming.menu.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.diagramming.menu.Menu;
import com.tibco.cep.diagramming.menu.MenuFactory;
import com.tibco.cep.diagramming.menu.MenuItem;
import com.tibco.cep.diagramming.menu.MenuPackage;
import com.tibco.cep.diagramming.menu.Pattern;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MenuFactoryImpl extends EFactoryImpl implements MenuFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static MenuFactory init() {
		try {
			MenuFactory theMenuFactory = (MenuFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.tibco.com/cep/diagramming/menu"); 
			if (theMenuFactory != null) {
				return theMenuFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new MenuFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MenuFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case MenuPackage.MENU: return createMenu();
			case MenuPackage.MENU_ITEM: return createMenuItem();
			case MenuPackage.PATTERN: return createPattern();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MenuItem createMenuItem() {
		MenuItemImpl menuItem = new MenuItemImpl();
		return menuItem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Menu createMenu() {
		MenuImpl menu = new MenuImpl();
		return menu;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Pattern createPattern() {
		PatternImpl pattern = new PatternImpl();
		return pattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MenuPackage getMenuPackage() {
		return (MenuPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static MenuPackage getPackage() {
		return MenuPackage.eINSTANCE;
	}

} //MenuFactoryImpl
