package com.tibco.cep.studio.dbconcept.conceptgen.impl;

import com.tibco.cep.studio.dbconcept.conceptgen.DBProperty;

/**
 * 
 * @author schelwa
 * 
 * A database object column/property is represented by this class
 *
 */
abstract public class DBPropertyImpl extends BasePropertyImpl implements DBProperty {

	//Database data type
	protected String columnType;

//	//in case this a part of primary or foreign key, this property describes its position
//	protected int position;

	//need to read sequence 
	protected String sequence;

	//is this a part of foreign key
	protected boolean isFK;

	//is this a part of primary key
	protected boolean isPK;

	//only for number data type
	protected int scale;

	public DBPropertyImpl(String columnName,
			String columnType, int length, int precision,
			int scale,
			boolean nullable) {
		super(columnName, columnName);
		this.columnType = columnType;
		this.length = length;
		this.precision = precision;
		this.scale = scale;
		this.nullable = nullable;
	}

	public String getColumnName() {
		return name;
	}
	
	public String getColumnType() {
		return this.columnType;
	}

	public String getSequence() {
		return sequence;
	}

	public boolean isFK() {
		return isFK;
	}

	public boolean isPK() {
		return isPK;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}

	public void setFK(boolean isFK) {
		this.isFK = isFK;
	}

	public void setPK(boolean isPK) {
		this.isPK = isPK;
	}
}
