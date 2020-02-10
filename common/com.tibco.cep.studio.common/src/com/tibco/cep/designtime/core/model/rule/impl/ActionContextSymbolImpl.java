/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.rule.ActionContextSymbol;
import com.tibco.cep.designtime.core.model.rule.ActionType;
import com.tibco.cep.designtime.core.model.rule.RulePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Action Context Symbol</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.ActionContextSymbolImpl#getActionType <em>Action Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ActionContextSymbolImpl extends SymbolImpl implements ActionContextSymbol {
	/**
	 * The default value of the '{@link #getActionType() <em>Action Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActionType()
	 * @generated
	 * @ordered
	 */
	protected static final ActionType ACTION_TYPE_EDEFAULT = ActionType.CREATE;

	/**
	 * The cached value of the '{@link #getActionType() <em>Action Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActionType()
	 * @generated
	 * @ordered
	 */
	protected ActionType actionType = ACTION_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ActionContextSymbolImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulePackage.Literals.ACTION_CONTEXT_SYMBOL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActionType getActionType() {
		return actionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActionType(ActionType newActionType) {
		ActionType oldActionType = actionType;
		actionType = newActionType == null ? ACTION_TYPE_EDEFAULT : newActionType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.ACTION_CONTEXT_SYMBOL__ACTION_TYPE, oldActionType, actionType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RulePackage.ACTION_CONTEXT_SYMBOL__ACTION_TYPE:
				return getActionType();
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
			case RulePackage.ACTION_CONTEXT_SYMBOL__ACTION_TYPE:
				setActionType((ActionType)newValue);
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
			case RulePackage.ACTION_CONTEXT_SYMBOL__ACTION_TYPE:
				setActionType(ACTION_TYPE_EDEFAULT);
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
			case RulePackage.ACTION_CONTEXT_SYMBOL__ACTION_TYPE:
				return actionType != ACTION_TYPE_EDEFAULT;
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
		result.append(" (actionType: ");
		result.append(actionType);
		result.append(')');
		return result.toString();
	}

} //ActionContextSymbolImpl
