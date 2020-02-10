package com.tibco.cep.studio.dbconcept.conceptgen.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PostgresCatalogHelper implements CatalogHelper {

	/**
	 * Gets the list of schema names based on the schema_owner for PostgreSQL
	 * 
	 * @param conn
	 * @param schemaOwner
	 * @param databaseName
	 * @return
	 * @throws SQLException
	 */
	public String[] getSchemas(Connection conn, String schemaOwner, String databaseName) throws SQLException {
		StringBuffer sqlQuery = new StringBuffer("select schema_name from " + databaseName
				+ ".information_schema.schemata where catalog_name='" + databaseName + "'");
		String schemaOwnerClause = "";
		if (schemaOwner != null && schemaOwner.trim().length() > 0)
			schemaOwnerClause = " and schema_owner = '" + schemaOwner + "' and ";
		else
			schemaOwnerClause = " and ";
		sqlQuery.append(schemaOwnerClause);
		sqlQuery.append("schema_name not like 'pg%' and schema_name != 'information_schema'");

		PreparedStatement pstmt = conn.prepareStatement(sqlQuery.toString());
		ResultSet schRs = pstmt.executeQuery();
		List<String> schemaList = new ArrayList<String>();
		while (schRs.next()) {
			String schemaName = schRs.getString("schema_name");
			if (schemaName != null)
				schemaList.add(schemaName);
		}
		return (String[]) schemaList.toArray(new String[] {});
	}

	/**
	 * Gets the Map of Table Alias to Parent Table for a DB
	 * 
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public HashMap<?, ?> getAliasToTableMapping(Connection conn) throws SQLException {
		return null;
	}

	/**
	 * Creates BE DB property for a particular DB Column
	 * 
	 * @param entityName
	 * @param columnName
	 * @param columnType
	 * @param length
	 * @param precision
	 * @param scale
	 * @param nullable
	 * @return
	 */
	public DBPropertyImpl createDBProperty(String entityName, String columnName, String columnType, int length,
			int precision, int scale, boolean nullable) {
		return new PostgresPropertyImpl(columnName, columnType, length, precision, scale, nullable);
	}

	/**
	 * Checks whether the DB object with the given name is a system object logic
	 * to filter system DB objects by names does not apply to this DB
	 * 
	 * @param objectName
	 * @return
	 */
	public boolean isSystemObject(String objectName) {
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
