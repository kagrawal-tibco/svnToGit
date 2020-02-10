/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.Map;
import java.util.Properties;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.BackingStoreConfig;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.ConnectionConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.cep.runtime.service.om.impl.invm.NoOpLocalCache;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Backing Store Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.BackingStoreConfigImpl#getCacheAside <em>Cache Aside</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.BackingStoreConfigImpl#getCacheLoaderClass <em>Cache Loader Class</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.BackingStoreConfigImpl#getDataStorePath <em>Data Store Path</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.BackingStoreConfigImpl#getEnforcePools <em>Enforce Pools</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.BackingStoreConfigImpl#getPersistenceOption <em>Persistence Option</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.BackingStoreConfigImpl#getPersistencePolicy <em>Persistence Policy</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.BackingStoreConfigImpl#getParallelRecovery <em>Parallel Recovery</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.BackingStoreConfigImpl#getPrimaryConnection <em>Primary Connection</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.BackingStoreConfigImpl#getSecondaryConnection <em>Secondary Connection</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.BackingStoreConfigImpl#getStrategy <em>Strategy</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.BackingStoreConfigImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BackingStoreConfigImpl extends EObjectImpl implements BackingStoreConfig {

	/**
	 * The cached value of the '{@link #getCacheAside() <em>Cache Aside</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCacheAside()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig cacheAside;

	/**
	 * The cached value of the '{@link #getCacheLoaderClass() <em>Cache Loader Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCacheLoaderClass()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig cacheLoaderClass;

	/**
	 * The cached value of the '{@link #getDataStorePath() <em>Data Store Path</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataStorePath()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig dataStorePath;

	/**
	 * The cached value of the '{@link #getEnforcePools() <em>Enforce Pools</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnforcePools()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig enforcePools;

	/**
	 * The cached value of the '{@link #getPersistenceOption() <em>Persistence Option</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPersistenceOption()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig persistenceOption;

	/**
	 * The cached value of the '{@link #getPersistencePolicy() <em>Persistence Policy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPersistencePolicy()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig persistencePolicy;

	/**
	 * The cached value of the '{@link #getParallelRecovery() <em>Parallel Recovery</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParallelRecovery()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig parallelRecovery;

	/**
	 * The cached value of the '{@link #getPrimaryConnection() <em>Primary Connection</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimaryConnection()
	 * @generated
	 * @ordered
	 */
	protected ConnectionConfig primaryConnection;

	/**
	 * The cached value of the '{@link #getSecondaryConnection() <em>Secondary Connection</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondaryConnection()
	 * @generated
	 * @ordered
	 */
	protected ConnectionConfig secondaryConnection;

	/**
	 * The cached value of the '{@link #getStrategy() <em>Strategy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStrategy()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig strategy;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig type;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BackingStoreConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getBackingStoreConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getCacheAside() {
		return cacheAside;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCacheAside(OverrideConfig newCacheAside, NotificationChain msgs) {
		OverrideConfig oldCacheAside = cacheAside;
		cacheAside = newCacheAside;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_CONFIG__CACHE_ASIDE, oldCacheAside, newCacheAside);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCacheAside(OverrideConfig newCacheAside) {
		if (newCacheAside != cacheAside) {
			NotificationChain msgs = null;
			if (cacheAside != null)
				msgs = ((InternalEObject)cacheAside).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_CONFIG__CACHE_ASIDE, null, msgs);
			if (newCacheAside != null)
				msgs = ((InternalEObject)newCacheAside).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_CONFIG__CACHE_ASIDE, null, msgs);
			msgs = basicSetCacheAside(newCacheAside, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_CONFIG__CACHE_ASIDE, newCacheAside, newCacheAside));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getCacheLoaderClass() {
		return cacheLoaderClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCacheLoaderClass(OverrideConfig newCacheLoaderClass, NotificationChain msgs) {
		OverrideConfig oldCacheLoaderClass = cacheLoaderClass;
		cacheLoaderClass = newCacheLoaderClass;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_CONFIG__CACHE_LOADER_CLASS, oldCacheLoaderClass, newCacheLoaderClass);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCacheLoaderClass(OverrideConfig newCacheLoaderClass) {
		if (newCacheLoaderClass != cacheLoaderClass) {
			NotificationChain msgs = null;
			if (cacheLoaderClass != null)
				msgs = ((InternalEObject)cacheLoaderClass).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_CONFIG__CACHE_LOADER_CLASS, null, msgs);
			if (newCacheLoaderClass != null)
				msgs = ((InternalEObject)newCacheLoaderClass).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_CONFIG__CACHE_LOADER_CLASS, null, msgs);
			msgs = basicSetCacheLoaderClass(newCacheLoaderClass, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_CONFIG__CACHE_LOADER_CLASS, newCacheLoaderClass, newCacheLoaderClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getDataStorePath() {
		return dataStorePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDataStorePath(OverrideConfig newDataStorePath, NotificationChain msgs) {
		OverrideConfig oldDataStorePath = dataStorePath;
		dataStorePath = newDataStorePath;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_CONFIG__DATA_STORE_PATH, oldDataStorePath, newDataStorePath);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDataStorePath(OverrideConfig newDataStorePath) {
		if (newDataStorePath != dataStorePath) {
			NotificationChain msgs = null;
			if (dataStorePath != null)
				msgs = ((InternalEObject)dataStorePath).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_CONFIG__DATA_STORE_PATH, null, msgs);
			if (newDataStorePath != null)
				msgs = ((InternalEObject)newDataStorePath).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_CONFIG__DATA_STORE_PATH, null, msgs);
			msgs = basicSetDataStorePath(newDataStorePath, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_CONFIG__DATA_STORE_PATH, newDataStorePath, newDataStorePath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getEnforcePools() {
		return enforcePools;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEnforcePools(OverrideConfig newEnforcePools, NotificationChain msgs) {
		OverrideConfig oldEnforcePools = enforcePools;
		enforcePools = newEnforcePools;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_CONFIG__ENFORCE_POOLS, oldEnforcePools, newEnforcePools);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnforcePools(OverrideConfig newEnforcePools) {
		if (newEnforcePools != enforcePools) {
			NotificationChain msgs = null;
			if (enforcePools != null)
				msgs = ((InternalEObject)enforcePools).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_CONFIG__ENFORCE_POOLS, null, msgs);
			if (newEnforcePools != null)
				msgs = ((InternalEObject)newEnforcePools).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_CONFIG__ENFORCE_POOLS, null, msgs);
			msgs = basicSetEnforcePools(newEnforcePools, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_CONFIG__ENFORCE_POOLS, newEnforcePools, newEnforcePools));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getPersistenceOption() {
		return persistenceOption;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPersistenceOption(OverrideConfig newPersistenceOption, NotificationChain msgs) {
		OverrideConfig oldPersistenceOption = persistenceOption;
		persistenceOption = newPersistenceOption;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_CONFIG__PERSISTENCE_OPTION, oldPersistenceOption, newPersistenceOption);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPersistenceOption(OverrideConfig newPersistenceOption) {
		if (newPersistenceOption != persistenceOption) {
			NotificationChain msgs = null;
			if (persistenceOption != null)
				msgs = ((InternalEObject)persistenceOption).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_CONFIG__PERSISTENCE_OPTION, null, msgs);
			if (newPersistenceOption != null)
				msgs = ((InternalEObject)newPersistenceOption).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_CONFIG__PERSISTENCE_OPTION, null, msgs);
			msgs = basicSetPersistenceOption(newPersistenceOption, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_CONFIG__PERSISTENCE_OPTION, newPersistenceOption, newPersistenceOption));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getPersistencePolicy() {
		return persistencePolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPersistencePolicy(OverrideConfig newPersistencePolicy, NotificationChain msgs) {
		OverrideConfig oldPersistencePolicy = persistencePolicy;
		persistencePolicy = newPersistencePolicy;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_CONFIG__PERSISTENCE_POLICY, oldPersistencePolicy, newPersistencePolicy);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPersistencePolicy(OverrideConfig newPersistencePolicy) {
		if (newPersistencePolicy != persistencePolicy) {
			NotificationChain msgs = null;
			if (persistencePolicy != null)
				msgs = ((InternalEObject)persistencePolicy).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_CONFIG__PERSISTENCE_POLICY, null, msgs);
			if (newPersistencePolicy != null)
				msgs = ((InternalEObject)newPersistencePolicy).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_CONFIG__PERSISTENCE_POLICY, null, msgs);
			msgs = basicSetPersistencePolicy(newPersistencePolicy, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_CONFIG__PERSISTENCE_POLICY, newPersistencePolicy, newPersistencePolicy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getParallelRecovery() {
		return parallelRecovery;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParallelRecovery(OverrideConfig newParallelRecovery, NotificationChain msgs) {
		OverrideConfig oldParallelRecovery = parallelRecovery;
		parallelRecovery = newParallelRecovery;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_CONFIG__PARALLEL_RECOVERY, oldParallelRecovery, newParallelRecovery);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParallelRecovery(OverrideConfig newParallelRecovery) {
		if (newParallelRecovery != parallelRecovery) {
			NotificationChain msgs = null;
			if (parallelRecovery != null)
				msgs = ((InternalEObject)parallelRecovery).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_CONFIG__PARALLEL_RECOVERY, null, msgs);
			if (newParallelRecovery != null)
				msgs = ((InternalEObject)newParallelRecovery).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_CONFIG__PARALLEL_RECOVERY, null, msgs);
			msgs = basicSetParallelRecovery(newParallelRecovery, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_CONFIG__PARALLEL_RECOVERY, newParallelRecovery, newParallelRecovery));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectionConfig getPrimaryConnection() {
		return primaryConnection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPrimaryConnection(ConnectionConfig newPrimaryConnection, NotificationChain msgs) {
		ConnectionConfig oldPrimaryConnection = primaryConnection;
		primaryConnection = newPrimaryConnection;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_CONFIG__PRIMARY_CONNECTION, oldPrimaryConnection, newPrimaryConnection);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPrimaryConnection(ConnectionConfig newPrimaryConnection) {
		if (newPrimaryConnection != primaryConnection) {
			NotificationChain msgs = null;
			if (primaryConnection != null)
				msgs = ((InternalEObject)primaryConnection).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_CONFIG__PRIMARY_CONNECTION, null, msgs);
			if (newPrimaryConnection != null)
				msgs = ((InternalEObject)newPrimaryConnection).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_CONFIG__PRIMARY_CONNECTION, null, msgs);
			msgs = basicSetPrimaryConnection(newPrimaryConnection, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_CONFIG__PRIMARY_CONNECTION, newPrimaryConnection, newPrimaryConnection));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectionConfig getSecondaryConnection() {
		return secondaryConnection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSecondaryConnection(ConnectionConfig newSecondaryConnection, NotificationChain msgs) {
		ConnectionConfig oldSecondaryConnection = secondaryConnection;
		secondaryConnection = newSecondaryConnection;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_CONFIG__SECONDARY_CONNECTION, oldSecondaryConnection, newSecondaryConnection);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSecondaryConnection(ConnectionConfig newSecondaryConnection) {
		if (newSecondaryConnection != secondaryConnection) {
			NotificationChain msgs = null;
			if (secondaryConnection != null)
				msgs = ((InternalEObject)secondaryConnection).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_CONFIG__SECONDARY_CONNECTION, null, msgs);
			if (newSecondaryConnection != null)
				msgs = ((InternalEObject)newSecondaryConnection).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_CONFIG__SECONDARY_CONNECTION, null, msgs);
			msgs = basicSetSecondaryConnection(newSecondaryConnection, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_CONFIG__SECONDARY_CONNECTION, newSecondaryConnection, newSecondaryConnection));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getStrategy() {
		return strategy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStrategy(OverrideConfig newStrategy, NotificationChain msgs) {
		OverrideConfig oldStrategy = strategy;
		strategy = newStrategy;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_CONFIG__STRATEGY, oldStrategy, newStrategy);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStrategy(OverrideConfig newStrategy) {
		if (newStrategy != strategy) {
			NotificationChain msgs = null;
			if (strategy != null)
				msgs = ((InternalEObject)strategy).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_CONFIG__STRATEGY, null, msgs);
			if (newStrategy != null)
				msgs = ((InternalEObject)newStrategy).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_CONFIG__STRATEGY, null, msgs);
			msgs = basicSetStrategy(newStrategy, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_CONFIG__STRATEGY, newStrategy, newStrategy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetType(OverrideConfig newType, NotificationChain msgs) {
		OverrideConfig oldType = type;
		type = newType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_CONFIG__TYPE, oldType, newType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(OverrideConfig newType) {
		if (newType != type) {
			NotificationChain msgs = null;
			if (type != null)
				msgs = ((InternalEObject)type).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_CONFIG__TYPE, null, msgs);
			if (newType != null)
				msgs = ((InternalEObject)newType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_CONFIG__TYPE, null, msgs);
			msgs = basicSetType(newType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_CONFIG__TYPE, newType, newType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.BACKING_STORE_CONFIG__CACHE_ASIDE:
				return basicSetCacheAside(null, msgs);
			case CddPackage.BACKING_STORE_CONFIG__CACHE_LOADER_CLASS:
				return basicSetCacheLoaderClass(null, msgs);
			case CddPackage.BACKING_STORE_CONFIG__DATA_STORE_PATH:
				return basicSetDataStorePath(null, msgs);
			case CddPackage.BACKING_STORE_CONFIG__ENFORCE_POOLS:
				return basicSetEnforcePools(null, msgs);
			case CddPackage.BACKING_STORE_CONFIG__PERSISTENCE_OPTION:
				return basicSetPersistenceOption(null, msgs);
			case CddPackage.BACKING_STORE_CONFIG__PERSISTENCE_POLICY:
				return basicSetPersistencePolicy(null, msgs);
			case CddPackage.BACKING_STORE_CONFIG__PARALLEL_RECOVERY:
				return basicSetParallelRecovery(null, msgs);
			case CddPackage.BACKING_STORE_CONFIG__PRIMARY_CONNECTION:
				return basicSetPrimaryConnection(null, msgs);
			case CddPackage.BACKING_STORE_CONFIG__SECONDARY_CONNECTION:
				return basicSetSecondaryConnection(null, msgs);
			case CddPackage.BACKING_STORE_CONFIG__STRATEGY:
				return basicSetStrategy(null, msgs);
			case CddPackage.BACKING_STORE_CONFIG__TYPE:
				return basicSetType(null, msgs);
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
			case CddPackage.BACKING_STORE_CONFIG__CACHE_ASIDE:
				return getCacheAside();
			case CddPackage.BACKING_STORE_CONFIG__CACHE_LOADER_CLASS:
				return getCacheLoaderClass();
			case CddPackage.BACKING_STORE_CONFIG__DATA_STORE_PATH:
				return getDataStorePath();
			case CddPackage.BACKING_STORE_CONFIG__ENFORCE_POOLS:
				return getEnforcePools();
			case CddPackage.BACKING_STORE_CONFIG__PERSISTENCE_OPTION:
				return getPersistenceOption();
			case CddPackage.BACKING_STORE_CONFIG__PERSISTENCE_POLICY:
				return getPersistencePolicy();
			case CddPackage.BACKING_STORE_CONFIG__PARALLEL_RECOVERY:
				return getParallelRecovery();
			case CddPackage.BACKING_STORE_CONFIG__PRIMARY_CONNECTION:
				return getPrimaryConnection();
			case CddPackage.BACKING_STORE_CONFIG__SECONDARY_CONNECTION:
				return getSecondaryConnection();
			case CddPackage.BACKING_STORE_CONFIG__STRATEGY:
				return getStrategy();
			case CddPackage.BACKING_STORE_CONFIG__TYPE:
				return getType();
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
			case CddPackage.BACKING_STORE_CONFIG__CACHE_ASIDE:
				setCacheAside((OverrideConfig)newValue);
				return;
			case CddPackage.BACKING_STORE_CONFIG__CACHE_LOADER_CLASS:
				setCacheLoaderClass((OverrideConfig)newValue);
				return;
			case CddPackage.BACKING_STORE_CONFIG__DATA_STORE_PATH:
				setDataStorePath((OverrideConfig)newValue);
				return;
			case CddPackage.BACKING_STORE_CONFIG__ENFORCE_POOLS:
				setEnforcePools((OverrideConfig)newValue);
				return;
			case CddPackage.BACKING_STORE_CONFIG__PERSISTENCE_OPTION:
				setPersistenceOption((OverrideConfig)newValue);
				return;
			case CddPackage.BACKING_STORE_CONFIG__PERSISTENCE_POLICY:
				setPersistencePolicy((OverrideConfig)newValue);
				return;
			case CddPackage.BACKING_STORE_CONFIG__PARALLEL_RECOVERY:
				setParallelRecovery((OverrideConfig)newValue);
				return;
			case CddPackage.BACKING_STORE_CONFIG__PRIMARY_CONNECTION:
				setPrimaryConnection((ConnectionConfig)newValue);
				return;
			case CddPackage.BACKING_STORE_CONFIG__SECONDARY_CONNECTION:
				setSecondaryConnection((ConnectionConfig)newValue);
				return;
			case CddPackage.BACKING_STORE_CONFIG__STRATEGY:
				setStrategy((OverrideConfig)newValue);
				return;
			case CddPackage.BACKING_STORE_CONFIG__TYPE:
				setType((OverrideConfig)newValue);
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
			case CddPackage.BACKING_STORE_CONFIG__CACHE_ASIDE:
				setCacheAside((OverrideConfig)null);
				return;
			case CddPackage.BACKING_STORE_CONFIG__CACHE_LOADER_CLASS:
				setCacheLoaderClass((OverrideConfig)null);
				return;
			case CddPackage.BACKING_STORE_CONFIG__DATA_STORE_PATH:
				setDataStorePath((OverrideConfig)null);
				return;
			case CddPackage.BACKING_STORE_CONFIG__ENFORCE_POOLS:
				setEnforcePools((OverrideConfig)null);
				return;
			case CddPackage.BACKING_STORE_CONFIG__PERSISTENCE_OPTION:
				setPersistenceOption((OverrideConfig)null);
				return;
			case CddPackage.BACKING_STORE_CONFIG__PERSISTENCE_POLICY:
				setPersistencePolicy((OverrideConfig)null);
				return;
			case CddPackage.BACKING_STORE_CONFIG__PARALLEL_RECOVERY:
				setParallelRecovery((OverrideConfig)null);
				return;
			case CddPackage.BACKING_STORE_CONFIG__PRIMARY_CONNECTION:
				setPrimaryConnection((ConnectionConfig)null);
				return;
			case CddPackage.BACKING_STORE_CONFIG__SECONDARY_CONNECTION:
				setSecondaryConnection((ConnectionConfig)null);
				return;
			case CddPackage.BACKING_STORE_CONFIG__STRATEGY:
				setStrategy((OverrideConfig)null);
				return;
			case CddPackage.BACKING_STORE_CONFIG__TYPE:
				setType((OverrideConfig)null);
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
			case CddPackage.BACKING_STORE_CONFIG__CACHE_ASIDE:
				return cacheAside != null;
			case CddPackage.BACKING_STORE_CONFIG__CACHE_LOADER_CLASS:
				return cacheLoaderClass != null;
			case CddPackage.BACKING_STORE_CONFIG__DATA_STORE_PATH:
				return dataStorePath != null;
			case CddPackage.BACKING_STORE_CONFIG__ENFORCE_POOLS:
				return enforcePools != null;
			case CddPackage.BACKING_STORE_CONFIG__PERSISTENCE_OPTION:
				return persistenceOption != null;
			case CddPackage.BACKING_STORE_CONFIG__PERSISTENCE_POLICY:
				return persistencePolicy != null;
			case CddPackage.BACKING_STORE_CONFIG__PARALLEL_RECOVERY:
				return parallelRecovery != null;
			case CddPackage.BACKING_STORE_CONFIG__PRIMARY_CONNECTION:
				return primaryConnection != null;
			case CddPackage.BACKING_STORE_CONFIG__SECONDARY_CONNECTION:
				return secondaryConnection != null;
			case CddPackage.BACKING_STORE_CONFIG__STRATEGY:
				return strategy != null;
			case CddPackage.BACKING_STORE_CONFIG__TYPE:
				return type != null;
		}
		return super.eIsSet(featureID);
	}

	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Map<Object, Object> toProperties() {
		final Properties props = new Properties();

        //With the introduction of As-Shared-all+BDB and AS-Shared-Nothing
        //CLUSTER_HAS_BACKING_STORE now implies the enabling of traditional stores
        //like oracle/sqlserver
        //CLUSTER_HAS_BACKING_STORE is now true only if all of the below are true:
        // 1)shared-all is true
        // 2)and if the store is a traditional store ( Oracle/SQLServer)

        CddTools.addEntryFromMixed(props,
                SystemProperty.BACKING_STORE_TYPE.getPropertyName(),
                this.getPersistenceOption(), true);

        String persistenceOption = CddTools.getValueFromMixed(this.getPersistenceOption());
		if (persistenceOption!=null && !(persistenceOption.equals(BackingStoreConfig.PERSISTENCE_OPTION_NONE))) {

            if ((persistenceOption.compareToIgnoreCase(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_ALL) == 0) ||
                (persistenceOption.compareToIgnoreCase(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_ALL_ALT) == 0)) {
                // Check if backingstore is SqlServer/Oracle
                String databaseType = CddTools.getValueFromMixed(this.getType());
                if ((databaseType.compareToIgnoreCase(BackingStoreConfig.TYPE_ORACLE) == 0) || 
                    (databaseType.compareToIgnoreCase(BackingStoreConfig.TYPE_SQLSERVER) == 0) ||
                    (databaseType.compareToIgnoreCase(BackingStoreConfig.TYPE_DB2) == 0)||
                    (databaseType.compareToIgnoreCase(BackingStoreConfig.TYPE_MYSQL) == 0) ||
                    (databaseType.compareToIgnoreCase(BackingStoreConfig.TYPE_POSTGRES) == 0)) {
                    props.put(
                        SystemProperty.CLUSTER_HAS_BACKING_STORE.getPropertyName(),
                        Boolean.TRUE.toString());
                }
            }
            else if ((persistenceOption.compareToIgnoreCase(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_NOTHING) == 0) ||
                     (persistenceOption.compareToIgnoreCase(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_NOTHING_ALT) == 0)) {

                // --- Settings for BE+AS Shared Nothing
                //be.engine.cluster.lockless.provider.lockfree=false
                //be.engine.cluster.lockless.provider.classname=com.tibco.cep.runtime.managed.DataGridSNManagedObjectSpi
                //be.engine.cluster.lockless.provider=custom
                //be.engine.cluster.as.tuple.explicit=true
                //be.backingstore.useobjecttable=false

                props.put(SystemProperty.CLUSTER_LOCKLESS_PROVIDER.getPropertyName() + ".lockfree", Boolean.FALSE.toString());
                props.put(SystemProperty.CLUSTER_LOCKLESS_PROVIDER.getPropertyName()+".classname","com.tibco.cep.runtime.managed.DataGridSNManagedObjectSpi");
                props.put(SystemProperty.CLUSTER_LOCKLESS_PROVIDER.getPropertyName(),"custom");
                props.put(SystemProperty.PROP_TUPLE_EXPLICIT.getPropertyName(),Boolean.TRUE.toString());
                props.put(SystemProperty.CLUSTER_USEOBJECTTABLE.getPropertyName(),Boolean.FALSE.toString());
                props.put(SystemProperty.VM_LOCALCACHE_CLASSNAME.getPropertyName(), NoOpLocalCache.class.getName());

                // ---Settings for BE+AS Shared Nothing
            }

			CddTools.addEntryFromMixed(props,
					SystemProperty.CLUSTER_IS_CACHE_ASIDE.getPropertyName(),
					this.cacheAside, true);

			CddTools.addEntryFromMixed(props,
					SystemProperty.BACKING_STORE_CACHE_LOADER_CLASS
							.getPropertyName(), this.cacheLoaderClass, true);

			CddTools.addEntryFromMixed(props,
					SystemProperty.BACKING_STORE_DB_ENFORCE_POOLS_PRIMARY
							.getPropertyName(), this.enforcePools, true);

			CddTools.addEntryFromMixed(props,
					SystemProperty.BACKING_STORE_DB_ENFORCE_POOLS_SECONDARY
							.getPropertyName(), this.enforcePools, true);

			if (null != this.primaryConnection) {
				CddTools.addEntryFromMixed(props,
						"be.backingstore.dburi.pool.initial.0",
						this.primaryConnection.getInitialSize(), true);
				CddTools.addEntryFromMixed(props,
						"be.backingstore.dburi.pool.max.0",
						this.primaryConnection.getMaxSize(), true);
				CddTools.addEntryFromMixed(props,
						"be.backingstore.dburi.pool.min.0",
						this.primaryConnection.getMinSize(), true);

				final String uri = this.primaryConnection.getUri();
				if (null != uri) {
					props.put("be.backingstore.dburi.0", uri);
				}
			}

			if (null != this.secondaryConnection) {
				CddTools.addEntryFromMixed(props,
						"be.backingstore.dburi.pool.initial.1",
						this.secondaryConnection.getInitialSize(), true);
				CddTools.addEntryFromMixed(props,
						"be.backingstore.dburi.pool.max.1",
						this.secondaryConnection.getMaxSize(), true);
				CddTools.addEntryFromMixed(props,
						"be.backingstore.dburi.pool.min.1",
						this.secondaryConnection.getMinSize(), true);

				final String uri = this.secondaryConnection.getUri();
				if (null != uri) {
					props.put("be.backingstore.dburi.1", uri);
				}
			}

			CddTools.addEntryFromMixed(props,
					"be.backingstore.dburi.strategy.0", this.strategy, true);
			CddTools.addEntryFromMixed(props,
					"be.backingstore.dburi.strategy.1", this.strategy, true);

			CddTools.addEntryFromMixed(props,
					SystemProperty.BACKING_STORE_DB_TYPE.getPropertyName(),
					this.type, true);
		}

		return props;
	}

} //BackingStoreConfigImpl
