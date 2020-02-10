package com.tibco.cep.dashboard.common.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//TODO think if we can use FieldValue instead of Comparable , that gives us the power of allowing NULL values
public class FieldValueArray extends FieldValue {

	private static final long serialVersionUID = -7963403133729164233L;

	@SuppressWarnings("rawtypes")
	private List<Comparable> values;

	protected FieldValueArray() {
	}

	public FieldValueArray(DataType datatype, Comparable<?>[] values){
		this(datatype,values,false);
	}

	public FieldValueArray(DataType datatype, boolean isNull){
		this(datatype,new Comparable<?>[]{datatype.getDefaultValue()},isNull);
	}

	@SuppressWarnings("rawtypes")
	public FieldValueArray(DataType datatype, Comparable<?>[] values, boolean isNull){
		super(datatype,null,isNull);
		this.values = new ArrayList<Comparable>();
		if (values != null){
			for (Comparable<?> value : values) {
				this.values.add(value);
			}
		}
		stringRepresentation = isNull + "" + values.toString();
	}

	@Override
	public Comparable<?> getValue() {
		return getValue(0);
	}

	@Override
	public void setValue(Comparable<?> value) {
		setValue(0, value);
	}

	public Comparable<?> getValue(int index) {
		return values.get(index);
	}

	public Comparable<?>[] getValues() {
		return values.toArray(new Comparable<?>[values.size()]);
	}

	public void setValue(int index,Comparable<?> value) {
		values.set(index, value);
		stringRepresentation = isNull + "" + values.toString();
	}

	public void addValue(Comparable<?> value) {
		values.add(value);
		stringRepresentation = isNull + "" + values.toString();
	}

	public boolean removeValue(Comparable<?> value) {
		boolean remove = values.remove(value);
		stringRepresentation = isNull + "" + values.toString();
		return remove;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object clone() throws CloneNotSupportedException {
		FieldValueArray clone = new FieldValueArray();
		clone.dataType = this.dataType;
		clone.isNull = this.isNull;
		clone.stringRepresentation = this.stringRepresentation;
		clone.values = new ArrayList<Comparable>(values);
		return clone;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int compareTo(FieldValue o) {
		if (o instanceof FieldValueArray){
			FieldValueArray oArray = (FieldValueArray) o;
			if (isNull == o.isNull) {
				int size = values.size();
				int diff = size - oArray.values.size();
				if (diff == 0){
					for (int i = 0; i < size; i++) {
						diff = values.get(i).compareTo(oArray.values.get(i));
						if (diff != 0){
							return diff;
						}
					}
				}
				return diff;
			}
			if (isNull == true) {
				return -1;
			}
		}
		return 1;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this){
			return true;
		}
		if (obj instanceof FieldValueArray){
			FieldValueArray fieldValueArray = (FieldValueArray) obj;
			return fieldValueArray.isNull == isNull && fieldValueArray.values.equals(values);
		}
		return false;

	}

	@SuppressWarnings("rawtypes")
	@Override
	public String toString() {
		if (isNull() == true){
			return "null";
		}
		StringBuilder sb = new StringBuilder("[");
		Iterator<Comparable> valuesIterator = values.iterator();
		while (valuesIterator.hasNext()) {
			Comparable value = valuesIterator.next();
			sb.append(dataType.toString(value));
			if (valuesIterator.hasNext() == true){
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
}
