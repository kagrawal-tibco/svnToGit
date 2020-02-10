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
import com.tibco.be.util.config.cdd.JobManagerConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Job Manager Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.JobManagerConfigImpl#getJobPoolQueueSize <em>Job Pool Queue Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.JobManagerConfigImpl#getJobPoolThreadCount <em>Job Pool Thread Count</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class JobManagerConfigImpl extends EObjectImpl implements JobManagerConfig {
	/**
	 * The cached value of the '{@link #getJobPoolQueueSize() <em>Job Pool Queue Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJobPoolQueueSize()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig jobPoolQueueSize;

	/**
	 * The cached value of the '{@link #getJobPoolThreadCount() <em>Job Pool Thread Count</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJobPoolThreadCount()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig jobPoolThreadCount;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JobManagerConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getJobManagerConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getJobPoolQueueSize() {
		return jobPoolQueueSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJobPoolQueueSize(OverrideConfig newJobPoolQueueSize, NotificationChain msgs) {
		OverrideConfig oldJobPoolQueueSize = jobPoolQueueSize;
		jobPoolQueueSize = newJobPoolQueueSize;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.JOB_MANAGER_CONFIG__JOB_POOL_QUEUE_SIZE, oldJobPoolQueueSize, newJobPoolQueueSize);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJobPoolQueueSize(OverrideConfig newJobPoolQueueSize) {
		if (newJobPoolQueueSize != jobPoolQueueSize) {
			NotificationChain msgs = null;
			if (jobPoolQueueSize != null)
				msgs = ((InternalEObject)jobPoolQueueSize).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.JOB_MANAGER_CONFIG__JOB_POOL_QUEUE_SIZE, null, msgs);
			if (newJobPoolQueueSize != null)
				msgs = ((InternalEObject)newJobPoolQueueSize).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.JOB_MANAGER_CONFIG__JOB_POOL_QUEUE_SIZE, null, msgs);
			msgs = basicSetJobPoolQueueSize(newJobPoolQueueSize, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.JOB_MANAGER_CONFIG__JOB_POOL_QUEUE_SIZE, newJobPoolQueueSize, newJobPoolQueueSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getJobPoolThreadCount() {
		return jobPoolThreadCount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJobPoolThreadCount(OverrideConfig newJobPoolThreadCount, NotificationChain msgs) {
		OverrideConfig oldJobPoolThreadCount = jobPoolThreadCount;
		jobPoolThreadCount = newJobPoolThreadCount;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.JOB_MANAGER_CONFIG__JOB_POOL_THREAD_COUNT, oldJobPoolThreadCount, newJobPoolThreadCount);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJobPoolThreadCount(OverrideConfig newJobPoolThreadCount) {
		if (newJobPoolThreadCount != jobPoolThreadCount) {
			NotificationChain msgs = null;
			if (jobPoolThreadCount != null)
				msgs = ((InternalEObject)jobPoolThreadCount).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.JOB_MANAGER_CONFIG__JOB_POOL_THREAD_COUNT, null, msgs);
			if (newJobPoolThreadCount != null)
				msgs = ((InternalEObject)newJobPoolThreadCount).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.JOB_MANAGER_CONFIG__JOB_POOL_THREAD_COUNT, null, msgs);
			msgs = basicSetJobPoolThreadCount(newJobPoolThreadCount, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.JOB_MANAGER_CONFIG__JOB_POOL_THREAD_COUNT, newJobPoolThreadCount, newJobPoolThreadCount));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Map<Object, Object> toProperties() {
		final Properties p = new Properties();

		CddTools.addEntryFromMixed(
				p, "ProcessAgent.${AgentGroupName}.jobQueueSize",
				this.getJobPoolQueueSize(), true);
		
		CddTools.addEntryFromMixed(
				p, "ProcessAgent.${AgentGroupName}.jobThreadPoolSize",
				this.getJobPoolThreadCount(), true);

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
			case CddPackage.JOB_MANAGER_CONFIG__JOB_POOL_QUEUE_SIZE:
				return basicSetJobPoolQueueSize(null, msgs);
			case CddPackage.JOB_MANAGER_CONFIG__JOB_POOL_THREAD_COUNT:
				return basicSetJobPoolThreadCount(null, msgs);
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
			case CddPackage.JOB_MANAGER_CONFIG__JOB_POOL_QUEUE_SIZE:
				return getJobPoolQueueSize();
			case CddPackage.JOB_MANAGER_CONFIG__JOB_POOL_THREAD_COUNT:
				return getJobPoolThreadCount();
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
			case CddPackage.JOB_MANAGER_CONFIG__JOB_POOL_QUEUE_SIZE:
				setJobPoolQueueSize((OverrideConfig)newValue);
				return;
			case CddPackage.JOB_MANAGER_CONFIG__JOB_POOL_THREAD_COUNT:
				setJobPoolThreadCount((OverrideConfig)newValue);
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
			case CddPackage.JOB_MANAGER_CONFIG__JOB_POOL_QUEUE_SIZE:
				setJobPoolQueueSize((OverrideConfig)null);
				return;
			case CddPackage.JOB_MANAGER_CONFIG__JOB_POOL_THREAD_COUNT:
				setJobPoolThreadCount((OverrideConfig)null);
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
			case CddPackage.JOB_MANAGER_CONFIG__JOB_POOL_QUEUE_SIZE:
				return jobPoolQueueSize != null;
			case CddPackage.JOB_MANAGER_CONFIG__JOB_POOL_THREAD_COUNT:
				return jobPoolThreadCount != null;
		}
		return super.eIsSet(featureID);
	}

} //JobManagerConfigImpl
