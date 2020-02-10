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

import com.tibco.cep.diagramming.menu.MenuItem;
import com.tibco.cep.diagramming.menu.MenuPackage;
import com.tibco.cep.diagramming.menu.Pattern;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.diagramming.menu.impl.MenuItemImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.cep.diagramming.menu.impl.MenuItemImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.diagramming.menu.impl.MenuItemImpl#getIcon <em>Icon</em>}</li>
 *   <li>{@link com.tibco.cep.diagramming.menu.impl.MenuItemImpl#getCommand <em>Command</em>}</li>
 *   <li>{@link com.tibco.cep.diagramming.menu.impl.MenuItemImpl#isVisible <em>Visible</em>}</li>
 *   <li>{@link com.tibco.cep.diagramming.menu.impl.MenuItemImpl#getPattern <em>Pattern</em>}</li>
 *   <li>{@link com.tibco.cep.diagramming.menu.impl.MenuItemImpl#getMnemonic <em>Mnemonic</em>}</li>
 *   <li>{@link com.tibco.cep.diagramming.menu.impl.MenuItemImpl#isChecked <em>Checked</em>}</li>
 *   <li>{@link com.tibco.cep.diagramming.menu.impl.MenuItemImpl#getKeycode <em>Keycode</em>}</li>
 *   <li>{@link com.tibco.cep.diagramming.menu.impl.MenuItemImpl#getModifiers <em>Modifiers</em>}</li>
 *   <li>{@link com.tibco.cep.diagramming.menu.impl.MenuItemImpl#isSeparator <em>Separator</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MenuItemImpl extends EObjectImpl implements MenuItem {
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
	 * The default value of the '{@link #getMnemonic() <em>Mnemonic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMnemonic()
	 * @generated
	 * @ordered
	 */
	protected static final String MNEMONIC_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMnemonic() <em>Mnemonic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMnemonic()
	 * @generated
	 * @ordered
	 */
	protected String mnemonic = MNEMONIC_EDEFAULT;

	/**
	 * The default value of the '{@link #isChecked() <em>Checked</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isChecked()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CHECKED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isChecked() <em>Checked</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isChecked()
	 * @generated
	 * @ordered
	 */
	protected boolean checked = CHECKED_EDEFAULT;

	/**
	 * The default value of the '{@link #getKeycode() <em>Keycode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeycode()
	 * @generated
	 * @ordered
	 */
	protected static final int KEYCODE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getKeycode() <em>Keycode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeycode()
	 * @generated
	 * @ordered
	 */
	protected int keycode = KEYCODE_EDEFAULT;

	/**
	 * The default value of the '{@link #getModifiers() <em>Modifiers</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModifiers()
	 * @generated
	 * @ordered
	 */
	protected static final int MODIFIERS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getModifiers() <em>Modifiers</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModifiers()
	 * @generated
	 * @ordered
	 */
	protected int modifiers = MODIFIERS_EDEFAULT;

	/**
	 * The default value of the '{@link #isSeparator() <em>Separator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSeparator()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SEPARATOR_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSeparator() <em>Separator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSeparator()
	 * @generated
	 * @ordered
	 */
	protected boolean separator = SEPARATOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MenuItemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MenuPackage.Literals.MENU_ITEM;
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
			eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU_ITEM__ID, oldId, id));
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
			eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU_ITEM__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMnemonic() {
		return mnemonic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMnemonic(String newMnemonic) {
		String oldMnemonic = mnemonic;
		mnemonic = newMnemonic;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU_ITEM__MNEMONIC, oldMnemonic, mnemonic));
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
			eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU_ITEM__ICON, oldIcon, icon));
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
			eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU_ITEM__COMMAND, oldCommand, command));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChecked(boolean newChecked) {
		boolean oldChecked = checked;
		checked = newChecked;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU_ITEM__CHECKED, oldChecked, checked));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getKeycode() {
		return keycode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKeycode(int newKeycode) {
		int oldKeycode = keycode;
		keycode = newKeycode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU_ITEM__KEYCODE, oldKeycode, keycode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getModifiers() {
		return modifiers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModifiers(int newModifiers) {
		int oldModifiers = modifiers;
		modifiers = newModifiers;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU_ITEM__MODIFIERS, oldModifiers, modifiers));
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
			eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU_ITEM__VISIBLE, oldVisible, visible));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSeparator() {
		return separator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSeparator(boolean newSeparator) {
		boolean oldSeparator = separator;
		separator = newSeparator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MenuPackage.MENU_ITEM__SEPARATOR, oldSeparator, separator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Pattern> getPattern() {
		if (pattern == null) {
			pattern = new EObjectContainmentEList<Pattern>(Pattern.class, this, MenuPackage.MENU_ITEM__PATTERN);
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
			case MenuPackage.MENU_ITEM__PATTERN:
				return ((InternalEList<?>)getPattern()).basicRemove(otherEnd, msgs);
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
			case MenuPackage.MENU_ITEM__ID:
				return getId();
			case MenuPackage.MENU_ITEM__NAME:
				return getName();
			case MenuPackage.MENU_ITEM__ICON:
				return getIcon();
			case MenuPackage.MENU_ITEM__COMMAND:
				return getCommand();
			case MenuPackage.MENU_ITEM__VISIBLE:
				return isVisible();
			case MenuPackage.MENU_ITEM__PATTERN:
				return getPattern();
			case MenuPackage.MENU_ITEM__MNEMONIC:
				return getMnemonic();
			case MenuPackage.MENU_ITEM__CHECKED:
				return isChecked();
			case MenuPackage.MENU_ITEM__KEYCODE:
				return getKeycode();
			case MenuPackage.MENU_ITEM__MODIFIERS:
				return getModifiers();
			case MenuPackage.MENU_ITEM__SEPARATOR:
				return isSeparator();
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
			case MenuPackage.MENU_ITEM__ID:
				setId((String)newValue);
				return;
			case MenuPackage.MENU_ITEM__NAME:
				setName((String)newValue);
				return;
			case MenuPackage.MENU_ITEM__ICON:
				setIcon((String)newValue);
				return;
			case MenuPackage.MENU_ITEM__COMMAND:
				setCommand((String)newValue);
				return;
			case MenuPackage.MENU_ITEM__VISIBLE:
				setVisible((Boolean)newValue);
				return;
			case MenuPackage.MENU_ITEM__PATTERN:
				getPattern().clear();
				getPattern().addAll((Collection<? extends Pattern>)newValue);
				return;
			case MenuPackage.MENU_ITEM__MNEMONIC:
				setMnemonic((String)newValue);
				return;
			case MenuPackage.MENU_ITEM__CHECKED:
				setChecked((Boolean)newValue);
				return;
			case MenuPackage.MENU_ITEM__KEYCODE:
				setKeycode((Integer)newValue);
				return;
			case MenuPackage.MENU_ITEM__MODIFIERS:
				setModifiers((Integer)newValue);
				return;
			case MenuPackage.MENU_ITEM__SEPARATOR:
				setSeparator((Boolean)newValue);
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
			case MenuPackage.MENU_ITEM__ID:
				setId(ID_EDEFAULT);
				return;
			case MenuPackage.MENU_ITEM__NAME:
				setName(NAME_EDEFAULT);
				return;
			case MenuPackage.MENU_ITEM__ICON:
				setIcon(ICON_EDEFAULT);
				return;
			case MenuPackage.MENU_ITEM__COMMAND:
				setCommand(COMMAND_EDEFAULT);
				return;
			case MenuPackage.MENU_ITEM__VISIBLE:
				setVisible(VISIBLE_EDEFAULT);
				return;
			case MenuPackage.MENU_ITEM__PATTERN:
				getPattern().clear();
				return;
			case MenuPackage.MENU_ITEM__MNEMONIC:
				setMnemonic(MNEMONIC_EDEFAULT);
				return;
			case MenuPackage.MENU_ITEM__CHECKED:
				setChecked(CHECKED_EDEFAULT);
				return;
			case MenuPackage.MENU_ITEM__KEYCODE:
				setKeycode(KEYCODE_EDEFAULT);
				return;
			case MenuPackage.MENU_ITEM__MODIFIERS:
				setModifiers(MODIFIERS_EDEFAULT);
				return;
			case MenuPackage.MENU_ITEM__SEPARATOR:
				setSeparator(SEPARATOR_EDEFAULT);
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
			case MenuPackage.MENU_ITEM__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case MenuPackage.MENU_ITEM__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case MenuPackage.MENU_ITEM__ICON:
				return ICON_EDEFAULT == null ? icon != null : !ICON_EDEFAULT.equals(icon);
			case MenuPackage.MENU_ITEM__COMMAND:
				return COMMAND_EDEFAULT == null ? command != null : !COMMAND_EDEFAULT.equals(command);
			case MenuPackage.MENU_ITEM__VISIBLE:
				return visible != VISIBLE_EDEFAULT;
			case MenuPackage.MENU_ITEM__PATTERN:
				return pattern != null && !pattern.isEmpty();
			case MenuPackage.MENU_ITEM__MNEMONIC:
				return MNEMONIC_EDEFAULT == null ? mnemonic != null : !MNEMONIC_EDEFAULT.equals(mnemonic);
			case MenuPackage.MENU_ITEM__CHECKED:
				return checked != CHECKED_EDEFAULT;
			case MenuPackage.MENU_ITEM__KEYCODE:
				return keycode != KEYCODE_EDEFAULT;
			case MenuPackage.MENU_ITEM__MODIFIERS:
				return modifiers != MODIFIERS_EDEFAULT;
			case MenuPackage.MENU_ITEM__SEPARATOR:
				return separator != SEPARATOR_EDEFAULT;
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
		result.append(", mnemonic: ");
		result.append(mnemonic);
		result.append(", checked: ");
		result.append(checked);
		result.append(", keycode: ");
		result.append(keycode);
		result.append(", modifiers: ");
		result.append(modifiers);
		result.append(", separator: ");
		result.append(separator);
		result.append(')');
		return result.toString();
	}

} //MenuItemImpl
