package com.tibco.be.parser.codegen;

import com.tibco.be.util.BEProperties;

public interface CodeGenConstants {
	
	public enum CODE_GEN_JAVA_VERSIONS {
		JAVA_VERSION_1_5("1.5"),
		JAVA_VERSION_1_6("1.6"),
		JAVA_VERSION_1_7("1.7"),
		JAVA_VERSION_1_8("1.8");
		
		private String version;
		CODE_GEN_JAVA_VERSIONS(String s) {
			this.version = s;			
		}
	}
	
	public static final BEProperties properties = BEProperties.loadDefault();
	public static final String RULE_FILE_EXTENSION = properties.getString("be.codegen.ruleFileExtension", ".rul");//$NON-NLS-1$
	public static final String JAVA_FILE_EXTENSION = properties.getString("be.codegen.javaFileExtension", ".java");//$NON-NLS-1$
	public static final String JAVA_TARGET_VERSION = properties.getString("be.codegen.javaTargetVersion", "1.8");//$NON-NLS-1$
	public static final String charset = CGConstants.charset;
	
	public static final String BE_CODEGEN_PACKAGER = "packager";//$NON-NLS-1$
	public static final String BE_CODEGEN_ONTOLOGY = "ontology";//$NON-NLS-1$
    public static final String BE_CODEGEN_EVENT_DIR = "eventDir";//$NON-NLS-1$
    public static final String BE_CODEGEN_CONCEPT_DIR = "conceptDir";//$NON-NLS-1$
    public static final String BE_CODEGEN_SCORECARD_DIR = "scorecardDir";//$NON-NLS-1$
    public static final String BE_CODEGEN_JAVA_SRC_DIR = "javaSrcDir";//$NON-NLS-1$
    public static final String BE_CODEGEN_RULE_DIR = "ruleDir";//$NON-NLS-1$
    public static final String BE_CODEGEN_RULEFUNCTION_DIR = "ruleFunctionDir";//$NON-NLS-1$
	public static final String BE_CODEGEN_TIME_EVENT_DIR = "timeEventDir";//$NON-NLS-1$
	public static final String BE_CODEGEN_RULEFUNCTION_USAGE = "ruleFnUsages";//$NON-NLS-1$
	public static final String BE_CODEGEN_OVERSIZE_STRING_CONSTANTS = "oversizeStringConstants";//$NON-NLS-1$
	public static final String BE_CODEGEN_PROP_INFO_CACHE= "propInfoCache";//$NON-NLS-1$
	public static final String BE_CODEGEN_CLASSPATH = "ClassPath";//$NON-NLS-1$
	public static final String BE_CODEGEN_ERRORLIST = "errorList";//$NON-NLS-1$
	public static final String BE_CODEGEN_DEBUG = "isDebug";//$NON-NLS-1$
	public static final String BE_CODEGEN_PARALLEL_EXECUTOR_SERVICE = "parallelExecutorService";//$NON-NLS-1$
	public static final String BE_CODEGEN_PARALLEL_FUTURES = "parallelFutures";//$NON-NLS-1$
	public static final String BE_CODEGEN_PARALLEL_CODEGEN_RESULT = "parallelCodeGenResult"; //$NON-NLS-1$
	
	public static final String BE_CODEGEN_EXTENSION_POINT_MODULE = "com.tibco.cep.studio.core.moduleCodeGenerator";//$NON-NLS-1$
	public static final String BE_CODEGEN_EXTENSION_POINT_JAVA = "com.tibco.cep.studio.core.javaCodeGenerator";//$NON-NLS-1$
	public static final String BE_CODEGEN_EXTENSION_POINT_JAVA_RESOURCE = "com.tibco.cep.studio.core.javaResourceGenerator";//$NON-NLS-1$
	public static final String BE_CODEGEN_EXTENSION_POINT_EVENT = "com.tibco.cep.studio.core.eventCodeGenerator";//$NON-NLS-1$
	public static final String BE_CODEGEN_EXTENSION_POINT_CONCEPT = "com.tibco.cep.studio.core.conceptCodeGenerator";//$NON-NLS-1$
	public static final String BE_CODEGEN_EXTENSION_POINT_POJO_CONCEPT = "com.tibco.cep.studio.core.pojoConceptCodeGenerator";//$NON-NLS-1$
	public static final String BE_CODEGEN_EXTENSION_POINT_DB_CONCEPT = "com.tibco.cep.studio.core.dbConceptCodeGenerator";//$NON-NLS-1$
	public static final String BE_CODEGEN_EXTENSION_POINT_METRIC_CONCEPT = "com.tibco.cep.studio.core.metricConceptCodeGenerator";//$NON-NLS-1$
	public static final String BE_CODEGEN_EXTENSION_POINT_RULE_FUNCTION = "com.tibco.cep.studio.core.ruleFunctionCodeGenerator";//$NON-NLS-1$
	public static final String BE_CODEGEN_EXTENSION_POINT_RULE = "com.tibco.cep.studio.core.ruleCodeGenerator";//$NON-NLS-1$
	public static final String BE_CODEGEN_EXTENSION_POINT_DECISION_TABLE = "com.tibco.cep.studio.core.decisionTableCodeGenerator";//$NON-NLS-1$
	public static final String BE_CODEGEN_EXTENSION_POINT_ATTR_GENERATOR = "generator";
	// BE Packager
	public static final String BE_PROJECT = "BE.Project";//$NON-NLS-1$
	public static final String BE_PROJECT_REPO_PATH = "BE.Project.Repo.Path";//$NON-NLS-1$
	public static final String BE_ENTERPRISE_ARCHIVE = "BE.Ear";//$NON-NLS-1$
	//SAR Packager
	public static final String BE_CODEGEN_EXTENSION_POINT_SAR_PROVIDER="com.tibco.cep.studio.core.sharedArchiveResourceProvider";//$NON-NLS-1$
	public static final String BE_CODEGEN_EXTENSION_POINT_ATTR_SAR_PROVIDER="provider";//$NON-NLS-1$
	
	public static final String BE_SAR_PACKAGER_JAR_OUTPUT_STREAM = "SarPackager.jarOutputStream";//$NON-NLS-1$
	public static final String BE_SAR_ENTRY_LIST = "SarPackager.EntryList";//$NON-NLS-1$
	public static final String BE_PROJECT_NAME = "BE.Project.Name";//$NON-NLS-1$
	public static final String JAR_FILE_EXTENSION = ".jar"; //$NON-NLS-1$

	public static final String BE_CODEGEN_ENABLE_STATS_SCORECARD = "be.codegen.enableStatsScorecard";
}
