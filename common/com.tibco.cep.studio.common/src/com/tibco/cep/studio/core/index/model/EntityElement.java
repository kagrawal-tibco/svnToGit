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
 * A representation of the model object '<em><b>Entity Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.EntityElement#getEntity <em>Entity</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getEntityElement()
 * @model
 * @generated
 */
public interface EntityElement extends TypeElement {
	/**
	 * Returns the value of the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity</em>' reference.
	 * @see #setEntity(Entity)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getEntityElement_Entity()
	 * @model required="true"
	 * @generated
	 */
	Entity getEntity();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.EntityElement#getEntity <em>Entity</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entity</em>' reference.
	 * @see #getEntity()
	 * @generated
	 */
	void setEntity(Entity value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorType="com.tibco.cep.studio.core.index.model.IStructuredElementVisitor"
	 * @generated
	 */
	void accept(IStructuredElementVisitor visitor);

} // EntityElement
