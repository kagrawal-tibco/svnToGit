package com.tibco.cep.studio.dbconcept.conceptgen.impl;

import com.tibco.cep.studio.dbconcept.conceptgen.BaseProperty;

/**
 * 
 * DB Property which handles types specific to DB2
 * 
 */
public class DB2DBPropertyImpl extends DBPropertyImpl {

	public DB2DBPropertyImpl(String columnName, String columnType, int length,
			int precision, int scale, boolean nullable) {
		super(columnName, columnType, length, precision, scale, nullable);
		setType();
	}

	private void setType() {

		if ("VARCHAR".equalsIgnoreCase(columnType)
				|| "CHAR".equalsIgnoreCase(columnType)
				|| "CLOB".equalsIgnoreCase(columnType)) {

			type = BaseProperty.STRING;

		} else if ("DATE".equalsIgnoreCase(columnType)) {
			type = BaseProperty.DATE;
		} else if ("TIMESTAMP".equalsIgnoreCase(columnType)) {
			type = BaseProperty.DATETIME;
		} else if ("DECIMAL".equalsIgnoreCase(columnType)) {
			type = BaseProperty.DOUBLE;
		} else if ("FLOAT".equalsIgnoreCase(columnType)
				|| "DOUBLE".equalsIgnoreCase(columnType)
				|| "REAL".equalsIgnoreCase(columnType)) {
			type = BaseProperty.DOUBLE;
		} else if ("SMALLINT".equalsIgnoreCase(columnType)
				|| "INTEGER".equalsIgnoreCase(columnType)) {
			type = BaseProperty.INTEGER;
		} else if ("NUMERIC".equalsIgnoreCase(columnType)
				|| "BIGINT".equalsIgnoreCase(columnType)) {
			type = BaseProperty.LONG;
		} else {
			type = BaseProperty.STRING;
		}
	}
}
