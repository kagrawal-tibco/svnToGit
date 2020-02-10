/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.designtime.core.model.rule.ActionContext;
import com.tibco.cep.designtime.core.model.rule.RulePackage;
import com.tibco.cep.designtime.core.model.rule.Symbols;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Action Context</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.ActionContextImpl#getActionContextSymbols <em>Action Context Symbols</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ActionContextImpl extends EObjectImpl implements ActionContext {
	/**
	 * The cached value of the '{@link #getActionContextSymbols() <em>Action Context Symbols</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActionContextSymbols()
	 * @generated
	 * @ordered
	 */
	protected Symbols actionContextSymbols;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ActionContextImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulePackage.Literals.ACTION_CONTEXT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Symbols getActionContextSymbols() {
		return actionContextSymbols;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetActionContextSymbols(Symbols newActionContextSymbols, NotificationChain msgs) {
		Symbols oldActionContextSymbols = actionContextSymbols;
		actionContextSymbols = newActionContextSymbols;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RulePackage.ACTION_CONTEXT__ACTION_CONTEXT_SYMBOLS, oldActionContextSymbols, newActionContextSymbols);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActionContextSymbols(Symbols newActionContextSymbols) {
		if (newActionContextSymbols != actionContextSymbols) {
			NotificationChain msgs = null;
			if (actionContextSymbols != null)
				msgs = ((InternalEObject)actionContextSymbols).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RulePackage.ACTION_CONTEXT__ACTION_CONTEXT_SYMBOLS, null, msgs);
			if (newActionContextSymbols != null)
				msgs = ((InternalEObject)newActionContextSymbols).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RulePackage.ACTION_CONTEXT__ACTION_CONTEXT_SYMBOLS, null, msgs);
			msgs = basicSetActionContextSymbols(newActionContextSymbols, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.ACTION_CONTEXT__ACTION_CONTEXT_SYMBOLS, newActionContextSymbols, newActionContextSymbols));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RulePackage.ACTION_CONTEXT__ACTION_CONTEXT_SYMBOLS:
				return basicSetActionContextSymbols(null, msgs);
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
			case RulePackage.ACTION_CONTEXT__ACTION_CONTEXT_SYMBOLS:
				return getActionContextSymbols();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RulePackage.ACTION_CONTEXT__ACTION_CONTEXT_SYMBOLS:
				setActionContextSymbols((Symbols)newValue);
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
			case RulePackage.ACTION_CONTEXT__ACTION_CONTEXT_SYMBOLS:
				setActionContextSymbols((Symbols)null);
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
			case RulePackage.ACTION_CONTEXT__ACTION_CONTEXT_SYMBOLS:
				return actionContextSymbols != null;
		}
		return super.eIsSet(featureID);
	}

} //ActionContextImpl
