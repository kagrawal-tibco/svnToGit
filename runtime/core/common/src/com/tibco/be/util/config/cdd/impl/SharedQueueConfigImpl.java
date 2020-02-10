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

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.be.util.config.cdd.SharedQueueConfig;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Shared Queue Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.SharedQueueConfigImpl#getSize <em>Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.SharedQueueConfigImpl#getWorkers <em>Workers</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SharedQueueConfigImpl extends EObjectImpl implements SharedQueueConfig {
	/**
	 * The cached value of the '{@link #getSize() <em>Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSize()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig size;

	/**
	 * The cached value of the '{@link #getWorkers() <em>Workers</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWorkers()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig workers;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SharedQueueConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getSharedQueueConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getSize() {
		return size;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSize(OverrideConfig newSize, NotificationChain msgs) {
		OverrideConfig oldSize = size;
		size = newSize;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.SHARED_QUEUE_CONFIG__SIZE, oldSize, newSize);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSize(OverrideConfig newSize) {
		if (newSize != size) {
			NotificationChain msgs = null;
			if (size != null)
				msgs = ((InternalEObject)size).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.SHARED_QUEUE_CONFIG__SIZE, null, msgs);
			if (newSize != null)
				msgs = ((InternalEObject)newSize).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.SHARED_QUEUE_CONFIG__SIZE, null, msgs);
			msgs = basicSetSize(newSize, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.SHARED_QUEUE_CONFIG__SIZE, newSize, newSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getWorkers() {
		return workers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetWorkers(OverrideConfig newWorkers, NotificationChain msgs) {
		OverrideConfig oldWorkers = workers;
		workers = newWorkers;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.SHARED_QUEUE_CONFIG__WORKERS, oldWorkers, newWorkers);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWorkers(OverrideConfig newWorkers) {
		if (newWorkers != workers) {
			NotificationChain msgs = null;
			if (workers != null)
				msgs = ((InternalEObject)workers).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.SHARED_QUEUE_CONFIG__WORKERS, null, msgs);
			if (newWorkers != null)
				msgs = ((InternalEObject)newWorkers).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.SHARED_QUEUE_CONFIG__WORKERS, null, msgs);
			msgs = basicSetWorkers(newWorkers, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.SHARED_QUEUE_CONFIG__WORKERS, newWorkers, newWorkers));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Map<Object, Object> toProperties() {
        final Properties p = new Properties();        
        CddTools.addEntryFromMixed(p, SystemProperty.SHARED_QUEUE_SIZE.getPropertyName(), this.size, true);        
        CddTools.addEntryFromMixed(p, SystemProperty.SHARED_QUEUE_WORKERS.getPropertyName(), this.workers, true);
        return p;
    }

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.SHARED_QUEUE_CONFIG__SIZE:
				return basicSetSize(null, msgs);
			case CddPackage.SHARED_QUEUE_CONFIG__WORKERS:
				return basicSetWorkers(null, msgs);
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
			case CddPackage.SHARED_QUEUE_CONFIG__SIZE:
				return getSize();
			case CddPackage.SHARED_QUEUE_CONFIG__WORKERS:
				return getWorkers();
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
			case CddPackage.SHARED_QUEUE_CONFIG__SIZE:
				setSize((OverrideConfig)newValue);
				return;
			case CddPackage.SHARED_QUEUE_CONFIG__WORKERS:
				setWorkers((OverrideConfig)newValue);
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
			case CddPackage.SHARED_QUEUE_CONFIG__SIZE:
				setSize((OverrideConfig)null);
				return;
			case CddPackage.SHARED_QUEUE_CONFIG__WORKERS:
				setWorkers((OverrideConfig)null);
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
			case CddPackage.SHARED_QUEUE_CONFIG__SIZE:
				return size != null;
			case CddPackage.SHARED_QUEUE_CONFIG__WORKERS:
				return workers != null;
		}
		return super.eIsSet(featureID);
	}

} //SharedQueueConfigImpl
