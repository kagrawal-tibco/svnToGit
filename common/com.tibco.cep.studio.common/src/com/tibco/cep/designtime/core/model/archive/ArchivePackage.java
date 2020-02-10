/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.archive;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see com.tibco.cep.designtime.core.model.archive.ArchiveFactory
 * @model kind="package"
 * @generated
 */
public interface ArchivePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "archive";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/designtime/core/model/archive";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "archive";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ArchivePackage eINSTANCE = com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.archive.impl.ArchiveResourceImpl <em>Resource</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchiveResourceImpl
	 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getArchiveResource()
	 * @generated
	 */
	int ARCHIVE_RESOURCE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHIVE_RESOURCE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHIVE_RESOURCE__DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHIVE_RESOURCE__AUTHOR = 2;

	/**
	 * The number of structural features of the '<em>Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHIVE_RESOURCE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.archive.impl.EnterpriseArchiveImpl <em>Enterprise Archive</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.archive.impl.EnterpriseArchiveImpl
	 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getEnterpriseArchive()
	 * @generated
	 */
	int ENTERPRISE_ARCHIVE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTERPRISE_ARCHIVE__NAME = ARCHIVE_RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTERPRISE_ARCHIVE__DESCRIPTION = ARCHIVE_RESOURCE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTERPRISE_ARCHIVE__AUTHOR = ARCHIVE_RESOURCE__AUTHOR;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTERPRISE_ARCHIVE__VERSION = ARCHIVE_RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTERPRISE_ARCHIVE__FOLDER = ARCHIVE_RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTERPRISE_ARCHIVE__OWNER_PROJECT_NAME = ARCHIVE_RESOURCE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>File Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTERPRISE_ARCHIVE__FILE_LOCATION = ARCHIVE_RESOURCE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Include Global Vars</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTERPRISE_ARCHIVE__INCLUDE_GLOBAL_VARS = ARCHIVE_RESOURCE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Business Events Archives</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTERPRISE_ARCHIVE__BUSINESS_EVENTS_ARCHIVES = ARCHIVE_RESOURCE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Shared Archives</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTERPRISE_ARCHIVE__SHARED_ARCHIVES = ARCHIVE_RESOURCE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Process Archives</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTERPRISE_ARCHIVE__PROCESS_ARCHIVES = ARCHIVE_RESOURCE_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Enterprise Archive</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTERPRISE_ARCHIVE_FEATURE_COUNT = ARCHIVE_RESOURCE_FEATURE_COUNT + 8;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.archive.impl.BEArchiveResourceImpl <em>BE Archive Resource</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.archive.impl.BEArchiveResourceImpl
	 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getBEArchiveResource()
	 * @generated
	 */
	int BE_ARCHIVE_RESOURCE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE_ARCHIVE_RESOURCE__NAME = ARCHIVE_RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE_ARCHIVE_RESOURCE__DESCRIPTION = ARCHIVE_RESOURCE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE_ARCHIVE_RESOURCE__AUTHOR = ARCHIVE_RESOURCE__AUTHOR;

	/**
	 * The feature id for the '<em><b>Compile With Debug</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE_ARCHIVE_RESOURCE__COMPILE_WITH_DEBUG = ARCHIVE_RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Delete Temp Files</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE_ARCHIVE_RESOURCE__DELETE_TEMP_FILES = ARCHIVE_RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Compile Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE_ARCHIVE_RESOURCE__COMPILE_PATH = ARCHIVE_RESOURCE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Extra Class Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE_ARCHIVE_RESOURCE__EXTRA_CLASS_PATH = ARCHIVE_RESOURCE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>BE Archive Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE_ARCHIVE_RESOURCE_FEATURE_COUNT = ARCHIVE_RESOURCE_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.archive.impl.BusinessEventsArchiveResourceImpl <em>Business Events Archive Resource</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.archive.impl.BusinessEventsArchiveResourceImpl
	 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getBusinessEventsArchiveResource()
	 * @generated
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE__NAME = BE_ARCHIVE_RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE__DESCRIPTION = BE_ARCHIVE_RESOURCE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE__AUTHOR = BE_ARCHIVE_RESOURCE__AUTHOR;

	/**
	 * The feature id for the '<em><b>Compile With Debug</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE__COMPILE_WITH_DEBUG = BE_ARCHIVE_RESOURCE__COMPILE_WITH_DEBUG;

	/**
	 * The feature id for the '<em><b>Delete Temp Files</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE__DELETE_TEMP_FILES = BE_ARCHIVE_RESOURCE__DELETE_TEMP_FILES;

	/**
	 * The feature id for the '<em><b>Compile Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE__COMPILE_PATH = BE_ARCHIVE_RESOURCE__COMPILE_PATH;

	/**
	 * The feature id for the '<em><b>Extra Class Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE__EXTRA_CLASS_PATH = BE_ARCHIVE_RESOURCE__EXTRA_CLASS_PATH;

	/**
	 * The feature id for the '<em><b>Archive Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE__ARCHIVE_TYPE = BE_ARCHIVE_RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>All Rule Sets</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE__ALL_RULE_SETS = BE_ARCHIVE_RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>All Destinations</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE__ALL_DESTINATIONS = BE_ARCHIVE_RESOURCE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Included Rule Sets</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE__INCLUDED_RULE_SETS = BE_ARCHIVE_RESOURCE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Startup Actions</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE__STARTUP_ACTIONS = BE_ARCHIVE_RESOURCE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Shutdown Actions</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE__SHUTDOWN_ACTIONS = BE_ARCHIVE_RESOURCE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Input Destinations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE__INPUT_DESTINATIONS = BE_ARCHIVE_RESOURCE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Om Check Pt Interval</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_CHECK_PT_INTERVAL = BE_ARCHIVE_RESOURCE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Om Cache Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_CACHE_SIZE = BE_ARCHIVE_RESOURCE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Om Check Pt Ops Limit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_CHECK_PT_OPS_LIMIT = BE_ARCHIVE_RESOURCE_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Om Delete Policy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_DELETE_POLICY = BE_ARCHIVE_RESOURCE_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Om No Recovery</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_NO_RECOVERY = BE_ARCHIVE_RESOURCE_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Om Db Environment Dir</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_DB_ENVIRONMENT_DIR = BE_ARCHIVE_RESOURCE_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Omtg Global Cache</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE__OMTG_GLOBAL_CACHE = BE_ARCHIVE_RESOURCE_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Omtg Cache Config File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE__OMTG_CACHE_CONFIG_FILE = BE_ARCHIVE_RESOURCE_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Advanced Settings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE__ADVANCED_SETTINGS = BE_ARCHIVE_RESOURCE_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Object Mgmt Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE__OBJECT_MGMT_TYPE = BE_ARCHIVE_RESOURCE_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Agent Group Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE__AGENT_GROUP_NAME = BE_ARCHIVE_RESOURCE_FEATURE_COUNT + 17;

	/**
	 * The feature id for the '<em><b>Cache Config Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE__CACHE_CONFIG_TYPE = BE_ARCHIVE_RESOURCE_FEATURE_COUNT + 18;

	/**
	 * The feature id for the '<em><b>Omtg Custom Recovery Factory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE__OMTG_CUSTOM_RECOVERY_FACTORY = BE_ARCHIVE_RESOURCE_FEATURE_COUNT + 19;

	/**
	 * The number of structural features of the '<em>Business Events Archive Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_EVENTS_ARCHIVE_RESOURCE_FEATURE_COUNT = BE_ARCHIVE_RESOURCE_FEATURE_COUNT + 20;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.archive.impl.InputDestinationImpl <em>Input Destination</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.archive.impl.InputDestinationImpl
	 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getInputDestination()
	 * @generated
	 */
	int INPUT_DESTINATION = 4;

	/**
	 * The feature id for the '<em><b>Destination URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_DESTINATION__DESTINATION_URI = 0;

	/**
	 * The feature id for the '<em><b>Preprocessor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_DESTINATION__PREPROCESSOR = 1;

	/**
	 * The feature id for the '<em><b>Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_DESTINATION__DEFAULT = 2;

	/**
	 * The feature id for the '<em><b>Enable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_DESTINATION__ENABLE = 3;

	/**
	 * The feature id for the '<em><b>Workers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_DESTINATION__WORKERS = 4;

	/**
	 * The feature id for the '<em><b>Queue Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_DESTINATION__QUEUE_SIZE = 5;

	/**
	 * The feature id for the '<em><b>Thread Affinity Rule Function</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_DESTINATION__THREAD_AFFINITY_RULE_FUNCTION = 6;

	/**
	 * The number of structural features of the '<em>Input Destination</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_DESTINATION_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.archive.impl.AdvancedEntitySettingImpl <em>Advanced Entity Setting</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.archive.impl.AdvancedEntitySettingImpl
	 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getAdvancedEntitySetting()
	 * @generated
	 */
	int ADVANCED_ENTITY_SETTING = 5;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVANCED_ENTITY_SETTING__ENTITY = 0;

	/**
	 * The feature id for the '<em><b>Deployed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVANCED_ENTITY_SETTING__DEPLOYED = 1;

	/**
	 * The feature id for the '<em><b>Recovery Function</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVANCED_ENTITY_SETTING__RECOVERY_FUNCTION = 2;

	/**
	 * The feature id for the '<em><b>Cache Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVANCED_ENTITY_SETTING__CACHE_MODE = 3;

	/**
	 * The number of structural features of the '<em>Advanced Entity Setting</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVANCED_ENTITY_SETTING_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.archive.impl.SharedArchiveImpl <em>Shared Archive</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.archive.impl.SharedArchiveImpl
	 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getSharedArchive()
	 * @generated
	 */
	int SHARED_ARCHIVE = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ARCHIVE__NAME = ARCHIVE_RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ARCHIVE__DESCRIPTION = ARCHIVE_RESOURCE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ARCHIVE__AUTHOR = ARCHIVE_RESOURCE__AUTHOR;

	/**
	 * The feature id for the '<em><b>Shared Resources</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ARCHIVE__SHARED_RESOURCES = ARCHIVE_RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Shared Files</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ARCHIVE__SHARED_FILES = ARCHIVE_RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Shared Jar Files</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ARCHIVE__SHARED_JAR_FILES = ARCHIVE_RESOURCE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Shared Archive</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ARCHIVE_FEATURE_COUNT = ARCHIVE_RESOURCE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.archive.impl.ProcessArchiveImpl <em>Process Archive</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.archive.impl.ProcessArchiveImpl
	 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getProcessArchive()
	 * @generated
	 */
	int PROCESS_ARCHIVE = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ARCHIVE__NAME = ARCHIVE_RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ARCHIVE__DESCRIPTION = ARCHIVE_RESOURCE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ARCHIVE__AUTHOR = ARCHIVE_RESOURCE__AUTHOR;

	/**
	 * The number of structural features of the '<em>Process Archive</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ARCHIVE_FEATURE_COUNT = ARCHIVE_RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.archive.impl.AdapterArchiveImpl <em>Adapter Archive</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.archive.impl.AdapterArchiveImpl
	 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getAdapterArchive()
	 * @generated
	 */
	int ADAPTER_ARCHIVE = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_ARCHIVE__NAME = ARCHIVE_RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_ARCHIVE__DESCRIPTION = ARCHIVE_RESOURCE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_ARCHIVE__AUTHOR = ARCHIVE_RESOURCE__AUTHOR;

	/**
	 * The number of structural features of the '<em>Adapter Archive</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_ARCHIVE_FEATURE_COUNT = ARCHIVE_RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.archive.BE_ARCHIVE_TYPE <em>BE ARCHIVE TYPE</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.archive.BE_ARCHIVE_TYPE
	 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getBE_ARCHIVE_TYPE()
	 * @generated
	 */
	int BE_ARCHIVE_TYPE = 9;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.archive.CACHE_MODE <em>CACHE MODE</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.archive.CACHE_MODE
	 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getCACHE_MODE()
	 * @generated
	 */
	int CACHE_MODE = 10;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.archive.WORKERS <em>WORKERS</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.archive.WORKERS
	 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getWORKERS()
	 * @generated
	 */
	int WORKERS = 11;


	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.archive.OBJECT_MGMT_TYPE <em>OBJECT MGMT TYPE</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.archive.OBJECT_MGMT_TYPE
	 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getOBJECT_MGMT_TYPE()
	 * @generated
	 */
	int OBJECT_MGMT_TYPE = 12;


	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.archive.CACHE_CONFIG_TYPE <em>CACHE CONFIG TYPE</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.archive.CACHE_CONFIG_TYPE
	 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getCACHE_CONFIG_TYPE()
	 * @generated
	 */
	int CACHE_CONFIG_TYPE = 13;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.archive.ArchiveResource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.ArchiveResource
	 * @generated
	 */
	EClass getArchiveResource();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.ArchiveResource#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.ArchiveResource#getName()
	 * @see #getArchiveResource()
	 * @generated
	 */
	EAttribute getArchiveResource_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.ArchiveResource#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.ArchiveResource#getDescription()
	 * @see #getArchiveResource()
	 * @generated
	 */
	EAttribute getArchiveResource_Description();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.ArchiveResource#getAuthor <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Author</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.ArchiveResource#getAuthor()
	 * @see #getArchiveResource()
	 * @generated
	 */
	EAttribute getArchiveResource_Author();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.archive.EnterpriseArchive <em>Enterprise Archive</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enterprise Archive</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.EnterpriseArchive
	 * @generated
	 */
	EClass getEnterpriseArchive();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#getVersion()
	 * @see #getEnterpriseArchive()
	 * @generated
	 */
	EAttribute getEnterpriseArchive_Version();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#getFolder <em>Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Folder</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#getFolder()
	 * @see #getEnterpriseArchive()
	 * @generated
	 */
	EAttribute getEnterpriseArchive_Folder();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#getOwnerProjectName <em>Owner Project Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Owner Project Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#getOwnerProjectName()
	 * @see #getEnterpriseArchive()
	 * @generated
	 */
	EAttribute getEnterpriseArchive_OwnerProjectName();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#getFileLocation <em>File Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File Location</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#getFileLocation()
	 * @see #getEnterpriseArchive()
	 * @generated
	 */
	EAttribute getEnterpriseArchive_FileLocation();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#isIncludeGlobalVars <em>Include Global Vars</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Include Global Vars</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#isIncludeGlobalVars()
	 * @see #getEnterpriseArchive()
	 * @generated
	 */
	EAttribute getEnterpriseArchive_IncludeGlobalVars();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#getBusinessEventsArchives <em>Business Events Archives</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Business Events Archives</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#getBusinessEventsArchives()
	 * @see #getEnterpriseArchive()
	 * @generated
	 */
	EReference getEnterpriseArchive_BusinessEventsArchives();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#getSharedArchives <em>Shared Archives</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Shared Archives</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#getSharedArchives()
	 * @see #getEnterpriseArchive()
	 * @generated
	 */
	EReference getEnterpriseArchive_SharedArchives();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#getProcessArchives <em>Process Archives</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Process Archives</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#getProcessArchives()
	 * @see #getEnterpriseArchive()
	 * @generated
	 */
	EReference getEnterpriseArchive_ProcessArchives();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.archive.BEArchiveResource <em>BE Archive Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>BE Archive Resource</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.BEArchiveResource
	 * @generated
	 */
	EClass getBEArchiveResource();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.BEArchiveResource#isCompileWithDebug <em>Compile With Debug</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Compile With Debug</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.BEArchiveResource#isCompileWithDebug()
	 * @see #getBEArchiveResource()
	 * @generated
	 */
	EAttribute getBEArchiveResource_CompileWithDebug();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.BEArchiveResource#isDeleteTempFiles <em>Delete Temp Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Delete Temp Files</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.BEArchiveResource#isDeleteTempFiles()
	 * @see #getBEArchiveResource()
	 * @generated
	 */
	EAttribute getBEArchiveResource_DeleteTempFiles();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.BEArchiveResource#getCompilePath <em>Compile Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Compile Path</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.BEArchiveResource#getCompilePath()
	 * @see #getBEArchiveResource()
	 * @generated
	 */
	EAttribute getBEArchiveResource_CompilePath();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.BEArchiveResource#getExtraClassPath <em>Extra Class Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Extra Class Path</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.BEArchiveResource#getExtraClassPath()
	 * @see #getBEArchiveResource()
	 * @generated
	 */
	EAttribute getBEArchiveResource_ExtraClassPath();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource <em>Business Events Archive Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Business Events Archive Resource</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource
	 * @generated
	 */
	EClass getBusinessEventsArchiveResource();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getArchiveType <em>Archive Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Archive Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getArchiveType()
	 * @see #getBusinessEventsArchiveResource()
	 * @generated
	 */
	EAttribute getBusinessEventsArchiveResource_ArchiveType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#isAllRuleSets <em>All Rule Sets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>All Rule Sets</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#isAllRuleSets()
	 * @see #getBusinessEventsArchiveResource()
	 * @generated
	 */
	EAttribute getBusinessEventsArchiveResource_AllRuleSets();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#isAllDestinations <em>All Destinations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>All Destinations</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#isAllDestinations()
	 * @see #getBusinessEventsArchiveResource()
	 * @generated
	 */
	EAttribute getBusinessEventsArchiveResource_AllDestinations();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getIncludedRuleSets <em>Included Rule Sets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Included Rule Sets</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getIncludedRuleSets()
	 * @see #getBusinessEventsArchiveResource()
	 * @generated
	 */
	EAttribute getBusinessEventsArchiveResource_IncludedRuleSets();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getStartupActions <em>Startup Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Startup Actions</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getStartupActions()
	 * @see #getBusinessEventsArchiveResource()
	 * @generated
	 */
	EAttribute getBusinessEventsArchiveResource_StartupActions();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getShutdownActions <em>Shutdown Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Shutdown Actions</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getShutdownActions()
	 * @see #getBusinessEventsArchiveResource()
	 * @generated
	 */
	EAttribute getBusinessEventsArchiveResource_ShutdownActions();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getInputDestinations <em>Input Destinations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Input Destinations</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getInputDestinations()
	 * @see #getBusinessEventsArchiveResource()
	 * @generated
	 */
	EReference getBusinessEventsArchiveResource_InputDestinations();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmCheckPtInterval <em>Om Check Pt Interval</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Om Check Pt Interval</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmCheckPtInterval()
	 * @see #getBusinessEventsArchiveResource()
	 * @generated
	 */
	EAttribute getBusinessEventsArchiveResource_OmCheckPtInterval();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmCacheSize <em>Om Cache Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Om Cache Size</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmCacheSize()
	 * @see #getBusinessEventsArchiveResource()
	 * @generated
	 */
	EAttribute getBusinessEventsArchiveResource_OmCacheSize();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmCheckPtOpsLimit <em>Om Check Pt Ops Limit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Om Check Pt Ops Limit</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmCheckPtOpsLimit()
	 * @see #getBusinessEventsArchiveResource()
	 * @generated
	 */
	EAttribute getBusinessEventsArchiveResource_OmCheckPtOpsLimit();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#isOmDeletePolicy <em>Om Delete Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Om Delete Policy</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#isOmDeletePolicy()
	 * @see #getBusinessEventsArchiveResource()
	 * @generated
	 */
	EAttribute getBusinessEventsArchiveResource_OmDeletePolicy();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#isOmNoRecovery <em>Om No Recovery</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Om No Recovery</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#isOmNoRecovery()
	 * @see #getBusinessEventsArchiveResource()
	 * @generated
	 */
	EAttribute getBusinessEventsArchiveResource_OmNoRecovery();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmDbEnvironmentDir <em>Om Db Environment Dir</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Om Db Environment Dir</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmDbEnvironmentDir()
	 * @see #getBusinessEventsArchiveResource()
	 * @generated
	 */
	EAttribute getBusinessEventsArchiveResource_OmDbEnvironmentDir();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmtgGlobalCache <em>Omtg Global Cache</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Omtg Global Cache</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmtgGlobalCache()
	 * @see #getBusinessEventsArchiveResource()
	 * @generated
	 */
	EAttribute getBusinessEventsArchiveResource_OmtgGlobalCache();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmtgCacheConfigFile <em>Omtg Cache Config File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Omtg Cache Config File</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmtgCacheConfigFile()
	 * @see #getBusinessEventsArchiveResource()
	 * @generated
	 */
	EAttribute getBusinessEventsArchiveResource_OmtgCacheConfigFile();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getAdvancedSettings <em>Advanced Settings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Advanced Settings</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getAdvancedSettings()
	 * @see #getBusinessEventsArchiveResource()
	 * @generated
	 */
	EReference getBusinessEventsArchiveResource_AdvancedSettings();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getObjectMgmtType <em>Object Mgmt Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Object Mgmt Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getObjectMgmtType()
	 * @see #getBusinessEventsArchiveResource()
	 * @generated
	 */
	EAttribute getBusinessEventsArchiveResource_ObjectMgmtType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getAgentGroupName <em>Agent Group Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Agent Group Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getAgentGroupName()
	 * @see #getBusinessEventsArchiveResource()
	 * @generated
	 */
	EAttribute getBusinessEventsArchiveResource_AgentGroupName();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getCacheConfigType <em>Cache Config Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cache Config Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getCacheConfigType()
	 * @see #getBusinessEventsArchiveResource()
	 * @generated
	 */
	EAttribute getBusinessEventsArchiveResource_CacheConfigType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmtgCustomRecoveryFactory <em>Omtg Custom Recovery Factory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Omtg Custom Recovery Factory</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource#getOmtgCustomRecoveryFactory()
	 * @see #getBusinessEventsArchiveResource()
	 * @generated
	 */
	EAttribute getBusinessEventsArchiveResource_OmtgCustomRecoveryFactory();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.archive.InputDestination <em>Input Destination</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Input Destination</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.InputDestination
	 * @generated
	 */
	EClass getInputDestination();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.InputDestination#getDestinationURI <em>Destination URI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Destination URI</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.InputDestination#getDestinationURI()
	 * @see #getInputDestination()
	 * @generated
	 */
	EAttribute getInputDestination_DestinationURI();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.InputDestination#getPreprocessor <em>Preprocessor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Preprocessor</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.InputDestination#getPreprocessor()
	 * @see #getInputDestination()
	 * @generated
	 */
	EAttribute getInputDestination_Preprocessor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.InputDestination#isDefault <em>Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.InputDestination#isDefault()
	 * @see #getInputDestination()
	 * @generated
	 */
	EAttribute getInputDestination_Default();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.InputDestination#isEnable <em>Enable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enable</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.InputDestination#isEnable()
	 * @see #getInputDestination()
	 * @generated
	 */
	EAttribute getInputDestination_Enable();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.InputDestination#getWorkers <em>Workers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Workers</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.InputDestination#getWorkers()
	 * @see #getInputDestination()
	 * @generated
	 */
	EAttribute getInputDestination_Workers();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.InputDestination#getQueueSize <em>Queue Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Queue Size</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.InputDestination#getQueueSize()
	 * @see #getInputDestination()
	 * @generated
	 */
	EAttribute getInputDestination_QueueSize();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.InputDestination#getThreadAffinityRuleFunction <em>Thread Affinity Rule Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Thread Affinity Rule Function</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.InputDestination#getThreadAffinityRuleFunction()
	 * @see #getInputDestination()
	 * @generated
	 */
	EAttribute getInputDestination_ThreadAffinityRuleFunction();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting <em>Advanced Entity Setting</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Advanced Entity Setting</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting
	 * @generated
	 */
	EClass getAdvancedEntitySetting();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting#getEntity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Entity</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting#getEntity()
	 * @see #getAdvancedEntitySetting()
	 * @generated
	 */
	EAttribute getAdvancedEntitySetting_Entity();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting#isDeployed <em>Deployed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Deployed</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting#isDeployed()
	 * @see #getAdvancedEntitySetting()
	 * @generated
	 */
	EAttribute getAdvancedEntitySetting_Deployed();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting#getRecoveryFunction <em>Recovery Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Recovery Function</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting#getRecoveryFunction()
	 * @see #getAdvancedEntitySetting()
	 * @generated
	 */
	EAttribute getAdvancedEntitySetting_RecoveryFunction();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting#getCacheMode <em>Cache Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cache Mode</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting#getCacheMode()
	 * @see #getAdvancedEntitySetting()
	 * @generated
	 */
	EAttribute getAdvancedEntitySetting_CacheMode();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.archive.SharedArchive <em>Shared Archive</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Shared Archive</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.SharedArchive
	 * @generated
	 */
	EClass getSharedArchive();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.designtime.core.model.archive.SharedArchive#getSharedResources <em>Shared Resources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Shared Resources</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.SharedArchive#getSharedResources()
	 * @see #getSharedArchive()
	 * @generated
	 */
	EAttribute getSharedArchive_SharedResources();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.designtime.core.model.archive.SharedArchive#getSharedFiles <em>Shared Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Shared Files</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.SharedArchive#getSharedFiles()
	 * @see #getSharedArchive()
	 * @generated
	 */
	EAttribute getSharedArchive_SharedFiles();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.designtime.core.model.archive.SharedArchive#getSharedJarFiles <em>Shared Jar Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Shared Jar Files</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.SharedArchive#getSharedJarFiles()
	 * @see #getSharedArchive()
	 * @generated
	 */
	EAttribute getSharedArchive_SharedJarFiles();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.archive.ProcessArchive <em>Process Archive</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Process Archive</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.ProcessArchive
	 * @generated
	 */
	EClass getProcessArchive();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.archive.AdapterArchive <em>Adapter Archive</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Adapter Archive</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.AdapterArchive
	 * @generated
	 */
	EClass getAdapterArchive();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.archive.BE_ARCHIVE_TYPE <em>BE ARCHIVE TYPE</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>BE ARCHIVE TYPE</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.BE_ARCHIVE_TYPE
	 * @generated
	 */
	EEnum getBE_ARCHIVE_TYPE();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.archive.CACHE_MODE <em>CACHE MODE</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>CACHE MODE</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.CACHE_MODE
	 * @generated
	 */
	EEnum getCACHE_MODE();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.archive.WORKERS <em>WORKERS</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>WORKERS</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.WORKERS
	 * @generated
	 */
	EEnum getWORKERS();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.archive.OBJECT_MGMT_TYPE <em>OBJECT MGMT TYPE</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>OBJECT MGMT TYPE</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.OBJECT_MGMT_TYPE
	 * @generated
	 */
	EEnum getOBJECT_MGMT_TYPE();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.archive.CACHE_CONFIG_TYPE <em>CACHE CONFIG TYPE</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>CACHE CONFIG TYPE</em>'.
	 * @see com.tibco.cep.designtime.core.model.archive.CACHE_CONFIG_TYPE
	 * @generated
	 */
	EEnum getCACHE_CONFIG_TYPE();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ArchiveFactory getArchiveFactory();

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
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.archive.impl.ArchiveResourceImpl <em>Resource</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchiveResourceImpl
		 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getArchiveResource()
		 * @generated
		 */
		EClass ARCHIVE_RESOURCE = eINSTANCE.getArchiveResource();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARCHIVE_RESOURCE__NAME = eINSTANCE.getArchiveResource_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARCHIVE_RESOURCE__DESCRIPTION = eINSTANCE.getArchiveResource_Description();

		/**
		 * The meta object literal for the '<em><b>Author</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARCHIVE_RESOURCE__AUTHOR = eINSTANCE.getArchiveResource_Author();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.archive.impl.EnterpriseArchiveImpl <em>Enterprise Archive</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.archive.impl.EnterpriseArchiveImpl
		 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getEnterpriseArchive()
		 * @generated
		 */
		EClass ENTERPRISE_ARCHIVE = eINSTANCE.getEnterpriseArchive();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTERPRISE_ARCHIVE__VERSION = eINSTANCE.getEnterpriseArchive_Version();

		/**
		 * The meta object literal for the '<em><b>Folder</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTERPRISE_ARCHIVE__FOLDER = eINSTANCE.getEnterpriseArchive_Folder();

		/**
		 * The meta object literal for the '<em><b>Owner Project Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTERPRISE_ARCHIVE__OWNER_PROJECT_NAME = eINSTANCE.getEnterpriseArchive_OwnerProjectName();

		/**
		 * The meta object literal for the '<em><b>File Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTERPRISE_ARCHIVE__FILE_LOCATION = eINSTANCE.getEnterpriseArchive_FileLocation();

		/**
		 * The meta object literal for the '<em><b>Include Global Vars</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTERPRISE_ARCHIVE__INCLUDE_GLOBAL_VARS = eINSTANCE.getEnterpriseArchive_IncludeGlobalVars();

		/**
		 * The meta object literal for the '<em><b>Business Events Archives</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTERPRISE_ARCHIVE__BUSINESS_EVENTS_ARCHIVES = eINSTANCE.getEnterpriseArchive_BusinessEventsArchives();

		/**
		 * The meta object literal for the '<em><b>Shared Archives</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTERPRISE_ARCHIVE__SHARED_ARCHIVES = eINSTANCE.getEnterpriseArchive_SharedArchives();

		/**
		 * The meta object literal for the '<em><b>Process Archives</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTERPRISE_ARCHIVE__PROCESS_ARCHIVES = eINSTANCE.getEnterpriseArchive_ProcessArchives();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.archive.impl.BEArchiveResourceImpl <em>BE Archive Resource</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.archive.impl.BEArchiveResourceImpl
		 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getBEArchiveResource()
		 * @generated
		 */
		EClass BE_ARCHIVE_RESOURCE = eINSTANCE.getBEArchiveResource();

		/**
		 * The meta object literal for the '<em><b>Compile With Debug</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BE_ARCHIVE_RESOURCE__COMPILE_WITH_DEBUG = eINSTANCE.getBEArchiveResource_CompileWithDebug();

		/**
		 * The meta object literal for the '<em><b>Delete Temp Files</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BE_ARCHIVE_RESOURCE__DELETE_TEMP_FILES = eINSTANCE.getBEArchiveResource_DeleteTempFiles();

		/**
		 * The meta object literal for the '<em><b>Compile Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BE_ARCHIVE_RESOURCE__COMPILE_PATH = eINSTANCE.getBEArchiveResource_CompilePath();

		/**
		 * The meta object literal for the '<em><b>Extra Class Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BE_ARCHIVE_RESOURCE__EXTRA_CLASS_PATH = eINSTANCE.getBEArchiveResource_ExtraClassPath();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.archive.impl.BusinessEventsArchiveResourceImpl <em>Business Events Archive Resource</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.archive.impl.BusinessEventsArchiveResourceImpl
		 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getBusinessEventsArchiveResource()
		 * @generated
		 */
		EClass BUSINESS_EVENTS_ARCHIVE_RESOURCE = eINSTANCE.getBusinessEventsArchiveResource();

		/**
		 * The meta object literal for the '<em><b>Archive Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUSINESS_EVENTS_ARCHIVE_RESOURCE__ARCHIVE_TYPE = eINSTANCE.getBusinessEventsArchiveResource_ArchiveType();

		/**
		 * The meta object literal for the '<em><b>All Rule Sets</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUSINESS_EVENTS_ARCHIVE_RESOURCE__ALL_RULE_SETS = eINSTANCE.getBusinessEventsArchiveResource_AllRuleSets();

		/**
		 * The meta object literal for the '<em><b>All Destinations</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUSINESS_EVENTS_ARCHIVE_RESOURCE__ALL_DESTINATIONS = eINSTANCE.getBusinessEventsArchiveResource_AllDestinations();

		/**
		 * The meta object literal for the '<em><b>Included Rule Sets</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUSINESS_EVENTS_ARCHIVE_RESOURCE__INCLUDED_RULE_SETS = eINSTANCE.getBusinessEventsArchiveResource_IncludedRuleSets();

		/**
		 * The meta object literal for the '<em><b>Startup Actions</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUSINESS_EVENTS_ARCHIVE_RESOURCE__STARTUP_ACTIONS = eINSTANCE.getBusinessEventsArchiveResource_StartupActions();

		/**
		 * The meta object literal for the '<em><b>Shutdown Actions</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUSINESS_EVENTS_ARCHIVE_RESOURCE__SHUTDOWN_ACTIONS = eINSTANCE.getBusinessEventsArchiveResource_ShutdownActions();

		/**
		 * The meta object literal for the '<em><b>Input Destinations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BUSINESS_EVENTS_ARCHIVE_RESOURCE__INPUT_DESTINATIONS = eINSTANCE.getBusinessEventsArchiveResource_InputDestinations();

		/**
		 * The meta object literal for the '<em><b>Om Check Pt Interval</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_CHECK_PT_INTERVAL = eINSTANCE.getBusinessEventsArchiveResource_OmCheckPtInterval();

		/**
		 * The meta object literal for the '<em><b>Om Cache Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_CACHE_SIZE = eINSTANCE.getBusinessEventsArchiveResource_OmCacheSize();

		/**
		 * The meta object literal for the '<em><b>Om Check Pt Ops Limit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_CHECK_PT_OPS_LIMIT = eINSTANCE.getBusinessEventsArchiveResource_OmCheckPtOpsLimit();

		/**
		 * The meta object literal for the '<em><b>Om Delete Policy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_DELETE_POLICY = eINSTANCE.getBusinessEventsArchiveResource_OmDeletePolicy();

		/**
		 * The meta object literal for the '<em><b>Om No Recovery</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_NO_RECOVERY = eINSTANCE.getBusinessEventsArchiveResource_OmNoRecovery();

		/**
		 * The meta object literal for the '<em><b>Om Db Environment Dir</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_DB_ENVIRONMENT_DIR = eINSTANCE.getBusinessEventsArchiveResource_OmDbEnvironmentDir();

		/**
		 * The meta object literal for the '<em><b>Omtg Global Cache</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUSINESS_EVENTS_ARCHIVE_RESOURCE__OMTG_GLOBAL_CACHE = eINSTANCE.getBusinessEventsArchiveResource_OmtgGlobalCache();

		/**
		 * The meta object literal for the '<em><b>Omtg Cache Config File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUSINESS_EVENTS_ARCHIVE_RESOURCE__OMTG_CACHE_CONFIG_FILE = eINSTANCE.getBusinessEventsArchiveResource_OmtgCacheConfigFile();

		/**
		 * The meta object literal for the '<em><b>Advanced Settings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BUSINESS_EVENTS_ARCHIVE_RESOURCE__ADVANCED_SETTINGS = eINSTANCE.getBusinessEventsArchiveResource_AdvancedSettings();

		/**
		 * The meta object literal for the '<em><b>Object Mgmt Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUSINESS_EVENTS_ARCHIVE_RESOURCE__OBJECT_MGMT_TYPE = eINSTANCE.getBusinessEventsArchiveResource_ObjectMgmtType();

		/**
		 * The meta object literal for the '<em><b>Agent Group Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUSINESS_EVENTS_ARCHIVE_RESOURCE__AGENT_GROUP_NAME = eINSTANCE.getBusinessEventsArchiveResource_AgentGroupName();

		/**
		 * The meta object literal for the '<em><b>Cache Config Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUSINESS_EVENTS_ARCHIVE_RESOURCE__CACHE_CONFIG_TYPE = eINSTANCE.getBusinessEventsArchiveResource_CacheConfigType();

		/**
		 * The meta object literal for the '<em><b>Omtg Custom Recovery Factory</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUSINESS_EVENTS_ARCHIVE_RESOURCE__OMTG_CUSTOM_RECOVERY_FACTORY = eINSTANCE.getBusinessEventsArchiveResource_OmtgCustomRecoveryFactory();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.archive.impl.InputDestinationImpl <em>Input Destination</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.archive.impl.InputDestinationImpl
		 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getInputDestination()
		 * @generated
		 */
		EClass INPUT_DESTINATION = eINSTANCE.getInputDestination();

		/**
		 * The meta object literal for the '<em><b>Destination URI</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INPUT_DESTINATION__DESTINATION_URI = eINSTANCE.getInputDestination_DestinationURI();

		/**
		 * The meta object literal for the '<em><b>Preprocessor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INPUT_DESTINATION__PREPROCESSOR = eINSTANCE.getInputDestination_Preprocessor();

		/**
		 * The meta object literal for the '<em><b>Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INPUT_DESTINATION__DEFAULT = eINSTANCE.getInputDestination_Default();

		/**
		 * The meta object literal for the '<em><b>Enable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INPUT_DESTINATION__ENABLE = eINSTANCE.getInputDestination_Enable();

		/**
		 * The meta object literal for the '<em><b>Workers</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INPUT_DESTINATION__WORKERS = eINSTANCE.getInputDestination_Workers();

		/**
		 * The meta object literal for the '<em><b>Queue Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INPUT_DESTINATION__QUEUE_SIZE = eINSTANCE.getInputDestination_QueueSize();

		/**
		 * The meta object literal for the '<em><b>Thread Affinity Rule Function</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INPUT_DESTINATION__THREAD_AFFINITY_RULE_FUNCTION = eINSTANCE.getInputDestination_ThreadAffinityRuleFunction();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.archive.impl.AdvancedEntitySettingImpl <em>Advanced Entity Setting</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.archive.impl.AdvancedEntitySettingImpl
		 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getAdvancedEntitySetting()
		 * @generated
		 */
		EClass ADVANCED_ENTITY_SETTING = eINSTANCE.getAdvancedEntitySetting();

		/**
		 * The meta object literal for the '<em><b>Entity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADVANCED_ENTITY_SETTING__ENTITY = eINSTANCE.getAdvancedEntitySetting_Entity();

		/**
		 * The meta object literal for the '<em><b>Deployed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADVANCED_ENTITY_SETTING__DEPLOYED = eINSTANCE.getAdvancedEntitySetting_Deployed();

		/**
		 * The meta object literal for the '<em><b>Recovery Function</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADVANCED_ENTITY_SETTING__RECOVERY_FUNCTION = eINSTANCE.getAdvancedEntitySetting_RecoveryFunction();

		/**
		 * The meta object literal for the '<em><b>Cache Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADVANCED_ENTITY_SETTING__CACHE_MODE = eINSTANCE.getAdvancedEntitySetting_CacheMode();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.archive.impl.SharedArchiveImpl <em>Shared Archive</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.archive.impl.SharedArchiveImpl
		 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getSharedArchive()
		 * @generated
		 */
		EClass SHARED_ARCHIVE = eINSTANCE.getSharedArchive();

		/**
		 * The meta object literal for the '<em><b>Shared Resources</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHARED_ARCHIVE__SHARED_RESOURCES = eINSTANCE.getSharedArchive_SharedResources();

		/**
		 * The meta object literal for the '<em><b>Shared Files</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHARED_ARCHIVE__SHARED_FILES = eINSTANCE.getSharedArchive_SharedFiles();

		/**
		 * The meta object literal for the '<em><b>Shared Jar Files</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHARED_ARCHIVE__SHARED_JAR_FILES = eINSTANCE.getSharedArchive_SharedJarFiles();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.archive.impl.ProcessArchiveImpl <em>Process Archive</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.archive.impl.ProcessArchiveImpl
		 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getProcessArchive()
		 * @generated
		 */
		EClass PROCESS_ARCHIVE = eINSTANCE.getProcessArchive();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.archive.impl.AdapterArchiveImpl <em>Adapter Archive</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.archive.impl.AdapterArchiveImpl
		 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getAdapterArchive()
		 * @generated
		 */
		EClass ADAPTER_ARCHIVE = eINSTANCE.getAdapterArchive();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.archive.BE_ARCHIVE_TYPE <em>BE ARCHIVE TYPE</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.archive.BE_ARCHIVE_TYPE
		 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getBE_ARCHIVE_TYPE()
		 * @generated
		 */
		EEnum BE_ARCHIVE_TYPE = eINSTANCE.getBE_ARCHIVE_TYPE();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.archive.CACHE_MODE <em>CACHE MODE</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.archive.CACHE_MODE
		 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getCACHE_MODE()
		 * @generated
		 */
		EEnum CACHE_MODE = eINSTANCE.getCACHE_MODE();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.archive.WORKERS <em>WORKERS</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.archive.WORKERS
		 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getWORKERS()
		 * @generated
		 */
		EEnum WORKERS = eINSTANCE.getWORKERS();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.archive.OBJECT_MGMT_TYPE <em>OBJECT MGMT TYPE</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.archive.OBJECT_MGMT_TYPE
		 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getOBJECT_MGMT_TYPE()
		 * @generated
		 */
		EEnum OBJECT_MGMT_TYPE = eINSTANCE.getOBJECT_MGMT_TYPE();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.archive.CACHE_CONFIG_TYPE <em>CACHE CONFIG TYPE</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.archive.CACHE_CONFIG_TYPE
		 * @see com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl#getCACHE_CONFIG_TYPE()
		 * @generated
		 */
		EEnum CACHE_CONFIG_TYPE = eINSTANCE.getCACHE_CONFIG_TYPE();

	}

} //ArchivePackage
