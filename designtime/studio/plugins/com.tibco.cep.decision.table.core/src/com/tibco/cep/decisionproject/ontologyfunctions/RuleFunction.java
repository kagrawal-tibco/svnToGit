/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontologyfunctions;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule Function</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontologyfunctions.RuleFunction#getFunctionLabel <em>Function Label</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontologyfunctions.RuleFunction#getValidity <em>Validity</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsPackage#getRuleFunction()
 * @model
 * @generated
 */
public interface RuleFunction extends AbstractResource {
	/**
	 * Returns the value of the '<em><b>Function Label</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Function Label</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function Label</em>' containment reference.
	 * @see #setFunctionLabel(FunctionLabel)
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsPackage#getRuleFunction_FunctionLabel()
	 * @model containment="true"
	 * @generated
	 */
	FunctionLabel getFunctionLabel();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontologyfunctions.RuleFunction#getFunctionLabel <em>Function Label</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function Label</em>' containment reference.
	 * @see #getFunctionLabel()
	 * @generated
	 */
	void setFunctionLabel(FunctionLabel value);

	/**
	 * Returns the value of the '<em><b>Validity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Validity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Validity</em>' attribute.
	 * @see #setValidity(String)
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsPackage#getRuleFunction_Validity()
	 * @model required="true"
	 * @generated
	 */
	String getValidity();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontologyfunctions.RuleFunction#getValidity <em>Validity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Validity</em>' attribute.
	 * @see #getValidity()
	 * @generated
	 */
	void setValidity(String value);

} // RuleFunction
