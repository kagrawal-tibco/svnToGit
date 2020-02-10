/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see com.tibco.cep.decisionproject.ontology.OntologyFactory
 * @model kind="package"
 * @generated
 */
public interface OntologyPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "ontology";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/decisionproject/model/ontology.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "ont";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	OntologyPackage eINSTANCE = com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontology.AccessControlCandidate <em>Access Control Candidate</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontology.AccessControlCandidate
	 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getAccessControlCandidate()
	 * @generated
	 */
	int ACCESS_CONTROL_CANDIDATE = 16;

	/**
	 * The feature id for the '<em><b>Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCESS_CONTROL_CANDIDATE__MODIFIED = 0;

	/**
	 * The number of structural features of the '<em>Access Control Candidate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCESS_CONTROL_CANDIDATE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontology.impl.AbstractResourceImpl <em>Abstract Resource</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontology.impl.AbstractResourceImpl
	 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getAbstractResource()
	 * @generated
	 */
	int ABSTRACT_RESOURCE = 0;

	/**
	 * The feature id for the '<em><b>Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_RESOURCE__MODIFIED = ACCESS_CONTROL_CANDIDATE__MODIFIED;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_RESOURCE__NAME = ACCESS_CONTROL_CANDIDATE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_RESOURCE__DESCRIPTION = ACCESS_CONTROL_CANDIDATE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_RESOURCE__FOLDER = ACCESS_CONTROL_CANDIDATE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Abstract Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_RESOURCE_FEATURE_COUNT = ACCESS_CONTROL_CANDIDATE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontology.impl.ParentResourceImpl <em>Parent Resource</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontology.impl.ParentResourceImpl
	 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getParentResource()
	 * @generated
	 */
	int PARENT_RESOURCE = 8;

	/**
	 * The feature id for the '<em><b>Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARENT_RESOURCE__MODIFIED = ABSTRACT_RESOURCE__MODIFIED;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARENT_RESOURCE__NAME = ABSTRACT_RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARENT_RESOURCE__DESCRIPTION = ABSTRACT_RESOURCE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARENT_RESOURCE__FOLDER = ABSTRACT_RESOURCE__FOLDER;

	/**
	 * The number of structural features of the '<em>Parent Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARENT_RESOURCE_FEATURE_COUNT = ABSTRACT_RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontology.impl.FolderImpl <em>Folder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontology.impl.FolderImpl
	 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getFolder()
	 * @generated
	 */
	int FOLDER = 1;

	/**
	 * The feature id for the '<em><b>Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__MODIFIED = PARENT_RESOURCE__MODIFIED;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__NAME = PARENT_RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__DESCRIPTION = PARENT_RESOURCE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__FOLDER = PARENT_RESOURCE__FOLDER;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__RESOURCE = PARENT_RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Folder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER_FEATURE_COUNT = PARENT_RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontology.impl.ConceptImpl <em>Concept</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontology.impl.ConceptImpl
	 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getConcept()
	 * @generated
	 */
	int CONCEPT = 2;

	/**
	 * The feature id for the '<em><b>Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__MODIFIED = PARENT_RESOURCE__MODIFIED;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__NAME = PARENT_RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__DESCRIPTION = PARENT_RESOURCE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__FOLDER = PARENT_RESOURCE__FOLDER;

	/**
	 * The feature id for the '<em><b>Alias</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__ALIAS = PARENT_RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Super Concept Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__SUPER_CONCEPT_PATH = PARENT_RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Score Card</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__SCORE_CARD = PARENT_RESOURCE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__PROPERTY = PARENT_RESOURCE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Db Concept</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT__DB_CONCEPT = PARENT_RESOURCE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Concept</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCEPT_FEATURE_COUNT = PARENT_RESOURCE_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontology.impl.EventImpl <em>Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontology.impl.EventImpl
	 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getEvent()
	 * @generated
	 */
	int EVENT = 3;

	/**
	 * The feature id for the '<em><b>Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__MODIFIED = PARENT_RESOURCE__MODIFIED;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__NAME = PARENT_RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__DESCRIPTION = PARENT_RESOURCE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__FOLDER = PARENT_RESOURCE__FOLDER;

	/**
	 * The feature id for the '<em><b>Alias</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__ALIAS = PARENT_RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Ttl</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__TTL = PARENT_RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Ttl Units</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__TTL_UNITS = PARENT_RESOURCE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Super Event Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__SUPER_EVENT_PATH = PARENT_RESOURCE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Default Destination Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__DEFAULT_DESTINATION_NAME = PARENT_RESOURCE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Expiry Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__EXPIRY_ACTION = PARENT_RESOURCE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>User Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__USER_PROPERTY = PARENT_RESOURCE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__TYPE = PARENT_RESOURCE_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_FEATURE_COUNT = PARENT_RESOURCE_FEATURE_COUNT + 8;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontology.impl.RuleSetImpl <em>Rule Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontology.impl.RuleSetImpl
	 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getRuleSet()
	 * @generated
	 */
	int RULE_SET = 4;

	/**
	 * The feature id for the '<em><b>Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET__MODIFIED = PARENT_RESOURCE__MODIFIED;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET__NAME = PARENT_RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET__DESCRIPTION = PARENT_RESOURCE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET__FOLDER = PARENT_RESOURCE__FOLDER;

	/**
	 * The feature id for the '<em><b>Rule Set Paricipant</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET__RULE_SET_PARICIPANT = PARENT_RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Rule Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET_FEATURE_COUNT = PARENT_RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontology.impl.RuleSetParticipantImpl <em>Rule Set Participant</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontology.impl.RuleSetParticipantImpl
	 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getRuleSetParticipant()
	 * @generated
	 */
	int RULE_SET_PARTICIPANT = 10;

	/**
	 * The feature id for the '<em><b>Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET_PARTICIPANT__MODIFIED = ABSTRACT_RESOURCE__MODIFIED;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET_PARTICIPANT__NAME = ABSTRACT_RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET_PARTICIPANT__DESCRIPTION = ABSTRACT_RESOURCE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET_PARTICIPANT__FOLDER = ABSTRACT_RESOURCE__FOLDER;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET_PARTICIPANT__PATH = ABSTRACT_RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parent Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET_PARTICIPANT__PARENT_PATH = ABSTRACT_RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Rule Set Participant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET_PARTICIPANT_FEATURE_COUNT = ABSTRACT_RESOURCE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontology.impl.RuleImpl <em>Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontology.impl.RuleImpl
	 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getRule()
	 * @generated
	 */
	int RULE = 5;

	/**
	 * The feature id for the '<em><b>Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__MODIFIED = RULE_SET_PARTICIPANT__MODIFIED;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__NAME = RULE_SET_PARTICIPANT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__DESCRIPTION = RULE_SET_PARTICIPANT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__FOLDER = RULE_SET_PARTICIPANT__FOLDER;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__PATH = RULE_SET_PARTICIPANT__PATH;

	/**
	 * The feature id for the '<em><b>Parent Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__PARENT_PATH = RULE_SET_PARTICIPANT__PARENT_PATH;

	/**
	 * The number of structural features of the '<em>Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FEATURE_COUNT = RULE_SET_PARTICIPANT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontology.impl.RuleFunctionImpl <em>Rule Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontology.impl.RuleFunctionImpl
	 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getRuleFunction()
	 * @generated
	 */
	int RULE_FUNCTION = 6;

	/**
	 * The feature id for the '<em><b>Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__MODIFIED = ABSTRACT_RESOURCE__MODIFIED;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__NAME = ABSTRACT_RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__DESCRIPTION = ABSTRACT_RESOURCE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__FOLDER = ABSTRACT_RESOURCE__FOLDER;

	/**
	 * The feature id for the '<em><b>Header</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__HEADER = ABSTRACT_RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__ARGUMENTS = ABSTRACT_RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Body</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION__BODY = ABSTRACT_RESOURCE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Rule Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FUNCTION_FEATURE_COUNT = ABSTRACT_RESOURCE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontology.impl.PropertyImpl <em>Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontology.impl.PropertyImpl
	 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getProperty()
	 * @generated
	 */
	int PROPERTY = 7;

	/**
	 * The feature id for the '<em><b>Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__MODIFIED = ABSTRACT_RESOURCE__MODIFIED;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__NAME = ABSTRACT_RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__DESCRIPTION = ABSTRACT_RESOURCE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__FOLDER = ABSTRACT_RESOURCE__FOLDER;

	/**
	 * The feature id for the '<em><b>Data Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__DATA_TYPE = ABSTRACT_RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>History Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__HISTORY_SIZE = ABSTRACT_RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>History Policy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__HISTORY_POLICY = ABSTRACT_RESOURCE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Multiple</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__MULTIPLE = ABSTRACT_RESOURCE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__DEFAULT_VALUE = ABSTRACT_RESOURCE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Property Resource Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__PROPERTY_RESOURCE_TYPE = ABSTRACT_RESOURCE_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FEATURE_COUNT = ABSTRACT_RESOURCE_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontology.impl.OntologyImpl <em>Ontology</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyImpl
	 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getOntology()
	 * @generated
	 */
	int ONTOLOGY = 9;

	/**
	 * The feature id for the '<em><b>Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONTOLOGY__MODIFIED = PARENT_RESOURCE__MODIFIED;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONTOLOGY__NAME = PARENT_RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONTOLOGY__DESCRIPTION = PARENT_RESOURCE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONTOLOGY__FOLDER = PARENT_RESOURCE__FOLDER;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONTOLOGY__RESOURCE = PARENT_RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Ontology</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONTOLOGY_FEATURE_COUNT = PARENT_RESOURCE_FEATURE_COUNT + 1;


	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontology.impl.DTRuleImpl <em>DT Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontology.impl.DTRuleImpl
	 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getDTRule()
	 * @generated
	 */
	int DT_RULE = 11;

	/**
	 * The feature id for the '<em><b>Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DT_RULE__MODIFIED = RULE_SET_PARTICIPANT__MODIFIED;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DT_RULE__NAME = RULE_SET_PARTICIPANT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DT_RULE__DESCRIPTION = RULE_SET_PARTICIPANT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DT_RULE__FOLDER = RULE_SET_PARTICIPANT__FOLDER;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DT_RULE__PATH = RULE_SET_PARTICIPANT__PATH;

	/**
	 * The feature id for the '<em><b>Parent Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DT_RULE__PARENT_PATH = RULE_SET_PARTICIPANT__PARENT_PATH;

	/**
	 * The feature id for the '<em><b>Table</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DT_RULE__TABLE = RULE_SET_PARTICIPANT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>DT Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DT_RULE_FEATURE_COUNT = RULE_SET_PARTICIPANT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontology.impl.HeaderImpl <em>Header</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontology.impl.HeaderImpl
	 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getHeader()
	 * @generated
	 */
	int HEADER = 12;

	/**
	 * The feature id for the '<em><b>Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER__PROPERTY = 0;

	/**
	 * The number of structural features of the '<em>Header</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontology.impl.ArgumentsImpl <em>Arguments</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontology.impl.ArgumentsImpl
	 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getArguments()
	 * @generated
	 */
	int ARGUMENTS = 13;

	/**
	 * The feature id for the '<em><b>Argument</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENTS__ARGUMENT = 0;

	/**
	 * The number of structural features of the '<em>Arguments</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENTS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontology.impl.ImplementationImpl <em>Implementation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontology.impl.ImplementationImpl
	 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getImplementation()
	 * @generated
	 */
	int IMPLEMENTATION = 14;

	/**
	 * The feature id for the '<em><b>Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLEMENTATION__MODIFIED = ABSTRACT_RESOURCE__MODIFIED;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLEMENTATION__NAME = ABSTRACT_RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLEMENTATION__DESCRIPTION = ABSTRACT_RESOURCE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLEMENTATION__FOLDER = ABSTRACT_RESOURCE__FOLDER;

	/**
	 * The feature id for the '<em><b>Style</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLEMENTATION__STYLE = ABSTRACT_RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Implements</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLEMENTATION__IMPLEMENTS = ABSTRACT_RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLEMENTATION__VERSION = ABSTRACT_RESOURCE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Last Modified By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLEMENTATION__LAST_MODIFIED_BY = ABSTRACT_RESOURCE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLEMENTATION__LAST_MODIFIED = ABSTRACT_RESOURCE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Dirty</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLEMENTATION__DIRTY = ABSTRACT_RESOURCE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Locked</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLEMENTATION__LOCKED = ABSTRACT_RESOURCE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Show Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLEMENTATION__SHOW_DESCRIPTION = ABSTRACT_RESOURCE_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Implementation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLEMENTATION_FEATURE_COUNT = ABSTRACT_RESOURCE_FEATURE_COUNT + 8;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontology.impl.ArgumentImpl <em>Argument</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontology.impl.ArgumentImpl
	 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getArgument()
	 * @generated
	 */
	int ARGUMENT = 15;

	/**
	 * The feature id for the '<em><b>Argument Entry</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT__ARGUMENT_ENTRY = 0;

	/**
	 * The number of structural features of the '<em>Argument</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontology.impl.PrimitiveHolderImpl <em>Primitive Holder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontology.impl.PrimitiveHolderImpl
	 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getPrimitiveHolder()
	 * @generated
	 */
	int PRIMITIVE_HOLDER = 17;

	/**
	 * The feature id for the '<em><b>Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMITIVE_HOLDER__MODIFIED = ABSTRACT_RESOURCE__MODIFIED;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMITIVE_HOLDER__NAME = ABSTRACT_RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMITIVE_HOLDER__DESCRIPTION = ABSTRACT_RESOURCE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMITIVE_HOLDER__FOLDER = ABSTRACT_RESOURCE__FOLDER;

	/**
	 * The feature id for the '<em><b>Alias</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMITIVE_HOLDER__ALIAS = ABSTRACT_RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Primitive Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMITIVE_HOLDER__PRIMITIVE_TYPE = ABSTRACT_RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Primitive Holder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMITIVE_HOLDER_FEATURE_COUNT = ABSTRACT_RESOURCE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionproject.ontology.ArgumentResource <em>Argument Resource</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionproject.ontology.ArgumentResource
	 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getArgumentResource()
	 * @generated
	 */
	int ARGUMENT_RESOURCE = 18;

	/**
	 * The feature id for the '<em><b>Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT_RESOURCE__MODIFIED = ABSTRACT_RESOURCE__MODIFIED;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT_RESOURCE__NAME = ABSTRACT_RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT_RESOURCE__DESCRIPTION = ABSTRACT_RESOURCE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT_RESOURCE__FOLDER = ABSTRACT_RESOURCE__FOLDER;

	/**
	 * The feature id for the '<em><b>Alias</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT_RESOURCE__ALIAS = ABSTRACT_RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Argument Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT_RESOURCE_FEATURE_COUNT = ABSTRACT_RESOURCE_FEATURE_COUNT + 1;

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontology.AbstractResource <em>Abstract Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Resource</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.AbstractResource
	 * @generated
	 */
	EClass getAbstractResource();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.AbstractResource#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.AbstractResource#getName()
	 * @see #getAbstractResource()
	 * @generated
	 */
	EAttribute getAbstractResource_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.AbstractResource#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.AbstractResource#getDescription()
	 * @see #getAbstractResource()
	 * @generated
	 */
	EAttribute getAbstractResource_Description();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.AbstractResource#getFolder <em>Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Folder</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.AbstractResource#getFolder()
	 * @see #getAbstractResource()
	 * @generated
	 */
	EAttribute getAbstractResource_Folder();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontology.Folder <em>Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Folder</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Folder
	 * @generated
	 */
	EClass getFolder();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.decisionproject.ontology.Folder#getResource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Resource</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Folder#getResource()
	 * @see #getFolder()
	 * @generated
	 */
	EReference getFolder_Resource();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontology.Concept <em>Concept</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Concept</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Concept
	 * @generated
	 */
	EClass getConcept();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.Concept#getSuperConceptPath <em>Super Concept Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Super Concept Path</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Concept#getSuperConceptPath()
	 * @see #getConcept()
	 * @generated
	 */
	EAttribute getConcept_SuperConceptPath();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.Concept#isScoreCard <em>Score Card</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Score Card</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Concept#isScoreCard()
	 * @see #getConcept()
	 * @generated
	 */
	EAttribute getConcept_ScoreCard();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.decisionproject.ontology.Concept#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Property</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Concept#getProperty()
	 * @see #getConcept()
	 * @generated
	 */
	EReference getConcept_Property();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.Concept#isDbConcept <em>Db Concept</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Db Concept</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Concept#isDbConcept()
	 * @see #getConcept()
	 * @generated
	 */
	EAttribute getConcept_DbConcept();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontology.Event <em>Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Event
	 * @generated
	 */
	EClass getEvent();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.Event#getTtl <em>Ttl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ttl</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Event#getTtl()
	 * @see #getEvent()
	 * @generated
	 */
	EAttribute getEvent_Ttl();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.Event#getTtlUnits <em>Ttl Units</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ttl Units</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Event#getTtlUnits()
	 * @see #getEvent()
	 * @generated
	 */
	EAttribute getEvent_TtlUnits();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.Event#getSuperEventPath <em>Super Event Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Super Event Path</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Event#getSuperEventPath()
	 * @see #getEvent()
	 * @generated
	 */
	EAttribute getEvent_SuperEventPath();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.Event#getDefaultDestinationName <em>Default Destination Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Destination Name</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Event#getDefaultDestinationName()
	 * @see #getEvent()
	 * @generated
	 */
	EAttribute getEvent_DefaultDestinationName();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.decisionproject.ontology.Event#getExpiryAction <em>Expiry Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expiry Action</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Event#getExpiryAction()
	 * @see #getEvent()
	 * @generated
	 */
	EReference getEvent_ExpiryAction();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.decisionproject.ontology.Event#getUserProperty <em>User Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>User Property</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Event#getUserProperty()
	 * @see #getEvent()
	 * @generated
	 */
	EReference getEvent_UserProperty();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.Event#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Event#getType()
	 * @see #getEvent()
	 * @generated
	 */
	EAttribute getEvent_Type();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontology.RuleSet <em>Rule Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Set</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.RuleSet
	 * @generated
	 */
	EClass getRuleSet();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.decisionproject.ontology.RuleSet#getRuleSetParicipant <em>Rule Set Paricipant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Rule Set Paricipant</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.RuleSet#getRuleSetParicipant()
	 * @see #getRuleSet()
	 * @generated
	 */
	EReference getRuleSet_RuleSetParicipant();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontology.Rule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Rule
	 * @generated
	 */
	EClass getRule();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontology.RuleFunction <em>Rule Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Function</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.RuleFunction
	 * @generated
	 */
	EClass getRuleFunction();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.decisionproject.ontology.RuleFunction#getHeader <em>Header</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Header</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.RuleFunction#getHeader()
	 * @see #getRuleFunction()
	 * @generated
	 */
	EReference getRuleFunction_Header();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.decisionproject.ontology.RuleFunction#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Body</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.RuleFunction#getBody()
	 * @see #getRuleFunction()
	 * @generated
	 */
	EAttribute getRuleFunction_Body();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.decisionproject.ontology.RuleFunction#getArguments <em>Arguments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Arguments</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.RuleFunction#getArguments()
	 * @see #getRuleFunction()
	 * @generated
	 */
	EReference getRuleFunction_Arguments();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontology.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Property
	 * @generated
	 */
	EClass getProperty();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.Property#getDataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Data Type</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Property#getDataType()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_DataType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.Property#getHistorySize <em>History Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>History Size</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Property#getHistorySize()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_HistorySize();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.Property#getHistoryPolicy <em>History Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>History Policy</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Property#getHistoryPolicy()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_HistoryPolicy();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.Property#isMultiple <em>Multiple</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Multiple</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Property#isMultiple()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Multiple();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.Property#getDefaultValue <em>Default Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Value</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Property#getDefaultValue()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_DefaultValue();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.Property#getPropertyResourceType <em>Property Resource Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property Resource Type</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Property#getPropertyResourceType()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_PropertyResourceType();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontology.ParentResource <em>Parent Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parent Resource</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.ParentResource
	 * @generated
	 */
	EClass getParentResource();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontology.Ontology <em>Ontology</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ontology</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Ontology
	 * @generated
	 */
	EClass getOntology();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.decisionproject.ontology.Ontology#getResource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Resource</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Ontology#getResource()
	 * @see #getOntology()
	 * @generated
	 */
	EReference getOntology_Resource();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontology.RuleSetParticipant <em>Rule Set Participant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Set Participant</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.RuleSetParticipant
	 * @generated
	 */
	EClass getRuleSetParticipant();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.RuleSetParticipant#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.RuleSetParticipant#getPath()
	 * @see #getRuleSetParticipant()
	 * @generated
	 */
	EAttribute getRuleSetParticipant_Path();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.RuleSetParticipant#getParentPath <em>Parent Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parent Path</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.RuleSetParticipant#getParentPath()
	 * @see #getRuleSetParticipant()
	 * @generated
	 */
	EAttribute getRuleSetParticipant_ParentPath();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontology.DTRule <em>DT Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DT Rule</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.DTRule
	 * @generated
	 */
	EClass getDTRule();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.decisionproject.ontology.DTRule#getTable <em>Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Table</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.DTRule#getTable()
	 * @see #getDTRule()
	 * @generated
	 */
	EReference getDTRule_Table();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontology.Header <em>Header</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Header</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Header
	 * @generated
	 */
	EClass getHeader();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.decisionproject.ontology.Header#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Property</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Header#getProperty()
	 * @see #getHeader()
	 * @generated
	 */
	EReference getHeader_Property();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontology.Arguments <em>Arguments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Arguments</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Arguments
	 * @generated
	 */
	EClass getArguments();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.decisionproject.ontology.Arguments#getArgument <em>Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Argument</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Arguments#getArgument()
	 * @see #getArguments()
	 * @generated
	 */
	EReference getArguments_Argument();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontology.Implementation <em>Implementation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Implementation</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Implementation
	 * @generated
	 */
	EClass getImplementation();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.Implementation#getStyle <em>Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Style</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Implementation#getStyle()
	 * @see #getImplementation()
	 * @generated
	 */
	EAttribute getImplementation_Style();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.Implementation#getImplements <em>Implements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Implements</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Implementation#getImplements()
	 * @see #getImplementation()
	 * @generated
	 */
	EAttribute getImplementation_Implements();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.Implementation#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Implementation#getVersion()
	 * @see #getImplementation()
	 * @generated
	 */
	EAttribute getImplementation_Version();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.Implementation#getLastModifiedBy <em>Last Modified By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Modified By</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Implementation#getLastModifiedBy()
	 * @see #getImplementation()
	 * @generated
	 */
	EAttribute getImplementation_LastModifiedBy();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.Implementation#getLastModified <em>Last Modified</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Modified</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Implementation#getLastModified()
	 * @see #getImplementation()
	 * @generated
	 */
	EAttribute getImplementation_LastModified();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.Implementation#isDirty <em>Dirty</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Dirty</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Implementation#isDirty()
	 * @see #getImplementation()
	 * @generated
	 */
	EAttribute getImplementation_Dirty();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.Implementation#isLocked <em>Locked</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Locked</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Implementation#isLocked()
	 * @see #getImplementation()
	 * @generated
	 */
	EAttribute getImplementation_Locked();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.Implementation#isShowDescription <em>Show Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Show Description</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Implementation#isShowDescription()
	 * @see #getImplementation()
	 * @generated
	 */
	EAttribute getImplementation_ShowDescription();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontology.Argument <em>Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Argument</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Argument
	 * @generated
	 */
	EClass getArgument();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.decisionproject.ontology.Argument#getArgumentEntry <em>Argument Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Argument Entry</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.Argument#getArgumentEntry()
	 * @see #getArgument()
	 * @generated
	 */
	EReference getArgument_ArgumentEntry();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontology.AccessControlCandidate <em>Access Control Candidate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Access Control Candidate</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.AccessControlCandidate
	 * @generated
	 */
	EClass getAccessControlCandidate();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.AccessControlCandidate#isModified <em>Modified</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Modified</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.AccessControlCandidate#isModified()
	 * @see #getAccessControlCandidate()
	 * @generated
	 */
	EAttribute getAccessControlCandidate_Modified();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontology.PrimitiveHolder <em>Primitive Holder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Primitive Holder</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.PrimitiveHolder
	 * @generated
	 */
	EClass getPrimitiveHolder();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.PrimitiveHolder#getPrimitiveType <em>Primitive Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Primitive Type</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.PrimitiveHolder#getPrimitiveType()
	 * @see #getPrimitiveHolder()
	 * @generated
	 */
	EAttribute getPrimitiveHolder_PrimitiveType();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionproject.ontology.ArgumentResource <em>Argument Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Argument Resource</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.ArgumentResource
	 * @generated
	 */
	EClass getArgumentResource();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionproject.ontology.ArgumentResource#getAlias <em>Alias</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Alias</em>'.
	 * @see com.tibco.cep.decisionproject.ontology.ArgumentResource#getAlias()
	 * @see #getArgumentResource()
	 * @generated
	 */
	EAttribute getArgumentResource_Alias();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	OntologyFactory getOntologyFactory();

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
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontology.impl.AbstractResourceImpl <em>Abstract Resource</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontology.impl.AbstractResourceImpl
		 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getAbstractResource()
		 * @generated
		 */
		EClass ABSTRACT_RESOURCE = eINSTANCE.getAbstractResource();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_RESOURCE__NAME = eINSTANCE.getAbstractResource_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_RESOURCE__DESCRIPTION = eINSTANCE.getAbstractResource_Description();

		/**
		 * The meta object literal for the '<em><b>Folder</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_RESOURCE__FOLDER = eINSTANCE.getAbstractResource_Folder();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontology.impl.FolderImpl <em>Folder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontology.impl.FolderImpl
		 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getFolder()
		 * @generated
		 */
		EClass FOLDER = eINSTANCE.getFolder();

		/**
		 * The meta object literal for the '<em><b>Resource</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FOLDER__RESOURCE = eINSTANCE.getFolder_Resource();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontology.impl.ConceptImpl <em>Concept</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontology.impl.ConceptImpl
		 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getConcept()
		 * @generated
		 */
		EClass CONCEPT = eINSTANCE.getConcept();

		/**
		 * The meta object literal for the '<em><b>Super Concept Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONCEPT__SUPER_CONCEPT_PATH = eINSTANCE.getConcept_SuperConceptPath();

		/**
		 * The meta object literal for the '<em><b>Score Card</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONCEPT__SCORE_CARD = eINSTANCE.getConcept_ScoreCard();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONCEPT__PROPERTY = eINSTANCE.getConcept_Property();

		/**
		 * The meta object literal for the '<em><b>Db Concept</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONCEPT__DB_CONCEPT = eINSTANCE.getConcept_DbConcept();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontology.impl.EventImpl <em>Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontology.impl.EventImpl
		 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getEvent()
		 * @generated
		 */
		EClass EVENT = eINSTANCE.getEvent();

		/**
		 * The meta object literal for the '<em><b>Ttl</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT__TTL = eINSTANCE.getEvent_Ttl();

		/**
		 * The meta object literal for the '<em><b>Ttl Units</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT__TTL_UNITS = eINSTANCE.getEvent_TtlUnits();

		/**
		 * The meta object literal for the '<em><b>Super Event Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT__SUPER_EVENT_PATH = eINSTANCE.getEvent_SuperEventPath();

		/**
		 * The meta object literal for the '<em><b>Default Destination Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT__DEFAULT_DESTINATION_NAME = eINSTANCE.getEvent_DefaultDestinationName();

		/**
		 * The meta object literal for the '<em><b>Expiry Action</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT__EXPIRY_ACTION = eINSTANCE.getEvent_ExpiryAction();

		/**
		 * The meta object literal for the '<em><b>User Property</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT__USER_PROPERTY = eINSTANCE.getEvent_UserProperty();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT__TYPE = eINSTANCE.getEvent_Type();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontology.impl.RuleSetImpl <em>Rule Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontology.impl.RuleSetImpl
		 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getRuleSet()
		 * @generated
		 */
		EClass RULE_SET = eINSTANCE.getRuleSet();

		/**
		 * The meta object literal for the '<em><b>Rule Set Paricipant</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_SET__RULE_SET_PARICIPANT = eINSTANCE.getRuleSet_RuleSetParicipant();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontology.impl.RuleImpl <em>Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontology.impl.RuleImpl
		 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getRule()
		 * @generated
		 */
		EClass RULE = eINSTANCE.getRule();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontology.impl.RuleFunctionImpl <em>Rule Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontology.impl.RuleFunctionImpl
		 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getRuleFunction()
		 * @generated
		 */
		EClass RULE_FUNCTION = eINSTANCE.getRuleFunction();

		/**
		 * The meta object literal for the '<em><b>Header</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_FUNCTION__HEADER = eINSTANCE.getRuleFunction_Header();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_FUNCTION__BODY = eINSTANCE.getRuleFunction_Body();

		/**
		 * The meta object literal for the '<em><b>Arguments</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_FUNCTION__ARGUMENTS = eINSTANCE.getRuleFunction_Arguments();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontology.impl.PropertyImpl <em>Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontology.impl.PropertyImpl
		 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getProperty()
		 * @generated
		 */
		EClass PROPERTY = eINSTANCE.getProperty();

		/**
		 * The meta object literal for the '<em><b>Data Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__DATA_TYPE = eINSTANCE.getProperty_DataType();

		/**
		 * The meta object literal for the '<em><b>History Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__HISTORY_SIZE = eINSTANCE.getProperty_HistorySize();

		/**
		 * The meta object literal for the '<em><b>History Policy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__HISTORY_POLICY = eINSTANCE.getProperty_HistoryPolicy();

		/**
		 * The meta object literal for the '<em><b>Multiple</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__MULTIPLE = eINSTANCE.getProperty_Multiple();

		/**
		 * The meta object literal for the '<em><b>Default Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__DEFAULT_VALUE = eINSTANCE.getProperty_DefaultValue();

		/**
		 * The meta object literal for the '<em><b>Property Resource Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__PROPERTY_RESOURCE_TYPE = eINSTANCE.getProperty_PropertyResourceType();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontology.impl.ParentResourceImpl <em>Parent Resource</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontology.impl.ParentResourceImpl
		 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getParentResource()
		 * @generated
		 */
		EClass PARENT_RESOURCE = eINSTANCE.getParentResource();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontology.impl.OntologyImpl <em>Ontology</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyImpl
		 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getOntology()
		 * @generated
		 */
		EClass ONTOLOGY = eINSTANCE.getOntology();

		/**
		 * The meta object literal for the '<em><b>Resource</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ONTOLOGY__RESOURCE = eINSTANCE.getOntology_Resource();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontology.impl.RuleSetParticipantImpl <em>Rule Set Participant</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontology.impl.RuleSetParticipantImpl
		 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getRuleSetParticipant()
		 * @generated
		 */
		EClass RULE_SET_PARTICIPANT = eINSTANCE.getRuleSetParticipant();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_SET_PARTICIPANT__PATH = eINSTANCE.getRuleSetParticipant_Path();

		/**
		 * The meta object literal for the '<em><b>Parent Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_SET_PARTICIPANT__PARENT_PATH = eINSTANCE.getRuleSetParticipant_ParentPath();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontology.impl.DTRuleImpl <em>DT Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontology.impl.DTRuleImpl
		 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getDTRule()
		 * @generated
		 */
		EClass DT_RULE = eINSTANCE.getDTRule();

		/**
		 * The meta object literal for the '<em><b>Table</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DT_RULE__TABLE = eINSTANCE.getDTRule_Table();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontology.impl.HeaderImpl <em>Header</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontology.impl.HeaderImpl
		 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getHeader()
		 * @generated
		 */
		EClass HEADER = eINSTANCE.getHeader();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HEADER__PROPERTY = eINSTANCE.getHeader_Property();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontology.impl.ArgumentsImpl <em>Arguments</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontology.impl.ArgumentsImpl
		 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getArguments()
		 * @generated
		 */
		EClass ARGUMENTS = eINSTANCE.getArguments();

		/**
		 * The meta object literal for the '<em><b>Argument</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARGUMENTS__ARGUMENT = eINSTANCE.getArguments_Argument();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontology.impl.ImplementationImpl <em>Implementation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontology.impl.ImplementationImpl
		 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getImplementation()
		 * @generated
		 */
		EClass IMPLEMENTATION = eINSTANCE.getImplementation();

		/**
		 * The meta object literal for the '<em><b>Style</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPLEMENTATION__STYLE = eINSTANCE.getImplementation_Style();

		/**
		 * The meta object literal for the '<em><b>Implements</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPLEMENTATION__IMPLEMENTS = eINSTANCE.getImplementation_Implements();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPLEMENTATION__VERSION = eINSTANCE.getImplementation_Version();

		/**
		 * The meta object literal for the '<em><b>Last Modified By</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPLEMENTATION__LAST_MODIFIED_BY = eINSTANCE.getImplementation_LastModifiedBy();

		/**
		 * The meta object literal for the '<em><b>Last Modified</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPLEMENTATION__LAST_MODIFIED = eINSTANCE.getImplementation_LastModified();

		/**
		 * The meta object literal for the '<em><b>Dirty</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPLEMENTATION__DIRTY = eINSTANCE.getImplementation_Dirty();

		/**
		 * The meta object literal for the '<em><b>Locked</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPLEMENTATION__LOCKED = eINSTANCE.getImplementation_Locked();

		/**
		 * The meta object literal for the '<em><b>Show Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPLEMENTATION__SHOW_DESCRIPTION = eINSTANCE.getImplementation_ShowDescription();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontology.impl.ArgumentImpl <em>Argument</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontology.impl.ArgumentImpl
		 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getArgument()
		 * @generated
		 */
		EClass ARGUMENT = eINSTANCE.getArgument();

		/**
		 * The meta object literal for the '<em><b>Argument Entry</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARGUMENT__ARGUMENT_ENTRY = eINSTANCE.getArgument_ArgumentEntry();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontology.AccessControlCandidate <em>Access Control Candidate</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontology.AccessControlCandidate
		 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getAccessControlCandidate()
		 * @generated
		 */
		EClass ACCESS_CONTROL_CANDIDATE = eINSTANCE.getAccessControlCandidate();

		/**
		 * The meta object literal for the '<em><b>Modified</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCESS_CONTROL_CANDIDATE__MODIFIED = eINSTANCE.getAccessControlCandidate_Modified();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontology.impl.PrimitiveHolderImpl <em>Primitive Holder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontology.impl.PrimitiveHolderImpl
		 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getPrimitiveHolder()
		 * @generated
		 */
		EClass PRIMITIVE_HOLDER = eINSTANCE.getPrimitiveHolder();

		/**
		 * The meta object literal for the '<em><b>Primitive Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRIMITIVE_HOLDER__PRIMITIVE_TYPE = eINSTANCE.getPrimitiveHolder_PrimitiveType();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionproject.ontology.ArgumentResource <em>Argument Resource</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionproject.ontology.ArgumentResource
		 * @see com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl#getArgumentResource()
		 * @generated
		 */
		EClass ARGUMENT_RESOURCE = eINSTANCE.getArgumentResource();

		/**
		 * The meta object literal for the '<em><b>Alias</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARGUMENT_RESOURCE__ALIAS = eINSTANCE.getArgumentResource_Alias();

	}

} //OntologyPackage
