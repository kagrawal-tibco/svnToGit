/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.states;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State Entity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.StateEntity#getOwnerStateMachine <em>Owner State Machine</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.StateEntity#getOwnerStateMachinePath <em>Owner State Machine Path</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.StateEntity#getParent <em>Parent</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.StateEntity#getTimeout <em>Timeout</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.StateEntity#getTimeoutUnits <em>Timeout Units</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateEntity()
 * @model abstract="true"
 * @generated
 */
public interface StateEntity extends Entity {
	/**
	 * Returns the value of the '<em><b>Owner State Machine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner State Machine</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner State Machine</em>' reference.
	 * @see #setOwnerStateMachine(StateMachine)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateEntity_OwnerStateMachine()
	 * @model transient="true"
	 * @generated
	 */
	StateMachine getOwnerStateMachine();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.StateEntity#getOwnerStateMachine <em>Owner State Machine</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner State Machine</em>' reference.
	 * @see #getOwnerStateMachine()
	 * @generated
	 */
	void setOwnerStateMachine(StateMachine value);

	/**
	 * Returns the value of the '<em><b>Owner State Machine Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner State Machine Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner State Machine Path</em>' attribute.
	 * @see #setOwnerStateMachinePath(String)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateEntity_OwnerStateMachinePath()
	 * @model
	 * @generated
	 */
	String getOwnerStateMachinePath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.StateEntity#getOwnerStateMachinePath <em>Owner State Machine Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner State Machine Path</em>' attribute.
	 * @see #getOwnerStateMachinePath()
	 * @generated
	 */
	void setOwnerStateMachinePath(String value);

	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #setParent(StateEntity)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateEntity_Parent()
	 * @model
	 * @generated
	 */
	StateEntity getParent();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.StateEntity#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(StateEntity value);

	/**
	 * Returns the value of the '<em><b>Timeout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Timeout</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timeout</em>' attribute.
	 * @see #setTimeout(int)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateEntity_Timeout()
	 * @model required="true"
	 * @generated
	 */
	int getTimeout();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.StateEntity#getTimeout <em>Timeout</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Timeout</em>' attribute.
	 * @see #getTimeout()
	 * @generated
	 */
	void setTimeout(int value);

	/**
	 * Returns the value of the '<em><b>Timeout Units</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.TIMEOUT_UNITS}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Timeout Units</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timeout Units</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.TIMEOUT_UNITS
	 * @see #setTimeoutUnits(TIMEOUT_UNITS)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateEntity_TimeoutUnits()
	 * @model required="true"
	 * @generated
	 */
	TIMEOUT_UNITS getTimeoutUnits();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.StateEntity#getTimeoutUnits <em>Timeout Units</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Timeout Units</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.TIMEOUT_UNITS
	 * @see #getTimeoutUnits()
	 * @generated
	 */
	void setTimeoutUnits(TIMEOUT_UNITS value);

} // StateEntity
