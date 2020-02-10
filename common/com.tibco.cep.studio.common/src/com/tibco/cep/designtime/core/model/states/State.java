/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.states;

import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.State#isInternalTransitionEnabled <em>Internal Transition Enabled</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.State#getEntryAction <em>Entry Action</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.State#getExitAction <em>Exit Action</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.State#getTimeoutAction <em>Timeout Action</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.State#getInternalTransitionRule <em>Internal Transition Rule</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.State#getTimeoutExpression <em>Timeout Expression</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.State#getTimeoutStateGUID <em>Timeout State GUID</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.State#getTimeoutTransitionGUID <em>Timeout Transition GUID</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.State#getTimeoutPolicy <em>Timeout Policy</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.State#getTimeoutState <em>Timeout State</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getState()
 * @model
 * @generated
 */
public interface State extends StateVertex {
	/**
	 * Returns the value of the '<em><b>Internal Transition Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Internal Transition Enabled</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Internal Transition Enabled</em>' attribute.
	 * @see #setInternalTransitionEnabled(boolean)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getState_InternalTransitionEnabled()
	 * @model
	 * @generated
	 */
	boolean isInternalTransitionEnabled();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.State#isInternalTransitionEnabled <em>Internal Transition Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Internal Transition Enabled</em>' attribute.
	 * @see #isInternalTransitionEnabled()
	 * @generated
	 */
	void setInternalTransitionEnabled(boolean value);

	/**
	 * Returns the value of the '<em><b>Entry Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entry Action</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entry Action</em>' containment reference.
	 * @see #setEntryAction(Rule)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getState_EntryAction()
	 * @model containment="true"
	 * @generated
	 */
	Rule getEntryAction();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.State#getEntryAction <em>Entry Action</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entry Action</em>' containment reference.
	 * @see #getEntryAction()
	 * @generated
	 */
	void setEntryAction(Rule value);

	/**
	 * Returns the value of the '<em><b>Exit Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exit Action</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exit Action</em>' containment reference.
	 * @see #setExitAction(Rule)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getState_ExitAction()
	 * @model containment="true"
	 * @generated
	 */
	Rule getExitAction();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.State#getExitAction <em>Exit Action</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exit Action</em>' containment reference.
	 * @see #getExitAction()
	 * @generated
	 */
	void setExitAction(Rule value);

	/**
	 * Returns the value of the '<em><b>Timeout Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Timeout Action</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timeout Action</em>' containment reference.
	 * @see #setTimeoutAction(Rule)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getState_TimeoutAction()
	 * @model containment="true"
	 * @generated
	 */
	Rule getTimeoutAction();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.State#getTimeoutAction <em>Timeout Action</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Timeout Action</em>' containment reference.
	 * @see #getTimeoutAction()
	 * @generated
	 */
	void setTimeoutAction(Rule value);

	/**
	 * Returns the value of the '<em><b>Internal Transition Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Internal Transition Rule</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Internal Transition Rule</em>' containment reference.
	 * @see #setInternalTransitionRule(Rule)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getState_InternalTransitionRule()
	 * @model containment="true"
	 * @generated
	 */
	Rule getInternalTransitionRule();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.State#getInternalTransitionRule <em>Internal Transition Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Internal Transition Rule</em>' containment reference.
	 * @see #getInternalTransitionRule()
	 * @generated
	 */
	void setInternalTransitionRule(Rule value);

	/**
	 * Returns the value of the '<em><b>Timeout Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Timeout Expression</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timeout Expression</em>' containment reference.
	 * @see #setTimeoutExpression(RuleFunction)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getState_TimeoutExpression()
	 * @model containment="true"
	 * @generated
	 */
	RuleFunction getTimeoutExpression();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.State#getTimeoutExpression <em>Timeout Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Timeout Expression</em>' containment reference.
	 * @see #getTimeoutExpression()
	 * @generated
	 */
	void setTimeoutExpression(RuleFunction value);

	/**
	 * Returns the value of the '<em><b>Timeout State GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Timeout State GUID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timeout State GUID</em>' attribute.
	 * @see #setTimeoutStateGUID(String)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getState_TimeoutStateGUID()
	 * @model
	 * @generated
	 */
	String getTimeoutStateGUID();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.State#getTimeoutStateGUID <em>Timeout State GUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Timeout State GUID</em>' attribute.
	 * @see #getTimeoutStateGUID()
	 * @generated
	 */
	void setTimeoutStateGUID(String value);

	/**
	 * Returns the value of the '<em><b>Timeout Transition GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Timeout Transition GUID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timeout Transition GUID</em>' attribute.
	 * @see #setTimeoutTransitionGUID(String)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getState_TimeoutTransitionGUID()
	 * @model
	 * @generated
	 */
	String getTimeoutTransitionGUID();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.State#getTimeoutTransitionGUID <em>Timeout Transition GUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Timeout Transition GUID</em>' attribute.
	 * @see #getTimeoutTransitionGUID()
	 * @generated
	 */
	void setTimeoutTransitionGUID(String value);

	/**
	 * Returns the value of the '<em><b>Timeout Policy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Timeout Policy</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timeout Policy</em>' attribute.
	 * @see #setTimeoutPolicy(int)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getState_TimeoutPolicy()
	 * @model
	 * @generated
	 */
	int getTimeoutPolicy();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.State#getTimeoutPolicy <em>Timeout Policy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Timeout Policy</em>' attribute.
	 * @see #getTimeoutPolicy()
	 * @generated
	 */
	void setTimeoutPolicy(int value);

	/**
	 * Returns the value of the '<em><b>Timeout State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Timeout State</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timeout State</em>' reference.
	 * @see #setTimeoutState(State)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getState_TimeoutState()
	 * @model
	 * @generated
	 */
	State getTimeoutState();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.State#getTimeoutState <em>Timeout State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Timeout State</em>' reference.
	 * @see #getTimeoutState()
	 * @generated
	 */
	void setTimeoutState(State value);

} // State
