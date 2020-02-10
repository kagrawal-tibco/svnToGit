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

import com.tibco.be.util.config.cdd.CacheManagerConfig;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.DbConceptsConfig;
import com.tibco.be.util.config.cdd.MemoryManagerConfig;
import com.tibco.be.util.config.cdd.ObjectManagementConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Object Management Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ObjectManagementConfigImpl#getMemoryManager <em>Memory Manager</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ObjectManagementConfigImpl#getCacheManager <em>Cache Manager</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ObjectManagementConfigImpl#getDbConcepts <em>Db Concepts</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ObjectManagementConfigImpl extends EObjectImpl implements ObjectManagementConfig {
	/**
	 * The cached value of the '{@link #getMemoryManager() <em>Memory Manager</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMemoryManager()
	 * @generated
	 * @ordered
	 */
	protected MemoryManagerConfig memoryManager;

	/**
	 * The cached value of the '{@link #getCacheManager() <em>Cache Manager</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCacheManager()
	 * @generated
	 * @ordered
	 */
	protected CacheManagerConfig cacheManager;

	/**
	 * The cached value of the '{@link #getDbConcepts() <em>Db Concepts</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDbConcepts()
	 * @generated
	 * @ordered
	 */
	protected DbConceptsConfig dbConcepts;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ObjectManagementConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getObjectManagementConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MemoryManagerConfig getMemoryManager() {
		return memoryManager;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMemoryManager(MemoryManagerConfig newMemoryManager, NotificationChain msgs) {
		MemoryManagerConfig oldMemoryManager = memoryManager;
		memoryManager = newMemoryManager;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.OBJECT_MANAGEMENT_CONFIG__MEMORY_MANAGER, oldMemoryManager, newMemoryManager);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMemoryManager(MemoryManagerConfig newMemoryManager) {
		if (newMemoryManager != memoryManager) {
			NotificationChain msgs = null;
			if (memoryManager != null)
				msgs = ((InternalEObject)memoryManager).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.OBJECT_MANAGEMENT_CONFIG__MEMORY_MANAGER, null, msgs);
			if (newMemoryManager != null)
				msgs = ((InternalEObject)newMemoryManager).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.OBJECT_MANAGEMENT_CONFIG__MEMORY_MANAGER, null, msgs);
			msgs = basicSetMemoryManager(newMemoryManager, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.OBJECT_MANAGEMENT_CONFIG__MEMORY_MANAGER, newMemoryManager, newMemoryManager));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CacheManagerConfig getCacheManager() {
		return cacheManager;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCacheManager(CacheManagerConfig newCacheManager, NotificationChain msgs) {
		CacheManagerConfig oldCacheManager = cacheManager;
		cacheManager = newCacheManager;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.OBJECT_MANAGEMENT_CONFIG__CACHE_MANAGER, oldCacheManager, newCacheManager);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCacheManager(CacheManagerConfig newCacheManager) {
		if (newCacheManager != cacheManager) {
			NotificationChain msgs = null;
			if (cacheManager != null)
				msgs = ((InternalEObject)cacheManager).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.OBJECT_MANAGEMENT_CONFIG__CACHE_MANAGER, null, msgs);
			if (newCacheManager != null)
				msgs = ((InternalEObject)newCacheManager).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.OBJECT_MANAGEMENT_CONFIG__CACHE_MANAGER, null, msgs);
			msgs = basicSetCacheManager(newCacheManager, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.OBJECT_MANAGEMENT_CONFIG__CACHE_MANAGER, newCacheManager, newCacheManager));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DbConceptsConfig getDbConcepts() {
		return dbConcepts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDbConcepts(DbConceptsConfig newDbConcepts, NotificationChain msgs) {
		DbConceptsConfig oldDbConcepts = dbConcepts;
		dbConcepts = newDbConcepts;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.OBJECT_MANAGEMENT_CONFIG__DB_CONCEPTS, oldDbConcepts, newDbConcepts);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDbConcepts(DbConceptsConfig newDbConcepts) {
		if (newDbConcepts != dbConcepts) {
			NotificationChain msgs = null;
			if (dbConcepts != null)
				msgs = ((InternalEObject)dbConcepts).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.OBJECT_MANAGEMENT_CONFIG__DB_CONCEPTS, null, msgs);
			if (newDbConcepts != null)
				msgs = ((InternalEObject)newDbConcepts).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.OBJECT_MANAGEMENT_CONFIG__DB_CONCEPTS, null, msgs);
			msgs = basicSetDbConcepts(newDbConcepts, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.OBJECT_MANAGEMENT_CONFIG__DB_CONCEPTS, newDbConcepts, newDbConcepts));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.OBJECT_MANAGEMENT_CONFIG__MEMORY_MANAGER:
				return basicSetMemoryManager(null, msgs);
			case CddPackage.OBJECT_MANAGEMENT_CONFIG__CACHE_MANAGER:
				return basicSetCacheManager(null, msgs);
			case CddPackage.OBJECT_MANAGEMENT_CONFIG__DB_CONCEPTS:
				return basicSetDbConcepts(null, msgs);
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
			case CddPackage.OBJECT_MANAGEMENT_CONFIG__MEMORY_MANAGER:
				return getMemoryManager();
			case CddPackage.OBJECT_MANAGEMENT_CONFIG__CACHE_MANAGER:
				return getCacheManager();
			case CddPackage.OBJECT_MANAGEMENT_CONFIG__DB_CONCEPTS:
				return getDbConcepts();
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
			case CddPackage.OBJECT_MANAGEMENT_CONFIG__MEMORY_MANAGER:
				setMemoryManager((MemoryManagerConfig)newValue);
				return;
			case CddPackage.OBJECT_MANAGEMENT_CONFIG__CACHE_MANAGER:
				setCacheManager((CacheManagerConfig)newValue);
				return;
			case CddPackage.OBJECT_MANAGEMENT_CONFIG__DB_CONCEPTS:
				setDbConcepts((DbConceptsConfig)newValue);
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
			case CddPackage.OBJECT_MANAGEMENT_CONFIG__MEMORY_MANAGER:
				setMemoryManager((MemoryManagerConfig)null);
				return;
			case CddPackage.OBJECT_MANAGEMENT_CONFIG__CACHE_MANAGER:
				setCacheManager((CacheManagerConfig)null);
				return;
			case CddPackage.OBJECT_MANAGEMENT_CONFIG__DB_CONCEPTS:
				setDbConcepts((DbConceptsConfig)null);
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
			case CddPackage.OBJECT_MANAGEMENT_CONFIG__MEMORY_MANAGER:
				return memoryManager != null;
			case CddPackage.OBJECT_MANAGEMENT_CONFIG__CACHE_MANAGER:
				return cacheManager != null;
			case CddPackage.OBJECT_MANAGEMENT_CONFIG__DB_CONCEPTS:
				return dbConcepts != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * @generated NOT
	 */
	@Override
	public Map<Object, Object> toProperties() {
		final Properties props = new Properties(); 

		if (null != this.cacheManager) {
			props.putAll(this.cacheManager.toProperties());
		}
		
		if (null != this.memoryManager) {
			props.putAll(this.memoryManager.toProperties());
		}

        if (null != this.dbConcepts) {
            props.putAll(this.dbConcepts.toProperties());
        }
        
        return props;
	}

} //ObjectManagementConfigImpl
