/**
 * 
 */
package com.tibco.cep.runtime.model.serializers;

import java.util.Arrays;

/**
 * @author vpatil
 *
 */
public enum FieldType {
	INTEGER("integer", "int"),
	LONG("long", null),
	FLOAT("float", null),
	DOUBLE("double", null),
	BOOLEAN("boolean", null),
	STRING("string", null),
	DATETIME("datetime", null),
	BLOB("blob", "opaque");
	
	private String type;
	private String alternateType;
	private FieldType(String type, String alternateType) {
		this.type = type;
		this.alternateType = alternateType;
	}
	
	public static FieldType getByType(String type) {
		for (FieldType cType : values()) {
			if (cType.type.equals(type) || (type.equals(cType.alternateType))) return cType;
		}
		throw new IllegalArgumentException(String.format("Invalid Field type[%s] specified. Valid field types are %s", type, getFieldTypeNames()));
	}
	
	@Override
	public String toString() {
		return this.type;
	}
	
	public static String getFieldTypeNames() {
		String[] fieldTypes = new String[FieldType.values().length]; int i=0;
		for (FieldType fieldType : FieldType.values()) {
			fieldTypes[i++] = fieldType.toString();
		}
		return Arrays.toString(fieldTypes);
	}
}
