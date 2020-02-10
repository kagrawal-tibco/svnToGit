package com.tibco.cep.studio.dbconcept.conceptgen.impl;

import com.tibco.be.jdbcstore.ssl.JdbcSSLConnectionInfo;
import com.tibco.cep.studio.dbconcept.conceptgen.DBDataSource;

public class DBDataSourceImpl implements DBDataSource {

    protected String dsName;
    protected String jdbcDriver;
    protected String connectionurl;
    protected String username;
    protected String password;
    protected String schemaOwner;
    protected String jdbcResourceURI;
    protected int loginTimeout = 60;
    protected int retryCount = 3;

    protected String dbType;

    private JdbcSSLConnectionInfo sslConnectionInfo;

    public String getConnectionUrl() {
        return connectionurl;
    }

    public String getName() {
        return dsName;
    }

    public String getDriver() {
        return jdbcDriver;
    }

    public String getPassword() {
        return password;
    }

    public String getSchemaOwner(){
        return schemaOwner;
    }

    public String getUserId() {
        return username;
    }

    public String getDBType(){
        return dbType;
    }

    public void setConnectionUrl(String connectionurl) {
        this.connectionurl = connectionurl;
    }

    public void setDriver(String driver) {
        // Support for Oracle-11g
        if (driver.startsWith("oracle.jdbc.driver")) {
            this.jdbcDriver = driver.replace("oracle.jdbc.driver", "oracle.jdbc");
            System.err.println("Database datasource is configured using deprecated package 'oracle.jdbc.driver'");
        } else {
            this.jdbcDriver = driver;
        }
        this.dbType = getDBType(driver);
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSchemaOwner(String schemaOwner){
        this.schemaOwner = schemaOwner;
    }

    public String getJdbcResourceURI() {
        return jdbcResourceURI;
    }

    public void setJdbcResourceURI(String jdbcResourceURI) {
        this.jdbcResourceURI = jdbcResourceURI;
    }

    public void setLoginTimeout(int timeout) {
        this.loginTimeout = timeout;
    }

    public void setRetryCount(int count) {
        this.retryCount = count;
    }

	public static String getDBType(String driver) {

		switch (driver) {
		case ORACLE_THIN_DRIVER_v11:
		case ORACLE_THIN_DRIVER:
		case ORACLE_TIBCO_DRIVER:
			return ORACLE;
		case SQLSERVER_TIBCO_DRIVER:
		case MSSQL_WEBLOGIC_DRIVER:
		case MSSQL_MICROSOFT_DRIVER:
		case MSSQL_JTDS_DRIVER:
			return MSSQL;
		case TIBCO_SYBASE_DRIVER:
		case SYBASE_JCONN2_DRIVER:
		case SYBASE_JCONN3_DRIVER:
		case SYBASE_JCONN4_DRIVER:
			return SYBASE;
		case IBM_DB2_DRIVER:
		case TIBCO_DB2_DRIVER:
		case IBM_DB2_ISERIES:
			return DB2;
		case MYSQL_DRIVER:
			return MYSQL;
		case POSTGRES_DRIVER:
			return POSTGRES;
		default:
			return null;
		}

	}

    public int getLoginTimeout() {
        return loginTimeout;
    }

    public int getRetryCount() {
        return retryCount;
    }
    
    private static String getDBNamePart(String connURL) {
		String dbNamePart = "";
		if (connURL.lastIndexOf("/") > -1 && connURL.contains("?")) {
			// check if parameters are passed in the connURL
			dbNamePart = connURL.substring(connURL.lastIndexOf("/") + 1,
					connURL.length());
			dbNamePart = dbNamePart.substring(0, dbNamePart.indexOf("?"));
		} else if (connURL.lastIndexOf("/") > -1) {
			dbNamePart = connURL.substring(connURL.lastIndexOf("/") + 1,
					connURL.length());
		} else {
			throw new RuntimeException("Invalid database url " + connURL);
		}
		return dbNamePart;
	}

	// get databasename part from conn url
	public static String resolveDatabaseName(String driver, String connURL) {
		String dbNamePart = "";
		switch (driver) {
		case SQLSERVER_TIBCO_DRIVER:
		case MSSQL_MICROSOFT_DRIVER:
			dbNamePart = getMsSqlDBNamePart(connURL);
			break;
		case TIBCO_SYBASE_DRIVER:
			dbNamePart = connURL.substring(connURL.indexOf("databaseName=")).substring("databaseName=".length());
			break;
		case MSSQL_WEBLOGIC_DRIVER:
			dbNamePart = connURL.substring("jdbc:weblogic:mssqlserver4:".length(), connURL.indexOf("@"));
			break;
		case IBM_DB2_DRIVER:
		case MYSQL_DRIVER:
		case POSTGRES_DRIVER:
		case SYBASE_JCONN2_DRIVER:
		case SYBASE_JCONN3_DRIVER:
		case SYBASE_JCONN4_DRIVER:
		default:
			dbNamePart = getDBNamePart(connURL);
			break;
		}
		return dbNamePart;
	}
    
    private static String getMsSqlDBNamePart(String connURL) {
        int j = -1;
        String dbNamePart = null;
        if( (j = connURL.indexOf("databaseName=")) > -1) {
            dbNamePart = connURL.substring(j + "databaseName=".length());
        } else if ((j = connURL.indexOf("databasename=")) > -1) {
            dbNamePart = connURL.substring(j + "databasename=".length());
        } else if ((j = connURL.indexOf("database=")) > -1) {
            dbNamePart = connURL.substring(j + "database=".length());
        }
        int k = -1;
        if (dbNamePart != null && (k = dbNamePart.indexOf(";")) > -1) {
        	dbNamePart = dbNamePart.substring(0,k);
        }

        return dbNamePart;
    }

    public static boolean isSQLServer(String driver){
        String dbType = DBDataSourceImpl.getDBType(driver);
        return DBDataSource.MSSQL.equals(dbType);
    }

    public static boolean isDB2Driver(String driver){
        String dbType = DBDataSourceImpl.getDBType(driver);
        return DBDataSource.DB2.equals(dbType);
    }

    public static boolean isMySQLDriver(String driver){
        String dbType = DBDataSourceImpl.getDBType(driver);
        return DBDataSource.MYSQL.equals(dbType);
    }
    
    public static boolean isPostgresDriver(String driver){
        String dbType = DBDataSourceImpl.getDBType(driver);
        return DBDataSource.POSTGRES.equals(dbType);
    }

    public static boolean isOracleDriver(String driver){
        String dbType = DBDataSourceImpl.getDBType(driver);
        return DBDataSource.ORACLE.equals(dbType);
    }

    public static boolean isSybaseDriver(String driver){
        String dbType = DBDataSourceImpl.getDBType(driver);
        return DBDataSource.SYBASE.equals(dbType);
    }

	@Override
	public JdbcSSLConnectionInfo getSSLConnectionInfo() {
		return this.sslConnectionInfo;
	}
	
	public void setSSLConnectionInfo(JdbcSSLConnectionInfo sslConnectionInfo) {
    	this.sslConnectionInfo = sslConnectionInfo;
    }
}
