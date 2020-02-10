package com.tibco.cep.studio.dbconcept.conceptgen.impl;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.studio.dbconcept.conceptgen.DBEntity;
import com.tibco.cep.studio.dbconcept.conceptgen.DBSchema;

public class DBSchemaImpl implements DBSchema {
	
	private String schemaName;
	
	private String schemaAlias;
	
	private Map<String, DBEntity> entities = new HashMap<String, DBEntity>();

	public DBSchemaImpl(String schemaName, String aliasName){
		this.schemaName = schemaName;
	}
	
	public Map<String, DBEntity> getEntities() {
		return entities;
	}

	public DBEntity getEntity(String entityName) {
		return (DBEntity) entities.get(entityName);
	}

	public String getName() {
		return schemaName;
	}
	
	public String getAlias() {
		return schemaAlias;
	}
	
	public void setAliasName(String aliasName) {
		this.schemaAlias = aliasName;
	}
	
	public void addEntity(DBEntity dbe){
		this.entities.put(dbe.getFullName(), dbe);
	}
	
	public void addEntities(Map<String, DBEntity> m){
		this.entities.putAll(m);
	}
	
	public String toString(){
		return getName();
	}
}
