package com.tibco.cep.studio.dbconcept.conceptgen;

import  com.tibco.be.jdbcstore.ssl.JdbcSSLConnectionInfo;

/**
 * 
 * @author schelwa
 * 
 * An interface to describe the data sources used by the impl. this information
 * is used by framework to create jdbc connection related meta information in the generated classes
 * and in the BW project. 
 *
 */
public interface DBDataSource {
	
	public static final String ORACLE = "ORACLE";
	public static final String MSSQL = "MSSQL";
	public static final String MYSQL = "MYSQL";
	public static final String POSTGRES = "POSTGRES";
	public static final String SYBASE = "SYBASE";
	public static final String DB2 = "DB2";
	public static final String DEFAULT_OWNER_FOR_SQLSERVER = "dbo";
	public static final String DEFAULT_OWNER_FOR_SYBASE = "dbo";
	
	public static final String ORACLE_THIN_DRIVER = "oracle.jdbc.driver.OracleDriver";
	public static final String ORACLE_THIN_DRIVER_v11 = "oracle.jdbc.OracleDriver";
	public static final String ORACLE_TIBCO_DRIVER = "tibcosoftwareinc.jdbc.oracle.OracleDriver";
//	public static final String ORACLE_TIBCO_RAC_DRIVER = "tibcosoftwareinc.jdbc.oracle.OracleDriver";
//	public static final String ORACLE_OCI_DRIVER = "oracle.jdbc.driver.OracleDriver";
//	public static final String ORACLE_OCI_DRIVER_v11 = "oracle.jdbc.OracleDriver";
//	public static final String ORACLE_JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
//	public static final String ORACLE_JDBC_DRIVER_v11 = "oracle.jdbc.OracleDriver";
//	
	
	public static final String SQLSERVER_TIBCO_DRIVER = "tibcosoftwareinc.jdbc.sqlserver.SQLServerDriver";
	public static final String MSSQL_WEBLOGIC_DRIVER = "weblogic.jdbc.mssqlserver4.Driver";
	public static final String MSSQL_MICROSOFT_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static final String MSSQL_JTDS_DRIVER = "net.sourceforge.jtds.jdbc.Driver";
	
	public static final String SYBASE_JCONN2_DRIVER = "com.sybase.jdbc2.jdbc.SybDriver";
	public static final String SYBASE_JCONN3_DRIVER = "com.sybase.jdbc3.jdbc.SybDriver";
	public static final String SYBASE_JCONN4_DRIVER = "com.sybase.jdbc4.jdbc.SybDriver";
	public static final String TIBCO_SYBASE_DRIVER = "tibcosoftwareinc.jdbc.sybase.SybaseDriver";
	
	public static final String IBM_DB2_DRIVER = "com.ibm.db2.jcc.DB2Driver";
	public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	public static final String POSTGRES_DRIVER = "org.postgresql.Driver";
	public static final String TIBCO_DB2_DRIVER = "tibcosoftwareinc.jdbc.db2.DB2Driver";
	public static final String IBM_DB2_ISERIES = "com.ibm.as400.access.AS400JDBCDriver";
	
	/**
	 * 
	 * @return Logical name for a data source
	 */
	public String getName();
	
	/**
	 * 
	 * @return User id to use for DB connection to this datasource
	 */
	public String getUserId();
	
	/**
	 * 
	 * @return Password for the connection to this datasource
	 */
	public String getPassword();
	
	/**
	 * 
	 * @return Connectoin URL for this datasource
	 */
	public String getConnectionUrl();
	
	/**
	 * 
	 * @return Database driver to use for this datasource
	 */
	public String getDriver();
	
	/**
	 * 
	 * @return Database driver to use for this datasource
	 */
	public String getSchemaOwner();
	
	/**
	 * 
	 * 	@return Database driver to use for this datasource
	 */
	public String getDBType();
	
	/**
	 * 
	 * @return Database JDBC URI
	 */
	public String getJdbcResourceURI();
	
	
	/**
	 * 
	 * @return Time to wait for database login
	 */
	public int getRetryCount();

	/**
	 * 
	 * @return SSL configurations
	 */
	public JdbcSSLConnectionInfo getSSLConnectionInfo();
}
