package com.tibco.be.migration;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 14, 2008
 * Time: 1:48:07 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BEMigrationConstants {

    public static final String DBCOPY = "copy";
    
    public static final String EXPORT = "export";
    public static final String IMPORT = "import";

    public static final String DATABASE = "DB";
    public static final String BDB = "BDB";

    public static final String PROP_INPUT_URL = "be.migration.input.path";
    public static final String PROP_OUTPUT_URL = "be.migration.output.path";
    public static final String PROP_PROJECT_FILE_PATH = "be.migration.project.path";
    public static final String PROP_MIGRATION_MODE = "be.migration.mode";
    public static final String PROP_EXPIMP_METHOD = "be.migration.method";
    public static final String PROP_CACHE_CONFIG = "tangosol.coherence.cacheconfig";
    public static final String PROP_DB_CONN_STR = "be.migration.db.connection";
    public static final String PROP_BAR_NAME = "be.migration.bar.name";
    public static final String PROP_PARTITION_ID = "be.migration.partition.id";
    public static final String USING_SYSTEM_PROPERTIES_KEY = "be.migration.useSystemProperties";
    public static final String PROP_USING_MULTITHREADS = "be.migration.import.multithreads";
    public static final String PROP_IMPORT_THREADS = "be.migration.import.threads";
    public static final String PROP_DB_POOLSIZE = "be.migration.oracle.poolSize";
    public static final String PROP_DB_RETRYINTERVAL = "be.migration.oracle.retryInterval";

    int IMPEXP_METHOD_BDB = 1;
    int IMPEXP_METHOD_DB = 2;
}