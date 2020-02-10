/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Connection Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.ConnectionConfig#getInitialSize <em>Initial Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ConnectionConfig#getMinSize <em>Min Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ConnectionConfig#getMaxSize <em>Max Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ConnectionConfig#getUri <em>Uri</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getConnectionConfig()
 * @model extendedMetaData="name='connection-type' kind='elementOnly'"
 * @generated
 */
public interface ConnectionConfig extends ArtifactConfig {
	/**
	 * Returns the value of the '<em><b>Initial Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Initial Size</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initial Size</em>' containment reference.
	 * @see #setInitialSize(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getConnectionConfig_InitialSize()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='initial-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getInitialSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ConnectionConfig#getInitialSize <em>Initial Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initial Size</em>' containment reference.
	 * @see #getInitialSize()
	 * @generated
	 */
	void setInitialSize(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Min Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min Size</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min Size</em>' containment reference.
	 * @see #setMinSize(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getConnectionConfig_MinSize()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='min-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getMinSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ConnectionConfig#getMinSize <em>Min Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Size</em>' containment reference.
	 * @see #getMinSize()
	 * @generated
	 */
	void setMinSize(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Max Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Size</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Size</em>' containment reference.
	 * @see #setMaxSize(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getConnectionConfig_MaxSize()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='max-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getMaxSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ConnectionConfig#getMaxSize <em>Max Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Size</em>' containment reference.
	 * @see #getMaxSize()
	 * @generated
	 */
	void setMaxSize(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uri</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uri</em>' attribute.
	 * @see #setUri(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getConnectionConfig_Uri()
	 * @model dataType="com.tibco.be.util.config.cdd.OntologyUriConfig" required="true"
	 *        extendedMetaData="kind='element' name='uri' namespace='##targetNamespace'"
	 * @generated
	 */
	String getUri();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ConnectionConfig#getUri <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uri</em>' attribute.
	 * @see #getUri()
	 * @generated
	 */
	void setUri(String value);

} // ConnectionConfig
