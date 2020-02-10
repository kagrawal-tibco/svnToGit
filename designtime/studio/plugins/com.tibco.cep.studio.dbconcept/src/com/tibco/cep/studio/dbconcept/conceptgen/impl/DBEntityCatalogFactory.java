package com.tibco.cep.studio.dbconcept.conceptgen.impl;

import com.tibco.cep.studio.dbconcept.conceptgen.DBDataSource;
import com.tibco.cep.studio.dbconcept.conceptgen.DBEntityCatalog;

public class DBEntityCatalogFactory {
	
	public static DBEntityCatalog createDBCatalog(DBDataSource ds) {
		
		String type = ds.getDBType();
		
		if (DBDataSource.ORACLE.equalsIgnoreCase(type)) {
			return new OracleCatalog(ds);
		} else if (DBDataSource.MSSQL.equalsIgnoreCase(type)) {
			return new SQLServerCatalog(ds);
		}
		
		return null;
	}
}
