/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.model.topology;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Software</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.Software#getBe <em>Be</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getSoftware()
 * @model extendedMetaData="name='software_._type' kind='elementOnly'"
 * @generated
 */
public interface Software extends EObject {
	/**
	 * Returns the value of the '<em><b>Be</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.core.model.topology.Be}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Be</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Be</em>' containment reference list.
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getSoftware_Be()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='be' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Be> getBe();

} // Software
