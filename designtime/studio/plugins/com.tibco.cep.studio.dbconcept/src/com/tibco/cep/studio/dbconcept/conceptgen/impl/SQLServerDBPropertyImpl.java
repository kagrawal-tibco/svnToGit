package com.tibco.cep.studio.dbconcept.conceptgen.impl;

/**
 * 
 * @author bgokhale
 * 
 * Handles types specific to SQLServer
 *
 */
public class SQLServerDBPropertyImpl extends DBPropertyImpl {

	int scale;
	public SQLServerDBPropertyImpl(String entityName, String columnName,
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
			
		} else if (columnType.equalsIgnoreCase("datetime")) {
			type = DATETIME;
		} else if (columnType.equalsIgnoreCase("smalldatetime")) {
			type = DATETIME;
		} else if (columnType.equalsIgnoreCase("decimal")) {
			length = precision;
			type = DOUBLE;
		} else if (columnType.equalsIgnoreCase("float")) {
			length = precision;
			type = DOUBLE;
		} else if (columnType.equalsIgnoreCase("real")) {
			length = precision;
			type = DOUBLE;
		} else if (columnType.equalsIgnoreCase("int")) {
			length = precision;
			type = INTEGER;
		} else if (columnType.equalsIgnoreCase("money")) {
			length = precision;
			type = DOUBLE;
		} else if (columnType.equalsIgnoreCase("nchar")) {
			//length = length
			type = STRING;
		} else if (columnType.equalsIgnoreCase("numeric")) {
			length = precision;
			type = DOUBLE;
		} else if (columnType.equalsIgnoreCase("nvarchar")) {
			//length = length
			type = STRING;
		} else if (columnType.equalsIgnoreCase("smallint")) {
			length = precision;
			type = INTEGER;
		} else if (columnType.equalsIgnoreCase("smallmoney")) {
			length = precision;
			type = DOUBLE;
		} else if (columnType.equalsIgnoreCase("tinyint")) {
			length = precision;
			type = INTEGER;
		} else if (columnType.equalsIgnoreCase("uniqueidentifier")) {
			//length = length
			type = STRING;
		} else if (columnType.equalsIgnoreCase("bigint")) {
			length = precision;
			type = LONG;
		} else if (columnType.equalsIgnoreCase("varbinary")) {
			//length = length
			type = STRING;
		} else if (columnType.equalsIgnoreCase("varchar")) {
			//length == length
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
