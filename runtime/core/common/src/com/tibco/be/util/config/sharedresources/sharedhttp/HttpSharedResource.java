/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedhttp;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Http Shared Resource</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedhttp.HttpSharedResource#getConfig <em>Config</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.sharedresources.sharedhttp.SharedhttpPackage#getHttpSharedResource()
 * @model extendedMetaData="name='httpSharedResourceType' kind='elementOnly'"
 * @generated
 */
public interface HttpSharedResource extends EObject {
	/**
	 * Returns the value of the '<em><b>Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Config</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Config</em>' containment reference.
	 * @see #setConfig(Config)
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.SharedhttpPackage#getHttpSharedResource_Config()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='config'"
	 * @generated
	 */
	Config getConfig();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedhttp.HttpSharedResource#getConfig <em>Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Config</em>' containment reference.
	 * @see #getConfig()
	 * @generated
	 */
	void setConfig(Config value);

} // HttpSharedResource
