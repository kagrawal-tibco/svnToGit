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
 * A representation of the model object '<em><b>Job Manager Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.JobManagerConfig#getJobPoolQueueSize <em>Job Pool Queue Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.JobManagerConfig#getJobPoolThreadCount <em>Job Pool Thread Count</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getJobManagerConfig()
 * @model extendedMetaData="name='job-manager-type' kind='elementOnly'"
 * @generated
 */
public interface JobManagerConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Job Pool Queue Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Job Pool Queue Size</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Job Pool Queue Size</em>' containment reference.
	 * @see #setJobPoolQueueSize(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getJobManagerConfig_JobPoolQueueSize()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='job-pool-queue-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getJobPoolQueueSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.JobManagerConfig#getJobPoolQueueSize <em>Job Pool Queue Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Job Pool Queue Size</em>' containment reference.
	 * @see #getJobPoolQueueSize()
	 * @generated
	 */
	void setJobPoolQueueSize(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Job Pool Thread Count</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Job Pool Thread Count</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Job Pool Thread Count</em>' containment reference.
	 * @see #setJobPoolThreadCount(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getJobManagerConfig_JobPoolThreadCount()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='job-pool-thread-count' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getJobPoolThreadCount();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.JobManagerConfig#getJobPoolThreadCount <em>Job Pool Thread Count</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Job Pool Thread Count</em>' containment reference.
	 * @see #getJobPoolThreadCount()
	 * @generated
	 */
	void setJobPoolThreadCount(OverrideConfig value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='return new java.util.Properties();'"
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // JobManagerConfig
