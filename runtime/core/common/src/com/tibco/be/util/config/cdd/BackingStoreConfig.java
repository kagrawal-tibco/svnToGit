/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Backing Store Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getCacheAside <em>Cache Aside</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getCacheLoaderClass <em>Cache Loader Class</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getDataStorePath <em>Data Store Path</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getEnforcePools <em>Enforce Pools</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getPersistenceOption <em>Persistence Option</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getPersistencePolicy <em>Persistence Policy</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getParallelRecovery <em>Parallel Recovery</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getPrimaryConnection <em>Primary Connection</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getSecondaryConnection <em>Secondary Connection</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getStrategy <em>Strategy</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getBackingStoreConfig()
 * @model extendedMetaData="name='backing-store-type' kind='elementOnly'"
 * @generated
 */
public interface BackingStoreConfig extends EObject {

    public static final String PERSISTENCE_OPTION_NONE = "None";
    public static final String PERSISTENCE_OPTION_SHARED_ALL = "Shared All";
    public static final String PERSISTENCE_OPTION_SHARED_ALL_ALT = "SharedAll";
    public static final String PERSISTENCE_OPTION_SHARED_NOTHING = "Shared Nothing";
    public static final String PERSISTENCE_OPTION_SHARED_NOTHING_ALT = "SharedNothing";
	
	public static final String TYPE_ORACLE = "Oracle";
    public static final String TYPE_SQLSERVER = "SQL Server";
    public static final String TYPE_DB2 = "DB2";
    public static final String TYPE_MYSQL = "MySQL";
    public static final String TYPE_POSTGRES = "PostgreSQL";
    public static final String TYPE_BDB = "Berkeley DB";
    
    public static final String PERSISTENCE_POLICY_ASYNC = "ASYNC";
    public static final String PERSISTENCE_POLICY_SYNC = "SYNC";
	
	/**
	 * Returns the value of the '<em><b>Cache Aside</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Enables cache-aside if true.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Cache Aside</em>' containment reference.
	 * @see #setCacheAside(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getBackingStoreConfig_CacheAside()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='cache-aside' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getCacheAside();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getCacheAside <em>Cache Aside</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache Aside</em>' containment reference.
	 * @see #getCacheAside()
	 * @generated
	 */
	void setCacheAside(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Cache Loader Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Coherence backing store implementation.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Cache Loader Class</em>' containment reference.
	 * @see #setCacheLoaderClass(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getBackingStoreConfig_CacheLoaderClass()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='cache-loader-class' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getCacheLoaderClass();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getCacheLoaderClass <em>Cache Loader Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache Loader Class</em>' containment reference.
	 * @see #getCacheLoaderClass()
	 * @generated
	 */
	void setCacheLoaderClass(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Data Store Path</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Specifies the path for the data store.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Data Store Path</em>' containment reference.
	 * @see #setDataStorePath(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getBackingStoreConfig_DataStorePath()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='data-store-path' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getDataStorePath();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getDataStorePath <em>Data Store Path</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Store Path</em>' containment reference.
	 * @see #getDataStorePath()
	 * @generated
	 */
	void setDataStorePath(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Enforce Pools</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Enables all the connection pools.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Enforce Pools</em>' containment reference.
	 * @see #setEnforcePools(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getBackingStoreConfig_EnforcePools()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='enforce-pools' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getEnforcePools();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getEnforcePools <em>Enforce Pools</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enforce Pools</em>' containment reference.
	 * @see #getEnforcePools()
	 * @generated
	 */
	void setEnforcePools(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Persistence Option</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Enables the backing store - None, Shared All, Shared Nothing
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Persistence Option</em>' containment reference.
	 * @see #setPersistenceOption(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getBackingStoreConfig_PersistenceOption()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='persistence-option' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getPersistenceOption();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getPersistenceOption <em>Persistence Option</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Persistence Option</em>' containment reference.
	 * @see #getPersistenceOption()
	 * @generated
	 */
	void setPersistenceOption(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Persistence Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Persistence policy to be used (SYNC or ASYNC).
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Persistence Policy</em>' containment reference.
	 * @see #setPersistencePolicy(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getBackingStoreConfig_PersistencePolicy()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='persistence-policy' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getPersistencePolicy();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getPersistencePolicy <em>Persistence Policy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Persistence Policy</em>' containment reference.
	 * @see #getPersistencePolicy()
	 * @generated
	 */
	void setPersistencePolicy(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Parallel Recovery</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Parallel Recovery.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Parallel Recovery</em>' containment reference.
	 * @see #setParallelRecovery(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getBackingStoreConfig_ParallelRecovery()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='parallel-recovery' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getParallelRecovery();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getParallelRecovery <em>Parallel Recovery</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parallel Recovery</em>' containment reference.
	 * @see #getParallelRecovery()
	 * @generated
	 */
	void setParallelRecovery(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Primary Connection</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Primary connection to the database.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Primary Connection</em>' containment reference.
	 * @see #setPrimaryConnection(ConnectionConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getBackingStoreConfig_PrimaryConnection()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='primary-connection' namespace='##targetNamespace'"
	 * @generated
	 */
	ConnectionConfig getPrimaryConnection();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getPrimaryConnection <em>Primary Connection</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primary Connection</em>' containment reference.
	 * @see #getPrimaryConnection()
	 * @generated
	 */
	void setPrimaryConnection(ConnectionConfig value);

	/**
	 * Returns the value of the '<em><b>Secondary Connection</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Secondary connection to the database.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Secondary Connection</em>' containment reference.
	 * @see #setSecondaryConnection(ConnectionConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getBackingStoreConfig_SecondaryConnection()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='secondary-connection' namespace='##targetNamespace'"
	 * @generated
	 */
	ConnectionConfig getSecondaryConnection();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getSecondaryConnection <em>Secondary Connection</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Secondary Connection</em>' containment reference.
	 * @see #getSecondaryConnection()
	 * @generated
	 */
	void setSecondaryConnection(ConnectionConfig value);

	/**
	 * Returns the value of the '<em><b>Strategy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Whether to use jdbc or proprietary mode.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Strategy</em>' containment reference.
	 * @see #setStrategy(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getBackingStoreConfig_Strategy()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='strategy' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getStrategy();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getStrategy <em>Strategy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Strategy</em>' containment reference.
	 * @see #getStrategy()
	 * @generated
	 */
	void setStrategy(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Database type, e.g. oracle or sqlserver.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type</em>' containment reference.
	 * @see #setType(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getBackingStoreConfig_Type()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='type' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getType();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.BackingStoreConfig#getType <em>Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' containment reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(OverrideConfig value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // BackingStoreConfig
