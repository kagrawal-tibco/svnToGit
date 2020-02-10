/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.element;

import com.tibco.cep.designtime.core.model.Entity;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Process Entity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.ProcessEntity#getProperties <em>Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getProcessEntity()
 * @model
 * @generated
 */
public interface ProcessEntity extends Entity {
	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.element.PropertyDefinition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Properties</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getProcessEntity_Properties()
	 * @model containment="true"
	 * @generated
	 */
	EList<PropertyDefinition> getProperties();

} // ProcessEntity
