/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedjndiconfig;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getResourceType <em>Resource Type</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getResourceType1 <em>Resource Type1</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getProviderUrl <em>Provider Url</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getValidateJndiSecurityContext <em>Validate Jndi Security Context</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getContextFactory <em>Context Factory</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getProviderUrl1 <em>Provider Url1</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getSecurityPrincipal <em>Security Principal</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getSecurityCredentials <em>Security Credentials</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getOptionalJndiProperties <em>Optional Jndi Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigPackage#getConfig()
 * @model extendedMetaData="name='Config-type' kind='elementOnly'"
 * @generated
 */
public interface Config extends EObject {
	/**
     * Returns the value of the '<em><b>Resource Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resource Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Resource Type</em>' attribute.
     * @see #setResourceType(String)
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigPackage#getConfig_ResourceType()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='resourceType' namespace='##targetNamespace'"
     * @generated
     */
    String getResourceType();

    /**
     * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getResourceType <em>Resource Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Resource Type</em>' attribute.
     * @see #getResourceType()
     * @generated
     */
    void setResourceType(String value);

    /**
     * Returns the value of the '<em><b>Resource Type1</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resource Type1</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Resource Type1</em>' attribute.
     * @see #setResourceType1(String)
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigPackage#getConfig_ResourceType1()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='description' namespace='##targetNamespace'"
     * @generated
     */
    String getResourceType1();

    /**
     * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getResourceType1 <em>Resource Type1</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Resource Type1</em>' attribute.
     * @see #getResourceType1()
     * @generated
     */
    void setResourceType1(String value);

    /**
     * Returns the value of the '<em><b>Validate Jndi Security Context</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Validate Jndi Security Context</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>Validate Jndi Security Context</em>' attribute.
     * @see #setValidateJndiSecurityContext(Object)
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigPackage#getConfig_ValidateJndiSecurityContext()
     * @model dataType="com.tibco.be.util.config.sharedresources.basetypes.BooleanOrGvs" required="true"
     *        extendedMetaData="kind='element' name='ValidateJndiSecurityContext' namespace='##targetNamespace'"
     * @generated
     */
	Object getValidateJndiSecurityContext();

	/**
     * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getValidateJndiSecurityContext <em>Validate Jndi Security Context</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Validate Jndi Security Context</em>' attribute.
     * @see #getValidateJndiSecurityContext()
     * @generated
     */
	void setValidateJndiSecurityContext(Object value);

	/**
     * Returns the value of the '<em><b>Context Factory</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Context Factory</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>Context Factory</em>' attribute.
     * @see #setContextFactory(String)
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigPackage#getConfig_ContextFactory()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='ContextFactory' namespace='##targetNamespace'"
     * @generated
     */
	String getContextFactory();

	/**
     * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getContextFactory <em>Context Factory</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Context Factory</em>' attribute.
     * @see #getContextFactory()
     * @generated
     */
	void setContextFactory(String value);

	/**
     * Returns the value of the '<em><b>Provider Url1</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Provider Url1</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Provider Url1</em>' attribute.
     * @see #setProviderUrl1(String)
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigPackage#getConfig_ProviderUrl1()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='ProviderUrl' namespace='##targetNamespace'"
     * @generated
     */
    String getProviderUrl1();

    /**
     * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getProviderUrl1 <em>Provider Url1</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Provider Url1</em>' attribute.
     * @see #getProviderUrl1()
     * @generated
     */
    void setProviderUrl1(String value);

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
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigPackage#getConfig_ProviderUrl()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='ProviderUrl' namespace='##targetNamespace'"
     * @generated
     */
	String getProviderUrl();

	/**
     * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getProviderUrl <em>Provider Url</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Provider Url</em>' attribute.
     * @see #getProviderUrl()
     * @generated
     */
	void setProviderUrl(String value);

	/**
     * Returns the value of the '<em><b>Security Principal</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Security Principal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>Security Principal</em>' attribute.
     * @see #setSecurityPrincipal(String)
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigPackage#getConfig_SecurityPrincipal()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='SecurityPrincipal' namespace='##targetNamespace'"
     * @generated
     */
	String getSecurityPrincipal();

	/**
     * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getSecurityPrincipal <em>Security Principal</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Security Principal</em>' attribute.
     * @see #getSecurityPrincipal()
     * @generated
     */
	void setSecurityPrincipal(String value);

	/**
     * Returns the value of the '<em><b>Security Credentials</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Security Credentials</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>Security Credentials</em>' attribute.
     * @see #setSecurityCredentials(String)
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigPackage#getConfig_SecurityCredentials()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='SecurityCredentials' namespace='##targetNamespace'"
     * @generated
     */
	String getSecurityCredentials();

	/**
     * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getSecurityCredentials <em>Security Credentials</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Security Credentials</em>' attribute.
     * @see #getSecurityCredentials()
     * @generated
     */
	void setSecurityCredentials(String value);

	/**
     * Returns the value of the '<em><b>Optional Jndi Properties</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Optional Jndi Properties</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Optional Jndi Properties</em>' containment reference.
     * @see #setOptionalJndiProperties(OptionalJndiProperties)
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigPackage#getConfig_OptionalJndiProperties()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='OptionalJNDIProperties' namespace='##targetNamespace'"
     * @generated
     */
    OptionalJndiProperties getOptionalJndiProperties();

    /**
     * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config#getOptionalJndiProperties <em>Optional Jndi Properties</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Optional Jndi Properties</em>' containment reference.
     * @see #getOptionalJndiProperties()
     * @generated
     */
    void setOptionalJndiProperties(OptionalJndiProperties value);

} // Config
