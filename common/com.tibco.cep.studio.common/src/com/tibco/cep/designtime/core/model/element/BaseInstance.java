/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.element;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Base Instance</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.BaseInstance#getResourcePath <em>Resource Path</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getBaseInstance()
 * @model
 * @generated
 */
public interface BaseInstance extends EObject {
	/**
	 * Returns the value of the '<em><b>Resource Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resource Path</em>' attribute.
	 * @see #setResourcePath(String)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getBaseInstance_ResourcePath()
	 * @model
	 * @generated
	 */
	String getResourcePath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.BaseInstance#getResourcePath <em>Resource Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resource Path</em>' attribute.
	 * @see #getResourcePath()
	 * @generated
	 */
	void setResourcePath(String value);

} // BaseInstance
