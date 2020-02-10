package com.tibco.cep.dashboard.common.data;

import java.io.Serializable;

/**
 * Define all the field information of a tuple schema field.
 */
public final class TupleSchemaField implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3140859372570441599L;

	private String fieldName;
	private String fieldID;
	private DataType fieldDataType;
	private long fieldMaxSize;
	private boolean array;
	private FieldValue defaultValue;
	private boolean systemField;
	private boolean artificalField;
	private FieldValue nullValue;

	private String stringRepresentation;
	private int hashCode;

	public TupleSchemaField(String fieldName, String fieldID, DataType fieldDataType, long fieldMaxSize, boolean array, String defaultValue, String nullValue, boolean systemField, boolean artificalField) {
		super();
		this.fieldName = fieldName;
		this.fieldID = fieldID;
		this.fieldDataType = fieldDataType;
		this.fieldMaxSize = fieldMaxSize;
		this.array = array;
		if (defaultValue != null) {
			this.defaultValue = new FieldValue(this.fieldDataType,this.fieldDataType.valueOf(defaultValue),false);
		} else {
			this.defaultValue = new FieldValue(this.fieldDataType,this.fieldDataType.getDefaultValue(),false);
		}
		if (nullValue != null) {
			this.nullValue = new FieldValue(this.fieldDataType,this.fieldDataType.valueOf(nullValue),true);
		}
		else {
			this.nullValue = new FieldValue(this.fieldDataType,this.defaultValue.getValue(),true);
		}
		this.systemField = systemField;
		this.artificalField = artificalField;
		StringBuilder builder = new StringBuilder("TupleSchemaField");
		builder.append("[name=");
		builder.append(fieldName);
		builder.append(",id=");
		builder.append(fieldID);
		builder.append(",datatype=");
		builder.append(fieldDataType);
		builder.append(",maxsize=");
		builder.append(fieldMaxSize);
		builder.append(",array=");
		builder.append(array);
		builder.append(",defaultvalue=");
		builder.append(defaultValue);
		builder.append(",value=");
		builder.append(nullValue);
		builder.append(",systemfield=");
		builder.append(systemField);
		builder.append(",artificialfield=");
		builder.append(artificalField);
		builder.append("]");
		stringRepresentation = builder.toString();
		hashCode = stringRepresentation.hashCode();
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getFieldID() {
		return fieldID;
	}

	public DataType getFieldDataType() {
		return fieldDataType;
	}

	public long getFieldMaxSize() {
		return fieldMaxSize;
	}

	public boolean isArray() {
		return array;
	}

	public FieldValue getDefaultValue() {
		return defaultValue;
	}

	public FieldValue getNULLValue() {
		return nullValue;
	}

	public boolean isSystemField() {
		return systemField;
	}

	public boolean isArtificalField() {
		return artificalField;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TupleSchemaField){
			TupleSchemaField field = (TupleSchemaField) obj;
			return field.stringRepresentation.equals(stringRepresentation);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public String toString() {
		return stringRepresentation;
	}

}