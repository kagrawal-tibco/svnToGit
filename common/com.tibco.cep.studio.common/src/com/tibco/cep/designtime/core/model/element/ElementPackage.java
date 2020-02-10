/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.element;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import com.tibco.cep.designtime.core.model.ModelPackage;

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
 * @see com.tibco.cep.designtime.core.model.element.ElementFactory
 * @model kind="package"
 * @generated
 */
public interface ElementPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "element";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/designtime/core/model/element";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "element";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ElementPackage eINSTANCE = com.tibco.cep.designtime.core.model.element.impl.ElementPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.element.impl.ConceptImpl <em>Concept</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.element.impl.ConceptImpl
	 * @see com.tibco.cep.designtime.core.model.element.impl.ElementPackageImpl#getConcept()
	 * @generated
	 */
	int CONCEPT = 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__NAMESPACE = ModelPackage.RULE_PARTICIPANT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__FOLDER = ModelPackage.RULE_PARTICIPANT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__NAME = ModelPackage.RULE_PARTICIPANT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__DESCRIPTION = ModelPackage.RULE_PARTICIPANT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__LAST_MODIFIED = ModelPackage.RULE_PARTICIPANT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__GUID = ModelPackage.RULE_PARTICIPANT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__ONTOLOGY = ModelPackage.RULE_PARTICIPANT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__EXTENDED_PROPERTIES = ModelPackage.RULE_PARTICIPANT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__HIDDEN_PROPERTIES = ModelPackage.RULE_PARTICIPANT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__TRANSIENT_PROPERTIES = ModelPackage.RULE_PARTICIPANT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__OWNER_PROJECT_NAME = ModelPackage.RULE_PARTICIPANT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Sub Concepts</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__SUB_CONCEPTS = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__PROPERTIES = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Rule Set</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__RULE_SET = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>State Machine Paths</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__STATE_MACHINE_PATHS = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Root State Machine Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__ROOT_STATE_MACHINE_PATH = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Parent Concept</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__PARENT_CONCEPT = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Parent Concept Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__PARENT_CONCEPT_PATH = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Super Concept</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__SUPER_CONCEPT = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Super Concept Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__SUPER_CONCEPT_PATH = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Scorecard</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__SCORECARD = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Metric</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__METRIC = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Contained</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__CONTAINED = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>POJO</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__POJO = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Transient</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__TRANSIENT = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Impl Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__IMPL_CLASS = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Sub Concepts Path</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__SUB_CONCEPTS_PATH = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Auto Start State Machine</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__AUTO_START_STATE_MACHINE = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 16;

	/**
	 * The number of structural features of the '<em>Concept</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT_FEATURE_COUNT = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 17;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.element.impl.ScorecardImpl <em>Scorecard</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.element.impl.ScorecardImpl
	 * @see com.tibco.cep.designtime.core.model.element.impl.ElementPackageImpl#getScorecard()
	 * @generated
	 */
	int SCORECARD = 1;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__NAMESPACE = CONCEPT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__FOLDER = CONCEPT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__NAME = CONCEPT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__DESCRIPTION = CONCEPT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__LAST_MODIFIED = CONCEPT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__GUID = CONCEPT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__ONTOLOGY = CONCEPT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__EXTENDED_PROPERTIES = CONCEPT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__HIDDEN_PROPERTIES = CONCEPT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__TRANSIENT_PROPERTIES = CONCEPT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__OWNER_PROJECT_NAME = CONCEPT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Sub Concepts</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__SUB_CONCEPTS = CONCEPT__SUB_CONCEPTS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__PROPERTIES = CONCEPT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Rule Set</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__RULE_SET = CONCEPT__RULE_SET;

	/**
	 * The feature id for the '<em><b>State Machine Paths</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__STATE_MACHINE_PATHS = CONCEPT__STATE_MACHINE_PATHS;

	/**
	 * The feature id for the '<em><b>Root State Machine Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__ROOT_STATE_MACHINE_PATH = CONCEPT__ROOT_STATE_MACHINE_PATH;

	/**
	 * The feature id for the '<em><b>Parent Concept</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__PARENT_CONCEPT = CONCEPT__PARENT_CONCEPT;

	/**
	 * The feature id for the '<em><b>Parent Concept Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__PARENT_CONCEPT_PATH = CONCEPT__PARENT_CONCEPT_PATH;

	/**
	 * The feature id for the '<em><b>Super Concept</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__SUPER_CONCEPT = CONCEPT__SUPER_CONCEPT;

	/**
	 * The feature id for the '<em><b>Super Concept Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__SUPER_CONCEPT_PATH = CONCEPT__SUPER_CONCEPT_PATH;

	/**
	 * The feature id for the '<em><b>Scorecard</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__SCORECARD = CONCEPT__SCORECARD;

	/**
	 * The feature id for the '<em><b>Metric</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__METRIC = CONCEPT__METRIC;

	/**
	 * The feature id for the '<em><b>Contained</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__CONTAINED = CONCEPT__CONTAINED;

	/**
	 * The feature id for the '<em><b>POJO</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__POJO = CONCEPT__POJO;

	/**
	 * The feature id for the '<em><b>Transient</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__TRANSIENT = CONCEPT__TRANSIENT;

	/**
	 * The feature id for the '<em><b>Impl Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__IMPL_CLASS = CONCEPT__IMPL_CLASS;

	/**
	 * The feature id for the '<em><b>Sub Concepts Path</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__SUB_CONCEPTS_PATH = CONCEPT__SUB_CONCEPTS_PATH;

	/**
	 * The feature id for the '<em><b>Auto Start State Machine</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD__AUTO_START_STATE_MACHINE = CONCEPT__AUTO_START_STATE_MACHINE;

	/**
	 * The number of structural features of the '<em>Scorecard</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCORECARD_FEATURE_COUNT = CONCEPT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl <em>Property Definition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl
	 * @see com.tibco.cep.designtime.core.model.element.impl.ElementPackageImpl#getPropertyDefinition()
	 * @generated
	 */
	int PROPERTY_DEFINITION = 2;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__NAMESPACE = ModelPackage.ENTITY__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__FOLDER = ModelPackage.ENTITY__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__NAME = ModelPackage.ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__DESCRIPTION = ModelPackage.ENTITY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__LAST_MODIFIED = ModelPackage.ENTITY__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__GUID = ModelPackage.ENTITY__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__ONTOLOGY = ModelPackage.ENTITY__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__EXTENDED_PROPERTIES = ModelPackage.ENTITY__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__HIDDEN_PROPERTIES = ModelPackage.ENTITY__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__TRANSIENT_PROPERTIES = ModelPackage.ENTITY__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__OWNER_PROJECT_NAME = ModelPackage.ENTITY__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__TYPE = ModelPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Concept Type Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__CONCEPT_TYPE_PATH = ModelPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__ARRAY = ModelPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Owner Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__OWNER_PATH = ModelPackage.ENTITY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>History Policy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__HISTORY_POLICY = ModelPackage.ENTITY_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>History Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__HISTORY_SIZE = ModelPackage.ENTITY_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Order</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__ORDER = ModelPackage.ENTITY_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__DEFAULT_VALUE = ModelPackage.ENTITY_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Disjoint Set</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__DISJOINT_SET = ModelPackage.ENTITY_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Super</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__SUPER = ModelPackage.ENTITY_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__PARENT = ModelPackage.ENTITY_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Transitive</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__TRANSITIVE = ModelPackage.ENTITY_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Equivalent Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__EQUIVALENT_PROPERTIES = ModelPackage.ENTITY_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Attribute Definitions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__ATTRIBUTE_DEFINITIONS = ModelPackage.ENTITY_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Aggregation Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__AGGREGATION_TYPE = ModelPackage.ENTITY_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Group By Field</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__GROUP_BY_FIELD = ModelPackage.ENTITY_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Group By Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__GROUP_BY_POSITION = ModelPackage.ENTITY_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Time Window Field</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__TIME_WINDOW_FIELD = ModelPackage.ENTITY_FEATURE_COUNT + 17;

	/**
	 * The feature id for the '<em><b>Domain Instances</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION__DOMAIN_INSTANCES = ModelPackage.ENTITY_FEATURE_COUNT + 18;

	/**
	 * The number of structural features of the '<em>Property Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEFINITION_FEATURE_COUNT = ModelPackage.ENTITY_FEATURE_COUNT + 19;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.element.impl.AttributeDefinitionImpl <em>Attribute Definition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.element.impl.AttributeDefinitionImpl
	 * @see com.tibco.cep.designtime.core.model.element.impl.ElementPackageImpl#getAttributeDefinition()
	 * @generated
	 */
	int ATTRIBUTE_DEFINITION = 3;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__NAMESPACE = PROPERTY_DEFINITION__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__FOLDER = PROPERTY_DEFINITION__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__NAME = PROPERTY_DEFINITION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__DESCRIPTION = PROPERTY_DEFINITION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__LAST_MODIFIED = PROPERTY_DEFINITION__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__GUID = PROPERTY_DEFINITION__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__ONTOLOGY = PROPERTY_DEFINITION__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__EXTENDED_PROPERTIES = PROPERTY_DEFINITION__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__HIDDEN_PROPERTIES = PROPERTY_DEFINITION__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__TRANSIENT_PROPERTIES = PROPERTY_DEFINITION__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__OWNER_PROJECT_NAME = PROPERTY_DEFINITION__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__TYPE = PROPERTY_DEFINITION__TYPE;

	/**
	 * The feature id for the '<em><b>Concept Type Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__CONCEPT_TYPE_PATH = PROPERTY_DEFINITION__CONCEPT_TYPE_PATH;

	/**
	 * The feature id for the '<em><b>Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__ARRAY = PROPERTY_DEFINITION__ARRAY;

	/**
	 * The feature id for the '<em><b>Owner Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__OWNER_PATH = PROPERTY_DEFINITION__OWNER_PATH;

	/**
	 * The feature id for the '<em><b>History Policy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__HISTORY_POLICY = PROPERTY_DEFINITION__HISTORY_POLICY;

	/**
	 * The feature id for the '<em><b>History Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__HISTORY_SIZE = PROPERTY_DEFINITION__HISTORY_SIZE;

	/**
	 * The feature id for the '<em><b>Order</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__ORDER = PROPERTY_DEFINITION__ORDER;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__DEFAULT_VALUE = PROPERTY_DEFINITION__DEFAULT_VALUE;

	/**
	 * The feature id for the '<em><b>Disjoint Set</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__DISJOINT_SET = PROPERTY_DEFINITION__DISJOINT_SET;

	/**
	 * The feature id for the '<em><b>Super</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__SUPER = PROPERTY_DEFINITION__SUPER;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__PARENT = PROPERTY_DEFINITION__PARENT;

	/**
	 * The feature id for the '<em><b>Transitive</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__TRANSITIVE = PROPERTY_DEFINITION__TRANSITIVE;

	/**
	 * The feature id for the '<em><b>Equivalent Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__EQUIVALENT_PROPERTIES = PROPERTY_DEFINITION__EQUIVALENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Attribute Definitions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__ATTRIBUTE_DEFINITIONS = PROPERTY_DEFINITION__ATTRIBUTE_DEFINITIONS;

	/**
	 * The feature id for the '<em><b>Aggregation Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__AGGREGATION_TYPE = PROPERTY_DEFINITION__AGGREGATION_TYPE;

	/**
	 * The feature id for the '<em><b>Group By Field</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__GROUP_BY_FIELD = PROPERTY_DEFINITION__GROUP_BY_FIELD;

	/**
	 * The feature id for the '<em><b>Group By Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__GROUP_BY_POSITION = PROPERTY_DEFINITION__GROUP_BY_POSITION;

	/**
	 * The feature id for the '<em><b>Time Window Field</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__TIME_WINDOW_FIELD = PROPERTY_DEFINITION__TIME_WINDOW_FIELD;

	/**
	 * The feature id for the '<em><b>Domain Instances</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION__DOMAIN_INSTANCES = PROPERTY_DEFINITION__DOMAIN_INSTANCES;

	/**
	 * The number of structural features of the '<em>Attribute Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEFINITION_FEATURE_COUNT = PROPERTY_DEFINITION_FEATURE_COUNT + 0;


	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.element.impl.MetricImpl <em>Metric</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.element.impl.MetricImpl
	 * @see com.tibco.cep.designtime.core.model.element.impl.ElementPackageImpl#getMetric()
	 * @generated
	 */
	int METRIC = 4;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__NAMESPACE = CONCEPT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__FOLDER = CONCEPT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__NAME = CONCEPT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__DESCRIPTION = CONCEPT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__LAST_MODIFIED = CONCEPT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__GUID = CONCEPT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__ONTOLOGY = CONCEPT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__EXTENDED_PROPERTIES = CONCEPT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__HIDDEN_PROPERTIES = CONCEPT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__TRANSIENT_PROPERTIES = CONCEPT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__OWNER_PROJECT_NAME = CONCEPT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Sub Concepts</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__SUB_CONCEPTS = CONCEPT__SUB_CONCEPTS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__PROPERTIES = CONCEPT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Rule Set</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__RULE_SET = CONCEPT__RULE_SET;

	/**
	 * The feature id for the '<em><b>State Machine Paths</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__STATE_MACHINE_PATHS = CONCEPT__STATE_MACHINE_PATHS;

	/**
	 * The feature id for the '<em><b>Root State Machine Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__ROOT_STATE_MACHINE_PATH = CONCEPT__ROOT_STATE_MACHINE_PATH;

	/**
	 * The feature id for the '<em><b>Parent Concept</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__PARENT_CONCEPT = CONCEPT__PARENT_CONCEPT;

	/**
	 * The feature id for the '<em><b>Parent Concept Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__PARENT_CONCEPT_PATH = CONCEPT__PARENT_CONCEPT_PATH;

	/**
	 * The feature id for the '<em><b>Super Concept</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__SUPER_CONCEPT = CONCEPT__SUPER_CONCEPT;

	/**
	 * The feature id for the '<em><b>Super Concept Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__SUPER_CONCEPT_PATH = CONCEPT__SUPER_CONCEPT_PATH;

	/**
	 * The feature id for the '<em><b>Scorecard</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__SCORECARD = CONCEPT__SCORECARD;

	/**
	 * The feature id for the '<em><b>Metric</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__METRIC = CONCEPT__METRIC;

	/**
	 * The feature id for the '<em><b>Contained</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__CONTAINED = CONCEPT__CONTAINED;

	/**
	 * The feature id for the '<em><b>POJO</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__POJO = CONCEPT__POJO;

	/**
	 * The feature id for the '<em><b>Transient</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__TRANSIENT = CONCEPT__TRANSIENT;

	/**
	 * The feature id for the '<em><b>Impl Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__IMPL_CLASS = CONCEPT__IMPL_CLASS;

	/**
	 * The feature id for the '<em><b>Sub Concepts Path</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__SUB_CONCEPTS_PATH = CONCEPT__SUB_CONCEPTS_PATH;

	/**
	 * The feature id for the '<em><b>Auto Start State Machine</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__AUTO_START_STATE_MACHINE = CONCEPT__AUTO_START_STATE_MACHINE;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__TYPE = CONCEPT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Window Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__WINDOW_TYPE = CONCEPT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Window Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__WINDOW_SIZE = CONCEPT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Recurring Time Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__RECURRING_TIME_TYPE = CONCEPT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Recurring Frequency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__RECURRING_FREQUENCY = CONCEPT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Start Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__START_TIME = CONCEPT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__SHORT_NAME = CONCEPT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Persistent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__PERSISTENT = CONCEPT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Transactional Rolling</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__TRANSACTIONAL_ROLLING = CONCEPT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__ENABLED = CONCEPT_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Retention Time Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__RETENTION_TIME_TYPE = CONCEPT_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Retention Time Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__RETENTION_TIME_SIZE = CONCEPT_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>User Defined Fields</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__USER_DEFINED_FIELDS = CONCEPT_FEATURE_COUNT + 12;

	/**
	 * The number of structural features of the '<em>Metric</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_FEATURE_COUNT = CONCEPT_FEATURE_COUNT + 13;


	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.element.impl.BaseInstanceImpl <em>Base Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.element.impl.BaseInstanceImpl
	 * @see com.tibco.cep.designtime.core.model.element.impl.ElementPackageImpl#getBaseInstance()
	 * @generated
	 */
	int BASE_INSTANCE = 5;

	/**
	 * The feature id for the '<em><b>Resource Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_INSTANCE__RESOURCE_PATH = 0;

	/**
	 * The number of structural features of the '<em>Base Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_INSTANCE_FEATURE_COUNT = 1;


	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.element.impl.ProcessEntityImpl <em>Process Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.element.impl.ProcessEntityImpl
	 * @see com.tibco.cep.designtime.core.model.element.impl.ElementPackageImpl#getProcessEntity()
	 * @generated
	 */
	int PROCESS_ENTITY = 6;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ENTITY__NAMESPACE = ModelPackage.ENTITY__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ENTITY__FOLDER = ModelPackage.ENTITY__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ENTITY__NAME = ModelPackage.ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ENTITY__DESCRIPTION = ModelPackage.ENTITY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ENTITY__LAST_MODIFIED = ModelPackage.ENTITY__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ENTITY__GUID = ModelPackage.ENTITY__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ENTITY__ONTOLOGY = ModelPackage.ENTITY__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ENTITY__EXTENDED_PROPERTIES = ModelPackage.ENTITY__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ENTITY__HIDDEN_PROPERTIES = ModelPackage.ENTITY__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ENTITY__TRANSIENT_PROPERTIES = ModelPackage.ENTITY__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ENTITY__OWNER_PROJECT_NAME = ModelPackage.ENTITY__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ENTITY__PROPERTIES = ModelPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Process Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ENTITY_FEATURE_COUNT = ModelPackage.ENTITY_FEATURE_COUNT + 1;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.element.Concept <em>Concept</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Concept</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Concept
	 * @generated
	 */
	EClass getConcept();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.designtime.core.model.element.Concept#getSubConcepts <em>Sub Concepts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Sub Concepts</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Concept#getSubConcepts()
	 * @see #getConcept()
	 * @generated
	 */
	EReference getConcept_SubConcepts();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.element.Concept#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Concept#getProperties()
	 * @see #getConcept()
	 * @generated
	 */
	EReference getConcept_Properties();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.element.Concept#getRuleSet <em>Rule Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Rule Set</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Concept#getRuleSet()
	 * @see #getConcept()
	 * @generated
	 */
	EReference getConcept_RuleSet();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.designtime.core.model.element.Concept#getStateMachinePaths <em>State Machine Paths</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>State Machine Paths</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Concept#getStateMachinePaths()
	 * @see #getConcept()
	 * @generated
	 */
	EAttribute getConcept_StateMachinePaths();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.Concept#getRootStateMachinePath <em>Root State Machine Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Root State Machine Path</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Concept#getRootStateMachinePath()
	 * @see #getConcept()
	 * @generated
	 */
	EAttribute getConcept_RootStateMachinePath();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.element.Concept#getParentConcept <em>Parent Concept</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent Concept</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Concept#getParentConcept()
	 * @see #getConcept()
	 * @generated
	 */
	EReference getConcept_ParentConcept();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.element.Concept#getSuperConcept <em>Super Concept</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Super Concept</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Concept#getSuperConcept()
	 * @see #getConcept()
	 * @generated
	 */
	EReference getConcept_SuperConcept();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.Concept#getSuperConceptPath <em>Super Concept Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Super Concept Path</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Concept#getSuperConceptPath()
	 * @see #getConcept()
	 * @generated
	 */
	EAttribute getConcept_SuperConceptPath();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.Concept#isScorecard <em>Scorecard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Scorecard</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Concept#isScorecard()
	 * @see #getConcept()
	 * @generated
	 */
	EAttribute getConcept_Scorecard();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.Concept#isMetric <em>Metric</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Metric</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Concept#isMetric()
	 * @see #getConcept()
	 * @generated
	 */
	EAttribute getConcept_Metric();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.Concept#isContained <em>Contained</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Contained</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Concept#isContained()
	 * @see #getConcept()
	 * @generated
	 */
	EAttribute getConcept_Contained();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.Concept#isPOJO <em>POJO</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>POJO</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Concept#isPOJO()
	 * @see #getConcept()
	 * @generated
	 */
	EAttribute getConcept_POJO();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.Concept#isTransient <em>Transient</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Transient</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Concept#isTransient()
	 * @see #getConcept()
	 * @generated
	 */
	EAttribute getConcept_Transient();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.Concept#getImplClass <em>Impl Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Impl Class</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Concept#getImplClass()
	 * @see #getConcept()
	 * @generated
	 */
	EAttribute getConcept_ImplClass();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.designtime.core.model.element.Concept#getSubConceptsPath <em>Sub Concepts Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Sub Concepts Path</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Concept#getSubConceptsPath()
	 * @see #getConcept()
	 * @generated
	 */
	EAttribute getConcept_SubConceptsPath();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.Concept#getParentConceptPath <em>Parent Concept Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parent Concept Path</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Concept#getParentConceptPath()
	 * @see #getConcept()
	 * @generated
	 */
	EAttribute getConcept_ParentConceptPath();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.Concept#isAutoStartStateMachine <em>Auto Start State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Auto Start State Machine</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Concept#isAutoStartStateMachine()
	 * @see #getConcept()
	 * @generated
	 */
	EAttribute getConcept_AutoStartStateMachine();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.element.Scorecard <em>Scorecard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Scorecard</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Scorecard
	 * @generated
	 */
	EClass getScorecard();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition <em>Property Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Definition</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.PropertyDefinition
	 * @generated
	 */
	EClass getPropertyDefinition();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.PropertyDefinition#getType()
	 * @see #getPropertyDefinition()
	 * @generated
	 */
	EAttribute getPropertyDefinition_Type();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getConceptTypePath <em>Concept Type Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Concept Type Path</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.PropertyDefinition#getConceptTypePath()
	 * @see #getPropertyDefinition()
	 * @generated
	 */
	EAttribute getPropertyDefinition_ConceptTypePath();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#isArray <em>Array</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Array</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.PropertyDefinition#isArray()
	 * @see #getPropertyDefinition()
	 * @generated
	 */
	EAttribute getPropertyDefinition_Array();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getOwnerPath <em>Owner Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Owner Path</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.PropertyDefinition#getOwnerPath()
	 * @see #getPropertyDefinition()
	 * @generated
	 */
	EAttribute getPropertyDefinition_OwnerPath();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getHistoryPolicy <em>History Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>History Policy</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.PropertyDefinition#getHistoryPolicy()
	 * @see #getPropertyDefinition()
	 * @generated
	 */
	EAttribute getPropertyDefinition_HistoryPolicy();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getHistorySize <em>History Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>History Size</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.PropertyDefinition#getHistorySize()
	 * @see #getPropertyDefinition()
	 * @generated
	 */
	EAttribute getPropertyDefinition_HistorySize();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getOrder <em>Order</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Order</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.PropertyDefinition#getOrder()
	 * @see #getPropertyDefinition()
	 * @generated
	 */
	EAttribute getPropertyDefinition_Order();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getDefaultValue <em>Default Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Value</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.PropertyDefinition#getDefaultValue()
	 * @see #getPropertyDefinition()
	 * @generated
	 */
	EAttribute getPropertyDefinition_DefaultValue();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getDisjointSet <em>Disjoint Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Disjoint Set</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.PropertyDefinition#getDisjointSet()
	 * @see #getPropertyDefinition()
	 * @generated
	 */
	EAttribute getPropertyDefinition_DisjointSet();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getSuper <em>Super</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Super</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.PropertyDefinition#getSuper()
	 * @see #getPropertyDefinition()
	 * @generated
	 */
	EAttribute getPropertyDefinition_Super();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.PropertyDefinition#getParent()
	 * @see #getPropertyDefinition()
	 * @generated
	 */
	EReference getPropertyDefinition_Parent();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#isTransitive <em>Transitive</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Transitive</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.PropertyDefinition#isTransitive()
	 * @see #getPropertyDefinition()
	 * @generated
	 */
	EAttribute getPropertyDefinition_Transitive();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getEquivalentProperties <em>Equivalent Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Equivalent Properties</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.PropertyDefinition#getEquivalentProperties()
	 * @see #getPropertyDefinition()
	 * @generated
	 */
	EReference getPropertyDefinition_EquivalentProperties();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getAttributeDefinitions <em>Attribute Definitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute Definitions</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.PropertyDefinition#getAttributeDefinitions()
	 * @see #getPropertyDefinition()
	 * @generated
	 */
	EReference getPropertyDefinition_AttributeDefinitions();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getAggregationType <em>Aggregation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Aggregation Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.PropertyDefinition#getAggregationType()
	 * @see #getPropertyDefinition()
	 * @generated
	 */
	EAttribute getPropertyDefinition_AggregationType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#isGroupByField <em>Group By Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Group By Field</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.PropertyDefinition#isGroupByField()
	 * @see #getPropertyDefinition()
	 * @generated
	 */
	EAttribute getPropertyDefinition_GroupByField();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getGroupByPosition <em>Group By Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Group By Position</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.PropertyDefinition#getGroupByPosition()
	 * @see #getPropertyDefinition()
	 * @generated
	 */
	EAttribute getPropertyDefinition_GroupByPosition();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#isTimeWindowField <em>Time Window Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time Window Field</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.PropertyDefinition#isTimeWindowField()
	 * @see #getPropertyDefinition()
	 * @generated
	 */
	EAttribute getPropertyDefinition_TimeWindowField();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getDomainInstances <em>Domain Instances</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Domain Instances</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.PropertyDefinition#getDomainInstances()
	 * @see #getPropertyDefinition()
	 * @generated
	 */
	EReference getPropertyDefinition_DomainInstances();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.element.AttributeDefinition <em>Attribute Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Definition</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.AttributeDefinition
	 * @generated
	 */
	EClass getAttributeDefinition();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.element.Metric <em>Metric</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metric</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Metric
	 * @generated
	 */
	EClass getMetric();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.Metric#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Metric#getType()
	 * @see #getMetric()
	 * @generated
	 */
	EAttribute getMetric_Type();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.Metric#getWindowType <em>Window Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Window Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Metric#getWindowType()
	 * @see #getMetric()
	 * @generated
	 */
	EAttribute getMetric_WindowType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.Metric#getWindowSize <em>Window Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Window Size</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Metric#getWindowSize()
	 * @see #getMetric()
	 * @generated
	 */
	EAttribute getMetric_WindowSize();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.Metric#getRecurringTimeType <em>Recurring Time Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Recurring Time Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Metric#getRecurringTimeType()
	 * @see #getMetric()
	 * @generated
	 */
	EAttribute getMetric_RecurringTimeType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.Metric#getRecurringFrequency <em>Recurring Frequency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Recurring Frequency</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Metric#getRecurringFrequency()
	 * @see #getMetric()
	 * @generated
	 */
	EAttribute getMetric_RecurringFrequency();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.Metric#getStartTime <em>Start Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start Time</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Metric#getStartTime()
	 * @see #getMetric()
	 * @generated
	 */
	EAttribute getMetric_StartTime();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.Metric#getShortName <em>Short Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Short Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Metric#getShortName()
	 * @see #getMetric()
	 * @generated
	 */
	EAttribute getMetric_ShortName();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.Metric#isPersistent <em>Persistent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Persistent</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Metric#isPersistent()
	 * @see #getMetric()
	 * @generated
	 */
	EAttribute getMetric_Persistent();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.Metric#isTransactionalRolling <em>Transactional Rolling</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Transactional Rolling</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Metric#isTransactionalRolling()
	 * @see #getMetric()
	 * @generated
	 */
	EAttribute getMetric_TransactionalRolling();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.Metric#isEnabled <em>Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enabled</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Metric#isEnabled()
	 * @see #getMetric()
	 * @generated
	 */
	EAttribute getMetric_Enabled();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.Metric#getRetentionTimeType <em>Retention Time Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Retention Time Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Metric#getRetentionTimeType()
	 * @see #getMetric()
	 * @generated
	 */
	EAttribute getMetric_RetentionTimeType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.Metric#getRetentionTimeSize <em>Retention Time Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Retention Time Size</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Metric#getRetentionTimeSize()
	 * @see #getMetric()
	 * @generated
	 */
	EAttribute getMetric_RetentionTimeSize();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.element.Metric#getUserDefinedFields <em>User Defined Fields</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>User Defined Fields</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.Metric#getUserDefinedFields()
	 * @see #getMetric()
	 * @generated
	 */
	EReference getMetric_UserDefinedFields();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.element.BaseInstance <em>Base Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Base Instance</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.BaseInstance
	 * @generated
	 */
	EClass getBaseInstance();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.element.BaseInstance#getResourcePath <em>Resource Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Resource Path</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.BaseInstance#getResourcePath()
	 * @see #getBaseInstance()
	 * @generated
	 */
	EAttribute getBaseInstance_ResourcePath();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.element.ProcessEntity <em>Process Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Process Entity</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.ProcessEntity
	 * @generated
	 */
	EClass getProcessEntity();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.element.ProcessEntity#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see com.tibco.cep.designtime.core.model.element.ProcessEntity#getProperties()
	 * @see #getProcessEntity()
	 * @generated
	 */
	EReference getProcessEntity_Properties();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ElementFactory getElementFactory();

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
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.element.impl.ConceptImpl <em>Concept</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.element.impl.ConceptImpl
		 * @see com.tibco.cep.designtime.core.model.element.impl.ElementPackageImpl#getConcept()
		 * @generated
		 */
		EClass CONCEPT = eINSTANCE.getConcept();

		/**
		 * The meta object literal for the '<em><b>Sub Concepts</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONCEPT__SUB_CONCEPTS = eINSTANCE.getConcept_SubConcepts();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONCEPT__PROPERTIES = eINSTANCE.getConcept_Properties();

		/**
		 * The meta object literal for the '<em><b>Rule Set</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONCEPT__RULE_SET = eINSTANCE.getConcept_RuleSet();

		/**
		 * The meta object literal for the '<em><b>State Machine Paths</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONCEPT__STATE_MACHINE_PATHS = eINSTANCE.getConcept_StateMachinePaths();

		/**
		 * The meta object literal for the '<em><b>Root State Machine Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONCEPT__ROOT_STATE_MACHINE_PATH = eINSTANCE.getConcept_RootStateMachinePath();

		/**
		 * The meta object literal for the '<em><b>Parent Concept</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONCEPT__PARENT_CONCEPT = eINSTANCE.getConcept_ParentConcept();

		/**
		 * The meta object literal for the '<em><b>Super Concept</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONCEPT__SUPER_CONCEPT = eINSTANCE.getConcept_SuperConcept();

		/**
		 * The meta object literal for the '<em><b>Super Concept Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONCEPT__SUPER_CONCEPT_PATH = eINSTANCE.getConcept_SuperConceptPath();

		/**
		 * The meta object literal for the '<em><b>Scorecard</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONCEPT__SCORECARD = eINSTANCE.getConcept_Scorecard();

		/**
		 * The meta object literal for the '<em><b>Metric</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONCEPT__METRIC = eINSTANCE.getConcept_Metric();

		/**
		 * The meta object literal for the '<em><b>Contained</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONCEPT__CONTAINED = eINSTANCE.getConcept_Contained();

		/**
		 * The meta object literal for the '<em><b>POJO</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONCEPT__POJO = eINSTANCE.getConcept_POJO();

		/**
		 * The meta object literal for the '<em><b>Transient</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONCEPT__TRANSIENT = eINSTANCE.getConcept_Transient();

		/**
		 * The meta object literal for the '<em><b>Impl Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONCEPT__IMPL_CLASS = eINSTANCE.getConcept_ImplClass();

		/**
		 * The meta object literal for the '<em><b>Sub Concepts Path</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONCEPT__SUB_CONCEPTS_PATH = eINSTANCE.getConcept_SubConceptsPath();

		/**
		 * The meta object literal for the '<em><b>Parent Concept Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONCEPT__PARENT_CONCEPT_PATH = eINSTANCE.getConcept_ParentConceptPath();

		/**
		 * The meta object literal for the '<em><b>Auto Start State Machine</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONCEPT__AUTO_START_STATE_MACHINE = eINSTANCE.getConcept_AutoStartStateMachine();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.element.impl.ScorecardImpl <em>Scorecard</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.element.impl.ScorecardImpl
		 * @see com.tibco.cep.designtime.core.model.element.impl.ElementPackageImpl#getScorecard()
		 * @generated
		 */
		EClass SCORECARD = eINSTANCE.getScorecard();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl <em>Property Definition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl
		 * @see com.tibco.cep.designtime.core.model.element.impl.ElementPackageImpl#getPropertyDefinition()
		 * @generated
		 */
		EClass PROPERTY_DEFINITION = eINSTANCE.getPropertyDefinition();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_DEFINITION__TYPE = eINSTANCE.getPropertyDefinition_Type();

		/**
		 * The meta object literal for the '<em><b>Concept Type Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_DEFINITION__CONCEPT_TYPE_PATH = eINSTANCE.getPropertyDefinition_ConceptTypePath();

		/**
		 * The meta object literal for the '<em><b>Array</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_DEFINITION__ARRAY = eINSTANCE.getPropertyDefinition_Array();

		/**
		 * The meta object literal for the '<em><b>Owner Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_DEFINITION__OWNER_PATH = eINSTANCE.getPropertyDefinition_OwnerPath();

		/**
		 * The meta object literal for the '<em><b>History Policy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_DEFINITION__HISTORY_POLICY = eINSTANCE.getPropertyDefinition_HistoryPolicy();

		/**
		 * The meta object literal for the '<em><b>History Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_DEFINITION__HISTORY_SIZE = eINSTANCE.getPropertyDefinition_HistorySize();

		/**
		 * The meta object literal for the '<em><b>Order</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_DEFINITION__ORDER = eINSTANCE.getPropertyDefinition_Order();

		/**
		 * The meta object literal for the '<em><b>Default Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_DEFINITION__DEFAULT_VALUE = eINSTANCE.getPropertyDefinition_DefaultValue();

		/**
		 * The meta object literal for the '<em><b>Disjoint Set</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_DEFINITION__DISJOINT_SET = eINSTANCE.getPropertyDefinition_DisjointSet();

		/**
		 * The meta object literal for the '<em><b>Super</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_DEFINITION__SUPER = eINSTANCE.getPropertyDefinition_Super();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_DEFINITION__PARENT = eINSTANCE.getPropertyDefinition_Parent();

		/**
		 * The meta object literal for the '<em><b>Transitive</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_DEFINITION__TRANSITIVE = eINSTANCE.getPropertyDefinition_Transitive();

		/**
		 * The meta object literal for the '<em><b>Equivalent Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_DEFINITION__EQUIVALENT_PROPERTIES = eINSTANCE.getPropertyDefinition_EquivalentProperties();

		/**
		 * The meta object literal for the '<em><b>Attribute Definitions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_DEFINITION__ATTRIBUTE_DEFINITIONS = eINSTANCE.getPropertyDefinition_AttributeDefinitions();

		/**
		 * The meta object literal for the '<em><b>Aggregation Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_DEFINITION__AGGREGATION_TYPE = eINSTANCE.getPropertyDefinition_AggregationType();

		/**
		 * The meta object literal for the '<em><b>Group By Field</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_DEFINITION__GROUP_BY_FIELD = eINSTANCE.getPropertyDefinition_GroupByField();

		/**
		 * The meta object literal for the '<em><b>Group By Position</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_DEFINITION__GROUP_BY_POSITION = eINSTANCE.getPropertyDefinition_GroupByPosition();

		/**
		 * The meta object literal for the '<em><b>Time Window Field</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_DEFINITION__TIME_WINDOW_FIELD = eINSTANCE.getPropertyDefinition_TimeWindowField();

		/**
		 * The meta object literal for the '<em><b>Domain Instances</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_DEFINITION__DOMAIN_INSTANCES = eINSTANCE.getPropertyDefinition_DomainInstances();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.element.impl.AttributeDefinitionImpl <em>Attribute Definition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.element.impl.AttributeDefinitionImpl
		 * @see com.tibco.cep.designtime.core.model.element.impl.ElementPackageImpl#getAttributeDefinition()
		 * @generated
		 */
		EClass ATTRIBUTE_DEFINITION = eINSTANCE.getAttributeDefinition();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.element.impl.MetricImpl <em>Metric</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.element.impl.MetricImpl
		 * @see com.tibco.cep.designtime.core.model.element.impl.ElementPackageImpl#getMetric()
		 * @generated
		 */
		EClass METRIC = eINSTANCE.getMetric();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METRIC__TYPE = eINSTANCE.getMetric_Type();

		/**
		 * The meta object literal for the '<em><b>Window Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METRIC__WINDOW_TYPE = eINSTANCE.getMetric_WindowType();

		/**
		 * The meta object literal for the '<em><b>Window Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METRIC__WINDOW_SIZE = eINSTANCE.getMetric_WindowSize();

		/**
		 * The meta object literal for the '<em><b>Recurring Time Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METRIC__RECURRING_TIME_TYPE = eINSTANCE.getMetric_RecurringTimeType();

		/**
		 * The meta object literal for the '<em><b>Recurring Frequency</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METRIC__RECURRING_FREQUENCY = eINSTANCE.getMetric_RecurringFrequency();

		/**
		 * The meta object literal for the '<em><b>Start Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METRIC__START_TIME = eINSTANCE.getMetric_StartTime();

		/**
		 * The meta object literal for the '<em><b>Short Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METRIC__SHORT_NAME = eINSTANCE.getMetric_ShortName();

		/**
		 * The meta object literal for the '<em><b>Persistent</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METRIC__PERSISTENT = eINSTANCE.getMetric_Persistent();

		/**
		 * The meta object literal for the '<em><b>Transactional Rolling</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METRIC__TRANSACTIONAL_ROLLING = eINSTANCE.getMetric_TransactionalRolling();

		/**
		 * The meta object literal for the '<em><b>Enabled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METRIC__ENABLED = eINSTANCE.getMetric_Enabled();

		/**
		 * The meta object literal for the '<em><b>Retention Time Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METRIC__RETENTION_TIME_TYPE = eINSTANCE.getMetric_RetentionTimeType();

		/**
		 * The meta object literal for the '<em><b>Retention Time Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METRIC__RETENTION_TIME_SIZE = eINSTANCE.getMetric_RetentionTimeSize();

		/**
		 * The meta object literal for the '<em><b>User Defined Fields</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METRIC__USER_DEFINED_FIELDS = eINSTANCE.getMetric_UserDefinedFields();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.element.impl.BaseInstanceImpl <em>Base Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.element.impl.BaseInstanceImpl
		 * @see com.tibco.cep.designtime.core.model.element.impl.ElementPackageImpl#getBaseInstance()
		 * @generated
		 */
		EClass BASE_INSTANCE = eINSTANCE.getBaseInstance();

		/**
		 * The meta object literal for the '<em><b>Resource Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BASE_INSTANCE__RESOURCE_PATH = eINSTANCE.getBaseInstance_ResourcePath();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.element.impl.ProcessEntityImpl <em>Process Entity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.element.impl.ProcessEntityImpl
		 * @see com.tibco.cep.designtime.core.model.element.impl.ElementPackageImpl#getProcessEntity()
		 * @generated
		 */
		EClass PROCESS_ENTITY = eINSTANCE.getProcessEntity();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROCESS_ENTITY__PROPERTIES = eINSTANCE.getProcessEntity_Properties();

	}

} //ElementPackage
