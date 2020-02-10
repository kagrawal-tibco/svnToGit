/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontologyfunctions;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsPackage
 * @generated
 */
public interface OntologyFunctionsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	OntologyFunctionsFactory eINSTANCE = com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Root</em>'.
	 * @generated
	 */
	OntologyFunctionsRoot createOntologyFunctionsRoot();

	/**
	 * Returns a new object of class '<em>Ontology Functions</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Ontology Functions</em>'.
	 * @generated
	 */
	OntologyFunctions createOntologyFunctions();

	/**
	 * Returns a new object of class '<em>Abstract Resource</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Abstract Resource</em>'.
	 * @generated
	 */
	AbstractResource createAbstractResource();

	/**
	 * Returns a new object of class '<em>Concept</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Concept</em>'.
	 * @generated
	 */
	Concept createConcept();

	/**
	 * Returns a new object of class '<em>Folder</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Folder</em>'.
	 * @generated
	 */
	Folder createFolder();

	/**
	 * Returns a new object of class '<em>Event</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Event</em>'.
	 * @generated
	 */
	Event createEvent();

	/**
	 * Returns a new object of class '<em>Rule Function</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rule Function</em>'.
	 * @generated
	 */
	RuleFunction createRuleFunction();

	/**
	 * Returns a new object of class '<em>Function Label</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Function Label</em>'.
	 * @generated
	 */
	FunctionLabel createFunctionLabel();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	OntologyFunctionsPackage getOntologyFunctionsPackage();

} //OntologyFunctionsFactory
