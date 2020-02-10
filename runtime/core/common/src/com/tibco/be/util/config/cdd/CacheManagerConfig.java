/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import java.util.Map;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cache Manager Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getCacheAgentQuorum <em>Cache Agent Quorum</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getBackingStore <em>Backing Store</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getDomainObjects <em>Domain Objects</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getProvider <em>Provider</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getBackupCopies <em>Backup Copies</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getEntityCacheSize <em>Entity Cache Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getObjectTable <em>Object Table</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getDiscoveryURL <em>Discovery URL</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getListenURL <em>Listen URL</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getRemoteListenURL <em>Remote Listen URL</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getProtocolTimeout <em>Protocol Timeout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getReadTimeout <em>Read Timeout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getWriteTimeout <em>Write Timeout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getLockTimeout <em>Lock Timeout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getShoutdownWait <em>Shoutdown Wait</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getWorkerthreadsCount <em>Workerthreads Count</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getExplicitTuple <em>Explicit Tuple</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getSecurityConfig <em>Security Config</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getCacheManagerConfig()
 * @model extendedMetaData="name='cache-manager-type' kind='elementOnly'"
 * @generated
 */
public interface CacheManagerConfig extends ObjectManagerConfig {
	/**
	 * Returns the value of the '<em><b>Cache Agent Quorum</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache Agent Quorum</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cache Agent Quorum</em>' containment reference.
	 * @see #setCacheAgentQuorum(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCacheManagerConfig_CacheAgentQuorum()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='cache-agent-quorum' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getCacheAgentQuorum();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getCacheAgentQuorum <em>Cache Agent Quorum</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache Agent Quorum</em>' containment reference.
	 * @see #getCacheAgentQuorum()
	 * @generated
	 */
	void setCacheAgentQuorum(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Backing Store</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Backing Store</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Backing Store</em>' containment reference.
	 * @see #setBackingStore(BackingStoreConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCacheManagerConfig_BackingStore()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='backing-store' namespace='##targetNamespace'"
	 * @generated
	 */
	BackingStoreConfig getBackingStore();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getBackingStore <em>Backing Store</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Backing Store</em>' containment reference.
	 * @see #getBackingStore()
	 * @generated
	 */
	void setBackingStore(BackingStoreConfig value);

	/**
	 * Returns the value of the '<em><b>Domain Objects</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain Objects</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain Objects</em>' containment reference.
	 * @see #setDomainObjects(DomainObjectsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCacheManagerConfig_DomainObjects()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='domain-objects' namespace='##targetNamespace'"
	 * @generated
	 */
	DomainObjectsConfig getDomainObjects();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getDomainObjects <em>Domain Objects</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain Objects</em>' containment reference.
	 * @see #getDomainObjects()
	 * @generated
	 */
	void setDomainObjects(DomainObjectsConfig value);

	/**
	 * Returns the value of the '<em><b>Provider</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provider</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provider</em>' containment reference.
	 * @see #setProvider(ProviderConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCacheManagerConfig_Provider()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='provider' namespace='##targetNamespace'"
	 * @generated
	 */
	ProviderConfig getProvider();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getProvider <em>Provider</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provider</em>' containment reference.
	 * @see #getProvider()
	 * @generated
	 */
	void setProvider(ProviderConfig value);

	/**
	 * Returns the value of the '<em><b>Backup Copies</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Backup Copies</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Backup Copies</em>' containment reference.
	 * @see #setBackupCopies(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCacheManagerConfig_BackupCopies()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='backup-copies' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getBackupCopies();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getBackupCopies <em>Backup Copies</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Backup Copies</em>' containment reference.
	 * @see #getBackupCopies()
	 * @generated
	 */
	void setBackupCopies(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Entity Cache Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity Cache Size</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity Cache Size</em>' containment reference.
	 * @see #setEntityCacheSize(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCacheManagerConfig_EntityCacheSize()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='entity-cache-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getEntityCacheSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getEntityCacheSize <em>Entity Cache Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entity Cache Size</em>' containment reference.
	 * @see #getEntityCacheSize()
	 * @generated
	 */
	void setEntityCacheSize(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Object Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Object Table</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object Table</em>' containment reference.
	 * @see #setObjectTable(ObjectTableConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCacheManagerConfig_ObjectTable()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='object-table' namespace='##targetNamespace'"
	 * @generated
	 */
	ObjectTableConfig getObjectTable();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getObjectTable <em>Object Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object Table</em>' containment reference.
	 * @see #getObjectTable()
	 * @generated
	 */
	void setObjectTable(ObjectTableConfig value);

	/**
	 * Returns the value of the '<em><b>Discovery URL</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Discovery URL</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Discovery URL</em>' containment reference.
	 * @see #setDiscoveryURL(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCacheManagerConfig_DiscoveryURL()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='discovery-url' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getDiscoveryURL();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getDiscoveryURL <em>Discovery URL</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Discovery URL</em>' containment reference.
	 * @see #getDiscoveryURL()
	 * @generated
	 */
	void setDiscoveryURL(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Listen URL</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Listen URL</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Listen URL</em>' containment reference.
	 * @see #setListenURL(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCacheManagerConfig_ListenURL()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='listen-url' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getListenURL();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getListenURL <em>Listen URL</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Listen URL</em>' containment reference.
	 * @see #getListenURL()
	 * @generated
	 */
	void setListenURL(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Remote Listen URL</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Remote Listen URL</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Remote Listen URL</em>' containment reference.
	 * @see #setRemoteListenURL(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCacheManagerConfig_RemoteListenURL()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='remote-listen-url' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getRemoteListenURL();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getRemoteListenURL <em>Remote Listen URL</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remote Listen URL</em>' containment reference.
	 * @see #getRemoteListenURL()
	 * @generated
	 */
	void setRemoteListenURL(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Protocol Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Protocol Timeout</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Protocol Timeout</em>' containment reference.
	 * @see #setProtocolTimeout(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCacheManagerConfig_ProtocolTimeout()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='protocol-timeout' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getProtocolTimeout();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getProtocolTimeout <em>Protocol Timeout</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Protocol Timeout</em>' containment reference.
	 * @see #getProtocolTimeout()
	 * @generated
	 */
	void setProtocolTimeout(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Read Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Read Timeout</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Read Timeout</em>' containment reference.
	 * @see #setReadTimeout(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCacheManagerConfig_ReadTimeout()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='read-timeout' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getReadTimeout();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getReadTimeout <em>Read Timeout</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Read Timeout</em>' containment reference.
	 * @see #getReadTimeout()
	 * @generated
	 */
	void setReadTimeout(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Write Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Write Timeout</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Write Timeout</em>' containment reference.
	 * @see #setWriteTimeout(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCacheManagerConfig_WriteTimeout()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='write-timeout' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getWriteTimeout();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getWriteTimeout <em>Write Timeout</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Write Timeout</em>' containment reference.
	 * @see #getWriteTimeout()
	 * @generated
	 */
	void setWriteTimeout(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Lock Timeout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lock Timeout</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lock Timeout</em>' containment reference.
	 * @see #setLockTimeout(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCacheManagerConfig_LockTimeout()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='lock-timeout' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getLockTimeout();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getLockTimeout <em>Lock Timeout</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lock Timeout</em>' containment reference.
	 * @see #getLockTimeout()
	 * @generated
	 */
	void setLockTimeout(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Shoutdown Wait</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shoutdown Wait</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shoutdown Wait</em>' containment reference.
	 * @see #setShoutdownWait(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCacheManagerConfig_ShoutdownWait()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='shoutdown-wait' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getShoutdownWait();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getShoutdownWait <em>Shoutdown Wait</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shoutdown Wait</em>' containment reference.
	 * @see #getShoutdownWait()
	 * @generated
	 */
	void setShoutdownWait(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Workerthreads Count</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Workerthreads Count</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Workerthreads Count</em>' containment reference.
	 * @see #setWorkerthreadsCount(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCacheManagerConfig_WorkerthreadsCount()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='workerthreads-count' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getWorkerthreadsCount();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getWorkerthreadsCount <em>Workerthreads Count</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Workerthreads Count</em>' containment reference.
	 * @see #getWorkerthreadsCount()
	 * @generated
	 */
	void setWorkerthreadsCount(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Explicit Tuple</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Explicit Tuple</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Explicit Tuple</em>' containment reference.
	 * @see #setExplicitTuple(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCacheManagerConfig_ExplicitTuple()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='explicit-tuple' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getExplicitTuple();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getExplicitTuple <em>Explicit Tuple</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Explicit Tuple</em>' containment reference.
	 * @see #getExplicitTuple()
	 * @generated
	 */
	void setExplicitTuple(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Security Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Security Config</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Security Config</em>' containment reference.
	 * @see #setSecurityConfig(CacheManagerSecurityConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCacheManagerConfig_SecurityConfig()
	 * @model containment="true"
	 *        extendedMetaData="name='security-config' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	CacheManagerSecurityConfig getSecurityConfig();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CacheManagerConfig#getSecurityConfig <em>Security Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Security Config</em>' containment reference.
	 * @see #getSecurityConfig()
	 * @generated
	 */
	void setSecurityConfig(CacheManagerSecurityConfig value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='return new java.util.Properties();'"
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // CacheManagerConfig
