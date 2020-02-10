/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Load Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.LoadConfig#getMaxActive <em>Max Active</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getLoadConfig()
 * @model extendedMetaData="name='load-type' kind='elementOnly'"
 * @generated
 */
public interface LoadConfig extends ArtifactConfig {
	/**
	 * Returns the value of the '<em><b>Max Active</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Active</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Active</em>' containment reference.
	 * @see #setMaxActive(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLoadConfig_MaxActive()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='max-active' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getMaxActive();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LoadConfig#getMaxActive <em>Max Active</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Active</em>' containment reference.
	 * @see #getMaxActive()
	 * @generated
	 */
	void setMaxActive(OverrideConfig value);

} // LoadConfig
