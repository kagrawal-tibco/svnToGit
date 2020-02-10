/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.archive.config;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entity Resources</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.config.EntityResources#getEntity <em>Entity</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.archive.config.ConfigPackage#getEntityResources()
 * @model
 * @generated
 */
public interface EntityResources extends EObject {
	/**
	 * Returns the value of the '<em><b>Entity</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.Entity}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.archive.config.ConfigPackage#getEntityResources_Entity()
	 * @model containment="true"
	 * @generated
	 */
	EList<Entity> getEntity();

} // EntityResources
