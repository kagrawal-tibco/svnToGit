package com.tibco.cep.dashboard.plugin.beviews.drilldown.related;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConceptValueObject {
	
	private String typeId;
	
	private String typeName;
	
	private String id;
	
	private LinkedHashMap<String, String> fields;
	
	private LinkedHashMap<String, List<ConceptValueObject>> children;

	public ConceptValueObject(String typeid, String typeName, String id) {
		super();
		this.typeId = typeid;
		this.typeName = typeName;
		this.id = id;
		fields = new LinkedHashMap<String, String>();
		children = new LinkedHashMap<String, List<ConceptValueObject>>();
	}
	
	public void addField(String name, String value){
		fields.put(name, value);
	}
	
	public void addChild(ConceptValueObject child){
		List<ConceptValueObject> list = children.get(child.getTypeName());
		if (list == null) {
			list = new LinkedList<ConceptValueObject>();
			children.put(child.getTypeName(), list);
		}
		list.add(child);
	}
	
	public Map<String,String> getFields(){
		return Collections.unmodifiableMap(fields);
	}
	
	public Collection<String> getChildrenTypes(){
		return children.keySet();
	}
	
	public Collection<ConceptValueObject> getChildren(String type){
		return children.get(type);
	}
	
	public String getTypeId() {
		return typeId;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public String getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((typeId == null) ? 0 : typeId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ConceptValueObject other = (ConceptValueObject) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (typeId == null) {
			if (other.typeId != null) {
				return false;
			}
		} else if (!typeId.equals(other.typeId)) {
			return false;
		}
		return true;
	}
	
}
