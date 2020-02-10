package com.tibco.cep.studio.dashboard.core.insight.model.helpers;

public final class Subscript {
	
	private int index;
	
	private boolean isIndex;
	
	private String childName;
	
	private String childType;
	
	public Subscript(String value){
		try {
			index = Integer.parseInt(value);
			isIndex = true;
		} catch (NumberFormatException e) {
			String[] splits = value.split("=");
			if (splits.length != 2){
				throw new IllegalArgumentException(value+" is not in 'name=type' format");
			}
			childName = splits[0];
			childType = splits[1];
		}
	}
	
	public boolean isIndex(){
		return isIndex;
	}
	
	public int getIndex(){
		return index;
	}

	public final String getChildName() {
		return childName;
	}

	public final String getChildType() {
		return childType;
	}
	
}
