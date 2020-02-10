package com.tibco.be.jdbcstore;

import java.util.Arrays;
import java.util.List;

import com.tibco.be.model.rdf.RDFTypes;

public class SqlTypePostgres extends SqlType {

	@Override
	public String formatDropTableSql(String tableName) {
		return "DROP TABLE IF EXISTS " + tableName;
	}

	@Override
	public String getdeleteAllCommand(String tableName) {
		return "DELETE FROM " + tableName;
	}

	@Override
	public int getMaxIdentifierLength() {
		return 63;
	}

	@Override
	public int getSqlTypeForPrimitiveType(int primitiveTypeIntId) {
		switch (primitiveTypeIntId) {
		case RDFTypes.BOOLEAN_TYPEID:
			return java.sql.Types.NUMERIC;
		case RDFTypes.INTEGER_TYPEID:
			return java.sql.Types.INTEGER;
		case RDFTypes.LONG_TYPEID:
			return java.sql.Types.BIGINT;
		case RDFTypes.DOUBLE_TYPEID:
			return java.sql.Types.DOUBLE;
		case RDFTypes.STRING_TYPEID:
			return java.sql.Types.VARCHAR;
		/*
		 * This is mapped to a complex type case RDFTypes.DATETIME_TYPEID:
		 * return java.sql.Types.TIMESTAMP;
		 */
		default:
			System.err.println("Unsupported type integer Id '" + String.valueOf(primitiveTypeIntId)
					+ "' to convert to SQL data type.");
			return java.sql.Types.VARCHAR;
		}
	}

	@Override
	public String getColumnTypeForPrimitiveType(String primitiveType, long size) {
		if (RDBMSType.sUseAnsiType) {
			if (primitiveType.equalsIgnoreCase("String") && size > 8000) {
				return ("text");
			} else if (primitiveType.equalsIgnoreCase("Integer")) {
				return ("int");
			} else if (primitiveType.equalsIgnoreCase("boolean")) {
				return ("numeric(1)");
			} else if (primitiveType.equalsIgnoreCase("blob")) {
				return ("bytea");
			} else if (primitiveType.equalsIgnoreCase("long")) {
				return ("bigint");
			}
			return RDBMSType.sSqlTypeAnsi.getColumnTypeForPrimitiveType(primitiveType, size);
		}
		if (primitiveType.equalsIgnoreCase("String")) {
			if (size <= 1)
				return ("varchar(255)");
			if (size > 4000)
				return ("text");
			return ("varchar(" + size + ")");
		} else if (primitiveType.equalsIgnoreCase("Integer")) {
			return ("int");
		} else if (primitiveType.equalsIgnoreCase("long")) {
			return ("bigint");
		} else if (primitiveType.equalsIgnoreCase("double")) {
			return ("double precision");
		} else if (primitiveType.equalsIgnoreCase("boolean")) {
			return ("numeric(1)");
		} else if (primitiveType.equalsIgnoreCase("blob")) {
			return ("bytea");
		} else if (primitiveType.equalsIgnoreCase("timestamp")) {
			return ("timestamp");
		} else if (primitiveType.equalsIgnoreCase("char")) {
			return ("char(1)");
		} else {
			System.err.println("Unsupported type '" + primitiveType + "' to convert to SQL column type.");
			return "varchar(128)";
		}
	}

	@Override
	public String getColumnTypeForPrimitiveType(int primitiveTypeIntId, long size) {
		switch (primitiveTypeIntId) {
		case RDFTypes.BOOLEAN_TYPEID:
			return ("numeric(1)");
		case RDFTypes.INTEGER_TYPEID:
			return ("integer");
		case RDFTypes.LONG_TYPEID: // assume signed value
			return ("bigint)");
		case RDFTypes.DOUBLE_TYPEID:
			return ("double precision");
		case RDFTypes.STRING_TYPEID:
			if (size <= 1)
				return ("varchar(128)");
			if (size >= 4000)
				return ("text");
			return ("varchar(" + size + ")");
		default:
			System.err.println("Unsupported type integer Id '" + String.valueOf(primitiveTypeIntId)
					+ "' to convert to SQL column type.");
			return "varchar(128)";
		}
	}

	@Override
	public List getMatchingTypes(String primitiveType) {
		BOOLEAN_TYPES = Arrays.asList(new String[] { "NUMERIC, INTEGER, SMALLINT" });
		INTEGER_TYPES = Arrays.asList(new String[] { "INT", "INTEGER" });
		LONG_TYPES = Arrays.asList(new String[] { "BIGINT" });
		return super.getMatchingTypes(primitiveType);
	}

	@Override
	public List getCompatibleTypes(String primitiveType) {
		BLOB_COMPATIBLES = Arrays.asList(new String[] { "BYTEA" });
		BOOLEAN_COMPATIBLES = Arrays.asList(new String[] { "NUMERIC, INTEGER, SMALLINT" });
		CHAR_COMPATIBLES = Arrays.asList(new String[] { "CHAR", "VARCHAR" });
		CLOB_COMPATIBLES = Arrays.asList(new String[] { "TEXT" });
		DOUBLE_COMPATIBLES = Arrays.asList(new String[] { "DOUBLE", "REAL", "NUMERIC" });
		INTEGER_COMPATIBLES = Arrays.asList(new String[] { "INTEGER", "SMALLINT", "NUMERIC" });
		LONG_COMPATIBLES = Arrays.asList(new String[] { "BIGINT" });
		STRING_COMPATIBLES = Arrays.asList(new String[] { "TEXT", "CHAR", "VARCHAR" });
		TIMESTAMP_COMPATIBLES = Arrays.asList(new String[] { "TIMESTAMP", "TIME", "DATE" });
		return super.getMatchingTypes(primitiveType);
	}

	@Override
	public String formatDropIndexSql(String tableName, String indexName, String columns, boolean isUnique) {
		if (isUnique) {
			return "ALTER TABLE " + tableName + " DROP CONSTRAINT " + tableName + "_pkey";
		} else {
			return "DROP INDEX " + indexName;
		}
	}

	@Override
	public String sanitizeNameIfNeeded(String columnName) {
		return null;
	}

	@Override
	public String getSequenceCheckQuery(String sequenceName, int increment) {
		return "SELECT sequence_name FROM information_schema.sequences WHERE sequence_name='" + sequenceName + "'";
	}

	@Override
	public String getSequenceCreateQuery(String sequenceName, long minValue, long maxValue, long start, int increment) {
		StringBuffer query = new StringBuffer("CREATE SEQUENCE " + sequenceName);
		query.append(" START WITH " + start);
		if (minValue >= 0) {
			query.append(" MINVALUE " + minValue);
		} else {
			query.append(" NO MINVALUE ");
		}
		if (maxValue > 0) {
			query.append(" MAXVALUE " + maxValue);
		} else {
			query.append(" NO MAXVALUE ");
		}
		if (increment > 1) {
			query.append(" INCREMENT BY " + increment);
		}
		query.append(" CACHE 1 CYCLE");
		return query.toString();
	}

	@Override
	public String getSequenceDropQuery(String sequenceName) {
		return "DROP SEQUENCE " + sequenceName;
	}

	@Override
	public String getSequenceNextQuery(String sequenceName, boolean forUpdate) {
		if (forUpdate) {
			return null; // No query required to update
		}
		return "SELECT nextVal('" + sequenceName + "')";
	}

	@Override
	public String formatObjectTableDeleteTimeCondition() {
		return "SELECT DATE_PART('day', current_date::timestamp - '1970-01-02'::timestamp)  * (86400000)";
	}
}
