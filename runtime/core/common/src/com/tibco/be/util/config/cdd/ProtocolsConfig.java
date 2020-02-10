/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Protocols Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.ProtocolsConfig#getProtocol <em>Protocol</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getProtocolsConfig()
 * @model extendedMetaData="name='protocols-type' kind='elementOnly'"
 * @generated
 */
public interface ProtocolsConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Protocol</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.OverrideConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Protocol</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Protocol</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getProtocolsConfig_Protocol()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='protocol' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<OverrideConfig> getProtocol();

} // ProtocolsConfig
