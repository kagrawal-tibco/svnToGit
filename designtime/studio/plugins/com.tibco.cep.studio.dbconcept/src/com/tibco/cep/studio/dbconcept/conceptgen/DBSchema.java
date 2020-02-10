package com.tibco.cep.studio.dbconcept.conceptgen;

import java.util.Map;

public interface DBSchema {

	public String getName();
	
	/*public String getAlias();*/
	
	public Map<String, DBEntity> getEntities();
	
	public DBEntity getEntity(String entityName);
	
}
