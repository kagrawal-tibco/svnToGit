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
 * A representation of the model object '<em><b>Mm Action Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.MmActionConfig#getMmExecuteCommand <em>Mm Execute Command</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.MmActionConfig#getMmSendEmail <em>Mm Send Email</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getMmActionConfig()
 * @model extendedMetaData="name='mm-action-type' kind='elementOnly'"
 * @generated
 */
public interface MmActionConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Mm Execute Command</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.MmExecuteCommandConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mm Execute Command</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mm Execute Command</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getMmActionConfig_MmExecuteCommand()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='mm-execute-command' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<MmExecuteCommandConfig> getMmExecuteCommand();

	/**
	 * Returns the value of the '<em><b>Mm Send Email</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.MmSendEmailConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mm Send Email</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mm Send Email</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getMmActionConfig_MmSendEmail()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='mm-send-email' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<MmSendEmailConfig> getMmSendEmail();

} // MmActionConfig
