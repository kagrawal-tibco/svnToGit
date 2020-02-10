package com.tibco.cep.dashboard.common.data;

@SuppressWarnings("serial")
public abstract class DataType implements java.io.Serializable {
	
	private String dataTypeID;
	private Comparable<?> defaultValue;
	
	private int hashCode;
	private String toString;
	
	public DataType(String dataTypeID, String defaultValue) {
		if (dataTypeID == null){
			throw new IllegalArgumentException("DataTypeID cannot be null");
		}
		this.hashCode = this.getClass().getName().hashCode();
		this.toString = dataTypeID;//this.getClass().getName();
		this.dataTypeID = dataTypeID;
		this.defaultValue = valueOf(defaultValue);
	}

	public final String getDataTypeID() {
		return dataTypeID;
	}

	public final Comparable<?> getDefaultValue() {
		return defaultValue;
	}

	@Override
	public final boolean equals(Object obj) {
		if (obj instanceof DataType){
			DataType castedObj = (DataType) obj;
			return castedObj.toString.equals(toString);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return hashCode;
		
	}

	@Override
	public String toString() {
		return toString;
	}

	public abstract Comparable<?> valueOf(String s) throws IllegalArgumentException;
	
	public abstract String toString(Object o);
	
}