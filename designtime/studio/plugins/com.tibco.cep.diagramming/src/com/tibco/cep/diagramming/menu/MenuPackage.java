/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.diagramming.menu;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.tibco.cep.diagramming.menu.MenuFactory
 * @model kind="package"
 * @generated
 */
public interface MenuPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "menu";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.tibco.com/cep/diagramming/menu";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "menu";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MenuPackage eINSTANCE = com.tibco.cep.diagramming.menu.impl.MenuPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.diagramming.menu.Item <em>Item</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.diagramming.menu.Item
	 * @see com.tibco.cep.diagramming.menu.impl.MenuPackageImpl#getItem()
	 * @generated
	 */
	int ITEM = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM__ID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM__NAME = 1;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM__ICON = 2;

	/**
	 * The feature id for the '<em><b>Command</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM__COMMAND = 3;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM__VISIBLE = 4;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM__PATTERN = 5;

	/**
	 * The number of structural features of the '<em>Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link com.tibco.cep.diagramming.menu.impl.MenuItemImpl <em>Item</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.diagramming.menu.impl.MenuItemImpl
	 * @see com.tibco.cep.diagramming.menu.impl.MenuPackageImpl#getMenuItem()
	 * @generated
	 */
	int MENU_ITEM = 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.diagramming.menu.impl.MenuImpl <em>Menu</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.diagramming.menu.impl.MenuImpl
	 * @see com.tibco.cep.diagramming.menu.impl.MenuPackageImpl#getMenu()
	 * @generated
	 */
	int MENU = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU__ID = ITEM__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU__NAME = ITEM__NAME;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU__ICON = ITEM__ICON;

	/**
	 * The feature id for the '<em><b>Command</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU__COMMAND = ITEM__COMMAND;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU__VISIBLE = ITEM__VISIBLE;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU__PATTERN = ITEM__PATTERN;

	/**
	 * The feature id for the '<em><b>Is Sub Menu</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU__IS_SUB_MENU = ITEM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Items</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU__ITEMS = ITEM_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Menu</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU_FEATURE_COUNT = ITEM_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU_ITEM__ID = ITEM__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU_ITEM__NAME = ITEM__NAME;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU_ITEM__ICON = ITEM__ICON;

	/**
	 * The feature id for the '<em><b>Command</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU_ITEM__COMMAND = ITEM__COMMAND;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU_ITEM__VISIBLE = ITEM__VISIBLE;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU_ITEM__PATTERN = ITEM__PATTERN;

	/**
	 * The feature id for the '<em><b>Mnemonic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU_ITEM__MNEMONIC = ITEM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Checked</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU_ITEM__CHECKED = ITEM_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Keycode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU_ITEM__KEYCODE = ITEM_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Modifiers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU_ITEM__MODIFIERS = ITEM_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Separator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU_ITEM__SEPARATOR = ITEM_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU_ITEM_FEATURE_COUNT = ITEM_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.tibco.cep.diagramming.menu.impl.PatternImpl <em>Pattern</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.diagramming.menu.impl.PatternImpl
	 * @see com.tibco.cep.diagramming.menu.impl.MenuPackageImpl#getPattern()
	 * @generated
	 */
	int PATTERN = 3;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN__PATTERN = 0;

	/**
	 * The number of structural features of the '<em>Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN_FEATURE_COUNT = 1;

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.diagramming.menu.Item <em>Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Item</em>'.
	 * @see com.tibco.cep.diagramming.menu.Item
	 * @generated
	 */
	EClass getItem();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.diagramming.menu.Item#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.tibco.cep.diagramming.menu.Item#getId()
	 * @see #getItem()
	 * @generated
	 */
	EAttribute getItem_Id();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.diagramming.menu.Item#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.cep.diagramming.menu.Item#getName()
	 * @see #getItem()
	 * @generated
	 */
	EAttribute getItem_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.diagramming.menu.Item#getIcon <em>Icon</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Icon</em>'.
	 * @see com.tibco.cep.diagramming.menu.Item#getIcon()
	 * @see #getItem()
	 * @generated
	 */
	EAttribute getItem_Icon();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.diagramming.menu.Item#getCommand <em>Command</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Command</em>'.
	 * @see com.tibco.cep.diagramming.menu.Item#getCommand()
	 * @see #getItem()
	 * @generated
	 */
	EAttribute getItem_Command();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.diagramming.menu.Item#isVisible <em>Visible</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Visible</em>'.
	 * @see com.tibco.cep.diagramming.menu.Item#isVisible()
	 * @see #getItem()
	 * @generated
	 */
	EAttribute getItem_Visible();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.diagramming.menu.Item#getPattern <em>Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Pattern</em>'.
	 * @see com.tibco.cep.diagramming.menu.Item#getPattern()
	 * @see #getItem()
	 * @generated
	 */
	EReference getItem_Pattern();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.diagramming.menu.MenuItem <em>Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Item</em>'.
	 * @see com.tibco.cep.diagramming.menu.MenuItem
	 * @generated
	 */
	EClass getMenuItem();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.diagramming.menu.MenuItem#getMnemonic <em>Mnemonic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mnemonic</em>'.
	 * @see com.tibco.cep.diagramming.menu.MenuItem#getMnemonic()
	 * @see #getMenuItem()
	 * @generated
	 */
	EAttribute getMenuItem_Mnemonic();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.diagramming.menu.MenuItem#isChecked <em>Checked</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Checked</em>'.
	 * @see com.tibco.cep.diagramming.menu.MenuItem#isChecked()
	 * @see #getMenuItem()
	 * @generated
	 */
	EAttribute getMenuItem_Checked();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.diagramming.menu.MenuItem#getKeycode <em>Keycode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Keycode</em>'.
	 * @see com.tibco.cep.diagramming.menu.MenuItem#getKeycode()
	 * @see #getMenuItem()
	 * @generated
	 */
	EAttribute getMenuItem_Keycode();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.diagramming.menu.MenuItem#getModifiers <em>Modifiers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Modifiers</em>'.
	 * @see com.tibco.cep.diagramming.menu.MenuItem#getModifiers()
	 * @see #getMenuItem()
	 * @generated
	 */
	EAttribute getMenuItem_Modifiers();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.diagramming.menu.MenuItem#isSeparator <em>Separator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Separator</em>'.
	 * @see com.tibco.cep.diagramming.menu.MenuItem#isSeparator()
	 * @see #getMenuItem()
	 * @generated
	 */
	EAttribute getMenuItem_Separator();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.diagramming.menu.Menu <em>Menu</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Menu</em>'.
	 * @see com.tibco.cep.diagramming.menu.Menu
	 * @generated
	 */
	EClass getMenu();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.diagramming.menu.Menu#isIsSubMenu <em>Is Sub Menu</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Sub Menu</em>'.
	 * @see com.tibco.cep.diagramming.menu.Menu#isIsSubMenu()
	 * @see #getMenu()
	 * @generated
	 */
	EAttribute getMenu_IsSubMenu();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.diagramming.menu.Menu#getItems <em>Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Items</em>'.
	 * @see com.tibco.cep.diagramming.menu.Menu#getItems()
	 * @see #getMenu()
	 * @generated
	 */
	EReference getMenu_Items();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.diagramming.menu.Pattern <em>Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pattern</em>'.
	 * @see com.tibco.cep.diagramming.menu.Pattern
	 * @generated
	 */
	EClass getPattern();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.diagramming.menu.Pattern#getPattern <em>Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pattern</em>'.
	 * @see com.tibco.cep.diagramming.menu.Pattern#getPattern()
	 * @see #getPattern()
	 * @generated
	 */
	EAttribute getPattern_Pattern();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MenuFactory getMenuFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.tibco.cep.diagramming.menu.Item <em>Item</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.diagramming.menu.Item
		 * @see com.tibco.cep.diagramming.menu.impl.MenuPackageImpl#getItem()
		 * @generated
		 */
		EClass ITEM = eINSTANCE.getItem();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ITEM__ID = eINSTANCE.getItem_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ITEM__NAME = eINSTANCE.getItem_Name();

		/**
		 * The meta object literal for the '<em><b>Icon</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ITEM__ICON = eINSTANCE.getItem_Icon();

		/**
		 * The meta object literal for the '<em><b>Command</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ITEM__COMMAND = eINSTANCE.getItem_Command();

		/**
		 * The meta object literal for the '<em><b>Visible</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ITEM__VISIBLE = eINSTANCE.getItem_Visible();

		/**
		 * The meta object literal for the '<em><b>Pattern</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITEM__PATTERN = eINSTANCE.getItem_Pattern();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.diagramming.menu.impl.MenuItemImpl <em>Item</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.diagramming.menu.impl.MenuItemImpl
		 * @see com.tibco.cep.diagramming.menu.impl.MenuPackageImpl#getMenuItem()
		 * @generated
		 */
		EClass MENU_ITEM = eINSTANCE.getMenuItem();

		/**
		 * The meta object literal for the '<em><b>Mnemonic</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MENU_ITEM__MNEMONIC = eINSTANCE.getMenuItem_Mnemonic();

		/**
		 * The meta object literal for the '<em><b>Checked</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MENU_ITEM__CHECKED = eINSTANCE.getMenuItem_Checked();

		/**
		 * The meta object literal for the '<em><b>Keycode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MENU_ITEM__KEYCODE = eINSTANCE.getMenuItem_Keycode();

		/**
		 * The meta object literal for the '<em><b>Modifiers</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MENU_ITEM__MODIFIERS = eINSTANCE.getMenuItem_Modifiers();

		/**
		 * The meta object literal for the '<em><b>Separator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MENU_ITEM__SEPARATOR = eINSTANCE.getMenuItem_Separator();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.diagramming.menu.impl.MenuImpl <em>Menu</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.diagramming.menu.impl.MenuImpl
		 * @see com.tibco.cep.diagramming.menu.impl.MenuPackageImpl#getMenu()
		 * @generated
		 */
		EClass MENU = eINSTANCE.getMenu();

		/**
		 * The meta object literal for the '<em><b>Is Sub Menu</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MENU__IS_SUB_MENU = eINSTANCE.getMenu_IsSubMenu();

		/**
		 * The meta object literal for the '<em><b>Items</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MENU__ITEMS = eINSTANCE.getMenu_Items();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.diagramming.menu.impl.PatternImpl <em>Pattern</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.diagramming.menu.impl.PatternImpl
		 * @see com.tibco.cep.diagramming.menu.impl.MenuPackageImpl#getPattern()
		 * @generated
		 */
		EClass PATTERN = eINSTANCE.getPattern();

		/**
		 * The meta object literal for the '<em><b>Pattern</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PATTERN__PATTERN = eINSTANCE.getPattern_Pattern();

	}

} //MenuPackage
