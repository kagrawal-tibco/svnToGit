package com.tibco.cep.runtime.management.impl.cluster.data.space;

import com.tibco.as.space.IndexDef;
import com.tibco.as.space.Space;

/**
 * Data pertaining to a {@link Space}'s indices. 
 * @author mwiley
 */
public class SpaceIndex{

	public static final String TREE_TYPE = "Tree";
	public static final String GRID_TYPE = "Grid";

	private String name;
	private String type;
	private String fields;

	public SpaceIndex(){
		name = "";
		type = "";
		fields = "";
	}

	public SpaceIndex(String name, String typeName, String fields){
		this.name = name;
		this.type = typeName;
		this.fields = fields;
	}

	/**
	 * @return The name of this index
	 */
	public String getName(){
		return name;
	}

	/**
	 * @return The type name of this index (a String representation of {@link IndexDef.IndexType})
	 */
	public String getTypeName(){
		return type;
	}

	/**
	 * @return The field names that comprise this index. The string will be formatted:  "[value1, value2, ...]"
	 */
	public String getFields(){
		return fields;
	}

	/**
	 * @param name The value to set for the index's name
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * @param name The value to set for the index's type name (a String representation of {@link IndexDef.IndexType})
	 */
	public void setTypeName(String value){
		this.type = value;
	}

	/**
	 * @param fields The value to set for the index's fields
	 */
	public void setFields(String fields){
		this.fields = fields;
	}
}
