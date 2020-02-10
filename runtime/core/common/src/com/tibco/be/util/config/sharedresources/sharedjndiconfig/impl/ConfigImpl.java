/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl;

import com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config;
import com.tibco.be.util.config.sharedresources.sharedjndiconfig.OptionalJndiProperties;
import com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.ConfigImpl#getResourceType <em>Resource Type</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.ConfigImpl#getResourceType1 <em>Resource Type1</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.ConfigImpl#getProviderUrl <em>Provider Url</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.ConfigImpl#getValidateJndiSecurityContext <em>Validate Jndi Security Context</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.ConfigImpl#getContextFactory <em>Context Factory</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.ConfigImpl#getProviderUrl1 <em>Provider Url1</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.ConfigImpl#getSecurityPrincipal <em>Security Principal</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.ConfigImpl#getSecurityCredentials <em>Security Credentials</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.ConfigImpl#getOptionalJndiProperties <em>Optional Jndi Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConfigImpl extends EObjectImpl implements Config {
	/**
     * The default value of the '{@link #getResourceType() <em>Resource Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResourceType()
     * @generated
     * @ordered
     */
    protected static final String RESOURCE_TYPE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getResourceType() <em>Resource Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResourceType()
     * @generated
     * @ordered
     */
    protected String resourceType = RESOURCE_TYPE_EDEFAULT;

    /**
     * The default value of the '{@link #getResourceType1() <em>Resource Type1</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResourceType1()
     * @generated
     * @ordered
     */
    protected static final String RESOURCE_TYPE1_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getResourceType1() <em>Resource Type1</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResourceType1()
     * @generated
     * @ordered
     */
    protected String resourceType1 = RESOURCE_TYPE1_EDEFAULT;

    /**
     * The default value of the '{@link #getProviderUrl() <em>Provider Url</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getProviderUrl()
     * @generated
     * @ordered
     */
	protected static final String PROVIDER_URL_EDEFAULT = null;

	/**
     * The cached value of the '{@link #getProviderUrl() <em>Provider Url</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getProviderUrl()
     * @generated
     * @ordered
     */
	protected String providerUrl = PROVIDER_URL_EDEFAULT;

    /**
     * The default value of the '{@link #getValidateJndiSecurityContext() <em>Validate Jndi Security Context</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getValidateJndiSecurityContext()
     * @generated
     * @ordered
     */
	protected static final Object VALIDATE_JNDI_SECURITY_CONTEXT_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getValidateJndiSecurityContext() <em>Validate Jndi Security Context</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getValidateJndiSecurityContext()
     * @generated
     * @ordered
     */
	protected Object validateJndiSecurityContext = VALIDATE_JNDI_SECURITY_CONTEXT_EDEFAULT;

    /**
     * The default value of the '{@link #getContextFactory() <em>Context Factory</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getContextFactory()
     * @generated
     * @ordered
     */
	protected static final String CONTEXT_FACTORY_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getContextFactory() <em>Context Factory</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getContextFactory()
     * @generated
     * @ordered
     */
	protected String contextFactory = CONTEXT_FACTORY_EDEFAULT;

    /**
     * The default value of the '{@link #getProviderUrl1() <em>Provider Url1</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProviderUrl1()
     * @generated
     * @ordered
     */
    protected static final String PROVIDER_URL1_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getProviderUrl1() <em>Provider Url1</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProviderUrl1()
     * @generated
     * @ordered
     */
    protected String providerUrl1 = PROVIDER_URL1_EDEFAULT;

	/**
     * The default value of the '{@link #getSecurityPrincipal() <em>Security Principal</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getSecurityPrincipal()
     * @generated
     * @ordered
     */
	protected static final String SECURITY_PRINCIPAL_EDEFAULT = null;

	/**
     * The cached value of the '{@link #getSecurityPrincipal() <em>Security Principal</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getSecurityPrincipal()
     * @generated
     * @ordered
     */
	protected String securityPrincipal = SECURITY_PRINCIPAL_EDEFAULT;

	/**
     * The default value of the '{@link #getSecurityCredentials() <em>Security Credentials</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getSecurityCredentials()
     * @generated
     * @ordered
     */
	protected static final String SECURITY_CREDENTIALS_EDEFAULT = null;

	/**
     * The cached value of the '{@link #getSecurityCredentials() <em>Security Credentials</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getSecurityCredentials()
     * @generated
     * @ordered
     */
	protected String securityCredentials = SECURITY_CREDENTIALS_EDEFAULT;

	/**
     * The cached value of the '{@link #getOptionalJndiProperties() <em>Optional Jndi Properties</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOptionalJndiProperties()
     * @generated
     * @ordered
     */
    protected OptionalJndiProperties optionalJndiProperties;

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected ConfigImpl() {
        super();
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	protected EClass eStaticClass() {
        return SharedjndiconfigPackage.Literals.CONFIG;
    }

	/**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getResourceType()
    {
        return resourceType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setResourceType(String newResourceType)
    {
        String oldResourceType = resourceType;
        resourceType = newResourceType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, SharedjndiconfigPackage.CONFIG__RESOURCE_TYPE, oldResourceType, resourceType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getResourceType1()
    {
        return resourceType1;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setResourceType1(String newResourceType1)
    {
        String oldResourceType1 = resourceType1;
        resourceType1 = newResourceType1;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, SharedjndiconfigPackage.CONFIG__RESOURCE_TYPE1, oldResourceType1, resourceType1));
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public Object getValidateJndiSecurityContext() {
        return validateJndiSecurityContext;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setValidateJndiSecurityContext(Object newValidateJndiSecurityContext) {
        Object oldValidateJndiSecurityContext = validateJndiSecurityContext;
        validateJndiSecurityContext = newValidateJndiSecurityContext;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, SharedjndiconfigPackage.CONFIG__VALIDATE_JNDI_SECURITY_CONTEXT, oldValidateJndiSecurityContext, validateJndiSecurityContext));
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public String getContextFactory() {
        return contextFactory;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setContextFactory(String newContextFactory) {
        String oldContextFactory = contextFactory;
        contextFactory = newContextFactory;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, SharedjndiconfigPackage.CONFIG__CONTEXT_FACTORY, oldContextFactory, contextFactory));
    }

	/**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getProviderUrl1()
    {
        return providerUrl1;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setProviderUrl1(String newProviderUrl1)
    {
        String oldProviderUrl1 = providerUrl1;
        providerUrl1 = newProviderUrl1;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, SharedjndiconfigPackage.CONFIG__PROVIDER_URL1, oldProviderUrl1, providerUrl1));
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public String getProviderUrl() {
        return providerUrl;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setProviderUrl(String newProviderUrl) {
        String oldProviderUrl = providerUrl;
        providerUrl = newProviderUrl;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, SharedjndiconfigPackage.CONFIG__PROVIDER_URL, oldProviderUrl, providerUrl));
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public String getSecurityPrincipal() {
        return securityPrincipal;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setSecurityPrincipal(String newSecurityPrincipal) {
        String oldSecurityPrincipal = securityPrincipal;
        securityPrincipal = newSecurityPrincipal;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, SharedjndiconfigPackage.CONFIG__SECURITY_PRINCIPAL, oldSecurityPrincipal, securityPrincipal));
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public String getSecurityCredentials() {
        return securityCredentials;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setSecurityCredentials(String newSecurityCredentials) {
        String oldSecurityCredentials = securityCredentials;
        securityCredentials = newSecurityCredentials;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, SharedjndiconfigPackage.CONFIG__SECURITY_CREDENTIALS, oldSecurityCredentials, securityCredentials));
    }

	/**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OptionalJndiProperties getOptionalJndiProperties()
    {
        return optionalJndiProperties;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetOptionalJndiProperties(OptionalJndiProperties newOptionalJndiProperties, NotificationChain msgs)
    {
        OptionalJndiProperties oldOptionalJndiProperties = optionalJndiProperties;
        optionalJndiProperties = newOptionalJndiProperties;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SharedjndiconfigPackage.CONFIG__OPTIONAL_JNDI_PROPERTIES, oldOptionalJndiProperties, newOptionalJndiProperties);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOptionalJndiProperties(OptionalJndiProperties newOptionalJndiProperties)
    {
        if (newOptionalJndiProperties != optionalJndiProperties)
        {
            NotificationChain msgs = null;
            if (optionalJndiProperties != null)
                msgs = ((InternalEObject)optionalJndiProperties).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SharedjndiconfigPackage.CONFIG__OPTIONAL_JNDI_PROPERTIES, null, msgs);
            if (newOptionalJndiProperties != null)
                msgs = ((InternalEObject)newOptionalJndiProperties).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SharedjndiconfigPackage.CONFIG__OPTIONAL_JNDI_PROPERTIES, null, msgs);
            msgs = basicSetOptionalJndiProperties(newOptionalJndiProperties, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, SharedjndiconfigPackage.CONFIG__OPTIONAL_JNDI_PROPERTIES, newOptionalJndiProperties, newOptionalJndiProperties));
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID)
        {
            case SharedjndiconfigPackage.CONFIG__OPTIONAL_JNDI_PROPERTIES:
                return basicSetOptionalJndiProperties(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID)
        {
            case SharedjndiconfigPackage.CONFIG__RESOURCE_TYPE:
                return getResourceType();
            case SharedjndiconfigPackage.CONFIG__RESOURCE_TYPE1:
                return getResourceType1();
            case SharedjndiconfigPackage.CONFIG__PROVIDER_URL:
                return getProviderUrl();
            case SharedjndiconfigPackage.CONFIG__VALIDATE_JNDI_SECURITY_CONTEXT:
                return getValidateJndiSecurityContext();
            case SharedjndiconfigPackage.CONFIG__CONTEXT_FACTORY:
                return getContextFactory();
            case SharedjndiconfigPackage.CONFIG__PROVIDER_URL1:
                return getProviderUrl1();
            case SharedjndiconfigPackage.CONFIG__SECURITY_PRINCIPAL:
                return getSecurityPrincipal();
            case SharedjndiconfigPackage.CONFIG__SECURITY_CREDENTIALS:
                return getSecurityCredentials();
            case SharedjndiconfigPackage.CONFIG__OPTIONAL_JNDI_PROPERTIES:
                return getOptionalJndiProperties();
        }
        return super.eGet(featureID, resolve, coreType);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public void eSet(int featureID, Object newValue) {
        switch (featureID)
        {
            case SharedjndiconfigPackage.CONFIG__RESOURCE_TYPE:
                setResourceType((String)newValue);
                return;
            case SharedjndiconfigPackage.CONFIG__RESOURCE_TYPE1:
                setResourceType1((String)newValue);
                return;
            case SharedjndiconfigPackage.CONFIG__PROVIDER_URL:
                setProviderUrl((String)newValue);
                return;
            case SharedjndiconfigPackage.CONFIG__VALIDATE_JNDI_SECURITY_CONTEXT:
                setValidateJndiSecurityContext(newValue);
                return;
            case SharedjndiconfigPackage.CONFIG__CONTEXT_FACTORY:
                setContextFactory((String)newValue);
                return;
            case SharedjndiconfigPackage.CONFIG__PROVIDER_URL1:
                setProviderUrl1((String)newValue);
                return;
            case SharedjndiconfigPackage.CONFIG__SECURITY_PRINCIPAL:
                setSecurityPrincipal((String)newValue);
                return;
            case SharedjndiconfigPackage.CONFIG__SECURITY_CREDENTIALS:
                setSecurityCredentials((String)newValue);
                return;
            case SharedjndiconfigPackage.CONFIG__OPTIONAL_JNDI_PROPERTIES:
                setOptionalJndiProperties((OptionalJndiProperties)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public void eUnset(int featureID) {
        switch (featureID)
        {
            case SharedjndiconfigPackage.CONFIG__RESOURCE_TYPE:
                setResourceType(RESOURCE_TYPE_EDEFAULT);
                return;
            case SharedjndiconfigPackage.CONFIG__RESOURCE_TYPE1:
                setResourceType1(RESOURCE_TYPE1_EDEFAULT);
                return;
            case SharedjndiconfigPackage.CONFIG__PROVIDER_URL:
                setProviderUrl(PROVIDER_URL_EDEFAULT);
                return;
            case SharedjndiconfigPackage.CONFIG__VALIDATE_JNDI_SECURITY_CONTEXT:
                setValidateJndiSecurityContext(VALIDATE_JNDI_SECURITY_CONTEXT_EDEFAULT);
                return;
            case SharedjndiconfigPackage.CONFIG__CONTEXT_FACTORY:
                setContextFactory(CONTEXT_FACTORY_EDEFAULT);
                return;
            case SharedjndiconfigPackage.CONFIG__PROVIDER_URL1:
                setProviderUrl1(PROVIDER_URL1_EDEFAULT);
                return;
            case SharedjndiconfigPackage.CONFIG__SECURITY_PRINCIPAL:
                setSecurityPrincipal(SECURITY_PRINCIPAL_EDEFAULT);
                return;
            case SharedjndiconfigPackage.CONFIG__SECURITY_CREDENTIALS:
                setSecurityCredentials(SECURITY_CREDENTIALS_EDEFAULT);
                return;
            case SharedjndiconfigPackage.CONFIG__OPTIONAL_JNDI_PROPERTIES:
                setOptionalJndiProperties((OptionalJndiProperties)null);
                return;
        }
        super.eUnset(featureID);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public boolean eIsSet(int featureID) {
        switch (featureID)
        {
            case SharedjndiconfigPackage.CONFIG__RESOURCE_TYPE:
                return RESOURCE_TYPE_EDEFAULT == null ? resourceType != null : !RESOURCE_TYPE_EDEFAULT.equals(resourceType);
            case SharedjndiconfigPackage.CONFIG__RESOURCE_TYPE1:
                return RESOURCE_TYPE1_EDEFAULT == null ? resourceType1 != null : !RESOURCE_TYPE1_EDEFAULT.equals(resourceType1);
            case SharedjndiconfigPackage.CONFIG__PROVIDER_URL:
                return PROVIDER_URL_EDEFAULT == null ? providerUrl != null : !PROVIDER_URL_EDEFAULT.equals(providerUrl);
            case SharedjndiconfigPackage.CONFIG__VALIDATE_JNDI_SECURITY_CONTEXT:
                return VALIDATE_JNDI_SECURITY_CONTEXT_EDEFAULT == null ? validateJndiSecurityContext != null : !VALIDATE_JNDI_SECURITY_CONTEXT_EDEFAULT.equals(validateJndiSecurityContext);
            case SharedjndiconfigPackage.CONFIG__CONTEXT_FACTORY:
                return CONTEXT_FACTORY_EDEFAULT == null ? contextFactory != null : !CONTEXT_FACTORY_EDEFAULT.equals(contextFactory);
            case SharedjndiconfigPackage.CONFIG__PROVIDER_URL1:
                return PROVIDER_URL1_EDEFAULT == null ? providerUrl1 != null : !PROVIDER_URL1_EDEFAULT.equals(providerUrl1);
            case SharedjndiconfigPackage.CONFIG__SECURITY_PRINCIPAL:
                return SECURITY_PRINCIPAL_EDEFAULT == null ? securityPrincipal != null : !SECURITY_PRINCIPAL_EDEFAULT.equals(securityPrincipal);
            case SharedjndiconfigPackage.CONFIG__SECURITY_CREDENTIALS:
                return SECURITY_CREDENTIALS_EDEFAULT == null ? securityCredentials != null : !SECURITY_CREDENTIALS_EDEFAULT.equals(securityCredentials);
            case SharedjndiconfigPackage.CONFIG__OPTIONAL_JNDI_PROPERTIES:
                return optionalJndiProperties != null;
        }
        return super.eIsSet(featureID);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (resourceType: ");
        result.append(resourceType);
        result.append(", resourceType1: ");
        result.append(resourceType1);
        result.append(", providerUrl: ");
        result.append(providerUrl);
        result.append(", validateJndiSecurityContext: ");
        result.append(validateJndiSecurityContext);
        result.append(", contextFactory: ");
        result.append(contextFactory);
        result.append(", providerUrl1: ");
        result.append(providerUrl1);
        result.append(", securityPrincipal: ");
        result.append(securityPrincipal);
        result.append(", securityCredentials: ");
        result.append(securityCredentials);
        result.append(')');
        return result.toString();
    }

} //ConfigImpl
