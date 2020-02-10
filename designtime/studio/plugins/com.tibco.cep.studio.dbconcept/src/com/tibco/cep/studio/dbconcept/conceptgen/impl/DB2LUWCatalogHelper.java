package com.tibco.cep.studio.dbconcept.conceptgen.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.tibco.cep.studio.dbconcept.ModulePlugin;
import com.tibco.cep.studio.dbconcept.conceptgen.IDB2GenericCatalogHelper;

/**
 * 
 * @author slokhand
 *
 */
public class DB2LUWCatalogHelper implements IDB2GenericCatalogHelper {

	/**
	 * Map containing alias to table mapping
	 */
	protected HashMap<?, ?> aliasToTableMapping;

	DB2LUWCatalogHelper() {
	}
	
	/**
	 * get db2's installation OS
	 */
	public String getDB2OSType() {
		return "DB2_OS_LUW";
	}

	@Override
	public HashMap<?, ?> getAliasToTableMapping(Connection conn) throws SQLException {
		return null;
		/*
		String sqlQuery = "select tabschema,tabname,base_tabschema,base_tabname from syscat.tables where "
				+ "type = ?";
		PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
		pstmt.setString(1, "A");
		ResultSet schRs = pstmt.executeQuery();
		while (schRs.next()) {
			String tableAliasSchemaName = schRs.getString("tabschema");
			if (tableAliasSchemaName != null) {
				tableAliasSchemaName = tableAliasSchemaName.trim();
			}
			String tableSchemaName = schRs.getString("base_tabschema");
			if (tableSchemaName != null) {
				tableSchemaName = tableSchemaName.trim();
			}
			String aliasName = schRs.getString("tabname");
			if (aliasName != null) {
				aliasName = aliasName.trim();
			}
			String tableName = schRs.getString("base_tabname");
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
	 * try to introspect using syscat.schemata catalog
	 * @param conn
	 * @param schemaOwner
	 * @param schemaList
	 * @return
	 * @throws SQLException
	 * 
	 * Works with db2 installed on LUW
	 * LUW - LINUX , UNIX and WINDOWS... 
	 */
	
	@Override
	public boolean getSchemas(Connection conn, String schemaOwner,
			List<Object> schemaList) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet schRs = null;

		try {
			StringBuilder sqlQuery = new StringBuilder();
			sqlQuery.append("select schemaname from syscat.schemata ");
			String schemaOwnerClause = "";
			if (schemaOwner != null && schemaOwner.trim().length() > 0) {
				schemaOwnerClause = " where definer = '" + schemaOwner
						+ "' and ";
			} else {
				schemaOwnerClause = " where ";
			}
			sqlQuery.append(schemaOwnerClause);
			sqlQuery
					.append("schemaname not like 'SYS%' and schemaname not in ('DB2QP','SESSION')");
			pstmt = conn.prepareStatement(sqlQuery.toString());
			schRs = pstmt.executeQuery();
			while (schRs.next()) {
				String schemaName = schRs.getString("schemaname");
				if (schemaName != null) {
					schemaList.add(schemaName.trim());
				}
			}
			schRs.close();
			pstmt.close();
			return true;
		} catch (Exception e) {
			ModulePlugin.log(e);
		} 
		return false;
	}

}
