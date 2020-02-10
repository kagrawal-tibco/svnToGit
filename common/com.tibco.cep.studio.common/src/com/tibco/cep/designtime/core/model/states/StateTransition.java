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
 * A representation of the model object '<em><b>State Transition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.StateTransition#getToState <em>To State</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.StateTransition#getFromState <em>From State</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.StateTransition#getGuardRule <em>Guard Rule</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.StateTransition#isLambda <em>Lambda</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.StateTransition#isFwdCorrelates <em>Fwd Correlates</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.StateTransition#getLabel <em>Label</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateTransition()
 * @model
 * @generated
 */
public interface StateTransition extends StateLink {
	/**
	 * Returns the value of the '<em><b>To State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>To State</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To State</em>' reference.
	 * @see #setToState(State)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateTransition_ToState()
	 * @model
	 * @generated
	 */
	State getToState();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.StateTransition#getToState <em>To State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To State</em>' reference.
	 * @see #getToState()
	 * @generated
	 */
	void setToState(State value);

	/**
	 * Returns the value of the '<em><b>From State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>From State</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From State</em>' reference.
	 * @see #setFromState(State)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateTransition_FromState()
	 * @model
	 * @generated
	 */
	State getFromState();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.StateTransition#getFromState <em>From State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From State</em>' reference.
	 * @see #getFromState()
	 * @generated
	 */
	void setFromState(State value);

	/**
	 * Returns the value of the '<em><b>Guard Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Guard Rule</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Guard Rule</em>' containment reference.
	 * @see #setGuardRule(Rule)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateTransition_GuardRule()
	 * @model containment="true"
	 * @generated
	 */
	Rule getGuardRule();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.StateTransition#getGuardRule <em>Guard Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Guard Rule</em>' containment reference.
	 * @see #getGuardRule()
	 * @generated
	 */
	void setGuardRule(Rule value);

	/**
	 * Returns the value of the '<em><b>Lambda</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lambda</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lambda</em>' attribute.
	 * @see #setLambda(boolean)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateTransition_Lambda()
	 * @model
	 * @generated
	 */
	boolean isLambda();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.StateTransition#isLambda <em>Lambda</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lambda</em>' attribute.
	 * @see #isLambda()
	 * @generated
	 */
	void setLambda(boolean value);

	/**
	 * Returns the value of the '<em><b>Fwd Correlates</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fwd Correlates</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fwd Correlates</em>' attribute.
	 * @see #setFwdCorrelates(boolean)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateTransition_FwdCorrelates()
	 * @model
	 * @generated
	 */
	boolean isFwdCorrelates();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.StateTransition#isFwdCorrelates <em>Fwd Correlates</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fwd Correlates</em>' attribute.
	 * @see #isFwdCorrelates()
	 * @generated
	 */
	void setFwdCorrelates(boolean value);

	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateTransition_Label()
	 * @model
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.StateTransition#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

} // StateTransition
