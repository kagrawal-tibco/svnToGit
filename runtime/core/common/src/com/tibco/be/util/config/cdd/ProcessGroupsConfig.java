/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Process Groups Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.ProcessGroupsConfig#getProcesses <em>Processes</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessGroupsConfig()
 * @model extendedMetaData="name='process-groups-type' kind='elementOnly'"
 * @generated
 */
public interface ProcessGroupsConfig extends ArtifactConfig {
	/**
	 * Returns the value of the '<em><b>Processes</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.ProcessesConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Processes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Processes</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessGroupsConfig_Processes()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='processes' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<ProcessesConfig> getProcesses();

} // ProcessGroupsConfig
