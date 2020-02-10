/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.states;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.states.StatesPackage
 * @generated
 */
public interface StatesFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	StatesFactory eINSTANCE = com.tibco.cep.designtime.core.model.states.impl.StatesFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>State Link</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>State Link</em>'.
	 * @generated
	 */
	StateLink createStateLink();

	/**
	 * Returns a new object of class '<em>State Annotation Link</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>State Annotation Link</em>'.
	 * @generated
	 */
	StateAnnotationLink createStateAnnotationLink();

	/**
	 * Returns a new object of class '<em>State Transition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>State Transition</em>'.
	 * @generated
	 */
	StateTransition createStateTransition();

	/**
	 * Returns a new object of class '<em>Internal State Transition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Internal State Transition</em>'.
	 * @generated
	 */
	InternalStateTransition createInternalStateTransition();

	/**
	 * Returns a new object of class '<em>State Machine</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>State Machine</em>'.
	 * @generated
	 */
	StateMachine createStateMachine();

	/**
	 * Returns a new object of class '<em>State Vertex</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>State Vertex</em>'.
	 * @generated
	 */
	StateVertex createStateVertex();

	/**
	 * Returns a new object of class '<em>State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>State</em>'.
	 * @generated
	 */
	State createState();

	/**
	 * Returns a new object of class '<em>State Composite</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>State Composite</em>'.
	 * @generated
	 */
	StateComposite createStateComposite();

	/**
	 * Returns a new object of class '<em>State Submachine</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>State Submachine</em>'.
	 * @generated
	 */
	StateSubmachine createStateSubmachine();

	/**
	 * Returns a new object of class '<em>State End</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>State End</em>'.
	 * @generated
	 */
	StateEnd createStateEnd();

	/**
	 * Returns a new object of class '<em>State Simple</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>State Simple</em>'.
	 * @generated
	 */
	StateSimple createStateSimple();

	/**
	 * Returns a new object of class '<em>State Start</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>State Start</em>'.
	 * @generated
	 */
	StateStart createStateStart();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	StatesPackage getStatesPackage();

} //StatesFactory
