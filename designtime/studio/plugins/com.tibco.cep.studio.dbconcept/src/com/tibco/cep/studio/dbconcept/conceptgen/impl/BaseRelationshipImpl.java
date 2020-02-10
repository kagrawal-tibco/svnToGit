package com.tibco.cep.studio.dbconcept.conceptgen.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import com.tibco.cep.studio.dbconcept.conceptgen.BaseEntity;
import com.tibco.cep.studio.dbconcept.conceptgen.BaseRelationship;
import com.tibco.cep.studio.dbconcept.conceptgen.RelationshipKey;
/**
 * 
 * @author schelwa
 * 
 * the relationship between entities
 *
 */
public class BaseRelationshipImpl implements BaseRelationship {

	protected String relationshipName;

	protected BaseEntity childEntity;
	
	protected String childEntityName;
	
	protected int relEnum;
	
	protected int cardinality = 0;
	
	protected Set<RelationshipKey> keySet = new LinkedHashSet<RelationshipKey>();
	
	protected int position;
	
	/*
	 * for the time being its going to be 0
	 */
	public int getCardinality() {
		return cardinality;
	}

	public BaseEntity getChildEntity() {
		return childEntity;
	}

	public String getChildEntityName() {
		return childEntityName;
	}
	
	public String getName() {
		return relationshipName;
	}

	public int getRelationshipEnum() {
		return relEnum;
	}

	public void setName(String name){
		this.relationshipName = name;
	}
	
	public void setRelationshipEnum(int relEnum) {
		this.relEnum = relEnum;
	}
	
	public void setChildEntityName(String name) {
		this.childEntityName = name;
	}
	
	public void setChildEntity(BaseEntity childEntity) {
		this.childEntity = childEntity;
	}
	
	public void setCardinality(int num){
		this.cardinality = num;
	}

	public void addRelationshipKey(RelationshipKey key) {
		keySet.add(key);
	}
	
	public void addRelationshipKeySet(Set<RelationshipKey> keys) {
		keySet.addAll(keys);
	}
	
	public Set<RelationshipKey> getRelationshipKeySet() {
		return keySet;
	}

	public int getPosition() {
		return position;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
}
