/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedjmscon.impl;

import com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment;
import com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Naming Environment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.NamingEnvironmentImpl#getUseJndi <em>Use Jndi</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.NamingEnvironmentImpl#getProviderUrl <em>Provider Url</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.NamingEnvironmentImpl#getNamingUrl <em>Naming Url</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.NamingEnvironmentImpl#getNamingInitialContextFactory <em>Naming Initial Context Factory</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.NamingEnvironmentImpl#getTopicFactoryName <em>Topic Factory Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.NamingEnvironmentImpl#getQueueFactoryName <em>Queue Factory Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.NamingEnvironmentImpl#getNamingPrincipal <em>Naming Principal</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.NamingEnvironmentImpl#getNamingCredential <em>Naming Credential</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NamingEnvironmentImpl extends EObjectImpl implements NamingEnvironment {
	/**
	 * The default value of the '{@link #getUseJndi() <em>Use Jndi</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUseJndi()
	 * @generated
	 * @ordered
	 */
	protected static final Object USE_JNDI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUseJndi() <em>Use Jndi</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUseJndi()
	 * @generated
	 * @ordered
	 */
	protected Object useJndi = USE_JNDI_EDEFAULT;

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
	 * The default value of the '{@link #getNamingUrl() <em>Naming Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamingUrl()
	 * @generated
	 * @ordered
	 */
	protected static final String NAMING_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNamingUrl() <em>Naming Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamingUrl()
	 * @generated
	 * @ordered
	 */
	protected String namingUrl = NAMING_URL_EDEFAULT;

	/**
	 * The default value of the '{@link #getNamingInitialContextFactory() <em>Naming Initial Context Factory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamingInitialContextFactory()
	 * @generated
	 * @ordered
	 */
	protected static final String NAMING_INITIAL_CONTEXT_FACTORY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNamingInitialContextFactory() <em>Naming Initial Context Factory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamingInitialContextFactory()
	 * @generated
	 * @ordered
	 */
	protected String namingInitialContextFactory = NAMING_INITIAL_CONTEXT_FACTORY_EDEFAULT;

	/**
	 * The default value of the '{@link #getTopicFactoryName() <em>Topic Factory Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTopicFactoryName()
	 * @generated
	 * @ordered
	 */
	protected static final String TOPIC_FACTORY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTopicFactoryName() <em>Topic Factory Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTopicFactoryName()
	 * @generated
	 * @ordered
	 */
	protected String topicFactoryName = TOPIC_FACTORY_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getQueueFactoryName() <em>Queue Factory Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueueFactoryName()
	 * @generated
	 * @ordered
	 */
	protected static final String QUEUE_FACTORY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getQueueFactoryName() <em>Queue Factory Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueueFactoryName()
	 * @generated
	 * @ordered
	 */
	protected String queueFactoryName = QUEUE_FACTORY_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getNamingPrincipal() <em>Naming Principal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamingPrincipal()
	 * @generated
	 * @ordered
	 */
	protected static final String NAMING_PRINCIPAL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNamingPrincipal() <em>Naming Principal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamingPrincipal()
	 * @generated
	 * @ordered
	 */
	protected String namingPrincipal = NAMING_PRINCIPAL_EDEFAULT;

	/**
	 * The default value of the '{@link #getNamingCredential() <em>Naming Credential</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamingCredential()
	 * @generated
	 * @ordered
	 */
	protected static final String NAMING_CREDENTIAL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNamingCredential() <em>Naming Credential</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamingCredential()
	 * @generated
	 * @ordered
	 */
	protected String namingCredential = NAMING_CREDENTIAL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NamingEnvironmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SharedjmsconPackage.Literals.NAMING_ENVIRONMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getUseJndi() {
		return useJndi;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUseJndi(Object newUseJndi) {
		Object oldUseJndi = useJndi;
		useJndi = newUseJndi;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.NAMING_ENVIRONMENT__USE_JNDI, oldUseJndi, useJndi));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.NAMING_ENVIRONMENT__PROVIDER_URL, oldProviderUrl, providerUrl));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNamingUrl() {
		return namingUrl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNamingUrl(String newNamingUrl) {
		String oldNamingUrl = namingUrl;
		namingUrl = newNamingUrl;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.NAMING_ENVIRONMENT__NAMING_URL, oldNamingUrl, namingUrl));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNamingInitialContextFactory() {
		return namingInitialContextFactory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNamingInitialContextFactory(String newNamingInitialContextFactory) {
		String oldNamingInitialContextFactory = namingInitialContextFactory;
		namingInitialContextFactory = newNamingInitialContextFactory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.NAMING_ENVIRONMENT__NAMING_INITIAL_CONTEXT_FACTORY, oldNamingInitialContextFactory, namingInitialContextFactory));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTopicFactoryName() {
		return topicFactoryName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTopicFactoryName(String newTopicFactoryName) {
		String oldTopicFactoryName = topicFactoryName;
		topicFactoryName = newTopicFactoryName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.NAMING_ENVIRONMENT__TOPIC_FACTORY_NAME, oldTopicFactoryName, topicFactoryName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getQueueFactoryName() {
		return queueFactoryName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQueueFactoryName(String newQueueFactoryName) {
		String oldQueueFactoryName = queueFactoryName;
		queueFactoryName = newQueueFactoryName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.NAMING_ENVIRONMENT__QUEUE_FACTORY_NAME, oldQueueFactoryName, queueFactoryName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNamingPrincipal() {
		return namingPrincipal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNamingPrincipal(String newNamingPrincipal) {
		String oldNamingPrincipal = namingPrincipal;
		namingPrincipal = newNamingPrincipal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.NAMING_ENVIRONMENT__NAMING_PRINCIPAL, oldNamingPrincipal, namingPrincipal));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNamingCredential() {
		return namingCredential;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNamingCredential(String newNamingCredential) {
		String oldNamingCredential = namingCredential;
		namingCredential = newNamingCredential;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.NAMING_ENVIRONMENT__NAMING_CREDENTIAL, oldNamingCredential, namingCredential));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SharedjmsconPackage.NAMING_ENVIRONMENT__USE_JNDI:
				return getUseJndi();
			case SharedjmsconPackage.NAMING_ENVIRONMENT__PROVIDER_URL:
				return getProviderUrl();
			case SharedjmsconPackage.NAMING_ENVIRONMENT__NAMING_URL:
				return getNamingUrl();
			case SharedjmsconPackage.NAMING_ENVIRONMENT__NAMING_INITIAL_CONTEXT_FACTORY:
				return getNamingInitialContextFactory();
			case SharedjmsconPackage.NAMING_ENVIRONMENT__TOPIC_FACTORY_NAME:
				return getTopicFactoryName();
			case SharedjmsconPackage.NAMING_ENVIRONMENT__QUEUE_FACTORY_NAME:
				return getQueueFactoryName();
			case SharedjmsconPackage.NAMING_ENVIRONMENT__NAMING_PRINCIPAL:
				return getNamingPrincipal();
			case SharedjmsconPackage.NAMING_ENVIRONMENT__NAMING_CREDENTIAL:
				return getNamingCredential();
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
		switch (featureID) {
			case SharedjmsconPackage.NAMING_ENVIRONMENT__USE_JNDI:
				setUseJndi(newValue);
				return;
			case SharedjmsconPackage.NAMING_ENVIRONMENT__PROVIDER_URL:
				setProviderUrl((String)newValue);
				return;
			case SharedjmsconPackage.NAMING_ENVIRONMENT__NAMING_URL:
				setNamingUrl((String)newValue);
				return;
			case SharedjmsconPackage.NAMING_ENVIRONMENT__NAMING_INITIAL_CONTEXT_FACTORY:
				setNamingInitialContextFactory((String)newValue);
				return;
			case SharedjmsconPackage.NAMING_ENVIRONMENT__TOPIC_FACTORY_NAME:
				setTopicFactoryName((String)newValue);
				return;
			case SharedjmsconPackage.NAMING_ENVIRONMENT__QUEUE_FACTORY_NAME:
				setQueueFactoryName((String)newValue);
				return;
			case SharedjmsconPackage.NAMING_ENVIRONMENT__NAMING_PRINCIPAL:
				setNamingPrincipal((String)newValue);
				return;
			case SharedjmsconPackage.NAMING_ENVIRONMENT__NAMING_CREDENTIAL:
				setNamingCredential((String)newValue);
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
		switch (featureID) {
			case SharedjmsconPackage.NAMING_ENVIRONMENT__USE_JNDI:
				setUseJndi(USE_JNDI_EDEFAULT);
				return;
			case SharedjmsconPackage.NAMING_ENVIRONMENT__PROVIDER_URL:
				setProviderUrl(PROVIDER_URL_EDEFAULT);
				return;
			case SharedjmsconPackage.NAMING_ENVIRONMENT__NAMING_URL:
				setNamingUrl(NAMING_URL_EDEFAULT);
				return;
			case SharedjmsconPackage.NAMING_ENVIRONMENT__NAMING_INITIAL_CONTEXT_FACTORY:
				setNamingInitialContextFactory(NAMING_INITIAL_CONTEXT_FACTORY_EDEFAULT);
				return;
			case SharedjmsconPackage.NAMING_ENVIRONMENT__TOPIC_FACTORY_NAME:
				setTopicFactoryName(TOPIC_FACTORY_NAME_EDEFAULT);
				return;
			case SharedjmsconPackage.NAMING_ENVIRONMENT__QUEUE_FACTORY_NAME:
				setQueueFactoryName(QUEUE_FACTORY_NAME_EDEFAULT);
				return;
			case SharedjmsconPackage.NAMING_ENVIRONMENT__NAMING_PRINCIPAL:
				setNamingPrincipal(NAMING_PRINCIPAL_EDEFAULT);
				return;
			case SharedjmsconPackage.NAMING_ENVIRONMENT__NAMING_CREDENTIAL:
				setNamingCredential(NAMING_CREDENTIAL_EDEFAULT);
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
		switch (featureID) {
			case SharedjmsconPackage.NAMING_ENVIRONMENT__USE_JNDI:
				return USE_JNDI_EDEFAULT == null ? useJndi != null : !USE_JNDI_EDEFAULT.equals(useJndi);
			case SharedjmsconPackage.NAMING_ENVIRONMENT__PROVIDER_URL:
				return PROVIDER_URL_EDEFAULT == null ? providerUrl != null : !PROVIDER_URL_EDEFAULT.equals(providerUrl);
			case SharedjmsconPackage.NAMING_ENVIRONMENT__NAMING_URL:
				return NAMING_URL_EDEFAULT == null ? namingUrl != null : !NAMING_URL_EDEFAULT.equals(namingUrl);
			case SharedjmsconPackage.NAMING_ENVIRONMENT__NAMING_INITIAL_CONTEXT_FACTORY:
				return NAMING_INITIAL_CONTEXT_FACTORY_EDEFAULT == null ? namingInitialContextFactory != null : !NAMING_INITIAL_CONTEXT_FACTORY_EDEFAULT.equals(namingInitialContextFactory);
			case SharedjmsconPackage.NAMING_ENVIRONMENT__TOPIC_FACTORY_NAME:
				return TOPIC_FACTORY_NAME_EDEFAULT == null ? topicFactoryName != null : !TOPIC_FACTORY_NAME_EDEFAULT.equals(topicFactoryName);
			case SharedjmsconPackage.NAMING_ENVIRONMENT__QUEUE_FACTORY_NAME:
				return QUEUE_FACTORY_NAME_EDEFAULT == null ? queueFactoryName != null : !QUEUE_FACTORY_NAME_EDEFAULT.equals(queueFactoryName);
			case SharedjmsconPackage.NAMING_ENVIRONMENT__NAMING_PRINCIPAL:
				return NAMING_PRINCIPAL_EDEFAULT == null ? namingPrincipal != null : !NAMING_PRINCIPAL_EDEFAULT.equals(namingPrincipal);
			case SharedjmsconPackage.NAMING_ENVIRONMENT__NAMING_CREDENTIAL:
				return NAMING_CREDENTIAL_EDEFAULT == null ? namingCredential != null : !NAMING_CREDENTIAL_EDEFAULT.equals(namingCredential);
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
		result.append(" (useJndi: ");
		result.append(useJndi);
		result.append(", providerUrl: ");
		result.append(providerUrl);
		result.append(", namingUrl: ");
		result.append(namingUrl);
		result.append(", namingInitialContextFactory: ");
		result.append(namingInitialContextFactory);
		result.append(", topicFactoryName: ");
		result.append(topicFactoryName);
		result.append(", queueFactoryName: ");
		result.append(queueFactoryName);
		result.append(", namingPrincipal: ");
		result.append(namingPrincipal);
		result.append(", namingCredential: ");
		result.append(namingCredential);
		result.append(')');
		return result.toString();
	}

} //NamingEnvironmentImpl
