/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.tester.emf.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage
 * @generated
 */
public interface ModelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ModelFactory eINSTANCE = com.tibco.cep.studio.tester.emf.model.impl.ModelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Casual Objects Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Casual Objects Type</em>'.
	 * @generated
	 */
	CasualObjectsType createCasualObjectsType();

	/**
	 * Returns a new object of class '<em>Concept Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Concept Type</em>'.
	 * @generated
	 */
	ConceptType createConceptType();

	/**
	 * Returns a new object of class '<em>Entity Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Entity Type</em>'.
	 * @generated
	 */
	EntityType createEntityType();

	/**
	 * Returns a new object of class '<em>Event Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Event Type</em>'.
	 * @generated
	 */
	EventType createEventType();

	/**
	 * Returns a new object of class '<em>Execution Object Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Execution Object Type</em>'.
	 * @generated
	 */
	ExecutionObjectType createExecutionObjectType();

	/**
	 * Returns a new object of class '<em>Invocation Object Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Invocation Object Type</em>'.
	 * @generated
	 */
	InvocationObjectType createInvocationObjectType();

	/**
	 * Returns a new object of class '<em>Namespace Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Namespace Type</em>'.
	 * @generated
	 */
	NamespaceType createNamespaceType();

	/**
	 * Returns a new object of class '<em>Ontology Object Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Ontology Object Type</em>'.
	 * @generated
	 */
	OntologyObjectType createOntologyObjectType();

	/**
	 * Returns a new object of class '<em>Operation Object Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Operation Object Type</em>'.
	 * @generated
	 */
	OperationObjectType createOperationObjectType();

	/**
	 * Returns a new object of class '<em>Property Attrs Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property Attrs Type</em>'.
	 * @generated
	 */
	PropertyAttrsType createPropertyAttrsType();

	/**
	 * Returns a new object of class '<em>Property Modification Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property Modification Type</em>'.
	 * @generated
	 */
	PropertyModificationType createPropertyModificationType();

	/**
	 * Returns a new object of class '<em>Property Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property Type</em>'.
	 * @generated
	 */
	PropertyType createPropertyType();

	/**
	 * Returns a new object of class '<em>Rete Object Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rete Object Type</em>'.
	 * @generated
	 */
	ReteObjectType createReteObjectType();

	/**
	 * Returns a new object of class '<em>Tester Results Datatype</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tester Results Datatype</em>'.
	 * @generated
	 */
	TesterResultsDatatype createTesterResultsDatatype();

	/**
	 * Returns a new object of class '<em>Tester Results Operation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tester Results Operation</em>'.
	 * @generated
	 */
	TesterResultsOperation createTesterResultsOperation();

	/**
	 * Returns a new object of class '<em>Tester Results Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tester Results Type</em>'.
	 * @generated
	 */
	TesterResultsType createTesterResultsType();

	/**
	 * Returns a new object of class '<em>Tester Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tester Root</em>'.
	 * @generated
	 */
	TesterRoot createTesterRoot();

	/**
	 * Returns a new object of class '<em>Value Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Value Type</em>'.
	 * @generated
	 */
	ValueType createValueType();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ModelPackage getModelPackage();

} //ModelFactory
