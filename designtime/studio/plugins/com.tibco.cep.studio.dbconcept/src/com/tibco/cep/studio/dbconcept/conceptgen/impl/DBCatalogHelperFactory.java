package com.tibco.cep.studio.dbconcept.conceptgen.impl;

import com.tibco.cep.studio.dbconcept.conceptgen.DBDataSource;

/**
 * Catalog Factory to create DB Specific catalog helper
 * @author vjavere
 *
 */
public class DBCatalogHelperFactory {
	
	public static CatalogHelper createDBCatalogHelper(DBDataSource ds) {
		if(DBDataSource.ORACLE.equalsIgnoreCase(ds.getDBType())) {
			return new OracleCatalogHelper();
		} else if(DBDataSource.MSSQL.equalsIgnoreCase(ds.getDBType())) {
			return new SQLServerCatalogHelper();
		} else if(DBDataSource.MYSQL.equalsIgnoreCase(ds.getDBType())) {
			return new MySQLCatalogHelper();
		} else if(DBDataSource.DB2.equalsIgnoreCase(ds.getDBType())) {
			return new DB2CatalogHelper();
		} else if(DBDataSource.SYBASE.equalsIgnoreCase(ds.getDBType())) {
			return new SybaseCatalogHelper();
		} else if (DBDataSource.POSTGRES.equalsIgnoreCase(ds.getDBType())) {
			return new PostgresCatalogHelper();
		}
		return null;
	}
}
