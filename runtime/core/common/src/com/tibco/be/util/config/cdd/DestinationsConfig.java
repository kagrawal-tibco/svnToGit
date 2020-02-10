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
 * A representation of the model object '<em><b>Destinations Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.DestinationsConfig#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DestinationsConfig#getRef <em>Ref</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DestinationsConfig#getDestination <em>Destination</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getDestinationsConfig()
 * @model extendedMetaData="name='destinations-type' kind='elementOnly'"
 * @generated
 */
public interface DestinationsConfig extends ArtifactConfig {
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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDestinationsConfig_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:1'"
	 * @generated
	 */
	FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>Ref</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.DestinationsConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ref</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ref</em>' reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDestinationsConfig_Ref()
	 * @model resolveProxies="false" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ref' namespace='##targetNamespace' group='#group:1'"
	 * @generated
	 */
	EList<DestinationsConfig> getRef();

	/**
	 * Returns the value of the '<em><b>Destination</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.DestinationConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Destination</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Destination</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDestinationsConfig_Destination()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='destination' namespace='##targetNamespace' group='#group:1'"
	 * @generated
	 */
	EList<DestinationConfig> getDestination();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" many="false"
	 * @generated
	 */
	EList<DestinationConfig> getAllDestinations();

} // DestinationsConfig
