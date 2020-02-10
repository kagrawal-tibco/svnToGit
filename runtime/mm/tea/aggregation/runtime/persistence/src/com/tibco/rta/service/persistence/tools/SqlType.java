package com.tibco.rta.service.persistence.tools;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.tibco.rta.model.DataType;


public abstract class SqlType {
    // Override these values, according to the underlying database 
    // Primitive types and their equivalents as represented by database
    // e.g.: FLOAT represents a Double
    public List<String> BLOB_TYPES = Arrays.asList(new String[] { "BLOB" });
    public List<String> BOOLEAN_TYPES = Arrays.asList(new String[] { "NUMBER" });
    public List<String> CHAR_TYPES = Arrays.asList(new String[] { "CHAR" });
    public List<String> CLOB_TYPES = Arrays.asList(new String[] { "CLOB" });
    public List<String> DOUBLE_TYPES = Arrays.asList(new String[] { "DOUBLE", "FLOAT" });
    public List<String> INTEGER_TYPES = Arrays.asList(new String[] { "INTEGER", "NUMBER", "NUMERIC" });
    public List<String> LONG_TYPES = Arrays.asList(new String[] { "DECIMAL", "NUMBER", "NUMERIC" });
    public List<String> STRING_TYPES = Arrays.asList(new String[] { "STRING", "CHAR", "VARCHAR" });
    public List<String> TIMESTAMP_TYPES = Arrays.asList(new String[] { "TIMESTAMP" });
    
    // Override these values, according to the underlying database 
    // Primitive types and their compatibles as represented by database
    // e.g.: FLOAT data type can be altered to a String
    public List<String> BLOB_COMPATIBLES = Arrays.asList(new String[] { "BLOB" });
    public List<String> BOOLEAN_COMPATIBLES = Arrays.asList(new String[] { "NUMBER" });
    public List<String> CHAR_COMPATIBLES = Arrays.asList(new String[] { "CHAR", "VARCHAR"});
    public List<String> CLOB_COMPATIBLES = Arrays.asList(new String[] { "CLOB" });
    public List<String> DOUBLE_COMPATIBLES = Arrays.asList(new String[] { "DOUBLE", "FLOAT", "NUMBER" });
    public List<String> INTEGER_COMPATIBLES = Arrays.asList(new String[] { "INTEGER", "NUMBER", "NUMERIC" });
    public List<String> LONG_COMPATIBLES = Arrays.asList(new String[] { "DECIMAL", "NUMBER", "NUMERIC" });
    public List<String> STRING_COMPATIBLES = Arrays.asList(new String[] { "STRING", "CHAR", "VARCHAR", "NUMBER", "FLOAT" });
    public List<String> TIMESTAMP_COMPATIBLES = Arrays.asList(new String[] { "TIMESTAMP" });     
    
    public static boolean optimizeReads = false;
    public static boolean optimizeWrites = false;
    
    public abstract int getMaxIdentifierLength();
    public abstract int getSqlTypeForPrimitiveType(DataType primitiveType);
    public abstract String getColumnTypeForPrimitiveType(String primitiveTypeId, long size);
    public abstract String getColumnTypeForPrimitiveType(DataType primitiveType, long size);
    public abstract String sanitizeNameIfNeeded(String columnName);

    public abstract String getSequenceCheckQuery(String sequenceName, int increment);
    public abstract String getSequenceCreateQuery(String sequenceName, long minValue, long maxValue, long start, int increment);
    public abstract String getSequenceDropQuery(String sequenceName);
    public abstract String getSequenceNextQuery(String sequenceName, boolean forUpdate);

    public final boolean columnTypeMatches(String primitiveType, long size, String currentType, long currentSize) {
        // System.err.println("Comparing " + primitiveType + " [" + size + "]  &  " + currentType + " [" + currentSize + "]");
        boolean matching = getMatchingTypes(primitiveType).contains(currentType.toUpperCase());
        if (matching) {
            // Additional checks goes here
            if (primitiveType.equalsIgnoreCase("String") && (size > 0) && (size < 4000)) {
                matching = (size <= currentSize);
            } 
        }
        if (matching == false) {
            // Used for debugging
            //System.err.println("Comparing " + primitiveType + " [" + size + "]  &  " + currentType + " [" + currentSize + "]");
            //System.err.println("Compared to: " + getMatchingTypes(primitiveType));
            //matching = getMatchingTypes(primitiveType).contains(currentType.toUpperCase());
        }
        return matching;
    }
        
    public List<String> getMatchingTypes(String primitiveType) {
        if (primitiveType.equalsIgnoreCase("blob")) {
            return BLOB_TYPES;
        } 
        if (primitiveType.equalsIgnoreCase("boolean")) {
            return BOOLEAN_TYPES;
        } 
        if (primitiveType.equalsIgnoreCase("char")) {
            return CHAR_TYPES;
        } 
        if (primitiveType.equalsIgnoreCase("clob")) {
            return CLOB_TYPES;
        } 
        if (primitiveType.equalsIgnoreCase("double")) {          
            return DOUBLE_TYPES;
        } 
        if (primitiveType.equalsIgnoreCase("long")) {
            return LONG_TYPES;
        } 
        if (primitiveType.equalsIgnoreCase("integer")) {
            return INTEGER_TYPES;
        } 
        if (primitiveType.equalsIgnoreCase("number")) {
            return INTEGER_TYPES;
        }
        if (primitiveType.equalsIgnoreCase("String")) {
            return STRING_TYPES;
        } 
        if (primitiveType.equalsIgnoreCase("timestamp")) {
            return TIMESTAMP_TYPES;
        }
        return Collections.EMPTY_LIST;
    }
    
    public final boolean columnTypeConvertable(String primitiveType, long size, String currentType, long currentSize) {
        // System.err.println("Converting " + primitiveType + " [" + size + "]  &  " + currentType + " [" + currentSize + "]");
        boolean compatible = getCompatibleTypes(primitiveType).contains(currentType.toUpperCase());
        if (compatible) {
            // Additional checks goes here
        } else { 
            // Used for debugging
            //System.err.println("Converting " + primitiveType + " [" + size + "]  &  " + currentType + " [" + currentSize + "]");
            //compatible = getCompatibleTypes(primitiveType).contains(currentType.toUpperCase());
        }
        return compatible;
    }

    public List getCompatibleTypes(String primitiveType) {
        if (primitiveType.equalsIgnoreCase("blob")) {
            return BLOB_COMPATIBLES;
        } 
        if (primitiveType.equalsIgnoreCase("boolean")) {
            return BOOLEAN_COMPATIBLES;
        } 
        if (primitiveType.equalsIgnoreCase("char")) {
            return CHAR_COMPATIBLES;
        } 
        if (primitiveType.equalsIgnoreCase("clob")) {
            return CLOB_COMPATIBLES;
        } 
        if (primitiveType.equalsIgnoreCase("double")) {          
            return DOUBLE_COMPATIBLES;
        } 
        if (primitiveType.equalsIgnoreCase("long")) {
            return LONG_COMPATIBLES;
        } 
        if (primitiveType.equalsIgnoreCase("integer")) {
            return INTEGER_COMPATIBLES;
        } 
        if (primitiveType.equalsIgnoreCase("number")) {
            return INTEGER_COMPATIBLES;
        } 
        if (primitiveType.equalsIgnoreCase("String")) {
            return STRING_COMPATIBLES;
        } 
        if (primitiveType.equalsIgnoreCase("timestamp")) {
            return TIMESTAMP_COMPATIBLES;
        }
        return Collections.EMPTY_LIST;
    }

    //ALTER TABLE D_Table ADD [COLUMN] ABC NUMBER 
    public String getAddColumnCommand() {
        return " ADD ";
    }

    //ALTER TABLE D_Table DROP COLUMN ABC 
    public String getDropColumnCommand() {
        return " DROP COLUMN ";
    }

    //ALTER TABLE D_Table ALTER COLUMN ABC NUMBER 
    public String getModifyColumnCommand() {
        return " ALTER COLUMN ";
    }

    //ALTER TABLE D_Table ALTER COLUMN ABC SET DATA TYPE NUMBER (DB2 syntax requires 'SET DATA TYPE') 
    public String getColumnModificationTypeCommand() {
        return "";
    }
    
    public String getExecuteCommand() {
        return "";
    }
    
    public String getBeginTxnCommand() {
    	return "";
    }

    public String getCommitCommand() {
        return "";
    }

    public String getBeginDefinitionMarker() {
        return "";
    }

    public String getEndDefinitionMarker() {
        return "";
    }
    
    public String getNewline() {
        return ";" + JdbcDeployment.BRK;
    }

    public String formatDropTableSql(String tableName) {
        return "DROP TABLE " + tableName;
    }

    // Columns can be a comma separated string in case of multi-column index
    public String formatCreateIndexSql(String tableName, String indexName, String columns, boolean isUnique) {
        if (isUnique) {
            // Create PK, instead of unique indexes (CR#1-AFETYM)
            //return "CREATE UNIQUE INDEX " + indexName + " ON " + tableName + "(" + columns + ")";
            if (columns.indexOf(',') < 0) {
                return "ALTER TABLE " + tableName + " ADD PRIMARY KEY (" + columns + ")";
            } else {
                String primaryKeyName = indexName.replaceFirst("i_", "pk_");
                return "ALTER TABLE " + tableName + " ADD CONSTRAINT " + primaryKeyName + " PRIMARY KEY (" + columns + ")";
            }
        } else {
            return "CREATE INDEX " + indexName + " ON " + tableName + "(" + columns + ")";
        }
    }
    
    public String formatDropIndexSql(String tableName, String indexName, String columns, boolean isUnique) {
        if (isUnique) {
            if (columns.indexOf(',') < 0) {
                return "ALTER TABLE " + tableName + " DROP PRIMARY KEY";
            } else {
                String primaryKeyName = indexName.replaceFirst("i_", "pk_");
                return "ALTER TABLE " + tableName + " DROP CONSTRAINT " + primaryKeyName;
            }
        } else {
            return "DROP INDEX " + indexName;
        }
    }

    // FIX THIS - This has to be made abstract eventually, for now we put oracle style here
    public String formatObjectTableDeleteTimeCondition() {
        return "SELECT (sysdate - to_date('02-JAN-1970','DD-MON-YYYY')) * (86400000) FROM dual";
    }
    
    public String getConnectionCheckQuery() {
        return "SELECT typeId FROM ClassRegistry WHERE 1=2";
    }

    public String optimizeInsertStatement(String original) {
		return original;
    }

    public String optimizeSelectStatement(String original) {
		return original;
    }

    public String optimizeUpdateStatement(String original) {
		return original;
    }
    
    public String optimizeDeleteStatement(String original) {
		return original;
    }
}
