package com.tibco.cep.studio.dbconcept.conceptgen;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author slokhand
 *
 */

public interface IDB2GenericCatalogHelper {

	boolean getSchemas(Connection conn,String schemaOwner, List<Object> schemaList) throws SQLException;
	HashMap<?, ?> getAliasToTableMapping(Connection conn) throws SQLException;
	String getDB2OSType();

}
