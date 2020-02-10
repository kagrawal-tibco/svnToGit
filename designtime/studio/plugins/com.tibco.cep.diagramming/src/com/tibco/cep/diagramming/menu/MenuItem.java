/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.diagramming.menu;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.diagramming.menu.MenuItem#getMnemonic <em>Mnemonic</em>}</li>
 *   <li>{@link com.tibco.cep.diagramming.menu.MenuItem#isChecked <em>Checked</em>}</li>
 *   <li>{@link com.tibco.cep.diagramming.menu.MenuItem#getKeycode <em>Keycode</em>}</li>
 *   <li>{@link com.tibco.cep.diagramming.menu.MenuItem#getModifiers <em>Modifiers</em>}</li>
 *   <li>{@link com.tibco.cep.diagramming.menu.MenuItem#isSeparator <em>Separator</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.diagramming.menu.MenuPackage#getMenuItem()
 * @model
 * @generated
 */
public interface MenuItem extends Item {
	/**
	 * Returns the value of the '<em><b>Mnemonic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mnemonic</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mnemonic</em>' attribute.
	 * @see #setMnemonic(String)
	 * @see com.tibco.cep.diagramming.menu.MenuPackage#getMenuItem_Mnemonic()
	 * @model
	 * @generated
	 */
	String getMnemonic();

	/**
	 * Sets the value of the '{@link com.tibco.cep.diagramming.menu.MenuItem#getMnemonic <em>Mnemonic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mnemonic</em>' attribute.
	 * @see #getMnemonic()
	 * @generated
	 */
	void setMnemonic(String value);

	/**
	 * Returns the value of the '<em><b>Checked</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Checked</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Checked</em>' attribute.
	 * @see #setChecked(boolean)
	 * @see com.tibco.cep.diagramming.menu.MenuPackage#getMenuItem_Checked()
	 * @model
	 * @generated
	 */
	boolean isChecked();

	/**
	 * Sets the value of the '{@link com.tibco.cep.diagramming.menu.MenuItem#isChecked <em>Checked</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Checked</em>' attribute.
	 * @see #isChecked()
	 * @generated
	 */
	void setChecked(boolean value);

	/**
	 * Returns the value of the '<em><b>Keycode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Keycode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Keycode</em>' attribute.
	 * @see #setKeycode(int)
	 * @see com.tibco.cep.diagramming.menu.MenuPackage#getMenuItem_Keycode()
	 * @model
	 * @generated
	 */
	int getKeycode();

	/**
	 * Sets the value of the '{@link com.tibco.cep.diagramming.menu.MenuItem#getKeycode <em>Keycode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Keycode</em>' attribute.
	 * @see #getKeycode()
	 * @generated
	 */
	void setKeycode(int value);

	/**
	 * Returns the value of the '<em><b>Modifiers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Modifiers</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Modifiers</em>' attribute.
	 * @see #setModifiers(int)
	 * @see com.tibco.cep.diagramming.menu.MenuPackage#getMenuItem_Modifiers()
	 * @model
	 * @generated
	 */
	int getModifiers();

	/**
	 * Sets the value of the '{@link com.tibco.cep.diagramming.menu.MenuItem#getModifiers <em>Modifiers</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Modifiers</em>' attribute.
	 * @see #getModifiers()
	 * @generated
	 */
	void setModifiers(int value);

	/**
	 * Returns the value of the '<em><b>Separator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Separator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Separator</em>' attribute.
	 * @see #setSeparator(boolean)
	 * @see com.tibco.cep.diagramming.menu.MenuPackage#getMenuItem_Separator()
	 * @model
	 * @generated
	 */
	boolean isSeparator();

	/**
	 * Sets the value of the '{@link com.tibco.cep.diagramming.menu.MenuItem#isSeparator <em>Separator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Separator</em>' attribute.
	 * @see #isSeparator()
	 * @generated
	 */
	void setSeparator(boolean value);

} // MenuItem
