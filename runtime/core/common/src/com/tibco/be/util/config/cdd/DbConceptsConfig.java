/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Db Concepts Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.DbConceptsConfig#getCheckInterval <em>Check Interval</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DbConceptsConfig#getDbUris <em>Db Uris</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DbConceptsConfig#getInactivityTimeout <em>Inactivity Timeout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DbConceptsConfig#getInitialSize <em>Initial Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DbConceptsConfig#getMaxSize <em>Max Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DbConceptsConfig#getMinSize <em>Min Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DbConceptsConfig#getPropertyCheckInterval <em>Property Check Interval</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DbConceptsConfig#getRetryCount <em>Retry Count</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.DbConceptsConfig#getWaitTimeout <em>Wait Timeout</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getDbConceptsConfig()
 * @model extendedMetaData="name='db-concepts-type' kind='elementOnly'"
 * @generated
 */
public interface DbConceptsConfig extends ArtifactConfig {
	/**
	 * Returns the value of the '<em><b>Check Interval</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Check Interval</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Check Interval</em>' containment reference.
	 * @see #setCheckInterval(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDbConceptsConfig_CheckInterval()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='check-interval' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getCheckInterval();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DbConceptsConfig#getCheckInterval <em>Check Interval</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Check Interval</em>' containment reference.
	 * @see #getCheckInterval()
	 * @generated
	 */
	void setCheckInterval(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Db Uris</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Db Uris</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Db Uris</em>' containment reference.
	 * @see #setDbUris(UrisConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDbConceptsConfig_DbUris()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='db-uris' namespace='##targetNamespace'"
	 * @generated
	 */
	UrisConfig getDbUris();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DbConceptsConfig#getDbUris <em>Db Uris</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Db Uris</em>' containment reference.
	 * @see #getDbUris()
	 * @generated
	 */
	void setDbUris(UrisConfig value);

	/**
	 * Returns the value of the '<em><b>Inactivity Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inactivity Timeout</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inactivity Timeout</em>' containment reference.
	 * @see #setInactivityTimeout(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDbConceptsConfig_InactivityTimeout()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='inactivity-timeout' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getInactivityTimeout();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DbConceptsConfig#getInactivityTimeout <em>Inactivity Timeout</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inactivity Timeout</em>' containment reference.
	 * @see #getInactivityTimeout()
	 * @generated
	 */
	void setInactivityTimeout(OverrideConfig value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDbConceptsConfig_InitialSize()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='initial-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getInitialSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DbConceptsConfig#getInitialSize <em>Initial Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initial Size</em>' containment reference.
	 * @see #getInitialSize()
	 * @generated
	 */
	void setInitialSize(OverrideConfig value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDbConceptsConfig_MaxSize()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='max-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getMaxSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DbConceptsConfig#getMaxSize <em>Max Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Size</em>' containment reference.
	 * @see #getMaxSize()
	 * @generated
	 */
	void setMaxSize(OverrideConfig value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDbConceptsConfig_MinSize()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='min-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getMinSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DbConceptsConfig#getMinSize <em>Min Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Size</em>' containment reference.
	 * @see #getMinSize()
	 * @generated
	 */
	void setMinSize(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Property Check Interval</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Check Interval</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Check Interval</em>' containment reference.
	 * @see #setPropertyCheckInterval(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDbConceptsConfig_PropertyCheckInterval()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='property-check-interval' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getPropertyCheckInterval();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DbConceptsConfig#getPropertyCheckInterval <em>Property Check Interval</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Check Interval</em>' containment reference.
	 * @see #getPropertyCheckInterval()
	 * @generated
	 */
	void setPropertyCheckInterval(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Retry Count</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Retry Count</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Retry Count</em>' containment reference.
	 * @see #setRetryCount(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDbConceptsConfig_RetryCount()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='retry-count' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getRetryCount();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DbConceptsConfig#getRetryCount <em>Retry Count</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Retry Count</em>' containment reference.
	 * @see #getRetryCount()
	 * @generated
	 */
	void setRetryCount(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Wait Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Wait Timeout</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Wait Timeout</em>' containment reference.
	 * @see #setWaitTimeout(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getDbConceptsConfig_WaitTimeout()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='wait-timeout' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getWaitTimeout();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.DbConceptsConfig#getWaitTimeout <em>Wait Timeout</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Wait Timeout</em>' containment reference.
	 * @see #getWaitTimeout()
	 * @generated
	 */
	void setWaitTimeout(OverrideConfig value);

} // DbConceptsConfig
