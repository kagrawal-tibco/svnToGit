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
 * A representation of the model object '<em><b>Concept</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.Concept#getSuperConceptPath <em>Super Concept Path</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.Concept#isScoreCard <em>Score Card</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.Concept#getProperty <em>Property</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getConcept()
 * @model
 * 
 */
public interface Concept extends ParentResource, ArgumentResource {
	/**
	 * Returns the value of the '<em><b>Super Concept Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Super Concept Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Super Concept Path</em>' attribute.
	 * @see #setSuperConceptPath(String)
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getConcept_SuperConceptPath()
	 * @model
	 * @generated
	 */
	String getSuperConceptPath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.Concept#getSuperConceptPath <em>Super Concept Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Super Concept Path</em>' attribute.
	 * @see #getSuperConceptPath()
	 * @generated
	 */
	void setSuperConceptPath(String value);

	/**
	 * Returns the value of the '<em><b>Score Card</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Score Card</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Score Card</em>' attribute.
	 * @see #setScoreCard(boolean)
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getConcept_ScoreCard()
	 * @model
	 * @generated
	 */
	boolean isScoreCard();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.Concept#isScoreCard <em>Score Card</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Score Card</em>' attribute.
	 * @see #isScoreCard()
	 * @generated
	 */
	void setScoreCard(boolean value);

	/**
	 * Returns the value of the '<em><b>Property</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.decisionproject.ontology.Property}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property</em>' containment reference list.
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getConcept_Property()
	 * @model containment="true"
	 * @generated
	 */
	EList<AbstractResource> getProperty();

	/**
	 * Returns the value of the '<em><b>Db Concept</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Db Concept</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Db Concept</em>' attribute.
	 * @see #setDbConcept(boolean)
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getConcept_DbConcept()
	 * @model
	 * @generated
	 */
	boolean isDbConcept();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.Concept#isDbConcept <em>Db Concept</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Db Concept</em>' attribute.
	 * @see #isDbConcept()
	 * @generated
	 */
	void setDbConcept(boolean value);

} // Concept
