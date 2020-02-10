package com.tibco.cep.webstudio.client.decisiontable.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author sasahoo
 *
 */
public class ParentArgumentResource extends ArgumentResource {

	private List<ArgumentResource> children = new ArrayList<ArgumentResource>();
	private String path;

	public ParentArgumentResource(String name, String path, String type) {
		super(name, type);
		this.path = path;
	}
	
	public List<ArgumentResource> getChildren() {
		return children;
	}

	public void setChildren(List<ArgumentResource> children) {
		this.children = children;
	}


	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
