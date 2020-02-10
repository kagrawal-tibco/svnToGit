package com.tibco.cep.dashboard.common.data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.dashboard.common.utils.SUID;

public class Tuple implements Serializable, Cloneable {

	private static final long serialVersionUID = 3984778433164653707L;

	protected String id;

	protected TupleSchema schema;

	protected Map<String, FieldValue> fieldIdToFieldValueMap;

	protected boolean dirty;

	protected long sysTimeStamp;

	protected Tuple() {
	}

	public Tuple(TupleSchema schema, Map<String, FieldValue> fieldIdToFieldValueMap) {
		this(schema);
		setFieldValues(fieldIdToFieldValueMap);
	}

	protected Tuple(TupleSchema schema) {
		this.schema = schema;
		this.fieldIdToFieldValueMap = new HashMap<String, FieldValue>();
	}

	protected void setFieldValues(Map<String, FieldValue> fieldIdToFieldValueMap) {
		validateFieldValueMap(fieldIdToFieldValueMap);
		this.fieldIdToFieldValueMap = new HashMap<String, FieldValue>(fieldIdToFieldValueMap);
		sysTimeStamp = System.currentTimeMillis();
		TupleSchemaField idField = schema.getIDField();
		if (idField != null){
			this.id = fieldIdToFieldValueMap.get(idField.getFieldID()).toString();
		}
		else {
			this.id = "TUP:"+SUID.createId();
		}

	}

	protected void validateFieldValueMap(Map<String, FieldValue> fieldIdToFieldValueMap) {
		int fieldCnt = this.schema.getFieldCount();
		for (int i = 0; i < fieldCnt; i++) {
			TupleSchemaField field = this.schema.getFieldByPosition(i);
			FieldValue fieldValue = fieldIdToFieldValueMap.get(field.getFieldID());
			if (fieldValue == null){
				//we add a NULL value
				if (field.isArray() == true){
					fieldValue = new FieldValueArray(field.getFieldDataType(),true);
				}
				else {
					fieldValue = new FieldValue(field.getFieldDataType(),true);
				}
				fieldIdToFieldValueMap.put(field.getFieldID(), fieldValue);
			}
			else {
				//validate that incoming value is correct
				if (field.isArray() == true && fieldValue instanceof FieldValueArray == false){
					throw new IllegalArgumentException("Value for "+field.getFieldName()+" is not array based");
				}
				if (field.getFieldDataType() != fieldValue.getDataType()){
					throw new IllegalArgumentException("Datatype of value does not match the datatype of "+field.getFieldName());
				}
			}
		}
	}

	public FieldValue getFieldValueByID(String fieldID) {
		return (FieldValue) fieldIdToFieldValueMap.get(fieldID);
	}

	public FieldValue getFieldValueByName(String fieldName) {
		TupleSchemaField field = this.schema.getFieldByName(fieldName);
		if (field == null){
			throw new IllegalArgumentException(fieldName+" does not exist in "+schema);
		}
		return getFieldValueByID(field.getFieldID());
	}

	public FieldValue getFieldValueByPosition(int fieldPosition) {
		TupleSchemaField field = this.schema.getFieldByPosition(fieldPosition);
		if (field == null){
			throw new IllegalArgumentException(fieldPosition+" does not exist in "+schema);
		}
		return getFieldValueByID(field.getFieldID());
	}

	public FieldValueArray getFieldArrayValueByID(String fieldID) {
		return ((FieldValueArray) fieldIdToFieldValueMap.get(fieldID));
	}

	public FieldValueArray getFieldArrayValueByName(String fieldName) {
		TupleSchemaField field = this.schema.getFieldByName(fieldName);
		if (field == null){
			throw new IllegalArgumentException(fieldName+" does not exist in "+schema);
		}
		return getFieldArrayValueByID(field.getFieldID());
	}

	public FieldValueArray getFieldArrayValueByPosition(int fieldPosition) {
		TupleSchemaField field = this.schema.getFieldByPosition(fieldPosition);
		if (field == null){
			throw new IllegalArgumentException(fieldPosition+" does not exist in "+schema);
		}
		return getFieldArrayValueByID(field.getFieldID());
	}

	public boolean isFieldNullByID(String fieldID) {
		FieldValue field = getFieldValueByID(fieldID);
		if (field == null){
			throw new IllegalArgumentException(fieldID+" does not exist in "+schema);
		}
		return field.isNull();
	}

	public boolean isFieldNullByName(String fieldName) {
		FieldValue field = getFieldValueByName(fieldName);
		if (field == null){
			throw new IllegalArgumentException(fieldName+" does not exist in "+schema);
		}
		return field.isNull();
	}

	public boolean isFieldNullByPosition(int position) {
		FieldValue field = getFieldValueByPosition(position);
		if (field == null){
			throw new IllegalArgumentException(position+" does not exist in "+schema);
		}
		return field.isNull();
	}

	public void setFieldNullByID(String fieldID) {
		FieldValue fieldValue = getFieldValueByID(fieldID);
		if (fieldValue == null) {
			fieldValue = new FieldValue(schema.getFieldDataTypeByID(fieldID), true);
			this.fieldIdToFieldValueMap.put(fieldID, fieldValue);
		} else {
			fieldValue.setValue(schema.getFieldDataTypeByID(fieldID).getDefaultValue());
			fieldValue.setNull(true);
		}
		dirty = true;
	}

	public void setFieldNullByName(String fieldName) {
		setFieldNullByID(this.schema.getFieldIDByName(fieldName));
	}

	public void setFieldNullByPosition(int position) {
		setFieldNullByID(this.schema.getFieldIDByPosition(position));
	}

	public int getFieldCount() {
		return schema.getFieldCount();
	}

	public String getId() {
		return id;
	}

	public boolean isDirty() {
		return dirty;
	}

	public TupleSchema getSchema() {
		return schema;
	}

	public long getTimestamp() {
		TupleSchemaField timeStampField = schema.getTimeStampField();
		if (timeStampField == null) {
			return sysTimeStamp;
		}
		Date timestamp = (Date) getFieldValueByID(timeStampField.getFieldID()).getValue();
		return timestamp.getTime();
	}

	public String getTypeId() {
		return schema.getTypeID();
	}

	public Object clone() throws CloneNotSupportedException {
		Tuple clone = new Tuple();
		clone.id = this.id;
		clone.schema = this.schema;
		clone.sysTimeStamp = this.sysTimeStamp;
		clone.dirty = this.dirty;
		clone.fieldIdToFieldValueMap = new HashMap<String, FieldValue>();
		for (String fieldID : this.fieldIdToFieldValueMap.keySet()) {
			FieldValue fieldValue = this.fieldIdToFieldValueMap.get(fieldID);
			clone.fieldIdToFieldValueMap.put(fieldID, (FieldValue) fieldValue.clone());
		}
		return clone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (dirty ? 1231 : 1237);
		result = prime * result + ((fieldIdToFieldValueMap == null) ? 0 : fieldIdToFieldValueMap.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((schema == null) ? 0 : schema.hashCode());
		result = prime * result + (int) (sysTimeStamp ^ (sysTimeStamp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tuple other = (Tuple) obj;
		if (schema == null) {
			if (other.schema != null)
				return false;
		} else if (!schema.equals(other.schema))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (fieldIdToFieldValueMap == null) {
			if (other.fieldIdToFieldValueMap != null)
				return false;
		} else if (!fieldIdToFieldValueMap.equals(other.fieldIdToFieldValueMap))
			return false;
		if (dirty != other.dirty)
			return false;
		if (sysTimeStamp != other.sysTimeStamp)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Tuple[");
		sb.append("id="+id);
		sb.append(",sysTimeStamp="+sysTimeStamp);
		sb.append(",dirty="+dirty);
		sb.append(",values=[");
		int cnt = schema.getFieldCount();
		for (int i = 0; i < cnt; i++) {
			TupleSchemaField schemaField = schema.getFieldByPosition(i);
			FieldValue fieldValue = fieldIdToFieldValueMap.get(schemaField.getFieldID());
			sb.append("[id="+schemaField.getFieldID());
			sb.append(",name="+schemaField.getFieldName());
			sb.append(",datatype="+schemaField.getFieldDataType());
			sb.append(",value="+fieldValue);
			sb.append("]");
			if (i + 1 < cnt){
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}



}