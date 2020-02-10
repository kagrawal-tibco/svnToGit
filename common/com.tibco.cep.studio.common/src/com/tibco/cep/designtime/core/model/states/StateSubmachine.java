/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.states;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State Submachine</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.StateSubmachine#getURI <em>URI</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.StateSubmachine#isCallExplicitly <em>Call Explicitly</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.StateSubmachine#isPreserveForwardCorrelation <em>Preserve Forward Correlation</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateSubmachine()
 * @model
 * @generated
 */
public interface StateSubmachine extends StateComposite {
	/**
	 * Returns the value of the '<em><b>URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>URI</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>URI</em>' attribute.
	 * @see #setURI(String)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateSubmachine_URI()
	 * @model
	 * @generated
	 */
	String getURI();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.StateSubmachine#getURI <em>URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>URI</em>' attribute.
	 * @see #getURI()
	 * @generated
	 */
	void setURI(String value);

	/**
	 * Returns the value of the '<em><b>Call Explicitly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Call Explicitly</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Call Explicitly</em>' attribute.
	 * @see #setCallExplicitly(boolean)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateSubmachine_CallExplicitly()
	 * @model
	 * @generated
	 */
	boolean isCallExplicitly();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.StateSubmachine#isCallExplicitly <em>Call Explicitly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Call Explicitly</em>' attribute.
	 * @see #isCallExplicitly()
	 * @generated
	 */
	void setCallExplicitly(boolean value);

	/**
	 * Returns the value of the '<em><b>Preserve Forward Correlation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Preserve Forward Correlation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Preserve Forward Correlation</em>' attribute.
	 * @see #setPreserveForwardCorrelation(boolean)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateSubmachine_PreserveForwardCorrelation()
	 * @model
	 * @generated
	 */
	boolean isPreserveForwardCorrelation();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.StateSubmachine#isPreserveForwardCorrelation <em>Preserve Forward Correlation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Preserve Forward Correlation</em>' attribute.
	 * @see #isPreserveForwardCorrelation()
	 * @generated
	 */
	void setPreserveForwardCorrelation(boolean value);

} // StateSubmachine
