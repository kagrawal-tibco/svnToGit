package com.tibco.cep.studio.dbconcept.conceptgen.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.studio.dbconcept.conceptgen.DBEntity;

/**
 * 
 * @author schelwa
 * 
 * This class represent database object like table or view.
 * Implements DBEntity as well
 *
 */

public class DBEntityImpl extends BaseEntityImpl implements DBEntity {

	protected String objectName; // table/view name

	protected int objectType; //TABLE / VIEW
	
	protected String dbName; // database name.
	
	protected String dbVersion; // database version.

	protected String schemaName; //schema name within the database ==> user schema

//	protected Map columns = new HashMap();
	
	protected Map<String, DBConstraint> fkRelations =  new HashMap<String, DBConstraint>();

	protected DBConstraint pkRelation;
	
	protected int entityTypeEnum;
	
	protected String jdbcResourceURI;
	
	public DBEntityImpl(String name, String namespace, String dbObjectname, int dbObjectType, String dbSchemaName) {
		super(name, namespace);
		this.objectName = dbObjectname;
		this.objectType = dbObjectType;
		this.schemaName = dbSchemaName;
	}

	public void setEntityTypeEnum(int entityTypeEnum) {
		this.entityTypeEnum = entityTypeEnum;
	}

	public void setFkRelations(Map<String, DBConstraint> fkRelations) {
		if(fkRelations != null) {
			this.fkRelations.putAll(fkRelations);
		}
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}

	public void setPkRelation(DBConstraint pkRelation) {
		this.pkRelation = pkRelation;
	}

	public void setDBName(String dbName) {
		this.dbName = dbName;
	}

	public void setDBVersion(String version) {
		this.dbVersion = version;
	}

	public Map<String, DBConstraint> getFkRelations() {
		return fkRelations;
	}

	public DBConstraint getPkRelation() {
		return pkRelation;
	}
	
	public String getDBName() {
		return dbName;
	}

	public String getDBVersion() {
		return dbVersion;
	}

	public int getEntityType() {
//		if ("T".equalsIgnoreCase(objectType)) 
//			return DBEntity.TABLE;
//		else
//			return DBEntity.VIEW;
		return objectType;
	}


	public String getSchema() {
		return schemaName;
	}


	public String getObjName() {
		return objectName;
	}

	public int getBEEntityType() {
		return entityTypeEnum;
	}
	
	public void setBEEntityType(int entityTypeEnum) {
		this.entityTypeEnum = entityTypeEnum;
	}
	
	public void setPrimaryKey(DBConstraint pkRel) {
		this.pkRelation = pkRel;
	}
	
	public void addForeignKey(DBConstraint fkRel) {
		this.fkRelations.put(fkRel.getConstraintName(), fkRel);
	}

	public Set<?> getPK(){
		return this.pkRelation.getColumns();
	}

	public String getJdbcResourceURI() {
		return jdbcResourceURI;
	}
	public void setJDBCResourceURI(String jdbcResourceURI) {
		this.jdbcResourceURI = jdbcResourceURI;
	}
	
	public String toString(){
		return getFullName();
	}
	
}
