/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology;

import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule Function</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.RuleFunction#getHeader <em>Header</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.RuleFunction#getArguments <em>Arguments</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.RuleFunction#getBody <em>Body</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getRuleFunction()
 * @model
 * @generated
 */
public interface RuleFunction extends AbstractResource {

	/**
	 * Returns the value of the '<em><b>Header</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Header</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Header</em>' containment reference.
	 * @see #setHeader(Header)
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getRuleFunction_Header()
	 * @model containment="true"
	 * @generated
	 */
	Header getHeader();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.RuleFunction#getHeader <em>Header</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Header</em>' containment reference.
	 * @see #getHeader()
	 * @generated
	 */
	void setHeader(Header value);

	/**
	 * Returns the value of the '<em><b>Body</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Body</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Body</em>' attribute list.
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getRuleFunction_Body()
	 * @model
	 * @generated
	 */
	EList<String> getBody();

	/**
	 * Returns the value of the '<em><b>Arguments</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Arguments</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Arguments</em>' containment reference.
	 * @see #setArguments(Arguments)
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getRuleFunction_Arguments()
	 * @model containment="true"
	 * @generated
	 */
	Arguments getArguments();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.RuleFunction#getArguments <em>Arguments</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Arguments</em>' containment reference.
	 * @see #getArguments()
	 * @generated
	 */
	void setArguments(Arguments value);
} // RuleFunction
