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
 * A representation of the model object '<em><b>Deployment Mappings</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DeploymentMappings#getDeploymentMapping <em>Deployment Mapping</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDeploymentMappings()
 * @model extendedMetaData="name='deployment-mappings_._type' kind='elementOnly'"
 * @generated
 */
public interface DeploymentMappings extends EObject {
	/**
	 * Returns the value of the '<em><b>Deployment Mapping</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.core.model.topology.DeploymentMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deployment Mapping</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deployment Mapping</em>' containment reference list.
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDeploymentMappings_DeploymentMapping()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='deployment-mapping' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<DeploymentMapping> getDeploymentMapping();

} // DeploymentMappings
