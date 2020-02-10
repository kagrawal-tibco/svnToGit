package com.tibco.cep.studio.dbconcept.conceptgen.impl;

import com.tibco.cep.studio.dbconcept.conceptgen.BaseProperty;

/**
 * 
 * @author schelwa
 * 
 * handles types specific to Oracle
 *
 */
public class OracleDBPropertyImpl extends DBPropertyImpl {
	

	public OracleDBPropertyImpl(String columnName,
			String columnType, int length, int precision, int scale,
			boolean nullable) {
		super(columnName, columnType, length, precision, scale, nullable);
		setType();
	}

	private void setType() {

		if ("VARCHAR2".equalsIgnoreCase(columnType)
				|| "CHAR".equalsIgnoreCase(columnType)
				|| "NVARCHAR2".equalsIgnoreCase(columnType)
				|| "NCHAR".equalsIgnoreCase(columnType)) {
			
			type = BaseProperty.STRING;
			
		} else if ("DATE".equalsIgnoreCase(columnType)) {
			type = BaseProperty.DATE;
		} else if ("DATETIME".equalsIgnoreCase(columnType)) {
			type = BaseProperty.DATETIME;
		} else if ("FLOAT".equalsIgnoreCase(columnType)) {
			type = BaseProperty.DOUBLE;
		} else if ("DOUBLE".equalsIgnoreCase(columnType)) {
			type = BaseProperty.DOUBLE;
		} else if ("INTEGER".equalsIgnoreCase(columnType)
				|| "INT".equalsIgnoreCase(columnType)) {
			type = BaseProperty.INTEGER;
		} else if ("NUMBER".equalsIgnoreCase(columnType)) {
			if (scale != 0) {
				type = BaseProperty.DOUBLE;
			} else if(precision > 0) { 
				
				//valid int number is in range -2,147,483,648 to 2,147,483,647 (10 digit number)
				//valid long number is in range -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807 (19 digit number)
				
				if(precision > 18){
					type = BaseProperty.DOUBLE;
				} else if(precision > 9){ 
					type = BaseProperty.LONG;
				} else {
					type = BaseProperty.INTEGER;
				}
				
			} else {
				type = BaseProperty.DOUBLE;
			}
		} else if ("BOOLEAN".equalsIgnoreCase(columnType)
				|| "BOOL".equalsIgnoreCase(columnType)) {
			type = BaseProperty.BOOLEAN;
		} else if (columnType.contains("INTERVAL")) {
			type = BaseProperty.INTEGER;
		}  else if (columnType.contains("TIMESTAMP")) {
			type = BaseProperty.INTEGER;
			type = BaseProperty.DATETIME;
		} else {
			type = BaseProperty.STRING;
		}
	}
}
