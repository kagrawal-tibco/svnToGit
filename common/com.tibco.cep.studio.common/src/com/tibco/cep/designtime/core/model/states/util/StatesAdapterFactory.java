/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.states.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.states.*;
import com.tibco.cep.designtime.core.model.states.InternalStateTransition;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateAnnotationLink;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEnd;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateLink;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateSimple;
import com.tibco.cep.designtime.core.model.states.StateStart;
import com.tibco.cep.designtime.core.model.states.StateSubmachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.designtime.core.model.states.StateVertex;
import com.tibco.cep.designtime.core.model.states.StatesPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.states.StatesPackage
 * @generated
 */
public class StatesAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static StatesPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatesAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = StatesPackage.eINSTANCE;
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
	protected StatesSwitch<Adapter> modelSwitch =
		new StatesSwitch<Adapter>() {
			@Override
			public Adapter caseStateEntity(StateEntity object) {
				return createStateEntityAdapter();
			}
			@Override
			public Adapter caseStateLink(StateLink object) {
				return createStateLinkAdapter();
			}
			@Override
			public Adapter caseStateAnnotationLink(StateAnnotationLink object) {
				return createStateAnnotationLinkAdapter();
			}
			@Override
			public Adapter caseStateTransition(StateTransition object) {
				return createStateTransitionAdapter();
			}
			@Override
			public Adapter caseInternalStateTransition(InternalStateTransition object) {
				return createInternalStateTransitionAdapter();
			}
			@Override
			public Adapter caseStateMachine(StateMachine object) {
				return createStateMachineAdapter();
			}
			@Override
			public Adapter caseStateVertex(StateVertex object) {
				return createStateVertexAdapter();
			}
			@Override
			public Adapter caseState(State object) {
				return createStateAdapter();
			}
			@Override
			public Adapter caseStateComposite(StateComposite object) {
				return createStateCompositeAdapter();
			}
			@Override
			public Adapter caseStateSubmachine(StateSubmachine object) {
				return createStateSubmachineAdapter();
			}
			@Override
			public Adapter caseStateEnd(StateEnd object) {
				return createStateEndAdapter();
			}
			@Override
			public Adapter caseStateSimple(StateSimple object) {
				return createStateSimpleAdapter();
			}
			@Override
			public Adapter caseStateStart(StateStart object) {
				return createStateStartAdapter();
			}
			@Override
			public Adapter caseEntity(Entity object) {
				return createEntityAdapter();
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
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.states.StateEntity <em>State Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.states.StateEntity
	 * @generated
	 */
	public Adapter createStateEntityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.states.StateLink <em>State Link</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.states.StateLink
	 * @generated
	 */
	public Adapter createStateLinkAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.states.StateAnnotationLink <em>State Annotation Link</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.states.StateAnnotationLink
	 * @generated
	 */
	public Adapter createStateAnnotationLinkAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.states.StateTransition <em>State Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.states.StateTransition
	 * @generated
	 */
	public Adapter createStateTransitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.states.InternalStateTransition <em>Internal State Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.states.InternalStateTransition
	 * @generated
	 */
	public Adapter createInternalStateTransitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.states.StateMachine <em>State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.states.StateMachine
	 * @generated
	 */
	public Adapter createStateMachineAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.states.StateVertex <em>State Vertex</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.states.StateVertex
	 * @generated
	 */
	public Adapter createStateVertexAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.states.State <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.states.State
	 * @generated
	 */
	public Adapter createStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.states.StateComposite <em>State Composite</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.states.StateComposite
	 * @generated
	 */
	public Adapter createStateCompositeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.states.StateSubmachine <em>State Submachine</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.states.StateSubmachine
	 * @generated
	 */
	public Adapter createStateSubmachineAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.states.StateEnd <em>State End</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.states.StateEnd
	 * @generated
	 */
	public Adapter createStateEndAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.states.StateSimple <em>State Simple</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.states.StateSimple
	 * @generated
	 */
	public Adapter createStateSimpleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.states.StateStart <em>State Start</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.states.StateStart
	 * @generated
	 */
	public Adapter createStateStartAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.Entity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.Entity
	 * @generated
	 */
	public Adapter createEntityAdapter() {
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

} //StatesAdapterFactory
