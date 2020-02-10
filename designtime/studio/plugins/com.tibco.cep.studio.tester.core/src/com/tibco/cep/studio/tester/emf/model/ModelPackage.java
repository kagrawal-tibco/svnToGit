/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.tester.emf.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.tester.emf.model.ModelFactory
 * @model kind="package"
 *        annotation="http://www.w3.org/XML/1998/namespace lang='en'"
 * @generated
 */
public interface ModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "model";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "www.tibco.com/be/studio/tester";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "test";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ModelPackage eINSTANCE = com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.tester.emf.model.impl.CasualObjectsTypeImpl <em>Casual Objects Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.impl.CasualObjectsTypeImpl
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getCasualObjectsType()
	 * @generated
	 */
	int CASUAL_OBJECTS_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Concept</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASUAL_OBJECTS_TYPE__CONCEPT = 0;

	/**
	 * The feature id for the '<em><b>Event</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASUAL_OBJECTS_TYPE__EVENT = 1;

	/**
	 * The number of structural features of the '<em>Casual Objects Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASUAL_OBJECTS_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.tester.emf.model.impl.EntityTypeImpl <em>Entity Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.impl.EntityTypeImpl
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getEntityType()
	 * @generated
	 */
	int ENTITY_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_TYPE__PROPERTY = 0;

	/**
	 * The feature id for the '<em><b>Modified Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_TYPE__MODIFIED_PROPERTY = 1;

	/**
	 * The feature id for the '<em><b>Ext Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_TYPE__EXT_ID = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_TYPE__ID = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_TYPE__NAME = 4;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_TYPE__NAMESPACE = 5;

	/**
	 * The number of structural features of the '<em>Entity Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_TYPE_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.tester.emf.model.impl.ConceptTypeImpl <em>Concept Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ConceptTypeImpl
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getConceptType()
	 * @generated
	 */
	int CONCEPT_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT_TYPE__PROPERTY = ENTITY_TYPE__PROPERTY;

	/**
	 * The feature id for the '<em><b>Modified Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT_TYPE__MODIFIED_PROPERTY = ENTITY_TYPE__MODIFIED_PROPERTY;

	/**
	 * The feature id for the '<em><b>Ext Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT_TYPE__EXT_ID = ENTITY_TYPE__EXT_ID;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT_TYPE__ID = ENTITY_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT_TYPE__NAME = ENTITY_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT_TYPE__NAMESPACE = ENTITY_TYPE__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Is Scorecard</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT_TYPE__IS_SCORECARD = ENTITY_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Concept Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT_TYPE_FEATURE_COUNT = ENTITY_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.tester.emf.model.impl.EventTypeImpl <em>Event Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.impl.EventTypeImpl
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getEventType()
	 * @generated
	 */
	int EVENT_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE__PROPERTY = ENTITY_TYPE__PROPERTY;

	/**
	 * The feature id for the '<em><b>Modified Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE__MODIFIED_PROPERTY = ENTITY_TYPE__MODIFIED_PROPERTY;

	/**
	 * The feature id for the '<em><b>Ext Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE__EXT_ID = ENTITY_TYPE__EXT_ID;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE__ID = ENTITY_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE__NAME = ENTITY_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE__NAMESPACE = ENTITY_TYPE__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Payload</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE__PAYLOAD = ENTITY_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Event Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE_FEATURE_COUNT = ENTITY_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.tester.emf.model.impl.ExecutionObjectTypeImpl <em>Execution Object Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ExecutionObjectTypeImpl
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getExecutionObjectType()
	 * @generated
	 */
	int EXECUTION_OBJECT_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Rete Object</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_OBJECT_TYPE__RETE_OBJECT = 0;

	/**
	 * The feature id for the '<em><b>Invocation Object</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_OBJECT_TYPE__INVOCATION_OBJECT = 1;

	/**
	 * The feature id for the '<em><b>Casual Objects</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_OBJECT_TYPE__CASUAL_OBJECTS = 2;

	/**
	 * The number of structural features of the '<em>Execution Object Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_OBJECT_TYPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.tester.emf.model.impl.InvocationObjectTypeImpl <em>Invocation Object Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.impl.InvocationObjectTypeImpl
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getInvocationObjectType()
	 * @generated
	 */
	int INVOCATION_OBJECT_TYPE = 5;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INVOCATION_OBJECT_TYPE__NAMESPACE = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INVOCATION_OBJECT_TYPE__TYPE = 1;

	/**
	 * The number of structural features of the '<em>Invocation Object Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INVOCATION_OBJECT_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.tester.emf.model.impl.NamespaceTypeImpl <em>Namespace Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.impl.NamespaceTypeImpl
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getNamespaceType()
	 * @generated
	 */
	int NAMESPACE_TYPE = 6;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE_TYPE__VALUE = 0;

	/**
	 * The number of structural features of the '<em>Namespace Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.tester.emf.model.impl.OntologyObjectTypeImpl <em>Ontology Object Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.impl.OntologyObjectTypeImpl
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getOntologyObjectType()
	 * @generated
	 */
	int ONTOLOGY_OBJECT_TYPE = 7;

	/**
	 * The feature id for the '<em><b>Operation Object</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONTOLOGY_OBJECT_TYPE__OPERATION_OBJECT = 0;

	/**
	 * The feature id for the '<em><b>Data Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONTOLOGY_OBJECT_TYPE__DATA_TYPE = 1;

	/**
	 * The number of structural features of the '<em>Ontology Object Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONTOLOGY_OBJECT_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.tester.emf.model.impl.OperationObjectTypeImpl <em>Operation Object Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.impl.OperationObjectTypeImpl
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getOperationObjectType()
	 * @generated
	 */
	int OPERATION_OBJECT_TYPE = 8;

	/**
	 * The feature id for the '<em><b>Execution Object</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_OBJECT_TYPE__EXECUTION_OBJECT = 0;

	/**
	 * The feature id for the '<em><b>Operation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_OBJECT_TYPE__OPERATION = 1;

	/**
	 * The number of structural features of the '<em>Operation Object Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_OBJECT_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.tester.emf.model.impl.PropertyAttrsTypeImpl <em>Property Attrs Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.impl.PropertyAttrsTypeImpl
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getPropertyAttrsType()
	 * @generated
	 */
	int PROPERTY_ATTRS_TYPE = 9;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ATTRS_TYPE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Data Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ATTRS_TYPE__DATA_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Multiple</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ATTRS_TYPE__MULTIPLE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ATTRS_TYPE__NAME = 3;

	/**
	 * The number of structural features of the '<em>Property Attrs Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ATTRS_TYPE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.tester.emf.model.impl.PropertyModificationTypeImpl <em>Property Modification Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.impl.PropertyModificationTypeImpl
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getPropertyModificationType()
	 * @generated
	 */
	int PROPERTY_MODIFICATION_TYPE = 10;

	/**
	 * The feature id for the '<em><b>Old Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_MODIFICATION_TYPE__OLD_VALUE = 0;

	/**
	 * The feature id for the '<em><b>New Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_MODIFICATION_TYPE__NEW_VALUE = 1;

	/**
	 * The feature id for the '<em><b>Data Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_MODIFICATION_TYPE__DATA_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Multiple</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_MODIFICATION_TYPE__MULTIPLE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_MODIFICATION_TYPE__NAME = 4;

	/**
	 * The number of structural features of the '<em>Property Modification Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_MODIFICATION_TYPE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.tester.emf.model.impl.PropertyTypeImpl <em>Property Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.impl.PropertyTypeImpl
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getPropertyType()
	 * @generated
	 */
	int PROPERTY_TYPE = 11;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TYPE__VALUE = PROPERTY_ATTRS_TYPE__VALUE;

	/**
	 * The feature id for the '<em><b>Data Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TYPE__DATA_TYPE = PROPERTY_ATTRS_TYPE__DATA_TYPE;

	/**
	 * The feature id for the '<em><b>Multiple</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TYPE__MULTIPLE = PROPERTY_ATTRS_TYPE__MULTIPLE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TYPE__NAME = PROPERTY_ATTRS_TYPE__NAME;

	/**
	 * The number of structural features of the '<em>Property Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TYPE_FEATURE_COUNT = PROPERTY_ATTRS_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.tester.emf.model.impl.ReteObjectTypeImpl <em>Rete Object Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ReteObjectTypeImpl
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getReteObjectType()
	 * @generated
	 */
	int RETE_OBJECT_TYPE = 12;

	/**
	 * The feature id for the '<em><b>Change Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETE_OBJECT_TYPE__CHANGE_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Concept</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETE_OBJECT_TYPE__CONCEPT = 1;

	/**
	 * The feature id for the '<em><b>Event</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETE_OBJECT_TYPE__EVENT = 2;

	/**
	 * The number of structural features of the '<em>Rete Object Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETE_OBJECT_TYPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.tester.emf.model.impl.TesterResultsDatatypeImpl <em>Tester Results Datatype</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.impl.TesterResultsDatatypeImpl
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getTesterResultsDatatype()
	 * @generated
	 */
	int TESTER_RESULTS_DATATYPE = 13;

	/**
	 * The feature id for the '<em><b>Run Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTER_RESULTS_DATATYPE__RUN_NAME = 0;

	/**
	 * The feature id for the '<em><b>Ontology Object</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTER_RESULTS_DATATYPE__ONTOLOGY_OBJECT = 1;

	/**
	 * The feature id for the '<em><b>Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTER_RESULTS_DATATYPE__PROJECT = 2;

	/**
	 * The number of structural features of the '<em>Tester Results Datatype</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTER_RESULTS_DATATYPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.tester.emf.model.impl.TesterResultsOperationImpl <em>Tester Results Operation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.impl.TesterResultsOperationImpl
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getTesterResultsOperation()
	 * @generated
	 */
	int TESTER_RESULTS_OPERATION = 14;

	/**
	 * The feature id for the '<em><b>Run Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTER_RESULTS_OPERATION__RUN_NAME = 0;

	/**
	 * The feature id for the '<em><b>Operation Object</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTER_RESULTS_OPERATION__OPERATION_OBJECT = 1;

	/**
	 * The feature id for the '<em><b>Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTER_RESULTS_OPERATION__PROJECT = 2;

	/**
	 * The number of structural features of the '<em>Tester Results Operation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTER_RESULTS_OPERATION_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.tester.emf.model.impl.TesterResultsTypeImpl <em>Tester Results Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.impl.TesterResultsTypeImpl
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getTesterResultsType()
	 * @generated
	 */
	int TESTER_RESULTS_TYPE = 15;

	/**
	 * The feature id for the '<em><b>Run Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTER_RESULTS_TYPE__RUN_NAME = 0;

	/**
	 * The feature id for the '<em><b>Execution Object</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTER_RESULTS_TYPE__EXECUTION_OBJECT = 1;

	/**
	 * The feature id for the '<em><b>Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTER_RESULTS_TYPE__PROJECT = 2;

	/**
	 * The number of structural features of the '<em>Tester Results Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTER_RESULTS_TYPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.tester.emf.model.impl.TesterRootImpl <em>Tester Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.impl.TesterRootImpl
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getTesterRoot()
	 * @generated
	 */
	int TESTER_ROOT = 16;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTER_ROOT__MIXED = 0;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTER_ROOT__XMLNS_PREFIX_MAP = 1;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTER_ROOT__XSI_SCHEMA_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Concept</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTER_ROOT__CONCEPT = 3;

	/**
	 * The feature id for the '<em><b>Event</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTER_ROOT__EVENT = 4;

	/**
	 * The feature id for the '<em><b>Execution Object</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTER_ROOT__EXECUTION_OBJECT = 5;

	/**
	 * The feature id for the '<em><b>New Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTER_ROOT__NEW_VALUE = 6;

	/**
	 * The feature id for the '<em><b>Old Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTER_ROOT__OLD_VALUE = 7;

	/**
	 * The feature id for the '<em><b>Operation Object</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTER_ROOT__OPERATION_OBJECT = 8;

	/**
	 * The feature id for the '<em><b>Rete Object</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTER_ROOT__RETE_OBJECT = 9;

	/**
	 * The feature id for the '<em><b>Tester Results</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTER_ROOT__TESTER_RESULTS = 10;

	/**
	 * The number of structural features of the '<em>Tester Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TESTER_ROOT_FEATURE_COUNT = 11;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.tester.emf.model.impl.ValueTypeImpl <em>Value Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ValueTypeImpl
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getValueType()
	 * @generated
	 */
	int VALUE_TYPE = 17;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_TYPE__VALUE = 0;

	/**
	 * The number of structural features of the '<em>Value Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.tester.emf.model.ChangeTypeType <em>Change Type Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.ChangeTypeType
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getChangeTypeType()
	 * @generated
	 */
	int CHANGE_TYPE_TYPE = 18;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.tester.emf.model.DataTypeType <em>Data Type Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.DataTypeType
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getDataTypeType()
	 * @generated
	 */
	int DATA_TYPE_TYPE = 19;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.tester.emf.model.DataTypeType1 <em>Data Type Type1</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.DataTypeType1
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getDataTypeType1()
	 * @generated
	 */
	int DATA_TYPE_TYPE1 = 20;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.tester.emf.model.OperationType <em>Operation Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.OperationType
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getOperationType()
	 * @generated
	 */
	int OPERATION_TYPE = 21;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.tester.emf.model.TypeType <em>Type Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.TypeType
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getTypeType()
	 * @generated
	 */
	int TYPE_TYPE = 22;

	/**
	 * The meta object id for the '<em>Change Type Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.ChangeTypeType
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getChangeTypeTypeObject()
	 * @generated
	 */
	int CHANGE_TYPE_TYPE_OBJECT = 23;

	/**
	 * The meta object id for the '<em>Data Type Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.DataTypeType
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getDataTypeTypeObject()
	 * @generated
	 */
	int DATA_TYPE_TYPE_OBJECT = 24;

	/**
	 * The meta object id for the '<em>Data Type Type Object1</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.DataTypeType1
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getDataTypeTypeObject1()
	 * @generated
	 */
	int DATA_TYPE_TYPE_OBJECT1 = 25;

	/**
	 * The meta object id for the '<em>Operation Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.OperationType
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getOperationTypeObject()
	 * @generated
	 */
	int OPERATION_TYPE_OBJECT = 26;

	/**
	 * The meta object id for the '<em>Type Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.tester.emf.model.TypeType
	 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getTypeTypeObject()
	 * @generated
	 */
	int TYPE_TYPE_OBJECT = 27;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.tester.emf.model.CasualObjectsType <em>Casual Objects Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Casual Objects Type</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.CasualObjectsType
	 * @generated
	 */
	EClass getCasualObjectsType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.tester.emf.model.CasualObjectsType#getConcept <em>Concept</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Concept</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.CasualObjectsType#getConcept()
	 * @see #getCasualObjectsType()
	 * @generated
	 */
	EReference getCasualObjectsType_Concept();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.tester.emf.model.CasualObjectsType#getEvent <em>Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Event</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.CasualObjectsType#getEvent()
	 * @see #getCasualObjectsType()
	 * @generated
	 */
	EReference getCasualObjectsType_Event();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.tester.emf.model.ConceptType <em>Concept Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Concept Type</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.ConceptType
	 * @generated
	 */
	EClass getConceptType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.tester.emf.model.ConceptType#isIsScorecard <em>Is Scorecard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Scorecard</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.ConceptType#isIsScorecard()
	 * @see #getConceptType()
	 * @generated
	 */
	EAttribute getConceptType_IsScorecard();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.tester.emf.model.EntityType <em>Entity Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity Type</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.EntityType
	 * @generated
	 */
	EClass getEntityType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.tester.emf.model.EntityType#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Property</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.EntityType#getProperty()
	 * @see #getEntityType()
	 * @generated
	 */
	EReference getEntityType_Property();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.tester.emf.model.EntityType#getModifiedProperty <em>Modified Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Modified Property</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.EntityType#getModifiedProperty()
	 * @see #getEntityType()
	 * @generated
	 */
	EReference getEntityType_ModifiedProperty();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.tester.emf.model.EntityType#getExtId <em>Ext Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ext Id</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.EntityType#getExtId()
	 * @see #getEntityType()
	 * @generated
	 */
	EAttribute getEntityType_ExtId();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.tester.emf.model.EntityType#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.EntityType#getId()
	 * @see #getEntityType()
	 * @generated
	 */
	EAttribute getEntityType_Id();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.tester.emf.model.EntityType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.EntityType#getName()
	 * @see #getEntityType()
	 * @generated
	 */
	EAttribute getEntityType_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.tester.emf.model.EntityType#getNamespace <em>Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Namespace</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.EntityType#getNamespace()
	 * @see #getEntityType()
	 * @generated
	 */
	EAttribute getEntityType_Namespace();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.tester.emf.model.EventType <em>Event Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event Type</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.EventType
	 * @generated
	 */
	EClass getEventType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.tester.emf.model.EventType#getPayload <em>Payload</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Payload</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.EventType#getPayload()
	 * @see #getEventType()
	 * @generated
	 */
	EAttribute getEventType_Payload();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.tester.emf.model.ExecutionObjectType <em>Execution Object Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Execution Object Type</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.ExecutionObjectType
	 * @generated
	 */
	EClass getExecutionObjectType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.tester.emf.model.ExecutionObjectType#getReteObject <em>Rete Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rete Object</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.ExecutionObjectType#getReteObject()
	 * @see #getExecutionObjectType()
	 * @generated
	 */
	EReference getExecutionObjectType_ReteObject();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.tester.emf.model.ExecutionObjectType#getInvocationObject <em>Invocation Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Invocation Object</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.ExecutionObjectType#getInvocationObject()
	 * @see #getExecutionObjectType()
	 * @generated
	 */
	EReference getExecutionObjectType_InvocationObject();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.tester.emf.model.ExecutionObjectType#getCasualObjects <em>Casual Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Casual Objects</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.ExecutionObjectType#getCasualObjects()
	 * @see #getExecutionObjectType()
	 * @generated
	 */
	EReference getExecutionObjectType_CasualObjects();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.tester.emf.model.InvocationObjectType <em>Invocation Object Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Invocation Object Type</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.InvocationObjectType
	 * @generated
	 */
	EClass getInvocationObjectType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.tester.emf.model.InvocationObjectType#getNamespace <em>Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Namespace</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.InvocationObjectType#getNamespace()
	 * @see #getInvocationObjectType()
	 * @generated
	 */
	EReference getInvocationObjectType_Namespace();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.tester.emf.model.InvocationObjectType#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.InvocationObjectType#getType()
	 * @see #getInvocationObjectType()
	 * @generated
	 */
	EAttribute getInvocationObjectType_Type();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.tester.emf.model.NamespaceType <em>Namespace Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Namespace Type</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.NamespaceType
	 * @generated
	 */
	EClass getNamespaceType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.tester.emf.model.NamespaceType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.NamespaceType#getValue()
	 * @see #getNamespaceType()
	 * @generated
	 */
	EAttribute getNamespaceType_Value();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.tester.emf.model.OntologyObjectType <em>Ontology Object Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ontology Object Type</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.OntologyObjectType
	 * @generated
	 */
	EClass getOntologyObjectType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.tester.emf.model.OntologyObjectType#getOperationObject <em>Operation Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Operation Object</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.OntologyObjectType#getOperationObject()
	 * @see #getOntologyObjectType()
	 * @generated
	 */
	EReference getOntologyObjectType_OperationObject();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.tester.emf.model.OntologyObjectType#getDataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Data Type</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.OntologyObjectType#getDataType()
	 * @see #getOntologyObjectType()
	 * @generated
	 */
	EAttribute getOntologyObjectType_DataType();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.tester.emf.model.OperationObjectType <em>Operation Object Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operation Object Type</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.OperationObjectType
	 * @generated
	 */
	EClass getOperationObjectType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.tester.emf.model.OperationObjectType#getExecutionObject <em>Execution Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Execution Object</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.OperationObjectType#getExecutionObject()
	 * @see #getOperationObjectType()
	 * @generated
	 */
	EReference getOperationObjectType_ExecutionObject();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.tester.emf.model.OperationObjectType#getOperation <em>Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operation</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.OperationObjectType#getOperation()
	 * @see #getOperationObjectType()
	 * @generated
	 */
	EAttribute getOperationObjectType_Operation();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.tester.emf.model.PropertyAttrsType <em>Property Attrs Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Attrs Type</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.PropertyAttrsType
	 * @generated
	 */
	EClass getPropertyAttrsType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.tester.emf.model.PropertyAttrsType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.PropertyAttrsType#getValue()
	 * @see #getPropertyAttrsType()
	 * @generated
	 */
	EAttribute getPropertyAttrsType_Value();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.tester.emf.model.PropertyAttrsType#getDataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Data Type</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.PropertyAttrsType#getDataType()
	 * @see #getPropertyAttrsType()
	 * @generated
	 */
	EAttribute getPropertyAttrsType_DataType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.tester.emf.model.PropertyAttrsType#isMultiple <em>Multiple</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Multiple</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.PropertyAttrsType#isMultiple()
	 * @see #getPropertyAttrsType()
	 * @generated
	 */
	EAttribute getPropertyAttrsType_Multiple();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.tester.emf.model.PropertyAttrsType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.PropertyAttrsType#getName()
	 * @see #getPropertyAttrsType()
	 * @generated
	 */
	EAttribute getPropertyAttrsType_Name();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.tester.emf.model.PropertyModificationType <em>Property Modification Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Modification Type</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.PropertyModificationType
	 * @generated
	 */
	EClass getPropertyModificationType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.tester.emf.model.PropertyModificationType#getOldValue <em>Old Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Old Value</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.PropertyModificationType#getOldValue()
	 * @see #getPropertyModificationType()
	 * @generated
	 */
	EReference getPropertyModificationType_OldValue();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.tester.emf.model.PropertyModificationType#getNewValue <em>New Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>New Value</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.PropertyModificationType#getNewValue()
	 * @see #getPropertyModificationType()
	 * @generated
	 */
	EReference getPropertyModificationType_NewValue();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.tester.emf.model.PropertyModificationType#getDataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Data Type</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.PropertyModificationType#getDataType()
	 * @see #getPropertyModificationType()
	 * @generated
	 */
	EAttribute getPropertyModificationType_DataType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.tester.emf.model.PropertyModificationType#isMultiple <em>Multiple</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Multiple</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.PropertyModificationType#isMultiple()
	 * @see #getPropertyModificationType()
	 * @generated
	 */
	EAttribute getPropertyModificationType_Multiple();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.tester.emf.model.PropertyModificationType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.PropertyModificationType#getName()
	 * @see #getPropertyModificationType()
	 * @generated
	 */
	EAttribute getPropertyModificationType_Name();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.tester.emf.model.PropertyType <em>Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Type</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.PropertyType
	 * @generated
	 */
	EClass getPropertyType();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.tester.emf.model.ReteObjectType <em>Rete Object Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rete Object Type</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.ReteObjectType
	 * @generated
	 */
	EClass getReteObjectType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.tester.emf.model.ReteObjectType#getChangeType <em>Change Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Change Type</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.ReteObjectType#getChangeType()
	 * @see #getReteObjectType()
	 * @generated
	 */
	EAttribute getReteObjectType_ChangeType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.tester.emf.model.ReteObjectType#getConcept <em>Concept</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Concept</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.ReteObjectType#getConcept()
	 * @see #getReteObjectType()
	 * @generated
	 */
	EReference getReteObjectType_Concept();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.tester.emf.model.ReteObjectType#getEvent <em>Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Event</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.ReteObjectType#getEvent()
	 * @see #getReteObjectType()
	 * @generated
	 */
	EReference getReteObjectType_Event();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.tester.emf.model.TesterResultsDatatype <em>Tester Results Datatype</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tester Results Datatype</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterResultsDatatype
	 * @generated
	 */
	EClass getTesterResultsDatatype();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.tester.emf.model.TesterResultsDatatype#getRunName <em>Run Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Run Name</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterResultsDatatype#getRunName()
	 * @see #getTesterResultsDatatype()
	 * @generated
	 */
	EAttribute getTesterResultsDatatype_RunName();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.tester.emf.model.TesterResultsDatatype#getOntologyObject <em>Ontology Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ontology Object</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterResultsDatatype#getOntologyObject()
	 * @see #getTesterResultsDatatype()
	 * @generated
	 */
	EReference getTesterResultsDatatype_OntologyObject();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.tester.emf.model.TesterResultsDatatype#getProject <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Project</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterResultsDatatype#getProject()
	 * @see #getTesterResultsDatatype()
	 * @generated
	 */
	EAttribute getTesterResultsDatatype_Project();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.tester.emf.model.TesterResultsOperation <em>Tester Results Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tester Results Operation</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterResultsOperation
	 * @generated
	 */
	EClass getTesterResultsOperation();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.tester.emf.model.TesterResultsOperation#getRunName <em>Run Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Run Name</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterResultsOperation#getRunName()
	 * @see #getTesterResultsOperation()
	 * @generated
	 */
	EAttribute getTesterResultsOperation_RunName();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.tester.emf.model.TesterResultsOperation#getOperationObject <em>Operation Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Operation Object</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterResultsOperation#getOperationObject()
	 * @see #getTesterResultsOperation()
	 * @generated
	 */
	EReference getTesterResultsOperation_OperationObject();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.tester.emf.model.TesterResultsOperation#getProject <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Project</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterResultsOperation#getProject()
	 * @see #getTesterResultsOperation()
	 * @generated
	 */
	EAttribute getTesterResultsOperation_Project();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.tester.emf.model.TesterResultsType <em>Tester Results Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tester Results Type</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterResultsType
	 * @generated
	 */
	EClass getTesterResultsType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.tester.emf.model.TesterResultsType#getRunName <em>Run Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Run Name</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterResultsType#getRunName()
	 * @see #getTesterResultsType()
	 * @generated
	 */
	EAttribute getTesterResultsType_RunName();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.tester.emf.model.TesterResultsType#getExecutionObject <em>Execution Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Execution Object</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterResultsType#getExecutionObject()
	 * @see #getTesterResultsType()
	 * @generated
	 */
	EReference getTesterResultsType_ExecutionObject();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.tester.emf.model.TesterResultsType#getProject <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Project</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterResultsType#getProject()
	 * @see #getTesterResultsType()
	 * @generated
	 */
	EAttribute getTesterResultsType_Project();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.tester.emf.model.TesterRoot <em>Tester Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tester Root</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterRoot
	 * @generated
	 */
	EClass getTesterRoot();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterRoot#getMixed()
	 * @see #getTesterRoot()
	 * @generated
	 */
	EAttribute getTesterRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterRoot#getXMLNSPrefixMap()
	 * @see #getTesterRoot()
	 * @generated
	 */
	EReference getTesterRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterRoot#getXSISchemaLocation()
	 * @see #getTesterRoot()
	 * @generated
	 */
	EReference getTesterRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getConcept <em>Concept</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Concept</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterRoot#getConcept()
	 * @see #getTesterRoot()
	 * @generated
	 */
	EReference getTesterRoot_Concept();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getEvent <em>Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Event</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterRoot#getEvent()
	 * @see #getTesterRoot()
	 * @generated
	 */
	EReference getTesterRoot_Event();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getExecutionObject <em>Execution Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Execution Object</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterRoot#getExecutionObject()
	 * @see #getTesterRoot()
	 * @generated
	 */
	EReference getTesterRoot_ExecutionObject();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getNewValue <em>New Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>New Value</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterRoot#getNewValue()
	 * @see #getTesterRoot()
	 * @generated
	 */
	EReference getTesterRoot_NewValue();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getOldValue <em>Old Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Old Value</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterRoot#getOldValue()
	 * @see #getTesterRoot()
	 * @generated
	 */
	EReference getTesterRoot_OldValue();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getOperationObject <em>Operation Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Operation Object</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterRoot#getOperationObject()
	 * @see #getTesterRoot()
	 * @generated
	 */
	EReference getTesterRoot_OperationObject();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getReteObject <em>Rete Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rete Object</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterRoot#getReteObject()
	 * @see #getTesterRoot()
	 * @generated
	 */
	EReference getTesterRoot_ReteObject();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getTesterResults <em>Tester Results</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Tester Results</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterRoot#getTesterResults()
	 * @see #getTesterRoot()
	 * @generated
	 */
	EReference getTesterRoot_TesterResults();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.tester.emf.model.ValueType <em>Value Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Value Type</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.ValueType
	 * @generated
	 */
	EClass getValueType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.tester.emf.model.ValueType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.ValueType#getValue()
	 * @see #getValueType()
	 * @generated
	 */
	EAttribute getValueType_Value();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.studio.tester.emf.model.ChangeTypeType <em>Change Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Change Type Type</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.ChangeTypeType
	 * @generated
	 */
	EEnum getChangeTypeType();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.studio.tester.emf.model.DataTypeType <em>Data Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Data Type Type</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.DataTypeType
	 * @generated
	 */
	EEnum getDataTypeType();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.studio.tester.emf.model.DataTypeType1 <em>Data Type Type1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Data Type Type1</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.DataTypeType1
	 * @generated
	 */
	EEnum getDataTypeType1();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.studio.tester.emf.model.OperationType <em>Operation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Operation Type</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.OperationType
	 * @generated
	 */
	EEnum getOperationType();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.studio.tester.emf.model.TypeType <em>Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Type Type</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.TypeType
	 * @generated
	 */
	EEnum getTypeType();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.studio.tester.emf.model.ChangeTypeType <em>Change Type Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Change Type Type Object</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.ChangeTypeType
	 * @model instanceClass="com.tibco.cep.studio.tester.emf.model.ChangeTypeType"
	 *        extendedMetaData="name='ChangeType_._type:Object' baseType='ChangeType_._type'"
	 * @generated
	 */
	EDataType getChangeTypeTypeObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.studio.tester.emf.model.DataTypeType <em>Data Type Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Data Type Type Object</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.DataTypeType
	 * @model instanceClass="com.tibco.cep.studio.tester.emf.model.DataTypeType"
	 *        extendedMetaData="name='dataType_._type:Object' baseType='dataType_._type'"
	 * @generated
	 */
	EDataType getDataTypeTypeObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.studio.tester.emf.model.DataTypeType1 <em>Data Type Type Object1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Data Type Type Object1</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.DataTypeType1
	 * @model instanceClass="com.tibco.cep.studio.tester.emf.model.DataTypeType1"
	 *        extendedMetaData="name='dataType_._1_._type:Object' baseType='dataType_._1_._type'"
	 * @generated
	 */
	EDataType getDataTypeTypeObject1();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.studio.tester.emf.model.OperationType <em>Operation Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Operation Type Object</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.OperationType
	 * @model instanceClass="com.tibco.cep.studio.tester.emf.model.OperationType"
	 *        extendedMetaData="name='OperationType:Object' baseType='OperationType'"
	 * @generated
	 */
	EDataType getOperationTypeObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.studio.tester.emf.model.TypeType <em>Type Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Type Type Object</em>'.
	 * @see com.tibco.cep.studio.tester.emf.model.TypeType
	 * @model instanceClass="com.tibco.cep.studio.tester.emf.model.TypeType"
	 *        extendedMetaData="name='Type_._type:Object' baseType='Type_._type'"
	 * @generated
	 */
	EDataType getTypeTypeObject();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ModelFactory getModelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.tester.emf.model.impl.CasualObjectsTypeImpl <em>Casual Objects Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.impl.CasualObjectsTypeImpl
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getCasualObjectsType()
		 * @generated
		 */
		EClass CASUAL_OBJECTS_TYPE = eINSTANCE.getCasualObjectsType();

		/**
		 * The meta object literal for the '<em><b>Concept</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CASUAL_OBJECTS_TYPE__CONCEPT = eINSTANCE.getCasualObjectsType_Concept();

		/**
		 * The meta object literal for the '<em><b>Event</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CASUAL_OBJECTS_TYPE__EVENT = eINSTANCE.getCasualObjectsType_Event();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.tester.emf.model.impl.ConceptTypeImpl <em>Concept Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ConceptTypeImpl
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getConceptType()
		 * @generated
		 */
		EClass CONCEPT_TYPE = eINSTANCE.getConceptType();

		/**
		 * The meta object literal for the '<em><b>Is Scorecard</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONCEPT_TYPE__IS_SCORECARD = eINSTANCE.getConceptType_IsScorecard();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.tester.emf.model.impl.EntityTypeImpl <em>Entity Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.impl.EntityTypeImpl
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getEntityType()
		 * @generated
		 */
		EClass ENTITY_TYPE = eINSTANCE.getEntityType();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY_TYPE__PROPERTY = eINSTANCE.getEntityType_Property();

		/**
		 * The meta object literal for the '<em><b>Modified Property</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY_TYPE__MODIFIED_PROPERTY = eINSTANCE.getEntityType_ModifiedProperty();

		/**
		 * The meta object literal for the '<em><b>Ext Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITY_TYPE__EXT_ID = eINSTANCE.getEntityType_ExtId();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITY_TYPE__ID = eINSTANCE.getEntityType_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITY_TYPE__NAME = eINSTANCE.getEntityType_Name();

		/**
		 * The meta object literal for the '<em><b>Namespace</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITY_TYPE__NAMESPACE = eINSTANCE.getEntityType_Namespace();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.tester.emf.model.impl.EventTypeImpl <em>Event Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.impl.EventTypeImpl
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getEventType()
		 * @generated
		 */
		EClass EVENT_TYPE = eINSTANCE.getEventType();

		/**
		 * The meta object literal for the '<em><b>Payload</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT_TYPE__PAYLOAD = eINSTANCE.getEventType_Payload();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.tester.emf.model.impl.ExecutionObjectTypeImpl <em>Execution Object Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ExecutionObjectTypeImpl
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getExecutionObjectType()
		 * @generated
		 */
		EClass EXECUTION_OBJECT_TYPE = eINSTANCE.getExecutionObjectType();

		/**
		 * The meta object literal for the '<em><b>Rete Object</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXECUTION_OBJECT_TYPE__RETE_OBJECT = eINSTANCE.getExecutionObjectType_ReteObject();

		/**
		 * The meta object literal for the '<em><b>Invocation Object</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXECUTION_OBJECT_TYPE__INVOCATION_OBJECT = eINSTANCE.getExecutionObjectType_InvocationObject();

		/**
		 * The meta object literal for the '<em><b>Casual Objects</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXECUTION_OBJECT_TYPE__CASUAL_OBJECTS = eINSTANCE.getExecutionObjectType_CasualObjects();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.tester.emf.model.impl.InvocationObjectTypeImpl <em>Invocation Object Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.impl.InvocationObjectTypeImpl
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getInvocationObjectType()
		 * @generated
		 */
		EClass INVOCATION_OBJECT_TYPE = eINSTANCE.getInvocationObjectType();

		/**
		 * The meta object literal for the '<em><b>Namespace</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INVOCATION_OBJECT_TYPE__NAMESPACE = eINSTANCE.getInvocationObjectType_Namespace();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INVOCATION_OBJECT_TYPE__TYPE = eINSTANCE.getInvocationObjectType_Type();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.tester.emf.model.impl.NamespaceTypeImpl <em>Namespace Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.impl.NamespaceTypeImpl
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getNamespaceType()
		 * @generated
		 */
		EClass NAMESPACE_TYPE = eINSTANCE.getNamespaceType();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMESPACE_TYPE__VALUE = eINSTANCE.getNamespaceType_Value();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.tester.emf.model.impl.OntologyObjectTypeImpl <em>Ontology Object Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.impl.OntologyObjectTypeImpl
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getOntologyObjectType()
		 * @generated
		 */
		EClass ONTOLOGY_OBJECT_TYPE = eINSTANCE.getOntologyObjectType();

		/**
		 * The meta object literal for the '<em><b>Operation Object</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ONTOLOGY_OBJECT_TYPE__OPERATION_OBJECT = eINSTANCE.getOntologyObjectType_OperationObject();

		/**
		 * The meta object literal for the '<em><b>Data Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ONTOLOGY_OBJECT_TYPE__DATA_TYPE = eINSTANCE.getOntologyObjectType_DataType();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.tester.emf.model.impl.OperationObjectTypeImpl <em>Operation Object Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.impl.OperationObjectTypeImpl
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getOperationObjectType()
		 * @generated
		 */
		EClass OPERATION_OBJECT_TYPE = eINSTANCE.getOperationObjectType();

		/**
		 * The meta object literal for the '<em><b>Execution Object</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATION_OBJECT_TYPE__EXECUTION_OBJECT = eINSTANCE.getOperationObjectType_ExecutionObject();

		/**
		 * The meta object literal for the '<em><b>Operation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPERATION_OBJECT_TYPE__OPERATION = eINSTANCE.getOperationObjectType_Operation();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.tester.emf.model.impl.PropertyAttrsTypeImpl <em>Property Attrs Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.impl.PropertyAttrsTypeImpl
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getPropertyAttrsType()
		 * @generated
		 */
		EClass PROPERTY_ATTRS_TYPE = eINSTANCE.getPropertyAttrsType();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_ATTRS_TYPE__VALUE = eINSTANCE.getPropertyAttrsType_Value();

		/**
		 * The meta object literal for the '<em><b>Data Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_ATTRS_TYPE__DATA_TYPE = eINSTANCE.getPropertyAttrsType_DataType();

		/**
		 * The meta object literal for the '<em><b>Multiple</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_ATTRS_TYPE__MULTIPLE = eINSTANCE.getPropertyAttrsType_Multiple();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_ATTRS_TYPE__NAME = eINSTANCE.getPropertyAttrsType_Name();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.tester.emf.model.impl.PropertyModificationTypeImpl <em>Property Modification Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.impl.PropertyModificationTypeImpl
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getPropertyModificationType()
		 * @generated
		 */
		EClass PROPERTY_MODIFICATION_TYPE = eINSTANCE.getPropertyModificationType();

		/**
		 * The meta object literal for the '<em><b>Old Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_MODIFICATION_TYPE__OLD_VALUE = eINSTANCE.getPropertyModificationType_OldValue();

		/**
		 * The meta object literal for the '<em><b>New Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_MODIFICATION_TYPE__NEW_VALUE = eINSTANCE.getPropertyModificationType_NewValue();

		/**
		 * The meta object literal for the '<em><b>Data Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_MODIFICATION_TYPE__DATA_TYPE = eINSTANCE.getPropertyModificationType_DataType();

		/**
		 * The meta object literal for the '<em><b>Multiple</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_MODIFICATION_TYPE__MULTIPLE = eINSTANCE.getPropertyModificationType_Multiple();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_MODIFICATION_TYPE__NAME = eINSTANCE.getPropertyModificationType_Name();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.tester.emf.model.impl.PropertyTypeImpl <em>Property Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.impl.PropertyTypeImpl
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getPropertyType()
		 * @generated
		 */
		EClass PROPERTY_TYPE = eINSTANCE.getPropertyType();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.tester.emf.model.impl.ReteObjectTypeImpl <em>Rete Object Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ReteObjectTypeImpl
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getReteObjectType()
		 * @generated
		 */
		EClass RETE_OBJECT_TYPE = eINSTANCE.getReteObjectType();

		/**
		 * The meta object literal for the '<em><b>Change Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RETE_OBJECT_TYPE__CHANGE_TYPE = eINSTANCE.getReteObjectType_ChangeType();

		/**
		 * The meta object literal for the '<em><b>Concept</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RETE_OBJECT_TYPE__CONCEPT = eINSTANCE.getReteObjectType_Concept();

		/**
		 * The meta object literal for the '<em><b>Event</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RETE_OBJECT_TYPE__EVENT = eINSTANCE.getReteObjectType_Event();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.tester.emf.model.impl.TesterResultsDatatypeImpl <em>Tester Results Datatype</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.impl.TesterResultsDatatypeImpl
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getTesterResultsDatatype()
		 * @generated
		 */
		EClass TESTER_RESULTS_DATATYPE = eINSTANCE.getTesterResultsDatatype();

		/**
		 * The meta object literal for the '<em><b>Run Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TESTER_RESULTS_DATATYPE__RUN_NAME = eINSTANCE.getTesterResultsDatatype_RunName();

		/**
		 * The meta object literal for the '<em><b>Ontology Object</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TESTER_RESULTS_DATATYPE__ONTOLOGY_OBJECT = eINSTANCE.getTesterResultsDatatype_OntologyObject();

		/**
		 * The meta object literal for the '<em><b>Project</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TESTER_RESULTS_DATATYPE__PROJECT = eINSTANCE.getTesterResultsDatatype_Project();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.tester.emf.model.impl.TesterResultsOperationImpl <em>Tester Results Operation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.impl.TesterResultsOperationImpl
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getTesterResultsOperation()
		 * @generated
		 */
		EClass TESTER_RESULTS_OPERATION = eINSTANCE.getTesterResultsOperation();

		/**
		 * The meta object literal for the '<em><b>Run Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TESTER_RESULTS_OPERATION__RUN_NAME = eINSTANCE.getTesterResultsOperation_RunName();

		/**
		 * The meta object literal for the '<em><b>Operation Object</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TESTER_RESULTS_OPERATION__OPERATION_OBJECT = eINSTANCE.getTesterResultsOperation_OperationObject();

		/**
		 * The meta object literal for the '<em><b>Project</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TESTER_RESULTS_OPERATION__PROJECT = eINSTANCE.getTesterResultsOperation_Project();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.tester.emf.model.impl.TesterResultsTypeImpl <em>Tester Results Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.impl.TesterResultsTypeImpl
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getTesterResultsType()
		 * @generated
		 */
		EClass TESTER_RESULTS_TYPE = eINSTANCE.getTesterResultsType();

		/**
		 * The meta object literal for the '<em><b>Run Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TESTER_RESULTS_TYPE__RUN_NAME = eINSTANCE.getTesterResultsType_RunName();

		/**
		 * The meta object literal for the '<em><b>Execution Object</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TESTER_RESULTS_TYPE__EXECUTION_OBJECT = eINSTANCE.getTesterResultsType_ExecutionObject();

		/**
		 * The meta object literal for the '<em><b>Project</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TESTER_RESULTS_TYPE__PROJECT = eINSTANCE.getTesterResultsType_Project();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.tester.emf.model.impl.TesterRootImpl <em>Tester Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.impl.TesterRootImpl
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getTesterRoot()
		 * @generated
		 */
		EClass TESTER_ROOT = eINSTANCE.getTesterRoot();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TESTER_ROOT__MIXED = eINSTANCE.getTesterRoot_Mixed();

		/**
		 * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TESTER_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getTesterRoot_XMLNSPrefixMap();

		/**
		 * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TESTER_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getTesterRoot_XSISchemaLocation();

		/**
		 * The meta object literal for the '<em><b>Concept</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TESTER_ROOT__CONCEPT = eINSTANCE.getTesterRoot_Concept();

		/**
		 * The meta object literal for the '<em><b>Event</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TESTER_ROOT__EVENT = eINSTANCE.getTesterRoot_Event();

		/**
		 * The meta object literal for the '<em><b>Execution Object</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TESTER_ROOT__EXECUTION_OBJECT = eINSTANCE.getTesterRoot_ExecutionObject();

		/**
		 * The meta object literal for the '<em><b>New Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TESTER_ROOT__NEW_VALUE = eINSTANCE.getTesterRoot_NewValue();

		/**
		 * The meta object literal for the '<em><b>Old Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TESTER_ROOT__OLD_VALUE = eINSTANCE.getTesterRoot_OldValue();

		/**
		 * The meta object literal for the '<em><b>Operation Object</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TESTER_ROOT__OPERATION_OBJECT = eINSTANCE.getTesterRoot_OperationObject();

		/**
		 * The meta object literal for the '<em><b>Rete Object</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TESTER_ROOT__RETE_OBJECT = eINSTANCE.getTesterRoot_ReteObject();

		/**
		 * The meta object literal for the '<em><b>Tester Results</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TESTER_ROOT__TESTER_RESULTS = eINSTANCE.getTesterRoot_TesterResults();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.tester.emf.model.impl.ValueTypeImpl <em>Value Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ValueTypeImpl
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getValueType()
		 * @generated
		 */
		EClass VALUE_TYPE = eINSTANCE.getValueType();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALUE_TYPE__VALUE = eINSTANCE.getValueType_Value();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.tester.emf.model.ChangeTypeType <em>Change Type Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.ChangeTypeType
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getChangeTypeType()
		 * @generated
		 */
		EEnum CHANGE_TYPE_TYPE = eINSTANCE.getChangeTypeType();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.tester.emf.model.DataTypeType <em>Data Type Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.DataTypeType
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getDataTypeType()
		 * @generated
		 */
		EEnum DATA_TYPE_TYPE = eINSTANCE.getDataTypeType();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.tester.emf.model.DataTypeType1 <em>Data Type Type1</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.DataTypeType1
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getDataTypeType1()
		 * @generated
		 */
		EEnum DATA_TYPE_TYPE1 = eINSTANCE.getDataTypeType1();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.tester.emf.model.OperationType <em>Operation Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.OperationType
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getOperationType()
		 * @generated
		 */
		EEnum OPERATION_TYPE = eINSTANCE.getOperationType();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.tester.emf.model.TypeType <em>Type Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.TypeType
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getTypeType()
		 * @generated
		 */
		EEnum TYPE_TYPE = eINSTANCE.getTypeType();

		/**
		 * The meta object literal for the '<em>Change Type Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.ChangeTypeType
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getChangeTypeTypeObject()
		 * @generated
		 */
		EDataType CHANGE_TYPE_TYPE_OBJECT = eINSTANCE.getChangeTypeTypeObject();

		/**
		 * The meta object literal for the '<em>Data Type Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.DataTypeType
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getDataTypeTypeObject()
		 * @generated
		 */
		EDataType DATA_TYPE_TYPE_OBJECT = eINSTANCE.getDataTypeTypeObject();

		/**
		 * The meta object literal for the '<em>Data Type Type Object1</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.DataTypeType1
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getDataTypeTypeObject1()
		 * @generated
		 */
		EDataType DATA_TYPE_TYPE_OBJECT1 = eINSTANCE.getDataTypeTypeObject1();

		/**
		 * The meta object literal for the '<em>Operation Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.OperationType
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getOperationTypeObject()
		 * @generated
		 */
		EDataType OPERATION_TYPE_OBJECT = eINSTANCE.getOperationTypeObject();

		/**
		 * The meta object literal for the '<em>Type Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.tester.emf.model.TypeType
		 * @see com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl#getTypeTypeObject()
		 * @generated
		 */
		EDataType TYPE_TYPE_OBJECT = eINSTANCE.getTypeTypeObject();

	}

} //ModelPackage
