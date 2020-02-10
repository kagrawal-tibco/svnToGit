/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.dtmodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Table Rule Set</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleSetImpl#getRule <em>Rule</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleSetImpl#getColumns <em>Columns</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TableRuleSetImpl extends MetadatableImpl implements TableRuleSet {
	/**
	 * The cached value of the '{@link #getRule() <em>Rule</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRule()
	 * @generated
	 * @ordered
	 */
	protected EList<TableRule> rule;

	/**
	 * The cached value of the '{@link #getColumns() <em>Columns</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumns()
	 * @generated
	 * @ordered
	 */
	protected Columns columns;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TableRuleSetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DtmodelPackage.Literals.TABLE_RULE_SET;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TableRule> getRule() {
		if (rule == null) {
			rule = new EObjectContainmentEList<TableRule>(TableRule.class, this, DtmodelPackage.TABLE_RULE_SET__RULE);
		}
		return rule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Columns getColumns() {
		return columns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetColumns(Columns newColumns, NotificationChain msgs) {
		Columns oldColumns = columns;
		columns = newColumns;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DtmodelPackage.TABLE_RULE_SET__COLUMNS, oldColumns, newColumns);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setColumns(Columns newColumns) {
		if (newColumns != columns) {
			NotificationChain msgs = null;
			if (columns != null)
				msgs = ((InternalEObject)columns).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DtmodelPackage.TABLE_RULE_SET__COLUMNS, null, msgs);
			if (newColumns != null)
				msgs = ((InternalEObject)newColumns).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DtmodelPackage.TABLE_RULE_SET__COLUMNS, null, msgs);
			msgs = basicSetColumns(newColumns, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.TABLE_RULE_SET__COLUMNS, newColumns, newColumns));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DtmodelPackage.TABLE_RULE_SET__RULE:
				return ((InternalEList<?>)getRule()).basicRemove(otherEnd, msgs);
			case DtmodelPackage.TABLE_RULE_SET__COLUMNS:
				return basicSetColumns(null, msgs);
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
			case DtmodelPackage.TABLE_RULE_SET__RULE:
				return getRule();
			case DtmodelPackage.TABLE_RULE_SET__COLUMNS:
				return getColumns();
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
			case DtmodelPackage.TABLE_RULE_SET__RULE:
				getRule().clear();
				getRule().addAll((Collection<? extends TableRule>)newValue);
				return;
			case DtmodelPackage.TABLE_RULE_SET__COLUMNS:
				setColumns((Columns)newValue);
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
			case DtmodelPackage.TABLE_RULE_SET__RULE:
				getRule().clear();
				return;
			case DtmodelPackage.TABLE_RULE_SET__COLUMNS:
				setColumns((Columns)null);
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
			case DtmodelPackage.TABLE_RULE_SET__RULE:
				return rule != null && !rule.isEmpty();
			case DtmodelPackage.TABLE_RULE_SET__COLUMNS:
				return columns != null;
		}
		return super.eIsSet(featureID);
	}

} //TableRuleSetImpl
