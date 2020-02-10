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
 * A representation of the model object '<em><b>Import Registry Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.ImportRegistryEntry#getLocation <em>Location</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.ImportRegistryEntry#getNamespace <em>Namespace</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getImportRegistryEntry()
 * @model
 * @generated
 */
public interface ImportRegistryEntry extends EObject {
	/**
	 * Returns the value of the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Location</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Location</em>' attribute.
	 * @see #setLocation(String)
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getImportRegistryEntry_Location()
	 * @model
	 * @generated
	 */
	String getLocation();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.event.ImportRegistryEntry#getLocation <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location</em>' attribute.
	 * @see #getLocation()
	 * @generated
	 */
	void setLocation(String value);

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
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getImportRegistryEntry_Namespace()
	 * @model
	 * @generated
	 */
	String getNamespace();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.event.ImportRegistryEntry#getNamespace <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Namespace</em>' attribute.
	 * @see #getNamespace()
	 * @generated
	 */
	void setNamespace(String value);

} // ImportRegistryEntry
