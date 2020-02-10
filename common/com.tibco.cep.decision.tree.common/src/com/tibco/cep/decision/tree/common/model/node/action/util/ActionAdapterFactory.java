/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node.action.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

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
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.decision.tree.common.model.node.action.ActionPackage
 * @generated
 */
public class ActionAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ActionPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActionAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ActionPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ActionSwitch<Adapter> modelSwitch =
		new ActionSwitch<Adapter>() {
			@Override
			public Adapter caseAction(Action object) {
				return createActionAdapter();
			}
			@Override
			public Adapter caseAssignment(Assignment object) {
				return createAssignmentAdapter();
			}
			@Override
			public Adapter caseCallRF(CallRF object) {
				return createCallRFAdapter();
			}
			@Override
			public Adapter caseCallDecisionTreeAction(CallDecisionTreeAction object) {
				return createCallDecisionTreeActionAdapter();
			}
			@Override
			public Adapter caseCallDecisionTableAction(CallDecisionTableAction object) {
				return createCallDecisionTableActionAdapter();
			}
			@Override
			public Adapter caseFlowElement(FlowElement object) {
				return createFlowElementAdapter();
			}
			@Override
			public Adapter caseNodeElement(NodeElement object) {
				return createNodeElementAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decision.tree.common.model.node.action.Action <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decision.tree.common.model.node.action.Action
	 * @generated
	 */
	public Adapter createActionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decision.tree.common.model.node.action.Assignment <em>Assignment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decision.tree.common.model.node.action.Assignment
	 * @generated
	 */
	public Adapter createAssignmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decision.tree.common.model.node.action.CallRF <em>Call RF</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decision.tree.common.model.node.action.CallRF
	 * @generated
	 */
	public Adapter createCallRFAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decision.tree.common.model.node.action.CallDecisionTreeAction <em>Call Decision Tree Action</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decision.tree.common.model.node.action.CallDecisionTreeAction
	 * @generated
	 */
	public Adapter createCallDecisionTreeActionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decision.tree.common.model.node.action.CallDecisionTableAction <em>Call Decision Table Action</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decision.tree.common.model.node.action.CallDecisionTableAction
	 * @generated
	 */
	public Adapter createCallDecisionTableActionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decision.tree.common.model.FlowElement <em>Flow Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decision.tree.common.model.FlowElement
	 * @generated
	 */
	public Adapter createFlowElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decision.tree.common.model.node.NodeElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decision.tree.common.model.node.NodeElement
	 * @generated
	 */
	public Adapter createNodeElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //ActionAdapterFactory
