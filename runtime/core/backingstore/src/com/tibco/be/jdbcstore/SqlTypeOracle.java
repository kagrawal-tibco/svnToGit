package com.tibco.be.jdbcstore;

import java.util.Arrays;
import java.util.List;

import com.tibco.be.model.rdf.RDFTypes;

public class SqlTypeOracle extends SqlType {

    public int getMaxIdentifierLength() {
        return 30;
    }

    @Override
    public String getDropColumnCommand() {
        return " DROP ";
    }
    
    @Override
    public String getModifyColumnCommand() {
        return " MODIFY ";
    }
    
    @Override
    public String getCommitCommand() {
        return "COMMIT";
    }
    
    @Override
    public String getBeginDefinitionMarker() {
        return "( ";
    }

    @Override
    public String getEndDefinitionMarker() {
        return " )";
    }

    @Override
    public List getMatchingTypes(String primitiveType) {
        DOUBLE_TYPES = Arrays.asList(new String[] { "DOUBLE", "FLOAT", "BINARY_DOUBLE" });
        STRING_TYPES = Arrays.asList(new String[] { "STRING", "CHAR", "VARCHAR", "VARCHAR2", "CLOB" });
        TIMESTAMP_TYPES = Arrays.asList(new String[] { "TIMESTAMP(3)", "TIMESTAMP(6)" });
        return super.getMatchingTypes(primitiveType);
    }

    @Override
    public List getCompatibleTypes(String primitiveType) {
        return super.getCompatibleTypes(primitiveType);
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
            return java.sql.Types.VARCHAR;
            /*
             * This is mapped to a complex type case RDFTypes.DATETIME_TYPEID:
             * return java.sql.Types.TIMESTAMP;
             */
        default:
            System.err.println("Unsupported type integer Id '" + String.valueOf(primitiveTypeIntId) + "' to convert to SQL data type.");
            return java.sql.Types.VARCHAR;
        }
    }

    public String getColumnTypeForPrimitiveType(String primitiveType, long size) {
        // Oracle supports ansi data type specifications
        // FIX THIS - Need to add logic to create clob/varchar based on size
        if (RDBMSType.sUseAnsiType) {
            if (primitiveType.equalsIgnoreCase("String") && size > 4000) {
            	if ((!RDBMSType.getExpandMaxStringSize() && size > 4000) || (RDBMSType.getExpandMaxStringSize() && size > 32767)) {
            		primitiveType = "clob";
            	}
            } else if (primitiveType.equalsIgnoreCase("boolean")) {
                return ("number(1)");
            }
            return RDBMSType.sSqlTypeAnsi.getColumnTypeForPrimitiveType(primitiveType, size);
        }
        if (primitiveType.equalsIgnoreCase("String")) {
            if (size <= 1) {
                // FIX THIS VWC - do we need a smaller default size?
                return ("varchar2(255)");
            }
            // FIX THIS - Need to turn string > 4000 into clob
            // Should fetch this size limit from db directly
            if ((!RDBMSType.getExpandMaxStringSize() && size > 4000) || (RDBMSType.getExpandMaxStringSize() && size > 32767)) {
                return ("clob");
            }
            return ("varchar2(" + size + ")");
        } else if (primitiveType.equalsIgnoreCase("integer")) {
            return ("number(10)");
        } else if (primitiveType.equalsIgnoreCase("number")) {
            return ("number"); // TODO: Fix this even Integer is number(38)
        } else if (primitiveType.equalsIgnoreCase("long")) {
            return ("number(19)"); // TODO: Fix this even Integer is number(38)
        } else if (primitiveType.equalsIgnoreCase("double")) {
            return ("binary_double");
        } else if (primitiveType.equalsIgnoreCase("boolean")) {
            return ("number(1)");
        } else if (primitiveType.equalsIgnoreCase("blob")) {
            return ("blob");
        } else if (primitiveType.equalsIgnoreCase("timestamp")) {
            // return ("timestamp(3)");
            return ("timestamp");
        } else if (primitiveType.equalsIgnoreCase("char")) {
            return ("char(1)");
        } else {
            System.err.println("Unsupported type '" + primitiveType + "' to convert to SQL column type.");
            return "varchar2(128)";
        }
    }

    public String getColumnTypeForPrimitiveType(int primitiveTypeIntId, long size) {
        switch (primitiveTypeIntId) {
        case RDFTypes.BOOLEAN_TYPEID:
            return ("integer");
        case RDFTypes.INTEGER_TYPEID:
            return ("integer");
        case RDFTypes.LONG_TYPEID: // assume signed value
            return ("number(19,0)"); // TODO: Fix this even Integer is number(38)
        case RDFTypes.DOUBLE_TYPEID:
            return ("float");
        case RDFTypes.STRING_TYPEID:
            if (size <= 1)
                return ("varchar2(128)");
            if (size >= 4000)
                return ("varchar2(4000)");
            return ("varchar2(" + size + ")");
        default:
            System.err.println("Unsupported type integer Id '" + String.valueOf(primitiveTypeIntId) + "' to convert to SQL column type.");
            return "varchar2(128)";
        }
    }

    /**
     * If underlying database has name restrictions for the default column-name;
     * then sanitize the column name and return a valid one. If the default name
     * is acceptable, then return 'null' to signal no change was necessary.
     * Note: Ignore name-length restrictions here, since that is checked later.
     */
    public String sanitizeNameIfNeeded(String columnName) {
        /*
         * Valid names for Oracle: - Begin with a letter - Consist only of
         * alphanumeric and the special characters ($_#) Note: Length (max 30)
         * is checked outside this function
         */
        StringBuilder newName = new StringBuilder();
        boolean sanitized = false;
        if (Character.isLetter(columnName.charAt(0)) == false) {
            sanitized = true;
            newName.append("c_");
        }
        for (int i = 0; i < columnName.length(); i++) {
            char ch = columnName.charAt(i);
            if (Character.isLetterOrDigit(ch) || ch == '_' || ch == '$' || ch == '#') {
                newName.append(ch);
            } else {
                sanitized = true;
                newName.append("_");
            }
        }
        if (sanitized) {
            return newName.toString();
        } else {
            return null;
        }
    }
    
    public String getSequenceCheckQuery(String sequenceName, int increment) {
        return "SELECT sequence_name FROM user_sequences WHERE sequence_name='" + sequenceName + "'";
    }
    
    public String getSequenceCreateQuery(String sequenceName, long minValue, long maxValue, long start, int increment) {
        StringBuffer query = new StringBuffer("CREATE SEQUENCE " + sequenceName);
        query.append(" START WITH " + start);
        if (minValue >= 0) {
            query.append(" MINVALUE " + minValue);
        } else {
            query.append(" NOMINVALUE ");
        }
        if (maxValue > 0) {
            query.append(" MAXVALUE " + maxValue);
        } else {
            query.append(" NOMAXVALUE ");
        }
        if (increment > 1) {
            query.append(" INCREMENT BY " + increment);
        }
        query.append(" NOCACHE CYCLE");
        return query.toString();
    }
    
    public String getSequenceDropQuery(String sequenceName) {
        return "DROP SEQUENCE " + sequenceName;
    }

    public String getSequenceNextQuery(String sequenceName, boolean forUpdate) {
        if (forUpdate) {
            return null; // No query required to update
        }
        return "SELECT " + sequenceName + ".nextVal FROM dual";
    }
    
    // Optimizer hints!!!
    // http://download.oracle.com/docs/cd/A87860_01/doc/server.817/a76992/hints.htm
}
