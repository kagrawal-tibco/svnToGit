/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.states.impl;

import com.tibco.cep.designtime.core.model.states.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.designtime.core.model.states.InternalStateTransition;
import com.tibco.cep.designtime.core.model.states.STATE_TIMEOUT_POLICY;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateAnnotationLink;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEnd;
import com.tibco.cep.designtime.core.model.states.StateLink;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateSimple;
import com.tibco.cep.designtime.core.model.states.StateStart;
import com.tibco.cep.designtime.core.model.states.StateSubmachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.designtime.core.model.states.StateVertex;
import com.tibco.cep.designtime.core.model.states.StatesFactory;
import com.tibco.cep.designtime.core.model.states.StatesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class StatesFactoryImpl extends EFactoryImpl implements StatesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static StatesFactory init() {
		try {
			StatesFactory theStatesFactory = (StatesFactory)EPackage.Registry.INSTANCE.getEFactory(StatesPackage.eNS_URI);
			if (theStatesFactory != null) {
				return theStatesFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new StatesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatesFactoryImpl() {
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
			case StatesPackage.STATE_LINK: return createStateLink();
			case StatesPackage.STATE_ANNOTATION_LINK: return createStateAnnotationLink();
			case StatesPackage.STATE_TRANSITION: return createStateTransition();
			case StatesPackage.INTERNAL_STATE_TRANSITION: return createInternalStateTransition();
			case StatesPackage.STATE_MACHINE: return createStateMachine();
			case StatesPackage.STATE_VERTEX: return createStateVertex();
			case StatesPackage.STATE: return createState();
			case StatesPackage.STATE_COMPOSITE: return createStateComposite();
			case StatesPackage.STATE_SUBMACHINE: return createStateSubmachine();
			case StatesPackage.STATE_END: return createStateEnd();
			case StatesPackage.STATE_SIMPLE: return createStateSimple();
			case StatesPackage.STATE_START: return createStateStart();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case StatesPackage.STATE_TIMEOUT_POLICY:
				return createSTATE_TIMEOUT_POLICYFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case StatesPackage.STATE_TIMEOUT_POLICY:
				return convertSTATE_TIMEOUT_POLICYToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateLink createStateLink() {
		StateLinkImpl stateLink = new StateLinkImpl();
		return stateLink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateAnnotationLink createStateAnnotationLink() {
		StateAnnotationLinkImpl stateAnnotationLink = new StateAnnotationLinkImpl();
		return stateAnnotationLink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateTransition createStateTransition() {
		StateTransitionImpl stateTransition = new StateTransitionImpl();
		return stateTransition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InternalStateTransition createInternalStateTransition() {
		InternalStateTransitionImpl internalStateTransition = new InternalStateTransitionImpl();
		return internalStateTransition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateMachine createStateMachine() {
		StateMachineImpl stateMachine = new StateMachineImpl();
		return stateMachine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateVertex createStateVertex() {
		StateVertexImpl stateVertex = new StateVertexImpl();
		return stateVertex;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State createState() {
		StateImpl state = new StateImpl();
		return state;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateComposite createStateComposite() {
		StateCompositeImpl stateComposite = new StateCompositeImpl();
		return stateComposite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateSubmachine createStateSubmachine() {
		StateSubmachineImpl stateSubmachine = new StateSubmachineImpl();
		return stateSubmachine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateEnd createStateEnd() {
		StateEndImpl stateEnd = new StateEndImpl();
		return stateEnd;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateSimple createStateSimple() {
		StateSimpleImpl stateSimple = new StateSimpleImpl();
		return stateSimple;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateStart createStateStart() {
		StateStartImpl stateStart = new StateStartImpl();
		return stateStart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public STATE_TIMEOUT_POLICY createSTATE_TIMEOUT_POLICYFromString(EDataType eDataType, String initialValue) {
		STATE_TIMEOUT_POLICY result = STATE_TIMEOUT_POLICY.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSTATE_TIMEOUT_POLICYToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatesPackage getStatesPackage() {
		return (StatesPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static StatesPackage getPackage() {
		return StatesPackage.eINSTANCE;
	}

} //StatesFactoryImpl
