/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.diagramming.menu.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.cep.diagramming.menu.Item;
import com.tibco.cep.diagramming.menu.Menu;
import com.tibco.cep.diagramming.menu.MenuFactory;
import com.tibco.cep.diagramming.menu.MenuItem;
import com.tibco.cep.diagramming.menu.MenuPackage;
import com.tibco.cep.diagramming.menu.Pattern;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MenuPackageImpl extends EPackageImpl implements MenuPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass itemEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass menuItemEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass menuEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass patternEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.tibco.cep.diagramming.menu.MenuPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private MenuPackageImpl() {
		super(eNS_URI, MenuFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link MenuPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static MenuPackage init() {
		if (isInited) return (MenuPackage)EPackage.Registry.INSTANCE.getEPackage(MenuPackage.eNS_URI);

		// Obtain or create and register package
		MenuPackageImpl theMenuPackage = (MenuPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof MenuPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new MenuPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theMenuPackage.createPackageContents();

		// Initialize created meta-data
		theMenuPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theMenuPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(MenuPackage.eNS_URI, theMenuPackage);
		return theMenuPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getItem() {
		return itemEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getItem_Id() {
		return (EAttribute)itemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getItem_Name() {
		return (EAttribute)itemEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getItem_Icon() {
		return (EAttribute)itemEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getItem_Command() {
		return (EAttribute)itemEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getItem_Visible() {
		return (EAttribute)itemEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getItem_Pattern() {
		return (EReference)itemEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMenuItem() {
		return menuItemEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMenuItem_Mnemonic() {
		return (EAttribute)menuItemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMenuItem_Checked() {
		return (EAttribute)menuItemEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMenuItem_Keycode() {
		return (EAttribute)menuItemEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMenuItem_Modifiers() {
		return (EAttribute)menuItemEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMenuItem_Separator() {
		return (EAttribute)menuItemEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMenu() {
		return menuEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMenu_IsSubMenu() {
		return (EAttribute)menuEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMenu_Items() {
		return (EReference)menuEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPattern() {
		return patternEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPattern_Pattern() {
		return (EAttribute)patternEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MenuFactory getMenuFactory() {
		return (MenuFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		itemEClass = createEClass(ITEM);
		createEAttribute(itemEClass, ITEM__ID);
		createEAttribute(itemEClass, ITEM__NAME);
		createEAttribute(itemEClass, ITEM__ICON);
		createEAttribute(itemEClass, ITEM__COMMAND);
		createEAttribute(itemEClass, ITEM__VISIBLE);
		createEReference(itemEClass, ITEM__PATTERN);

		menuEClass = createEClass(MENU);
		createEAttribute(menuEClass, MENU__IS_SUB_MENU);
		createEReference(menuEClass, MENU__ITEMS);

		menuItemEClass = createEClass(MENU_ITEM);
		createEAttribute(menuItemEClass, MENU_ITEM__MNEMONIC);
		createEAttribute(menuItemEClass, MENU_ITEM__CHECKED);
		createEAttribute(menuItemEClass, MENU_ITEM__KEYCODE);
		createEAttribute(menuItemEClass, MENU_ITEM__MODIFIERS);
		createEAttribute(menuItemEClass, MENU_ITEM__SEPARATOR);

		patternEClass = createEClass(PATTERN);
		createEAttribute(patternEClass, PATTERN__PATTERN);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		menuEClass.getESuperTypes().add(this.getItem());
		menuItemEClass.getESuperTypes().add(this.getItem());

		// Initialize classes and features; add operations and parameters
		initEClass(itemEClass, Item.class, "Item", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getItem_Id(), ecorePackage.getEString(), "id", null, 0, 1, Item.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getItem_Name(), ecorePackage.getEString(), "name", null, 0, 1, Item.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getItem_Icon(), ecorePackage.getEString(), "icon", null, 0, 1, Item.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getItem_Command(), ecorePackage.getEString(), "command", null, 0, 1, Item.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getItem_Visible(), ecorePackage.getEBoolean(), "visible", null, 0, 1, Item.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getItem_Pattern(), this.getPattern(), null, "pattern", null, 0, -1, Item.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(menuEClass, Menu.class, "Menu", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMenu_IsSubMenu(), ecorePackage.getEBoolean(), "isSubMenu", null, 0, 1, Menu.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMenu_Items(), this.getItem(), null, "items", null, 0, -1, Menu.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(menuItemEClass, MenuItem.class, "MenuItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMenuItem_Mnemonic(), ecorePackage.getEString(), "mnemonic", null, 0, 1, MenuItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMenuItem_Checked(), ecorePackage.getEBoolean(), "checked", null, 0, 1, MenuItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMenuItem_Keycode(), ecorePackage.getEInt(), "keycode", null, 0, 1, MenuItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMenuItem_Modifiers(), ecorePackage.getEInt(), "modifiers", null, 0, 1, MenuItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMenuItem_Separator(), ecorePackage.getEBoolean(), "separator", null, 0, 1, MenuItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(patternEClass, Pattern.class, "Pattern", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPattern_Pattern(), ecorePackage.getEString(), "pattern", null, 0, 1, Pattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //MenuPackageImpl
