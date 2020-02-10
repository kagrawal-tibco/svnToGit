/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.diagramming.menu;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.diagramming.menu.Item#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.cep.diagramming.menu.Item#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.diagramming.menu.Item#getIcon <em>Icon</em>}</li>
 *   <li>{@link com.tibco.cep.diagramming.menu.Item#getCommand <em>Command</em>}</li>
 *   <li>{@link com.tibco.cep.diagramming.menu.Item#isVisible <em>Visible</em>}</li>
 *   <li>{@link com.tibco.cep.diagramming.menu.Item#getPattern <em>Pattern</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.diagramming.menu.MenuPackage#getItem()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Item extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see com.tibco.cep.diagramming.menu.MenuPackage#getItem_Id()
	 * @model
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link com.tibco.cep.diagramming.menu.Item#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.tibco.cep.diagramming.menu.MenuPackage#getItem_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.diagramming.menu.Item#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Icon</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Icon</em>' attribute.
	 * @see #setIcon(String)
	 * @see com.tibco.cep.diagramming.menu.MenuPackage#getItem_Icon()
	 * @model
	 * @generated
	 */
	String getIcon();

	/**
	 * Sets the value of the '{@link com.tibco.cep.diagramming.menu.Item#getIcon <em>Icon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Icon</em>' attribute.
	 * @see #getIcon()
	 * @generated
	 */
	void setIcon(String value);

	/**
	 * Returns the value of the '<em><b>Command</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Command</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Command</em>' attribute.
	 * @see #setCommand(String)
	 * @see com.tibco.cep.diagramming.menu.MenuPackage#getItem_Command()
	 * @model
	 * @generated
	 */
	String getCommand();

	/**
	 * Sets the value of the '{@link com.tibco.cep.diagramming.menu.Item#getCommand <em>Command</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Command</em>' attribute.
	 * @see #getCommand()
	 * @generated
	 */
	void setCommand(String value);

	/**
	 * Returns the value of the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Visible</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Visible</em>' attribute.
	 * @see #setVisible(boolean)
	 * @see com.tibco.cep.diagramming.menu.MenuPackage#getItem_Visible()
	 * @model
	 * @generated
	 */
	boolean isVisible();

	/**
	 * Sets the value of the '{@link com.tibco.cep.diagramming.menu.Item#isVisible <em>Visible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Visible</em>' attribute.
	 * @see #isVisible()
	 * @generated
	 */
	void setVisible(boolean value);

	/**
	 * Returns the value of the '<em><b>Pattern</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.diagramming.menu.Pattern}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pattern</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pattern</em>' containment reference list.
	 * @see com.tibco.cep.diagramming.menu.MenuPackage#getItem_Pattern()
	 * @model containment="true"
	 * @generated
	 */
	EList<Pattern> getPattern();

} // Item
