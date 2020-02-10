/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.Properties;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.AgentsConfig;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.HttpConfig;
import com.tibco.be.util.config.cdd.LogConfigConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.be.util.config.cdd.ProcessingUnitConfig;
import com.tibco.be.util.config.cdd.ProcessingUnitSecurityConfig;
import com.tibco.be.util.config.cdd.PropertyGroupConfig;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Processing Unit Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ProcessingUnitConfigImpl#getAgents <em>Agents</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ProcessingUnitConfigImpl#getCacheStorageEnabled <em>Cache Storage Enabled</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ProcessingUnitConfigImpl#getDbConcepts <em>Db Concepts</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ProcessingUnitConfigImpl#getLogs <em>Logs</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ProcessingUnitConfigImpl#getHotDeploy <em>Hot Deploy</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ProcessingUnitConfigImpl#getHttp <em>Http</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ProcessingUnitConfigImpl#getPropertyGroup <em>Property Group</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ProcessingUnitConfigImpl#getSecurityConfig <em>Security Config</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProcessingUnitConfigImpl extends ArtifactConfigImpl implements ProcessingUnitConfig {
	/**
	 * The cached value of the '{@link #getAgents() <em>Agents</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAgents()
	 * @generated
	 * @ordered
	 */
	protected AgentsConfig agents;

	/**
	 * The cached value of the '{@link #getCacheStorageEnabled() <em>Cache Storage Enabled</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCacheStorageEnabled()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig cacheStorageEnabled;

	/**
	 * The cached value of the '{@link #getDbConcepts() <em>Db Concepts</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDbConcepts()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig dbConcepts;

	/**
	 * The cached value of the '{@link #getLogs() <em>Logs</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLogs()
	 * @generated
	 * @ordered
	 */
	protected LogConfigConfig logs;

	/**
	 * The cached value of the '{@link #getHotDeploy() <em>Hot Deploy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHotDeploy()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig hotDeploy;

	/**
	 * The cached value of the '{@link #getHttp() <em>Http</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHttp()
	 * @generated
	 * @ordered
	 */
	protected HttpConfig http;

	/**
	 * The cached value of the '{@link #getPropertyGroup() <em>Property Group</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyGroup()
	 * @generated
	 * @ordered
	 */
	protected PropertyGroupConfig propertyGroup;

	/**
	 * The cached value of the '{@link #getSecurityConfig() <em>Security Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecurityConfig()
	 * @generated
	 * @ordered
	 */
	protected ProcessingUnitSecurityConfig securityConfig;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProcessingUnitConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getProcessingUnitConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AgentsConfig getAgents() {
		return agents;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAgents(AgentsConfig newAgents, NotificationChain msgs) {
		AgentsConfig oldAgents = agents;
		agents = newAgents;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.PROCESSING_UNIT_CONFIG__AGENTS, oldAgents, newAgents);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAgents(AgentsConfig newAgents) {
		if (newAgents != agents) {
			NotificationChain msgs = null;
			if (agents != null)
				msgs = ((InternalEObject)agents).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.PROCESSING_UNIT_CONFIG__AGENTS, null, msgs);
			if (newAgents != null)
				msgs = ((InternalEObject)newAgents).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.PROCESSING_UNIT_CONFIG__AGENTS, null, msgs);
			msgs = basicSetAgents(newAgents, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.PROCESSING_UNIT_CONFIG__AGENTS, newAgents, newAgents));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getCacheStorageEnabled() {
		return cacheStorageEnabled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCacheStorageEnabled(OverrideConfig newCacheStorageEnabled, NotificationChain msgs) {
		OverrideConfig oldCacheStorageEnabled = cacheStorageEnabled;
		cacheStorageEnabled = newCacheStorageEnabled;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.PROCESSING_UNIT_CONFIG__CACHE_STORAGE_ENABLED, oldCacheStorageEnabled, newCacheStorageEnabled);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCacheStorageEnabled(OverrideConfig newCacheStorageEnabled) {
		if (newCacheStorageEnabled != cacheStorageEnabled) {
			NotificationChain msgs = null;
			if (cacheStorageEnabled != null)
				msgs = ((InternalEObject)cacheStorageEnabled).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.PROCESSING_UNIT_CONFIG__CACHE_STORAGE_ENABLED, null, msgs);
			if (newCacheStorageEnabled != null)
				msgs = ((InternalEObject)newCacheStorageEnabled).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.PROCESSING_UNIT_CONFIG__CACHE_STORAGE_ENABLED, null, msgs);
			msgs = basicSetCacheStorageEnabled(newCacheStorageEnabled, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.PROCESSING_UNIT_CONFIG__CACHE_STORAGE_ENABLED, newCacheStorageEnabled, newCacheStorageEnabled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getDbConcepts() {
		return dbConcepts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDbConcepts(OverrideConfig newDbConcepts, NotificationChain msgs) {
		OverrideConfig oldDbConcepts = dbConcepts;
		dbConcepts = newDbConcepts;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.PROCESSING_UNIT_CONFIG__DB_CONCEPTS, oldDbConcepts, newDbConcepts);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDbConcepts(OverrideConfig newDbConcepts) {
		if (newDbConcepts != dbConcepts) {
			NotificationChain msgs = null;
			if (dbConcepts != null)
				msgs = ((InternalEObject)dbConcepts).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.PROCESSING_UNIT_CONFIG__DB_CONCEPTS, null, msgs);
			if (newDbConcepts != null)
				msgs = ((InternalEObject)newDbConcepts).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.PROCESSING_UNIT_CONFIG__DB_CONCEPTS, null, msgs);
			msgs = basicSetDbConcepts(newDbConcepts, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.PROCESSING_UNIT_CONFIG__DB_CONCEPTS, newDbConcepts, newDbConcepts));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LogConfigConfig getLogs() {
		return logs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLogs(LogConfigConfig newLogs) {
		LogConfigConfig oldLogs = logs;
		logs = newLogs;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.PROCESSING_UNIT_CONFIG__LOGS, oldLogs, logs));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getHotDeploy() {
		return hotDeploy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHotDeploy(OverrideConfig newHotDeploy, NotificationChain msgs) {
		OverrideConfig oldHotDeploy = hotDeploy;
		hotDeploy = newHotDeploy;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.PROCESSING_UNIT_CONFIG__HOT_DEPLOY, oldHotDeploy, newHotDeploy);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHotDeploy(OverrideConfig newHotDeploy) {
		if (newHotDeploy != hotDeploy) {
			NotificationChain msgs = null;
			if (hotDeploy != null)
				msgs = ((InternalEObject)hotDeploy).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.PROCESSING_UNIT_CONFIG__HOT_DEPLOY, null, msgs);
			if (newHotDeploy != null)
				msgs = ((InternalEObject)newHotDeploy).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.PROCESSING_UNIT_CONFIG__HOT_DEPLOY, null, msgs);
			msgs = basicSetHotDeploy(newHotDeploy, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.PROCESSING_UNIT_CONFIG__HOT_DEPLOY, newHotDeploy, newHotDeploy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HttpConfig getHttp() {
		return http;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHttp(HttpConfig newHttp, NotificationChain msgs) {
		HttpConfig oldHttp = http;
		http = newHttp;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.PROCESSING_UNIT_CONFIG__HTTP, oldHttp, newHttp);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHttp(HttpConfig newHttp) {
		if (newHttp != http) {
			NotificationChain msgs = null;
			if (http != null)
				msgs = ((InternalEObject)http).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.PROCESSING_UNIT_CONFIG__HTTP, null, msgs);
			if (newHttp != null)
				msgs = ((InternalEObject)newHttp).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.PROCESSING_UNIT_CONFIG__HTTP, null, msgs);
			msgs = basicSetHttp(newHttp, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.PROCESSING_UNIT_CONFIG__HTTP, newHttp, newHttp));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyGroupConfig getPropertyGroup() {
		return propertyGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPropertyGroup(PropertyGroupConfig newPropertyGroup, NotificationChain msgs) {
		PropertyGroupConfig oldPropertyGroup = propertyGroup;
		propertyGroup = newPropertyGroup;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.PROCESSING_UNIT_CONFIG__PROPERTY_GROUP, oldPropertyGroup, newPropertyGroup);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropertyGroup(PropertyGroupConfig newPropertyGroup) {
		if (newPropertyGroup != propertyGroup) {
			NotificationChain msgs = null;
			if (propertyGroup != null)
				msgs = ((InternalEObject)propertyGroup).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.PROCESSING_UNIT_CONFIG__PROPERTY_GROUP, null, msgs);
			if (newPropertyGroup != null)
				msgs = ((InternalEObject)newPropertyGroup).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.PROCESSING_UNIT_CONFIG__PROPERTY_GROUP, null, msgs);
			msgs = basicSetPropertyGroup(newPropertyGroup, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.PROCESSING_UNIT_CONFIG__PROPERTY_GROUP, newPropertyGroup, newPropertyGroup));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessingUnitSecurityConfig getSecurityConfig() {
		return securityConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSecurityConfig(ProcessingUnitSecurityConfig newSecurityConfig, NotificationChain msgs) {
		ProcessingUnitSecurityConfig oldSecurityConfig = securityConfig;
		securityConfig = newSecurityConfig;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.PROCESSING_UNIT_CONFIG__SECURITY_CONFIG, oldSecurityConfig, newSecurityConfig);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSecurityConfig(ProcessingUnitSecurityConfig newSecurityConfig) {
		if (newSecurityConfig != securityConfig) {
			NotificationChain msgs = null;
			if (securityConfig != null)
				msgs = ((InternalEObject)securityConfig).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.PROCESSING_UNIT_CONFIG__SECURITY_CONFIG, null, msgs);
			if (newSecurityConfig != null)
				msgs = ((InternalEObject)newSecurityConfig).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.PROCESSING_UNIT_CONFIG__SECURITY_CONFIG, null, msgs);
			msgs = basicSetSecurityConfig(newSecurityConfig, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.PROCESSING_UNIT_CONFIG__SECURITY_CONFIG, newSecurityConfig, newSecurityConfig));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.PROCESSING_UNIT_CONFIG__AGENTS:
				return basicSetAgents(null, msgs);
			case CddPackage.PROCESSING_UNIT_CONFIG__CACHE_STORAGE_ENABLED:
				return basicSetCacheStorageEnabled(null, msgs);
			case CddPackage.PROCESSING_UNIT_CONFIG__DB_CONCEPTS:
				return basicSetDbConcepts(null, msgs);
			case CddPackage.PROCESSING_UNIT_CONFIG__HOT_DEPLOY:
				return basicSetHotDeploy(null, msgs);
			case CddPackage.PROCESSING_UNIT_CONFIG__HTTP:
				return basicSetHttp(null, msgs);
			case CddPackage.PROCESSING_UNIT_CONFIG__PROPERTY_GROUP:
				return basicSetPropertyGroup(null, msgs);
			case CddPackage.PROCESSING_UNIT_CONFIG__SECURITY_CONFIG:
				return basicSetSecurityConfig(null, msgs);
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
			case CddPackage.PROCESSING_UNIT_CONFIG__AGENTS:
				return getAgents();
			case CddPackage.PROCESSING_UNIT_CONFIG__CACHE_STORAGE_ENABLED:
				return getCacheStorageEnabled();
			case CddPackage.PROCESSING_UNIT_CONFIG__DB_CONCEPTS:
				return getDbConcepts();
			case CddPackage.PROCESSING_UNIT_CONFIG__LOGS:
				return getLogs();
			case CddPackage.PROCESSING_UNIT_CONFIG__HOT_DEPLOY:
				return getHotDeploy();
			case CddPackage.PROCESSING_UNIT_CONFIG__HTTP:
				return getHttp();
			case CddPackage.PROCESSING_UNIT_CONFIG__PROPERTY_GROUP:
				return getPropertyGroup();
			case CddPackage.PROCESSING_UNIT_CONFIG__SECURITY_CONFIG:
				return getSecurityConfig();
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
			case CddPackage.PROCESSING_UNIT_CONFIG__AGENTS:
				setAgents((AgentsConfig)newValue);
				return;
			case CddPackage.PROCESSING_UNIT_CONFIG__CACHE_STORAGE_ENABLED:
				setCacheStorageEnabled((OverrideConfig)newValue);
				return;
			case CddPackage.PROCESSING_UNIT_CONFIG__DB_CONCEPTS:
				setDbConcepts((OverrideConfig)newValue);
				return;
			case CddPackage.PROCESSING_UNIT_CONFIG__LOGS:
				setLogs((LogConfigConfig)newValue);
				return;
			case CddPackage.PROCESSING_UNIT_CONFIG__HOT_DEPLOY:
				setHotDeploy((OverrideConfig)newValue);
				return;
			case CddPackage.PROCESSING_UNIT_CONFIG__HTTP:
				setHttp((HttpConfig)newValue);
				return;
			case CddPackage.PROCESSING_UNIT_CONFIG__PROPERTY_GROUP:
				setPropertyGroup((PropertyGroupConfig)newValue);
				return;
			case CddPackage.PROCESSING_UNIT_CONFIG__SECURITY_CONFIG:
				setSecurityConfig((ProcessingUnitSecurityConfig)newValue);
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
			case CddPackage.PROCESSING_UNIT_CONFIG__AGENTS:
				setAgents((AgentsConfig)null);
				return;
			case CddPackage.PROCESSING_UNIT_CONFIG__CACHE_STORAGE_ENABLED:
				setCacheStorageEnabled((OverrideConfig)null);
				return;
			case CddPackage.PROCESSING_UNIT_CONFIG__DB_CONCEPTS:
				setDbConcepts((OverrideConfig)null);
				return;
			case CddPackage.PROCESSING_UNIT_CONFIG__LOGS:
				setLogs((LogConfigConfig)null);
				return;
			case CddPackage.PROCESSING_UNIT_CONFIG__HOT_DEPLOY:
				setHotDeploy((OverrideConfig)null);
				return;
			case CddPackage.PROCESSING_UNIT_CONFIG__HTTP:
				setHttp((HttpConfig)null);
				return;
			case CddPackage.PROCESSING_UNIT_CONFIG__PROPERTY_GROUP:
				setPropertyGroup((PropertyGroupConfig)null);
				return;
			case CddPackage.PROCESSING_UNIT_CONFIG__SECURITY_CONFIG:
				setSecurityConfig((ProcessingUnitSecurityConfig)null);
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
			case CddPackage.PROCESSING_UNIT_CONFIG__AGENTS:
				return agents != null;
			case CddPackage.PROCESSING_UNIT_CONFIG__CACHE_STORAGE_ENABLED:
				return cacheStorageEnabled != null;
			case CddPackage.PROCESSING_UNIT_CONFIG__DB_CONCEPTS:
				return dbConcepts != null;
			case CddPackage.PROCESSING_UNIT_CONFIG__LOGS:
				return logs != null;
			case CddPackage.PROCESSING_UNIT_CONFIG__HOT_DEPLOY:
				return hotDeploy != null;
			case CddPackage.PROCESSING_UNIT_CONFIG__HTTP:
				return http != null;
			case CddPackage.PROCESSING_UNIT_CONFIG__PROPERTY_GROUP:
				return propertyGroup != null;
			case CddPackage.PROCESSING_UNIT_CONFIG__SECURITY_CONFIG:
				return securityConfig != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * @generated NOT
	 */
    public Properties toProperties() {
        final Properties p = new Properties();

        // Active agents
        if (null != this.agents) {
            p.putAll(this.agents.toProperties());
        }

        CddTools.addEntryFromMixed(p, SystemProperty.DB_CONCEPTS_ENABLED.getPropertyName(), this.getDbConcepts(), true);
        CddTools.addEntryFromMixed(p, SystemProperty.CLUSTER_NODE_ISSEEDER.getPropertyName(), this.getCacheStorageEnabled(), true);
        CddTools.addEntryFromMixed(p, SystemProperty.HOT_DEPLOY_ENABLED.getPropertyName(), this.getHotDeploy(), true);

        if (null != this.http) {
            p.putAll(this.http.toProperties());
        }
        
        if (null != this.logs) {
            p.putAll(this.logs.toProperties());
        }

        // P.U. level Security settings
		if (this.securityConfig != null) {
			p.putAll(this.securityConfig.toProperties());
		}

        // P.U.-level custom properties
        if (null != this.propertyGroup) {
            p.putAll(this.propertyGroup.toProperties());
        }

        return p;
    }
} //ProcessingUnitConfigImpl
