package com.tibco.cep.studio.dbconcept.conceptgen.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import com.tibco.cep.studio.dbconcept.ModulePlugin;
import com.tibco.cep.studio.dbconcept.conceptgen.IDB2GenericCatalogHelper;

public class DB2ZOSCatalogHelper implements IDB2GenericCatalogHelper {

	/**
	 * Map containing alias to table mapping
	 */
	protected HashMap<?, ?> aliasToTableMapping;

	DB2ZOSCatalogHelper() {
	}

	/**
	 * get db2's installation OS
	 */
	public String getDB2OSType() {
		return "DB2_OS_ZOS";
	}
	
	@Override
	public HashMap<?, ?> getAliasToTableMapping(Connection conn) throws SQLException {
		return null;
		/*
		String creatorName = conn.getMetaData().getUserName();

		String sqlQuery = "select CREATOR,TBNAME,REFTBNAME,REFTBCREATOR from SYSIBM.sysrels where creator = '"
				+ creatorName + "'";
		PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
		ResultSet schRs = pstmt.executeQuery();
		while (schRs.next()) {
			String tableAliasSchemaName = schRs.getString("CREATOR");
			if (tableAliasSchemaName != null) {
				tableAliasSchemaName = tableAliasSchemaName.trim();
			}
			String tableSchemaName = schRs.getString("REFTBCREATOR");
			if (tableSchemaName != null) {
				tableSchemaName = tableSchemaName.trim();
			}
			String aliasName = schRs.getString("TBNAME");
			if (aliasName != null) {
				aliasName = aliasName.trim();
			}
			String tableName = schRs.getString("REFTBNAME");
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
	 * try to introspect using sysibm.systables catalog
	 * @param conn
	 * @param schemaOwner
	 * @param schemaList
	 * @return
	 * @throws SQLException
	 * 
	 * Works with db2 installed on z/os 
	 */	
	@Override
	public boolean getSchemas(Connection conn, String schemaOwner,
			List<Object> schemaList) throws SQLException {

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt
					.executeQuery("select distinct creator from sysibm.systables where createdby = '"
							+ schemaOwner + "'");
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
