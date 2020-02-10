package com.tibco.cep.studio.core.functions.annotations;

/**
 * @author Pranab Dhar Parameter Information
 */
public class ParamTypeInfo {
	String name;
	String typeClassName;
	boolean isPrimitive;
	boolean isArray;

	public ParamTypeInfo() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeClassName() {
		return typeClassName;
	}

	public void setTypeClassName(String typeClassName) {
		this.typeClassName = typeClassName;
	}

	public boolean isPrimitive() {
		return isPrimitive;
	}

	public void setPrimitive(boolean isPrimitive) {
		this.isPrimitive = isPrimitive;
	}

	public boolean isArray() {
		return isArray;
	}

	public void setArray(boolean isArray) {
		this.isArray = isArray;
	}

}