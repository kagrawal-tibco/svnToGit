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
 * A representation of the model object '<em><b>Deployment Units</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DeploymentUnits#getDeploymentUnit <em>Deployment Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDeploymentUnits()
 * @model extendedMetaData="name='deployment-units_._type' kind='elementOnly'"
 * @generated
 */
public interface DeploymentUnits extends EObject {
	/**
	 * Returns the value of the '<em><b>Deployment Unit</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.core.model.topology.DeploymentUnit}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deployment Unit</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deployment Unit</em>' containment reference list.
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDeploymentUnits_DeploymentUnit()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='deployment-unit' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<DeploymentUnit> getDeploymentUnit();

} // DeploymentUnits
