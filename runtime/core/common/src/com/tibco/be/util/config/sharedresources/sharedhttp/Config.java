/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedhttp;

import com.tibco.be.util.config.sharedresources.aemetaservices.Ssl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedhttp.Config#getHost <em>Host</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedhttp.Config#getPort <em>Port</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedhttp.Config#getEnableLookups <em>Enable Lookups</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedhttp.Config#getUseSsl <em>Use Ssl</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedhttp.Config#getSsl <em>Ssl</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedhttp.Config#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.sharedresources.sharedhttp.SharedhttpPackage#getConfig()
 * @model extendedMetaData="name='configType' kind='elementOnly'"
 * @generated
 */
public interface Config extends EObject {
	/**
	 * Returns the value of the '<em><b>Host</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Host</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Host</em>' attribute.
	 * @see #setHost(String)
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.SharedhttpPackage#getConfig_Host()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='Host'"
	 * @generated
	 */
	String getHost();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedhttp.Config#getHost <em>Host</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Host</em>' attribute.
	 * @see #getHost()
	 * @generated
	 */
	void setHost(String value);

	/**
	 * Returns the value of the '<em><b>Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Port</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Port</em>' attribute.
	 * @see #setPort(Object)
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.SharedhttpPackage#getConfig_Port()
	 * @model dataType="com.tibco.be.util.config.sharedresources.basetypes.IntOrGVs" required="true"
	 *        extendedMetaData="kind='element' name='Port'"
	 * @generated
	 */
	Object getPort();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedhttp.Config#getPort <em>Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Port</em>' attribute.
	 * @see #getPort()
	 * @generated
	 */
	void setPort(Object value);

	/**
	 * Returns the value of the '<em><b>Enable Lookups</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enable Lookups</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enable Lookups</em>' attribute.
	 * @see #setEnableLookups(Object)
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.SharedhttpPackage#getConfig_EnableLookups()
	 * @model dataType="com.tibco.be.util.config.sharedresources.basetypes.BooleanOrGvs" required="true"
	 *        extendedMetaData="kind='element' name='enableLookups'"
	 * @generated
	 */
	Object getEnableLookups();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedhttp.Config#getEnableLookups <em>Enable Lookups</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enable Lookups</em>' attribute.
	 * @see #getEnableLookups()
	 * @generated
	 */
	void setEnableLookups(Object value);

	/**
	 * Returns the value of the '<em><b>Use Ssl</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Use Ssl</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Use Ssl</em>' attribute.
	 * @see #setUseSsl(Object)
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.SharedhttpPackage#getConfig_UseSsl()
	 * @model dataType="com.tibco.be.util.config.sharedresources.basetypes.BooleanOrGvs"
	 *        extendedMetaData="kind='element' name='useSsl'"
	 * @generated
	 */
	Object getUseSsl();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedhttp.Config#getUseSsl <em>Use Ssl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Use Ssl</em>' attribute.
	 * @see #getUseSsl()
	 * @generated
	 */
	void setUseSsl(Object value);

	/**
	 * Returns the value of the '<em><b>Ssl</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ssl</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ssl</em>' containment reference.
	 * @see #setSsl(Ssl)
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.SharedhttpPackage#getConfig_Ssl()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='ssl' namespace='http://www.tibco.com/xmlns/aemeta/services/2002'"
	 * @generated
	 */
	Ssl getSsl();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedhttp.Config#getSsl <em>Ssl</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ssl</em>' containment reference.
	 * @see #getSsl()
	 * @generated
	 */
	void setSsl(Ssl value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see com.tibco.be.util.config.sharedresources.sharedhttp.SharedhttpPackage#getConfig_Description()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='description'"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedhttp.Config#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

} // Config
