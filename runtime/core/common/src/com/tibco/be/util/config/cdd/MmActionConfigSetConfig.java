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
 * A representation of the model object '<em><b>Mm Action Config Set Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.MmActionConfigSetConfig#getMmActionConfig <em>Mm Action Config</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getMmActionConfigSetConfig()
 * @model extendedMetaData="name='mm-action-config-set-type' kind='elementOnly'"
 * @generated
 */
public interface MmActionConfigSetConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Mm Action Config</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.MmActionConfigConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mm Action Config</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mm Action Config</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getMmActionConfigSetConfig_MmActionConfig()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='mm-action-config' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<MmActionConfigConfig> getMmActionConfig();

} // MmActionConfigSetConfig
