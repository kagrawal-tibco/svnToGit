/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.scope.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.scope.CompilableScope;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.model.scope.ScopePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Compilable Scope</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.scope.impl.CompilableScopeImpl#getGlobalVariables <em>Global Variables</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.scope.impl.CompilableScopeImpl#getConditionScope <em>Condition Scope</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.scope.impl.CompilableScopeImpl#getActionScope <em>Action Scope</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CompilableScopeImpl extends EObjectImpl implements CompilableScope {
	/**
	 * The cached value of the '{@link #getGlobalVariables() <em>Global Variables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGlobalVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<GlobalVariableDef> globalVariables;

	/**
	 * The cached value of the '{@link #getConditionScope() <em>Condition Scope</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConditionScope()
	 * @generated
	 * @ordered
	 */
	protected ScopeBlock conditionScope;

	/**
	 * The cached value of the '{@link #getActionScope() <em>Action Scope</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActionScope()
	 * @generated
	 * @ordered
	 */
	protected ScopeBlock actionScope;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompilableScopeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScopePackage.Literals.COMPILABLE_SCOPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GlobalVariableDef> getGlobalVariables() {
		if (globalVariables == null) {
			globalVariables = new EObjectContainmentEList<GlobalVariableDef>(GlobalVariableDef.class, this, ScopePackage.COMPILABLE_SCOPE__GLOBAL_VARIABLES);
		}
		return globalVariables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScopeBlock getConditionScope() {
		return conditionScope;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConditionScope(ScopeBlock newConditionScope, NotificationChain msgs) {
		ScopeBlock oldConditionScope = conditionScope;
		conditionScope = newConditionScope;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ScopePackage.COMPILABLE_SCOPE__CONDITION_SCOPE, oldConditionScope, newConditionScope);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConditionScope(ScopeBlock newConditionScope) {
		if (newConditionScope != conditionScope) {
			NotificationChain msgs = null;
			if (conditionScope != null)
				msgs = ((InternalEObject)conditionScope).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ScopePackage.COMPILABLE_SCOPE__CONDITION_SCOPE, null, msgs);
			if (newConditionScope != null)
				msgs = ((InternalEObject)newConditionScope).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ScopePackage.COMPILABLE_SCOPE__CONDITION_SCOPE, null, msgs);
			msgs = basicSetConditionScope(newConditionScope, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScopePackage.COMPILABLE_SCOPE__CONDITION_SCOPE, newConditionScope, newConditionScope));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScopeBlock getActionScope() {
		return actionScope;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetActionScope(ScopeBlock newActionScope, NotificationChain msgs) {
		ScopeBlock oldActionScope = actionScope;
		actionScope = newActionScope;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ScopePackage.COMPILABLE_SCOPE__ACTION_SCOPE, oldActionScope, newActionScope);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActionScope(ScopeBlock newActionScope) {
		if (newActionScope != actionScope) {
			NotificationChain msgs = null;
			if (actionScope != null)
				msgs = ((InternalEObject)actionScope).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ScopePackage.COMPILABLE_SCOPE__ACTION_SCOPE, null, msgs);
			if (newActionScope != null)
				msgs = ((InternalEObject)newActionScope).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ScopePackage.COMPILABLE_SCOPE__ACTION_SCOPE, null, msgs);
			msgs = basicSetActionScope(newActionScope, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScopePackage.COMPILABLE_SCOPE__ACTION_SCOPE, newActionScope, newActionScope));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ScopePackage.COMPILABLE_SCOPE__GLOBAL_VARIABLES:
				return ((InternalEList<?>)getGlobalVariables()).basicRemove(otherEnd, msgs);
			case ScopePackage.COMPILABLE_SCOPE__CONDITION_SCOPE:
				return basicSetConditionScope(null, msgs);
			case ScopePackage.COMPILABLE_SCOPE__ACTION_SCOPE:
				return basicSetActionScope(null, msgs);
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
			case ScopePackage.COMPILABLE_SCOPE__GLOBAL_VARIABLES:
				return getGlobalVariables();
			case ScopePackage.COMPILABLE_SCOPE__CONDITION_SCOPE:
				return getConditionScope();
			case ScopePackage.COMPILABLE_SCOPE__ACTION_SCOPE:
				return getActionScope();
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
			case ScopePackage.COMPILABLE_SCOPE__GLOBAL_VARIABLES:
				getGlobalVariables().clear();
				getGlobalVariables().addAll((Collection<? extends GlobalVariableDef>)newValue);
				return;
			case ScopePackage.COMPILABLE_SCOPE__CONDITION_SCOPE:
				setConditionScope((ScopeBlock)newValue);
				return;
			case ScopePackage.COMPILABLE_SCOPE__ACTION_SCOPE:
				setActionScope((ScopeBlock)newValue);
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
			case ScopePackage.COMPILABLE_SCOPE__GLOBAL_VARIABLES:
				getGlobalVariables().clear();
				return;
			case ScopePackage.COMPILABLE_SCOPE__CONDITION_SCOPE:
				setConditionScope((ScopeBlock)null);
				return;
			case ScopePackage.COMPILABLE_SCOPE__ACTION_SCOPE:
				setActionScope((ScopeBlock)null);
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
			case ScopePackage.COMPILABLE_SCOPE__GLOBAL_VARIABLES:
				return globalVariables != null && !globalVariables.isEmpty();
			case ScopePackage.COMPILABLE_SCOPE__CONDITION_SCOPE:
				return conditionScope != null;
			case ScopePackage.COMPILABLE_SCOPE__ACTION_SCOPE:
				return actionScope != null;
		}
		return super.eIsSet(featureID);
	}

} //CompilableScopeImpl
