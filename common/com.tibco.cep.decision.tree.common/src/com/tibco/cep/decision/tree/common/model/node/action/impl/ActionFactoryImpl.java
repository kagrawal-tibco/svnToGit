/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node.action.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.decision.tree.common.model.node.action.Action;
import com.tibco.cep.decision.tree.common.model.node.action.ActionFactory;
import com.tibco.cep.decision.tree.common.model.node.action.ActionPackage;
import com.tibco.cep.decision.tree.common.model.node.action.Assignment;
import com.tibco.cep.decision.tree.common.model.node.action.CallDecisionTableAction;
import com.tibco.cep.decision.tree.common.model.node.action.CallDecisionTreeAction;
import com.tibco.cep.decision.tree.common.model.node.action.CallRF;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ActionFactoryImpl extends EFactoryImpl implements ActionFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ActionFactory init() {
		try {
			ActionFactory theActionFactory = (ActionFactory)EPackage.Registry.INSTANCE.getEFactory("http:///com/tibco/cep/decision/tree/model/node/action"); 
			if (theActionFactory != null) {
				return theActionFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ActionFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActionFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ActionPackage.ACTION: return createAction();
			case ActionPackage.ASSIGNMENT: return createAssignment();
			case ActionPackage.CALL_RF: return createCallRF();
			case ActionPackage.CALL_DECISION_TREE_ACTION: return createCallDecisionTreeAction();
			case ActionPackage.CALL_DECISION_TABLE_ACTION: return createCallDecisionTableAction();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Action createAction() {
		ActionImpl action = new ActionImpl();
		return action;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Assignment createAssignment() {
		AssignmentImpl assignment = new AssignmentImpl();
		return assignment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CallRF createCallRF() {
		CallRFImpl callRF = new CallRFImpl();
		return callRF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CallDecisionTreeAction createCallDecisionTreeAction() {
		CallDecisionTreeActionImpl callDecisionTreeAction = new CallDecisionTreeActionImpl();
		return callDecisionTreeAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CallDecisionTableAction createCallDecisionTableAction() {
		CallDecisionTableActionImpl callDecisionTableAction = new CallDecisionTableActionImpl();
		return callDecisionTableAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActionPackage getActionPackage() {
		return (ActionPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ActionPackage getPackage() {
		return ActionPackage.eINSTANCE;
	}

} //ActionFactoryImpl
