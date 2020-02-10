package com.tibco.cep.studio.dbconcept.conceptgen.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.studio.dbconcept.conceptgen.BaseEntity;
import com.tibco.cep.studio.dbconcept.conceptgen.BaseProperty;
import com.tibco.cep.studio.dbconcept.conceptgen.BaseRelationship;

/**
 * 
 * @author schelwa
 * 
 * An extended entity is either a derived base/database entity or 
 * a newly introduced entity for which information is provided
 * in database schema  
 *
 */
public class BaseEntityImpl implements BaseEntity {
	
	protected String name;
	protected String desc;
	protected String alias;
	protected String namespace;
	protected String datasource;
	protected Map<String, BaseRelationship> relationships = new LinkedHashMap<String, BaseRelationship>();
	protected Map<String, BaseProperty> properties = new LinkedHashMap<String, BaseProperty>();
	protected String superEntityName;
	protected int type;
	
	public BaseEntityImpl(String name, String namespace) {
		this.name = name;
		this.namespace = namespace == null ? "" : namespace;
	}

	public void setAliasName(String alias) {
		this.alias = alias;
	}


	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String desc) {
		this.desc = desc;
	}
	
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}
	
	public Map<String, BaseProperty> getAllProperties() {
		return this.properties;
	}
	
	public void setProperties(Map<String, BaseProperty> properties) {
		this.properties.putAll(properties);
	}

	public void addProperty(BaseProperty property) {
		this.properties.put(property.getName(),property);
	}


	public void removeRelationships(BaseRelationship rel) {
		this.relationships.remove(rel.getName());
	}

	public void addRelationship(BaseRelationship relationship) {
		this.relationships.put(relationship.getName(), relationship);
	}
	
	public void setSuperEntityName(String superEntityName) {
		this.superEntityName = superEntityName;
	}

	public void setType(int type){
		this.type = type;
	}
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return desc;
	}
	
	public String getAlias() {
		return alias;
	}

	public String getAlias(String dictionary) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<BaseRelationship> getChildEntities() {
		if(relationships.size() > 0) {
			return new ArrayList<BaseRelationship>(relationships.values());
		} else {
			return new ArrayList<BaseRelationship>();
		}
	}

	public String getFullName() {
		return namespace + name;
	}

	public String getNamespace() {
		return namespace;
	}

	public List<BaseProperty> getProperties() {
		return new ArrayList<BaseProperty>(properties.values());
	}

	public BaseProperty getProperty(String propertyName) {
		return (BaseProperty) properties.get(propertyName);
	}

	public BaseRelationship getRelationship(String relationshipName) {
		return (BaseRelationship) relationships.get(relationshipName);
	}

	public String getSuperEntityName() {
		return superEntityName;
	}
	
	public String getDatasource() {
		return this.datasource;
	}
	
	public int getType(){
		return type;
	}
	
	public Map<String, BaseRelationship> getRelations() {
		return this.relationships;
	}
}
