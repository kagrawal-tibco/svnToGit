/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule Set Participant</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.RuleSetParticipant#getPath <em>Path</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.RuleSetParticipant#getParentPath <em>Parent Path</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getRuleSetParticipant()
 * @model
 * @generated
 */
public interface RuleSetParticipant extends AbstractResource {

	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #setPath(String)
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getRuleSetParticipant_Path()
	 * @model
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.RuleSetParticipant#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);

	/**
	 * Returns the value of the '<em><b>Parent Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Path</em>' attribute.
	 * @see #setParentPath(String)
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getRuleSetParticipant_ParentPath()
	 * @model
	 * @generated
	 */
	String getParentPath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.RuleSetParticipant#getParentPath <em>Parent Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Path</em>' attribute.
	 * @see #getParentPath()
	 * @generated
	 */
	void setParentPath(String value);
} // RuleSetParticipant
