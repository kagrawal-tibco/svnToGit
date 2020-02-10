package com.tibco.cep.studio.dbconcept.conceptgen.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Sybase Specific Implementation of the catalog helper
 * @author vjavere
 *
 */
public class SybaseCatalogHelper implements CatalogHelper {
	
	private static final String SYS_OBJECTS_PREFIX = "sys";
	
	/**
	 * Gets the list of schema names based on the schema_owner for a DB
	 * For Sybase DB the schemaOwner is the user schema in the DB. So just return the owner, 
	 * If the owner is not specified then return the lost of all owners i.e. creators from system catalog views
	 * @param conn
	 * @param schemaOwner
	 * @param databaseName
	 * @return
	 * @throws SQLException
	 */
	public String[] getSchemas(Connection conn,String schemaOwner,String databaseName) throws SQLException {
		
		if(schemaOwner != null && schemaOwner.trim().length() > 0)
			return new String [] {schemaOwner}; // database owner is the schema name in Sybase, so filter by owner name
		else {
			String sqlQuery = "select distinct creator from syscatalog";  
			Statement stmt = conn.createStatement();
		    ResultSet schRs = stmt.executeQuery(sqlQuery);
		    List<String> schemaList = new ArrayList<String>();
		    while(schRs.next()) {
		    	String schemaName = schRs.getString("creator");
		    	if(schemaName != null)
		    		schemaList.add(schemaName);
		    }
		    return (String [])schemaList.toArray(new String [] {} );
		}
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
		return new DefaultDBPropertyImpl(entityName,columnName, columnType, length, precision, scale, nullable);
	}
	
	/**
	 * Checks whether the DB object with the given name is a system object
   	 * @param objectName
	 * @return
	 */
	public boolean isSystemObject( String objectName ) {
		if(objectName.startsWith(SYS_OBJECTS_PREFIX)) 
			return true;
		else
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
