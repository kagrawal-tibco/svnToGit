/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model;

import com.tibco.cep.designtime.core.model.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Shared Entity Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.SharedEntityElement#getSharedEntity <em>Shared Entity</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getSharedEntityElement()
 * @model
 * @generated
 */
public interface SharedEntityElement extends SharedElement, EntityElement {
	/**
	 * Returns the value of the '<em><b>Shared Entity</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shared Entity</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shared Entity</em>' containment reference.
	 * @see #setSharedEntity(Entity)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getSharedEntityElement_SharedEntity()
	 * @model containment="true" required="true" transient="true"
	 * @generated
	 */
	Entity getSharedEntity();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.SharedEntityElement#getSharedEntity <em>Shared Entity</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shared Entity</em>' containment reference.
	 * @see #getSharedEntity()
	 * @generated
	 */
	void setSharedEntity(Entity value);

} // SharedEntityElement
