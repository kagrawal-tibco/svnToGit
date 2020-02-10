/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.model.topology;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.core.model.topology.TopologyFactory
 * @model kind="package"
 * @generated
 */
public interface TopologyPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "topology";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TopologyPackage eINSTANCE = com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.model.topology.impl.BeRuntimeImpl <em>Be Runtime</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.model.topology.impl.BeRuntimeImpl
	 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getBeRuntime()
	 * @generated
	 */
	int BE_RUNTIME = 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE_RUNTIME__VERSION = 0;

	/**
	 * The number of structural features of the '<em>Be Runtime</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE_RUNTIME_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.model.topology.impl.BeImpl <em>Be</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.model.topology.impl.BeImpl
	 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getBe()
	 * @generated
	 */
	int BE = 1;

	/**
	 * The feature id for the '<em><b>Tra</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE__TRA = 0;

	/**
	 * The feature id for the '<em><b>Home</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE__HOME = 1;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE__VERSION = 2;

	/**
	 * The number of structural features of the '<em>Be</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.model.topology.impl.ClustersImpl <em>Clusters</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.model.topology.impl.ClustersImpl
	 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getClusters()
	 * @generated
	 */
	int CLUSTERS = 2;

	/**
	 * The feature id for the '<em><b>Cluster</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTERS__CLUSTER = 0;

	/**
	 * The number of structural features of the '<em>Clusters</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTERS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.model.topology.impl.ClusterImpl <em>Cluster</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.model.topology.impl.ClusterImpl
	 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getCluster()
	 * @generated
	 */
	int CLUSTER = 3;

	/**
	 * The feature id for the '<em><b>Master Files</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTER__MASTER_FILES = 0;

	/**
	 * The feature id for the '<em><b>Run Version</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTER__RUN_VERSION = 1;

	/**
	 * The feature id for the '<em><b>Deployment Units</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTER__DEPLOYMENT_UNITS = 2;

	/**
	 * The feature id for the '<em><b>Deployment Mappings</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTER__DEPLOYMENT_MAPPINGS = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTER__NAME = 4;

	/**
	 * The feature id for the '<em><b>Project Cdd</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTER__PROJECT_CDD = 5;

	/**
	 * The number of structural features of the '<em>Cluster</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLUSTER_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.model.topology.impl.DeployedFilesImpl <em>Deployed Files</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.model.topology.impl.DeployedFilesImpl
	 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getDeployedFiles()
	 * @generated
	 */
	int DEPLOYED_FILES = 4;

	/**
	 * The feature id for the '<em><b>Cdd Deployed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYED_FILES__CDD_DEPLOYED = 0;

	/**
	 * The feature id for the '<em><b>Ear Deployed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYED_FILES__EAR_DEPLOYED = 1;

	/**
	 * The number of structural features of the '<em>Deployed Files</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYED_FILES_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.model.topology.impl.DeploymentMappingsImpl <em>Deployment Mappings</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.model.topology.impl.DeploymentMappingsImpl
	 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getDeploymentMappings()
	 * @generated
	 */
	int DEPLOYMENT_MAPPINGS = 5;

	/**
	 * The feature id for the '<em><b>Deployment Mapping</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYMENT_MAPPINGS__DEPLOYMENT_MAPPING = 0;

	/**
	 * The number of structural features of the '<em>Deployment Mappings</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYMENT_MAPPINGS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.model.topology.impl.DeploymentMappingImpl <em>Deployment Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.model.topology.impl.DeploymentMappingImpl
	 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getDeploymentMapping()
	 * @generated
	 */
	int DEPLOYMENT_MAPPING = 6;

	/**
	 * The feature id for the '<em><b>Deployment Unit Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYMENT_MAPPING__DEPLOYMENT_UNIT_REF = 0;

	/**
	 * The feature id for the '<em><b>Host Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYMENT_MAPPING__HOST_REF = 1;

	/**
	 * The number of structural features of the '<em>Deployment Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYMENT_MAPPING_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.model.topology.impl.DeploymentUnitsImpl <em>Deployment Units</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.model.topology.impl.DeploymentUnitsImpl
	 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getDeploymentUnits()
	 * @generated
	 */
	int DEPLOYMENT_UNITS = 7;

	/**
	 * The feature id for the '<em><b>Deployment Unit</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYMENT_UNITS__DEPLOYMENT_UNIT = 0;

	/**
	 * The number of structural features of the '<em>Deployment Units</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYMENT_UNITS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.model.topology.impl.DeploymentUnitImpl <em>Deployment Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.model.topology.impl.DeploymentUnitImpl
	 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getDeploymentUnit()
	 * @generated
	 */
	int DEPLOYMENT_UNIT = 8;

	/**
	 * The feature id for the '<em><b>Deployed Files</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYMENT_UNIT__DEPLOYED_FILES = 0;

	/**
	 * The feature id for the '<em><b>Processing Units Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYMENT_UNIT__PROCESSING_UNITS_CONFIG = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYMENT_UNIT__ID = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYMENT_UNIT__NAME = 3;

	/**
	 * The number of structural features of the '<em>Deployment Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYMENT_UNIT_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl
	 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 9;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MIXED = 0;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Be</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BE = 3;

	/**
	 * The feature id for the '<em><b>Be Runtime</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BE_RUNTIME = 4;

	/**
	 * The feature id for the '<em><b>Cdd Deployed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CDD_DEPLOYED = 5;

	/**
	 * The feature id for the '<em><b>Cdd Master</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CDD_MASTER = 6;

	/**
	 * The feature id for the '<em><b>Cluster</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CLUSTER = 7;

	/**
	 * The feature id for the '<em><b>Clusters</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CLUSTERS = 8;

	/**
	 * The feature id for the '<em><b>Deployed Files</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DEPLOYED_FILES = 9;

	/**
	 * The feature id for the '<em><b>Deployment Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DEPLOYMENT_MAPPING = 10;

	/**
	 * The feature id for the '<em><b>Deployment Mappings</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DEPLOYMENT_MAPPINGS = 11;

	/**
	 * The feature id for the '<em><b>Deployment Unit</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DEPLOYMENT_UNIT = 12;

	/**
	 * The feature id for the '<em><b>Deployment Units</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DEPLOYMENT_UNITS = 13;

	/**
	 * The feature id for the '<em><b>Ear Deployed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EAR_DEPLOYED = 14;

	/**
	 * The feature id for the '<em><b>Ear Master</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EAR_MASTER = 15;

	/**
	 * The feature id for the '<em><b>Hawk</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__HAWK = 16;

	/**
	 * The feature id for the '<em><b>Home</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__HOME = 17;

	/**
	 * The feature id for the '<em><b>Hostname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__HOSTNAME = 18;

	/**
	 * The feature id for the '<em><b>Host Resource</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__HOST_RESOURCE = 19;

	/**
	 * The feature id for the '<em><b>Host Resources</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__HOST_RESOURCES = 20;

	/**
	 * The feature id for the '<em><b>Ip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__IP = 21;

	/**
	 * The feature id for the '<em><b>Master Files</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MASTER_FILES = 22;

	/**
	 * The feature id for the '<em><b>Os Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__OS_TYPE = 23;

	/**
	 * The feature id for the '<em><b>Processing Unit Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PROCESSING_UNIT_CONFIG = 24;

	/**
	 * The feature id for the '<em><b>Processing Units Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PROCESSING_UNITS_CONFIG = 25;

	/**
	 * The feature id for the '<em><b>Pstools</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PSTOOLS = 26;

	/**
	 * The feature id for the '<em><b>Run Version</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__RUN_VERSION = 27;

	/**
	 * The feature id for the '<em><b>Site</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SITE = 28;

	/**
	 * The feature id for the '<em><b>Software</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SOFTWARE = 29;

	/**
	 * The feature id for the '<em><b>Ssh</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SSH = 30;

	/**
	 * The feature id for the '<em><b>Start Pu Method</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__START_PU_METHOD = 31;

	/**
	 * The feature id for the '<em><b>Tra</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TRA = 32;

	/**
	 * The feature id for the '<em><b>User Credentials</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__USER_CREDENTIALS = 33;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 34;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.model.topology.impl.HawkImpl <em>Hawk</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.model.topology.impl.HawkImpl
	 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getHawk()
	 * @generated
	 */
	int HAWK = 10;

	/**
	 * The number of structural features of the '<em>Hawk</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HAWK_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.model.topology.impl.HostResourcesImpl <em>Host Resources</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.model.topology.impl.HostResourcesImpl
	 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getHostResources()
	 * @generated
	 */
	int HOST_RESOURCES = 11;

	/**
	 * The feature id for the '<em><b>Host Resource</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOST_RESOURCES__HOST_RESOURCE = 0;

	/**
	 * The number of structural features of the '<em>Host Resources</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOST_RESOURCES_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.model.topology.impl.HostResourceImpl <em>Host Resource</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.model.topology.impl.HostResourceImpl
	 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getHostResource()
	 * @generated
	 */
	int HOST_RESOURCE = 12;

	/**
	 * The feature id for the '<em><b>Hostname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOST_RESOURCE__HOSTNAME = 0;

	/**
	 * The feature id for the '<em><b>Ip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOST_RESOURCE__IP = 1;

	/**
	 * The feature id for the '<em><b>User Credentials</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOST_RESOURCE__USER_CREDENTIALS = 2;

	/**
	 * The feature id for the '<em><b>Os Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOST_RESOURCE__OS_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Software</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOST_RESOURCE__SOFTWARE = 4;

	/**
	 * The feature id for the '<em><b>Start Pu Method</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOST_RESOURCE__START_PU_METHOD = 5;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOST_RESOURCE__ID = 6;

	/**
	 * The number of structural features of the '<em>Host Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOST_RESOURCE_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.model.topology.impl.MasterFilesImpl <em>Master Files</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.model.topology.impl.MasterFilesImpl
	 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getMasterFiles()
	 * @generated
	 */
	int MASTER_FILES = 13;

	/**
	 * The feature id for the '<em><b>Cdd Master</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MASTER_FILES__CDD_MASTER = 0;

	/**
	 * The feature id for the '<em><b>Ear Master</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MASTER_FILES__EAR_MASTER = 1;

	/**
	 * The number of structural features of the '<em>Master Files</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MASTER_FILES_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.model.topology.impl.ProcessingUnitConfigImpl <em>Processing Unit Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.model.topology.impl.ProcessingUnitConfigImpl
	 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getProcessingUnitConfig()
	 * @generated
	 */
	int PROCESSING_UNIT_CONFIG = 14;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSING_UNIT_CONFIG__ID = 0;

	/**
	 * The feature id for the '<em><b>Jmxport</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSING_UNIT_CONFIG__JMXPORT = 1;

	/**
	 * The feature id for the '<em><b>Puid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSING_UNIT_CONFIG__PUID = 2;

	/**
	 * The feature id for the '<em><b>Use As Engine Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSING_UNIT_CONFIG__USE_AS_ENGINE_NAME = 3;

	/**
	 * The number of structural features of the '<em>Processing Unit Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSING_UNIT_CONFIG_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.model.topology.impl.ProcessingUnitsConfigImpl <em>Processing Units Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.model.topology.impl.ProcessingUnitsConfigImpl
	 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getProcessingUnitsConfig()
	 * @generated
	 */
	int PROCESSING_UNITS_CONFIG = 15;

	/**
	 * The feature id for the '<em><b>Processing Unit Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSING_UNITS_CONFIG__PROCESSING_UNIT_CONFIG = 0;

	/**
	 * The number of structural features of the '<em>Processing Units Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESSING_UNITS_CONFIG_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.model.topology.impl.PstoolsImpl <em>Pstools</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.model.topology.impl.PstoolsImpl
	 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getPstools()
	 * @generated
	 */
	int PSTOOLS = 16;

	/**
	 * The number of structural features of the '<em>Pstools</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSTOOLS_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.model.topology.impl.RunVersionImpl <em>Run Version</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.model.topology.impl.RunVersionImpl
	 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getRunVersion()
	 * @generated
	 */
	int RUN_VERSION = 17;

	/**
	 * The feature id for the '<em><b>Be Runtime</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RUN_VERSION__BE_RUNTIME = 0;

	/**
	 * The number of structural features of the '<em>Run Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RUN_VERSION_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.model.topology.impl.SiteImpl <em>Site</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.model.topology.impl.SiteImpl
	 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getSite()
	 * @generated
	 */
	int SITE = 18;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITE__DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Clusters</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITE__CLUSTERS = 1;

	/**
	 * The feature id for the '<em><b>Host Resources</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITE__HOST_RESOURCES = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITE__NAME = 3;

	/**
	 * The number of structural features of the '<em>Site</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.model.topology.impl.SoftwareImpl <em>Software</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.model.topology.impl.SoftwareImpl
	 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getSoftware()
	 * @generated
	 */
	int SOFTWARE = 19;

	/**
	 * The feature id for the '<em><b>Be</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOFTWARE__BE = 0;

	/**
	 * The number of structural features of the '<em>Software</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOFTWARE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.model.topology.impl.SshImpl <em>Ssh</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.model.topology.impl.SshImpl
	 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getSsh()
	 * @generated
	 */
	int SSH = 20;

	/**
	 * The feature id for the '<em><b>Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SSH__PORT = 0;

	/**
	 * The number of structural features of the '<em>Ssh</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SSH_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.model.topology.impl.StartPuMethodImpl <em>Start Pu Method</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.model.topology.impl.StartPuMethodImpl
	 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getStartPuMethod()
	 * @generated
	 */
	int START_PU_METHOD = 21;

	/**
	 * The feature id for the '<em><b>Hawk</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_PU_METHOD__HAWK = 0;

	/**
	 * The feature id for the '<em><b>Ssh</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_PU_METHOD__SSH = 1;

	/**
	 * The feature id for the '<em><b>Pstools</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_PU_METHOD__PSTOOLS = 2;

	/**
	 * The number of structural features of the '<em>Start Pu Method</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_PU_METHOD_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.model.topology.impl.UserCredentialsImpl <em>User Credentials</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.model.topology.impl.UserCredentialsImpl
	 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getUserCredentials()
	 * @generated
	 */
	int USER_CREDENTIALS = 22;

	/**
	 * The feature id for the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_CREDENTIALS__PASSWORD = 0;

	/**
	 * The feature id for the '<em><b>Username</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_CREDENTIALS__USERNAME = 1;

	/**
	 * The number of structural features of the '<em>User Credentials</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_CREDENTIALS_FEATURE_COUNT = 2;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.model.topology.BeRuntime <em>Be Runtime</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Be Runtime</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.BeRuntime
	 * @generated
	 */
	EClass getBeRuntime();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.BeRuntime#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.BeRuntime#getVersion()
	 * @see #getBeRuntime()
	 * @generated
	 */
	EAttribute getBeRuntime_Version();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.model.topology.Be <em>Be</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Be</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.Be
	 * @generated
	 */
	EClass getBe();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.Be#getTra <em>Tra</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tra</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.Be#getTra()
	 * @see #getBe()
	 * @generated
	 */
	EAttribute getBe_Tra();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.Be#getHome <em>Home</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Home</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.Be#getHome()
	 * @see #getBe()
	 * @generated
	 */
	EAttribute getBe_Home();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.Be#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.Be#getVersion()
	 * @see #getBe()
	 * @generated
	 */
	EAttribute getBe_Version();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.model.topology.Clusters <em>Clusters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Clusters</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.Clusters
	 * @generated
	 */
	EClass getClusters();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.core.model.topology.Clusters#getCluster <em>Cluster</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Cluster</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.Clusters#getCluster()
	 * @see #getClusters()
	 * @generated
	 */
	EReference getClusters_Cluster();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.model.topology.Cluster <em>Cluster</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cluster</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.Cluster
	 * @generated
	 */
	EClass getCluster();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.Cluster#getMasterFiles <em>Master Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Master Files</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.Cluster#getMasterFiles()
	 * @see #getCluster()
	 * @generated
	 */
	EReference getCluster_MasterFiles();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.Cluster#getRunVersion <em>Run Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Run Version</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.Cluster#getRunVersion()
	 * @see #getCluster()
	 * @generated
	 */
	EReference getCluster_RunVersion();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.Cluster#getDeploymentUnits <em>Deployment Units</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Deployment Units</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.Cluster#getDeploymentUnits()
	 * @see #getCluster()
	 * @generated
	 */
	EReference getCluster_DeploymentUnits();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.Cluster#getDeploymentMappings <em>Deployment Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Deployment Mappings</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.Cluster#getDeploymentMappings()
	 * @see #getCluster()
	 * @generated
	 */
	EReference getCluster_DeploymentMappings();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.Cluster#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.Cluster#getName()
	 * @see #getCluster()
	 * @generated
	 */
	EAttribute getCluster_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.Cluster#getProjectCdd <em>Project Cdd</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Project Cdd</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.Cluster#getProjectCdd()
	 * @see #getCluster()
	 * @generated
	 */
	EAttribute getCluster_ProjectCdd();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.model.topology.DeployedFiles <em>Deployed Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Deployed Files</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DeployedFiles
	 * @generated
	 */
	EClass getDeployedFiles();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.DeployedFiles#getCddDeployed <em>Cdd Deployed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cdd Deployed</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DeployedFiles#getCddDeployed()
	 * @see #getDeployedFiles()
	 * @generated
	 */
	EAttribute getDeployedFiles_CddDeployed();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.DeployedFiles#getEarDeployed <em>Ear Deployed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ear Deployed</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DeployedFiles#getEarDeployed()
	 * @see #getDeployedFiles()
	 * @generated
	 */
	EAttribute getDeployedFiles_EarDeployed();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.model.topology.DeploymentMappings <em>Deployment Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Deployment Mappings</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DeploymentMappings
	 * @generated
	 */
	EClass getDeploymentMappings();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.core.model.topology.DeploymentMappings#getDeploymentMapping <em>Deployment Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Deployment Mapping</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DeploymentMappings#getDeploymentMapping()
	 * @see #getDeploymentMappings()
	 * @generated
	 */
	EReference getDeploymentMappings_DeploymentMapping();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.model.topology.DeploymentMapping <em>Deployment Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Deployment Mapping</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DeploymentMapping
	 * @generated
	 */
	EClass getDeploymentMapping();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.DeploymentMapping#getDeploymentUnitRef <em>Deployment Unit Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Deployment Unit Ref</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DeploymentMapping#getDeploymentUnitRef()
	 * @see #getDeploymentMapping()
	 * @generated
	 */
	EAttribute getDeploymentMapping_DeploymentUnitRef();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.DeploymentMapping#getHostRef <em>Host Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Host Ref</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DeploymentMapping#getHostRef()
	 * @see #getDeploymentMapping()
	 * @generated
	 */
	EAttribute getDeploymentMapping_HostRef();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.model.topology.DeploymentUnits <em>Deployment Units</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Deployment Units</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DeploymentUnits
	 * @generated
	 */
	EClass getDeploymentUnits();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.core.model.topology.DeploymentUnits#getDeploymentUnit <em>Deployment Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Deployment Unit</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DeploymentUnits#getDeploymentUnit()
	 * @see #getDeploymentUnits()
	 * @generated
	 */
	EReference getDeploymentUnits_DeploymentUnit();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.model.topology.DeploymentUnit <em>Deployment Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Deployment Unit</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DeploymentUnit
	 * @generated
	 */
	EClass getDeploymentUnit();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.DeploymentUnit#getDeployedFiles <em>Deployed Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Deployed Files</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DeploymentUnit#getDeployedFiles()
	 * @see #getDeploymentUnit()
	 * @generated
	 */
	EReference getDeploymentUnit_DeployedFiles();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.DeploymentUnit#getProcessingUnitsConfig <em>Processing Units Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Processing Units Config</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DeploymentUnit#getProcessingUnitsConfig()
	 * @see #getDeploymentUnit()
	 * @generated
	 */
	EReference getDeploymentUnit_ProcessingUnitsConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.DeploymentUnit#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DeploymentUnit#getId()
	 * @see #getDeploymentUnit()
	 * @generated
	 */
	EAttribute getDeploymentUnit_Id();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.DeploymentUnit#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DeploymentUnit#getName()
	 * @see #getDeploymentUnit()
	 * @generated
	 */
	EAttribute getDeploymentUnit_Name();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getBe <em>Be</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Be</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getBe()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Be();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getBeRuntime <em>Be Runtime</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Be Runtime</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getBeRuntime()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_BeRuntime();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getCddDeployed <em>Cdd Deployed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cdd Deployed</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getCddDeployed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_CddDeployed();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getCddMaster <em>Cdd Master</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cdd Master</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getCddMaster()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_CddMaster();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getCluster <em>Cluster</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cluster</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getCluster()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Cluster();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getClusters <em>Clusters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Clusters</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getClusters()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Clusters();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getDeployedFiles <em>Deployed Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Deployed Files</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getDeployedFiles()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_DeployedFiles();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getDeploymentMapping <em>Deployment Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Deployment Mapping</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getDeploymentMapping()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_DeploymentMapping();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getDeploymentMappings <em>Deployment Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Deployment Mappings</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getDeploymentMappings()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_DeploymentMappings();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getDeploymentUnit <em>Deployment Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Deployment Unit</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getDeploymentUnit()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_DeploymentUnit();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getDeploymentUnits <em>Deployment Units</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Deployment Units</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getDeploymentUnits()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_DeploymentUnits();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getEarDeployed <em>Ear Deployed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ear Deployed</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getEarDeployed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_EarDeployed();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getEarMaster <em>Ear Master</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ear Master</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getEarMaster()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_EarMaster();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getHawk <em>Hawk</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Hawk</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getHawk()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Hawk();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getHome <em>Home</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Home</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getHome()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Home();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getHostname <em>Hostname</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hostname</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getHostname()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Hostname();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getHostResource <em>Host Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Host Resource</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getHostResource()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_HostResource();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getHostResources <em>Host Resources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Host Resources</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getHostResources()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_HostResources();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getIp <em>Ip</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ip</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getIp()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Ip();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getMasterFiles <em>Master Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Master Files</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getMasterFiles()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MasterFiles();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getOsType <em>Os Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Os Type</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getOsType()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_OsType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getProcessingUnitConfig <em>Processing Unit Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Processing Unit Config</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getProcessingUnitConfig()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ProcessingUnitConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getProcessingUnitsConfig <em>Processing Units Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Processing Units Config</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getProcessingUnitsConfig()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ProcessingUnitsConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getPstools <em>Pstools</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pstools</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getPstools()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Pstools();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getRunVersion <em>Run Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Run Version</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getRunVersion()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_RunVersion();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getSite <em>Site</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Site</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getSite()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Site();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getSoftware <em>Software</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Software</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getSoftware()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Software();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getSsh <em>Ssh</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Ssh</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getSsh()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Ssh();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getStartPuMethod <em>Start Pu Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Start Pu Method</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getStartPuMethod()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_StartPuMethod();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getTra <em>Tra</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tra</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getTra()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Tra();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getUserCredentials <em>User Credentials</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>User Credentials</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot#getUserCredentials()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_UserCredentials();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.model.topology.Hawk <em>Hawk</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Hawk</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.Hawk
	 * @generated
	 */
	EClass getHawk();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.model.topology.HostResources <em>Host Resources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Host Resources</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.HostResources
	 * @generated
	 */
	EClass getHostResources();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.core.model.topology.HostResources#getHostResource <em>Host Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Host Resource</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.HostResources#getHostResource()
	 * @see #getHostResources()
	 * @generated
	 */
	EReference getHostResources_HostResource();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.model.topology.HostResource <em>Host Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Host Resource</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.HostResource
	 * @generated
	 */
	EClass getHostResource();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.HostResource#getHostname <em>Hostname</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hostname</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.HostResource#getHostname()
	 * @see #getHostResource()
	 * @generated
	 */
	EAttribute getHostResource_Hostname();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.HostResource#getIp <em>Ip</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ip</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.HostResource#getIp()
	 * @see #getHostResource()
	 * @generated
	 */
	EAttribute getHostResource_Ip();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.HostResource#getUserCredentials <em>User Credentials</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>User Credentials</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.HostResource#getUserCredentials()
	 * @see #getHostResource()
	 * @generated
	 */
	EReference getHostResource_UserCredentials();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.HostResource#getOsType <em>Os Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Os Type</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.HostResource#getOsType()
	 * @see #getHostResource()
	 * @generated
	 */
	EAttribute getHostResource_OsType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.HostResource#getSoftware <em>Software</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Software</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.HostResource#getSoftware()
	 * @see #getHostResource()
	 * @generated
	 */
	EReference getHostResource_Software();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.HostResource#getStartPuMethod <em>Start Pu Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Start Pu Method</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.HostResource#getStartPuMethod()
	 * @see #getHostResource()
	 * @generated
	 */
	EReference getHostResource_StartPuMethod();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.HostResource#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.HostResource#getId()
	 * @see #getHostResource()
	 * @generated
	 */
	EAttribute getHostResource_Id();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.model.topology.MasterFiles <em>Master Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Master Files</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.MasterFiles
	 * @generated
	 */
	EClass getMasterFiles();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.MasterFiles#getCddMaster <em>Cdd Master</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cdd Master</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.MasterFiles#getCddMaster()
	 * @see #getMasterFiles()
	 * @generated
	 */
	EAttribute getMasterFiles_CddMaster();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.MasterFiles#getEarMaster <em>Ear Master</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ear Master</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.MasterFiles#getEarMaster()
	 * @see #getMasterFiles()
	 * @generated
	 */
	EAttribute getMasterFiles_EarMaster();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.model.topology.ProcessingUnitConfig <em>Processing Unit Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Processing Unit Config</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.ProcessingUnitConfig
	 * @generated
	 */
	EClass getProcessingUnitConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.ProcessingUnitConfig#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.ProcessingUnitConfig#getId()
	 * @see #getProcessingUnitConfig()
	 * @generated
	 */
	EAttribute getProcessingUnitConfig_Id();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.ProcessingUnitConfig#getJmxport <em>Jmxport</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Jmxport</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.ProcessingUnitConfig#getJmxport()
	 * @see #getProcessingUnitConfig()
	 * @generated
	 */
	EAttribute getProcessingUnitConfig_Jmxport();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.ProcessingUnitConfig#getPuid <em>Puid</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Puid</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.ProcessingUnitConfig#getPuid()
	 * @see #getProcessingUnitConfig()
	 * @generated
	 */
	EAttribute getProcessingUnitConfig_Puid();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.ProcessingUnitConfig#isUseAsEngineName <em>Use As Engine Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Use As Engine Name</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.ProcessingUnitConfig#isUseAsEngineName()
	 * @see #getProcessingUnitConfig()
	 * @generated
	 */
	EAttribute getProcessingUnitConfig_UseAsEngineName();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.model.topology.ProcessingUnitsConfig <em>Processing Units Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Processing Units Config</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.ProcessingUnitsConfig
	 * @generated
	 */
	EClass getProcessingUnitsConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.core.model.topology.ProcessingUnitsConfig#getProcessingUnitConfig <em>Processing Unit Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Processing Unit Config</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.ProcessingUnitsConfig#getProcessingUnitConfig()
	 * @see #getProcessingUnitsConfig()
	 * @generated
	 */
	EReference getProcessingUnitsConfig_ProcessingUnitConfig();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.model.topology.Pstools <em>Pstools</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pstools</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.Pstools
	 * @generated
	 */
	EClass getPstools();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.model.topology.RunVersion <em>Run Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Run Version</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.RunVersion
	 * @generated
	 */
	EClass getRunVersion();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.RunVersion#getBeRuntime <em>Be Runtime</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Be Runtime</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.RunVersion#getBeRuntime()
	 * @see #getRunVersion()
	 * @generated
	 */
	EReference getRunVersion_BeRuntime();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.model.topology.Site <em>Site</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Site</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.Site
	 * @generated
	 */
	EClass getSite();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.Site#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.Site#getDescription()
	 * @see #getSite()
	 * @generated
	 */
	EAttribute getSite_Description();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.Site#getClusters <em>Clusters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Clusters</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.Site#getClusters()
	 * @see #getSite()
	 * @generated
	 */
	EReference getSite_Clusters();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.Site#getHostResources <em>Host Resources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Host Resources</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.Site#getHostResources()
	 * @see #getSite()
	 * @generated
	 */
	EReference getSite_HostResources();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.Site#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.Site#getName()
	 * @see #getSite()
	 * @generated
	 */
	EAttribute getSite_Name();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.model.topology.Software <em>Software</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Software</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.Software
	 * @generated
	 */
	EClass getSoftware();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.core.model.topology.Software#getBe <em>Be</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Be</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.Software#getBe()
	 * @see #getSoftware()
	 * @generated
	 */
	EReference getSoftware_Be();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.model.topology.Ssh <em>Ssh</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ssh</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.Ssh
	 * @generated
	 */
	EClass getSsh();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.Ssh#getPort <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Port</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.Ssh#getPort()
	 * @see #getSsh()
	 * @generated
	 */
	EAttribute getSsh_Port();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.model.topology.StartPuMethod <em>Start Pu Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Start Pu Method</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.StartPuMethod
	 * @generated
	 */
	EClass getStartPuMethod();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.StartPuMethod#getHawk <em>Hawk</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Hawk</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.StartPuMethod#getHawk()
	 * @see #getStartPuMethod()
	 * @generated
	 */
	EReference getStartPuMethod_Hawk();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.StartPuMethod#getSsh <em>Ssh</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Ssh</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.StartPuMethod#getSsh()
	 * @see #getStartPuMethod()
	 * @generated
	 */
	EReference getStartPuMethod_Ssh();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.model.topology.StartPuMethod#getPstools <em>Pstools</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pstools</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.StartPuMethod#getPstools()
	 * @see #getStartPuMethod()
	 * @generated
	 */
	EReference getStartPuMethod_Pstools();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.model.topology.UserCredentials <em>User Credentials</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>User Credentials</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.UserCredentials
	 * @generated
	 */
	EClass getUserCredentials();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.UserCredentials#getPassword <em>Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Password</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.UserCredentials#getPassword()
	 * @see #getUserCredentials()
	 * @generated
	 */
	EAttribute getUserCredentials_Password();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.model.topology.UserCredentials#getUsername <em>Username</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Username</em>'.
	 * @see com.tibco.cep.studio.core.model.topology.UserCredentials#getUsername()
	 * @see #getUserCredentials()
	 * @generated
	 */
	EAttribute getUserCredentials_Username();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TopologyFactory getTopologyFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.model.topology.impl.BeRuntimeImpl <em>Be Runtime</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.model.topology.impl.BeRuntimeImpl
		 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getBeRuntime()
		 * @generated
		 */
		EClass BE_RUNTIME = eINSTANCE.getBeRuntime();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BE_RUNTIME__VERSION = eINSTANCE.getBeRuntime_Version();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.model.topology.impl.BeImpl <em>Be</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.model.topology.impl.BeImpl
		 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getBe()
		 * @generated
		 */
		EClass BE = eINSTANCE.getBe();

		/**
		 * The meta object literal for the '<em><b>Tra</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BE__TRA = eINSTANCE.getBe_Tra();

		/**
		 * The meta object literal for the '<em><b>Home</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BE__HOME = eINSTANCE.getBe_Home();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BE__VERSION = eINSTANCE.getBe_Version();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.model.topology.impl.ClustersImpl <em>Clusters</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.model.topology.impl.ClustersImpl
		 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getClusters()
		 * @generated
		 */
		EClass CLUSTERS = eINSTANCE.getClusters();

		/**
		 * The meta object literal for the '<em><b>Cluster</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLUSTERS__CLUSTER = eINSTANCE.getClusters_Cluster();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.model.topology.impl.ClusterImpl <em>Cluster</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.model.topology.impl.ClusterImpl
		 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getCluster()
		 * @generated
		 */
		EClass CLUSTER = eINSTANCE.getCluster();

		/**
		 * The meta object literal for the '<em><b>Master Files</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLUSTER__MASTER_FILES = eINSTANCE.getCluster_MasterFiles();

		/**
		 * The meta object literal for the '<em><b>Run Version</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLUSTER__RUN_VERSION = eINSTANCE.getCluster_RunVersion();

		/**
		 * The meta object literal for the '<em><b>Deployment Units</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLUSTER__DEPLOYMENT_UNITS = eINSTANCE.getCluster_DeploymentUnits();

		/**
		 * The meta object literal for the '<em><b>Deployment Mappings</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLUSTER__DEPLOYMENT_MAPPINGS = eINSTANCE.getCluster_DeploymentMappings();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLUSTER__NAME = eINSTANCE.getCluster_Name();

		/**
		 * The meta object literal for the '<em><b>Project Cdd</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLUSTER__PROJECT_CDD = eINSTANCE.getCluster_ProjectCdd();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.model.topology.impl.DeployedFilesImpl <em>Deployed Files</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.model.topology.impl.DeployedFilesImpl
		 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getDeployedFiles()
		 * @generated
		 */
		EClass DEPLOYED_FILES = eINSTANCE.getDeployedFiles();

		/**
		 * The meta object literal for the '<em><b>Cdd Deployed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEPLOYED_FILES__CDD_DEPLOYED = eINSTANCE.getDeployedFiles_CddDeployed();

		/**
		 * The meta object literal for the '<em><b>Ear Deployed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEPLOYED_FILES__EAR_DEPLOYED = eINSTANCE.getDeployedFiles_EarDeployed();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.model.topology.impl.DeploymentMappingsImpl <em>Deployment Mappings</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.model.topology.impl.DeploymentMappingsImpl
		 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getDeploymentMappings()
		 * @generated
		 */
		EClass DEPLOYMENT_MAPPINGS = eINSTANCE.getDeploymentMappings();

		/**
		 * The meta object literal for the '<em><b>Deployment Mapping</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEPLOYMENT_MAPPINGS__DEPLOYMENT_MAPPING = eINSTANCE.getDeploymentMappings_DeploymentMapping();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.model.topology.impl.DeploymentMappingImpl <em>Deployment Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.model.topology.impl.DeploymentMappingImpl
		 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getDeploymentMapping()
		 * @generated
		 */
		EClass DEPLOYMENT_MAPPING = eINSTANCE.getDeploymentMapping();

		/**
		 * The meta object literal for the '<em><b>Deployment Unit Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEPLOYMENT_MAPPING__DEPLOYMENT_UNIT_REF = eINSTANCE.getDeploymentMapping_DeploymentUnitRef();

		/**
		 * The meta object literal for the '<em><b>Host Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEPLOYMENT_MAPPING__HOST_REF = eINSTANCE.getDeploymentMapping_HostRef();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.model.topology.impl.DeploymentUnitsImpl <em>Deployment Units</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.model.topology.impl.DeploymentUnitsImpl
		 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getDeploymentUnits()
		 * @generated
		 */
		EClass DEPLOYMENT_UNITS = eINSTANCE.getDeploymentUnits();

		/**
		 * The meta object literal for the '<em><b>Deployment Unit</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEPLOYMENT_UNITS__DEPLOYMENT_UNIT = eINSTANCE.getDeploymentUnits_DeploymentUnit();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.model.topology.impl.DeploymentUnitImpl <em>Deployment Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.model.topology.impl.DeploymentUnitImpl
		 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getDeploymentUnit()
		 * @generated
		 */
		EClass DEPLOYMENT_UNIT = eINSTANCE.getDeploymentUnit();

		/**
		 * The meta object literal for the '<em><b>Deployed Files</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEPLOYMENT_UNIT__DEPLOYED_FILES = eINSTANCE.getDeploymentUnit_DeployedFiles();

		/**
		 * The meta object literal for the '<em><b>Processing Units Config</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEPLOYMENT_UNIT__PROCESSING_UNITS_CONFIG = eINSTANCE.getDeploymentUnit_ProcessingUnitsConfig();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEPLOYMENT_UNIT__ID = eINSTANCE.getDeploymentUnit_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEPLOYMENT_UNIT__NAME = eINSTANCE.getDeploymentUnit_Name();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl
		 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getDocumentRoot()
		 * @generated
		 */
		EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

		/**
		 * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

		/**
		 * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getDocumentRoot_XSISchemaLocation();

		/**
		 * The meta object literal for the '<em><b>Be</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__BE = eINSTANCE.getDocumentRoot_Be();

		/**
		 * The meta object literal for the '<em><b>Be Runtime</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__BE_RUNTIME = eINSTANCE.getDocumentRoot_BeRuntime();

		/**
		 * The meta object literal for the '<em><b>Cdd Deployed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__CDD_DEPLOYED = eINSTANCE.getDocumentRoot_CddDeployed();

		/**
		 * The meta object literal for the '<em><b>Cdd Master</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__CDD_MASTER = eINSTANCE.getDocumentRoot_CddMaster();

		/**
		 * The meta object literal for the '<em><b>Cluster</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__CLUSTER = eINSTANCE.getDocumentRoot_Cluster();

		/**
		 * The meta object literal for the '<em><b>Clusters</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__CLUSTERS = eINSTANCE.getDocumentRoot_Clusters();

		/**
		 * The meta object literal for the '<em><b>Deployed Files</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__DEPLOYED_FILES = eINSTANCE.getDocumentRoot_DeployedFiles();

		/**
		 * The meta object literal for the '<em><b>Deployment Mapping</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__DEPLOYMENT_MAPPING = eINSTANCE.getDocumentRoot_DeploymentMapping();

		/**
		 * The meta object literal for the '<em><b>Deployment Mappings</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__DEPLOYMENT_MAPPINGS = eINSTANCE.getDocumentRoot_DeploymentMappings();

		/**
		 * The meta object literal for the '<em><b>Deployment Unit</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__DEPLOYMENT_UNIT = eINSTANCE.getDocumentRoot_DeploymentUnit();

		/**
		 * The meta object literal for the '<em><b>Deployment Units</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__DEPLOYMENT_UNITS = eINSTANCE.getDocumentRoot_DeploymentUnits();

		/**
		 * The meta object literal for the '<em><b>Ear Deployed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__EAR_DEPLOYED = eINSTANCE.getDocumentRoot_EarDeployed();

		/**
		 * The meta object literal for the '<em><b>Ear Master</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__EAR_MASTER = eINSTANCE.getDocumentRoot_EarMaster();

		/**
		 * The meta object literal for the '<em><b>Hawk</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__HAWK = eINSTANCE.getDocumentRoot_Hawk();

		/**
		 * The meta object literal for the '<em><b>Home</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__HOME = eINSTANCE.getDocumentRoot_Home();

		/**
		 * The meta object literal for the '<em><b>Hostname</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__HOSTNAME = eINSTANCE.getDocumentRoot_Hostname();

		/**
		 * The meta object literal for the '<em><b>Host Resource</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__HOST_RESOURCE = eINSTANCE.getDocumentRoot_HostResource();

		/**
		 * The meta object literal for the '<em><b>Host Resources</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__HOST_RESOURCES = eINSTANCE.getDocumentRoot_HostResources();

		/**
		 * The meta object literal for the '<em><b>Ip</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__IP = eINSTANCE.getDocumentRoot_Ip();

		/**
		 * The meta object literal for the '<em><b>Master Files</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MASTER_FILES = eINSTANCE.getDocumentRoot_MasterFiles();

		/**
		 * The meta object literal for the '<em><b>Os Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__OS_TYPE = eINSTANCE.getDocumentRoot_OsType();

		/**
		 * The meta object literal for the '<em><b>Processing Unit Config</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__PROCESSING_UNIT_CONFIG = eINSTANCE.getDocumentRoot_ProcessingUnitConfig();

		/**
		 * The meta object literal for the '<em><b>Processing Units Config</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__PROCESSING_UNITS_CONFIG = eINSTANCE.getDocumentRoot_ProcessingUnitsConfig();

		/**
		 * The meta object literal for the '<em><b>Pstools</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__PSTOOLS = eINSTANCE.getDocumentRoot_Pstools();

		/**
		 * The meta object literal for the '<em><b>Run Version</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__RUN_VERSION = eINSTANCE.getDocumentRoot_RunVersion();

		/**
		 * The meta object literal for the '<em><b>Site</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__SITE = eINSTANCE.getDocumentRoot_Site();

		/**
		 * The meta object literal for the '<em><b>Software</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__SOFTWARE = eINSTANCE.getDocumentRoot_Software();

		/**
		 * The meta object literal for the '<em><b>Ssh</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__SSH = eINSTANCE.getDocumentRoot_Ssh();

		/**
		 * The meta object literal for the '<em><b>Start Pu Method</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__START_PU_METHOD = eINSTANCE.getDocumentRoot_StartPuMethod();

		/**
		 * The meta object literal for the '<em><b>Tra</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__TRA = eINSTANCE.getDocumentRoot_Tra();

		/**
		 * The meta object literal for the '<em><b>User Credentials</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__USER_CREDENTIALS = eINSTANCE.getDocumentRoot_UserCredentials();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.model.topology.impl.HawkImpl <em>Hawk</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.model.topology.impl.HawkImpl
		 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getHawk()
		 * @generated
		 */
		EClass HAWK = eINSTANCE.getHawk();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.model.topology.impl.HostResourcesImpl <em>Host Resources</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.model.topology.impl.HostResourcesImpl
		 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getHostResources()
		 * @generated
		 */
		EClass HOST_RESOURCES = eINSTANCE.getHostResources();

		/**
		 * The meta object literal for the '<em><b>Host Resource</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HOST_RESOURCES__HOST_RESOURCE = eINSTANCE.getHostResources_HostResource();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.model.topology.impl.HostResourceImpl <em>Host Resource</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.model.topology.impl.HostResourceImpl
		 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getHostResource()
		 * @generated
		 */
		EClass HOST_RESOURCE = eINSTANCE.getHostResource();

		/**
		 * The meta object literal for the '<em><b>Hostname</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HOST_RESOURCE__HOSTNAME = eINSTANCE.getHostResource_Hostname();

		/**
		 * The meta object literal for the '<em><b>Ip</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HOST_RESOURCE__IP = eINSTANCE.getHostResource_Ip();

		/**
		 * The meta object literal for the '<em><b>User Credentials</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HOST_RESOURCE__USER_CREDENTIALS = eINSTANCE.getHostResource_UserCredentials();

		/**
		 * The meta object literal for the '<em><b>Os Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HOST_RESOURCE__OS_TYPE = eINSTANCE.getHostResource_OsType();

		/**
		 * The meta object literal for the '<em><b>Software</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HOST_RESOURCE__SOFTWARE = eINSTANCE.getHostResource_Software();

		/**
		 * The meta object literal for the '<em><b>Start Pu Method</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HOST_RESOURCE__START_PU_METHOD = eINSTANCE.getHostResource_StartPuMethod();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HOST_RESOURCE__ID = eINSTANCE.getHostResource_Id();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.model.topology.impl.MasterFilesImpl <em>Master Files</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.model.topology.impl.MasterFilesImpl
		 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getMasterFiles()
		 * @generated
		 */
		EClass MASTER_FILES = eINSTANCE.getMasterFiles();

		/**
		 * The meta object literal for the '<em><b>Cdd Master</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MASTER_FILES__CDD_MASTER = eINSTANCE.getMasterFiles_CddMaster();

		/**
		 * The meta object literal for the '<em><b>Ear Master</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MASTER_FILES__EAR_MASTER = eINSTANCE.getMasterFiles_EarMaster();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.model.topology.impl.ProcessingUnitConfigImpl <em>Processing Unit Config</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.model.topology.impl.ProcessingUnitConfigImpl
		 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getProcessingUnitConfig()
		 * @generated
		 */
		EClass PROCESSING_UNIT_CONFIG = eINSTANCE.getProcessingUnitConfig();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROCESSING_UNIT_CONFIG__ID = eINSTANCE.getProcessingUnitConfig_Id();

		/**
		 * The meta object literal for the '<em><b>Jmxport</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROCESSING_UNIT_CONFIG__JMXPORT = eINSTANCE.getProcessingUnitConfig_Jmxport();

		/**
		 * The meta object literal for the '<em><b>Puid</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROCESSING_UNIT_CONFIG__PUID = eINSTANCE.getProcessingUnitConfig_Puid();

		/**
		 * The meta object literal for the '<em><b>Use As Engine Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROCESSING_UNIT_CONFIG__USE_AS_ENGINE_NAME = eINSTANCE.getProcessingUnitConfig_UseAsEngineName();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.model.topology.impl.ProcessingUnitsConfigImpl <em>Processing Units Config</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.model.topology.impl.ProcessingUnitsConfigImpl
		 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getProcessingUnitsConfig()
		 * @generated
		 */
		EClass PROCESSING_UNITS_CONFIG = eINSTANCE.getProcessingUnitsConfig();

		/**
		 * The meta object literal for the '<em><b>Processing Unit Config</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROCESSING_UNITS_CONFIG__PROCESSING_UNIT_CONFIG = eINSTANCE.getProcessingUnitsConfig_ProcessingUnitConfig();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.model.topology.impl.PstoolsImpl <em>Pstools</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.model.topology.impl.PstoolsImpl
		 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getPstools()
		 * @generated
		 */
		EClass PSTOOLS = eINSTANCE.getPstools();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.model.topology.impl.RunVersionImpl <em>Run Version</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.model.topology.impl.RunVersionImpl
		 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getRunVersion()
		 * @generated
		 */
		EClass RUN_VERSION = eINSTANCE.getRunVersion();

		/**
		 * The meta object literal for the '<em><b>Be Runtime</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RUN_VERSION__BE_RUNTIME = eINSTANCE.getRunVersion_BeRuntime();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.model.topology.impl.SiteImpl <em>Site</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.model.topology.impl.SiteImpl
		 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getSite()
		 * @generated
		 */
		EClass SITE = eINSTANCE.getSite();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SITE__DESCRIPTION = eINSTANCE.getSite_Description();

		/**
		 * The meta object literal for the '<em><b>Clusters</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SITE__CLUSTERS = eINSTANCE.getSite_Clusters();

		/**
		 * The meta object literal for the '<em><b>Host Resources</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SITE__HOST_RESOURCES = eINSTANCE.getSite_HostResources();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SITE__NAME = eINSTANCE.getSite_Name();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.model.topology.impl.SoftwareImpl <em>Software</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.model.topology.impl.SoftwareImpl
		 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getSoftware()
		 * @generated
		 */
		EClass SOFTWARE = eINSTANCE.getSoftware();

		/**
		 * The meta object literal for the '<em><b>Be</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOFTWARE__BE = eINSTANCE.getSoftware_Be();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.model.topology.impl.SshImpl <em>Ssh</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.model.topology.impl.SshImpl
		 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getSsh()
		 * @generated
		 */
		EClass SSH = eINSTANCE.getSsh();

		/**
		 * The meta object literal for the '<em><b>Port</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SSH__PORT = eINSTANCE.getSsh_Port();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.model.topology.impl.StartPuMethodImpl <em>Start Pu Method</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.model.topology.impl.StartPuMethodImpl
		 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getStartPuMethod()
		 * @generated
		 */
		EClass START_PU_METHOD = eINSTANCE.getStartPuMethod();

		/**
		 * The meta object literal for the '<em><b>Hawk</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference START_PU_METHOD__HAWK = eINSTANCE.getStartPuMethod_Hawk();

		/**
		 * The meta object literal for the '<em><b>Ssh</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference START_PU_METHOD__SSH = eINSTANCE.getStartPuMethod_Ssh();

		/**
		 * The meta object literal for the '<em><b>Pstools</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference START_PU_METHOD__PSTOOLS = eINSTANCE.getStartPuMethod_Pstools();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.model.topology.impl.UserCredentialsImpl <em>User Credentials</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.model.topology.impl.UserCredentialsImpl
		 * @see com.tibco.cep.studio.core.model.topology.impl.TopologyPackageImpl#getUserCredentials()
		 * @generated
		 */
		EClass USER_CREDENTIALS = eINSTANCE.getUserCredentials();

		/**
		 * The meta object literal for the '<em><b>Password</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER_CREDENTIALS__PASSWORD = eINSTANCE.getUserCredentials_Password();

		/**
		 * The meta object literal for the '<em><b>Username</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER_CREDENTIALS__USERNAME = eINSTANCE.getUserCredentials_Username();

	}

} //TopologyPackage
