/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedhttp.impl;

import com.tibco.be.util.config.sharedresources.aemetaservices.Ssl;

import com.tibco.be.util.config.sharedresources.sharedhttp.Config;
import com.tibco.be.util.config.sharedresources.sharedhttp.SharedhttpPackage;

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
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedhttp.impl.ConfigImpl#getHost <em>Host</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedhttp.impl.ConfigImpl#getPort <em>Port</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedhttp.impl.ConfigImpl#getEnableLookups <em>Enable Lookups</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedhttp.impl.ConfigImpl#getUseSsl <em>Use Ssl</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedhttp.impl.ConfigImpl#getSsl <em>Ssl</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedhttp.impl.ConfigImpl#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConfigImpl extends EObjectImpl implements Config {
	/**
	 * The default value of the '{@link #getHost() <em>Host</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHost()
	 * @generated
	 * @ordered
	 */
	protected static final String HOST_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHost() <em>Host</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHost()
	 * @generated
	 * @ordered
	 */
	protected String host = HOST_EDEFAULT;

	/**
	 * The default value of the '{@link #getPort() <em>Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPort()
	 * @generated
	 * @ordered
	 */
	protected static final Object PORT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPort() <em>Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPort()
	 * @generated
	 * @ordered
	 */
	protected Object port = PORT_EDEFAULT;

	/**
	 * The default value of the '{@link #getEnableLookups() <em>Enable Lookups</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnableLookups()
	 * @generated
	 * @ordered
	 */
	protected static final Object ENABLE_LOOKUPS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEnableLookups() <em>Enable Lookups</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnableLookups()
	 * @generated
	 * @ordered
	 */
	protected Object enableLookups = ENABLE_LOOKUPS_EDEFAULT;

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
	 * The cached value of the '{@link #getSsl() <em>Ssl</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSsl()
	 * @generated
	 * @ordered
	 */
	protected Ssl ssl;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

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
		return SharedhttpPackage.Literals.CONFIG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHost() {
		return host;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHost(String newHost) {
		String oldHost = host;
		host = newHost;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedhttpPackage.CONFIG__HOST, oldHost, host));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getPort() {
		return port;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPort(Object newPort) {
		Object oldPort = port;
		port = newPort;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedhttpPackage.CONFIG__PORT, oldPort, port));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getEnableLookups() {
		return enableLookups;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnableLookups(Object newEnableLookups) {
		Object oldEnableLookups = enableLookups;
		enableLookups = newEnableLookups;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedhttpPackage.CONFIG__ENABLE_LOOKUPS, oldEnableLookups, enableLookups));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SharedhttpPackage.CONFIG__USE_SSL, oldUseSsl, useSsl));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SharedhttpPackage.CONFIG__SSL, oldSsl, newSsl);
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
				msgs = ((InternalEObject)ssl).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SharedhttpPackage.CONFIG__SSL, null, msgs);
			if (newSsl != null)
				msgs = ((InternalEObject)newSsl).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SharedhttpPackage.CONFIG__SSL, null, msgs);
			msgs = basicSetSsl(newSsl, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedhttpPackage.CONFIG__SSL, newSsl, newSsl));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedhttpPackage.CONFIG__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SharedhttpPackage.CONFIG__SSL:
				return basicSetSsl(null, msgs);
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
			case SharedhttpPackage.CONFIG__HOST:
				return getHost();
			case SharedhttpPackage.CONFIG__PORT:
				return getPort();
			case SharedhttpPackage.CONFIG__ENABLE_LOOKUPS:
				return getEnableLookups();
			case SharedhttpPackage.CONFIG__USE_SSL:
				return getUseSsl();
			case SharedhttpPackage.CONFIG__SSL:
				return getSsl();
			case SharedhttpPackage.CONFIG__DESCRIPTION:
				return getDescription();
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
			case SharedhttpPackage.CONFIG__HOST:
				setHost((String)newValue);
				return;
			case SharedhttpPackage.CONFIG__PORT:
				setPort(newValue);
				return;
			case SharedhttpPackage.CONFIG__ENABLE_LOOKUPS:
				setEnableLookups(newValue);
				return;
			case SharedhttpPackage.CONFIG__USE_SSL:
				setUseSsl(newValue);
				return;
			case SharedhttpPackage.CONFIG__SSL:
				setSsl((Ssl)newValue);
				return;
			case SharedhttpPackage.CONFIG__DESCRIPTION:
				setDescription((String)newValue);
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
			case SharedhttpPackage.CONFIG__HOST:
				setHost(HOST_EDEFAULT);
				return;
			case SharedhttpPackage.CONFIG__PORT:
				setPort(PORT_EDEFAULT);
				return;
			case SharedhttpPackage.CONFIG__ENABLE_LOOKUPS:
				setEnableLookups(ENABLE_LOOKUPS_EDEFAULT);
				return;
			case SharedhttpPackage.CONFIG__USE_SSL:
				setUseSsl(USE_SSL_EDEFAULT);
				return;
			case SharedhttpPackage.CONFIG__SSL:
				setSsl((Ssl)null);
				return;
			case SharedhttpPackage.CONFIG__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
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
			case SharedhttpPackage.CONFIG__HOST:
				return HOST_EDEFAULT == null ? host != null : !HOST_EDEFAULT.equals(host);
			case SharedhttpPackage.CONFIG__PORT:
				return PORT_EDEFAULT == null ? port != null : !PORT_EDEFAULT.equals(port);
			case SharedhttpPackage.CONFIG__ENABLE_LOOKUPS:
				return ENABLE_LOOKUPS_EDEFAULT == null ? enableLookups != null : !ENABLE_LOOKUPS_EDEFAULT.equals(enableLookups);
			case SharedhttpPackage.CONFIG__USE_SSL:
				return USE_SSL_EDEFAULT == null ? useSsl != null : !USE_SSL_EDEFAULT.equals(useSsl);
			case SharedhttpPackage.CONFIG__SSL:
				return ssl != null;
			case SharedhttpPackage.CONFIG__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
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
		result.append(" (host: ");
		result.append(host);
		result.append(", port: ");
		result.append(port);
		result.append(", enableLookups: ");
		result.append(enableLookups);
		result.append(", useSsl: ");
		result.append(useSsl);
		result.append(", description: ");
		result.append(description);
		result.append(')');
		return result.toString();
	}

} //ConfigImpl
