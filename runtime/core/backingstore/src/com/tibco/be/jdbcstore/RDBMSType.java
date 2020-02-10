package com.tibco.be.jdbcstore;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * RDBMSType abstracts info about the different rdbms types that backing store
 * supports, and provides a look up mechanism to get the correct
 * driver for a rdbms. See SqlType for info on what that driver provides.
 */
public class RDBMSType {

    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(JdbcStore.class);

    public static final int    ANSI = 0;
    public static final int    ORACLE = 1;
    public static final int    SYBASE = 2;
    public static final int    DB2 = 3;
    public static final int    MYSQL = 4;
    public static final int    SQLSERVER = 5;
    public static final int    POSTGRES = 6;

    public static final String RDBMS_TYPE_NAME_ANSI="Ansi";
    public static final String RDBMS_TYPE_NAME_ORACLE="Oracle";
    public static final String RDBMS_TYPE_NAME_SYBASE="Sybase";
    public static final String RDBMS_TYPE_NAME_DB2="DB2";
    public static final String RDBMS_TYPE_NAME_MYSQL="MySQL";
    public static final String RDBMS_TYPE_NAME_SQLSERVER="SqlServer";
    public static final String RDBMS_TYPE_NAME_POSTGRES="PostgreSQL";

    private static final String DEFAULT_RDBMS_TYPE_NAME=RDBMS_TYPE_NAME_ANSI;

    private static Set<RDBMSTypeEntry> sEntries =
        new HashSet<RDBMSTypeEntry>();

    public static int           sRuntimeType;
    public static String        sRuntimeTypeName;
    public static SqlType       sSqlType;
    public static SqlType       sSqlTypeAnsi;
    public static Map           sSystemTableMap;
    public static Map           sDBKeywordMap;
    public static boolean       sUseAnsiType;
    public static boolean       sHistIdNotNull = false; // current used for allowing null in history value of id type
    public static boolean       sChildIdNotNull = false; // current used for allowing null in contained child array of id type
    public static boolean		sExpandStringMaxSize = false;
	
    static {
        // Initialize drivers for supported rdbms.
        sSqlTypeAnsi = new SqlTypeAnsi();
        sEntries.add(new RDBMSTypeEntry(ANSI, RDBMS_TYPE_NAME_ANSI,
            sSqlTypeAnsi));
        sEntries.add(new RDBMSTypeEntry(ORACLE, RDBMS_TYPE_NAME_ORACLE,
            new SqlTypeOracle()));
        sEntries.add(new RDBMSTypeEntry(SYBASE, RDBMS_TYPE_NAME_SYBASE,
            new SqlTypeSybase()));
        sEntries.add(new RDBMSTypeEntry(DB2, RDBMS_TYPE_NAME_DB2,
            new SqlTypeDB2()));
        sEntries.add(new RDBMSTypeEntry(SQLSERVER, RDBMS_TYPE_NAME_SQLSERVER,
            new SqlTypeSqlServer()));
        sEntries.add(new RDBMSTypeEntry(MYSQL, RDBMS_TYPE_NAME_MYSQL,
            new SqlTypeMySQL()));
        sEntries.add(new RDBMSTypeEntry(POSTGRES, RDBMS_TYPE_NAME_POSTGRES,
            new SqlTypePostgres()));

        // Set the default rdbms info.
        String dbType = DEFAULT_RDBMS_TYPE_NAME; 
        RDBMSTypeEntry entry = getEntryByName(dbType);

        sRuntimeType = entry.type;
        sSqlType = entry.sqlType;
        sRuntimeTypeName = entry.name;
    }

    public static void setDefaultSqlType(String dbType, boolean useAnsi) {
        RDBMSTypeEntry entry = getEntryByName(dbType);
        if (entry == null) {
            logger.log(Level.ERROR, "Cannot set default sql type to '%s' - defaulting to Oracle", dbType);
            entry = getEntryByName("Oracle");
        }
        sRuntimeType = entry.type;
        sSqlType = entry.sqlType;
        sRuntimeTypeName = entry.name;
        sUseAnsiType = useAnsi;
        // DB2 does not fully support ANSI "e.g. no 'char varying'"
        if (sRuntimeType == RDBMSType.DB2) {
            sUseAnsiType = false;
        }
    }

    public static void setSystemTableMap(Map map) {
        sSystemTableMap = map;
    }

    public static void setDBKeywordMap(Map map) {
        sDBKeywordMap = map;
    }

    public static void setHistIdNotNull(boolean histIdNotNull) {
        sHistIdNotNull = histIdNotNull;
    }
    
    public static boolean getHistIdNotNull() {
        return sHistIdNotNull;
    }
    
    public static void setChildIdNotNull(boolean childIdNotNull) {
    	sChildIdNotNull = childIdNotNull;
    }
    
    public static boolean getChildIdNotNull() {
        return sChildIdNotNull;
    }
    
    public static void setExpandMaxStringSize(boolean expandStringMaxSize) {
    	sExpandStringMaxSize = expandStringMaxSize;
    }
    
    public static boolean getExpandMaxStringSize() {
    	return sExpandStringMaxSize;
    }
    
    /**
     * Given the rdbms type string, return the SqlType instance
     * for that rdbms.
     *
     * @param dbType
     * @return
     */
    public static SqlType getSqlType(String dbType) {
        RDBMSTypeEntry entry = getEntryByName(dbType);
        return (entry != null) ? entry.sqlType : null;
    }

    /**
     * Given the rdbms type number, return the SqlType instance
     * for that rdbms.
     *
     * @param dbType
     * @return
     */
    public static SqlType getSqlType(int dbType) {
        RDBMSTypeEntry entry = getEntryByNumber(dbType);
        return (entry != null) ? entry.sqlType : null;
    }

    /**
     * Internal. Get rdbms type entry for given rdbms type name.
     *
     * @param name
     * @return
     */
    private static RDBMSTypeEntry getEntryByName(String name) {
        String type = name.replaceAll(" ", "");
        for (RDBMSTypeEntry entry : sEntries) {
            if (entry.name.equalsIgnoreCase(type)) {
                return entry;
            }
        }
        return null;
    }

    /**
     * Internal. Get rdbms type entry for given rdbms type number..
     *
     * @param type
     * @return
     */
    private static RDBMSTypeEntry getEntryByNumber(int type) {
        for (RDBMSTypeEntry entry : sEntries) {
            if (entry.type == type) {
                return entry;
            }
        }
        return null;
    }

    /**
     * Simple encapsulation of info about a specific rdbms.
     */
    static class RDBMSTypeEntry {
        public int type;
        public String name;
        public SqlType sqlType;

        /**
         * Create instance of entry for given rdbms type.
         *
         * @param type
         * @param name
         * @param sqlType
         */
        public RDBMSTypeEntry(int type, String name, SqlType sqlType) {
            this.type = type;
            this.name = name;
            this.sqlType = sqlType;
        }
    }
}
