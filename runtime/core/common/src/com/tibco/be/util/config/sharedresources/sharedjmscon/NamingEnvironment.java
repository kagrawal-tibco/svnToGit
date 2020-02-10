/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedjmscon;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Naming Environment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getUseJndi <em>Use Jndi</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getProviderUrl <em>Provider Url</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getNamingUrl <em>Naming Url</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getNamingInitialContextFactory <em>Naming Initial Context Factory</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getTopicFactoryName <em>Topic Factory Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getQueueFactoryName <em>Queue Factory Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getNamingPrincipal <em>Naming Principal</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getNamingCredential <em>Naming Credential</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getNamingEnvironment()
 * @model extendedMetaData="name='NamingEnvironment-type' kind='elementOnly'"
 * @generated
 */
public interface NamingEnvironment extends EObject {
	/**
	 * Returns the value of the '<em><b>Use Jndi</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Use Jndi</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Use Jndi</em>' attribute.
	 * @see #setUseJndi(Object)
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getNamingEnvironment_UseJndi()
	 * @model dataType="com.tibco.be.util.config.sharedresources.basetypes.BooleanOrGvs" required="true"
	 *        extendedMetaData="kind='element' name='UseJNDI' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getUseJndi();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getUseJndi <em>Use Jndi</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Use Jndi</em>' attribute.
	 * @see #getUseJndi()
	 * @generated
	 */
	void setUseJndi(Object value);

	/**
	 * Returns the value of the '<em><b>Provider Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provider Url</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provider Url</em>' attribute.
	 * @see #setProviderUrl(String)
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getNamingEnvironment_ProviderUrl()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='ProviderURL' namespace='##targetNamespace'"
	 * @generated
	 */
	String getProviderUrl();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getProviderUrl <em>Provider Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provider Url</em>' attribute.
	 * @see #getProviderUrl()
	 * @generated
	 */
	void setProviderUrl(String value);

	/**
	 * Returns the value of the '<em><b>Naming Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Naming Url</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Naming Url</em>' attribute.
	 * @see #setNamingUrl(String)
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getNamingEnvironment_NamingUrl()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='NamingURL' namespace='##targetNamespace'"
	 * @generated
	 */
	String getNamingUrl();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getNamingUrl <em>Naming Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Naming Url</em>' attribute.
	 * @see #getNamingUrl()
	 * @generated
	 */
	void setNamingUrl(String value);

	/**
	 * Returns the value of the '<em><b>Naming Initial Context Factory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Naming Initial Context Factory</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Naming Initial Context Factory</em>' attribute.
	 * @see #setNamingInitialContextFactory(String)
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getNamingEnvironment_NamingInitialContextFactory()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='NamingInitialContextFactory' namespace='##targetNamespace'"
	 * @generated
	 */
	String getNamingInitialContextFactory();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getNamingInitialContextFactory <em>Naming Initial Context Factory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Naming Initial Context Factory</em>' attribute.
	 * @see #getNamingInitialContextFactory()
	 * @generated
	 */
	void setNamingInitialContextFactory(String value);

	/**
	 * Returns the value of the '<em><b>Topic Factory Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Topic Factory Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Topic Factory Name</em>' attribute.
	 * @see #setTopicFactoryName(String)
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getNamingEnvironment_TopicFactoryName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='TopicFactoryName' namespace='##targetNamespace'"
	 * @generated
	 */
	String getTopicFactoryName();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getTopicFactoryName <em>Topic Factory Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Topic Factory Name</em>' attribute.
	 * @see #getTopicFactoryName()
	 * @generated
	 */
	void setTopicFactoryName(String value);

	/**
	 * Returns the value of the '<em><b>Queue Factory Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Queue Factory Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Queue Factory Name</em>' attribute.
	 * @see #setQueueFactoryName(String)
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getNamingEnvironment_QueueFactoryName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='QueueFactoryName' namespace='##targetNamespace'"
	 * @generated
	 */
	String getQueueFactoryName();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getQueueFactoryName <em>Queue Factory Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Queue Factory Name</em>' attribute.
	 * @see #getQueueFactoryName()
	 * @generated
	 */
	void setQueueFactoryName(String value);

	/**
	 * Returns the value of the '<em><b>Naming Principal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Naming Principal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Naming Principal</em>' attribute.
	 * @see #setNamingPrincipal(String)
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getNamingEnvironment_NamingPrincipal()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='NamingPrincipal' namespace='##targetNamespace'"
	 * @generated
	 */
	String getNamingPrincipal();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getNamingPrincipal <em>Naming Principal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Naming Principal</em>' attribute.
	 * @see #getNamingPrincipal()
	 * @generated
	 */
	void setNamingPrincipal(String value);

	/**
	 * Returns the value of the '<em><b>Naming Credential</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Naming Credential</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Naming Credential</em>' attribute.
	 * @see #setNamingCredential(String)
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getNamingEnvironment_NamingCredential()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='NamingCredential' namespace='##targetNamespace'"
	 * @generated
	 */
	String getNamingCredential();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment#getNamingCredential <em>Naming Credential</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Naming Credential</em>' attribute.
	 * @see #getNamingCredential()
	 * @generated
	 */
	void setNamingCredential(String value);

} // NamingEnvironment
