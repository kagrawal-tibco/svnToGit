package com.tibco.cep.mapper.codegen;

public class VariableType implements IVariableType {

	String name;
	boolean isConcept;
	boolean isEvent;
	boolean typeRequiresBox = true;
	private boolean isProcess;
	private boolean isTimeEvent;
	private boolean isArray;
	
	public VariableType(String name, boolean isConcept, boolean isEvent, boolean isTimeEvent, boolean isProcess, boolean isArray) {
		this.name = name;
		this.isConcept = isConcept;
		this.isEvent = isEvent;
		this.isTimeEvent = isTimeEvent;
		this.isProcess = isProcess;
		this.isArray = isArray;
	}

	@Override
	public boolean isTypeRequiresBox() {
		return typeRequiresBox;
	}

	public void setTypeRequiresBox(boolean typeRequiresBox) {
		this.typeRequiresBox = typeRequiresBox;
	}

	@Override
	public String getTypeName() {
		return name;
	}
	
	public String getBoxedTypeName() {
		if(name.equals("int")){
			return "Integer";
		} else if(name.equals("double")){
			return "Double";
		} else if(name.equals("long")){
			return "Long";
		} else if(name.equals("boolean")){
			return "Boolean";
		} else if(name.equals("DateTime")){
			return java.util.Calendar.class.getCanonicalName();
		} else {
			return name;
		}
	}

	@Override
	public boolean isConcept() {
		return isConcept;
	}

	@Override
	public boolean isEvent() {
		return isEvent;
	}
	
	@Override
	public boolean isTimeEvent() {
		return isTimeEvent;
	}
	
	@Override
	public boolean isArray() {
		return isArray;
	}
	
	public boolean isPrimitive() {
		return !isConcept && !isEvent && !isProcess && !isTimeEvent;
	}
	
	public String toString() {
		return name;
	}
	@Override
	public boolean isProcess() {
		return isProcess;
	}

}
