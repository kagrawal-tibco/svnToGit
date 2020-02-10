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
import com.tibco.be.util.config.cdd.MmActionConfig;
import com.tibco.be.util.config.cdd.MmActionConfigConfig;
import com.tibco.be.util.config.cdd.MmTriggerConditionConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mm Action Config Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.MmActionConfigConfigImpl#getMmTriggerCondition <em>Mm Trigger Condition</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.MmActionConfigConfigImpl#getMmAction <em>Mm Action</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.MmActionConfigConfigImpl#getActionName <em>Action Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MmActionConfigConfigImpl extends EObjectImpl implements MmActionConfigConfig {
	/**
	 * The cached value of the '{@link #getMmTriggerCondition() <em>Mm Trigger Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMmTriggerCondition()
	 * @generated
	 * @ordered
	 */
	protected MmTriggerConditionConfig mmTriggerCondition;

	/**
	 * The cached value of the '{@link #getMmAction() <em>Mm Action</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMmAction()
	 * @generated
	 * @ordered
	 */
	protected MmActionConfig mmAction;

	/**
	 * The default value of the '{@link #getActionName() <em>Action Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActionName()
	 * @generated
	 * @ordered
	 */
	protected static final String ACTION_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getActionName() <em>Action Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActionName()
	 * @generated
	 * @ordered
	 */
	protected String actionName = ACTION_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MmActionConfigConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getMmActionConfigConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MmTriggerConditionConfig getMmTriggerCondition() {
		return mmTriggerCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMmTriggerCondition(MmTriggerConditionConfig newMmTriggerCondition, NotificationChain msgs) {
		MmTriggerConditionConfig oldMmTriggerCondition = mmTriggerCondition;
		mmTriggerCondition = newMmTriggerCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.MM_ACTION_CONFIG_CONFIG__MM_TRIGGER_CONDITION, oldMmTriggerCondition, newMmTriggerCondition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMmTriggerCondition(MmTriggerConditionConfig newMmTriggerCondition) {
		if (newMmTriggerCondition != mmTriggerCondition) {
			NotificationChain msgs = null;
			if (mmTriggerCondition != null)
				msgs = ((InternalEObject)mmTriggerCondition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.MM_ACTION_CONFIG_CONFIG__MM_TRIGGER_CONDITION, null, msgs);
			if (newMmTriggerCondition != null)
				msgs = ((InternalEObject)newMmTriggerCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.MM_ACTION_CONFIG_CONFIG__MM_TRIGGER_CONDITION, null, msgs);
			msgs = basicSetMmTriggerCondition(newMmTriggerCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.MM_ACTION_CONFIG_CONFIG__MM_TRIGGER_CONDITION, newMmTriggerCondition, newMmTriggerCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MmActionConfig getMmAction() {
		return mmAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMmAction(MmActionConfig newMmAction, NotificationChain msgs) {
		MmActionConfig oldMmAction = mmAction;
		mmAction = newMmAction;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.MM_ACTION_CONFIG_CONFIG__MM_ACTION, oldMmAction, newMmAction);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMmAction(MmActionConfig newMmAction) {
		if (newMmAction != mmAction) {
			NotificationChain msgs = null;
			if (mmAction != null)
				msgs = ((InternalEObject)mmAction).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.MM_ACTION_CONFIG_CONFIG__MM_ACTION, null, msgs);
			if (newMmAction != null)
				msgs = ((InternalEObject)newMmAction).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.MM_ACTION_CONFIG_CONFIG__MM_ACTION, null, msgs);
			msgs = basicSetMmAction(newMmAction, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.MM_ACTION_CONFIG_CONFIG__MM_ACTION, newMmAction, newMmAction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getActionName() {
		return actionName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActionName(String newActionName) {
		String oldActionName = actionName;
		actionName = newActionName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.MM_ACTION_CONFIG_CONFIG__ACTION_NAME, oldActionName, actionName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.MM_ACTION_CONFIG_CONFIG__MM_TRIGGER_CONDITION:
				return basicSetMmTriggerCondition(null, msgs);
			case CddPackage.MM_ACTION_CONFIG_CONFIG__MM_ACTION:
				return basicSetMmAction(null, msgs);
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
			case CddPackage.MM_ACTION_CONFIG_CONFIG__MM_TRIGGER_CONDITION:
				return getMmTriggerCondition();
			case CddPackage.MM_ACTION_CONFIG_CONFIG__MM_ACTION:
				return getMmAction();
			case CddPackage.MM_ACTION_CONFIG_CONFIG__ACTION_NAME:
				return getActionName();
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
			case CddPackage.MM_ACTION_CONFIG_CONFIG__MM_TRIGGER_CONDITION:
				setMmTriggerCondition((MmTriggerConditionConfig)newValue);
				return;
			case CddPackage.MM_ACTION_CONFIG_CONFIG__MM_ACTION:
				setMmAction((MmActionConfig)newValue);
				return;
			case CddPackage.MM_ACTION_CONFIG_CONFIG__ACTION_NAME:
				setActionName((String)newValue);
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
			case CddPackage.MM_ACTION_CONFIG_CONFIG__MM_TRIGGER_CONDITION:
				setMmTriggerCondition((MmTriggerConditionConfig)null);
				return;
			case CddPackage.MM_ACTION_CONFIG_CONFIG__MM_ACTION:
				setMmAction((MmActionConfig)null);
				return;
			case CddPackage.MM_ACTION_CONFIG_CONFIG__ACTION_NAME:
				setActionName(ACTION_NAME_EDEFAULT);
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
			case CddPackage.MM_ACTION_CONFIG_CONFIG__MM_TRIGGER_CONDITION:
				return mmTriggerCondition != null;
			case CddPackage.MM_ACTION_CONFIG_CONFIG__MM_ACTION:
				return mmAction != null;
			case CddPackage.MM_ACTION_CONFIG_CONFIG__ACTION_NAME:
				return ACTION_NAME_EDEFAULT == null ? actionName != null : !ACTION_NAME_EDEFAULT.equals(actionName);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (actionName: ");
		result.append(actionName);
		result.append(')');
		return result.toString();
	}

} //MmActionConfigConfigImpl
