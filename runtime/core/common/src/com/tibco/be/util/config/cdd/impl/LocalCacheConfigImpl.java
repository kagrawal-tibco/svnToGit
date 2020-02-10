/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import com.tibco.cep.runtime.util.SystemProperty;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.EvictionConfig;
import com.tibco.be.util.config.cdd.LocalCacheConfig;

import java.util.Map;
import java.util.Properties;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Local Cache Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LocalCacheConfigImpl#getEviction <em>Eviction</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LocalCacheConfigImpl extends ArtifactConfigImpl implements LocalCacheConfig {
	/**
	 * The cached value of the '{@link #getEviction() <em>Eviction</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEviction()
	 * @generated
	 * @ordered
	 */
	protected EvictionConfig eviction;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LocalCacheConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getLocalCacheConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EvictionConfig getEviction() {
		return eviction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEviction(EvictionConfig newEviction, NotificationChain msgs) {
		EvictionConfig oldEviction = eviction;
		eviction = newEviction;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LOCAL_CACHE_CONFIG__EVICTION, oldEviction, newEviction);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEviction(EvictionConfig newEviction) {
		if (newEviction != eviction) {
			NotificationChain msgs = null;
			if (eviction != null)
				msgs = ((InternalEObject)eviction).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOCAL_CACHE_CONFIG__EVICTION, null, msgs);
			if (newEviction != null)
				msgs = ((InternalEObject)newEviction).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOCAL_CACHE_CONFIG__EVICTION, null, msgs);
			msgs = basicSetEviction(newEviction, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LOCAL_CACHE_CONFIG__EVICTION, newEviction, newEviction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.LOCAL_CACHE_CONFIG__EVICTION:
				return basicSetEviction(null, msgs);
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
			case CddPackage.LOCAL_CACHE_CONFIG__EVICTION:
				return getEviction();
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
			case CddPackage.LOCAL_CACHE_CONFIG__EVICTION:
				setEviction((EvictionConfig)newValue);
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
			case CddPackage.LOCAL_CACHE_CONFIG__EVICTION:
				setEviction((EvictionConfig)null);
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
			case CddPackage.LOCAL_CACHE_CONFIG__EVICTION:
				return eviction != null;
		}
		return super.eIsSet(featureID);
	}


    /**
     * @generated not
     */
    @Override
    public Map<Object, Object> toProperties() {
        final EvictionConfig eviction = this.getEviction();
        return (null == eviction)
                ? new Properties()
                : eviction.toProperties();
    }

} //LocalCacheConfigImpl
