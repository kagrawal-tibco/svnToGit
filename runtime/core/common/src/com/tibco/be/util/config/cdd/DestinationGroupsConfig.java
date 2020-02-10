/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Destination Groups Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.DestinationGroupsConfig#getDestinations <em>Destinations</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getDestinationGroupsConfig()
 * @model extendedMetaData="name='destination-groups-type' kind='elementOnly'"
 * @generated
 */
public interface DestinationGroupsConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Destinations</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.DestinationsConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Destinations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Destinations</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDestinationGroupsConfig_Destinations()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='destinations' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<DestinationsConfig> getDestinations();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='return new java.util.Properties();'"
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // DestinationGroupsConfig
