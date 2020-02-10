/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.states.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.states.InternalStateTransition;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StatesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Internal State Transition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.InternalStateTransitionImpl#getRule <em>Rule</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.InternalStateTransitionImpl#getOwnerState <em>Owner State</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InternalStateTransitionImpl extends StateTransitionImpl implements InternalStateTransition {
	/**
	 * The cached value of the '{@link #getRule() <em>Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRule()
	 * @generated
	 * @ordered
	 */
	protected Rule rule;

	/**
	 * The cached value of the '{@link #getOwnerState() <em>Owner State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnerState()
	 * @generated
	 * @ordered
	 */
	protected State ownerState;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InternalStateTransitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StatesPackage.Literals.INTERNAL_STATE_TRANSITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule getRule() {
		if (rule != null && rule.eIsProxy()) {
			InternalEObject oldRule = (InternalEObject)rule;
			rule = (Rule)eResolveProxy(oldRule);
			if (rule != oldRule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StatesPackage.INTERNAL_STATE_TRANSITION__RULE, oldRule, rule));
			}
		}
		return rule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule basicGetRule() {
		return rule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRule(Rule newRule) {
		Rule oldRule = rule;
		rule = newRule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.INTERNAL_STATE_TRANSITION__RULE, oldRule, rule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State getOwnerState() {
		if (ownerState != null && ownerState.eIsProxy()) {
			InternalEObject oldOwnerState = (InternalEObject)ownerState;
			ownerState = (State)eResolveProxy(oldOwnerState);
			if (ownerState != oldOwnerState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StatesPackage.INTERNAL_STATE_TRANSITION__OWNER_STATE, oldOwnerState, ownerState));
			}
		}
		return ownerState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State basicGetOwnerState() {
		return ownerState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwnerState(State newOwnerState) {
		State oldOwnerState = ownerState;
		ownerState = newOwnerState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.INTERNAL_STATE_TRANSITION__OWNER_STATE, oldOwnerState, ownerState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StatesPackage.INTERNAL_STATE_TRANSITION__RULE:
				if (resolve) return getRule();
				return basicGetRule();
			case StatesPackage.INTERNAL_STATE_TRANSITION__OWNER_STATE:
				if (resolve) return getOwnerState();
				return basicGetOwnerState();
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
			case StatesPackage.INTERNAL_STATE_TRANSITION__RULE:
				setRule((Rule)newValue);
				return;
			case StatesPackage.INTERNAL_STATE_TRANSITION__OWNER_STATE:
				setOwnerState((State)newValue);
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
			case StatesPackage.INTERNAL_STATE_TRANSITION__RULE:
				setRule((Rule)null);
				return;
			case StatesPackage.INTERNAL_STATE_TRANSITION__OWNER_STATE:
				setOwnerState((State)null);
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
			case StatesPackage.INTERNAL_STATE_TRANSITION__RULE:
				return rule != null;
			case StatesPackage.INTERNAL_STATE_TRANSITION__OWNER_STATE:
				return ownerState != null;
		}
		return super.eIsSet(featureID);
	}

} //InternalStateTransitionImpl
