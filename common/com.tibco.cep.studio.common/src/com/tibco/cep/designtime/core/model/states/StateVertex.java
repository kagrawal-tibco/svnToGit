/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.states;

import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State Vertex</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.StateVertex#getIncomingTransitions <em>Incoming Transitions</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.StateVertex#getOutgoingTransitions <em>Outgoing Transitions</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateVertex()
 * @model
 * @generated
 */
public interface StateVertex extends StateEntity {

	/**
	 * Returns the value of the '<em><b>Incoming Transitions</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.states.StateTransition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incoming Transitions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming Transitions</em>' reference list.
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateVertex_IncomingTransitions()
	 * @model
	 * @generated
	 */
	EList<StateTransition> getIncomingTransitions();

	/**
	 * Returns the value of the '<em><b>Outgoing Transitions</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.states.StateTransition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outgoing Transitions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing Transitions</em>' reference list.
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateVertex_OutgoingTransitions()
	 * @model
	 * @generated
	 */
	EList<StateTransition> getOutgoingTransitions();
} // StateVertex
