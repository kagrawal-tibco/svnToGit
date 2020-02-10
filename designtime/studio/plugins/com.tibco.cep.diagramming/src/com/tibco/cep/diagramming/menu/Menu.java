/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.diagramming.menu;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Menu</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.diagramming.menu.Menu#isIsSubMenu <em>Is Sub Menu</em>}</li>
 *   <li>{@link com.tibco.cep.diagramming.menu.Menu#getItems <em>Items</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.diagramming.menu.MenuPackage#getMenu()
 * @model
 * @generated
 */
public interface Menu extends Item {
	/**
	 * Returns the value of the '<em><b>Is Sub Menu</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Sub Menu</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Sub Menu</em>' attribute.
	 * @see #setIsSubMenu(boolean)
	 * @see com.tibco.cep.diagramming.menu.MenuPackage#getMenu_IsSubMenu()
	 * @model
	 * @generated
	 */
	boolean isIsSubMenu();

	/**
	 * Sets the value of the '{@link com.tibco.cep.diagramming.menu.Menu#isIsSubMenu <em>Is Sub Menu</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Sub Menu</em>' attribute.
	 * @see #isIsSubMenu()
	 * @generated
	 */
	void setIsSubMenu(boolean value);

	/**
	 * Returns the value of the '<em><b>Items</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.diagramming.menu.Item}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Items</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Items</em>' containment reference list.
	 * @see com.tibco.cep.diagramming.menu.MenuPackage#getMenu_Items()
	 * @model containment="true"
	 * @generated
	 */
	EList<Item> getItems();

} // Menu
