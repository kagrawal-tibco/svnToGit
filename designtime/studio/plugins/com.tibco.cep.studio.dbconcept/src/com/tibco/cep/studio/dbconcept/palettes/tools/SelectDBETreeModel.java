package com.tibco.cep.studio.dbconcept.palettes.tools;

import com.tibco.cep.studio.dbconcept.conceptgen.DBEntityCatalog;

/**
 * 
 * @author moshaikh
 *
 */
public class SelectDBETreeModel {

	DBEntityCatalog dbeCatalog;
	
	public SelectDBETreeModel(DBEntityCatalog dbeCatalog) {
		this.dbeCatalog = dbeCatalog;
	}

	public DBEntityCatalog getDbeCatalog() {
		return dbeCatalog;
	}

	public void setDbeCatalog(DBEntityCatalog dbeCatalog) {
		this.dbeCatalog = dbeCatalog;
	}

	public String getDataSourceType() {
		return dbeCatalog.getDatabaseType();
	}
}

