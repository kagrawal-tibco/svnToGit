/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.model.topology.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

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
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TopologyPackageImpl extends EPackageImpl implements TopologyPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass beRuntimeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass beEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass clustersEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass clusterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deployedFilesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deploymentMappingsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deploymentMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deploymentUnitsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deploymentUnitEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass documentRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass hawkEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass hostResourcesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass hostResourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass masterFilesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass processingUnitConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass processingUnitsConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pstoolsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass runVersionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass siteEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass softwareEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sshEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass startPuMethodEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass userCredentialsEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TopologyPackageImpl() {
		super(eNS_URI, TopologyFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link TopologyPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TopologyPackage init() {
		if (isInited) return (TopologyPackage)EPackage.Registry.INSTANCE.getEPackage(TopologyPackage.eNS_URI);

		// Obtain or create and register package
		TopologyPackageImpl theTopologyPackage = (TopologyPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof TopologyPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new TopologyPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theTopologyPackage.createPackageContents();

		// Initialize created meta-data
		theTopologyPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTopologyPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TopologyPackage.eNS_URI, theTopologyPackage);
		return theTopologyPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBeRuntime() {
		return beRuntimeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBeRuntime_Version() {
		return (EAttribute)beRuntimeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBe() {
		return beEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBe_Tra() {
		return (EAttribute)beEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBe_Home() {
		return (EAttribute)beEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBe_Version() {
		return (EAttribute)beEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getClusters() {
		return clustersEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClusters_Cluster() {
		return (EReference)clustersEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCluster() {
		return clusterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCluster_MasterFiles() {
		return (EReference)clusterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCluster_RunVersion() {
		return (EReference)clusterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCluster_DeploymentUnits() {
		return (EReference)clusterEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCluster_DeploymentMappings() {
		return (EReference)clusterEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCluster_Name() {
		return (EAttribute)clusterEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCluster_ProjectCdd() {
		return (EAttribute)clusterEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeployedFiles() {
		return deployedFilesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDeployedFiles_CddDeployed() {
		return (EAttribute)deployedFilesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDeployedFiles_EarDeployed() {
		return (EAttribute)deployedFilesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeploymentMappings() {
		return deploymentMappingsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDeploymentMappings_DeploymentMapping() {
		return (EReference)deploymentMappingsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeploymentMapping() {
		return deploymentMappingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDeploymentMapping_DeploymentUnitRef() {
		return (EAttribute)deploymentMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDeploymentMapping_HostRef() {
		return (EAttribute)deploymentMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeploymentUnits() {
		return deploymentUnitsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDeploymentUnits_DeploymentUnit() {
		return (EReference)deploymentUnitsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeploymentUnit() {
		return deploymentUnitEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDeploymentUnit_DeployedFiles() {
		return (EReference)deploymentUnitEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDeploymentUnit_ProcessingUnitsConfig() {
		return (EReference)deploymentUnitEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDeploymentUnit_Id() {
		return (EAttribute)deploymentUnitEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDeploymentUnit_Name() {
		return (EAttribute)deploymentUnitEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDocumentRoot() {
		return documentRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_Mixed() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_XMLNSPrefixMap() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_XSISchemaLocation() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Be() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_BeRuntime() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_CddDeployed() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_CddMaster() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Cluster() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Clusters() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_DeployedFiles() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_DeploymentMapping() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_DeploymentMappings() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_DeploymentUnit() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_DeploymentUnits() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_EarDeployed() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_EarMaster() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Hawk() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_Home() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_Hostname() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_HostResource() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_HostResources() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_Ip() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_MasterFiles() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(22);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_OsType() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(23);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_ProcessingUnitConfig() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(24);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_ProcessingUnitsConfig() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(25);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Pstools() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(26);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_RunVersion() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(27);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Site() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(28);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Software() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(29);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Ssh() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(30);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_StartPuMethod() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(31);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_Tra() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(32);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_UserCredentials() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(33);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHawk() {
		return hawkEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHostResources() {
		return hostResourcesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHostResources_HostResource() {
		return (EReference)hostResourcesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHostResource() {
		return hostResourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHostResource_Hostname() {
		return (EAttribute)hostResourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHostResource_Ip() {
		return (EAttribute)hostResourceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHostResource_UserCredentials() {
		return (EReference)hostResourceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHostResource_OsType() {
		return (EAttribute)hostResourceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHostResource_Software() {
		return (EReference)hostResourceEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHostResource_StartPuMethod() {
		return (EReference)hostResourceEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHostResource_Id() {
		return (EAttribute)hostResourceEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMasterFiles() {
		return masterFilesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMasterFiles_CddMaster() {
		return (EAttribute)masterFilesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMasterFiles_EarMaster() {
		return (EAttribute)masterFilesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProcessingUnitConfig() {
		return processingUnitConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProcessingUnitConfig_Id() {
		return (EAttribute)processingUnitConfigEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProcessingUnitConfig_Jmxport() {
		return (EAttribute)processingUnitConfigEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProcessingUnitConfig_Puid() {
		return (EAttribute)processingUnitConfigEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProcessingUnitConfig_UseAsEngineName() {
		return (EAttribute)processingUnitConfigEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProcessingUnitsConfig() {
		return processingUnitsConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessingUnitsConfig_ProcessingUnitConfig() {
		return (EReference)processingUnitsConfigEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPstools() {
		return pstoolsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRunVersion() {
		return runVersionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRunVersion_BeRuntime() {
		return (EReference)runVersionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSite() {
		return siteEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSite_Description() {
		return (EAttribute)siteEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSite_Clusters() {
		return (EReference)siteEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSite_HostResources() {
		return (EReference)siteEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSite_Name() {
		return (EAttribute)siteEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSoftware() {
		return softwareEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSoftware_Be() {
		return (EReference)softwareEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSsh() {
		return sshEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSsh_Port() {
		return (EAttribute)sshEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStartPuMethod() {
		return startPuMethodEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStartPuMethod_Hawk() {
		return (EReference)startPuMethodEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStartPuMethod_Ssh() {
		return (EReference)startPuMethodEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStartPuMethod_Pstools() {
		return (EReference)startPuMethodEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUserCredentials() {
		return userCredentialsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUserCredentials_Password() {
		return (EAttribute)userCredentialsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUserCredentials_Username() {
		return (EAttribute)userCredentialsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TopologyFactory getTopologyFactory() {
		return (TopologyFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		beRuntimeEClass = createEClass(BE_RUNTIME);
		createEAttribute(beRuntimeEClass, BE_RUNTIME__VERSION);

		beEClass = createEClass(BE);
		createEAttribute(beEClass, BE__TRA);
		createEAttribute(beEClass, BE__HOME);
		createEAttribute(beEClass, BE__VERSION);

		clustersEClass = createEClass(CLUSTERS);
		createEReference(clustersEClass, CLUSTERS__CLUSTER);

		clusterEClass = createEClass(CLUSTER);
		createEReference(clusterEClass, CLUSTER__MASTER_FILES);
		createEReference(clusterEClass, CLUSTER__RUN_VERSION);
		createEReference(clusterEClass, CLUSTER__DEPLOYMENT_UNITS);
		createEReference(clusterEClass, CLUSTER__DEPLOYMENT_MAPPINGS);
		createEAttribute(clusterEClass, CLUSTER__NAME);
		createEAttribute(clusterEClass, CLUSTER__PROJECT_CDD);

		deployedFilesEClass = createEClass(DEPLOYED_FILES);
		createEAttribute(deployedFilesEClass, DEPLOYED_FILES__CDD_DEPLOYED);
		createEAttribute(deployedFilesEClass, DEPLOYED_FILES__EAR_DEPLOYED);

		deploymentMappingsEClass = createEClass(DEPLOYMENT_MAPPINGS);
		createEReference(deploymentMappingsEClass, DEPLOYMENT_MAPPINGS__DEPLOYMENT_MAPPING);

		deploymentMappingEClass = createEClass(DEPLOYMENT_MAPPING);
		createEAttribute(deploymentMappingEClass, DEPLOYMENT_MAPPING__DEPLOYMENT_UNIT_REF);
		createEAttribute(deploymentMappingEClass, DEPLOYMENT_MAPPING__HOST_REF);

		deploymentUnitsEClass = createEClass(DEPLOYMENT_UNITS);
		createEReference(deploymentUnitsEClass, DEPLOYMENT_UNITS__DEPLOYMENT_UNIT);

		deploymentUnitEClass = createEClass(DEPLOYMENT_UNIT);
		createEReference(deploymentUnitEClass, DEPLOYMENT_UNIT__DEPLOYED_FILES);
		createEReference(deploymentUnitEClass, DEPLOYMENT_UNIT__PROCESSING_UNITS_CONFIG);
		createEAttribute(deploymentUnitEClass, DEPLOYMENT_UNIT__ID);
		createEAttribute(deploymentUnitEClass, DEPLOYMENT_UNIT__NAME);

		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__BE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__BE_RUNTIME);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__CDD_DEPLOYED);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__CDD_MASTER);
		createEReference(documentRootEClass, DOCUMENT_ROOT__CLUSTER);
		createEReference(documentRootEClass, DOCUMENT_ROOT__CLUSTERS);
		createEReference(documentRootEClass, DOCUMENT_ROOT__DEPLOYED_FILES);
		createEReference(documentRootEClass, DOCUMENT_ROOT__DEPLOYMENT_MAPPING);
		createEReference(documentRootEClass, DOCUMENT_ROOT__DEPLOYMENT_MAPPINGS);
		createEReference(documentRootEClass, DOCUMENT_ROOT__DEPLOYMENT_UNIT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__DEPLOYMENT_UNITS);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__EAR_DEPLOYED);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__EAR_MASTER);
		createEReference(documentRootEClass, DOCUMENT_ROOT__HAWK);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__HOME);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__HOSTNAME);
		createEReference(documentRootEClass, DOCUMENT_ROOT__HOST_RESOURCE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__HOST_RESOURCES);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__IP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MASTER_FILES);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__OS_TYPE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__PROCESSING_UNIT_CONFIG);
		createEReference(documentRootEClass, DOCUMENT_ROOT__PROCESSING_UNITS_CONFIG);
		createEReference(documentRootEClass, DOCUMENT_ROOT__PSTOOLS);
		createEReference(documentRootEClass, DOCUMENT_ROOT__RUN_VERSION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__SITE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__SOFTWARE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__SSH);
		createEReference(documentRootEClass, DOCUMENT_ROOT__START_PU_METHOD);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__TRA);
		createEReference(documentRootEClass, DOCUMENT_ROOT__USER_CREDENTIALS);

		hawkEClass = createEClass(HAWK);

		hostResourcesEClass = createEClass(HOST_RESOURCES);
		createEReference(hostResourcesEClass, HOST_RESOURCES__HOST_RESOURCE);

		hostResourceEClass = createEClass(HOST_RESOURCE);
		createEAttribute(hostResourceEClass, HOST_RESOURCE__HOSTNAME);
		createEAttribute(hostResourceEClass, HOST_RESOURCE__IP);
		createEReference(hostResourceEClass, HOST_RESOURCE__USER_CREDENTIALS);
		createEAttribute(hostResourceEClass, HOST_RESOURCE__OS_TYPE);
		createEReference(hostResourceEClass, HOST_RESOURCE__SOFTWARE);
		createEReference(hostResourceEClass, HOST_RESOURCE__START_PU_METHOD);
		createEAttribute(hostResourceEClass, HOST_RESOURCE__ID);

		masterFilesEClass = createEClass(MASTER_FILES);
		createEAttribute(masterFilesEClass, MASTER_FILES__CDD_MASTER);
		createEAttribute(masterFilesEClass, MASTER_FILES__EAR_MASTER);

		processingUnitConfigEClass = createEClass(PROCESSING_UNIT_CONFIG);
		createEAttribute(processingUnitConfigEClass, PROCESSING_UNIT_CONFIG__ID);
		createEAttribute(processingUnitConfigEClass, PROCESSING_UNIT_CONFIG__JMXPORT);
		createEAttribute(processingUnitConfigEClass, PROCESSING_UNIT_CONFIG__PUID);
		createEAttribute(processingUnitConfigEClass, PROCESSING_UNIT_CONFIG__USE_AS_ENGINE_NAME);

		processingUnitsConfigEClass = createEClass(PROCESSING_UNITS_CONFIG);
		createEReference(processingUnitsConfigEClass, PROCESSING_UNITS_CONFIG__PROCESSING_UNIT_CONFIG);

		pstoolsEClass = createEClass(PSTOOLS);

		runVersionEClass = createEClass(RUN_VERSION);
		createEReference(runVersionEClass, RUN_VERSION__BE_RUNTIME);

		siteEClass = createEClass(SITE);
		createEAttribute(siteEClass, SITE__DESCRIPTION);
		createEReference(siteEClass, SITE__CLUSTERS);
		createEReference(siteEClass, SITE__HOST_RESOURCES);
		createEAttribute(siteEClass, SITE__NAME);

		softwareEClass = createEClass(SOFTWARE);
		createEReference(softwareEClass, SOFTWARE__BE);

		sshEClass = createEClass(SSH);
		createEAttribute(sshEClass, SSH__PORT);

		startPuMethodEClass = createEClass(START_PU_METHOD);
		createEReference(startPuMethodEClass, START_PU_METHOD__HAWK);
		createEReference(startPuMethodEClass, START_PU_METHOD__SSH);
		createEReference(startPuMethodEClass, START_PU_METHOD__PSTOOLS);

		userCredentialsEClass = createEClass(USER_CREDENTIALS);
		createEAttribute(userCredentialsEClass, USER_CREDENTIALS__PASSWORD);
		createEAttribute(userCredentialsEClass, USER_CREDENTIALS__USERNAME);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(beRuntimeEClass, BeRuntime.class, "BeRuntime", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBeRuntime_Version(), theXMLTypePackage.getString(), "version", "4.0.0", 0, 1, BeRuntime.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(beEClass, Be.class, "Be", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBe_Tra(), theXMLTypePackage.getString(), "tra", null, 0, 1, Be.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBe_Home(), theXMLTypePackage.getString(), "home", null, 1, 1, Be.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBe_Version(), theXMLTypePackage.getString(), "version", "4.0.0", 0, 1, Be.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(clustersEClass, Clusters.class, "Clusters", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getClusters_Cluster(), this.getCluster(), null, "cluster", null, 1, -1, Clusters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(clusterEClass, Cluster.class, "Cluster", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCluster_MasterFiles(), this.getMasterFiles(), null, "masterFiles", null, 1, 1, Cluster.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCluster_RunVersion(), this.getRunVersion(), null, "runVersion", null, 1, 1, Cluster.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCluster_DeploymentUnits(), this.getDeploymentUnits(), null, "deploymentUnits", null, 0, 1, Cluster.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCluster_DeploymentMappings(), this.getDeploymentMappings(), null, "deploymentMappings", null, 0, 1, Cluster.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCluster_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, Cluster.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCluster_ProjectCdd(), theXMLTypePackage.getString(), "projectCdd", null, 0, 1, Cluster.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(deployedFilesEClass, DeployedFiles.class, "DeployedFiles", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDeployedFiles_CddDeployed(), theXMLTypePackage.getString(), "cddDeployed", null, 1, 1, DeployedFiles.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDeployedFiles_EarDeployed(), theXMLTypePackage.getString(), "earDeployed", null, 1, 1, DeployedFiles.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(deploymentMappingsEClass, DeploymentMappings.class, "DeploymentMappings", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDeploymentMappings_DeploymentMapping(), this.getDeploymentMapping(), null, "deploymentMapping", null, 1, -1, DeploymentMappings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(deploymentMappingEClass, DeploymentMapping.class, "DeploymentMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDeploymentMapping_DeploymentUnitRef(), theXMLTypePackage.getIDREF(), "deploymentUnitRef", null, 0, 1, DeploymentMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDeploymentMapping_HostRef(), theXMLTypePackage.getIDREF(), "hostRef", null, 0, 1, DeploymentMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(deploymentUnitsEClass, DeploymentUnits.class, "DeploymentUnits", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDeploymentUnits_DeploymentUnit(), this.getDeploymentUnit(), null, "deploymentUnit", null, 1, -1, DeploymentUnits.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(deploymentUnitEClass, DeploymentUnit.class, "DeploymentUnit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDeploymentUnit_DeployedFiles(), this.getDeployedFiles(), null, "deployedFiles", null, 1, 1, DeploymentUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDeploymentUnit_ProcessingUnitsConfig(), this.getProcessingUnitsConfig(), null, "processingUnitsConfig", null, 1, 1, DeploymentUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDeploymentUnit_Id(), theXMLTypePackage.getID(), "id", null, 0, 1, DeploymentUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDeploymentUnit_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, DeploymentUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Be(), this.getBe(), null, "be", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_BeRuntime(), this.getBeRuntime(), null, "beRuntime", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_CddDeployed(), theXMLTypePackage.getString(), "cddDeployed", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_CddMaster(), theXMLTypePackage.getString(), "cddMaster", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Cluster(), this.getCluster(), null, "cluster", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Clusters(), this.getClusters(), null, "clusters", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_DeployedFiles(), this.getDeployedFiles(), null, "deployedFiles", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_DeploymentMapping(), this.getDeploymentMapping(), null, "deploymentMapping", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_DeploymentMappings(), this.getDeploymentMappings(), null, "deploymentMappings", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_DeploymentUnit(), this.getDeploymentUnit(), null, "deploymentUnit", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_DeploymentUnits(), this.getDeploymentUnits(), null, "deploymentUnits", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_EarDeployed(), theXMLTypePackage.getString(), "earDeployed", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_EarMaster(), theXMLTypePackage.getString(), "earMaster", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Hawk(), this.getHawk(), null, "hawk", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_Home(), theXMLTypePackage.getString(), "home", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_Hostname(), theXMLTypePackage.getString(), "hostname", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_HostResource(), this.getHostResource(), null, "hostResource", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_HostResources(), this.getHostResources(), null, "hostResources", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_Ip(), theXMLTypePackage.getString(), "ip", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MasterFiles(), this.getMasterFiles(), null, "masterFiles", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_OsType(), theXMLTypePackage.getString(), "osType", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ProcessingUnitConfig(), this.getProcessingUnitConfig(), null, "processingUnitConfig", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ProcessingUnitsConfig(), this.getProcessingUnitsConfig(), null, "processingUnitsConfig", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Pstools(), this.getPstools(), null, "pstools", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_RunVersion(), this.getRunVersion(), null, "runVersion", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Site(), this.getSite(), null, "site", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Software(), this.getSoftware(), null, "software", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Ssh(), this.getSsh(), null, "ssh", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_StartPuMethod(), this.getStartPuMethod(), null, "startPuMethod", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_Tra(), theXMLTypePackage.getString(), "tra", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_UserCredentials(), this.getUserCredentials(), null, "userCredentials", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(hawkEClass, Hawk.class, "Hawk", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(hostResourcesEClass, HostResources.class, "HostResources", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getHostResources_HostResource(), this.getHostResource(), null, "hostResource", null, 1, -1, HostResources.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(hostResourceEClass, HostResource.class, "HostResource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getHostResource_Hostname(), theXMLTypePackage.getString(), "hostname", null, 1, 1, HostResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHostResource_Ip(), theXMLTypePackage.getString(), "ip", null, 1, 1, HostResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHostResource_UserCredentials(), this.getUserCredentials(), null, "userCredentials", null, 0, 1, HostResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHostResource_OsType(), theXMLTypePackage.getString(), "osType", null, 1, 1, HostResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHostResource_Software(), this.getSoftware(), null, "software", null, 1, 1, HostResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHostResource_StartPuMethod(), this.getStartPuMethod(), null, "startPuMethod", null, 1, 1, HostResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHostResource_Id(), theXMLTypePackage.getID(), "id", null, 0, 1, HostResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(masterFilesEClass, MasterFiles.class, "MasterFiles", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMasterFiles_CddMaster(), theXMLTypePackage.getString(), "cddMaster", null, 1, 1, MasterFiles.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMasterFiles_EarMaster(), theXMLTypePackage.getString(), "earMaster", null, 0, 1, MasterFiles.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(processingUnitConfigEClass, ProcessingUnitConfig.class, "ProcessingUnitConfig", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProcessingUnitConfig_Id(), theXMLTypePackage.getID(), "id", null, 0, 1, ProcessingUnitConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProcessingUnitConfig_Jmxport(), theXMLTypePackage.getString(), "jmxport", null, 0, 1, ProcessingUnitConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProcessingUnitConfig_Puid(), theXMLTypePackage.getString(), "puid", null, 0, 1, ProcessingUnitConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProcessingUnitConfig_UseAsEngineName(), theXMLTypePackage.getBoolean(), "useAsEngineName", "false", 0, 1, ProcessingUnitConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(processingUnitsConfigEClass, ProcessingUnitsConfig.class, "ProcessingUnitsConfig", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProcessingUnitsConfig_ProcessingUnitConfig(), this.getProcessingUnitConfig(), null, "processingUnitConfig", null, 1, -1, ProcessingUnitsConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(pstoolsEClass, Pstools.class, "Pstools", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(runVersionEClass, RunVersion.class, "RunVersion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRunVersion_BeRuntime(), this.getBeRuntime(), null, "beRuntime", null, 1, 1, RunVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(siteEClass, Site.class, "Site", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSite_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, Site.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSite_Clusters(), this.getClusters(), null, "clusters", null, 1, 1, Site.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSite_HostResources(), this.getHostResources(), null, "hostResources", null, 0, 1, Site.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSite_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, Site.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(softwareEClass, Software.class, "Software", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSoftware_Be(), this.getBe(), null, "be", null, 1, -1, Software.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sshEClass, Ssh.class, "Ssh", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSsh_Port(), theXMLTypePackage.getString(), "port", null, 0, 1, Ssh.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(startPuMethodEClass, StartPuMethod.class, "StartPuMethod", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStartPuMethod_Hawk(), this.getHawk(), null, "hawk", null, 0, 1, StartPuMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStartPuMethod_Ssh(), this.getSsh(), null, "ssh", null, 0, 1, StartPuMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStartPuMethod_Pstools(), this.getPstools(), null, "pstools", null, 0, 1, StartPuMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(userCredentialsEClass, UserCredentials.class, "UserCredentials", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUserCredentials_Password(), theXMLTypePackage.getString(), "password", null, 0, 1, UserCredentials.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUserCredentials_Username(), theXMLTypePackage.getString(), "username", null, 0, 1, UserCredentials.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
		// null
		createNullAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";		
		addAnnotation
		  (beRuntimeEClass, 
		   source, 
		   new String[] {
			 "name", "be-runtime_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getBeRuntime_Version(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "version"
		   });		
		addAnnotation
		  (beEClass, 
		   source, 
		   new String[] {
			 "name", "be_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getBe_Tra(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "tra",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getBe_Home(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "home",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getBe_Version(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "version"
		   });		
		addAnnotation
		  (clustersEClass, 
		   source, 
		   new String[] {
			 "name", "clusters_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getClusters_Cluster(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "cluster",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (clusterEClass, 
		   source, 
		   new String[] {
			 "name", "cluster_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getCluster_MasterFiles(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "master-files",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getCluster_RunVersion(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "run-version",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getCluster_DeploymentUnits(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "deployment-units",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getCluster_DeploymentMappings(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "deployment-mappings",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getCluster_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name"
		   });		
		addAnnotation
		  (getCluster_ProjectCdd(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "project-cdd"
		   });		
		addAnnotation
		  (deployedFilesEClass, 
		   source, 
		   new String[] {
			 "name", "deployed-files_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getDeployedFiles_CddDeployed(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "cdd-deployed",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDeployedFiles_EarDeployed(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ear-deployed",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (deploymentMappingsEClass, 
		   source, 
		   new String[] {
			 "name", "deployment-mappings_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getDeploymentMappings_DeploymentMapping(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "deployment-mapping",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (deploymentMappingEClass, 
		   source, 
		   new String[] {
			 "name", "deployment-mapping_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getDeploymentMapping_DeploymentUnitRef(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "deployment-unit-ref"
		   });		
		addAnnotation
		  (getDeploymentMapping_HostRef(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "host-ref"
		   });		
		addAnnotation
		  (deploymentUnitsEClass, 
		   source, 
		   new String[] {
			 "name", "deployment-units_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getDeploymentUnits_DeploymentUnit(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "deployment-unit",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (deploymentUnitEClass, 
		   source, 
		   new String[] {
			 "name", "deployment-unit_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getDeploymentUnit_DeployedFiles(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "deployed-files",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDeploymentUnit_ProcessingUnitsConfig(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "processing-units-config",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDeploymentUnit_Id(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "id"
		   });		
		addAnnotation
		  (getDeploymentUnit_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name"
		   });		
		addAnnotation
		  (documentRootEClass, 
		   source, 
		   new String[] {
			 "name", "",
			 "kind", "mixed"
		   });		
		addAnnotation
		  (getDocumentRoot_Mixed(), 
		   source, 
		   new String[] {
			 "kind", "elementWildcard",
			 "name", ":mixed"
		   });		
		addAnnotation
		  (getDocumentRoot_XMLNSPrefixMap(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xmlns:prefix"
		   });		
		addAnnotation
		  (getDocumentRoot_XSISchemaLocation(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xsi:schemaLocation"
		   });		
		addAnnotation
		  (getDocumentRoot_Be(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "be",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_BeRuntime(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "be-runtime",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_CddDeployed(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "cdd-deployed",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_CddMaster(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "cdd-master",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Cluster(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "cluster",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Clusters(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "clusters",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_DeployedFiles(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "deployed-files",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_DeploymentMapping(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "deployment-mapping",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_DeploymentMappings(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "deployment-mappings",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_DeploymentUnit(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "deployment-unit",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_DeploymentUnits(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "deployment-units",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_EarDeployed(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ear-deployed",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_EarMaster(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ear-master",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Hawk(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "hawk",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Home(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "home",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Hostname(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "hostname",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_HostResource(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "host-resource",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_HostResources(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "host-resources",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Ip(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ip",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_MasterFiles(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "master-files",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_OsType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "os-type",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_ProcessingUnitConfig(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "processing-unit-config",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_ProcessingUnitsConfig(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "processing-units-config",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Pstools(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "pstools",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_RunVersion(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "run-version",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Site(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "site",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Software(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "software",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Ssh(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ssh",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_StartPuMethod(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "start-pu-method",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Tra(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "tra",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_UserCredentials(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "user-credentials",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (hawkEClass, 
		   source, 
		   new String[] {
			 "name", "hawk_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (hostResourcesEClass, 
		   source, 
		   new String[] {
			 "name", "host-resources_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getHostResources_HostResource(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "host-resource",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (hostResourceEClass, 
		   source, 
		   new String[] {
			 "name", "host-resource_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getHostResource_Hostname(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "hostname",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getHostResource_Ip(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ip",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getHostResource_UserCredentials(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "user-credentials",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getHostResource_OsType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "os-type",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getHostResource_Software(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "software",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getHostResource_StartPuMethod(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "start-pu-method",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getHostResource_Id(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "id"
		   });		
		addAnnotation
		  (masterFilesEClass, 
		   source, 
		   new String[] {
			 "name", "master-files_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getMasterFiles_CddMaster(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "cdd-master",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getMasterFiles_EarMaster(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ear-master",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (processingUnitConfigEClass, 
		   source, 
		   new String[] {
			 "name", "processing-unit-config_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getProcessingUnitConfig_Id(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "id"
		   });		
		addAnnotation
		  (getProcessingUnitConfig_Jmxport(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "jmxport"
		   });		
		addAnnotation
		  (getProcessingUnitConfig_Puid(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "puid"
		   });		
		addAnnotation
		  (getProcessingUnitConfig_UseAsEngineName(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "use-as-engine-name"
		   });		
		addAnnotation
		  (processingUnitsConfigEClass, 
		   source, 
		   new String[] {
			 "name", "processing-units-config_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getProcessingUnitsConfig_ProcessingUnitConfig(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "processing-unit-config",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (pstoolsEClass, 
		   source, 
		   new String[] {
			 "name", "pstools_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (runVersionEClass, 
		   source, 
		   new String[] {
			 "name", "run-version_._type",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getRunVersion_BeRuntime(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "be-runtime",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (siteEClass, 
		   source, 
		   new String[] {
			 "name", "site",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getSite_Description(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "description",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSite_Clusters(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "clusters",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSite_HostResources(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "host-resources",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSite_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name"
		   });		
		addAnnotation
		  (softwareEClass, 
		   source, 
		   new String[] {
			 "name", "software_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getSoftware_Be(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "be",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (sshEClass, 
		   source, 
		   new String[] {
			 "name", "ssh_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getSsh_Port(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "port"
		   });		
		addAnnotation
		  (startPuMethodEClass, 
		   source, 
		   new String[] {
			 "name", "start-pu-method_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getStartPuMethod_Hawk(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "hawk",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getStartPuMethod_Ssh(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ssh",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getStartPuMethod_Pstools(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "pstools",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (userCredentialsEClass, 
		   source, 
		   new String[] {
			 "name", "user-credentials_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getUserCredentials_Password(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "password"
		   });		
		addAnnotation
		  (getUserCredentials_Username(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "username"
		   });
	}

	/**
	 * Initializes the annotations for <b>null</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createNullAnnotations() {
		String source = null;																																																																																										
		addAnnotation
		  (runVersionEClass, 
		   source, 
		   new String[] {
		   });																	
	}

} //TopologyPackageImpl
