package com.tibco.cep.dashboard.common.data;

import java.io.Serializable;

public class FieldValue implements Serializable, Cloneable, Comparable<FieldValue> {

	private static final long serialVersionUID = 405557536901622065L;

	public static final FieldValue EMPTYSTR = new FieldValue(BuiltInTypes.STRING, "");

	public static final FieldValue FALSE = new FieldValue(BuiltInTypes.BOOLEAN, Boolean.FALSE);

	protected DataType dataType;
	@SuppressWarnings({ "rawtypes" })
	private Comparable value;
	protected boolean isNull;

	protected String stringRepresentation;

	protected FieldValue(){
	}

	public FieldValue(DataType dataType, Comparable<?> value) {
		this(dataType,value,false);
	}

	public FieldValue(DataType dataType, boolean isNull) {
		this(dataType,dataType.getDefaultValue(),isNull);
	}

	public FieldValue(DataType dataType, Comparable<?> value, boolean isNull) {
		this.dataType = dataType;
		this.value = value;
		this.isNull = isNull;
		stringRepresentation = isNull + "" + value;
	}

	public Comparable<?> getValue() {
		return value;
	}

	public void setValue(Comparable<?> value) {
		this.value = value;
		stringRepresentation = isNull + "" + value;
	}

	public final DataType getDataType() {
		return dataType;
	}

	public final boolean isNull() {
		return isNull;
	}

	public final void setNull(boolean isNull) {
		this.isNull = isNull;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int compareTo(FieldValue o) {
		if (isNull == o.isNull) {
			return value.compareTo(o.value);
		}
		if (isNull == true) {
			return -1;
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (obj == this){
			return true;
		}
		if (obj instanceof FieldValue){
			FieldValue fieldValue = (FieldValue) obj;
			return fieldValue.isNull == isNull && fieldValue.value.compareTo(value) == 0;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return stringRepresentation.hashCode();
	}

	@Override
	public String toString() {
		return (isNull || value == null) ? "NULL" : dataType.toString(value);
	}

	@Override
	public Object clone() throws CloneNotSupportedException{
		FieldValue clone = new FieldValue();
		clone.dataType = this.dataType;
		clone.isNull = this.isNull;
		clone.stringRepresentation = this.stringRepresentation;
		clone.value = value;
		return clone;
	}
}
