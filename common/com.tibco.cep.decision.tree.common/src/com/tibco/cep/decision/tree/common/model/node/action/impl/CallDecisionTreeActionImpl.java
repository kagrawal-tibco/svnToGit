/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node.action.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.decision.tree.common.model.DecisionTree;
import com.tibco.cep.decision.tree.common.model.node.action.ActionPackage;
import com.tibco.cep.decision.tree.common.model.node.action.CallDecisionTreeAction;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Call Decision Tree Action</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.action.impl.CallDecisionTreeActionImpl#getCallTree <em>Call Tree</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CallDecisionTreeActionImpl extends ActionImpl implements CallDecisionTreeAction {
	/**
	 * The cached value of the '{@link #getCallTree() <em>Call Tree</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCallTree()
	 * @generated
	 * @ordered
	 */
	protected DecisionTree callTree;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CallDecisionTreeActionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ActionPackage.Literals.CALL_DECISION_TREE_ACTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DecisionTree getCallTree() {
		if (callTree != null && callTree.eIsProxy()) {
			InternalEObject oldCallTree = (InternalEObject)callTree;
			callTree = (DecisionTree)eResolveProxy(oldCallTree);
			if (callTree != oldCallTree) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ActionPackage.CALL_DECISION_TREE_ACTION__CALL_TREE, oldCallTree, callTree));
			}
		}
		return callTree;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DecisionTree basicGetCallTree() {
		return callTree;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCallTree(DecisionTree newCallTree) {
		DecisionTree oldCallTree = callTree;
		callTree = newCallTree;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ActionPackage.CALL_DECISION_TREE_ACTION__CALL_TREE, oldCallTree, callTree));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ActionPackage.CALL_DECISION_TREE_ACTION__CALL_TREE:
				if (resolve) return getCallTree();
				return basicGetCallTree();
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
			case ActionPackage.CALL_DECISION_TREE_ACTION__CALL_TREE:
				setCallTree((DecisionTree)newValue);
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
			case ActionPackage.CALL_DECISION_TREE_ACTION__CALL_TREE:
				setCallTree((DecisionTree)null);
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
			case ActionPackage.CALL_DECISION_TREE_ACTION__CALL_TREE:
				return callTree != null;
		}
		return super.eIsSet(featureID);
	}

} //CallDecisionTreeActionImpl
