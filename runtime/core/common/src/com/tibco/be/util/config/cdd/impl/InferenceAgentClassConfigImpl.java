/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import com.tibco.be.util.config.cdd.BusinessworksConfig;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.DestinationsConfig;
import com.tibco.be.util.config.cdd.FunctionsConfig;
import com.tibco.be.util.config.cdd.InferenceAgentClassConfig;
import com.tibco.be.util.config.cdd.LoadConfig;
import com.tibco.be.util.config.cdd.LocalCacheConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.be.util.config.cdd.PropertyGroupConfig;
import com.tibco.be.util.config.cdd.RulesConfig;
import com.tibco.be.util.config.cdd.SharedQueueConfig;
import com.tibco.be.util.config.CddTools;
import com.tibco.cep.runtime.service.cluster.agent.AgentCapability;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import java.util.Map;
import java.util.Properties;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Inference Agent Class Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.InferenceAgentClassConfigImpl#getBusinessworks <em>Businessworks</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.InferenceAgentClassConfigImpl#getCheckForDuplicates <em>Check For Duplicates</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.InferenceAgentClassConfigImpl#getConcurrentRtc <em>Concurrent Rtc</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.InferenceAgentClassConfigImpl#getDestinations <em>Destinations</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.InferenceAgentClassConfigImpl#getLoad <em>Load</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.InferenceAgentClassConfigImpl#getLocalCache <em>Local Cache</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.InferenceAgentClassConfigImpl#getPropertyGroup <em>Property Group</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.InferenceAgentClassConfigImpl#getRules <em>Rules</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.InferenceAgentClassConfigImpl#getSharedQueue <em>Shared Queue</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.InferenceAgentClassConfigImpl#getShutdown <em>Shutdown</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.InferenceAgentClassConfigImpl#getStartup <em>Startup</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InferenceAgentClassConfigImpl extends AgentClassConfigImpl implements InferenceAgentClassConfig {
	/**
	 * The cached value of the '{@link #getBusinessworks() <em>Businessworks</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBusinessworks()
	 * @generated
	 * @ordered
	 */
	protected BusinessworksConfig businessworks;

	/**
	 * The cached value of the '{@link #getCheckForDuplicates() <em>Check For Duplicates</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCheckForDuplicates()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig checkForDuplicates;

	/**
	 * The cached value of the '{@link #getConcurrentRtc() <em>Concurrent Rtc</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConcurrentRtc()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig concurrentRtc;

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
	 * The cached value of the '{@link #getRules() <em>Rules</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRules()
	 * @generated
	 * @ordered
	 */
	protected RulesConfig rules;

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
	 * The cached value of the '{@link #getShutdown() <em>Shutdown</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShutdown()
	 * @generated
	 * @ordered
	 */
	protected FunctionsConfig shutdown;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InferenceAgentClassConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getInferenceAgentClassConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BusinessworksConfig getBusinessworks() {
		return businessworks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBusinessworks(BusinessworksConfig newBusinessworks, NotificationChain msgs) {
		BusinessworksConfig oldBusinessworks = businessworks;
		businessworks = newBusinessworks;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_AGENT_CLASS_CONFIG__BUSINESSWORKS, oldBusinessworks, newBusinessworks);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBusinessworks(BusinessworksConfig newBusinessworks) {
		if (newBusinessworks != businessworks) {
			NotificationChain msgs = null;
			if (businessworks != null)
				msgs = ((InternalEObject)businessworks).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_AGENT_CLASS_CONFIG__BUSINESSWORKS, null, msgs);
			if (newBusinessworks != null)
				msgs = ((InternalEObject)newBusinessworks).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_AGENT_CLASS_CONFIG__BUSINESSWORKS, null, msgs);
			msgs = basicSetBusinessworks(newBusinessworks, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_AGENT_CLASS_CONFIG__BUSINESSWORKS, newBusinessworks, newBusinessworks));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getCheckForDuplicates() {
		return checkForDuplicates;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCheckForDuplicates(OverrideConfig newCheckForDuplicates, NotificationChain msgs) {
		OverrideConfig oldCheckForDuplicates = checkForDuplicates;
		checkForDuplicates = newCheckForDuplicates;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_AGENT_CLASS_CONFIG__CHECK_FOR_DUPLICATES, oldCheckForDuplicates, newCheckForDuplicates);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCheckForDuplicates(OverrideConfig newCheckForDuplicates) {
		if (newCheckForDuplicates != checkForDuplicates) {
			NotificationChain msgs = null;
			if (checkForDuplicates != null)
				msgs = ((InternalEObject)checkForDuplicates).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_AGENT_CLASS_CONFIG__CHECK_FOR_DUPLICATES, null, msgs);
			if (newCheckForDuplicates != null)
				msgs = ((InternalEObject)newCheckForDuplicates).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_AGENT_CLASS_CONFIG__CHECK_FOR_DUPLICATES, null, msgs);
			msgs = basicSetCheckForDuplicates(newCheckForDuplicates, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_AGENT_CLASS_CONFIG__CHECK_FOR_DUPLICATES, newCheckForDuplicates, newCheckForDuplicates));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getConcurrentRtc() {
		return concurrentRtc;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConcurrentRtc(OverrideConfig newConcurrentRtc, NotificationChain msgs) {
		OverrideConfig oldConcurrentRtc = concurrentRtc;
		concurrentRtc = newConcurrentRtc;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_AGENT_CLASS_CONFIG__CONCURRENT_RTC, oldConcurrentRtc, newConcurrentRtc);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConcurrentRtc(OverrideConfig newConcurrentRtc) {
		if (newConcurrentRtc != concurrentRtc) {
			NotificationChain msgs = null;
			if (concurrentRtc != null)
				msgs = ((InternalEObject)concurrentRtc).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_AGENT_CLASS_CONFIG__CONCURRENT_RTC, null, msgs);
			if (newConcurrentRtc != null)
				msgs = ((InternalEObject)newConcurrentRtc).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_AGENT_CLASS_CONFIG__CONCURRENT_RTC, null, msgs);
			msgs = basicSetConcurrentRtc(newConcurrentRtc, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_AGENT_CLASS_CONFIG__CONCURRENT_RTC, newConcurrentRtc, newConcurrentRtc));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_AGENT_CLASS_CONFIG__DESTINATIONS, oldDestinations, newDestinations);
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
				msgs = ((InternalEObject)destinations).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_AGENT_CLASS_CONFIG__DESTINATIONS, null, msgs);
			if (newDestinations != null)
				msgs = ((InternalEObject)newDestinations).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_AGENT_CLASS_CONFIG__DESTINATIONS, null, msgs);
			msgs = basicSetDestinations(newDestinations, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_AGENT_CLASS_CONFIG__DESTINATIONS, newDestinations, newDestinations));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_AGENT_CLASS_CONFIG__LOAD, oldLoad, newLoad);
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
				msgs = ((InternalEObject)load).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_AGENT_CLASS_CONFIG__LOAD, null, msgs);
			if (newLoad != null)
				msgs = ((InternalEObject)newLoad).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_AGENT_CLASS_CONFIG__LOAD, null, msgs);
			msgs = basicSetLoad(newLoad, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_AGENT_CLASS_CONFIG__LOAD, newLoad, newLoad));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_AGENT_CLASS_CONFIG__LOCAL_CACHE, oldLocalCache, newLocalCache);
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
				msgs = ((InternalEObject)localCache).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_AGENT_CLASS_CONFIG__LOCAL_CACHE, null, msgs);
			if (newLocalCache != null)
				msgs = ((InternalEObject)newLocalCache).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_AGENT_CLASS_CONFIG__LOCAL_CACHE, null, msgs);
			msgs = basicSetLocalCache(newLocalCache, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_AGENT_CLASS_CONFIG__LOCAL_CACHE, newLocalCache, newLocalCache));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_AGENT_CLASS_CONFIG__PROPERTY_GROUP, oldPropertyGroup, newPropertyGroup);
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
				msgs = ((InternalEObject)propertyGroup).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_AGENT_CLASS_CONFIG__PROPERTY_GROUP, null, msgs);
			if (newPropertyGroup != null)
				msgs = ((InternalEObject)newPropertyGroup).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_AGENT_CLASS_CONFIG__PROPERTY_GROUP, null, msgs);
			msgs = basicSetPropertyGroup(newPropertyGroup, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_AGENT_CLASS_CONFIG__PROPERTY_GROUP, newPropertyGroup, newPropertyGroup));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RulesConfig getRules() {
		return rules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRules(RulesConfig newRules, NotificationChain msgs) {
		RulesConfig oldRules = rules;
		rules = newRules;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_AGENT_CLASS_CONFIG__RULES, oldRules, newRules);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRules(RulesConfig newRules) {
		if (newRules != rules) {
			NotificationChain msgs = null;
			if (rules != null)
				msgs = ((InternalEObject)rules).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_AGENT_CLASS_CONFIG__RULES, null, msgs);
			if (newRules != null)
				msgs = ((InternalEObject)newRules).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_AGENT_CLASS_CONFIG__RULES, null, msgs);
			msgs = basicSetRules(newRules, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_AGENT_CLASS_CONFIG__RULES, newRules, newRules));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_AGENT_CLASS_CONFIG__SHARED_QUEUE, oldSharedQueue, newSharedQueue);
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
				msgs = ((InternalEObject)sharedQueue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_AGENT_CLASS_CONFIG__SHARED_QUEUE, null, msgs);
			if (newSharedQueue != null)
				msgs = ((InternalEObject)newSharedQueue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_AGENT_CLASS_CONFIG__SHARED_QUEUE, null, msgs);
			msgs = basicSetSharedQueue(newSharedQueue, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_AGENT_CLASS_CONFIG__SHARED_QUEUE, newSharedQueue, newSharedQueue));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_AGENT_CLASS_CONFIG__SHUTDOWN, oldShutdown, newShutdown);
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
				msgs = ((InternalEObject)shutdown).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_AGENT_CLASS_CONFIG__SHUTDOWN, null, msgs);
			if (newShutdown != null)
				msgs = ((InternalEObject)newShutdown).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_AGENT_CLASS_CONFIG__SHUTDOWN, null, msgs);
			msgs = basicSetShutdown(newShutdown, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_AGENT_CLASS_CONFIG__SHUTDOWN, newShutdown, newShutdown));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_AGENT_CLASS_CONFIG__STARTUP, oldStartup, newStartup);
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
				msgs = ((InternalEObject)startup).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_AGENT_CLASS_CONFIG__STARTUP, null, msgs);
			if (newStartup != null)
				msgs = ((InternalEObject)newStartup).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_AGENT_CLASS_CONFIG__STARTUP, null, msgs);
			msgs = basicSetStartup(newStartup, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_AGENT_CLASS_CONFIG__STARTUP, newStartup, newStartup));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__BUSINESSWORKS:
				return basicSetBusinessworks(null, msgs);
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__CHECK_FOR_DUPLICATES:
				return basicSetCheckForDuplicates(null, msgs);
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__CONCURRENT_RTC:
				return basicSetConcurrentRtc(null, msgs);
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__DESTINATIONS:
				return basicSetDestinations(null, msgs);
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__LOAD:
				return basicSetLoad(null, msgs);
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__LOCAL_CACHE:
				return basicSetLocalCache(null, msgs);
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
				return basicSetPropertyGroup(null, msgs);
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__RULES:
				return basicSetRules(null, msgs);
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__SHARED_QUEUE:
				return basicSetSharedQueue(null, msgs);
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__SHUTDOWN:
				return basicSetShutdown(null, msgs);
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__STARTUP:
				return basicSetStartup(null, msgs);
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
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__BUSINESSWORKS:
				return getBusinessworks();
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__CHECK_FOR_DUPLICATES:
				return getCheckForDuplicates();
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__CONCURRENT_RTC:
				return getConcurrentRtc();
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__DESTINATIONS:
				return getDestinations();
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__LOAD:
				return getLoad();
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__LOCAL_CACHE:
				return getLocalCache();
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
				return getPropertyGroup();
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__RULES:
				return getRules();
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__SHARED_QUEUE:
				return getSharedQueue();
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__SHUTDOWN:
				return getShutdown();
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__STARTUP:
				return getStartup();
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
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__BUSINESSWORKS:
				setBusinessworks((BusinessworksConfig)newValue);
				return;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__CHECK_FOR_DUPLICATES:
				setCheckForDuplicates((OverrideConfig)newValue);
				return;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__CONCURRENT_RTC:
				setConcurrentRtc((OverrideConfig)newValue);
				return;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__DESTINATIONS:
				setDestinations((DestinationsConfig)newValue);
				return;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__LOAD:
				setLoad((LoadConfig)newValue);
				return;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__LOCAL_CACHE:
				setLocalCache((LocalCacheConfig)newValue);
				return;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
				setPropertyGroup((PropertyGroupConfig)newValue);
				return;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__RULES:
				setRules((RulesConfig)newValue);
				return;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__SHARED_QUEUE:
				setSharedQueue((SharedQueueConfig)newValue);
				return;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__SHUTDOWN:
				setShutdown((FunctionsConfig)newValue);
				return;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__STARTUP:
				setStartup((FunctionsConfig)newValue);
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
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__BUSINESSWORKS:
				setBusinessworks((BusinessworksConfig)null);
				return;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__CHECK_FOR_DUPLICATES:
				setCheckForDuplicates((OverrideConfig)null);
				return;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__CONCURRENT_RTC:
				setConcurrentRtc((OverrideConfig)null);
				return;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__DESTINATIONS:
				setDestinations((DestinationsConfig)null);
				return;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__LOAD:
				setLoad((LoadConfig)null);
				return;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__LOCAL_CACHE:
				setLocalCache((LocalCacheConfig)null);
				return;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
				setPropertyGroup((PropertyGroupConfig)null);
				return;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__RULES:
				setRules((RulesConfig)null);
				return;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__SHARED_QUEUE:
				setSharedQueue((SharedQueueConfig)null);
				return;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__SHUTDOWN:
				setShutdown((FunctionsConfig)null);
				return;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__STARTUP:
				setStartup((FunctionsConfig)null);
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
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__BUSINESSWORKS:
				return businessworks != null;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__CHECK_FOR_DUPLICATES:
				return checkForDuplicates != null;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__CONCURRENT_RTC:
				return concurrentRtc != null;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__DESTINATIONS:
				return destinations != null;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__LOAD:
				return load != null;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__LOCAL_CACHE:
				return localCache != null;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
				return propertyGroup != null;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__RULES:
				return rules != null;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__SHARED_QUEUE:
				return sharedQueue != null;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__SHUTDOWN:
				return shutdown != null;
			case CddPackage.INFERENCE_AGENT_CLASS_CONFIG__STARTUP:
				return startup != null;
		}
		return super.eIsSet(featureID);
	}
	/**
	 * @param featureId
	 * @return
	 * @generated NOT
	 */
	boolean isNull(int featureId) {
		EStructuralFeature sf = this.eClass().getEStructuralFeature(featureId);
		boolean set = this.eIsSet(sf.getFeatureID());
		if(set) {
			EObject sfo = (EObject) this.eGet(sf);
			if(sfo != null) {
				if(sfo instanceof EObject) {
					EStructuralFeature msf = sfo.eClass().getEStructuralFeature(CddPackage.SYSTEM_PROPERTY_CONFIG__MIXED);
					if(msf != null) {
						Object val = sfo.eGet(msf);
						if(null != val) {
							return true;
						}
					}
					
				}
			}
		}
		return false;
	}
	
	/**
	 * @param featureId
	 * @return
	 * @generated NOT
	 */
	Object getValue(int featureId) {
		EStructuralFeature sf = this.eClass().getEStructuralFeature(featureId);
		boolean set = this.eIsSet(sf.getFeatureID());
		if(set) {
			EObject sfo = (EObject) this.eGet(sf);
			if(sfo != null) {
				if(sfo instanceof EObject) {
					EStructuralFeature msf = sfo.eClass().getEStructuralFeature(CddPackage.SYSTEM_PROPERTY_CONFIG__MIXED);
					if(msf != null) {
						Object val = sfo.eGet(msf);
						if(null != val) {
							return val;
						}
					}
					
				}
			}
		}
		return null;
	}

	/**
	 * @generated NOT
	 */
	@Override
	public Map<Object, Object> toProperties() {
        final Properties p = (Properties) super.toProperties();
        
        if (null != this.businessworks) {
            p.putAll(this.businessworks.toProperties());
        }
        if (null != this.destinations) {
            p.putAll(this.destinations.toProperties());
        }
        if (null != this.load) {
            p.putAll(this.load.toProperties());
        }
        if (null != this.localCache) {
            p.putAll(this.localCache.toProperties());
        }
        if (null != this.rules) {
            p.putAll(this.rules.toProperties());
        }
        if (null != this.sharedQueue) {
            p.putAll(this.sharedQueue.toProperties());
        }
        // Properties
        if (null != this.propertyGroup) {
            p.putAll(this.propertyGroup.toProperties());
        }
        
        CddTools.addEntryFromMixed(
        		p, "Agent.${AgentGroupName}" + AgentCapability.CHECKDUPLICATES.getPropSuffix(),
        		this.checkForDuplicates, true);
        
        CddTools.addEntryFromMixed(
        		p, "Agent.${AgentGroupName}" + AgentCapability.CONCURRENTWM.getPropSuffix(),
        		this.concurrentRtc, true);
        
        return p;
    }
	
	
	
	
} //InferenceAgentClassConfigImpl
