/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.diagramming.menu.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.diagramming.menu.Item;
import com.tibco.cep.diagramming.menu.Menu;
import com.tibco.cep.diagramming.menu.MenuPackage;
import com.tibco.cep.diagramming.menu.Pattern;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Menu</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.diagramming.menu.impl.MenuImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.cep.diagramming.menu.impl.MenuImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.diagramming.menu.impl.MenuImpl#getIcon <em>Icon</em>}</li>
 *   <li>{@link com.tibco.cep.diagramming.menu.impl.MenuImpl#getCommand <em>Command</em>}</li>
 *   <li>{@link com.tibco.cep.diagramming.menu.impl.MenuImpl#isVisible <em>Visible</em>}</li>
 *   <li>{@link com.tibco.cep.diagramming.menu.impl.MenuImpl#getPattern <em>Pattern</em>}</li>
 *   <li>{@link com.tibco.cep.diagramming.menu.impl.MenuImpl#isIsSubMenu <em>Is Sub Menu</em>}</li>
 *   <li>{@link com.tibco.cep.diagramming.menu.impl.MenuImpl#getItems <em>Items</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MenuImpl extends EObjectImpl implements Menu {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getIcon() <em>Icon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIcon()
	 * @generated
	 * @ordered
	 */
	protected static final String ICON_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIcon() <em>Icon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIcon()
	 * @generated
	 * @ordered
	 */
	protected String icon = ICON_EDEFAULT;

	/**
	 * The default value of the '{@link #getCommand() <em>Command</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommand()
	 * @generated
	 * @ordered
	 */
	protected static final String COMMAND_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCommand() <em>Command</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommand()
	 * @generated
	 * @ordered
	 */
	protected String command = COMMAND_EDEFAULT;

	/**
	 * The default value of the '{@link #isVisible() <em>Visible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVisible()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VISIBLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isVisible() <em>Visible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVisible()
	 * @generated
	 * @ordered
	 */
	protected boolean visible = VISIBLE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPattern() <em>Pattern</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPattern()
	 * @generated
	 * @ordered
	 */
	protected EList<Pattern> pattern;

	/**
	 * The default value of the '{@link #isIsSubMenu() <em>Is Sub Menu</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsSubMenu()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_SUB_MENU_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsSubMenu() <em>Is Sub Menu</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsSubMenu()
	 * @generated
	 * @ordered
	 */
	protected boolean isSubMenu = IS_SUB_MENU_EDEFAULT;

	/**
	 * The cached value of the '{@link #getItems() <em>Items</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getItems()
	 * @generated
	 * @ordered
	 */
	protected EList<Item> items;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MenuImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MenuPackage.Literals.MENU;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIcon(String newIcon) {
		String oldIcon = icon;
		icon = newIcon;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU__ICON, oldIcon, icon));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCommand(String newCommand) {
		String oldCommand = command;
		command = newCommand;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU__COMMAND, oldCommand, command));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsSubMenu() {
		return isSubMenu;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsSubMenu(boolean newIsSubMenu) {
		boolean oldIsSubMenu = isSubMenu;
		isSubMenu = newIsSubMenu;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU__IS_SUB_MENU, oldIsSubMenu, isSubMenu));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Item> getItems() {
		if (items == null) {
			items = new EObjectContainmentEList<Item>(Item.class, this, MenuPackage.MENU__ITEMS);
		}
		return items;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVisible(boolean newVisible) {
		boolean oldVisible = visible;
		visible = newVisible;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU__VISIBLE, oldVisible, visible));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Pattern> getPattern() {
		if (pattern == null) {
			pattern = new EObjectContainmentEList<Pattern>(Pattern.class, this, MenuPackage.MENU__PATTERN);
		}
		return pattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MenuPackage.MENU__PATTERN:
				return ((InternalEList<?>)getPattern()).basicRemove(otherEnd, msgs);
			case MenuPackage.MENU__ITEMS:
				return ((InternalEList<?>)getItems()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MenuPackage.MENU__ID:
				return getId();
			case MenuPackage.MENU__NAME:
				return getName();
			case MenuPackage.MENU__ICON:
				return getIcon();
			case MenuPackage.MENU__COMMAND:
				return getCommand();
			case MenuPackage.MENU__VISIBLE:
				return isVisible();
			case MenuPackage.MENU__PATTERN:
				return getPattern();
			case MenuPackage.MENU__IS_SUB_MENU:
				return isIsSubMenu();
			case MenuPackage.MENU__ITEMS:
				return getItems();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MenuPackage.MENU__ID:
				setId((String)newValue);
				return;
			case MenuPackage.MENU__NAME:
				setName((String)newValue);
				return;
			case MenuPackage.MENU__ICON:
				setIcon((String)newValue);
				return;
			case MenuPackage.MENU__COMMAND:
				setCommand((String)newValue);
				return;
			case MenuPackage.MENU__VISIBLE:
				setVisible((Boolean)newValue);
				return;
			case MenuPackage.MENU__PATTERN:
				getPattern().clear();
				getPattern().addAll((Collection<? extends Pattern>)newValue);
				return;
			case MenuPackage.MENU__IS_SUB_MENU:
				setIsSubMenu((Boolean)newValue);
				return;
			case MenuPackage.MENU__ITEMS:
				getItems().clear();
				getItems().addAll((Collection<? extends Item>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case MenuPackage.MENU__ID:
				setId(ID_EDEFAULT);
				return;
			case MenuPackage.MENU__NAME:
				setName(NAME_EDEFAULT);
				return;
			case MenuPackage.MENU__ICON:
				setIcon(ICON_EDEFAULT);
				return;
			case MenuPackage.MENU__COMMAND:
				setCommand(COMMAND_EDEFAULT);
				return;
			case MenuPackage.MENU__VISIBLE:
				setVisible(VISIBLE_EDEFAULT);
				return;
			case MenuPackage.MENU__PATTERN:
				getPattern().clear();
				return;
			case MenuPackage.MENU__IS_SUB_MENU:
				setIsSubMenu(IS_SUB_MENU_EDEFAULT);
				return;
			case MenuPackage.MENU__ITEMS:
				getItems().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MenuPackage.MENU__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case MenuPackage.MENU__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case MenuPackage.MENU__ICON:
				return ICON_EDEFAULT == null ? icon != null : !ICON_EDEFAULT.equals(icon);
			case MenuPackage.MENU__COMMAND:
				return COMMAND_EDEFAULT == null ? command != null : !COMMAND_EDEFAULT.equals(command);
			case MenuPackage.MENU__VISIBLE:
				return visible != VISIBLE_EDEFAULT;
			case MenuPackage.MENU__PATTERN:
				return pattern != null && !pattern.isEmpty();
			case MenuPackage.MENU__IS_SUB_MENU:
				return isSubMenu != IS_SUB_MENU_EDEFAULT;
			case MenuPackage.MENU__ITEMS:
				return items != null && !items.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (id: ");
		result.append(id);
		result.append(", name: ");
		result.append(name);
		result.append(", icon: ");
		result.append(icon);
		result.append(", command: ");
		result.append(command);
		result.append(", visible: ");
		result.append(visible);
		result.append(", isSubMenu: ");
		result.append(isSubMenu);
		result.append(')');
		return result.toString();
	}

} //MenuImpl
