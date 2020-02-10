/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Shared Queue Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.SharedQueueConfig#getSize <em>Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.SharedQueueConfig#getWorkers <em>Workers</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getSharedQueueConfig()
 * @model extendedMetaData="name='shared-queue-type' kind='elementOnly'"
 * @generated
 */
public interface SharedQueueConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Size</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Size</em>' containment reference.
	 * @see #setSize(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getSharedQueueConfig_Size()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.SharedQueueConfig#getSize <em>Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Size</em>' containment reference.
	 * @see #getSize()
	 * @generated
	 */
	void setSize(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Workers</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Workers</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Workers</em>' containment reference.
	 * @see #setWorkers(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getSharedQueueConfig_Workers()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='workers' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getWorkers();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.SharedQueueConfig#getWorkers <em>Workers</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Workers</em>' containment reference.
	 * @see #getWorkers()
	 * @generated
	 */
	void setWorkers(OverrideConfig value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // SharedQueueConfig
