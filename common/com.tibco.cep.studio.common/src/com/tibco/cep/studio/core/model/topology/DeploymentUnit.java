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
 * A representation of the model object '<em><b>Deployment Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DeploymentUnit#getDeployedFiles <em>Deployed Files</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DeploymentUnit#getProcessingUnitsConfig <em>Processing Units Config</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DeploymentUnit#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DeploymentUnit#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDeploymentUnit()
 * @model extendedMetaData="name='deployment-unit_._type' kind='elementOnly'"
 * @generated
 */
public interface DeploymentUnit extends EObject {
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
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDeploymentUnit_DeployedFiles()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='deployed-files' namespace='##targetNamespace'"
	 * @generated
	 */
	DeployedFiles getDeployedFiles();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DeploymentUnit#getDeployedFiles <em>Deployed Files</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deployed Files</em>' containment reference.
	 * @see #getDeployedFiles()
	 * @generated
	 */
	void setDeployedFiles(DeployedFiles value);

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
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDeploymentUnit_ProcessingUnitsConfig()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='processing-units-config' namespace='##targetNamespace'"
	 * @generated
	 */
	ProcessingUnitsConfig getProcessingUnitsConfig();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DeploymentUnit#getProcessingUnitsConfig <em>Processing Units Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Processing Units Config</em>' containment reference.
	 * @see #getProcessingUnitsConfig()
	 * @generated
	 */
	void setProcessingUnitsConfig(ProcessingUnitsConfig value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDeploymentUnit_Id()
	 * @model id="true" dataType="org.eclipse.emf.ecore.xml.type.ID"
	 *        extendedMetaData="kind='attribute' name='id'"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DeploymentUnit#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

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
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDeploymentUnit_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DeploymentUnit#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // DeploymentUnit
