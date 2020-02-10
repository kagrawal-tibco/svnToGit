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
 * A representation of the model object '<em><b>Rule Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.RuleSet#getRuleSetParicipant <em>Rule Set Paricipant</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getRuleSet()
 * @model
 * @generated
 */
public interface RuleSet extends ParentResource {
	/**
	 * Returns the value of the '<em><b>Rule Set Paricipant</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.decisionproject.ontology.RuleSetParticipant}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Set Paricipant</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Set Paricipant</em>' reference list.
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getRuleSet_RuleSetParicipant()
	 * @model
	 * @generated
	 */
	EList<RuleSetParticipant> getRuleSetParicipant();

} // RuleSet
