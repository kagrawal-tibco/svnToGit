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
 * A representation of the model object '<em><b>Mm Action Config Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.MmActionConfigConfig#getMmTriggerCondition <em>Mm Trigger Condition</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.MmActionConfigConfig#getMmAction <em>Mm Action</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.MmActionConfigConfig#getActionName <em>Action Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getMmActionConfigConfig()
 * @model extendedMetaData="name='mm-action-config-type' kind='elementOnly'"
 * @generated
 */
public interface MmActionConfigConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Mm Trigger Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mm Trigger Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mm Trigger Condition</em>' containment reference.
	 * @see #setMmTriggerCondition(MmTriggerConditionConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getMmActionConfigConfig_MmTriggerCondition()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='mm-trigger-condition' namespace='##targetNamespace'"
	 * @generated
	 */
	MmTriggerConditionConfig getMmTriggerCondition();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.MmActionConfigConfig#getMmTriggerCondition <em>Mm Trigger Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mm Trigger Condition</em>' containment reference.
	 * @see #getMmTriggerCondition()
	 * @generated
	 */
	void setMmTriggerCondition(MmTriggerConditionConfig value);

	/**
	 * Returns the value of the '<em><b>Mm Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mm Action</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mm Action</em>' containment reference.
	 * @see #setMmAction(MmActionConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getMmActionConfigConfig_MmAction()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='mm-action' namespace='##targetNamespace'"
	 * @generated
	 */
	MmActionConfig getMmAction();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.MmActionConfigConfig#getMmAction <em>Mm Action</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mm Action</em>' containment reference.
	 * @see #getMmAction()
	 * @generated
	 */
	void setMmAction(MmActionConfig value);

	/**
	 * Returns the value of the '<em><b>Action Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action Name</em>' attribute.
	 * @see #setActionName(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getMmActionConfigConfig_ActionName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='action-name'"
	 * @generated
	 */
	String getActionName();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.MmActionConfigConfig#getActionName <em>Action Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Action Name</em>' attribute.
	 * @see #getActionName()
	 * @generated
	 */
	void setActionName(String value);

} // MmActionConfigConfig
