/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.model.topology;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Processing Units Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.ProcessingUnitsConfig#getProcessingUnitConfig <em>Processing Unit Config</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getProcessingUnitsConfig()
 * @model extendedMetaData="name='processing-units-config_._type' kind='elementOnly'"
 * @generated
 */
public interface ProcessingUnitsConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Processing Unit Config</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.core.model.topology.ProcessingUnitConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Processing Unit Config</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Processing Unit Config</em>' containment reference list.
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getProcessingUnitsConfig_ProcessingUnitConfig()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='processing-unit-config' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<ProcessingUnitConfig> getProcessingUnitConfig();

} // ProcessingUnitsConfig
