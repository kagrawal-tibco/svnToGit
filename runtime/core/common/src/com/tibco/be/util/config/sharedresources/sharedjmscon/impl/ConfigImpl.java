/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedjmscon.impl;

import com.tibco.be.util.config.sharedresources.aemetaservices.Ssl;

import com.tibco.be.util.config.sharedresources.sharedjmscon.Config;
import com.tibco.be.util.config.sharedresources.sharedjmscon.ConnectionAttributes;
import com.tibco.be.util.config.sharedresources.sharedjmscon.JndiProperties;
import com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment;
import com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage;

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
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.ConfigImpl#getNamingEnvironment <em>Naming Environment</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.ConfigImpl#getConnectionAttributes <em>Connection Attributes</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.ConfigImpl#getUseXacf <em>Use Xacf</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.ConfigImpl#getUseSsl <em>Use Ssl</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.ConfigImpl#getUseSharedJndiConfig <em>Use Shared Jndi Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.ConfigImpl#getAdmFactorySslPassword <em>Adm Factory Ssl Password</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.ConfigImpl#getSsl <em>Ssl</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.ConfigImpl#getJndiSharedConfiguration <em>Jndi Shared Configuration</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.ConfigImpl#getJndiProperties <em>Jndi Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConfigImpl extends EObjectImpl implements Config {
	/**
	 * The cached value of the '{@link #getNamingEnvironment() <em>Naming Environment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamingEnvironment()
	 * @generated
	 * @ordered
	 */
	protected NamingEnvironment namingEnvironment;

	/**
	 * The cached value of the '{@link #getConnectionAttributes() <em>Connection Attributes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnectionAttributes()
	 * @generated
	 * @ordered
	 */
	protected ConnectionAttributes connectionAttributes;

	/**
	 * The default value of the '{@link #getUseXacf() <em>Use Xacf</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUseXacf()
	 * @generated
	 * @ordered
	 */
	protected static final Object USE_XACF_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUseXacf() <em>Use Xacf</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUseXacf()
	 * @generated
	 * @ordered
	 */
	protected Object useXacf = USE_XACF_EDEFAULT;

	/**
	 * The default value of the '{@link #getUseSsl() <em>Use Ssl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUseSsl()
	 * @generated
	 * @ordered
	 */
	protected static final Object USE_SSL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUseSsl() <em>Use Ssl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUseSsl()
	 * @generated
	 * @ordered
	 */
	protected Object useSsl = USE_SSL_EDEFAULT;

	/**
	 * The default value of the '{@link #getUseSharedJndiConfig() <em>Use Shared Jndi Config</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUseSharedJndiConfig()
	 * @generated
	 * @ordered
	 */
	protected static final Object USE_SHARED_JNDI_CONFIG_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUseSharedJndiConfig() <em>Use Shared Jndi Config</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUseSharedJndiConfig()
	 * @generated
	 * @ordered
	 */
	protected Object useSharedJndiConfig = USE_SHARED_JNDI_CONFIG_EDEFAULT;

	/**
	 * The default value of the '{@link #getAdmFactorySslPassword() <em>Adm Factory Ssl Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdmFactorySslPassword()
	 * @generated
	 * @ordered
	 */
	protected static final String ADM_FACTORY_SSL_PASSWORD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAdmFactorySslPassword() <em>Adm Factory Ssl Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdmFactorySslPassword()
	 * @generated
	 * @ordered
	 */
	protected String admFactorySslPassword = ADM_FACTORY_SSL_PASSWORD_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSsl() <em>Ssl</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSsl()
	 * @generated
	 * @ordered
	 */
	protected Ssl ssl;

	/**
	 * The default value of the '{@link #getJndiSharedConfiguration() <em>Jndi Shared Configuration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJndiSharedConfiguration()
	 * @generated
	 * @ordered
	 */
	protected static final String JNDI_SHARED_CONFIGURATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getJndiSharedConfiguration() <em>Jndi Shared Configuration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJndiSharedConfiguration()
	 * @generated
	 * @ordered
	 */
	protected String jndiSharedConfiguration = JNDI_SHARED_CONFIGURATION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getJndiProperties() <em>Jndi Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJndiProperties()
	 * @generated
	 * @ordered
	 */
	protected JndiProperties jndiProperties;

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
		return SharedjmsconPackage.Literals.CONFIG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamingEnvironment getNamingEnvironment() {
		return namingEnvironment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNamingEnvironment(NamingEnvironment newNamingEnvironment, NotificationChain msgs) {
		NamingEnvironment oldNamingEnvironment = namingEnvironment;
		namingEnvironment = newNamingEnvironment;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.CONFIG__NAMING_ENVIRONMENT, oldNamingEnvironment, newNamingEnvironment);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNamingEnvironment(NamingEnvironment newNamingEnvironment) {
		if (newNamingEnvironment != namingEnvironment) {
			NotificationChain msgs = null;
			if (namingEnvironment != null)
				msgs = ((InternalEObject)namingEnvironment).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SharedjmsconPackage.CONFIG__NAMING_ENVIRONMENT, null, msgs);
			if (newNamingEnvironment != null)
				msgs = ((InternalEObject)newNamingEnvironment).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SharedjmsconPackage.CONFIG__NAMING_ENVIRONMENT, null, msgs);
			msgs = basicSetNamingEnvironment(newNamingEnvironment, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.CONFIG__NAMING_ENVIRONMENT, newNamingEnvironment, newNamingEnvironment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectionAttributes getConnectionAttributes() {
		return connectionAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConnectionAttributes(ConnectionAttributes newConnectionAttributes, NotificationChain msgs) {
		ConnectionAttributes oldConnectionAttributes = connectionAttributes;
		connectionAttributes = newConnectionAttributes;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.CONFIG__CONNECTION_ATTRIBUTES, oldConnectionAttributes, newConnectionAttributes);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConnectionAttributes(ConnectionAttributes newConnectionAttributes) {
		if (newConnectionAttributes != connectionAttributes) {
			NotificationChain msgs = null;
			if (connectionAttributes != null)
				msgs = ((InternalEObject)connectionAttributes).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SharedjmsconPackage.CONFIG__CONNECTION_ATTRIBUTES, null, msgs);
			if (newConnectionAttributes != null)
				msgs = ((InternalEObject)newConnectionAttributes).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SharedjmsconPackage.CONFIG__CONNECTION_ATTRIBUTES, null, msgs);
			msgs = basicSetConnectionAttributes(newConnectionAttributes, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.CONFIG__CONNECTION_ATTRIBUTES, newConnectionAttributes, newConnectionAttributes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getUseXacf() {
		return useXacf;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUseXacf(Object newUseXacf) {
		Object oldUseXacf = useXacf;
		useXacf = newUseXacf;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.CONFIG__USE_XACF, oldUseXacf, useXacf));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getUseSsl() {
		return useSsl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUseSsl(Object newUseSsl) {
		Object oldUseSsl = useSsl;
		useSsl = newUseSsl;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.CONFIG__USE_SSL, oldUseSsl, useSsl));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getUseSharedJndiConfig() {
		return useSharedJndiConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUseSharedJndiConfig(Object newUseSharedJndiConfig) {
		Object oldUseSharedJndiConfig = useSharedJndiConfig;
		useSharedJndiConfig = newUseSharedJndiConfig;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.CONFIG__USE_SHARED_JNDI_CONFIG, oldUseSharedJndiConfig, useSharedJndiConfig));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAdmFactorySslPassword() {
		return admFactorySslPassword;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAdmFactorySslPassword(String newAdmFactorySslPassword) {
		String oldAdmFactorySslPassword = admFactorySslPassword;
		admFactorySslPassword = newAdmFactorySslPassword;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.CONFIG__ADM_FACTORY_SSL_PASSWORD, oldAdmFactorySslPassword, admFactorySslPassword));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Ssl getSsl() {
		return ssl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSsl(Ssl newSsl, NotificationChain msgs) {
		Ssl oldSsl = ssl;
		ssl = newSsl;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.CONFIG__SSL, oldSsl, newSsl);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSsl(Ssl newSsl) {
		if (newSsl != ssl) {
			NotificationChain msgs = null;
			if (ssl != null)
				msgs = ((InternalEObject)ssl).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SharedjmsconPackage.CONFIG__SSL, null, msgs);
			if (newSsl != null)
				msgs = ((InternalEObject)newSsl).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SharedjmsconPackage.CONFIG__SSL, null, msgs);
			msgs = basicSetSsl(newSsl, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.CONFIG__SSL, newSsl, newSsl));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getJndiSharedConfiguration() {
		return jndiSharedConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJndiSharedConfiguration(String newJndiSharedConfiguration) {
		String oldJndiSharedConfiguration = jndiSharedConfiguration;
		jndiSharedConfiguration = newJndiSharedConfiguration;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.CONFIG__JNDI_SHARED_CONFIGURATION, oldJndiSharedConfiguration, jndiSharedConfiguration));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JndiProperties getJndiProperties() {
		return jndiProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJndiProperties(JndiProperties newJndiProperties, NotificationChain msgs) {
		JndiProperties oldJndiProperties = jndiProperties;
		jndiProperties = newJndiProperties;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.CONFIG__JNDI_PROPERTIES, oldJndiProperties, newJndiProperties);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJndiProperties(JndiProperties newJndiProperties) {
		if (newJndiProperties != jndiProperties) {
			NotificationChain msgs = null;
			if (jndiProperties != null)
				msgs = ((InternalEObject)jndiProperties).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SharedjmsconPackage.CONFIG__JNDI_PROPERTIES, null, msgs);
			if (newJndiProperties != null)
				msgs = ((InternalEObject)newJndiProperties).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SharedjmsconPackage.CONFIG__JNDI_PROPERTIES, null, msgs);
			msgs = basicSetJndiProperties(newJndiProperties, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.CONFIG__JNDI_PROPERTIES, newJndiProperties, newJndiProperties));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SharedjmsconPackage.CONFIG__NAMING_ENVIRONMENT:
				return basicSetNamingEnvironment(null, msgs);
			case SharedjmsconPackage.CONFIG__CONNECTION_ATTRIBUTES:
				return basicSetConnectionAttributes(null, msgs);
			case SharedjmsconPackage.CONFIG__SSL:
				return basicSetSsl(null, msgs);
			case SharedjmsconPackage.CONFIG__JNDI_PROPERTIES:
				return basicSetJndiProperties(null, msgs);
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
		switch (featureID) {
			case SharedjmsconPackage.CONFIG__NAMING_ENVIRONMENT:
				return getNamingEnvironment();
			case SharedjmsconPackage.CONFIG__CONNECTION_ATTRIBUTES:
				return getConnectionAttributes();
			case SharedjmsconPackage.CONFIG__USE_XACF:
				return getUseXacf();
			case SharedjmsconPackage.CONFIG__USE_SSL:
				return getUseSsl();
			case SharedjmsconPackage.CONFIG__USE_SHARED_JNDI_CONFIG:
				return getUseSharedJndiConfig();
			case SharedjmsconPackage.CONFIG__ADM_FACTORY_SSL_PASSWORD:
				return getAdmFactorySslPassword();
			case SharedjmsconPackage.CONFIG__SSL:
				return getSsl();
			case SharedjmsconPackage.CONFIG__JNDI_SHARED_CONFIGURATION:
				return getJndiSharedConfiguration();
			case SharedjmsconPackage.CONFIG__JNDI_PROPERTIES:
				return getJndiProperties();
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
			case SharedjmsconPackage.CONFIG__NAMING_ENVIRONMENT:
				setNamingEnvironment((NamingEnvironment)newValue);
				return;
			case SharedjmsconPackage.CONFIG__CONNECTION_ATTRIBUTES:
				setConnectionAttributes((ConnectionAttributes)newValue);
				return;
			case SharedjmsconPackage.CONFIG__USE_XACF:
				setUseXacf(newValue);
				return;
			case SharedjmsconPackage.CONFIG__USE_SSL:
				setUseSsl(newValue);
				return;
			case SharedjmsconPackage.CONFIG__USE_SHARED_JNDI_CONFIG:
				setUseSharedJndiConfig(newValue);
				return;
			case SharedjmsconPackage.CONFIG__ADM_FACTORY_SSL_PASSWORD:
				setAdmFactorySslPassword((String)newValue);
				return;
			case SharedjmsconPackage.CONFIG__SSL:
				setSsl((Ssl)newValue);
				return;
			case SharedjmsconPackage.CONFIG__JNDI_SHARED_CONFIGURATION:
				setJndiSharedConfiguration((String)newValue);
				return;
			case SharedjmsconPackage.CONFIG__JNDI_PROPERTIES:
				setJndiProperties((JndiProperties)newValue);
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
			case SharedjmsconPackage.CONFIG__NAMING_ENVIRONMENT:
				setNamingEnvironment((NamingEnvironment)null);
				return;
			case SharedjmsconPackage.CONFIG__CONNECTION_ATTRIBUTES:
				setConnectionAttributes((ConnectionAttributes)null);
				return;
			case SharedjmsconPackage.CONFIG__USE_XACF:
				setUseXacf(USE_XACF_EDEFAULT);
				return;
			case SharedjmsconPackage.CONFIG__USE_SSL:
				setUseSsl(USE_SSL_EDEFAULT);
				return;
			case SharedjmsconPackage.CONFIG__USE_SHARED_JNDI_CONFIG:
				setUseSharedJndiConfig(USE_SHARED_JNDI_CONFIG_EDEFAULT);
				return;
			case SharedjmsconPackage.CONFIG__ADM_FACTORY_SSL_PASSWORD:
				setAdmFactorySslPassword(ADM_FACTORY_SSL_PASSWORD_EDEFAULT);
				return;
			case SharedjmsconPackage.CONFIG__SSL:
				setSsl((Ssl)null);
				return;
			case SharedjmsconPackage.CONFIG__JNDI_SHARED_CONFIGURATION:
				setJndiSharedConfiguration(JNDI_SHARED_CONFIGURATION_EDEFAULT);
				return;
			case SharedjmsconPackage.CONFIG__JNDI_PROPERTIES:
				setJndiProperties((JndiProperties)null);
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
			case SharedjmsconPackage.CONFIG__NAMING_ENVIRONMENT:
				return namingEnvironment != null;
			case SharedjmsconPackage.CONFIG__CONNECTION_ATTRIBUTES:
				return connectionAttributes != null;
			case SharedjmsconPackage.CONFIG__USE_XACF:
				return USE_XACF_EDEFAULT == null ? useXacf != null : !USE_XACF_EDEFAULT.equals(useXacf);
			case SharedjmsconPackage.CONFIG__USE_SSL:
				return USE_SSL_EDEFAULT == null ? useSsl != null : !USE_SSL_EDEFAULT.equals(useSsl);
			case SharedjmsconPackage.CONFIG__USE_SHARED_JNDI_CONFIG:
				return USE_SHARED_JNDI_CONFIG_EDEFAULT == null ? useSharedJndiConfig != null : !USE_SHARED_JNDI_CONFIG_EDEFAULT.equals(useSharedJndiConfig);
			case SharedjmsconPackage.CONFIG__ADM_FACTORY_SSL_PASSWORD:
				return ADM_FACTORY_SSL_PASSWORD_EDEFAULT == null ? admFactorySslPassword != null : !ADM_FACTORY_SSL_PASSWORD_EDEFAULT.equals(admFactorySslPassword);
			case SharedjmsconPackage.CONFIG__SSL:
				return ssl != null;
			case SharedjmsconPackage.CONFIG__JNDI_SHARED_CONFIGURATION:
				return JNDI_SHARED_CONFIGURATION_EDEFAULT == null ? jndiSharedConfiguration != null : !JNDI_SHARED_CONFIGURATION_EDEFAULT.equals(jndiSharedConfiguration);
			case SharedjmsconPackage.CONFIG__JNDI_PROPERTIES:
				return jndiProperties != null;
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
		result.append(" (useXacf: ");
		result.append(useXacf);
		result.append(", useSsl: ");
		result.append(useSsl);
		result.append(", useSharedJndiConfig: ");
		result.append(useSharedJndiConfig);
		result.append(", admFactorySslPassword: ");
		result.append(admFactorySslPassword);
		result.append(", jndiSharedConfiguration: ");
		result.append(jndiSharedConfiguration);
		result.append(')');
		return result.toString();
	}

} //ConfigImpl
