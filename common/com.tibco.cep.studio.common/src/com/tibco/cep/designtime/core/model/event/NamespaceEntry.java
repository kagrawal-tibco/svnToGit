/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.event;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Namespace Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.NamespaceEntry#getPrefix <em>Prefix</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.NamespaceEntry#getNamespace <em>Namespace</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getNamespaceEntry()
 * @model
 * @generated
 */
public interface NamespaceEntry extends EObject {
	/**
	 * Returns the value of the '<em><b>Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Prefix</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Prefix</em>' attribute.
	 * @see #setPrefix(String)
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getNamespaceEntry_Prefix()
	 * @model
	 * @generated
	 */
	String getPrefix();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.event.NamespaceEntry#getPrefix <em>Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Prefix</em>' attribute.
	 * @see #getPrefix()
	 * @generated
	 */
	void setPrefix(String value);

	/**
	 * Returns the value of the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Namespace</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Namespace</em>' attribute.
	 * @see #setNamespace(String)
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getNamespaceEntry_Namespace()
	 * @model
	 * @generated
	 */
	String getNamespace();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.event.NamespaceEntry#getNamespace <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Namespace</em>' attribute.
	 * @see #getNamespace()
	 * @generated
	 */
	void setNamespace(String value);

} // NamespaceEntry
