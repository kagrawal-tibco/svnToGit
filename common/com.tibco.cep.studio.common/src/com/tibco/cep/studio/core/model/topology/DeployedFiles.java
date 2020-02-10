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
 * A representation of the model object '<em><b>Deployed Files</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DeployedFiles#getCddDeployed <em>Cdd Deployed</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.DeployedFiles#getEarDeployed <em>Ear Deployed</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDeployedFiles()
 * @model extendedMetaData="name='deployed-files_._type' kind='elementOnly'"
 * @generated
 */
public interface DeployedFiles extends EObject {
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
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDeployedFiles_CddDeployed()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='cdd-deployed' namespace='##targetNamespace'"
	 * @generated
	 */
	String getCddDeployed();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DeployedFiles#getCddDeployed <em>Cdd Deployed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cdd Deployed</em>' attribute.
	 * @see #getCddDeployed()
	 * @generated
	 */
	void setCddDeployed(String value);

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
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getDeployedFiles_EarDeployed()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='ear-deployed' namespace='##targetNamespace'"
	 * @generated
	 */
	String getEarDeployed();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.DeployedFiles#getEarDeployed <em>Ear Deployed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ear Deployed</em>' attribute.
	 * @see #getEarDeployed()
	 * @generated
	 */
	void setEarDeployed(String value);

} // DeployedFiles
