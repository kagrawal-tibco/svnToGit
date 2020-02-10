/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.states;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.element.Concept;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State Machine</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.StateMachine#getStateTransitions <em>State Transitions</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.StateMachine#getAnnotationLinks <em>Annotation Links</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.StateMachine#isFwdCorrelates <em>Fwd Correlates</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.StateMachine#isMain <em>Main</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.StateMachine#getOwnerConceptPath <em>Owner Concept Path</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateMachine()
 * @model
 * @generated
 */
public interface StateMachine extends StateComposite {
	/**
	 * Returns the value of the '<em><b>State Transitions</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.states.StateTransition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State Transitions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State Transitions</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateMachine_StateTransitions()
	 * @model containment="true"
	 * @generated
	 */
	EList<StateTransition> getStateTransitions();

	/**
	 * Returns the value of the '<em><b>Annotation Links</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.states.StateAnnotationLink}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Annotation Links</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Annotation Links</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateMachine_AnnotationLinks()
	 * @model containment="true"
	 * @generated
	 */
	EList<StateAnnotationLink> getAnnotationLinks();

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
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateMachine_FwdCorrelates()
	 * @model
	 * @generated
	 */
	boolean isFwdCorrelates();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.StateMachine#isFwdCorrelates <em>Fwd Correlates</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fwd Correlates</em>' attribute.
	 * @see #isFwdCorrelates()
	 * @generated
	 */
	void setFwdCorrelates(boolean value);

	/**
	 * Returns the value of the '<em><b>Main</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Main</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Main</em>' attribute.
	 * @see #setMain(boolean)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateMachine_Main()
	 * @model
	 * @generated
	 */
	boolean isMain();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.StateMachine#isMain <em>Main</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Main</em>' attribute.
	 * @see #isMain()
	 * @generated
	 */
	void setMain(boolean value);

	/**
	 * Returns the value of the '<em><b>Owner Concept Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner Concept Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner Concept Path</em>' attribute.
	 * @see #setOwnerConceptPath(String)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateMachine_OwnerConceptPath()
	 * @model
	 * @generated
	 */
	String getOwnerConceptPath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.StateMachine#getOwnerConceptPath <em>Owner Concept Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner Concept Path</em>' attribute.
	 * @see #getOwnerConceptPath()
	 * @generated
	 */
	void setOwnerConceptPath(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	Concept getOwnerConcept();

} // StateMachine
