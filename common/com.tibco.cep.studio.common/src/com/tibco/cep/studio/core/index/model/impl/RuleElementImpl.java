/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.IStructuredElementVisitor;
import com.tibco.cep.studio.core.index.model.IndexPackage;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.scope.RootScopeBlock;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.RuleElementImpl#getRule <em>Rule</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.RuleElementImpl#isVirtual <em>Virtual</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.RuleElementImpl#getScope <em>Scope</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.RuleElementImpl#getGlobalVariables <em>Global Variables</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.RuleElementImpl#getIndexName <em>Index Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RuleElementImpl extends TypeElementImpl implements RuleElement {
	/**
	 * The cached value of the '{@link #getRule() <em>Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRule()
	 * @generated
	 * @ordered
	 */
	protected Compilable rule;

	/**
	 * The default value of the '{@link #isVirtual() <em>Virtual</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVirtual()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VIRTUAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isVirtual() <em>Virtual</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVirtual()
	 * @generated
	 * @ordered
	 */
	protected boolean virtual = VIRTUAL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getScope() <em>Scope</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScope()
	 * @generated
	 * @ordered
	 */
	protected RootScopeBlock scope;

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
	 * The default value of the '{@link #getIndexName() <em>Index Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndexName()
	 * @generated
	 * @ordered
	 */
	protected static final String INDEX_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIndexName() <em>Index Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndexName()
	 * @generated
	 * @ordered
	 */
	protected String indexName = INDEX_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IndexPackage.Literals.RULE_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Compilable getRule() {
		return rule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRule(Compilable newRule, NotificationChain msgs) {
		Compilable oldRule = rule;
		rule = newRule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IndexPackage.RULE_ELEMENT__RULE, oldRule, newRule);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRule(Compilable newRule) {
		if (newRule != rule) {
			NotificationChain msgs = null;
			if (rule != null)
				msgs = ((InternalEObject)rule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IndexPackage.RULE_ELEMENT__RULE, null, msgs);
			if (newRule != null)
				msgs = ((InternalEObject)newRule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IndexPackage.RULE_ELEMENT__RULE, null, msgs);
			msgs = basicSetRule(newRule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.RULE_ELEMENT__RULE, newRule, newRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isVirtual() {
		return virtual;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVirtual(boolean newVirtual) {
		boolean oldVirtual = virtual;
		virtual = newVirtual;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.RULE_ELEMENT__VIRTUAL, oldVirtual, virtual));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RootScopeBlock getScope() {
		return scope;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetScope(RootScopeBlock newScope, NotificationChain msgs) {
		RootScopeBlock oldScope = scope;
		scope = newScope;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IndexPackage.RULE_ELEMENT__SCOPE, oldScope, newScope);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScope(RootScopeBlock newScope) {
		if (newScope != scope) {
			NotificationChain msgs = null;
			if (scope != null)
				msgs = ((InternalEObject)scope).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IndexPackage.RULE_ELEMENT__SCOPE, null, msgs);
			if (newScope != null)
				msgs = ((InternalEObject)newScope).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IndexPackage.RULE_ELEMENT__SCOPE, null, msgs);
			msgs = basicSetScope(newScope, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.RULE_ELEMENT__SCOPE, newScope, newScope));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GlobalVariableDef> getGlobalVariables() {
		if (globalVariables == null) {
			globalVariables = new EObjectContainmentEList<GlobalVariableDef>(GlobalVariableDef.class, this, IndexPackage.RULE_ELEMENT__GLOBAL_VARIABLES);
		}
		return globalVariables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIndexName() {
		return indexName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIndexName(String newIndexName) {
		String oldIndexName = indexName;
		indexName = newIndexName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.RULE_ELEMENT__INDEX_NAME, oldIndexName, indexName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void doVisitChildren(IStructuredElementVisitor visitor) {
		EList<GlobalVariableDef> variables = getGlobalVariables();
    	for (GlobalVariableDef globalVariableDef : variables) {
    		visitor.visit(globalVariableDef);
		}
    	RootScopeBlock scope = getScope();
    	if (scope != null) {
    		scope.accept(visitor);
    	}
	}
	

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void accept(IStructuredElementVisitor visitor) {
        visitor.preVisit(this);
        if (visitor.visit(this)) {
            doVisitChildren(visitor);
        }
        visitor.postVisit(this);
	}

    

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IndexPackage.RULE_ELEMENT__RULE:
				return basicSetRule(null, msgs);
			case IndexPackage.RULE_ELEMENT__SCOPE:
				return basicSetScope(null, msgs);
			case IndexPackage.RULE_ELEMENT__GLOBAL_VARIABLES:
				return ((InternalEList<?>)getGlobalVariables()).basicRemove(otherEnd, msgs);
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
			case IndexPackage.RULE_ELEMENT__RULE:
				return getRule();
			case IndexPackage.RULE_ELEMENT__VIRTUAL:
				return isVirtual();
			case IndexPackage.RULE_ELEMENT__SCOPE:
				return getScope();
			case IndexPackage.RULE_ELEMENT__GLOBAL_VARIABLES:
				return getGlobalVariables();
			case IndexPackage.RULE_ELEMENT__INDEX_NAME:
				return getIndexName();
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
			case IndexPackage.RULE_ELEMENT__RULE:
				setRule((Compilable)newValue);
				return;
			case IndexPackage.RULE_ELEMENT__VIRTUAL:
				setVirtual((Boolean)newValue);
				return;
			case IndexPackage.RULE_ELEMENT__SCOPE:
				setScope((RootScopeBlock)newValue);
				return;
			case IndexPackage.RULE_ELEMENT__GLOBAL_VARIABLES:
				getGlobalVariables().clear();
				getGlobalVariables().addAll((Collection<? extends GlobalVariableDef>)newValue);
				return;
			case IndexPackage.RULE_ELEMENT__INDEX_NAME:
				setIndexName((String)newValue);
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
			case IndexPackage.RULE_ELEMENT__RULE:
				setRule((Compilable)null);
				return;
			case IndexPackage.RULE_ELEMENT__VIRTUAL:
				setVirtual(VIRTUAL_EDEFAULT);
				return;
			case IndexPackage.RULE_ELEMENT__SCOPE:
				setScope((RootScopeBlock)null);
				return;
			case IndexPackage.RULE_ELEMENT__GLOBAL_VARIABLES:
				getGlobalVariables().clear();
				return;
			case IndexPackage.RULE_ELEMENT__INDEX_NAME:
				setIndexName(INDEX_NAME_EDEFAULT);
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
			case IndexPackage.RULE_ELEMENT__RULE:
				return rule != null;
			case IndexPackage.RULE_ELEMENT__VIRTUAL:
				return virtual != VIRTUAL_EDEFAULT;
			case IndexPackage.RULE_ELEMENT__SCOPE:
				return scope != null;
			case IndexPackage.RULE_ELEMENT__GLOBAL_VARIABLES:
				return globalVariables != null && !globalVariables.isEmpty();
			case IndexPackage.RULE_ELEMENT__INDEX_NAME:
				return INDEX_NAME_EDEFAULT == null ? indexName != null : !INDEX_NAME_EDEFAULT.equals(indexName);
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
		result.append(" (virtual: ");
		result.append(virtual);
		result.append(", indexName: ");
		result.append(indexName);
		result.append(')');
		return result.toString();
	}


} //RuleElementImpl
