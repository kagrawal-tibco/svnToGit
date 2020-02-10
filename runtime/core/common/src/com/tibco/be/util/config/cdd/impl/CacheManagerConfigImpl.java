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

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.BackingStoreConfig;
import com.tibco.be.util.config.cdd.CacheManagerConfig;
import com.tibco.be.util.config.cdd.CacheManagerSecurityConfig;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.DomainObjectsConfig;
import com.tibco.be.util.config.cdd.ObjectTableConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.be.util.config.cdd.ProviderConfig;
import com.tibco.be.util.packaging.Constants;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Cache Manager Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CacheManagerConfigImpl#getCacheAgentQuorum <em>Cache Agent Quorum</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CacheManagerConfigImpl#getBackingStore <em>Backing Store</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CacheManagerConfigImpl#getDomainObjects <em>Domain Objects</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CacheManagerConfigImpl#getProvider <em>Provider</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CacheManagerConfigImpl#getBackupCopies <em>Backup Copies</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CacheManagerConfigImpl#getEntityCacheSize <em>Entity Cache Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CacheManagerConfigImpl#getObjectTable <em>Object Table</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CacheManagerConfigImpl#getDiscoveryURL <em>Discovery URL</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CacheManagerConfigImpl#getListenURL <em>Listen URL</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CacheManagerConfigImpl#getRemoteListenURL <em>Remote Listen URL</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CacheManagerConfigImpl#getProtocolTimeout <em>Protocol Timeout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CacheManagerConfigImpl#getReadTimeout <em>Read Timeout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CacheManagerConfigImpl#getWriteTimeout <em>Write Timeout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CacheManagerConfigImpl#getLockTimeout <em>Lock Timeout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CacheManagerConfigImpl#getShoutdownWait <em>Shoutdown Wait</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CacheManagerConfigImpl#getWorkerthreadsCount <em>Workerthreads Count</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CacheManagerConfigImpl#getExplicitTuple <em>Explicit Tuple</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CacheManagerConfigImpl#getSecurityConfig <em>Security Config</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CacheManagerConfigImpl extends ObjectManagerConfigImpl implements CacheManagerConfig {
	/**
	 * The cached value of the '{@link #getCacheAgentQuorum() <em>Cache Agent Quorum</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCacheAgentQuorum()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig cacheAgentQuorum;

	/**
	 * The cached value of the '{@link #getBackingStore() <em>Backing Store</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBackingStore()
	 * @generated
	 * @ordered
	 */
	protected BackingStoreConfig backingStore;

	/**
	 * The cached value of the '{@link #getDomainObjects() <em>Domain Objects</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomainObjects()
	 * @generated
	 * @ordered
	 */
	protected DomainObjectsConfig domainObjects;

	/**
	 * The cached value of the '{@link #getProvider() <em>Provider</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProvider()
	 * @generated
	 * @ordered
	 */
	protected ProviderConfig provider;

	/**
	 * The cached value of the '{@link #getBackupCopies() <em>Backup Copies</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBackupCopies()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig backupCopies;

	/**
	 * The cached value of the '{@link #getEntityCacheSize() <em>Entity Cache Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntityCacheSize()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig entityCacheSize;

	/**
	 * The cached value of the '{@link #getObjectTable() <em>Object Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectTable()
	 * @generated
	 * @ordered
	 */
	protected ObjectTableConfig objectTable;

	/**
	 * The cached value of the '{@link #getDiscoveryURL() <em>Discovery URL</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiscoveryURL()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig discoveryURL;

	/**
	 * The cached value of the '{@link #getListenURL() <em>Listen URL</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getListenURL()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig listenURL;

	/**
	 * The cached value of the '{@link #getRemoteListenURL() <em>Remote Listen URL</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRemoteListenURL()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig remoteListenURL;

	/**
	 * The cached value of the '{@link #getProtocolTimeout() <em>Protocol Timeout</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProtocolTimeout()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig protocolTimeout;

	/**
	 * The cached value of the '{@link #getReadTimeout() <em>Read Timeout</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReadTimeout()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig readTimeout;

	/**
	 * The cached value of the '{@link #getWriteTimeout() <em>Write Timeout</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWriteTimeout()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig writeTimeout;

	/**
	 * The cached value of the '{@link #getLockTimeout() <em>Lock Timeout</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLockTimeout()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig lockTimeout;

	/**
	 * The cached value of the '{@link #getShoutdownWait() <em>Shoutdown Wait</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShoutdownWait()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig shoutdownWait;

	/**
	 * The cached value of the '{@link #getWorkerthreadsCount() <em>Workerthreads Count</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWorkerthreadsCount()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig workerthreadsCount;

	/**
	 * The cached value of the '{@link #getExplicitTuple() <em>Explicit Tuple</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExplicitTuple()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig explicitTuple;

	/**
	 * The cached value of the '{@link #getSecurityConfig() <em>Security Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecurityConfig()
	 * @generated
	 * @ordered
	 */
	protected CacheManagerSecurityConfig securityConfig;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CacheManagerConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getCacheManagerConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getCacheAgentQuorum() {
		return cacheAgentQuorum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCacheAgentQuorum(OverrideConfig newCacheAgentQuorum, NotificationChain msgs) {
		OverrideConfig oldCacheAgentQuorum = cacheAgentQuorum;
		cacheAgentQuorum = newCacheAgentQuorum;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__CACHE_AGENT_QUORUM, oldCacheAgentQuorum, newCacheAgentQuorum);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCacheAgentQuorum(OverrideConfig newCacheAgentQuorum) {
		if (newCacheAgentQuorum != cacheAgentQuorum) {
			NotificationChain msgs = null;
			if (cacheAgentQuorum != null)
				msgs = ((InternalEObject)cacheAgentQuorum).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__CACHE_AGENT_QUORUM, null, msgs);
			if (newCacheAgentQuorum != null)
				msgs = ((InternalEObject)newCacheAgentQuorum).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__CACHE_AGENT_QUORUM, null, msgs);
			msgs = basicSetCacheAgentQuorum(newCacheAgentQuorum, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__CACHE_AGENT_QUORUM, newCacheAgentQuorum, newCacheAgentQuorum));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BackingStoreConfig getBackingStore() {
		return backingStore;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBackingStore(BackingStoreConfig newBackingStore, NotificationChain msgs) {
		BackingStoreConfig oldBackingStore = backingStore;
		backingStore = newBackingStore;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__BACKING_STORE, oldBackingStore, newBackingStore);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBackingStore(BackingStoreConfig newBackingStore) {
		if (newBackingStore != backingStore) {
			NotificationChain msgs = null;
			if (backingStore != null)
				msgs = ((InternalEObject)backingStore).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__BACKING_STORE, null, msgs);
			if (newBackingStore != null)
				msgs = ((InternalEObject)newBackingStore).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__BACKING_STORE, null, msgs);
			msgs = basicSetBackingStore(newBackingStore, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__BACKING_STORE, newBackingStore, newBackingStore));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainObjectsConfig getDomainObjects() {
		return domainObjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDomainObjects(DomainObjectsConfig newDomainObjects, NotificationChain msgs) {
		DomainObjectsConfig oldDomainObjects = domainObjects;
		domainObjects = newDomainObjects;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__DOMAIN_OBJECTS, oldDomainObjects, newDomainObjects);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDomainObjects(DomainObjectsConfig newDomainObjects) {
		if (newDomainObjects != domainObjects) {
			NotificationChain msgs = null;
			if (domainObjects != null)
				msgs = ((InternalEObject)domainObjects).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__DOMAIN_OBJECTS, null, msgs);
			if (newDomainObjects != null)
				msgs = ((InternalEObject)newDomainObjects).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__DOMAIN_OBJECTS, null, msgs);
			msgs = basicSetDomainObjects(newDomainObjects, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__DOMAIN_OBJECTS, newDomainObjects, newDomainObjects));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProviderConfig getProvider() {
		return provider;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProvider(ProviderConfig newProvider, NotificationChain msgs) {
		ProviderConfig oldProvider = provider;
		provider = newProvider;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__PROVIDER, oldProvider, newProvider);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProvider(ProviderConfig newProvider) {
		if (newProvider != provider) {
			NotificationChain msgs = null;
			if (provider != null)
				msgs = ((InternalEObject)provider).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__PROVIDER, null, msgs);
			if (newProvider != null)
				msgs = ((InternalEObject)newProvider).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__PROVIDER, null, msgs);
			msgs = basicSetProvider(newProvider, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__PROVIDER, newProvider, newProvider));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getBackupCopies() {
		return backupCopies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBackupCopies(OverrideConfig newBackupCopies, NotificationChain msgs) {
		OverrideConfig oldBackupCopies = backupCopies;
		backupCopies = newBackupCopies;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__BACKUP_COPIES, oldBackupCopies, newBackupCopies);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBackupCopies(OverrideConfig newBackupCopies) {
		if (newBackupCopies != backupCopies) {
			NotificationChain msgs = null;
			if (backupCopies != null)
				msgs = ((InternalEObject)backupCopies).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__BACKUP_COPIES, null, msgs);
			if (newBackupCopies != null)
				msgs = ((InternalEObject)newBackupCopies).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__BACKUP_COPIES, null, msgs);
			msgs = basicSetBackupCopies(newBackupCopies, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__BACKUP_COPIES, newBackupCopies, newBackupCopies));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getEntityCacheSize() {
		return entityCacheSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEntityCacheSize(OverrideConfig newEntityCacheSize, NotificationChain msgs) {
		OverrideConfig oldEntityCacheSize = entityCacheSize;
		entityCacheSize = newEntityCacheSize;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__ENTITY_CACHE_SIZE, oldEntityCacheSize, newEntityCacheSize);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntityCacheSize(OverrideConfig newEntityCacheSize) {
		if (newEntityCacheSize != entityCacheSize) {
			NotificationChain msgs = null;
			if (entityCacheSize != null)
				msgs = ((InternalEObject)entityCacheSize).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__ENTITY_CACHE_SIZE, null, msgs);
			if (newEntityCacheSize != null)
				msgs = ((InternalEObject)newEntityCacheSize).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__ENTITY_CACHE_SIZE, null, msgs);
			msgs = basicSetEntityCacheSize(newEntityCacheSize, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__ENTITY_CACHE_SIZE, newEntityCacheSize, newEntityCacheSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ObjectTableConfig getObjectTable() {
		return objectTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetObjectTable(ObjectTableConfig newObjectTable, NotificationChain msgs) {
		ObjectTableConfig oldObjectTable = objectTable;
		objectTable = newObjectTable;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__OBJECT_TABLE, oldObjectTable, newObjectTable);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setObjectTable(ObjectTableConfig newObjectTable) {
		if (newObjectTable != objectTable) {
			NotificationChain msgs = null;
			if (objectTable != null)
				msgs = ((InternalEObject)objectTable).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__OBJECT_TABLE, null, msgs);
			if (newObjectTable != null)
				msgs = ((InternalEObject)newObjectTable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__OBJECT_TABLE, null, msgs);
			msgs = basicSetObjectTable(newObjectTable, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__OBJECT_TABLE, newObjectTable, newObjectTable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getDiscoveryURL() {
		return discoveryURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDiscoveryURL(OverrideConfig newDiscoveryURL, NotificationChain msgs) {
		OverrideConfig oldDiscoveryURL = discoveryURL;
		discoveryURL = newDiscoveryURL;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__DISCOVERY_URL, oldDiscoveryURL, newDiscoveryURL);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDiscoveryURL(OverrideConfig newDiscoveryURL) {
		if (newDiscoveryURL != discoveryURL) {
			NotificationChain msgs = null;
			if (discoveryURL != null)
				msgs = ((InternalEObject)discoveryURL).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__DISCOVERY_URL, null, msgs);
			if (newDiscoveryURL != null)
				msgs = ((InternalEObject)newDiscoveryURL).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__DISCOVERY_URL, null, msgs);
			msgs = basicSetDiscoveryURL(newDiscoveryURL, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__DISCOVERY_URL, newDiscoveryURL, newDiscoveryURL));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getListenURL() {
		return listenURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetListenURL(OverrideConfig newListenURL, NotificationChain msgs) {
		OverrideConfig oldListenURL = listenURL;
		listenURL = newListenURL;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__LISTEN_URL, oldListenURL, newListenURL);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setListenURL(OverrideConfig newListenURL) {
		if (newListenURL != listenURL) {
			NotificationChain msgs = null;
			if (listenURL != null)
				msgs = ((InternalEObject)listenURL).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__LISTEN_URL, null, msgs);
			if (newListenURL != null)
				msgs = ((InternalEObject)newListenURL).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__LISTEN_URL, null, msgs);
			msgs = basicSetListenURL(newListenURL, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__LISTEN_URL, newListenURL, newListenURL));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getRemoteListenURL() {
		return remoteListenURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRemoteListenURL(OverrideConfig newRemoteListenURL, NotificationChain msgs) {
		OverrideConfig oldRemoteListenURL = remoteListenURL;
		remoteListenURL = newRemoteListenURL;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__REMOTE_LISTEN_URL, oldRemoteListenURL, newRemoteListenURL);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRemoteListenURL(OverrideConfig newRemoteListenURL) {
		if (newRemoteListenURL != remoteListenURL) {
			NotificationChain msgs = null;
			if (remoteListenURL != null)
				msgs = ((InternalEObject)remoteListenURL).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__REMOTE_LISTEN_URL, null, msgs);
			if (newRemoteListenURL != null)
				msgs = ((InternalEObject)newRemoteListenURL).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__REMOTE_LISTEN_URL, null, msgs);
			msgs = basicSetRemoteListenURL(newRemoteListenURL, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__REMOTE_LISTEN_URL, newRemoteListenURL, newRemoteListenURL));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getProtocolTimeout() {
		return protocolTimeout;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProtocolTimeout(OverrideConfig newProtocolTimeout, NotificationChain msgs) {
		OverrideConfig oldProtocolTimeout = protocolTimeout;
		protocolTimeout = newProtocolTimeout;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__PROTOCOL_TIMEOUT, oldProtocolTimeout, newProtocolTimeout);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProtocolTimeout(OverrideConfig newProtocolTimeout) {
		if (newProtocolTimeout != protocolTimeout) {
			NotificationChain msgs = null;
			if (protocolTimeout != null)
				msgs = ((InternalEObject)protocolTimeout).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__PROTOCOL_TIMEOUT, null, msgs);
			if (newProtocolTimeout != null)
				msgs = ((InternalEObject)newProtocolTimeout).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__PROTOCOL_TIMEOUT, null, msgs);
			msgs = basicSetProtocolTimeout(newProtocolTimeout, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__PROTOCOL_TIMEOUT, newProtocolTimeout, newProtocolTimeout));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getReadTimeout() {
		return readTimeout;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReadTimeout(OverrideConfig newReadTimeout, NotificationChain msgs) {
		OverrideConfig oldReadTimeout = readTimeout;
		readTimeout = newReadTimeout;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__READ_TIMEOUT, oldReadTimeout, newReadTimeout);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReadTimeout(OverrideConfig newReadTimeout) {
		if (newReadTimeout != readTimeout) {
			NotificationChain msgs = null;
			if (readTimeout != null)
				msgs = ((InternalEObject)readTimeout).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__READ_TIMEOUT, null, msgs);
			if (newReadTimeout != null)
				msgs = ((InternalEObject)newReadTimeout).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__READ_TIMEOUT, null, msgs);
			msgs = basicSetReadTimeout(newReadTimeout, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__READ_TIMEOUT, newReadTimeout, newReadTimeout));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getWriteTimeout() {
		return writeTimeout;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetWriteTimeout(OverrideConfig newWriteTimeout, NotificationChain msgs) {
		OverrideConfig oldWriteTimeout = writeTimeout;
		writeTimeout = newWriteTimeout;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__WRITE_TIMEOUT, oldWriteTimeout, newWriteTimeout);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWriteTimeout(OverrideConfig newWriteTimeout) {
		if (newWriteTimeout != writeTimeout) {
			NotificationChain msgs = null;
			if (writeTimeout != null)
				msgs = ((InternalEObject)writeTimeout).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__WRITE_TIMEOUT, null, msgs);
			if (newWriteTimeout != null)
				msgs = ((InternalEObject)newWriteTimeout).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__WRITE_TIMEOUT, null, msgs);
			msgs = basicSetWriteTimeout(newWriteTimeout, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__WRITE_TIMEOUT, newWriteTimeout, newWriteTimeout));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getLockTimeout() {
		return lockTimeout;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLockTimeout(OverrideConfig newLockTimeout, NotificationChain msgs) {
		OverrideConfig oldLockTimeout = lockTimeout;
		lockTimeout = newLockTimeout;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__LOCK_TIMEOUT, oldLockTimeout, newLockTimeout);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLockTimeout(OverrideConfig newLockTimeout) {
		if (newLockTimeout != lockTimeout) {
			NotificationChain msgs = null;
			if (lockTimeout != null)
				msgs = ((InternalEObject)lockTimeout).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__LOCK_TIMEOUT, null, msgs);
			if (newLockTimeout != null)
				msgs = ((InternalEObject)newLockTimeout).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__LOCK_TIMEOUT, null, msgs);
			msgs = basicSetLockTimeout(newLockTimeout, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__LOCK_TIMEOUT, newLockTimeout, newLockTimeout));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getShoutdownWait() {
		return shoutdownWait;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetShoutdownWait(OverrideConfig newShoutdownWait, NotificationChain msgs) {
		OverrideConfig oldShoutdownWait = shoutdownWait;
		shoutdownWait = newShoutdownWait;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__SHOUTDOWN_WAIT, oldShoutdownWait, newShoutdownWait);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShoutdownWait(OverrideConfig newShoutdownWait) {
		if (newShoutdownWait != shoutdownWait) {
			NotificationChain msgs = null;
			if (shoutdownWait != null)
				msgs = ((InternalEObject)shoutdownWait).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__SHOUTDOWN_WAIT, null, msgs);
			if (newShoutdownWait != null)
				msgs = ((InternalEObject)newShoutdownWait).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__SHOUTDOWN_WAIT, null, msgs);
			msgs = basicSetShoutdownWait(newShoutdownWait, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__SHOUTDOWN_WAIT, newShoutdownWait, newShoutdownWait));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getWorkerthreadsCount() {
		return workerthreadsCount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetWorkerthreadsCount(OverrideConfig newWorkerthreadsCount, NotificationChain msgs) {
		OverrideConfig oldWorkerthreadsCount = workerthreadsCount;
		workerthreadsCount = newWorkerthreadsCount;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__WORKERTHREADS_COUNT, oldWorkerthreadsCount, newWorkerthreadsCount);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWorkerthreadsCount(OverrideConfig newWorkerthreadsCount) {
		if (newWorkerthreadsCount != workerthreadsCount) {
			NotificationChain msgs = null;
			if (workerthreadsCount != null)
				msgs = ((InternalEObject)workerthreadsCount).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__WORKERTHREADS_COUNT, null, msgs);
			if (newWorkerthreadsCount != null)
				msgs = ((InternalEObject)newWorkerthreadsCount).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__WORKERTHREADS_COUNT, null, msgs);
			msgs = basicSetWorkerthreadsCount(newWorkerthreadsCount, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__WORKERTHREADS_COUNT, newWorkerthreadsCount, newWorkerthreadsCount));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getExplicitTuple() {
		return explicitTuple;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExplicitTuple(OverrideConfig newExplicitTuple, NotificationChain msgs) {
		OverrideConfig oldExplicitTuple = explicitTuple;
		explicitTuple = newExplicitTuple;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__EXPLICIT_TUPLE, oldExplicitTuple, newExplicitTuple);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExplicitTuple(OverrideConfig newExplicitTuple) {
		if (newExplicitTuple != explicitTuple) {
			NotificationChain msgs = null;
			if (explicitTuple != null)
				msgs = ((InternalEObject)explicitTuple).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__EXPLICIT_TUPLE, null, msgs);
			if (newExplicitTuple != null)
				msgs = ((InternalEObject)newExplicitTuple).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__EXPLICIT_TUPLE, null, msgs);
			msgs = basicSetExplicitTuple(newExplicitTuple, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__EXPLICIT_TUPLE, newExplicitTuple, newExplicitTuple));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CacheManagerSecurityConfig getSecurityConfig() {
		return securityConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSecurityConfig(CacheManagerSecurityConfig newSecurityConfig, NotificationChain msgs) {
		CacheManagerSecurityConfig oldSecurityConfig = securityConfig;
		securityConfig = newSecurityConfig;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__SECURITY_CONFIG, oldSecurityConfig, newSecurityConfig);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSecurityConfig(CacheManagerSecurityConfig newSecurityConfig) {
		if (newSecurityConfig != securityConfig) {
			NotificationChain msgs = null;
			if (securityConfig != null)
				msgs = ((InternalEObject)securityConfig).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__SECURITY_CONFIG, null, msgs);
			if (newSecurityConfig != null)
				msgs = ((InternalEObject)newSecurityConfig).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_CONFIG__SECURITY_CONFIG, null, msgs);
			msgs = basicSetSecurityConfig(newSecurityConfig, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_CONFIG__SECURITY_CONFIG, newSecurityConfig, newSecurityConfig));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public Map<Object, Object> toProperties() {
		final Properties props = new Properties();

		CddTools.addEntryFromMixed(
        		props, SystemProperty.CLUSTER_MIN_CACHE_SERVERS.getPropertyName(),
        		this.cacheAgentQuorum, true, "0");

//		if (null != this.eviction) {
//			if ((null != this.eviction.maxSize)
//					&& !this.eviction.maxSize.trim().isEmpty()) {
//				props.setProperty(
//						SystemProperty.CLUSTER_LIMITED_SIZE.getPropertyName(),
//						this.eviction.maxSize);
//			}
//			if ((null != this.eviction.maxTime)
//					&& !this.eviction.maxTime.trim().isEmpty()) {
//				props.setProperty(
//						SystemProperty.CLUSTER_LIMITED_TIME.getPropertyName(),
//						this.eviction.maxTime);
//			}
//		}
//
		if (null != this.backingStore) {
			props.putAll(this.backingStore.toProperties());
		}

		CddTools.addEntryFromMixed(
        		props, SystemProperty.CLUSTER_BACKUP_COUNT.getPropertyName(),
        		this.backupCopies, true);

		if (null != this.domainObjects) {
			props.putAll(this.domainObjects.toProperties());
			props.put(Constants.PROPERTY_NAME_OM_CACHE_ADVANCED_ENTITIES,
					new DomainObjectsConfigSerializableWrapper(this.domainObjects));
		}

		CddTools.addEntryFromMixed(props,
				SystemProperty.CLUSTER_LIMITED_SIZE.getPropertyName(),
				this.entityCacheSize, true);
		CddTools.addEntryFromMixed(props, SystemProperty.AS_DISCOVER_URL.getPropertyName(),
				this.discoveryURL, true);
		CddTools.addEntryFromMixed(props, SystemProperty.AS_LISTEN_URL.getPropertyName(),
				this.listenURL, true);
		CddTools.addEntryFromMixed(props, SystemProperty.AS_REMOTE_LISTEN_URL.getPropertyName(),
				this.remoteListenURL, true);
		CddTools.addEntryFromMixed(props, SystemProperty.AS_PROTOCOL_TIMEOUT.getPropertyName(),
				this.protocolTimeout, true);
		CddTools.addEntryFromMixed(props,
				SystemProperty.AS_READ_TIMEOUT.getPropertyName(), this.readTimeout, true);
		CddTools.addEntryFromMixed(props,
				SystemProperty.AS_WRITE_TIMEOUT.getPropertyName(), this.writeTimeout, true);
		CddTools.addEntryFromMixed(props, SystemProperty.AS_LOCK_TTL.getPropertyName(),
				this.lockTimeout, true);
		CddTools.addEntryFromMixed(props,
				SystemProperty.AS_SHUTDOWN_WAIT_MILLIS.getPropertyName(),
				this.shoutdownWait, true);
		CddTools.addEntryFromMixed(props,
				SystemProperty.AS_WORKERTHREADS_COUNT.getPropertyName(),
				this.workerthreadsCount, true);
		CddTools.addEntryFromMixed(props, SystemProperty.PROP_TUPLE_EXPLICIT.getPropertyName(),
				this.explicitTuple, true);

		if (null != this.objectTable) {
			props.putAll(this.objectTable.toProperties());
		}

		props.putAll(this.provider.toProperties());

		//Cluster level security settings
		if (this.securityConfig != null) {
			props.putAll(this.securityConfig.toProperties());
		}

		return props;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.CACHE_MANAGER_CONFIG__CACHE_AGENT_QUORUM:
				return basicSetCacheAgentQuorum(null, msgs);
			case CddPackage.CACHE_MANAGER_CONFIG__BACKING_STORE:
				return basicSetBackingStore(null, msgs);
			case CddPackage.CACHE_MANAGER_CONFIG__DOMAIN_OBJECTS:
				return basicSetDomainObjects(null, msgs);
			case CddPackage.CACHE_MANAGER_CONFIG__PROVIDER:
				return basicSetProvider(null, msgs);
			case CddPackage.CACHE_MANAGER_CONFIG__BACKUP_COPIES:
				return basicSetBackupCopies(null, msgs);
			case CddPackage.CACHE_MANAGER_CONFIG__ENTITY_CACHE_SIZE:
				return basicSetEntityCacheSize(null, msgs);
			case CddPackage.CACHE_MANAGER_CONFIG__OBJECT_TABLE:
				return basicSetObjectTable(null, msgs);
			case CddPackage.CACHE_MANAGER_CONFIG__DISCOVERY_URL:
				return basicSetDiscoveryURL(null, msgs);
			case CddPackage.CACHE_MANAGER_CONFIG__LISTEN_URL:
				return basicSetListenURL(null, msgs);
			case CddPackage.CACHE_MANAGER_CONFIG__REMOTE_LISTEN_URL:
				return basicSetRemoteListenURL(null, msgs);
			case CddPackage.CACHE_MANAGER_CONFIG__PROTOCOL_TIMEOUT:
				return basicSetProtocolTimeout(null, msgs);
			case CddPackage.CACHE_MANAGER_CONFIG__READ_TIMEOUT:
				return basicSetReadTimeout(null, msgs);
			case CddPackage.CACHE_MANAGER_CONFIG__WRITE_TIMEOUT:
				return basicSetWriteTimeout(null, msgs);
			case CddPackage.CACHE_MANAGER_CONFIG__LOCK_TIMEOUT:
				return basicSetLockTimeout(null, msgs);
			case CddPackage.CACHE_MANAGER_CONFIG__SHOUTDOWN_WAIT:
				return basicSetShoutdownWait(null, msgs);
			case CddPackage.CACHE_MANAGER_CONFIG__WORKERTHREADS_COUNT:
				return basicSetWorkerthreadsCount(null, msgs);
			case CddPackage.CACHE_MANAGER_CONFIG__EXPLICIT_TUPLE:
				return basicSetExplicitTuple(null, msgs);
			case CddPackage.CACHE_MANAGER_CONFIG__SECURITY_CONFIG:
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
			case CddPackage.CACHE_MANAGER_CONFIG__CACHE_AGENT_QUORUM:
				return getCacheAgentQuorum();
			case CddPackage.CACHE_MANAGER_CONFIG__BACKING_STORE:
				return getBackingStore();
			case CddPackage.CACHE_MANAGER_CONFIG__DOMAIN_OBJECTS:
				return getDomainObjects();
			case CddPackage.CACHE_MANAGER_CONFIG__PROVIDER:
				return getProvider();
			case CddPackage.CACHE_MANAGER_CONFIG__BACKUP_COPIES:
				return getBackupCopies();
			case CddPackage.CACHE_MANAGER_CONFIG__ENTITY_CACHE_SIZE:
				return getEntityCacheSize();
			case CddPackage.CACHE_MANAGER_CONFIG__OBJECT_TABLE:
				return getObjectTable();
			case CddPackage.CACHE_MANAGER_CONFIG__DISCOVERY_URL:
				return getDiscoveryURL();
			case CddPackage.CACHE_MANAGER_CONFIG__LISTEN_URL:
				return getListenURL();
			case CddPackage.CACHE_MANAGER_CONFIG__REMOTE_LISTEN_URL:
				return getRemoteListenURL();
			case CddPackage.CACHE_MANAGER_CONFIG__PROTOCOL_TIMEOUT:
				return getProtocolTimeout();
			case CddPackage.CACHE_MANAGER_CONFIG__READ_TIMEOUT:
				return getReadTimeout();
			case CddPackage.CACHE_MANAGER_CONFIG__WRITE_TIMEOUT:
				return getWriteTimeout();
			case CddPackage.CACHE_MANAGER_CONFIG__LOCK_TIMEOUT:
				return getLockTimeout();
			case CddPackage.CACHE_MANAGER_CONFIG__SHOUTDOWN_WAIT:
				return getShoutdownWait();
			case CddPackage.CACHE_MANAGER_CONFIG__WORKERTHREADS_COUNT:
				return getWorkerthreadsCount();
			case CddPackage.CACHE_MANAGER_CONFIG__EXPLICIT_TUPLE:
				return getExplicitTuple();
			case CddPackage.CACHE_MANAGER_CONFIG__SECURITY_CONFIG:
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
			case CddPackage.CACHE_MANAGER_CONFIG__CACHE_AGENT_QUORUM:
				setCacheAgentQuorum((OverrideConfig)newValue);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__BACKING_STORE:
				setBackingStore((BackingStoreConfig)newValue);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__DOMAIN_OBJECTS:
				setDomainObjects((DomainObjectsConfig)newValue);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__PROVIDER:
				setProvider((ProviderConfig)newValue);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__BACKUP_COPIES:
				setBackupCopies((OverrideConfig)newValue);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__ENTITY_CACHE_SIZE:
				setEntityCacheSize((OverrideConfig)newValue);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__OBJECT_TABLE:
				setObjectTable((ObjectTableConfig)newValue);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__DISCOVERY_URL:
				setDiscoveryURL((OverrideConfig)newValue);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__LISTEN_URL:
				setListenURL((OverrideConfig)newValue);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__REMOTE_LISTEN_URL:
				setRemoteListenURL((OverrideConfig)newValue);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__PROTOCOL_TIMEOUT:
				setProtocolTimeout((OverrideConfig)newValue);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__READ_TIMEOUT:
				setReadTimeout((OverrideConfig)newValue);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__WRITE_TIMEOUT:
				setWriteTimeout((OverrideConfig)newValue);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__LOCK_TIMEOUT:
				setLockTimeout((OverrideConfig)newValue);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__SHOUTDOWN_WAIT:
				setShoutdownWait((OverrideConfig)newValue);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__WORKERTHREADS_COUNT:
				setWorkerthreadsCount((OverrideConfig)newValue);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__EXPLICIT_TUPLE:
				setExplicitTuple((OverrideConfig)newValue);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__SECURITY_CONFIG:
				setSecurityConfig((CacheManagerSecurityConfig)newValue);
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
			case CddPackage.CACHE_MANAGER_CONFIG__CACHE_AGENT_QUORUM:
				setCacheAgentQuorum((OverrideConfig)null);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__BACKING_STORE:
				setBackingStore((BackingStoreConfig)null);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__DOMAIN_OBJECTS:
				setDomainObjects((DomainObjectsConfig)null);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__PROVIDER:
				setProvider((ProviderConfig)null);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__BACKUP_COPIES:
				setBackupCopies((OverrideConfig)null);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__ENTITY_CACHE_SIZE:
				setEntityCacheSize((OverrideConfig)null);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__OBJECT_TABLE:
				setObjectTable((ObjectTableConfig)null);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__DISCOVERY_URL:
				setDiscoveryURL((OverrideConfig)null);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__LISTEN_URL:
				setListenURL((OverrideConfig)null);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__REMOTE_LISTEN_URL:
				setRemoteListenURL((OverrideConfig)null);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__PROTOCOL_TIMEOUT:
				setProtocolTimeout((OverrideConfig)null);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__READ_TIMEOUT:
				setReadTimeout((OverrideConfig)null);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__WRITE_TIMEOUT:
				setWriteTimeout((OverrideConfig)null);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__LOCK_TIMEOUT:
				setLockTimeout((OverrideConfig)null);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__SHOUTDOWN_WAIT:
				setShoutdownWait((OverrideConfig)null);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__WORKERTHREADS_COUNT:
				setWorkerthreadsCount((OverrideConfig)null);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__EXPLICIT_TUPLE:
				setExplicitTuple((OverrideConfig)null);
				return;
			case CddPackage.CACHE_MANAGER_CONFIG__SECURITY_CONFIG:
				setSecurityConfig((CacheManagerSecurityConfig)null);
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
			case CddPackage.CACHE_MANAGER_CONFIG__CACHE_AGENT_QUORUM:
				return cacheAgentQuorum != null;
			case CddPackage.CACHE_MANAGER_CONFIG__BACKING_STORE:
				return backingStore != null;
			case CddPackage.CACHE_MANAGER_CONFIG__DOMAIN_OBJECTS:
				return domainObjects != null;
			case CddPackage.CACHE_MANAGER_CONFIG__PROVIDER:
				return provider != null;
			case CddPackage.CACHE_MANAGER_CONFIG__BACKUP_COPIES:
				return backupCopies != null;
			case CddPackage.CACHE_MANAGER_CONFIG__ENTITY_CACHE_SIZE:
				return entityCacheSize != null;
			case CddPackage.CACHE_MANAGER_CONFIG__OBJECT_TABLE:
				return objectTable != null;
			case CddPackage.CACHE_MANAGER_CONFIG__DISCOVERY_URL:
				return discoveryURL != null;
			case CddPackage.CACHE_MANAGER_CONFIG__LISTEN_URL:
				return listenURL != null;
			case CddPackage.CACHE_MANAGER_CONFIG__REMOTE_LISTEN_URL:
				return remoteListenURL != null;
			case CddPackage.CACHE_MANAGER_CONFIG__PROTOCOL_TIMEOUT:
				return protocolTimeout != null;
			case CddPackage.CACHE_MANAGER_CONFIG__READ_TIMEOUT:
				return readTimeout != null;
			case CddPackage.CACHE_MANAGER_CONFIG__WRITE_TIMEOUT:
				return writeTimeout != null;
			case CddPackage.CACHE_MANAGER_CONFIG__LOCK_TIMEOUT:
				return lockTimeout != null;
			case CddPackage.CACHE_MANAGER_CONFIG__SHOUTDOWN_WAIT:
				return shoutdownWait != null;
			case CddPackage.CACHE_MANAGER_CONFIG__WORKERTHREADS_COUNT:
				return workerthreadsCount != null;
			case CddPackage.CACHE_MANAGER_CONFIG__EXPLICIT_TUPLE:
				return explicitTuple != null;
			case CddPackage.CACHE_MANAGER_CONFIG__SECURITY_CONFIG:
				return securityConfig != null;
		}
		return super.eIsSet(featureID);
	}

} //CacheManagerConfigImpl
