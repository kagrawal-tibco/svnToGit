/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.model.topology.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

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
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage
 * @generated
 */
public class TopologySwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static TopologyPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TopologySwitch() {
		if (modelPackage == null) {
			modelPackage = TopologyPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case TopologyPackage.BE_RUNTIME: {
				BeRuntime beRuntime = (BeRuntime)theEObject;
				T result = caseBeRuntime(beRuntime);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TopologyPackage.BE: {
				Be be = (Be)theEObject;
				T result = caseBe(be);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TopologyPackage.CLUSTERS: {
				Clusters clusters = (Clusters)theEObject;
				T result = caseClusters(clusters);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TopologyPackage.CLUSTER: {
				Cluster cluster = (Cluster)theEObject;
				T result = caseCluster(cluster);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TopologyPackage.DEPLOYED_FILES: {
				DeployedFiles deployedFiles = (DeployedFiles)theEObject;
				T result = caseDeployedFiles(deployedFiles);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TopologyPackage.DEPLOYMENT_MAPPINGS: {
				DeploymentMappings deploymentMappings = (DeploymentMappings)theEObject;
				T result = caseDeploymentMappings(deploymentMappings);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TopologyPackage.DEPLOYMENT_MAPPING: {
				DeploymentMapping deploymentMapping = (DeploymentMapping)theEObject;
				T result = caseDeploymentMapping(deploymentMapping);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TopologyPackage.DEPLOYMENT_UNITS: {
				DeploymentUnits deploymentUnits = (DeploymentUnits)theEObject;
				T result = caseDeploymentUnits(deploymentUnits);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TopologyPackage.DEPLOYMENT_UNIT: {
				DeploymentUnit deploymentUnit = (DeploymentUnit)theEObject;
				T result = caseDeploymentUnit(deploymentUnit);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TopologyPackage.DOCUMENT_ROOT: {
				DocumentRoot documentRoot = (DocumentRoot)theEObject;
				T result = caseDocumentRoot(documentRoot);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TopologyPackage.HAWK: {
				Hawk hawk = (Hawk)theEObject;
				T result = caseHawk(hawk);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TopologyPackage.HOST_RESOURCES: {
				HostResources hostResources = (HostResources)theEObject;
				T result = caseHostResources(hostResources);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TopologyPackage.HOST_RESOURCE: {
				HostResource hostResource = (HostResource)theEObject;
				T result = caseHostResource(hostResource);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TopologyPackage.MASTER_FILES: {
				MasterFiles masterFiles = (MasterFiles)theEObject;
				T result = caseMasterFiles(masterFiles);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TopologyPackage.PROCESSING_UNIT_CONFIG: {
				ProcessingUnitConfig processingUnitConfig = (ProcessingUnitConfig)theEObject;
				T result = caseProcessingUnitConfig(processingUnitConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TopologyPackage.PROCESSING_UNITS_CONFIG: {
				ProcessingUnitsConfig processingUnitsConfig = (ProcessingUnitsConfig)theEObject;
				T result = caseProcessingUnitsConfig(processingUnitsConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TopologyPackage.PSTOOLS: {
				Pstools pstools = (Pstools)theEObject;
				T result = casePstools(pstools);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TopologyPackage.RUN_VERSION: {
				RunVersion runVersion = (RunVersion)theEObject;
				T result = caseRunVersion(runVersion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TopologyPackage.SITE: {
				Site site = (Site)theEObject;
				T result = caseSite(site);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TopologyPackage.SOFTWARE: {
				Software software = (Software)theEObject;
				T result = caseSoftware(software);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TopologyPackage.SSH: {
				Ssh ssh = (Ssh)theEObject;
				T result = caseSsh(ssh);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TopologyPackage.START_PU_METHOD: {
				StartPuMethod startPuMethod = (StartPuMethod)theEObject;
				T result = caseStartPuMethod(startPuMethod);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TopologyPackage.USER_CREDENTIALS: {
				UserCredentials userCredentials = (UserCredentials)theEObject;
				T result = caseUserCredentials(userCredentials);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Be Runtime</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Be Runtime</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBeRuntime(BeRuntime object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Be</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Be</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBe(Be object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Clusters</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Clusters</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseClusters(Clusters object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Cluster</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cluster</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCluster(Cluster object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Deployed Files</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Deployed Files</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDeployedFiles(DeployedFiles object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Deployment Mappings</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Deployment Mappings</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDeploymentMappings(DeploymentMappings object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Deployment Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Deployment Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDeploymentMapping(DeploymentMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Deployment Units</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Deployment Units</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDeploymentUnits(DeploymentUnits object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Deployment Unit</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Deployment Unit</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDeploymentUnit(DeploymentUnit object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Document Root</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDocumentRoot(DocumentRoot object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Hawk</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Hawk</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHawk(Hawk object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Host Resources</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Host Resources</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHostResources(HostResources object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Host Resource</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Host Resource</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHostResource(HostResource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Master Files</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Master Files</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMasterFiles(MasterFiles object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Processing Unit Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Processing Unit Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProcessingUnitConfig(ProcessingUnitConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Processing Units Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Processing Units Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProcessingUnitsConfig(ProcessingUnitsConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pstools</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pstools</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePstools(Pstools object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Run Version</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Run Version</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRunVersion(RunVersion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Site</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Site</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSite(Site object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Software</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Software</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSoftware(Software object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ssh</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ssh</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSsh(Ssh object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Start Pu Method</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Start Pu Method</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStartPuMethod(StartPuMethod object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>User Credentials</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>User Credentials</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUserCredentials(UserCredentials object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //TopologySwitch
