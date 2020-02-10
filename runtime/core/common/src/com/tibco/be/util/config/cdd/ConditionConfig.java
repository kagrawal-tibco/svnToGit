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
 * A representation of the model object '<em><b>Condition Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.ConditionConfig#getGetProperty <em>Get Property</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getConditionConfig()
 * @model extendedMetaData="name='condition-type' kind='elementOnly'"
 * @generated
 */
public interface ConditionConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Get Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Get Property</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Get Property</em>' containment reference.
	 * @see #setGetProperty(GetPropertyConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getConditionConfig_GetProperty()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='get-property' namespace='##targetNamespace'"
	 * @generated
	 */
	GetPropertyConfig getGetProperty();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ConditionConfig#getGetProperty <em>Get Property</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Get Property</em>' containment reference.
	 * @see #getGetProperty()
	 * @generated
	 */
	void setGetProperty(GetPropertyConfig value);

} // ConditionConfig
