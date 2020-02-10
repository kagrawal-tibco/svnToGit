/**
 */
package com.tibco.be.util.config.cdd.impl;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.be.util.config.cdd.PublisherConfig;

import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Publisher Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.PublisherConfigImpl#getPublisherQueueSize <em>Publisher Queue Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.PublisherConfigImpl#getPublisherThreadCount <em>Publisher Thread Count</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PublisherConfigImpl extends EObjectImpl implements PublisherConfig {
	/**
	 * The cached value of the '{@link #getPublisherQueueSize() <em>Publisher Queue Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPublisherQueueSize()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig publisherQueueSize;

	/**
	 * The cached value of the '{@link #getPublisherThreadCount() <em>Publisher Thread Count</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPublisherThreadCount()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig publisherThreadCount;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PublisherConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getPublisherConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getPublisherQueueSize() {
		return publisherQueueSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPublisherQueueSize(OverrideConfig newPublisherQueueSize, NotificationChain msgs) {
		OverrideConfig oldPublisherQueueSize = publisherQueueSize;
		publisherQueueSize = newPublisherQueueSize;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.PUBLISHER_CONFIG__PUBLISHER_QUEUE_SIZE, oldPublisherQueueSize, newPublisherQueueSize);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPublisherQueueSize(OverrideConfig newPublisherQueueSize) {
		if (newPublisherQueueSize != publisherQueueSize) {
			NotificationChain msgs = null;
			if (publisherQueueSize != null)
				msgs = ((InternalEObject)publisherQueueSize).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.PUBLISHER_CONFIG__PUBLISHER_QUEUE_SIZE, null, msgs);
			if (newPublisherQueueSize != null)
				msgs = ((InternalEObject)newPublisherQueueSize).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.PUBLISHER_CONFIG__PUBLISHER_QUEUE_SIZE, null, msgs);
			msgs = basicSetPublisherQueueSize(newPublisherQueueSize, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.PUBLISHER_CONFIG__PUBLISHER_QUEUE_SIZE, newPublisherQueueSize, newPublisherQueueSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getPublisherThreadCount() {
		return publisherThreadCount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPublisherThreadCount(OverrideConfig newPublisherThreadCount, NotificationChain msgs) {
		OverrideConfig oldPublisherThreadCount = publisherThreadCount;
		publisherThreadCount = newPublisherThreadCount;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.PUBLISHER_CONFIG__PUBLISHER_THREAD_COUNT, oldPublisherThreadCount, newPublisherThreadCount);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPublisherThreadCount(OverrideConfig newPublisherThreadCount) {
		if (newPublisherThreadCount != publisherThreadCount) {
			NotificationChain msgs = null;
			if (publisherThreadCount != null)
				msgs = ((InternalEObject)publisherThreadCount).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.PUBLISHER_CONFIG__PUBLISHER_THREAD_COUNT, null, msgs);
			if (newPublisherThreadCount != null)
				msgs = ((InternalEObject)newPublisherThreadCount).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.PUBLISHER_CONFIG__PUBLISHER_THREAD_COUNT, null, msgs);
			msgs = basicSetPublisherThreadCount(newPublisherThreadCount, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.PUBLISHER_CONFIG__PUBLISHER_THREAD_COUNT, newPublisherThreadCount, newPublisherThreadCount));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map<Object, Object> toProperties() {
		return new java.util.Properties();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.PUBLISHER_CONFIG__PUBLISHER_QUEUE_SIZE:
				return basicSetPublisherQueueSize(null, msgs);
			case CddPackage.PUBLISHER_CONFIG__PUBLISHER_THREAD_COUNT:
				return basicSetPublisherThreadCount(null, msgs);
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
			case CddPackage.PUBLISHER_CONFIG__PUBLISHER_QUEUE_SIZE:
				return getPublisherQueueSize();
			case CddPackage.PUBLISHER_CONFIG__PUBLISHER_THREAD_COUNT:
				return getPublisherThreadCount();
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
			case CddPackage.PUBLISHER_CONFIG__PUBLISHER_QUEUE_SIZE:
				setPublisherQueueSize((OverrideConfig)newValue);
				return;
			case CddPackage.PUBLISHER_CONFIG__PUBLISHER_THREAD_COUNT:
				setPublisherThreadCount((OverrideConfig)newValue);
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
			case CddPackage.PUBLISHER_CONFIG__PUBLISHER_QUEUE_SIZE:
				setPublisherQueueSize((OverrideConfig)null);
				return;
			case CddPackage.PUBLISHER_CONFIG__PUBLISHER_THREAD_COUNT:
				setPublisherThreadCount((OverrideConfig)null);
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
			case CddPackage.PUBLISHER_CONFIG__PUBLISHER_QUEUE_SIZE:
				return publisherQueueSize != null;
			case CddPackage.PUBLISHER_CONFIG__PUBLISHER_THREAD_COUNT:
				return publisherThreadCount != null;
		}
		return super.eIsSet(featureID);
	}

} //PublisherConfigImpl
