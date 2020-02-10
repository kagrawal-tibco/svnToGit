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
 * A representation of the model object '<em><b>State Composite</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.StateComposite#getTimeoutComposite <em>Timeout Composite</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.StateComposite#isConcurrentState <em>Concurrent State</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.StateComposite#getRegions <em>Regions</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.StateComposite#getStateEntities <em>State Entities</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateComposite()
 * @model
 * @generated
 */
public interface StateComposite extends State {
	/**
	 * Returns the value of the '<em><b>Timeout Composite</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Timeout Composite</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timeout Composite</em>' attribute.
	 * @see #setTimeoutComposite(int)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateComposite_TimeoutComposite()
	 * @model
	 * @generated
	 */
	int getTimeoutComposite();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.StateComposite#getTimeoutComposite <em>Timeout Composite</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Timeout Composite</em>' attribute.
	 * @see #getTimeoutComposite()
	 * @generated
	 */
	void setTimeoutComposite(int value);

	/**
	 * Returns the value of the '<em><b>Concurrent State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Concurrent State</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Concurrent State</em>' attribute.
	 * @see #setConcurrentState(boolean)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateComposite_ConcurrentState()
	 * @model
	 * @generated
	 */
	boolean isConcurrentState();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.StateComposite#isConcurrentState <em>Concurrent State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Concurrent State</em>' attribute.
	 * @see #isConcurrentState()
	 * @generated
	 */
	void setConcurrentState(boolean value);

	/**
	 * Returns the value of the '<em><b>Regions</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.states.StateComposite}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Regions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Regions</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateComposite_Regions()
	 * @model containment="true"
	 * @generated
	 */
	EList<StateComposite> getRegions();

	/**
	 * Returns the value of the '<em><b>State Entities</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.states.StateEntity}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State Entities</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State Entities</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateComposite_StateEntities()
	 * @model containment="true"
	 * @generated
	 */
	EList<StateEntity> getStateEntities();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	boolean isRegion();

} // StateComposite
