/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontologyfunctions;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Concept</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontologyfunctions.Concept#getFunctionLabel <em>Function Label</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsPackage#getConcept()
 * @model
 * @generated
 */
public interface Concept extends AbstractResource {
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
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsPackage#getConcept_FunctionLabel()
	 * @model containment="true"
	 * @generated
	 */
	FunctionLabel getFunctionLabel();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontologyfunctions.Concept#getFunctionLabel <em>Function Label</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function Label</em>' containment reference.
	 * @see #getFunctionLabel()
	 * @generated
	 */
	void setFunctionLabel(FunctionLabel value);

} // Concept
