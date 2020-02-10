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

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Case Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.condition.impl.CaseGroupImpl#getCases <em>Cases</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CaseGroupImpl extends CaseImpl implements CaseGroup {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CaseGroupImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConditionPackage.Literals.CASE_GROUP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Case> getCases() {
		if (cases == null) {
			cases = new EObjectResolvingEList<Case>(Case.class, this, ConditionPackage.CASE_GROUP__CASES);
		}
		return cases;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ConditionPackage.CASE_GROUP__CASES:
				return getCases();
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
			case ConditionPackage.CASE_GROUP__CASES:
				getCases().clear();
				getCases().addAll((Collection<? extends Case>)newValue);
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
			case ConditionPackage.CASE_GROUP__CASES:
				getCases().clear();
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
			case ConditionPackage.CASE_GROUP__CASES:
				return cases != null && !cases.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //CaseGroupImpl
