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
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.MmAlertConfig;
import com.tibco.be.util.config.cdd.MmHealthLevelConfig;
import com.tibco.be.util.config.cdd.MmTriggerConditionConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mm Trigger Condition Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.MmTriggerConditionConfigImpl#getMmHealthLevel <em>Mm Health Level</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.MmTriggerConditionConfigImpl#getMmAlert <em>Mm Alert</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MmTriggerConditionConfigImpl extends EObjectImpl implements MmTriggerConditionConfig {
	/**
	 * The cached value of the '{@link #getMmHealthLevel() <em>Mm Health Level</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMmHealthLevel()
	 * @generated
	 * @ordered
	 */
	protected MmHealthLevelConfig mmHealthLevel;

	/**
	 * The cached value of the '{@link #getMmAlert() <em>Mm Alert</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMmAlert()
	 * @generated
	 * @ordered
	 */
	protected MmAlertConfig mmAlert;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MmTriggerConditionConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getMmTriggerConditionConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MmHealthLevelConfig getMmHealthLevel() {
		return mmHealthLevel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMmHealthLevel(MmHealthLevelConfig newMmHealthLevel, NotificationChain msgs) {
		MmHealthLevelConfig oldMmHealthLevel = mmHealthLevel;
		mmHealthLevel = newMmHealthLevel;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.MM_TRIGGER_CONDITION_CONFIG__MM_HEALTH_LEVEL, oldMmHealthLevel, newMmHealthLevel);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMmHealthLevel(MmHealthLevelConfig newMmHealthLevel) {
		if (newMmHealthLevel != mmHealthLevel) {
			NotificationChain msgs = null;
			if (mmHealthLevel != null)
				msgs = ((InternalEObject)mmHealthLevel).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.MM_TRIGGER_CONDITION_CONFIG__MM_HEALTH_LEVEL, null, msgs);
			if (newMmHealthLevel != null)
				msgs = ((InternalEObject)newMmHealthLevel).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.MM_TRIGGER_CONDITION_CONFIG__MM_HEALTH_LEVEL, null, msgs);
			msgs = basicSetMmHealthLevel(newMmHealthLevel, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.MM_TRIGGER_CONDITION_CONFIG__MM_HEALTH_LEVEL, newMmHealthLevel, newMmHealthLevel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MmAlertConfig getMmAlert() {
		return mmAlert;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMmAlert(MmAlertConfig newMmAlert, NotificationChain msgs) {
		MmAlertConfig oldMmAlert = mmAlert;
		mmAlert = newMmAlert;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.MM_TRIGGER_CONDITION_CONFIG__MM_ALERT, oldMmAlert, newMmAlert);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMmAlert(MmAlertConfig newMmAlert) {
		if (newMmAlert != mmAlert) {
			NotificationChain msgs = null;
			if (mmAlert != null)
				msgs = ((InternalEObject)mmAlert).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.MM_TRIGGER_CONDITION_CONFIG__MM_ALERT, null, msgs);
			if (newMmAlert != null)
				msgs = ((InternalEObject)newMmAlert).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.MM_TRIGGER_CONDITION_CONFIG__MM_ALERT, null, msgs);
			msgs = basicSetMmAlert(newMmAlert, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.MM_TRIGGER_CONDITION_CONFIG__MM_ALERT, newMmAlert, newMmAlert));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.MM_TRIGGER_CONDITION_CONFIG__MM_HEALTH_LEVEL:
				return basicSetMmHealthLevel(null, msgs);
			case CddPackage.MM_TRIGGER_CONDITION_CONFIG__MM_ALERT:
				return basicSetMmAlert(null, msgs);
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
			case CddPackage.MM_TRIGGER_CONDITION_CONFIG__MM_HEALTH_LEVEL:
				return getMmHealthLevel();
			case CddPackage.MM_TRIGGER_CONDITION_CONFIG__MM_ALERT:
				return getMmAlert();
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
			case CddPackage.MM_TRIGGER_CONDITION_CONFIG__MM_HEALTH_LEVEL:
				setMmHealthLevel((MmHealthLevelConfig)newValue);
				return;
			case CddPackage.MM_TRIGGER_CONDITION_CONFIG__MM_ALERT:
				setMmAlert((MmAlertConfig)newValue);
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
			case CddPackage.MM_TRIGGER_CONDITION_CONFIG__MM_HEALTH_LEVEL:
				setMmHealthLevel((MmHealthLevelConfig)null);
				return;
			case CddPackage.MM_TRIGGER_CONDITION_CONFIG__MM_ALERT:
				setMmAlert((MmAlertConfig)null);
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
			case CddPackage.MM_TRIGGER_CONDITION_CONFIG__MM_HEALTH_LEVEL:
				return mmHealthLevel != null;
			case CddPackage.MM_TRIGGER_CONDITION_CONFIG__MM_ALERT:
				return mmAlert != null;
		}
		return super.eIsSet(featureID);
	}

} //MmTriggerConditionConfigImpl
