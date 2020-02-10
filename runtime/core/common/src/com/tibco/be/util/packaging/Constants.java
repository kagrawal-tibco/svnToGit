package com.tibco.be.util.packaging;


import com.tibco.xml.data.primitive.ExpandedName;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Feb 16, 2007
 * Time: 11:30:05 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Constants {


    String ARCHIVE_EXTENSION = ".bar";
    String QUERY_ARCHIVE_EXTENSION = ".qar";

    String PATH_TO_DEPLOYMENT_FILE = "/com/tibco/deployment/be-engine.xml";

    String NAME_BE_PROPERTIES = "BusinessEvents Properties";
    String NAME_JAR_FILE = "be.jar";
    String NAME_SHOW_COMPILER_DEBUG_TAB = "TIBCO.BE.debug.compiler.tab";

    String PROPERTY_NAME_ARCHIVE_TYPE = "archiveType";
    String PROPERTY_NAME_NAME = "name";
    String PROPERTY_NAME_BUSINESSEVENTS_ARCHIVE = "BusinessEventsArchive";
    String PROPERTY_NAME_BUSINESSEVENTS_ARCHIVES = "BusinessEventsArchives";
    String PROPERTY_NAME_OM_ENABLE = "omEnable";
    String PROPERTY_NAME_OM_CLASS = "omClass";
    String PROPERTY_NAME_OM_BDB_CHECKPOINT_INTERVAL = "omCheckPtInterval";
    String PROPERTY_NAME_OM_BDB_PROPERTY_CACHE_SIZE = "omPropCacheSize";
    String PROPERTY_NAME_OM_BDB_CHECKPOINT_OPS_LIMIT = "omCheckPtOpsLimit";
    String PROPERTY_NAME_OM_BDB_DELETE_POLICY = "omDeletePolicy";
    String PROPERTY_NAME_OM_BDB_NO_RECOVERY_REQD = "omNoRecovery";
    String PROPERTY_NAME_OM_BDB_DB_ENV_DIR = "omDbEnvDir";
    String PROPERTY_NAME_OM_BDB_NUM_BDB = "omNumBDB";
    String PROPERTY_NAME_OM_CACHE_ADVANCED_ENTITIES = "omtgAdvancedEntitySettings";
    String PROPERTY_NAME_OM_IN_MEMORY = "inMemory";
    String PROPERTY_NAME_OM_TANGOSOL_AGENT_NAME = "omtgGlobalCache";
    String PROPERTY_NAME_OM_TANGOSOL_CACHECONFIGFILE = "omtgCacheConfigFile";
    String PROPERTY_NAME_OM_TANGOSOL_CACHECONFIGFILE_FILE = PROPERTY_NAME_OM_TANGOSOL_CACHECONFIGFILE + ".file";
    String PROPERTY_NAME_OM_TANGOSOL_CACHECONFIGFILE_FILE_REF = PROPERTY_NAME_OM_TANGOSOL_CACHECONFIGFILE_FILE + ".ref";
    String PROPERTY_NAME_OM_TANGOSOL_CACHECONFIGFILE_RESOURCE = PROPERTY_NAME_OM_TANGOSOL_CACHECONFIGFILE + ".resource";
    String PROPERTY_NAME_OM_TANGOSOL_CACHECONFIGFILE_RESOURCE_REF = PROPERTY_NAME_OM_TANGOSOL_CACHECONFIGFILE_RESOURCE + ".ref";
    String PROPERTY_NAME_OM_TANGOSOL_RECOVERY_FACTORY = "omtgCustomRecoveryFactory";
    String PROPERTY_NAME_OM_ENABLE_FALSE = "" + false;
    String PROPERTY_NAME_OM_TANGOSOL = "tangosol";
    String PROPERTY_NAME_OM_BDB = "bdb";
    String PROPERTY_NAME_OM_QUERY = "query";
    String PROPERTY_NAME_INCLUDE_ALL_RULESETS = "includeAllRuleSets";
    String PROPERTY_NAME_ALL_DESTINATIONS_INPUT_ENABLED = "allDestinationsInputEnabled";
    String PROPERTY_NAME_DESTINATIONS_INPUT_ENABLED = "destinationsInputEnabled";
    String PROPERTY_NAME_DESTINATIONS_RULEFUNCTIONS = "destinationsRuleFunctions";
    String PROPERTY_NAME_DESTINATIONS_WORKERS = "destinationsworkers";
    String PROPERTY_NAME_DESTINATIONS_QUEUE_SIZE = "destinationsqueuesize";
    String PROPERTY_NAME_DESTINATIONS_QUEUE_WEIGHT = "destinationsqueueweight";
    String PROPERTY_NAME_COMPILE_PATH = "compilePath";
    String PROPERTY_NAME_EXTRA_CLASSPATH = "extraClasspath";
    String PROPERTY_NAME_RULESETS = "ruleSets";
    String PROPERTY_NAME_INCLUDE_NO_RULESET = "includeNoRuleSet";
    String PROPERTY_NAME_RULE_FN_COLUMN = "ruleFn";
    String PROPERTY_NAME_COLUMN_WORKERS = "inputworkers";
    String PROPERTY_NAME_COLUMN_QUEUE_SIZE = "inputqueuesize";
    String PROPERTY_NAME_COLUMN_QUEUE_WEIGHT = "inputqueueweight";
    String PROPERTY_NAME_STARTUP = "startup";
    String PROPERTY_NAME_SHUTDOWN = "shutdown";
    String PROPERTY_NAME_COMPILE_WITH_DEBUG = "compileWithDebug";
//    String PROPERTY_NAME_COMPILE_WITH_DEBUG = "enableDebug";
    String PROPERTY_NAME_DELETE_TMP_FILES = "deleteTempFiles";
    String PROPERTY_NAME_DEBUG_USE_DEFAULT = "debugUseDefault";
    String PROPERTY_NAME_DEBUG_JAVA_HOME = "debugJavaHome";
    String PROPERTY_NAME_DEBUG_BE_HOME = "debugBEHome";
    String PROPERTY_NAME_DEBUG_ENGINE_TRA = "debugEngineTRA";
    String PROPERTY_NAME_DEBUG_EAR = "debugEAR";
    String PROPERTY_NAME_DEBUG_WORKING_DIR = "debugWorkingDir";
    String PROPERTY_NAME_DEBUG_VM_ARGS = "debugVMArgs";
    String PROPERTY_NAME_DEBUG_BUILD_EAR = "buildDebugEAR";
    String PROPERTY_NAME_DEBUG_APP_ARGS = "debugAppArgs";

    String VALUE_ARCHIVE_TYPE_INFERENCE = ArchiveType.INFERENCE.name();
    String VALUE_ARCHIVE_TYPE_INFERENCE_AND_QUERY = ArchiveType.INFERENCE_AND_QUERY.name();
    String VALUE_ARCHIVE_TYPE_QUERY = ArchiveType.QUERY.name();
    String VALUE_OM_ENABLE_DEFAULT = "false";
    String VALUE_OM_CHKPT_INTERVAL_DEFAULT = "30";
    String VALUE_OM_PROPERTY_CACHE_SIZE_DEFAULT = "10000";
    String VALUE_OM_CHKPT_OPS_LIMIT_DEFAULT = "1000";
    String VALUE_OM_TANGOSOL_CACHE_NAME_DEFAULT = "<Default>";
    String VALUE_OM_TANGOSOL_DEFAULT_AGENT_NAME_PREFIX = "agent";
    String VALUE_CACHE_MODE_CACHE_AND_MEMORY = "cacheAndMemory";
    String VALUE_CACHE_MODE_CACHE_ONLY = "cache";
    String VALUE_CACHE_MODE_MEMORY_ONLY = "memory";
    String[] VALUES_CACHE_MODE = new String[]{
            VALUE_CACHE_MODE_CACHE_AND_MEMORY,
            VALUE_CACHE_MODE_CACHE_ONLY,
            VALUE_CACHE_MODE_MEMORY_ONLY
    };

    String VALUES_INPUT_WORKERS_SYSTEM = "0";
    String VALUES_INPUT_WORKERS_CLIENT = "-1";
    String VALUES_INPUT_WORKERS_1 = "1";
    String VALUES_INPUT_WORKERS_2 = "2";
    String VALUES_INPUT_WORKERS_3 = "3";
    String VALUES_INPUT_WORKERS_4 = "4";
    String VALUES_INPUT_WORKERS_5 = "5";
    String VALUES_INPUT_WORKERS_6 = "6";
    String VALUES_INPUT_WORKERS_7 = "7";
    String VALUES_INPUT_WORKERS_8 = "8";
    String[] VALUES_INPUT_WORKERS = new String[]{
            VALUES_INPUT_WORKERS_SYSTEM,
            VALUES_INPUT_WORKERS_CLIENT,
            VALUES_INPUT_WORKERS_1,
            VALUES_INPUT_WORKERS_2,
            VALUES_INPUT_WORKERS_3,
            VALUES_INPUT_WORKERS_4,
            VALUES_INPUT_WORKERS_5,
            VALUES_INPUT_WORKERS_6,
            VALUES_INPUT_WORKERS_7,
            VALUES_INPUT_WORKERS_8
    };

    int VALUE_INPUT_WORKERS_DEFAULT = 0;
    int VALUE_INPUT_QUEUE_SIZE_DEFAULT = 0;
    int VALUE_INPUT_QUEUE_WEIGHT_DEFAULT = 0;

    ExpandedName XNAME_PROPERTIES = ExpandedName.makeName("properties");
    ExpandedName XNAME_PROPERTY = ExpandedName.makeName("property");
    ExpandedName XNAME_NAME = ExpandedName.makeName(PROPERTY_NAME_NAME);
    ExpandedName XNAME_DEFAULT = ExpandedName.makeName("default");
    ExpandedName XNAME_DESCRIPTION = ExpandedName.makeName("description");
    ExpandedName XNAME_VERSION = ExpandedName.makeName("version");
    ExpandedName XNAME_DEPLOYED_ELEMS = ExpandedName.makeName(PROPERTY_NAME_OM_CACHE_ADVANCED_ENTITIES);
    ExpandedName XNAME_ROW = ExpandedName.makeName("row");
    ExpandedName XNAME_TYPE = ExpandedName.makeName("type");


    public static enum ArchiveType {
        INFERENCE,
        INFERENCE_AND_QUERY,
        QUERY,
        DASHBOARD, //Added by Anand 12/08/2009 to support dashboard agent
        DATAGRID,//Added by Suresh 11/20/2010 to support CacheAgent
        PROCESSGRAPH,
        LIVEVIEW

    }


    interface Config {

        String NAMESPACE = "http://www.tibco.com/be/schemas/config";        

        interface XNames {
            ExpandedName ADMIN = ExpandedName.makeName(NAMESPACE, "admin");
            ExpandedName ARCHIVES = ExpandedName.makeName(NAMESPACE, "archives");
            ExpandedName BEARCHIVE = ExpandedName.makeName(NAMESPACE, "BEArchive");
            ExpandedName BW = ExpandedName.makeName(NAMESPACE, "bebw");
            ExpandedName CHANNEL = ExpandedName.makeName(NAMESPACE, "channel");
            ExpandedName COMMON = ExpandedName.makeName(NAMESPACE, "common");
            ExpandedName CONTAINER = ExpandedName.makeName(NAMESPACE, "container");
            ExpandedName DESIGNTIME_EDITION = ExpandedName.makeName(NAMESPACE, "edition");
            ExpandedName DESIGNTIME_LICENSE = ExpandedName.makeName(NAMESPACE, "license");
            ExpandedName DESIGNTIME_VERSIONS = ExpandedName.makeName(NAMESPACE, "versions");
            ExpandedName DESTINATION = ExpandedName.makeName(NAMESPACE, "destination");
            ExpandedName DRIVERS = ExpandedName.makeName(NAMESPACE, "drivers");
            ExpandedName EDITION = ExpandedName.makeName(NAMESPACE, "edition");
            ExpandedName ENGINE_CONFIG = ExpandedName.makeName(NAMESPACE, "engine-config");
            ExpandedName FUNCTIONS = ExpandedName.makeName(NAMESPACE, "functions");
            ExpandedName INPUT_WORKERS = ExpandedName.makeName(NAMESPACE, PROPERTY_NAME_COLUMN_WORKERS);
            ExpandedName INPUT_QUEUE_SIZE = ExpandedName.makeName(NAMESPACE, PROPERTY_NAME_COLUMN_QUEUE_SIZE);
            ExpandedName INPUT_QUEUE_WEIGHT = ExpandedName.makeName(NAMESPACE, PROPERTY_NAME_COLUMN_QUEUE_WEIGHT);
            ExpandedName KERNEL = ExpandedName.makeName(NAMESPACE, "kernel");
            ExpandedName LISTEN_DESTINATION = ExpandedName.makeName(NAMESPACE, "listendestination");
            ExpandedName LISTEN_DESTINATIONS = ExpandedName.makeName(NAMESPACE, "listendestinations");
            ExpandedName OM = ExpandedName.makeName(NAMESPACE, "om-parameters");
            ExpandedName OM_BDB_CHECKPOINT_INTERVAL = ExpandedName.makeName(NAMESPACE, PROPERTY_NAME_OM_BDB_CHECKPOINT_INTERVAL);
            ExpandedName OM_BDB_CHECKPOINT_OPS_LIMIT = ExpandedName.makeName(NAMESPACE, PROPERTY_NAME_OM_BDB_CHECKPOINT_OPS_LIMIT);
            ExpandedName OM_BDB_DB_ENV_DIR = ExpandedName.makeName(NAMESPACE, PROPERTY_NAME_OM_BDB_DB_ENV_DIR);
            ExpandedName OM_BDB_DELETE_POLICY = ExpandedName.makeName(NAMESPACE, PROPERTY_NAME_OM_BDB_DELETE_POLICY);
            ExpandedName OM_BDB_NO_RECOVERY_REQD = ExpandedName.makeName(NAMESPACE, PROPERTY_NAME_OM_BDB_NO_RECOVERY_REQD);
            ExpandedName OM_BDB_NUM_BDB = ExpandedName.makeName(NAMESPACE, PROPERTY_NAME_OM_BDB_NUM_BDB);
            ExpandedName OM_BDB_PROPERTY_CACHE_SIZE = ExpandedName.makeName(NAMESPACE, PROPERTY_NAME_OM_BDB_PROPERTY_CACHE_SIZE);
            ExpandedName OM_CACHECONFIGFILE_FILE = ExpandedName.makeName(NAMESPACE, "cacheConfigFilePath");
            ExpandedName OM_CACHECONFIGFILE_RESOURCE = ExpandedName.makeName(NAMESPACE, "cacheConfigResourceUri");
            ExpandedName OM_CLASS = ExpandedName.makeName(NAMESPACE, PROPERTY_NAME_OM_CLASS);
            ExpandedName OM_ENABLE = ExpandedName.makeName(NAMESPACE, "omEnable");
            ExpandedName OM_ENABLED = ExpandedName.makeName(NAMESPACE, PROPERTY_NAME_OM_ENABLE);
            ExpandedName OM_TANGOSOL_AGENT_NAME = ExpandedName.makeName(NAMESPACE, PROPERTY_NAME_OM_TANGOSOL_AGENT_NAME);
            ExpandedName OM_TANGOSOL_RECOVERY_FACTORY = ExpandedName.makeName(NAMESPACE, PROPERTY_NAME_OM_TANGOSOL_RECOVERY_FACTORY);
            ExpandedName PALETTES = ExpandedName.makeName(NAMESPACE, "palettes");
            ExpandedName PREPROCESSOR = ExpandedName.makeName(NAMESPACE, "Preprocessor");
            ExpandedName PREPROCESSOR_URI = ExpandedName.makeName(NAMESPACE, "Preprocessor");
            ExpandedName QUEUE_SIZE = ExpandedName.makeName(NAMESPACE, "inputqueuesize");
            ExpandedName QUEUE_WEIGHT = ExpandedName.makeName(NAMESPACE, "inputqueueweight");
            ExpandedName RESOURCES = ExpandedName.makeName(NAMESPACE, "resources");
            ExpandedName RULESETS = ExpandedName.makeName(NAMESPACE, "rulesets");
            ExpandedName RULESETS_REF = ExpandedName.makeName(NAMESPACE, "RulesetRef");
            ExpandedName STATEMODELER = ExpandedName.makeName(NAMESPACE, "statemodeler");
            ExpandedName THREADING_MODEL = ExpandedName.makeName(NAMESPACE, "inputworkers");
            ExpandedName WORKERS = ExpandedName.makeName(NAMESPACE, "threading-model");
        }
    }


    interface DD {

        String BASE_XSD_FILENAME = "DeploymentDescriptorArchive.xsd";
        String CDD = "CDD";
        String FILENAME = "TIBCO.xml";
        String NAMESPACE = "http://www.tibco.com/xmlns/dd";
        String NAMESPACE_PREFIX = "dd";
        String PUID = "PUID";
        String TABS_XML_FILENAME = "Tabs.xml";
        String TABS_XSD_FILENAME = "Tabs.xsd";
        String RESOURCE_BUNDLE_TAB_NAME_PROPERTY_STEM = "tab.";
        String DIFF_NEW_ARCHIVE_NO_CONFIG_NECESSARY = "NEW_ARCHIVE_NO_CONFIG_NECESSARY";
        String DIFF_NEW_ARCHIVE_CONFIG_NECESSARY = "NEW_ARCHIVE_CONFIG_NECESSARY";
        String DIFF_REMOVED_ARCHIVE = "REMOVED_ARCHIVE";
        String DIFF_UPDATED_ARCHIVE_CONFIG_NECESSARY = "UPDATED_ARCHIVE_CONFIG_NECESSARY";
        String DIFF_UPDATED_ARCHIVE_DEPLOY_NECESSARY = "UPDATED_ARCHIVE_DEPLOY_NECESSARY";
        String DIFF_UPDATED_DD_DEPLOY_NECESSARY = "UPDATED_DD_DEPLOY_NECESSARY";
        String DIFF_UNCHANGED_ARCHIVE = "UNCHANGED_ARCHIVE";

        interface XNames {

            ExpandedName COMPONENT_SOFTWARE_NAME = ExpandedName.makeName(DD.NAMESPACE, "componentSoftwareName");
            ExpandedName COMPONENT_SOFTWARE_REFERENCE = ExpandedName.makeName(DD.NAMESPACE, "ComponentSoftwareReference");
            ExpandedName CONFIG_VERSION = ExpandedName.makeName(DD.NAMESPACE, "configVersion");
            ExpandedName CONFIGURABLE_AT_DEPLOYMENT = ExpandedName.makeName(DD.NAMESPACE, "configurableAtDeployment");
            ExpandedName CREATION_DATE = ExpandedName.makeName(DD.NAMESPACE, "creationDate");
            ExpandedName DEPLOYMENT_DESCRIPTOR_FACTORY = ExpandedName.makeName(DD.NAMESPACE, "DeploymentDescriptorFactory");
            ExpandedName DEPLOYMENT_DESCRIPTOR_FACTORY_CLASS_NAME = ExpandedName.makeName(DD.NAMESPACE, "deploymentDescriptorFactoryClassName");
            ExpandedName DEPLOYMENT_DESCRIPTOR_XSD_FILE_NAME = ExpandedName.makeName(DD.NAMESPACE, "deploymentDescriptorXsdFileName");
            ExpandedName DEPLOYMENT_DESCRIPTORS = ExpandedName.makeName(DD.NAMESPACE, "DeploymentDescriptors");
            ExpandedName DEPLOYMENT_SETTABLE = ExpandedName.makeName(DD.NAMESPACE, "deploymentSettable");
            ExpandedName DESCRIPTION = ExpandedName.makeName(DD.NAMESPACE, "description");
            ExpandedName DISABLE_CONFIGURE_AT_DEPLOYMENT = ExpandedName.makeName(DD.NAMESPACE, "disableConfigureAtDeployment");
            ExpandedName IS_APPLICATION_ARCHIVE = ExpandedName.makeName(DD.NAMESPACE, "isApplicationArchive");
            ExpandedName KEYWORD = ExpandedName.makeName(DD.NAMESPACE, "keyword");
            ExpandedName MAX_INCLUSIVE = ExpandedName.makeName(DD.NAMESPACE, "maxInclusive");
            ExpandedName MIN_INCLUSIVE = ExpandedName.makeName(DD.NAMESPACE, "minInclusive");
            ExpandedName MINIMUM_COMPONENT_SOFTWARE_VERSION = ExpandedName.makeName(DD.NAMESPACE, "minimumComponentSoftwareVersion");
            ExpandedName MINIMUM_COMPONENT_SOFTWARE_VERSION_NUMBER = ExpandedName.makeName(DD.NAMESPACE, "minimumComponentSoftwareVersionNumber");
            ExpandedName MINIMUM_TRA_VERSION = ExpandedName.makeName(DD.NAMESPACE, "minimumTRAVersion");
            ExpandedName MOD_TIME = ExpandedName.makeName(DD.NAMESPACE, "modTime");
            ExpandedName MODULES = ExpandedName.makeName(DD.NAMESPACE, "Modules");
            ExpandedName NAME = ExpandedName.makeName(DD.NAMESPACE, "name");
            ExpandedName NAME_VALUE_PAIR = ExpandedName.makeName(DD.NAMESPACE, "NameValuePair");
            ExpandedName NAME_VALUE_PAIR_BOOLEAN = ExpandedName.makeName(DD.NAMESPACE, "NameValuePairBoolean");
            ExpandedName NAME_VALUE_PAIR_INTEGER = ExpandedName.makeName(DD.NAMESPACE, "NameValuePairInteger");
            ExpandedName NAME_VALUE_PAIR_PASSWORD = ExpandedName.makeName(DD.NAMESPACE, "NameValuePairPassword");
            ExpandedName NAME_VALUE_PAIRS = ExpandedName.makeName(DD.NAMESPACE, "NameValuePairs");
            ExpandedName OWNER = ExpandedName.makeName(DD.NAMESPACE, "owner");
            ExpandedName PATH_NAME = ExpandedName.makeName(DD.NAMESPACE, "pathName");            
            ExpandedName REQUIRES_CONFIGURATION = ExpandedName.makeName(DD.NAMESPACE, "requiresConfiguration");
            ExpandedName SERVICE_SETTABLE = ExpandedName.makeName(DD.NAMESPACE, "serviceSettable");
            ExpandedName START_AS_ONE_OF = ExpandedName.makeName(DD.NAMESPACE, "StartAsOneOf");
            ExpandedName TYPE = ExpandedName.makeName(DD.NAMESPACE, "type");
            ExpandedName VALUE = ExpandedName.makeName(DD.NAMESPACE, "value");
            ExpandedName VERSION = ExpandedName.makeName(DD.NAMESPACE, "version");

        }

    }


    interface RepoTypes {

        String NAMESPACE = "http://www.tibco.com/xmlns/repo/types/2002";

        interface XNames {

            ExpandedName DEPLOYMENT_SETTABLE = ExpandedName.makeName(RepoTypes.NAMESPACE, "deploymentSettable");
            ExpandedName GLOBAL_VARIABLE = ExpandedName.makeName(RepoTypes.NAMESPACE, "globalVariable");
            ExpandedName GLOBAL_VARIABLES = ExpandedName.makeName(RepoTypes.NAMESPACE, "globalVariables");
            ExpandedName MOD_TIME = ExpandedName.makeName(RepoTypes.NAMESPACE, "modTime");
            ExpandedName NAME = ExpandedName.makeName(RepoTypes.NAMESPACE, "name");
            ExpandedName SERVICE_SETTABLE = ExpandedName.makeName(RepoTypes.NAMESPACE, "serviceSettable");
            ExpandedName TYPE = ExpandedName.makeName(RepoTypes.NAMESPACE, "type");
            ExpandedName VALUE = ExpandedName.makeName(RepoTypes.NAMESPACE, "value");
            ExpandedName CONSTRAINT = ExpandedName.makeName(RepoTypes.NAMESPACE, "constraint");

        }

    }


    interface RepoInstance {


        String BW_AND_ADAPTER_DD_NAME = "TIBCO BusinessWorks and Adapters Deployment Repository Instance";
        String NAMESPACE_PREFIX = "repoinstance";
        String XSD_FILE_NAME_STEM = "com/tibco/dd/repo/RepoInstance";
        String NAMESPACE = "http://www.tibco.com/xmlns/repoinstance";


        interface XNames {

            ExpandedName REPO_INSTANCE = ExpandedName.makeName(RepoInstance.NAMESPACE, "RepoInstance");
            ExpandedName SELECTED_REPO_INSTANCE_CONFIGURATION_NAME = ExpandedName.makeName(RepoInstance.NAMESPACE,
                    "selectedRepoInstanceConfigurationName");
            
        }
    }


}
