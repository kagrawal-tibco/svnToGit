/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node.action.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import com.tibco.cep.decision.tree.common.model.FlowElement;
import com.tibco.cep.decision.tree.common.model.node.NodeElement;
import com.tibco.cep.decision.tree.common.model.node.action.Action;
import com.tibco.cep.decision.tree.common.model.node.action.ActionPackage;
import com.tibco.cep.decision.tree.common.model.node.action.Assignment;
import com.tibco.cep.decision.tree.common.model.node.action.CallDecisionTableAction;
import com.tibco.cep.decision.tree.common.model.node.action.CallDecisionTreeAction;
import com.tibco.cep.decision.tree.common.model.node.action.CallRF;

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
 * @see com.tibco.cep.decision.tree.common.model.node.action.ActionPackage
 * @generated
 */
public class ActionSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ActionPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActionSwitch() {
		if (modelPackage == null) {
			modelPackage = ActionPackage.eINSTANCE;
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
			case ActionPackage.ACTION: {
				Action action = (Action)theEObject;
				T result = caseAction(action);
				if (result == null) result = caseNodeElement(action);
				if (result == null) result = caseFlowElement(action);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ActionPackage.ASSIGNMENT: {
				Assignment assignment = (Assignment)theEObject;
				T result = caseAssignment(assignment);
				if (result == null) result = caseAction(assignment);
				if (result == null) result = caseNodeElement(assignment);
				if (result == null) result = caseFlowElement(assignment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ActionPackage.CALL_RF: {
				CallRF callRF = (CallRF)theEObject;
				T result = caseCallRF(callRF);
				if (result == null) result = caseAction(callRF);
				if (result == null) result = caseNodeElement(callRF);
				if (result == null) result = caseFlowElement(callRF);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ActionPackage.CALL_DECISION_TREE_ACTION: {
				CallDecisionTreeAction callDecisionTreeAction = (CallDecisionTreeAction)theEObject;
				T result = caseCallDecisionTreeAction(callDecisionTreeAction);
				if (result == null) result = caseAction(callDecisionTreeAction);
				if (result == null) result = caseNodeElement(callDecisionTreeAction);
				if (result == null) result = caseFlowElement(callDecisionTreeAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ActionPackage.CALL_DECISION_TABLE_ACTION: {
				CallDecisionTableAction callDecisionTableAction = (CallDecisionTableAction)theEObject;
				T result = caseCallDecisionTableAction(callDecisionTableAction);
				if (result == null) result = caseAction(callDecisionTableAction);
				if (result == null) result = caseNodeElement(callDecisionTableAction);
				if (result == null) result = caseFlowElement(callDecisionTableAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAction(Action object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Assignment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Assignment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAssignment(Assignment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Call RF</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Call RF</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCallRF(CallRF object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Call Decision Tree Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Call Decision Tree Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCallDecisionTreeAction(CallDecisionTreeAction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Call Decision Table Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Call Decision Table Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCallDecisionTableAction(CallDecisionTableAction object) {
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

} //ActionSwitch
