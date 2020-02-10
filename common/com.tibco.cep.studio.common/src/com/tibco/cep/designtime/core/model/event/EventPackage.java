/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.event;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see com.tibco.cep.designtime.core.model.event.EventFactory
 * @model kind="package"
 * @generated
 */
public interface EventPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "event";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/designtime/core/model/event";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "event";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EventPackage eINSTANCE = com.tibco.cep.designtime.core.model.event.impl.EventPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.event.impl.EventImpl <em>Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.event.impl.EventImpl
	 * @see com.tibco.cep.designtime.core.model.event.impl.EventPackageImpl#getEvent()
	 * @generated
	 */
	int EVENT = 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__NAMESPACE = ModelPackage.RULE_PARTICIPANT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__FOLDER = ModelPackage.RULE_PARTICIPANT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__NAME = ModelPackage.RULE_PARTICIPANT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__DESCRIPTION = ModelPackage.RULE_PARTICIPANT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__LAST_MODIFIED = ModelPackage.RULE_PARTICIPANT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__GUID = ModelPackage.RULE_PARTICIPANT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__ONTOLOGY = ModelPackage.RULE_PARTICIPANT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__EXTENDED_PROPERTIES = ModelPackage.RULE_PARTICIPANT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__HIDDEN_PROPERTIES = ModelPackage.RULE_PARTICIPANT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__TRANSIENT_PROPERTIES = ModelPackage.RULE_PARTICIPANT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__OWNER_PROJECT_NAME = ModelPackage.RULE_PARTICIPANT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__TYPE = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Super Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__SUPER_EVENT = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Super Event Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__SUPER_EVENT_PATH = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Sub Events</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__SUB_EVENTS = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Ancestor Properties</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__ANCESTOR_PROPERTIES = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Payload String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__PAYLOAD_STRING = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Ttl</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__TTL = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Ttl Units</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__TTL_UNITS = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__PROPERTIES = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Rule Set</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__RULE_SET = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Specified Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__SPECIFIED_TIME = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Serialization Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__SERIALIZATION_FORMAT = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Namespace Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__NAMESPACE_ENTRIES = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Registry Import Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__REGISTRY_IMPORT_ENTRIES = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Sub Events Path</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__SUB_EVENTS_PATH = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Expiry Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__EXPIRY_ACTION = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Payload</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__PAYLOAD = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Retry On Exception</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__RETRY_ON_EXCEPTION = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 17;

	/**
	 * The number of structural features of the '<em>Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_FEATURE_COUNT = ModelPackage.RULE_PARTICIPANT_FEATURE_COUNT + 18;


	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.event.impl.NamespaceEntryImpl <em>Namespace Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.event.impl.NamespaceEntryImpl
	 * @see com.tibco.cep.designtime.core.model.event.impl.EventPackageImpl#getNamespaceEntry()
	 * @generated
	 */
	int NAMESPACE_ENTRY = 1;

	/**
	 * The feature id for the '<em><b>Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE_ENTRY__PREFIX = 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE_ENTRY__NAMESPACE = 1;

	/**
	 * The number of structural features of the '<em>Namespace Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.event.impl.ImportRegistryEntryImpl <em>Import Registry Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.event.impl.ImportRegistryEntryImpl
	 * @see com.tibco.cep.designtime.core.model.event.impl.EventPackageImpl#getImportRegistryEntry()
	 * @generated
	 */
	int IMPORT_REGISTRY_ENTRY = 2;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_REGISTRY_ENTRY__LOCATION = 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_REGISTRY_ENTRY__NAMESPACE = 1;

	/**
	 * The number of structural features of the '<em>Import Registry Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_REGISTRY_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.event.impl.SimpleEventImpl <em>Simple Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.event.impl.SimpleEventImpl
	 * @see com.tibco.cep.designtime.core.model.event.impl.EventPackageImpl#getSimpleEvent()
	 * @generated
	 */
	int SIMPLE_EVENT = 3;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__NAMESPACE = EVENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__FOLDER = EVENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__NAME = EVENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__DESCRIPTION = EVENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__LAST_MODIFIED = EVENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__GUID = EVENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__ONTOLOGY = EVENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__EXTENDED_PROPERTIES = EVENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__HIDDEN_PROPERTIES = EVENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__TRANSIENT_PROPERTIES = EVENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__OWNER_PROJECT_NAME = EVENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__TYPE = EVENT__TYPE;

	/**
	 * The feature id for the '<em><b>Super Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__SUPER_EVENT = EVENT__SUPER_EVENT;

	/**
	 * The feature id for the '<em><b>Super Event Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__SUPER_EVENT_PATH = EVENT__SUPER_EVENT_PATH;

	/**
	 * The feature id for the '<em><b>Sub Events</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__SUB_EVENTS = EVENT__SUB_EVENTS;

	/**
	 * The feature id for the '<em><b>Ancestor Properties</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__ANCESTOR_PROPERTIES = EVENT__ANCESTOR_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Payload String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__PAYLOAD_STRING = EVENT__PAYLOAD_STRING;

	/**
	 * The feature id for the '<em><b>Ttl</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__TTL = EVENT__TTL;

	/**
	 * The feature id for the '<em><b>Ttl Units</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__TTL_UNITS = EVENT__TTL_UNITS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__PROPERTIES = EVENT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Rule Set</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__RULE_SET = EVENT__RULE_SET;

	/**
	 * The feature id for the '<em><b>Specified Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__SPECIFIED_TIME = EVENT__SPECIFIED_TIME;

	/**
	 * The feature id for the '<em><b>Serialization Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__SERIALIZATION_FORMAT = EVENT__SERIALIZATION_FORMAT;

	/**
	 * The feature id for the '<em><b>Namespace Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__NAMESPACE_ENTRIES = EVENT__NAMESPACE_ENTRIES;

	/**
	 * The feature id for the '<em><b>Registry Import Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__REGISTRY_IMPORT_ENTRIES = EVENT__REGISTRY_IMPORT_ENTRIES;

	/**
	 * The feature id for the '<em><b>Sub Events Path</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__SUB_EVENTS_PATH = EVENT__SUB_EVENTS_PATH;

	/**
	 * The feature id for the '<em><b>Expiry Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__EXPIRY_ACTION = EVENT__EXPIRY_ACTION;

	/**
	 * The feature id for the '<em><b>Payload</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__PAYLOAD = EVENT__PAYLOAD;

	/**
	 * The feature id for the '<em><b>Retry On Exception</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__RETRY_ON_EXCEPTION = EVENT__RETRY_ON_EXCEPTION;

	/**
	 * The feature id for the '<em><b>Channel URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__CHANNEL_URI = EVENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Destination Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__DESTINATION_NAME = EVENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Simple Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT_FEATURE_COUNT = EVENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.event.impl.TimeEventImpl <em>Time Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.event.impl.TimeEventImpl
	 * @see com.tibco.cep.designtime.core.model.event.impl.EventPackageImpl#getTimeEvent()
	 * @generated
	 */
	int TIME_EVENT = 4;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__NAMESPACE = EVENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__FOLDER = EVENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__NAME = EVENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__DESCRIPTION = EVENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__LAST_MODIFIED = EVENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__GUID = EVENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__ONTOLOGY = EVENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__EXTENDED_PROPERTIES = EVENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__HIDDEN_PROPERTIES = EVENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__TRANSIENT_PROPERTIES = EVENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__OWNER_PROJECT_NAME = EVENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__TYPE = EVENT__TYPE;

	/**
	 * The feature id for the '<em><b>Super Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__SUPER_EVENT = EVENT__SUPER_EVENT;

	/**
	 * The feature id for the '<em><b>Super Event Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__SUPER_EVENT_PATH = EVENT__SUPER_EVENT_PATH;

	/**
	 * The feature id for the '<em><b>Sub Events</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__SUB_EVENTS = EVENT__SUB_EVENTS;

	/**
	 * The feature id for the '<em><b>Ancestor Properties</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__ANCESTOR_PROPERTIES = EVENT__ANCESTOR_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Payload String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__PAYLOAD_STRING = EVENT__PAYLOAD_STRING;

	/**
	 * The feature id for the '<em><b>Ttl</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__TTL = EVENT__TTL;

	/**
	 * The feature id for the '<em><b>Ttl Units</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__TTL_UNITS = EVENT__TTL_UNITS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__PROPERTIES = EVENT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Rule Set</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__RULE_SET = EVENT__RULE_SET;

	/**
	 * The feature id for the '<em><b>Specified Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__SPECIFIED_TIME = EVENT__SPECIFIED_TIME;

	/**
	 * The feature id for the '<em><b>Serialization Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__SERIALIZATION_FORMAT = EVENT__SERIALIZATION_FORMAT;

	/**
	 * The feature id for the '<em><b>Namespace Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__NAMESPACE_ENTRIES = EVENT__NAMESPACE_ENTRIES;

	/**
	 * The feature id for the '<em><b>Registry Import Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__REGISTRY_IMPORT_ENTRIES = EVENT__REGISTRY_IMPORT_ENTRIES;

	/**
	 * The feature id for the '<em><b>Sub Events Path</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__SUB_EVENTS_PATH = EVENT__SUB_EVENTS_PATH;

	/**
	 * The feature id for the '<em><b>Expiry Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__EXPIRY_ACTION = EVENT__EXPIRY_ACTION;

	/**
	 * The feature id for the '<em><b>Payload</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__PAYLOAD = EVENT__PAYLOAD;

	/**
	 * The feature id for the '<em><b>Retry On Exception</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__RETRY_ON_EXCEPTION = EVENT__RETRY_ON_EXCEPTION;

	/**
	 * The feature id for the '<em><b>Schedule Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__SCHEDULE_TYPE = EVENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Time Event Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__TIME_EVENT_COUNT = EVENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Interval</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__INTERVAL = EVENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Interval Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__INTERVAL_UNIT = EVENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Time Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT_FEATURE_COUNT = EVENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.event.impl.UserPropertyImpl <em>User Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.event.impl.UserPropertyImpl
	 * @see com.tibco.cep.designtime.core.model.event.impl.EventPackageImpl#getUserProperty()
	 * @generated
	 */
	int USER_PROPERTY = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_PROPERTY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_PROPERTY__TYPE = 1;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_PROPERTY__EXTENDED_PROPERTIES = 2;

	/**
	 * The number of structural features of the '<em>User Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_PROPERTY_FEATURE_COUNT = 3;


	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.event.impl.AdvisoryEventImpl <em>Advisory Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.event.impl.AdvisoryEventImpl
	 * @see com.tibco.cep.designtime.core.model.event.impl.EventPackageImpl#getAdvisoryEvent()
	 * @generated
	 */
	int ADVISORY_EVENT = 6;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__NAMESPACE = EVENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__FOLDER = EVENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__NAME = EVENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__DESCRIPTION = EVENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__LAST_MODIFIED = EVENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__GUID = EVENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__ONTOLOGY = EVENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__EXTENDED_PROPERTIES = EVENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__HIDDEN_PROPERTIES = EVENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__TRANSIENT_PROPERTIES = EVENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__OWNER_PROJECT_NAME = EVENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__TYPE = EVENT__TYPE;

	/**
	 * The feature id for the '<em><b>Super Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__SUPER_EVENT = EVENT__SUPER_EVENT;

	/**
	 * The feature id for the '<em><b>Super Event Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__SUPER_EVENT_PATH = EVENT__SUPER_EVENT_PATH;

	/**
	 * The feature id for the '<em><b>Sub Events</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__SUB_EVENTS = EVENT__SUB_EVENTS;

	/**
	 * The feature id for the '<em><b>Ancestor Properties</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__ANCESTOR_PROPERTIES = EVENT__ANCESTOR_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Payload String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__PAYLOAD_STRING = EVENT__PAYLOAD_STRING;

	/**
	 * The feature id for the '<em><b>Ttl</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__TTL = EVENT__TTL;

	/**
	 * The feature id for the '<em><b>Ttl Units</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__TTL_UNITS = EVENT__TTL_UNITS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__PROPERTIES = EVENT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Rule Set</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__RULE_SET = EVENT__RULE_SET;

	/**
	 * The feature id for the '<em><b>Specified Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__SPECIFIED_TIME = EVENT__SPECIFIED_TIME;

	/**
	 * The feature id for the '<em><b>Serialization Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__SERIALIZATION_FORMAT = EVENT__SERIALIZATION_FORMAT;

	/**
	 * The feature id for the '<em><b>Namespace Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__NAMESPACE_ENTRIES = EVENT__NAMESPACE_ENTRIES;

	/**
	 * The feature id for the '<em><b>Registry Import Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__REGISTRY_IMPORT_ENTRIES = EVENT__REGISTRY_IMPORT_ENTRIES;

	/**
	 * The feature id for the '<em><b>Sub Events Path</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__SUB_EVENTS_PATH = EVENT__SUB_EVENTS_PATH;

	/**
	 * The feature id for the '<em><b>Expiry Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__EXPIRY_ACTION = EVENT__EXPIRY_ACTION;

	/**
	 * The feature id for the '<em><b>Payload</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__PAYLOAD = EVENT__PAYLOAD;

	/**
	 * The feature id for the '<em><b>Retry On Exception</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT__RETRY_ON_EXCEPTION = EVENT__RETRY_ON_EXCEPTION;

	/**
	 * The number of structural features of the '<em>Advisory Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVISORY_EVENT_FEATURE_COUNT = EVENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.event.EVENT_TYPE <em>EVENT TYPE</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.event.EVENT_TYPE
	 * @see com.tibco.cep.designtime.core.model.event.impl.EventPackageImpl#getEVENT_TYPE()
	 * @generated
	 */
	int EVENT_TYPE = 7;


	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.event.EVENT_SCHEDULE_TYPE <em>EVENT SCHEDULE TYPE</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.event.EVENT_SCHEDULE_TYPE
	 * @see com.tibco.cep.designtime.core.model.event.impl.EventPackageImpl#getEVENT_SCHEDULE_TYPE()
	 * @generated
	 */
	int EVENT_SCHEDULE_TYPE = 8;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.event.Event <em>Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.Event
	 * @generated
	 */
	EClass getEvent();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.event.Event#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.Event#getType()
	 * @see #getEvent()
	 * @generated
	 */
	EAttribute getEvent_Type();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.event.Event#getSuperEvent <em>Super Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Super Event</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.Event#getSuperEvent()
	 * @see #getEvent()
	 * @generated
	 */
	EReference getEvent_SuperEvent();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.event.Event#getSuperEventPath <em>Super Event Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Super Event Path</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.Event#getSuperEventPath()
	 * @see #getEvent()
	 * @generated
	 */
	EAttribute getEvent_SuperEventPath();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.designtime.core.model.event.Event#getSubEvents <em>Sub Events</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Sub Events</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.Event#getSubEvents()
	 * @see #getEvent()
	 * @generated
	 */
	EReference getEvent_SubEvents();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.designtime.core.model.event.Event#getAncestorProperties <em>Ancestor Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ancestor Properties</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.Event#getAncestorProperties()
	 * @see #getEvent()
	 * @generated
	 */
	EReference getEvent_AncestorProperties();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.event.Event#getPayload <em>Payload</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Payload</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.Event#getPayload()
	 * @see #getEvent()
	 * @generated
	 */
	EReference getEvent_Payload();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.event.Event#isRetryOnException <em>Retry On Exception</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Retry On Exception</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.Event#isRetryOnException()
	 * @see #getEvent()
	 * @generated
	 */
	EAttribute getEvent_RetryOnException();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.event.NamespaceEntry <em>Namespace Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Namespace Entry</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.NamespaceEntry
	 * @generated
	 */
	EClass getNamespaceEntry();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.event.NamespaceEntry#getPrefix <em>Prefix</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Prefix</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.NamespaceEntry#getPrefix()
	 * @see #getNamespaceEntry()
	 * @generated
	 */
	EAttribute getNamespaceEntry_Prefix();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.event.NamespaceEntry#getNamespace <em>Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Namespace</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.NamespaceEntry#getNamespace()
	 * @see #getNamespaceEntry()
	 * @generated
	 */
	EAttribute getNamespaceEntry_Namespace();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.event.ImportRegistryEntry <em>Import Registry Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Import Registry Entry</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.ImportRegistryEntry
	 * @generated
	 */
	EClass getImportRegistryEntry();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.event.ImportRegistryEntry#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Location</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.ImportRegistryEntry#getLocation()
	 * @see #getImportRegistryEntry()
	 * @generated
	 */
	EAttribute getImportRegistryEntry_Location();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.event.ImportRegistryEntry#getNamespace <em>Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Namespace</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.ImportRegistryEntry#getNamespace()
	 * @see #getImportRegistryEntry()
	 * @generated
	 */
	EAttribute getImportRegistryEntry_Namespace();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.event.Event#getPayloadString <em>Payload String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Payload String</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.Event#getPayloadString()
	 * @see #getEvent()
	 * @generated
	 */
	EAttribute getEvent_PayloadString();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.event.Event#getTtl <em>Ttl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ttl</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.Event#getTtl()
	 * @see #getEvent()
	 * @generated
	 */
	EAttribute getEvent_Ttl();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.event.Event#getTtlUnits <em>Ttl Units</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ttl Units</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.Event#getTtlUnits()
	 * @see #getEvent()
	 * @generated
	 */
	EAttribute getEvent_TtlUnits();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.event.Event#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.Event#getProperties()
	 * @see #getEvent()
	 * @generated
	 */
	EReference getEvent_Properties();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.event.Event#getRuleSet <em>Rule Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Rule Set</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.Event#getRuleSet()
	 * @see #getEvent()
	 * @generated
	 */
	EReference getEvent_RuleSet();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.event.Event#getSpecifiedTime <em>Specified Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specified Time</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.Event#getSpecifiedTime()
	 * @see #getEvent()
	 * @generated
	 */
	EAttribute getEvent_SpecifiedTime();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.event.Event#getSerializationFormat <em>Serialization Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Serialization Format</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.Event#getSerializationFormat()
	 * @see #getEvent()
	 * @generated
	 */
	EAttribute getEvent_SerializationFormat();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.event.Event#getNamespaceEntries <em>Namespace Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Namespace Entries</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.Event#getNamespaceEntries()
	 * @see #getEvent()
	 * @generated
	 */
	EReference getEvent_NamespaceEntries();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.event.Event#getRegistryImportEntries <em>Registry Import Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Registry Import Entries</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.Event#getRegistryImportEntries()
	 * @see #getEvent()
	 * @generated
	 */
	EReference getEvent_RegistryImportEntries();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.designtime.core.model.event.Event#getSubEventsPath <em>Sub Events Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Sub Events Path</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.Event#getSubEventsPath()
	 * @see #getEvent()
	 * @generated
	 */
	EAttribute getEvent_SubEventsPath();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.event.Event#getExpiryAction <em>Expiry Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expiry Action</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.Event#getExpiryAction()
	 * @see #getEvent()
	 * @generated
	 */
	EReference getEvent_ExpiryAction();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.event.SimpleEvent <em>Simple Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simple Event</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.SimpleEvent
	 * @generated
	 */
	EClass getSimpleEvent();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.event.SimpleEvent#getChannelURI <em>Channel URI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Channel URI</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.SimpleEvent#getChannelURI()
	 * @see #getSimpleEvent()
	 * @generated
	 */
	EAttribute getSimpleEvent_ChannelURI();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.event.SimpleEvent#getDestinationName <em>Destination Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Destination Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.SimpleEvent#getDestinationName()
	 * @see #getSimpleEvent()
	 * @generated
	 */
	EAttribute getSimpleEvent_DestinationName();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.event.TimeEvent <em>Time Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Time Event</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.TimeEvent
	 * @generated
	 */
	EClass getTimeEvent();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.event.TimeEvent#getScheduleType <em>Schedule Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schedule Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.TimeEvent#getScheduleType()
	 * @see #getTimeEvent()
	 * @generated
	 */
	EAttribute getTimeEvent_ScheduleType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.event.TimeEvent#getTimeEventCount <em>Time Event Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time Event Count</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.TimeEvent#getTimeEventCount()
	 * @see #getTimeEvent()
	 * @generated
	 */
	EAttribute getTimeEvent_TimeEventCount();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.event.TimeEvent#getInterval <em>Interval</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Interval</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.TimeEvent#getInterval()
	 * @see #getTimeEvent()
	 * @generated
	 */
	EAttribute getTimeEvent_Interval();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.event.TimeEvent#getIntervalUnit <em>Interval Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Interval Unit</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.TimeEvent#getIntervalUnit()
	 * @see #getTimeEvent()
	 * @generated
	 */
	EAttribute getTimeEvent_IntervalUnit();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.event.UserProperty <em>User Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>User Property</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.UserProperty
	 * @generated
	 */
	EClass getUserProperty();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.event.UserProperty#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.UserProperty#getName()
	 * @see #getUserProperty()
	 * @generated
	 */
	EAttribute getUserProperty_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.event.UserProperty#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.UserProperty#getType()
	 * @see #getUserProperty()
	 * @generated
	 */
	EAttribute getUserProperty_Type();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.event.UserProperty#getExtendedProperties <em>Extended Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Extended Properties</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.UserProperty#getExtendedProperties()
	 * @see #getUserProperty()
	 * @generated
	 */
	EReference getUserProperty_ExtendedProperties();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.event.AdvisoryEvent <em>Advisory Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Advisory Event</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.AdvisoryEvent
	 * @generated
	 */
	EClass getAdvisoryEvent();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.event.EVENT_TYPE <em>EVENT TYPE</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>EVENT TYPE</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.EVENT_TYPE
	 * @generated
	 */
	EEnum getEVENT_TYPE();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.event.EVENT_SCHEDULE_TYPE <em>EVENT SCHEDULE TYPE</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>EVENT SCHEDULE TYPE</em>'.
	 * @see com.tibco.cep.designtime.core.model.event.EVENT_SCHEDULE_TYPE
	 * @generated
	 */
	EEnum getEVENT_SCHEDULE_TYPE();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EventFactory getEventFactory();

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
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.event.impl.EventImpl <em>Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.event.impl.EventImpl
		 * @see com.tibco.cep.designtime.core.model.event.impl.EventPackageImpl#getEvent()
		 * @generated
		 */
		EClass EVENT = eINSTANCE.getEvent();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT__TYPE = eINSTANCE.getEvent_Type();

		/**
		 * The meta object literal for the '<em><b>Super Event</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT__SUPER_EVENT = eINSTANCE.getEvent_SuperEvent();

		/**
		 * The meta object literal for the '<em><b>Super Event Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT__SUPER_EVENT_PATH = eINSTANCE.getEvent_SuperEventPath();

		/**
		 * The meta object literal for the '<em><b>Sub Events</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT__SUB_EVENTS = eINSTANCE.getEvent_SubEvents();

		/**
		 * The meta object literal for the '<em><b>Ancestor Properties</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT__ANCESTOR_PROPERTIES = eINSTANCE.getEvent_AncestorProperties();

		/**
		 * The meta object literal for the '<em><b>Payload</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT__PAYLOAD = eINSTANCE.getEvent_Payload();

		/**
		 * The meta object literal for the '<em><b>Retry On Exception</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT__RETRY_ON_EXCEPTION = eINSTANCE.getEvent_RetryOnException();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.event.impl.NamespaceEntryImpl <em>Namespace Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.event.impl.NamespaceEntryImpl
		 * @see com.tibco.cep.designtime.core.model.event.impl.EventPackageImpl#getNamespaceEntry()
		 * @generated
		 */
		EClass NAMESPACE_ENTRY = eINSTANCE.getNamespaceEntry();

		/**
		 * The meta object literal for the '<em><b>Prefix</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMESPACE_ENTRY__PREFIX = eINSTANCE.getNamespaceEntry_Prefix();

		/**
		 * The meta object literal for the '<em><b>Namespace</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMESPACE_ENTRY__NAMESPACE = eINSTANCE.getNamespaceEntry_Namespace();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.event.impl.ImportRegistryEntryImpl <em>Import Registry Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.event.impl.ImportRegistryEntryImpl
		 * @see com.tibco.cep.designtime.core.model.event.impl.EventPackageImpl#getImportRegistryEntry()
		 * @generated
		 */
		EClass IMPORT_REGISTRY_ENTRY = eINSTANCE.getImportRegistryEntry();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORT_REGISTRY_ENTRY__LOCATION = eINSTANCE.getImportRegistryEntry_Location();

		/**
		 * The meta object literal for the '<em><b>Namespace</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORT_REGISTRY_ENTRY__NAMESPACE = eINSTANCE.getImportRegistryEntry_Namespace();

		/**
		 * The meta object literal for the '<em><b>Payload String</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT__PAYLOAD_STRING = eINSTANCE.getEvent_PayloadString();

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
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT__PROPERTIES = eINSTANCE.getEvent_Properties();

		/**
		 * The meta object literal for the '<em><b>Rule Set</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT__RULE_SET = eINSTANCE.getEvent_RuleSet();

		/**
		 * The meta object literal for the '<em><b>Specified Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT__SPECIFIED_TIME = eINSTANCE.getEvent_SpecifiedTime();

		/**
		 * The meta object literal for the '<em><b>Serialization Format</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT__SERIALIZATION_FORMAT = eINSTANCE.getEvent_SerializationFormat();

		/**
		 * The meta object literal for the '<em><b>Namespace Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT__NAMESPACE_ENTRIES = eINSTANCE.getEvent_NamespaceEntries();

		/**
		 * The meta object literal for the '<em><b>Registry Import Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT__REGISTRY_IMPORT_ENTRIES = eINSTANCE.getEvent_RegistryImportEntries();

		/**
		 * The meta object literal for the '<em><b>Sub Events Path</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT__SUB_EVENTS_PATH = eINSTANCE.getEvent_SubEventsPath();

		/**
		 * The meta object literal for the '<em><b>Expiry Action</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT__EXPIRY_ACTION = eINSTANCE.getEvent_ExpiryAction();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.event.impl.SimpleEventImpl <em>Simple Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.event.impl.SimpleEventImpl
		 * @see com.tibco.cep.designtime.core.model.event.impl.EventPackageImpl#getSimpleEvent()
		 * @generated
		 */
		EClass SIMPLE_EVENT = eINSTANCE.getSimpleEvent();

		/**
		 * The meta object literal for the '<em><b>Channel URI</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_EVENT__CHANNEL_URI = eINSTANCE.getSimpleEvent_ChannelURI();

		/**
		 * The meta object literal for the '<em><b>Destination Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_EVENT__DESTINATION_NAME = eINSTANCE.getSimpleEvent_DestinationName();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.event.impl.TimeEventImpl <em>Time Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.event.impl.TimeEventImpl
		 * @see com.tibco.cep.designtime.core.model.event.impl.EventPackageImpl#getTimeEvent()
		 * @generated
		 */
		EClass TIME_EVENT = eINSTANCE.getTimeEvent();

		/**
		 * The meta object literal for the '<em><b>Schedule Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TIME_EVENT__SCHEDULE_TYPE = eINSTANCE.getTimeEvent_ScheduleType();

		/**
		 * The meta object literal for the '<em><b>Time Event Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TIME_EVENT__TIME_EVENT_COUNT = eINSTANCE.getTimeEvent_TimeEventCount();

		/**
		 * The meta object literal for the '<em><b>Interval</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TIME_EVENT__INTERVAL = eINSTANCE.getTimeEvent_Interval();

		/**
		 * The meta object literal for the '<em><b>Interval Unit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TIME_EVENT__INTERVAL_UNIT = eINSTANCE.getTimeEvent_IntervalUnit();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.event.impl.UserPropertyImpl <em>User Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.event.impl.UserPropertyImpl
		 * @see com.tibco.cep.designtime.core.model.event.impl.EventPackageImpl#getUserProperty()
		 * @generated
		 */
		EClass USER_PROPERTY = eINSTANCE.getUserProperty();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER_PROPERTY__NAME = eINSTANCE.getUserProperty_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER_PROPERTY__TYPE = eINSTANCE.getUserProperty_Type();

		/**
		 * The meta object literal for the '<em><b>Extended Properties</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USER_PROPERTY__EXTENDED_PROPERTIES = eINSTANCE.getUserProperty_ExtendedProperties();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.event.impl.AdvisoryEventImpl <em>Advisory Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.event.impl.AdvisoryEventImpl
		 * @see com.tibco.cep.designtime.core.model.event.impl.EventPackageImpl#getAdvisoryEvent()
		 * @generated
		 */
		EClass ADVISORY_EVENT = eINSTANCE.getAdvisoryEvent();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.event.EVENT_TYPE <em>EVENT TYPE</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.event.EVENT_TYPE
		 * @see com.tibco.cep.designtime.core.model.event.impl.EventPackageImpl#getEVENT_TYPE()
		 * @generated
		 */
		EEnum EVENT_TYPE = eINSTANCE.getEVENT_TYPE();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.event.EVENT_SCHEDULE_TYPE <em>EVENT SCHEDULE TYPE</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.event.EVENT_SCHEDULE_TYPE
		 * @see com.tibco.cep.designtime.core.model.event.impl.EventPackageImpl#getEVENT_SCHEDULE_TYPE()
		 * @generated
		 */
		EEnum EVENT_SCHEDULE_TYPE = eINSTANCE.getEVENT_SCHEDULE_TYPE();

	}

} //EventPackage
