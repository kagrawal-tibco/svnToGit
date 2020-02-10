/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.model.topology.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.studio.core.model.topology.Be;
import com.tibco.cep.studio.core.model.topology.BeRuntime;
import com.tibco.cep.studio.core.model.topology.Cluster;
import com.tibco.cep.studio.core.model.topology.Clusters;
import com.tibco.cep.studio.core.model.topology.DeployedFiles;
import com.tibco.cep.studio.core.model.topology.DeploymentMapping;
import com.tibco.cep.studio.core.model.topology.DeploymentMappings;
import com.tibco.cep.studio.core.model.topology.DeploymentUnit;
import com.tibco.cep.studio.core.model.topology.DeploymentUnits;
import com.tibco.cep.studio.core.model.topology.DocumentRoot;
import com.tibco.cep.studio.core.model.topology.Hawk;
import com.tibco.cep.studio.core.model.topology.HostResource;
import com.tibco.cep.studio.core.model.topology.HostResources;
import com.tibco.cep.studio.core.model.topology.MasterFiles;
import com.tibco.cep.studio.core.model.topology.ProcessingUnitConfig;
import com.tibco.cep.studio.core.model.topology.ProcessingUnitsConfig;
import com.tibco.cep.studio.core.model.topology.Pstools;
import com.tibco.cep.studio.core.model.topology.RunVersion;
import com.tibco.cep.studio.core.model.topology.Site;
import com.tibco.cep.studio.core.model.topology.Software;
import com.tibco.cep.studio.core.model.topology.Ssh;
import com.tibco.cep.studio.core.model.topology.StartPuMethod;
import com.tibco.cep.studio.core.model.topology.TopologyFactory;
import com.tibco.cep.studio.core.model.topology.TopologyPackage;
import com.tibco.cep.studio.core.model.topology.UserCredentials;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TopologyFactoryImpl extends EFactoryImpl implements TopologyFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TopologyFactory init() {
		try {
			TopologyFactory theTopologyFactory = (TopologyFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd"); 
			if (theTopologyFactory != null) {
				return theTopologyFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TopologyFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TopologyFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case TopologyPackage.BE_RUNTIME: return createBeRuntime();
			case TopologyPackage.BE: return createBe();
			case TopologyPackage.CLUSTERS: return createClusters();
			case TopologyPackage.CLUSTER: return createCluster();
			case TopologyPackage.DEPLOYED_FILES: return createDeployedFiles();
			case TopologyPackage.DEPLOYMENT_MAPPINGS: return createDeploymentMappings();
			case TopologyPackage.DEPLOYMENT_MAPPING: return createDeploymentMapping();
			case TopologyPackage.DEPLOYMENT_UNITS: return createDeploymentUnits();
			case TopologyPackage.DEPLOYMENT_UNIT: return createDeploymentUnit();
			case TopologyPackage.DOCUMENT_ROOT: return createDocumentRoot();
			case TopologyPackage.HAWK: return createHawk();
			case TopologyPackage.HOST_RESOURCES: return createHostResources();
			case TopologyPackage.HOST_RESOURCE: return createHostResource();
			case TopologyPackage.MASTER_FILES: return createMasterFiles();
			case TopologyPackage.PROCESSING_UNIT_CONFIG: return createProcessingUnitConfig();
			case TopologyPackage.PROCESSING_UNITS_CONFIG: return createProcessingUnitsConfig();
			case TopologyPackage.PSTOOLS: return createPstools();
			case TopologyPackage.RUN_VERSION: return createRunVersion();
			case TopologyPackage.SITE: return createSite();
			case TopologyPackage.SOFTWARE: return createSoftware();
			case TopologyPackage.SSH: return createSsh();
			case TopologyPackage.START_PU_METHOD: return createStartPuMethod();
			case TopologyPackage.USER_CREDENTIALS: return createUserCredentials();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BeRuntime createBeRuntime() {
		BeRuntimeImpl beRuntime = new BeRuntimeImpl();
		return beRuntime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Be createBe() {
		BeImpl be = new BeImpl();
		return be;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Clusters createClusters() {
		ClustersImpl clusters = new ClustersImpl();
		return clusters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Cluster createCluster() {
		ClusterImpl cluster = new ClusterImpl();
		return cluster;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeployedFiles createDeployedFiles() {
		DeployedFilesImpl deployedFiles = new DeployedFilesImpl();
		return deployedFiles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeploymentMappings createDeploymentMappings() {
		DeploymentMappingsImpl deploymentMappings = new DeploymentMappingsImpl();
		return deploymentMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeploymentMapping createDeploymentMapping() {
		DeploymentMappingImpl deploymentMapping = new DeploymentMappingImpl();
		return deploymentMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeploymentUnits createDeploymentUnits() {
		DeploymentUnitsImpl deploymentUnits = new DeploymentUnitsImpl();
		return deploymentUnits;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeploymentUnit createDeploymentUnit() {
		DeploymentUnitImpl deploymentUnit = new DeploymentUnitImpl();
		return deploymentUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentRoot createDocumentRoot() {
		DocumentRootImpl documentRoot = new DocumentRootImpl();
		return documentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Hawk createHawk() {
		HawkImpl hawk = new HawkImpl();
		return hawk;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HostResources createHostResources() {
		HostResourcesImpl hostResources = new HostResourcesImpl();
		return hostResources;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HostResource createHostResource() {
		HostResourceImpl hostResource = new HostResourceImpl();
		return hostResource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MasterFiles createMasterFiles() {
		MasterFilesImpl masterFiles = new MasterFilesImpl();
		return masterFiles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessingUnitConfig createProcessingUnitConfig() {
		ProcessingUnitConfigImpl processingUnitConfig = new ProcessingUnitConfigImpl();
		return processingUnitConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessingUnitsConfig createProcessingUnitsConfig() {
		ProcessingUnitsConfigImpl processingUnitsConfig = new ProcessingUnitsConfigImpl();
		return processingUnitsConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Pstools createPstools() {
		PstoolsImpl pstools = new PstoolsImpl();
		return pstools;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RunVersion createRunVersion() {
		RunVersionImpl runVersion = new RunVersionImpl();
		return runVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Site createSite() {
		SiteImpl site = new SiteImpl();
		return site;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Software createSoftware() {
		SoftwareImpl software = new SoftwareImpl();
		return software;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Ssh createSsh() {
		SshImpl ssh = new SshImpl();
		return ssh;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StartPuMethod createStartPuMethod() {
		StartPuMethodImpl startPuMethod = new StartPuMethodImpl();
		return startPuMethod;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserCredentials createUserCredentials() {
		UserCredentialsImpl userCredentials = new UserCredentialsImpl();
		return userCredentials;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TopologyPackage getTopologyPackage() {
		return (TopologyPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TopologyPackage getPackage() {
		return TopologyPackage.eINSTANCE;
	}

} //TopologyFactoryImpl
