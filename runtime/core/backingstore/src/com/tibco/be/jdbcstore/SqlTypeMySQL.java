package com.tibco.be.jdbcstore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.tibco.be.model.rdf.RDFTypes;

public class SqlTypeMySQL extends SqlType {

    public int getMaxIdentifierLength() {
        return 64;
    }

    @Override
    public String formatDropTableSql(String tableName) {
        return "DROP TABLE IF EXISTS " + tableName;
    }

    @Override
    public String getdeleteAllCommand(String tableName) {
        return "DELETE FROM " + tableName;
    }

    public int getSqlTypeForPrimitiveType(int primitiveTypeIntId)
    {
        switch (primitiveTypeIntId)
        {
            case RDFTypes.BOOLEAN_TYPEID:
                return java.sql.Types.TINYINT;
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
    public String getColumnTypeForPrimitiveType(String primitiveType, long size)
    {
        if (RDBMSType.sUseAnsiType) {
            if (primitiveType.equalsIgnoreCase("String") && size > 8000) {
                return ("text");
            }
            else if (primitiveType.equalsIgnoreCase("Integer")) {
                return ("int");
            }
            else if (primitiveType.equalsIgnoreCase("boolean")) {
                return ("tinyint");
            }
            else if (primitiveType.equalsIgnoreCase("long")) {
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
        }
        else if (primitiveType.equalsIgnoreCase("Integer")) {
            return ("int");
        }
        else if (primitiveType.equalsIgnoreCase("long")) {
            return ("bigint");
        }
        else if (primitiveType.equalsIgnoreCase("double")) {
            return ("double precision");
        }
        else if (primitiveType.equalsIgnoreCase("boolean")) {
            return ("tinyint");
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

    @Override
    public List getMatchingTypes(String primitiveType) {
        BOOLEAN_TYPES = Arrays.asList(new String[] { "TINYINT" });
        INTEGER_TYPES = Arrays.asList(new String[] { "INT","INTEGER"});
        LONG_TYPES = Arrays.asList(new String[] { "BIGINT"});
        return super.getMatchingTypes(primitiveType);
    }

    @Override
    public List getCompatibleTypes(String primitiveType) {
        BLOB_COMPATIBLES = Arrays.asList(new String[] { "BLOB", "TINYBLOB", "MEDIUMBLOB" });
        BOOLEAN_COMPATIBLES = Arrays.asList(new String[] { "TINYINT", "BOOLEAN" });
        CHAR_COMPATIBLES = Arrays.asList(new String[] { "CHAR", "VARCHAR" });
        CLOB_COMPATIBLES = Arrays.asList(new String[] { "TEXT","MEDIUMTEXT", "LONGTEXT" });
        DOUBLE_COMPATIBLES = Arrays.asList(new String[] { "DOUBLE","FLOAT"});
        INTEGER_COMPATIBLES = Arrays.asList(new String[] { "INTEGER", "NUMBER", "NUMERIC" });
        LONG_COMPATIBLES = Arrays.asList(new String[] { "BIGINT"});
        STRING_COMPATIBLES = Arrays.asList(new String[] { "STRING", "CHAR", "VARCHAR"});
        TIMESTAMP_COMPATIBLES = Arrays.asList(new String[] { "TIMESTAMP", "DATETIME", "TIME", "DATE"});
        return super.getMatchingTypes(primitiveType);
    }

    public String getColumnTypeForPrimitiveType(int primitiveTypeIntId, long size)
    {
        switch (primitiveTypeIntId)
        {
            case RDFTypes.BOOLEAN_TYPEID:
                return ("tinyint");
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
        // TODO Auto-generated method stub
        return null;
    }

    private static HashMap<String,Integer> sequences = new HashMap<String,Integer>();
    //have create CacheIds as a system table
    @Override
    public String getSequenceCheckQuery(String sequenceName, int increment) {
        sequences.put(sequenceName, increment);
        return "SELECT cacheIdGeneratorName FROM CacheIds WHERE cacheIdGeneratorName='" + sequenceName + "'";
    }

    //mysql does not use sequences
    @Override
    public String getSequenceCreateQuery(String sequenceName, long minValue, long maxValue, long start, int increment) {
        sequences.put(sequenceName, increment);
        return "INSERT INTO CacheIds (cacheIdGeneratorName, nextCacheId) VALUES ('" + sequenceName + "', '" + start + "')";
    }

    @Override
    public String getSequenceDropQuery(String sequenceName) {
        sequences.remove(sequenceName);
        return "DELETE FROM CacheIds WHERE cacheIdGeneratorName='" + sequenceName + "'";
    }

    @Override
    public String getSequenceNextQuery(String sequenceName, boolean forUpdate) {
        int increment = 100;
        if (sequences.containsKey(sequenceName)) {
            increment = sequences.get(sequenceName);
        }
        if (forUpdate) {
            /*return "UPDATE CacheIds SET nextCacheId=" + increment + 
                    " + (SELECT nextCacheId FROM CacheIds WHERE cacheIdGeneratorName='" + sequenceName + "')" +
                    " WHERE cacheIdGeneratorName='" + sequenceName + "'";*/
        	
        	return "UPDATE CacheIds SET nextCacheId=" + increment +
					" + (SELECT t.nextCacheId FROM (select nextCacheId, cacheIdGeneratorName from CacheIds) t WHERE t.cacheIdGeneratorName='" + sequenceName + "') "
							+ "WHERE cacheIdGeneratorName='" + sequenceName + "'";
        }
        return "SELECT nextCacheId FROM CacheIds WHERE cacheIdGeneratorName='" + sequenceName + "'";
    }
    
    @Override
    public String formatDropIndexSql(String tableName, String indexName, String columns, boolean isUnique) {
        if (isUnique) {
            return "ALTER TABLE " + tableName + " DROP PRIMARY KEY";
        } else {
            return "ALTER TABLE " + tableName + " DROP INDEX " + indexName;
        }
    }

    @Override
    public String formatObjectTableDeleteTimeCondition() {
        return "SELECT (DATEDIFF(sysdate(),'1970-01-02')  * (86400000))";
    }
    
    @Override
    public String getModifyColumnCommand() {
        return " MODIFY ";
    }
}
