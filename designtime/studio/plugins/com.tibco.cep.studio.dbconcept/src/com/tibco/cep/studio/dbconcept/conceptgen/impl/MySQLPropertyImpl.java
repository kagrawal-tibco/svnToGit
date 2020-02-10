package com.tibco.cep.studio.dbconcept.conceptgen.impl;

import com.tibco.cep.studio.dbconcept.conceptgen.BaseProperty;

/**
 * 
 * @author slokhand
 * 
 *         Implementation of MySQLDBPropertyImpl specific to MySQL database
 * 
 */
public class MySQLPropertyImpl extends DBPropertyImpl {

	public MySQLPropertyImpl(String columnName, String columnType, int length,
			int precision, int scale, boolean nullable) {
		super(columnName, columnType, length, precision, scale, nullable);
		setType();
	}

	private void setType() {
		// as per 5.0 MySQL data type reference...
		if ("BIT".equalsIgnoreCase(columnType)) {

			length = 1;
			type = BaseProperty.BOOLEAN;

		} else if ("VARCHAR".equalsIgnoreCase(columnType)
				|| "CHAR".equalsIgnoreCase(columnType)
				|| "LONGVARCHAR".equalsIgnoreCase(columnType)) {

			type = BaseProperty.STRING;

		} else if ("TINYINT".equalsIgnoreCase(columnType)
				|| "TINYINT UNSIGNED".equalsIgnoreCase(columnType)
				|| "SMALLINT".equalsIgnoreCase(columnType)
				|| "SMALLINT UNSIGNED".equalsIgnoreCase(columnType)
				|| "MEDIUMINT".equalsIgnoreCase(columnType)
				|| "MEDIUMINT UNSIGNED".equalsIgnoreCase(columnType)
				|| "INT".equalsIgnoreCase(columnType)
				|| "YEAR".equalsIgnoreCase(columnType)
				|| "TIME".equalsIgnoreCase(columnType)
				|| "INTEGER".equalsIgnoreCase(columnType)) {

			length = precision;
			type = BaseProperty.INTEGER;

		} else if ("DECIMAL".equalsIgnoreCase(columnType)
				|| "DECIMAL UNSIGNED".equalsIgnoreCase(columnType)
				|| "FLOAT".equalsIgnoreCase(columnType)
				|| "FLOAT UNSIGNED".equalsIgnoreCase(columnType)
				|| "REAL".equalsIgnoreCase(columnType)
				|| "DOUBLE".equalsIgnoreCase(columnType)
				|| "NUMERIC".equalsIgnoreCase(columnType)
				|| "DOUBLE UNSIGNED".equalsIgnoreCase(columnType)) {

			length = precision;
			type = BaseProperty.DOUBLE;

		} else if ("DATETIME".equalsIgnoreCase(columnType)
				|| "TIMESTAMP".equalsIgnoreCase(columnType)) {

			type = BaseProperty.DATETIME;

		} else if ("DATE".equalsIgnoreCase(columnType)) {

			type = DATE;

		} else if ("BIGINT".equalsIgnoreCase(columnType)
				|| "INT UNSIGNED".equalsIgnoreCase(columnType)
				|| "BIGINT UNSIGNED".equalsIgnoreCase(columnType)) {
			length = precision;
			type = BaseProperty.LONG;
		} else {
			length = 0;
			type = BaseProperty.STRING;
		}

	}

}
