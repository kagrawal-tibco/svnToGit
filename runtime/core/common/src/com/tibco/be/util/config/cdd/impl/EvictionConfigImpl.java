/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import com.tibco.be.util.config.CddTools;
import com.tibco.cep.runtime.service.cluster.agent.AgentCapability;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.EvictionConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;

import java.util.Map;
import java.util.Properties;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Eviction Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.EvictionConfigImpl#getMaxSize <em>Max Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.EvictionConfigImpl#getMaxTime <em>Max Time</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EvictionConfigImpl extends ArtifactConfigImpl implements EvictionConfig {
	/**
	 * The cached value of the '{@link #getMaxSize() <em>Max Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxSize()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig maxSize;

	/**
	 * The cached value of the '{@link #getMaxTime() <em>Max Time</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxTime()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig maxTime;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EvictionConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getEvictionConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getMaxSize() {
		return maxSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMaxSize(OverrideConfig newMaxSize, NotificationChain msgs) {
		OverrideConfig oldMaxSize = maxSize;
		maxSize = newMaxSize;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.EVICTION_CONFIG__MAX_SIZE, oldMaxSize, newMaxSize);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxSize(OverrideConfig newMaxSize) {
		if (newMaxSize != maxSize) {
			NotificationChain msgs = null;
			if (maxSize != null)
				msgs = ((InternalEObject)maxSize).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.EVICTION_CONFIG__MAX_SIZE, null, msgs);
			if (newMaxSize != null)
				msgs = ((InternalEObject)newMaxSize).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.EVICTION_CONFIG__MAX_SIZE, null, msgs);
			msgs = basicSetMaxSize(newMaxSize, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.EVICTION_CONFIG__MAX_SIZE, newMaxSize, newMaxSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getMaxTime() {
		return maxTime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMaxTime(OverrideConfig newMaxTime, NotificationChain msgs) {
		OverrideConfig oldMaxTime = maxTime;
		maxTime = newMaxTime;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.EVICTION_CONFIG__MAX_TIME, oldMaxTime, newMaxTime);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxTime(OverrideConfig newMaxTime) {
		if (newMaxTime != maxTime) {
			NotificationChain msgs = null;
			if (maxTime != null)
				msgs = ((InternalEObject)maxTime).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.EVICTION_CONFIG__MAX_TIME, null, msgs);
			if (newMaxTime != null)
				msgs = ((InternalEObject)newMaxTime).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.EVICTION_CONFIG__MAX_TIME, null, msgs);
			msgs = basicSetMaxTime(newMaxTime, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.EVICTION_CONFIG__MAX_TIME, newMaxTime, newMaxTime));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.EVICTION_CONFIG__MAX_SIZE:
				return basicSetMaxSize(null, msgs);
			case CddPackage.EVICTION_CONFIG__MAX_TIME:
				return basicSetMaxTime(null, msgs);
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
			case CddPackage.EVICTION_CONFIG__MAX_SIZE:
				return getMaxSize();
			case CddPackage.EVICTION_CONFIG__MAX_TIME:
				return getMaxTime();
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
			case CddPackage.EVICTION_CONFIG__MAX_SIZE:
				setMaxSize((OverrideConfig)newValue);
				return;
			case CddPackage.EVICTION_CONFIG__MAX_TIME:
				setMaxTime((OverrideConfig)newValue);
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
			case CddPackage.EVICTION_CONFIG__MAX_SIZE:
				setMaxSize((OverrideConfig)null);
				return;
			case CddPackage.EVICTION_CONFIG__MAX_TIME:
				setMaxTime((OverrideConfig)null);
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
			case CddPackage.EVICTION_CONFIG__MAX_SIZE:
				return maxSize != null;
			case CddPackage.EVICTION_CONFIG__MAX_TIME:
				return maxTime != null;
		}
		return super.eIsSet(featureID);
	}


    /**
     * @generated NOT
     */
    @Override
    public Map<Object, Object> toProperties() {
        final Properties properties = (Properties) super.toProperties();

        CddTools.addEntryFromMixed(
                properties,
                "Agent.${AgentGroupName}" + AgentCapability.L1CACHEEXPIRYMILLIS.getPropSuffix(),
                this.getMaxTime(),
                true);

        CddTools.addEntryFromMixed(
                properties,
                "Agent.${AgentGroupName}" + AgentCapability.L1CACHESIZE.getPropSuffix(),
                this.getMaxSize(),
                true);

        return properties;
    }


} //EvictionConfigImpl
