/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.designtimelib;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Design Time Lib Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.designtimelib.DesignTimeLibEntry#getAlias <em>Alias</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.designtimelib.DesignTimeLibEntry#getLocation <em>Location</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.designtimelib.DesigntimelibPackage#getDesignTimeLibEntry()
 * @model
 * @generated
 */
public interface DesignTimeLibEntry extends EObject {
	/**
	 * Returns the value of the '<em><b>Alias</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Alias</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alias</em>' attribute.
	 * @see #setAlias(String)
	 * @see com.tibco.cep.designtime.core.model.designtimelib.DesigntimelibPackage#getDesignTimeLibEntry_Alias()
	 * @model
	 * @generated
	 */
	String getAlias();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.designtimelib.DesignTimeLibEntry#getAlias <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Alias</em>' attribute.
	 * @see #getAlias()
	 * @generated
	 */
	void setAlias(String value);

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
	 * @see com.tibco.cep.designtime.core.model.designtimelib.DesigntimelibPackage#getDesignTimeLibEntry_Location()
	 * @model
	 * @generated
	 */
	String getLocation();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.designtimelib.DesignTimeLibEntry#getLocation <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location</em>' attribute.
	 * @see #getLocation()
	 * @generated
	 */
	void setLocation(String value);

} // DesignTimeLibEntry
