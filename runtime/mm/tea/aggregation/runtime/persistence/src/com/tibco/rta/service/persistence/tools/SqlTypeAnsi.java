package com.tibco.rta.service.persistence.tools;

import com.tibco.rta.model.DataType;


public class SqlTypeAnsi extends SqlType {

    public int getMaxIdentifierLength() {
        return 30;
    }

    public int getSqlTypeForPrimitiveType(DataType primitiveType) {
        switch (primitiveType) {
        case BOOLEAN:
            return java.sql.Types.CHAR;
        case INTEGER:
            return java.sql.Types.INTEGER;
        case LONG:
            return java.sql.Types.BIGINT;
        case DOUBLE:
            return java.sql.Types.DOUBLE;
        case STRING:
            return java.sql.Types.VARCHAR;
        case CLOB:
        	return java.sql.Types.CLOB;
            /* This is mapped to a complex type
            case RDFTypes.DATETIME_TYPEID:
                return java.sql.Types.TIMESTAMP;
             */
        default:
            System.err.println("Unsupported type integer Id '" + String.valueOf(primitiveType) + "' to convert to SQL data type.");
            return java.sql.Types.VARCHAR;
        }
    }

    public String getColumnTypeForPrimitiveType(String primitiveType, long size) {
        if (primitiveType.equalsIgnoreCase("String")) {
            if (size <= 1) {
                // FIX THIS VWC - do we need a smaller default size?
                // Should we get this default value from somewhere?
                return ("char varying(255)");
            }
            /* disable this because this should not be specific to any db implementation
            if (size >= 4000) {
                return ("char varying(4000)");
            }
             */
            return ("char varying(" + size + ")");
        }
        else if (primitiveType.equalsIgnoreCase("integer")) {
            return ("numeric(10)");
        }
        else if (primitiveType.equalsIgnoreCase("number")) {
            return ("numeric"); // TODO: Fix this even Integer is number(38)
        }
        else if (primitiveType.equalsIgnoreCase("long")) {
            return ("numeric(19)"); // TODO: Fix this even Integer is number(38)
        }
        else if (primitiveType.equalsIgnoreCase("double")) {
            // use float or double precision
            return ("double precision");
        }
        else if (primitiveType.equalsIgnoreCase("boolean")) {
            return ("bit");
        }
        else if (primitiveType.equalsIgnoreCase("blob")) {
            return ("blob");
        }
        else if (primitiveType.equalsIgnoreCase("timestamp")) {
            return ("timestamp");
        }
        else if (primitiveType.equalsIgnoreCase("char")) {
            return ("char(1)");
        }
        else if (primitiveType.equalsIgnoreCase("clob")) {
            return ("clob");
        }
        else {
            System.err.println("Unsupported type '" + primitiveType + "' to convert to SQL column type.");
            new Exception("primitiveType").printStackTrace();
            return "char varying(128)";
        }
    }

    public String getColumnTypeForPrimitiveType(DataType primitiveType, long size) {
        switch (primitiveType) {
        case BOOLEAN:
            return ("integer");
        case INTEGER:
            return ("numeric(10)");
        case LONG: // assume signed value
            return ("numeric(19)"); // TODO: Fix this even Integer is number(38)
        case DOUBLE:
            return ("double precision");
        case STRING:
            if (size <= 1)
                return ("char varying(128)");
            if (size >= 4000)
                return ("char varying(4000)");
            return ("char varying(" + size + ")");
        case CLOB:
        	return ("clob");
        default:
            System.err.println("Unsupported type integer Id '" + String.valueOf(primitiveType) + "' to convert to SQL column type.");
            return "char varying(128)";
        }
    }

    /**
     * If underlying database has name restrictions for the default column-name;
     * then sanitize the column name and return a valid one.
     * If the default name is acceptable, then return 'null' to signal no change
     * was necessary.
     * Note: Ignore name-length restrictions here, since that is checked later.
     */
    public String sanitizeNameIfNeeded(String columnName) {
        return null;
    }

    @Override
    public String getSequenceCheckQuery(String sequenceName, int increment) {
        return null;
    }

    @Override
    public String getSequenceCreateQuery(String sequenceName, long minValue, long maxValue, long start, int increment) {
        return null;
    }

    @Override
    public String getSequenceDropQuery(String sequenceName) {
        return null;
    }

    @Override
    public String getSequenceNextQuery(String sequenceName, boolean forUpdate) {
        return null;
    }
}
