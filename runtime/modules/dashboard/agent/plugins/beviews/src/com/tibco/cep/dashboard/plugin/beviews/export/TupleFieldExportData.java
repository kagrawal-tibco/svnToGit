package com.tibco.cep.dashboard.plugin.beviews.export;

/**
 * @author rajesh
 * 
 */
public class TupleFieldExportData {

	private String name;
	private Object value;
	private boolean bSystem;

	public TupleFieldExportData(String name, Object value, boolean bSystem) {
		this.name = name;
		this.value = value;
		this.bSystem = bSystem;
	}

	public boolean isSystem() {
		return bSystem;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}
	
}