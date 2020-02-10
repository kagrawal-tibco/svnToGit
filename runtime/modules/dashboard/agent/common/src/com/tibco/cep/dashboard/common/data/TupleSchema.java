package com.tibco.cep.dashboard.common.data;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 */
public class TupleSchema implements Serializable {

	private static final long serialVersionUID = -3240290343790691711L;

	public static final String ID_FIELD_NAME = "id";

	private String sourceName;
	private transient TupleSchemaSource source;
	private String scopeName;
	private String typeName;
	private String typeID;

	private boolean dynamic;

	private List<TupleSchemaField> fields;
	private transient Map<String, TupleSchemaField> fieldsByName;
	private transient Map<String, TupleSchemaField> fieldsByID;
	private transient List<TupleSchemaField> systemFields;

	private TupleSchemaField idField;

	private TupleSchemaField timeStampField;

	public TupleSchema(TupleSchemaSource source) {
		this(source, false);
	}

	public TupleSchema(TupleSchemaSource source, boolean dynamic) {
		this.source = source;
		this.sourceName = this.source.getClass().getName();
		this.dynamic = dynamic;
		typeName = this.source.getTypeName();
		scopeName = this.source.getScopeName();
		typeID = this.source.getTypeID();
		fields = new LinkedList<TupleSchemaField>();
		fieldsByName = new HashMap<String, TupleSchemaField>();
		fieldsByID = new HashMap<String, TupleSchemaField>();
		systemFields = new LinkedList<TupleSchemaField>();
	}

	public TupleSchemaSource getSchemaSource(){
		return source;
	}

	public int addField(int position, String fieldName, String fieldID, DataType dataType, boolean isArray, long maxSize) {
		return addField(position, fieldName, fieldID, dataType, isArray, maxSize, false);
	}

	public int addField(int position, String fieldName, String fieldID, DataType dataType, boolean isArray, long maxSize, boolean isSystemField) {
		return addField(position, fieldName, fieldID, dataType, isArray, maxSize, isSystemField, false, null);
	}

	public int addField(int position, String fieldName, String fieldID, DataType dataType, boolean isArray, long maxSize, boolean isSystemField, boolean isArtificalField) {
		return addField(position, fieldName, fieldID, dataType, isArray, maxSize, isSystemField, isArtificalField, null);
	}

	public int addField(int position, String fieldName, String fieldID, DataType dataType, boolean isArray, long maxSize, boolean isSystemField, boolean isArtificalField, String defaultValue) {
		return addField(position, fieldName, fieldID, dataType, isArray, maxSize, isSystemField, isArtificalField, defaultValue, defaultValue);
	}

	public int addField(int position, String fieldName, String fieldID, DataType dataType, boolean isArray, long maxSize, boolean isSystemField, boolean isArtificalField, String defaultValue,String nullValue) {
		return addField(position, new TupleSchemaField(fieldName, fieldID, dataType, maxSize, isArray, defaultValue, nullValue,isSystemField, isArtificalField));
	}

	public int addField(int position, TupleSchemaField field) {
		if (fields.contains(field) == true) {
			throw new IllegalArgumentException(field.getFieldName() + " already exists");
		}
		if (position < 0) {
			position = getFieldCount();
		}
		fields.add(position, field);
		fieldsByName.put(field.getFieldName(), field);
		fieldsByID.put(field.getFieldID(), field);
		if (field.isSystemField() == true) {
			systemFields.add(field);
		}
		return position;
	}

	public int getFieldCount() {
		return fields.size();
	}

	public String getFieldIDByPosition(int position) {
		return getFieldByPosition(position).getFieldID();
	}

	public String getFieldNameByPosition(int position) {
		return getFieldByPosition(position).getFieldName();
	}

	public DataType getFieldDataTypeByPosition(int position) {
		return getFieldByPosition(position).getFieldDataType();
	}

	public long getFieldMaxSizeByPosition(int position) {
		return getFieldByPosition(position).getFieldMaxSize();
	}

	public boolean getFieldIsArrayByPosition(int position) {
		return getFieldByPosition(position).isArray();
	}

	public boolean getFieldIsSystemFieldByPosition(int position) {
		return getFieldByPosition(position).isSystemField();
	}

	public FieldValue getFieldDefaultValueByPosition(int position) {
		return getFieldByPosition(position).getDefaultValue();
	}

	public String getFieldIDByName(String fieldName) {
		return getFieldByName(fieldName).getFieldID();
	}

	public DataType getFieldDataTypeByName(String fieldName) {
		return getFieldByName(fieldName).getFieldDataType();
	}

	public long getFieldMaxSizeByName(String fieldName) {
		return getFieldByName(fieldName).getFieldMaxSize();
	}

	public boolean getFieldIsArrayByName(String fieldName) {
		return getFieldByName(fieldName).isArray();
	}

	public boolean getFieldIsSystemFieldByName(String fieldName) {
		return getFieldByName(fieldName).isSystemField();
	}

	public FieldValue getFieldDefaultValueByName(String fieldName) {
		return getFieldByName(fieldName).getDefaultValue();
	}

	public String getFieldNameByID(String fieldID) {
		return getFieldByID(fieldID).getFieldName();
	}

	public DataType getFieldDataTypeByID(String fieldID) {
		return getFieldByID(fieldID).getFieldDataType();
	}

	public long getFieldMaxSizeByID(String fieldID) {
		return getFieldByID(fieldID).getFieldMaxSize();
	}

	public boolean getFieldIsArrayByID(String fieldID) {
		return getFieldByID(fieldID).isArray();
	}

	public boolean getFieldIsSystemFieldByID(String fieldID) {
		return getFieldByID(fieldID).isSystemField();
	}

	public FieldValue getFieldDefaultValueByID(String fieldID) {
		return getFieldByID(fieldID).getDefaultValue();
	}

	public boolean hasSystemFields() {
		return !systemFields.isEmpty();
	}

	public final TupleSchemaField getFieldByPosition(int position) {
		return fields.get(position);
	}

	public final TupleSchemaField getFieldByName(String fieldName) {
		return fieldsByName.get(fieldName);
	}

	public TupleSchemaField getFieldByID(String fieldId) {
		return fieldsByID.get(fieldId);
	}

	public boolean hasField(String fieldName) {
		return fieldsByName.containsKey(fieldName);
	}

	public final int getFieldPositionByName(String fieldName) {
		return fields.indexOf(fieldsByName.get(fieldName));
	}

	public int getFieldPositionById(String fieldId) {
		return fields.indexOf(fieldsByID.get(fieldId));
	}

	public TupleSchemaField removeFieldByPosition(int position) {
		TupleSchemaField schemaField = fields.remove(position);
		if (schemaField != null) {
			fieldsByName.remove(schemaField.getFieldName());
			fieldsByID.remove(schemaField.getFieldID());
			if (schemaField.isSystemField() == true) {
				systemFields.remove(schemaField);
			}
		}
		return schemaField;
	}

	public TupleSchemaField removeFieldByName(String fieldName) {
		TupleSchemaField schemaField = fieldsByName.remove(fieldName);
		if (schemaField != null) {
			fields.remove(schemaField);
			fieldsByID.remove(schemaField.getFieldID());
			if (schemaField.isSystemField() == true) {
				systemFields.remove(schemaField);
			}
		}
		return schemaField;
	}

	public TupleSchemaField removeFieldByID(String fieldID) {
		TupleSchemaField schemaField = fieldsByID.remove(fieldID);
		if (schemaField != null) {
			fields.remove(schemaField);
			fieldsByName.remove(schemaField.getFieldName());
			if (schemaField.isSystemField() == true) {
				systemFields.remove(schemaField);
			}
		}
		return schemaField;
	}

	public List<TupleSchemaField> getSystemFields() {
		return Collections.unmodifiableList(systemFields);
	}

	public boolean hasArrayFields() {
		for (TupleSchemaField field : fields) {
			if (field.isArray() == true) {
				return true;
			}
		}
		return false;
	}

	public void setTimeStampField(TupleSchemaField field){
		if (this.fields.contains(field) == false){
			throw new IllegalArgumentException(field.getFieldName()+" is not a part of "+source.toString()+"'s schema");
		}
		if (field.getFieldDataType() != BuiltInTypes.DATETIME){
			throw new IllegalArgumentException(field.getFieldName()+"' datatype["+field.getFieldDataType()+"] is not suitable for timestamp field usage");
		}
		this.timeStampField = field;
	}

	public TupleSchemaField getTimeStampField(){
		return timeStampField;
	}

	public void setIDField(TupleSchemaField field){
		if (this.fields.contains(field) == false){
			throw new IllegalArgumentException(field.getFieldName()+" is not a part of "+source.toString()+"'s schema");
		}
		this.idField = field;
	}

	public TupleSchemaField getIDField(){
		return idField;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(TupleSchema.class.getSimpleName());
		builder.append("[source=");
		builder.append(source.toString());
		builder.append(",fields=[");
		int fieldsCnt = fields.size();
		for (int i = 0; i < fieldsCnt; i++) {
			TupleSchemaField field = getFieldByPosition(i);
			if (i == 0) {
				builder.append("position[");
			} else {
				builder.append(",position[");
			}
			builder.append(i);
			builder.append("]=");
			builder.append(field.toString());
		}
		builder.append("]]");
		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof TupleSchema) {
			TupleSchema objAsSchema = (TupleSchema) obj;
			return objAsSchema.source.equals(source);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return source.hashCode();
	}

	public final String getScopeName() {
		return source.getScopeName();
	}

	public final String getTypeID() {
		return source.getTypeID();
	}

	public final String getTypeName() {
		return source.getTypeName();
	}

	public final boolean isDynamic() {
		return dynamic;
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		this.source.writeExternal(out);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		try {
			this.source = (TupleSchemaSource) Class.forName(this.sourceName).newInstance();
			this.source.setScopeName(scopeName);
			this.source.setTypeID(typeID);
			this.source.setTypeName(typeName);
			this.source.readExternal(in);
			for (TupleSchemaField field : fields) {
				fieldsByID.put(field.getFieldID(), field);
				fieldsByName.put(field.getFieldName(), field);
				if (field.isSystemField() == true){
					systemFields.add(field);
				}
			}
		} catch (InstantiationException e) {
			throw new IOException("could not instantiate "+sourceName,e);
		} catch (IllegalAccessException e) {
			throw new IOException("could not access "+sourceName,e);
		}
	}

}
