/**
 */
package com.tibco.be.util.config.cdd;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Publisher Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.PublisherConfig#getPublisherQueueSize <em>Publisher Queue Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.PublisherConfig#getPublisherThreadCount <em>Publisher Thread Count</em>}</li>
 * </ul>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getPublisherConfig()
 * @model extendedMetaData="name='publisher-type' kind='elementOnly'"
 * @generated
 */
public interface PublisherConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Publisher Queue Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Publisher Queue Size</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Publisher Queue Size</em>' containment reference.
	 * @see #setPublisherQueueSize(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getPublisherConfig_PublisherQueueSize()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='publisher-queue-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getPublisherQueueSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.PublisherConfig#getPublisherQueueSize <em>Publisher Queue Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Publisher Queue Size</em>' containment reference.
	 * @see #getPublisherQueueSize()
	 * @generated
	 */
	void setPublisherQueueSize(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Publisher Thread Count</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Publisher Thread Count</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Publisher Thread Count</em>' containment reference.
	 * @see #setPublisherThreadCount(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getPublisherConfig_PublisherThreadCount()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='publisher-thread-count' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getPublisherThreadCount();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.PublisherConfig#getPublisherThreadCount <em>Publisher Thread Count</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Publisher Thread Count</em>' containment reference.
	 * @see #getPublisherThreadCount()
	 * @generated
	 */
	void setPublisherThreadCount(OverrideConfig value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='return new java.util.Properties();'"
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // PublisherConfig
