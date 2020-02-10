package com.tibco.cep.studio.core.functions.annotations;

import java.util.ArrayList;
import java.util.List;

import com.tibco.be.model.functions.BEPackage;


/**
 * @author Pranab Dhar Category Information
 */
public class CategoryInfo {
	private String category;
	String implClassName;
	List<FunctionInfo> functions = new ArrayList<FunctionInfo>();
	private BEPackage annotation;

	public CategoryInfo() {
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImplClass() {
		return implClassName;
	}

	public void setImplClassName(String implClassName) {
		this.implClassName = implClassName;
	}

	public List<FunctionInfo> getFunctions() {
		return functions;
	}

	public void setAnnotation(BEPackage pkgAnnotation) {
		this.annotation = pkgAnnotation;
		
	}
	public BEPackage getAnnotation() {
		return annotation;
	}

}