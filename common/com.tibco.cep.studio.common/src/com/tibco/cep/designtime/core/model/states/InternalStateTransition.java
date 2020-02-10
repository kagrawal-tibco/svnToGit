/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.states;

import com.tibco.cep.designtime.core.model.rule.Rule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Internal State Transition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.InternalStateTransition#getRule <em>Rule</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.InternalStateTransition#getOwnerState <em>Owner State</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getInternalStateTransition()
 * @model
 * @generated
 */
public interface InternalStateTransition extends StateTransition {
	/**
	 * Returns the value of the '<em><b>Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule</em>' reference.
	 * @see #setRule(Rule)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getInternalStateTransition_Rule()
	 * @model
	 * @generated
	 */
	Rule getRule();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.InternalStateTransition#getRule <em>Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule</em>' reference.
	 * @see #getRule()
	 * @generated
	 */
	void setRule(Rule value);

	/**
	 * Returns the value of the '<em><b>Owner State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner State</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner State</em>' reference.
	 * @see #setOwnerState(State)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getInternalStateTransition_OwnerState()
	 * @model
	 * @generated
	 */
	State getOwnerState();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.InternalStateTransition#getOwnerState <em>Owner State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner State</em>' reference.
	 * @see #getOwnerState()
	 * @generated
	 */
	void setOwnerState(State value);

} // InternalStateTransition
