package com.tibco.cep.studio.dbconcept.palettes.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.tibco.cep.studio.dbconcept.conceptgen.DBEntity;
import com.tibco.cep.studio.dbconcept.conceptgen.DBEntityCatalog;
import com.tibco.cep.studio.dbconcept.conceptgen.DBSchema;

/**
 * 
 * @author moshaikh
 * 
 */
public class ConceptDBETableModel {

	private DBEntityCatalog dbeCatalog;
	private List<DBEntity> selectedEntities;
	private Collection<DBSchema> selectedSchemas;

	public ConceptDBETableModel(DBEntityCatalog dbeCatalog,
			Map<String, DBEntity> selectedEntities) {
		this.dbeCatalog = dbeCatalog;

		this.selectedEntities = new ArrayList<DBEntity>();
		this.selectedEntities.addAll(selectedEntities.values());

		this.selectedSchemas = new HashSet<DBSchema>();
		for (DBEntity entity : this.selectedEntities) {
			DBSchema dbs = dbeCatalog.getDBSchema(entity.getSchema());
			if (dbs != null)
				this.selectedSchemas.add(dbs);
		}
	}

	public DBEntityCatalog getDbeCatalog() {
		return dbeCatalog;
	}

	public List<DBEntity> getSelectedEntities() {
		return selectedEntities;
	}

	public Collection<DBSchema> getSelectedSchemas() {
		return selectedSchemas;
	}

	public String getDatabaseType() {
		return dbeCatalog.getDatabaseType();
	}
}
