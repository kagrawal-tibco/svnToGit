package com.tibco.cep.webstudio.client.decisiontable.model;

/**
 * 
 * @author sasahoo
 *
 */
public class ArgumentResource {

	private String name;
	private String alias;
	private String type;
	private ParentArgumentResource parent;
	private String ownerPath;
	private boolean array;
	private String conceptTypePath;
	private boolean primitive = false;
	private boolean hasAssociatedDomain = false;
	
	public ArgumentResource(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public ParentArgumentResource getParent() {
		return parent;
	}

	public void setParent(ParentArgumentResource parent) {
		this.parent = parent;
	}

	public String getOwnerPath() {
		return ownerPath;
	}

	public void setOwnerPath(String ownerPath) {
		this.ownerPath = ownerPath;
	}

	public String getConceptTypePath() {
		return conceptTypePath;
	}

	public void setConceptTypePath(String conceptTypePath) {
		this.conceptTypePath = conceptTypePath;
	}
	
	public boolean isArray() {
		return array;
	}

	public void setArray(boolean array) {
		this.array = array;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public boolean isPrimitive() {
		return primitive;
	}

	public void setPrimitive(boolean primitive) {
		this.primitive = primitive;
	}

	public boolean hasAssociatedDomain() {
		return hasAssociatedDomain;
	}

	public void setAssociatedDomain(boolean hasAssociatedDomain) {
		this.hasAssociatedDomain = hasAssociatedDomain;
	}


}