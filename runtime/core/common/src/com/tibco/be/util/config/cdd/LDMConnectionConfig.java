/**
 */
package com.tibco.be.util.config.cdd;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>LDM Connection Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.LDMConnectionConfig#getLdmUrl <em>Ldm Url</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.LDMConnectionConfig#getUserName <em>User Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.LDMConnectionConfig#getUserPassword <em>User Password</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.LDMConnectionConfig#getInitialSize <em>Initial Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.LDMConnectionConfig#getMinSize <em>Min Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.LDMConnectionConfig#getMaxSize <em>Max Size</em>}</li>
 * </ul>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getLDMConnectionConfig()
 * @model extendedMetaData="name='ldm-connection-type' kind='elementOnly'"
 * @generated
 */
public interface LDMConnectionConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Ldm Url</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ldm Url</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ldm Url</em>' containment reference.
	 * @see #setLdmUrl(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLDMConnectionConfig_LdmUrl()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='ldm-url' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getLdmUrl();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LDMConnectionConfig#getLdmUrl <em>Ldm Url</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ldm Url</em>' containment reference.
	 * @see #getLdmUrl()
	 * @generated
	 */
	void setLdmUrl(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>User Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User Name</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Name</em>' containment reference.
	 * @see #setUserName(SecurityOverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLDMConnectionConfig_UserName()
	 * @model containment="true" required="true"
	 *        extendedMetaData="name='user-name' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	SecurityOverrideConfig getUserName();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LDMConnectionConfig#getUserName <em>User Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User Name</em>' containment reference.
	 * @see #getUserName()
	 * @generated
	 */
	void setUserName(SecurityOverrideConfig value);

	/**
	 * Returns the value of the '<em><b>User Password</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User Password</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Password</em>' containment reference.
	 * @see #setUserPassword(SecurityOverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLDMConnectionConfig_UserPassword()
	 * @model containment="true" required="true"
	 *        extendedMetaData="name='user-password' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	SecurityOverrideConfig getUserPassword();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LDMConnectionConfig#getUserPassword <em>User Password</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User Password</em>' containment reference.
	 * @see #getUserPassword()
	 * @generated
	 */
	void setUserPassword(SecurityOverrideConfig value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLDMConnectionConfig_InitialSize()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='initial-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getInitialSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LDMConnectionConfig#getInitialSize <em>Initial Size</em>}' containment reference.
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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLDMConnectionConfig_MinSize()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='min-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getMinSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LDMConnectionConfig#getMinSize <em>Min Size</em>}' containment reference.
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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLDMConnectionConfig_MaxSize()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='max-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getMaxSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LDMConnectionConfig#getMaxSize <em>Max Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Size</em>' containment reference.
	 * @see #getMaxSize()
	 * @generated
	 */
	void setMaxSize(OverrideConfig value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='return new java.util.Properties();'"
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // LDMConnectionConfig
