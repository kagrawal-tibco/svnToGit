/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State Machine Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 			Represents the concrete subclass of Component to handle state machine visualization.
 * 			Contains 'n' StateVisualization(s) where 'n' is equal to the number of State instances in the StateMachines
 * 			Contains reference to a StateMachine
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.StateMachineComponent#getStateMachine <em>State Machine</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.StateMachineComponent#getStateVisualizationWidth <em>State Visualization Width</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.StateMachineComponent#getStateVisualizationHeight <em>State Visualization Height</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getStateMachineComponent()
 * @model extendedMetaData="name='StateMachineComponent' kind='elementOnly'"
 * @generated
 */
public interface StateMachineComponent extends Component {
	/**
	 * Returns the value of the '<em><b>State Machine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State Machine</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State Machine</em>' reference.
	 * @see #setStateMachine(EObject)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getStateMachineComponent_StateMachine()
	 * @model resolveProxies="false" required="true"
	 *        extendedMetaData="kind='element' name='stateMachine'"
	 * @generated
	 */
	EObject getStateMachine();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.StateMachineComponent#getStateMachine <em>State Machine</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State Machine</em>' reference.
	 * @see #getStateMachine()
	 * @generated
	 */
	void setStateMachine(EObject value);

	/**
	 * Returns the value of the '<em><b>State Visualization Width</b></em>' attribute.
	 * The default value is <code>"80"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State Visualization Width</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State Visualization Width</em>' attribute.
	 * @see #setStateVisualizationWidth(int)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getStateMachineComponent_StateVisualizationWidth()
	 * @model default="80" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 * @generated
	 */
	int getStateVisualizationWidth();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.StateMachineComponent#getStateVisualizationWidth <em>State Visualization Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State Visualization Width</em>' attribute.
	 * @see #getStateVisualizationWidth()
	 * @generated
	 */
	void setStateVisualizationWidth(int value);

	/**
	 * Returns the value of the '<em><b>State Visualization Height</b></em>' attribute.
	 * The default value is <code>"40"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State Visualization Height</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State Visualization Height</em>' attribute.
	 * @see #setStateVisualizationHeight(int)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getStateMachineComponent_StateVisualizationHeight()
	 * @model default="40" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 * @generated
	 */
	int getStateVisualizationHeight();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.StateMachineComponent#getStateVisualizationHeight <em>State Visualization Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State Visualization Height</em>' attribute.
	 * @see #getStateVisualizationHeight()
	 * @generated
	 */
	void setStateVisualizationHeight(int value);

} // StateMachineComponent
