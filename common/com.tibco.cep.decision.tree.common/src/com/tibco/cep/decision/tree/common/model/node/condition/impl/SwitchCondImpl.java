/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node.condition.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import com.tibco.cep.decision.tree.common.model.node.condition.Case;
import com.tibco.cep.decision.tree.common.model.node.condition.CaseGroup;
import com.tibco.cep.decision.tree.common.model.node.condition.ConditionPackage;
import com.tibco.cep.decision.tree.common.model.node.condition.SwitchCond;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Switch Cond</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.condition.impl.SwitchCondImpl#getCases <em>Cases</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.condition.impl.SwitchCondImpl#getCasegroups <em>Casegroups</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SwitchCondImpl extends CondImpl implements SwitchCond {
	/**
	 * The cached value of the '{@link #getCases() <em>Cases</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCases()
	 * @generated
	 * @ordered
	 */
	protected EList<Case> cases;
	/**
	 * The cached value of the '{@link #getCasegroups() <em>Casegroups</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCasegroups()
	 * @generated
	 * @ordered
	 */
	protected EList<CaseGroup> casegroups;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SwitchCondImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConditionPackage.Literals.SWITCH_COND;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Case> getCases() {
		if (cases == null) {
			cases = new EObjectResolvingEList<Case>(Case.class, this, ConditionPackage.SWITCH_COND__CASES);
		}
		return cases;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CaseGroup> getCasegroups() {
		if (casegroups == null) {
			casegroups = new EObjectResolvingEList<CaseGroup>(CaseGroup.class, this, ConditionPackage.SWITCH_COND__CASEGROUPS);
		}
		return casegroups;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ConditionPackage.SWITCH_COND__CASES:
				return getCases();
			case ConditionPackage.SWITCH_COND__CASEGROUPS:
				return getCasegroups();
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
			case ConditionPackage.SWITCH_COND__CASES:
				getCases().clear();
				getCases().addAll((Collection<? extends Case>)newValue);
				return;
			case ConditionPackage.SWITCH_COND__CASEGROUPS:
				getCasegroups().clear();
				getCasegroups().addAll((Collection<? extends CaseGroup>)newValue);
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
			case ConditionPackage.SWITCH_COND__CASES:
				getCases().clear();
				return;
			case ConditionPackage.SWITCH_COND__CASEGROUPS:
				getCasegroups().clear();
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
			case ConditionPackage.SWITCH_COND__CASES:
				return cases != null && !cases.isEmpty();
			case ConditionPackage.SWITCH_COND__CASEGROUPS:
				return casegroups != null && !casegroups.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //SwitchCondImpl
