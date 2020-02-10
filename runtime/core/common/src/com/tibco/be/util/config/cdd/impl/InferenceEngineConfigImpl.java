/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.InferenceEngineConfig;
import com.tibco.be.util.config.cdd.LocalCacheConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.be.util.config.cdd.PropertyGroupConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Inference Engine Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.InferenceEngineConfigImpl#getCheckForDuplicates <em>Check For Duplicates</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.InferenceEngineConfigImpl#getConcurrentRtc <em>Concurrent Rtc</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.InferenceEngineConfigImpl#getLocalCache <em>Local Cache</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.InferenceEngineConfigImpl#getPropertyGroup <em>Property Group</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InferenceEngineConfigImpl extends ArtifactConfigImpl implements InferenceEngineConfig {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InferenceEngineConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getInferenceEngineConfig();
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_ENGINE_CONFIG__CHECK_FOR_DUPLICATES, oldCheckForDuplicates, newCheckForDuplicates);
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
				msgs = ((InternalEObject)checkForDuplicates).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_ENGINE_CONFIG__CHECK_FOR_DUPLICATES, null, msgs);
			if (newCheckForDuplicates != null)
				msgs = ((InternalEObject)newCheckForDuplicates).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_ENGINE_CONFIG__CHECK_FOR_DUPLICATES, null, msgs);
			msgs = basicSetCheckForDuplicates(newCheckForDuplicates, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_ENGINE_CONFIG__CHECK_FOR_DUPLICATES, newCheckForDuplicates, newCheckForDuplicates));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_ENGINE_CONFIG__CONCURRENT_RTC, oldConcurrentRtc, newConcurrentRtc);
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
				msgs = ((InternalEObject)concurrentRtc).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_ENGINE_CONFIG__CONCURRENT_RTC, null, msgs);
			if (newConcurrentRtc != null)
				msgs = ((InternalEObject)newConcurrentRtc).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_ENGINE_CONFIG__CONCURRENT_RTC, null, msgs);
			msgs = basicSetConcurrentRtc(newConcurrentRtc, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_ENGINE_CONFIG__CONCURRENT_RTC, newConcurrentRtc, newConcurrentRtc));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_ENGINE_CONFIG__LOCAL_CACHE, oldLocalCache, newLocalCache);
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
				msgs = ((InternalEObject)localCache).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_ENGINE_CONFIG__LOCAL_CACHE, null, msgs);
			if (newLocalCache != null)
				msgs = ((InternalEObject)newLocalCache).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_ENGINE_CONFIG__LOCAL_CACHE, null, msgs);
			msgs = basicSetLocalCache(newLocalCache, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_ENGINE_CONFIG__LOCAL_CACHE, newLocalCache, newLocalCache));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_ENGINE_CONFIG__PROPERTY_GROUP, oldPropertyGroup, newPropertyGroup);
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
				msgs = ((InternalEObject)propertyGroup).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_ENGINE_CONFIG__PROPERTY_GROUP, null, msgs);
			if (newPropertyGroup != null)
				msgs = ((InternalEObject)newPropertyGroup).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.INFERENCE_ENGINE_CONFIG__PROPERTY_GROUP, null, msgs);
			msgs = basicSetPropertyGroup(newPropertyGroup, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.INFERENCE_ENGINE_CONFIG__PROPERTY_GROUP, newPropertyGroup, newPropertyGroup));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.INFERENCE_ENGINE_CONFIG__CHECK_FOR_DUPLICATES:
				return basicSetCheckForDuplicates(null, msgs);
			case CddPackage.INFERENCE_ENGINE_CONFIG__CONCURRENT_RTC:
				return basicSetConcurrentRtc(null, msgs);
			case CddPackage.INFERENCE_ENGINE_CONFIG__LOCAL_CACHE:
				return basicSetLocalCache(null, msgs);
			case CddPackage.INFERENCE_ENGINE_CONFIG__PROPERTY_GROUP:
				return basicSetPropertyGroup(null, msgs);
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
			case CddPackage.INFERENCE_ENGINE_CONFIG__CHECK_FOR_DUPLICATES:
				return getCheckForDuplicates();
			case CddPackage.INFERENCE_ENGINE_CONFIG__CONCURRENT_RTC:
				return getConcurrentRtc();
			case CddPackage.INFERENCE_ENGINE_CONFIG__LOCAL_CACHE:
				return getLocalCache();
			case CddPackage.INFERENCE_ENGINE_CONFIG__PROPERTY_GROUP:
				return getPropertyGroup();
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
			case CddPackage.INFERENCE_ENGINE_CONFIG__CHECK_FOR_DUPLICATES:
				setCheckForDuplicates((OverrideConfig)newValue);
				return;
			case CddPackage.INFERENCE_ENGINE_CONFIG__CONCURRENT_RTC:
				setConcurrentRtc((OverrideConfig)newValue);
				return;
			case CddPackage.INFERENCE_ENGINE_CONFIG__LOCAL_CACHE:
				setLocalCache((LocalCacheConfig)newValue);
				return;
			case CddPackage.INFERENCE_ENGINE_CONFIG__PROPERTY_GROUP:
				setPropertyGroup((PropertyGroupConfig)newValue);
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
			case CddPackage.INFERENCE_ENGINE_CONFIG__CHECK_FOR_DUPLICATES:
				setCheckForDuplicates((OverrideConfig)null);
				return;
			case CddPackage.INFERENCE_ENGINE_CONFIG__CONCURRENT_RTC:
				setConcurrentRtc((OverrideConfig)null);
				return;
			case CddPackage.INFERENCE_ENGINE_CONFIG__LOCAL_CACHE:
				setLocalCache((LocalCacheConfig)null);
				return;
			case CddPackage.INFERENCE_ENGINE_CONFIG__PROPERTY_GROUP:
				setPropertyGroup((PropertyGroupConfig)null);
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
			case CddPackage.INFERENCE_ENGINE_CONFIG__CHECK_FOR_DUPLICATES:
				return checkForDuplicates != null;
			case CddPackage.INFERENCE_ENGINE_CONFIG__CONCURRENT_RTC:
				return concurrentRtc != null;
			case CddPackage.INFERENCE_ENGINE_CONFIG__LOCAL_CACHE:
				return localCache != null;
			case CddPackage.INFERENCE_ENGINE_CONFIG__PROPERTY_GROUP:
				return propertyGroup != null;
		}
		return super.eIsSet(featureID);
	}

} //InferenceEngineConfigImpl
