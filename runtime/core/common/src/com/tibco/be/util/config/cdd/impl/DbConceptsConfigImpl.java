/**
 * <copyright> </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.DbConceptsConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.be.util.config.cdd.UrisConfig;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Db
 * Concepts Config</b></em>'. <!-- end-user-doc --> <p> The following features
 * are implemented: <ul> <li>{@link com.tibco.be.util.config.cdd.impl.DbConceptsConfigImpl#getCheckInterval <em>Check Interval</em>}</li> <li>{@link com.tibco.be.util.config.cdd.impl.DbConceptsConfigImpl#getDbUris <em>Db Uris</em>}</li> <li>{@link com.tibco.be.util.config.cdd.impl.DbConceptsConfigImpl#getInactivityTimeout <em>Inactivity Timeout</em>}</li> <li>{@link com.tibco.be.util.config.cdd.impl.DbConceptsConfigImpl#getInitialSize <em>Initial Size</em>}</li> <li>{@link com.tibco.be.util.config.cdd.impl.DbConceptsConfigImpl#getMaxSize <em>Max Size</em>}</li> <li>{@link com.tibco.be.util.config.cdd.impl.DbConceptsConfigImpl#getMinSize <em>Min Size</em>}</li> <li>{@link com.tibco.be.util.config.cdd.impl.DbConceptsConfigImpl#getPropertyCheckInterval <em>Property Check Interval</em>}</li> <li>{@link com.tibco.be.util.config.cdd.impl.DbConceptsConfigImpl#getRetryCount <em>Retry Count</em>}</li> <li>{@link com.tibco.be.util.config.cdd.impl.DbConceptsConfigImpl#getWaitTimeout <em>Wait Timeout</em>}</li>
 * </ul> </p>
 *
 * @generated
 */
public class DbConceptsConfigImpl extends ArtifactConfigImpl implements DbConceptsConfig {

    /**
     * The cached value of the '{@link #getCheckInterval() <em>Check Interval</em>}'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getCheckInterval() @generated @ordered
     */
    protected OverrideConfig checkInterval;
    /**
     * The cached value of the '{@link #getDbUris() <em>Db Uris</em>}'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDbUris() @generated @ordered
     */
    protected UrisConfig dbUris;
    /**
     * The cached value of the '{@link #getInactivityTimeout() <em>Inactivity Timeout</em>}'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getInactivityTimeout() @generated @ordered
     */
    protected OverrideConfig inactivityTimeout;
    /**
     * The cached value of the '{@link #getInitialSize() <em>Initial Size</em>}'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getInitialSize() @generated @ordered
     */
    protected OverrideConfig initialSize;
    /**
     * The cached value of the '{@link #getMaxSize() <em>Max Size</em>}'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getMaxSize() @generated @ordered
     */
    protected OverrideConfig maxSize;
    /**
     * The cached value of the '{@link #getMinSize() <em>Min Size</em>}'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getMinSize() @generated @ordered
     */
    protected OverrideConfig minSize;
    /**
     * The cached value of the '{@link #getPropertyCheckInterval() <em>Property Check Interval</em>}'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getPropertyCheckInterval() @generated @ordered
     */
    protected OverrideConfig propertyCheckInterval;
    /**
     * The cached value of the '{@link #getRetryCount() <em>Retry Count</em>}'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getRetryCount() @generated @ordered
     */
    protected OverrideConfig retryCount;
    /**
     * The cached value of the '{@link #getWaitTimeout() <em>Wait Timeout</em>}'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getWaitTimeout() @generated @ordered
     */
    protected OverrideConfig waitTimeout;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    protected DbConceptsConfigImpl() {
		super();
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    @Override
    protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getDbConceptsConfig();
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    public OverrideConfig getCheckInterval() {
		return checkInterval;
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    public NotificationChain basicSetCheckInterval(OverrideConfig newCheckInterval, NotificationChain msgs) {
		OverrideConfig oldCheckInterval = checkInterval;
		checkInterval = newCheckInterval;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DB_CONCEPTS_CONFIG__CHECK_INTERVAL, oldCheckInterval, newCheckInterval);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    public void setCheckInterval(OverrideConfig newCheckInterval) {
		if (newCheckInterval != checkInterval) {
			NotificationChain msgs = null;
			if (checkInterval != null)
				msgs = ((InternalEObject)checkInterval).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DB_CONCEPTS_CONFIG__CHECK_INTERVAL, null, msgs);
			if (newCheckInterval != null)
				msgs = ((InternalEObject)newCheckInterval).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DB_CONCEPTS_CONFIG__CHECK_INTERVAL, null, msgs);
			msgs = basicSetCheckInterval(newCheckInterval, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DB_CONCEPTS_CONFIG__CHECK_INTERVAL, newCheckInterval, newCheckInterval));
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    public UrisConfig getDbUris() {
		return dbUris;
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    public NotificationChain basicSetDbUris(UrisConfig newDbUris, NotificationChain msgs) {
		UrisConfig oldDbUris = dbUris;
		dbUris = newDbUris;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DB_CONCEPTS_CONFIG__DB_URIS, oldDbUris, newDbUris);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    public void setDbUris(UrisConfig newDbUris) {
		if (newDbUris != dbUris) {
			NotificationChain msgs = null;
			if (dbUris != null)
				msgs = ((InternalEObject)dbUris).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DB_CONCEPTS_CONFIG__DB_URIS, null, msgs);
			if (newDbUris != null)
				msgs = ((InternalEObject)newDbUris).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DB_CONCEPTS_CONFIG__DB_URIS, null, msgs);
			msgs = basicSetDbUris(newDbUris, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DB_CONCEPTS_CONFIG__DB_URIS, newDbUris, newDbUris));
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    public OverrideConfig getInactivityTimeout() {
		return inactivityTimeout;
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    public NotificationChain basicSetInactivityTimeout(OverrideConfig newInactivityTimeout, NotificationChain msgs) {
		OverrideConfig oldInactivityTimeout = inactivityTimeout;
		inactivityTimeout = newInactivityTimeout;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DB_CONCEPTS_CONFIG__INACTIVITY_TIMEOUT, oldInactivityTimeout, newInactivityTimeout);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    public void setInactivityTimeout(OverrideConfig newInactivityTimeout) {
		if (newInactivityTimeout != inactivityTimeout) {
			NotificationChain msgs = null;
			if (inactivityTimeout != null)
				msgs = ((InternalEObject)inactivityTimeout).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DB_CONCEPTS_CONFIG__INACTIVITY_TIMEOUT, null, msgs);
			if (newInactivityTimeout != null)
				msgs = ((InternalEObject)newInactivityTimeout).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DB_CONCEPTS_CONFIG__INACTIVITY_TIMEOUT, null, msgs);
			msgs = basicSetInactivityTimeout(newInactivityTimeout, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DB_CONCEPTS_CONFIG__INACTIVITY_TIMEOUT, newInactivityTimeout, newInactivityTimeout));
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    public OverrideConfig getInitialSize() {
		return initialSize;
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    public NotificationChain basicSetInitialSize(OverrideConfig newInitialSize, NotificationChain msgs) {
		OverrideConfig oldInitialSize = initialSize;
		initialSize = newInitialSize;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DB_CONCEPTS_CONFIG__INITIAL_SIZE, oldInitialSize, newInitialSize);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    public void setInitialSize(OverrideConfig newInitialSize) {
		if (newInitialSize != initialSize) {
			NotificationChain msgs = null;
			if (initialSize != null)
				msgs = ((InternalEObject)initialSize).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DB_CONCEPTS_CONFIG__INITIAL_SIZE, null, msgs);
			if (newInitialSize != null)
				msgs = ((InternalEObject)newInitialSize).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DB_CONCEPTS_CONFIG__INITIAL_SIZE, null, msgs);
			msgs = basicSetInitialSize(newInitialSize, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DB_CONCEPTS_CONFIG__INITIAL_SIZE, newInitialSize, newInitialSize));
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    public OverrideConfig getMaxSize() {
		return maxSize;
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    public NotificationChain basicSetMaxSize(OverrideConfig newMaxSize, NotificationChain msgs) {
		OverrideConfig oldMaxSize = maxSize;
		maxSize = newMaxSize;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DB_CONCEPTS_CONFIG__MAX_SIZE, oldMaxSize, newMaxSize);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    public void setMaxSize(OverrideConfig newMaxSize) {
		if (newMaxSize != maxSize) {
			NotificationChain msgs = null;
			if (maxSize != null)
				msgs = ((InternalEObject)maxSize).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DB_CONCEPTS_CONFIG__MAX_SIZE, null, msgs);
			if (newMaxSize != null)
				msgs = ((InternalEObject)newMaxSize).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DB_CONCEPTS_CONFIG__MAX_SIZE, null, msgs);
			msgs = basicSetMaxSize(newMaxSize, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DB_CONCEPTS_CONFIG__MAX_SIZE, newMaxSize, newMaxSize));
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    public OverrideConfig getMinSize() {
		return minSize;
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    public NotificationChain basicSetMinSize(OverrideConfig newMinSize, NotificationChain msgs) {
		OverrideConfig oldMinSize = minSize;
		minSize = newMinSize;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DB_CONCEPTS_CONFIG__MIN_SIZE, oldMinSize, newMinSize);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    public void setMinSize(OverrideConfig newMinSize) {
		if (newMinSize != minSize) {
			NotificationChain msgs = null;
			if (minSize != null)
				msgs = ((InternalEObject)minSize).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DB_CONCEPTS_CONFIG__MIN_SIZE, null, msgs);
			if (newMinSize != null)
				msgs = ((InternalEObject)newMinSize).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DB_CONCEPTS_CONFIG__MIN_SIZE, null, msgs);
			msgs = basicSetMinSize(newMinSize, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DB_CONCEPTS_CONFIG__MIN_SIZE, newMinSize, newMinSize));
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    public OverrideConfig getPropertyCheckInterval() {
		return propertyCheckInterval;
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    public NotificationChain basicSetPropertyCheckInterval(OverrideConfig newPropertyCheckInterval, NotificationChain msgs) {
		OverrideConfig oldPropertyCheckInterval = propertyCheckInterval;
		propertyCheckInterval = newPropertyCheckInterval;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DB_CONCEPTS_CONFIG__PROPERTY_CHECK_INTERVAL, oldPropertyCheckInterval, newPropertyCheckInterval);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    public void setPropertyCheckInterval(OverrideConfig newPropertyCheckInterval) {
		if (newPropertyCheckInterval != propertyCheckInterval) {
			NotificationChain msgs = null;
			if (propertyCheckInterval != null)
				msgs = ((InternalEObject)propertyCheckInterval).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DB_CONCEPTS_CONFIG__PROPERTY_CHECK_INTERVAL, null, msgs);
			if (newPropertyCheckInterval != null)
				msgs = ((InternalEObject)newPropertyCheckInterval).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DB_CONCEPTS_CONFIG__PROPERTY_CHECK_INTERVAL, null, msgs);
			msgs = basicSetPropertyCheckInterval(newPropertyCheckInterval, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DB_CONCEPTS_CONFIG__PROPERTY_CHECK_INTERVAL, newPropertyCheckInterval, newPropertyCheckInterval));
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    public OverrideConfig getRetryCount() {
		return retryCount;
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    public NotificationChain basicSetRetryCount(OverrideConfig newRetryCount, NotificationChain msgs) {
		OverrideConfig oldRetryCount = retryCount;
		retryCount = newRetryCount;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DB_CONCEPTS_CONFIG__RETRY_COUNT, oldRetryCount, newRetryCount);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    public void setRetryCount(OverrideConfig newRetryCount) {
		if (newRetryCount != retryCount) {
			NotificationChain msgs = null;
			if (retryCount != null)
				msgs = ((InternalEObject)retryCount).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DB_CONCEPTS_CONFIG__RETRY_COUNT, null, msgs);
			if (newRetryCount != null)
				msgs = ((InternalEObject)newRetryCount).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DB_CONCEPTS_CONFIG__RETRY_COUNT, null, msgs);
			msgs = basicSetRetryCount(newRetryCount, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DB_CONCEPTS_CONFIG__RETRY_COUNT, newRetryCount, newRetryCount));
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    public OverrideConfig getWaitTimeout() {
		return waitTimeout;
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    public NotificationChain basicSetWaitTimeout(OverrideConfig newWaitTimeout, NotificationChain msgs) {
		OverrideConfig oldWaitTimeout = waitTimeout;
		waitTimeout = newWaitTimeout;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DB_CONCEPTS_CONFIG__WAIT_TIMEOUT, oldWaitTimeout, newWaitTimeout);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    public void setWaitTimeout(OverrideConfig newWaitTimeout) {
		if (newWaitTimeout != waitTimeout) {
			NotificationChain msgs = null;
			if (waitTimeout != null)
				msgs = ((InternalEObject)waitTimeout).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DB_CONCEPTS_CONFIG__WAIT_TIMEOUT, null, msgs);
			if (newWaitTimeout != null)
				msgs = ((InternalEObject)newWaitTimeout).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DB_CONCEPTS_CONFIG__WAIT_TIMEOUT, null, msgs);
			msgs = basicSetWaitTimeout(newWaitTimeout, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DB_CONCEPTS_CONFIG__WAIT_TIMEOUT, newWaitTimeout, newWaitTimeout));
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.DB_CONCEPTS_CONFIG__CHECK_INTERVAL:
				return basicSetCheckInterval(null, msgs);
			case CddPackage.DB_CONCEPTS_CONFIG__DB_URIS:
				return basicSetDbUris(null, msgs);
			case CddPackage.DB_CONCEPTS_CONFIG__INACTIVITY_TIMEOUT:
				return basicSetInactivityTimeout(null, msgs);
			case CddPackage.DB_CONCEPTS_CONFIG__INITIAL_SIZE:
				return basicSetInitialSize(null, msgs);
			case CddPackage.DB_CONCEPTS_CONFIG__MAX_SIZE:
				return basicSetMaxSize(null, msgs);
			case CddPackage.DB_CONCEPTS_CONFIG__MIN_SIZE:
				return basicSetMinSize(null, msgs);
			case CddPackage.DB_CONCEPTS_CONFIG__PROPERTY_CHECK_INTERVAL:
				return basicSetPropertyCheckInterval(null, msgs);
			case CddPackage.DB_CONCEPTS_CONFIG__RETRY_COUNT:
				return basicSetRetryCount(null, msgs);
			case CddPackage.DB_CONCEPTS_CONFIG__WAIT_TIMEOUT:
				return basicSetWaitTimeout(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CddPackage.DB_CONCEPTS_CONFIG__CHECK_INTERVAL:
				return getCheckInterval();
			case CddPackage.DB_CONCEPTS_CONFIG__DB_URIS:
				return getDbUris();
			case CddPackage.DB_CONCEPTS_CONFIG__INACTIVITY_TIMEOUT:
				return getInactivityTimeout();
			case CddPackage.DB_CONCEPTS_CONFIG__INITIAL_SIZE:
				return getInitialSize();
			case CddPackage.DB_CONCEPTS_CONFIG__MAX_SIZE:
				return getMaxSize();
			case CddPackage.DB_CONCEPTS_CONFIG__MIN_SIZE:
				return getMinSize();
			case CddPackage.DB_CONCEPTS_CONFIG__PROPERTY_CHECK_INTERVAL:
				return getPropertyCheckInterval();
			case CddPackage.DB_CONCEPTS_CONFIG__RETRY_COUNT:
				return getRetryCount();
			case CddPackage.DB_CONCEPTS_CONFIG__WAIT_TIMEOUT:
				return getWaitTimeout();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CddPackage.DB_CONCEPTS_CONFIG__CHECK_INTERVAL:
				setCheckInterval((OverrideConfig)newValue);
				return;
			case CddPackage.DB_CONCEPTS_CONFIG__DB_URIS:
				setDbUris((UrisConfig)newValue);
				return;
			case CddPackage.DB_CONCEPTS_CONFIG__INACTIVITY_TIMEOUT:
				setInactivityTimeout((OverrideConfig)newValue);
				return;
			case CddPackage.DB_CONCEPTS_CONFIG__INITIAL_SIZE:
				setInitialSize((OverrideConfig)newValue);
				return;
			case CddPackage.DB_CONCEPTS_CONFIG__MAX_SIZE:
				setMaxSize((OverrideConfig)newValue);
				return;
			case CddPackage.DB_CONCEPTS_CONFIG__MIN_SIZE:
				setMinSize((OverrideConfig)newValue);
				return;
			case CddPackage.DB_CONCEPTS_CONFIG__PROPERTY_CHECK_INTERVAL:
				setPropertyCheckInterval((OverrideConfig)newValue);
				return;
			case CddPackage.DB_CONCEPTS_CONFIG__RETRY_COUNT:
				setRetryCount((OverrideConfig)newValue);
				return;
			case CddPackage.DB_CONCEPTS_CONFIG__WAIT_TIMEOUT:
				setWaitTimeout((OverrideConfig)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    @Override
    public void eUnset(int featureID) {
		switch (featureID) {
			case CddPackage.DB_CONCEPTS_CONFIG__CHECK_INTERVAL:
				setCheckInterval((OverrideConfig)null);
				return;
			case CddPackage.DB_CONCEPTS_CONFIG__DB_URIS:
				setDbUris((UrisConfig)null);
				return;
			case CddPackage.DB_CONCEPTS_CONFIG__INACTIVITY_TIMEOUT:
				setInactivityTimeout((OverrideConfig)null);
				return;
			case CddPackage.DB_CONCEPTS_CONFIG__INITIAL_SIZE:
				setInitialSize((OverrideConfig)null);
				return;
			case CddPackage.DB_CONCEPTS_CONFIG__MAX_SIZE:
				setMaxSize((OverrideConfig)null);
				return;
			case CddPackage.DB_CONCEPTS_CONFIG__MIN_SIZE:
				setMinSize((OverrideConfig)null);
				return;
			case CddPackage.DB_CONCEPTS_CONFIG__PROPERTY_CHECK_INTERVAL:
				setPropertyCheckInterval((OverrideConfig)null);
				return;
			case CddPackage.DB_CONCEPTS_CONFIG__RETRY_COUNT:
				setRetryCount((OverrideConfig)null);
				return;
			case CddPackage.DB_CONCEPTS_CONFIG__WAIT_TIMEOUT:
				setWaitTimeout((OverrideConfig)null);
				return;
		}
		super.eUnset(featureID);
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CddPackage.DB_CONCEPTS_CONFIG__CHECK_INTERVAL:
				return checkInterval != null;
			case CddPackage.DB_CONCEPTS_CONFIG__DB_URIS:
				return dbUris != null;
			case CddPackage.DB_CONCEPTS_CONFIG__INACTIVITY_TIMEOUT:
				return inactivityTimeout != null;
			case CddPackage.DB_CONCEPTS_CONFIG__INITIAL_SIZE:
				return initialSize != null;
			case CddPackage.DB_CONCEPTS_CONFIG__MAX_SIZE:
				return maxSize != null;
			case CddPackage.DB_CONCEPTS_CONFIG__MIN_SIZE:
				return minSize != null;
			case CddPackage.DB_CONCEPTS_CONFIG__PROPERTY_CHECK_INTERVAL:
				return propertyCheckInterval != null;
			case CddPackage.DB_CONCEPTS_CONFIG__RETRY_COUNT:
				return retryCount != null;
			case CddPackage.DB_CONCEPTS_CONFIG__WAIT_TIMEOUT:
				return waitTimeout != null;
		}
		return super.eIsSet(featureID);
	}

    @Override
    public Map<Object, Object> toProperties() {
        final Properties props = new Properties(); 
        //Get all uris
        UrisConfig dbUris = getDbUris();
        List<String> sharedResourceUris = dbUris.getUri();
        StringBuilder stringBuilder = new StringBuilder();
        
        for (int loop = 0, length = sharedResourceUris.size(); loop < length; loop++) {
            String sharedResourceUri = sharedResourceUris.get(loop);
            stringBuilder.append(sharedResourceUri);
            //Append comma
            stringBuilder.append(",");
        }
        
        String dbUriList = (stringBuilder.length() > 0) ? stringBuilder.substring(0, stringBuilder.length()-1) : stringBuilder.toString();
        
        props.put(SystemProperty.DB_CONCEPTS_DBURI.getPropertyName(), dbUriList);
        props.put(SystemProperty.DB_CONCEPTS_POOL_INITIAL.getPropertyName(), CddTools.getValueFromMixed(getInitialSize()));
        props.put(SystemProperty.DB_CONCEPTS_POOL_MIN.getPropertyName(), CddTools.getValueFromMixed(getMinSize()));
        props.put(SystemProperty.DB_CONCEPTS_POOL_MAX.getPropertyName(), CddTools.getValueFromMixed(getMaxSize()));
        props.put(SystemProperty.DB_CONCEPTS_POOL_INACTIVITY_TIMEOUT.getPropertyName(), CddTools.getValueFromMixed(getInactivityTimeout()));
        props.put(SystemProperty.DB_CONCEPTS_PROPERTY_CHECK_INTERVAL.getPropertyName(), CddTools.getValueFromMixed(getCheckInterval()));
        props.put(SystemProperty.DB_CONCEPTS_CONNECTION_RETRY_COUNT.getPropertyName(), CddTools.getValueFromMixed(getRetryCount()));
        props.put(SystemProperty.DB_CONCEPTS_POOL_WAIT_TIME.getPropertyName(), CddTools.getValueFromMixed(getWaitTimeout()));
        return props;
    }
} //DbConceptsConfigImpl
