package com.tibco.cep.runtime.management.impl.cluster.data.space;

import com.tibco.as.space.FieldDef;
import com.tibco.as.space.Space;

/**
 * Data pertaining to a {@link Space}'s fields.
 * @author mwiley
 */
public class SpaceField{
	private String name;
	private String type;
	private boolean nullable;
	private boolean key;

	public SpaceField(){
		name = "";
		type = null;
		nullable = false;
	}

	public SpaceField(String name, String typeName, boolean isNullable, boolean isKey){
		this.name = name;
		this.type = typeName;
		this.nullable = isNullable;
		this.key = isKey;
	}

	/**
	 * @return The field's name.
	 */
	public String getName(){
		return name;
	}

	/**
	 * @return The field's type (a String representation of {@link FieldDef.FieldType}.
	 */
	public String getTypeName(){
		return type;
	}

	/**
	 * @return Flag indicating whether or not the field is nullable in ActiveSpaces.
	 */
	public boolean isNullable(){
		return nullable;
	}

	/**
	 * @return Flag indicating whether or not the field is a key in the corresponding Space.
	 */
	public boolean isKey(){
		return key;
	}

	/**
	 * @param name The name to use for the field.
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * @param name The type to use for the field. Should be a String representation of a {@link FieldDef.FieldType}.
	 */
	public void setTypeName(String value){
		this.type = value;
	}

	/**
	 * @param nullable The value to use to indicate whether the field is nullable.
	 */
	public void setNullable(boolean nullable){
		this.nullable = nullable;
	}
	
	/**
	 * @param isKey The value to use to indicate whether the field is a key field.
	 */
	public void setIsKey(boolean isKey){
		this.key = isKey;
	}

	@Override
	public String toString(){
		return "{name:" + name + ", type:" + type + ", nullable:" + nullable + ", isKey:" + key + "}";
	}

}
