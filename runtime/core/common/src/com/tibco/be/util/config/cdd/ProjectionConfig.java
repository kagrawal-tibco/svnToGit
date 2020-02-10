/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Projection Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.ProjectionConfig#getSetProperty <em>Set Property</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getProjectionConfig()
 * @model extendedMetaData="name='projection-type' kind='elementOnly'"
 * @generated
 */
public interface ProjectionConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Set Property</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.SetPropertyConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Set Property</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Set Property</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProjectionConfig_SetProperty()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='set-property' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<SetPropertyConfig> getSetProperty();

} // ProjectionConfig
