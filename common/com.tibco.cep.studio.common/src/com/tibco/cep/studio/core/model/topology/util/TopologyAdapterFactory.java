/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.model.topology.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

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
import com.tibco.cep.studio.core.model.topology.TopologyPackage;
import com.tibco.cep.studio.core.model.topology.UserCredentials;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage
 * @generated
 */
public class TopologyAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static TopologyPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TopologyAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = TopologyPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TopologySwitch<Adapter> modelSwitch =
		new TopologySwitch<Adapter>() {
			@Override
			public Adapter caseBeRuntime(BeRuntime object) {
				return createBeRuntimeAdapter();
			}
			@Override
			public Adapter caseBe(Be object) {
				return createBeAdapter();
			}
			@Override
			public Adapter caseClusters(Clusters object) {
				return createClustersAdapter();
			}
			@Override
			public Adapter caseCluster(Cluster object) {
				return createClusterAdapter();
			}
			@Override
			public Adapter caseDeployedFiles(DeployedFiles object) {
				return createDeployedFilesAdapter();
			}
			@Override
			public Adapter caseDeploymentMappings(DeploymentMappings object) {
				return createDeploymentMappingsAdapter();
			}
			@Override
			public Adapter caseDeploymentMapping(DeploymentMapping object) {
				return createDeploymentMappingAdapter();
			}
			@Override
			public Adapter caseDeploymentUnits(DeploymentUnits object) {
				return createDeploymentUnitsAdapter();
			}
			@Override
			public Adapter caseDeploymentUnit(DeploymentUnit object) {
				return createDeploymentUnitAdapter();
			}
			@Override
			public Adapter caseDocumentRoot(DocumentRoot object) {
				return createDocumentRootAdapter();
			}
			@Override
			public Adapter caseHawk(Hawk object) {
				return createHawkAdapter();
			}
			@Override
			public Adapter caseHostResources(HostResources object) {
				return createHostResourcesAdapter();
			}
			@Override
			public Adapter caseHostResource(HostResource object) {
				return createHostResourceAdapter();
			}
			@Override
			public Adapter caseMasterFiles(MasterFiles object) {
				return createMasterFilesAdapter();
			}
			@Override
			public Adapter caseProcessingUnitConfig(ProcessingUnitConfig object) {
				return createProcessingUnitConfigAdapter();
			}
			@Override
			public Adapter caseProcessingUnitsConfig(ProcessingUnitsConfig object) {
				return createProcessingUnitsConfigAdapter();
			}
			@Override
			public Adapter casePstools(Pstools object) {
				return createPstoolsAdapter();
			}
			@Override
			public Adapter caseRunVersion(RunVersion object) {
				return createRunVersionAdapter();
			}
			@Override
			public Adapter caseSite(Site object) {
				return createSiteAdapter();
			}
			@Override
			public Adapter caseSoftware(Software object) {
				return createSoftwareAdapter();
			}
			@Override
			public Adapter caseSsh(Ssh object) {
				return createSshAdapter();
			}
			@Override
			public Adapter caseStartPuMethod(StartPuMethod object) {
				return createStartPuMethodAdapter();
			}
			@Override
			public Adapter caseUserCredentials(UserCredentials object) {
				return createUserCredentialsAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.model.topology.BeRuntime <em>Be Runtime</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.model.topology.BeRuntime
	 * @generated
	 */
	public Adapter createBeRuntimeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.model.topology.Be <em>Be</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.model.topology.Be
	 * @generated
	 */
	public Adapter createBeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.model.topology.Clusters <em>Clusters</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.model.topology.Clusters
	 * @generated
	 */
	public Adapter createClustersAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.model.topology.Cluster <em>Cluster</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.model.topology.Cluster
	 * @generated
	 */
	public Adapter createClusterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.model.topology.DeployedFiles <em>Deployed Files</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.model.topology.DeployedFiles
	 * @generated
	 */
	public Adapter createDeployedFilesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.model.topology.DeploymentMappings <em>Deployment Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.model.topology.DeploymentMappings
	 * @generated
	 */
	public Adapter createDeploymentMappingsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.model.topology.DeploymentMapping <em>Deployment Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.model.topology.DeploymentMapping
	 * @generated
	 */
	public Adapter createDeploymentMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.model.topology.DeploymentUnits <em>Deployment Units</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.model.topology.DeploymentUnits
	 * @generated
	 */
	public Adapter createDeploymentUnitsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.model.topology.DeploymentUnit <em>Deployment Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.model.topology.DeploymentUnit
	 * @generated
	 */
	public Adapter createDeploymentUnitAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.model.topology.DocumentRoot
	 * @generated
	 */
	public Adapter createDocumentRootAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.model.topology.Hawk <em>Hawk</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.model.topology.Hawk
	 * @generated
	 */
	public Adapter createHawkAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.model.topology.HostResources <em>Host Resources</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.model.topology.HostResources
	 * @generated
	 */
	public Adapter createHostResourcesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.model.topology.HostResource <em>Host Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.model.topology.HostResource
	 * @generated
	 */
	public Adapter createHostResourceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.model.topology.MasterFiles <em>Master Files</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.model.topology.MasterFiles
	 * @generated
	 */
	public Adapter createMasterFilesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.model.topology.ProcessingUnitConfig <em>Processing Unit Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.model.topology.ProcessingUnitConfig
	 * @generated
	 */
	public Adapter createProcessingUnitConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.model.topology.ProcessingUnitsConfig <em>Processing Units Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.model.topology.ProcessingUnitsConfig
	 * @generated
	 */
	public Adapter createProcessingUnitsConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.model.topology.Pstools <em>Pstools</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.model.topology.Pstools
	 * @generated
	 */
	public Adapter createPstoolsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.model.topology.RunVersion <em>Run Version</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.model.topology.RunVersion
	 * @generated
	 */
	public Adapter createRunVersionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.model.topology.Site <em>Site</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.model.topology.Site
	 * @generated
	 */
	public Adapter createSiteAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.model.topology.Software <em>Software</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.model.topology.Software
	 * @generated
	 */
	public Adapter createSoftwareAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.model.topology.Ssh <em>Ssh</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.model.topology.Ssh
	 * @generated
	 */
	public Adapter createSshAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.model.topology.StartPuMethod <em>Start Pu Method</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.model.topology.StartPuMethod
	 * @generated
	 */
	public Adapter createStartPuMethodAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.model.topology.UserCredentials <em>User Credentials</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.model.topology.UserCredentials
	 * @generated
	 */
	public Adapter createUserCredentialsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //TopologyAdapterFactory
