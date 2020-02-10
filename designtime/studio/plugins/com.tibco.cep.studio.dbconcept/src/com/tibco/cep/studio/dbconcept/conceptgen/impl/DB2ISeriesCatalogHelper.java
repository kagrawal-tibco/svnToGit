package com.tibco.cep.studio.dbconcept.conceptgen.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import com.tibco.cep.studio.dbconcept.ModulePlugin;
import com.tibco.cep.studio.dbconcept.conceptgen.IDB2GenericCatalogHelper;

/**
 * 
 * @author slokhand
 *
 */
public class DB2ISeriesCatalogHelper implements IDB2GenericCatalogHelper {

	/**
	 * Map containing alias to table mapping
	 */
	protected HashMap<?, ?> aliasToTableMapping;
	
	
	public DB2ISeriesCatalogHelper() {
	}
	/**
	 * get db2's installation OS
	 */
	public String getDB2OSType() {
		return "DB2_OS_ISERIES";
	}
	
	@Override
	public HashMap<?, ?> getAliasToTableMapping(Connection conn) throws SQLException {
		return null;
		/*
		String creatorName = conn.getMetaData().getUserName();

		String sqlQuery = "select TABLE_NAME,TABLE_SCHEMA,BASE_TABLE_NAME,BASE_TABLE_SCHEMA from QSYS2.SYSTABLES where "
				+ "TABLE_TYPE = ?";

		PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
		pstmt.setString(1, "A");
		ResultSet schRs = pstmt.executeQuery();
		while (schRs.next()) {
			String tableAliasSchemaName = schRs.getString("TABLE_SCHEMA");
			if (tableAliasSchemaName != null) {
				tableAliasSchemaName = tableAliasSchemaName.trim();
			}
			String tableSchemaName = schRs.getString("BASE_TABLE_SCHEMA");
			if (tableSchemaName != null) {
				tableSchemaName = tableSchemaName.trim();
			}
			String aliasName = schRs.getString("TABLE_NAME");
			if (aliasName != null) {
				aliasName = aliasName.trim();
			}
			String tableName = schRs.getString("BASE_TABLE_NAME");
			if (tableName != null) {
				tableName = tableName.trim();
			}
			if (aliasToTableMapping == null)
				aliasToTableMapping = new HashMap();
			aliasToTableMapping.put(tableAliasSchemaName + "." + aliasName,
					tableSchemaName + "." + tableName);

		}
		return aliasToTableMapping;
		*/
	}

	/** 
	 * try to introspect using QSYS2.SYSTABLES catalog
	 * @param conn
	 * @param schemaOwner
	 * @param schemaList
	 * @return
	 * @throws SQLException
	 * 
	 * Works with db2 installed on iSeries
	 */	
	@Override
	public boolean getSchemas(Connection conn, String schemaOwner,
			List<Object> schemaList) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			String sqlQuery = "select distinct TABLE_SCHEMA from QSYS2.SYSTABLES where TABLE_OWNER ='"
					+ schemaOwner + "'";

			rs = stmt.executeQuery(sqlQuery);
			while (rs.next()) {
				schemaList.add(rs.getObject(1));
			}
			rs.close();
			stmt.close();
			return true;
		} catch (Exception e) {
			ModulePlugin.log(e);
		} 
		return false;
	}



}
