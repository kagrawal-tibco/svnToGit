/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.configuration;

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
 * @see com.tibco.cep.studio.common.configuration.ConfigurationFactory
 * @model kind="package"
 * @generated
 */
public interface ConfigurationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "configuration";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/studio/common/configuration/model/project_configuration.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "configuration";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ConfigurationPackage eINSTANCE = com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.configuration.impl.StudioProjectConfigurationImpl <em>Studio Project Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.configuration.impl.StudioProjectConfigurationImpl
	 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getStudioProjectConfiguration()
	 * @generated
	 */
	int STUDIO_PROJECT_CONFIGURATION = 0;

	/**
	 * The feature id for the '<em><b>Thirdparty Lib Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDIO_PROJECT_CONFIGURATION__THIRDPARTY_LIB_ENTRIES = 0;

	/**
	 * The feature id for the '<em><b>Core Internal Lib Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDIO_PROJECT_CONFIGURATION__CORE_INTERNAL_LIB_ENTRIES = 1;

	/**
	 * The feature id for the '<em><b>Custom Function Lib Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDIO_PROJECT_CONFIGURATION__CUSTOM_FUNCTION_LIB_ENTRIES = 2;

	/**
	 * The feature id for the '<em><b>Project Lib Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDIO_PROJECT_CONFIGURATION__PROJECT_LIB_ENTRIES = 3;

	/**
	 * The feature id for the '<em><b>Java Classpath Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDIO_PROJECT_CONFIGURATION__JAVA_CLASSPATH_ENTRIES = 4;

	/**
	 * The feature id for the '<em><b>Java Source Folder Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDIO_PROJECT_CONFIGURATION__JAVA_SOURCE_FOLDER_ENTRIES = 5;

	/**
	 * The feature id for the '<em><b>Enterprise Archive Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDIO_PROJECT_CONFIGURATION__ENTERPRISE_ARCHIVE_CONFIGURATION = 6;

	/**
	 * The feature id for the '<em><b>Bpmn Process Settings</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDIO_PROJECT_CONFIGURATION__BPMN_PROCESS_SETTINGS = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDIO_PROJECT_CONFIGURATION__NAME = 8;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDIO_PROJECT_CONFIGURATION__VERSION = 9;

	/**
	 * The feature id for the '<em><b>Build</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDIO_PROJECT_CONFIGURATION__BUILD = 10;

	/**
	 * The feature id for the '<em><b>File Time Stamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDIO_PROJECT_CONFIGURATION__FILE_TIME_STAMP = 11;

	/**
	 * The feature id for the '<em><b>Xpath Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDIO_PROJECT_CONFIGURATION__XPATH_VERSION = 12;

	/**
	 * The number of structural features of the '<em>Studio Project Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDIO_PROJECT_CONFIGURATION_FEATURE_COUNT = 13;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.configuration.impl.ProjectConfigurationEntryImpl <em>Project Configuration Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.configuration.impl.ProjectConfigurationEntryImpl
	 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getProjectConfigurationEntry()
	 * @generated
	 */
	int PROJECT_CONFIGURATION_ENTRY = 1;

	/**
	 * The number of structural features of the '<em>Project Configuration Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_CONFIGURATION_ENTRY_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.configuration.impl.BuildPathEntryImpl <em>Build Path Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.configuration.impl.BuildPathEntryImpl
	 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getBuildPathEntry()
	 * @generated
	 */
	int BUILD_PATH_ENTRY = 2;

	/**
	 * The feature id for the '<em><b>Entry Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILD_PATH_ENTRY__ENTRY_TYPE = PROJECT_CONFIGURATION_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILD_PATH_ENTRY__PATH = PROJECT_CONFIGURATION_ENTRY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILD_PATH_ENTRY__TIMESTAMP = PROJECT_CONFIGURATION_ENTRY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Var</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILD_PATH_ENTRY__VAR = PROJECT_CONFIGURATION_ENTRY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILD_PATH_ENTRY__READ_ONLY = PROJECT_CONFIGURATION_ENTRY_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILD_PATH_ENTRY__DEPRECATED = PROJECT_CONFIGURATION_ENTRY_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Resolved Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILD_PATH_ENTRY__RESOLVED_PATH = PROJECT_CONFIGURATION_ENTRY_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Build Path Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILD_PATH_ENTRY_FEATURE_COUNT = PROJECT_CONFIGURATION_ENTRY_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.configuration.impl.ProjectLibraryEntryImpl <em>Project Library Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.configuration.impl.ProjectLibraryEntryImpl
	 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getProjectLibraryEntry()
	 * @generated
	 */
	int PROJECT_LIBRARY_ENTRY = 3;

	/**
	 * The feature id for the '<em><b>Entry Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_LIBRARY_ENTRY__ENTRY_TYPE = BUILD_PATH_ENTRY__ENTRY_TYPE;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_LIBRARY_ENTRY__PATH = BUILD_PATH_ENTRY__PATH;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_LIBRARY_ENTRY__TIMESTAMP = BUILD_PATH_ENTRY__TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Var</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_LIBRARY_ENTRY__VAR = BUILD_PATH_ENTRY__VAR;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_LIBRARY_ENTRY__READ_ONLY = BUILD_PATH_ENTRY__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_LIBRARY_ENTRY__DEPRECATED = BUILD_PATH_ENTRY__DEPRECATED;

	/**
	 * The feature id for the '<em><b>Resolved Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_LIBRARY_ENTRY__RESOLVED_PATH = BUILD_PATH_ENTRY__RESOLVED_PATH;

	/**
	 * The number of structural features of the '<em>Project Library Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_LIBRARY_ENTRY_FEATURE_COUNT = BUILD_PATH_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.configuration.impl.JavaLibEntryImpl <em>Java Lib Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.configuration.impl.JavaLibEntryImpl
	 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getJavaLibEntry()
	 * @generated
	 */
	int JAVA_LIB_ENTRY = 5;

	/**
	 * The feature id for the '<em><b>Entry Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_LIB_ENTRY__ENTRY_TYPE = BUILD_PATH_ENTRY__ENTRY_TYPE;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_LIB_ENTRY__PATH = BUILD_PATH_ENTRY__PATH;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_LIB_ENTRY__TIMESTAMP = BUILD_PATH_ENTRY__TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Var</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_LIB_ENTRY__VAR = BUILD_PATH_ENTRY__VAR;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_LIB_ENTRY__READ_ONLY = BUILD_PATH_ENTRY__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_LIB_ENTRY__DEPRECATED = BUILD_PATH_ENTRY__DEPRECATED;

	/**
	 * The feature id for the '<em><b>Resolved Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_LIB_ENTRY__RESOLVED_PATH = BUILD_PATH_ENTRY__RESOLVED_PATH;

	/**
	 * The feature id for the '<em><b>Native Library Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_LIB_ENTRY__NATIVE_LIBRARY_LOCATION = BUILD_PATH_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Java Lib Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_LIB_ENTRY_FEATURE_COUNT = BUILD_PATH_ENTRY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.configuration.impl.ThirdPartyLibEntryImpl <em>Third Party Lib Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.configuration.impl.ThirdPartyLibEntryImpl
	 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getThirdPartyLibEntry()
	 * @generated
	 */
	int THIRD_PARTY_LIB_ENTRY = 4;

	/**
	 * The feature id for the '<em><b>Entry Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIRD_PARTY_LIB_ENTRY__ENTRY_TYPE = JAVA_LIB_ENTRY__ENTRY_TYPE;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIRD_PARTY_LIB_ENTRY__PATH = JAVA_LIB_ENTRY__PATH;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIRD_PARTY_LIB_ENTRY__TIMESTAMP = JAVA_LIB_ENTRY__TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Var</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIRD_PARTY_LIB_ENTRY__VAR = JAVA_LIB_ENTRY__VAR;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIRD_PARTY_LIB_ENTRY__READ_ONLY = JAVA_LIB_ENTRY__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIRD_PARTY_LIB_ENTRY__DEPRECATED = JAVA_LIB_ENTRY__DEPRECATED;

	/**
	 * The feature id for the '<em><b>Resolved Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIRD_PARTY_LIB_ENTRY__RESOLVED_PATH = JAVA_LIB_ENTRY__RESOLVED_PATH;

	/**
	 * The feature id for the '<em><b>Native Library Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIRD_PARTY_LIB_ENTRY__NATIVE_LIBRARY_LOCATION = JAVA_LIB_ENTRY__NATIVE_LIBRARY_LOCATION;

	/**
	 * The number of structural features of the '<em>Third Party Lib Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIRD_PARTY_LIB_ENTRY_FEATURE_COUNT = JAVA_LIB_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.configuration.impl.CoreJavaLibEntryImpl <em>Core Java Lib Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.configuration.impl.CoreJavaLibEntryImpl
	 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getCoreJavaLibEntry()
	 * @generated
	 */
	int CORE_JAVA_LIB_ENTRY = 6;

	/**
	 * The feature id for the '<em><b>Entry Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORE_JAVA_LIB_ENTRY__ENTRY_TYPE = JAVA_LIB_ENTRY__ENTRY_TYPE;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORE_JAVA_LIB_ENTRY__PATH = JAVA_LIB_ENTRY__PATH;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORE_JAVA_LIB_ENTRY__TIMESTAMP = JAVA_LIB_ENTRY__TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Var</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORE_JAVA_LIB_ENTRY__VAR = JAVA_LIB_ENTRY__VAR;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORE_JAVA_LIB_ENTRY__READ_ONLY = JAVA_LIB_ENTRY__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORE_JAVA_LIB_ENTRY__DEPRECATED = JAVA_LIB_ENTRY__DEPRECATED;

	/**
	 * The feature id for the '<em><b>Resolved Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORE_JAVA_LIB_ENTRY__RESOLVED_PATH = JAVA_LIB_ENTRY__RESOLVED_PATH;

	/**
	 * The feature id for the '<em><b>Native Library Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORE_JAVA_LIB_ENTRY__NATIVE_LIBRARY_LOCATION = JAVA_LIB_ENTRY__NATIVE_LIBRARY_LOCATION;

	/**
	 * The number of structural features of the '<em>Core Java Lib Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORE_JAVA_LIB_ENTRY_FEATURE_COUNT = JAVA_LIB_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.configuration.impl.CustomFunctionLibEntryImpl <em>Custom Function Lib Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.configuration.impl.CustomFunctionLibEntryImpl
	 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getCustomFunctionLibEntry()
	 * @generated
	 */
	int CUSTOM_FUNCTION_LIB_ENTRY = 7;

	/**
	 * The feature id for the '<em><b>Entry Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_FUNCTION_LIB_ENTRY__ENTRY_TYPE = JAVA_LIB_ENTRY__ENTRY_TYPE;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_FUNCTION_LIB_ENTRY__PATH = JAVA_LIB_ENTRY__PATH;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_FUNCTION_LIB_ENTRY__TIMESTAMP = JAVA_LIB_ENTRY__TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Var</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_FUNCTION_LIB_ENTRY__VAR = JAVA_LIB_ENTRY__VAR;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_FUNCTION_LIB_ENTRY__READ_ONLY = JAVA_LIB_ENTRY__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_FUNCTION_LIB_ENTRY__DEPRECATED = JAVA_LIB_ENTRY__DEPRECATED;

	/**
	 * The feature id for the '<em><b>Resolved Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_FUNCTION_LIB_ENTRY__RESOLVED_PATH = JAVA_LIB_ENTRY__RESOLVED_PATH;

	/**
	 * The feature id for the '<em><b>Native Library Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_FUNCTION_LIB_ENTRY__NATIVE_LIBRARY_LOCATION = JAVA_LIB_ENTRY__NATIVE_LIBRARY_LOCATION;

	/**
	 * The number of structural features of the '<em>Custom Function Lib Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_FUNCTION_LIB_ENTRY_FEATURE_COUNT = JAVA_LIB_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.configuration.impl.EnterpriseArchiveEntryImpl <em>Enterprise Archive Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.configuration.impl.EnterpriseArchiveEntryImpl
	 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getEnterpriseArchiveEntry()
	 * @generated
	 */
	int ENTERPRISE_ARCHIVE_ENTRY = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTERPRISE_ARCHIVE_ENTRY__NAME = PROJECT_CONFIGURATION_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTERPRISE_ARCHIVE_ENTRY__AUTHOR = PROJECT_CONFIGURATION_ENTRY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTERPRISE_ARCHIVE_ENTRY__DESCRIPTION = PROJECT_CONFIGURATION_ENTRY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTERPRISE_ARCHIVE_ENTRY__VERSION = PROJECT_CONFIGURATION_ENTRY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTERPRISE_ARCHIVE_ENTRY__PATH = PROJECT_CONFIGURATION_ENTRY_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Include Service Level Global Vars</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTERPRISE_ARCHIVE_ENTRY__INCLUDE_SERVICE_LEVEL_GLOBAL_VARS = PROJECT_CONFIGURATION_ENTRY_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Debug</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTERPRISE_ARCHIVE_ENTRY__DEBUG = PROJECT_CONFIGURATION_ENTRY_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Temp Output Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTERPRISE_ARCHIVE_ENTRY__TEMP_OUTPUT_PATH = PROJECT_CONFIGURATION_ENTRY_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Overwrite</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTERPRISE_ARCHIVE_ENTRY__OVERWRITE = PROJECT_CONFIGURATION_ENTRY_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Delete Temp Files</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTERPRISE_ARCHIVE_ENTRY__DELETE_TEMP_FILES = PROJECT_CONFIGURATION_ENTRY_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Build Classes Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTERPRISE_ARCHIVE_ENTRY__BUILD_CLASSES_ONLY = PROJECT_CONFIGURATION_ENTRY_FEATURE_COUNT + 10;

	/**
	 * The number of structural features of the '<em>Enterprise Archive Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTERPRISE_ARCHIVE_ENTRY_FEATURE_COUNT = PROJECT_CONFIGURATION_ENTRY_FEATURE_COUNT + 11;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.configuration.impl.BpmnProcessSettingsImpl <em>Bpmn Process Settings</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.configuration.impl.BpmnProcessSettingsImpl
	 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getBpmnProcessSettings()
	 * @generated
	 */
	int BPMN_PROCESS_SETTINGS = 9;

	/**
	 * The feature id for the '<em><b>Build Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PROCESS_SETTINGS__BUILD_FOLDER = 0;

	/**
	 * The feature id for the '<em><b>Palette Path Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PROCESS_SETTINGS__PALETTE_PATH_ENTRIES = 1;

	/**
	 * The feature id for the '<em><b>Selected Process Paths</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PROCESS_SETTINGS__SELECTED_PROCESS_PATHS = 2;

	/**
	 * The feature id for the '<em><b>Process Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PROCESS_SETTINGS__PROCESS_PREFIX = 3;

	/**
	 * The feature id for the '<em><b>Rule Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PROCESS_SETTINGS__RULE_PREFIX = 4;

	/**
	 * The feature id for the '<em><b>Rule Function Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PROCESS_SETTINGS__RULE_FUNCTION_PREFIX = 5;

	/**
	 * The feature id for the '<em><b>Concept Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PROCESS_SETTINGS__CONCEPT_PREFIX = 6;

	/**
	 * The feature id for the '<em><b>Event Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PROCESS_SETTINGS__EVENT_PREFIX = 7;

	/**
	 * The feature id for the '<em><b>Time Event Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PROCESS_SETTINGS__TIME_EVENT_PREFIX = 8;

	/**
	 * The feature id for the '<em><b>Scorecard Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PROCESS_SETTINGS__SCORECARD_PREFIX = 9;

	/**
	 * The feature id for the '<em><b>Name Prefixes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PROCESS_SETTINGS__NAME_PREFIXES = 10;

	/**
	 * The number of structural features of the '<em>Bpmn Process Settings</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PROCESS_SETTINGS_FEATURE_COUNT = 11;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.configuration.impl.BpmnPalettePathEntryImpl <em>Bpmn Palette Path Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.configuration.impl.BpmnPalettePathEntryImpl
	 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getBpmnPalettePathEntry()
	 * @generated
	 */
	int BPMN_PALETTE_PATH_ENTRY = 10;

	/**
	 * The feature id for the '<em><b>Entry Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PALETTE_PATH_ENTRY__ENTRY_TYPE = BUILD_PATH_ENTRY__ENTRY_TYPE;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PALETTE_PATH_ENTRY__PATH = BUILD_PATH_ENTRY__PATH;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PALETTE_PATH_ENTRY__TIMESTAMP = BUILD_PATH_ENTRY__TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Var</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PALETTE_PATH_ENTRY__VAR = BUILD_PATH_ENTRY__VAR;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PALETTE_PATH_ENTRY__READ_ONLY = BUILD_PATH_ENTRY__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PALETTE_PATH_ENTRY__DEPRECATED = BUILD_PATH_ENTRY__DEPRECATED;

	/**
	 * The feature id for the '<em><b>Resolved Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PALETTE_PATH_ENTRY__RESOLVED_PATH = BUILD_PATH_ENTRY__RESOLVED_PATH;

	/**
	 * The number of structural features of the '<em>Bpmn Palette Path Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PALETTE_PATH_ENTRY_FEATURE_COUNT = BUILD_PATH_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.configuration.impl.BpmnProcessPathEntryImpl <em>Bpmn Process Path Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.configuration.impl.BpmnProcessPathEntryImpl
	 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getBpmnProcessPathEntry()
	 * @generated
	 */
	int BPMN_PROCESS_PATH_ENTRY = 11;

	/**
	 * The feature id for the '<em><b>Entry Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PROCESS_PATH_ENTRY__ENTRY_TYPE = BUILD_PATH_ENTRY__ENTRY_TYPE;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PROCESS_PATH_ENTRY__PATH = BUILD_PATH_ENTRY__PATH;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PROCESS_PATH_ENTRY__TIMESTAMP = BUILD_PATH_ENTRY__TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Var</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PROCESS_PATH_ENTRY__VAR = BUILD_PATH_ENTRY__VAR;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PROCESS_PATH_ENTRY__READ_ONLY = BUILD_PATH_ENTRY__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PROCESS_PATH_ENTRY__DEPRECATED = BUILD_PATH_ENTRY__DEPRECATED;

	/**
	 * The feature id for the '<em><b>Resolved Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PROCESS_PATH_ENTRY__RESOLVED_PATH = BUILD_PATH_ENTRY__RESOLVED_PATH;

	/**
	 * The number of structural features of the '<em>Bpmn Process Path Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BPMN_PROCESS_PATH_ENTRY_FEATURE_COUNT = BUILD_PATH_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.configuration.impl.NamePrefixImpl <em>Name Prefix</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.configuration.impl.NamePrefixImpl
	 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getNamePrefix()
	 * @generated
	 */
	int NAME_PREFIX = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_PREFIX__NAME = 0;

	/**
	 * The feature id for the '<em><b>Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_PREFIX__PREFIX = 1;

	/**
	 * The number of structural features of the '<em>Name Prefix</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_PREFIX_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.configuration.impl.NativeLibraryPathImpl <em>Native Library Path</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.configuration.impl.NativeLibraryPathImpl
	 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getNativeLibraryPath()
	 * @generated
	 */
	int NATIVE_LIBRARY_PATH = 13;

	/**
	 * The feature id for the '<em><b>Entry Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NATIVE_LIBRARY_PATH__ENTRY_TYPE = BUILD_PATH_ENTRY__ENTRY_TYPE;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NATIVE_LIBRARY_PATH__PATH = BUILD_PATH_ENTRY__PATH;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NATIVE_LIBRARY_PATH__TIMESTAMP = BUILD_PATH_ENTRY__TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Var</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NATIVE_LIBRARY_PATH__VAR = BUILD_PATH_ENTRY__VAR;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NATIVE_LIBRARY_PATH__READ_ONLY = BUILD_PATH_ENTRY__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NATIVE_LIBRARY_PATH__DEPRECATED = BUILD_PATH_ENTRY__DEPRECATED;

	/**
	 * The feature id for the '<em><b>Resolved Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NATIVE_LIBRARY_PATH__RESOLVED_PATH = BUILD_PATH_ENTRY__RESOLVED_PATH;

	/**
	 * The number of structural features of the '<em>Native Library Path</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NATIVE_LIBRARY_PATH_FEATURE_COUNT = BUILD_PATH_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.configuration.impl.JavaClasspathEntryImpl <em>Java Classpath Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.configuration.impl.JavaClasspathEntryImpl
	 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getJavaClasspathEntry()
	 * @generated
	 */
	int JAVA_CLASSPATH_ENTRY = 14;

	/**
	 * The feature id for the '<em><b>Entry Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_CLASSPATH_ENTRY__ENTRY_TYPE = BUILD_PATH_ENTRY__ENTRY_TYPE;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_CLASSPATH_ENTRY__PATH = BUILD_PATH_ENTRY__PATH;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_CLASSPATH_ENTRY__TIMESTAMP = BUILD_PATH_ENTRY__TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Var</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_CLASSPATH_ENTRY__VAR = BUILD_PATH_ENTRY__VAR;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_CLASSPATH_ENTRY__READ_ONLY = BUILD_PATH_ENTRY__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_CLASSPATH_ENTRY__DEPRECATED = BUILD_PATH_ENTRY__DEPRECATED;

	/**
	 * The feature id for the '<em><b>Resolved Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_CLASSPATH_ENTRY__RESOLVED_PATH = BUILD_PATH_ENTRY__RESOLVED_PATH;

	/**
	 * The feature id for the '<em><b>Classpath Entry Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_CLASSPATH_ENTRY__CLASSPATH_ENTRY_TYPE = BUILD_PATH_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_CLASSPATH_ENTRY__SOURCE_FOLDER = BUILD_PATH_ENTRY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Inclusion Pattern</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_CLASSPATH_ENTRY__INCLUSION_PATTERN = BUILD_PATH_ENTRY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Exclusion Pattern</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_CLASSPATH_ENTRY__EXCLUSION_PATTERN = BUILD_PATH_ENTRY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Output Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_CLASSPATH_ENTRY__OUTPUT_LOCATION = BUILD_PATH_ENTRY_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Source Attachment Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_CLASSPATH_ENTRY__SOURCE_ATTACHMENT_PATH = BUILD_PATH_ENTRY_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Source Attachment Root Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_CLASSPATH_ENTRY__SOURCE_ATTACHMENT_ROOT_PATH = BUILD_PATH_ENTRY_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Java Classpath Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_CLASSPATH_ENTRY_FEATURE_COUNT = BUILD_PATH_ENTRY_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.configuration.impl.JavaSourceFolderEntryImpl <em>Java Source Folder Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.configuration.impl.JavaSourceFolderEntryImpl
	 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getJavaSourceFolderEntry()
	 * @generated
	 */
	int JAVA_SOURCE_FOLDER_ENTRY = 15;

	/**
	 * The feature id for the '<em><b>Entry Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_SOURCE_FOLDER_ENTRY__ENTRY_TYPE = BUILD_PATH_ENTRY__ENTRY_TYPE;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_SOURCE_FOLDER_ENTRY__PATH = BUILD_PATH_ENTRY__PATH;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_SOURCE_FOLDER_ENTRY__TIMESTAMP = BUILD_PATH_ENTRY__TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Var</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_SOURCE_FOLDER_ENTRY__VAR = BUILD_PATH_ENTRY__VAR;

	/**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_SOURCE_FOLDER_ENTRY__READ_ONLY = BUILD_PATH_ENTRY__READ_ONLY;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_SOURCE_FOLDER_ENTRY__DEPRECATED = BUILD_PATH_ENTRY__DEPRECATED;

	/**
	 * The feature id for the '<em><b>Resolved Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_SOURCE_FOLDER_ENTRY__RESOLVED_PATH = BUILD_PATH_ENTRY__RESOLVED_PATH;

	/**
	 * The number of structural features of the '<em>Java Source Folder Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_SOURCE_FOLDER_ENTRY_FEATURE_COUNT = BUILD_PATH_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE <em>LIBRARY PATH TYPE</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE
	 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getLIBRARY_PATH_TYPE()
	 * @generated
	 */
	int LIBRARY_PATH_TYPE = 16;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.configuration.XPATH_VERSION <em>XPATH VERSION</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.configuration.XPATH_VERSION
	 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getXPATH_VERSION()
	 * @generated
	 */
	int XPATH_VERSION = 17;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.configuration.JAVA_CLASSPATH_ENTRY_TYPE <em>JAVA CLASSPATH ENTRY TYPE</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.configuration.JAVA_CLASSPATH_ENTRY_TYPE
	 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getJAVA_CLASSPATH_ENTRY_TYPE()
	 * @generated
	 */
	int JAVA_CLASSPATH_ENTRY_TYPE = 18;

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration <em>Studio Project Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Studio Project Configuration</em>'.
	 * @see com.tibco.cep.studio.common.configuration.StudioProjectConfiguration
	 * @generated
	 */
	EClass getStudioProjectConfiguration();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getThirdpartyLibEntries <em>Thirdparty Lib Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Thirdparty Lib Entries</em>'.
	 * @see com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getThirdpartyLibEntries()
	 * @see #getStudioProjectConfiguration()
	 * @generated
	 */
	EReference getStudioProjectConfiguration_ThirdpartyLibEntries();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getCoreInternalLibEntries <em>Core Internal Lib Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Core Internal Lib Entries</em>'.
	 * @see com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getCoreInternalLibEntries()
	 * @see #getStudioProjectConfiguration()
	 * @generated
	 */
	EReference getStudioProjectConfiguration_CoreInternalLibEntries();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getCustomFunctionLibEntries <em>Custom Function Lib Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Custom Function Lib Entries</em>'.
	 * @see com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getCustomFunctionLibEntries()
	 * @see #getStudioProjectConfiguration()
	 * @generated
	 */
	EReference getStudioProjectConfiguration_CustomFunctionLibEntries();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getProjectLibEntries <em>Project Lib Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Project Lib Entries</em>'.
	 * @see com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getProjectLibEntries()
	 * @see #getStudioProjectConfiguration()
	 * @generated
	 */
	EReference getStudioProjectConfiguration_ProjectLibEntries();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getJavaClasspathEntries <em>Java Classpath Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Java Classpath Entries</em>'.
	 * @see com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getJavaClasspathEntries()
	 * @see #getStudioProjectConfiguration()
	 * @generated
	 */
	EReference getStudioProjectConfiguration_JavaClasspathEntries();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getJavaSourceFolderEntries <em>Java Source Folder Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Java Source Folder Entries</em>'.
	 * @see com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getJavaSourceFolderEntries()
	 * @see #getStudioProjectConfiguration()
	 * @generated
	 */
	EReference getStudioProjectConfiguration_JavaSourceFolderEntries();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getEnterpriseArchiveConfiguration <em>Enterprise Archive Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Enterprise Archive Configuration</em>'.
	 * @see com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getEnterpriseArchiveConfiguration()
	 * @see #getStudioProjectConfiguration()
	 * @generated
	 */
	EReference getStudioProjectConfiguration_EnterpriseArchiveConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getName()
	 * @see #getStudioProjectConfiguration()
	 * @generated
	 */
	EAttribute getStudioProjectConfiguration_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getVersion()
	 * @see #getStudioProjectConfiguration()
	 * @generated
	 */
	EAttribute getStudioProjectConfiguration_Version();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getBuild <em>Build</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Build</em>'.
	 * @see com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getBuild()
	 * @see #getStudioProjectConfiguration()
	 * @generated
	 */
	EAttribute getStudioProjectConfiguration_Build();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getFileTimeStamp <em>File Time Stamp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File Time Stamp</em>'.
	 * @see com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getFileTimeStamp()
	 * @see #getStudioProjectConfiguration()
	 * @generated
	 */
	EAttribute getStudioProjectConfiguration_FileTimeStamp();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getXpathVersion <em>Xpath Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xpath Version</em>'.
	 * @see com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getXpathVersion()
	 * @see #getStudioProjectConfiguration()
	 * @generated
	 */
	EAttribute getStudioProjectConfiguration_XpathVersion();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getBpmnProcessSettings <em>Bpmn Process Settings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Bpmn Process Settings</em>'.
	 * @see com.tibco.cep.studio.common.configuration.StudioProjectConfiguration#getBpmnProcessSettings()
	 * @see #getStudioProjectConfiguration()
	 * @generated
	 */
	EReference getStudioProjectConfiguration_BpmnProcessSettings();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.common.configuration.ProjectConfigurationEntry <em>Project Configuration Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Project Configuration Entry</em>'.
	 * @see com.tibco.cep.studio.common.configuration.ProjectConfigurationEntry
	 * @generated
	 */
	EClass getProjectConfigurationEntry();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.common.configuration.BuildPathEntry <em>Build Path Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Build Path Entry</em>'.
	 * @see com.tibco.cep.studio.common.configuration.BuildPathEntry
	 * @generated
	 */
	EClass getBuildPathEntry();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.BuildPathEntry#getEntryType <em>Entry Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Entry Type</em>'.
	 * @see com.tibco.cep.studio.common.configuration.BuildPathEntry#getEntryType()
	 * @see #getBuildPathEntry()
	 * @generated
	 */
	EAttribute getBuildPathEntry_EntryType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.BuildPathEntry#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see com.tibco.cep.studio.common.configuration.BuildPathEntry#getPath()
	 * @see #getBuildPathEntry()
	 * @generated
	 */
	EAttribute getBuildPathEntry_Path();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.BuildPathEntry#getTimestamp <em>Timestamp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Timestamp</em>'.
	 * @see com.tibco.cep.studio.common.configuration.BuildPathEntry#getTimestamp()
	 * @see #getBuildPathEntry()
	 * @generated
	 */
	EAttribute getBuildPathEntry_Timestamp();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.BuildPathEntry#isVar <em>Var</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Var</em>'.
	 * @see com.tibco.cep.studio.common.configuration.BuildPathEntry#isVar()
	 * @see #getBuildPathEntry()
	 * @generated
	 */
	EAttribute getBuildPathEntry_Var();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.BuildPathEntry#isReadOnly <em>Read Only</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Read Only</em>'.
	 * @see com.tibco.cep.studio.common.configuration.BuildPathEntry#isReadOnly()
	 * @see #getBuildPathEntry()
	 * @generated
	 */
	EAttribute getBuildPathEntry_ReadOnly();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.BuildPathEntry#isDeprecated <em>Deprecated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Deprecated</em>'.
	 * @see com.tibco.cep.studio.common.configuration.BuildPathEntry#isDeprecated()
	 * @see #getBuildPathEntry()
	 * @generated
	 */
	EAttribute getBuildPathEntry_Deprecated();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.BuildPathEntry#getResolvedPath <em>Resolved Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Resolved Path</em>'.
	 * @see com.tibco.cep.studio.common.configuration.BuildPathEntry#getResolvedPath()
	 * @see #getBuildPathEntry()
	 * @generated
	 */
	EAttribute getBuildPathEntry_ResolvedPath();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.common.configuration.ProjectLibraryEntry <em>Project Library Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Project Library Entry</em>'.
	 * @see com.tibco.cep.studio.common.configuration.ProjectLibraryEntry
	 * @generated
	 */
	EClass getProjectLibraryEntry();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.common.configuration.ThirdPartyLibEntry <em>Third Party Lib Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Third Party Lib Entry</em>'.
	 * @see com.tibco.cep.studio.common.configuration.ThirdPartyLibEntry
	 * @generated
	 */
	EClass getThirdPartyLibEntry();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.common.configuration.JavaLibEntry <em>Java Lib Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Lib Entry</em>'.
	 * @see com.tibco.cep.studio.common.configuration.JavaLibEntry
	 * @generated
	 */
	EClass getJavaLibEntry();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.common.configuration.JavaLibEntry#getNativeLibraryLocation <em>Native Library Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Native Library Location</em>'.
	 * @see com.tibco.cep.studio.common.configuration.JavaLibEntry#getNativeLibraryLocation()
	 * @see #getJavaLibEntry()
	 * @generated
	 */
	EReference getJavaLibEntry_NativeLibraryLocation();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.common.configuration.CoreJavaLibEntry <em>Core Java Lib Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Core Java Lib Entry</em>'.
	 * @see com.tibco.cep.studio.common.configuration.CoreJavaLibEntry
	 * @generated
	 */
	EClass getCoreJavaLibEntry();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.common.configuration.CustomFunctionLibEntry <em>Custom Function Lib Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Custom Function Lib Entry</em>'.
	 * @see com.tibco.cep.studio.common.configuration.CustomFunctionLibEntry
	 * @generated
	 */
	EClass getCustomFunctionLibEntry();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry <em>Enterprise Archive Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enterprise Archive Entry</em>'.
	 * @see com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry
	 * @generated
	 */
	EClass getEnterpriseArchiveEntry();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#getName()
	 * @see #getEnterpriseArchiveEntry()
	 * @generated
	 */
	EAttribute getEnterpriseArchiveEntry_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#getAuthor <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Author</em>'.
	 * @see com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#getAuthor()
	 * @see #getEnterpriseArchiveEntry()
	 * @generated
	 */
	EAttribute getEnterpriseArchiveEntry_Author();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#getDescription()
	 * @see #getEnterpriseArchiveEntry()
	 * @generated
	 */
	EAttribute getEnterpriseArchiveEntry_Description();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#getVersion()
	 * @see #getEnterpriseArchiveEntry()
	 * @generated
	 */
	EAttribute getEnterpriseArchiveEntry_Version();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#getPath()
	 * @see #getEnterpriseArchiveEntry()
	 * @generated
	 */
	EAttribute getEnterpriseArchiveEntry_Path();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#isIncludeServiceLevelGlobalVars <em>Include Service Level Global Vars</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Include Service Level Global Vars</em>'.
	 * @see com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#isIncludeServiceLevelGlobalVars()
	 * @see #getEnterpriseArchiveEntry()
	 * @generated
	 */
	EAttribute getEnterpriseArchiveEntry_IncludeServiceLevelGlobalVars();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#isDebug <em>Debug</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Debug</em>'.
	 * @see com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#isDebug()
	 * @see #getEnterpriseArchiveEntry()
	 * @generated
	 */
	EAttribute getEnterpriseArchiveEntry_Debug();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#getTempOutputPath <em>Temp Output Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Temp Output Path</em>'.
	 * @see com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#getTempOutputPath()
	 * @see #getEnterpriseArchiveEntry()
	 * @generated
	 */
	EAttribute getEnterpriseArchiveEntry_TempOutputPath();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#isOverwrite <em>Overwrite</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Overwrite</em>'.
	 * @see com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#isOverwrite()
	 * @see #getEnterpriseArchiveEntry()
	 * @generated
	 */
	EAttribute getEnterpriseArchiveEntry_Overwrite();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#isDeleteTempFiles <em>Delete Temp Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Delete Temp Files</em>'.
	 * @see com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#isDeleteTempFiles()
	 * @see #getEnterpriseArchiveEntry()
	 * @generated
	 */
	EAttribute getEnterpriseArchiveEntry_DeleteTempFiles();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#isBuildClassesOnly <em>Build Classes Only</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Build Classes Only</em>'.
	 * @see com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry#isBuildClassesOnly()
	 * @see #getEnterpriseArchiveEntry()
	 * @generated
	 */
	EAttribute getEnterpriseArchiveEntry_BuildClassesOnly();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings <em>Bpmn Process Settings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bpmn Process Settings</em>'.
	 * @see com.tibco.cep.studio.common.configuration.BpmnProcessSettings
	 * @generated
	 */
	EClass getBpmnProcessSettings();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getBuildFolder <em>Build Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Build Folder</em>'.
	 * @see com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getBuildFolder()
	 * @see #getBpmnProcessSettings()
	 * @generated
	 */
	EAttribute getBpmnProcessSettings_BuildFolder();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getPalettePathEntries <em>Palette Path Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Palette Path Entries</em>'.
	 * @see com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getPalettePathEntries()
	 * @see #getBpmnProcessSettings()
	 * @generated
	 */
	EReference getBpmnProcessSettings_PalettePathEntries();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getSelectedProcessPaths <em>Selected Process Paths</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Selected Process Paths</em>'.
	 * @see com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getSelectedProcessPaths()
	 * @see #getBpmnProcessSettings()
	 * @generated
	 */
	EReference getBpmnProcessSettings_SelectedProcessPaths();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getProcessPrefix <em>Process Prefix</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Process Prefix</em>'.
	 * @see com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getProcessPrefix()
	 * @see #getBpmnProcessSettings()
	 * @generated
	 */
	EAttribute getBpmnProcessSettings_ProcessPrefix();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getRulePrefix <em>Rule Prefix</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rule Prefix</em>'.
	 * @see com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getRulePrefix()
	 * @see #getBpmnProcessSettings()
	 * @generated
	 */
	EAttribute getBpmnProcessSettings_RulePrefix();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getRuleFunctionPrefix <em>Rule Function Prefix</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rule Function Prefix</em>'.
	 * @see com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getRuleFunctionPrefix()
	 * @see #getBpmnProcessSettings()
	 * @generated
	 */
	EAttribute getBpmnProcessSettings_RuleFunctionPrefix();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getConceptPrefix <em>Concept Prefix</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Concept Prefix</em>'.
	 * @see com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getConceptPrefix()
	 * @see #getBpmnProcessSettings()
	 * @generated
	 */
	EAttribute getBpmnProcessSettings_ConceptPrefix();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getEventPrefix <em>Event Prefix</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Event Prefix</em>'.
	 * @see com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getEventPrefix()
	 * @see #getBpmnProcessSettings()
	 * @generated
	 */
	EAttribute getBpmnProcessSettings_EventPrefix();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getTimeEventPrefix <em>Time Event Prefix</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time Event Prefix</em>'.
	 * @see com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getTimeEventPrefix()
	 * @see #getBpmnProcessSettings()
	 * @generated
	 */
	EAttribute getBpmnProcessSettings_TimeEventPrefix();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getScorecardPrefix <em>Scorecard Prefix</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Scorecard Prefix</em>'.
	 * @see com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getScorecardPrefix()
	 * @see #getBpmnProcessSettings()
	 * @generated
	 */
	EAttribute getBpmnProcessSettings_ScorecardPrefix();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getNamePrefixes <em>Name Prefixes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Name Prefixes</em>'.
	 * @see com.tibco.cep.studio.common.configuration.BpmnProcessSettings#getNamePrefixes()
	 * @see #getBpmnProcessSettings()
	 * @generated
	 */
	EReference getBpmnProcessSettings_NamePrefixes();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.common.configuration.BpmnPalettePathEntry <em>Bpmn Palette Path Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bpmn Palette Path Entry</em>'.
	 * @see com.tibco.cep.studio.common.configuration.BpmnPalettePathEntry
	 * @generated
	 */
	EClass getBpmnPalettePathEntry();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.common.configuration.BpmnProcessPathEntry <em>Bpmn Process Path Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bpmn Process Path Entry</em>'.
	 * @see com.tibco.cep.studio.common.configuration.BpmnProcessPathEntry
	 * @generated
	 */
	EClass getBpmnProcessPathEntry();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.common.configuration.NamePrefix <em>Name Prefix</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Name Prefix</em>'.
	 * @see com.tibco.cep.studio.common.configuration.NamePrefix
	 * @generated
	 */
	EClass getNamePrefix();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.NamePrefix#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.cep.studio.common.configuration.NamePrefix#getName()
	 * @see #getNamePrefix()
	 * @generated
	 */
	EAttribute getNamePrefix_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.NamePrefix#getPrefix <em>Prefix</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Prefix</em>'.
	 * @see com.tibco.cep.studio.common.configuration.NamePrefix#getPrefix()
	 * @see #getNamePrefix()
	 * @generated
	 */
	EAttribute getNamePrefix_Prefix();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.common.configuration.NativeLibraryPath <em>Native Library Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Native Library Path</em>'.
	 * @see com.tibco.cep.studio.common.configuration.NativeLibraryPath
	 * @generated
	 */
	EClass getNativeLibraryPath();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.common.configuration.JavaClasspathEntry <em>Java Classpath Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Classpath Entry</em>'.
	 * @see com.tibco.cep.studio.common.configuration.JavaClasspathEntry
	 * @generated
	 */
	EClass getJavaClasspathEntry();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.JavaClasspathEntry#getClasspathEntryType <em>Classpath Entry Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Classpath Entry Type</em>'.
	 * @see com.tibco.cep.studio.common.configuration.JavaClasspathEntry#getClasspathEntryType()
	 * @see #getJavaClasspathEntry()
	 * @generated
	 */
	EAttribute getJavaClasspathEntry_ClasspathEntryType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.JavaClasspathEntry#getSourceFolder <em>Source Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source Folder</em>'.
	 * @see com.tibco.cep.studio.common.configuration.JavaClasspathEntry#getSourceFolder()
	 * @see #getJavaClasspathEntry()
	 * @generated
	 */
	EAttribute getJavaClasspathEntry_SourceFolder();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.studio.common.configuration.JavaClasspathEntry#getInclusionPattern <em>Inclusion Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Inclusion Pattern</em>'.
	 * @see com.tibco.cep.studio.common.configuration.JavaClasspathEntry#getInclusionPattern()
	 * @see #getJavaClasspathEntry()
	 * @generated
	 */
	EAttribute getJavaClasspathEntry_InclusionPattern();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.studio.common.configuration.JavaClasspathEntry#getExclusionPattern <em>Exclusion Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Exclusion Pattern</em>'.
	 * @see com.tibco.cep.studio.common.configuration.JavaClasspathEntry#getExclusionPattern()
	 * @see #getJavaClasspathEntry()
	 * @generated
	 */
	EAttribute getJavaClasspathEntry_ExclusionPattern();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.JavaClasspathEntry#getOutputLocation <em>Output Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Output Location</em>'.
	 * @see com.tibco.cep.studio.common.configuration.JavaClasspathEntry#getOutputLocation()
	 * @see #getJavaClasspathEntry()
	 * @generated
	 */
	EAttribute getJavaClasspathEntry_OutputLocation();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.JavaClasspathEntry#getSourceAttachmentPath <em>Source Attachment Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source Attachment Path</em>'.
	 * @see com.tibco.cep.studio.common.configuration.JavaClasspathEntry#getSourceAttachmentPath()
	 * @see #getJavaClasspathEntry()
	 * @generated
	 */
	EAttribute getJavaClasspathEntry_SourceAttachmentPath();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.configuration.JavaClasspathEntry#getSourceAttachmentRootPath <em>Source Attachment Root Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source Attachment Root Path</em>'.
	 * @see com.tibco.cep.studio.common.configuration.JavaClasspathEntry#getSourceAttachmentRootPath()
	 * @see #getJavaClasspathEntry()
	 * @generated
	 */
	EAttribute getJavaClasspathEntry_SourceAttachmentRootPath();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.common.configuration.JavaSourceFolderEntry <em>Java Source Folder Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Source Folder Entry</em>'.
	 * @see com.tibco.cep.studio.common.configuration.JavaSourceFolderEntry
	 * @generated
	 */
	EClass getJavaSourceFolderEntry();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE <em>LIBRARY PATH TYPE</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>LIBRARY PATH TYPE</em>'.
	 * @see com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE
	 * @generated
	 */
	EEnum getLIBRARY_PATH_TYPE();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.studio.common.configuration.XPATH_VERSION <em>XPATH VERSION</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>XPATH VERSION</em>'.
	 * @see com.tibco.cep.studio.common.configuration.XPATH_VERSION
	 * @generated
	 */
	EEnum getXPATH_VERSION();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.studio.common.configuration.JAVA_CLASSPATH_ENTRY_TYPE <em>JAVA CLASSPATH ENTRY TYPE</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>JAVA CLASSPATH ENTRY TYPE</em>'.
	 * @see com.tibco.cep.studio.common.configuration.JAVA_CLASSPATH_ENTRY_TYPE
	 * @generated
	 */
	EEnum getJAVA_CLASSPATH_ENTRY_TYPE();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ConfigurationFactory getConfigurationFactory();

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
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.configuration.impl.StudioProjectConfigurationImpl <em>Studio Project Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.configuration.impl.StudioProjectConfigurationImpl
		 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getStudioProjectConfiguration()
		 * @generated
		 */
		EClass STUDIO_PROJECT_CONFIGURATION = eINSTANCE.getStudioProjectConfiguration();

		/**
		 * The meta object literal for the '<em><b>Thirdparty Lib Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STUDIO_PROJECT_CONFIGURATION__THIRDPARTY_LIB_ENTRIES = eINSTANCE.getStudioProjectConfiguration_ThirdpartyLibEntries();

		/**
		 * The meta object literal for the '<em><b>Core Internal Lib Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STUDIO_PROJECT_CONFIGURATION__CORE_INTERNAL_LIB_ENTRIES = eINSTANCE.getStudioProjectConfiguration_CoreInternalLibEntries();

		/**
		 * The meta object literal for the '<em><b>Custom Function Lib Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STUDIO_PROJECT_CONFIGURATION__CUSTOM_FUNCTION_LIB_ENTRIES = eINSTANCE.getStudioProjectConfiguration_CustomFunctionLibEntries();

		/**
		 * The meta object literal for the '<em><b>Project Lib Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STUDIO_PROJECT_CONFIGURATION__PROJECT_LIB_ENTRIES = eINSTANCE.getStudioProjectConfiguration_ProjectLibEntries();

		/**
		 * The meta object literal for the '<em><b>Java Classpath Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STUDIO_PROJECT_CONFIGURATION__JAVA_CLASSPATH_ENTRIES = eINSTANCE.getStudioProjectConfiguration_JavaClasspathEntries();

		/**
		 * The meta object literal for the '<em><b>Java Source Folder Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STUDIO_PROJECT_CONFIGURATION__JAVA_SOURCE_FOLDER_ENTRIES = eINSTANCE.getStudioProjectConfiguration_JavaSourceFolderEntries();

		/**
		 * The meta object literal for the '<em><b>Enterprise Archive Configuration</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STUDIO_PROJECT_CONFIGURATION__ENTERPRISE_ARCHIVE_CONFIGURATION = eINSTANCE.getStudioProjectConfiguration_EnterpriseArchiveConfiguration();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STUDIO_PROJECT_CONFIGURATION__NAME = eINSTANCE.getStudioProjectConfiguration_Name();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STUDIO_PROJECT_CONFIGURATION__VERSION = eINSTANCE.getStudioProjectConfiguration_Version();

		/**
		 * The meta object literal for the '<em><b>Build</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STUDIO_PROJECT_CONFIGURATION__BUILD = eINSTANCE.getStudioProjectConfiguration_Build();

		/**
		 * The meta object literal for the '<em><b>File Time Stamp</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STUDIO_PROJECT_CONFIGURATION__FILE_TIME_STAMP = eINSTANCE.getStudioProjectConfiguration_FileTimeStamp();

		/**
		 * The meta object literal for the '<em><b>Xpath Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STUDIO_PROJECT_CONFIGURATION__XPATH_VERSION = eINSTANCE.getStudioProjectConfiguration_XpathVersion();

		/**
		 * The meta object literal for the '<em><b>Bpmn Process Settings</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STUDIO_PROJECT_CONFIGURATION__BPMN_PROCESS_SETTINGS = eINSTANCE.getStudioProjectConfiguration_BpmnProcessSettings();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.configuration.impl.ProjectConfigurationEntryImpl <em>Project Configuration Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.configuration.impl.ProjectConfigurationEntryImpl
		 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getProjectConfigurationEntry()
		 * @generated
		 */
		EClass PROJECT_CONFIGURATION_ENTRY = eINSTANCE.getProjectConfigurationEntry();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.configuration.impl.BuildPathEntryImpl <em>Build Path Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.configuration.impl.BuildPathEntryImpl
		 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getBuildPathEntry()
		 * @generated
		 */
		EClass BUILD_PATH_ENTRY = eINSTANCE.getBuildPathEntry();

		/**
		 * The meta object literal for the '<em><b>Entry Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUILD_PATH_ENTRY__ENTRY_TYPE = eINSTANCE.getBuildPathEntry_EntryType();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUILD_PATH_ENTRY__PATH = eINSTANCE.getBuildPathEntry_Path();

		/**
		 * The meta object literal for the '<em><b>Timestamp</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUILD_PATH_ENTRY__TIMESTAMP = eINSTANCE.getBuildPathEntry_Timestamp();

		/**
		 * The meta object literal for the '<em><b>Var</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUILD_PATH_ENTRY__VAR = eINSTANCE.getBuildPathEntry_Var();

		/**
		 * The meta object literal for the '<em><b>Read Only</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUILD_PATH_ENTRY__READ_ONLY = eINSTANCE.getBuildPathEntry_ReadOnly();

		/**
		 * The meta object literal for the '<em><b>Deprecated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUILD_PATH_ENTRY__DEPRECATED = eINSTANCE.getBuildPathEntry_Deprecated();

		/**
		 * The meta object literal for the '<em><b>Resolved Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUILD_PATH_ENTRY__RESOLVED_PATH = eINSTANCE.getBuildPathEntry_ResolvedPath();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.configuration.impl.ProjectLibraryEntryImpl <em>Project Library Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.configuration.impl.ProjectLibraryEntryImpl
		 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getProjectLibraryEntry()
		 * @generated
		 */
		EClass PROJECT_LIBRARY_ENTRY = eINSTANCE.getProjectLibraryEntry();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.configuration.impl.ThirdPartyLibEntryImpl <em>Third Party Lib Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.configuration.impl.ThirdPartyLibEntryImpl
		 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getThirdPartyLibEntry()
		 * @generated
		 */
		EClass THIRD_PARTY_LIB_ENTRY = eINSTANCE.getThirdPartyLibEntry();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.configuration.impl.JavaLibEntryImpl <em>Java Lib Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.configuration.impl.JavaLibEntryImpl
		 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getJavaLibEntry()
		 * @generated
		 */
		EClass JAVA_LIB_ENTRY = eINSTANCE.getJavaLibEntry();

		/**
		 * The meta object literal for the '<em><b>Native Library Location</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JAVA_LIB_ENTRY__NATIVE_LIBRARY_LOCATION = eINSTANCE.getJavaLibEntry_NativeLibraryLocation();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.configuration.impl.CoreJavaLibEntryImpl <em>Core Java Lib Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.configuration.impl.CoreJavaLibEntryImpl
		 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getCoreJavaLibEntry()
		 * @generated
		 */
		EClass CORE_JAVA_LIB_ENTRY = eINSTANCE.getCoreJavaLibEntry();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.configuration.impl.CustomFunctionLibEntryImpl <em>Custom Function Lib Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.configuration.impl.CustomFunctionLibEntryImpl
		 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getCustomFunctionLibEntry()
		 * @generated
		 */
		EClass CUSTOM_FUNCTION_LIB_ENTRY = eINSTANCE.getCustomFunctionLibEntry();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.configuration.impl.EnterpriseArchiveEntryImpl <em>Enterprise Archive Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.configuration.impl.EnterpriseArchiveEntryImpl
		 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getEnterpriseArchiveEntry()
		 * @generated
		 */
		EClass ENTERPRISE_ARCHIVE_ENTRY = eINSTANCE.getEnterpriseArchiveEntry();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTERPRISE_ARCHIVE_ENTRY__NAME = eINSTANCE.getEnterpriseArchiveEntry_Name();

		/**
		 * The meta object literal for the '<em><b>Author</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTERPRISE_ARCHIVE_ENTRY__AUTHOR = eINSTANCE.getEnterpriseArchiveEntry_Author();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTERPRISE_ARCHIVE_ENTRY__DESCRIPTION = eINSTANCE.getEnterpriseArchiveEntry_Description();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTERPRISE_ARCHIVE_ENTRY__VERSION = eINSTANCE.getEnterpriseArchiveEntry_Version();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTERPRISE_ARCHIVE_ENTRY__PATH = eINSTANCE.getEnterpriseArchiveEntry_Path();

		/**
		 * The meta object literal for the '<em><b>Include Service Level Global Vars</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTERPRISE_ARCHIVE_ENTRY__INCLUDE_SERVICE_LEVEL_GLOBAL_VARS = eINSTANCE.getEnterpriseArchiveEntry_IncludeServiceLevelGlobalVars();

		/**
		 * The meta object literal for the '<em><b>Debug</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTERPRISE_ARCHIVE_ENTRY__DEBUG = eINSTANCE.getEnterpriseArchiveEntry_Debug();

		/**
		 * The meta object literal for the '<em><b>Temp Output Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTERPRISE_ARCHIVE_ENTRY__TEMP_OUTPUT_PATH = eINSTANCE.getEnterpriseArchiveEntry_TempOutputPath();

		/**
		 * The meta object literal for the '<em><b>Overwrite</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTERPRISE_ARCHIVE_ENTRY__OVERWRITE = eINSTANCE.getEnterpriseArchiveEntry_Overwrite();

		/**
		 * The meta object literal for the '<em><b>Delete Temp Files</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTERPRISE_ARCHIVE_ENTRY__DELETE_TEMP_FILES = eINSTANCE.getEnterpriseArchiveEntry_DeleteTempFiles();

		/**
		 * The meta object literal for the '<em><b>Build Classes Only</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTERPRISE_ARCHIVE_ENTRY__BUILD_CLASSES_ONLY = eINSTANCE.getEnterpriseArchiveEntry_BuildClassesOnly();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.configuration.impl.BpmnProcessSettingsImpl <em>Bpmn Process Settings</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.configuration.impl.BpmnProcessSettingsImpl
		 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getBpmnProcessSettings()
		 * @generated
		 */
		EClass BPMN_PROCESS_SETTINGS = eINSTANCE.getBpmnProcessSettings();

		/**
		 * The meta object literal for the '<em><b>Build Folder</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BPMN_PROCESS_SETTINGS__BUILD_FOLDER = eINSTANCE.getBpmnProcessSettings_BuildFolder();

		/**
		 * The meta object literal for the '<em><b>Palette Path Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BPMN_PROCESS_SETTINGS__PALETTE_PATH_ENTRIES = eINSTANCE.getBpmnProcessSettings_PalettePathEntries();

		/**
		 * The meta object literal for the '<em><b>Selected Process Paths</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BPMN_PROCESS_SETTINGS__SELECTED_PROCESS_PATHS = eINSTANCE.getBpmnProcessSettings_SelectedProcessPaths();

		/**
		 * The meta object literal for the '<em><b>Process Prefix</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BPMN_PROCESS_SETTINGS__PROCESS_PREFIX = eINSTANCE.getBpmnProcessSettings_ProcessPrefix();

		/**
		 * The meta object literal for the '<em><b>Rule Prefix</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BPMN_PROCESS_SETTINGS__RULE_PREFIX = eINSTANCE.getBpmnProcessSettings_RulePrefix();

		/**
		 * The meta object literal for the '<em><b>Rule Function Prefix</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BPMN_PROCESS_SETTINGS__RULE_FUNCTION_PREFIX = eINSTANCE.getBpmnProcessSettings_RuleFunctionPrefix();

		/**
		 * The meta object literal for the '<em><b>Concept Prefix</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BPMN_PROCESS_SETTINGS__CONCEPT_PREFIX = eINSTANCE.getBpmnProcessSettings_ConceptPrefix();

		/**
		 * The meta object literal for the '<em><b>Event Prefix</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BPMN_PROCESS_SETTINGS__EVENT_PREFIX = eINSTANCE.getBpmnProcessSettings_EventPrefix();

		/**
		 * The meta object literal for the '<em><b>Time Event Prefix</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BPMN_PROCESS_SETTINGS__TIME_EVENT_PREFIX = eINSTANCE.getBpmnProcessSettings_TimeEventPrefix();

		/**
		 * The meta object literal for the '<em><b>Scorecard Prefix</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BPMN_PROCESS_SETTINGS__SCORECARD_PREFIX = eINSTANCE.getBpmnProcessSettings_ScorecardPrefix();

		/**
		 * The meta object literal for the '<em><b>Name Prefixes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BPMN_PROCESS_SETTINGS__NAME_PREFIXES = eINSTANCE.getBpmnProcessSettings_NamePrefixes();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.configuration.impl.BpmnPalettePathEntryImpl <em>Bpmn Palette Path Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.configuration.impl.BpmnPalettePathEntryImpl
		 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getBpmnPalettePathEntry()
		 * @generated
		 */
		EClass BPMN_PALETTE_PATH_ENTRY = eINSTANCE.getBpmnPalettePathEntry();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.configuration.impl.BpmnProcessPathEntryImpl <em>Bpmn Process Path Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.configuration.impl.BpmnProcessPathEntryImpl
		 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getBpmnProcessPathEntry()
		 * @generated
		 */
		EClass BPMN_PROCESS_PATH_ENTRY = eINSTANCE.getBpmnProcessPathEntry();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.configuration.impl.NamePrefixImpl <em>Name Prefix</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.configuration.impl.NamePrefixImpl
		 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getNamePrefix()
		 * @generated
		 */
		EClass NAME_PREFIX = eINSTANCE.getNamePrefix();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAME_PREFIX__NAME = eINSTANCE.getNamePrefix_Name();

		/**
		 * The meta object literal for the '<em><b>Prefix</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAME_PREFIX__PREFIX = eINSTANCE.getNamePrefix_Prefix();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.configuration.impl.NativeLibraryPathImpl <em>Native Library Path</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.configuration.impl.NativeLibraryPathImpl
		 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getNativeLibraryPath()
		 * @generated
		 */
		EClass NATIVE_LIBRARY_PATH = eINSTANCE.getNativeLibraryPath();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.configuration.impl.JavaClasspathEntryImpl <em>Java Classpath Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.configuration.impl.JavaClasspathEntryImpl
		 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getJavaClasspathEntry()
		 * @generated
		 */
		EClass JAVA_CLASSPATH_ENTRY = eINSTANCE.getJavaClasspathEntry();

		/**
		 * The meta object literal for the '<em><b>Classpath Entry Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JAVA_CLASSPATH_ENTRY__CLASSPATH_ENTRY_TYPE = eINSTANCE.getJavaClasspathEntry_ClasspathEntryType();

		/**
		 * The meta object literal for the '<em><b>Source Folder</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JAVA_CLASSPATH_ENTRY__SOURCE_FOLDER = eINSTANCE.getJavaClasspathEntry_SourceFolder();

		/**
		 * The meta object literal for the '<em><b>Inclusion Pattern</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JAVA_CLASSPATH_ENTRY__INCLUSION_PATTERN = eINSTANCE.getJavaClasspathEntry_InclusionPattern();

		/**
		 * The meta object literal for the '<em><b>Exclusion Pattern</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JAVA_CLASSPATH_ENTRY__EXCLUSION_PATTERN = eINSTANCE.getJavaClasspathEntry_ExclusionPattern();

		/**
		 * The meta object literal for the '<em><b>Output Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JAVA_CLASSPATH_ENTRY__OUTPUT_LOCATION = eINSTANCE.getJavaClasspathEntry_OutputLocation();

		/**
		 * The meta object literal for the '<em><b>Source Attachment Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JAVA_CLASSPATH_ENTRY__SOURCE_ATTACHMENT_PATH = eINSTANCE.getJavaClasspathEntry_SourceAttachmentPath();

		/**
		 * The meta object literal for the '<em><b>Source Attachment Root Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JAVA_CLASSPATH_ENTRY__SOURCE_ATTACHMENT_ROOT_PATH = eINSTANCE.getJavaClasspathEntry_SourceAttachmentRootPath();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.configuration.impl.JavaSourceFolderEntryImpl <em>Java Source Folder Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.configuration.impl.JavaSourceFolderEntryImpl
		 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getJavaSourceFolderEntry()
		 * @generated
		 */
		EClass JAVA_SOURCE_FOLDER_ENTRY = eINSTANCE.getJavaSourceFolderEntry();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE <em>LIBRARY PATH TYPE</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE
		 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getLIBRARY_PATH_TYPE()
		 * @generated
		 */
		EEnum LIBRARY_PATH_TYPE = eINSTANCE.getLIBRARY_PATH_TYPE();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.configuration.XPATH_VERSION <em>XPATH VERSION</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.configuration.XPATH_VERSION
		 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getXPATH_VERSION()
		 * @generated
		 */
		EEnum XPATH_VERSION = eINSTANCE.getXPATH_VERSION();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.configuration.JAVA_CLASSPATH_ENTRY_TYPE <em>JAVA CLASSPATH ENTRY TYPE</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.configuration.JAVA_CLASSPATH_ENTRY_TYPE
		 * @see com.tibco.cep.studio.common.configuration.impl.ConfigurationPackageImpl#getJAVA_CLASSPATH_ENTRY_TYPE()
		 * @generated
		 */
		EEnum JAVA_CLASSPATH_ENTRY_TYPE = eINSTANCE.getJAVA_CLASSPATH_ENTRY_TYPE();

	}

} //ConfigurationPackage
