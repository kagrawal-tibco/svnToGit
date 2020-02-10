package com.tibco.cep.studio.dbconcept.conceptgen.impl;

import com.tibco.cep.studio.dbconcept.conceptgen.BaseProperty;

public class PostgresPropertyImpl extends DBPropertyImpl {

	public PostgresPropertyImpl(String columnName, String columnType, int length, int precision, int scale,
			boolean nullable) {
		super(columnName, columnType, length, precision, scale, nullable);
		setType();
	}

	private void setType() {
		if ("BOOLEAN".equalsIgnoreCase(columnType)) {
			type = BaseProperty.BOOLEAN;

		} else if ("CHARACTER".equalsIgnoreCase(columnType) || "TEXT".equalsIgnoreCase(columnType)
				|| "VARCHAR".equalsIgnoreCase(columnType) || "CHARACTER VARYING".equalsIgnoreCase(columnType)) {
			type = BaseProperty.STRING;

		} else if ("INTEGER".equalsIgnoreCase(columnType) || "SMALLINT".equalsIgnoreCase(columnType)
				|| "SERIAL".equalsIgnoreCase(columnType) || "INT".equalsIgnoreCase(columnType)) {
			length = precision;
			type = BaseProperty.INTEGER;

		} else if ("DECIMAL".equalsIgnoreCase(columnType) || "DOUBLE PRECISION".equalsIgnoreCase(columnType)
				|| "REAL".equalsIgnoreCase(columnType) || "DOUBLE".equalsIgnoreCase(columnType)
				|| "NUMERIC".equalsIgnoreCase(columnType)) {
			length = precision;
			type = BaseProperty.DOUBLE;

		} else if ("TIMESTAMP".equalsIgnoreCase(columnType)) {
			type = BaseProperty.DATETIME;
		} else if ("DATE".equalsIgnoreCase(columnType)) {
			type = DATE;
		} else if ("BIGINT".equalsIgnoreCase(columnType) || "BIGSERIAL".equalsIgnoreCase(columnType)) {
			length = precision;
			type = BaseProperty.LONG;
		} else {
			length = 0;
			type = BaseProperty.STRING;
		}

	}

}
