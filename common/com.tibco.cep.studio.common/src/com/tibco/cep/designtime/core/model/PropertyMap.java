/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property Map</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.PropertyMap#getProperties <em>Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.ModelPackage#getPropertyMap()
 * @model
 * @generated
 */
public interface PropertyMap extends EObject {
	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.Entity}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Properties</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.ModelPackage#getPropertyMap_Properties()
	 * @model containment="true"
	 * @generated
	 */
	EList<Entity> getProperties();

} // PropertyMap
