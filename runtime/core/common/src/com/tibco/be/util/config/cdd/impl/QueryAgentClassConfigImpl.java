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

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.DestinationsConfig;
import com.tibco.be.util.config.cdd.FunctionsConfig;
import com.tibco.be.util.config.cdd.LoadConfig;
import com.tibco.be.util.config.cdd.LocalCacheConfig;
import com.tibco.be.util.config.cdd.PropertyGroupConfig;
import com.tibco.be.util.config.cdd.QueryAgentClassConfig;
import com.tibco.be.util.config.cdd.SharedQueueConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Query Agent Class Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.QueryAgentClassConfigImpl#getDestinations <em>Destinations</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.QueryAgentClassConfigImpl#getStartup <em>Startup</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.QueryAgentClassConfigImpl#getShutdown <em>Shutdown</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.QueryAgentClassConfigImpl#getLoad <em>Load</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.QueryAgentClassConfigImpl#getLocalCache <em>Local Cache</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.QueryAgentClassConfigImpl#getPropertyGroup <em>Property Group</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.QueryAgentClassConfigImpl#getSharedQueue <em>Shared Queue</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class QueryAgentClassConfigImpl extends AgentClassConfigImpl implements QueryAgentClassConfig {
	/**
	 * The cached value of the '{@link #getDestinations() <em>Destinations</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDestinations()
	 * @generated
	 * @ordered
	 */
	protected DestinationsConfig destinations;

	/**
	 * The cached value of the '{@link #getStartup() <em>Startup</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartup()
	 * @generated
	 * @ordered
	 */
	protected FunctionsConfig startup;

	/**
	 * The cached value of the '{@link #getShutdown() <em>Shutdown</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShutdown()
	 * @generated
	 * @ordered
	 */
	protected FunctionsConfig shutdown;

	/**
	 * The cached value of the '{@link #getLoad() <em>Load</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLoad()
	 * @generated
	 * @ordered
	 */
	protected LoadConfig load;

	/**
	 * The cached value of the '{@link #getLocalCache() <em>Local Cache</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocalCache()
	 * @generated
	 * @ordered
	 */
	protected LocalCacheConfig localCache;

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
	 * The cached value of the '{@link #getSharedQueue() <em>Shared Queue</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSharedQueue()
	 * @generated
	 * @ordered
	 */
	protected SharedQueueConfig sharedQueue;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected QueryAgentClassConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getQueryAgentClassConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DestinationsConfig getDestinations() {
		return destinations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDestinations(DestinationsConfig newDestinations, NotificationChain msgs) {
		DestinationsConfig oldDestinations = destinations;
		destinations = newDestinations;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.QUERY_AGENT_CLASS_CONFIG__DESTINATIONS, oldDestinations, newDestinations);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDestinations(DestinationsConfig newDestinations) {
		if (newDestinations != destinations) {
			NotificationChain msgs = null;
			if (destinations != null)
				msgs = ((InternalEObject)destinations).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.QUERY_AGENT_CLASS_CONFIG__DESTINATIONS, null, msgs);
			if (newDestinations != null)
				msgs = ((InternalEObject)newDestinations).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.QUERY_AGENT_CLASS_CONFIG__DESTINATIONS, null, msgs);
			msgs = basicSetDestinations(newDestinations, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.QUERY_AGENT_CLASS_CONFIG__DESTINATIONS, newDestinations, newDestinations));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionsConfig getStartup() {
		return startup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStartup(FunctionsConfig newStartup, NotificationChain msgs) {
		FunctionsConfig oldStartup = startup;
		startup = newStartup;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.QUERY_AGENT_CLASS_CONFIG__STARTUP, oldStartup, newStartup);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartup(FunctionsConfig newStartup) {
		if (newStartup != startup) {
			NotificationChain msgs = null;
			if (startup != null)
				msgs = ((InternalEObject)startup).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.QUERY_AGENT_CLASS_CONFIG__STARTUP, null, msgs);
			if (newStartup != null)
				msgs = ((InternalEObject)newStartup).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.QUERY_AGENT_CLASS_CONFIG__STARTUP, null, msgs);
			msgs = basicSetStartup(newStartup, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.QUERY_AGENT_CLASS_CONFIG__STARTUP, newStartup, newStartup));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionsConfig getShutdown() {
		return shutdown;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetShutdown(FunctionsConfig newShutdown, NotificationChain msgs) {
		FunctionsConfig oldShutdown = shutdown;
		shutdown = newShutdown;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.QUERY_AGENT_CLASS_CONFIG__SHUTDOWN, oldShutdown, newShutdown);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShutdown(FunctionsConfig newShutdown) {
		if (newShutdown != shutdown) {
			NotificationChain msgs = null;
			if (shutdown != null)
				msgs = ((InternalEObject)shutdown).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.QUERY_AGENT_CLASS_CONFIG__SHUTDOWN, null, msgs);
			if (newShutdown != null)
				msgs = ((InternalEObject)newShutdown).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.QUERY_AGENT_CLASS_CONFIG__SHUTDOWN, null, msgs);
			msgs = basicSetShutdown(newShutdown, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.QUERY_AGENT_CLASS_CONFIG__SHUTDOWN, newShutdown, newShutdown));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LoadConfig getLoad() {
		return load;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLoad(LoadConfig newLoad, NotificationChain msgs) {
		LoadConfig oldLoad = load;
		load = newLoad;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.QUERY_AGENT_CLASS_CONFIG__LOAD, oldLoad, newLoad);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLoad(LoadConfig newLoad) {
		if (newLoad != load) {
			NotificationChain msgs = null;
			if (load != null)
				msgs = ((InternalEObject)load).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.QUERY_AGENT_CLASS_CONFIG__LOAD, null, msgs);
			if (newLoad != null)
				msgs = ((InternalEObject)newLoad).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.QUERY_AGENT_CLASS_CONFIG__LOAD, null, msgs);
			msgs = basicSetLoad(newLoad, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.QUERY_AGENT_CLASS_CONFIG__LOAD, newLoad, newLoad));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalCacheConfig getLocalCache() {
		return localCache;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLocalCache(LocalCacheConfig newLocalCache, NotificationChain msgs) {
		LocalCacheConfig oldLocalCache = localCache;
		localCache = newLocalCache;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.QUERY_AGENT_CLASS_CONFIG__LOCAL_CACHE, oldLocalCache, newLocalCache);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocalCache(LocalCacheConfig newLocalCache) {
		if (newLocalCache != localCache) {
			NotificationChain msgs = null;
			if (localCache != null)
				msgs = ((InternalEObject)localCache).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.QUERY_AGENT_CLASS_CONFIG__LOCAL_CACHE, null, msgs);
			if (newLocalCache != null)
				msgs = ((InternalEObject)newLocalCache).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.QUERY_AGENT_CLASS_CONFIG__LOCAL_CACHE, null, msgs);
			msgs = basicSetLocalCache(newLocalCache, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.QUERY_AGENT_CLASS_CONFIG__LOCAL_CACHE, newLocalCache, newLocalCache));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.QUERY_AGENT_CLASS_CONFIG__PROPERTY_GROUP, oldPropertyGroup, newPropertyGroup);
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
				msgs = ((InternalEObject)propertyGroup).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.QUERY_AGENT_CLASS_CONFIG__PROPERTY_GROUP, null, msgs);
			if (newPropertyGroup != null)
				msgs = ((InternalEObject)newPropertyGroup).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.QUERY_AGENT_CLASS_CONFIG__PROPERTY_GROUP, null, msgs);
			msgs = basicSetPropertyGroup(newPropertyGroup, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.QUERY_AGENT_CLASS_CONFIG__PROPERTY_GROUP, newPropertyGroup, newPropertyGroup));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SharedQueueConfig getSharedQueue() {
		return sharedQueue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSharedQueue(SharedQueueConfig newSharedQueue, NotificationChain msgs) {
		SharedQueueConfig oldSharedQueue = sharedQueue;
		sharedQueue = newSharedQueue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.QUERY_AGENT_CLASS_CONFIG__SHARED_QUEUE, oldSharedQueue, newSharedQueue);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSharedQueue(SharedQueueConfig newSharedQueue) {
		if (newSharedQueue != sharedQueue) {
			NotificationChain msgs = null;
			if (sharedQueue != null)
				msgs = ((InternalEObject)sharedQueue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.QUERY_AGENT_CLASS_CONFIG__SHARED_QUEUE, null, msgs);
			if (newSharedQueue != null)
				msgs = ((InternalEObject)newSharedQueue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.QUERY_AGENT_CLASS_CONFIG__SHARED_QUEUE, null, msgs);
			msgs = basicSetSharedQueue(newSharedQueue, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.QUERY_AGENT_CLASS_CONFIG__SHARED_QUEUE, newSharedQueue, newSharedQueue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__DESTINATIONS:
				return basicSetDestinations(null, msgs);
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__STARTUP:
				return basicSetStartup(null, msgs);
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__SHUTDOWN:
				return basicSetShutdown(null, msgs);
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__LOAD:
				return basicSetLoad(null, msgs);
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__LOCAL_CACHE:
				return basicSetLocalCache(null, msgs);
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
				return basicSetPropertyGroup(null, msgs);
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__SHARED_QUEUE:
				return basicSetSharedQueue(null, msgs);
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
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__DESTINATIONS:
				return getDestinations();
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__STARTUP:
				return getStartup();
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__SHUTDOWN:
				return getShutdown();
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__LOAD:
				return getLoad();
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__LOCAL_CACHE:
				return getLocalCache();
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
				return getPropertyGroup();
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__SHARED_QUEUE:
				return getSharedQueue();
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
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__DESTINATIONS:
				setDestinations((DestinationsConfig)newValue);
				return;
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__STARTUP:
				setStartup((FunctionsConfig)newValue);
				return;
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__SHUTDOWN:
				setShutdown((FunctionsConfig)newValue);
				return;
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__LOAD:
				setLoad((LoadConfig)newValue);
				return;
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__LOCAL_CACHE:
				setLocalCache((LocalCacheConfig)newValue);
				return;
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
				setPropertyGroup((PropertyGroupConfig)newValue);
				return;
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__SHARED_QUEUE:
				setSharedQueue((SharedQueueConfig)newValue);
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
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__DESTINATIONS:
				setDestinations((DestinationsConfig)null);
				return;
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__STARTUP:
				setStartup((FunctionsConfig)null);
				return;
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__SHUTDOWN:
				setShutdown((FunctionsConfig)null);
				return;
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__LOAD:
				setLoad((LoadConfig)null);
				return;
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__LOCAL_CACHE:
				setLocalCache((LocalCacheConfig)null);
				return;
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
				setPropertyGroup((PropertyGroupConfig)null);
				return;
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__SHARED_QUEUE:
				setSharedQueue((SharedQueueConfig)null);
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
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__DESTINATIONS:
				return destinations != null;
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__STARTUP:
				return startup != null;
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__SHUTDOWN:
				return shutdown != null;
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__LOAD:
				return load != null;
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__LOCAL_CACHE:
				return localCache != null;
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
				return propertyGroup != null;
			case CddPackage.QUERY_AGENT_CLASS_CONFIG__SHARED_QUEUE:
				return sharedQueue != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * @generated NOT
	 */
	@Override
	public Map<Object, Object> toProperties() {
        final Properties p = new Properties();
//        if (null != this.load) {
//            p.putAll(this.load.toProperties());
//        }
        if (null != this.propertyGroup) {
            p.putAll(this.propertyGroup.toProperties());
        }
        return p;
    }
	
	
} //QueryAgentClassConfigImpl
