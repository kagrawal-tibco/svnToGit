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
 * A representation of the model object '<em><b>Function Groups Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.FunctionGroupsConfig#getFunctions <em>Functions</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getFunctionGroupsConfig()
 * @model extendedMetaData="name='function-groups-type' kind='elementOnly'"
 * @generated
 */
public interface FunctionGroupsConfig extends ArtifactConfig {
	/**
	 * Returns the value of the '<em><b>Functions</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.FunctionsConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Functions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Functions</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getFunctionGroupsConfig_Functions()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='functions' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<FunctionsConfig> getFunctions();

} // FunctionGroupsConfig
