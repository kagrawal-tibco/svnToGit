/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.model.topology;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Deployment Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DeploymentMapping#getDeploymentUnitRef <em>Deployment Unit Ref</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DeploymentMapping#getHostRef <em>Host Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDeploymentMapping()
 * @model extendedMetaData="name='deployment-mapping_._type' kind='empty'"
 * @generated
 */
public interface DeploymentMapping extends EObject {
	/**
	 * Returns the value of the '<em><b>Deployment Unit Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deployment Unit Ref</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deployment Unit Ref</em>' attribute.
	 * @see #setDeploymentUnitRef(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDeploymentMapping_DeploymentUnitRef()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.IDREF"
	 *        extendedMetaData="kind='attribute' name='deployment-unit-ref'"
	 * @generated
	 */
	String getDeploymentUnitRef();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DeploymentMapping#getDeploymentUnitRef <em>Deployment Unit Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deployment Unit Ref</em>' attribute.
	 * @see #getDeploymentUnitRef()
	 * @generated
	 */
	void setDeploymentUnitRef(String value);

	/**
	 * Returns the value of the '<em><b>Host Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Host Ref</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Host Ref</em>' attribute.
	 * @see #setHostRef(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDeploymentMapping_HostRef()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.IDREF"
	 *        extendedMetaData="kind='attribute' name='host-ref'"
	 * @generated
	 */
	String getHostRef();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DeploymentMapping#getHostRef <em>Host Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Host Ref</em>' attribute.
	 * @see #getHostRef()
	 * @generated
	 */
	void setHostRef(String value);

} // DeploymentMapping
