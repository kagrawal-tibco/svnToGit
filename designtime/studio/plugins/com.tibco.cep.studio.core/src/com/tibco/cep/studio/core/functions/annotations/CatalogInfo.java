package com.tibco.cep.studio.core.functions.annotations;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Pranab Dhar Catalog Information
 */
public class CatalogInfo {
	String name;
	List<CategoryInfo> categories = new ArrayList<CategoryInfo>();

	public CatalogInfo() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CategoryInfo> getCategories() {
		return categories;
	}
	
	

	
	
	
	

}