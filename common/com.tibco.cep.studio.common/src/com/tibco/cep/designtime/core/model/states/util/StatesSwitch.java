/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.states.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

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
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.states.StatesPackage
 * @generated
 */
public class StatesSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static StatesPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatesSwitch() {
		if (modelPackage == null) {
			modelPackage = StatesPackage.eINSTANCE;
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
			case StatesPackage.STATE_ENTITY: {
				StateEntity stateEntity = (StateEntity)theEObject;
				T result = caseStateEntity(stateEntity);
				if (result == null) result = caseEntity(stateEntity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case StatesPackage.STATE_LINK: {
				StateLink stateLink = (StateLink)theEObject;
				T result = caseStateLink(stateLink);
				if (result == null) result = caseStateEntity(stateLink);
				if (result == null) result = caseEntity(stateLink);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case StatesPackage.STATE_ANNOTATION_LINK: {
				StateAnnotationLink stateAnnotationLink = (StateAnnotationLink)theEObject;
				T result = caseStateAnnotationLink(stateAnnotationLink);
				if (result == null) result = caseStateLink(stateAnnotationLink);
				if (result == null) result = caseStateEntity(stateAnnotationLink);
				if (result == null) result = caseEntity(stateAnnotationLink);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case StatesPackage.STATE_TRANSITION: {
				StateTransition stateTransition = (StateTransition)theEObject;
				T result = caseStateTransition(stateTransition);
				if (result == null) result = caseStateLink(stateTransition);
				if (result == null) result = caseStateEntity(stateTransition);
				if (result == null) result = caseEntity(stateTransition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case StatesPackage.INTERNAL_STATE_TRANSITION: {
				InternalStateTransition internalStateTransition = (InternalStateTransition)theEObject;
				T result = caseInternalStateTransition(internalStateTransition);
				if (result == null) result = caseStateTransition(internalStateTransition);
				if (result == null) result = caseStateLink(internalStateTransition);
				if (result == null) result = caseStateEntity(internalStateTransition);
				if (result == null) result = caseEntity(internalStateTransition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case StatesPackage.STATE_MACHINE: {
				StateMachine stateMachine = (StateMachine)theEObject;
				T result = caseStateMachine(stateMachine);
				if (result == null) result = caseStateComposite(stateMachine);
				if (result == null) result = caseState(stateMachine);
				if (result == null) result = caseStateVertex(stateMachine);
				if (result == null) result = caseStateEntity(stateMachine);
				if (result == null) result = caseEntity(stateMachine);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case StatesPackage.STATE_VERTEX: {
				StateVertex stateVertex = (StateVertex)theEObject;
				T result = caseStateVertex(stateVertex);
				if (result == null) result = caseStateEntity(stateVertex);
				if (result == null) result = caseEntity(stateVertex);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case StatesPackage.STATE: {
				State state = (State)theEObject;
				T result = caseState(state);
				if (result == null) result = caseStateVertex(state);
				if (result == null) result = caseStateEntity(state);
				if (result == null) result = caseEntity(state);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case StatesPackage.STATE_COMPOSITE: {
				StateComposite stateComposite = (StateComposite)theEObject;
				T result = caseStateComposite(stateComposite);
				if (result == null) result = caseState(stateComposite);
				if (result == null) result = caseStateVertex(stateComposite);
				if (result == null) result = caseStateEntity(stateComposite);
				if (result == null) result = caseEntity(stateComposite);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case StatesPackage.STATE_SUBMACHINE: {
				StateSubmachine stateSubmachine = (StateSubmachine)theEObject;
				T result = caseStateSubmachine(stateSubmachine);
				if (result == null) result = caseStateComposite(stateSubmachine);
				if (result == null) result = caseState(stateSubmachine);
				if (result == null) result = caseStateVertex(stateSubmachine);
				if (result == null) result = caseStateEntity(stateSubmachine);
				if (result == null) result = caseEntity(stateSubmachine);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case StatesPackage.STATE_END: {
				StateEnd stateEnd = (StateEnd)theEObject;
				T result = caseStateEnd(stateEnd);
				if (result == null) result = caseState(stateEnd);
				if (result == null) result = caseStateVertex(stateEnd);
				if (result == null) result = caseStateEntity(stateEnd);
				if (result == null) result = caseEntity(stateEnd);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case StatesPackage.STATE_SIMPLE: {
				StateSimple stateSimple = (StateSimple)theEObject;
				T result = caseStateSimple(stateSimple);
				if (result == null) result = caseState(stateSimple);
				if (result == null) result = caseStateVertex(stateSimple);
				if (result == null) result = caseStateEntity(stateSimple);
				if (result == null) result = caseEntity(stateSimple);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case StatesPackage.STATE_START: {
				StateStart stateStart = (StateStart)theEObject;
				T result = caseStateStart(stateStart);
				if (result == null) result = caseState(stateStart);
				if (result == null) result = caseStateVertex(stateStart);
				if (result == null) result = caseStateEntity(stateStart);
				if (result == null) result = caseEntity(stateStart);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State Entity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State Entity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStateEntity(StateEntity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State Link</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State Link</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStateLink(StateLink object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State Annotation Link</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State Annotation Link</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStateAnnotationLink(StateAnnotationLink object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State Transition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State Transition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStateTransition(StateTransition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Internal State Transition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Internal State Transition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInternalStateTransition(InternalStateTransition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State Machine</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State Machine</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStateMachine(StateMachine object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State Vertex</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State Vertex</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStateVertex(StateVertex object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseState(State object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State Composite</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State Composite</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStateComposite(StateComposite object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State Submachine</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State Submachine</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStateSubmachine(StateSubmachine object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State End</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State End</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStateEnd(StateEnd object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State Simple</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State Simple</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStateSimple(StateSimple object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State Start</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State Start</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStateStart(StateStart object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Entity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEntity(Entity object) {
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

} //StatesSwitch
