/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedjmscon;

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
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getNamingEnvironment <em>Naming Environment</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getConnectionAttributes <em>Connection Attributes</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getUseXacf <em>Use Xacf</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getUseSsl <em>Use Ssl</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getUseSharedJndiConfig <em>Use Shared Jndi Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getAdmFactorySslPassword <em>Adm Factory Ssl Password</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getSsl <em>Ssl</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getJndiSharedConfiguration <em>Jndi Shared Configuration</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getJndiProperties <em>Jndi Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getConfig()
 * @model extendedMetaData="name='Config-type' kind='elementOnly'"
 * @generated
 */
public interface Config extends EObject {
	/**
	 * Returns the value of the '<em><b>Naming Environment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Naming Environment</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Naming Environment</em>' containment reference.
	 * @see #setNamingEnvironment(NamingEnvironment)
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getConfig_NamingEnvironment()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='NamingEnvironment' namespace='##targetNamespace'"
	 * @generated
	 */
	NamingEnvironment getNamingEnvironment();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getNamingEnvironment <em>Naming Environment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Naming Environment</em>' containment reference.
	 * @see #getNamingEnvironment()
	 * @generated
	 */
	void setNamingEnvironment(NamingEnvironment value);

	/**
	 * Returns the value of the '<em><b>Connection Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Connection Attributes</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Connection Attributes</em>' containment reference.
	 * @see #setConnectionAttributes(ConnectionAttributes)
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getConfig_ConnectionAttributes()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='ConnectionAttributes' namespace='##targetNamespace'"
	 * @generated
	 */
	ConnectionAttributes getConnectionAttributes();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getConnectionAttributes <em>Connection Attributes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Connection Attributes</em>' containment reference.
	 * @see #getConnectionAttributes()
	 * @generated
	 */
	void setConnectionAttributes(ConnectionAttributes value);

	/**
	 * Returns the value of the '<em><b>Use Xacf</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Use Xacf</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Use Xacf</em>' attribute.
	 * @see #setUseXacf(Object)
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getConfig_UseXacf()
	 * @model dataType="com.tibco.be.util.config.sharedresources.basetypes.BooleanOrGvs" required="true"
	 *        extendedMetaData="kind='element' name='UseXACF' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getUseXacf();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getUseXacf <em>Use Xacf</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Use Xacf</em>' attribute.
	 * @see #getUseXacf()
	 * @generated
	 */
	void setUseXacf(Object value);

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
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getConfig_UseSsl()
	 * @model dataType="com.tibco.be.util.config.sharedresources.basetypes.BooleanOrGvs"
	 *        extendedMetaData="kind='element' name='useSsl' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getUseSsl();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getUseSsl <em>Use Ssl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Use Ssl</em>' attribute.
	 * @see #getUseSsl()
	 * @generated
	 */
	void setUseSsl(Object value);

	/**
	 * Returns the value of the '<em><b>Use Shared Jndi Config</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Use Shared Jndi Config</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Use Shared Jndi Config</em>' attribute.
	 * @see #setUseSharedJndiConfig(Object)
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getConfig_UseSharedJndiConfig()
	 * @model dataType="com.tibco.be.util.config.sharedresources.basetypes.BooleanOrGvs"
	 *        extendedMetaData="kind='element' name='UseSharedJndiConfig' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getUseSharedJndiConfig();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getUseSharedJndiConfig <em>Use Shared Jndi Config</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Use Shared Jndi Config</em>' attribute.
	 * @see #getUseSharedJndiConfig()
	 * @generated
	 */
	void setUseSharedJndiConfig(Object value);

	/**
	 * Returns the value of the '<em><b>Adm Factory Ssl Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Adm Factory Ssl Password</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Adm Factory Ssl Password</em>' attribute.
	 * @see #setAdmFactorySslPassword(String)
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getConfig_AdmFactorySslPassword()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='AdmFactorySslPassword' namespace='##targetNamespace'"
	 * @generated
	 */
	String getAdmFactorySslPassword();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getAdmFactorySslPassword <em>Adm Factory Ssl Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Adm Factory Ssl Password</em>' attribute.
	 * @see #getAdmFactorySslPassword()
	 * @generated
	 */
	void setAdmFactorySslPassword(String value);

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
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getConfig_Ssl()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='ssl' namespace='http://www.tibco.com/xmlns/aemeta/services/2002'"
	 * @generated
	 */
	Ssl getSsl();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getSsl <em>Ssl</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ssl</em>' containment reference.
	 * @see #getSsl()
	 * @generated
	 */
	void setSsl(Ssl value);

	/**
	 * Returns the value of the '<em><b>Jndi Shared Configuration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Jndi Shared Configuration</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jndi Shared Configuration</em>' attribute.
	 * @see #setJndiSharedConfiguration(String)
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getConfig_JndiSharedConfiguration()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='JndiSharedConfiguration' namespace='##targetNamespace'"
	 * @generated
	 */
	String getJndiSharedConfiguration();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getJndiSharedConfiguration <em>Jndi Shared Configuration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Jndi Shared Configuration</em>' attribute.
	 * @see #getJndiSharedConfiguration()
	 * @generated
	 */
	void setJndiSharedConfiguration(String value);

	/**
	 * Returns the value of the '<em><b>Jndi Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Jndi Properties</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jndi Properties</em>' containment reference.
	 * @see #setJndiProperties(JndiProperties)
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getConfig_JndiProperties()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='JNDIProperties' namespace='##targetNamespace'"
	 * @generated
	 */
	JndiProperties getJndiProperties();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config#getJndiProperties <em>Jndi Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Jndi Properties</em>' containment reference.
	 * @see #getJndiProperties()
	 * @generated
	 */
	void setJndiProperties(JndiProperties value);

} // Config
