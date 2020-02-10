package com.tibco.cep.studio.dbconcept.conceptgen.impl;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;


/**
 * Interface which defines the helper methods for DB Specific Catalog APIs 
 * @author vjavere
 *
 */
public interface CatalogHelper {
	
	/**
	 * Gets the list of schema names based on the schema_owner for a DB 
	 * @param conn
	 * @param schemaOwner
	 * @param databaseName
	 * @return
	 * @throws SQLException
	 */
	public String [] getSchemas(Connection conn,String schemaOwner,String databaseName) throws SQLException;
	
	/**
	 * Gets the Map of Table Alias to Parent Table for a DB
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public HashMap<?, ?> getAliasToTableMapping(Connection conn) throws SQLException;
	
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
	public DBPropertyImpl createDBProperty(String entityName, String columnName, String columnType, int length, int precision, int scale, boolean nullable);

	/**
	 * Checks whether the DB object with the given name is a system object
	 * This method is used when the db objects are not differentiated from tables and 
	 * views in the Database
	 * @param objectName
	 * @return
	 */
	public boolean isSystemObject( String objectName );
	
	/**
	 * return the "namespace prefix" that should be used while generating the actual namespace
	 * for example, for oracle, where schemaname=hr (no concept of a database), 
	 * this will return hr and for sqlserver where 
	 * databasename=Master and schemaname=dbo, it will return Master/dbo
	 */
	public String getNamespacePrefix(String dsName, String schemaName);
}
