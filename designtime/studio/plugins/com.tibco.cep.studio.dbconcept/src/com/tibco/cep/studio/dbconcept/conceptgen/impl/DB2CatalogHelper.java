package com.tibco.cep.studio.dbconcept.conceptgen.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.tibco.cep.studio.dbconcept.conceptgen.IDB2GenericCatalogHelper;

/**
 * DB2 Implementation of the catalog helper
 * @author vjavere
 *
 */
public class DB2CatalogHelper implements CatalogHelper {

	/**
	 * Map containing alias to table mapping
	 */
	protected HashMap<?, ?> aliasToTableMapping;
	
	// identifying the db2 installation on luw,z/os or iSeries
	private IDB2GenericCatalogHelper m_DB2GenericCatalogHelper = null;
	
	/**
	 * get the db2 catalog helper
	 * @return
	 */
	public IDB2GenericCatalogHelper getDB2GenericCatalogHelper() {
		return m_DB2GenericCatalogHelper;
	}

	/**
	 * Gets the list of schema names based on the schema_owner for DB2
	 * For DB2 the schema owner is the definer in the schemata catalog view
	 * The DB organization is DB tables are physical organized into schemas, definer is the owner of the schema, it is a DB user principal
	 * A particular user can own multiple schemas 
	 * @param conn
	 * @param schemaOwner
	 * @param databaseName
	 * @return
	 * @throws SQLException
	 */
	public String[] getSchemas(Connection conn,String schemaOwner,String databaseName) throws SQLException {
		List<Object> schemaList = new ArrayList<Object>();

		/**
		 * bit of a hack -- there is no cleaner way for now. 
		 * try to introspect syscat. 
		 * if that fails, introspect sysibm
		 * if that fails, introspect qsys2
		 */
		// Check for is db2 to installed on LUW - LINUX,UNIX,WINDOWS
		m_DB2GenericCatalogHelper = new DB2LUWCatalogHelper();
		if (!m_DB2GenericCatalogHelper.getSchemas(conn, schemaOwner,
				schemaList)) {
			// Check for is db2 to installed on Z/OS - MAINFRAME
			m_DB2GenericCatalogHelper = new DB2ZOSCatalogHelper();
			if (!m_DB2GenericCatalogHelper.getSchemas(conn, schemaOwner,
					schemaList)) {
				// Check for is db2 to installed on iSeries - MAINFRAME
				m_DB2GenericCatalogHelper = new DB2ISeriesCatalogHelper();
				if (!m_DB2GenericCatalogHelper.getSchemas(conn,
						schemaOwner, schemaList)) {
					throw new SQLException(
							"Error while reading database catalogs...");
				}
			}
		}
	    return (String [])schemaList.toArray(new String [] {} );
	}

	public HashMap<?, ?> getAliasToTableMapping(Connection conn) throws SQLException {
		 return aliasToTableMapping = null;
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
	public DBPropertyImpl createDBProperty(String entityName, String columnName, String columnType, int length, int precision, int scale, boolean nullable) {
		return new DB2DBPropertyImpl(columnName, columnType, length, precision, scale, nullable);
	}
	
	/**
	 * Checks whether the DB object with the given name is a system object
	 * @param objectName
	 * @return
	 */
	public boolean isSystemObject( String objectName ) {
		return false;       // logic to filter system DB objects by names does not apply to this DB 
	}

	@Override
	public String getNamespacePrefix(String dsName, String schemaName) {
		StringBuffer buf = new StringBuffer();
		
		if(dsName != null && !dsName.trim().isEmpty())
			buf.append(dsName + "/");
		
		if(schemaName != null && !schemaName.trim().isEmpty())
			buf.append(schemaName + "/");
		
		return buf.toString();
	}
}
