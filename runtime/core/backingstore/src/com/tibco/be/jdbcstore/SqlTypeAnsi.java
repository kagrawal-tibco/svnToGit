package com.tibco.be.jdbcstore;

import com.tibco.be.model.rdf.RDFTypes;

public class SqlTypeAnsi extends SqlType {
	
	static boolean multiByteFlag = false;
	static{
		multiByteFlag = Boolean.getBoolean("be.jdbc.multibyte.support");
	}

    public int getMaxIdentifierLength() {
        return 30;
    }

    public int getSqlTypeForPrimitiveType(int primitiveTypeIntId) {
        switch (primitiveTypeIntId) {
        case RDFTypes.BOOLEAN_TYPEID:
            return java.sql.Types.INTEGER;
        case RDFTypes.INTEGER_TYPEID:
            return java.sql.Types.INTEGER;
        case RDFTypes.LONG_TYPEID:
            return java.sql.Types.BIGINT;
        case RDFTypes.DOUBLE_TYPEID:
            return java.sql.Types.DOUBLE;
        case RDFTypes.STRING_TYPEID:
            return multiByteFlag ? java.sql.Types.NVARCHAR : java.sql.Types.VARCHAR;
            /* This is mapped to a complex type
            case RDFTypes.DATETIME_TYPEID:
                return java.sql.Types.TIMESTAMP;
             */
        default:
            System.err.println("Unsupported type integer Id '" + String.valueOf(primitiveTypeIntId) + "' to convert to SQL data type.");
            return multiByteFlag ? java.sql.Types.NVARCHAR : java.sql.Types.VARCHAR;
        }
    }

    public String getColumnTypeForPrimitiveType(String primitiveType, long size) {
        if (primitiveType.equalsIgnoreCase("String")) {
            if (size <= 1) {
                // FIX THIS VWC - do we need a smaller default size?
                // Should we get this default value from somewhere?
                return multiByteFlag ? ("nvarchar(255)") : ("char varying(255)");
            }
            /* disable this because this should not be specific to any db implementation
            if (size >= 4000) {
                return ("char varying(4000)");
            }
             */
            return multiByteFlag ? ("nvarchar(" + size + ")") : ("char varying(" + size + ")");
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
            return multiByteFlag ? ("nchar(1)") : ("char(1)");
        }
        else if (primitiveType.equalsIgnoreCase("clob")) {
            return ("clob");
        }
        else {
            System.err.println("Unsupported type '" + primitiveType + "' to convert to SQL column type.");
            new Exception("primitiveType").printStackTrace();
            return multiByteFlag ? "nvarchar(128)" : "char varying(128)";
        }
    }

    public String getColumnTypeForPrimitiveType(int primitiveTypeIntId, long size) {
        switch (primitiveTypeIntId) {
        case RDFTypes.BOOLEAN_TYPEID:
            return ("integer");
        case RDFTypes.INTEGER_TYPEID:
            return ("numeric(10)");
        case RDFTypes.LONG_TYPEID: // assume signed value
            return ("numeric(19)"); // TODO: Fix this even Integer is number(38)
        case RDFTypes.DOUBLE_TYPEID:
            return ("double precision");
        case RDFTypes.STRING_TYPEID:
            if (size <= 1)
                return multiByteFlag ? ("nvarchar(128)") : ("char varying(128)");
            if (size >= 4000)
                return multiByteFlag ? ("nvarchar(4000)") : ("char varying(4000)");
            return multiByteFlag ? ("nvarchar(" + size + ")") : ("char varying(" + size + ")");
        default:
            System.err.println("Unsupported type integer Id '" + String.valueOf(primitiveTypeIntId) + "' to convert to SQL column type.");
            return multiByteFlag ? "nvarchar(128)" : "char varying(128)";
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
