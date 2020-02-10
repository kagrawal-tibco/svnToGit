/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.model.topology;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getBe <em>Be</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getBeRuntime <em>Be Runtime</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getCddDeployed <em>Cdd Deployed</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getCddMaster <em>Cdd Master</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getCluster <em>Cluster</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getClusters <em>Clusters</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getDeployedFiles <em>Deployed Files</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getDeploymentMapping <em>Deployment Mapping</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getDeploymentMappings <em>Deployment Mappings</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getDeploymentUnit <em>Deployment Unit</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getDeploymentUnits <em>Deployment Units</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getEarDeployed <em>Ear Deployed</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getEarMaster <em>Ear Master</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getHawk <em>Hawk</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getHome <em>Home</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getHostname <em>Hostname</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getHostResource <em>Host Resource</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getHostResources <em>Host Resources</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getIp <em>Ip</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getMasterFiles <em>Master Files</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getOsType <em>Os Type</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getProcessingUnitConfig <em>Processing Unit Config</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getProcessingUnitsConfig <em>Processing Units Config</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getPstools <em>Pstools</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getRunVersion <em>Run Version</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getSite <em>Site</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getSoftware <em>Software</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getSsh <em>Ssh</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getStartPuMethod <em>Start Pu Method</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getTra <em>Tra</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getUserCredentials <em>User Credentials</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface DocumentRoot extends EObject {
	/**
	 * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mixed</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mixed</em>' attribute list.
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_Mixed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' name=':mixed'"
	 * @generated
	 */
	FeatureMap getMixed();

	/**
	 * Returns the value of the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XMLNS Prefix Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XMLNS Prefix Map</em>' map.
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_XMLNSPrefixMap()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
	 *        extendedMetaData="kind='attribute' name='xmlns:prefix'"
	 * @generated
	 */
	EMap<String, String> getXMLNSPrefixMap();

	/**
	 * Returns the value of the '<em><b>XSI Schema Location</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XSI Schema Location</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XSI Schema Location</em>' map.
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_XSISchemaLocation()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
	 *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
	 * @generated
	 */
	EMap<String, String> getXSISchemaLocation();

	/**
	 * Returns the value of the '<em><b>Be</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Be</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Be</em>' containment reference.
	 * @see #setBe(Be)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_Be()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='be' namespace='##targetNamespace'"
	 * @generated
	 */
	Be getBe();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getBe <em>Be</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Be</em>' containment reference.
	 * @see #getBe()
	 * @generated
	 */
	void setBe(Be value);

	/**
	 * Returns the value of the '<em><b>Be Runtime</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Be Runtime</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Be Runtime</em>' containment reference.
	 * @see #setBeRuntime(BeRuntime)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_BeRuntime()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='be-runtime' namespace='##targetNamespace'"
	 * @generated
	 */
	BeRuntime getBeRuntime();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getBeRuntime <em>Be Runtime</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Be Runtime</em>' containment reference.
	 * @see #getBeRuntime()
	 * @generated
	 */
	void setBeRuntime(BeRuntime value);

	/**
	 * Returns the value of the '<em><b>Cdd Deployed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cdd Deployed</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cdd Deployed</em>' attribute.
	 * @see #setCddDeployed(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_CddDeployed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='cdd-deployed' namespace='##targetNamespace'"
	 * @generated
	 */
	String getCddDeployed();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getCddDeployed <em>Cdd Deployed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cdd Deployed</em>' attribute.
	 * @see #getCddDeployed()
	 * @generated
	 */
	void setCddDeployed(String value);

	/**
	 * Returns the value of the '<em><b>Cdd Master</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cdd Master</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cdd Master</em>' attribute.
	 * @see #setCddMaster(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_CddMaster()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='cdd-master' namespace='##targetNamespace'"
	 * @generated
	 */
	String getCddMaster();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getCddMaster <em>Cdd Master</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cdd Master</em>' attribute.
	 * @see #getCddMaster()
	 * @generated
	 */
	void setCddMaster(String value);

	/**
	 * Returns the value of the '<em><b>Cluster</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cluster</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cluster</em>' containment reference.
	 * @see #setCluster(Cluster)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_Cluster()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='cluster' namespace='##targetNamespace'"
	 * @generated
	 */
	Cluster getCluster();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getCluster <em>Cluster</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cluster</em>' containment reference.
	 * @see #getCluster()
	 * @generated
	 */
	void setCluster(Cluster value);

	/**
	 * Returns the value of the '<em><b>Clusters</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Clusters</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Clusters</em>' containment reference.
	 * @see #setClusters(Clusters)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_Clusters()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='clusters' namespace='##targetNamespace'"
	 * @generated
	 */
	Clusters getClusters();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getClusters <em>Clusters</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Clusters</em>' containment reference.
	 * @see #getClusters()
	 * @generated
	 */
	void setClusters(Clusters value);

	/**
	 * Returns the value of the '<em><b>Deployed Files</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deployed Files</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deployed Files</em>' containment reference.
	 * @see #setDeployedFiles(DeployedFiles)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_DeployedFiles()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='deployed-files' namespace='##targetNamespace'"
	 * @generated
	 */
	DeployedFiles getDeployedFiles();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getDeployedFiles <em>Deployed Files</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deployed Files</em>' containment reference.
	 * @see #getDeployedFiles()
	 * @generated
	 */
	void setDeployedFiles(DeployedFiles value);

	/**
	 * Returns the value of the '<em><b>Deployment Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deployment Mapping</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deployment Mapping</em>' containment reference.
	 * @see #setDeploymentMapping(DeploymentMapping)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_DeploymentMapping()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='deployment-mapping' namespace='##targetNamespace'"
	 * @generated
	 */
	DeploymentMapping getDeploymentMapping();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getDeploymentMapping <em>Deployment Mapping</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deployment Mapping</em>' containment reference.
	 * @see #getDeploymentMapping()
	 * @generated
	 */
	void setDeploymentMapping(DeploymentMapping value);

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
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_DeploymentMappings()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='deployment-mappings' namespace='##targetNamespace'"
	 * @generated
	 */
	DeploymentMappings getDeploymentMappings();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getDeploymentMappings <em>Deployment Mappings</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deployment Mappings</em>' containment reference.
	 * @see #getDeploymentMappings()
	 * @generated
	 */
	void setDeploymentMappings(DeploymentMappings value);

	/**
	 * Returns the value of the '<em><b>Deployment Unit</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deployment Unit</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deployment Unit</em>' containment reference.
	 * @see #setDeploymentUnit(DeploymentUnit)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_DeploymentUnit()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='deployment-unit' namespace='##targetNamespace'"
	 * @generated
	 */
	DeploymentUnit getDeploymentUnit();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getDeploymentUnit <em>Deployment Unit</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deployment Unit</em>' containment reference.
	 * @see #getDeploymentUnit()
	 * @generated
	 */
	void setDeploymentUnit(DeploymentUnit value);

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
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_DeploymentUnits()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='deployment-units' namespace='##targetNamespace'"
	 * @generated
	 */
	DeploymentUnits getDeploymentUnits();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getDeploymentUnits <em>Deployment Units</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deployment Units</em>' containment reference.
	 * @see #getDeploymentUnits()
	 * @generated
	 */
	void setDeploymentUnits(DeploymentUnits value);

	/**
	 * Returns the value of the '<em><b>Ear Deployed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ear Deployed</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ear Deployed</em>' attribute.
	 * @see #setEarDeployed(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_EarDeployed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ear-deployed' namespace='##targetNamespace'"
	 * @generated
	 */
	String getEarDeployed();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getEarDeployed <em>Ear Deployed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ear Deployed</em>' attribute.
	 * @see #getEarDeployed()
	 * @generated
	 */
	void setEarDeployed(String value);

	/**
	 * Returns the value of the '<em><b>Ear Master</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ear Master</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ear Master</em>' attribute.
	 * @see #setEarMaster(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_EarMaster()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ear-master' namespace='##targetNamespace'"
	 * @generated
	 */
	String getEarMaster();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getEarMaster <em>Ear Master</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ear Master</em>' attribute.
	 * @see #getEarMaster()
	 * @generated
	 */
	void setEarMaster(String value);

	/**
	 * Returns the value of the '<em><b>Hawk</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hawk</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hawk</em>' containment reference.
	 * @see #setHawk(Hawk)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_Hawk()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='hawk' namespace='##targetNamespace'"
	 * @generated
	 */
	Hawk getHawk();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getHawk <em>Hawk</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hawk</em>' containment reference.
	 * @see #getHawk()
	 * @generated
	 */
	void setHawk(Hawk value);

	/**
	 * Returns the value of the '<em><b>Home</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Home</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Home</em>' attribute.
	 * @see #setHome(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_Home()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='home' namespace='##targetNamespace'"
	 * @generated
	 */
	String getHome();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getHome <em>Home</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Home</em>' attribute.
	 * @see #getHome()
	 * @generated
	 */
	void setHome(String value);

	/**
	 * Returns the value of the '<em><b>Hostname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hostname</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hostname</em>' attribute.
	 * @see #setHostname(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_Hostname()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='hostname' namespace='##targetNamespace'"
	 * @generated
	 */
	String getHostname();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getHostname <em>Hostname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hostname</em>' attribute.
	 * @see #getHostname()
	 * @generated
	 */
	void setHostname(String value);

	/**
	 * Returns the value of the '<em><b>Host Resource</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Host Resource</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Host Resource</em>' containment reference.
	 * @see #setHostResource(HostResource)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_HostResource()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='host-resource' namespace='##targetNamespace'"
	 * @generated
	 */
	HostResource getHostResource();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getHostResource <em>Host Resource</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Host Resource</em>' containment reference.
	 * @see #getHostResource()
	 * @generated
	 */
	void setHostResource(HostResource value);

	/**
	 * Returns the value of the '<em><b>Host Resources</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Host Resources</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Host Resources</em>' containment reference.
	 * @see #setHostResources(HostResources)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_HostResources()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='host-resources' namespace='##targetNamespace'"
	 * @generated
	 */
	HostResources getHostResources();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getHostResources <em>Host Resources</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Host Resources</em>' containment reference.
	 * @see #getHostResources()
	 * @generated
	 */
	void setHostResources(HostResources value);

	/**
	 * Returns the value of the '<em><b>Ip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ip</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ip</em>' attribute.
	 * @see #setIp(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_Ip()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ip' namespace='##targetNamespace'"
	 * @generated
	 */
	String getIp();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getIp <em>Ip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ip</em>' attribute.
	 * @see #getIp()
	 * @generated
	 */
	void setIp(String value);

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
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_MasterFiles()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='master-files' namespace='##targetNamespace'"
	 * @generated
	 */
	MasterFiles getMasterFiles();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getMasterFiles <em>Master Files</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Master Files</em>' containment reference.
	 * @see #getMasterFiles()
	 * @generated
	 */
	void setMasterFiles(MasterFiles value);

	/**
	 * Returns the value of the '<em><b>Os Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Os Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Os Type</em>' attribute.
	 * @see #setOsType(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_OsType()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='os-type' namespace='##targetNamespace'"
	 * @generated
	 */
	String getOsType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getOsType <em>Os Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Os Type</em>' attribute.
	 * @see #getOsType()
	 * @generated
	 */
	void setOsType(String value);

	/**
	 * Returns the value of the '<em><b>Processing Unit Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Processing Unit Config</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Processing Unit Config</em>' containment reference.
	 * @see #setProcessingUnitConfig(ProcessingUnitConfig)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_ProcessingUnitConfig()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='processing-unit-config' namespace='##targetNamespace'"
	 * @generated
	 */
	ProcessingUnitConfig getProcessingUnitConfig();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getProcessingUnitConfig <em>Processing Unit Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Processing Unit Config</em>' containment reference.
	 * @see #getProcessingUnitConfig()
	 * @generated
	 */
	void setProcessingUnitConfig(ProcessingUnitConfig value);

	/**
	 * Returns the value of the '<em><b>Processing Units Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Processing Units Config</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Processing Units Config</em>' containment reference.
	 * @see #setProcessingUnitsConfig(ProcessingUnitsConfig)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_ProcessingUnitsConfig()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='processing-units-config' namespace='##targetNamespace'"
	 * @generated
	 */
	ProcessingUnitsConfig getProcessingUnitsConfig();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getProcessingUnitsConfig <em>Processing Units Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Processing Units Config</em>' containment reference.
	 * @see #getProcessingUnitsConfig()
	 * @generated
	 */
	void setProcessingUnitsConfig(ProcessingUnitsConfig value);

	/**
	 * Returns the value of the '<em><b>Pstools</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pstools</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pstools</em>' containment reference.
	 * @see #setPstools(Pstools)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_Pstools()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='pstools' namespace='##targetNamespace'"
	 * @generated
	 */
	Pstools getPstools();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getPstools <em>Pstools</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pstools</em>' containment reference.
	 * @see #getPstools()
	 * @generated
	 */
	void setPstools(Pstools value);

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
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_RunVersion()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='run-version' namespace='##targetNamespace'"
	 * @generated
	 */
	RunVersion getRunVersion();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getRunVersion <em>Run Version</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Run Version</em>' containment reference.
	 * @see #getRunVersion()
	 * @generated
	 */
	void setRunVersion(RunVersion value);

	/**
	 * Returns the value of the '<em><b>Site</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Site</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Site</em>' containment reference.
	 * @see #setSite(Site)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_Site()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='site' namespace='##targetNamespace'"
	 * @generated
	 */
	Site getSite();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getSite <em>Site</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Site</em>' containment reference.
	 * @see #getSite()
	 * @generated
	 */
	void setSite(Site value);

	/**
	 * Returns the value of the '<em><b>Software</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Software</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Software</em>' containment reference.
	 * @see #setSoftware(Software)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_Software()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='software' namespace='##targetNamespace'"
	 * @generated
	 */
	Software getSoftware();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getSoftware <em>Software</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Software</em>' containment reference.
	 * @see #getSoftware()
	 * @generated
	 */
	void setSoftware(Software value);

	/**
	 * Returns the value of the '<em><b>Ssh</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ssh</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ssh</em>' containment reference.
	 * @see #setSsh(Ssh)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_Ssh()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ssh' namespace='##targetNamespace'"
	 * @generated
	 */
	Ssh getSsh();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getSsh <em>Ssh</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ssh</em>' containment reference.
	 * @see #getSsh()
	 * @generated
	 */
	void setSsh(Ssh value);

	/**
	 * Returns the value of the '<em><b>Start Pu Method</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start Pu Method</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Pu Method</em>' containment reference.
	 * @see #setStartPuMethod(StartPuMethod)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_StartPuMethod()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='start-pu-method' namespace='##targetNamespace'"
	 * @generated
	 */
	StartPuMethod getStartPuMethod();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getStartPuMethod <em>Start Pu Method</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Pu Method</em>' containment reference.
	 * @see #getStartPuMethod()
	 * @generated
	 */
	void setStartPuMethod(StartPuMethod value);

	/**
	 * Returns the value of the '<em><b>Tra</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tra</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tra</em>' attribute.
	 * @see #setTra(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_Tra()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='tra' namespace='##targetNamespace'"
	 * @generated
	 */
	String getTra();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getTra <em>Tra</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tra</em>' attribute.
	 * @see #getTra()
	 * @generated
	 */
	void setTra(String value);

	/**
	 * Returns the value of the '<em><b>User Credentials</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User Credentials</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Credentials</em>' containment reference.
	 * @see #setUserCredentials(UserCredentials)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDocumentRoot_UserCredentials()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='user-credentials' namespace='##targetNamespace'"
	 * @generated
	 */
	UserCredentials getUserCredentials();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DocumentRoot#getUserCredentials <em>User Credentials</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User Credentials</em>' containment reference.
	 * @see #getUserCredentials()
	 * @generated
	 */
	void setUserCredentials(UserCredentials value);

} // DocumentRoot
