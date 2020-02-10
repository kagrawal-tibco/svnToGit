package com.tibco.cep.studio.dbconcept.conceptgen.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class MySQLCatalogHelper implements CatalogHelper {

	/**
	 * Gets the list of schema names based on the schema_owner for MySQL
     * For MySQL schema_name is the database name
	 * The DB tables are not organized in schemas  
	 * @param conn
	 * @param schemaOwner
	 * @param databaseName
	 * @return
	 * @throws SQLException
	 */
	public String[] getSchemas(Connection conn,String schemaOwner,String databaseName) throws SQLException {
	    return new String [] {databaseName};
	}
	
	/**
	 * Gets the Map of Table Alias to Parent Table for a DB
	 * Not Applicable to MySQL
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public HashMap<?, ?> getAliasToTableMapping(Connection conn) throws SQLException {
		return null;
	}

	/**
	 * Creates BE DB property for a particular DB Column
	 * @param entityName
	 * @param columnName
	 * @param columnType
	 * @param length
	 * @param precision
	 * @param scale
	 * @param nullable
	 * @return
	 */
	public DBPropertyImpl createDBProperty(String entityName, String columnName, String columnType, int length, int precision, int scale,	boolean nullable) {
		//common column datatypes for SQL Server and MySQL
		return new MySQLPropertyImpl(columnName, columnType, length, precision, scale, nullable);
	}
	
	/**
	 * Checks whether the DB object with the given name is a system object
     * logic to filter system DB objects by names does not apply to this DB
   	 * @param objectName
	 * @return
	 */
	public boolean isSystemObject( String objectName ) {
		return false;      
	}
	
	@Override
	public String getNamespacePrefix(String dsName, String schemaName) {
		StringBuffer buf = new StringBuffer();

		if (dsName != null && !dsName.trim().isEmpty())
			buf.append(dsName + "/");

		if (schemaName != null && !schemaName.trim().isEmpty())
			buf.append(schemaName + "/");
		return buf.toString();
	}
	
}
