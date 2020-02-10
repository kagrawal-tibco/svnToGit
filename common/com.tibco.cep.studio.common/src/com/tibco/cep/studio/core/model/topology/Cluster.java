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
 * A representation of the model object '<em><b>Cluster</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.Cluster#getMasterFiles <em>Master Files</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.Cluster#getRunVersion <em>Run Version</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.Cluster#getDeploymentUnits <em>Deployment Units</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.Cluster#getDeploymentMappings <em>Deployment Mappings</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.Cluster#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.Cluster#getProjectCdd <em>Project Cdd</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getCluster()
 * @model extendedMetaData="name='cluster_._type' kind='elementOnly'"
 * @generated
 */
public interface Cluster extends EObject {
	/**
	 * Returns the value of the '<em><b>Master Files</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Master Files</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Master Files</em>' containment reference.
	 * @see #setMasterFiles(MasterFiles)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getCluster_MasterFiles()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='master-files' namespace='##targetNamespace'"
	 * @generated
	 */
	MasterFiles getMasterFiles();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.Cluster#getMasterFiles <em>Master Files</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Master Files</em>' containment reference.
	 * @see #getMasterFiles()
	 * @generated
	 */
	void setMasterFiles(MasterFiles value);

	/**
	 * Returns the value of the '<em><b>Run Version</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Run Version</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Run Version</em>' containment reference.
	 * @see #setRunVersion(RunVersion)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getCluster_RunVersion()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='run-version' namespace='##targetNamespace'"
	 * @generated
	 */
	RunVersion getRunVersion();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.Cluster#getRunVersion <em>Run Version</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Run Version</em>' containment reference.
	 * @see #getRunVersion()
	 * @generated
	 */
	void setRunVersion(RunVersion value);

	/**
	 * Returns the value of the '<em><b>Deployment Units</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deployment Units</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deployment Units</em>' containment reference.
	 * @see #setDeploymentUnits(DeploymentUnits)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getCluster_DeploymentUnits()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='deployment-units' namespace='##targetNamespace'"
	 * @generated
	 */
	DeploymentUnits getDeploymentUnits();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.Cluster#getDeploymentUnits <em>Deployment Units</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deployment Units</em>' containment reference.
	 * @see #getDeploymentUnits()
	 * @generated
	 */
	void setDeploymentUnits(DeploymentUnits value);

	/**
	 * Returns the value of the '<em><b>Deployment Mappings</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deployment Mappings</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deployment Mappings</em>' containment reference.
	 * @see #setDeploymentMappings(DeploymentMappings)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getCluster_DeploymentMappings()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='deployment-mappings' namespace='##targetNamespace'"
	 * @generated
	 */
	DeploymentMappings getDeploymentMappings();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.Cluster#getDeploymentMappings <em>Deployment Mappings</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deployment Mappings</em>' containment reference.
	 * @see #getDeploymentMappings()
	 * @generated
	 */
	void setDeploymentMappings(DeploymentMappings value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getCluster_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.Cluster#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Project Cdd</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project Cdd</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project Cdd</em>' attribute.
	 * @see #setProjectCdd(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getCluster_ProjectCdd()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='project-cdd'"
	 * @generated
	 */
	String getProjectCdd();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.Cluster#getProjectCdd <em>Project Cdd</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project Cdd</em>' attribute.
	 * @see #getProjectCdd()
	 * @generated
	 */
	void setProjectCdd(String value);

} // Cluster
