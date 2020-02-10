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
 * A representation of the model object '<em><b>Alert Config Set Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.AlertConfigSetConfig#getAlertConfig <em>Alert Config</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getAlertConfigSetConfig()
 * @model extendedMetaData="name='alert-config-set-type' kind='elementOnly'"
 * @generated
 */
public interface AlertConfigSetConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Alert Config</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.AlertConfigConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Alert Config</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alert Config</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getAlertConfigSetConfig_AlertConfig()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='alert-config' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<AlertConfigConfig> getAlertConfig();

} // AlertConfigSetConfig
