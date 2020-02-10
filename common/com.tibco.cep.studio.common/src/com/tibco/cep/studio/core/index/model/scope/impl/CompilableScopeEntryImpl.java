/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.scope.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.studio.core.index.model.scope.CompilableScope;
import com.tibco.cep.studio.core.index.model.scope.CompilableScopeEntry;
import com.tibco.cep.studio.core.index.model.scope.ScopePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Compilable Scope Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.scope.impl.CompilableScopeEntryImpl#getRuleName <em>Rule Name</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.scope.impl.CompilableScopeEntryImpl#getScope <em>Scope</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CompilableScopeEntryImpl extends EObjectImpl implements CompilableScopeEntry {
	/**
	 * The default value of the '{@link #getRuleName() <em>Rule Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleName()
	 * @generated
	 * @ordered
	 */
	protected static final String RULE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRuleName() <em>Rule Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleName()
	 * @generated
	 * @ordered
	 */
	protected String ruleName = RULE_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getScope() <em>Scope</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScope()
	 * @generated
	 * @ordered
	 */
	protected CompilableScope scope;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompilableScopeEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScopePackage.Literals.COMPILABLE_SCOPE_ENTRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRuleName() {
		return ruleName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRuleName(String newRuleName) {
		String oldRuleName = ruleName;
		ruleName = newRuleName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScopePackage.COMPILABLE_SCOPE_ENTRY__RULE_NAME, oldRuleName, ruleName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompilableScope getScope() {
		return scope;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetScope(CompilableScope newScope, NotificationChain msgs) {
		CompilableScope oldScope = scope;
		scope = newScope;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ScopePackage.COMPILABLE_SCOPE_ENTRY__SCOPE, oldScope, newScope);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScope(CompilableScope newScope) {
		if (newScope != scope) {
			NotificationChain msgs = null;
			if (scope != null)
				msgs = ((InternalEObject)scope).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ScopePackage.COMPILABLE_SCOPE_ENTRY__SCOPE, null, msgs);
			if (newScope != null)
				msgs = ((InternalEObject)newScope).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ScopePackage.COMPILABLE_SCOPE_ENTRY__SCOPE, null, msgs);
			msgs = basicSetScope(newScope, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScopePackage.COMPILABLE_SCOPE_ENTRY__SCOPE, newScope, newScope));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ScopePackage.COMPILABLE_SCOPE_ENTRY__SCOPE:
				return basicSetScope(null, msgs);
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
			case ScopePackage.COMPILABLE_SCOPE_ENTRY__RULE_NAME:
				return getRuleName();
			case ScopePackage.COMPILABLE_SCOPE_ENTRY__SCOPE:
				return getScope();
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
			case ScopePackage.COMPILABLE_SCOPE_ENTRY__RULE_NAME:
				setRuleName((String)newValue);
				return;
			case ScopePackage.COMPILABLE_SCOPE_ENTRY__SCOPE:
				setScope((CompilableScope)newValue);
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
			case ScopePackage.COMPILABLE_SCOPE_ENTRY__RULE_NAME:
				setRuleName(RULE_NAME_EDEFAULT);
				return;
			case ScopePackage.COMPILABLE_SCOPE_ENTRY__SCOPE:
				setScope((CompilableScope)null);
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
			case ScopePackage.COMPILABLE_SCOPE_ENTRY__RULE_NAME:
				return RULE_NAME_EDEFAULT == null ? ruleName != null : !RULE_NAME_EDEFAULT.equals(ruleName);
			case ScopePackage.COMPILABLE_SCOPE_ENTRY__SCOPE:
				return scope != null;
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
		result.append(" (ruleName: ");
		result.append(ruleName);
		result.append(')');
		return result.toString();
	}

} //CompilableScopeEntryImpl
