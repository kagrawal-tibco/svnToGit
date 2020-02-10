/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.element.ElementPackage;

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
 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelFactory
 * @model kind="package"
 * @generated
 */
public interface ChannelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "channel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/designtime/core/model/service/channel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "channel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ChannelPackage eINSTANCE = com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.ChannelImpl <em>Channel</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelImpl
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getChannel()
	 * @generated
	 */
	int CHANNEL = 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANNEL__NAMESPACE = ModelPackage.ENTITY__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANNEL__FOLDER = ModelPackage.ENTITY__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANNEL__NAME = ModelPackage.ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANNEL__DESCRIPTION = ModelPackage.ENTITY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANNEL__LAST_MODIFIED = ModelPackage.ENTITY__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANNEL__GUID = ModelPackage.ENTITY__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANNEL__ONTOLOGY = ModelPackage.ENTITY__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANNEL__EXTENDED_PROPERTIES = ModelPackage.ENTITY__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANNEL__HIDDEN_PROPERTIES = ModelPackage.ENTITY__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANNEL__TRANSIENT_PROPERTIES = ModelPackage.ENTITY__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANNEL__OWNER_PROJECT_NAME = ModelPackage.ENTITY__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Driver</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANNEL__DRIVER = ModelPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Driver Manager</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANNEL__DRIVER_MANAGER = ModelPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Channel</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANNEL_FEATURE_COUNT = ModelPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.DestinationImpl <em>Destination</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.DestinationImpl
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getDestination()
	 * @generated
	 */
	int DESTINATION = 1;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION__NAMESPACE = ModelPackage.ENTITY__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION__FOLDER = ModelPackage.ENTITY__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION__NAME = ModelPackage.ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION__DESCRIPTION = ModelPackage.ENTITY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION__LAST_MODIFIED = ModelPackage.ENTITY__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION__GUID = ModelPackage.ENTITY__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION__ONTOLOGY = ModelPackage.ENTITY__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION__EXTENDED_PROPERTIES = ModelPackage.ENTITY__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION__HIDDEN_PROPERTIES = ModelPackage.ENTITY__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION__TRANSIENT_PROPERTIES = ModelPackage.ENTITY__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION__OWNER_PROJECT_NAME = ModelPackage.ENTITY__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Event URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION__EVENT_URI = ModelPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Serializer Deserializer Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION__SERIALIZER_DESERIALIZER_CLASS = ModelPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION__PROPERTIES = ModelPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Driver Config</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION__DRIVER_CONFIG = ModelPackage.ENTITY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Input Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION__INPUT_ENABLED = ModelPackage.ENTITY_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Output Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION__OUTPUT_ENABLED = ModelPackage.ENTITY_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Destination</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_FEATURE_COUNT = ModelPackage.ENTITY_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverConfigImpl <em>Driver Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.DriverConfigImpl
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getDriverConfig()
	 * @generated
	 */
	int DRIVER_CONFIG = 2;

	/**
	 * The feature id for the '<em><b>Config Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVER_CONFIG__CONFIG_METHOD = 0;

	/**
	 * The feature id for the '<em><b>Reference</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVER_CONFIG__REFERENCE = 1;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVER_CONFIG__LABEL = 2;

	/**
	 * The feature id for the '<em><b>Channel</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVER_CONFIG__CHANNEL = 3;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVER_CONFIG__PROPERTIES = 4;

	/**
	 * The feature id for the '<em><b>Destinations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVER_CONFIG__DESTINATIONS = 5;

	/**
	 * The feature id for the '<em><b>Extended Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVER_CONFIG__EXTENDED_CONFIGURATION = 6;

	/**
	 * The feature id for the '<em><b>Driver Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVER_CONFIG__DRIVER_TYPE = 7;

	/**
	 * The feature id for the '<em><b>Choice</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVER_CONFIG__CHOICE = 8;

	/**
	 * The number of structural features of the '<em>Driver Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVER_CONFIG_FEATURE_COUNT = 9;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.DestinationConceptImpl <em>Destination Concept</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.DestinationConceptImpl
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getDestinationConcept()
	 * @generated
	 */
	int DESTINATION_CONCEPT = 3;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__NAMESPACE = ElementPackage.CONCEPT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__FOLDER = ElementPackage.CONCEPT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__NAME = ElementPackage.CONCEPT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__DESCRIPTION = ElementPackage.CONCEPT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__LAST_MODIFIED = ElementPackage.CONCEPT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__GUID = ElementPackage.CONCEPT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__ONTOLOGY = ElementPackage.CONCEPT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__EXTENDED_PROPERTIES = ElementPackage.CONCEPT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__HIDDEN_PROPERTIES = ElementPackage.CONCEPT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__TRANSIENT_PROPERTIES = ElementPackage.CONCEPT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__OWNER_PROJECT_NAME = ElementPackage.CONCEPT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Sub Concepts</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__SUB_CONCEPTS = ElementPackage.CONCEPT__SUB_CONCEPTS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__PROPERTIES = ElementPackage.CONCEPT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Rule Set</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__RULE_SET = ElementPackage.CONCEPT__RULE_SET;

	/**
	 * The feature id for the '<em><b>State Machine Paths</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__STATE_MACHINE_PATHS = ElementPackage.CONCEPT__STATE_MACHINE_PATHS;

	/**
	 * The feature id for the '<em><b>Root State Machine Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__ROOT_STATE_MACHINE_PATH = ElementPackage.CONCEPT__ROOT_STATE_MACHINE_PATH;

	/**
	 * The feature id for the '<em><b>State Machines</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
//	int DESTINATION_CONCEPT__STATE_MACHINES = ElementPackage.CONCEPT__STATE_MACHINES;

	/**
	 * The feature id for the '<em><b>Root State Machine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
//	int DESTINATION_CONCEPT__ROOT_STATE_MACHINE = ElementPackage.CONCEPT__ROOT_STATE_MACHINE;

	/**
	 * The feature id for the '<em><b>Parent Concept</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__PARENT_CONCEPT = ElementPackage.CONCEPT__PARENT_CONCEPT;

	/**
	 * The feature id for the '<em><b>Parent Concept Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__PARENT_CONCEPT_PATH = ElementPackage.CONCEPT__PARENT_CONCEPT_PATH;

	/**
	 * The feature id for the '<em><b>Super Concept</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__SUPER_CONCEPT = ElementPackage.CONCEPT__SUPER_CONCEPT;

	/**
	 * The feature id for the '<em><b>Super Concept Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__SUPER_CONCEPT_PATH = ElementPackage.CONCEPT__SUPER_CONCEPT_PATH;

	/**
	 * The feature id for the '<em><b>Scorecard</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__SCORECARD = ElementPackage.CONCEPT__SCORECARD;

	/**
	 * The feature id for the '<em><b>Metric</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__METRIC = ElementPackage.CONCEPT__METRIC;

	/**
	 * The feature id for the '<em><b>Contained</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__CONTAINED = ElementPackage.CONCEPT__CONTAINED;

	/**
	 * The feature id for the '<em><b>POJO</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__POJO = ElementPackage.CONCEPT__POJO;

	/**
	 * The feature id for the '<em><b>Transient</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__TRANSIENT = ElementPackage.CONCEPT__TRANSIENT;

	/**
	 * The feature id for the '<em><b>Impl Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__IMPL_CLASS = ElementPackage.CONCEPT__IMPL_CLASS;

	/**
	 * The feature id for the '<em><b>Sub Concepts Path</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__SUB_CONCEPTS_PATH = ElementPackage.CONCEPT__SUB_CONCEPTS_PATH;

	/**
	 * The feature id for the '<em><b>Auto Start State Machine</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT__AUTO_START_STATE_MACHINE = ElementPackage.CONCEPT__AUTO_START_STATE_MACHINE;

	/**
	 * The number of structural features of the '<em>Destination Concept</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_CONCEPT_FEATURE_COUNT = ElementPackage.CONCEPT_FEATURE_COUNT + 0;


	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverManagerImpl <em>Driver Manager</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.DriverManagerImpl
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getDriverManager()
	 * @generated
	 */
	int DRIVER_MANAGER = 4;

	/**
	 * The number of structural features of the '<em>Driver Manager</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVER_MANAGER_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverRegistrationImpl <em>Driver Registration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.DriverRegistrationImpl
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getDriverRegistration()
	 * @generated
	 */
	int DRIVER_REGISTRATION = 5;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVER_REGISTRATION__LABEL = 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVER_REGISTRATION__VERSION = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVER_REGISTRATION__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Channel Descriptor</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVER_REGISTRATION__CHANNEL_DESCRIPTOR = 3;

	/**
	 * The feature id for the '<em><b>Destination Descriptor</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVER_REGISTRATION__DESTINATION_DESCRIPTOR = 4;

	/**
	 * The feature id for the '<em><b>Resources Allowed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVER_REGISTRATION__RESOURCES_ALLOWED = 5;

	/**
	 * The feature id for the '<em><b>Serializer Config</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVER_REGISTRATION__SERIALIZER_CONFIG = 6;

	/**
	 * The feature id for the '<em><b>Choice Configurations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVER_REGISTRATION__CHOICE_CONFIGURATIONS = 7;

	/**
	 * The feature id for the '<em><b>Extended Configurations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVER_REGISTRATION__EXTENDED_CONFIGURATIONS = 8;

	/**
	 * The feature id for the '<em><b>Driver Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVER_REGISTRATION__DRIVER_TYPE = 9;

	/**
	 * The feature id for the '<em><b>Choice</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVER_REGISTRATION__CHOICE = 10;

	/**
	 * The number of structural features of the '<em>Driver Registration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVER_REGISTRATION_FEATURE_COUNT = 11;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.SerializerConfigImpl <em>Serializer Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.SerializerConfigImpl
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getSerializerConfig()
	 * @generated
	 */
	int SERIALIZER_CONFIG = 6;

	/**
	 * The feature id for the '<em><b>User Defined</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIALIZER_CONFIG__USER_DEFINED = 0;

	/**
	 * The feature id for the '<em><b>Serializers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIALIZER_CONFIG__SERIALIZERS = 1;

	/**
	 * The number of structural features of the '<em>Serializer Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIALIZER_CONFIG_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.ChoiceConfigurationImpl <em>Choice Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChoiceConfigurationImpl
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getChoiceConfiguration()
	 * @generated
	 */
	int CHOICE_CONFIGURATION = 7;

	/**
	 * The feature id for the '<em><b>Property Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE_CONFIGURATION__PROPERTY_NAME = 0;

	/**
	 * The feature id for the '<em><b>Property Parent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE_CONFIGURATION__PROPERTY_PARENT = 1;

	/**
	 * The feature id for the '<em><b>Config Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE_CONFIGURATION__CONFIG_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Choices</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE_CONFIGURATION__CHOICES = 3;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE_CONFIGURATION__DEFAULT_VALUE = 4;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE_CONFIGURATION__DISPLAY_NAME = 5;

	/**
	 * The number of structural features of the '<em>Choice Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE_CONFIGURATION_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.ChoiceImpl <em>Choice</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChoiceImpl
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getChoice()
	 * @generated
	 */
	int CHOICE = 8;

	/**
	 * The feature id for the '<em><b>Displayed Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE__DISPLAYED_VALUE = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE__VALUE = 1;

	/**
	 * The feature id for the '<em><b>Extended Configuration</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE__EXTENDED_CONFIGURATION = 2;

	/**
	 * The number of structural features of the '<em>Choice</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.SerializerInfoImpl <em>Serializer Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.SerializerInfoImpl
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getSerializerInfo()
	 * @generated
	 */
	int SERIALIZER_INFO = 9;

	/**
	 * The feature id for the '<em><b>Serializer Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIALIZER_INFO__SERIALIZER_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Serializer Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIALIZER_INFO__SERIALIZER_CLASS = 1;

	/**
	 * The feature id for the '<em><b>Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIALIZER_INFO__DEFAULT = 2;

	/**
	 * The number of structural features of the '<em>Serializer Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIALIZER_INFO_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.ExtendedConfigurationImpl <em>Extended Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ExtendedConfigurationImpl
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getExtendedConfiguration()
	 * @generated
	 */
	int EXTENDED_CONFIGURATION = 10;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENDED_CONFIGURATION__PROPERTIES = 0;

	/**
	 * The number of structural features of the '<em>Extended Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENDED_CONFIGURATION_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.PropertyDescriptorMapEntryImpl <em>Property Descriptor Map Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.PropertyDescriptorMapEntryImpl
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getPropertyDescriptorMapEntry()
	 * @generated
	 */
	int PROPERTY_DESCRIPTOR_MAP_ENTRY = 14;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.PropertyDescriptorMapImpl <em>Property Descriptor Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.PropertyDescriptorMapImpl
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getPropertyDescriptorMap()
	 * @generated
	 */
	int PROPERTY_DESCRIPTOR_MAP = 15;

	/**
	 * The feature id for the '<em><b>Descriptors</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DESCRIPTOR_MAP__DESCRIPTORS = 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.PropertyDescriptorImpl <em>Property Descriptor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.PropertyDescriptorImpl
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getPropertyDescriptor()
	 * @generated
	 */
	int PROPERTY_DESCRIPTOR = 13;

	/**
	 * The number of structural features of the '<em>Property Descriptor Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DESCRIPTOR_MAP_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.DestinationDescriptorImpl <em>Destination Descriptor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.DestinationDescriptorImpl
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getDestinationDescriptor()
	 * @generated
	 */
	int DESTINATION_DESCRIPTOR = 11;

	/**
	 * The feature id for the '<em><b>Descriptors</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_DESCRIPTOR__DESCRIPTORS = PROPERTY_DESCRIPTOR_MAP__DESCRIPTORS;

	/**
	 * The number of structural features of the '<em>Destination Descriptor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESTINATION_DESCRIPTOR_FEATURE_COUNT = PROPERTY_DESCRIPTOR_MAP_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.ChannelDescriptorImpl <em>Descriptor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelDescriptorImpl
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getChannelDescriptor()
	 * @generated
	 */
	int CHANNEL_DESCRIPTOR = 12;

	/**
	 * The feature id for the '<em><b>Descriptors</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANNEL_DESCRIPTOR__DESCRIPTORS = PROPERTY_DESCRIPTOR_MAP__DESCRIPTORS;

	/**
	 * The feature id for the '<em><b>Destination Descriptor</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANNEL_DESCRIPTOR__DESTINATION_DESCRIPTOR = PROPERTY_DESCRIPTOR_MAP_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Descriptor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANNEL_DESCRIPTOR_FEATURE_COUNT = PROPERTY_DESCRIPTOR_MAP_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DESCRIPTOR__DEFAULT_VALUE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DESCRIPTOR__NAME = 1;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DESCRIPTOR__PATTERN = 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DESCRIPTOR__TYPE = 3;

	/**
	 * The feature id for the '<em><b>Mandatory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DESCRIPTOR__MANDATORY = 4;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DESCRIPTOR__DISPLAY_NAME = 5;

	/**
	 * The feature id for the '<em><b>Gv Toggle</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DESCRIPTOR__GV_TOGGLE = 6;

	/**
	 * The number of structural features of the '<em>Property Descriptor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DESCRIPTOR_FEATURE_COUNT = 7;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DESCRIPTOR_MAP_ENTRY__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DESCRIPTOR_MAP_ENTRY__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Property Descriptor Map Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DESCRIPTOR_MAP_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD <em>CONFIG METHOD</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getCONFIG_METHOD()
	 * @generated
	 */
	int CONFIG_METHOD = 20;


	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE <em>DRIVER TYPE</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getDRIVER_TYPE()
	 * @generated
	 */
	int DRIVER_TYPE = 16;


	/**
	 * The number of structural features of the '<em>DRIVER TYPE</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVER_TYPE_FEATURE_COUNT = 0;


	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverTypeInfoImpl <em>Driver Type Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.DriverTypeInfoImpl
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getDriverTypeInfo()
	 * @generated
	 */
	int DRIVER_TYPE_INFO = 17;

	/**
	 * The feature id for the '<em><b>Driver Type Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVER_TYPE_INFO__DRIVER_TYPE_NAME = DRIVER_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Shared Resource Extension</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVER_TYPE_INFO__SHARED_RESOURCE_EXTENSION = DRIVER_TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Driver Type Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVER_TYPE_INFO_FEATURE_COUNT = DRIVER_TYPE_FEATURE_COUNT + 2;


	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.HttpChannelDriverConfigImpl <em>Http Channel Driver Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.HttpChannelDriverConfigImpl
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getHttpChannelDriverConfig()
	 * @generated
	 */
	int HTTP_CHANNEL_DRIVER_CONFIG = 18;

	/**
	 * The feature id for the '<em><b>Config Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_CHANNEL_DRIVER_CONFIG__CONFIG_METHOD = DRIVER_CONFIG__CONFIG_METHOD;

	/**
	 * The feature id for the '<em><b>Reference</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_CHANNEL_DRIVER_CONFIG__REFERENCE = DRIVER_CONFIG__REFERENCE;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_CHANNEL_DRIVER_CONFIG__LABEL = DRIVER_CONFIG__LABEL;

	/**
	 * The feature id for the '<em><b>Channel</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_CHANNEL_DRIVER_CONFIG__CHANNEL = DRIVER_CONFIG__CHANNEL;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_CHANNEL_DRIVER_CONFIG__PROPERTIES = DRIVER_CONFIG__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Destinations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_CHANNEL_DRIVER_CONFIG__DESTINATIONS = DRIVER_CONFIG__DESTINATIONS;

	/**
	 * The feature id for the '<em><b>Extended Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_CHANNEL_DRIVER_CONFIG__EXTENDED_CONFIGURATION = DRIVER_CONFIG__EXTENDED_CONFIGURATION;

	/**
	 * The feature id for the '<em><b>Driver Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_CHANNEL_DRIVER_CONFIG__DRIVER_TYPE = DRIVER_CONFIG__DRIVER_TYPE;

	/**
	 * The feature id for the '<em><b>Choice</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_CHANNEL_DRIVER_CONFIG__CHOICE = DRIVER_CONFIG__CHOICE;

	/**
	 * The feature id for the '<em><b>Web Application Descriptors</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_CHANNEL_DRIVER_CONFIG__WEB_APPLICATION_DESCRIPTORS = DRIVER_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Http Channel Driver Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HTTP_CHANNEL_DRIVER_CONFIG_FEATURE_COUNT = DRIVER_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.WebApplicationDescriptorImpl <em>Web Application Descriptor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.WebApplicationDescriptorImpl
	 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getWebApplicationDescriptor()
	 * @generated
	 */
	int WEB_APPLICATION_DESCRIPTOR = 19;

	/**
	 * The feature id for the '<em><b>Context URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_APPLICATION_DESCRIPTOR__CONTEXT_URI = 0;

	/**
	 * The feature id for the '<em><b>Web App Source Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_APPLICATION_DESCRIPTOR__WEB_APP_SOURCE_PATH = 1;

	/**
	 * The number of structural features of the '<em>Web Application Descriptor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_APPLICATION_DESCRIPTOR_FEATURE_COUNT = 2;

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.service.channel.Channel <em>Channel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Channel</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.Channel
	 * @generated
	 */
	EClass getChannel();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.service.channel.Channel#getDriver <em>Driver</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Driver</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.Channel#getDriver()
	 * @see #getChannel()
	 * @generated
	 */
	EReference getChannel_Driver();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.service.channel.Channel#getDriverManager <em>Driver Manager</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Driver Manager</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.Channel#getDriverManager()
	 * @see #getChannel()
	 * @generated
	 */
	EReference getChannel_DriverManager();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.service.channel.Destination <em>Destination</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Destination</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.Destination
	 * @generated
	 */
	EClass getDestination();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.Destination#getEventURI <em>Event URI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Event URI</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.Destination#getEventURI()
	 * @see #getDestination()
	 * @generated
	 */
	EAttribute getDestination_EventURI();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.Destination#getSerializerDeserializerClass <em>Serializer Deserializer Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Serializer Deserializer Class</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.Destination#getSerializerDeserializerClass()
	 * @see #getDestination()
	 * @generated
	 */
	EAttribute getDestination_SerializerDeserializerClass();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.service.channel.Destination#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Properties</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.Destination#getProperties()
	 * @see #getDestination()
	 * @generated
	 */
	EReference getDestination_Properties();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.service.channel.Destination#getDriverConfig <em>Driver Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Driver Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.Destination#getDriverConfig()
	 * @see #getDestination()
	 * @generated
	 */
	EReference getDestination_DriverConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.Destination#isInputEnabled <em>Input Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Input Enabled</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.Destination#isInputEnabled()
	 * @see #getDestination()
	 * @generated
	 */
	EAttribute getDestination_InputEnabled();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.Destination#isOutputEnabled <em>Output Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Output Enabled</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.Destination#isOutputEnabled()
	 * @see #getDestination()
	 * @generated
	 */
	EAttribute getDestination_OutputEnabled();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig <em>Driver Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Driver Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverConfig
	 * @generated
	 */
	EClass getDriverConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getDriverType <em>Driver Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Driver Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getDriverType()
	 * @see #getDriverConfig()
	 * @generated
	 */
	EReference getDriverConfig_DriverType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getChoice <em>Choice</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Choice</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getChoice()
	 * @see #getDriverConfig()
	 * @generated
	 */
	EReference getDriverConfig_Choice();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getConfigMethod <em>Config Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Config Method</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getConfigMethod()
	 * @see #getDriverConfig()
	 * @generated
	 */
	EAttribute getDriverConfig_ConfigMethod();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getReference <em>Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Reference</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getReference()
	 * @see #getDriverConfig()
	 * @generated
	 */
	EAttribute getDriverConfig_Reference();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getLabel()
	 * @see #getDriverConfig()
	 * @generated
	 */
	EAttribute getDriverConfig_Label();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getChannel <em>Channel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Channel</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getChannel()
	 * @see #getDriverConfig()
	 * @generated
	 */
	EReference getDriverConfig_Channel();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Properties</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getProperties()
	 * @see #getDriverConfig()
	 * @generated
	 */
	EReference getDriverConfig_Properties();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getDestinations <em>Destinations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Destinations</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getDestinations()
	 * @see #getDriverConfig()
	 * @generated
	 */
	EReference getDriverConfig_Destinations();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getExtendedConfiguration <em>Extended Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Extended Configuration</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getExtendedConfiguration()
	 * @see #getDriverConfig()
	 * @generated
	 */
	EReference getDriverConfig_ExtendedConfiguration();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.service.channel.DestinationConcept <em>Destination Concept</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Destination Concept</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DestinationConcept
	 * @generated
	 */
	EClass getDestinationConcept();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.service.channel.DriverManager <em>Driver Manager</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Driver Manager</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverManager
	 * @generated
	 */
	EClass getDriverManager();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration <em>Driver Registration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Driver Registration</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverRegistration
	 * @generated
	 */
	EClass getDriverRegistration();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getDriverType <em>Driver Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Driver Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getDriverType()
	 * @see #getDriverRegistration()
	 * @generated
	 */
	EReference getDriverRegistration_DriverType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getChoice <em>Choice</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Choice</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getChoice()
	 * @see #getDriverRegistration()
	 * @generated
	 */
	EReference getDriverRegistration_Choice();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getLabel()
	 * @see #getDriverRegistration()
	 * @generated
	 */
	EAttribute getDriverRegistration_Label();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getVersion()
	 * @see #getDriverRegistration()
	 * @generated
	 */
	EAttribute getDriverRegistration_Version();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getDescription()
	 * @see #getDriverRegistration()
	 * @generated
	 */
	EAttribute getDriverRegistration_Description();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getChannelDescriptor <em>Channel Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Channel Descriptor</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getChannelDescriptor()
	 * @see #getDriverRegistration()
	 * @generated
	 */
	EReference getDriverRegistration_ChannelDescriptor();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getDestinationDescriptor <em>Destination Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Destination Descriptor</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getDestinationDescriptor()
	 * @see #getDriverRegistration()
	 * @generated
	 */
	EReference getDriverRegistration_DestinationDescriptor();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getResourcesAllowed <em>Resources Allowed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Resources Allowed</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getResourcesAllowed()
	 * @see #getDriverRegistration()
	 * @generated
	 */
	EAttribute getDriverRegistration_ResourcesAllowed();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getSerializerConfig <em>Serializer Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Serializer Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getSerializerConfig()
	 * @see #getDriverRegistration()
	 * @generated
	 */
	EReference getDriverRegistration_SerializerConfig();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getChoiceConfigurations <em>Choice Configurations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Choice Configurations</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getChoiceConfigurations()
	 * @see #getDriverRegistration()
	 * @generated
	 */
	EReference getDriverRegistration_ChoiceConfigurations();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getExtendedConfigurations <em>Extended Configurations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Extended Configurations</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverRegistration#getExtendedConfigurations()
	 * @see #getDriverRegistration()
	 * @generated
	 */
	EReference getDriverRegistration_ExtendedConfigurations();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.service.channel.SerializerConfig <em>Serializer Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Serializer Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.SerializerConfig
	 * @generated
	 */
	EClass getSerializerConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.SerializerConfig#isUserDefined <em>User Defined</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>User Defined</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.SerializerConfig#isUserDefined()
	 * @see #getSerializerConfig()
	 * @generated
	 */
	EAttribute getSerializerConfig_UserDefined();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.designtime.core.model.service.channel.SerializerConfig#getSerializers <em>Serializers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Serializers</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.SerializerConfig#getSerializers()
	 * @see #getSerializerConfig()
	 * @generated
	 */
	EReference getSerializerConfig_Serializers();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration <em>Choice Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Choice Configuration</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration
	 * @generated
	 */
	EClass getChoiceConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration#getPropertyName <em>Property Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration#getPropertyName()
	 * @see #getChoiceConfiguration()
	 * @generated
	 */
	EAttribute getChoiceConfiguration_PropertyName();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration#getPropertyParent <em>Property Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property Parent</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration#getPropertyParent()
	 * @see #getChoiceConfiguration()
	 * @generated
	 */
	EAttribute getChoiceConfiguration_PropertyParent();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration#getConfigType <em>Config Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Config Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration#getConfigType()
	 * @see #getChoiceConfiguration()
	 * @generated
	 */
	EAttribute getChoiceConfiguration_ConfigType();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration#getChoices <em>Choices</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Choices</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration#getChoices()
	 * @see #getChoiceConfiguration()
	 * @generated
	 */
	EReference getChoiceConfiguration_Choices();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration#getDefaultValue <em>Default Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Value</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration#getDefaultValue()
	 * @see #getChoiceConfiguration()
	 * @generated
	 */
	EAttribute getChoiceConfiguration_DefaultValue();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration#getDisplayName()
	 * @see #getChoiceConfiguration()
	 * @generated
	 */
	EAttribute getChoiceConfiguration_DisplayName();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.service.channel.Choice <em>Choice</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Choice</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.Choice
	 * @generated
	 */
	EClass getChoice();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.Choice#getDisplayedValue <em>Displayed Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Displayed Value</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.Choice#getDisplayedValue()
	 * @see #getChoice()
	 * @generated
	 */
	EAttribute getChoice_DisplayedValue();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.Choice#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.Choice#getValue()
	 * @see #getChoice()
	 * @generated
	 */
	EAttribute getChoice_Value();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.service.channel.Choice#getExtendedConfiguration <em>Extended Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Extended Configuration</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.Choice#getExtendedConfiguration()
	 * @see #getChoice()
	 * @generated
	 */
	EReference getChoice_ExtendedConfiguration();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.service.channel.SerializerInfo <em>Serializer Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Serializer Info</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.SerializerInfo
	 * @generated
	 */
	EClass getSerializerInfo();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.SerializerInfo#getSerializerType <em>Serializer Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Serializer Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.SerializerInfo#getSerializerType()
	 * @see #getSerializerInfo()
	 * @generated
	 */
	EAttribute getSerializerInfo_SerializerType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.SerializerInfo#getSerializerClass <em>Serializer Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Serializer Class</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.SerializerInfo#getSerializerClass()
	 * @see #getSerializerInfo()
	 * @generated
	 */
	EAttribute getSerializerInfo_SerializerClass();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.SerializerInfo#isDefault <em>Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.SerializerInfo#isDefault()
	 * @see #getSerializerInfo()
	 * @generated
	 */
	EAttribute getSerializerInfo_Default();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.service.channel.ExtendedConfiguration <em>Extended Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Extended Configuration</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.ExtendedConfiguration
	 * @generated
	 */
	EClass getExtendedConfiguration();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.service.channel.ExtendedConfiguration#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.ExtendedConfiguration#getProperties()
	 * @see #getExtendedConfiguration()
	 * @generated
	 */
	EReference getExtendedConfiguration_Properties();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.service.channel.DestinationDescriptor <em>Destination Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Destination Descriptor</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DestinationDescriptor
	 * @generated
	 */
	EClass getDestinationDescriptor();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.service.channel.ChannelDescriptor <em>Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Descriptor</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelDescriptor
	 * @generated
	 */
	EClass getChannelDescriptor();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.service.channel.ChannelDescriptor#getDestinationDescriptor <em>Destination Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Destination Descriptor</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelDescriptor#getDestinationDescriptor()
	 * @see #getChannelDescriptor()
	 * @generated
	 */
	EReference getChannelDescriptor_DestinationDescriptor();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor <em>Property Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Descriptor</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor
	 * @generated
	 */
	EClass getPropertyDescriptor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#getDefaultValue <em>Default Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Value</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#getDefaultValue()
	 * @see #getPropertyDescriptor()
	 * @generated
	 */
	EAttribute getPropertyDescriptor_DefaultValue();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#getName()
	 * @see #getPropertyDescriptor()
	 * @generated
	 */
	EAttribute getPropertyDescriptor_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#getPattern <em>Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pattern</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#getPattern()
	 * @see #getPropertyDescriptor()
	 * @generated
	 */
	EAttribute getPropertyDescriptor_Pattern();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#getType()
	 * @see #getPropertyDescriptor()
	 * @generated
	 */
	EAttribute getPropertyDescriptor_Type();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#isMandatory <em>Mandatory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mandatory</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#isMandatory()
	 * @see #getPropertyDescriptor()
	 * @generated
	 */
	EAttribute getPropertyDescriptor_Mandatory();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#getDisplayName()
	 * @see #getPropertyDescriptor()
	 * @generated
	 */
	EAttribute getPropertyDescriptor_DisplayName();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#isGvToggle <em>Gv Toggle</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Gv Toggle</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor#isGvToggle()
	 * @see #getPropertyDescriptor()
	 * @generated
	 */
	EAttribute getPropertyDescriptor_GvToggle();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMapEntry <em>Property Descriptor Map Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Descriptor Map Entry</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMapEntry
	 * @generated
	 */
	EClass getPropertyDescriptorMapEntry();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMapEntry#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMapEntry#getKey()
	 * @see #getPropertyDescriptorMapEntry()
	 * @generated
	 */
	EAttribute getPropertyDescriptorMapEntry_Key();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMapEntry#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMapEntry#getValue()
	 * @see #getPropertyDescriptorMapEntry()
	 * @generated
	 */
	EReference getPropertyDescriptorMapEntry_Value();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMap <em>Property Descriptor Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Descriptor Map</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMap
	 * @generated
	 */
	EClass getPropertyDescriptorMap();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMap#getDescriptors <em>Descriptors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Descriptors</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMap#getDescriptors()
	 * @see #getPropertyDescriptorMap()
	 * @generated
	 */
	EReference getPropertyDescriptorMap_Descriptors();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD <em>CONFIG METHOD</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>CONFIG METHOD</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD
	 * @generated
	 */
	EEnum getCONFIG_METHOD();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE <em>DRIVER TYPE</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DRIVER TYPE</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE
	 * @generated
	 */
	EClass getDRIVER_TYPE();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.service.channel.DriverTypeInfo <em>Driver Type Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Driver Type Info</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverTypeInfo
	 * @generated
	 */
	EClass getDriverTypeInfo();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.DriverTypeInfo#getDriverTypeName <em>Driver Type Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Driver Type Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverTypeInfo#getDriverTypeName()
	 * @see #getDriverTypeInfo()
	 * @generated
	 */
	EAttribute getDriverTypeInfo_DriverTypeName();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.DriverTypeInfo#getSharedResourceExtension <em>Shared Resource Extension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Shared Resource Extension</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.DriverTypeInfo#getSharedResourceExtension()
	 * @see #getDriverTypeInfo()
	 * @generated
	 */
	EAttribute getDriverTypeInfo_SharedResourceExtension();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.service.channel.HttpChannelDriverConfig <em>Http Channel Driver Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Http Channel Driver Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.HttpChannelDriverConfig
	 * @generated
	 */
	EClass getHttpChannelDriverConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.service.channel.HttpChannelDriverConfig#getWebApplicationDescriptors <em>Web Application Descriptors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Web Application Descriptors</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.HttpChannelDriverConfig#getWebApplicationDescriptors()
	 * @see #getHttpChannelDriverConfig()
	 * @generated
	 */
	EReference getHttpChannelDriverConfig_WebApplicationDescriptors();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.service.channel.WebApplicationDescriptor <em>Web Application Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Web Application Descriptor</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.WebApplicationDescriptor
	 * @generated
	 */
	EClass getWebApplicationDescriptor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.WebApplicationDescriptor#getContextURI <em>Context URI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Context URI</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.WebApplicationDescriptor#getContextURI()
	 * @see #getWebApplicationDescriptor()
	 * @generated
	 */
	EAttribute getWebApplicationDescriptor_ContextURI();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.service.channel.WebApplicationDescriptor#getWebAppSourcePath <em>Web App Source Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Web App Source Path</em>'.
	 * @see com.tibco.cep.designtime.core.model.service.channel.WebApplicationDescriptor#getWebAppSourcePath()
	 * @see #getWebApplicationDescriptor()
	 * @generated
	 */
	EAttribute getWebApplicationDescriptor_WebAppSourcePath();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ChannelFactory getChannelFactory();

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
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.ChannelImpl <em>Channel</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelImpl
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getChannel()
		 * @generated
		 */
		EClass CHANNEL = eINSTANCE.getChannel();

		/**
		 * The meta object literal for the '<em><b>Driver</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANNEL__DRIVER = eINSTANCE.getChannel_Driver();

		/**
		 * The meta object literal for the '<em><b>Driver Manager</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANNEL__DRIVER_MANAGER = eINSTANCE.getChannel_DriverManager();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.DestinationImpl <em>Destination</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.DestinationImpl
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getDestination()
		 * @generated
		 */
		EClass DESTINATION = eINSTANCE.getDestination();

		/**
		 * The meta object literal for the '<em><b>Event URI</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DESTINATION__EVENT_URI = eINSTANCE.getDestination_EventURI();

		/**
		 * The meta object literal for the '<em><b>Serializer Deserializer Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DESTINATION__SERIALIZER_DESERIALIZER_CLASS = eINSTANCE.getDestination_SerializerDeserializerClass();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DESTINATION__PROPERTIES = eINSTANCE.getDestination_Properties();

		/**
		 * The meta object literal for the '<em><b>Driver Config</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DESTINATION__DRIVER_CONFIG = eINSTANCE.getDestination_DriverConfig();

		/**
		 * The meta object literal for the '<em><b>Input Enabled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DESTINATION__INPUT_ENABLED = eINSTANCE.getDestination_InputEnabled();

		/**
		 * The meta object literal for the '<em><b>Output Enabled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DESTINATION__OUTPUT_ENABLED = eINSTANCE.getDestination_OutputEnabled();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverConfigImpl <em>Driver Config</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.DriverConfigImpl
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getDriverConfig()
		 * @generated
		 */
		EClass DRIVER_CONFIG = eINSTANCE.getDriverConfig();

		/**
		 * The meta object literal for the '<em><b>Driver Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DRIVER_CONFIG__DRIVER_TYPE = eINSTANCE.getDriverConfig_DriverType();

		/**
		 * The meta object literal for the '<em><b>Choice</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DRIVER_CONFIG__CHOICE = eINSTANCE.getDriverConfig_Choice();

		/**
		 * The meta object literal for the '<em><b>Config Method</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DRIVER_CONFIG__CONFIG_METHOD = eINSTANCE.getDriverConfig_ConfigMethod();

		/**
		 * The meta object literal for the '<em><b>Reference</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DRIVER_CONFIG__REFERENCE = eINSTANCE.getDriverConfig_Reference();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DRIVER_CONFIG__LABEL = eINSTANCE.getDriverConfig_Label();

		/**
		 * The meta object literal for the '<em><b>Channel</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DRIVER_CONFIG__CHANNEL = eINSTANCE.getDriverConfig_Channel();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DRIVER_CONFIG__PROPERTIES = eINSTANCE.getDriverConfig_Properties();

		/**
		 * The meta object literal for the '<em><b>Destinations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DRIVER_CONFIG__DESTINATIONS = eINSTANCE.getDriverConfig_Destinations();

		/**
		 * The meta object literal for the '<em><b>Extended Configuration</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DRIVER_CONFIG__EXTENDED_CONFIGURATION = eINSTANCE.getDriverConfig_ExtendedConfiguration();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.DestinationConceptImpl <em>Destination Concept</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.DestinationConceptImpl
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getDestinationConcept()
		 * @generated
		 */
		EClass DESTINATION_CONCEPT = eINSTANCE.getDestinationConcept();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverManagerImpl <em>Driver Manager</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.DriverManagerImpl
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getDriverManager()
		 * @generated
		 */
		EClass DRIVER_MANAGER = eINSTANCE.getDriverManager();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverRegistrationImpl <em>Driver Registration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.DriverRegistrationImpl
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getDriverRegistration()
		 * @generated
		 */
		EClass DRIVER_REGISTRATION = eINSTANCE.getDriverRegistration();

		/**
		 * The meta object literal for the '<em><b>Driver Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DRIVER_REGISTRATION__DRIVER_TYPE = eINSTANCE.getDriverRegistration_DriverType();

		/**
		 * The meta object literal for the '<em><b>Choice</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DRIVER_REGISTRATION__CHOICE = eINSTANCE.getDriverRegistration_Choice();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DRIVER_REGISTRATION__LABEL = eINSTANCE.getDriverRegistration_Label();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DRIVER_REGISTRATION__VERSION = eINSTANCE.getDriverRegistration_Version();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DRIVER_REGISTRATION__DESCRIPTION = eINSTANCE.getDriverRegistration_Description();

		/**
		 * The meta object literal for the '<em><b>Channel Descriptor</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DRIVER_REGISTRATION__CHANNEL_DESCRIPTOR = eINSTANCE.getDriverRegistration_ChannelDescriptor();

		/**
		 * The meta object literal for the '<em><b>Destination Descriptor</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DRIVER_REGISTRATION__DESTINATION_DESCRIPTOR = eINSTANCE.getDriverRegistration_DestinationDescriptor();

		/**
		 * The meta object literal for the '<em><b>Resources Allowed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DRIVER_REGISTRATION__RESOURCES_ALLOWED = eINSTANCE.getDriverRegistration_ResourcesAllowed();

		/**
		 * The meta object literal for the '<em><b>Serializer Config</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DRIVER_REGISTRATION__SERIALIZER_CONFIG = eINSTANCE.getDriverRegistration_SerializerConfig();

		/**
		 * The meta object literal for the '<em><b>Choice Configurations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DRIVER_REGISTRATION__CHOICE_CONFIGURATIONS = eINSTANCE.getDriverRegistration_ChoiceConfigurations();

		/**
		 * The meta object literal for the '<em><b>Extended Configurations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DRIVER_REGISTRATION__EXTENDED_CONFIGURATIONS = eINSTANCE.getDriverRegistration_ExtendedConfigurations();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.SerializerConfigImpl <em>Serializer Config</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.SerializerConfigImpl
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getSerializerConfig()
		 * @generated
		 */
		EClass SERIALIZER_CONFIG = eINSTANCE.getSerializerConfig();

		/**
		 * The meta object literal for the '<em><b>User Defined</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERIALIZER_CONFIG__USER_DEFINED = eINSTANCE.getSerializerConfig_UserDefined();

		/**
		 * The meta object literal for the '<em><b>Serializers</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SERIALIZER_CONFIG__SERIALIZERS = eINSTANCE.getSerializerConfig_Serializers();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.ChoiceConfigurationImpl <em>Choice Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChoiceConfigurationImpl
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getChoiceConfiguration()
		 * @generated
		 */
		EClass CHOICE_CONFIGURATION = eINSTANCE.getChoiceConfiguration();

		/**
		 * The meta object literal for the '<em><b>Property Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHOICE_CONFIGURATION__PROPERTY_NAME = eINSTANCE.getChoiceConfiguration_PropertyName();

		/**
		 * The meta object literal for the '<em><b>Property Parent</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHOICE_CONFIGURATION__PROPERTY_PARENT = eINSTANCE.getChoiceConfiguration_PropertyParent();

		/**
		 * The meta object literal for the '<em><b>Config Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHOICE_CONFIGURATION__CONFIG_TYPE = eINSTANCE.getChoiceConfiguration_ConfigType();

		/**
		 * The meta object literal for the '<em><b>Choices</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHOICE_CONFIGURATION__CHOICES = eINSTANCE.getChoiceConfiguration_Choices();

		/**
		 * The meta object literal for the '<em><b>Default Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHOICE_CONFIGURATION__DEFAULT_VALUE = eINSTANCE.getChoiceConfiguration_DefaultValue();

		/**
		 * The meta object literal for the '<em><b>Display Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHOICE_CONFIGURATION__DISPLAY_NAME = eINSTANCE.getChoiceConfiguration_DisplayName();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.ChoiceImpl <em>Choice</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChoiceImpl
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getChoice()
		 * @generated
		 */
		EClass CHOICE = eINSTANCE.getChoice();

		/**
		 * The meta object literal for the '<em><b>Displayed Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHOICE__DISPLAYED_VALUE = eINSTANCE.getChoice_DisplayedValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHOICE__VALUE = eINSTANCE.getChoice_Value();

		/**
		 * The meta object literal for the '<em><b>Extended Configuration</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHOICE__EXTENDED_CONFIGURATION = eINSTANCE.getChoice_ExtendedConfiguration();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.SerializerInfoImpl <em>Serializer Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.SerializerInfoImpl
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getSerializerInfo()
		 * @generated
		 */
		EClass SERIALIZER_INFO = eINSTANCE.getSerializerInfo();

		/**
		 * The meta object literal for the '<em><b>Serializer Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERIALIZER_INFO__SERIALIZER_TYPE = eINSTANCE.getSerializerInfo_SerializerType();

		/**
		 * The meta object literal for the '<em><b>Serializer Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERIALIZER_INFO__SERIALIZER_CLASS = eINSTANCE.getSerializerInfo_SerializerClass();

		/**
		 * The meta object literal for the '<em><b>Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERIALIZER_INFO__DEFAULT = eINSTANCE.getSerializerInfo_Default();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.ExtendedConfigurationImpl <em>Extended Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ExtendedConfigurationImpl
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getExtendedConfiguration()
		 * @generated
		 */
		EClass EXTENDED_CONFIGURATION = eINSTANCE.getExtendedConfiguration();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTENDED_CONFIGURATION__PROPERTIES = eINSTANCE.getExtendedConfiguration_Properties();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.DestinationDescriptorImpl <em>Destination Descriptor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.DestinationDescriptorImpl
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getDestinationDescriptor()
		 * @generated
		 */
		EClass DESTINATION_DESCRIPTOR = eINSTANCE.getDestinationDescriptor();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.ChannelDescriptorImpl <em>Descriptor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelDescriptorImpl
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getChannelDescriptor()
		 * @generated
		 */
		EClass CHANNEL_DESCRIPTOR = eINSTANCE.getChannelDescriptor();

		/**
		 * The meta object literal for the '<em><b>Destination Descriptor</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANNEL_DESCRIPTOR__DESTINATION_DESCRIPTOR = eINSTANCE.getChannelDescriptor_DestinationDescriptor();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.PropertyDescriptorImpl <em>Property Descriptor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.PropertyDescriptorImpl
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getPropertyDescriptor()
		 * @generated
		 */
		EClass PROPERTY_DESCRIPTOR = eINSTANCE.getPropertyDescriptor();

		/**
		 * The meta object literal for the '<em><b>Default Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_DESCRIPTOR__DEFAULT_VALUE = eINSTANCE.getPropertyDescriptor_DefaultValue();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_DESCRIPTOR__NAME = eINSTANCE.getPropertyDescriptor_Name();

		/**
		 * The meta object literal for the '<em><b>Pattern</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_DESCRIPTOR__PATTERN = eINSTANCE.getPropertyDescriptor_Pattern();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_DESCRIPTOR__TYPE = eINSTANCE.getPropertyDescriptor_Type();

		/**
		 * The meta object literal for the '<em><b>Mandatory</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_DESCRIPTOR__MANDATORY = eINSTANCE.getPropertyDescriptor_Mandatory();

		/**
		 * The meta object literal for the '<em><b>Display Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_DESCRIPTOR__DISPLAY_NAME = eINSTANCE.getPropertyDescriptor_DisplayName();

		/**
		 * The meta object literal for the '<em><b>Gv Toggle</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_DESCRIPTOR__GV_TOGGLE = eINSTANCE.getPropertyDescriptor_GvToggle();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.PropertyDescriptorMapEntryImpl <em>Property Descriptor Map Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.PropertyDescriptorMapEntryImpl
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getPropertyDescriptorMapEntry()
		 * @generated
		 */
		EClass PROPERTY_DESCRIPTOR_MAP_ENTRY = eINSTANCE.getPropertyDescriptorMapEntry();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_DESCRIPTOR_MAP_ENTRY__KEY = eINSTANCE.getPropertyDescriptorMapEntry_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_DESCRIPTOR_MAP_ENTRY__VALUE = eINSTANCE.getPropertyDescriptorMapEntry_Value();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.PropertyDescriptorMapImpl <em>Property Descriptor Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.PropertyDescriptorMapImpl
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getPropertyDescriptorMap()
		 * @generated
		 */
		EClass PROPERTY_DESCRIPTOR_MAP = eINSTANCE.getPropertyDescriptorMap();

		/**
		 * The meta object literal for the '<em><b>Descriptors</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_DESCRIPTOR_MAP__DESCRIPTORS = eINSTANCE.getPropertyDescriptorMap_Descriptors();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD <em>CONFIG METHOD</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getCONFIG_METHOD()
		 * @generated
		 */
		EEnum CONFIG_METHOD = eINSTANCE.getCONFIG_METHOD();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE <em>DRIVER TYPE</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getDRIVER_TYPE()
		 * @generated
		 */
		EClass DRIVER_TYPE = eINSTANCE.getDRIVER_TYPE();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverTypeInfoImpl <em>Driver Type Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.DriverTypeInfoImpl
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getDriverTypeInfo()
		 * @generated
		 */
		EClass DRIVER_TYPE_INFO = eINSTANCE.getDriverTypeInfo();

		/**
		 * The meta object literal for the '<em><b>Driver Type Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DRIVER_TYPE_INFO__DRIVER_TYPE_NAME = eINSTANCE.getDriverTypeInfo_DriverTypeName();

		/**
		 * The meta object literal for the '<em><b>Shared Resource Extension</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DRIVER_TYPE_INFO__SHARED_RESOURCE_EXTENSION = eINSTANCE.getDriverTypeInfo_SharedResourceExtension();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.HttpChannelDriverConfigImpl <em>Http Channel Driver Config</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.HttpChannelDriverConfigImpl
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getHttpChannelDriverConfig()
		 * @generated
		 */
		EClass HTTP_CHANNEL_DRIVER_CONFIG = eINSTANCE.getHttpChannelDriverConfig();

		/**
		 * The meta object literal for the '<em><b>Web Application Descriptors</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HTTP_CHANNEL_DRIVER_CONFIG__WEB_APPLICATION_DESCRIPTORS = eINSTANCE.getHttpChannelDriverConfig_WebApplicationDescriptors();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.service.channel.impl.WebApplicationDescriptorImpl <em>Web Application Descriptor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.WebApplicationDescriptorImpl
		 * @see com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl#getWebApplicationDescriptor()
		 * @generated
		 */
		EClass WEB_APPLICATION_DESCRIPTOR = eINSTANCE.getWebApplicationDescriptor();

		/**
		 * The meta object literal for the '<em><b>Context URI</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WEB_APPLICATION_DESCRIPTOR__CONTEXT_URI = eINSTANCE.getWebApplicationDescriptor_ContextURI();

		/**
		 * The meta object literal for the '<em><b>Web App Source Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WEB_APPLICATION_DESCRIPTOR__WEB_APP_SOURCE_PATH = eINSTANCE.getWebApplicationDescriptor_WebAppSourcePath();

	}

} //ChannelPackage
