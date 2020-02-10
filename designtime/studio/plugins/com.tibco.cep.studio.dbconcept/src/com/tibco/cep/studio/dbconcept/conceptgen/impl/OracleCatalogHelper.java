package com.tibco.cep.studio.dbconcept.conceptgen.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Oracle Implementation of the catalog helper
 * @author vjavere
 *
 */
public class OracleCatalogHelper implements CatalogHelper {

	/**
	 * Gets the list of schema names based on the schema_owner for a DB
	 * For Oracle DB the schemaOwner is the user schema in the DB. So just return the owner 
	 * @param conn
	 * @param schemaOwner
	 * @param databaseName
	 * @return
	 * @throws SQLException
	 */
	public String[] getSchemas(Connection conn,String schemaOwner,String databaseName) throws SQLException {
		return new String [] {schemaOwner.toUpperCase()}; // database owner is the schema in Oracle, so filter by owner name
	}

	/**
	 * Gets the Map of Table Alias to Parent Table for a DB
	 * Not Applicable to Oracle
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
	public DBPropertyImpl createDBProperty(String entityName,String columnName, String columnType, int length, int precision, int scale,	boolean nullable) {
		return new OracleDBPropertyImpl(columnName, columnType, length, precision, scale, nullable);
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
		return schemaName + "/";
	}
	
}
