/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology;

import com.tibco.cep.decision.table.model.dtmodel.Table;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DT Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.DTRule#getTable <em>Table</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getDTRule()
 * @model
 * @generated
 */
public interface DTRule extends RuleSetParticipant {
	/**
	 * Returns the value of the '<em><b>Table</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table</em>' reference.
	 * @see #setTable(Table)
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getDTRule_Table()
	 * @model
	 * @generated
	 */
	Table getTable();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.DTRule#getTable <em>Table</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Table</em>' reference.
	 * @see #getTable()
	 * @generated
	 */
	void setTable(Table value);

} // DTRule
