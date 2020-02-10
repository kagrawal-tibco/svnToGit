package com.tibco.cep.studio.dbconcept.conceptgen.impl;

/**
 * 
 * Default implementaton of general types supported by DB's
 *
 */
public class DefaultDBPropertyImpl extends DBPropertyImpl {

	int scale;
	public DefaultDBPropertyImpl(String entityName, String columnName,
			String columnType, int length, int precision, int scale,
			boolean nullable) {
		
		super( columnName, columnType, length, precision, scale, nullable);
		setLenAndType();
		this.scale = scale;
		this.alias = columnName.replaceAll("\\.", "_");
	}


	// *****************************************************************
	// Return the BE type that corresponds to the table column type.
	// ***************************************************************** 
	private void setLenAndType() {
		if (columnType.equalsIgnoreCase("bit")) {
			length = 1;
			type = BOOLEAN;
		} else if (columnType.equalsIgnoreCase("datetime") || columnType.equalsIgnoreCase("timestamp") || columnType.equalsIgnoreCase("smalldatetime")) {
			type = DATETIME;
		} else if (columnType.equalsIgnoreCase("date")) {
			type = DATE;
		} else if (columnType.equalsIgnoreCase("decimal") || columnType.equalsIgnoreCase("float") || columnType.equalsIgnoreCase("real")) {
			length = precision;
			type = DOUBLE;
		} else if (columnType.equalsIgnoreCase("int") || columnType.equalsIgnoreCase("int identity")) {
			length = precision;
			type = INTEGER;
		} else if (columnType.equalsIgnoreCase("mediumint") || columnType.equalsIgnoreCase("mediumint identity")) {
			length = precision;
			type = INTEGER;
		} else if (columnType.equalsIgnoreCase("money")) {
			length = precision;
			type = DOUBLE;
		} else if (columnType.equalsIgnoreCase("nchar")) {
			//length = length
			type = STRING;
		} else if (columnType.equalsIgnoreCase("numeric") || columnType.equalsIgnoreCase("numeric identity") || columnType.equalsIgnoreCase("numeric() identity")) {
			length = precision;
			type = DOUBLE;
		} else if (columnType.equalsIgnoreCase("nvarchar")) {
			//length = length
			type = STRING;
		} else if (columnType.equalsIgnoreCase("smallint") || columnType.equalsIgnoreCase("smallint identity")) {
			length = precision;
			type = INTEGER;
		} else if (columnType.equalsIgnoreCase("unsigned smallint") || columnType.equalsIgnoreCase("unsigned bigint") || columnType.equalsIgnoreCase("unsigned bigint")) {
			length = precision;
			type = INTEGER;
		} else if (columnType.equalsIgnoreCase("smallmoney")) {
			length = precision;
			type = DOUBLE;
		} else if (columnType.equalsIgnoreCase("tinyint") || columnType.equalsIgnoreCase("tinyint identity")) {
			length = precision;
			type = INTEGER;
		} else if (columnType.equalsIgnoreCase("uniqueidentifier") || columnType.equalsIgnoreCase("uniqueidentifierstr")) {
			//length = length
			type = STRING;
		} else if (columnType.equalsIgnoreCase("bigint") || columnType.equalsIgnoreCase("bigint identity")) {
			length = precision;
			type = LONG;
		} else if (columnType.equalsIgnoreCase("smallinteger") || columnType.equalsIgnoreCase("smallinteger identity") || columnType.equalsIgnoreCase("integer") || columnType.equalsIgnoreCase("integer identity")) {
			length = precision;
			type = INTEGER;
		} else if (columnType.equalsIgnoreCase("varbinary")) {
			//length = length
			type = STRING;
		} else if (columnType.equalsIgnoreCase("xml")) {
			length = precision;
			type = STRING;
		} else {
			length = 0;
			type = STRING;
		}
	}
}
