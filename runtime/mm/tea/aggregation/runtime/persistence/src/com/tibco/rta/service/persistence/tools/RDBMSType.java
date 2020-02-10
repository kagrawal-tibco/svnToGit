package com.tibco.rta.service.persistence.tools;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * RDBMSType abstracts info about the different rdbms types that backing store
 * supports, and provides a look up mechanism to get the correct driver for a
 * rdbms. See SqlType for info on what that driver provides.
 */
public class RDBMSType {

	public static final int ANSI = 0;
	public static final int ORACLE = 1;
	public static final int SYBASE = 2;
	public static final int DB2 = 3;
	public static final int MYSQL = 4;
	public static final int SQLSERVER = 5;
	public static final int POSTGRESQL = 6;
	public static final int H2 = 7;
	
	public static final String RDBMS_TYPE_NAME_ANSI = "ansi";
	public static final String RDBMS_TYPE_NAME_ORACLE = "oracle";
	public static final String RDBMS_TYPE_NAME_SYBASE = "synase";
	public static final String RDBMS_TYPE_NAME_DB2 = "db2";
	public static final String RDBMS_TYPE_NAME_MYSQL = "mysql";
	public static final String RDBMS_TYPE_NAME_SQLSERVER = "sqlserver";
	public static final String RDBMS_TYPE_NAME_POSTGRESQL = "postgresql";
	public static final String RDBMS_TYPE_NAME_H2 = "H2";

	private static final String DEFAULT_RDBMS_TYPE_NAME = RDBMS_TYPE_NAME_ANSI;

	private static Set<RDBMSTypeEntry> sEntries = new HashSet<RDBMSTypeEntry>();

	public static int sRuntimeType;
	public static String sRuntimeTypeName;
	public static SqlType sSqlType;
	public static SqlType sSqlTypeAnsi;
	public static Map sSystemTableMap;
	public static Map sDBKeywordMap;
	public static boolean sUseAnsiType;

	static {
		// Initialize drivers for supported rdbms.
		sSqlTypeAnsi = new SqlTypeAnsi();
		sEntries.add(new RDBMSTypeEntry(ANSI, RDBMS_TYPE_NAME_ANSI, sSqlTypeAnsi));
		sEntries.add(new RDBMSTypeEntry(ORACLE, RDBMS_TYPE_NAME_ORACLE, new SqlTypeOracle()));
		sEntries.add(new RDBMSTypeEntry(POSTGRESQL, RDBMS_TYPE_NAME_POSTGRESQL, new SqlTypePostgreSQL()));
		sEntries.add(new RDBMSTypeEntry(H2, RDBMS_TYPE_NAME_H2, new SqlTypeH2()));
		sEntries.add(new RDBMSTypeEntry(MYSQL, RDBMS_TYPE_NAME_MYSQL, new SqlTypeMySql()));
		sEntries.add(new RDBMSTypeEntry(SQLSERVER, RDBMS_TYPE_NAME_SQLSERVER, new SqlTypeSqlServer()));
		sEntries.add(new RDBMSTypeEntry(DB2, RDBMS_TYPE_NAME_DB2, new SqlTypeDB2()));
		// sEntries.add(new RDBMSTypeEntry(SYBASE, RDBMS_TYPE_NAME_SYBASE,
		// new SqlTypeSybase()));		 
		// sEntries.add(new RDBMSTypeEntry(SQLSERVER, RDBMS_TYPE_NAME_SQLSERVER,
		// new SqlTypeSqlServer()));
		// sEntries.add(new RDBMSTypeEntry(MYSQL, RDBMS_TYPE_NAME_MYSQL,
		// new SqlTypeMySQL()));

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
			throw new RuntimeException("Invalid database type '" + dbType + "'.");
//			entry = getEntryByName("Oracle");
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

	/**
	 * Given the rdbms type string, return the SqlType instance for that rdbms.
	 * 
	 * @param dbType
	 * @return
	 */
	public static SqlType getSqlType(String dbType) {
		RDBMSTypeEntry entry = getEntryByName(dbType);
		return (entry != null) ? entry.sqlType : null;
	}

	/**
	 * Given the rdbms type number, return the SqlType instance for that rdbms.
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
		for (RDBMSTypeEntry entry : sEntries) {
			if (entry.name.equalsIgnoreCase(name))
				return entry;
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
			if (entry.type == type)
				return entry;
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

		@Override
		public String toString() {
			return "RDBMSTypeEntry [type=" + type + ", name=" + name + ", sqlType=" + sqlType + "]";
		}
	}
}
