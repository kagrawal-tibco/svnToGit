/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.model.topology;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage
 * @generated
 */
public interface TopologyFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TopologyFactory eINSTANCE = com.tibco.cep.studio.core.model.topology.impl.TopologyFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Be Runtime</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Be Runtime</em>'.
	 * @generated
	 */
	BeRuntime createBeRuntime();

	/**
	 * Returns a new object of class '<em>Be</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Be</em>'.
	 * @generated
	 */
	Be createBe();

	/**
	 * Returns a new object of class '<em>Clusters</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Clusters</em>'.
	 * @generated
	 */
	Clusters createClusters();

	/**
	 * Returns a new object of class '<em>Cluster</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Cluster</em>'.
	 * @generated
	 */
	Cluster createCluster();

	/**
	 * Returns a new object of class '<em>Deployed Files</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Deployed Files</em>'.
	 * @generated
	 */
	DeployedFiles createDeployedFiles();

	/**
	 * Returns a new object of class '<em>Deployment Mappings</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Deployment Mappings</em>'.
	 * @generated
	 */
	DeploymentMappings createDeploymentMappings();

	/**
	 * Returns a new object of class '<em>Deployment Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Deployment Mapping</em>'.
	 * @generated
	 */
	DeploymentMapping createDeploymentMapping();

	/**
	 * Returns a new object of class '<em>Deployment Units</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Deployment Units</em>'.
	 * @generated
	 */
	DeploymentUnits createDeploymentUnits();

	/**
	 * Returns a new object of class '<em>Deployment Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Deployment Unit</em>'.
	 * @generated
	 */
	DeploymentUnit createDeploymentUnit();

	/**
	 * Returns a new object of class '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Document Root</em>'.
	 * @generated
	 */
	DocumentRoot createDocumentRoot();

	/**
	 * Returns a new object of class '<em>Hawk</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Hawk</em>'.
	 * @generated
	 */
	Hawk createHawk();

	/**
	 * Returns a new object of class '<em>Host Resources</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Host Resources</em>'.
	 * @generated
	 */
	HostResources createHostResources();

	/**
	 * Returns a new object of class '<em>Host Resource</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Host Resource</em>'.
	 * @generated
	 */
	HostResource createHostResource();

	/**
	 * Returns a new object of class '<em>Master Files</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Master Files</em>'.
	 * @generated
	 */
	MasterFiles createMasterFiles();

	/**
	 * Returns a new object of class '<em>Processing Unit Config</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Processing Unit Config</em>'.
	 * @generated
	 */
	ProcessingUnitConfig createProcessingUnitConfig();

	/**
	 * Returns a new object of class '<em>Processing Units Config</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Processing Units Config</em>'.
	 * @generated
	 */
	ProcessingUnitsConfig createProcessingUnitsConfig();

	/**
	 * Returns a new object of class '<em>Pstools</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Pstools</em>'.
	 * @generated
	 */
	Pstools createPstools();

	/**
	 * Returns a new object of class '<em>Run Version</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Run Version</em>'.
	 * @generated
	 */
	RunVersion createRunVersion();

	/**
	 * Returns a new object of class '<em>Site</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Site</em>'.
	 * @generated
	 */
	Site createSite();

	/**
	 * Returns a new object of class '<em>Software</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Software</em>'.
	 * @generated
	 */
	Software createSoftware();

	/**
	 * Returns a new object of class '<em>Ssh</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Ssh</em>'.
	 * @generated
	 */
	Ssh createSsh();

	/**
	 * Returns a new object of class '<em>Start Pu Method</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Start Pu Method</em>'.
	 * @generated
	 */
	StartPuMethod createStartPuMethod();

	/**
	 * Returns a new object of class '<em>User Credentials</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>User Credentials</em>'.
	 * @generated
	 */
	UserCredentials createUserCredentials();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	TopologyPackage getTopologyPackage();

} //TopologyFactory
