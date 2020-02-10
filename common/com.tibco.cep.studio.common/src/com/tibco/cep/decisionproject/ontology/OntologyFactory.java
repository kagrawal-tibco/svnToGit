/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage
 * @generated
 */
public interface OntologyFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	OntologyFactory eINSTANCE = com.tibco.cep.decisionproject.ontology.impl.OntologyFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Folder</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Folder</em>'.
	 * @generated
	 */
	Folder createFolder();

	/**
	 * Returns a new object of class '<em>Concept</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Concept</em>'.
	 * @generated
	 */
	Concept createConcept();

	/**
	 * Returns a new object of class '<em>Event</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Event</em>'.
	 * @generated
	 */
	Event createEvent();

	/**
	 * Returns a new object of class '<em>Rule Set</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rule Set</em>'.
	 * @generated
	 */
	RuleSet createRuleSet();

	/**
	 * Returns a new object of class '<em>Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rule</em>'.
	 * @generated
	 */
	Rule createRule();

	/**
	 * Returns a new object of class '<em>Rule Function</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rule Function</em>'.
	 * @generated
	 */
	RuleFunction createRuleFunction();

	/**
	 * Returns a new object of class '<em>Property</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property</em>'.
	 * @generated
	 */
	Property createProperty();

	/**
	 * Returns a new object of class '<em>Ontology</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Ontology</em>'.
	 * @generated
	 */
	Ontology createOntology();

	/**
	 * Returns a new object of class '<em>Rule Set Participant</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rule Set Participant</em>'.
	 * @generated
	 */
	RuleSetParticipant createRuleSetParticipant();

	/**
	 * Returns a new object of class '<em>DT Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DT Rule</em>'.
	 * @generated
	 */
	DTRule createDTRule();

	/**
	 * Returns a new object of class '<em>Header</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Header</em>'.
	 * @generated
	 */
	Header createHeader();

	/**
	 * Returns a new object of class '<em>Arguments</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Arguments</em>'.
	 * @generated
	 */
	Arguments createArguments();

	/**
	 * Returns a new object of class '<em>Argument</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Argument</em>'.
	 * @generated
	 */
	Argument createArgument();

	/**
	 * Returns a new object of class '<em>Primitive Holder</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Primitive Holder</em>'.
	 * @generated
	 */
	PrimitiveHolder createPrimitiveHolder();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	OntologyPackage getOntologyPackage();

} //OntologyFactory
