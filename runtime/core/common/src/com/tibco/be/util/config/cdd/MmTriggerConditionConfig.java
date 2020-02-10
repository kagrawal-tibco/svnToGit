/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mm Trigger Condition Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.MmTriggerConditionConfig#getMmHealthLevel <em>Mm Health Level</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.MmTriggerConditionConfig#getMmAlert <em>Mm Alert</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getMmTriggerConditionConfig()
 * @model extendedMetaData="name='mm-trigger-condition-type' kind='elementOnly'"
 * @generated
 */
public interface MmTriggerConditionConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Mm Health Level</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mm Health Level</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mm Health Level</em>' containment reference.
	 * @see #setMmHealthLevel(MmHealthLevelConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getMmTriggerConditionConfig_MmHealthLevel()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='mm-health-level' namespace='##targetNamespace'"
	 * @generated
	 */
	MmHealthLevelConfig getMmHealthLevel();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.MmTriggerConditionConfig#getMmHealthLevel <em>Mm Health Level</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mm Health Level</em>' containment reference.
	 * @see #getMmHealthLevel()
	 * @generated
	 */
	void setMmHealthLevel(MmHealthLevelConfig value);

	/**
	 * Returns the value of the '<em><b>Mm Alert</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mm Alert</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mm Alert</em>' containment reference.
	 * @see #setMmAlert(MmAlertConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getMmTriggerConditionConfig_MmAlert()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='mm-alert' namespace='##targetNamespace'"
	 * @generated
	 */
	MmAlertConfig getMmAlert();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.MmTriggerConditionConfig#getMmAlert <em>Mm Alert</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mm Alert</em>' containment reference.
	 * @see #getMmAlert()
	 * @generated
	 */
	void setMmAlert(MmAlertConfig value);

} // MmTriggerConditionConfig
