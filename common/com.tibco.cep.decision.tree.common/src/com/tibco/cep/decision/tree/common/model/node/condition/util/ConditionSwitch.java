/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node.condition.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import com.tibco.cep.decision.tree.common.model.FlowElement;
import com.tibco.cep.decision.tree.common.model.node.NodeElement;
import com.tibco.cep.decision.tree.common.model.node.condition.BoolCond;
import com.tibco.cep.decision.tree.common.model.node.condition.Case;
import com.tibco.cep.decision.tree.common.model.node.condition.CaseGroup;
import com.tibco.cep.decision.tree.common.model.node.condition.Cond;
import com.tibco.cep.decision.tree.common.model.node.condition.ConditionPackage;
import com.tibco.cep.decision.tree.common.model.node.condition.SwitchCond;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.decision.tree.common.model.node.condition.ConditionPackage
 * @generated
 */
public class ConditionSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ConditionPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConditionSwitch() {
		if (modelPackage == null) {
			modelPackage = ConditionPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case ConditionPackage.COND: {
				Cond cond = (Cond)theEObject;
				T result = caseCond(cond);
				if (result == null) result = caseNodeElement(cond);
				if (result == null) result = caseFlowElement(cond);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ConditionPackage.BOOL_COND: {
				BoolCond boolCond = (BoolCond)theEObject;
				T result = caseBoolCond(boolCond);
				if (result == null) result = caseCond(boolCond);
				if (result == null) result = caseNodeElement(boolCond);
				if (result == null) result = caseFlowElement(boolCond);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ConditionPackage.SWITCH_COND: {
				SwitchCond switchCond = (SwitchCond)theEObject;
				T result = caseSwitchCond(switchCond);
				if (result == null) result = caseCond(switchCond);
				if (result == null) result = caseNodeElement(switchCond);
				if (result == null) result = caseFlowElement(switchCond);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ConditionPackage.CASE: {
				Case case_ = (Case)theEObject;
				T result = caseCase(case_);
				if (result == null) result = caseFlowElement(case_);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ConditionPackage.CASE_GROUP: {
				CaseGroup caseGroup = (CaseGroup)theEObject;
				T result = caseCaseGroup(caseGroup);
				if (result == null) result = caseCase(caseGroup);
				if (result == null) result = caseFlowElement(caseGroup);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Cond</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cond</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCond(Cond object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bool Cond</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bool Cond</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBoolCond(BoolCond object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Switch Cond</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Switch Cond</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSwitchCond(SwitchCond object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Case</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Case</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCase(Case object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Case Group</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Case Group</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCaseGroup(CaseGroup object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Flow Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Flow Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFlowElement(FlowElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNodeElement(NodeElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //ConditionSwitch
