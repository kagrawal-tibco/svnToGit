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
 * A representation of the model object '<em><b>Clusters</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.Clusters#getCluster <em>Cluster</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getClusters()
 * @model extendedMetaData="name='clusters_._type' kind='elementOnly'"
 * @generated
 */
public interface Clusters extends EObject {
	/**
	 * Returns the value of the '<em><b>Cluster</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.core.model.topology.Cluster}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cluster</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cluster</em>' containment reference list.
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getClusters_Cluster()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='cluster' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Cluster> getCluster();

} // Clusters
