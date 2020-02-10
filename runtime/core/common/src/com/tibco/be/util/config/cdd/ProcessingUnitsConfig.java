/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Processing Units Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.ProcessingUnitsConfig#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ProcessingUnitsConfig#getProcessingUnit <em>Processing Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessingUnitsConfig()
 * @model extendedMetaData="name='processing-units-type' kind='elementOnly'"
 * @generated
 */
public interface ProcessingUnitsConfig extends ArtifactConfig {
	/**
	 * Returns the value of the '<em><b>Group</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Group</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group</em>' attribute list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessingUnitsConfig_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:1'"
	 * @generated
	 */
	FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>Processing Unit</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.ProcessingUnitConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Processing Unit</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Processing Unit</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProcessingUnitsConfig_ProcessingUnit()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='processing-unit' namespace='##targetNamespace' group='#group:1'"
	 * @generated
	 */
	EList<ProcessingUnitConfig> getProcessingUnit();

} // ProcessingUnitsConfig
