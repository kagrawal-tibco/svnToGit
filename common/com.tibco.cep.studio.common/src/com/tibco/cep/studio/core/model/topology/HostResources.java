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
 * A representation of the model object '<em><b>Host Resources</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.HostResources#getHostResource <em>Host Resource</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getHostResources()
 * @model extendedMetaData="name='host-resources_._type' kind='elementOnly'"
 * @generated
 */
public interface HostResources extends EObject {
	/**
	 * Returns the value of the '<em><b>Host Resource</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.core.model.topology.HostResource}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Host Resource</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Host Resource</em>' containment reference list.
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getHostResources_HostResource()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='host-resource' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<HostResource> getHostResource();

} // HostResources
