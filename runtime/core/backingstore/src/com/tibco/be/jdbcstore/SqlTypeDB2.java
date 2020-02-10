package com.tibco.be.jdbcstore;

import java.util.Arrays;
import java.util.List;

import com.tibco.be.model.rdf.RDFTypes;

public class SqlTypeDB2 extends SqlType {

    public int getMaxIdentifierLength() {
        return 128;
    }

    //ALTER TABLE D_Table ALTER COLUMN ABC SET DATA TYPE NUMBER (DB2 syntax requires 'SET DATA TYPE') 
    //REORG TABLE D_Table (might be needed after many alterations)
    public String getColumnModificationTypeCommand() {
        return "SET DATA TYPE";
    }
    
    public String formatObjectTableDeleteTimeCondition() {
        return "SELECT (DAYS(current date) - DAYS(TO_DATE('1970-01-02','YYYY-MM-DD'))) * (86400000) FROM sysibm.sysdummy1";
    }

    @Override
    public List getMatchingTypes(String primitiveType) {
        BOOLEAN_TYPES = Arrays.asList(new String[] { "SMALLINT" });
        STRING_TYPES = Arrays.asList(new String[] { "CHAR", "VARCHAR", "CLOB" });
        LONG_TYPES = Arrays.asList(new String[] {"BIGINT"});
        return super.getMatchingTypes(primitiveType);
    }

    @Override
    public List getCompatibleTypes(String primitiveType) {
        INTEGER_COMPATIBLES = Arrays.asList(new String[] { "INTEGER", "NUMBER", "NUMERIC" });
        STRING_COMPATIBLES = Arrays.asList(new String[] { "STRING", "CHAR", "VARCHAR", "INTEGER", "NUMBER", "FLOAT" });
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
            /* This is mapped to a complex type
            case RDFTypes.DATETIME_TYPEID:
                return java.sql.Types.TIMESTAMP;
             */
        default:
            System.err.println("Unsupported type integer Id '" + String.valueOf(primitiveTypeIntId) + "' to convert to SQL data type.");
            return java.sql.Types.VARCHAR;
        }
    }

    public String getColumnTypeForPrimitiveType(String primitiveType, long size) {
        // DB2 may support ansi data type specifications in the future
        // FIX THIS - Need to add logic to create clob/varchar based on size
        if (RDBMSType.sUseAnsiType) {
            if (primitiveType.equalsIgnoreCase("String") && size > 4000) {
                primitiveType = "clob";
            }
            return RDBMSType.sSqlTypeAnsi.getColumnTypeForPrimitiveType(primitiveType, size);
        }
        if (primitiveType.equalsIgnoreCase("String")) {
            if (size <= 1)
                return ("varchar(255)");
            if (size > 4000)
                return ("clob");
            return ("varchar(" + size + ")");
        }
        else if (primitiveType.equalsIgnoreCase("Integer")) {
            return ("integer");
        }
        else if (primitiveType.equalsIgnoreCase("long")) {
            return ("bigint"); // TODO: Fix this even Integer is decimal(38)
        }
        else if (primitiveType.equalsIgnoreCase("double")) {
            return ("double");
        }
        else if (primitiveType.equalsIgnoreCase("boolean")) {
            return ("smallint");
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
        else {
            System.err.println("Unsupported type '" + primitiveType + "' to convert to SQL column type.");
            return "varchar(128)";
        }
    }

    public String getColumnTypeForPrimitiveType(int primitiveTypeIntId, long size) {
        switch (primitiveTypeIntId) {
        case RDFTypes.BOOLEAN_TYPEID:
            return ("smallint");
        case RDFTypes.INTEGER_TYPEID:
            return ("integer");
        case RDFTypes.LONG_TYPEID: // assume signed value
            return ("bigint"); // TODO: Fix this even Integer is decimal(38)
        case RDFTypes.DOUBLE_TYPEID:
            return ("double");
        case RDFTypes.STRING_TYPEID:
            if (size <= 1)
                return ("varchar(255)");
            if (size >= 4000)
                return ("clob");
            return ("varchar(" + size + ")");
        default:
            System.err.println("Unsupported type integer Id '" + String.valueOf(primitiveTypeIntId) + "' to convert to SQL column type.");
            return "varchar(128)";
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
        return "SELECT seqname FROM syscat.sequences WHERE seqname='" + sequenceName + "'";
    }

    @Override
    public String getSequenceCreateQuery(String sequenceName, long minValue, long maxValue, long start, int increment) {
        StringBuffer query = new StringBuffer("CREATE SEQUENCE " + sequenceName);
        query.append(" AS BIGINT");
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

    @Override
    public String getSequenceDropQuery(String sequenceName) {
        return "DROP SEQUENCE " + sequenceName + " RESTRICT";
    }

    @Override
    public String getSequenceNextQuery(String sequenceName, boolean forUpdate) {
        if (forUpdate) {
            return null; // No query required to update
        }
        return "VALUES NEXTVAL for " + sequenceName;
    }
}
