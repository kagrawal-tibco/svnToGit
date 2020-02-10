/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.model.topology.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

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
 * An implementation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getBe <em>Be</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getBeRuntime <em>Be Runtime</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getCddDeployed <em>Cdd Deployed</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getCddMaster <em>Cdd Master</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getCluster <em>Cluster</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getClusters <em>Clusters</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getDeployedFiles <em>Deployed Files</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getDeploymentMapping <em>Deployment Mapping</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getDeploymentMappings <em>Deployment Mappings</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getDeploymentUnit <em>Deployment Unit</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getDeploymentUnits <em>Deployment Units</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getEarDeployed <em>Ear Deployed</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getEarMaster <em>Ear Master</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getHawk <em>Hawk</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getHome <em>Home</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getHostname <em>Hostname</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getHostResource <em>Host Resource</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getHostResources <em>Host Resources</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getIp <em>Ip</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getMasterFiles <em>Master Files</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getOsType <em>Os Type</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getProcessingUnitConfig <em>Processing Unit Config</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getProcessingUnitsConfig <em>Processing Units Config</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getPstools <em>Pstools</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getRunVersion <em>Run Version</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getSite <em>Site</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getSoftware <em>Software</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getSsh <em>Ssh</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getStartPuMethod <em>Start Pu Method</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getTra <em>Tra</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DocumentRootImpl#getUserCredentials <em>User Credentials</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DocumentRootImpl extends EObjectImpl implements DocumentRoot {
	/**
	 * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMixed()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap mixed;

	/**
	 * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXMLNSPrefixMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xMLNSPrefixMap;

	/**
	 * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXSISchemaLocation()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xSISchemaLocation;

	/**
	 * The default value of the '{@link #getCddDeployed() <em>Cdd Deployed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCddDeployed()
	 * @generated
	 * @ordered
	 */
	protected static final String CDD_DEPLOYED_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getCddMaster() <em>Cdd Master</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCddMaster()
	 * @generated
	 * @ordered
	 */
	protected static final String CDD_MASTER_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getEarDeployed() <em>Ear Deployed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEarDeployed()
	 * @generated
	 * @ordered
	 */
	protected static final String EAR_DEPLOYED_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getEarMaster() <em>Ear Master</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEarMaster()
	 * @generated
	 * @ordered
	 */
	protected static final String EAR_MASTER_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getHome() <em>Home</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHome()
	 * @generated
	 * @ordered
	 */
	protected static final String HOME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getHostname() <em>Hostname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHostname()
	 * @generated
	 * @ordered
	 */
	protected static final String HOSTNAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getIp() <em>Ip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIp()
	 * @generated
	 * @ordered
	 */
	protected static final String IP_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getOsType() <em>Os Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOsType()
	 * @generated
	 * @ordered
	 */
	protected static final String OS_TYPE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getTra() <em>Tra</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTra()
	 * @generated
	 * @ordered
	 */
	protected static final String TRA_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DocumentRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TopologyPackage.Literals.DOCUMENT_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, TopologyPackage.DOCUMENT_ROOT__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getXMLNSPrefixMap() {
		if (xMLNSPrefixMap == null) {
			xMLNSPrefixMap = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, TopologyPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		}
		return xMLNSPrefixMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getXSISchemaLocation() {
		if (xSISchemaLocation == null) {
			xSISchemaLocation = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, TopologyPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		}
		return xSISchemaLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Be getBe() {
		return (Be)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__BE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBe(Be newBe, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(TopologyPackage.Literals.DOCUMENT_ROOT__BE, newBe, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBe(Be newBe) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__BE, newBe);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BeRuntime getBeRuntime() {
		return (BeRuntime)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__BE_RUNTIME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBeRuntime(BeRuntime newBeRuntime, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(TopologyPackage.Literals.DOCUMENT_ROOT__BE_RUNTIME, newBeRuntime, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBeRuntime(BeRuntime newBeRuntime) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__BE_RUNTIME, newBeRuntime);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCddDeployed() {
		return (String)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__CDD_DEPLOYED, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCddDeployed(String newCddDeployed) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__CDD_DEPLOYED, newCddDeployed);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCddMaster() {
		return (String)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__CDD_MASTER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCddMaster(String newCddMaster) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__CDD_MASTER, newCddMaster);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Cluster getCluster() {
		return (Cluster)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__CLUSTER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCluster(Cluster newCluster, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(TopologyPackage.Literals.DOCUMENT_ROOT__CLUSTER, newCluster, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCluster(Cluster newCluster) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__CLUSTER, newCluster);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Clusters getClusters() {
		return (Clusters)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__CLUSTERS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetClusters(Clusters newClusters, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(TopologyPackage.Literals.DOCUMENT_ROOT__CLUSTERS, newClusters, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClusters(Clusters newClusters) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__CLUSTERS, newClusters);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeployedFiles getDeployedFiles() {
		return (DeployedFiles)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__DEPLOYED_FILES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeployedFiles(DeployedFiles newDeployedFiles, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(TopologyPackage.Literals.DOCUMENT_ROOT__DEPLOYED_FILES, newDeployedFiles, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeployedFiles(DeployedFiles newDeployedFiles) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__DEPLOYED_FILES, newDeployedFiles);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeploymentMapping getDeploymentMapping() {
		return (DeploymentMapping)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__DEPLOYMENT_MAPPING, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeploymentMapping(DeploymentMapping newDeploymentMapping, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(TopologyPackage.Literals.DOCUMENT_ROOT__DEPLOYMENT_MAPPING, newDeploymentMapping, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeploymentMapping(DeploymentMapping newDeploymentMapping) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__DEPLOYMENT_MAPPING, newDeploymentMapping);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeploymentMappings getDeploymentMappings() {
		return (DeploymentMappings)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__DEPLOYMENT_MAPPINGS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeploymentMappings(DeploymentMappings newDeploymentMappings, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(TopologyPackage.Literals.DOCUMENT_ROOT__DEPLOYMENT_MAPPINGS, newDeploymentMappings, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeploymentMappings(DeploymentMappings newDeploymentMappings) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__DEPLOYMENT_MAPPINGS, newDeploymentMappings);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeploymentUnit getDeploymentUnit() {
		return (DeploymentUnit)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__DEPLOYMENT_UNIT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeploymentUnit(DeploymentUnit newDeploymentUnit, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(TopologyPackage.Literals.DOCUMENT_ROOT__DEPLOYMENT_UNIT, newDeploymentUnit, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeploymentUnit(DeploymentUnit newDeploymentUnit) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__DEPLOYMENT_UNIT, newDeploymentUnit);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeploymentUnits getDeploymentUnits() {
		return (DeploymentUnits)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__DEPLOYMENT_UNITS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeploymentUnits(DeploymentUnits newDeploymentUnits, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(TopologyPackage.Literals.DOCUMENT_ROOT__DEPLOYMENT_UNITS, newDeploymentUnits, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeploymentUnits(DeploymentUnits newDeploymentUnits) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__DEPLOYMENT_UNITS, newDeploymentUnits);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEarDeployed() {
		return (String)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__EAR_DEPLOYED, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEarDeployed(String newEarDeployed) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__EAR_DEPLOYED, newEarDeployed);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEarMaster() {
		return (String)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__EAR_MASTER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEarMaster(String newEarMaster) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__EAR_MASTER, newEarMaster);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Hawk getHawk() {
		return (Hawk)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__HAWK, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHawk(Hawk newHawk, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(TopologyPackage.Literals.DOCUMENT_ROOT__HAWK, newHawk, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHawk(Hawk newHawk) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__HAWK, newHawk);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHome() {
		return (String)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__HOME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHome(String newHome) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__HOME, newHome);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHostname() {
		return (String)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__HOSTNAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHostname(String newHostname) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__HOSTNAME, newHostname);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HostResource getHostResource() {
		return (HostResource)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__HOST_RESOURCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHostResource(HostResource newHostResource, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(TopologyPackage.Literals.DOCUMENT_ROOT__HOST_RESOURCE, newHostResource, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHostResource(HostResource newHostResource) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__HOST_RESOURCE, newHostResource);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HostResources getHostResources() {
		return (HostResources)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__HOST_RESOURCES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHostResources(HostResources newHostResources, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(TopologyPackage.Literals.DOCUMENT_ROOT__HOST_RESOURCES, newHostResources, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHostResources(HostResources newHostResources) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__HOST_RESOURCES, newHostResources);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIp() {
		return (String)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__IP, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIp(String newIp) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__IP, newIp);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MasterFiles getMasterFiles() {
		return (MasterFiles)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__MASTER_FILES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMasterFiles(MasterFiles newMasterFiles, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(TopologyPackage.Literals.DOCUMENT_ROOT__MASTER_FILES, newMasterFiles, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMasterFiles(MasterFiles newMasterFiles) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__MASTER_FILES, newMasterFiles);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOsType() {
		return (String)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__OS_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOsType(String newOsType) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__OS_TYPE, newOsType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessingUnitConfig getProcessingUnitConfig() {
		return (ProcessingUnitConfig)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__PROCESSING_UNIT_CONFIG, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProcessingUnitConfig(ProcessingUnitConfig newProcessingUnitConfig, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(TopologyPackage.Literals.DOCUMENT_ROOT__PROCESSING_UNIT_CONFIG, newProcessingUnitConfig, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProcessingUnitConfig(ProcessingUnitConfig newProcessingUnitConfig) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__PROCESSING_UNIT_CONFIG, newProcessingUnitConfig);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessingUnitsConfig getProcessingUnitsConfig() {
		return (ProcessingUnitsConfig)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__PROCESSING_UNITS_CONFIG, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProcessingUnitsConfig(ProcessingUnitsConfig newProcessingUnitsConfig, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(TopologyPackage.Literals.DOCUMENT_ROOT__PROCESSING_UNITS_CONFIG, newProcessingUnitsConfig, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProcessingUnitsConfig(ProcessingUnitsConfig newProcessingUnitsConfig) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__PROCESSING_UNITS_CONFIG, newProcessingUnitsConfig);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Pstools getPstools() {
		return (Pstools)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__PSTOOLS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPstools(Pstools newPstools, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(TopologyPackage.Literals.DOCUMENT_ROOT__PSTOOLS, newPstools, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPstools(Pstools newPstools) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__PSTOOLS, newPstools);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RunVersion getRunVersion() {
		return (RunVersion)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__RUN_VERSION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRunVersion(RunVersion newRunVersion, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(TopologyPackage.Literals.DOCUMENT_ROOT__RUN_VERSION, newRunVersion, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRunVersion(RunVersion newRunVersion) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__RUN_VERSION, newRunVersion);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Site getSite() {
		return (Site)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__SITE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSite(Site newSite, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(TopologyPackage.Literals.DOCUMENT_ROOT__SITE, newSite, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSite(Site newSite) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__SITE, newSite);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Software getSoftware() {
		return (Software)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__SOFTWARE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSoftware(Software newSoftware, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(TopologyPackage.Literals.DOCUMENT_ROOT__SOFTWARE, newSoftware, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSoftware(Software newSoftware) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__SOFTWARE, newSoftware);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Ssh getSsh() {
		return (Ssh)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__SSH, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSsh(Ssh newSsh, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(TopologyPackage.Literals.DOCUMENT_ROOT__SSH, newSsh, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSsh(Ssh newSsh) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__SSH, newSsh);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StartPuMethod getStartPuMethod() {
		return (StartPuMethod)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__START_PU_METHOD, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStartPuMethod(StartPuMethod newStartPuMethod, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(TopologyPackage.Literals.DOCUMENT_ROOT__START_PU_METHOD, newStartPuMethod, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartPuMethod(StartPuMethod newStartPuMethod) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__START_PU_METHOD, newStartPuMethod);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTra() {
		return (String)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__TRA, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTra(String newTra) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__TRA, newTra);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserCredentials getUserCredentials() {
		return (UserCredentials)getMixed().get(TopologyPackage.Literals.DOCUMENT_ROOT__USER_CREDENTIALS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUserCredentials(UserCredentials newUserCredentials, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(TopologyPackage.Literals.DOCUMENT_ROOT__USER_CREDENTIALS, newUserCredentials, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUserCredentials(UserCredentials newUserCredentials) {
		((FeatureMap.Internal)getMixed()).set(TopologyPackage.Literals.DOCUMENT_ROOT__USER_CREDENTIALS, newUserCredentials);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TopologyPackage.DOCUMENT_ROOT__MIXED:
				return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
			case TopologyPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return ((InternalEList<?>)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
			case TopologyPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return ((InternalEList<?>)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
			case TopologyPackage.DOCUMENT_ROOT__BE:
				return basicSetBe(null, msgs);
			case TopologyPackage.DOCUMENT_ROOT__BE_RUNTIME:
				return basicSetBeRuntime(null, msgs);
			case TopologyPackage.DOCUMENT_ROOT__CLUSTER:
				return basicSetCluster(null, msgs);
			case TopologyPackage.DOCUMENT_ROOT__CLUSTERS:
				return basicSetClusters(null, msgs);
			case TopologyPackage.DOCUMENT_ROOT__DEPLOYED_FILES:
				return basicSetDeployedFiles(null, msgs);
			case TopologyPackage.DOCUMENT_ROOT__DEPLOYMENT_MAPPING:
				return basicSetDeploymentMapping(null, msgs);
			case TopologyPackage.DOCUMENT_ROOT__DEPLOYMENT_MAPPINGS:
				return basicSetDeploymentMappings(null, msgs);
			case TopologyPackage.DOCUMENT_ROOT__DEPLOYMENT_UNIT:
				return basicSetDeploymentUnit(null, msgs);
			case TopologyPackage.DOCUMENT_ROOT__DEPLOYMENT_UNITS:
				return basicSetDeploymentUnits(null, msgs);
			case TopologyPackage.DOCUMENT_ROOT__HAWK:
				return basicSetHawk(null, msgs);
			case TopologyPackage.DOCUMENT_ROOT__HOST_RESOURCE:
				return basicSetHostResource(null, msgs);
			case TopologyPackage.DOCUMENT_ROOT__HOST_RESOURCES:
				return basicSetHostResources(null, msgs);
			case TopologyPackage.DOCUMENT_ROOT__MASTER_FILES:
				return basicSetMasterFiles(null, msgs);
			case TopologyPackage.DOCUMENT_ROOT__PROCESSING_UNIT_CONFIG:
				return basicSetProcessingUnitConfig(null, msgs);
			case TopologyPackage.DOCUMENT_ROOT__PROCESSING_UNITS_CONFIG:
				return basicSetProcessingUnitsConfig(null, msgs);
			case TopologyPackage.DOCUMENT_ROOT__PSTOOLS:
				return basicSetPstools(null, msgs);
			case TopologyPackage.DOCUMENT_ROOT__RUN_VERSION:
				return basicSetRunVersion(null, msgs);
			case TopologyPackage.DOCUMENT_ROOT__SITE:
				return basicSetSite(null, msgs);
			case TopologyPackage.DOCUMENT_ROOT__SOFTWARE:
				return basicSetSoftware(null, msgs);
			case TopologyPackage.DOCUMENT_ROOT__SSH:
				return basicSetSsh(null, msgs);
			case TopologyPackage.DOCUMENT_ROOT__START_PU_METHOD:
				return basicSetStartPuMethod(null, msgs);
			case TopologyPackage.DOCUMENT_ROOT__USER_CREDENTIALS:
				return basicSetUserCredentials(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TopologyPackage.DOCUMENT_ROOT__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case TopologyPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				if (coreType) return getXMLNSPrefixMap();
				else return getXMLNSPrefixMap().map();
			case TopologyPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				if (coreType) return getXSISchemaLocation();
				else return getXSISchemaLocation().map();
			case TopologyPackage.DOCUMENT_ROOT__BE:
				return getBe();
			case TopologyPackage.DOCUMENT_ROOT__BE_RUNTIME:
				return getBeRuntime();
			case TopologyPackage.DOCUMENT_ROOT__CDD_DEPLOYED:
				return getCddDeployed();
			case TopologyPackage.DOCUMENT_ROOT__CDD_MASTER:
				return getCddMaster();
			case TopologyPackage.DOCUMENT_ROOT__CLUSTER:
				return getCluster();
			case TopologyPackage.DOCUMENT_ROOT__CLUSTERS:
				return getClusters();
			case TopologyPackage.DOCUMENT_ROOT__DEPLOYED_FILES:
				return getDeployedFiles();
			case TopologyPackage.DOCUMENT_ROOT__DEPLOYMENT_MAPPING:
				return getDeploymentMapping();
			case TopologyPackage.DOCUMENT_ROOT__DEPLOYMENT_MAPPINGS:
				return getDeploymentMappings();
			case TopologyPackage.DOCUMENT_ROOT__DEPLOYMENT_UNIT:
				return getDeploymentUnit();
			case TopologyPackage.DOCUMENT_ROOT__DEPLOYMENT_UNITS:
				return getDeploymentUnits();
			case TopologyPackage.DOCUMENT_ROOT__EAR_DEPLOYED:
				return getEarDeployed();
			case TopologyPackage.DOCUMENT_ROOT__EAR_MASTER:
				return getEarMaster();
			case TopologyPackage.DOCUMENT_ROOT__HAWK:
				return getHawk();
			case TopologyPackage.DOCUMENT_ROOT__HOME:
				return getHome();
			case TopologyPackage.DOCUMENT_ROOT__HOSTNAME:
				return getHostname();
			case TopologyPackage.DOCUMENT_ROOT__HOST_RESOURCE:
				return getHostResource();
			case TopologyPackage.DOCUMENT_ROOT__HOST_RESOURCES:
				return getHostResources();
			case TopologyPackage.DOCUMENT_ROOT__IP:
				return getIp();
			case TopologyPackage.DOCUMENT_ROOT__MASTER_FILES:
				return getMasterFiles();
			case TopologyPackage.DOCUMENT_ROOT__OS_TYPE:
				return getOsType();
			case TopologyPackage.DOCUMENT_ROOT__PROCESSING_UNIT_CONFIG:
				return getProcessingUnitConfig();
			case TopologyPackage.DOCUMENT_ROOT__PROCESSING_UNITS_CONFIG:
				return getProcessingUnitsConfig();
			case TopologyPackage.DOCUMENT_ROOT__PSTOOLS:
				return getPstools();
			case TopologyPackage.DOCUMENT_ROOT__RUN_VERSION:
				return getRunVersion();
			case TopologyPackage.DOCUMENT_ROOT__SITE:
				return getSite();
			case TopologyPackage.DOCUMENT_ROOT__SOFTWARE:
				return getSoftware();
			case TopologyPackage.DOCUMENT_ROOT__SSH:
				return getSsh();
			case TopologyPackage.DOCUMENT_ROOT__START_PU_METHOD:
				return getStartPuMethod();
			case TopologyPackage.DOCUMENT_ROOT__TRA:
				return getTra();
			case TopologyPackage.DOCUMENT_ROOT__USER_CREDENTIALS:
				return getUserCredentials();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TopologyPackage.DOCUMENT_ROOT__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__BE:
				setBe((Be)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__BE_RUNTIME:
				setBeRuntime((BeRuntime)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__CDD_DEPLOYED:
				setCddDeployed((String)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__CDD_MASTER:
				setCddMaster((String)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__CLUSTER:
				setCluster((Cluster)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__CLUSTERS:
				setClusters((Clusters)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__DEPLOYED_FILES:
				setDeployedFiles((DeployedFiles)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__DEPLOYMENT_MAPPING:
				setDeploymentMapping((DeploymentMapping)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__DEPLOYMENT_MAPPINGS:
				setDeploymentMappings((DeploymentMappings)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__DEPLOYMENT_UNIT:
				setDeploymentUnit((DeploymentUnit)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__DEPLOYMENT_UNITS:
				setDeploymentUnits((DeploymentUnits)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__EAR_DEPLOYED:
				setEarDeployed((String)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__EAR_MASTER:
				setEarMaster((String)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__HAWK:
				setHawk((Hawk)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__HOME:
				setHome((String)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__HOSTNAME:
				setHostname((String)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__HOST_RESOURCE:
				setHostResource((HostResource)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__HOST_RESOURCES:
				setHostResources((HostResources)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__IP:
				setIp((String)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__MASTER_FILES:
				setMasterFiles((MasterFiles)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__OS_TYPE:
				setOsType((String)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__PROCESSING_UNIT_CONFIG:
				setProcessingUnitConfig((ProcessingUnitConfig)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__PROCESSING_UNITS_CONFIG:
				setProcessingUnitsConfig((ProcessingUnitsConfig)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__PSTOOLS:
				setPstools((Pstools)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__RUN_VERSION:
				setRunVersion((RunVersion)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__SITE:
				setSite((Site)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__SOFTWARE:
				setSoftware((Software)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__SSH:
				setSsh((Ssh)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__START_PU_METHOD:
				setStartPuMethod((StartPuMethod)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__TRA:
				setTra((String)newValue);
				return;
			case TopologyPackage.DOCUMENT_ROOT__USER_CREDENTIALS:
				setUserCredentials((UserCredentials)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TopologyPackage.DOCUMENT_ROOT__MIXED:
				getMixed().clear();
				return;
			case TopologyPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				getXMLNSPrefixMap().clear();
				return;
			case TopologyPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				getXSISchemaLocation().clear();
				return;
			case TopologyPackage.DOCUMENT_ROOT__BE:
				setBe((Be)null);
				return;
			case TopologyPackage.DOCUMENT_ROOT__BE_RUNTIME:
				setBeRuntime((BeRuntime)null);
				return;
			case TopologyPackage.DOCUMENT_ROOT__CDD_DEPLOYED:
				setCddDeployed(CDD_DEPLOYED_EDEFAULT);
				return;
			case TopologyPackage.DOCUMENT_ROOT__CDD_MASTER:
				setCddMaster(CDD_MASTER_EDEFAULT);
				return;
			case TopologyPackage.DOCUMENT_ROOT__CLUSTER:
				setCluster((Cluster)null);
				return;
			case TopologyPackage.DOCUMENT_ROOT__CLUSTERS:
				setClusters((Clusters)null);
				return;
			case TopologyPackage.DOCUMENT_ROOT__DEPLOYED_FILES:
				setDeployedFiles((DeployedFiles)null);
				return;
			case TopologyPackage.DOCUMENT_ROOT__DEPLOYMENT_MAPPING:
				setDeploymentMapping((DeploymentMapping)null);
				return;
			case TopologyPackage.DOCUMENT_ROOT__DEPLOYMENT_MAPPINGS:
				setDeploymentMappings((DeploymentMappings)null);
				return;
			case TopologyPackage.DOCUMENT_ROOT__DEPLOYMENT_UNIT:
				setDeploymentUnit((DeploymentUnit)null);
				return;
			case TopologyPackage.DOCUMENT_ROOT__DEPLOYMENT_UNITS:
				setDeploymentUnits((DeploymentUnits)null);
				return;
			case TopologyPackage.DOCUMENT_ROOT__EAR_DEPLOYED:
				setEarDeployed(EAR_DEPLOYED_EDEFAULT);
				return;
			case TopologyPackage.DOCUMENT_ROOT__EAR_MASTER:
				setEarMaster(EAR_MASTER_EDEFAULT);
				return;
			case TopologyPackage.DOCUMENT_ROOT__HAWK:
				setHawk((Hawk)null);
				return;
			case TopologyPackage.DOCUMENT_ROOT__HOME:
				setHome(HOME_EDEFAULT);
				return;
			case TopologyPackage.DOCUMENT_ROOT__HOSTNAME:
				setHostname(HOSTNAME_EDEFAULT);
				return;
			case TopologyPackage.DOCUMENT_ROOT__HOST_RESOURCE:
				setHostResource((HostResource)null);
				return;
			case TopologyPackage.DOCUMENT_ROOT__HOST_RESOURCES:
				setHostResources((HostResources)null);
				return;
			case TopologyPackage.DOCUMENT_ROOT__IP:
				setIp(IP_EDEFAULT);
				return;
			case TopologyPackage.DOCUMENT_ROOT__MASTER_FILES:
				setMasterFiles((MasterFiles)null);
				return;
			case TopologyPackage.DOCUMENT_ROOT__OS_TYPE:
				setOsType(OS_TYPE_EDEFAULT);
				return;
			case TopologyPackage.DOCUMENT_ROOT__PROCESSING_UNIT_CONFIG:
				setProcessingUnitConfig((ProcessingUnitConfig)null);
				return;
			case TopologyPackage.DOCUMENT_ROOT__PROCESSING_UNITS_CONFIG:
				setProcessingUnitsConfig((ProcessingUnitsConfig)null);
				return;
			case TopologyPackage.DOCUMENT_ROOT__PSTOOLS:
				setPstools((Pstools)null);
				return;
			case TopologyPackage.DOCUMENT_ROOT__RUN_VERSION:
				setRunVersion((RunVersion)null);
				return;
			case TopologyPackage.DOCUMENT_ROOT__SITE:
				setSite((Site)null);
				return;
			case TopologyPackage.DOCUMENT_ROOT__SOFTWARE:
				setSoftware((Software)null);
				return;
			case TopologyPackage.DOCUMENT_ROOT__SSH:
				setSsh((Ssh)null);
				return;
			case TopologyPackage.DOCUMENT_ROOT__START_PU_METHOD:
				setStartPuMethod((StartPuMethod)null);
				return;
			case TopologyPackage.DOCUMENT_ROOT__TRA:
				setTra(TRA_EDEFAULT);
				return;
			case TopologyPackage.DOCUMENT_ROOT__USER_CREDENTIALS:
				setUserCredentials((UserCredentials)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TopologyPackage.DOCUMENT_ROOT__MIXED:
				return mixed != null && !mixed.isEmpty();
			case TopologyPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
			case TopologyPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
			case TopologyPackage.DOCUMENT_ROOT__BE:
				return getBe() != null;
			case TopologyPackage.DOCUMENT_ROOT__BE_RUNTIME:
				return getBeRuntime() != null;
			case TopologyPackage.DOCUMENT_ROOT__CDD_DEPLOYED:
				return CDD_DEPLOYED_EDEFAULT == null ? getCddDeployed() != null : !CDD_DEPLOYED_EDEFAULT.equals(getCddDeployed());
			case TopologyPackage.DOCUMENT_ROOT__CDD_MASTER:
				return CDD_MASTER_EDEFAULT == null ? getCddMaster() != null : !CDD_MASTER_EDEFAULT.equals(getCddMaster());
			case TopologyPackage.DOCUMENT_ROOT__CLUSTER:
				return getCluster() != null;
			case TopologyPackage.DOCUMENT_ROOT__CLUSTERS:
				return getClusters() != null;
			case TopologyPackage.DOCUMENT_ROOT__DEPLOYED_FILES:
				return getDeployedFiles() != null;
			case TopologyPackage.DOCUMENT_ROOT__DEPLOYMENT_MAPPING:
				return getDeploymentMapping() != null;
			case TopologyPackage.DOCUMENT_ROOT__DEPLOYMENT_MAPPINGS:
				return getDeploymentMappings() != null;
			case TopologyPackage.DOCUMENT_ROOT__DEPLOYMENT_UNIT:
				return getDeploymentUnit() != null;
			case TopologyPackage.DOCUMENT_ROOT__DEPLOYMENT_UNITS:
				return getDeploymentUnits() != null;
			case TopologyPackage.DOCUMENT_ROOT__EAR_DEPLOYED:
				return EAR_DEPLOYED_EDEFAULT == null ? getEarDeployed() != null : !EAR_DEPLOYED_EDEFAULT.equals(getEarDeployed());
			case TopologyPackage.DOCUMENT_ROOT__EAR_MASTER:
				return EAR_MASTER_EDEFAULT == null ? getEarMaster() != null : !EAR_MASTER_EDEFAULT.equals(getEarMaster());
			case TopologyPackage.DOCUMENT_ROOT__HAWK:
				return getHawk() != null;
			case TopologyPackage.DOCUMENT_ROOT__HOME:
				return HOME_EDEFAULT == null ? getHome() != null : !HOME_EDEFAULT.equals(getHome());
			case TopologyPackage.DOCUMENT_ROOT__HOSTNAME:
				return HOSTNAME_EDEFAULT == null ? getHostname() != null : !HOSTNAME_EDEFAULT.equals(getHostname());
			case TopologyPackage.DOCUMENT_ROOT__HOST_RESOURCE:
				return getHostResource() != null;
			case TopologyPackage.DOCUMENT_ROOT__HOST_RESOURCES:
				return getHostResources() != null;
			case TopologyPackage.DOCUMENT_ROOT__IP:
				return IP_EDEFAULT == null ? getIp() != null : !IP_EDEFAULT.equals(getIp());
			case TopologyPackage.DOCUMENT_ROOT__MASTER_FILES:
				return getMasterFiles() != null;
			case TopologyPackage.DOCUMENT_ROOT__OS_TYPE:
				return OS_TYPE_EDEFAULT == null ? getOsType() != null : !OS_TYPE_EDEFAULT.equals(getOsType());
			case TopologyPackage.DOCUMENT_ROOT__PROCESSING_UNIT_CONFIG:
				return getProcessingUnitConfig() != null;
			case TopologyPackage.DOCUMENT_ROOT__PROCESSING_UNITS_CONFIG:
				return getProcessingUnitsConfig() != null;
			case TopologyPackage.DOCUMENT_ROOT__PSTOOLS:
				return getPstools() != null;
			case TopologyPackage.DOCUMENT_ROOT__RUN_VERSION:
				return getRunVersion() != null;
			case TopologyPackage.DOCUMENT_ROOT__SITE:
				return getSite() != null;
			case TopologyPackage.DOCUMENT_ROOT__SOFTWARE:
				return getSoftware() != null;
			case TopologyPackage.DOCUMENT_ROOT__SSH:
				return getSsh() != null;
			case TopologyPackage.DOCUMENT_ROOT__START_PU_METHOD:
				return getStartPuMethod() != null;
			case TopologyPackage.DOCUMENT_ROOT__TRA:
				return TRA_EDEFAULT == null ? getTra() != null : !TRA_EDEFAULT.equals(getTra());
			case TopologyPackage.DOCUMENT_ROOT__USER_CREDENTIALS:
				return getUserCredentials() != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (mixed: ");
		result.append(mixed);
		result.append(')');
		return result.toString();
	}

} //DocumentRootImpl
